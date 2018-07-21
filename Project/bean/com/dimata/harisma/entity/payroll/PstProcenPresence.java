/*
 * PstProcenPresence.java
 *
 * Created on August 28, 2007, 1:45 PM
 */

package com.dimata.harisma.entity.payroll;

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



/**
 *
 * @author  yunny
 */
public class PstProcenPresence extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
    public static final  String TBL_PROCEN = "hr_procen_absence";//"HR_PROCEN_ABSENCE";
    
    public static final  int FLD_PROCEN_PRESENCE_ID = 0;
    public static final  int FLD_PROCEN_PRESENCE = 1;
    public static final  int FLD_ABSENCE_DAY= 2;
    
    public static final  String[] fieldNames = {
                "PROCEN_PRESENCE_ID",
                "PROCEN_PRESENCE",
                "ABSENCE_DAY"
         };
     public static final  int[] fieldTypes = {
		TYPE_LONG  + TYPE_PK + TYPE_ID,
		TYPE_FLOAT,
                TYPE_INT
         };
    
    
    /** Creates a new instance of PstProcenPresence */
    public PstProcenPresence() {
    }
    
     public PstProcenPresence(int i) throws DBException { 
            super(new PstProcenPresence()); 
    }
     
    public PstProcenPresence(String sOid) throws DBException { 
        super(new PstProcenPresence(0)); 
        if(!locate(sOid)) 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
        else 
                return; 
   }
    
    public PstProcenPresence(long lOid) throws DBException { 
        super(new PstProcenPresence(0)); 
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
    
    
    
    public String[] getFieldNames() {
         return fieldNames;
    }
    
    public int getFieldSize() {
         return fieldNames.length; 
    }
    
    public int[] getFieldTypes() {
        return fieldTypes; 
        
    }
    
    public String getPersistentName() {
        return new PstProcenPresence().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_PROCEN;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        if(ent==null){ 
            throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
     return deleteExc(ent.getOID());
    }
    
    public long fetchExc(Entity ent) throws Exception {
        ProcenPresence procenPresence = fetchExc(ent.getOID()); 
        ent = (Entity)procenPresence; 
        return procenPresence.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((ProcenPresence) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
         return updateExc((ProcenPresence) ent); 
    }
    
    public static ProcenPresence fetchExc(long oid) throws DBException{ 
        try{ 
                ProcenPresence procenPresence = new ProcenPresence();
                PstProcenPresence pstProcenPresence = new PstProcenPresence(oid); 
                procenPresence.setOID(oid);
                procenPresence.setProcenPresence(pstProcenPresence.getdouble(FLD_PROCEN_PRESENCE));
                procenPresence.setAbsenceDay(pstProcenPresence.getInt(FLD_ABSENCE_DAY));
                return procenPresence;
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstCurrencyType(0),DBException.UNKNOWN); 
        } 
        
}
    
 public static long insertExc(ProcenPresence procenPresence) throws DBException{ 
		try{ 
			PstProcenPresence pstProcenPresence = new PstProcenPresence(0);

			pstProcenPresence.setDouble(FLD_PROCEN_PRESENCE, procenPresence.getProcenPresence());
			pstProcenPresence.setInt(FLD_ABSENCE_DAY, procenPresence.getAbsenceDay());

			pstProcenPresence.insert(); 
			procenPresence.setOID(pstProcenPresence.getlong(FLD_PROCEN_PRESENCE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstProcenPresence(0),DBException.UNKNOWN); 
		}
		return procenPresence.getOID();
	}
 
 public static long updateExc(ProcenPresence procenPresence) throws DBException{ 
		try{ 
		  if(procenPresence.getOID() != 0){ 
                        PstProcenPresence pstProcenPresence = new PstProcenPresence(procenPresence.getOID());
                        pstProcenPresence.setDouble(FLD_PROCEN_PRESENCE, procenPresence.getProcenPresence());
                        pstProcenPresence.setInt(FLD_ABSENCE_DAY, procenPresence.getAbsenceDay());

                      pstProcenPresence.update();
		      return procenPresence.getOID();

		     }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstProcenPresence(0),DBException.UNKNOWN); 
		}
		return 0;
}
 
  public static long deleteExc(long oid) throws DBException{ 
        try{ 
                PstProcenPresence pstProcenPresence = new PstProcenPresence(oid);
                pstProcenPresence.delete();
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstProcenPresence(0),DBException.UNKNOWN); 
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
                String sql = "SELECT * FROM " + TBL_PROCEN; 
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
                        ProcenPresence procenPresence = new ProcenPresence();
                        resultToObject(rs, procenPresence);
                        lists.add(procenPresence);
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
   
   public static void resultToObject(ResultSet rs, ProcenPresence procenPresence){
		try{
                        procenPresence.setOID(rs.getLong(PstProcenPresence.fieldNames[PstProcenPresence.FLD_PROCEN_PRESENCE_ID]));
                        procenPresence.setProcenPresence(rs.getDouble(PstProcenPresence.fieldNames[PstProcenPresence.FLD_PROCEN_PRESENCE]));
                        procenPresence.setAbsenceDay(rs.getInt(PstProcenPresence.fieldNames[PstProcenPresence.FLD_ABSENCE_DAY]));
		}catch(Exception e){ }
	}
   
    public static boolean checkOID(long procenPresenceId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PROCEN + " WHERE " + 
				PstProcenPresence.fieldNames[PstProcenPresence.FLD_PROCEN_PRESENCE_ID] + " = '" + procenPresenceId+"'";

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
			String sql = "SELECT COUNT("+ PstProcenPresence.fieldNames[PstProcenPresence.FLD_PROCEN_PRESENCE_ID] + ") FROM " + TBL_PROCEN;
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
    
    public static int findLimitStart( long oid, int recordToGet, String whereClause,String orderClause){
		String order = "";
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   TaxType taxType = (TaxType)list.get(ls);
				   if(oid == taxType.getOID())
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
    /* this method used to get the maksimum number from procen presence
     * Created by Yunny
     */
    public static int getMaxNum(){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT MAX("+ PstProcenPresence.fieldNames[PstProcenPresence.FLD_ABSENCE_DAY] + ") FROM " + TBL_PROCEN;
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
   
    /* this method used to get the minimum number from procen presence
     * Created by Yunny
     */
    public static int getMaxMin(){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT MIN("+ PstProcenPresence.fieldNames[PstProcenPresence.FLD_ABSENCE_DAY] + ") FROM " + TBL_PROCEN;
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
    
    
     /* this method used to get the procentase  from procen presence
     * @ param absenceDay
     * @ param minNum
     * @ param maxNum
     * Created by Yunny
     */
    public static double getProcentase(int absenceDay,int maxNum,int minNum){
		DBResultSet dbrs = null;
                double count = 0;
		try {
                        if(absenceDay > maxNum ){
                                count = 0;
                         }
                        else if(absenceDay < minNum){
                                count = 100;
                        }
                        else{  
                            String sql = "SELECT "+ PstProcenPresence.fieldNames[PstProcenPresence.FLD_PROCEN_PRESENCE] + " FROM " + TBL_PROCEN+
                                         " WHERE "+PstProcenPresence.fieldNames[PstProcenPresence.FLD_ABSENCE_DAY]+"="+absenceDay;
                            dbrs = DBHandler.execQueryResult(sql);
                            ResultSet rs = dbrs.getResultSet();
                            while(rs.next()) {  
                                count = rs.getDouble(1);
                            }
                            rs.close();
                        }
			return count;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    
    
}
