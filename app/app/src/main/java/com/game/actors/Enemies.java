package com.game.actors;

import com.framework.IFactory;
import com.framework.graphics.RenderQueue;

import java.util.TimerTask;
import java.util.Timer;

public class Enemies {
	public static Integer STARTING_ENEMIES = 5;
	public static Integer INITIAL_DELAY = 3000;
	public static Integer INCREMENT_VALUE = 5;
	public static Integer DELAY = 400;
	public static Integer MAX = 150;
	
	private Timer spawnTimer = new Timer();
	private Boolean shouldSpawn;
    private Integer spawnCount;
	private Enemy[] enemies;
	
	public Enemies() {
		Enemy.KILL_COUNT = 0;
		spawnCount = 0;
	}
	
	public void initialise(IFactory factory) {
		enemies = new Enemy[MAX];
		for(int i = 0; i < MAX; i++) {
			enemies[i] = new Enemy();
			enemies[i].initialise(factory);
		} 
		
		spawnTimer.schedule(new SpawnEvent(), INITIAL_DELAY);
	}
	
	public int getNumberOfEnemiesKilled() {
		return Enemy.KILL_COUNT;
	}
	
	public void killEnemies() {
		for(int i = 0; i < spawnCount; i++) {
			if(enemies[i].hasSpawned()) {
				enemies[i].explode();
				enemies[i].resetObject();
			}
		}
	}

	public void update() {
		if(shouldSpawn) {
			if(spawnCount < MAX && spawnCount < STARTING_ENEMIES) {
				if(!enemies[spawnCount].hasSpawned()) {
					enemies[spawnCount].spawn(spawnCount);
					spawnTimer.schedule(new SpawnEvent(), DELAY);
					++spawnCount;
				}
			} shouldSpawn = false;
		}
		
		for(int i = 0; i < spawnCount; i++) {
			enemies[i].setSpeed(Enemy.SPEED);
			enemies[i].update();
		}
	}

	public Enemy get(int i) {
		return enemies[i];
	}
	
	public int getSpawns() {
		return spawnCount;
	}

	public void resetObject() {
		for(int i = 0; i < spawnCount; i++) {
			enemies[i].resetObject();
		}
		
		Enemy.KILL_COUNT = 0;
		spawnCount = 0;
		spawnTimer.schedule(new SpawnEvent(), INITIAL_DELAY);
	}

	public void draw(RenderQueue renderList) {
		for(int i = 0; i < spawnCount; i++) {
			enemies[i].draw(renderList);
		} 
	}

    private class SpawnEvent extends TimerTask {
        @Override
        public void run() {
            shouldSpawn = true;
        }
    }
}