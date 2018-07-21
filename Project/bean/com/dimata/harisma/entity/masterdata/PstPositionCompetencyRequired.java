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
 * @author Hendra McHen | 2015-02-02
 */
public class PstPositionCompetencyRequired extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_COMPETENCY_REQUIRED = "hr_pos_competency_req";
    public static final int FLD_POS_COMP_REQ_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_COMPETENCY_ID = 2;
    public static final int FLD_SCORE_REQ_MIN = 3;
    public static final int FLD_SCORE_REQ_RECOMMENDED = 4;
    public static final int FLD_COMPETENCY_LEVEL_ID_MIN = 5;
    public static final int FLD_COMPETENCY_LEVEL_ID_RECOMMENDED = 6;
    public static final int FLD_COMPETENCY_LEVEL_ID = 7;
    public static final int FLD_NOTE = 8;
    public static final int FLD_RE_TRAIN_OR_SERTFC_REQ = 9;
    public static final int FLD_VALID_START = 10;
    public static final int FLD_VALID_END = 11;
    public static String[] fieldNames = {
        "POS_COMP_REQ_ID",
        "POSITION_ID",
        "COMPETENCY_ID",
        "SCORE_REQ_MIN",
        "SCORE_REQ_RECOMMENDED",
        "COMPETENCY_LEVEL_ID_MIN",
        "COMPETENCY_LEVEL_ID_RECOMMENDED",
        "COMPETENCY_LEVEL_ID",
        "NOTE",
        "RE_TRAIN_OR_SERTFC_REQ",
        "VALID_START",
        "VALID_END"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE
    };

    public PstPositionCompetencyRequired() {
    }

    public PstPositionCompetencyRequired(int i) throws DBException {
        super(new PstPositionCompetencyRequired());
    }

    public PstPositionCompetencyRequired(String sOid) throws DBException {
        super(new PstPositionCompetencyRequired(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionCompetencyRequired(long lOid) throws DBException {
        super(new PstPositionCompetencyRequired(0));
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
        return TBL_POSITION_COMPETENCY_REQUIRED;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionCompetencyRequired().getClass().getName();
    }

    public static PositionCompetencyRequired fetchExc(long oid) throws DBException {
        try {
            PositionCompetencyRequired entPositionCompetencyRequired = new PositionCompetencyRequired();
            PstPositionCompetencyRequired pstPositionCompetencyRequired = new PstPositionCompetencyRequired(oid);
            entPositionCompetencyRequired.setOID(oid);
            entPositionCompetencyRequired.setPositionId(pstPositionCompetencyRequired.getlong(FLD_POSITION_ID));
            entPositionCompetencyRequired.setCompetencyId(pstPositionCompetencyRequired.getlong(FLD_COMPETENCY_ID));
            entPositionCompetencyRequired.setScoreReqMin(pstPositionCompetencyRequired.getfloat(FLD_SCORE_REQ_MIN));
            entPositionCompetencyRequired.setScoreReqRecommended(pstPositionCompetencyRequired.getfloat(FLD_SCORE_REQ_RECOMMENDED));
            entPositionCompetencyRequired.setCompetencyLevelIdMin(pstPositionCompetencyRequired.getInt(FLD_COMPETENCY_LEVEL_ID_MIN));
            entPositionCompetencyRequired.setCompetencyLevelIdRecommended(pstPositionCompetencyRequired.getInt(FLD_COMPETENCY_LEVEL_ID_RECOMMENDED));
            entPositionCompetencyRequired.setCompetencyLevelId(pstPositionCompetencyRequired.getLong(FLD_COMPETENCY_LEVEL_ID));
            entPositionCompetencyRequired.setNote(pstPositionCompetencyRequired.getString(FLD_NOTE));
            entPositionCompetencyRequired.setReTrainOrSertfcReq(pstPositionCompetencyRequired.getInt(FLD_RE_TRAIN_OR_SERTFC_REQ));
            entPositionCompetencyRequired.setValidStart(pstPositionCompetencyRequired.getDate(FLD_VALID_START));
            entPositionCompetencyRequired.setValidEnd(pstPositionCompetencyRequired.getDate(FLD_VALID_END));
            return entPositionCompetencyRequired;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompetencyRequired(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionCompetencyRequired entPositionCompetencyRequired = fetchExc(entity.getOID());
        entity = (Entity) entPositionCompetencyRequired;
        return entPositionCompetencyRequired.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PositionCompetencyRequired) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PositionCompetencyRequired) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(PositionCompetencyRequired entPositionCompetencyRequired) throws DBException {
        try {
            PstPositionCompetencyRequired pstPositionCompetencyRequired = new PstPositionCompetencyRequired(0);
            pstPositionCompetencyRequired.setLong(FLD_POSITION_ID, entPositionCompetencyRequired.getPositionId());
            pstPositionCompetencyRequired.setLong(FLD_COMPETENCY_ID, entPositionCompetencyRequired.getCompetencyId());
            pstPositionCompetencyRequired.setFloat(FLD_SCORE_REQ_MIN, entPositionCompetencyRequired.getScoreReqMin());
            pstPositionCompetencyRequired.setFloat(FLD_SCORE_REQ_RECOMMENDED, entPositionCompetencyRequired.getScoreReqRecommended());
            pstPositionCompetencyRequired.setInt(FLD_COMPETENCY_LEVEL_ID_MIN, entPositionCompetencyRequired.getCompetencyLevelIdMin());
            pstPositionCompetencyRequired.setInt(FLD_COMPETENCY_LEVEL_ID_RECOMMENDED, entPositionCompetencyRequired.getCompetencyLevelIdRecommended());
            pstPositionCompetencyRequired.setLong(FLD_COMPETENCY_LEVEL_ID, entPositionCompetencyRequired.getCompetencyLevelId());
            pstPositionCompetencyRequired.setString(FLD_NOTE, entPositionCompetencyRequired.getNote());
            pstPositionCompetencyRequired.setInt(FLD_RE_TRAIN_OR_SERTFC_REQ, entPositionCompetencyRequired.getReTrainOrSertfcReq());
            pstPositionCompetencyRequired.setDate(FLD_VALID_START, entPositionCompetencyRequired.getValidStart());
            pstPositionCompetencyRequired.setDate(FLD_VALID_END, entPositionCompetencyRequired.getValidEnd());
            pstPositionCompetencyRequired.insert();
            entPositionCompetencyRequired.setOID(pstPositionCompetencyRequired.getLong(FLD_POS_COMP_REQ_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompetencyRequired(0), DBException.UNKNOWN);
        }
        return entPositionCompetencyRequired.getOID();
    }

    public static long updateExc(PositionCompetencyRequired entPositionCompetencyRequired) throws DBException {
        try {
            if (entPositionCompetencyRequired.getOID() != 0) {
                PstPositionCompetencyRequired pstPositionCompetencyRequired = new PstPositionCompetencyRequired(entPositionCompetencyRequired.getOID());
                pstPositionCompetencyRequired.setLong(FLD_POSITION_ID, entPositionCompetencyRequired.getPositionId());
                pstPositionCompetencyRequired.setLong(FLD_COMPETENCY_ID, entPositionCompetencyRequired.getCompetencyId());
                pstPositionCompetencyRequired.setFloat(FLD_SCORE_REQ_MIN, entPositionCompetencyRequired.getScoreReqMin());
                pstPositionCompetencyRequired.setFloat(FLD_SCORE_REQ_RECOMMENDED, entPositionCompetencyRequired.getScoreReqRecommended());
                pstPositionCompetencyRequired.setInt(FLD_COMPETENCY_LEVEL_ID_MIN, entPositionCompetencyRequired.getCompetencyLevelIdMin());
                pstPositionCompetencyRequired.setInt(FLD_COMPETENCY_LEVEL_ID_RECOMMENDED, entPositionCompetencyRequired.getCompetencyLevelIdRecommended());
                pstPositionCompetencyRequired.setLong(FLD_COMPETENCY_LEVEL_ID, entPositionCompetencyRequired.getCompetencyLevelId());
                pstPositionCompetencyRequired.setString(FLD_NOTE, entPositionCompetencyRequired.getNote());
                pstPositionCompetencyRequired.setInt(FLD_RE_TRAIN_OR_SERTFC_REQ, entPositionCompetencyRequired.getReTrainOrSertfcReq());
                pstPositionCompetencyRequired.setDate(FLD_VALID_START, entPositionCompetencyRequired.getValidStart());
                pstPositionCompetencyRequired.setDate(FLD_VALID_END, entPositionCompetencyRequired.getValidEnd());
                pstPositionCompetencyRequired.update();
                return entPositionCompetencyRequired.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompetencyRequired(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPositionCompetencyRequired pstPositionCompetencyRequired = new PstPositionCompetencyRequired(oid);
            pstPositionCompetencyRequired.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCompetencyRequired(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_POSITION_COMPETENCY_REQUIRED;
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
                PositionCompetencyRequired entPositionCompetencyRequired = new PositionCompetencyRequired();
                resultToObject(rs, entPositionCompetencyRequired);
                lists.add(entPositionCompetencyRequired);
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
    
    public static Vector listInnerJoin(String where){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql  = "SELECT * FROM " + TBL_POSITION_COMPETENCY_REQUIRED;
                   sql += " INNER JOIN hr_competency ON hr_pos_competency_req.COMPETENCY_ID=hr_competency.COMPETENCY_ID ";
                   sql += " WHERE hr_pos_competency_req.POSITION_ID = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                PositionCompetencyRequired entPositionCompetencyRequired = new PositionCompetencyRequired();
                Competency compe = new Competency();
                resultToObject(rs, entPositionCompetencyRequired);
                vect.add(entPositionCompetencyRequired);
                compe.setCompetencyName(rs.getString(PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_NAME]));
                vect.add(compe);
                lists.add(vect);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }
    
    public static Vector listInnerJoinVer1(String where){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql  = "SELECT * FROM " + TBL_POSITION_COMPETENCY_REQUIRED;
                   sql += " INNER JOIN hr_competency ON hr_pos_competency_req.COMPETENCY_ID=hr_competency.COMPETENCY_ID ";
                   sql += " INNER JOIN hr_position ON hr_pos_competency_req.POSITION_ID=hr_position.POSITION_ID ";
                   sql += " WHERE "+TBL_POSITION_COMPETENCY_REQUIRED+"."+PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_POS_COMP_REQ_ID]+" = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                // Position Competency Required
                PositionCompetencyRequired entPositionCompetencyRequired = new PositionCompetencyRequired();
                resultToObject(rs, entPositionCompetencyRequired);
                vect.add(entPositionCompetencyRequired);
                // Competency
                Competency compe = new Competency();
                compe.setCompetencyName(rs.getString(PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_NAME]));
                vect.add(compe);
                // Position
                Position pos = new Position();
                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(pos);
                lists.add(vect);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }
    // resultToObject PositionCompetencyRequired
    private static void resultToObject(ResultSet rs, PositionCompetencyRequired entPositionCompetencyRequired) {
        try {
            entPositionCompetencyRequired.setOID(rs.getLong(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_POS_COMP_REQ_ID]));
            entPositionCompetencyRequired.setPositionId(rs.getLong(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_POSITION_ID]));
            entPositionCompetencyRequired.setCompetencyId(rs.getLong(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_COMPETENCY_ID]));
            entPositionCompetencyRequired.setScoreReqMin(rs.getFloat(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_SCORE_REQ_MIN]));
            entPositionCompetencyRequired.setScoreReqRecommended(rs.getFloat(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_SCORE_REQ_RECOMMENDED]));
            entPositionCompetencyRequired.setCompetencyLevelIdMin(rs.getInt(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_COMPETENCY_LEVEL_ID_MIN]));
            entPositionCompetencyRequired.setCompetencyLevelIdRecommended(rs.getInt(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_COMPETENCY_LEVEL_ID_RECOMMENDED]));
            entPositionCompetencyRequired.setCompetencyLevelId(rs.getLong(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_COMPETENCY_LEVEL_ID]));
            entPositionCompetencyRequired.setNote(rs.getString(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_NOTE]));
            entPositionCompetencyRequired.setReTrainOrSertfcReq(rs.getInt(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_RE_TRAIN_OR_SERTFC_REQ]));
            entPositionCompetencyRequired.setValidStart(rs.getDate(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_VALID_START]));
            entPositionCompetencyRequired.setValidEnd(rs.getDate(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_VALID_END]));
        } catch (Exception e) {
        }
    }
    // resutlToObject CompetencyLevel
    private static void resultToObjectCompetencyLevel(ResultSet rs, CompetencyLevel entCompetencyLevel) {
        try {

            entCompetencyLevel.setOID(rs.getLong(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_LEVEL_ID]));
            entCompetencyLevel.setCompetencyId(rs.getLong(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_ID]));
            entCompetencyLevel.setScoreValue(rs.getFloat(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_SCORE_VALUE]));
            entCompetencyLevel.setDescription(rs.getString(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_DESCRIPTION]));
            entCompetencyLevel.setLevelMin(rs.getInt(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_LEVEL_MIN]));
            entCompetencyLevel.setLevelMax(rs.getInt(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_LEVEL_MAX]));
            entCompetencyLevel.setLevelUnit(rs.getString(PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_LEVEL_UNIT]));

        } catch (Exception e) {
        }
    }
    public static boolean checkOID(long entPositionCompetencyRequiredId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_COMPETENCY_REQUIRED + " WHERE "
                    + PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_POS_COMP_REQ_ID] + " = " + entPositionCompetencyRequiredId;

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
            String sql = "SELECT COUNT(" + PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_POS_COMP_REQ_ID] + ") FROM " + TBL_POSITION_COMPETENCY_REQUIRED;
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
                    PositionCompetencyRequired entPositionCompetencyRequired = (PositionCompetencyRequired) list.get(ls);
                    if (oid == entPositionCompetencyRequired.getOID()) {
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
