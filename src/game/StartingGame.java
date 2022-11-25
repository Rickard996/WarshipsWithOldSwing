package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//Api for sound
import javax.sound.sampled.*;

public class StartingGame {

	//components of the game logic
	private GameHelper helper; //a help to build the the game. 
	//note how the helper is already initialized
	private ArrayList<Startup> startups = new ArrayList<Startup>(); // List if startup objects
	private int numOfGuesses = 0;  // to check the number of Guesses. current number of guesses
	private int maxNumOfTries = 0;  
	
	//final grid of ships
	private int[] finalGrid;
	
	//Components of GUI
	private TextArea textArea;
	private JLabel counter; //to count the tries
	private TextArea textAreaShips; //Here the user send the number of ships the game will have
	
	//Components of sound
	AudioInputStream audioInputStream;
	Clip clip;
	
	//list of all the checkBoxes
	private ArrayList<JCheckBox> checkboxList;
	private JFrame frame;
	
	String[] lettersName = {"A", "B", "C", "D", "E", "F", "G", "H"};
	String[] numbersName = {"1", "2", "3", "4", "5", "6", "7", "8"};
	String[] startupsName = {"Alpha", "Beta", "Gamma", "Delta"};
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartingGame game = new StartingGame();
		game.buildGui();
		game.buildSoundConfig();
		game.buildGameAndStart();  //builds a new game and start playing
		
	}

	//build the clip to be abled to close it and stop it
	private void buildSoundConfig() {
		// TODO Auto-generated method stub
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buildGui() {
		// TODO Auto-generated method stub
		frame = new JFrame("Startup Game with GUI: Sink the ships!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout); 
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//box for the buttons. Also gonna add a Jlabel counter of the tries
		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		//here will be four buttons: new game, save game, load game, send guess 
		buttonBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//new game button
		JButton newGame = new JButton("New Game");
		newGame.setAlignmentX(JButton.CENTER_ALIGNMENT);  //center the button
		newGame.addActionListener(event->buildGameAndStart());
		buttonBox.add(newGame);
		
		//send Guess button
		JButton sendGuess = new JButton("Send Guess");
		sendGuess.setAlignmentX(JButton.CENTER_ALIGNMENT);
		sendGuess.addActionListener(event->checkGuessAndContinue());
		buttonBox.add(sendGuess);
		
		counter = new JLabel("Tries: 0", JLabel.CENTER);
		counter.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		Font fontCounter = new Font("Verdana", Font.BOLD, 15);
		counter.setFont(fontCounter);
		counter.setForeground(Color.BLACK);
		buttonBox.add(counter);
		
		//Label quantity of ships
		JLabel quantityLabel = new JLabel("Choose number of ships (1-4)", JLabel.CENTER);
		quantityLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		Font quantityFont = new Font("Verdana", Font.BOLD, 10);
		quantityLabel.setFont(quantityFont);
		quantityLabel.setForeground(Color.red);
		quantityLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		buttonBox.add(quantityLabel);
		
		//JScroll para que user envie la cantidad de barcos (maximo 4)
		textAreaShips = new TextArea(2, 5);
		JScrollPane scrollNumberOfShips = new JScrollPane(textAreaShips);
		textAreaShips.setFont(quantityFont);
		textAreaShips.setEditable(true);
		textAreaShips.setText("3");  //3 ships by default, but the user can change it
		scrollNumberOfShips.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		buttonBox.add(scrollNumberOfShips);
		
		//JScroll para enviar info la jugador
		textArea = new TextArea(5, 10);
		JScrollPane scrollPaneText = new JScrollPane(textArea);
		textArea.setEditable(false);
		scrollPaneText.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		
		//box for the letters labels. Disabled
//		Box lettersBox = new Box(BoxLayout.Y_AXIS);
//		
//		for(String letter:lettersName) {
//			JLabel letterLabel = new JLabel(letter); 
//			letterLabel.setBorder(BorderFactory.createEmptyBorder(4, 1, 4, 1));
//			lettersBox.add(letterLabel);
//		}
		
		//numberBox disabled
//		Box numberBox = new Box(BoxLayout.X_AXIS);
//		for(String number: numbersName) {
//			JLabel numberLabel = new JLabel(number);
//			numberLabel.setBorder(BorderFactory.createEmptyBorder(4, 9, 4, 9));
//			numberBox.add(numberLabel);
//		}
		
		Box instructionsBox = new Box(BoxLayout.Y_AXIS);
		JLabel titleOfGame = new JLabel("Sink the ships!", JLabel.CENTER);
		titleOfGame.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		Font titleFont = new Font("Verdana", Font.BOLD, 30);
		titleOfGame.setFont(titleFont);
		titleOfGame.setForeground(Color.BLACK);
		instructionsBox.add(titleOfGame);
		
		JLabel instructions1 = new JLabel("Click the boxes and send your guesses to find the enemy ships. Do"
				+ "it with the minimum number of tries posible. If you take too long the ships will attack!", JLabel.CENTER);
		instructions1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		Font instructionsFont = new Font("Verdana", Font.ITALIC, 15);
		instructions1.setFont(instructionsFont);
		instructions1.setForeground(Color.GRAY);
		instructions1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		instructionsBox.add(instructions1);
		
		//colocando cajas in the JPanel
		//background.add(BorderLayout.WEST, lettersBox);
		background.add(BorderLayout.EAST, buttonBox);
//		background.add(BorderLayout.NORTH, numberBox);
		background.add(BorderLayout.SOUTH, scrollPaneText);
		background.add(BorderLayout.NORTH, instructionsBox);
		
		
		//adding to the frame
		frame.getContentPane().add(background);
		
		//Creating the grid
		GridLayout grid = new GridLayout(8,8);
		//distancia horizontal y vertical entre componentes
		//en la grilla
		grid.setHgap(1);
		grid.setHgap(2);
		
		//the panel containing the grid goes in the center
		JPanel mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);
		
		checkboxList = new ArrayList<>();
		for(int i=0; i<64;i++) {  //from 0 to 63
			JCheckBox checkBox = new JCheckBox();
			//create a new helper to use its functionality
			helper = new GameHelper();
			String label = helper.getAlphaCoordsFromIndex(i);
			checkBox.setText(label);
			
			checkBox.setSelected(false);  //parten desmarcadas
			checkboxList.add(checkBox);  //we add it to the checkboxList
			mainPanel.add(checkBox);
		}
		
		frame.setBounds(100, 100, 500, 500);
		frame.pack();
		frame.setVisible(true);
	}
	
	//here we check the guess from the user and send a feedback of what he got
	private void checkGuessAndContinue() {
		
		for(int i=0; i<64; i++) {  //loop por la grilla de guess que selecciono el user
			JCheckBox checkbox = checkboxList.get(i);
			//if is selected ie the user make a guess on this checkbox
			if(checkbox.isSelected()) {
				numOfGuesses++;
				if(finalGrid[i]==1) { //si acerto el usuario con su guess
					ArrayList<String> alphaFormatCrush = helper.convertCoordsToAlphaFormat(new int[]{i});
					textArea.append("You got a kick!! on cell: "+alphaFormatCrush.get(0)+"\n");
					
					//sound of ship creak
					buildSoundAndPlay("/sound/ship-creak-final.wav");
					
					Startup startupToBeSinked = null;
					for(Startup startup: startups) {
						startup.checkYourself(i);
//						ArrayList<Integer> locationCells;  for testing
//						locationCells = startup.getLocationcells();
//						System.out.println(locationCells.size());
						if(startup.checkForSinking()) {
							startupToBeSinked = startup;
							textArea.append("You have disabled successfully the ship: "+startup.getName()+"\n");
							
						}
					}
					if(startupToBeSinked!=null) {
						startups.remove(startupToBeSinked);
					}
					checkbox.setEnabled(false); //disable the button. show the user he got something
					checkbox.setBackground(Color.RED); //set the color to mark there is a ship
					
					//If there are no more ships in the List
					if(startups.size()==0) {
						finalizeGame("win");
						break;
					}
					//if you reached maximum num of tries, user lose
					if(numOfGuesses >= maxNumOfTries) {
						finalizeGame("lose");
						break;
					}
				}else { //si no acerto
					checkbox.setBackground(Color.BLUE);
					textArea.append("Touched water!!:"+"\n");
					//play sound of water

					buildSoundAndPlay("/sound/big-water-crash-final.wav");
				}
			}
		}
//		System.out.println("Your current quantity of attempts is: "+numOfGuesses);
		counter.setText("Tries: "+String.valueOf(numOfGuesses));
		
		clearAllCheckboxes();  //clear all the checkboxes after making a guess
	}

	private void buildSoundAndPlay(String path) {
		// TODO Auto-generated method stub
		//close and stop previous clip (if there is one)
		clip.stop();
		clip.close();
		
			try {
				audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
				clip.open(audioInputStream);
				clip.start();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Unsuported audio file");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void finalizeGame(String winOrLose) {
		// TODO Auto-generated method stub
		
		if(winOrLose.equals("win")) {
			textArea.append("You have crushed all the ships, you won!"+"\n");
			textArea.append("It took you "+numOfGuesses+" Tries to make it"+"\n");
			textArea.append("You can choose a number of ships and play again!"+"\n");
		}
		
		if(winOrLose.equals("lose")) {
			textArea.append("You took too long, the ships attacked you back, you lose ;("+"\n");
			textArea.append("the furtive ships were : "+"\n");
			for(Startup startup: startups) {
				textArea.append(startup.getName()+" placed at: "+startup.getAlphaCoords()+"\n");
			}
			textArea.append("You can choose a number of ships and play again!"+"\n");
		}
		
		clearAndCleanAllCheckboxes();
		for(JCheckBox checkbox: checkboxList) {
			checkbox.setEnabled(false);
		}
		
	}

	private void clearAllCheckboxes() {
		// TODO Auto-generated method stub
		for(JCheckBox checkbox: checkboxList) {
			checkbox.setSelected(false);
		}
	}

	//Here we build a new game and prepare all to start playing
	//Game Helper helps to locate initially the startups
	private void buildGameAndStart() {
		// TODO Auto-generated method stub
		startups.clear(); //remove al previous elements if there are
		clearAndCleanAllCheckboxes();  //Clear all the boxes to use them again
		numOfGuesses = 0; //reset number of guesses
		
//		Startup startupOne = new Startup("Ricardo");
//		startups.add(startupOne);
//		Startup startupTwo = new Startup("Pelao");
//		startups.add(startupTwo);
		
		//HERE GOES MESSAGES - INSTRUCTIONS TO PLAY
		
		helper = new GameHelper();  //create a new game helper. this is always we create a new game
		//or we start the application
		
		String userQuantity = textAreaShips.getText();  //the input from user. can be any String, so i have to check
		
		//debo hacer un textbox to receive los datos
		int quantityOfShips = helper.ships(userQuantity);  //ships revisa este valor sea adecuado, y asigna un valor para
		//trabajar correctamente (menor o igual a 4 y mayor que cero)
		
		for(int i=0; i<quantityOfShips; i++) {
			Startup newStartup = new Startup(startupsName[i]);
			startups.add(newStartup);
		}
		
		//we place each startup created
		for(Startup startup: startups) {
			int[] newLocation = helper.placeStartup(3);  //get a valid location for the startup. the helper is only one
			ArrayList<String> alphaCoords = helper.convertCoordsToAlphaFormat(newLocation);  //get the alpha coords
			//already initialized
			startup.setLocationcells(newLocation);  //set the locations of the cells for each startup
			startup.setAlphaCoords(alphaCoords);  //set in format alpha coords
			
		}
		
		finalGrid = helper.getGrid();  //get all the final locations, get the grid
		
		maxNumOfTries = 64 - quantityOfShips*3;  //parameter for win or lose GRID 8X8
	}

	private void clearAndCleanAllCheckboxes() {
		// TODO Auto-generated method stub
		clearAllCheckboxes();
		for(JCheckBox checkbox: checkboxList) {
			checkbox.setEnabled(true);
			checkbox.setBackground(Color.WHITE);
		}
	}


	
	
	
	
	
	

}
