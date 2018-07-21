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

public class PstComponentMapJv extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_COMPONENT_MAP_JV = "hr_component_map_jv";
    public static final int FLD_COMPONENT_MAP_JV_ID = 0;
    public static final int FLD_COMPONENT_ID = 1;
    public static final int FLD_COMPONENT_NAME = 2;
    public static final int FLD_COMP_CODE_ID = 3;
    public static String[] fieldNames = {
        "COMPONENT_MAP_JV_ID",
        "COMPONENT_ID",
        "COMPONENT_NAME",
        "COMP_CODE_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG
    };

    public PstComponentMapJv() {
    }

    public PstComponentMapJv(int i) throws DBException {
        super(new PstComponentMapJv());
    }

    public PstComponentMapJv(String sOid) throws DBException {
        super(new PstComponentMapJv(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstComponentMapJv(long lOid) throws DBException {
        super(new PstComponentMapJv(0));
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
        return TBL_COMPONENT_MAP_JV;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstComponentMapJv().getClass().getName();
    }

    public static ComponentMapJv fetchExc(long oid) throws DBException {
        try {
            ComponentMapJv entComponentMapJv = new ComponentMapJv();
            PstComponentMapJv pstComponentMapJv = new PstComponentMapJv(oid);
            entComponentMapJv.setOID(oid);
            entComponentMapJv.setComponentId(pstComponentMapJv.getLong(FLD_COMPONENT_ID));
            entComponentMapJv.setComponentName(pstComponentMapJv.getString(FLD_COMPONENT_NAME));
            entComponentMapJv.setCompCodeId(pstComponentMapJv.getLong(FLD_COMP_CODE_ID));
            return entComponentMapJv;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentMapJv(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        ComponentMapJv entComponentMapJv = fetchExc(entity.getOID());
        entity = (Entity) entComponentMapJv;
        return entComponentMapJv.getOID();
    }

    public static synchronized long updateExc(ComponentMapJv entComponentMapJv) throws DBException {
        try {
            if (entComponentMapJv.getOID() != 0) {
                PstComponentMapJv pstComponentMapJv = new PstComponentMapJv(entComponentMapJv.getOID());
                pstComponentMapJv.setLong(FLD_COMPONENT_ID, entComponentMapJv.getComponentId());
                pstComponentMapJv.setString(FLD_COMPONENT_NAME, entComponentMapJv.getComponentName());
                pstComponentMapJv.setLong(FLD_COMP_CODE_ID, entComponentMapJv.getCompCodeId());
                pstComponentMapJv.update();
                return entComponentMapJv.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentMapJv(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((ComponentMapJv) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstComponentMapJv pstComponentMapJv = new PstComponentMapJv(oid);
            pstComponentMapJv.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentMapJv(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(ComponentMapJv entComponentMapJv) throws DBException {
        try {
            PstComponentMapJv pstComponentMapJv = new PstComponentMapJv(0);
            pstComponentMapJv.setLong(FLD_COMPONENT_ID, entComponentMapJv.getComponentId());
            pstComponentMapJv.setString(FLD_COMPONENT_NAME, entComponentMapJv.getComponentName());
            pstComponentMapJv.setLong(FLD_COMP_CODE_ID, entComponentMapJv.getCompCodeId());
            pstComponentMapJv.insert();
            entComponentMapJv.setOID(pstComponentMapJv.getLong(FLD_COMPONENT_MAP_JV_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentMapJv(0), DBException.UNKNOWN);
        }
        return entComponentMapJv.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((ComponentMapJv) entity);
    }

    public static void resultToObject(ResultSet rs, ComponentMapJv entComponentMapJv) {
        try {
            entComponentMapJv.setOID(rs.getLong(PstComponentMapJv.fieldNames[PstComponentMapJv.FLD_COMPONENT_MAP_JV_ID]));
            entComponentMapJv.setComponentId(rs.getLong(PstComponentMapJv.fieldNames[PstComponentMapJv.FLD_COMPONENT_ID]));
            entComponentMapJv.setComponentName(rs.getString(PstComponentMapJv.fieldNames[PstComponentMapJv.FLD_COMPONENT_NAME]));
            entComponentMapJv.setCompCodeId(rs.getLong(PstComponentMapJv.fieldNames[PstComponentMapJv.FLD_COMP_CODE_ID]));
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
            String sql = "SELECT * FROM " + TBL_COMPONENT_MAP_JV;
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
                ComponentMapJv entComponentMapJv = new ComponentMapJv();
                resultToObject(rs, entComponentMapJv);
                lists.add(entComponentMapJv);
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

    public static boolean checkOID(long entComponentMapJvId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_COMPONENT_MAP_JV + " WHERE "
                    + PstComponentMapJv.fieldNames[PstComponentMapJv.FLD_COMPONENT_MAP_JV_ID] + " = " + entComponentMapJvId;
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
            String sql = "SELECT COUNT(" + PstComponentMapJv.fieldNames[PstComponentMapJv.FLD_COMPONENT_MAP_JV_ID] + ") FROM " + TBL_COMPONENT_MAP_JV;
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
                    ComponentMapJv entComponentMapJv = (ComponentMapJv) list.get(ls);
                    if (oid == entComponentMapJv.getOID()) {
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