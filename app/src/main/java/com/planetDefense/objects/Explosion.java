package com.planetDefense.objects;

import com.framework.math.Vector2;
import com.framework.opengl.OpenglParticles;

public class Explosion {
	private OpenglParticles[] Shooters;
	private Vector2[] Dest;
	
	public Explosion() {
		Shooters = new OpenglParticles[4];
		Dest = new Vector2[4];
		
		for(int i = 0; i < 4; i++) {
			Shooters[i] = new OpenglParticles();
			Dest[i] = new Vector2();
		}
	}
	
	public void DrawAt(float x, float y) {
		for(int i = 0; i < 4; i++) {
			Shooters[i].setPosition(x, y);
		}
	}
	
	public void Initialise(float radius) {
		Dest[0].set(0, 0 + radius);
		Dest[1].set(0 + radius, 0);
		Dest[2].set(0 - radius, 0);
		Dest[3].set(0, 0 - radius);
		
		for(int i = 0; i < 4; i++) {
			Shooters[i].initialiseParticles(250, 0, 0);
			Shooters[i].setDestinations(Dest[i].getX(), Dest[i].getY());
		}
	}
	
	public void Update() {
		for(int i = 0; i < 4; i++) {
			Shooters[i].update();
		}
	}
	
	public void Reset() {
		for(int i = 0; i < 4; i++) {
			Shooters[i].reset();
		}
	}

	public OpenglParticles getRaw(int i) {
		return Shooters[i];
	}
}
