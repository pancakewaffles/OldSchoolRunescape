import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(author = "Eliot", name = "Open Flax Spinner", version = 1.0, logo = "", info = "Spins flax into bowstrings.")
public class OpenFlaxSpinner extends Script {
  private ArrayList<Task> tasks = new ArrayList<>();
  private long startTime;

  @Override
  public void onStart() {
    tasks.add(new WalkToBankTask(this));
    tasks.add(new BankTask(this));
    tasks.add(new WalkToWheelTask(this));
    tasks.add(new SpinTask(this));
    startTime = System.currentTimeMillis();
  }

  @Override
  public int onLoop() throws InterruptedException {
    for (Task task : tasks) {
      if (task.verify())
        return task.execute();
    }
    return 150;
  }

  @Override
  public void onPaint(Graphics2D g) {
    long timePassed = System.currentTimeMillis() - startTime;
    int seconds = (int) (timePassed / 1000) % 60;
    int minutes = (int) ((timePassed / (1000 * 60)) % 60);
    int hours = (int) ((timePassed / (1000 * 60 * 60)));
    if (hours > 99) {
      g.drawString((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" +
          (seconds < 10 ? "0" : "") + seconds, 120, 440);
    } else {
      g.drawString((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" +
          (seconds < 10 ? "0" : "") + seconds, 120, 440);
    }

  }
}
