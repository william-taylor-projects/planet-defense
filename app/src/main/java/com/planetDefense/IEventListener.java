package com.planetDefense;

public interface IEventListener {
	public void check(IEventManager manager);
	public void eventType(IEvent event);
}
