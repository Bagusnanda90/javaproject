/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.lateness;

/**
 *
 * @author khirayinnura
 */
public class LatenessMonthlyRekap {
    private String empNum = "";
    private String empName = "";
    private int sumHour = 0;
    private int sumMinute = 0;
    private String totalMonth = "";
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
     * @return the sumHour
     */
    public int getSumHour() {
        return sumHour;
    }

    /**
     * @param sumHour the sumHour to set
     */
    public void setSumHour(int sumHour) {
        this.sumHour = sumHour;
    }

    /**
     * @return the sumMinute
     */
    public int getSumMinute() {
        return sumMinute;
    }

    /**
     * @param sumMinute the sumMinute to set
     */
    public void setSumMinute(int sumMinute) {
        this.sumMinute = sumMinute;
    }

    /**
     * @return the totalMonth
     */
    public String getTotalMonth() {
        return totalMonth;
    }

    /**
     * @param totalMonth the totalMonth to set
     */
    public void setTotalMonth(String totalMonth) {
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
