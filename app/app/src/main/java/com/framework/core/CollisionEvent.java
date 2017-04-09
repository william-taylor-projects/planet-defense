package com.framework.core;

import com.framework.IEvent;
import com.framework.IEventListener;
import com.framework.IEventManager;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;

public class CollisionEvent implements IEventListener {
    public interface CollisionArray {
        void collisionID(Integer num);
    }

    private OpenglImage obj1;
    private OpenglImage obj2;
    private IEvent event;
    private int Number;

    public CollisionEvent() {
        Number = -1;
    }

    public void surfaces(OpenglImage i, OpenglImage b, int num) {
        Number = num;
        obj1 = i;
        obj2 = b;
    }

    public void surfaces(OpenglImage i, OpenglImage b) {
        obj1 = i;
        obj2 = b;
    }

    @Override
    public void check(IEventManager manager) {
        Vector2 posTwo = obj1.getPosition();
        Vector2 posOne = obj2.getPosition();
        Vector2 szTwo = obj1.getSize();
        Vector2 szOne = obj2.getSize();

        float x2 = posTwo.getX();
        float x1 = posOne.getX();
        float w2 = (x2 + szTwo.getX());
        float w1 = (x1 + szOne.getX());

        if(x1 <= w2 && w1 >= x2) {

            float y2 = posTwo.getY();
            float h2 = (y2 + szTwo.getY());
            float y1 = posOne.getY();
            float h1 = (y1 + szOne.getY());

            if(y1 <= h2 && h1 >= y2) {
                if(this.event instanceof CollisionArray) {
                    ((CollisionArray)event).collisionID(Number);
                }

                manager.triggerEvent(event, null);
            }
        }
    }

    @Override
    public void eventType(IEvent event) {
        this.event = event;
    }
}
