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
import com.planetDefense.*;
import com.planetDefense.TaloniteRenderer.RenderQueue;

import events.ShipCollision;
import events.ShipDeath;


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
	private GL_Image Sprite;
	
	private Collision[] Collisions;
	private Explosion Effect;	
	private Integer Health = 0;
	private Boolean damage;
	private Boolean Dead;
	private Boolean shoot = true;
	private Float x = 0f;
	private Float y = 0f;
	
	private GL_TextureManager.Sprite Advanced;
	private GL_TextureManager.Sprite Medium;
	private GL_TextureManager.Sprite Basic;
	
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
	
		Sprite = new GL_Image();
		Sprite.setPosition(577, 260, 80, 110);
		Sprite.load("sprites/spr.png", "Ship");
		
		GL_Image Sprite2 = new GL_Image();
		Sprite2.setPosition(577, 260, 80, 110);
		Sprite2.load("sprites/spr3.png", "Ship2");
		
		GL_Image Sprite3 = new GL_Image();
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
		Enemys enemys = factory.Request("Enemys");
		Collisions = new Collision[Enemys.MAX];
		ShipEvent = new ShipCollision(enemys, this);
		
		for(int i = 0; i < Enemys.MAX; i++) {
			Collisions[i] = new Collision();
			Collisions[i].surfaces(Sprite, (GL_Image)enemys.get(i).GetRawObject(), i);
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
				Vector2D Position = Sprite.getPosition();
				Vector2D Size = Sprite.getSize();
				
				float xdist = (Position.x() + (float)Size.x()/2) - x;
				float ydist = y - (Position.y() + (float)Size.y()/2);
				
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
		Details.x = Sprite.getPosition().x();
		Details.y = Sprite.getPosition().y();
		Details.r = (float)Sprite.getSize().x()/2;
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
	
	public GL_Image GetSprite() {
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
			renderList.pushRenderable(Sprite);
		}
		
		for(int i = 0; i < 4; i++) {
			renderList.pushEffect(Effect.getRaw(i));
		}
	}
}
