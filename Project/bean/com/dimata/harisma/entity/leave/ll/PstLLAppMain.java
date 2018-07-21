/*
 * PstDpApplication.java
 *
 * Created on October 21, 2004, 12:05 PM
 */

package com.dimata.harisma.entity.leave.ll;

import com.dimata.harisma.entity.leave.*;
import com.dimata.qdep.db.*; 
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language; 

import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class PstLLAppMain  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_LL_APP_MAIN = "hr_ll_app";
    
    public static final int FLD_LL_APP_ID = 0;    
    public static final int FLD_SUBMISSION_DATE = 1;
    public static final int FLD_APPROVAL_ID = 2;
    public static final int FLD_APPROVAL_DATE = 3;
    public static final int FLD_EMPLOYEE_ID = 4;
    public static final int FLD_BALANCE = 5;
    public static final int FLD_DOC_STATUS = 6;
    public static final int FLD_APPROVAL2_ID = 7;
    public static final int FLD_APPROVAL3_ID = 8;
    public static final int FLD_APPROVAL2_DATE = 9;
    public static final int FLD_APPROVAL3_DATE = 10;
    public static final int FLD_START_DATE = 11;
    public static final int FLD_END_DATE = 12;
    public static final int FLD_REQUEST_QTY = 13;
    public static final int FLD_TAKEN_QTY = 14;
    
    public static final String[] fieldNames = {
        "LL_APP_ID",
        "SUBMISSION_DATE",
        "APPROVAL_ID",
        "APPROVAL_DATE",
        "EMPLOYEE_ID",
        "BALANCE",
        "DOC_STATUS",
        "APPROVAL2_ID",
        "APPROVAL3_ID",
        "APPROVAL2_DATE",
        "APPROVAL3_DATE",
        "START_DATE",
        "END_DATE",
        "REQUEST_QTY",
        "TAKEN_QTY"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,        
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,        
        TYPE_DATE,        
        TYPE_DATE,        
        TYPE_DATE,       
        TYPE_INT,       
        TYPE_INT       
    };
    
    public static final int FLD_DOC_STATUS_VALID = 0;
    public static final int FLD_DOC_STATUS_NOT_VALID = 1;
    public static final String[] fieldStatusNames = {
        "Valid",
        "Not Valid"
    };    
    
    
    public PstLLAppMain() {
    }
    
    public PstLLAppMain(int i) throws DBException {
        super(new PstLLAppMain());
    }
    
    public PstLLAppMain(String sOid) throws DBException {
        super(new PstLLAppMain(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstLLAppMain(long lOid) throws DBException {
        super(new PstLLAppMain(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_LL_APP_MAIN;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstLLAppMain().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        LLAppMain objLLAppMain = fetchExc(ent.getOID());
        return objLLAppMain.getOID();
    }
    
    public static LLAppMain fetchExc(long oid) throws DBException {
        try {
            LLAppMain objLLAppMain = new LLAppMain();
            PstLLAppMain objPstLLAppMain = new PstLLAppMain(oid);
            objLLAppMain.setOID(oid);
            
            objLLAppMain.setEmployeeId(objPstLLAppMain.getlong(FLD_EMPLOYEE_ID));
            objLLAppMain.setSubmissionDate(objPstLLAppMain.getDate(FLD_SUBMISSION_DATE));
            objLLAppMain.setApprovalId(objPstLLAppMain.getlong(FLD_APPROVAL_ID));
            objLLAppMain.setApprovalDate(objPstLLAppMain.getDate(FLD_APPROVAL_DATE));
            objLLAppMain.setBalance(objPstLLAppMain.getInt(FLD_BALANCE));
            objLLAppMain.setDocumentStatus(objPstLLAppMain.getInt(FLD_DOC_STATUS));
            
            objLLAppMain.setApproval2Id(objPstLLAppMain.getlong(FLD_APPROVAL2_ID));
            objLLAppMain.setApproval3Id(objPstLLAppMain.getlong(FLD_APPROVAL3_ID));
            objLLAppMain.setApproval2Date(objPstLLAppMain.getDate(FLD_APPROVAL2_DATE));
            objLLAppMain.setApproval3Date(objPstLLAppMain.getDate(FLD_APPROVAL3_DATE));
            
            objLLAppMain.setStartDate(objPstLLAppMain.getDate(FLD_START_DATE));
            objLLAppMain.setEndDate(objPstLLAppMain.getDate(FLD_END_DATE));
            
            objLLAppMain.setRequestQty(objPstLLAppMain.getInt(FLD_REQUEST_QTY));
            objLLAppMain.setTakenQty(objPstLLAppMain.getInt(FLD_TAKEN_QTY));
            
            return objLLAppMain;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLAppMain(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((LLAppMain) ent);
    }
    
    public static long updateExc(LLAppMain objLLAppMain) throws DBException {
        try {
            if (objLLAppMain.getOID() != 0) {
                PstLLAppMain objPstLLAppMain = new PstLLAppMain(objLLAppMain.getOID());
                
                objPstLLAppMain.setLong(FLD_EMPLOYEE_ID, objLLAppMain.getEmployeeId());              
                objPstLLAppMain.setDate(FLD_SUBMISSION_DATE, objLLAppMain.getSubmissionDate());              
                objPstLLAppMain.setLong(FLD_APPROVAL_ID, objLLAppMain.getApprovalId());              
                objPstLLAppMain.setDate(FLD_APPROVAL_DATE, objLLAppMain.getApprovalDate());              
                objPstLLAppMain.setInt(FLD_BALANCE, objLLAppMain.getBalance());                     
                objPstLLAppMain.setInt(FLD_DOC_STATUS, objLLAppMain.getDocumentStatus());   
                
                objPstLLAppMain.setLong(FLD_APPROVAL2_ID, objLLAppMain.getApproval2Id());              
                objPstLLAppMain.setLong(FLD_APPROVAL3_ID, objLLAppMain.getApproval3Id());              
                objPstLLAppMain.setDate(FLD_APPROVAL2_DATE, objLLAppMain.getApproval2Date());              
                objPstLLAppMain.setDate(FLD_APPROVAL3_DATE, objLLAppMain.getApproval3Date());              
                
                objPstLLAppMain.setDate(FLD_START_DATE, objLLAppMain.getStartDate());              
                objPstLLAppMain.setDate(FLD_END_DATE, objLLAppMain.getEndDate());  
                
                objPstLLAppMain.setInt(FLD_REQUEST_QTY, objLLAppMain.getRequestQty());              
                objPstLLAppMain.setInt(FLD_TAKEN_QTY, objLLAppMain.getTakenQty());              
                
                objPstLLAppMain.update();
                return objLLAppMain.getOID();                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLAppMain(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstLLAppMain objPstLLAppMain = new PstLLAppMain(oid);
            objPstLLAppMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLAppMain(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((LLAppMain)ent);
    }
    
    public static long insertExc(LLAppMain objLLAppMain) throws DBException {
        try {
            PstLLAppMain objPstLLAppMain = new PstLLAppMain(0);

            objPstLLAppMain.setLong(FLD_EMPLOYEE_ID, objLLAppMain.getEmployeeId());              
            objPstLLAppMain.setDate(FLD_SUBMISSION_DATE, objLLAppMain.getSubmissionDate());              
            objPstLLAppMain.setLong(FLD_APPROVAL_ID, objLLAppMain.getApprovalId());              
            objPstLLAppMain.setDate(FLD_APPROVAL_DATE, objLLAppMain.getApprovalDate());               
            objPstLLAppMain.setInt(FLD_BALANCE, objLLAppMain.getBalance());                        
            objPstLLAppMain.setInt(FLD_DOC_STATUS, objLLAppMain.getDocumentStatus());   
                
            objPstLLAppMain.setLong(FLD_APPROVAL2_ID, objLLAppMain.getApproval2Id());              
            objPstLLAppMain.setLong(FLD_APPROVAL3_ID, objLLAppMain.getApproval3Id());              
            objPstLLAppMain.setDate(FLD_APPROVAL2_DATE, objLLAppMain.getApproval2Date());              
            objPstLLAppMain.setDate(FLD_APPROVAL3_DATE, objLLAppMain.getApproval3Date());            
            
            objPstLLAppMain.setDate(FLD_START_DATE, objLLAppMain.getStartDate());            
            objPstLLAppMain.setDate(FLD_END_DATE, objLLAppMain.getEndDate());            

            objPstLLAppMain.setInt(FLD_REQUEST_QTY, objLLAppMain.getRequestQty());              
            objPstLLAppMain.setInt(FLD_TAKEN_QTY, objLLAppMain.getTakenQty()); 
            
            objPstLLAppMain.insert();
            objLLAppMain.setOID(objPstLLAppMain.getlong(FLD_LL_APP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLAppMain(0), DBException.UNKNOWN);
        }
        return objLLAppMain.getOID();
    }
    
    /**
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_LL_APP_MAIN;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;            
            System.out.println("\tPstLLAppMain sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                LLAppMain objLLAppMain = new LLAppMain();
                resultToObject(rs, objLLAppMain);
                lists.add(objLLAppMain);
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
    
    private static void resultToObject(ResultSet rs, LLAppMain objLLAppMain) {
        try {
            objLLAppMain.setOID(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_LL_APP_ID]));
            objLLAppMain.setEmployeeId(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]));
            objLLAppMain.setSubmissionDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_SUBMISSION_DATE]));
            objLLAppMain.setApprovalId(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID]));
            objLLAppMain.setApprovalDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_DATE]));
            objLLAppMain.setBalance(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_BALANCE]));
            objLLAppMain.setDocumentStatus(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_DOC_STATUS]));
            
            objLLAppMain.setApproval2Id(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL2_ID]));
            objLLAppMain.setApproval3Id(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL3_ID]));
            objLLAppMain.setApproval2Date(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL2_DATE]));
            objLLAppMain.setApproval3Date(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL3_DATE]));
            
            objLLAppMain.setStartDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_START_DATE]));
            objLLAppMain.setEndDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]));
            
            objLLAppMain.setRequestQty(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_REQUEST_QTY]));
            objLLAppMain.setTakenQty(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_TAKEN_QTY]));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstLLAppMain.fieldNames[PstLLAppMain.FLD_LL_APP_ID] + ") FROM " + TBL_LL_APP_MAIN;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;  
            
//            System.out.println("sql : " + sql);
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
}
