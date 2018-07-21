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
//}

// import harisma
import com.dimata.harisma.entity.employee.EmpReprimand;
import com.dimata.harisma.report.ReprimandListXLS;
import java.text.SimpleDateFormat;

/**
 *
 * @author guest
 */
public class PstEmpReprimand extends DBHandler implements I_Language, I_PersintentExc, I_DBInterface, I_DBType {

    public static final String TBL_REPRIMAND = "hr_reprimand";
    public static final int FLD_REPRIMAND_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_NUMBER = 2;
    public static final int FLD_CHAPTER = 3;
    public static final int FLD_ARTICLE = 4;
    public static final int FLD_PAGE = 5;
    public static final int FLD_DESCRIPTION = 6;
    public static final int FLD_REP_DATE = 7;
    public static final int FLD_VALIDITY = 8;

    /**
     * Ari_20110909
     * merubah reprimandLevel menjadi reprimandLevelId {
     */
    public static final int FLD_REPRIMAND_LEVEL_ID = 9;
    public static final int FLD_COMPANY_ID = 10;
    public static final int FLD_DIVISION_ID = 11;
    public static final int FLD_DEPARTMENT_ID = 12;
    public static final int FLD_SECTION_ID = 13;
    public static final int FLD_POSITION_ID = 14;
    public static final int FLD_LEVEL_ID = 15;
    public static final int FLD_EMP_CATEGORY_ID = 16;
    public static final int FLD_VERSE = 17;
    public static final int FLD_DOCUMENT = 18;
    public static final int FLD_EMP_DOC_ID = 19;
    public static final int FLD_ACKNOWLEDGE_STATUS = 20;
    public static String[] fieldNames = {
        "REPRIMAND_ID",
        "EMPLOYEE_ID",
        "NUMBER",
        "CHAPTER",
        "ARTICLE",
        "PAGE",
        "DESCRIPTION",
        "REPRIMAND_DATE",
        "VALID_UNTIL",
        "REPRIMAND_LEVEL_ID",
        "COMPANY_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "POSITION_ID",
        "LEVEL_ID",
        "EMP_CATEGORY_ID",
        "VERSE",
        "DOCUMENT",
        "EMP_DOC_ID",
        "ACKNOWLEDGE_STATUS"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT
    };

    /* public static final int REPRIMAND_LEVEL_1            =   0;
    public static final int REPRIMAND_LEVEL_2            =   1;
    public static final int REPRIMAND_LEVEL_3            =   2;
    public static final int REPRIMAND_SUSPENSION         =   3;
    
    public static String[] levelNames = 
    {
    "Reprimand Level 1",
    "Reprimand Level 2",
    "Reprimand Level 3",
    "Suspension"
    };    

    public static Vector getLevelKeys() {
    Vector keys = new Vector();

    for(int i=0; i<levelNames.length; i++) {
    keys.add(levelNames[i]);
    }

    return keys;
    }
    
    public static Vector getLevelValues() {
    Vector values = new Vector();

    for(int i=0; i<levelNames.length; i++) {
    values.add(String.valueOf(i));
    }

    return values;
    }
    
     */
    public PstEmpReprimand() {
    }

    public PstEmpReprimand(int i) throws DBException {
        super(new PstEmpReprimand());
    }

    public PstEmpReprimand(long lOid) throws DBException {
        super(new PstEmpReprimand(0));
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

    public PstEmpReprimand(String sOid) throws DBException {
        super(new PstEmpReprimand(0));

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
        return TBL_REPRIMAND;
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
        EmpReprimand reprimand = fetchExc(ent.getOID());
        ent = (Entity) reprimand;
        return reprimand.getOID();
    }

    public long updateExc(Entity ent) throws Exception {
        return insertExc((EmpReprimand) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        return updateExc((EmpReprimand) ent);
    }

    public long insertExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpReprimand fetchExc(long oid) throws DBException {
        try {
            EmpReprimand reprimand = new EmpReprimand();
            PstEmpReprimand pstReprimand = new PstEmpReprimand(oid);
            reprimand.setOID(oid);

            //reprimand.s (pstReprimand.getlong(FLD_REPRIMAND_ID));
            reprimand.setArticle(pstReprimand.getString(FLD_ARTICLE));
            reprimand.setChapter(pstReprimand.getString(FLD_CHAPTER));
            reprimand.setDescription(pstReprimand.getString(FLD_DESCRIPTION));
            reprimand.setEmployeeId(pstReprimand.getlong(FLD_EMPLOYEE_ID));
            reprimand.setPage(pstReprimand.getString(FLD_PAGE));
            reprimand.setReprimandNumber(pstReprimand.getInt(FLD_NUMBER));
            reprimand.setReprimandDate(pstReprimand.getDate(FLD_REP_DATE));
            reprimand.setValidityDate(pstReprimand.getDate(FLD_VALIDITY));
            reprimand.setReprimandLevelId(pstReprimand.getlong(FLD_REPRIMAND_LEVEL_ID));
            reprimand.setCompanyId(pstReprimand.getlong(FLD_COMPANY_ID));
            reprimand.setDivisionId(pstReprimand.getlong(FLD_DIVISION_ID));
            reprimand.setDepartmentId(pstReprimand.getlong(FLD_DEPARTMENT_ID));
            reprimand.setSectionId(pstReprimand.getlong(FLD_SECTION_ID));
            reprimand.setPositionId(pstReprimand.getlong(FLD_POSITION_ID));
            reprimand.setLevelId(pstReprimand.getlong(FLD_LEVEL_ID));
            reprimand.setEmpCategoryId(pstReprimand.getlong(FLD_EMP_CATEGORY_ID));
            reprimand.setVerse(pstReprimand.getString(FLD_VERSE));
            reprimand.setDocument(pstReprimand.getString(FLD_DOCUMENT));
            reprimand.setEmpDocId(pstReprimand.getLong(FLD_EMP_DOC_ID));
            reprimand.setAcknowledgeStatus(pstReprimand.getInt(FLD_ACKNOWLEDGE_STATUS));
            return reprimand;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpReprimand(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpReprimand reprimand) throws DBException {
        try {
            PstEmpReprimand pstReprimand = new PstEmpReprimand(0);

            pstReprimand.setLong(FLD_EMPLOYEE_ID, reprimand.getEmployeeId());
            pstReprimand.setString(FLD_ARTICLE, reprimand.getArticle());
            pstReprimand.setString(FLD_CHAPTER, reprimand.getChapter());
            pstReprimand.setString(FLD_DESCRIPTION, reprimand.getDescription());
            pstReprimand.setInt(FLD_NUMBER, reprimand.getReprimandNumber());
            pstReprimand.setString(FLD_PAGE, reprimand.getPage());
            pstReprimand.setDate(FLD_REP_DATE, reprimand.getReprimandDate());
            pstReprimand.setDate(FLD_VALIDITY, reprimand.getValidityDate());
            pstReprimand.setLong(FLD_REPRIMAND_LEVEL_ID, reprimand.getReprimandLevelId());
            pstReprimand.setLong(FLD_COMPANY_ID, reprimand.getCompanyId());
            pstReprimand.setLong(FLD_DIVISION_ID, reprimand.getDivisionId());
            pstReprimand.setLong(FLD_DEPARTMENT_ID, reprimand.getDepartmentId());
            pstReprimand.setLong(FLD_SECTION_ID, reprimand.getSectionId());
            pstReprimand.setLong(FLD_POSITION_ID, reprimand.getPositionId());
            pstReprimand.setLong(FLD_LEVEL_ID, reprimand.getLevelId());
            pstReprimand.setLong(FLD_EMP_CATEGORY_ID, reprimand.getEmpCategoryId());
            pstReprimand.setString(FLD_VERSE, reprimand.getVerse());
            pstReprimand.setString(FLD_DOCUMENT, reprimand.getDocument());
            pstReprimand.setLong(FLD_EMP_DOC_ID, reprimand.getEmpDocId());
            pstReprimand.setInt(FLD_ACKNOWLEDGE_STATUS, reprimand.getAcknowledgeStatus());
            pstReprimand.insert();

            reprimand.setOID(pstReprimand.getlong(FLD_REPRIMAND_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpReprimand(0), DBException.UNKNOWN);
        }

        return reprimand.getOID();
    }

    public static long updateExc(EmpReprimand reprimand) throws DBException {
        try {
            if (reprimand.getOID() != 0) {
                PstEmpReprimand pstReprimand = new PstEmpReprimand(reprimand.getOID());

                pstReprimand.setLong(FLD_EMPLOYEE_ID, reprimand.getEmployeeId());
                pstReprimand.setString(FLD_ARTICLE, reprimand.getArticle());
                pstReprimand.setString(FLD_CHAPTER, reprimand.getChapter());
                pstReprimand.setString(FLD_DESCRIPTION, reprimand.getDescription());
                pstReprimand.setInt(FLD_NUMBER, reprimand.getReprimandNumber());
                pstReprimand.setString(FLD_PAGE, reprimand.getPage());
                pstReprimand.setDate(FLD_REP_DATE, reprimand.getReprimandDate());
                pstReprimand.setDate(FLD_VALIDITY, reprimand.getValidityDate());
                pstReprimand.setLong(FLD_REPRIMAND_LEVEL_ID, reprimand.getReprimandLevelId());
                pstReprimand.setLong(FLD_COMPANY_ID, reprimand.getCompanyId());
                pstReprimand.setLong(FLD_DIVISION_ID, reprimand.getDivisionId());
                pstReprimand.setLong(FLD_DEPARTMENT_ID, reprimand.getDepartmentId());
                pstReprimand.setLong(FLD_SECTION_ID, reprimand.getSectionId());
                pstReprimand.setLong(FLD_POSITION_ID, reprimand.getPositionId());
                pstReprimand.setLong(FLD_LEVEL_ID, reprimand.getLevelId());
                pstReprimand.setLong(FLD_EMP_CATEGORY_ID, reprimand.getEmpCategoryId());
                pstReprimand.setString(FLD_VERSE, reprimand.getVerse());
                pstReprimand.setString(FLD_DOCUMENT, reprimand.getDocument());
                pstReprimand.setLong(FLD_EMP_DOC_ID, reprimand.getEmpDocId());
                pstReprimand.setInt(FLD_ACKNOWLEDGE_STATUS, reprimand.getAcknowledgeStatus());
                pstReprimand.update();

                return reprimand.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpReprimand(0), DBException.UNKNOWN);
        }

        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpReprimand pstReprimand = new PstEmpReprimand(oid);
            pstReprimand.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpReprimand(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_REPRIMAND;
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
                EmpReprimand reprimand = new EmpReprimand();
                resultToObject(rs, reprimand);
                lists.add(reprimand);
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

    public static void resultToObject(ResultSet rs, EmpReprimand reprimand) {
        try {
            reprimand.setOID(rs.getLong(PstEmpReprimand.fieldNames[FLD_REPRIMAND_ID]));
            reprimand.setEmployeeId(rs.getLong(PstEmpReprimand.fieldNames[FLD_EMPLOYEE_ID]));
            reprimand.setArticle(rs.getString(PstEmpReprimand.fieldNames[FLD_ARTICLE]));
            reprimand.setChapter(rs.getString(PstEmpReprimand.fieldNames[FLD_CHAPTER]));
            reprimand.setDescription(rs.getString(PstEmpReprimand.fieldNames[FLD_DESCRIPTION]));
            reprimand.setPage(rs.getString(PstEmpReprimand.fieldNames[FLD_PAGE]));
            reprimand.setReprimandNumber(rs.getInt(PstEmpReprimand.fieldNames[FLD_NUMBER]));
            reprimand.setReprimandDate(rs.getDate(PstEmpReprimand.fieldNames[FLD_REP_DATE]));
            reprimand.setValidityDate(rs.getDate(PstEmpReprimand.fieldNames[FLD_VALIDITY]));
            reprimand.setReprimandLevelId(rs.getLong(PstEmpReprimand.fieldNames[FLD_REPRIMAND_LEVEL_ID]));
            reprimand.setCompanyId(rs.getLong(PstEmpReprimand.fieldNames[FLD_COMPANY_ID]));
            reprimand.setDivisionId(rs.getLong(PstEmpReprimand.fieldNames[FLD_DIVISION_ID]));
            reprimand.setDepartmentId(rs.getLong(PstEmpReprimand.fieldNames[FLD_DEPARTMENT_ID]));
            reprimand.setSectionId(rs.getLong(PstEmpReprimand.fieldNames[FLD_SECTION_ID]));
            reprimand.setPositionId(rs.getLong(PstEmpReprimand.fieldNames[FLD_POSITION_ID]));
            reprimand.setLevelId(rs.getLong(PstEmpReprimand.fieldNames[FLD_LEVEL_ID]));
            reprimand.setEmpCategoryId(rs.getLong(PstEmpReprimand.fieldNames[FLD_EMP_CATEGORY_ID]));
            reprimand.setVerse(rs.getString(PstEmpReprimand.fieldNames[FLD_VERSE]));
            reprimand.setDocument(rs.getString(PstEmpReprimand.fieldNames[FLD_DOCUMENT]));
            reprimand.setEmpDocId(rs.getLong(PstEmpReprimand.fieldNames[FLD_EMP_DOC_ID]));
            reprimand.setAcknowledgeStatus(rs.getInt(PstEmpReprimand.fieldNames[FLD_ACKNOWLEDGE_STATUS]));
        } catch (Exception e) {
        }
    }
    
    public static void updateFileName(String fileName,long idDetail) {
        try {
            String sql = "UPDATE " + PstEmpReprimand.TBL_REPRIMAND+
            " SET " + PstEmpReprimand.fieldNames[FLD_DOCUMENT] + " = '" + fileName +"'"+
            " WHERE " + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID] +
            " = " + idDetail ;           
            System.out.println("sql PstEmpReprimand.updateFileName : " + sql);
            int result = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("\tExc updateFileName : " + e.toString());
        } finally {
            //System.out.println("\tFinal updatePresenceStatus");
        }
    }

    public static boolean checkOID(long reprimId) {
        DBResultSet dbrs = null;
        boolean result = false;

        try {
            String sql = "SELECT * FROM " + TBL_REPRIMAND + " WHERE "
                    + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID] + " = '" + reprimId + "'";

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
            String sql = "SELECT COUNT(" + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID]
                    + ") FROM " + TBL_REPRIMAND;

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
                    EmpReprimand reprimand = (EmpReprimand) list.get(ls);
                    if (oid == reprimand.getOID()) {
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

    public static boolean checkWarning(long reprimandId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_REPRIMAND + " WHERE "
                    + //PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_WARN_LEVEL] + " = '" + reprimandId + "'";
                    PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_LEVEL_ID] + " = '" + reprimandId + "'";

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
    /*}*/

    //Gede_7Feb2012 {
    //untuk report excel
    public static int getCount2(SrcEmployee srcEmployee) {
        DBResultSet dbrs = null;
        SessEmployee sessEmployee = new SessEmployee();

        try {
            String sql = "SELECT COUNT(R." + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID] + ") FROM " + PstEmpReprimand.TBL_REPRIMAND + " R INNER JOIN "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON R." + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]) + " WHERE " + sessEmployee.whereList(srcEmployee) + "GROUP BY R." + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]
                    + " ORDER BY COUNT(R." + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID] + ") DESC LIMIT 1";

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
    //Reprimand
    public static String getReprimand(String level) {
        String reprimandLevel = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_DESC]
                    + " FROM " + PstReprimand.TBL_HR_REPRIMAND + " WHERE " + PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID]
                    + "=" + level;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                reprimandLevel = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return reprimandLevel;
    }
    //point

    public static String getReprimandPoint(String pointLvl) {
        String reprimandPoint = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_POINT]
                    + " FROM " + PstReprimand.TBL_HR_REPRIMAND + " WHERE " + PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID]
                    + "=" + pointLvl;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                reprimandPoint = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return reprimandPoint;
    }
    //}
    
    public static Vector listDataTable(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT EMP.* "
                    + "FROM "+PstEmpReprimand.TBL_REPRIMAND+" AS EMP "
                    + "INNER JOIN "+PstDivision.TBL_HR_DIVISION+" AS DI "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID]+" = "
                    + "DI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" "
                    + "INNER JOIN "+PstPosition.TBL_HR_POSITION+" AS POS "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID]+" = "
                    + "POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" "
                    + "LEFT JOIN "+PstWarningReprimandBab.TBL_BAB+" AS BAB "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_CHAPTER]+" = "
                    + "BAB."+PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID]+" "
                    + "LEFT JOIN "+PstWarningReprimandPasal.TBL_PASAL+" AS PASAL "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_ARTICLE]+" = "
                    + "PASAL."+PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_ID]+" "
                    + "LEFT JOIN "+PstWarningReprimandAyat.TBL_AYAT+" AS AYAT "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_VERSE]+" = "
                    + "AYAT."+PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_ID]+" "
                    + "LEFT JOIN "+PstReprimand.TBL_HR_REPRIMAND+" AS LVL "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_LEVEL_ID]+" = "
                    + "LVL."+PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID]+ " "
                    + "LEFT JOIN "+PstEmpDoc.TBL_HR_EMP_DOC+" AS DOC "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+" = "
                    + "DOC."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID];
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
                EmpReprimand reprimand = new EmpReprimand();
                resultToObject(rs, reprimand);
                lists.add(reprimand);
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
            String sql = "SELECT COUNT(EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID]+") "
                    + "FROM "+PstEmpReprimand.TBL_REPRIMAND+" AS EMP "
                    + "INNER JOIN "+PstDivision.TBL_HR_DIVISION+" AS DI "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID]+" = "
                    + "DI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" "
                    + "INNER JOIN "+PstPosition.TBL_HR_POSITION+" AS POS "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID]+" = "
                    + "POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" "
                    + "LEFT JOIN "+PstWarningReprimandBab.TBL_BAB+" AS BAB "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_CHAPTER]+" = "
                    + "BAB."+PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID]+" "
                    + "LEFT JOIN "+PstWarningReprimandPasal.TBL_PASAL+" AS PASAL "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_ARTICLE]+" = "
                    + "PASAL."+PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_ID]+" "
                    + "LEFT JOIN "+PstWarningReprimandAyat.TBL_AYAT+" AS AYAT "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_VERSE]+" = "
                    + "AYAT."+PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_ID]+" "
                    + "LEFT JOIN "+PstReprimand.TBL_HR_REPRIMAND+" AS LVL "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_LEVEL_ID]+" = "
                    + "LVL."+PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID]+ " "
                    + "LEFT JOIN "+PstEmpDoc.TBL_HR_EMP_DOC+" AS DOC "
                    + "ON EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+" = "
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
    
        public static Hashtable fetchExcHashtable(long oid) throws DBException {
        try {
            Hashtable empReprimand = new Hashtable();
            EmpReprimand reprimand = new EmpReprimand();
            PstEmpReprimand pstEmpReprimand = new PstEmpReprimand(oid);
            Employee employee = new Employee();
            
            reprimand.setEmployeeId(pstEmpReprimand.getLong(FLD_EMPLOYEE_ID));
            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID], reprimand.getEmployeeId());
            
            reprimand.setDepartmentId(pstEmpReprimand.getlong(FLD_DEPARTMENT_ID));
            Department department = new Department();
            try { department = PstDepartment.fetchExc(reprimand.getDepartmentId()); } catch (Exception e){ }
            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DEPARTMENT_ID], department.getDepartment());
            
            reprimand.setPositionId(pstEmpReprimand.getlong(FLD_POSITION_ID));
            Position position = new Position();
            try { position = PstPosition.fetchExc(reprimand.getPositionId()); } catch (Exception e){ }
            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID], position.getPosition());
            
            reprimand.setSectionId(pstEmpReprimand.getlong(FLD_SECTION_ID));
            Section section = new Section();
            try { section = PstSection.fetchExc(reprimand.getSectionId()); } catch (Exception e){ }
            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_SECTION_ID], section.getSection());
            
            reprimand.setDivisionId(pstEmpReprimand.getlong(FLD_DIVISION_ID));
            Division division = new Division();
            try { division = PstDivision.fetchExc(reprimand.getDivisionId()); } catch (Exception e){ }
            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID], division.getDivision());
            
            reprimand.setCompanyId(pstEmpReprimand.getlong(FLD_COMPANY_ID));
            Company company = new Company();
            try { company = PstCompany.fetchExc(reprimand.getCompanyId()); } catch (Exception e){ }
            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_COMPANY_ID], company.getCompany());
            
            reprimand.setReprimandLevelId(pstEmpReprimand.getlong(FLD_REPRIMAND_LEVEL_ID));
            Reprimand rep = new Reprimand();
            try { rep = PstReprimand.fetchExc(reprimand.getReprimandLevelId()); } catch (Exception e){ }
            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_LEVEL_ID], ""+rep.getReprimandDesc());
            
            reprimand.setReprimandDate(pstEmpReprimand.getDate(FLD_REP_DATE));
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            String repDate = sdf.format(reprimand.getReprimandDate());

            empReprimand.put(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE], repDate);
            
            try { employee = PstEmployee.fetchExc(reprimand.getEmployeeId()); } catch (Exception exc){}
            empReprimand.put(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME], employee.getFullName());
            return empReprimand;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAward(0), DBException.UNKNOWN);
        }
    }
}
