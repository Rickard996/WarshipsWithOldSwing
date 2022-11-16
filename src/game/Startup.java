package game;

import java.util.ArrayList;

public class Startup {

	private String name;
	private ArrayList<Integer> locationCells; //12 por ejemplo seria B1 en la grilla
	private int numberOfIndividualHits = 0;  //internal of the Startup
	
	public Startup(String name) {
		this.name = name;
	}
	
	public void setLocationcells(int[] newLocationCells) {
		locationCells = new ArrayList<>();
		for(int location: newLocationCells) {
			locationCells.add(location);
		}
	}
	
	public String getName() {
		return name;
	}
	
	//check the guess of the user
	public boolean checkYourself(Integer userGuess) {
		
		boolean result = false;
		int index = locationCells.indexOf(userGuess);
		
		if(index>0) {
			numberOfIndividualHits++;
			locationCells.remove(index);
			result = true;
		}
		return result;
	}
	
	//aparte chequeo si ya se hundio esta startup
	public boolean checkForSinking() {
		boolean result = false;
		if(numberOfIndividualHits==locationCells.size()) {
			result = true;
		}
		return result;
	}
	
	
	
	
	
	
	
	
}
