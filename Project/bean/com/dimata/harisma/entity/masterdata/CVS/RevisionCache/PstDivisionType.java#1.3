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
import java.util.Hashtable;
import java.util.Vector;

public class PstDivisionType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DIVISION_TYPE = "hr_division_type";
    public static final int FLD_DIVISION_TYPE_ID = 0;
    public static final int FLD_GROUP_TYPE = 1;
    public static final int FLD_TYPE_NAME = 2;
    public static final int FLD_DESCRIPTION = 3;
    public static final int FLD_LEVEL = 4;
    public static String[] fieldNames = {
        "DIVISION_TYPE_ID",
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

    public static final int TYPE_DIVISION = 0;
    public static final int TYPE_BRANCH_OF_COMPANY = 1;
    public static final int TYPE_BOD = 2;
    public static final int TYPE_SHAREHOLDER = 3;
    public static final int TYPE_COMPANY_MEDIA = 4;
    
    public static String[] groupType = {
      "Division", "Branch Of Company", "BOD", "Shareholder", "Company Media"
    };
    
    public PstDivisionType() {
    }

    public PstDivisionType(int i) throws DBException {
        super(new PstDivisionType());
    }

    public PstDivisionType(String sOid) throws DBException {
        super(new PstDivisionType(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDivisionType(long lOid) throws DBException {
        super(new PstDivisionType(0));
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
        return TBL_DIVISION_TYPE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDivisionType().getClass().getName();
    }

    public static DivisionType fetchExc(long oid) throws DBException {
        try {
            DivisionType entDivisionType = new DivisionType();
            PstDivisionType pstDivisionType = new PstDivisionType(oid);
            entDivisionType.setOID(oid);
            entDivisionType.setGroupType(pstDivisionType.getInt(FLD_GROUP_TYPE));
            entDivisionType.setTypeName(pstDivisionType.getString(FLD_TYPE_NAME));
            entDivisionType.setDescription(pstDivisionType.getString(FLD_DESCRIPTION));
            entDivisionType.setLevel(pstDivisionType.getInt(FLD_LEVEL));
            return entDivisionType;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionType(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DivisionType entDivisionType = fetchExc(entity.getOID());
        entity = (Entity) entDivisionType;
        return entDivisionType.getOID();
    }

    public static synchronized long updateExc(DivisionType entDivisionType) throws DBException {
        try {
            if (entDivisionType.getOID() != 0) {
                PstDivisionType pstDivisionType = new PstDivisionType(entDivisionType.getOID());
                pstDivisionType.setInt(FLD_GROUP_TYPE, entDivisionType.getGroupType());
                pstDivisionType.setString(FLD_TYPE_NAME, entDivisionType.getTypeName());
                pstDivisionType.setString(FLD_DESCRIPTION, entDivisionType.getDescription());
                pstDivisionType.setInt(FLD_LEVEL, entDivisionType.getLevel());
                pstDivisionType.update();
                return entDivisionType.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionType(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DivisionType) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDivisionType pstDivisionType = new PstDivisionType(oid);
            pstDivisionType.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionType(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DivisionType entDivisionType) throws DBException {
        try {
            PstDivisionType pstDivisionType = new PstDivisionType(0);
            pstDivisionType.setInt(FLD_GROUP_TYPE, entDivisionType.getGroupType());
            pstDivisionType.setString(FLD_TYPE_NAME, entDivisionType.getTypeName());
            pstDivisionType.setString(FLD_DESCRIPTION, entDivisionType.getDescription());
            pstDivisionType.setInt(FLD_LEVEL, entDivisionType.getLevel());
            pstDivisionType.insert();
            entDivisionType.setOID(pstDivisionType.getLong(FLD_DIVISION_TYPE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDivisionType(0), DBException.UNKNOWN);
        }
        return entDivisionType.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DivisionType) entity);
    }

    public static void resultToObject(ResultSet rs, DivisionType entDivisionType) {
        try {
            entDivisionType.setOID(rs.getLong(PstDivisionType.fieldNames[PstDivisionType.FLD_DIVISION_TYPE_ID]));
            entDivisionType.setGroupType(rs.getInt(PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]));
            entDivisionType.setTypeName(rs.getString(PstDivisionType.fieldNames[PstDivisionType.FLD_TYPE_NAME]));
            entDivisionType.setDescription(rs.getString(PstDivisionType.fieldNames[PstDivisionType.FLD_DESCRIPTION]));
            entDivisionType.setLevel(rs.getInt(PstDivisionType.fieldNames[PstDivisionType.FLD_LEVEL]));
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
            String sql = "SELECT * FROM " + TBL_DIVISION_TYPE;
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
                DivisionType entDivisionType = new DivisionType();
                resultToObject(rs, entDivisionType);
                lists.add(entDivisionType);
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
    
    
    public static Hashtable<String, DivisionType> listMap(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable<String, DivisionType> lists = new Hashtable<String, DivisionType>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_DIVISION_TYPE;
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
                DivisionType entDivisionType = new DivisionType();
                resultToObject(rs, entDivisionType);
                lists.put(""+entDivisionType.getOID(), entDivisionType);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable<String, DivisionType>();
    }

    public static boolean checkOID(long entDivisionTypeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DIVISION_TYPE + " WHERE "
                    + PstDivisionType.fieldNames[PstDivisionType.FLD_DIVISION_TYPE_ID] + " = " + entDivisionTypeId;
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
            String sql = "SELECT COUNT(" + PstDivisionType.fieldNames[PstDivisionType.FLD_DIVISION_TYPE_ID] + ") FROM " + TBL_DIVISION_TYPE;
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
                    DivisionType entDivisionType = (DivisionType) list.get(ls);
                    if (oid == entDivisionType.getOID()) {
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
