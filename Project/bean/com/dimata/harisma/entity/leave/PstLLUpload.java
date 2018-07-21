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
public class PstLLUpload  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
   
    public static final String TBL_LL_UPLOAD = "hr_ll_upload";
    
    public static final int FLD_LL_UPLOAD_ID            = 0;    
    public static final int FLD_OPNAME_DATE             = 1;
    public static final int FLD_EMPLOYEE_ID             = 2;
    public static final int FLD_LL_TAKEN_YEAR1          = 3;   
    public static final int FLD_DATA_STATUS             = 4;
    public static final int FLD_STOCk                   = 5;      
    public static final int FLD_NEW_LL                  = 6;    
    public static final int FLD_LAST_PER_TO_CLEAR_LL    = 7;
    public static final int FLD_LL_STOCK_ID             = 8;
    public static final int FLD_LL_QTY                  = 9;   
    public static final int FLD_NEW_LL_2                = 10;
    
    public static final String[] fieldNames = {
        "LL_UPLOAD_ID",
        "OPNAME_DATE",
        "EMPLOYEE_ID",
        "LL_TAKEN_YEAR1",        
        "DATA_STATUS",
        "STOCK",
        "NEW_LL",
        "LAST_PER_TO_CLEAR_LL",
        "LL_STOCK_ID",        
        "LL_QTY",
        "NEW_LL_2"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_LONG + TYPE_FK,
        TYPE_FLOAT,       
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG+ TYPE_FK,
        TYPE_FLOAT,
        TYPE_FLOAT
    };
    
    public static final int FLD_DOC_STATUS_PROCESS = 1;
    public static final int FLD_DOC_STATUS_NOT_PROCESS = 0;
    public static final String[] fieldStatusNames = {
        "",
        "Process"
    };   
    
    
    public PstLLUpload() {
    }
    
    public PstLLUpload(int i) throws DBException {
        super(new PstLLUpload());
    }
    
    public PstLLUpload(String sOid) throws DBException {
        super(new PstLLUpload(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstLLUpload(long lOid) throws DBException {
        super(new PstLLUpload(0));
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
        return TBL_LL_UPLOAD;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstLLUpload().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        LLUpload objLLUpload = fetchExc(ent.getOID());
        return objLLUpload.getOID();
    }
    
    public static LLUpload fetchExc(long oid) throws DBException {
        try {
            LLUpload objLLUpload = new LLUpload();
            PstLLUpload objPstLLUpload = new PstLLUpload(oid);
            objLLUpload.setOID(oid);
            
            objLLUpload.setDataStatus(objPstLLUpload.getInt(FLD_DATA_STATUS));
            objLLUpload.setEmployeeId(objPstLLUpload.getlong(FLD_EMPLOYEE_ID));
            objLLUpload.setOpnameDate(objPstLLUpload.getDate(FLD_OPNAME_DATE));
            objLLUpload.setLlTakenYear1(objPstLLUpload.getfloat(FLD_LL_TAKEN_YEAR1));
            objLLUpload.setStock(objPstLLUpload.getfloat(FLD_STOCk));            
            objLLUpload.setNewLL(objPstLLUpload.getfloat(FLD_NEW_LL));
            objLLUpload.setLastPerToClearLL(objPstLLUpload.getfloat(FLD_LAST_PER_TO_CLEAR_LL));
            objLLUpload.setLLStockID(objPstLLUpload.getlong(FLD_LL_STOCK_ID));
            objLLUpload.setLLQty(objPstLLUpload.getfloat(FLD_LL_QTY));
            objLLUpload.setNewLL2(objPstLLUpload.getfloat(FLD_NEW_LL_2));
            
            return objLLUpload;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLUpload(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((AlUpload) ent);
    }
    
    public static long updateExc(LLUpload objLLUpload) throws DBException {
        try {
            if (objLLUpload.getOID() != 0) {
                PstLLUpload objPstLLUpload = new PstLLUpload(objLLUpload.getOID());
                             
                objPstLLUpload.setInt(FLD_DATA_STATUS, objLLUpload.getDataStatus());
                objPstLLUpload.setLong(FLD_EMPLOYEE_ID, objLLUpload.getEmployeeId());
                objPstLLUpload.setDate(FLD_OPNAME_DATE, objLLUpload.getOpnameDate());
                objPstLLUpload.setFloat(FLD_LL_TAKEN_YEAR1, objLLUpload.getLlTakenYear1());
                objPstLLUpload.setFloat(FLD_STOCk, objLLUpload.getStock());                               
                objPstLLUpload.setFloat(FLD_NEW_LL,objLLUpload.getNewLL());
                objPstLLUpload.setFloat(FLD_LAST_PER_TO_CLEAR_LL,objLLUpload.getLastPerToClearLL());              
                objPstLLUpload.setLong(FLD_LL_STOCK_ID, objLLUpload.getLLStockID());
                objPstLLUpload.setFloat(FLD_LL_QTY, objLLUpload.getLLQty());
                objPstLLUpload.setFloat(FLD_NEW_LL_2, objLLUpload.getNewLL2());
                
                objPstLLUpload.update();
                return objLLUpload.getOID();                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLUpload(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstLLUpload objPstAlUpload = new PstLLUpload(oid);
            objPstAlUpload.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLUpload(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((AlUpload)ent);
    }
    
    public static long insertExc(LLUpload objLLUpload) throws DBException {
        try {
            PstLLUpload objPstLLUpload = new PstLLUpload(0);

            objPstLLUpload.setInt(FLD_DATA_STATUS, objLLUpload.getDataStatus());
            objPstLLUpload.setLong(FLD_EMPLOYEE_ID, objLLUpload.getEmployeeId());
            objPstLLUpload.setDate(FLD_OPNAME_DATE, objLLUpload.getOpnameDate());
            objPstLLUpload.setFloat(FLD_LL_TAKEN_YEAR1, objLLUpload.getLlTakenYear1());
            objPstLLUpload.setFloat(FLD_STOCk, objLLUpload.getStock());            
            objPstLLUpload.setFloat(FLD_NEW_LL,objLLUpload.getNewLL());
            objPstLLUpload.setFloat(FLD_LAST_PER_TO_CLEAR_LL,objLLUpload.getLastPerToClearLL());              
            objPstLLUpload.setLong(FLD_LL_STOCK_ID, objLLUpload.getLLStockID());
            objPstLLUpload.setFloat(FLD_LL_QTY, objLLUpload.getLLQty());
            objPstLLUpload.setFloat(FLD_NEW_LL_2, objLLUpload.getNewLL2());
            
            objPstLLUpload.insert();
            objLLUpload.setOID(objPstLLUpload.getlong(FLD_LL_UPLOAD_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLUpload(0), DBException.UNKNOWN);
        }
        return objLLUpload.getOID();
    }
/**
 * create by satrya 20130704
 * @param whereClause
 * @return 
 */
  public static Vector listLLOpname(String whereClause){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT * FROM "+TBL_LL_UPLOAD +" p "; 

             if(whereClause != null && whereClause.length() > 0){
                sql = sql + " WHERE " + whereClause;
             }
                    
            sql+= " ORDER BY p."+PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE]+" DESC LIMIT 0,1";
                    
                
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                LLUpload objLLUpload = new LLUpload();
                resultToObject(rs, objLLUpload);
                lists.add(objLLUpload);
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
            String sql = "SELECT * FROM " + TBL_LL_UPLOAD;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;            
           //System.out.println("PstAlUpload sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                LLUpload objLLUpload = new LLUpload();
                resultToObject(rs, objLLUpload);
                lists.add(objLLUpload);
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
    
    private static void resultToObject(ResultSet rs, LLUpload objLLUpload) {
        try {
            objLLUpload.setOID(rs.getLong(PstLLUpload.fieldNames[PstLLUpload.FLD_LL_UPLOAD_ID]));
            objLLUpload.setDataStatus(rs.getInt(PstLLUpload.fieldNames[PstLLUpload.FLD_DATA_STATUS]));
            objLLUpload.setEmployeeId(rs.getLong(PstLLUpload.fieldNames[PstLLUpload.FLD_EMPLOYEE_ID]));
            objLLUpload.setOpnameDate(rs.getDate(PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE]));
            objLLUpload.setLlTakenYear1(rs.getFloat(PstLLUpload.fieldNames[PstLLUpload.FLD_LL_TAKEN_YEAR1]));
            objLLUpload.setStock(rs.getFloat(PstLLUpload.fieldNames[PstLLUpload.FLD_STOCk]));            
            objLLUpload.setNewLL(rs.getFloat(PstLLUpload.fieldNames[PstLLUpload.FLD_NEW_LL]));
            objLLUpload.setLastPerToClearLL(rs.getFloat(PstLLUpload.fieldNames[PstLLUpload.FLD_LAST_PER_TO_CLEAR_LL]));
            objLLUpload.setLLStockID(rs.getLong(PstLLUpload.fieldNames[PstLLUpload.FLD_LL_STOCK_ID])); 
            objLLUpload.setLLQty(rs.getFloat(PstLLUpload.fieldNames[PstLLUpload.FLD_LL_QTY])); 
            objLLUpload.setNewLL2(rs.getFloat(PstLLUpload.fieldNames[PstLLUpload.FLD_NEW_LL_2]));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("
                    + PstLLUpload.fieldNames[PstLLUpload.FLD_LL_UPLOAD_ID] + ") FROM " 
                    + TBL_LL_UPLOAD;
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
