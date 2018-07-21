/*
 * absenceAnalyser.java
 *
 * Created on June 7, 2004, 9:33 AM
 */
package com.dimata.harisma.utility.service.presence;

// package core java
import java.util.Vector;
import java.util.Date;
import java.sql.*;

// package qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

// package harisma
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.session.attendance.SessDayOfPayment;
import com.dimata.system.entity.PstSystemProperty;

/**
 * This class is a service which will check presence per employee per day
 * and update flag into ABSENCE if actually that employee absence on selected day
 * in employee_schedule table (HR_EMP_SCHEDULE)
 * @author  gedhy
 */

public class AbsenceAnalyser {

    static Date dtStartSvc; // untuk start pertama service
    static Date dtHistory; // untuk start pertama service  
    private static boolean running = false; // status service  

    /**
     * @return the running
     */
    public static boolean isRunning() {
        return running;
    }

    /**
     * @param aRunning the running to set
     */
    public static void setRunning(boolean aRunning) {
        running = aRunning;
    }

    public Date getDate(){
        return dtStartSvc;
    }

    public void setDate(Date dt) {
        dtHistory = dt;
        dtStartSvc = dt;
    }

    public Date getDateHistory() {
        return dtHistory;
    }

    public boolean getStatus() {
        return isRunning();
    }

    public AbsenceAnalyser() {
    }

    public synchronized void startService(){
        if (!isRunning()) {
            System.out.println(".:: AbsenceAnalyser service started ... !!!");
            try {
                setRunning(true);
                Thread thr = new Thread(new ServiceAbsence());
                thr.setDaemon(false);
                thr.start();

            } catch (Exception e) {
                System.out.println(">>> Exc when AbsenceAnalyser start ... !!!");
            }
        }
    }

    public synchronized void stopService() {
        setRunning(false);
        System.out.println(".:: Absence Analyser service stoped ... !!!");
    }

    /** 
     * check/update status presence depend on actual presence  
     * @param presenceDate
     * @created by Edhy
     */
    public static void checkEmployeeAbsences(Date presenceDate, long lDepartmentOid, long lSectionOid, String smEployeeName){
        
        Vector result = new Vector(1, 1);
        
        if (presenceDate != null){
            
            DBResultSet dbrs = null;
            Date dtTommorow = new Date(presenceDate.getYear(), presenceDate.getMonth(), presenceDate.getDate() + 1);
            long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
            String strDate = "\"" + Formater.formatDate(presenceDate, "yyyy-MM-dd") + "\"";
            int selectedIndex = presenceDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
            
            try {
                
                String sql = "SELECT SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                        " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                        " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                        " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " LEFT JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                        " = " + periodId;

                if (lDepartmentOid != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                            " = " + lDepartmentOid;
                }

                if (lSectionOid != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                            " = " + lSectionOid;
                }

                if (smEployeeName != null && smEployeeName.length() > 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                            " LIKE \"%" + smEployeeName + "%\"";
                }

                //System.out.println(new AbsenceAnalyser().getClass().getName() + ".checkEmployeeAbsence() sql : " + sql);
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                boolean presenceAvailable = false;

                while (rs.next()) {
                    
                    presenceAvailable = true;

                    // check schedule type, category holiday or on schedule                     
                    long employeeId = rs.getLong(1);
                    int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int schld1stCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_FIRST_SCHEDULE, employeeId, dtTommorow);
                    int schld2ndCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_SECOND_SCHEDULE, employeeId, dtTommorow);

                    // if schedule category IN (available on the office) 
                    if (!(schld1stCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT //       || schld1stCategory==PstScheduleCategory.CATEGORY_OFF //SEBELUMNYA ENABLED
                            //       || schld1stCategory==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT //SEBELUMNYA ENABLED
                            //       || schld1stCategory==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE //SEBELUMNYA ENABLED
                            //       || schld1stCategory==PstScheduleCategory.CATEGORY_LONG_LEAVE //SEBELUMNYA ENABLED   
                            )) {

                        // check if schedule first is split shift
                        if (schld1stCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                            // if one or both (in or out) of first schedule is null
                            if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                            }
                        }

                        if (schld2ndCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                            // if one or both (in or out) of second schedule is null
                            if (!(rs.getDate(4) != null && rs.getDate(5) != null)) {
                                intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                            }
                        }

                        // khusus tambahan intimas 
                        // updated by Yunny
                        //( jika ambil cuti langsug ngupdate status di empschedule)
                        if (schld1stCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE){
                            if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                //System.out.println("intFirstStatus 5 "+intFirstStatus);
                                // update reason dengan 3 ( Cuti Melahirkan ) karena cuti juga dianggap absen
                                updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 3);
                            }
                        }
                        // jika libur saat libur schedulenya ( day_off) langusng link ke working schedule
                        if (schld1stCategory == PstScheduleCategory.CATEGORY_OFF) {
                            if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                // update reason dengan 16 ( Libur Jadwal) karena libur juga dianggap absen
                                updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 16);
                            }
                        }
                        // jika libur saat libur schedulenya ( LL) langusng link ke working schedule
                        if (schld1stCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                            if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                // update reason dengan 5 ( Cuti Melahirkan) karena cuti melahirkan juga dianggap absen
                                updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 5);
                            }
                        }
                        // jika libur saat libur schedulenya ( PH) langusng link ke working schedule
                        if (schld1stCategory == PstScheduleCategory.CATEGORY_ABSENCE){
                            if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                // update reason dengan 17 ( Public Holiday) karena Public Holiday juga dianggap absen
                                updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 17);
                            }
                        } //***********************************************************************************
                        // check if schedule is regular without schedule second
                        else {
                            // if one or both (in or out) of first first if null
                            if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                            }

                            // if both (in or out) of first not null
                            if ((rs.getDate(2) != null && rs.getDate(3) != null)) {
                                //intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;                                    
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            }

                        }

                        // update status presence                             
                        updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);
                    }

                //------------- PROSES UPDATE STATUS LEAVE (DP, AL dan LL) numpang start ----------    

                //long empScheduleId = rs.getLong(6);                        

                // if schedule category is Dp then process "DP taken process"

                //if( schld1stCategory==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT )
                //{                        
                // System.out.println(".::Process DP taken ...");

                // update data pada stock Dp and insert data di DP stock taken                          
                //PstDpStockManagement pstDpStockManagement =  new PstDpStockManagement();                        
                //pstDpStockManagement.updateDpStockManagementByPresence(presenceDate, employeeId);                                                                        
                // }

                // if schedule category is Al then process "AL taken process"
                //if( schld1stCategory==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE )
                //{
                // System.out.println(".::Process AL taken ...");

                // update data pada stock Al and insert data di AL taken 
                //PstAlStockManagement pstAlStockManagement =  new PstAlStockManagement();
                //pstAlStockManagement.updateAlStockManagementByPresence(presenceDate, employeeId);                          
                //}

                // if schedule category is Ll then process "LL taken process"
                //if( schld1stCategory==PstScheduleCategory.CATEGORY_LONG_LEAVE )
                //{  
                //  System.out.println(".::Process LL taken ...");

                // update data pada stock LL and insert data di LL taken
                // PstLLStockManagement pstLlStockManagement =  new PstLLStockManagement();
                // pstLlStockManagement.updateLLStockManagementPresence(presenceDate, employeeId);                          
                //}
                //------------- PROSES UPDATE STATUS LEAVE (DP, AL dan LL) numpang end ----------                    

                }
            } catch (Exception e) {
                System.out.println("Exc checkEmployeeAbsence : " + e.toString());
            } finally {
                DBResultSet.close(dbrs);
            }
        } else {
            System.out.println("\tSelected data is null on listEmployeePresence");
        }
    }

    /**
     * @Author Roy Andika
     * @param presenceDate
     * @param lDepartmentOid
     * @param lSectionOid
     * @param smEployeeName
     */
    public static void checkEmployeeAbsence(Date presenceDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrolNum){
//update by satrya 2012-08-01
        // public static void checkEmployeeAbsence(Date presenceDate, long lDepartmentOid, long lSectionOid, String smEployeeName){

        if (presenceDate != null) {
            DBResultSet dbrs = null;

            int oidAbsenceReportOff = 0;
            
            try {
                /* cek system property ABSENCE_REPORT_OFF */
                oidAbsenceReportOff = Integer.parseInt(PstSystemProperty.getValueByName("ABSENCE_REPORT_OFF"));

            } catch (Exception e) {
                oidAbsenceReportOff = 0;    /* Default = 0*/
                System.out.println("EXCEPTION " + e.toString());
            }

            Date dtTommorow = new Date(presenceDate.getYear(), presenceDate.getMonth(), presenceDate.getDate() + 1);
            long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
            int selectedIndex = presenceDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);

            try {
                String sql = "SELECT SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                        " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                        " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                        " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " LEFT JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                        " = " + periodId;

                if (lDepartmentOid != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                            " = " + lDepartmentOid;
                }

                if (lSectionOid != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                            " = " + lSectionOid;
                }

                if (smEployeeName != null && smEployeeName.length() > 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                            " LIKE \"%" + smEployeeName + "%\"";
                }
                //update by satrya 2012-08-01
                if (sPayrolNum!=null && sPayrolNum.length() > 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                            " = \"" + sPayrolNum + "\"";
                }

               // System.out.println(new AbsenceAnalyser().getClass().getName() + ".checkEmployeeAbsence() sql : " + sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                //while (rs.next() && running ){
                //update by satrya 20120831
                while (rs.next() && isRunning() ){
                    // while (rs.next() && isRunning() ){


                    /* Check schedule type, category holiday or on schedule  */
                    long employeeId = rs.getLong(1);
                    int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int schld1stCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_FIRST_SCHEDULE, employeeId, dtTommorow);
                    int schld2ndCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_SECOND_SCHEDULE, employeeId, dtTommorow);

                    switch (oidAbsenceReportOff){

                        /* Absence report include off schedule */
                        case PstSystemProperty.ABSENCE_INCLUDE_OFF_SCHEDULE:

                            if (!(schld1stCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)){

                                /* Check if schedule first is split shift */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT){
                                    /* if one or both (in or out) of first schedule is null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)){
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                if (schld2ndCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                                    /* if one or both (in or out) of second schedule is null */
                                    if (!(rs.getDate(4) != null && rs.getDate(5) != null)) {
                                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                /* Jika ambil cuti langsug ngupdate status di empschedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        /* update reason dengan 3 ( Cuti Murni ) karena cuti juga dianggap absen */
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 3);

                                    }
                                }

                                /* Jika libur saat libur schedulenya ( day_off ) langusng link ke working schedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_OFF) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        /* update reason dengan 16 ( Libur Jadwal) karena libur juga dianggap absen */
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 16);
                                    }
                                }

                                /* jika libur saat libur schedulenya ( LL ) langusng link ke working schedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        // update reason dengan 5 ( Cuti Melahirkan) karena cuti melahirkan juga dianggap absen
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 5);
                                    }
                                }

                                /* Jika libur saat libur schedulenya ( PH ) langusng link ke working schedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_ABSENCE) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        /*update reason dengan 17 ( Public Holiday) karena Public Holiday juga dianggap absen */
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 17);
                                    }

                                } else { /* check if schedule is regular without schedule second */
                                    /* if one or both (in or out) of first if null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }

                                    /* if both (in or out) of first not null */
                                    if ((rs.getDate(2) != null && rs.getDate(3) != null)) {

                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }

                                }

                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);

                            }

                            break;

                        default:
                            if (!(schld1stCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_OFF || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_UNPAID_LEAVE || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE ||
                                    schld1stCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)) {

                                /* Check if schedule first is split shift */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                                    /* if one or both (in or out) of first schedule is null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                if (schld2ndCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                                    /* If one or both (in or out) of second schedule is null */
                                    if (!(rs.getDate(4) != null && rs.getDate(5) != null)) {
                                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                /*Schedule hadir */
                                if (schld1stCategory != PstScheduleCategory.CATEGORY_ABSENCE) {

                                    /* if one or both (in or out) of first first if null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }

                                    /* if both (in or out) of first not null */
                                    if ((rs.getDate(2) != null && rs.getDate(3) != null)) {

                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;

                                    }

                                }

                                /* Update status presence */
                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);

                            }else{
                                
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;              
                                    
                                intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    
                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);                           
                                
                            }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exc checkEmployeeAbsence : " + e.toString());
            } finally {
                DBResultSet.close(dbrs);
            }
        } else {
            System.out.println("\tSelected data is null on listEmployeePresence");
        }
    }
    
    
    /**
     * @Author Roy Andika
     * @param presenceDate
     * @param lDepartmentOid
     * @param lSectionOid
     * @param smEployeeName
     */
    public static void checkEmployeeAbsenceAutomatic(Date presenceDate, long empId) {
    
        if (presenceDate != null) {
            DBResultSet dbrs = null;

            int oidAbsenceReportOff = 0;
            
            try {
                
                oidAbsenceReportOff = Integer.parseInt(PstSystemProperty.getValueByName("ABSENCE_REPORT_OFF"));

            } catch (Exception e) {

                oidAbsenceReportOff = 0;    /* Default = 0*/
                System.out.println("EXCEPTION SYS PROP 'ABSENCE_REPORT_OFF' : " + e.toString());

            }

            Date dtTommorow = new Date(presenceDate.getYear(), presenceDate.getMonth(), presenceDate.getDate() + 1);
            
            long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
            int selectedIndex = presenceDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);

            try {
                String sql = "SELECT SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                        " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                        " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                        " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " LEFT JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                        " = " + periodId;

                if (empId != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                            " = " + empId;
                }

                //System.out.println(new AbsenceAnalyser().getClass().getName() + "Check Employee Absence sql : " + sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {

                    /* Check schedule type, category holiday or on schedule  */
                    long employeeId = rs.getLong(1);
                    int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int schld1stCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_FIRST_SCHEDULE, employeeId, dtTommorow);
                    int schld2ndCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_SECOND_SCHEDULE, employeeId, dtTommorow);

                    switch (oidAbsenceReportOff){

                        /* Absence report include off schedule */
                        case PstSystemProperty.ABSENCE_INCLUDE_OFF_SCHEDULE:

                            if (!(schld1stCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)){

                                /* Check if schedule first is split shift */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT){
                                    /* if one or both (in or out) of first schedule is null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                if (schld2ndCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                                    /* if one or both (in or out) of second schedule is null */
                                    if (!(rs.getDate(4) != null && rs.getDate(5) != null)) {
                                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                /* Jika ambil cuti langsug ngupdate status di empschedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        /* update reason dengan 3 ( Cuti Melahirkan ) karena cuti juga dianggap absen */
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 3);

                                    }
                                }

                                /* Jika libur saat libur schedulenya ( day_off ) langusng link ke working schedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_OFF) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        /* update reason dengan 16 ( Libur Jadwal) karena libur juga dianggap absen */
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 16);
                                    }
                                }

                                /* jika libur saat libur schedulenya ( LL ) langusng link ke working schedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        // update reason dengan 5 ( Cuti Melahirkan) karena cuti melahirkan juga dianggap absen
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 5);
                                    }
                                }

                                /* Jika libur saat libur schedulenya ( PH ) langusng link ke working schedule */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_ABSENCE) {
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        /*update reason dengan 17 ( Public Holiday) karena Public Holiday juga dianggap absen */
                                        updatePresenceReason(periodId, employeeId, firstFieldIndexReason, 17);
                                    }

                                } else { /* check if schedule is regular without schedule second */
                                    /* if one or both (in or out) of first if null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }

                                    /* if both (in or out) of first not null */
                                    if ((rs.getDate(2) != null && rs.getDate(3) != null)) {

                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }

                                }

                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);

                            }

                            break;

                        default:
                            
                            if (!(schld1stCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_OFF ||                                      
                                    schld1stCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE || 
                                    schld1stCategory == PstScheduleCategory.CATEGORY_UNPAID_LEAVE ||
                                    schld1stCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)) {

                                /* Check if schedule first is split shift */
                                if (schld1stCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                                    /* if one or both (in or out) of first schedule is null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                if (schld2ndCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT){
                                    /* If one or both (in or out) of second schedule is null */
                                    if (!(rs.getDate(4) != null && rs.getDate(5) != null)) {
                                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }

                                /*Schedule hadir */
                                if (schld1stCategory != PstScheduleCategory.CATEGORY_ABSENCE){

                                    /* if one or both (in or out) of first first if null */
                                    if (!(rs.getDate(2) != null && rs.getDate(3) != null)) {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }

                                    /* if both (in or out) of first not null */
                                    if ((rs.getDate(2) != null && rs.getDate(3) != null)) {

                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;

                                    }
                                }

                                /* Update status presence */
                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);

                            }else{
                                
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;              
                                    
                                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    
                                    updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);
                            }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception check Employee Absence : " + e.toString());
            } finally {
                DBResultSet.close(dbrs);
            }
        } else {
            System.out.println("\tSelected data is null on listEmployeePresence");
        }
    }
    
    

    /** 
     * check/update status presence depend on actual presence
     * @param presenceDate
     * @created by edhy
     */
    public static void processEmployeeAbsence(Date presenceDate, long empId){
        Date dtTmp = new Date();
        Date dtNow = new Date(dtTmp.getYear(), dtTmp.getMonth(), dtTmp.getDate());
        Date dtPresence = new Date(presenceDate.getYear(), presenceDate.getMonth(), presenceDate.getDate());
        if (dtPresence.getTime() < dtNow.getTime()) {

            Vector result = new Vector(1, 1);
            if (presenceDate != null){
                DBResultSet dbrs = null;
                long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
                String strDate = "\"" + Formater.formatDate(presenceDate, "yyyy-MM-dd") + "\"";
                int selectedIndex = presenceDate.getDate();
                int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
                int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
                
                try {
                    
                    String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                            " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                            " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                            " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                            " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                            " LEFT JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                            " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                            " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                            " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                            " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                            " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                            " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                            " = " + periodId +
                            " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                            " = " + empId;

                    //System.out.println("\tprocessEmployeeAbsence : "+sql);   
                    //System.out.println("DBHandler.execQueryResult(sql) processEmpAbsence before : "+(new Date()).getTime());   
                    dbrs = DBHandler.execQueryResult(sql);
                    //System.out.println("DBHandler.execQueryResult(sql) processEmpAbsence after : "+(new Date()).getTime());   
                    ResultSet rs = dbrs.getResultSet();
                    //System.out.println("looping absence before : "+(new Date()).getTime());   
                    while (rs.next()) {
                        // check schedule type, category holiday or on schedule                    
                        int scheduleCategory = rs.getInt(1);
                        long employeeId = rs.getLong(2);
                        int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;

                        // if schedule category aren't CATEGORY_ABSENCE or CATEGORY_OFF
                        if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE || scheduleCategory == PstScheduleCategory.CATEGORY_OFF || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)) {
                            // check if schedule is split shift
                            if (scheduleCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                                // if one or both (in or out) of first schedule is null
                                if (!(rs.getDate(3) != null && rs.getDate(4) != null)) {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                }

                                // if one or both (in or out) of second schedule is null
                                if (!(rs.getDate(5) != null && rs.getDate(6) != null)) {
                                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                }

                            } // check if schedule is not split shift
                            else {
                                // if one or both (in or out) of first first if null
                                if (!(rs.getDate(3) != null && rs.getDate(4) != null)) {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                }
                            }

                            // update status presence       
                            if (!(intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_OK && intSecondStatus == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);
                            } else {
                                updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK);
                            }
                        }
                    }
//                    System.out.println("looping absence after : "+(new Date()).getTime());   
                } catch (Exception e) {
                    System.out.println("Exc checkEmployeeAbsence : " + e.toString());
                } finally {
                    DBResultSet.close(dbrs);
                }
            } else {
                System.out.println("\tSelected data is null on listEmployeePresence");
            }
        }
    }

    /**
     * get field index that will update
     * @param periodId
     * @param employeeId
     * @param idxFieldNameFirst
     * @param statusFirst
     * @param idxFieldNameSecond
     * @param statusSecond
     * @created by Edhy
     */
    public static void updatePresenceStatus(long periodId, long employeeId, int idxFieldNameFirst, int statusFirst, int idxFieldNameSecond, int statusSecond) {
        try {
            
            String sql = "UPDATE " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE +
                    " SET " + PstEmpSchedule.fieldNames[idxFieldNameFirst] + " = " + statusFirst +
                    ", " + PstEmpSchedule.fieldNames[idxFieldNameSecond] + " = " + statusSecond +
                    " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = " + employeeId;

            //System.out.println("\tUpdate Presence Status : " + sql);
            int result = DBHandler.execUpdate(sql);
            
        } catch (Exception e) {
            System.out.println("\tExc updatePresenceStatus : " + e.toString());
        } finally {
        //System.out.println("\tFinal updatePresenceStatus");
        }
    }

    /**
     * get field index that will update
     * @param periodId
     * @param employeeId
     * @param idxFieldNameFirst
     * @param statusFirst
     * @param idxFieldNameSecond
     * @param statusSecond
     * @created by Yunny
     * khusus untuk intimas
     */
    public static void updatePresenceReason(long periodId, long employeeId, int idxFieldNameFirst, int reasonFirst) {
        try {
            String sql = "UPDATE " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE +
                    " SET " + PstEmpSchedule.fieldNames[idxFieldNameFirst] + " = " + reasonFirst +
                    " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = " + employeeId;

            System.out.println("\tupdatePresenceReasonfhdjhfj : " + sql);
            int result = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("\tExc updatePresenceStatus : " + e.toString());
        } finally {
            //System.out.println("\tFinal updatePresenceStatus");
        }
    }

    public static void main(String args[]) {
        Date presenceDate = new Date(105, 3, 19);
        System.out.println("[AbsenceAnalyser] starting ...");
        //checkEmployeeAbsence(presenceDate, 504404240100922686L, 504404240100922716L, "KOMANG ROY MUDARSA");
        checkEmployeeAbsence(presenceDate, 504404240100922686L, 504404240100922265L, "KOMANG ROY MUDARSA","10010");
        System.out.println("[AbsenceAnalyser] stopped ...");

    /*
    Date newDate = new Date(105,3,19);        
    Date tmpDate = new Date();
    java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
    cal.setTime(newDate);
    int maxDate = cal.getActualMaximum(cal.DAY_OF_MONTH);
    Date nowDate = new Date(tmpDate.getYear(), tmpDate.getMonth(), tmpDate.getDate());
    boolean running = true;
    int i = -1;
    while(running)
    {
    i++;
    Date itrDate = new Date(newDate.getYear(), newDate.getMonth(), newDate.getDate()+i);
    if (itrDate.getTime() <= nowDate.getTime())     
    {           
    System.out.println("--- process on : "+itrDate);                
    checkEmployeeAbsence(itrDate);
    }
    else
    {
    running = false;
    }
    }        
    System.out.println("Process absence taken Dp finish ...");        
     */
    }
}
