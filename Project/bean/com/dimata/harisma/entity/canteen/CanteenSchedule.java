/*
 * CanteenSchedule.java
 *
 * Created on April 23, 2005, 11:29 AM
 */

package com.dimata.harisma.entity.canteen;

import java.util.Date;
import com.dimata.qdep.entity.*;

/**
 *
 * @author  gedhy
 */
public class CanteenSchedule extends Entity 
{
    
    /** Holds value of property sCode. */
    private String sCode = "";
    
    /** Holds value of property sName. */
    private String sName = "";
    
    /** Holds value of property dScheduleDate. */
    private Date dScheduleDate;
    
    /** Holds value of property tTimeOpen. */
    private Date tTimeOpen;
    
    /** Holds value of property tTimeClose. */
    private Date tTimeClose;
    
    /** Creates a new instance of CanteenSchedule */
    public CanteenSchedule() {
    }
    
    /** Getter for property sCode.
     * @return Value of property sCode.
     *
     */
    public String getSCode() {
        return this.sCode;
    }
    
    /** Setter for property sCode.
     * @param sCode New value of property sCode.
     *
     */
    public void setSCode(String sCode) {
        this.sCode = sCode;
    }
    
    /** Getter for property sName.
     * @return Value of property sName.
     *
     */
    public String getSName() {
        return this.sName;
    }
    
    /** Setter for property sName.
     * @param sName New value of property sName.
     *
     */
    public void setSName(String sName) {
        this.sName = sName;
    }
    
    /** Getter for property dScheduleDate.
     * @return Value of property dScheduleDate.
     *
     */
    public Date getDScheduleDate() {
        return this.dScheduleDate;
    }
    
    /** Setter for property dScheduleDate.
     * @param dScheduleDate New value of property dScheduleDate.
     *
     */
    public void setDScheduleDate(Date dScheduleDate) {
        this.dScheduleDate = dScheduleDate;
    }
    
    /** Getter for property tTimeOpen.
     * @return Value of property tTimeOpen.
     *
     */
    public Date getTTimeOpen() {
        return this.tTimeOpen;
    }
    
    /** Setter for property tTimeOpen.
     * @param tTimeOpen New value of property tTimeOpen.
     *
     */
    public void setTTimeOpen(Date tTimeOpen) {
        this.tTimeOpen = tTimeOpen;
    }
    
    /** Getter for property tTimeClose.
     * @return Value of property tTimeClose.
     *
     */
    public Date getTTimeClose() {
        return this.tTimeClose;
    }
    
    /** Setter for property tTimeClose.
     * @param tTimeClose New value of property tTimeClose.
     *
     */
    public void setTTimeClose(Date tTimeClose) {
        this.tTimeClose = tTimeClose;
    }
    
}
