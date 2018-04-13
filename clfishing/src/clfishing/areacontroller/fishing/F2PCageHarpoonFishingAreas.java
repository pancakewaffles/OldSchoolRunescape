/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clfishing.areacontroller.fishing;

import java.util.ArrayList;
import java.util.List;
import org.osbot.rs07.api.map.Area;

/**
 *
 * @author jonas
 */
public class F2PCageHarpoonFishingAreas {
    private final Area musaPoint = new Area(2924, 3180, 2925, 3178);
    private final List<Area> allAreas = new ArrayList();
    
    public F2PCageHarpoonFishingAreas(){
        allAreas.add(musaPoint);
    }
    /**
     * @return the musaPoint
     */
    public Area getMusaPoint() {
        return musaPoint;
    }

    /**
     * @return the allAreas
     */
    public List<Area> getAllAreas() {
        return allAreas;
    }

}
