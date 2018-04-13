package runecrafter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.security.GeneralSecurityException;

import javax.swing.WindowConstants;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Author", info = "Crafts Air and Earth runes and banks them", logo = "", name = "RuneCrafter", version = 1.1)

public class RuneCrafter extends Script {

	State state;

	/* Items */
	static String TALISMAN_NAME;
	static String TIARA_NAME;
	static String RUINS = "Mysterious ruins";
	static String ESSENCE;
	static String RUNES;
	static String ACCESSORY;
	static String ALTAR = "Altar";
	static String PORTAL = "Portal";

	/* Areas */
	static Area BANK_AREA;
	static Area ALTAR_AREA;

	/* Altar area register */
	final static Area AIR_ALTAR_AREA = new Area(2980, 3289, 2989, 3296);
	final static Area EARTH_ALTAR_AREA = new Area(3303, 3472, 3309, 3476);

	/* Bank area register */
	final static Area FALADOR_EAST_BANK_AREA = new Area(3009, 3355, 3017, 3358);
	final static Area VARROCK_EAST_BANK_AREA = new Area(3250, 3420, 3254, 3422);

	/* Altar register (for GUI) */
	final static String AIR_ALTAR = "Air altar";
	final static String EARTH_ALTAR = "Earth altar";

	/* Talisman register */
	final static String AIR_TALISMAN = "Air talisman";
	final static String EARTH_TALISMAN = "Earth talisman";

	/* Tiara register */
	final static String AIR_TIARA = "Air tiara";
	final static String EARTH_TIARA = "Earth tiara";

	/* Accesorry type register */
	final static String TALISMAN = "Talisman";
	final static String TIARA = "Tiara";

	/* Rune register */
	final static String AIR_RUNE = "Air rune";
	final static String EARTH_RUNE = "Earth rune";

	/* Essence register */
	final static String RUNE_ESSENCE = "Rune essence";
	final static String PURE_ESSENCE = "Pure essence";

	/* Timing intervals */
	static int BETWEEN_LOOPS_LOW;
	static int BETWEEN_LOOPS_HIGH;
	static int BETWEEN_ACTIONS_LOW;
	static int BETWEEN_ACTIONS_HIGH;

	/* Remaining amount checker */
	long essenceLeft;

	/* For GUI */
	public boolean isStarted = false;

	/* For paint */
	static int startXp;
	static int xpGained;
	long startTime;

	boolean inAltar;
	int runesCrafted = 0;

	public void onStart() {
		this.log("Welcome to RuneCrafter");

		/* Until start is clicked */
		BETWEEN_LOOPS_LOW = 200;
		BETWEEN_LOOPS_HIGH = 1500;
		BETWEEN_ACTIONS_LOW = 200;
		BETWEEN_ACTIONS_HIGH = 1200;

		RuneCrafterGUI gui = new RuneCrafterGUI(this);
		gui.setVisible(true);
		gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		state = State.WAITING_FOR_INPUT;

		startTime = System.currentTimeMillis();
		startXp = getSkills().getExperience(Skill.RUNECRAFTING);

	}

	public void onExit() {
		this.log("Exiting Runecrafter");
	}

	public void getState() {

		inAltar = false;
		RS2Object altar = getObjects().closest(ALTAR);
		/* Check if we are in rune altar area */
		if (altar != null && map.isWithinRange(altar, 25)) {
			inAltar = true;
		}

		if (!isAccessoryOnHand() && !BANK_AREA.contains(this.myPlayer())) {
			state = State.WALK_TO_BANK;
		}
		if (inventory.contains(ESSENCE) && !ALTAR_AREA.contains(this.myPlayer()) && !inAltar && isAccessoryOnHand()) {
			state = State.WALK_TO_ALTAR;
		} else if (inventory.contains(ESSENCE) && ALTAR_AREA.contains(this.myPlayer()) && isAccessoryOnHand()) {
			state = State.IN_ALTAR_AREA;
		} else if (inventory.contains(ESSENCE) && inAltar) {
			state = State.INSIDE_ALTAR;
		} else if (!inventory.contains(ESSENCE) && inAltar) {
			state = State.GOING_TO_PORTAL;
		} else if ((!inventory.contains(ESSENCE) || !isAccessoryOnHand()) && !BANK_AREA.contains(this.myPlayer())) {
			state = State.WALK_TO_BANK;
		} else if ((!inventory.contains(ESSENCE) || inventory.contains(RUNES) || !isAccessoryOnHand())
				&& BANK_AREA.contains(this.myPlayer())) {
			state = State.BANK;
		}

	}

	@Override
	public int onLoop() throws InterruptedException {

		if (isStarted) {

			getState();
			if (!this.myPlayer().isAnimating()) {
				if (state.equals(State.WALK_TO_ALTAR)) {
					antiBan();
					walkToAltar();
				} else if (state.equals(State.IN_ALTAR_AREA)) {
					enterAltar();
				} else if (state.equals(State.INSIDE_ALTAR)) {
					craftRunes();
				} else if (state.equals(State.GOING_TO_PORTAL)) {
					antiBan();
					goToPortal();
				} else if (state.equals(State.WALK_TO_BANK)) {
					antiBan();
					walkToBank();
				} else if (state.equals(State.BANK)) {
					doBank();
				}
				sleep(random(BETWEEN_ACTIONS_LOW, BETWEEN_ACTIONS_HIGH));

			}
		}
		return random(BETWEEN_LOOPS_LOW, BETWEEN_LOOPS_HIGH);
	}

	public void walkToAltar() {
		walking.webWalk(ALTAR_AREA);
	}

	public void enterAltar() throws InterruptedException {
		RS2Object ruins = objects.getObjects().closest(RUINS);

		if (ruins != null) {
			if (ruins.isVisible()) {
				if (!this.myPlayer().isMoving() && !this.myPlayer().isAnimating() && isAccessoryOnHand()) {
					if (ACCESSORY == TALISMAN) {
						inventory.interact("Use", TALISMAN_NAME);
						sleep(random(BETWEEN_ACTIONS_LOW, BETWEEN_ACTIONS_HIGH));
						ruins.interact();
					} else if (ACCESSORY == TIARA) {
						sleep(random(BETWEEN_ACTIONS_LOW, BETWEEN_ACTIONS_HIGH));
						ruins.interact("Enter");
					}
				}
			} else {
				camera.toEntity(ruins);
			}
		}

	}

	public void craftRunes() {
		RS2Object altar = objects.getObjects().closest(ALTAR);
		Player player = this.myPlayer();

		if (inventory.isItemSelected()) {
			inventory.deselectItem();
		}
		if (altar != null) {
			if (!player.isMoving() && !player.isAnimating()) {
				if (altar.isVisible()) {
					altar.interact("Craft-rune");
				} else {
					camera.toEntity(altar);
					log("Turning camera to altar");
					if (!map.isWithinRange(altar, 4)) {
						walking.walk(altar);
					}
				}

			}
		}

	}

	public void goToPortal() {
		RS2Object portal = objects.getObjects().closest(PORTAL);
		Player player = this.myPlayer();
		if (portal != null) {
			if (portal.isVisible()) {
				if (!player.isMoving() && !player.isAnimating()) {
					portal.interact("Use");
					boolean statsAdded = false;
					if (!statsAdded) {
						runesCrafted += inventory.getAmount(RUNES);
						xpGained = getSkills().getExperience(Skill.RUNECRAFTING) - startXp;
						statsAdded = true;
					}
				}
			} else {
				camera.toEntity(portal);
				if(!map.canReach(portal)) {
					walking.walk(portal);
				}
			}

		}
	}

	public void walkToBank() {
		walking.webWalk(BANK_AREA);
	}

	public void doBank() throws InterruptedException {
		if (bank.isOpen()) {
			sleep(random(BETWEEN_ACTIONS_LOW, BETWEEN_ACTIONS_HIGH));
			
			// Have an empty inventory to work with.
			if(ACCESSORY == TALISMAN) {
				bank.depositAllExcept(TALISMAN_NAME);
			}else {
				bank.depositAll();
			}
			
			// Grab required accessories if not equipped.
			if (!isAccessoryOnHand()) {		
				
				sleep(random(BETWEEN_ACTIONS_LOW, BETWEEN_ACTIONS_HIGH));
				
				if (ACCESSORY == TALISMAN) {
					bank.withdraw(TALISMAN_NAME, 1);
				} else if (ACCESSORY == TIARA) {
					bank.withdraw(TIARA_NAME, 1);
				}
			}
			
			// Grab the rune essences.
			essenceLeft = getInventory().getAmount(ESSENCE) + getBank().getAmount(ESSENCE);
			if (essenceLeft < 1) {
				state = State.LOGGING_OUT;
				getBank().close();
				sleep(random(BETWEEN_ACTIONS_LOW + 1500, BETWEEN_ACTIONS_HIGH + 1500));
				getLogoutTab().open();
				sleep(random(BETWEEN_ACTIONS_LOW + 1500, BETWEEN_ACTIONS_HIGH + 1500));
				getLogoutTab().logOut();
				stop();
			}

			bank.withdrawAll(ESSENCE);
		} else {
			RS2Object bankBooth = objects.getObjects().closest("Bank booth");
			if (bankBooth != null) {
				if (bankBooth.isVisible()) {
					bankBooth.interact("Bank");
					sleep(random(BETWEEN_ACTIONS_LOW, BETWEEN_ACTIONS_HIGH));
				} else {
					camera.toEntity(bankBooth);
				}
			}

		}
	}

	public void onPause() {
		state = State.PAUSED;
	}

	public void onPaint(Graphics2D g) {

		String printState = "Loading";
		if (state == State.WALK_TO_ALTAR) {
			printState = "Walking to Ruins";
		} else if (state == State.IN_ALTAR_AREA) {
			printState = "Entering Altar";
		} else if (state == State.INSIDE_ALTAR) {
			printState = "Crafting runes";
		} else if (state == State.GOING_TO_PORTAL) {
			printState = "Going to portal";
		} else if (state == State.WALK_TO_BANK) {
			printState = "Walking to Bank";
		} else if (state == State.IN_BANK) {
			printState = "Accessing Bank";
		} else if (state == State.BANK) {
			printState = "Banking";
		} else if (state == State.PAUSED) {
			printState = "Paused";
		} else if (state == State.LOADING) {
			printState = "Loading";
		} else if (state == State.LOGGING_OUT) {
			printState = "Out of " + ESSENCE + "! Logging out.";
		} else if (state == State.WAITING_FOR_INPUT) {
			printState = "Waiting for input parameters";
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.BOLD, 12));
		if (RUNES != null) {
			g.drawString("Crafting: " + RUNES + "s", 25, 35);
		}
		g.drawString("Runes crafted: " + runesCrafted, 25, 46);
		g.drawString("Runes per hour: " + getPerHour(runesCrafted, System.currentTimeMillis() - startTime), 25, 57);
		g.drawString("EXP Gained: " + xpGained, 25, 68);
		g.drawString("EXP Gained p/h: " + getPerHour(xpGained, System.currentTimeMillis() - startTime), 25, 79);
		g.drawString("Status: " + printState, 25, 91);
		g.drawString("Running: " + formatTime(System.currentTimeMillis() - startTime), 25, 103);
	
        // Draw mouse cursor
        g.setColor(Color.ORANGE);
        g.fillOval(mouse.getPosition().x, mouse.getPosition().y, 10, 10);
	}

	public void setIsStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public static int getPerHour(int value, long runTime) {
		if (runTime > 0) {
			return (int) (value * 3600000d / runTime);
		} else {
			return 0;
		}
	}
	
	private String formatTime(long ms){
	    long s = ms / 1000, m = s / 60, h = m / 60;
	    s %= 60; m %= 60; h %= 24;
	    return String.format("%02d:%02d:%02d", h, m, s);
	}

	public boolean isAccessoryOnHand() {
		boolean accessoryOnHand = false;
		if (ACCESSORY == TALISMAN) {
			accessoryOnHand = inventory.contains(TALISMAN_NAME);
		} else if (ACCESSORY == TIARA) {
			if (equipment.contains(TIARA_NAME)) {
				accessoryOnHand = true;
			} else {
				if (inventory.contains(TIARA_NAME)) {
					inventory.interact("Wear", TIARA_NAME);
					accessoryOnHand = true;
				}
			}
		}

		return accessoryOnHand;
	}

	public static void setCraftingParams(String altar, String accessory) {

		if (altar.equals(AIR_ALTAR)) {
			RUNES = AIR_RUNE;
			BANK_AREA = FALADOR_EAST_BANK_AREA;
			ALTAR_AREA = AIR_ALTAR_AREA;

			if (accessory.equals(TALISMAN)) {
				TALISMAN_NAME = AIR_TALISMAN;
				ACCESSORY = TALISMAN;
			} else if (accessory.equals(TIARA)) {
				TIARA_NAME = AIR_TIARA;
				ACCESSORY = TIARA;
			}
		} else if (altar.equals(EARTH_ALTAR)) {
			RUNES = EARTH_RUNE;
			BANK_AREA = VARROCK_EAST_BANK_AREA;
			ALTAR_AREA = EARTH_ALTAR_AREA;

			if (accessory.equals(TALISMAN)) {
				TALISMAN_NAME = EARTH_TALISMAN;
				ACCESSORY = TALISMAN;
			} else if (accessory.equals(TIARA)) {
				TIARA_NAME = EARTH_TIARA;
				ACCESSORY = TIARA;
			}
		}

	}

	public void antiBan() throws InterruptedException {
		if (random(0, 5) == 1) {
			sleep(random(BETWEEN_ACTIONS_LOW, BETWEEN_ACTIONS_HIGH));
			switch (random(0, 5)) {
			case 0:
				log("Anti ban case 0: Moving pitch");
				this.camera.movePitch(random(50, 60));
				break;
			case 1:
				log("Anti ban case 2: Opening Skills tab");
				this.tabs.open(Tab.SKILLS);
				break;
			case 2:
				log("Anti ban case 3: Opening Quest tab");
				this.tabs.open(Tab.QUEST);
				break;
			case 3:
				log("Anti ban case 3: Moving yaw");
				this.camera.moveYaw(140 + random(45 + 80));
				break;
			case 4:
				log("Anti ban case 4: Moving pitch");
				this.camera.movePitch(random(20, 80));
				break;
			}
		}
	}

	public static void setESSENCE(String eSSENCE) {
		ESSENCE = eSSENCE;
	}

	public static void setBETWEEN_LOOPS_LOW(int bETWEEN_LOOPS_LOW) {
		BETWEEN_LOOPS_LOW = bETWEEN_LOOPS_LOW;
	}

	public static void setBETWEEN_LOOPS_HIGH(int bETWEEN_LOOPS_HIGH) {
		BETWEEN_LOOPS_HIGH = bETWEEN_LOOPS_HIGH;
	}

	public static void setBETWEEN_ACTIONS_LOW(int bETWEEN_ACTIONS_LOW) {
		BETWEEN_ACTIONS_LOW = bETWEEN_ACTIONS_LOW;
	}

	public static void setBETWEEN_ACTIONS_HIGH(int bETWEEN_ACTIONS_HIGH) {
		BETWEEN_ACTIONS_HIGH = bETWEEN_ACTIONS_HIGH;
	}

	public static void setAccessory(String accessory) {
		ACCESSORY = accessory;
	}

}
