/*
 * PstCanteenSchedule.java
 *
 * Created on April 23, 2005, 11:41 AM
 */

package com.dimata.harisma.entity.canteen;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*; 

/**
 *
 * @author  gedhy
 */
public class PstCanteenSchedule  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_CANTEEN_SCHEDULE ="hr_canteen_schedule";//"HR_CANTEEN_SCHEDULE";
    
    public static final  int FLD_CANTEEN_SCHEDULE_ID = 0;
    public static final  int FLD_CODE = 1;
    public static final  int FLD_NAME = 2;
    public static final  int FLD_SCHEDULE_DATE = 3;
    public static final  int FLD_TIME_OPEN = 4;
    public static final  int FLD_TIME_CLOSE = 5;
    
    public static final  String[] fieldNames = {
        "CANTEEN_SCHEDULE_ID",
        "CODE",
        "NAME",
        "SCHEDULE_DATE",
        "TIME_OPEN",
        "TIME_CLOSE"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE
    };
    
    public PstCanteenSchedule(){
    }
    
    public PstCanteenSchedule(int i) throws DBException {
        super(new PstCanteenSchedule());
    }
    
    public PstCanteenSchedule(String sOid) throws DBException {
        super(new PstCanteenSchedule(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstCanteenSchedule(long lOid) throws DBException {
        super(new PstCanteenSchedule(0));
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
        return TBL_HR_CANTEEN_SCHEDULE;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstCanteenSchedule().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        CanteenSchedule canteenSchedule = fetchExc(ent.getOID());
        ent = (Entity)canteenSchedule;
        return canteenSchedule.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((CanteenSchedule) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((CanteenSchedule) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static CanteenSchedule fetchExc(long oid) throws DBException{
        try{
            CanteenSchedule canteenSchedule = new CanteenSchedule();
            PstCanteenSchedule pstCanteenSchedule = new PstCanteenSchedule(oid);
            canteenSchedule.setOID(oid);
            
            canteenSchedule.setSCode(pstCanteenSchedule.getString(FLD_CODE));
            canteenSchedule.setSName(pstCanteenSchedule.getString(FLD_NAME));
            canteenSchedule.setDScheduleDate(pstCanteenSchedule.getDate(FLD_SCHEDULE_DATE));
            canteenSchedule.setTTimeOpen(pstCanteenSchedule.getDate(FLD_TIME_OPEN));
            canteenSchedule.setTTimeClose(pstCanteenSchedule.getDate(FLD_TIME_CLOSE));
            
            return canteenSchedule;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstCanteenSchedule(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(CanteenSchedule canteenSchedule) throws DBException{
        try{
            PstCanteenSchedule pstCanteenSchedule = new PstCanteenSchedule(0);
            
            pstCanteenSchedule.setString(FLD_CODE, canteenSchedule.getSCode());
            pstCanteenSchedule.setString(FLD_NAME, canteenSchedule.getSName());
            pstCanteenSchedule.setDate(FLD_SCHEDULE_DATE, canteenSchedule.getDScheduleDate());
            pstCanteenSchedule.setDate(FLD_TIME_OPEN, canteenSchedule.getTTimeOpen());
            pstCanteenSchedule.setDate(FLD_TIME_CLOSE, canteenSchedule.getTTimeClose());
            
            pstCanteenSchedule.insert();
            canteenSchedule.setOID(pstCanteenSchedule.getlong(FLD_CANTEEN_SCHEDULE_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstCanteenSchedule(0),DBException.UNKNOWN);
        }
        return canteenSchedule.getOID();
    }
    
    public static long updateExc(CanteenSchedule canteenSchedule) throws DBException{
        try{
            if(canteenSchedule.getOID() != 0){
                PstCanteenSchedule pstCanteenSchedule = new PstCanteenSchedule(canteenSchedule.getOID());
                
                pstCanteenSchedule.setString(FLD_CODE, canteenSchedule.getSCode());
                pstCanteenSchedule.setString(FLD_NAME, canteenSchedule.getSName());
                pstCanteenSchedule.setDate(FLD_SCHEDULE_DATE, canteenSchedule.getDScheduleDate());
                pstCanteenSchedule.setDate(FLD_TIME_OPEN, canteenSchedule.getTTimeOpen());
                pstCanteenSchedule.setDate(FLD_TIME_CLOSE, canteenSchedule.getTTimeClose());
                
                
                pstCanteenSchedule.update();
                return canteenSchedule.getOID();
                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstCanteenSchedule(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstCanteenSchedule pstCanteenSchedule = new PstCanteenSchedule(oid);
            pstCanteenSchedule.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstCanteenSchedule(0),DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_CANTEEN_SCHEDULE;
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
                CanteenSchedule canteenSchedule = new CanteenSchedule();
                resultToObject(rs, canteenSchedule);
                lists.add(canteenSchedule);
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
    
    public static void resultToObject(ResultSet rs, CanteenSchedule canteenSchedule){
        try{
            canteenSchedule.setOID(rs.getLong(PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CANTEEN_SCHEDULE_ID]));
            canteenSchedule.setSCode(rs.getString(PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE]));
            canteenSchedule.setSName(rs.getString(PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_NAME]));
            canteenSchedule.setDScheduleDate(rs.getDate(PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_SCHEDULE_DATE]));
            canteenSchedule.setTTimeOpen(rs.getTime(PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_TIME_OPEN]));
            canteenSchedule.setTTimeClose(rs.getTime(PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_TIME_CLOSE]));
        }catch(Exception e){ }
    }
    
    public static boolean checkOID(long canteenScheduleId){
        DBResultSet dbrs = null;
        boolean result = false;
        try{
            String sql = "SELECT * FROM " + TBL_HR_CANTEEN_SCHEDULE + " WHERE " +
            PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CANTEEN_SCHEDULE_ID] + " = " + canteenScheduleId;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) { result = true; }
            rs.close();
        }catch(Exception e){
            System.out.println("err : "+e.toString());
        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CANTEEN_SCHEDULE_ID] + ") FROM " + TBL_HR_CANTEEN_SCHEDULE;
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
    public static int findLimitStart( long oid, int recordToGet, String whereClause, String order){
        int size = getCount(whereClause);
        int start = 0;
        boolean found =false;
        for(int i=0; (i < size) && !found ; i=i+recordToGet){
            Vector list =  list(i,recordToGet, whereClause, order);
            start = i;
            if(list.size()>0){
                for(int ls=0;ls<list.size();ls++){
                    CanteenSchedule canteenSchedule = (CanteenSchedule)list.get(ls);
                    if(oid == canteenSchedule.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if(start == 0)
            cmd =  Command.FIRST;
        else{
            if(start == (vectSize-recordToGet))
                cmd = Command.LAST;
            else{
                start = start + recordToGet;
                if(start <= (vectSize - recordToGet)){
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                }else{
                    start = start - recordToGet;
                    if(start > 0){
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }
        
        return cmd;
    }
    
}
