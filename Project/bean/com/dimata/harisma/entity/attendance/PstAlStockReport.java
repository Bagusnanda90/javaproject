/*
 * PstAlStockReport.java
 *
 * Created on October 2, 2004, 9:23 AM
 */

package com.dimata.harisma.entity.attendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Date;

import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;

import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.employee.*;

/**
 *
 * @author  gedhy
 */
public class PstAlStockReport extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_AL_STOCK_REPORT ="hr_al_stock_report";// "HR_AL_STOCK_REPORT";
    
    public static final int FLD_AL_STOCK_REPORT_ID = 0;
    public static final int FLD_PERIODE_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_LAST_YEAR_QTY = 3;
    public static final int FLD_ENT_THIS_YEAR_QTY = 4;
    public static final int FLD_EARNED_YTD_QTY = 5;
    public static final int FLD_TAKEN_MTD_QTY = 6;
    public static final int FLD_TAKEN_YTD_QTY = 7;
    
    public static final String[] fieldNames = {
        "AL_STOCK_REPORT_ID",
        "PERIODE_ID",
        "EMPLOYEE_ID",
        "LAST_YEAR_QTY",
        "ENT_THIS_YEAR_QTY",
        "EARNED_YTD_QTY",
        "FLD_TAKEN_MTD_QTY",
        "FLD_TAKEN_YTD_QTY"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };
    
    public static final int TOTAL_ENTITLED_AL_DEFAULT = 13;
    
    public PstAlStockReport() {
    }
    
    public PstAlStockReport(int i) throws DBException {
        super(new PstAlStockReport());
    }
    
    public PstAlStockReport(String sOid) throws DBException {
        super(new PstAlStockReport(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstAlStockReport(long lOid) throws DBException {
        super(new PstAlStockReport(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_AL_STOCK_REPORT;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstAlStockReport().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        AlStockReport objAlStockReport = fetchExc(ent.getOID());
        return objAlStockReport.getOID();
    }
    
    public static AlStockReport fetchExc(long oid) throws DBException {
        try {
            AlStockReport objAlStockReport = new AlStockReport();
            PstAlStockReport objPstAlStockReport = new PstAlStockReport(oid);
            objAlStockReport.setOID(oid);
            
            objAlStockReport.setPeriodId(objPstAlStockReport.getlong(FLD_PERIODE_ID));
            objAlStockReport.setEmployeeId(objPstAlStockReport.getlong(FLD_EMPLOYEE_ID));
            objAlStockReport.setLastYearQty(objPstAlStockReport.getfloat(FLD_LAST_YEAR_QTY));
            objAlStockReport.setEntThisYearQty(objPstAlStockReport.getfloat(FLD_ENT_THIS_YEAR_QTY));
            objAlStockReport.setEarnedYtdQty(objPstAlStockReport.getfloat(FLD_EARNED_YTD_QTY));
            objAlStockReport.setTakenMtdQty(objPstAlStockReport.getfloat(FLD_TAKEN_MTD_QTY));
            objAlStockReport.setTakenYtdQty(objPstAlStockReport.getfloat(FLD_TAKEN_YTD_QTY));
            
            return objAlStockReport;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockReport(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((AlStockReport) ent);
    }
    
    public static long updateExc(AlStockReport objAlStockReport) throws DBException {
        try {
            if (objAlStockReport.getOID() != 0) {
                PstAlStockReport objPstAlStockReport = new PstAlStockReport(objAlStockReport.getOID());
                
                objPstAlStockReport.setLong(FLD_PERIODE_ID, objAlStockReport.getPeriodId());
                objPstAlStockReport.setLong(FLD_EMPLOYEE_ID, objAlStockReport.getEmployeeId());
                objPstAlStockReport.setFloat(FLD_LAST_YEAR_QTY, objAlStockReport.getLastYearQty());
                objPstAlStockReport.setFloat(FLD_ENT_THIS_YEAR_QTY, objAlStockReport.getEntThisYearQty());
                objPstAlStockReport.setFloat(FLD_EARNED_YTD_QTY, objAlStockReport.getEarnedYtdQty());
                objPstAlStockReport.setFloat(FLD_TAKEN_MTD_QTY, objAlStockReport.getTakenMtdQty());
                objPstAlStockReport.setFloat(FLD_TAKEN_YTD_QTY, objAlStockReport.getTakenYtdQty());
                
                objPstAlStockReport.update();
                return objAlStockReport.getOID();
                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockReport(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstAlStockReport objPstAlStockReport = new PstAlStockReport(oid);
            objPstAlStockReport.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockReport(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((AlStockReport)ent);
    }
    
    public static long insertExc(AlStockReport objAlStockReport) throws DBException {
        try {
            PstAlStockReport objPstAlStockReport = new PstAlStockReport(0);
            
            objPstAlStockReport.setLong(FLD_PERIODE_ID, objAlStockReport.getPeriodId());
            objPstAlStockReport.setLong(FLD_EMPLOYEE_ID, objAlStockReport.getEmployeeId());
            objPstAlStockReport.setFloat(FLD_LAST_YEAR_QTY, objAlStockReport.getLastYearQty());
            objPstAlStockReport.setFloat(FLD_ENT_THIS_YEAR_QTY, objAlStockReport.getEntThisYearQty());
            objPstAlStockReport.setFloat(FLD_EARNED_YTD_QTY, objAlStockReport.getEarnedYtdQty());
            objPstAlStockReport.setFloat(FLD_TAKEN_MTD_QTY, objAlStockReport.getTakenMtdQty());
            objPstAlStockReport.setFloat(FLD_TAKEN_YTD_QTY, objAlStockReport.getTakenYtdQty());
            
            objPstAlStockReport.insert();
            objAlStockReport.setOID(objPstAlStockReport.getlong(FLD_AL_STOCK_REPORT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockReport(0), DBException.UNKNOWN);
        }
        return objAlStockReport.getOID();
    }
    
    
    
    /**
     * @param rs
     * @param objAlStockReport
     */
    private static void resultToObject(ResultSet rs, AlStockReport objAlStockReport) {
        try 
        {
            objAlStockReport.setOID(rs.getLong(PstAlStockReport.fieldNames[PstAlStockReport.FLD_AL_STOCK_REPORT_ID]));
            objAlStockReport.setPeriodId(rs.getLong(PstAlStockReport.fieldNames[PstAlStockReport.FLD_PERIODE_ID]));
            objAlStockReport.setEmployeeId(rs.getLong(PstAlStockReport.fieldNames[PstAlStockReport.FLD_EMPLOYEE_ID]));
            objAlStockReport.setLastYearQty(rs.getFloat(PstAlStockReport.fieldNames[PstAlStockReport.FLD_LAST_YEAR_QTY]));
            objAlStockReport.setEntThisYearQty(rs.getFloat(PstAlStockReport.fieldNames[PstAlStockReport.FLD_ENT_THIS_YEAR_QTY]));
            objAlStockReport.setEarnedYtdQty(rs.getFloat(PstAlStockReport.fieldNames[PstAlStockReport.FLD_EARNED_YTD_QTY]));
            objAlStockReport.setTakenMtdQty(rs.getFloat(PstAlStockReport.fieldNames[PstAlStockReport.FLD_TAKEN_MTD_QTY]));
            objAlStockReport.setTakenYtdQty(rs.getFloat(PstAlStockReport.fieldNames[PstAlStockReport.FLD_TAKEN_YTD_QTY]));            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    
    public static Vector listAll(){
        return list(0, 500, "","");
    }
    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_AL_STOCK_REPORT;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                AlStockReport dpStockReport = new AlStockReport();
                resultToObject(rs, dpStockReport);
                lists.add(dpStockReport);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstAlStockReport.fieldNames[PstAlStockReport.FLD_AL_STOCK_REPORT_ID] + ") FROM " + TBL_AL_STOCK_REPORT;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while(rs.next()) { count = rs.getInt(1); }
            
            rs.close();
            return count;
        }catch(Exception e) {
            return 0;
        }finally {
            DBResultSet.close(dbrs);
        }
    }
    
    
    /* This method used to find current data */
    public static int findLimitStart( long oid, int recordToGet, String whereClause){
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found =false;
        for(int i=0; (i < size) && !found ; i=i+recordToGet){
            Vector list =  list(i,recordToGet, whereClause, order);
            start = i;
            if(list.size()>0){
                for(int ls=0;ls<list.size();ls++){
                    PrevLeave prevleave = (PrevLeave)list.get(ls);
                    if(oid == prevleave.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    
    /**
     * @param periodId
     * @param employeeId
     * @return
     */    
    public static AlStockReport getAlStockReport(long periodId, long employeeId)
    {        
        AlStockReport objAlStockReport = new AlStockReport();
        String whereClause = PstAlStockReport.fieldNames[PstAlStockReport.FLD_PERIODE_ID] + 
                             " = " + periodId + 
                             " AND " + PstAlStockReport.fieldNames[PstAlStockReport.FLD_EMPLOYEE_ID] + 
                             " = " + employeeId;
        Vector vectAlStockReport = list(0, 0, whereClause, "");
        if(vectAlStockReport!=null && vectAlStockReport.size()>0)
        {
            objAlStockReport = (AlStockReport)vectAlStockReport.get(0);
        }        
        
        return objAlStockReport;
    }
    
    /**     
     * @param objAlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */    
    public static long storeAlStockReport(AlStockReport objAlStockReport, long periodId, long employeeId, float lastYearQty, float entThisYearQty, float earnedYtdQty, float takenMtdQty, float takenYtdQty)
    {    
        long oidAlStockReport = 0;
        
        // insert new record to AlStockReport
        if(objAlStockReport.getOID() == 0)
        {
            //prevQty = prevQty + getAlStockReport(periodId, employeeId);
            try
            {
                objAlStockReport.setPeriodId(periodId);
                objAlStockReport.setEmployeeId(employeeId);
                objAlStockReport.setLastYearQty(lastYearQty);
                objAlStockReport.setEntThisYearQty(entThisYearQty);
                objAlStockReport.setEarnedYtdQty(earnedYtdQty);
                objAlStockReport.setTakenMtdQty(takenMtdQty);
                objAlStockReport.setTakenYtdQty(takenYtdQty);
                
                oidAlStockReport = insertExc(objAlStockReport);
            }
            catch(Exception e)
            {
                System.out.println("Exc when storeAlStockReport - INSERT : " + e.toString());
            }
        }
        
        // update selected record AlStockReport
        else
        {
            try
            {
                float intLastYearOld = objAlStockReport.getLastYearQty();
                float intEntThisMonthOld = objAlStockReport.getEntThisYearQty();
                float intEarnMthOld = objAlStockReport.getEarnedYtdQty();
                float intTakenMtdOld = objAlStockReport.getTakenMtdQty();
                float intTakenYtdOld = objAlStockReport.getTakenYtdQty();  
                
                objAlStockReport.setLastYearQty(intLastYearOld+lastYearQty);
                objAlStockReport.setEntThisYearQty(intEntThisMonthOld+entThisYearQty);
                objAlStockReport.setEarnedYtdQty(intEarnMthOld+earnedYtdQty);
                objAlStockReport.setTakenMtdQty(intTakenMtdOld+takenMtdQty);
                objAlStockReport.setTakenYtdQty(intTakenYtdOld+takenYtdQty);     
                
                oidAlStockReport = updateExc(objAlStockReport);                
            }
            catch(Exception e)
            {
                System.out.println("Exc when storeAlStockReport - UPDATE : " + e.toString());
            }            
        }        
        
        return oidAlStockReport;
    }


    /**     
     * @param objAlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */    
    public static long insertNewsAlStockReport(long periodId, long employeeId, float lastYearQty, float entThisYearQty, float earnedYtdQty, float takenMtdQty, float takenYtdQty)
    {    
        long oidAlStockReport = 0;        
        try
        {
            AlStockReport objAlStockReport = new AlStockReport();  
            objAlStockReport.setPeriodId(periodId);
            objAlStockReport.setEmployeeId(employeeId);
            objAlStockReport.setLastYearQty(lastYearQty);
            objAlStockReport.setEntThisYearQty(entThisYearQty);
            objAlStockReport.setEarnedYtdQty(earnedYtdQty);
            objAlStockReport.setTakenMtdQty(takenMtdQty);
            objAlStockReport.setTakenYtdQty(takenYtdQty);

            oidAlStockReport = insertExc(objAlStockReport);
        }
        catch(Exception e)
        {
            System.out.println("Exc when insertNewsAlStockReport : " + e.toString());
        }
        return oidAlStockReport;
    }

    
    /**     
     * @param objAlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */    
    public static long updateExistingAlStockReport(AlStockReport objAlStockReport, long periodId, long employeeId, float lastYearQty, float entThisYearQty, float earnedYtdQty, float takenMtdQty, float takenYtdQty)
    {    
        long oidAlStockReport = 0;
        
        // update existing record to AlStockReport
        if(objAlStockReport.getOID() != 0)      
        {
            try
            {
                float intLastYearOld = objAlStockReport.getLastYearQty();
                float intEntThisMonthOld = objAlStockReport.getEntThisYearQty();
                float intEarnMthOld = objAlStockReport.getEarnedYtdQty();
                float intTakenMtdOld = objAlStockReport.getTakenMtdQty();
                float intTakenYtdOld = objAlStockReport.getTakenYtdQty();  
                
                objAlStockReport.setLastYearQty(intLastYearOld+lastYearQty);
                objAlStockReport.setEntThisYearQty(intEntThisMonthOld+entThisYearQty);
                objAlStockReport.setEarnedYtdQty(intEarnMthOld+earnedYtdQty);
                objAlStockReport.setTakenMtdQty(intTakenMtdOld+takenMtdQty);
                objAlStockReport.setTakenYtdQty(intTakenYtdOld+takenYtdQty);     
                
                oidAlStockReport = updateExc(objAlStockReport);                
            }
            catch(Exception e)
            {
                System.out.println("Exc when updateExistingAlStockReport : " + e.toString());
            }            
        }        
        
        return oidAlStockReport;
    }
    
    /**     
     * @param objAlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */    
    public static int updateAlStockReportExpired(long periodId)
    {    
        if(periodId != 0)
        {            
            int result = 0;        
            try 
            {
                String sql = "UPDATE " + TBL_AL_STOCK_REPORT + 
                             " SET " + fieldNames[FLD_LAST_YEAR_QTY] +
                             " = 0 " + 
                             " WHERE " + fieldNames[FLD_PERIODE_ID] + 
                             " = " + periodId;            
                System.out.println("sQL updateAlStockReportExpired : " + sql);             
                result = DBHandler.execUpdate(sql);                        
            }
            catch(Exception e) 
            {
                System.out.println("Exc updateAlStockReportExpired : " + e.toString());
            }
            finally 
            {            
                return result;
            }
        }
        
        return 0;

    }

    
    /**
     * @param periodId
     * @created by Edhy
     */    
    public static void transferLastAlStockReport(long lastPeriodId, long periodId)
    {            
        String whereClause = PstAlStockReport.fieldNames[PstAlStockReport.FLD_PERIODE_ID] + " = " + lastPeriodId;
        Vector vectAlStockReport = list(0, 0, whereClause, "");
        if(vectAlStockReport!=null && vectAlStockReport.size()>0)
        {
            int maxAlStockReport = vectAlStockReport.size();
            for(int i=0; i<maxAlStockReport; i++)
            {
                AlStockReport objAlStockReport = (AlStockReport) vectAlStockReport.get(i);
                
                float alLastYearQtyBalance = objAlStockReport.getLastYearQty() - objAlStockReport.getTakenYtdQty(); 
                if(alLastYearQtyBalance < 0)  alLastYearQtyBalance = 0;
                
                float alEntThisYear = objAlStockReport.getEntThisYearQty();                
                float alEarnYtd = objAlStockReport.getEarnedYtdQty();                
                float alTakenYtd = objAlStockReport.getTakenYtdQty();
                
                // set value to new 
                AlStockReport objAlStockReportWillInsert = getAlStockReport(periodId, objAlStockReport.getEmployeeId());
                if(objAlStockReportWillInsert.getOID() == 0)
                {
                    objAlStockReportWillInsert.setPeriodId(periodId);
                    objAlStockReportWillInsert.setEmployeeId(objAlStockReport.getEmployeeId());
                    objAlStockReportWillInsert.setLastYearQty(alLastYearQtyBalance);
                    objAlStockReportWillInsert.setEntThisYearQty(alEntThisYear);
                    objAlStockReportWillInsert.setEarnedYtdQty(alEarnYtd);
                    objAlStockReportWillInsert.setTakenMtdQty(0);  
                    objAlStockReportWillInsert.setTakenYtdQty(alTakenYtd);  

                    try
                    {
                        long oidAlStockReport = PstAlStockReport.insertExc(objAlStockReportWillInsert);
                    }
                    catch(Exception e)
                    {
                        System.out.println("transferLastAlStockReport if exc : " + e.toString());
                    }
                }
            }            
        }
        
        
        // if no AlStockReport available yet, insert new AlStockReport
        else
        {   
            if(periodId != 0)
            {
                String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
                Vector vct = PstEmployee.list(0, 0, where, null);

                if(vct!=null && vct.size()>0)
                {
                    int maxEmp = vct.size();
                    for(int i=0; i<maxEmp; i++)
                    {
                        Employee emp = (Employee) vct.get(i); 
                        AlStockReport objAlStockReport = new AlStockReport();

                        objAlStockReport.setPeriodId(periodId);
                        objAlStockReport.setEmployeeId(emp.getOID());
                        objAlStockReport.setLastYearQty(0);
                        objAlStockReport.setEntThisYearQty(TOTAL_ENTITLED_AL_DEFAULT);
                        objAlStockReport.setEarnedYtdQty(0);
                        objAlStockReport.setTakenMtdQty(0);  
                        objAlStockReport.setTakenYtdQty(0);      

                        try
                        {
                            long oidAlStockReport = PstAlStockReport.insertExc(objAlStockReport);
                        }
                        catch(Exception e)
                        {
                            System.out.println("transferLastAlStockReport else exc : " + e.toString());
                        }                                        
                    }
                }            
            }
        }        
    }
}
