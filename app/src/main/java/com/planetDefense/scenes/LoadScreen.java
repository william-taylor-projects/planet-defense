package com.planetDefense.scenes;

import com.framework.IFactory;
import com.framework.ISceneLoader;
import com.framework.core.Scene;
import com.framework.graphics.Image;
import com.framework.graphics.RenderQueue;

public class LoadScreen extends Scene implements ISceneLoader {
	private static final String SPLASH_FILENAME = "sprites/splash.bmp";
	private static final Float ALPHA_START = 0F;
	private static final Float APLHA_END = 1F;

	private LoadingProcess loader;
	private Image splash;
	private Float alpha = ALPHA_START;

	@Override
	public void onCreate(IFactory factory) {
		loader = new LoadingProcess(factory);
		loader.setupAssets();
	}

	@Override
	public void onEnter(Integer nextScene) {
		if(splash == null) {
			splash = new Image(SPLASH_FILENAME);
			splash.setPosition(0, 0, 1280, 800); 
		}
	}

	@Override
	public void onUpdate() {
		splash.shade(1F, 1F, 1F, alpha);
		splash.update();

		alpha += 0.1F;
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(splash);
	}

	@Override
	public boolean hasLoaded() {
		return(alpha >= APLHA_END);
	}

	public void loaded() {
		alpha = 100.0F;
	}
}
