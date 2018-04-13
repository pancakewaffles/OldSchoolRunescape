package antiban;

import java.util.Random;

import org.osbot.rs07.api.Camera;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class MoveCamera {
	
	private Script script;
	private Camera camera;
	int stddev = 30;
	int[] methods = new int[] {0,1,2,3,4};
	double[] probabilityArray;
	private boolean DEBUG = true;
	
	
	public MoveCamera(Script script) {	
		this.script = script;
		this.camera = script.camera;	
	}
	public void begin() {
		generateProbabilities();
		
		double probability = Math.random();
		
		int choice = -1;
		
		for(int i = 0;i<methods.length;i++) {
			if(probability<=probabilityArray[i]) {
				choice = i;
				break;
			}
		}
		
		switch(choice) {
			case 0:
				movetoTop();
				break;
			case 1:
				movetoBottom();
				break;
			case 2:
				dualMovement();
				break;
			case 3:
				reset();
			case 4:
				bored();
				break;
				
			
			
		}
		
		
		
	}
	private void bored() {
		if(DEBUG) {
			script.log("Launching bored()");
		}
		camera.movePitch(camera.getPitchAngle() + 10);
		camera.moveYaw(camera.getYawAngle() + 90);
		
		try {
			script.sleep(script.random(3000,5000));
		}catch (InterruptedException e){
			script.log(e);
		}
		if(Math.random() < 0.35) {
			camera.movePitch(camera.getPitchAngle() - 10);
			camera.moveYaw(camera.getYawAngle() - 90);
		}
		
	}
	private void reset() {
		ResetMapDirection resetter = new ResetMapDirection(script);
		resetter.begin();
		
	}
	private void dualMovement() {
		if(DEBUG) {
			script.log("Launching dualMovement()");
		}
		
		int desiredAngle = (int) (Math.random() * 70);
		int desiredAngleVaried = desiredAngle + stddev;
		
		camera.movePitch(camera.getPitchAngle() + desiredAngleVaried/6);
		camera.moveYaw(camera.getYawAngle() + desiredAngleVaried);
		
		
	}
	private void movetoBottom() {
		if(DEBUG) {
			script.log("Launching moveToBottom()");
		}
		
		
		if(Math.random() < 0.15) {
			camera.toBottom();
			
		}else if(camera.getPitchAngle() == camera.getLowestPitchAngle()) {
			camera.movePitch(camera.getPitchAngle() + 15);
		}
		
	}
	private void movetoTop() {
		if(DEBUG) {
			script.log("Launching movetoTop()");
		}
		
		if(Math.random() < 0.40) {
			camera.toTop();
			camera.movePitch(camera.getPitchAngle() - 25);
		}else if(camera.getPitchAngle() == 67) {
			camera.movePitch(camera.getPitchAngle() - 20);
		}
		
	}
	private void generateProbabilities() {
		probabilityArray = new double[5];
		for(int i = 0;i<probabilityArray.length;i++) {
			probabilityArray[i] = 0.20 + i*0.20;
		}
		
	}
	
	/* Five sets of human camera actions:
	 * 
	 * 1) move camera to the top (highest angle possible).
	 * 2) move camera to the bottom ( lowest angle possible).
	 * 3) move camera moveYaw and movePitch
	 * 4) Press the compass button to reset map.
	 * 5) the "bored" movement (move camera moveYaw and movePitch)
	 * 
	 */
	
	
	

}
