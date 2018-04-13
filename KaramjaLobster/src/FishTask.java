import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

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
public class FishTask extends Task {





    public FishTask(Script script) {
        super(script);
    }

    @Override
    public boolean verify() {
        return  !script.myPlayer().isAnimating() && !script.inventory.isFull();
    }

    @Override
    public int execute() {
        script.log("Finding New Lobster Spot");

        NPC fishSpot = script.getNpcs().closest(1522);


        if (fishSpot != null && !script.myPlayer().isAnimating()){
            fishSpot.interact("Cage");
            new ConditionalSleep(5000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return script.myPlayer().isAnimating();

                }
            }.sleep();

        }


        return random(300,1200);
    }

    @Override
    public String describe() {
        return "Fishing Lobster Bro";
    }
}