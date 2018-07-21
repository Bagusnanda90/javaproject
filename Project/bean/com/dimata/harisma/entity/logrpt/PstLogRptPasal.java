/*
 * PstLogRptPasal.java
 *
 * Created on April 7, 2002, 9:29 AM
 */

package com.dimata.harisma.entity.logrpt;

/**
 *
 * @author  ktanjana
 * @version
 */

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;


public class PstLogRptPasal extends DBHandler implements I_DBInterface, I_DBType, I_Persintent  {
    
    public static final String TBL_LOG_RPT_PASAL = "log_report_pasal";
    public static final int FLD_LOG_REPORT_ID        = 0;
    public static final int FLD_PASAL_UMUM_ID        = 1;
    public static final int FLD_PASAL_KHUSUS_ID	     = 2;
    
    public static  final String[] fieldNames = {
        "LOG_REPORT_ID","PASAL_UMUM_ID","PASAL_KHUSUS_ID"
    } ;
    
    public static int[] fieldTypes = {
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_PK + TYPE_FK + TYPE_LONG
    };
    
    
    /** Creates new PstLogRptPasal */
    public PstLogRptPasal() {
    }
        
    public PstLogRptPasal(int i) throws DBException {
        super(new PstLogRptPasal());
    }
    
    
    public PstLogRptPasal(String sOid) throws DBException 
    {
        super(new PstLogRptPasal(0));
        if(!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    
    public PstLogRptPasal(long reportId, long pslUmumId, long pslKhususId) throws DBException
    {
        super(new PstLogRptPasal(0));
        
        if(!locate(reportId, pslUmumId,pslKhususId))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
        
    }
    
    
    /**
     *	Implemanting I_Entity interface methods
     */
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_LOG_RPT_PASAL;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {                
        return new PstLogRptPasal().getClass().getName();
    }
    
    
    /**
     *	Implementing I_DBInterface interface methods
     */
    public long fetch(Entity ent) {        
        LogRptPasal entObj = PstLogRptPasal.fetch(ent.getOID(0),ent.getOID(1), ent.getOID(2));
        ent = (Entity)entObj;
        return entObj.getOID();         
    }
    

    public long insert(Entity ent) {
        return PstLogRptPasal.insert((LogRptPasal) ent);
    }
    
    public long update(Entity ent) {
        return update((LogRptPasal) ent);
    }
    
    public long delete(Entity ent) {
        return delete((LogRptPasal) ent);
    }
        
    
    
    public static LogRptPasal fetch(long reportId, long pslUmumId, long pslKhususId)
    {
        LogRptPasal entObj = new LogRptPasal();
        try {
            PstLogRptPasal pstObj = new PstLogRptPasal(reportId, pslUmumId, pslKhususId);
            entObj.setReportId(reportId);
            entObj.setPasalUmumId(pslUmumId);
            entObj.setPasalKhususId(pslKhususId);
        }
        catch(DBException e) {
            System.out.println(e);
        }
        return entObj;
    }
    
    
    public static long insert(LogRptPasal entObj)
    {
        try{
            PstLogRptPasal pstObj = new PstLogRptPasal(0);
            
            pstObj.setLong(FLD_LOG_REPORT_ID, entObj.getReportId());
            pstObj.setLong(FLD_PASAL_UMUM_ID, entObj.getPasalUmumId());
            pstObj.setLong(FLD_PASAL_KHUSUS_ID, entObj.getPasalKhususId());
            
            pstObj.insert();            
            return entObj.getReportId();
        }
        catch(DBException e) {
            System.out.println(e);
        }
        return 0;  
    }
    

    public static long deleteByReportId(long oid)
    {
       PstLogRptPasal pstObj = new PstLogRptPasal();
       DBResultSet dbrs=null;
       try {
            String sql = "DELETE FROM " + pstObj.getTableName() +
                         " WHERE " + PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_LOG_REPORT_ID] +
                         " = '" + oid +"'";
            //System.out.println(sql);
            int status = DBHandler.execUpdate(sql);
            return oid;            
       }catch(Exception e) {
            System.out.println(e);            
        }
        finally{
            DBResultSet.close(dbrs);
        }        
        
        return 0;
    }
    
    public static long update(LogRptPasal entObj)
    {
        if(entObj != null && entObj.getReportId() != 0)
        {
            try {
                PstLogRptPasal pstObj = new PstLogRptPasal(entObj.getReportId(), entObj.getPasalUmumId(), entObj.getPasalKhususId());
                
                pstObj.update();
                return entObj.getReportId();
            }catch(Exception e) {
                System.out.println(e);
            }            
        }
        return 0;
    }
   
    
    public static Vector listAll()
    {
        return list(0, 0, null,null);
    }
    
    public static Vector listByReportId(long reportId){
        String where =fieldNames[FLD_LOG_REPORT_ID]+"='"+reportId+"'";
        return list(0,100, where, fieldNames[FLD_PASAL_UMUM_ID]);
    }


    public static Vector list(int limitStart, int recordToGet, String whereClause, String order)
    {
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_LOG_RPT_PASAL;
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
				LogRptPasal logRptPasal = new LogRptPasal ();
				resultToObject(rs, logRptPasal);
				lists.add(logRptPasal);
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

	public static void resultToObject(ResultSet rs, LogRptPasal logRptPasal){
		try{
			logRptPasal.setReportId(rs.getLong(PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_LOG_REPORT_ID]));
			logRptPasal.setPasalUmumId(rs.getLong(PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_PASAL_UMUM_ID]));
			logRptPasal.setPasalKhususId(rs.getLong(PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_PASAL_KHUSUS_ID]));
		}catch(Exception e){ }
	}


    public static Vector listByReportIdWithJoin(long reportId){
        return listWithJoin(0,100, fieldNames[FLD_LOG_REPORT_ID]+"='"+reportId+"'",fieldNames[FLD_PASAL_UMUM_ID]);
    }

    public static Vector listWithJoin(int limitStart, int recordToGet, String whereClause, String order)
    {
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT a.*,b."+PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM]+
                                ","+PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS]+" FROM " + TBL_LOG_RPT_PASAL+ " as a inner join " +
                                PstLogPasalUmum.TBL_LOG_PASAL_UMUM+" as b on b."+ PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM_ID]+
                                "=a."+PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_PASAL_UMUM_ID]+" inner join "+
                                PstLogPasalKhusus.TBL_LOG_PASAL_KHUSUS+" as c on c."+PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS_ID]+
                                "=a."+PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_PASAL_KHUSUS_ID]+" " ;
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
				LogRptPasal logRptPasal = new LogRptPasal ();
				resultToObjectWithJoin(rs, logRptPasal);
				lists.add(logRptPasal);
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

	public static void resultToObjectWithJoin(ResultSet rs, LogRptPasal logRptPasal){
		try{
			logRptPasal.setReportId(rs.getLong(PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_LOG_REPORT_ID]));
			logRptPasal.setPasalUmumId(rs.getLong(PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_PASAL_UMUM_ID]));
			logRptPasal.setPasalKhususId(rs.getLong(PstLogRptPasal.fieldNames[PstLogRptPasal.FLD_PASAL_KHUSUS_ID]));
                        logRptPasal.setPasalUmum(rs.getString(PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM]));
                        logRptPasal.setPasalKhusus(rs.getString(PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS]));
		}catch(Exception e){ }
	}



}
