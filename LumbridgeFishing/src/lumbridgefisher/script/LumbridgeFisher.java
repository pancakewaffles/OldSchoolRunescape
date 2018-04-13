package lumbridgefisher.script;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.osbot.rs07.api.Combat;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import antiban.CheckTabber;
import antiban.MoveCamera;
import antiban.ResetMapDirection;
import antiban.RightClicky;




@ScriptManifest(author = "Author", name = "Lumbridge Fisher", info = "1.0", version = 1.0, logo = "")
public class LumbridgeFisher extends Script {
	
	
	
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
    private MoveCamera moveCamera = new MoveCamera(this); // 25% chance
    private ResetMapDirection resetMapDirection = new ResetMapDirection(this); // 25% chance
    private RightClicky rightclicker = new RightClicky(this,RIGHT_CLICKER_AREA_RADIUS); // 25% chance
    private CheckTabber checkTabber = new CheckTabber(this); // 25% chance
    private final double ANTIBAN_RECEPTION = 0.12;
    
    
  //GUI related variables
    private JFrame gui;
    
    private long startTime;
    private long runTime = 0;
    private Thread thread;
    private boolean started = false;
    private boolean isRunning = true;
    
    private enum State {
        FISH, BANK, UNDERATTACK
    };
    
    @Override
    public final void onStart(){
    	

		log(getCamera().getLowestPitchAngle());
    	
		
        gui();

        
    }

	@Override
	public int onLoop() throws InterruptedException {
		//check if the botting should start or wait
        if(!started){
            new ConditionalSleep(60*30*1000, 500){
                @Override
                public boolean condition() throws InterruptedException{
                    return started;
                }
            }.sleep();
        }else {
        	
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
        	
        	log("Checking Antiban.");
        	if(enableAntiban()) {
            	antibanProtocol();
            }
        }
		return random(1500, 2500);
	}
	


	@Override
    public final void onExit(){
        isRunning = false;
        status = "Stopped";
        log("Lumbridge Fisher has stopped.");
    }
    
    private State getState() {
        if(!this.inventory.contains(SMALLNET_ID)) {
        	return State.BANK;
        }
        if(this.inventory.isFull()){
            return State.BANK;
        }
        if(myPlayer().isUnderAttack()) {
        	return State.UNDERATTACK;
        }
	return State.FISH;
    }
    
	private void attacked() {
    	log("MATE WE ARE BEING ATTACKED!");
    	@SuppressWarnings("unchecked")
		NPC npc = getNpcs().closest(new Filter<NPC>() {

			@Override
			public boolean match(NPC n) {
				return (n.getLevel() >=myPlayer().getCombatLevel());
			}
			
		});
    	// Do we run or do we fight?
    	if(npc !=null || myPlayer().getHealthPercent() < 50) {
    		// We run because the npc attacking us is higher level than us, or our player health is too low.
        	
    		
    		
    		log("We are running away from "+npc.getName());
    		
    		getSettings().setRunning(true);
        	
        	if(getWalking().webWalk(IntermediateArea)) {
        		new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return IntermediateArea.contains(myPosition());
                    }
                }.sleep();
        	}
        	log("Okay we have safely made it back to the safe zone.");
        	
        	
        	try {
        		sleep(15000); 	
        	}catch(Exception e) {
        		log(e);
        	}
        	getLogoutTab().logOut();
        	log("Logged out because you were attacked by "+npc.getName()+" whilst fishing.");
    		
    	}else {
    		// Else we fight.
    		log("We shall now fight "+npc.getName());
    		if(getCombat().isAutoRetaliateOn() == false) {
    			getCombat().toggleAutoRetaliate(true);
    		}
    		new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                @Override
                public boolean condition() throws InterruptedException{
                     return (myPlayer().getHealthPercent() < 50);
                }
            }.sleep();
    		
    	}
	
    }
    
    private void fish() {
    	if(!atPosition){
            atPosition = true;
            if(!lumbridgeSwampFishingArea.contains(this.myPosition()) && !this.myPlayer().isMoving() && getWalking().webWalk(lumbridgeSwampFishingArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                        return lumbridgeSwampFishingArea.contains(myPosition());
                    }
                }.sleep();
            }
        }else if(!this.myPlayer().isAnimating() && !this.myPlayer().isMoving()){
        	
        	try {
				sleep(random(500,2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log("Can't sleep.");
			}
        	
        	getClosestFishingSpot().ifPresent(npc -> {
                npc.interact(ACTION);      
            });
            
        }
		
	}
    private Optional<NPC> getClosestFishingSpot() {
        return Optional.ofNullable(getNpcs().closest(true, npc ->
                npc.getName().equals("Fishing spot") && npc.hasAction(ACTION) && lumbridgeSwampFishingArea.contains(npc) && !trickSpot.contains(npc)
        ));
    }

	private void getFromBank() {
		if(!lumbridgeBank.contains(this.myPosition()) && !this.myPlayer().isMoving()){
			// First we walk to the Intermediate Area. Webwalking has some issues with Lumbridge Upper Bank.
			if(getWalking().webWalk(IntermediateArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return IntermediateArea.contains(myPosition());
                    }
                }.sleep();
			}
			log("We reached the Lumbridge Castle Foyer.");
			
			if(getWalking().webWalk(PenultimateArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return PenultimateArea.contains(myPosition());
                    }
                }.sleep();
			}
			log("We reached the left stairwell.");
			
			
             if(getWalking().webWalk(lumbridgeBank)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return lumbridgeBank.contains(myPosition());
                    }
                }.sleep();
                
                
                
            }
        }else if(lumbridgeBank.contains(this.myPosition()) && !this.myPlayer().isMoving() && !this.bank.isOpen()){
            RS2Object bankSpot = this.getObjects().closest("Bank booth");
            if(bankSpot != null && bankSpot.exists()){
                if(bankSpot.interact("Bank")){
                    //open bank
                    new ConditionalSleep(30000, (int)(Math.random() * 500 + 300)){
                        @Override
                        public boolean condition() throws InterruptedException{
                             return bank.isOpen();
                        }
                    }.sleep();
                }
                
                
                if(this.bank.isOpen()){
                    
                    this.bank.depositAllExcept(SMALLNET_ID);
                    withdrawrequiredItem();
                  
                    this.bank.close();
                    atPosition = false;
                }
            }
        }
		
		
		
		
		
		
	}
	
	
	private void withdrawrequiredItem() {
		if(!this.inventory.contains(SMALLNET_ID)){
			
            if(this.bank.contains(SMALLNET_ID)){
                if(this.bank.withdraw(SMALLNET_ID, 1)){
                	waitforWithdrawal();
                	
                }
            }else{
                log("Bank does not contains the required fishing items, and nor does your inventory." + SMALLNET);
                this.stop();
            }
            
        }
		
	}

	private void waitforWithdrawal() {
		new ConditionalSleep(1500, (int)(Math.random() * 500 + 300)){
            @Override
            public boolean condition() throws InterruptedException{
                 return inventory.contains(SMALLNET_ID);
            }
        }.sleep();
		
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
	
	
    private void gui(){
    	
        
        // Declare two constants for width and height of the GUI
        final int GUI_WIDTH = 350, GUI_HEIGHT = 150;
        
        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calculating x and y coordinates
        final int gX = (int) (screenSize.getWidth() / 2) - (GUI_WIDTH / 2);
        final int gY = (int) (screenSize.getHeight() / 2) - (GUI_HEIGHT / 2);

        // Create a new JFrame with the title "GUI"
        gui = new JFrame("Lumbridge Fisher");
        
        // Set the x coordinate, y coordinate, width and height of the GUI
        gui.setBounds(gX, gY, GUI_WIDTH, GUI_HEIGHT);

        gui.setResizable(false); // Disable resizing
        
        // Create a sub container JPanel
        JPanel panel = new JPanel();

        // Add it to the GUI
        gui.add(panel);

        JLabel label = new JLabel("All you got to do is press the Start button, from anywhere."); // Create a label
        label.setForeground(Color.black); // Set text colour to white
        panel.add(label); // Add it to the JPanel
        

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            //do some startup procedure
            started  = true;
            gui.setVisible(false);
            startTime = System.currentTimeMillis();
            getExperienceTracker().start(Skill.FISHING);
            thread = new Thread(){
                public synchronized void run(){
                    for(;;){
                        if(isRunning){
                            try {
                                runTime = System.currentTimeMillis() - startTime;
                                this.wait(50);
                            } catch (InterruptedException ex) {
                                log(ex);
                            }
                        }else{
                            break;
                        }
                    }
                }
            };
            thread.start();
            status = "Running";
            
            
        });
        panel.add(startButton);
        
        // Make the GUI visible
        gui.setVisible(true);
    }
	
    @Override
    public void onPaint(final Graphics2D g) {
        if(started){ // If the user has started the script
        	
            //g.drawImage(image, 1, 338, null);
            g.setColor(Color.DARK_GRAY);
            
            g.drawString(status, 121, 406); // run time
            g.drawString(getSkills().getStatic(Skill.FISHING) + " (" + getExperienceTracker().getGainedLevels(Skill.FISHING) + ")", 121, 437); //current fishing level
            g.drawString("" + getExperienceTracker().getGainedXP(Skill.FISHING), 121, 467); // exp gained
            
            g.drawString(formatTime(runTime), 293, 406); // run time
            g.drawString("" + getExperienceTracker().getGainedXPPerHour(Skill.FISHING), 293, 467); //exp per hour
            
        	
            
            // Draw mouse cursor
            g.setColor(Color.ORANGE);
            g.fillOval(mouse.getPosition().x, mouse.getPosition().y, 10, 10);
            
        }
    }
    
    /**
     * Format a little
     * @param ms
     * @return 
     */
    private final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
            s %= 60;
            m %= 60;
            h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
