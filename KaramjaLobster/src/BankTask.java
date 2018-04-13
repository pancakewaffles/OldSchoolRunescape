import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;

/**
 * Created by tek on 8/14/2016.
 */
public class BankTask extends Task {

    private Area depositBoxArea = new Area(3043, 3237, 3052, 3234);



    public BankTask(Script script) {
        super(script);
    }

    @Override
    public boolean verify() {
        return script.inventory.isFull();
    }

    @Override
    public int execute() {
        script.log("Depositing Lobster");

        if (!depositBoxArea.contains(script.myPlayer())) {

            script.getWalking().webWalk(depositBoxArea);
        }
        if (script.getDepositBox().isOpen()) {
            if (script.getInventory().contains("Raw lobster")) {
                script.depositBox.depositAllExcept("Lobster pot", "Coins");
                new ConditionalSleep(3000,5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return !script.inventory.contains("Raw lobster");

                    }
                }.sleep();

            }
        } else {
            Entity depositBooth = script.getObjects().closest("Bank deposit box");
            if (depositBooth != null) {
                script.depositBox.open();
                return random(300,800);
            }
        }

        return random(300,400);
    }

    @Override
    public String describe() {
        return "Depositing Lobster";
    }
}