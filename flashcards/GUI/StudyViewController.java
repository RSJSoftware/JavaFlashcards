package samuelstrobel.flashcards.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import samuelstrobel.flashcards.utilities.GlobalStatistics;
import samuelstrobel.flashcards.utilities.SaveLoad;

public class StudyViewController implements Initializable {
	public Label lStats;
	public Label lDeckName;
	public Label lCurrentCard;
	public Button bBack;
	public Button bOptions;
	public Button bPrev;
	public Button bCard;
    public Button bCardView;
	public Button bCorrect;
	public Button bIncorrect;
	private int currentSide = 0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		//set the card text to that of the first card, swap sides one and two if that option is marked, if the deck is empty disable button and inform user
		if(GlobalStatistics.getDeck().getDeckLength() <= 0) {
			if(GlobalStatistics.getTempDeck() != null && GlobalStatistics.getTempDeck().getDeckLength() > 0) {
				if(GlobalStatistics.getOnlyFlag())
					bCard.setText("There are no flagged cards!\nPress the 'options' button to undo this selection.");
				else if(GlobalStatistics.getIsSelectedCat(1))
					bCard.setText("There are no cards under the selected category one!\nPress the 'options' button to undo this selection.");
				else if(GlobalStatistics.getIsSelectedCat(2))
					bCard.setText("There are no cards under the selected category two!\nPress the 'options' button to undo this selection.");
				else if(GlobalStatistics.getIsSelectedCat(3))
					bCard.setText("There are no cards under the selected category three!\nPress the 'options' button to undo this selection.");
				else if(GlobalStatistics.getIsSelectedCat(4))
					bCard.setText("There are no cards under the selected category four!\nPress the 'options' button to undo this selection.");
			} else {
				bCard.setText("The deck is empty!\nPress the 'cards' button to make a new card.");
			}
			bCard.setDisable(true);
			bCorrect.setDisable(true);
			bIncorrect.setDisable(true);
			bPrev.setDisable(true);
		} else {
			if(!GlobalStatistics.getStartSide() && !GlobalStatistics.getCard().getSide(currentSide + 1).equals("")) {
				bCard.setText(GlobalStatistics.getCard().getSide(currentSide + 1));
			} else {
				bCard.setText(GlobalStatistics.getCard().getSide(currentSide));
			}
		}

		//disable the previous button if this is the first card in the deck
		if(GlobalStatistics.getCardIndex() == 0)
			bPrev.setDisable(true);
		
		//set the deck name on the top of the window
    	if(GlobalStatistics.getTempDeck() == null)
    		lDeckName.setText(GlobalStatistics.getDeck().getName());
    	else
    		lDeckName.setText(GlobalStatistics.getTempDeck().getName());

    	//set the current card label to the number of the current card
    	lCurrentCard.setText("Card: "  + (GlobalStatistics.getCardIndex() + 1) + "/" + GlobalStatistics.getDeck().getDeckLength());
    	
    	//show the correct stats to the user, make sure to not divide by 0
        if(GlobalStatistics.getCardIndex() == 0)
            lStats.setText("0/0  --%");
        else
            lStats.setText(GlobalStatistics.getCorrectStats() + "/" + GlobalStatistics.getCardIndex() + "  " + (int)(((double)GlobalStatistics.getCorrectStats()/GlobalStatistics.getCardIndex())*100) + "%");
	}
	
	public void handleOptionsButtonClick() throws IOException {
        //change the scene to the create a deck scene
        GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyView.fxml");
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyOptions.fxml")));
	}
	
	public void handleCardButtonClick() {
		//give the user a box to type in if they have type in the answer and it's on the correct side
		if(GlobalStatistics.getType()) {
			TextInputDialog newCatDialog = new TextInputDialog();
			newCatDialog.setTitle("Category Input Dialog");
			newCatDialog.setHeaderText("");
			newCatDialog.setContentText("Please enter a category: ");
			
			//check what the user enters and compare it to what the actual answer is. This should only be done on side one or two depending on what is set to show up first
			if(GlobalStatistics.getStartSide()) {
				if(currentSide == 0 && !GlobalStatistics.getCard().getSide(1).equals("")) {
					Optional<String> result = newCatDialog.showAndWait();
			        if(result.isPresent()) {
			        	//let the user know if they were correct or incorrect by changing the text color
			        	if(result.get().toLowerCase().trim().equals(GlobalStatistics.getCard().getSide(1).toLowerCase().trim())) {
			        		bCard.setStyle("-fx-text-fill: blue");
			        	} else {
			        		bCard.setStyle("-fx-text-fill: red");
			        	}
			        }
				} else {
					//change the text color back
					bCard.setStyle("-fx-text-fill: black");
				}
			} else {
				if(currentSide == 0 && !GlobalStatistics.getCard().getSide(0).equals("")) {
					Optional<String> result = newCatDialog.showAndWait();
			        if(result.isPresent()) {
			        	//let the user know if they were correct or incorrect by changing the text color
			        	if(result.get().toLowerCase().trim().equals(GlobalStatistics.getCard().getSide(0).toLowerCase().trim())) {
			        		bCard.setStyle("-fx-text-fill: blue");
			        	} else {
			        		bCard.setStyle("-fx-text-fill: red");
			        	}
			        }
				} else {
					//change the text color back
					bCard.setStyle("-fx-text-fill: black");
				}
			}
		}
		
		//set card to the next side
		if(!"".equals(GlobalStatistics.getCard().getSide(currentSide + 1)) && currentSide != 4)
			currentSide += 1;
		else
			currentSide = 0;
		
		//swap sides one and two if that option is marked
		if(!GlobalStatistics.getStartSide() && !GlobalStatistics.getCard().getSide(currentSide + 1).equals("")) {
			if(currentSide == 0)
				bCard.setText(GlobalStatistics.getCard().getSide(currentSide + 1));
			else if(currentSide == 1)
				bCard.setText(GlobalStatistics.getCard().getSide(currentSide - 1));
			else
				bCard.setText(GlobalStatistics.getCard().getSide(currentSide));
		} else 
			bCard.setText(GlobalStatistics.getCard().getSide(currentSide));
	}
	
	public void handleIncorrectButtonClick() throws IOException {
		GlobalStatistics.getCard().setCorrect(false);
		
		//if it's the end of the deck inform the user and display stats
		if(GlobalStatistics.getDeck().getDeckLength() <= (GlobalStatistics.getCardIndex() + 1)){
			System.out.println("End of deck for now!");
                        bCorrect.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyStatsView.fxml")));
		} else {
			//update the card and reload the page with the new card
			GlobalStatistics.setCard(GlobalStatistics.getDeck().getCard(GlobalStatistics.getCardIndex() + 1));
			GlobalStatistics.incCardIndex();
			bIncorrect.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
		}
	}

	public void handleCorrectButtonClick() throws IOException {
		GlobalStatistics.incCorrectStats();
		GlobalStatistics.getCard().setCorrect(true);
		System.out.println(GlobalStatistics.getCorrectStats());
                
		//if it's the end of the deck inform the user and display stats
		if(GlobalStatistics.getDeck().getDeckLength() <= (GlobalStatistics.getCardIndex() + 1)){
			System.out.println("End of deck for now!");
                        bCorrect.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyStatsView.fxml")));
		} else {
			//update the card and reload the page with the new card
			GlobalStatistics.setCard(GlobalStatistics.getDeck().getCard(GlobalStatistics.getCardIndex() + 1));
			GlobalStatistics.incCardIndex();
			bIncorrect.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
		}
	}

	public void handlePrevButtonClick() throws IOException {
		//if it's the end of the deck inform the user and display stats
		if(GlobalStatistics.getCardIndex() == 0){
			System.out.println("This is the beginning of the deck!");
		} else {
			//update the card, stats, and reload the page with the new card
			GlobalStatistics.setCard(GlobalStatistics.getDeck().getCard(GlobalStatistics.getCardIndex() - 1));
			GlobalStatistics.decCardIndex();
			if(GlobalStatistics.getCard().getCorrect())
				GlobalStatistics.decCorrectStats();
			bIncorrect.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
		}
	}

	public void handleBackButtonClick() throws IOException {
		//debug statement
		System.out.println("Back button clicked.");
		
		//save the state of the deck
		if(GlobalStatistics.getTempDeck() == null)
			SaveLoad.saveState(GlobalStatistics.getDeck().getName());
    	else
    		SaveLoad.saveState(GlobalStatistics.getTempDeck().getName());

		//change the scene to the create a deck scene
		bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
	}
        
    public void handleCardViewButtonClick() throws IOException {
            //debug statement
            System.out.println("Card view button clicked.");

            GlobalStatistics.setTempCard(GlobalStatistics.getCard());
            GlobalStatistics.setTempCardIndex(GlobalStatistics.getCardIndex());

            //change the scene to the cards view scene
        GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyView.fxml");
            bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml")));
	}
        
    public void handleTitleClick() throws IOException{
    	//debug statement
    	System.out.println(lDeckName.getText() + " label clicked"); 
    	
        //change the scene to the create a deck scene
    	GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyView.fxml");
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewDeck.fxml")));
    }
}
