import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.Script;

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
public class WalkToKaramjaTask extends Task {

    private Area karamjaLobsterSpot = new Area(2919, 3183, 2930, 3174);


    public WalkToKaramjaTask(Script script) {
        super(script);
    }

    @Override
    public boolean verify() {
        return  !karamjaLobsterSpot.contains(script.myPlayer());
    }

    @Override
    public int execute() {

        script.log("Walking to Karamja fishing Spot");

        script.getWalking().webWalk(karamjaLobsterSpot);





        return random(300,1200);
    }

    @Override
    public String describe() {
        return "Walking to Karamja fishing Spot";
    }
}
