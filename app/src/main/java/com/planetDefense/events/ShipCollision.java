package com.planetDefense.events;

import java.util.TimerTask;
import java.util.Timer;

import com.framework.IEvent;
import com.framework.core.CollisionEvent;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.planetDefense.actors.Explosion;
import com.planetDefense.actors.Enemies;
import com.planetDefense.actors.Ship;

public class ShipCollision implements IEvent, CollisionEvent.CollisionArray {
    private static final Integer TIMEOUT = 750;
    private Explosion effect;
    private Enemies enemy;
    private Ship ship;
    private int num;

    public ShipCollision(Enemies enemy, Ship ship) {
        this.enemy = enemy;
        this.ship = ship;
    }

    @Override
    public void onActivate(Object data) {
        if(enemy != null && ship != null) {
            final OpenglImage enemyImage = (OpenglImage)enemy.get(num).getRawObject();
            final OpenglImage shipImage = (OpenglImage)ship.getRawObject();

            Vector2 p = enemyImage.getPosition();
            Vector2 s = enemyImage.getSize();

            effect = enemy.get(num).getEffect();
            effect.drateAt(p.getX() + s.getX() / 2, p.getY() + s.getY() / 2);
            effect.reset();

            shipImage.setShade(1f, 0.1f, 0.1f, 1f);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    shipImage.setShade(1f, 1f, 1f, 1f);
                }
            }, TIMEOUT);

            enemy.get(num).resetObject();
            ship.takeDamage();
        }
    }

    @Override
    public void collisionID(Integer ID) {
        this.num = ID;
    }

    @Override
    public void update() {

    }
}
