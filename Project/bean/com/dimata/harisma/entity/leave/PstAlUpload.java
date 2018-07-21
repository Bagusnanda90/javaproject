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
public class PstAlUpload  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_AL_UPLOAD = "hr_al_upload";
    
    public static final int FLD_AL_UPLOAD_ID = 0;    
    public static final int FLD_STOCK_ID = 1;     
    public static final int FLD_OPNAME_DATE = 2;
    public static final int FLD_EMPLOYEE_ID = 3;
    public static final int FLD_LAST_PER_TO_CLEAR = 4;
    public static final int FLD_CURR_PERIOD_TAKEN = 5;
    public static final int FLD_DATA_STATUS = 6;
    public static final int FLD_NEW_AL = 7;
    public static final int FLD_NOTE = 8;
    public static final int FLD_NEW_QTY = 9;
        
    
    public static final String[] fieldNames = {
        "AL_UPLOAD_ID", //0
        "STOCK_ID",
        "OPNAME_DATE",
        "EMPLOYEE_ID",
        "LAST_PER_TO_CLEAR",
        "CURR_PERIOD_TAKEN", //5
        "DATA_STATUS",
        "NEW_AL",
        "NOTE",        
        "NEW_QTY"       
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, //0
        TYPE_LONG + TYPE_FK,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_FLOAT,        
        TYPE_FLOAT, //5
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_FLOAT
    };
    
    public static final int FLD_DOC_STATUS_PROCESS = 1;
    public static final int FLD_DOC_STATUS_NOT_PROCESS = 0;
    public static final String[] fieldStatusNames = {
        "",
        "Process"
    };    
    
    
    public PstAlUpload() {
    }
    
    public PstAlUpload(int i) throws DBException {
        super(new PstAlUpload());
    }
    
    public PstAlUpload(String sOid) throws DBException {
        super(new PstAlUpload(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstAlUpload(long lOid) throws DBException {
        super(new PstAlUpload(0));
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
        return TBL_AL_UPLOAD;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstAlUpload().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        AlUpload objAlUpload = fetchExc(ent.getOID());
        return objAlUpload.getOID();
    }
    
    public static AlUpload fetchExc(long oid) throws DBException {
        try {
            AlUpload objAlUpload = new AlUpload();
            PstAlUpload objPstAlUpload = new PstAlUpload(oid);
            objAlUpload.setOID(oid);
            
            objAlUpload.setCurrPerTaken(objPstAlUpload.getfloat(FLD_CURR_PERIOD_TAKEN));
            objAlUpload.setStockId(objPstAlUpload.getInt(FLD_STOCK_ID));
            objAlUpload.setDataStatus(objPstAlUpload.getInt(FLD_DATA_STATUS));
            objAlUpload.setEmployeeId(objPstAlUpload.getlong(FLD_EMPLOYEE_ID));
            objAlUpload.setLastPerToClear(objPstAlUpload.getfloat(FLD_LAST_PER_TO_CLEAR));
            objAlUpload.setOpnameDate(objPstAlUpload.getDate(FLD_OPNAME_DATE));
            objAlUpload.setNewAl(objPstAlUpload.getfloat(FLD_NEW_AL));            
            objAlUpload.setNote(objPstAlUpload.getString(FLD_NOTE));
            objAlUpload.setNewQty(objPstAlUpload.getfloat(FLD_NEW_QTY));
            
            return objAlUpload;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlUpload(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((AlUpload) ent);
    }
    
    public static long updateExc(AlUpload objAlUpload) throws DBException {
        try {
            if (objAlUpload.getOID() != 0) {
                PstAlUpload objPstAlUpload = new PstAlUpload(objAlUpload.getOID());
                
                objPstAlUpload.setFloat(FLD_CURR_PERIOD_TAKEN, objAlUpload.getCurrPerTaken());              
                objPstAlUpload.setInt(FLD_DATA_STATUS, objAlUpload.getDataStatus());              
                objPstAlUpload.setFloat(FLD_LAST_PER_TO_CLEAR, objAlUpload.getLastPerToClear());              
                objPstAlUpload.setLong(FLD_EMPLOYEE_ID, objAlUpload.getEmployeeId());              
                objPstAlUpload.setDate(FLD_OPNAME_DATE, objAlUpload.getOpnameDate());              
                objPstAlUpload.setFloat(FLD_NEW_AL, objAlUpload.getNewAl());
                objPstAlUpload.setLong(FLD_STOCK_ID, objAlUpload.getStockId());
                objPstAlUpload.setString(FLD_NOTE, objAlUpload.getNote());
                objPstAlUpload.setFloat(FLD_NEW_QTY, objAlUpload.getNewQty());
                
                objPstAlUpload.update();
                return objAlUpload.getOID();                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlUpload(0), DBException.UNKNOWN);
        }
        return 0;
    }
    public synchronized static void updateNote(String note,long oidUpload) {
        try {
            String sql = " UPDATE "+PstAlUpload.TBL_AL_UPLOAD 
                         + " SET "+PstAlUpload.fieldNames[PstAlUpload.FLD_NOTE]+ "=\""+note+"\""
                         + " WHERE "+PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID]+"="+oidUpload;
                    
            int i = DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

    }
    //create by satrya 2013-01-04
    /**
     * 
     * @param alUpload 
     */
    public synchronized static void updateDataStatusByEmpId(long empId) {
        try {
            String sql = " UPDATE "+PstAlUpload.TBL_AL_UPLOAD 
                         + " SET "+PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS]+ "="+PstAlUpload.FLD_DOC_STATUS_PROCESS
                         + " WHERE "+PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]+"="+empId;
                    
            int i = DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

    }
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstAlUpload objPstAlUpload = new PstAlUpload(oid);
            objPstAlUpload.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlUpload(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((AlUpload)ent);
    }
    
    public static long insertExc(AlUpload objAlUpload) throws DBException {
        try {
            PstAlUpload objPstAlUpload = new PstAlUpload(0);

            objPstAlUpload.setFloat(FLD_CURR_PERIOD_TAKEN, objAlUpload.getCurrPerTaken());              
            objPstAlUpload.setInt(FLD_DATA_STATUS, objAlUpload.getDataStatus());              
            objPstAlUpload.setFloat(FLD_LAST_PER_TO_CLEAR, objAlUpload.getLastPerToClear());              
            objPstAlUpload.setLong(FLD_EMPLOYEE_ID, objAlUpload.getEmployeeId());              
            objPstAlUpload.setDate(FLD_OPNAME_DATE, objAlUpload.getOpnameDate());              
            objPstAlUpload.setFloat(FLD_NEW_AL, objAlUpload.getNewAl());
            objPstAlUpload.setLong(FLD_STOCK_ID, objAlUpload.getStockId());
            objPstAlUpload.setString(FLD_NOTE, objAlUpload.getNote());
            objPstAlUpload.setFloat(FLD_NEW_QTY, objAlUpload.getNewQty());

            objPstAlUpload.insert();
            objAlUpload.setOID(objPstAlUpload.getlong(FLD_AL_UPLOAD_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlUpload(0), DBException.UNKNOWN);
        }
        return objAlUpload.getOID();
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
            String sql = "SELECT * FROM " + TBL_AL_UPLOAD;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;            
           // System.out.println("PstAlUpload sql list list : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                AlUpload objAlUpload = new AlUpload();
                resultToObject(rs, objAlUpload);
                lists.add(objAlUpload);
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
    /**
     * Create by satrya 2013-01-06
     * Keterangan : mencari List dari AL prev sesuai tgkl yg di pilih
     * @param empId
     * @param selectedDate
     * @return 
     */
    
    public static Vector listAlOpname(long empId,long alUploadId,String whereClause){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT * FROM "+TBL_AL_UPLOAD +" p "; 

             if(whereClause != null && whereClause.length() > 0){
                sql = sql + " WHERE " + whereClause;
             }
                    
            sql+= " ORDER BY p."+PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE]+" DESC LIMIT 0,1";
                    
                
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                AlUpload objAlUpload = new AlUpload();
                resultToObject(rs, objAlUpload);
                lists.add(objAlUpload);
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
    
    
    private static void resultToObject(ResultSet rs, AlUpload objAlUpload) {
        try {
            objAlUpload.setOID(rs.getLong(PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID]));
            objAlUpload.setCurrPerTaken(rs.getFloat(PstAlUpload.fieldNames[PstAlUpload.FLD_CURR_PERIOD_TAKEN]));
            objAlUpload.setDataStatus(rs.getInt(PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS]));
            objAlUpload.setEmployeeId(rs.getLong(PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]));
            objAlUpload.setLastPerToClear(rs.getFloat(PstAlUpload.fieldNames[PstAlUpload.FLD_LAST_PER_TO_CLEAR]));
            objAlUpload.setOpnameDate(rs.getDate(PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE]));
            objAlUpload.setNewAl(rs.getFloat(PstAlUpload.fieldNames[PstAlUpload.FLD_NEW_AL]));
            objAlUpload.setStockId(rs.getLong(PstAlUpload.fieldNames[PstAlUpload.FLD_STOCK_ID]));
            objAlUpload.setNote(rs.getString(PstAlUpload.fieldNames[PstAlUpload.FLD_NOTE]));
            objAlUpload.setNewQty(rs.getFloat(PstAlUpload.fieldNames[PstAlUpload.FLD_NEW_QTY]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID] + ") FROM " + TBL_AL_UPLOAD;
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
     * @desc mencari data al upload berdasarkan id employee dan tanggal opname
     */
    public static Vector listByIdAndOpnameDate(int limitStart,int recordToGet,long employeeId, Date opnameDate,String order){
        Vector vAlUpload = new Vector();
        String where = fieldNames[FLD_EMPLOYEE_ID]+"="+employeeId
                +fieldNames[FLD_OPNAME_DATE]+"=\""+Formater.formatDate(opnameDate, "yyyy-MM-dd")+"\"";
        vAlUpload = list(limitStart, recordToGet, where, order);
        return vAlUpload;
    }
  
}
