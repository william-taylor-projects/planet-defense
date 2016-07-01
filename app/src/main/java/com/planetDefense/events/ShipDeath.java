package com.planetDefense.events;

import com.framework.IEvent;
import com.framework.core.SceneManager;
import com.planetDefense.activity.MainActivity;
import com.planetDefense.objects.*;


public class ShipDeath implements IEvent {
	private static final SceneManager scenes;
	private static final Statistics stats;

	private Ship player;

	static {
		scenes = SceneManager.get();
		stats = Statistics.get();
	}

	public ShipDeath(Ship player) {
		this.player = player;
	}

	@Override
	public void onActivate(Object data) {
		player.Repair();
		scenes.switchTo(MainActivity.GAMEOVER);
		stats.gamePlayed();	
	}

	@Override
	public void update() {

	}
}
