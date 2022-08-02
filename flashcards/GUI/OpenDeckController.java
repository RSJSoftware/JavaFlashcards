package samuelstrobel.flashcards.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import samuelstrobel.flashcards.utilities.Decks;
import samuelstrobel.flashcards.utilities.GlobalStatistics;
import samuelstrobel.flashcards.utilities.SaveLoad;
import samuelstrobel.flashcards.utilities.Session;

public class OpenDeckController implements Initializable {
	public Button bBack;
	public Button bFind;
	public Button bOpen;
	public Label lDeckName;
	public Label lDeckDes;
	public Label lDeckLen;
	public ListView<String> listDecks;
	private FileChooser deckChooser = new FileChooser();
	
    private ObservableList<String> decks = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File folder = new File(System.getProperty("user.dir"));
		File[] allFiles = folder.listFiles();
		
        //fill in the list view with the decks
        decks.clear();
        for(int i = 0; i < allFiles.length; i++)
        	if(allFiles[i].getName().substring(allFiles[i].getName().lastIndexOf(".") + 1, allFiles[i].getName().length()).equals("fcd"))
        		decks.add(allFiles[i].getName().substring(0, allFiles[i].getName().lastIndexOf(".")));
        //if there were no decks, tell the user and disable the view
        if(decks.size() == 0) {
        	decks.add("There are no decks to display.");
        	listDecks.setDisable(true);
        }
        listDecks.getItems().addAll(decks);
        
        deckChooser.setInitialDirectory(folder);
        
        //make the open button dependent on a selection from the list
        bOpen.disableProperty().bind(listDecks.getSelectionModel().selectedItemProperty().isNull());
        
        //add a listener to the list so that if it's selected it will display the stats of the deck for the user to see
        listDecks.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {updateLabels();});
	}
	
	public void handleBackButtonClick() throws IOException {
		System.out.println("Cancel button pressed!");
		
        //change the scene to the previous scene
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
	}
	
	public void handleFindButtonClick() throws IOException {
		System.out.println("Find Deck button clicked.");
		
		//add file filters
		deckChooser.setTitle("Open Deck");
		deckChooser.getExtensionFilters().clear();
		deckChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Flashcard Decks (.fcd)", "*.fcd*"),
			new FileChooser.ExtensionFilter("Comma Separated Values (.csv)", "*.csv*"),
			new FileChooser.ExtensionFilter("Tab Separated Values (.xls)", "*.xls*"),
			new FileChooser.ExtensionFilter("All files", "*.*")
		);
		
		//open a file explorer and allow the user to find the deck they want to open
		File file = deckChooser.showOpenDialog(bFind.getScene().getWindow());
		if(file != null)
			SaveLoad.load(file);
		
		//open a deck as long as the load was successful
		if(GlobalStatistics.getDeck() != null && GlobalStatistics.getJustLoaded()) {
			//change the scene to the study scene with newly loaded deck using the default settings
			GlobalStatistics.setJustLoaded(false);
			Session.deckDefault();
			GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/MainMenu.fxml");
			bFind.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
		}
	}
	
	public void handleOpenButtonClick() throws IOException {
		//open the selected deck
		File load = new File(listDecks.getSelectionModel().getSelectedItem().toString() + ".fcd");
		File loadState = new File(listDecks.getSelectionModel().getSelectedItem().toString() + ".svs");
		SaveLoad.load(load);
		
		if(loadState.exists()) {
			//ask if the user wants to load the previous state of this deck
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Notice");
			alert.setContentText("Do you want to load your previous study session for this deck?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				//if the user says yes, load the state
				SaveLoad.loadState(listDecks.getSelectionModel().getSelectedItem().toString());
			}
		}
		
		//open a deck as long as the load was successful
		if(GlobalStatistics.getDeck() != null && GlobalStatistics.getJustLoaded()) {
			//change the scene to the study scene with newly loaded deck
			GlobalStatistics.setJustLoaded(false);
			GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/MainMenu.fxml");
			
			if(GlobalStatistics.getStudy())
				bFind.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
			else
				bFind.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyStatsView.fxml")));
		}
	}

	public void updateLabels() {
		//load the selected file
		Decks deck;
		try {
			FileInputStream load = new FileInputStream(listDecks.getSelectionModel().getSelectedItem().toString() + ".fcd");
			ObjectInputStream in = new ObjectInputStream(load);
			deck = (Decks) in.readObject();
			in.close();
			load.close();
		} catch(IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Decks class not found");
			c.printStackTrace();
			return;
		} 
		
		lDeckName.setText(deck.getName());
		lDeckDes.setText(deck.getDescription());
		lDeckLen.setText(deck.getDeckLength() + "");
	}
}
