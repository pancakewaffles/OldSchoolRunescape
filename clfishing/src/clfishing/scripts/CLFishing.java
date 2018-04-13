/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clfishing.scripts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import clfishing.areacontroller.FishingAreas;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.LogoutTab;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.utility.ConditionalSleep;

import antiban.CheckTabber;
import antiban.MoveCamera;
import antiban.ResetMapDirection;
import antiban.RightClicky;
import clfishing.areacontroller.BankingAreas;
import clfishing.commons.DistanceMeasure;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osbot.rs07.api.Walking;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.Combat;

/**
 *
 * @original author Lagoni
 */
@ScriptManifest(author = "Author", name = "CL Private Fisher", info = "0.5b", version = 1.0, logo = "")
public class CLFishing extends Script{
	
	
    private FishingAreas fishingAreaController = new FishingAreas();
    private BankingAreas bankingAreaController = new BankingAreas();
    private Area closestFishingArea;
    private Area closestBank;
    private NPC fishingSpot;
    private boolean atPosition = false;
    private String status = "Stopped";
    private boolean hasPainted = false;
    private Image image;
    private FishingType lastLevelFishingType;
    private FishingType currentLevelFishingType;
    
    
    //GUI related variables
    private JFrame gui;
    private BotType currentBotType = BotType.PF;
    private FishingType currentFishingType = FishingType.SMALLNET;
    private JComboBox<FishingType> fishingType = new JComboBox<>(new FishingType[]{FishingType.SMALLNET});
    private JComboBox<BotType> botType = new JComboBox<>(new BotType[]{BotType.NF, BotType.PF});
    private long startTime;
    private long runTime = 0;
    private Thread thread;
    private boolean started = false;
    private boolean isRunning = true;
    
    
    // Custom Antiban features
    private MoveCamera moveCamera; // 25% chance
    private ResetMapDirection resetMapDirection; // 25% chance
    private RightClicky rightclicker; // 25% chance
    private CheckTabber checkTabber; // 25% chance
    
    
    /**
     * PF = Power Fishing
     * NF = Normal Fishing / banking with the fish
     */
    private enum BotType{
        PF, NF
    }
    
    /**
     * LEVEL = Finding the best suitable fishingtype
     */
    private enum FishingType{
        SMALLNET("Small fishing net", "Net", new int[]{303}),
        LURE("Fly fishing rod", "Lure", new int[]{309, 314}),
        BAIT("Fishing rod", "Bait", new int[]{307, 313}),
        CAGE("Lobster pot", "Cage", new int[]{311}),
        HARPOON("Harpoon", "Harpoon", new int[]{301}),
        BIGNET("Big fishing net", "Net", new int[]{305}),
        LEVEL("?", "?", new int[]{});
 
        private final String tool;
        private final String action;
        private final int[] reqItems;
        
        FishingType(String tool, String action, int[] reqItems) {
            this.tool = tool;
            this.action = action;
            this.reqItems = reqItems;
        }
 
        public String getTool() {
            return tool;
        }
 
        public String getAction() {
            return action;
        }
        
        public int[] getRequiredItems(){
            return reqItems;
        }
    }
    
    /**
     * Which state the bot is in
     */
    private enum BotState {
        FISH, DROP, BANK, UNDERATTACK
    };
    
    /**
     * Lets do some fun stuff
     * @return
     * @throws InterruptedException 
     */
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
        	rightclicker = new RightClicky(this, myPlayer().getArea(30));
        	
            if(currentFishingType == FishingType.LEVEL){
                getFishType();
            }
            //do the botting
            if(lastLevelFishingType != currentLevelFishingType){
                setRelevantArea();
                lastLevelFishingType = currentLevelFishingType;
            }
            switch(currentBotType){
                case PF: // lets power fish.
                    switch(getState()){
                        case BANK:
                            getFromBank();
                            break;
                        case FISH:
                            fish();
                            break;
                        case DROP://drop everything
                                if(currentFishingType == FishingType.LEVEL){
                                    this.inventory.dropAllExcept(currentLevelFishingType.getRequiredItems());
                                }else {
                                    this.inventory.dropAllExcept(currentFishingType.getRequiredItems());
                                }
                        break;
                    }
                break;
                case NF:
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
                break;
            }
            
            if(enableAntiban()) {
            	antibanProtocol();
            }
        }
        
        
        
        return random(1500, 4000);
    }
    
    /**
     * Lets fish
     */
    private void fish() {
        if(!atPosition){
            atPosition = true;
            if(!closestFishingArea.contains(this.myPosition()) && !this.myPlayer().isMoving() && getWalking().webWalk(closestFishingArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                        return closestFishingArea.contains(myPosition());
                    }
                }.sleep();
            }
        }else if(!this.myPlayer().isAnimating() && !this.myPlayer().isMoving()){
            fishingSpot = this.getNpcs().closest("Fishing spot");
            if (fishingSpot != null && fishingSpot.exists()) {
                if(currentFishingType == FishingType.LEVEL){
                    doFishing(fishingSpot, currentLevelFishingType.getAction());
                }else{
                    doFishing(fishingSpot, currentFishingType.getAction());
                }
            }
        }
    }
    
    /**
     * Lets do the fishing
     * @param fishingSpot
     * @param action 
     */
    private void doFishing(NPC fishingSpot, String action){
    	fishingSpot.interact(action);
		
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
		if(Math.random() < 0.20) {
			return true;
		}else {
			return false;
		}
	}

	// A small fix if you are being attacked whilst fishing.
    // Runs back to the bank and logs out.
    @SuppressWarnings("unchecked")
	private void attacked() {
    	log("MATE WE ARE BEING ATTACKED!");
    	// Do we run or do we fight?
    	if(npcs.closest(new Filter<NPC>() {

			@Override
			public boolean match(NPC n) {
				return (n.getLevel() >= myPlayer().getCombatLevel());
			}
    		
    	})!=null || myPlayer().getHealthPercent() < 50) {
    		// We run because the npc attacking us is higher level than us, or our player health is too low.
        	getSettings().setRunning(true);
        	
        	if(getWalking().webWalk(closestBank)) {
        		new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return closestBank.contains(myPosition());
                    }
                }.sleep();
        	}
        	log("Okay we have safely made it back to the bank.");
        	
        	if(closestBank.contains(myPosition())) {
        		getFromBank();
        	}
        	
        	try {
        		sleep(15000); 	
        	}catch(Exception e) {
        		log(e);
        	}
        	getLogoutTab().logOut();
        	log("Logged out because you were attacked by something whilst fishing.");
    		
    	}else {
    		// Else we fight.
    		Combat combat = new Combat();
    		if(combat.isAutoRetaliateOn() == false) {
    			combat.toggleAutoRetaliate(true);
    		}
    		new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                @Override
                public boolean condition() throws InterruptedException{
                     return (myPlayer().getHealthPercent() < 50);
                }
            }.sleep();
    		
    	}
    	

    	
    	
    }
    /**
     * Gets the fishingtype depending on the most relevant one
     * @return 
     */
    private void getFishType(){
        if(getSkills().getStatic(Skill.FISHING) >= 1 && getSkills().getStatic(Skill.FISHING) < 20){
            currentLevelFishingType = FishingType.SMALLNET;
        }else if(getSkills().getStatic(Skill.FISHING) >= 20){
            currentLevelFishingType = FishingType.LURE;
        }else{
            currentLevelFishingType = FishingType.SMALLNET;
        }
    }
    
    /**
     * Get some items from the bank
     */
    private void getFromBank(){
        if(!closestBank.contains(this.myPosition()) && !this.myPlayer().isMoving()){
            if(getWalking().webWalk(closestBank)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return closestBank.contains(myPosition());
                    }
                }.sleep();
                
                
                
            }
        }else if(closestBank.contains(this.myPosition()) && !this.myPlayer().isMoving() && !this.bank.isOpen()){
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
            }
        }else if(this.bank.isOpen()){
            if(currentFishingType == FishingType.LEVEL){
                this.bank.depositAllExcept(currentLevelFishingType.getRequiredItems());
                withdrawItem(currentLevelFishingType.getRequiredItems());
            }else {
                this.bank.depositAllExcept(currentFishingType.getRequiredItems());
                withdrawItem(currentFishingType.getRequiredItems());
            }
            this.bank.close();
            atPosition = false;
        }
    }
    
    /**
     * Withdraw an item
     * @param item 
     */
    private void withdrawItem(int[] withDrawItems){
        if(!this.inventory.contains(withDrawItems[0])){
            if(this.bank.contains(withDrawItems[0])){
                if(this.bank.withdraw(withDrawItems[0], 1)){
                    //withdraw
                    waitForItemWithDraw(withDrawItems[0]);
                }
            }else{
                log("Bank does not contains the item " + withDrawItems[0]);
                this.stop();
            }
        }
        if(withDrawItems.length > 1){
            if(!this.inventory.contains(withDrawItems[1])){
                if(this.bank.contains(withDrawItems[1])){
                    if(this.bank.withdrawAll(withDrawItems[1])){
                        //withdraw
                        waitForItemWithDraw(withDrawItems[1]);
                    }else{
                        log("error");
                    }
                }else{
                    log("Bank does not contains the item " + withDrawItems[1]);
                    this.stop();
                }
            }
        }
    }
    /**
     * Sleeps while while waiting for the item
     * @param item 
     */
    private void waitForItemWithDraw(int item){
        new ConditionalSleep(3000, (int)(Math.random() * 500 + 300)){
            @Override
            public boolean condition() throws InterruptedException{
                 return inventory.contains(item);
            }
        }.sleep();
    }
    
    /**
     * Get the botstate depending on the characters situation
     * @return 
     */
    private BotState getState() {
        if(currentFishingType == FishingType.LEVEL){
            if(!this.inventory.contains(currentLevelFishingType.getRequiredItems())){
                return BotState.BANK;
            }
        }else if(!this.inventory.contains(currentFishingType.getRequiredItems())){
            return BotState.BANK;
        }
        if(this.inventory.isFull()){
            switch(currentBotType){
                case PF:
                    return BotState.DROP;
                case NF:
                    return BotState.BANK;
            }
        }
        if(myPlayer().isUnderAttack()) {
        	return BotState.UNDERATTACK;
        }
	return BotState.FISH;
    }
    
    /**
     * When the script starts do something
     */
    @Override
    public final void onStart(){
    	// Load up antibans.
		moveCamera = new MoveCamera(this);
		
		resetMapDirection = new ResetMapDirection(this);
		checkTabber = new CheckTabber(this);
    	
        gui();
        try {
            BufferedImage img = ImageIO.read(new URL("http://i.imgur.com/0BG49GF.png"));
            image = img;
        } catch (MalformedURLException ex) {
            log(ex);
        } catch (IOException ex) {
            log(ex);
        }
        
        
    }
    
    /**
     * when we exit do something
     */
    @Override
    public final void onExit(){
        isRunning = false;
        status = "Stopped";
        log("CLfisher stopped working");
    }
    
    /**
     * Lets paint something
     * @param g 
     */
    @Override
    public void onPaint(final Graphics2D g) {
        if(started){ // If the user has started the script
        	
            //g.drawImage(image, 1, 338, null);
            g.setColor(Color.DARK_GRAY);
            
            g.drawString(status, 121, 406); // run time
            g.drawString(getSkills().getStatic(Skill.FISHING) + " (" + getExperienceTracker().getGainedLevels(Skill.FISHING) + ")", 121, 437); //current fishing level
            g.drawString("" + getExperienceTracker().getGainedXP(Skill.FISHING), 121, 467); // exp gained
            
            g.drawString(formatTime(runTime), 293, 406); // run time
            g.drawString(currentFishingType.toString(), 293, 437); // run time
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
    
    /**
     * Do some gui work
     */
    private void gui(){
    	
        
        if(!this.worlds.isMembersWorld()){
            fishingType = new JComboBox<>(new FishingType[]{FishingType.SMALLNET, FishingType.LURE, FishingType.HARPOON, FishingType.CAGE, FishingType.BAIT, FishingType.LEVEL});
        }else{
            fishingType = new JComboBox<>(new FishingType[]{FishingType.SMALLNET, FishingType.LURE, FishingType.HARPOON, FishingType.CAGE, FishingType.BAIT, FishingType.BIGNET, FishingType.LEVEL});
        }
        // Declare two constants for width and height of the GUI
        final int GUI_WIDTH = 350, GUI_HEIGHT = 150;
        
        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calculating x and y coordinates
        final int gX = (int) (screenSize.getWidth() / 2) - (GUI_WIDTH / 2);
        final int gY = (int) (screenSize.getHeight() / 2) - (GUI_HEIGHT / 2);

        // Create a new JFrame with the title "GUI"
        gui = new JFrame("CLFisher");
        
        // Set the x coordinate, y coordinate, width and height of the GUI
        gui.setBounds(gX, gY, GUI_WIDTH, GUI_HEIGHT);

        gui.setResizable(false); // Disable resizing
        
        // Create a sub container JPanel
        JPanel panel = new JPanel();

        // Add it to the GUI
        gui.add(panel);

        JLabel label = new JLabel("Script type"); // Create a label
        label.setForeground(Color.black); // Set text colour to white
        panel.add(label); // Add it to the JPanel
        
        botType.setSelectedItem(BotType.PF);
        // Add an action listener, to listen for user's selections, assign to a variable called selectedTree on selection.
        botType.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBotType = ((BotType)botType.getSelectedItem());
                
            }
        });
        // Add the select box to the JPanel
        panel.add(botType);
        
        JLabel fishing_type = new JLabel("Fishing type"); // Create a label
        fishing_type.setForeground(Color.black); // Set text colour to white
        panel.add(fishing_type); // Add it to the JPanel
        
        // Add an action listener, to listen for user's selections, assign to a variable called selectedTree on selection.
        fishingType.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFishingType = ((FishingType)fishingType.getSelectedItem());
            }
        });
        // Add the select box to the JPanel
        panel.add(fishingType);
        
        
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
            
            if(currentFishingType == FishingType.LEVEL){
                getFishType();
            }
            
            setRelevantArea();
        });
        panel.add(startButton);
        
        // Make the GUI visible
        gui.setVisible(true);
    }
    
    /**
     * Find the most relevant areas to fish
     */
    private void setRelevantArea(){
        List<Area> potentialAreas = new ArrayList();
        if(currentFishingType == FishingType.SMALLNET || currentFishingType == FishingType.BAIT || (currentFishingType == FishingType.LEVEL && currentLevelFishingType == FishingType.SMALLNET)){
            potentialAreas.addAll(fishingAreaController.getF2pSmallNetBaitFishingAreas().getAllAreas());
            if(this.worlds.isMembersWorld()){
                potentialAreas.addAll(fishingAreaController.getP2pSmallNetBaitFishingAreas().getAllAreas());
            }
        }else if(currentFishingType == FishingType.LURE || currentFishingType == FishingType.BAIT || (currentFishingType == FishingType.LEVEL && currentLevelFishingType == FishingType.LURE)){
            potentialAreas.addAll(fishingAreaController.getF2pLureBaitFishingAreas().getAllAreas());
            if(this.worlds.isMembersWorld()){
                potentialAreas.addAll(fishingAreaController.getP2pLureBaitFishingAreas().getAllAreas());
            }
        }else if(currentFishingType == FishingType.CAGE || currentFishingType == FishingType.HARPOON){
            potentialAreas.addAll(fishingAreaController.getF2pCageHarpoonFishingAreas().getAllAreas());
            if(this.worlds.isMembersWorld()){
                potentialAreas.addAll(fishingAreaController.getP2pCageHarpoonFishingAreas().getAllAreas());
            }
        }else if((currentFishingType == FishingType.BIGNET || currentFishingType == FishingType.HARPOON) && this.worlds.isMembersWorld()){
            potentialAreas.addAll(fishingAreaController.getP2pBigNetHarpoonFishingArea().getAllAreas());
        }
        List<Area> potentialBankAreas;
        switch(currentBotType){
            case PF:
                closestFishingArea = DistanceMeasure.getClosestArea(potentialAreas, this.myPosition());
                potentialBankAreas = new ArrayList();
                potentialBankAreas.addAll(bankingAreaController.getF2pBankingAreas().getAllBanks());
                if(this.worlds.isMembersWorld()){
                    potentialBankAreas.addAll(bankingAreaController.getP2pBankingAreas().getAllBanks());
                }
                closestBank = DistanceMeasure.getClosestArea(potentialBankAreas, closestFishingArea);
                break;
            case NF:
                potentialBankAreas = new ArrayList();
                potentialBankAreas.addAll(bankingAreaController.getF2pBankingAreas().getAllBanks());
                if(this.worlds.isMembersWorld()){
                    potentialBankAreas.addAll(bankingAreaController.getP2pBankingAreas().getAllBanks());
                }
                int distance = Integer.MAX_VALUE;
                for(Area a: potentialAreas){
                    for(Area ba: potentialBankAreas){
                        int currentDistance = a.getRandomPosition().distance(ba.getRandomPosition());
                        if(currentDistance < distance ){
                            closestBank = ba;
                            closestFishingArea = a;
                            distance = currentDistance;
                        }
                    }
                }
                
                
                
                
                break;
        }
    }
}
