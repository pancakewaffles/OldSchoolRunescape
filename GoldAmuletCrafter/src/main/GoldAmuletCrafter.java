package main;

import java.awt.Color;

import java.awt.Font;

import java.awt.Graphics2D;


import org.osbot.rs07.api.map.Area;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;

import org.osbot.rs07.api.ui.Skill;

import org.osbot.rs07.event.WalkingEvent;

import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import org.osbot.rs07.utility.Condition;

import org.osbot.rs07.utility.ConditionalSleep;

import antiban.CheckTabber;
import antiban.MoveCamera;
import antiban.ResetMapDirection;
import antiban.RightClicky;


@ScriptManifest(author = "Author", info = "Crafts Gold Amulets.", name = "Gold Amulet Crafter", version = 1.0, logo = "")

public class GoldAmuletCrafter extends Script {


	long totalgold;

	String state;

	long startTime;

	Area alkharid = new Area(3263,3157, 3285,3195); // Upper left bound, lower right bound.

	//Position[] path2bank = new Position[] { new Position(3274,3186,0), new Position(3278,3179,0), new Position(3273,3167,0), new Position(3269,3167,0),};

	Area bank = Banks.AL_KHARID;

	Position furnace = new Position (3274,3186,0);

	//Position[] path2furnace = new Position[] { new Position(3272,3167,0), new Position(3275,3174,0), new Position(3279,3181,0), new Position(3274,3186,0),};

	
	final int AMULET_MOULD_ID = 1595;
	final int GOLD_BAR_ID = 2357;
	final int WIDGET_ROOT = 446;
	final int WIDGET_CHILD = 34;
	
	 // Custom Antiban features
	final int RIGHT_CLICKER_AREA_RADIUS = 6;
    private MoveCamera moveCamera = new MoveCamera(this); // 25% chance
    private ResetMapDirection resetMapDirection = new ResetMapDirection(this); // 25% chance
    private RightClicky rightclicker = new RightClicky(this, RIGHT_CLICKER_AREA_RADIUS); // 25% chance
    private CheckTabber checkTabber = new CheckTabber(this); // 25% chance
    private final double ANTIBAN_RECEPTION = 0.35;
    
    
	
	@Override
	public void onStart() 

	{

		startTime = System.currentTimeMillis();

		for(Skill skill : new Skill[]{Skill.CRAFTING}) {

		    getExperienceTracker().start(skill); }
		
			

	}


	private enum State 

	{

		BANK_AMULETS,MAKE_AMULETS,IDLE,WALK_TO_ALKHARID

	}


	private State getState() 

	{

		if(getSkills().getDynamic(Skill.CRAFTING) >= 8 && !alkharid.contains(myPlayer()))

		{

			return State.WALK_TO_ALKHARID;

		}

		if(getSkills().getDynamic(Skill.CRAFTING) >= 8 && alkharid.contains(myPlayer()) && !getInventory().contains(AMULET_MOULD_ID) || !getInventory().contains(GOLD_BAR_ID))

		{

			return State.BANK_AMULETS;

		}

		if(getSkills().getDynamic(Skill.CRAFTING) >= 8 && alkharid.contains(myPlayer()) && getInventory().contains(AMULET_MOULD_ID, GOLD_BAR_ID))

		{

			return State.MAKE_AMULETS;

		}

		return State.IDLE;

	}


	@Override

	public int onLoop() throws InterruptedException 

	{	




		switch(getState()) 

		{

		case WALK_TO_ALKHARID:
			
			log("Checking Antiban.");
			if(enableAntiban()) {
	        	antibanProtocol();
	        }

			state = "Walking To Alkharid";

			getWalking().webWalk(alkharid);

			break;


		case BANK_AMULETS:
			
			// If you want to pretend you afk-ed.
			sleep(random(700,13000));
			log("I am just going to pretend I afk-ed and came back.");
			
			log("Checking Antiban.");
			if(enableAntiban()) {
	        	antibanProtocol();
	        }
			
			

			state = "Walking To The Bank";

	        getWalking().webWalk(bank);

			state = "Banking";

			getObjects().closest("Bank booth").interact("Bank");

			new ConditionalSleep(2500)

            {					

				@Override

				public boolean condition() throws InterruptedException 

				{

					return getBank().isOpen();

				}

			}.sleep();

			sleep(random(300,600));

			if(getBank().isOpen())

			{

				totalgold = getInventory().getAmount(GOLD_BAR_ID) + getBank().getAmount(GOLD_BAR_ID);
				log(String.format("You have %d gold bars.",totalgold));

				if(totalgold < 1)

				{

					getBank().close();

					sleep(random(75,150));

					getLogoutTab().open();

					sleep(random(75,150));

					getLogoutTab().logOut();

					stop();

				}

				getBank().depositAllExcept(AMULET_MOULD_ID);

				sleep(random(100,200));

				if(!getInventory().contains(AMULET_MOULD_ID))

				{

					getBank().withdraw(AMULET_MOULD_ID, 1);

					sleep(random(100,200));

				}

				getBank().withdrawAll(GOLD_BAR_ID);

				sleep(random(100,200));

				getBank().close();

			}

			break;


		case MAKE_AMULETS:
			
			

			state = "Walking To The Furnace";

			getWalking().webWalk(furnace);
			
			new ConditionalSleep(90000) {

				@Override
				public boolean condition() throws InterruptedException {
					// TODO Auto-generated method stub
					return furnace.distance(myPosition()) <= 4;
				}
				
			}.sleep();
			

			state = "Crafting Amulets";

			if(getWidgets().isVisible(WIDGET_ROOT, WIDGET_CHILD))

			{

				getWidgets().interact(WIDGET_ROOT,WIDGET_CHILD,"Make-X");

				sleep(random(1200,2100));

				long amount = getInventory().getAmount(GOLD_BAR_ID);

				String amount1 = String.valueOf(amount);

				getKeyboard().typeString(amount1, true);

				sleep(random(1000,2300));
				
				getMouse().moveOutsideScreen();

				new ConditionalSleep(50000)

	            {					

					@Override

					public boolean condition() throws InterruptedException 

					{

						return !getInventory().contains(GOLD_BAR_ID) || getDialogues().inDialogue();

					}

				}.sleep();
				
				
				sleep(random(500,4000));

			}

			else

			{
				
				
				log("Checking Antiban.");
				if(enableAntiban()) {
		        	antibanProtocol();
		        }

				Entity furnace = objects.closest("Furnace");

				getInventory().interact("Use", GOLD_BAR_ID);

				sleep(random(150,300));

				furnace.interact("Use");

				new ConditionalSleep(5000)

	            {					

					@Override

					public boolean condition() throws InterruptedException 

					{

						return getWidgets().isVisible(WIDGET_ROOT, WIDGET_CHILD);

					}

				}.sleep();

				sleep(random(300,900));

			}

			break;


		case IDLE:

			state = "You do not have lv8 Crafting!";

			break;

		}
		


		return random(500,800);

	}


	@Override

	public void onExit() {


	}



	@Override

	public void onPaint(Graphics2D g) 

	{

		long ss = (System.currentTimeMillis() - startTime) / 1000;

        long mm = ss / 60;

        long hh = mm / 60;

        Font font = new Font("Sans-Serif", Font.BOLD, 14);

	    g.setColor(Color.BLACK);

	    g.setFont(font);

	    g.drawString("EXP Gained: " + getExperienceTracker().getGainedXP(Skill.CRAFTING) + " (" + getExperienceTracker().getGainedXPPerHour(Skill.CRAFTING) + ")", 8, 333);

        g.drawString("Run time: " + hh + "h" + ":" + mm%60 + "m" + ":" + ss%60 + "s" , 8, 318);

        g.drawString("Status: " + state, 8, 303);
        
        
        // Draw mouse cursor
        g.setColor(Color.ORANGE);
        g.fillOval(mouse.getPosition().x, mouse.getPosition().y, 10, 10);

	}
	
	private void antibanProtocol() {
    	double probability = Math.random();
		if(probability < 0.25) {
			moveCamera.begin();
			log("Move Camera Antiban has begun.");
		}else if(probability < 0.50) {
			rightclicker.begin();
			log("Right Clicker Antiban has begun.");
		}else if(probability < 0.75) {
			resetMapDirection.begin();
			log("Reset Map Direction Antiban has begun.");
		}else {
			checkTabber.begin();
			log("CheckTabber has begun.");
		}
		
	}

	private boolean enableAntiban() {
		if(Math.random() < ANTIBAN_RECEPTION) {
			return true;
		}else {
			return false;
		}
	}


}
