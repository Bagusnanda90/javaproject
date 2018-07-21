
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.employee; 

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
import com.dimata.harisma.entity.employee.*; 

public class PstEmpMessage extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_EMP_MESSAGE = "hr_emp_message";//"HR_EMP_Message";

	public static final  int FLD_EMP_MESSAGE_ID = 0;
	public static final  int FLD_EMPLOYEE_ID = 1;
	public static final  int FLD_START_DATE = 2;
	public static final  int FLD_END_DATE = 3;
	public static final  int FLD_MESSAGE = 4;
	public static final  int FLD_ID_SEND = 5;

	public static final  String[] fieldNames = {
		"MESSAGE_ID",
		"EMPLOYEE_ID",
		"START_ACTIVE",
		"END_ACTIVE",
		"MESSAGE",
		"SEND"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_DATE,
		TYPE_DATE,
		TYPE_STRING,
		TYPE_INT
        }; 

	public PstEmpMessage(){
	}

	public PstEmpMessage(int i) throws DBException { 
		super(new PstEmpMessage()); 
	}

	public PstEmpMessage(String sOid) throws DBException { 
		super(new PstEmpMessage(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstEmpMessage(long lOid) throws DBException { 
		super(new PstEmpMessage(0)); 
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
		return TBL_HR_EMP_MESSAGE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstEmpMessage().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		EmpMessage empMessage = fetchExc(ent.getOID()); 
		ent = (Entity)empMessage; 
		return empMessage.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((EmpMessage) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((EmpMessage) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static EmpMessage fetchExc(long oid) throws DBException{ 
		try{ 
			EmpMessage empMessage = new EmpMessage();
			PstEmpMessage pstEmpMessage = new PstEmpMessage(oid); 
			empMessage.setOID(oid);

			empMessage.setEmployeeId(pstEmpMessage.getlong(FLD_EMPLOYEE_ID));
			empMessage.setStartDate(pstEmpMessage.getDate(FLD_START_DATE));
			empMessage.setEndDate(pstEmpMessage.getDate(FLD_END_DATE));
			empMessage.setMessage(pstEmpMessage.getString(FLD_MESSAGE));
			empMessage.setIsSend(pstEmpMessage.getInt(FLD_ID_SEND));
			return empMessage; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpMessage(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(EmpMessage empMessage) throws DBException{ 
		try{ 
			PstEmpMessage pstEmpMessage = new PstEmpMessage(0);

			pstEmpMessage.setLong(FLD_EMPLOYEE_ID, empMessage.getEmployeeId());
			pstEmpMessage.setDate(FLD_START_DATE, empMessage.getStartDate());
			pstEmpMessage.setDate(FLD_END_DATE, empMessage.getEndDate());
			pstEmpMessage.setString(FLD_MESSAGE, empMessage.getMessage());
			pstEmpMessage.setInt(FLD_ID_SEND, empMessage.getIsSend());
			
			pstEmpMessage.insert(); 
			empMessage.setOID(pstEmpMessage.getlong(FLD_EMP_MESSAGE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpMessage(0),DBException.UNKNOWN); 
		}
		return empMessage.getOID();
	}

	public static long updateExc(EmpMessage empMessage) throws DBException{ 
		try{ 
                    if(empMessage.getOID() != 0){ 
                        PstEmpMessage pstEmpMessage = new PstEmpMessage(empMessage.getOID());

                        pstEmpMessage.setLong(FLD_EMPLOYEE_ID, empMessage.getEmployeeId());
                        pstEmpMessage.setDate(FLD_START_DATE, empMessage.getStartDate());
                        pstEmpMessage.setDate(FLD_END_DATE, empMessage.getEndDate());
                        pstEmpMessage.setString(FLD_MESSAGE, empMessage.getMessage());
                        pstEmpMessage.setInt(FLD_ID_SEND, empMessage.getIsSend());
                        pstEmpMessage.update(); 
                        return empMessage.getOID();
                    }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpMessage(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstEmpMessage pstEmpMessage = new PstEmpMessage(oid);
			pstEmpMessage.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpMessage(0),DBException.UNKNOWN); 
		}
		return oid;
	}

	public static Vector listAll(){ 
		return list(0, 0, "",""); 
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_EMP_MESSAGE; 
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
				EmpMessage empMessage = new EmpMessage();
				resultToObject(rs, empMessage);
				lists.add(empMessage);
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

	private static void resultToObject(ResultSet rs, EmpMessage empMessage){
		try{
			empMessage.setOID(rs.getLong(PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMP_MESSAGE_ID]));
			empMessage.setEmployeeId(rs.getLong(PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID]));
			empMessage.setStartDate(rs.getDate(PstEmpMessage.fieldNames[PstEmpMessage.FLD_START_DATE]));
			empMessage.setEndDate(rs.getDate(PstEmpMessage.fieldNames[PstEmpMessage.FLD_END_DATE]));
			empMessage.setMessage(rs.getString(PstEmpMessage.fieldNames[PstEmpMessage.FLD_MESSAGE]));
			empMessage.setIsSend(rs.getInt(PstEmpMessage.fieldNames[PstEmpMessage.FLD_ID_SEND]));
			
		}catch(Exception e){ }
	}

	public static boolean checkOID(long empMessageId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_MESSAGE + " WHERE " + 
						PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMP_MESSAGE_ID] + " = " + empMessageId;

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
        
        public static boolean checkEmployeeId(long empId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_MESSAGE + " WHERE " + 
						PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID] + " = " + empId;

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
			String sql = "SELECT COUNT("+ PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMP_MESSAGE_ID] + ") FROM " + TBL_HR_EMP_MESSAGE;
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
			  	   EmpMessage empMessage = (EmpMessage)list.get(ls);
				   if(oid == empMessage.getOID())
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
                    //System.out.println("next.......................");
             	}else{
                    start = start - recordToGet;
		             if(start > 0){
                         cmd = Command.PREV;
                      //   System.out.println("prev.......................");
		             }
                }
            }
        }

        return cmd;
    }



    public static long deleteByMsgEmployee(long empOid){
    	try{
        	String sql = " DELETE FROM "+PstEmpMessage.TBL_HR_EMP_MESSAGE+
                		 " WHERE "+PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID]+
                         " = "+empOid;

        	int status = DBHandler.execUpdate(sql);

    	}catch(Exception exc){
        	System.out.println("error delete Emp Message "+exc.toString());
    	}
    	return empOid;
    }
    
    public static EmpMessage getEmpMessageByEmpId(long employeeId){
        EmpMessage empMsg = new EmpMessage();
        String whereClasue = fieldNames[FLD_EMPLOYEE_ID]
                +" = "+employeeId;
        Vector vList = new Vector(1,1);
        vList = list(0, 0, whereClasue, null);
        if(vList.size()>0){
            empMsg = (EmpMessage)vList.get(0);
        }
        return empMsg;
    }
    
    public static Vector listActiveMessage(Date currDate){
        Vector vList = new Vector(1,1);
        String whereClause = fieldNames[FLD_START_DATE]
                +" <='"+Formater.formatDate(currDate, "yyyy-MM-dd")+"'"
                +"  AND "+fieldNames[FLD_END_DATE]
                +" >= '"+Formater.formatDate(currDate, "yyyy-MM-dd")+"'"
                ;
        vList = list(0, 0, whereClause, fieldNames[FLD_START_DATE]);
        return vList;
    }
    
}
