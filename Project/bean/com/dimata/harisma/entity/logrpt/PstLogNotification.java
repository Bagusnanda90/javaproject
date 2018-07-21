/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.logrpt;

/* package java */ 
import com.dimata.harisma.entity.admin.PstAppUser;
import java.sql.*;
import java.util.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
/**
 *
 * @author dimata005
 */
public class PstLogNotification extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final  String TBL_LOG_NOTIFICATION = "log_notification";

    public static final  int FLD_NOTIFICATION_ID = 0;
    public static final  int FLD_REPORT_ID = 1;
    public static final  int FLD_USER_ID = 2;
    public static final  int FLD_DATE_NOTIFICATION = 3;
    public static final  int FLD_LOG_NOTIFICATION = 4;
    public static final  int FLD_STATUS_NOTIFICATION = 5;
   
	public static final  String[] fieldNames = {
            "NOTIFICATION_ID",
            "REPORT_ID",
            "USER_ID",
            "DATE",
            "LOG_NOTIFICATION",
            "STATUS_NOTIFICATION"
	 };

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_DATE,
                TYPE_STRING,
                TYPE_INT
	 };

	public PstLogNotification(){
	}

	public PstLogNotification(int i) throws DBException {
		super(new PstLogNotification());
	}

	public PstLogNotification(String sOid) throws DBException {
		super(new PstLogNotification(0));
		if(!locate(sOid))
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		else
			return;
	}

	public PstLogNotification(long lOid) throws DBException {
		super(new PstLogNotification(0));
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
		return TBL_LOG_NOTIFICATION;
	}

	public String[] getFieldNames(){
		return fieldNames;
	}

	public int[] getFieldTypes(){
		return fieldTypes;
	}

	public String getPersistentName(){
		return new PstLogNotification().getClass().getName();
	}

	public long fetchExc(Entity ent) throws Exception{
		LogNotification logNotification = fetchExc(ent.getOID());
		ent = (Entity)logNotification;
		return logNotification.getOID();
	}

	public long insertExc(Entity ent) throws Exception{
		return insertExc((LogNotification) ent);
	}

	public long updateExc(Entity ent) throws Exception{
		return updateExc((LogNotification) ent);
	}

	public long deleteExc(Entity ent) throws Exception{
		if(ent==null){
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		}
		return deleteExc(ent.getOID());
	}

	public static LogNotification fetchExc(long oid) throws DBException{
		try{
			LogNotification logNotification = new LogNotification();
			PstLogNotification pstLogReportNotification = new PstLogNotification(oid);
			logNotification.setOID(oid);
                        logNotification.setReportId(pstLogReportNotification.getlong(FLD_REPORT_ID));
                        logNotification.setUserId(pstLogReportNotification.getlong(FLD_USER_ID));
                        logNotification.setDateNotification(pstLogReportNotification.getDate(FLD_DATE_NOTIFICATION));
                        logNotification.setLogNotification(pstLogReportNotification.getString(FLD_LOG_NOTIFICATION));
                        logNotification.setStatusNotification(pstLogReportNotification.getInt(FLD_NOTIFICATION_ID));
			return logNotification;
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogNotification(0),DBException.UNKNOWN);
		}
	}

	public static long insertExc(LogNotification logNotification) throws DBException{
		try{
			PstLogNotification pstLogReportNotification = new PstLogNotification(0);

                        pstLogReportNotification.setLong(FLD_REPORT_ID, logNotification.getReportId());
                        pstLogReportNotification.setLong(FLD_USER_ID, logNotification.getUserId());
                        pstLogReportNotification.setDate(FLD_DATE_NOTIFICATION, logNotification.getDateNotification());
                        pstLogReportNotification.setString(FLD_LOG_NOTIFICATION, logNotification.getLogNotification());
                        pstLogReportNotification.setInt(FLD_STATUS_NOTIFICATION, logNotification.getStatusNotification());
                        
			pstLogReportNotification.insert();
			logNotification.setOID(pstLogReportNotification.getlong(FLD_NOTIFICATION_ID));
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogNotification(0),DBException.UNKNOWN);
		}
		return logNotification.getOID();
	}

	public static long updateExc(LogNotification logNotification) throws DBException{
		try{
			if(logNotification.getOID() != 0){
                            PstLogNotification pstLogReportNotification = new PstLogNotification(logNotification.getOID());

                            pstLogReportNotification.setLong(FLD_REPORT_ID, logNotification.getReportId());
                            pstLogReportNotification.setLong(FLD_USER_ID, logNotification.getUserId());
                            pstLogReportNotification.setDate(FLD_DATE_NOTIFICATION, logNotification.getDateNotification());
                            pstLogReportNotification.setString(FLD_LOG_NOTIFICATION, logNotification.getLogNotification());
                            pstLogReportNotification.setInt(FLD_STATUS_NOTIFICATION, logNotification.getStatusNotification());
                            pstLogReportNotification.update();
                            return logNotification.getOID();

			}
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogNotification(0),DBException.UNKNOWN);
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{
		try{
			PstLogNotification pstLogReportNotification = new PstLogNotification(oid);
			pstLogReportNotification.delete();
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogNotification(0),DBException.UNKNOWN);
		}
		return oid;
	}

	public static Vector listAll(){
		return list(0, 1000, "","");
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_LOG_NOTIFICATION;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logNotification : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogNotification logNotification = new LogNotification();
				resultToObject(rs, logNotification);
				lists.add(logNotification);
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

	private static void resultToObject(ResultSet rs, LogNotification logNotification){
		try{
			logNotification.setOID(rs.getLong(PstLogNotification.fieldNames[PstLogNotification.FLD_NOTIFICATION_ID]));
                        logNotification.setReportId(rs.getLong(PstLogNotification.fieldNames[PstLogNotification.FLD_REPORT_ID]));
                        logNotification.setUserId(rs.getLong(PstLogNotification.fieldNames[PstLogNotification.FLD_USER_ID]));
                        logNotification.setDateNotification(rs.getDate(PstLogNotification.fieldNames[PstLogNotification.FLD_DATE_NOTIFICATION]));
                        logNotification.setLogNotification(rs.getString(PstLogNotification.fieldNames[PstLogNotification.FLD_LOG_NOTIFICATION]));
                        logNotification.setStatusNotification(rs.getInt(PstLogNotification.fieldNames[PstLogNotification.FLD_STATUS_NOTIFICATION]));

		}catch(Exception e){ }
	}

        public static int findLimitCommand(int start, int recordToGet, int vectSize)
       {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
    	if(start == 0)
            cmd =  Command.FIRST;
        else{
            if(start == (vectSize-recordToGet))
                cmd = Command.LAST;
            else{
            	start = start + recordToGet;
             	if(start <= (vectSize - recordToGet)){
                 	cmd = Command.NEXT;
                    System.out.println("next.......................");
             	}else{
                    start = start - recordToGet;
		             if(start > 0){
                         cmd = Command.PREV;
                         System.out.println("prev.......................");
		             }
                }
            }
        }

        return cmd;
    }

    public static long deleteByReportId(long oid)
    {
       PstLogCc pstObj = new PstLogCc();
       DBResultSet dbrs=null;
       try {
            String sql = "DELETE FROM " + TBL_LOG_NOTIFICATION +
                         " WHERE " + PstLogNotification.fieldNames[PstLogNotification.FLD_REPORT_ID] +
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

    public static boolean checkNotif(int limitStart,int recordToGet, String whereClause, String order){
		//Vector lists = new Vector();
		DBResultSet dbrs = null;
                boolean result = false;
		try {
			String sql = "SELECT a.*, b.* FROM " + TBL_LOG_NOTIFICATION +" as a inner join " +
                                PstAppUser.TBL_APP_USER +" as b on a."+ fieldNames[FLD_USER_ID]+"="+
                                "b."+PstAppUser.fieldNames[PstAppUser.FLD_USER_ID] ;

			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logReaderList : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) { result = true; break; }
			rs.close();
			return result;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return result;
	}


    public static long updateClearNotif(long oid)
    {
       DBResultSet dbrs=null;
       try {
            String sql = "UPDATE " + TBL_LOG_NOTIFICATION +" SET "+PstLogNotification.fieldNames[PstLogNotification.FLD_STATUS_NOTIFICATION]+"=1"+
                         " WHERE " + PstLogNotification.fieldNames[PstLogNotification.FLD_REPORT_ID] +
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
}
