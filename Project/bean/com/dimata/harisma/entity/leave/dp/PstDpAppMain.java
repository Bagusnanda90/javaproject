/*
 * PstDpApplication.java
 *
 * Created on October 21, 2004, 12:05 PM
 */

package com.dimata.harisma.entity.leave.dp;

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
public class PstDpAppMain  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_DP_APP_MAIN = "hr_dp_app";
    
    public static final int FLD_DP_APP_ID = 0;    
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
    
    public static final String[] fieldNames = {
        "DP_APP_ID",
        "SUBMISSION_DATE",
        "APPROVAL_ID",
        "APPROVAL_DATE",
        "EMPLOYEE_ID",
        "BALANCE",
        "DOC_STATUS",
        "APPROVAL2_ID",
        "APPROVAL3_ID",
        "APPROVAL2_DATE",
        "APPROVAL3_DATE"
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
        TYPE_DATE        
    };
    
    public static final int FLD_DOC_STATUS_VALID = 0;
    public static final int FLD_DOC_STATUS_NOT_VALID = 1;
    public static final String[] fieldStatusNames = {
        "Valid",
        "Not Valid"
    };    
    
    
    public PstDpAppMain() {
    }
    
    public PstDpAppMain(int i) throws DBException {
        super(new PstDpAppMain());
    }
    
    public PstDpAppMain(String sOid) throws DBException {
        super(new PstDpAppMain(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDpAppMain(long lOid) throws DBException {
        super(new PstDpAppMain(0));
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
        return TBL_DP_APP_MAIN;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstDpAppMain().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        DpAppMain objDpAppMain = fetchExc(ent.getOID());
        return objDpAppMain.getOID();
    }
    
    public static DpAppMain fetchExc(long oid) throws DBException {
        try {
            DpAppMain objDpAppMain = new DpAppMain();
            PstDpAppMain objPstDpAppMain = new PstDpAppMain(oid);
            objDpAppMain.setOID(oid);
            
            objDpAppMain.setEmployeeId(objPstDpAppMain.getlong(FLD_EMPLOYEE_ID));
            objDpAppMain.setSubmissionDate(objPstDpAppMain.getDate(FLD_SUBMISSION_DATE));
            objDpAppMain.setApprovalId(objPstDpAppMain.getlong(FLD_APPROVAL_ID));
            objDpAppMain.setApprovalDate(objPstDpAppMain.getDate(FLD_APPROVAL_DATE));
            objDpAppMain.setBalance(objPstDpAppMain.getInt(FLD_BALANCE));
            objDpAppMain.setDocumentStatus(objPstDpAppMain.getInt(FLD_DOC_STATUS));
            
            objDpAppMain.setApproval2Id(objPstDpAppMain.getlong(FLD_APPROVAL2_ID));
            objDpAppMain.setApproval3Id(objPstDpAppMain.getlong(FLD_APPROVAL3_ID));
            objDpAppMain.setApproval2Date(objPstDpAppMain.getDate(FLD_APPROVAL2_DATE));
            objDpAppMain.setApproval3Date(objPstDpAppMain.getDate(FLD_APPROVAL3_DATE));
            
            return objDpAppMain;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppMain(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((DpAppMain) ent);
    }
    
    public static long updateExc(DpAppMain objDpAppMain) throws DBException {
        try {
            if (objDpAppMain.getOID() != 0) {
                PstDpAppMain objPstDpAppMain = new PstDpAppMain(objDpAppMain.getOID());
                
                objPstDpAppMain.setLong(FLD_EMPLOYEE_ID, objDpAppMain.getEmployeeId());              
                objPstDpAppMain.setDate(FLD_SUBMISSION_DATE, objDpAppMain.getSubmissionDate());              
                objPstDpAppMain.setLong(FLD_APPROVAL_ID, objDpAppMain.getApprovalId());              
                objPstDpAppMain.setDate(FLD_APPROVAL_DATE, objDpAppMain.getApprovalDate());              
                objPstDpAppMain.setInt(FLD_BALANCE, objDpAppMain.getBalance());                     
                objPstDpAppMain.setInt(FLD_DOC_STATUS, objDpAppMain.getDocumentStatus());   
                
                objPstDpAppMain.setLong(FLD_APPROVAL2_ID, objDpAppMain.getApproval2Id());              
                objPstDpAppMain.setLong(FLD_APPROVAL3_ID, objDpAppMain.getApproval3Id());              
                objPstDpAppMain.setDate(FLD_APPROVAL2_DATE, objDpAppMain.getApproval2Date());              
                objPstDpAppMain.setDate(FLD_APPROVAL3_DATE, objDpAppMain.getApproval3Date());              
                
                objPstDpAppMain.update();
                return objDpAppMain.getOID();                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppMain(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstDpAppMain objPstDpAppMain = new PstDpAppMain(oid);
            objPstDpAppMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppMain(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((DpAppMain)ent);
    }
    
    public static long insertExc(DpAppMain objDpAppMain) throws DBException {
        try {
            PstDpAppMain objPstDpAppMain = new PstDpAppMain(0);

            objPstDpAppMain.setLong(FLD_EMPLOYEE_ID, objDpAppMain.getEmployeeId());              
            objPstDpAppMain.setDate(FLD_SUBMISSION_DATE, objDpAppMain.getSubmissionDate());              
            objPstDpAppMain.setLong(FLD_APPROVAL_ID, objDpAppMain.getApprovalId());              
            objPstDpAppMain.setDate(FLD_APPROVAL_DATE, objDpAppMain.getApprovalDate());               
            objPstDpAppMain.setInt(FLD_BALANCE, objDpAppMain.getBalance());                        
            objPstDpAppMain.setInt(FLD_DOC_STATUS, objDpAppMain.getDocumentStatus());   
                
            objPstDpAppMain.setLong(FLD_APPROVAL2_ID, objDpAppMain.getApproval2Id());              
            objPstDpAppMain.setLong(FLD_APPROVAL3_ID, objDpAppMain.getApproval3Id());              
            objPstDpAppMain.setDate(FLD_APPROVAL2_DATE, objDpAppMain.getApproval2Date());              
            objPstDpAppMain.setDate(FLD_APPROVAL3_DATE, objDpAppMain.getApproval3Date());            

            objPstDpAppMain.insert();
            objDpAppMain.setOID(objPstDpAppMain.getlong(FLD_DP_APP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppMain(0), DBException.UNKNOWN);
        }
        return objDpAppMain.getOID();
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
            String sql = "SELECT * FROM " + TBL_DP_APP_MAIN;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;            
           // System.out.println("PstDpAppMain sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DpAppMain objDpAppMain = new DpAppMain();
                resultToObject(rs, objDpAppMain);
                lists.add(objDpAppMain);
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
    
    private static void resultToObject(ResultSet rs, DpAppMain objDpAppMain) {
        try {
            objDpAppMain.setOID(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]));
            objDpAppMain.setEmployeeId(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]));
            objDpAppMain.setSubmissionDate(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE]));
            objDpAppMain.setApprovalId(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID]));
            objDpAppMain.setApprovalDate(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_DATE]));
            objDpAppMain.setBalance(rs.getInt(PstDpAppMain.fieldNames[PstDpAppMain.FLD_BALANCE]));
            objDpAppMain.setDocumentStatus(rs.getInt(PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]));
            
            objDpAppMain.setApproval2Id(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_ID]));
            objDpAppMain.setApproval3Id(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_ID]));
            objDpAppMain.setApproval2Date(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_DATE]));
            objDpAppMain.setApproval3Date(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_DATE]));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID] + ") FROM " + TBL_DP_APP_MAIN;
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
