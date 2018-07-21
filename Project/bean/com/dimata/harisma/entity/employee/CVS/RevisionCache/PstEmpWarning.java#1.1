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
import com.dimata.harisma.entity.employee.EmpWarning;

/**
 *
 * @author guest
 */
public class PstEmpWarning extends DBHandler implements I_Language, I_PersintentExc, I_DBInterface, I_DBType {

    /**
     * Ari_20110903
     * Mengganti Warn_Level menjadi Warn_Level_Id
     * {
     */
    public static final String TBL_WARNING = "hr_warning";
    public static final int FLD_WARNING_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_BREAK_FACT = 2;
    public static final int FLD_BREAK_DATE = 3;
    public static final int FLD_WARN_BY = 4;
    public static final int FLD_WARN_DATE = 5;
    public static final int FLD_VALID_UNTIL = 6;
    //public static final int FLD_WARN_LEVEL    = 7;
    public static final int FLD_WARN_LEVEL_ID = 7;
    public static String[] fieldNames = {
        "WARNING_ID",
        "EMPLOYEE_ID",
        "BREAK_FACT",
        "BREAK_DATE",
        "WARN_BY",
        "WARN_DATE",
        "VALID_UNTIL",
        "WARN_LEVEL_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG
    };

    /* public static final int WARN_VERBAL             =   0;
    public static final int WARN_LEVEL_1            =   1;
    public static final int WARN_LEVEL_2            =   2;
    public static final int WARN_LEVEL_3            =   3;

    public static String[] levelNames = 
    {
    "Verbal Warning",
    "Warning Level 1",
    "Warning Level 2",
    "Warning Level 3"
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
    public PstEmpWarning() {
    }

    public PstEmpWarning(int i) throws DBException {
        super(new PstEmpWarning());
    }

    public PstEmpWarning(long lOid) throws DBException {
        super(new PstEmpWarning(0));
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

    public PstEmpWarning(String sOid) throws DBException {
        super(new PstEmpWarning(0));

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
        return TBL_WARNING;
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
        EmpWarning warning = fetchExc(ent.getOID());
        ent = (Entity) warning;
        return warning.getOID();
    }

    public long updateExc(Entity ent) throws Exception {
        return insertExc((EmpWarning) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        return updateExc((EmpWarning) ent);
    }

    public long insertExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpWarning fetchExc(long oid) throws DBException {
        try {
            EmpWarning warning = new EmpWarning();
            PstEmpWarning pstWarning = new PstEmpWarning(oid);

            warning.setOID(pstWarning.getlong(FLD_WARNING_ID));
            warning.setEmployeeId(pstWarning.getlong(FLD_EMPLOYEE_ID));
            warning.setBreakDate(pstWarning.getDate(FLD_BREAK_DATE));
            warning.setBreakFact(pstWarning.getString(FLD_BREAK_FACT));
            warning.setWarningDate(pstWarning.getDate(FLD_WARN_DATE));
            warning.setWarningBy(pstWarning.getString(FLD_WARN_BY));
            warning.setValidityDate(pstWarning.getDate(FLD_VALID_UNTIL));
            //warning.setWarnLevel(pstWarning.getInt(FLD_WARN_LEVEL));
            warning.setWarnLevelId(pstWarning.getlong(FLD_WARN_LEVEL_ID));



            return warning;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpWarning(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpWarning warning) throws DBException {
        try {
            PstEmpWarning pstWarning = new PstEmpWarning(0);

            pstWarning.setLong(FLD_EMPLOYEE_ID, warning.getEmployeeId());
            pstWarning.setDate(FLD_BREAK_DATE, warning.getBreakDate());
            pstWarning.setString(FLD_BREAK_FACT, warning.getBreakFact());
            pstWarning.setDate(FLD_WARN_DATE, warning.getWarningDate());
            pstWarning.setString(FLD_WARN_BY, warning.getWarningBy());
            pstWarning.setDate(FLD_VALID_UNTIL, warning.getValidityDate());
            //pstWarning.setInt(FLD_WARN_LEVEL, warning.getWarnLevel());
            pstWarning.setLong(FLD_WARN_LEVEL_ID, warning.getWarnLevelId());

            pstWarning.insert();

            warning.setOID(pstWarning.getlong(FLD_WARNING_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpWarning(0), DBException.UNKNOWN);
        }

        return warning.getOID();
    }

    public static long updateExc(EmpWarning warning) throws DBException {
        try {
            if (warning.getOID() != 0) {
                PstEmpWarning pstWarning = new PstEmpWarning(warning.getOID());

                pstWarning.setLong(FLD_EMPLOYEE_ID, warning.getEmployeeId());
                pstWarning.setDate(FLD_BREAK_DATE, warning.getBreakDate());
                pstWarning.setString(FLD_BREAK_FACT, warning.getBreakFact());
                pstWarning.setDate(FLD_WARN_DATE, warning.getWarningDate());
                pstWarning.setString(FLD_WARN_BY, warning.getWarningBy());
                pstWarning.setDate(FLD_VALID_UNTIL, warning.getValidityDate());
                //pstWarning.setInt(FLD_WARN_LEVEL, warning.getWarnLevel());
                pstWarning.setLong(FLD_WARN_LEVEL_ID, warning.getWarnLevelId());

                pstWarning.update();

                return warning.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpWarning(0), DBException.UNKNOWN);
        }

        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpWarning pstEmpWarning = new PstEmpWarning(oid);
            pstEmpWarning.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpWarning(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_WARNING;
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
                EmpWarning warning = new EmpWarning();
                resultToObject(rs, warning);
                lists.add(warning);
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

    public static void resultToObject(ResultSet rs, EmpWarning warning) {
        try {
            warning.setOID(rs.getLong(PstEmpWarning.fieldNames[FLD_WARNING_ID]));
            warning.setEmployeeId(rs.getLong(PstEmpWarning.fieldNames[FLD_EMPLOYEE_ID]));
            warning.setBreakDate(rs.getDate(PstEmpWarning.fieldNames[FLD_BREAK_DATE]));
            warning.setBreakFact(rs.getString(PstEmpWarning.fieldNames[FLD_BREAK_FACT]));
            warning.setWarningDate(rs.getDate(PstEmpWarning.fieldNames[FLD_WARN_DATE]));
            warning.setWarningBy(rs.getString(PstEmpWarning.fieldNames[FLD_WARN_BY]));
            warning.setValidityDate(rs.getDate(PstEmpWarning.fieldNames[FLD_VALID_UNTIL]));
            //warning.setWarnLevel(rs.getInt(PstEmpWarning.fieldNames[FLD_WARN_LEVEL]));
            warning.setWarnLevelId(rs.getLong(PstEmpWarning.fieldNames[FLD_WARN_LEVEL_ID]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long warningId) {
        DBResultSet dbrs = null;
        boolean result = false;

        try {
            String sql = "SELECT * FROM " + TBL_WARNING + " WHERE "
                    + PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID] + " = '" + warningId + "'";

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
            String sql = "SELECT COUNT(" + PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID]
                    + ") FROM " + TBL_WARNING;

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
                    EmpWarning warning = (EmpWarning) list.get(ls);
                    if (oid == warning.getOID()) {
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

    public static boolean checkWarning(long warnId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_WARNING + " WHERE "
                    + //PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_LEVEL] + " = '" + warnId + "'";
                    PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_LEVEL_ID] + " = '" + warnId + "'";

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
            String sql = "SELECT COUNT(W." + PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID] + ") FROM " + PstEmpWarning.TBL_WARNING + " W INNER JOIN "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON W." + PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]) + " WHERE " + sessEmployee.whereList(srcEmployee) + "GROUP BY W." + PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]
                    + " ORDER BY COUNT(W." + PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID] + ") DESC LIMIT 1";

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
    //Warning

    public static String getWarning(String level) {
        String warningLevel = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstWarning.fieldNames[PstWarning.FLD_WARN_DESC]
                    + " FROM " + PstWarning.TBL_HR_WARNING + " WHERE " + PstWarning.fieldNames[PstWarning.FLD_WARN_ID]
                    + "=" + level;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql1:"+sql);
            while (rs.next()) {
                warningLevel = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return warningLevel;
    }
    //Warning

    public static String getWarningPoint(String pointLvl) {
        String warningPoint = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstWarning.fieldNames[PstWarning.FLD_WARN_POINT]
                    + " FROM " + PstWarning.TBL_HR_WARNING + " WHERE " + PstWarning.fieldNames[PstWarning.FLD_WARN_ID]
                    + "=" + pointLvl;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql1:"+sql);
            while (rs.next()) {
                warningPoint = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return warningPoint;
    }
    //}
}
