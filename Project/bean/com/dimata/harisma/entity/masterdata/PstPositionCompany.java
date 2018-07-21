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

public class PstPositionCompany extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_COMPANY = "hr_position_company";
    public static final int FLD_POSITION_COMPANY_ID = 0;
    public static final int FLD_COMPANY_ID = 1;
    public static final int FLD_POSITION_ID = 2;
    public static String[] fieldNames = {
        "POSITION_COMPANY_ID",
        "COMPANY_ID",
        "POSITION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstPositionCompany() {
    }

    public PstPositionCompany(int i) throws DBException {
        super(new PstPositionCompany());
    }

    public PstPositionCompany(String sOid) throws DBException {
        super(new PstPositionCompany(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionCompany(long lOid) throws DBException {
        super(new PstPositionCompany(0));
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
        return TBL_POSITION_COMPANY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionCompany().getClass().getName();
    }

    public static PositionCompany fetchExc(long oid) throws DBException {
        try {
            PositionCompany entPositionCompany = new PositionCompany();
            PstPositionCompany pstPositionCompany = new PstPositionCompany(oid);
            entPositionCompany.setOID(oid);
            entPositionCompany.setCompanyId(pstPositionCompany.getLong(FLD_COMPANY_ID));
            entPositionCompany.setPositionId(pstPositionCompany.getLong(FLD_POSITION_ID));
            return entPositionCompany;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompany(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionCompany entPositionCompany = fetchExc(entity.getOID());
        entity = (Entity) entPositionCompany;
        return entPositionCompany.getOID();
    }

    public static synchronized long updateExc(PositionCompany entPositionCompany) throws DBException {
        try {
            if (entPositionCompany.getOID() != 0) {
                PstPositionCompany pstPositionCompany = new PstPositionCompany(entPositionCompany.getOID());
                pstPositionCompany.setLong(FLD_COMPANY_ID, entPositionCompany.getCompanyId());
                pstPositionCompany.setLong(FLD_POSITION_ID, entPositionCompany.getPositionId());
                pstPositionCompany.update();
                return entPositionCompany.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompany(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionCompany) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionCompany pstPositionCompany = new PstPositionCompany(oid);
            pstPositionCompany.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompany(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionCompany entPositionCompany) throws DBException {
        try {
            PstPositionCompany pstPositionCompany = new PstPositionCompany(0);
            pstPositionCompany.setLong(FLD_COMPANY_ID, entPositionCompany.getCompanyId());
            pstPositionCompany.setLong(FLD_POSITION_ID, entPositionCompany.getPositionId());
            pstPositionCompany.insert();
            entPositionCompany.setOID(pstPositionCompany.getLong(FLD_POSITION_COMPANY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompany(0), DBException.UNKNOWN);
        }
        return entPositionCompany.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionCompany) entity);
    }

    public static void resultToObject(ResultSet rs, PositionCompany entPositionCompany) {
        try {
            entPositionCompany.setOID(rs.getLong(PstPositionCompany.fieldNames[PstPositionCompany.FLD_POSITION_COMPANY_ID]));
            entPositionCompany.setCompanyId(rs.getLong(PstPositionCompany.fieldNames[PstPositionCompany.FLD_COMPANY_ID]));
            entPositionCompany.setPositionId(rs.getLong(PstPositionCompany.fieldNames[PstPositionCompany.FLD_POSITION_ID]));
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
            String sql = "SELECT * FROM " + TBL_POSITION_COMPANY;
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
                PositionCompany entPositionCompany = new PositionCompany();
                resultToObject(rs, entPositionCompany);
                lists.add(entPositionCompany);
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

    public static boolean checkOID(long entPositionCompanyId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_COMPANY + " WHERE "
                    + PstPositionCompany.fieldNames[PstPositionCompany.FLD_POSITION_COMPANY_ID] + " = " + entPositionCompanyId;
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
            String sql = "SELECT COUNT(" + PstPositionCompany.fieldNames[PstPositionCompany.FLD_POSITION_COMPANY_ID] + ") FROM " + TBL_POSITION_COMPANY;
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
                    PositionCompany entPositionCompany = (PositionCompany) list.get(ls);
                    if (oid == entPositionCompany.getOID()) {
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
