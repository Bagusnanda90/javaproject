/*
 * ProcenPresence.java
 *
 * Created on August 28, 2007, 1:42 PM
 */

package com.dimata.harisma.entity.payroll;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class ProcenPresence extends Entity {
    private double procenPresence = 0;
    private int absenceDay = 0;
    
    
    /** Creates a new instance of ProcenPresence */
    public ProcenPresence() {
    }
    
    /**
     * Getter for property procenPresence.
     * @return Value of property procenPresence.
     */
    public double getProcenPresence() {
        return procenPresence;
    }
    
    /**
     * Setter for property procenPresence.
     * @param procenPresence New value of property procenPresence.
     */
    public void setProcenPresence(double procenPresence) {
        this.procenPresence = procenPresence;
    }
    
    /**
     * Getter for property absenceDay.
     * @return Value of property absenceDay.
     */
    public int getAbsenceDay() {
        return absenceDay;
    }
    
    /**
     * Setter for property absenceDay.
     * @param absenceDay New value of property absenceDay.
     */
    public void setAbsenceDay(int absenceDay) {
        this.absenceDay = absenceDay;
    }
    
}
