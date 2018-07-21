package com.dimata.harisma.entity.employee;

// import java
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

// import qdep
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.session.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.common.entity.contact.*;
//}

// import harisma
import com.dimata.harisma.entity.employee.EmpAward;
import java.text.SimpleDateFormat;

/**
 *
 * @author guest
 */
public class PstEmpAward extends DBHandler implements I_Language, I_PersintentExc, I_DBInterface, I_DBType {

    public static final String TBL_AWARD = "hr_award";
    public static final int FLD_AWARD_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_DEPARTMENT_ID = 2;
    public static final int FLD_SECTION_ID = 3;
    public static final int FLD_AWARD_DATE = 4;
    public static final int FLD_AWARD_TYPE = 5;
    public static final int FLD_AWARD_DESC = 6;
    public static final int FLD_PROVIDER_ID = 7;
    public static final int FLD_TITLE = 8;
    public static final int FLD_DIVISION_ID = 9;
    public static final int FLD_COMPANY_ID = 10;
    public static final int FLD_DOCUMENT = 11;
    public static final int FLD_DOC_ID = 12;
    public static final int FLD_ACKNOWLEDGE_STATUS = 13;
    public static final int FLD_EXTERNAL_FROM = 14;
    public static String[] fieldNames = {
        "AWARD_ID",
        "EMP_ID",
        "DEPT_ID",
        "SECT_ID",
        "DATE",
        "TYPE",
        "DESCRIPTION",
        "PROVIDER_ID",
        "TITLE",
        "DIVISION_ID",
        "COMPANY_ID",
        "DOCUMENT",
        "DOC_ID",
        "ACKNOWLEDGE_STATUS",
        "EXTERNAL_FROM"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING
    };

    public PstEmpAward() {
    }

    public PstEmpAward(int i) throws DBException {
        super(new PstEmpAward());
    }

    public PstEmpAward(long lOid) throws DBException {
        super(new PstEmpAward(0));
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

    public PstEmpAward(String sOid) throws DBException {
        super(new PstEmpAward(0));

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
        return TBL_AWARD;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return this.getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpAward award = fetchExc(ent.getOID());
        ent = (Entity) award;
        return award.getOID();
    }

    public long updateExc(Entity ent) throws Exception {
        return insertExc((EmpAward) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        return updateExc((EmpAward) ent);
    }

    public long insertExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpAward fetchExc(long oid) throws DBException {
        try {
            EmpAward award = new EmpAward();
            PstEmpAward pstAward = new PstEmpAward(oid);

            award.setOID(pstAward.getlong(FLD_AWARD_ID));
            award.setEmployeeId(pstAward.getlong(FLD_EMPLOYEE_ID));
            award.setProviderId(pstAward.getlong(FLD_PROVIDER_ID));
            award.setDepartmentId(pstAward.getlong(FLD_DEPARTMENT_ID));
            award.setSectionId(pstAward.getlong(FLD_SECTION_ID));
            award.setAwardDate(pstAward.getDate(FLD_AWARD_DATE));
            award.setAwardType(pstAward.getlong(FLD_AWARD_TYPE));
            award.setAwardDescription(pstAward.getString(FLD_AWARD_DESC));
            award.setTitle(pstAward.getString(FLD_TITLE));
            award.setDivisionId(pstAward.getlong(FLD_DIVISION_ID));
            award.setCompanyId(pstAward.getlong(FLD_COMPANY_ID));
            award.setDocument(pstAward.getString(FLD_DOCUMENT));
            award.setDocId(pstAward.getlong(FLD_DOC_ID));
            award.setAcknowledgeStatus(pstAward.getInt(FLD_ACKNOWLEDGE_STATUS));
            award.setExternalFrom(pstAward.getString(FLD_EXTERNAL_FROM));

            return award;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAward(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpAward award) throws DBException {
        try {
            PstEmpAward pstAward = new PstEmpAward(0);

            pstAward.setLong(FLD_EMPLOYEE_ID, award.getEmployeeId());
            pstAward.setLong(FLD_DEPARTMENT_ID, award.getDepartmentId());
            pstAward.setLong(FLD_PROVIDER_ID, award.getProviderId());
            pstAward.setLong(FLD_SECTION_ID, award.getSectionId());
            pstAward.setDate(FLD_AWARD_DATE, award.getAwardDate());
            pstAward.setLong(FLD_AWARD_TYPE, award.getAwardType());
            pstAward.setString(FLD_AWARD_DESC, award.getAwardDescription());
            pstAward.setString(FLD_TITLE, award.getTitle());
            pstAward.setLong(FLD_DIVISION_ID, award.getDivisionId());
            pstAward.setLong(FLD_COMPANY_ID, award.getCompanyId());
            pstAward.setString(FLD_DOCUMENT, award.getDocument());
            pstAward.setLong(FLD_DOC_ID, award.getDocId());
            pstAward.setInt(FLD_ACKNOWLEDGE_STATUS, award.getAcknowledgeStatus());
            pstAward.setString(FLD_EXTERNAL_FROM, award.getExternalFrom());
            pstAward.insert();

            award.setOID(pstAward.getlong(FLD_AWARD_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAward(0), DBException.UNKNOWN);
        }

        return award.getOID();
    }

    public static long updateExc(EmpAward award) throws DBException {
        try {
            if (award.getOID() != 0) {
                PstEmpAward pstAward = new PstEmpAward(award.getOID());

                pstAward.setLong(FLD_EMPLOYEE_ID, award.getEmployeeId());
                pstAward.setLong(FLD_PROVIDER_ID, award.getProviderId());
                pstAward.setLong(FLD_DEPARTMENT_ID, award.getDepartmentId());
                pstAward.setLong(FLD_SECTION_ID, award.getSectionId());
                pstAward.setDate(FLD_AWARD_DATE, award.getAwardDate());
                pstAward.setLong(FLD_AWARD_TYPE, award.getAwardType());
                pstAward.setString(FLD_AWARD_DESC, award.getAwardDescription());
                pstAward.setString(FLD_TITLE, award.getTitle());
                pstAward.setLong(FLD_DIVISION_ID, award.getDivisionId());
                pstAward.setLong(FLD_COMPANY_ID, award.getCompanyId());
                pstAward.setString(FLD_DOCUMENT, award.getDocument());
                pstAward.setLong(FLD_DOC_ID, award.getDocId());
                pstAward.setInt(FLD_ACKNOWLEDGE_STATUS, award.getAcknowledgeStatus());
                pstAward.setString(FLD_EXTERNAL_FROM, award.getExternalFrom());
                pstAward.update();

                return award.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAward(0), DBException.UNKNOWN);
        }

        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpAward pstEmpWarning = new PstEmpAward(oid);
            pstEmpWarning.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAward(0), DBException.UNKNOWN);
        }

        return oid;
    }

    public static Vector listAll() {
        return list(0, 0, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT * FROM " + TBL_AWARD;
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
                EmpAward award = new EmpAward();
                resultToObject(rs, award);
                lists.add(award);
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

    public static void resultToObject(ResultSet rs, EmpAward award) {
        try {
            award.setOID(rs.getLong(PstEmpAward.fieldNames[FLD_AWARD_ID]));
            award.setEmployeeId(rs.getLong(PstEmpAward.fieldNames[FLD_EMPLOYEE_ID]));
            award.setProviderId(rs.getLong(PstEmpAward.fieldNames[FLD_PROVIDER_ID]));
            award.setDepartmentId(rs.getLong(PstEmpAward.fieldNames[FLD_DEPARTMENT_ID]));
            award.setSectionId(rs.getLong(PstEmpAward.fieldNames[FLD_SECTION_ID]));
            award.setAwardDate(rs.getDate(PstEmpAward.fieldNames[FLD_AWARD_DATE]));
            award.setAwardType(rs.getLong(PstEmpAward.fieldNames[FLD_AWARD_TYPE]));
            award.setAwardDescription(rs.getString(PstEmpAward.fieldNames[FLD_AWARD_DESC]));
            award.setTitle(rs.getString(PstEmpAward.fieldNames[FLD_TITLE]));
            award.setDivisionId(rs.getLong(PstEmpAward.fieldNames[FLD_DIVISION_ID]));
            award.setCompanyId(rs.getLong(PstEmpAward.fieldNames[FLD_COMPANY_ID]));
            award.setDocument(rs.getString(PstEmpAward.fieldNames[FLD_DOCUMENT]));
            award.setDocId(rs.getLong(PstEmpAward.fieldNames[FLD_DOC_ID]));
            award.setAcknowledgeStatus(rs.getInt(PstEmpAward.fieldNames[FLD_ACKNOWLEDGE_STATUS]));
            award.setExternalFrom(rs.getString(PstEmpAward.fieldNames[FLD_EXTERNAL_FROM]));
        } catch (Exception e) {
        }
    }
    
    public static void updateFileName(String fileName,long idDetail) {
        try {
            String sql = "UPDATE " + PstEmpAward.TBL_AWARD+
            " SET " + PstEmpAward.fieldNames[FLD_DOCUMENT] + " = '" + fileName +"'"+
            " WHERE " + PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID] +
            " = " + idDetail ;           
            System.out.println("sql PstDataUploadDetail.updateFileName : " + sql);
            int result = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("\tExc updateFileName : " + e.toString());
        } finally {
            //System.out.println("\tFinal updatePresenceStatus");
        }
    }

    public static boolean checkOID(long awardId) {
        DBResultSet dbrs = null;
        boolean result = false;

        try {
            String sql = "SELECT * FROM " + TBL_AWARD + " WHERE "
                    + PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID] + " = '" + awardId + "'";

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
            String sql = "SELECT COUNT(" + PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID]
                    + ") FROM " + TBL_AWARD;

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

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;

        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    EmpAward award = (EmpAward) list.get(ls);
                    if (oid == award.getOID()) {
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

    public static boolean checkAward(long oid) {
        DBResultSet dbrs = null;
        boolean result = false;

        try {
            String sql = "SELECT * FROM " + TBL_AWARD + " WHERE "
                    + PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE] + " = " + oid + "";

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

    //Gede_7Feb2012 {
    //untuk report excel
    public static int getCount2(SrcEmployee srcEmployee) {
        DBResultSet dbrs = null;
        SessEmployee sessEmployee = new SessEmployee();

        try {//SELECT COUNT(a.AWARD_ID) FROM hr_award a INNER JOIN hr_employee e
            //ON a.EMP_ID=e.EMPLOYEE_ID WHERE e.DIVISION_ID=3 GROUP BY a.EMP_ID ORDER BY COUNT(a.AWARD_ID) DESC LIMIT 1

            String sql = "SELECT COUNT(A." + PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID] + ") FROM " + PstEmpAward.TBL_AWARD + " A INNER JOIN "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON A." + PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]) + " WHERE " + sessEmployee.whereList(srcEmployee) + "GROUP BY A." + PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]
                    + " ORDER BY COUNT(A." + PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID] + ") DESC LIMIT 1";

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
    //}
    //Gede_14Feb2012 {
    //Award

    public static String getAward(String level) {
        String awardLevel = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE]
                    + " FROM " + PstAwardType.TBL_AWARD_TYPE + " WHERE " + PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE_ID]
                    + "=" + level;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                awardLevel = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return awardLevel;
    }

    public static String getDepartment(String dept) {
        String department = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " FROM " + PstDepartment.TBL_HR_DEPARTMENT + " WHERE " + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + "=" + dept;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                department = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return department;
    }

    public static String getSection(String sect) {
        String section = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + " FROM " + PstSection.TBL_HR_SECTION + " WHERE " + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + "=" + sect;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                section = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return section;
    }
    
    public static Hashtable fetchExcHashtable(long oid) throws DBException {
        try {
            Hashtable empAward = new Hashtable();
            EmpAward award = new EmpAward();
            PstEmpAward pstEmpAward = new PstEmpAward(oid);
            Employee employee = new Employee();
            
            award.setEmployeeId(pstEmpAward.getLong(FLD_EMPLOYEE_ID));
            empAward.put(PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID], award.getEmployeeId());
            award.setTitle(pstEmpAward.getString(FLD_TITLE));
            empAward.put(PstEmpAward.fieldNames[PstEmpAward.FLD_TITLE], award.getTitle());
            award.setAwardDate(pstEmpAward.getDate(FLD_AWARD_DATE));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            String awardDate = sdf.format(award.getAwardDate());
            empAward.put(PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE], awardDate);
            
            try {
                employee = PstEmployee.fetchExc(award.getEmployeeId());
            } catch (Exception exc){
                
            }
            empAward.put(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME], employee.getFullName());
            return empAward;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAward(0), DBException.UNKNOWN);
        }
    }
    
    public static Vector listDataTable(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT EMP.* FROM "+PstEmpAward.TBL_AWARD+" AS EMP "
                    + "LEFT JOIN "+PstDivision.TBL_HR_DIVISION+" AS DI "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_DIVISION_ID]+" = "
                    + "DI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" "
                    + "LEFT JOIN "+PstContactList.TBL_CONTACT_LIST+" AS CON "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_PROVIDER_ID]+" = "
                    + "CON."+PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+" "
                    + "LEFT JOIN "+PstAwardType.TBL_AWARD_TYPE+" AS TYPE "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE]+" = "
                    + "TYPE."+PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE_ID]+" "
                    + "LEFT JOIN "+PstEmpDoc.TBL_HR_EMP_DOC+" AS DOC "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+" = "
                    + "DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID];
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
                EmpAward award = new EmpAward();
                resultToObject(rs, award);
                lists.add(award);
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
    
    public static int getCountDataTable(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID]+") "
                    + "FROM "+PstEmpAward.TBL_AWARD+" AS EMP "
                    + "LEFT JOIN "+PstDivision.TBL_HR_DIVISION+" AS DI "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_DIVISION_ID]+" = "
                    + "DI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" "
                    + "LEFT JOIN "+PstContactList.TBL_CONTACT_LIST+" AS CON "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_PROVIDER_ID]+" = "
                    + "CON."+PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+" "
                    + "LEFT JOIN "+PstAwardType.TBL_AWARD_TYPE+" AS TYPE "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE]+" = "
                    + "TYPE."+PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE_ID]+" "
                    + "LEFT JOIN "+PstEmpDoc.TBL_HR_EMP_DOC+" AS DOC "
                    + "ON EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+" = "
                    + "DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID];
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
    //}
}
