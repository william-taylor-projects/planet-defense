package com.game.actors;

import com.framework.IFactory;
import com.framework.core.CollisionEvent;
import com.framework.core.EventManager;
import com.framework.graphics.RenderQueue;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.framework.opengl.OpenglTextureUnit;
import com.game.events.ShipCollision;
import com.game.events.ShipDeath;
import android.view.MotionEvent;

public class Ship {
	public static Integer MOVEMENT_ADD = 0;
	public static Integer ARMOUR_LEVEL = 0;
	public static Integer CASH_TO_ADD = 10;
	public static Integer CASH = 0;
	
	public enum CURRENT_SHIP {
		ADVANCED,
		MEDIUM,
		BASIC
	}
	
	public class ShipDetails {
		public float Rotation;
		public float r, x, y;
	}

	private CollisionEvent[] collisions;
	private ShipCollision shipEvent;
	private ShipDetails details;
	private OpenglImage sprite;
	private Explosion effect;
	private Integer health = 0;
	private Boolean damage;
	private Boolean dead;
	private Boolean shoot = true;
	private Float x = 0f;
	private Float y = 0f;
	
	private OpenglTextureUnit advancedTextureUnit;
	private OpenglTextureUnit mediumTextureUnit;
	private OpenglTextureUnit basicTextureUnit;
	private CURRENT_SHIP shipType;
	
	public void enableShots() {
		shoot = true;
	}
	
	public boolean shotsEnabled() {
		return shoot;
	}
	
	public void disableShots() {
		shoot = false;
	}
	
	public Ship() {
		effect = new Explosion();
		effect.initialise(30);
	
		sprite = new OpenglImage();
		sprite.setPosition(577, 260, 80, 110);
		sprite.load("sprites/spr.png", "Ship");
		
		OpenglImage Sprite2 = new OpenglImage();
		Sprite2.setPosition(577, 260, 80, 110);
		Sprite2.load("sprites/spr3.png", "Ship2");

		OpenglImage Sprite3 = new OpenglImage();
		Sprite3.setPosition(577, 260, 80, 110);
		Sprite3.load("sprites/spr2.png", "Ship3");
		
		advancedTextureUnit = Sprite3.getSprite();
		mediumTextureUnit = Sprite2.getSprite();
		basicTextureUnit = sprite.getSprite();
		
		details = new ShipDetails();
		health = 100;
		dead = false;
		
		shipType = CURRENT_SHIP.BASIC;
		damage = true;
	}
	
	public void enableDamage() {
		damage = true;
	}
	
	public void disableDamage() {
		damage = false;
	}
	
	public void setShip(CURRENT_SHIP i) {
		switch(i) {
			case ADVANCED: 
			{
				shipType = CURRENT_SHIP.ADVANCED;
				sprite.setSprite(advancedTextureUnit);
				break;
			}
			
			case MEDIUM: 
			{
				shipType = CURRENT_SHIP.MEDIUM;
				sprite.setSprite(mediumTextureUnit);
				break;
			}
			
			case BASIC: 
			{
				shipType = CURRENT_SHIP.BASIC;
				sprite.setSprite(basicTextureUnit);
				break;
			}
		}
	}
	
	public void resetObject() {
		Ship.ARMOUR_LEVEL = 0;
		Ship.CASH_TO_ADD = 10;
		Missiles.DELAY = 500;
		
		setShip(CURRENT_SHIP.BASIC);
		sprite.reset();
		health = 100;
		dead = false;
		CASH = 0;
	}
	
	public void addCash() {
		CASH += CASH_TO_ADD;
	}
	
	public boolean isAlive() {
		return(health > 0);
	}
	
	public void takeDamage() {
		if(this.damage) {
			switch(shipType)
			{
				case ADVANCED: health -= (5 - ARMOUR_LEVEL); break;
				case MEDIUM: health -= (7 - ARMOUR_LEVEL); break;
				case BASIC: health -= (10 - ARMOUR_LEVEL); break;
				
				default: break;
			}
		}
	}
	
	public void kill() {
		health = -1;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void initialise(IFactory factory) {
		Enemies enemies = factory.request("Enemys");
		collisions = new CollisionEvent[Enemies.MAX];
		shipEvent = new ShipCollision(enemies, this);
		
		for(int i = 0; i < Enemies.MAX; i++) {
			collisions[i] = new CollisionEvent();
			collisions[i].surfaces(sprite, (OpenglImage)enemies.get(i).getRawObject(), i);
			collisions[i].eventType(shipEvent);
		}

		EventManager.get().addListeners(collisions);
	}

	
	public void Update() {	
		float Angle = 0.0f;
		
		if(health <= 0 && !dead) {
			EventManager.get().queueEvent(new ShipDeath(this), 3000, null);
			effect.drateAt(GetDetails().x, GetDetails().y);
			effect.reset();
			dead = true;
		} else {
			if(x != 0f && y != 0f) {
				Vector2 Position = sprite.getPosition();
				Vector2 Size = sprite.getSize();
				
				float xdist = (Position.getX() + (float)Size.getX()/2) - x;
				float ydist = y - (Position.getY() + (float)Size.getY()/2);
				
				if(xdist >= 0 && ydist >= 0) {
					Angle = 360 - (float)Math.toDegrees(Math.atan(xdist/ydist));
				} else if(xdist < 0 && ydist > 0) {
					Angle = (float)Math.toDegrees(Math.atan(xdist/ydist));
					Angle *= -1;
				} else if(xdist < 0 && ydist < 0) {
					Angle =  180 - (float)Math.toDegrees(Math.atan(xdist/ydist));
				} else {
					Angle = 180 - (float)Math.toDegrees(Math.atan(xdist/ydist));
				} 
			} 
		}
		
		switch(this.shipType)
		{
			case ADVANCED: sprite.shift(x, y, 100 - MOVEMENT_ADD);  break;
			case MEDIUM: sprite.shift(x, y, 70 - MOVEMENT_ADD); break;
			case BASIC: sprite.shift(x, y, 60 - MOVEMENT_ADD); break;
		}
		
		sprite.update((int) -Angle);
		effect.update();
	}
	
	public ShipDetails GetDetails() {
		details.Rotation = sprite.getRotation();
		details.x = sprite.getPosition().getX();
		details.y = sprite.getPosition().getY();
		details.r = sprite.getSize().getX()/2;
		return details;
	}
	
	public void Repair() {
		this.health = 100;
	}

	public void OnTouch(MotionEvent e, float x, float y) {
		if(health > 0) {
			this.x = x;
			this.y = y;
		}
	}
	
	public OpenglImage getSprite() {
		return sprite;
	}

	public Object getRawObject() {
		return sprite;
	}
	
	public void Update(int Angle) {
		sprite.update(Angle);
	}

	public void draw(RenderQueue renderList) {
		if(health > 0) {
			renderList.put(sprite);
		}
		
		for(int i = 0; i < 4; i++) {
			renderList.put(effect.getRaw(i));
		}
	}
}
