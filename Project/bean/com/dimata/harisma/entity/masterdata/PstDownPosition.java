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

public class PstDownPosition extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DOWN_POSITION = "hr_managing_position";
    public static final int FLD_DOWN_POSITION_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_POSITION_DOWNLINK = 2;
    public static final int FLD_TYPE_OF_LINK = 3;
    public static String[] fieldNames = {
        "MNG_POSITION_ID",
        "TOP_POSITION_ID",
        "POSITION_ID",
        "TYPE_OF_TOP_LINK"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

    public PstDownPosition() {
    }

    public PstDownPosition(int i) throws DBException {
        super(new PstDownPosition());
    }

    public PstDownPosition(String sOid) throws DBException {
        super(new PstDownPosition(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDownPosition(long lOid) throws DBException {
        super(new PstDownPosition(0));
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
        return TBL_DOWN_POSITION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDownPosition().getClass().getName();
    }

    public static DownPosition fetchExc(long oid) throws DBException {
        try {
            DownPosition entDownPosition = new DownPosition();
            PstDownPosition pstDownPosition = new PstDownPosition(oid);
            entDownPosition.setOID(oid);
            entDownPosition.setPositionId(pstDownPosition.getLong(FLD_POSITION_ID));
            entDownPosition.setPositionDownlink(pstDownPosition.getLong(FLD_POSITION_DOWNLINK));
            entDownPosition.setTypeOfLink(pstDownPosition.getInt(FLD_TYPE_OF_LINK));
            return entDownPosition;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDownPosition(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DownPosition entDownPosition = fetchExc(entity.getOID());
        entity = (Entity) entDownPosition;
        return entDownPosition.getOID();
    }

    public static synchronized long updateExc(DownPosition entDownPosition) throws DBException {
        try {
            if (entDownPosition.getOID() != 0) {
                PstDownPosition pstDownPosition = new PstDownPosition(entDownPosition.getOID());
                pstDownPosition.setLong(FLD_POSITION_ID, entDownPosition.getPositionId());
                pstDownPosition.setLong(FLD_POSITION_DOWNLINK, entDownPosition.getPositionDownlink());
                pstDownPosition.setInt(FLD_TYPE_OF_LINK, entDownPosition.getTypeOfLink());
                pstDownPosition.update();
                return entDownPosition.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDownPosition(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DownPosition) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDownPosition pstDownPosition = new PstDownPosition(oid);
            pstDownPosition.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDownPosition(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DownPosition entDownPosition) throws DBException {
        try {
            PstDownPosition pstDownPosition = new PstDownPosition(0);
            pstDownPosition.setLong(FLD_POSITION_ID, entDownPosition.getPositionId());
            pstDownPosition.setLong(FLD_POSITION_DOWNLINK, entDownPosition.getPositionDownlink());
            pstDownPosition.setInt(FLD_TYPE_OF_LINK, entDownPosition.getTypeOfLink());
            pstDownPosition.insert();
            entDownPosition.setOID(pstDownPosition.getLong(FLD_DOWN_POSITION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDownPosition(0), DBException.UNKNOWN);
        }
        return entDownPosition.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DownPosition) entity);
    }

    public static void resultToObject(ResultSet rs, DownPosition entDownPosition) {
        try {
            entDownPosition.setOID(rs.getLong(PstDownPosition.fieldNames[PstDownPosition.FLD_DOWN_POSITION_ID]));
            entDownPosition.setPositionId(rs.getLong(PstDownPosition.fieldNames[PstDownPosition.FLD_POSITION_ID]));
            entDownPosition.setPositionDownlink(rs.getLong(PstDownPosition.fieldNames[PstDownPosition.FLD_POSITION_DOWNLINK]));
            entDownPosition.setTypeOfLink(rs.getInt(PstDownPosition.fieldNames[PstDownPosition.FLD_TYPE_OF_LINK]));
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
            String sql = "SELECT * FROM " + TBL_DOWN_POSITION;
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
                DownPosition entDownPosition = new DownPosition();
                resultToObject(rs, entDownPosition);
                lists.add(entDownPosition);
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

    public static boolean checkOID(long entDownPositionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DOWN_POSITION + " WHERE "
                    + PstDownPosition.fieldNames[PstDownPosition.FLD_DOWN_POSITION_ID] + " = " + entDownPositionId;
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
            String sql = "SELECT COUNT(" + PstDownPosition.fieldNames[PstDownPosition.FLD_DOWN_POSITION_ID] + ") FROM " + TBL_DOWN_POSITION;
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
                    DownPosition entDownPosition = (DownPosition) list.get(ls);
                    if (oid == entDownPosition.getOID()) {
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