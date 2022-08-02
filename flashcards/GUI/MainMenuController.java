package samuelstrobel.flashcards.GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import samuelstrobel.flashcards.utilities.GlobalStatistics;
import samuelstrobel.flashcards.utilities.SaveLoad;
import samuelstrobel.flashcards.utilities.Decks;

public class MainMenuController implements Initializable{
	public Button bNewDeck;
	public Button bLoadDeck;
	public Button bResume;
	public Button bExportDeck;
	private FileChooser deckChooser = new FileChooser();


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//disable the button if there is no deck
		bResume.setDisable(GlobalStatistics.getDeck() == null);
		bExportDeck.setDisable(GlobalStatistics.getDeck() == null);
		
		//configure the file chooser window
		deckChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	}
	
	//handle the button click for new deck button
	public void handleNewButtonClick() throws IOException {
		//debug statement
		System.out.println("New Deck button clicked.");
		
		//change the scene to the create a deck scene and set this as the last scene
		GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/MainMenu.fxml");
		bNewDeck.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewDeck.fxml")));
	}
	 
	
	//handle the button click for load deck button
	public void handleLoadButtonClick() throws IOException {
		System.out.println("Load Deck button clicked.");
		
		//change the scene to the study scene with newly loaded deck and set this as the last scene
		GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/MainMenu.fxml");
		bNewDeck.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/OpenDeck.fxml")));
	}
	
	
	//handle the button click for resume deck button
	public void handleResumeButtonClick() throws IOException {
		//make sure there's a deck loaded and use it
		if(GlobalStatistics.getDeck() != null) {
		//change the scene to the study scene with currently loaded parameters and set this as the last scene
		GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/MainMenu.fxml");

    	if(GlobalStatistics.getStudy())
    		bNewDeck.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
    	else
    		bNewDeck.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyStatsView.fxml")));
		} else
			bResume.setText("There are no decks being studied!");
	}
	
	//handle the button click for the export deck button
	public void handleExportButtonClick() throws IOException {
		System.out.println("Export Deck button clicked.");
		
		//add file filters
		deckChooser.setTitle("Save Deck");
		deckChooser.getExtensionFilters().clear();
		deckChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Comma Separated Values (.csv)", ".csv"),
				new FileChooser.ExtensionFilter("Tab Separated Values (.xls)", ".xls")
		);
		
		//open a file explorer and allow the user to find the deck they want to open
		File file = deckChooser.showSaveDialog(bNewDeck.getScene().getWindow());
		if(file != null) {
			System.out.println(file.getName());
			if(GlobalStatistics.getTempDeck() != null)
				SaveLoad.output(GlobalStatistics.getTempDeck(), file);
			else
				SaveLoad.output(GlobalStatistics.getDeck(), file);
		}
	}
}
