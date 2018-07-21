/*
 * PstTrainingFile.java
 *
 * Created on December 7, 2007, 3:55 PM
 */

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

import com.dimata.harisma.entity.employee.*; 



/**
 *
 * @author  YUNI
 */
public class PstTrainingFile extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
     public static final  String TBL_HR_TRAINING_FILE = "hr_training_file";//"HR_TRAINING_FILE";
     
    public static final  int FLD_TRAINING_FILE_ID = 0;
    public static final  int FLD_TRAINING_ID = 1;
    public static final  int FLD_FILE_NAME = 2;
    public static final  int FLD_ATTACH_FILE = 3;
   
     public static final  String[] fieldNames = {
		"TRAINING_FILE_ID",
		"TRAINING_ID",
		"FILE_NAME",
                "ATTACH_FILE"
                
     }; 
     
     public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_STRING,
                TYPE_STRING
                
	 };
   
    /** Creates a new instance of PstTrainingFile */
    public PstTrainingFile() {
    }
    
    public PstTrainingFile(int i) throws DBException { 
		super(new PstTrainingFile()); 
    }
    
    public PstTrainingFile(String sOid) throws DBException { 
		super(new PstTrainingFile(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
   }
    
   public PstTrainingFile(long lOid) throws DBException { 
		super(new PstTrainingFile(0)); 
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
   
    
    public long deleteExc(Entity ent) throws Exception {
         if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
	 return deleteExc(ent.getOID()); 
    }
    
    public long fetchExc(Entity ent) throws Exception {
        TrainingFile trainingFile = fetchExc(ent.getOID()); 
        ent = (Entity)trainingFile; 
        return trainingFile.getOID(); 
    }
     
    public long insertExc(Entity ent) throws Exception {
         return insertExc((TrainingFile) ent); 
        
    }
    
    public long updateExc(Entity ent) throws Exception {
         return updateExc((TrainingFile) ent); 
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
        return new PstTrainingFile().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_HR_TRAINING_FILE ;
    }
    
     public static TrainingFile fetchExc(long oid) throws DBException{ 
		try{ 
			TrainingFile trainingFile = new TrainingFile();
			PstTrainingFile pstTrainingFile = new PstTrainingFile(oid); 
			trainingFile.setOID(oid);

			trainingFile.setTrainingId(pstTrainingFile.getlong(FLD_TRAINING_ID));
                        trainingFile.setFileName(pstTrainingFile.getString(FLD_FILE_NAME));
                        trainingFile.setAttachFile(pstTrainingFile.getString(FLD_ATTACH_FILE));
                      
			return trainingFile; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTrainingFile(0),DBException.UNKNOWN); 
		} 
	}
     
     public static long insertExc(TrainingFile trainingFile) throws DBException{ 
		try{ 
			PstTrainingFile pstTrainingFile = new PstTrainingFile(0);
                        pstTrainingFile.setLong(FLD_TRAINING_ID, trainingFile.getTrainingId());
                        pstTrainingFile.setString(FLD_FILE_NAME, trainingFile.getFileName());
                        pstTrainingFile.setString(FLD_ATTACH_FILE, trainingFile.getAttachFile());
                       
		        pstTrainingFile.insert(); 
			trainingFile.setOID(pstTrainingFile.getlong(FLD_TRAINING_FILE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpRelevantDoc(0),DBException.UNKNOWN); 
		}
		return trainingFile.getOID();
	}
     
     public static long updateExc(TrainingFile trainingFile) throws DBException{ 
		try{ 
			if(trainingFile.getOID() != 0){ 
				PstTrainingFile pstTrainingFile = new PstTrainingFile(trainingFile.getOID());
                        	pstTrainingFile.setLong(FLD_TRAINING_ID, trainingFile.getTrainingId());
                                pstTrainingFile.setString(FLD_FILE_NAME, trainingFile.getFileName());
                                pstTrainingFile.setString(FLD_ATTACH_FILE, trainingFile.getAttachFile());
                         	pstTrainingFile.update(); 
				return trainingFile.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpRelevantDoc(0),DBException.UNKNOWN); 
		}
		return 0;
	}
     
      public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstTrainingFile pstTrainingFile = new PstTrainingFile(oid);
			pstTrainingFile.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTrainingFile(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_TRAINING_FILE; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                        System.out.println("sql training...."+sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				TrainingFile trainingFile = new TrainingFile();
				resultToObject(rs, trainingFile);
				lists.add(trainingFile);
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
        
         private static void resultToObject(ResultSet rs, TrainingFile trainingFile){
		try{
			trainingFile.setOID(rs.getLong(PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID]));
                        trainingFile.setTrainingId(rs.getLong(PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_ID]));
                        trainingFile.setFileName(rs.getString(PstTrainingFile.fieldNames[PstTrainingFile.FLD_FILE_NAME]));
                        trainingFile.setAttachFile(rs.getString(PstTrainingFile.fieldNames[PstTrainingFile.FLD_ATTACH_FILE]));
                      
		}catch(Exception e){ }
	}
         
        public static boolean checkOID(long trainingId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_TRAINING_FILE + " WHERE " + 
				     PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID] + " = '" + trainingId + "'";

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
			String sql = "SELECT COUNT("+ PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID] + ") FROM " + TBL_HR_TRAINING_FILE;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause,String orderClause){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause);
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   TrainingFile trainingFile = (TrainingFile)list.get(ls);
				   if(oid == trainingFile.getOID())
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
     
     
  
     
  public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        
        /*Date netDate = new Date();*/
        TrainingFile trainingFile = new TrainingFile();
        trainingFile.setTrainingId(123);
        trainingFile.setFileName("dsfg");
        
        try{
            PstTrainingFile.insertExc(trainingFile);
            System.out.println("masuk tabel");
        }catch(Exception e){
            System.out.println("err tabel");
            System.out.println("Err"+e.toString());
        }
     
    /* String stOut = listDateOut(26, 226, 504404343502872505L);
     System.out.println("dtOut........"+stOut);   
     Date dtOut = Formater.formatDate(stOut, "yyyy-MM-dd HH:mm");
     System.out.println("dtOut........"+dtOut);    
     String dtActualReal = Formater.formatDate(dtOut, "yyyy-MM-dd");
     String dtTimeActualReal = Formater.formatTimeLocale(dtOut, "HH:mm");
     System.out.println("dtActualReal........"+dtActualReal);
     System.out.println("dtTimeActualReal........"+dtTimeActualReal);
     Date dtTimeReal = Formater.formatDate(dtTimeActualReal, "HH:mm");
     System.out.println("dtTimeReal........"+dtTimeReal);
     
     String strCoba = "10";
     double coba = Double.parseDouble(strCoba);
     System.out.println("coba:::::::::::::::::::::::::::::::::::"+coba);*/
     
   }
   
    
}
