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
public class F2PSmallNetBaitFishingAreas {
    private final Area netFishingAreaLumbridge = new Area(3240, 3151, 3246, 3154);
    private final Area netFishingAreaDraynorVillage = new Area(3083, 3235, 3088, 3221);
    private final Area netFishingAreaAlKharid1 = new Area(3266, 3150, 3270, 3145);
    private final Area netFishingAreaAlKharid2 = new Area(3274, 3142, 3279, 3138);
    private final Area[] netFishingAreaAlKharid = new Area[]{netFishingAreaAlKharid1, netFishingAreaAlKharid2};
    private final Area mudskipperPoint1 = new Area(2986, 3178, 2990, 3175);
    private final Area mudskipperPoint2 = new Area(2997, 3159, 2999, 3157);
    private final Area[] mudskipperPoint = new Area[]{mudskipperPoint1, mudskipperPoint2};
    private final Area musaPoint = new Area(2924, 3180, 2925, 3178);
    private final Area banditCamp = new Area(3048, 3707, 3052, 3705);

    private final List<Area> allAreas = new ArrayList();
    
    public F2PSmallNetBaitFishingAreas(){
        allAreas.add(netFishingAreaLumbridge);
        allAreas.add(netFishingAreaDraynorVillage);
        allAreas.add(musaPoint);
        allAreas.add(banditCamp);
        allAreas.addAll(Arrays.asList(netFishingAreaAlKharid));
        allAreas.addAll(Arrays.asList(mudskipperPoint));
    }
    /**
     * @return the netFishingAreaLumbridge
     */
    public Area getNetFishingAreaLumbridge() {
        return netFishingAreaLumbridge;
    }

    /**
     * @return the netFishingAreaDraynorVillage
     */
    public Area getNetFishingAreaDraynorVillage() {
        return netFishingAreaDraynorVillage;
    }

    /**
     * @return the netFishingAreaAlKharid1
     */
    public Area getNetFishingAreaAlKharid1() {
        return netFishingAreaAlKharid1;
    }

    /**
     * @return the netFishingAreaAlKharid2
     */
    public Area getNetFishingAreaAlKharid2() {
        return netFishingAreaAlKharid2;
    }

    /**
     * @return the netFishingAreaAlKharid
     */
    public Area[] getNetFishingAreaAlKharid() {
        return netFishingAreaAlKharid;
    }

    /**
     * @return the mudskipperPoint1
     */
    public Area getMudskipperPoint1() {
        return mudskipperPoint1;
    }

    /**
     * @return the mudskipperPoint2
     */
    public Area getMudskipperPoint2() {
        return mudskipperPoint2;
    }

    /**
     * @return the mudskipperPoint
     */
    public Area[] getMudskipperPoint() {
        return mudskipperPoint;
    }

    /**
     * @return the musaPoint
     */
    public Area getMusaPoint() {
        return musaPoint;
    }

    /**
     * @return the banditCamp
     */
    public Area getBanditCamp() {
        return banditCamp;
    }

    /**
     * @return the allAreas
     */
    public List<Area> getAllAreas() {
        return allAreas;
    }
}
