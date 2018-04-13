import static org.osbot.rs07.script.MethodProvider.random;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Optional;

import javax.swing.JFrame;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import antiban.CheckTabber;
import antiban.MoveCamera;
import antiban.ResetMapDirection;
import antiban.RightClicky;

public class FishAnchovies extends Task {

	
	
	
	
	private Area lumbridgeSwampFishingArea = new Area(3238, 3148, 3247, 3156);
    private Area lumbridgeBank = Banks.LUMBRIDGE_UPPER;
    private Area IntermediateArea = new Area(3216,3212,3213,3225);
    private Area PenultimateArea = new Area(3206,3209,3210,3211);
    private Area trickSpot = new Area(3245,3157,3246,3157);
    private NPC fishingSpot;
    private boolean atPosition = false;
    private String status = "Stopped";
    
    // Required Fishing Items to fish Shrimps/Anchovies at Lumbridge Swamp
    private final String SMALLNET = "Small fishing net";
    private final int SMALLNET_ID = 303;
    private final String ACTION = "Net";
    
 // Custom Antiban features
    private final int RIGHT_CLICKER_AREA_RADIUS = 6;
    private MoveCamera moveCamera = new MoveCamera(script); // 25% chance
    private ResetMapDirection resetMapDirection = new ResetMapDirection(script); // 25% chance
    private RightClicky rightclicker = new RightClicky(script,RIGHT_CLICKER_AREA_RADIUS); // 25% chance
    private CheckTabber checkTabber = new CheckTabber(script); // 25% chance
    private final double ANTIBAN_RECEPTION = 0.06;
    
    
  //GUI related variables
    
    private long startTime;
    private long runTime = 0;
    private Thread thread;
    
    private boolean isRunning = true;
    
    private enum State {
        FISH, BANK, UNDERATTACK
    };


    public FishAnchovies(Script script) {
        super(script);
      
        startTime = System.currentTimeMillis();
        script.getExperienceTracker().start(Skill.FISHING);
        thread = new Thread(){
            public synchronized void run(){
                for(;;){
                    if(isRunning){
                        try {
                            runTime = System.currentTimeMillis() - startTime;
                            this.wait(50);
                        } catch (InterruptedException ex) {
                            script.log(ex);
                        }
                    }else{
                        break;
                    }
                }
            }
        };
        thread.start();
        status = "Running";
    }

    @Override
    public boolean verify() {
        return false;
    }

    @Override
    public int execute() {
		
        	
        	switch(getState()){
            case BANK:
                getFromBank();
                break;
            case FISH:
                fish();
                break;
            case UNDERATTACK:
            	attacked();
            	break;

        	}
        	
        	script.log("Checking Antiban.");
        	if(enableAntiban()) {
            	antibanProtocol();
            }
        
		return random(1500, 2500);
    }

    @Override
    public String describe() {
        return "Fishing Anchovies.";
    }

    @Override
	public void paint(Graphics2D g) {
		
        
        	
            //g.drawImage(image, 1, 338, null);
            g.setColor(Color.DARK_GRAY);
            
            g.drawString(status, 121, 406); // run time
            g.drawString(script.getSkills().getStatic(Skill.FISHING) + " (" + script.getExperienceTracker().getGainedLevels(Skill.FISHING) + ")", 121, 437); //current fishing level
            g.drawString("" + script.getExperienceTracker().getGainedXP(Skill.FISHING), 121, 467); // exp gained
            
            g.drawString(formatTime(runTime), 293, 406); // run time
            g.drawString("" + script.getExperienceTracker().getGainedXPPerHour(Skill.FISHING), 293, 467); //exp per hour
            
            
        	
            
            // Draw mouse cursor
            g.setColor(Color.ORANGE);
            g.fillOval(script.mouse.getPosition().x, script.mouse.getPosition().y, 10, 10);
            
        
		
		
	}
    public final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
    
    private State getState() {
        if(!script.inventory.contains(SMALLNET_ID)) {
        	return State.BANK;
        }
        if(script.inventory.isFull()){
            return State.BANK;
        }
        if(script.myPlayer().isUnderAttack()) {
        	return State.UNDERATTACK;
        }
	return State.FISH;
    }
    
	private void attacked() {
    	script.log("MATE WE ARE BEING ATTACKED!");
    	@SuppressWarnings("unchecked")
		NPC npc = script.getNpcs().closest(new Filter<NPC>() {

			@Override
			public boolean match(NPC n) {
				return (n.getLevel() >=script.myPlayer().getCombatLevel());
			}
			
		});
    	// Do we run or do we fight?
    	if(npc !=null || script.myPlayer().getHealthPercent() < 50) {
    		// We run because the npc attacking us is higher level than us, or our player health is too low.
        	
    		
    		
    		script.log("We are running away from "+npc.getName());
    		
    		script.getSettings().setRunning(true);
        	
        	if(script.getWalking().webWalk(IntermediateArea)) {
        		new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return IntermediateArea.contains(script.myPosition());
                    }
                }.sleep();
        	}
        	script.log("Okay we have safely made it back to the safe zone.");
        	
        	
        	try {
        		script.sleep(15000); 	
        	}catch(Exception e) {
        		script.log(e);
        	}
        	script.getLogoutTab().logOut();
        	script.log("Logged out because you were attacked by "+npc.getName()+" whilst fishing.");
    		
    	}else {
    		// Else we fight.
    		script.log("We shall now fight "+npc.getName());
    		if(script.getCombat().isAutoRetaliateOn() == false) {
    			script.getCombat().toggleAutoRetaliate(true);
    		}
    		new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                @Override
                public boolean condition() throws InterruptedException{
                     return (script.myPlayer().getHealthPercent() < 50);
                }
            }.sleep();
    		
    	}
	
    }
    
    private void fish() {
    	if(!atPosition){
            atPosition = true;
            if(!lumbridgeSwampFishingArea.contains(script.myPosition()) && !script.myPlayer().isMoving() && script.getWalking().webWalk(lumbridgeSwampFishingArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                        return lumbridgeSwampFishingArea.contains(script.myPosition());
                    }
                }.sleep();
            }
        }else if(!script.myPlayer().isAnimating() && !script.myPlayer().isMoving()){
        	
        	try {
        		script.sleep(random(500,13000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				script.log("Can't sleep.");
			}
        	
        	getClosestFishingSpot().ifPresent(npc -> {
                npc.interact(ACTION);      
            });
            
        }
		
	}
    private Optional<NPC> getClosestFishingSpot() {
        return Optional.ofNullable(script.getNpcs().closest(true, npc ->
                npc.getName().equals("Fishing spot") && npc.hasAction(ACTION) && lumbridgeSwampFishingArea.contains(npc) && !trickSpot.contains(npc)
        ));
    }

	private void getFromBank() {
		if(!lumbridgeBank.contains(script.myPosition()) && !script.myPlayer().isMoving()){
			// First we walk to the Intermediate Area. Webwalking has some issues with Lumbridge Upper Bank.
			if(script.getWalking().webWalk(IntermediateArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return IntermediateArea.contains(script.myPosition());
                    }
                }.sleep();
			}
			script.log("We reached the Lumbridge Castle Foyer.");
			
			if(script.getWalking().webWalk(PenultimateArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return PenultimateArea.contains(script.myPosition());
                    }
                }.sleep();
			}
			script.log("We reached the left stairwell.");
			
			
             if(script.getWalking().webWalk(lumbridgeBank)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return lumbridgeBank.contains(script.myPosition());
                    }
                }.sleep();
                
                
                
            }
        }else if(lumbridgeBank.contains(script.myPosition()) && !script.myPlayer().isMoving() && !script.bank.isOpen()){
            RS2Object bankSpot = script.getObjects().closest("Bank booth");
            if(bankSpot != null && bankSpot.exists()){
                if(bankSpot.interact("Bank")){
                    //open bank
                    new ConditionalSleep(30000, (int)(Math.random() * 500 + 300)){
                        @Override
                        public boolean condition() throws InterruptedException{
                             return script.bank.isOpen();
                        }
                    }.sleep();
                }
                
                
                if(script.bank.isOpen()){
                    
                	script.bank.depositAllExcept(SMALLNET_ID);
                    withdrawrequiredItem();
                  
                    script.bank.close();
                    atPosition = false;
                }
            }
        }
		
		
		
		
		
		
	}
	
	
	private void withdrawrequiredItem() {
		if(!script.inventory.contains(SMALLNET_ID)){
			
            if(script.bank.contains(SMALLNET_ID)){
                if(script.bank.withdraw(SMALLNET_ID, 1)){
                	waitforWithdrawal();
                	
                }
            }else{
            	script.log("Bank does not contains the required fishing items, and nor does your inventory." + SMALLNET);
                script.stop();
            }
            
        }
		
	}

	private void waitforWithdrawal() {
		new ConditionalSleep(1500, (int)(Math.random() * 500 + 300)){
            @Override
            public boolean condition() throws InterruptedException{
                 return script.inventory.contains(SMALLNET_ID);
            }
        }.sleep();
		
	}

	private void antibanProtocol() {
    	double probability = Math.random();
		if(probability < 0.25) {
			moveCamera.begin();
			script.log("Move Camera Antiban has begun.");
		}else if(probability < 0.50) {
			rightclicker.begin();
			script.log("Right Clicker Antiban has begun.");
		}else if(probability < 0.75) {
			resetMapDirection.begin();
			script.log("Reset Map Direction Antiban has begun.");
		}else {
			checkTabber.begin();
			script.log("CheckTabber has begun.");
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
