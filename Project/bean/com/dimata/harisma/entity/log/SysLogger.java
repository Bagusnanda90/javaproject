
package com.dimata.harisma.entity.log;


import java.util.*;

import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class SysLogger extends Entity {
   
    private long logId = 0;
    private Date logDate = new Date();
    private String logSysMode = "";
    private int logCategory = 0;
    private String logNote = "";
    
    
    public SysLogger() {
    }
    
    
    public long getLogId() {
        return logId;
    }
    
    public void setLogId(long logId) {
        this.logId = logId;
    }
    
    public Date getLogDate() {
        return logDate;
    }
    
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
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
    
    public String getLogNote() {
        return logNote;
    }
    
    public void setLogNote(String logNote) {
        this.logNote = logNote;
    }
    
}
