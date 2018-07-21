/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;


import static com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal.deleteExc;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal.fetchExc;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal.getCount;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal.insertExc;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal.list;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal.resultToObject;
import static com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal.updateExc;
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
public class PstWarningReprimandPasal extends DBHandler
        implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
// Bagian deklarasi dari nama tabel dan masing-masing index dari field

    public static final String TBL_PASAL = "hr_warning_reprimand_pasal";
    /*
    PASAL_ID
    PASAL_TITLE
    BAB_ID
     */
    public static final int FLD_PASAL_ID = 0;
    public static final int FLD_PASAL_TITLE = 1;
    public static final int FLD_BAB_ID = 2;

    // array string dari nama-nama field
    // dan nilai dari array harus sesuai dg nama field pada tabel db
    public static String[] fieldNames = {
        "PASAL_ID",
        "PASAL_TITLE",
        "BAB_ID"
    };
    // array bertipe integer dari fieldTypes
    // nilai-nilai dari array tersebut adalah suatu constanta
    // dimana masing-masing telah memiliki nilai int yg telah ditentukan
    // if you want to see the value, please ctrl+click the type name
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_LONG
    };
    /*
     * Dibawah ini adalah bagian dari constractor Persistent
     * dimana harus terdapat:
     * public PstWarningReprimandPasal();
     * public PstWarningReprimandPasal(int i) throws DBException;
     * public PstWarningReprimandPasal(String sOid) throws DBException
     * public PstWarningReprimandPasal(long lOid) throws DBException
     */
    // constract jika tidak ada parameter

    public PstWarningReprimandPasal() {
    }
    // constract jika parameter bernilai int

    public PstWarningReprimandPasal(int i) throws DBException {
        super(new PstWarningReprimandPasal());
    }
    // constract jika parameter bernilai string

    public PstWarningReprimandPasal(String sOid) throws DBException {
        super(new PstWarningReprimandPasal(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }
    // constract jika parameter bernilai long
    // mengubah nilai long menjadi string

    public PstWarningReprimandPasal(long lOid) throws DBException {
        super(new PstWarningReprimandPasal(0));
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
        return TBL_PASAL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstWarningReprimandPasal().getClass().getName();
    }

    public static WarningReprimandPasal fetchExc(long oid) throws DBException {

        try {
            WarningReprimandPasal pasal = new WarningReprimandPasal();
            PstWarningReprimandPasal pstPasal = new PstWarningReprimandPasal(oid);
            pasal.setOID(oid);
            pasal.setPasalTitle(pstPasal.getString(FLD_PASAL_TITLE));       
            pasal.setBabId(pstPasal.getLong(FLD_BAB_ID));
            return pasal;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandPasal(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        WarningReprimandPasal pasal = fetchExc(entity.getOID());
        entity = (Entity) pasal;
        return pasal.getOID();
    }

    public static synchronized long updateExc(WarningReprimandPasal pasal) throws DBException {
        try {
            if (pasal.getOID() != 0) {
                PstWarningReprimandPasal pstPasal = new PstWarningReprimandPasal(pasal.getOID());
                pstPasal.setString(FLD_PASAL_TITLE, pasal.getPasalTitle());
                pstPasal.setLong(FLD_BAB_ID, pasal.getBabId());
                pstPasal.update();
                return pasal.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandPasal(0), DBException.UNKNOWN);
        }

        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((WarningReprimandPasal) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstWarningReprimandPasal pstPasal = new PstWarningReprimandPasal(oid);

            pstPasal.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstWarningReprimandPasal(0), DBException.UNKNOWN);

        }

        return oid;

    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(WarningReprimandPasal pasal) throws DBException 
    {
        try {
            // create object persistent
            PstWarningReprimandPasal pstPasal = new PstWarningReprimandPasal(0);
            // setString(); diambil dari DBHandler
            pstPasal.setString(FLD_PASAL_TITLE, pasal.getPasalTitle());
            pstPasal.setLong(FLD_BAB_ID, pasal.getBabId());
            pstPasal.insert();
            // setelah record di-set dengan nilai yg telah ditentukan
            // dan ID yang telah digenerate maka entity di setOID
            pasal.setOID(pstPasal.getLong(FLD_PASAL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarningReprimandPasal(0), DBException.UNKNOWN);
        }
        return pasal.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((WarningReprimandPasal) entity);
    }
    // hasil ke objek
    public static void resultToObject(ResultSet rs, WarningReprimandPasal pasal) {

        try {
            // entity pasal di set dengan nilai dari resultSet
            pasal.setOID(rs.getLong(PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_ID]));
            pasal.setPasalTitle(rs.getString(PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE]));
            pasal.setBabId(rs.getLong(PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_BAB_ID]));
        } catch (Exception e) {
        }

    }

    /**
     * KETERANGAN: Fungsi untuk melakukan list table pasal , berdasarkan
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

            String sql = "SELECT * FROM " + TBL_PASAL;

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
                WarningReprimandPasal pasal = new WarningReprimandPasal();
                resultToObject(rs, pasal);
                lists.add(pasal);
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
            String sql = "SELECT * FROM " + TBL_PASAL + " WHERE "
                    + PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_ID] + " = " + mSId;
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
            String sql = "SELECT COUNT(" + PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_ID]
                    + ") FROM " + TBL_PASAL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);//ambil isi ResultSet yg 1 atau PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_JENIS_ITEM_ID] 
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
                    WarningReprimandPasal jenisItems = (WarningReprimandPasal) list.get(ls);
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
