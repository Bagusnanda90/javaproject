/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu
 * @version  	:  [version]
 */
/**
 * *****************************************************************
 * Class Description : PstMappingKadivMain Imput Parameters : [input
 * parameter ...] Output : [output ...]
 ******************************************************************
 */
package com.dimata.harisma.entity.masterdata.mappingkadiv;

/**
 *
 * @author Wiweka
 */
/* package java */
import com.dimata.harisma.entity.masterdata.leaveconfiguration.*;
import com.dimata.harisma.entity.masterdata.*;
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstMappingKadivMain extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_MAPPING_KADIV_MAIN = "hr_mapping_kadiv_main";//"hr_company";
    public static final int FLD_MAPPING_KADIV_MAIN_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final String[] fieldNames = {
        "MAPPING_KADIV_MAIN_ID",
        "EMPLOYEE_ID",
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
    };

    public PstMappingKadivMain() {
    }

    public PstMappingKadivMain(int i) throws DBException {
        super(new PstMappingKadivMain());
    }

    public PstMappingKadivMain(String sOid) throws DBException {
        super(new PstMappingKadivMain(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstMappingKadivMain(long lOid) throws DBException {
        super(new PstMappingKadivMain(0));
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
        return TBL_HR_MAPPING_KADIV_MAIN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstMappingKadivMain().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        MappingKadivMain mappingKadivMain = fetchExc(ent.getOID());
        ent = (Entity) mappingKadivMain;
        return mappingKadivMain.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((MappingKadivMain) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((MappingKadivMain) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static MappingKadivMain fetchExc(long oid) throws DBException {
        try {
            MappingKadivMain mappingKadivMain = new MappingKadivMain();
            PstMappingKadivMain pstMappingKadivMain = new PstMappingKadivMain(oid);
            mappingKadivMain.setOID(oid);

            mappingKadivMain.setEmployeeId(pstMappingKadivMain.getlong(FLD_EMPLOYEE_ID));

            return mappingKadivMain;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivMain(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(MappingKadivMain mappingKadivMain) throws DBException {
        try {
            PstMappingKadivMain pstMappingKadivMain = new PstMappingKadivMain(0);

            pstMappingKadivMain.setLong(FLD_EMPLOYEE_ID, mappingKadivMain.getEmployeeId());


            pstMappingKadivMain.insert();
            mappingKadivMain.setOID(pstMappingKadivMain.getlong(FLD_MAPPING_KADIV_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivMain(0), DBException.UNKNOWN);
        }
        return mappingKadivMain.getOID();
    }

    public static long updateExc(MappingKadivMain mappingKadivMain) throws DBException {
        try {
            if (mappingKadivMain.getOID() != 0) {
                PstMappingKadivMain pstMappingKadivMain = new PstMappingKadivMain(mappingKadivMain.getOID());

                pstMappingKadivMain.setLong(FLD_EMPLOYEE_ID, mappingKadivMain.getEmployeeId());


                pstMappingKadivMain.update();
                return mappingKadivMain.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivMain(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstMappingKadivMain pstMappingKadivMain = new PstMappingKadivMain(oid);
            pstMappingKadivMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivMain(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_MAPPING_KADIV_MAIN;
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
                MappingKadivMain mappingKadivMain = new MappingKadivMain();
                resultToObject(rs, mappingKadivMain);
                lists.add(mappingKadivMain);
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

    public static Vector listJoinEmployee(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT mkm.*,emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+"pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION] + " FROM " + PstMappingKadivMain.TBL_HR_MAPPING_KADIV_MAIN + " AS mkm "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON mkm." + PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID];
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
                MappingKadivMain mappingKadivMain = new MappingKadivMain();
                resultToObject(rs, mappingKadivMain);
                mappingKadivMain.setNameEmployee(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                mappingKadivMain.setNamePosition(rs.getString("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                lists.add(mappingKadivMain);
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

    public static void resultToObject(ResultSet rs, MappingKadivMain mappingKadivMain) {
        try {
            mappingKadivMain.setOID(rs.getLong(PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID]));
            mappingKadivMain.setEmployeeId(rs.getLong(PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_EMPLOYEE_ID]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long oidMain) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_MAPPING_KADIV_MAIN + " WHERE "
                    + PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID] + " = " + oidMain;

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
            String sql = "SELECT COUNT(" + PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID] + ") FROM " + TBL_HR_MAPPING_KADIV_MAIN;
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


    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    MappingKadivMain mappingKadivMain = (MappingKadivMain) list.get(ls);
                    if (oid == mappingKadivMain.getOID()) {
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
}
