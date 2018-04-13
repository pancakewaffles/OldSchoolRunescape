import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;

public class BankTask extends Task {

  private Area bankArea = new Area(new Position(3207, 3217, 2), new Position(3210, 3220, 2));

  public BankTask(Script script) {
    super(script);
  }

  @Override
  public boolean verify() {
    return (script.getInventory().isEmpty() ||
        (script.getInventory().isFull() && !script.getInventory().onlyContains("Flax"))) &&
        bankArea.contains(script.myPlayer());

  }

  @Override
  public int execute() {
    if (script.getBank().isOpen()) {
      if (script.getInventory().isEmpty()) {
        script.getBank().withdrawAll("Flax");
      } else {
        script.getBank().depositAll();
      }
    } else {
      Entity bankBooth = script.getObjects().closest("Bank booth");
      if (bankBooth != null) {
        bankBooth.interact("Bank");
        return 1400;
      }
    }

    return 300;
  }

  @Override
  public String describe() {
    return "Banking.";
  }
}
