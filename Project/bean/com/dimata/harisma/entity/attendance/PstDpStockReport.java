/*
 * PstDpStockReport.java
 *
 * Created on September 29, 2004, 4:33 PM
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

/**
 *
 * @author  gedhy
 */
public class PstDpStockReport  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_DP_STOCK_REPORT ="hr_dp_stock_report";// "HR_DP_STOCK_REPORT";
    
    public static final int FLD_DP_STOCK_REPORT_ID = 0;
    public static final int FLD_PERIODE_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_PREV_QTY = 3;
    public static final int FLD_MTD_QTY = 4;
    public static final int FLD_USED_QTY = 5;
    public static final int FLD_EXPIRED_QTY = 6;
    
    public static final String[] fieldNames = {
        "DP_STOCK_REPORT_ID",
        "PERIODE_ID",
        "EMPLOYEE_ID",
        "PREV_QTY",
        "MTD_QTY",
        "USED_QTY",
        "EXPIRED_QTY"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT
    };
    
    public PstDpStockReport() {
    }
    
    public PstDpStockReport(int i) throws DBException {
        super(new PstDpStockReport());
    }
    
    public PstDpStockReport(String sOid) throws DBException {
        super(new PstDpStockReport(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDpStockReport(long lOid) throws DBException {
        super(new PstDpStockReport(0));
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
        return TBL_DP_STOCK_REPORT;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstDpStockReport().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        DpStockReport objDpStockReport = fetchExc(ent.getOID());
        return objDpStockReport.getOID();
    }
    
    public static DpStockReport fetchExc(long oid) throws DBException {
        try {
            DpStockReport objDpStockReport = new DpStockReport();
            PstDpStockReport objPstDpStockReport = new PstDpStockReport(oid);
            objDpStockReport.setOID(oid);
            
            objDpStockReport.setPeriodId(objPstDpStockReport.getlong(FLD_PERIODE_ID));
            objDpStockReport.setEmpId(objPstDpStockReport.getlong(FLD_EMPLOYEE_ID));
            objDpStockReport.setPrevQty(objPstDpStockReport.getfloat(FLD_PREV_QTY));
            objDpStockReport.setMtdQty(objPstDpStockReport.getfloat(FLD_MTD_QTY));
            objDpStockReport.setUsedQty(objPstDpStockReport.getfloat(FLD_USED_QTY));
            objDpStockReport.setExpiredQty(objPstDpStockReport.getfloat(FLD_EXPIRED_QTY));
            
            return objDpStockReport;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockReport(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((DpStockReport) ent);
    }
    
    public static long updateExc(DpStockReport objDpStockReport) throws DBException {
        try {
            if (objDpStockReport.getOID() != 0) {
                PstDpStockReport objPstDpStockReport = new PstDpStockReport(objDpStockReport.getOID());
                
                objPstDpStockReport.setLong(FLD_PERIODE_ID, objDpStockReport.getPeriodId());
                objPstDpStockReport.setLong(FLD_EMPLOYEE_ID, objDpStockReport.getEmpId());
                objPstDpStockReport.setFloat(FLD_PREV_QTY, objDpStockReport.getPrevQty());
                objPstDpStockReport.setFloat(FLD_MTD_QTY, objDpStockReport.getMtdQty());
                objPstDpStockReport.setFloat(FLD_USED_QTY, objDpStockReport.getUsedQty());
                objPstDpStockReport.setFloat(FLD_EXPIRED_QTY, objDpStockReport.getExpiredQty());
                
                objPstDpStockReport.update();
                return objDpStockReport.getOID();
                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockReport(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstDpStockReport objPstDpStockReport = new PstDpStockReport(oid);
            objPstDpStockReport.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockReport(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((DpStockReport)ent);
    }
    
    public static long insertExc(DpStockReport objDpStockReport) throws DBException {
        try {
            PstDpStockReport objPstDpStockReport = new PstDpStockReport(0);
            
            objPstDpStockReport.setLong(FLD_PERIODE_ID, objDpStockReport.getPeriodId());
            objPstDpStockReport.setLong(FLD_EMPLOYEE_ID, objDpStockReport.getEmpId());
            objPstDpStockReport.setFloat(FLD_PREV_QTY, objDpStockReport.getPrevQty());
            objPstDpStockReport.setFloat(FLD_MTD_QTY, objDpStockReport.getMtdQty());
            objPstDpStockReport.setFloat(FLD_USED_QTY, objDpStockReport.getUsedQty());
            objPstDpStockReport.setFloat(FLD_EXPIRED_QTY, objDpStockReport.getExpiredQty());
            
            objPstDpStockReport.insert();
            objDpStockReport.setOID(objPstDpStockReport.getlong(FLD_DP_STOCK_REPORT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockReport(0), DBException.UNKNOWN);
        }
        return objDpStockReport.getOID();
    }
    
    
    
    /**
     * @param rs
     * @param objDpStockReport
     */
    private static void resultToObject(ResultSet rs, DpStockReport objDpStockReport) {
        try {
            objDpStockReport.setOID(rs.getLong(PstDpStockReport.fieldNames[PstDpStockReport.FLD_DP_STOCK_REPORT_ID]));
            objDpStockReport.setPeriodId(rs.getLong(PstDpStockReport.fieldNames[PstDpStockReport.FLD_PERIODE_ID]));
            objDpStockReport.setEmpId(rs.getLong(PstDpStockReport.fieldNames[PstDpStockReport.FLD_EMPLOYEE_ID]));
            objDpStockReport.setPrevQty(rs.getFloat(PstDpStockReport.fieldNames[PstDpStockReport.FLD_PREV_QTY]));
            objDpStockReport.setMtdQty(rs.getFloat(PstDpStockReport.fieldNames[PstDpStockReport.FLD_MTD_QTY]));
            objDpStockReport.setUsedQty(rs.getFloat(PstDpStockReport.fieldNames[PstDpStockReport.FLD_USED_QTY]));
            objDpStockReport.setExpiredQty(rs.getFloat(PstDpStockReport.fieldNames[PstDpStockReport.FLD_EXPIRED_QTY]));
            
        }
        catch (Exception e) {
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
            String sql = "SELECT * FROM " + TBL_DP_STOCK_REPORT;
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
                DpStockReport dpStockReport = new DpStockReport();
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
            String sql = "SELECT COUNT("+ PstDpStockReport.fieldNames[PstDpStockReport.FLD_DP_STOCK_REPORT_ID] + ") FROM " + TBL_DP_STOCK_REPORT;
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
    public static DpStockReport getDpStockReport(long periodId, long employeeId)
    {        
        DpStockReport objDpStockReport = new DpStockReport();
        String whereClause = PstDpStockReport.fieldNames[PstDpStockReport.FLD_PERIODE_ID] + 
                             " = " + periodId + 
                             " AND " + PstDpStockReport.fieldNames[PstDpStockReport.FLD_EMPLOYEE_ID] + 
                             " = " + employeeId;
        Vector vectDpStockReport = list(0, 0, whereClause, "");
        if(vectDpStockReport!=null && vectDpStockReport.size()>0)
        {
            objDpStockReport = (DpStockReport)vectDpStockReport.get(0);
        }        
        
        return objDpStockReport;
    }
    
    /**     
     * @param objDpStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */    
    public static long storeDpStockReport(DpStockReport objDpStockReport, long periodId, long employeeId, float prevQty, float mtdQty, float usedQty, float expiredQty)
    {    
        long oidDpStockReport = 0;
        
        // insert new record to DpStockReport
        if(objDpStockReport.getOID() == 0)
        {
            prevQty = prevQty + getDpStockBalanceLastPeriod(periodId, employeeId);
            try
            {
                objDpStockReport.setPeriodId(periodId);
                objDpStockReport.setEmpId(employeeId);
                objDpStockReport.setPrevQty(prevQty);
                objDpStockReport.setMtdQty(mtdQty);
                objDpStockReport.setUsedQty(usedQty);
                objDpStockReport.setExpiredQty(expiredQty);
                
                oidDpStockReport = insertExc(objDpStockReport);
            }
            catch(Exception e)
            {
                System.out.println("Exc when storeDpStockReport - INSERT : " + e.toString());
            }
        }
        
        // update selected record DpStockReport
        else
        {
            try
            {
                float oldMtdQty = objDpStockReport.getMtdQty();
                float oldUsedQty = objDpStockReport.getUsedQty(); 
                float oldExpiredQty = objDpStockReport.getExpiredQty();
                
                objDpStockReport.setMtdQty(oldMtdQty+mtdQty);  
                objDpStockReport.setUsedQty(oldUsedQty+usedQty);
                objDpStockReport.setExpiredQty(oldExpiredQty+expiredQty);  
                
                oidDpStockReport = updateExc(objDpStockReport);                
            }
            catch(Exception e)
            {
                System.out.println("Exc when storeDpStockReport - UPDATE : " + e.toString());
            }            
        }        
        
        return oidDpStockReport;
    }

    
    /**
     * @param periodId
     * @created by Edhy
     */    
    public static void transferLastDpStockReport(long yesterdayPeriodId, long periodId)
    {            
        String whereClause = PstDpStockReport.fieldNames[PstDpStockReport.FLD_PERIODE_ID] + " = " + yesterdayPeriodId;
        Vector vectDpStockReport = list(0, 0, whereClause, "");      
        if(vectDpStockReport!=null && vectDpStockReport.size()>0)
        {
            int maxDpStockReport = vectDpStockReport.size();
            for(int i=0; i<maxDpStockReport; i++)
            {
                DpStockReport objDpStockReport = (DpStockReport) vectDpStockReport.get(i);
                float dpStockBalance = objDpStockReport.getPrevQty() + objDpStockReport.getMtdQty() - objDpStockReport.getUsedQty() - objDpStockReport.getExpiredQty(); 
                
                // get object DpStockReport if exist in this currenct period
                DpStockReport objDpStockReportWillInsert = getDpStockReport(periodId, objDpStockReport.getEmpId());
                
                // if selected object that searched if null, then insert new
                if(objDpStockReportWillInsert.getOID() == 0)
                {
                    objDpStockReportWillInsert.setPeriodId(periodId);
                    objDpStockReportWillInsert.setEmpId(objDpStockReport.getEmpId());
                    objDpStockReportWillInsert.setPrevQty(dpStockBalance);
                    objDpStockReportWillInsert.setMtdQty(0);
                    objDpStockReportWillInsert.setUsedQty(0);
                    objDpStockReportWillInsert.setExpiredQty(0);  

                    try
                    {
                        long oidDpStockReport = PstDpStockReport.insertExc(objDpStockReportWillInsert);
                    }
                    catch(Exception e)
                    {
                        System.out.println("transferLastDpStockReport exc : " + e.toString());
                    }
                }
            }            
        }
        
    }

    
    /**
     * @param currPeriodId
     * @param employeeId
     * @return
     */    
    public static float getDpStockBalanceLastPeriod(long currPeriodId, long employeeId)
    {
        float result = 0;
        long lastPeriodId = PstPeriod.getPeriodIdJustBefore(currPeriodId);
        
        String whereClause = PstDpStockReport.fieldNames[PstDpStockReport.FLD_PERIODE_ID] + 
                             "=" + lastPeriodId + 
                             " AND " + PstDpStockReport.fieldNames[PstDpStockReport.FLD_EMPLOYEE_ID] + 
                             "=" + employeeId;
        Vector vectDpStockReport = list(0, 0, whereClause, "");
        if(vectDpStockReport!=null && vectDpStockReport.size()>0)
        {
            DpStockReport objDpStockReport = (DpStockReport) vectDpStockReport.get(0);
            result = objDpStockReport.getPrevQty() + objDpStockReport.getMtdQty() - objDpStockReport.getUsedQty() - objDpStockReport.getExpiredQty();
        }
        
        return result;
    }
    
    
    public static void processDpStockToReport() 
    {
        String whereClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + 
                             " = " + PstDpStockManagement.DP_STS_AKTIF;
        String orderBy = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        Vector vectDpStock = PstDpStockManagement.list(0, 0, whereClause, orderBy);
        if(vectDpStock!=null && vectDpStock.size()>0)
        {
            int intMaxDp = vectDpStock.size();
            for(int i=0; i<intMaxDp; i++)
            {
                DpStockManagement dpStockManagement = (DpStockManagement) vectDpStock.get(i);

                // process to dpStockManagement
                if(dpStockManagement.getiDpStatus() != PstDpStockManagement.DP_STS_NOT_AKTIF)
                {
                    try  
                    {
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(dpStockManagement.getDtOwningDate());                     
                        
                        float intDpPrev = dpStockManagement.getiDpQty();
                        float intDpQty = 0;
                        float intDpUsed = dpStockManagement.getQtyUsed();
                         
                        DpStockReport objDpStockReport = PstDpStockReport.getDpStockReport(periodId, dpStockManagement.getEmployeeId());                                            
                        if(objDpStockReport.getOID() != 0)
                        {
                            intDpPrev = 0;
                            intDpQty = dpStockManagement.getiDpQty() + objDpStockReport.getMtdQty();
                            //intDpUsed = intDpUsed + objDpStockReport.getUsedQty();
                        }                        
                        
                        long oidDpStockReport = PstDpStockReport.storeDpStockReport(objDpStockReport, periodId, dpStockManagement.getEmployeeId(), intDpPrev, intDpQty, intDpUsed, 0);
                    }
                    catch(Exception e)
                    {
                        System.out.println("--- processDpStockToReport exc : " + e.toString());
                    }   
                }                      
            }
        }        
    }
    
    
    /**
     * @param owningDate
     */    
    public static void processDpStockToReport(Date owningDate) 
    {
        String strOwningDate = Formater.formatDate(owningDate, "yyyy-MM-dd");
        String whereClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + 
                             " = " + PstDpStockManagement.DP_STS_AKTIF + 
                             " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +  
                             " \"" + strOwningDate + "\"";
        String orderBy = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        Vector vectDpStock = PstDpStockManagement.list(0, 0, whereClause, orderBy);
        System.out.println("vectDpStock : " + vectDpStock.size());
        if(vectDpStock!=null && vectDpStock.size()>0)
        {
            int intMaxDp = vectDpStock.size();
            for(int i=0; i<intMaxDp; i++)
            {
                DpStockManagement dpStockManagement = (DpStockManagement) vectDpStock.get(i);

                // process to dpStockManagement
                if(dpStockManagement.getiDpStatus() != PstDpStockManagement.DP_STS_NOT_AKTIF)
                {
                    try  
                    {
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(dpStockManagement.getDtOwningDate());                     
                        
                        float intDpPrev = dpStockManagement.getiDpQty();
                        float intDpQty = 0;
                        float intDpUsed = dpStockManagement.getQtyUsed();
                         
                        DpStockReport objDpStockReport = PstDpStockReport.getDpStockReport(periodId, dpStockManagement.getEmployeeId());                                            
                        if(objDpStockReport.getOID() != 0)
                        {
                            intDpPrev = 0;
                            intDpQty = dpStockManagement.getiDpQty() + objDpStockReport.getMtdQty();
                            //intDpUsed = intDpUsed + objDpStockReport.getUsedQty();
                        }                        
                        
                        long oidDpStockReport = PstDpStockReport.storeDpStockReport(objDpStockReport, periodId, dpStockManagement.getEmployeeId(), intDpPrev, intDpQty, intDpUsed, 0);
                    }
                    catch(Exception e)
                    {
                        System.out.println("--- processDpStockToReport exc : " + e.toString());
                    }   
                }                      
            }
        }        
    }    
    
}
