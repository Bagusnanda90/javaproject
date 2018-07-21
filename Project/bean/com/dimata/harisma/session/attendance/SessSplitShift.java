/*
 * SessSplitShift.java
 *
 * Created on May 26, 2004, 2:34 PM
 */

package com.dimata.harisma.session.attendance;

// package core java
import java.util.Date;
import java.util.Vector;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.*;

// package qdep 
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.util.CalendarCalc;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

// package harisma 
import com.dimata.harisma.entity.masterdata.*; 
import com.dimata.harisma.entity.employee.*; 
import com.dimata.harisma.entity.attendance.*; 
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.LogicParser;

/**
 *
 * @author  gedhy
 */
public class SessSplitShift {
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
     * get list split shift data daily
     * @param departmentId
     * @param selectedDate
     * @return
     * @created by Edhy
     */        
    public static Vector listSplitShiftDataDaily(long departmentId, Date selectedDate)
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null;         
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);        
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);                
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +            
                         ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
                         ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                         ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +                
                         ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
                         ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                         ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +                                   
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1] +
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1] +
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
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
                         " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                         " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                         " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + 
                         " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +                             
                         " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +                             
                         " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +                             
                         " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + 
                         " = " + periodId +                          
                         " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + 
                         " = " + departmentId + 
                         " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + 
                         " = " + PstScheduleCategory.CATEGORY_SPLIT_SHIFT + 
                         " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                      
//                System.out.println("\tlistSplitShiftDataFromEmpSchedule : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                while(rs.next()) 
                {                     
                    SplitShiftDaily splitShiftDaily = new SplitShiftDaily();
                    
                    splitShiftDaily.setEmpNum(rs.getString(1));
                    splitShiftDaily.setEmpFullName(rs.getString(2));
                    splitShiftDaily.setScheduleSymbol1(rs.getString(3));
                    splitShiftDaily.setScheduleIn1st(rs.getTime(4));
                    splitShiftDaily.setScheduleOut1st(rs.getTime(5));
                    splitShiftDaily.setScheduleSymbol2(rs.getString(6));
                    splitShiftDaily.setScheduleIn2nd(rs.getTime(7));  
                    splitShiftDaily.setScheduleOut2nd(rs.getTime(8));
                    splitShiftDaily.setActualIn1st(rs.getTime(9));
                    splitShiftDaily.setActualIn2nd(rs.getTime(10));
                    splitShiftDaily.setActualOut1st(rs.getTime(11));
                    splitShiftDaily.setActualOut2nd(rs.getTime(12));
                    
                    result.add(splitShiftDaily);                    
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSplitShiftDataDaily exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }                  
    }    
    
    
    /**
     * get list split shift data daily
     * @param departmentId
     * @param selectedDate
     * @param sectionId
     * @return
     * @created by Yunny
     */        
    // update by devin 2014-01-29
    //public static Vector listSplitShiftDataDaily(long departmentId,Date selectedDate,long sectionId)
    public static Vector listSplitShiftDataDaily(long departmentId,long companyId,long divisionId, Date selectedDate,long sectionId,String empNumb,String fullName)
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null;         
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);        
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);                
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +            
                         ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
                         ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                         ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +                
                         ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] +
                         ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                         ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +                                   
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1] +
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1] +
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
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
                         " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                         " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                         " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + 
                         " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +                             
                         " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +                             
                         " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +                             
                         " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + 
                         " = " + periodId +                          
                         " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + 
                         " = " + PstScheduleCategory.CATEGORY_SPLIT_SHIFT;
                         
                if(departmentId!=0){
                   sql = sql + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + 
                         " = " + departmentId;
                }
            
                if(sectionId!=0){
                   sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + 
                         " = " + sectionId;
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
                  //update by devin 2014-01-29
                       /* if (empNumb != null && empNumb.length() > 0) {
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
            
               //System.out.println("\tlistSplitShiftDataFromEmpSchedule : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                while(rs.next()) 
                {                     
                    SplitShiftDaily splitShiftDaily = new SplitShiftDaily();
                    splitShiftDaily.setEmpNum(rs.getString(1));
                    splitShiftDaily.setEmpFullName(rs.getString(2));
                    splitShiftDaily.setScheduleSymbol1(rs.getString(3));
                    splitShiftDaily.setScheduleIn1st(rs.getTime(4));
                    splitShiftDaily.setScheduleOut1st(rs.getTime(5));
                    splitShiftDaily.setScheduleSymbol2(rs.getString(6));
                    splitShiftDaily.setScheduleIn2nd(rs.getTime(7));  
                    splitShiftDaily.setScheduleOut2nd(rs.getTime(8));
                    splitShiftDaily.setActualIn1st(rs.getTime(9));
                    splitShiftDaily.setActualIn2nd(rs.getTime(10));
                    splitShiftDaily.setActualOut1st(rs.getTime(11));
                    splitShiftDaily.setActualOut2nd(rs.getTime(12));
                    
                    result.add(splitShiftDaily);                    
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSplitShiftDataDaily exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }                  
    }    
    

    /**
     * get list split shift data weekly
     * @param departmentId
     * @param selectedDate
     * @return
     * @created by Edhy
     */    
    public static Vector listSplitShiftDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex)
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
        
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];   
                         
                         // schedule first
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)];                
                         }

                         // schedule second
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)];                
                         }

                         // in first
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)];                
                         }

                         // in second
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)];                
                         }

                         // out first
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)];                
                         }
            
                         // out second
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)];                
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
                      
                //System.out.println("\tlistSplitShiftDataWeekly : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();        
                while(rs.next()) 
                { 
                     SplitShiftWeekly splitShiftWeekly = new SplitShiftWeekly();
                  
                     splitShiftWeekly.setEmpNum(rs.getString(1));
                     splitShiftWeekly.setEmpName(rs.getString(2));
                     
                    // values schedule 1st
                    Vector vectSchedule1st = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        vectSchedule1st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)])));                        
                    }                                        
                    splitShiftWeekly.setEmpSchedules1st(vectSchedule1st);                    
                     

                    // values schedule 2nd
                    Vector vectSchedule2nd = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        vectSchedule2nd.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])));                        
                    }                                        
                    splitShiftWeekly.setEmpSchedules2nd(vectSchedule2nd);                    
                    

                    // values actual in 1st
                    Vector vectActualIn1st = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])!=null)
                        {                    
                            vectActualIn1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])));
                        }   
                        else
                        {
                            vectActualIn1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]));
                        }                                                                        
                    }                    
                    splitShiftWeekly.setEmpIn1st(vectActualIn1st);                    


                    // values actual in 2nd
                    Vector vectActualIn2nd = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])!=null)
                        {                    
                            vectActualIn2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])));
                        }   
                        else
                        {
                            vectActualIn2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]));
                        }                                                                        
                    }                    
                    splitShiftWeekly.setEmpIn2nd(vectActualIn2nd);                    
                    
                    
                    // values actual out 1st
                    Vector vectActualOut1st = new Vector();
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])!=null)
                        {                    
                            vectActualOut1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])));
                        }
                        else
                        {
                            vectActualOut1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]));
                        }                        
                    }                    
                    splitShiftWeekly.setEmpOut1st(vectActualOut1st);
                    

                    // values actual out 2nd
                    Vector vectActualOut2nd = new Vector();
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])!=null)
                        {                    
                            vectActualOut2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])));
                        }
                        else
                        {
                            vectActualOut2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]));
                        }                        
                    }                    
                    splitShiftWeekly.setEmpOut2nd(vectActualOut2nd);                    

                    result.add(splitShiftWeekly);                    
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSplitShiftDataWeekly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;  
        }                  
    }    
    
     /**
     * get list split shift data weekly
     * @param departmentId
     * @param selectedDate
     * @param sectionId
     * @return
     * @created by Yunny
     */    
    public static Vector listSplitShiftDataWeekly(long departmentId,long companyId,long divisionId, int iCalendarType, Date selectedMonth, int weekIndex,long sectionId,String empNum,String fullName)
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
        
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];   
                         
                         // schedule first
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)];                
                         }

                         // schedule second
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)];                
                         }

                         // in first
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)];                
                         }

                         // in second
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)];                
                         }

                         // out first
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)];                
                         }
            
                         // out second
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)];                
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
                      //update by devin 2014-01-30
                         
                 if (empNum != null && empNum != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNum);
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
                      
                System.out.println("\tlistSplitShiftDataWeekly : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();        
                while(rs.next()) 
                { 
                     SplitShiftWeekly splitShiftWeekly = new SplitShiftWeekly();
                  
                     splitShiftWeekly.setEmpNum(rs.getString(1));
                     splitShiftWeekly.setEmpName(rs.getString(2));
                     
                    // values schedule 1st
                    Vector vectSchedule1st = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        vectSchedule1st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)])));                        
                    }                                        
                    splitShiftWeekly.setEmpSchedules1st(vectSchedule1st);                    
                     

                    // values schedule 2nd
                    Vector vectSchedule2nd = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        vectSchedule2nd.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])));                        
                    }                                        
                    splitShiftWeekly.setEmpSchedules2nd(vectSchedule2nd);                    
                    

                    // values actual in 1st
                    Vector vectActualIn1st = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])!=null)
                        {                    
                            vectActualIn1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)])));
                        }   
                        else
                        {
                            vectActualIn1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (i-1)]));
                        }                                                                        
                    }                    
                    splitShiftWeekly.setEmpIn1st(vectActualIn1st);                    


                    // values actual in 2nd
                    Vector vectActualIn2nd = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])!=null)
                        {                    
                            vectActualIn2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])));
                        }   
                        else
                        {
                            vectActualIn2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]));
                        }                                                                        
                    }                    
                    splitShiftWeekly.setEmpIn2nd(vectActualIn2nd);                    
                    
                    
                    // values actual out 1st
                    Vector vectActualOut1st = new Vector();
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])!=null)
                        {                    
                            vectActualOut1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)])));
                        }
                        else
                        {
                            vectActualOut1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i-1)]));
                        }                        
                    }                    
                    splitShiftWeekly.setEmpOut1st(vectActualOut1st);
                    

                    // values actual out 2nd
                    Vector vectActualOut2nd = new Vector();
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])!=null)
                        {                    
                            vectActualOut2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)])));
                        }
                        else
                        {
                            vectActualOut2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]));
                        }                        
                    }                    
                    splitShiftWeekly.setEmpOut2nd(vectActualOut2nd);                    

                    result.add(splitShiftWeekly);                    
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSplitShiftDataWeekly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;  
        }                  
    }    

    
    /**
     * get list split shift data monthly
     * @param departmentId
     * @param selectedDate
     * @return
     * @created by Edhy
     */    
    public static Vector listSplitShiftDataMonthly(long departmentId, Date selectedMonth)
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null; 
        
        // get maximum date on selected month        
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear()+1900, selectedMonth.getMonth(), 1);                
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);         
                        
        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);                
        
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];   
                         
                         // schedule first
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];                
                         }

                         // schedule second
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];                
                         }

                         // in first
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i];                
                         }

                         // in second
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];                
                         }

                         // out first
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i];                
                         }
            
                         // out second
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];                
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
                      
//                System.out.println("\tlistSplitShiftDataWeekly : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                while(rs.next()) 
                { 
                    SplitShiftMonthly splitShiftMonthly = new SplitShiftMonthly();
                  
                    splitShiftMonthly.setEmpNum(rs.getString(1));
                    splitShiftMonthly.setEmpName(rs.getString(2));
                     
                    // values schedule 1st
                    Vector vectSchedule1st = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        vectSchedule1st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));                        
                    }                                        
                    splitShiftMonthly.setEmpSchedules1st(vectSchedule1st);                    
                     

                    // values schedule 2nd
                    Vector vectSchedule2nd = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        vectSchedule2nd.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));                        
                    }                                        
                    splitShiftMonthly.setEmpSchedules2nd(vectSchedule2nd);                    
                    

                    // values actual in 1st
                    Vector vectActualIn1st = new Vector();                      
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])!=null)
                        {                    
                            vectActualIn1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])));
                        }   
                        else
                        {
                            vectActualIn1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]));
                        }                                                                        
                    }                    
                    splitShiftMonthly.setEmpIn1st(vectActualIn1st);                    


                    // values actual in 2nd
                    Vector vectActualIn2nd = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])!=null)
                        {                    
                            vectActualIn2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                        }   
                        else
                        {
                            vectActualIn2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                        }                                                                        
                    }                    
                    splitShiftMonthly.setEmpIn2nd(vectActualIn2nd);                    
                    
                    
                    // values actual out 1st
                    Vector vectActualOut1st = new Vector();
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])!=null)
                        {                    
                            vectActualOut1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])));
                        }
                        else
                        {
                            vectActualOut1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]));
                        }                        
                    }                    
                    splitShiftMonthly.setEmpOut1st(vectActualOut1st);
                    

                    // values actual out 2nd
                    Vector vectActualOut2nd = new Vector();
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])!=null)
                        {                    
                            vectActualOut2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                        }
                        else
                        {
                            vectActualOut2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                        }                        
                    }                    
                    splitShiftMonthly.setEmpOut2nd(vectActualOut2nd);                    

                    result.add(splitShiftMonthly);                      
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSplitShiftDataMonthly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;  
        }                  
    }    
    
    
     /**
     * get list split shift data monthly
     * @param departmentId
     * @param selectedDate
     * @param sectionId
     * @return
     * @created by Yunny
     */    
    //update by devin 2014-01-29 public static Vector listSplitShiftDataMonthly(long departmentId, Date selectedMonth,long sectionId)
    public static Vector listSplitShiftDataMonthly(long departmentId,long companyId,long divisionId ,Date selectedMonth,long sectionId,String empNum,String fullName)
    {
        Vector result = new Vector(1,1);
        DBResultSet dbrs = null; 
        
        // get maximum date on selected month        
        GregorianCalendar calenderWeek = new GregorianCalendar(selectedMonth.getYear()+1900, selectedMonth.getMonth(), 1);                
        int maxDayOnSelectedMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);         
                        
        // get periodId of selected date
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedMonth);                
        
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];   
                         
                         // schedule first
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];                
                         }

                         // schedule second
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];                
                         }

                         // in first
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i];                
                         }

                         // in second
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];                
                         }

                         // out first
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i];                
                         }
            
                         // out second
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];                
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
                         " = " + companyId ;
                  }
                  //update by devin 2014-01-29
                  if(divisionId!=0){
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + 
                         " = " + divisionId ;
                  }
                   if(sectionId!=0){
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + 
                         " = " + sectionId ;
                  }
                    //update by devin 2014-01-30

                 if (empNum != null && empNum != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNum);
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
                
                System.out.println("\tlistSplitShiftDataWeekly : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                while(rs.next()) 
                { 
                    SplitShiftMonthly splitShiftMonthly = new SplitShiftMonthly();
                  
                    splitShiftMonthly.setEmpNum(rs.getString(1));
                    splitShiftMonthly.setEmpName(rs.getString(2));
                     
                    // values schedule 1st
                    Vector vectSchedule1st = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        vectSchedule1st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));                        
                    }                                        
                    splitShiftMonthly.setEmpSchedules1st(vectSchedule1st);                    
                     

                    // values schedule 2nd
                    Vector vectSchedule2nd = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        vectSchedule2nd.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));                        
                    }                                        
                    splitShiftMonthly.setEmpSchedules2nd(vectSchedule2nd);                    
                    

                    // values actual in 1st
                    Vector vectActualIn1st = new Vector();                      
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])!=null)
                        {                    
                            vectActualIn1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])));
                        }   
                        else
                        {
                            vectActualIn1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]));
                        }                                                                        
                    }                    
                    splitShiftMonthly.setEmpIn1st(vectActualIn1st);                    


                    // values actual in 2nd
                    Vector vectActualIn2nd = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])!=null)
                        {                    
                            vectActualIn2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                        }   
                        else
                        {
                            vectActualIn2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                        }                                                                        
                    }                    
                    splitShiftMonthly.setEmpIn2nd(vectActualIn2nd);                    
                    
                    
                    // values actual out 1st
                    Vector vectActualOut1st = new Vector();
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])!=null)
                        {                    
                            vectActualOut1st.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])));
                        }
                        else
                        {
                            vectActualOut1st.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]));
                        }                        
                    }                    
                    splitShiftMonthly.setEmpOut1st(vectActualOut1st);
                    

                    // values actual out 2nd
                    Vector vectActualOut2nd = new Vector();
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                     
                        if(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])!=null)
                        {                    
                            vectActualOut2nd.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]),rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                        }
                        else
                        {
                            vectActualOut2nd.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                        }                        
                    }                    
                    splitShiftMonthly.setEmpOut2nd(vectActualOut2nd);                    

                    result.add(splitShiftMonthly);                      
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSplitShiftDataMonthly exc : "+e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;  
        }                  
    }    

    
    /**
     * get status if vectSchedule contains split shift schedule
     * @param vectSchedule
     * @return
     * @created by Edhy
     */     
    public static boolean containSchldSplitShift(Vector vectSchedule)
    {
        boolean result = false;
        
        String listSplitShift = "";
        if(vectSchedule!=null && vectSchedule.size()>0)
        {
            int maxSchld = vectSchedule.size();
            for(int i=0; i<maxSchld; i++)
            {
                listSplitShift += String.valueOf(vectSchedule.get(i)) + ",";
            }
            
            if(listSplitShift!=null && listSplitShift.length()>0)
            {
                listSplitShift = listSplitShift.substring(0,listSplitShift.length()-1);
                
                DBResultSet dbrs = null;         
                try {
                    String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +                             
                                 " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                                 " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +
                                 " ON CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +
                                 " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +                          
                                 " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + 
                                 " IN ( " + listSplitShift + ")";                         

                        //System.out.println("\tcontainSchldNightShift : "+sql);
                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();     
                        while(rs.next())
                        {
                            if(rs.getInt(1) == PstScheduleCategory.CATEGORY_SPLIT_SHIFT)
                            {
                                result = true;
                                break;
                            }                        
                        }
                }
                catch(Exception e)
                {
                    System.out.println("containSchldSplitShift exc : "+e.toString());
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
    
    public Vector<SplitShiftMonthlyRekap> getListMonth(Vector listSplitShiftPresence, int dateOfMonth, Date endDate) {

        Vector list = new Vector(1, 1);
        
        SplitShiftMonthlyRekap splitShiftMonthlyRekap= null;
        
        if (listSplitShiftPresence != null && listSplitShiftPresence.size() > 0) {
            
            int maxAbsenteeismPresence = listSplitShiftPresence.size();
            int dataAmount = 0;
            try {
                for (int i = 0; i < maxAbsenteeismPresence; i++) {
                    SplitShiftMonthly splitShiftMonthly = (SplitShiftMonthly)listSplitShiftPresence.get(i);
			String empNum = splitShiftMonthly.getEmpNum();
			String empName = splitShiftMonthly.getEmpName();
			Vector empSchedules1st = splitShiftMonthly.getEmpSchedules1st();
			Vector empActualIn1st = splitShiftMonthly.getEmpIn1st();
			Vector empActualOut1st = splitShiftMonthly.getEmpOut1st();
			Vector empSchedules2nd = splitShiftMonthly.getEmpSchedules2nd();
			Vector empActualIn2nd = splitShiftMonthly.getEmpIn2nd();
			Vector empActualOut2nd = splitShiftMonthly.getEmpOut2nd();
			
			// check apakah dalam vector schedule ada schedule tipe SPLIT SHIFT ???			
			boolean containSchldSplitShift = SessSplitShift.containSchldSplitShift(empSchedules1st); 
			int startPeriodSplit = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));									
			startPeriodSplit = startPeriodSplit - 1;
			if(containSchldSplitShift)
			{
				int totalSplitShift = 0;
				int presenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
				//Vector rowxMonth = new Vector(1,1);
				for(int isch=0; isch<empSchedules1st.size(); isch++)
				{
					if(startPeriodSplit==dateOfMonth){
						startPeriodSplit = 1;
					}
					else{
						startPeriodSplit = startPeriodSplit +1;
					}
					String schldSymbol1st = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules1st.get(startPeriodSplit-1))),PstScheduleCategory.CATEGORY_SPLIT_SHIFT);
					if(schldSymbol1st!=null && schldSymbol1st.length()>0)
					{
						String strDuration = "";
						Date dateActualIn1st = (Date)empActualIn1st.get(startPeriodSplit-1);
						Date dateActualOut1st = (Date)empActualOut1st.get(startPeriodSplit-1);
						Date dateActualIn2nd = (Date)empActualIn2nd.get(startPeriodSplit-1);
						Date dateActualOut2nd = (Date)empActualOut2nd.get(startPeriodSplit-1);						
						if((dateActualIn2nd != null && dateActualOut2nd != null) && (dateActualIn1st != null && dateActualOut1st != null)){			
							long iDuration1st = dateActualOut1st.getTime()/60000 - dateActualIn1st.getTime()/60000;
							long iDuration2nd = dateActualOut2nd.getTime()/60000 - dateActualIn2nd.getTime()/60000;							
							long iDuration = iDuration1st + iDuration2nd;
							long iDurationHour = (iDuration - (iDuration % 60)) / 60;

							// ngecek durasi waktu apakah 8 jam atau lebih
							//if(iDurationHour>=8)
							//{							
								long iDurationMin = iDuration % 60;
								String strDurationHour = iDurationHour + "h, ";
								String strDurationMin = iDurationMin + "m";
								strDuration = strDurationHour + strDurationMin;
								presenceNull += 1; 							
							//}	
						}else{
							strDuration = "";
						}					
						//rowxMonth.add(strDuration);
						
						if(strDuration!=null && strDuration.length()>0)
						{
							totalSplitShift += 1;
						}
					}
					else
					{
						//rowxMonth.add("");											
					}
				}								
				
				if(presenceNull>0 && totalSplitShift>0)
				{
                                    
                                    splitShiftMonthlyRekap = new SplitShiftMonthlyRekap();
					
					splitShiftMonthlyRekap.setEmpNum(empNum);  
					splitShiftMonthlyRekap.setEmpName(empName);
                                        splitShiftMonthlyRekap.setMonth(endDate.getMonth());
					splitShiftMonthlyRekap.setTotalMonth(totalSplitShift);
                                        
                                        list.add(splitShiftMonthlyRekap);
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
