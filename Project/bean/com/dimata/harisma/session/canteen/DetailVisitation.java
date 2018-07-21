/*
 * DetailVisitation.java
 * @author  rusdianta
 * Created on January 27, 2005, 4:24 PM
 */

package com.dimata.harisma.session.canteen;

public class DetailVisitation {
    
    private String employeePayroll;
    private String employeeName;
    private int detailVisits[];
    private int totalVisits;
    
    
    public DetailVisitation() {
        employeePayroll = "";
        employeeName = "";
        detailVisits = new int [0];
        totalVisits = 0;
    }
    
    public DetailVisitation(int numOfDay) {
        employeePayroll = "";
        employeeName = "";
        detailVisits = new int [numOfDay];
        for (int i = 0; i < numOfDay; i++)
            detailVisits[i] = 0;
        totalVisits = 0;
    }
    
    public String getEmployeePayroll() {
        return employeePayroll;
    }
    
    public void setEmployeePayroll(String employeePayroll) {
        if (employeePayroll.length() > 0)
            this.employeePayroll = employeePayroll;
        else
            this.employeePayroll = "";
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        if (employeeName.length() > 0)
            this.employeeName = employeeName;
        else
            this.employeeName = "";
    }
    
    public byte getNumOfDay() {
        return (byte) detailVisits.length;
    }
    
    public int[] getDetailVisits() {
        return detailVisits;
    }
    
    public void setDetailVisits(int detailVisits[]) {
        this.detailVisits = detailVisits;
    }
    
    public void addDetailVisits(int index) {
        if (index < detailVisits.length)
            detailVisits[index]++;
    }
    
    public int getTotalVisits() {
        return totalVisits;
    }
    
    public void setTotalVisits(int totalVisits) {
        if (totalVisits > 0)
            this.totalVisits = totalVisits;
        else
            this.totalVisits = 0;
    }
}
