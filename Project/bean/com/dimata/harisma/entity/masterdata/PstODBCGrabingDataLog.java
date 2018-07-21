
/* Created on 	:  [date] [time] AM/PM
 *
 * @author	 :
 * @version	 :
 */

/*******************************************************************
 * Class Description 	: [project description ... ]
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.entity.masterdata;
/* package java */
import java.io.*
;
import java.sql.*
;import java.util.*
;import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package HarismaVisual */
/*import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.db.DBException;
import com.dimata.harisma.db.DBLogger;*/
import com.dimata.harisma.entity.masterdata.*;

public class PstODBCGrabingDataLog extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_ODBC_GRABING_DATA_LOG = "odbc_grabing_data_log";//"ODBC_GRABING_DATA_LOG";
    
    public static final  int FLD_LOG_ID = 0;
    public static final  int FLD_DATE = 1;
    
    public static final  String[] fieldNames = {
        "LOG_ID",
        "DATE"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE
    };
    
    public PstODBCGrabingDataLog(){
    }
    
    public PstODBCGrabingDataLog(int i) throws DBException {
        super(new PstODBCGrabingDataLog());
    }
    
    public PstODBCGrabingDataLog(String sOid) throws DBException {
        super(new PstODBCGrabingDataLog(0));
        if(!locate(sOid))
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstODBCGrabingDataLog(long lOid) throws DBException {
        super(new PstODBCGrabingDataLog(0));
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
        return TBL_ODBC_GRABING_DATA_LOG;
    }
    
    public String[] getFieldNames(){
        return fieldNames;
    }
    
    public int[] getFieldTypes(){
        return fieldTypes;
    }
    
    public String getPersistentName(){
        return new PstODBCGrabingDataLog().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception{
        ODBCGrabingDataLog odbcgrabingdatalog = fetchExc(ent.getOID());
        ent = (Entity)odbcgrabingdatalog;
        return odbcgrabingdatalog.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception{
        return insertExc((ODBCGrabingDataLog) ent);
    }
    
    public long updateExc(Entity ent) throws Exception{
        return updateExc((ODBCGrabingDataLog) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception{
        if(ent==null){
            throw new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public static ODBCGrabingDataLog fetchExc(long oid) throws DBException{
        try{
            ODBCGrabingDataLog odbcgrabingdatalog = new ODBCGrabingDataLog();
            PstODBCGrabingDataLog pstODBCGrabingDataLog = new PstODBCGrabingDataLog(oid);
            odbcgrabingdatalog.setOID(oid);
            
            odbcgrabingdatalog.setDate(pstODBCGrabingDataLog.getDate(FLD_DATE));
            
            return odbcgrabingdatalog;
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstODBCGrabingDataLog(0),DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(ODBCGrabingDataLog odbcgrabingdatalog) throws DBException{
        try{
            PstODBCGrabingDataLog pstODBCGrabingDataLog = new PstODBCGrabingDataLog(0);
            
            pstODBCGrabingDataLog.setDate(FLD_DATE, odbcgrabingdatalog.getDate());
            
            pstODBCGrabingDataLog.insert();
            odbcgrabingdatalog.setOID(pstODBCGrabingDataLog.getlong(FLD_LOG_ID));
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstODBCGrabingDataLog(0),DBException.UNKNOWN);
        }
        return odbcgrabingdatalog.getOID();
    }
    
    public static long updateExc(ODBCGrabingDataLog odbcgrabingdatalog) throws DBException{
        try{
            if(odbcgrabingdatalog.getOID() != 0){
                PstODBCGrabingDataLog pstODBCGrabingDataLog = new PstODBCGrabingDataLog(odbcgrabingdatalog.getOID());
                
                pstODBCGrabingDataLog.setDate(FLD_DATE, odbcgrabingdatalog.getDate());
                
                pstODBCGrabingDataLog.update();
                return odbcgrabingdatalog.getOID();
                
            }
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstODBCGrabingDataLog(0),DBException.UNKNOWN);
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException{
        try{
            PstODBCGrabingDataLog pstODBCGrabingDataLog = new PstODBCGrabingDataLog(oid);
            pstODBCGrabingDataLog.delete();
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
            throw new DBException(new PstODBCGrabingDataLog(0),DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_ODBC_GRABING_DATA_LOG;
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
                ODBCGrabingDataLog odbcgrabingdatalog = new ODBCGrabingDataLog();
                resultToObject(rs, odbcgrabingdatalog);
                lists.add(odbcgrabingdatalog);
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
    
    private static void resultToObject(ResultSet rs, ODBCGrabingDataLog odbcgrabingdatalog){
        try{
            odbcgrabingdatalog.setOID(rs.getLong(PstODBCGrabingDataLog.fieldNames[PstODBCGrabingDataLog.FLD_LOG_ID]));
            Date date = DBHandler.convertDate(rs.getDate(PstODBCGrabingDataLog.fieldNames[PstODBCGrabingDataLog.FLD_DATE]),rs.getTime(PstODBCGrabingDataLog.fieldNames[PstODBCGrabingDataLog.FLD_DATE]));
            odbcgrabingdatalog.setDate(date);
            
        }catch(Exception e){ }
    }
    
    public static boolean checkOID(long logId){
        DBResultSet dbrs = null;
        boolean result = false;
        try{
            String sql = "SELECT * FROM " + TBL_ODBC_GRABING_DATA_LOG + " WHERE " +
            PstODBCGrabingDataLog.fieldNames[PstODBCGrabingDataLog.FLD_LOG_ID] + " = " + logId;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) { result = true; }
            rs.close();
        }catch(Exception e){
            System.out.println("err : "+e.toString());
        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstODBCGrabingDataLog.fieldNames[PstODBCGrabingDataLog.FLD_LOG_ID] + ") FROM " + TBL_ODBC_GRABING_DATA_LOG;
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
                    ODBCGrabingDataLog odbcgrabingdatalog = (ODBCGrabingDataLog)list.get(ls);
                    if(oid == odbcgrabingdatalog.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    public static Vector selectExc(ODBCGrabingDataLog objODBCGrabingDataLog){
        Vector list = new Vector();
        list = PstODBCGrabingDataLog.list(0, 0, "", "");
        int jumlahRecord=list.size();
        System.out.println("Jumlah = "+jumlahRecord);
        for(int i=jumlahRecord-1; i < list.size(); i++){
            objODBCGrabingDataLog = (ODBCGrabingDataLog)list.get(i);
            System.out.println("Tanggalan = "+objODBCGrabingDataLog.getDate());
        }
        return list;
        
    }
    
    
    public static void main(String[] args){
        ODBCGrabingDataLog objODBCGrabingDataLog = new ODBCGrabingDataLog();
        
        //objODBCGrabingDataLog.setDate(new Date());
        try{
            //PstODBCGrabingDataLog.insertExc(objODBCGrabingDataLog);
            PstODBCGrabingDataLog.selectExc(objODBCGrabingDataLog);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}


