/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.deleteExc;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.fetchExc;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.fieldNames;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.fieldTypes;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.getCount;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.insertExc;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.list;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.resultToObject;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat.updateExc;
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
public class PstWarningReprimandAyat extends DBHandler
        implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
// Bagian deklarasi dari nama tabel dan masing-masing index dari field

    public static final String TBL_AYAT = "hr_warning_reprimand_ayat";
    /*
    AYAT_ID
    AYAT_TITLE
    AYAT_DESCRIPTION
    PASAL_ID
    AYAT_PAGE
     */
    public static final int FLD_AYAT_ID = 0;
    public static final int FLD_AYAT_TITLE = 1;
    public static final int FLD_AYAT_DESCRIPTION = 2;
    public static final int FLD_PASAL_ID = 3;
    public static final int FLD_AYAT_PAGE = 4;

    // array string dari nama-nama field
    // dan nilai dari array harus sesuai dg nama field pada tabel db
    public static String[] fieldNames = {
        "AYAT_ID",
        "AYAT_TITLE",
        "AYAT_DESCRIPTION",
        "PASAL_ID",
        "AYAT_PAGE"
    };
    // array bertipe integer dari fieldTypes
    // nilai-nilai dari array tersebut adalah suatu constanta
    // dimana masing-masing telah memiliki nilai int yg telah ditentukan
    // if you want to see the value, please ctrl+click the type name
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT
    };
    /*
     * Dibawah ini adalah bagian dari constractor Persistent
     * dimana harus terdapat:
     * public PstWarningReprimandAyat();
     * public PstWarningReprimandAyat(int i) throws DBException;
     * public PstWarningReprimandAyat(String sOid) throws DBException
     * public PstWarningReprimandAyat(long lOid) throws DBException
     */
    // constract jika tidak ada parameter

    public PstWarningReprimandAyat() {
    }
    // constract jika parameter bernilai int

    public PstWarningReprimandAyat(int i) throws DBException {
        super(new PstWarningReprimandAyat());
    }
    // constract jika parameter bernilai string

    public PstWarningReprimandAyat(String sOid) throws DBException {
        super(new PstWarningReprimandAyat(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }
    // constract jika parameter bernilai long
    // mengubah nilai long menjadi string

    public PstWarningReprimandAyat(long lOid) throws DBException {
        super(new PstWarningReprimandAyat(0));
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
        return TBL_AYAT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstWarningReprimandAyat().getClass().getName();
    }

    public static WarningReprimandAyat fetchExc(long oid) throws DBException {

        try {
            WarningReprimandAyat ayat = new WarningReprimandAyat();
            PstWarningReprimandAyat pstAyat = new PstWarningReprimandAyat(oid);
            ayat.setOID(oid);
            ayat.setAyatTitle(pstAyat.getString(FLD_AYAT_TITLE));
            ayat.setAyatDescription(pstAyat.getString(FLD_AYAT_DESCRIPTION));
            ayat.setPasalId(pstAyat.getLong(FLD_PASAL_ID));
            ayat.setAyatPage(pstAyat.getInt(FLD_AYAT_PAGE));
            return ayat;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandAyat(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        WarningReprimandAyat ayat = fetchExc(entity.getOID());
        entity = (Entity) ayat;
        return ayat.getOID();
    }

    public static synchronized long updateExc(WarningReprimandAyat ayat) throws DBException {
        try {
            if (ayat.getOID() != 0) {
                PstWarningReprimandAyat pstAyat = new PstWarningReprimandAyat(ayat.getOID());
                pstAyat.setString(FLD_AYAT_TITLE, ayat.getAyatTitle());
                pstAyat.setString(FLD_AYAT_DESCRIPTION, ayat.getAyatDescription());
                pstAyat.setLong(FLD_PASAL_ID, ayat.getPasalId());
                pstAyat.setInt(FLD_AYAT_PAGE, ayat.getAyatPage());
                pstAyat.update();
                return ayat.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandAyat(0), DBException.UNKNOWN);
        }

        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((WarningReprimandAyat) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstWarningReprimandAyat pstAyat = new PstWarningReprimandAyat(oid);

            pstAyat.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstWarningReprimandAyat(0), DBException.UNKNOWN);

        }

        return oid;

    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(WarningReprimandAyat ayat) throws DBException 
    {
        try {
            // create object persistent
            PstWarningReprimandAyat pstAyat = new PstWarningReprimandAyat(0);
            // setString(); diambil dari DBHandler
            pstAyat.setString(FLD_AYAT_TITLE, ayat.getAyatTitle());
            pstAyat.setString(FLD_AYAT_DESCRIPTION, ayat.getAyatDescription());
            pstAyat.setLong(FLD_PASAL_ID, ayat.getPasalId());
            pstAyat.setInt(FLD_AYAT_PAGE, ayat.getAyatPage());
            pstAyat.insert();
            // setelah record di-set dengan nilai yg telah ditentukan
            // dan ID yang telah digenerate maka entity di setOID
            ayat.setOID(pstAyat.getLong(FLD_AYAT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandAyat(0), DBException.UNKNOWN);
        }
        return ayat.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((WarningReprimandAyat) entity);
    }
    // hasil ke objek
    public static void resultToObject(ResultSet rs, WarningReprimandAyat ayat) {

        try {
            // entity ayat di set dengan nilai dari resultSet
            ayat.setOID(rs.getLong(PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_ID]));
            ayat.setAyatTitle(rs.getString(PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE]));
            ayat.setAyatDescription(rs.getString(PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_DESCRIPTION]));
            ayat.setPasalId(rs.getLong(PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_PASAL_ID]));
            ayat.setAyatPage(rs.getInt(PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_PAGE]));
        } catch (Exception e) {
        }

    }

    /**
     * KETERANGAN: Fungsi untuk melakukan list table ayat , berdasarkan
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

            String sql = "SELECT * FROM " + TBL_AYAT;

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
                WarningReprimandAyat ayat = new WarningReprimandAyat();
                resultToObject(rs, ayat);
                lists.add(ayat);
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
            String sql = "SELECT * FROM " + TBL_AYAT + " WHERE "
                    + PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_ID] + " = " + mSId;
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
            String sql = "SELECT COUNT(" + PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_ID]
                    + ") FROM " + TBL_AYAT;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);//ambil isi ResultSet yg 1 atau PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_JENIS_ITEM_ID] 
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
                    WarningReprimandAyat jenisItems = (WarningReprimandAyat) list.get(ls);
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