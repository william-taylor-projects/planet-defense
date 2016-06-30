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
import com.planetDefense.objects.*;

/**
 *  The earth collision event handles when 
 *  a enemy hits the earth
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class EarthCollision implements IEvent {
	private static final EventManager eventManager;
	/** The explosion effect that needs to be drawn when the event is called */
	private Explosion effect;
	
	/** A reference to the earth so we can reduce its health */
	private Earth earth;
	
	/** A reference to the enemy that hits so we can reset it */
	private Enemy enemy;
	
	/** A reference to the player to pass to the earth death event */
	private Ship player;

	/** Static initialiser to grab the event manager */
	static  {
		eventManager = EventManager.get();
	}
	
	/**
	 * A constructor that gets all the data needed to execute the event
	 * 
	 * @param enemy A reference to the enemy that hits the earth
	 * @param earth A reference to the planet in the scene
	 * @param player A reference to the Ship that the player controls
	 */
	public EarthCollision(Enemy enemy, Earth earth, Ship player) {
		this.player = player;
		this.earth = earth;
		this.enemy = enemy;
	}
	
	/** The handler for the event */
	@Override
	public void onActivate(Object data) {	
		/** Make sure that the event has all the data needed */
		if(enemy != null && earth != null && player != null) {
			/** get the position of the planet */
			Vector2D position = earth.getSprite().getPosition();
			Vector2D size = earth.getSprite().getSize();
					
			/** Find the centre */
			float x = position.x() + (float)size.x()/2;
			float y = position.y() + (float)size.y()/2;
			
			/** Setup the effect to play at centre */
			effect = enemy.getEffect();
			effect.DrawAt(x, y);
			effect.Reset();
			
			/** Reduce earths health */
			earth.takeDamage(10);
			enemy.resetObject();
			
			/** Execute EarthDeath event if the earth is dead */
			if(earth.getHealth() <= 0) {
				EarthDeath event = new EarthDeath(earth, player);
				eventManager.triggerEvent(event, null);
			}
		}
	}

	/** Not needed for this event */
	@Override
	public void update() {
		;
	}
}
