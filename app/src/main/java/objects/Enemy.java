package objects;

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
import com.planetDefense.TaloniteRenderer.RenderQueue;
import com.planetDefense.*;

import java.util.Random;
import events.*;

/**
 *  A class that manages a enemy in the
 *  MainScene object.
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class Enemy {
	/** Statistics that need to be counted */
	public static Integer KILL_COUNT = 0;
	public static Float INCREMENT = 0.025F;
	public static Float SPEED = 1.0F;
	
	/** Static as we only need one for every enemy */
	private static Random random = new Random();
	
	/** The effect to be played when the enemy is killed */
	private Explosion effect;
	
	/** The sprite for the enemy */
	private GL_Image sprite;
	
	private EarthCollision collision;
	private Collision listener;
	
	/** A reference to the earth for events */
	private Earth earth;
	
	/** A reference to the ship for event */
	private Ship ship;
	
	/** Has the enemy spawned yet variable */
	private Boolean spawned;
	
	/** What is the speed the enemy moves at */
	private Float speed;

	/**
	 * Basic constructor sets up the sprite and object
	 */
	public Enemy() {	
		sprite = new GL_Image();
		sprite.load("sprites/enemy.png", "Enemy");
		sprite.setPosition(-50, -50, 50, 50);
	
		effect = new Explosion();
		effect.Initialise(10);
	
		spawned = false;	
		this.speed = Enemy.SPEED;
	}
	
	/** You basic initialise function that setups the events and th object itself */
	public void initialise(IFactory factory) {
		/** get the earth and player classes from the factory */
		earth = factory.Request("Earth");
		ship = factory.Request("Ship");	
		
		/** Then setup the event that needs these ojects */
		collision = new EarthCollision(this, earth, ship);
		
		/** setup the listener */
		listener = new Collision();
		listener.surfaces(getSprite(), earth.getSprite());
		listener.eventType(collision);		
		
		/** and register it with the event manager */
		EventManager.get().addListener(listener);
	}
	
	/** Simply resets the object to its original state */
	public void resetObject() {
		sprite.translateOnce(0, 0);
		sprite.reset();
		spawned = false;
		KILL_COUNT++;
	}
	
	/** simply sets-off the explosion when called */
	public void explode() {
		Vector2D vec = sprite.getPosition();
		
		if(vec.x() != -50 && vec.y() != 50) {
			Statistics.get().enemyDown();
			
			Integer x = (int) (sprite.getPosition().x() + sprite.getSize().x()/2);
			Integer y = (int) (sprite.getPosition().y() + sprite.getSize().y()/2);
		
			effect.DrawAt(x, y);
			effect.Reset();
		}
	}

	/** update function just moves the object */
	public void update() {
		if(this.spawned) {
			sprite.translateTo(615, 375, speed);
			sprite.update(0);
		} 
		
		// and updates the effect
		effect.Update();
	}
	
	/** Spawn function does exactly what it says it does it */
	public void spawn(int seed) {
		if(!this.spawned) {
			random.setSeed(seed);
			spawned = true;
	
			Integer x = random.nextInt(1800) - 200;		
			Integer y = random.nextInt(1800) - 400;
			
			if(y <= 800 && y >= 0) {
				if(random.nextBoolean()) {
					x = 1500;
				} else {
					x = -200;
				}
			}
			
			sprite.translate(x, y);
		}
	}

	/** the draw function just adds the objects to the render queue */
	public void Draw(RenderQueue renderList) {
		// only if the enemy has spawned are we going to draw it
		if(this.spawned) {
			renderList.pushRenderable(sprite);
		} 
		
		for(int i = 0; i < 4; i++) {
			renderList.pushEffect(effect.getRaw(i));
		}
	}
	
	/** --------------------------------------------------------
	 *														   *
	 * 		 The following is a load of get functions i dont   *
	 *	 think i need to comment each one as its pretty	       *
     *	 obvious what they do.								   *
     *														   *
	 --------------------------------------------------------- */	  
	
	public Explosion getEffect() {
		return(effect);
	}
	
	public GL_Image getSprite() {
		return(this.sprite);
	}
	
	public void setSpeed(Float i) {
		speed = i;
	}
	
	public boolean hasSpawned() {
		return(spawned);
	}
	
	public Object GetRawObject() {
		return(sprite);
	}
}
