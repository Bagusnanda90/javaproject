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
import com.dimata.util.DynamicField;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class PstCompetencyLevel extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_COMPETENCY_LEVEL = "hr_competency_level";
    public static final int FLD_COMPETENCY_LEVEL_ID = 0;
    public static final int FLD_COMPETENCY_ID = 1;
    public static final int FLD_SCORE_VALUE = 2;
    public static final int FLD_DESCRIPTION = 3;
    public static final int FLD_LEVEL_MIN = 4;
    public static final int FLD_LEVEL_MAX = 5;
    public static final int FLD_LEVEL_UNIT = 6;
    public static final int FLD_VALID_START = 7;
    public static final int FLD_VALID_END = 8;
    public static String[] fieldNames = {
        "COMPETENCY_LEVEL_ID",
        "COMPETENCY_ID",
        "SCORE_VALUE",
        "DESCRIPTION",
        "LEVEL_MIN",
        "LEVEL_MAX",
        "LEVEL_UNIT",
        "VALID_START",
        "VALID_END"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE
    };

    public PstCompetencyLevel() {
    }

    public PstCompetencyLevel(int i) throws DBException {
        super(new PstCompetencyLevel());
    }

    public PstCompetencyLevel(String sOid) throws DBException {
        super(new PstCompetencyLevel(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCompetencyLevel(long lOid) throws DBException {
        super(new PstCompetencyLevel(0));
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
        return TBL_COMPETENCY_LEVEL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCompetencyLevel().getClass().getName();
    }

    public static CompetencyLevel fetchExc(long oid) throws DBException {
        try {
            CompetencyLevel entCompetencyLevel = new CompetencyLevel();
            PstCompetencyLevel pstCompetencyLevel = new PstCompetencyLevel(oid);
            entCompetencyLevel.setOID(oid);
            entCompetencyLevel.setCompetencyId(pstCompetencyLevel.getlong(FLD_COMPETENCY_ID));
            entCompetencyLevel.setScoreValue((float)pstCompetencyLevel.getdouble(FLD_SCORE_VALUE));
            entCompetencyLevel.setDescription(pstCompetencyLevel.getString(FLD_DESCRIPTION));
            entCompetencyLevel.setLevelMin(pstCompetencyLevel.getInt(FLD_LEVEL_MIN));
            entCompetencyLevel.setLevelMax(pstCompetencyLevel.getInt(FLD_LEVEL_MAX));
            entCompetencyLevel.setLevelUnit(pstCompetencyLevel.getString(FLD_LEVEL_UNIT));
            entCompetencyLevel.setValidStart(pstCompetencyLevel.getDate(FLD_VALID_START));
            entCompetencyLevel.setValidEnd(pstCompetencyLevel.getDate(FLD_VALID_END));
            return entCompetencyLevel;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyLevel(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CompetencyLevel entCompetencyLevel = fetchExc(entity.getOID());
        entity = (Entity) entCompetencyLevel;
        return entCompetencyLevel.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((CompetencyLevel) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((CompetencyLevel) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(CompetencyLevel entCompetencyLevel) throws DBException {
        try {

            PstCompetencyLevel pstCompetencyLevel = new PstCompetencyLevel(0);
            pstCompetencyLevel.setLong(FLD_COMPETENCY_ID, entCompetencyLevel.getCompetencyId());
            pstCompetencyLevel.setFloat(FLD_SCORE_VALUE, entCompetencyLevel.getScoreValue());
            pstCompetencyLevel.setString(FLD_DESCRIPTION, entCompetencyLevel.getDescription());
            pstCompetencyLevel.setInt(FLD_LEVEL_MIN, entCompetencyLevel.getLevelMin());
            pstCompetencyLevel.setInt(FLD_LEVEL_MAX, entCompetencyLevel.getLevelMax());
            pstCompetencyLevel.setString(FLD_LEVEL_UNIT, entCompetencyLevel.getLevelUnit());
            pstCompetencyLevel.setDate(FLD_VALID_START, entCompetencyLevel.getValidStart());
            pstCompetencyLevel.setDate(FLD_VALID_END, entCompetencyLevel.getValidEnd());
            pstCompetencyLevel.insert();
            entCompetencyLevel.setOID(pstCompetencyLevel.getLong(FLD_COMPETENCY_LEVEL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyLevel(0), DBException.UNKNOWN);
        }
        return entCompetencyLevel.getOID();
    }

    public static long updateExc(CompetencyLevel entCompetencyLevel) throws DBException {
        try {
            if (entCompetencyLevel.getOID() != 0) {
                PstCompetencyLevel pstCompetencyLevel = new PstCompetencyLevel(entCompetencyLevel.getOID());

                pstCompetencyLevel.setLong(FLD_COMPETENCY_ID, entCompetencyLevel.getCompetencyId());
                pstCompetencyLevel.setFloat(FLD_SCORE_VALUE, entCompetencyLevel.getScoreValue());
                pstCompetencyLevel.setString(FLD_DESCRIPTION, entCompetencyLevel.getDescription());
                pstCompetencyLevel.setInt(FLD_LEVEL_MIN, entCompetencyLevel.getLevelMin());
                pstCompetencyLevel.setInt(FLD_LEVEL_MAX, entCompetencyLevel.getLevelMax());
                pstCompetencyLevel.setString(FLD_LEVEL_UNIT, entCompetencyLevel.getLevelUnit());
                pstCompetencyLevel.setDate(FLD_VALID_START, entCompetencyLevel.getValidStart());
                pstCompetencyLevel.setDate(FLD_VALID_END, entCompetencyLevel.getValidEnd());
                pstCompetencyLevel.update();
                return entCompetencyLevel.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyLevel(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstCompetencyLevel pstCompetencyLevel = new PstCompetencyLevel(oid);
            pstCompetencyLevel.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyLevel(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_COMPETENCY_LEVEL;
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
                CompetencyLevel entCompetencyLevel = new CompetencyLevel();
                resultToObject(rs, entCompetencyLevel);
                lists.add(entCompetencyLevel);
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
    
    public static Vector<DynamicField> getData(String query, Vector<String> fields){
        Vector list = new Vector();
        DBResultSet dbrs = null;
        try {
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DynamicField dynamic = new DynamicField();
                for (int i=0; i<fields.size(); i++){
                    String dataField = (String)fields.get(i);
                    dynamic.setFields(dataField, rs.getString(dataField));
                }
                list.add(dynamic);
            }
            rs.close();
            return list;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }

    private static void resultToObject(ResultSet rs, CompetencyLevel entCompetencyLevel) {
        try {

            entCompetencyLevel.setOID(rs.getLong(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_LEVEL_ID]));
            entCompetencyLevel.setCompetencyId(rs.getLong(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_ID]));
            entCompetencyLevel.setScoreValue(rs.getFloat(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_SCORE_VALUE]));
            entCompetencyLevel.setDescription(rs.getString(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_DESCRIPTION]));
            entCompetencyLevel.setLevelMin(rs.getInt(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_LEVEL_MIN]));
            entCompetencyLevel.setLevelMax(rs.getInt(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_LEVEL_MAX]));
            entCompetencyLevel.setLevelUnit(rs.getString(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_LEVEL_UNIT]));
            entCompetencyLevel.setValidStart(rs.getDate(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_VALID_START]));
            entCompetencyLevel.setValidEnd(rs.getDate(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_VALID_END]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entCompetencyLevelId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_COMPETENCY_LEVEL + " WHERE "
                    + PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_LEVEL_ID] + " = " + entCompetencyLevelId;

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
            String sql = "SELECT COUNT(" + PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_LEVEL_ID] + ") FROM " + TBL_COMPETENCY_LEVEL;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    CompetencyLevel entCompetencyLevel = (CompetencyLevel) list.get(ls);
                    if (oid == entCompetencyLevel.getOID()) {
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
    /* This method used to find command where current data */

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