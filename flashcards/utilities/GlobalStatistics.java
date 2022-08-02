package samuelstrobel.flashcards.utilities;

import java.util.Stack;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GlobalStatistics {
	private static Decks currentDeck;
    private static Decks deckView;
	private static Decks tempDeck;
	private static Cards currentCard;
    private static Cards tempCard;
	private static Stack<String> scenes = new Stack<String>();
	private static int correctStats;
	private static int cardIndex;
    private static int tempCardIndex;
    private static int editCategory;
    private static int sortedSide = 0;
    private static boolean isStartSideOne = true;
    private static boolean isStudy = true;
    private static boolean isShuffle = false;
    private static boolean isOnlyFlag = false;
    private static boolean isCatOne = false;
    private static boolean isCatTwo = false;
    private static boolean isCatThree = false;
    private static boolean isCatFour = false;
    private static boolean isJustLoaded = false;
    private static boolean isType = false;
    private static String selectedCatOne = "-1";
    private static String selectedCatTwo = "-1";
    private static String selectedCatThree = "-1";
    private static String selectedCatFour = "-1";
    
    //initizalizing method
    public static void initialize() {
    	currentDeck = null;
        deckView = null;
    	tempDeck = null;
    	currentCard = null;
        tempCard = null;
    	scenes.clear();
    	correctStats = 0;
    	cardIndex = 0;
        tempCardIndex = 0;
        editCategory = 0;
        sortedSide = 0;
        isStartSideOne = true;
        isStudy = true;
        isShuffle = false;
        isOnlyFlag = false;
        isType = false;
        isCatOne = false;
        isCatTwo = false;
        isCatThree = false;
        isCatFour = false;
        selectedCatOne = "-1";
        selectedCatTwo = "-1";
        selectedCatThree = "-1";
        selectedCatFour = "-1";
    }
    
	//setter methods
	public static void setDeck(Decks deck) {
		currentDeck = deck;
	}
	
        public static void setTempDeck(Decks deck){
            tempDeck = deck;
        }
        
        public static void setTempCard(Cards card){
            tempCard = card;
        }
        
        public static void setTempCardIndex(int index){
            tempCardIndex = index;
        }
        
	public static void setCard(Cards card) {
		currentCard = card;
	}
	
        public static void setDeckView(Decks deck){
            deckView = deck;
        }
        
        public static void removeDeckView(){
            deckView = null;
        }
	
	public static void removeDeck() {
		currentDeck = null;
	}
	
	public static void removeCard() {
		currentCard = null;
	}
        
        public static void removeTempCard(){
            tempCard = null;
        }
        
        public static void removeTempDeck(){
            tempDeck = null;
        }
	
	public static void setCorrectStats(int number) {
		correctStats = number;
	}
	
	public static void incCorrectStats() {
		correctStats++;
	}
	
	public static void decCorrectStats() {
		correctStats--;
	}
	
	public static void setCardIndex(int index) {
		cardIndex = index;
	}
	
	public static void incCardIndex() {
		cardIndex++;
	}

	public static void decCardIndex() {
		cardIndex--;
	}
	
	public static void setStudy(boolean study) {
		isStudy = study;
	}
	
	public static void setSortedSide(int side) {
		sortedSide = side;
	}
	
	public static void setShuffle(boolean shuffle) {
		isShuffle = shuffle;
	}
        
	public static void setOnlyFlag(boolean onlyFlag) {
		isOnlyFlag = onlyFlag;
	}
	
	public static void setType(boolean type) {
		isType = type;
	}
	
	public static void setEditCategory(int cat) {
		editCategory = cat;
	}
	
	public static void setStartSide(boolean side) {
		isStartSideOne = side;
	}
	
	public static void setJustLoaded(boolean load) {
		isJustLoaded = load;
	}
	
	public static void setSelectedCat(int cat, String selected) {
		switch(cat) {
			case 1: selectedCatOne = selected; break;
			case 2: selectedCatTwo = selected; break;
			case 3: selectedCatThree = selected; break;
			case 4: selectedCatFour = selected; break;
			default: System.out.println("This category does not exist. (setSelectedCat)");
		}
	}
	
	public static void setIsSelectedCat(int cat, Boolean selected) {
		switch(cat) {
			case 1: isCatOne = selected; break;
			case 2: isCatTwo = selected; break;
			case 3: isCatThree = selected; break;
			case 4: isCatFour = selected; break;
			default: System.out.println("This category does not exist. (setIsSelectedCat)");
		}
	}
	
	//getter methods
	public static Decks getDeck() {
		return currentDeck;
	}
	
	public static Cards getCard() {
		return currentCard;
	}
	
	public static int getCorrectStats() {
		return correctStats;
	}
	
	public static int getCardIndex() {
		return cardIndex;
	}
        
        public static Decks getTempDeck(){
            return tempDeck;
        }
        
        public static Cards getTempCard(){
            return tempCard;
        }
        
        public static Decks getDeckView(){
            return deckView;
        }
        
        public static int getTempCardIndex(){
            return tempCardIndex;
        }
        
        public static int getSortedSide() {
        	return sortedSide;
        }
    	
    	public static boolean getStudy() {
    		return isStudy;
    	}
    	
    	public static boolean getShuffle() {
    		return isShuffle;
    	}
    	
    	public static boolean getOnlyFlag() {
    		return isOnlyFlag;
    	}
    	
    	public static int getEditCategory() {
    		return editCategory;
    	}
    	
    	public static boolean getStartSide() {
    		return isStartSideOne;
    	}
    	
    	public static boolean getJustLoaded() {
    		return isJustLoaded;
    	}
    	
    	public static boolean getType() {
    		return isType;
    	}
    	
    	public static String getSelectedCat(int cat) {
    		switch(cat) {
				case 1: return selectedCatOne;
				case 2: return selectedCatTwo;
				case 3: return selectedCatThree;
				case 4: return selectedCatFour;
				default: System.out.println("This category does not exist. (getSelectedCat)"); return "-1";
			}
    	}
    	
    	public static Boolean getIsSelectedCat(int cat) {
    		switch(cat) {
				case 1: return isCatOne;
				case 2: return isCatTwo;
				case 3: return isCatThree;
				case 4: return isCatFour;
				default: System.out.println("This category does not exist. (getIsSelectedCat)"); return false;
			}
    	}
	
	//control for the scene stack
	public static void pushScene(String scene) {
		scenes.push(scene);
	}
	
	public static String popScene() {
		return scenes.pop();
	}
	
	public static String peekScene() {
		return scenes.peek();
	}
	
        //method for creating a review deck to study
	public static Decks createReviewDeck(){
            //find how many missed cards there were
            int size = currentDeck.getDeckLength() - correctStats;
            int lastIndex = 0;
            
            //create a new review deck
            Decks reviewDeck = new Decks(size);
            
            //make references to all missed cards in the new deck
            for(int i = 0; i < size; i++){
            	for(int j = lastIndex; j < currentDeck.getDeckLength(); j++)
	                if(!currentDeck.getCard(j).getCorrect()) {
	                    reviewDeck.setCard(i, currentDeck.getCard(j));
	                    lastIndex = j+1;
	                    break;
	                }//end inner loop
            }//end outer loop
            
            //return the new deck
            return reviewDeck;
        }
        
        //method for making a deck view (a sorted deck)
        public static Decks createDeckView(){
        	Decks newDeckView;
        	
            //make a new identical deck to the current deck (after making sure it's not a review deck)
        	if(tempDeck == null) {
		        newDeckView = new Decks(currentDeck.getDeckLength());
		        for(int i = 0; i < currentDeck.getDeckLength(); i++)
		            newDeckView.setCard(i, currentDeck.getCard(i));
        	}else {
        		newDeckView = new Decks(tempDeck.getDeckLength());
		        for(int i = 0; i < tempDeck.getDeckLength(); i++)
		            newDeckView.setCard(i, tempDeck.getCard(i));
        	}
            
            //sort deck by side one as long as there are cards
        	if(newDeckView.getDeckLength() > 0)
        		newDeckView.sort(0);
            
            //return the new deck
            return newDeckView;
        }
        
        public static Decks createFlagOnlyView() {
        	Decks flagOnlyView;
        	int flagged = 0;
        	int lastIndex = 0;
        	
        	//find the number of flagged cards and use that to make the length of the new deck
        	for(int i = 0; i < currentDeck.getDeckLength(); i++)
        		if(currentDeck.getCard(i).getFlag())
        			flagged++;
        	
        	int size = flagged;
        	
        	flagOnlyView = new Decks(size);
        	
        	//make references to all flagged cards in the new deck
            for(int i = 0; i < size; i++){
            	for(int j = lastIndex; j < currentDeck.getDeckLength(); j++)
	                if(currentDeck.getCard(j).getFlag()) {
	                	flagOnlyView.setCard(i, currentDeck.getCard(j));
	                    lastIndex = j+1;
	                    break;
	                }//end inner loop
            }//end outer loop
	        
            if(size <= 0) {
            	//make an error dialog if there are no flagged cards and undo the selection
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Empty Alert");
        		alert.setHeaderText("No Flagged Cards");
        		alert.setContentText("There are no flagged cards in this deck!");
        		alert.showAndWait();
        		isOnlyFlag = false;
        		return currentDeck;
            } else {
            	//return the new deck
            	return flagOnlyView;
            }
        }
        
        public static Decks createCatView(int cat) {
        	Decks catView;
        	String[] selectedCats = {""};
        	int lastIndex = 0;
        	
        	//get the selected categories for the category that's being checked
        	switch(cat) {
				case 1: selectedCats = selectedCatOne.split(","); break;
				case 2: selectedCats = selectedCatTwo.split(","); break;
				case 3: selectedCats = selectedCatThree.split(","); break;
				case 4: selectedCats = selectedCatFour.split(","); break;
				default: System.out.println("This category does not exist. (create cat view)");
        	}
        	
        	int size = 0;
        	System.out.println(selectedCats.length);
        	//get the size by comparing each card with the category
    		for(int i = 0; i < currentDeck.getDeckLength(); i++) 
    			for(int j = 0; j < selectedCats.length; j++) {
    				if(currentDeck.getCard(i).getCategory(cat) == Integer.parseInt(selectedCats[j]))
    					size++; 
    			}
        	
    		System.out.println(size);
        	catView = new Decks(size);
        	
        	//make references to all matching cards in the new deck
            for(int i = 0; i < size; i++){
            	for(int j = lastIndex; j < currentDeck.getDeckLength();j++) {
            		for(int k = 0; k < selectedCats.length; k++) {
		                if(currentDeck.getCard(j).getCategory(cat) == Integer.parseInt(selectedCats[k])) {
		                	System.out.println("Card Found and Set!");
		                	catView.setCard(i, currentDeck.getCard(j));
		                    lastIndex = j+1;
		                    break;
		                }//end inner loops
            		}
            	}
            }//end outer loop
            
            if(size <= 0) {
            	//make an error dialog if there are no flagged cards and undo the selection
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Empty Alert");
        		alert.setHeaderText("No Cards With These Categories");
        		alert.setContentText("There are no cards in this deck with the category " + cat + " selections!");
        		alert.showAndWait();
        		switch(cat) {
					case 1: isCatOne = false; break;
					case 2: isCatTwo = false; break;
					case 3: isCatThree = false; break;
					case 4: isCatFour = false; break;
					default: System.out.println("This category does not exist. (create cat view)");
        		}
        		return currentDeck;
            } else {
            	//return the new deck
            	return catView;
            }
        }
        
        //method for setting the deck to 
        public static void initializeDeck(){
        	//shuffle the current deck if it's checked, otherwise, sort it
        	if(currentDeck.getDeckLength() > 0) {
	        	if(isShuffle)
	        		currentDeck.shuffle();
	        	else
	        		currentDeck.sort(0);
	        	
	        	//make a new deck if it's only flagged cards
	        	if(isOnlyFlag) {
	        		if(tempDeck == null)
	        			tempDeck = currentDeck;
	        		
	        		currentDeck = createFlagOnlyView();
	        	}
	
	        	//make a new deck if it's only a certain category
	        	if(isCatOne) {
	        		if(tempDeck == null)
	        			tempDeck = currentDeck;
	        		
	        		currentDeck = createCatView(1);
	        	}
	        	
	        	if(isCatTwo) {
	        		if(tempDeck == null)
	        			tempDeck = currentDeck;
	        		
	        		currentDeck = createCatView(2);
	        	}
	        	
	        	if(isCatThree) {
	        		if(tempDeck == null)
	        			tempDeck = currentDeck;
	        		
	        		currentDeck = createCatView(3);
	        	}
	        	
	        	if(isCatFour) {
	        		if(tempDeck == null)
	        			tempDeck = currentDeck;
	        		
	        		currentDeck = createCatView(4);
	        	}
	        	
	        	if(currentDeck.getDeckLength() > 0)
	        		currentCard = currentDeck.getCard(0);
        	}
        	
        	correctStats = 0;
        	cardIndex = 0;
        }
}
