package com.game.scenes;

import com.framework.IEvent;
import com.framework.IFactory;
import com.framework.IUiEvent;
import com.framework.core.ClickEvent;
import com.framework.core.EventManager;
import com.framework.core.Scene;
import com.framework.dialogs.MessageBox;
import com.framework.graphics.Button;
import com.framework.graphics.Font;
import com.framework.graphics.Image;
import com.framework.graphics.Label;
import com.framework.graphics.RenderQueue;
import com.game.actors.Earth;
import com.game.actors.Missiles;
import com.game.actors.Ship;
import com.game.actors.Ship.CURRENT_SHIP;
import android.view.MotionEvent;

import com.game.events.*;

public class UpgradeState extends Scene {
	private static final Integer REPAIR_EARTH_COST = 2000;
	private static final Integer REPAIR_SHIP_COST = 1000;
	private static final Integer PERK_THREE_COST = 1000;
	private static final Integer PERK_TWO_COST = 1000;
	private static final Integer PERK_ONE_COST = 1000;
	private static final Integer SHIP_THREE_COST = 2000;
	private static final Integer SHIP_TWO_COST = 1000;
	private static final Integer SHIP_ONE_COST = 100;

	private UpgradeButtons upgradeButtons;
	private UpgradeText upgradeText;
	private ClickEvent event;
	private Button backButton;
	private Image background;
	private Image perkThree;
	private Image perkTwo;
	private Image perkOne;
	private Image shipThree;
	private Image shipTwo;
	private Image shipOne;

	@Override
	public void onCreate(IFactory factory) {
		background = factory.request("MenuBackground");
		backButton = factory.request("BackButton");
		
		/** Setup the game.events */
		event = new ClickEvent(backButton);
		event.eventType(new StateClick(2));
		
		/** Setup the inner classes that manage certain elements */
		upgradeButtons = new UpgradeButtons();
		upgradeButtons.initialise(factory);
		
		upgradeText = new UpgradeText();
		upgradeText.initialise(factory);

		perkThree = new Image("sprites/armour.png");
		perkTwo = new Image("sprites/reload.png");
		perkOne = new Image("sprites/money.png");

		perkThree.setPosition(1100, 250, 75, 75);
		perkTwo.setPosition(1100, 150, 75, 75);
		perkOne.setPosition(1100, 50, 75, 75);

		shipThree = new Image("sprites/spr.png");
		shipTwo = new Image("sprites/spr3.png");
		shipOne = new Image("sprites/spr2.png");

		shipThree.setPosition(200, 400, 80, 110);
		shipTwo.setPosition(200, 275, 80, 110);
		shipOne.setPosition(200, 150, 80, 110);
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		upgradeButtons.onTouch(e, x, y);
		event.OnTouch(e, x, y);
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);
		renderList.put(backButton);

		upgradeButtons.onRender(renderList);
		upgradeText.onRender(renderList);

		renderList.put(shipThree);
		renderList.put(shipTwo);
		renderList.put(shipOne);
		renderList.put(perkThree);
		renderList.put(perkTwo);
        renderList.put(perkOne);
	}
	
	/** update function simply updates all the elements in the scene */
	@Override
	public void onUpdate() {
		/** update button, background and inner classes */
		upgradeButtons.update();
		upgradeText.update();
		background.update();
		backButton.update();
		
		/** The the ship icons */
		shipThree.update();
		shipTwo.update();
		shipOne.update();
		
		/** The the perk icons */
		perkThree.update();
		perkTwo.update();
		perkOne.update();
	}

	@Override
	public void onEnter(Integer nextScene) {
		EventManager.get().addListener(event);
	}

	@Override
	public void onExit(Integer nextScene) {
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
		private Button buyShipThree;
		private Button buyShipTwo;
		private Button buyShipOne;
		
		private Button buyPerkThree;
		private Button buyPerkTwo;
		private Button buyPerkOne;
		
		private Button buyPlayerHealth;
		private Button buyEarthHealth;
		
		private ClickEvent buyShipEvent[] = new ClickEvent[3];
		private ClickEvent repairEvent[] = new ClickEvent[2];
		private ClickEvent perkEvent[] = new ClickEvent[3];
		private Earth earth;
		private Ship Player;
		
		private class ShipChange implements IEvent, IUiEvent {
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
				Player.setShip(newShip);
				Ship.CASH -= shipCost;
			}
			
			@Override
			public void update() {
				;
			}
		}		
		private class RepairItem implements IEvent, IUiEvent {
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
		private class BuyPerk implements IEvent, IUiEvent {
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
				} else if(type.equalsIgnoreCase("Speed fire")) {
					Missiles.DELAY = 250;
				} else {
					Ship.CASH_TO_ADD = 15;
				}
			}
		}
	
		/** Basic contructor allocates all the memory needed for all buttons & game.events */
		public UpgradeButtons() {
			/** get the font needed for the buttons */
			Font e = Font.get("small");
			
			/** allocate memory for all the buttons */
			buyPlayerHealth = new Button(e);
			buyEarthHealth = new Button(e);
			buyShipThree = new Button(e);
			buyPerkThree = new Button(e);
			buyShipOne = new Button(e);
			buyPerkOne = new Button(e);
			buyPerkTwo = new Button(e);
			buyShipTwo = new Button(e);
	
			/** Setup the buy ship game.events and pass the button which activates them */
			buyShipEvent[2] = new ClickEvent(buyShipThree);
			buyShipEvent[1] = new ClickEvent(buyShipTwo);
			buyShipEvent[0] = new ClickEvent(buyShipOne);
			
			/** Setup the buy perk game.events and pass the button which activates them */
			perkEvent[0] = new ClickEvent(buyPerkOne);
			perkEvent[1] = new ClickEvent(buyPerkTwo);
			perkEvent[2] = new ClickEvent(buyPerkThree);
			
			/** Setup the repair game.events and pass the button which activates them */
			repairEvent[1] = new ClickEvent(buyPlayerHealth);
			repairEvent[0] = new ClickEvent(buyEarthHealth);
		}	
	
		/** initialise function that setups all the object needed for drawing */
		public void initialise(IFactory factory) {
			/** request these elements from the factory */
			earth = factory.request("Earth");
			Player = factory.request("Ship");
			
			/** setup the text for each button  */
			buyShipThree.setText("Buy Ship $" + SHIP_ONE_COST, 500, 425, 200, 125);
			buyShipTwo.setText("Buy Ship $" + SHIP_TWO_COST, 500, 300, 200, 125);
			buyShipOne.setText("Buy Ship $" + SHIP_THREE_COST, 500, 175, 200, 125);
			
			/** setup the text for each button  */ 
			buyPerkThree.setText("Amour Plating $" + PERK_ONE_COST, 900, 250, 400, 50);
			buyPerkOne.setText("Speed fire $" + PERK_TWO_COST, 900, 150, 400, 50);
			buyPerkTwo.setText("Quick Money $" + PERK_THREE_COST, 900, 50, 400, 50);
			
			buyPlayerHealth.setText("Repair Ship $" + REPAIR_SHIP_COST, 920, 600, 400, 50);
			buyEarthHealth.setText("Repair Planet $" + REPAIR_EARTH_COST, 920, 525, 400, 50);
			
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
			perkEvent[0].eventType(new BuyPerk("Speed fire", PERK_TWO_COST));
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
				for(ClickEvent c : buyShipEvent) {
					c.OnTouch(e, x, y);
				}
				
				for(ClickEvent c : repairEvent) {
					c.OnTouch(e, x, y);
				}
				
				for(ClickEvent c : perkEvent) {
					c.OnTouch(e, x, y);
				}
			}
		}
		
		/** a private function to make drawing so many buttons easier */
		private void onRender(RenderQueue renderList, Button...buttons) {
			for(Button o : buttons) {
				renderList.put(o);
			}
		}
		
		/** a private function to make updating so many buttons easier */
		private void update(Button...buttons) {
			for(Button o : buttons) {
				o.update();
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
		private Label buyHealthTitle;
		
		/** The buy ship title texture */
		private Label buyShipTitle;
		
		/** The buy ship title texture */
		private Label buyPerkTitle;

		/** The constructor that sets up the game.objects needed */
		public UpgradeText() {
			Font e = Font.get("large");
            Font m = Font.get("small");
			
			buyHealthTitle = new Label(m, "Buy Health");
			buyShipTitle = new Label(e, "Buy Ships");
			buyPerkTitle = new Label(m, "Buy Perks");
		}

		/** onRender function simply pushes the items to the queue */
		public void onRender(RenderQueue renderList) {
			renderList.put(buyHealthTitle);
			renderList.put(buyShipTitle);
			renderList.put(buyPerkTitle);
        }

        /** loads the texture elements at there needed position */
		public void initialise(IFactory factory) {
			buyHealthTitle.load(830, 700);
			buyPerkTitle.load(840, 350);
			buyShipTitle.load(200, 600);
		}

		/** simply updates the text elements */
		public void update() {
			buyHealthTitle.update();
			buyShipTitle.update();
			buyPerkTitle.update();
		}
	}
}
