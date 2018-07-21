/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.configrewardnpunisment;

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
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Devin
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Devin
 */
public class PstRewardAndPunishmentMain extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_REWARD_PUNISMENT_MAIN = "hr_reward_punisment_main";
    public static final int FLD_REWARD_PUNISMENT_MAIN_ID = 0;
    public static final int FLD_DETAIL_NBH_NO = 1;
    public static final int FLD_JENIS_SO_ID = 2;
    public static final int FLD_LOCATION_ID = 3;
    public static final int FLD_DATE_CREATE_DOC = 4;
    public static final int FLD_STATUS_DOC = 5;
    public static final int FLD_APPROVAL_ONE = 6;
    public static final int FLD_APPROVAL_TWO = 7;
    public static final int FLD_APPROVAL_THREE = 8;
    public static final int FLD_COUNT_IDX = 9;
    public static final int FLD_NET_SALES_PERIOD = 10;
    public static final int FLD_BARANG_HILANG = 11;
    public static final int FLD_STATUS_OPNAME = 12;
    public static final int FLD_NILAI_STATUS_OPNAME = 13;
    public static final int FLD_CREATE_FORM_MAIN = 14;
     public static final int FLD_START_DATE_PERIOD = 15;
     public static final int FLD_END_DATE_PERIOD = 16;
     public static final int FLD_ENTRI_OPNAME_SALES_ID = 17;
    
    public static String[] fieldNames = {
        "REWARD_PUNISMENT_MAIN_ID",
        "DETAIL_NBH_NO",
        "JENIS_SO_ID",
        "LOCATION_ID",
        "DATE_CREATE_DOC",
        "STATUS_DOC",
        "APPROVAL_ONE",
        "APPROVAL_TWO",
        "APPROVAL_THREE",
        "COUNT_IDX",
        "NET_SALES_PERIOD",
        "BARANG_HILANG",
        "STATUS_OPNAME",
        "NILAI_STATUS_OPNAME",
        "CREATE_FORM_MAIN",
        "START_DATE_PERIOD",
        "END_DATE_PERIOD",
        "ENTRI_OPNAME_SALES_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,//"REWARD_PUNISMENT_MAIN_ID",
        TYPE_LONG,//"DETAIL_NBH_NO",
        TYPE_LONG,//"JENIS_SO_ID",
        TYPE_LONG,//"LOCATION_ID",

        TYPE_DATE,//"DATE_CREATE_DOC",
        TYPE_INT,//"STATUS_DOC",
        TYPE_LONG,//"APPROVAL_ONE",
        TYPE_LONG,//"APPROVAL_TWO",
        TYPE_LONG,//"APPROVAL_THREE",
        TYPE_INT,//COUNT_IDX,
        TYPE_FLOAT,//"NET_SALES_PERIOD",
        TYPE_FLOAT,//"BARANG_HILANG",
        TYPE_STRING,//"STATUS_OPNAME",
        TYPE_FLOAT,//"NILAI_STATUS_OPNAME",
        TYPE_STRING,//CREATE_FORM_MAIN
        TYPE_DATE,//"START_DATE_PERIOD",
        TYPE_DATE,//"END_DATE_PERIOD"
        TYPE_LONG,//"ENTRI_OPNAME_SALES_ID"
    };

    public PstRewardAndPunishmentMain() {
    }

    public PstRewardAndPunishmentMain(int i) throws DBException {
        super(new PstRewardAndPunishmentMain());
    }

    public PstRewardAndPunishmentMain(String sOid) throws DBException {

        super(new PstRewardAndPunishmentMain(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstRewardAndPunishmentMain(long lOid) throws DBException {

        super(new PstRewardAndPunishmentMain(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_REWARD_PUNISMENT_MAIN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstRewardAndPunishmentMain().getClass().getName();
    }

    /**
     * Keterangan: untuk mengambil data dari database berdasarkan oid
     * oidEmployee dan kemudaian di set objecknya
     *
     * @param oid : oidEmployee
     * @return
     * @throws DBException
     */
    public static RewardnPunismentMain fetchExc(long oid) throws DBException {

        try {
            RewardnPunismentMain rewardnPunismentMain = new RewardnPunismentMain();
            PstRewardAndPunishmentMain pstRewardnPunismentMain = new PstRewardAndPunishmentMain(oid);
            rewardnPunismentMain.setOID(oid);
            rewardnPunismentMain.setDetailNbhNo(pstRewardnPunismentMain.getString(FLD_DETAIL_NBH_NO));
            rewardnPunismentMain.setJenisSoId(pstRewardnPunismentMain.getlong(FLD_JENIS_SO_ID));
            rewardnPunismentMain.setLocationId(pstRewardnPunismentMain.getlong(FLD_LOCATION_ID));
            rewardnPunismentMain.setStatusDoc(pstRewardnPunismentMain.getInt(FLD_STATUS_DOC));
            rewardnPunismentMain.setDtCreateDocument(pstRewardnPunismentMain.getDate(FLD_DATE_CREATE_DOC));
            rewardnPunismentMain.setApprovallOne(pstRewardnPunismentMain.getlong(FLD_APPROVAL_ONE));
            rewardnPunismentMain.setApprovallTwo(pstRewardnPunismentMain.getlong(FLD_APPROVAL_TWO));
            rewardnPunismentMain.setApprovallThree(pstRewardnPunismentMain.getlong(FLD_APPROVAL_THREE));
            rewardnPunismentMain.setCountIdx(pstRewardnPunismentMain.getInt(FLD_COUNT_IDX));
            rewardnPunismentMain.setNetSales(pstRewardnPunismentMain.getdouble(FLD_NET_SALES_PERIOD));
            rewardnPunismentMain.setBarangHilang(pstRewardnPunismentMain.getdouble(FLD_BARANG_HILANG));
            rewardnPunismentMain.setStatusOpname(pstRewardnPunismentMain.getString(FLD_STATUS_OPNAME));
            rewardnPunismentMain.setNilaiStatusOpname(pstRewardnPunismentMain.getInt(FLD_NILAI_STATUS_OPNAME));
            rewardnPunismentMain.setCreateFormMain(pstRewardnPunismentMain.getString(FLD_CREATE_FORM_MAIN));
            rewardnPunismentMain.setDtFromPeriod(pstRewardnPunismentMain.getDate(FLD_START_DATE_PERIOD));
            rewardnPunismentMain.setDtToPeriod(pstRewardnPunismentMain.getDate(FLD_END_DATE_PERIOD));
            
             rewardnPunismentMain.setEntriOpnameId(pstRewardnPunismentMain.getLong(FLD_ENTRI_OPNAME_SALES_ID));

            return rewardnPunismentMain;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentMain(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        RewardnPunismentMain rewardnPunismentMain = fetchExc(entity.getOID());
        entity = (Entity) rewardnPunismentMain;
        return rewardnPunismentMain.getOID();
    }

    /**
     * Keterangan: fungsi untuk update data to database create by satrya
     * 2013-09-27
     *
     * @param RewardnPunismentMain
     * @return
     * @throws DBException
     */
    public static synchronized long updateExc(RewardnPunismentMain rewardnPunismentMain) throws DBException {
        try {
            if (rewardnPunismentMain.getOID() != 0) {
                
                PstRewardAndPunishmentMain pstRewardnPunismentMain = new PstRewardAndPunishmentMain(rewardnPunismentMain.getOID());
                RewardnPunismentMain rewardnPunismentMainMax = fetchRewardNpunismentMainNumMax();
            String x="RP-";

            if (rewardnPunismentMainMax != null && rewardnPunismentMainMax.getOID() != 0 && rewardnPunismentMainMax.getCountIdx() != 0) {
                int n = 1;
               
                try {
                    n = rewardnPunismentMainMax.getCountIdx() + 1;
                } catch (Exception exc) {
                    // klu gagal
                    n = rewardnPunismentMainMax.getCountIdx() + 1; // 
                }
                rewardnPunismentMain.setDetailNbhNo(x+ n);
            } else {
                 rewardnPunismentMain.setDetailNbhNo(x+ 1);
            }
            pstRewardnPunismentMain.setString(FLD_DETAIL_NBH_NO, "\""+rewardnPunismentMain.getDetailNbhNo()+"\"");
                pstRewardnPunismentMain.setLong(FLD_JENIS_SO_ID, rewardnPunismentMain.getJenisSoId());
                pstRewardnPunismentMain.setLong(FLD_LOCATION_ID, rewardnPunismentMain.getLocationId());
              
                pstRewardnPunismentMain.setInt(FLD_STATUS_DOC, rewardnPunismentMain.getStatusDoc());
                pstRewardnPunismentMain.setDate(FLD_DATE_CREATE_DOC, rewardnPunismentMain.getDtCreateDocument());
                pstRewardnPunismentMain.setLong(FLD_APPROVAL_ONE, rewardnPunismentMain.getApprovallOne());
                pstRewardnPunismentMain.setLong(FLD_APPROVAL_TWO, rewardnPunismentMain.getApprovallTwo());
                pstRewardnPunismentMain.setLong(FLD_APPROVAL_THREE, rewardnPunismentMain.getApprovallThree());
                pstRewardnPunismentMain.setInt(FLD_COUNT_IDX, rewardnPunismentMain.getCountIdx());
                
                pstRewardnPunismentMain.setDouble(FLD_NET_SALES_PERIOD, rewardnPunismentMain.getNetSales());
                pstRewardnPunismentMain.setDouble(FLD_BARANG_HILANG, rewardnPunismentMain.getBarangHilang());
                pstRewardnPunismentMain.setString(FLD_STATUS_OPNAME, rewardnPunismentMain.getStatusOpname());
                pstRewardnPunismentMain.setDouble(FLD_NILAI_STATUS_OPNAME, rewardnPunismentMain.getNilaiStatusOpname());
                pstRewardnPunismentMain.setString(FLD_CREATE_FORM_MAIN, rewardnPunismentMain.getCreateFormMain());
              
                pstRewardnPunismentMain.setDate(FLD_START_DATE_PERIOD, rewardnPunismentMain.getDtFromPeriod());
                pstRewardnPunismentMain.setDate(FLD_END_DATE_PERIOD, rewardnPunismentMain.getDtToPeriod());
                
                pstRewardnPunismentMain.setLong(FLD_ENTRI_OPNAME_SALES_ID, rewardnPunismentMain.getEntriOpnameId());
              
                pstRewardnPunismentMain.update();

                return rewardnPunismentMain.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentMain(0), DBException.UNKNOWN);
        }

        return 0;

    }

    
  
    
    
    public long updateExc(Entity entity) throws Exception {
        return updateExc((RewardnPunismentMain) entity);
    }

    /**
     * Keterangan: delete data employee
     *
     * @param oid
     * @return
     * @throws DBException
     */
    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstRewardAndPunishmentMain pstRewardnPunismentMain = new PstRewardAndPunishmentMain(oid) {
            };

            pstRewardnPunismentMain.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstRewardAndPunishmentMain(0) {
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

    
    public static synchronized long insertExc(RewardnPunismentMain rewardnPunismentMain)
            throws DBException {
        try {

        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentMain(0) {
            }, DBException.UNKNOWN);
        }
        return 0;
    }

    /**
     * Ketrangan: fungsi untuk melakukan insert to database
     *
     * @param RewardnPunismentMain
     * @return
     * @throws DBException
     */
    public static synchronized RewardnPunismentMain insertExcs(RewardnPunismentMain rewardnPunismentMain)
            throws DBException {
        try {

            PstRewardAndPunishmentMain pstRewardnPunismentMain = new PstRewardAndPunishmentMain(0);

            
            pstRewardnPunismentMain.setLong(FLD_JENIS_SO_ID, rewardnPunismentMain.getJenisSoId());
            pstRewardnPunismentMain.setLong(FLD_LOCATION_ID, rewardnPunismentMain.getLocationId());
            pstRewardnPunismentMain.setInt(FLD_STATUS_DOC, rewardnPunismentMain.getStatusDoc());
            pstRewardnPunismentMain.setDate(FLD_DATE_CREATE_DOC, rewardnPunismentMain.getDtCreateDocument());
            pstRewardnPunismentMain.setLong(FLD_APPROVAL_ONE, rewardnPunismentMain.getApprovallOne());
            pstRewardnPunismentMain.setLong(FLD_APPROVAL_TWO, rewardnPunismentMain.getApprovallTwo());
            pstRewardnPunismentMain.setLong(FLD_APPROVAL_THREE, rewardnPunismentMain.getApprovallThree());
            pstRewardnPunismentMain.setDouble(FLD_NET_SALES_PERIOD, rewardnPunismentMain.getNetSales());
            pstRewardnPunismentMain.setDouble(FLD_BARANG_HILANG, rewardnPunismentMain.getBarangHilang());
            pstRewardnPunismentMain.setString(FLD_STATUS_OPNAME, rewardnPunismentMain.getStatusOpname());
            pstRewardnPunismentMain.setDouble(FLD_NILAI_STATUS_OPNAME, rewardnPunismentMain.getNilaiStatusOpname());
            pstRewardnPunismentMain.setString(FLD_CREATE_FORM_MAIN, rewardnPunismentMain.getCreateFormMain());
            pstRewardnPunismentMain.setDate(FLD_START_DATE_PERIOD, rewardnPunismentMain.getDtFromPeriod());
            pstRewardnPunismentMain.setDate(FLD_END_DATE_PERIOD, rewardnPunismentMain.getDtToPeriod());
            pstRewardnPunismentMain.setLong(FLD_ENTRI_OPNAME_SALES_ID, rewardnPunismentMain.getEntriOpnameId());
            RewardnPunismentMain rewardnPunismentMainMax = fetchRewardNpunismentMainNumMax();
            //String x = "RP-";
            String x="RP-";
            /*if(rewardnPunismentMain.getStatusOpname()!=null && rewardnPunismentMain.getStatusOpname().length()>0){
                /*if(rewardnPunismentMain.getStatusOpname().equalsIgnoreCase(EntriOpnameSales.fieldNamesStatus[EntriOpnameSales.STATUS_REWARD])){
                    x = "R-";
                }else{
                    x = "P-";
                }
            }*/
            if (rewardnPunismentMainMax != null && rewardnPunismentMainMax.getOID() != 0 && rewardnPunismentMainMax.getCountIdx() != 0) {
                int n = 1;
               
                try {
                    n = rewardnPunismentMainMax.getCountIdx() + 1;
                } catch (Exception exc) {
                    // klu gagal
                    n = rewardnPunismentMainMax.getCountIdx() + 1; // 
                }
                rewardnPunismentMain.setDetailNbhNo(x+ n);
            } else {
                 rewardnPunismentMain.setDetailNbhNo(x+ 1);
            }
            pstRewardnPunismentMain.setString(FLD_DETAIL_NBH_NO, "\""+rewardnPunismentMain.getDetailNbhNo()+"\"");
            pstRewardnPunismentMain.insert();

            rewardnPunismentMain.setOID(pstRewardnPunismentMain.getlong(FLD_REWARD_PUNISMENT_MAIN_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentMain(0) {
            }, DBException.UNKNOWN);
        }
        return rewardnPunismentMain;
    }

    public static RewardnPunismentMain fetchRewardNpunismentMainNumMax() {
        RewardnPunismentMain rewardnPunismentMain = new RewardnPunismentMain();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_REWARD_PUNISMENT_MAIN + "  ORDER BY " + fieldNames[FLD_COUNT_IDX] + " DESC LIMIT 0,1";
            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, rewardnPunismentMain);
            }
            rs.close();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rewardnPunismentMain;
        }

    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((RewardnPunismentMain) entity);
    }

    public static void resultToObject(ResultSet rs, RewardnPunismentMain rewardnPunismentMain) {

        try {

            rewardnPunismentMain.setOID(rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID]));
            rewardnPunismentMain.setDetailNbhNo(rs.getString(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_DETAIL_NBH_NO]));
            rewardnPunismentMain.setJenisSoId(rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_JENIS_SO_ID]));
            rewardnPunismentMain.setLocationId(rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_LOCATION_ID]));
                

            rewardnPunismentMain.setStatusDoc(rs.getInt(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_DOC]));
            rewardnPunismentMain.setDtCreateDocument(rs.getDate(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_DATE_CREATE_DOC]));
            rewardnPunismentMain.setApprovallOne(rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_APPROVAL_ONE]));
            rewardnPunismentMain.setApprovallTwo(rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_APPROVAL_TWO]));
            rewardnPunismentMain.setApprovallThree(rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_APPROVAL_THREE]));
            rewardnPunismentMain.setCountIdx(rs.getInt(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_COUNT_IDX]));
            
            
            rewardnPunismentMain.setNetSales(rs.getDouble(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_NET_SALES_PERIOD]));
            rewardnPunismentMain.setBarangHilang(rs.getDouble(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_BARANG_HILANG]));
            rewardnPunismentMain.setStatusOpname(rs.getString(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]));
            rewardnPunismentMain.setNilaiStatusOpname(rs.getDouble(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_NILAI_STATUS_OPNAME]));

            rewardnPunismentMain.setCreateFormMain(rs.getString(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_CREATE_FORM_MAIN]));
            
            rewardnPunismentMain.setDtFromPeriod(rs.getDate(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_START_DATE_PERIOD]));
            rewardnPunismentMain.setDtToPeriod(rs.getDate(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_END_DATE_PERIOD]));
            
            rewardnPunismentMain.setEntriOpnameId(rs.getLong(fieldNames[FLD_ENTRI_OPNAME_SALES_ID]));
//set OID employee dari FLD_EMPLOYEE_ID


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

            String sql = "SELECT * FROM " + TBL_HR_REWARD_PUNISMENT_MAIN;

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
                RewardnPunismentMain rewardnPunismentMain = new RewardnPunismentMain();
                resultToObject(rs, rewardnPunismentMain);
                lists.add(rewardnPunismentMain);
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

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static boolean checkOID(long mSId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_REWARD_PUNISMENT_MAIN + " WHERE "
                    + PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID] + " = " + mSId;
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
     * @param RewardnPunismentMain
     * @return
     */
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID]
                    + ") FROM " + TBL_HR_REWARD_PUNISMENT_MAIN;
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
                    RewardnPunismentMain jenisItems = (RewardnPunismentMain) list.get(ls);
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
    
        /**
     * keterangan : update nama by Id create by: priska tgl: 2014-12-3
     *
     * @param RewardnPunismentMain
     * @return
     */
    public static long getMainIdWhereEntriOpname(long entriopname) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID]
                    + " FROM " + TBL_HR_REWARD_PUNISMENT_MAIN;
                   sql = sql + " WHERE "+ PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID] +" = " + entriopname;
            
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            long mainId = 0;
            while (rs.next()) {
                mainId = rs.getLong(1);//ambil isi ResultSet yg 1 atau PstEmployee.fieldNames[PstEmployee.FLD_JENIS_ITEM_ID] 
            }
            rs.close();
            return mainId;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    
    /**
     * mengetahui apakaha sudah ada id'nya / datanya
     * @return 
     */
     public static Hashtable hashListRewatPunismentMainAda() {

        Hashtable lists = new Hashtable();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_HR_REWARD_PUNISMENT_MAIN;

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                long oidOpnameSales=rs.getLong(fieldNames[FLD_ENTRI_OPNAME_SALES_ID]);
                 RewardnPunismentMain rewardnPunismentMain = new RewardnPunismentMain();
                resultToObject(rs, rewardnPunismentMain);
                lists.put(""+oidOpnameSales, rewardnPunismentMain);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
             return lists;
        }
       
    }

}
