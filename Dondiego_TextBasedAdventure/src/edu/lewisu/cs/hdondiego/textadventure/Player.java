package edu.lewisu.cs.hdondiego.textadventure;

public class Player {
	private String name; // main character: Pepe
	private int health; // default -> 100 (max)
	private boolean isPlayerDead; // default -> false
	private boolean foundReward; // default -> false

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void applyHealthEffect(int value) {
		// if the + symbol was replaced with a - sign and negative damage was inflicted
		// the user would be gaining health points instead of losing health points
		// it is just passing the calculation of the player's current health and the health effect for a Room
		
		// applying the health effect to the Player
		if (health + value >= 100) {
			setHealth(100);
		} else if (health + value <= 0) {
			setHealth(0);
			setPlayerDead(true);
		} else {
			setHealth(health + value);
		}
	}

	public boolean isPlayerDead() {
		return isPlayerDead;
	}

	public void setPlayerDead(boolean isPlayerDead) {
		this.isPlayerDead = isPlayerDead;
	}
	
	public boolean isFoundReward() {
		return foundReward;
	}

	public void setFoundReward(boolean foundReward) {
		this.foundReward = foundReward;
	}
	
	public Player() {
		name = "";
		health = 100;
		isPlayerDead = false;
		foundReward = false;
	}
	
	public Player(String name) {
		this();
		setName(name);
	}
	
	public String toString() {
		return String.format("Name: %s\nHealth: %d", name, health);
	}
}
