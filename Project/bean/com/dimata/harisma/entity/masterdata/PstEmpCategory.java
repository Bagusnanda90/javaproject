
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

public class PstEmpCategory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_EMP_CATEGORY = "hr_emp_category";//"HR_EMP_CATEGORY";

	public static final  int FLD_EMP_CATEGORY_ID = 0;
	public static final  int FLD_EMP_CATEGORY = 1;
	public static final  int FLD_DESCRIPTION = 2;
	public static final  int FLD_TYPE_FOR_TAX = 3;
        //update by satrya 2013-04-11
        public static final int FLD_ENTITLE_FOR_LEAVE=4;
        //update by satrya 2014-02-10
        public static final int FLD_ENTITLE_FOR_INSENTIF=5;
        
        public static final int FLD_CODE=6;
	public static final int FLD_CATEGORY_TYPE=7;
	public static final int FLD_ENTITLE_DP=8;
        
	public static final  String[] fieldNames = {
		"EMP_CATEGORY_ID",
		"EMP_CATEGORY",
		"DESCRIPTION",
                "TYPE_FOR_TAX",
                "ENTITLE_FOR_LEAVE",
                "ENTITLE_FOR_INSENTIF",
                "CODE",
                "CATEGORY_TYPE",
                "ENTITLE_DP"
                
	 }; 
        
	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
		TYPE_STRING,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_STRING,
                TYPE_INT,
                TYPE_INT
	 }; 
    public static int ENTITLE_NO=0;
   public static int ENTITLE_YES=1;
  
   public static final String[] fieldFlagEntitle={
       "NO",
       "YES"
   };
   
   public static int CATEGORY_LOKAL=0;
   public static int CATEGORY_ASING=1;
   public static final String[] fieldFlagCategory={
       "LOKAL",
       "ASING"
   };
   
	public PstEmpCategory(){
	}

	public PstEmpCategory(int i) throws DBException { 
		super(new PstEmpCategory()); 
	}

	public PstEmpCategory(String sOid) throws DBException { 
		super(new PstEmpCategory(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstEmpCategory(long lOid) throws DBException { 
		super(new PstEmpCategory(0)); 
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
		return TBL_HR_EMP_CATEGORY;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstEmpCategory().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		EmpCategory empcategory = fetchExc(ent.getOID()); 
		ent = (Entity)empcategory; 
		return empcategory.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((EmpCategory) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((EmpCategory) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static EmpCategory fetchExc(long oid) throws DBException{ 
		try{ 
			EmpCategory empcategory = new EmpCategory();
			PstEmpCategory pstEmpCategory = new PstEmpCategory(oid); 
			empcategory.setOID(oid);

			empcategory.setEmpCategory(pstEmpCategory.getString(FLD_EMP_CATEGORY));
			empcategory.setDescription(pstEmpCategory.getString(FLD_DESCRIPTION));
			empcategory.setTypeForTax(pstEmpCategory.getInt(FLD_TYPE_FOR_TAX));
                        //update by satrya 2013-04-11
                        empcategory.setEntitleLeave(pstEmpCategory.getInt(FLD_ENTITLE_FOR_LEAVE));
                        empcategory.setEntitleInsentif(pstEmpCategory.getInt(FLD_ENTITLE_FOR_INSENTIF));
                        empcategory.setCode(pstEmpCategory.getString(FLD_CODE));
                        empcategory.setCategoryType(pstEmpCategory.getInt(FLD_CATEGORY_TYPE));
                        empcategory.setEntitleDP(pstEmpCategory.getInt(FLD_ENTITLE_DP));
			return empcategory; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpCategory(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(EmpCategory empcategory) throws DBException{ 
		try{ 
			PstEmpCategory pstEmpCategory = new PstEmpCategory(0);

			pstEmpCategory.setString(FLD_EMP_CATEGORY, empcategory.getEmpCategory());
			pstEmpCategory.setString(FLD_DESCRIPTION, empcategory.getDescription());
			pstEmpCategory.setInt(FLD_TYPE_FOR_TAX, empcategory.getTypeForTax());
///update by satrya 2013-04-11
                        pstEmpCategory.setInt(FLD_ENTITLE_FOR_LEAVE, empcategory.getEntitleLeave());
                        //update by satrya 2014-02-10
                        pstEmpCategory.setInt(FLD_ENTITLE_FOR_INSENTIF, empcategory.getEntitleInsentif());
                        pstEmpCategory.setString(FLD_CODE, empcategory.getCode());
                        pstEmpCategory.setInt(FLD_CATEGORY_TYPE, empcategory.getCategoryType());
                        pstEmpCategory.setInt(FLD_ENTITLE_DP, empcategory.getEntitleDP());
			pstEmpCategory.insert(); 
			empcategory.setOID(pstEmpCategory.getlong(FLD_EMP_CATEGORY_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpCategory(0),DBException.UNKNOWN); 
		}
		return empcategory.getOID();
	}

	public static long updateExc(EmpCategory empcategory) throws DBException{ 
		try{ 
			if(empcategory.getOID() != 0){ 
				PstEmpCategory pstEmpCategory = new PstEmpCategory(empcategory.getOID());

				pstEmpCategory.setString(FLD_EMP_CATEGORY, empcategory.getEmpCategory());
				pstEmpCategory.setString(FLD_DESCRIPTION, empcategory.getDescription());
				pstEmpCategory.setInt(FLD_TYPE_FOR_TAX, empcategory.getTypeForTax());
//update by satrya 2013-04-11
                                pstEmpCategory.setInt(FLD_ENTITLE_FOR_LEAVE, empcategory.getEntitleLeave());
                                //update by satrya 2014-02-10
                                pstEmpCategory.setInt(FLD_ENTITLE_FOR_INSENTIF, empcategory.getEntitleInsentif());
                                pstEmpCategory.setString(FLD_CODE, empcategory.getCode());
                                pstEmpCategory.setInt(FLD_CATEGORY_TYPE, empcategory.getCategoryType());
                                pstEmpCategory.setInt(FLD_ENTITLE_DP, empcategory.getEntitleDP());
				pstEmpCategory.update(); 
				return empcategory.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpCategory(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstEmpCategory pstEmpCategory = new PstEmpCategory(oid);
			pstEmpCategory.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpCategory(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY; 
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
				EmpCategory empcategory = new EmpCategory();
				resultToObject(rs, empcategory);
				lists.add(empcategory);
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
        
          public static String[] listNameCategory(int limitStart,int recordToGet, String whereClause, String order){
                String[] listCategory=null;
                DBResultSet dbrs = null;
                try {
                        String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY; 
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
                        String strCategory="";
                        while(rs.next()) {
                                strCategory = strCategory + rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY])+",";
                        }
                        if(strCategory!=null && strCategory.length()>0){
                            strCategory = strCategory.substring(0,strCategory.length()-1);
                            listCategory= strCategory.split(",");
                        }
                        rs.close();
                      
                }catch(Exception e) {
                        System.out.println(e);
                }finally {
                        DBResultSet.close(dbrs);
                          return listCategory;
                }
        }
        //update by devin 2014-02-10
        public static Vector listDat(int limitStart,int recordToGet, String whereClause, String order, String Cat){
		Vector lists = new Vector(); 
                if(Cat==null || Cat.length()==0){
                   return new Vector();
                }
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY +  " WHERE " ; 
			if(Cat != null && Cat.length() > 0){
                          String category="";//""+PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_EMPCAT_ID];
        
                               category = category + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " IN (" + Cat +")";
                               sql = sql + category;
      
        
                        }	
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				EmpCategory empcategory = new EmpCategory();
				resultToObject(rs, empcategory);
				lists.add(empcategory);
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
        /**
         * digunakan khusus pencarian emp category di public leave
         * @param limitStart
         * @param recordToGet
         * @param whereClause
         * @param order
         * @return 
         */
        //update by devin 2014-02-10
      public static Vector listCategory(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY; 
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
				EmpCategory empcategory = new EmpCategory();
				resultToObject(rs, empcategory);
				lists.add(empcategory);
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
      
      public static Hashtable listt(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable listt = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                EmpCategory empCategory = new EmpCategory();
               resultToObject(rs, empCategory);
                listt.put(""+empCategory.getOID(), empCategory.getEmpCategory());
            }
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return listt;
        }
      
    }
      
    public static Hashtable<String, EmpCategory> listMap(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable listt = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                EmpCategory empCategory = new EmpCategory();
               resultToObject(rs, empCategory);
                listt.put(""+empCategory.getOID(), empCategory);
            }
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return listt;
        }
      
    }
     
      
      //update by devin 2014-02-10
      /**
       * digunakan untuk mengambil data  di tabel hr_public_leave
       * @param oidPublicHoliday
       * @return 
       */
      public  static String getDataCat(long oidPublicHoliday)
      
      {
          String list = "";
          if(oidPublicHoliday ==0){
              return "";
          }
          DBResultSet dbrs =null;
          try{
              String sql  = "SELECT " + PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_EMPCAT_ID] + " FROM " + PstPublicLeave.TBL_PUBLIC_LEAVE + " WHERE " + PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_HOLIDAY_ID] + " = " + oidPublicHoliday;
             dbrs = DBHandler.execQueryResult(sql);
             ResultSet rs = dbrs.getResultSet();
             while(rs.next()){
                // PublicLeave publicLeave = new  PublicLeave();
                 //publicLeave.setEmpCat(rs.getLong(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_EMPCAT_ID]));
                // list.add(publicLeave);
                 list=list+""+rs.getLong(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_EMPCAT_ID])+",";
               
             }
             if(list!=null && list.length()>0){
                 list=list.substring(0, list.length()-1);
             }
          }catch(Exception exc){
              
          }finally{
              DBResultSet.close(dbrs);
              return list;
          }
      }
        //update by devin 
      /**
       * untuk mencari oid public leave id
       * @param limitStart
       * @param recordToGet
       * @param whereClause
       * @param order
       * @return 
       */
      public static int getNilaiData(long oidPublicHoliday){
          int list=0;
          if(oidPublicHoliday==0){
              return 0;
          }
          DBResultSet dbrs=null;
          try{
             String sql = " SELECT COUNT("+PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_LEAVE_ID]+") FROM " + PstPublicLeave.TBL_PUBLIC_LEAVE + " WHERE " + PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_HOLIDAY_ID] + " = " + oidPublicHoliday; 
             dbrs = DBHandler.execQueryResult(sql);
             ResultSet rs = dbrs.getResultSet();
             while(rs.next()){
                 list=rs.getInt(1);
             }
          }catch(Exception exc){
              
          }finally{
              DBResultSet.close(dbrs);
              return list;
          }
          
      } 
public static Hashtable getHashListEmpSchedule(int limitStart,int recordToGet, String whereClause, String order){
		Hashtable lists = new Hashtable(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY; 
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
				EmpCategory empcategory = new EmpCategory();
				resultToObject(rs, empcategory);
				lists.put(empcategory.getOID(),empcategory);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return lists;
	}
/**
 * create by satrya ramayu
 * @return 
 */
public static Hashtable hashListEmpCategory(){
 Hashtable hashListEmpCat= new Hashtable();
    DBResultSet dbrs = null;
    try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY; 

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                    EmpCategory empcategory = new EmpCategory();
                    resultToObject(rs, empcategory);
                    hashListEmpCat.put(empcategory.getOID(), empcategory);
            }
            rs.close();


    }catch(Exception e) {
            System.out.println(e);
    }finally {
            DBResultSet.close(dbrs);
    }
            return hashListEmpCat;
}


	private static void resultToObject(ResultSet rs, EmpCategory empcategory){
		try{
			empcategory.setOID(rs.getLong(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]));
			empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
			empcategory.setDescription(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_DESCRIPTION]));
			empcategory.setTypeForTax(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_TYPE_FOR_TAX]));
                       //update by satrya 2013-04-11
                        empcategory.setEntitleLeave(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_ENTITLE_FOR_LEAVE]));
                        //update by satrya 2014-02-10
                        empcategory.setEntitleInsentif(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_ENTITLE_FOR_INSENTIF]));
                        empcategory.setCode(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_CODE]));
                        empcategory.setCategoryType(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_CATEGORY_TYPE]));
                        empcategory.setEntitleDP(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_ENTITLE_DP]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long empCategoryId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_CATEGORY + " WHERE " + 
						PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = " + empCategoryId;
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
			String sql = "SELECT COUNT("+ PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + ") FROM " + TBL_HR_EMP_CATEGORY;
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
			  	   EmpCategory empcategory = (EmpCategory)list.get(ls);
				   if(oid == empcategory.getOID())
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
    	if(PstEmployee.checkEmpCategory(oid))
            return true;
    	else{
            if(PstCareerPath.checkEmpCategory(oid))
            	return true;
            else
                return false;

        }
    }
    /**
     * update by satrya 2014-02-10
     * @return 
     */
        public static Hashtable getHashTableEmpCatId(){
                Hashtable hashTblCategory = new Hashtable();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + PstEmpCategory.TBL_HR_EMP_CATEGORY; 
				sql = sql + " WHERE " + PstEmpCategory.fieldNames[PstEmpCategory.FLD_ENTITLE_FOR_INSENTIF]+"="+PstEmpCategory.ENTITLE_YES;
				sql = sql + " LIMIT " + 0 + ","+ 1 ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				hashTblCategory.put(rs.getLong(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]),true);
			}
			rs.close();
			return hashTblCategory;
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return hashTblCategory;
	}
}
