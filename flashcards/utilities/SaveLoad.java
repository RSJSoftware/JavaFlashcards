package samuelstrobel.flashcards.utilities;

import java.io.*;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SaveLoad {
	//method to save decks by printing their contents to a .csv or .xls file to be easily read later
	public static void output(Decks currentDeck, File saveFile) throws IOException {
		String fileType = "." + saveFile.getName().substring(saveFile.getName().lastIndexOf(".") + 1, saveFile.getName().length());
		System.out.println(fileType);
		//create a new file if it doesn't already exist
		if(saveFile.createNewFile())
			System.out.println("File created.");
		else
			System.out.println("File exists.");
		
		//save the information from the deck into the file
		//FileWriter writer = new FileWriter(saveFile);
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(saveFile), "UTF8"));
		writer.write(currentDeck.save(fileType));
		writer.close();
	}
	
	//method to load decks from a file
	public static void load(File loadFile) throws FileNotFoundException {
		System.out.println("loading file: " + loadFile.getName());
		boolean invalid = false;
		Decks deck = new Decks();
		
		try {
			//determine the file type and open it up
			if(loadFile.getName().contains(".csv") || loadFile.getName().contains(".xls") || loadFile.getName().contains(".txt")) {
				if(loadFile.getName().contains(".csv"))
					System.out.println("loading .csv file");
				else
					System.out.println("loading .xls file");
					
				int cardNum = 0;
				String nextCard = "";
				String[] info;
				Scanner reader = new Scanner(loadFile, "UTF-8");
				
				//make deck and load name and description
				nextCard = reader.nextLine();
				System.out.println(nextCard);
				deck.setName(nextCard);
				deck.setDescription(reader.nextLine());
				
				//get the categories
				for(int i = 1; i <= 4; i++) {
					nextCard = reader.nextLine();
					if(loadFile.getName().contains(".csv"))
						info = nextCard.split(",");
					else
						info = nextCard.split("\t");
					System.out.println(nextCard + ", " + info.length);
					if(!nextCard.equals(""))
						for(int j = 0; j < info.length; j++) {
							deck.addCategory(i, info[j]);
							System.out.println(info[j]);
						}
				}
				
				//skip the blank line
				reader.nextLine();
				
				//set first card
				nextCard = reader.nextLine();
				System.out.println(nextCard);
				if(loadFile.getName().contains(".csv"))
					info = nextCard.split(",");
				else
					info = nextCard.split("\t");
				System.out.println(info[9]);
				deck.getCard(cardNum).setSide(0, info[0].trim());
				deck.getCard(cardNum).setSide(1, info[1].trim());
				deck.getCard(cardNum).setSide(2, info[2].trim());
				deck.getCard(cardNum).setSide(3, info[3].trim());
				deck.getCard(cardNum).setSide(4, info[4].trim());
				

				deck.getCard(cardNum).setCategory(1, Integer.parseInt(info[5]));
				deck.getCard(cardNum).setCategory(2, Integer.parseInt(info[6]));
				deck.getCard(cardNum).setCategory(3, Integer.parseInt(info[7]));
				deck.getCard(cardNum).setCategory(4, Integer.parseInt(info[8]));
				
				if(info[9].equals("true"))
					deck.getCard(cardNum).setCorrect(true);
				else
					deck.getCard(cardNum).setCorrect(false);
				if(info[10].equals("true"))
					deck.getCard(cardNum).setFlag(true);
				else
					deck.getCard(cardNum).setFlag(false);
				deck.getCard(cardNum).setNew(false);
				cardNum++;
				//add and fill in the remaining cards as necessary
				while(reader.hasNextLine()) {
					deck.addCard();
					nextCard = reader.nextLine();
					System.out.println(nextCard);
					if(loadFile.getName().contains(".csv"))
						info = nextCard.split(",");
					else
						info = nextCard.split("\t");
					System.out.println(info[9]);
					deck.getCard(cardNum).setSide(0, info[0].trim());
					deck.getCard(cardNum).setSide(1, info[1].trim());
					deck.getCard(cardNum).setSide(2, info[2].trim());
					deck.getCard(cardNum).setSide(3, info[3].trim());
					deck.getCard(cardNum).setSide(4, info[4].trim());
					
					deck.getCard(cardNum).setCategory(1, Integer.parseInt(info[5]));
					deck.getCard(cardNum).setCategory(2, Integer.parseInt(info[6]));
					deck.getCard(cardNum).setCategory(3, Integer.parseInt(info[7]));
					deck.getCard(cardNum).setCategory(4, Integer.parseInt(info[8]));
					
					if(info[9].equals("true"))
						deck.getCard(cardNum).setCorrect(true);
					else
						deck.getCard(cardNum).setCorrect(false);
					if(info[10].equals("true"))
						deck.getCard(cardNum).setFlag(true);
					else
						deck.getCard(cardNum).setFlag(false);
					deck.getCard(cardNum).setNew(false);
					cardNum++;
				};
			} else if(loadFile.getName().contains(".fcd")) {
				//load the serialized file
				System.out.println("loading serialized object");
				try {
					FileInputStream load = new FileInputStream(loadFile.getName());
					ObjectInputStream in = new ObjectInputStream(load);
					deck = (Decks) in.readObject();
					in.close();
					load.close();
				} catch(IOException i) {
					i.printStackTrace();
					return;
				} catch (ClassNotFoundException c) {
					System.out.println("Decks class not found");
					c.printStackTrace();
					return;
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid File Type");
				alert.setHeaderText("Invalid File Type");
				alert.setContentText("This file type is not supported!");
				alert.showAndWait();
				invalid = true;
				return;
			}
			
			if(!invalid) {
				//reset the statistics
				GlobalStatistics.initialize();
				
				//set this as the current deck
				GlobalStatistics.setDeck(deck);
				GlobalStatistics.setCard(deck.getCard(0));
				GlobalStatistics.setCardIndex(0);
				GlobalStatistics.setJustLoaded(true);
			}
		} catch (Exception  i) {
			//make an error dialog if there was an error loading the deck
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid File");
			alert.setHeaderText("Invalid File");
			alert.setContentText("This file is not a loadable deck!");
			alert.showAndWait();
		}
	}

	//save a deck by serializing it
	public static void save(Decks currentDeck) throws IOException{
		FileOutputStream saveFile = new FileOutputStream(currentDeck.getName() + ".fcd");
		ObjectOutputStream out = new ObjectOutputStream(saveFile);
		out.writeObject(currentDeck);
		out.close();
		saveFile.close();
	}

	//save tje state of a deck
	public static void saveState(String name) throws IOException{
		Session save = new Session();
		
		//save the current session if one exists
		if(GlobalStatistics.getDeck() != null) {
			FileOutputStream saveFile = new FileOutputStream(name + ".svs");
			ObjectOutputStream out = new ObjectOutputStream(saveFile);
			out.writeObject(save);
			out.close();
			saveFile.close();
		}
	}
	
	public static void loadState(String name) {
		Session save;
		
		//load previous session if one exists
		try {
			FileInputStream load = new FileInputStream(name + ".svs");
			ObjectInputStream in = new ObjectInputStream(load);
			save = (Session) in.readObject();
			in.close();
			load.close();
			save.initialize();
		} catch(IOException i) {
			System.out.println("No saved states");
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Decks class not found");
			c.printStackTrace();
			return;
		}
	}
	
	//delete a deck
	public static void delete(Decks currentDeck) {
		File file = new File(currentDeck.getName() + ".fcd");
		File fileTwo = new File(currentDeck.getName() + ".svs");
		
		//delete the deck and its saveState
		if(file.delete())
            System.out.println("Deck deleted successfully"); 
        else
            System.out.println("Failed to delete the deck");
		
		if(fileTwo.delete())
            System.out.println("Deck deleted successfully"); 
        else
            System.out.println("Failed to delete the deck");
	}
}
