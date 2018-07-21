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
public class PstLogCc extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{

    public static final  String TBL_LOG_CC = "log_cc";

    public static final  int FLD_LOG_CC_ID = 0;
    public static final  int FLD_REPORT_CC_ID = 1;
    public static final  int FLD_USER_CC_ID = 2;

	public static final  String[] fieldNames = {
            "LOG_CC_ID",
            "REPORT_ID",
            "USER_ID"
	 };

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
                TYPE_LONG
	 };

	public PstLogCc(){
	}

	public PstLogCc(int i) throws DBException {
		super(new PstLogCc());
	}

	public PstLogCc(String sOid) throws DBException {
		super(new PstLogCc(0));
		if(!locate(sOid))
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		else
			return;
	}

	public PstLogCc(long lOid) throws DBException {
		super(new PstLogCc(0));
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
		return TBL_LOG_CC;
	}

	public String[] getFieldNames(){
		return fieldNames;
	}

	public int[] getFieldTypes(){
		return fieldTypes;
	}

	public String getPersistentName(){
		return new PstLogCc().getClass().getName();
	}

	public long fetchExc(Entity ent) throws Exception{
		LogCc logCc = fetchExc(ent.getOID());
		ent = (Entity)logCc;
		return logCc.getOID();
	}

	public long insertExc(Entity ent) throws Exception{
		return insertExc((LogCc) ent);
	}

	public long updateExc(Entity ent) throws Exception{
		return updateExc((LogCc) ent);
	}

	public long deleteExc(Entity ent) throws Exception{
		if(ent==null){
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		}
		return deleteExc(ent.getOID());
	}

	public static LogCc fetchExc(long oid) throws DBException{
		try{
			LogCc logCc = new LogCc();
			PstLogCc pstLogCc = new PstLogCc(oid);
			logCc.setOID(oid);
                        logCc.setReportId(pstLogCc.getlong(FLD_REPORT_CC_ID));
                        logCc.setUserId(pstLogCc.getlong(FLD_USER_CC_ID));
			return logCc;
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogCc(0),DBException.UNKNOWN);
		}
	}

	public static long insertExc(LogCc logCc) throws DBException{
		try{
			PstLogCc pstLogCc = new PstLogCc(0);

                        pstLogCc.setLong(FLD_REPORT_CC_ID, logCc.getReportId());
                        pstLogCc.setLong(FLD_USER_CC_ID, logCc.getUserId());

			pstLogCc.insert();
			logCc.setOID(pstLogCc.getlong(FLD_LOG_CC_ID));
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogCc(0),DBException.UNKNOWN);
		}
		return logCc.getOID();
	}

	public static long updateExc(LogCc logCc) throws DBException{
		try{
			if(logCc.getOID() != 0){
                            PstLogCc pstLogCc = new PstLogCc(logCc.getOID());

                            pstLogCc.setLong(FLD_REPORT_CC_ID, logCc.getReportId());
                            pstLogCc.setLong(FLD_USER_CC_ID, logCc.getUserId());
                            pstLogCc.update();
                            return logCc.getOID();

			}
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogCc(0),DBException.UNKNOWN);
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{
		try{
			PstLogCc pstLogCc = new PstLogCc(oid);
			pstLogCc.delete();
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogCc(0),DBException.UNKNOWN);
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
			String sql = "SELECT * FROM " + TBL_LOG_CC;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logCc : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogCc logCc = new LogCc();
				resultToObject(rs, logCc);
				lists.add(logCc);
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

	private static void resultToObject(ResultSet rs, LogCc logCc){
		try{
			logCc.setOID(rs.getLong(PstLogCc.fieldNames[PstLogCc.FLD_LOG_CC_ID]));
                        logCc.setReportId(rs.getLong(PstLogCc.fieldNames[PstLogCc.FLD_REPORT_CC_ID]));
                        logCc.setUserId(rs.getLong(PstLogCc.fieldNames[PstLogCc.FLD_USER_CC_ID]));

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
            String sql = "DELETE FROM " + TBL_LOG_CC +
                         " WHERE " + PstLogCc.fieldNames[PstLogCc.FLD_REPORT_CC_ID] +
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


    public static Vector listWithJoin(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT a.*, b.* FROM " + TBL_LOG_CC +" as a inner join " +
                                PstAppUser.TBL_APP_USER +" as b on a."+ fieldNames[FLD_USER_CC_ID]+"="+
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
			while(rs.next()) {
				LogReaderList logReaderList = new LogReaderList();
				resultToObjectWithJoin(rs, logReaderList);
				lists.add(logReaderList);
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

    private static void resultToObjectWithJoin(ResultSet rs, LogReaderList logReaderList){
		try{
			logReaderList.setOID(rs.getLong(PstLogCc.fieldNames[PstLogCc.FLD_LOG_CC_ID]));
                        logReaderList.setLogReportId(rs.getLong(PstLogCc.fieldNames[PstLogCc.FLD_REPORT_CC_ID]));
                        logReaderList.setUserId(rs.getLong(PstLogCc.fieldNames[PstLogCc.FLD_USER_CC_ID]));
                        logReaderList.setUserName(rs.getString(PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME]));
		}catch(Exception e){ }
	}
}
