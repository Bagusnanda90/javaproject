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
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class PstCompetencyDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_COMPETENCY_DETAIL = "hr_competency_detail";
    public static final int FLD_DETAIL_ID = 0;
    public static final int FLD_COMPETENCY_ID = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static String[] fieldNames = {
        "DETAIL_ID",
        "COMPETENCY_ID",
        "DESCRIPTION"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING
    };

    public PstCompetencyDetail() {
    }

    public PstCompetencyDetail(int i) throws DBException {
        super(new PstCompetencyDetail());
    }

    public PstCompetencyDetail(String sOid) throws DBException {
        super(new PstCompetencyDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCompetencyDetail(long lOid) throws DBException {
        super(new PstCompetencyDetail(0));
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
        return TBL_COMPETENCY_DETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCompetencyDetail().getClass().getName();
    }

    public static CompetencyDetail fetchExc(long oid) throws DBException {
        try {
            CompetencyDetail entCompetencyDetail = new CompetencyDetail();
            PstCompetencyDetail pstCompetencyDetail = new PstCompetencyDetail(oid);
            entCompetencyDetail.setOID(oid);
            entCompetencyDetail.setCompetencyId(pstCompetencyDetail.getlong(FLD_COMPETENCY_ID));
            entCompetencyDetail.setDescription(pstCompetencyDetail.getString(FLD_DESCRIPTION));
            return entCompetencyDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyDetail(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CompetencyDetail entCompetencyDetail = fetchExc(entity.getOID());
        entity = (Entity) entCompetencyDetail;
        return entCompetencyDetail.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((CompetencyDetail) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((CompetencyDetail) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(CompetencyDetail entCompetencyDetail) throws DBException {
        try {
            PstCompetencyDetail pstCompetencyDetail = new PstCompetencyDetail(0);

            pstCompetencyDetail.setLong(FLD_COMPETENCY_ID, entCompetencyDetail.getCompetencyId());
            pstCompetencyDetail.setString(FLD_DESCRIPTION, entCompetencyDetail.getDescription());

            pstCompetencyDetail.insert();
            entCompetencyDetail.setOID(pstCompetencyDetail.getLong(FLD_DETAIL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyDetail(0), DBException.UNKNOWN);
        }
        return entCompetencyDetail.getOID();
    }

    public static long updateExc(CompetencyDetail entCompetencyDetail) throws DBException {
        try {
            if (entCompetencyDetail.getOID() != 0) {
                PstCompetencyDetail pstCompetencyDetail = new PstCompetencyDetail(entCompetencyDetail.getOID());

                pstCompetencyDetail.setLong(FLD_COMPETENCY_ID, entCompetencyDetail.getCompetencyId());
                pstCompetencyDetail.setString(FLD_DESCRIPTION, entCompetencyDetail.getDescription());

                pstCompetencyDetail.update();
                return entCompetencyDetail.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstCompetencyDetail pstCompetencyDetail = new PstCompetencyDetail(oid);
            pstCompetencyDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetencyDetail(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_COMPETENCY_DETAIL;
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
                CompetencyDetail entCompetencyDetail = new CompetencyDetail();
                resultToObject(rs, entCompetencyDetail);
                lists.add(entCompetencyDetail);
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

    private static void resultToObject(ResultSet rs, CompetencyDetail entCompetencyDetail) {
        try {

            entCompetencyDetail.setOID(rs.getLong(PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_DETAIL_ID]));
            entCompetencyDetail.setCompetencyId(rs.getLong(PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_COMPETENCY_ID]));
            entCompetencyDetail.setDescription(rs.getString(PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_DESCRIPTION]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entCompetencyDetailId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_COMPETENCY_DETAIL + " WHERE "
                    + PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_DETAIL_ID] + " = " + entCompetencyDetailId;

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
            String sql = "SELECT COUNT(" + PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_DETAIL_ID] + ") FROM " + TBL_COMPETENCY_DETAIL;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    CompetencyDetail entCompetencyDetail = (CompetencyDetail) list.get(ls);
                    if (oid == entCompetencyDetail.getOID()) {
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
