package game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StartingGame {

	//components of the game logic
	private GameHelper helper; //a help to build the the game. 
	//note how the helper is already initialized
	private ArrayList<Startup> startups = new ArrayList<Startup>(); // List if startup objects
	private int numOfGuesses = 0;  // to check the number of Guesses
	
	//final grid of ships
	private int[] finalGrid;
	
	//Components of GUI
	
	//list of all the checkBoxes
	private ArrayList<JCheckBox> checkboxList;
	private JFrame frame;
	
	String[] lettersName = {"A", "B", "C", "D", "E", "F", "G", "H"};
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartingGame game = new StartingGame();
		game.buildGui();
		game.buildGameAndStart();  //builds a new game and start playing
		
	}

	private void buildGui() {
		// TODO Auto-generated method stub
		frame = new JFrame("Startup Game with GUI: Sink the ships!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout); 
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//box for the buttons
		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		//here will be four buttons: new game, save game, load game, send guess 
		
		//new game button
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(event->buildGameAndStart());
		buttonBox.add(newGame);
		
		//send Guess button
		JButton sendGuess = new JButton("Send Guess");
		sendGuess.addActionListener(event->checkGuessAndContinue());
		buttonBox.add(sendGuess);
		
		//box for the letters labels
		Box lettersBox = new Box(BoxLayout.Y_AXIS);
		
		for(String letter:lettersName) {
			JLabel letterLabel = new JLabel(letter); 
			letterLabel.setBorder(BorderFactory.createEmptyBorder(4, 1, 4, 1));
			lettersBox.add(letterLabel);
		}
		
		//colocando cajas in the JPanel
		background.add(BorderLayout.WEST, lettersBox);
		background.add(BorderLayout.EAST, buttonBox);
		
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
		for(int i=0; i<64;i++) {
			JCheckBox checkBox = new JCheckBox();
			checkBox.setSelected(false);  //parten desmarcadas

			
			checkboxList.add(checkBox);  //we add it to the checkboxList
			mainPanel.add(checkBox);
		}
		
		frame.setBounds(50, 50, 300, 300);
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
				if(finalGrid[i]==1) {
					System.out.println("You got a kick!! on cell: "+i);
				}
			}
		}
		System.out.println("Your current quantity of attempts is: "+numOfGuesses);
	}

	//Here we build a new game and prepare all to start playing
	//Game Helper helps to locate initially the startups
	private void buildGameAndStart() {
		// TODO Auto-generated method stub
		startups.clear(); //remove al previous elements if there are
		
		Startup startupOne = new Startup("Ricardo");
		startups.add(startupOne);
		Startup startupTwo = new Startup("Pelao");
		startups.add(startupTwo);
		
		//HERE GOES MESSAGES - INSTRUCTIONS TO PLAY
		
		helper = new GameHelper();  //create a new game helper. this is always we create a new game
		//or we start the application
		
		//we place each startup created
		for(Startup startup: startups) {
			int[] newLocation = helper.placeStartup();  //get a valid location for the startup. the helper is only one
			//already initialized
			startup.setLocationcells(newLocation);  //set the locations of the cells for each startup
		}
		
		finalGrid = helper.getGrid();  //get all the final locations
		
		for(int value: finalGrid) {
			System.out.println(value);
		}
		
	}
	
	
	
	
	
	

}
