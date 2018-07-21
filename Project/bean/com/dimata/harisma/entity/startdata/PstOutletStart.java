
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

package com.dimata.harisma.entity.startdata; 

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

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.startdata.*; 

public class PstOutletStart extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_OUTLET_START = "outlet_start";//"OUTLET_START";

	public static final  int FLD_ID = 0;
	public static final  int FLD_DEP = 1;
	public static final  int FLD_DEP_NAME = 2;
	public static final  int FLD_LOCATION = 3;
	public static final  int FLD_LOC_NAME = 4;

	public static final  String[] fieldNames = {
		"ID",
		"DEP",
		"DEP_NAME",
		"LOCATION",
		"LOC_NAME"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING
	 }; 

	public PstOutletStart(){
	}

	public PstOutletStart(int i) throws DBException { 
		super(new PstOutletStart()); 
	}

	public PstOutletStart(String sOid) throws DBException { 
		super(new PstOutletStart(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstOutletStart(long lOid) throws DBException { 
		super(new PstOutletStart(0)); 
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
		return TBL_OUTLET_START;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstOutletStart().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		OutletStart outletstart = fetchExc(ent.getOID()); 
		ent = (Entity)outletstart; 
		return outletstart.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((OutletStart) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((OutletStart) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static OutletStart fetchExc(long oid) throws DBException{ 
		try{ 
			OutletStart outletstart = new OutletStart();
			PstOutletStart pstOutletStart = new PstOutletStart(oid); 
			outletstart.setOID(oid);

			outletstart.setDep(pstOutletStart.getString(FLD_DEP));
			outletstart.setDepName(pstOutletStart.getString(FLD_DEP_NAME));
			outletstart.setLocation(pstOutletStart.getString(FLD_LOCATION));
			outletstart.setLocName(pstOutletStart.getString(FLD_LOC_NAME));

			return outletstart; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOutletStart(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(OutletStart outletstart) throws DBException{ 
		try{ 
			PstOutletStart pstOutletStart = new PstOutletStart(0);

			pstOutletStart.setString(FLD_DEP, outletstart.getDep());
			pstOutletStart.setString(FLD_DEP_NAME, outletstart.getDepName());
			pstOutletStart.setString(FLD_LOCATION, outletstart.getLocation());
			pstOutletStart.setString(FLD_LOC_NAME, outletstart.getLocName());

			pstOutletStart.insert(); 
			outletstart.setOID(pstOutletStart.getlong(FLD_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOutletStart(0),DBException.UNKNOWN); 
		}
		return outletstart.getOID();
	}

	public static long updateExc(OutletStart outletstart) throws DBException{ 
		try{ 
			if(outletstart.getOID() != 0){ 
				PstOutletStart pstOutletStart = new PstOutletStart(outletstart.getOID());

				pstOutletStart.setString(FLD_DEP, outletstart.getDep());
				pstOutletStart.setString(FLD_DEP_NAME, outletstart.getDepName());
				pstOutletStart.setString(FLD_LOCATION, outletstart.getLocation());
				pstOutletStart.setString(FLD_LOC_NAME, outletstart.getLocName());

				pstOutletStart.update(); 
				return outletstart.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOutletStart(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstOutletStart pstOutletStart = new PstOutletStart(oid);
			pstOutletStart.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOutletStart(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_OUTLET_START; 
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
				OutletStart outletstart = new OutletStart();
				resultToObject(rs, outletstart);
				lists.add(outletstart);
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

	private static void resultToObject(ResultSet rs, OutletStart outletstart){
		try{
			outletstart.setOID(rs.getLong(PstOutletStart.fieldNames[PstOutletStart.FLD_ID]));
			outletstart.setDep(rs.getString(PstOutletStart.fieldNames[PstOutletStart.FLD_DEP]));
			outletstart.setDepName(rs.getString(PstOutletStart.fieldNames[PstOutletStart.FLD_DEP_NAME]));
			outletstart.setLocation(rs.getString(PstOutletStart.fieldNames[PstOutletStart.FLD_LOCATION]));
			outletstart.setLocName(rs.getString(PstOutletStart.fieldNames[PstOutletStart.FLD_LOC_NAME]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long id){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_OUTLET_START + " WHERE " + 
						PstOutletStart.fieldNames[PstOutletStart.FLD_ID] + " = " + id;

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
			String sql = "SELECT COUNT("+ PstOutletStart.fieldNames[PstOutletStart.FLD_ID] + ") FROM " + TBL_OUTLET_START;
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
			  	   OutletStart outletstart = (OutletStart)list.get(ls);
				   if(oid == outletstart.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
}
