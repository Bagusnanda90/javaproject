
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

public class LeaveTarget extends Entity { 
    
    private Date targetDate;
    private String name;
    private double dpTarget;
    private double alTarget;
    private double llTarget;

    public double getAlTarget() {
        return alTarget;
    }

    public void setAlTarget(double alTarget) {
        this.alTarget = alTarget;
    }

    public double getDpTarget() {
        return dpTarget;
    }

    public void setDpTarget(double dpTarget) {
        this.dpTarget = dpTarget;
    }

    public double getLlTarget() {
        return llTarget;
    }

    public void setLlTarget(double llTarget) {
        this.llTarget = llTarget;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }
    
}
