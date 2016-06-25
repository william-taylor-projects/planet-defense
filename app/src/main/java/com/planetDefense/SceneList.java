package com.planetDefense;

import android.view.MotionEvent;

/**
 * A abstract class that sets out a layout for a collection 
 * of scene objects to make a scenes code simpler and
 * interaction with a scenes objects.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public abstract class SceneList {	
	/**
	 * A simple onTouch function that will send
	 * the relevant data to the scenelist.
	 * 
	 * @param e The motion event class provided by android
	 * @param x The x position of the touch (Relative to the screen size)
	 * @param y The y position of the touch (Relative to the screen size)
	 */
	public void onTouch(MotionEvent e, int x, int y) {}	
	
	/**
	 * A function that should be called to initialise the scene object
	 * @param factory Represents a factory that holds all game objects.
	 */
	public abstract void Initialise(IFactory factory);
	
	/** a function that should be called by the scene when its entered */
	public void onEnter() {}
	
	/** a function that should be called by the scene when its exited */
	public void onExit() {}
	
	/**	a function that should be called when the scene is updated	*/
	public abstract void Update();
}
