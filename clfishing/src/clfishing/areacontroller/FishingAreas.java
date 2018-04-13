/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clfishing.areacontroller;

import clfishing.areacontroller.fishing.F2PCageHarpoonFishingAreas;
import clfishing.areacontroller.fishing.P2PLureBaitFishingAreas;
import clfishing.areacontroller.fishing.P2PBigNetHarpoonFishingArea;
import clfishing.areacontroller.fishing.F2PLureBaitFishingAreas;
import clfishing.areacontroller.fishing.P2PCageHarpoonFishingAreas;
import clfishing.areacontroller.fishing.P2PSmallNetBaitFishingAreas;
import clfishing.areacontroller.fishing.F2PSmallNetBaitFishingAreas;

/**
 *
 * @author jonas
 */
public class FishingAreas {
    //pay to play fishing areas
    private final P2PSmallNetBaitFishingAreas p2pSmallNetBaitFishingAreas;
    private final P2PLureBaitFishingAreas p2pLureBaitFishingAreas;
    private final P2PCageHarpoonFishingAreas p2pCageHarpoonFishingAreas;
    private final P2PBigNetHarpoonFishingArea p2pBigNetHarpoonFishingArea;
    
    //free to play fishing areas
    private final F2PSmallNetBaitFishingAreas f2pSmallNetBaitFishingAreas;
    private final F2PLureBaitFishingAreas f2pLureBaitFishingAreas;
    private final F2PCageHarpoonFishingAreas f2pCageHarpoonFishingAreas;
    
    /**
     * 
     */
    public FishingAreas(){
        p2pSmallNetBaitFishingAreas = new P2PSmallNetBaitFishingAreas();
        p2pLureBaitFishingAreas = new P2PLureBaitFishingAreas();
        p2pCageHarpoonFishingAreas = new P2PCageHarpoonFishingAreas();
        p2pBigNetHarpoonFishingArea = new P2PBigNetHarpoonFishingArea();
        
        f2pSmallNetBaitFishingAreas = new F2PSmallNetBaitFishingAreas();
        f2pLureBaitFishingAreas = new F2PLureBaitFishingAreas();
        f2pCageHarpoonFishingAreas = new F2PCageHarpoonFishingAreas();
    }

    /**
     * @return the p2pSmallNetBaitFishingAreas
     */
    public P2PSmallNetBaitFishingAreas getP2pSmallNetBaitFishingAreas() {
        return p2pSmallNetBaitFishingAreas;
    }

    /**
     * @return the p2pLureBaitFishingAreas
     */
    public P2PLureBaitFishingAreas getP2pLureBaitFishingAreas() {
        return p2pLureBaitFishingAreas;
    }

    /**
     * @return the p2pCageHarpoonFishingAreas
     */
    public P2PCageHarpoonFishingAreas getP2pCageHarpoonFishingAreas() {
        return p2pCageHarpoonFishingAreas;
    }

    /**
     * @return the p2pBigNetHarpoonFishingArea
     */
    public P2PBigNetHarpoonFishingArea getP2pBigNetHarpoonFishingArea() {
        return p2pBigNetHarpoonFishingArea;
    }

    /**
     * @return the f2pSmallNetBaitFishingAreas
     */
    public F2PSmallNetBaitFishingAreas getF2pSmallNetBaitFishingAreas() {
        return f2pSmallNetBaitFishingAreas;
    }

    /**
     * @return the f2pLureBaitFishingAreas
     */
    public F2PLureBaitFishingAreas getF2pLureBaitFishingAreas() {
        return f2pLureBaitFishingAreas;
    }

    /**
     * @return the f2pCageHarpoonFishingAreas
     */
    public F2PCageHarpoonFishingAreas getF2pCageHarpoonFishingAreas() {
        return f2pCageHarpoonFishingAreas;
    }

}
