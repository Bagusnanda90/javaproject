package com.dimata.harisma.session.attendance;

// import core java package
import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;

// import dimata package
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;

// import qdep package
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;

// import project package
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.attendance.ALStockReporting;   
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.harisma.entity.attendance.PstAlStockExpired;
import com.dimata.harisma.entity.search.SrcLeaveManagement;

/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Jul 8, 2004
 * Time: 11:02:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class AnnualLeaveMontly 
{

    public Vector getAlMonthly(long oidPeriod, long oidDept) 
    {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try 
        {
            LeavePeriod leavePeriod = new LeavePeriod();
            leavePeriod = PstLeavePeriod.fetchExc(oidPeriod);
            Date dt = new Date();
            dt.setDate(1);
            dt.setMonth(0);
            dt.setYear(leavePeriod.getEndDate().getYear());

            String stDate = Formater.formatDate(dt, "yyyy-MM-dd");
            String endDate = Formater.formatDate(leavePeriod.getEndDate(), "yyyy-MM-dd");  
            String sQL = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + 
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + 
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + 
                         ", AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] + 
                         ", SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + ") AS EARNED" +
                         ", SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + ") AS TAKEN_YTD" +
                         ", SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ") AS CLR_YTD" +
                         " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL" +
                         " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" + 
                         " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                         " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                         " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                         " = " + oidDept +                         
                         " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + 
                         " BETWEEN \"" + stDate + "\" AND \"" + endDate + "\"" + 
                         " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + 
                         " NOT IN (" + PstAlStockManagement.AL_STS_EXPIRED + "," + PstAlStockManagement.AL_STS_NOT_AKTIF + ")" +
                         " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + 
                         " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                Vector vt = new Vector();
                Employee emp = new Employee();
                emp.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));

                vt.add("0");
                vt.add(emp);
                vt.add("" + rs.getInt(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED]));
                vt.add("" + rs.getInt("EARNED"));
            
                
                AlStockManagement alStockManagement = getDataAl(oidPeriod, emp.getOID());
                vt.add("" + alStockManagement.getQtyUsed());
                vt.add("" + rs.getInt("TAKEN_YTD"));
                vt.add("" + rs.getInt("CLR_YTD"));

                list.add(vt);
            }
            rs.close();
            DBResultSet.close(dbrs);
            
            
            
            sQL = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ", " +
                  " SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ") AS CLR_YTD" +
                  " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AL" +
                  " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                  " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                  " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                  " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDept +
                  " AND YEAR(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")=" + (leavePeriod.getEndDate().getYear() - 1) +
                  " AND (AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "!=" + PstAlStockManagement.AL_STS_EXPIRED +
                  " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "!=" + PstAlStockManagement.AL_STS_NOT_AKTIF +
                  " ) GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            dbrs = DBHandler.execQueryResult(sQL);
            rs = dbrs.getResultSet();            
            while (rs.next()) 
            {
                list = equalAlCurrentLast(list, rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), rs.getInt("CLR_YTD"));
            }
            rs.close();
        }
        catch (Exception e) 
        {
            System.out.println(" Err AnnualLeaveMontly >> : " + e.toString());
        }
        finally 
        {  
            DBResultSet.close(dbrs);
        }
        return list;
    }

    
    /**
     * untuk pencarian annual leave sesuai
     * dengan periode parameter
     * @param oidPeriod
     * @return
     * @created by Edhy
     */
    public static Vector getAnnualLeaveStock() 
    {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try 
        {
            String sQL = "SELECT " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ", " +
                    " SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ") AS RESIDUE" +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AL" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                    
                    " WHERE AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + 
                    " = " + PstAlStockManagement.AL_STS_AKTIF +                    
                    " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + 
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];                    
            
            //System.out.println("AL STOCK SQL : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);   
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                Vector vt = new Vector(1, 1);
                vt.add("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add("" + rs.getInt("RESIDUE"));

                list.add(vt);
            }
            rs.close();
        }
        catch (Exception e) 
        {
            System.out.println("ERR  getAnnualLeave : > " + e.toString());
        }
        finally 
        {
            DBResultSet.close(dbrs);
        }
        return list;
    }

    
    /** gadnyana
     * untuk pencarian annual leave sesuai
     * dengan periode parameter
     * @param oidPeriod
     * @return
     */
    public Vector getAnnualLeave(long oidPeriod) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try {
            LeavePeriod leavePeriod = new LeavePeriod();
            leavePeriod = PstLeavePeriod.fetchExc(oidPeriod);
            String sQL = "SELECT " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ", " +
                    " SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ") AS RESIDUE" +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AL" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +

                    /*
                    " WHERE YEAR(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")=" + (leavePeriod.getEndDate().getYear() - 1) +
                    " AND (AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "!=" + PstAlStockManagement.AL_STS_EXPIRED +
                    " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "!=" + PstAlStockManagement.AL_STS_NOT_AKTIF +
                    " ) GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    */

                    // edit by edhy       
                    " WHERE AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + 
                    " = " + PstAlStockManagement.AL_STS_AKTIF +                    
                    " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + 
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];                    
            
            //System.out.println("AL STOCK SQL : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);   
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                vt.add("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add("" + rs.getInt("RESIDUE"));

                list.add(vt);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("ERR  getAnnualLeave : > " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }

    /** gadnyana
     * untuk pencarian annual leave sesuai
     * dengan periode, oid employee parameter
     * @param oidPeriod
     * @return
     */
    public Vector getAnnualLeave(long oidPeriod, long empOid) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try {
            LeavePeriod leavePeriod = new LeavePeriod();
            leavePeriod = PstLeavePeriod.fetchExc(oidPeriod);
            String sQL = "SELECT " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ", " +
                    " SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ") AS RESIDUE" +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AL" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    
                    /*
                    " WHERE YEAR(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")=" + (leavePeriod.getEndDate().getYear() - 1) +
                    " AND (AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "!=" + PstAlStockManagement.AL_STS_EXPIRED +
                    " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "!=" + PstAlStockManagement.AL_STS_NOT_AKTIF +
                    " ) AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" +empOid+
                    " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + " !=0 "+
                    "GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    */
                    
                    // edit by edhy       
                    " WHERE AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + 
                    " = " + PstAlStockManagement.AL_STS_AKTIF +                    
                    " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" +empOid+
                    " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + " !=0 "+
                    " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];                    

            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                vt.add("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add("" + rs.getInt("RESIDUE"));

                list.add(vt);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("ERR  getAnnualLeave : > " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }


    /**
     * @param empOid
     * @return
     * @created by Edhy
     */    
    public int getCurrentALStock(long empOid) 
    {
        DBResultSet dbrs = null;        
        int result = 0;
        try 
        {
            String sQL = "SELECT SUM(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ")" + 
                         " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT +                          
                         " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + 
                         " = " + empOid + 
                         " GROUP BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];                        
            
            System.out.println("getCurrentALStock.sql : " + sQL);             
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                result = rs.getInt(1);
                break;
            }
            rs.close();
        }
        catch (Exception e) 
        {
            System.out.println("Exc on " + getClass().getName() + " : " + e.toString());
        }
        finally 
        {
            DBResultSet.close(dbrs);  
        }
        return result;
    }

    
    /** gadnyana
     *
     * @return vector
     */
    public static Vector prosessGetAl(long oidPeriod) {
        Vector vt = new Vector(1, 1);
        try {
            if (oidPeriod != 0) {
                Period period = PstPeriod.fetchExc(oidPeriod);
                LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(period.getStartDate());
                AnnualLeaveMontly annualLeaveMontly = new AnnualLeaveMontly();
                vt = annualLeaveMontly.getAnnualLeave(leavePeriod.getOID());
            }
        } catch (Exception e) {
            System.out.println("ERR > prosessGetAl : " + e.toString());
        }
        return vt;
    }

    public static Vector prosessGetAl(long oidPeriod, long oidEmp) {
        Vector vt = new Vector(1, 1);
        try {
            if (oidPeriod != 0) {
                Period period = PstPeriod.fetchExc(oidPeriod);
                LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(period.getStartDate());
                AnnualLeaveMontly annualLeaveMontly = new AnnualLeaveMontly();
                vt = annualLeaveMontly.getAnnualLeave(leavePeriod.getOID(),oidEmp);
            }
        } catch (Exception e) {
            System.out.println("ERR > prosessGetAl : " + e.toString());
        }
        return vt;
    }

    /**gadnyana
     * untuk mencari al yang current
     * @param oidLeavePeriod
     * @param oidEmp
     * @return
     */
    public AlStockManagement getDataAl(long oidLeavePeriod, long oidEmp) {
        AlStockManagement alStockManagement = new AlStockManagement();
        String whereClause = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] + "=" + oidLeavePeriod +
                //update by satrya 2012-10-16
                " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" + oidEmp;
        Vector vt = PstAlStockManagement.list(0, 0, whereClause, "");
        if (vt != null && vt.size() > 0) {
            alStockManagement = (AlStockManagement) vt.get(0);
        }
        return alStockManagement;
    }

    /**gadnyana
     * untuk mencari qty sisa last year dan
     * di gabung dengan current year
     * @param vect
     * @param oidEmp
     * @param qty
     * @return
     */
    public Vector equalAlCurrentLast(Vector vect, long oidEmp, int qty) {
        if (vect != null && vect.size() > 0) {
            for (int k = 0; k < vect.size(); k++) {
                Vector vt = (Vector) vect.get(k);
                Employee emp = (Employee) vt.get(1);
                if (oidEmp == emp.getOID()) {
                    vt.setElementAt("" + qty, 0);
                    vect.setElementAt(vt, k);
                    break;
                }
            }
        }
        return vect;
    }
    
    /**
     * @param text
     * @return
     */    
    private static Vector logicParser(String text)
    {
        Vector vector = LogicParser.textSentence(text);
        for(int i =0;i < vector.size();i++)
        {
            String code =(String)vector.get(i);
            if(((vector.get(vector.size()-1)).equals(LogicParser.SIGN))&&
              ((vector.get(vector.size()-1)).equals(LogicParser.ENGLISH)))
            { 
                vector.remove(vector.size()-1);
            }
        }
        return vector;
    }
    
    
    // ------------------ Start AL Reporting --------------------
    /**
     * @param srcLeaveManagement
     * @return
     * @created by Edhy
     */
    synchronized public static Vector listALStockReport(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_al_stock_temp";  //"hr_al_stock_temp";
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            // empty table AL stock temporary table
            int status = emptyTemp(); 
            
            // add data into AL stock temporary table (to clear lat year, entitle this year, earned ytd, taken mtd and taken ytd)
            status = addToClearLastYearToTemp(srcLeaveManagement);            
            status = addEntitleThisYearToTemp(srcLeaveManagement);            
            status = addEarnYearToDateToTemp(srcLeaveManagement);            
            status = addTakenMonthToDateToTemp(srcLeaveManagement);            
            status = addTakenYearToDateToTemp(srcLeaveManagement);            
            
            String sQL = "SELECT PAYROLL, NAME, SUM(TO_CLEAR_LAST_YEAR), SUM(ENT_CURR_YEAR), SUM(EARNED_YTD), SUM(TAKEN_MTD), SUM(TAKEN_YTD) "+
                         " FROM " + tblDpReportName +
                         " GROUP BY PAYROLL";
            
            System.out.println("listDpStockReport SQL : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                ALStockReporting objALStockReporting = new ALStockReporting();
                
                objALStockReporting.setPayroll(rs.getString(1));
                objALStockReporting.setName(rs.getString(2));
                objALStockReporting.setToClearLastYear(rs.getInt(3));
                objALStockReporting.setEntitleCurrYear(rs.getInt(4));
                objALStockReporting.setEarnedYtd(rs.getInt(5));
                objALStockReporting.setTakenMtd(rs.getInt(6));
                objALStockReporting.setTakenYtd(rs.getInt(7));
                
                result.add(objALStockReporting);
            }
            
            // empty table AL stock temporary table
            status = emptyTemp();
            rs.close();
        }
        catch(Exception e) {
            System.out.println("Exc when listALStockReport : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
 
    
    /**
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int emptyTemp() {
        String tblDpReportName = "hr_al_stock_temp";  //"hr_al_stock_temp";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "DELETE FROM " + tblDpReportName;
            
            System.out.println("emptyTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc emptyTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }         
    
    
    /**
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int emptyTempPrev() {
        String tblDpReportName = "hr_al_stock_temp_prev";  //"hr_al_stock_temp_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "DELETE FROM " + tblDpReportName;
            
            System.out.println("emptyTempPrev SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc emptyTempPrev : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }         
    
    /**
     * get earn AL last year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp_PREV"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addEarnLastYearToTempPrev(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName =  "hr_al_stock_temp_prev";  //"hr_al_stock_temp_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+            
            ", SUM(AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]+")"+            
            ", 0" + // taken ytd
            ", 0" + // expired ytd
            " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" AS AL" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonALWhereClause(srcLeaveManagement);                                   
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                
                Date dtLastYear = new Date(selectedDate.getYear()-1, selectedDate.getMonth(), 1);
                strLeavePeriodCondition = " YEAR(AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]+")" +
                " = YEAR(\""+Formater.formatDate(dtLastYear,"yyyy-MM-dd") + "\")";
            }

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }   
            //updat eby ayu
             if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addEarnLastYearToTempPrev SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addEarnLastYearToTempPrev : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    
    /**
     * get taken AL last year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp_PREV"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addTakenLastYearToTempPrev(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_al_stock_temp_prev";  //"hr_al_stock_temp_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+            
            ", 0" + // earn curr year
            ", SUM(TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]+")" +
            ", 0" + // expired ytd
            " FROM "+PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS TAKEN" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];            
            
            String strCommCondition = genCommonALWhereClause(srcLeaveManagement);                                   
            
            String strStockIdCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) 
            {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                
                Date dtLastYear = new Date(selectedDate.getYear()-1, selectedDate.getMonth(), 1);                
                strStockIdCondition = " TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] +
                " IN (SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + 
                " WHERE YEAR(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")" + 
                " = YEAR(\"" + Formater.formatDate(dtLastYear,"yyyy-MM-dd") + "\"))";
            }
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                
                Date dtLastYear = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                strLeavePeriodCondition = " TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+
                " < \""+Formater.formatDate(dtLastYear,"yyyy-MM-dd") + "\"";
            }

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strStockIdCondition!=null && strStockIdCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strStockIdCondition;
                }
                else {
                    whereClause = strStockIdCondition;
                }
            }            

            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            } 
            //update by ayu
             if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY TAKEN." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addTakenLastYearToTempPrev SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addTakenLastYearToTempPrev : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    

    /**
     * get expired AL last year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp_PREV"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addExpiredLastYearToTempPrev(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_al_stock_temp_prev";  //"hr_al_stock_temp_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+            
            ", 0" + // earn curr year
            ", 0" + // taken ytd
            ", SUM(EXP."+PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_QTY]+")" +            
            " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" AS AL" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
            " INNER JOIN "+PstAlStockExpired.TBL_HR_AL_STOCK_EXPIRED+" AS EXP" +
            " ON EXP."+PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_ID]+
            " = AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID];            
            
            String strCommCondition = genCommonALWhereClause(srcLeaveManagement);                                   
            
            String strStockIdCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) 
            {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                
                Date dtLastYear = new Date(selectedDate.getYear()-1, selectedDate.getMonth(), 1);                
                strStockIdCondition = " EXP."+PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_ID] +
                " IN (SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + 
                " WHERE YEAR(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")" + 
                " = YEAR(\"" + Formater.formatDate(dtLastYear,"yyyy-MM-dd") + "\"))";
            }
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                
                Date dtLastYear = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                strLeavePeriodCondition = " EXP."+PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_DATE]+
                " < \""+Formater.formatDate(dtLastYear,"yyyy-MM-dd") + "\"";
            }

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strStockIdCondition!=null && strStockIdCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strStockIdCondition;
                }
                else {
                    whereClause = strStockIdCondition;
                }
            }            

            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }  
            
            //update by ayu
             if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addExpiredLastYearToTempPrev SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addExpiredLastYearToTempPrev : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }

    
    /**
     * get stock AL last year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addToClearLastYearToTemp(SrcLeaveManagement srcLeaveManagement) {        
        String tblDpReportName = "hr_al_stock_temp";
        String tblDpReportNamePrev = "hr_al_stock_temp_prev";        
        DBResultSet dbrs = null;
        int status = 0;
        try {
            
            // empty table AL stock temporary table
            status = emptyTempPrev();
            
            // add data into AL stock temporary table (prev, earn, used and expr)            
            status = addEarnLastYearToTempPrev(srcLeaveManagement);
            status = addTakenLastYearToTempPrev(srcLeaveManagement);
            status = addExpiredLastYearToTempPrev(srcLeaveManagement);

            String sQL = "INSERT INTO " + tblDpReportName + 
                         " SELECT PAYROLL, NAME, (SUM(EARNED)-SUM(TAKEN)-SUM(EXPIRED)), 0, 0, 0, 0"+
                         " FROM " + tblDpReportNamePrev +
                         " GROUP BY PAYROLL";              
            
            System.out.println("addPrevToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
            
            // empty table Dp stock temporary table
            status = emptyTempPrev();                        
        }
        catch(Exception e) {
            System.out.println("Exc addToClearLastYearToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    /**
     * get entitle AL this year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addEntitleThisYearToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_al_stock_temp";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // to clear last year
            ", MAX(AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED]+")"+
            ", 0" + // earned mtd
            ", 0" + // taken mtd
            ", 0" + // taken ytd
            " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" AS AL" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonALWhereClause(srcLeaveManagement);                                   
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                                
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                cal.setTime(selectedDate);                
                Date startDtSelected = new Date(selectedDate.getYear(), 0, 1);
                Date endDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));                
                strLeavePeriodCondition = " AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]+
                " BETWEEN \""+Formater.formatDate(startDtSelected,"yyyy-MM-dd")+"\""+ 
                " AND \""+Formater.formatDate(endDtSelected,"yyyy-MM-dd")+"\"";
            }

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }  
            
            //update by ayu
             if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addEntitleThisYearToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addEntitleThisYearToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    

    /**
     * get earn YTD AL this year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addEarnYearToDateToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_al_stock_temp";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // to clear last year
            ", 0" + // entitle current year
            ", SUM(AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]+")"+            
            ", 0" + // taken mtd
            ", 0" + // taken ytd
            " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" AS AL" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];            
            
            String strCommCondition = genCommonALWhereClause(srcLeaveManagement);                                   
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                                
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                cal.setTime(selectedDate);
                
                Date startDtSelected = new Date(selectedDate.getYear(), 0, 1);
                Date endDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));
                strLeavePeriodCondition = " AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]+
                " BETWEEN \""+Formater.formatDate(startDtSelected,"yyyy-MM-dd")+"\"" + 
                " AND \""+Formater.formatDate(endDtSelected,"yyyy-MM-dd")+"\"";                 
            }            

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }            
            //update by ayu
             if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addEarnYearToDateToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addEarnYearToDateToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    
    /**
     * get taken MTD AL this year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addTakenMonthToDateToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_al_stock_temp";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // to clear last year
            ", 0" + // entitle current year
            ", 0" + // earn ytd            
            ", SUM(TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]+")" +
            ", 0" + // taken ytd
            " FROM "+PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS TAKEN" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonALWhereClause(srcLeaveManagement);                                   
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                                
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                cal.setTime(selectedDate);
                
                Date startDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date endDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));
                strLeavePeriodCondition = " TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+
                " BETWEEN \""+Formater.formatDate(startDtSelected,"yyyy-MM-dd")+"\"" + 
                " AND \""+Formater.formatDate(endDtSelected,"yyyy-MM-dd")+"\"";                 
            }
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            //update by ayu
             if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY TAKEN." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID];            
            
            System.out.println("addTakenMonthToDateToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addTakenMonthToDateToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    /**
     * get taken YTD AL this year per employee based on parameter in search object
     * and then insert them into temporary table called "hr_al_stock_temp"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addTakenYearToDateToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_al_stock_temp";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // to clear last year
            ", 0" + // entitle current year
            ", 0" + // earn ytd
            ", 0" + // taken mtd                        
            ", SUM(TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]+")" +
            " FROM "+PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS TAKEN" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonALWhereClause(srcLeaveManagement);                                               
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                                
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                cal.setTime(selectedDate);
                
                Date startDtSelected = new Date(selectedDate.getYear(), 0, 1);
                Date endDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));
                strLeavePeriodCondition = " TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+
                " BETWEEN \""+Formater.formatDate(startDtSelected,"yyyy-MM-dd")+"\"" + 
                " AND \""+Formater.formatDate(endDtSelected,"yyyy-MM-dd")+"\"";                 
            }            
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            //update by ayu
             if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY TAKEN." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addTakenYearToDateToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addTakenYearToDateToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    
    /**
     * @param srcLeaveManagement
     * @return  
     */    
    public static String genCommonALWhereClause(SrcLeaveManagement srcLeaveManagement)
    {
        String strNameCondition = "";
        if((srcLeaveManagement.getEmpName()!= null)&& (srcLeaveManagement.getEmpName().length()>0)) {
            Vector vectName = logicParser(srcLeaveManagement.getEmpName());
            if(vectName != null && vectName.size()>0) {
                strNameCondition = " (";
                for(int i = 0; i <vectName.size();i++) {
                    String str = (String)vectName.get(i);
                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        strNameCondition = strNameCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                        " LIKE '%"+str.trim()+"%' ";
                    }
                    else {
                        strNameCondition = strNameCondition + str.trim();
                    }
                }
                strNameCondition = strNameCondition + ")";
            }
        }

        String strNumCondition = "";
        if((srcLeaveManagement.getEmpNum()!= null)&& (srcLeaveManagement.getEmpNum().length()>0)) {
            Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
            if(vectNum != null && vectNum.size()>0) {
                strNumCondition = " (";
                for(int i = 0; i <vectNum.size();i++) {
                    String str = (String)vectNum.get(i);
                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        strNumCondition = strNumCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                        " LIKE '"+str.trim()+"%' ";
                    }
                    else {
                        strNumCondition = strNumCondition + str.trim();
                    }
                }
                strNumCondition = strNumCondition + ")";
            }
        }

        String strCategoryCondition = "";
        if(srcLeaveManagement.getEmpCatId() != 0) {
            strCategoryCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+
            " = "+srcLeaveManagement.getEmpCatId();
        }

        String strDepartmentCondition = "";
        if(srcLeaveManagement.getEmpDeptId() != 0) {
            strDepartmentCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
            " = "+srcLeaveManagement.getEmpDeptId();
        }

        String strSectionCondition = "";
        if(srcLeaveManagement.getEmpSectionId() != 0) {
            strSectionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
            " = "+srcLeaveManagement.getEmpSectionId();
        }

        String strPositionCondition = "";
        if(srcLeaveManagement.getEmpPosId() != 0) {
            strPositionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
            " = "+srcLeaveManagement.getEmpPosId();
        }
        // tambahan level
        // intimas
        String strLevelCondition = "";
        if(srcLeaveManagement.getEmpLevelId() != 0) {
            strLevelCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
            " = "+srcLeaveManagement.getEmpLevelId();
        }
        
        String whereClause = "";
        if(strNameCondition!=null && strNameCondition.length()>0) {
            whereClause = strNameCondition;
        }

        if(strNumCondition!=null && strNumCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strNumCondition;
            }
            else {
                whereClause = strNumCondition;
            }
        }

        if(strCategoryCondition!=null && strCategoryCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strCategoryCondition;
            }
            else {
                whereClause = strCategoryCondition;
            }
        }


        if(strDepartmentCondition!=null && strDepartmentCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strDepartmentCondition;
            }
            else {
                whereClause = strDepartmentCondition;
            }
        }

        if(strSectionCondition!=null && strSectionCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strSectionCondition;
            }
            else {
                whereClause = strSectionCondition;
            }
        }

        if(strPositionCondition!=null && strPositionCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strPositionCondition;
            }
            else {
                whereClause = strPositionCondition;
            }
        }
        
        if(strLevelCondition!=null && strLevelCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strLevelCondition;
            }
            else {
                whereClause = strLevelCondition;
            }
        }
        
        
        return whereClause;
    }
    // ------------------ End AL reporting -------------------
    
    
    public static void main(String args[])
    {
        SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
        srcLeaveManagement.setPeriodChecked(false);
        srcLeaveManagement.setLeavePeriod(new Date());
        
        /*
        int status = addExpiredLastYearToTempPrev(srcLeaveManagement);
        System.out.println("status : " +status);
        */        

        Vector vectResult = listALStockReport(srcLeaveManagement);
        if(vectResult!=null && vectResult.size()>0)
        {
            int maxResult = vectResult.size();
            for(int i=0; i<maxResult; i++)
            {
                ALStockReporting objALStockReporting = (ALStockReporting) vectResult.get(i);
                System.out.println("Payroll : " + objALStockReporting.getPayroll());
                System.out.println("Name    : " + objALStockReporting.getName());
                System.out.println("Last    : " + objALStockReporting.getToClearLastYear());
                System.out.println("Entitle : " + objALStockReporting.getEntitleCurrYear());
                System.out.println("Earn    : " + objALStockReporting.getEarnedYtd());
                System.out.println("Tk Mtd  : " + objALStockReporting.getTakenMtd());
                System.out.println("Tk Ytd  : " + objALStockReporting.getTakenYtd());
                System.out.println("");
            }
        }
        else
        {
            System.out.println("Result null ...");
        } 
    }
}
