/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

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
import com.dimata.util.DynamicField;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

public class PstTrainingScore extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TRAINING_SCORE = "hr_training_score";
    public static final int FLD_TRAINING_SCORE_ID = 0;
    public static final int FLD_TRAINING_ID = 1;
    public static final int FLD_POINT_MIN = 2;
    public static final int FLD_POINT_MAX = 3;
    public static final int FLD_DURATION_MIN = 4;
    public static final int FLD_DURATION_MAX = 5;
    public static final int FLD_FREQUENCY_MIN = 6;
    public static final int FLD_FREQUENCY_MAX = 7;
    public static final int FLD_SCORE = 8;
    public static final int FLD_NOTE = 9;
    public static final int FLD_VALID_START = 10;
    public static final int FLD_VALID_END = 11;
    public static String[] fieldNames = {
        "TRAINING_SCORE_ID",
        "TRAINING_ID",
        "POINT_MIN",
        "POINT_MAX",
        "DURATION_MIN",
        "DURATION_MAX",
        "FREQUENCY_MIN",
        "FREQUENCY_MAX",
        "SCORE",
        "NOTE",
        "VALID_START",
        "VALID_END"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE
    };

    public PstTrainingScore() {
    }

    public PstTrainingScore(int i) throws DBException {
        super(new PstTrainingScore());
    }

    public PstTrainingScore(String sOid) throws DBException {
        super(new PstTrainingScore(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstTrainingScore(long lOid) throws DBException {
        super(new PstTrainingScore(0));
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
        return TBL_TRAINING_SCORE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstTrainingScore().getClass().getName();
    }

    public static TrainingScore fetchExc(long oid) throws DBException {
        try {
            TrainingScore entTrainingScore = new TrainingScore();
            PstTrainingScore pstTrainingScore = new PstTrainingScore(oid);
            entTrainingScore.setOID(oid);
            entTrainingScore.setTrainingId(pstTrainingScore.getLong(FLD_TRAINING_ID));
            entTrainingScore.setPointMin(pstTrainingScore.getfloat(FLD_POINT_MIN));
            entTrainingScore.setPointMax(pstTrainingScore.getfloat(FLD_POINT_MAX));
            entTrainingScore.setDurationMin(pstTrainingScore.getfloat(FLD_DURATION_MIN));
            entTrainingScore.setDurationMax(pstTrainingScore.getfloat(FLD_DURATION_MAX));
            entTrainingScore.setFrequencyMin(pstTrainingScore.getfloat(FLD_FREQUENCY_MIN));
            entTrainingScore.setFrequencyMax(pstTrainingScore.getfloat(FLD_FREQUENCY_MAX));
            entTrainingScore.setScore(pstTrainingScore.getfloat(FLD_SCORE));
            entTrainingScore.setNote(pstTrainingScore.getString(FLD_NOTE));
            entTrainingScore.setValidStart(pstTrainingScore.getDate(FLD_VALID_START));
            entTrainingScore.setValidEnd(pstTrainingScore.getDate(FLD_VALID_END));
            return entTrainingScore;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingScore(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        TrainingScore entTrainingScore = fetchExc(entity.getOID());
        entity = (Entity) entTrainingScore;
        return entTrainingScore.getOID();
    }

    public static synchronized long updateExc(TrainingScore entTrainingScore) throws DBException {
        try {
            if (entTrainingScore.getOID() != 0) {
                PstTrainingScore pstTrainingScore = new PstTrainingScore(entTrainingScore.getOID());
                pstTrainingScore.setLong(FLD_TRAINING_ID, entTrainingScore.getTrainingId());
                pstTrainingScore.setFloat(FLD_POINT_MIN, entTrainingScore.getPointMin());
                pstTrainingScore.setFloat(FLD_POINT_MAX, entTrainingScore.getPointMax());
                pstTrainingScore.setFloat(FLD_DURATION_MIN, entTrainingScore.getDurationMin());
                pstTrainingScore.setFloat(FLD_DURATION_MAX, entTrainingScore.getDurationMax());
                pstTrainingScore.setFloat(FLD_FREQUENCY_MIN, entTrainingScore.getFrequencyMin());
                pstTrainingScore.setFloat(FLD_FREQUENCY_MAX, entTrainingScore.getFrequencyMax());
                pstTrainingScore.setFloat(FLD_SCORE, entTrainingScore.getScore());
                pstTrainingScore.setString(FLD_NOTE, entTrainingScore.getNote());
                pstTrainingScore.setDate(FLD_VALID_START, entTrainingScore.getValidStart());
                pstTrainingScore.setDate(FLD_VALID_END, entTrainingScore.getValidEnd());
                pstTrainingScore.update();
                return entTrainingScore.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingScore(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((TrainingScore) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstTrainingScore pstTrainingScore = new PstTrainingScore(oid);
            pstTrainingScore.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingScore(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(TrainingScore entTrainingScore) throws DBException {
        try {
            PstTrainingScore pstTrainingScore = new PstTrainingScore(0);
            pstTrainingScore.setLong(FLD_TRAINING_ID, entTrainingScore.getTrainingId());
            pstTrainingScore.setFloat(FLD_POINT_MIN, entTrainingScore.getPointMin());
            pstTrainingScore.setFloat(FLD_POINT_MAX, entTrainingScore.getPointMax());
            pstTrainingScore.setFloat(FLD_DURATION_MIN, entTrainingScore.getDurationMin());
            pstTrainingScore.setFloat(FLD_DURATION_MAX, entTrainingScore.getDurationMax());
            pstTrainingScore.setFloat(FLD_FREQUENCY_MIN, entTrainingScore.getFrequencyMin());
            pstTrainingScore.setFloat(FLD_FREQUENCY_MAX, entTrainingScore.getFrequencyMax());
            pstTrainingScore.setFloat(FLD_SCORE, entTrainingScore.getScore());
            pstTrainingScore.setString(FLD_NOTE, entTrainingScore.getNote());
            pstTrainingScore.setDate(FLD_VALID_START, entTrainingScore.getValidStart());
            pstTrainingScore.setDate(FLD_VALID_END, entTrainingScore.getValidEnd());
            pstTrainingScore.insert();
            entTrainingScore.setOID(pstTrainingScore.getLong(FLD_TRAINING_SCORE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingScore(0), DBException.UNKNOWN);
        }
        return entTrainingScore.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((TrainingScore) entity);
    }

    public static void resultToObject(ResultSet rs, TrainingScore entTrainingScore) {
        try {
            entTrainingScore.setOID(rs.getLong(PstTrainingScore.fieldNames[PstTrainingScore.FLD_TRAINING_SCORE_ID]));
            entTrainingScore.setTrainingId(rs.getLong(PstTrainingScore.fieldNames[PstTrainingScore.FLD_TRAINING_ID]));
            entTrainingScore.setPointMin(rs.getFloat(PstTrainingScore.fieldNames[PstTrainingScore.FLD_POINT_MIN]));
            entTrainingScore.setPointMax(rs.getFloat(PstTrainingScore.fieldNames[PstTrainingScore.FLD_POINT_MAX]));
            entTrainingScore.setDurationMin(rs.getFloat(PstTrainingScore.fieldNames[PstTrainingScore.FLD_DURATION_MIN]));
            entTrainingScore.setDurationMax(rs.getFloat(PstTrainingScore.fieldNames[PstTrainingScore.FLD_DURATION_MAX]));
            entTrainingScore.setFrequencyMin(rs.getFloat(PstTrainingScore.fieldNames[PstTrainingScore.FLD_FREQUENCY_MIN]));
            entTrainingScore.setFrequencyMax(rs.getFloat(PstTrainingScore.fieldNames[PstTrainingScore.FLD_FREQUENCY_MAX]));
            entTrainingScore.setScore(rs.getFloat(PstTrainingScore.fieldNames[PstTrainingScore.FLD_SCORE]));
            entTrainingScore.setNote(rs.getString(PstTrainingScore.fieldNames[PstTrainingScore.FLD_NOTE]));
            entTrainingScore.setValidStart(rs.getDate(PstTrainingScore.fieldNames[PstTrainingScore.FLD_VALID_START]));
            entTrainingScore.setValidEnd(rs.getDate(PstTrainingScore.fieldNames[PstTrainingScore.FLD_VALID_END]));
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
            String sql = "SELECT * FROM " + TBL_TRAINING_SCORE;
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
                TrainingScore entTrainingScore = new TrainingScore();
                resultToObject(rs, entTrainingScore);
                lists.add(entTrainingScore);
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
    
    public static Vector<DynamicField> getData(String query, Vector<String> fields){
        Vector list = new Vector();
        DBResultSet dbrs = null;
        try {
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DynamicField dynamic = new DynamicField();
                for (int i=0; i<fields.size(); i++){
                    String dataField = (String)fields.get(i);
                    dynamic.setFields(dataField, rs.getString(dataField));
                }
                list.add(dynamic);
            }
            rs.close();
            return list;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }
    
    public static Vector<DynamicField> getDistinctTrainingScore(){
        Vector<DynamicField> list = new Vector();
        String query  = " SELECT DISTINCT "+TBL_TRAINING_SCORE+"."+fieldNames[FLD_TRAINING_ID]+", ";
               query += " "+PstTraining.TBL_HR_TRAINING+"."+PstTraining.fieldNames[PstTraining.FLD_NAME]+" FROM "+TBL_TRAINING_SCORE;
               query += " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" ON ";
               query += " "+PstTraining.TBL_HR_TRAINING+"."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+"=";
               query += " "+TBL_TRAINING_SCORE+"."+fieldNames[FLD_TRAINING_ID];
        Vector<String> fields = new Vector();
        fields.add(TBL_TRAINING_SCORE+"."+fieldNames[FLD_TRAINING_ID]);
        fields.add(PstTraining.TBL_HR_TRAINING+"."+PstTraining.fieldNames[PstTraining.FLD_NAME]);
        list = getData(query, fields);        
        return list;
    }

    public static boolean checkOID(long entTrainingScoreId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_TRAINING_SCORE + " WHERE "
                    + PstTrainingScore.fieldNames[PstTrainingScore.FLD_TRAINING_SCORE_ID] + " = " + entTrainingScoreId;
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
            String sql = "SELECT COUNT(" + PstTrainingScore.fieldNames[PstTrainingScore.FLD_TRAINING_SCORE_ID] + ") FROM " + TBL_TRAINING_SCORE;
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
                    TrainingScore entTrainingScore = (TrainingScore) list.get(ls);
                    if (oid == entTrainingScore.getOID()) {
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