/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class PstSpecialLeaveStock extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    public static final  String TBL_HR_SPECIAL_LEAVE_STOCK = "hr_special_leave_stock";//HR_AL_STOCK_EXPIRED";
    
    public static final  int FLD_SPECIAL_LEAVE_STOCK_ID = 0;
    public static final  int FLD_EMPLOYEE_ID = 1;    
    public static final  int FLD_TAKEN_DATE = 2;
    public static final  int FLD_TAKEN_QTY = 3;
    public static final  int FLD_SYMBOL_ID = 4;
    public static final  int FLD_SPECIAL_LEAVE_ID = 5;
    public static final  int FLD_LEAVE_STATUS = 6;
        
    public static final  String[] fieldNames = {
        "SPECIAL_LEAVE_STOCK_ID",
        "EMPLOYEE_ID",
        "TAKEN_DATE",
        "TAKEN_QTY",
        "SYMBOL_ID",
        "SPECIAL_LEAVE_ID",
        "LEAVE_STATUS"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };
        
    public static final int STATUS_NOT_PROCESSED = 0;
    public static final int STATUS_PROCESSED = 1;
    
    public static String[] strStatus = 
    {
        "Processed",
        ""
    };
    
    public PstSpecialLeaveStock(){
    }
    
    public PstSpecialLeaveStock(int i) throws DBException {
        super(new PstSpecialLeaveStock());
    }
    
    public PstSpecialLeaveStock(String sOid) throws DBException {
        super(new PstSpecialLeaveStock(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstSpecialLeaveStock(long lOid) throws DBException {
        super(new PstSpecialLeaveStock(0));
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
        return TBL_HR_SPECIAL_LEAVE_STOCK;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstSpecialLeaveStock().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        SpecialLeaveStock specialLeaveStock = fetchExc(ent.getOID());
        ent = (Entity)specialLeaveStock;
        return specialLeaveStock.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((SpecialLeaveStock) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((SpecialLeaveStock) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static SpecialLeaveStock fetchExc(long oid) throws DBException{
        try{
            SpecialLeaveStock specialLeaveStock = new SpecialLeaveStock();
            PstSpecialLeaveStock pstSpecialLeaveStock = new PstSpecialLeaveStock(oid);
            specialLeaveStock.setOID(oid);
            
            specialLeaveStock.setEmployeeId(pstSpecialLeaveStock.getlong(FLD_EMPLOYEE_ID));
            specialLeaveStock.setTakenDate(pstSpecialLeaveStock.getDate(FLD_TAKEN_DATE));
            specialLeaveStock.setTakenQty(pstSpecialLeaveStock.getInt(FLD_TAKEN_QTY));
            specialLeaveStock.setSymbolId(pstSpecialLeaveStock.getlong(FLD_SYMBOL_ID));
            specialLeaveStock.setSpecialLeaveId(pstSpecialLeaveStock.getlong(FLD_SPECIAL_LEAVE_ID));
            specialLeaveStock.setLeaveStatus(pstSpecialLeaveStock.getInt(FLD_LEAVE_STATUS));
            
            return specialLeaveStock;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveStock(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(SpecialLeaveStock specialLeaveStock) throws DBException{
        try{
            PstSpecialLeaveStock pstSpecialLeaveStock = new PstSpecialLeaveStock(0);
            
            /*specialLeaveStock.setEmployeeId(pstSpecialLeaveStock.getlong(FLD_EMPLOYEE_ID));
            specialLeaveStock.setTakenDate(pstSpecialLeaveStock.getDate(FLD_TAKEN_DATE));
            specialLeaveStock.setTakenQty(pstSpecialLeaveStock.getInt(FLD_TAKEN_QTY));
            specialLeaveStock.setSymbolId(pstSpecialLeaveStock.getlong(FLD_SCHEDULE_ID));
            */
            
            pstSpecialLeaveStock.setLong(FLD_EMPLOYEE_ID, specialLeaveStock.getEmployeeId());
            pstSpecialLeaveStock.setDate(FLD_TAKEN_DATE, specialLeaveStock.getTakenDate());
            pstSpecialLeaveStock.setInt(FLD_TAKEN_QTY, specialLeaveStock.getTakenQty());
            pstSpecialLeaveStock.setLong(FLD_SYMBOL_ID, specialLeaveStock.getSymbolId());
            pstSpecialLeaveStock.setLong(FLD_SPECIAL_LEAVE_ID, specialLeaveStock.getSpecialLeaveId());
            pstSpecialLeaveStock.setLong(FLD_LEAVE_STATUS, specialLeaveStock.getLeaveStatus());
            
            pstSpecialLeaveStock.insert();
            specialLeaveStock.setOID(pstSpecialLeaveStock.getlong(FLD_SPECIAL_LEAVE_STOCK_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveStock(0),DBException.UNKNOWN);
        }
        return specialLeaveStock.getOID();
    }
    
    public static long updateExc(SpecialLeaveStock specialLeaveStock) throws DBException{
        try{
            if(specialLeaveStock.getOID() != 0){
                PstSpecialLeaveStock pstSpecialLeaveStock = new PstSpecialLeaveStock(specialLeaveStock.getOID());
                
                /*specialLeaveStock.setEmployeeId(pstSpecialLeaveStock.getlong(FLD_EMPLOYEE_ID));
                specialLeaveStock.setTakenDate(pstSpecialLeaveStock.getDate(FLD_TAKEN_DATE));
                specialLeaveStock.setTakenQty(pstSpecialLeaveStock.getInt(FLD_TAKEN_QTY));
                specialLeaveStock.setSymbolId(pstSpecialLeaveStock.getlong(FLD_SCHEDULE_ID));
                */
                
                pstSpecialLeaveStock.setLong(FLD_EMPLOYEE_ID, specialLeaveStock.getEmployeeId());
                pstSpecialLeaveStock.setDate(FLD_TAKEN_DATE, specialLeaveStock.getTakenDate());
                pstSpecialLeaveStock.setInt(FLD_TAKEN_QTY, specialLeaveStock.getTakenQty());
                pstSpecialLeaveStock.setLong(FLD_SYMBOL_ID, specialLeaveStock.getSymbolId());
                pstSpecialLeaveStock.setLong(FLD_SPECIAL_LEAVE_ID, specialLeaveStock.getSpecialLeaveId());                
                pstSpecialLeaveStock.setLong(FLD_LEAVE_STATUS, specialLeaveStock.getLeaveStatus());
                
                pstSpecialLeaveStock.update();
                return specialLeaveStock.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveStock(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstSpecialLeaveStock pstSpecialLeaveStock = new PstSpecialLeaveStock(oid);
            pstSpecialLeaveStock.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveStock(0),DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_SPECIAL_LEAVE_STOCK;
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
                SpecialLeaveStock specialLeaveStock = new SpecialLeaveStock();
                resultToObject(rs, specialLeaveStock);
                lists.add(specialLeaveStock);
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
    
    public static void resultToObject(ResultSet rs, SpecialLeaveStock specialLeaveStock){
        try{
            specialLeaveStock.setOID(rs.getLong(PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_STOCK_ID]));
            specialLeaveStock.setEmployeeId(rs.getLong(PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_EMPLOYEE_ID]));          
            specialLeaveStock.setTakenDate(rs.getDate(PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE]));          
            specialLeaveStock.setTakenQty(rs.getInt(PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_QTY]));    
            specialLeaveStock.setSymbolId(rs.getLong(PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SYMBOL_ID]));
            specialLeaveStock.setSpecialLeaveId(rs.getLong(PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_ID]));
            specialLeaveStock.setLeaveStatus(rs.getInt(PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_LEAVE_STATUS]));
        }
        catch(Exception e){ }
    }       
        
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_STOCK_ID] + ") FROM " + TBL_HR_SPECIAL_LEAVE_STOCK;
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
                    SpecialLeaveStock specialLeaveStock = (SpecialLeaveStock)list.get(ls);
                    if(oid == specialLeaveStock.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
}
