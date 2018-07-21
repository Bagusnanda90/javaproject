/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Tu Roy
 */
public class PstAlStockExtended extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
    public static final  String TBL_HR_AL_STOCK_EXTENDED = "hr_al_stock_extended";//HR_AL_STOCK_EXPIRED";
    
    public static final  int FLD_AL_STOCK_EXTENDED_ID = 0;
    public static final  int FLD_AL_STOCK_ID = 1;    
    public static final  int FLD_EXTENDED_DATE = 2;
    public static final  int FLD_EXTENDED_QTY = 3;
    public static final  int FLD_DECISION_DATE = 4;
    public static final  int FLD_NOTE = 5;
    public static final  int FLD_EXTENDED_BY_PIC = 6;
    public static final  int FLD_APPROVE_BY_PIC = 7;
    
    
    public static final  String[] fieldNames = {
        "AL_STOCK_EXTENDED_ID",
        "AL_STOCK_ID",
        "EXTENDED_DATE",
        "EXTENDED_QTY",
        "DECISION_DATE",
        "NOTE",
        "EXTENDED_BY_PIC",
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
    
    public PstAlStockExtended(){
        
    }
    
    public PstAlStockExtended(int i) throws DBException{
        super(new PstAlStockExtended());
    }
    
    public PstAlStockExtended(String sOid) throws DBException {
        super(new PstAlStockExtended(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;        
    }
    
    public PstAlStockExtended(long lOid) throws DBException{
        super(new PstAlStockExtended(0));
        String sOid = "0";
        try{
           sOid = String.valueOf(lOid);
           
        }catch(Exception e){
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
        return TBL_HR_AL_STOCK_EXTENDED;
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
        AlStockExtended alStockExtended = fetchExc(ent.getOID());
        ent = (Entity)alStockExtended;
        return alStockExtended.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((AlStockExtended) ent);
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
    
    public static AlStockExtended fetchExc(long oid) throws DBException{
        try{
            AlStockExtended alStockExtended = new AlStockExtended();
            PstAlStockExtended pstAlStockExtended = new PstAlStockExtended(oid);
            alStockExtended.setOID(oid);
            
            alStockExtended.setAlStockId(pstAlStockExtended.getlong(FLD_AL_STOCK_ID));
            alStockExtended.setExtendedDate(pstAlStockExtended.getDate(FLD_EXTENDED_DATE));
            alStockExtended.setExtendedQty(pstAlStockExtended.getfloat(FLD_EXTENDED_QTY));
            alStockExtended.setDecisionDate(pstAlStockExtended.getDate(FLD_DECISION_DATE));
            alStockExtended.setNote(pstAlStockExtended.getString(FLD_NOTE));
            alStockExtended.setExtendedByPic(pstAlStockExtended.getString(FLD_EXTENDED_BY_PIC));
            alStockExtended.setApproveByPic(pstAlStockExtended.getString(FLD_APPROVE_BY_PIC)); 
            
            return alStockExtended;
            
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstAlStockExtended(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(AlStockExtended alStockExtended) throws DBException{
        try{
            PstAlStockExtended pstAlStockExtended = new PstAlStockExtended(0);
            
            pstAlStockExtended.setLong(FLD_AL_STOCK_ID, alStockExtended.getAlStockId());
            pstAlStockExtended.setDate(FLD_EXTENDED_DATE, alStockExtended.getExtendedDate());
            pstAlStockExtended.setFloat(FLD_EXTENDED_QTY, alStockExtended.getExtendedQty());
            pstAlStockExtended.setDate(FLD_DECISION_DATE, alStockExtended.getDecisionDate());
            pstAlStockExtended.setString(FLD_NOTE,alStockExtended.getNote());
            pstAlStockExtended.setString(FLD_EXTENDED_BY_PIC,alStockExtended.getExtendedByPic());
            pstAlStockExtended.setString(FLD_APPROVE_BY_PIC,alStockExtended.getApproveByPic());            
            
            pstAlStockExtended.insert();
            alStockExtended.setOID(pstAlStockExtended.getlong(FLD_AL_STOCK_EXTENDED_ID));
            
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstAlStockExpired(0),DBException.UNKNOWN);
        }
        return alStockExtended.getOID();
    }
    
    
    public static long updateExc(AlStockExtended alStockExtended)throws DBException{
        try{
           if(alStockExtended.getOID() != 0){
                PstAlStockExtended pstAlStockExtended = new PstAlStockExtended(alStockExtended.getOID());
                
                pstAlStockExtended.setLong(FLD_AL_STOCK_ID, alStockExtended.getAlStockId());
                pstAlStockExtended.setDate(FLD_EXTENDED_DATE, alStockExtended.getExtendedDate());
                pstAlStockExtended.setFloat(FLD_EXTENDED_QTY, alStockExtended.getExtendedQty());
                pstAlStockExtended.setDate(FLD_DECISION_DATE, alStockExtended.getDecisionDate());
                pstAlStockExtended.setString(FLD_NOTE, alStockExtended.getNote());
                pstAlStockExtended.setString(FLD_EXTENDED_BY_PIC, alStockExtended.getExtendedByPic());
                pstAlStockExtended.setString(FLD_APPROVE_BY_PIC, alStockExtended.getApproveByPic());
                
                pstAlStockExtended.update();
                return alStockExtended.getOID();                
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
        try{
            String sql = "SELECT "+PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_AL_STOCK_EXTENDED_ID]+","
                                        +PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_AL_STOCK_ID]+","
                                        +PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_EXTENDED_DATE]+","
                                        +PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_EXTENDED_QTY]+","
                                        +PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_DECISION_DATE]+","
                                        +PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_NOTE]+","
                                        +PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_EXTENDED_BY_PIC]+","
                                        +PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_APPROVE_BY_PIC]+
                                        " FROM "+TBL_HR_AL_STOCK_EXTENDED;
            
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
                AlStockExtended alStockExtended = new AlStockExtended();
                resultToObject(rs, alStockExtended);
                lists.add(alStockExtended);
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
    public static void resultToObject(ResultSet rs, AlStockExtended alStockExtended){
        try{
            alStockExtended.setOID(rs.getLong(PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_AL_STOCK_EXTENDED_ID]));
            alStockExtended.setAlStockId(rs.getLong(PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_AL_STOCK_ID]));
            alStockExtended.setExtendedDate(rs.getDate(PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_EXTENDED_DATE]));
            alStockExtended.setExtendedQty(rs.getInt(PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_EXTENDED_QTY]));  
            alStockExtended.setDecisionDate(rs.getDate(PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_DECISION_DATE]));
            alStockExtended.setNote(rs.getString(rs.getString(PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_NOTE])));
            alStockExtended.setExtendedByPic(rs.getString(PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_EXTENDED_BY_PIC]));
            alStockExtended.setApproveByPic(rs.getString(PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_APPROVE_BY_PIC]));
            
        }catch(Exception e){ System.out.println("Exception : "+e.toString());}
    }
   
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT COUNT("+PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_AL_STOCK_EXTENDED_ID]+
                    ") FROM "+TBL_HR_AL_STOCK_EXTENDED;
            
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while(rs.next()) { count = rs.getInt(1); }
            
            rs.close();
            return count;
        }catch(Exception e){
            return 0;
        }finally{
            DBResultSet.close(dbrs);
        }  
        
        
    }
    
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
                    AlStockExtended alStockExtended = (AlStockExtended)list.get(ls);
                    if(oid == alStockExtended.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    public static void deleteByAlStok(long lAlStockOid){
        DBResultSet dbrs = null;
        try{
            String sql = "DELETE FROM "+PstAlStockExtended.TBL_HR_AL_STOCK_EXTENDED+
                    " WHERE "+PstAlStockExtended.fieldNames[PstAlStockExtended.FLD_AL_STOCK_EXTENDED_ID]+
                    " = "+lAlStockOid;
            
            int status = DBHandler.execUpdate(sql);
        }catch(Exception e){
            System.out.println("Exception : "+e.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
    }
  
}
