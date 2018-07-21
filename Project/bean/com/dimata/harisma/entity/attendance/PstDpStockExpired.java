/*
 * PstDpStockExpired.java
 *
 * Created on December 22, 2004, 12:01 PM
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
import com.dimata.harisma.entity.attendance.*; 

/**
 *
 * @author  gedhy
 */
public class PstDpStockExpired extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_DP_STOCK_EXPIRED = "hr_dp_stock_expired";//HR_DP_STOCK_EXPIRED";
    
    public static final  int FLD_DP_STOCK_EXPIRED_ID = 0;
    public static final  int FLD_DP_STOCK_ID = 1;    
    public static final  int FLD_EXPIRED_DATE = 2;
    public static final  int FLD_EXPIRED_QTY = 3;


    
    public static final  String[] fieldNames = {
        "DP_STOCK_EXPIRED_ID",
        "DP_STOCK_ID",
        "EXPIRED_DATE",
        "EXPIRED_QTY"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT
    };
    
    public PstDpStockExpired(){
    }
    
    public PstDpStockExpired(int i) throws DBException {
        super(new PstDpStockExpired());
    }
    
    public PstDpStockExpired(String sOid) throws DBException {
        super(new PstDpStockExpired(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDpStockExpired(long lOid) throws DBException {
        super(new PstDpStockExpired(0));
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
        return TBL_HR_DP_STOCK_EXPIRED;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstDpStockExpired().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        DpStockExpired dpStockExpired = fetchExc(ent.getOID());
        ent = (Entity)dpStockExpired;
        return dpStockExpired.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((DpStockExpired) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((DpStockExpired) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static DpStockExpired fetchExc(long oid) throws DBException{
        try{
            DpStockExpired dpStockExpired = new DpStockExpired();
            PstDpStockExpired pstDpStockExpired = new PstDpStockExpired(oid);
            dpStockExpired.setOID(oid);
            
            dpStockExpired.setDpStockId(pstDpStockExpired.getlong(FLD_DP_STOCK_ID));
            dpStockExpired.setExpiredDate(pstDpStockExpired.getDate(FLD_EXPIRED_DATE));
            dpStockExpired.setExpiredQty(pstDpStockExpired.getInt(FLD_EXPIRED_QTY));
            
            return dpStockExpired;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockExpired(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(DpStockExpired dpStockExpired) throws DBException{
        try{
            PstDpStockExpired pstDpStockExpired = new PstDpStockExpired(0);
            
            pstDpStockExpired.setLong(FLD_DP_STOCK_ID, dpStockExpired.getDpStockId());
            pstDpStockExpired.setDate(FLD_EXPIRED_DATE, dpStockExpired.getExpiredDate());
            pstDpStockExpired.setFloat(FLD_EXPIRED_QTY, dpStockExpired.getExpiredQty());
            
            pstDpStockExpired.insert();
            dpStockExpired.setOID(pstDpStockExpired.getlong(FLD_DP_STOCK_EXPIRED_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockExpired(0),DBException.UNKNOWN);
        }
        return dpStockExpired.getOID();
    }
    
    public static long updateExc(DpStockExpired dpStockExpired) throws DBException{
        try{
            if(dpStockExpired.getOID() != 0){
                PstDpStockExpired pstDpStockExpired = new PstDpStockExpired(dpStockExpired.getOID());
                
                pstDpStockExpired.setLong(FLD_DP_STOCK_ID, dpStockExpired.getDpStockId());
                pstDpStockExpired.setDate(FLD_EXPIRED_DATE, dpStockExpired.getExpiredDate());
                pstDpStockExpired.setFloat(FLD_EXPIRED_QTY, dpStockExpired.getExpiredQty());
                
                pstDpStockExpired.update();
                return dpStockExpired.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockExpired(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstDpStockExpired pstDpStockExpired = new PstDpStockExpired(oid);
            pstDpStockExpired.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockExpired(0),DBException.UNKNOWN);
        }
        return oid;
    }
    
    public static Vector listAll(){
        return list(0, 500, "","");  
    }
    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DP_STOCK_EXPIRED;
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
                DpStockExpired dpStockExpired = new DpStockExpired();
                resultToObject(rs, dpStockExpired);
                lists.add(dpStockExpired);
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
    
    public static void resultToObject(ResultSet rs, DpStockExpired dpStockExpired){
        try{
            dpStockExpired.setOID(rs.getLong(PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_EXPIRED_ID]));
            dpStockExpired.setDpStockId(rs.getLong(PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID]));
            dpStockExpired.setExpiredDate(rs.getDate(PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_DATE]));
            dpStockExpired.setExpiredQty(rs.getInt(PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_QTY]));            
        }catch(Exception e){ }
    }
    
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_EXPIRED_ID] + ") FROM " + TBL_HR_DP_STOCK_EXPIRED;
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
                    DpStockExpired dpStockExpired = (DpStockExpired)list.get(ls);
                    if(oid == dpStockExpired.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    
    public static void deleteByDpStock(long lDpStockOid)
    {
        DBResultSet dbrs = null;
        try
        {
            String sql = "DELETE FROM "+PstDpStockExpired.TBL_HR_DP_STOCK_EXPIRED+
            " WHERE "+PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID]+
            " = "+lDpStockOid;

            int status = DBHandler.execUpdate(sql);
        }
        catch(Exception e)
        {
            System.out.println("Exception e : "+e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
        }
    }
    
}
