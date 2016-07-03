package com.planetDefense.actors;

import com.framework.graphics.RenderQueue;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;

public class Earth {
	private static final Float EARTH_ROTATION = 0.25f;
	private static final Float MOON_ROTATION = 1.0f;
	private static final Float ROTATION = 0.01f;

	private OpenglImage earthSprite;
	private OpenglImage moonSprite;
	private Vector2 spriteCenter;

    private Integer health = 100;
	private Float earthRotation = 0f;
	private Float moonRotation = 0f;
	private Float rotation = 0f;

	public Earth() {
		earthSprite = new OpenglImage();
		earthSprite.load("sprites/planet.png", "Earth");
		earthSprite.setPosition(585, 350, 100, 100);
		
		moonSprite = new OpenglImage();
		moonSprite.load("sprites/moon.png", "Moon");
		moonSprite.setPosition(560, 340, 35, 35);
		
		spriteCenter = new Vector2();
	}

	public void update() {				
		earthRotation -= EARTH_ROTATION;
		moonRotation += MOON_ROTATION;
		rotation += ROTATION;

		Float x = (float)(getRadius() * Math.sin(rotation)) + getCentre().getX();
		Float y = (float)(getRadius() * Math.cos(rotation)) + getCentre().getY();
		
		moonSprite.translateOnce((int)(x - earthSprite.getPosition().getX()), (int)(y - earthSprite.getPosition().getY()));
		moonSprite.update(moonRotation.intValue());	
		
		earthSprite.update(earthRotation.intValue());	
	}

	public void resetObject() {
		earthRotation = 0.0f;
		moonRotation = 0.0f;
		rotation = 0.0f;
	}

	public OpenglImage getSprite() {
		return earthSprite;
	}

	public void takeDamage(int i) {
		health -= i;
	}

	public void repair() {
		this.health = 100;
	}

	public Integer getHealth() {
		return health;
	}

	public void draw(RenderQueue renderList) {
		renderList.put(earthSprite);
		renderList.put(moonSprite);
	}

	public void kill() {

	}

	private Vector2 getCentre() {
		Integer x = (int)(earthSprite.getPosition().getX() + (earthSprite.getSize().getX()/2));
		Integer y = (int)(earthSprite.getPosition().getY() + (earthSprite.getSize().getY()/2));
		spriteCenter.set(x, y);
		return(spriteCenter);	
	}

	private float getRadius() {
		return((earthSprite.getSize().getX()/2) + 40);
	}
}
