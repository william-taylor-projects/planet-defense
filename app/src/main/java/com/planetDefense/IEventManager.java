package com.planetDefense;

public interface IEventManager {
	public void addListeners(IEventListener[] event);
	public void addListener(IEventListener event);
	public void removeListener(IEventListener event);
	public void queueEvent(IEvent event, int ms, Object data);
	public void triggerEvent(IEvent event, Object data);
	public void update();
}
