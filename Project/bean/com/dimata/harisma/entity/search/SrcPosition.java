/*
 * SrcPosition.java
 *
 * Created on January 5, 2005, 1:30 PM
 */

package com.dimata.harisma.entity.search;

import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class SrcPosition {
    /* Update by Hendra Putu | 2015-08-15 */
    private int radioButton = 1;
    private String startDate = "";
    private String endDate = "";
    /** Holds value of property posName. */
    private String posName = "";
    
    /** Holds value of property posLevel. */
    private int posLevel = -1;
    private long levelRankID = 0;
    
    /** Creates a new instance of SrcPosition */
    public SrcPosition() {
    }
    
    /** Getter for property posName.
     * @return Value of property posName.
     *
     */
    public String getPosName() {
        return this.posName;
    }
    
    /** Setter for property posName.
     * @param posName New value of property posName.
     *
     */
    public void setPosName(String posName) {
        this.posName = posName;
    }
    
    /** Getter for property posLevel.
     * @return Value of property posLevel.
     *
     */
    public int getPosLevel() {
        return this.posLevel;
    }
    
    /** Setter for property posLevel.
     * @param posLevel New value of property posLevel.
     *
     */
    public void setPosLevel(int posLevel) {
        this.posLevel = posLevel;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the radioButton
     */
    public int getRadioButton() {
        return radioButton;
    }

    /**
     * @param radioButton the radioButton to set
     */
    public void setRadioButton(int radioButton) {
        this.radioButton = radioButton;
    }

    /**
     * @return the levelRankID
     */
    public long getLevelRankID() {
        return levelRankID;
    }

    /**
     * @param levelRankID the levelRankID to set
     */
    public void setLevelRankID(long levelRankID) {
        this.levelRankID = levelRankID;
    }
    
}
