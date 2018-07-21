/*
 * SessNightShift.java
 *
 * Created on June 1, 2004, 3:36 PM
 */

package com.dimata.harisma.session.attendance;

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

/**
 *
 * @author  gedhy
 */
public class SessNightShift 

{    
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
    
    /**
     * get list night shift data daily
     * asumsi kalo night shift berarti schedule out adalah pd hari berikutnya :)
     * @param <CODE>departmentId</CODE>department selected by user
     * @param <CODE>selectedDate</CODE>date selected by user
     * @return
     * @created by Edhy
     */
    public static Vector listNightShiftDataDaily(long departmentId, Date selectedDate) 
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
            ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
            ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
            ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
            ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +
            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1] +
            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1] +
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
            " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
            " = " + PstScheduleCategory.CATEGORY_NIGHT_WORKER +
            " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
           // System.out.println("\tlistSplitShiftDataFromEmpSchedule : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate()+1);
            while(rs.next()) 
            {
                NightShiftDaily nightShiftDaily = new NightShiftDaily();
                
                nightShiftDaily.setEmpNum(rs.getString(1));
                nightShiftDaily.setEmpName(rs.getString(2));
                nightShiftDaily.setSchldSymbol(rs.getString(3));
                
                // if schld In is 00:00, its mean in the next day
                Time time = rs.getTime(4);
                if(time.getHours()==0 && time.getMinutes()==0) 
                {
                    if(rs.getTime(4)!=null) {
                        nightShiftDaily.setSchldIn(DBHandler.convertDate(sqlNextDate, rs.getTime(4)));
                    }
                    else 
                    {
                        nightShiftDaily.setSchldIn(rs.getTime(4));
                    }
                }
                else 
                {
                    if(rs.getTime(4)!=null) 
                    {
                        nightShiftDaily.setSchldIn(DBHandler.convertDate(sqlCurrDate, rs.getTime(4)));
                    }
                    else 
                    {
                        nightShiftDaily.setSchldIn(rs.getTime(4));
                    }
                }
                
                if(rs.getTime(5)!=null) 
                {
                    nightShiftDaily.setSchldOut(DBHandler.convertDate(sqlNextDate, rs.getTime(5)));
                }
                else 
                {
                    nightShiftDaily.setSchldOut(rs.getTime(5));
                }
                
                if(rs.getTime(6)!=null) 
                {
                    nightShiftDaily.setActualIn(DBHandler.convertDate(rs.getDate(6),rs.getTime(6)));
                }
                else 
                {
                    nightShiftDaily.setActualIn(rs.getTime(6));
                }
                
                if(rs.getTime(7)!=null) 
                {
                    nightShiftDaily.setActualOut(DBHandler.convertDate(rs.getDate(7),rs.getTime(7)));
                }
                else 
                {
                    nightShiftDaily.setActualOut(rs.getTime(7));
                }
                
                result.add(nightShiftDaily);
            }
        }
        catch(Exception e) 
        {
            System.out.println("listNightShiftDataDaily exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
     /**
     * get list night shift data daily
     * asumsi kalo night shift berarti schedule out adalah pd hari berikutnya :)
     * @param <CODE>departmentId</CODE>department selected by user
     * @param <CODE>selectedDate</CODE>date selected by user
     * @param <CODE>sectionId</CODE>date selected by user
     * @return
     * @created by Edhy
     */
    //update by devin 2014-01-29 
    //public static Vector listNightShiftDataDaily(long departmentId, Date selectedDate,long sectionId)
    public static Vector listNightShiftDataDaily(long departmentId,long companyId,long divisionId, Date selectedDate,long sectionId,String empNumb, String fullName)
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
            ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
            ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
            ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
            ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +
            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1] +
            ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1] +
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
            " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
            " = " + PstScheduleCategory.CATEGORY_NIGHT_WORKER;
            
           if(departmentId!=0){
            sql = sql + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                " = " + departmentId;
           }
           //update by devin 2014-01-29
            if(companyId!=0){
            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +
                " = " + companyId;
           }
             if(divisionId!=0){
            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +
                " = " + divisionId;
           }
            
           if(sectionId!=0){
            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                " = " + sectionId;
           }
           //update by devin 2014-01-29
           /*if(empNumb !=null && empNumb.length() > 0){
                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE " + "\"%" + empNumb.trim() + "%\"";
                        }
           
                        if(fullName !=null && fullName.length() > 0){
                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE " + "\"%" + fullName.trim() + "%\"";
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
                            sql = sql +" "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
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
                            sql = sql +" "+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }
           
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
           /// System.out.println("\tlistSplitShiftDataFromEmpSchedule : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate()+1);
            while(rs.next()) 
            {
                NightShiftDaily nightShiftDaily = new NightShiftDaily();
                
                nightShiftDaily.setEmpNum(rs.getString(1));
                nightShiftDaily.setEmpName(rs.getString(2));
                nightShiftDaily.setSchldSymbol(rs.getString(3));
                
                // if schld In is 00:00, its mean in the next day
                Time time = rs.getTime(4);
                if(time.getHours()==0 && time.getMinutes()==0) 
                {
                    if(rs.getTime(4)!=null) {
                        nightShiftDaily.setSchldIn(DBHandler.convertDate(sqlNextDate, rs.getTime(4)));
                    }
                    else 
                    {
                        nightShiftDaily.setSchldIn(rs.getTime(4));
                    }
                }
                else 
                {
                    if(rs.getTime(4)!=null) 
                    {
                        nightShiftDaily.setSchldIn(DBHandler.convertDate(sqlCurrDate, rs.getTime(4)));
                    }
                    else 
                    {
                        nightShiftDaily.setSchldIn(rs.getTime(4));
                    }
                }
                
                if(rs.getTime(5)!=null) 
                {
                    nightShiftDaily.setSchldOut(DBHandler.convertDate(sqlNextDate, rs.getTime(5)));
                }
                else 
                {
                    nightShiftDaily.setSchldOut(rs.getTime(5));
                }
                
                if(rs.getTime(6)!=null) 
                {
                    nightShiftDaily.setActualIn(DBHandler.convertDate(rs.getDate(6),rs.getTime(6)));
                }
                else 
                {
                    nightShiftDaily.setActualIn(rs.getTime(6));
                }
                
                if(rs.getTime(7)!=null) 
                {
                    nightShiftDaily.setActualOut(DBHandler.convertDate(rs.getDate(7),rs.getTime(7)));
                }
                else 
                {
                    nightShiftDaily.setActualOut(rs.getTime(7));
                }
                
                result.add(nightShiftDaily);
            }
        }
        catch(Exception e) 
        {
            System.out.println("listNightShiftDataDaily exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    
    /**
     * get list night shift data weekly
     * @param <CODE>departmentId</CODE>selected department
     * @param <CODE>selectedMonth</CODE>selected month
     * @param <CODE>weekIndex</CODE>selected week of selected month
     * @created by Edhy
     */
    public static Vector listNightShiftDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex) 
    {
        Vector result = new Vector(1,1);
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
            for(int i=intStartDate; i<=intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)];
            }
            
            // fields actual in
            for(int i=intStartDate; i<=intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)];
            }
            
            // fields actual out
            for(int i=intStartDate; i<=intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)];
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
            
            //System.out.println("\tlistNightShiftDataWeekly : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) 
            {
                NightShiftWeekly nightShiftWeekly = new NightShiftWeekly();
                
                nightShiftWeekly.setEmpNum(rs.getString(1)!=null ? rs.getString(1) : "");
                nightShiftWeekly.setEmpName(rs.getString(2)!=null ? rs.getString(2) : "");
                
                // values schedule
                Vector vectSchedule = new Vector();
                for(int i=intStartDate; i<=intEndDate; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)])));
                }
                nightShiftWeekly.setEmpSchedules(vectSchedule);
                
                // values actual in
                Vector vectActualIn = new Vector();
                for(int i=intStartDate; i<=intEndDate; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])!=null) 
                    {
                        vectActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])));
                    }
                    else 
                    {
                        vectActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]));
                    }
                }
                nightShiftWeekly.setEmpIn(vectActualIn);
                
                // values actual out
                Vector vectActualOut = new Vector();
                for(int i=intStartDate; i<=intEndDate; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])!=null) 
                    {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])));
                    }
                    else 
                    {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]));
                    }
                }
                nightShiftWeekly.setEmpOut(vectActualOut);
                
                result.add(nightShiftWeekly);
            }
        }
        catch(Exception e) 
        {
            System.out.println("listNightShiftDataWeekly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * get list night shift data weekly
     * @param <CODE>departmentId</CODE>selected department
     * @param <CODE>selectedMonth</CODE>selected month
     * @param <CODE>weekIndex</CODE>selected week of selected month
     * @param <CODE>sectionId</CODE>selected week of selected month
     * @created by Yunny
     */
    //update by devin 2013-01-29
    //public static Vector listNightShiftDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex,long sectionId) 
    public static Vector listNightShiftDataWeekly(long departmentId,long companyId,long divisionId,int iCalendarType, Date selectedMonth, int weekIndex,long sectionId,String empNumb, String fullName) 
    {
        Vector result = new Vector(1,1);
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
            for(int i=intStartDate; i<=intEndDate; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)];
            }
            
            // fields actual in
            for(int i=intStartDate; i<=intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)];
            }
            
            // fields actual out
            for(int i=intStartDate; i<=intEndDate; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)];
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
           if(departmentId!=0){
           sql = sql + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
            " = " + departmentId ;
           }
            if(companyId!=0){
           sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +
            " = " + departmentId ;
           }
             if(divisionId!=0){
           sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +
            " = " + departmentId ;
           }
            
           if(sectionId!=0){
            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
            " = " + sectionId;
           }
           //update by devin 2014-01-29
           /*if(empNumb !=null && empNumb.length() > 0){
                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE " + "\"%" + empNumb.trim() + "%\"";
                        }
            
                        if(fullName !=null && fullName.length() > 0){
                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE " + "\"%" + fullName.trim() + "%\"";
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
                            sql = sql +" "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
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
                            sql = sql +" "+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }
           
            
          sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
            //System.out.println("\tlistNightShiftDataWeekly : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) 
            {
                NightShiftWeekly nightShiftWeekly = new NightShiftWeekly();
                
                nightShiftWeekly.setEmpNum(rs.getString(1)!=null ? rs.getString(1) : "");
                nightShiftWeekly.setEmpName(rs.getString(2)!=null ? rs.getString(2) : "");
                
                // values schedule
                Vector vectSchedule = new Vector();
                for(int i=intStartDate; i<=intEndDate; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)])));
                }
                nightShiftWeekly.setEmpSchedules(vectSchedule);
                
                // values actual in
                Vector vectActualIn = new Vector();
                for(int i=intStartDate; i<=intEndDate; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])!=null) 
                    {
                        vectActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])));
                    }
                    else 
                    {
                        vectActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]));
                    }
                }
                nightShiftWeekly.setEmpIn(vectActualIn);
                
                // values actual out
                Vector vectActualOut = new Vector();
                for(int i=intStartDate; i<=intEndDate; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])!=null) 
                    {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])));
                    }
                    else 
                    {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]));
                    }
                }
                nightShiftWeekly.setEmpOut(vectActualOut);
                
                result.add(nightShiftWeekly);
            }
        }
        catch(Exception e) 
        {
            System.out.println("listNightShiftDataWeekly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    
    /**
     * get list night shift data monthly
     * @param <CODE>departmentId</CODE>selected department
     * @param <CODE>selectedMonth</CODE>selected month
     * @created by Edhy
     */
    public static Vector listNightShiftDataMonthly(long departmentId, Date selectedMonth) 
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null;
        
        // get maximum date on selected month
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear()+1900, selectedMonth.getMonth(), 1);
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);
        
        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
            ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            
            // fields schedule
            for(int i=0; i<maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];
            }
            
            // fields actual in
            for(int i=0; i<maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i];
            }
            
            // fields actual out
            for(int i=0; i<maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i];
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
            
            //System.out.println("\tlistNightShiftDataWeekly : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) 
            {
                NightShiftMonthly nightShiftMonthly = new NightShiftMonthly();
                
                nightShiftMonthly.setEmpNum(rs.getString(1)!=null ? rs.getString(1) : "");
                nightShiftMonthly.setEmpName(rs.getString(2)!=null ? rs.getString(2) : "");
                
                // values schedule
                Vector vectSchedule = new Vector();
                for(int i=0; i<maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                nightShiftMonthly.setEmpSchedules(vectSchedule);
                
                // values actual in
                Vector vectActualIn = new Vector();
                for(int i=0; i<maxDayOnSelectedMonth; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])!=null) 
                    {
                        vectActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])));
                    }
                    else 
                    {
                        vectActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]));
                    }
                }
                nightShiftMonthly.setEmpIn(vectActualIn);
                
                // values actual out
                Vector vectActualOut = new Vector();
                for(int i=0; i<maxDayOnSelectedMonth; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])!=null) 
                    {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])));
                    }
                    else 
                    {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]));
                    }
                }
                nightShiftMonthly.setEmpOut(vectActualOut);
                
                result.add(nightShiftMonthly);
            }
        }
        catch(Exception e) 
        {
            System.out.println("listNightShiftDataMonthly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
     /**
     * get list night shift data monthly
     * @param <CODE>departmentId</CODE>selected department
     * @param <CODE>selectedMonth</CODE>selected month
     * @param <CODE>sectionId</CODE>selected section
     * @created by Yunny
     */
    //update by devin 2014-01-29
    //public static Vector listNightShiftDataMonthly(long departmentId,Date selectedMonth,long sectionId)
    public static Vector listNightShiftDataMonthly(long departmentId,long companyId,long divisionId, Date selectedMonth,long sectionId,String empNumb, String fullName) 
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null;
        
        // get maximum date on selected month
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear()+1900, selectedMonth.getMonth(), 1);
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);
        
        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
            ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            
            // fields schedule
            for(int i=0; i<maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];
            }
            
            // fields actual in
            for(int i=0; i<maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i];
            }
            
            // fields actual out
            for(int i=0; i<maxDayOnSelectedMonth; i++) 
            {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i];
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
            if(departmentId!=0){
                sql = sql + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                " = " + departmentId ;
            }
              if(companyId!=0){
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +
                " = " + companyId ;
            }
              if(divisionId!=0){
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +
                " = " + divisionId ;
            }
            
            if(sectionId!=0){
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                " = " + sectionId ;
            }
             //update by devin 2014-01-29
           /*if(empNumb !=null && empNumb.length() > 0){
                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE " + "\"%" + empNumb.trim() + "%\"";
                        }
            
                        if(fullName !=null && fullName.length() > 0){
                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE " + "\"%" + fullName.trim() + "%\"";
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
                            sql = sql +" "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
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
                            sql = sql +" "+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }
           
           sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
            System.out.println("\tlistNightShiftDataWeekly : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) 
            {
                NightShiftMonthly nightShiftMonthly = new NightShiftMonthly();
                
                nightShiftMonthly.setEmpNum(rs.getString(1)!=null ? rs.getString(1) : "");
                nightShiftMonthly.setEmpName(rs.getString(2)!=null ? rs.getString(2) : "");
                
                // values schedule
                Vector vectSchedule = new Vector();
                for(int i=0; i<maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                nightShiftMonthly.setEmpSchedules(vectSchedule);
                
                // values actual in
                Vector vectActualIn = new Vector();
                for(int i=0; i<maxDayOnSelectedMonth; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])!=null) 
                    {
                        vectActualIn.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])));
                    }
                    else 
                    {
                        vectActualIn.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]));
                    }
                }
                nightShiftMonthly.setEmpIn(vectActualIn);
                
                // values actual out
                Vector vectActualOut = new Vector();
                for(int i=0; i<maxDayOnSelectedMonth; i++) 
                {
                    if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])!=null) 
                    {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])));
                    }
                    else 
                    {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]));
                    }
                }
                nightShiftMonthly.setEmpOut(vectActualOut);
                
                result.add(nightShiftMonthly);
            }
        }
        catch(Exception e) 
        {
            System.out.println("listNightShiftDataMonthly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    
    /**
     * get status if vectSchedule contains night shift schedule
     * @param vectSchedule
     * @return
     * @created by Edhy
     */
    public static boolean containSchldNightShift(Vector vectSchedule) 
    {
        boolean result = false;
        
        String listNightShift = "";
        if(vectSchedule!=null && vectSchedule.size()>0) 
        {
            int maxSchld = vectSchedule.size();
            for(int i=0; i<maxSchld; i++) 
            {
                listNightShift += String.valueOf(vectSchedule.get(i)) + ",";
            }
            
            if(listNightShift!=null && listNightShift.length()>0) 
            {
                listNightShift = listNightShift.substring(0,listNightShift.length()-1);
                
                DBResultSet dbrs = null;
                try 
                {
                    String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                    " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                    " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                    " ON CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                    " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                    " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    " IN ( " + listNightShift + ")";
                    
//                    System.out.println("\tcontainSchldNightShift : "+sql);
                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    while(rs.next()) 
                    {
                        if(rs.getInt(1) == PstScheduleCategory.CATEGORY_NIGHT_WORKER) 
                        {
                            result = true;
                            break;
                        }
                    }
                }
                catch(Exception e) 
                {
                    System.out.println("containSchldNightShift exc : "+e.toString());
                }
                finally 
                {
                    DBResultSet.close(dbrs);
                    return result;
                }
            }
            else 
            {
                return result;
            }
        }
        return result;
    }
    
    
    /** get status if specified presence is valid for NIGHT SHIFT schedule
     * @param nightShiftScheduleId
     * @param datePresenceIn
     * @param datePresenceOut
     * @return
     * @created by Edhy
     */
    public static boolean validNightShiftPresence(long nightShiftScheduleId, Date datePresenceIn, Date datePresenceOut) 
    {
        boolean result = false;
        
        // benar2 night shift jika antara IN dan OUT adalah berbeda hari
        if(datePresenceIn!=null && datePresenceOut!=null)
        {
            int dayIn = datePresenceIn.getDate();
            int dayOut = datePresenceOut.getDate();
            if( (dayOut - dayIn) > 0 ) 
            {
                result = true;
            }
        }
        
        return result;
    }    
    public Vector<NightShiftMonthlyRekap> getListMonth(Vector listNightShiftPresence, int dateOfMonth, Date endDate) {
    
        Vector result = new Vector(1,1);
        Vector list = new Vector(1, 1);
        NightShiftMonthlyRekap nightShiftMonthlyRekap = null;
        
	if(listNightShiftPresence!=null && listNightShiftPresence.size()>0)
	{
		int startPeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
		
		
		// vector of data will used in pdf report
		Vector vectDataToPdf = new Vector(1,1);									

		int maxNightShiftPresence = listNightShiftPresence.size();  
		int dataAmount = 0;									 							
		for(int i=0; i<maxNightShiftPresence; i++) 
		{
			NightShiftMonthly nightShiftWeekly = (NightShiftMonthly)listNightShiftPresence.get(i);
			String empNum = nightShiftWeekly.getEmpNum();
			String empName = nightShiftWeekly.getEmpName();
			Vector empSchedules = nightShiftWeekly.getEmpSchedules();
			Vector empActualIn = nightShiftWeekly.getEmpIn();
			Vector empActualOut = nightShiftWeekly.getEmpOut();
			
			// check apakah dalam vector schedule ada schedule tipe NIGHT SHIFT ???			
			boolean containSchldNightShift = SessNightShift.containSchldNightShift(empSchedules);			
			int startPeriodNight = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));			
			startPeriodNight = startPeriodNight - 1;
			if(containSchldNightShift)
			{
				int totalNightShift = 0;
				int presenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
				Vector rowxMonth = new Vector(1,1);				
				for(int isch=0; isch<empSchedules.size(); isch++)
				{
					if(startPeriodNight == dateOfMonth){
						startPeriodNight =1;
					}
					else{
						startPeriodNight  = startPeriodNight + 1;
					}
					long nightShiftScheduleId = Long.parseLong(String.valueOf(empSchedules.get(startPeriodNight-1)));				
					String schldSymbol = PstScheduleSymbol.getScheduleSymbol(nightShiftScheduleId,PstScheduleCategory.CATEGORY_NIGHT_WORKER);
					if(schldSymbol!=null && schldSymbol.length()>0)
					{
						String strDuration = "";
						Date dateActualIn = (Date)empActualIn.get(startPeriodNight-1);
						Date dateActualOut = (Date)empActualOut.get(startPeriodNight-1);
						boolean presenceIsNightShift = SessNightShift.validNightShiftPresence(nightShiftScheduleId,dateActualIn,dateActualOut);
						if(dateActualIn != null && dateActualOut != null && presenceIsNightShift){  			
							long iDuration = dateActualOut.getTime()/60000 - dateActualIn.getTime()/60000;
							long iDurationHour = (iDuration - (iDuration % 60)) / 60;
							long iDurationMin = iDuration % 60;
							String strDurationHour = iDurationHour + "h, ";
							String strDurationMin = iDurationMin + "m";
							strDuration = strDurationHour + strDurationMin;
							presenceNull += 1;
						}else{
							strDuration = "";
						}					
						rowxMonth.add(strDuration);						
						
						if(strDuration!=null && strDuration.length()>0)
						{
							totalNightShift += 1;
						}
					}
					else
					{
						rowxMonth.add("");											
					}
				}								
				
				
				if(presenceNull>0)
				{
                                    nightShiftMonthlyRekap = new NightShiftMonthlyRekap();
                                    nightShiftMonthlyRekap.setEmpNum(empNum);
                                    nightShiftMonthlyRekap.setEmpName(empName);
                                    nightShiftMonthlyRekap.setTotalMonth(totalNightShift);
                                    nightShiftMonthlyRekap.setMonth(endDate.getMonth());
                                    
                                    list.add(nightShiftMonthlyRekap);
				}													

			}			
		}
									
	}
	return list;
    }
}
