/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.harisma.machine.transferdataemployee;
import com.dimata.harisma.entity.attendance.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class TabelMachineTransaction {
    /*
     TRANSACTION_ID  	bigint(20)
     CARD_ID 	varchar(30)
     DATE_TRANSACTION 	date
     MODE 	varchar(5)
     MACHINE 	varchar(5)
     POSTED 	int(11)
     */
    private String cardId;
    private Date dateTransaction;
    private String mode;
    private String station;
    private int verify=0;
    private int posted;
    private String note="";
    
    private long machineTransId;
    
    public final static int VERIFY_VALID=0;
    public final static int VERIFY_INVALID=1;
    public final static String VerifyCode[]={"VALID","INVALID"};
    
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
    
    public void setDateTransaction(String YY, String MM, String DD, String HH, String NN) {
        setDateTransaction(new Date(
                Integer.parseInt(YY)+1900
                , Integer.parseInt(MM)-1
                , Integer.parseInt(DD)
                , Integer.parseInt(HH)
                , Integer.parseInt(NN)
                ));
    }
    
    public void setDateTransactionDate(String YY, String MM, String DD, String HH, String NN) {
        setDateTransaction(new Date(
                Integer.parseInt(YY)-1900
                , Integer.parseInt(MM)-1
                , Integer.parseInt(DD)
                , Integer.parseInt(HH)
                , Integer.parseInt(NN)
                ));
    }

    public void setDateTransaction(String dateStr, String format){
        setDateTransaction(formatDate(dateStr,format));
    }

    public static Date formatDate(String strDate, String inFormat) {
        Date dt = null;
        try {
            SimpleDateFormat inDF = (SimpleDateFormat)DateFormat.getDateInstance();
            inDF.applyPattern(inFormat);
            dt = inDF.parse(strDate);
        } catch(Exception e) {
            System.out.println("ERROR::" + e.toString());
        }
        return dt;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
        if(station!=null) { 
            this.station =station.trim();
        }else{
        this.station = station;
        }
    }

    /**
     * @return the verify
     */
    public int getVerify() {
        return verify;
    }

    /**
     * @param verify the verify to set
     */
    public void setVerify(int verify) {
        this.verify = verify;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the machineTransId
     */
    public long getMachineTransId() {
        return machineTransId;
    }

    /**
     * @param machineTransId the machineTransId to set
     */
    public void setMachineTransId(long machineTransId) {
        this.machineTransId = machineTransId;
    }
    
}
