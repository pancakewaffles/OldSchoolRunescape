/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clfishing.areacontroller.fishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.osbot.rs07.api.map.Area;
/**
 *
 * @author jonas
 */
public class P2PSmallNetBaitFishingAreas {
    private final Area catherby1 = new Area(2838, 3433, 2841, 3432);
    private final Area catherby2 = new Area(2845, 3431, 2848, 3430);
    private final Area catherby3 = new Area(2853, 3425, 2856, 3424);
    private final Area catherby4 = new Area(2859, 3428, 2862, 3427);
    private final Area[] catherby = new Area[]{catherby1, catherby2, catherby3, catherby4};
    private final Area entrana1 = new Area(2841, 3354, 2845, 3355);
    private final Area entrana2 = new Area(2843, 3361, 2845, 3360);
    private final Area entrana3 = new Area(2848, 3363, 2851, 3362);
    private final Area[] entrana = new Area[]{entrana1, entrana2, entrana3};
    private final Area rellekka1 = new Area(2631, 3696, 2632, 3693);
    private final Area rellekka2 = new Area(2640, 3699, 2641, 3697);
    private final Area rellekka3 = new Area(2649, 3709, 2652, 3709);
    private final Area[] rellekka = new Area[]{rellekka1, rellekka2, rellekka3};
    private final Area barbarianAssault1 = new Area(2498, 3545, 2501, 3547);
    private final Area barbarianAssault2 = new Area(2511, 3560, 2513, 3562);
    private final Area barbarianAssault3 = new Area(2517, 3577, 2518, 3573);
    private final Area[] barbarianAssault = new Area[]{barbarianAssault1, barbarianAssault2, barbarianAssault3};
    private final Area fishingArea = new Area(2792, 3279, 2794, 3275);
    private final List<Area> allAreas = new ArrayList();
    
    public P2PSmallNetBaitFishingAreas(){
        allAreas.addAll(Arrays.asList(catherby)); 
        allAreas.addAll(Arrays.asList(entrana)); 
        allAreas.addAll(Arrays.asList(rellekka)); 
        allAreas.addAll(Arrays.asList(barbarianAssault)); 
        allAreas.add(fishingArea); 
    }
    /**
     * @return the catherby1
     */
    public Area getCatherby1() {
        return catherby1;
    }

    /**
     * @return the catherby2
     */
    public Area getCatherby2() {
        return catherby2;
    }

    /**
     * @return the catherby3
     */
    public Area getCatherby3() {
        return catherby3;
    }

    /**
     * @return the catherby4
     */
    public Area getCatherby4() {
        return catherby4;
    }

    /**
     * @return the catherby
     */
    public Area[] getCatherby() {
        return catherby;
    }

    /**
     * @return the entrana1
     */
    public Area getEntrana1() {
        return entrana1;
    }

    /**
     * @return the entrana2
     */
    public Area getEntrana2() {
        return entrana2;
    }

    /**
     * @return the entrana3
     */
    public Area getEntrana3() {
        return entrana3;
    }

    /**
     * @return the entrana
     */
    public Area[] getEntrana() {
        return entrana;
    }

    /**
     * @return the rellekka1
     */
    public Area getRellekka1() {
        return rellekka1;
    }

    /**
     * @return the rellekka2
     */
    public Area getRellekka2() {
        return rellekka2;
    }

    /**
     * @return the rellekka3
     */
    public Area getRellekka3() {
        return rellekka3;
    }

    /**
     * @return the rellekka
     */
    public Area[] getRellekka() {
        return rellekka;
    }

    /**
     * @return the barbarianAssault1
     */
    public Area getBarbarianAssault1() {
        return barbarianAssault1;
    }

    /**
     * @return the barbarianAssault2
     */
    public Area getBarbarianAssault2() {
        return barbarianAssault2;
    }

    /**
     * @return the barbarianAssault3
     */
    public Area getBarbarianAssault3() {
        return barbarianAssault3;
    }

    /**
     * @return the barbarianAssault
     */
    public Area[] getBarbarianAssault() {
        return barbarianAssault;
    }

    /**
     * @return the fishingArea
     */
    public Area getFishingArea() {
        return fishingArea;
    }

    /**
     * @return the allAreas
     */
    public List<Area> getAllAreas() {
        return allAreas;
    }
}
