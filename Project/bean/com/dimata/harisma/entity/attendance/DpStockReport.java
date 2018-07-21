/*
 * DpStockReport.java
 *
 * Created on September 29, 2004, 4:33 PM
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author  gedhy
 */
public class DpStockReport extends Entity{
    
    /** Holds value of property empId. */
    private long empId;
    
    /** Holds value of property prevQty. */
    private float prevQty;
    
    /** Holds value of property periodId. */
    private long periodId;
    
    /** Holds value of property mtdQty. */
    private float mtdQty;
    
    /** Holds value of property usedQty. */
    private float usedQty;
    
    /** Holds value of property expiredQty. */
    private float expiredQty;
    
    /** Creates a new instance of DpStockReport */
    public DpStockReport() {
    }
    
    /** Getter for property empId.
     * @return Value of property empId.
     *
     */
    public long getEmpId() {
        return this.empId;
    }
    
    /** Setter for property empId.
     * @param empId New value of property empId.
     *
     */
    public void setEmpId(long empId) {
        this.empId = empId;
    }
    
    /** Getter for property prevQty.
     * @return Value of property prevQty.
     *
     */
    public float getPrevQty() {
        return this.prevQty;
    }
    
    /** Setter for property prevQty.
     * @param prevQty New value of property prevQty.
     *
     */
    public void setPrevQty(float prevQty) {
        this.prevQty = prevQty;
    }
    
    /** Getter for property periodId.
     * @return Value of property periodId.
     *
     */
    public long getPeriodId() {
        return this.periodId;
    }
    
    /** Setter for property periodId.
     * @param periodId New value of property periodId.
     *
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }
    
    /** Getter for property mtdQty.
     * @return Value of property mtdQty.
     *
     */
    public float getMtdQty() {
        return this.mtdQty;
    }
    
    /** Setter for property mtdQty.
     * @param mtdQty New value of property mtdQty.
     *
     */
    public void setMtdQty(float mtdQty) {
        this.mtdQty = mtdQty;
    }
    
    /** Getter for property usedQty.
     * @return Value of property usedQty.
     *
     */
    public float getUsedQty() {
        return this.usedQty;
    }
    
    /** Setter for property usedQty.
     * @param usedQty New value of property usedQty.
     *
     */
    public void setUsedQty(float usedQty) {
        this.usedQty = usedQty;
    }
    
    /** Getter for property expiredQty.
     * @return Value of property expiredQty.
     *
     */
    public float getExpiredQty() {
        return this.expiredQty;
    }
    
    /** Setter for property expiredQty.
     * @param expiredQty New value of property expiredQty.
     *
     */
    public void setExpiredQty(float expiredQty) {
        this.expiredQty = expiredQty;
    }
    
}
