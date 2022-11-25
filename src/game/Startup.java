package game;

import java.util.ArrayList;

//Startup or Ship
public class Startup {

	private String name;
	private ArrayList<Integer> locationCells; //12 por ejemplo seria B1 en la grilla
	private int numberOfIndividualHits = 0;  //internal of the Startup
	private ArrayList<String> alphaCoords;  //location Cells in format alpha numeric
	
	public Startup(String name) {
		this.name = name;
	}
	
	public void setAlphaCoords(ArrayList<String> alphaCoords) {
		this.alphaCoords = alphaCoords;
	}
	
	public ArrayList<String> getAlphaCoords(){
		return this.alphaCoords;
	}
	
	public void setLocationcells(int[] newLocationCells) {
		locationCells = new ArrayList<>();
		for(int location: newLocationCells) {
			locationCells.add(location);
		}
	}
	
	public ArrayList<Integer> getLocationcells() {
		return locationCells;
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getNumberOfIndividualHits() {
		return numberOfIndividualHits;
	}
	
	
	//check the guess of the user
	public boolean checkYourself(Integer userGuess) {
		
		boolean result = false;
		int index = locationCells.indexOf(userGuess);
		
		if(index>-1) {
			numberOfIndividualHits++;
			locationCells.remove(index);
			result = true;
		}
		return result;
	}
	
	//aparte chequeo si ya se hundio esta startup
	public boolean checkForSinking() {
		boolean result = false;
		if(locationCells.size()==0) {   //if there is no element in the list (all were erased)
			result = true;
		}
		return result;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
	
	
	
	
}
