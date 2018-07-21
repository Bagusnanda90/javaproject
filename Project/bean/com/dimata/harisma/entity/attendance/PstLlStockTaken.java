/*
 * PstLlStockTaken.java
 *
 * Created on December 22, 2004, 6:25 PM
 */

package com.dimata.harisma.entity.attendance;

// package core java
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

// package qdep
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

// package harisma
import com.dimata.harisma.entity.employee.*; 
import com.dimata.harisma.entity.attendance.*;

import com.dimata.util.Formater;
import com.dimata.util.DateCalc;
        
/**
 *
 * @author  gedhy
 */
public class PstLlStockTaken extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_LL_STOCK_TAKEN = "hr_ll_stock_taken";//"HR_LL_STOCK_TAKEN";
    
    public static final  int FLD_LL_STOCK_TAKEN_ID = 0;
    public static final  int FLD_EMPLOYEE_ID = 1; 
    public static final  int FLD_LL_STOCK_ID = 2;    
    public static final  int FLD_TAKEN_DATE = 3;
    public static final  int FLD_TAKEN_QTY = 4;
    public static final  int FLD_PAID_DATE = 5;
    public static final  int FLD_TAKEN_FROM_STATUS = 6;
    public static final  int FLD_LEAVE_APPLICATION_ID = 7;
    public static final  int FLD_TAKEN_FINNISH_DATE = 8;    
    
    public static final  String[] fieldNames = {
        "LL_STOCK_TAKEN_ID",
        "EMPLOYEE_ID",
        "LL_STOCK_ID",
        "TAKEN_DATE",
        "TAKEN_QTY",
        "PAID_DATE",
        "TAKEN_FROM_STATUS",
        "LEAVE_APPLICATION_ID",
        "TAKEN_FINNISH_DATE",
        
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE
    };
    
    public static final int TAKEN_FROM_STATUS_USER = 0;
    public static final int TAKEN_FROM_STATUS_SYSTEM = 1;
    
    public PstLlStockTaken(){
    }
    
    public PstLlStockTaken(int i) throws DBException {
        super(new PstLlStockTaken());
    }
    
    public PstLlStockTaken(String sOid) throws DBException {
        super(new PstLlStockTaken(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstLlStockTaken(long lOid) throws DBException {
        super(new PstLlStockTaken(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        }catch(Exception e) {
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public int getFieldSize(){
        return fieldNames.length;
    }
    
    public String getTableName(){
        return TBL_HR_LL_STOCK_TAKEN;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstLlStockTaken().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        LlStockTaken llStockTaken = fetchExc(ent.getOID());
        ent = (Entity)llStockTaken;
        return llStockTaken.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((LlStockTaken) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((LlStockTaken) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static LlStockTaken fetchExc(long oid) throws DBException{
        try{
            LlStockTaken llStockTaken = new LlStockTaken();
            PstLlStockTaken pstLlStockTaken = new PstLlStockTaken(oid);
            llStockTaken.setOID(oid);
            
            llStockTaken.setEmployeeId(pstLlStockTaken.getlong(FLD_EMPLOYEE_ID));
            llStockTaken.setLlStockId(pstLlStockTaken.getlong(FLD_LL_STOCK_ID));
            llStockTaken.setTakenDate(pstLlStockTaken.getDate(FLD_TAKEN_DATE));
            llStockTaken.setTakenQty(pstLlStockTaken.getfloat(FLD_TAKEN_QTY));
            llStockTaken.setPaidDate(pstLlStockTaken.getDate(FLD_PAID_DATE));
            llStockTaken.setTakenFromStatus(pstLlStockTaken.getInt(FLD_TAKEN_FROM_STATUS));
            llStockTaken.setLlStockId(pstLlStockTaken.getlong(FLD_LEAVE_APPLICATION_ID));
            llStockTaken.setTakenFinnishDate(pstLlStockTaken.getDate(FLD_TAKEN_FINNISH_DATE));
            
            return llStockTaken;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockTaken(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(LlStockTaken llStockTaken) throws DBException{
        try{
            PstLlStockTaken pstLlStockTaken = new PstLlStockTaken(0);
            
            pstLlStockTaken.setLong(FLD_EMPLOYEE_ID, llStockTaken.getEmployeeId());
            pstLlStockTaken.setLong(FLD_LL_STOCK_ID, llStockTaken.getLlStockId());
            pstLlStockTaken.setDate(FLD_TAKEN_DATE, llStockTaken.getTakenDate());
            pstLlStockTaken.setFloat(FLD_TAKEN_QTY, llStockTaken.getTakenQty());
            pstLlStockTaken.setDate(FLD_PAID_DATE, llStockTaken.getPaidDate());
            pstLlStockTaken.setInt(FLD_TAKEN_FROM_STATUS, llStockTaken.getTakenFromStatus());
            pstLlStockTaken.setLong(FLD_LEAVE_APPLICATION_ID, llStockTaken.getLeaveApplicationId());
            pstLlStockTaken.setDate(FLD_TAKEN_FINNISH_DATE, llStockTaken.getTakenFinnishDate());
            
            pstLlStockTaken.insert();
            llStockTaken.setOID(pstLlStockTaken.getlong(FLD_LL_STOCK_TAKEN_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockTaken(0),DBException.UNKNOWN);
        }
        return llStockTaken.getOID();
    }
    
    public static long updateExc(LlStockTaken llStockTaken) throws DBException{
        try{
            if(llStockTaken.getOID() != 0){
                PstLlStockTaken pstLlStockTaken = new PstLlStockTaken(llStockTaken.getOID());
                
                pstLlStockTaken.setLong(FLD_EMPLOYEE_ID, llStockTaken.getEmployeeId());
                pstLlStockTaken.setLong(FLD_LL_STOCK_ID, llStockTaken.getLlStockId());
                pstLlStockTaken.setDate(FLD_TAKEN_DATE, llStockTaken.getTakenDate());
                pstLlStockTaken.setFloat(FLD_TAKEN_QTY, llStockTaken.getTakenQty());
                pstLlStockTaken.setDate(FLD_PAID_DATE, llStockTaken.getPaidDate());
                pstLlStockTaken.setInt(FLD_TAKEN_FROM_STATUS, llStockTaken.getTakenFromStatus());
                pstLlStockTaken.setLong(FLD_LEAVE_APPLICATION_ID, llStockTaken.getLeaveApplicationId());
                pstLlStockTaken.setDate(FLD_TAKEN_FINNISH_DATE, llStockTaken.getTakenFinnishDate());
                
                pstLlStockTaken.update();
                return llStockTaken.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockTaken(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstLlStockTaken pstLlStockTaken = new PstLlStockTaken(oid);
            pstLlStockTaken.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockTaken(0),DBException.UNKNOWN);
        }
        return oid;
    }

    public static int deleteByLeaveAppId(long leaveAppOid) throws Exception{
        try {
            String where =" "+ fieldNames[FLD_LEAVE_APPLICATION_ID]+" = "+leaveAppOid;
            PstLlStockTaken pst =  new PstLlStockTaken(0);
            pst.deleteRecords(0, where);
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockTaken(0), DBException.UNKNOWN);
        }
        return 0;
    }

    
    public static Vector listAll(){
        return list(0, 500, "","");
    }
    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LL_STOCK_TAKEN;
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
                LlStockTaken llStockTaken = new LlStockTaken();
                resultToObject(rs, llStockTaken);
                lists.add(llStockTaken);
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
    
    public static Vector listV(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LL_STOCK_TAKEN;
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
                LlStockTaken llStockTaken = new LlStockTaken();
                resultToObject(rs, llStockTaken);
                lists.add(llStockTaken);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }
    
    public static void resultToObject(ResultSet rs, LlStockTaken llStockTaken){
        try{
            llStockTaken.setOID(rs.getLong(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID]));
            llStockTaken.setEmployeeId(rs.getLong(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]));
            llStockTaken.setLlStockId(rs.getLong(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]));
            llStockTaken.setTakenDate(rs.getDate(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]));
            llStockTaken.setTakenQty(rs.getFloat(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY]));   
            llStockTaken.setPaidDate(rs.getDate(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_PAID_DATE]));
            llStockTaken.setTakenFromStatus(rs.getInt(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FROM_STATUS]));
            llStockTaken.setLlStockId(rs.getLong(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]));
            llStockTaken.setTakenFinnishDate(rs.getDate(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]));
            llStockTaken.setLeaveApplicationId(rs.getLong(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]));
            
        }catch(Exception e){ }
    }
    
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + ") FROM " + TBL_HR_LL_STOCK_TAKEN;
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
                    LlStockTaken llStockTaken = (LlStockTaken)list.get(ls);
                    if(oid == llStockTaken.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    /**
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */    
    public static Vector getLlPayable(long employeeId)  
    {  
        Vector result = new Vector(1,1);        
        DBResultSet dbrs = null;        
        String stSQL = "SELECT " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] +
                       ", " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID] +
                       ", " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID] +
                       ", " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] +
                       ", " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + 
                       ", " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_PAID_DATE] +
                       " FROM " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN +
                       " WHERE " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID] +
                       " = 0" + 
                       " ORDER BY " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE];
        try
        {
//            System.out.println("SQL getLlPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                LlStockTaken objLlStockTaken = new LlStockTaken();
                objLlStockTaken.setOID(rs.getLong(1));
                objLlStockTaken.setEmployeeId(rs.getLong(2));
                objLlStockTaken.setLlStockId(rs.getInt(3));
                objLlStockTaken.setTakenDate(rs.getDate(4));
                objLlStockTaken.setTakenQty(rs.getFloat(5));                
                objLlStockTaken.setPaidDate(rs.getDate(6));
                
                result.add(objLlStockTaken);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getLlPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }        
    
    /**
     * keteranagn list pencarian LL yg masih minus
     * /n/ create by satrya 2013-09-27
     * @param employeeId
     * @return 
     */
    public static LLStockManagement getLLPaidStockManagement(long employeeId){  
        //Vector result = new Vector(1,1);  
    LLStockManagement llStockManagement = new LLStockManagement();
        if(employeeId==0){
            return llStockManagement;
        }
        DBResultSet dbrs = null;        
        String stSQL = 
               " SELECT LL_MAN.* FROM "+PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL_TAKEN "
+ " INNER JOIN "+PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL_MAN ON LL_TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]
+ " =LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]
+ " WHERE (LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]+">0 AND  LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]
+ " >= LL_TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY]+") AND LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]+"!=0"
+ " AND LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+"="+PstLLStockManagement.LL_STS_AKTIF;
        try
        {
//            System.out.println("SQL getLlPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                llStockManagement = new LLStockManagement();
                PstLLStockManagement.resultToObject(rs, llStockManagement);
                
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getLlPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return llStockManagement;
        }        
    }
   
    /**
     * keteranagan LL unpaid
     * create by satrya 2013-09-27
     * @param employeeId
     * @return 
     */
    public static Vector getLlUnpaid(long employeeId){  
        Vector result = new Vector(1,1);       
        if(employeeId==0){
            return result;
        }
        DBResultSet dbrs = null;        
        String stSQL = 
               " SELECT LL_TAKEN.* FROM "+PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL_TAKEN "
+ " INNER JOIN "+PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL_MAN ON LL_TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]
+ " =LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]
+ " WHERE (LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]
        +"<0 OR LL_TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + ">= LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]+")" 
+ " AND LL_MAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]+"!=0"
+ " AND LL_TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]+"="+employeeId
+ " ORDER BY " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE];
        try
        {
//            System.out.println("SQL getLlPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                LlStockTaken llStockTaken = new LlStockTaken();
                llStockTaken.setOID(rs.getLong(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID]));
                llStockTaken.setEmployeeId(rs.getLong(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]));
                llStockTaken.setLlStockId(rs.getInt(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]));
                llStockTaken.setTakenDate(rs.getDate(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]));
                llStockTaken.setTakenQty(rs.getFloat(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY]));                
                llStockTaken.setPaidDate(rs.getDate(PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_PAID_DATE]));
                
                result.add(llStockTaken);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getLlPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }
    
    /**
     * @param llStockOid
     * @param employeeId
     * @param takenDate
     * @return
     */    
    public static boolean existLlStockTaken(long llStockOid, long employeeId, Date takenDate)
    {
        boolean result = false;        
        DBResultSet dbrs = null;    
        String strTakenDate = "\"" + com.dimata.util.Formater.formatDate(takenDate, "yyyy-MM-dd") + "\"";
        String stSQL = "SELECT " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] +
                       " FROM " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN +
                       " WHERE " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID] +
                       " = " + llStockOid + 
                       " AND " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] +
                       " = " + strTakenDate;
        try
        {
//            System.out.println("SQL existLlStockTaken : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                result = true;
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when existLlStockTaken : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }                        
    };        
    
    
    /**     
     * @param employeeId
     * @param takenDate
     * @return
     */    
    public static boolean existLlStockTaken(long employeeId, Date takenDate)
    {
        boolean result = false;        
        DBResultSet dbrs = null;    
        String strTakenDate = "\"" + com.dimata.util.Formater.formatDate(takenDate, "yyyy-MM-dd") + "\"";
        String stSQL = "SELECT " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] +
                       " FROM " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN +
                       " WHERE " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] +
                       " = " + strTakenDate;
        try
        {
//            System.out.println("SQL existLlStockTaken : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                result = true;
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when existLlStockTaken : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }                        
    };        
 
    /**
     * @param lDepartmentOid
     * @created by Edhy     
     */
    public void deleteLlStockTakenPerDepartment(long lDepartmentOid) 
    {                     
        DBResultSet dbrs = null;
        String stSQL = " SELECT LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] +                               
                       " FROM "+ TBL_HR_LL_STOCK_TAKEN + " AS LL" +
                       " INNER JOIN "+ PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                       " ON LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID] +
                       " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                       
                       " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                       " = " + lDepartmentOid;
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                long lLlStockOid = rs.getLong(1);                
                
                // delete Ll stock
                try
                {
                    long oidLlStock = PstLlStockTaken.deleteExc(lLlStockOid);
                }
                catch(Exception e)
                {
                    System.out.println("Exc : " + e.toString());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Exc : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
        } 
    }
    
     /* this method used to get sum of leave per year
     * @param :whereClause
     * created By Artha
     **/
     public static int getSumLeave(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM("+ fieldNames[FLD_TAKEN_QTY] 
                    + ") FROM " + TBL_HR_LL_STOCK_TAKEN;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
//            System.out.println("sql getSum  "+sql);
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
 
     /**
     * Keterangan: untuk mencari taken DP
     * create by satrya 2014-01-06
     * @param leaveApplicationId
     * @param employeeId
     * @return 
     */
    public static Vector getLlTaken(long leaveApplicationId,long employeeId) {
        Vector result = new Vector(1, 1);
        if(leaveApplicationId==0 || employeeId==0){
            return result;
        }
        DBResultSet dbrs = null;
        String stSQL = "SELECT * "
                + " FROM " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN
                + " WHERE " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
                + " = "+leaveApplicationId
                + " ORDER BY " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE];
        try {

            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LlStockTaken objLlStockTaken = new LlStockTaken();
                resultToObject(rs, objLlStockTaken);
                result.add(objLlStockTaken);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when getAlPayable : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
   /**
     * menghitung Al quantity ( dalam unit workday ( 8 jam kerja ) dari seorang karyawan pada rentang periode waktu tertentu
     * @param employeeId
     * @param startDate
     * @param endDate
     * @return 
     */
    public static float getLlQty(long employeeId, Date startDate, Date endDate) {
        float qty = 0.0f;
        String where = fieldNames[FLD_EMPLOYEE_ID] + "=\""+ employeeId + "\" AND  (" + fieldNames[FLD_TAKEN_DATE] + " BETWEEN \""
                + Formater.formatDate(startDate, "yyyy-MM-dd ") + "00:00:00\" AND \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "23:59:59\") OR "
                + " (" + fieldNames[FLD_TAKEN_FINNISH_DATE] + " BETWEEN \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "00:00:00\" AND \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "23:59:59\")";

        Vector list = list(0, 100, where, fieldNames[FLD_TAKEN_DATE]);
        if (list != null && list.size() > 0) {
            for (int idx = 0; idx < list.size(); idx++) {
                LlStockTaken llStockTaken = (LlStockTaken) list.get(idx);
                if ((DateCalc.dayDifference(llStockTaken.getTakenDate(), startDate) >= 0)
                        && (DateCalc.dayDifference(llStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                    // taken date and taken finish di dalam start and end date
                    if (llStockTaken.getTakenQty() == (float) Math.floor(llStockTaken.getTakenQty())) {
                        // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                        qty = qty + DateCalc.dayDifference(startDate, endDate)+1;
                    } else {
                        // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                        qty = qty + DateCalc.dayDifference(startDate, endDate);
                        qty = qty + llStockTaken.getTakenQty() - (float) Math.floor(llStockTaken.getTakenQty());
                    }
                } else {
                    if ((DateCalc.dayDifference(llStockTaken.getTakenDate(), startDate) >= 0)
                            && (DateCalc.dayDifference(llStockTaken.getTakenFinnishDate(), endDate) > 0)) {
                        // taken date and taken finish di dalam start and end date
                        if (llStockTaken.getTakenQty() == (float) Math.floor(llStockTaken.getTakenQty())) {
                            // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                            qty = qty + DateCalc.dayDifference(startDate, llStockTaken.getTakenFinnishDate())+1;
                        } else {
                            // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                            qty = qty + DateCalc.dayDifference(startDate, llStockTaken.getTakenFinnishDate());
                            qty = qty + llStockTaken.getTakenQty() - (float) Math.floor(llStockTaken.getTakenQty());
                        }
                    } else {
                        if ((DateCalc.dayDifference(llStockTaken.getTakenDate(), startDate) < 0)
                                && (DateCalc.dayDifference(llStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                            // taken date and taken finish di dalam start and end date
                            if (llStockTaken.getTakenQty() == (float) Math.floor(llStockTaken.getTakenQty())) {
                                // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                                qty = qty + DateCalc.dayDifference(llStockTaken.getTakenDate(), endDate)+1;
                            } else {
                                // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                                qty = qty + DateCalc.dayDifference(llStockTaken.getTakenDate(), endDate);
                                qty = qty + llStockTaken.getTakenQty() - (float) Math.floor(llStockTaken.getTakenQty());
                            }
                        } else {
                            // startdate  and enddate  beyond takendate and takenfinishdate
                            qty = qty + llStockTaken.getTakenQty();                            
                        }
                    }
                }
            }
        }
        return qty;
    }     
     
     
}
