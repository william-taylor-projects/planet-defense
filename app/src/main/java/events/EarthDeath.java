package events;

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
import com.planetDefense.*;
import objects.*;

/**
 *  The earth death event handles when 
 *  a planet is destroyed
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class EarthDeath implements IEvent {
	/** A reference to the planet in the scene */
	private Earth earth;
	
	/** A reference to the player in the scene */
	private Ship ship;
	
	/**
	 * Basic constructor that setups the event
	 * 
	 * @param earth a reference to the planet
	 * @param ship a reference to the player
	 */
	public EarthDeath(Earth earth, Ship ship) {
		this.earth = earth;
		this.ship = ship;
	}
	
	/** Event handler for the event */
	@Override
	public void onActivate(Object data) {
		earth.kill();
		ship.Kill();
	}

	/** Not needed for this type of event */
	@Override
	public void update() {
		;
	}
}
