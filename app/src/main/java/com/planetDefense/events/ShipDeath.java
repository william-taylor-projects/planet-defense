package com.planetDefense.events;

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

import com.planetDefense.activity.MainActivity;
import com.planetDefense.objects.*;

/**
 *  The ship death event handles when 
 *  a player dies.
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class ShipDeath implements IEvent {
	private static final SceneManager scenes;
	private static final Statistics stats;
	
	/** Reference to the player in the game */
	private Ship player;
	
	/** Static initialiser so we only grab the managers once */
	static {
		scenes = SceneManager.get();
		stats = Statistics.get();
	}
	
	/**
	 * Basic constructor setup the event and recieves the player
	 * @param player
	 */
	public ShipDeath(Ship player) {
		this.player = player;
	}

	/** Event handler for this event */
	@Override
	public void onActivate(Object data) {
		// repair player so they can play it again
		player.Repair();
		
		// switch to new state & update stats
		scenes.SwitchTo(MainActivity.GAMEOVER);
		stats.gamePlayed();	
	}

	/** Not needed for this type of event */
	@Override
	public void update() {
		;
	}
}
