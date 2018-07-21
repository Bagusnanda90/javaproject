/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Apr 7, 2004
 * Time: 1:25:38 PM
 * To change this template use Options | File Templates.
 */
package com.dimata.harisma.session.lateness;

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
import com.dimata.util.LogicParser;

import java.util.Vector;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SessEmployeeLateness {

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
     * gadnyana
     * pencarian data late untuk harian  
     *
     * @param departmentId
     * @param selectedDate
     * @return
     */
    public static Vector listLatenessDaily(long departmentId, Date selectedDate) 
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
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " AND ((SCH." + PstEmpSchedule.fieldNames[firstFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_LATE + ") OR " +
                    " (SCH." + PstEmpSchedule.fieldNames[secondFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_LATE + "))" +
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

           System.out.println("\tlistlateness daily : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            while (rs.next()) 
            {
                LatenessDaily latenessDaily = new LatenessDaily();
                latenessDaily.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                latenessDaily.setEmpName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                latenessDaily.setDepId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                latenessDaily.setSchldSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                latenessDaily.setSchSymbolId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                latenessDaily.setCatType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));

                // if schld In is 00:00, its mean in the next day
                if (rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]) != null) 
                {
                    latenessDaily.setSchldIn(DBHandler.convertDate(sqlNextDate, rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN])));
                }
                else 
                {
                    latenessDaily.setSchldIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                }

                // get symbol out time
                if (rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]) != null) 
                {
                    latenessDaily.setSchldOut(DBHandler.convertDate(sqlNextDate, rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT])));
                }
                else 
                {
                    latenessDaily.setSchldOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                }

                // get shift in I
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualIn(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualIn(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]));
                }

                // get shift out I
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualOut(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualOut(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]));
                }

                // get shift in II
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualInII(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualInII(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                }

                // get shift out II
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualOutII(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualOutII(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                }

                result.add(latenessDaily);
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
     * Yunny
     * pencarian data late untuk harian  
     *
     * @param departmentId
     * @param selectedDate
     * @param sectionId
     * @return
     */
    //update by satrya 2012-10-03
    //public static Vector listLatenessDaily(long departmentId, Date selectedDate,long sectionId,) 
    public static Vector listLatenessDaily(long departmentId,long companyId,long divisionId, Date selectedDate,long sectionId,String empNumb, String fullName) 
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
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)] +
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (idxFieldName - 1)] +
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)] +
                    ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +
                    //update by satrya 2012-11-10
                    ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
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
                  if(departmentId !=0){
                   sql = sql+ " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId ;
                   }
                   //update by devin 2014-01-28
                    if(companyId!=0){
                        
                        sql = sql + " AND EMP. "+PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+" = "+companyId;
                              
                    }
                     if(divisionId!=0){
                        
                        sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+" = "+divisionId;
                              
                    }
                  //update by satrya 2012-11-07
                    sql = sql +" AND ((SCH." + PstEmpSchedule.fieldNames[firstFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_LATE + ") OR " +
                    " (SCH." + PstEmpSchedule.fieldNames[secondFieldIndex] +
                    " = " + PstEmpSchedule.STATUS_PRESENCE_LATE + ") OR"
                    + " (SCH."+PstEmpSchedule.fieldNames[firstFieldIndex] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+") OR (SCH."
                    +  PstEmpSchedule.fieldNames[secondFieldIndex] + " = " +PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+"))" ;
                    
                        
                                //update by satrya 2012-10-03
//                   if(empNumb !=null && empNumb.length() > 0){
//                       sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE " + "\"%" + empNumb.trim() + "%\"";
//                   }
//                   if(fullName !=null && fullName.length() > 0){
//                       sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE " + "\"%" + fullName.trim() + "%\"";
//                   }
                   //update by satrya 2012-07-16
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
                            sql = sql +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }
                
            }
            //update by satrya 2012-07-16
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
                            sql = sql +" EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }

                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

           //System.out.println("\tlistlateness daily : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            while (rs.next()) 
            {
                LatenessDaily latenessDaily = new LatenessDaily();
                latenessDaily.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                latenessDaily.setEmpName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                latenessDaily.setDepId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                latenessDaily.setSchldSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                latenessDaily.setSchSymbolId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                latenessDaily.setCatType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));

                // if schld In is 00:00, its mean in the next day
                if (rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]) != null) 
                {
                    latenessDaily.setSchldIn(DBHandler.convertDate(sqlNextDate, rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN])));
                }
                else 
                {
                    latenessDaily.setSchldIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                }

                // get symbol out time
                if (rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]) != null) 
                {
                    latenessDaily.setSchldOut(DBHandler.convertDate(sqlNextDate, rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT])));
                }
                else 
                {
                    latenessDaily.setSchldOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                }

                // get shift in I
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualIn(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualIn(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (idxFieldName - 1)]));
                }

                // get shift out I
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualOut(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualOut(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (idxFieldName - 1)]));
                }

                // get shift in II
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualInII(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualInII(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                }

                // get shift out II
                if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]) != null) 
                {
                    latenessDaily.setActualOutII(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)])));
                }
                else 
                {
                    latenessDaily.setActualOutII(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (idxFieldName - 1)]));
                }
                //update by satrya 2012-11-10
                latenessDaily.setEmpId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                latenessDaily.setSelectedDate(selectedDate);
                

                result.add(latenessDaily);
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

    public static Vector listLatenessDaily(long departmentId,long companyId,long divisionId, Date fromDate,Date toDate,long sectionId,String empNumb, String fullName){
        
        Vector result = new Vector();

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
                Vector dLateneess = listLatenessDaily(departmentId,companyId,divisionId,selectedDate, sectionId, empNumb, fullName);
                 if (dLateneess != null && dLateneess.size() > 0) { 
                result.addAll(dLateneess);
                 }
            }
        }
        return result;
    }
    /**
     * gadnyana
     * untuk pencarian data lateness yang mingguan
     * @param departmentId
     * @param selectedMonth
     * @return
     */
    public static Vector listLatenessDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        System.out.println("weekIndex  "+weekIndex);
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
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " AND ";

                    // fields actual shift in
                    String sql2 = "";
                    for (int i = intStartDate; i <= intEndDate; i++) 
                    {
                        if(sql2.length()==0)
                        {
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                    }
                    sql2 = "("+sql2+")";
                    sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistLatenessDataWeekly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                LatenessWeekly latenessWeekly = new LatenessWeekly();

                latenessWeekly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                latenessWeekly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)])));
                }
                latenessWeekly.setEmpSchedules1st(vectSchedule);

                // values schedule II
                Vector vectScheduleII = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectScheduleII.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)])));
                }
                latenessWeekly.setEmpSchedules2nd(vectScheduleII);

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
                latenessWeekly.setEmpIn1st(vectActualIn);

                // values actual out
                Vector vectActualOut = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]) != null) {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)])));
                    } else {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]));
                    }
                }
                latenessWeekly.setEmpOut1st(vectActualOut);

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
                latenessWeekly.setEmpIn2nd(vectShiftActualIn);

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
                latenessWeekly.setEmpOut2nd(vectShiftActualOut);


                result.add(latenessWeekly);
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
     * untuk pencarian data lateness yang mingguan (periode dinamis)
     * @param departmentId
     * @param selectedMonth
     * @param sectionId
     * @return
     */
    //update by satrya 2012-10-03
    //public static Vector listLatenessDataWeeklyDinamis(long departmentId, int iCalendarType,Date selectedMonth, int weekIndex,long sectionId, String empNumb, String fullName) 
    //update by devin 2014-01-29
    // public static Vector listLatenessDataWeeklyDinamis(long departmentId, int iCalendarType,Date selectedMonth, int weekIndex,long sectionId, String empNumb, String fullName) 
    public static Vector listLatenessDataWeeklyDinamis(long departmentId,long companyId,long divisionId, int iCalendarType,Date selectedMonth, int weekIndex,long sectionId, String empNumb, String fullName) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        //System.out.println("sectionId  "+sectionId);
        // get start and end date of selected week of selected month
        CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);   
        Date startDateWeek = objCalendarCalc.getStartDateOfTheWeek(selectedMonth, weekIndex);
        Date endDateWeek = objCalendarCalc.getEndDateOfTheWeek(selectedMonth, weekIndex);      
        int intStartDate = startDateWeek.getDate();
        int intEndDate = endDateWeek.getDate();        

        // get periodId of selected date
        long periodId    = PstPeriod.getPeriodIdBySelectedDate(startDateWeek);
        long periodIdEnd = PstPeriod.getPeriodIdBySelectedDate(endDateWeek);

        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                     //update by satrya 2012-11-10
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

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
                    " = " + periodId;
                    /*(departmentId!=0 ? 
                     (" AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " AND "):"");*/
                    if(departmentId !=0){
                        sql = sql + "  AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                         " = " + departmentId;
                  }
                    //update by devin 2014-01-29
                if(companyId !=0){
                        sql = sql + "  AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +
                         " = " + companyId;
                  }
                if(divisionId !=0){
                        sql = sql + "  AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +
                         " = " + divisionId;
                  }
                    if(sectionId!=0){
                        sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                              " = "+sectionId;
                    }
                       //update by satrya 2012-10-03
//                    
//                        if(empNumb !=null && empNumb.length() > 0){
//                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE " + "\"%" + empNumb.trim() + "%\"";
//                        }
//                  
//                  
//                        if(fullName !=null && fullName.length() > 0){
//                            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" = "+ fullName.trim();
//                        }
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
                            sql = sql +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }
                
            }
            //update by satrya 2012-07-16
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
                            sql = sql +" EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }


                    // fields actual shift in
                    String sql2 = "";
                    String sql3="";
                    String sql4="";
                    for (int i = intStartDate; i <= intEndDate; i++) 
                    {
                        if(sql2.length()==0)
                        {
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                        if(sql3.length()==0)
                        {
                           //if()
                            sql3 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+")";
                        }
                        else
                        {
                            sql3 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+")";
                        }
                         if(sql4.length()==0)
                        {
                           //if()
                            sql4 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";
                        }
                        else
                        {
                            sql4 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i-1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";
                        }
                    }
//                   
//                    sql2 = " AND ("+sql2+")";
//                    sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                     sql2 = " AND ((("+sql2+")";
                     sql3 = " OR ("+sql3+"))";
                     sql4 = " OR (("+sql4+")))";
                     //sql5 = " OR ("+sql5+")))";
                    sql += sql2 + sql3 + sql4 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //System.out.println("\tlistLatenessDataWeekly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                LatenessWeekly latenessWeekly = new LatenessWeekly();

                latenessWeekly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                latenessWeekly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");
                //update by satrya 2012-11-10
                latenessWeekly.setEmpId(rs.getLong(3));

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)])));
                }
                latenessWeekly.setEmpSchedules1st(vectSchedule);

                // values schedule II
                Vector vectScheduleII = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectScheduleII.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)])));
                }
                latenessWeekly.setEmpSchedules2nd(vectScheduleII);

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
                latenessWeekly.setEmpIn1st(vectActualIn);

                // values actual out
                Vector vectActualOut = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]) != null) {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)])));
                    } else {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]));
                    }
                }
                latenessWeekly.setEmpOut1st(vectActualOut);

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
                latenessWeekly.setEmpIn2nd(vectShiftActualIn);

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
                latenessWeekly.setEmpOut2nd(vectShiftActualOut);


                result.add(latenessWeekly);
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
     * @author Ketut Kartika T
     * @descriptionuntuk pencarian data lateness yang mingguan (periode dinamis)
     * @param departmentId
     * @param selectedMonth
     * @param sectionId
     * @return
     */
    
   /*
    public static Vector listLatenessDataDateRange(long departmentId, int iCalendarType,Date startDate, Date endDate, long sectionId) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        if(startDate==null){
            startDate = new Date();
        }
        
        if(endDate==null){
            endDate = new Date();
        }
        
        if(startDate.after(endDate)){
            Date tempDate = startDate;
            startDate = (Date)endDate.clone();
            endDate = (Date)startDate.clone();
        }
               
        long oneDayMill = 1000 * 60 * 60 * 24;
        long dateDif = endDate.getTime() - startDate.getTime();
        
        

        // get periodId of selected date range 
        long periodId    = PstPeriod.getPeriodIdBySelectedDate(startDate);
        long periodIdEnd = PstPeriod.getPeriodIdBySelectedDate(endDate);  // if periodeId !=periodeIdEn => Cross Periode
                                
        try 
        {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            // fields schedule
            Date theDate = (Date) startDate.clone();
            int iFirstDate = theDate.getDate();
            do{
                int iDate = theDate.getDate();
                if(iDate > iFirstDate){
                    // periode pertama yang masuk di date range
                    
                }else {
                    // periode ke dua yang masuk di date range
                }
                    
                
                theDate = new Date(theDate.getTime() + oneDayMill);// add one Day
            } while (endDate.after(theDate));
            
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
                    (departmentId!=0 ? 
                     (" AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " AND "):"");
            
                    if(sectionId!=0){
                        sql = sql + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                              " = "+sectionId+ " AND ";
                    }

                    // fields actual shift in
                    String sql2 = "";
                    for (int i = intStartDate; i <= intEndDate; i++) 
                    {
                        if(sql2.length()==0)
                        {
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                    }
                    sql2 = "("+sql2+")";
                    sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistLatenessDataWeekly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                LatenessWeekly latenessWeekly = new LatenessWeekly();

                latenessWeekly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                latenessWeekly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule
                Vector vectSchedule = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i - 1)])));
                }
                latenessWeekly.setEmpSchedules1st(vectSchedule);

                // values schedule II
                Vector vectScheduleII = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) 
                {
                    vectScheduleII.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (i - 1)])));
                }
                latenessWeekly.setEmpSchedules2nd(vectScheduleII);

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
                latenessWeekly.setEmpIn1st(vectActualIn);

                // values actual out
                Vector vectActualOut = new Vector();
                for (int i = intStartDate; i <= intEndDate; i++) {
                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]) != null) {
                        vectActualOut.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)])));
                    } else {
                        vectActualOut.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (i - 1)]));
                    }
                }
                latenessWeekly.setEmpOut1st(vectActualOut);

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
                latenessWeekly.setEmpIn2nd(vectShiftActualIn);

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
                latenessWeekly.setEmpOut2nd(vectShiftActualOut);


                result.add(latenessWeekly);
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
    
    */
    

    /**
     * gadnyana
     * untuk mencari data lateness yang bulanan
     *
     * @param departmentId
     * @param selectedMonth
     * @return
     */
    public static Vector listLatenessDataMonthly(long departmentId, Date selectedMonth) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

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
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " AND ";
     
                    // fields actual shift in
                    String sql2 = "";
                    int iterasi = PstEmpSchedule.OFFSET_INDEX_STATUS_DINAMIS;
                    //System.out.println("")
                    for (int i = 0; i <=maxDayOnSelectedMonth; i++) 
                    {
                        
                        if(sql2.length()==0)
                        {
                           //if()
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                    }
                    sql2 = "("+sql2+")";
                    sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistLatenessDataMonthly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                LatenessMonthly latenessMonthly = new LatenessMonthly();

                latenessMonthly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                latenessMonthly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule 1st
                Vector vectSchedule = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                latenessMonthly.setEmpSchedules1st(vectSchedule);

                // values schedule 1st
                Vector vectSchedule2st = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule2st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                }
                latenessMonthly.setEmpSchedules2nd(vectSchedule2st);

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
                latenessMonthly.setEmpIn1st(vectActualIn);

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
                latenessMonthly.setEmpOut1st(vectActualOut); 

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
                latenessMonthly.setEmpIn2nd(vectShiftActualIn);

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
                latenessMonthly.setEmpOut2nd(vectShiftActualOut);


                result.add(latenessMonthly);
            }
        }
        catch (Exception e) 
        {
            System.out.println("listLatenessDataMonthly exc : " + e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * ktanjana
     * untuk mencari data lateness periode
     *
     * @param departmentId
     * @param periodId
     * @param startDatePeriod
     * @return
     */
    //update by satrya 2012-10-03
    //public static Vector listLatenessDataMonthly(long departmentId, long periodId) 
    //update by devin 2014-01-29
    //  public static Vector listLatenessDataMonthly(long departmentId, long periodId, String empNumb, String fullName) 
    public static Vector listLatenessDataMonthly(long departmentId,long companyId,long divisionId,long sectionId, long periodId, String empNumb, String fullName) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        int maxDayOnSelectedMonth=31; // default maximum data 

        try 
        {
            Period objPeriod = PstPeriod.fetchExc(periodId);
            
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    //update by satrya 2012-11-10
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            

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
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId;
                  if(departmentId !=0){
                        sql = sql + "  AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                         " = " + departmentId;
                  }
                  //update by devin 2014-01-29
                   if(companyId !=0){
                        sql = sql + "  AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +
                         " = " + companyId;
                  }
                    if(divisionId !=0){
                        sql = sql + "  AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +
                         " = " + divisionId;
                  }
                     if(sectionId !=0){
                        sql = sql + "  AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                         " = " + sectionId;
                  }
            //update by satrya 2012-10-03
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
                    // fields actual shift in
                    String sql2 = "";
                    String sql3="";
                    String sql4="";
                    int iterasi = PstEmpSchedule.OFFSET_INDEX_STATUS_DINAMIS;
                    //System.out.println("")
                    for (int i = 0; i <maxDayOnSelectedMonth; i++) 
                        //update by satrya 2012-11-10
                        //dikarenakan kelebihan dan muncul reason1
                        //for (int i = 0; i <maxDayOnSelectedMonth; i++) 
                    {
                        
                        if(sql2.length()==0)
                        {
                           //if()
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                         if(sql3.length()==0)
                        {
                           //if()
                            sql3 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+")";
                        }
                        else
                        {
                            sql3 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME+")";
                        }
                         if(sql4.length()==0)
                        {
                           //if()
                            sql4 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";
                        }
                        else
                        {
                            sql4 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY+")";
                        }
                    }
//                    sql2 = " AND ("+sql2+")";
//                    sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                       sql2 = " AND ((("+sql2+")";
                     sql3 = " OR ("+sql3+"))";
                     sql4 = " OR (("+sql4+")))";
                     //sql5 = " OR ("+sql5+")))";
                    sql += sql2 + sql3 + sql4 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

           // System.out.println("\tlistLatenessDataMonthly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                LatenessMonthly latenessMonthly = new LatenessMonthly();

                latenessMonthly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                latenessMonthly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");
                //update by satrya 2012-11-10
                latenessMonthly.setEmpId(rs.getLong(3));

                // values schedule 1st
                Vector vectSchedule = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                latenessMonthly.setEmpSchedules1st(vectSchedule);

                // values schedule 1st
                Vector vectSchedule2st = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule2st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                }
                latenessMonthly.setEmpSchedules2nd(vectSchedule2st);

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
                latenessMonthly.setEmpIn1st(vectActualIn);

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
                latenessMonthly.setEmpOut1st(vectActualOut); 

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
                latenessMonthly.setEmpIn2nd(vectShiftActualIn);

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
                latenessMonthly.setEmpOut2nd(vectShiftActualOut);


                result.add(latenessMonthly);
            }
        }
        catch (Exception e) 
        {
            System.out.println("listLatenessDataMonthly exc : " + e.toString());
            return result;
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    
    /**
     * gadnyana
     * untuk mencari data lateness yang bulanan
     *
     * @param departmentId
     * @param selectedMonth
     * @param sectionId
     * @return
     */
    public static Vector listLatenessDataMonthly(long departmentId, Date selectedMonth,long sectionId) 
    {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

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
                    " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = " + departmentId +
                    " AND ";
            
                    if(sectionId!=0){
                        sql = sql + "EMP. "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                              " = "+sectionId+ " AND ";
                    }
     
                    // fields actual shift in
                    String sql2 = "";
                    int iterasi = PstEmpSchedule.OFFSET_INDEX_STATUS_DINAMIS;
                    //System.out.println("")
                    for (int i = 0; i <=maxDayOnSelectedMonth; i++) 
                    {
                        
                        if(sql2.length()==0)
                        {
                           //if()
                            sql2 = "(SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                        else
                        {
                            sql2 += " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+
                                    " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]+"="+PstEmpSchedule.STATUS_PRESENCE_LATE+")";
                        }
                    }
                    sql2 = "("+sql2+")";
                    sql += sql2 + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistLatenessDataMonthly : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                LatenessMonthly latenessMonthly = new LatenessMonthly();

                latenessMonthly.setEmpNum(rs.getString(1) != null ? rs.getString(1) : "");
                latenessMonthly.setEmpName(rs.getString(2) != null ? rs.getString(2) : "");

                // values schedule 1st
                Vector vectSchedule = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));
                }
                latenessMonthly.setEmpSchedules1st(vectSchedule);

                // values schedule 1st
                Vector vectSchedule2st = new Vector();
                for (int i = 0; i < maxDayOnSelectedMonth; i++) 
                {
                    vectSchedule2st.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                }
                latenessMonthly.setEmpSchedules2nd(vectSchedule2st);

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
                latenessMonthly.setEmpIn1st(vectActualIn);

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
                latenessMonthly.setEmpOut1st(vectActualOut); 

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
                latenessMonthly.setEmpIn2nd(vectShiftActualIn);

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
                latenessMonthly.setEmpOut2nd(vectShiftActualOut);


                result.add(latenessMonthly);
            }
        }
        catch (Exception e) 
        {
            System.out.println("listLatenessDataMonthly exc : " + e.toString());
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
            System.out.println(new SessEmployeeLateness().getClass().getName()+".getDateRoster() exc : " + e.toString());
        }
        return scheduleSymbol;
    }
    
    public Vector cekLateEmployee(Date timeIn, Date timeInSchedule,Date timeOut,Date timeOutSchedule) {
        
        Vector vlLate = new Vector(1, 1);
        
        try {
            // diproses jika "timeIn on schedule!=null" dan "time In on presence!=null"
            if (timeIn != null && timeInSchedule != null) {

                // waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
                // berisi data HOUR dan MINUTES 
                Date dtSchedule = new Date();
                if (timeInSchedule.getHours() == 0 && timeInSchedule.getMinutes() == 0) {
                    dtSchedule.setDate(timeIn.getDate() + 1);
                    dtSchedule.setMonth(timeIn.getMonth());
                    dtSchedule.setYear(timeIn.getYear());
                    dtSchedule.setHours(timeInSchedule.getHours());
                    dtSchedule.setMinutes(timeInSchedule.getMinutes() - SessEmployeeLateness.TIME_LATES);
                    dtSchedule.setSeconds(0);
                } else {
                    dtSchedule.setDate(timeIn.getDate());
                    dtSchedule.setMonth(timeIn.getMonth());
                    dtSchedule.setYear(timeIn.getYear());
                    dtSchedule.setHours(timeInSchedule.getHours());
                    dtSchedule.setMinutes(timeInSchedule.getMinutes() - SessEmployeeLateness.TIME_LATES);
                    dtSchedule.setSeconds(0);
                }

                // waktu time In on presence
                Date dtPresence = new Date();
                dtPresence.setTime(timeIn.getTime());
                dtPresence.setSeconds(0);

                // ambil selisih antara timeIn schedule dengan timeIn presence (dalam detik)
                long lTimeSchld = dtSchedule.getTime();
                long lTimeActual = dtPresence.getTime();
                long iDurationSec = lTimeSchld / 1000 - lTimeActual / 1000;


                // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                int intMult = (iDurationSec < -1) ? -1 : 1;

                // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                iDurationSec = iDurationSec * intMult;

                // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                long iDurationMin = 0;
                if (iDurationSec >= 60) {
                    iDurationMin = (iDurationSec - (iDurationSec % 60)) / 60;
                    iDurationSec = iDurationSec % 60;
                }

                // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                long iDurationHour = 0;
                if (iDurationMin >= 60) {
                    iDurationHour = (iDurationMin - (iDurationMin % 60)) / 60;
                    iDurationMin = iDurationMin % 60;
                    iDurationSec = iDurationSec % 60;
                }

                // memasukkan data hasil proses ke vector durasi jam
               if ((iDurationHour * intMult) < 0) {
                    vlLate.add("" + (iDurationHour * intMult));
                } else {
                    vlLate.add("0");
                }
                // memasukkan data hasil proses ke vector durasi menit
                if ((iDurationMin * intMult) < 0) {
                    vlLate.add("" + (iDurationMin * intMult));
                } else {
                    vlLate.add("0");
                }
            } // tidak diproses karena salah satu "timeIn on schedule" dan/atau "time In on presence"	adalah null	 
            else {
                vlLate.add("0");
                vlLate.add("0");
            }
            //update by satrya 2012-11-07
                if(timeOut != null && timeOutSchedule != null)
		{
			
			// waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
			// berisi data HOUR dan MINUTES 
			Date dtScheduleOut = new Date();			
			dtScheduleOut.setDate(timeOut.getDate());
			dtScheduleOut.setMonth(timeOut.getMonth());
			dtScheduleOut.setYear(timeOut.getYear());
			dtScheduleOut.setHours(timeOutSchedule.getHours());
			dtScheduleOut.setMinutes(timeOutSchedule.getMinutes()- SessEmployeeLateness.TIME_LATES);
			dtScheduleOut.setSeconds(0);
			
			// waktu time In on presence
			Date dtPresenceOut = new Date();
			dtPresenceOut.setTime(timeOut.getTime());
			dtPresenceOut.setSeconds(0);			
			
			// ambil selisih antara timeOut schedule dengan timeOut presence (dalam detik)
			long lTimeSchldOut = dtScheduleOut.getTime();
			long lTimeActualOut = dtPresenceOut.getTime();
			long iDurationSecOut =  lTimeActualOut/1000 - lTimeSchldOut/1000;
			

			// faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
			int intMultTo = (iDurationSecOut < -1) ? -1 : 1;
			
			// nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
			iDurationSecOut = iDurationSecOut * intMultTo;			
			
			// hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
			long iDurationMinOut = 0;			
			if(iDurationSecOut >= 60)
			{
				iDurationMinOut = (iDurationSecOut - (iDurationSecOut % 60)) / 60;
				iDurationSecOut = iDurationSecOut % 60;
			}
			
			// hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
			long iDurationHourOut = 0;			
			if(iDurationMinOut >= 60)
			{
				iDurationHourOut = (iDurationMinOut - (iDurationMinOut % 60)) / 60;
				iDurationMinOut = iDurationMinOut % 60;
				iDurationSecOut = iDurationSecOut % 60;				
			}

			// memasukkan data hasil proses ke vector durasi jam
			if( (iDurationHourOut * intMultTo) < 0 )
			{
				//strDurationHourOut = (iDurationHourOut * intMultTo) + "h, ";
                                vlLate.add("" + (iDurationHourOut * intMultTo)); 
              
			}
                        else {
                            vlLate.add("0");
                        }

			// memasukkan data hasil proses ke vector durasi menit
			if( (iDurationMinOut * intMultTo) < 0 )
			{
				//strDurationMinOut = (iDurationMinOut * intMultTo) +"m ";
                            vlLate.add("" + (iDurationMinOut * intMultTo));
			}else{
                            vlLate.add("0");
                        }
                         
			//vlLate.add(strDurationHourOut + strDurationMinOut); 
		}else{
                    vlLate.add("0");
                    vlLate.add("0");
                }
                
        } catch (Exception e) {
            System.out.println("Exc on cekLateEmployee : " + e.toString());
        }
        return vlLate;
    }

    public Vector<LatenessMonthlyRekap> getListMonth(Vector listLate, Date dtSch, Date stDate, Date endDate,Vector listPresencePersonalInOut) {
    
        int max = (int) ((endDate.getTime() - stDate.getTime()) / (1000 * 60 * 60 * 24));

        Date tmpDate = new Date(stDate.getTime());
        Date tmpEndDate = new Date(endDate.getTime());
        Date dtTemp = new Date(stDate.getTime());

        Vector list = new Vector(1, 1);

        LatenessMonthlyRekap latenessMonthlyRekap = null;
        
        int index = -1;
        int num = 0;
        if (listLate != null && listLate.size() > 0) {

            try {
                for (int i = 0; i < listLate.size(); i++) {

                    LatenessMonthly latenessMonthly = (LatenessMonthly) listLate.get(i);

                    String empNum = latenessMonthly.getEmpNum();
                    String empName = latenessMonthly.getEmpName();
                    Vector empSchedules1st = latenessMonthly.getEmpSchedules1st();
                    Vector empSchedules2nd = latenessMonthly.getEmpSchedules2nd();
                    Vector empActualIn1st = latenessMonthly.getEmpIn1st();
                    Vector empActualIn2nd = latenessMonthly.getEmpIn2nd();
                    Vector empActualOut1st = latenessMonthly.getEmpOut1st();
                    Vector empActualOut2nd = latenessMonthly.getEmpOut2nd();
                    
                    //latenessYearly.setEmpNum(empNum);
                    //latenessYearly.setEmpName(empName);


                    Vector vt1St = new Vector(1, 1);
                    Vector vt2St = new Vector(1, 1);
                    int totHour1st = 0;
                    int totMenit1st = 0;
                    int totHour2st = 0;
                    int totMenit2st = 0;
                    //int kk = 0;
                    Date dtSchDateTime = new Date();
                    Date dtPresenceDateTime = new Date();

                    //tmpDate = new Date(stDate.getTime());
                    while (tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate)) {
                        int kk = tmpDate.getDate() - 1;
                        //update by satrya 2012-11-10
                        long temDurHourBo = 0;
                        long tempDurMinBi = 0;
                        long temDurSecBi = 0;
                        long temDurHourBi = 0;
                        String strDurationHourBo = "";
                        String strDurationMinBo = "";
                        String strDurationHourBi = "";
                        String strDurationMinBi = "";
                        long tempDurMinBo = 0;
                        long temDurSecBo = 0;
                        long durHourBi = 0;
                        long durMinBi = 0;
                        long durHourBo = 0;
                        long durMinBo = 0;
                        long durHourOUT = 0;
                        long durMinOUT = 0;
                        long durHourIN = 0;
                        long durMinIN = 0;
                        long lSchedulePersonal = 0;
                        long lActualPersonal = 0;
                        Date schPo = null;
                        String sSchPo = "";
                        Date schPi = null;
                        String sSchPi = "";
                        String stimeIn1st = "";
                        String stimeOut1st = "";

                        long temDurHourIN = 0;
                        long tempDurMinIN = 0;
                        long temDurSecIN = 0;

                        long temDurHourOUT = 0;
                        long tempDurMinOUT = 0;
                        long temDurSecOUT = 0;
                        String strDurationHourIN = "";
                        String strDurationMinIN = "";
                        String strDurationHourOUT = "";
                        String strDurationMinOUT = "";
                        Vector hasil = new Vector(1, 1);
                        Vector InOut = new Vector(1, 1);

                        for (int idxPer = 0; idxPer < listPresencePersonalInOut.size(); idxPer++) {
                            Presence presenceBreak = (Presence) listPresencePersonalInOut.get(idxPer);
                            int schPresence = 0;
                            if (presenceBreak.getScheduleDatetime() != null) {
                                dtSchDateTime = (Date) presenceBreak.getScheduleDatetime().clone();
                                dtSchDateTime.setDate(dtSchDateTime.getDate());
                                dtSchDateTime.setMonth(dtSchDateTime.getMonth());
                                dtSchDateTime.setYear(dtSchDateTime.getYear());
                                dtSchDateTime.setHours(dtSchDateTime.getHours());
                                dtSchDateTime.setMinutes(dtSchDateTime.getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtSchDateTime.setSeconds(0);
                                schPresence = presenceBreak.getScheduleDatetime().getDate();
                            }
                            if (presenceBreak.getPresenceDatetime() != null) {
                                //update by satrya 2012-10-17
                                dtPresenceDateTime = (Date) presenceBreak.getPresenceDatetime().clone();
                                dtPresenceDateTime.setHours(dtPresenceDateTime.getHours());
                                dtPresenceDateTime.setMinutes(dtPresenceDateTime.getMinutes());
                                dtPresenceDateTime.setSeconds(0);
                            }
                            if (presenceBreak.getScheduleDatetime() != null
                                    && presenceBreak.getEmployeeId() == latenessMonthly.getEmpId()
                                    && (schPresence == tmpDate.getDate())) {

                                if (presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {

                                    if (dtPresenceDateTime != null && dtSchDateTime != null) {
                                        // ambil selisih antara Break Out presence dengan Schedule Break Out (dalam detik)
                                        lSchedulePersonal = dtSchDateTime.getTime();
                                        lActualPersonal = dtPresenceDateTime.getTime();
                                        long iDurationSecBo = lActualPersonal / 1000 - lSchedulePersonal / 1000;
                                        schPo = dtSchDateTime;
                                        // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                                        int intMultBo = (iDurationSecBo < -1) ? -1 : 1;

                                        // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                                        iDurationSecBo = iDurationSecBo * intMultBo;

                                        // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                                        long iDurationMinBo = 0;
                                        if (iDurationSecBo >= 60) {
                                            iDurationMinBo = (iDurationSecBo - (iDurationSecBo % 60)) / 60;

                                            iDurationSecBo = iDurationSecBo % 60;

                                        }

                                        // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                                        long iDurationHourBo = 0;
                                        if (iDurationMinBo >= 60) {
                                            iDurationHourBo = (iDurationMinBo - (iDurationMinBo % 60)) / 60;

                                            iDurationMinBo = iDurationMinBo % 60;
                                            iDurationSecBo = iDurationSecBo % 60;
                                        }
                                        temDurHourBo = iDurationHourBo;
                                        tempDurMinBo = iDurationMinBo;
                                        temDurSecBo = iDurationSecBo;

                                        // memasukkan data hasil proses ke vector durasi jam
                                        if ((temDurHourBo * intMultBo) < 0) {
                                            strDurationHourBo = (temDurHourBo * intMultBo) + "h, ";
                                            durHourBo = (temDurHourBo * intMultBo);
                                            totHour1st = totHour1st + Integer.parseInt("" + durHourBo); // total 1st hour
                                        }

                                        // memasukkan data hasil proses ke vector durasi menit
                                        if ((tempDurMinBo * intMultBo) < 0) {
                                            strDurationMinBo = (tempDurMinBo * intMultBo) + "m ";
                                            durMinBo = (tempDurMinBo * intMultBo);
                                            totMenit1st = totMenit1st + Integer.parseInt("" + durMinBo); // total 1st menit
                                        }

                                        hasil.add(strDurationHourBo + strDurationMinBo);
                                        strDurationHourBo = "";
                                        strDurationMinBo = "";


                                    } else {
                                        hasil.add("" + "");
                                    }
                                    listPresencePersonalInOut.remove(idxPer);
                                    idxPer = idxPer - 1;
                                }//end personal out
                                else if (presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {
                                    if (dtPresenceDateTime != null && dtSchDateTime != null) {
                                        // ambil selisih antara Break Out presence dengan Schedule Break Out (dalam detik)
                                        lSchedulePersonal = dtSchDateTime.getTime();
                                        lActualPersonal = dtPresenceDateTime.getTime();
                                        long iDurationSecBi = lSchedulePersonal / 1000 - lActualPersonal / 1000;
                                        schPi = dtSchDateTime;
                                        // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                                        int intMultBi = (iDurationSecBi < -1) ? -1 : 1;

                                        // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                                        iDurationSecBi = iDurationSecBi * intMultBi;

                                        // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                                        long iDurationMinBi = 0;
                                        if (iDurationSecBi >= 60) {
                                            iDurationMinBi = (iDurationSecBi - (iDurationSecBi % 60)) / 60;

                                            iDurationSecBi = iDurationSecBi % 60;

                                        }

                                        // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                                        long iDurationHourBi = 0;
                                        if (iDurationMinBi >= 60) {
                                            iDurationHourBi = (iDurationMinBi - (iDurationMinBi % 60)) / 60;

                                            iDurationMinBi = iDurationMinBi % 60;
                                            iDurationSecBi = iDurationSecBi % 60;
                                        }
                                        temDurHourBi = iDurationHourBi;
                                        tempDurMinBi = iDurationMinBi;
                                        temDurSecBi = iDurationSecBi;

                                        //temDurHourBi= temDurHourBi+iDurationHourBi;
                                        //tempDurMinBi = tempDurMinBi+iDurationMinBi;
                                        //temDurSecBi= temDurSecBi+iDurationSecBi;
                                        // memasukkan data hasil proses ke vector durasi jam
                                        if ((temDurHourBi * intMultBi) < 0) {
                                            strDurationHourBi = (temDurHourBi * intMultBi) + "h, ";
                                            durHourBi = (temDurHourBi * intMultBi);
                                            totHour1st = totHour1st + Integer.parseInt("" + durHourBi); // total 1st hour
                                        }

                                        // memasukkan data hasil proses ke vector durasi menit
                                        if ((tempDurMinBi * intMultBi) < 0) {
                                            strDurationMinBi = (tempDurMinBi * intMultBi) + "m ";
                                            durMinBi = (tempDurMinBi * intMultBi);
                                            totMenit1st = totMenit1st + Integer.parseInt("" + durMinBi); // total 1st menit
                                        }

                                        hasil.add(strDurationHourBi + strDurationMinBi);
                                        strDurationHourBi = "";
                                        strDurationMinBi = "";


                                    } else {
                                        hasil.add("" + "");
                                    }
                                    listPresencePersonalInOut.remove(idxPer);
                                    idxPer = idxPer - 1;
                                }//end personal In
                            }//end cek yg sama
                        }//end listPresencePersonalInOut  
                        //for (int x = stDate.getDate(); x <= endDate.getDate(); x++) {
                        String formTime = "";
                        String formTime2 = "";
                        String formTimeOut = "";
                        String formTimeOut2 = "";
                        //update by satrya 2012-11-10
                        String formTimeBO = "";
                        String formTimeBi = "";
                        Date timeIn = (Date) empActualIn1st.get(kk);
                        Date actualIn2nd = (Date) empActualIn2nd.get(kk);

                        Date timeOut = (Date) empActualOut1st.get(kk);
                        Date actualOut2nd = (Date) empActualOut2nd.get(kk);

                        // jika 2 shift
                        if ((timeIn != null && timeOut != null) && (actualIn2nd != null && actualOut2nd != null)) {
                            long oidSch1St = Long.parseLong((String) empSchedules1st.get(kk));
                            long oidSch2St = Long.parseLong((String) empSchedules2nd.get(kk));

                            // get schedule 1 st
                            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                            try {
                                scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception e) {
                            }
                            Vector vect = cekLateEmployee(timeIn, scheduleSymbol.getTimeIn(), timeOut, scheduleSymbol.getTimeOut()); // value 1 st
                            if ((Integer.parseInt("" + vect.get(0)) != 0) || (Integer.parseInt("" + vect.get(1)) != 0)) {
                                if (Integer.parseInt("" + vect.get(0)) != 0) {
                                    formTime = vect.get(0) + "h";
                                }

                                if (Integer.parseInt("" + vect.get(1)) != 0) {
                                    if (formTime.length() > 0) {
                                        if (formTime.length() > 3) {
                                            formTime = formTime + ", " + vect.get(1) + "m";
                                        } else {
                                            formTime = formTime + ",  " + vect.get(1) + "m";
                                        }
                                    } else {
                                        formTime = vect.get(1) + "m";
                                    }
                                }
                                totHour1st = totHour1st + Integer.parseInt("" + vect.get(0)); // total 1st hour
                                totMenit1st = totMenit1st + Integer.parseInt("" + vect.get(1)); // total 1st menit
                            }

                            // get schedule 2 st
                            try {
                                scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch2St);
                            } catch (Exception e) {
                            }
                            vect = cekLateEmployee(actualIn2nd, scheduleSymbol.getTimeIn(), actualOut2nd, scheduleSymbol.getTimeOut());  // value 2st
                            if ((Integer.parseInt("" + vect.get(0)) != 0) || (Integer.parseInt("" + vect.get(1)) != 0)) {
                                if (Integer.parseInt("" + vect.get(0)) != 0) {
                                    formTime2 = vect.get(0) + "h";
                                }
                                if (Integer.parseInt("" + vect.get(1)) != 0) {
                                    if (formTime2.length() > 0) {
                                        if (formTime.length() > 3) {
                                            formTime2 = formTime2 + ", " + vect.get(1) + "m";
                                        } else {
                                            formTime2 = formTime2 + ", " + vect.get(1) + "m";
                                        }
                                    } else {
                                        formTime2 = vect.get(1) + "m";
                                    }
                                }
                                totHour2st = totHour2st + Integer.parseInt("" + vect.get(0)); // total 2st hour
                                totMenit2st = totMenit2st + Integer.parseInt("" + vect.get(1)); // total 2st menit
                            }

                            vt1St.add(formTime);
                            vt2St.add(formTime2);
                        } // 1 shift
                        else if (timeIn != null || timeOut != null) {
                            long oidSch1St = Long.parseLong((String) empSchedules1st.get(kk));
                            // get schedule 1 st
                            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                            try {
                                scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception e) {
                            }
                            //update by satrya 2012-11-10
                            if (timeIn != null && scheduleSymbol.getTimeIn() != null) {

                                // waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
                                // berisi data HOUR dan MINUTES 
                                Date dtSchedule = new Date();
                                if (scheduleSymbol.getTimeIn().getHours() == 0 && scheduleSymbol.getTimeIn().getMinutes() == 0) {
                                    dtSchedule.setDate(timeIn.getDate() + 1);
                                    dtSchedule.setMonth(timeIn.getMonth());
                                    dtSchedule.setYear(timeIn.getYear());
                                    dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(0);
                                } else {
                                    dtSchedule.setDate(timeIn.getDate());
                                    dtSchedule.setMonth(timeIn.getMonth());
                                    dtSchedule.setYear(timeIn.getYear());
                                    dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                                    dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                    dtSchedule.setSeconds(0);
                                }

                                // waktu time In on presence
                                Date dtPresence = new Date();
                                dtPresence.setTime(timeIn.getTime());
                                dtPresence.setSeconds(0);

                                // ambil selisih antara timeIn schedule dengan timeIn presence (dalam detik)
                                long lTimeSchld = dtSchedule.getTime();
                                long lTimeActual = dtPresence.getTime();
                                long iDurationSec = lTimeSchld / 1000 - lTimeActual / 1000;


                                // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                                int intMult = (iDurationSec < -1) ? -1 : 1;

                                // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                                iDurationSec = iDurationSec * intMult;

                                // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                                long iDurationMin = 0;
                                if (iDurationSec >= 60) {
                                    iDurationMin = (iDurationSec - (iDurationSec % 60)) / 60;
                                    iDurationSec = iDurationSec % 60;
                                }

                                // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                                long iDurationHour = 0;
                                if (iDurationMin >= 60) {
                                    iDurationHour = (iDurationMin - (iDurationMin % 60)) / 60;
                                    iDurationMin = iDurationMin % 60;
                                    iDurationSec = iDurationSec % 60;
                                }
                                temDurHourIN = iDurationHour;
                                tempDurMinIN = iDurationMin;
                                temDurSecIN = iDurationSec;
                                if ((temDurHourIN * intMult) < 0) {
                                    strDurationHourIN = (temDurHourIN * intMult) + "h, ";
                                    durHourIN = (temDurHourIN * intMult);
                                    totHour1st = totHour1st + Integer.parseInt("" + durHourIN); // total 1st hour
                                }

                                // memasukkan data hasil proses ke vector durasi menit
                                if ((tempDurMinIN * intMult) < 0) {
                                    strDurationMinIN = (tempDurMinIN * intMult) + "m ";
                                    durMinIN = (tempDurMinIN * intMult);
                                    totMenit1st = totMenit1st + Integer.parseInt("" + durMinIN); // total 1st menit
                                }
                                InOut.add(strDurationHourIN + strDurationMinIN);
                                strDurationHourIN = "";
                                strDurationMinIN = "";


                            } // tidak diproses karena salah satu "timeIn on schedule" dan/atau "time In on presence"	adalah null	 
                            else {
                                InOut.add("" + "");
                            }

                            if (timeOut != null && scheduleSymbol.getTimeOut() != null) {

                                // waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
                                // berisi data HOUR dan MINUTES 
                                Date dtScheduleOut = new Date();
                                dtScheduleOut.setDate(timeOut.getDate());
                                dtScheduleOut.setMonth(timeOut.getMonth());
                                dtScheduleOut.setYear(timeOut.getYear());
                                dtScheduleOut.setHours(scheduleSymbol.getTimeOut().getHours());
                                dtScheduleOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes() - SessEmployeeLateness.TIME_LATES);
                                dtScheduleOut.setSeconds(0);

                                // waktu time In on presence
                                Date dtPresenceOut = new Date();
                                dtPresenceOut.setTime(timeOut.getTime());
                                dtPresenceOut.setSeconds(0);

                                // ambil selisih antara timeOut schedule dengan timeOut presence (dalam detik)
                                long lTimeSchldOut = dtScheduleOut.getTime();
                                long lTimeActualOut = dtPresenceOut.getTime();
                                long iDurationSecOut = lTimeActualOut / 1000 - lTimeSchldOut / 1000;


                                // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                                int intMultTo = (iDurationSecOut < -1) ? -1 : 1;

                                // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                                iDurationSecOut = iDurationSecOut * intMultTo;

                                // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                                long iDurationMinOut = 0;
                                if (iDurationSecOut >= 60) {
                                    iDurationMinOut = (iDurationSecOut - (iDurationSecOut % 60)) / 60;
                                    iDurationSecOut = iDurationSecOut % 60;
                                }

                                // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                                long iDurationHourOut = 0;
                                if (iDurationMinOut >= 60) {
                                    iDurationHourOut = (iDurationMinOut - (iDurationMinOut % 60)) / 60;
                                    iDurationMinOut = iDurationMinOut % 60;
                                    iDurationSecOut = iDurationSecOut % 60;
                                }
                                temDurHourOUT = iDurationHourOut;
                                tempDurMinOUT = iDurationMinOut;
                                temDurSecOUT = iDurationSecOut;
                                if ((temDurHourOUT * intMultTo) < 0) {
                                    strDurationHourOUT = (temDurHourOUT * intMultTo) + "h, ";
                                    durHourOUT = (temDurHourOUT * intMultTo);
                                    totHour1st = totHour1st + Integer.parseInt("" + durHourOUT); // total 1st hour
                                }

                                // memasukkan data hasil proses ke vector durasi menit
                                if ((temDurHourOUT * intMultTo) < 0) {
                                    strDurationMinOUT = (tempDurMinOUT * intMultTo) + "m ";
                                    durMinOUT = (tempDurMinOUT * intMultTo);
                                    totMenit1st = totMenit1st + Integer.parseInt("" + durMinOUT); // total 1st menit
                                }
                                InOut.add(strDurationHourOUT + strDurationMinOUT);
                                strDurationHourOUT = "";
                                strDurationMinOUT = "";


                            } else {
                                InOut.add("" + "");
                            }
                            if (InOut != null && InOut.size() > 0) {
                                for (int idxSch = 0; idxSch < InOut.size(); idxSch++) {
                                    if (idxSch % 2 == 0) {
                                        if (InOut.get(idxSch) != null) {
                                            formTime = formTime + ((String) InOut.get(idxSch));
                                        }
                                        // hasil.remove(idx);
                                        // idx = idx -1;
                                    } else {
                                        if (InOut.get(idxSch) != null) {
                                            formTimeOut = formTimeOut + ((String) InOut.get(idxSch));
                                            // hasil.remove(idx);
                                        }
                                    }
                                }
                            }
                            //cek break In break out
                            if (hasil != null && hasil.size() > 0) {
                                for (int idx = 0; idx < hasil.size(); idx++) {
                                    if (idx % 2 == 0) {
                                        if (hasil.get(idx) != null) {
                                            formTimeBO = formTimeBO + ((String) hasil.get(idx));
                                        }
                                        // hasil.remove(idx);
                                        // idx = idx -1;
                                    } else {
                                        if (hasil.get(idx) != null) {
                                            formTimeBi = formTimeBi + ((String) hasil.get(idx));
                                            // hasil.remove(idx);
                                        }
                                    }
                                }
                            }

                            if (!(formTimeBO != null && formTimeBO.length() > 0)) {
                                formTimeBO = ".";
                            }
                            if (!(formTimeBi != null && formTimeBi.length() > 0)) {
                                formTimeBi = ".";
                            }
                            if (!(formTime != null && formTime.length() > 0)) {
                                formTime = ".";
                            }
                            if (!(formTimeOut != null && formTimeOut.length() > 0)) {
                                formTimeOut = ".";
                            }
                            vt1St.add(formTime + "/" + formTimeOut + "/" + formTimeBO + "/" + formTimeBi);
                            formTimeBi = "";
                            formTimeBO = "";
                            vt2St.add("");
                        } // jika schedule off, absence, DP, AL atau LL						
                        else {
                            vt1St.add("OFF");
                            vt2St.add("");
                        }

                        //kk++;
                        tmpDate = new Date(tmpDate.getTime() + 24 * 60 * 60 * 1000);

                    }

                    boolean bool = false;
                    num++;

                    for (int k = 0; k < vt1St.size(); k++) {

                        if (!vt2St.get(k).toString().equals("")) {
                            bool = true;
                        }
                    }

                    String strTotal = "";
                    if (totMenit1st != 0) {
                        int jm = totMenit1st / 60;
                        if (jm != 0) {
                            totHour1st = totHour1st + jm;
                            if ((totMenit1st % 60) != 0) {
                                totMenit1st = totMenit1st % 60;
                            }
                        }
                    }

                    if (totHour1st != 0) {
                        strTotal = totHour1st + "h";
                    }
                    if (totMenit1st != 0) {
                        if (strTotal.length() > 0) {
                            if (strTotal.length() > 3) {
                                strTotal = strTotal + ", " + totMenit1st + "m";
                            } else {
                                strTotal = strTotal + ",  " + totMenit1st + "m";
                            }
                        } else {
                            strTotal = totMenit1st + "m";
                        }
                    }

                    latenessMonthlyRekap = new LatenessMonthlyRekap();
                    latenessMonthlyRekap.setEmpNum(empNum);
                    latenessMonthlyRekap.setEmpName(empName);
                    latenessMonthlyRekap.setSumHour(totHour1st);
                    latenessMonthlyRekap.setSumMinute(totMenit1st);
                    latenessMonthlyRekap.setTotalMonth(strTotal);
                    latenessMonthlyRekap.setMonth(endDate.getMonth());
                    
                    list.add(latenessMonthlyRekap);

                    if (bool) {
                     
                        strTotal = "";
                        if (totMenit2st != 0) {
                            int jm = totMenit2st / 60;
                            if (jm != 0) {
                                totHour2st = totHour2st + jm;
                                if ((totMenit2st % 60) != 0) {
                                    totMenit2st = totMenit2st % 60;
                                }
                            }
                        }

                        if (totHour2st != 0) {
                            strTotal = totHour2st + "h";
                        }
                        if (totMenit2st != 0) {
                            if (strTotal.length() > 0) {
                                if (strTotal.length() > 3) {
                                    strTotal = strTotal + ", " + totMenit2st + "m";
                                } else {
                                    strTotal = strTotal + ",  " + totMenit2st + "m";
                                }
                            } else {
                                strTotal = totMenit1st + "m";
                            }
                        }
                        
                        latenessMonthlyRekap.setEmpNum(empNum);
                        latenessMonthlyRekap.setEmpName(empName);
                        latenessMonthlyRekap.setSumHour(totHour1st);
                        latenessMonthlyRekap.setSumMinute(totMenit1st);
                        latenessMonthlyRekap.setTotalMonth(strTotal);
                        latenessMonthlyRekap.setMonth(endDate.getMonth());

                        list.add(latenessMonthlyRekap);
                    }
                    tmpDate = new Date(stDate.getTime());
                    //list.add(latenessYearly);
                }//end loop listLate
            } catch (Exception ex) {
                System.out.println("Exception ListLate : " + ex.toString());
            }
            
        }//end jika lisLate kosong
        return list;
    }
}
