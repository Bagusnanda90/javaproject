/*
 * PstSalaryLevelDetail.java
 *
 * Created on March 31, 2007, 1:44 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
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
import com.dimata.system.entity.system.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;

/**
 *
 * @author  autami
 */
public class PstSalaryLevelDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_PAY_LEVEL_COM =  "pay_level_comp";//"PAY_LEVEL_COMP";
    
    public static final  int FLD_PAY_LEVEL_COM_ID     = 0;
    public static final  int FLD_LEVEL_CODE           = 1;
    public static final  int FLD_COMP_CODE            = 2;
    public static final  int FLD_FORMULA              = 3;
    public static final  int FLD_SORT_IDX             = 4;
    public static final  int FLD_PAY_PERIOD          = 5;
    public static final  int FLD_TAKE_HOME_PAY        = 6;
    public static final  int FLD_COPY_DATA            = 7;
    public static final  int FLD_COMPONENT_ID            = 8;
    
    
    public static final  String[] fieldNames = {
                "PAY_LEVEL_COM_ID",
		"LEVEL_CODE",
		"COMP_CODE",
		"FORMULA",
                "SORT_IDX",
                "PAY_PERIOD",
                "TAKE_HOME_PAY",
                "COPY_DATA",
                "COMPONENT_ID"
                
    };
      
    public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,                
                TYPE_INT,
                TYPE_LONG
    };
    
    //value for Pay Period
    public static final int PERIODE_WEEKLY	= 0;
    public static final int PERIODE_MONTHLY 	= 1;
    public static final int PERIODE_YEAR 	= 2;

    public static final String[] periodKey = {"Weekly","Monthly", "Yearly"};
    public static final int[] periodValue = {0,1,2};
    
    //value for Take Home Pay
    public static final int NO_TAKE 		= 0;
    public static final int YES_TAKE 		= 1;

    public static final String[] takeKey = {"No","Yes"};
    public static final int[] takeValue = {0,1};
    
    //value for Take Home Pay
    public static final int NO_COPY 		= 0;
    public static final int YES_COPY 		= 1;

    public static final String[] copyKey = {"No","Yes"};
    public static final int[] copyValue = {0,1};
    
    /** Creates a new instance of PstSalaryLevelDetail */
    public PstSalaryLevelDetail() {
    }
    
    public PstSalaryLevelDetail(int i) throws DBException { 
	super(new PstSalaryLevelDetail()); 
    }
    
    public PstSalaryLevelDetail(String sOid) throws DBException { 
        super(new PstSalaryLevelDetail(0)); 
        if(!locate(sOid)) 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
        else 
                return; 
    }
    
    public PstSalaryLevelDetail(long lOid) throws DBException { 
		super(new PstSalaryLevelDetail(0)); 
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
        SalaryLevelDetail salaryLevelDetail = fetchExc(ent.getOID()); 
		ent = (Entity)salaryLevelDetail; 
		return salaryLevelDetail.getOID(); 
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
        return new PstSalaryLevelDetail().getClass().getName(); 
    }
    
    public String getTableName() {
          return TBL_PAY_LEVEL_COM;
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((SalaryLevelDetail) ent); 
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((SalaryLevelDetail) ent);
    }
    
    public static SalaryLevelDetail fetchExc(long oid) throws DBException{ 
        try{ 
                SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                PstSalaryLevelDetail pstSalaryLevelDetail = new PstSalaryLevelDetail(oid); 
                salaryLevelDetail.setOID(oid);

                salaryLevelDetail.setLevelCode(pstSalaryLevelDetail.getString(FLD_LEVEL_CODE));
                salaryLevelDetail.setCompCode(pstSalaryLevelDetail.getString(FLD_COMP_CODE));
                salaryLevelDetail.setFormula(pstSalaryLevelDetail.getString(FLD_FORMULA));
                salaryLevelDetail.setSortIdx(pstSalaryLevelDetail.getInt(FLD_SORT_IDX));
                salaryLevelDetail.setPayPeriod(pstSalaryLevelDetail.getInt(FLD_PAY_PERIOD));
                salaryLevelDetail.setTakeHomePay(pstSalaryLevelDetail.getInt(FLD_TAKE_HOME_PAY));
                salaryLevelDetail.setCopyData(pstSalaryLevelDetail.getInt(FLD_COPY_DATA));
                salaryLevelDetail.setComponentId(pstSalaryLevelDetail.getlong(FLD_COMPONENT_ID));
                

                return salaryLevelDetail;
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstSalaryLevelDetail(0),DBException.UNKNOWN); 
        } 
     }
    
    public static long insertExc(SalaryLevelDetail salaryLevelDetail) throws DBException{ 
        try{ 
                PstSalaryLevelDetail pstSalaryLevelDetail = new PstSalaryLevelDetail(0);

                pstSalaryLevelDetail.setString(FLD_LEVEL_CODE, salaryLevelDetail.getLevelCode());
                pstSalaryLevelDetail.setString(FLD_COMP_CODE, salaryLevelDetail.getCompCode());
                pstSalaryLevelDetail.setString(FLD_FORMULA, salaryLevelDetail.getFormula());
                pstSalaryLevelDetail.setInt(FLD_SORT_IDX, salaryLevelDetail.getSortIdx());
                pstSalaryLevelDetail.setInt(FLD_PAY_PERIOD, salaryLevelDetail.getPayPeriod());
                pstSalaryLevelDetail.setInt(FLD_TAKE_HOME_PAY, salaryLevelDetail.getTakeHomePay());
                pstSalaryLevelDetail.setInt(FLD_COPY_DATA, salaryLevelDetail.getCopyData()); 
                pstSalaryLevelDetail.setLong(FLD_COMPONENT_ID, salaryLevelDetail.getComponentId()); 
          
                pstSalaryLevelDetail.insert();
                salaryLevelDetail.setOID(pstSalaryLevelDetail.getlong(FLD_PAY_LEVEL_COM_ID));
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstSalaryLevelDetail(0),DBException.UNKNOWN); 
        }
        return salaryLevelDetail.getOID();
   }
    
   public static long updateExc(SalaryLevelDetail salaryLevelDetail) throws DBException{ 
        try{ 
          if(salaryLevelDetail.getOID() != 0){ 
                PstSalaryLevelDetail pstSalaryLevelDetail = new PstSalaryLevelDetail(salaryLevelDetail.getOID());
                
                pstSalaryLevelDetail.setString(FLD_LEVEL_CODE, salaryLevelDetail.getLevelCode());
                pstSalaryLevelDetail.setString(FLD_COMP_CODE, salaryLevelDetail.getCompCode());
                pstSalaryLevelDetail.setString(FLD_FORMULA, salaryLevelDetail.getFormula());
                pstSalaryLevelDetail.setInt(FLD_SORT_IDX, salaryLevelDetail.getSortIdx());
                pstSalaryLevelDetail.setInt(FLD_PAY_PERIOD, salaryLevelDetail.getPayPeriod());
                pstSalaryLevelDetail.setInt(FLD_TAKE_HOME_PAY, salaryLevelDetail.getTakeHomePay());
                pstSalaryLevelDetail.setInt(FLD_COPY_DATA, salaryLevelDetail.getCopyData()); 
                pstSalaryLevelDetail.setLong(FLD_COMPONENT_ID, salaryLevelDetail.getComponentId());


                pstSalaryLevelDetail.update();
                return salaryLevelDetail.getOID();
           }
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstSalaryLevelDetail(0),DBException.UNKNOWN); 
        }
        return 0;
   }
     
   public static long deleteExc(long oid) throws DBException{ 
        try{ 
                PstSalaryLevelDetail pstSalaryLevelDetail = new PstSalaryLevelDetail(oid);
                pstSalaryLevelDetail.delete();
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstSalaryLevelDetail(0),DBException.UNKNOWN); 
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
                String sql = "SELECT * FROM " + TBL_PAY_LEVEL_COM; 
                if(whereClause != null && whereClause.length() > 0)
                        sql = sql + " WHERE " + whereClause;
                if(order != null && order.length() > 0)
                        sql = sql + " ORDER BY " + order;
                if(limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                else
                        sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                
                System.out.println("sql PstSalaryLevelDetail.list.........."+sql);
                dbrs = DBHandler.execQueryResult(sql);
                //System.out.println("SQL whereClause"+sql);
                
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()) {
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        resultToObject(rs, salaryLevelDetail);
                        lists.add(salaryLevelDetail);
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
   
  
   public static void resultToObject(ResultSet rs, SalaryLevelDetail salaryLevelDetail){
        try{
                salaryLevelDetail.setOID(rs.getLong(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID]));
                salaryLevelDetail.setLevelCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]));
                salaryLevelDetail.setCompCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]));
                salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                salaryLevelDetail.setSortIdx(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_SORT_IDX]));
                salaryLevelDetail.setPayPeriod(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_PERIOD]));
                salaryLevelDetail.setTakeHomePay(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]));
                salaryLevelDetail.setCopyData(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COPY_DATA]));
                salaryLevelDetail.setComponentId(rs.getLong(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]));
                

        }catch(Exception e){ }
   }
   
   public static boolean checkOID(long salaryLevelDetailId){
        DBResultSet dbrs = null;
        boolean result = false;
        try{
                String sql = "SELECT * FROM " + TBL_PAY_LEVEL_COM + " WHERE " + 
                             PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID] + " = '" + salaryLevelDetailId+"'";

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
                String sql = "SELECT COUNT("+ PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID] + ") FROM " + TBL_PAY_LEVEL_COM;
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
   
   public static int getTakeHomePay(String salaryLevel,String compCode){
        DBResultSet dbrs = null;
       // String codeOvt = PstSystemProperty.getValueByName("CODE_OVT");
        try {
                String sql = "SELECT "+ PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " FROM " + TBL_PAY_LEVEL_COM+
                             " WHERE "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+salaryLevel+"'"+
                             " AND "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+"='"+compCode+"'";
                           
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
                           SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail)list.get(ls);
                           if(oid == salaryLevelDetail.getOID())
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
    /* This method used to get the salary components  
     * created by Yunny
     */
   public static Vector listComponent(int limitStart,int recordToGet,String salaryLevel,int compType){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {
                 String sql = "SELECT * FROM " + TBL_PAY_LEVEL_COM+" AS lev"+
                             " INNER JOIN "+PstPayComponent.TBL_PAY_COMPONENT+ " AS comp "+
                             " ON lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]+
                             " = comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]+
                             " WHERE comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+
                             " = "+compType+
                             " AND lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+
                             " = '"+salaryLevel+"'";
                 
                 sql = sql +" ORDER BY lev."+ fieldNames[FLD_SORT_IDX] + " LIMIT " + limitStart + ","+ recordToGet ;
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
               // System.out.println("SQL    "+compType+"-"+sql);
                //System.out.println("masuk:::::::::::: "+rs);
                while(rs.next()) {
                        
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        resultToObject(rs, salaryLevelDetail);
                        //update by satrya 2014-04-02
                        salaryLevelDetail.setCompName(rs.getString("comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME])); 
                        lists.add(salaryLevelDetail);
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
    * Description list without limit
    * Date : 2015-03-17
    * Author : Hendra Putu
    */
    public static Vector listComponentNoLimit(String salaryLevel, int compType) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_LEVEL_COM + " AS lev"
                    + " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS comp "
                    + " ON lev." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]
                    + " = comp." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]
                    + " WHERE comp." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]
                    + " = " + compType
                    + " AND lev." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]
                    + " = '" + salaryLevel + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
   
                SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                resultToObject(rs, salaryLevelDetail);
                //update by satrya 2014-04-02
                salaryLevelDetail.setCompName(rs.getString("comp." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                lists.add(salaryLevelDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

   
    /* This method used to get the salary components  
     * created by Yunny
     */
   public static Vector listComponent(int limitStart,int recordToGet,String salaryLevel,String compCode){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {
                 String sql = "SELECT * FROM " + TBL_PAY_LEVEL_COM+" AS lev"+
                             " INNER JOIN "+PstPayComponent.TBL_PAY_COMPONENT+ " AS comp "+
                             " ON lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]+
                             " = comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]+
                             " WHERE comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                             " = \""+compCode+"\" "+
                             " AND lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+
                             " = '"+salaryLevel+"'";
                 
                 sql = sql +" ORDER BY lev."+ fieldNames[FLD_SORT_IDX] + " LIMIT " + limitStart + ","+ recordToGet ;
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
               // System.out.println("SQL    "+compType+"-"+sql);
                //System.out.println("masuk:::::::::::: "+rs);
                while(rs.next()) {
                        
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        resultToObject(rs, salaryLevelDetail);
                        lists.add(salaryLevelDetail);
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
   
   
public static Vector listComponent(int limitStart,int recordToGet,String salaryLevel,int compType, String addWhere ){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {
                 String sql = "SELECT * FROM " + TBL_PAY_LEVEL_COM+" AS lev"+
                             " INNER JOIN "+PstPayComponent.TBL_PAY_COMPONENT+ " AS comp "+
                             " ON lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]+
                             " = comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]+
                             " WHERE comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+
                             " = "+compType+
                             " AND lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+
                             " = '"+salaryLevel+"'" + addWhere;
                 
                 sql = sql +" ORDER BY lev."+ fieldNames[FLD_SORT_IDX] + " LIMIT " + limitStart + ","+ recordToGet ;
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
               // System.out.println("SQL    "+compType+"-"+sql);
                //System.out.println("masuk:::::::::::: "+rs);
                while(rs.next()) {
                        
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        resultToObject(rs, salaryLevelDetail);
                        lists.add(salaryLevelDetail);
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
   
   
    public static Vector listComponentVer1(int limitStart, int recordToGet, String salaryLevel, int compType, String addWhere, long empId) {
        Employee emp = new Employee();
        if (empId != 0){
            try {
                emp = PstEmployee.fetchExc(empId);
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_LEVEL_COM + " AS lev"
                    + " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS comp "
                    + " ON lev." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]
                    + " = comp." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]
                    + " WHERE comp." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]
                    + " = " + compType
                    + " AND lev." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]
                    + " = '" + salaryLevel + "'" + addWhere;

            sql = sql + " ORDER BY lev." + fieldNames[FLD_SORT_IDX] + " LIMIT " + limitStart + "," + recordToGet;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            /*
resign = 1
pensiun_program = 1
JHT = 0
PENSION = 0

resign = 1
pensiun_program = 0
JHT = 0
PENSION = 0

resign = 0
pensiun_program = 1
JHT = 1
PENSION = 1

resign = 0
pensiun_program = 0
JHT = 1
PENSION = 0 
            */
            while (rs.next()) {
                String compName = rs.getString("COMP_NAME");
                SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                salaryLevelDetail.setOID(rs.getLong(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID]));
                salaryLevelDetail.setLevelCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]));
                salaryLevelDetail.setCompCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]));
                salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                salaryLevelDetail.setSortIdx(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_SORT_IDX]));
                salaryLevelDetail.setPayPeriod(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_PERIOD]));
                salaryLevelDetail.setTakeHomePay(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]));
                salaryLevelDetail.setCopyData(rs.getInt(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COPY_DATA]));
                salaryLevelDetail.setComponentId(rs.getLong(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]));
                //resultToObject(rs, salaryLevelDetail);
                if (emp.getResigned() == 1){
                    /* JHT = 0 | PENSION = 0 */

                    if (compName.equals("ASTEK")){
                    salaryLevelDetail.setFormula("=0");
                    }
                    if (compName.equals("PENSION")){
                        salaryLevelDetail.setFormula("=0");
                    }

                    String subStrCompName = compName.substring(0, 3);
                    if (subStrCompName.equals("JHT")){
                        salaryLevelDetail.setFormula("=0");
                    }
                    
                } else {
                    if (emp.getStatusPensiunProgram() == 1){
                        /* JHT = 1 | PENSION = 1 */
                        if (compName.equals("ASTEK")){
                            salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        }
                        if (compName.equals("PENSION")){
                            salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        }

                        String subStrCompName = compName.substring(0, 3);
                        if (subStrCompName.equals("JHT")){
                            salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        }
                    } else {
                        /* JHT = 1 | PENSION = 0 */
                        if (compName.equals("ASTEK")){
                            salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        }
                        if (compName.equals("PENSION")){
                            salaryLevelDetail.setFormula("=0");
                        }

                        String subStrCompName = compName.substring(0, 3);
                        if (subStrCompName.equals("JHT")){
                            salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        }
                    }
                }

                lists.add(salaryLevelDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
   /* This method used to get count of list component
    *   Created By Yunny
    **/
   public static int getCountListComponent(String salaryLevel,int compType){
        DBResultSet dbrs = null;
        try {
                
                String sql = "SELECT COUNT("+ PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_LEVEL_COM_ID] + ") FROM " + TBL_PAY_LEVEL_COM+" AS lev"+
                             " INNER JOIN "+PstPayComponent.TBL_PAY_COMPONENT+ " AS comp "+
                             " ON lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMPONENT_ID]+
                             " = comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]+
                             " WHERE comp."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+
                             " = "+compType+
                              " AND lev."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+
                             " = '"+salaryLevel+"'"; 
                         
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
   
   
   /* This method used to get formula
    *   Created By Yunny
    **/
   public static String getFormula(String comp_code){
        DBResultSet dbrs = null;
        try {
                
                String sql = "SELECT "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+ " FROM "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+
                             " WHERE "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+ "='"+comp_code+"'"; 
                
                System.out.println("sql formula........"+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                String count = "";
                while(rs.next()) { count = rs.getString(1); }

                rs.close();
                return count;
        }catch(Exception e) {
                return "";
        }finally {
                DBResultSet.close(dbrs);
        }
   }
   
   /* This method used to get the salary components  
     * created by Yunny
     */
   public static Vector getIdByHomePay(String salaryLevel,int compType){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {
                  String sql = "SELECT PAY_LEVEL_COMP_ID FROM " + TBL_PAY_LEVEL_COM + " WHERE " + 
                                 PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " = '" + PstSalaryLevelDetail.YES_TAKE + "'";
           dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                System.out.println("SQL -------"+sql);
                //System.out.println("masuk:::::::::::: "+rs);
                while(rs.next()) {
                        
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        resultToObject(rs, salaryLevelDetail);
                        lists.add(salaryLevelDetail);
                }
                rs.close();
                return lists;

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
                return new Vector();
   }/**
    * Create by satrya 20130207
    * Keterangan: fungsinya untuk di export excel tanpa ada parameter showPayslip sehingga menampilkan semu komponen
    * @param takeStatus
    * @param salaryLevel
    * @param typeComp
    * @param paySlipId
    * @param keyPeriod
    * @param includeZero
    * @param payslipGroupId
    * @return 
    */
   public static Vector listPaySlip(int takeStatus,String salaryLevel,int typeComp,long paySlipId, int keyPeriod, boolean includeZero, long payslipGroupId){
      Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {     
                  
                  String whereClause = "";
                  String sql = "SELECT PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]+","+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+","+
                               ///update by satrya 2013-02-06
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+
                               " FROM "+PstPayComponent.TBL_PAY_COMPONENT+" AS PAY"+
                               " LEFT JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+" AS LEV ON "+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+
                               " AND  LEV." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " =" +takeStatus+ 
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+salaryLevel+"'"+
                               " AND LEV."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAY_PERIOD]+"="+keyPeriod + 
                               ( includeZero ? " LEFT " :" INNER " ) + " JOIN "+PstPaySlipComp.TBL_PAY_SLIP_COMP+" AS COMP"+ 
                               " ON PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+
                               " AND COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId +
                               " WHERE " ;
                              //update by satrya 2013-01-24
                              if(payslipGroupId!=0){
                                  sql+= " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]+"="+payslipGroupId + " AND ";
                              }
                               sql += " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+typeComp + " AND ";
                               sql += " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS]+" > "+ PstPayComponent.NO_SHOW_PAYSLIP ;
                {
                    whereClause = whereClause + ( includeZero ? "(1=1)" : " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+" > 0" ) +" AND ";
                }
                 if(whereClause != null && whereClause.length()>0){
                    whereClause = " AND ("+ whereClause.substring(0,whereClause.length()-4)+")";
                    sql = sql + whereClause;
                 
                 }  
                sql = sql + " ORDER BY PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX];
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
               
                //System.out.println("listPaySlip:::::::::::: "+sql);
                while(rs.next()) {
                        Vector vect = new Vector(1,1);
                         
                        PayComponent payComponent = new PayComponent();
                        payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                        payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                        payComponent.setShowpayslip(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]));
                        vect.add(payComponent);
                        
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        salaryLevelDetail.setLevelCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]));
                        vect.add(salaryLevelDetail);
                        
                        PaySlipComp paySlipComp = new PaySlipComp();
                        paySlipComp.setOID(rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]));
                        paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                        
                        vect.add(paySlipComp);
                        
                        lists.add(vect);
                           
                           
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
    /* This method used to get the salary components 
     * created by Yunny
     */
   public static Vector listPaySlip(int takeStatus,String salaryLevel,int typeComp,long paySlipId,int showPayslip, int keyPeriod, boolean includeZero, long payslipGroupId){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {     
                  
                  String whereClause = "";
                  String sql = "SELECT PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]+","+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+","+
                               ///update by satrya 2013-02-06
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+
                               " FROM "+PstPayComponent.TBL_PAY_COMPONENT+" AS PAY"+
                               " LEFT JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+" AS LEV ON "+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+
                               " AND  LEV." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " =" +takeStatus+ 
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+salaryLevel+"'"+
                               " AND LEV."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAY_PERIOD]+"="+keyPeriod + 
                               ( includeZero ? " LEFT " :" INNER " ) + " JOIN "+PstPaySlipComp.TBL_PAY_SLIP_COMP+" AS COMP"+ 
                               " ON PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+
                               " AND COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId +
                               " WHERE " ;
                              //update by satrya 2013-01-24
                              if(payslipGroupId!=0){
                                  sql+= " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]+"="+payslipGroupId + " AND ";
                              }
                               sql += " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+typeComp;
                               sql += " AND PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]+"="+showPayslip;
                /*          
                // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4"))){
                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                }
                else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5"))){
                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                }
                else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6"))){
                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                }
                else */
                {
                    whereClause = whereClause + ( includeZero ? "(1=1)" : " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+" > 0" ) +" AND ";
                }
                 if(whereClause != null && whereClause.length()>0){
                    whereClause = " AND ("+ whereClause.substring(0,whereClause.length()-4)+")";
                    sql = sql + whereClause;
                 
                 }  
                sql = sql + " ORDER BY PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX];
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
               
                //System.out.println("listPaySlip:::::::::::: "+sql);
                while(rs.next()) {
                        Vector vect = new Vector(1,1);
                         
                        PayComponent payComponent = new PayComponent();
                        payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                        payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                        payComponent.setShowpayslip(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]));
                        vect.add(payComponent);
                        
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        salaryLevelDetail.setLevelCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]));
                        vect.add(salaryLevelDetail);
                        
                        PaySlipComp paySlipComp = new PaySlipComp();
                        paySlipComp.setOID(rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]));
                        paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                        
                        vect.add(paySlipComp);
                        
                        lists.add(vect);
                           
                           
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
   
   public static Vector listPaySlipNew(int takeStatus,String salaryLevel,int typeComp,long paySlipId, int keyPeriod, boolean includeZero, long payslipGroupId){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {     
                  
                  String whereClause = "";
                  String sql = "SELECT PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]+","+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+","+
                               ///update by satrya 2013-02-06
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]+","+
                               // update by Hendra Putu | 2015-02-24 | Description: FLD_USED_IN_FORML
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_USED_IN_FORML]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+
                               " FROM "+PstPayComponent.TBL_PAY_COMPONENT+" AS PAY"+
                               " LEFT JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+" AS LEV ON "+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+
                               " AND  LEV." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " =" +takeStatus+ 
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+salaryLevel+"'"+
                               " AND LEV."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAY_PERIOD]+"="+keyPeriod + 
                               ( includeZero ? " LEFT " :" INNER " ) + " JOIN "+PstPaySlipComp.TBL_PAY_SLIP_COMP+" AS COMP"+ 
                               " ON PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+
                               " AND COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId +
                               " WHERE " ;
                              //update by satrya 2013-01-24
                              if(payslipGroupId!=0){
                                  sql+= " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]+"="+payslipGroupId + " AND ";
                              }
                               sql += " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+typeComp;
                               //sql += " AND PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]+"="+showPayslip;
                               //sql += " OR PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]+"="+showPayslip1;
                /*          
                // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4"))){
                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                }
                else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5"))){
                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                }
                else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6"))){
                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                }
                else */
                {
                    whereClause = whereClause + ( includeZero ? "(1=1)" : " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+" > 0" ) +" AND ";
                }
                 if(whereClause != null && whereClause.length()>0){
                    whereClause = " AND ("+ whereClause.substring(0,whereClause.length()-4)+")";
                    sql = sql + whereClause;
                 
                 }  
                sql = sql + " ORDER BY PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX];
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
               
                //System.out.println("listPaySlip:::::::::::: "+sql);
                while(rs.next()) {
                        Vector vect = new Vector(1,1);
                        if (rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]) != PstPayComponent.YES_NOT_EQUALS_0){
                            if (rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]) != PstPayComponent.NO_SHOW_PAYSLIP){
                                PayComponent payComponent = new PayComponent();
                                payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                                payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                                payComponent.setUsedInForml(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_USED_IN_FORML]));
                                payComponent.setShowpayslip(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]));
                                vect.add(payComponent);

                                SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                                salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                                salaryLevelDetail.setLevelCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]));
                                vect.add(salaryLevelDetail);

                                PaySlipComp paySlipComp = new PaySlipComp();
                                paySlipComp.setOID(rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]));
                                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));

                                vect.add(paySlipComp);
                                lists.add(vect);
                            }
                        } else {
                            if((rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]) > 0)||(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]) < 0)){
                                PayComponent payComponent = new PayComponent();
                                payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                                payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                                payComponent.setUsedInForml(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_USED_IN_FORML]));
                                payComponent.setShowpayslip(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]));
                                vect.add(payComponent);

                                SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                                salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                                salaryLevelDetail.setLevelCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]));
                                vect.add(salaryLevelDetail);

                                PaySlipComp paySlipComp = new PaySlipComp();
                                paySlipComp.setOID(rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]));
                                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));

                                vect.add(paySlipComp);
                                lists.add(vect);
                            }
                        }
                        //lists.add(vect);
                           
                           
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
   
   /* This method used to get the salary components 
     * created by Yunny
     */
   public static Vector listPaySlipGlobal(int takeStatus,String salaryLevel,long paySlipId, int keyPeriod){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {
                  String whereClause = "";
                  String sql = "SELECT PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]+","+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+","+
                               " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+","+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+","+
                               " COMP. "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+
                               " FROM "+PstPayComponent.TBL_PAY_COMPONENT+" AS PAY"+
                               " LEFT JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+" AS LEV ON "+                           
                               " PAY. "+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+
                               " AND LEV." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " =" +takeStatus+ 
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+salaryLevel+"'" +
                               " AND LEV."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAY_PERIOD]+"="+keyPeriod +
                               " INNER JOIN "+PstPaySlipComp.TBL_PAY_SLIP_COMP+" AS COMP"+
                               " ON PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+
                               " WHERE "+
                               "  COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId
                               ;
                               //" AND COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+" > 0";
                  
                               /*
                                // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                                if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4"))){
                                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                                }
                                else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5"))){
                                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                                }
                                else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6"))){
                                    whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                                " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                                " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                                " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                                }
                                 else */
                                 {
                                    whereClause = whereClause + " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+" > 0 AND ";
                                }

             if(whereClause != null && whereClause.length()>0){
                whereClause = " AND ("+ whereClause.substring(0,whereClause.length()-4)+")";
                sql = sql + whereClause;

             }                 

                  
           sql = sql + " ORDER BY PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+
                       ", PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX];
               // System.out.println("listPaySlipGeneral:::::::::::: "+sql);
           dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
               
                while(rs.next()) {
                        Vector vect = new Vector(1,1);
                         
                        PayComponent payComponent = new PayComponent();
                        payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                        payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                        payComponent.setCompType(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]));
                        vect.add(payComponent);
                        
                        SalaryLevelDetail salaryLevelDetail = new SalaryLevelDetail();
                        salaryLevelDetail.setFormula(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]));
                        salaryLevelDetail.setLevelCode(rs.getString(PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]));
                        vect.add(salaryLevelDetail);
                        
                        PaySlipComp paySlipComp = new PaySlipComp();
                        paySlipComp.setOID(rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]));
                        paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                        
                        vect.add(paySlipComp);
                        
                        lists.add(vect);
                           
                           
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
   
   public static int getSumBenefit(int takeStatus,String salaryLevel,int typeComp,long paySlipId, int keyPeriod,long payslipGroupId){
       DBResultSet dbrs = null;
        try {
               String whereClause = "";
               String sql = "SELECT SUM(COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+")"+
                               " FROM "+PstPayComponent.TBL_PAY_COMPONENT+" AS PAY"+
                               " LEFT JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+" AS LEV ON "+
                               " PAY. "+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+
                               " INNER JOIN "+PstPaySlipComp.TBL_PAY_SLIP_COMP+" AS COMP"+
                               " ON PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+
                               " WHERE LEV." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " =" +takeStatus+ 
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+salaryLevel+"'"+
                               " AND PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+typeComp+
                               " AND COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_PERIOD]+"="+keyPeriod;
                //update by satrya 2013-01-25      
               if(payslipGroupId!=0){
                                  sql+= " AND PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]+"="+payslipGroupId;
              }
                 // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                   /* if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4"))){
                        whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                    " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                    }
                    else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5"))){
                        whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                    " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                    }
                    else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6"))){
                        whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                    " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                    }*/
               
                 
                     if(whereClause != null && whereClause.length()>0){
                        whereClause = " AND ("+ whereClause.substring(0,whereClause.length()-4)+")";
                        sql = sql + whereClause;
                 
                     }  
              // System.out.println("sql sum benefit -------"+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                int sumBenefit= 0;
             
                while(rs.next()) { 
                    sumBenefit = rs.getInt(1); 
                   //System.out.println("sumBenefit..........."+sumBenefit);
                }

                rs.close();
                return sumBenefit;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs);
        }
   }
   
   public static double getSumBenefitDoub(int takeStatus,String salaryLevel,int typeComp,long paySlipId, int keyPeriod,long payslipGroupId){
       DBResultSet dbrs = null;
        try {
               String whereClause = "";
               String sql = "SELECT SUM(COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+")"+
                               " FROM "+PstPayComponent.TBL_PAY_COMPONENT+" AS PAY"+
                               " INNER JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+" AS LEV ON "+
                               " PAY. "+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " LEV. "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+
                               " INNER JOIN "+PstPaySlipComp.TBL_PAY_SLIP_COMP+" AS COMP"+
                               " ON PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"="+
                               " COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+
                               " WHERE LEV." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY] + " =" +takeStatus+ 
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+salaryLevel+"'"+
                               " AND PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+typeComp+
                               " AND COMP."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
                               " AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_PAY_PERIOD]+"="+keyPeriod;
                //update by satrya 2013-01-25      
               if(payslipGroupId!=0){
                                  sql+= " AND PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]+"="+payslipGroupId;
              }
                 // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                   /* if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4"))){
                        whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                    " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                    }
                    else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5"))){
                        whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                    " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                    }
                    else if(salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6"))){
                        whereClause = whereClause + " PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE1").trim()+"'"+ 
                                    " OR  PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
                                    " = '"+PstSystemProperty.getValueByName("COMP_CODE2").trim()+ "' AND ";
                    }*/
               
                 
                     if(whereClause != null && whereClause.length()>0){
                        whereClause = " AND ("+ whereClause.substring(0,whereClause.length()-4)+")";
                        sql = sql + whereClause;
                 
                     }  
                //System.out.println("sql PstSalaryLevelDetail.getSumBenefitDoub -------"+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                double sumBenefit= 0;
             
                while(rs.next()) { 
                    sumBenefit = rs.getDouble(1); 
                   //System.out.println("sumBenefitDouble..........."+sumBenefit);
                }

                rs.close();
                return sumBenefit;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs);
        }
   }
   
   public static double getPrevPeriodValue(long periodId, long employee_id, String compCode){
       double compVal = 0.0;
       
       
       
       return compVal;        
   }
   
   public static int deleteByLevelCode(String levelCode) // update by Kartika 23 Nov 2012
            throws DBException {
       DBResultSet dbrs = null;
       try {
            String sql = "DELETE FROM " + TBL_PAY_LEVEL_COM + " WHERE "+ fieldNames[FLD_LEVEL_CODE]+"="+levelCode;                         
            int status = DBHandler.execUpdate(sql);
            return status;            
       }catch(Exception e) {
            System.out.println(e);            
        }
        finally{
            DBResultSet.close(dbrs);
        }
        
        return -1;
    }

   public static int deleteByWhere(String where) // add by Kartika 23 Nov 2012
            throws DBException {
       DBResultSet dbrs = null;
       if(where==null || where.length()<3){
           return -1;
       }
       try {
            String sql = "DELETE FROM " + TBL_PAY_LEVEL_COM + " WHERE "+where;                         
            int status = DBHandler.execUpdate(sql);
            return status;            
       }catch(Exception e) {
            System.out.println(e);            
        }
        finally{
            DBResultSet.close(dbrs);
        }
        
        return -1;
    }

   
}
