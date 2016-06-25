package com.planetDefense;

import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.support.v4.view.GestureDetectorCompat;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Activity;
import android.os.Build;

/**
 *  This class provides a simple interface
 *  to initialize the aegis framework for 
 *  the user. Users simply create a instance
 *  of this class in there main activity.
 * 
 * @version 28/01/2014
 * @author William Taylor
 */
@SuppressWarnings("deprecation") @SuppressLint("NewApi")
public class TaloniteGame extends Application implements OnGestureListener {
	/** A public access to the game activity */
	public static Activity Activity;
	
	/** Games are designed to a certain screen size then scaled */
	private final Integer SURFACE_HEIGHT = 800;
	private final Integer SURFACE_WIDTH = 1280;
	
	/**	A class to detect flicks from the user */
	private GestureDetectorCompat detector; 
	 
	/** Android activity variable for setting up states */
	private ITaloniteActivity aegisActivity;
	
	/** OpenGL Renderer/Surface variable handles all graphic operations */
	private GLSurfaceView renderingThread;
	
	/** Android Activity for getting info */
	private Activity gameActivity;
	
	/** Scalar variables for transforming pointer touch positions */
	private Float height = 1.0f;
	private Float width = 1.0f;
	
	/** A variable that tells if the game is landscape or portrait */
	private Boolean landscape;
	
	private static Boolean disable;

	/**
	 *	Starts the game and get all info from the android
	 *	activity.
	 *
	 * @param activity Android activity variable
	 */
	public void start(ITaloniteActivity activity) {
		detector = new GestureDetectorCompat(getApplicationContext(), this);

		ResourceManager.Get().Initialise(getApplicationContext());
		DisplayMetrics Window = new DisplayMetrics();
		landscape = true;
		disable = false;
	
		// Setup rendering thread
		renderingThread = new GLSurfaceView(this);
		renderingThread.setEGLContextClientVersion(2);
		renderingThread.setRenderer(new TaloniteRenderer());
		renderingThread.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		renderingThread.setPreserveEGLContextOnPause(true);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		}
		
		// Set up 
		gameActivity.getWindowManager().getDefaultDisplay().getMetrics(Window);		
		gameActivity.setContentView(renderingThread);
		
		height = (float)Window.heightPixels/SURFACE_HEIGHT;
		width = (float)Window.widthPixels/SURFACE_WIDTH;
		
		aegisActivity = activity;
		aegisActivity.setupStates(SceneManager.get());
		
		Log.e("h2", "H2");
	}
	
	public void makePortrait() {
		landscape = false;
	}
	
	public IFactory getFactory() {
		return SceneManager.get().getFactory();
	}
	
	/**
	 * Function that helps stop the activity sending messages
	 * to the framework before it has been set up.
	 * 
	 * @return boolean to indicate if the framework has loaded all components
	 */
	public boolean hasInitialised() {
		return SceneManager.get().hasLoaded();
	}
	
	public static void EnableInput() {
		disable = false;
	}
	
	public static void DisableInput() {
		disable = true;
	}
	/**
	 * Sets up the window/surface for draw by accessing
	 * the default window from the Android activity.
	 * 
	 * @param activity Android activity to get window from.
	 */
	public void setupWindow(Activity activity) {
		gameActivity = activity;
		gameActivity.requestWindowFeature(0x1);
		gameActivity.getWindow().setFlags(0x400, 0x400);
		
		Activity = activity;
	}
	
	/**
	 * Function handles the onTouch event activiated by the Android activity
	 * and sends the relevant data to the current scene.
	 * 
	 * @param e Touch event variable which will be sent to the current scene.
	 */
	public boolean touchEvent(MotionEvent e) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		}
		
 		if(!disable) {
			detector.onTouchEvent(e);
			
			if(landscape && e != null) {
				if((e != null) && (height != 0) && (width != 0)) {
					// Set y(0) to the bottem left screen point.
					int y = (int)((SURFACE_HEIGHT) - (e.getY() / height));
					int x = (int)(e.getX() / width);
					
					// Pass transformed data to current scene.
					SceneManager.get().GetCurrent().onTouch(e, x, y);
					return true;
				}
			} else {
				if(height != 0 && width != 0) {
					// Set y(0) to the bottem left screen point.
					int y = (int)((SURFACE_HEIGHT) - (e.getY() / height));
					int x = (int)(e.getX() / width);
					
					// Pass transformed data to current scene.
					SceneManager.get().GetCurrent().onTouch(e, x, y);
					return true;
				}
			}
 		}
		return true;
	}

	
	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		}
		
		Scene scene = SceneManager.get().GetCurrent();
	
		if(velocityX*velocityX > velocityY*velocityY) {
			scene.onFling(e1, e2, velocityX, 0);
			return true;
		} else {
			scene.onFling(e1, e2, 0, velocityY);
			return true;
		}
	}

	@Override
	public void onLongPress(MotionEvent e) {
		int y = (int)((SURFACE_HEIGHT) - (e.getY() / height));
		int x = (int)(e.getX() / width);
		
		// Pass transformed data to current scene.
		SceneManager.get().GetCurrent().onLongPress(e, x, y);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public void onPause() {
		renderingThread.onPause();
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		}
	}

	public void onResume() {
		renderingThread.onResume();
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			renderingThread.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		}
	}
}