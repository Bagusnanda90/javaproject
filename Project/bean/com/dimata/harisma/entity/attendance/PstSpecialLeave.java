
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
 * @author bayu
 */

public class PstSpecialLeave extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_HR_SPECIAL_LEAVE = "hr_special_leave";
    
    public static final  int FLD_SPECIAL_LEAVE_ID = 0;
    public static final  int FLD_EMPLOYEE_ID = 1;    
    public static final  int FLD_REQUEST_DATE = 2;
    public static final  int FLD_UNPAID_REASON = 3;
    public static final  int FLD_REMARK = 4;
    public static final  int FLD_APPROVAL_ID = 5;
    public static final  int FLD_APPROVAL2_ID = 6;
    public static final  int FLD_APPROVAL3_ID= 7;
    public static final  int FLD_APPROVAL_DATE = 8;
    public static final  int FLD_APPROVAL2_DATE = 9;
    public static final  int FLD_APPROVAL3_DATE = 10;
        
    public static final  String[] fieldNames = {
        "SPECIAL_LEAVE_ID",
        "EMPLOYEE_ID",
        "REQUEST_DATE",
        "UNPAID_LEAVE_REASON",
        "OTHER_REMARKS",
        "APPROVAL_ID",
        "APPROVAL2_ID",
        "APPROVAL3_ID",
        "APPROVAL_DATE",
        "APPROVAL2_DATE",
        "APPROVAL3_DATE"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,        
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE
    };
    
    public PstSpecialLeave(){
    }
    
    public PstSpecialLeave(int i) throws DBException {
        super(new PstSpecialLeave());
    }
    
    public PstSpecialLeave(String sOid) throws DBException {
        super(new PstSpecialLeave(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstSpecialLeave(long lOid) throws DBException {
        super(new PstSpecialLeave(0));
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
        return TBL_HR_SPECIAL_LEAVE;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstSpecialLeave().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        SpecialLeave specialLeave = fetchExc(ent.getOID());
        ent = (Entity)specialLeave;
        return specialLeave.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((SpecialLeave) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((SpecialLeave) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static SpecialLeave fetchExc(long oid) throws DBException{
        try{
            SpecialLeave specialLeave = new SpecialLeave();
            PstSpecialLeave pstSpecialLeave = new PstSpecialLeave(oid);
            specialLeave.setOID(oid);
            
            specialLeave.setEmployeeId(pstSpecialLeave.getlong(FLD_EMPLOYEE_ID));
            specialLeave.setRequestDate(pstSpecialLeave.getDate(FLD_REQUEST_DATE));
            specialLeave.setUnpaidLeaveReason(pstSpecialLeave.getString(FLD_UNPAID_REASON));
            specialLeave.setOtherRemarks(pstSpecialLeave.getString(FLD_REMARK));  
            specialLeave.setApprovalId(pstSpecialLeave.getlong(FLD_APPROVAL_ID));
            specialLeave.setApproval2Id(pstSpecialLeave.getlong(FLD_APPROVAL2_ID));
            specialLeave.setApproval3Id(pstSpecialLeave.getlong(FLD_APPROVAL3_ID));
            specialLeave.setApprovalDate(pstSpecialLeave.getDate(FLD_APPROVAL_DATE));
            specialLeave.setApproval2Date(pstSpecialLeave.getDate(FLD_APPROVAL2_DATE));
            specialLeave.setApproval3Date(pstSpecialLeave.getDate(FLD_APPROVAL3_DATE));
            
            return specialLeave;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeave(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(SpecialLeave specialLeave) throws DBException{
        try{
            PstSpecialLeave pstSpecialLeave = new PstSpecialLeave(0);
                    
            pstSpecialLeave.setLong(FLD_EMPLOYEE_ID, specialLeave.getEmployeeId());
            pstSpecialLeave.setDate(FLD_REQUEST_DATE, specialLeave.getRequestDate());
            pstSpecialLeave.setString(FLD_UNPAID_REASON, specialLeave.getUnpaidLeaveReason());
            pstSpecialLeave.setString(FLD_REMARK, specialLeave.getOtherRemarks());
            pstSpecialLeave.setLong(FLD_APPROVAL_ID, specialLeave.getApprovalId());
            pstSpecialLeave.setLong(FLD_APPROVAL2_ID, specialLeave.getApproval2Id());
            pstSpecialLeave.setLong(FLD_APPROVAL3_ID, specialLeave.getApproval3Id());
            pstSpecialLeave.setDate(FLD_APPROVAL_DATE, specialLeave.getApprovalDate());
            pstSpecialLeave.setDate(FLD_APPROVAL2_DATE, specialLeave.getApproval2Date());
            pstSpecialLeave.setDate(FLD_APPROVAL3_DATE, specialLeave.getApproval3Date());
            
            pstSpecialLeave.insert();
            specialLeave.setOID(pstSpecialLeave.getlong(FLD_SPECIAL_LEAVE_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeave(0),DBException.UNKNOWN);
        }
        return specialLeave.getOID();
    }
    
    public static long updateExc(SpecialLeave specialLeave) throws DBException{
        try{
            if(specialLeave.getOID() != 0){
                PstSpecialLeave pstSpecialLeave = new PstSpecialLeave(specialLeave.getOID());
                
                pstSpecialLeave.setLong(FLD_EMPLOYEE_ID, specialLeave.getEmployeeId());
                pstSpecialLeave.setDate(FLD_REQUEST_DATE, specialLeave.getRequestDate());
                pstSpecialLeave.setString(FLD_UNPAID_REASON, specialLeave.getUnpaidLeaveReason());
                pstSpecialLeave.setString(FLD_REMARK, specialLeave.getOtherRemarks());       
                pstSpecialLeave.setLong(FLD_APPROVAL_ID, specialLeave.getApprovalId());
                pstSpecialLeave.setLong(FLD_APPROVAL2_ID, specialLeave.getApproval2Id());
                pstSpecialLeave.setLong(FLD_APPROVAL3_ID, specialLeave.getApproval3Id());
                pstSpecialLeave.setDate(FLD_APPROVAL_DATE, specialLeave.getApprovalDate());
                pstSpecialLeave.setDate(FLD_APPROVAL2_DATE, specialLeave.getApproval2Date());
                pstSpecialLeave.setDate(FLD_APPROVAL3_DATE, specialLeave.getApproval3Date());
                
                pstSpecialLeave.update();
                return specialLeave.getOID();                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeave(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstSpecialLeave pstSpecialLeave = new PstSpecialLeave(oid);
            pstSpecialLeave.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstSpecialLeave(0),DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_SPECIAL_LEAVE;
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
                SpecialLeave specialLeave = new SpecialLeave();
                resultToObject(rs, specialLeave);
                lists.add(specialLeave);
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
    
    public static void resultToObject(ResultSet rs, SpecialLeave specialLeave){
        try{
            specialLeave.setOID(rs.getLong(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_SPECIAL_LEAVE_ID]));
            specialLeave.setEmployeeId(rs.getLong(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_EMPLOYEE_ID]));          
            specialLeave.setRequestDate(rs.getDate(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_REQUEST_DATE]));          
            specialLeave.setUnpaidLeaveReason(rs.getString(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_UNPAID_REASON]));    
            specialLeave.setOtherRemarks(rs.getString(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_REMARK]));
            specialLeave.setApprovalId(rs.getLong(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_APPROVAL_ID]));
            specialLeave.setApproval2Id(rs.getLong(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_APPROVAL2_ID]));
            specialLeave.setApproval3Id(rs.getLong(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_APPROVAL3_ID]));
            specialLeave.setApprovalDate(rs.getDate(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_APPROVAL_DATE]));
            specialLeave.setApproval2Date(rs.getDate(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_APPROVAL2_DATE]));
            specialLeave.setApproval3Date(rs.getDate(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_APPROVAL3_DATE]));
            
        }catch(Exception e){ }
    }       
        
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_SPECIAL_LEAVE_ID] + ") FROM " + TBL_HR_SPECIAL_LEAVE;
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
                    SpecialLeave specialLeave = (SpecialLeave)list.get(ls);
                    if(oid == specialLeave.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
}
