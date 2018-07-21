package com.dimata.harisma.entity.search;


import java.util.*;

import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class SrcSysLogger {
 
    private Date logDateStart = new Date();
    private Date logDateEnd = new Date();
    private String logSysMode = "";
    private int logCategory = 0;
    

    public SrcSysLogger() {
    }
    
    
      
    public Date getLogDateStart() {
        return logDateStart;
    }
    
    public void setLogDateStart(Date logDateStart) {
        this.logDateStart = logDateStart;
    }
    
    public Date getLogDateEnd() {
        return logDateEnd;
    }
    
    public void setLogDateEnd(Date logDateEnd) {
        this.logDateEnd = logDateEnd;
    }
    
    public String getLogSysMode() {
        return logSysMode;
    }
    
    public void setLogSysMode(String logSysMode) {
        this.logSysMode = logSysMode;
    }
    
    public int getLogCategory() {
        return logCategory;
    }
    
    public void setLogCategory(int logCategory) {
        this.logCategory = logCategory;
    }
        
}
