package com.game.actors;

import com.framework.math.Vector2;
import com.framework.opengl.OpenglParticles;

public class Explosion {
	private OpenglParticles[] shooters;
	private Vector2[] dest;
	
	public Explosion() {
		shooters = new OpenglParticles[4];
		dest = new Vector2[4];
		for(int i = 0; i < 4; i++) {
			shooters[i] = new OpenglParticles();
			dest[i] = new Vector2();
		}
	}
	
	public void drateAt(float x, float y) {
		for(int i = 0; i < 4; i++) {
			shooters[i].setPosition(x, y);
		}
	}
	
	public void initialise(float radius) {
		dest[0].set(0, 0 + radius);
		dest[1].set(0 + radius, 0);
		dest[2].set(0 - radius, 0);
		dest[3].set(0, 0 - radius);
		
		for(int i = 0; i < 4; i++) {
			shooters[i].initialiseParticles(250, 0, 0);
			shooters[i].setDestinations(dest[i].getX(), dest[i].getY());
		}
	}
	
	public void update() {
		for(int i = 0; i < 4; i++) {
			shooters[i].update();
		}
	}
	
	public void reset() {
		for(int i = 0; i < 4; i++) {
			shooters[i].reset();
		}
	}

	public OpenglParticles getRaw(int i) {
		return shooters[i];
	}
}
