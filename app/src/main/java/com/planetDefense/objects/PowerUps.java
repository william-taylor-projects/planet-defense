package com.planetDefense.objects;

import com.framework.core.CollisionEvent;
import com.framework.core.EventManager;
import com.framework.core.SceneManager;
import com.framework.graphics.RenderQueue;
import com.planetDefense.events.UsePowerUp;
import java.util.*;
import com.planetDefense.activity.MainActivity;

public class PowerUps {
	private static final Integer NUKE_CHANGES = 1;
	private static final Integer MOVE_CHANGES = 3;
	private static final Integer FAST_CHANGES = 2;
	
	private static final Integer SPAWN_TIMEOUT = 10000;
	private static final Integer SPAWN_TIME = 20000;

	private Vector<PowerUp> Powerups = new Vector<PowerUp>();
	private Random Rand = new Random();
	
	private UsePowerUp userPowerUp;
	private CollisionEvent PowerUpCollision;
	private PowerUp CurrentPowerUp;
	private Timer TimerOutTimer;
	private Boolean TimeOut;
	private Ship Player;
	
	private PowerUp FireRate;
	private PowerUp Move;
	private PowerUp Nuke;
	
	public PowerUps(Ship s, Enemies e) {
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
		
		userPowerUp = new UsePowerUp(CurrentPowerUp);
		
		PowerUpCollision = new CollisionEvent();
		PowerUpCollision.eventType(userPowerUp);
		PowerUpCollision.surfaces(Player.GetSprite(), CurrentPowerUp.getSprite());
		
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
					CurrentPowerUp.dropItem(spawn_x, spawn_y);
					CurrentPowerUp.enable();
					
					userPowerUp.ChangePowerUp(CurrentPowerUp);
					
					PowerUpCollision.surfaces(Player.GetSprite(), CurrentPowerUp.getSprite());
				}
			}
		}.setID(++currentSpawnNumber), SPAWN_TIME);
		
		TimerOutTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				CurrentPowerUp.disable();
				if(!CurrentPowerUp.used()) {
					CurrentPowerUp.hide();
					TimeOut = true;
				}
			}		
		}, SPAWN_TIME + SPAWN_TIMEOUT);
	}
	
	public void Update() {	
		if(CurrentPowerUp.used() || TimeOut) {
			TimeOut = false;		
			
			CurrentPowerUp.disable();
			CurrentPowerUp.hide();
			
			TimerOutTimer.cancel();
			TimerOutTimer.purge();
			
			StartTimer();
		} CurrentPowerUp.update();
	}
	
	public void Draw() {
		CurrentPowerUp.draw();
	}

	public void draw(RenderQueue renderList) {
		renderList.put(CurrentPowerUp.getSprite());
	}
}
