/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 * Date : 2015-07-29
 * @author Hendra Putu
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

public class PstPositionDepartment extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_DEPARTMENT = "hr_position_department";
    public static final int FLD_POSITION_DEPARTMENT_ID = 0;
    public static final int FLD_DEPARTMENT_ID = 1;
    public static final int FLD_POSITION_ID = 2;
    public static String[] fieldNames = {
        "POSITION_DEPARTMENT_ID",
        "DEPARTMENT_ID",
        "POSITION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstPositionDepartment() {
    }

    public PstPositionDepartment(int i) throws DBException {
        super(new PstPositionDepartment());
    }

    public PstPositionDepartment(String sOid) throws DBException {
        super(new PstPositionDepartment(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionDepartment(long lOid) throws DBException {
        super(new PstPositionDepartment(0));
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
        return TBL_POSITION_DEPARTMENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionDepartment().getClass().getName();
    }

    public static PositionDepartment fetchExc(long oid) throws DBException {
        try {
            PositionDepartment entPositionDepartment = new PositionDepartment();
            PstPositionDepartment pstPositionDepartment = new PstPositionDepartment(oid);
            entPositionDepartment.setOID(oid);
            entPositionDepartment.setDepartmentId(pstPositionDepartment.getLong(FLD_DEPARTMENT_ID));
            entPositionDepartment.setPositionId(pstPositionDepartment.getLong(FLD_POSITION_ID));
            return entPositionDepartment;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionDepartment(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionDepartment entPositionDepartment = fetchExc(entity.getOID());
        entity = (Entity) entPositionDepartment;
        return entPositionDepartment.getOID();
    }

    public static synchronized long updateExc(PositionDepartment entPositionDepartment) throws DBException {
        try {
            if (entPositionDepartment.getOID() != 0) {
                PstPositionDepartment pstPositionDepartment = new PstPositionDepartment(entPositionDepartment.getOID());
                pstPositionDepartment.setLong(FLD_DEPARTMENT_ID, entPositionDepartment.getDepartmentId());
                pstPositionDepartment.setLong(FLD_POSITION_ID, entPositionDepartment.getPositionId());
                pstPositionDepartment.update();
                return entPositionDepartment.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionDepartment(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionDepartment) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionDepartment pstPositionDepartment = new PstPositionDepartment(oid);
            pstPositionDepartment.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionDepartment(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionDepartment entPositionDepartment) throws DBException {
        try {
            PstPositionDepartment pstPositionDepartment = new PstPositionDepartment(0);
            pstPositionDepartment.setLong(FLD_DEPARTMENT_ID, entPositionDepartment.getDepartmentId());
            pstPositionDepartment.setLong(FLD_POSITION_ID, entPositionDepartment.getPositionId());
            pstPositionDepartment.insert();
            entPositionDepartment.setOID(pstPositionDepartment.getLong(FLD_POSITION_DEPARTMENT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionDepartment(0), DBException.UNKNOWN);
        }
        return entPositionDepartment.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionDepartment) entity);
    }

    public static void resultToObject(ResultSet rs, PositionDepartment entPositionDepartment) {
        try {
            entPositionDepartment.setOID(rs.getLong(PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_POSITION_DEPARTMENT_ID]));
            entPositionDepartment.setDepartmentId(rs.getLong(PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_DEPARTMENT_ID]));
            entPositionDepartment.setPositionId(rs.getLong(PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_POSITION_ID]));
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
            String sql = "SELECT * FROM " + TBL_POSITION_DEPARTMENT;
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
                PositionDepartment entPositionDepartment = new PositionDepartment();
                resultToObject(rs, entPositionDepartment);
                lists.add(entPositionDepartment);
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

    public static boolean checkOID(long entPositionDepartmentId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_DEPARTMENT + " WHERE "
                    + PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_POSITION_DEPARTMENT_ID] + " = " + entPositionDepartmentId;
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
            String sql = "SELECT COUNT(" + PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_POSITION_DEPARTMENT_ID] + ") FROM " + TBL_POSITION_DEPARTMENT;
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
                    PositionDepartment entPositionDepartment = (PositionDepartment) list.get(ls);
                    if (oid == entPositionDepartment.getOID()) {
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
