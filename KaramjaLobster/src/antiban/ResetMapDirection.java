package antiban;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.script.Script;

public class ResetMapDirection {
	private Script script;
	
	final int NORTH_YAW1 = 355;
	final int NORTH_YAW2 = 356;
	final int NORTH_YAW3 = 357;
	final int NORTH_YAW4 = 358;
	final int NORTH_YAW5 = 359;
	final int NORTH_YAW6 = 0;
	final int NORTH_YAW7 = 1;
	final int NORTH_YAW8 = 2;
	final int NORTH_YAW9 = 3;
	
	
	public ResetMapDirection(Script s) {
		this.script = s;
		
	}
	public void begin() {
		if(script.camera.getYawAngle() == NORTH_YAW1 ||  // camera is already in North position
		   script.camera.getYawAngle() == NORTH_YAW2 ||
		   script.camera.getYawAngle() == NORTH_YAW3 ||
		   script.camera.getYawAngle() == NORTH_YAW4 ||
		   script.camera.getYawAngle() == NORTH_YAW5 ||
		   script.camera.getYawAngle() == NORTH_YAW6 ||
		   script.camera.getYawAngle() == NORTH_YAW7 ||
		   script.camera.getYawAngle() == NORTH_YAW8 ||
		   script.camera.getYawAngle() == NORTH_YAW9
				) {
			MoveCamera moveCamera = new MoveCamera(script);
			moveCamera.begin();
			
			
		}else {
			pressReset();
		}

	}
	private void pressReset() {
		int stddev = (int)Math.random()*5;
		if(Math.random()<0.50) {
			stddev = -1*stddev;
		}
	
		script.mouse.click(550+stddev,17+stddev,false);
		
	
		script.mouse.moveOutsideScreen();
	}

}
