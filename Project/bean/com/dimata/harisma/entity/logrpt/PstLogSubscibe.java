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
public class PstLogSubscibe extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
      
    public static final  String TBL_LOG_SUBSCRIBE = "log_subscribe";

    public static final  int FLD_SUBSCRIBE_ID = 0;
    public static final  int FLD_REPORT_ID = 1;
    public static final  int FLD_USER_ID = 2;

	public static final  String[] fieldNames = {
            "SUBSCRIBE_ID",
            "REPORT_ID",
            "USER_ID"
	 };

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
                TYPE_LONG
	 };

	public PstLogSubscibe(){
	}

	public PstLogSubscibe(int i) throws DBException {
		super(new PstLogSubscibe());
	}

	public PstLogSubscibe(String sOid) throws DBException {
		super(new PstLogSubscibe(0));
		if(!locate(sOid))
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		else
			return;
	}

	public PstLogSubscibe(long lOid) throws DBException {
		super(new PstLogSubscibe(0));
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
		return TBL_LOG_SUBSCRIBE;
	}

	public String[] getFieldNames(){
		return fieldNames;
	}

	public int[] getFieldTypes(){
		return fieldTypes;
	}

	public String getPersistentName(){
		return new PstLogSubscibe().getClass().getName();
	}

	public long fetchExc(Entity ent) throws Exception{
		LogSubscribe logSubscribe = fetchExc(ent.getOID());
		ent = (Entity)logSubscribe;
		return logSubscribe.getOID();
	}

	public long insertExc(Entity ent) throws Exception{
		return insertExc((LogSubscribe) ent);
	}

	public long updateExc(Entity ent) throws Exception{
		return updateExc((LogSubscribe) ent);
	}

	public long deleteExc(Entity ent) throws Exception{
		if(ent==null){
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		}
		return deleteExc(ent.getOID());
	}

	public static LogSubscribe fetchExc(long oid) throws DBException{
		try{
			LogSubscribe logSubscribe = new LogSubscribe();
			PstLogSubscibe pstLogSubscibe = new PstLogSubscibe(oid);
			logSubscribe.setOID(oid);
                        logSubscribe.setReportId(pstLogSubscibe.getlong(FLD_REPORT_ID));
                        logSubscribe.setUserId(pstLogSubscibe.getlong(FLD_USER_ID));
			return logSubscribe;
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogSubscibe(0),DBException.UNKNOWN);
		}
	}

	public static long insertExc(LogSubscribe logSubscribe) throws DBException{
		try{
			PstLogSubscibe pstLogSubscibe = new PstLogSubscibe(0);

                        pstLogSubscibe.setLong(FLD_REPORT_ID, logSubscribe.getReportId());
                        pstLogSubscibe.setLong(FLD_USER_ID, logSubscribe.getUserId());

			pstLogSubscibe.insert();
			logSubscribe.setOID(pstLogSubscibe.getlong(FLD_SUBSCRIBE_ID));
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogSubscibe(0),DBException.UNKNOWN);
		}
		return logSubscribe.getOID();
	}

	public static long updateExc(LogSubscribe logSubscribe) throws DBException{
		try{
			if(logSubscribe.getOID() != 0){
                            PstLogSubscibe pstLogSubscibe = new PstLogSubscibe(logSubscribe.getOID());

                            pstLogSubscibe.setLong(FLD_REPORT_ID, logSubscribe.getReportId());
                            pstLogSubscibe.setLong(FLD_USER_ID, logSubscribe.getUserId());
                            pstLogSubscibe.update();
                            return logSubscribe.getOID();

			}
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogSubscibe(0),DBException.UNKNOWN);
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{
		try{
			PstLogSubscibe pstLogSubscibe = new PstLogSubscibe(oid);
			pstLogSubscibe.delete();
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstLogSubscibe(0),DBException.UNKNOWN);
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
			String sql = "SELECT * FROM " + TBL_LOG_SUBSCRIBE;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logSubscribe : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogSubscribe logSubscribe = new LogSubscribe();
				resultToObject(rs, logSubscribe);
				lists.add(logSubscribe);
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

	private static void resultToObject(ResultSet rs, LogSubscribe logSubscribe){
		try{
			logSubscribe.setOID(rs.getLong(PstLogSubscibe.fieldNames[PstLogSubscibe.FLD_SUBSCRIBE_ID]));
                        logSubscribe.setReportId(rs.getLong(PstLogSubscibe.fieldNames[PstLogSubscibe.FLD_REPORT_ID]));
                        logSubscribe.setUserId(rs.getLong(PstLogSubscibe.fieldNames[PstLogSubscibe.FLD_USER_ID]));

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

    public static boolean checkSubscribe(int limitStart,int recordToGet, String whereClause, String order){
		//Vector lists = new Vector();
		DBResultSet dbrs = null;
                boolean result = false;
		try {
			String sql = "SELECT a.*, b.* FROM " + TBL_LOG_SUBSCRIBE +" as a inner join " +
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


    public static long deleteWithReportId(long oid)
    {
       DBResultSet dbrs=null;
       try {
            String sql = "DELETE FROM " + TBL_LOG_SUBSCRIBE +
                         " WHERE " + PstLogSubscibe.fieldNames[PstLogSubscibe.FLD_REPORT_ID] +
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
