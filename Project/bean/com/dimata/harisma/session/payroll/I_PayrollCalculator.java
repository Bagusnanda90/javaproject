/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.overtime.HashTblOvertimeDetail;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author ktanjana
 */
public interface I_PayrollCalculator {
    
   public boolean checkPayrollComponent(String payCompCode, long employeeId, long departmentId, Date selDate, boolean isHoliday, long oidLeave, long oidOvertime, int statusSchedule,int iPositionLevel,int iPropInsentifLevel,String strSchldSymbol1,long empCategory);   
   public double calculatePayrollFormula(String variableCode, long employeeId, long departmentId,long periodId, int dayOfMonth, int statusSchedule,Date dtPeriod,int iPositionLevel , int iPropInsentifLevel);                        
   //public double calculatePayrollFormula(String variableCode, long employeeId, long departmentId,long periodId, int dayOfMonth, int statusSchedule); 
   public void initializedPreloadedData(Date selectedDateFrom, Date selectedDateTo);
   public boolean checkInsentif(long employeeId, long religionId, long departementId, int statusSchedule, int iPositionLevel, int iPropInsentifLevel, long scheduleId, Hashtable hashScheduleOff,boolean isHoliday, Date selectedDate,HashTblOvertimeDetail hashTblOvertimeDetail,long empCategory);
   //update by satrya 2014-01-31 public boolean checkInsentif(long employeeId,long religionId,long departementId,int status,int iPositionLevel,int iPropInsentifLevel,long scheduleId,long scheduleOff,Date selectedDate);
   
   public void loadEmpCategoryInsentif();
}
