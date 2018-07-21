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
public class PstCompetencyGroup extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_COMPETENCY_GROUP = "hr_competency_group";
    public static final int FLD_COMPETENCY_GROUP_ID = 0;
    public static final int FLD_GROUP_NAME = 1;
    public static final int FLD_NOTE = 2;
    public static String[] fieldNames = {
        "COMPETENCY_GROUP_ID",
        "GROUP_NAME",
        "NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstCompetencyGroup() {
    }

    public PstCompetencyGroup(int i) throws DBException {
        super(new PstCompetencyGroup());
    }

    public PstCompetencyGroup(String sOid) throws DBException {
        super(new PstCompetencyGroup(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCompetencyGroup(long lOid) throws DBException {
        super(new PstCompetencyGroup(0));
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
        return TBL_COMPETENCY_GROUP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCompetencyGroup().getClass().getName();
    }

    public static CompetencyGroup fetchExc(long oid) throws DBException {
        try {
            CompetencyGroup entCompetencyGroup = new CompetencyGroup();
            PstCompetencyGroup pstCompetencyGroup = new PstCompetencyGroup(oid);
            entCompetencyGroup.setOID(oid);
            entCompetencyGroup.setGroupName(pstCompetencyGroup.getString(FLD_GROUP_NAME));
            entCompetencyGroup.setNote(pstCompetencyGroup.getString(FLD_NOTE));
            return entCompetencyGroup;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyGroup(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CompetencyGroup entCompetencyGroup = fetchExc(entity.getOID());
        entity = (Entity) entCompetencyGroup;
        return entCompetencyGroup.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((CompetencyGroup) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((CompetencyGroup) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(CompetencyGroup entCompetencyGroup) throws DBException {
        try {
            PstCompetencyGroup pstCompetencyGroup = new PstCompetencyGroup(0);
            pstCompetencyGroup.setString(FLD_GROUP_NAME, entCompetencyGroup.getGroupName());
            pstCompetencyGroup.setString(FLD_NOTE, entCompetencyGroup.getNote());
            pstCompetencyGroup.insert();
            entCompetencyGroup.setOID(pstCompetencyGroup.getLong(FLD_COMPETENCY_GROUP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyGroup(0), DBException.UNKNOWN);
        }
        return entCompetencyGroup.getOID();
    }

    public static long updateExc(CompetencyGroup entCompetencyGroup) throws DBException {
        try {
            if (entCompetencyGroup.getOID() != 0) {
                PstCompetencyGroup pstCompetencyGroup = new PstCompetencyGroup(entCompetencyGroup.getOID());

                pstCompetencyGroup.setString(FLD_GROUP_NAME, entCompetencyGroup.getGroupName());
                pstCompetencyGroup.setString(FLD_NOTE, entCompetencyGroup.getNote());

                pstCompetencyGroup.update();
                return entCompetencyGroup.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyGroup(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstCompetencyGroup pstCompetencyGroup = new PstCompetencyGroup(oid);
            pstCompetencyGroup.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyGroup(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_COMPETENCY_GROUP;
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
                CompetencyGroup entCompetencyGroup = new CompetencyGroup();
                resultToObject(rs, entCompetencyGroup);
                lists.add(entCompetencyGroup);
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

    private static void resultToObject(ResultSet rs, CompetencyGroup entCompetencyGroup) {
        try {

            entCompetencyGroup.setOID(rs.getLong(PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_COMPETENCY_GROUP_ID]));
            entCompetencyGroup.setGroupName(rs.getString(PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_GROUP_NAME]));
            entCompetencyGroup.setNote(rs.getString(PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_NOTE]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entCompetencyGroupId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_COMPETENCY_GROUP + " WHERE "
                    + PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_COMPETENCY_GROUP_ID] + " = " + entCompetencyGroupId;

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
            String sql = "SELECT COUNT(" + PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_COMPETENCY_GROUP_ID] + ") FROM " + TBL_COMPETENCY_GROUP;
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
                    CompetencyGroup entCompetencyGroup = (CompetencyGroup) list.get(ls);
                    if (oid == entCompetencyGroup.getOID()) {
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