/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Hendra Putu
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

public class PstEducationScore extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_EDUCATION_SCORE = "hr_education_score";
    public static final int FLD_EDUCATION_SCORE_ID = 0;
    public static final int FLD_EDUCATION_ID = 1;
    public static final int FLD_POINT_MIN = 2;
    public static final int FLD_POINT_MAX = 3;
    public static final int FLD_DURATION_MIN = 4;
    public static final int FLD_DURATION_MAX = 5;
    public static final int FLD_SCORE = 6;
    public static final int FLD_VALID_START = 7;
    public static final int FLD_VALID_END = 8;
    public static String[] fieldNames = {
        "EDUCATION_SCORE_ID",
        "EDUCATION_ID",
        "POINT_MIN",
        "POINT_MAX",
        "DURATION_MIN",
        "DURATION_MAX",
        "SCORE",
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
        TYPE_DATE,
        TYPE_DATE
    };

    public PstEducationScore() {
    }

    public PstEducationScore(int i) throws DBException {
        super(new PstEducationScore());
    }

    public PstEducationScore(String sOid) throws DBException {
        super(new PstEducationScore(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEducationScore(long lOid) throws DBException {
        super(new PstEducationScore(0));
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
        return TBL_EDUCATION_SCORE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEducationScore().getClass().getName();
    }

    public static EducationScore fetchExc(long oid) throws DBException {
        try {
            EducationScore entEducationScore = new EducationScore();
            PstEducationScore pstEducationScore = new PstEducationScore(oid);
            entEducationScore.setOID(oid);
            entEducationScore.setEducationId(pstEducationScore.getLong(FLD_EDUCATION_ID));
            entEducationScore.setPointMin(pstEducationScore.getfloat(FLD_POINT_MIN));
            entEducationScore.setPointMax(pstEducationScore.getfloat(FLD_POINT_MAX));
            entEducationScore.setDurationMin(pstEducationScore.getfloat(FLD_DURATION_MIN));
            entEducationScore.setDurationMax(pstEducationScore.getfloat(FLD_DURATION_MAX));
            entEducationScore.setScore(pstEducationScore.getfloat(FLD_SCORE));
            entEducationScore.setValidStart(pstEducationScore.getDate(FLD_VALID_START));
            entEducationScore.setValidEnd(pstEducationScore.getDate(FLD_VALID_END));
            return entEducationScore;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEducationScore(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        EducationScore entEducationScore = fetchExc(entity.getOID());
        entity = (Entity) entEducationScore;
        return entEducationScore.getOID();
    }

    public static synchronized long updateExc(EducationScore entEducationScore) throws DBException {
        try {
            if (entEducationScore.getOID() != 0) {
                PstEducationScore pstEducationScore = new PstEducationScore(entEducationScore.getOID());
                pstEducationScore.setLong(FLD_EDUCATION_ID, entEducationScore.getEducationId());
                pstEducationScore.setFloat(FLD_POINT_MIN, entEducationScore.getPointMin());
                pstEducationScore.setFloat(FLD_POINT_MAX, entEducationScore.getPointMax());
                pstEducationScore.setFloat(FLD_DURATION_MIN, entEducationScore.getDurationMin());
                pstEducationScore.setFloat(FLD_DURATION_MAX, entEducationScore.getDurationMax());
                pstEducationScore.setFloat(FLD_SCORE, entEducationScore.getScore());
                pstEducationScore.setDate(FLD_VALID_START, entEducationScore.getValidStart());
                pstEducationScore.setDate(FLD_VALID_END, entEducationScore.getValidEnd());
                pstEducationScore.update();
                return entEducationScore.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEducationScore(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((EducationScore) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstEducationScore pstEducationScore = new PstEducationScore(oid);
            pstEducationScore.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEducationScore(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(EducationScore entEducationScore) throws DBException {
        try {
            PstEducationScore pstEducationScore = new PstEducationScore(0);
            pstEducationScore.setLong(FLD_EDUCATION_ID, entEducationScore.getEducationId());
            pstEducationScore.setFloat(FLD_POINT_MIN, entEducationScore.getPointMin());
            pstEducationScore.setFloat(FLD_POINT_MAX, entEducationScore.getPointMax());
            pstEducationScore.setFloat(FLD_DURATION_MIN, entEducationScore.getDurationMin());
            pstEducationScore.setFloat(FLD_DURATION_MAX, entEducationScore.getDurationMax());
            pstEducationScore.setFloat(FLD_SCORE, entEducationScore.getScore());
            pstEducationScore.setDate(FLD_VALID_START, entEducationScore.getValidStart());
            pstEducationScore.setDate(FLD_VALID_END, entEducationScore.getValidEnd());
            pstEducationScore.insert();
            entEducationScore.setOID(pstEducationScore.getLong(FLD_EDUCATION_SCORE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEducationScore(0), DBException.UNKNOWN);
        }
        return entEducationScore.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((EducationScore) entity);
    }

    public static void resultToObject(ResultSet rs, EducationScore entEducationScore) {
        try {
            entEducationScore.setOID(rs.getLong(PstEducationScore.fieldNames[PstEducationScore.FLD_EDUCATION_SCORE_ID]));
            entEducationScore.setEducationId(rs.getLong(PstEducationScore.fieldNames[PstEducationScore.FLD_EDUCATION_ID]));
            entEducationScore.setPointMin(rs.getFloat(PstEducationScore.fieldNames[PstEducationScore.FLD_POINT_MIN]));
            entEducationScore.setPointMax(rs.getFloat(PstEducationScore.fieldNames[PstEducationScore.FLD_POINT_MAX]));
            entEducationScore.setDurationMin(rs.getFloat(PstEducationScore.fieldNames[PstEducationScore.FLD_DURATION_MIN]));
            entEducationScore.setDurationMax(rs.getFloat(PstEducationScore.fieldNames[PstEducationScore.FLD_DURATION_MAX]));
            entEducationScore.setScore(rs.getFloat(PstEducationScore.fieldNames[PstEducationScore.FLD_SCORE]));
            entEducationScore.setValidStart(rs.getDate(PstEducationScore.fieldNames[PstEducationScore.FLD_VALID_START]));
            entEducationScore.setValidEnd(rs.getDate(PstEducationScore.fieldNames[PstEducationScore.FLD_VALID_END]));
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
            String sql = "SELECT * FROM " + TBL_EDUCATION_SCORE;
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
                EducationScore entEducationScore = new EducationScore();
                resultToObject(rs, entEducationScore);
                lists.add(entEducationScore);
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
    
    public static Vector<DynamicField> getDistinctEducationScore(){
        Vector<DynamicField> list = new Vector();
        String query  = " SELECT DISTINCT "+TBL_EDUCATION_SCORE+"."+fieldNames[FLD_EDUCATION_ID]+", ";
               query += " "+PstEducation.TBL_HR_EDUCATION+"."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION]+" FROM "+TBL_EDUCATION_SCORE;
               query += " INNER JOIN "+PstEducation.TBL_HR_EDUCATION+" ON ";
               query += " "+PstEducation.TBL_HR_EDUCATION+"."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+"=";
               query += TBL_EDUCATION_SCORE+"."+fieldNames[FLD_EDUCATION_ID];
        Vector<String> fields = new Vector();
        fields.add(TBL_EDUCATION_SCORE+"."+fieldNames[FLD_EDUCATION_ID]);
        fields.add(PstEducation.TBL_HR_EDUCATION+"."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION]);
        list = getData(query, fields);        
        return list;
    }

    public static boolean checkOID(long entEducationScoreId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EDUCATION_SCORE + " WHERE "
                    + PstEducationScore.fieldNames[PstEducationScore.FLD_EDUCATION_SCORE_ID] + " = " + entEducationScoreId;
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
            String sql = "SELECT COUNT(" + PstEducationScore.fieldNames[PstEducationScore.FLD_EDUCATION_SCORE_ID] + ") FROM " + TBL_EDUCATION_SCORE;
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
                    EducationScore entEducationScore = (EducationScore) list.get(ls);
                    if (oid == entEducationScore.getOID()) {
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
