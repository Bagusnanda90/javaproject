/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import java.util.Vector;

public class PstOutSourceCostMaster extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_OUTSOURCECOSTMASTER = "hr_outsrc_cost_master";
    public static final int FLD_OUTSOURCECOSTMASTERID = 0;
    public static final int FLD_SHOWINDEX = 1;
    public static final int FLD_COSTCODE = 2;
    public static final int FLD_COSTNAME = 3;
    public static final int FLD_TYPE = 4;
    public static final int FLD_NOTE = 5;
    public static final int FLD_PARENTOUTSOURCECOSTID = 6;
    
    public static final String[] typeKey = {"PLAN & INPUT", "LINK & INPUT"};
    public static final int[] typeValue = {0, 1};
    public static final int COST_TYPE_PLAN_N_INPUT=0;
    public static final int COST_TYPE_LINK_N_INPUT=1;
    
    public static String[] fieldNames = {
        "OUTSRC_COST_ID",
        "SHOW_INDEX",
        "COST_CODE",
        "COST_NAME",
        "TYPE",
        "NOTE",
        "PARENT_OUTSRC_COST_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_BLOB
    };

    public PstOutSourceCostMaster() {
    }

    public PstOutSourceCostMaster(int i) throws DBException {
        super(new PstOutSourceCostMaster());
    }

    public PstOutSourceCostMaster(String sOid) throws DBException {
        super(new PstOutSourceCostMaster(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourceCostMaster(long lOid) throws DBException {
        super(new PstOutSourceCostMaster(0));
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
        return TBL_OUTSOURCECOSTMASTER;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourceCostMaster().getClass().getName();
    }

    public static OutSourceCostMaster fetchExc(long oid) throws DBException {
        try {
            OutSourceCostMaster entOutSourceCostMaster = new OutSourceCostMaster();
            PstOutSourceCostMaster pstOutSourceCostMaster = new PstOutSourceCostMaster(oid);
            entOutSourceCostMaster.setOID(oid);
            entOutSourceCostMaster.setShowIndex(pstOutSourceCostMaster.getInt(FLD_SHOWINDEX));
            entOutSourceCostMaster.setCostCode(pstOutSourceCostMaster.getString(FLD_COSTCODE));
            entOutSourceCostMaster.setCostName(pstOutSourceCostMaster.getString(FLD_COSTNAME));
            entOutSourceCostMaster.setType(pstOutSourceCostMaster.getInt(FLD_TYPE));
            entOutSourceCostMaster.setNote(pstOutSourceCostMaster.getString(FLD_NOTE));
            entOutSourceCostMaster.setParentOutSourceCostId(pstOutSourceCostMaster.getlong(FLD_PARENTOUTSOURCECOSTID));
            return entOutSourceCostMaster;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCostMaster(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourceCostMaster entOutSourceCostMaster = fetchExc(entity.getOID());
        entity = (Entity) entOutSourceCostMaster;
        return entOutSourceCostMaster.getOID();
    }

    public static synchronized long updateExc(OutSourceCostMaster entOutSourceCostMaster) throws DBException {
        try {
            if (entOutSourceCostMaster.getOID() != 0) {
                PstOutSourceCostMaster pstOutSourceCostMaster = new PstOutSourceCostMaster(entOutSourceCostMaster.getOID());
                pstOutSourceCostMaster.setInt(FLD_SHOWINDEX, entOutSourceCostMaster.getShowIndex());
                pstOutSourceCostMaster.setString(FLD_COSTCODE, entOutSourceCostMaster.getCostCode());
                pstOutSourceCostMaster.setString(FLD_COSTNAME, entOutSourceCostMaster.getCostName());
                pstOutSourceCostMaster.setInt(FLD_TYPE, entOutSourceCostMaster.getType());
                pstOutSourceCostMaster.setString(FLD_NOTE, entOutSourceCostMaster.getNote());
                pstOutSourceCostMaster.setLong(FLD_PARENTOUTSOURCECOSTID, entOutSourceCostMaster.getParentOutSourceCostId());
                pstOutSourceCostMaster.update();
                return entOutSourceCostMaster.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCostMaster(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourceCostMaster) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourceCostMaster pstOutSourceCostMaster = new PstOutSourceCostMaster(oid);
            pstOutSourceCostMaster.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCostMaster(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourceCostMaster entOutSourceCostMaster) throws DBException {
        try {
            PstOutSourceCostMaster pstOutSourceCostMaster = new PstOutSourceCostMaster(0);
            pstOutSourceCostMaster.setInt(FLD_SHOWINDEX, entOutSourceCostMaster.getShowIndex());
            pstOutSourceCostMaster.setString(FLD_COSTCODE, entOutSourceCostMaster.getCostCode());
            pstOutSourceCostMaster.setString(FLD_COSTNAME, entOutSourceCostMaster.getCostName());
            pstOutSourceCostMaster.setInt(FLD_TYPE, entOutSourceCostMaster.getType());
            pstOutSourceCostMaster.setString(FLD_NOTE, entOutSourceCostMaster.getNote());
            pstOutSourceCostMaster.setLong(FLD_PARENTOUTSOURCECOSTID, entOutSourceCostMaster.getParentOutSourceCostId());
            pstOutSourceCostMaster.insert();
            entOutSourceCostMaster.setOID(pstOutSourceCostMaster.getlong(FLD_OUTSOURCECOSTMASTERID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCostMaster(0), DBException.UNKNOWN);
        }
        return entOutSourceCostMaster.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourceCostMaster) entity);
    }

    public static void resultToObject(ResultSet rs, OutSourceCostMaster entOutSourceCostMaster) {
        try {
            entOutSourceCostMaster.setOID(rs.getLong(PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_OUTSOURCECOSTMASTERID]));
            entOutSourceCostMaster.setShowIndex(rs.getInt(PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_SHOWINDEX]));
            entOutSourceCostMaster.setCostCode(rs.getString(PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_COSTCODE]));
            entOutSourceCostMaster.setCostName(rs.getString(PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_COSTNAME]));
            entOutSourceCostMaster.setType(rs.getInt(PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_TYPE]));
            entOutSourceCostMaster.setNote(rs.getString(PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_NOTE]));
            entOutSourceCostMaster.setParentOutSourceCostId(rs.getLong(PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_PARENTOUTSOURCECOSTID]));
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
            String sql = "SELECT * FROM " + TBL_OUTSOURCECOSTMASTER;
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
                OutSourceCostMaster entOutSourceCostMaster = new OutSourceCostMaster();
                resultToObject(rs, entOutSourceCostMaster);
                lists.add(entOutSourceCostMaster);
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
    
    public static String getName(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_COSTNAME] + " FROM " + TBL_OUTSOURCECOSTMASTER;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            String name = "";
            while (rs.next()) {
                name = rs.getString(1);
            }
            rs.close();
            return name;
        } catch (Exception e) {
            return "";
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static boolean checkOID(long entOutSourceCostMasterId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCECOSTMASTER + " WHERE "
                    + PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_OUTSOURCECOSTMASTERID] + " = " + entOutSourceCostMasterId;
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
            String sql = "SELECT COUNT(" + PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_OUTSOURCECOSTMASTERID] + ") FROM " + TBL_OUTSOURCECOSTMASTER;
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
                    OutSourceCostMaster entOutSourceCostMaster = (OutSourceCostMaster) list.get(ls);
                    if (oid == entOutSourceCostMaster.getOID()) {
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
