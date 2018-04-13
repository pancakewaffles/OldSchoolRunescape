import java.awt.Graphics2D;

import org.osbot.rs07.api.GroundItems;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import antiban.CheckTabber;
import antiban.MoveCamera;
import antiban.ResetMapDirection;
import antiban.RightClicky;

@ScriptManifest(author = "Author", info = "Picks feathers",name = "Feather Fairy", version = 0.1, logo = "")
public class main extends Script {
	
	Area featherCollectionArea;
	boolean inArea;
	
    // Custom Antiban features
    private boolean antiban = false;
    private MoveCamera moveCamera; // 25% chance
    private ResetMapDirection resetMapDirection; // 25% chance
    private RightClicky rightclicker; // 25% chance
    private CheckTabber checkTabber; // 25% chance

	@Override
	public void onExit() throws InterruptedException {
		log("This is printed to the logger when the script exits.");
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.drawString("Feather Fairy", 10, 10);
		
	}

	@Override
	public void onStart() throws InterruptedException {
		
		// Load up the antibans.
		moveCamera = new MoveCamera(this);
		rightclicker = new RightClicky(this,featherCollectionArea);
		resetMapDirection = new ResetMapDirection(this);
		checkTabber = new CheckTabber(this);
		
		Position topLeft = new Position(3225,3287,0);
		Position bottomRight = new Position(3237,3301,0);
		featherCollectionArea = new Area(topLeft,bottomRight);
		
		if(Banks.VARROCK_WEST.contains(myPlayer().getPosition())) {
			log("I am in the bank!");
		}
			
		if(featherCollectionArea.contains(myPosition())) {
			inArea = true;	
		}else {
			inArea = false;
			log("Go to the area.");
		}
		
			
		
		//getInventory();
		//myPlayer();
		//getWalking();
			
		
		
	}

	@Override
	public int onLoop() throws InterruptedException {
		
		if(inArea==true) {

			if(randomise()<=0.5) {
				GroundItems items = getGroundItems();
				Entity feather = items.closest("Feather");
				if(feather!=null && feather.isVisible() && featherCollectionArea.contains(feather)) {
						getMouse().move(feather.getX(),feather.getY());	
						getMouse().click(true);
						feather.interact("Take");
						
								
					}
				
			}else {
				GroundItems items = getGroundItems();
				Entity bone = items.closest("Bones");
				
				if(bone != null && bone.isVisible() && featherCollectionArea.contains(bone)) {
					getMouse().move(bone.getX(),bone.getY());
					getMouse().click(true);
					bone.interact("Take");	
					
					
					
					
				}
			}
			
			customSleep(2000);
			log("I just woke up!");	
			
		}
		
		
		
		return random(2000,4000);
	}
	
	private double randomise() {
		return Math.random();
	}
	
	private void customSleep(int howLong) {
		log("I am sleeping!");
		ConditionalSleep cs = new ConditionalSleep(howLong, 1000) {

			@Override
			public boolean condition() throws InterruptedException {
				return enableAntiban();
			}
			
		};
		cs.sleep();
		if(antiban == true) {
			log("Activating antiban measures.");
			antiban = false;
			antibanProtocol();
		}
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
		if(Math.random() < 0.30) {
			antiban = true;
			return true;
		}else {
			return false;
		}
	}
	

}


