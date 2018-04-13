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
public class P2PLureBaitFishingAreas {
    private final Area entrana = new Area(2876, 3341, 2878, 3332);
    private final Area treeGnomeStronghold1 = new Area(2390, 3424, 2393, 3422);
    private final Area treeGnomeStronghold2 = new Area(2380, 3416, 2381, 3412);
    private final Area[] treeGnomeStronghold = new Area[]{treeGnomeStronghold1, treeGnomeStronghold2};
    private final Area eastArdougne = new Area(2560, 3377, 2569, 3368);
    private final Area observatory = new Area(2461, 3157, 2475, 3143);
    private final Area isafdar1 = new Area(2215, 3243, 2219, 3244);
    private final Area isafdar2 = new Area(2270, 3257, 2275, 3251);
    private final Area[] isafdar = new Area[]{isafdar1, isafdar2};
    private final Area titheFarm = new Area(1713, 3614, 1721, 3608);
    private final Area mess = new Area(1646, 3560, 1650, 3558);
    private final Area infirmary = new Area(1585, 3567, 1588, 3565);
    private final Area charcoalBurners1 = new Area(1680, 3491, 1676, 3488);
    private final Area charcoalBurners2 = new Area(1670, 3478, 1671, 3480);
    private final Area charcoalBurners3 = new Area(1677, 3471, 1679, 3469);
    private final Area[] charcoalBurners = new Area[]{charcoalBurners1, charcoalBurners2, charcoalBurners3};
    private final Area piscariliusHouse1 = new Area(1746, 3800, 1749, 3801);
    private final Area piscariliusHouse2 = new Area(1763, 3794, 1766, 3795);
    private final Area[] piscariliusHouse = new Area[]{piscariliusHouse1, piscariliusHouse2};
    private final Area jatizso1 = new Area(2402, 3782, 2415, 3781);
    private final Area jatizso2 = new Area(2420, 3790, 2422, 3789);
    private final Area[] jatizso = new Area[]{jatizso1, jatizso2};
    private final Area apeaToll = new Area(2774, 2742, 2778, 2740);
    private final Area shiloVillage = new Area(2854, 2978, 2862, 2971);
    private final Area sinclairMansion1 = new Area(2726, 3526, 2729, 3523);
    private final Area sinclairMansion2 = new Area(2715, 3532, 2718, 3530);
    private final Area[] sinclairMansion = new Area[]{sinclairMansion1, sinclairMansion2};
    private final List<Area> allAreas = new ArrayList();
    
    public P2PLureBaitFishingAreas(){
        allAreas.add(entrana);
        allAreas.add(eastArdougne);
        allAreas.add(observatory);
        allAreas.add(titheFarm);
        allAreas.add(mess);
        allAreas.add(infirmary);
        allAreas.add(apeaToll);
        allAreas.add(shiloVillage);
        allAreas.addAll(Arrays.asList(treeGnomeStronghold));
        allAreas.addAll(Arrays.asList(isafdar));
        allAreas.addAll(Arrays.asList(charcoalBurners));
        allAreas.addAll(Arrays.asList(piscariliusHouse));
        allAreas.addAll(Arrays.asList(jatizso));
        allAreas.addAll(Arrays.asList(sinclairMansion));
    }
    /**
     * @return the entrana
     */
    public Area getEntrana() {
        return entrana;
    }

    /**
     * @return the treeGnomeStronghold1
     */
    public Area getTreeGnomeStronghold1() {
        return treeGnomeStronghold1;
    }

    /**
     * @return the treeGnomeStronghold2
     */
    public Area getTreeGnomeStronghold2() {
        return treeGnomeStronghold2;
    }

    /**
     * @return the treeGnomeStronghold
     */
    public Area[] getTreeGnomeStronghold() {
        return treeGnomeStronghold;
    }

    /**
     * @return the eastArdougne
     */
    public Area getEastArdougne() {
        return eastArdougne;
    }

    /**
     * @return the observatory
     */
    public Area getObservatory() {
        return observatory;
    }

    /**
     * @return the isafdar1
     */
    public Area getIsafdar1() {
        return isafdar1;
    }

    /**
     * @return the isafdar2
     */
    public Area getIsafdar2() {
        return isafdar2;
    }

    /**
     * @return the isafdar
     */
    public Area[] getIsafdar() {
        return isafdar;
    }

    /**
     * @return the titheFarm
     */
    public Area getTitheFarm() {
        return titheFarm;
    }

    /**
     * @return the mess
     */
    public Area getMess() {
        return mess;
    }

    /**
     * @return the infirmary
     */
    public Area getInfirmary() {
        return infirmary;
    }

    /**
     * @return the charcoalBurners1
     */
    public Area getCharcoalBurners1() {
        return charcoalBurners1;
    }

    /**
     * @return the charcoalBurners2
     */
    public Area getCharcoalBurners2() {
        return charcoalBurners2;
    }

    /**
     * @return the charcoalBurners3
     */
    public Area getCharcoalBurners3() {
        return charcoalBurners3;
    }

    /**
     * @return the charcoalBurners
     */
    public Area[] getCharcoalBurners() {
        return charcoalBurners;
    }

    /**
     * @return the piscariliusHouse1
     */
    public Area getPiscariliusHouse1() {
        return piscariliusHouse1;
    }

    /**
     * @return the piscariliusHouse2
     */
    public Area getPiscariliusHouse2() {
        return piscariliusHouse2;
    }

    /**
     * @return the piscariliusHouse
     */
    public Area[] getPiscariliusHouse() {
        return piscariliusHouse;
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
     * @return the apeaToll
     */
    public Area getApeaToll() {
        return apeaToll;
    }

    /**
     * @return the shiloVillage
     */
    public Area getShiloVillage() {
        return shiloVillage;
    }

    /**
     * @return the sinclairMansion1
     */
    public Area getSinclairMansion1() {
        return sinclairMansion1;
    }

    /**
     * @return the sinclairMansion2
     */
    public Area getSinclairMansion2() {
        return sinclairMansion2;
    }

    /**
     * @return the sinclairMansion
     */
    public Area[] getSinclairMansion() {
        return sinclairMansion;
    }

    /**
     * @return the allAreas
     */
    public List<Area> getAllAreas() {
        return allAreas;
    }
}
