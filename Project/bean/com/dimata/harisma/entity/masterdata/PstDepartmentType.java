/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 * @date 2015-08-04
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

public class PstDepartmentType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DEPARTMENT_TYPE = "hr_department_type";
    public static final int FLD_DEPARTMENT_TYPE_ID = 0;
    public static final int FLD_GROUP_TYPE = 1;
    public static final int FLD_TYPE_NAME = 2;
    public static final int FLD_DESCRIPTION = 3;
    public static final int FLD_LEVEL = 4;
    public static String[] fieldNames = {
        "DEPARTMENT_TYPE_ID",
        "GROUP_TYPE",
        "TYPE_NAME",
        "DESCRIPTION",
        "LEVEL"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT
    };

    public static String[] groupType = {
      "Regular", "Sub Branch"
    };
    
    public PstDepartmentType() {
    }

    public PstDepartmentType(int i) throws DBException {
        super(new PstDepartmentType());
    }

    public PstDepartmentType(String sOid) throws DBException {
        super(new PstDepartmentType(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDepartmentType(long lOid) throws DBException {
        super(new PstDepartmentType(0));
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
        return TBL_DEPARTMENT_TYPE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDepartmentType().getClass().getName();
    }

    public static DepartmentType fetchExc(long oid) throws DBException {
        try {
            DepartmentType entDepartmentType = new DepartmentType();
            PstDepartmentType pstDepartmentType = new PstDepartmentType(oid);
            entDepartmentType.setOID(oid);
            entDepartmentType.setGroupType(pstDepartmentType.getInt(FLD_GROUP_TYPE));
            entDepartmentType.setTypeName(pstDepartmentType.getString(FLD_TYPE_NAME));
            entDepartmentType.setDescription(pstDepartmentType.getString(FLD_DESCRIPTION));
            entDepartmentType.setLevel(pstDepartmentType.getInt(FLD_LEVEL));
            return entDepartmentType;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartmentType(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DepartmentType entDepartmentType = fetchExc(entity.getOID());
        entity = (Entity) entDepartmentType;
        return entDepartmentType.getOID();
    }

    public static synchronized long updateExc(DepartmentType entDepartmentType) throws DBException {
        try {
            if (entDepartmentType.getOID() != 0) {
                PstDepartmentType pstDepartmentType = new PstDepartmentType(entDepartmentType.getOID());
                pstDepartmentType.setInt(FLD_GROUP_TYPE, entDepartmentType.getGroupType());
                pstDepartmentType.setString(FLD_TYPE_NAME, entDepartmentType.getTypeName());
                pstDepartmentType.setString(FLD_DESCRIPTION, entDepartmentType.getDescription());
                pstDepartmentType.setInt(FLD_LEVEL, entDepartmentType.getLevel());
                pstDepartmentType.update();
                return entDepartmentType.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartmentType(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DepartmentType) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDepartmentType pstDepartmentType = new PstDepartmentType(oid);
            pstDepartmentType.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartmentType(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DepartmentType entDepartmentType) throws DBException {
        try {
            PstDepartmentType pstDepartmentType = new PstDepartmentType(0);
            pstDepartmentType.setInt(FLD_GROUP_TYPE, entDepartmentType.getGroupType());
            pstDepartmentType.setString(FLD_TYPE_NAME, entDepartmentType.getTypeName());
            pstDepartmentType.setString(FLD_DESCRIPTION, entDepartmentType.getDescription());
            pstDepartmentType.setInt(FLD_LEVEL, entDepartmentType.getLevel());
            pstDepartmentType.insert();
            entDepartmentType.setOID(pstDepartmentType.getLong(FLD_DEPARTMENT_TYPE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDepartmentType(0), DBException.UNKNOWN);
        }
        return entDepartmentType.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DepartmentType) entity);
    }

    public static void resultToObject(ResultSet rs, DepartmentType entDepartmentType) {
        try {
            entDepartmentType.setOID(rs.getLong(PstDepartmentType.fieldNames[PstDepartmentType.FLD_DEPARTMENT_TYPE_ID]));
            entDepartmentType.setGroupType(rs.getInt(PstDepartmentType.fieldNames[PstDepartmentType.FLD_GROUP_TYPE]));
            entDepartmentType.setTypeName(rs.getString(PstDepartmentType.fieldNames[PstDepartmentType.FLD_TYPE_NAME]));
            entDepartmentType.setDescription(rs.getString(PstDepartmentType.fieldNames[PstDepartmentType.FLD_DESCRIPTION]));
            entDepartmentType.setLevel(rs.getInt(PstDepartmentType.fieldNames[PstDepartmentType.FLD_LEVEL]));
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
            String sql = "SELECT * FROM " + TBL_DEPARTMENT_TYPE;
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
                DepartmentType entDepartmentType = new DepartmentType();
                resultToObject(rs, entDepartmentType);
                lists.add(entDepartmentType);
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

    public static boolean checkOID(long entDepartmentTypeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DEPARTMENT_TYPE + " WHERE "
                    + PstDepartmentType.fieldNames[PstDepartmentType.FLD_DEPARTMENT_TYPE_ID] + " = " + entDepartmentTypeId;
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
            String sql = "SELECT COUNT(" + PstDepartmentType.fieldNames[PstDepartmentType.FLD_DEPARTMENT_TYPE_ID] + ") FROM " + TBL_DEPARTMENT_TYPE;
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
                    DepartmentType entDepartmentType = (DepartmentType) list.get(ls);
                    if (oid == entDepartmentType.getOID()) {
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
