/*
 * SplitShiftMonthly.java
 *
 * Created on May 27, 2004, 12:28 PM
 */

package com.dimata.harisma.session.attendance;

import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class SplitShiftYearly {
    
    private String empNum;
    private String empName;
    private Vector<SplitShiftMonthlyRekap> totalMonthly = new Vector();

    
    public SplitShiftYearly(String empNum, String empName){
        this.empName = empName;
        this.empNum = empNum;
        for(int i=0; i < 12; i++){
            totalMonthly.add(new SplitShiftMonthlyRekap());
        }
        
    }
    
    /**
     * @return the empNum
     */
    public String getEmpNum() {
        return empNum;
    }

    /**
     * @param empNum the empNum to set
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    /**
     * @return the empName
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @return the totalMonthly
     */
    public Vector getTotalMonthly() {
        return totalMonthly;
    }

    /**
     * @param totalMonthly the totalMonthly to set
     */
    private void setTotalMonthly(Vector totalMonthly) {
        this.totalMonthly = totalMonthly;
    }
    
    public void setMonthlyRekap(SplitShiftMonthlyRekap monthlyRekap) {
        if(monthlyRekap==null || monthlyRekap.getMonth() < 0 || monthlyRekap.getMonth() > 12){
            return;
        }
        this.totalMonthly.set(monthlyRekap.getMonth(), monthlyRekap);
    }
    
    public SplitShiftMonthlyRekap getMonthlyRekap(int month){
        if(month < 0 || month > 12){
            return null;
        }
        return totalMonthly.get(month);
        
    }

    
}
