/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

/**
 *
 * @author Dimata 007
 */
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

public class PstCandidateLocation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_CANDIDATE_LOCATION = "hr_candidate_location";
    public static final int FLD_CANDIDATE_LOC_ID = 0;
    public static final int FLD_CANDIDATE_MAIN_ID = 1;
    public static final int FLD_GEN_ID = 2;
    public static final int FLD_DIVISION_ID = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_SECTION_ID = 5;
    public static String[] fieldNames = {
        "CANDIDATE_LOC_ID",
        "CANDIDATE_MAIN_ID",
        "GEN_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstCandidateLocation() {
    }

    public PstCandidateLocation(int i) throws DBException {
        super(new PstCandidateLocation());
    }

    public PstCandidateLocation(String sOid) throws DBException {
        super(new PstCandidateLocation(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCandidateLocation(long lOid) throws DBException {
        super(new PstCandidateLocation(0));
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
        return TBL_CANDIDATE_LOCATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCandidateLocation().getClass().getName();
    }

    public static CandidateLocation fetchExc(long oid) throws DBException {
        try {
            CandidateLocation entCandidateLocation = new CandidateLocation();
            PstCandidateLocation pstCandidateLocation = new PstCandidateLocation(oid);
            entCandidateLocation.setOID(oid);
            entCandidateLocation.setCandidateMainId(pstCandidateLocation.getLong(FLD_CANDIDATE_MAIN_ID));
            entCandidateLocation.setGenId(pstCandidateLocation.getLong(FLD_GEN_ID));
            entCandidateLocation.setDivisionId(pstCandidateLocation.getLong(FLD_DIVISION_ID));
            entCandidateLocation.setDepartmentId(pstCandidateLocation.getLong(FLD_DEPARTMENT_ID));
            entCandidateLocation.setSectionId(pstCandidateLocation.getLong(FLD_SECTION_ID));
            return entCandidateLocation;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidateLocation(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CandidateLocation entCandidateLocation = fetchExc(entity.getOID());
        entity = (Entity) entCandidateLocation;
        return entCandidateLocation.getOID();
    }

    public static synchronized long updateExc(CandidateLocation entCandidateLocation) throws DBException {
        try {
            if (entCandidateLocation.getOID() != 0) {
                PstCandidateLocation pstCandidateLocation = new PstCandidateLocation(entCandidateLocation.getOID());
                pstCandidateLocation.setLong(FLD_CANDIDATE_MAIN_ID, entCandidateLocation.getCandidateMainId());
                pstCandidateLocation.setLong(FLD_GEN_ID, entCandidateLocation.getGenId());
                pstCandidateLocation.setLong(FLD_DIVISION_ID, entCandidateLocation.getDivisionId());
                pstCandidateLocation.setLong(FLD_DEPARTMENT_ID, entCandidateLocation.getDepartmentId());
                pstCandidateLocation.setLong(FLD_SECTION_ID, entCandidateLocation.getSectionId());
                pstCandidateLocation.update();
                return entCandidateLocation.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidateLocation(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((CandidateLocation) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstCandidateLocation pstCandidateLocation = new PstCandidateLocation(oid);
            pstCandidateLocation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidateLocation(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(CandidateLocation entCandidateLocation) throws DBException {
        try {
            PstCandidateLocation pstCandidateLocation = new PstCandidateLocation(0);
            pstCandidateLocation.setLong(FLD_CANDIDATE_MAIN_ID, entCandidateLocation.getCandidateMainId());
            pstCandidateLocation.setLong(FLD_GEN_ID, entCandidateLocation.getGenId());
            pstCandidateLocation.setLong(FLD_DIVISION_ID, entCandidateLocation.getDivisionId());
            pstCandidateLocation.setLong(FLD_DEPARTMENT_ID, entCandidateLocation.getDepartmentId());
            pstCandidateLocation.setLong(FLD_SECTION_ID, entCandidateLocation.getSectionId());
            pstCandidateLocation.insert();
            entCandidateLocation.setOID(pstCandidateLocation.getLong(FLD_CANDIDATE_LOC_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidateLocation(0), DBException.UNKNOWN);
        }
        return entCandidateLocation.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((CandidateLocation) entity);
    }

    public static void resultToObject(ResultSet rs, CandidateLocation entCandidateLocation) {
        try {
            entCandidateLocation.setOID(rs.getLong(PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_CANDIDATE_LOC_ID]));
            entCandidateLocation.setCandidateMainId(rs.getLong(PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_CANDIDATE_MAIN_ID]));
            entCandidateLocation.setGenId(rs.getLong(PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_GEN_ID]));
            entCandidateLocation.setDivisionId(rs.getLong(PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_DIVISION_ID]));
            entCandidateLocation.setDepartmentId(rs.getLong(PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_DEPARTMENT_ID]));
            entCandidateLocation.setSectionId(rs.getLong(PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_SECTION_ID]));
        } catch (Exception e) {
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_CANDIDATE_LOCATION;
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
                CandidateLocation entCandidateLocation = new CandidateLocation();
                resultToObject(rs, entCandidateLocation);
                lists.add(entCandidateLocation);
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

    public static boolean checkOID(long entCandidateLocationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_CANDIDATE_LOCATION + " WHERE "
                    + PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_CANDIDATE_LOC_ID] + " = " + entCandidateLocationId;
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
            String sql = "SELECT COUNT(" + PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_CANDIDATE_LOC_ID] + ") FROM " + TBL_CANDIDATE_LOCATION;
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

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    CandidateLocation entCandidateLocation = (CandidateLocation) list.get(ls);
                    if (oid == entCandidateLocation.getOID()) {
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