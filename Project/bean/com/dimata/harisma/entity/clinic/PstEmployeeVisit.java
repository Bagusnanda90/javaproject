
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
import com.dimata.harisma.entity.clinic.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.session.clinic.*;

public class PstEmployeeVisit extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_EMP_VISIT = "hr_emp_visit";//"HR_EMP_VISIT";

	public static final  int FLD_EMP_VISIT_ID = 0;
	public static final  int FLD_VISIT_DATE = 1;
	public static final  int FLD_EMPLOYEE_ID = 2;
	public static final  int FLD_DIAGNOSE = 3;
	public static final  int FLD_VISITED_BY = 4;
	public static final  int FLD_DESCRIPTION = 5;

	public static final  String[] fieldNames = {
		"EMP_VISIT_ID",
		"VISIT_DATE",
		"EMPLOYEE_ID",
		"DIAGNOSE",
		"VISITED_BY",
		"DESCRIPTION"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_DATE,
		TYPE_LONG,
		TYPE_STRING,
		TYPE_LONG,
		TYPE_STRING
	 }; 

	public PstEmployeeVisit(){
	}

	public PstEmployeeVisit(int i) throws DBException { 
		super(new PstEmployeeVisit()); 
	}

	public PstEmployeeVisit(String sOid) throws DBException { 
		super(new PstEmployeeVisit(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstEmployeeVisit(long lOid) throws DBException { 
		super(new PstEmployeeVisit(0)); 
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
		return TBL_HR_EMP_VISIT;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstEmployeeVisit().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		EmployeeVisit employeevisit = fetchExc(ent.getOID()); 
		ent = (Entity)employeevisit; 
		return employeevisit.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((EmployeeVisit) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((EmployeeVisit) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static EmployeeVisit fetchExc(long oid) throws DBException{ 
		try{ 
			EmployeeVisit employeevisit = new EmployeeVisit();
			PstEmployeeVisit pstEmployeeVisit = new PstEmployeeVisit(oid); 
			employeevisit.setOID(oid);

			employeevisit.setVisitDate(pstEmployeeVisit.getDate(FLD_VISIT_DATE));
			employeevisit.setEmployeeId(pstEmployeeVisit.getlong(FLD_EMPLOYEE_ID));
			employeevisit.setDiagnose(pstEmployeeVisit.getString(FLD_DIAGNOSE));
			employeevisit.setVisitedBy(pstEmployeeVisit.getlong(FLD_VISITED_BY));
			employeevisit.setDescription(pstEmployeeVisit.getString(FLD_DESCRIPTION));

			return employeevisit; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeVisit(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(EmployeeVisit employeevisit) throws DBException{ 
		try{ 
			PstEmployeeVisit pstEmployeeVisit = new PstEmployeeVisit(0);

			pstEmployeeVisit.setDate(FLD_VISIT_DATE, employeevisit.getVisitDate());
			pstEmployeeVisit.setLong(FLD_EMPLOYEE_ID, employeevisit.getEmployeeId());
			pstEmployeeVisit.setString(FLD_DIAGNOSE, employeevisit.getDiagnose());
			pstEmployeeVisit.setLong(FLD_VISITED_BY, employeevisit.getVisitedBy());
			pstEmployeeVisit.setString(FLD_DESCRIPTION, employeevisit.getDescription());

			pstEmployeeVisit.insert(); 
			employeevisit.setOID(pstEmployeeVisit.getlong(FLD_EMP_VISIT_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeVisit(0),DBException.UNKNOWN); 
		}
		return employeevisit.getOID();
	}

	public static long updateExc(EmployeeVisit employeevisit) throws DBException{ 
		try{ 
			if(employeevisit.getOID() != 0){ 
				PstEmployeeVisit pstEmployeeVisit = new PstEmployeeVisit(employeevisit.getOID());

				pstEmployeeVisit.setDate(FLD_VISIT_DATE, employeevisit.getVisitDate());
				pstEmployeeVisit.setLong(FLD_EMPLOYEE_ID, employeevisit.getEmployeeId());
				pstEmployeeVisit.setString(FLD_DIAGNOSE, employeevisit.getDiagnose());
				pstEmployeeVisit.setLong(FLD_VISITED_BY, employeevisit.getVisitedBy());
				pstEmployeeVisit.setString(FLD_DESCRIPTION, employeevisit.getDescription());

				pstEmployeeVisit.update(); 
				return employeevisit.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeVisit(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstEmployeeVisit pstEmployeeVisit = new PstEmployeeVisit(oid);
			pstEmployeeVisit.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeVisit(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_EMP_VISIT; 
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
				EmployeeVisit employeevisit = new EmployeeVisit();
				resultToObject(rs, employeevisit);
				lists.add(employeevisit);
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

	private static void resultToObject(ResultSet rs, EmployeeVisit employeevisit){
		try{
			employeevisit.setOID(rs.getLong(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMP_VISIT_ID]));
			employeevisit.setVisitDate(rs.getDate(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISIT_DATE]));
			employeevisit.setEmployeeId(rs.getLong(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMPLOYEE_ID]));
			employeevisit.setDiagnose(rs.getString(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_DIAGNOSE]));
			employeevisit.setVisitedBy(rs.getLong(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISITED_BY]));
			employeevisit.setDescription(rs.getString(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_DESCRIPTION]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long empVisitId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_VISIT + " WHERE " + 
						PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMP_VISIT_ID] + " = " + empVisitId;

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
			String sql = "SELECT COUNT("+ PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMP_VISIT_ID] + ") FROM " + TBL_HR_EMP_VISIT;
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
			  	   EmployeeVisit employeevisit = (EmployeeVisit)list.get(ls);
				   if(oid == employeevisit.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}

    /* This method used to find current data */
	public static int findLimitStart( long oid, int recordToGet, int size, SrcEmployeeVisit srcEmployeeVisit){
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector listEmployeeVisit = SessEmployeeVisit.searchEmployeeVisit(srcEmployeeVisit, start, recordToGet);
			 start = i;
			 if(listEmployeeVisit.size()>0){
			  for(int ls=0;ls<listEmployeeVisit.size();ls++){
                   Vector tempVect = (Vector)listEmployeeVisit.get(ls);
			  	   EmployeeVisit employeevisit = (EmployeeVisit)tempVect.get(0);
				   if(oid == employeevisit.getOID())
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
