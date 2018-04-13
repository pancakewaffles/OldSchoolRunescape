package antiban;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class CheckTabber {
	Script script;
	boolean DEBUG = true;
	
	
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
			
			script.mouse.move(700,281);
			
			
			try {
				MethodProvider.sleep(300);
			} catch (InterruptedException e) {
				script.log(e);
			}
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
