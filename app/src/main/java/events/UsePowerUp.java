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
import objects.PowerUp;

/**
 *  The use power up event that is called
 *  when a player gets a power up.
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class UsePowerUp implements IEvent {
	/** The powerup that is called upon collision */
	private PowerUp powerUp;
	
	/** Constructor just accepts the powerup */
	public UsePowerUp(PowerUp powerUp) {
		ChangePowerUp(powerUp);
	}
	
	/** Called when we want to change the powerup */
	public void ChangePowerUp(PowerUp p) {
		powerUp = p;
	}

	/** Event handler for the event */
	@Override
	public void onActivate(Object data) {
		powerUp.onPickUp();
	}

	/** Not needed for this type of event */
	@Override
	public void update() {
		;
	}
}
