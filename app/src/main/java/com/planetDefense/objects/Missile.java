package com.planetDefense.objects;

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
import com.planetDefense.objects.Ship.ShipDetails;

public class Missile {
	private Vector2D Transition;
	private ShipDetails Details;
	private Boolean Fired;
	private GL_Image Sprite;
	private Ship Player;
	
	public Missile(Ship Player) {
		Sprite = new GL_Image();
		Sprite.load("sprites/ball.png", "Ball");
		Sprite.setPosition(-20, -20, 20, 20);
		
		Transition = new Vector2D();
		Transition.Set(0, 0);
		
		this.Player = Player;
		this.Fired = false;
	}
	
	public void Update() {	
		this.Details =  Player.GetDetails();
	
		if(Transition.x() == 0 || Transition.y() == 0) {
			Sprite.shift(Details.x + Details.r, Details.y + 45, 1);
			Fired = false;
		}
		
		Sprite.translate(Transition.x(), Transition.y());
		Sprite.update(0);
	}
	
	public void Fire() {
		ShipDetails details = Player.GetDetails();
		
		float distancex = Missiles.LaunchPos.x() - (details.x + details.r);
		float distancey = Missiles.LaunchPos.y() - (details.y + details.r);
		
		Transition.Set(distancex/20, distancey/20);		
		Fired = true;
	}
	
	public void Draw() {
		Sprite.render();
	}
	
	public GL_Image getSprite() {
		return Sprite;
	}
	
	public Vector2D getTransition() {
		return Transition;
	}
	
	public void Reset() {
		Transition.Set(0, 0);
		Sprite.reset();
		Fired = false;
	}
	
	public boolean hasFired() {
		return(Fired);
	}
}
