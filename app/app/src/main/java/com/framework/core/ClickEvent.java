package com.framework.core;

import android.util.Log;
import android.view.MotionEvent;

import com.framework.IEvent;
import com.framework.IEventListener;
import com.framework.IEventManager;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;

public class ClickEvent implements IEventListener {
    private com.framework.graphics.Button button;
    private MotionEvent holdEvent;
    private MotionEvent motionEvent;
    private IEvent event;
    private float x = 0;
    private float y = 0;

    public ClickEvent(com.framework.graphics.Button button) {
        this.button = button;
    }

    public void OnTouch(MotionEvent e, float x, float y) {
        this.motionEvent = e;
        this.x = x;
        this.y = y;
    }

    @Override
    public void check(IEventManager manager) {
        event.update();

        OpenglImage sprite = button.getImage();

        if(sprite != null)
        {
            Vector2 Position = sprite.getPosition();
            Vector2 Size = sprite.getSize();

            if(motionEvent != null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if(x >= Position.getX() && x <= Position.getX() + Size.getX()) {
                    if(y >= Position.getY() && y <= Position.getY() + Size.getY()) {
                        manager.triggerEvent(event, false);
                        OnTouch(null, -1.0f, -1.0f);
                    }
                }
            }

            if(holdEvent != null && holdEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if(x >= Position.getX() && x <= Position.getX() + Size.getX()) {
                    if(y >= Position.getY() && y <= Position.getY() + Size.getY()) {
                        manager.triggerEvent(event, true);
                        onLongPress(null, -1, -1);
                    }
                }
            }
        }
    }

    @Override
    public void eventType(IEvent event) {
        if(event == null) {
            Log.e("Error", "null event");
        } else {
            this.event = event;
        }
    }

    public void onLongPress(MotionEvent e, int x2, int y2) {
        this.holdEvent = e;
        this.x = x2;
        this.y = y2;
    }
}
