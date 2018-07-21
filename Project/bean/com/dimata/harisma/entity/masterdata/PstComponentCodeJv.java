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

public class PstComponentCodeJv extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_COMPONENT_CODE_JV = "hr_component_code_jv";
    public static final int FLD_COMP_CODE_ID = 0;
    public static final int FLD_COMP_CODE = 1;
    public static String[] fieldNames = {
        "COMP_CODE_ID",
        "COMP_CODE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING
    };

    public PstComponentCodeJv() {
    }

    public PstComponentCodeJv(int i) throws DBException {
        super(new PstComponentCodeJv());
    }

    public PstComponentCodeJv(String sOid) throws DBException {
        super(new PstComponentCodeJv(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstComponentCodeJv(long lOid) throws DBException {
        super(new PstComponentCodeJv(0));
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
        return TBL_COMPONENT_CODE_JV;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstComponentCodeJv().getClass().getName();
    }

    public static ComponentCodeJv fetchExc(long oid) throws DBException {
        try {
            ComponentCodeJv entComponentCodeJv = new ComponentCodeJv();
            PstComponentCodeJv pstComponentCodeJv = new PstComponentCodeJv(oid);
            entComponentCodeJv.setOID(oid);
            entComponentCodeJv.setCompCode(pstComponentCodeJv.getString(FLD_COMP_CODE));
            return entComponentCodeJv;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCodeJv(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        ComponentCodeJv entComponentCodeJv = fetchExc(entity.getOID());
        entity = (Entity) entComponentCodeJv;
        return entComponentCodeJv.getOID();
    }

    public static synchronized long updateExc(ComponentCodeJv entComponentCodeJv) throws DBException {
        try {
            if (entComponentCodeJv.getOID() != 0) {
                PstComponentCodeJv pstComponentCodeJv = new PstComponentCodeJv(entComponentCodeJv.getOID());
                pstComponentCodeJv.setString(FLD_COMP_CODE, entComponentCodeJv.getCompCode());
                pstComponentCodeJv.update();
                return entComponentCodeJv.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCodeJv(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((ComponentCodeJv) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstComponentCodeJv pstComponentCodeJv = new PstComponentCodeJv(oid);
            pstComponentCodeJv.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCodeJv(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(ComponentCodeJv entComponentCodeJv) throws DBException {
        try {
            PstComponentCodeJv pstComponentCodeJv = new PstComponentCodeJv(0);
            pstComponentCodeJv.setString(FLD_COMP_CODE, entComponentCodeJv.getCompCode());
            pstComponentCodeJv.insert();
            entComponentCodeJv.setOID(pstComponentCodeJv.getLong(FLD_COMP_CODE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCodeJv(0), DBException.UNKNOWN);
        }
        return entComponentCodeJv.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((ComponentCodeJv) entity);
    }

    public static void resultToObject(ResultSet rs, ComponentCodeJv entComponentCodeJv) {
        try {
            entComponentCodeJv.setOID(rs.getLong(PstComponentCodeJv.fieldNames[PstComponentCodeJv.FLD_COMP_CODE_ID]));
            entComponentCodeJv.setCompCode(rs.getString(PstComponentCodeJv.fieldNames[PstComponentCodeJv.FLD_COMP_CODE]));
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
            String sql = "SELECT * FROM " + TBL_COMPONENT_CODE_JV;
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
                ComponentCodeJv entComponentCodeJv = new ComponentCodeJv();
                resultToObject(rs, entComponentCodeJv);
                lists.add(entComponentCodeJv);
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

    public static boolean checkOID(long entComponentCodeJvId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_COMPONENT_CODE_JV + " WHERE "
                    + PstComponentCodeJv.fieldNames[PstComponentCodeJv.FLD_COMP_CODE_ID] + " = " + entComponentCodeJvId;
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
            String sql = "SELECT COUNT(" + PstComponentCodeJv.fieldNames[PstComponentCodeJv.FLD_COMP_CODE_ID] + ") FROM " + TBL_COMPONENT_CODE_JV;
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
                    ComponentCodeJv entComponentCodeJv = (ComponentCodeJv) list.get(ls);
                    if (oid == entComponentCodeJv.getOID()) {
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