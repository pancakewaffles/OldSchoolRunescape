package main.script.Wool_Runner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(author = "Author", info = "Wool Running made ez pz", name = "sheepShearer", version = 1, logo = "")
public class WoolRunner extends Script {
	
	private static final Area SHEEP_AREA = new Area(3211, 3259, 3195, 3274).setPlane(0);
    private long scriptTimer = System.currentTimeMillis();
    
    private Area IntermediateArea = new Area(3216,3212,3213,3225);
    private Area PenultimateArea = new Area(3206,3209,3210,3211);
   
	
	@Override
	public void onStart() throws InterruptedException {
		/*
		if (!myPlayer().getPosition().equals(SHEEP_AREA)) {
			getWalking().webWalk(SHEEP_AREA);
		}
		*/
	}

    

    private final ConditionalSleep animationSleep = new ConditionalSleep(500, 1200) {
        @Override
        public boolean condition() throws InterruptedException {
            return myPlayer().isAnimating();
        }
    };

    @SuppressWarnings("unchecked")
	private Optional<NPC> getClosestSheep() {
        return Optional.ofNullable(getNpcs().closest(true, npc ->
                npc.getName().equals("Sheep") && npc.hasAction("Shear") && SHEEP_AREA.contains(npc) && !npc.hasAction("Talk-to") // Watch out for THE THING
        ));
    }

    @Override
    public int onLoop() throws InterruptedException {
    	if(!getInventory().contains("Shears")) {
    		getRequiredItems();
    	}else {
    	
        if (getInventory().isFull()) {
            if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
                if (getBank().isOpen()) {
                    if (!getInventory().isEmptyExcept("Shears")) {
                        getBank().depositAllExcept("Shears");
                    }
                } else {
                    getBank().open();
                }
            } else {
                if (execute(new WebWalkEvent(Banks.LUMBRIDGE_UPPER)).hasFinished()) {
                    return 100;
                }
            }
        } else {
            if (SHEEP_AREA.contains(myPlayer())) {
                if (!myPlayer().isAnimating()) {
                    getClosestSheep().ifPresent(npc -> {
                        if (npc.interact("Shear")) {
                            animationSleep.sleep();
                        }
                    });
                }
            } else {
                if (getBank().isOpen()) {
                    getBank().close();
                } else {
                    if (execute(new WebWalkEvent(SHEEP_AREA)).hasFinished()) {
                        return 100;
                    }
                }
            }
        }
        
    	}
        return 1000;
    }

	private void getRequiredItems() {
		
		if(!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			// First we walk to the Intermediate Area. Webwalking has some issues with Lumbridge Upper Bank.
			if(getWalking().webWalk(IntermediateArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return IntermediateArea.contains(myPosition());
                    }
                }.sleep();
			}
			log("We reached the Lumbridge Castle Foyer.");
			
			if(getWalking().webWalk(PenultimateArea)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                	@Override
                    public boolean condition() throws InterruptedException{
                         return PenultimateArea.contains(myPosition());
                    }
                }.sleep();
			}
			
			log("We reached the left stairwell.");
			
			
             if(getWalking().webWalk(Banks.LUMBRIDGE_UPPER)){
                new ConditionalSleep(900000, (int)(Math.random() * 500 + 300)){
                    @Override
                    public boolean condition() throws InterruptedException{
                         return Banks.LUMBRIDGE_UPPER.contains(myPosition());
                    }
                }.sleep();
             }
		}else {
			if (getBank().isOpen()) {
				try {
					sleep(random(300, 500));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log("Can't sleep.");
				}

				getBank().depositAll();
				
				getBank().withdraw("Shears",1);
				
				
			} else {
				RS2Object bankBooth = getObjects().closest("Bank booth");
				if (bankBooth != null) {
					if (bankBooth.isVisible()) {
						bankBooth.interact("Bank");
						try {
							sleep(random(2000, 3000));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							log("Can't sleep.");
						}
					} else {
						getCamera().toEntity(bankBooth);
					}
				}

			}
		}
		
		
		
		
	
		
	
	}
	

	@Override
	public void onPaint(Graphics2D graphics) {
		drawMouse(graphics);
		Font font = new Font("TimesRoman", Font.PLAIN, 14);
		graphics.setFont(font);
		graphics.setColor(Color.WHITE);
		graphics.drawString("Wool Runner script created by: Author", 5, 40);
		long runTime = System.currentTimeMillis() - scriptTimer;
		graphics.drawString("Script Runtime: " + formatTime(runTime), 5, 55);
	}

	public void drawMouse(Graphics graphic) {
		((Graphics2D) graphic).setRenderingHints(
				new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		Point pointer = mouse.getPosition();
		Graphics2D spinG = (Graphics2D) graphic.create();
		Graphics2D spinGRev = (Graphics2D) graphic.create();
		spinG.setColor(new Color(255, 255, 255));
		spinGRev.setColor(Color.cyan);
		spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2 * Math.PI / 180.0, pointer.x, pointer.y);
		spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d) * 2 * Math.PI / 180.0, pointer.x,
				pointer.y);

		final int outerSize = 20;
		final int innerSize = 12;

		spinG.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		spinG.drawArc(pointer.x - (outerSize / 2), pointer.y - (outerSize / 2), outerSize, outerSize, 100, 75);
		spinG.drawArc(pointer.x - (outerSize / 2), pointer.y - (outerSize / 2), outerSize, outerSize, -100, 75);
		spinGRev.drawArc(pointer.x - (innerSize / 2), pointer.y - (innerSize / 2), innerSize, innerSize, 100, 75);
		spinGRev.drawArc(pointer.x - (innerSize / 2), pointer.y - (innerSize / 2), innerSize, innerSize, -100, 75);
	}

	public final String formatTime(final long ms) {
		long s = ms / 1000, m = s / 60, h = m / 60, d = h / 24;
		s %= 60;
		m %= 60;
		h %= 24;
		return d > 0 ? String.format("%02d:%02d:%02d:%02d", d, h, m, s)
				: h > 0 ? String.format("%02d:%02d:%02d", h, m, s) : String.format("%02d:%02d", m, s);
	}

	public void bank() throws InterruptedException {
		openBank();
		depositBank();
		closeBank();
	}

	public void openBank() throws InterruptedException {
		RS2Object bankObject = getObjects().closest(18491);
		if (!getBank().isOpen()) {
			bankObject.interact("Bank");
			sleep(random(1600, 5000));
		}
	}

	public void depositBank() throws InterruptedException {
		if (getBank().isOpen()) {
			getBank().depositAllExcept("Shears");
			sleep(random(400, 750));
		}
	}

	public void closeBank() throws InterruptedException {
		if (getBank().isOpen()) {
			getBank().close();
		}
	}

	
	
}
