package antiban;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class CheckTabber {
	private Script script;
	boolean DEBUG = true;
	
	final int FISHING_SKILLTAB_X = 700;
	final int FISHING_SKILLTAB_Y = 281;
	
	int stddev = 7;
	
	public CheckTabber(Script s) {
		this.script = s;
		
	}
	
	public void begin() {
		checkInventory();
	}
	
	private void checkInventory() {
		if(DEBUG) {
			script.log("Checking Inventory.");
		}
		if(script.getTabs().getOpen() == Tab.INVENTORY) {
			checkSkills();
		}else {
			script.getTabs().open(Tab.INVENTORY);
			
			if(!script.getInventory().isEmpty()) {
				script.mouse.move(script.mouse.getPosition().x - 20, script.mouse.getPosition().y - 20);
			}
			
			try {
				MethodProvider.sleep(script.random(300,1800));
			} catch (InterruptedException e) {
				script.log(e);
			}
			
			script.getMouse().moveOutsideScreen();
			
		}
		
		
		
	}

	private void checkSkills() {
		if(DEBUG) {
			script.log("Checking Skills.");
		}
		if(script.getTabs().getOpen() == Tab.SKILLS) {
			checkQuests();
		}else {
			script.getTabs().open(Tab.SKILLS);
			
			int realisticskilltab_x = (int) (Math.floor(Math.random()*stddev) + FISHING_SKILLTAB_X);
			int realisticskilltab_y = (int) (Math.floor(Math.random()*stddev) + FISHING_SKILLTAB_Y);
			
			script.mouse.move(realisticskilltab_x,realisticskilltab_y);
			
			
			try {
				MethodProvider.sleep(script.random(300,1800));
			} catch (InterruptedException e) {
				script.log(e);
			}
			
			script.getMouse().moveOutsideScreen();
		}
		
	}

	private void checkQuests() {
		if(DEBUG) {
			script.log("Checking Quests.");
		}
		if(script.getTabs().getOpen() == Tab.QUEST) {
			checkInventory();
		}else {
		script.getTabs().open(Tab.QUEST);
		script.mouse.moveOutsideScreen();
		}
		
	}
}
