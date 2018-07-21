/*
 * SessEmployeeEarly.java
 *
 * Created on December 12, 2007, 3:52 PM
 */

package com.dimata.harisma.session.early;

import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.search.SrcLateness;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;
import com.dimata.util.Formater;
import com.dimata.util.CalendarCalc;

import java.util.Vector;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *
 * @author  YUNI
 */
public class SessEmployeeEarly {
     public static final int TIME_EARLY = 0;
     
     /**
     * yunny
     * pencarian data early untuk harian  
     *
     * @param departmentId
     * @param selectedDate
     * @return
     */
    public static Vector listEarlyDaily(long departmentId, Date selectedDate,long sectionId) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (idxFieldName - 1);
        int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1);

        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)] +
                    ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
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
                    /*" AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +*/
                    " AND ((SCH." + PstEmpSchedule.fieldNames[firstFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME + ") OR " +
                    " (SCH." + PstEmpSchedule.fieldNames[secondFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME + ") OR "+
                    " (SCH." + PstEmpSchedule.fieldNames[firstFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY + ") OR "+
                    " (SCH." + PstEmpSchedule.fieldNames[secondFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY + "))";
            
                    String whereClause = "";
                    
                     if(departmentId != 0)
                        whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                        " = "+departmentId + " AND ";
                    
                     if(sectionId != 0)
                        whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                        " = "+ sectionId + " AND ";
                    
                    
                     if(whereClause != null && whereClause.length()>0){
                        whereClause = " AND "+ whereClause.substring(0,whereClause.length()-4);
                        sql = sql + whereClause;
                    }
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            

            System.out.println("\tlistEarly daily : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            while (rs.next()) 
            {
                EarlyDaily earlyDaily = new EarlyDaily();
                earlyDaily.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                earlyDaily.setEmpName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                earlyDaily.setDepId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                earlyDaily.setSchldSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                earlyDaily.setSchSymbolId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                earlyDaily.setCatType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));

                // if schld In is 00:00, its mean in the next day
                if (rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]) != null) 
                {
                    earlyDaily.setSchldIn(DBHandler.convertDate(sqlNextDate, rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN])));
                }
                else 
                {
                    earlyDaily.setSchldIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                }

                // get symbol out time
                if (rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]) != null) 
                {
                    earlyDaily.setSchldOut(DBHandler.convertDate(sqlNextDate, rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT])));
                }
                else 
                {
                    earlyDaily.setSchldOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                }

                // get shift in I
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]) != null) 
                {
                    earlyDaily.setActualIn(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)])));
                }
                else 
                {
                    earlyDaily.setActualIn(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]));
                }

                // get shift out I
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]) != null) 
                {
                    earlyDaily.setActualOut(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)])));
                }
                else 
                {
                    earlyDaily.setActualOut(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]));
                }

                // get shift in II
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]) != null) 
                {
                    earlyDaily.setActualInII(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)])));
                }
                else 
                {
                    earlyDaily.setActualInII(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                }

                // get shift out II
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]) != null) 
                {
                    earlyDaily.setActualOutII(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)])));
                }
                else 
                {
                    earlyDaily.setActualOutII(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                }

                result.add(earlyDaily);
            }
        }
        catch (Exception e) 
        {
            System.out.println("latenessDaily exc : " + e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
     /**
     * @param dt
     * @return
     */
    public static ScheduleSymbol getDateRoster(int dt, EmpSchedule empSchedule) 
    {
        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        try 
        {
            switch (dt) 
            {
                case 1:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD1());
                    break;
                case 2:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD2());
                    break;
                case 3:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD3());
                    break;
                case 4:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD4());
                    break;
                case 5:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD5());
                    break;
                case 6:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD6());
                    break;
                case 7:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD7());
                    break;
                case 8:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD8());
                    break;
                case 9:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD9());
                    break;
                case 10:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD10());
                    break;
                case 11:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD11());
                    break;
                case 12:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD12());
                    break;
                case 13:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD13());
                    break;
                case 14:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD14());
                    break;
                case 15:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD15());
                    break;
                case 16:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD16());
                    break;
                case 17:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD17());
                    break;
                case 18:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD18());
                    break;
                case 19:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD19());
                    break;
                case 20:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD20());
                    break;
                case 21:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD21());
                    break;
                case 22:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD22());
                    break;
                case 23:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD23());
                    break;
                case 24:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD24());
                    break;
                case 25:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD25());
                    break;
                case 26:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD26());
                    break;
                case 27:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD27());
                    break;
                case 28:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD28());
                    break;
                case 29:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD29());
                    break;
                case 30:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD30());
                    break;
                case 31:
                    scheduleSymbol = PstScheduleSymbol.fetchExc(empSchedule.getD31());
                    break;
            }
        } 
        catch (Exception e) 
        {
            System.out.println(new SessEmployeeEarly().getClass().getName()+".getDateRoster() exc : " + e.toString());
        }
        return scheduleSymbol;
    }
    
    
    /**
     * yunny
     * untuk pencarian data early home  yang mingguan
     * @param departmentId
     * @param selectedMonth
     * @param sectionId
     * @return
     */
    public static Vector listEarlyDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex,long oidSection) 
    {
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

        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // fields schedule
            for (int i = intStartDate; i <= intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)];
            }

            // fields schedule II
            for (int i = intStartDate; i <= intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)];
            }

            // fields actual in
            for (int i = intStartDate; i <= intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i - 1)];
            }

            // fields actual out
            for (int i = intStartDate; i <= intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)];
            }

            // fields actual shift in
            for (int i = intStartDate; i <= intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)];
            }

            // fields actual shift out
            for (int i = intStartDate; i <= intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)];
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
                   " AND ";
                  
                    // fields actual shift in
                    String sql2 = "";
                    for (int i = intStartDate; i <= intEndDate; i++) 
                    {
                        if(sql2.length()==0)
                        {
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+" OR " +
                                    " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";
             
                        }
                    }
                    sql2 = "("+sql2+")";
                    
                   
                    sql += sql2 ;//+ " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    
                     String whereClause = "";
                     if(departmentId != 0)
                        whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                        " = "+departmentId + " AND ";
                    
                     if(oidSection != 0)
                        whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                        " = "+ oidSection + " AND ";
                    
                     if(whereClause != null && whereClause.length()>0){
                        whereClause = " AND "+ whereClause.substring(0,whereClause.length()-4);
                        sql = sql + whereClause;
                    }

                   sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            System.out.println("\tlistEarlyDataWeekly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                EarlyWeekly earlyWeekly = new EarlyWeekly();

                earlyWeekly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                earlyWeekly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)])));
                }
                earlyWeekly.setEmpSchedules1st(vectSchedule);

                // values schedule II
                Vector vectScheduleII = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectScheduleII.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)])));
                }
                earlyWeekly.setEmpSchedules2nd(vectScheduleII);

                // values actual in
                Vector vectActualIn = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i - 1)]) != null) 
                    {
                        vectActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i - 1)])));
                    }
                    else 
                    {
                        vectActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i - 1)]));
                    }
                }
                earlyWeekly.setEmpIn1st(vectActualIn);

                // values actual out
                Vector vectActualOut = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]) != null) {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)])));
                    } else {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]));
                    }
                }
                earlyWeekly.setEmpOut1st(vectActualOut);

                // values actual in shift II
                Vector vectShiftActualIn = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]) != null) 
                    {
                        vectShiftActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)])));
                    }
                    else 
                    {
                        vectShiftActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]));
                    }
                }
                earlyWeekly.setEmpIn2nd(vectShiftActualIn);

                // values actual out shift II
                Vector vectShiftActualOut = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]) != null) 
                    {
                        vectShiftActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)])));
                    }
                    else 
                    {
                        vectShiftActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]));
                    }
                }
                earlyWeekly.setEmpOut2nd(vectShiftActualOut);


                result.add(earlyWeekly);
            }
        }
        catch (Exception e) 
        {                         
            System.out.println("listLatenessDataWeekly exc : " + e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * yunny
     * untuk mencari data early home yang bulanan
     *
     * @param departmentId
     * @param selectedMonth
     * @param sectionId
     * @return
     */
    public static Vector listEarlyDataMonthly(long departmentId, Date selectedMonth,long sectionId) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        System.out.println("sectionId...."+sectionId);
        // get maximum date on selected month
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear() + 1900, selectedMonth.getMonth(), 1);
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);

        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // fields schedule 1st
            for (int i = 0; i < maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];
            }

            // fields schedule 2st
            for (int i = 0; i < maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];
            }

            // fields actual in
            for (int i = 0; i < maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i];
            }

            // fields actual out
            for (int i = 0; i < maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i];
            }

            // fields actual shift in
            for (int i = 0; i <= maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)];
            }

            // fields actual shift out
            for (int i = 0; i <= maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)];
            }

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " INNER JOIN "+PstSection.TBL_HR_SECTION+ " AS SECT"+
                    " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                    " = SECT."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId+
                    " AND ";
                  
     
                    // fields actual shift in
                    String whereClause = "";
                    if(sectionId !=0){
                       whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                        " = "+ sectionId + " AND ";
                    }
                    String sql2 = "";
                    for (int i = 0; i <= maxDayOnSelectedMonth; i++) 
                    {
                        if(sql2.length()==0)
                        {
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+" OR "+
                                    " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i + 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i + 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";
;
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+" OR "+
                                    " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";;
                        }
                    }
                    sql2 = "("+sql2+")";
                    sql += sql2;// + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    
                     if(whereClause != null && whereClause.length()>0){
                        whereClause = " AND "+ whereClause.substring(0,whereClause.length()-4);
                        sql = sql + whereClause;
                    }


                   sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                   
            System.out.println("\tlistEarlyDataMonthly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                EarlyMontly earlyMontly = new EarlyMontly();

                earlyMontly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                earlyMontly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule 1st
                Vector vectSchedule = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                earlyMontly.setEmpSchedules1st(vectSchedule);

                // values schedule 1st
                Vector vectSchedule2st = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule2st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                }
                earlyMontly.setEmpSchedules2nd(vectSchedule2st);

                // values actual in
                Vector vectActualIn = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]) != null) 
                    {
                        vectActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])));
                    }
                    else 
                    {
                        vectActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]));
                    }
                }
                earlyMontly.setEmpIn1st(vectActualIn);

                // values actual out
                Vector vectActualOut = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]) != null) 
                    {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])));
                    }
                    else 
                    {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]));
                    }
                }
                earlyMontly.setEmpOut1st(vectActualOut); 

                // values actual in
                Vector vectShiftActualIn = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]) != null) 
                    {
                        vectShiftActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                    }
                    else 
                    {
                        vectShiftActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                    }
                }
                earlyMontly.setEmpIn2nd(vectShiftActualIn);

                // values actual out
                Vector vectShiftActualOut = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]) != null) 
                    {
                        vectShiftActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                    }
                    else 
                    {
                        vectShiftActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                    }
                }
                earlyMontly.setEmpOut2nd(vectShiftActualOut);


                result.add(earlyMontly);
            }
        }
        catch (Exception e) 
        {
            System.out.println("listEarlyDataMonthly exc : " + e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    
    /** Creates a new instance of SessEmployeeEarly */
    public SessEmployeeEarly() {
    }
    
    
    
}
