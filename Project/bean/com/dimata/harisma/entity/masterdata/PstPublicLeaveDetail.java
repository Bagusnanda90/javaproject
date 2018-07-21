/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;
/* package java */ 
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
/**
 *
 * @author Satrya Ramayu
 */
public class PstPublicLeaveDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    public static final  String TBL_PUBLIC_LEAVE_DETAIL      =   "hr_public_leave_detail";//"";

	public static final  int FLD_DETAIL_PUBLIC_DETAIL_ID     = 0;
	public static final  int FLD_EMPLOYEE_ID        = 1;
	public static final  int FLD_TYPE_LEAVE_ID        = 2;
	public static final  int FLD_PUBLIC_LEAVE_ID        = 3;
	public static final  int FLD_PUBLIC_HOLIDAY_ID        = 4;
        public static final  int FLD_LEAVE_APPLICATION_ID        = 5;
        public static final  int FLD_PUBLIC_LEAVE_DETAIL_DATE_FROM        = 6;
        public static final  int FLD_PUBLIC_LEAVE_DETAIL_DATE_TO        = 7;
	public static final  String[] fieldNames = {
		"DETAIL_PUBLIC_LEAVE_ID",
		"EMPLOYEE_ID",
		"TYPE_LEAVE",
		"PUBLIC_LEAVE_ID",
		"PUBLIC_HOLIDAY_ID",
                "LEAVE_APPLICATION_ID",
                "DATE_FROM_DETAIL",
                "DATE_TO_DETAIL"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_LONG,
		TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_DATE,
                TYPE_DATE
	 }; 
          public static final int TYPE_LEAVE=1;
	public PstPublicLeaveDetail(){
	}

	public PstPublicLeaveDetail(int i) throws DBException { 
		super(new PstPublicLeaveDetail()); 
	}

	public PstPublicLeaveDetail(String sOid) throws DBException { 
		super(new PstPublicLeaveDetail(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstPublicLeaveDetail(long lOid) throws DBException { 
		super(new PstPublicLeaveDetail(0)); 
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
		return TBL_PUBLIC_LEAVE_DETAIL;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstPublicLeaveDetail().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		PublicLeaveDetail publicLeaveDetail = fetchExc(ent.getOID()); 
		ent = (Entity)publicLeaveDetail; 
		return publicLeaveDetail.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((PublicLeaveDetail) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((PublicLeaveDetail) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static PublicLeaveDetail fetchExc(long oid) throws DBException{ 
		try{ 
			PublicLeaveDetail publicLeaveDetail = new PublicLeaveDetail();
			PstPublicLeaveDetail PstPublicLeaveDetail = new PstPublicLeaveDetail(oid); 
			publicLeaveDetail.setOID(oid);

			publicLeaveDetail.setEmployeeId(PstPublicLeaveDetail.getlong(FLD_EMPLOYEE_ID));
			publicLeaveDetail.setPublicHolidayId(PstPublicLeaveDetail.getlong(FLD_PUBLIC_HOLIDAY_ID));
			publicLeaveDetail.setPublicLeaveId(PstPublicLeaveDetail.getlong(FLD_PUBLIC_LEAVE_ID));
			publicLeaveDetail.setTypeLeaveId(PstPublicLeaveDetail.getlong(FLD_TYPE_LEAVE_ID));
                        publicLeaveDetail.setAppLeaveId(PstPublicLeaveDetail.getlong(FLD_LEAVE_APPLICATION_ID));
                        publicLeaveDetail.setDateFrom(PstPublicLeaveDetail.getDate(FLD_PUBLIC_LEAVE_DETAIL_DATE_FROM));
                        publicLeaveDetail.setDateTo(PstPublicLeaveDetail.getDate(FLD_PUBLIC_LEAVE_DETAIL_DATE_TO));

			return publicLeaveDetail; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPublicLeaveDetail(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(PublicLeaveDetail publicLeaveDetail) throws DBException{ 
		try{ 
			PstPublicLeaveDetail pstPublicLeaveDetail = new PstPublicLeaveDetail(0);

			pstPublicLeaveDetail.setLong(FLD_EMPLOYEE_ID, publicLeaveDetail.getEmployeeId());
			pstPublicLeaveDetail.setLong(FLD_PUBLIC_HOLIDAY_ID, publicLeaveDetail.getPublicHolidayId());
			pstPublicLeaveDetail.setLong(FLD_PUBLIC_LEAVE_ID, publicLeaveDetail.getPublicLeaveId());
			pstPublicLeaveDetail.setLong(FLD_TYPE_LEAVE_ID, publicLeaveDetail.getTypeLeaveId());
                        pstPublicLeaveDetail.setLong(FLD_LEAVE_APPLICATION_ID, publicLeaveDetail.getAppLeaveId());
                        pstPublicLeaveDetail.setDate(FLD_PUBLIC_LEAVE_DETAIL_DATE_FROM, publicLeaveDetail.getDateFrom());
                        pstPublicLeaveDetail.setDate(FLD_PUBLIC_LEAVE_DETAIL_DATE_TO, publicLeaveDetail.getDateTo());
			pstPublicLeaveDetail.insert(); 
			publicLeaveDetail.setOID(pstPublicLeaveDetail.getlong(FLD_DETAIL_PUBLIC_DETAIL_ID)); 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPublicLeaveDetail(0),DBException.UNKNOWN); 
		}
		return publicLeaveDetail.getOID();
	}

	public static long updateExc(PublicLeaveDetail publicLeaveDetail) throws DBException{ 
		try{ 
                    if(publicLeaveDetail.getOID() != 0){ 
                        PstPublicLeaveDetail pstPublicLeaveDetail = new PstPublicLeaveDetail(publicLeaveDetail.getOID());

                        pstPublicLeaveDetail.setLong(FLD_EMPLOYEE_ID, publicLeaveDetail.getEmployeeId());
			pstPublicLeaveDetail.setLong(FLD_PUBLIC_HOLIDAY_ID, publicLeaveDetail.getPublicHolidayId());
			pstPublicLeaveDetail.setLong(FLD_PUBLIC_LEAVE_ID, publicLeaveDetail.getPublicLeaveId());
			pstPublicLeaveDetail.setLong(FLD_TYPE_LEAVE_ID, publicLeaveDetail.getTypeLeaveId());
                        pstPublicLeaveDetail.setLong(FLD_LEAVE_APPLICATION_ID, publicLeaveDetail.getAppLeaveId());
                        pstPublicLeaveDetail.setDate(FLD_PUBLIC_LEAVE_DETAIL_DATE_FROM, publicLeaveDetail.getDateFrom());
                        pstPublicLeaveDetail.setDate(FLD_PUBLIC_LEAVE_DETAIL_DATE_TO, publicLeaveDetail.getDateTo());
                        pstPublicLeaveDetail.update(); 
                        return publicLeaveDetail.getOID();

                    }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPublicLeaveDetail(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPublicLeaveDetail pstPublicLeaveDetail = new PstPublicLeaveDetail(oid);
			pstPublicLeaveDetail.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPublicLeaveDetail(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PUBLIC_LEAVE_DETAIL; 
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
				PublicLeaveDetail publicLeaveDetail = new PublicLeaveDetail();
				resultToObject(rs, publicLeaveDetail);
				lists.add(publicLeaveDetail);
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

	private static void resultToObject(ResultSet rs, PublicLeaveDetail publicLeaveDetail){
		try{
			publicLeaveDetail.setOID(rs.getLong(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_DETAIL_PUBLIC_DETAIL_ID]));
			publicLeaveDetail.setEmployeeId(rs.getLong(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_EMPLOYEE_ID]));
			publicLeaveDetail.setPublicHolidayId(rs.getLong(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_PUBLIC_HOLIDAY_ID]));
			publicLeaveDetail.setPublicLeaveId(rs.getLong(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_PUBLIC_LEAVE_ID]));
			publicLeaveDetail.setTypeLeaveId(rs.getLong(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_TYPE_LEAVE_ID]));
                        publicLeaveDetail.setTypeLeaveId(rs.getLong(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_LEAVE_APPLICATION_ID]));
                        publicLeaveDetail.setDateFrom(rs.getDate(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_PUBLIC_LEAVE_DETAIL_DATE_FROM]));
                        publicLeaveDetail.setDateTo(rs.getDate(PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_PUBLIC_LEAVE_DETAIL_DATE_TO]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long publicDetailId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PUBLIC_LEAVE_DETAIL + " WHERE " + 
						PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_DETAIL_PUBLIC_DETAIL_ID] + " = " + publicDetailId;

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
			String sql = "SELECT COUNT("+ PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_DETAIL_PUBLIC_DETAIL_ID] + ") FROM " + TBL_PUBLIC_LEAVE_DETAIL;
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
			  	   PublicLeaveDetail publicLeaveDetail = (PublicLeaveDetail)list.get(ls);
				   if(oid == publicLeaveDetail.getOID())
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
 * Keterangan : Fungsi untuk menampilkan Employee Id, Divison Name, Departement Name, Section Name, Employee Category Name
 * @param limitStart
 * @param recordToGet
 * @param order
 * @param empNumber
 * @param fullName
 * @param departementId
 * @param section
 * @param empCat
 * @return 
 */
    //public static Vector getEmployeeidWithEmpCategory(int limitStart, int recordToGet, String order,String empNumber,String fullName,long departementId,long section,long empCat,int status) 
  public static Vector getEmployeeidWithEmpCategory(int limitStart, int recordToGet, String order,String empNumber,String fullName,long departementId,long section,long empCat,int status,long oidReligion,long oidCompany,long oidDivision) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+","
                    + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+","
                     + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+","
                    + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+","
                    + " DIVX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+","
                    + " DPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+","
                    + " SEC."+PstSection.fieldNames[PstSection.FLD_SECTION]+","
                    + " EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + " FROM "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
+ " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DPT ON (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"=DPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+")"
+ " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " AS DIVX ON (EMP."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"=DIVX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+")"
+ " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SEC ON (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"=SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+")"
+ " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " AS CMP ON (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"=CMP."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+")"
+ " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS EMPCAT ON (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+ " = EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+")" 
+ " WHERE (1=1) ";
            if(empNumber!=null && empNumber.length()>0){
                sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+ "=\""+empNumber+"\""; 
            }
            if(fullName!=null && fullName.length()>0){
                sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+ "=\""+fullName+"\""; 
            }
            if(departementId!=0){
                 sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ "=\""+departementId+"\""; 
            }
            if(section!=0){
                 sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+ "=\""+section+"\""; 
            }
            if(empCat!=0){
                sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+ "=\""+empCat+"\""; 
            }
            //update by devin 2014-02-08
            if(status==0){
                sql= sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " +status;
            }
            //update by devin 2014-02-17
            if(oidReligion>0){
                sql= sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + " = " +oidReligion;
            }
            //update by devin 2014-02-18
            if(oidCompany>0){
                sql= sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " +oidCompany;
            }
            if(oidDivision>0){
                sql= sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " +oidDivision;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
    
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee  " + sql);
            while (rs.next()) {
                PublicLeaveDetail publicLeaveDetail = new PublicLeaveDetail();
                //resultToObject(rs, employee);
                publicLeaveDetail.setEmployeeId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                publicLeaveDetail.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                publicLeaveDetail.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                publicLeaveDetail.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                publicLeaveDetail.setDepartement(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                publicLeaveDetail.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                publicLeaveDetail.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                publicLeaveDetail.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                lists.add(publicLeaveDetail);
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

 
}
