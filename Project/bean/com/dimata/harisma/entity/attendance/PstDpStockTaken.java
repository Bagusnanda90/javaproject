/*
 * PstDpStockTaken.java
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
import com.dimata.harisma.entity.employee.*; 
import com.dimata.harisma.entity.attendance.*; 
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.util.Formater;
import com.dimata.util.DateCalc;

/**
 *
 * @author  gedhy
 */
public class PstDpStockTaken extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_DP_STOCK_TAKEN ="hr_dp_stock_taken";// "HR_DP_STOCK_TAKEN";
    
    public static final  int FLD_DP_STOCK_TAKEN_ID = 0;
    public static final  int FLD_EMPLOYEE_ID = 1;  
    public static final  int FLD_DP_STOCK_ID = 2;    
    public static final  int FLD_TAKEN_DATE = 3;
    public static final  int FLD_TAKEN_QTY = 4;
    public static final  int FLD_PAID_DATE = 5;
    public static final  int FLD_LEAVE_APPLICATION_ID = 6;
    public static final  int FLD_TAKEN_FINNISH_DATE = 7;
    //update by satrya 2013-03-19
    //public static final  int FLD_FLAG_DP_BALANCE = 8;
    
    public static final  String[] fieldNames = {
        "DP_STOCK_TAKEN_ID",
        "EMPLOYEE_ID",
        "DP_STOCK_ID",
        "TAKEN_DATE",
        "TAKEN_QTY",
        "PAID_DATE",
        "LEAVE_APPLICATION_ID",
        "TAKEN_FINNISH_DATE"
      //  "FLAG_DP_BALANCE"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE
        //TYPE_INT
    };
    
    //public static final int DP_BALANCE_UNCHECKED=0;
    //public static final int DP_BALANCE_CHECKED=1;
    
    public PstDpStockTaken(){
    }
    
    public PstDpStockTaken(int i) throws DBException {
        super(new PstDpStockTaken());
    }
    
    public PstDpStockTaken(String sOid) throws DBException {
        super(new PstDpStockTaken(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDpStockTaken(long lOid) throws DBException {
        super(new PstDpStockTaken(0));
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
        return TBL_HR_DP_STOCK_TAKEN;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstDpStockTaken().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        DpStockTaken dpStockTaken = fetchExc(ent.getOID());
        ent = (Entity)dpStockTaken;
        return dpStockTaken.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((DpStockTaken) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((DpStockTaken) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static DpStockTaken fetchExc(long oid) throws DBException{
        try{
            DpStockTaken dpStockTaken = new DpStockTaken();
            PstDpStockTaken pstDpStockTaken = new PstDpStockTaken(oid);
            dpStockTaken.setOID(oid);
            
            dpStockTaken.setEmployeeId(pstDpStockTaken.getlong(FLD_EMPLOYEE_ID));
            dpStockTaken.setDpStockId(pstDpStockTaken.getlong(FLD_DP_STOCK_ID));
            dpStockTaken.setTakenDate(pstDpStockTaken.getDate(FLD_TAKEN_DATE));
            dpStockTaken.setTakenQty(pstDpStockTaken.getfloat(FLD_TAKEN_QTY));
            dpStockTaken.setPaidDate(pstDpStockTaken.getDate(FLD_PAID_DATE));
            dpStockTaken.setLeaveApplicationId(pstDpStockTaken.getlong(FLD_LEAVE_APPLICATION_ID));
            dpStockTaken.setTakenFinnishDate(pstDpStockTaken.getDate(FLD_TAKEN_FINNISH_DATE));
            //update by satrya 2013-03-19
            //dpStockTaken.setFlagDpBalance(pstDpStockTaken.getInt(FLD_FLAG_DP_BALANCE));
            
            return dpStockTaken;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockTaken(0),DBException.UNKNOWN);
        }
    }
    
    public  static synchronized long insertExc(DpStockTaken dpStockTaken) throws DBException{
        try{
            PstDpStockTaken pstDpStockTaken = new PstDpStockTaken(0);
            
            pstDpStockTaken.setLong(FLD_DP_STOCK_ID, dpStockTaken.getDpStockId());
            pstDpStockTaken.setLong(FLD_EMPLOYEE_ID, dpStockTaken.getEmployeeId());
            pstDpStockTaken.setDate(FLD_TAKEN_DATE, dpStockTaken.getTakenDate());
            pstDpStockTaken.setFloat(FLD_TAKEN_QTY, dpStockTaken.getTakenQty());
            pstDpStockTaken.setDate(FLD_PAID_DATE, dpStockTaken.getPaidDate());
            pstDpStockTaken.setLong(FLD_LEAVE_APPLICATION_ID, dpStockTaken.getLeaveApplicationId());
            pstDpStockTaken.setDate(FLD_TAKEN_FINNISH_DATE, dpStockTaken.getTakenFinnishDate());
            //update by satrya 2013-03-19
//            pstDpStockTaken.setInt(FLD_FLAG_DP_BALANCE, dpStockTaken.getFlagDpBalance());
            
            pstDpStockTaken.insert();
            dpStockTaken.setOID(pstDpStockTaken.getlong(FLD_DP_STOCK_TAKEN_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockTaken(0),DBException.UNKNOWN);
        }
        return dpStockTaken.getOID();
    }
  
    public synchronized static long updateExc(DpStockTaken dpStockTaken) throws DBException{
        try{
            if(dpStockTaken.getOID() != 0){
                PstDpStockTaken pstDpStockTaken = new PstDpStockTaken(dpStockTaken.getOID());
                
                pstDpStockTaken.setLong(FLD_EMPLOYEE_ID, dpStockTaken.getEmployeeId());
                pstDpStockTaken.setLong(FLD_DP_STOCK_ID, dpStockTaken.getDpStockId());
                pstDpStockTaken.setDate(FLD_TAKEN_DATE, dpStockTaken.getTakenDate());
                pstDpStockTaken.setFloat(FLD_TAKEN_QTY, dpStockTaken.getTakenQty());
                pstDpStockTaken.setDate(FLD_PAID_DATE, dpStockTaken.getPaidDate());
                pstDpStockTaken.setLong(FLD_LEAVE_APPLICATION_ID, dpStockTaken.getLeaveApplicationId());
                pstDpStockTaken.setDate(FLD_TAKEN_FINNISH_DATE, dpStockTaken.getTakenFinnishDate());
                //update by satrya 2013-03-19
               // pstDpStockTaken.setInt(FLD_FLAG_DP_BALANCE, dpStockTaken.getFlagDpBalance());
                
                pstDpStockTaken.update();
                return dpStockTaken.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockTaken(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    /**
     * Keterangan : untuk melakukan update DP Taken Qty , Paid Date , Taken Date, Finish Date
     * jika Paid Date null maka akan di update berdasarkan Owning Date diu PstManagement, jika Paid Date sudah ada maka tidak akan di update
     * @param dpStockTaken
     * @param cekPaidDate 
     */
    public synchronized static void updateTakenNew( DpStockTaken dpStockTaken,boolean cekPaidDate,boolean checkOidTaken) {

        DBResultSet dbrs = null;

        try {
            String sql = " UPDATE "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +" AS t "
                    + " SET t."+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]
                   + " = "+dpStockTaken.getTakenQty()
                   + ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                   + " = "+"\""+Formater.formatDate(dpStockTaken.getTakenDate(), "yyyy-MM-dd HH:mm:ss")+"\"" 
                   + ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]
                   + " = "+"\""+Formater.formatDate(dpStockTaken.getTakenFinnishDate(),  "yyyy-MM-dd HH:mm:ss")+"\"";
                   if(checkOidTaken){
                       sql = sql +" ,t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                   + " = "+dpStockTaken.getDpStockId();
                   }
                  if(cekPaidDate){
                   sql = sql + ", t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]
                   + " = "+"\""+Formater.formatDate(dpStockTaken.getPaidDate(), "yyyy-MM-dd HH:mm:ss")+"\"";
                   }
                   sql = sql + " WHERE "
                  + "t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+" = " + dpStockTaken.getOID();

            //System.out.println("SQL update doc status " + sql);

            int i = DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

    }
    /**
     * Create By satrya 2012-12-12
     * Keterangan Untuk mengecek apakah DP sudah di gunakan, berguna untuk di Overtime
     * @param dpStockId
     * @return 
     */
    public static boolean isNotDpUse(long dpStockId) {
        DBResultSet dbrs = null;
        try {

                String sql = " SELECT "+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]
                        + ","+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                        +" FROM " +PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN
                        + " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                        + " = " + dpStockId;
                
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                return false;///maka tidak akan di delete
            }
            
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return true;
    }
    
     /**
      * @create By satrya 2012-12-19
      * @param dpChekBalancing
      */
       public synchronized static void updateQtyUse( DpChekBalancing dpChekBalancing) {

        DBResultSet dbrs = null;
        try {
            String sql = " UPDATE "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +" AS t INNER JOIN "
                   +PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS m ON(t."
                   +PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                   + " = m."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+")" 
                    + " SET  m."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
                   + " = "+dpChekBalancing.getDp_qty();
                   
                   sql = sql + " WHERE t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+" = " + dpChekBalancing.getDp_stokTakenId();

            //System.out.println("SQL update doc status " + sql);

            int i = DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

    }
       /**
        * @create By satrya 2012-12-19
        * @param dpChekBalancing 
        */
        public synchronized static void updateStockIdPaidDate(DpStockTaken dpStockTaken,boolean NotUpdatePaidTok) {

        DBResultSet dbrs = null;
          if(dpStockTaken.getDpStockTakenId()!=0){
        try {
     
            String sql = " UPDATE "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN
                    + " SET "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]
                   + " = \""+Formater.formatDate(dpStockTaken.getPaidDate(), "yyyy-MM-dd 00:00:00")+"\"" ;
                 if(NotUpdatePaidTok){
                     sql+= ", "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                   + " = "+dpStockTaken.getDpStockId();
                 }
                   
                   sql = sql + " WHERE "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+" = " + dpStockTaken.getDpStockTakenId();

            //System.out.println("SQL update doc status " + sql);

            int i = DBHandler.execUpdate(sql);
      
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
      }   
    }
    public static long deleteExc(long oid) throws DBException{
        try{
            PstDpStockTaken pstDpStockTaken = new PstDpStockTaken(oid);
            pstDpStockTaken.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstDpStockTaken(0),DBException.UNKNOWN);
        }
        return oid;
    }

    /**
     * Keterangan: untuk mencari taken DP
     * create by satrya 2014-01-06
     * @param leaveApplicationId
     * @param employeeId
     * @return 
     */
    public static Vector getDpTaken(long leaveApplicationId,long employeeId) {
        Vector result = new Vector(1, 1);
        if(leaveApplicationId==0 || employeeId==0){
            return result;
        }
        DBResultSet dbrs = null;
        String stSQL = "SELECT * "
                + " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN
                + " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]
                + " = "+leaveApplicationId
                + " ORDER BY " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE];
        try {

            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DpStockTaken objDpStockTaken = new DpStockTaken();
                resultToObject(rs, objDpStockTaken);
                result.add(objDpStockTaken);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when getAlPayable : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    public static int deleteByLeaveAppId(long leaveAppOid) throws Exception{
        try {
            String where =" "+ fieldNames[FLD_LEAVE_APPLICATION_ID]+" = "+leaveAppOid;
            PstDpStockTaken pst =  new PstDpStockTaken(0);
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
            String sql = "SELECT * FROM " + TBL_HR_DP_STOCK_TAKEN;
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
                DpStockTaken dpStockTaken = new DpStockTaken();
                resultToObject(rs, dpStockTaken);
                lists.add(dpStockTaken);
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
    //create by satrya 2013-01-04
    /**
     * 
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector listStockTakenByEmpNumb(String whereClause,String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
         
           String sql = " SELECT "
                   + " DST."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+","
                   + " DST."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+","
                   + " DST."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+","
                   + " DST."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]+","
                   + " DST."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+","
                   + " DST."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] 
	+ " FROM "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN+" AS DST INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE
        + " AS EMP ON (DST."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+" =EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+")";
            
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
             if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                 DpStockTaken dpStockTaken = new DpStockTaken();
               java.util.Date dtTaken =  DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]),rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]));
               java.util.Date dtFinnish =  DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]),rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]));
                dpStockTaken.setDpStockTakenId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]));
                dpStockTaken.setEmployeeId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]));
                dpStockTaken.setLeaveApplicationId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]));
                dpStockTaken.setTakenDate(dtTaken);
                dpStockTaken.setTakenFinnishDate(dtFinnish);
                dpStockTaken.setTakenQty(rs.getFloat(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY])); 
                lists.add(dpStockTaken);
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
    public static void resultToObject(ResultSet rs, DpStockTaken dpStockTaken){
        try{
            dpStockTaken.setOID(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]));
            dpStockTaken.setEmployeeId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]));
            dpStockTaken.setDpStockId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]));
            //dpStockTaken.setTakenDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]));
            dpStockTaken.setTakenQty(rs.getFloat(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]));   
            //dpStockTaken.setPaidDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]));
            dpStockTaken.setLeaveApplicationId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]));
            //dpStockTaken.setTakenFinnishDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]));
            //update by satrya 2013-03-19
//            dpStockTaken.setFlagDpBalance(rs.getInt(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_FLAG_DP_BALANCE]));
            dpStockTaken.setTakenDate(
            rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]) != null ? 
                    PstEmpSchedule.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]), 
                                               rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE])) :
                    rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]));
            dpStockTaken.setPaidDate(
                rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]) != null ? 
                    PstEmpSchedule.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]), 
                                               rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE])) :
                    rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]));            
            dpStockTaken.setTakenFinnishDate(                    
                rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]) != null ? 
                    PstEmpSchedule.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]), 
                                               rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE])) :
                    rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]));
            
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] + ") FROM " + TBL_HR_DP_STOCK_TAKEN;
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
                    DpStockTaken dpStockTaken = (DpStockTaken)list.get(ls);
                    if(oid == dpStockTaken.getOID())
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
    public static Vector getDpPayable(long employeeId)  
    {  
        Vector result = new Vector(1,1);        
        DBResultSet dbrs = null;        
        /*String stSQL = "SELECT " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] +                       
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE] +                                              
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] +  
                       //update by satrya 2013-03-19
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] +  
//                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_FLAG_DP_BALANCE] +  
                       " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +
                       " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                       " = 0" + 
                       " ORDER BY " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE];*/
      //update by satrya 2013-10-17
        String stSQL = " SELECT DPSTOK.* FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN  + " AS DPSTOK "
+ " LEFT JOIN " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DPMAN ON DPSTOK."+fieldNames[FLD_DP_STOCK_ID]+ " =DPSTOK."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
+ " INNER JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA ON LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+"=DPSTOK."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]
+ " WHERE "
+ "(DPSTOK."+fieldNames[FLD_DP_STOCK_ID]+"=0 OR DPMAN."
+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+"<0 OR DPMAN."
+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
+"-DPMAN."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]+ " <0) AND DPSTOK."
+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+"="+employeeId
+ " AND DPSTOK."+fieldNames[FLD_DP_STOCK_ID] + " =0"
+ " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "="+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED
+ " GROUP BY DPSTOK."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] 
+ " ORDER BY " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE];
        try
        {
//            System.out.println("SQL getDpPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                DpStockTaken objDpStockTaken = new DpStockTaken();
                objDpStockTaken.setOID(rs.getLong(1));
                objDpStockTaken.setEmployeeId(rs.getLong(2));
                objDpStockTaken.setDpStockId(rs.getInt(3));
                objDpStockTaken.setTakenDate(DBHandler.convertDate(rs.getDate(4), rs.getTime(4)));
                objDpStockTaken.setTakenFinnishDate(DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE])));
                objDpStockTaken.setTakenQty(rs.getFloat(5));                
                objDpStockTaken.setPaidDate(rs.getDate(6));                
                //update by satrya 2013-03-19
                objDpStockTaken.setLeaveApplicationId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]));
//                objDpStockTaken.setFlagDpBalance(rs.getInt(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_FLAG_DP_BALANCE]));
                result.add(objDpStockTaken);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getDpPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }    
    
    /**
     * @desc Dipergunakan untuk mengecek dp yang belum terbayar sebelum opname
     * @param employeeId
     * @param takenDate
     * @return
     * @created by artha
     */    
    public static Vector getDpPayable(long employeeId, Date takenDate)  
    {  
        Vector result = new Vector(1,1);        
        DBResultSet dbrs = null;        
        String stSQL = "SELECT " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] +                       
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE] +                                              
                       " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +
                       " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                       " = 0" + 
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                       " = '"+ Formater.formatDate(takenDate, "yyyy-MM-dd 00:00:00")+
                       "' ORDER BY " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE];
        try
        {
//            System.out.println("SQL getDpPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                DpStockTaken objDpStockTaken = new DpStockTaken(); 
                resultToObject(rs, objDpStockTaken);
                
                result.add(objDpStockTaken);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getDpPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }    
    /**
     * @desc Dipergunakan untuk mengecek dp yang belum terbayar sebelum opname
     * @param employeeId
     * @param takenDate
     * @return
     * @created by artha
     */    
    public static Vector getDpPayableBeforeOpname(long employeeId, Date takenDate)  
    {  
        Vector result = new Vector(1,1);        
        DBResultSet dbrs = null;        
        String stSQL = "SELECT " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] +                       
                       ", " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE] +                                              
                       " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +
                       " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                       " = 0" + 
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                       " <= '"+ Formater.formatDate(takenDate, "yyyy-MM-dd 00:00:00")+
                       "' ORDER BY " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE];
        try
        {
            System.out.println("[SQL] entity.attendance.PstDpStockTaken.getDpPayableBeforeOpname : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                DpStockTaken objDpStockTaken = new DpStockTaken(); 
                resultToObject(rs, objDpStockTaken);
                
                result.add(objDpStockTaken);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getDpPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }    
    
    /**
     * @param dpStockOid
     * @param employeeId
     * @param takenDate
     * @return
     */    
    public static boolean existDpStockTaken(long dpStockOid, long employeeId, Date takenDate)
    {
        boolean result = false;        
        DBResultSet dbrs = null;      
        String strTakenDate = "\"" + com.dimata.util.Formater.formatDate(takenDate, "yyyy-MM-dd") + "\"";
        String stSQL = "SELECT " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] +
                       " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +
                       " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                       " = " + dpStockOid + 
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                       " = " + strTakenDate;
        try
        {
//            System.out.println("SQL existDpStockTaken(dpStockOid, employeeId, takenDate) : " + stSQL);
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
            System.out.println("Exc when existDpStockTaken(dpStockOid, employeeId, takenDate) : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }                        
    };       

    
    /**
     * @param dpStockOid
     * @param employeeId
     * @param takenDate
     * @return
     */    
    public static boolean existDpStockTaken(long employeeId, Date takenDate)
    {
        boolean result = false;        
        DBResultSet dbrs = null;      
        String strTakenDate = "\"" + com.dimata.util.Formater.formatDate(takenDate, "yyyy-MM-dd") + "\"";
        String stSQL = "SELECT " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] +
                       " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +
                       " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                       " = " + strTakenDate;
        try
        {
//            System.out.println("SQL existDpStockTaken(employeeId, takenDate) : " + stSQL);
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
            System.out.println("Exc when existDpStockTaken(dpStockOid, employeeId, takenDate) : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }                        
    };       
    
    
    public static void deleteByEmployee(long lEmployeeOid)
    {
        DBResultSet dbrs = null;
        try
        {
            String sql = "DELETE FROM "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN+
            " WHERE "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+
            " = "+lEmployeeOid;

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
      
    public static void deleteByDpStock(long lDpStockOid)
    {
        DBResultSet dbrs = null;
        try
        {
            String sql = "DELETE FROM "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN+
            " WHERE "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]+
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
    //create by satrya 2013-01-09
    /**
     * 
     * @param lDpStockTakenID 
     */
     public static void deleteByDpStockTaken(long lDpStockTakenID)
    {
        DBResultSet dbrs = null;
        try
        {
            String sql = "DELETE FROM "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN+
            " WHERE "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+
            " = "+lDpStockTakenID;

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
    
    /**
     * @param lDepartmentOid
     * @created by Edhy     
     */
    public void deleteDpStockTakenPerDepartment(long lDepartmentOid) 
    {                     
        DBResultSet dbrs = null;
        String stSQL = " SELECT DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] +                               
                       " FROM "+ TBL_HR_DP_STOCK_TAKEN + " AS DP" +
                       " INNER JOIN "+ PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                       " ON DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] +
                       " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                       
                       " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                       " = " + lDepartmentOid;
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                long lDpStockOid = rs.getLong(1);                
                
                // delete Dp stock
                try
                {
                    long oidDpStock = PstDpStockTaken.deleteExc(lDpStockOid);
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
    
    /**
     * menghitung DP qunatity ( dalam unit workday ( 8 jam kerja ) dari seorang karyawan pada rentang periode waktu tertentu
     * @param employeeId
     * @param startDate
     * @param endDate
     * @return 
     */
    public static float getDpQty(long employeeId, Date startDate, Date endDate) {
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
                DpStockTaken dpStockTaken = (DpStockTaken) list.get(idx);
                if ((DateCalc.dayDifference(dpStockTaken.getTakenDate(), startDate) >= 0)
                        && (DateCalc.dayDifference(dpStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                    // taken date and taken finish di dalam start and end date
                    if (dpStockTaken.getTakenQty() == (float) Math.floor(dpStockTaken.getTakenQty())) {
                        // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                        qty = qty + DateCalc.dayDifference(startDate, endDate)+1;
                    } else {
                        // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                        qty = qty + DateCalc.dayDifference(startDate, endDate);
                        qty = qty + dpStockTaken.getTakenQty() - (float) Math.floor(dpStockTaken.getTakenQty());
                    }
                } else {
                    if ((DateCalc.dayDifference(dpStockTaken.getTakenDate(), startDate) >= 0)
                            && (DateCalc.dayDifference(dpStockTaken.getTakenFinnishDate(), endDate) > 0)) {
                        // taken date and taken finish di dalam start and end date
                        if (dpStockTaken.getTakenQty() == (float) Math.floor(dpStockTaken.getTakenQty())) {
                            // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                            qty = qty + DateCalc.dayDifference(startDate, dpStockTaken.getTakenFinnishDate())+1;
                        } else {
                            // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                            qty = qty + DateCalc.dayDifference(startDate, dpStockTaken.getTakenFinnishDate());
                            qty = qty + dpStockTaken.getTakenQty() - (float) Math.floor(dpStockTaken.getTakenQty());
                        }
                    } else {
                        if ((DateCalc.dayDifference(dpStockTaken.getTakenDate(), startDate) < 0)
                                && (DateCalc.dayDifference(dpStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                            // taken date and taken finish di dalam start and end date
                            if (dpStockTaken.getTakenQty() == (float) Math.floor(dpStockTaken.getTakenQty())) {
                                // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                                qty = qty + DateCalc.dayDifference(dpStockTaken.getTakenDate(), endDate)+1;
                            } else {
                                // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                                qty = qty + DateCalc.dayDifference(dpStockTaken.getTakenDate(), endDate);
                                qty = qty + dpStockTaken.getTakenQty() - (float) Math.floor(dpStockTaken.getTakenQty());
                            }
                        } else {
                            // startdate  and enddate  beyond takendate and takenfinishdate
                            qty = qty + dpStockTaken.getTakenQty();                            
                        }
                    }
                }
            }
        }
        return qty;
    }  
    
    public static long getDpStokTakenId (long stokId){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DP_STOCK_TAKEN 
                    +" WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]+"="+stokId;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            long oidDPStokTakenId = 0;
            while(rs.next()) { oidDPStokTakenId = rs.getLong(1); }
            
            rs.close();
            return oidDPStokTakenId;
            
        }catch(Exception e) {
           return 0;
        }finally {
            DBResultSet.close(dbrs);
        }
        
    }
    
    public static boolean getHaveTakenExecuteForm(Date dateFrom, long empId) {
        DBResultSet dbrs = null;
        boolean ada = false;
        try {
            String sql = "SELECT hdst.* FROM `hr_dp_stock_taken` hdst INNER JOIN `hr_leave_application` hla ON hla.`LEAVE_APPLICATION_ID` = `hdst`.`LEAVE_APPLICATION_ID` "; 
                   sql += "WHERE `hdst`.`EMPLOYEE_ID` = "+empId+" AND ( \""+ Formater.formatDate(dateFrom, "yyyy-MM-dd HH:mm:ss") +"\" BETWEEN `hdst`.`TAKEN_DATE` AND `hdst`.`TAKEN_FINNISH_DATE`  )  AND `hla`.`DOC_STATUS` > 2 ";
            
          dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ada = true;
            }

            rs.close();
            return ada;
        } catch (Exception e) {
            return ada;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    
    /**
     * <pre>create by satrya 2013-12-20</pre>
     * Keterangan: untuk update paid date menjadi null
     * @param dpStockManagement 
     */
     public synchronized static void updateStockIdAndPaidDateBecomeNull(DpStockManagement dpStockManagement) {
          if(dpStockManagement.getOID()!=0){
        try {
     
            String sql = " UPDATE "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN
                    + " SET "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]
                   + " = (NULL), "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                   + " = "+0;
                
                   
                   sql = sql + " WHERE "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]+" = " + dpStockManagement.getOID();

            //System.out.println("SQL update doc status " + sql);

            int i = DBHandler.execUpdate(sql);
      
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
      }   
    }

}
