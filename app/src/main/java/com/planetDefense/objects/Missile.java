package com.planetDefense.objects;

import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.planetDefense.objects.Ship.ShipDetails;

public class Missile {
	private OpenglImage Sprite;
	private Vector2 Transition;
	private ShipDetails Details;
	private Boolean Fired;
	private Ship Player;
	
	public Missile(Ship Player) {
		Sprite = new OpenglImage();
		Sprite.load("sprites/ball.png", "Ball");
		Sprite.setPosition(-20, -20, 20, 20);
		
		Transition = new Vector2();
		Transition.set(0, 0);
		
		this.Player = Player;
		this.Fired = false;
	}
	
	public void Update() {	
		this.Details =  Player.GetDetails();
	
		if(Transition.getX() == 0 || Transition.getY() == 0) {
			Sprite.shift(Details.x + Details.r, Details.y + 45, 1);
			Fired = false;
		}
		
		Sprite.translate(Transition.getX(), Transition.getY());
		Sprite.update(0);
	}
	
	public void Fire() {
		ShipDetails details = Player.GetDetails();
		
		float distancex = Missiles.LaunchPos.getX() - (details.x + details.r);
		float distancey = Missiles.LaunchPos.getY() - (details.y + details.r);
		
		Transition.set(distancex / 20, distancey/20);
		Fired = true;
	}
	
	public void Draw() {
		Sprite.render();
	}
	
	public OpenglImage getSprite() {
		return Sprite;
	}
	
	public Vector2 getTransition() {
		return Transition;
	}
	
	public void Reset() {
		Transition.set(0, 0);
		Sprite.reset();
		Fired = false;
	}
	
	public boolean hasFired() {
		return(Fired);
	}
}
