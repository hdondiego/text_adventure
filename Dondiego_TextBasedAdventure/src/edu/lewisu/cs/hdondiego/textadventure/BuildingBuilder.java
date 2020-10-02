package edu.lewisu.cs.hdondiego.textadventure;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BuildingBuilder {
	public static Building buildFromFile(String fname) {
		File f = new File(fname);
		return buildFromFile(f);
	}
	
	// modify 
	public static Building buildFromFile(File f) {
		try {
			Scanner fsc = new Scanner(f);
			String line;
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> descs = new ArrayList<String>();
			// new lines
			ArrayList<String> posHealthNarr = new ArrayList<String>(); // positive health narrative
			ArrayList<String> negHealthNarr = new ArrayList<String>(); // negative health narrative
			ArrayList<Integer> posHealthEffect = new ArrayList<Integer>(); // positive health effect
			ArrayList<Integer> negHealthEffect = new ArrayList<Integer>(); // negative health effect
			
			//
			ArrayList<String> norths = new ArrayList<String>();
			ArrayList<String> souths = new ArrayList<String>();
			ArrayList<String> easts = new ArrayList<String>();
			ArrayList<String> wests = new ArrayList<String>();
			String[] parts;
			
			// asides from the key for the layout, it holds the rest of the details needed for the game
			// description, positive health narrative, negative health narrative, positive health effect, negative health effect
			String[] details;
			
			Room rm;
			Building bldg = new Building();
			while (fsc.hasNextLine()) {
				line = fsc.nextLine().trim();
				if (line.length() > 0) {
					parts = line.split("\t");
					names.add(parts[0]);
					
					// Ex.
					// E	entry	Entry to Peter's House. Probably the most popular way to enter his house, if you ask me.\n*heavy nasal inhale* Is it me or does it smell like Pine-Sol up in here. <3\n*groan* It smells like McDonald's fry grease up in here *gag*\n10\n-10
					// Entry to Peter's House. Probably the most popular way to enter his house, if you ask me.\n*heavy nasal inhale* Is it me or does it smell like Pine-Sol up in here. <3\n*groan* It smells like McDonald's fry grease up in here *gag*\n10\n-10
					// ['Entry to Peter's House. Probably the most popular way to enter his house, if you ask me.', '*heavy nasal inhale* Is it me or does it smell like Pine-Sol up in here. <3', '*groan* It smells like McDonald's fry grease up in here *gag*', '10', '-10']
					details = parts[1].split("/");
					
					/*
					details[0] being the description
					details[1] being the positive health narrative
					details[2] being the negative health narrative
					details[3] being the positive health effect
					details[4] being the negative health effect
					*/
					
					// Ex.
					//'Entry to Peter's House. Probably the most popular way to enter his house, if you ask me.'
					descs.add(details[0]);
					
					// Ex.
					// '*heavy nasal inhale* Is it me or does it smell like Pine-Sol up in here. <3'
					posHealthNarr.add(details[1]);
					
					// Ex.
					// '*groan* It smells like McDonald's fry grease up in here *gag*'
					negHealthNarr.add(details[2]);
					
					// Ex.
					// '10' -> convert to Integer = 10
					posHealthEffect.add(Integer.parseInt(details[3]));
					
					// Ex.
					// '-10' -> convert to Integer = 10
					negHealthEffect.add(Integer.parseInt(details[4]));
					
					/*
					 * In this new version, we will not only pass the description to the Room,
					 * we will also pass the two health narratives and two health effects
					 * 
					 */
					
					/*
					 * parts[0] being the name of the building
					 * details[0] being the description
					 * details[1] being the positive health narrative
					 * details[2] being the negative health narrative
					 * details[3] being the positive health effect
					 * details[4] being the negative health effect
					 */
					
					rm = new Room(parts[0],details[0],details[1],details[2],Integer.parseInt(details[3]),Integer.parseInt(details[4])); // parts[2] <- health effect
					bldg.addRoom(rm);
					norths.add(parts[2]);
					souths.add(parts[3]);
					easts.add(parts[4]);
					wests.add(parts[5]);
				}
			}
			
			int roomNum = 0;
			ArrayList<Room> allRooms = bldg.getRooms();
			
			for (Room room : allRooms) {
				bldg.setNeighborsByName(room,norths.get(roomNum),souths.get(roomNum),easts.get(roomNum),wests.get(roomNum));
				roomNum++;
			}
			fsc.close();
			return bldg;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
}