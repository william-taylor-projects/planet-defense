package com.planetDefense.actors;

import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.planetDefense.actors.Ship.ShipDetails;

public class Missile {
	private OpenglImage missileImage;
	private Vector2 translation;
	private ShipDetails shipDetails;
	private Boolean hasFired;
	private Ship player;
	
	public Missile(Ship Player) {
		missileImage = new OpenglImage();
		missileImage.load("sprites/ball.png", "Ball");
		missileImage.setPosition(-20, -20, 20, 20);
		
		translation = new Vector2();
		translation.set(0, 0);
		
		this.player = Player;
		this.hasFired = false;
	}
	
	public void update() {
		this.shipDetails =  player.GetDetails();
	
		if(translation.getX() == 0 || translation.getY() == 0) {
			missileImage.shift(shipDetails.x + shipDetails.r, shipDetails.y + 45, 1);
			hasFired = false;
		}
		
		missileImage.translate(translation.getX(), translation.getY());
		missileImage.update(0);
	}
	
	public void fire() {
		ShipDetails details = player.GetDetails();
		
		float distanceX = Missiles.LaunchPos.getX() - (details.x + details.r);
		float distanceY = Missiles.LaunchPos.getY() - (details.y + details.r);
		
		translation.set(distanceX / 20, distanceY / 20);
		hasFired = true;
	}
	
	public void draw() {
		missileImage.render();
	}
	
	public OpenglImage getMissileImage() {
		return missileImage;
	}
	
	public Vector2 getTranslation() {
		return translation;
	}
	
	public void reset() {
		translation.set(0, 0);
		missileImage.reset();
		hasFired = false;
	}
	
	public boolean hasFired() {
		return(hasFired);
	}
}
