/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.logrpt;

/**
 *
 * @author D
 */
import com.dimata.qdep.entity.*;
import java.util.Date;

public class LogHistory extends Entity {
 private long logObjId=0;
    private Date logDate = new Date();
    private String logNote = "";
    private long userId = 0;
    private String userName="";

    /**
     * @return the logObjId
     */
    public long getLogObjId() {
        return logObjId;
    }

    /**
     * @param logObjId the logObjId to set
     */
    public void setLogObjId(long logObjId) {
        this.logObjId = logObjId;
    }

    /**
     * @return the logDate
     */
    public Date getLogDate() {
        return logDate;
    }

    /**
     * @param logDate the logDate to set
     */
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    /**
     * @return the logNote
     */
    public String getLogNote() {
        return logNote;
    }

    /**
     * @param logNote the logNote to set
     */
    public void setLogNote(String logNote) {
        this.logNote = logNote;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
