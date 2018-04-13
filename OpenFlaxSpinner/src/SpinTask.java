import org.osbot.rs07.api.Widgets;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.List;

public class SpinTask extends Task {

  private Area wheelArea = new Area(new Position(3208, 3212, 1), new Position(3213, 3216, 1));

  public SpinTask(Script script) {
    super(script);
  }

  @Override
  public boolean verify() {
    return script.getInventory().isFull() && !script.getInventory().onlyContains("Bow string") &&
        wheelArea.contains(script.myPlayer()) && !script.myPlayer().isAnimating();

  }

  @Override
  public int execute() {
    RS2Widget bs = script.getWidgets().getWidgetContainingText("Bow String");
    RS2Widget ea = script.getWidgets().get(162, 32);
    if (bs != null) {
      bs.interact("Make X");
    } else if (ea.isVisible()) {
      script.getKeyboard().typeString(Integer.toString(script.random(28, 99)));
    } else {
      if (script.getInventory().contains("Bow string") && new ConditionalSleep(1200) {
        @Override
        public boolean condition() throws InterruptedException {
          return script.myPlayer().isAnimating();
        }
      }.sleep()) {
        return 1200;
      } else {
        Entity spinningWheel = script.getObjects().closest("Spinning wheel");
        if (spinningWheel != null) {
          spinningWheel.interact("Spin");
        }
      }
    }

    return 1200;
  }

  @Override
  public String describe() {
    return "Spinning flax.";
  }
}
