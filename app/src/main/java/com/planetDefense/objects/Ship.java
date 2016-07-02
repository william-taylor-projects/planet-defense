package com.planetDefense.objects;

import com.framework.IFactory;
import com.framework.core.CollisionEvent;
import com.framework.core.EventManager;
import com.framework.graphics.RenderQueue;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.framework.opengl.OpenglTextureUnit;
import com.planetDefense.events.ShipCollision;
import com.planetDefense.events.ShipDeath;
import android.view.MotionEvent;

public class Ship {
	public static Integer MOVEMENT_ADD = 0;
	public static Integer ARMOUR_LEVEL = 0;
	public static Integer CASH_TO_ADD = 10;
	public static Integer CASH = 0;
	
	public static enum CURRENT_SHIP {
		ADVANCED,
		MEDIUM,
		BASIC
	}
	
	public class ShipDetails {
		public float Rotation;
		public float r, x, y;
	}

	private ShipCollision ShipEvent;
	private ShipDetails Details;
	private OpenglImage Sprite;
	
	private CollisionEvent[] Collisions;
	private Explosion Effect;	
	private Integer Health = 0;
	private Boolean damage;
	private Boolean Dead;
	private Boolean shoot = true;
	private Float x = 0f;
	private Float y = 0f;
	
	private OpenglTextureUnit Advanced;
	private OpenglTextureUnit Medium;
	private OpenglTextureUnit Basic;
	
	private CURRENT_SHIP Type;
	
	public void EnableShots() {
		shoot = true;
	}
	
	public boolean shotsEnabled() {
		return shoot;
	}
	
	public void DisableShots() {
		shoot = false;
	}
	
	public Ship() {
		Effect = new Explosion();
		Effect.Initialise(30);
	
		Sprite = new OpenglImage();
		Sprite.setPosition(577, 260, 80, 110);
		Sprite.load("sprites/spr.png", "Ship");
		
		OpenglImage Sprite2 = new OpenglImage();
		Sprite2.setPosition(577, 260, 80, 110);
		Sprite2.load("sprites/spr3.png", "Ship2");

		OpenglImage Sprite3 = new OpenglImage();
		Sprite3.setPosition(577, 260, 80, 110);
		Sprite3.load("sprites/spr2.png", "Ship3");
		
		Advanced = Sprite3.getSprite();
		Medium = Sprite2.getSprite();
		Basic = Sprite.getSprite();
		
		Details = new ShipDetails();
		Health = 100;
		Dead = false;
		
		Type = CURRENT_SHIP.BASIC;
		damage = true;
	}
	
	public void EnableDamage() {
		damage = true;
	}
	
	public void DisableDamage() {
		damage = false;
	}
	
	public void SetShip(CURRENT_SHIP i) {	
		switch(i) {
			case ADVANCED: 
			{
				Type = CURRENT_SHIP.ADVANCED;
				Sprite.setSprite(Advanced); 
				break;
			}
			
			case MEDIUM: 
			{
				Type = CURRENT_SHIP.MEDIUM;
				Sprite.setSprite(Medium); 
				break;
			}
			
			case BASIC: 
			{
				Type = CURRENT_SHIP.BASIC;
				Sprite.setSprite(Basic); 
				break;
			}
		}
	}
	
	public void ResetObject() {
		Ship.ARMOUR_LEVEL = 0;
		Ship.CASH_TO_ADD = 10;
		Missiles.DELAY = 500;
		
		SetShip(CURRENT_SHIP.BASIC);
		Sprite.reset();
		Health = 100;
		Dead = false;
		CASH = 0;
	}
	
	public void AddCash() {
		CASH += CASH_TO_ADD;
	}
	
	public boolean isAlive() {
		return(Health > 0);
	}
	
	public void TakeDamage() {
		if(this.damage) {
			switch(Type)
			{
				case ADVANCED: Health -= (5 - ARMOUR_LEVEL); break;
				case MEDIUM: Health -= (7 - ARMOUR_LEVEL); break;
				case BASIC: Health -= (10 - ARMOUR_LEVEL); break;
				
				default: break;
			}
		}
	}
	
	public void Kill() {
		Health = -1;
	}
	
	public int GetHealth() {
		return Health;
	}
	
	public void Initialise(IFactory factory) {
		Enemies enemys = factory.request("Enemys");
		Collisions = new CollisionEvent[Enemies.MAX];
		ShipEvent = new ShipCollision(enemys, this);
		
		for(int i = 0; i < Enemies.MAX; i++) {
			Collisions[i] = new CollisionEvent();
			Collisions[i].surfaces(Sprite, (OpenglImage)enemys.get(i).GetRawObject(), i);
			Collisions[i].eventType(ShipEvent);
		}

		EventManager.get().addListeners(Collisions);
	}

	
	public void Update() {	
		float Angle = 0.0f;
		
		if(Health <= 0 && !Dead) {
			EventManager.get().queueEvent(new ShipDeath(this), 3000, null);
			Effect.DrawAt(GetDetails().x, GetDetails().y);
			Effect.Reset();
			Dead = true;
		} else {
			if(x != 0f && y != 0f) {
				Vector2 Position = Sprite.getPosition();
				Vector2 Size = Sprite.getSize();
				
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
		
		switch(this.Type)
		{
			case ADVANCED: Sprite.shift(x, y, 100 - MOVEMENT_ADD);  break;
			case MEDIUM: Sprite.shift(x, y, 70 - MOVEMENT_ADD); break;
			case BASIC: Sprite.shift(x, y, 60 - MOVEMENT_ADD); break;
		}
		
		Sprite.update((int)-Angle);
		Effect.Update();
	}
	
	public ShipDetails GetDetails() {
		Details.Rotation = Sprite.getRotation();
		Details.x = Sprite.getPosition().getX();
		Details.y = Sprite.getPosition().getY();
		Details.r = Sprite.getSize().getX()/2;
		return Details;
	}
	
	public void Repair() {
		this.Health = 100;
	}

	public void OnTouch(MotionEvent e, float x, float y) {
		if(Health > 0) {
			this.x = x;
			this.y = y;
		}
	}
	
	public OpenglImage GetSprite() {
		return Sprite;
	}

	public Object GetRawObject() {
		return Sprite;
	}
	
	public void Update(int Angle) {
		Sprite.update(Angle);
	}

	public void draw(RenderQueue renderList) {
		if(Health > 0) {
			renderList.put(Sprite);
		}
		
		for(int i = 0; i < 4; i++) {
			renderList.put(Effect.getRaw(i));
		}
	}
}
