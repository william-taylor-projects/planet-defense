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
import com.planetDefense.events.UsePowerUp;
import java.util.*;

import com.planetDefense.Collision;
import com.planetDefense.EventManager;
import com.planetDefense.activity.MainActivity;
import com.planetDefense.SceneManager;
import com.planetDefense.TaloniteRenderer.RenderQueue;


public class PowerUps {
	private static final Integer NUKE_CHANGES = 1;
	private static final Integer MOVE_CHANGES = 3;
	private static final Integer FAST_CHANGES = 2;
	
	private static final Integer SPAWN_TIMEOUT = 10000;
	private static final Integer SPAWN_TIME = 20000;

	private Vector<PowerUp> Powerups = new Vector<PowerUp>();
	private Random Rand = new Random();
	
	private UsePowerUp CollisionEvent;
	private Collision PowerUpCollision;
	private PowerUp CurrentPowerUp;
	private Timer TimerOutTimer;
	private Boolean TimeOut;
	private Ship Player;
	
	private PowerUp FireRate;
	private PowerUp Move;
	private PowerUp Nuke;
	
	public PowerUps(Ship s, Enemys e) {
		TimeOut = false;
		Player = s;
		
		FireRate = new FastFire();
		Move = new MoveFast(s);
		Nuke = new Nuke(e);
		
		for(int i = 0; i < FAST_CHANGES; i++) {
			Powerups.add(FireRate);
		}
		
		for(int i = 0; i < NUKE_CHANGES; i++) {
			Powerups.add(Nuke);
		}
	
		for(int i = 0; i < MOVE_CHANGES; i++) {
			Powerups.add(Move);
		}
		
		CurrentPowerUp = Powerups.get(Rand.nextInt(Powerups.size()));
		CurrentPowerUp.enable();
		
		CollisionEvent = new UsePowerUp(CurrentPowerUp);
		
		PowerUpCollision = new Collision();
		PowerUpCollision.eventType(CollisionEvent);
		PowerUpCollision.surfaces(Player.GetSprite(), CurrentPowerUp.GetSprite());
		
		EventManager.get().addListener(PowerUpCollision);
	}
	
	public void onEnter() {
		StartTimer();
	}
	
	public void onExit() {
		TimerOutTimer.cancel();
		TimerOutTimer.purge();
	}
	
	private static Integer currentSpawnNumber = 0;
	
	private void StartTimer() {		
		TimerOutTimer = new Timer();
		TimerOutTimer.schedule(new TimerTask() {	
			private Integer localID = 0;
			
			public TimerTask setID(Integer ID) {
				localID = ID;
				return this;
			}
			
			@Override
			public void run() {
				if(SceneManager.get().getLocation() == MainActivity.LEVEL && localID == currentSpawnNumber) {				
					Integer spawn_x = Rand.nextInt(300) + 100; // 200;
					Integer spawn_y = Rand.nextInt(150) + 100;
					
					if(Player.GetDetails().x <= 640) {
						spawn_x = Rand.nextInt(300) + 700;
					}
					
					if(Player.GetDetails().y <= 400) {
						spawn_y = Rand.nextInt(150) + 600;
					}
					
					CurrentPowerUp = Powerups.get(Rand.nextInt(Powerups.size()));
					CurrentPowerUp.DropItem(spawn_x, spawn_y);
					CurrentPowerUp.enable();
					
					CollisionEvent.ChangePowerUp(CurrentPowerUp);
					
					PowerUpCollision.surfaces(Player.GetSprite(), CurrentPowerUp.GetSprite());
				}
			}
		}.setID(++currentSpawnNumber), SPAWN_TIME);
		
		TimerOutTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				CurrentPowerUp.disable();
				if(!CurrentPowerUp.Used()) {
					CurrentPowerUp.Hide();
					TimeOut = true;
				}
			}		
		}, SPAWN_TIME + SPAWN_TIMEOUT);
	}
	
	public void Update() {	
		if(CurrentPowerUp.Used() || TimeOut) {
			TimeOut = false;		
			
			CurrentPowerUp.disable();
			CurrentPowerUp.Hide();
			
			TimerOutTimer.cancel();
			TimerOutTimer.purge();
			
			StartTimer();
		} CurrentPowerUp.Update();
	}
	
	public void Draw() {
		CurrentPowerUp.Draw();
	}

	public void draw(RenderQueue renderList) {
		renderList.pushRenderable(CurrentPowerUp.GetSprite());
	}
}
