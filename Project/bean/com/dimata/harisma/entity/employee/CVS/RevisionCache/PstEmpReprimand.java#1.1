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
        "REPRIMAND_LEVEL_ID"
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
        TYPE_LONG
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
        } catch (Exception e) {
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
}
