/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */
/*******************************************************************
 * Class Description 	: PstCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Wiweka
 */
/* package java */
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstCompany extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_COMPANY = "pay_general";//"hr_company";
    public static final int FLD_COMPANY_ID = 0;
    public static final int FLD_COMPANY = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static final int FLD_CODE_COMPANY = 3;
    public static final int FLD_COMPANY_PARENTS_ID = 4;
    public static final String[] fieldNames = {
        "GEN_ID", //"COMPANY_ID",
        "COMPANY", //"COMPANY",
        "COMP_ADDRESS", //"DESCRIPTION"
        "CODE_COMPANY",
        "COMPANY_PARENTS_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };

    public PstCompany() {
    }

    public PstCompany(int i) throws DBException {
        super(new PstCompany());
    }

    public PstCompany(String sOid) throws DBException {
        super(new PstCompany(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCompany(long lOid) throws DBException {
        super(new PstCompany(0));
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
        return TBL_HR_COMPANY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCompany().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Company company = fetchExc(ent.getOID());
        ent = (Entity) company;
        return company.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Company) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Company) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Company fetchExc(long oid) throws DBException {
        try {
            Company company = new Company();
            PstCompany pstCompany = new PstCompany(oid);
            company.setOID(oid);

            company.setCompany(pstCompany.getString(FLD_COMPANY));
            company.setDescription(pstCompany.getString(FLD_DESCRIPTION));
            company.setCodeCompany(pstCompany.getString(FLD_CODE_COMPANY));
            company.setCompanyParentsId(pstCompany.getlong(FLD_COMPANY_PARENTS_ID));
            return company;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompany(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Company company) throws DBException {
        try {
            PstCompany pstCompany = new PstCompany(0);

            pstCompany.setString(FLD_COMPANY, company.getCompany());
            pstCompany.setString(FLD_DESCRIPTION, company.getDescription());
            pstCompany.setString(FLD_CODE_COMPANY, company.getCodeCompany());
            pstCompany.setLong(FLD_COMPANY_PARENTS_ID, company.getCompanyParentsId());
            pstCompany.insert();
            company.setOID(pstCompany.getlong(FLD_COMPANY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompany(0), DBException.UNKNOWN);
        }
        return company.getOID();
    }

    public static long updateExc(Company company) throws DBException {
        try {
            if (company.getOID() != 0) {
                PstCompany pstCompany = new PstCompany(company.getOID());

                pstCompany.setString(FLD_COMPANY, company.getCompany());
                pstCompany.setString(FLD_DESCRIPTION, company.getDescription());
                pstCompany.setString(FLD_CODE_COMPANY, company.getCodeCompany());
                pstCompany.setLong(FLD_COMPANY_PARENTS_ID, company.getCompanyParentsId());
                pstCompany.update();
                return company.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompany(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstCompany pstCompany = new PstCompany(oid);
            pstCompany.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompany(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_COMPANY;
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
                Company company = new Company();
                resultToObject(rs, company);
                lists.add(company);
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
 * List Company by division
 * create by satrya 2013-04-20
 * @param limitStart
 * @param recordToGet
 * @param whereClause
 * @param order
 * @return 
 */
public static Vector listCompanyByDivisionId(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT COMP.* FROM " +PstCompany.TBL_HR_COMPANY + " AS COMP "  
        + " INNER JOIN "+PstDivision.TBL_HR_DIVISION + " AS DIVX ON DIVX."
        +PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]
        +"=COMP."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID];
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
                Company company = new Company();
                resultToObject(rs, company);
                lists.add(company);
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
 * create by satrya 2013-09-11
 * @param limitStart
 * @param recordToGet
 * @param whereClause
 * @param order
 * @return 
 */
public static Vector listCompanyByDivisionIdAndDepartMent(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        try {
            String sql = " SELECT COMP.* FROM " +PstCompany.TBL_HR_COMPANY + " AS COMP "  
        + " INNER JOIN "+PstDivision.TBL_HR_DIVISION 
        + " AS DIVX ON DIVX."+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]
        +"=COMP."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
        +" INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT ON DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]
        +"=DIVX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID];
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            sql = sql + " GROUP BY " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID];
            
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
                Company company = new Company();
                resultToObject(rs, company);
                lists.add(company);
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
 * List Companty berdasarkan departementID
 * create by satrya 2013-04-20
 * @param limitStart
 * @param recordToGet
 * @param whereClause
 * @param order
 * @return 
 */
public static Vector listCompanyByDeptId(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT COMP.* FROM "+PstDivision.TBL_HR_DIVISION + " AS DIVX  "
 + " INNER JOIN "+PstCompany.TBL_HR_COMPANY + " AS COMP ON DIVX."+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+ " =COMP."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] 
 + " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT ON DIVX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" =dept."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID];
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
                Company company = new Company();
                resultToObject(rs, company);
                lists.add(company);
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

    
    public static void resultToObject(ResultSet rs, Company company) {
        try {
            company.setOID(rs.getLong(PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]));
            company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
            company.setDescription(rs.getString(PstCompany.fieldNames[PstCompany.FLD_DESCRIPTION]));
            company.setCodeCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_CODE_COMPANY]));
            company.setCompanyParentsId(rs.getLong(PstCompany.fieldNames[PstCompany.FLD_COMPANY_PARENTS_ID]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long companyId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_COMPANY + " WHERE "
                    + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " = " + companyId;

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
            String sql = "SELECT COUNT(" + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + ") FROM " + TBL_HR_COMPANY;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    Company company = (Company) list.get(ls);
                    if (oid == company.getOID()) {
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
        if (PstEmployee.checkCompany(oid)) {
            return true;
        } else {
            if (PstCareerPath.checkCompany(oid)) {
                return true;
            } else {
                return false;
            }

        }
    }

    //Gede_21MARET2012
    public static long getId(String whereClause) {
        DBResultSet dbrs = null;
        long result = 0;
        try {
            String sql = "SELECT c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " FROM " + PstDepartment.TBL_HR_DEPARTMENT + " dep INNER JOIN "
                    + PstDivision.TBL_HR_DIVISION + " d ON dep." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " c ON d." + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID];

            sql = sql + whereClause;

            System.out.println("SQL " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println("exception " + e);
            return 0;

        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    //}
    //Gede_27Maret2012
    //nama company{
    public static String getCompanyName(long oidCompany) {
        String result = "";
        try {
            PstCompany pstCompany = new PstCompany();
            Company company = pstCompany.fetchExc(oidCompany);
            return company.getCompany();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }
    //}
}
