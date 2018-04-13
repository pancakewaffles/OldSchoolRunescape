/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clfishing.areacontroller.banking;

import java.util.ArrayList;
import java.util.List;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;

/**
 *
 * @author jonas
 */
public class F2PBankingAreas {
    private final Area draynorVillageBank = Banks.DRAYNOR;
    private final Area alKharidBank = Banks.AL_KHARID;
    private final Area varrockBankEast = Banks.VARROCK_EAST;
    private final Area varrockBankWest = Banks.VARROCK_WEST;
    private final Area lumbridgeBankLower =  Banks.LUMBRIDGE_LOWER;
    private final Area lumbridgeBankUpper =  Banks.LUMBRIDGE_UPPER;
    private final Area faladorBankEast = Banks.FALADOR_EAST;
    private final Area faladorBankWest = Banks.FALADOR_WEST;
    private final Area edgeville = Banks.EDGEVILLE;
    private final Area grandExchangeBank = Banks.GRAND_EXCHANGE;

    private final List<Area> allBanks = new ArrayList();
    
    public F2PBankingAreas(){
        allBanks.add(draynorVillageBank);
        allBanks.add(alKharidBank);
        allBanks.add(varrockBankEast);
        allBanks.add(varrockBankWest);
        allBanks.add(lumbridgeBankLower);
        allBanks.add(lumbridgeBankUpper);
        allBanks.add(faladorBankEast);
        allBanks.add(faladorBankWest);
        allBanks.add(edgeville);
        allBanks.add(grandExchangeBank);
    }
    /**
     * @return the draynorVillageBank
     */
    public Area getDraynorVillageBank() {
        return draynorVillageBank;
    }

    /**
     * @return the alKharidBank
     */
    public Area getAlKharidBank() {
        return alKharidBank;
    }

    /**
     * @return the varrockBankEast
     */
    public Area getVarrockBank1() {
        return varrockBankEast;
    }

    /**
     * @return the varrockBankWest
     */
    public Area getVarrockBank2() {
        return varrockBankWest;
    }

    /**
     * @return the lumbridgeBankLower
     */
    public Area getLumbridgeBank() {
        return lumbridgeBankLower;
    }

    /**
     * @return the faladorBankEast
     */
    public Area getFaladorBank1() {
        return faladorBankEast;
    }

    /**
     * @return the faladorBankWest
     */
    public Area getFaladorBank2() {
        return faladorBankWest;
    }

    /**
     * @return the edgeville
     */
    public Area getEdgeville() {
        return edgeville;
    }

    /**
     * @return the grandExchangeBank
     */
    public Area getGrandExchangeBank() {
        return grandExchangeBank;
    }

    /**
     * @return the allBanks
     */
    public List<Area> getAllBanks() {
        return allBanks;
    }

    /**
     * @return the varrockBankEast
     */
    public Area getVarrockBankEast() {
        return varrockBankEast;
    }

    /**
     * @return the varrockBankWest
     */
    public Area getVarrockBankWest() {
        return varrockBankWest;
    }

    /**
     * @return the lumbridgeBankLower
     */
    public Area getLumbridgeBankLower() {
        return lumbridgeBankLower;
    }

    /**
     * @return the lumbridgeBankUpper
     */
    public Area getLumbridgeBankUpper() {
        return lumbridgeBankUpper;
    }

    /**
     * @return the faladorBankEast
     */
    public Area getFaladorBankEast() {
        return faladorBankEast;
    }

    /**
     * @return the faladorBankWest
     */
    public Area getFaladorBankWest() {
        return faladorBankWest;
    }
    
}
