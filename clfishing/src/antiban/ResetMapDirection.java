package antiban;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.script.Script;

public class ResetMapDirection {
	Script script;
	
	
	public ResetMapDirection(Script s) {
		this.script = s;
		
	}
	public void begin() {
		int stddev = (int)Math.random()*5;
		if(Math.random()<0.50) {
			stddev = -1*stddev;
		}
	
		script.mouse.click(550+stddev,17+stddev,false);
		
	
		script.mouse.moveOutsideScreen();
	}

}
