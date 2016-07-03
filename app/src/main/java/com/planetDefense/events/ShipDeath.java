package com.planetDefense.events;

import com.framework.IEvent;
import com.framework.core.SceneManager;
import com.planetDefense.activity.MainActivity;
import com.planetDefense.actors.Ship;
import com.planetDefense.common.Statistics;

public class ShipDeath implements IEvent {
    private static final SceneManager scenes = SceneManager.get();
    private static final Statistics stats = Statistics.get();
    private Ship player;

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
