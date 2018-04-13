package antiban;

import java.util.Random;

import org.osbot.rs07.api.Camera;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;


/* Five sets of human camera actions:
 * 
 * 1) move camera to the top (highest angle possible).
 * 2) move camera to the bottom ( lowest angle possible).
 * 3) move camera moveYaw and movePitch
 * 4) Press the compass button to reset map.
 * 5) the "bored" movement (move camera moveYaw and movePitch)
 * 
 */
public class MoveCamera {
	
	private Script script;
	int stddev = 30;
	int[] methods = new int[] {0,1,2,3,4};
	double[] probabilityArray;
	private boolean DEBUG = true;
	final int MAX_PITCH = 67;
	
	
	public MoveCamera(Script s) {
		this.script = s;	
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
		int allowedPitchDegrees = (int) (script.getCamera().getLowestPitchAngle() + (script.getCamera().getPitchAngle() - script.getCamera().getLowestPitchAngle() + 10)%( (int) (MAX_PITCH-script.getCamera().getLowestPitchAngle()) ));
		int allowedYawDegrees = (int) (script.getCamera().getYawAngle() + 90)%360;
		script.getCamera().movePitch(allowedPitchDegrees);
		script.getCamera().moveYaw(allowedYawDegrees);
		
		try {
			MethodProvider.sleep(MethodProvider.random(3000,5000));
		}catch (InterruptedException e){
			script.log("Sleep interrupted.");
		}
		if(Math.random() < 0.35) {
			script.getCamera().movePitch(Math.max(script.getCamera().getPitchAngle() - 10 , script.getCamera().getLowestPitchAngle()));
			script.getCamera().moveYaw((script.getCamera().getYawAngle() - 90)%360);
		}
		
		script.getMouse().moveOutsideScreen();
		
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
		
		int difference = MAX_PITCH - script.getCamera().getLowestPitchAngle();
		
		script.getCamera().movePitch( (int) ( script.getCamera().getLowestPitchAngle() + ((int)desiredAngleVaried/6)%difference  )  );
		script.getCamera().moveYaw(((int) (script.getCamera().getYawAngle() + desiredAngleVaried) )%360);
		
		
	}
	private void movetoBottom() {
		if(DEBUG) {
			script.log("Launching moveToBottom()");
		}
		
		
		if(Math.random() < 0.15) {
			script.getCamera().movePitch(script.getCamera().getLowestPitchAngle());
			
		}else if(script.getCamera().getPitchAngle() == script.getCamera().getLowestPitchAngle()) {
			script.getCamera().movePitch(script.getCamera().getPitchAngle() + 15);
		}else {
			
		}
		
		script.getMouse().moveOutsideScreen();
		
	}
	private void movetoTop() {
		if(DEBUG) {
			script.log("Launching movetoTop()");
		}
		
		if(Math.random() < 0.40) {
			script.getCamera().movePitch(MAX_PITCH);
			script.getCamera().movePitch(script.getCamera().getPitchAngle() - 25);
		}else if(script.getCamera().getPitchAngle() == MAX_PITCH) {
			script.getCamera().movePitch(script.getCamera().getPitchAngle() - 20);
		}else {
			
		}
		
	}
	private void generateProbabilities() {
		probabilityArray = new double[5];
		for(int i = 0;i<probabilityArray.length;i++) {
			probabilityArray[i] = 0.20 + i*0.20;
		}
		
	}
	

	
	
	

}
