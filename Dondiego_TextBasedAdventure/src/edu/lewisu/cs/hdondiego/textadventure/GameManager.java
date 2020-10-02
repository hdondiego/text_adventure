package edu.lewisu.cs.hdondiego.textadventure;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {
	private Room position;  // where the player is right now
	private Building building;   // where we are traveling through
	private Player player;	// holds information about the player's name, health, and boolean if they're dead
	
	public GameManager(Building bldg) {
		building = bldg;
		
		// instantiating a new player
		this.player = new Player("Pepe");
	}
	/**
	 * returns the outcome of moving in direction dir
	 * @param dir - N, S, E, or W
	 * @return a string that describes what happened - might embellish
	 */
	public String move(String dir) {
		Room newPosition = building.getRoom(position, dir);
		if (newPosition == null) {
			return "You can't move in that direction.\n";
		} else {
			// will hold the entire message the user sees after making a move
			String message = "";
			
			position = building.getRoom(position,dir); // changes from old position to the new position of player
			message = "You are now in room " + position.getName();
			
			// informs the player about what the Room is about
			message += '\n' + position.getDescription();
			
			// checking to see if the player reached the goal
			if (position.isHasGoal()) {
				// player has found reward and set's foundReward to true
				// this is for play() to know when to stop the game
				player.setFoundReward(true);
				message += '\n' + "I found it! Now I can finally play it all night!" + '\n' + "***Congratulations! You won!***\n";
			} else {
				// if Mom is in the location that the player is at
				if (position.isMomThere()) {
					message += "\nWAIT, MOM NO! *SMACK*";
					
					message += '\n' + "Health Effect: -40";
					
					// Mom inflicts -40 damage
					player.applyHealthEffect(-40);
					
					// checks to see if the player died
					if (player.isPlayerDead()) {
						// if they died, use died message
						message += '\n' + "Aghhh... *gasp* *collapses* The Withdrawals... The Withdrawals...! *squirms*";
						return message;
					}
					
					// the player survived, but we don't know if the player will face more damage from the room
					message += "Oooo.... :'( It's not like things can't get any worse, right?";
				}
				
				// tells the player about what happens when they are in that room
				// positive or negative - these are already picked randomly
				message += '\n' + position.getHealthNarr();
				
				if (position.getHealthEffect() > 0) {
					// when Room.getHealthEffect() returns value, it is just an integer
					// the + sign helps prevent confusion when seeing an integer and not knowing what impact it did
					message += '\n' + "Health Effect: +" + position.getHealthEffect();
				} else {
					// If Room.getHealthEffect() returns a negative integer, it will also return the negative sign
					message += '\n' + "Health Effect: " + position.getHealthEffect();
				}
				
				// applies the effect to the player from that Room
				player.applyHealthEffect(position.getHealthEffect());
				
				// informs player about their name and their health
				message += '\n' + player.toString() + '\n';
				
				// checks to see that after the effect was made if it was enough to cause the player to die
				if (player.isPlayerDead()) {
					// if so, also include death message
					message += '\n' + "Aghhh... *gasp* *collapses* The Withdrawals... The Withdrawals...! *squirms*";
				}
			}
			
			return message;
		}
	}
	/**
	 * Updates the player's position based on the player's keyboard entires.
	 * @param bldg - remove in future versions b/c we already have one associated
	 */
	public void play(Building bldg) {
		/*
		 * 1. Start game
		 * 2. Player tells direction of their choosing
		 * 3. Check if direction we want to move is valid -> if valid, (the plan) inform player about the description,
		 *  (if the award is there, tell them they won. Otherwise...)if Mom is there -> warn the player that they encountered Mom and inflict damage on player,
		 *  the healthNarrative, and then the health effect
		 */
		
		Scanner sc = new Scanner(System.in);
		String playAgain = "";
		Random rnd = new Random();
		int goalRoomNum = 0;
		int momLocation = 0;
		do {
			// prints the starting message with each new game
			System.out.println("Title: Withdrawals from Within");
			System.out.print("Okay, Pepe... Mom hid your new Call of Stealth game.\n"
					+ "You're running out of time to be the Prestige Gunner first.\n"
					+ "She doesn't understand that video games are life...\n"
					+ "My homework can wait another month.\n"
					+ "Time to put my Stealth skills to the test...\n");
			
			// picking which room (besides entrance) will hold the reward
			while(true) {
				// randomly picking a number within the size of the Rooms
				goalRoomNum = rnd.nextInt(bldg.getRooms().size());
				
				// checking to see if a Room at that index is the entrance
				// if the index is not the entrance...
				if (!bldg.getRooms().get(goalRoomNum).getName().equals("entrance")) {
					// change the default value (false - not having the goal) of hasGoal (holds reward) to true
					// after it was determined that this will be the Room to hold the goal
					bldg.getRooms().get(goalRoomNum).setHasGoal(true);
					
					// leave the while loop
					break;
				}
			}
			
			// setting the location of Mom - a frequently moving enemy
			momLocation = rnd.nextInt(bldg.getRooms().size());
			bldg.getRooms().get(goalRoomNum).setMomThere(true);
			
			
			position = bldg.findRoomByName("entrance");  // must have a room called this
			if (position == null) {
				System.out.println("Invalid starting position. One of the rooms must be named 'entrance'.");
			} else {
				String dir = "";
				while (!dir.equals("Q")) {
					System.out.print("Enter direction (N, S, E, W, or Q to quit): ");
					dir = sc.nextLine().toUpperCase().trim();
					if (!dir.equals("Q")) {
						System.out.println(move(dir));
						
						// clear Mom's location
						for (Room room : bldg.getRooms()) {
							if (room.isMomThere()) {
								room.setMomThere(false);
							}
						}
						
						// set Mom's new location
						momLocation = rnd.nextInt(bldg.getRooms().size());
						bldg.getRooms().get(goalRoomNum).setMomThere(true);
						
						if (player.isFoundReward() || player.isPlayerDead()) {
							// if the player found the reward, quit the game to stop the player from moving elsewhere
							dir = "Q";
						}
					}
				}
			}
			
			// player finished the game
			// asking player if they want to play again
			do {
				System.out.print("Would you like to play again? (Y or N): ");
				playAgain = sc.nextLine().toUpperCase().trim();
			} while (!playAgain.equals("Y") && !playAgain.equals("N"));
			
			if (playAgain.equals("N")) {
				// no longer wants to play
				System.out.print("\nThank you for playing!");
				break;
			} else {
				// wants to play again -> must reset game
				player = new Player("Pepe"); // to restore player
				// randomly picking if each room will either have positive or negative impact against player
				// they were already picked when the Room objects were made, now it has to be picked again
				for (Room room : bldg.getRooms()) {
					room.randUsePosEffect();
					
					// if the room has the reward
					if (room.isHasGoal()) {
						// reset it so that it doesn't have it
						room.setHasGoal(false);
					}
					
					// resetting the game so that Mom is not in any one of the rooms, for now
					if (room.isMomThere()) {
						room.setMomThere(false);
					}
				}
			}
		} while (playAgain.equalsIgnoreCase("Y"));
	}
}