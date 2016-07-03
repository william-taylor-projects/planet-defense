package com.planetDefense.events;

import com.framework.IEvent;
import com.framework.opengl.OpenglImage;
import com.planetDefense.actors.Enemy;
import com.planetDefense.actors.Explosion;
import com.planetDefense.actors.Missiles;
import com.planetDefense.actors.Ship;
import com.planetDefense.common.Statistics;

public class BulletCollision implements IEvent {
    private Explosion explosion;
    private Missiles missiles;
    private Integer number;
    private Enemy enemy;
    private Ship ship;

    public void provideData(Missiles missiles, Enemy enemy, Integer number, Ship ship) {
        this.missiles = missiles;
        this.number = number;
        this.enemy = enemy;
        this.ship = ship;
    }

    @Override
    public void onActivate(Object data) {
        if(missiles != null && enemy != null && ship != null) {
            OpenglImage sprite = missiles.getMissile(number);

            float x = sprite.getPosition().getX() + sprite.getSize().getX()/2;
            float y = sprite.getPosition().getY() + sprite.getSize().getY()/2;

            explosion = enemy.getEffect();
            explosion.drateAt(x, y);
            explosion.reset();

            this.missiles.resetMissile(number);
            this.enemy.resetObject();
            this.ship.addCash();

            Statistics.get().enemyDown();
            sprite.reset();
        }
    }

    @Override
    public void update() {
        ;
    }
}
