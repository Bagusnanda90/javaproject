/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import static com.dimata.qdep.db.I_DBType.TYPE_ID;
import static com.dimata.qdep.db.I_DBType.TYPE_INT;
import static com.dimata.qdep.db.I_DBType.TYPE_PK;
import static com.dimata.qdep.db.I_DBType.TYPE_STRING;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Hendra McHen
 */
public class PstWarningReprimandBab extends DBHandler
        implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
// Bagian deklarasi dari nama tabel dan masing-masing index dari field

    public static final String TBL_BAB = "hr_warning_reprimand_bab";
    public static final int FLD_BAB_ID = 0;
    public static final int FLD_BAB_TITLE = 1;

    // array string dari nama-nama field
    // dan nilai dari array harus sesuai dg nama field pada tabel db
    public static String[] fieldNames = {
        "BAB_ID",
        "BAB_TITLE"
    };
    // array bertipe integer dari fieldTypes
    // nilai-nilai dari array tersebut adalah suatu constanta
    // dimana masing-masing telah memiliki nilai int yg telah ditentukan
    // if you want to see the value, please ctrl+click the type name
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING
    };
    /*
     * Dibawah ini adalah bagian dari constractor Persistent
     * dimana harus terdapat:
     * public PstWarningReprimandBab();
     * public PstWarningReprimandBab(int i) throws DBException;
     * public PstWarningReprimandBab(String sOid) throws DBException
     * public PstWarningReprimandBab(long lOid) throws DBException
     */
    // constract jika tidak ada parameter

    public PstWarningReprimandBab() {
    }
    // constract jika parameter bernilai int

    public PstWarningReprimandBab(int i) throws DBException {
        super(new PstWarningReprimandBab());
    }
    // constract jika parameter bernilai string

    public PstWarningReprimandBab(String sOid) throws DBException {
        super(new PstWarningReprimandBab(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }
    // constract jika parameter bernilai long
    // mengubah nilai long menjadi string

    public PstWarningReprimandBab(long lOid) throws DBException {
        super(new PstWarningReprimandBab(0));
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
    /*
     * Getter untuk ukuran field, nama tabel, 
     * nama field, dan tipe field
     */

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_BAB;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstWarningReprimandBab().getClass().getName();
    }

    public static WarningReprimandBab fetchExc(long oid) throws DBException {

        try {
            WarningReprimandBab bab = new WarningReprimandBab();
            PstWarningReprimandBab pstBab = new PstWarningReprimandBab(oid);
            bab.setOID(oid);
            bab.setBabTitle(pstBab.getString(FLD_BAB_TITLE));
            return bab;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandBab(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        WarningReprimandBab bab = fetchExc(entity.getOID());
        entity = (Entity) bab;
        return bab.getOID();
    }

    public static synchronized long updateExc(WarningReprimandBab bab) throws DBException {
        try {
            if (bab.getOID() != 0) {
                PstWarningReprimandBab pstBab = new PstWarningReprimandBab(bab.getOID());
                pstBab.setString(FLD_BAB_TITLE, bab.getBabTitle());
                pstBab.update();
                return bab.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandBab(0), DBException.UNKNOWN);
        }

        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((WarningReprimandBab) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstWarningReprimandBab pstBab = new PstWarningReprimandBab(oid);

            pstBab.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstWarningReprimandBab(0), DBException.UNKNOWN);

        }

        return oid;

    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(WarningReprimandBab bab) throws DBException 
    {
        try {
            // create object persistent
            PstWarningReprimandBab pstBab = new PstWarningReprimandBab(0);
            // setString(); diambil dari DBHandler
            pstBab.setString(FLD_BAB_TITLE, bab.getBabTitle());
            pstBab.insert();
            // setelah record di-set dengan nilai yg telah ditentukan
            // dan ID yang telah digenerate maka entity di setOID
            bab.setOID(pstBab.getLong(FLD_BAB_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandBab(0), DBException.UNKNOWN);
        }
        return bab.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((WarningReprimandBab) entity);
    }
    // hasil ke objek
    public static void resultToObject(ResultSet rs, WarningReprimandBab bab) {

        try {
            // entity bab di set dengan nilai dari resultSet
            bab.setOID(rs.getLong(PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID]));
            bab.setBabTitle(rs.getString(PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_TITLE]));
        } catch (Exception e) {
        }

    }

    /**
     * KETERANGAN: Fungsi untuk melakukan list table bab , berdasarkan
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

            String sql = "SELECT * FROM " + TBL_BAB;

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
                WarningReprimandBab bab = new WarningReprimandBab();
                resultToObject(rs, bab);
                lists.add(bab);
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
            String sql = "SELECT * FROM " + TBL_BAB + " WHERE "
                    + PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID] + " = " + mSId;
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
            String sql = "SELECT COUNT(" + PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID]
                    + ") FROM " + TBL_BAB;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);//ambil isi ResultSet yg 1 atau PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_JENIS_ITEM_ID] 
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
                    WarningReprimandBab jenisItems = (WarningReprimandBab) list.get(ls);
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
