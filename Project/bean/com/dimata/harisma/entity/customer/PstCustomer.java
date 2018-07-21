/*
 * PstCustomer.java
 *
 * Created on April 3, 2002, 9:29 AM
 */

package com.dimata.harisma.entity.customer;

/**
 *
 * @author  ktanjana
 * @version
 */

import com.dimata.harisma.entity.customer.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;

public class  PstCustomer extends DBHandler implements I_DBInterface, I_DBType, I_Persintent  {
    
    public static final String TBL_DRS_CUSTOMER = "drs_customer";
    public static final int FLD_CUSTOMER_ID		= 0;
    public static final int FLD_CUSTOMER_NAME		= 1;
    public static final int FLD_ADDRESS 		= 2;
    public static final int FLD_CITY 		        = 3;
    public static final int FLD_POSTAL_CODE		= 4;
    public static final int FLD_PROVINCE		= 5;
    public static final int FLD_COUNTRY 		= 6;
    public static final int FLD_PHONE1 		        = 7;
    public static final int FLD_PHONE2		        = 8;
    public static final int FLD_PHONE3		        = 9;
    public static final int FLD_FAX 		        = 10;
    public static final int FLD_EMAIL 		        = 11;
    public static final int FLD_PERSON_NAME		= 12;
    public static final int FLD_PERSON_PHONE		= 13;
    public static final int FLD_REG_DATE 		= 14;
    public static final int FLD_UPDATE_DATE 		= 15;
    public static final int FLD_NOTE 		        = 16;
    
    
    public static  final String[] fieldNames = {
        "CUSTOMER_ID", "CUSTOMER_NAME", "ADDRESS", "CITY","POSTAL_CODE", "PROVINCE", "COUNTRY",
        "PHONE1", "PHONE2", "PHONE3", "FAX","EMAIL", "PERSON_NAME", "PERSON_PHONE", "REG_DATE",
        "UPDATE_DATE","NOTE" };

    
    public static int[] fieldTypes = {
        TYPE_PK + TYPE_LONG + TYPE_ID, TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING,
        TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING,
        TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_DATE,  TYPE_DATE, TYPE_STRING
    };
    
    /** Creates new PstCustomer */
    public PstCustomer() {
    }
    
    public PstCustomer(int i) throws DBException {
        super(new PstCustomer());
    }
    
    
    public PstCustomer(String sOid) throws DBException {
        super(new PstCustomer(0));
        if(!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    
    public PstCustomer(long lOid) throws DBException {
        super(new PstCustomer(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        }catch(Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        
        if(!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
        
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_DRS_CUSTOMER;
    }
    
    public String getPersistentName() {
        return new PstCustomer().getClass().getName();
    }
    
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    
    public long delete(Entity ent) {
        return delete((Customer) ent);
    }
    
    public long insert(Entity ent) {
        return PstCustomer.insert((Customer) ent);
    }
    
    
    public long update(Entity ent) {
        return update((Customer) ent);
    }
    
    public long fetch(Entity ent) {
        Customer entObj = PstCustomer.fetch(ent.getOID());
        ent = (Entity)entObj;
        return ent.getOID();
    }
    
    
    public static Customer fetch(long oid) {
        Customer entObj = new Customer();
        try {
            PstCustomer pstObj = new PstCustomer(oid);
            entObj.setOID(oid);
            entObj.setCustomerName(pstObj.getString(FLD_CUSTOMER_NAME));
            entObj.setAddress(pstObj.getString(FLD_ADDRESS));
            entObj.setCity(pstObj.getString(FLD_CITY));
            entObj.setPostCode(pstObj.getString(FLD_POSTAL_CODE));
            entObj.setProvince(pstObj.getString(FLD_PROVINCE));
            entObj.setCountry(pstObj.getString(FLD_COUNTRY));
            entObj.setPhone1(pstObj.getString(FLD_PHONE1));
            entObj.setPhone2(pstObj.getString(FLD_PHONE2));
            entObj.setPhone3(pstObj.getString(FLD_PHONE3));
            entObj.setFax(pstObj.getString(FLD_FAX));
            entObj.setEmail(pstObj.getString(FLD_EMAIL));
            entObj.setPersonName(pstObj.getString(FLD_PERSON_NAME));
            entObj.setPersonPhone(pstObj.getString(FLD_PERSON_PHONE));
            entObj.setRegDate(pstObj.getDate(FLD_REG_DATE));
            entObj.setUpdateDate(pstObj.getDate(FLD_UPDATE_DATE));
            entObj.setNote(pstObj.getString(FLD_NOTE));
        }
        catch(DBException e) {
            System.out.println(e);
        }
        return entObj;
    }
    
    
    public static long insert(Customer entObj) {
        try{
            PstCustomer pstObj = new PstCustomer(0);
            pstObj.setString(FLD_CUSTOMER_NAME, entObj.getCustomerName());
            pstObj.setString(FLD_ADDRESS, entObj.getAddress());
            pstObj.setString(FLD_CITY, entObj.getCity());
            pstObj.setString(FLD_POSTAL_CODE, entObj.getPostCode());
            pstObj.setString(FLD_PROVINCE, entObj.getProvince());
            pstObj.setString(FLD_COUNTRY, entObj.getCountry());
            pstObj.setString(FLD_PHONE1, entObj.getPhone1());
            pstObj.setString(FLD_PHONE2, entObj.getPhone2());
            pstObj.setString(FLD_PHONE3, entObj.getPhone3());
            pstObj.setString(FLD_FAX, entObj.getFax());
            pstObj.setString(FLD_EMAIL, entObj.getEmail());
            pstObj.setString(FLD_PERSON_NAME, entObj.getPersonName());
            pstObj.setString(FLD_PERSON_PHONE, entObj.getPersonPhone());
            pstObj.setDate(FLD_REG_DATE, entObj.getRegDate());
            pstObj.setDate(FLD_UPDATE_DATE, entObj.getUpdateDate());
            pstObj.setString(FLD_NOTE, entObj.getNote());
            
            
            pstObj.insert();
            entObj.setOID(pstObj.getlong(FLD_CUSTOMER_ID));
            return entObj.getOID();
        }
        catch(DBException e) {
            System.out.println(e);
        }
        return 0;
    }
    
    
    public static long update(Customer entObj) {
        if( (entObj!=null) && (entObj.getOID() != 0)) {
            try {
            PstCustomer pstObj = new PstCustomer(entObj.getOID());
            pstObj.setString(FLD_CUSTOMER_NAME, entObj.getCustomerName());
            pstObj.setString(FLD_ADDRESS, entObj.getAddress());
            pstObj.setString(FLD_CITY, entObj.getCity());
            pstObj.setString(FLD_POSTAL_CODE, entObj.getPostCode());
            pstObj.setString(FLD_PROVINCE, entObj.getProvince());
            pstObj.setString(FLD_COUNTRY, entObj.getCountry());
            pstObj.setString(FLD_PHONE1, entObj.getPhone1());
            pstObj.setString(FLD_PHONE2, entObj.getPhone2());
            pstObj.setString(FLD_PHONE3, entObj.getPhone3());
            pstObj.setString(FLD_FAX, entObj.getFax());
            pstObj.setString(FLD_EMAIL, entObj.getEmail());
            pstObj.setString(FLD_PERSON_NAME, entObj.getPersonName());
            pstObj.setString(FLD_PERSON_PHONE, entObj.getPersonPhone());
            pstObj.setDate(FLD_REG_DATE, entObj.getRegDate());
            pstObj.setDate(FLD_UPDATE_DATE, entObj.getUpdateDate());
            pstObj.setString(FLD_NOTE, entObj.getNote());
                
                pstObj.update();
                return entObj.getOID();
            }catch(Exception e) {
                System.out.println(e);
            }
        }
        return 0;
    }
    
    
    public static long delete(long oid) {
        try {
            PstCustomer pstObj = new PstCustomer(oid);
            pstObj.delete();
            return oid;
        }catch(Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs=null;
        try{
            int count = 0;
            String sql = " SELECT COUNT("+fieldNames[FLD_CUSTOMER_ID] +") AS NRCOUNT" + 
                         " FROM " + TBL_DRS_CUSTOMER;


            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;

            //System.out.println(sql);
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                count = rs.getInt(1);
            }            
             
            return count;
        }
        catch (Exception exc){
            System.out.println("getCount "+ exc);
            return 0;
        }
        finally{
            DBResultSet.close(dbrs);
        }
        
    }
    
    public static  Vector listPartObj(int start , int recordToGet, String whereClause, String order)
    {
        Vector lists = new Vector();        
        DBResultSet dbrs=null;
        try {
            String sql = "SELECT "+fieldNames[FLD_CUSTOMER_ID] +
                         ", " + fieldNames[FLD_CUSTOMER_NAME] +
                         ", " + fieldNames[FLD_ADDRESS] +
                         ", " + fieldNames[FLD_CITY] +
                         ", " + fieldNames[FLD_POSTAL_CODE] +
                         ", " + fieldNames[FLD_PROVINCE] +
                         ", " + fieldNames[FLD_COUNTRY] +
                         ", " + fieldNames[FLD_PHONE1] +
                         ", " + fieldNames[FLD_PHONE2] +
                         ", " + fieldNames[FLD_PHONE3] +
                         ", " + fieldNames[FLD_FAX] +
                         ", " + fieldNames[FLD_EMAIL] +
                         ", " + fieldNames[FLD_PERSON_NAME] +
                         ", " + fieldNames[FLD_PERSON_PHONE] +           
                         ", " + fieldNames[FLD_REG_DATE] +
                         ", " + fieldNames[FLD_UPDATE_DATE] +
                         ", " + fieldNames[FLD_NOTE] +
                         " FROM " + TBL_DRS_CUSTOMER;


            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;

            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;

            
            if((start == 0)&&(recordToGet == 0))
                sql = sql + "" ;  //nothing to do
            else
                sql = sql + " LIMIT " + start + ","+ recordToGet ;

            //System.out.println(sql);
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                Customer appPriv = new Customer();
                resultToObject(rs, appPriv);
                lists.add(appPriv);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);            
       }
       finally{
            DBResultSet.close(dbrs);
       }
       return new Vector();
    }

    
    private static void resultToObject(ResultSet rs, Customer appPriv) {
        try {
            appPriv.setOID(rs.getLong(fieldNames[FLD_CUSTOMER_ID]));
            appPriv.setCustomerName(rs.getString(fieldNames[FLD_CUSTOMER_NAME]));
            appPriv.setAddress(rs.getString(fieldNames[FLD_ADDRESS]));
            appPriv.setCity(rs.getString(fieldNames[FLD_CITY]));
            appPriv.setPostCode(rs.getString(fieldNames[FLD_POSTAL_CODE]));
            appPriv.setProvince(rs.getString(fieldNames[FLD_PROVINCE]));
            appPriv.setCountry(rs.getString(fieldNames[FLD_COUNTRY]));
            appPriv.setPhone1(rs.getString(fieldNames[FLD_PHONE1]));
            appPriv.setPhone2(rs.getString(fieldNames[FLD_PHONE2]));
            appPriv.setPhone3(rs.getString(fieldNames[FLD_PHONE3]));
            appPriv.setFax(rs.getString(fieldNames[FLD_FAX]));
            appPriv.setEmail(rs.getString(fieldNames[FLD_EMAIL]));
            appPriv.setPersonName(rs.getString(fieldNames[FLD_PERSON_NAME]));
            appPriv.setPersonPhone(rs.getString(fieldNames[FLD_PERSON_PHONE]));
            
            Date now = new Date();
            String str = rs.getString(fieldNames[FLD_REG_DATE]);
           // if(str==null?str:PstCustomer.getPstCustomer(now));
               
            Date dt = Formater.formatDate(str, "yyyy-MM-dd HH:mm:ss");
            //System.out.println("--- ^_^ :"+dt);
            appPriv.setRegDate(dt);
            
            String st = rs.getString(fieldNames[FLD_UPDATE_DATE]);
            Date dte = Formater.formatDate(st, "yyyy-MM-dd hh:mm:ss");
            appPriv.setUpdateDate(dte);
            
            appPriv.setNote(rs.getString(fieldNames[FLD_NOTE]));
        }catch(Exception e){
            System.out.println("resultToObject() appgroup " + e.toString());
        }
    }
    
    public static Vector listall(){ 
		return listPartObj(0, 1000, "","");
	}
    
    
    
}
