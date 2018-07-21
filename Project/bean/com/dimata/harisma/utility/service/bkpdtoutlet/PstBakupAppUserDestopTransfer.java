/*
 * PstBakupAppUserDestopTransfer.java
 *
 * Created on April 3, 2002, 9:29 AM
 */

package com.dimata.harisma.utility.service.bkpdtoutlet;

/**
 *
 * @author  ktanjana
 * @version
 */

import com.dimata.harisma.utility.harisma.machine.transferdataemployee.*;
import com.dimata.harisma.entity.admin.*;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_Persintent;
import java.sql.*;
import java.util.*;
import java.util.Date;


//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;

public class PstBakupAppUserDestopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_Persintent  {
    
    public static final String TBL_APP_USER = "hr_app_user";
    public static final int FLD_USER_ID			= 0;
    public static final int FLD_LOGIN_ID		= 1;
    public static final int FLD_PASSWORD		= 2;
    public static final int FLD_FULL_NAME		= 3;
    public static final int FLD_EMAIL			= 4;
    public static final int FLD_DESCRIPTION		= 5;
    public static final int FLD_REG_DATE		= 6;
    public static final int FLD_UPDATE_DATE		= 7;
    public static final int FLD_USER_STATUS		= 8;
    public static final int FLD_LAST_LOGIN_DATE         = 9;
    public static final int FLD_LAST_LOGIN_IP		= 10;
    public static final int FLD_EMPLOYEE_ID		= 11;
    
    public static  final String[] fieldNames = {
        "USER_ID", "LOGIN_ID", "PASSWORD", "FULL_NAME", "EMAIL", "DESCRIPTION"
        ,"REG_DATE", "UPDATE_DATE", "USER_STATUS", "LAST_LOGIN_DATE", "LAST_LOGIN_IP"
        ,"EMPLOYEE_ID"
    } ;
    
    public static int[] fieldTypes = {
        TYPE_PK + TYPE_LONG + TYPE_ID, TYPE_STRING,   TYPE_STRING,  TYPE_STRING,  TYPE_STRING,  TYPE_STRING,
        TYPE_DATE, TYPE_DATE, TYPE_INT, TYPE_DATE, TYPE_STRING, TYPE_LONG
    };
    
    /** Creates new PstBakupAppUserDestopTransfer */
    public PstBakupAppUserDestopTransfer() {
    }
    
    public PstBakupAppUserDestopTransfer(int i) throws DBException {
        super(new PstBakupAppUserDestopTransfer());
    }
    
    
    public PstBakupAppUserDestopTransfer(String sOid) throws DBException {
        super(new PstBakupAppUserDestopTransfer(0));
        if(!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    
    public PstBakupAppUserDestopTransfer(long lOid) throws DBException {
        super(new PstBakupAppUserDestopTransfer(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        }catch(Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        
        if(!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
        
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_APP_USER;
    }
    
    public String getPersistentName() {
        return new PstBakupAppUserDestopTransfer().getClass().getName();
    }
    
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    
    public long delete(Entity ent) {
        return delete((AppUser) ent);
    }
    
    public long insert(Entity ent){
        return 0;
    }
    
    
    public long update(Entity ent) {
        return 0;
        //return update((AppUser) ent);
    }
    
    public long fetch(Entity ent) {
       
        return 0;
    }
    
    
  
    public static String insert(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException  {
        String sql="";
       try{
        PstBakupAppUserDestopTransfer pstObj = new PstBakupAppUserDestopTransfer(0);
        
        pstObj.setString(FLD_LOGIN_ID, tabelEmployeeOutletTransferData.getLoginId());
        pstObj.setString(FLD_PASSWORD, tabelEmployeeOutletTransferData.getPassword());
        
        pstObj.setDate(FLD_REG_DATE, new Date());//entObj.getRegDate());
        pstObj.setDate(FLD_UPDATE_DATE, new Date());//entObj.getUpdateDate());
        pstObj.setLong(FLD_EMPLOYEE_ID, tabelEmployeeOutletTransferData.getEmployeeId());
        //pstObj.setDate(FLD_LAST_LOGIN_DATE, entObj.getLastLoginDate());
        //pstObj.setString(FLD_LAST_LOGIN_IP, entObj.getLastLoginIp());
        
        //pstObj.insert(tabelEmployeeOutletTransferData.getUserId());
        sql = pstObj.SyntacInsert(tabelEmployeeOutletTransferData.getUserId());
        //entObj.setOID(pstObj.getlong(FLD_USER_ID));
        //return tabelEmployeeOutletTransferData.getUserId();
       }
        catch(DBException e) {
            System.out.println(e);
        }
        return sql;

    }
    
    
    public static long update(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        PstBakupAppUserDestopTransfer pstObj = null;
        if(tabelEmployeeOutletTransferData.getUserId()!=0){
        pstObj = new PstBakupAppUserDestopTransfer(tabelEmployeeOutletTransferData.getUserId());
        pstObj.setString(FLD_LOGIN_ID, tabelEmployeeOutletTransferData.getLoginId());
        pstObj.setString(FLD_PASSWORD, tabelEmployeeOutletTransferData.getPassword());
        
        pstObj.setDate(FLD_REG_DATE, new Date());//entObj.getRegDate());
        pstObj.setDate(FLD_UPDATE_DATE, new Date());//entObj.getUpdateDate());
        pstObj.setLong(FLD_EMPLOYEE_ID, tabelEmployeeOutletTransferData.getEmployeeId());
        }
        //pstObj.setDate(FLD_LAST_LOGIN_DATE, entObj.getLastLoginDate());
        //pstObj.setString(FLD_LAST_LOGIN_IP, entObj.getLastLoginIp());
        return tabelEmployeeOutletTransferData.getUserId();
    }
    
    
    public static long delete(long oid) {
        try {
            PstBakupAppUserDestopTransfer pstObj = new PstBakupAppUserDestopTransfer(oid);
            pstObj.delete();
            return oid;
        }catch(Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs=null;
        try{
            int count = 0;
            String sql = " SELECT COUNT("+fieldNames[FLD_USER_ID] +") AS NRCOUNT" +
            " FROM " + TBL_APP_USER;
            
            
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            
            //System.out.println(sql);
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        }
        catch (Exception exc){
            System.out.println("getCount "+ exc);
            return 0;
        }
        finally{
            DBResultSet.close(dbrs);
        }        
        
    }
    
    public static Vector listPartObj(int start , int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {
            String sql = "SELECT " +  fieldNames[FLD_USER_ID]
            + ", " + fieldNames[FLD_LOGIN_ID]
            + ", " + fieldNames[FLD_FULL_NAME]
            + ", " + fieldNames[FLD_EMAIL]
            + ", " + fieldNames[FLD_USER_STATUS]
            + ", " + fieldNames[FLD_EMPLOYEE_ID]
            + " FROM " + TBL_APP_USER;
            
            
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            
            
            if((start == 0)&&(recordToGet == 0))
                sql = sql + "" ;  //nothing to do
            else
                sql = sql + " LIMIT " + start + ","+ recordToGet ;
            
            //System.out.println(sql);
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next()) {
                AppUser appUser = new AppUser();
                resultToObject(rs, appUser);
                lists.add(appUser);
            }
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }
        finally{
            DBResultSet.close(dbrs);
        }        
        return new Vector();
    }
    
    private static void resultToObject(ResultSet rs, AppUser entObj) {
        try {
            entObj.setOID(rs.getLong(fieldNames[FLD_USER_ID]));
            entObj.setLoginId(rs.getString(fieldNames[FLD_LOGIN_ID]));
            entObj.setFullName(rs.getString(fieldNames[FLD_FULL_NAME]));
            entObj.setEmail(rs.getString(fieldNames[FLD_EMAIL]));
            entObj.setUserStatus(rs.getInt(fieldNames[FLD_USER_STATUS]));
            entObj.setEmployeeId(rs.getLong(fieldNames[FLD_EMPLOYEE_ID]));
            
        }catch(Exception e){
            System.out.println("resultToObject() appuser -> : " + e.toString());
        }
    }
    
    public static Hashtable<String,Boolean> hashUsersdhAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashUsersdhAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_USER_ID]+" FROM " + TBL_APP_USER;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashUsersdhAda.put(""+rs.getLong(fieldNames[FLD_USER_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashUsersdhAda;
        }
        
    }
    
}
