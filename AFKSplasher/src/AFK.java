import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.input.mouse.ClickMouseEvent;
import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "AFK Splasher", author = "Author", version = 1.2, info = "", logo = "") 

public class AFK extends Script {

   
    private long startTime;
    public final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
 @Override
    public void onStart() {
    	 startTime = System.currentTimeMillis();
        //Code here will execute before the loop is started
    	 getExperienceTracker().start(Skill.MAGIC);
    }

    

    @Override

    public void onExit() {

        //Code here will execute after the script ends

    }

    

@Override
    public int onLoop() throws InterruptedException {
    	
    	mouse.click(random(556, 730), random(220, 450), false);
    
    	sleep(random(180000,1080000)); //3 minutes to 18 minutes
        return 100; //The amount of time in milliseconds before the loop starts over

    }

    @Override

    public void onPaint(Graphics2D g) {
    	Font font = new Font("Times New Roman", Font.BOLD, 14);
    	Point mP = getMouse().getPosition();
    	g.setColor(Color.white);
    	g.drawLine(mP.x - 10, mP.y + 10, mP.x + 10, mP.y - 10); 
    	g.setColor(Color.CYAN);
    	g.drawLine(mP.x + 10, mP.y + 10, mP.x - 10, mP.y - 10);
    	g.drawString("XP Gained: "+ getExperienceTracker().getGainedXP(Skill.MAGIC), 10, 250);
    	g.drawString("XP / HR: "+ getExperienceTracker().getGainedXPPerHour(Skill.MAGIC), 10, 270);
    	g.drawString("Time to LVL: "+ formatTime(getExperienceTracker().getTimeToLevel(Skill.MAGIC)), 10, 290);
    	final long runTime = System.currentTimeMillis() - startTime;
    	g.drawString("Time Ran: "+ formatTime(runTime), 10, 310);
    	
    	
        //This is where you will put your code for paint(s)

    }

}
