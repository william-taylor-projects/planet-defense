package com.planetDefense.scenes;

import com.framework.IContainer;
import android.view.MotionEvent;

import com.framework.IFactory;
import com.framework.core.SceneList;
import com.framework.graphics.Image;
import com.framework.graphics.RenderQueue;
import com.planetDefense.activity.MainActivity;
import com.planetDefense.objects.*;

public class MainObjects extends SceneList implements IContainer {
	private Integer remainingEnemys;
	private Image background;
	private Missiles missile;
	private PowerUps dropables;
	private GameHUD gameHUD;
	private Enemys enemys;
	private Earth world;
	private Ship ship;

	public MainObjects() {
		missile = new Missiles();
		gameHUD = new GameHUD();
		enemys = new Enemys();
		world = new Earth();
		ship = new Ship();

		background = new Image("sprites/pic.bmp");
		background.setPosition(0, 0, 1280, 800);		
		dropables = new PowerUps(ship, enemys);
	}

	public void Initialise(IFactory factory) {
		gameHUD.Initialise(factory);
		missile.Initialise(factory);
		enemys.Initialise(factory);
		ship.Initialise(factory);
		
		Reset(MainActivity.MENU);
	}

	@Override
	public void stackSubObjects(IFactory factory) {
		/** Stack the the following sub game.objects */
		factory.stack(background, "LevelBackground");
		factory.stack(missile, "Missiles");
		factory.stack(enemys, "Enemys");
		factory.stack(world, "Earth");
		factory.stack(ship, "Ship");

		factory.stackContainer(gameHUD, "GameHUD");
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
		remainingEnemys = com.planetDefense.objects.Enemys.STARTING_ENEMYS;
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
			remainingEnemys = com.planetDefense.objects.Enemys.STARTING_ENEMYS;
			enemys.ResetObject();
		}

		gameHUD.SetLevel(value);
	}

	@Override
	public void update() {
		background.update();
		dropables.Update();
		gameHUD.Update();
		missile.Update();
		enemys.Update();
		world.update();
		ship.Update();	

		int startCount = com.planetDefense.objects.Enemys.STARTING_ENEMYS;
		int killCount = enemys.GetNumberOffKills();
		
		remainingEnemys = startCount - killCount;
	}

	public void onEnter() {
		for(int i = 0; i < Missiles.COUNT; i++) {
			missile.ResetMissile(i);
		} dropables.onEnter();
	}

	public void onExit(Integer e) {
		if(e == MainActivity.GAMEOVER) {
			enemys.ResetObject();
			world.resetObject();
			ship.ResetObject();
		}

		dropables.onExit();
	}

	public void onRender(RenderQueue renderList) {
		renderList.put(background);

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