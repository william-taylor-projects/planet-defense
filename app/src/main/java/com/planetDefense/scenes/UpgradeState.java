package com.planetDefense.scenes;

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
import com.planetDefense.objects.Ship.CURRENT_SHIP;
import android.view.MotionEvent;

import com.planetDefense.objects.*;
import com.planetDefense.events.*;

/**
 *  This is the main scene for the game.
 *  All its game.objects are stored in the Main
 *  Objects class for more clear code
 *  
 * @version : final version for release
 * @author : William Taylor
 */
public class UpgradeState extends Scene {
	/** Some static final variables that are used as settings for this state */
	private static final Integer REPAIR_EARTH_COST = 2000;
	private static final Integer REPAIR_SHIP_COST = 1000;

	private static final Integer PERK_THREE_COST = 1000;
	private static final Integer PERK_TWO_COST = 1000;
	private static final Integer PERK_ONE_COST = 1000;
	
	private static final Integer SHIP_THREE_COST = 2000;
	private static final Integer SHIP_TWO_COST = 1000;
	private static final Integer SHIP_ONE_COST = 100;
	
	/** A reference to a inner class that manages all the buttons in the scene */
	private UpgradeButtons upgradeButtons;
	
	/** A reference to a inner class that manages all the text in the scene */
	private UpgradeText upgradeText;
	
	/** The background texture for this scene */
	private GameImage background;
	
	/** The back button for the scene  */
	private GameButton backButton;
	
	/** perk three's icon */
	private GameImage perkThree;
	
	/** perk two's icon */
	private GameImage perkTwo;
	
	/** perk one's icon */
	private GameImage perkOne;
	
	/** ship three's icon */
	private GameImage shipThree;
	
	/** ship two's icon */
	private GameImage shipTwo;
	
	/** ship one's icon*/
	private GameImage shipOne;
	
	/** The back event that is fired */
	private Click event;

	/** onCreate function which is called when the scene is first created*/
	@Override
	public void onCreate(IFactory factory) {
		/** Get the following assets from the frameworks factory */
		background = factory.Request("MenuBackground");
		backButton = factory.Request("BackButton");
		
		/** Setup the game.events */
		event = new Click(backButton);
		event.eventType(new StateClick(2));
		
		/** Setup the inner classes that manage certain elements */
		upgradeButtons = new UpgradeButtons();
		upgradeButtons.initialise(factory);
		
		upgradeText = new UpgradeText();
		upgradeText.initialise(factory);
		
		/** Then setup the icons for the perks */
		perkThree = new GameImage("sprites/armour.png");
		perkTwo = new GameImage("sprites/reload.png");
		perkOne = new GameImage("sprites/money.png");
		
		/** setup the positions */
		perkThree.setPosition(1100, 250, 75, 75);
		perkTwo.setPosition(1100, 150, 75, 75);
		perkOne.setPosition(1100, 50, 75, 75);
		
		/** setup the icons for the ships */
		shipThree = new GameImage("sprites/spr.png");
		shipTwo = new GameImage("sprites/spr3.png");
		shipOne = new GameImage("sprites/spr2.png");
		
		/** setup the positions */
		shipThree.setPosition(200, 400, 80, 110);
		shipTwo.setPosition(200, 275, 80, 110);
		shipOne.setPosition(200, 150, 80, 110);
	}

	/** onTouchEvent handler which then passes the data to the inner classes & event */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		upgradeButtons.onTouch(e, x, y);
		event.OnTouch(e, x, y);
	}
	
	/** onRender function just pushes all the game.objects to the queue */
	@Override
	public void onRender(RenderQueue renderList) {
		/** insert the background and button first */
		renderList.pushRenderable(background);
		renderList.pushRenderable(backButton);
		
		/** then call the inner classes onRender function */
		upgradeButtons.onRender(renderList);
		upgradeText.onRender(renderList);
		
		/** then overlay all the icons for the scene */
		renderList.pushRenderable(shipThree);
		renderList.pushRenderable(shipTwo);
		renderList.pushRenderable(shipOne);
		
		renderList.pushRenderable(perkThree);
		renderList.pushRenderable(perkTwo);
		renderList.pushRenderable(perkOne);
	}
	
	/** update function simply updates all the elements in the scene */
	@Override
	public void onUpdate() {
		/** Update button, background and inner classes */
		upgradeButtons.update();
		upgradeText.update();
		background.update();
		backButton.Update();
		
		/** The the ship icons */
		shipThree.update();
		shipTwo.update();
		shipOne.update();
		
		/** The the perk icons */
		perkThree.update();
		perkTwo.update();
		perkOne.update();
	}

	/** this function is called when the state is entered */
	@Override
	public void onEnter(Integer nextScene) {		
		/** & register the event with the event manager */
		EventManager.get().addListener(event);	
	}

	/** this function is called when the state is exited */
	@Override
	public void onExit(Integer nextScene) {
		/** We remove the event from the event manager */
		EventManager.get().removeListener(event);	
	}
	
	/** Private function that posts a message if the user hasnt got enough money */
	private void postNotEnoughCashMessage() {
		new MessageBox()
			.setTitle("Not enough cash")
			.setMessage("Sorry you do not have enough cash")
			.show(true);
	}
	
	/** Private function that posts a message if the user has already got the perk */
	private void postAlreadyBaughtMessage() {
		new MessageBox()
			.setTitle("Perk Problem")
			.setMessage("You already have this perk")
			.show(true);
	}

	/**
	 * A inner class that manages all the buttons
	 * and the game.events that they fire.
	 * 
	 * @author William Taylor
	 */
	public class UpgradeButtons {
		private GameButton buyShipThree;
		private GameButton buyShipTwo;
		private GameButton buyShipOne;
		
		private GameButton buyPerkThree;
		private GameButton buyPerkTwo;
		private GameButton buyPerkOne;
		
		private GameButton buyPlayerHealth;
		private GameButton buyEarthHealth;
		
		private Click buyShipEvent[] = new Click[3];
		private Click repairEvent[] = new Click[2];
		private Click perkEvent[] = new Click[3];
		private Earth earth;
		private Ship Player;
		
		private class ShipChange implements IEvent, UiEvent {
			private CURRENT_SHIP newShip;
			private Integer shipCost;
			
			public ShipChange(CURRENT_SHIP s, Integer cost) {
				shipCost = cost;
				newShip = s;
			}
			
			@Override
			public void onActivate(Object data) {	
				if(Ship.CASH >= shipCost) {
					new MessageBox().setTitle("Buy Ship ?")
						.setMessage("Are you sure ?")
						.onAccept(this)
						.EnableYesNo()
						.show(false);
				} else {
					postNotEnoughCashMessage();
				}
			}
	
			@Override
			public void onUiEvent() {
				Player.SetShip(newShip);
				Ship.CASH -= shipCost;
			}
			
			@Override
			public void update() {
				;
			}
		}		
		private class RepairItem implements IEvent, UiEvent {
			private Boolean repairShip;
			private Integer actionCost;
			
			public RepairItem(Boolean s, Integer itemCost) {
				actionCost = itemCost;
				repairShip = s;
			}
			
			@Override
			public void onActivate(Object data) {
				if(Ship.CASH >= actionCost) {
					String title = "";
					
					if(repairShip) {
						title = "Repair Ship ?";
					} else {
						title = "Repair Planet ?";
					}
		
					new MessageBox().setTitle(title)
						.setMessage("Are you sure ?")
						.EnableYesNo()
						.onAccept(this)
						.show(false);
				} else {
					postNotEnoughCashMessage();
				}
			}
	
			@Override
			public void onUiEvent() {
				Ship.CASH -= actionCost;
				if(repairShip) {
					Player.Repair();
				} else {
					earth.repair();
				}
			}
	
			@Override
			public void update() {
				;
			}
		}
		private class BuyPerk implements IEvent, UiEvent {
			private Boolean used;
			private Integer cost;
			private String type;
			
			public BuyPerk(String s, Integer perkCost) {
				cost = perkCost;
				used = false;
				type = s;
			}
			
			@Override
			public void onActivate(Object data) {
				if(used) {
					postAlreadyBaughtMessage();
				} else {
					if(Ship.CASH >= cost) {
						new MessageBox()
							.setTitle(type)
							.setMessage("Are you sure you want to buy this perk ?")
							.onAccept(this)
							.EnableYesNo()
							.show(false);
					} else {
						postNotEnoughCashMessage();
					}
				}
			}
			
			@Override
			public void update() {
				;
			}
	
			@Override
			public void onUiEvent() {
				Ship.CASH -= cost;
				used = true;
				
				if(type.equalsIgnoreCase("Amour Plating")) {
					Ship.ARMOUR_LEVEL = 2;
				} else if(type.equalsIgnoreCase("Speed Fire")) {
					Missiles.DELAY = 250;
				} else {
					Ship.CASH_TO_ADD = 15;
				}
			}
		}
	
		/** Basic contructor allocates all the memory needed for all buttons & game.events */
		public UpgradeButtons() {
			/** get the font needed for the buttons */
			LabelEngine e = LabelEngine.Get("small");
			
			/** allocate memory for all the buttons */
			buyPlayerHealth = new GameButton(e);
			buyEarthHealth = new GameButton(e);
			buyShipThree = new GameButton(e);
			buyPerkThree = new GameButton(e);
			buyShipOne = new GameButton(e);
			buyPerkOne = new GameButton(e);
			buyPerkTwo = new GameButton(e);
			buyShipTwo = new GameButton(e);
	
			/** Setup the buy ship game.events and pass the button which activates them */
			buyShipEvent[2] = new Click(buyShipThree);
			buyShipEvent[1] = new Click(buyShipTwo);
			buyShipEvent[0] = new Click(buyShipOne);	
			
			/** Setup the buy perk game.events and pass the button which activates them */
			perkEvent[0] = new Click(buyPerkOne);
			perkEvent[1] = new Click(buyPerkTwo);
			perkEvent[2] = new Click(buyPerkThree);
			
			/** Setup the repair game.events and pass the button which activates them */
			repairEvent[1] = new Click(buyPlayerHealth);
			repairEvent[0] = new Click(buyEarthHealth);
		}	
	
		/** initialise function that setups all the object needed for drawing */
		public void initialise(IFactory factory) {
			/** request these elements from the factory */
			earth = factory.Request("Earth");
			Player = factory.Request("Ship");
			
			/** setup the text for each button  */
			buyShipThree.SetText("Buy Ship $" + SHIP_ONE_COST, 500, 425, 200, 125);
			buyShipTwo.SetText("Buy Ship $" + SHIP_TWO_COST, 500, 300, 200, 125);
			buyShipOne.SetText("Buy Ship $" + SHIP_THREE_COST, 500, 175, 200, 125);
			
			/** setup the text for each button  */ 
			buyPerkThree.SetText("Amour Plating $" + PERK_ONE_COST, 900, 250, 400, 50);
			buyPerkOne.SetText("Speed Fire $" + PERK_TWO_COST, 900, 150, 400, 50);
			buyPerkTwo.SetText("Quick Money $" + PERK_THREE_COST, 900, 50, 400, 50);
			
			buyPlayerHealth.SetText("Repair Ship $" + REPAIR_SHIP_COST, 920, 600, 400, 50);
			buyEarthHealth.SetText("Repair Planet $" + REPAIR_EARTH_COST, 920, 525, 400, 50);
			
			/** setup the game.events for each button  */
			buyShipEvent[2].eventType(new ShipChange(CURRENT_SHIP.BASIC, SHIP_ONE_COST));
			buyShipEvent[1].eventType(new ShipChange(CURRENT_SHIP.MEDIUM, SHIP_TWO_COST));
			buyShipEvent[0].eventType(new ShipChange(CURRENT_SHIP.ADVANCED, SHIP_THREE_COST));
			
			repairEvent[0].eventType(new RepairItem(false, REPAIR_EARTH_COST));
			repairEvent[1].eventType(new RepairItem(true, REPAIR_SHIP_COST));
	
			/** Add listeners to the event */
			EventManager e = EventManager.get();
			
			e.addListeners(buyShipEvent);
			e.addListeners(repairEvent);
			e.addListeners(perkEvent);
			
			/** The init the perk game.events which need to created on each play */
			initPerkEvents();
		}
		
		/** simply creates new buy perks game.events for each play session */
		public void initPerkEvents() {
			perkEvent[0].eventType(new BuyPerk("Speed Fire", PERK_TWO_COST));
			perkEvent[1].eventType(new BuyPerk("Quick Money", PERK_THREE_COST));
			perkEvent[2].eventType(new BuyPerk("Amour Plating", PERK_ONE_COST));
		}

		/** update function that will update all elements in the scene */
		public void update() {
			/** update all the buttons in the scene */
			update(buyShipThree, buyShipTwo, buyShipOne);
			update(buyPerkThree, buyPerkTwo, buyPerkOne);
			update(buyPlayerHealth, buyEarthHealth);
		}
		
		/** onRender function that adds all the buttons into the queue */
		public void onRender(RenderQueue renderList) {
			onRender(renderList, buyShipThree, buyShipTwo, buyShipOne);
			onRender(renderList, buyPerkThree, buyPerkTwo, buyPerkOne);
			onRender(renderList, buyPlayerHealth, buyEarthHealth);
		}
		
		/** Handles the onTouch event which passes all the data to the game.events */
		public void onTouch(MotionEvent e, int x, int y) {
			if(e.getAction() == MotionEvent.ACTION_DOWN) {
				for(Click c : buyShipEvent) {
					c.OnTouch(e, x, y);
				}
				
				for(Click c : repairEvent) {
					c.OnTouch(e, x, y);
				}
				
				for(Click c : perkEvent) {
					c.OnTouch(e, x, y);
				}
			}
		}
		
		/** a private function to make drawing so many buttons easier */
		private void onRender(RenderQueue renderList, GameButton...buttons) {
			for(GameButton o : buttons) {
				renderList.pushRenderable(o);
			}
		}
		
		/** a private function to make updating so many buttons easier */
		private void update(GameButton...buttons) {
			for(GameButton o : buttons) {
				o.Update();
			}
		}
	}

	/** re-creates the perk game.events */
	public void reset() {
		upgradeButtons.initPerkEvents();
	}
	
	/**
	 * A inner class that handles all the titles 
	 * in the game.scenes.
	 * 
	 * @author William Taylor
	 */
	public class UpgradeText  {
		/** The buy health title in the scene */
		private LabelLine buyHealthTitle;
		
		/** The buy ship title texture */
		private LabelLine buyShipTitle;
		
		/** The buy ship title texture */
		private LabelLine buyPerkTitle;

		/** The constructor that sets up the game.objects needed */
		public UpgradeText() {
			LabelEngine e = LabelEngine.Get("large");
			LabelEngine m = LabelEngine.Get("small");
			
			buyHealthTitle = new LabelLine(m, "Buy Health");
			buyShipTitle = new LabelLine(e, "Buy Ships");
			buyPerkTitle = new LabelLine(m, "Buy Perks");
		}

		/** onRender function simply pushes the items to the queue */
		public void onRender(RenderQueue renderList) {
			renderList.pushRenderable(buyHealthTitle);
			renderList.pushRenderable(buyShipTitle);
			renderList.pushRenderable(buyPerkTitle);
		}

		/** loads the texture elements at there needed position */
		public void initialise(IFactory factory) {
			buyHealthTitle.Load(830, 700);
			buyPerkTitle.Load(840, 350);
			buyShipTitle.Load(200, 600);
		}

		/** simply updates the text elements */
		public void update() {
			buyHealthTitle.Update();
			buyShipTitle.Update();
			buyPerkTitle.Update();
		}
	}
}
