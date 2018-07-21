/*
 * PstLlStockExpired.java
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
import com.dimata.harisma.entity.attendance.*; 

/**
 *
 * @author  gedhy
 */
public class PstLlStockExpired extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_LL_STOCK_EXPIRED ="hr_ll_stock_expired";// "HR_LL_STOCK_EXPIRED";
    
    public static final  int FLD_LL_STOCK_EXPIRED_ID    = 0;
    public static final  int FLD_LL_STOCK_ID            = 1;    
    public static final  int FLD_EXPIRED_DATE           = 2;
    public static final  int FLD_EXPIRED_QTY            = 3;
    public static final  int FLD_EXPIRED_LL             = 4;
    public static final  int FLD_EXPIRED_LAST           = 5;
    
    public static final  String[] fieldNames = {
        "LL_STOCK_EXPIRED_ID",
        "LL_STOCK_ID",
        "EXPIRED_DATE",
        "EXPIRED_QTY",
        "EXPIRED_LL",
        "EXPIRED_LAST"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT
    };
    
    /*status Expired LL */
    public static final int EXPIRED_LL_I  = 1;
    public static final int EXPIRED_LL_II = 2;
    
    /*value untuk expired last */
    public static final int LAST_FALSE  = 0;
    public static final int LAST_TRUE   = 1;
    
    public PstLlStockExpired(){
    }
    
    public PstLlStockExpired(int i) throws DBException {
        super(new PstLlStockExpired());
    }
    
    public PstLlStockExpired(String sOid) throws DBException {
        super(new PstLlStockExpired(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstLlStockExpired(long lOid) throws DBException {
        super(new PstLlStockExpired(0));
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
        return TBL_HR_LL_STOCK_EXPIRED;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstLlStockExpired().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        LlStockExpired llStockExpired = fetchExc(ent.getOID());
        ent = (Entity)llStockExpired;
        return llStockExpired.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((LlStockExpired) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((LlStockExpired) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static LlStockExpired fetchExc(long oid) throws DBException{
        try{
            LlStockExpired llStockExpired = new LlStockExpired();
            PstLlStockExpired pstLlStockExpired = new PstLlStockExpired(oid);
            llStockExpired.setOID(oid);
            
            llStockExpired.setLlStockId(pstLlStockExpired.getlong(FLD_LL_STOCK_ID));
            llStockExpired.setExpiredDate(pstLlStockExpired.getDate(FLD_EXPIRED_DATE));
            llStockExpired.setExpiredQty(pstLlStockExpired.getfloat(FLD_EXPIRED_QTY));
            llStockExpired.setExpiredLL(pstLlStockExpired.getfloat(FLD_EXPIRED_LL));
            llStockExpired.setExpiredLast(pstLlStockExpired.getfloat(FLD_EXPIRED_LAST));
            
            return llStockExpired;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockExpired(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(LlStockExpired llStockExpired) throws DBException{
        try{
            PstLlStockExpired pstLlStockExpired = new PstLlStockExpired(0);
            
            pstLlStockExpired.setLong(FLD_LL_STOCK_ID, llStockExpired.getLlStockId());
            pstLlStockExpired.setDate(FLD_EXPIRED_DATE, llStockExpired.getExpiredDate());
            pstLlStockExpired.setFloat(FLD_EXPIRED_QTY, llStockExpired.getExpiredQty());
            pstLlStockExpired.setFloat(FLD_EXPIRED_LL, llStockExpired.getExpiredLL());
            pstLlStockExpired.setFloat(FLD_EXPIRED_LAST, llStockExpired.getExpiredLast());
            
            pstLlStockExpired.insert();
            llStockExpired.setOID(pstLlStockExpired.getlong(FLD_LL_STOCK_EXPIRED_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockExpired(0),DBException.UNKNOWN);
        }
        return llStockExpired.getOID();
    }
    
    public static long updateExc(LlStockExpired llStockExpired) throws DBException{
        try{
            if(llStockExpired.getOID() != 0){
                PstLlStockExpired pstLlStockExpired = new PstLlStockExpired(llStockExpired.getOID());
                
                pstLlStockExpired.setLong(FLD_LL_STOCK_ID, llStockExpired.getLlStockId());
                pstLlStockExpired.setDate(FLD_EXPIRED_DATE, llStockExpired.getExpiredDate());
                pstLlStockExpired.setFloat(FLD_EXPIRED_QTY, llStockExpired.getExpiredQty());
                pstLlStockExpired.setFloat(FLD_EXPIRED_LL, llStockExpired.getExpiredLL());
                pstLlStockExpired.setFloat(FLD_EXPIRED_LAST, llStockExpired.getExpiredLast());
                
                pstLlStockExpired.update();
                return llStockExpired.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockExpired(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstLlStockExpired pstLlStockExpired = new PstLlStockExpired(oid);
            pstLlStockExpired.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstLlStockExpired(0),DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_LL_STOCK_EXPIRED;
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
                LlStockExpired llStockExpired = new LlStockExpired();
                resultToObject(rs, llStockExpired);
                lists.add(llStockExpired);
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
    
    public static void resultToObject(ResultSet rs, LlStockExpired llStockExpired){
        try{
            llStockExpired.setOID(rs.getLong(PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_LL_STOCK_EXPIRED_ID]));
            llStockExpired.setLlStockId(rs.getLong(PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_LL_STOCK_ID]));
            llStockExpired.setExpiredDate(rs.getDate(PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_EXPIRED_DATE]));
            llStockExpired.setExpiredQty(rs.getFloat(PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_EXPIRED_QTY]));            
            llStockExpired.setExpiredLL(rs.getFloat(PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_EXPIRED_LL])); 
            llStockExpired.setExpiredLast(rs.getFloat(PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_EXPIRED_LAST])); 
            
        }catch(Exception e){ 
            System.out.println("EXCEPTION :: "+e.toString());
        }
    }
    
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_LL_STOCK_EXPIRED_ID] + ") FROM " + TBL_HR_LL_STOCK_EXPIRED;
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
                    LlStockExpired llStockExpired = (LlStockExpired)list.get(ls);
                    if(oid == llStockExpired.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    public static void deleteByLlStock(long lLlStockOid)
    {
        DBResultSet dbrs = null;
        try
        {
            String sql = "DELETE FROM "+PstLlStockExpired.TBL_HR_LL_STOCK_EXPIRED+
            " WHERE "+PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_LL_STOCK_ID]+
            " = "+lLlStockOid;

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
