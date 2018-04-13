import java.awt.Color;

import java.awt.Font;

import java.awt.Graphics2D;

import java.awt.Point;

import java.awt.Rectangle;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.URL;

import java.net.URLConnection;

import java.util.Optional;


import org.osbot.rs07.api.map.Area;

import org.osbot.rs07.api.map.Position;

import org.osbot.rs07.api.map.constants.Banks;

import org.osbot.rs07.api.model.GroundItem;

import org.osbot.rs07.api.ui.Skill;

import org.osbot.rs07.api.ui.Spells;

import org.osbot.rs07.script.MethodProvider;

import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import org.osbot.rs07.utility.ConditionalSleep;




@ScriptManifest(author = "Author", info = "", logo = "", name = "Zamorak Wine Grabber", version = 1.0)

public class WineOfZamorak extends Script {

	public static Area area = new Area(2933, 3513, 2930, 3517);

	public static Position pos = new Position(2930, 3515, 0);
	
	public static Position closestPositiontoWine = new Position(2931,3515,0);

	public static String state;

	private Optional<Integer> price;

	private long startTime;

	private long itemCount = 0;

	private long currentItemCount = -1;
	


	@ Override

	public void onStart() {

		price = getPrice(245);

		log(price);

		startTime = System.currentTimeMillis();

		getExperienceTracker().start(Skill.MAGIC);
		
		log(getEquipment().isWieldingWeapon("Staff of air"));

	}


	@ Override

	public int onLoop() throws InterruptedException {

		if (getInventory().contains("Law rune") && getEquipment().isWieldingWeapon("Staff of air")) {

			for (States state : States.values())

				if (state.canProcess(this))

					state.process(this);

			recountItems();

		}else { 
			retrieveRequiredEquipment();
			
			// We have our equipment. Now time to equip them.
			getInventory().interact("Wield", "Staff of air");
		}

		return 33;

	}


	private void retrieveRequiredEquipment() {
		// Let's go to the bank.
		if(!Banks.FALADOR_WEST.contains(myPlayer())) {
		state = "Walking to Falador Bank.";
		getWalking().webWalk(Banks.FALADOR_WEST);
		}
		
		// Let's withdraw our required items.
		if (getBank().isOpen()) {
			// If we do not have our staff of air equipped, or in the inventory, we withdraw it from the bank. I am not checking if it is present in the bank.
			if(!getEquipment().isWieldingWeapon("Staff of air") && !getInventory().contains("Staff of air")) {
				getBank().withdraw("Staff of air", 1);
				try {
					sleep(random(100,200));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log("Can't sleep.");
				}
			}
			
			// We check if there are any law runes in our bank. If so, we withdraw them.
			if(getBank().getAmount("Law rune") > 0) {
				log("You have "+getBank().getAmount("Law rune") + " in your bank. We shall withdraw them all.");
				getBank().withdrawAll("Law rune");
				try {
					sleep(random(100,200));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log("Can't sleep.");
				}
				
			}
			
			// Do a final check to see if we have law runes in our inventory. If we don't, then we stop the script.
			if(!getInventory().contains("Law rune")){
				log("You do not have enough law runes. Stopping script now.");
				getBank().close();
				try {
					sleep(random(75,150));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log("Can't sleep.");
				}
				getLogoutTab().open();
				try {
					sleep(random(75,150));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log("Can't sleep.");
				}
				getLogoutTab().logOut();
				stop();
				
			}
				
				
				
			
			
			getBank().close();
			try {
				sleep(random(500,1000));
			}catch(InterruptedException e) {
				log("Can't sleep.");
			}
			
			

		} else {
			state = "Opening bank.";
			getObjects().closest("Bank booth").interact("Bank");
			new ConditionalSleep(3000) {
				@Override
				public boolean condition() throws InterruptedException {
					return getBank().isOpen();
				}
			}.sleep();
		}
		
		
		
		
		
		
	}


	enum States {
		

		GRAB {


			@ Override

			public boolean canProcess(MethodProvider mp) {

				return area.contains(mp.myPlayer()) && !mp.getInventory().isFull();

			}


			@ Override

			public void process(MethodProvider mp) {

				GroundItem wine = mp.getGroundItems().closest("Wine of zamorak");

				if (mp.getMagic().isSpellSelected()) {

					if (wine != null && wine.isVisible()) {

						wine.interact("Cast");

						state = "Interacting";
						
						try { // Prevents over-clicking of spell.
							mp.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							mp.log("Can't sleep.");
						}

					} else {

						Rectangle rect = pos.getPolygon(mp.getBot()).getBounds();

						mp.getMouse().move(rect.x + (rect.width / 2), rect.y + (rect.height / 2));

						state = "Moving mouse to center";

					}

				} else {

					state = "Casting";

					mp.getMagic().castSpell(Spells.NormalSpells.TELEKINETIC_GRAB);

				}

			}


		},

		WALK {

			@ Override 

			public boolean canProcess(MethodProvider mp) {

				return !Banks.FALADOR_WEST.contains(mp.myPlayer()) && mp.getInventory().isFull()

						|| !area.contains(mp.myPlayer()) && !mp.getInventory().isFull();

			}


			@ Override 

			public void process(MethodProvider mp) {

				

				if (!Banks.FALADOR_WEST.contains(mp.myPlayer())) {
					
					// Check if we can teleport to Falador instead.
					/*
					try {
						if(mp.getMagic().canCast(Spells.NormalSpells.FALADOR_TELEPORT)) {
							mp.getMagic().castSpell(Spells.NormalSpells.FALADOR_TELEPORT);
							state = "Teleporting to Falador.";
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						mp.log("Teleportation failed.");
					}
					*/
					
					state = "Walking to Falador Bank.";
					mp.getWalking().webWalk(Banks.FALADOR_WEST);

				}else{
					state = "Walking to area.";
					mp.getWalking().webWalk(area);
					// Make sure you walk to the closest position. Increases chances of getting the wine.
					// Especially if you are competing with other ppl.
					mp.getWalking().webWalk(closestPositiontoWine);
				}
					

			}


		},

		BANK {


			@ Override 

			public boolean canProcess(MethodProvider mp) {

				return Banks.FALADOR_WEST.contains(mp.myPlayer()) && mp.getInventory().isFull();

			}


			@ Override 

			public void process(MethodProvider mp) throws InterruptedException {

				if (mp.getBank().isOpen()) {

					state = "Depositing";

					mp.getBank().depositAll("Wine of zamorak");

				} else {

					state = "Opening bank";

					mp.getBank().open();

					new ConditionalSleep(1000) {

						@Override
						public boolean condition() throws InterruptedException {

							return mp.getBank().isOpen();

						}

					};

				}

			}


		};

		public abstract boolean canProcess(MethodProvider mp) throws InterruptedException;


		public abstract void process(MethodProvider mp) throws InterruptedException;

	}


	public void onPaint(Graphics2D g) {

		Graphics2D cursor = (Graphics2D) g.create();

		Graphics2D paint = (Graphics2D) g.create();


		final long runTime = System.currentTimeMillis() - startTime;

		Point mP = getMouse().getPosition();

		Rectangle rect = pos.getPolygon(getBot()).getBounds();

		Color tBlack = new Color(0, 0, 0, 128);

		paint.setFont(new Font("Arial", Font.PLAIN, 12));


		paint.setColor(tBlack);

		paint.fillRect(0, 255, 200, 80);

		paint.setColor(Color.WHITE);

		paint.drawRect(0, 255, 200, 80);

		paint.drawString("Wine Grabber " + getVersion(), 5, 270);

		paint.drawString("Time running: " + formatTime(runTime), 5, 285);

		paint.drawString("Magic xp gained: " + formatValue(getExperienceTracker().getGainedXP(Skill.MAGIC)), 5, 300);

		paint.drawString("Gained money: " + formatValue(price.get() * itemCount), 5, 315);

		paint.drawString("State: " + state, 5, 330);


		cursor.setColor(Color.WHITE);

		cursor.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5);

		cursor.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);

		cursor.drawPolygon(pos.getPolygon(getBot()));

		cursor.drawString("x", rect.x + (rect.width / 2), rect.y + (rect.height / 2));

	}


	private Optional<Integer> getPrice(int id) {

		Optional<Integer> price = Optional.empty();

		try {

			URL url = new URL("http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);

			URLConnection con = url.openConnection();

			con.setRequestProperty("User-Agent",

					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

			con.setUseCaches(true);

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String[] data = br.readLine().replace("{", "").replace("}", "").split(",");

			br.close();

			price = Optional.of(Integer.parseInt(data[0].split(":")[1]));

		} catch (Exception e) {

			e.printStackTrace();

		}

		return price;

	}


	public final String formatValue(final long l) {

		return (l > 1_000_000) ? String.format("%.2fm", (double) (l / 1_000_000))

				: (l > 1000) ? String.format("%.1fk", (double) (l / 1000)) : l + "";

	}


	public final String formatTime(final long ms) {

		long s = ms / 1000, m = s / 60, h = m / 60;

		s %= 60;

		m %= 60;

		h %= 24;

		return String.format("%02d:%02d:%02d", h, m, s);

	}


	public void recountItems() {

		long amt = getInventory().getAmount("Wine of zamorak");

		if (currentItemCount == -1)

			currentItemCount = amt;

		else if (amt < currentItemCount) {

			currentItemCount = amt;

		} else {

			itemCount += amt - currentItemCount;

			currentItemCount = amt;

		}

	}

}