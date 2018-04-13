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
		
		int choice = 0;
		
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
		camera.movePitch(camera.getPitchAngle() + 50);
		camera.moveYaw(camera.getYawAngle() + 50);
		
		new ConditionalSleep( (int) Math.floor(Math.random()*4000 + 1000) ) {

			@Override
			public boolean condition() throws InterruptedException {
				return 0>1;
			}
			
		}.sleep();
		if(Math.random() < 0.35) {
			camera.movePitch(camera.getPitchAngle() - 50);
			camera.moveYaw(camera.getYawAngle() - 50);
		}
		
	}
	private void reset() {
		// TODO Auto-generated method stub
		
	}
	private void dualMovement() {
		if(DEBUG) {
			script.log("Launching dualMovement()");
		}
		
		int desiredAngle = (int) (Math.random() * 35);
		int desiredAngleVaried = desiredAngle + stddev;
		
		camera.movePitch(camera.getPitchAngle() + desiredAngleVaried);
		camera.moveYaw(camera.getYawAngle() + desiredAngleVaried);
		
		
	}
	private void movetoBottom() {
		if(DEBUG) {
			script.log("Launching moveToBottom()");
		}
		
		
		if(Math.random() < 0.15) {
			camera.toBottom();
			camera.movePitch(camera.getLowestPitchAngle() + 20);
		}
		
	}
	private void movetoTop() {
		if(DEBUG) {
			script.log("Launching movetoTop()");
		}
		
		if(Math.random() < 0.20) {
			camera.toTop();
			camera.movePitch(camera.getPitchAngle() - 20);
		}else {
			camera.toTop();
		}
		
	}
	private void generateProbabilities() {
		probabilityArray = new double[5];
		for(int i = 0;i<probabilityArray.length;i++) {
			probabilityArray[i] = (double)1.0/methods.length;
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
