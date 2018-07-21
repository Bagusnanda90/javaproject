/*
 * SessAbsenteeism.java
 *
 * Created on June 7, 2004, 12:02 PM
 */
package com.dimata.harisma.session.absenteeism;

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

// import harisma
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.LogicParser;
import java.util.Hashtable;

/**
 *
 * @author gedhy
 */
public class SessAbsenteeism {

    public static final int TIME_LATES = 0;

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
    //update by satrya 2012-10-19
    private String empNum;
    private String empFullName;

    /**
     * get list absenteeism data daily
     *
     * @param departmentId
     * @param selectedDate
     * @return
     * @created by Edhy
     */
    //public static Vector listAbsenteeismDataDaily(long departmentId, Date selectedDate, long sectionId)
    //upate by satrya 2012-10-19
    // update by devin 2014-01-29 public static Vector listAbsenteeismDataDaily(long departmentId, Date selectedDate, long sectionId, String empNumb,String fullName)
    public static Vector listAbsenteeismDataDaily(long departmentId, long companyId, long divisionId, Date selectedDate, long sectionId, String empNumb, String fullName) {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + //update by satrya 2012-10-29
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1]
                    + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + //update by satrya 2012-10-19
                    " LEFT JOIN " + PstReason.TBL_HR_REASON + " AS HRS"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                    + " = HRS." + PstReason.fieldNames[PstReason.FLD_NO]
                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId
                    + (departmentId != 0
                    ? " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = " + departmentId : "")
                    + (companyId != 0
                    ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = " + companyId : "")
                    + (divisionId != 0
                    ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = " + divisionId : "")
                    + (sectionId != 0 ? (" AND SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " = " + sectionId) : "")
                    + " AND (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]
                    + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ") "
                    + " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " NOT IN (" + PstScheduleCategory.CATEGORY_ABSENCE
                    + "," + PstScheduleCategory.CATEGORY_OFF
                    + "," + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                    + "," + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                    + "," + PstScheduleCategory.CATEGORY_LONG_LEAVE + ")"
                    //untuk mencari karyawan risigned
                    + "  AND (( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.YES_RESIGN + " AND " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\"" + " AND " + "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  23:59:59") + "\""
                    + " ) OR (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))";
            //update by satrya 2012-07-16
                        /*if (empNumb != null && empNumb.length() > 0) {
             sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
             + " = " + "\"" + empNumb.trim() + "\"";//penambahan trim
             }
             //update by satrya 2012-07-16
             if (fullName != null && fullName.length() > 0) {
             sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
             + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
             }*/
            //update by devin 2014-01-30

            if (empNumb != null && empNumb != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNumb);
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }

            }
            //update by devin 2014-01-30
            if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }

            }
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            ///System.out.println("\tlistAbsenteeismDataDaily : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //  java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());                
            // java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate()+1);                
            while (rs.next()) {
                AbsenteeismDaily absenteeismDaily = new AbsenteeismDaily();
                absenteeismDaily.setEmpNum(rs.getString(1));
                absenteeismDaily.setEmpName(rs.getString(2));
                absenteeismDaily.setSchldSymbol(rs.getString(3));
                absenteeismDaily.setSchldIn(rs.getTime(4));
                absenteeismDaily.setSchldOut(rs.getTime(5));
                absenteeismDaily.setNoReason1st(rs.getInt(6));
                absenteeismDaily.setNoReason2nd(rs.getInt(7));
                absenteeismDaily.setSelectedDate(selectedDate);
                absenteeismDaily.setNote1st(rs.getString(8));
                absenteeismDaily.setNote2nd(rs.getString(9));
                //absenteeismDaily.setReasonCode(rs.getString(6));
                // absenteeismDaily.setRemark(rs.getString(6));

                result.add(absenteeismDaily);
            }
        } catch (Exception e) {
            System.out.println("listAbsenteeismDataDaily exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;

        }
    }

    /**
     * Keterangan : untuk mencAri list absence
     *
     * @param departmentId
     * @param fromDate
     * @param toDate
     * @param sectionId
     * @param empNumb
     * @param fullName
     * @by: satrya
     * @return
     */
    //update by devin 2014-01-29 
    //public static Vector listAbsenteeismDataDaily(long departmentId, Date fromDate,Date toDate, long sectionId, String empNumb,String fullName){
    public static Vector listAbsenteeismDataDaily(long departmentId, long companyId, long divisionId, Date fromDate, Date toDate, long sectionId, String empNumb, String fullName) {
        Vector result = new Vector();
        /// AbsenteeismDaily absenteeismDaily = null;
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
                //update by devin 
                //Vector dAbsence = listAbsenteeismDataDaily(departmentId, selectedDate, sectionId, empNumb, fullName);
                Vector dAbsence = listAbsenteeismDataDaily(departmentId, companyId, divisionId, selectedDate, sectionId, empNumb, fullName);
                if (dAbsence != null && dAbsence.size() > 0) {
                    result.addAll(dAbsence);
                }
            }
        }
        return result;
    }

    /**
     * get list absenteeism data weekly
     *
     * @param selectedMonth
     * @param weekIndex
     * @param departmentId
     * @return
     * @created by Edhy
     */
    public static Vector listAbsenteeismDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex, String empNum, String fullName) {
        return listAbsenteeismDataWeekly(departmentId, iCalendarType, selectedMonth, weekIndex, "", "");
    }

    //update by devin 2014-01-29 public static Vector listAbsenteeismDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex, long sectionId,String empNumb,String fullName)
    public static Vector listAbsenteeismDataWeekly(long departmentId, long companyId, long divisionId, int iCalendarType, Date selectedMonth, int weekIndex, long sectionId, String empNumb, String fullName) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get start and end date of selected week of selected month
        CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);
        Date startDateWeek = objCalendarCalc.getStartDateOfTheWeek(selectedMonth, weekIndex);
        Date endDateWeek = objCalendarCalc.getEndDateOfTheWeek(selectedMonth, weekIndex);
        int intStartDate = startDateWeek.getDate();
        int intEndDate = endDateWeek.getDate();

        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(startDateWeek);
        // long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // schedule
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)];
            }

            // actual status
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)];
            }

            // actual reason 1st
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i - 1)];
            }
            // actual reason 2nd
            //update by satrya 2012-10-22
            for (int i = intStartDate; i <= intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)];
            }

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId
                    + (departmentId != 0
                    ? " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = " + departmentId : "")
                    + (companyId != 0
                    ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = " + companyId : "")
                    + (divisionId != 0
                    ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = " + divisionId : "")
                    + (sectionId != 0 ? (" AND SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " = " + sectionId) : "");
            //update by satrya 2012-10-22
                        /*if (empNumb != null && empNumb.length() > 0) {
             sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
             + " = " + "\"" + empNumb.trim() + "\"";//penambahan trim
             }
             //update by satrya 2012-10-22
             if (fullName != null && fullName.length() > 0) {
             sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
             + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
             }*/
            //update by devin 2014-01-30

            if (empNumb != null && empNumb != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNumb);
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }

            }
            //update by devin 2014-01-30
            if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }

            }
//                         sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];    
            String sql2 = "";
            for (int i = intStartDate; i <= intEndDate; i++) {

                if (sql2.length() == 0) {
                    //if()
                    sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                            + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";
                } else {
                    sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                            + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";
                }
            }
            sql2 = " AND (" + sql2 + ")";
            sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //  System.out.println("\tlistAbsenteeismDataWeekly : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AbsenteeismWeekly absenteeismWeekly = new AbsenteeismWeekly();

                absenteeismWeekly.setEmpNum(rs.getString(1));
                absenteeismWeekly.setEmpName(rs.getString(2));

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)])));
                }
                absenteeismWeekly.setEmpSchedules(vectSchedule);

                // values status
                Vector vectStatus = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)])));
                }
                absenteeismWeekly.setAbsStatus(vectStatus);

                // values reason
                Vector vectReason = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i - 1)])));
                }
                absenteeismWeekly.setAbsReason(vectReason);

                // values reason 2ndd
                //update by satrya 2012-10-22
                 /*Vector vectReason2nd = new Vector();                    
                 for(int i=intStartDate; i<=intEndDate; i++)
                 {                        
                 vectReason2nd.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)])));
                 }                                        
                 absenteeismWeekly.setAbsReason(vectReason2nd);                                        
                 */

                result.add(absenteeismWeekly);
            }
        } catch (Exception e) {
            System.out.println("listAbsenteeismDataWeekly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * get list absenteeism data monthly
     *
     * @param <CODE>departmentId</CODE>selected department
     * @param <CODE>selectedMonth</CODE>selected month
     * @created by Edhy
     */
    //update by devin 2014-01-29 public static Vector listAbsenteeismDataMonthly(long departmentId, Date selectedMonth, long sectionId,String empNumb,String fullName)
    public static Vector listAbsenteeismDataMonthly(long departmentId, long companyId, long divisionId, Date selectedMonth, long sectionId, String empNumb, String fullName) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get maximum date on selected month        
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear() + 1900, selectedMonth.getMonth(), 1);
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

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

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + //update by satrya 2012-11-19
                    " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId
                    + (departmentId != 0
                    ? " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = " + departmentId : "")
                    + (companyId != 0
                    ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = " + companyId : "")
                    + (divisionId != 0
                    ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = " + divisionId : "")
                    + (sectionId != 0 ? (" AND SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " = " + sectionId) : "");
            //update by satrya 2012-10-22
                        /*if (empNumb != null && empNumb.length() > 0) {
             sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
             + " = " + "\"" + empNumb.trim() + "\"";//penambahan trim
             }
             //update by satrya 2012-10-22
             if (fullName != null && fullName.length() > 0) {
             sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
             + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
             }*/
            //update by devin 2014-01-30

            if (empNumb != null && empNumb != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNumb);
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }

            }
            //update by devin 2014-01-30
            if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }

            }
            //update bvy satrya 2012-11-16
            String sql2 = "";
            for (int i = 0; i < maxDayOnSelectedMonth; i++) {

                if (sql2.length() == 0) {
                    //if()
                    sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                            + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";
                } else {
                    sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                            + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i] + "=" + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";
                }
            }
            sql2 = " AND (" + sql2 + ")";
            sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            //sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //System.out.println("\tlistAbsenteeismDataMonthly : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AbsenteeismMonthly absenteeismMonthly = new AbsenteeismMonthly();

                absenteeismMonthly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                absenteeismMonthly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                absenteeismMonthly.setEmpSchedules(vectSchedule);

                // values status
                Vector vectStatus = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i])));
                }
                absenteeismMonthly.setPresenceStatus(vectStatus);

                // values reason
                Vector vectReason = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) {
                    vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i])));
                }
                absenteeismMonthly.setAbsReason(vectReason);

                result.add(absenteeismMonthly);
            }
        } catch (Exception e) {
            System.out.println("listAbsenteeismDataMonthly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * get status if vectSchedule contains night shift schedule
     *
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
                    String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                            + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                            + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                            + " ON CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                            + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                            + " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                            + " IN ( " + listSchedule + ")";

                    //System.out.println("\tcontainSchldNightShift : "+sql);
                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    while (rs.next()) {
                        if (!(rs.getInt(1) == PstScheduleCategory.CATEGORY_ABSENCE
                                || rs.getInt(1) == PstScheduleCategory.CATEGORY_OFF
                                || rs.getInt(1) == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                || rs.getInt(1) == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                || rs.getInt(1) == PstScheduleCategory.CATEGORY_LONG_LEAVE)) {
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

    public Vector<AbsenteeismMonthlyRekap> getListMonth(Vector listAbsenteeismPresence, int dateOfMonth, Date endDate) {

        Vector list = new Vector(1, 1);
        
        AbsenteeismMonthlyRekap absenteeismMonthlyRekap= null;
        
        if (listAbsenteeismPresence != null && listAbsenteeismPresence.size() > 0) {
            
            int maxAbsenteeismPresence = listAbsenteeismPresence.size();
            int dataAmount = 0;
            try {
                for (int i = 0; i < maxAbsenteeismPresence; i++) {
                    AbsenteeismMonthly absenteeismWeekly = (AbsenteeismMonthly) listAbsenteeismPresence.get(i);
                    String empNum = absenteeismWeekly.getEmpNum();
                    String empName = absenteeismWeekly.getEmpName();
                    Vector empSchedules = absenteeismWeekly.getEmpSchedules();
                    Vector absStatus = absenteeismWeekly.getPresenceStatus();
                    Vector absReason = absenteeismWeekly.getAbsReason();

                    // check apakah dalam vector schedule ada schedule tipe not OFf/ABSENCE ???			
                    boolean containSchldNotOff = SessAbsenteeism.containSchldNotOff(empSchedules);
                    int startPeriodAbsence = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
                    startPeriodAbsence = startPeriodAbsence - 1;
                    //System.out.println("dateOfMonth"+dateOfMonth);

                    if (containSchldNotOff) {
                        int totalAbsenteeism = 0;
                        int absenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
                        Vector rowxMonth = new Vector(1, 1);
                        for (int isch = 0; isch < empSchedules.size(); isch++) {
                            if (startPeriodAbsence == dateOfMonth) {
                                startPeriodAbsence = 1;
                            } else {
                                startPeriodAbsence = startPeriodAbsence + 1;
                            }
                            //String schldSymbol = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules.get(isch))));
                            String schldSymbol = "";
                            int schldCategory = 0;
                            String currAbsence = "";
                            Vector vectSchldSymbol = PstScheduleSymbol.getScheduleData(Long.parseLong(String.valueOf(empSchedules.get(startPeriodAbsence - 1))));
                            if (vectSchldSymbol != null && vectSchldSymbol.size() > 0) {

                                Vector vectTemp = (Vector) vectSchldSymbol.get(0);
                                schldSymbol = String.valueOf(vectTemp.get(0));
                                schldCategory = Integer.parseInt(String.valueOf(vectTemp.get(1)));
                            }

                            if (schldSymbol != null && schldSymbol.length() > 0) {
                                int statusAbsence = Integer.parseInt(String.valueOf(absStatus.get(startPeriodAbsence - 1)));
                                int reasonAbsence = Integer.parseInt(String.valueOf(absReason.get(startPeriodAbsence - 1)));
                                if (!(schldCategory == PstScheduleCategory.CATEGORY_OFF
                                        || schldCategory == PstScheduleCategory.CATEGORY_ABSENCE
                                        || schldCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                        || schldCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                        || schldCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                        && (statusAbsence == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) //(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_ABSENCE && reasonAbsence==PstEmpSchedule.REASON_ABSENCE_ALPHA))
                                {

                                    currAbsence = PstReason.getStrReasonCode(reasonAbsence);

                                    absenceNull += 1;
                                } else {
                                    currAbsence = "";
                                }
                                //rowxMonth.add(currAbsence);

                                if (currAbsence != null && currAbsence.length() > 0) {
                                    totalAbsenteeism += 1;
                                }

                            } else {
                               // rowxMonth.add("");
                            }
                        }

                        if (absenceNull > 0) {
                            
                            absenteeismMonthlyRekap = new AbsenteeismMonthlyRekap();
                            
                            absenteeismMonthlyRekap.setEmpNum(empNum);
                            absenteeismMonthlyRekap.setEmpName(empName);
                            absenteeismMonthlyRekap.setTotalMonth(totalAbsenteeism);
                            absenteeismMonthlyRekap.setMonth(endDate.getMonth());
                            
                            list.add(absenteeismMonthlyRekap);
                        }

                    }
                }//end list absence
            } catch (Exception ex) {
                System.out.println("Exception list Montly sickness" + ex);
            }

        }
        return list;
    }
}
