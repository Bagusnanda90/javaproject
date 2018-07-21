
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

public class PstLeaveTarget extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LEAVE_TARGET      =   "hr_leave_target";//"";

	public static final  int FLD_LEAVE_TARGET_ID      = 0;
	public static final  int FLD_TARGET_DATE         = 1;
	public static final  int FLD_NAME        = 2;
	public static final  int FLD_DP_TARGET        = 3;
	public static final  int FLD_AL_TARGET        = 4;
	public static final  int FLD_LL_TARGET        = 5;
        
	public static final  String[] fieldNames = {
		"LEAVE_TARGET_ID",
		"DATE",
		"NAME",
		"DP_TARGET",
		"AL_TARGET",
		"LL_TARGET"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_DATE,
		TYPE_STRING,
		TYPE_FLOAT,
		TYPE_FLOAT,
		TYPE_FLOAT
	 }; 

	public PstLeaveTarget(){
	}

	public PstLeaveTarget(int i) throws DBException { 
		super(new PstLeaveTarget()); 
	}

	public PstLeaveTarget(String sOid) throws DBException { 
		super(new PstLeaveTarget(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLeaveTarget(long lOid) throws DBException { 
		super(new PstLeaveTarget(0)); 
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
		return TBL_LEAVE_TARGET;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLeaveTarget().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LeaveTarget leaveTarget = fetchExc(ent.getOID()); 
		ent = (Entity)leaveTarget; 
		return leaveTarget.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LeaveTarget) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LeaveTarget) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LeaveTarget fetchExc(long oid) throws DBException{ 
		try{ 
			LeaveTarget leaveTarget = new LeaveTarget();
			PstLeaveTarget PstLeaveTarget = new PstLeaveTarget(oid); 
			leaveTarget.setOID(oid);

			leaveTarget.setTargetDate(PstLeaveTarget.getDate(FLD_TARGET_DATE));
			leaveTarget.setName(PstLeaveTarget.getString(FLD_NAME));
			leaveTarget.setDpTarget(PstLeaveTarget.getdouble(FLD_DP_TARGET));
			leaveTarget.setAlTarget(PstLeaveTarget.getdouble(FLD_AL_TARGET));
			leaveTarget.setLlTarget(PstLeaveTarget.getdouble(FLD_LL_TARGET));

			return leaveTarget; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTarget(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LeaveTarget leaveTarget) throws DBException{ 
		try{ 
			PstLeaveTarget PstLeaveTarget = new PstLeaveTarget(0);

			PstLeaveTarget.setDate(FLD_TARGET_DATE, leaveTarget.getTargetDate());
			PstLeaveTarget.setString(FLD_NAME, leaveTarget.getName());
			PstLeaveTarget.setDouble(FLD_DP_TARGET, leaveTarget.getDpTarget());
			PstLeaveTarget.setDouble(FLD_AL_TARGET, leaveTarget.getAlTarget());
			PstLeaveTarget.setDouble(FLD_LL_TARGET, leaveTarget.getLlTarget());

			PstLeaveTarget.insert(); 
			leaveTarget.setOID(PstLeaveTarget.getlong(FLD_LEAVE_TARGET_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTarget(0),DBException.UNKNOWN); 
		}
		return leaveTarget.getOID();
	}

	public static long updateExc(LeaveTarget leaveTarget) throws DBException{ 
		try{ 
                    if(leaveTarget.getOID() != 0){ 
                        PstLeaveTarget PstLeaveTarget = new PstLeaveTarget(leaveTarget.getOID());

                        PstLeaveTarget.setDate(FLD_TARGET_DATE, leaveTarget.getTargetDate());
                        PstLeaveTarget.setString(FLD_NAME, leaveTarget.getName());
                        PstLeaveTarget.setDouble(FLD_DP_TARGET, leaveTarget.getDpTarget());
                        PstLeaveTarget.setDouble(FLD_AL_TARGET, leaveTarget.getAlTarget());
                        PstLeaveTarget.setDouble(FLD_LL_TARGET, leaveTarget.getLlTarget());

                        PstLeaveTarget.update(); 
                        return leaveTarget.getOID();

                    }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTarget(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLeaveTarget PstLeaveTarget = new PstLeaveTarget(oid);
			PstLeaveTarget.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTarget(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LEAVE_TARGET; 
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
				LeaveTarget leaveTarget = new LeaveTarget();
				resultToObject(rs, leaveTarget);
				lists.add(leaveTarget);
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

	private static void resultToObject(ResultSet rs, LeaveTarget leaveTarget){
		try{
			leaveTarget.setOID(rs.getLong(PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_LEAVE_TARGET_ID]));
			leaveTarget.setTargetDate(rs.getDate(PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_TARGET_DATE]));
			leaveTarget.setName(rs.getString(PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_NAME]));
			leaveTarget.setDpTarget(rs.getDouble(PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_DP_TARGET]));
			leaveTarget.setAlTarget(rs.getDouble(PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_AL_TARGET]));
			leaveTarget.setLlTarget(rs.getDouble(PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_LL_TARGET]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long leaveTargetId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LEAVE_TARGET + " WHERE " + 
						PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_LEAVE_TARGET_ID] + " = " + leaveTargetId;

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
			String sql = "SELECT COUNT("+ PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_LEAVE_TARGET_ID] + ") FROM " + TBL_LEAVE_TARGET;
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
			  	   LeaveTarget leaveTarget = (LeaveTarget)list.get(ls);
				   if(oid == leaveTarget.getOID())
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
    
    public static boolean checkTargetDate(Date targetDate){
        DBResultSet dbrs = null;
        boolean result = false;
        Date dateaStart = new Date(targetDate.getYear(), targetDate.getMonth(),1);
        try{
                String sql = "SELECT * FROM " + TBL_LEAVE_TARGET + " WHERE " + 
                                        PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_TARGET_DATE] 
                                        + " = '" + Formater.formatDate(dateaStart, "yyyy-MM-dd")+"'";

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
    
    
    public static long getTargetDateId(Date targetDate){
        DBResultSet dbrs = null;
        long result = 0;
        Date dateaStart = new Date(targetDate.getYear(), targetDate.getMonth(),1);
        try{
                String sql = "SELECT * FROM " + TBL_LEAVE_TARGET + " WHERE " + 
                                        PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_TARGET_DATE] 
                                        + " = '" + Formater.formatDate(dateaStart, "yyyy-MM-dd")+"'";

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) {
                    LeaveTarget objLeaveTarget = new LeaveTarget();
                    resultToObject(rs, objLeaveTarget);
                    result = objLeaveTarget.getOID();
                }
                rs.close();
        }catch(Exception e){
                System.out.println("err : "+e.toString());
        }finally{
                DBResultSet.close(dbrs);
                return result;
        }
    }
    
    public static LeaveTarget getLeaveTarget(Date targetDate){
        Date dateStart = new Date(targetDate.getYear(),targetDate.getMonth(),1);
        
        String whereClause = PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_TARGET_DATE] 
                                + " = '" + Formater.formatDate(dateStart, "yyyy-MM-dd")+"'";
        Vector vLeaveTarget = new Vector(1,1);
        vLeaveTarget = list(0, 0, whereClause, null);
        LeaveTarget leaveTarget = new LeaveTarget();
        if(vLeaveTarget.size()>0){
            leaveTarget = (LeaveTarget)vLeaveTarget.get(0);
        }
        return leaveTarget;
    }
    
}
