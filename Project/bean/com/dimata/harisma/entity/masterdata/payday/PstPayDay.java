/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.payday;

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

/**
 *
 * @author satrya Ramayu
 */
public class PstPayDay extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_PAY_DAY = "hr_pay_day";
    public static final int FLD_PAY_DAY_ID = 0;
    public static final int FLD_EMP_CATEGORY_ID = 1;
    public static final int FLD_POSITION_ID = 2;
    public static final int FLD_VALUE_PAY_DAY = 3;
    public static String[] fieldNames = {
        "PAY_DAY_ID",
        "EMP_CATEGORY_ID",
        "POSITION_ID",
        "VALUE_PAY_DAY",
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT
    };

    public PstPayDay() {
    }

    public PstPayDay(int i) throws DBException {

        super(new PstPayDay());


    }

    public PstPayDay(String sOid) throws DBException {

        super(new PstPayDay(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstPayDay(long lOid) throws DBException {

        super(new PstPayDay(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_PAY_DAY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPayDay().getClass().getName();
    }

    /**
     * Keterangan: untuk mengambil data dari database berdasarkan oid
     * oidEmployee dan kemudaian di set objecknya
     *
     * @param oid : oidEmployee
     * @return
     * @throws DBException
     */
    public static PayDay fetchExc(long oid) throws DBException {

        try {
            PayDay payDay = new PayDay();
            PstPayDay pstPayDay = new PstPayDay(oid);
            payDay.setOID(oid);
            payDay.setEmpCategoryId(pstPayDay.getlong(FLD_EMP_CATEGORY_ID));
            payDay.setPositionId(pstPayDay.getlong(FLD_POSITION_ID));
            payDay.setValuePayDay(pstPayDay.getdouble(FLD_VALUE_PAY_DAY));


            return payDay;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayDay(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PayDay payDay = fetchExc(entity.getOID());
        entity = (Entity) payDay;
        return payDay.getOID();
    }

    /**
     * Keterangan: fungsi untuk update data to database create by satrya
     * 2013-09-27
     *
     * @param ConfigRewardAndPunishment
     * @return
     * @throws DBException
     */
    public static synchronized long updateExc(PayDay payDay) throws DBException {
        try {
            if (payDay.getOID() != 0) {
                PstPayDay pstPayDay = new PstPayDay(payDay.getOID()) {
                };
                pstPayDay.setLong(FLD_EMP_CATEGORY_ID, payDay.getEmpCategoryId());
                pstPayDay.setLong(FLD_POSITION_ID, payDay.getPositionId());
                pstPayDay.setDouble(FLD_VALUE_PAY_DAY, payDay.getValuePayDay());

                pstPayDay.update();

                return payDay.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayDay(0), DBException.UNKNOWN);
        }

        return 0;

    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PayDay) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstPayDay pstPayDay = new PstPayDay(oid) {
            };

            pstPayDay.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstPayDay(0) {
            }, DBException.UNKNOWN);

        }

        return oid;

    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    /**
     * Ketrangan: fungsi untuk melakukan insert to database
     *
     * @param ConfigRewardAndPunishment
     * @return
     * @throws DBException
     */
    public static synchronized long insertExc(PayDay payDay)
            throws DBException {
        try {

            PstPayDay pstPayDay = new PstPayDay(0);
            pstPayDay.setLong(FLD_EMP_CATEGORY_ID, payDay.getEmpCategoryId());
            pstPayDay.setLong(FLD_POSITION_ID, payDay.getPositionId());
            pstPayDay.setDouble(FLD_VALUE_PAY_DAY, payDay.getValuePayDay());

            pstPayDay.insert();

            payDay.setOID(pstPayDay.getlong(FLD_PAY_DAY_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayDay(0) {
            }, DBException.UNKNOWN);
        }
        return payDay.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PayDay) entity);
    }

    public static void resultToObject(ResultSet rs, PayDay payDay) {

        try {

            payDay.setOID(rs.getLong(PstPayDay.fieldNames[PstPayDay.FLD_PAY_DAY_ID]));
            payDay.setEmpCategoryId(rs.getLong(PstPayDay.fieldNames[PstPayDay.FLD_EMP_CATEGORY_ID]));
            payDay.setPositionId(rs.getLong(PstPayDay.fieldNames[PstPayDay.FLD_POSITION_ID]));
            payDay.setValuePayDay(rs.getDouble(PstPayDay.fieldNames[PstPayDay.FLD_VALUE_PAY_DAY]));
        } catch (Exception e) {
        }

    }

    /**
     * KETERANGAN: Fungsi untuk melakukan list table employee , berdasarkan
     * parameter di bawah
     *
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

            String sql = "SELECT * FROM " + TBL_HR_PAY_DAY;

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
                PayDay payDay = new PayDay();
                resultToObject(rs, payDay);
                lists.add(payDay);
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

    
    public static HashTblPayDay hashtblPayDay(int limitStart, int recordToGet, String whereClause, String order) {

        HashTblPayDay hashTblPayDay = new HashTblPayDay();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_HR_PAY_DAY;

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
                
                hashTblPayDay.addHashTblPayDayValueDwOnly(rs.getLong(fieldNames[FLD_EMP_CATEGORY_ID]), rs.getDouble(fieldNames[FLD_VALUE_PAY_DAY]));
                hashTblPayDay.addPayDayByPosition(rs.getLong(fieldNames[FLD_EMP_CATEGORY_ID]), rs.getLong(fieldNames[FLD_POSITION_ID]), rs.getDouble(fieldNames[FLD_VALUE_PAY_DAY]));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashTblPayDay;
    }

    
    
    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static boolean checkOID(long mSId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PAY_DAY + " WHERE "
                    + PstPayDay.fieldNames[PstPayDay.FLD_PAY_DAY_ID] + " = " + mSId;
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
     * keterangan : update nama by Id create by: devin tgl: 2013-11-21
     *
     * @param ConfigRewardAndPunishment
     * @return
     */
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstPayDay.fieldNames[PstPayDay.FLD_PAY_DAY_ID]
                    + ") FROM " + TBL_HR_PAY_DAY;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);//ambil isi ResultSet yg 1 atau PstEmployee.fieldNames[PstEmployee.FLD_JENIS_ITEM_ID] 
            }
            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * keterangan: limit
     *
     * @param oid : ini merupakan oid jenis Item
     * @param recordToGet
     * @param whereClause
     * @param orderClause
     * @return
     */
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    PayDay jenisItems = (PayDay) list.get(ls);
                    if (oid == jenisItems.getOID()) {
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
    /* This method used to find command where current data */

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
