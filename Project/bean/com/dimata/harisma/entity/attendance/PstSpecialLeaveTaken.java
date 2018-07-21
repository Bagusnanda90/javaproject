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
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Bayu
 */
public class PstSpecialLeaveTaken extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_SPECIAL_LEAVE_TAKEN = "hr_special_leave_taken";
    
    public static final  int FLD_SPECIAL_LEAVE_TAKEN_ID = 0;
    public static final  int FLD_EMPLOYEE_ID = 1;    
    public static final  int FLD_TAKEN_DATE = 2;
    public static final  int FLD_TAKEN_QTY = 3;
    public static final  int FLD_PAID_DATE = 4;
    public static final  int FLD_SYMBOL_ID = 5;
        
    public static final  String[] fieldNames = {
        "SPECIAL_LEAVE_TAKEN_ID",
        "EMPLOYEE_ID",
        "TAKEN_DATE",
        "TAKEN_QTY",
        "PAID_DATE",
        "SYMBOL_ID"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_DATE,
        TYPE_LONG
    };
       
    
    public PstSpecialLeaveTaken(){
    }
    
    public PstSpecialLeaveTaken(int i) throws DBException {
        super(new PstSpecialLeaveTaken());
    }
    
    public PstSpecialLeaveTaken(String sOid) throws DBException {
        super(new PstSpecialLeaveTaken(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstSpecialLeaveTaken(long lOid) throws DBException {
        super(new PstSpecialLeaveTaken(0));
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
        return TBL_HR_SPECIAL_LEAVE_TAKEN;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstSpecialLeaveTaken().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        SpecialLeaveTaken specialLeaveTaken = fetchExc(ent.getOID());
        ent = (Entity)specialLeaveTaken;
        return specialLeaveTaken.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((SpecialLeaveTaken) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((SpecialLeaveTaken) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static SpecialLeaveTaken fetchExc(long oid) throws DBException{
        try{
            SpecialLeaveTaken specialLeaveTaken = new SpecialLeaveTaken();
            PstSpecialLeaveTaken pstSpecialLeaveTaken = new PstSpecialLeaveTaken(oid);
            specialLeaveTaken.setOID(oid);
            
            specialLeaveTaken.setEmployeeId(pstSpecialLeaveTaken.getlong(FLD_EMPLOYEE_ID));
            specialLeaveTaken.setTakenDate(pstSpecialLeaveTaken.getDate(FLD_TAKEN_DATE));
            specialLeaveTaken.setTakenQty(pstSpecialLeaveTaken.getInt(FLD_TAKEN_QTY));
            specialLeaveTaken.setPaidDate(pstSpecialLeaveTaken.getDate(FLD_PAID_DATE));
            specialLeaveTaken.setSymbolId(pstSpecialLeaveTaken.getlong(FLD_SYMBOL_ID));
            
            return specialLeaveTaken;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveTaken(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(SpecialLeaveTaken specialLeaveTaken) throws DBException{
        try{
            PstSpecialLeaveTaken pstSpecialLeaveTaken = new PstSpecialLeaveTaken(0);
            
        
            pstSpecialLeaveTaken.setLong(FLD_EMPLOYEE_ID, specialLeaveTaken.getEmployeeId());
            pstSpecialLeaveTaken.setDate(FLD_TAKEN_DATE, specialLeaveTaken.getTakenDate());
            pstSpecialLeaveTaken.setInt(FLD_TAKEN_QTY, specialLeaveTaken.getTakenQty());
            pstSpecialLeaveTaken.setDate(FLD_PAID_DATE, specialLeaveTaken.getPaidDate());
            pstSpecialLeaveTaken.setLong(FLD_SYMBOL_ID, specialLeaveTaken.getSymbolId());
            
            pstSpecialLeaveTaken.insert();
            specialLeaveTaken.setOID(pstSpecialLeaveTaken.getlong(FLD_SPECIAL_LEAVE_TAKEN_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveTaken(0),DBException.UNKNOWN);
        }
        return specialLeaveTaken.getOID();
    }
    
    public static long updateExc(SpecialLeaveTaken specialLeaveTaken) throws DBException{
        try{
            if(specialLeaveTaken.getOID() != 0){
                PstSpecialLeaveTaken pstSpecialLeaveTaken = new PstSpecialLeaveTaken(specialLeaveTaken.getOID());
              
                pstSpecialLeaveTaken.setLong(FLD_EMPLOYEE_ID, specialLeaveTaken.getEmployeeId());
                pstSpecialLeaveTaken.setDate(FLD_TAKEN_DATE, specialLeaveTaken.getTakenDate());
                pstSpecialLeaveTaken.setInt(FLD_TAKEN_QTY, specialLeaveTaken.getTakenQty());
                pstSpecialLeaveTaken.setDate(FLD_PAID_DATE, specialLeaveTaken.getPaidDate());
                pstSpecialLeaveTaken.setLong(FLD_SYMBOL_ID, specialLeaveTaken.getSymbolId());
            
                
                pstSpecialLeaveTaken.update();
                return specialLeaveTaken.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveTaken(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstSpecialLeaveTaken pstSpecialLeaveTaken = new PstSpecialLeaveTaken(oid);
            pstSpecialLeaveTaken.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeaveTaken(0),DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_SPECIAL_LEAVE_TAKEN;
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
                SpecialLeaveTaken specialLeaveTaken = new SpecialLeaveTaken();
                resultToObject(rs, specialLeaveTaken);
                lists.add(specialLeaveTaken);
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
    
    public static void resultToObject(ResultSet rs, SpecialLeaveTaken specialLeaveTaken){
        try{
            specialLeaveTaken.setOID(rs.getLong(PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_SPECIAL_LEAVE_TAKEN_ID]));
            specialLeaveTaken.setEmployeeId(rs.getLong(PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_EMPLOYEE_ID]));          
            specialLeaveTaken.setTakenDate(rs.getDate(PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE]));          
            specialLeaveTaken.setTakenQty(rs.getInt(PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_QTY]));    
            specialLeaveTaken.setPaidDate(rs.getDate(PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_PAID_DATE]));          
            specialLeaveTaken.setSymbolId(rs.getLong(PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_SYMBOL_ID]));          
        }
        catch(Exception e){ }
    }       
        
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_SPECIAL_LEAVE_TAKEN_ID] + ") FROM " + TBL_HR_SPECIAL_LEAVE_TAKEN;
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
                    SpecialLeaveTaken specialLeaveTaken = (SpecialLeaveTaken)list.get(ls);
                    if(oid == specialLeaveTaken.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    public static boolean getHaveTakenExecuteForm(Date dateFrom, long empId) {
        DBResultSet dbrs = null;
        boolean ada = false;
        try {
            String sql = "SELECT hsult.* FROM `hr_special_leave_taken` hsult INNER JOIN `hr_leave_application` hla ON hla.`LEAVE_APPLICATION_ID` = `hsult`.`LEAVE_APPLICATION_ID`"; 
                   sql += "WHERE `hsult`.`EMPLOYEE_ID` = "+empId+" AND ( \""+ Formater.formatDate(dateFrom, "yyyy-MM-dd HH:mm:ss") +"\" = `hsult`.`TAKEN_DATE`  )  AND `hla`.`DOC_STATUS` > 2 ";
            
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
     * @param specialLeaveOid
     * @param employeeId
     * @param takenDate
     * @return
     */    
    public static boolean isExistSpecialLeaveTaken(long employeeId, Date takenDate)
    {
        boolean result = false;        
        DBResultSet dbrs = null;      
        
        String stSQL = " SELECT " + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_SPECIAL_LEAVE_TAKEN_ID] +
                       " FROM " + PstSpecialLeaveTaken.TBL_HR_SPECIAL_LEAVE_TAKEN +
                       " WHERE " + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE] +
                       " = '" + Formater.formatDate(takenDate, "yyyy-MM-dd") + "'";
        try
        {
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
            System.out.println("Exc when isExistSpecialLeaveTaken : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }                        
    };      
    
}
