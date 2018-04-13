package antiban;

import java.util.List;
import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class RightClicky {
	
	private Script script;
	private Area area;
	final int AREA_RADIUS;
	
	
	public RightClicky(Script s, int radius) {
		this.script = s;
		this.AREA_RADIUS = radius;
		
		
	}
	public void begin() {
		this.area = script.myPlayer().getArea(7);
		// Shall we right click ppl, NPCs, or ground items?
		double r = Math.random();
		if(r <= 0.33) { // Right click ppl
			rightClickppl();
			
		}else if(r <= 0.66) { // Right click NPCs
			rightClickNPCs();
			
		}else { // Right click objects
			rightClickObjects();
			
		}
		
		
		
	}
	private void rightClickObjects() {
		script.log("Right click objects.");
		// There are lots of objects, but we can narrow it down to examinable objects.
		List<RS2Object> objectlist = script.getObjects().getAll();
		if(!objectlist.isEmpty()) {
			for(RS2Object object : objectlist) {
				if(area.contains(script.myPlayer()) && area.contains(object) && object.hasAction("Examine")) {
					script.mouse.move(object.getX(),object.getY());
					try {
						script.sleep(400);
					}catch(InterruptedException e) {
						script.log("Sleep disrupted.");
					}
					script.mouse.click(object.getX(),object.getY(),true);
					script.log("Right Clicking "+object.getName());
					if(Math.random() < 0.35) {
						object.interact("Examine");
						script.log("Examining "+ object.getName());
					}
					break;
				}
			}
			script.log("Well, there aren't any examinable objects for me to examine.");
			
		}
		
		
		
	}
	private void rightClickNPCs() {
		script.log("Right click NPCs.");
		
		// We can either right click an NPC, or Examine him/her.
		// First, we must check whether there are any available NPCs.
		if(checkforNPCs() == true) {
			script.log("We have some NPCs we can right click.");
			List<NPC> npclist = script.getNpcs().getAll();
			for(NPC npc : npclist) {
				if(area.contains(script.myPlayer()) && area.contains(npc)) {
					script.mouse.move(npc.getX(),npc.getY());
					try {
						script.sleep(400);
					}catch(InterruptedException e) {
						script.log("Sleep disrupted.");
					}
					script.mouse.click(npc.getX(),npc.getY(),true);
					script.log("Right Clicking "+npc.getName());
					if(Math.random() < 0.35) {
						npc.interact("Examine");
						script.log("Examining "+npc.getName());
						
					}
					break;
					
				}
			}
			
			try {
				MethodProvider.sleep(script.random(300,1800));
			} catch (InterruptedException e) {
				script.log(e);
			}
			
			script.getMouse().moveOutsideScreen();
		}else {
			rightClickObjects();
		}
		
	    
		
		
		
		
	}

	private void rightClickppl() {
		script.log("Right click ppl.");
		
		if(checkforPlayers() == true) {
			script.log("We have some players we can right click.");
			List<Player> playerss = script.players.getAll();
			for(Player p : playerss) {
				if(area.contains(script.myPlayer()) && area.contains(p) && !p.getName().equals(script.myPlayer().getName()) ) {
					
					script.mouse.move(p.getX(),p.getY());
					try {
						script.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						script.log("Sleep disrupted.");
					}
					script.mouse.click(p.getX(),p.getY(),true);
					script.log("Right Clicking "+p.getName());
					break;
				}
			}
			
			try {
				MethodProvider.sleep(script.random(300,1800));
			} catch (InterruptedException e) {
				script.log(e);
			}
			
			script.getMouse().moveOutsideScreen();
		}
		else {
			rightClickNPCs();
		}
		
		
	}
	private boolean checkforPlayers() {
		List<Player> playerss = script.getPlayers().getAll();
		if(playerss.isEmpty()) {
			return false;
		}
        for (Player p : playerss) {
            if( p != null && area.contains(p) && !p.getName().equals(script.myPlayer().getName()) ) {
                return true;
            }
        }
        return false;
	}
	private boolean checkforNPCs() {
		List<NPC> npclist = script.getNpcs().getAll();
		if(npclist.isEmpty()) {
			return false;
		}
		for(NPC npc : npclist) {
			if(npc!=null && area.contains(npc)) {
				return true;
			}
		}
		return false;
	}
}
