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

/**
 *
 * @author Dimata 007
 */
public class PstCompetencyType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_COMPETENCY_TYPE = "hr_competency_type";
    public static final int FLD_COMPETENCY_TYPE_ID = 0;
    public static final int FLD_TYPE_NAME = 1;
    public static final int FLD_NOTE = 2;
    public static String[] fieldNames = {
        "COMPETENCY_TYPE_ID",
        "TYPE_NAME",
        "NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstCompetencyType() {
    }

    public PstCompetencyType(int i) throws DBException {
        super(new PstCompetencyType());
    }

    public PstCompetencyType(String sOid) throws DBException {
        super(new PstCompetencyType(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCompetencyType(long lOid) throws DBException {
        super(new PstCompetencyType(0));
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
        return TBL_COMPETENCY_TYPE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCompetencyType().getClass().getName();
    }

    public static CompetencyType fetchExc(long oid) throws DBException {
        try {
            CompetencyType entCompetencyType = new CompetencyType();
            PstCompetencyType pstCompetencyType = new PstCompetencyType(oid);
            entCompetencyType.setOID(oid);
            entCompetencyType.setTypeName(pstCompetencyType.getString(FLD_TYPE_NAME));
            entCompetencyType.setNote(pstCompetencyType.getString(FLD_NOTE));
            return entCompetencyType;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyType(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CompetencyType entCompetencyType = fetchExc(entity.getOID());
        entity = (Entity) entCompetencyType;
        return entCompetencyType.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((CompetencyType) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((CompetencyType) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(CompetencyType entCompetencyType) throws DBException {
        try {
            PstCompetencyType pstCompetencyType = new PstCompetencyType(0);
            pstCompetencyType.setString(FLD_TYPE_NAME, entCompetencyType.getTypeName());
            pstCompetencyType.setString(FLD_NOTE, entCompetencyType.getNote());
            pstCompetencyType.insert();
            entCompetencyType.setOID(pstCompetencyType.getLong(FLD_COMPETENCY_TYPE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyType(0), DBException.UNKNOWN);
        }
        return entCompetencyType.getOID();
    }

    public static long updateExc(CompetencyType entCompetencyType) throws DBException {
        try {
            if (entCompetencyType.getOID() != 0) {
                PstCompetencyType pstCompetencyType = new PstCompetencyType(entCompetencyType.getOID());

                pstCompetencyType.setString(FLD_TYPE_NAME, entCompetencyType.getTypeName());
                pstCompetencyType.setString(FLD_NOTE, entCompetencyType.getNote());

                pstCompetencyType.update();
                return entCompetencyType.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyType(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstCompetencyType pstCompetencyType = new PstCompetencyType(oid);
            pstCompetencyType.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyType(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_COMPETENCY_TYPE;
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
                CompetencyType entCompetencyType = new CompetencyType();
                resultToObject(rs, entCompetencyType);
                lists.add(entCompetencyType);
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

    private static void resultToObject(ResultSet rs, CompetencyType entCompetencyType) {
        try {

            entCompetencyType.setOID(rs.getLong(PstCompetencyType.fieldNames[PstCompetencyType.FLD_COMPETENCY_TYPE_ID]));
            entCompetencyType.setTypeName(rs.getString(PstCompetencyType.fieldNames[PstCompetencyType.FLD_TYPE_NAME]));
            entCompetencyType.setNote(rs.getString(PstCompetencyType.fieldNames[PstCompetencyType.FLD_NOTE]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entCompetencyTypeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_COMPETENCY_TYPE + " WHERE "
                    + PstCompetencyType.fieldNames[PstCompetencyType.FLD_COMPETENCY_TYPE_ID] + " = " + entCompetencyTypeId;

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
            String sql = "SELECT COUNT(" + PstCompetencyType.fieldNames[PstCompetencyType.FLD_COMPETENCY_TYPE_ID] + ") FROM " + TBL_COMPETENCY_TYPE;
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
                    CompetencyType entCompetencyType = (CompetencyType) list.get(ls);
                    if (oid == entCompetencyType.getOID()) {
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