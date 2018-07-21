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
import com.dimata.util.Formater;
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
public class PstEntriOpnameSales extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_ENTRI_OPNAME_SALES = "hr_entri_opname_sales";
    public static final int FLD_ENTRI_OPNAME_SALES_ID = 0;
    public static final int FLD_JENIS_SO_ID = 1;
    public static final int FLD_LOCATION_ID = 2;
    public static final int FLD_TYPE_OF_TOLERANCE = 3;
    public static final int FLD_NET_SALES_PERIOD = 4;
    public static final int FLD_PROSENTASE_TOLERANCE = 5;
    public static final int FLD_BARANG_HILANG = 6;
    public static final int FLD_CREATE_FORM_LOCATION_OPNAME = 7;
    public static final int FLD_PLUS_MINUS_HITUNG = 8;
    public static final int FLD_STATUS_OPNAME = 9;
    public static final int FLD_DATE_FROM_PERIODE = 10;
    public static final int FLD_DATE_TO_PERIODE = 11;
 //   public static final int FLD_STATUS_PROSESS = 12;
    public static String[] fieldNames = {
        "ENTRI_OPNAME_SALES_ID",
        "JENIS_SO_ID",
        "LOCATION_ID",
        "TYPE_OF_TOLERANCE",
        "NET_SALES_PERIOD",
        "PROSENTASE_TOLERANCE",
        "BARANG_HILANG",
        "CREATE_FORM_LOCATION_OPNAME",
        "PLUS_MINUS_HITUNG",
        "STATUS_OPNAME",
        "DATE_FROM_PERIODE",
        "DATE_TO_PERIODE"
      //  "STATUS_PROSESS" //berfungsi untuk mengenatui apakah statusnya sudah di prosess atau belum
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, //         "ENTRI_OPNAME_SALES_ID",
        TYPE_LONG,//        "JENIS_SO_ID",
        TYPE_LONG,//        "LOCATION_ID",
        TYPE_INT,//        "TYPE_OF_TOLERANCE",
        TYPE_FLOAT,//        "NET_SALES_PERIOD",
        TYPE_FLOAT,//        "PROSENTASE_TOLERANCE",
        TYPE_FLOAT,//        "BARANG_HILANG",
        TYPE_STRING,//        "CREATE_FORM_LOCATION_OPNAME"
        TYPE_FLOAT,//PLUS_MINUS_HITUNG
        TYPE_STRING,//STATUS_OPNAME
       TYPE_DATE,//"DATE_FROM_PERIODE",
       TYPE_DATE,  //"DATE_TO_PERIODE",
     //   TYPE_INT,//STATUS_PROSESS
    };

    public PstEntriOpnameSales() {
    }

    public PstEntriOpnameSales(int i) throws DBException {

        super(new PstEntriOpnameSales());


    }

    public PstEntriOpnameSales(String sOid) throws DBException {

        super(new PstEntriOpnameSales(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstEntriOpnameSales(long lOid) throws DBException {

        super(new PstEntriOpnameSales(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_ENTRI_OPNAME_SALES;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEntriOpnameSales().getClass().getName();
    }

    /**
     * Keterangan: untuk mengambil data dari database berdasarkan oid
     * oidEmployee dan kemudaian di set objecknya
     *
     * @param oid : oidEmployee
     * @return
     * @throws DBException
     */
    public static EntriOpnameSales fetchExc(long oid) throws DBException {

        try {
            EntriOpnameSales entriOpnameSales = new EntriOpnameSales();
            PstEntriOpnameSales pstEntriOpnameSales = new PstEntriOpnameSales(oid);
            entriOpnameSales.setOID(oid);
            entriOpnameSales.setLocationId(pstEntriOpnameSales.getLong(FLD_LOCATION_ID));
            entriOpnameSales.setJenisSoId(pstEntriOpnameSales.getLong(FLD_JENIS_SO_ID));
            entriOpnameSales.setTypeTolerance(pstEntriOpnameSales.getInt(FLD_TYPE_OF_TOLERANCE));
            entriOpnameSales.setNetSalesPeriod(pstEntriOpnameSales.getdouble(FLD_NET_SALES_PERIOD));
            entriOpnameSales.setProsentaseTolerance(pstEntriOpnameSales.getdouble(FLD_PROSENTASE_TOLERANCE));
            entriOpnameSales.setBarangHilang(pstEntriOpnameSales.getdouble(FLD_BARANG_HILANG));
            entriOpnameSales.setCreateLocationName(pstEntriOpnameSales.getString(FLD_CREATE_FORM_LOCATION_OPNAME));
            entriOpnameSales.setPlusMinus(pstEntriOpnameSales.getdouble(FLD_PLUS_MINUS_HITUNG));
            entriOpnameSales.setStatusOpname(pstEntriOpnameSales.getString(FLD_PLUS_MINUS_HITUNG));
            entriOpnameSales.setDtFromPeriod(pstEntriOpnameSales.getDate(FLD_DATE_FROM_PERIODE));
            entriOpnameSales.setDtToPeriod(pstEntriOpnameSales.getDate(FLD_DATE_TO_PERIODE));
          //  entriOpnameSales.setStatusProses(pstEntriOpnameSales.getInt(FLD_STATUS_PROSESS));
            return entriOpnameSales;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEntriOpnameSales(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        EntriOpnameSales EntriOpnameSales = fetchExc(entity.getOID());
        entity = (Entity) EntriOpnameSales;
        return EntriOpnameSales.getOID();
    }

    /**
     * Keterangan: fungsi untuk update data to database create by satrya
     * 2013-09-27
     *
     * @param EntriOpnameSales
     * @return
     * @throws DBException
     */
    public static synchronized long updateExc(EntriOpnameSales entriOpnameSales) throws DBException {
        try {
            if (entriOpnameSales.getOID() != 0) {
                PstEntriOpnameSales pstEntriOpnameSales = new PstEntriOpnameSales(entriOpnameSales.getOID());
                pstEntriOpnameSales.setLong(FLD_LOCATION_ID, entriOpnameSales.getLocationId());
                pstEntriOpnameSales.setLong(FLD_JENIS_SO_ID, entriOpnameSales.getJenisSoId());
                pstEntriOpnameSales.setInt(FLD_TYPE_OF_TOLERANCE, entriOpnameSales.getTypeTolerance());
                pstEntriOpnameSales.setDouble(FLD_NET_SALES_PERIOD, entriOpnameSales.getNetSalesPeriod());
                pstEntriOpnameSales.setDouble(FLD_PROSENTASE_TOLERANCE, entriOpnameSales.getProsentaseTolerance());
                pstEntriOpnameSales.setDouble(FLD_BARANG_HILANG, entriOpnameSales.getBarangHilang());
                pstEntriOpnameSales.setString(FLD_CREATE_FORM_LOCATION_OPNAME, entriOpnameSales.getCreateLocationName());
                pstEntriOpnameSales.setDouble(FLD_PLUS_MINUS_HITUNG, entriOpnameSales.getPlusMinus());
                pstEntriOpnameSales.setString(FLD_STATUS_OPNAME, entriOpnameSales.getStatusOpname());
                pstEntriOpnameSales.setDate(FLD_DATE_FROM_PERIODE, entriOpnameSales.getDtFromPeriod());
                pstEntriOpnameSales.setDate(FLD_DATE_TO_PERIODE, entriOpnameSales.getDtToPeriod());
               // pstEntriOpnameSales.setInt(FLD_STATUS_PROSESS, entriOpnameSales.getStatusProses());
                pstEntriOpnameSales.update();

                return entriOpnameSales.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEntriOpnameSales(0), DBException.UNKNOWN);
        }

        return 0;

    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((EntriOpnameSales) entity);
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

            PstEntriOpnameSales pstEntriOpnameSales = new PstEntriOpnameSales(oid) {
            };

            pstEntriOpnameSales.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstEntriOpnameSales(0) {
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
     * @param EntriOpnameSales
     * @return
     * @throws DBException
     */
    public static synchronized long insertExc(EntriOpnameSales entriOpnameSales)
            throws DBException {
        try {

            PstEntriOpnameSales pstEntriOpnameSales = new PstEntriOpnameSales(0);
            pstEntriOpnameSales.setLong(FLD_LOCATION_ID, entriOpnameSales.getLocationId());
            pstEntriOpnameSales.setLong(FLD_JENIS_SO_ID, entriOpnameSales.getJenisSoId());
            pstEntriOpnameSales.setInt(FLD_TYPE_OF_TOLERANCE, entriOpnameSales.getTypeTolerance());
            pstEntriOpnameSales.setDouble(FLD_NET_SALES_PERIOD, entriOpnameSales.getNetSalesPeriod());
            pstEntriOpnameSales.setDouble(FLD_PROSENTASE_TOLERANCE, entriOpnameSales.getProsentaseTolerance());
            pstEntriOpnameSales.setDouble(FLD_BARANG_HILANG, entriOpnameSales.getBarangHilang());
            pstEntriOpnameSales.setString(FLD_CREATE_FORM_LOCATION_OPNAME, entriOpnameSales.getCreateLocationName());
            pstEntriOpnameSales.setDouble(FLD_PLUS_MINUS_HITUNG, entriOpnameSales.getPlusMinus());
            pstEntriOpnameSales.setString(FLD_STATUS_OPNAME, entriOpnameSales.getStatusOpname());
             pstEntriOpnameSales.setDate(FLD_DATE_FROM_PERIODE, entriOpnameSales.getDtFromPeriod());
                pstEntriOpnameSales.setDate(FLD_DATE_TO_PERIODE, entriOpnameSales.getDtToPeriod());
          //  pstEntriOpnameSales.setInt(FLD_STATUS_PROSESS, entriOpnameSales.getStatusProses());
            pstEntriOpnameSales.insert();

            entriOpnameSales.setOID(pstEntriOpnameSales.getlong(FLD_ENTRI_OPNAME_SALES_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEntriOpnameSales(0) {
            }, DBException.UNKNOWN);
        }
        return entriOpnameSales.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((EntriOpnameSales) entity);
    }

    public static void resultToObject(ResultSet rs, EntriOpnameSales entriOpnameSales) {

        try {

            entriOpnameSales.setOID(rs.getLong(PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_ENTRI_OPNAME_SALES_ID]));
            entriOpnameSales.setLocationId(rs.getLong(fieldNames[FLD_LOCATION_ID]));
            entriOpnameSales.setJenisSoId(rs.getLong(fieldNames[FLD_JENIS_SO_ID]));
            entriOpnameSales.setTypeTolerance(rs.getInt(fieldNames[FLD_TYPE_OF_TOLERANCE]));
            entriOpnameSales.setNetSalesPeriod(rs.getDouble(fieldNames[FLD_NET_SALES_PERIOD]));
            entriOpnameSales.setProsentaseTolerance(rs.getDouble(fieldNames[FLD_PROSENTASE_TOLERANCE]));
            entriOpnameSales.setBarangHilang(rs.getDouble(fieldNames[FLD_BARANG_HILANG]));
            entriOpnameSales.setCreateLocationName(rs.getString(fieldNames[FLD_CREATE_FORM_LOCATION_OPNAME]));
            entriOpnameSales.setPlusMinus(rs.getDouble(fieldNames[FLD_PLUS_MINUS_HITUNG]));
            entriOpnameSales.setStatusOpname(rs.getString(fieldNames[FLD_PLUS_MINUS_HITUNG]));
            entriOpnameSales.setDtFromPeriod(rs.getDate(fieldNames[FLD_DATE_FROM_PERIODE]));
            entriOpnameSales.setDtToPeriod(rs.getDate(fieldNames[FLD_DATE_TO_PERIODE]));
        //    entriOpnameSales.setStatusProses(rs.getInt(fieldNames[FLD_STATUS_PROSESS]));
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

            String sql = "SELECT * FROM " + TBL_HR_ENTRI_OPNAME_SALES;

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
                EntriOpnameSales entriOpnameSales = new EntriOpnameSales();
                resultToObject(rs, entriOpnameSales);
                lists.add(entriOpnameSales);
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
    
    
    /**
     * 
     * @param limitStart
     * @param recordToGet
     * @param order
     * @return 
     */
    public static Vector listWithParam(int limitStart, int recordToGet,String order,SrcEntriOpnameSales entriOpnameSales) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_HR_ENTRI_OPNAME_SALES;
            sql= sql + " WHERE (1=1)";
            if(entriOpnameSales!=null){
                if(entriOpnameSales.getLocationId()!=0){
                    sql = sql + " AND " + fieldNames[FLD_LOCATION_ID]+"="+entriOpnameSales.getLocationId();
                }
                if(entriOpnameSales.getJenisSoId()!=0){
                    sql = sql + " AND " + fieldNames[FLD_JENIS_SO_ID]+"="+entriOpnameSales.getJenisSoId();
                }
                if(entriOpnameSales.getDtFromPeriod()!=null && entriOpnameSales.getDtToPeriod()!=null){
                   // sql = sql + " AND " + fieldNames[FLD_PERIOD_ID]+"="+entriOpnameSales.getPeriodId();
                    sql= sql + " AND " + fieldNames[FLD_DATE_FROM_PERIODE] + " = \"" +Formater.formatDate(entriOpnameSales.getDtFromPeriod(), "yyyy-MM-dd" + " \"")
                             + " AND " + fieldNames[FLD_DATE_TO_PERIODE]   + " = \"" +Formater.formatDate(entriOpnameSales.getDtToPeriod(), "yyyy-MM-dd" + " \"");
                    //   sql = sql + "\""+Formater.formatDate(entriOpnameSales.getDtToPeriod(), "yyyy-MM-dd")+"\" = " 
                    // + fieldNames[FLD_DATE_FROM_PERIODE]+ " AND "
                    // + fieldNames[FLD_DATE_TO_PERIODE] + " = \""+Formater.formatDate(entriOpnameSales.getDtFromPeriod(), "yyyy-MM-dd")+"\"";
                }
                if(entriOpnameSales.getTypeTolerance()!=-1){
                    sql = sql + " AND " + fieldNames[FLD_TYPE_OF_TOLERANCE]+"="+entriOpnameSales.getTypeTolerance();
                }
                if(entriOpnameSales.getCreateLocationName()!=null){
                    sql = sql + " AND " + fieldNames[FLD_CREATE_FORM_LOCATION_OPNAME]+" LIKE '%"+entriOpnameSales.getCreateLocationName()+"%'";
                }
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
                EntriOpnameSales objEntriOpnameSales = new EntriOpnameSales();
                resultToObject(rs, objEntriOpnameSales);
                lists.add(objEntriOpnameSales);
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
            String sql = "SELECT * FROM " + TBL_HR_ENTRI_OPNAME_SALES + " WHERE "
                    + PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_ENTRI_OPNAME_SALES_ID] + " = " + mSId;
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

    //priska 2014-12-08
    public static int gettypetoleransi(String whereClause) {

        int nilaiku=0;
//
//        SELECT `EOS`.`TYPE_OF_TOLERANCE`
//FROM `hr_reward_punisment_main` AS RPM, `hr_entri_opname_sales` AS EOS
//WHERE `RPM`.`ENTRI_OPNAME_SALES_ID` = `EOS`.`ENTRI_OPNAME_SALES_ID`
        
      DBResultSet dbrs = null;
        try {
            String sql = "SELECT EOS."+PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_TYPE_OF_TOLERANCE]
                       + " FROM "+PstRewardAndPunishmentMain.TBL_HR_REWARD_PUNISMENT_MAIN+" AS RPM, "
                       +  PstEntriOpnameSales.TBL_HR_ENTRI_OPNAME_SALES+" AS EOS";
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
          
            while (rs.next()) {
                nilaiku= rs.getInt(1);//nilainya
            }
            rs.close();
            return nilaiku;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static double getnilaiopname(String whereClause) {

        double nilaiku=0;
        
      DBResultSet dbrs = null;
        try {
            String sql = "SELECT RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_NILAI_STATUS_OPNAME]
                       + " FROM "+PstRewardAndPunishmentMain.TBL_HR_REWARD_PUNISMENT_MAIN+" AS RPM";
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE RPM." + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
          
            while (rs.next()) {
                nilaiku= rs.getInt(1);//nilainya
            }
            rs.close();
            return nilaiku;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    /**
     * keterangan : update nama by Id create by: devin tgl: 2013-11-21
     *
     * @param EntriOpnameSales
     * @return
     */
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_ENTRI_OPNAME_SALES_ID]
                    + ") FROM " + TBL_HR_ENTRI_OPNAME_SALES;
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
    
    public static int getCountWithParam(SrcEntriOpnameSales entriOpnameSales) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_ENTRI_OPNAME_SALES_ID]
                    + ") FROM " + TBL_HR_ENTRI_OPNAME_SALES;
            sql= sql + " WHERE (1=1)";
            if(entriOpnameSales!=null){
                if(entriOpnameSales.getLocationId()!=0){
                    sql = sql + " AND " + fieldNames[FLD_LOCATION_ID]+"="+entriOpnameSales.getLocationId();
                }
                if(entriOpnameSales.getJenisSoId()!=0){
                    sql = sql + " AND " + fieldNames[FLD_JENIS_SO_ID]+"="+entriOpnameSales.getJenisSoId();
                }
                if(entriOpnameSales.getDtFromPeriod()!=null && entriOpnameSales.getDtToPeriod()!=null){
                   // sql = sql + " AND " + fieldNames[FLD_PERIOD_ID]+"="+entriOpnameSales.getPeriodId();
                     sql = sql + "\""+Formater.formatDate(entriOpnameSales.getDtToPeriod(), "yyyy-MM-dd")+"\" > " 
       + fieldNames[FLD_DATE_FROM_PERIODE]+ " AND "
       + fieldNames[FLD_DATE_TO_PERIODE] + " > \""+Formater.formatDate(entriOpnameSales.getDtFromPeriod(), "yyyy-MM-dd")+"\"";
                }
                if(entriOpnameSales.getTypeTolerance()!=-1){
                    sql = sql + " AND " + fieldNames[FLD_TYPE_OF_TOLERANCE]+"="+entriOpnameSales.getTypeTolerance();
                }
                if(entriOpnameSales.getCreateLocationName()!=null){
                    sql = sql + " AND " + fieldNames[FLD_CREATE_FORM_LOCATION_OPNAME]+" LIKE '%"+entriOpnameSales.getCreateLocationName()+"%'";
                }
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
                    EntriOpnameSales jenisItems = (EntriOpnameSales) list.get(ls);
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
    
    public static long getEntriOpnameSalesId (long oidEntriOpname){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_ENTRI_OPNAME_SALES 
                    +" WHERE " + fieldNames[FLD_ENTRI_OPNAME_SALES_ID]+"="+oidEntriOpname;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            long oidEntriOpnamex = 0;
            while(rs.next()) { oidEntriOpnamex = rs.getLong(1); }
            
            rs.close();
            return oidEntriOpnamex;
            
        }catch(Exception e) {
           return 0;
        }finally {
            DBResultSet.close(dbrs);
        }
        
    }
}

