/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Acer
 */
/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import java.sql.ResultSet;
import java.util.Vector;

public class PstSubSection extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_SUB_SECTION = "hr_sub_section";
    public static final int FLD_SUB_SECTION_ID = 0;
    public static final int FLD_SECTION_ID = 1;
    public static final int FLD_SUB_SECTION = 2;
    public static final int FLD_DESCRIPTION = 3;
    public static final int FLD_VALID_STATUS = 4;
    public static final int FLD_VALID_START = 5;
    public static final int FLD_VALID_END = 6;

    public static String[] fieldNames = {
        "SUB_SECTION_ID",
        "SECTION_ID",
        "SUB_SECTION",
        "DESCRIPTION",
        "VALID_STATUS",
        "VALID_START",
        "VALID_END"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE
    };
    
    public static final int VALID_ACTIVE = 1;
    public static final int VALID_HISTORY = 0;
    
    public static final String[] validStatusValue = {
         "HISTORY","ACTIVE"
    };

    public PstSubSection() {
    }

    public PstSubSection(int i) throws DBException {
        super(new PstSubSection());
    }

    public PstSubSection(String sOid) throws DBException {
        super(new PstSubSection(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstSubSection(long lOid) throws DBException {
        super(new PstSubSection(0));
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
        return TBL_SUB_SECTION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstSubSection().getClass().getName();
    }

    public static SubSection fetchExc(long oid) throws DBException {
        try {
            SubSection entSubSection = new SubSection();
            PstSubSection pstSubSection = new PstSubSection(oid);
            entSubSection.setOID(oid);
            entSubSection.setSectionId(pstSubSection.getLong(FLD_SECTION_ID));
            entSubSection.setSubSection(pstSubSection.getString(FLD_SUB_SECTION));
            entSubSection.setDescription(pstSubSection.getString(FLD_DESCRIPTION));
            entSubSection.setValidStatus(pstSubSection.getInt(FLD_VALID_STATUS));
            entSubSection.setValidStart(pstSubSection.getDate(FLD_VALID_START));
            entSubSection.setValidEnd(pstSubSection.getDate(FLD_VALID_END));
            return entSubSection;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSubSection(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        SubSection entSubSection = fetchExc(entity.getOID());
        entity = (Entity) entSubSection;
        return entSubSection.getOID();
    }

    public static synchronized long updateExc(SubSection entSubSection) throws DBException {
        try {
            if (entSubSection.getOID() != 0) {
                PstSubSection pstSubSection = new PstSubSection(entSubSection.getOID());
                pstSubSection.setLong(FLD_SECTION_ID, entSubSection.getSectionId());
                pstSubSection.setString(FLD_SUB_SECTION, entSubSection.getSubSection());
                pstSubSection.setString(FLD_DESCRIPTION, entSubSection.getDescription());
                pstSubSection.setInt(FLD_VALID_STATUS, entSubSection.getValidStatus());
                pstSubSection.setDate(FLD_VALID_START, entSubSection.getValidStart());
                pstSubSection.setDate(FLD_VALID_END, entSubSection.getValidEnd());
                pstSubSection.update();
                return entSubSection.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSubSection(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((SubSection) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstSubSection pstSubSection = new PstSubSection(oid);
            pstSubSection.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSubSection(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(SubSection entSubSection) throws DBException {
        try {
            PstSubSection pstSubSection = new PstSubSection(0);
            pstSubSection.setLong(FLD_SECTION_ID, entSubSection.getSectionId());
            pstSubSection.setString(FLD_SUB_SECTION, entSubSection.getSubSection());
            pstSubSection.setString(FLD_DESCRIPTION, entSubSection.getDescription());
            pstSubSection.setInt(FLD_VALID_STATUS, entSubSection.getValidStatus());
            pstSubSection.setDate(FLD_VALID_START, entSubSection.getValidStart());
            pstSubSection.setDate(FLD_VALID_END, entSubSection.getValidEnd());
            pstSubSection.insert();
            entSubSection.setOID(pstSubSection.getLong(FLD_SUB_SECTION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSubSection(0), DBException.UNKNOWN);
        }
        return entSubSection.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((SubSection) entity);
    }

    public static void resultToObject(ResultSet rs, SubSection entSubSection) {
        try {
            entSubSection.setOID(rs.getLong(PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION_ID]));
            entSubSection.setSectionId(rs.getLong(PstSubSection.fieldNames[PstSubSection.FLD_SECTION_ID]));
            entSubSection.setSubSection(rs.getString(PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION]));
            entSubSection.setDescription(rs.getString(PstSubSection.fieldNames[PstSubSection.FLD_DESCRIPTION]));
            entSubSection.setValidStatus(rs.getInt(PstSubSection.fieldNames[PstSubSection.FLD_VALID_STATUS]));
            entSubSection.setValidStart(rs.getDate(PstSubSection.fieldNames[PstSubSection.FLD_VALID_START]));
            entSubSection.setValidEnd(rs.getDate(PstSubSection.fieldNames[PstSubSection.FLD_VALID_END]));
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
            String sql = "SELECT * FROM " + TBL_SUB_SECTION;
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
                SubSection entSubSection = new SubSection();
                resultToObject(rs, entSubSection);
                lists.add(entSubSection);
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

    public static boolean checkOID(long entSubSectionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_SUB_SECTION + " WHERE "
                    + PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION_ID] + " = " + entSubSectionId;
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
            String sql = "SELECT COUNT(" + PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION_ID] + ") FROM " + TBL_SUB_SECTION;
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
                    SubSection entSubSection = (SubSection) list.get(ls);
                    if (oid == entSubSection.getOID()) {
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
        } else if (start == (vectSize - recordToGet)) {
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
        return cmd;
    }
}
