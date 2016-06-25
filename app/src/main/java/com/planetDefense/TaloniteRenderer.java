package com.planetDefense;

import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

/**
 * Simple renderer class that handles all graphic
 * events and clears the screen when needed and
 * calls scene function when certain messages 
 * are recieved.
 * 
 * @version 28/01/2014
 * @author William Taylor
 */
public class TaloniteRenderer implements Renderer {	
	public class RenderQueue {
		private Vector<GL_Particles> particles;
		private Vector<GL_Image> sprites;
		private Vector<GL_Text> strings;
		private Vector<GL_Line> lines;
		
		public GL_Image background;
		public GL_Image header;
		
		public RenderQueue() {
			particles = new Vector<GL_Particles>();
			sprites = new Vector<GL_Image>();
			strings = new Vector<GL_Text>();
			lines = new Vector<GL_Line>();
		}

		public GL_Image getTop() {
			return header;
		}
		
		public void pushRenderableTop(GL_Image sprite) {
			header = sprite;
		}
		
		public void pushBackground(GL_Image background) {
			this.background = background;
		}
		
		public void pushEffect(GL_Particles effect) {
			particles.add(effect);
		}
		
		public void pushRenderable(Object renderable) {
			if(renderable != null) {
				if(renderable instanceof GameImage) {
					GL_Image raw = (GL_Image)((GameImage)renderable).getRawObject();
					sprites.add(raw);
				}
				
				if(renderable instanceof LabelParagraph) {
					LabelParagraph paragraph = (LabelParagraph)renderable;
					for(int i = 0; i < paragraph.getLineNumber(); i++) {
						pushRenderable(paragraph.getLine(i));
					}
				}
				
				if(renderable instanceof GL_Line) {
					lines.add(((GL_Line)renderable));
				}
				
				if(renderable instanceof GL_Image) {
					sprites.add((GL_Image)(renderable));
				}
				
				if(renderable instanceof GL_Text) {
					strings.add((GL_Text)(renderable));
				}
				
				if(renderable instanceof GameButton) {
					pushRenderable((GL_Image)((GameButton)renderable).getImage());
					pushRenderable((GL_Text)((GameButton)renderable).getText());
				}
				
				if(renderable instanceof LabelLine) {
					GL_Text raw = (GL_Text)((LabelLine)renderable).GetRawGL();
					strings.add(raw);
				}
			}
			
		}
		
		public void pushString(GL_Text string) {
			if(string != null && string.isVisible()) {
				strings.add(string);
			}
		}
		
		public void clear() {
			header = null;
			particles.clear();
			sprites.clear();
			strings.clear();
			lines.clear();
		}
		
		public Vector<GL_Line> getLines() {
			return lines;
		}
		
		public Vector<GL_Image> getSprites() {
			return sprites;
		}
		
		public Vector<GL_Text> getStrings() {
			return strings;
		}

		public Vector<GL_Particles> getEffects() {
			return particles;
		}
	}
	
	private EventManager eventManager;
	private SceneManager sceneManager;
	private RenderQueue renderList;
	private static Integer launchCount = 0;
	
	/**
	 * Basic Constructor
	 */
	public TaloniteRenderer() {
		eventManager = EventManager.get();
		sceneManager = SceneManager.get();
		renderList = new RenderQueue();	
	}
	
	/**
	 *	Starts the game and get all info from the android
	 *	activity.
	 *
	 * @param gl Deprecated part of opengl es 1
	 */
	@Override
	public void onDrawFrame(GL10 arg0) {		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		eventManager.update();
		sceneManager.change();
		
		Scene currentScene = sceneManager.GetCurrent();
		
		if(currentScene != null) {
			currentScene.onUpdate();		
			currentScene.onRender(renderList);	
		}
		
		renderImages();
		renderEffect();
		renderText();
		
		renderList.clear();
	}
	
	/**
	 *	Initialises gl features that will be needed activity.
	 *
	 * @param config relevant surface info not needed.
	 * @param gl Deprecated part of opengl es 1
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {			
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);	
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		if(launchCount == 2) {
			System.exit(0);
		} else {		
			++launchCount;
		}
	}
	
	/**
	 *	Handles if the surface changes in anyway.
	 *
	 * @param gl Deprecated part of opengl es 1
	 * @param height New surface heght
	 * @param width New surface width
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	}
	
	private void renderEffect() {
		Vector<GL_Particles> effects = renderList.getEffects();
		if(!effects.isEmpty()) {
			GL_Particles start = effects.get(0);
			start.startRender();
			
			for(int i = 0; i < effects.size(); i++) {
				effects.get(i).render();
			}
			
			start.endRender();
		}
	}
	
	private void renderImages() {
		Vector<GL_Image> sprites = renderList.getSprites();
		if(!sprites.isEmpty()) {
			GL_Image sprite = sprites.get(0);
			
			if(renderList.background != null) {
				renderList.background.render();
			}
			
			Vector<GL_Line> lines = renderList.getLines();
			if(!lines.isEmpty()) {			
				for(int i = 0; i < lines.size(); i++) {
					lines.get(i).Draw();
				}
			}
			
			sprite.beginProgram();
			
			for(int i = 0; i < sprites.size(); i++) {
				sprite = sprites.get(i);
				
				if(sprite.isVisible()) {
					sprite.startRender();
				}
			}
			
			sprite.endProgram();
		}
	}
	
	public void renderText() {
		Vector<GL_Text> strings = renderList.getStrings();
		if(!strings.isEmpty()) {			
			GL_Text string = strings.get(0);
			
			string.beginProgram();

			for(int i = 0; i < strings.size(); i++) {
				string = strings.get(i);
				string.startRender();
				
				if(string.isVisible()) {
					string.startRender();
				}
			}
			
			string.endProgram();
			
			if(renderList.getTop() != null) {
				renderList.getTop().beginProgram();
				renderList.getTop().startRender();
				renderList.getTop().endProgram();
			}
		}
	}
}
