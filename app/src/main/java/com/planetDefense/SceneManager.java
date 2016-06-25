package com.planetDefense;

import java.util.Vector;

/**
 * A class that represents the default layout for 
 * a game scene. It is an abstract class just so 
 * each game scene doesnt need to implement every
 * function as sometimes its not required.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class SceneManager {
	/** The singletons instance  */
	private static SceneManager Instance;
	/** A collection to store all scene */
	private Vector<Scene> Scenes;
	/** The first scene which is a loading scene */
	private ISceneLoader First;
	/** Game factory which olds each object is maintained by this class */
	private IFactory Factory;
	/** Just a integer to the current scene in the vector */
	private Integer Current;
	/** A simple boolean to test if the game has loaded */
	private Boolean Loaded;
	
	/**
	 * your typical get function for a singleton.
	 * @return the singletons instance.
	 */
	public static SceneManager get() {
		if(Instance == null) {
			Instance = new SceneManager();
		} return Instance;
	}
	
	/**
	 * private default constructor as this class is a instance.
	 */
	private SceneManager() {
		Scenes = new Vector<Scene>();
		Factory = new Factory();
		Loaded = false;
		Current = 0;
	}
	
	/**
	 * This function simple returns the current state
	 * however if the game hasnt loaded it goes straight
	 * to the loading state and draw what the loading state
	 * asks.
	 * 
	 * @return gets the current state.
	 */
	public Scene GetCurrent() {
		if(this.First != null) {
			if(!First.hasLoaded()) {
				((Scene)First).onEnter(0);
				return (Scene)First;
			} else if(First.hasLoaded() && !Loaded) {
				((Scene)First).onCreate(Factory);
			} 
		} 
		
		if(!Loaded) {
			for(Scene s : Scenes) {
				s.onCreate(Factory);
			} Loaded = true;
			Scenes.get(0).onEnter(0);
		}
		
		if(Current < Scenes.size()) {
			return Scenes.get(Current);
		} return null;
	}
	
	public void setupStates() {
		((Scene)First).onCreate(Factory);
		(First).loaded();
		
		for(Scene s : Scenes) {
			s.onCreate(Factory);
		} 
	}
	
	/**
	 * Creates a scene and stores it in the vector
	 * @param scene
	 */
	public void createScene(Scene scene) {
		if(scene instanceof ISceneLoader) {
			First = (ISceneLoader)scene;
		} else {
			Scenes.add(scene);
		}
	}
	
	/**
	 * Switches the current state to the new one
	 * stored in the vector at point i.
	 * @param i the scene to go to
	 */
	
	private int nextScene = 0;
	public void SwitchTo(int i) {
		nextScene = i;
	}
	
	/** Sets the starting point for the application. */
	public void StartFrom(int i) {
		nextScene = i;
		Current = i;
	}
	
	public Integer getLocation() {
		return Current;
	}

	/** returns the factory variable */
	public IFactory getFactory() {
		return Factory;
	}
	
	/** a function that simply say is that game has loaded */
	public boolean hasLoaded() {
		return Loaded;
	}
	
	public Scene getScene(Integer sceneID) {
		if(sceneID < Scenes.size()) {
			return Scenes.get(sceneID);
		}
		
		return null;
	}

	public void change() {
		if(Current != nextScene) {
			Integer previous = Current;
			Scenes.get(Current).onExit(nextScene);
			Current = nextScene;
			Scenes.get(Current).onEnter(previous);
		}
	}
}
