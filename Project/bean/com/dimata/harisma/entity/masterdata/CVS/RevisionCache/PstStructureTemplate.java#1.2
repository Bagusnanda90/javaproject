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

public class PstStructureTemplate extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String SESS_STRUCTURE_ORG = "SESS_STRUCTURE_ORG";
    public static final String TBL_STRUCTURE_TEMPLATE = "hr_structure_template";
    public static final int FLD_TEMPLATE_ID = 0;
    public static final int FLD_TEMPLATE_NAME = 1;
    public static final int FLD_TEMPLATE_DESC = 2;
    public static final int FLD_START_DATE = 3;
    public static final int FLD_END_DATE = 4;
    public static String[] fieldNames = {
        "TEMPLATE_ID",
        "TEMPLATE_NAME",
        "TEMPLATE_DESC",
        "START_DATE",
        "END_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE
    };

    public PstStructureTemplate() {
    }

    public PstStructureTemplate(int i) throws DBException {
        super(new PstStructureTemplate());
    }

    public PstStructureTemplate(String sOid) throws DBException {
        super(new PstStructureTemplate(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstStructureTemplate(long lOid) throws DBException {
        super(new PstStructureTemplate(0));
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
        return TBL_STRUCTURE_TEMPLATE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstStructureTemplate().getClass().getName();
    }

    public static StructureTemplate fetchExc(long oid) throws DBException {
        try {
            StructureTemplate entStructureTemplate = new StructureTemplate();
            PstStructureTemplate pstStructureTemplate = new PstStructureTemplate(oid);
            entStructureTemplate.setOID(oid);
            entStructureTemplate.setTemplateName(pstStructureTemplate.getString(FLD_TEMPLATE_NAME));
            entStructureTemplate.setTemplateDesc(pstStructureTemplate.getString(FLD_TEMPLATE_DESC));
            entStructureTemplate.setStartDate(pstStructureTemplate.getDate(FLD_START_DATE));
            entStructureTemplate.setEndDate(pstStructureTemplate.getDate(FLD_END_DATE));
            return entStructureTemplate;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstStructureTemplate(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        StructureTemplate entStructureTemplate = fetchExc(entity.getOID());
        entity = (Entity) entStructureTemplate;
        return entStructureTemplate.getOID();
    }

    public static synchronized long updateExc(StructureTemplate entStructureTemplate) throws DBException {
        try {
            if (entStructureTemplate.getOID() != 0) {
                PstStructureTemplate pstStructureTemplate = new PstStructureTemplate(entStructureTemplate.getOID());
                pstStructureTemplate.setString(FLD_TEMPLATE_NAME, entStructureTemplate.getTemplateName());
                pstStructureTemplate.setString(FLD_TEMPLATE_DESC, entStructureTemplate.getTemplateDesc());
                pstStructureTemplate.setDate(FLD_START_DATE, entStructureTemplate.getStartDate());
                pstStructureTemplate.setDate(FLD_END_DATE, entStructureTemplate.getEndDate());
                pstStructureTemplate.update();
                return entStructureTemplate.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstStructureTemplate(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((StructureTemplate) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstStructureTemplate pstStructureTemplate = new PstStructureTemplate(oid);
            pstStructureTemplate.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstStructureTemplate(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(StructureTemplate entStructureTemplate) throws DBException {
        try {
            PstStructureTemplate pstStructureTemplate = new PstStructureTemplate(0);
            pstStructureTemplate.setString(FLD_TEMPLATE_NAME, entStructureTemplate.getTemplateName());
            pstStructureTemplate.setString(FLD_TEMPLATE_DESC, entStructureTemplate.getTemplateDesc());
            pstStructureTemplate.setDate(FLD_START_DATE, entStructureTemplate.getStartDate());
            pstStructureTemplate.setDate(FLD_END_DATE, entStructureTemplate.getEndDate());
            pstStructureTemplate.insert();
            entStructureTemplate.setOID(pstStructureTemplate.getLong(FLD_TEMPLATE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstStructureTemplate(0), DBException.UNKNOWN);
        }
        return entStructureTemplate.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((StructureTemplate) entity);
    }

    public static void resultToObject(ResultSet rs, StructureTemplate entStructureTemplate) {
        try {
            entStructureTemplate.setOID(rs.getLong(PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_ID]));
            entStructureTemplate.setTemplateName(rs.getString(PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_NAME]));
            entStructureTemplate.setTemplateDesc(rs.getString(PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_DESC]));
            entStructureTemplate.setStartDate(rs.getDate(PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_START_DATE]));
            entStructureTemplate.setEndDate(rs.getDate(PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_END_DATE]));
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
            String sql = "SELECT * FROM " + TBL_STRUCTURE_TEMPLATE;
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
                StructureTemplate entStructureTemplate = new StructureTemplate();
                resultToObject(rs, entStructureTemplate);
                lists.add(entStructureTemplate);
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

    public static boolean checkOID(long entStructureTemplateId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_STRUCTURE_TEMPLATE + " WHERE "
                    + PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_ID] + " = " + entStructureTemplateId;
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
            String sql = "SELECT COUNT(" + PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_ID] + ") FROM " + TBL_STRUCTURE_TEMPLATE;
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
                    StructureTemplate entStructureTemplate = (StructureTemplate) list.get(ls);
                    if (oid == entStructureTemplate.getOID()) {
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