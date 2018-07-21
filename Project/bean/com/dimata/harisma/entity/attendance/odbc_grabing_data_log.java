/*
 * odbc_grabing_data_log.java
 *
 * Created on November 9, 2006, 10:06 AM
 */

package com.dimata.harisma.entity.attendance;

/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
/**
 *
 * @author  yunny
 */
public class odbc_grabing_data_log extends Entity{
    private long logId;
    private Date date;
    
   
    public long getLogId() {
        return logId;
    }    
   
    public void setLogId(long logId) {
        this.logId = logId;
    }    
  
    public Date getDate() {
        return date;
    }
  
    public void setDate(Date date) {
        this.date = date;
    }
    
}
