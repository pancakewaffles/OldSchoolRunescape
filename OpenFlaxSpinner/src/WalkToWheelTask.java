import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.Script;

public class WalkToWheelTask extends Task {

  private Area wheelArea = new Area(new Position(3208, 3212, 1), new Position(3213, 3216, 1));

  public WalkToWheelTask(Script script) {
    super(script);
  }

  @Override
  public boolean verify() {
    return script.getInventory().isFull() && script.getInventory().onlyContains("Flax") && !wheelArea.contains(script.myPlayer());
  }

  @Override
  public int execute() {
    script.getWalking().webWalk(wheelArea);
    return 200;
  }

  @Override
  public String describe() {
    return "Walking to wheel.";
  }
}