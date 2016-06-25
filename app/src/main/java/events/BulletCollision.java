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
 *  The bullet collision event handles when 
 *  a bullet hits an enemy
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class BulletCollision implements IEvent {
	/** The explosion graphic that must be played when a bullet hits */
	private Explosion explosion;
	
	/** A reference to the container with all the missiles */
	private Missiles missiles;
	
	/** A reference to the enemy that was hit */
	private Enemy enemy;
	
	/** The missile number or ID */
	private Integer number;
	
	/** A reference to the ship */
	private Ship ship;

	/**
	 * Provide data function that passes the information to the state when all 
	 * objects have been setup, not through the contructor.
	 * 
	 * @param missiles a reference to the missile container
	 * @param enemy a reference to the enemy that is hit
	 * @param number a ID for the missile
	 * @param ship the player class
	 */
	public void provideData(Missiles missiles, Enemy enemy, Integer number, Ship ship) {
		this.missiles = missiles;
		this.number = number;
		this.enemy = enemy;
		this.ship = ship;
	}
	
	/** The handler for the event */
	@Override
	public void onActivate(Object data) {
		/** Make sure that the data has been passed to the event handler */
		if(missiles != null && enemy != null && ship != null) {
			/** Get the missile that hit the enemy */
			GL_Image sprite = missiles.GetMissile(number);
			
			/** Get centre position to play the explosion at */
			float x = sprite.getPosition().x() + sprite.getSize().x()/2;
			float y = sprite.getPosition().y() + sprite.getSize().y()/2;
			
			/** Setup the effect to be drawn */
			explosion = enemy.getEffect();
			explosion.DrawAt(x, y);
			explosion.Reset();
			
			/** Reset the enemy so it can spawn again */
			this.missiles.ResetMissile(number);
			this.enemy.resetObject();
			this.ship.AddCash();
			
			/** Updates stats */
			Statistics.get().enemyDown();
			
			/** reset missile */
			sprite.reset();
		}
	}

	/** Not needed for this type of event */
	@Override
	public void update() {
		;
	}
}
