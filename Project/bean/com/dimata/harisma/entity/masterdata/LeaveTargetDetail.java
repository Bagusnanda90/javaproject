
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata;
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class LeaveTargetDetail extends Entity { 
    
    private long leaveTargetId;
    private float targetDP;
    private float targetAL;
    private float targetLL;
    private long deparmentId;

    public long getDeparmentId() {
        return deparmentId;
    }

    public void setDeparmentId(long deparmentId) {
        this.deparmentId = deparmentId;
    }

    public float getTargetAL() {
        return targetAL;
    }

    public void setTargetAL(float targetAL) {
        this.targetAL = targetAL;
    }

    public float getTargetDP() {
        return targetDP;
    }

    public void setTargetDP(float targetDP) {
        this.targetDP = targetDP;
    }

    public float getTargetLL() {
        return targetLL;
    }

    public void setTargetLL(float targetLL) {
        this.targetLL = targetLL;
    }

    public long getLeaveTargetId() {
        return leaveTargetId;
    }

    public void setLeaveTargetId(long leaveTargetId) {
        this.leaveTargetId = leaveTargetId;
    }

    
    
    
    
}
