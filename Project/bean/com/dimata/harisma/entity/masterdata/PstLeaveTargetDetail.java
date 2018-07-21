
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

public class PstLeaveTargetDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LEAVE_TARGET_DETAIL      =   "hr_leave_target_detail";//"";

	public static final  int FLD_LEAVE_TARGET_DETAIL_ID      = 0;
	public static final  int FLD_LEAVE_TARGET_ID         = 1;
	public static final  int FLD_TARGET_DP        = 2;
	public static final  int FLD_TARGET_AL        = 3;
	public static final  int FLD_TARGET_LL        = 4;
	public static final  int FLD_DEPARTMENT_ID    = 5;

	public static final  String[] fieldNames = {
		"LEAVE_TARGET_DETAIL_ID",
		"LEAVE_TARGET_ID",
		"TARGET_DP",
		"TARGET_AL",
		"TARGET_LL",
		"DEPARTMENT_ID"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_FLOAT,
		TYPE_FLOAT,
		TYPE_FLOAT,
		TYPE_LONG
	 }; 

	public PstLeaveTargetDetail(){
	}

	public PstLeaveTargetDetail(int i) throws DBException { 
		super(new PstLeaveTargetDetail()); 
	}

	public PstLeaveTargetDetail(String sOid) throws DBException { 
		super(new PstLeaveTargetDetail(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLeaveTargetDetail(long lOid) throws DBException { 
		super(new PstLeaveTargetDetail(0)); 
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
		return TBL_LEAVE_TARGET_DETAIL;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLeaveTargetDetail().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LeaveTargetDetail leaveTargetDetail = fetchExc(ent.getOID()); 
		ent = (Entity)leaveTargetDetail; 
		return leaveTargetDetail.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LeaveTargetDetail) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LeaveTargetDetail) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LeaveTargetDetail fetchExc(long oid) throws DBException{ 
		try{ 
			LeaveTargetDetail leaveTargetDetail = new LeaveTargetDetail();
			PstLeaveTargetDetail PstLeaveTargetDetail = new PstLeaveTargetDetail(oid); 
			leaveTargetDetail.setOID(oid);

			leaveTargetDetail.setLeaveTargetId(PstLeaveTargetDetail.getlong(FLD_LEAVE_TARGET_ID));
			leaveTargetDetail.setTargetDP(PstLeaveTargetDetail.getfloat(FLD_TARGET_DP));
			leaveTargetDetail.setTargetAL(PstLeaveTargetDetail.getfloat(FLD_TARGET_AL));
			leaveTargetDetail.setTargetLL(PstLeaveTargetDetail.getfloat(FLD_TARGET_LL));
			leaveTargetDetail.setDeparmentId(PstLeaveTargetDetail.getlong(FLD_DEPARTMENT_ID));

			return leaveTargetDetail; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTargetDetail(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LeaveTargetDetail leaveTargetDetail) throws DBException{ 
		try{ 
			PstLeaveTargetDetail PstLeaveTargetDetail = new PstLeaveTargetDetail(0);

			PstLeaveTargetDetail.setLong(FLD_LEAVE_TARGET_ID, leaveTargetDetail.getLeaveTargetId());
			PstLeaveTargetDetail.setFloat(FLD_TARGET_DP, leaveTargetDetail.getTargetDP());
			PstLeaveTargetDetail.setFloat(FLD_TARGET_AL, leaveTargetDetail.getTargetAL());
			PstLeaveTargetDetail.setFloat(FLD_TARGET_LL, leaveTargetDetail.getTargetLL());
			PstLeaveTargetDetail.setLong(FLD_DEPARTMENT_ID, leaveTargetDetail.getDeparmentId());

			PstLeaveTargetDetail.insert(); 
			leaveTargetDetail.setOID(PstLeaveTargetDetail.getlong(FLD_LEAVE_TARGET_DETAIL_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTargetDetail(0),DBException.UNKNOWN); 
		}
		return leaveTargetDetail.getOID();
	}

	public static long updateExc(LeaveTargetDetail leaveTargetDetail) throws DBException{ 
		try{ 
                    if(leaveTargetDetail.getOID() != 0){ 
                        PstLeaveTargetDetail PstLeaveTargetDetail = new PstLeaveTargetDetail(leaveTargetDetail.getOID());

                        PstLeaveTargetDetail.setLong(FLD_LEAVE_TARGET_ID, leaveTargetDetail.getLeaveTargetId());
                        PstLeaveTargetDetail.setFloat(FLD_TARGET_DP, leaveTargetDetail.getTargetDP());
                        PstLeaveTargetDetail.setFloat(FLD_TARGET_AL, leaveTargetDetail.getTargetAL());
                        PstLeaveTargetDetail.setFloat(FLD_TARGET_LL, leaveTargetDetail.getTargetLL());
                        PstLeaveTargetDetail.setLong(FLD_DEPARTMENT_ID, leaveTargetDetail.getDeparmentId());

                        PstLeaveTargetDetail.update(); 
                        return leaveTargetDetail.getOID();
                    }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTargetDetail(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLeaveTargetDetail PstLeaveTargetDetail = new PstLeaveTargetDetail(oid);
			PstLeaveTargetDetail.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeaveTargetDetail(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LEAVE_TARGET_DETAIL; 
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
				LeaveTargetDetail leaveTargetDetail = new LeaveTargetDetail();
				resultToObject(rs, leaveTargetDetail);
				lists.add(leaveTargetDetail);
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

	private static void resultToObject(ResultSet rs, LeaveTargetDetail leaveTargetDetail){
		try{
			leaveTargetDetail.setOID(rs.getLong(PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_LEAVE_TARGET_DETAIL_ID]));
			leaveTargetDetail.setLeaveTargetId(rs.getLong(PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_LEAVE_TARGET_ID]));
			leaveTargetDetail.setTargetDP(rs.getFloat(PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_TARGET_DP]));
			leaveTargetDetail.setTargetAL(rs.getFloat(PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_TARGET_AL]));
			leaveTargetDetail.setTargetLL(rs.getFloat(PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_TARGET_LL]));
			leaveTargetDetail.setDeparmentId(rs.getLong(PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_DEPARTMENT_ID]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long leaveTargetDetailId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LEAVE_TARGET_DETAIL + " WHERE " + 
						PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_LEAVE_TARGET_DETAIL_ID] + " = " + leaveTargetDetailId;

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
			String sql = "SELECT COUNT("+ PstLeaveTargetDetail.fieldNames[PstLeaveTargetDetail.FLD_LEAVE_TARGET_DETAIL_ID] + ") FROM " + TBL_LEAVE_TARGET_DETAIL;
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
			  	   LeaveTargetDetail leaveTargetDetail = (LeaveTargetDetail)list.get(ls);
				   if(oid == leaveTargetDetail.getOID())
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
    
    public static boolean deleteByLeaveTarget(long leaveTargetId){
        boolean isSuccess = false;
        int val = 0;
        try {
                String sql = "DELETE FROM "+ PstLeaveTargetDetail.TBL_LEAVE_TARGET_DETAIL 
                        + " WHERE " +fieldNames[FLD_LEAVE_TARGET_ID]
                        +"="+leaveTargetId;
                
                val = DBHandler.execUpdate(sql);
                isSuccess = true;
        }catch(Exception e) {
                isSuccess = false;
        }
        return isSuccess;
    }
    
}
