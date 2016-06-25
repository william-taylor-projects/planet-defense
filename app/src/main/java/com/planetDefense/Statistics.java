package com.planetDefense;

/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty. 
 *  In no event will the authors be held liable for any damages arising from 
 *  the use of this software. Permission is granted to anyone to use this 
 *  software for any purpose, including commercial applications, and to 
 *  alter it and redistribute it freely, subject to the following 
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented; 
 *     you must not claim that you wrote the original software. 
 *	   If you use this software in a product, an acknowledgment 
 *     in the product documentation would be appreciated 
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, 
 *     and must not be misrepresented as being the original 
 *     software.
 *  
 *  3. This notice may not be removed or altered 
 *     from any source distribution.
 *     
 */
import java.util.Vector;

import com.planetDefense.MessageBox;
import com.planetDefense.ResourceFile;

/**
 *  The statistics file which holds the players
 *  stats..
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class Statistics {	
	/** Inner class for stats information */
	public class Statistic {
		public Integer totalWaves;
		public Integer totalGames;
		public Integer killCount;
		public Integer levels;
	}
	
	/** This is a singleton as most scenes require this class */
	private static Statistics instance;
	
	/** The resource file itself & statistics */
	private ResourceFile saveFile;
	
	/** A instance for the inner class */
	private Statistic stats;
	
	/** A variable that tells if this is the first time the app has opened */
	private Boolean first;
	
	/**
	 * Basic Constructor
	 */
	private Statistics() {
		/** Init all the variables C++ Style :P */ 
		this.saveFile = new ResourceFile("save.xt");
		this.stats = new Statistic();
		this.first = false;
		
		if(!saveFile.Exists()) {
			Vector<String> integer = new Vector<String>();
			
			integer.add(String.valueOf(0));
			integer.add(String.valueOf(0));
			integer.add(String.valueOf(0));
			integer.add(String.valueOf(0));
			
			saveFile.Write(integer);
			first = true;
		} 
			
		stats = getStatistics();
	}
	
	/**
	 * Your basic singleton return function
	 * @return the instance of the class
	 */
	public static Statistics get() {
		if(instance == null) {
			instance = new Statistics();
		} return instance;
	}
	
	/**
	 * Called when the application exits saves the new stats
	 */
	public void release() {
		// make sure we write to a file that is actually there
		if(saveFile.Exists()) {
			// Create the vector and add data so its written to the file
			Vector<String> integer = new Vector<String>();
			
			integer.add(String.valueOf(stats.totalWaves));
			integer.add(String.valueOf(stats.totalGames));
			integer.add(String.valueOf(stats.killCount));
			integer.add(String.valueOf(stats.levels));
			
			// save it to the file
			saveFile.Write(integer);
		}
	}
	
	/** Function that is called to set a new record */
	public Statistics newRecord(Integer wave) {
		/** Only update if there is a new record */
		if(stats.levels < wave) {
			stats.levels = wave;
		} return this;
	}
	
	/** basic read stats function just updates stats and returns them */
	public Statistic readReadStats() {
		stats = getStatistics();
		return(stats);
	}
	
	/** Reset stats function sets everything to zero **/
	public Statistics resetStats() {
		Vector<String> integer = new Vector<String>();
		
		integer.add(String.valueOf(0));
		integer.add(String.valueOf(0));
		integer.add(String.valueOf(0));
		integer.add(String.valueOf(0));
		
		saveFile.Write(integer);
		return this;
	}

	/** Just updates and reads the statistics from the file */
	private Statistic getStatistics() {	
		try {
			saveFile.PrepareToRead();
			
			stats.totalWaves = Integer.valueOf(saveFile.ReadLine());
			stats.totalGames = Integer.valueOf(saveFile.ReadLine());
			stats.killCount = Integer.valueOf(saveFile.ReadLine());
			stats.levels = Integer.valueOf(saveFile.ReadLine());
			
			saveFile.Close();
		} catch(Exception e) {
			/** Post a message box incase there is a file read error */
			MessageBox error = new MessageBox();
		
			/** get the info from the exceptions */
			error.setMessage(e.getMessage());
			error.setTitle(e.toString());
			
			/** Show the messagebox */
			error.show(true);
			
		}
		
		/** return stats even if null */
		return(stats);
	}
	
	/** Updates the total waves */
	public Statistics waveDone() {
		if(stats != null) {
			stats.totalWaves++;
		} return this;
	}
	
	/** Updates the total games */
	public Statistics gamePlayed() {
		if(stats != null) {
			stats.totalGames++;
		} return this;
	}
	
	/** Updates the kill count */
	public Statistics enemyDown() {
		if(stats != null) {
			stats.killCount++;
		} return this;
	}
	
	/** Tells the game if its the first time */
	public Boolean isFirstTimePlaying() {
		/** If so it displays the tutorial */
		return(first);
	}
	
	/** Get function for the inner class */
	public Statistic getStats() {
		return stats;
	}
}
