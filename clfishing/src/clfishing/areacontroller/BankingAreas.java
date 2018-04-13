/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clfishing.areacontroller;

import clfishing.areacontroller.banking.P2PBankingAreas;
import clfishing.areacontroller.banking.F2PBankingAreas;

/**
 *
 * @author jonas
 */
public class BankingAreas {
    private final F2PBankingAreas f2pBankingAreas;
    private final P2PBankingAreas p2pBankingAreas;
    
    public BankingAreas(){
        f2pBankingAreas = new F2PBankingAreas();
        p2pBankingAreas = new P2PBankingAreas();
    }

    /**
     * @return the f2pBankingAreas
     */
    public F2PBankingAreas getF2pBankingAreas() {
        return f2pBankingAreas;
    }

    /**
     * @return the p2pBankingAreas
     */
    public P2PBankingAreas getP2pBankingAreas() {
        return p2pBankingAreas;
    }
}
