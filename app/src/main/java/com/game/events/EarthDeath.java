package com.game.events;

import com.framework.IEvent;
import com.game.actors.Earth;
import com.game.actors.Ship;

public class EarthDeath implements IEvent {
    private Earth earth;
    private Ship ship;

    public EarthDeath(Earth earth, Ship ship) {
        this.earth = earth;
        this.ship = ship;
    }

    @Override
    public void onActivate(Object data) {
        earth.kill();
        ship.kill();
    }

    @Override
    public void update() {

    }
}
