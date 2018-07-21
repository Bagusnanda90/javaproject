/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class PstPaySlipGroup extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    public static final String TBL_PAYSLIP_GROUP = "pay_payslip_group";
    public static final int FLD_PAYSLIP_GROUP_ID = 0;
    public static final int FLD_PAYSLIP_GROUP_NAME = 1;
    public static final int FLD_PAYSLIP_GROUP_DESC = 2;
    public static final String[] fieldNames = {
        "PAYSLIP_GROUP_ID", //"COMPANY_ID",
        "PAYSLIP_GROUP_NAME", //"COMPANY",
        "PAYSLIP_GROUP_DESC", //"DESCRIPTION"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstPaySlipGroup() {
    }

    public PstPaySlipGroup(int i) throws DBException {
        super(new PstPaySlipGroup());
    }

    public PstPaySlipGroup(String sOid) throws DBException {
        super(new PstPaySlipGroup(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPaySlipGroup(long lOid) throws DBException {
        super(new PstPaySlipGroup(0));
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
        return TBL_PAYSLIP_GROUP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPaySlipGroup().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        PaySlipGroup paySlipGroup = fetchExc(ent.getOID());
        ent = (Entity) paySlipGroup;
        return paySlipGroup.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PaySlipGroup) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PaySlipGroup) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static PaySlipGroup fetchExc(long oid) throws DBException {
        try {
            PaySlipGroup paySlipGroup = new PaySlipGroup();
            PstPaySlipGroup pstPaySlipGroup = new PstPaySlipGroup(oid);
            paySlipGroup.setOID(oid);

            paySlipGroup.setGroupName(pstPaySlipGroup.getString(FLD_PAYSLIP_GROUP_NAME));
            paySlipGroup.setGroupDesc(pstPaySlipGroup.getString(FLD_PAYSLIP_GROUP_DESC));

            return paySlipGroup;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlipGroup(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(PaySlipGroup paySlipGroup) throws DBException {
        try {
            PstPaySlipGroup pstPaySlipGroup = new PstPaySlipGroup(0);

            pstPaySlipGroup.setString(FLD_PAYSLIP_GROUP_NAME, paySlipGroup.getGroupName());
            pstPaySlipGroup.setString(FLD_PAYSLIP_GROUP_DESC, paySlipGroup.getGroupDesc());

            pstPaySlipGroup.insert();
            paySlipGroup.setOID(pstPaySlipGroup.getlong(FLD_PAYSLIP_GROUP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlipGroup(0), DBException.UNKNOWN);
        }
        return paySlipGroup.getOID();
    }

    public static long updateExc(PaySlipGroup paySlipGroup) throws DBException {
        try {
            if (paySlipGroup.getOID() != 0) {
                PstPaySlipGroup pstPaySlipGroup = new PstPaySlipGroup(paySlipGroup.getOID());

                pstPaySlipGroup.setString(FLD_PAYSLIP_GROUP_NAME, paySlipGroup.getGroupName());
                pstPaySlipGroup.setString(FLD_PAYSLIP_GROUP_DESC, paySlipGroup.getGroupDesc());

                pstPaySlipGroup.update();
                return paySlipGroup.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlipGroup(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPaySlipGroup pstPaySlipGroup = new PstPaySlipGroup(oid);
            pstPaySlipGroup.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlipGroup(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    /**
     * Keterangan: list payslip group
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAYSLIP_GROUP;
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
                PaySlipGroup paySlipGroup = new PaySlipGroup();
                resultToObject(rs, paySlipGroup);
                lists.add(paySlipGroup);
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
    
    public static Hashtable<String, PaySlipGroup> listInHashtable(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable<String, PaySlipGroup> lists = new Hashtable<String, PaySlipGroup>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAYSLIP_GROUP;
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
                PaySlipGroup paySlipGroup = new PaySlipGroup();
                resultToObject(rs, paySlipGroup);
                lists.put(""+paySlipGroup.getOID(), paySlipGroup);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }
    
    
    

    public static void resultToObject(ResultSet rs, PaySlipGroup paySlipGroup) {
        try {
            paySlipGroup.setOID(rs.getLong(PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_ID]));
            paySlipGroup.setGroupName(rs.getString(PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_NAME]));
            paySlipGroup.setGroupDesc(rs.getString(PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_DESC]));

        } catch (Exception e) {
            System.out.println("Exception"+e);
        }
    }
    
    /**
     * Keterangan : pengecekan apakah payslipGroup ini sudah di gunakan
     * @param oid : oid dari payslipGroupId
     * @return boolean
     */
    public static boolean checkUsePaySlipGroup(long oid) {
      DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_PAYSLIP_GROUP+ " WHERE "
                    + PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_ID] + " = '" + oid + "'";
            //masih belum jadi
            //sdad
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
    /**
     * Keterangan: mencari jumlah data count yg ada
     * @param whereClause
     * @return 
     */
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_ID] + ") FROM " + TBL_PAYSLIP_GROUP;
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


}
