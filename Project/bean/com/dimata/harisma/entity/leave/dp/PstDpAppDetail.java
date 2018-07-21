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
public class PstDpAppDetail  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_DP_APP_DETAIL = "hr_dp_app_detail";
    
    public static final int FLD_DP_APP_DETAIL_ID = 0;    
    public static final int FLD_DP_APP_ID = 1;    
    public static final int FLD_TAKEN_DATE = 2;
    public static final int FLD_DP_ID = 3;
    public static final int FLD_STATUS = 4;
    
    public static final String[] fieldNames = {
        "DP_APP_DETAIL_ID",
        "DP_APP_ID",
        "TAKEN_DATE",
        "DP_ID",
        "STATUS"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT
    };
     
    public static final int STATUS_NOT_PROCESSED = 0;
    public static final int STATUS_PROCESSED = 1;
    
    public PstDpAppDetail() {
    }
    
    public PstDpAppDetail(int i) throws DBException {
        super(new PstDpAppDetail());
    }
    
    public PstDpAppDetail(String sOid) throws DBException {
        super(new PstDpAppDetail(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDpAppDetail(long lOid) throws DBException {
        super(new PstDpAppDetail(0));
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
        return TBL_DP_APP_DETAIL;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstDpAppDetail().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        DpAppDetail objDpAppDetail = fetchExc(ent.getOID());
        return objDpAppDetail.getOID();
    }
    
    public static DpAppDetail fetchExc(long oid) throws DBException {
        try {
            DpAppDetail objDpAppDetail = new DpAppDetail();
            PstDpAppDetail objPstDpAppDetail = new PstDpAppDetail(oid);
            objDpAppDetail.setOID(oid);
            
            objDpAppDetail.setDpAppMainId(objPstDpAppDetail.getlong(FLD_DP_APP_ID));
            objDpAppDetail.setTakenDate(objPstDpAppDetail.getDate(FLD_TAKEN_DATE));
            objDpAppDetail.setDpId(objPstDpAppDetail.getlong(FLD_DP_ID));
            objDpAppDetail.setStatus(objPstDpAppDetail.getInt(FLD_STATUS));
            
            return objDpAppDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppDetail(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((DpAppDetail) ent);
    }
    
    public static long updateExc(DpAppDetail objDpAppDetail) throws DBException {
        try {
            if (objDpAppDetail.getOID() != 0) {
                PstDpAppDetail objPstDpAppDetail = new PstDpAppDetail(objDpAppDetail.getOID());
                
                objPstDpAppDetail.setLong(FLD_DP_APP_ID, objDpAppDetail.getDpAppMainId());        
                objPstDpAppDetail.setDate(FLD_TAKEN_DATE, objDpAppDetail.getTakenDate());          
                objPstDpAppDetail.setLong(FLD_DP_ID, objDpAppDetail.getDpId());        
                objPstDpAppDetail.setInt(FLD_STATUS, objDpAppDetail.getStatus());        
                
                objPstDpAppDetail.update();
                return objDpAppDetail.getOID();                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstDpAppDetail objPstDpAppDetail = new PstDpAppDetail(oid);
            objPstDpAppDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppDetail(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((DpAppDetail)ent);
    }
    
    public static long insertExc(DpAppDetail objDpAppDetail) throws DBException {
        try {
            PstDpAppDetail objPstDpAppDetail = new PstDpAppDetail(0);

            objPstDpAppDetail.setLong(FLD_DP_APP_ID, objDpAppDetail.getDpAppMainId());        
            objPstDpAppDetail.setDate(FLD_TAKEN_DATE, objDpAppDetail.getTakenDate());   
            objPstDpAppDetail.setLong(FLD_DP_ID, objDpAppDetail.getDpId());      
            objPstDpAppDetail.setInt(FLD_STATUS, objDpAppDetail.getStatus());      

            objPstDpAppDetail.insert();
            objDpAppDetail.setOID(objPstDpAppDetail.getlong(FLD_DP_APP_DETAIL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpAppDetail(0), DBException.UNKNOWN);
        }
        return objDpAppDetail.getOID();
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
            String sql = "SELECT * FROM " + TBL_DP_APP_DETAIL;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;            
         //   System.out.println("PstDpAppDetail sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DpAppDetail objDpAppDetail = new DpAppDetail();
                resultToObject(rs, objDpAppDetail);
                lists.add(objDpAppDetail);
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
    
    private static void resultToObject(ResultSet rs, DpAppDetail objDpAppDetail) {
        try {
            objDpAppDetail.setOID(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID]));
            objDpAppDetail.setDpAppMainId(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]));
            objDpAppDetail.setTakenDate(rs.getDate(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]));
            objDpAppDetail.setDpId(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_ID]));
            objDpAppDetail.setStatus(rs.getInt(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_STATUS]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID] + ") FROM " + TBL_DP_APP_DETAIL;
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
