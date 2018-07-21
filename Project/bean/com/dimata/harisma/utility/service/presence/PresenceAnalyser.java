/*
 * PresenceAnalyser.java
 *
 * Created on October 5, 2004, 9:04 AM
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
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.leave.PstSpecialUnpaidLeaveTaken;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.session.attendance.OutPermision;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.harisma.session.lateness.SessEmployeeLateness;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.utility.machine.AnalyseStatusDataPresence;
import com.dimata.harisma.utility.machine.TransManagerAssistant;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author  gedhy
 */
public class PresenceAnalyser {
    
    /** Creates a new instance of PresenceAnalyser */ 
    public PresenceAnalyser() {
    }
    
    static boolean running = false; // status service

    public boolean getStatus()
    {
        return running;
    }    
            private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
    public synchronized void startService() 
    {
        if (!running) 
        {
            System.out.println(".:: Presence Analyser service started ... !!!");   
            try 
            {
                running = true;
                Thread thr = new Thread(new ServicePresence());
                thr.setDaemon(false);
                thr.start();
            }
            catch (Exception e) 
            {
                System.out.println(">>> Exc when PresenceAnalyser start ... !!!");
            }
        }
    }

    public synchronized void stopService() 
    {
        running = false;
        System.out.println(".:: PresenceAnalyser service stoped ... !!!");
    }
    
    
    
    /** 
     * Analyze presence data followed by check Absence and Lateness status
     * which source data come from objEmpScheduleHistory and objEmpSchedule     
     * @created by Edhy
     */              
    public void analyzeEmpPresenceData(long lDepartmentOid, long lSectionOid, String sEmployeeName, String sPayrolNum)
    //update by satrya 2012-08-01
            //public void analyzeEmpPresenceData(long lDepartmentOid, long lSectionOid, String sEmployeeName)
    {
        /* Get list all empScheduleHistory record on database */        
        Vector vectEmpScheduleHistory = PstEmpScheduleHistory.getListEmpScheduleHistory(lDepartmentOid, lSectionOid, sEmployeeName, sPayrolNum);
        //date by satrya 2012-08-01
        //Vector vectEmpScheduleHistory = PstEmpScheduleHistory.getListEmpScheduleHistory(lDepartmentOid, lSectionOid, sEmployeeName);
        
        
        if(vectEmpScheduleHistory!=null && vectEmpScheduleHistory.size()>0)
        {
            int intMaxEmpSchldHistory = vectEmpScheduleHistory.size();
            
            PstEmpScheduleHistory objPstEmpScheduleHistory = new PstEmpScheduleHistory();
            
            // iterate to process analyse PRESENCE followed by ABSENCE and LATENESS
            
            for(int i=0; i<intMaxEmpSchldHistory; i++)
            {
                if (!running){ 
                    break;
                }
                EmpScheduleHistory objEmpScheduleHistory = new EmpScheduleHistory();

                try{
                    objEmpScheduleHistory = (EmpScheduleHistory) vectEmpScheduleHistory.get(i);
                }catch(Exception E){
                    System.out.println("Exc when loop i = " + i +" : "+ E.toString());
                    continue;
                }

                //System.out.print("<START> Employee Id " + objEmpScheduleHistory.getEmployeeId() + " : on schedule id " + objEmpScheduleHistory.getOID());

                EmpSchedule objEmpScheduleOld = new EmpSchedule();
                try{
                    //Generate objEmpScheduleOld which source data come from objEmpSchdeduleHistory
                    objEmpScheduleOld = PstEmpSchedule.generateObjEmpSchedule(objEmpScheduleHistory);
                }catch(Exception E){
                    System.out.println("Exc when fetch Employee Id " + objEmpScheduleHistory.getEmployeeId() + " : on schedule id " + objEmpScheduleHistory.getOID() +" : "+ E.toString());
                    continue;
                }
                
                // generate or fetch objEmpSchedule (current schedule) which will compare with this history one
                EmpSchedule objEmpSchedule = new EmpSchedule();                
                
                try
                {
                    objEmpSchedule = PstEmpSchedule.fetchExc(objEmpScheduleHistory.getEmpScheduleOrgId());
                    
                }catch(Exception e){
                    System.out.println("Exc when fetch Employee Id " + objEmpScheduleOld.getEmployeeId() + " : on schedule id " + objEmpScheduleOld.getOID() +" : "+ e.toString());
                    continue;
                }
                
                //process analyze Presence, Absence and Lateness
                try{
                    PstPresence.importPresenceTriggerByEmpSchedule(objEmpScheduleOld, objEmpSchedule);
                }catch(Exception E){
                    System.out.println("Exc when importPresenceTriggerByEmpSchedule Employee Id " + objEmpScheduleOld.getEmployeeId() + " : on schedule id " + objEmpScheduleOld.getOID() +" : "+ E.toString());
                    continue;
                }
                //Delete empScheduleHistory that already analyze                
                try
                {   
                    objPstEmpScheduleHistory.deleteExc(objEmpScheduleHistory.getOID());
                    
                }catch(Exception e){ 
                    System.out.println("Exc when delete objPstEmpScheduleHistory on " + getClass().getName() + " : " + e.toString());
                    continue;
                }

                //System.out.println("<END>");
            }            
        }else{
           // System.out.println("No obj EmpSchedule History available will be analyze ...");
        }
    }
    
    
    /**
     * keterangan: untuk analisa status
     * create by satrya 2013-07-25 di update by priska
     * @param limitStart
     * @param recordToGet
     * @param selectedDate
     * @param lDepartmentOid
     * @param lSectionOid
     * @param smEployeeName
     * @param sPayrollNum 
     */
    public static void analyzePresencePerEmployee(int limitStart,int recordToGet,Date selectedDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum,String empCat ){
        //public static void analyzePresencePerEmployee(int limitStart,int recordToGet,Date selectedDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum){
        analyzePresencePerEmployee(limitStart, recordToGet, selectedDate, lDepartmentOid, lSectionOid, smEployeeName, sPayrollNum, null,null,null,empCat,0);
    }
    
    public static void analyzePresencePerEmployee(int limitStart,int recordToGet,Date selectedDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum,String empCat,long lDivisionOid ){
        //public static void analyzePresencePerEmployee(int limitStart,int recordToGet,Date selectedDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum){
        analyzePresencePerEmployee(limitStart, recordToGet, selectedDate, lDepartmentOid, lSectionOid, smEployeeName, sPayrollNum, null,null,null,empCat,lDivisionOid);
    }

    
    
    //update by satrya 2012-09-13
    /**
     * 
     * @param presenceDate
     * @param lDepartmentOid
     * @param lSectionOid
     * @param smEployeeName
     * @param sPayrollNum
     * @create satrya
     * @description  untuk menganalisa status apakah karyawan tersebut absence,late,OK dll
     */
    //update by devin 2014-02-21
    public static void analyzePresencePerEmployee(int limitStart,int recordToGet,Date selectedDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum,String oidSPresence,TransManagerAssistant transManagerAssistant, long lDivisionOid) {
        
        analyzePresencePerEmployee( limitStart, recordToGet, selectedDate,  lDepartmentOid,  lSectionOid,  smEployeeName,  sPayrollNum, oidSPresence, transManagerAssistant,null,"",lDivisionOid);
    
    }
    // public static void analyzePresencePerEmployee( int limitStart,int recordToGet,Date selectedDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum,String oidSPresence,TransManagerAssistant transManagerAssistant) {
    public static void analyzePresencePerEmployee( int limitStart,int recordToGet,Date selectedDate, long lDepartmentOid, long lSectionOid, String smEployeeName, String sPayrollNum,String oidSPresence,TransManagerAssistant transManagerAssistant,Vector ovt,String empCat,long lDivisionOid) {
        Vector result = new Vector(1, 1);  
        if (selectedDate != null) 
        {
            DBResultSet dbrs = null;
            long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
            int selectedIndex = selectedDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
         
              //priska menambahkan update ke reason 23-12-2014
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int firstFieldIndexNote = PstEmpSchedule.OFFSET_INDEX_NOTE + (selectedIndex - 1);
            int intFirstReason =0;
            try{
                String sintFirstReason = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                intFirstReason = Integer.parseInt(sintFirstReason);
             }catch(Exception ex){
                 System.out.println("VALUE_B_REASON_SYMBOL NOT Be SET"+ex);
                 intFirstReason=0;
             }
            //priska 2015-03-09
            long propLateToleransi = -1;
            try {
                 propLateToleransi = Long.parseLong(PstSystemProperty.getValueByName("VALUE_LATE_TOLERANSI"));
            } catch (Exception ex) {
                System.out.println("Execption VALUE_LATE_TOLERANSI: " + ex);
            }
            //mecari time pada jam yang sudah diset
            long nilaiLateToleransi = propLateToleransi * 60 * -60000 ; 
              //update by priska 2015-03-05
          
           long NOTDC = 0;
           
           try{
                NOTDC = Integer.valueOf(PstSystemProperty.getValueByName("VALUE_NOTDC")); 
            } catch (Exception e){
               System.out.printf("VALUE_NOTDC TIDAK DI SET?"); 
            }
           
           
           String oidAl = String.valueOf(PstSystemProperty.getValueByName("OID_AL"));
           String oidLl = String.valueOf(PstSystemProperty.getValueByName("OID_LL"));
           String oidDp = String.valueOf(PstSystemProperty.getValueByName("OID_DP"));
           String oidUl = String.valueOf(PstSystemProperty.getValueByName("OID_UL"));
           String oidIMT = String.valueOf(PstSystemProperty.getValueByName("OID_IMT"));
           String oidIPC = String.valueOf(PstSystemProperty.getValueByName("OID_IPC"));
           String oidIDT = String.valueOf(PstSystemProperty.getValueByName("OID_IDT"));
           String oidChildBirth = String.valueOf(PstSystemProperty.getValueByName("OID_ChildBirth"));
           String oidSl = String.valueOf(PstSystemProperty.getValueByName("OID_SL"));
           String oidH4 = String.valueOf(PstSystemProperty.getValueByName("OID_H4"));
           String oidS = String.valueOf(PstSystemProperty.getValueByName("OID_S"));
           String oidSpecial = String.valueOf(PstSystemProperty.getValueByName("OID_SPECIAL"));
           
           
           String reasonValueIPC = String.valueOf(PstSystemProperty.getValueByName("REASON_VALUE_IPC"));
           String reasonValueIDT = String.valueOf(PstSystemProperty.getValueByName("REASON_VALUE_IDT"));
            int ConfigAutoOk =0;
            try{
                String sConfigAutoOk = PstSystemProperty.getValueByName("CONFIG_AUTO_OK"); 
                ConfigAutoOk = Integer.parseInt(sConfigAutoOk);
             }catch(Exception ex){
                 System.out.println("CONFIG_AUTO_OK NOT Be SET"+ex);

             }
            //update by satrya 2012-10-31 
            Vector vListLeavePermision = null;
            Vector vPresenceBreak = null;
            Date dtSchedule = new Date();
            Date dtScheduleDateTime = new Date();
            //update by satrya 2012-10-31
            
            Date dtScheduleIn = new Date();
            dtScheduleIn.setDate(selectedDate.getDate());
            dtScheduleIn.setMonth(selectedDate.getMonth());
            dtScheduleIn.setYear(selectedDate.getYear());
            //update by satrya 2012-11-1
            Date dtScheduleOut = new Date();
            dtScheduleOut.setDate(selectedDate.getDate());
            dtScheduleOut.setMonth(selectedDate.getMonth());
            dtScheduleOut.setYear(selectedDate.getYear());
            //
            dtSchedule.setDate(selectedDate.getDate());
            dtSchedule.setMonth(selectedDate.getMonth());
            dtSchedule.setYear(selectedDate.getYear());
             long employeeId  = 0;
              int intFirstStatus =0;
             int cekLeave =0;
             //update by satrya 2013-07-26
             String empNumber="";
             Date dtIn=null;
             Date dtOut=null;
             //update by devin 2014-02-21
             Date fromDateTime = new Date();
             Date toDateTime = new Date();
              if(ovt !=null && ovt.size()>0)
              for(int c=0;c<ovt.size();c++){
                   OvertimeDetail ov =(OvertimeDetail)ovt.get(c);
                   fromDateTime = ov.getDateFrom();
                   toDateTime = ov.getDateTo();
             
                   
                                                          
               }
             
             try{
                 String sCekLeave = PstSystemProperty.getValueByName("LEAVE_FOR_ABSENCE"); 
                 cekLeave = Integer.parseInt(sCekLeave);
             }catch(Exception ex){
                 System.out.println("LEAVE_FOR_ABSENCE NOT Be SET"+ex);
                 cekLeave=0;
             }
            String k ="";
            //mencari Presence Break In dan Out
            //Vector vPresenceBreakOutIn = new Vector();
            //vPresenceBreakOutInddd = PstPresence.list(0,0, sPayrollNum, lDepartmentOid, smEployeeName, selectedDate, selectedDate, lSectionOid, sPayrollNum);
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
                //update by satrya 2012-09-13
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]+
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN] +
                //update by satrya 2013-07-26
                ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PRESENCE_CHECK_PARAMETER] +        
                        
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
                
                if(lDivisionOid != 0)
                {
                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +                               
                           " = " + lDivisionOid;
                }  
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
                
                 if(!empCat.equals("") || empCat != "" || empCat.length() > 0 )
                {
                     sql = sql + " AND EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " IN ( "+ empCat +" )";
                }
                 
                if(smEployeeName != null && smEployeeName.length() > 0)
                {
//                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +                               
//                           " LIKE \"%" + smEployeeName + "%\"";
                      Vector vectFullName = logicParser(smEployeeName);
                           sql = sql + " AND ";
                         if (vectFullName != null && vectFullName.size() > 0) {
                             sql = sql + " (";
                             for (int i = 0; i < vectFullName.size(); i++) {
                                 String str = (String) vectFullName.get(i);
                                 if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                     sql = sql + " EMP. "+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                             + " LIKE '%" + str.trim() + "%' ";
                                 } else {
                                     sql = sql + str.trim();
                                 }
                             }
                             sql = sql + ")";
                         } 
                }                                             
                //update by satrya 2012-08-01
                if(sPayrollNum!=null && sPayrollNum.length() > 0)
                {
//                     sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +                               
//                           "= \"" + sPayrollNum + "\"";
                     Vector vecNum = logicParser(sPayrollNum);
                           sql = sql + " AND ";
                         if (vecNum != null && vecNum.size() > 0) {
                             sql = sql + " (";
                             for (int i = 0; i < vecNum.size(); i++) {
                                 String str = (String) vecNum.get(i);
                                 if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                     sql = sql + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                             + " LIKE '%" + str.trim() + "%' ";
                                 } else {
                                     sql = sql + str.trim();
                                 }
                             }
                             sql = sql + ")";
                         } 
                     
                }
                
                // mencari data masing-masing first "in-out" atau second "in-out" yang tidak null
                /*sql = sql + " AND" +
                            " (" +
                            " (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] + "))" +
                            " OR (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + "))" +
                            " )" ; */               
                if(limitStart == 0 && recordToGet == 0)
                    sql = sql + "";
                else
                    sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                dbrs = DBHandler.execQueryResult(sql);
                k=sql;
                ResultSet rs = dbrs.getResultSet();
                 while (rs.next()) {
                    /*
                     * Description : penambahan dua baris code, yaitu jika employee PRESENCE_CHECK_PARAMETER tidak sama dengan Always OK
                     * Date : 2015-01-15
                     * Author : Hendra McHen
                     */
                    int empPresenceOK = rs.getInt("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PRESENCE_CHECK_PARAMETER]);
                    

                        boolean cekCutiAda = true;// proses pengecekan cuti : true jika cuti harus di check, false jika ada cuti
                        // check schedule type, category holiday or on schedule
                        int scheduleCategory = rs.getInt(1);
                        employeeId = rs.getLong(3);
                        ///update by satrya 2013-07-26
                        empNumber = rs.getString("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                        if (rs.getDate(4) != null) {
                            dtIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                        } else {
                            dtIn = null;
                        }
                        if (rs.getDate(5) != null) {
                            dtOut = DBHandler.convertDate(rs.getDate(5), rs.getTime(5));
                        } else {
                            dtOut = null;
                        }

                        //update by satrya 2012-09-15
                        int status_IN = 0;
                        int status_OUT = 0;
                        int status_BOUT = 0;
                        int status_BIN = 0;
                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;

                        if (!(scheduleCategory == PstScheduleCategory.CATEGORY_OFF)) {//jika schedulenya !=off maka sistem mengaggap dia ok
                     /* ini tidak mau jika schedulenya AL 
                             * if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE
                             || scheduleCategory == PstScheduleCategory.CATEGORY_OFF
                             || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                             || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                             || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE
                             || scheduleCategory == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                             || scheduleCategory == PstScheduleCategory.CATEGORY_UNPAID_LEAVE)){*/

                            // get symbol in
                            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();//SessEmployeeLateness.getDateRoster(presenceDate.getDate(), empschedule);
                            scheduleSymbol.setOID(rs.getLong(9));//set Oid schedule symbol
                            scheduleSymbol.setTimeIn(rs.getTime(10));//set schedule Time In
                            scheduleSymbol.setTimeOut(rs.getTime(11)); //set schedule Time Out
                            scheduleSymbol.setBreakOut(rs.getTime(12)); //set schedule break out
                            scheduleSymbol.setBreakIn(rs.getTime(13));//set schedule Break in

                            if (scheduleCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) {
                                if (rs.getDate(4) != null) {
                                    Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                                    Date cx = scheduleSymbol.getTimeIn();
                                    if (cx.getHours() == 0) {
                                        cx.setHours(23);
                                        cx.setMinutes(59);
                                        cx.setSeconds(59);
                                        scheduleSymbol.setTimeIn(cx);
                                    }


                                    dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(0);

                                    // waktu masuk
                                    Date dtPresenceIn = new Date();
                                    dtPresenceIn.setTime(timeIn.getTime());
                                    dtPresenceIn.setSeconds(0);

                                    long iDuration = 0;
                                    //long iDurationHour = 0;
                                    //long iDurationMin = 0;
                                /*iDuration = DateCalc.timeDifference(dtx, dt);                                

                                     if (iDuration < 0) 
                                     {
                                     intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                     }*/
                                    iDuration = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                    if (iDuration <= nilaiLateToleransi) {
                                        status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                    } else {
                                        status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }
                                }

                                //if (!(rs.getDate(6) != null)) {
                                if ((rs.getDate(6) != null)) {
                                    Date timeIn = DBHandler.convertDate(rs.getDate(6), rs.getTime(6));
                                    //Date dt = new Date();
                                    long oidSymbol = rs.getLong(8);
                                    //update by satrya 2012-09-13
                                    if (scheduleSymbol != null && oidSymbol != 0 && scheduleSymbol.getOID() != oidSymbol) {
                                        try {
                                            scheduleSymbol = PstScheduleSymbol.fetchExc(oidSymbol);
                                        } catch (Exception e) {
                                            System.out.println("Exc when fetch Schedule Symbol : " + e.toString());
                                        }
                                    }
                                    Date cx = scheduleSymbol.getTimeIn();
                                    if (cx.getHours() == 0) {
                                        cx.setHours(23);
                                        cx.setMinutes(59);
                                        cx.setSeconds(59);
                                        scheduleSymbol.setTimeIn(cx);
                                    }

                                    /*dt.setDate(timeIn.getDate());
                                     dt.setMonth(timeIn.getMonth());
                                     dt.setYear(timeIn.getYear());
                                     dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                     dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                     dt.setSeconds(0);*/
                                    dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(0);

                                    // waktu masuk
                                    Date dtPresenceIn = new Date();
                                    dtPresenceIn.setTime(timeIn.getTime());
                                    dtPresenceIn.setSeconds(0);

                                    long iDuration = 0;
                                    //long iDurationHour = 0;
                                    //long iDurationMin = 0;
                                    // iDuration = DateCalc.timeDifference(dtx, dt);
                                /*if (iDuration < 0) 
                                     {
                                     intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                     } */
                                    iDuration = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                    if (iDuration <= 0) {
                                        status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                    } else {
                                        status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }
                                }

                            } else {

                                // jika "IN" bukan null

                                if (rs.getDate(4) != null) {
                                    Date timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));

                                    Date cx = scheduleSymbol.getTimeIn();
                                    if (cx.getHours() == 0) {
                                        cx.setHours(23);
                                        cx.setMinutes(59);
                                        cx.setSeconds(59);
                                        scheduleSymbol.setTimeIn(cx);
                                    }


                                    dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(0);

                                    // waktu masuk
                                    Date dtPresenceIn = new Date();
                                    dtPresenceIn.setTime(timeIn.getTime());
                                    dtPresenceIn.setSeconds(0);

                                    long iDurationIn = 0;
                                    //long iDurationHour = 0;
                                    //long iDurationMin = 0;
                                    iDurationIn = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                    if (iDurationIn < nilaiLateToleransi) {
                                        status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                    } else {
                                        status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }
                                }
                                //mencari status Out
                                //update by satrya 2012-09-13
                                if (rs.getDate(5) != null) {
                                    Date timeOut = DBHandler.convertDate(rs.getDate(5), rs.getTime(5)); //mencari presence time Out

                                    /*Date timeOutNol = scheduleSymbol.getTimeOut();
                                     if(timeOutNol.getHours()==0) //jika time Outnya jam 0
                                     {
                                     timeOutNol.setHours(23);
                                     timeOutNol.setMinutes(59);
                                     timeOutNol.setSeconds(59);
                                     scheduleSymbol.setTimeOut(timeOutNol);
                                     }*/

                                    dtSchedule.setHours(scheduleSymbol.getTimeOut().getHours());
                                    dtSchedule.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(0);

                                    if ( scheduleSymbol.getTimeOut().getHours() == 0 && scheduleSymbol.getTimeOut().getMinutes() == 0 && scheduleSymbol.getTimeOut().getSeconds() == 0 ) {
                                    dtSchedule.setHours(23);
                                    dtSchedule.setMinutes(59 - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(59);
                                    }
                                    
                                    // waktu masuk
                                    Date dtPresenceOut = new Date();
                                    dtPresenceOut.setTime(timeOut.getTime());
                                    dtPresenceOut.setSeconds(0);
                                    
                                    Date dtscheduleOutX = new Date();
                                    if (scheduleCategory == PstScheduleCategory.CATEGORY_ACCROSS_DAY){
                                        dtscheduleOutX = new Date(dtSchedule.getTime() + (24*60*60*1000));
                                    } else {
                                        dtscheduleOutX = dtSchedule;
                                    }
                                    
                                    long iDurationOut = 0;
                                    iDurationOut = DateCalc.timeDifference(dtPresenceOut, dtscheduleOutX);
                                    if (iDurationOut > 0) {
                                        status_OUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                    } else {
                                        status_OUT = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }
                                }


                                if (status_IN != 0 && status_OUT != 0) {
                                    if (status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE || status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME) {
                                        if (status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE && status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME) {
                                            //min ceknya 15 menit
                                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY;
                                        } else if (status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE && status_OUT == PstEmpSchedule.STATUS_PRESENCE_OK) {
                                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                        } else if (status_IN == PstEmpSchedule.STATUS_PRESENCE_OK && status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME) {
                                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                        }
                                    } else {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }
                                } else {
                                    if (status_IN != 0 && status_OUT == 0) {
                                        if (status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE) {
                                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                        } else {
                                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                        }
                                    } else if (status_IN == 0 && status_OUT != 0) {
                                        if (status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME) {
                                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                        } else {
                                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                        }
                                    } else {
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                    }
                                }
                                //update by satrya 2012-10-31
                                //untuk cek apakah ada leave

                                Vector vListLeaveTakenFinishDate = SessLeaveApp.checkDetailLeaveTakenDate(employeeId, selectedDate);
                                if (vListLeaveTakenFinishDate != null && vListLeaveTakenFinishDate.size() > 0) {
                                    Date timeIn = null;
                                    Date timeOut = null;

                                    if (rs.getTime(4) != null) {
                                        timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                                        dtScheduleIn.setHours(scheduleSymbol.getTimeIn().getHours());
                                        dtScheduleIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                        dtScheduleIn.setSeconds(0);
                                    }

                                    if (rs.getDate(5) != null) {
                                        timeOut = DBHandler.convertDate(rs.getDate(5), rs.getTime(5));
                                        dtScheduleOut.setHours(scheduleSymbol.getTimeOut().getHours());
                                        dtScheduleOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                        dtScheduleOut.setSeconds(0);
                                    }
                                    LeaveCheckTakenDateFinish leaveCheck = null;

                                    if (cekLeave == 0) {///melakukan cek jika dia cekLeave=1 maka sistem akan melakukan set Statusnya menjadi absence <case di KTI>
                                        for (int leaveCheckIdx = 0; leaveCheckIdx < vListLeaveTakenFinishDate.size(); leaveCheckIdx++) {
                                            leaveCheck = (LeaveCheckTakenDateFinish) vListLeaveTakenFinishDate.get(leaveCheckIdx);
                                            if ((leaveCheck.getTakenDate().getTime() <= dtScheduleIn.getTime()
                                                    && leaveCheck.getFinishDate().getTime() >= (timeIn == null ? 0 : timeIn.getTime()))
                                                    || (leaveCheck.getTakenDate().getTime() <= dtScheduleOut.getTime()
                                                    && leaveCheck.getFinishDate().getTime() >= (timeOut == null ? 0 : timeOut.getTime()))) {
                                                //intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK; 
                                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                                cekCutiAda = false;
                                            }
                                        }
                                    } else {
                                        //intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                        cekCutiAda = false;
                                    }
                                }
                                if (cekCutiAda && (intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_OK || intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED)) {
                                    // analisa break in - out
                                    //Vector vListLeavePermision  = SessEmpSchedule.listLeavePermision(employeeId, selectedDate, lDepartmentOid, periodId);///schedule ada Leave atau break
                                    //Vector vPresenceBreak  = PstPresence.listPresenceBreakByEmpId(selectedDate, employeeId); ///untuk mencari presence
                                    //update by satrya 2012-10-31
                                    //if(vListLeavePermision == null){
                                    vListLeavePermision = SessEmpSchedule.listLeavePermision(employeeId, selectedDate, lDepartmentOid, periodId);///schedule ada Leave atau break
                                    // }
                                    //if(vPresenceBreak == null){
                                    
                                    //by priska config untuk menggunakan break in out atau tidak dalam analisa 20150527
                                     int ConfigBoutBin = 0;
                                try{
                                    ConfigBoutBin = Integer.valueOf(PstSystemProperty.getValueByName("WITHOUT_BOUT_BIN")); 
                                } catch (Exception e){
                                   System.out.printf("Tanpa Break Out / In"); 
                                }
                                if (ConfigBoutBin == 0){
                                vPresenceBreak  = PstPresence.listPresenceBreakByEmpId(selectedDate, employeeId); ///untuk mencari presence
                                }
                                    //}
                                    if (vListLeavePermision != null && vListLeavePermision.size() > 0
                                            || vPresenceBreak != null && vPresenceBreak.size() > 0) {
                                        //mencari status BOut
                                        //mengambil list personalIn dan Out
                                        //update by satrya 2012-09-13
                                        for (int lvIdx = 0; lvIdx < vListLeavePermision.size(); lvIdx++) {
                                            OutPermision outPermision = (OutPermision) vListLeavePermision.get(lvIdx);
                                            OutPermision inPermision = null;
                                            if (outPermision.getInOutType() == OutPermision.INOUT_TYPE_OUT && ((lvIdx + 1) < vListLeavePermision.size())) {
                                                inPermision = (OutPermision) vListLeavePermision.get(lvIdx + 1);
                                            }
                                            if (ConfigBoutBin == 0){
                                            for (int prIdx = 0; prIdx < vPresenceBreak.size(); prIdx++) {
                                                Presence presenceBreak = (Presence) vPresenceBreak.get(prIdx);
                                                /* if( outPermision.getInOutType() == OutPermision.INOUT_TYPE_OUT
                                                 && presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL  ){
                                                 break;//keluar dari loop vPresenceBreak
                                                 }
                                                 if( outPermision.getInOutType() == OutPermision.INOUT_TYPE_IN
                                                 && presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL  ){
                                                 continue;//keluar dari loop vPresenceBreak
                                                 }
                                                 * */
                                                //update by satrya 2012-11-13
                                                if (prIdx % 2 == 0) {
                                                    //mencari personal Out
                                                    if (outPermision.getInOutType() == OutPermision.INOUT_TYPE_OUT && presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {
                                                        Date scheduleDatebreakOut = outPermision.getTypeScheduleDateTime(); //mencari ScheduleDateTime B Out
                                                        Date presenceDate = presenceBreak.getPresenceDatetime();
                                                        /*Date breakOutNol = outPermision.getTypeScheduleDateTime();
                                                         Date presenceDateNol = presenceBreak.getPresenceDatetime();
                                                         if(breakOutNol.getHours()==0) //jika time Outnya jam 0
                                                         {
                                                         breakOutNol.setHours(23);
                                                         breakOutNol.setMinutes(59);
                                                         breakOutNol.setSeconds(59);
                                                         //scheduleSymbol.setTimeOut(breakOutNol);
                                                         }
                                                         if(presenceDateNol.getHours()==0) //jika time Outnya jam 0
                                                         {
                                                         presenceDateNol.setHours(23);
                                                         presenceDateNol.setMinutes(59);
                                                         presenceDateNol.setSeconds(59);
                                                         // scheduleSymbol.setTimeIn(presenceDateNol);
                                                         }*/
                                                        dtScheduleDateTime.setDate(scheduleDatebreakOut.getDate());
                                                        dtScheduleDateTime.setMonth(scheduleDatebreakOut.getMonth());
                                                        dtScheduleDateTime.setYear(scheduleDatebreakOut.getYear());
                                                        dtScheduleDateTime.setHours(scheduleDatebreakOut.getHours());
                                                        dtScheduleDateTime.setMinutes(scheduleDatebreakOut.getMinutes() - SessEmployeeLateness.TIME_LATES);
                                                        dtScheduleDateTime.setSeconds(0);

                                                        // waktu keluar istirahat
                                                        Date dtSPresenceTimeBOut = new Date();
                                                        dtSPresenceTimeBOut.setTime(presenceDate.getTime());
                                                        dtSPresenceTimeBOut.setSeconds(0);

                                                        long iDurationBOut = 0;
                                                        iDurationBOut = DateCalc.timeDifference(dtSPresenceTimeBOut, dtScheduleDateTime);
                                                        //update by devin 2014-02-21
                                                        if (ovt != null && ovt.size() > 0) {
                                                            if (dtSPresenceTimeBOut.getTime() < fromDateTime.getTime() || dtSPresenceTimeBOut.getTime() > toDateTime.getTime()) {
                                                                status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;
                                                            }
                                                        } else if (iDurationBOut > 0) {
                                                            status_BOUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                                        } else {

                                                            Date scheduleDatebreakIn = inPermision.getTypeScheduleDateTime();
                                                            scheduleDatebreakIn.setSeconds(0);

                                                            if (DateCalc.timeDifference(scheduleDatebreakIn, dtSPresenceTimeBOut) > 0) {
                                                                if ((lvIdx + 2) < vListLeavePermision.size()) {
                                                                    lvIdx = lvIdx + 2;
                                                                    break;
                                                                }
                                                                //update by satrya 2012-11-08
                                                                //vPresenceBreak.remove(prIdx);
                                                                //update by satrya 2012-11-13
                                                                if (status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME) {
                                                                    status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;
                                                                }
                                                            } else {
                                                                if (status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME) {
                                                                    status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;
                                                                }
                                                                // vPresenceBreak.remove(prIdx);
                                                                break;
                                                            }
                                                        }
                                                    }//end loppoutpermition out
                                                } else {
                                                    //mencari personal IN
                                                    if (outPermision.getInOutType() == OutPermision.INOUT_TYPE_IN
                                                            && presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {
                                                        Date scheduleDatebreakIn = outPermision.getTypeScheduleDateTime(); //mencari ScheduleDateTime B Out
                                                        Date presenceDate = presenceBreak.getPresenceDatetime();
                                                        /*Date breakInNol = outPermision.getTypeScheduleDateTime();
                                                         Date presenceDateNol = presenceBreak.getPresenceDatetime();
                                                
                                                         if(breakInNol.getHours()==0) //jika time Outnya jam 0
                                                         {
                                                         breakInNol.setHours(23);
                                                         breakInNol.setMinutes(59);
                                                         breakInNol.setSeconds(59);
                                                         // scheduleSymbol.setTimeOut(breakInNol);
                                                         }
                                                         if(presenceDateNol.getHours()==0) //jika time Outnya jam 0
                                                         {
                                                         presenceDateNol.setHours(23);
                                                         presenceDateNol.setMinutes(59);
                                                         presenceDateNol.setSeconds(59);
                                                         //scheduleSymbol.setTimeIn(presenceDateNol);
                                                         }*/
                                                        dtScheduleDateTime.setDate(scheduleDatebreakIn.getDate());
                                                        dtScheduleDateTime.setMonth(scheduleDatebreakIn.getMonth());
                                                        dtScheduleDateTime.setYear(scheduleDatebreakIn.getYear());
                                                        dtScheduleDateTime.setHours(scheduleDatebreakIn.getHours());
                                                        dtScheduleDateTime.setMinutes(scheduleDatebreakIn.getMinutes() - SessEmployeeLateness.TIME_LATES);
                                                        dtScheduleDateTime.setSeconds(0);

                                                        // waktu keluar istirahat
                                                        Date dtSPresenceTimeBIn = new Date();
                                                        dtSPresenceTimeBIn.setTime(presenceDate.getTime());
                                                        dtSPresenceTimeBIn.setSeconds(0);

                                                        long iDurationBOut = 0;
                                                        iDurationBOut = DateCalc.timeDifference(dtSPresenceTimeBIn, dtScheduleDateTime);
                                                        ///??? tanya
                                                        //update by devin 2014-02-21
                                                        if (ovt != null && ovt.size() > 0) {
                                                            if (dtSPresenceTimeBIn.getTime() < fromDateTime.getTime() || dtSPresenceTimeBIn.getTime() > toDateTime.getTime()) {
                                                                status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                                            }
                                                        } else if (iDurationBOut < 0) {
                                                            status_BIN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                                        } else {
                                                            if (status_BIN != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BIN != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME) {
                                                                status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                                            }
                                                        }
                                                        //vPresenceBreak.remove(prIdx);
                                                    }
                                                }//end chk bil ganjil  
                                            }   //end loop break 
                                            }//end if configbinout
                                        }//end LOOP FOR LEAVE
                                        if (status_BOUT != 0 || status_BIN != 0) {
                                            if (status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED || status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED
                                                    || status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME || status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE) {
                                                if (status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME && status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE) {
                                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY;
                                                } else if (status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT == PstEmpSchedule.STATUS_PRESENCE_OK) {
                                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                                }//update by satrya 2012-11-13
                                                else if (status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED && status_BOUT == PstEmpSchedule.STATUS_PRESENCE_OK) {
                                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                                } else if (status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME && status_BIN == PstEmpSchedule.STATUS_PRESENCE_OK) {
                                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                                }//update by satrya 2012-11-13
                                                else if (status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED && status_BIN == PstEmpSchedule.STATUS_PRESENCE_OK) {
                                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                                } else if (status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME && status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED) {
                                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                                } else if (status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED && status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE) {
                                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                                }
                                            }
                                        }
                                    }

                                }//end break

                            }// end schedule normal
                        }   //end schedule category

                         if (scheduleCategory == PstScheduleCategory.CATEGORY_OFF){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        
                        
                        //schedule 6 jam kerja by priska 20150508
                        int Config6jamkerja = 0;
                                try{
                                    Config6jamkerja = Integer.valueOf(PstSystemProperty.getValueByName("CONFIG6JAMKERJA")); 
                                } catch (Exception e){
                                   //System.out.printf("6 jam kerja"); 
                                }
                        if (Config6jamkerja == 1 ){
                            if (rs.getDate(4) != null && rs.getDate(5) != null){
                            Date timeInN = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                            Date timeOutN = DBHandler.convertDate(rs.getDate(5), rs.getTime(5));

                            if (status_IN == PstEmpSchedule.STATUS_PRESENCE_OK && status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                               Date scheduleInN = rs.getTime(10);  
                                if ( timeInN.getTime() < scheduleInN.getTime() ){
                                    long lnTempo =  timeOutN.getTime() - scheduleInN.getTime(); // get the time in milli seconds
                                    long minimaljamkerja = 6 * 60 * 60000 ; 
                                    if (lnTempo > minimaljamkerja ){
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    } 
                                } else {
                                    long lnTempo =  timeOutN.getTime() - timeInN.getTime(); // get the time in milli seconds
                                    long minimaljamkerja = 6 * 60 * 60000 ; 
                                    if (lnTempo > minimaljamkerja ){
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }    
                                } 
                            }
                        }
                        }
                        
                         //jika ada leave maka ok
                        //priska 20150513
                        if (dtScheduleIn != null && employeeId > 1){
                        long cekAl = PstLeaveApplication.getLeaveAlExecute(employeeId, dtScheduleIn);
                        if ((cekAl > 0) || ((oidAl.equals(""+rs.getLong(9))) && (!oidAl.equals("") ) ) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        long cekDp = PstLeaveApplication.getLeaveDpExecute(employeeId, dtScheduleIn);
                        if (cekDp > 0 || ((oidDp.equals(""+rs.getLong(9))) && (!oidDp.equals("") ) ) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        long cekSl = PstLeaveApplication.getLeaveSpecial(employeeId, dtScheduleIn);
                        if (cekSl > 0){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        
                         //priska menambahkan annual leave 2016-04-03
                       boolean isSpecialLeave = false;
                        try{
                            ScheduleSymbol scheduleSymbol = PstScheduleSymbol.fetchExc(rs.getLong(9));
                            if (oidSpecial.equals(""+scheduleSymbol.getScheduleCategoryId())){
                                isSpecialLeave = true;
                            }
                        }catch(Exception e){}
                        
                        if (isSpecialLeave) {
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        }
                       
                        
                        if (empPresenceOK == 1){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                          //sementara untuk di borobudur
                        long scheduleID = rs.getLong(9);
                        if (scheduleID == 504404576464835128l){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }  
                        // update status schedule       
                        long reasonSelect = PstEmpSchedule.getReasonValue(periodId, employeeId, firstFieldIndexReason); 
                        String noteSelect = PstEmpSchedule.getNoteValue(periodId, employeeId, firstFieldIndexNote); 
                        int successUpd = 0 ;
                        
                        if (ConfigAutoOk == 1 && (reasonSelect != 0 || (noteSelect != null && !noteSelect.equals("") && !noteSelect.equals(" "))  ) && (reasonSelect != NOTDC && reasonSelect != intFirstReason) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                         if ( ConfigAutoOk == 1 && (noteSelect != null && !noteSelect.equals("") && !noteSelect.equals(" ") )){
                             intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                             intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                         boolean cekUl = PstSpecialUnpaidLeaveTaken.UnpaidLeaveToday(employeeId, selectedDate);
                        if (cekUl){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                        } 
                        if (intFirstStatus != PstEmpSchedule.STATUS_PRESENCE_ABSENCE ){
                        intFirstReason = 0;
                        }
                        successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);
                         
                        
                        
//                        if (ConfigAutoOk == 1 && (reasonSelect != 0 || (noteSelect != null && !noteSelect.equals("")) ) && (reasonSelect != NOTDC && reasonSelect != intFirstReason) ){
//                             //priska menambahkan kondisi jika absence
//                            if (intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE ){
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);
//                            } else {
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, intSecondStatus, firstFieldIndexReason, 0);
//                            }
//                        } else if ((ConfigAutoOk == 1 && (noteSelect != null && !noteSelect.equals("")))) {
//                            successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, intSecondStatus, firstFieldIndexReason, 0);
//                        } else {
//                            if (intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE ){
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);
//                            } else {
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, 0);
//                            }     
//                        }
                 
                        
                        //update reason 20151020
                           int intreasonA =-1;
                           int intAutoReason = 0;
                        try{
                            try{
                            String sintreasonA = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                            intreasonA = Integer.parseInt(sintreasonA);
                            }catch(Exception ex){}
                            try{
                             String sinAutoReason = PstSystemProperty.getValueByName("SET_AUTO_REASON"); 
                            intAutoReason = Integer.parseInt(sinAutoReason);
                            }catch(Exception ex){}
                            long cekreason = PstEmpSchedule.getReasonValue(periodId, employeeId, firstFieldIndexReason);
                            if ((intFirstStatus != PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && ((!reasonValueIPC.equals(""+cekreason)) && (!reasonValueIDT.equals(""+cekreason))  ) ) {
                            
                               int upReason = LatenessAnalyser.updatePresenceReason(periodId, employeeId,firstFieldIndexReason ,0) ;
                            }  else if ((intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (intAutoReason == 1) ){
                               int upReason = LatenessAnalyser.updatePresenceReason(periodId, employeeId,firstFieldIndexReason ,intreasonA) ; 
                            }
                        }catch(Exception ex){
                             System.out.println("VALUE_REASON A NOT Be SET"+ex);
                        }
                        
                        if (successUpd != 0 && oidSPresence != null && oidSPresence.length() > 0) {
                            //String oidSPresence= analyseStatusDataPresence.getPresenceId();
                            PstPresence.updatePresenceStatusAnalyze(employeeId, oidSPresence, periodId);
                            String sDtIn = dtIn == null ? "-" : Formater.formatDate(dtIn, "yyyy-MM-dd HH:mm");
                            String sDtOut = dtOut == null ? "-" : Formater.formatDate(dtOut, "yyyy-MM-dd HH:mm");
                            transManagerAssistant.setMessageProsessAbsence("Date Analize Presence Status..: " + Formater.formatDate(selectedDate, "dd-MM-yyyy") + " || Payroll Number:" + empNumber + ">> DATE IN: " + sDtIn + " || DATE OUT:" + sDtOut + " || STATUS: " + PstEmpSchedule.strPresenceStatus[intFirstStatus]);
                        }
                        // System.out.println("Status yang di hasilkan : employe ID:"+employeeId+"Periode:"+periodId+"Statusnya: "+intFirstStatus+"tanggal"+selectedDate);
                    } // End while(rs.next)
                
                    //System.out.println("Status yang di hasilkan : employe ID: " +employeeId+ " Periode: " +periodId+ " Statusnya: "+intFirstStatus+ " tanggal "+selectedDate);
                    //jika schedule tidak ada maka di set Ok
                    //else{
                        
                        /*intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        
                        LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);                                                  
                        */
                    //}
               // }    
            }
            catch (Exception e) 
            {
                System.out.println("Exc checkEmployeeLateness : " + e.toString()+" Employee Id "+employeeId+k);
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
    
 //update by satrya 2012-10-15
    /**
     * Ktereangan: melakukan alanisa status late,ok,only In dan out dllll by employee id
     * @param selectedDate
     * @param employeId 
     */
    public static void analyzePresencePerEmployeeByEmployeeId(Date selectedDate, long employeeId) {
        Vector result = new Vector(1, 1);  
        if (selectedDate != null) 
        {
            DBResultSet dbrs = null;
            long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
            int selectedIndex = selectedDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
            
              //priska menambahkan update ke reason 23-12-2014
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int firstFieldIndexNote = PstEmpSchedule.OFFSET_INDEX_NOTE + (selectedIndex - 1);
            int intFirstReason =0;
            try{
                String sintFirstReason = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                intFirstReason = Integer.parseInt(sintFirstReason);
             }catch(Exception ex){
                 System.out.println("VALUE_B_REASON_SYMBOL NOT Be SET"+ex);
                 intFirstReason=0;
             }
        
            //priska 2015-03-09
            long propLateToleransi = -1;
            try {
                 propLateToleransi = Long.parseLong(PstSystemProperty.getValueByName("VALUE_LATE_TOLERANSI"));
            } catch (Exception ex) {
                System.out.println("Execption VALUE_LATE_TOLERANSI: " + ex);
            }
            //mecari time pada jam yang sudah diset
            long nilaiLateToleransi = propLateToleransi * 60 * -60000 ; 
            
                //update by priska 2015-03-05
          
           long NOTDC = 0;
           
           try{
                NOTDC = Integer.valueOf(PstSystemProperty.getValueByName("VALUE_NOTDC")); 
            } catch (Exception e){
               System.out.printf("VALUE_NOTDC TIDAK DI SET?"); 
            }
              
          
           String oidAl = String.valueOf(PstSystemProperty.getValueByName("OID_AL"));
           String oidLl = String.valueOf(PstSystemProperty.getValueByName("OID_LL"));
           String oidDp = String.valueOf(PstSystemProperty.getValueByName("OID_DP"));
           String oidUl = String.valueOf(PstSystemProperty.getValueByName("OID_UL"));
           String oidIMT = String.valueOf(PstSystemProperty.getValueByName("OID_IMT"));
           String oidIPC = String.valueOf(PstSystemProperty.getValueByName("OID_IPC"));
           String oidIDT = String.valueOf(PstSystemProperty.getValueByName("OID_IDT"));
           String oidChildBirth = String.valueOf(PstSystemProperty.getValueByName("OID_ChildBirth"));
           String oidSl = String.valueOf(PstSystemProperty.getValueByName("OID_SL"));
           String oidH4 = String.valueOf(PstSystemProperty.getValueByName("OID_H4"));
           String oidS = String.valueOf(PstSystemProperty.getValueByName("OID_S"));
           
           String oidSpecial = String.valueOf(PstSystemProperty.getValueByName("OID_SPECIAL"));
           
           String reasonValueIPC = String.valueOf(PstSystemProperty.getValueByName("REASON_VALUE_IPC"));
           String reasonValueIDT = String.valueOf(PstSystemProperty.getValueByName("REASON_VALUE_IDT"));
          
            int ConfigAutoOk =0;
            try{
                String sConfigAutoOk = PstSystemProperty.getValueByName("CONFIG_AUTO_OK"); 
                ConfigAutoOk = Integer.parseInt(sConfigAutoOk);
             }catch(Exception ex){
                 System.out.println("CONFIG_AUTO_OK NOT Be SET"+ex);

             }
            //di hidden karena pencarian berdasarkan schedulenya
            //Vector vListLeaveTakenFinishDate = SessLeaveApp.checkDetailLeaveTakenDate(employeeId,selectedDate);
            //update by satrya 2012-10-31 
            //Vector vLisOverlapCuti = null;
            Vector vPresenceBreak = null;
            Date dtSchedule = null;
            //Date dtScheduleDateTime = new Date();
             //update by satrya 2012-10-31
            Date dtScheduleIn = null;
            Date dtScheduleOut = null;
            dtSchedule = (Date)selectedDate.clone();
            /*dtSchedule.setDate(selectedDate.getDate());
            dtSchedule.setMonth(selectedDate.getMonth());
            dtSchedule.setYear(selectedDate.getYear());*/
            // long employeeId  = 0;
              int intFirstStatus =0;
           int cekLeave =0;
             try{
                 String sCekLeave = PstSystemProperty.getValueByName("LEAVE_FOR_ABSENCE"); 
                 cekLeave = Integer.parseInt(sCekLeave);
             }catch(Exception ex){
                 System.out.println("LEAVE_FOR_ABSENCE NOT Be SET"+ex);
                 cekLeave=0;
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
                //update by satrya 2012-09-13
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]+
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN] +
                        
                //update by priska 2015-06-02
                ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PRESENCE_CHECK_PARAMETER] + 
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
                
                if(employeeId !=0){
                    sql = sql + " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                " = " + employeeId;
                }
                // mencari data masing-masing first "in-out" atau second "in-out" yang tidak null
                /*sql = sql + " AND" +
                            " (" +
                            " (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] + "))" +
                            " OR (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + "))" +
                            " )" ; */               
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) 
                {
                   int empPresenceOK = rs.getInt("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PRESENCE_CHECK_PARAMETER]);
                    
                     boolean cekCutiAda= true;// proses pengecekan cuti : true jika cuti harus di check, false jika ada cuti
                    // check schedule type, category holiday or on schedule
                    int scheduleCategory = rs.getInt(1);
                    employeeId = rs.getLong(3);
                    //update by satrya 2012-09-15
                    int status_IN=0;
                    int status_OUT=0;
                    int status_BOUT = 0;
                    int status_BIN = 0;
                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    
                    if (!(scheduleCategory == PstScheduleCategory.CATEGORY_OFF)){//jika schedulenya !=off maka sistem mengaggap dia ok
                     /* ini tidak mau jika schedulenya AL 
                      * if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_OFF
                            || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                            || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_UNPAID_LEAVE)){*/
                        
                        // get symbol in
                        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();//SessEmployeeLateness.getDateRoster(presenceDate.getDate(), empschedule);
                        scheduleSymbol.setOID(rs.getLong(9));//set Oid schedule symbol
                        scheduleSymbol.setTimeIn(rs.getTime(10));//set schedule Time In
                        scheduleSymbol.setTimeOut(rs.getTime(11)); //set schedule Time Out
                        scheduleSymbol.setBreakOut(rs.getTime(12)); //set schedule break out
                        scheduleSymbol.setBreakIn(rs.getTime(13));//set schedule Break in
                        
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
                                 
                                
                                dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                // waktu masuk
                                Date dtPresenceIn = new Date();
                                dtPresenceIn.setTime(timeIn.getTime());
                                dtPresenceIn.setSeconds(0);
                                
                                long iDuration = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                                /*iDuration = DateCalc.timeDifference(dtx, dt);                                

                                if (iDuration < 0) 
                                {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }*/
                                 iDuration = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                if (iDuration <= nilaiLateToleransi) 
                                {
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } else{
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                }         
                            }
                            
                            if (!(rs.getDate(6) != null)) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(6), rs.getTime(6));
                                Date dt = new Date();
                                long oidSymbol = rs.getLong(8);
                                //update by satrya 2012-09-13
                                if(scheduleSymbol!=null && scheduleSymbol.getOID()!=oidSymbol){
                                try
                                {
                                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSymbol);
                                }
                                catch(Exception e)
                                {
                                    System.out.println("Exc when fetch Schedule Symbol : " + e.toString());
                                }
                               }
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0){
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                
                                /*dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);*/
                                dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                // waktu masuk
                                Date dtPresenceIn = new Date();
                                dtPresenceIn.setTime(timeIn.getTime());
                                dtPresenceIn.setSeconds(0); 
                                
                                long iDuration = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                               // iDuration = DateCalc.timeDifference(dtx, dt);
                                /*if (iDuration < 0) 
                                {
                                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } */  
                               iDuration = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                if (iDuration <= nilaiLateToleransi) 
                                {
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } else{
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
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
                                 
                                
                                dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                // waktu masuk
                                Date dtPresenceIn = new Date();
                                dtPresenceIn.setTime(timeIn.getTime());
                                dtPresenceIn.setSeconds(0);  
                                
                                long iDurationIn = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                                
                                
                                iDurationIn = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                if (iDurationIn < nilaiLateToleransi) 
                                {
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } else{
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                }                                                                 
                            } 
                           //mencari status Out
                            //update by satrya 2012-09-13
                            if(rs.getDate(5) !=null){
                                Date timeOut = DBHandler.convertDate(rs.getDate(5), rs.getTime(5)); //mencari presence time Out
                               
                                /*Date timeOutNol = scheduleSymbol.getTimeOut();
                                if(timeOutNol.getHours()==0) //jika time Outnya jam 0
                                {
                                    timeOutNol.setHours(23);
                                    timeOutNol.setMinutes(59);
                                    timeOutNol.setSeconds(59);
                                    scheduleSymbol.setTimeOut(timeOutNol);
                                }*/
                                 
                                dtSchedule.setHours(scheduleSymbol.getTimeOut().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                 if ( scheduleSymbol.getTimeOut().getHours() == 0 && scheduleSymbol.getTimeOut().getMinutes() == 0 && scheduleSymbol.getTimeOut().getSeconds() == 0 ) {
                                    dtSchedule.setHours(23);
                                    dtSchedule.setMinutes(59 - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(59);
                                 }
                                
                                // waktu masuk
                                Date dtPresenceOut = new Date();
                                dtPresenceOut.setTime(timeOut.getTime());
                                dtPresenceOut.setSeconds(0);  
                                
                                    Date dtscheduleOutX = new Date();
                                    if (scheduleCategory == PstScheduleCategory.CATEGORY_ACCROSS_DAY){
                                        dtscheduleOutX = new Date(dtSchedule.getTime() + (24*60*60*1000));
                                    } else {
                                        dtscheduleOutX = dtSchedule;
                                    }
                                    
                                    long iDurationOut = 0;
                                    iDurationOut = DateCalc.timeDifference(dtPresenceOut, dtscheduleOutX);
                                    if (iDurationOut > 0) {
                                        status_OUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                    } else {
                                        status_OUT = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }   
                            }
                                                                                     
                                
                           if( status_IN!=0 && status_OUT!=0 ){
                               if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE ||  status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                   if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE &&  status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                       //min ceknya 15 menit
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY;
                                   } else if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE &&  status_OUT == PstEmpSchedule.STATUS_PRESENCE_OK){
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                   }  else if(status_IN == PstEmpSchedule.STATUS_PRESENCE_OK &&  status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                   }                               
                               } else{
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_OK;
                               }
                           } else{
                               if( status_IN!=0 && status_OUT==0 ){
                                  if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE ){
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                  } else{
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                  }
                               } else if( status_IN==0 && status_OUT!=0 ){
                                  if(status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ){
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                  } else{
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                  }
                               } else  {
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                               }
                           }
                           //update by satrya 2012-10-31
                           //untuk cek apakah ada leave
                           if(scheduleSymbol!=null && scheduleSymbol.getTimeIn()!=null){
                                dtScheduleIn = (Date) selectedDate.clone();
                                dtScheduleIn.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtScheduleIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtScheduleIn.setSeconds(0);
                           }
                           if(scheduleSymbol!=null && scheduleSymbol.getTimeOut()!=null){
                               dtScheduleOut = (Date)selectedDate.clone();
                               dtScheduleOut.setHours(scheduleSymbol.getTimeOut().getHours());
                               dtScheduleOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                               dtScheduleOut.setSeconds(0);
                               if(dtScheduleIn!=null && dtScheduleOut.getHours()< dtScheduleIn.getHours()){
                                   dtScheduleOut.setTime(dtScheduleOut.getTime()+ 24*60*60*1000);
                               }
                           }
                           Vector vListLeaveTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(employeeId,dtScheduleIn,dtScheduleOut);
                              //Vector vListLeaveTakenFinishDate = SessLeaveApp.checkDetailLeaveTakenDate(employeeId,selectedDate);
                           if(vListLeaveTakenFinishDate !=null && vListLeaveTakenFinishDate.size() >0){
                               Date timeIn = null;
                               Date timeOut = null;
                           
                               if(rs.getTime(4)!=null){ 
                                  timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4)); 
                                  /*dtScheduleIn = (Date) selectedDate.clone();
                                   dtScheduleIn.setHours(scheduleSymbol.getTimeIn().getHours());
                                   dtScheduleIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                   dtScheduleIn.setSeconds(0);*/
                               }
                               
                               if(rs.getDate(5)!=null){
                                   timeOut = DBHandler.convertDate(rs.getDate(5), rs.getTime(5));
                                   /*dtScheduleOut = (Date)selectedDate.clone();
                                   dtScheduleOut.setHours(scheduleSymbol.getTimeOut().getHours());
                                   dtScheduleOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                   dtScheduleOut.setSeconds(0);*/
                               }
                                 LeaveCheckTakenDateFinish leaveCheck = null;
                                 
                            if(cekLeave==0){///melakukan cek jika dia cekLeave=1 maka sistem akan melakukan set Statusnya menjadi absence <case di KTI>
                               for(int leaveCheckIdx=0; leaveCheckIdx < vListLeaveTakenFinishDate.size(); leaveCheckIdx++){
                                    leaveCheck = (LeaveCheckTakenDateFinish) vListLeaveTakenFinishDate.get(leaveCheckIdx);
                                    if(dtScheduleIn!=null && dtScheduleOut!=null && (leaveCheck.getTakenDate().getTime() <= dtScheduleIn.getTime()
                                            && leaveCheck.getFinishDate().getTime() >= (timeIn==null ? 0 :timeIn.getTime()))
                                            || (leaveCheck.getTakenDate().getTime() <= dtScheduleOut.getTime()
                                            && leaveCheck.getFinishDate().getTime() >= (timeOut==null ? 0 :timeOut.getTime()))){
                                        //intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK; 
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK; 
                                        cekCutiAda = false;
                                    }
                               }
                            }else{
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                 //intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE; 
                                        cekCutiAda = false;
                            }
                           }
                            if(cekCutiAda && (intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_OK ||  intFirstStatus ==  PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED)){
                                // analisa break in - out
                                //Vector vListLeavePermision  = SessEmpSchedule.listLeavePermision(employeeId, selectedDate, lDepartmentOid, periodId);///schedule ada Leave atau break
                                //Vector vPresenceBreak  = PstPresence.listPresenceBreakByEmpId(selectedDate, employeeId); ///untuk mencari presence
                                //update by satrya 2012-10-31
                                //if(vListLeavePermision == null){
                               
                                //untuk mencari cutinya
                               // vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(employeeId, fromDate,toDate);//SessEmpSchedule.listLeavePermision(employeeId, selectedDate, 0, periodId);///schedule ada Leave atau break
                               // }
                                //if(vPresenceBreak == null){
                               
                                //by priska config untuk menggunakan break in out atau tidak dalam analisa
                                     int ConfigBoutBin = 0;
                                try{
                                    ConfigBoutBin = Integer.valueOf(PstSystemProperty.getValueByName("WITHOUT_BOUT_BIN")); 
                                } catch (Exception e){
                                   System.out.printf("Tanpa Break Out / In"); 
                                }
                                if (ConfigBoutBin == 0){
                                vPresenceBreak  = PstPresence.listPresenceBreakByEmpId(selectedDate, employeeId); ///untuk mencari presence
                                }
                                
                                //}
                                if(vPresenceBreak !=null && vPresenceBreak.size() > 0){
                                    //mencari status BOut
                                     //mengambil list personalIn dan Out
                                     //update by satrya 2012-09-13
                                    //update by satrya 2013-06-11
                                    Date dtSchEmpScheduleBIn = null;
                                    Date dtSchEmpScheduleBOut = null;
                                    Date dtpresenceDateTime=null;
                                    long preBreakOut =0;
                                    long preBreakIn=0;
                                    Date dtBreakOut=null;
                                    Date dtBreakIn=null;
                                      boolean ispreBreakOutsdhdiambil = false;   
                                    if(vPresenceBreak!=null && vPresenceBreak.size()>0){
                                        for(int prIdx = 0;prIdx < vPresenceBreak.size();prIdx++){                                                
                                         Presence presenceBreak = (Presence) vPresenceBreak.get(prIdx);
                                            //update by satrya 2014-04-02
                                            //pengecekan jika presence schedule date time lebih dari 2 hari tidak sesuai dengan presence, kasusnya presence date tgl 9 february 2014 12:00, dan presence schedule datetime 7 february 2014 00:00:00
                                                if(presenceBreak.getScheduleDatetime()!=null && presenceBreak.getPresenceDatetime()!=null && (presenceBreak.getScheduleDatetime().getDate()<presenceBreak.getPresenceDatetime().getDate())){
                                                   long checkLebih = DateCalc.timeDifference(presenceBreak.getScheduleDatetime(),presenceBreak.getPresenceDatetime());
                                                   //pengecekan jika lebih dari 1 hari 15 jam maka itu tidak normal untuk schedule datetime
                                                   if(checkLebih> (1000 * 60 * 60 * 39)){
                                                       continue;
                                                   }
                                                }
                                            
                                            if(scheduleSymbol.getBreakOut()!=null&& selectedDate!=null){
                                               dtSchEmpScheduleBOut = (Date) selectedDate.clone();
                                               dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                                               dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                                               dtSchEmpScheduleBOut.setSeconds(0);
                                           }
                                           if(scheduleSymbol.getBreakIn()!=null && selectedDate!=null){
                                               dtSchEmpScheduleBIn = (Date) selectedDate.clone();
                                               dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                                               dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                                               dtSchEmpScheduleBIn.setSeconds(0);
                                             //jika dia ada cross days
                                              if(dtSchEmpScheduleBIn.getHours()<dtSchEmpScheduleBOut.getHours()){
                                                  dtSchEmpScheduleBIn = new Date(dtSchEmpScheduleBIn.getTime()+ 24*60*60*1000);
                                              }
                                           }
                                           if(presenceBreak.getPresenceDatetime() !=null){ 
                                                dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                                                dtpresenceDateTime.setSeconds(0);       
                                           }
                                            if(dtpresenceDateTime!=null && presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                                preBreakOut  = dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO 
                                                dtBreakOut = dtpresenceDateTime;
                                                ispreBreakOutsdhdiambil = false;
                                             }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                                preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                                dtBreakIn = presenceBreak.getPresenceDatetime();
                                                ispreBreakOutsdhdiambil = true;
                                             }
                                            if(ispreBreakOutsdhdiambil){
                                                  //cek da cuti
                                                Vector vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(employeeId, dtBreakOut,dtBreakIn);
                                                if(vLisOverlapCuti!=null && vLisOverlapCuti.size()>0){
                                                      for(int idxcuti=0; idxcuti< vLisOverlapCuti.size();idxcuti++){
                                                          LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish)vLisOverlapCuti.get(idxcuti);
                                                          if(leaveCheckTaken.getTakenDate()!=null 
                                                                  && preBreakOut < leaveCheckTaken.getTakenDate().getTime() 
                                                                  && preBreakOut < dtSchEmpScheduleBOut.getTime()){
                                                               status_BOUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;          
                                                          }else{
                                                             if(status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;                                                        
                                                             }
                                                          }
                                                          
                                                          if(dtSchEmpScheduleBIn!=null 
                                                                  && preBreakIn > leaveCheckTaken.getFinishDate().getTime()
                                                                  && preBreakIn > dtSchEmpScheduleBIn.getTime()){
                                                              
                                                                status_BIN = PstEmpSchedule.STATUS_PRESENCE_LATE; 
                                                          }else{
                                                              if(status_BIN != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BIN != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                    status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;                                                    
                                                              }
                                                          }
                                                      }
                                                }//end cek cuti
                                                else{
                                                    if(preBreakOut < dtSchEmpScheduleBOut.getTime()){
                                                          status_BOUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;   
                                                    }else{
                                                         if(status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;                                                        
                                                             }
                                                    }
                                                    if(preBreakIn > dtSchEmpScheduleBIn.getTime()){
                                                        status_BIN = PstEmpSchedule.STATUS_PRESENCE_LATE; 
                                                    }else{
                                                         if(status_BIN != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BIN != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                    status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;                                                    
                                                         }
                                                    }
                                                    
                                                    //update by satrya 2013-06-17
                                                    preBreakOut=0L;
                                                    preBreakIn=0L;
                                                }
                                            }
                                            
                                             
                                                
                                     }//end loop        
                                        //update by satrya 2013-06-17
                                            if(preBreakOut==0 || preBreakIn==0){
                                                //jika dia hanya ada break In atau break Out saja
                                                if(preBreakOut!=0){
                                                    if(preBreakOut < dtSchEmpScheduleBOut.getTime()){
                                                          status_BOUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;   
                                                    }else{
                                                         if(status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;                                                        
                                                             }
                                                    }
                                                }
                                                if(preBreakIn!=0){
                                                    if(preBreakIn > dtSchEmpScheduleBIn.getTime()){
                                                        status_BIN = PstEmpSchedule.STATUS_PRESENCE_LATE; 
                                                    }else{
                                                         if(status_BIN != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BIN != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                    status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;                                                    
                                                         }
                                                    }
                                                }
                                             }
                                    }
                                        if( status_BOUT!=0 || status_BIN!=0 ){
                                            if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED || status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED 
                                                    || status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ||   status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE){
                                                if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE){
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY;
                                                } else if(status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE &&  status_BOUT == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                                }//update by satrya 2012-11-13
                                                else if(status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED&&  status_BOUT == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                                }
                                                else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                                }//update by satrya 2012-11-13
                                                 else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                                }
                                                else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED){
                                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                                } else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE){
                                                     intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                                }                               
                                            }
                                        } /*else{
                                            if( status_BOUT!=0 && status_BIN==0 ){
                                               if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ){
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                               } else{
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                               }
                                            } else if( status_IN==0 && status_OUT!=0 ){
                                               if(status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ){
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                               } else{
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                               }
                                            } else  {
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                            }
                                        }*/
                                }
                               
                            }//end break
                           
                          }// end schedule normal
                        }   //end schedule category
                     
                      if (scheduleCategory == PstScheduleCategory.CATEGORY_OFF){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                     //schedule 6 jam kerja by priska
                    int Config6jamkerja = 0;
                                try{
                                    Config6jamkerja = Integer.valueOf(PstSystemProperty.getValueByName("CONFIG6JAMKERJA")); 
                                } catch (Exception e){
                                  // System.out.printf("6 jam kerja"); 
                                }
                        if (Config6jamkerja == 1 ){
                        if (rs.getDate(4) != null && rs.getDate(5) != null){
                            Date timeInN = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                            Date timeOutN = DBHandler.convertDate(rs.getDate(5), rs.getTime(5));

                            if (status_IN == PstEmpSchedule.STATUS_PRESENCE_OK && status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                               Date scheduleInN = rs.getTime(10);  
                                if ( timeInN.getTime() < scheduleInN.getTime() ){
                                    long lnTempo =  timeOutN.getTime() - scheduleInN.getTime(); // get the time in milli seconds
                                    long minimaljamkerja = 6 * 60 * 60000 ; 
                                    if (lnTempo > minimaljamkerja ){
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    } 
                                } else {
                                    long lnTempo =  timeOutN.getTime() - timeInN.getTime(); // get the time in milli seconds
                                    long minimaljamkerja = 6 * 60 * 60000 ; 
                                    if (lnTempo > minimaljamkerja ){
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }    
                                } 
                            }
                        }
                        }
                     //jika ada leave maka ok
                        //priska 20150513
                        if (dtScheduleIn != null && employeeId > 1){
                        long cekAl = PstLeaveApplication.getLeaveAlExecute(employeeId, dtScheduleIn);
                        if ((cekAl > 0) || ((oidAl.equals(""+rs.getLong(9))) && (!oidAl.equals("") ) ) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        long cekDp = PstLeaveApplication.getLeaveDpExecute(employeeId, dtScheduleIn);
                        if (cekDp > 0 || ((oidDp.equals(""+rs.getLong(9))) && (!oidDp.equals("") ) ) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        long cekSl = PstLeaveApplication.getLeaveSpecial(employeeId, dtScheduleIn);
                        if (cekSl > 0){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        
                         //priska menambahkan annual leave 2016-04-03
                       boolean isSpecialLeave = false;
                        try{
                            ScheduleSymbol scheduleSymbol = PstScheduleSymbol.fetchExc(rs.getLong(9));
                            if (oidSpecial.equals(""+scheduleSymbol.getScheduleCategoryId())){
                                isSpecialLeave = true;
                            }
                        }catch(Exception e){}
                        
                        if (isSpecialLeave) {
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        }
                        
                        
                        if (empPresenceOK == 1){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                       
                         //sementara untuk di borobudur
                        long scheduleID = rs.getLong(9);
                        if (scheduleID == 504404576464835128l){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                    
                        // update status schedule         
                        //priska 20150306
                        long reasonSelect = PstEmpSchedule.getReasonValue(periodId, employeeId, firstFieldIndexReason); 
                        String noteSelect = PstEmpSchedule.getNoteValue(periodId, employeeId, firstFieldIndexNote); 
                        int successUpd = 0 ;
                        
                        if (ConfigAutoOk == 1 && (reasonSelect != 0 || (noteSelect != null && !noteSelect.equals("") && !noteSelect.equals(" "))  ) && (reasonSelect != NOTDC && reasonSelect != intFirstReason) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                         if ( ConfigAutoOk == 1 && (noteSelect != null && !noteSelect.equals("") && !noteSelect.equals(" ") )){
                             intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                             intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        boolean cekUl = PstSpecialUnpaidLeaveTaken.UnpaidLeaveToday(employeeId, selectedDate);
                        if (cekUl){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                        } 
                        if (intFirstStatus != PstEmpSchedule.STATUS_PRESENCE_ABSENCE ){
                        intFirstReason = 0;
                        }
                        successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);
                         
                    
                        //update reason 20151020
                           int intreasonA =-1;
                            int intAutoReason =-1;
                        try{
                            try{
                            String sintreasonA = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                            intreasonA = Integer.parseInt(sintreasonA);
                            }catch(Exception ex){}
                            try{
                             String sinAutoReason = PstSystemProperty.getValueByName("SET_AUTO_REASON"); 
                            intAutoReason = Integer.parseInt(sinAutoReason);
                            }catch(Exception ex){}
                            long cekreason = PstEmpSchedule.getReasonValue(periodId, employeeId, firstFieldIndexReason);
                            if ((intFirstStatus != PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && ((!reasonValueIPC.equals(""+cekreason)) && (!reasonValueIDT.equals(""+cekreason))  ) ) {
                            
                               int upReason = LatenessAnalyser.updatePresenceReason(periodId, employeeId,firstFieldIndexReason ,0) ;
                            }  else if ((intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (intAutoReason == 1) ){
                               int upReason = LatenessAnalyser.updatePresenceReason(periodId, employeeId,firstFieldIndexReason ,intreasonA) ; 
                            }
                        }catch(Exception ex){
                             System.out.println("VALUE_REASON AL NOT Be SET"+ex);
                        }
                        
                    
                      //  int successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus,firstFieldIndexReason, intFirstReason );                                                  
                       // System.out.println("Status yang di hasilkan : employe ID:"+employeeId+"Periode:"+periodId+"Statusnya: "+intFirstStatus+"tanggal"+selectedDate);
                    } 
                    //System.out.println("Status yang di hasilkan : employe ID: " +employeeId+ " Periode: " +periodId+ " Statusnya: "+intFirstStatus+ " tanggal "+selectedDate);
                    //jika schedule tidak ada maka di set Ok
                    //else{
                        
                        /*intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        
                        LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);                                                  
                        */
                    //}
               // }    
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
    //priska menambhkan untuk analysis dari report daily
    public static void analyzePresencePerEmployeeByEmployeeIdReportDaily(Date selectedDate, long employeeId,int reason, String note) {
        Vector result = new Vector(1, 1);  
        if (selectedDate != null) 
        {
            DBResultSet dbrs = null;
            long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
            int selectedIndex = selectedDate.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
            
              //priska menambahkan update ke reason 23-12-2014
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int firstFieldIndexNote = PstEmpSchedule.OFFSET_INDEX_NOTE + (selectedIndex - 1);
            int intFirstReason =0;
            try{
                String sintFirstReason = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                intFirstReason = Integer.parseInt(sintFirstReason);
             }catch(Exception ex){
                 System.out.println("VALUE_B_REASON_SYMBOL NOT Be SET"+ex);
                 intFirstReason=0;
             }
        
            //priska 2015-03-09
            long propLateToleransi = -1;
            try {
                 propLateToleransi = Long.parseLong(PstSystemProperty.getValueByName("VALUE_LATE_TOLERANSI"));
            } catch (Exception ex) {
                System.out.println("Execption VALUE_LATE_TOLERANSI: " + ex);
            }
            //mecari time pada jam yang sudah diset
            long nilaiLateToleransi = propLateToleransi * 60 * -60000 ; 
            
                //update by priska 2015-03-05
          
           long NOTDC = 0;
           
           try{
                NOTDC = Integer.valueOf(PstSystemProperty.getValueByName("VALUE_NOTDC")); 
            } catch (Exception e){
               System.out.printf("VALUE_NOTDC TIDAK DI SET?"); 
            }
              
           String oidAl = String.valueOf(PstSystemProperty.getValueByName("OID_AL"));
           String oidLl = String.valueOf(PstSystemProperty.getValueByName("OID_LL"));
           String oidDp = String.valueOf(PstSystemProperty.getValueByName("OID_DP"));
           String oidUl = String.valueOf(PstSystemProperty.getValueByName("OID_UL"));
           String oidIMT = String.valueOf(PstSystemProperty.getValueByName("OID_IMT"));
           String oidIPC = String.valueOf(PstSystemProperty.getValueByName("OID_IPC"));
           String oidIDT = String.valueOf(PstSystemProperty.getValueByName("OID_IDT"));
           String oidChildBirth = String.valueOf(PstSystemProperty.getValueByName("OID_ChildBirth"));
           String oidSl = String.valueOf(PstSystemProperty.getValueByName("OID_SL"));
           String oidH4 = String.valueOf(PstSystemProperty.getValueByName("OID_H4"));
           String oidS = String.valueOf(PstSystemProperty.getValueByName("OID_S"));
           
           
           String oidSpecial = String.valueOf(PstSystemProperty.getValueByName("OID_SPECIAL"));
           String reasonValueIPC = String.valueOf(PstSystemProperty.getValueByName("REASON_VALUE_IPC"));
           String reasonValueIDT = String.valueOf(PstSystemProperty.getValueByName("REASON_VALUE_IDT"));
                
            int ConfigAutoOk =0;
            try{
                String sConfigAutoOk = PstSystemProperty.getValueByName("CONFIG_AUTO_OK"); 
                ConfigAutoOk = Integer.parseInt(sConfigAutoOk);
             }catch(Exception ex){
                 System.out.println("CONFIG_AUTO_OK NOT Be SET"+ex);

             }
            //di hidden karena pencarian berdasarkan schedulenya
            //Vector vListLeaveTakenFinishDate = SessLeaveApp.checkDetailLeaveTakenDate(employeeId,selectedDate);
            //update by satrya 2012-10-31 
            //Vector vLisOverlapCuti = null;
            Vector vPresenceBreak = null;
            Date dtSchedule = null;
            //Date dtScheduleDateTime = new Date();
             //update by satrya 2012-10-31
            Date dtScheduleIn = null;
            Date dtScheduleOut = null;
            dtSchedule = (Date)selectedDate.clone();
            /*dtSchedule.setDate(selectedDate.getDate());
            dtSchedule.setMonth(selectedDate.getMonth());
            dtSchedule.setYear(selectedDate.getYear());*/
            // long employeeId  = 0;
              int intFirstStatus =0;
           int cekLeave =0;
             try{
                 String sCekLeave = PstSystemProperty.getValueByName("LEAVE_FOR_ABSENCE"); 
                 cekLeave = Integer.parseInt(sCekLeave);
             }catch(Exception ex){
                 System.out.println("LEAVE_FOR_ABSENCE NOT Be SET"+ex);
                 cekLeave=0;
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
                //update by satrya 2012-09-13
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]+
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT] +
                ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN] +
                        //update by priska 2015-06-02
                ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PRESENCE_CHECK_PARAMETER] + 
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
                
                if(employeeId !=0){
                    sql = sql + " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                " = " + employeeId;
                }
                // mencari data masing-masing first "in-out" atau second "in-out" yang tidak null
                /*sql = sql + " AND" +
                            " (" +
                            " (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] + "))" +
                            " OR (NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + ")" +
                            " AND NOT ISNULL(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] + "))" +
                            " )" ; */               
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) 
                {
                   int empPresenceOK = rs.getInt("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PRESENCE_CHECK_PARAMETER]);
                
                     boolean cekCutiAda= true;// proses pengecekan cuti : true jika cuti harus di check, false jika ada cuti
                    // check schedule type, category holiday or on schedule
                    int scheduleCategory = rs.getInt(1);
                    employeeId = rs.getLong(3);
                    //update by satrya 2012-09-15
                    int status_IN=0;
                    int status_OUT=0;
                    int status_BOUT = 0;
                    int status_BIN = 0;
                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                    
                    
                    if (!(scheduleCategory == PstScheduleCategory.CATEGORY_OFF)){//jika schedulenya !=off maka sistem mengaggap dia ok
                     /* ini tidak mau jika schedulenya AL 
                      * if (!(scheduleCategory == PstScheduleCategory.CATEGORY_ABSENCE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_OFF
                            || scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                            || scheduleCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                            || scheduleCategory == PstScheduleCategory.CATEGORY_UNPAID_LEAVE)){*/
                        
                        // get symbol in
                        //SessEmployeeLateness.getDateRoster(presenceDate.getDate(), empschedule);
                        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                        scheduleSymbol.setOID(rs.getLong(9));//set Oid schedule symbol
                        scheduleSymbol.setTimeIn(rs.getTime(10));//set schedule Time In
                        scheduleSymbol.setTimeOut(rs.getTime(11)); //set schedule Time Out
                        scheduleSymbol.setBreakOut(rs.getTime(12)); //set schedule break out
                        scheduleSymbol.setBreakIn(rs.getTime(13));//set schedule Break in
                        
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
                                 
                                
                                dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                // waktu masuk
                                Date dtPresenceIn = new Date();
                                dtPresenceIn.setTime(timeIn.getTime());
                                dtPresenceIn.setSeconds(0);
                                
                                long iDuration = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                                /*iDuration = DateCalc.timeDifference(dtx, dt);                                

                                if (iDuration < 0) 
                                {
                                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                }*/
                                 iDuration = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                if (iDuration < nilaiLateToleransi) 
                                {
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } else{
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                }         
                            }
                            
                            if (!(rs.getDate(6) != null)) 
                            {
                                Date timeIn = DBHandler.convertDate(rs.getDate(6), rs.getTime(6));
                                Date dt = new Date();
                                long oidSymbol = rs.getLong(8);
                                //update by satrya 2012-09-13
                                if(scheduleSymbol!=null && scheduleSymbol.getOID()!=oidSymbol){
                                try
                                {
                                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSymbol);
                                }
                                catch(Exception e)
                                {
                                    System.out.println("Exc when fetch Schedule Symbol : " + e.toString());
                                }
                               }
                                Date cx = scheduleSymbol.getTimeIn();
                                if(cx.getHours()==0){
                                    cx.setHours(23);
                                    cx.setMinutes(59);
                                    cx.setSeconds(59);
                                    scheduleSymbol.setTimeIn(cx);
                                }
                                
                                /*dt.setDate(timeIn.getDate());
                                dt.setMonth(timeIn.getMonth());
                                dt.setYear(timeIn.getYear());
                                dt.setHours(scheduleSymbol.getTimeIn().getHours());
                                dt.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dt.setSeconds(0);*/
                                dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                // waktu masuk
                                Date dtPresenceIn = new Date();
                                dtPresenceIn.setTime(timeIn.getTime());
                                dtPresenceIn.setSeconds(0); 
                                
                                long iDuration = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                               // iDuration = DateCalc.timeDifference(dtx, dt);
                                /*if (iDuration < 0) 
                                {
                                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } */  
                               iDuration = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                if (iDuration <= nilaiLateToleransi) 
                                {
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } else{
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
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
                                 
                                
                                dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                // waktu masuk
                                Date dtPresenceIn = new Date();
                                dtPresenceIn.setTime(timeIn.getTime());
                                dtPresenceIn.setSeconds(0);  
                                
                                long iDurationIn = 0;
                                //long iDurationHour = 0;
                                //long iDurationMin = 0;
                                
                                
                                iDurationIn = DateCalc.timeDifference(dtPresenceIn, dtSchedule);
                                if (iDurationIn < nilaiLateToleransi) 
                                {
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_LATE;
                                } else{
                                    status_IN = PstEmpSchedule.STATUS_PRESENCE_OK;
                                }                                                                 
                            } 
                           //mencari status Out
                            //update by satrya 2012-09-13
                            if(rs.getDate(5) !=null){
                                Date timeOut = DBHandler.convertDate(rs.getDate(5), rs.getTime(5)); //mencari presence time Out
                               
                                /*Date timeOutNol = scheduleSymbol.getTimeOut();
                                if(timeOutNol.getHours()==0) //jika time Outnya jam 0
                                {
                                    timeOutNol.setHours(23);
                                    timeOutNol.setMinutes(59);
                                    timeOutNol.setSeconds(59);
                                    scheduleSymbol.setTimeOut(timeOutNol);
                                }*/
                                 
                                dtSchedule.setHours(scheduleSymbol.getTimeOut().getHours());
                                dtSchedule.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchedule.setSeconds(0);
                                
                                 if ( scheduleSymbol.getTimeOut().getHours() == 0 && scheduleSymbol.getTimeOut().getMinutes() == 0 && scheduleSymbol.getTimeOut().getSeconds() == 0 ) {
                                    dtSchedule.setHours(23);
                                    dtSchedule.setMinutes(59 - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(59);
                                 }
                                
                                // waktu masuk
                                Date dtPresenceOut = new Date();
                                dtPresenceOut.setTime(timeOut.getTime());
                                dtPresenceOut.setSeconds(0);  
                                
                                Date dtscheduleOutX = new Date();
                                    if (scheduleCategory == PstScheduleCategory.CATEGORY_ACCROSS_DAY){
                                        dtscheduleOutX = new Date(dtSchedule.getTime() + (24*60*60*1000));
                                    } else {
                                        dtscheduleOutX = dtSchedule;
                                    }
                                    
                                    long iDurationOut = 0;
                                    iDurationOut = DateCalc.timeDifference(dtPresenceOut, dtscheduleOutX);
                                    if (iDurationOut > 0) {
                                        status_OUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                    } else {
                                        status_OUT = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }   
                            }
                                                                                     
                                
                           if( status_IN!=0 && status_OUT!=0 ){
                               if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE ||  status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                   if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE &&  status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                       //min ceknya 15 menit
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY;
                                   } else if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE &&  status_OUT == PstEmpSchedule.STATUS_PRESENCE_OK){
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                   }  else if(status_IN == PstEmpSchedule.STATUS_PRESENCE_OK &&  status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                   }                               
                               } else{
                                    intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_OK;
                               }
                           } else{
                               if( status_IN!=0 && status_OUT==0 ){
                                  if(status_IN == PstEmpSchedule.STATUS_PRESENCE_LATE ){
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                  } else{
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                  }
                               } else if( status_IN==0 && status_OUT!=0 ){
                                  if(status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ){
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                  } else{
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                  }
                               } else  {
                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                               }
                           }
                           //update by satrya 2012-10-31
                           //untuk cek apakah ada leave
                           if(scheduleSymbol!=null && scheduleSymbol.getTimeIn()!=null){
                                dtScheduleIn = (Date) selectedDate.clone();
                                dtScheduleIn.setHours(scheduleSymbol.getTimeIn().getHours());
                                dtScheduleIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtScheduleIn.setSeconds(0);
                           }
                           if(scheduleSymbol!=null && scheduleSymbol.getTimeOut()!=null){
                               dtScheduleOut = (Date)selectedDate.clone();
                               dtScheduleOut.setHours(scheduleSymbol.getTimeOut().getHours());
                               dtScheduleOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                               dtScheduleOut.setSeconds(0);
                               if(dtScheduleIn!=null && dtScheduleOut.getHours()< dtScheduleIn.getHours()){
                                   dtScheduleOut.setTime(dtScheduleOut.getTime()+ 24*60*60*1000);
                               }
                           }
                           Vector vListLeaveTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(employeeId,dtScheduleIn,dtScheduleOut);
                              //Vector vListLeaveTakenFinishDate = SessLeaveApp.checkDetailLeaveTakenDate(employeeId,selectedDate);
                           if(vListLeaveTakenFinishDate !=null && vListLeaveTakenFinishDate.size() >0){
                               Date timeIn = null;
                               Date timeOut = null;
                           
                               if(rs.getTime(4)!=null){ 
                                  timeIn = DBHandler.convertDate(rs.getDate(4), rs.getTime(4)); 
                                  /*dtScheduleIn = (Date) selectedDate.clone();
                                   dtScheduleIn.setHours(scheduleSymbol.getTimeIn().getHours());
                                   dtScheduleIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                   dtScheduleIn.setSeconds(0);*/
                               }
                               
                               if(rs.getDate(5)!=null){
                                   timeOut = DBHandler.convertDate(rs.getDate(5), rs.getTime(5));
                                   /*dtScheduleOut = (Date)selectedDate.clone();
                                   dtScheduleOut.setHours(scheduleSymbol.getTimeOut().getHours());
                                   dtScheduleOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                   dtScheduleOut.setSeconds(0);*/
                               }
                                 LeaveCheckTakenDateFinish leaveCheck = null;
                                 
                            if(cekLeave==0){///melakukan cek jika dia cekLeave=1 maka sistem akan melakukan set Statusnya menjadi absence <case di KTI>
                               for(int leaveCheckIdx=0; leaveCheckIdx < vListLeaveTakenFinishDate.size(); leaveCheckIdx++){
                                    leaveCheck = (LeaveCheckTakenDateFinish) vListLeaveTakenFinishDate.get(leaveCheckIdx);
                                    if(dtScheduleIn!=null && dtScheduleOut!=null && (leaveCheck.getTakenDate().getTime() <= dtScheduleIn.getTime()
                                            && leaveCheck.getFinishDate().getTime() >= (timeIn==null ? 0 :timeIn.getTime()))
                                            || (leaveCheck.getTakenDate().getTime() <= dtScheduleOut.getTime()
                                            && leaveCheck.getFinishDate().getTime() >= (timeOut==null ? 0 :timeOut.getTime()))){
                                        //intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK; 
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK; 
                                        cekCutiAda = false;
                                    }
                               }
                            }else{
                                 //intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                        cekCutiAda = false;
                            }
                           }
                            if(cekCutiAda && (intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_OK ||  intFirstStatus ==  PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED)){
                                // analisa break in - out
                                //Vector vListLeavePermision  = SessEmpSchedule.listLeavePermision(employeeId, selectedDate, lDepartmentOid, periodId);///schedule ada Leave atau break
                                //Vector vPresenceBreak  = PstPresence.listPresenceBreakByEmpId(selectedDate, employeeId); ///untuk mencari presence
                                //update by satrya 2012-10-31
                                //if(vListLeavePermision == null){
                               
                                //untuk mencari cutinya
                               // vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(employeeId, fromDate,toDate);//SessEmpSchedule.listLeavePermision(employeeId, selectedDate, 0, periodId);///schedule ada Leave atau break
                               // }
                                //if(vPresenceBreak == null){
                                //by priska config untuk menggunakan break in out atau tidak dalam analisa
                                     int ConfigBoutBin = 0;
                                try{
                                    ConfigBoutBin = Integer.valueOf(PstSystemProperty.getValueByName("WITHOUT_BOUT_BIN")); 
                                } catch (Exception e){
                                   System.out.printf("Tanpa Break Out / In"); 
                                }
                                if (ConfigBoutBin == 0){
                                vPresenceBreak  = PstPresence.listPresenceBreakByEmpId(selectedDate, employeeId); ///untuk mencari presence
                                }
                                //}
                                if(vPresenceBreak !=null && vPresenceBreak.size() > 0){
                                    //mencari status BOut
                                     //mengambil list personalIn dan Out
                                     //update by satrya 2012-09-13
                                    //update by satrya 2013-06-11
                                    Date dtSchEmpScheduleBIn = null;
                                    Date dtSchEmpScheduleBOut = null;
                                    Date dtpresenceDateTime=null;
                                    long preBreakOut =0;
                                    long preBreakIn=0;
                                    Date dtBreakOut=null;
                                    Date dtBreakIn=null;
                                      boolean ispreBreakOutsdhdiambil = false;   
                                    if(vPresenceBreak!=null && vPresenceBreak.size()>0){
                                        for(int prIdx = 0;prIdx < vPresenceBreak.size();prIdx++){                                                
                                         Presence presenceBreak = (Presence) vPresenceBreak.get(prIdx);
                                            //update by satrya 2014-04-02
                                            //pengecekan jika presence schedule date time lebih dari 2 hari tidak sesuai dengan presence, kasusnya presence date tgl 9 february 2014 12:00, dan presence schedule datetime 7 february 2014 00:00:00
                                                if(presenceBreak.getScheduleDatetime()!=null && presenceBreak.getPresenceDatetime()!=null && (presenceBreak.getScheduleDatetime().getDate()<presenceBreak.getPresenceDatetime().getDate())){
                                                   long checkLebih = DateCalc.timeDifference(presenceBreak.getScheduleDatetime(),presenceBreak.getPresenceDatetime());
                                                   //pengecekan jika lebih dari 1 hari 15 jam maka itu tidak normal untuk schedule datetime
                                                   if(checkLebih> (1000 * 60 * 60 * 39)){
                                                       continue;
                                                   }
                                                }
                                            
                                            if(scheduleSymbol.getBreakOut()!=null&& selectedDate!=null){
                                               dtSchEmpScheduleBOut = (Date) selectedDate.clone();
                                               dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                                               dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                                               dtSchEmpScheduleBOut.setSeconds(0);
                                           }
                                           if(scheduleSymbol.getBreakIn()!=null && selectedDate!=null){
                                               dtSchEmpScheduleBIn = (Date) selectedDate.clone();
                                               dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                                               dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                                               dtSchEmpScheduleBIn.setSeconds(0);
                                             //jika dia ada cross days
                                              if(dtSchEmpScheduleBIn.getHours()<dtSchEmpScheduleBOut.getHours()){
                                                  dtSchEmpScheduleBIn = new Date(dtSchEmpScheduleBIn.getTime()+ 24*60*60*1000);
                                              }
                                           }
                                           if(presenceBreak.getPresenceDatetime() !=null){ 
                                                dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                                                dtpresenceDateTime.setSeconds(0);       
                                           }
                                            if(dtpresenceDateTime!=null && presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                                preBreakOut  = dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO 
                                                dtBreakOut = dtpresenceDateTime;
                                                ispreBreakOutsdhdiambil = false;
                                             }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                                preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                                dtBreakIn = presenceBreak.getPresenceDatetime();
                                                ispreBreakOutsdhdiambil = true;
                                             }
                                            if(ispreBreakOutsdhdiambil){
                                                  //cek da cuti
                                                Vector vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(employeeId, dtBreakOut,dtBreakIn);
                                                if(vLisOverlapCuti!=null && vLisOverlapCuti.size()>0){
                                                      for(int idxcuti=0; idxcuti< vLisOverlapCuti.size();idxcuti++){
                                                          LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish)vLisOverlapCuti.get(idxcuti);
                                                          if(leaveCheckTaken.getTakenDate()!=null 
                                                                  && preBreakOut < leaveCheckTaken.getTakenDate().getTime() 
                                                                  && preBreakOut < dtSchEmpScheduleBOut.getTime()){
                                                               status_BOUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;          
                                                          }else{
                                                             if(status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;                                                        
                                                             }
                                                          }
                                                          
                                                          if(dtSchEmpScheduleBIn!=null 
                                                                  && preBreakIn > leaveCheckTaken.getFinishDate().getTime()
                                                                  && preBreakIn > dtSchEmpScheduleBIn.getTime()){
                                                              
                                                                status_BIN = PstEmpSchedule.STATUS_PRESENCE_LATE; 
                                                          }else{
                                                              if(status_BIN != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BIN != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                    status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;                                                    
                                                              }
                                                          }
                                                      }
                                                }//end cek cuti
                                                else{
                                                    if(preBreakOut < dtSchEmpScheduleBOut.getTime()){
                                                          status_BOUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;   
                                                    }else{
                                                         if(status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;                                                        
                                                             }
                                                    }
                                                    if(preBreakIn > dtSchEmpScheduleBIn.getTime()){
                                                        status_BIN = PstEmpSchedule.STATUS_PRESENCE_LATE; 
                                                    }else{
                                                         if(status_BIN != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BIN != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                    status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;                                                    
                                                         }
                                                    }
                                                    
                                                    //update by satrya 2013-06-17
                                                    preBreakOut=0L;
                                                    preBreakIn=0L;
                                                }
                                            }
                                            
                                             
                                                
                                     }//end loop        
                                        //update by satrya 2013-06-17
                                            if(preBreakOut==0 || preBreakIn==0){
                                                //jika dia hanya ada break In atau break Out saja
                                                if(preBreakOut!=0){
                                                    if(preBreakOut < dtSchEmpScheduleBOut.getTime()){
                                                          status_BOUT = PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;   
                                                    }else{
                                                         if(status_BOUT != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BOUT != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                status_BOUT = PstEmpSchedule.STATUS_PRESENCE_OK;                                                        
                                                             }
                                                    }
                                                }
                                                if(preBreakIn!=0){
                                                    if(preBreakIn > dtSchEmpScheduleBIn.getTime()){
                                                        status_BIN = PstEmpSchedule.STATUS_PRESENCE_LATE; 
                                                    }else{
                                                         if(status_BIN != PstEmpSchedule.STATUS_PRESENCE_LATE && status_BIN != PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                                                                    status_BIN = PstEmpSchedule.STATUS_PRESENCE_OK;                                                    
                                                         }
                                                    }
                                                }
                                             }
                                    }
                                        if( status_BOUT!=0 || status_BIN!=0 ){
                                            if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED || status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED 
                                                    || status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ||   status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE){
                                                if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE){
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY;
                                                } else if(status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE &&  status_BOUT == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                                }//update by satrya 2012-11-13
                                                else if(status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED&&  status_BOUT == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                                }
                                                else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                                }//update by satrya 2012-11-13
                                                 else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_OK){
                                                 intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                                }
                                                else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED){
                                                      intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                                } else if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED &&  status_BIN == PstEmpSchedule.STATUS_PRESENCE_LATE){
                                                     intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                                }                               
                                            }
                                        } /*else{
                                            if( status_BOUT!=0 && status_BIN==0 ){
                                               if(status_BOUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ){
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_LATE;
                                               } else{
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_IN;
                                               }
                                            } else if( status_IN==0 && status_OUT!=0 ){
                                               if(status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME ){
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME;
                                               } else{
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT;
                                               }
                                            } else  {
                                                   intFirstStatus =  PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                                            }
                                        }*/
                                }
                               
                            }//end break
                           
                          }// end schedule normal
                        }   //end schedule category
                     
                     if (scheduleCategory == PstScheduleCategory.CATEGORY_OFF){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                    
                     //schedule 6 jam kerja by priska 20150508
                    int Config6jamkerja = 0;
                                try{
                                    Config6jamkerja = Integer.valueOf(PstSystemProperty.getValueByName("CONFIG6JAMKERJA")); 
                                } catch (Exception e){
                                 //  System.out.printf("6 jam kerja"); 
                                }
                        if (Config6jamkerja == 1 ){
                        if (rs.getDate(4) != null && rs.getDate(5) != null){
                            Date timeInN = DBHandler.convertDate(rs.getDate(4), rs.getTime(4));
                            Date timeOutN = DBHandler.convertDate(rs.getDate(5), rs.getTime(5));

                            if (status_IN == PstEmpSchedule.STATUS_PRESENCE_OK && status_OUT == PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME){
                               Date scheduleInN = rs.getTime(10);  
                                if ( timeInN.getTime() < scheduleInN.getTime() ){
                                    long lnTempo =  timeOutN.getTime() - scheduleInN.getTime(); // get the time in milli seconds
                                    long minimaljamkerja = 6 * 60 * 60000 ; 
                                    if (lnTempo > minimaljamkerja ){
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    } 
                                } else {
                                    long lnTempo =  timeOutN.getTime() - timeInN.getTime(); // get the time in milli seconds
                                    long minimaljamkerja = 6 * 60 * 60000 ; 
                                    if (lnTempo > minimaljamkerja ){
                                        intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                                    }    
                                } 
                            }
                        }
                        }
                        //jika ada leave maka ok
                        //priska 20150513
                        if (dtScheduleIn != null && employeeId > 1){
                        long cekAl = PstLeaveApplication.getLeaveAlExecute(employeeId, dtScheduleIn);
                        
                        if ((cekAl > 0) || ((oidAl.equals(""+rs.getLong(9))) && (!oidAl.equals("") ) ) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        
                        
                        long cekDp = PstLeaveApplication.getLeaveDpExecute(employeeId, dtScheduleIn);
                        if (cekDp > 0 || ((oidDp.equals(""+rs.getLong(9))) && (!oidDp.equals("") ) ) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                       
                        long cekSl = PstLeaveApplication.getLeaveSpecial(employeeId, dtScheduleIn);
                        if (cekSl > 0){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        
                        //priska menambahkan annual leave 2016-04-03
                       boolean isSpecialLeave = false;
                        try{
                            ScheduleSymbol scheduleSymbol = PstScheduleSymbol.fetchExc(rs.getLong(9));
                            if (oidSpecial.equals(""+scheduleSymbol.getScheduleCategoryId())){
                                isSpecialLeave = true;
                            }
                        }catch(Exception e){}
                        
                        if (isSpecialLeave) {
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        
                        }
                        
                        
                        if (empPresenceOK == 1){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                       
                        //sementara untuk di borobudur
                        long scheduleID = rs.getLong(9);
                        if (scheduleID == 504404576464835128l){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        
                        
                        // update status schedule         
                        //priska 20150306
                        long reasonSelect = PstEmpSchedule.getReasonValue(periodId, employeeId, firstFieldIndexReason); 
                        String noteSelect = PstEmpSchedule.getNoteValue(periodId, employeeId, firstFieldIndexNote); 
                        //int reasonSelect = reason; 
                        //String noteSelect = note;
                        int successUpd = 0 ;
                        
                        if (ConfigAutoOk == 1 && (reasonSelect != 0 || (noteSelect != null && !noteSelect.equals("") && !noteSelect.equals(" "))  ) && (reasonSelect != NOTDC && reasonSelect != intFirstReason) ){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                            intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        if ( ConfigAutoOk == 1 && (noteSelect != null && !noteSelect.equals("") && !noteSelect.equals(" ") )){
                             intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                             intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        }
                        boolean cekUl = PstSpecialUnpaidLeaveTaken.UnpaidLeaveToday(employeeId, selectedDate);
                        if (cekUl){
                            intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_ABSENCE;
                        }
                        if (intFirstStatus != PstEmpSchedule.STATUS_PRESENCE_ABSENCE ){
                        intFirstReason = 0;
                        }
                        successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);
                         
                        
//                        if ((ConfigAutoOk == 1 && (reasonSelect != 0 || (noteSelect != null && !noteSelect.equals("")) )) && (reasonSelect != NOTDC && reasonSelect != intFirstReason) ){
//                            //priska menambahkan kondisi jika absence
//                            if (intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE ){
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);
//                            } else {
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, intSecondStatus, firstFieldIndexReason, 0);
//                            }
//                        } else if ((ConfigAutoOk == 1 && (noteSelect != null && !noteSelect.equals("")))) {
//                            successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, PstEmpSchedule.STATUS_PRESENCE_OK, secondFieldIndex, intSecondStatus, firstFieldIndexReason, 0);
//                        } else {    
//                            if (intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE ){
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, intFirstReason);
//                            } else {
//                                successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, firstFieldIndexReason, 0);
//                            }     
//                        
//                        }
                        
                       //update reason 20151020
                         //update reason 20151020
                           int intreasonA =-1;
                           int intAutoReason = 0;
                        try{
                            try{
                            String sintreasonA = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                            intreasonA = Integer.parseInt(sintreasonA);
                            }catch(Exception ex){}
                            try{
                            String sinAutoReason = PstSystemProperty.getValueByName("SET_AUTO_REASON"); 
                            intAutoReason = Integer.parseInt(sinAutoReason);
                            }catch(Exception ex){}
                            long cekreason = PstEmpSchedule.getReasonValue(periodId, employeeId, firstFieldIndexReason);
                            if ((intFirstStatus != PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && ((!reasonValueIPC.equals(""+cekreason)) && (!reasonValueIDT.equals(""+cekreason))  ) ) {
                               int upReason = LatenessAnalyser.updatePresenceReason(periodId, employeeId,firstFieldIndexReason ,0) ;
                            }  else if ((intFirstStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (intAutoReason == 1) ){
                               int upReason = LatenessAnalyser.updatePresenceReason(periodId, employeeId,firstFieldIndexReason ,intreasonA) ; 
                            }
                        }catch(Exception ex){
                             System.out.println("VALUE_REASON AL NOT Be SET"+ex);
                        }
                       
                 
                    
                      //  int successUpd = LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus,firstFieldIndexReason, intFirstReason );                                                  
                       // System.out.println("Status yang di hasilkan : employe ID:"+employeeId+"Periode:"+periodId+"Statusnya: "+intFirstStatus+"tanggal"+selectedDate);
                    }
                    //System.out.println("Status yang di hasilkan : employe ID: " +employeeId+ " Periode: " +periodId+ " Statusnya: "+intFirstStatus+ " tanggal "+selectedDate);
                    //jika schedule tidak ada maka di set Ok
                    //else{
                        
                        /*intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;
                        
                        LatenessAnalyser.updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus);                                                  
                        */
                    //}
               // }    
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
    
    public static void main(String args[])
    {
        PresenceAnalyser objPresenceAnalyser = new PresenceAnalyser();
        objPresenceAnalyser.analyzeEmpPresenceData(0,0,"","");
        //update by satrya 2012-08-01
        // objPresenceAnalyser.analyzeEmpPresenceData(0,0,"");
    }
        
}
