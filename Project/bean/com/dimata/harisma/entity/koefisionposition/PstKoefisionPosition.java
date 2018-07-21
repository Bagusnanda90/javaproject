/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.koefisionposition;

import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstPosition;
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
public class PstKoefisionPosition extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_KOEFISION_POSITION = "hr_koefision_position";
    public static final int FLD_KOEFISION_POSITION_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_NILAI_KOEFISION_OUTLET = 2;
    public static final int FLD_NILAI_KOEFISION_DC = 3;
    public static String[] fieldNames = {
        "KOEFISION_POSITION_ID",
        "POSITION_ID",
        "NILAI_KOEFISION_OUTLET",
        "NILAI_KOEFISION_DC"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,};

    public PstKoefisionPosition() {
    }

    public PstKoefisionPosition(int i) throws DBException {

        super(new PstKoefisionPosition());


    }

    public PstKoefisionPosition(String sOid) throws DBException {

        super(new PstKoefisionPosition(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstKoefisionPosition(long lOid) throws DBException {

        super(new PstKoefisionPosition(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_KOEFISION_POSITION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKoefisionPosition().getClass().getName();
    }

    /**
     * Keterangan: untuk mengambil data dari database berdasarkan oid
     * oidEmployee dan kemudaian di set objecknya
     *
     * @param oid : oidEmployee
     * @return
     * @throws DBException
     */
    public static KoefisionPosition fetchExc(long oid) throws DBException {

        try {
            KoefisionPosition koefisionPosition = new KoefisionPosition();
            PstKoefisionPosition pstKoefisionPosition = new PstKoefisionPosition(oid);
            koefisionPosition.setOID(oid);
            koefisionPosition.setPositionId(pstKoefisionPosition.getLong(FLD_POSITION_ID));
            koefisionPosition.setNilaiKoefisionOutlet(pstKoefisionPosition.getdouble(FLD_NILAI_KOEFISION_OUTLET));
            koefisionPosition.setNilaiKoefisionDc(pstKoefisionPosition.getdouble(FLD_NILAI_KOEFISION_DC));



            return koefisionPosition;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKoefisionPosition(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        KoefisionPosition koefisionPosition = fetchExc(entity.getOID());
        entity = (Entity) koefisionPosition;
        return koefisionPosition.getOID();
    }

    /**
     * Keterangan: fungsi untuk update data to database create by satrya
     * 2013-09-27
     *
     * @param ConfigRewardAndPunishment
     * @return
     * @throws DBException
     */
    public static synchronized long updateExc(KoefisionPosition koefisionPosition) throws DBException {
        try {
            if (koefisionPosition.getOID() != 0) {
                PstKoefisionPosition pstKoefisionPosition = new PstKoefisionPosition(koefisionPosition.getOID()) {
                };
                pstKoefisionPosition.setLong(FLD_POSITION_ID, koefisionPosition.getPositionId());
                pstKoefisionPosition.setDouble(FLD_NILAI_KOEFISION_OUTLET, koefisionPosition.getNilaiKoefisionOutlet());
                pstKoefisionPosition.setDouble(FLD_NILAI_KOEFISION_DC, koefisionPosition.getNilaiKoefisionDc());




                pstKoefisionPosition.update();

                return koefisionPosition.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKoefisionPosition(0), DBException.UNKNOWN);
        }

        return 0;

    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((KoefisionPosition) entity);
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

            PstKoefisionPosition pstKoefisionPosition = new PstKoefisionPosition(oid) {
            };

            pstKoefisionPosition.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstKoefisionPosition(0) {
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
    public static synchronized long insertExc(KoefisionPosition koefisionPosition)
            throws DBException {
        try {

            PstKoefisionPosition pstKoefisionPosition = new PstKoefisionPosition(0);

            pstKoefisionPosition.setLong(FLD_POSITION_ID, koefisionPosition.getPositionId());
            pstKoefisionPosition.setDouble(FLD_NILAI_KOEFISION_OUTLET, koefisionPosition.getNilaiKoefisionOutlet());
            pstKoefisionPosition.setDouble(FLD_NILAI_KOEFISION_DC, koefisionPosition.getNilaiKoefisionDc());



            pstKoefisionPosition.insert();

            koefisionPosition.setOID(pstKoefisionPosition.getlong(FLD_KOEFISION_POSITION_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKoefisionPosition(0) {
            }, DBException.UNKNOWN);
        }
        return koefisionPosition.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((KoefisionPosition) entity);
    }

    public static void resultToObject(ResultSet rs, KoefisionPosition koefisionPosition) {

        try {

            koefisionPosition.setOID(rs.getLong(PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]));
            koefisionPosition.setPositionId(rs.getLong(PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_POSITION_ID]));
            koefisionPosition.setNilaiKoefisionOutlet(rs.getDouble(PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_OUTLET]));
            koefisionPosition.setNilaiKoefisionDc(rs.getDouble(PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_DC]));



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

            String sql = "SELECT * FROM " + TBL_HR_KOEFISION_POSITION;

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
                KoefisionPosition koefisionPosition = new KoefisionPosition();
                resultToObject(rs, koefisionPosition);

                lists.add(koefisionPosition);
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
     * key: positionId
     * value:objPosition
     * hashtable koefisient position
     * @return 
     */
    public static Hashtable hashKoefisienPosition() {

        Hashtable lists = new Hashtable();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_HR_KOEFISION_POSITION;

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                KoefisionPosition koefisionPosition = new KoefisionPosition();
                resultToObject(rs, koefisionPosition);
                lists.put(koefisionPosition.getPositionId(), koefisionPosition);
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

    
    /**
     * list obj Koefisient position
     * @return 
     */
    public static Hashtable HashObjAllKoefisientPosition() {

        Hashtable lists = new Hashtable();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_HR_KOEFISION_POSITION;

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                KoefisionPosition koefisionPosition = new KoefisionPosition();
                resultToObject(rs, koefisionPosition);

                lists.put(koefisionPosition.getPositionId(), koefisionPosition);
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

     public static double koefisiennilai(String whereClause, String nilai) {

        double nilaiku=0;

      DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + nilai
                        + " FROM " + TBL_HR_KOEFISION_POSITION;
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
    
    //update by devin 2014-03-28
    public static Vector innerJoinList(int limit, int recordToGet) {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT koe.*," + " pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION] + " FROM " + TBL_HR_KOEFISION_POSITION + " AS koe INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON koe." + PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_POSITION_ID]
                    + "=pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];
            if (limit == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limit + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                KoefisionPosition koefisionPosition = new KoefisionPosition();
                resultToObject(rs, koefisionPosition);
                koefisionPosition.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                result.add(koefisionPosition);
            }

        } catch (Exception exc) {
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static boolean checkOID(long mSId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KOEFISION_POSITION + " WHERE "
                    + PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID] + " = " + mSId;
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
            String sql = "SELECT COUNT(" + PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]
                    + ") FROM " + TBL_HR_KOEFISION_POSITION;
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
                    KoefisionPosition jenisItems = (KoefisionPosition) list.get(ls);
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

    //priska 2014-11-30
    public static Vector carinamaposisi(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {
            String  sql  = "SELECT P." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                         + " FROM    " + PstKoefisionPosition.TBL_HR_KOEFISION_POSITION +" AS KP, "
                                       + PstPosition.TBL_HR_POSITION +" AS P ";
            if (whereClause != null && whereClause.length() > 0) {
                    sql  = sql   + " WHERE KP."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+ " = P."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] 
                         + " AND "+ whereClause;
            }
            sql = sql + " LIMIT 0,1";
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Position position = new Position();
                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                lists.add(position);
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
