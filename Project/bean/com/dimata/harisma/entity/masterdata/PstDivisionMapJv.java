/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Dimata 007
 */
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

public class PstDivisionMapJv extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DIVISION_MAP_JV = "hr_division_map_jv";
    public static final int FLD_DIVISION_MAP_JV_ID = 0;
    public static final int FLD_DIVISION_ID = 1;
    public static final int FLD_DIVISION_NAME = 2;
    public static final int FLD_DIVISION_CODE_ID = 3;
    public static String[] fieldNames = {
        "DIVISION_MAP_JV_ID",
        "DIVISION_ID",
        "DIVISION_NAME",
        "DIVISION_CODE_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG
    };

    public PstDivisionMapJv() {
    }

    public PstDivisionMapJv(int i) throws DBException {
        super(new PstDivisionMapJv());
    }

    public PstDivisionMapJv(String sOid) throws DBException {
        super(new PstDivisionMapJv(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDivisionMapJv(long lOid) throws DBException {
        super(new PstDivisionMapJv(0));
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
        return TBL_DIVISION_MAP_JV;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDivisionMapJv().getClass().getName();
    }

    public static DivisionMapJv fetchExc(long oid) throws DBException {
        try {
            DivisionMapJv entDivisionMapJv = new DivisionMapJv();
            PstDivisionMapJv pstDivisionMapJv = new PstDivisionMapJv(oid);
            entDivisionMapJv.setOID(oid);
            entDivisionMapJv.setDivisionId(pstDivisionMapJv.getLong(FLD_DIVISION_ID));
            entDivisionMapJv.setDivisionName(pstDivisionMapJv.getString(FLD_DIVISION_NAME));
            entDivisionMapJv.setDivisionCodeId(pstDivisionMapJv.getLong(FLD_DIVISION_CODE_ID));
            return entDivisionMapJv;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionMapJv(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DivisionMapJv entDivisionMapJv = fetchExc(entity.getOID());
        entity = (Entity) entDivisionMapJv;
        return entDivisionMapJv.getOID();
    }

    public static synchronized long updateExc(DivisionMapJv entDivisionMapJv) throws DBException {
        try {
            if (entDivisionMapJv.getOID() != 0) {
                PstDivisionMapJv pstDivisionMapJv = new PstDivisionMapJv(entDivisionMapJv.getOID());
                pstDivisionMapJv.setLong(FLD_DIVISION_ID, entDivisionMapJv.getDivisionId());
                pstDivisionMapJv.setString(FLD_DIVISION_NAME, entDivisionMapJv.getDivisionName());
                pstDivisionMapJv.setLong(FLD_DIVISION_CODE_ID, entDivisionMapJv.getDivisionCodeId());
                pstDivisionMapJv.update();
                return entDivisionMapJv.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionMapJv(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DivisionMapJv) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDivisionMapJv pstDivisionMapJv = new PstDivisionMapJv(oid);
            pstDivisionMapJv.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionMapJv(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DivisionMapJv entDivisionMapJv) throws DBException {
        try {
            PstDivisionMapJv pstDivisionMapJv = new PstDivisionMapJv(0);
            pstDivisionMapJv.setLong(FLD_DIVISION_ID, entDivisionMapJv.getDivisionId());
            pstDivisionMapJv.setString(FLD_DIVISION_NAME, entDivisionMapJv.getDivisionName());
            pstDivisionMapJv.setLong(FLD_DIVISION_CODE_ID, entDivisionMapJv.getDivisionCodeId());
            pstDivisionMapJv.insert();
            entDivisionMapJv.setOID(pstDivisionMapJv.getLong(FLD_DIVISION_MAP_JV_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionMapJv(0), DBException.UNKNOWN);
        }
        return entDivisionMapJv.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DivisionMapJv) entity);
    }

    public static void resultToObject(ResultSet rs, DivisionMapJv entDivisionMapJv) {
        try {
            entDivisionMapJv.setOID(rs.getLong(PstDivisionMapJv.fieldNames[PstDivisionMapJv.FLD_DIVISION_MAP_JV_ID]));
            entDivisionMapJv.setDivisionId(rs.getLong(PstDivisionMapJv.fieldNames[PstDivisionMapJv.FLD_DIVISION_ID]));
            entDivisionMapJv.setDivisionName(rs.getString(PstDivisionMapJv.fieldNames[PstDivisionMapJv.FLD_DIVISION_NAME]));
            entDivisionMapJv.setDivisionCodeId(rs.getLong(PstDivisionMapJv.fieldNames[PstDivisionMapJv.FLD_DIVISION_CODE_ID]));
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
            String sql = "SELECT * FROM " + TBL_DIVISION_MAP_JV;
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
                DivisionMapJv entDivisionMapJv = new DivisionMapJv();
                resultToObject(rs, entDivisionMapJv);
                lists.add(entDivisionMapJv);
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

    public static boolean checkOID(long entDivisionMapJvId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DIVISION_MAP_JV + " WHERE "
                    + PstDivisionMapJv.fieldNames[PstDivisionMapJv.FLD_DIVISION_MAP_JV_ID] + " = " + entDivisionMapJvId;
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
            String sql = "SELECT COUNT(" + PstDivisionMapJv.fieldNames[PstDivisionMapJv.FLD_DIVISION_MAP_JV_ID] + ") FROM " + TBL_DIVISION_MAP_JV;
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
                    DivisionMapJv entDivisionMapJv = (DivisionMapJv) list.get(ls);
                    if (oid == entDivisionMapJv.getOID()) {
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