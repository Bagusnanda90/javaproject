
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 

/* package java */ 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.masterdata.*; 
import com.dimata. harisma.entity.employee.*;

public class PstAwardType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_AWARD_TYPE      =   "hr_award_type";//"HR_EMP_CATEGORY";

	public static final  int FLD_AWARD_TYPE_ID      = 0;
	public static final  int FLD_AWARD_TYPE         = 1;
	public static final  int FLD_DESCRIPTION        = 2;

	public static final  String[] fieldNames = {
		"AWARD_TYPE_ID",
		"AWARD_TYPE",
		"DESCRIPTION"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
		TYPE_STRING
	 }; 

	public PstAwardType(){
	}

	public PstAwardType(int i) throws DBException { 
		super(new PstAwardType()); 
	}

	public PstAwardType(String sOid) throws DBException { 
		super(new PstAwardType(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstAwardType(long lOid) throws DBException { 
		super(new PstAwardType(0)); 
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
		return TBL_AWARD_TYPE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstAwardType().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		AwardType awardType = fetchExc(ent.getOID()); 
		ent = (Entity)awardType; 
		return awardType.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((AwardType) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((AwardType) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static AwardType fetchExc(long oid) throws DBException{ 
		try{ 
			AwardType awardType = new AwardType();
			PstAwardType PstAwardType = new PstAwardType(oid); 
			awardType.setOID(oid);

			awardType.setAwardType(PstAwardType.getString(FLD_AWARD_TYPE));
			awardType.setDescription(PstAwardType.getString(FLD_DESCRIPTION));

			return awardType; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAwardType(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(AwardType awardType) throws DBException{ 
		try{ 
			PstAwardType PstAwardType = new PstAwardType(0);

			PstAwardType.setString(FLD_AWARD_TYPE, awardType.getAwardType());
			PstAwardType.setString(FLD_DESCRIPTION, awardType.getDescription());

			PstAwardType.insert(); 
			awardType.setOID(PstAwardType.getlong(FLD_AWARD_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAwardType(0),DBException.UNKNOWN); 
		}
		return awardType.getOID();
	}

	public static long updateExc(AwardType awardType) throws DBException{ 
		try{ 
			if(awardType.getOID() != 0){ 
				PstAwardType PstAwardType = new PstAwardType(awardType.getOID());

				PstAwardType.setString(FLD_AWARD_TYPE, awardType.getAwardType());
				PstAwardType.setString(FLD_DESCRIPTION, awardType.getDescription());

				PstAwardType.update(); 
				return awardType.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAwardType(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstAwardType PstAwardType = new PstAwardType(oid);
			PstAwardType.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAwardType(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_AWARD_TYPE; 
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
				AwardType awardType = new AwardType();
				resultToObject(rs, awardType);
				lists.add(awardType);
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

	private static void resultToObject(ResultSet rs, AwardType awardType){
		try{
			awardType.setOID(rs.getLong(PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE_ID]));
			awardType.setAwardType(rs.getString(PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE]));
			awardType.setDescription(rs.getString(PstAwardType.fieldNames[PstAwardType.FLD_DESCRIPTION]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long awardTypeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_AWARD_TYPE + " WHERE " + 
						PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE_ID] + " = " + awardTypeId;

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
			String sql = "SELECT COUNT("+ PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE_ID] + ") FROM " + TBL_AWARD_TYPE;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause,String order){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, order); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   AwardType awardType = (AwardType)list.get(ls);
				   if(oid == awardType.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
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



    public static boolean checkMaster(long oid)
    {
    	if(PstEmpAward.checkAward(oid))
            return true;
    	else
            return false;
    }
}
