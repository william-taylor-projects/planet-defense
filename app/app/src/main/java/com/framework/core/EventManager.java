package com.framework.core;

import java.util.*;

import com.framework.IEvent;
import com.framework.IEventListener;
import com.framework.IEventManager;

public class EventManager implements IEventManager {
    private static EventManager instance;
    private List<IEventListener> listeners;

    private EventManager() {
        listeners = new ArrayList<IEventListener>();
    }

    public static EventManager get() {
        if(instance == null) {
            instance = new EventManager();
        } return instance;
    }

    @Override
    public void addListener(IEventListener event) {
        listeners.add(event);
    }

    @Override
    public void removeListener(IEventListener event) {
        for(int i = 0; i < listeners.size(); i++) {
            IEventListener observer = listeners.get(i);
            if(event.equals(observer)) {
                listeners.remove(i);
            }
        }
    }

    @Override
    public void triggerEvent(IEvent event, Object data) {
        if(event != null) {
            event.onActivate(data);
        }
    }

    @Override
    public void update() {
        for(int i = 0; i < listeners.size(); i++) {
            listeners.get(i).check(this);
        }
    }

    @Override
    public void addListeners(IEventListener[] event) {
        for(IEventListener e : event) {
            addListener(e);
        }
    }

    @Override
    public void queueEvent(IEvent event, int ms, Object data) {
        new Timer().schedule(new TimerTask() {
            private IEvent e;
            private Object d;

            public TimerTask e(IEvent e, Object data) {
                this.e = e;
                this.d = data;
                return this;
            }

            @Override
            public void run() {
                e.onActivate(d);
            }

        }.e(event, data), ms);
    }
}
