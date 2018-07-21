/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessMachineTransactionDestop {
    private Vector dtTransaction = new Vector();
    private String cardId;
    private Vector mode  = new Vector();
    

    /**
     * @return the dtTransaction
     */
    public Date getDtTransaction(int idx) {
        Date dtTrans=null;
        if(dtTransaction!=null && dtTransaction.size()>0){
            dtTrans = (Date)dtTransaction.get(idx);
        }
        return dtTrans;
        //return dtTransaction;
    }
    
    public int getSizeDtTransaction() {
        return dtTransaction.size();
    }

    /**
     * @param dtTransaction the dtTransaction to set
     */
    public void addDtTransaction(Date dtTrans) {
        this.dtTransaction.add(dtTrans);
    }

    /**
     * @return the cardId
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId the cardId to set
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * @return the mode
     */
    public String getMode(int idx) {
        String modeAbs="";
        if(mode!=null && mode.size()>0){
            modeAbs = (String)mode.get(idx);
        }
        return modeAbs;
    }

    /**
     * @param mode the mode to set
     */
    public void addMode(String mode) {
        this.mode.add(mode);
    }
    
    public int getSizeMode() {
        return mode.size();
    }
}
