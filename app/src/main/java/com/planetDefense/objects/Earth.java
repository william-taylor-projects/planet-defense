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
import com.planetDefense.TaloniteRenderer.RenderQueue;

/**
 *  A class that manages the earth and moon object that
 *  is drawn in the MainScene
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class Earth {	
	/** Settings for the earth object that i can edit easily */
	private static final Float EARTH_ROTATION = 0.25f;
	private static final Float MOON_ROTATION = 1.0f;
	private static final Float ROTATION = 0.01f;
	
	/** The sprite center */
	private Vector2D spriteCenter;
	
	/** The earth image */
	private GL_Image earthSprite;
	
	/** The moons image */
	private GL_Image moonSprite;
	
	/** The earths texture rotation value */
	private Float earthRotation = 0f;
	
	/** The moons rotation value */
	private Float moonRotation = 0f;
	private Float rotation = 0f;
	
	/** Planets health */
	private Integer health = 100;
	
	/**
	 * Your basic constructor
	 */
	public Earth() {
		earthSprite = new GL_Image();
		earthSprite.load("sprites/planet.png", "Earth");
		earthSprite.setPosition(585, 350, 100, 100);
		
		moonSprite = new GL_Image();
		moonSprite.load("sprites/moon.png", "Moon");
		moonSprite.setPosition(560, 340, 35, 35);
		
		spriteCenter = new Vector2D();
	}
	
	/** Update function that just rotates the earth and the moon */
	public void update() {				
		earthRotation -= EARTH_ROTATION;
		moonRotation += MOON_ROTATION;
		rotation += ROTATION;

		Float x = (float)(getRadius() * Math.sin(rotation)) + getCentre().x();
		Float y = (float)(getRadius() * Math.cos(rotation)) + getCentre().y();
		
		moonSprite.translateOnce((int)(x - earthSprite.getPosition().x()), (int)(y - earthSprite.getPosition().y()));
		moonSprite.update(moonRotation.intValue());	
		
		earthSprite.update(earthRotation.intValue());	
	}

	/** Reset function simply resets the object	 */
	public void resetObject() {
		earthRotation = 0.0f;
		moonRotation = 0.0f;
		rotation = 0.0f;
	}

	/** get sprite function that returns the GL_Image that is the earth */
	public GL_Image getSprite() {
		return earthSprite;
	}
	
	/** take damage function that does exactly what it says it does */
	public void takeDamage(int i) {
		health -= i;
	}

	/** sets the health back to normal */
	public void repair() {
		this.health = 100;
	}
	
	/** Basic get function for the health variale */
	public Integer getHealth() {
		return health;
	}

	/** draw function that adds the sprites to the render queue */
	public void draw(RenderQueue renderList) {
		renderList.pushRenderable(earthSprite);
		renderList.pushRenderable(moonSprite);
	}

	/** To be implemented at a later stage */
	public void kill() {
		;
	}
	
	/** get centre function used by this class to calculated the centre point for the earth */
	private Vector2D getCentre() {
		Integer x = (int)(earthSprite.getPosition().x() + (earthSprite.getSize().x()/2));
		Integer y = (int)(earthSprite.getPosition().y() + (earthSprite.getSize().y()/2));
		spriteCenter.Set(x, y);
		return(spriteCenter);	
	}
	
	/** used by this class only */
	private float getRadius() {
		return((earthSprite.getSize().x()/2) + 40);
	}
}
