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
public class PstDataUploadMain extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DSJ_DATA_MAIN = "dsj_data_main";
    public static final int FLD_DATA_MAIN_ID = 0;
    public static final int FLD_OBJECT_ID = 1;
    public static final int FLD_OBJECT_CLASS = 2;
    public static final int FLD_DATA_MAIN_TITLE = 3;
    public static final int FLD_DATA_MAIN_DESC = 4;
    public static final int FLD_DATA_GROUP_ID = 5;
    public static String[] fieldNames = {
        "DATA_MAIN_ID",
        "OBJECT_ID",
        "OBJECT_CLASS",
        "DATA_MAIN_TITLE",
        "DATA_MAIN_DESC",
        "DATA_GROUP_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };

    public PstDataUploadMain() {
    }

    public PstDataUploadMain(int i) throws DBException {
        super(new PstDataUploadMain());
    }

    public PstDataUploadMain(String sOid) throws DBException {
        super(new PstDataUploadMain(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDataUploadMain(long lOid) throws DBException {
        super(new PstDataUploadMain(0));
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
        return TBL_DSJ_DATA_MAIN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDataUploadMain().getClass().getName();
    }

    public static DataUploadMain fetchExc(long oid) throws DBException {
        try {
            DataUploadMain entDataUploadMain = new DataUploadMain();
            PstDataUploadMain pstDataUploadMain = new PstDataUploadMain(oid);
            entDataUploadMain.setOID(oid);
            entDataUploadMain.setObjectId(pstDataUploadMain.getLong(FLD_OBJECT_ID));
            entDataUploadMain.setObjectClass(pstDataUploadMain.getString(FLD_OBJECT_CLASS));
            entDataUploadMain.setDataMainTitle(pstDataUploadMain.getString(FLD_DATA_MAIN_TITLE));
            entDataUploadMain.setDataMainDesc(pstDataUploadMain.getString(FLD_DATA_MAIN_DESC));
            entDataUploadMain.setDataGroupId(pstDataUploadMain.getLong(FLD_DATA_GROUP_ID));
            return entDataUploadMain;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadMain(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DataUploadMain entDataUploadMain = fetchExc(entity.getOID());
        entity = (Entity) entDataUploadMain;
        return entDataUploadMain.getOID();
    }

    public static synchronized long updateExc(DataUploadMain entDataUploadMain) throws DBException {
        try {
            if (entDataUploadMain.getOID() != 0) {
                PstDataUploadMain pstDataUploadMain = new PstDataUploadMain(entDataUploadMain.getOID());
                pstDataUploadMain.setLong(FLD_OBJECT_ID, entDataUploadMain.getObjectId());
                pstDataUploadMain.setString(FLD_OBJECT_CLASS, entDataUploadMain.getObjectClass());
                pstDataUploadMain.setString(FLD_DATA_MAIN_TITLE, entDataUploadMain.getDataMainTitle());
                pstDataUploadMain.setString(FLD_DATA_MAIN_DESC, entDataUploadMain.getDataMainDesc());
                pstDataUploadMain.setLong(FLD_DATA_GROUP_ID, entDataUploadMain.getDataGroupId());
                pstDataUploadMain.update();
                return entDataUploadMain.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadMain(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DataUploadMain) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDataUploadMain pstDataUploadMain = new PstDataUploadMain(oid);
            pstDataUploadMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadMain(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DataUploadMain entDataUploadMain) throws DBException {
        try {
            PstDataUploadMain pstDataUploadMain = new PstDataUploadMain(0);
            pstDataUploadMain.setLong(FLD_OBJECT_ID, entDataUploadMain.getObjectId());
            pstDataUploadMain.setString(FLD_OBJECT_CLASS, entDataUploadMain.getObjectClass());
            pstDataUploadMain.setString(FLD_DATA_MAIN_TITLE, entDataUploadMain.getDataMainTitle());
            pstDataUploadMain.setString(FLD_DATA_MAIN_DESC, entDataUploadMain.getDataMainDesc());
            pstDataUploadMain.setLong(FLD_DATA_GROUP_ID, entDataUploadMain.getDataGroupId());
            pstDataUploadMain.insert();
            entDataUploadMain.setOID(pstDataUploadMain.getLong(FLD_DATA_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadMain(0), DBException.UNKNOWN);
        }
        return entDataUploadMain.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DataUploadMain) entity);
    }

    public static void resultToObject(ResultSet rs, DataUploadMain entDataUploadMain) {
        try {
            entDataUploadMain.setOID(rs.getLong(PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_DATA_MAIN_ID]));
            entDataUploadMain.setObjectId(rs.getLong(PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_OBJECT_ID]));
            entDataUploadMain.setObjectClass(rs.getString(PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_OBJECT_CLASS]));
            entDataUploadMain.setDataMainTitle(rs.getString(PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_DATA_MAIN_TITLE]));
            entDataUploadMain.setDataMainDesc(rs.getString(PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_DATA_MAIN_DESC]));
            entDataUploadMain.setDataGroupId(rs.getLong(PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_DATA_GROUP_ID]));
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
            String sql = "SELECT * FROM " + TBL_DSJ_DATA_MAIN;
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
                DataUploadMain entDataUploadMain = new DataUploadMain();
                resultToObject(rs, entDataUploadMain);
                lists.add(entDataUploadMain);
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

    public static boolean checkOID(long entDataUploadMainId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DSJ_DATA_MAIN + " WHERE "
                    + PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_DATA_MAIN_ID] + " = " + entDataUploadMainId;
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
            String sql = "SELECT COUNT(" + PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_DATA_MAIN_ID] + ") FROM " + TBL_DSJ_DATA_MAIN;
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
                    DataUploadMain entDataUploadMain = (DataUploadMain) list.get(ls);
                    if (oid == entDataUploadMain.getOID()) {
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
