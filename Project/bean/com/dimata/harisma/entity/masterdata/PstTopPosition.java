/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.employee.PstEmployee;
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

public class PstTopPosition extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TOP_POSITION = "hr_managing_position";
    public static final int FLD_TOP_POSITION_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_POSITION_TOPLINK = 2;
    public static final int FLD_TYPE_OF_LINK = 3;
    public static String[] fieldNames = {
        "MNG_POSITION_ID",
        "POSITION_ID",
        "TOP_POSITION_ID",
        "TYPE_OF_TOP_LINK"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

    public PstTopPosition() {
    }

    public PstTopPosition(int i) throws DBException {
        super(new PstTopPosition());
    }

    public PstTopPosition(String sOid) throws DBException {
        super(new PstTopPosition(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstTopPosition(long lOid) throws DBException {
        super(new PstTopPosition(0));
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
        return TBL_TOP_POSITION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstTopPosition().getClass().getName();
    }

    public static TopPosition fetchExc(long oid) throws DBException {
        try {
            TopPosition entTopPosition = new TopPosition();
            PstTopPosition pstTopPosition = new PstTopPosition(oid);
            entTopPosition.setOID(oid);
            entTopPosition.setPositionId(pstTopPosition.getLong(FLD_POSITION_ID));
            entTopPosition.setPositionToplink(pstTopPosition.getLong(FLD_POSITION_TOPLINK));
            entTopPosition.setTypeOfLink(pstTopPosition.getInt(FLD_TYPE_OF_LINK));
            return entTopPosition;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTopPosition(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        TopPosition entTopPosition = fetchExc(entity.getOID());
        entity = (Entity) entTopPosition;
        return entTopPosition.getOID();
    }

    public static synchronized long updateExc(TopPosition entTopPosition) throws DBException {
        try {
            if (entTopPosition.getOID() != 0) {
                PstTopPosition pstTopPosition = new PstTopPosition(entTopPosition.getOID());
                pstTopPosition.setLong(FLD_POSITION_ID, entTopPosition.getPositionId());
                pstTopPosition.setLong(FLD_POSITION_TOPLINK, entTopPosition.getPositionToplink());
                pstTopPosition.setInt(FLD_TYPE_OF_LINK, entTopPosition.getTypeOfLink());
                pstTopPosition.update();
                return entTopPosition.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTopPosition(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((TopPosition) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstTopPosition pstTopPosition = new PstTopPosition(oid);
            pstTopPosition.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTopPosition(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(TopPosition entTopPosition) throws DBException {
        try {
            PstTopPosition pstTopPosition = new PstTopPosition(0);
            pstTopPosition.setLong(FLD_POSITION_ID, entTopPosition.getPositionId());
            pstTopPosition.setLong(FLD_POSITION_TOPLINK, entTopPosition.getPositionToplink());
            pstTopPosition.setInt(FLD_TYPE_OF_LINK, entTopPosition.getTypeOfLink());
            pstTopPosition.insert();
            entTopPosition.setOID(pstTopPosition.getLong(FLD_TOP_POSITION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTopPosition(0), DBException.UNKNOWN);
        }
        return entTopPosition.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((TopPosition) entity);
    }

    public static void resultToObject(ResultSet rs, TopPosition entTopPosition) {
        try {
            entTopPosition.setOID(rs.getLong(PstTopPosition.fieldNames[PstTopPosition.FLD_TOP_POSITION_ID]));
            entTopPosition.setPositionId(rs.getLong(PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_ID]));
            entTopPosition.setPositionToplink(rs.getLong(PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]));
            entTopPosition.setTypeOfLink(rs.getInt(PstTopPosition.fieldNames[PstTopPosition.FLD_TYPE_OF_LINK]));
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
            String sql = "SELECT * FROM " + TBL_TOP_POSITION;
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
                TopPosition entTopPosition = new TopPosition();
                resultToObject(rs, entTopPosition);
                lists.add(entTopPosition);
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
    
    public static String listEmployeeTopPositionId(int limitStart, int recordToGet, String whereClause, String order) {
        String listEmployeeTopPositionId = "";
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT HMP.* " +
                     " FROM " + TBL_TOP_POSITION + " HMP " +
                    "INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" HE ON HE."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] +" = HMP."+PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_ID];
                                    
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
                TopPosition entTopPosition = new TopPosition();
                String topLinkId = (rs.getString("HMP."+PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]));
                listEmployeeTopPositionId = listEmployeeTopPositionId + topLinkId +",";
                //lists.add(entTopPosition);
            }
            rs.close();
            return listEmployeeTopPositionId;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return  listEmployeeTopPositionId;
    }

    public static boolean checkOID(long entTopPositionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_TOP_POSITION + " WHERE "
                    + PstTopPosition.fieldNames[PstTopPosition.FLD_TOP_POSITION_ID] + " = " + entTopPositionId;
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
            String sql = "SELECT COUNT(" + PstTopPosition.fieldNames[PstTopPosition.FLD_TOP_POSITION_ID] + ") FROM " + TBL_TOP_POSITION;
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
                    TopPosition entTopPosition = (TopPosition) list.get(ls);
                    if (oid == entTopPosition.getOID()) {
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
