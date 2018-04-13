import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

public class WalkToBankTask extends Task {

  private Area bankArea = new Area(new Position(3207, 3217, 2), new Position(3210, 3220, 2));

  public WalkToBankTask(Script script) {
    super(script);
  }

  @Override
  public boolean verify() {
    return script.getInventory().isFull() && script.getInventory().onlyContains("Bow string") && !bankArea.contains(script.myPlayer());
  }

  @Override
  public int execute() {
    script.getWalking().webWalk(bankArea);
    return 200;
  }

  @Override
  public String describe() {
    return "Walking to bank.";
  }
}