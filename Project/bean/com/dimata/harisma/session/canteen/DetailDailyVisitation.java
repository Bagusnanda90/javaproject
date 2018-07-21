/*
 * DetailDailyVisitation.java
 * @author  rusdianta
 * Created on January 27, 2005, 1:29 PM
 */

package com.dimata.harisma.session.canteen;

import java.util.*;

public class DetailDailyVisitation {
    
    private String employeePayroll;
    private String employeeName;
    private String scheduleSymbol;

    public String getStrVisitTimes() {
        return strVisitTimes;
    }

    public void setStrVisitTimes(String strVisitTimes) {
        this.strVisitTimes = strVisitTimes;
    }

    private boolean nightShift;
    private Vector visitTimes = new Vector();
    private String strVisitTimes = "";
    private int numVisits;
    
    /** Creates a new instance of DetailDailyVisitation */
    public DetailDailyVisitation() {
        employeePayroll = "";
        employeeName = "";
        scheduleSymbol = "-";
        nightShift = false;
        visitTimes = null;
        numVisits = 0;
    }
    
    public String getEmployeePayroll() {
        return employeePayroll;
    }
    
    public void setEmployeePayroll(String employeePayroll) {
        this.employeePayroll = employeePayroll;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public boolean getNightShift() {
        return nightShift;
    }
    
    public void setNightShift(boolean nightShift) {
        this.nightShift = nightShift;
    }
    
    public String getScheduleSymbol() {
        return scheduleSymbol;
    }
    
    public void setScheduleSymbol(String scheduleSymbol) {
        if (scheduleSymbol.length() > 0)            
            this.scheduleSymbol = scheduleSymbol;
        else
            this.scheduleSymbol = "-";
    }
    
    public Vector getVisitTimes() {
        return visitTimes;
    }
    
    public void setVisitTimes(Vector visitTimes) {
        this.visitTimes = visitTimes;
    }
    
    public int getNumVisits() {
        return numVisits;
    }
    
    public void setNumVisits(int numVisits) {
        this.numVisits = numVisits;
    }
}
