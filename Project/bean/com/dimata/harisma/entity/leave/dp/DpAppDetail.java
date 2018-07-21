/*
 * LeaveApplication.java
 *
 * Created on October 27, 2004, 11:51 AM
 */

package com.dimata.harisma.entity.leave.dp;

import com.dimata.harisma.entity.leave.*;
import com.dimata.qdep.entity.Entity; 
import java.util.Date;



/**
 *
 * @author  artha
 */
public class DpAppDetail extends Entity
{
    
   long dpAppMainId;
   long dpId;
   Date takenDate;
   int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
   
    public long getDpId() {
        return dpId;
    }

    public void setDpId(long dpId) {
        this.dpId = dpId;
    }

   
    public long getDpAppMainId() {
        return dpAppMainId;
    }

    public void setDpAppMainId(long dpAppMainId) {
        this.dpAppMainId = dpAppMainId;
    }
    
    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }
    
}
