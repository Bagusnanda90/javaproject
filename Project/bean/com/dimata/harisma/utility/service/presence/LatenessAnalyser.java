/*
 * LatenessAnalyser.java
 *
 * Created on June 7, 2004, 9:33 AM
 */

package com.dimata.harisma.utility.service.presence;

// package core java

import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.session.lateness.SessEmployeeLateness;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.DateCalc;

import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;

public class LatenessAnalyser {
    
    static Date dtStartSvc; // untuk start pertama service
    static Date dtHistory; // untuk start pertama service
    static boolean running = false; // status service
    
    public Date getDate(){
        return dtStartSvc;
    }
    
    public void setDate(Date dt ){
        dtHistory = dt;
        dtStartSvc = dt;
    }
    
    public Date getDateHistory(){
        return dtHistory;
    }
    
    public boolean getStatus(){
        return running;
    }
    
    public LatenessAnalyser() {
    }
    
    public synchronized void startService() {
        if (!running) {
            System.out.println(".:: LatenessAnalyser service started ... !!!");
            try {
                running = true;
                Thread thr = new Thread(new ServiceLateness());
                thr.setDaemon(false);
                thr.start();
                
            } catch (Exception e) {
                System.out.println(">>> Exc when LatenessAnalyser started : " + e.toString());
            }
        }
    }
    
    public synchronized void stopService() {
        running = false;
        System.out.println(".:: LatenessAnalyser service stoped ... !!!");
    }
    
//    public void checkEmployeeLateness(Date presenceDate) {
//        Vector result = new Vector(1, 1);
//        if (presenceDate != null) {
//            DBResultSet dbrs = null;
//            long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
//            int selectedIndex = presenceDate.getDate();
//            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
//            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
//            try {
//                String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
//                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
//                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
//                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
//                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
//                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
//                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
//                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
//                " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
//                " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
//                " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
//                " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
//                " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
//                " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
//                " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
//                " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
//                " = " + periodId +
//                
//                // mencari data masing-masing first "in-out" atau second "in-out" yang tidak null
//                " AND" +
//                " (" +
//                " (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] + ")" +
//                " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] + "))" +
//                " OR (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + ")" +
//                " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + "))" +
//                " )" ;
//                
//                // untuk debug
//                //                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
//                //                        " = 504404240100955195";
//                
//                // sebelumnya
//                //                        " AND (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +")"+
//                //                        " OR NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)]+"))";
//                
//                //System.out.println("\tcheckEmployeeLateness : "+sql);
//                dbrs = DBHandler.execQueryResult(sql);
//                ResultSet rs = dbrs.getResultSet();
//                while (rs.next()) {
//                    // get data schedule untuk mencari emp symbol
//                    EmpSchedule empschedule = new EmpSchedule();
//                    try {
//                        empschedule = PstEmpSchedule.fetchExc(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
//                    } catch (Exception e) 
//                    {
//                         System.out.println("Exc when PstEmpSchedule.fetchExc() : " + e.toString());   
//                    }
//                    
//                    // check schedule type, category holiday or on schedule
//                    int scheduleCategory = rs.getInt(1);
//                    long employeeId = rs.getLong(3);
//                    
//                    int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
//                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
//                    if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE
//                    || scheduleCategory == PstScheduleCategory.CATEGORY_OFF
//                    || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
//                    || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
//                    || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)
//                    ) {
//                        
//                        // get symbol in
//                        ScheduleSymbol scheduleSymbol = SessEmployeeLateness.getDateRoster(presenceDate.getDate(), empschedule);
//                        if (scheduleCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
//                            if (rs.getDate(4) != null) {
//                                Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
//                                
//                                Date cx = scheduleSymbol.getTimeIn();
//                                if(cx.getHours()==0){
//                                    cx.setHours(23);
//                                    cx.setMinutes(59);
//                                    cx.setSeconds(59);
//                                    scheduleSymbol.setTimeIn(cx);
//                                }
//                                Date dt = new Date();
//                                dt.setDate(timeIn.getDate());
//                                dt.setMonth(timeIn.getMonth());
//                                dt.setYear(timeIn.getYear());
//                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
//                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
//                                dt.setSeconds(0);
//                                
//                                // waktu masuk
//                                Date dtx = new Date();
//                                dtx.setTime(timeIn.getTime());
//                                dtx.setSeconds(0);
//                                
//                                long iDuration = 0;
//                                long iDurationHour = 0;
//                                long iDurationMin = 0;
//                                iDuration = DateCalc.timeDifference(dtx, dt);
//                                /*
//                                iDuration = iDuration / 60000;
//                                iDurationHour = (iDuration - (iDuration % 60)) / 60;
//                                 
//                                iDurationMin = iDuration % 60;
//                                 */
//                                
//                                //if ((iDurationHour < 0) || (iDurationMin < 0)) {
//                                if (iDuration < 0) {
//                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
//                                }
//                                //System.out.println("iDurationHour : "+iDurationHour+" iDurationMin:"+iDurationMin+" intFirstStatus:"+intFirstStatus);
//                            }
//                            
//                            if (!(rs.getDate(6) != null)) {
//                                Date timeIn = DBHandler.convertDate(rs.getDate(6), rs.getTime(6));
//                                Date dt = new Date();
//                                long oidSymbol = rs.getLong(8);
//                                try{
//                                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSymbol);
//                                }catch(Exception e){}
//                                Date cx = scheduleSymbol.getTimeIn();
//                                if(cx.getHours()==0){
//                                    cx.setHours(23);
//                                    cx.setMinutes(59);
//                                    cx.setSeconds(59);
//                                    scheduleSymbol.setTimeIn(cx);
//                                }
//                                
//                                dt.setDate(timeIn.getDate());
//                                dt.setMonth(timeIn.getMonth());
//                                dt.setYear(timeIn.getYear());
//                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
//                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
//                                dt.setSeconds(0);
//                                
//                                // waktu masuk
//                                Date dtx = new Date();
//                                dtx.setTime(timeIn.getTime());
//                                dtx.setSeconds(0);
//                                
//                                long iDuration = 0;
//                                long iDurationHour = 0;
//                                long iDurationMin = 0;
//                                iDuration = DateCalc.timeDifference(dtx, dt);
//                                /*
//                                iDuration = iDuration / 60000;
//                                iDurationHour = (iDuration - (iDuration % 60)) / 60;
//                                 
//                                iDurationMin = iDuration % 60;
//                                if ((iDurationHour < 0) || (iDurationMin < 0)) {
//                                 */
//                                if (iDuration < 0) {
//                                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
//                                }
//                                // System.out.println("iDurationHour : "+iDurationHour+" iDurationMin:"+iDurationMin+" intFirstStatus:"+intFirstStatus);
//                            }
//                        }
//                        
//                        
//                        else {
//                            // jika "IN" bukan null
//                            if (rs.getDate(4) != null) {
//                                Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
//                                Date cx = scheduleSymbol.getTimeIn();
//                                if(cx.getHours()==0) {
//                                    cx.setHours(23);
//                                    cx.setMinutes(59);
//                                    cx.setSeconds(59);
//                                    scheduleSymbol.setTimeIn(cx);
//                                }
//                                
//                                //                                System.out.println("timeIn1 : " + timeIn);
//                                //                                System.out.println("cx : " + cx);
//                                
//                                
//                                Date dt = new Date();
//                                dt.setDate(timeIn.getDate());
//                                dt.setMonth(timeIn.getMonth());
//                                dt.setYear(timeIn.getYear());
//                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
//                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
//                                dt.setSeconds(0);
//                                //                                System.out.println("dtSchedule : " + dt);
//                                
//                                
//                                // waktu masuk
//                                Date dtx = new Date();
//                                dtx.setTime(timeIn.getTime());
//                                dtx.setSeconds(0);
//                                
//                                //                                System.out.println("dtActual : " + dtx);
//                                
//                                long iDuration = 0;
//                                long iDurationHour = 0;
//                                long iDurationMin = 0;
//                                iDuration = DateCalc.timeDifference(dtx, dt);
//                                /*
//                                System.out.println("iDuration before : " + iDuration);
//                                iDuration = iDuration / 60000;
//                                System.out.println("iDuration after : " + iDuration);
//                                iDurationHour = (iDuration - (iDuration % 60)) / 60;
//                                 
//                                iDurationMin = iDuration % 60;
//                                System.out.println("iDurationHour : " + iDurationHour);
//                                System.out.println("iDurationMin : " + iDurationMin);
//                                 */
//                                //                                if ((iDurationHour < 0) || (iDurationMin < 0)) {
//                                if (iDuration < 0) {
//                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
//                                }
//                                // System.out.println("iDurationHour : "+iDurationHour+" iDurationMin:"+iDurationMin+" intFirstStatus:"+intFirstStatus);
//                            }
//                        }
//                        
//                        //                        System.out.println("intFirstStatus : " + intFirstStatus);
//                        //                        System.out.println("intSecondStatus : " + intSecondStatus);
//                        
//                        // update status schedule
//                        if ((intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_LATE) || (intSecondStatus == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
//                            updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);
//                            //                            System.out.println("updatePresenceStatus");
//                        }
//                    }
//                }
//                //System.out.println("--- Process checkEmployeeAbsence already done ...");
//            } catch (Exception e) {
//                System.out.println("Exc checkEmployeeLateness : " + e.toString());
//            } finally {
//                DBResultSet.close(dbrs);
//            }
//        } else {
//            System.out.println("\tSelected data is null on checkEmployeeLateness");
//        }
//    }
    

    public void checkEmployeeLateness(Date presenceDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum) 
            //update by satrya 2012-08-01
            //public void checkEmployeeLateness(Date presenceDate, long lDepartmentOid, long lSectionOid, String smEployeeName) 
    {
        Vector result = new Vector(1, 1);  
        if (presenceDate != null) 
        {
            DBResultSet dbrs = null;
            long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
            int selectedIndex = presenceDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
            
              //priska menambahkan update ke reason 23-12-2014
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int intFirstReason =0;
            try{
                String sintFirstReason = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                intFirstReason = Integer.parseInt(sintFirstReason);
             }catch(Exception ex){
                 System.out.println("VALUE_B_REASON_SYMBOL NOT Be SET"+ex);
                 intFirstReason=0;
             }
            
            try 
            {
                String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                                               
                " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                " = " + periodId;
                
                if(lDepartmentOid != 0)
                {
                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +                               
                           " = " + lDepartmentOid;
                }                

                if(lSectionOid != 0)
                {
                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +                               
                           " = " + lSectionOid;
                }

                if(smEployeeName != null && smEployeeName.length() > 0)
                {
                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +                               
                           " LIKE \"%" + smEployeeName + "%\"";
                }                                             
                //update by satrya 2012-08-01
                if(sPayrollNum!=null && sPayrollNum.length() > 0)
                {
                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +                               
                           "= \"" + sPayrollNum + "\"";
                }
                
                // mencari data masing-masing first "in-out" atau second "in-out" yang tidak null
                sql = sql + " AND" +
                            " (" +
                            " (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] + "))" +
                            " OR (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + "))" +
                            " )" ;                
                
                //System.out.println(new LatenessAnalyser().getClass().getName()+".checkEmployeeLateness() sql : "+sql); 
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) 
                {
                    // get data schedule untuk mencari emp symbol
                    EmpSchedule empschedule = new EmpSchedule();
                    try 
                    {
                        empschedule = PstEmpSchedule.fetchExc(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                    }
                    catch (Exception e) 
                    {
                         System.out.println("Exc when PstEmpSchedule.fetchExc() : " + e.toString());   
                    }
                    
                    // check schedule type, category holiday or on schedule
                    int scheduleCategory = rs.getInt(1);
                    long employeeId = rs.getLong(3);
                    
                    int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    
                    if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_OFF
                            || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                            || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_UNPAID_LEAVE)){
                        
                        // get symbol in
                        ScheduleSymbol scheduleSymbol = SessEmployeeLateness.getDateRoster(presenceDate.getDate(), empschedule);
                        if (scheduleCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) 
                        {
                            if (rs.getDate(4) != null) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                                
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0)
                                {
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                Date dt = new Date();
                                dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);
                                
                                // waktu masuk
                                Date dtx = new Date();
                                dtx.setTime(timeIn.getTime());
                                dtx.setSeconds(0);
                                
                                long iDuration = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                                iDuration = DateCalc.timeDifference(dtx, dt);                                

                                if (iDuration < 0) 
                                {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }                                
                            }
                            
                            if (!(rs.getDate(6) != null)) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(6), rs.getTime(6));
                                Date dt = new Date();
                                long oidSymbol = rs.getLong(8);
                                try
                                {
                                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSymbol);
                                }
                                catch(Exception e)
                                {
                                    System.out.println("Exc when fetch Schedule Symbol : " + e.toString());
                                }
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0){
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                
                                dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);
                                
                                // waktu masuk
                                Date dtx = new Date();
                                dtx.setTime(timeIn.getTime());
                                dtx.setSeconds(0);
                                
                                long iDuration = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                                iDuration = DateCalc.timeDifference(dtx, dt);
                                if (iDuration < 0) 
                                {
                                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }                                
                            }
                        
                        }else{
                            
                            // jika "IN" bukan null
                            if (rs.getDate(4) != null) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0) 
                                {
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                
                                Date dt = new Date();
                                dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);
                                
                                // waktu masuk
                                Date dtx = new Date();
                                dtx.setTime(timeIn.getTime());
                                dtx.setSeconds(0);  
                                
                                long iDuration = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                                iDuration = DateCalc.timeDifference(dtx, dt);
                                if (iDuration < 0) 
                                {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }                                
                            }
                        }                        
                        
                        // update status schedule                        
                        updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);                                                  
                        
                    }else{
                        
                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        
                        updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);                                                  
                        
                    }
                }    
            }
            catch (Exception e) 
            {
                System.out.println("Exc checkEmployeeLateness : " + e.toString());
            }
            finally 
            {
                DBResultSet.close(dbrs);
            }
        }
        else 
        {
            System.out.println("\tSelected data is null on checkEmployeeLateness");
        }
    }
    
    
    
    /**
     * @AUTHOR : ROY ANDIKA
     * @Descrip: Untuk memproses keterlambatan employee
     */
    public static void EmployeeLateness(Date presenceDate, long employeeOid) 
    {
        //Vector result = new Vector(1, 1);  
        if (presenceDate != null) 
        {
            DBResultSet dbrs = null;
            long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
            int selectedIndex = presenceDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
            
              //priska menambahkan update ke reason 23-12-2014
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int intFirstReason =0;
            try{
                String sintFirstReason = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                intFirstReason = Integer.parseInt(sintFirstReason);
             }catch(Exception ex){
                 System.out.println("VALUE_B_REASON_SYMBOL NOT Be SET"+ex);
                 intFirstReason=0;
             }
            
            try 
            {
                String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                                               
                " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                " = " + periodId;
                
                if(employeeOid != 0)
                {
                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                               
                           " = " + employeeOid;
                }                
                                          
                
                /* Mencari data masing-masing first "in-out" atau second "in-out" yang tidak null */
                sql = sql + " AND" + " (" +
                            " (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] + "))" +
                            " OR (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + "))" +
                            " )" ;                
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                while (rs.next()) 
                {
                    /* get data schedule untuk mencari emp symbol */
                    EmpSchedule empschedule = new EmpSchedule();
                    try{
                        empschedule = PstEmpSchedule.fetchExc(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                    }catch (Exception e){
                         System.out.println("Exc when PstEmpSchedule.fetchExc() : " + e.toString());   
                    }
                    
                    /* Check schedule type, category holiday or on schedule */
                    int scheduleCategory = rs.getInt(1);
                    long employeeId = rs.getLong(3);
                    
                    int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    
                    if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_OFF
                            || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                            || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_UNPAID_LEAVE)){
                        
                        // get symbol in
                        ScheduleSymbol scheduleSymbol = SessEmployeeLateness.getDateRoster(presenceDate.getDate(), empschedule);
                        
                        if (scheduleCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) 
                        {
                            if (rs.getDate(4) != null) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                                
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0)
                                {
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                Date dt = new Date();
                                dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);
                                
                                // waktu masuk
                                Date dtx = new Date();
                                dtx.setTime(timeIn.getTime());
                                dtx.setSeconds(0);
                                
                                long iDuration = 0;
                               
                                iDuration = DateCalc.timeDifference(dtx, dt);                                

                                if (iDuration < 0) 
                                {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }                                
                            }
                            
                            if (!(rs.getDate(6) != null)) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(6), rs.getTime(6));
                                Date dt = new Date();
                                long oidSymbol = rs.getLong(8);
                                try
                                {
                                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSymbol);
                                }
                                catch(Exception e)
                                {
                                    System.out.println("Exc when fetch Schedule Symbol : " + e.toString());
                                }
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0){
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                
                                dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);
                                
                                // waktu masuk
                                Date dtx = new Date();
                                dtx.setTime(timeIn.getTime());
                                dtx.setSeconds(0);
                                
                                long iDuration = 0;
                               
                                iDuration = DateCalc.timeDifference(dtx, dt);
                                if (iDuration < 0) 
                                {
                                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }                                
                            }
                        
                        }else{
                            
                            // jika "IN" bukan null
                            if (rs.getDate(4) != null) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0) 
                                {
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                
                                Date dt = new Date();
                                dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);
                                
                                // waktu masuk
                                Date dtx = new Date();
                                dtx.setTime(timeIn.getTime());
                                dtx.setSeconds(0);  
                                
                                long iDuration = 0;
                                
                                iDuration = DateCalc.timeDifference(dtx, dt);
                                if (iDuration < 0) 
                                {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }                                
                            }
                        }                        
                        
                        // update status schedule                        
                        updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);                                                  
                        
                    }else{
                        
                        intFirstStatus  = PstEmpSchedule.STATUS_PRESENCE_OK;
                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        
                        updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);                                                  
                        
                    }
                }    
            }
            catch (Exception e){
                System.out.println("Exc check Employee Lateness : " + e.toString());
            }finally{
                DBResultSet.close(dbrs);
            }
        }
        else 
        {
            System.out.println("\tSelected data is null on checkEmployeeLateness");
        }
    }
    
    /**
     * @param presenceDate
     * @created by Edhy
     */    
    public static void processEmployeeLateness(Date presenceDate, long empId) 
    {
        Date dtTmp = new Date();
        Date dtNow = new Date(dtTmp.getYear(), dtTmp.getMonth(), dtTmp.getDate());            
        Date dtPresence = new Date(presenceDate.getYear(), presenceDate.getMonth(), presenceDate.getDate());            
        if(dtPresence.getTime() < dtNow.getTime())
        {

            Vector result = new Vector(1, 1);
            
            if (presenceDate != null) 
            {
                DBResultSet dbrs = null;
                long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);   
                int selectedIndex = presenceDate.getDate();
                int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
                int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
                                
                  //priska menambahkan update ke reason 23-12-2014
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int intFirstReason =0;
            try{
                String sintFirstReason = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                intFirstReason = Integer.parseInt(sintFirstReason);
             }catch(Exception ex){
                 System.out.println("VALUE_B_REASON_SYMBOL NOT Be SET"+ex);
                 intFirstReason=0;
             }
                
                try 
                {
                    String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                    " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                    " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                    " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                    " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND" +
                    " (" +
                    " (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] + ")" +
                    " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] + "))" +
                    " OR (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + ")" +
                    " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + "))" +
                    " )" +                
                    " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = " + empId;

//                    System.out.println("\tprocessEmployeeLateness : "+sql);
//                    System.out.println("DBHandler.execQueryResult(sql) lateness before : "+(new Date()).getTime());   
                    dbrs = DBHandler.execQueryResult(sql);
//                    System.out.println("DBHandler.execQueryResult(sql) lateness after : "+(new Date()).getTime());   
                    ResultSet rs = dbrs.getResultSet();
//                    System.out.println("looping lateness before : "+(new Date()).getTime());   
                    while (rs.next()) 
                    {
                        // get data schedule untuk mencari emp symbol
                        EmpSchedule empschedule = new EmpSchedule();
                        try 
                        {
                            empschedule = PstEmpSchedule.fetchExc(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                        } 
                        catch (Exception e) 
                        {
                            System.out.println("Exc when fetchExc : " + e.toString());    
                        }

                        // check schedule type, category holiday or on schedule
                        int scheduleCategory = rs.getInt(1);
                        long employeeId = rs.getLong(3);

                        int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE
                        || scheduleCategory == PstScheduleCategory.CATEGORY_OFF
                        || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                        || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                        || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)
                        ) 
                        {                        
                            // get symbol in
                            ScheduleSymbol scheduleSymbol = SessEmployeeLateness.getDateRoster(presenceDate.getDate(), empschedule);
                            if (scheduleCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) 
                            {
                                if (rs.getDate(4) != null) 
                                {
                                    Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));

                                    Date cx = scheduleSymbol.getTimeIn();
                                    if(cx.getHours()==0)
                                    {
                                        cx.setHours(23);
                                        cx.setMinutes(59);
                                        cx.setSeconds(59);
                                        scheduleSymbol.setTimeIn(cx);
                                    }
                                    Date dt = new Date();
                                    dt.setDate(timeIn.getDate());
                                    dt.setMonth(timeIn.getMonth());
                                    dt.setYear(timeIn.getYear());
                                    dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dt.setSeconds(0);

                                    // waktu masuk
                                    Date dtx = new Date();
                                    dtx.setTime(timeIn.getTime());
                                    dtx.setSeconds(0);

                                    long iDuration = 0;
                                    long iDurationHour = 0;
                                    long iDurationMin = 0;
                                    iDuration = DateCalc.timeDifference(dtx, dt);                                

                                    if (iDuration < 0) 
                                    {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                    }
                                }

                                if (!(rs.getDate(6) != null)) 
                                {
                                    Date timeIn = DBHandler.convertDate(rs.getDate(6), rs.getTime(6));
                                    Date dt = new Date();
                                    long oidSymbol = rs.getLong(8);
                                    try
                                    {
                                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidSymbol);
                                    }
                                    catch(Exception e)
                                    {
                                    }

                                    Date cx = scheduleSymbol.getTimeIn();
                                    if(cx.getHours()==0)
                                    {
                                        cx.setHours(23);
                                        cx.setMinutes(59);
                                        cx.setSeconds(59);
                                        scheduleSymbol.setTimeIn(cx);
                                    }

                                    dt.setDate(timeIn.getDate());
                                    dt.setMonth(timeIn.getMonth());
                                    dt.setYear(timeIn.getYear());
                                    dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dt.setSeconds(0);

                                    // waktu masuk
                                    Date dtx = new Date();
                                    dtx.setTime(timeIn.getTime());
                                    dtx.setSeconds(0);

                                    long iDuration = 0;
                                    long iDurationHour = 0;
                                    long iDurationMin = 0;
                                    iDuration = DateCalc.timeDifference(dtx, dt);

                                    if (iDuration < 0) 
                                    {
                                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                    }                                
                                }
                            }


                            else 
                            {
                                // jika "IN" bukan null
                                if (rs.getDate(4) != null) 
                                {
                                    Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                                    Date cx = scheduleSymbol.getTimeIn();
                                    if(cx.getHours()==0) 
                                    {
                                        cx.setHours(23);
                                        cx.setMinutes(59);
                                        cx.setSeconds(59);
                                        scheduleSymbol.setTimeIn(cx);
                                    }

                                    Date dt = new Date();
                                    dt.setDate(timeIn.getDate());
                                    dt.setMonth(timeIn.getMonth());
                                    dt.setYear(timeIn.getYear());
                                    dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dt.setSeconds(0);

                                    // waktu masuk
                                    Date dtx = new Date();
                                    dtx.setTime(timeIn.getTime());
                                    dtx.setSeconds(0);                                

                                    long iDuration = 0;
                                    long iDurationHour = 0;
                                    long iDurationMin = 0;
                                    iDuration = DateCalc.timeDifference(dtx, dt);

                                    if (iDuration < 0) 
                                    {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                    }                                
                                }
                            }

                            if ((intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_LATE) || (intSecondStatus == PstEmpSchedule.STATUS_PRESENCE_LATE)) 
                            {
                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);                        
                                //System.out.println("\t.::updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);");
                            }
                            else  
                            {
                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, firstFieldIndexReason, intFirstReason);                        
                                //System.out.println("\t.::updatePresenceStatus OKOKOKOK");                                
                            }
                        }
                    }
                //System.out.println("looping lateness after : "+(new Date()).getTime());   
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc checkEmployeeLateness : " + e.toString());
                } 
                finally 
                {
                    DBResultSet.close(dbrs);
                }
            } 
            else 
            {
                System.out.println("\tSelected data is null on checkEmployeeLateness");
            }
        }
    }

    
    public static int updatePresenceStatus(long periodId, long employeeId, int idxFieldNameFirst, int statusFirst, int idxFieldNameSecond, int statusSecond,int idxFieldNameFirstReason ,int reasonfirst) {
        int success=0;
        
        int intabsen =-1;
            try{
                String sintabsen = PstSystemProperty.getValueByName("VALUE_ABSENSE"); 
                intabsen = Integer.parseInt(sintabsen);
             }catch(Exception ex){
                 System.out.println("VALUE_ABSENSE NOT Be SET"+ex);
                 intabsen=-1;
             }
          int intreasonB =-1;
            try{
                String sintreasonB = PstSystemProperty.getValueByName("VALUE_REASON_B"); 
                intreasonB = Integer.parseInt(sintreasonB);
             }catch(Exception ex){
                 System.out.println("VALUE_REASON B NOT Be SET"+ex);
                 intreasonB=-1;
             }
            
        long cekreason = PstEmpSchedule.getReasonValue(periodId, employeeId, idxFieldNameFirstReason);  
            
        try {
            
            String sql = "UPDATE " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE +
            " SET " + PstEmpSchedule.fieldNames[idxFieldNameFirst] + " = " + statusFirst +
            ", " + PstEmpSchedule.fieldNames[idxFieldNameSecond] + " = " + statusSecond ;
                    
            //if (statusFirst != 0 && statusFirst > 0 && (statusFirst==intabsen || cekreason == intreasonB ) ){
            //sql = sql + ", " + PstEmpSchedule.fieldNames[idxFieldNameFirstReason] + " = " + reasonfirst ;
            //} 
              
            sql = sql + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
            " = " + periodId +
            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
            " = " + employeeId;
            
            //System.out.println("sql : " + sql);
            success = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("\tExc updatePresenceStatus : " + e.toString());
        } finally {
            //System.out.println("\tFinal updatePresenceStatus");
            //DBResultSet.close(dbrs);
            return success;
        }
    }
    
    
    //public static int updatePresenceReason(long periodId, long employeeId, int idxFieldNameFirst, int statusFirst, int idxFieldNameSecond, int statusSecond,int idxFieldNameFirstReason ,int reasonfirst) {
    public static int updatePresenceReason(long periodId, long employeeId,int idxFieldNameFirstReason ,int reasonfirst) {
    
        int success=0;
           
        try {
            
            String sql = "UPDATE " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE +
            " SET " + PstEmpSchedule.fieldNames[idxFieldNameFirstReason] + " = " + reasonfirst ;
            
              
            sql = sql + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
            " = " + periodId +
            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
            " = " + employeeId;
            success = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("\tExc updatePresenceStatus : " + e.toString());
        } finally {
            return success;
        }
    }
    
    public static void main(String args[]) {
        
        LatenessAnalyser ll = new LatenessAnalyser();
        
        Date dt = new Date();
        dt.setDate(2);
        dt.setMonth(5);
        ll.setDate(dt);
        ll.startService();
        
    }
    
    
}
