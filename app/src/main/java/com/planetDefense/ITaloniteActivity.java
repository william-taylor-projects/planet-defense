package com.planetDefense;

/**
 * A simple interface that must be implemented
 * by the main activity before the framework
 * can be used as you add custom scenes by 
 * implementing the SetupStates function.
 * 
 * @version 28.01/2014
 * @author William Taylor
 *
 */
public interface ITaloniteActivity {
	/**
	 * This function is called when new scenes can be 
	 * added to the game after the framework is setup.
	 * 
	 * @param states The statemanager class that allows the user to add states
	 */
	public void setupStates(SceneManager states);
}
