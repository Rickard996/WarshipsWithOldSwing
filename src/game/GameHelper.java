package game;

import java.util.ArrayList;
import java.util.Random;

public class GameHelper {
	
	private static final String ALPHABET = "ABCDEFGH";
	private static final int GRID_LENGTH = 8; //is static and is final, so is a constant. grid 8x8
	private static final int GRID_SIZE = 64;
	private static final int MAX_ATTEMPTS = 200; //number of maximum attempts to create a valid lyout for the ships
	
	static final int HORIZONTAL_INCREMENT = 1; //avanza de uno en uno 
	static final int VERTICAL_INCREMENT = 8; //avanza de a 8 (GRID_LENGTH)
		
	private final int[] grid = new int[GRID_SIZE];  //la disposicion final de la grid
	//if is length 64 it means 0..63
	private final Random random = new Random();
	
	private int startupUpOrDown = 0;  //determina alternadamente si colocar el barco horizontal o vertical
	
	public int[] getGrid() {
		return grid;
	}
	
	
	public int ships(String input) {
		int finalQuantity = 3;  //default value
		try {
			finalQuantity = Integer.parseInt(input);
		}catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		
		if(finalQuantity<5 && finalQuantity>0) {
			return finalQuantity;
		}else {
			return 3;
		}
	}
	
	public int[] placeStartup(int sizeOfShip){
		
		int[] startupCoords = new int[sizeOfShip];  //The ships will be of size 3 casillas tipically
		int attempts = 0;  //intentos de crear el layout
		boolean success = false;
		startupUpOrDown++;  
		int increment = getIncrement();
		
		while(!success && attempts<MAX_ATTEMPTS) {
			int location = random.nextInt(GRID_SIZE);
			for(int i=0; i<startupCoords.length; i++) {
				startupCoords[i] = location;
				location+=increment;
			}
			
			if(startupFits(startupCoords, increment)) {  //si cae dentro de la grilla..
				success = coordsAvailable(startupCoords);  //true si son Coords disponibles-liberadas en la grilla
			}
		}
		savePositionToGrid(startupCoords);  //takes the generated coords and set the final grid (int[] to ArrayList<Integer>
		ArrayList<String> alphaCells = convertCoordsToAlphaFormat(startupCoords);
		System.out.println("Ships placed at: "+alphaCells);
				
		return startupCoords;
	}

	public ArrayList<String> convertCoordsToAlphaFormat(int[] startupCoords) {
		// TODO Auto-generated method stub
		
		ArrayList<String> alphaCells = new ArrayList<>();
		for(int coord: startupCoords) {
			String alphaCoords = getAlphaCoordsFromIndex(coord);
			alphaCells.add(alphaCoords);
		}
		return alphaCells;
	}

	public String getAlphaCoordsFromIndex(int index) {
		// TODO Auto-generated method stub
		int row = calcRowFromIndex(index);
		int column = index%GRID_LENGTH+1;   //el resto. grilla tamaño 8- columna 0 1 etc
		String letter = ALPHABET.substring(row, row+1);
		return letter+column;
	}

	private void savePositionToGrid(int[] startupCoords) {
		// TODO Auto-generated method stub
		for(int coord: startupCoords) {
			grid[coord]=1;  //save the used positions as 1
		}
		
		
	}

	private boolean coordsAvailable(int[] startupCoords) {
		// TODO Auto-generated method stub
		
		for(int coord: startupCoords) {
			if(grid[coord]!=0) {  //si hay alguna que ya este asignada, return false
				return false;
			}
		}
		return true;
	}

	private boolean startupFits(int[] startupCoords, int increment) {
		// TODO Auto-generated method stub
		int valueInFinalLocation = startupCoords[startupCoords.length-1];  //the last position in the array
		if(increment == HORIZONTAL_INCREMENT) {
			return calcRowFromIndex(startupCoords[0]) == calcRowFromIndex(valueInFinalLocation); //si ambas filas coinciden
			//entonces es una posicion valida
		}else {
			return valueInFinalLocation < GRID_SIZE; //caso posicionamiento vertical, basta que sea menor que 64 para ser valido
		}
	}

	private int calcRowFromIndex(int index) {  //calcula la fila donde esta este indice en la grilla
		//index between 0..64
		// TODO Auto-generated method stub
		return index/GRID_LENGTH;
	}

	private int getIncrement() {
		// TODO Auto-generated method stub
		if(startupUpOrDown%2==0) {
			return HORIZONTAL_INCREMENT;
		}else {
			return VERTICAL_INCREMENT;
		}
	}
	
	
	
	
}
