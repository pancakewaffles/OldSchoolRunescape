import java.awt.Graphics2D;

import org.osbot.rs07.script.Script;

public abstract class Task {
    // The script instance
    protected Script script;

    public Task(Script script) {
        this.script = script;
    }

    /**
     * @return if this Task should execute.
     */
    public abstract boolean verify();

    /**
     * Executes this Task.
     *
     * @return sleep time after this task ends.
     */
    public abstract int execute();

    /**
     * @return a description of the current Task.
     */
    public abstract String describe();
    
    public abstract void paint(Graphics2D g);
} 