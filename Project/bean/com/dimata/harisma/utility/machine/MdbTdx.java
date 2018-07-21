/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

/**
 *
 * @author Roy Andika
 * @Desc Untuk database mdb tdx 
 */
public class MdbTdx {
   
    private String cardId   = "";
    private String dateTrn  = "";
    private int posted;
    private String station  = "";
    private String mode     = "";
    private int HarismaSt   = 0;
    
    public String mode_in   = "A";
    public String mode_out  = "B";
    

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getDateTrn() {
        return dateTrn;
    }

    public void setDateTrn(String dateTrn) {
        this.dateTrn = dateTrn;
    }

    public int getPosted() {
        return posted;
    }

    public void setPosted(int posted) {
        this.posted = posted;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getHarismaSt() {
        return HarismaSt;
    }

    public void setHarismaSt(int HarismaSt) {
        this.HarismaSt = HarismaSt;
    }
    
}
