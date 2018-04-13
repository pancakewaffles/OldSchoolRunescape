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
public class P2PBankingAreas {
    private final Area duelArenaBank = Banks.DUEL_ARENA;
    private final Area clanWarsBank = new Area(3367, 3174, 3372, 3172);
    private final Area mosLeHarmlessBank = new Area(3679, 2983, 3683, 2981);
    private final Area burghDeRottBank = new Area(3493, 3212, 3499, 3211);
    private final Area canafisBank = Banks.CANIFIS;
    private final Area portPhasmatysBank = new Area(3687, 3470, 3690, 3462);
    private final Area warriorsGuildBank = new Area(2840, 3544, 2845, 3539);
    private final Area catherbyBank = Banks.CATHERBY;
    private final Area shiloVillageBank = new Area(2844, 2955, 2860, 2953);
    private final Area marimBank = new Area(2780, 2784, 2781, 2782);
    private final Area voidKnightsOutpostBank = new Area(2666, 2654, 2668, 2652);
    private final Area wizardsGuildBank = new Area(2610, 3096, 2614, 3089);
    private final Area trawlerBank = new Area(2660, 3162, 2664, 3160);
    private final Area eastArdougneBankNorth = Banks.ARDOUGNE_NORTH;
    private final Area eastArdougneSouth = Banks.ARDOUGNE_SOUTH;
    private final Area fishingGuildBank = new Area(2584, 3421, 2588, 3419);
    private final Area seersVillageBank = new Area(2729, 3495, 2722, 3491);
    private final Area etceteriaBank = new Area(2618, 3896, 2621, 3893);
    private final Area jetizsoBank = new Area(2415, 3802, 2418, 3800);
    private final Area neitiznotBank = new Area(2335, 3806, 2338, 3808);
    private final Area lunarIsleBank = new Area(2098, 3920, 2103, 3918);
    private final Area piscatorisFishingColonyBank = new Area(2328, 3687, 2331, 3692);
    private final Area treeGnomeStrongholdBank = new Area(2446, 3426, 2450, 3422);
    private final Area lletyaBank = new Area(2351, 3162, 2354, 3166);
    private final Area castleWarsBank = Banks.CASTLE_WARS;
    private final Area doorsOfDinh = new Area(1639, 3943, 1641, 3946);
    private final Area blastMineBank = new Area(1503, 3854, 1507, 3856);
    private final Area sulphurMineBank = new Area(1458, 3860, 1455, 3863);
    private final Area lovakiteMineBank = Banks.LOVAKITE_MINE;
    private final Area lovakengjHouseBank = Banks.LOVAKENGJ_HOUSE;
    private final Area mountQuidamortemBank = new Area(1253, 3572, 1255, 3570);
    private final Area shayzienHouseBank = Banks.SHAYZIEN_HOUSE;
    private final Area landsEndBank = new Area(1509, 3422, 1513, 3416);
    private final Area woodcuttingGuildBank = new Area(1590, 3479, 1592, 3476);
    private final Area hosidiusHouseBank = Banks.HOSIDIUS_HOUSE;
    private final Area messBank = new Area(1653, 3608, 1659, 3606);
    private final Area arceuusHouse =  Banks.ARCEUUS_HOUSE;
    private final Area vineryBank = new Area(1806, 3571, 1811, 3575);
    private final Area grandTreeBank1 = new Area(2449, 3479, 2449, 3481).setPlane(1);
    private final Area grandTreeBank2 = new Area(2439, 3488, 2441, 3488).setPlane(1);
    private final Area shantayPassBank = new Area(3306, 3122, 3310, 3118);
    private final List<Area> allBanks = new ArrayList();
    
    public P2PBankingAreas(){
        allBanks.add(duelArenaBank);
        allBanks.add(clanWarsBank);
        allBanks.add(mosLeHarmlessBank);
        allBanks.add(burghDeRottBank);
        allBanks.add(canafisBank);
        allBanks.add(portPhasmatysBank);
        allBanks.add(warriorsGuildBank);
        allBanks.add(catherbyBank);
        allBanks.add(shiloVillageBank);
        allBanks.add(marimBank);
        allBanks.add(voidKnightsOutpostBank);
        allBanks.add(wizardsGuildBank);
        allBanks.add(trawlerBank);
        allBanks.add(eastArdougneBankNorth);
        allBanks.add(eastArdougneSouth);
        allBanks.add(fishingGuildBank);
        allBanks.add(seersVillageBank);
        allBanks.add(etceteriaBank);
        allBanks.add(jetizsoBank);
        allBanks.add(neitiznotBank);
        allBanks.add(lunarIsleBank);
        allBanks.add(piscatorisFishingColonyBank);
        allBanks.add(treeGnomeStrongholdBank);
        allBanks.add(lletyaBank);
        allBanks.add(castleWarsBank);
        allBanks.add(doorsOfDinh);
        allBanks.add(blastMineBank);
        allBanks.add(sulphurMineBank);
        allBanks.add(lovakiteMineBank);
        allBanks.add(lovakengjHouseBank);
        allBanks.add(mountQuidamortemBank);
        allBanks.add(shayzienHouseBank);
        allBanks.add(landsEndBank);
        allBanks.add(woodcuttingGuildBank);
        allBanks.add(hosidiusHouseBank);
        allBanks.add(messBank);
        allBanks.add(arceuusHouse);
        allBanks.add(vineryBank);
        allBanks.add(grandTreeBank1);
        allBanks.add(grandTreeBank2);
        allBanks.add(shantayPassBank);
    }
    /**
     * @return the duelArenaBank
     */
    public Area getDuelArenaBank() {
        return duelArenaBank;
    }

    /**
     * @return the clanWarsBank
     */
    public Area getClanWarsBank() {
        return clanWarsBank;
    }
    
    /**
     * @return the mosLeHarmlessBank
     */
    public Area getMosLeHarmlessBank() {
        return mosLeHarmlessBank;
    }

    /**
     * @return the burghDeRottBank
     */
    public Area getBurghDeRottBank() {
        return burghDeRottBank;
    }

    /**
     * @return the canafisBank
     */
    public Area getCanafisBank() {
        return canafisBank;
    }

    /**
     * @return the portPhasmatysBank
     */
    public Area getPortPhasmatysBank() {
        return portPhasmatysBank;
    }

    /**
     * @return the warriorsGuildBank
     */
    public Area getWarriorsGuildBank() {
        return warriorsGuildBank;
    }

    /**
     * @return the catherbyBank
     */
    public Area getCatherbyBank() {
        return catherbyBank;
    }

    /**
     * @return the shiloVillageBank
     */
    public Area getShiloVillageBank() {
        return shiloVillageBank;
    }

    /**
     * @return the marimBank
     */
    public Area getMarimBank() {
        return marimBank;
    }

    /**
     * @return the voidKnightsOutpostBank
     */
    public Area getVoidKnightsOutpostBank() {
        return voidKnightsOutpostBank;
    }

    /**
     * @return the wizardsGuildBank
     */
    public Area getWizardsGuildBank() {
        return wizardsGuildBank;
    }

    /**
     * @return the trawlerBank
     */
    public Area getTrawlerBank() {
        return trawlerBank;
    }

    /**
     * @return the eastArdougneBankNorth
     */
    public Area getEastArdougneBank1() {
        return eastArdougneBankNorth;
    }

    /**
     * @return the eastArdougneSouth
     */
    public Area getEastArdougneBank2() {
        return eastArdougneSouth;
    }

    /**
     * @return the fishingGuildBank
     */
    public Area getFishingGuildBank() {
        return fishingGuildBank;
    }

    /**
     * @return the seersVillageBank
     */
    public Area getSeersVillageBank() {
        return seersVillageBank;
    }

    /**
     * @return the etceteriaBank
     */
    public Area getEtceteriaBank() {
        return etceteriaBank;
    }

    /**
     * @return the jetizsoBank
     */
    public Area getJetizsoBank() {
        return jetizsoBank;
    }

    /**
     * @return the neitiznotBank
     */
    public Area getNeitiznotBank() {
        return neitiznotBank;
    }

    /**
     * @return the lunarIsleBank
     */
    public Area getLunarIsleBank() {
        return lunarIsleBank;
    }

    /**
     * @return the piscatorisFishingColonyBank
     */
    public Area getPiscatorisFishingColonyBank() {
        return piscatorisFishingColonyBank;
    }

    /**
     * @return the treeGnomeStrongholdBank
     */
    public Area getTreeGnomeStrongholdBank() {
        return treeGnomeStrongholdBank;
    }

    /**
     * @return the lletyaBank
     */
    public Area getLletyaBank() {
        return lletyaBank;
    }

    /**
     * @return the castleWarsBank
     */
    public Area getCastleWarsBank() {
        return castleWarsBank;
    }

    /**
     * @return the doorsOfDinh
     */
    public Area getDoorsOfDinh() {
        return doorsOfDinh;
    }

    /**
     * @return the blastMineBank
     */
    public Area getBlastMineBank() {
        return blastMineBank;
    }

    /**
     * @return the sulphurMineBank
     */
    public Area getSulphurMineBank() {
        return sulphurMineBank;
    }

    /**
     * @return the lovakiteMineBank
     */
    public Area getLovakiteMineBank() {
        return lovakiteMineBank;
    }

    /**
     * @return the lovakengjHouseBank
     */
    public Area getLovakengjHouseBank() {
        return lovakengjHouseBank;
    }

    /**
     * @return the mountQuidamortemBank
     */
    public Area getMountQuidamortemBank() {
        return mountQuidamortemBank;
    }

    /**
     * @return the shayzienHouseBank
     */
    public Area getShayzienHouseBank() {
        return shayzienHouseBank;
    }

    /**
     * @return the landsEndBank
     */
    public Area getLandsEndBank() {
        return landsEndBank;
    }

    /**
     * @return the woodcuttingGuildBank
     */
    public Area getWoodcuttingGuildBank() {
        return woodcuttingGuildBank;
    }

    /**
     * @return the hosidiusHouseBank
     */
    public Area getHosidiusHouseBank() {
        return hosidiusHouseBank;
    }

    /**
     * @return the messBank
     */
    public Area getMessBank() {
        return messBank;
    }

    /**
     * @return the arceuusHouse
     */
    public Area getArceuusHouse() {
        return arceuusHouse;
    }

    /**
     * @return the vineryBank
     */
    public Area getVineryBank() {
        return vineryBank;
    }

    /**
     * @return the grandTreeBank1
     */
    public Area getGrandTreeBank1() {
        return grandTreeBank1;
    }

    /**
     * @return the grandTreeBank2
     */
    public Area getGrandTreeBank2() {
        return grandTreeBank2;
    }

    /**
     * @return the allBanks
     */
    public List<Area> getAllBanks() {
        return allBanks;
    }

    /**
     * @return the shantayPassBank
     */
    public Area getShantayPassBank() {
        return shantayPassBank;
    }
}
