import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Identifiable;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.util.ExperienceTracker;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(author = "Author", name = "Miner", info = "Miner", version = 0.1, logo = "http://oi63.tinypic.com/29cszl3.jpg")
public final class main extends Script
{
	// Paint stuff
    private String stateString;
    private ExperienceTracker experienceTracker;
    private long timeBegan;
    private long timeRan;
    private long lastAntiBan;
    private long nextAntiBan;
    //private final Image bg;
    private final Image bankPaintOn;
    private final Image bankPaintOff;
    private final Image antibanButtonOn;
    private final Image antibanButtonOFF;
    private final Image startButtonimg;
    private final Image cursor;
    int mouseX;
    int mouseY;
    int r;
    AffineTransform identity;
    AffineTransform trans;
    final Font font;
    private final LinkedList<MousePathPoint> mousePath;
    
    // Buttons and their logic
    private final Rectangle bankButton;
    private final Rectangle antibanButton;
    private final Rectangle startButton;
    private MouseListener clickListener;
    private boolean shouldBank;
    private boolean shouldAntiban;
    private boolean started;
    
    // Ores and their logic
    private Ores oreList;
    private Position posHolder;
    private Position hoverPos;
    private RS2Object activeOre;
    private RS2Object oreToBeChecked;
    private boolean isHovering;
    private List<RS2Object> nearbyOres;
    private List<RS2Object> allObjects;
    private List<Position> selectedOrePos;
    
    // Areas and their logic
    private Entity banker;
    private Area miningArea;
    private Area closestBank;
    private Area DWARVENMINE;
    private Area SHILOBANK;
    private Area PORTSARIMBANK;
    private Area[] bankAreas;
    
    // Lists
    private String[] bankActions;
    private String[] pickaxes;
    private String[] keepItems;
    private String[] dontDrop;
    
    public main() {
    	// Set up gui and logic.
        //this.bg = this.getImage("http://i.imgur.com/8mzp75F.png");
        this.bankPaintOn = this.getImage("http://i.imgur.com/a55TblB.png");
        this.bankPaintOff = this.getImage("http://i.imgur.com/lAMSdTp.png");
        this.antibanButtonOn = this.getImage("http://i.imgur.com/JVR587S.png");
        this.antibanButtonOFF = this.getImage("http://i.imgur.com/KcVBDYf.png");
        this.startButtonimg = this.getImage("http://i.imgur.com/qDc5xge.png");
        this.cursor = this.getImage("http://i.imgur.com/byIHPr2.png");
        this.r = 0;
        this.identity = new AffineTransform();
        this.trans = new AffineTransform();
        this.font = new Font("Tunga", 1, 15);
        this.mousePath = new LinkedList<MousePathPoint>();
        this.bankButton = new Rectangle(0, 0, 120, 50);
        this.antibanButton = new Rectangle(0, 50, 120, 50);
        this.startButton = new Rectangle(645, 452, 120, 50);
        this.clickListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (main.this.bankButton.contains(e.getPoint())) {
                    main.this.shouldBank = !main.this.shouldBank;
                }
                if (main.this.antibanButton.contains(e.getPoint())) {
                    main.this.shouldAntiban = !main.this.shouldAntiban;
                }
                if (main.this.startButton.contains(e.getPoint())) {
                    main.this.started = true;
                }
                // If we are not clicking the bank button, antiban button or start button, check if we are clicking an ore.
                // The logic is like this, if the clicked ore is in the selectedOre array, deselect it.
                // If the clicked ore is clicked ore is not in the selectedOre array, check if it is in the nearbyOres array. If it is, select it. (add it to the selectedOre array)
                // *** We are locating the ores based on the ore's position.
                else if (!main.this.started) {
                    for (int i = 0; i < main.this.selectedOrePos.size(); ++i) {
                        if (main.this.selectedOrePos.get(i).getPolygon(main.this.getBot()).contains(e.getPoint())) {
                            main.this.selectedOrePos.remove(main.this.selectedOrePos.get(i));
                            return;
                        }
                    }
                    for (int i = 0; i < main.this.nearbyOres.size(); ++i) {
                        if (main.this.nearbyOres.get(i).getPosition().getPolygon(main.this.getBot()).contains(e.getPoint())) {
                            main.this.selectedOrePos.add(main.this.nearbyOres.get(i).getPosition());
                            return;
                        }
                    }
                }
            }
            
            @Override
            public void mousePressed(final MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(final MouseEvent e) {
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
            }
        };
        this.shouldBank = false;
        this.shouldAntiban = false;
        this.started = false;
        
        // Set up ores and logic.
        this.oreList = new Ores();
        this.isHovering = false;
        this.nearbyOres = new ArrayList<RS2Object>();
        this.allObjects = new ArrayList<RS2Object>();
        selectedOrePos = new ArrayList<Position>();
        
        // Set up banking and logic.
        this.banker = null;
        this.DWARVENMINE = new Area(new Position(3060, 9732, 0), new Position(3010, 9852, 0));
        this.SHILOBANK = new Area(new Position(2854, 2957, 0), new Position(2850, 2951, 0));
        this.PORTSARIMBANK = new Area(new Position(3047, 3233, 0), new Position(3043, 3237, 0));
        bankAreas = new Area[] { Banks.AL_KHARID, Banks.ARCEUUS_HOUSE, Banks.ARDOUGNE_NORTH, Banks.ARDOUGNE_SOUTH, Banks.CAMELOT, Banks.CANIFIS, Banks.CASTLE_WARS, Banks.CATHERBY, Banks.DRAYNOR, Banks.DUEL_ARENA, Banks.EDGEVILLE, Banks.FALADOR_EAST, Banks.FALADOR_WEST, Banks.GNOME_STRONGHOLD, Banks.GRAND_EXCHANGE, Banks.HOSIDIUS_HOUSE, Banks.LOVAKENGJ_HOUSE, Banks.LOVAKITE_MINE, Banks.LUMBRIDGE_UPPER, Banks.PEST_CONTROL, Banks.PISCARILIUS_HOUSE, Banks.SHAYZIEN_HOUSE, Banks.TZHAAR, Banks.VARROCK_EAST, Banks.VARROCK_WEST, Banks.YANILLE, this.SHILOBANK, this.PORTSARIMBANK };
        this.bankActions = new String[] { "Bank", "Deposit" };
        this.pickaxes = new String[] { "Bronze pickaxe", "Iron pickaxe", "Steel pickaxe", "Black pickaxe", "Mithril pickaxe", "Adamant pickaxe", "Rune pickaxe", "Dragon pickaxe", "3rd age pickaxe", "Infernal pickaxe" };
        this.keepItems = new String[] { "Waterskin(1)", "Waterskin(2)", "Waterskin(3)", "Waterskin(4)", "Coins" };
        this.dontDrop = new String[this.keepItems.length + this.pickaxes.length];
    }
    
    public final void onStart() throws InterruptedException {
        dontDrop = merge(pickaxes, keepItems);
        if (!getInventory().contains(pickaxes) && !getEquipment().contains(pickaxes)) {
            log("No pickaxe was found. Stopping script.");
            stop(false);
        }
        miningArea = myPlayer().getArea(3);
        
        experienceTracker = getExperienceTracker();
        experienceTracker.start(Skill.MINING);
        
        timeBegan = System.currentTimeMillis();
        lastAntiBan = 0L;
        nextAntiBan = random(60000, 180000);
        
        // Getting all the ores organised. Having them added to nearby ores.
        allObjects = (List<RS2Object>)getObjects().getAll();
        for (int i = 0; i < allObjects.size(); ++i) {
            if (allObjects.get(i).hasAction(new String[] { "Mine" }) && miningArea.contains((Entity)allObjects.get(i))) {
                nearbyOres.add(allObjects.get(i));
            }
        }
        allObjects = null; // getObjects().getAll() might be too big and memory-intensive. This would improve performance.
        
        // Finds closest bank.
        if (DWARVENMINE.contains((Entity)myPlayer())) {
            closestBank = Banks.FALADOR_EAST;
        }
        else {
            for (int i = 0; i < bankAreas.length; ++i) {
                if (closestBank == null) {
                    closestBank = bankAreas[i];
                }
                else if (this.myPosition().distance(bankAreas[i].getRandomPosition()) < myPosition().distance(closestBank.getRandomPosition())) {
                    closestBank = bankAreas[i];
                }
            }
        }
    }
    
    public final int onLoop() throws InterruptedException {
        this.getBot().addMouseListener(this.clickListener);
        
        switch (this.getState()) {
            case SELECTING: {
                break;
            }
            case MINING: {
                mine();
                break;
            }
            case DROPPING: {
                drop();
                break;
            }
            case BANKING: {
                banking();
                break;
            }
            case RETURNING: {
                returning();
                break;
            }
            case WAITING: {
                sleep(500);
                break;
            }
            case ANTIBAN: {
                antiban();
                break;
            }
            default: {
                sleep(500);
                break;
            }
        }
        return 100;
    }
    
    public void onPaint(final Graphics2D g) {
        this.r %= 90;
        this.mouseX = this.getMouse().getPosition().x;
        this.mouseY = this.getMouse().getPosition().y;
        g.setFont(this.font);
       // g.drawImage(this.bg, 0, 337, null);
        this.trans.setTransform(this.identity);
        this.trans.rotate(Math.toRadians(this.r), this.mouseX, this.mouseY);
        this.trans.translate(this.mouseX - 8, this.mouseY - 8);
        g.drawImage(this.cursor, this.trans, null);
        while (!this.mousePath.isEmpty() && this.mousePath.peek().isUp()) {
            this.mousePath.remove();
        }
        final Point clientCursor = this.getMouse().getPosition();
        final MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, 300);
        if (this.mousePath.isEmpty() || !this.mousePath.getLast().equals(mpp)) {
            this.mousePath.add(mpp);
        }
        MousePathPoint lastPoint = null;
        for (final MousePathPoint a : this.mousePath) {
            if (lastPoint != null) {
                g.setColor(Color.WHITE);
                g.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
            }
            lastPoint = a;
        }
        g.setColor(Color.WHITE);
        this.timeRan = System.currentTimeMillis() - this.timeBegan;
        final String timeString = this.convertMillis(this.timeRan);
        final String nextAntibanString = this.convertMillis(this.nextAntiBan - (this.timeRan - this.lastAntiBan));
        if (this.stateString != null) {
            g.drawString(this.stateString, 415, 490);
        }
        if (this.experienceTracker != null) {
            final int x1 = 25;
            final int y1 = 370;
            final int x2 = 200;
            g.drawString("EXP GAINED", x1 + 0, y1 + 0);
            g.drawString("" + this.experienceTracker.getGainedXP(Skill.MINING), x1 + 100, y1 + 0);
            g.drawString("EXP/HR", x1 + 0, y1 + 35);
            g.drawString("" + this.experienceTracker.getGainedXPPerHour(Skill.MINING), x1 + 100, y1 + 35);
            g.drawString("TTL", x1 + 0, y1 + 70);
            g.drawString("" + this.convertMillis(this.experienceTracker.getTimeToLevel(Skill.MINING)), x1 + 100, y1 + 70);
            g.drawString("LVLS GAINED", x1 + 0, y1 + 105);
            g.drawString("" + this.experienceTracker.getGainedLevels(Skill.MINING), x1 + 100, y1 + 105);
            g.drawString("RUNNING TIME", x2 + 0, y1 + 0);
            g.drawString("" + timeString, x2 + 100, y1 + 0);
            if (this.shouldAntiban) {
                g.drawString("NEXT ANTIBAN", x2 + 0, y1 + 35);
                g.drawString("" + nextAntibanString, x2 + 100, y1 + 35);
            }
        }
        if (this.miningArea.contains((Entity)this.myPlayer())) {
            if (!this.started) {
                g.setColor(Color.WHITE);
                g.drawString("PLEASE SELECT ORE.", 195, 305);
                g.setColor(Color.GRAY);
                for (int i = 0; i < this.nearbyOres.size(); ++i) {
                    if (this.nearbyOres.get(i) != null) {
                        g.draw(this.nearbyOres.get(i).getPosition().getPolygon(this.getBot()));
                    }
                }
                g.drawImage(this.startButtonimg, 645, 452, null);
            }
            g.setColor(Color.WHITE);
            for (int i = 0; i < selectedOrePos.size(); ++i) {
                if (selectedOrePos.get(i) != null) {
                    g.draw(selectedOrePos.get(i).getPolygon(this.getBot()));
                }
            }
            if (activeOre != null) {
                g.setColor(Color.BLUE);
                g.draw(activeOre.getPosition().getPolygon(this.getBot()));
            }
        }
        if (this.shouldBank) {
            g.drawImage(this.bankPaintOn, 0, 0, null);
        }
        else {
            g.drawImage(this.bankPaintOff, 0, 0, null);
        }
        if (this.shouldAntiban) {
            g.drawImage(this.antibanButtonOn, 0, 50, null);
        }
        else {
            g.drawImage(this.antibanButtonOFF, 0, 50, null);
        }
        g.setColor(Color.WHITE);
        g.drawString("ZealMiner v0.5", 420, 325);
        ++this.r;
    }
    
    private Image getImage(final String url) {
        try {
            return ImageIO.read(new URL(url));
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
	private void mine() throws InterruptedException {
        if (activeOre != null && !activeOre.isVisible()) { // If we have an activeOre and it is not visible, move camera to make it visible.
            getCamera().toEntity( (Entity) activeOre);
        }
        if (selectedOrePos != null) { // selectedOrePos is an arraylist of selected ore positions. The whole script will work only if selectedOrePos has something.
        	
        	// Our player is standing still.
        	// There are two cases: Either we have not started mining, or we have finished mining.
            if (!myPlayer().isMoving() && !myPlayer().isAnimating()) { 
            	/*
            	// The double-clicking fix.
                if (activeOre != null) { // The double-clicking problem occurs because the player stops moving a split second before mining. It stands next to the ore, stops moving, then starts mining.
                        if (oreList.isValid(activeOre.getId())) { // Check the activeOre.
                            activeOre = oreToBeChecked;
                            oreToBeChecked = null;
                            //activeOre.interact(new String[] { "Mine" });
                            sleep(750);
                            return;
                        }
                        activeOre = null; // This fix also provides a nice little antiban - if your activeOre is depleted even before you start mining, then we switch to the next ore.
                        return;
                    }
                }*/
                
                
            	// Case 1: We have finished mining.
                if (isHovering) { // If we are hovering, that means we must have gotten the next ore, and so we click the next ore.
                	log("I have finished mining and will now mine the next ore.");
                    if (hoverPos != null) {
                        oreToBeChecked = getObjects().closest(new Filter[] { j -> ((RS2Object) j).getPosition().equals(hoverPos) });
                        if (oreList.isValid(oreToBeChecked.getId())) {
                            activeOre = oreToBeChecked;
                            oreToBeChecked = null;
                            activeOre.interact(new String[] { "Mine" });
                            new ConditionalSleep(10000) {

								@Override
								public boolean condition() throws InterruptedException {
									return myPlayer().isAnimating();
								}
                            	
                            }.sleep();
                            isHovering = false;
                            return;
                        }
                        isHovering = false;
                    }
                }
                
                // Case 2: We have not started mining.
                else { // We set the activeOre by checking whether the closest selected ore is valid (i.e. is not depleted) and set that to activeOre.
                	log("I have not started mining and am selecting an active ore.");
                    for (int i = 0; i < selectedOrePos.size(); ++i) {
                        posHolder = selectedOrePos.get(i);
                        oreToBeChecked = getObjects().closest(new Filter[] { j -> ((RS2Object) j).getPosition().equals(posHolder) });
                        if (oreList.isValid(oreToBeChecked.getId())) {
                            activeOre = oreToBeChecked;
                            oreToBeChecked = null;
                            activeOre.interact(new String[] { "Mine" });
                            new ConditionalSleep(10000) {

								@Override
								public boolean condition() throws InterruptedException {
									return myPlayer().isAnimating();
								}
                            	
                            }.sleep();
                            return;
                        }
                    }
                }
            }
            
            // We are currently mining/moving.
            // Well, then there are two steps.
            // Step 1: Hover over the next ore.
            // Step 2: Check if our ore has been mined, then we mine the next ore.
            if (!isHovering) { // We are currently mining/moving, and we want to hover over the next ore. (i.e. getting the next ore)
            	
            	log("I am currently mining/moving, and will now hover over the next ore, if there is a next ore.");
            	
                for (int i = 0; i < selectedOrePos.size(); ++i) {
                    posHolder = selectedOrePos.get(i);
                    if (posHolder != null) {
                        oreToBeChecked = getObjects().closest(new Filter[] { j -> ((RS2Object) j).getPosition().equals(posHolder) });
                        if (oreList.isValid(oreToBeChecked.getId()) && !posHolder.equals(activeOre.getPosition())) {
                            hoverPos = posHolder;
                            hoverPos.hover(getBot());
                            isHovering = true;
                           
                        }
                    }
                }
            }
            
            if (isHovering && activeOre != null) { // We are currently mining/moving, and we have hovered over the next ore. So, we check if the ore we are mining has been mined.
            	
            	log("I am currently mining/moving, and have hovered over the next ore, so I will check the current ore. If it is depleted, I will mine the next ore.");
            	
                oreToBeChecked = activeOre;
                if (oreToBeChecked != null && !oreList.isValid(oreToBeChecked.getId()) && isHovering && hoverPos != null) {
                    activeOre = getObjects().closest(new Filter[] { i -> ((RS2Object) i).getPosition().equals(hoverPos) }); // Set nextOre to be the activeOre.
                    activeOre.interact(new String[] { "Mine" });
                    new ConditionalSleep(10000) {

						@Override
						public boolean condition() throws InterruptedException {
							return myPlayer().isAnimating();
						}
                    	
                    }.sleep();
                    isHovering = false;
                }
            }
        }
    }
    
    private void drop() {
        this.getInventory().dropAllExcept(this.dontDrop);
    }
    
    @SuppressWarnings("unchecked")
	private void banking() throws InterruptedException {
    	// Walking to the closest bank, and opening it.
        if (!closestBank.contains((Entity)this.myPlayer())) {
            this.getWalking().webWalk(new Area[] { closestBank });
        }
        // We have reached the bank. Now we select a banker/bank booth/deposit box and interact with it.
        else if (!this.getBank().isOpen() && !this.getDepositBox().isOpen()) {
            if (this.banker != null) { // Opens the bank.
                this.banker.interact(this.bankActions);
                sleep((long)random(2500, 2500));
            }
            else { // Selects the banker; either the booth, the banker, or the deposit box.
                this.banker = this.getObjects().closest(new Filter[] { i -> i != null && ((RS2Object) i).hasAction(this.bankActions) }); 
                if (this.banker == null) {
                    this.banker = this.getNpcs().closest(new Filter[] { i -> i != null && ((NPC) i).hasAction(this.bankActions) });
                }
            }
        }
        // Well done, we have opened the bank.
        else {
            if (this.banker.hasAction(new String[] { "Deposit" })) { // Oh we selected a deposit box.
                this.getDepositBox().depositAllExcept(this.pickaxes);
                sleep((long)random(200, 1000));
                this.getDepositBox().close();
            }
            else { // Else we must have selected a banker or a bank booth. Interact as such.
                this.getBank().depositAllExcept(this.pickaxes);
                sleep((long)random(200, 1000));
                this.getBank().close();
            }
            this.banker = null; // Reset it so that it doesn't take up space.
        }
    }
    
    private void returning() {
        if (this.miningArea.getRandomPosition().distance(this.myPosition()) > 25) {
            this.getWalking().webWalk(new Area[] { this.miningArea });
        }
        else {
            this.getWalking().walk(this.miningArea);
        }
    }
    
    private void antiban() throws InterruptedException {
        switch (random(0, 2)) {
            case 0: {
                this.log("Antiban: Moving Camera");
                this.getCamera().moveYaw(random(-180, 180));
                break;
            }
            case 1: {
                this.log("Antiban: Checking Exp");
                this.getSkills().hoverSkill(Skill.MINING);
                break;
            }
            case 2: {
                this.log("Antiban: Tabbing out");
                this.getMouse().moveOutsideScreen();
                sleep((long)random(10000, 25000));
                break;
            }
        }
        this.lastAntiBan = this.timeRan;
        this.nextAntiBan = random(60000, 180000);
    }
    
    private State getState() {
        if (!this.started) {
            this.stateString = "Selecting...";
            return State.SELECTING;
        }
        // Check the antiban first, else it will never get checked. 
        if (this.shouldAntiban && this.nextAntiBan - (this.timeRan - this.lastAntiBan) < 0L) {
            this.stateString = "Antiban...";
            return State.ANTIBAN;
        }
        // My inventory is not full. Hmm, I am not at the mining area, so I shall return to the mining area.
        // If I am at the mining area, then I shall just mine.
        if (!this.inventory.isFull()) {
            if (!this.miningArea.contains(this.myPosition())) {
                this.stateString = "Returning...";
                return State.RETURNING;
            }
            this.stateString = "Mining...";
            return State.MINING;
        }
        
        else {
        	/* This is a pointless if case.
            if (!this.inventory.isFull()) {
                this.stateString = "Waiting...";
                return State.WAITING;
            }
            */
            if (this.shouldBank) {
                this.stateString = "Banking...";
                return State.BANKING;
            }
            this.stateString = "Dropping...";
            return State.DROPPING;
        }
    }
    
    private String convertMillis(final long duration) {
        String result = "";
        final long days = TimeUnit.MILLISECONDS.toDays(duration);
        final long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        if (days == 0L) {
            result = hours + "." + minutes + "." + seconds;
        }
        else {
            result = days + "." + hours + "." + minutes + "." + seconds;
        }
        return result;
    }
    
    private static String[] merge(final String[] a, final String[] b) {
        final String[] answer = new String[a.length + b.length];
        int i = 0;
        int j = 0;
        while (i < a.length) {
            answer[i] = a[i];
            ++i;
        }
        while (j < b.length) {
            answer[i] = b[j];
            ++i;
            ++j;
        }
        return answer;
    }
    
    private enum State
    {
        SELECTING, 
        MINING, 
        BANKING, 
        DROPPING, 
        WAITING, 
        RETURNING, 
        ANTIBAN;
    }
    
    private class MousePathPoint extends Point
    {
        private long finishTime;
        private double lastingTime;
        
        public MousePathPoint(final int x, final int y, final int lastingTime) {
            super(x, y);
            this.lastingTime = lastingTime;
            this.finishTime = System.currentTimeMillis() + lastingTime;
        }
        
        public boolean isUp() {
            return System.currentTimeMillis() > this.finishTime;
        }
    }
}
