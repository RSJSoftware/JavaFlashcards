package samuelstrobel.flashcards.GUI;

import samuelstrobel.flashcards.utilities.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewDeckController implements Initializable {
	public Button bConfirm;
	public Button bBack;
	public Button bDelete;
	public Button bCats;
	public TextArea tDescription;
	public TextField tName;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//make sure there's a current deck and the user didn't just press new deck
		if(GlobalStatistics.getTempDeck() != null && !GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/MainMenu.fxml")) {
			tName.setText(GlobalStatistics.getTempDeck().getName());
			tDescription.setText(GlobalStatistics.getTempDeck().getDescription());
		} else if(GlobalStatistics.getDeck() != null && (!GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/MainMenu.fxml") || GlobalStatistics.getDeck().getCard(0).getNew())) {
			tName.setText(GlobalStatistics.getDeck().getName());
			tDescription.setText(GlobalStatistics.getDeck().getDescription());
		}
		
		//makes the confirm button unclickable if there is no name inserted
		bConfirm.disableProperty().bind(Bindings.isEmpty(tName.textProperty()));
		
		//make the delete and category buttons unavailable if it's not in edit mode
		if(GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/MainMenu.fxml")) {
			bDelete.setVisible(false);
			bDelete.setDisable(true);
			bCats.setVisible(false);
			bCats.setDisable(true);
		}
	}
	
	
	public void handleBackButtonClick() throws IOException {
		//debug statement
		System.out.println("Back button clicked.");
		
		//delete the deck if the user is canceling making a new deck after it's already been created and set
		if(GlobalStatistics.getDeck() != null && GlobalStatistics.getDeck().getCard(0).getNew()) {
		    GlobalStatistics.initialize();
			GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/MainMenu.fxml");
		}
		
		//change the scene to the create a deck scene and clear anything that was set
		bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
	}
	
	public void handleDeleteButtonClick() throws IOException {
		//make sure the user really wants to delete this deck, if they do, delete the deck save and go back to the main menu
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("WARNING");
		if(GlobalStatistics.getTempDeck() != null) {
			alert.setContentText("Do you really want to delete " + GlobalStatistics.getTempDeck().getName() + "?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    SaveLoad.delete(GlobalStatistics.getTempDeck());
			    GlobalStatistics.initialize();
				bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/MainMenu.fxml")));
			} else {
			    System.out.println("nevermind...");
			}
		} else {
			alert.setContentText("Do you really want to delete " + GlobalStatistics.getDeck().getName() + "?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    SaveLoad.delete(GlobalStatistics.getDeck());
			    GlobalStatistics.initialize();
				bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/MainMenu.fxml")));
			} else {
			    System.out.println("nevermind...");
			}
		}
	}
	
	public void handleConfButtonClick() throws IOException {
		//boolean for checks
		boolean isOkay = true;
		boolean isCurrent = true;
		
		//debug statement
		System.out.println("Confirm button clicked.");
		System.out.println("Name is: " + tName.getText());
		System.out.println("Description is: " + tDescription.getText());
		
		//make sure it's not just the same name as the current deck and this deck isn't a new deck
		if(GlobalStatistics.getTempDeck() != null) {
			if(!tName.getText().equals(GlobalStatistics.getTempDeck().getName()) || GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/MainMenu.fxml")) {
				isCurrent = false;
			}
		} else if(GlobalStatistics.getDeck() != null) {
			if(!tName.getText().equals(GlobalStatistics.getDeck().getName()) || GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/MainMenu.fxml")) {
				isCurrent = false;
			}
		}

		//make sure a deck by this name does not already exist if it's not the same name as the current deck
		if(!isCurrent) {
			File folder = new File(System.getProperty("user.dir"));
			File[] allFiles = folder.listFiles();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("WARNING");
			alert.setContentText("A deck by this name already exists, delete and replace it?");
			
			
			for(int i = 0; i < allFiles.length; i++)
	        	if(allFiles[i].getName().substring(allFiles[i].getName().lastIndexOf(".") + 1, allFiles[i].getName().length()).equals("fcd"))
	        		if(allFiles[i].getName().substring(0, allFiles[i].getName().lastIndexOf(".")).equals(tName.getText())) {
	        			Optional<ButtonType> result = alert.showAndWait();
	        			if (result.get() == ButtonType.OK){
	        			    System.out.println("replacing deck");
	        			} else {
	        			    System.out.println("nevermind...");
	        			    isOkay = false;
	        			}
        		}
		}
		
		//if the deck name is a new one, or cleared to replace and old deck, made a new deck
		if(isOkay) {
			if(GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/MainMenu.fxml")) {
				//reset the deck and all
				GlobalStatistics.removeCard();
				GlobalStatistics.removeDeck();
				GlobalStatistics.removeTempCard();
				GlobalStatistics.removeTempDeck();
				GlobalStatistics.setCorrectStats(0);
				GlobalStatistics.removeDeckView();
				
				//make and set a new deck
				Decks deck = new Decks();
				GlobalStatistics.setDeck(deck);
				GlobalStatistics.setCard(GlobalStatistics.getDeck().getCard(0));
				GlobalStatistics.setCardIndex(0);
				
				Session.deckDefault();
			} else {
				//delete the current save of the deck to rename it
				if(GlobalStatistics.getTempDeck() != null)
					SaveLoad.delete(GlobalStatistics.getTempDeck());
				else
					SaveLoad.delete(GlobalStatistics.getDeck());
			}
			
			//update the current deck name and description if it exists
			if(GlobalStatistics.getTempDeck() != null) {
				GlobalStatistics.getTempDeck().setName(tName.getText());
				
				if(!tDescription.getText().isEmpty())
					GlobalStatistics.getTempDeck().setDescription(tDescription.getText());
			} else {
				GlobalStatistics.getDeck().setName(tName.getText());
				
				if(!tDescription.getText().isEmpty())
					GlobalStatistics.getDeck().setDescription(tDescription.getText());
			}
			
			//set this scene as the last scene if this is a new deck
			if(GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/MainMenu.fxml")) {
				GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/NewDeck.fxml");
				bConfirm.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewCard.fxml")));
			} else {
				//save the changes made to the deck
				if(GlobalStatistics.getTempDeck() != null)
					SaveLoad.save(GlobalStatistics.getTempDeck());
				else
					SaveLoad.save(GlobalStatistics.getDeck());
				bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
			}
		}
	}
	
	public void handleCatsButtonClick() throws IOException {
		//debug statement
		System.out.println("Category button clicked.");
		
		//push this scene and go to the category scene
		GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/NewDeck.fxml");
		bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewCategory.fxml")));
	}
}