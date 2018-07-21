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

public class PstPositionSection extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_SECTION = "hr_position_section";
    public static final int FLD_POSITION_SECTION_ID = 0;
    public static final int FLD_SECTION_ID = 1;
    public static final int FLD_POSITION_ID = 2;
    public static String[] fieldNames = {
        "POSITION_SECTION_ID",
        "SECTION_ID",
        "POSITION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstPositionSection() {
    }

    public PstPositionSection(int i) throws DBException {
        super(new PstPositionSection());
    }

    public PstPositionSection(String sOid) throws DBException {
        super(new PstPositionSection(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionSection(long lOid) throws DBException {
        super(new PstPositionSection(0));
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
        return TBL_POSITION_SECTION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionSection().getClass().getName();
    }

    public static PositionSection fetchExc(long oid) throws DBException {
        try {
            PositionSection entPositionSection = new PositionSection();
            PstPositionSection pstPositionSection = new PstPositionSection(oid);
            entPositionSection.setOID(oid);
            entPositionSection.setSectionId(pstPositionSection.getLong(FLD_SECTION_ID));
            entPositionSection.setPositionId(pstPositionSection.getLong(FLD_POSITION_ID));
            return entPositionSection;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionSection(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionSection entPositionSection = fetchExc(entity.getOID());
        entity = (Entity) entPositionSection;
        return entPositionSection.getOID();
    }

    public static synchronized long updateExc(PositionSection entPositionSection) throws DBException {
        try {
            if (entPositionSection.getOID() != 0) {
                PstPositionSection pstPositionSection = new PstPositionSection(entPositionSection.getOID());
                pstPositionSection.setLong(FLD_SECTION_ID, entPositionSection.getSectionId());
                pstPositionSection.setLong(FLD_POSITION_ID, entPositionSection.getPositionId());
                pstPositionSection.update();
                return entPositionSection.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionSection(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionSection) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionSection pstPositionSection = new PstPositionSection(oid);
            pstPositionSection.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionSection(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionSection entPositionSection) throws DBException {
        try {
            PstPositionSection pstPositionSection = new PstPositionSection(0);
            pstPositionSection.setLong(FLD_SECTION_ID, entPositionSection.getSectionId());
            pstPositionSection.setLong(FLD_POSITION_ID, entPositionSection.getPositionId());
            pstPositionSection.insert();
            entPositionSection.setOID(pstPositionSection.getLong(FLD_POSITION_SECTION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionSection(0), DBException.UNKNOWN);
        }
        return entPositionSection.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionSection) entity);
    }

    public static void resultToObject(ResultSet rs, PositionSection entPositionSection) {
        try {
            entPositionSection.setOID(rs.getLong(PstPositionSection.fieldNames[PstPositionSection.FLD_POSITION_SECTION_ID]));
            entPositionSection.setSectionId(rs.getLong(PstPositionSection.fieldNames[PstPositionSection.FLD_SECTION_ID]));
            entPositionSection.setPositionId(rs.getLong(PstPositionSection.fieldNames[PstPositionSection.FLD_POSITION_ID]));
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
            String sql = "SELECT * FROM " + TBL_POSITION_SECTION;
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
                PositionSection entPositionSection = new PositionSection();
                resultToObject(rs, entPositionSection);
                lists.add(entPositionSection);
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

    public static boolean checkOID(long entPositionSectionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_SECTION + " WHERE "
                    + PstPositionSection.fieldNames[PstPositionSection.FLD_POSITION_SECTION_ID] + " = " + entPositionSectionId;
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
            String sql = "SELECT COUNT(" + PstPositionSection.fieldNames[PstPositionSection.FLD_POSITION_SECTION_ID] + ") FROM " + TBL_POSITION_SECTION;
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
                    PositionSection entPositionSection = (PositionSection) list.get(ls);
                    if (oid == entPositionSection.getOID()) {
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
