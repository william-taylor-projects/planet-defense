package com.planetDefense;

import java.io.InputStream;
import java.util.Scanner;

import android.content.res.AssetManager;
import android.content.Context;

/**
 * This is a resource manager that should be used
 * to grab files in the assets folder.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class ResourceManager {
	/** The singleton's instance*/
	private static ResourceManager Instance;
	/** The game context. */
	private static Context GameContext;
	
	/**
	 * Static function for access the singleton
	 * @return the singletons instance.
	 */
	public static ResourceManager Get() {
		if(Instance == null) {
			Instance = new ResourceManager();
		} return Instance;
	}
	
	/**
	 * A function that returns a certain resource.
	 * @param filename the location.
	 * @return the file as a inputstream
	 */
	public InputStream GetResource(String filename) {
		AssetManager manager = GameContext.getAssets();
		InputStream stream = null;
		try {
			stream = manager.open(filename);
		} catch (Exception e) {
			System.out.println(e.toString());
		} return stream;
	}
	
	/**
	 * A function that returns a file that will 
	 * allow the user to grab data line by line.
	 * 
	 * @param filename the location of the file.
	 * @return the file as a scanner.
	 */
	public Scanner GetFile(String filename) {
		Scanner scanner = null;
		try {
			scanner =  new Scanner(GetResource(filename));
		}  catch (Exception e) {
			System.out.println(e.toString());
		} return scanner;
	}
	
	/**
	 * Initialises the resourse manager.
	 * @param c the applications context
	 */
	public void Initialise(Context c) {
		GameContext = c;
	}
	
	/**
	 * a get function to get the android apps context
	 * @return the apps contect.
	 */
	public Context GetContext() {
		return GameContext;
	}
}
