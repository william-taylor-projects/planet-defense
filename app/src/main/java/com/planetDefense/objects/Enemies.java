package com.planetDefense.objects;

import com.framework.IFactory;
import com.framework.graphics.RenderQueue;

import java.util.TimerTask;
import java.util.Timer;

public class Enemies {
	public static Integer INITIAL_DELAY = 3000;
	public static Integer INCREMENT_VALUE = 5;
	public static Integer STARTING_ENEMYS = 5;	
	public static Integer DELAY = 400;
	public static Integer MAX = 150;

	private class SpawnEvent extends TimerTask {
		@Override
		public void run() {
			Spawn = true;
		}
	}
	
	private Timer SpawnTimer = new Timer();
	private Integer SpawnCount;
	private Boolean Spawn = false;
	private Enemy[] Enemys;
	
	public Enemies() {
		Enemy.KILL_COUNT = 0;
		SpawnCount = 0;
	}
	
	public void Initialise(IFactory factory) {
		Enemys = new Enemy[MAX];
		for(int i = 0; i < MAX; i++) {
			Enemys[i] = new Enemy();
			Enemys[i].initialise(factory);
		} 
		
		SpawnTimer.schedule(new SpawnEvent(), INITIAL_DELAY);
	}
	
	public int GetNumberOffKills() {
		return Enemy.KILL_COUNT;
	}
	
	public void KillEnemys() {
		for(int i = 0; i < SpawnCount; i++) {
			if(Enemys[i].hasSpawned()) {
				Enemys[i].explode();
				Enemys[i].resetObject();
			}
		}
	}

	public void Update() {		
		if(Spawn) {
			if(SpawnCount < MAX && SpawnCount < STARTING_ENEMYS) {
				if(!Enemys[SpawnCount].hasSpawned()) {
					Enemys[SpawnCount].spawn(SpawnCount);
					SpawnTimer.schedule(new SpawnEvent(), DELAY);
					++SpawnCount;
				}
			} Spawn = false;
		}
		
		for(int i = 0; i < SpawnCount; i++) {
			Enemys[i].setSpeed(Enemy.SPEED);
			Enemys[i].update();
		}
	}
	
	public Object GetRawObject() {
		return this.Enemys;
	}
	
	public Enemy get(int i) {
		return Enemys[i];
	}
	
	public int getSpawns() {
		return SpawnCount;
	}

	public void ResetObject() {
		for(int i = 0; i < SpawnCount; i++) {
			Enemys[i].resetObject();
		}
		
		Enemy.KILL_COUNT = 0;
		SpawnCount = 0;
		
		SpawnTimer.schedule(new SpawnEvent(), INITIAL_DELAY);
	}

	public void draw(RenderQueue renderList) {
		for(int i = 0; i < SpawnCount; i++) {
			Enemys[i].Draw(renderList);
		} 
	}
}