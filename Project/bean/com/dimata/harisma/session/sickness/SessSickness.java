/*
 * SessSickness.java
 *
 * Created on June 17, 2004, 6:27 PM
 */
package com.dimata.harisma.session.sickness;

// package java core
import java.util.Date;
import java.util.Vector;
import java.util.GregorianCalendar;
import java.sql.*;

// package qdep 
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.util.CalendarCalc;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.system.entity.*;

// import harisma
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;

/**
 *
 * @author  gedhy
 */
public class SessSickness {
    private String empNum ;
    private String empFullName;
    
    /** 
     * get list absenteeism data daily
     * @param departmentId
     * @param selectedDate
     * @return
     * @created by Edhy
     */
    public static Vector listSicknessDataDaily(long departmentId, Date selectedDate) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName - 1] +
                    " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] +
                    " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                    " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                    " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " AND ((SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                    " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1] +
                    " = " + PstEmpSchedule.REASON_ABSENCE_SICKNESS + ") " +
                    " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                    " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                    " = " + PstEmpSchedule.REASON_ABSENCE_SICKNESS + "))" +
                    " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                    " NOT IN (" + PstScheduleCategory.CATEGORY_ABSENCE +
                    "," + PstScheduleCategory.CATEGORY_OFF +
                    "," + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT +
                    "," + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE +
                    "," + PstScheduleCategory.CATEGORY_LONG_LEAVE + ")" +
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //System.out.println("\tlistSicknessDataDaily : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            while (rs.next()) {
                SicknessDaily sicknessDaily = new SicknessDaily();

                sicknessDaily.setEmpNum(rs.getString(1));
                sicknessDaily.setEmpName(rs.getString(2));
                sicknessDaily.setSchldSymbol(rs.getString(3));
                sicknessDaily.setSchldIn(rs.getTime(4));
                sicknessDaily.setSchldOut(rs.getTime(5));
                sicknessDaily.setRemark(rs.getString(6));

                result.add(sicknessDaily);
            }
        } catch (Exception e) {
            System.out.println("listSicknessDataDaily exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /** 
     * get list absenteeism data daily
     * @param departmentId
     * @param selectedDate
     * @return
     * @created by Edhy
     */
    //update by satrya 2012-10-25
    public static Vector listSicknessDataDaily(long oidSickLeave,long oidSickLeaveWoDC,long departmentId, Date selectedDate, long sectionId, String empNumb, String fullName,int start,int recordToGet, String whereReasonList) {
        // public static Vector listSicknessDataDaily(long departmentId, Date selectedDate, long sectionId) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        int CSTD = 4;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        try {
           // String whereClause = "";
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName - 1] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] +
                    " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                    " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                    " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId;


            if (oidSickLeave != 0) {
                String strX = " OR ((SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] + " = '" + oidSickLeaveWoDC + "') " +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] + " = '" + oidSickLeaveWoDC + "') ";

                sql = sql + " AND ((SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] +
                        " = '" + oidSickLeave + "') OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] + " = '" + oidSickLeave + "') " +
                        (oidSickLeaveWoDC == 0 ? "" : strX) + ") ";
                        if(whereReasonList!=null && whereReasonList.length()>0){
                        sql = sql + "OR  (SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]+" "+whereReasonList+"))";
                        }else{
                            sql = sql + ")";
                        }
                        //OR  (SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]+" = "+1+")";hgjhgj

            } else {
                sql = sql + " AND ((SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1] +
                        " = " + PstEmpSchedule.REASON_ABSENCE_SICKNESS + ") " +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + PstEmpSchedule.REASON_ABSENCE_SICKNESS + ")" +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1] +
                        " = " + CSTD + ")" +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + CSTD + "))" +
                        " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                        " NOT IN (" + PstScheduleCategory.CATEGORY_ABSENCE +
                        "," + PstScheduleCategory.CATEGORY_OFF +
                        "," + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT +
                        "," + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE +
                        "," + PstScheduleCategory.CATEGORY_LONG_LEAVE + ")";
            }
            if (departmentId != 0) {
                sql = sql + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                        " = " + departmentId;
            }
            if (sectionId != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + sectionId;

            }
               //update by satrya 2012-07-30

            if (empNumb != null && empNumb.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                         + " LIKE " + "\"%" + empNumb + "%\"";
            }
            //update by satrya 2012-07-16
            if (fullName !=null && fullName.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + " LIKE " + "\"%" + fullName + "%\"";
            }
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
           if (start == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }
           // System.out.println("\tlistSicknessDataDaily : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            while (rs.next()) {
                SicknessDaily sicknessDaily = new SicknessDaily();

                sicknessDaily.setEmpNum(rs.getString(1));
                sicknessDaily.setEmpName(rs.getString(2));
                sicknessDaily.setSchldSymbol(rs.getString(3));
                sicknessDaily.setSchldIn(rs.getTime(4));
                sicknessDaily.setSchldOut(rs.getTime(5));
                sicknessDaily.setRemark(rs.getString(6));
                sicknessDaily.setReason(rs.getInt(7));
                sicknessDaily.setScheduleID(rs.getLong(8));
                sicknessDaily.setSelectedDate(selectedDate);
                result.add(sicknessDaily);
                
            }
        } catch (Exception e) {
            System.out.println("listSicknessDataDaily exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    //update by satrya 2012-10-26
    /**
     * Keterangan : mencari count sicness
     * @param oidDepartment
     * @param selectedDateFrom
     * @param selectedDateTo
     * @param oidSection
     * @param empNum
     * @param fullName
     * @return 
     */
       public static int getCountSessSickness(long sOidDc,long sOidNDC,long oidDepartment,Date selectedDateFrom,Date selectedDateTo,long oidSection,String empNum,String fullName,String whareClause) {
        int count=0;
           if (selectedDateFrom != null && selectedDateTo != null) {
            if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
             long diffStartToFinish = selectedDateTo.getTime() - selectedDateFrom.getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
            for (int i = 0; i <= itDate; i++) {
                Date selectedDate = new Date(selectedDateFrom.getTime() + i * 1000L * 60 * 60 * 24);
                 /*int temCount = countEmpSickness(sOidDc, sOidNDC, oidDepartment, selectedDate, oidSection, empNum, fullName, whareClause);
                 if(temCount !=0){
                 count = count+ temCount;*/
                 
                  int icount = vCountSicknessDataDailyForPdf(sOidDc, sOidNDC, oidDepartment, selectedDate, oidSection, empNum, fullName, whareClause);
               count = count + icount;
            }
          
    }
           return count;
  }
  

       /**
        *  //Keterangan : mencari count sickness
        * @param sOidDc
        * @param sOidNDC
        * @param oidDepartment
        * @param selectedDate
        * @param oidSection
        * @param empNum
        * @param fullName
        * @param whareClause
        * @return 
        */
 /*public static int countEmpSickness(long sOidDc, long sOidNDC, long oidDepartment, Date selectedDate, long oidSection,String empNum, String fullName,String  whereClause){ 
 
      int count = 0; 
        DBResultSet dbrs = null;
        int CSTD = 4;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        try {
            //String whereClause = "";
            String sql = "SELECT COUNT(EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +")"+
                    " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] +
                    " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                    " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                    " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId;


            if (sOidDc != 0) {
                String strX = " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] + " = '" + sOidNDC + "') " +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] + " = '" + sOidNDC + "') ";
  
                sql = sql + " AND ((SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] +
                        " = '" + sOidDc + "') OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] + " = '" + sOidDc + "') " +
                        (sOidNDC == 0 ? "" : strX) + ") ";
                        if(whereClause!=null && whereClause.length()>0){
                        sql = sql + "OR  (SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]+" "+whereClause+")";
                        }else{
                            sql = sql + ")";
                        }
            } else {
                sql = sql + " AND ((SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1] +
                        " = " + PstEmpSchedule.REASON_ABSENCE_SICKNESS + ") " +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + PstEmpSchedule.REASON_ABSENCE_SICKNESS + ")" +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1] +
                        " = " + CSTD + ")" +
                        " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                        " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                        " = " + CSTD + "))" +
                        " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                        " NOT IN (" + PstScheduleCategory.CATEGORY_ABSENCE +
                        "," + PstScheduleCategory.CATEGORY_OFF +
                        "," + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT +
                        "," + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE +
                        "," + PstScheduleCategory.CATEGORY_LONG_LEAVE + ")";
            }
            if (oidDepartment != 0) {
                whereClause = whereClause + " DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                        " = " + oidDepartment + " AND ";
            }
            if (oidSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + oidSection + " AND ";

            }
               //update by satrya 2012-07-30

            if (empNum != null && empNum.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                         + " LIKE " + "\"%" + empNum + "%\"";
            }
            //update by satrya 2012-07-16
            if (fullName !=null && fullName.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + " LIKE " + "\"%" + fullName + "%\"";
            }
                   // System.out.println("countPresence Daily SQL = " + sql);
                    dbrs = DBHandler.execQueryResult(sql);
                   //System.out.println(sql);
                    ResultSet rs = dbrs.getResultSet();

                    while (rs.next()) {
                        count = rs.getInt(1);

                    }
                    rs.close();                   
                } catch (Exception e) {
                    System.out.println("[ERROR] " + e);
                } finally {
                    DBResultSet.close(dbrs);
                }
                return count;
}
   */
/**
 * Keterangan: fungsi ini di pakai di daily sickness.jsp
 * @param departmentId
 * @param fromDate
 * @param toDate
 * @param sectionId
 * @param empNumb
 * @param fullName
 * @return 
 */
     public static Vector listSicknessDataDaily(long oIdSickDC,long oIdSickNDC,int iSickDc,int iSickNDC,long departmentId, Date selectedDateFrom,Date selectedDateTo, long sectionId, String empNumb, String fullName,int limitStart,int recordToGet,String whereClause) {
       Vector result = new Vector();

        if (selectedDateFrom != null && selectedDateTo != null) {
            if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
            long diffStartToFinish = selectedDateTo.getTime() - selectedDateFrom.getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
              int countX =0;
            int limitStartX=limitStart, recordToGetX=recordToGet; 
            int recordGot =0;
           // int tempCount=0;
            for (int i = 0; i <= itDate; i++) {
                Date selectedDate = new Date(selectedDateFrom.getTime() + i * 1000L * 60 * 60 * 24);//Date(getFromDate().getYear(), getFromDate().getMonth(), (getFromDate().getDate() + i));
                
                int countbyDate = vCountSicknessDataDailyForPdf(oIdSickDC, oIdSickNDC, departmentId, selectedDate, sectionId, empNumb, fullName, whereClause);
                //int countbyDate = countEmpSickness(oIdSickDC, oIdSickNDC, departmentId, selectedDate, sectionId, empNumb, fullName, whereClause);
                //int countbyDate = countEmpPresenceDaily(departmentId, selectedDate, sectionId, empNumId, fullName, limitStartX, recordToGetX);
              if(i != itDate){ 
                if(limitStartX > countbyDate){
                 limitStartX = limitStartX - countbyDate;
                   continue;
                }
              }else{
                   if(limitStartX > countbyDate){
                 limitStartX = limitStartX + countbyDate;
                  // continue;
                }
              }
              // limitStartX =  tempCount  - limitStartX;
               /*  if(limitStartX > countbyDate){
                 limitStartX = limitStartX - countbyDate;
                   continue;
                }*/
                Vector dSicness = listSicknessDataDaily(oIdSickNDC, oIdSickNDC, departmentId, selectedDate, sectionId, empNumb, fullName, limitStartX, recordToGetX, whereClause);
                if (dSicness != null && dSicness.size() > 0) {
                    result.addAll(dSicness);
                    countX = dSicness.size();
                    recordGot = recordGot  + countX;
                    if(recordGot>= recordToGet){
                        return result; //// jika jumlah yg didapat sudah = jumlah yang harus di dapat berhenti dan return
                    }
                    
                    if(countX< recordToGetX){
                        limitStartX =0;
                        recordToGetX = recordToGetX - countX;
                    }                                 
                }
            }
                
               
               /* Vector dSicness = listSicknessDataDaily(departmentId, selectedDate, sectionId, empNumb, fullName,whereReasonList,start,recordToGet);
                 
                if (dSicness != null && dSicness.size() > 0) { 
                result.addAll(dSicness);
                 }
            }*/
        }
        return result;
     }
     /**
      * Keterangan : untuk report Pdf
      * @param departmentId
      * @param fromDate
      * @param toDate
      * @param sectionId
      * @param empNumb
      * @param fullName
      * @param limitStart
      * @param recordToGet
      * @return 
      */
public static Vector listSicknessDataDailyForPdf(long departmentId, Date fromDate,Date toDate, long sectionId, String empNumb, String fullName,int limitStart,int recordToGet) {
            Vector result = new Vector();
            int iSickDc = -1;
int iSickNDC=-1;
long oidSickLeave = 0;
long oidSickLeaveWoDC = 0;
 String sISickWDC = PstSystemProperty.getValueByName("SICK_REASON_WITH_DC");
String sISickWoDC = PstSystemProperty.getValueByName("SICK_REASON_NOT_DC"); 

  String sOIDSickLeave = PstSystemProperty.getValueByName("OID_SICK_LEAVE");
  String sOIDSickLeaveWoDC = PstSystemProperty.getValueByName("OID_SICK_LEAVE_WO_DC"); 
                
try {
    if( (sISickWDC!=null) && (sISickWDC.length()>0)) {
        iSickDc = Integer.parseInt(sISickWDC);
    }
     if( (sISickWoDC!=null) && (sISickWoDC.length()>0)) {
        iSickNDC = Integer.parseInt(sISickWoDC);
    }
      if( (sOIDSickLeave!=null) && (sOIDSickLeave.length()>0)) {
                        oidSickLeave = Long.parseLong(sOIDSickLeave);
    }
    if( (sOIDSickLeaveWoDC!=null) && (sOIDSickLeaveWoDC.length()>0)) {
        oidSickLeaveWoDC = Long.parseLong(sOIDSickLeaveWoDC);
    }
}catch(Exception exc){
    System.out.println("===> NOT SICK LEAVE OID DEFINED => USE ABSENCE MANAGEMENT AND SCHEDULE STATUS AS SICKNESS REPORT");
}
           String whereReasonList ="IN("+iSickDc+","+iSickNDC+")";
           /* String whereReason = PstReason.fieldNames[PstReason.FLD_SCHEDULE_ID]+" BETWEEN "+sOIDSickLeave+" AND "+sOIDSickLeaveWoDC; 
              Vector listReason = new Vector();
                try {
                    listReason = PstReason.list(0, 0, whereReason, null);
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }

            if(listReason !=null && listReason.size() > 0){ 
                 whereReasonList = " IN (";
                   for(int ix=0 ; ix < listReason.size();ix++){
                    reason = (Reason) listReason.get(ix);     
                    whereReasonList = whereReasonList  + "  " + reason.getNo()+ ( ix== listReason.size()-1?")":",");
                  }
            }*/     
        if (fromDate != null && toDate != null) {
            if (fromDate.getTime() > toDate.getTime()) {
                Date tempFromDate = fromDate;
                Date tempToDate = toDate;
                fromDate = tempToDate;
                toDate = tempFromDate;
            }
            long diffStartToFinish = toDate.getTime() - fromDate.getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
            for (int i = 0; i <= itDate; i++) {
                Date selectedDate = new Date(fromDate.getTime() + i * 1000L * 60 * 60 * 24);//Date(getFromDate().getYear(), getFromDate().getMonth(), (getFromDate().getDate() + i));
                    Vector dSicness = listSicknessDataDaily(oidSickLeave, oidSickLeaveWoDC, departmentId, selectedDate, sectionId, empNumb, fullName, 0, 0, whereReasonList);
                 
                if (dSicness != null && dSicness.size() > 0) { 
                    result.addAll(dSicness);
                 }
            }   
        }
        return result;
     }
 
//update by satrya 2012-10-29
//count dari vecktor
/**
 * Untuk mencari count vector
 * terserah bisa di hapus
 */
public static int vCountSicknessDataDailyForPdf(long sOidDc, long sOidNDC,long  oidDepartment,Date selectedDate, long oidSection,String empNum, String fullName,String whareClause) {
            Vector result = new Vector();
            int count=0;
          
           /* String whereReason = PstReason.fieldNames[PstReason.FLD_SCHEDULE_ID]+" BETWEEN "+sOIDSickLeave+" AND "+sOIDSickLeaveWoDC; 
              Vector listReason = new Vector();
                try {
                    listReason = PstReason.list(0, 0, whereReason, null);
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }

            if(listReason !=null && listReason.size() > 0){ 
                 whereReasonList = " IN (";
                   for(int ix=0 ; ix < listReason.size();ix++){
                    reason = (Reason) listReason.get(ix);     
                    whereReasonList = whereReasonList  + "  " + reason.getNo()+ ( ix== listReason.size()-1?")":",");
                  }
            }*/     
        if (selectedDate != null) {
                    Vector dSicness = listSicknessDataDaily(sOidDc, sOidNDC, oidDepartment, selectedDate, oidSection, empNum, fullName, 0, 0, whareClause);
                 
                if (dSicness != null && dSicness.size() > 0) { 
                    result.addAll(dSicness);
                 }
            //}   
            count = result.size();
        }
        return count;
     }

/** 
     * get list absenteeism data weekly
     * @param selectedMonth
     * @param weekIndex
     * @param departmentId
     * @return
     * @created by Edhy
     */
    public static Vector listSicknessDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get start and end date of selected week of selected month
        CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);
        Date startDateWeek = objCalendarCalc.getStartDateOfTheWeek(selectedMonth, weekIndex);
        Date endDateWeek = objCalendarCalc.getEndDateOfTheWeek(selectedMonth, weekIndex);
        int intStartDate = startDateWeek.getDate();
        int intEndDate = endDateWeek.getDate();

        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // schedule
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)];
            }

            // actual status
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)];
            }

            // actual reason
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i - 1)];
            }

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistSicknessDataWeekly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SicknessWeekly sicknessWeekly = new SicknessWeekly();

                sicknessWeekly.setEmpNum(rs.getString(1));
                sicknessWeekly.setEmpName(rs.getString(2));

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)])));
                }
                sicknessWeekly.setEmpSchedules(vectSchedule);

                // values status
                Vector vectStatus = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)])));
                }
                sicknessWeekly.setAbsStatus(vectStatus);


                // values reason
                Vector vectReason = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i - 1)])));
                }
                sicknessWeekly.setAbsReason(vectReason);

                result.add(sicknessWeekly);
            }
        } catch (Exception e) {
            System.out.println("listSicknessDataWeekly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /** 
     * get list absenteeism data weekly
     * @param selectedMonth
     * @param weekIndex
     * @param departmentId
     * @return
     * @created by Yunny
     * Untuk Intimas
     */
    public static Vector listSicknessDataWeeklyInt(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex, String empNumb,String fullName) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get start and end date of selected week of selected month
        CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);
        Date startDateWeek = objCalendarCalc.getStartDateOfTheWeek(selectedMonth, weekIndex);
        Date endDateWeek = objCalendarCalc.getEndDateOfTheWeek(selectedMonth, weekIndex);
        int intStartDate = startDateWeek.getDate();
        int intEndDate = endDateWeek.getDate();

        // get periodId of selected date
        //long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(startDateWeek);
      
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // schedule
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)];
            }

            // actual status
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)];
            }

            // actual reason
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i - 1)];
            }

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId;
            if(departmentId !=0){
                   sql = sql + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId;
            }       
            //update by satrya 2012-07-30
            if (empNumb != null && empNumb.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                         + " LIKE " + "\"%" + empNumb + "%\"";
            }
            //update by satrya 2012-07-16
            if (fullName !=null && fullName.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + " LIKE " + "\"%" + fullName + "%\"";
            }
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            //System.out.println("\tlistSicknessDataWeekly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SicknessWeekly sicknessWeekly = new SicknessWeekly();

                sicknessWeekly.setEmpNum(rs.getString(1));
                sicknessWeekly.setEmpName(rs.getString(2));

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)])));
                }
                sicknessWeekly.setEmpSchedules(vectSchedule);

                // values status
                Vector vectStatus = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)])));
                }
                sicknessWeekly.setAbsStatus(vectStatus);


                // values reason
                Vector vectReason = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i - 1)])));
                }
                sicknessWeekly.setAbsReason(vectReason);

                result.add(sicknessWeekly);
            }
        } catch (Exception e) {
            System.out.println("listSicknessDataWeekly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /** 
     * get list absenteeism data monthly          
     * @param <CODE>departmentId</CODE>selected department
     * @param <CODE>selectedMonth</CODE>selected month     
     * @created by Edhy
     */
    public static Vector listSicknessDataMonthly(long departmentId, Date selectedMonth) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get maximum date on selected month        
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear() + 1900, selectedMonth.getMonth(), 1);
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // fields schedule 
            for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];
            }

            // fields status
            for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i];
            }

            // fields reason
            for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i];
            }

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistSicknessDataMonthly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SicknessMonthly sicknessMonthly = new SicknessMonthly();

                sicknessMonthly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                sicknessMonthly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                sicknessMonthly.setEmpSchedules(vectSchedule);

                // values status
                Vector vectStatus = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i])));
                }
                sicknessMonthly.setPresenceStatus(vectStatus);

                // values reason
                Vector vectReason = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i])));
                }
                sicknessMonthly.setAbsReason(vectReason);

                result.add(sicknessMonthly);
            }
        } catch (Exception e) {
            System.out.println("listSicknessDataMonthly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /** 
     * get list absenteeism data monthly          
     * @param <CODE>departmentId</CODE>selected department
     * @param <CODE>selectedMonth</CODE>selected month     
     * @created by Edhy
     */
    public static Vector listSicknessDataMonthly(long departmentId, Date selectedMonth, long sectionId, String empNumb,String fullName,String sOIDSickLeave,String sOIDSickLeaveWoDC,String sISickWDC,String sISickWoDC) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get maximum date on selected month        
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear() + 1900, selectedMonth.getMonth(), 1);
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);
        
        try {
            String whereClause = "";
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // fields schedule 
            for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];
            }

            // fields status
            for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i];
            }

            // fields reason
            for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i];
            }

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId ;

            if (departmentId != 0) {
                whereClause = whereClause + " DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                        " = " + departmentId + " AND ";
            }
            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + sectionId + " AND ";

            }
            //update by satrya 2012-07-30
            if (empNumb != null && empNumb.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                         + " LIKE " + "\"%" + empNumb + "%\"";
            }
            //update by satrya 2012-07-16
            if (fullName !=null && fullName.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + " LIKE " + "\"%" + fullName + "%\"";
            }  
            String sql2 = "";
            String sql3 = "";
            String sql4="";
            String sql5="";
                for (int i = 0; i <maxDayOnSelectedMonth; i++) 
                    {
                        
                        if(sql2.length()==0)
                        {
                           //if()
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i]+"="+"\""+sOIDSickLeave+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sOIDSickLeave+"\""+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i]+"="+"\""+sOIDSickLeave+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR +  PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sOIDSickLeave+"\""+")";
                        }
                        
                        if(sql3.length()==0)
                        {
                            sql3 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i]+"="+"\""+sOIDSickLeaveWoDC+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sOIDSickLeaveWoDC+"\""+")";
                        }
                        else
                        {
                            sql3 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i]+"="+"\""+sOIDSickLeaveWoDC+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR +  PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sOIDSickLeaveWoDC+"\""+")";
                        }
                         if(sql4.length()==0)
                        {
                            sql4 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i]+"="+"\""+sISickWDC+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sISickWDC+"\""+")";
                        }
                        else
                        {
                            sql4 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i]+"="+"\""+sISickWDC+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON +  PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sISickWDC+"\""+")";
                        }
                          if(sql5.length()==0)
                        {
                            sql5 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i]+"="+"\""+sISickWoDC+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sISickWoDC+"\""+")";
                        }
                        else
                        {
                            sql5 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i]+"="+"\""+sISickWoDC+"\""+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON +  PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+"\""+sISickWoDC+"\""+")";
                        }
                    }
                    sql2 = " AND ((("+sql2+")";
                     sql3 = " OR ("+sql3+"))";
                     sql4 = " OR (("+sql4+")";
                     sql5 = " OR ("+sql5+")))";
                    sql += sql2 + sql3 + sql4 +sql5 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

          //  sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

          //  System.out.println("\tlistSicknessDataMonthly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SicknessMonthly sicknessMonthly = new SicknessMonthly();

                sicknessMonthly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                sicknessMonthly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                sicknessMonthly.setEmpSchedules(vectSchedule);

                // values status
                Vector vectStatus = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i])));
                }
                sicknessMonthly.setPresenceStatus(vectStatus);

                // values reason
                Vector vectReason = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i])));
                }
                sicknessMonthly.setAbsReason(vectReason);

                result.add(sicknessMonthly);
            }
        } catch (Exception e) {
            System.out.println("listSicknessDataMonthly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
       
   public static Vector listSicknessDataMonthly(long departmentId, Date selectedMonth, long sectionId, String empNumb,String fullName) {
     return listSicknessDataMonthly(departmentId,selectedMonth,sectionId,empNumb,fullName, "","","", "");
 }
    /**
     * get status if vectSchedule contains night shift schedule
     * @param vectSchedule
     * @return
     * @created by Edhy
     */
    public static boolean containSchldNotOff(Vector vectSchedule) {
        boolean result = false;

        String listSchedule = "";
        if (vectSchedule != null && vectSchedule.size() > 0) {
            int maxSchld = vectSchedule.size();
            for (int i = 0; i < maxSchld; i++) {
                listSchedule += String.valueOf(vectSchedule.get(i)) + ",";
            }

            if (listSchedule != null && listSchedule.length() > 0) {
                listSchedule = listSchedule.substring(0, listSchedule.length() - 1);

                DBResultSet dbrs = null;
                try {
                    String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                            " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                            " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                            " ON CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                            " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                            " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                            " IN ( " + listSchedule + ")";

                    //System.out.println("\tcontainSchldNightShift : "+sql);
                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    while (rs.next()) {
                        if (!(rs.getInt(1) == PstScheduleCategory.CATEGORY_ABSENCE || rs.getInt(1) == PstScheduleCategory.CATEGORY_OFF || rs.getInt(1) == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || rs.getInt(1) == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || rs.getInt(1) == PstScheduleCategory.CATEGORY_LONG_LEAVE)) {
                            result = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("containSchldNotOff exc : " + e.toString());
                } finally {
                    DBResultSet.close(dbrs);
                    return result;
                }
            } else {
                return result;
            }
        }
        return result;
    }

    // added by Bayu    
    public static Vector listZeroSicknessOld(long departmentId, long sectionId, Date startDate, Date endDate) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        String start = (startDate.getYear() + 1900) + "-" + (startDate.getMonth() + 1) + "-" + startDate.getDate();
        String end = (endDate.getYear() + 1900) + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate();

        try {
            StringBuffer sql = new StringBuffer();

            sql.append(" SELECT DISTINCT EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).append(",");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).append(",");
            sql.append(" DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]).append(",");
            sql.append(" SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION]);
            sql.append(" FROM ").append(PstEmployee.TBL_HR_EMPLOYEE).append(" EMP");
            sql.append(" INNER JOIN ").append(PstDepartment.TBL_HR_DEPARTMENT).append(" DEPT");
            sql.append(" ON EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]);
            sql.append(" = DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            sql.append(" INNER JOIN ").append(PstSection.TBL_HR_SECTION).append(" SEC");
            sql.append(" ON EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]);
            sql.append(" = SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION_ID]);
            sql.append(" LEFT JOIN ").append(PstLeave.TBL_HR_LEAVE).append(" LV");
            sql.append(" ON EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
            sql.append(" = LV.").append(PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]);
            sql.append(" WHERE (");
            sql.append(PstLeave.fieldNames[PstLeave.FLD_LEAVE_ID]).append(" IS NULL");
            sql.append(" OR ");
            sql.append("  (").append(PstLeave.fieldNames[PstLeave.FLD_LEAVE_FROM]);
            sql.append("  BETWEEN '").append(start).append("' AND '").append(end).append("'");
            sql.append("  OR ").append(PstLeave.fieldNames[PstLeave.FLD_LEAVE_TO]);
            sql.append("  BETWEEN '").append(start).append("' AND '").append(end).append("')");
            sql.append("  AND ").append(PstLeave.fieldNames[PstLeave.FLD_SICK_LEAVE]).append(" IS NULL");
            sql.append(" OR ");
            sql.append("  (").append(PstLeave.fieldNames[PstLeave.FLD_LEAVE_FROM]).append(" < '").append(start).append("'");
            sql.append("  AND ").append(PstLeave.fieldNames[PstLeave.FLD_LEAVE_TO]).append("< '").append(start).append("')");
            sql.append("  OR (").append(PstLeave.fieldNames[PstLeave.FLD_LEAVE_FROM]).append(" > '").append(end).append("'");
            sql.append("  AND ").append(PstLeave.fieldNames[PstLeave.FLD_LEAVE_TO]).append("< '").append(end).append("')");
            sql.append(" )");

            if (departmentId != 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]);
                sql.append(" = ").append(departmentId);
            }

            if (sectionId != 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]);
                sql.append(" = ").append(sectionId);
            }

            sql.append(" ORDER BY ").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);


            //////////////////
            System.out.println(">>> SQL: " + sql.toString());
            /////////////////

            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ZeroSickness sickness = new ZeroSickness();

                sickness.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                sickness.setEmpName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                sickness.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                sickness.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));

                result.add(sickness);
            }
        } catch (Exception e) {
            System.out.println("listZeroSickness exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector listZeroSicknessOld2(long departmentId, long sectionId, long startPeriod, long endPeriod) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        Period startPer = new Period();
        Period endPer = new Period();
        Vector periodIds = new Vector();

        String sOIDSickLeave = PstSystemProperty.getValueByName("OID_SICK_LEAVE");
        String sOIDSickLeaveWoDC = PstSystemProperty.getValueByName("OID_SICK_LEAVE_WO_DC");

        long oidSickLeave = 0;
        long oidSickLeaveWoDC = 0;
        try {
            if ((sOIDSickLeave != null) && (sOIDSickLeave.length() > 0)) {
                oidSickLeave = Long.parseLong(sOIDSickLeave);
            }
            if ((sOIDSickLeaveWoDC != null) && (sOIDSickLeaveWoDC.length() > 0)) {
                oidSickLeaveWoDC = Long.parseLong(sOIDSickLeaveWoDC);
            }
        } catch (Exception exc) {
            System.out.println("===> NOT SICK LEAVE OID DEFINED => USE ABSENCE MANAGEMENT AND SCHEDULE STATUS AS SICKNESS REPORT");
        }


        try {
            startPer = PstPeriod.fetchExc(startPeriod);
            endPer = PstPeriod.fetchExc(endPeriod);

            String sqlPeriod = " SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] +
                    " FROM " + PstPeriod.TBL_HR_PERIOD +
                    " WHERE " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +
                    " >= '" + Formater.formatDate(startPer.getStartDate(), "yyyy-MM-dd") + "'" +
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +
                    " <= '" + Formater.formatDate(endPer.getEndDate(), "yyyy-MM-dd") + "'";


            System.out.println("SQL Getting Period: " + sqlPeriod);

            dbrs = DBHandler.execQueryResult(sqlPeriod);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                periodIds.add(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
            }
        } catch (Exception e) {

        } finally {
            DBResultSet.close(dbrs);
        }


        try {
            String sql = " SELECT DISTINCT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "," +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + "," +
                    " DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + "," +
                    " SEC." + PstSection.fieldNames[PstSection.FLD_SECTION] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP" +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " INNER JOIN " + PstSection.TBL_HR_SECTION + " SEC" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                    " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] +
                    " LEFT JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " SCH" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SCH." + PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID] +
                    " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " IN (";

            for (int i = 0; i < periodIds.size(); i++) {
                sql += periodIds.get(i);

                if (i < periodIds.size() - 1) {
                    sql += ",";
                } else {
                    sql += ") ";
                }
            }


            if (departmentId != 0) {
                sql += " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + departmentId;
            }

            if (sectionId != 0) {
                sql += " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + sectionId;
            }

            sql += " AND (";
            if (oidSickLeave != 0) {
                for (int i = 1; i <= 31; i++) {
                    sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1 - 1 + i] + " <> '" + oidSickLeave + "' ";
                    //if(i < 31)
                    sql += " AND ";
                }

                for (int i = 1; i <= 31; i++) {
                    sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1 - 1 + i] + " <> '" + oidSickLeaveWoDC + "' ";
                    //if(i < 31)
                    sql += " AND ";
                }

                for (int i = 1; i <= 31; i++) {
                    sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1 - 1 + i] + " <> '" + oidSickLeaveWoDC + "' ";
                    //if(i < 31)
                    sql += " AND ";
                }

                for (int i = 1; i <= 31; i++) {
                    sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1 - 1 + i] + " <> '" + oidSickLeave + "' ";
                    if (i < 31) {
                        sql += " AND ";
                    }
                }

            } else {
                for (int i = 1; i <= 31; i++) {
                    sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1 - 1 + i] + " <> " + PstEmpSchedule.REASON_ABSENCE_SICKNESS;
                    if (i < 31) {
                        sql += " AND ";
                    }
                }
            }

            sql += ") ";
            sql += " ORDER BY " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];


            //////////////////
            System.out.println(">>> SQL: " + sql.toString());
            /////////////////

            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ZeroSickness sickness = new ZeroSickness();

                sickness.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                sickness.setEmpName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                sickness.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                sickness.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));

                result.add(sickness);
            }
        } catch (Exception e) {
            System.out.println("listZeroSickness exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector listZeroSickness(long departmentId, long sectionId, long startPeriod, long endPeriod,String empNum,String fullName) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        Period startPer = new Period();
        Period endPer = new Period();
        Vector periodIds = new Vector();

        String sOIDSickLeave = PstSystemProperty.getValueByName("OID_SICK_LEAVE");
        String sOIDSickLeaveWoDC = PstSystemProperty.getValueByName("OID_SICK_LEAVE_WO_DC");

        long oidSickLeave = 0;
        long oidSickLeaveWoDC = 0;
        try {
            if ((sOIDSickLeave != null) && (sOIDSickLeave.length() > 0)) {
                oidSickLeave = Long.parseLong(sOIDSickLeave);
            }
            if ((sOIDSickLeaveWoDC != null) && (sOIDSickLeaveWoDC.length() > 0)) {
                oidSickLeaveWoDC = Long.parseLong(sOIDSickLeaveWoDC);
            }
        } catch (Exception exc) {
            System.out.println("===> NOT SICK LEAVE OID DEFINED => USE ABSENCE MANAGEMENT AND SCHEDULE STATUS AS SICKNESS REPORT");
        }

        try {
            startPer = PstPeriod.fetchExc(startPeriod);
            endPer = PstPeriod.fetchExc(endPeriod);

            String sqlPeriod = " SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] +
                    " FROM " + PstPeriod.TBL_HR_PERIOD +
                    " WHERE " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +
                    " >= '" + Formater.formatDate(startPer.getStartDate(), "yyyy-MM-dd") + "'" +
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +
                    " <= '" + Formater.formatDate(endPer.getEndDate(), "yyyy-MM-dd") + "';";

            //System.out.println("SQL Getting Period: " + sqlPeriod);

            dbrs = DBHandler.execQueryResult(sqlPeriod);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                periodIds.add(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
            }
        } catch (Exception e) {

        } finally {
            DBResultSet.close(dbrs);
        }

        Vector allResult= new Vector(1,1);

        try {
            for (int ip = 0; ip < periodIds.size(); ip++) {
                Vector empNoSickInPeriod = new Vector(); 
                String sql = " SELECT DISTINCT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "," +
                        " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + "," +
                        " DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + "," +
                        " SEC." + PstSection.fieldNames[PstSection.FLD_SECTION] +
                        " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP" +
                        " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT" +
                        " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                        " INNER JOIN " + PstSection.TBL_HR_SECTION + " SEC" +
                        " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] +
                        " LEFT JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " SCH" +
                        " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " = SCH." + PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID] +
                        " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                        "  = '" + periodIds.get(ip) + "' ";

                

                sql += " AND (";
                if (oidSickLeave != 0) {
                    for (int i = 1; i <= 31; i++) {
                        sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1 - 1 + i] + " <> '" + oidSickLeave + "' ";
                        //if(i < 31)
                        sql += " AND ";
                    }

                    for (int i = 1; i <= 31; i++) {
                        sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1 - 1 + i] + " <> '" + oidSickLeaveWoDC + "' ";
                        //if(i < 31)
                        sql += " AND ";
                    }

                    for (int i = 1; i <= 31; i++) {
                        sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1 - 1 + i] + " <> '" + oidSickLeaveWoDC + "' ";
                        //if(i < 31)
                        sql += " AND ";
                    }

                    for (int i = 1; i <= 31; i++) {
                        sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1 - 1 + i] + " <> '" + oidSickLeave + "' ";
                        if (i < 31) {
                            sql += " AND ";
                        }
                    }

                } else {
                    for (int i = 1; i <= 31; i++) {
                        sql += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1 - 1 + i] + " <> " + PstEmpSchedule.REASON_ABSENCE_SICKNESS;
                        if (i < 31) {
                            sql += " AND ";
                        }
                    }
                }

                sql += ") ";
                if (departmentId != 0) {
                    sql = " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                            " = " + departmentId;
                }

                if (sectionId != 0) {
                    sql = " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                            " = " + sectionId;
                }
                    //update by satrya 2012-07-30
            if (empNum != null && empNum.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                         + " LIKE " + "\"%" + empNum + "%\"";
            }
            //update by satrya 2012-07-16
            if (fullName !=null && fullName.length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + " LIKE " + "\"%" + fullName + "%\"";
            }
                sql += " ORDER BY " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];


                //////////////////
               // System.out.println(">>> SQL: " + sql.toString());
                /////////////////

                dbrs = DBHandler.execQueryResult(sql.toString());
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {
                    ZeroSickness sickness = new ZeroSickness();

                    sickness.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                    sickness.setEmpName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    sickness.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    sickness.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));

                    empNoSickInPeriod.add(sickness);
                }
                allResult.add(empNoSickInPeriod);
            }


        } catch (Exception e) {
            System.out.println("listZeroSickness exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
        }
        
        // make intersection vector of the result zero sickness per period
        if(allResult!=null  && allResult.size()>0){
            Vector firstNoSick = (Vector) allResult.get(0); // get the first result as references
            for(int ip=0;ip<firstNoSick.size();ip++){
                ZeroSickness refSick = (ZeroSickness) firstNoSick.get(ip); // get the sickness record
                for(int al=1;al< allResult.size();al++){
                    Vector tempNoSick = (Vector) allResult.get(al);
                    for(int ti=0;ti<tempNoSick.size();ti++){
                        ZeroSickness tempSick = (ZeroSickness) tempNoSick.get(ti); // get comparation
                        if(refSick.getEmpNum().equals(tempSick.getEmpNum())){ // if found go to next period
                            break;
                        }
                        if(ti==(tempNoSick.size()-1)){
                           firstNoSick.remove(ip);  // if not found then remove reference record on that position
                           ip=ip-1;
                           if(ip<0){ ip=0; }
                        }
                    }
                    
                }
            }
        }
                
        if(allResult!=null  && allResult.size()>0){
            result = (Vector) allResult.get(0);
        }
        return result;
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
     * @return the empFullName
     */
    public String getEmpFullName() {
        return empFullName;
    }

    /**
     * @param empFullName the empFullName to set
     */
    public void setEmpFullName(String empFullName) {
        this.empFullName = empFullName;
    }
    
    public Vector<SicknessMonthlyRekap> getListMonth(Vector listSicknessPresence, int dateOfMonth, Date endDate, String sOIDSickLeave, String sOIDSickLeaveWoDC, String sISickWDC, String sISickWoDC) {

        Vector list = new Vector(1, 1);
        
        SicknessMonthlyRekap sicknessMonthlyRekap= null;
        
        if (listSicknessPresence != null && listSicknessPresence.size() > 0) {
            
            int iSickWDC = -1;
            int iSickWoDC = -1;

            long oidSickLeave = 0;
            long oidSickLeaveWoDC = 0;
            try {
                if ((sOIDSickLeave != null) && (sOIDSickLeave.length() > 0)) {
                    oidSickLeave = Long.parseLong(sOIDSickLeave);
                }
                if ((sOIDSickLeaveWoDC != null) && (sOIDSickLeaveWoDC.length() > 0)) {
                    oidSickLeaveWoDC = Long.parseLong(sOIDSickLeaveWoDC);
                }
                if ((sISickWDC != null) && (sISickWDC.length() > 0)) {
                    iSickWDC = Integer.parseInt(sISickWDC);
                }
                if ((sISickWoDC != null) && (sISickWoDC.length() > 0)) {
                    iSickWoDC = Integer.parseInt(sISickWoDC);
                }
            } catch (Exception exc) {
                System.out.println("===> NOT SICK LEAVE OID DEFINED => USE ABSENCE MANAGEMENT AND SCHEDULE STATUS AS SICKNESS REPORT");
            }
            
            int maxSicknessPresence = listSicknessPresence.size();
            int dataAmount = 0;
            try {
                for (int i = 0; i < maxSicknessPresence; i++) {
                    SicknessMonthly absenteeismWeekly = (SicknessMonthly) listSicknessPresence.get(i);
                    String empNum = absenteeismWeekly.getEmpNum();
                    String empName = absenteeismWeekly.getEmpName();
                    Vector empSchedules = absenteeismWeekly.getEmpSchedules();
                    Vector absStatus = absenteeismWeekly.getPresenceStatus();
                    Vector absReason = absenteeismWeekly.getAbsReason();

                    int startPeriodSick = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));

                    startPeriodSick = startPeriodSick - 1;

                    if (oidSickLeave == 0) { // sickness report based on schedule status
                        // check apakah dalam vector schedule ada schedule tipe not OFf/ABSENCE ???			
                        boolean containSchldNotOff = SessSickness.containSchldNotOff(empSchedules);

                        if (containSchldNotOff) {
                            int totalSickness = 0;
                            int absenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
                            Vector rowxMonth = new Vector(1, 1);
                            for (int isch = 0; isch < empSchedules.size(); isch++) {
                                if (startPeriodSick == dateOfMonth) {
                                    startPeriodSick = 1;
                                } else {
                                    startPeriodSick = startPeriodSick + 1;
                                }
                                //String schldSymbol = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules.get(isch))));
                                String schldSymbol = "";
                                int schldCategory = 0;
                                String currAbsence = "";
                                Vector vectSchldSymbol = PstScheduleSymbol.getScheduleData(Long.parseLong(String.valueOf(empSchedules.get(startPeriodSick - 1))));
                                if (vectSchldSymbol != null && vectSchldSymbol.size() > 0) {
                                    Vector vectTemp = (Vector) vectSchldSymbol.get(0);
                                    schldSymbol = String.valueOf(vectTemp.get(0));
                                    schldCategory = Integer.parseInt(String.valueOf(vectTemp.get(1)));
                                }

                                if (schldSymbol != null && schldSymbol.length() > 0) {
                                    int statusAbsence = Integer.parseInt(String.valueOf(absStatus.get(startPeriodSick - 1)));
                                    int reasonAbsence = Integer.parseInt(String.valueOf(absReason.get(startPeriodSick - 1)));
                                    if (!(schldCategory == PstScheduleCategory.CATEGORY_OFF
                                            || schldCategory == PstScheduleCategory.CATEGORY_ABSENCE
                                            || schldCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                            || schldCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                            || schldCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                            && (statusAbsence == PstEmpSchedule.STATUS_PRESENCE_ABSENCE && reasonAbsence == PstEmpSchedule.REASON_ABSENCE_SICKNESS)) {
                                        currAbsence = "DC";
                                        absenceNull += 1;
                                    } // tambahkan untuk reason cstd,khusus intimas
                                    else if (!(schldCategory == PstScheduleCategory.CATEGORY_OFF
                                            || schldCategory == PstScheduleCategory.CATEGORY_ABSENCE /*|| schldCategory==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                             || schldCategory==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                             || schldCategory==PstScheduleCategory.CATEGORY_LONG_LEAVE*/)
                                            && (statusAbsence == PstEmpSchedule.STATUS_PRESENCE_ABSENCE && reasonAbsence == 4)) {
                                        currAbsence = "CSTD";
                                        absenceNull += 1;
                                    } else {
                                        currAbsence = "";
                                    }
                                    //rowxMonth.add(currAbsence);

                                    if (currAbsence != null && currAbsence.length() > 0) {
                                        totalSickness += 1;
                                    }
                                } else {
                                   // rowxMonth.add("");
                                }
                            }

                            if (absenceNull > 0) {
                                
                                sicknessMonthlyRekap = new SicknessMonthlyRekap();
                                sicknessMonthlyRekap.setEmpNum(empNum);
                                sicknessMonthlyRekap.setEmpName(empName);
                                sicknessMonthlyRekap.setMonth(endDate.getMonth());
                                sicknessMonthlyRekap.setTotalMonth(totalSickness);
                                
                                list.add(sicknessMonthlyRekap);
                            }

                        }
                    } else {
                        // check sick leave based on schedule
                        int totalSickness = 0;
                        int absenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
                        Vector rowxMonth = new Vector(1, 1);
                        for (int isch = 0; isch < empSchedules.size(); isch++) {
                            if (startPeriodSick == dateOfMonth) {
                                startPeriodSick = 1;
                            } else {
                                startPeriodSick = startPeriodSick + 1;
                            }
                            //String schldSymbol = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules.get(isch))));
                            String schldSymbol = "";
                            int schldCategory = 0;
                            String currAbsence = "";
                            long oidSch = Long.parseLong(String.valueOf(empSchedules.get(startPeriodSick - 1)));
                            int iReason = -1;
                            //mencari apakah ada reason misal yg bernilai 0= Dc bernilai 1= not DC
                            if (absReason.get(startPeriodSick - 1) != null) {
                                iReason = Integer.parseInt(String.valueOf(absReason.get(startPeriodSick - 1)));
                            }
                            if (oidSch == oidSickLeave) {
                                currAbsence = "DC";
                                absenceNull += 1;
                                totalSickness += 1;
                                //rowxMonth.add(currAbsence);
                            } else if (oidSch == oidSickLeaveWoDC) {
                                currAbsence = "W/O DC";
                                absenceNull += 1;
                                totalSickness += 1;
                               // rowxMonth.add(currAbsence);
                            } else if (iReason == iSickWDC) {
                                currAbsence = "DC";
                                absenceNull += 1;
                                totalSickness += 1;
                                //rowxMonth.add(currAbsence);
                            } else if (iReason == iSickWoDC) {
                                currAbsence = "W/O DC";
                                absenceNull += 1;
                                totalSickness += 1;
                                //rowxMonth.add(currAbsence);
                            } else {
                                //rowxMonth.add("");
                            }

                        }

                        if (absenceNull > 0) {
                            
                            sicknessMonthlyRekap = new SicknessMonthlyRekap();
                            
                            sicknessMonthlyRekap.setEmpNum(empNum);
                            sicknessMonthlyRekap.setEmpName(empName);
                            sicknessMonthlyRekap.setMonth(endDate.getMonth());
                            sicknessMonthlyRekap.setTotalMonth(totalSickness);

                            list.add(sicknessMonthlyRekap);
                        }


                    }
                    //list rows


                }

            } catch (Exception ex) {
                System.out.println("Exception list Montly sickness" + ex);
            }

        }
        return list;
    }
}
