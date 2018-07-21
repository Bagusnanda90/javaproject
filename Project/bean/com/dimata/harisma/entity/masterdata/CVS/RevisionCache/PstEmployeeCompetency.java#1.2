/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

public class PstEmployeeCompetency extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_EMPLOYEE_COMPETENCY = "hr_emp_competency";
    public static final int FLD_EMPLOYEE_COMP_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_COMPETENCY_ID = 2;
    public static final int FLD_LEVEL_VALUE = 3;
    public static final int FLD_SPECIAL_ACHIEVEMENT = 4;
    public static final int FLD_DATE_OF_ACHVMT = 5;
    public static final int FLD_HISTORY = 6;
    public static final int FLD_PROVIDER_ID = 7;
    public static String[] fieldNames = {
        "EMPLOYEE_COMP_ID",
        "EMPLOYEE_ID",
        "COMPETENCY_ID",
        "LEVEL_VALUE",
        "SPECIAL_ACHIEVEMENT",
        "DATE_OF_ACHVMT",
        "HISTORY",
        "PROVIDER_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG
    };

    public PstEmployeeCompetency() {
    }

    public PstEmployeeCompetency(int i) throws DBException {
        super(new PstEmployeeCompetency());
    }

    public PstEmployeeCompetency(String sOid) throws DBException {
        super(new PstEmployeeCompetency(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmployeeCompetency(long lOid) throws DBException {
        super(new PstEmployeeCompetency(0));
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
        return TBL_EMPLOYEE_COMPETENCY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmployeeCompetency().getClass().getName();
    }

    public static EmployeeCompetency fetchExc(long oid) throws DBException {
        try {
            EmployeeCompetency entEmployeeCompetency = new EmployeeCompetency();
            PstEmployeeCompetency pstEmployeeCompetency = new PstEmployeeCompetency(oid);
            entEmployeeCompetency.setOID(oid);
            entEmployeeCompetency.setEmployeeId(pstEmployeeCompetency.getLong(FLD_EMPLOYEE_ID));
            entEmployeeCompetency.setCompetencyId(pstEmployeeCompetency.getLong(FLD_COMPETENCY_ID));
            entEmployeeCompetency.setLevelValue(pstEmployeeCompetency.getfloat(FLD_LEVEL_VALUE));
            entEmployeeCompetency.setSpecialAchievement(pstEmployeeCompetency.getString(FLD_SPECIAL_ACHIEVEMENT));
            entEmployeeCompetency.setDateOfAchvmt(pstEmployeeCompetency.getDate(FLD_DATE_OF_ACHVMT));
            entEmployeeCompetency.setHistory(pstEmployeeCompetency.getInt(FLD_HISTORY));
            entEmployeeCompetency.setProviderId(pstEmployeeCompetency.getLong(FLD_PROVIDER_ID));
            return entEmployeeCompetency;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeCompetency(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        EmployeeCompetency entEmployeeCompetency = fetchExc(entity.getOID());
        entity = (Entity) entEmployeeCompetency;
        return entEmployeeCompetency.getOID();
    }

    public static synchronized long updateExc(EmployeeCompetency entEmployeeCompetency) throws DBException {
        try {
            if (entEmployeeCompetency.getOID() != 0) {
                PstEmployeeCompetency pstEmployeeCompetency = new PstEmployeeCompetency(entEmployeeCompetency.getOID());
                pstEmployeeCompetency.setLong(FLD_EMPLOYEE_ID, entEmployeeCompetency.getEmployeeId());
                pstEmployeeCompetency.setLong(FLD_COMPETENCY_ID, entEmployeeCompetency.getCompetencyId());
                pstEmployeeCompetency.setFloat(FLD_LEVEL_VALUE, entEmployeeCompetency.getLevelValue());
                pstEmployeeCompetency.setString(FLD_SPECIAL_ACHIEVEMENT, entEmployeeCompetency.getSpecialAchievement());
                pstEmployeeCompetency.setDate(FLD_DATE_OF_ACHVMT, entEmployeeCompetency.getDateOfAchvmt());
                pstEmployeeCompetency.setInt(FLD_HISTORY, entEmployeeCompetency.getHistory());
                pstEmployeeCompetency.setLong(FLD_PROVIDER_ID, entEmployeeCompetency.getProviderId());
                pstEmployeeCompetency.update();
                return entEmployeeCompetency.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeCompetency(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((EmployeeCompetency) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstEmployeeCompetency pstEmployeeCompetency = new PstEmployeeCompetency(oid);
            pstEmployeeCompetency.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeCompetency(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(EmployeeCompetency entEmployeeCompetency) throws DBException {
        try {
            PstEmployeeCompetency pstEmployeeCompetency = new PstEmployeeCompetency(0);
            pstEmployeeCompetency.setLong(FLD_EMPLOYEE_ID, entEmployeeCompetency.getEmployeeId());
            pstEmployeeCompetency.setLong(FLD_COMPETENCY_ID, entEmployeeCompetency.getCompetencyId());
            pstEmployeeCompetency.setFloat(FLD_LEVEL_VALUE, entEmployeeCompetency.getLevelValue());
            pstEmployeeCompetency.setString(FLD_SPECIAL_ACHIEVEMENT, entEmployeeCompetency.getSpecialAchievement());
            pstEmployeeCompetency.setDate(FLD_DATE_OF_ACHVMT, entEmployeeCompetency.getDateOfAchvmt());
            pstEmployeeCompetency.setInt(FLD_HISTORY, entEmployeeCompetency.getHistory());
            pstEmployeeCompetency.setLong(FLD_PROVIDER_ID, entEmployeeCompetency.getProviderId());
            pstEmployeeCompetency.insert();
            entEmployeeCompetency.setOID(pstEmployeeCompetency.getLong(FLD_EMPLOYEE_COMP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeCompetency(0), DBException.UNKNOWN);
        }
        return entEmployeeCompetency.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((EmployeeCompetency) entity);
    }

    public static void resultToObject(ResultSet rs, EmployeeCompetency entEmployeeCompetency) {
        try {
            entEmployeeCompetency.setOID(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_COMP_ID]));
            entEmployeeCompetency.setEmployeeId(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]));
            entEmployeeCompetency.setCompetencyId(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_COMPETENCY_ID]));
            entEmployeeCompetency.setLevelValue(rs.getFloat(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_LEVEL_VALUE]));
            entEmployeeCompetency.setSpecialAchievement(rs.getString(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_SPECIAL_ACHIEVEMENT]));
            entEmployeeCompetency.setDateOfAchvmt(rs.getDate(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_DATE_OF_ACHVMT]));
            entEmployeeCompetency.setHistory(rs.getInt(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_HISTORY]));
            entEmployeeCompetency.setProviderId(rs.getLong(PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_PROVIDER_ID]));
        } catch (Exception e) {
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_EMPLOYEE_COMPETENCY;
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
                EmployeeCompetency entEmployeeCompetency = new EmployeeCompetency();
                resultToObject(rs, entEmployeeCompetency);
                lists.add(entEmployeeCompetency);
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

    public static boolean checkOID(long entEmployeeCompetencyId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EMPLOYEE_COMPETENCY + " WHERE "
                    + PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_COMP_ID] + " = " + entEmployeeCompetencyId;
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
            String sql = "SELECT COUNT(" + PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_COMP_ID] + ") FROM " + TBL_EMPLOYEE_COMPETENCY;
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
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    EmployeeCompetency entEmployeeCompetency = (EmployeeCompetency) list.get(ls);
                    if (oid == entEmployeeCompetency.getOID()) {
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

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + (recordToGet - mdl);
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
}