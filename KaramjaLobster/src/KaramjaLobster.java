import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import static org.osbot.rs07.script.MethodProvider.random;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@ScriptManifest(author = "Author", name = "Karamja Lobster", version = 1.0, logo = "", info = "Fishes for Lobster on Karamja, banking at Port Sarrim deposit box.")
public class KaramjaLobster extends Script {

    
    private int lobsterCaught;
    private int fishExpGained;
    private long startTime;
    
    // For the setup.
	private String FISHING_EQUIPMENT = "Lobster pot";
	private String COINS = "Coins";
	private Area BANK = Banks.FALADOR_EAST;


    private ArrayList<Task> tasks = new ArrayList<>();


    @Override
    public void onStart() {

        log("Script Started");

        getExperienceTracker().start(Skill.FISHING);
        startTime = System.currentTimeMillis();

        tasks.add(new BankTask(this));
        tasks.add(new WalkToKaramjaTask(this));
        tasks.add(new FishTask(this));
        tasks.add(new ImFishingTask(this));
        


    }

    @Override
    public int onLoop() throws InterruptedException {
    	
    	if(getInventory().contains(FISHING_EQUIPMENT) && getInventory().contains(COINS)) {
    		
    		for (Task task : tasks) {
    			if (task.verify())
    				return task.execute();
    		}
    	}else {
    		// We will have to grab the required items from the bank.
    		// We will use Falador East Bank because that is the nearest to Port Sarim.
    		if(!BANK.contains(myPlayer())) {
    			getWalking().webWalk(BANK);
    		}else {
    			// Once in area, open bank and deposit all, withdraw all coins and 1 harpoon, in that order.
    			getRequiredItems();
    			
    		}
    	}
        return random(600, 1800);
    }
    
	private void getRequiredItems() {
		if (getBank().isOpen()) {
			try {
				sleep(random(300, 500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log("Can't sleep.");
			}

			getBank().depositAll();
			
			getBank().withdraw(FISHING_EQUIPMENT,1);
			
			getBank().withdrawAll(COINS);
			
		} else {
			RS2Object bankBooth = getObjects().closest("Bank booth");
			if (bankBooth != null) {
				if (bankBooth.isVisible()) {
					bankBooth.interact("Bank");
					try {
						sleep(random(2000, 3000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						log("Can't sleep.");
					}
				} else {
					getCamera().toEntity(bankBooth);
				}
			}

		}
		
	}


    public void onPaint(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.drawString("dontbuzz Karamja Lobster", 10, 40);

        fishExpGained = getExperienceTracker().getGainedXP(Skill.FISHING);
        g.setColor(Color.black);
        g.drawString("Fishing Exp Gained: " + fishExpGained, 10, 55);

        lobsterCaught = fishExpGained / 90;
        g.drawString("Lobster Caught: " + lobsterCaught, 10, 70);

        final long runTime = System.currentTimeMillis() - startTime;
        g.drawString("Time Running " + formatTime(runTime), 10, 85);
        
        // Draw mouse cursor
        g.setColor(Color.ORANGE);
        g.fillOval(mouse.getPosition().x, mouse.getPosition().y, 10, 10);


    }

    public final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
