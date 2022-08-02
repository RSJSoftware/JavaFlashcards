package samuelstrobel.flashcards.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import samuelstrobel.flashcards.utilities.GlobalStatistics;
import samuelstrobel.flashcards.utilities.SaveLoad;

public class NewCategoryController implements Initializable {
	public Label lTitle;
	public Button bConfirm;
	public Button bCancel;
	public Button bDeCatOne;
	public Button bDeCatTwo;
	public Button bDeCatThree;
	public Button bDeCatFour;
	public Button bRenameOne;
	public Button bRenameTwo;
	public Button bRenameThree;
	public Button bRenameFour;
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
	public void initialize(URL location, ResourceBundle resources) {
		if(GlobalStatistics.getTempDeck() != null)
			lTitle.setText("Categories for " + GlobalStatistics.getTempDeck().getName());
		else
			lTitle.setText("Categories for " + GlobalStatistics.getDeck().getName());
		
		//initialize the lists
		initializeCats();
		
		//allow multiple items to be selected on this lists
		lCatOne.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lCatTwo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lCatThree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lCatFour.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//make the delete, deselect, and rename buttons unselectable if nothing is selected
		bConfirm.disableProperty().bind(lCatOne.getSelectionModel().selectedItemProperty().isNull().and(lCatTwo.getSelectionModel().selectedItemProperty().isNull()).and(lCatThree.getSelectionModel().selectedItemProperty().isNull()).and(lCatFour.getSelectionModel().selectedItemProperty().isNull()));
		bDeCatOne.disableProperty().bind(lCatOne.getSelectionModel().selectedItemProperty().isNull());
		bRenameOne.disableProperty().bind(lCatOne.getSelectionModel().selectedItemProperty().isNull());
		bDeCatTwo.disableProperty().bind(lCatTwo.getSelectionModel().selectedItemProperty().isNull());
		bRenameTwo.disableProperty().bind(lCatTwo.getSelectionModel().selectedItemProperty().isNull());
		bDeCatThree.disableProperty().bind(lCatThree.getSelectionModel().selectedItemProperty().isNull());
		bRenameThree.disableProperty().bind(lCatThree.getSelectionModel().selectedItemProperty().isNull());
		bDeCatFour.disableProperty().bind(lCatFour.getSelectionModel().selectedItemProperty().isNull());
		bRenameFour.disableProperty().bind(lCatFour.getSelectionModel().selectedItemProperty().isNull());
	}
	
	public void handleConfButtonClick() throws IOException {
		System.out.println("Confirm button pressed!");
        ObservableList<Integer> selectedCats;
	
        Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("WARNING");
		alert.setContentText("Do you really want to delete the selected categories?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    System.out.println("replacing deck");
			//deselect the 'only categories' if that's selected and delete the selected categories
			if(lCatOne.getSelectionModel().getSelectedIndices() != null) {
				System.out.println("Deleting from cat 1");
				if(GlobalStatistics.getIsSelectedCat(1))
					GlobalStatistics.setIsSelectedCat(1, false); 
				GlobalStatistics.setSelectedCat(1, "-1");
				
				selectedCats = lCatOne.getSelectionModel().getSelectedIndices();
				//delete them from greatest to lowest
				for(int i = selectedCats.size() - 1; i >= 0; i--) {
					if(GlobalStatistics.getTempDeck() != null)
						GlobalStatistics.getTempDeck().removeCat(1, selectedCats.get(i));
					else
						GlobalStatistics.getDeck().removeCat(1, selectedCats.get(i));
				}
			}
			
			if(lCatTwo.getSelectionModel().getSelectedIndices() != null) {
				if(GlobalStatistics.getIsSelectedCat(2))
					GlobalStatistics.setIsSelectedCat(2, false); 
				GlobalStatistics.setSelectedCat(2, "-1");
				
				selectedCats = lCatTwo.getSelectionModel().getSelectedIndices();
				//delete them from greatest to lowest
				for(int i = selectedCats.size() - 1; i >= 0; i--) {
					if(GlobalStatistics.getTempDeck() != null)
						GlobalStatistics.getTempDeck().removeCat(2, selectedCats.get(i));
					else
						GlobalStatistics.getDeck().removeCat(2, selectedCats.get(i));
				}
			}
			
			if(lCatThree.getSelectionModel().getSelectedIndices() != null) {
				if(GlobalStatistics.getIsSelectedCat(3))
					GlobalStatistics.setIsSelectedCat(3, false); 
				GlobalStatistics.setSelectedCat(3, "-1");
				
				selectedCats = lCatThree.getSelectionModel().getSelectedIndices();
				//delete them from greatest to lowest
				for(int i = selectedCats.size() - 1; i >= 0; i--) {
					if(GlobalStatistics.getTempDeck() != null)
						GlobalStatistics.getTempDeck().removeCat(3, selectedCats.get(i));
					else
						GlobalStatistics.getDeck().removeCat(3, selectedCats.get(i));
				}
			}
			
			if(lCatFour.getSelectionModel().getSelectedIndices() != null) {
				if(GlobalStatistics.getIsSelectedCat(4))
					GlobalStatistics.setIsSelectedCat(4, false); 
				GlobalStatistics.setSelectedCat(4, "-1");
				
				selectedCats = lCatFour.getSelectionModel().getSelectedIndices();
				//delete them from greatest to lowest
				for(int i = selectedCats.size() - 1; i >= 0; i--) {
					if(GlobalStatistics.getTempDeck() != null)
						GlobalStatistics.getTempDeck().removeCat(4, selectedCats.get(i));
					else
						GlobalStatistics.getDeck().removeCat(4, selectedCats.get(i));
				}
			}
			
			//reinitialize and save the deck
			GlobalStatistics.initializeDeck();
			if(GlobalStatistics.getTempDeck() != null)
				SaveLoad.save(GlobalStatistics.getTempDeck());
			else
				SaveLoad.save(GlobalStatistics.getDeck());
			
			//reinitialize the lists
			initializeCats();
		} else {
			System.out.println("nevermind...");
		}
	}
	
	public void handleCancelButtonClick() throws IOException {
		System.out.println("Cancel button pressed!");
		
        //change the scene to the previous scene
        bCancel.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
	}

	public void handleDeSelOneButtonClick() {
		//deselect everything
		lCatOne.getSelectionModel().clearSelection();
	}
	
	public void handleDeSelTwoButtonClick() {
		//deselect everything
		lCatTwo.getSelectionModel().clearSelection();
	}
	
	public void handleDeSelThreeButtonClick() {
		//deselect everything
		lCatThree.getSelectionModel().clearSelection();
	}
	
	public void handleDeSelFourButtonClick() {
		//deselect everything
		lCatFour.getSelectionModel().clearSelection();
	}
	
	public void handleRenameOneButtonClick() throws IOException {
		//make sure there's only one category selected
		ObservableList<Integer> selectedCats = lCatOne.getSelectionModel().getSelectedIndices();
		
		Alert alertOne = new Alert(AlertType.ERROR);
		alertOne.setTitle("Multiple Selections Alert");
		alertOne.setHeaderText("Multiple Selections");
		alertOne.setContentText("Please select a single category to rename.");
		
		//if there are more than one selected, give the user an error
		if(selectedCats.size() != 1) {
			alertOne.showAndWait();
		} else {
			//give the user a dialog to enter a new name for the category
			TextInputDialog newCatDialog;
			if(GlobalStatistics.getTempDeck() != null)
				newCatDialog = new TextInputDialog(GlobalStatistics.getTempDeck().getCategory(1, lCatOne.getSelectionModel().getSelectedIndex()));
			else
				newCatDialog = new TextInputDialog(GlobalStatistics.getDeck().getCategory(1, lCatOne.getSelectionModel().getSelectedIndex()));
			newCatDialog.setTitle("Category Edit Dialog");
			newCatDialog.setHeaderText("Category " + 1);
			newCatDialog.setContentText("Please enter a category: ");
			
			//make an error dialog just in case the user enters a duplicate category
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Duplicate Alert");
			alert.setHeaderText("Duplicate Category");
			alert.setContentText("A category by this name already exists!");
			
			//if the user enters something, rename the category and update the list
			Optional<String> result = newCatDialog.showAndWait();
	        if(result.isPresent()) {
	        	boolean isDup = false;
				//rename the selected category as long as it's not a duplicate, otherwise cancel, and warn the user
		        if(GlobalStatistics.getTempDeck() == null) {
		        	for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(1); i++) {
		        		if(GlobalStatistics.getDeck().getCategory(1, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getDeck().setCategory(1, lCatOne.getSelectionModel().getSelectedIndex(), result.get());
		        }
		        else {
		        	for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(1); i++) {
		        		if(GlobalStatistics.getTempDeck().getCategory(1, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getTempDeck().setCategory(1, lCatOne.getSelectionModel().getSelectedIndex(), result.get());
		        }
		
				//save with the new category
		        if(GlobalStatistics.getTempDeck() == null)
		        	SaveLoad.save(GlobalStatistics.getDeck());
		        else
		        	SaveLoad.save(GlobalStatistics.getTempDeck());
	
		        //reload the lists
		        initializeCats();
	        }
		}
	}
	
	public void handleRenameTwoButtonClick() throws IOException {
		//make sure there's only one category selected
		ObservableList<Integer> selectedCats = lCatTwo.getSelectionModel().getSelectedIndices();
		
		Alert alertOne = new Alert(AlertType.ERROR);
		alertOne.setTitle("Multiple Selections Alert");
		alertOne.setHeaderText("Multiple Selections");
		alertOne.setContentText("Please select a single category to rename.");
		
		//if there are more than one selected, give the user an error
		if(selectedCats.size() != 1) {
			alertOne.showAndWait();
		} else {
			//give the user a dialog to enter a new name for the category
			TextInputDialog newCatDialog;
			if(GlobalStatistics.getTempDeck() != null)
				newCatDialog = new TextInputDialog(GlobalStatistics.getTempDeck().getCategory(2, lCatTwo.getSelectionModel().getSelectedIndex()));
			else
				newCatDialog = new TextInputDialog(GlobalStatistics.getDeck().getCategory(2, lCatTwo.getSelectionModel().getSelectedIndex()));
			newCatDialog.setTitle("Category Edit Dialog");
			newCatDialog.setHeaderText("Category " + 2);
			newCatDialog.setContentText("Please enter a category: ");
			
			//make an error dialog just in case the user enters a duplicate category
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Duplicate Alert");
			alert.setHeaderText("Duplicate Category");
			alert.setContentText("A category by this name already exists!");
			
			//if the user enters something, rename the category and update the list
			Optional<String> result = newCatDialog.showAndWait();
	        if(result.isPresent()) {
	        	boolean isDup = false;
				//rename the selected category as long as it's not a duplicate, otherwise cancel, and warn the user
		        if(GlobalStatistics.getTempDeck() == null) {
		        	for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(2); i++) {
		        		if(GlobalStatistics.getDeck().getCategory(2, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getDeck().setCategory(2, lCatTwo.getSelectionModel().getSelectedIndex(), result.get());
		        }
		        else {
		        	for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(2); i++) {
		        		if(GlobalStatistics.getTempDeck().getCategory(2, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getTempDeck().setCategory(2, lCatTwo.getSelectionModel().getSelectedIndex(), result.get());
		        }
		
				//save with the new category
		        if(GlobalStatistics.getTempDeck() == null)
		        	SaveLoad.save(GlobalStatistics.getDeck());
		        else
		        	SaveLoad.save(GlobalStatistics.getTempDeck());
	
		        //reload the lists
		        initializeCats();
	        }
		}
	}
	
	public void handleRenameThreeButtonClick() throws IOException {
		//make sure there's only one category selected
		ObservableList<Integer> selectedCats = lCatThree.getSelectionModel().getSelectedIndices();
		
		Alert alertOne = new Alert(AlertType.ERROR);
		alertOne.setTitle("Multiple Selections Alert");
		alertOne.setHeaderText("Multiple Selections");
		alertOne.setContentText("Please select a single category to rename.");
		
		//if there are more than one selected, give the user an error
		if(selectedCats.size() != 1) {
			alertOne.showAndWait();
		} else {
			//give the user a dialog to enter a new name for the category
			TextInputDialog newCatDialog;
			if(GlobalStatistics.getTempDeck() != null)
				newCatDialog = new TextInputDialog(GlobalStatistics.getTempDeck().getCategory(3, lCatThree.getSelectionModel().getSelectedIndex()));
			else
				newCatDialog = new TextInputDialog(GlobalStatistics.getDeck().getCategory(3, lCatThree.getSelectionModel().getSelectedIndex()));
			newCatDialog.setTitle("Category Edit Dialog");
			newCatDialog.setHeaderText("Category " + 3);
			newCatDialog.setContentText("Please enter a category: ");
			
			//make an error dialog just in case the user enters a duplicate category
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Duplicate Alert");
			alert.setHeaderText("Duplicate Category");
			alert.setContentText("A category by this name already exists!");
			
			//if the user enters something, rename the category and update the list
			Optional<String> result = newCatDialog.showAndWait();
	        if(result.isPresent()) {
	        	boolean isDup = false;
				//rename the selected category as long as it's not a duplicate, otherwise cancel, and warn the user
		        if(GlobalStatistics.getTempDeck() == null) {
		        	for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(3); i++) {
		        		if(GlobalStatistics.getDeck().getCategory(3, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getDeck().setCategory(3, lCatThree.getSelectionModel().getSelectedIndex(), result.get());
		        }
		        else {
		        	for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(3); i++) {
		        		if(GlobalStatistics.getTempDeck().getCategory(3, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getTempDeck().setCategory(3, lCatThree.getSelectionModel().getSelectedIndex(), result.get());
		        }
		
				//save with the new category
		        if(GlobalStatistics.getTempDeck() == null)
		        	SaveLoad.save(GlobalStatistics.getDeck());
		        else
		        	SaveLoad.save(GlobalStatistics.getTempDeck());
	
		        //reload the lists
		        initializeCats();
	        }
		}
	}
	
	public void handleRenameFourButtonClick() throws IOException {
		//make sure there's only one category selected
		ObservableList<Integer> selectedCats = lCatFour.getSelectionModel().getSelectedIndices();
		
		Alert alertOne = new Alert(AlertType.ERROR);
		alertOne.setTitle("Multiple Selections Alert");
		alertOne.setHeaderText("Multiple Selections");
		alertOne.setContentText("Please select a single category to rename.");
		
		//if there are more than one selected, give the user an error
		if(selectedCats.size() != 1) {
			alertOne.showAndWait();
		} else {
			//give the user a dialog to enter a new name for the category
			TextInputDialog newCatDialog;
			if(GlobalStatistics.getTempDeck() != null)
				newCatDialog = new TextInputDialog(GlobalStatistics.getTempDeck().getCategory(4, lCatFour.getSelectionModel().getSelectedIndex()));
			else
				newCatDialog = new TextInputDialog(GlobalStatistics.getDeck().getCategory(4, lCatFour.getSelectionModel().getSelectedIndex()));
			newCatDialog.setTitle("Category Edit Dialog");
			newCatDialog.setHeaderText("Category " + 4);
			newCatDialog.setContentText("Please enter a category: ");
			
			//make an error dialog just in case the user enters a duplicate category
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Duplicate Alert");
			alert.setHeaderText("Duplicate Category");
			alert.setContentText("A category by this name already exists!");
			
			//if the user enters something, rename the category and update the list
			Optional<String> result = newCatDialog.showAndWait();
	        if(result.isPresent()) {
	        	boolean isDup = false;
				//rename the selected category as long as it's not a duplicate, otherwise cancel, and warn the user
		        if(GlobalStatistics.getTempDeck() == null) {
		        	for(int i = 0; i < GlobalStatistics.getDeck().getCatLength(4); i++) {
		        		if(GlobalStatistics.getDeck().getCategory(4, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getDeck().setCategory(4, lCatFour.getSelectionModel().getSelectedIndex(), result.get());
		        }
		        else {
		        	for(int i = 0; i < GlobalStatistics.getTempDeck().getCatLength(4); i++) {
		        		if(GlobalStatistics.getTempDeck().getCategory(4, i).equals(result.get())) {
		        			alert.showAndWait();
		        			isDup = true;
		        		}
		        	}//end loop
		        	//if the category is not a duplicate, rename it
		        	if(!isDup)
	        			GlobalStatistics.getTempDeck().setCategory(4, lCatFour.getSelectionModel().getSelectedIndex(), result.get());
		        }
		
				//save with the new category
		        if(GlobalStatistics.getTempDeck() == null)
		        	SaveLoad.save(GlobalStatistics.getDeck());
		        else
		        	SaveLoad.save(GlobalStatistics.getTempDeck());
	
		        //reload the lists
		        initializeCats();
	        }
		}
	}
	
	private void initializeCats() {
		//set all lists
		catOne.clear();
		catTwo.clear();
		catThree.clear();
		catFour.clear();
		lCatOne.getItems().clear();
		lCatTwo.getItems().clear();
		lCatThree.getItems().clear();
		lCatFour.getItems().clear();
		
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
		
			//disable the list and buttons if there's no categories
			if(catOneLength <= 0) {
				catOne.add("<No Categories>");
				lCatOne.setDisable(true);
			}
			if(catTwoLength <= 0) {
				catTwo.add("<No Categories>");
				lCatTwo.setDisable(true);
			}
			if(catThreeLength <= 0) {
				catThree.add("<No Categories>");
				lCatThree.setDisable(true);
			}
			if(catFourLength <= 0) {
				catFour.add("<No Categories>");
				lCatFour.setDisable(true);
			}
			
			//add all options to the lists
	        lCatOne.getItems().addAll(catOne);
	        lCatTwo.getItems().addAll(catTwo);
	        lCatThree.getItems().addAll(catThree);
	        lCatFour.getItems().addAll(catFour);
		}
	}
}
