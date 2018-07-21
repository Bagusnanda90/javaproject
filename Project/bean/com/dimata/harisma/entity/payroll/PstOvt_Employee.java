/*
 * PstOvt_Employee.java
 *
 * Created on April 6, 2007, 1:13 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */

import java.util.Date;
import java.util.Vector;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.ResultSet;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.system.entity.system.*;
import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author  emerliana
 */

public class PstOvt_Employee extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
        public static final  String TBL_OVT_EMPLOYEE = "hr_overtime_detail";//"pay_ovt_employee";//"PAY_OVT_EMPLOYEE";
     
        public static final  int FLD_OVT_EMPLY_ID = 0;
	public static final  int FLD_PERIOD_ID = 1;
	public static final  int FLD_EMPLOYEE_NUM = 2;
	public static final  int FLD_WORK_DATE = 3;
	public static final  int FLD_STD_WORK_SCHDL = 4;
	public static final  int FLD_OVT_START = 5;
        public static final  int FLD_OVT_END = 6;
        public static final  int FLD_OVT_DURATION  = 7;
        public static final  int FLD_OVT_DOC_NR = 8;
        public static final  int FLD_STATUS = 9;
        public static final  int FLD_PAY_SLIP_ID = 10;
        
        public static final  int FLD_OVT_CODE = 11;
        public static final  int FLD_TOT_IDX = 12;
        /* public static final  String[] fieldNames = {
		"OVT_EMPLY_ID",
		"PERIOD_ID",
		"EMPLOYEE_NUM",
		"WORK_DATE",
		"STD_WORK_SCHDL",
		"OVT_START",
                "OVT_END",
                "OVT_DURATION",
                "OVT_DOC_NR",
                "STATUS",
                "PAY_SLIP_ID",
                
                "OVT_CODE",
                "TOT_IDX"
		
	 }; */
        
public static final  String[] fieldNames = {
		"OVERTIME_DETAIL_ID",
		"PERIOD_ID",
		"EMPLOYEE_NR_OVTM",
		"DATE_FROM",
		"STD_WORK_SCHDL",
		"REAL_DATE_FROM",
                "REAL_DATE_TO",
                "OVT_DURATION",
                "OVT_DOC_NR",
                "STATUS",
                "PAY_SLIP_ID",               
                "OVT_CODE",
                "TOT_IDX"		
	 };        

         public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
                TYPE_STRING,
                TYPE_DATE,
                TYPE_STRING,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_FLOAT,
                TYPE_STRING,
                TYPE_INT,
                TYPE_LONG, 
                
                TYPE_STRING,
                TYPE_FLOAT
         
	 };
       
        
        public static final int DRAFT = 1;
        public static final int APPROVE = 2;
        public static final int POSTED = 3;

        public static final String[]digit = {
            "","New/Draft","Approve","Posted"
        };
        
        public static final int DAILY = 0;
        public static final int MONTLY = 1;
       

        public static final String[]periodImport = {
            "Daily","Monthly"
        };
        
        // salary overtime
        public static double OV_SALARY = 0;
         
         
    /** Creates a new instance of PstOvt_Employee */
    public PstOvt_Employee() {
    }
    
    public PstOvt_Employee(int i) throws DBException {
        super(new PstOvt_Employee());
    }
    
     public PstOvt_Employee(String sOid) throws DBException {
        super(new PstOvt_Employee(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
      public PstOvt_Employee(long lOid) throws DBException {
        super(new PstOvt_Employee(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
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
        return new PstOvt_Employee().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_OVT_EMPLOYEE;
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((Ovt_Employee) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Ovt_Employee) ent);
    }
    
     public long fetchExc(Entity ent) throws Exception {
        Ovt_Employee ovt_Employee = fetchExc(ent.getOID());
        ent = (Entity) ovt_Employee;
        return ovt_Employee.getOID();
    }
     
     
      public long deleteExc(Entity ent) throws Exception {
         if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
      
      
     public static Ovt_Employee fetchExc(long oid) throws DBException {
        try {
            Ovt_Employee ovt_Employee = new Ovt_Employee();
            PstOvt_Employee pstOvt_Employee = new PstOvt_Employee(oid);
            ovt_Employee.setOID(oid);
            
            ovt_Employee.setDuration(pstOvt_Employee.getdouble(FLD_OVT_DURATION));
            ovt_Employee.setEmployee_num(pstOvt_Employee.getString(FLD_EMPLOYEE_NUM));
            ovt_Employee.setOvt_End(pstOvt_Employee.getDate(FLD_OVT_END));
            ovt_Employee.setOvt_Start(pstOvt_Employee.getDate(FLD_OVT_START));
            ovt_Employee.setOvt_doc_nr(pstOvt_Employee.getString(FLD_OVT_DOC_NR));
            ovt_Employee.setPay_slip_id(pstOvt_Employee.getlong(FLD_PAY_SLIP_ID));
            ovt_Employee.setPeriodId(pstOvt_Employee.getlong(FLD_PERIOD_ID));
            ovt_Employee.setStatus(pstOvt_Employee.getInt(FLD_STATUS));
            ovt_Employee.setWorkDate(pstOvt_Employee.getDate(FLD_WORK_DATE));
            ovt_Employee.setWork_schedule(pstOvt_Employee.getString(FLD_STD_WORK_SCHDL));
            
            ovt_Employee.setOvt_code(pstOvt_Employee.getString(FLD_OVT_CODE));
            ovt_Employee.setTot_Idx(pstOvt_Employee.getdouble(FLD_TOT_IDX));
                
            return ovt_Employee;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvt_Employee(0), DBException.UNKNOWN);
        }
    }
     
      public static long insertExc(Ovt_Employee ovt_Employee) throws DBException{ 
		try{ 
			PstOvt_Employee pstOvt_Employee = new PstOvt_Employee(0);

			pstOvt_Employee.setDouble(FLD_OVT_DURATION, ovt_Employee.getDuration());
                        pstOvt_Employee.setString(FLD_EMPLOYEE_NUM, ovt_Employee.getEmployee_num());
                        pstOvt_Employee.setDate(FLD_OVT_END, ovt_Employee.getOvt_End());
                        pstOvt_Employee.setDate(FLD_OVT_START, ovt_Employee.getOvt_Start());
                        pstOvt_Employee.setString(FLD_OVT_DOC_NR, ovt_Employee.getOvt_doc_nr());
                        pstOvt_Employee.setLong(FLD_PAY_SLIP_ID, ovt_Employee.getPay_slip_id());
                        pstOvt_Employee.setLong(FLD_PERIOD_ID, ovt_Employee.getPeriodId());
                        pstOvt_Employee.setInt(FLD_STATUS, ovt_Employee.getStatus());
                        pstOvt_Employee.setDate(FLD_WORK_DATE, ovt_Employee.getWorkDate());
                        pstOvt_Employee.setString(FLD_STD_WORK_SCHDL, ovt_Employee.getWork_schedule());
                        
                        pstOvt_Employee.setString(FLD_OVT_CODE, ovt_Employee.getOvt_code());
                        pstOvt_Employee.setDouble(FLD_TOT_IDX, ovt_Employee.getTot_Idx());
                        
                pstOvt_Employee.insert();
			ovt_Employee.setOID(pstOvt_Employee.getlong(FLD_OVT_EMPLY_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Employee(0),DBException.UNKNOWN); 
		}
		return ovt_Employee.getOID();
	}
      
      
      public static long updateExc(Ovt_Employee ovt_Employee) throws DBException{ 
		try{ 
		  if(ovt_Employee.getOID() != 0){ 
                        PstOvt_Employee pstOvt_Employee = new PstOvt_Employee(ovt_Employee.getOID());
                        
                        pstOvt_Employee.setDouble(FLD_OVT_DURATION, ovt_Employee.getDuration());
                        pstOvt_Employee.setString(FLD_EMPLOYEE_NUM, ovt_Employee.getEmployee_num());
                        pstOvt_Employee.setDate(FLD_OVT_END, ovt_Employee.getOvt_End());
                        pstOvt_Employee.setDate(FLD_OVT_START, ovt_Employee.getOvt_Start());
                        pstOvt_Employee.setString(FLD_OVT_DOC_NR, ovt_Employee.getOvt_doc_nr());
                        pstOvt_Employee.setLong(FLD_PAY_SLIP_ID, ovt_Employee.getPay_slip_id());
                        pstOvt_Employee.setLong(FLD_PERIOD_ID, ovt_Employee.getPeriodId());
                        pstOvt_Employee.setInt(FLD_STATUS, ovt_Employee.getStatus());
                        pstOvt_Employee.setDate(FLD_WORK_DATE, ovt_Employee.getWorkDate());
                        pstOvt_Employee.setString(FLD_STD_WORK_SCHDL, ovt_Employee.getWork_schedule());
                        pstOvt_Employee.setString(FLD_OVT_CODE, ovt_Employee.getOvt_code());
                        pstOvt_Employee.setDouble(FLD_TOT_IDX, ovt_Employee.getTot_Idx());
                        
                pstOvt_Employee.update();
				return ovt_Employee.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Employee(0),DBException.UNKNOWN); 
		}
		return 0;
	}
      
        public static long deleteExc(long oid) throws DBException{ 
        try{ 
                PstOvt_Employee pstOvt_Employee = new PstOvt_Employee(oid);
                pstOvt_Employee.delete();
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstOvt_Employee(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_OVT_EMPLOYEE; 
                        
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                        
                        System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				Ovt_Employee ovt_Employee = new Ovt_Employee();
				resultToObject(rs, ovt_Employee);
				lists.add(ovt_Employee);
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
     
     
      public static void resultToObject(ResultSet rs, Ovt_Employee ovt_Employee){
		try{
			ovt_Employee.setOID(rs.getLong(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_EMPLY_ID]));
                        ovt_Employee.setDuration(rs.getDouble(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_DURATION]));
                        ovt_Employee.setEmployee_num(rs.getString(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]));
                        
                        Date tm_end = DBHandler.convertDate(rs.getDate(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_END]),rs.getTime(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_END]));
                        ovt_Employee.setOvt_End(tm_end);
                        
                        //ovt_Employee.setOvt_End(rs.getDate(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_END]));
                        
                        Date tm_start = DBHandler.convertDate(rs.getDate(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_START]),rs.getTime(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_START]));
                        ovt_Employee.setOvt_Start(tm_start);
                        
                       // ovt_Employee.setOvt_Start(rs.getDate(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_START]));
                        ovt_Employee.setOvt_doc_nr(rs.getString(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_DOC_NR]));
                        ovt_Employee.setPay_slip_id(rs.getLong(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PAY_SLIP_ID]));
                        ovt_Employee.setPeriodId(rs.getLong(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID]));
                        ovt_Employee.setStatus(rs.getInt(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]));
                        ovt_Employee.setWorkDate(rs.getDate(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_WORK_DATE]));
                        ovt_Employee.setWork_schedule(rs.getString(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STD_WORK_SCHDL]));
                        
                        ovt_Employee.setOvt_code(rs.getString(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_CODE]));
                        ovt_Employee.setTot_Idx(rs.getDouble(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_TOT_IDX]));
                        
		}catch(Exception e){ }
	}
        
      public static boolean checkOID(long ovtEmployeeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_OVT_EMPLOYEE  + " WHERE " + 
						PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_EMPLY_ID] + " = '" +ovtEmployeeId+"'";

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
			String sql = "SELECT COUNT("+ PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_EMPLY_ID] + ") FROM " + TBL_OVT_EMPLOYEE;
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
			  	  Ovt_Employee ovt_Employee = (Ovt_Employee)list.get(ls);
				   if(oid == ovt_Employee.getOID())
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
   

public static Vector listSchedule(int j, long employee_id, long period_id)
{
       DBResultSet dbrs = null;
       Vector result = new Vector(1,1);
        try
       {    
           String sql = "SELECT SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                        " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];
           
           if(j==1)
           {
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1];
           }else if(j==2){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2];
           }else if(j==3){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3];
           }else if(j==4){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4];
           }else if(j==5){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5];
           }else if(j==6){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6];
           }else if(j==7){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7];
           }else if(j==8){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8];
           }else if(j==9){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9];
           }else if(j==10){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10];
           }else if(j==11){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11];
           }else if(j==12){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12];
           }else if(j==13){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13];
           }else if(j==14){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14];
           }else if(j==15){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15];
           }else if(j==16){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16];
           }else if(j==17){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17];
           }else if(j==18){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18];
           }else if(j==19){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19];
           }else if(j==20){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20];
           }else if(j==21){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21];
           }else if(j==22){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22];
           }else if(j==23){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23];
           }else if(j==24){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24];
           }else if(j==25){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25];
           }else if(j==26){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26];
           }else if(j==27){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27];
           }else if(j==28){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28];
           }else if(j==29){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29];
           }else if(j==30){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30];
           }else if(j==31){
               sql = sql +", SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]+
                    " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" SYM "+
                    " ON SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" = SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31];
           }
           /*/*select sch.D1, sch.employee_id, sym.symbol 
            from hr_emp_schedule sch 
            inner join hr_employee emp on sch.employee_id = emp.employee_id
            inner join hr_schedule_symbol sym on sym.schedule_id = sch.D1
            where sch.employee_id = 28 and sch.period_id = 504404332120242020
            *
            */
           
           sql = sql + " WHERE SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = "+employee_id+
                        " AND SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+" = "+period_id;
           
          //System.out.println("sqlnya::::::::::::::::::::::::::::;;"+sql);
          dbrs = DBHandler.execQueryResult(sql); 
          ResultSet rs = dbrs.getResultSet();
          
          while(rs.next())
          {
              Vector temp = new Vector();
              EmpSchedule objEmpSchedule = new EmpSchedule();
              //rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME])
              objEmpSchedule.setEmployeeId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
              temp.add(objEmpSchedule);
              
              ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
              scheduleSymbol.setSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
              temp.add(scheduleSymbol);

              result.add(temp);
          }
        }
       catch(Exception ex)
       {
            System.out.println("err Eselon List :"+ex.toString());
       }
        finally
        {
            DBResultSet.close(dbrs);
        }
        return result;

}

public static void deleteOvtEmployee(String empNum, String duration, long periodeOid){
    double durationDbl =0;
    if(duration.length() > 0){
        durationDbl = Double.parseDouble(duration);
    }else{
        durationDbl = 0;
    }
    
    try {
        String sql = "delete FROM " + TBL_OVT_EMPLOYEE+
            " where "+fieldNames[FLD_EMPLOYEE_NUM]+"='"+empNum+"' and "+
            fieldNames[FLD_OVT_DURATION]+"="+durationDbl+" and "+            
            fieldNames[FLD_PERIOD_ID]+"="+periodeOid;
            System.out.println("sql delete..............."+sql);
            DBHandler.execUpdate(sql);
    }catch(Exception e) {
            System.out.println(e);
    }
}

public static double hitTotDuration(Vector vListIndex, double duration){
    double jumlah = 0.0;
    
    if(vListIndex!=null && vListIndex.size()>0)
    {
        String durationStr = String.valueOf(duration);
        double durationDbl = Double.parseDouble(durationStr);
        double total_idx = 0.0;
        double tot_Idx = 0.0;

        String durationStrHourX = durationStr.substring(0,durationStr.indexOf("."));
        String durationStrMinX = "";
        String index_pay = "";
        durationStrMinX = durationStr.substring(durationStr.indexOf("."),durationStr.length());

        double iDurationMinDbl = Double.parseDouble(String.valueOf(0)+durationStrMinX);
        String striDurationMin = String.valueOf(iDurationMinDbl);
        double iDurationDbl = iDurationMinDbl * 100;
        String iDurationDblStrX = String.valueOf(iDurationDbl);
        String iDurationDblStr = iDurationDblStrX.substring(0,iDurationDblStrX.indexOf("."));

        long iDurationMin = Long.parseLong(iDurationDblStr);
        long iDurationHour = Long.parseLong(durationStrHourX);
        for(int k=0;k<vListIndex.size();k++)
        {
            Ovt_Idx over_Idx = (Ovt_Idx)vListIndex.get(k);
            double index_ov = over_Idx.getOvt_idx();
            double hourTo = over_Idx.getHour_to();
            double hourFrom = over_Idx.getHour_from();
            double pay_index = 0.0;


            if((durationDbl>0 && iDurationMin>0) || (durationDbl>0 && iDurationMin==0)) 
            {
                    if((iDurationHour>=hourFrom) && (iDurationHour<hourTo))
                    {
                            index_pay = String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin);
                            pay_index = Double.parseDouble(index_pay);
                            total_idx = pay_index * index_ov;
                            jumlah = jumlah + total_idx;
                            durationDbl = durationDbl - iDurationHour;
                            //Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
                            iDurationMin = 0;
                    }
                    else
                    {
                            if(iDurationHour<hourTo)
                            {
                                    index_pay = iDurationHour+"."+iDurationMin;
                                    //Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
                                    iDurationMin = 0;
                            }
                            else if(iDurationHour==0)
                            {
                                    index_pay = "0."+iDurationMin;
                            }
                            else{
                                    index_pay = String.valueOf(hourTo);
                            }
                            durationDbl = durationDbl - hourTo;
                            String strDuration = String.valueOf(durationDbl);
                            long durationLong = Long.parseLong(strDuration.substring(0,strDuration.indexOf(".")));
                            iDurationHour = durationLong;
                            pay_index = Double.parseDouble(index_pay);
                            total_idx = pay_index * index_ov;
                            jumlah = jumlah + total_idx;
                            System.out.println("total_idx:::::::::::::::::;"+total_idx);
                    }
            }
            else
            {
                    //untuk mengecek setelah pengurangan Hournya = 0, jika telah habis dikurangi maka melakukan pengecekan terhadap iDurationHournya  
                    if(iDurationMin>0)
                    {
                              index_pay = "0."+iDurationMin;
                              pay_index = Double.parseDouble(index_pay);
                              total_idx = pay_index * index_ov;
                              jumlah = jumlah + total_idx;
                              System.out.println("total_idx:::::::::::::::::;"+total_idx);
                              //jika masih lebih besar dari nol maka di set iDurationMin = 0
                              iDurationMin = 0;
                    }
                    else
                    {
                            index_pay = String.valueOf(0.0);
                            pay_index = Double.parseDouble(index_pay);
                            total_idx = pay_index * index_ov;
                            jumlah = jumlah + total_idx;
                            System.out.println("total_idx:::::::::::::::::;"+total_idx);
                    }

            }
        }
    }
    return jumlah;
}
//------------- start added by Yunny----------------------------
/**
    * this method used to get value of idx duration
    *  @ param = dateOfMonth
    *  @ param = periodId
    *  @ param = employeeNum
    * created by yunny
    */
    public static double getOvtDuration(String dateOfMonth,long periodId,String empNum){
        DBResultSet dbrs = null;
        double result = 0;
        try{
	        String sql = "SELECT "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_DURATION] +
			     " FROM " +PstOvt_Employee.TBL_OVT_EMPLOYEE + 
                             " WHERE " +PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID]+
                             " = "+periodId+
                             " AND (DAYOFMONTH("+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_WORK_DATE]+
                             ")) = "+dateOfMonth+
                             " AND "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]+
                             " = '"+empNum+"'";
                    
                    //System.out.println("sqlgetOvtDuration   "+sql);
                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                   // System.out.println("sql   "+sql);
                    while(rs.next()) { 
                        result = rs.getDouble(1);}
                    rs.close();
	        
        }catch(Exception e){
            System.out.println("Error");
        }
        return result;
	}

    /**
     * Calculate number of dates when an employee did overtime with minimum hours in a period and dates list
     * @param dates   : vector of dates to be check
     * @param periodId : period ID to be check
     * @param empNum    : employee number
     * @param minOvtmHours : minimum of hours each date to be calculated as overtime
     * @return total of date with overtime
     */
    public static int getTotalDatesOverTm(Vector dates, long periodId, String empNum, double minOvtmHours){
        int tl=0;
        
        if((dates==null) || (dates.size()<1) )
            return tl;
        
        for (int i=0;i< dates.size();i++){
            Date dt = (Date) dates.get(i);
            double ovtLen = PstOvt_Employee.getOvtDuration(""+dt.getDate(),periodId,empNum);
            if(ovtLen>=minOvtmHours){
                tl++;
            }            
        }                
        return tl;
    }
    
    
/*
 *method get for out from tabel hr_empSchedule
 *create by emerliana
 */
    
 public static String listDateOut(int j, long employee_id, long period_id){
     DBResultSet dbrs = null;
     //Date dtOut = new Date();
     String stOut = "";
     try{
                 String sql = "SELECT ";

                 if(j==1){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT1];
                 }else if(j==2){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2];
                 }else if(j==3){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT3];
                 }else if(j==4){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT4];
                 }else if(j==5){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT5];
                 }else if(j==6){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT6];
                 }else if(j==7){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT7];
                 }else if(j==8){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT8];
                 }else if(j==9){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT9];
                 }else if(j==10){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT10];
                 }else if(j==11){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT11];
                 }else if(j==12){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT12];
                 }else if(j==13){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT13];
                 }else if(j==14){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT14];
                 }else if(j==15){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT15];
                 }else if(j==16){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT16];
                 }else if(j==17){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT17];
                 }else if(j==18){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT18];
                 }else if(j==19){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT19];
                 }else if(j==20){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT20];
                 }else if(j==21){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT21];
                 }else if(j==22){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT22];
                 }else if(j==23){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT23];
                 }else if(j==24){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT24];
                 }else if(j==25){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT25];
                 }else if(j==26){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT26];
                 }else if(j==27){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT27];
                 }else if(j==28){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT28];
                 }else if(j==29){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT29];
                 }else if(j==30){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT30];
                 }else if(j==31){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT31];
                 }

                 sql = sql + " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+
                            " WHERE "+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+" = "+period_id+
                            " AND "+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = "+employee_id;

                 dbrs = DBHandler.execQueryResult(sql);
                 ResultSet rs = dbrs.getResultSet();

                 while(rs.next()) { 
                                    stOut = rs.getString(1);
                 }
                    rs.close();
     }catch(Exception e){
            System.out.println("Error");
     }
     
     return stOut;
 }
 
 //method for get start in empSchedule
 public static String listDateIn(int j, long employee_id, long period_id){
     DBResultSet dbrs = null;
     //Date dtOut = new Date();
     String stIn = "";
     try{
                 String sql = "SELECT ";

                 if(j==1){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN1];
                 }else if(j==2){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2];
                 }else if(j==3){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN3];
                 }else if(j==4){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN4];
                 }else if(j==5){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN5];
                 }else if(j==6){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN6];
                 }else if(j==7){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN7];
                 }else if(j==8){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN8];
                 }else if(j==9){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN9];
                 }else if(j==10){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN10];
                 }else if(j==11){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN11];
                 }else if(j==12){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN12];
                 }else if(j==13){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN13];
                 }else if(j==14){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN14];
                 }else if(j==15){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN15];
                 }else if(j==16){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN16];
                 }else if(j==17){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN17];
                 }else if(j==18){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN18];
                 }else if(j==19){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN19];
                 }else if(j==20){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN20];
                 }else if(j==21){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN21];
                 }else if(j==22){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN22];
                 }else if(j==23){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN23];
                 }else if(j==24){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN24];
                 }else if(j==25){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN25];
                 }else if(j==26){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN26];
                 }else if(j==27){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN27];
                 }else if(j==28){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN28];
                 }else if(j==29){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN29];
                 }else if(j==30){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN30];
                 }else if(j==31){
                     sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN31];
                 }

                 sql = sql + " FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+
                            " WHERE "+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+" = "+period_id+
                            " AND "+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = "+employee_id;

                 dbrs = DBHandler.execQueryResult(sql);
                 ResultSet rs = dbrs.getResultSet();

                 while(rs.next()) { 
                                    stIn = rs.getString(1);
                 }
                    rs.close();
     }catch(Exception e){
            System.out.println("Error");
     }
     
     return stIn;
 }
 
 /* This method used to get total idx of overtime
     * @ param : paySlipId
     * Created by Yunny
     */
    public static double getTotIdx(long paySlipId){
		DBResultSet dbrs = null;
                double sumSalary = 0;
		try {
                        String sql = "SELECT SUM("+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_TOT_IDX]+") FROM "+
                                     PstOvt_Employee.TBL_OVT_EMPLOYEE+
                                     " WHERE "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PAY_SLIP_ID]+
                                     " = "+paySlipId;
                      //  System.out.println("SQL getTotIdx"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        while(rs.next()) { sumSalary = rs.getDouble(1); }
                    	rs.close();
			return sumSalary;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    
    
    
    
    
    /* This method used to get total ovt of overtime
     * @ param : paySlipId
     * Created by Yunny
     */
    public static double getTotOvtDuration(long paySlipId){
		DBResultSet dbrs = null;
                double sumSalary = 0;
		try {
                        String sql = "SELECT SUM("+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_DURATION]+") FROM "+
                                     PstOvt_Employee.TBL_OVT_EMPLOYEE+
                                     " WHERE "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PAY_SLIP_ID]+
                                     " = "+paySlipId;
                        System.out.println("SQL getTotOvtDuration"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        while(rs.next()) { sumSalary = rs.getDouble(1); }
                    	rs.close();
			return sumSalary;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    
    /* this methode created to get the status of overtime
     *  @param : employeeNum
     * @param : periodId
     * @param : workDate
     * Created By Yunny
     */
    
    public static int getStatus(String employeeNum, long periodId, String workDate ){
		DBResultSet dbrs = null;
		try {
                	String sql = "SELECT STATUS FROM " + TBL_OVT_EMPLOYEE+
                                      " WHERE "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]+"='"+employeeNum+"'"+
                                      " AND "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID]+"="+periodId+
                                      " AND "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_WORK_DATE]+" = '"+workDate+"'";
			//System.out.println("SQL getStatus"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        
			int status = 0;
			while(rs.next()) { status = rs.getInt(1); }

			rs.close();
			return status;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    
    /* This method used to list employee overtime
     * Created By Yunny
     */
    
   public static Vector getOvtEmployee(long departmentId,long sectionId,long periodId, int index) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
       
        if (departmentId==0 && sectionId==0 && periodId==0)
            return new Vector(1,1);
         try {
            String sql = " SELECT  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", CAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+
            ", OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]+
            ", LEVEL."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]+
            " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP"+
            " INNER JOIN "+PstOvt_Employee.TBL_OVT_EMPLOYEE+" AS OVT"+
            " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            " = OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]+
            " INNER JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY+" AS CAT"+
            " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+
            " = CAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+
            " INNER JOIN "+PstPayEmpLevel.TBL_PAY_EMP_LEVEL+" AS LEVEL"+
            " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            " = LEVEL."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]+
            " WHERE  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"=0"+
            " AND LEVEL."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+"="+PstPayEmpLevel.CURRENT;
            
            String whereClause = "";
            
            
            
             if(departmentId!= 0){
                whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                " = "+departmentId+ " AND " ;
            }
           
              
           if(sectionId!= 0){
                      whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                     " = "+sectionId + " AND ";
                     
           }
            if(periodId!= 0){
                      whereClause = whereClause + " OVT."+PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+
                     " = "+periodId + " AND ";
                     
            }
            
             if(index < 2){
                      whereClause = whereClause + " LEVEL."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]+
                     " = "+index + " AND ";
                     
            }
            
            if(whereClause != null && whereClause.length()>0){
                whereClause = " AND "+ whereClause.substring(0,whereClause.length()-4);
                sql = sql + whereClause;
                 //sql = sql + " WHERE " + whereClause;
            }
            
            sql = sql + " GROUP BY OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM];
            System.out.println("\t SQL PstOvt_Employee.getOvtEmployee With index : " + sql);
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) {
                Vector vect = new Vector(1,1);
                
                Employee employee = new Employee();
                Ovt_Employee ovtEmp = new Ovt_Employee();
                EmpCategory empCat = new EmpCategory();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
              
                ovtEmp.setEmployee_num(rs.getString(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]));
                vect.add(ovtEmp);
                
                empCat.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCat);
                
                payEmpLevel.setOvtIdxType(rs.getInt(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]));
                vect.add(payEmpLevel);
                
                result.add(vect);
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
  
 
 public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        
        /*Date netDate = new Date();
        Ovt_Employee ovt_Employee = new Ovt_Employee();
        ovt_Employee.setEmployee_num("asdasdasfffdddsss");
        ovt_Employee.setDuration(1.2);
        ovt_Employee.setWorkDate(netDate);
        ovt_Employee.setTot_Idx(2.36);
        try{
            PstOvt_Employee.insertExc(ovt_Employee);
        }catch(Exception e){
            System.out.println("Err"+e.toString());
        }*/
     
     String stOut = listDateOut(26, 226, 504404343502872505L);
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
     System.out.println("coba:::::::::::::::::::::::::::::::::::"+coba);
     
    }

}

