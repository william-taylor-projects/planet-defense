package com.planetDefense;

/**
 * A interface that must be implemented when 
 * creating a load scene.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public interface ISceneLoader {
	/** function must be implemented so the library knows when to start loading assets */
	public boolean hasLoaded() ;

	public void loaded();
}
