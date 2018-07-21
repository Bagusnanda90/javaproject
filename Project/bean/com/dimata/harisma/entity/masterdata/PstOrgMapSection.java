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

public class PstOrgMapSection extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_ORG_MAP_SECTION = "hr_org_map_section";
    public static final int FLD_ORG_MAP_SECT_ID = 0;
    public static final int FLD_SECTION_ID = 1;
    public static final int FLD_TEMPLATE_ID = 2;
    public static String[] fieldNames = {
        "ORG_MAP_SECT_ID",
        "SECTION_ID",
        "TEMPLATE_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstOrgMapSection() {
    }

    public PstOrgMapSection(int i) throws DBException {
        super(new PstOrgMapSection());
    }

    public PstOrgMapSection(String sOid) throws DBException {
        super(new PstOrgMapSection(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOrgMapSection(long lOid) throws DBException {
        super(new PstOrgMapSection(0));
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
        return TBL_ORG_MAP_SECTION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOrgMapSection().getClass().getName();
    }

    public static OrgMapSection fetchExc(long oid) throws DBException {
        try {
            OrgMapSection entOrgMapSection = new OrgMapSection();
            PstOrgMapSection pstOrgMapSection = new PstOrgMapSection(oid);
            entOrgMapSection.setOID(oid);
            entOrgMapSection.setSectionId(pstOrgMapSection.getLong(FLD_SECTION_ID));
            entOrgMapSection.setTemplateId(pstOrgMapSection.getLong(FLD_TEMPLATE_ID));
            return entOrgMapSection;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOrgMapSection(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OrgMapSection entOrgMapSection = fetchExc(entity.getOID());
        entity = (Entity) entOrgMapSection;
        return entOrgMapSection.getOID();
    }

    public static synchronized long updateExc(OrgMapSection entOrgMapSection) throws DBException {
        try {
            if (entOrgMapSection.getOID() != 0) {
                PstOrgMapSection pstOrgMapSection = new PstOrgMapSection(entOrgMapSection.getOID());
                pstOrgMapSection.setLong(FLD_SECTION_ID, entOrgMapSection.getSectionId());
                pstOrgMapSection.setLong(FLD_TEMPLATE_ID, entOrgMapSection.getTemplateId());
                pstOrgMapSection.update();
                return entOrgMapSection.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOrgMapSection(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OrgMapSection) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOrgMapSection pstOrgMapSection = new PstOrgMapSection(oid);
            pstOrgMapSection.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOrgMapSection(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OrgMapSection entOrgMapSection) throws DBException {
        try {
            PstOrgMapSection pstOrgMapSection = new PstOrgMapSection(0);
            pstOrgMapSection.setLong(FLD_SECTION_ID, entOrgMapSection.getSectionId());
            pstOrgMapSection.setLong(FLD_TEMPLATE_ID, entOrgMapSection.getTemplateId());
            pstOrgMapSection.insert();
            entOrgMapSection.setOID(pstOrgMapSection.getLong(FLD_ORG_MAP_SECT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOrgMapSection(0), DBException.UNKNOWN);
        }
        return entOrgMapSection.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OrgMapSection) entity);
    }

    public static void resultToObject(ResultSet rs, OrgMapSection entOrgMapSection) {
        try {
            entOrgMapSection.setOID(rs.getLong(PstOrgMapSection.fieldNames[PstOrgMapSection.FLD_ORG_MAP_SECT_ID]));
            entOrgMapSection.setSectionId(rs.getLong(PstOrgMapSection.fieldNames[PstOrgMapSection.FLD_SECTION_ID]));
            entOrgMapSection.setTemplateId(rs.getLong(PstOrgMapSection.fieldNames[PstOrgMapSection.FLD_TEMPLATE_ID]));
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
            String sql = "SELECT * FROM " + TBL_ORG_MAP_SECTION;
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
                OrgMapSection entOrgMapSection = new OrgMapSection();
                resultToObject(rs, entOrgMapSection);
                lists.add(entOrgMapSection);
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

    public static boolean checkOID(long entOrgMapSectionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_ORG_MAP_SECTION + " WHERE "
                    + PstOrgMapSection.fieldNames[PstOrgMapSection.FLD_ORG_MAP_SECT_ID] + " = " + entOrgMapSectionId;
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
            String sql = "SELECT COUNT(" + PstOrgMapSection.fieldNames[PstOrgMapSection.FLD_ORG_MAP_SECT_ID] + ") FROM " + TBL_ORG_MAP_SECTION;
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
                    OrgMapSection entOrgMapSection = (OrgMapSection) list.get(ls);
                    if (oid == entOrgMapSection.getOID()) {
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