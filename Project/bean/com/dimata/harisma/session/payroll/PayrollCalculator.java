/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.masterdata.HolidaysTable;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.overtime.HashTblOvertimeDetail;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author ktanjana
 */
public class PayrollCalculator implements I_PayrollCalculator {   
    private HolidaysTable holidaysTable=null; // loaded pior to needed, that can get by other modules , so loading is only once
    private static Hashtable hashTblCategoryIsPermanent=null;
    private void loadHolidaysTable(Date startDate, Date endDate){
        holidaysTable = PstPublicHolidays.getHolidaysTable( startDate,  endDate);
    }

    /**
     * @return the holidaysTable
     */
    private  HolidaysTable getHolidaysTable() {
        return holidaysTable;
    }
    
    

    public boolean checkPayrollComponent(String payCompCode, long employeeId, long departmentId, Date selDate, boolean isHoliday, long oidLeave, long oidOvertime, int statusSchedule,int iPositionLevel,int iPropInsentifLevel,String strSchldSymbol1,long empCategoryId) {        
        if(payCompCode!=null){
          if(payCompCode.equalsIgnoreCase("INS"))  {
              // Satria lengkapi
             SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                String dayString = formatterDay.format(selDate);
               if(statusSchedule == PstEmpSchedule.STATUS_PRESENCE_OK 
                    && oidLeave==0 && !strSchldSymbol1.equalsIgnoreCase("off")
                       && iPositionLevel<=iPropInsentifLevel && !isHoliday){
              return true;
               }else{
                   return false;
               }
             
          } else{
              return false;
          }        
        }else{
          return false;
        }
    }

    public double calculatePayrollFormula(String variableCode, long employeeId, long departmentId,long periodId,
           int dayOfMonth, int statusSchedule,Date dtPeriodNew ,int iPositionLevel ,int  iPropInsentifLevel) {
        if(variableCode!=null){
            double result=0;
             if(variableCode.equalsIgnoreCase(PstPaySlip.PAY_COMP_DATE_OK))  {
              result = PstEmpSchedule.getStatusPresenceWoLeave(employeeId, periodId, 31, PstEmpSchedule.STATUS_PRESENCE_OK );
             }   
           return result;
        }else{
            return 0.0d;
        }
    }    

    public void initializedPreloadedData(Date selectedDateFrom, Date selectedDateTo) {
       loadHolidaysTable(selectedDateFrom, selectedDateTo);
    }
//update by satrya 2014-01-31
    public boolean checkInsentif(long employeeId, long religionId, long departementId, int statusSchedule, int iPositionLevel, int iPropInsentifLevel, long scheduleId, Hashtable hashScheduleOff,boolean isHoliday, Date selectedDate,HashTblOvertimeDetail hashTblOvertimeDetail,long empId) {
        return  false;//throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadEmpCategoryInsentif() {
       hashTblCategoryIsPermanent=PstEmpCategory.getHashTableEmpCatId();
    }
}
