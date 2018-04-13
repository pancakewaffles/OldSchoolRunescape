import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import PotatoMeister.Calculations;

@ScriptManifest(author = "PotatoSalad", info = "Picks potatoes at draynor", logo = "", name = "PotatoPicker", version = 1.0)
public class PotatoPicker extends Script{
	
	//private Image chat = getImage("http://i.imgur.com/gDz0NuG.png");
	
	private final Area bank = new Area(3092, 3242, 3094, 3245);
	private final Area field = new Area(3139, 3289, 3150, 3279);
	
	private RS2Object potato, booth;
	
	private int price;
	private int count, count2, collected;
	private long startTime;


	public void onStart(){
		price = Calculations.getPrice(1942);
		startTime = System.currentTimeMillis();
	}
	
	public void onMessage(Message m){
		if(m.getMessage().contains("You pick a potato.")){
			count++;
			collected++;
		}
	}
	
	public int onLoop() throws InterruptedException {
		if(getInventory().isFull()){
			bank();
		}else{
			if(field.contains(myPlayer())){
				if(!myPlayer().isAnimating() && !myPlayer().isMoving()){
					potato = getObjects().closest(g -> g != null && g.getName().equalsIgnoreCase("Potato") && g.hasAction("Pick") && field.contains(g));
					potato.interact("Pick");
					count = 0;
					int r = Calculations.Random(1200, 1600);
					while(count == 0 && count2 < r){
						sleep(1);
						count2++;
					}
					count2 = 0;
				}
			}else{
				getWalking().webWalk(field);
			}
		}
		
		return Calculations.Random(452, 569);
	}
	
	
    public void onPaint(Graphics2D g) {
        //g.drawImage(chat, 1, 254, null);
        long Runtime = System.currentTimeMillis() - startTime;
        
        long runtime = (int)(Runtime / 1000);
        long collectph = 3600 / runtime * collected;
        long profitph = collectph * price;
        long profit = collected * price;
 
        g.setColor(new Color(31, 221, 223));
        g.drawString("" + collected, 30, 390);
        g.drawString("" + collectph, 30, 433);
        g.drawString("" + profit, 330, 390);
        g.drawString("" + profitph, 330, 433);
        g.setColor(Color.WHITE);
    }
    

	private void bank() {
		if(bank.contains(myPlayer())){
			if(getBank().isOpen()){
				getBank().depositAll();
				getBank().close();
			}else{
				booth = getObjects().closest(g -> g != null && g.getName().equalsIgnoreCase("Bank booth") && g.hasAction("Bank"));
				booth.interact("Bank");
				new ConditionalSleep(4500, Calculations.Random(100, 200)) {

					public boolean condition() throws InterruptedException {
						return getBank().isOpen();
					}

				}.sleep();
			}
		}else{
			getWalking().webWalk(bank);
		}
	}

	private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

}
