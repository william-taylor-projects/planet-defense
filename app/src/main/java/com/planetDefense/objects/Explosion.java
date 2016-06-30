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

public class Explosion {
	private GL_Particles[] Shooters;
	private Vector2D[] Dest;
	
	public Explosion() {
		Shooters = new GL_Particles[4];
		Dest = new Vector2D[4];
		
		for(int i = 0; i < 4; i++) {
			Shooters[i] =  new GL_Particles();
			Dest[i] = new Vector2D();
		}
	}
	
	public void DrawAt(float x, float y) {
		for(int i = 0; i < 4; i++) {
			Shooters[i].SetPosition(x, y);
		}
	}
	
	public void Initialise(float radius) {
		Dest[0].Set(0, 0 + radius);
		Dest[1].Set(0 + radius, 0);
		Dest[2].Set(0 - radius, 0);
		Dest[3].Set(0, 0 - radius);
		
		for(int i = 0; i < 4; i++) {
			Shooters[i].initialiseParticles(250, 0, 0);
			Shooters[i].Destination(Dest[i].x(), Dest[i].y());
		}
	}
	
	public void Update() {
		for(int i = 0; i < 4; i++) {
			Shooters[i].Update();
		}
	}
	
	public void Reset() {
		for(int i = 0; i < 4; i++) {
			Shooters[i].Reset();
		}
	}

	public GL_Particles getRaw(int i) {
		return Shooters[i];
	}
}
