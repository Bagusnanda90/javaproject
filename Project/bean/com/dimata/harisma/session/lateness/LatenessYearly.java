/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.lateness;

import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class LatenessYearly {

    private String empNum;
    private String empName;
    private Vector<LatenessMonthlyRekap> totalMonthly = new Vector();

    
    public LatenessYearly(String empNum, String empName){
        this.empName = empName;
        this.empNum = empNum;
        for(int i=0; i < 12; i++){
            totalMonthly.add(new LatenessMonthlyRekap());
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
    
    public void setMonthlyRekap(LatenessMonthlyRekap monthlyRekap) {
        if(monthlyRekap==null || monthlyRekap.getMonth() < 0 || monthlyRekap.getMonth() > 12){
            return;
        }
        this.totalMonthly.set(monthlyRekap.getMonth(), monthlyRekap);
    }
    
    public LatenessMonthlyRekap getMonthlyRekap(int month){
        if(month < 0 || month > 12){
            return null;
        }
        return totalMonthly.get(month);
        
    }
    
}
