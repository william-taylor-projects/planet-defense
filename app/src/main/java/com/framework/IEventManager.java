package com.framework;

public interface IEventManager {
    void addListeners(IEventListener[] listeners);
    void addListener(IEventListener listener);
    void removeListener(IEventListener listener);
    void queueEvent(IEvent event, int ms, Object data);
    void triggerEvent(IEvent event, Object data);
    void update();
}
