/*
 * CanteenVisitation.java
 * @author rusdianta
 * Created on January 13, 2005, 3:46 PM
 */

package com.dimata.harisma.entity.canteen;

import java.util.Date;
import com.dimata.qdep.entity.*;

public class CanteenVisitation extends Entity {
    
    private long visitationId = 0;
    private long employeeId = 0;
    private Date visitationTime = null;
    private int status = -1;
    private int analyzed = 0;
    private int transferred = 0;
    
    /** Holds value of property numOfVisitation. */
    private int numOfVisitation = 1;
    
    /** Creates a new instance of CanteenVisitation */
    public CanteenVisitation() {
    }
    
    public long getVisitationId() {
        return visitationId;
    }
    
    public void setVisitationId(long visitationId) {
        if (visitationId > 0)
            this.visitationId = visitationId;
        else
            this.visitationId = 0;
    }
    
    public long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(long employeeId) {
        if (employeeId > 0)
            this.employeeId = employeeId;
        else
            this.employeeId = 0;
    }
    
    public Date getVisitationTime() {
        return visitationTime;
    }
    
    public void setVisitationTime(Date visitationTime) {
        this.visitationTime = visitationTime;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public int getAnalyzed() {
        return analyzed;
    }
    
    public void setAnalyzed(int analyzed) {
        this.analyzed = analyzed;        
    }
    
    public int getTransferred() {
        return transferred;
    }
    
    public void setTransferred(int transferred) {
        this.transferred = transferred;
    }
    
    /** Getter for property numOfVisitation.
     * @return Value of property numOfVisitation.
     *
     */
    public int getNumOfVisitation() {
        return this.numOfVisitation;
    }
    
    /** Setter for property numOfVisitation.
     * @param numOfVisitation New value of property numOfVisitation.
     *
     */
    public void setNumOfVisitation(int numOfVisitation) {
        if(numOfVisitation <= 0)
        {
            this.numOfVisitation = 1;
        }
        else
        {
            this.numOfVisitation = numOfVisitation;
        }
    }
    
}
