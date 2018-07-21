/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.specialdisp;

import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class SpecialDispYearly {
    private String empNum;
    private String empName;
    private Vector<SpecialDispMonthlyRekap> totalMonthly = new Vector();

    
    public SpecialDispYearly(String empNum, String empName){
        this.empName = empName;
        this.empNum = empNum;
        for(int i=0; i < 12; i++){
            totalMonthly.add(new SpecialDispMonthlyRekap());
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
    
    public void setMonthlyRekap(SpecialDispMonthlyRekap monthlyRekap) {
        if(monthlyRekap==null || monthlyRekap.getMonth() < 0 || monthlyRekap.getMonth() > 12){
            return;
        }
        this.totalMonthly.set(monthlyRekap.getMonth(), monthlyRekap);
    }
    
    public SpecialDispMonthlyRekap getMonthlyRekap(int month){
        if(month < 0 || month > 12){
            return null;
        }
        return totalMonthly.get(month);
        
    }
}
