package com.planetDefense.actors;

import com.framework.IFactory;
import com.framework.core.CollisionEvent;
import java.util.TimerTask;
import java.util.Timer;

import com.framework.core.EventManager;
import com.framework.graphics.RenderQueue;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.planetDefense.events.BulletCollision;
import android.view.MotionEvent;

public class Missiles {
	public static Vector2 LaunchPos = new Vector2();
	public static Integer ORIGINAL = 500;
	public static Integer DELAY = 500;
	public static Integer COUNT = 10;

	private CollisionEvent[] enemyCollisionEvents;
	private BulletCollision collectionEvent;
	private Missile[] missiles;
	private Enemies enemies;
	private Ship player;

    private Integer current = 0;
	private Boolean fire = false;

	public void initialise(IFactory factory) {
		enemies = factory.request("enemies");
		player = factory.request("Ship");
        missiles = new Missile[COUNT];
		for(int i = 0; i < COUNT; i++) {
			missiles[i] = new Missile(player);
		}

        collectionEvent = new BulletCollision();
		enemyCollisionEvents = new CollisionEvent[Enemies.MAX];
		for(int i = 0; i < enemyCollisionEvents.length; i++) {
			enemyCollisionEvents[i] = new CollisionEvent();
			enemyCollisionEvents[i].eventType(this.collectionEvent);
		}

        startTimer(DELAY);
	}
	
	public OpenglImage getMissile(int i) {
		return missiles[i].getMissileImage();
	}
	
	public void resetMissile(int i) {
		missiles[i].reset();
	}
	
	public void launchMissile(float x, float y) {
		LaunchPos.set(x, y);
		fire = true;
	}
	
	public void update() {
		Integer Spawns = enemies.getSpawns();
		EventManager manager = EventManager.get();
		for(int i = 0; i < COUNT; i++) {			
			if(missiles[i].hasFired()) {
				for(int c = 0; c < Spawns; c++) {
					Enemy e = enemies.get(c);
				
					if(e.hasSpawned()) {
						collectionEvent.provideData(this, e, i, this.player);
						
						enemyCollisionEvents[c].surfaces(missiles[i].getMissileImage(), e.getSprite());
						enemyCollisionEvents[c].check(manager);
					}
				}
			}

			missiles[i].update();
		}
	}

	public void onTouch(MotionEvent e, float x, float y) {
		Integer ID = e.getAction();
		LaunchPos.set(x, y);
		if(ID == MotionEvent.ACTION_MOVE || ID == MotionEvent.ACTION_DOWN) {
			launchMissile(x, y);
		} else {
			fire = false;
		}
	}
	
	private void startTimer(int ms) {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(fire && player.shotsEnabled()) {
					missiles[current].fire();
				
					if(++current >= COUNT) {
                        current = 0;
					} 
					
					missiles[current].reset();
					fire = false;
				}
				
				startTimer(DELAY);
			}
		}, DELAY);
	}

	public void resetObject() {
		for(int i = 0; i < COUNT; i++) {
			missiles[i].reset();
		}
	}

	public void draw(RenderQueue renderList) {
		if(this.player.isAlive()) {
			for(int i = 0; i < COUNT; i++) {
				if(missiles[i].hasFired()) {
					renderList.put(missiles[i].getMissileImage());
				}
			}
		}
	}
}