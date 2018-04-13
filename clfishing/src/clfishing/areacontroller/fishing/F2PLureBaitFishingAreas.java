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
public class F2PLureBaitFishingAreas {
    private final Area lumbridge = new Area(2838, 3433, 2841, 3432);
    private final Area barbarianVillage = new Area(3105, 3433, 3107, 3431);
    private final List<Area> allAreas = new ArrayList();
    
    public F2PLureBaitFishingAreas(){
        allAreas.add(lumbridge);
        allAreas.add(barbarianVillage);
    }
    /**
     * @return the lumbridge
     */
    public Area getLumbridge() {
        return lumbridge;
    }

    /**
     * @return the barbarianVillage
     */
    public Area getBarbarianVillage() {
        return barbarianVillage;
    }

    /**
     * @return the allAreas
     */
    public List<Area> getAllAreas() {
        return allAreas;
    }

}
