/*
 * PstLeaveApplicationDetailDetail.java
 *
 * Created on January 28, 2005, 9:33 AM
 */

package com.dimata.harisma.entity.leave;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Vector;
import java.util.Date;

import com.dimata.util.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author  gedhy
 */
public class PstLeaveApplicationDetail  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language 
{
    
    public static final String TBL_LEAVE_APPLICATION_DETAIL = "hr_leave_application_detail";//"HR_LEAVE_APPLICATION_DETAIL";
    
    public static final int FLD_LEAVE_APPLICATION_DETAIL_ID = 0;    
    public static final int FLD_LEAVE_APPLICATION_MAIN_ID = 1;            
    public static final int FLD_LEAVE_TYPE = 2;
    public static final int FLD_TAKEN_DATE = 3;       
    
    public static final String[] fieldNames = {
        "LEAVE_APPLICATION_DETAIL_ID",       
        "LEAVE_APPLICATION_MAIN_ID",
        "LEAVE_TYPE",       
        "TAKEN_DATE"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,        
        TYPE_LONG,
        TYPE_INT,        
        TYPE_DATE
    };
    
    public static final int LEAVE_APPLICATION_AL = 0;    
    public static final int LEAVE_APPLICATION_LL = 1;
    public static final int LEAVE_APPLICATION_MATERNITY = 2;
    public static final int LEAVE_APPLICATION_DC = 3;
    public static final int LEAVE_APPLICATION_UNPAID = 4;
    public static final int LEAVE_APPLICATION_SPECIAL = 5;
    public static final String[] strApplicationType = {
        "Annual Leave",       
        "Long Leave",
        "Maternity Leave",       
        "Sick Leave",       
        "Unpaid Leave",       
        "Special/Other Leave"    
    };
    
    public PstLeaveApplicationDetail() {
    }
    
    public PstLeaveApplicationDetail(int i) throws DBException {
        super(new PstLeaveApplicationDetail());
    }
    
    public PstLeaveApplicationDetail(String sOid) throws DBException {
        super(new PstLeaveApplicationDetail());
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstLeaveApplicationDetail(long lOid) throws DBException {
        super(new PstLeaveApplicationDetail());
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
        return TBL_LEAVE_APPLICATION_DETAIL;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstLeaveApplicationDetail().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        LeaveApplicationDetail objLeaveApplicationDetail = fetchExc(ent.getOID());
        return objLeaveApplicationDetail.getOID();
    }
    
    public static LeaveApplicationDetail fetchExc(long oid) throws DBException 
    {
        try 
        {
            LeaveApplicationDetail objLeaveApplicationDetail = new LeaveApplicationDetail();
            PstLeaveApplicationDetail objPstLeaveApplicationDetail = new PstLeaveApplicationDetail(oid);

            objLeaveApplicationDetail.setOID(oid);            
            objLeaveApplicationDetail.setLeaveMainOid(objPstLeaveApplicationDetail.getlong(FLD_LEAVE_APPLICATION_MAIN_ID));            
            objLeaveApplicationDetail.setLeaveType(objPstLeaveApplicationDetail.getInt(FLD_LEAVE_TYPE));
            objLeaveApplicationDetail.setTakenDate(objPstLeaveApplicationDetail.getDate(FLD_TAKEN_DATE));                        
            
            return objLeaveApplicationDetail;
        }
        catch (DBException dbe) 
        {
            throw dbe;
        }
        catch (Exception e) 
        {
            throw new DBException(new PstLeaveApplicationDetail(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception 
    {
        return updateExc((LeaveApplicationDetail) ent);
    }
    
    public static long updateExc(LeaveApplicationDetail objLeaveApplicationDetail) throws DBException 
    {
        try 
        {
            if (objLeaveApplicationDetail.getOID() != 0) 
            {
                PstLeaveApplicationDetail objPstLeaveApplicationDetail = new PstLeaveApplicationDetail(objLeaveApplicationDetail.getOID());                
                
                objPstLeaveApplicationDetail.setLong(FLD_LEAVE_APPLICATION_MAIN_ID, objLeaveApplicationDetail.getLeaveMainOid());
                objPstLeaveApplicationDetail.setInt(FLD_LEAVE_TYPE, objLeaveApplicationDetail.getLeaveType());
                objPstLeaveApplicationDetail.setDate(FLD_TAKEN_DATE, objLeaveApplicationDetail.getTakenDate());  
                
                objPstLeaveApplicationDetail.update();
                return objLeaveApplicationDetail.getOID();                
            }
        }
        catch (DBException dbe) 
        {
            throw dbe;
        }
        catch (Exception e) 
        {
            throw new DBException(new PstLeaveApplicationDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstLeaveApplicationDetail objPstLeaveApplicationDetail = new PstLeaveApplicationDetail(oid);
            objPstLeaveApplicationDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveApplicationDetail(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((LeaveApplicationDetail)ent);
    }
    
    public static long insertExc(LeaveApplicationDetail objLeaveApplicationDetail) throws DBException 
    {
        try 
        {
            PstLeaveApplicationDetail objPstLeaveApplicationDetail = new PstLeaveApplicationDetail(0);
        
            objPstLeaveApplicationDetail.setLong(FLD_LEAVE_APPLICATION_MAIN_ID, objLeaveApplicationDetail.getLeaveMainOid());
            objPstLeaveApplicationDetail.setInt(FLD_LEAVE_TYPE, objLeaveApplicationDetail.getLeaveType());
            objPstLeaveApplicationDetail.setDate(FLD_TAKEN_DATE, objLeaveApplicationDetail.getTakenDate());              
            
            objPstLeaveApplicationDetail.insert();
            objLeaveApplicationDetail.setOID(objPstLeaveApplicationDetail.getlong(FLD_LEAVE_APPLICATION_DETAIL_ID));
        }
        catch (DBException dbe) 
        {
            throw dbe;
        }
        catch (Exception e) 
        {
            throw new DBException(new PstLeaveApplicationDetail(0), DBException.UNKNOWN);
        }
        return objLeaveApplicationDetail.getOID();
    }
    
    /**
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order)
    {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try 
        {
            String sql = "SELECT * FROM " + TBL_LEAVE_APPLICATION_DETAIL;
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
            while(rs.next()) 
            {
                LeaveApplicationDetail objLeaveApplicationDetail = new LeaveApplicationDetail();
                resultToObject(rs, objLeaveApplicationDetail);
                lists.add(objLeaveApplicationDetail);
            }
            rs.close();
            return lists;            
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        finally 
        {
            DBResultSet.close(dbrs);
        }
        return new Vector();   
    }
    
    private static void resultToObject(ResultSet rs, LeaveApplicationDetail objLeaveApplicationDetail) 
    {
        try 
        {
            objLeaveApplicationDetail.setOID(rs.getLong(PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_DETAIL_ID]));            
            objLeaveApplicationDetail.setLeaveMainOid(rs.getLong(PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID]));
            objLeaveApplicationDetail.setLeaveType(rs.getInt(PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_TYPE]));
            objLeaveApplicationDetail.setTakenDate(rs.getDate(PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_TAKEN_DATE]));  
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }  
    
    
    /** 
     * generate new Leave Application Detail     
     * @param employeeId
     * @param submissionDate
     * @param takenDate
     * @param dpStockOid
     * @return
     */    
    public static long generateNewLeaveApplicationDetail(long lLeaveAppMainOid, int iLeaveType, Date dTakenDate)
    {
        long lResult = 0;        
        
        try
        {
            LeaveApplicationDetail objLeaveApplicationDetailNew = new LeaveApplicationDetail();
            objLeaveApplicationDetailNew.setLeaveMainOid(lLeaveAppMainOid);
            objLeaveApplicationDetailNew.setLeaveType(iLeaveType);
            objLeaveApplicationDetailNew.setTakenDate(dTakenDate);
            lResult = PstLeaveApplicationDetail.insertExc(objLeaveApplicationDetailNew);  
        }
        catch(Exception e)
        {
            System.out.println("Exc when generateNewLeaveApplicationDetail : " + e.toString());
        }
        
        return lResult;
    }
    
    
    /**
     * @param oidLeaveAppMain
     * @return
     */    
    public static int deleteByApplicationMain(long oidLeaveAppMain)
    {
        int result = 0;        
        try 
        {
            String sql = "DELETE FROM " + TBL_LEAVE_APPLICATION_DETAIL +
                         " WHERE " + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID] +
                         " = " + oidLeaveAppMain;
            System.out.println("sql : " + sql);
            result = DBHandler.execUpdate(sql);            
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        finally 
        {            
        }
        return result;   
    }
    
    
    /**
     * @param oidLeaveAppMain
     * @return
     */    
    public static Vector listDetailByApplicationMain(long oidLeaveAppMain)
    {
        Vector result = new Vector(1,1);         
        
        String whereClause = PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID] +
                             " =  " + oidLeaveAppMain;
        String orderBy = PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_TYPE] + 
                         ", " + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_TAKEN_DATE];

        result = list(0, 0, whereClause, orderBy);
        
        return result;   
    }    
    
    
    /**
     * @param oidLeaveAppMain
     * @return
     */    
    public static LeaveApplicationDetail getLeaveApplicationDetail(long lEmployeeOid, Date dTakenDate)
    {        
        DBResultSet dbrs = null;
        
        String sTakenDate = "";        
        if(sTakenDate!=null)
        {
            sTakenDate = Formater.formatDate(dTakenDate, "yyyy-MM-dd");            
        }
        
        try 
        {
            String sql = "SELECT DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_DETAIL_ID] + 
                         ", " + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID] + 
                         ", " + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_TYPE] + 
                         ", " + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_TAKEN_DATE] +                          
                         " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS MAIN " + 
                         " INNER JOIN " + PstLeaveApplicationDetail.TBL_LEAVE_APPLICATION_DETAIL + " AS DETAIL " + 
                         " ON MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + 
                         " = DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID] +
                         " WHERE MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] +
                         " = " + lEmployeeOid +                          
                         " AND DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_TAKEN_DATE] + 
                         " = \"" + sTakenDate + "\"" +
                         " AND MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] +
                         " = " + PstLeaveApplication.FLD_DOC_STATUS_VALID;            
            
//            System.out.println("getLeaveApplicationDetail sql : " + sql);  
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) 
            {
                LeaveApplicationDetail objLeaveApplicationDetail = new LeaveApplicationDetail();
                objLeaveApplicationDetail.setOID(rs.getLong(1));
                objLeaveApplicationDetail.setLeaveMainOid(rs.getLong(2));
                objLeaveApplicationDetail.setLeaveType(rs.getInt(3));
                objLeaveApplicationDetail.setTakenDate(rs.getDate(4));
                
                return objLeaveApplicationDetail;   
            } 
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        finally 
        {    
            DBResultSet.close(dbrs);            
        }        
        return new LeaveApplicationDetail();
    }    
}
