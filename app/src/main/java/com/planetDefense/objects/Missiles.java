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

import java.util.TimerTask;
import java.util.Timer;

import com.planetDefense.events.BulletCollision;
import android.view.MotionEvent;

public class Missiles {	
	public static Integer ORIGINAL = 500;
	public static Integer DELAY = 500;
	public static Integer COUNT = 10;
	
	private BulletCollision CollisionEvent = new BulletCollision();
	private Collision[] EnemyCollisions;
	
	private Missile[] Missiles = new Missile[COUNT];
	public static Vector2D LaunchPos = new Vector2D();
	private Enemys Enemys;
	private Ship Player;
	
	private Boolean Fire = false;
	private Integer Current = 0;

	public void Initialise(IFactory factory) {
		Enemys = factory.Request("Enemys");
		Player = factory.Request("Ship");
		StartTimer(DELAY);
		
		for(int i = 0; i < COUNT; i++) {
			Missiles[i] = new Missile(Player);
		} 
		
		EnemyCollisions = new Collision[com.planetDefense.objects.Enemys.MAX];
		for(int i = 0; i < EnemyCollisions.length; i++) {
			EnemyCollisions[i] = new Collision();
			EnemyCollisions[i].eventType(this.CollisionEvent);			
		}
	}
	
	public GL_Image GetMissile(int i) {
		return Missiles[i].getSprite();
	}
	
	public void ResetMissile(int i) {
		Missiles[i].Reset();
	}
	
	public void LaunchMissile(float x, float y) {
		LaunchPos.Set(x, y);
		Fire = true;
	}
	
	public void Update() {
		Integer Spawns = Enemys.getSpawns();
		EventManager manager = EventManager.get();
		for(int i = 0; i < COUNT; i++) {			
			if(Missiles[i].hasFired()) {
				for(int c = 0; c < Spawns; c++) {
					Enemy e = Enemys.get(c);
				
					if(e.hasSpawned()) {
						CollisionEvent.provideData(this, e, i, this.Player);
						
						EnemyCollisions[c].surfaces(Missiles[i].getSprite(), e.getSprite());
						EnemyCollisions[c].check(manager);
					}
				}
			}
			
			Missiles[i].Update();
		}
	}

	public void OnTouch(MotionEvent e, float x, float y) {
		Integer ID = e.getAction();
		LaunchPos.Set(x, y);
		if(ID == MotionEvent.ACTION_MOVE || ID == MotionEvent.ACTION_DOWN) {
			LaunchMissile(x, y);
		} else {
			Fire = false;
		}
	}

	public Object GetRawObject() {
		return this.Missiles;
	}
	
	private void StartTimer(int ms) {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(Fire && Player.shotsEnabled()) {	
					Missiles[Current].Fire();
				
					if(++Current >= COUNT) {
						Current = 0;
					} 
					
					Missiles[Current].Reset();
					Fire = false;
				}
				
				StartTimer(DELAY);
			}
		}, DELAY);
	}

	public void ResetObject() {
		for(int i = 0; i < COUNT; i++) {
			Missiles[i].Reset();
		}
	}

	public void draw(RenderQueue renderList) {
		if(this.Player.isAlive()) {
			for(int i = 0; i < COUNT; i++) {
				if(Missiles[i].hasFired()) {
					renderList.pushRenderable(Missiles[i].getSprite());
				}
			}
		}
	}
}