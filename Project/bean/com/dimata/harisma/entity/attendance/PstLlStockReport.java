/*
 * PstLlStockReport.java
 *
 * Created on October 13, 2004, 4:19 PM
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
public class PstLlStockReport extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_LL_STOCK_REPORT ="hr_ll_stock_report";// "HR_LL_STOCK_REPORT";
    
    public static final int FLD_LL_STOCK_REPORT_ID = 0;
    public static final int FLD_PERIODE_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;    
    public static final int FLD_QTY_ENTITLE1 = 3;
    public static final int FLD_QTY_ENTITLE2 = 4;
    public static final int FLD_QTY_ENTITLE3 = 5;
    public static final int FLD_QTY_ENTITLE4 = 6;
    public static final int FLD_QTY_ENTITLE5 = 7;
    public static final int FLD_QTY_ENTITLE6 = 8;
    public static final int FLD_QTY_ENTITLE7 = 9;
    public static final int FLD_QTY_ENTITLE8 = 10;
    public static final int FLD_QTY_ENTITLE9 = 11;
    public static final int FLD_QTY_ENTITLE10 = 12;
    public static final int FLD_QTY_TOTAL_LL = 13;    
    public static final int FLD_QTY_TAKEN_MTD = 14;
    public static final int FLD_QTY_TAKEN_YTD = 15;
    
    public static final String[] fieldNames = {
        "LL_STOCK_REPORT_ID",
        "PERIODE_ID",
        "EMPLOYEE_ID",
        "QTY_ENT1",
        "QTY_ENT2",
        "QTY_ENT3",
        "QTY_ENT4",
        "QTY_ENT5",
        "QTY_ENT6",
        "QTY_ENT7",
        "QTY_ENT8",
        "QTY_ENT9",
        "QTY_ENT10",
        "QTY_TOTAL_LL",
        "QTY_TAKEN_MTD",
        "QTY_TAKEN_YTD"        
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,        
        TYPE_FLOAT,
        TYPE_FLOAT
    };
    
    public PstLlStockReport() {
    }
    
    public PstLlStockReport(int i) throws DBException {
        super(new PstLlStockReport());
    }
    
    public PstLlStockReport(String sOid) throws DBException {
        super(new PstLlStockReport(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstLlStockReport(long lOid) throws DBException {
        super(new PstLlStockReport(0));
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
        return TBL_LL_STOCK_REPORT;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstLlStockReport().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        LlStockReport objLlStockReport = fetchExc(ent.getOID());
        return objLlStockReport.getOID();
    }
    
    public static LlStockReport fetchExc(long oid) throws DBException {
        try {
            LlStockReport objLlStockReport = new LlStockReport();
            PstLlStockReport objPstLlStockReport = new PstLlStockReport(oid);
            objLlStockReport.setOID(oid);
            
            objLlStockReport.setPeriodId(objPstLlStockReport.getlong(FLD_PERIODE_ID));
            objLlStockReport.setEmployeeId(objPstLlStockReport.getlong(FLD_EMPLOYEE_ID));
            objLlStockReport.setQtyEnt10(objPstLlStockReport.getInt(FLD_QTY_ENTITLE1));
            objLlStockReport.setQtyEnt2(objPstLlStockReport.getInt(FLD_QTY_ENTITLE2));
            objLlStockReport.setQtyEnt3(objPstLlStockReport.getInt(FLD_QTY_ENTITLE3));
            objLlStockReport.setQtyEnt4(objPstLlStockReport.getInt(FLD_QTY_ENTITLE4));
            objLlStockReport.setQtyEnt5(objPstLlStockReport.getInt(FLD_QTY_ENTITLE5));
            objLlStockReport.setQtyEnt6(objPstLlStockReport.getInt(FLD_QTY_ENTITLE6));
            objLlStockReport.setQtyEnt7(objPstLlStockReport.getInt(FLD_QTY_ENTITLE7));
            objLlStockReport.setQtyEnt8(objPstLlStockReport.getInt(FLD_QTY_ENTITLE8));
            objLlStockReport.setQtyEnt9(objPstLlStockReport.getInt(FLD_QTY_ENTITLE9));
            objLlStockReport.setQtyEnt10(objPstLlStockReport.getInt(FLD_QTY_ENTITLE10));            
            objLlStockReport.setQtyLlTotal(objPstLlStockReport.getInt(FLD_QTY_TOTAL_LL));
            objLlStockReport.setQtyLlTakenMtd(objPstLlStockReport.getInt(FLD_QTY_TAKEN_MTD));
            objLlStockReport.setQtyLlTakenYtd(objPstLlStockReport.getInt(FLD_QTY_TAKEN_YTD));
            
            return objLlStockReport;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLlStockReport(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((LlStockReport) ent);
    }
    
    public static long updateExc(LlStockReport objLlStockReport) throws DBException {
        try {
            if (objLlStockReport.getOID() != 0) {
                PstLlStockReport objPstLlStockReport = new PstLlStockReport(objLlStockReport.getOID());
                
                objPstLlStockReport.setLong(FLD_PERIODE_ID, objLlStockReport.getPeriodId());
                objPstLlStockReport.setLong(FLD_EMPLOYEE_ID, objLlStockReport.getEmployeeId());                
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE1, objLlStockReport.getQtyEnt1());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE2, objLlStockReport.getQtyEnt2());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE3, objLlStockReport.getQtyEnt3());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE4, objLlStockReport.getQtyEnt4());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE5, objLlStockReport.getQtyEnt5());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE6, objLlStockReport.getQtyEnt6());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE7, objLlStockReport.getQtyEnt7());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE8, objLlStockReport.getQtyEnt8());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE9, objLlStockReport.getQtyEnt9());
                objPstLlStockReport.setFloat(FLD_QTY_ENTITLE10, objLlStockReport.getQtyEnt10());
                objPstLlStockReport.setFloat(FLD_QTY_TOTAL_LL, objLlStockReport.getQtyLlTotal());
                objPstLlStockReport.setFloat(FLD_QTY_TAKEN_MTD, objLlStockReport.getQtyLlTakenMtd());
                objPstLlStockReport.setFloat(FLD_QTY_TAKEN_YTD, objLlStockReport.getQtyLlTakenYtd());
                
                objPstLlStockReport.update();
                return objLlStockReport.getOID();
                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLlStockReport(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstLlStockReport objPstLlStockReport = new PstLlStockReport(oid);
            objPstLlStockReport.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLlStockReport(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((LlStockReport)ent);
    }
    
    public static long insertExc(LlStockReport objLlStockReport) throws DBException {
        try {
            PstLlStockReport objPstLlStockReport = new PstLlStockReport(0);
            
            objPstLlStockReport.setLong(FLD_PERIODE_ID, objLlStockReport.getPeriodId());
            objPstLlStockReport.setLong(FLD_EMPLOYEE_ID, objLlStockReport.getEmployeeId());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE1, objLlStockReport.getQtyEnt1());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE2, objLlStockReport.getQtyEnt2());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE3, objLlStockReport.getQtyEnt3());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE4, objLlStockReport.getQtyEnt4());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE5, objLlStockReport.getQtyEnt5());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE6, objLlStockReport.getQtyEnt6());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE7, objLlStockReport.getQtyEnt7());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE8, objLlStockReport.getQtyEnt8());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE9, objLlStockReport.getQtyEnt9());
            objPstLlStockReport.setFloat(FLD_QTY_ENTITLE10, objLlStockReport.getQtyEnt10());            
            objPstLlStockReport.setFloat(FLD_QTY_TOTAL_LL, objLlStockReport.getQtyLlTotal());
            objPstLlStockReport.setFloat(FLD_QTY_TAKEN_MTD, objLlStockReport.getQtyLlTakenMtd());
            objPstLlStockReport.setFloat(FLD_QTY_TAKEN_YTD, objLlStockReport.getQtyLlTakenYtd());
            
            objPstLlStockReport.insert();
            objLlStockReport.setOID(objPstLlStockReport.getlong(FLD_LL_STOCK_REPORT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLlStockReport(0), DBException.UNKNOWN);
        }
        return objLlStockReport.getOID();
    }
    
    
    
    /**
     * @param rs
     * @param objLlStockReport
     */
    private static void resultToObject(ResultSet rs, LlStockReport objLlStockReport) {
        try 
        {
            objLlStockReport.setOID(rs.getLong(PstLlStockReport.fieldNames[PstLlStockReport.FLD_LL_STOCK_REPORT_ID]));
            objLlStockReport.setPeriodId(rs.getLong(PstLlStockReport.fieldNames[PstLlStockReport.FLD_PERIODE_ID]));
            objLlStockReport.setEmployeeId(rs.getLong(PstLlStockReport.fieldNames[PstLlStockReport.FLD_EMPLOYEE_ID]));
            objLlStockReport.setQtyEnt1(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE1]));            
            objLlStockReport.setQtyEnt2(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE2]));            
            objLlStockReport.setQtyEnt3(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE3]));            
            objLlStockReport.setQtyEnt4(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE4]));            
            objLlStockReport.setQtyEnt5(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE5]));            
            objLlStockReport.setQtyEnt6(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE6]));            
            objLlStockReport.setQtyEnt7(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE7]));            
            objLlStockReport.setQtyEnt8(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE8]));            
            objLlStockReport.setQtyEnt9(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE9]));            
            objLlStockReport.setQtyEnt10(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE10]));            
            objLlStockReport.setQtyLlTotal(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TOTAL_LL]));
            objLlStockReport.setQtyLlTakenMtd(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TAKEN_MTD]));
            objLlStockReport.setQtyLlTakenYtd(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TAKEN_YTD]));            
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
            String sql = "SELECT * FROM " + TBL_LL_STOCK_REPORT;
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
                LlStockReport alStockReport = new LlStockReport();
                resultToObject(rs, alStockReport);
                lists.add(alStockReport);
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
            String sql = "SELECT COUNT("+ PstLlStockReport.fieldNames[PstLlStockReport.FLD_LL_STOCK_REPORT_ID] + ") FROM " + TBL_LL_STOCK_REPORT;
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
    public static LlStockReport getLlStockReport(long periodId, long employeeId)
    {        
        LlStockReport objLlStockReport = new LlStockReport();
        String whereClause = PstLlStockReport.fieldNames[PstLlStockReport.FLD_PERIODE_ID] + 
                             " = " + periodId + 
                             " AND " + PstLlStockReport.fieldNames[PstLlStockReport.FLD_EMPLOYEE_ID] + 
                             " = " + employeeId;
        Vector vectLlStockReport = list(0, 0, whereClause, "");
        if(vectLlStockReport!=null && vectLlStockReport.size()>0)
        {
            objLlStockReport = (LlStockReport)vectLlStockReport.get(0);
        }        
        
        return objLlStockReport;
    }
    
    /**     
     * @param objLlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */        
    public static long storeLlStockReport(LlStockReport objLlStockReport, LlStockReport objLlStockReportNew)
    {    
        long oidLlStockReport = 0;
        
        // insert new record to LlStockReport
        if(objLlStockReport.getOID() == 0)
        {            
            try
            {
                objLlStockReport.setPeriodId(objLlStockReportNew.getPeriodId());
                objLlStockReport.setEmployeeId(objLlStockReportNew.getEmployeeId());
                objLlStockReport.setQtyEnt1(objLlStockReportNew.getQtyEnt1());
                objLlStockReport.setQtyEnt2(objLlStockReportNew.getQtyEnt2());
                objLlStockReport.setQtyEnt3(objLlStockReportNew.getQtyEnt3());
                objLlStockReport.setQtyEnt4(objLlStockReportNew.getQtyEnt4());
                objLlStockReport.setQtyEnt5(objLlStockReportNew.getQtyEnt5());
                objLlStockReport.setQtyEnt6(objLlStockReportNew.getQtyEnt6());
                objLlStockReport.setQtyEnt7(objLlStockReportNew.getQtyEnt7());
                objLlStockReport.setQtyEnt8(objLlStockReportNew.getQtyEnt8());
                objLlStockReport.setQtyEnt9(objLlStockReportNew.getQtyEnt9());
                objLlStockReport.setQtyEnt10(objLlStockReportNew.getQtyEnt10());
                objLlStockReport.setQtyLlTotal(objLlStockReportNew.getQtyLlTotal());
                objLlStockReport.setQtyLlTakenMtd(objLlStockReportNew.getQtyLlTakenMtd());
                objLlStockReport.setQtyLlTakenYtd(objLlStockReportNew.getQtyLlTakenYtd());
                
                oidLlStockReport = insertExc(objLlStockReport);
            }
            catch(Exception e)
            {
                System.out.println("Exc when storeLlStockReport - INSERT : " + e.toString());
            }
        }
        
        // update selected record LlStockReport
        else
        {
            try
            {                
                float intEnt1Old = objLlStockReport.getQtyEnt1();
                float intEnt2Old = objLlStockReport.getQtyEnt2();
                float intEnt3Old = objLlStockReport.getQtyEnt3();
                float intEnt4Old = objLlStockReport.getQtyEnt4();
                float intEnt5Old = objLlStockReport.getQtyEnt5();
                float intEnt6Old = objLlStockReport.getQtyEnt6();
                float intEnt7Old = objLlStockReport.getQtyEnt7();
                float intEnt8Old = objLlStockReport.getQtyEnt8();
                float intEnt9Old = objLlStockReport.getQtyEnt9();
                float intEnt10Old = objLlStockReport.getQtyEnt10();
                float intTotalOld = objLlStockReport.getQtyLlTotal();
                float intTakenMtdOld = objLlStockReport.getQtyLlTakenMtd();
                float intTakenYtdOld = objLlStockReport.getQtyLlTakenYtd();  
                
                objLlStockReport.setQtyEnt1(intEnt1Old + objLlStockReportNew.getQtyEnt1());
                objLlStockReport.setQtyEnt2(intEnt2Old + objLlStockReportNew.getQtyEnt2());
                objLlStockReport.setQtyEnt3(intEnt3Old + objLlStockReportNew.getQtyEnt3());
                objLlStockReport.setQtyEnt4(intEnt4Old + objLlStockReportNew.getQtyEnt4());
                objLlStockReport.setQtyEnt5(intEnt5Old + objLlStockReportNew.getQtyEnt5());
                objLlStockReport.setQtyEnt6(intEnt6Old + objLlStockReportNew.getQtyEnt6());
                objLlStockReport.setQtyEnt7(intEnt7Old + objLlStockReportNew.getQtyEnt7());
                objLlStockReport.setQtyEnt8(intEnt8Old + objLlStockReportNew.getQtyEnt8());
                objLlStockReport.setQtyEnt9(intEnt9Old + objLlStockReportNew.getQtyEnt9());
                objLlStockReport.setQtyEnt10(intEnt10Old + objLlStockReportNew.getQtyEnt10());
                objLlStockReport.setQtyLlTotal(intTotalOld + objLlStockReportNew.getQtyLlTotal());
                objLlStockReport.setQtyLlTakenMtd(intTakenMtdOld + objLlStockReportNew.getQtyLlTakenMtd());
                objLlStockReport.setQtyLlTakenYtd(intTakenYtdOld + objLlStockReportNew.getQtyLlTakenYtd());     
                
                oidLlStockReport = updateExc(objLlStockReport);                
            }
            catch(Exception e)
            {
                System.out.println("Exc when storeLlStockReport - UPDATE : " + e.toString());
            }            
        }                
        return oidLlStockReport;
    }
    

    /**     
     * @param objLlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */        
    public static long insertNewsLlStockReport(LlStockReport objLlStockReport)
    {    
        long oidLlStockReport = 0;        
        try
        {            
            oidLlStockReport = insertExc(objLlStockReport);
        }
        catch(Exception e)
        {
            System.out.println("Exc when insertNewsLlStockReport : " + e.toString());
        }
        return oidLlStockReport;
    }

    
    /**     
     * @param objLlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */        
    public static long updateExistingLlStockReport(LlStockReport objLlStockReport, LlStockReport objLlStockReportNew)
    {    
        long oidLlStockReport = 0;
        
        // update existing record to LlStockReport
        if(objLlStockReport.getOID() != 0)      
        {
            try
            {
                float intEnt1Old = objLlStockReport.getQtyEnt1();
                float intEnt2Old = objLlStockReport.getQtyEnt2();
                float intEnt3Old = objLlStockReport.getQtyEnt3();
                float intEnt4Old = objLlStockReport.getQtyEnt4();
                float intEnt5Old = objLlStockReport.getQtyEnt5();
                float intEnt6Old = objLlStockReport.getQtyEnt6();
                float intEnt7Old = objLlStockReport.getQtyEnt7();
                float intEnt8Old = objLlStockReport.getQtyEnt8();
                float intEnt9Old = objLlStockReport.getQtyEnt9();
                float intEnt10Old = objLlStockReport.getQtyEnt10();
                float intTotalOld = objLlStockReport.getQtyLlTotal();
                float intTakenMtdOld = objLlStockReport.getQtyLlTakenMtd();
                float intTakenYtdOld = objLlStockReport.getQtyLlTakenYtd();  
                
                objLlStockReport.setQtyEnt1(intEnt1Old + objLlStockReportNew.getQtyEnt1());
                objLlStockReport.setQtyEnt2(intEnt2Old + objLlStockReportNew.getQtyEnt2());
                objLlStockReport.setQtyEnt3(intEnt3Old + objLlStockReportNew.getQtyEnt3());
                objLlStockReport.setQtyEnt4(intEnt4Old + objLlStockReportNew.getQtyEnt4());
                objLlStockReport.setQtyEnt5(intEnt5Old + objLlStockReportNew.getQtyEnt5());
                objLlStockReport.setQtyEnt6(intEnt6Old + objLlStockReportNew.getQtyEnt6());
                objLlStockReport.setQtyEnt7(intEnt7Old + objLlStockReportNew.getQtyEnt7());
                objLlStockReport.setQtyEnt8(intEnt8Old + objLlStockReportNew.getQtyEnt8());
                objLlStockReport.setQtyEnt9(intEnt9Old + objLlStockReportNew.getQtyEnt9());
                objLlStockReport.setQtyEnt10(intEnt10Old + objLlStockReportNew.getQtyEnt10()); 
                objLlStockReport.setQtyLlTotal(intTotalOld + objLlStockReportNew.getQtyLlTotal());
                objLlStockReport.setQtyLlTakenMtd(intTakenMtdOld + objLlStockReportNew.getQtyLlTakenMtd());
                objLlStockReport.setQtyLlTakenYtd(intTakenYtdOld + objLlStockReportNew.getQtyLlTakenYtd());     
                
                oidLlStockReport = updateExc(objLlStockReport);                
            }
            catch(Exception e)
            {
                System.out.println("Exc when updateExistingLlStockReport : " + e.toString());
            }            
        }                
        return oidLlStockReport;
    }
    
    
    /**     
     * @param objLlStockReport
     * @param prevQty
     * @param mtdQty
     * @param usedQty
     * @param expiredQty
     */    
    /*
    public static int updateLlStockReportExpired(long periodId)
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
                System.out.println("sQL updateLlStockReportExpired : " + sql);             
                result = DBHandler.execUpdate(sql);                        
            }
            catch(Exception e) 
            {
                System.out.println("Exc updateLlStockReportExpired : " + e.toString());
            }
            finally 
            {            
                return result;
            }
        }
        
        return 0;

    }
    */
    
    /**
     * @param periodId
     * @created by Edhy
     */        
    public static void transferLastLlStockReport(long lastPeriodId, long periodId)
    {            
        String whereClause = PstLlStockReport.fieldNames[PstLlStockReport.FLD_PERIODE_ID] + " = " + lastPeriodId;
        Vector vectLlStockReport = list(0, 0, whereClause, "");
        if(vectLlStockReport!=null && vectLlStockReport.size()>0)
        {
            int maxLlStockReport = vectLlStockReport.size();
            for(int i=0; i<maxLlStockReport; i++)
            {
                LlStockReport objLlStockReport = (LlStockReport) vectLlStockReport.get(i);
                
                float ent1Qty = objLlStockReport.getQtyEnt1();
                float ent2Qty = objLlStockReport.getQtyEnt2();
                
                if(ent1Qty > 0)
                {
                    ent1Qty = ent1Qty - objLlStockReport.getQtyLlTakenYtd();
                }
                
                if(ent2Qty > 0)
                {
                    ent2Qty = ent2Qty - objLlStockReport.getQtyLlTakenYtd();
                }
                
                float totalQty = objLlStockReport.getQtyLlTotal();
                float takenMtdQty = objLlStockReport.getQtyLlTakenMtd();
                float takenYtdQty = objLlStockReport.getQtyLlTakenYtd();                  
                
                // set value to new 
                LlStockReport objLlStockReportWillInsert = getLlStockReport(periodId, objLlStockReport.getEmployeeId());
                if(objLlStockReportWillInsert.getOID() == 0)
                {
                    objLlStockReportWillInsert.setPeriodId(periodId);
                    objLlStockReportWillInsert.setEmployeeId(objLlStockReport.getEmployeeId());
                    objLlStockReportWillInsert.setQtyEnt1(ent1Qty);
                    objLlStockReportWillInsert.setQtyEnt2(ent2Qty);
                    objLlStockReportWillInsert.setQtyLlTotal(totalQty);
                    objLlStockReportWillInsert.setQtyLlTakenMtd(takenMtdQty);  
                    objLlStockReportWillInsert.setQtyLlTakenYtd(takenYtdQty);    

                    try
                    {
                        long oidLlStockReport = PstLlStockReport.insertExc(objLlStockReportWillInsert);
                    }
                    catch(Exception e)
                    {
                        System.out.println("transferLastLlStockReport if exc : " + e.toString());
                    }
                }
            }            
        }
        
        
        // if no LlStockReport available yet, insert new LlStockReport
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
                        LlStockReport objLlStockReport = new LlStockReport();

                        objLlStockReport.setPeriodId(periodId);
                        objLlStockReport.setEmployeeId(emp.getOID());
                        objLlStockReport.setQtyEnt1(0);
                        objLlStockReport.setQtyEnt2(0);
                        objLlStockReport.setQtyLlTotal(0);
                        objLlStockReport.setQtyLlTakenMtd(0);  
                        objLlStockReport.setQtyLlTakenYtd(0);    

                        try
                        {
                            long oidLlStockReport = PstLlStockReport.insertExc(objLlStockReport);
                        }
                        catch(Exception e)
                        {
                            System.out.println("transferLastLlStockReport else exc : " + e.toString());
                        }                                        
                    }
                }            
            }
        }        
    }    
    
}
