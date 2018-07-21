/*
 * SummaryDailyVisitation.java
 * @author rusdianta
 * Created on January 31, 2005, 5:07 PM
 */

package com.dimata.harisma.session.canteen;

import java.util.Vector;
import java.util.Date;

public class SummaryDailyVisitation {
    
    private String departmentName;
    private int totalNonNightVisits;
    private int totalNightVisits;
    private int numVisits;
    private long departmentOid;
    private Vector values = new Vector();
    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Vector getValues() {
        return values;
    }

    public void setValues(int values) {
        this.values.add(String.valueOf(values));
    }

    public long getDepartmentOid() {
        return departmentOid;
    }

    public void setDepartmentOid(long departmentOid) {
        this.departmentOid = departmentOid;
    }

    /** Creates a new instance of SummaryDailyVisitation */
    public SummaryDailyVisitation() {
        departmentName = "";
        totalNonNightVisits = 0;
        totalNightVisits = 0;
        numVisits = 0;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        if (departmentName.length() > 0)
            this.departmentName = departmentName;
        else
            this.departmentName = "";
    }
    
    public int getTotalNonNightVisits() {
        return totalNonNightVisits;
    }
    
    public void setTotalNonNightVisits(int totalNonNightVisits) {
        if (totalNonNightVisits > 0)
            this.totalNonNightVisits = totalNonNightVisits;
        else
            this.totalNonNightVisits = 0;
    }
    
    public int getTotalNightVisits() {
        return totalNightVisits;
    }
    
    public void setTotalNightVisits(int totalNightVisits) {
        if (totalNightVisits > 0)
            this.totalNightVisits = totalNightVisits;
        else
            this.totalNightVisits = 0;
    }
    
    public int getNumVisits() {
        return numVisits;
    }
    
    public void setNumVisits(int numVisits) {
        if (numVisits > 0)
            this.numVisits = numVisits;
        else
            this.numVisits = 0;
    }    
}
