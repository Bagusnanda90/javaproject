/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.specialdisp;

/**
 *
 * @author khirayinnura
 */
public class SpecialDispMonthlyRekap {
    private String empNum = "";
    private String empName = "";
    private int totalMonth = 0;
    private int month = 0;

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
     * @return the totalMonth
     */
    public int getTotalMonth() {
        return totalMonth;
    }

    /**
     * @param totalMonth the totalMonth to set
     */
    public void setTotalMonth(int totalMonth) {
        this.totalMonth = totalMonth;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }
}
