/*
 * PstPayComponentIn.java
 *
 * Created on June 13, 2007, 9:19 AM
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

//import com.dimata. harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author  yunny
 */
public class PstPayComponentIn extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
     public static final  String TBL_PAY_COMPONENT_IN = "pay_component_in";//"PAY_COMPONENT_IN";
     
      public static final  int FLD_COMP_ID = 0;
      public static final  int FLD_COMP_CODE = 1;
      public static final  int FLD_COMP_NAME= 2;
      
      
       public static final  String[] fieldNames = {
                "COMP_ID",
                "COMP_CODE",
                "COMP_NAME"
       };
       
        public static final  int[] fieldTypes = {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_STRING,
            TYPE_STRING
        };
    
    /** Creates a new instance of PstPayComponentIn */
    public PstPayComponentIn() {
    }
    
    public PstPayComponentIn(int i) throws DBException { 
		super(new PstPayComponentIn()); 
    }
    
    public PstPayComponentIn(String sOid) throws DBException { 
		super(new PstPayComponentIn(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}
    
     public PstPayComponentIn(long lOid) throws DBException { 
		super(new PstPayComponentIn(0)); 
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
        PayComponentIn payComponentIn = fetchExc(ent.getOID()); 
		ent = (Entity)payComponentIn; 
		return payComponentIn.getOID(); 
        
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
        return new PstPayComponentIn().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_PAY_COMPONENT_IN;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((PayComponentIn) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayComponentIn) ent);
    }
    
     public static PayComponentIn fetchExc(long oid) throws DBException{ 
		try{ 
			PayComponentIn payComponentIn = new PayComponentIn();
			PstPayComponentIn pstPayComponentIn = new PstPayComponentIn(oid); 
			payComponentIn.setOID(oid);
                        payComponentIn.setCompCode(pstPayComponentIn.getString(FLD_COMP_CODE));
                        payComponentIn.setCompName(pstPayComponentIn.getString(FLD_COMP_NAME));
                   	return payComponentIn;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayComponentIn(0),DBException.UNKNOWN); 
		} 
	}
     
     public static long insertExc(PayComponentIn payComponentIn) throws DBException{ 
		try{ 
                        PstPayComponentIn pstPayComponentIn = new PstPayComponentIn(0);
                	pstPayComponentIn.setString(FLD_COMP_CODE, payComponentIn.getCompCode());
                        pstPayComponentIn.setString(FLD_COMP_NAME, payComponentIn.getCompName());
                        //pstPayExecutive.setString(FLD_EXECUTIVE_NAME, payExecutive.getExecutiveName());
             	
                pstPayComponentIn.insert();
			payComponentIn.setOID(pstPayComponentIn.getlong(FLD_COMP_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayComponent(0),DBException.UNKNOWN); 
		}
		return payComponentIn.getOID();
	}
     
     public static long updateExc(PayComponentIn payComponentIn) throws DBException{ 
		try{ 
		  if(payComponentIn.getOID() != 0){ 
                        PstPayComponentIn pstPayComponentIn = new PstPayComponentIn(payComponentIn.getOID());
                        pstPayComponentIn.setString(FLD_COMP_CODE, payComponentIn.getCompCode());
                        pstPayComponentIn.setString(FLD_COMP_NAME, payComponentIn.getCompName());
                       /* pstPayExecutive.setString(FLD_EXECUTIVE_NAME, payExecutive.getExecutiveName());*/
                        
                pstPayComponentIn.update();
				return payComponentIn.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayComponentIn(0),DBException.UNKNOWN); 
		}
		return 0;
	}
     
      public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPayComponentIn pstPayComponentIn = new PstPayComponentIn(oid);
			pstPayComponentIn.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayComponentIn(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PAY_COMPONENT_IN; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        //System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				PayComponentIn payComponentIn = new PayComponentIn();
				resultToObject(rs, payComponentIn);
				lists.add(payComponentIn);
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
      
       public static void resultToObject(ResultSet rs, PayComponentIn payComponentIn){
		try{
			payComponentIn.setOID(rs.getLong(PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_ID]));
                        payComponentIn.setCompCode(rs.getString(PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_CODE]));
                        payComponentIn.setCompName(rs.getString(PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_NAME]));
                        
		}catch(Exception e){ }
	}
       
        public static boolean checkOID(long compId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_COMPONENT_IN + " WHERE " + 
						PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_ID] + " = '" + compId+"'";

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
			String sql = "SELECT COUNT("+ PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_ID] + ") FROM " + TBL_PAY_COMPONENT_IN;
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
			  	   PayComponentIn payComponentIn = (PayComponentIn)list.get(ls);
				   if(oid == payComponentIn.getOID())
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

     /**
    * this method used to clear all tabel pay_component_in
    * karena berfungsi sebagai penampung sementara
    * created by Yunny
    */
   public static void deleteAll()
    throws DBException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = getStatement(connection);
            String s1 = "DELETE FROM " + TBL_PAY_COMPONENT_IN;
            statement.executeUpdate(s1);
            //dbLog.info(s1);
        }
        catch(SQLException sqlexception) {
            sqlexception.printStackTrace(System.err);
            throw new DBException(null, sqlexception);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /* This method used to get component which have IN
     * created By Yunny
     */
     public static Vector getCompIn(){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT DISTINCT * FROM " + TBL_PAY_COMPONENT_IN+
                                     " GROUP BY "+PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_NAME]; 
			
			dbrs = DBHandler.execQueryResult(sql);
                        System.out.println("SQL In"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				PayComponentIn payComponentIn = new PayComponentIn();
				//resultToObject(rs, payComponentIn);
                                payComponentIn.setOID(rs.getLong(PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_ID]));
                                payComponentIn.setCompCode(rs.getString(PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_CODE]));
                                payComponentIn.setCompName(rs.getString(PstPayComponentIn.fieldNames[PstPayComponentIn.FLD_COMP_NAME]));
				lists.add(payComponentIn);
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
       
      
}
