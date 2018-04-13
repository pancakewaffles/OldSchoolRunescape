import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@ScriptManifest(author = "Author", info = "A simple Teleporter", name = "Teleporter", version = 1.0, logo = "")

    public class Main extends Script{
    private long timeBegan;
    private long timeRan;
    private int beginningMagicXp;
    private int begginingLevelMagic;
    private int currentMagicXp;
    private int xpToNextLevelMagic;
    private int currentLevelMagic;
    private int xpPerHourMagic;
    private int levelsGainedMagic;
    private int xpGainedMagic;

    private int mX, mY;
    private final Image mousePointer = getImage("http://i.imgur.com/kGJ88Xg.png");

    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
        }
        return null;
    }
    
    private boolean start = false;
       
    private MagicSpell chosenSpell;
    
    private JFrame gui;
    private MagicSpell[] teleportingOptionsList = new MagicSpell[] {
    																Spells.NormalSpells.FALADOR_TELEPORT,
    																Spells.NormalSpells.LUMBRIDGE_TELEPORT,
    																Spells.NormalSpells.VARROCK_TELEPORT,															
    };
    
    
    @Override
    public void onStart() {
        timeBegan = System.currentTimeMillis();
        beginningMagicXp = skills.getExperience(Skill.MAGIC);
        begginingLevelMagic = skills.getStatic(Skill.MAGIC);
        
        
        gui();
        

    }

    @Override
    public int onLoop() throws InterruptedException {
    	if(start) {
    		
    		
    	if(getMagic().canCast(chosenSpell)){
            if (new ConditionalSleep(3000, 100) {
                @Override
                public boolean condition() throws InterruptedException {
                    return getMagic().castSpell(chosenSpell);
                }
            }.sleep()) {
                new ConditionalSleep(1500, 100) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return !myPlayer().isAnimating();
                    }
                }.sleep();
            }
        }else{
            log("Ran out of runes, logging off");
            new ConditionalSleep(2000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !myPlayer().isAnimating(); // Wait for anim to finish so we can log off
                }
            }.sleep();
            stop(true);
        }
    	
    	
    	}

        return random(500,1000);
    }



    @Override
    public void onExit(){
        log("Successfully exited!");
    }

    public int xpToNextLevel(final Skill skill) {
        return skills.experienceToLevel(skill);
    }


    public void onPaint(Graphics2D g){
        Graphics2D gr = g;
        g.setColor(Color.WHITE);
        mX = getMouse().getPosition().x;
        mY = getMouse().getPosition().y;
        g.drawImage(mousePointer, mX - 15, mY - 15, null);
        timeRan = System.currentTimeMillis() - this.timeBegan;
        g.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
        currentLevelMagic = this.skills.getStatic(Skill.MAGIC);
        currentMagicXp = this.skills.getExperience(Skill.MAGIC);
        levelsGainedMagic = currentLevelMagic - begginingLevelMagic;
        xpToNextLevelMagic = xpToNextLevel(Skill.MAGIC);
        xpGainedMagic =  currentMagicXp - beginningMagicXp;
        xpPerHourMagic = (int) (this.xpGainedMagic * 3600000.0 / (System.currentTimeMillis() - this.timeBegan));
        g.setColor(Color.WHITE);
        g.drawString(begginingLevelMagic  + "/" + currentLevelMagic + "(" + levelsGainedMagic + ")" + "  XP Gained: " + xpGainedMagic + "      XP Needed: " + xpToNextLevelMagic
        + "     XP/hr: " + xpPerHourMagic, 14, 329);
        g.setColor(Color.WHITE);

    }
    // GUI stuff
    private void gui(){ 
        // Declare two constants for width and height of the GUI
        final int GUI_WIDTH = 350, GUI_HEIGHT = 150;  
        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Calculating x and y coordinates
        final int gX = (int) (screenSize.getWidth() / 2) - (GUI_WIDTH / 2);
        final int gY = (int) (screenSize.getHeight() / 2) - (GUI_HEIGHT / 2);
        // Create a new JFrame with the title "GUI"
        gui = new JFrame("Teleporter");
        // Set the x coordinate, y coordinate, width and height of the GUI
        gui.setBounds(gX, gY, GUI_WIDTH, GUI_HEIGHT);
        gui.setResizable(false); // Disable resizing
        
        // Create a sub container JPanel
        JPanel panel = new JPanel();
        // Add it to the GUI
        gui.add(panel);
        JLabel label = new JLabel("Select the teleport skill, and press Start."); // Create a label
        label.setForeground(Color.black); // Set text colour to white
        panel.add(label); // Add it to the JPanel
        
        // Adds the combo box
        JComboBox<MagicSpell> teleportingOptions = new JComboBox<MagicSpell>(teleportingOptionsList);
        teleportingOptions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chosenSpell = (MagicSpell) teleportingOptions.getSelectedItem();
				
			}
        	
        });
        panel.add(teleportingOptions);
        

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            //do some startup procedure
        	gui.setVisible(false);
        	start = true;
            
            
            
            
        });
        panel.add(startButton);
        
        // Make the GUI visible
        gui.setVisible(true);
    }




    private String ft(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));

        if (days == 0) {
            if(hours <=9 && minutes <=9){
                res = ("0" + hours + ":" + "0" + minutes + ":" + seconds);
                return res;
            }else if(hours <=9 && minutes <=9){res = ("0" + hours + ":" + "0" +  minutes + ":" + seconds); return res;}
            else{
                res = (hours + ":"  +  minutes + ":" + seconds);
            }

        }else{
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }

        return res;
    }

}
