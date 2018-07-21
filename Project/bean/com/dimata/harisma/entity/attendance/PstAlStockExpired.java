/*
 * PstAlStockExpired.java
 *
 * Created on December 22, 2004, 4:47 PM
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
public class PstAlStockExpired extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_AL_STOCK_EXPIRED = "hr_al_stock_expired";//HR_AL_STOCK_EXPIRED";
    
    public static final  int FLD_AL_STOCK_EXPIRED_ID = 0;
    public static final  int FLD_AL_STOCK_ID = 1;    
    public static final  int FLD_EXPIRED_DATE = 2;
    public static final  int FLD_EXPIRED_QTY = 3;
    public static final  int FLD_DECISION_DATE = 4;
    public static final  int FLD_NOTE = 5;
    public static final  int FLD_EXPIRED_BY_PIC = 6;
    public static final  int FLD_APPROVE_BY_PIC = 7;
    
    public static final  String[] fieldNames = {
        "AL_STOCK_EXPIRED_ID",
        "AL_STOCK_ID",
        "EXPIRED_DATE",
        "EXPIRED_QTY",
        "DECISION_DATE",
        "NOTE",
        "EXPIRED_BY_PIC",
        "APPROVE_BY_PIC"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT
    };
    
    public PstAlStockExpired(){
    }
    
    public PstAlStockExpired(int i) throws DBException {
        super(new PstAlStockExpired());
    }
    
    public PstAlStockExpired(String sOid) throws DBException {
        super(new PstAlStockExpired(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstAlStockExpired(long lOid) throws DBException {
        super(new PstAlStockExpired(0));
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
        return TBL_HR_AL_STOCK_EXPIRED;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstAlStockExpired().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        AlStockExpired alStockExpired = fetchExc(ent.getOID());
        ent = (Entity)alStockExpired;
        return alStockExpired.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((AlStockExpired) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((AlStockExpired) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static AlStockExpired fetchExc(long oid) throws DBException{
        try{
            AlStockExpired alStockExpired = new AlStockExpired();
            PstAlStockExpired pstAlStockExpired = new PstAlStockExpired(oid);
            alStockExpired.setOID(oid);
            
            alStockExpired.setAlStockId(pstAlStockExpired.getlong(FLD_AL_STOCK_ID));
            alStockExpired.setExpiredDate(pstAlStockExpired.getDate(FLD_EXPIRED_DATE));
            alStockExpired.setExpiredQty(pstAlStockExpired.getInt(FLD_EXPIRED_QTY));
            alStockExpired.setDecisionDate(pstAlStockExpired.getDate(FLD_DECISION_DATE));
            alStockExpired.setNote(pstAlStockExpired.getString(FLD_NOTE));
            alStockExpired.setExpiredByPic(pstAlStockExpired.getString(FLD_EXPIRED_BY_PIC));
            alStockExpired.setApproveByPic(pstAlStockExpired.getString(FLD_APPROVE_BY_PIC));            
            
            return alStockExpired;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstAlStockExpired(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(AlStockExpired alStockExpired) throws DBException{
        try{
            PstAlStockExpired pstAlStockExpired = new PstAlStockExpired(0);
            
            pstAlStockExpired.setLong(FLD_AL_STOCK_ID, alStockExpired.getAlStockId());
            pstAlStockExpired.setDate(FLD_EXPIRED_DATE, alStockExpired.getExpiredDate());
            pstAlStockExpired.setFloat(FLD_EXPIRED_QTY, alStockExpired.getExpiredQty());
            pstAlStockExpired.setDate(FLD_DECISION_DATE, alStockExpired.getDecisionDate());
            pstAlStockExpired.setString(FLD_NOTE,alStockExpired.getNote());
            pstAlStockExpired.setString(FLD_EXPIRED_BY_PIC,alStockExpired.getExpiredByPic());
            pstAlStockExpired.setString(FLD_APPROVE_BY_PIC,alStockExpired.getApproveByPic());            
            
            pstAlStockExpired.insert();
            alStockExpired.setOID(pstAlStockExpired.getlong(FLD_AL_STOCK_EXPIRED_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstAlStockExpired(0),DBException.UNKNOWN);
        }
        return alStockExpired.getOID();
    }
    
    public static long updateExc(AlStockExpired alStockExpired) throws DBException{
        try{
            if(alStockExpired.getOID() != 0){
                PstAlStockExpired pstAlStockExpired = new PstAlStockExpired(alStockExpired.getOID());
                
                pstAlStockExpired.setLong(FLD_AL_STOCK_ID, alStockExpired.getAlStockId());
                pstAlStockExpired.setDate(FLD_EXPIRED_DATE, alStockExpired.getExpiredDate());
                pstAlStockExpired.setFloat(FLD_EXPIRED_QTY, alStockExpired.getExpiredQty());
                pstAlStockExpired.setDate(FLD_DECISION_DATE, alStockExpired.getDecisionDate());
                pstAlStockExpired.setString(FLD_NOTE, alStockExpired.getNote());
                pstAlStockExpired.setString(FLD_EXPIRED_BY_PIC, alStockExpired.getExpiredByPic());
                pstAlStockExpired.setString(FLD_APPROVE_BY_PIC, alStockExpired.getApproveByPic());
                
                pstAlStockExpired.update();
                return alStockExpired.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstAlStockExpired(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstAlStockExpired pstAlStockExpired = new PstAlStockExpired(oid);
            pstAlStockExpired.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstAlStockExpired(0),DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_AL_STOCK_EXPIRED;
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
                AlStockExpired alStockExpired = new AlStockExpired();
                resultToObject(rs, alStockExpired);
                lists.add(alStockExpired);
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
    
    public static void resultToObject(ResultSet rs, AlStockExpired alStockExpired){
        try{
            alStockExpired.setOID(rs.getLong(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_EXPIRED_ID]));
            alStockExpired.setAlStockId(rs.getLong(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_ID]));
            alStockExpired.setExpiredDate(rs.getDate(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_DATE]));
            alStockExpired.setExpiredQty(rs.getInt(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_QTY]));  
            alStockExpired.setDecisionDate(rs.getDate(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_DECISION_DATE]));
            alStockExpired.setNote(rs.getString(rs.getString(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_NOTE])));
            alStockExpired.setExpiredByPic(rs.getString(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_BY_PIC]));
            alStockExpired.setApproveByPic(rs.getString(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_APPROVE_BY_PIC]));
            
        }catch(Exception e){ System.out.println("Exception : "+e.toString());}
    }
    
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_EXPIRED_ID] + ") FROM " + TBL_HR_AL_STOCK_EXPIRED;
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
                    AlStockExpired alStockExpired = (AlStockExpired)list.get(ls);
                    if(oid == alStockExpired.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    public static void deleteByAlStock(long lAlStockOid)
    {
        DBResultSet dbrs = null;
        try
        {
            String sql = "DELETE FROM "+PstAlStockExpired.TBL_HR_AL_STOCK_EXPIRED+
            " WHERE "+PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_ID]+
            " = "+lAlStockOid;

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
