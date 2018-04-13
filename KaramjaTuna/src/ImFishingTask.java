import org.osbot.rs07.script.Script;

import antiban.CheckTabber;
import antiban.MoveCamera;
import antiban.ResetMapDirection;
import antiban.RightClicky;

import static org.osbot.rs07.script.MethodProvider.random;

/**import org.osbot.rs07.api.map.Area;
 import org.osbot.rs07.api.map.constants.Banks;
 import org.osbot.rs07.api.model.Entity;
 import org.osbot.rs07.api.model.NPC;
 import org.osbot.rs07.script.Script;
 import org.osbot.rs07.utility.ConditionalSleep;

 import static org.osbot.rs07.script.MethodProvider.random;

 /**
 * Created by tek on 8/14/2016.
 */
public class ImFishingTask extends Task {

	 // Custom Antiban features
    private final int RIGHT_CLICKER_AREA_RADIUS = 6;
    private MoveCamera moveCamera; // 25% chance
    private ResetMapDirection resetMapDirection; // 25% chance
    private RightClicky rightclicker; // 25% chance
    private CheckTabber checkTabber; // 25% chance
    private final double ANTIBAN_RECEPTION = 0.12;


    public ImFishingTask(Script script) {
        super(script);
        
       moveCamera = new MoveCamera(this.script);
       resetMapDirection = new ResetMapDirection(this.script);
       rightclicker = new RightClicky(this.script,RIGHT_CLICKER_AREA_RADIUS);
       checkTabber = new CheckTabber(this.script);
    }

    @Override
    public boolean verify() {
        return  script.myPlayer().isAnimating() && !script.inventory.isFull();
    }

    @Override
    public int execute() {
        script.log("Fishing and checking Antiban.");
        
        // Run the antiban whilst I am fishing.
    	if(enableAntiban()) {
        	antibanProtocol();
        }
        
        return random(2000,4200);
    }

    @Override
    public String describe() {
        return "Im Fishing.....";
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