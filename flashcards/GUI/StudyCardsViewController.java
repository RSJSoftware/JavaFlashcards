package samuelstrobel.flashcards.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import samuelstrobel.flashcards.utilities.Cards;
import samuelstrobel.flashcards.utilities.GlobalStatistics;

public class StudyCardsViewController implements Initializable {
    public Button bBack;
    public Button bNewCard;
    public Button bEditCard;
    public Label lDeckName;
	public CheckBox cheFlag;
	public CheckBox cheUnFlag;
	public CheckBox cheEdit;
    public ChoiceBox cbSort;
	public ChoiceBox<String> choCatOne;
	public ChoiceBox<String> choCatTwo;
	public ChoiceBox<String> choCatThree;
	public ChoiceBox<String> choCatFour;
    public ListView<String> listCards;
    
    private ObservableList<String> cards = FXCollections.observableArrayList();
	private ChangeListener<Number> catOneListen;
	private ChangeListener<Number> catTwoListen;
	private ChangeListener<Number> catThreeListen;
	private ChangeListener<Number> catFourListen;
	private ChangeListener<Number> listListen;
	private ChangeListener<Boolean> flaggedListen; 
	private ChangeListener<Boolean> unflaggedListen; 
	private int catOneLengthFinal;
	private int catTwoLengthFinal;
	private int catThreeLengthFinal;
	private int catFourLengthFinal;
    
	@Override
    public void initialize(URL url, ResourceBundle rb) {
        // create a new deck with all the same cards in alphabetical order
        GlobalStatistics.setDeckView(GlobalStatistics.createDeckView());

    	if(GlobalStatistics.getTempDeck() == null)
    		lDeckName.setText(GlobalStatistics.getDeck().getName());
    	else
    		lDeckName.setText(GlobalStatistics.getTempDeck().getName());
        
        //initialize choice box
        for(int i = 0; i < 5; i++)
            cbSort.getItems().add(i+1);
        
        cbSort.getSelectionModel().selectFirst();

        //initialize categories
        initializeNormal();
        initializeCats();
        
        //make sure there are cards
        resetCardList();
        
        //change the font of the listview to a monospace font for readability
        listCards.setStyle("-fx-font: 8pt 'Lucida Console' ;");
        
        //add listener to remove all listeners and go into edit mode
	    cheEdit.selectedProperty().addListener((v, oldValue, newValue) -> {choCatOne.getSelectionModel().selectedIndexProperty().removeListener(catOneListen); 
	        choCatTwo.getSelectionModel().selectedIndexProperty().removeListener(catTwoListen); 
	        choCatThree.getSelectionModel().selectedIndexProperty().removeListener(catThreeListen); 
	        choCatFour.getSelectionModel().selectedIndexProperty().removeListener(catFourListen); 
	        listCards.getSelectionModel().selectedIndexProperty().removeListener(listListen);
	        cheFlag.selectedProperty().removeListener(flaggedListen);
	        cheUnFlag.selectedProperty().removeListener(unflaggedListen);
	        resetCardList();
	        initializeMode();});
        
	    //make sure these two aren't selected at the same time
	    cheFlag.selectedProperty().addListener((v, oldValue, newValue) -> {if(newValue == true && cheUnFlag.isSelected()) cheUnFlag.setSelected(false);});
	    cheUnFlag.selectedProperty().addListener((v, oldValue, newValue) -> {if(newValue == true && cheFlag.isSelected()) cheFlag.setSelected(false);});
	    
        //add listener to sort items
        cbSort.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {System.out.println("Sorting cards by side: " + newValue); GlobalStatistics.setSortedSide((int)newValue); GlobalStatistics.getDeckView().sort((int)newValue); resetCardList();});
    }    
    
    public void handleBackButtonClick() throws IOException{
        //debug statement
        System.out.println("Back button pressed!");
        GlobalStatistics.setSortedSide(0);
        
        //clear deck view and restore the current card
        GlobalStatistics.removeDeckView();
        GlobalStatistics.setCard(GlobalStatistics.getTempCard());
        GlobalStatistics.setCardIndex(GlobalStatistics.getTempCardIndex());
        GlobalStatistics.removeTempCard();
        
        //change the scene to the previous scene
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
    }
    
    public void handleNewCardButtonClick() throws IOException{
        //debug statement
        System.out.println("New card button pressed!");
        
        if(!cheEdit.isSelected()) {
	        //create a new card and set it to the current card
	        if(GlobalStatistics.getTempDeck() == null) {
		        GlobalStatistics.getDeck().addCard();
		        GlobalStatistics.setCard(GlobalStatistics.getDeck().getCard(GlobalStatistics.getDeck().getDeckLength()-1));
		        GlobalStatistics.setCardIndex(GlobalStatistics.getDeck().getDeckLength() - 1);
	        } else {
	        	GlobalStatistics.getTempDeck().addCard();
		        GlobalStatistics.setCard(GlobalStatistics.getTempDeck().getCard(GlobalStatistics.getTempDeck().getDeckLength()-1));
		        GlobalStatistics.setCardIndex(GlobalStatistics.getTempDeck().getDeckLength() - 1);
	        }
	        
	        //go to card creation view
	        GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml");
	        bNewCard.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewCard.fxml")));
        } else {
        	if(!listCards.getSelectionModel().isEmpty()) {
        		//make sure the user actually wants to delete all these cards
        		Alert alert = new Alert(AlertType.CONFIRMATION);
    			alert.setTitle("Confirmation Dialog");
    			alert.setHeaderText("WARNING");
    			alert.setContentText("Are you sure you want to delete all these cards?");
    			Optional<ButtonType> result = alert.showAndWait();
    			if (result.get() == ButtonType.OK){
    				//sort the deck so that the desired cards are actually deleted
    				if(GlobalStatistics.getTempDeck() != null)
                		GlobalStatistics.getTempDeck().sort(GlobalStatistics.getSortedSide());
    				else
                		GlobalStatistics.getDeck().sort(GlobalStatistics.getSortedSide());
    				
    				System.out.println("deleting cards");
    				
    				//get all selected cards and delete them in reverse order
    				ObservableList<Integer> selectedCards = listCards.getSelectionModel().getSelectedIndices();
	            	for(int i = selectedCards.size() - 1; i >= 0; i--) {
	            		if(GlobalStatistics.getTempDeck() != null) {
	                    	GlobalStatistics.getTempDeck().removeCard(selectedCards.get(i));
	            		}else {
	                    	GlobalStatistics.getDeck().removeCard(selectedCards.get(i));
	            		}
	            	}
	            	
	            	//initialize the deck again
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

	                // reset the deck view and reset the cards
	                GlobalStatistics.setDeckView(GlobalStatistics.createDeckView());
	                resetCardList();
    			} else {
    				System.out.println("Nevermind...");
    			}
        	}
        }
    }
    
    public void handleEditCardButtonClick() throws IOException{
        //debug statement
        System.out.println("Edit card button pressed!");
        
        if(!cheEdit.isSelected()) {
	        //if the cards are not all shown, find the card
	        if(!choCatOne.getSelectionModel().isEmpty() || !choCatTwo.getSelectionModel().isEmpty() || !choCatThree.getSelectionModel().isEmpty() || !choCatFour.getSelectionModel().isEmpty() || cheFlag.isSelected()) {
	        	GlobalStatistics.setCardIndex(findCard());
	        	GlobalStatistics.setCard(GlobalStatistics.getDeckView().getCard(GlobalStatistics.getCardIndex()));
	        }
	        
	        //go to card creation view
	        GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml");
	        bNewCard.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewCard.fxml")));
        } else {
        	if(!listCards.getSelectionModel().isEmpty()) {
        		ObservableList<Integer> selectedCards = listCards.getSelectionModel().getSelectedIndices();
        		
        		//set the category to all the cards if one is selected
        		if(!choCatOne.getSelectionModel().isEmpty()) {
	            	for(Object o: selectedCards) {
	            		if(choCatOne.getSelectionModel().getSelectedIndex() == catOneLengthFinal - 1)
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(1, -1);
	            		else
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(1, choCatOne.getSelectionModel().getSelectedIndex());
	            	}
        		}

        		if(!choCatTwo.getSelectionModel().isEmpty()) {
	            	for(Object o: selectedCards) {
	            		if(choCatTwo.getSelectionModel().getSelectedIndex() == catTwoLengthFinal - 1)
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(2, -1);
	            		else
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(2, choCatTwo.getSelectionModel().getSelectedIndex());
	            	}
        		}
        		
        		if(!choCatThree.getSelectionModel().isEmpty()) {
	            	for(Object o: selectedCards) {
	            		if(choCatThree.getSelectionModel().getSelectedIndex() == catThreeLengthFinal - 1)
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(3, -1);
	            		else
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(3, choCatThree.getSelectionModel().getSelectedIndex());
	            	}
        		}
        		
        		if(!choCatFour.getSelectionModel().isEmpty()) {
	            	for(Object o: selectedCards) {
	            		if(choCatFour.getSelectionModel().getSelectedIndex() == catFourLengthFinal - 1)
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(4, -1);
	            		else
	            			GlobalStatistics.getDeckView().getCard((int)o).setCategory(4, choCatFour.getSelectionModel().getSelectedIndex());
	            	}
        		}
        		
        		//warn the user before flagging cards in bulk
        		if(cheFlag.isSelected()) {
	            	for(Object o: selectedCards) {
    	            	GlobalStatistics.getDeckView().getCard((int)o).setFlag(true);
	            	}
    	        } else if (cheUnFlag.isSelected()) {
    	        	for(Object o: selectedCards) {
    	            	GlobalStatistics.getDeckView().getCard((int)o).setFlag(false);
	            	}
    	        }
        	}
        }
    }
    
    public void handleTitleClick() throws IOException{
    	//debug statement
    	System.out.println(lDeckName.getText() + " label clicked"); 
    	
        //change the scene to the create a deck scene
    	GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml");
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewDeck.fxml")));
    }
    
    public void resetCardList() {
    	listCards.getItems().clear();
    	cards.clear();
        //make sure there are cards
        if(GlobalStatistics.getDeckView().getDeckLength() <= 0) {
        	//if there are no cards, tell the user how to create some
    		cards.add("There are no cards! Press 'new card' to create one.");
    		listCards.setDisable(true);
    		bEditCard.setDisable(true);
    		cbSort.setDisable(true);
    	} else {
	        //fill in the list view with the cards if they fit the selections, in edit mode, just add all cards
    		if(!cheEdit.isSelected()) {
		        for(int i = 0; i < GlobalStatistics.getDeckView().getDeckLength(); i++)
		        	if(isCard(GlobalStatistics.getDeckView().getCard(i)))
		        		cards.add(String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(0)) + " | " + 
		        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(1)) + " | " + 
		        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(2)) + " | " +
		        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(3)) + " | " +
		        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(4)));
    		} else {
    			for(int i = 0; i < GlobalStatistics.getDeckView().getDeckLength(); i++)
	        		cards.add(String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(0)) + " | " + 
	        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(1)) + " | " + 
	        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(2)) + " | " +
	        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(3)) + " | " +
	        			String.format("%-15.15s", GlobalStatistics.getDeckView().getCard(i).getSide(4)));
    		}
    	}
        listCards.getItems().addAll(cards);
    }

    private void initializeMode() {
    	//reset the selection of all components
    	choCatOne.getSelectionModel().clearSelection();
    	choCatTwo.getSelectionModel().clearSelection();
    	choCatThree.getSelectionModel().clearSelection();
    	choCatFour.getSelectionModel().clearSelection();
    	cheFlag.setSelected(false);
    	
    	//initialize the scene to the correct listeners
    	if(cheEdit.isSelected())
    		initializeEdit();
    	else
    		initializeNormal();
    }
    
    private void initializeNormal() {
    	bNewCard.setText("New Card");
    	bNewCard.setStyle("-fx-text-fill: black");
    	bEditCard.setText("Edit Card");
    	
		//set a listener on the category choice box that will automatically resort to only show the cards that have one selection
		catOneListen = (v, oldValue, newValue) -> {if((int)newValue == (catOneLengthFinal)) {
			choCatOne.getSelectionModel().clearSelection();
			resetCardList();
		} else {
			resetCardList();
		}};
		choCatOne.getSelectionModel().selectedIndexProperty().addListener(catOneListen);
		
		//repeat for category two
		catTwoListen = (v, oldValue, newValue) -> {if((int)newValue == (catTwoLengthFinal)) {
			choCatTwo.getSelectionModel().clearSelection();
			resetCardList();
		} else {
			resetCardList();
		}};
		choCatTwo.getSelectionModel().selectedIndexProperty().addListener(catTwoListen);
		
		//repeat for category three
		catThreeListen = (v, oldValue, newValue) -> {if((int)newValue == (catThreeLengthFinal)) {
			choCatThree.getSelectionModel().clearSelection();
			resetCardList();
		} else {
			resetCardList();
		}};
		choCatThree.getSelectionModel().selectedIndexProperty().addListener(catThreeListen);
		
		//repeat for category four
		catFourListen = (v, oldValue, newValue) -> {if((int)newValue == (catFourLengthFinal)) {
			choCatFour.getSelectionModel().clearSelection();
			resetCardList();
		} else {
			resetCardList();
		}};
		choCatFour.getSelectionModel().selectedIndexProperty().addListener(catFourListen);
		
		//reset the card list if the flagged button is checked or unchecked
		flaggedListen = (v, oldValue, newValue) -> {resetCardList();};
		cheFlag.selectedProperty().addListener(flaggedListen);
		
		unflaggedListen = (v, oldValue, newValue) -> {resetCardList();};
		cheUnFlag.selectedProperty().addListener(unflaggedListen);
		
		//add a listener to the list that will update the current card and index to the selected one (works only when all cards are visible) 
		listListen = (v, oldValue, newValue) -> {GlobalStatistics.setCardIndex((int)newValue); GlobalStatistics.setCard(GlobalStatistics.getDeckView().getCard(GlobalStatistics.getCardIndex()));};
		listCards.getSelectionModel().selectedIndexProperty().addListener(listListen);
		
		//make sure only one thing is selectable from the card list
		listCards.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
    
    private void initializeEdit() {
    	bNewCard.setText("Delete Selected");
    	bNewCard.setStyle("-fx-text-fill: red");
    	bEditCard.setText("Set Selected");
		
    	//give function to the last option on each choice box
    	catOneListen = (v, oldValue, newValue) -> {if((int)newValue == (catOneLengthFinal)) {
			choCatOne.getSelectionModel().clearSelection();
		}};
		choCatOne.getSelectionModel().selectedIndexProperty().addListener(catOneListen);
		
		//repeat for category two
		catTwoListen = (v, oldValue, newValue) -> {if((int)newValue == (catTwoLengthFinal)) {
			choCatTwo.getSelectionModel().clearSelection();
		}};
		choCatTwo.getSelectionModel().selectedIndexProperty().addListener(catTwoListen);
		
		//repeat for category three
		catThreeListen = (v, oldValue, newValue) -> {if((int)newValue == (catThreeLengthFinal)) {
			choCatThree.getSelectionModel().clearSelection();
		}};
		choCatThree.getSelectionModel().selectedIndexProperty().addListener(catThreeListen);
		
		//repeat for category four
		catFourListen = (v, oldValue, newValue) -> {if((int)newValue == (catFourLengthFinal)) {
			choCatFour.getSelectionModel().clearSelection();
		}};
		choCatFour.getSelectionModel().selectedIndexProperty().addListener(catFourListen);
		
		//make sure only one thing is selectable from the card list
		listCards.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
    
    private void initializeCats() {
    	//start each length out at one, because each choice box will at least have one choice 
    			int catOneLength = 1;
    			int catTwoLength = 1;
    			int catThreeLength = 1;
    			int catFourLength = 1;
    			
    			//initialize choice box selections
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
    			choCatOne.getItems().add("unassigned");
    			choCatOne.getItems().add("no selection");
    			//set the final length for the category
    			catOneLengthFinal = catOneLength;
    			
    			//repeat for category two
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
    			choCatTwo.getItems().add("unassigned");
    			choCatTwo.getItems().add("no selection");
    			catTwoLengthFinal = catTwoLength;
    			
    			//repeat for category three
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
    			choCatThree.getItems().add("unassigned");
    			choCatThree.getItems().add("no selection");
    			catThreeLengthFinal = catThreeLength;
    			
    			//repeat for category four
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
    			choCatFour.getItems().add("unassigned");
    			choCatFour.getItems().add("no selection");
    			catFourLengthFinal = catFourLength;
    }
    
    private boolean isCard(Cards card) {
    	boolean result = true;
    	int check;
    	
    	//if there's a selection on cat one, see if a card fits that
    	if(!choCatOne.getSelectionModel().isEmpty()) {
    		check = choCatOne.getSelectionModel().getSelectedIndex();
    		if(GlobalStatistics.getTempDeck() != null) {
    			if(GlobalStatistics.getTempDeck().getCatLength(1) == check) {
    				if(card.getCategory(1) != -1)
    					result = false;
    			} else if(card.getCategory(1) != check)
					result = false;
    		} else {
    			if(GlobalStatistics.getDeck().getCatLength(1) == check) {
    				if(card.getCategory(1) != -1)
    					result = false;
    			} else if(card.getCategory(1) != check)
					result = false;
    		}
    	}
    	
    	//repeat for cat two
    	if(!choCatTwo.getSelectionModel().isEmpty()) {
    		check = choCatTwo.getSelectionModel().getSelectedIndex();
    		if(GlobalStatistics.getTempDeck() != null) {
    			if(GlobalStatistics.getTempDeck().getCatLength(2) == check) {
    				if(card.getCategory(2) != -1)
    					result = false;
    			} else if(card.getCategory(2) != check)
					result = false;
    		} else {
    			if(GlobalStatistics.getDeck().getCatLength(2) == check) {
    				if(card.getCategory(2) != -1)
    					result = false;
    			} else if(card.getCategory(2) != check)
					result = false;
    		}
    	}
    	
    	//repeat for cat three
    	if(!choCatThree.getSelectionModel().isEmpty()) {
    		check = choCatThree.getSelectionModel().getSelectedIndex();
    		if(GlobalStatistics.getTempDeck() != null) {
    			if(GlobalStatistics.getTempDeck().getCatLength(3) == check) {
    				if(card.getCategory(3) != -1)
    					result = false;
    			} else if(card.getCategory(3) != check)
					result = false;
    		} else {
    			if(GlobalStatistics.getDeck().getCatLength(3) == check) {
    				if(card.getCategory(3) != -1)
    					result = false;
    			} else if(card.getCategory(3) != check)
					result = false;
    		}
    	}
    	
    	//repeat for cat four
    	if(!choCatFour.getSelectionModel().isEmpty()) {
    		check = choCatFour.getSelectionModel().getSelectedIndex();
    		if(GlobalStatistics.getTempDeck() != null) {
    			if(GlobalStatistics.getTempDeck().getCatLength(4) == check) {
    				if(card.getCategory(4) != -1)
    					result = false;
    			} else if(card.getCategory(4) != check)
					result = false;
    		} else {
    			if(GlobalStatistics.getDeck().getCatLength(4) == check) {
    				if(card.getCategory(4) != -1)
    					result = false;
    			} else if(card.getCategory(4) != check)
					result = false;
    		}
    	}
    	
    	if(cheFlag.isSelected() && !card.getFlag())
    		result = false;
    	else if(cheUnFlag.isSelected() && card.getFlag())
    		result = false;
    	
    	
    	return result;
    }

    private int findCard() {
    	int correct = -1;
    	int index = 0;
    	
    	//find the selected card by determining where the selected card would be if the deck were all shown
    	for(int i = 0; correct != listCards.getSelectionModel().getSelectedIndex() && i < GlobalStatistics.getDeckView().getDeckLength(); i++) {
    		if(isCard(GlobalStatistics.getDeckView().getCard(i)))
    			correct++;
    		if(correct == listCards.getSelectionModel().getSelectedIndex())
    			index = i;
    	}

    	return index;
    }
}
