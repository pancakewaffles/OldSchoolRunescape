import org.osbot.rs07.script.Script;


import static org.osbot.rs07.script.MethodProvider.random;

import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;

public class GrabRequiredItems extends Task {
	
	private String FISHING_EQUIPMENT = "Harpoon";
	private String COINS = "Coins";
	private Area BANK = Banks.FALADOR_EAST;

	public GrabRequiredItems(Script script) {
		super(script);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean verify() {

		if(script.getInventory().contains(FISHING_EQUIPMENT) && script.getInventory().contains(COINS)) {
			return false;
		}
		return true;
	}

	@Override
	public int execute() {
		// We will use Falador East Bank because that is the nearest to Port Sarim.
		if(!BANK.contains(script.myPlayer())) {
			script.getWalking().webWalk(BANK);
		}else {
			// Once in area, open bank and deposit all, withdraw all coins and 1 harpoon, in that order.
			getRequiredItems();
			
		}
		
		
		
		
		
		
		return random(500,1000);
	}

	private void getRequiredItems() {
		if (script.getBank().isOpen()) {
			try {
				script.sleep(random(300, 500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				script.log("Can't sleep.");
			}

			script.getBank().depositAll();
			
			script.getBank().withdraw(FISHING_EQUIPMENT,1);
			
			script.getBank().withdrawAll(COINS);
			
		} else {
			RS2Object bankBooth = script.getObjects().closest("Bank booth");
			if (bankBooth != null) {
				if (bankBooth.isVisible()) {
					bankBooth.interact("Bank");
					try {
						script.sleep(random(2000, 3000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						script.log("Can't sleep.");
					}
				} else {
					script.getCamera().toEntity(bankBooth);
				}
			}

		}
		
	}

	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return "Walking to Falador East Bank to get required items.";
	}

}
