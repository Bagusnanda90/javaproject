/*
 * barcodeLog.java
 *
 * Created on May 14, 2004, 2:08 PM
 */

package com.dimata.harisma.utility.service.tma;

// package java
import java.util.Date;

// package qdep 
import com.dimata.qdep.entity.*;

/**
 *
 * @author  gedhy
 */
public class BarcodeLog extends Entity {
    
    private Date date = null;
    private String cmdType = "";        
    private String notes = "";

    public Date getDate(){
            return date;
    } 

    public void setDate(Date date) {
        if (date == null) 
            date = new Date();
        this.date = date;
    } 

    public String getCmdType() {
            return cmdType;
    } 

    public void setCmdType(String cmdType) { 
        if (cmdType == null)
                cmdType = "";
        this.cmdType = cmdType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes)
    {
        if (notes == null)
            notes = "";
        this.notes = notes;
    }
    
}
