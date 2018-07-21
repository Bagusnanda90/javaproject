/*
 * LeaveApplicationDetail.java
 *
 * Created on January 28, 2005, 9:33 AM
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
public class LeaveApplicationDetail extends Entity {
    
    /** Holds value of property leaveMainOid. */
    private long leaveMainOid;
    
    /** Holds value of property leaveType. */
    private int leaveType;
    
    /** Holds value of property takenDate. */
    private Date takenDate;
    
    /** Creates a new instance of LeaveApplicationDetail */
    public LeaveApplicationDetail() {
    }
    
    /** Getter for property leaveMainOid.
     * @return Value of property leaveMainOid.
     *
     */
    public long getLeaveMainOid() {
        return this.leaveMainOid;
    }
    
    /** Setter for property leaveMainOid.
     * @param leaveMainOid New value of property leaveMainOid.
     *
     */
    public void setLeaveMainOid(long leaveMainOid) {
        this.leaveMainOid = leaveMainOid;
    }
    
    /** Getter for property leaveType.
     * @return Value of property leaveType.
     *
     */
    public int getLeaveType() {
        return this.leaveType;
    }
    
    /** Setter for property leaveType.
     * @param leaveType New value of property leaveType.
     *
     */
    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
    }
    
    /** Getter for property startTakenDate.
     * @return Value of property startTakenDate.
     *
     */
    public Date getTakenDate() {
        return this.takenDate;
    }
    
    /** Setter for property startTakenDate.
     * @param startTakenDate New value of property startTakenDate.
     *
     */
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }
    
}
