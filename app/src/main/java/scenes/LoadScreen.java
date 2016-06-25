package scenes;

/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty. 
 *  In no event will the authors be held liable for any damages arising from 
 *  the use of this software. Permission is granted to anyone to use this 
 *  software for any purpose, including commercial applications, and to 
 *  alter it and redistribute it freely, subject to the following 
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented; 
 *     you must not claim that you wrote the original software. 
 *	   If you use this software in a product, an acknowledgment 
 *     in the product documentation would be appreciated 
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, 
 *     and must not be misrepresented as being the original 
 *     software.
 *  
 *  3. This notice may not be removed or altered 
 *     from any source distribution.
 *     
 */
import com.planetDefense.TaloniteRenderer.RenderQueue;
import com.planetDefense.*;

/**
 *  The load screen state that simply displays
 *  a logo until all shared assets have 
 *  been loaded
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class LoadScreen extends Scene implements ISceneLoader {
	/** Some final settings that can be change for the application */
	private static final String SPLASH_FILENAME = "sprites/splash.bmp";
	private static final Float ALPHA_START = 0F;
	private static final Float APLHA_END = 1F;
	
	/** A reference to the class that manages the loading */
	private LoadingProcess loader;
	
	/** A reference to the splash screen logo */
	private GameImage splash;
	
	/** The alpha value for the texture */
	private Float alpha = ALPHA_START;

	/** If the scene is a instance of ISceneLoader this is called after hasLoaded has returned true  */
	@Override
	public void onCreate(IFactory factory) {
		loader = new LoadingProcess(factory);
		loader.setupAssets();
	}

	/** onEnter is called constantly is the scene implements ISceneLoader */
	@Override
	public void onEnter(Integer nextScene) {
		/** Initialise the splash texture one */
		if(splash == null) {
			splash = new GameImage(SPLASH_FILENAME);
			splash.setPosition(0, 0, 1280, 800); 
		}
	}

	/** Called when the scene is to be updated */
	@Override
	public void onUpdate() {
		/** Set the textures colour value */
		splash.shade(1F, 1F, 1F, alpha);
		splash.update();
		
		/** Increment the alpha value */
		alpha += 0.1F;
	}
	
	/** Called when the scene is to be rendered */
	@Override
	public void onRender(RenderQueue renderList) {
		renderList.pushRenderable(splash);
	}
	
	/** Called to check is the scene is ready to load all scenes/assets */
	@Override
	public boolean hasLoaded() {
		/** When the image is fully visible start loading */
		return(alpha >= APLHA_END);
	}

	@Override
	public void loaded() {
		alpha = 100.0F;
	}
}
