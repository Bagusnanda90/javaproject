/* Created on 	:  27 Juli 2015 [time] AM/PM
 *
 * @author  	:  PRISKA
 * @version  	:  [version]
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author PRISKA
 */


public class PstPayrollGroup extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_PAYROLL_GROUP = "payroll_group";
    
    public static final int FLD_PAYROLL_GROUP_ID = 0;
    public static final int FLD_PAYROLL_GROUP_NAME = 1;
    public static final int FLD_PAYROLL_GROUP_DESCRIPTION = 2;
    public static final String[] fieldNames = {
        "PAYROLL_GROUP_ID",
        "PAYROLL_GROUP_NAME", 
        "PAYROLL_GROUP_DESCRIPTION"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstPayrollGroup() {
    }

    public PstPayrollGroup(int i) throws DBException {
        super(new PstPayrollGroup());
    }

    public PstPayrollGroup(String sOid) throws DBException {
        super(new PstPayrollGroup(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPayrollGroup(long lOid) throws DBException {
        super(new PstPayrollGroup(0));
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
        return TBL_PAYROLL_GROUP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPayrollGroup().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        PayrollGroup payrollGroup = fetchExc(ent.getOID());
        ent = (Entity) payrollGroup;
        return payrollGroup.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PayrollGroup) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayrollGroup) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static PayrollGroup fetchExc(long oid) throws DBException {
        try {
            PayrollGroup payrollGroup = new PayrollGroup();
            PstPayrollGroup pstPayrollGroup = new PstPayrollGroup(oid);
            payrollGroup.setOID(oid);

            payrollGroup.setPayrollGroupName(pstPayrollGroup.getString(FLD_PAYROLL_GROUP_NAME));
            payrollGroup.setDescription(pstPayrollGroup.getString(FLD_PAYROLL_GROUP_DESCRIPTION));
            return payrollGroup;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayrollGroup(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(PayrollGroup payrollGroup) throws DBException {
        try {
            PstPayrollGroup pstPayrollGroup = new PstPayrollGroup(0);

            pstPayrollGroup.setString(FLD_PAYROLL_GROUP_NAME, payrollGroup.getPayrollGroupName());
            pstPayrollGroup.setString(FLD_PAYROLL_GROUP_DESCRIPTION, payrollGroup.getDescription());
            pstPayrollGroup.insert();
            payrollGroup.setOID(pstPayrollGroup.getlong(FLD_PAYROLL_GROUP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayrollGroup(0), DBException.UNKNOWN);
        }
        return payrollGroup.getOID();
    }

    public static long updateExc(PayrollGroup payrollGroup) throws DBException {
        try {
            if (payrollGroup.getOID() != 0) {
                PstPayrollGroup pstPayrollGroup = new PstPayrollGroup(payrollGroup.getOID());

                pstPayrollGroup.setString(FLD_PAYROLL_GROUP_NAME, payrollGroup.getPayrollGroupName());
                pstPayrollGroup.setString(FLD_PAYROLL_GROUP_DESCRIPTION, payrollGroup.getDescription());
                pstPayrollGroup.update();
                return payrollGroup.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayrollGroup(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPayrollGroup pstPayrollGroup = new PstPayrollGroup(oid);
            pstPayrollGroup.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayrollGroup(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_PAYROLL_GROUP;
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
                PayrollGroup payrollGroup = new PayrollGroup();
                resultToObject(rs, payrollGroup);
                lists.add(payrollGroup);
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


    
    public static void resultToObject(ResultSet rs, PayrollGroup payrollGroup) {
        try {
            payrollGroup.setOID(rs.getLong(PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_ID]));
            payrollGroup.setPayrollGroupName(rs.getString(PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_NAME]));
            payrollGroup.setDescription(rs.getString(PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_DESCRIPTION]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long payrollGroupId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_PAYROLL_GROUP + " WHERE "
                    + PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_ID] + " = " + payrollGroupId;

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
            String sql = "SELECT COUNT(" + PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_ID] + ") FROM " + TBL_PAYROLL_GROUP;
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
                    PayrollGroup payrollGroup = (PayrollGroup) list.get(ls);
                    if (oid == payrollGroup.getOID()) {
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
