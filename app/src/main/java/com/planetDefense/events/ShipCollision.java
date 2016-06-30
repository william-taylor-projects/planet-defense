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
import com.planetDefense.Collision.CollisionArray;

import java.util.TimerTask;
import java.util.Timer;

import com.planetDefense.objects.Explosion;
import com.planetDefense.objects.Enemys;
import com.planetDefense.objects.Ship;

/**
 *  The earth death event handles when 
 *  a planet is destroyed
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class ShipCollision implements IEvent, CollisionArray {
	private Explosion effect;
	private Enemys enemy;
	private Ship ship;
	private int num;

	public ShipCollision(Enemys enemy, Ship ship) {
		this.enemy = enemy;
		this.ship = ship;
	}

	@Override
	public void onActivate(Object data) {
		if(enemy != null && ship != null) {
			GL_Image Object = (GL_Image)enemy.get(num).GetRawObject();
			GL_Image Sprite = (GL_Image)ship.GetRawObject();
			
			Vector2D p = Object.getPosition();
			Vector2D s = Object.getSize();
			
			effect = enemy.get(num).getEffect();
			effect.DrawAt(p.x() + (float)s.x()/2, p.y() + (float)s.y()/2);
			effect.Reset();
			
			Sprite.setShade(1f, 0.1f, 0.1f, 1f);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					GL_Image Sprite = (GL_Image)ship.GetRawObject();
					Sprite.setShade(1f, 1f, 1f, 1f);
				}
			}, 750);
			
			enemy.get(num).resetObject();
			ship.TakeDamage();
		}
	}

	@Override
	public void update() {
		;
	}

	@Override
	public void collisionID(Integer ID) {
		this.num = ID;
	}
}
