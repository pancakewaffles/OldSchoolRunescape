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
public class P2PCageHarpoonFishingAreas {
    private final Area fishingGuild1 = new Area(2602, 3425, 2604, 3424);
    private final Area fishingGuild2 = new Area(2611, 3414, 2608, 3415);
    private final Area fishingGuild3 = new Area(2627, 3413, 2629, 3417);
    private final Area fishingGuild4 = new Area(2633, 3426, 2634, 3424);
    private final Area fishingGuild5 = new Area(2631, 3434, 2632, 3437);
    private final Area fishingGuild6 = new Area(2639, 3445, 2638, 3442);
    private final Area[] fishingGuild = new Area[]{fishingGuild1, fishingGuild2, fishingGuild3, fishingGuild4, fishingGuild5, fishingGuild6};
    private final Area catherby1 = new Area(2838, 3433, 2841, 3432);
    private final Area catherby2 = new Area(2845, 3431, 2848, 3430);
    private final Area catherby3 = new Area(2853, 3425, 2856, 3424);
    private final Area catherby4 = new Area(2859, 3428, 2862, 3427);
    private final Area[] catherby = new Area[]{catherby1, catherby2, catherby3, catherby4};
    private final Area jatizso1 = new Area(2402, 3782, 2415, 3781);
    private final Area jatizso2 = new Area(2420, 3790, 2422, 3789);
    private final Area[] jatizso = new Area[]{jatizso1, jatizso2};
    private final Area rellekka1 = new Area(2631, 3696, 2632, 3693);
    private final Area rellekka2 = new Area(2640, 3699, 2641, 3697);
    private final Area rellekka3 = new Area(2649, 3709, 2652, 3709);
    private final Area[] rellekka = new Area[]{rellekka1, rellekka2, rellekka3};

    private final List<Area> allAreas = new ArrayList();
    
    public P2PCageHarpoonFishingAreas(){
        allAreas.addAll(Arrays.asList(fishingGuild));
        allAreas.addAll(Arrays.asList(catherby));
        allAreas.addAll(Arrays.asList(jatizso));
        allAreas.addAll(Arrays.asList(rellekka));
    }
    /**
     * @return the fishingGuild1
     */
    public Area getFishingGuild1() {
        return fishingGuild1;
    }

    /**
     * @return the fishingGuild2
     */
    public Area getFishingGuild2() {
        return fishingGuild2;
    }

    /**
     * @return the fishingGuild3
     */
    public Area getFishingGuild3() {
        return fishingGuild3;
    }

    /**
     * @return the fishingGuild4
     */
    public Area getFishingGuild4() {
        return fishingGuild4;
    }

    /**
     * @return the fishingGuild5
     */
    public Area getFishingGuild5() {
        return fishingGuild5;
    }

    /**
     * @return the fishingGuild6
     */
    public Area getFishingGuild6() {
        return fishingGuild6;
    }

    /**
     * @return the fishingGuild
     */
    public Area[] getFishingGuild() {
        return fishingGuild;
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
     * @return the jatizso1
     */
    public Area getJatizso1() {
        return jatizso1;
    }

    /**
     * @return the jatizso2
     */
    public Area getJatizso2() {
        return jatizso2;
    }

    /**
     * @return the jatizso
     */
    public Area[] getJatizso() {
        return jatizso;
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
     * @return the allAreas
     */
    public List<Area> getAllAreas() {
        return allAreas;
    }
    
}
