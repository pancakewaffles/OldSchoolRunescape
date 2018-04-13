package antiban;

import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;

public class RightClicky {
	
	Script script;
	Area area;
	
	
	public RightClicky(Script s,Area area) {
		this.script = s;
		this.area = area;
		
	}
	public void begin() {
		
		// Shall we right click ppl, NPCs, or ground items?
		double r = Math.random();
		if(r <= 0.33) { // Right click ppl
			rightClickppl();
			
		}else if(r <= 0.66) { // Right click NPCs
			rightClickNPCs();
			
		}else { // Right click ground items
			rightClickGroundItems();
			
		}
		
		
		
	}
	private void rightClickGroundItems() {
		script.log("Right click ground items.");
		Entity egg = script.getGroundItems().closest("Egg");
		if(egg != null && egg.isVisible()) {
			script.log("Examining an egg.");
			
			script.mouse.move(egg.getX(),egg.getY());	
			script.mouse.click(true);
			egg.interact("Examine");
		}else {
			script.log("I got nothing to examine.");
		}
		
	}
	private void rightClickNPCs() {
		script.log("Right click NPCs.");
		
		NPC target = script.npcs.closest("Chicken");
		if(target != null && target.isVisible()) {
			double r = Math.random();
			if(r < 0.50) {
				script.log("Examining a chicken.");
				
				script.mouse.move(target.getX(),target.getY());	
				script.mouse.click(true);
				target.interact("Examine");
			}else {
				script.log("Right clicking a chicken.");
				
				script.mouse.move(target.getX(),target.getY());	
				script.mouse.click(true);
				
			}
		}
		
		
	}
	private void rightClickppl() {
		script.log("Right click ppl.");
		
		if(checkforPlayers() == true) {
			List<Player> playerss = script.players.getAll();
			for(Player p : playerss) {
				if( area.contains(p) && !p.getName().equals(script.myPlayer().getName()) ) {
					
					script.mouse.move(p.getX(),p.getY());
					script.mouse.click(true);
					break;
				}
			}
		}
		
		
	}
	private boolean checkforPlayers() {
		List<Player> playerss = script.players.getAll();
        for (Player p : playerss) {
            if (area.contains(p) && !p.getName().equals(script.myPlayer().getName())) {
                return true;
            }
        }
        return false;
	}
}
