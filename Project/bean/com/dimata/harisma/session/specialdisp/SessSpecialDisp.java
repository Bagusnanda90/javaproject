/*
 * SessSpecialDisp.java
 *
 * Created on June 17, 2004, 6:28 PM
 */

package com.dimata.harisma.session.specialdisp;

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

/**
 *
 * @author  gedhy
 */
public class SessSpecialDisp 
{  
    
    /** 
     * get list absenteeism data daily
     * @param departmentId
     * @param selectedDate
     * @return
     * @created by Edhy
     */        
    public static Vector listSpecialDispDataDaily(long departmentId, Date selectedDate, long sectionId)
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
                         ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName-1] +                
                         " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                         " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                         " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                         " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                          
                         " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                         " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                         " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +                                                    
                         " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "+
                         " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                         " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]+                         
                         " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                         " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1] +
                         " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +                          
                         " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +                             
                         " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +                             
                         " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +                             
                         " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + 
                         " = " + periodId +                          
                         ( departmentId!=0 ? 
                         " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + 
                         " = " + departmentId : "") +                                                  
                         ( sectionId!=0 ? (" AND SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + 
                         " = " + sectionId) : "") +                          
                         " AND ((SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1] + 
                         " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
                         " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1] + 
                         " = " + PstEmpSchedule.REASON_ABSENCE_DISPENSATION + ") " + 
                         " OR (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] + 
                         " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + 
                         " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] + 
                         " = " + PstEmpSchedule.REASON_ABSENCE_DISPENSATION + "))" + 
                         " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +                             
                         " NOT IN (" + PstScheduleCategory.CATEGORY_ABSENCE + 
                         "," + PstScheduleCategory.CATEGORY_OFF + 
                         "," + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT  + 
                         "," + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE + 
                         "," + PstScheduleCategory.CATEGORY_LONG_LEAVE + ")" +
                         " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];                         
                      
//                System.out.println("\tlistSpecialDispDataDaily : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());                
                java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate()+1);                
                while(rs.next()) 
                {               
                    SpecialDispDaily specialDispDaily = new SpecialDispDaily();
                    
                    specialDispDaily.setEmpNum(rs.getString(1));
                    specialDispDaily.setEmpName(rs.getString(2));
                    specialDispDaily.setSchldSymbol(rs.getString(3));
                    specialDispDaily.setSchldIn(rs.getTime(4));
                    specialDispDaily.setSchldOut(rs.getTime(5));
                    specialDispDaily.setRemark(rs.getString(6));
                    
                    result.add(specialDispDaily);                                         
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSpecialDispDataDaily exc : "+e.toString());
            return result;
        }
        finally 
        {
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
     * @created by Edhy
     */    
    public static Vector listSpecialDispDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex)
    {
        return listSpecialDispDataWeekly(departmentId, iCalendarType,  selectedMonth, weekIndex, 0);
    }
    public static Vector listSpecialDispDataWeekly(long departmentId, int iCalendarType, Date selectedMonth, int weekIndex, long sectionId)
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
                         
                         // schedule
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)];                
                         }

                         // actual status
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i-1)];                
                         }
            
                         // actual reason
                         for(int i=intStartDate; i<=intEndDate; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i-1)];                
                         }            
            
                  sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                         " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                         " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                         " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                          
                         " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                         " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                         " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +                                                    
                         " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "+
                         " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                         " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]+                                                  
                         " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + 
                         " = " + periodId +                           
                         ( departmentId!=0 ? 
                         " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + 
                         " = " + departmentId : "") +                                                  
                         ( sectionId!=0 ? (" AND SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + 
                         " = " + sectionId) : "") +                          
                         " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];                         
                      
//                System.out.println("\tlistSpecialDispDataWeekly : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                while(rs.next()) 
                {                     
                    SpecialDispWeekly specialDispWeekly = new SpecialDispWeekly();
                  
                    specialDispWeekly.setEmpNum(rs.getString(1));
                    specialDispWeekly.setEmpName(rs.getString(2));
                     
                    // values schedule
                    Vector vectSchedule = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (i-1)])));                        
                    }                                        
                    specialDispWeekly.setEmpSchedules(vectSchedule);                    
                    
                    // values status
                    Vector vectStatus = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)
                    {                        
                        vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + (i-1)])));                        
                    }                                        
                    specialDispWeekly.setAbsStatus(vectStatus);                                        
                    
                    // values reason
                    Vector vectReason = new Vector();                    
                    for(int i=intStartDate; i<=intEndDate; i++)  
                    {                        
                        vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i-1)])));                        
                    }                                        
                    specialDispWeekly.setAbsReason(vectReason);                                        

                    result.add(specialDispWeekly);                    
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSpecialDispDataWeekly exc : "+e.toString());
            return result;
        }
        finally 
        {
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
    public static Vector listSpecialDispDataMonthly(long departmentId, Date selectedMonth, long sectionId)
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
            
                         // fields schedule 
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];                
                         }

                         // fields status
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i];                
                         }

                         // fields reason
                         for(int i=0; i<maxDayOnSelectedMonth; i++)
                         {
                            sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i];                
                         }

                  sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                         " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                         " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                         " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                          
                         " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT" +
                         " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                         " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +                                                    
                         " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "+
                         " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                         " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]+                         
                         " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + 
                         " = " + periodId +       
                         ( departmentId!=0 ? 
                         " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + 
                         " = " + departmentId : "") +                                                  
                         ( sectionId!=0 ? (" AND SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + 
                         " = " + sectionId) : "") +                          
                         " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                      
//                System.out.println("\tlistSpecialDispDataMonthly : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                while(rs.next()) 
                { 
                    SpecialDispMonthly specialDispMonthly = new SpecialDispMonthly();                  
                    
                    specialDispMonthly.setEmpNum(rs.getString(1)!=null ? rs.getString(1) : "");
                    specialDispMonthly.setEmpName(rs.getString(2)!=null ? rs.getString(2) : "");  
                    
                    // values schedule
                    Vector vectSchedule = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        vectSchedule.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));                        
                    }                                        
                    specialDispMonthly.setEmpSchedules(vectSchedule);                          
                    
                    // values status
                    Vector vectStatus = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        vectStatus.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i])));
                    }                    
                    specialDispMonthly.setPresenceStatus(vectStatus);                                        

                    // values reason
                    Vector vectReason = new Vector();                    
                    for(int i=0; i<maxDayOnSelectedMonth; i++)
                    {                        
                        vectReason.add(String.valueOf(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i])));
                    }                    
                    specialDispMonthly.setAbsReason(vectReason);                                        

                    result.add(specialDispMonthly);                    
                }                
        }
        catch(Exception e) 
        {
            System.out.println("listSpecialDispDataMonthly exc : "+e.toString());
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
    public static boolean containSchldNotOff(Vector vectSchedule)
    {
        boolean result = false;
        
        String listSchedule = ""; 
        if(vectSchedule!=null && vectSchedule.size()>0)
        {
            int maxSchld = vectSchedule.size();
            for(int i=0; i<maxSchld; i++)
            {
                listSchedule += String.valueOf(vectSchedule.get(i)) + ",";
            }
            
            if(listSchedule!=null && listSchedule.length()>0)
            {
                listSchedule = listSchedule.substring(0,listSchedule.length()-1);
                
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
                        while(rs.next())
                        {
                            if( !(rs.getInt(1) == PstScheduleCategory.CATEGORY_ABSENCE 
                                  || rs.getInt(1) == PstScheduleCategory.CATEGORY_OFF 
                                  || rs.getInt(1) == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                  || rs.getInt(1) == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE 
                                  || rs.getInt(1) == PstScheduleCategory.CATEGORY_LONG_LEAVE)                                   
                            )
                            {
                                result = true;
                                break;
                            }                        
                        }
                }
                catch(Exception e)
                {
                    System.out.println("containSchldNotOff exc : "+e.toString());
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
    
    public Vector<SpecialDispMonthlyRekap> getListMonth(Vector listSpecialDispPresence, int dateOfMonth, Date endDate) {

        Vector list = new Vector(1, 1);
        
        SpecialDispMonthlyRekap specialDispMonthlyRekap= null;
        
        if (listSpecialDispPresence != null && listSpecialDispPresence.size() > 0) {
            
            int maxSpecialDispPresence = listSpecialDispPresence.size();
            int dataAmount = 0;
            for (int i = 0; i < maxSpecialDispPresence; i++) {
                SpecialDispMonthly absenteeismWeekly = (SpecialDispMonthly) listSpecialDispPresence.get(i);
                String empNum = absenteeismWeekly.getEmpNum();
                String empName = absenteeismWeekly.getEmpName();
                Vector empSchedules = absenteeismWeekly.getEmpSchedules();
                Vector absStatus = absenteeismWeekly.getPresenceStatus();
                Vector absReason = absenteeismWeekly.getAbsReason();

                // check apakah dalam vector schedule ada schedule tipe not OFf/ABSENCE ???			
                boolean containSchldNotOff = SessSpecialDisp.containSchldNotOff(empSchedules);
                int startPerioDisp = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
                startPerioDisp = startPerioDisp - 1;
                if (containSchldNotOff) {
                    int totalSpecialDisp = 0;
                    int absenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
                    Vector rowxMonth = new Vector(1, 1);
                    for (int isch = 0; isch < empSchedules.size(); isch++) {
                        if (startPerioDisp == dateOfMonth) {
                            startPerioDisp = 1;
                        } else {
                            startPerioDisp = startPerioDisp + 1;
                        }
                        //String schldSymbol = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules.get(isch))));
                        String schldSymbol = "";
                        int schldCategory = 0;
                        String currAbsence = "";
                        Vector vectSchldSymbol = PstScheduleSymbol.getScheduleData(Long.parseLong(String.valueOf(empSchedules.get(startPerioDisp - 1))));
                        if (vectSchldSymbol != null && vectSchldSymbol.size() > 0) {
                            Vector vectTemp = (Vector) vectSchldSymbol.get(0);
                            schldSymbol = String.valueOf(vectTemp.get(0));
                            schldCategory = Integer.parseInt(String.valueOf(vectTemp.get(1)));
                        }

                        if (schldSymbol != null && schldSymbol.length() > 0) {
                            int statusAbsence = Integer.parseInt(String.valueOf(absStatus.get(startPerioDisp - 1)));
                            int reasonAbsence = Integer.parseInt(String.valueOf(absReason.get(startPerioDisp - 1)));
                            if (!(schldCategory == PstScheduleCategory.CATEGORY_OFF
                                    || schldCategory == PstScheduleCategory.CATEGORY_ABSENCE
                                    || schldCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                    || schldCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                    || schldCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                    && (statusAbsence == PstEmpSchedule.STATUS_PRESENCE_ABSENCE && reasonAbsence == PstEmpSchedule.REASON_ABSENCE_DISPENSATION)) {
                                currAbsence = "SD";
                                absenceNull += 1;
                            } else {
                                currAbsence = "";
                            }
                            //rowxMonth.add(currAbsence);

                            if (currAbsence != null && currAbsence.length() > 0) {
                                totalSpecialDisp += 1;
                            }

                        } else {
                           // rowxMonth.add("");
                        }
                    }

                    if (absenceNull > 0) {
                        specialDispMonthlyRekap = new SpecialDispMonthlyRekap();
                        
                        specialDispMonthlyRekap.setEmpNum(empNum);
                        specialDispMonthlyRekap.setEmpName(empName);
                        specialDispMonthlyRekap.setMonth(endDate.getMonth());
                        specialDispMonthlyRekap.setTotalMonth(totalSpecialDisp);
                        
                        list.add(specialDispMonthlyRekap);
                    }

                }
            }
        }
        return list;
    }
    
}
