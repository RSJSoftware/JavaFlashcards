package samuelstrobel.flashcards.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import samuelstrobel.flashcards.utilities.*;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class NewCardController implements Initializable{
	public Button bBack;
	public Button bConfirm;
	public Button bDelete;
	public TextArea tSideOne;
	public TextArea tSideTwo;
	public TextArea tSideThree;
	public TextArea tSideFour;
	public TextArea tSideFive;
	public CheckBox cheFlag;
	public ChoiceBox<String> choCatOne;
	public ChoiceBox<String> choCatTwo;
	public ChoiceBox<String> choCatThree;
	public ChoiceBox<String> choCatFour;
	public Label lDeSelOne;
	public Label lDeSelTwo;
	public Label lDeSelThree;
	public Label lDeSelFour;
	
	private boolean isDeleted = false;
	private ChangeListener<Number> catOneListen;
	private ChangeListener<Number> catTwoListen;
	private ChangeListener<Number> catThreeListen;
	private ChangeListener<Number> catFourListen;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		GlobalStatistics.setEditCategory(-1);
		
		//disable confirm button until one side if filled out, and the rest of the sides if the previous sides have nothing in them yet
		bConfirm.disableProperty().bind(Bindings.isEmpty(tSideOne.textProperty()));
		tSideThree.disableProperty().bind(Bindings.isEmpty(tSideOne.textProperty()).or(Bindings.isEmpty(tSideTwo.textProperty())));
		tSideFour.disableProperty().bind(Bindings.isEmpty(tSideOne.textProperty()).or(Bindings.isEmpty(tSideTwo.textProperty()).or(Bindings.isEmpty(tSideThree.textProperty()))));
		tSideFive.disableProperty().bind(Bindings.isEmpty(tSideOne.textProperty()).or(Bindings.isEmpty(tSideTwo.textProperty()).or(Bindings.isEmpty(tSideThree.textProperty()).or(Bindings.isEmpty(tSideFour.textProperty())))));
	
		//if it's a new card, disable the delete button, as it's useless
		if(GlobalStatistics.getCard().getNew()) {
			bDelete.setDisable(true);
			bDelete.setVisible(false);
		}
		
		//initialize choice box selections
		initializeCats();
		
		//load info is this is an edit and not a new card
		if(GlobalStatistics.getCard() != null) {
			tSideOne.setText(GlobalStatistics.getCard().getSide(0));
			tSideTwo.setText(GlobalStatistics.getCard().getSide(1));
			tSideThree.setText(GlobalStatistics.getCard().getSide(2));
			tSideFour.setText(GlobalStatistics.getCard().getSide(3));
			tSideFive.setText(GlobalStatistics.getCard().getSide(4));

			choCatOne.getSelectionModel().select(GlobalStatistics.getCard().getCategory(1));
			choCatTwo.getSelectionModel().select(GlobalStatistics.getCard().getCategory(2));
			choCatThree.getSelectionModel().select(GlobalStatistics.getCard().getCategory(3));
			choCatFour.getSelectionModel().select(GlobalStatistics.getCard().getCategory(4));
			
			cheFlag.setSelected(GlobalStatistics.getCard().getFlag());
		}
	}
	
	
	public void handleBackButtonClick() throws IOException {
		//debug statement
		System.out.println("Back button clicked.");
		//change the scene to the previous scene
		if(GlobalStatistics.popScene().equals("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml")){
            //remove a card if it's new, sort it to make sense if the card is being deleted
            if(GlobalStatistics.getCard().getNew()) {
                if(GlobalStatistics.getTempDeck() == null) {
                	if(isDeleted)
                		GlobalStatistics.getDeck().sort(GlobalStatistics.getSortedSide());
                	GlobalStatistics.getDeck().removeCard(GlobalStatistics.getCardIndex());
                } else {
                	if(isDeleted)
                		GlobalStatistics.getTempDeck().sort(GlobalStatistics.getSortedSide());
                	GlobalStatistics.getTempDeck().removeCard(GlobalStatistics.getCardIndex());
                }
                
                GlobalStatistics.setCard(GlobalStatistics.getTempCard() );
                GlobalStatistics.setCardIndex(GlobalStatistics.getTempCardIndex());
            }
            
            //save in case a new category was made
            if(GlobalStatistics.getTempDeck() == null)
            	SaveLoad.save(GlobalStatistics.getDeck());
            else
            	SaveLoad.save(GlobalStatistics.getTempDeck());
            
            //reset the deck if a card was deleted
            if(isDeleted) {
            	if(GlobalStatistics.getTempDeck() != null){
            		GlobalStatistics.setDeck(GlobalStatistics.getTempDeck());
            		GlobalStatistics.removeTempDeck();
            	}
            	GlobalStatistics.initializeDeck();
            	GlobalStatistics.setTempCard(GlobalStatistics.getCard());
            	GlobalStatistics.setTempCardIndex(GlobalStatistics.getCardIndex());
            	//change back to the study view as well
            	if(!GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/StudyView.fxml")) {
            		GlobalStatistics.setStudy(true);
            		GlobalStatistics.popScene();
            		GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyView.fxml");
            	}
            }
            
            bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml")));
        } else {
            bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewDeck.fxml")));
        }
	}
	
	public void handleConfButtonClick() throws IOException {
		//make sure if it was a new card
		boolean wasNew = false;
		
		//debug statement
		System.out.println("Card created.");
		//create a card and fill it in
		Cards card = GlobalStatistics.getCard();
		card.setSide(0, tSideOne.getText());
		if(!tSideTwo.getText().isEmpty())
			card.setSide(1, tSideTwo.getText());
		else
			card.setSide(1, "");
		if(!tSideThree.getText().isEmpty())
			card.setSide(2, tSideThree.getText());
		else
			card.setSide(2, "");
		if(!tSideFour.getText().isEmpty())
			card.setSide(3, tSideFour.getText());
		else
			card.setSide(3, "");
		if(!tSideFive.getText().isEmpty())
			card.setSide(4, tSideFive.getText());
		else
			card.setSide(4, "");
        
		System.out.println(choCatOne.getSelectionModel().isEmpty());
		if(!choCatOne.getSelectionModel().isEmpty()) {
			if(GlobalStatistics.getTempDeck() != null) {
				if(GlobalStatistics.getTempDeck().getCatLength(1) != choCatOne.getSelectionModel().getSelectedIndex())
					card.setCategory(1, choCatOne.getSelectionModel().getSelectedIndex());
			} else {
				if(GlobalStatistics.getDeck().getCatLength(1) != choCatOne.getSelectionModel().getSelectedIndex())
					card.setCategory(1, choCatOne.getSelectionModel().getSelectedIndex());
			}
		} else
			card.setCategory(1, -1);
		if(!choCatTwo.getSelectionModel().isEmpty()) {
			if(GlobalStatistics.getTempDeck() != null) {
				if(GlobalStatistics.getTempDeck().getCatLength(2) != choCatTwo.getSelectionModel().getSelectedIndex())
					card.setCategory(2, choCatTwo.getSelectionModel().getSelectedIndex());
			} else {
				if(GlobalStatistics.getDeck().getCatLength(2) != choCatTwo.getSelectionModel().getSelectedIndex())
					card.setCategory(2, choCatTwo.getSelectionModel().getSelectedIndex());
			}
		} else
			card.setCategory(2, -1);
		if(!choCatThree.getSelectionModel().isEmpty()) {
			if(GlobalStatistics.getTempDeck() != null) {
				if(GlobalStatistics.getTempDeck().getCatLength(3) != choCatThree.getSelectionModel().getSelectedIndex())
					card.setCategory(3, choCatThree.getSelectionModel().getSelectedIndex());
			} else {
				if(GlobalStatistics.getDeck().getCatLength(3) != choCatThree.getSelectionModel().getSelectedIndex())
					card.setCategory(3, choCatThree.getSelectionModel().getSelectedIndex());
			}
		} else
			card.setCategory(3, -1);
		if(!choCatFour.getSelectionModel().isEmpty()) {
			if(GlobalStatistics.getTempDeck() != null) {
				if(GlobalStatistics.getTempDeck().getCatLength(4) != choCatFour.getSelectionModel().getSelectedIndex())
					card.setCategory(4, choCatFour.getSelectionModel().getSelectedIndex());
			} else {
				if(GlobalStatistics.getDeck().getCatLength(4) != choCatFour.getSelectionModel().getSelectedIndex())
					card.setCategory(4, choCatFour.getSelectionModel().getSelectedIndex());
			}
		}else
			card.setCategory(4, -1);
		
		card.setFlag(cheFlag.isSelected());
		
        //remove the new card flag reset the flag if it was new
		if(card.getNew()) {
        	if(GlobalStatistics.getTempDeck() != null){
        		GlobalStatistics.setDeck(GlobalStatistics.getTempDeck());
        		GlobalStatistics.removeTempDeck();
        	}
			card.setNew(false);
	    	GlobalStatistics.initializeDeck();
	    	GlobalStatistics.setTempCard(GlobalStatistics.getCard());
	    	GlobalStatistics.setTempCardIndex(GlobalStatistics.getCardIndex());
	    	
	    	wasNew = true;
		}
		
        if(GlobalStatistics.getTempDeck() == null)
        	SaveLoad.save(GlobalStatistics.getDeck());
        else
        	SaveLoad.save(GlobalStatistics.getTempDeck());
        
		//set main menu as the last scene
		if(GlobalStatistics.popScene().equals("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml")) {
			bConfirm.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml")));
            if(wasNew) {
            	//change back to the study view as well
            	if(!GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/StudyView.fxml")) {
            		GlobalStatistics.setStudy(true);
            		GlobalStatistics.popScene();
            		GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyView.fxml");
            	}        
            }
		} else {
                    GlobalStatistics.initializeDeck();
                    bConfirm.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
                }
	}
    
    public void handleDeleteButtoneClick() throws IOException {
    	//make sure the user wants to delete the card, delete it if they do and go to the previous screen
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("WARNING");
		alert.setContentText("Do you really want to delete this card?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
	    	GlobalStatistics.getCard().setNew(true);
	    	isDeleted = true;
	    	
	    	handleBackButtonClick();
		} else {
		    System.out.println("nevermind...");
		}
    }
	
    public void handleDeSelOneClick() {
    	//clear the selection
    	choCatOne.getSelectionModel().clearSelection();
    }
    
    public void handleDeSelTwoClick() {
    	//clear the selection
    	choCatTwo.getSelectionModel().clearSelection();
    }
    
    public void handleDeSelThreeClick() {
    	//clear the selection
    	choCatThree.getSelectionModel().clearSelection();
    }
    
    public void handleDeSelFourClick() {
    	//clear the selection
    	choCatFour.getSelectionModel().clearSelection();
    }
    
	private void callCatBox(int cat) throws IOException{
		GlobalStatistics.setEditCategory(cat);
		
		//give the user a dialog to enter a new category
		TextInputDialog newCatDialog = new TextInputDialog();
		newCatDialog.setTitle("Category Input Dialog");
		newCatDialog.setHeaderText("Category " + cat);
		newCatDialog.setContentText("Please enter a category: ");
		
		//make an error dialog just in case the user enters a duplicate category
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Duplicate Alert");
		alert.setHeaderText("Duplicate Category");
		alert.setContentText("A category by this name already exists!");
		
		//if the user enters something, add a new category and update the list
		Optional<String> result = newCatDialog.showAndWait();
	        if(result.isPresent()) {
	        	boolean isDup = false;
			//add the new category as long as it's not a duplicate, otherwise cancel, and warn the user
	        if(GlobalStatistics.getTempDeck() == null) {
	        	for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(cat); i++) {
	        		if(GlobalStatistics.getDeck().getCategory(cat, i).equals(result.get())) {
	        			alert.showAndWait();
	        			isDup = true;
	        		}
	        	}//end loop
	        	//if the category is not a duplicate, add it
	        	if(!isDup)
        			GlobalStatistics.getDeck().addCategory(cat, result.get());
	        }
	        else {
	        	for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(cat); i++) {
	        		if(GlobalStatistics.getTempDeck().getCategory(cat, i).equals(result.get())) {
	        			alert.showAndWait();
	        			isDup = true;
	        		}
	        	}//end loop
	        	//if the category is not a duplicate, add it
	        	if(!isDup)
        			GlobalStatistics.getTempDeck().addCategory(cat, result.get());
	        }
	
			//save with the new category
	        if(GlobalStatistics.getTempDeck() == null)
	        	SaveLoad.save(GlobalStatistics.getDeck());
	        else
	        	SaveLoad.save(GlobalStatistics.getTempDeck());

	        //reset the listener of whatever was just modified and the categories to reflect the changes
	        if(cat == 1)
	        	choCatOne.getSelectionModel().selectedIndexProperty().removeListener(catOneListen);
	        else if(cat == 2)
	        	choCatTwo.getSelectionModel().selectedIndexProperty().removeListener(catTwoListen);
	        else if(cat == 3)
	        	choCatThree.getSelectionModel().selectedIndexProperty().removeListener(catThreeListen);
	        else if(cat == 4)
	        	choCatFour.getSelectionModel().selectedIndexProperty().removeListener(catFourListen);
	        initializeCats();
		}
	}
	
	private void initializeCats() {
		//start each length out at one, because each choice box will at least have one choice 
		int catOneLength = 1;
		int catTwoLength = 1;
		int catThreeLength = 1;
		int catFourLength = 1;
		
		//initialize choice box selections
		if(GlobalStatistics.getEditCategory() == -1 || GlobalStatistics.getEditCategory() == 1) {
			choCatOne.getItems().clear();
			if(GlobalStatistics.getTempDeck() != null) {
				for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(1); i++) {
					if(!"".equals(GlobalStatistics.getTempDeck().getCategory(1, i))) {
						choCatOne.getItems().add(GlobalStatistics.getTempDeck().getCategory(1, i));
						catOneLength++;
					}
				}
			} else {
				for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(1); i++) {
					if(!"".equals(GlobalStatistics.getDeck().getCategory(1, i))) {
						choCatOne.getItems().add(GlobalStatistics.getDeck().getCategory(1, i));
						catOneLength++;
					}
				}
			}
			choCatOne.getItems().add("New Category...");
			//set the final length for the category
			int catOneLengthFinal = catOneLength;
	
			//set a listener on the category choice box so that if "New Category..." is selected, it allows the user to create one right away
			catOneListen = (v, oldValue, newValue) -> {if((int)newValue == (catOneLengthFinal - 1))
				try {
					callCatBox(1);
				} catch (IOException e) {
					e.printStackTrace();
				}};
			choCatOne.getSelectionModel().selectedIndexProperty().addListener(catOneListen);

			//select the category that was just added if one was
			if(GlobalStatistics.getEditCategory() == 1)
				choCatOne.getSelectionModel().select(catOneLengthFinal - 2);
		}
		
		//repeat for category two
		if(GlobalStatistics.getEditCategory() == -1 || GlobalStatistics.getEditCategory() == 2) {
			choCatTwo.getItems().clear();
			if(GlobalStatistics.getTempDeck() != null) {
				for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(2); i++) {
					if(!"".equals(GlobalStatistics.getTempDeck().getCategory(2, i))) {
						choCatTwo.getItems().add(GlobalStatistics.getTempDeck().getCategory(2, i));
						catTwoLength++;
					}
				}
			} else {
				for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(2); i++) {
					if(!"".equals(GlobalStatistics.getDeck().getCategory(2, i))) {
						choCatTwo.getItems().add(GlobalStatistics.getDeck().getCategory(2, i));
						catTwoLength++;
					}
				}
			}
			choCatTwo.getItems().add("New Category...");
			int catTwoLengthFinal = catTwoLength;
			catTwoListen = (v, oldValue, newValue) -> {if((int)newValue == (catTwoLengthFinal - 1))
				try {
					callCatBox(2);
				} catch (IOException e) {
					e.printStackTrace();
				}};
			choCatTwo.getSelectionModel().selectedIndexProperty().addListener(catTwoListen);

			//select the category that was just added if one was
			if(GlobalStatistics.getEditCategory() == 2)
				choCatTwo.getSelectionModel().select(catTwoLengthFinal - 2);
		}
		
		//repeat for category three
		if(GlobalStatistics.getEditCategory() == -1 || GlobalStatistics.getEditCategory() == 3) {
			choCatThree.getItems().clear();
			if(GlobalStatistics.getTempDeck() != null) {
				for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(3); i++) {
					if(!"".equals(GlobalStatistics.getTempDeck().getCategory(3, i))) {
						choCatThree.getItems().add(GlobalStatistics.getTempDeck().getCategory(3, i));
						catThreeLength++;
					}
				}
			} else {
				for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(3); i++) {
					if(!"".equals(GlobalStatistics.getDeck().getCategory(3, i))) {
						choCatThree.getItems().add(GlobalStatistics.getDeck().getCategory(3, i));
						catThreeLength++;
					}
				}
			}
			choCatThree.getItems().add("New Category...");
			int catThreeLengthFinal = catThreeLength;
			catThreeListen = (v, oldValue, newValue) -> {if((int)newValue == (catThreeLengthFinal - 1))
				try {
					callCatBox(3);
				} catch (IOException e) {
					e.printStackTrace();
				}};
			choCatThree.getSelectionModel().selectedIndexProperty().addListener(catThreeListen);

			//select the category that was just added if one was
			if(GlobalStatistics.getEditCategory() == 3)
				choCatThree.getSelectionModel().select(catThreeLengthFinal - 2);
		}
		
		//repeat for category four
		if(GlobalStatistics.getEditCategory() == -1 || GlobalStatistics.getEditCategory() == 4) {
			choCatFour.getItems().clear();
			if(GlobalStatistics.getTempDeck() != null) {
				for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(4); i++) {
					if(!"".equals(GlobalStatistics.getTempDeck().getCategory(4, i))) {
						choCatFour.getItems().add(GlobalStatistics.getTempDeck().getCategory(4, i));
						catFourLength++;
					}
				}
			} else {
				for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(4); i++) {
					if(!"".equals(GlobalStatistics.getDeck().getCategory(4, i))) {
						choCatFour.getItems().add(GlobalStatistics.getDeck().getCategory(4, i));
						catFourLength++;
					}
				}
			}
			choCatFour.getItems().add("New Category...");
			int catFourLengthFinal = catFourLength;
			catFourListen = (v, oldValue, newValue) -> {if((int)newValue == (catFourLengthFinal - 1))
				try {
					callCatBox(4);
				} catch (IOException e) {
					e.printStackTrace();
				}};
			choCatFour.getSelectionModel().selectedIndexProperty().addListener(catFourListen);
			
			//select the category that was just added if one was
			if(GlobalStatistics.getEditCategory() == 4)
				choCatFour.getSelectionModel().select(catFourLengthFinal - 2);
		}
	}

}
