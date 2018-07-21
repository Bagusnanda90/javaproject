
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

package com.dimata.harisma.entity.clinic; 

/* package java */ 
import java.io.*
;
import java.sql.*
;import java.util.*
;import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata. harisma.entity.clinic.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.session.clinic.*;

public class PstGuestHandling extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_GUEST_HANDLING ="hr_guest_handling";// "HR_GUEST_HANDLING";

	public static final  int FLD_GUEST_CLINIC_ID = 0;
	public static final  int FLD_DATE = 1;
	public static final  int FLD_GUEST_NAME = 2;
	public static final  int FLD_ROOM = 3;
	public static final  int FLD_DIAGNOSIS = 4;
	public static final  int FLD_TREATMENT = 5;
	public static final  int FLD_DESCRIPTION = 6;

	public static final  String[] fieldNames = {
		"GUEST_CLINIC_ID",
		"DATE",
		"GUEST_NAME",
		"ROOM",
		"DIAGNOSIS",
		"TREATMENT",
		"DESCRIPTION"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_DATE,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING
	 }; 

	public PstGuestHandling(){
	}

	public PstGuestHandling(int i) throws DBException { 
		super(new PstGuestHandling()); 
	}

	public PstGuestHandling(String sOid) throws DBException { 
		super(new PstGuestHandling(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstGuestHandling(long lOid) throws DBException { 
		super(new PstGuestHandling(0)); 
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
		return TBL_HR_GUEST_HANDLING;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstGuestHandling().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		GuestHandling guesthandling = fetchExc(ent.getOID()); 
		ent = (Entity)guesthandling; 
		return guesthandling.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((GuestHandling) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((GuestHandling) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static GuestHandling fetchExc(long oid) throws DBException{ 
		try{ 
			GuestHandling guesthandling = new GuestHandling();
			PstGuestHandling pstGuestHandling = new PstGuestHandling(oid); 
			guesthandling.setOID(oid);

			guesthandling.setDate(pstGuestHandling.getDate(FLD_DATE));
			guesthandling.setGuestName(pstGuestHandling.getString(FLD_GUEST_NAME));
			guesthandling.setRoom(pstGuestHandling.getString(FLD_ROOM));
			guesthandling.setDiagnosis(pstGuestHandling.getString(FLD_DIAGNOSIS));
			guesthandling.setTreatment(pstGuestHandling.getString(FLD_TREATMENT));
			guesthandling.setDescription(pstGuestHandling.getString(FLD_DESCRIPTION));

			return guesthandling; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstGuestHandling(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(GuestHandling guesthandling) throws DBException{ 
		try{ 
			PstGuestHandling pstGuestHandling = new PstGuestHandling(0);

			pstGuestHandling.setDate(FLD_DATE, guesthandling.getDate());
			pstGuestHandling.setString(FLD_GUEST_NAME, guesthandling.getGuestName());
			pstGuestHandling.setString(FLD_ROOM, guesthandling.getRoom());
			pstGuestHandling.setString(FLD_DIAGNOSIS, guesthandling.getDiagnosis());
			pstGuestHandling.setString(FLD_TREATMENT, guesthandling.getTreatment());
			pstGuestHandling.setString(FLD_DESCRIPTION, guesthandling.getDescription());

			pstGuestHandling.insert(); 
			guesthandling.setOID(pstGuestHandling.getlong(FLD_GUEST_CLINIC_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstGuestHandling(0),DBException.UNKNOWN); 
		}
		return guesthandling.getOID();
	}

	public static long updateExc(GuestHandling guesthandling) throws DBException{ 
		try{ 
			if(guesthandling.getOID() != 0){ 
				PstGuestHandling pstGuestHandling = new PstGuestHandling(guesthandling.getOID());

				pstGuestHandling.setDate(FLD_DATE, guesthandling.getDate());
				pstGuestHandling.setString(FLD_GUEST_NAME, guesthandling.getGuestName());
				pstGuestHandling.setString(FLD_ROOM, guesthandling.getRoom());
				pstGuestHandling.setString(FLD_DIAGNOSIS, guesthandling.getDiagnosis());
				pstGuestHandling.setString(FLD_TREATMENT, guesthandling.getTreatment());
				pstGuestHandling.setString(FLD_DESCRIPTION, guesthandling.getDescription());

				pstGuestHandling.update(); 
				return guesthandling.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstGuestHandling(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstGuestHandling pstGuestHandling = new PstGuestHandling(oid);
			pstGuestHandling.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstGuestHandling(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_GUEST_HANDLING; 
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
				GuestHandling guesthandling = new GuestHandling();
				resultToObject(rs, guesthandling);
				lists.add(guesthandling);
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

	public static void resultToObject(ResultSet rs, GuestHandling guesthandling){
		try{
			guesthandling.setOID(rs.getLong(PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_CLINIC_ID]));
			guesthandling.setDate(rs.getDate(PstGuestHandling.fieldNames[PstGuestHandling.FLD_DATE]));
			guesthandling.setGuestName(rs.getString(PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_NAME]));
			guesthandling.setRoom(rs.getString(PstGuestHandling.fieldNames[PstGuestHandling.FLD_ROOM]));
			guesthandling.setDiagnosis(rs.getString(PstGuestHandling.fieldNames[PstGuestHandling.FLD_DIAGNOSIS]));
			guesthandling.setTreatment(rs.getString(PstGuestHandling.fieldNames[PstGuestHandling.FLD_TREATMENT]));
			guesthandling.setDescription(rs.getString(PstGuestHandling.fieldNames[PstGuestHandling.FLD_DESCRIPTION]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long guestClinicId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_GUEST_HANDLING + " WHERE " + 
						PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_CLINIC_ID] + " = " + guestClinicId;

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
			String sql = "SELECT COUNT("+ PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_CLINIC_ID] + ") FROM " + TBL_HR_GUEST_HANDLING;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause, String orderClause){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   GuestHandling guesthandling = (GuestHandling)list.get(ls);
				   if(oid == guesthandling.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}


    public static int findLimitStart( long oid, int recordToGet, int size, SrcGuestHandling srcGuestHandling){
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector listGuestHandling = SessGuestHandling.searchGuestHandling(srcGuestHandling, start, recordToGet);
			 start = i;
			 if(listGuestHandling.size()>0){
			  for(int ls=0;ls<listGuestHandling.size();ls++){
			  	   GuestHandling guestHandling = (GuestHandling)listGuestHandling.get(0);
				   if(oid == guestHandling.getOID())
					  found=true;

			  }
		  }
		}

		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}


	/* This method used to find command where current data */
	public static int findLimitCommand(int start, int recordToGet, int vectSize){
		 int cmd = Command.LIST;
		 int mdl = vectSize % recordToGet;
		 vectSize = vectSize + (recordToGet - mdl);
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
}
