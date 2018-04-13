import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;


@ScriptManifest(author = "Shaft", info = "searches the shelf in wise old man's house", logo = "", name = "TinderBoxes", version = 1.0)
public class TinderBoxes extends Script {

	private final Area house = new Area(3087, 3251, 3092, 3255);
	private final Area bank = new Area(3092, 3242, 3094, 3245);
	private RS2Object shelf, booth;
	
	private Image chat = getImage("http://i.imgur.com/Y5iHofA.png");

	private int count, count2;
	private int price;
	private long startTime;

	
	public void onStart(){
		price = Calculations.getPrice(590);
		startTime = System.currentTimeMillis();
	}
	
	public int onLoop() throws InterruptedException {
		if (!getInventory().isFull()) {
			if (house.contains(myPlayer())) {
				if (count < 28) {
					if (getInventory().contains(i -> i != null && i.getName().equalsIgnoreCase("tinderbox"))) {
						count += getInventory().getAmount(i -> i != null && i.getId() == 590);
						getInventory().dropAll(i -> i != null && i.getId() == 590);
						new ConditionalSleep(1500, Calculations.Random(100, 200)) {
							public boolean condition() throws InterruptedException {
								return !getInventory().contains(i -> i != null && i.getName().equalsIgnoreCase("tinderbox"));
							}
						}.sleep();
					} else {
						shelf = getObjects().closest(g -> g != null && g.getId() == 7079);
						if(getCamera().getYawAngle() < 330 && getCamera().getYawAngle() > 30){
							int yaw = Calculations.Random(-331, 29);
							if(yaw < 0) yaw = yaw + (-1);
							getCamera().moveYaw(yaw);
							sleep(Calculations.Random(12, 16));
						}
						shelf.interact("Search");
						new ConditionalSleep(4500, Calculations.Random(100, 200)) {
							public boolean condition() throws InterruptedException {
								return getInventory().contains(i -> i != null && i.getName().equalsIgnoreCase("tinderbox"));
							}

						}.sleep();
						if(getInventory().contains(i -> i != null && i.getId() == 590 && i.getName().equalsIgnoreCase("tinderbox"))){
							count2++;
						};
					}
				} else {
					GroundItem tinderbox = getGroundItems().closest(i -> i != null && i.getId() == 590);
					tinderbox.interact("Take");
				}

			} else {
				getWalking().webWalk(house);
			}
		} else {
			bank();
		}

		return Calculations.Random(34, 49);
	}

	private void bank() {
		if (bank.contains(myPlayer())) {
			if (getBank().isOpen()) {
				getBank().depositAll();
				getBank().close();
				count = 0;
			} else {
				booth = getObjects().closest(g -> g != null && g.getName().equalsIgnoreCase("Bank booth") && g.hasAction("Bank"));
				booth.interact("Bank");
				new ConditionalSleep(4500, Calculations.Random(100, 200)) {
					public boolean condition() throws InterruptedException {
						return getBank().isOpen();
					}
				}.sleep();
			}
		} else {
			getWalking().webWalk(bank);
		}
	}
	
	public void onPaint(Graphics2D g){
		g.drawString("" + count, 50, 50);
		g.drawString("" + count2, 50, 65);
        g.drawImage(chat, 1, 254, null);
        long Runtime = System.currentTimeMillis() - startTime;
        
        long runtime = (int)(Runtime / 1000);
        long collectph = 3600 / runtime * count2;
        long profitph = collectph * price;
        long profit = count2 * price;
 
        g.setColor(new Color(31, 221, 223));
        g.drawString("" + count2, 30, 390);
        g.drawString("" + collectph, 30, 433);
        g.drawString("" + profit, 330, 390);
        g.drawString("" + profitph, 330, 433);
		
	}
	
	private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

}