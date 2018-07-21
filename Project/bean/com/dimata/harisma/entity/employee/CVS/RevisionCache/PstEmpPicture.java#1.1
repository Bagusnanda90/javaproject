/*
 * PstEmpPicture.java
 *
 * Created on November 30, 2007, 11:01 AM
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
//import com.dimata. harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*; 

/**
 *
 * @author  yunny
 */
public class PstEmpPicture extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    public static final  String TBL_HR_EMP_PICTURE = "hr_emp_picture";//"HR_EMP_PICTURE";
    
    public static final  int FLD_PIC_EMP_ID = 0;
    public static final  int FLD_EMPLOYEE_ID = 1;
    public static final  int FLD_PIC = 2;
   
    public static final  String[] fieldNames = {
		"PIC_EMP_ID",
		"EMPLOYEE_ID",
		"PIC"
	 }; 
    
   public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_STRING
	 };
    
         
   
    /** Creates a new instance of PstEmpPicture */
    public PstEmpPicture() {
    }
    public PstEmpPicture(int i) throws DBException { 
		super(new PstEmpPicture()); 
    }

    public PstEmpPicture(String sOid) throws DBException { 
		super(new PstEmpPicture(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
   }
   
   public PstEmpPicture(long lOid) throws DBException { 
		super(new PstEmpPicture(0)); 
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
        return new PstEmpPicture().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_HR_EMP_PICTURE;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
    }
    
    public long fetchExc(Entity ent) throws Exception {
        EmpPicture empPicture = fetchExc(ent.getOID()); 
		ent = (Entity)empPicture; 
		return empPicture.getOID(); 
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpPicture) ent); 
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpPicture) ent); 
    }
    
    public static EmpPicture fetchExc(long oid) throws DBException{ 
		try{ 
			EmpPicture empPicture = new EmpPicture();
			PstEmpPicture pstEmpPicture = new PstEmpPicture(oid); 
			empPicture.setOID(oid);

			empPicture.setEmployeeId(pstEmpPicture.getlong(FLD_EMPLOYEE_ID));
                        empPicture.setPic(pstEmpPicture.getString(FLD_PIC));
			
			return empPicture; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpPicture(0),DBException.UNKNOWN); 
		} 
	}
    
  public static long insertExc(EmpPicture empPicture) throws DBException{ 
		try{ 
			PstEmpPicture pstEmpPicture = new PstEmpPicture(0);
                        pstEmpPicture.setLong(FLD_EMPLOYEE_ID, empPicture.getEmployeeId());
                        pstEmpPicture.setString(FLD_PIC, empPicture.getPic());
		        pstEmpPicture.insert(); 
			empPicture.setOID(pstEmpPicture.getlong(FLD_PIC_EMP_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpPicture(0),DBException.UNKNOWN); 
		}
		return empPicture.getOID();
	}
    
   
    public static long updateExc(EmpPicture empPicture) throws DBException{ 
		try{ 
			if(empPicture.getOID() != 0){ 
				PstEmpPicture pstEmpPicture = new PstEmpPicture(empPicture.getOID());

				pstEmpPicture.setLong(FLD_EMPLOYEE_ID, empPicture.getEmployeeId());
                                pstEmpPicture.setString(FLD_PIC, empPicture.getPic());
                            
				pstEmpPicture.update(); 
				return empPicture.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpPicture(0),DBException.UNKNOWN); 
		}
		return 0;
	}
    
	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstEmpPicture pstEmpPicture = new PstEmpPicture(oid);
			pstEmpPicture.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpPicture(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_EMP_PICTURE; 
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
				EmpPicture empPicture = new EmpPicture();
				resultToObject(rs, empPicture);
				lists.add(empPicture);
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
    
    private static void resultToObject(ResultSet rs, EmpPicture empPicture){
		try{
			empPicture.setOID(rs.getLong(PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID]));
                        empPicture.setPicEmpId(rs.getLong(PstEmpPicture.fieldNames[PstEmpPicture.FLD_EMPLOYEE_ID]));
			empPicture.setPic(rs.getString(PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC]));
		}catch(Exception e){ }
	}
    
    public static boolean checkOID(long empPictureId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_PICTURE + " WHERE " + 
						PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID] + " = '" + empPictureId + "'";

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
			String sql = "SELECT COUNT("+ PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID] + ") FROM " + TBL_HR_EMP_PICTURE;
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
			  	   EmpPicture empPicture = (EmpPicture)list.get(ls);
				   if(oid == empPicture.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
        
    public static long deleteByEmployee(long emplOID)
        {
            try{
                String sql = " DELETE FROM "+PstEmpPicture.TBL_HR_EMP_PICTURE +
                             " WHERE "+PstEmpPicture.fieldNames[PstEmpPicture.FLD_EMPLOYEE_ID] +
                             " = "+emplOID;

                int status = DBHandler.execUpdate(sql);
            }catch(Exception exc){
                    System.out.println("error delete language by employee "+exc.toString());
            }

            return emplOID;
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
    
    
    /**
     * Check if employee with this OID already exist
     * return true if exist, false otherwise
     * @param employeeId
     * @return
     */
    public static EmpPicture getObjEmpPicture(long employeeId) 
    {
        EmpPicture objEmpPicture = new EmpPicture();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID] +
                         ", " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_EMPLOYEE_ID] +
                         ", " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC] +
                         " FROM " + TBL_HR_EMP_PICTURE +
                         " WHERE " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_EMPLOYEE_ID] +
                         " = " + employeeId;
            
            System.out.println("SQL PstEmpPicture.getObjEmpPicture : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                objEmpPicture.setOID(rs.getLong(1));
                objEmpPicture.setEmployeeId(rs.getLong(2));
                objEmpPicture.setPic(rs.getString(3));
                
                return objEmpPicture;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            DBResultSet.close(dbrs);
            return objEmpPicture;
        }
    }        


  /*public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        
        /*Date netDate = new Date();*/
        /*EmpPicture empPicture = new EmpPicture();
        empPicture.setEmployeeId(123);
        empPicture.setPic("");
        try{
            PstEmpPicture.insertExc(empPicture);
            System.out.println("masuk tabel");
        }catch(Exception e){
            System.out.println("err tabel");
            System.out.println("Err"+e.toString());
        }*/
     
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
     
  //  }
   
}
