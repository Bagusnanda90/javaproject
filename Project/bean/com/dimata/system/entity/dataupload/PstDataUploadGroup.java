/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.entity.dataupload;

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
 * @author khirayinnura
 */
public class PstDataUploadGroup extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DSJ_DATA_GROUP = "dsj_data_group";
    public static final int FLD_DATA_GROUP_ID = 0;
    public static final int FLD_DATA_GROUP_TITLE = 1;
    public static final int FLD_DATA_GROUP_DESC = 2;
    public static final int FLD_DATA_GROUP_TIPE = 3;
    public static final int FLD_SYSTEM_NAME = 4;
    public static final int FLD_MODUL = 5;
    public static String[] fieldNames = {
        "DATA_GROUP_ID",
        "DATA_GROUP_TITLE",
        "DATA_GROUP_DESC",
        "DATA_GROUP_TIPE",
        "SYSTEM_NAME",
        "MODUL"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };

    public PstDataUploadGroup() {
    }

    public PstDataUploadGroup(int i) throws DBException {
        super(new PstDataUploadGroup());
    }

    public PstDataUploadGroup(String sOid) throws DBException {
        super(new PstDataUploadGroup(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDataUploadGroup(long lOid) throws DBException {
        super(new PstDataUploadGroup(0));
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
        return TBL_DSJ_DATA_GROUP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDataUploadGroup().getClass().getName();
    }

    public static DataUploadGroup fetchExc(long oid) throws DBException {
        try {
            DataUploadGroup entDataUploadGroup = new DataUploadGroup();
            PstDataUploadGroup pstDataUploadGroup = new PstDataUploadGroup(oid);
            entDataUploadGroup.setOID(oid);
            entDataUploadGroup.setDataGroupTitle(pstDataUploadGroup.getString(FLD_DATA_GROUP_TITLE));
            entDataUploadGroup.setDataGroupDesc(pstDataUploadGroup.getString(FLD_DATA_GROUP_DESC));
            entDataUploadGroup.setDataGroupTipe(pstDataUploadGroup.getInt(FLD_DATA_GROUP_TIPE));
            entDataUploadGroup.setSystemName(pstDataUploadGroup.getInt(FLD_SYSTEM_NAME));
            entDataUploadGroup.setModul(pstDataUploadGroup.getInt(FLD_MODUL));
            return entDataUploadGroup;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadGroup(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DataUploadGroup entDataUploadGroup = fetchExc(entity.getOID());
        entity = (Entity) entDataUploadGroup;
        return entDataUploadGroup.getOID();
    }

    public static synchronized long updateExc(DataUploadGroup entDataUploadGroup) throws DBException {
        try {
            if (entDataUploadGroup.getOID() != 0) {
                PstDataUploadGroup pstDataUploadGroup = new PstDataUploadGroup(entDataUploadGroup.getOID());
                pstDataUploadGroup.setString(FLD_DATA_GROUP_TITLE, entDataUploadGroup.getDataGroupTitle());
                pstDataUploadGroup.setString(FLD_DATA_GROUP_DESC, entDataUploadGroup.getDataGroupDesc());
                pstDataUploadGroup.setInt(FLD_DATA_GROUP_TIPE, entDataUploadGroup.getDataGroupTipe());
                pstDataUploadGroup.setInt(FLD_SYSTEM_NAME, entDataUploadGroup.getSystemName());
                pstDataUploadGroup.setInt(FLD_MODUL, entDataUploadGroup.getModul());
                pstDataUploadGroup.update();
                return entDataUploadGroup.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadGroup(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DataUploadGroup) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDataUploadGroup pstDataUploadGroup = new PstDataUploadGroup(oid);
            pstDataUploadGroup.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadGroup(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DataUploadGroup entDataUploadGroup) throws DBException {
        try {
            PstDataUploadGroup pstDataUploadGroup = new PstDataUploadGroup(0);
            pstDataUploadGroup.setString(FLD_DATA_GROUP_TITLE, entDataUploadGroup.getDataGroupTitle());
            pstDataUploadGroup.setString(FLD_DATA_GROUP_DESC, entDataUploadGroup.getDataGroupDesc());
            pstDataUploadGroup.setInt(FLD_DATA_GROUP_TIPE, entDataUploadGroup.getDataGroupTipe());
            pstDataUploadGroup.setInt(FLD_SYSTEM_NAME, entDataUploadGroup.getSystemName());
            pstDataUploadGroup.setInt(FLD_MODUL, entDataUploadGroup.getModul());
            pstDataUploadGroup.insert();
            entDataUploadGroup.setOID(pstDataUploadGroup.getlong(FLD_DATA_GROUP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadGroup(0), DBException.UNKNOWN);
        }
        return entDataUploadGroup.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DataUploadGroup) entity);
    }

    public static void resultToObject(ResultSet rs, DataUploadGroup entDataUploadGroup) {
        try {
            entDataUploadGroup.setOID(rs.getLong(PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_DATA_GROUP_ID]));
            entDataUploadGroup.setDataGroupTitle(rs.getString(PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_DATA_GROUP_TITLE]));
            entDataUploadGroup.setDataGroupDesc(rs.getString(PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_DATA_GROUP_DESC]));
            entDataUploadGroup.setDataGroupTipe(rs.getInt(PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_DATA_GROUP_TIPE]));
            entDataUploadGroup.setSystemName(rs.getInt(PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_SYSTEM_NAME]));
            entDataUploadGroup.setModul(rs.getInt(PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_MODUL]));
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
            String sql = "SELECT * FROM " + TBL_DSJ_DATA_GROUP;
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
                DataUploadGroup entDataUploadGroup = new DataUploadGroup();
                resultToObject(rs, entDataUploadGroup);
                lists.add(entDataUploadGroup);
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

    public static boolean checkOID(long entDataUploadGroupId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DSJ_DATA_GROUP + " WHERE "
                    + PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_DATA_GROUP_ID] + " = " + entDataUploadGroupId;
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
            String sql = "SELECT COUNT(" + PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_DATA_GROUP_ID] + ") FROM " + TBL_DSJ_DATA_GROUP;
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
                    DataUploadGroup entDataUploadGroup = (DataUploadGroup) list.get(ls);
                    if (oid == entDataUploadGroup.getOID()) {
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
