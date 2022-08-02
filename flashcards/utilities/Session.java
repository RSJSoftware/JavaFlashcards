package samuelstrobel.flashcards.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.Scanner;

public class Session implements Serializable{
	private  Decks currentDeck;
	private  Decks tempDeck;
	private  Cards currentCard;
	private  int correctStats;
	private  int cardIndex;
    private  boolean isStartSideOne = true;
    private  boolean isStudy = true;
    private  boolean isShuffle = false;
    private  boolean isOnlyFlag = false;
    private  boolean isType = false;
    private  boolean isCatOne = false;
    private  boolean isCatTwo = false;
    private  boolean isCatThree = false;
    private  boolean isCatFour = false;
    private  String selectedCatOne = "-1";
    private  String selectedCatTwo = "-1";
    private  String selectedCatThree = "-1";
    private  String selectedCatFour = "-1";

    //this is a save object and will pull the current state of global statistics in order to save it
    public Session() {
    	//if window was closed while in card view, save it as if it were in study view
    	try {
	    	if(GlobalStatistics.peekScene().equals("/samuelstrobel/flashcards/GUI/StudyCardsView.fxml")){
	            //remove a card if it's new
	            if(GlobalStatistics.getCard().getNew())
	                GlobalStatistics.getDeck().removeCard(GlobalStatistics.getCardIndex());
	    	}
    	} catch(EmptyStackException i) {
    		System.out.println("Empty Stack!");
    	}
    	
    	if(GlobalStatistics.getDeckView() != null) {
            GlobalStatistics.removeDeckView();
            GlobalStatistics.setCard(GlobalStatistics.getTempCard());
            GlobalStatistics.setCardIndex(GlobalStatistics.getTempCardIndex());
            GlobalStatistics.removeTempCard();
    	}
    	
    	currentDeck = GlobalStatistics.getDeck();
    	tempDeck = GlobalStatistics.getTempDeck();
    	currentCard = GlobalStatistics.getCard();
    	correctStats = GlobalStatistics.getCorrectStats();
    	cardIndex = GlobalStatistics.getCardIndex();
    	isStartSideOne = GlobalStatistics.getStartSide();
    	isStudy = GlobalStatistics.getStudy();
    	isShuffle = GlobalStatistics.getShuffle();
    	isOnlyFlag = GlobalStatistics.getOnlyFlag();
        isType = GlobalStatistics.getType();
    	isCatOne = GlobalStatistics.getIsSelectedCat(1);
    	isCatTwo = GlobalStatistics.getIsSelectedCat(2);
    	isCatThree = GlobalStatistics.getIsSelectedCat(3);
    	isCatFour = GlobalStatistics.getIsSelectedCat(4);
    	selectedCatOne = GlobalStatistics.getSelectedCat(1);
    	selectedCatTwo = GlobalStatistics.getSelectedCat(2);
    	selectedCatThree = GlobalStatistics.getSelectedCat(3);
    	selectedCatFour = GlobalStatistics.getSelectedCat(4);
    }
    
    public void initialize() {
    	GlobalStatistics.setDeck(currentDeck);
    	GlobalStatistics.setTempDeck(tempDeck);
    	GlobalStatistics.setCard(currentCard);
    	GlobalStatistics.setCorrectStats(correctStats);
    	GlobalStatistics.setCardIndex(cardIndex);
    	GlobalStatistics.setStartSide(isStartSideOne);
    	GlobalStatistics.setStudy(isStudy);
    	GlobalStatistics.setShuffle(isShuffle);
    	GlobalStatistics.setOnlyFlag(isOnlyFlag);
    	GlobalStatistics.setType(isType);
    	GlobalStatistics.setIsSelectedCat(1, isCatOne);
    	GlobalStatistics.setIsSelectedCat(2, isCatTwo);
    	GlobalStatistics.setIsSelectedCat(3, isCatThree);
    	GlobalStatistics.setIsSelectedCat(4, isCatFour);
    	GlobalStatistics.setSelectedCat(1, selectedCatOne);
    	GlobalStatistics.setSelectedCat(2, selectedCatTwo);
    	GlobalStatistics.setSelectedCat(3, selectedCatThree);
    	GlobalStatistics.setSelectedCat(4, selectedCatFour);
    }

    public static void deckDefault() throws FileNotFoundException {
    	File settings = new File("settings.ini");
    	//if a settings file exists, set those to the default values
    	if(settings.exists()) {
			Scanner reader = new Scanner(settings);
			if(reader.nextLine().equals("t"))
				GlobalStatistics.setStartSide(true);
			else
				GlobalStatistics.setStartSide(false);
			

			if(reader.nextLine().equals("t"))
				GlobalStatistics.setShuffle(true);
			else
				GlobalStatistics.setShuffle(false);
			

			if(reader.nextLine().equals("t"))
				GlobalStatistics.setOnlyFlag(true);
			else
				GlobalStatistics.setOnlyFlag(false);
			

			if(reader.nextLine().equals("t"))
				GlobalStatistics.setType(true);
			else
				GlobalStatistics.setType(false);
    	} else {
    		System.out.println("No settings file");
    	}
    }
}
