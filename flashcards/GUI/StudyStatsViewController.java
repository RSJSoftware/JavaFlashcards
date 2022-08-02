package samuelstrobel.flashcards.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import samuelstrobel.flashcards.utilities.GlobalStatistics;
import samuelstrobel.flashcards.utilities.SaveLoad;

public class StudyStatsViewController implements Initializable {
    public Button bBack;
    public Button bOptions;
    public Button bReview;
    public Button bCards;
    public Button bRestart;
    public Label lStats;
    public Label lDeckName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	GlobalStatistics.setStudy(false);
    	//set the title name
    	if(GlobalStatistics.getTempDeck() == null)
    		lDeckName.setText(GlobalStatistics.getDeck().getName());
    	else
    		lDeckName.setText(GlobalStatistics.getTempDeck().getName());
    	//set the statistics
        lStats.setText(GlobalStatistics.getCorrectStats() + "/" + GlobalStatistics.getDeck().getDeckLength() + "   " + (int)((double)GlobalStatistics.getCorrectStats()/GlobalStatistics.getDeck().getDeckLength() * 100) + "%");
    }    
    
    public void handleBackButtonClick() throws IOException{
        System.out.println("Back button clicked.");
        
        //save the state of the deck
		if(GlobalStatistics.getTempDeck() == null)
			SaveLoad.saveState(GlobalStatistics.getDeck().getName());
	  	else
	  		SaveLoad.saveState(GlobalStatistics.getTempDeck().getName());
      		
        //go back to the previous scene, which should be the main menu
		bReview.getScene().setRoot(FXMLLoader.load(getClass().getResource(GlobalStatistics.popScene())));
    }
    
    public void handleOptionsButtonClick() throws IOException{
        System.out.println("Options button clicked.");
        
        //change the scene to the create a deck scene
        GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyStatsView.fxml");
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyOptions.fxml")));
    }
    
    public void handleReviewButtonClick() throws IOException{
        System.out.println("Review button clicked.");
    	GlobalStatistics.setStudy(true);
        
        if(GlobalStatistics.getDeck().getDeckLength() == GlobalStatistics.getCorrectStats())
            bReview.setText("Nothing to review!");
        else{
            //save the current deck
            if(GlobalStatistics.getTempDeck() == null)
                GlobalStatistics.setTempDeck(GlobalStatistics.getDeck());

            //set the current deck as the review deck
            GlobalStatistics.setDeck(GlobalStatistics.createReviewDeck());
            
            //initialize the new deck
            GlobalStatistics.initializeDeck();
            
            System.out.println("New deck made for review");
            
            //reload the study
            bReview.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
        }
    }
    
    public void handleCardsButtonClick() throws IOException{
        //debug statement
        System.out.println("Card view button clicked.");

        GlobalStatistics.setTempCard(GlobalStatistics.getCard());
        GlobalStatistics.setTempCardIndex(GlobalStatistics.getCardIndex());

        //change the scene to the cards view scene
        GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyStatsView.fxml");
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml")));
    }
    
    public void handleRestartButtonClick() throws IOException{
        System.out.println("Restart button clicked.");
    	GlobalStatistics.setStudy(true);
        
        if(GlobalStatistics.getTempDeck() != null){
            GlobalStatistics.setDeck(GlobalStatistics.getTempDeck());
            GlobalStatistics.removeTempDeck();
        }
        
        //initialize the new deck
        GlobalStatistics.initializeDeck();
        
        //reload the study
        bRestart.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/StudyView.fxml")));
    }
    
    public void handleTitleClick() throws IOException{
    	//debug statement
    	System.out.println(lDeckName.getText() + " label clicked"); 
    	
        //change the scene to the create a deck scene
    	GlobalStatistics.pushScene("/samuelstrobel/flashcards/GUI/StudyStatsView.fxml");
        bBack.getScene().setRoot(FXMLLoader.load(getClass().getResource("/samuelstrobel/flashcards/GUI/NewDeck.fxml")));
    }
}
