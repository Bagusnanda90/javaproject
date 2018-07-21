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

public class PstCandidatePosition extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_CANDIDATE_POSITION = "hr_candidate_position";
    public static final int FLD_CANDIDATE_POS_ID = 0;
    public static final int FLD_CANDIDATE_MAIN_ID = 1;
    public static final int FLD_CANDIDATE_LOC_ID = 2;
    public static final int FLD_POSITION_ID = 3;
    public static final int FLD_NUMBER_OF_CANDIDATE = 4;
    public static final int FLD_DUE_DATE = 5;
    public static final int FLD_OBJECTIVES = 6;
    public static final int FLD_CANDIDATE_TYPE = 7;
    public static String[] fieldNames = {
        "CANDIDATE_POS_ID",
        "CANDIDATE_MAIN_ID",
        "CANDIDATE_LOC_ID",
        "POSITION_ID",
        "NUMBER_OF_CANDIDATE",
        "DUE_DATE",
        "OBJECTIVES",
        "CANDIDATE_TYPE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_INT
    };
    public static final int FREE_POSITION = 0;
    public static final int REPLACEMENT = 1;
    public static final int ROTATION = 2;
    public static final int TALENT_POOL = 3;
    public static final int MIX = 4;
    
    public static String[] candidateTypeNames = {
      "Free Position", "Replacement", "Rotation", "Talent Pool", "Mix"
    };

    public PstCandidatePosition() {
    }

    public PstCandidatePosition(int i) throws DBException {
        super(new PstCandidatePosition());
    }

    public PstCandidatePosition(String sOid) throws DBException {
        super(new PstCandidatePosition(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCandidatePosition(long lOid) throws DBException {
        super(new PstCandidatePosition(0));
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
        return TBL_CANDIDATE_POSITION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCandidatePosition().getClass().getName();
    }

    public static CandidatePosition fetchExc(long oid) throws DBException {
        try {
            CandidatePosition entCandidatePosition = new CandidatePosition();
            PstCandidatePosition pstCandidatePosition = new PstCandidatePosition(oid);
            entCandidatePosition.setOID(oid);
            entCandidatePosition.setCandidateMainId(pstCandidatePosition.getLong(FLD_CANDIDATE_MAIN_ID));
            entCandidatePosition.setCandidateLocId(pstCandidatePosition.getLong(FLD_CANDIDATE_LOC_ID));
            entCandidatePosition.setPositionId(pstCandidatePosition.getLong(FLD_POSITION_ID));
            entCandidatePosition.setNumberOfCandidate(pstCandidatePosition.getInt(FLD_NUMBER_OF_CANDIDATE));
            entCandidatePosition.setDueDate(pstCandidatePosition.getDate(FLD_DUE_DATE));
            entCandidatePosition.setObjectives(pstCandidatePosition.getString(FLD_OBJECTIVES));
            entCandidatePosition.setCandidateType(pstCandidatePosition.getInt(FLD_CANDIDATE_TYPE));
            return entCandidatePosition;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidatePosition(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CandidatePosition entCandidatePosition = fetchExc(entity.getOID());
        entity = (Entity) entCandidatePosition;
        return entCandidatePosition.getOID();
    }

    public static synchronized long updateExc(CandidatePosition entCandidatePosition) throws DBException {
        try {
            if (entCandidatePosition.getOID() != 0) {
                PstCandidatePosition pstCandidatePosition = new PstCandidatePosition(entCandidatePosition.getOID());
                pstCandidatePosition.setLong(FLD_CANDIDATE_MAIN_ID, entCandidatePosition.getCandidateMainId());
                pstCandidatePosition.setLong(FLD_CANDIDATE_LOC_ID, entCandidatePosition.getCandidateLocId());
                pstCandidatePosition.setLong(FLD_POSITION_ID, entCandidatePosition.getPositionId());
                pstCandidatePosition.setInt(FLD_NUMBER_OF_CANDIDATE, entCandidatePosition.getNumberOfCandidate());
                pstCandidatePosition.setDate(FLD_DUE_DATE, entCandidatePosition.getDueDate());
                pstCandidatePosition.setString(FLD_OBJECTIVES, entCandidatePosition.getObjectives());
                pstCandidatePosition.setInt(FLD_CANDIDATE_TYPE, entCandidatePosition.getCandidateType());
                pstCandidatePosition.update();
                return entCandidatePosition.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidatePosition(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((CandidatePosition) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstCandidatePosition pstCandidatePosition = new PstCandidatePosition(oid);
            pstCandidatePosition.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidatePosition(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(CandidatePosition entCandidatePosition) throws DBException {
        try {
            PstCandidatePosition pstCandidatePosition = new PstCandidatePosition(0);
            pstCandidatePosition.setLong(FLD_CANDIDATE_MAIN_ID, entCandidatePosition.getCandidateMainId());
            pstCandidatePosition.setLong(FLD_CANDIDATE_LOC_ID, entCandidatePosition.getCandidateLocId());
            pstCandidatePosition.setLong(FLD_POSITION_ID, entCandidatePosition.getPositionId());
            pstCandidatePosition.setInt(FLD_NUMBER_OF_CANDIDATE, entCandidatePosition.getNumberOfCandidate());
            pstCandidatePosition.setDate(FLD_DUE_DATE, entCandidatePosition.getDueDate());
            pstCandidatePosition.setString(FLD_OBJECTIVES, entCandidatePosition.getObjectives());
            pstCandidatePosition.setInt(FLD_CANDIDATE_TYPE, entCandidatePosition.getCandidateType());
            pstCandidatePosition.insert();
            entCandidatePosition.setOID(pstCandidatePosition.getLong(FLD_CANDIDATE_POS_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCandidatePosition(0), DBException.UNKNOWN);
        }
        return entCandidatePosition.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((CandidatePosition) entity);
    }

    public static void resultToObject(ResultSet rs, CandidatePosition entCandidatePosition) {
        try {
            entCandidatePosition.setOID(rs.getLong(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_POS_ID]));
            entCandidatePosition.setCandidateMainId(rs.getLong(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_MAIN_ID]));
            entCandidatePosition.setCandidateLocId(rs.getLong(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_LOC_ID]));
            entCandidatePosition.setPositionId(rs.getLong(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_POSITION_ID]));
            entCandidatePosition.setNumberOfCandidate(rs.getInt(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_NUMBER_OF_CANDIDATE]));
            entCandidatePosition.setDueDate(rs.getDate(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_DUE_DATE]));
            entCandidatePosition.setObjectives(rs.getString(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_OBJECTIVES]));
            entCandidatePosition.setCandidateType(rs.getInt(PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_TYPE]));
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
            String sql = "SELECT * FROM " + TBL_CANDIDATE_POSITION;
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
                CandidatePosition entCandidatePosition = new CandidatePosition();
                resultToObject(rs, entCandidatePosition);
                lists.add(entCandidatePosition);
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
    
    public static Vector listDistinct(String distinct, String whereClause) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT "+distinct+" FROM " + TBL_CANDIDATE_POSITION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                long candidateLocId = rs.getLong(distinct);
                lists.add(candidateLocId);
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

    public static boolean checkOID(long entCandidatePositionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_CANDIDATE_POSITION + " WHERE "
                    + PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_POS_ID] + " = " + entCandidatePositionId;
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
            String sql = "SELECT COUNT(" + PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_POS_ID] + ") FROM " + TBL_CANDIDATE_POSITION;
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
                    CandidatePosition entCandidatePosition = (CandidatePosition) list.get(ls);
                    if (oid == entCandidatePosition.getOID()) {
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
