
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.startdata; 

/* package java */ 
import java.sql.*
;import java.util.*
;import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
import com.dimata.harisma.entity.startdata.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.util.*;

public class PstEmployeeStart extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static long depOID = 0;
    public static long secOID = 0;
    public static long posOID = 0;


	public static final  String TBL_EMPLOYEE_START = "employee_start";//"EMPLOYEE_START";

	public static final  int FLD_ID = 0;
	public static final  int FLD_REG = 1;
	public static final  int FLD_NIK = 2;
	public static final  int FLD_NAME = 3;
	public static final  int FLD_ADDRESS1 = 4;
	public static final  int FLD_ADDRESS2 = 5;
	public static final  int FLD_CITY = 6;
	public static final  int FLD_PHONE = 7;
	public static final  int FLD_SEX = 8;
	public static final  int FLD_RELIGION = 9;
	public static final  int FLD_DIVITION = 10;
	public static final  int FLD_DEP = 11;
	public static final  int FLD_LOCATION = 12;
	public static final  int FLD_STATUS = 13;
	public static final  int FLD_POSITION = 14;
	public static final  int FLD_CHILD = 15;
	public static final  int FLD_DOB = 16;
	public static final  int FLD_START = 17;
    public static final  int FLD_LEVEL = 18;

	public static final  String[] fieldNames = {
		"ID",
		"REG",
		"NIK",
		"NAME",
		"ADDRESS1",
		"ADDRESS2",
		"CITY",
		"PHONE",
		"SEX",
		"RELIGION",
		"DIVITION",
		"DEP",
		"LOCATION",
		"STATUS",
		"POSITION",
		"CHILD",
		"DOB",
		"START",
        "LEVEL"
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
		TYPE_STRING,
		TYPE_STRING,
		TYPE_FLOAT,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_FLOAT,
		TYPE_STRING,
		TYPE_DATE,
        TYPE_STRING
	 };

	public PstEmployeeStart(){
	}

	public PstEmployeeStart(int i) throws DBException { 
		super(new PstEmployeeStart()); 
	}

	public PstEmployeeStart(String sOid) throws DBException { 
		super(new PstEmployeeStart(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstEmployeeStart(long lOid) throws DBException { 
		super(new PstEmployeeStart(0)); 
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
		return TBL_EMPLOYEE_START;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstEmployeeStart().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		EmployeeStart employeestart = fetchExc(ent.getOID()); 
		ent = (Entity)employeestart; 
		return employeestart.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((EmployeeStart) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((EmployeeStart) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static EmployeeStart fetchExc(long oid) throws DBException{ 
		try{ 
			EmployeeStart employeestart = new EmployeeStart();
			PstEmployeeStart pstEmployeeStart = new PstEmployeeStart(oid); 
			employeestart.setOID(oid);

			employeestart.setReg(pstEmployeeStart.getString(FLD_REG));
			employeestart.setNik(pstEmployeeStart.getString(FLD_NIK));
			employeestart.setName(pstEmployeeStart.getString(FLD_NAME));
			employeestart.setAddress1(pstEmployeeStart.getString(FLD_ADDRESS1));
			employeestart.setAddress2(pstEmployeeStart.getString(FLD_ADDRESS2));
			employeestart.setCity(pstEmployeeStart.getString(FLD_CITY));
			employeestart.setPhone(pstEmployeeStart.getString(FLD_PHONE));
			employeestart.setSex(pstEmployeeStart.getString(FLD_SEX));
			employeestart.setReligion(pstEmployeeStart.getString(FLD_RELIGION));
			employeestart.setDivition(pstEmployeeStart.getdouble(FLD_DIVITION));
			employeestart.setDep(pstEmployeeStart.getString(FLD_DEP));
			employeestart.setLocation(pstEmployeeStart.getString(FLD_LOCATION));
			employeestart.setStatus(pstEmployeeStart.getString(FLD_STATUS));
			employeestart.setPosition(pstEmployeeStart.getString(FLD_POSITION));
			employeestart.setChild(pstEmployeeStart.getdouble(FLD_CHILD));
			employeestart.setDob(pstEmployeeStart.getString(FLD_DOB));
			employeestart.setStart(pstEmployeeStart.getDate(FLD_START));
            employeestart.setLevel(pstEmployeeStart.getString(FLD_LEVEL));

			return employeestart;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeStart(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(EmployeeStart employeestart) throws DBException{ 
		try{ 
			PstEmployeeStart pstEmployeeStart = new PstEmployeeStart(0);

			pstEmployeeStart.setString(FLD_REG, employeestart.getReg());
			pstEmployeeStart.setString(FLD_NIK, employeestart.getNik());
			pstEmployeeStart.setString(FLD_NAME, employeestart.getName());
			pstEmployeeStart.setString(FLD_ADDRESS1, employeestart.getAddress1());
			pstEmployeeStart.setString(FLD_ADDRESS2, employeestart.getAddress2());
			pstEmployeeStart.setString(FLD_CITY, employeestart.getCity());
			pstEmployeeStart.setString(FLD_PHONE, employeestart.getPhone());
			pstEmployeeStart.setString(FLD_SEX, employeestart.getSex());
			pstEmployeeStart.setString(FLD_RELIGION, employeestart.getReligion());
			pstEmployeeStart.setDouble(FLD_DIVITION, employeestart.getDivition());
			pstEmployeeStart.setString(FLD_DEP, employeestart.getDep());
			pstEmployeeStart.setString(FLD_LOCATION, employeestart.getLocation());
			pstEmployeeStart.setString(FLD_STATUS, employeestart.getStatus());
			pstEmployeeStart.setString(FLD_POSITION, employeestart.getPosition());
			pstEmployeeStart.setDouble(FLD_CHILD, employeestart.getChild());
			pstEmployeeStart.setString(FLD_DOB, employeestart.getDob());
			pstEmployeeStart.setDate(FLD_START, employeestart.getStart());
            pstEmployeeStart.setString(FLD_LEVEL, employeestart.getLevel());

			pstEmployeeStart.insert();
			employeestart.setOID(pstEmployeeStart.getlong(FLD_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeStart(0),DBException.UNKNOWN); 
		}
		return employeestart.getOID();
	}

	public static long updateExc(EmployeeStart employeestart) throws DBException{ 
		try{ 
			if(employeestart.getOID() != 0){ 
				PstEmployeeStart pstEmployeeStart = new PstEmployeeStart(employeestart.getOID());

				pstEmployeeStart.setString(FLD_REG, employeestart.getReg());
				pstEmployeeStart.setString(FLD_NIK, employeestart.getNik());
				pstEmployeeStart.setString(FLD_NAME, employeestart.getName());
				pstEmployeeStart.setString(FLD_ADDRESS1, employeestart.getAddress1());
				pstEmployeeStart.setString(FLD_ADDRESS2, employeestart.getAddress2());
				pstEmployeeStart.setString(FLD_CITY, employeestart.getCity());
				pstEmployeeStart.setString(FLD_PHONE, employeestart.getPhone());
				pstEmployeeStart.setString(FLD_SEX, employeestart.getSex());
				pstEmployeeStart.setString(FLD_RELIGION, employeestart.getReligion());
				pstEmployeeStart.setDouble(FLD_DIVITION, employeestart.getDivition());
				pstEmployeeStart.setString(FLD_DEP, employeestart.getDep());
				pstEmployeeStart.setString(FLD_LOCATION, employeestart.getLocation());
				pstEmployeeStart.setString(FLD_STATUS, employeestart.getStatus());
				pstEmployeeStart.setString(FLD_POSITION, employeestart.getPosition());
				pstEmployeeStart.setDouble(FLD_CHILD, employeestart.getChild());
				pstEmployeeStart.setString(FLD_DOB, employeestart.getDob());
				pstEmployeeStart.setDate(FLD_START, employeestart.getStart());
                pstEmployeeStart.setString(FLD_LEVEL, employeestart.getLevel());

				pstEmployeeStart.update();
				return employeestart.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeStart(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstEmployeeStart pstEmployeeStart = new PstEmployeeStart(oid);
			pstEmployeeStart.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmployeeStart(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_EMPLOYEE_START; 
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
				EmployeeStart employeestart = new EmployeeStart();
				resultToObject(rs, employeestart);
				lists.add(employeestart);
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

	private static void resultToObject(ResultSet rs, EmployeeStart employeestart){
		try{
			employeestart.setOID(rs.getLong(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_ID]));
			employeestart.setReg(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_REG]));
			employeestart.setNik(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_NIK]));
			employeestart.setName(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_NAME]));
			employeestart.setAddress1(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_ADDRESS1]));
			employeestart.setAddress2(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_ADDRESS2]));
			employeestart.setCity(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_CITY]));
			employeestart.setPhone(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_PHONE]));
			employeestart.setSex(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_SEX]));
			employeestart.setReligion(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_RELIGION]));
			employeestart.setDivition(rs.getDouble(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_DIVITION]));
			employeestart.setDep(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_DEP]));
			employeestart.setLocation(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_LOCATION]));
			employeestart.setStatus(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_STATUS]));
			employeestart.setPosition(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_POSITION]));
			employeestart.setChild(rs.getDouble(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_CHILD]));
			employeestart.setDob(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_DOB]));
			employeestart.setStart(rs.getDate(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_START]));
            employeestart.setLevel(rs.getString(PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_LEVEL]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long id){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_EMPLOYEE_START + " WHERE " + 
						PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_ID] + " = " + id;

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
			String sql = "SELECT COUNT("+ PstEmployeeStart.fieldNames[PstEmployeeStart.FLD_ID] + ") FROM " + TBL_EMPLOYEE_START;
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
			  	   EmployeeStart employeestart = (EmployeeStart)list.get(ls);
				   if(oid == employeestart.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}


    public static void deleteAll(){
        try{
            String sql = "DELETE FROM HR_EMPLOYEE";
            DBHandler.execUpdate(sql);

            sql = " DELETE FROM HR_DEPARTMENT";
            DBHandler.execUpdate(sql);

			sql = " DELETE FROM HR_POSITION";
            DBHandler.execUpdate(sql);

			sql = " DELETE FROM HR_SECTION";
            DBHandler.execUpdate(sql);

            sql = " DELETE FROM HR_LEVEL";
            DBHandler.execUpdate(sql);

        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        finally{

        }
    }

    public static long getDepartmentId(String strNum){
		if(strNum.trim().length()>0){
	        String where = PstOutletStart.fieldNames[PstOutletStart.FLD_DEP]+"='"+strNum+"'";
	        Vector vct = PstOutletStart.list(0,0,where, null);
	        OutletStart os = new OutletStart();
	        Department dep = new Department();
	        if(vct!=null && vct.size()>0){
	        	os = (OutletStart)vct.get(0);
	
	            where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+"='"+os.getDepName().trim()+"'";
		        vct = PstDepartment.list(0,0, where, null);
	
	            if(vct!=null && vct.size()>0){
	            	dep = (Department)vct.get(0);
	            }
	        }
	
	        return dep.getOID();

        }
        return 0;
    }


    public static long getPositionId(String strNum){
        if(strNum.trim().length()>0){
	        String where = PstPosition.fieldNames[PstPosition.FLD_POSITION]+"='"+strNum+"'";
	        Vector vct = PstPosition.list(0,0,where, null);
	
	        Position pos = new Position();
	        if(vct!=null && vct.size()>0){
		        pos = (Position)vct.get(0);
	        }
	
	        return pos.getOID();
        }
        return 0;
    }

    public static long getSectionId(String strNum){
        if(strNum.trim().length()>0){
	        String where = PstOutletStart.fieldNames[PstOutletStart.FLD_LOCATION]+"='"+strNum+"'";
	        Vector vct = PstOutletStart.list(0,0,where, null);
	        OutletStart os = new OutletStart();
	        Section dep = new Section();
	        if(vct!=null && vct.size()>0){
	        	os = (OutletStart)vct.get(0);
	
	            where = PstSection.fieldNames[PstSection.FLD_SECTION]+"='"+os.getLocName().trim()+"'";
		        vct = PstSection.list(0,0, where, null);
	
	            if(vct!=null && vct.size()>0){
	            	dep = (Section)vct.get(0);
	            }
	        }
	
	        return dep.getOID();
        }
        return 0;
    }


    public static long getEmpcategory(String strNum){
        String where = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+"='"+strNum+"'";
        Vector vct = PstEmpCategory.list(0,0,where, null);
        EmpCategory os = new EmpCategory();

        if(vct!=null && vct.size()>0){
        	os = (EmpCategory)vct.get(0);

        }

        return os.getOID();
    }



    public static void insertDepartment(){
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT DISTINCT DEP_NAME FROM OUTLET_START";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Department dep = new Department();
            dep.setDepartment("UNKNOWN");
            dep.setDescription("-");

            try{
                depOID = PstDepartment.insertExc(dep);
                System.out.println(">>> -- "+dep.getDepartment()) ;
            }
            catch(Exception e){
                System.out.println("1 - "+e.toString());
            }

            Section sec = new Section();
            sec.setSection("UNKNOWN");
            sec.setDepartmentId(depOID);
            sec.setDescription("-");

            try{
                secOID = PstSection.insertExc(sec);
                System.out.println(">>>>>>>>> -- "+sec.getSection()) ;
            }
            catch(Exception e){
                         System.out.println("2 - "+e.toString());
            }

            while(rs.next()){
                String str = rs.getString("DEP_NAME");
                dep = new Department();
                dep.setDepartment(str);
                dep.setDescription("-");

                try{
                    PstDepartment.insertExc(dep);
                    System.out.println(">>> -- "+dep.getDepartment()) ;
                }
                catch(Exception e){
                    System.out.println("1 - "+e.toString());
                }

                if(dep.getOID()!=0){
                    DBResultSet dbrs1 = null;
                    try{
	                    sql = "SELECT LOC_NAME FROM OUTLET_START WHERE DEP_NAME = '"+str+"'";
			            dbrs1 = DBHandler.execQueryResult(sql);
			            ResultSet rs1 = dbrs1.getResultSet();

	                    while(rs1.next()){
	                        sec = new Section();
	                        sec.setSection(rs1.getString("LOC_NAME"));
	                        sec.setDepartmentId(dep.getOID());
	                        sec.setDescription("-");
	
	                        try{
			                    PstSection.insertExc(sec);
                                System.out.println(">>>>>>>>> -- "+sec.getSection()) ;
			                }
			                catch(Exception e){
                                System.out.println("2 - "+e.toString());
			                }
	                    }
                    }
                    catch(Exception e){
                        System.out.println("3 - "+e.toString());
                    }
                    finally{
                        DBResultSet.close(dbrs1);
                    }
                }

            }
        }
        catch(Exception e){
            System.out.println("4 - "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }
    }



    public static void insertPosition(){
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT DISTINCT POSITION FROM EMPLOYEE_START ORDER BY POSITION";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Position pos = new Position();
            pos.setPosition("UNKNOWN");
            pos.setDescription("-");

            try{
                posOID = PstPosition.insertExc(pos);
                System.out.println(">>> -- "+pos.getPosition()) ;
            }
            catch(Exception e){
                System.out.println("7 - "+e.toString());
            }

            while(rs.next()){
                String str = rs.getString("POSITION");
                pos = new Position();
                pos.setPosition(str);
                pos.setDescription("-");

                try{
                    PstPosition.insertExc(pos);
                    System.out.println(">>> -- "+pos.getPosition()) ;
                }
                catch(Exception e){
                    System.out.println("7 - "+e.toString());
                }
            }

        }
        catch(Exception e){
            System.out.println("5 - "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }
    }

    public static void insertLevel(){
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT DISTINCT LEVEL FROM EMPLOYEE_START";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                String str = rs.getString("LEVEL");
                Level pos = new Level();
                pos.setLevel(str);
                pos.setDescription(str);
                pos.setGroupRankId(504404191900498065L);

                try{
                    PstLevel.insertExc(pos);
                    System.out.println(">>> -- "+pos.getLevel()) ;
                }
                catch(Exception e){
                    System.out.println("7 - "+e.toString());
                }
            }
        }
        catch(Exception e){
            System.out.println("5 - "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }
    }

    public static long getReligion(String numb){
        int num = 0;
        try{
	       num = Integer.parseInt(numb);
        }
        catch(Exception e){
           System.out.println("19 -- "+e.toString());
        }

        String rel = "";
        switch (num){
        case 1 : rel = "Islam";
        break;
        case 2 : rel = "Kristen";
        break;
        case 3 : rel = "Hindu";
        break;
        case 4 : rel = "Budha";
        break;
        case 0 : rel = "UNKNOWN";
        break;
        }

		String where = PstReligion.fieldNames[PstReligion.FLD_RELIGION]+"='"+rel+"'";
        Vector vct = PstReligion.list(0,0, where, null);

        if(vct!=null && vct.size()>0){
        	Religion reli = (Religion)vct.get(0);
            return reli.getOID();
        }

        return 0;

    }

    public static long getLevel(String level){

		String where = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+"='"+level+"'";
        Vector vct = PstLevel.list(0,0, where, null);

        if(vct!=null && vct.size()>0){
        	Level reli = (Level)vct.get(0);
            return reli.getOID();
        }

        return 0;

    }

    public static long getMarital(String numb, double child){
        try{
	        int num = 0;
	        if(numb.trim().length()>0){
	        	num = Integer.parseInt(numb);
	        }
	        String rel = "";
	        switch (num){
            case 0 : rel = "S";
            break;
	        case 1 : rel = "S";
	        break;
	        case 2 : if(child==0){
                        rel = "M";
                     }else if(child==1){
	            		rel = "M1";
	        		 }else if(child==2){
	                	rel = "M2";
	            	 }else if(child==3){
	                    rel = "M3";
	                 }else if(child==4){
                        rel = "M4";
	                 }
	
	        break;
	        }
	
			String where = PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]+"='"+rel+"'";
	        Vector vct = PstMarital.list(0,0, where, null);
	
	        if(vct!=null && vct.size()>0){
	        	Marital reli = (Marital)vct.get(0);
	            return reli.getOID();
	        }
        }
        catch(Exception e){
            System.out.println("11 -- "+e.toString());
        }

        return 0;

    }


    public static void insertEmployee(){
        DBResultSet dbrs =  null;
        try{
            Vector vct = PstEmployeeStart.list(0,0, "", null);
            for(int i=0; i<vct.size(); i++){
                EmployeeStart emp = (EmployeeStart)vct.get(i);
                Employee empl = new Employee();

                String address = emp.getAddress1();
                if(emp.getAddress2().length()>0){
                    address = address + ", "+emp.getAddress2();
                }

                if(emp.getCity().length()>0){
                    address = address + ", "+emp.getCity();
                }

                empl.setAddress(address);
                empl.setFullName(emp.getName());

                empl.setLevelId(getLevel(emp.getLevel()));

    			try{
	                empl.setCommencingDate(emp.getStart());
                }
                catch(Exception e){
                    System.out.println("13 -- "+e.toString());
                }

                try{
                    if(getDepartmentId(emp.getDep())!=0){
                        empl.setDepartmentId(getDepartmentId(emp.getDep()));
                    }
                    else{
                        empl.setDepartmentId(depOID);
                    }
                }
                catch(Exception e){
                    System.out.println("14 -- "+e.toString());
                    empl.setDepartmentId(depOID);
                }

                empl.setEmployeeNum(emp.getNik());

                try{
	                empl.setMaritalId(getMarital(emp.getStatus(), emp.getChild()));
                }
                catch(Exception e){
                    System.out.println("15 -- "+e.toString());
                }

                empl.setPhone(""+emp.getPhone());

                try{
                    if(getPositionId(emp.getPosition())!=0){
	                	empl.setPositionId(getPositionId(emp.getPosition()));
                    }
                    else{
                        empl.setPositionId(posOID);
                    }
                }
                catch(Exception e){
                    System.out.println("16 -- "+e.toString());
                    empl.setPositionId(posOID);
                }

                try{
	                empl.setReligionId(getReligion(emp.getReligion()));
                }
                catch(Exception e){
                    System.out.println("17 -- "+e.toString());
                    empl.setReligionId(0);
                }

                try{
                    if(getSectionId(emp.getLocation())!=0){
		                empl.setSectionId(getSectionId(emp.getLocation()));
                    }
                    else{
                        empl.setSectionId(secOID);
                    }
                }
                catch(Exception e){
                    System.out.println("18 -- "+e.toString());
                    empl.setSectionId(secOID);
                }
                empl.setEmpCategoryId(getEmpcategory("Permanent"));

                String strDate = emp.getDob();
                if(strDate.trim().length()>0){
                    try{
		                Date dt = Formater.formatDate(strDate, "MM/dd/yy");
		                empl.setBirthDate(dt);
                    }
                    catch(Exception e){
                        System.out.println("12 -- "+e.toString());
                    }
                }
                // sex
                address = "";
                address = emp.getSex();
                if(address.trim().equalsIgnoreCase("F")){
					empl.setSex(PstEmployee.FEMALE);
                }
                else{
                    empl.setSex(PstEmployee.MALE);
                }

                // insert employee
                try{
                    PstEmployee.insertExc(empl);
                    System.out.println(">>> -- "+empl.getFullName()) ;
                }
                catch(Exception e){
                    System.out.println("9 - "+e.toString());
                }

            }
        }
        catch(Exception e){
            System.out.println("10 - "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }
    }


    public static void doTransfer(){
        System.out.println("--- deleting all data ");
        deleteAll();
        System.out.println("--- starting insert department ");
        insertDepartment();
        System.out.println("--- starting insert level ");
        insertLevel();
        System.out.println("--- starting insert position ");
        insertPosition();
        System.out.println("--- starting insert employee ");
        insertEmployee();
    }

    public static void doCreateBarcode(){
        Vector temp = PstEmployee.list(0,0,"","");
        if(temp!=null && temp.size()>0){
            for(int k=0;k<temp.size();k++){
                Employee emp = (Employee)temp.get(k);
                String barcode = emp.getEmployeeNum().substring(2,emp.getEmployeeNum().length());
                emp.setBarcodeNumber(barcode);

                try{
                    System.out.println("BARCODE => "+emp.getEmployeeNum()+" - "+emp.getBarcodeNumber());
                    PstEmployee.updateExc(emp);
                }catch(Exception e){
                    System.out.println("ER> "+e.toString());
                }
            }
        }
    }

    public static void doUpdateBirthDate(){
        Vector temp = PstEmployeeStart.list(0,0,"","");
        if(temp!=null && temp.size()>0){
            for(int k=0;k<temp.size();k++){
                EmployeeStart emp = (EmployeeStart)temp.get(k);
                Date dt = Formater.formatDate(emp.getDob(),"dd/MM/yyyy");
                System.out.println("XXX DT : "+dt);
                System.out.println("BIRTH DATE : "+k+" - "+Formater.formatDate(dt,"yyyy-MMM-dd"));

                String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+"='"+emp.getName()+"'";
                Vector vt = PstEmployee.list(0,0,whereClause,"");
                if(vt!=null && vt.size()>0){
                    Employee employee = (Employee)vt.get(0);
                    employee.setBirthDate(dt);
                    System.out.println("BIRTH DATE ASLI : "+Formater.formatDate(employee.getBirthDate(),"yyyy-MMM-dd"));
                    try{
                        PstEmployee.updateExc(employee);
                    }catch(Exception e){
                        System.out.println("ER> "+e.toString());
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        //doTransfer();
        //doCreateBarcode();
        doUpdateBirthDate();
    }
}
