package com.planetDefense;

import com.planetDefense.TaloniteRenderer.RenderQueue;

import android.view.MotionEvent;

/**
 * A class that represents the default layout for 
 * a game scene. It is an abstract class just so 
 * each game scene doesnt need to implement every
 * function as sometimes its not required.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public abstract class Scene {
	/**
	 * A simple onTouch function that will send
	 * the relevant data to the scene.
	 * 
	 * @param e The motion event class provided by android
	 * @param x The x position of the touch (Relative to the screen size)
	 * @param y The y position of the touch (Relative to the screen size)
	 */
	public void onTouch(MotionEvent e, int x, int y) {
		;
	}
	
	public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){}
	
	/**
	 * A function that is called when the scene is created
	 * 
	 * @param factory Represents a factory that holds all game objects.
	 */
	public void onCreate(IFactory factory) {}
	
	/** A function that is called when the scene needs to be rendered. */
	public void onRender(RenderQueue renderList) {}
	
	/** A function that is called when the scene is updated */
	public void onUpdate() {}
	
	/** Called when the user navigates to this scene */
	public void onEnter(Integer previousState) {}
	
	/** Called when the user exits the scene */
	public void onExit(Integer nextState) {}

	public void onLongPress(MotionEvent e, int x, int y) {}
}
