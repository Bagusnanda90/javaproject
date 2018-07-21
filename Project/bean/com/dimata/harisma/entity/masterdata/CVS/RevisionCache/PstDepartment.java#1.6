/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.entity.masterdata;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

//Gede_2April2012{
import com.dimata.harisma.entity.overtime.*;
//}

/* package HRIS */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.sessdepartment.SessDepartment;
import com.dimata.system.entity.PstSystemProperty;

public class PstDepartment extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_DEPARTMENT = "hr_department";//"HR_DEPARTMENT";
    public static final int FLD_DEPARTMENT_ID = 0;
    public static final int FLD_DEPARTMENT = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static final int FLD_DIVISION_ID = 3;
    public static final int FLD_JOIN_TO_DEPARTMENT_ID = 4;
    public static final int FLD_DEPARTMENT_TYPE_ID = 5;
    public static final int FLD_ADDRESS = 6;
    public static final int FLD_CITY = 7;
    public static final int FLD_NPWP = 8;
    public static final int FLD_PROVINCE = 9;
    public static final int FLD_REGION = 10;
    public static final int FLD_SUB_REGION = 11;
    public static final int FLD_VILLAGE = 12;
    public static final int FLD_AREA = 13;
    public static final int FLD_TELPHONE = 14;
    public static final int FLD_FAX_NUMBER = 15;
    
    public static final String[] fieldNames = {
        "DEPARTMENT_ID",
        "DEPARTMENT",
        "DESCRIPTION",
        "DIVISION_ID",
        "JOIN_TO_DEPARTMENT_ID",
        "DEPARTMENT_TYPE_ID",
        "ADDRESS",
        "CITY",
        "NPWP",
        "PROVINCE",
        "REGION",
        "SUB_REGION",
        "VILLAGE",
        "AREA",
        "TELPHONE",
        "FAX_NUMBER"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstDepartment() {
    }

    public PstDepartment(int i) throws DBException {
        super(new PstDepartment());
    }

    public PstDepartment(String sOid) throws DBException {
        super(new PstDepartment(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDepartment(long lOid) throws DBException {
        super(new PstDepartment(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_DEPARTMENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDepartment().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Department department = fetchExc(ent.getOID());
        ent = (Entity) department;
        return department.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Department) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Department) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Department fetchExc(long oid) throws DBException {
        try {
            Department department = new Department();
            PstDepartment pstDepartment = new PstDepartment(oid);
            department.setOID(oid);
            department.setDepartment(pstDepartment.getString(FLD_DEPARTMENT));
            department.setDescription(pstDepartment.getString(FLD_DESCRIPTION));
            department.setDivisionId(pstDepartment.getlong(FLD_DIVISION_ID));
            department.setJoinToDepartmentId(pstDepartment.getlong(FLD_JOIN_TO_DEPARTMENT_ID));
            department.setDepartmentTypeId(pstDepartment.getlong(FLD_DEPARTMENT_TYPE_ID));
            department.setAddress(pstDepartment.getString(FLD_ADDRESS));
            department.setCity(pstDepartment.getString(FLD_CITY));
            department.setNpwp(pstDepartment.getString(FLD_NPWP));
            department.setProvince(pstDepartment.getString(FLD_PROVINCE));
            department.setRegion(pstDepartment.getString(FLD_REGION));
            department.setSubRegion(pstDepartment.getString(FLD_SUB_REGION));
            department.setVillage(pstDepartment.getString(FLD_VILLAGE));
            department.setArea(pstDepartment.getString(FLD_AREA));
            department.setTelphone(pstDepartment.getString(FLD_TELPHONE));
            department.setFaxNumber(pstDepartment.getString(FLD_FAX_NUMBER));//
            return department;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartment(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Department department) throws DBException {
        try {
            PstDepartment pstDepartment = new PstDepartment(0);

            pstDepartment.setString(FLD_DEPARTMENT, department.getDepartment());
            pstDepartment.setString(FLD_DESCRIPTION, department.getDescription());
            pstDepartment.setLong(FLD_DIVISION_ID, department.getDivisionId());
            pstDepartment.setLong(FLD_JOIN_TO_DEPARTMENT_ID, department.getJoinToDepartmentId());
            pstDepartment.setLong(FLD_DEPARTMENT_TYPE_ID, department.getDepartmentTypeId());
            pstDepartment.setString(FLD_ADDRESS, department.getAddress());
            pstDepartment.setString(FLD_CITY, department.getCity());
            pstDepartment.setString(FLD_NPWP, department.getNpwp());
            pstDepartment.setString(FLD_PROVINCE, department.getProvince());
            pstDepartment.setString(FLD_REGION, department.getRegion());
            pstDepartment.setString(FLD_SUB_REGION, department.getSubRegion());
            pstDepartment.setString(FLD_VILLAGE, department.getVillage());
            pstDepartment.setString(FLD_AREA, department.getArea());
            pstDepartment.setString(FLD_TELPHONE, department.getTelphone());
            pstDepartment.setString(FLD_FAX_NUMBER, department.getFaxNumber());
            
            pstDepartment.insert();
            department.setOID(pstDepartment.getlong(FLD_DEPARTMENT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartment(0), DBException.UNKNOWN);
        }
        return department.getOID();
    }

    public static long updateExc(Department department) throws DBException {
        try {
            if (department.getOID() != 0) {
                PstDepartment pstDepartment = new PstDepartment(department.getOID());

                pstDepartment.setString(FLD_DEPARTMENT, department.getDepartment());
                pstDepartment.setString(FLD_DESCRIPTION, department.getDescription());
                pstDepartment.setLong(FLD_DIVISION_ID, department.getDivisionId());
                pstDepartment.setLong(FLD_JOIN_TO_DEPARTMENT_ID, department.getJoinToDepartmentId());
                pstDepartment.setLong(FLD_DEPARTMENT_TYPE_ID, department.getDepartmentTypeId());
                pstDepartment.setString(FLD_ADDRESS, department.getAddress());
                pstDepartment.setString(FLD_CITY, department.getCity());
                pstDepartment.setString(FLD_NPWP, department.getNpwp());
                pstDepartment.setString(FLD_PROVINCE, department.getProvince());
                pstDepartment.setString(FLD_REGION, department.getRegion());
                pstDepartment.setString(FLD_SUB_REGION, department.getSubRegion());
                pstDepartment.setString(FLD_VILLAGE, department.getVillage());
                pstDepartment.setString(FLD_AREA, department.getArea());
                pstDepartment.setString(FLD_TELPHONE, department.getTelphone());
                pstDepartment.setString(FLD_FAX_NUMBER, department.getFaxNumber());
                pstDepartment.update();
                return department.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartment(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstDepartment pstDepartment = new PstDepartment(oid);
            pstDepartment.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartment(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String joinSQL, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + TBL_HR_DEPARTMENT + ".*,j." + fieldNames[FLD_DEPARTMENT] + " AS JOIN_DEPT FROM "
                    + TBL_HR_DEPARTMENT + " LEFT JOIN "
                    + TBL_HR_DEPARTMENT + " j on j." + fieldNames[FLD_DEPARTMENT_ID] + "=" + TBL_HR_DEPARTMENT + "."
                    + fieldNames[FLD_JOIN_TO_DEPARTMENT_ID];
            if (joinSQL != null && joinSQL.length() > 0) {
                sql = sql + " " + joinSQL;
            }

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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                department.setJoinToDepartment(rs.getString("JOIN_DEPT"));
                lists.add(department);
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
    
    public static Hashtable<String, Department> listMap(int limitStart, int recordToGet, String joinSQL, String whereClause, String order) {
        Hashtable<String, Department> lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + TBL_HR_DEPARTMENT + ".*,j." + fieldNames[FLD_DEPARTMENT] + " AS JOIN_DEPT FROM "
                    + TBL_HR_DEPARTMENT + " LEFT JOIN "
                    + TBL_HR_DEPARTMENT + " j on j." + fieldNames[FLD_DEPARTMENT_ID] + "=" + TBL_HR_DEPARTMENT + "."
                    + fieldNames[FLD_JOIN_TO_DEPARTMENT_ID];
            if (joinSQL != null && joinSQL.length() > 0) {
                sql = sql + " " + joinSQL;
            }

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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                department.setJoinToDepartment(rs.getString("JOIN_DEPT"));
                lists.put(""+department.getOID(),department);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable<String, Department>();
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String untukTest = "";
        try {
            String sql = "SELECT " + TBL_HR_DEPARTMENT + ".*,j." + fieldNames[FLD_DEPARTMENT] + " AS JOIN_DEPT FROM "
                    + TBL_HR_DEPARTMENT + " LEFT JOIN "
                    + TBL_HR_DEPARTMENT + " j on j." + fieldNames[FLD_DEPARTMENT_ID] + "=" + TBL_HR_DEPARTMENT + "."
                    + fieldNames[FLD_JOIN_TO_DEPARTMENT_ID];
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
            dbrs = DBHandler.execQueryResult(sql);
            untukTest = sql;
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                department.setJoinToDepartment(rs.getString("JOIN_DEPT"));
                lists.add(department);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("Exception Deopartement: " + e.toString());
            System.out.println("exc" + untukTest);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    /**
     * @author : Hendra Putu
     * @date : 2015-02-17
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector listDepartmentVer1(int limitStart, int recordToGet, String companyId, String divisionId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String untukTest = "";
        try {
            String sql  = "SELECT hr_department.DEPARTMENT_ID, hr_department.DEPARTMENT FROM hr_department ";
                   sql += "INNER JOIN hr_division ON hr_department.DIVISION_ID=hr_division.DIVISION_ID ";
                   sql += "INNER JOIN pay_general ON hr_division.COMPANY_ID=pay_general.GEN_ID ";
                   sql += "WHERE hr_division.COMPANY_ID="+companyId+" AND hr_division.DIVISION_ID="+divisionId;

            dbrs = DBHandler.execQueryResult(sql);
            untukTest = sql;
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                lists.add(department);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("Exception Deopartement: " + e.toString());
            System.out.println("exc" + untukTest);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listWithJointToDep(int limitStart, int recordToGet, String joinSQL, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + TBL_HR_DEPARTMENT + ".*,j." + fieldNames[FLD_DEPARTMENT] + " AS JOIN_DEPT FROM "
                    + TBL_HR_DEPARTMENT + " LEFT JOIN "
                    + TBL_HR_DEPARTMENT + " j on j." + fieldNames[FLD_DEPARTMENT_ID] + "=" + TBL_HR_DEPARTMENT + "."
                    + fieldNames[FLD_JOIN_TO_DEPARTMENT_ID];
            if (joinSQL != null && joinSQL.length() > 0) {
                sql = sql + " " + joinSQL;
            }

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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                department.setJoinToDepartment(rs.getString("JOIN_DEPT"));
                lists.add(department);
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

    public static Vector listWithJointToDep(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + TBL_HR_DEPARTMENT + ".*,j." + fieldNames[FLD_DEPARTMENT] + " AS JOIN_DEPT FROM "
                    + TBL_HR_DEPARTMENT + " LEFT JOIN "
                    + TBL_HR_DEPARTMENT + " j on j." + fieldNames[FLD_DEPARTMENT_ID] + "=" + TBL_HR_DEPARTMENT + "."
                    + fieldNames[FLD_JOIN_TO_DEPARTMENT_ID];
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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                department.setJoinToDepartment(rs.getString("JOIN_DEPT"));
                lists.add(department);
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

    
    /**
     * list Department hashtable
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @return 
     */
     public static Hashtable hashListDepartment(int limitStart, int recordToGet, String whereClause) {
        DBResultSet dbrs = null;
        Hashtable hashListDepartment= new Hashtable();
        try {
            String sql = "SELECT * FROM " + TBL_HR_DEPARTMENT;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
                sql = sql + " ORDER BY " + fieldNames[FLD_DIVISION_ID]+","+fieldNames[FLD_DEPARTMENT]+" ASC ";
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            SessDepartment sessDepartment = new SessDepartment();
            long prevDivisionId=0;
            while (rs.next()) {
                
               Department department = new Department();
                long oidDivision = rs.getLong(fieldNames[FLD_DIVISION_ID]);
                if(prevDivisionId!=oidDivision){
                      sessDepartment = new SessDepartment();
                    hashListDepartment.put(oidDivision, sessDepartment);                  
                }
                
                 resultToObject(rs, department);
                prevDivisionId=department.getDivisionId();
                sessDepartment.addObjDepartment(department);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashListDepartment;
        }
    }
    
     /**
     * list Department hashtable
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @return 
     */
     public static Hashtable hashListDepartment2(int limitStart, int recordToGet, String whereClause) {
        DBResultSet dbrs = null;
        Hashtable hashListDepartment= new Hashtable();
        try {
            String sql = "SELECT * FROM " + TBL_HR_DEPARTMENT;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
                sql = sql + " ORDER BY " + fieldNames[FLD_DIVISION_ID]+","+fieldNames[FLD_DEPARTMENT]+" ASC ";
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            long prevDivisionId=0;
            while (rs.next()) {
                
               Department department = new Department();
                resultToObject(rs, department);
                    hashListDepartment.put(department.getDivisionId(), department);                  
                
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashListDepartment;
        }
    }
     
     
     /**
     * listDepartment by division create by satrya 2013-04-20
     *
     * @param limitStart
     * @param recordToGet
     * @param where
     * @param order
     * @return
     */
    public static Vector listDepartemntByDiv(int limitStart, int recordToGet, String where, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT dept.*"
                    //+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+",divX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    //+ ",dept."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+",dept."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] +
                    + " FROM " + PstDivision.TBL_HR_DIVISION + " AS DIVX "
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT ON DIVX." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "=dept." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID];
            if (where != null && where.length() > 0) {
                sql = sql + " WHERE " + where;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                lists.add(department);
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

    /**
     * List Departemnt create by satrya 2013-04-20
     *
     * @param limitStart
     * @param recordToGet
     * @param where
     * @param order
     * @return
     */
    public static Vector listDepartment(int limitStart, int recordToGet, String where, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT DEPT.*"
                    //+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+",divX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    //+ ",dept."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+",dept."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] +
                    + " FROM " + PstDivision.TBL_HR_DIVISION + " AS DIVX "
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT ON DIVX." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "=dept." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID];
            if (where != null && where.length() > 0) {
                sql = sql + " WHERE " + where;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                lists.add(department);
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
    
     /**
     * Get Division ID with Departemnt create by Priska 2014-12-13
     *
     * @param limitStart
     * @param recordToGet
     * @param where
     * @param order
     * @return
     */
    public static long getDivisionIdWheredepId(int limitStart, int recordToGet, String where, String order) {
       long  divId = 0;
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]
                    + " FROM " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT ";
            if (where != null && where.length() > 0) {
                sql = sql + " WHERE " + where;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                
                divId = rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]);
            }
            rs.close();
            return divId;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public static Vector listWithCompanyDiv(int limitStart, int recordToGet, String whereClause) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String order = PstCompany.fieldNames[PstCompany.FLD_COMPANY] + "," + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                + "," + fieldNames[FLD_DEPARTMENT];
        try {
            String sql = "SELECT DISTINCT(d." + fieldNames[FLD_DEPARTMENT_ID] + ")"
                    + ",d." + fieldNames[FLD_DEPARTMENT]
                    + ",d." + fieldNames[FLD_DIVISION_ID]
                    + ",d." + fieldNames[FLD_DESCRIPTION]
                    + ", c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ", v." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    //update by satrya 2013-09-16
                    + ", c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " FROM " + TBL_HR_DEPARTMENT + " d "
                    + " LEFT JOIN " + PstDivision.TBL_HR_DIVISION + " v "
                    + "  ON d." + fieldNames[FLD_DIVISION_ID] + " = " + " v." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " LEFT JOIN " + PstCompany.TBL_HR_COMPANY + " c "
                    + "  ON c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "=v." + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID];

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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                department.setCompany("" + rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                department.setDivision("" + rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                //update by satrya 2013-09-16
                department.setCompanyId(rs.getLong(PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]));
                lists.add(department);
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

    public static Vector listWithCompanyDivSection(int limitStart, int recordToGet, String whereClause) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String order = PstCompany.fieldNames[PstCompany.FLD_COMPANY] + "," + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                + ",d." + fieldNames[FLD_DEPARTMENT];
        try {
            String sql = "SELECT DISTINCT(d." + fieldNames[FLD_DEPARTMENT_ID] + ")"
                    + ",d." + fieldNames[FLD_DEPARTMENT]
                    + ",d." + fieldNames[FLD_DIVISION_ID]
                    + ",d." + fieldNames[FLD_DESCRIPTION]
                    + ", c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ", v." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    //update by satrya 2013-09-16
                    + ", c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + ", sec." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", sec." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " FROM " + TBL_HR_DEPARTMENT + " d "
                    + " LEFT JOIN " + PstDivision.TBL_HR_DIVISION + " v "
                    + "  ON d." + fieldNames[FLD_DIVISION_ID] + " = " + " v." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " LEFT JOIN " + PstCompany.TBL_HR_COMPANY + " c "
                    + "  ON c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "=v." + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]
                    //update by satrya 2013-09-20
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " sec   ON sec." + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]
                    + " =d." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];

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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                department.setCompany("" + rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                department.setDivision("" + rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                //update by satrya 2013-09-16
                department.setCompanyId(rs.getLong(PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]));

                department.setSectionId(rs.getLong(PstSection.fieldNames[PstSection.FLD_SECTION_ID]));
                department.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                lists.add(department);
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

    public static DepartmentIDnNameList genDepIDandNameWithCompanyDiv(int limitStart, int recordToGet, String whereClause, boolean withSelect) {
        DepartmentIDnNameList dbList = new DepartmentIDnNameList();
        Vector department_value = new Vector(1, 1);
        Vector department_key = new Vector(1, 1);
        if (withSelect) {
            department_value.add("0");
            department_key.add("select ...");
        }

        Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereClause);
        //Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
        String prevCompany = "";
        String prevDivision = "";

        for (int i = 0; i < listCostDept.size(); i++) {
            Department dept = (Department) listCostDept.get(i);
            if (prevCompany.equals(dept.getCompany())) {
                if (prevDivision.equals(dept.getDivision())) {
                    department_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment());
                    department_value.add(String.valueOf(dept.getOID()));
                } else {
                    department_key.add("&nbsp;-" + dept.getDivision() + "-");
                    department_value.add("-2");
                    department_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment());
                    department_value.add(String.valueOf(dept.getOID()));
                    prevDivision = dept.getDivision();
                }
            } else {
                department_key.add("-" + dept.getCompany() + "-");
                department_value.add("-1");
                department_key.add("&nbsp;-" + dept.getDivision() + "-");
                department_value.add("-2");
                department_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment());
                department_value.add(String.valueOf(dept.getOID()));
                prevCompany = dept.getCompany();
                prevDivision = dept.getDivision();
            }
        }
        dbList.setDepIDs(department_value);
        dbList.setDepNames(department_key);
        return dbList;
    }

    public static void resultToObject(ResultSet rs, Department department) {

        try {
            department.setOID(rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]));
            department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
            department.setDescription(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DESCRIPTION]));
            department.setDivisionId(rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]));
            department.setJoinToDepartmentId(rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID]));
            department.setDepartmentTypeId(rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_TYPE_ID]));
            department.setAddress(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_ADDRESS]));
            department.setCity(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_CITY]));
            department.setNpwp(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_NPWP]));
            department.setProvince(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_PROVINCE]));
            department.setRegion(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_REGION]));
            department.setSubRegion(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_SUB_REGION]));
            department.setVillage(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_VILLAGE]));
            department.setArea(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_AREA]));
            department.setTelphone(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_TELPHONE]));
            department.setFaxNumber(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_FAX_NUMBER]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long departmentId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DEPARTMENT + " WHERE "
                    + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + ") FROM " + TBL_HR_DEPARTMENT;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    

    public static int getCount(String joinSQL, String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + ") FROM " + TBL_HR_DEPARTMENT;
            if (joinSQL != null && joinSQL.length() > 0) {
                sql = sql + " " + joinSQL;
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String order) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    Department department = (Department) list.get(ls);
                    if (oid == department.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }

        return start;
    }

    public static boolean checkMaster(long oid) {
        if (PstEmployee.checkDepartment(oid)) {
            return true;
        } else {
            if (PstCareerPath.checkDepartment(oid)) {
                return true;
            } else {
                if (PstSection.checkDepartment(oid)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }

        return cmd;
    }

    //Gede_1Maret2012{
    //untuk structure employee
    public static Vector list2(int limitStart, int recordToGet, String whereClauseDep, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT dep.* FROM " + PstDepartment.TBL_HR_DEPARTMENT
                    + " dep INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                    + " emp ON dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION
                    + " pos ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];

            if (whereClauseDep != null && whereClauseDep.length() > 0) {
                sql = sql + " WHERE " + whereClauseDep;
            }
            sql = sql + " GROUP BY " + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]; // add by kartika 2014-02-02
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql Department " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                lists.add(department);
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
    //}

    //Gede_2April2012
    //{
    public static Vector list3(int limitStart, int recordToGet, String whereClauseDep, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT dep.* FROM " + PstDepartment.TBL_HR_DEPARTMENT
                    + " as dep INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " as emp ON dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "= emp."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " as d ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "= d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " as c ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + "= c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " as r ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + "= r." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " as odt ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " as o ON odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + "= o." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]
                    + //update by satrya 2013-08-13
                    " LEFT JOIN " + PstSection.TBL_HR_SECTION + " as sec ON sec." + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "= dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
            if (whereClauseDep != null && whereClauseDep.length() > 0) {
                sql = sql + " WHERE " + whereClauseDep;
            }
            sql = sql + " GROUP BY dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql Department " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Department department = new Department();
                resultToObject(rs, department);
                lists.add(department);
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
    //}
    //update by satrya 2012-10-17

    /**
     * Keterangan : mencari listSTruktur EMployee
     *
     * @param whereClause
     * @return
     */
    public static Vector listStruktur(String empNum, String fullName, long oidDepartement, long oidSection, long empId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT HE." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ",HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ",HC." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ",HD." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ",HDIV." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ",HS." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS HE "
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " AS HC ON(HE." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = HC."
                    + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + ")"
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT
                    + " AS HD ON (HE." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = HD." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + ")"
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " AS HDIV ON (HE."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = HDIV."
                    + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + ")"
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS HS ON (HE."
                    + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = HS." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + ")"
                    + "WHERE (1=1)";

            String whereClause = "";
            if (empId != 0) {
                whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " = \"" + empId + "\" AND ";
            }
            if (fullName != null && fullName.length() > 0) {
                whereClause = whereClause + " HE. " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + " LIKE " + "\"%" + fullName.trim() + "%\" AND";
            }
            if (empNum != null && empNum.length() > 0) {
                whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + " = \"" + empNum + "\" AND ";
            }
            if (oidDepartement != 0) {
                whereClause = whereClause + " HD." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                        + " = \"" + oidDepartement + "\" AND ";
            }
            if (oidSection != 0) {
                whereClause = whereClause + " HS." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " = \"" + oidSection + "\" AND ";
            }
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " 1 = 1 ";
                sql = sql + " AND " + whereClause;
            }



            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                StrukturEmployee strukturEmployee = new StrukturEmployee();
                strukturEmployee.setEmpNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                strukturEmployee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                strukturEmployee.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                strukturEmployee.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                strukturEmployee.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                strukturEmployee.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                strukturEmployee.setEmpId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                lists.add(strukturEmployee);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("Exception PstDepartment STruktur" + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listStruktur(String empNum, String fullName, long oidDepartement, long oidSection) {
        return listStruktur(empNum, fullName, oidDepartement, oidSection, 0);
    }

    public static long getOidDept(String empNum, String fullName, long oidDepartement, long oidSection, long empId) {
        long oidDept = 0;
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT HD." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS HE "
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT
                    + " AS HD ON (HE." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = HD." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + ")"
                    + "WHERE (1=1)";

            String whereClause = "";
            if (empId != 0) {
                whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " = \"" + empId + "\" AND ";
            }
            if (fullName != null && fullName.length() > 0) {
                whereClause = whereClause + " HE. " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + " LIKE " + "\"%" + fullName.trim() + "%\" AND";
            }
            if (empNum != null && empNum.length() > 0) {
                whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + " = \"" + empNum + "\" AND ";
            }
            if (oidDepartement != 0) {
                whereClause = whereClause + " HD." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                        + " = \"" + oidDepartement + "\" AND ";
            }
            if (oidSection != 0) {
                whereClause = whereClause + " HS." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " = \"" + oidSection + "\" AND ";
            }
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " 1 = 1 ";
                sql = sql + " AND " + whereClause;
            }



            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                oidDept = rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            }
            rs.close();


        } catch (Exception e) {
            System.out.println("Exception PstDepartment STruktur" + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return oidDept;
    }
/**
 * List Department Bertingkat
 * create by satrya 2014-06-13
 * @param oidEmployee
 * @param processDependOnUserDept
 * @return 
 */
    public static Vector listDepartment(long oidEmployee,boolean processDependOnUserDept,boolean isGeneralManager) {
        Vector dept_value = new Vector(1, 1);
        Vector dept_key = new Vector(1, 1);
        //Vector listDept = new Vector(1, 1);
        DepartmentIDnNameList keyList = new DepartmentIDnNameList();
        Employee employee = new Employee();
        if (processDependOnUserDept) {
            if (oidEmployee > 0) {
                long oidPosition = 0;
                try {
                    oidPosition = PstEmployee.getPositionId(oidEmployee);
                    employee = PstEmployee.fetchExc(oidEmployee);
                } catch (Exception exc) {
                }
                if (isGeneralManager) {
                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                    //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                } else {
                    Position position = null;
                    try {
                        position = PstPosition.fetchExc(oidPosition);
                    } catch (Exception exc) {
                    }
                    if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                        String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + employee.getDivisionId() + "";
                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                    } else {

                        String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + employee.getDepartmentId()
                                + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + employee.getDepartmentId() + ") ";
                        try {
                            String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                            Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                            int grpIdx = -1;
                            int maxGrp = depGroup == null ? 0 : depGroup.size();
                            int countIdx = 0;
                            int MAX_LOOP = 10;
                            int curr_loop = 0;
                            do { // find group department belonging to curretn user base in employee.getDepartmentId()
                                curr_loop++;
                                String[] grp = (String[]) depGroup.get(countIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    if (comp.trim().compareToIgnoreCase("" + employee.getDepartmentId()) == 0) {
                                        grpIdx = countIdx;   // A ha .. found here 
                                    }
                                }
                                countIdx++;
                            } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                            // compose where clause
                            if (grpIdx >= 0) {
                                String[] grp = (String[]) depGroup.get(grpIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                }
                            }
                        } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);

                        }
                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                    }
                }
            } else {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
            }
        } else {
            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
        }
        dept_value = keyList.getDepIDs();
        dept_key = keyList.getDepNames();
        Vector listDepartment = new Vector();
        listDepartment.add(dept_value);
        listDepartment.add(dept_key);
        return listDepartment;

    }
}
