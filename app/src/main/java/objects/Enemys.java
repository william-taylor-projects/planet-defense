package objects;

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
import com.planetDefense.*;

import java.util.TimerTask;
import java.util.Timer;


public class Enemys {		
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
	
	public Enemys() {
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