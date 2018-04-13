
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class CookAnchovies extends Task {
	
	Area lumbyRange  = new Area(3205, 3217, 3213, 3212);
    
    private Area PenultimateArea = new Area(3206,3209,3210,3211);
	
    String rawName;
    Area bankArea;
    Area rangeArea;
    
    private long startTime;
    
	
	
    public CookAnchovies(Script script) {
        super(script);
        rawName = "Raw shrimps";
        rangeArea = lumbyRange;
        bankArea = Banks.LUMBRIDGE_UPPER;
        
        

       
        startTime = System.currentTimeMillis();
        script.getExperienceTracker().start(Skill.COOKING);
        script.log("Starting Up");
    }

	@Override
	public boolean verify() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int execute(){
		switch (this.getState()) {
        case BANK: {
            try {
				doBank();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				script.log(e);
			}
            break;
        }
        case USERANGE: {
            try {
				useCookRange();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				script.log(e);
			}
            break;
        }
        case COOK: {
            try {
				doCook();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				script.log(e);
			}
            break;
        }
        case WALKTOBANK: {
            walktoLumbridgeBank();
            break;
        }
        case WALKTORANGE: {
            walktoRange();
            break;
        }
    }
    return script.random(200, 300);
	}

	private void walktoRange() {
		// First we walk to the PenultimateArea. Webwalking has some issues with Range and Lumbridge Castle in general.	
		if(script.getWalking().webWalk(PenultimateArea)){
            new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                @Override
                public boolean condition() throws InterruptedException{
                     return PenultimateArea.contains(script.myPosition());
                }
            }.sleep();
		}
		script.log("We reached the left stairwell.");
		
		
         if(script.getWalking().webWalk(rangeArea)){
            new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                @Override
                public boolean condition() throws InterruptedException{
                     return rangeArea.contains(script.myPosition());
                }
            }.sleep();
            
            
            
        }
		
	}

	private void walktoLumbridgeBank() {
		// First we walk to the PenultimateArea. Webwalking has some issues with Lumbridge Upper Bank.	
		if(script.getWalking().webWalk(PenultimateArea)){
            new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                @Override
                public boolean condition() throws InterruptedException{
                     return PenultimateArea.contains(script.myPosition());
                }
            }.sleep();
		}
		script.log("We reached the left stairwell.");
		
		
         if(script.getWalking().webWalk(bankArea)){
            new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                @Override
                public boolean condition() throws InterruptedException{
                     return bankArea.contains(script.myPosition());
                }
            }.sleep();
            
            
            
        }
		
	}

	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(Graphics2D g) {
	
        final long runTime = System.currentTimeMillis() - startTime;
        final int x = script.getMouse().getPosition().x;
        final int y = script.getMouse().getPosition().y;
        
        g.drawString(formatTime(runTime), 125, 375);
        g.drawString("", 125, 390);
        g.drawString(new StringBuilder().append(script.getSkills().getStatic(Skill.COOKING)).toString(), 125, 405);
        g.drawString(new StringBuilder().append(script.getExperienceTracker().getGainedLevels(Skill.COOKING)).toString(), 125, 420);
        g.drawString(new StringBuilder().append(formatValue(script.getExperienceTracker().getGainedXP(Skill.COOKING))).toString(), 125, 435);
        g.drawString(new StringBuilder().append(formatValue(script.getExperienceTracker().getGainedXPPerHour(Skill.COOKING))).toString(), 125, 450);
        g.drawString(new StringBuilder().append(formatValue(script.getSkills().experienceToLevel(Skill.ATTACK))).toString(), 125, 465);
        
     // Draw mouse cursor
        g.setColor(Color.ORANGE);
        g.fillOval(script.mouse.getPosition().x, script.mouse.getPosition().y, 10, 10);
		
	}
	
	
	 private enum State
	    {
	        USERANGE("USERANGE", 0), 
	        COOK("COOK", 1), 
	        BANK("BANK", 2), 
	        WALKTOBANK("WALKTOBANK", 3), 
	        WALKTORANGE("WALKTORANGE", 4), 
	        WAIT("WAIT", 5);
	        
	        private State(final String s, final int n) {
	        }
	    }
	 
	 
	 private State getState() {
	        if (!script.inventory.contains(new String[] { this.rawName }) && bankArea.contains((Entity)script.myPlayer())) {
	            return State.BANK;
	        }
	        if (script.inventory.contains(new String[] { this.rawName }) && !script.myPlayer().isAnimating() && rangeArea.contains((Entity)script.myPlayer()) && !script.widgets.isVisible(307, 2)) {
	            return State.USERANGE;
	        }
	        if (script.widgets.isVisible(307, 2) && script.inventory.contains(new String[] { this.rawName }) && this.rangeArea.contains((Entity)script.myPlayer())) {
	            return State.COOK;
	        }
	        if (!script.inventory.contains(new String[] { this.rawName }) && !this.bankArea.contains((Entity)script.myPlayer())) {
	            return State.WALKTOBANK;
	        }
	        if (script.inventory.contains(new String[] { this.rawName }) && !this.rangeArea.contains((Entity)script.myPlayer())) {
	            return State.WALKTORANGE;
	        }
	        return State.WAIT;
	    }
	 
	 public final String formatTime(final long ms) {
	        long s = ms / 1000L;
	        long m = s / 60L;
	        long h = m / 60L;
	        s %= 60L;
	        m %= 60L;
	        h %= 24L;
	        return String.format("%02d:%02d:%02d", h, m, s);
	    }
	    
	 
	 public final String formatValue(final int v) {
	        return (v > 1000000) ? String.format("%.2fm", v / 1000000) : ((v > 1000) ? String.format("%.1fk", v / 1000) : new StringBuilder(String.valueOf(v)).toString());
	    }
	 
	 
	    private void doBank() throws InterruptedException {
	        final Entity closestBankBooth = script.objects.closest(new String[] { "Bank Booth" });
	        if (script.bank.isOpen()) {
	            if (script.inventory.isEmpty()) {
	            	
	            	// Check if you have raw Anchovies in Bank. If you don't, log out.
	            	long rawFishLeft =  script.getBank().getAmount(rawName);
	            	if(rawFishLeft < 1) {
						if(rawName == "Raw anchovies") {
							script.getBank().close();
							script.sleep(script.random(75,150));
							script.getLogoutTab().open();
							script.sleep(script.random(75,150));
							script.getLogoutTab().logOut();
							script.stop();
						}else {
							rawName = "Raw anchovies";
						}
						
						
	            	}
	            	
	            	
	            	
	            	script.bank.withdrawAll(this.rawName);
	            	script.bank.close();
	            }
	            else {
	            	script.bank.depositAll();
	            }
	        }
	        else {
	            closestBankBooth.interact(new String[] { "Bank" });
	            script.log("Using Bank Booth");
	            script.sleep((long)script.random(750, 1500));
	        }
	    }
	    
	    

	    
	    private void useCookRange() throws InterruptedException {
	        if (script.inventory.contains(new String[] { this.rawName }) && this.rangeArea.contains((Entity)script.myPlayer()) && script.players.myPlayer().getAnimation() == -1) {
	        	script.getInventory().getItem(new String[] { this.rawName }).interact(new String[] { "Use" });
	        	script.log("Using " + this.rawName + " on range");
	            new ConditionalSleep(5000) {
	                public boolean condition() throws InterruptedException {
	                    return script.getInventory().isItemSelected();
	                }
	            }.sleep();
	            ((RS2Object)script.objects.closest(new String[] { "Cooking range" })).interact(new String[] { "Use" });
	            script.sleep(script.random(1000, 1500));
	            new ConditionalSleep(5000) {
	                public boolean condition() throws InterruptedException {
	                    return script.widgets.isVisible(307, 2);
	                }
	            }.sleep();
	        }
	    }
	    
	    private void doCook() throws InterruptedException {
	        if (script.widgets.isVisible(307, 2) && script.inventory.contains(new String[] { this.rawName })) {
	        	script.widgets.interact(307, 2, "Cook all");
	        	script.log("Cooking All");
	        	script.getMouse().moveOutsideScreen();
	        	script.sleep(script.random(1500, 3000));
	            new ConditionalSleep(60000) {
	                public boolean condition() throws InterruptedException {
	                    return !script.myPlayer().isAnimating();
	                }
	            }.sleep();
	            script.log("Sleeping till no animation");
	            
	            // Pretend I afk-ed.
	            script.sleep(script.random(500,13000));
	        }
	    }



}
