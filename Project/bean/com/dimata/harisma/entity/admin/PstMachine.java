/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.admin;

/**
 *
 * @author Gunadi
 */
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;

/* package java */
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;

public class PstMachine extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_MACHINE = "hr_machine";
    public static final int FLD_MACHINE_ID = 0;
    public static final int FLD_MACHINE_NAME = 1;
    public static final int FLD_MACHINE_IP = 2;
    public static final int FLD_MACHINE_PORT = 3;
    public static final int FLD_MACHINE_COM_KEY = 4;
    public static String[] fieldNames = {
        "MACHINE_ID",
        "MACHINE_NAME",
        "MACHINE_IP",
        "MACHINE_PORT",
        "MACHINE_COM_KEY"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT
    };

    public PstMachine() {
    }

    public PstMachine(int i) throws DBException {
        super(new PstMachine());
    }

    public PstMachine(String sOid) throws DBException {
        super(new PstMachine(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstMachine(long lOid) throws DBException {
        super(new PstMachine(0));
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
        return TBL_MACHINE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstMachine().getClass().getName();
    }

    public static Machine fetchExc(long oid) throws DBException {
        try {
            Machine entMachine = new Machine();
            PstMachine pstMachine = new PstMachine(oid);
            entMachine.setOID(oid);
            entMachine.setMachineName(pstMachine.getString(FLD_MACHINE_NAME));
            entMachine.setMachineIP(pstMachine.getString(FLD_MACHINE_IP));
            entMachine.setMachinePort(pstMachine.getInt(FLD_MACHINE_PORT));
            entMachine.setMachineComKey(pstMachine.getInt(FLD_MACHINE_COM_KEY));
            return entMachine;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMachine(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        Machine entMachine = fetchExc(entity.getOID());
        entity = (Entity) entMachine;
        return entMachine.getOID();
    }

    public static synchronized long updateExc(Machine entMachine) throws DBException {
        try {
            if (entMachine.getOID() != 0) {
                PstMachine pstMachine = new PstMachine(entMachine.getOID());
                pstMachine.setString(FLD_MACHINE_NAME, entMachine.getMachineName());
                pstMachine.setString(FLD_MACHINE_IP, entMachine.getMachineIP());
                pstMachine.setInt(FLD_MACHINE_PORT, entMachine.getMachinePort());
                pstMachine.setInt(FLD_MACHINE_COM_KEY, entMachine.getMachineComKey());
                pstMachine.update();
                return entMachine.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMachine(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((Machine) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstMachine pstMachine = new PstMachine(oid);
            pstMachine.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMachine(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(Machine entMachine) throws DBException {
        try {
            PstMachine pstMachine = new PstMachine(0);
            pstMachine.setString(FLD_MACHINE_NAME, entMachine.getMachineName());
            pstMachine.setString(FLD_MACHINE_IP, entMachine.getMachineIP());
            pstMachine.setInt(FLD_MACHINE_PORT, entMachine.getMachinePort());
            pstMachine.setInt(FLD_MACHINE_COM_KEY, entMachine.getMachineComKey());
            pstMachine.insert();
            entMachine.setOID(pstMachine.getLong(FLD_MACHINE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMachine(0), DBException.UNKNOWN);
        }
        return entMachine.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((Machine) entity);
    }

    public static void resultToObject(ResultSet rs, Machine entMachine) {
        try {
            entMachine.setOID(rs.getLong(PstMachine.fieldNames[PstMachine.FLD_MACHINE_ID]));
            entMachine.setMachineName(rs.getString(PstMachine.fieldNames[PstMachine.FLD_MACHINE_NAME]));
            entMachine.setMachineIP(rs.getString(PstMachine.fieldNames[PstMachine.FLD_MACHINE_IP]));
            entMachine.setMachinePort(rs.getInt(PstMachine.fieldNames[PstMachine.FLD_MACHINE_PORT]));
            entMachine.setMachineComKey(rs.getInt(PstMachine.fieldNames[PstMachine.FLD_MACHINE_COM_KEY]));
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
            String sql = "SELECT * FROM " + TBL_MACHINE;
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
                Machine entMachine = new Machine();
                resultToObject(rs, entMachine);
                lists.add(entMachine);
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

    public static boolean checkOID(long entMachineId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_MACHINE + " WHERE "
                    + PstMachine.fieldNames[PstMachine.FLD_MACHINE_ID] + " = " + entMachineId;
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
            String sql = "SELECT COUNT(" + PstMachine.fieldNames[PstMachine.FLD_MACHINE_ID] + ") FROM " + TBL_MACHINE;
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
                    Machine entMachine = (Machine) list.get(ls);
                    if (oid == entMachine.getOID()) {
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