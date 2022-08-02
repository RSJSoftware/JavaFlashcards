package samuelstrobel.flashcards.utilities;

import java.io.Serializable;
import java.util.Random;

public class Decks implements Serializable{
	private Cards[] content;
	private String[] categoryOne;
	private String[] categoryTwo;
	private String[] categoryThree;
	private String[] categoryFour;
	private String name;
	private String description;
	
	//base constructor for a new deck; this will be the only constructor used when making decks within the application
	public Decks() {
		content = new Cards[1];
		content[0] = new Cards();
		name = "";
		description = "";
		categoryOne = new String[0];
		categoryTwo = new String[0];
		categoryThree = new String[0];
		categoryFour = new String[0];
	}
	
	public Decks(int length) {
		content = new Cards[length];
		name = "";
		description = "";
		categoryOne = new String[0];
		categoryTwo = new String[0];
		categoryThree = new String[0];
		categoryFour = new String[0];
	}
        
        
	//setter methods for deck variables
	public void setName(String newName) {
		name = newName; 
	}
	
	public void setDescription(String newDesc) {
		description = newDesc;
	}
	
	public void setCategory(int cat, int index, String newCat) {
		switch(cat){
		case 1: categoryOne[index] = newCat; break;
		case 2: categoryTwo[index] = newCat; break;
		case 3: categoryThree[index] = newCat; break;
		case 4: categoryFour[index] = newCat; break;
		default: System.out.println("Error: category " + cat + " not found.");
		}
	}
	
	//getter methods for deck variables
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getCatLength(int cat) {
		switch(cat){
		case 1: return categoryOne.length;
		case 2: return categoryTwo.length;
		case 3: return categoryThree.length;
		case 4: return categoryFour.length;
		default: return -1;
		}
	}
	
	public String getCategory(int cat, int index) {
		switch(cat){
			case 1: return categoryOne[index];
			case 2: return categoryTwo[index];
			case 3: return categoryThree[index];
			case 4: return categoryFour[index];
			default: return "Error: category " + cat + " not found.";
		}
	}
	
	public Cards getCard(int index) {
		return content[index];
	}
	
	public int getDeckLength() {
		return content.length;
	}
	
	//method for adding new categories
	public void addCategory(int cat, String newCat) {
		int newLength;
		String[] newCats; 
		//make a new identical array of categories to add the new category and switch the new one with the old one
		switch(cat) {
			case 1: 
				newLength = categoryOne.length + 1;
				newCats = new String[newLength];
				
				if(newLength > 1) {
					for(int i = 0; i < categoryOne.length; i++)
						newCats[i] = categoryOne[i];
				}
				
				newCats[newCats.length - 1] = newCat;
				categoryOne = newCats;
				break;
				
			case 2:
				newLength = categoryTwo.length + 1;
				newCats = new String[newLength];
				
				if(newLength > 1) {
					for(int i = 0; i < categoryTwo.length; i++)
						newCats[i] = categoryTwo[i];
				}
				
				newCats[newCats.length - 1] = newCat;
				categoryTwo = newCats;
				break;
				
			case 3:
				newLength = categoryThree.length + 1;
				newCats = new String[newLength];
				
				if(newLength > 1) {
					for(int i = 0; i < categoryThree.length; i++)
						newCats[i] = categoryThree[i];
				}
				
				newCats[newCats.length - 1] = newCat;
				categoryThree = newCats;
				break;
				
			case 4:
				newLength = categoryFour.length + 1;
				newCats = new String[newLength];
				
				if(newLength > 1) {
					for(int i = 0; i < categoryFour.length; i++)
						newCats[i] = categoryFour[i];
				}
				
				newCats[newCats.length - 1] = newCat;
				categoryFour = newCats;
				break;
				
			default: System.out.println("Error category out of bounds. Unable to add: " + newCat + " to category number: " + cat);
		}
	}
	
	//method for removing categories
	public void removeCat(int cat, int index) {
		int newLength;
		String[] newCats; 
		
		switch(cat) {
			case 1: 
				//make the new length one shorter than the current length
				newLength = categoryOne.length - 1;
				newCats = new String[newLength];
				
				//copy over all the categories except the one that was deleted
				if(newLength > 0) {
					for(int i = 0, j = 0; i < newLength; i++, j++) {
						if(j == index)
							j++;
						newCats[i] = categoryOne[j];
					}
				}
				
				//fix the set categories for all the cards, if their category index was greater than the one deleted, move them down own, if it was the deleted, set them to default
				for(int i = 0; i < content.length; i++) {
					if(content[i].getCategory(1) == index)
						content[i].setCategory(1, -1);
					else if(content[i].getCategory(1) > index)
						content[i].setCategory(1, (content[i].getCategory(1) - 1));
				}
				
				//set the new categories
				categoryOne = newCats;
				break;
			case 2: 
				//make the new length one shorter than the current length
				newLength = categoryTwo.length - 1;
				newCats = new String[newLength];
				
				//copy over all the categories except the one that was deleted
				if(newLength > 0) {
					for(int i = 0, j = 0; i < newLength; i++, j++) {
						if(j == index)
							j++;
						newCats[i] = categoryTwo[j];
					}
				}
				
				//fix the set categories for all the cards, if their category index was greater than the one deleted, move them down own, if it was the deleted, set them to default
				for(int i = 0; i < content.length; i++) {
					if(content[i].getCategory(2) == index)
						content[i].setCategory(2, -1);
					else if(content[i].getCategory(2) > index)
						content[i].setCategory(2, (content[i].getCategory(1) - 1));
				}
				
				//set the new categories
				categoryTwo = newCats;
				break;
			case 3: 
				//make the new length one shorter than the current length
				newLength = categoryThree.length - 1;
				newCats = new String[newLength];
				
				//copy over all the categories except the one that was deleted
				if(newLength > 0) {
					for(int i = 0, j = 0; i < newLength; i++, j++) {
						if(j == index)
							j++;
						newCats[i] = categoryThree[j];
					}
				}
				
				//fix the set categories for all the cards, if their category index was greater than the one deleted, move them down own, if it was the deleted, set them to default
				for(int i = 0; i < content.length; i++) {
					if(content[i].getCategory(3) == index)
						content[i].setCategory(3, -1);
					else if(content[i].getCategory(3) > index)
						content[i].setCategory(3, (content[i].getCategory(1) - 1));
				}
				
				//set the new categories
				categoryThree = newCats;
				break;
			case 4: 
				//make the new length one shorter than the current length
				newLength = categoryFour.length - 1;
				newCats = new String[newLength];
				
				//copy over all the categories except the one that was deleted
				if(newLength > 0) {
					for(int i = 0, j = 0; i < newLength; i++, j++) {
						if(j == index)
							j++;
						newCats[i] = categoryFour[j];
					}
				}
				
				//fix the set categories for all the cards, if their category index was greater than the one deleted, move them down own, if it was the deleted, set them to default
				for(int i = 0; i < content.length; i++) {
					if(content[i].getCategory(4) == index)
						content[i].setCategory(4, -1);
					else if(content[i].getCategory(4) > index)
						content[i].setCategory(4, (content[i].getCategory(1) - 1));
				}
				
				//set the new categories
				categoryFour  = newCats;
				break;
			default: System.out.println("Error category out of bounds.");
		}
	}
	
	//method for adding new cards
	public void addCard() {
		//make a new identical array of cards with one extra empty card
		int newLength = content.length + 1;
		Cards[] newDeck = new Cards[newLength];
		
		for(int i = 0; i < content.length; i++)
			newDeck[i] = content[i];
		 
		newDeck[newDeck.length - 1] = new Cards();
		
		//switch the new deck with the old one
		content = newDeck;
	}
        
        //method to remove a card in the deck
        public void removeCard(int index){
            //swap the first and last card
            swap(index, (content.length - 1));
            
            //create a new deck that's one card shorter
            int newLength = content.length - 1;
            Cards[] newDeck = new Cards[newLength];

            //move all cards over except for the last one
            for(int i = 0; i < newLength; i++)
                    newDeck[i] = content[i];

            //switch the new deck with the old one
            content = newDeck;
        }
	
        //method for setting cards
        public void setCard(int index, Cards newCard){
            content[index] = newCard;
        }
	
	    //method for sorting cards
	    public void sort(int currentSide){
	        int end; 
	        int largest = 0;
	        
	        //use a selection sort to sort the list of cards
	        for(end = content.length - 1; end != 0; end--){
	            for(int i = 0; i <= end; i++)
	                //see if the current card is larger than the previous largest
	                if(content[i].compareTo(content[largest], currentSide) > 0)
	                    largest = i;//end inner loop
	            
	            //swap the largest card and the last card and reset the largest index to 0
	            swap(end, largest);
	            largest = 0;
	        }//end outer loop
	    }
        
        //method for shuffling cards
        public void shuffle(){
            Random rand = new Random();

            //swap the current card with a random card in the deck
            for(int i = 0; i < content.length; i++)
                swap(i, rand.nextInt(content.length));
        }
        
        //method for swapping two cards by index
        private void swap(int index, int newIndex){
            Cards temp = content[index];
            content[index] = content[newIndex];
            content[newIndex] = temp;
        }
        
	//printing method, should be readable as a .csv file
	public void print() {
		//print deck information
		System.out.println(name);
		System.out.println(description);
		System.out.println();
		//print card information
		for(Cards c : content)
			c.print();
	}
	
	//a separate print method for saving
	public String save(String fileType) {
		String output = "";
		
		//check the filetype and create the save accordingly
		if(fileType.equals(".csv")) {
			//print deck information
			output = output + (name) + "\n";
			output = output + (description) + "\n";
			
			//print all categories
			for(int i = 0; i < categoryOne.length; i++)
				output = output + categoryOne[i] + ",";
			output = output + "\n";
			for(int i = 0; i < categoryTwo.length; i++)
				output = output + categoryTwo[i] + ",";
			output = output + "\n";
			for(int i = 0; i < categoryThree.length; i++)
				output = output + categoryThree[i] + ",";
			output = output + "\n";
			for(int i = 0; i < categoryFour.length; i++)
				output = output + categoryFour[i] + ",";
			output = output + "\n\n";
		} else if(fileType.equals(".xls") || fileType.equals(".xlsx")) {
			//print deck information
			output = output + (name) + "\n";
			output = output + (description) + "\n";
			
			//print all categories
			for(int i = 0; i < categoryOne.length; i++)
				output = output + categoryOne[i] + "\t";
			output = output + "\n";
			for(int i = 0; i < categoryTwo.length; i++)
				output = output + categoryTwo[i] + "\t";
			output = output + "\n";
			for(int i = 0; i < categoryThree.length; i++)
				output = output + categoryThree[i] + "\t";
			output = output + "\n";
			for(int i = 0; i < categoryFour.length; i++)
				output = output + categoryFour[i] + "\t";
			output = output + "\n\n";
		} else {
			System.out.println("error, file type not supported: decks");
		}
		
		//print card information
		for(Cards c : content)
			output = output + c.save(fileType);
		
		return output;
	}
}
