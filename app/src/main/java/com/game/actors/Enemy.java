package com.game.actors;

import java.util.Random;

import com.framework.IFactory;
import com.framework.core.CollisionEvent;
import com.framework.core.EventManager;
import com.framework.graphics.RenderQueue;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.game.events.*;
import com.game.common.Statistics;

public class Enemy {
	public static Integer KILL_COUNT = 0;
	public static Float INCREMENT = 0.025F;
	public static Float SPEED = 1.0F;

	private static Random random = new Random();
	private Explosion effect;

	private OpenglImage sprite;
	
	private EarthCollision collision;
	private CollisionEvent listener;
	private Earth earth;
	private Ship ship;
	private Boolean spawned;
	private Float speed;

	public Enemy() {	
		sprite = new OpenglImage();
		sprite.load("sprites/enemy.png", "Enemy");
		sprite.setPosition(-50, -50, 50, 50);
	
		effect = new Explosion();
		effect.initialise(10);
	
		spawned = false;	
		this.speed = Enemy.SPEED;
	}

	public void initialise(IFactory factory) {
		earth = factory.request("Earth");
		ship = factory.request("Ship");

		collision = new EarthCollision(this, earth, ship);

		listener = new CollisionEvent();
		listener.surfaces(getSprite(), earth.getSprite());
		listener.eventType(collision);		

		EventManager.get().addListener(listener);
	}

	public void resetObject() {
		sprite.translateOnce(0, 0);
		sprite.reset();
		spawned = false;
		KILL_COUNT++;
	}

	public void explode() {
		Vector2 vec = sprite.getPosition();
		
		if(vec.getX() != -50 && vec.getY() != 50) {
			Statistics.get().enemyDown();
			
			Integer x = (int) (sprite.getPosition().getX() + sprite.getSize().getX()/2);
			Integer y = (int) (sprite.getPosition().getY() + sprite.getSize().getY()/2);
		
			effect.drateAt(x, y);
			effect.reset();
		}
	}

	public void update() {
		if(this.spawned) {
			sprite.translateTo(615, 375, speed);
			sprite.update(0);
		} 

		effect.update();
	}

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

	public void draw(RenderQueue renderList) {
		if(this.spawned) {
			renderList.put(sprite);
		} 
		
		for(int i = 0; i < 4; i++) {
			renderList.put(effect.getRaw(i));
		}
	}
	
	public Explosion getEffect() {
		return(effect);
	}
	
	public OpenglImage getSprite() {
		return(this.sprite);
	}
	
	public void setSpeed(Float i) {
		speed = i;
	}
	
	public boolean hasSpawned() {
		return(spawned);
	}
	
	public Object getRawObject() {
		return(sprite);
	}
}
