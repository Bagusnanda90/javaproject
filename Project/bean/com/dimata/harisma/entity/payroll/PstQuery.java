/*
 * PstQuery.java
 *
 * Created on August 3, 2007, 1:37 PM
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

/* package harisma */
//import com.dimata. harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.payroll.*;
//import com.dimata.harisma.entity.locker.*;

/**
 *
 * @author  yunny
 */
public class PstQuery extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language  {
     public static final  String TBL_HR_METADATA= "hr_metadata";//"HR_METADATA";

	public static final  int FLD_QUERY_ID= 0;
	public static final  int FLD_REPORT_TITLE= 1;
	public static final  int FLD_REPORT_SUBTITLE= 2;
	public static final  int FLD_WHERE_PARAM= 3;
	public static final  int FLD_ORDER_BY_PARAM= 4;
	public static final  int FLD_GROUP_BY_PARAM= 5;
	public static final  int FLD_QUERY= 6;
        public static final  int FLD_SUB_QUERY= 7;
	public static final  int FLD_DATE= 8;
	public static final  int FLD_DESCRIPTION= 9;
	
        
        public static final  String[] fieldNames = {
		"QUERY_ID",
		"REPORT_TITLE",
		"REPORT_SUBTITLE",
		"WHERE_PARAM",
		"ORDER_BY_PARAM",
		"GROUP_BY_PARAM",
		"QUERY",
                "SUB_QUERY",
		"DATE",
		"DESCRIPTION",
         };
         
        public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_DATE,
		TYPE_STRING,		
	 };

         
    
    /** Creates a new instance of PstQuery */
    public PstQuery() {
    }
    public PstQuery(int i) throws DBException { 
		super(new PstQuery()); 
	}
    
     public PstQuery(String sOid) throws DBException { 
		super(new PstQuery(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
    }
    public PstQuery(long lOid) throws DBException { 
		super(new PstQuery(0)); 
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
         Query query= fetchExc(ent.getOID()); 
         ent = (Entity)query; 
	 return query.getOID(); 
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
         return new PstQuery().getClass().getName(); 
	
     }
     
     public String getTableName() {
         return TBL_HR_METADATA;
     }
     
     public long insertExc(Entity ent) throws Exception {
         return insertExc((Query) ent); 
     }
     
     public long updateExc(Entity ent) throws Exception {
         return updateExc((Query) ent); 
     }
     
     public static Query fetchExc(long oid) throws DBException{ 
		try{ 
			Query query= new Query();
			PstQuery pstQuery= new PstQuery(oid); 
			query.setOID(oid);

			query.setReportTitle(pstQuery.getString(FLD_REPORT_TITLE));
                        query.setReportSubtitle(pstQuery.getString(FLD_REPORT_SUBTITLE));
                        query.setWhereParam(pstQuery.getString(FLD_WHERE_PARAM));
                        query.setOrderByParam(pstQuery.getString(FLD_ORDER_BY_PARAM));
                        query.setGroupByParam(pstQuery.getString(FLD_GROUP_BY_PARAM));
                        query.setQuery(pstQuery.getString(FLD_QUERY));
                        query.setSubQuery(pstQuery.getString(FLD_SUB_QUERY));
                        query.setDate(pstQuery.getDate(FLD_DATE));
                        query.setDescription(pstQuery.getString(FLD_DESCRIPTION));
   			return query;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayGeneral(0),DBException.UNKNOWN); 
		} 
	}
     
     public static long insertExc(Query query) throws DBException{ 
		try{ 
			PstQuery pstQuery= new PstQuery(0);
                        pstQuery.setString(FLD_REPORT_TITLE, query.getReportTitle());
                        pstQuery.setString(FLD_REPORT_SUBTITLE, query.getReportSubtitle());
                        pstQuery.setString(FLD_WHERE_PARAM, query.getWhereParam());
                        pstQuery.setString(FLD_ORDER_BY_PARAM, query.getOrderByParam());
                        pstQuery.setString(FLD_GROUP_BY_PARAM, query.getGroupByParam());
                        pstQuery.setString(FLD_QUERY, query.getQuery());
                        pstQuery.setString(FLD_SUB_QUERY, query.getSubQuery());
                        pstQuery.setDate(FLD_DATE, query.getDate());
                        pstQuery.setString(FLD_DESCRIPTION, query.getDescription());
      
                pstQuery.insert();
			query.setOID(pstQuery.getlong(FLD_QUERY_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstQuery(0),DBException.UNKNOWN); 
		}
		return query.getOID();
	}
     
     public static long updateExc(Query query) throws DBException{ 
		try{ 
		  if(query.getOID() != 0){ 
                        PstQuery pstQuery = new PstQuery(query.getOID());
                        pstQuery.setString(FLD_REPORT_TITLE, query.getReportTitle());
                        pstQuery.setString(FLD_REPORT_SUBTITLE, query.getReportSubtitle());
                        pstQuery.setString(FLD_WHERE_PARAM, query.getWhereParam());
                        pstQuery.setString(FLD_ORDER_BY_PARAM, query.getOrderByParam());
                        pstQuery.setString(FLD_GROUP_BY_PARAM, query.getGroupByParam());
                        pstQuery.setString(FLD_QUERY, query.getQuery());
                        pstQuery.setString(FLD_SUB_QUERY, query.getSubQuery());
                        pstQuery.setDate(FLD_DATE, query.getDate());
                        pstQuery.setString(FLD_DESCRIPTION, query.getDescription());
                        
                pstQuery.update();
			return query.getOID();
			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstQuery(0),DBException.UNKNOWN); 
		}
		return 0;
	}
     
      public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstQuery pstQuery= new PstQuery(oid);
			pstQuery.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstQuery(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_METADATA; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				Query query= new Query();
				resultToObject(rs, query);
				lists.add(query);
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

     
     public static void resultToObject(ResultSet rs, Query query){
		try{
			query.setOID(rs.getLong(PstQuery.fieldNames[PstQuery.FLD_QUERY_ID]));
                        query.setReportTitle(rs.getString(PstQuery.fieldNames[PstQuery.FLD_REPORT_TITLE]));
                        query.setReportSubtitle(rs.getString(PstQuery.fieldNames[PstQuery.FLD_REPORT_SUBTITLE]));
                        query.setWhereParam(rs.getString(PstQuery.fieldNames[PstQuery.FLD_WHERE_PARAM]));
                        query.setOrderByParam(rs.getString(PstQuery.fieldNames[PstQuery.FLD_ORDER_BY_PARAM]));
                        query.setGroupByParam(rs.getString(PstQuery.fieldNames[PstQuery.FLD_GROUP_BY_PARAM]));
                        query.setQuery(rs.getString(PstQuery.fieldNames[PstQuery.FLD_QUERY]));
                        query.setSubQuery(rs.getString(PstQuery.fieldNames[PstQuery.FLD_SUB_QUERY]));
                        query.setDate(rs.getDate(PstQuery.fieldNames[PstQuery.FLD_DATE]));
                        query.setDescription(rs.getString(PstQuery.fieldNames[PstQuery.FLD_DESCRIPTION]));
                        
        	}catch(Exception e){ }
	}
    
     
     public static boolean checkOID(long queryId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_METADATA + " WHERE " + 
						PstQuery.fieldNames[PstQuery.FLD_QUERY_ID] + " = '" + queryId+"'";

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
			String sql = "SELECT COUNT("+ PstQuery.fieldNames[PstQuery.FLD_QUERY_ID] + ") FROM " + TBL_HR_METADATA;
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
		String order = "";
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   Query query= (Query)list.get(ls);
				   if(oid == query.getOID())
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
    /*This methode used to get columnHeader from query
     * @param = sql
     *created By Yunny
     */
      
     public static Vector getHeaderCoulumn(String sqlQuery) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        String sql = "";
          try {
             sql = sql + sqlQuery;
           /*if(whereClause != null && whereClause.length()>0){
                whereClause = " AND "+ whereClause.substring(0,whereClause.length()-4);
                sql = sql + whereClause;
                 //sql = sql + " WHERE " + whereClause;
            }*/
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
           // while(rs.next()) {
              Vector headerColumn = new Vector(1,1);
              ResultSetMetaData rsmd = rs.getMetaData();  
              int numberOfColumns = rsmd.getColumnCount();
              if(numberOfColumns > 0){
                for (int i = 0; i < numberOfColumns; i++) {
                    result.add(""+rsmd.getColumnLabel(i+1));
                }
              }
               //result.add(headerColumn);
           // }
            
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
        
    }
     
      /*This methode used to get columnHeader from query
     * @param = sql
     * @param = whereClause
     *created By Yunny
     */
      
     public static Vector getContent(String sqlQuery,String whereClause) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        String sql = "";
          try {
             sql = sql + sqlQuery;
           if(whereClause != null && whereClause.length()>0){
                //whereClause =  whereClause;
              //  sql = sql + whereClause;
                 sql = sql + " WHERE " + whereClause;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) {
              Vector content = new Vector(1,1);
              ResultSetMetaData rsmd = rs.getMetaData();  
              int numberOfColumns = rsmd.getColumnCount();
              if(numberOfColumns > 0){
                for (int i = 0; i < numberOfColumns; i++) {
                    content.add(""+rs.getString(rsmd.getColumnLabel(i+1)));
                }
              }
               result.add(content);
            }
            
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
        
    }
      /*This methode used to get columnHeader from query
     * @param = sql
     *created By Yunny
     */
      
     public static Vector getSubQuery(String sqlQuery,String whereClause) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        String sql = "";
          try {
             sql = sql + sqlQuery;
             if(whereClause != null && whereClause.length()>0){
                //whereClause =  whereClause;
              //  sql = sql + whereClause;
                 sql = sql + " WHERE " + whereClause;
            }
            
          
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) {
              ResultSetMetaData rsmd = rs.getMetaData();  
              int numberOfColumns = rsmd.getColumnCount();
              if(numberOfColumns > 0){
                for (int i = 0; i < numberOfColumns; i++) {
                    result.add(""+rs.getString(rsmd.getColumnLabel(i+1)));
                }
              }
               //result.add(content);
            }
            
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
        
    }
}
