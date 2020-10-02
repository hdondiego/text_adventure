package edu.lewisu.cs.hdondiego.textadventure;

import java.util.LinkedHashMap;
import java.util.Random;

public class Room {
	private String name;
	private String description;
	private LinkedHashMap<String, Room> neighbors;
	private boolean hasGoal; // for building builder -> select one building to hold the reward
	private boolean usePosEffect; // boolean to determine if the user will experience a positive health effect upon entering the Room
	private boolean isMomThere; // for the GameManager to know if Mom is in that given Room
	private String posHealthNarr; // the message for a positive health narrative
	private String negHealthNarr; // the message for a negative health narrative
	private int posHealthEffect; // positive health effect to be added to the Player's health
	private int negHealthEffect; // negative health effect to be deducted from the Player's health
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LinkedHashMap<String, Room> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(LinkedHashMap<String, Room> neighbors) {
		this.neighbors = neighbors;
	}
	
	public boolean isHasGoal() {
		return hasGoal;
	}

	public void setHasGoal(boolean hasGoal) {
		this.hasGoal = hasGoal;
	}

	public boolean isUsePosEffect() {
		return usePosEffect;
	}

	public void setUsePosEffect(boolean usePosEffect) {
		this.usePosEffect = usePosEffect;
	}
	
	public void randUsePosEffect() {
		// stands for randomize use positive effect -> setting the usePosEffect to a new boolean value
		// use Random() to determine if it uses positive health effect or not
		Random rnd = new Random();
		int val = rnd.nextInt(2); // picking either 0 (false) or 1 (true)
		
		if (val == 0) {
			// false - cause damage to player
			usePosEffect = false;
		} else if (val == 1) {
			// true - give more health to player
			usePosEffect = true;
		} else {
			System.out.println("Something didn't go right when setting usePosEffect.");
		}
	}
	
	public boolean isMomThere() {
		return isMomThere;
	}

	public void setMomThere(boolean isMomThere) {
		this.isMomThere = isMomThere;
	}

	public String getPosHealthNarr() {
		return posHealthNarr;
	}

	public void setPosHealthNarr(String posHealthNarr) {
		this.posHealthNarr = posHealthNarr;
	}

	public String getNegHealthNarr() {
		return negHealthNarr;
	}

	public void setNegHealthNarr(String negHealthNarr) {
		this.negHealthNarr = negHealthNarr;
	}
	
	// returns either the positive or negative health narrative based on what was chose for usePosEffect upon instantiation
	public String getHealthNarr() {
		if (isUsePosEffect()) {
			return getPosHealthNarr();
		} else {
			return getNegHealthNarr();
		}
	}

	public int getPosHealthEffect() {
		return posHealthEffect;
	}

	public void setPosHealthEffect(int posHealthEffect) {
		this.posHealthEffect = posHealthEffect;
	}

	public int getNegHealthEffect() {
		return negHealthEffect;
	}

	public void setNegHealthEffect(int negHealthEffect) {
		this.negHealthEffect = negHealthEffect;
	}
	
	// returns the effect value depending on the usePosEffect boolean
	public int getHealthEffect() {
		if (isUsePosEffect()) {
			return getPosHealthEffect();
		} else {
			return getNegHealthEffect();
		}
	}
	
	public Room() {
		name = "";
		description = "";
		neighbors = new LinkedHashMap<String, Room>();
		hasGoal = false;
		isMomThere = false;
		posHealthNarr = "";
		negHealthNarr = "";
		posHealthEffect = 0;
		negHealthEffect = 0;
		randUsePosEffect(); // choose (set) whether this room will have a positive health effect on player or not
	}
	
	public Room(String name, String desc, String posHealthNarr, String negHealthNarr, int posHealthEffect, int negHealthEffect) {
		this(); // call the default constructor
		setName(name);
		setDescription(desc);
		setPosHealthNarr(posHealthNarr);
		setNegHealthNarr(negHealthNarr);
		setPosHealthEffect(posHealthEffect);
		setNegHealthEffect(negHealthEffect);
	}
	
	/*
	 * return the Room that lies in a particular direction
	 * @param dir - a cardinal direction
	 * @return the room in that direction (may be null)
	 */
	
	public Room getNeighbor(String dir) {
		return neighbors.get(dir);
	}
	
	/*
	 * set the neighbor of the room in a particular direction
	 * @param dir the cardinal direction
	 * @param rm the Room to connect there
	 */
	
	public void setNeighbor(String dir, Room rm) {
		neighbors.put(dir,  rm);
	}
	
	/*
	 * This returns the name of the room in a particular direction
	 * or "" if no room there
	 * @param dir the direction
	 * @return the name of the room or ""
	 */
	
	public String getNeighborName(String dir) {
		Room rm = getNeighbor(dir);
		if (rm == null) {
			return "";
		} else {
			return rm.getName();
		}
	}
	
	/*
	 * Returns names of all neighbors tab-delimited
	 * @returns tab-delimited string of neighbor names
	 */
	
	public String getNeighborsAsString() {
		return String.format("%s\t%s\t%s\t%s", getNeighborName("N"), 
				getNeighborName("S"), getNeighborName("E"), getNeighborName("W"));
	}
	
	@Override
	public boolean equals(Object other) {
		Room otherRoom = (Room)other;
		return name.equalsIgnoreCase(otherRoom.getName());
	}
	
	// even though it seems redundant
	// it helps provide more flexibility and it has minimal cost
	public boolean matchByName(String otherName) {
		return name.equalsIgnoreCase(otherName);
	}
	
	@Override
	public String toString() {
		return String.format("%s\t%s\t%s", getName(), getDescription(), getNeighborsAsString());
	}
}
