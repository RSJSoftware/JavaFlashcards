package samuelstrobel.flashcards.GUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import samuelstrobel.flashcards.utilities.GlobalStatistics;

public class StudyOptionsController implements Initializable {
	public Button bBack;
	public Button bConfirm;
	public Button bConfirmGlobal;
	public CheckBox cheShuffle;
	public CheckBox cheFlagged;
	public CheckBox cheType;
	public CheckBox cheCatOne;
	public CheckBox cheCatTwo;
	public CheckBox cheCatThree;
	public CheckBox cheCatFour;
	public ChoiceBox choStartSide;
	public ListView<String> lCatOne;
	public ListView<String> lCatTwo;
	public ListView<String> lCatThree;
	public ListView<String> lCatFour;

    private ObservableList<String> catOne = FXCollections.observableArrayList();
    private ObservableList<String> catTwo = FXCollections.observableArrayList();
    private ObservableList<String> catThree = FXCollections.observableArrayList();
    private ObservableList<String> catFour = FXCollections.observableArrayList();
    private int catOneLength = 0;
    private int catTwoLength = 0;
    private int catThreeLength = 0;
    private int catFourLength = 0; 
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String[] selectedCats = {""};
		//load the current parameters
		cheShuffle.setSelected(GlobalStatistics.getShuffle());
		cheFlagged.setSelected(GlobalStatistics.getOnlyFlag());
		cheType.setSelected(GlobalStatistics.getType());
		cheCatOne.setSelected(GlobalStatistics.getIsSelectedCat(1));
		cheCatTwo.setSelected(GlobalStatistics.getIsSelectedCat(2));
		cheCatThree.setSelected(GlobalStatistics.getIsSelectedCat(3));
		cheCatFour.setSelected(GlobalStatistics.getIsSelectedCat(4));
		
		//set up the choice box
		choStartSide.getItems().addAll("1", "2");
		
        //set current start side
		if(GlobalStatistics.getStartSide())
			choStartSide.getSelectionModel().select(0);
		else
			choStartSide.getSelectionModel().select(1);
			
		
		//set all lists
		catOne.clear();
		catTwo.clear();
		catThree.clear();
		catFour.clear();
		
		//make sure there's a deck and fill it in depending on which deck is the main one
		if(GlobalStatistics.getDeck() != null) {
			if(GlobalStatistics.getTempDeck() != null) {
				catOneLength = GlobalStatistics.getTempDeck().getCatLength(1);
				catTwoLength = GlobalStatistics.getTempDeck().getCatLength(2);
				catThreeLength = GlobalStatistics.getTempDeck().getCatLength(3);
				catFourLength = GlobalStatistics.getTempDeck().getCatLength(4);
				
		        for(int i = 0; i < catOneLength; i++)
		        	catOne.add(GlobalStatistics.getTempDeck().getCategory(1, i));
		        for(int i = 0; i < catTwoLength; i++)
		        	catTwo.add(GlobalStatistics.getTempDeck().getCategory(2, i));
		        for(int i = 0; i < catThreeLength; i++)
		        	catThree.add(GlobalStatistics.getTempDeck().getCategory(3, i));
		        for(int i = 0; i < catFourLength; i++)
		        	catFour.add(GlobalStatistics.getTempDeck().getCategory(4, i));
			} else {
				catOneLength = GlobalStatistics.getDeck().getCatLength(1);
				catTwoLength = GlobalStatistics.getDeck().getCatLength(2);
				catThreeLength = GlobalStatistics.getDeck().getCatLength(3);
				catFourLength = GlobalStatistics.getDeck().getCatLength(4);
				
		        for(int i = 0; i < catOneLength; i++)
		        	catOne.add(GlobalStatistics.getDeck().getCategory(1, i));
		        for(int i = 0; i < catTwoLength; i++)
		        	catTwo.add(GlobalStatistics.getDeck().getCategory(2, i));
		        for(int i = 0; i < catThreeLength; i++)
		        	catThree.add(GlobalStatistics.getDeck().getCategory(3, i));
		        for(int i = 0; i < catFourLength; i++)
		        	catFour.add(GlobalStatistics.getDeck().getCategory(4, i));
			}
			
			//add unassigned option
			catOne.add("unassigned");
			catTwo.add("unassigned");
			catThree.add("unassigned");
			catFour.add("unassigned");
			
			//add all options to the lists
	        lCatOne.getItems().addAll(catOne);
	        lCatTwo.getItems().addAll(catTwo);
	        lCatThree.getItems().addAll(catThree);
	        lCatFour.getItems().addAll(catFour);
		}
		
		//allow multiple items to be selected on this lists
		lCatOne.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lCatTwo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lCatThree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lCatFour.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//set the selected
		selectedCats = GlobalStatistics.getSelectedCat(1).split(",");
		for(int i = 0; i < selectedCats.length; i++)
			if(Integer.parseInt(selectedCats[i]) == -1)
				lCatOne.getSelectionModel().select(catOneLength); 
			else
				lCatOne.getSelectionModel().select(Integer.parseInt(selectedCats[i])); 
				
		selectedCats = GlobalStatistics.getSelectedCat(2).split(",");
		for(int i = 0; i < selectedCats.length; i++)
			if(Integer.parseInt(selectedCats[i]) == -1)
				lCatTwo.getSelectionModel().select(catTwoLength);
			else
				lCatTwo.getSelectionModel().select(Integer.parseInt(selectedCats[i])); 

		selectedCats = GlobalStatistics.getSelectedCat(3).split(",");
		for(int i = 0; i < selectedCats.length; i++)
			if(Integer.parseInt(selectedCats[i]) == -1)
				lCatThree.getSelectionModel().select(catThreeLength);
			else
				lCatThree.getSelectionModel().select(Integer.parseInt(selectedCats[i])); 

		selectedCats = GlobalStatistics.getSelectedCat(4).split(",");
		for(int i = 0; i < selectedCats.length; i++)
			if(Integer.parseInt(selectedCats[i]) == -1)
				lCatFour.getSelectionModel().select(catFourLength);
			else
				lCatFour.getSelectionModel().select(Integer.parseInt(selectedCats[i])); 
		
		lCatOne.disableProperty().bind(cheCatOne.selectedProperty().not());
		lCatTwo.disableProperty().bind(cheCatTwo.selectedProperty().not());
		lCatThree.disableProperty().bind(cheCatThree.selectedProperty().not());
		lCatFour.disableProperty().bind(cheCatFour.selectedProperty().not());
	}
	
    public void handleBackButtonClick() throws IOException{
        System.out.println("Back button clicked.");
        
        //go back to the previous scene, if it's a study view and not the stats view, reset to the first card
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
    }
    
    public void handleConfirmButtoneClick() throws IOException{
        System.out.println("Confirm button clicked.");
        ObservableList<Integer> selectedCats;
        String selectedCatsString;
        //update the shuffle parameters based on check box selections
        GlobalStatistics.setShuffle(cheShuffle.isSelected());
        GlobalStatistics.setOnlyFlag(cheFlagged.isSelected());
        GlobalStatistics.setType(cheType.isSelected());
        GlobalStatistics.setIsSelectedCat(1, cheCatOne.isSelected());
        GlobalStatistics.setIsSelectedCat(2, cheCatTwo.isSelected());
        GlobalStatistics.setIsSelectedCat(3, cheCatThree.isSelected());
        GlobalStatistics.setIsSelectedCat(4, cheCatFour.isSelected());
        GlobalStatistics.setStartSide(choStartSide.getSelectionModel().getSelectedIndex() == 0);
        
        //set each category
        if(cheCatOne.isSelected()) {
        	selectedCats = lCatOne.getSelectionModel().getSelectedIndices();
        	selectedCatsString = "";
        	for(Object o: selectedCats) {
        		if((int)o >= catOneLength)
        			selectedCatsString = selectedCatsString + "-1";
        		else
        			selectedCatsString = selectedCatsString + o + ",";
        	}
        	System.out.println(selectedCatsString);
        	GlobalStatistics.setSelectedCat(1, selectedCatsString);
        }
        
        if(cheCatTwo.isSelected()) {
        	selectedCats = lCatTwo.getSelectionModel().getSelectedIndices();
        	selectedCatsString = "";
        	for(Object o: selectedCats) {
        		if((int)o >= catTwoLength)
        			selectedCatsString = selectedCatsString + "-1";
        		else
        			selectedCatsString = selectedCatsString + o + ",";
        	}
        	
        	GlobalStatistics.setSelectedCat(2, selectedCatsString);
        }
        
        if(cheCatThree.isSelected()) {
        	selectedCats = lCatThree.getSelectionModel().getSelectedIndices();
        	selectedCatsString = "";
        	for(Object o: selectedCats) {
        		if((int)o >= catThreeLength)
        			selectedCatsString = selectedCatsString + "-1";
        		else
        			selectedCatsString = selectedCatsString + o + ",";
        	}
        	
        	GlobalStatistics.setSelectedCat(3, selectedCatsString);
        }
        
        if(cheCatFour.isSelected()) {
        	selectedCats = lCatFour.getSelectionModel().getSelectedIndices();
        	selectedCatsString = "";
        	for(Object o: selectedCats) {
        		if((int)o >= catFourLength)
        			selectedCatsString = selectedCatsString + "-1";
        		else
        			selectedCatsString = selectedCatsString + o + ",";
        	}
        	
        	GlobalStatistics.setSelectedCat(4, selectedCatsString);
        }
        
      //go back to the previous scene, if it's a study view and not the stats view, reset to the first card
        if(GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/StudyView.fxml")) {
        	if(GlobalStatistics.getTempDeck() != null) {
        		GlobalStatistics.setDeck(GlobalStatistics.getTempDeck());
        		GlobalStatistics.removeTempDeck();
        	}
        	GlobalStatistics.initializeDeck();
        }
        
        //go back to the previous scene
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
    }
    
    public void handleConfirmGlobalButtoneClick() throws IOException{
        System.out.println("Confirm global button clicked.");
        String selections = "";
        //print the four selections to the string
        if(choStartSide.getSelectionModel().getSelectedIndex() == 0)
        	selections += "t\n";
        else
        	selections += "f\n";
        
        if(cheShuffle.isSelected())
        	selections += "t\n";
        else
        	selections += "f\n";
        
        if(cheFlagged.isSelected())
        	selections += "t\n";
        else
        	selections += "f\n";
        
        if(cheType.isSelected())
        	selections += "t";
        else
        	selections += "f";
        
        //make a settings file if one doesn't already exist
        File settings = new File("settings.ini");
		if(settings.createNewFile())
			System.out.println("Settings file created.");
		else
			System.out.println("Settings file exists.");
		
		//save the four global default values
		FileWriter writer = new FileWriter(settings);
		writer.write(selections);
		writer.close();
		
		//handle it like it's a normal confirm button click
		handleConfirmButtoneClick();
    }
}
