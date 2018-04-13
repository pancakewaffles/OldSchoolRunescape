import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

// This script fishes for anchovies at Lumbridge up to a certain limit (say 500), then goes and cooks them at the Lumbridge range cooker. It is an amalgamation of two scripts I have.

@ScriptManifest(author = "Author", name = "LumbridgeAnchovies", version = 1.0, logo = "", info = "Fishes Anchovies at Lumbridge Swamp, then cooks them.")
public class LumbridgeAnchovies extends Script {

	private int fishCaught;
	private int limit = 100;
	private boolean shouldIfishAnchovies;
	private Task activeTask;
	private Task FishAnchoviesTask;
	private Task CookAnchoviesTask;
	
	
	


	

    


    @Override
    public void onStart() {

        log("Lumbridge Fishies has begun.");
        
        fishCaught = 0;
        FishAnchoviesTask = new FishAnchovies(this);
        CookAnchoviesTask = new CookAnchovies(this);
          


    }

    @Override
    public int onLoop() throws InterruptedException {
    	
    	if(fishCaught < limit) {
    		shouldIfishAnchovies = true;
    	}else {
    		shouldIfishAnchovies = false;
    	}
    	
    
    	if(shouldIfishAnchovies == true) {
    		activeTask = FishAnchoviesTask;
    	}else {
    		activeTask = CookAnchoviesTask;
    	}

        return activeTask.execute();
    }



    public void onPaint(Graphics2D g) {
    	
    	activeTask.paint(g);
    	g.drawString("Fish Caught :" + fishCaught, 293, 480); //exp per hour


    }
	public void onMessage(Message m){
		if(m.getMessage().contains("You catch some shrimps.") || m.getMessage().contains("You catch some anchovies.")){
			fishCaught++;
		}
	}

 
}
