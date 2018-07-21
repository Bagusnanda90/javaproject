/*
 * PstDpApplication.java
 *
 * Created on October 21, 2004, 12:05 PM
 */

package com.dimata.harisma.entity.leave;

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
public class PstDPUpload  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_DP_UPLOAD = "hr_dp_upload";
    
    public static final int FLD_DP_UPLOAD_ID    = 0;    
    public static final int FLD_OPNAME_DATE     = 1;
    public static final int FLD_EMPLOYEE_ID     = 2;
    public static final int FLD_DATA_STATUS     = 3;
    public static final int FLD_DP_AQ_DATE      = 4;
    public static final int FLD_DP_NUMBER       = 5;
    public static final int FLD_DP_STOCK_ID     = 6;
    
    
    /*
     `DP_UPLOAD_ID` bigint(20) NOT NULL default '0',
  `EMPLOYEE_ID` bigint(20) default NULL,
  `OPNAME_DATE` datetime default NULL,
  `DP_AQ_DATE` datetime default NULL,
  `DP_NUMBER` int(11) default NULL,
  `DATA_STATUS` int(11) default NULL 
     */
    public static final String[] fieldNames = {
        "DP_UPLOAD_ID",
        "OPNAME_DATE",
        "EMPLOYEE_ID",
        "DATA_STATUS",
        "DP_AQ_DATE",
        "DP_NUMBER",
        "DP_STOCK_ID"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,        
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_LONG
    };
    
    public static final int FLD_DOC_STATUS_PROCESS      = 1;
    public static final int FLD_DOC_STATUS_NOT_PROCESS  = 0;
    public static final String[] fieldStatusNames = {
        "",
        "Process"
    };    
    
    public static final int FLD_VAL_DP_STOCK_ID_EMPTY = 0;
    public static final int FLD_VAL_DP_STOCK_ID_NOT_EMPTY = 1;
    
    public PstDPUpload() {
    }
    
    public PstDPUpload(int i) throws DBException {
        super(new PstDPUpload());
    }
    
    public PstDPUpload(String sOid) throws DBException {
        super(new PstDPUpload(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDPUpload(long lOid) throws DBException {
        super(new PstDPUpload(0));
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
        return TBL_DP_UPLOAD;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstDPUpload().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        DPUpload objDPUpload = fetchExc(ent.getOID());
        return objDPUpload.getOID();
    }
    
    public static DPUpload fetchExc(long oid) throws DBException {
        try {
            DPUpload objDPUpload = new DPUpload();
            PstDPUpload objPstDPUpload = new PstDPUpload(oid);
            objDPUpload.setOID(oid);
            
            objDPUpload.setOpnameDate(objPstDPUpload.getDate(FLD_OPNAME_DATE));
            objDPUpload.setDataStatus(objPstDPUpload.getInt(FLD_DATA_STATUS));
            objDPUpload.setEmployeeId(objPstDPUpload.getlong(FLD_EMPLOYEE_ID));
            objDPUpload.setAcquisitionDate(objPstDPUpload.getDate(FLD_DP_AQ_DATE));
            objDPUpload.setDPNumber(objPstDPUpload.getfloat(FLD_DP_NUMBER));
            objDPUpload.setDpStockId(objPstDPUpload.getlong(FLD_DP_STOCK_ID));
            
            return objDPUpload;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDPUpload(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((DPUpload) ent);
    }
    
    public static long updateExc(DPUpload objDPUpload) throws DBException {
        try {
            if (objDPUpload.getOID() != 0) {
                
                PstDPUpload objPstDPUpload = new PstDPUpload(objDPUpload.getOID());
                objPstDPUpload.setInt(FLD_DATA_STATUS, objDPUpload.getDataStatus());
                objPstDPUpload.setLong(FLD_EMPLOYEE_ID, objDPUpload.getEmployeeId());
                objPstDPUpload.setDate(FLD_OPNAME_DATE, objDPUpload.getOpnameDate());
                objPstDPUpload.setDate(FLD_DP_AQ_DATE, objDPUpload.getAcquisitionDate());
                objPstDPUpload.setFloat(FLD_DP_NUMBER, objDPUpload.getDPNumber());
                objPstDPUpload.setLong(FLD_DP_STOCK_ID, objDPUpload.getDpStockId());
                
                objPstDPUpload.update();
                return objDPUpload.getOID();                
                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDPUpload(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstDPUpload objPstDPUpload = new PstDPUpload(oid);
            objPstDPUpload.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDPUpload(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((DPUpload)ent);
    }
    
    public static long insertExc(DPUpload objDPUpload) throws DBException {
        try {
            PstDPUpload objPstDPUpload = new PstDPUpload(0);

            objPstDPUpload.setInt(FLD_DATA_STATUS, objDPUpload.getDataStatus());
            objPstDPUpload.setLong(FLD_EMPLOYEE_ID, objDPUpload.getEmployeeId());
            objPstDPUpload.setDate(FLD_OPNAME_DATE, objDPUpload.getOpnameDate());
            objPstDPUpload.setDate(FLD_DP_AQ_DATE, objDPUpload.getAcquisitionDate());
            objPstDPUpload.setFloat(FLD_DP_NUMBER, objDPUpload.getDPNumber());
            objPstDPUpload.setLong(FLD_DP_STOCK_ID, objDPUpload.getDpStockId());
            
            objPstDPUpload.insert();
            objDPUpload.setOID(objPstDPUpload.getlong(FLD_DP_UPLOAD_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDPUpload(0), DBException.UNKNOWN);
        }
        return objDPUpload.getOID();
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
            String sql = "SELECT * FROM " + TBL_DP_UPLOAD;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;            
           // System.out.println("PstDPUpload sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DPUpload objDPUpload = new DPUpload();
                resultToObject(rs, objDPUpload);
                lists.add(objDPUpload);
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
    
    private static void resultToObject(ResultSet rs, DPUpload objDPUpload) {
        try {
            objDPUpload.setOID(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]));
            objDPUpload.setDataStatus(rs.getInt(PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS]));
            objDPUpload.setEmployeeId(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID]));
            objDPUpload.setOpnameDate(rs.getDate(PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE]));
            objDPUpload.setAcquisitionDate(rs.getDate(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_AQ_DATE]));
            objDPUpload.setDPNumber(rs.getInt(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_NUMBER]));
            objDPUpload.setDpStockId(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + ") FROM " + TBL_DP_UPLOAD;
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
    
    
    /**
     * @desc mencari data dp upload berdasarkan id employee dan tanggal opname
     */
    public static Vector listByIdAndOpnameDate(int limitStart,int recordToGet,long employeeId, Date opnameDate,String order){
        Vector vDPUpload = new Vector();
        String where = fieldNames[FLD_EMPLOYEE_ID]+"="+employeeId
                +fieldNames[FLD_OPNAME_DATE]+"=\""+Formater.formatDate(opnameDate, "yyyy-MM-dd")+"\"";
        vDPUpload = list(limitStart, recordToGet, where, order);
        return vDPUpload;
    }
  
}
