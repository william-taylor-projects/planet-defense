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
import android.view.MotionEvent;
import com.planetDefense.*;
import objects.*;

/**
 *  This is a scene list which controls all MainScene
 *  objects such as the ship and enemys
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class MainObjects extends SceneList implements IContainer {	
	/** A integer that counts the remaining enemys in the scene */
	private Integer remainingEnemys;	
	
	/** This the background texture for the level */
	private GameImage background;
	
	/** A container which controls a list of Missile objects */
	private Missiles missile;
	
	/** Another container that manages all the powerups that can be dropped */
	private PowerUps dropables;
	
	/** Yet Another container :P which has manages all the HUD objects */
	private GameHUD gameHUD;
	
	/** I like containers :3 This one holds all the enemys in the scene */
	private Enemys enemys;
	
	/** Again with a container, just stop it. It holds the moon and planet objects */
	private Earth world;
	
	/** Not a container D: This is just a reference to the player */
	private Ship ship;
	
	/**	The basic constructor for the object */
	public MainObjects() {	
		/** Create the objects used by the object */
		missile = new Missiles();
		gameHUD = new GameHUD();
		enemys = new Enemys();
		world = new Earth();
		ship = new Ship();
	
		/** Load the background image and pass data to the dropables container */
		background = new GameImage("sprites/pic.bmp");
		background.setPosition(0, 0, 1280, 800);		
		dropables = new PowerUps(ship, enemys);
	}
	
	/**	Initialise function which calls the relevant functions in the containers */
	public void Initialise(IFactory factory) {		
		gameHUD.Initialise(factory);
		missile.Initialise(factory);
		enemys.Initialise(factory);
		ship.Initialise(factory);
		
		Reset(MainActivity.MENU);
	}

	/** Must implement this method to add inner objects to the factory */
	@Override
	public void StackSubObjects(IFactory factory) {
		/** Stack the the following sub objects */
		factory.Stack(background, "LevelBackground");
		factory.Stack(missile, "Missiles");
		factory.Stack(enemys, "Enemys");
		factory.Stack(world, "Earth");
		factory.Stack(ship, "Ship");
		
		/** The stack the game HUD container which will stack its sub objects */
		factory.StackContainer(gameHUD, "GameHUD");
	}
	
	/**	Simple Check Function */
	public boolean Alive() {
		return(ship.isAlive());
	}

	/** Resets the MainObjects back to its original state */
	public void Reset(int ID) {
		/** Always reset HUD if the user is entering from the menu */
		if(ID == MainActivity.MENU) {
			gameHUD.ResetObject();
			gameHUD.SetLevel(1);
		}
		
		/** Reset the counters and enemy states */
		remainingEnemys = objects.Enemys.STARTING_ENEMYS;
		enemys.ResetObject();
		Enemy.SPEED = 0.75F;
		
		/** Reset the worlds rotation and its health */
		world.resetObject();
		world.repair();
		
		/** Do the same for the players ship */
		ship.ResetObject();
		ship.Repair();
	}
	
	/**	Handler function for when the user touches the screen */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		/** Send the data to the following classes */
		gameHUD.OnTouch(e, x, y);
		missile.OnTouch(e, x, y);
		ship.OnTouch(e, x, y);
	}

	/** Next level function that steps up the difficult when called */
	public void NextLevel(Integer value) {
		/** Make sure the ship is alive first */
		if(ship.isAlive()) {
			remainingEnemys = objects.Enemys.STARTING_ENEMYS;
			enemys.ResetObject();
		}
		
		/** Increment the level count in the HUD */
		gameHUD.SetLevel(value);
	}

	/** Update object function just updates the objects positions	 */
	@Override
	public void Update() {
		/** Update the following objects */
		background.update();
		dropables.Update();
		gameHUD.Update();
		missile.Update();
		enemys.Update();
		world.update();
		ship.Update();	
		
		/** Re calculate the remaining enemys on each update */
		int startCount = objects.Enemys.STARTING_ENEMYS;
		int killCount = enemys.GetNumberOffKills();
		
		remainingEnemys = startCount - killCount;
	}
	
	/**	onEnter function starts the timer for the droppables */
	public void onEnter() {
		for(int i = 0; i < Missiles.COUNT; i++) {
			missile.ResetMissile(i);
		} dropables.onEnter();
	}
	
	/** onExit stops the dropables timer and resets some objects */
	public void onExit(Integer e) {
		/** Reset some of the objects */
		if(e == MainActivity.GAMEOVER) {
			enemys.ResetObject();
			world.resetObject();
			ship.ResetObject();
		}
		
		/** Call the dropables version of the function */
		dropables.onExit();
	}
	
	/** onRender function that calls the respective render functions of each object  */
	public void onRender(RenderQueue renderList) {
		/** Send the background to the renderQueue first */
		renderList.pushRenderable(background);
		
		/** The call the object render functions */
		world.draw(renderList);
		missile.draw(renderList);
		enemys.draw(renderList);
		ship.draw(renderList);
		gameHUD.draw(renderList);
		dropables.draw(renderList);
	}
	
	/** Simple get function	 */
	public int getRemainingEnemys() {
		if(remainingEnemys < 0) {
			remainingEnemys = 0;
		} return this.remainingEnemys;
	}
}