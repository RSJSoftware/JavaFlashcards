package samuelstrobel.flashcards.utilities;

import java.io.Serializable;

public class Cards implements Serializable {
	private int MAX_SIDES = 5;
	
	private String[] side;
	private int categoryOne;
	private int categoryTwo;
	private int categoryThree;
	private int categoryFour;
	private Boolean isCorrect;
	private Boolean isFlag;
    private Boolean isNew;
	
	//base constructor for a card; this will be the only constructor used when making cards within the application
	public Cards() {
		side = new String[] {"","","","",""};
		categoryOne = -1;
		categoryTwo = -1;
		categoryThree = -1;
		categoryFour = -1;
		isCorrect = false;
		isFlag = false;
        isNew = true;
	}
	
	
	//setter methods for card variables
	public void setSide(int sideNum, String content) {
		//make sure the sideNum is within the max and min number of sides
		try 
		{
			side[sideNum] = content;
		} 
		catch (ArrayIndexOutOfBoundsException e) 
		{
			System.out.println("Side number: " + sideNum + " is not within 0 and " + MAX_SIDES + ".");
		}
		
	}
	
	public void setCorrect(Boolean isCorrectNew) {
		isCorrect = isCorrectNew;
	}
	
	public void setFlag(Boolean isFlagNew) {
		isFlag = isFlagNew;
	}
        
        public void setNew(Boolean isNewNew){
            isNew = isNewNew;
        }
	
	public void setCategory(int catNum, int cat) {
        switch(catNum) {
            case 1: categoryOne = cat; break;
            case 2: categoryTwo = cat; break;
            case 3: categoryThree = cat; break;
            case 4: categoryFour = cat; break;
            default: System.out.println("This category does not exist.");
        }
	}
	
	//getter methods for card variables
	public String getSide(int sideNum) {
		//make sure the sideNum is within the max and min number of sides
			try 
			{
				return side[sideNum];
			}
			catch (ArrayIndexOutOfBoundsException e) 
			{
				System.out.println("Side number: " + sideNum + " is not within 0 and " + MAX_SIDES + ".");
				return "";
			}
	}
	
	public int getCategory(int catNum) {
        switch(catNum) {
            case 1: return categoryOne;
            case 2: return categoryTwo;
            case 3: return categoryThree;
            case 4: return categoryFour;
            default: return -1;
        }
	}
	
	public Boolean getCorrect() {
		return isCorrect;
	}
	
	public Boolean getFlag() {
		return isFlag;
	}
        
    public Boolean getNew(){
        return isNew;
    }

    //method for comparing two cards by their first side
    public int compareTo(Cards card, int sideNum){
        //compare the strings, if the side is missing, return it as a higher number, because it should be at the end of a sorted list
        if(side[sideNum].equals("") && card.getSide(sideNum).equals(""))
            return 0;
        else if(side[sideNum].equals(""))
            return 1;
        else if(card.getSide(sideNum).equals(""))
            return -1;
        else
            return side[sideNum].toLowerCase().compareTo(card.getSide(sideNum).toLowerCase());
    }
        
        
	//print method for object, it should be in the format of a .csv to be easily loadable later
	public void print() {
		//print the value of all the sides
		for(String i : side)
			System.out.print((i+1) + ", ");
		
		//print the value of all the categories
		for(int i = 1; i <= 4; i++)
			System.out.print(this.getCategory(i) + ", ");
		
		//print both booleans
			System.out.println(isCorrect + ", " + isFlag);
	}
	
	//a separate print method for saving
	public String save(String fileType) {
		String output = "";
		
		//check the filetype and save accordingly
		if(fileType.equals(".csv")) {
			//print the value of all the sides
			for(int i = 0; i < 5; i++)
				output = output + (side[i] + ",");
			
			//print the value of all the categories
			for(int i = 1; i <= 4; i++)
				output = output + this.getCategory(i) + ",";
			
			//print both booleans
			output = output + (isCorrect + "," + isFlag + "\n");
		} else if(fileType.equals(".xls") || fileType.equals(".xlsx")) {
			//print the value of all the sides
			for(int i = 0; i < 5; i++)
				output = output + (side[i] + "\t");
			
			//print the value of all the categories
			for(int i = 1; i <= 4; i++)
				output = output + this.getCategory(i) + "\t";
			
			//print both booleans
			output = output + (isCorrect + "\t" + isFlag + "\n");
		} else {
			System.out.println("Error, file type not supported: cards");
		}
			
		return output;
	}
}
