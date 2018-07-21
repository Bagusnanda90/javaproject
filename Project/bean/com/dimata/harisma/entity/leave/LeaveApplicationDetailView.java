/*
 * LeaveApplicationDetailView.java
 *
 * Created on March 7, 2005, 4:22 PM
 */

package com.dimata.harisma.entity.leave;

// core java package
import java.util.Date;

// qdep package
import com.dimata.qdep.entity.*;

/**
 *
 * @author  gedhy
 */
public class LeaveApplicationDetailView extends Entity { 
    
    /** Holds value of property lLeaveMainOid. */
    private long lLeaveMainOid;
    
    /** Holds value of property iLeaveType. */
    private int iLeaveType;
    
    /** Holds value of property dTakenDateFrom. */
    private Date dTakenDateFrom; 
    
    /** Holds value of property dTakenDateTo. */
    private Date dTakenDateTo; 
    
    /** Creates a new instance of LeaveApplicationDetailView */
    public LeaveApplicationDetailView() {
    }
    
    /** Getter for property lLeaveMainOid.
     * @return Value of property lLeaveMainOid.
     *
     */
    public long getLLeaveMainOid() {
        return this.lLeaveMainOid;
    }
    
    /** Setter for property lLeaveMainOid.
     * @param lLeaveMainOid New value of property lLeaveMainOid.
     *
     */
    public void setLLeaveMainOid(long lLeaveMainOid) {
        this.lLeaveMainOid = lLeaveMainOid;
    }
    
    /** Getter for property iLeaveType.
     * @return Value of property iLeaveType.
     *
     */
    public int getILeaveType() {
        return this.iLeaveType;
    }
    
    /** Setter for property iLeaveType.
     * @param iLeaveType New value of property iLeaveType.
     *
     */
    public void setILeaveType(int iLeaveType) {
        this.iLeaveType = iLeaveType;
    }
    
    /** Getter for property dTakenDateFrom.
     * @return Value of property dTakenDateFrom.
     *
     */
    public Date getDTakenDateFrom() {
        return this.dTakenDateFrom;
    }
    
    /** Setter for property dTakenDateFrom.
     * @param dTakenDateFrom New value of property dTakenDateFrom.
     *
     */
    public void setDTakenDateFrom(Date dTakenDateFrom) {
        this.dTakenDateFrom = dTakenDateFrom;
    }
    
    /** Getter for property dTakenDateTo.
     * @return Value of property dTakenDateTo.
     *
     */
    public Date getDTakenDateTo() {
        return this.dTakenDateTo;
    }
    
    /** Setter for property dTakenDateTo.
     * @param dTakenDateTo New value of property dTakenDateTo.
     *
     */
    public void setDTakenDateTo(Date dTakenDateTo) {
        this.dTakenDateTo = dTakenDateTo;
    }
    
}
