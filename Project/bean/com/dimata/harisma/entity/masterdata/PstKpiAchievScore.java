/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Putu Hendra
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

public class PstKpiAchievScore extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_KPI_ACHIEV_SCORE = "hr_kpi_achiev_score";
    public static final int FLD_KPI_ACHIEV_SCORE_ID = 0;
    public static final int FLD_KPI_LIST_ID = 1;
    public static final int FLD_ACHIEV_PCTG_MIN = 2;
    public static final int FLD_ACHIEV_PCTG_MAX = 3;
    public static final int FLD_ACHIEV_DURATION_MIN = 4;
    public static final int FLD_ACHIEV_DURATION_MAX = 5;
    public static final int FLD_SCORE = 6;
    public static final int FLD_VALID_START = 7;
    public static final int FLD_VALID_END = 8;
    public static String[] fieldNames = {
        "KPI_ACHIEV_SCORE_ID",
        "KPI_LIST_ID",
        "ACHIEV_PCTG_MIN",
        "ACHIEV_PCTG_MAX",
        "ACHIEV_DURATION_MIN",
        "ACHIEV_DURATION_MAX",
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

    public PstKpiAchievScore() {
    }

    public PstKpiAchievScore(int i) throws DBException {
        super(new PstKpiAchievScore());
    }

    public PstKpiAchievScore(String sOid) throws DBException {
        super(new PstKpiAchievScore(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKpiAchievScore(long lOid) throws DBException {
        super(new PstKpiAchievScore(0));
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
        return TBL_KPI_ACHIEV_SCORE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKpiAchievScore().getClass().getName();
    }

    public static KpiAchievScore fetchExc(long oid) throws DBException {
        try {
            KpiAchievScore entKpiAchievScore = new KpiAchievScore();
            PstKpiAchievScore pstKpiAchievScore = new PstKpiAchievScore(oid);
            entKpiAchievScore.setOID(oid);
            entKpiAchievScore.setKpiListId(pstKpiAchievScore.getLong(FLD_KPI_LIST_ID));
            entKpiAchievScore.setAchievPctgMin(pstKpiAchievScore.getfloat(FLD_ACHIEV_PCTG_MIN));
            entKpiAchievScore.setAchievPctgMax(pstKpiAchievScore.getfloat(FLD_ACHIEV_PCTG_MAX));
            entKpiAchievScore.setAchievDurationMin(pstKpiAchievScore.getfloat(FLD_ACHIEV_DURATION_MIN));
            entKpiAchievScore.setAchievDurationMax(pstKpiAchievScore.getfloat(FLD_ACHIEV_DURATION_MAX));
            entKpiAchievScore.setScore(pstKpiAchievScore.getfloat(FLD_SCORE));
            entKpiAchievScore.setValidStart(pstKpiAchievScore.getDate(FLD_VALID_START));
            entKpiAchievScore.setValidEnd(pstKpiAchievScore.getDate(FLD_VALID_END));
            return entKpiAchievScore;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKpiAchievScore(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        KpiAchievScore entKpiAchievScore = fetchExc(entity.getOID());
        entity = (Entity) entKpiAchievScore;
        return entKpiAchievScore.getOID();
    }

    public static synchronized long updateExc(KpiAchievScore entKpiAchievScore) throws DBException {
        try {
            if (entKpiAchievScore.getOID() != 0) {
                PstKpiAchievScore pstKpiAchievScore = new PstKpiAchievScore(entKpiAchievScore.getOID());
                pstKpiAchievScore.setLong(FLD_KPI_LIST_ID, entKpiAchievScore.getKpiListId());
                pstKpiAchievScore.setFloat(FLD_ACHIEV_PCTG_MIN, entKpiAchievScore.getAchievPctgMin());
                pstKpiAchievScore.setFloat(FLD_ACHIEV_PCTG_MAX, entKpiAchievScore.getAchievPctgMax());
                pstKpiAchievScore.setFloat(FLD_ACHIEV_DURATION_MIN, entKpiAchievScore.getAchievDurationMin());
                pstKpiAchievScore.setFloat(FLD_ACHIEV_DURATION_MAX, entKpiAchievScore.getAchievDurationMax());
                pstKpiAchievScore.setFloat(FLD_SCORE, entKpiAchievScore.getScore());
                pstKpiAchievScore.setDate(FLD_VALID_START, entKpiAchievScore.getValidStart());
                pstKpiAchievScore.setDate(FLD_VALID_END, entKpiAchievScore.getValidEnd());
                pstKpiAchievScore.update();
                return entKpiAchievScore.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKpiAchievScore(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((KpiAchievScore) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstKpiAchievScore pstKpiAchievScore = new PstKpiAchievScore(oid);
            pstKpiAchievScore.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKpiAchievScore(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(KpiAchievScore entKpiAchievScore) throws DBException {
        try {
            PstKpiAchievScore pstKpiAchievScore = new PstKpiAchievScore(0);
            pstKpiAchievScore.setLong(FLD_KPI_LIST_ID, entKpiAchievScore.getKpiListId());
            pstKpiAchievScore.setFloat(FLD_ACHIEV_PCTG_MIN, entKpiAchievScore.getAchievPctgMin());
            pstKpiAchievScore.setFloat(FLD_ACHIEV_PCTG_MAX, entKpiAchievScore.getAchievPctgMax());
            pstKpiAchievScore.setFloat(FLD_ACHIEV_DURATION_MIN, entKpiAchievScore.getAchievDurationMin());
            pstKpiAchievScore.setFloat(FLD_ACHIEV_DURATION_MAX, entKpiAchievScore.getAchievDurationMax());
            pstKpiAchievScore.setFloat(FLD_SCORE, entKpiAchievScore.getScore());
            pstKpiAchievScore.setDate(FLD_VALID_START, entKpiAchievScore.getValidStart());
            pstKpiAchievScore.setDate(FLD_VALID_END, entKpiAchievScore.getValidEnd());
            pstKpiAchievScore.insert();
            entKpiAchievScore.setOID(pstKpiAchievScore.getLong(FLD_KPI_ACHIEV_SCORE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKpiAchievScore(0), DBException.UNKNOWN);
        }
        return entKpiAchievScore.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((KpiAchievScore) entity);
    }

    public static void resultToObject(ResultSet rs, KpiAchievScore entKpiAchievScore) {
        try {
            entKpiAchievScore.setOID(rs.getLong(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_KPI_ACHIEV_SCORE_ID]));
            entKpiAchievScore.setKpiListId(rs.getLong(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_KPI_LIST_ID]));
            entKpiAchievScore.setAchievPctgMin(rs.getFloat(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_ACHIEV_PCTG_MIN]));
            entKpiAchievScore.setAchievPctgMax(rs.getFloat(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_ACHIEV_PCTG_MAX]));
            entKpiAchievScore.setAchievDurationMin(rs.getFloat(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_ACHIEV_DURATION_MIN]));
            entKpiAchievScore.setAchievDurationMax(rs.getFloat(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_ACHIEV_DURATION_MAX]));
            entKpiAchievScore.setScore(rs.getFloat(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_SCORE]));
            entKpiAchievScore.setValidStart(rs.getDate(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_VALID_START]));
            entKpiAchievScore.setValidEnd(rs.getDate(PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_VALID_END]));
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
            String sql = "SELECT * FROM " + TBL_KPI_ACHIEV_SCORE;
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
                KpiAchievScore entKpiAchievScore = new KpiAchievScore();
                resultToObject(rs, entKpiAchievScore);
                lists.add(entKpiAchievScore);
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
    
    public static Vector<DynamicField> getDistinctKpiAchievScore(){
        Vector<DynamicField> list = new Vector();
        String query  = " SELECT DISTINCT "+TBL_KPI_ACHIEV_SCORE+"."+fieldNames[FLD_KPI_LIST_ID]+", ";
               query += " "+PstKPI_List.TBL_HR_KPI_LIST+"."+PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_TITLE]+" FROM "+TBL_KPI_ACHIEV_SCORE;
               query += " INNER JOIN "+PstKPI_List.TBL_HR_KPI_LIST+" ON ";
               query += " "+PstKPI_List.TBL_HR_KPI_LIST+"."+PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_LIST_ID]+"=";
               query += TBL_KPI_ACHIEV_SCORE+"."+fieldNames[FLD_KPI_LIST_ID];
        Vector<String> fields = new Vector();
        fields.add(TBL_KPI_ACHIEV_SCORE+"."+fieldNames[FLD_KPI_LIST_ID]);
        fields.add(PstKPI_List.TBL_HR_KPI_LIST+"."+PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_TITLE]);
        list = getData(query, fields);        
        return list;
    }

    public static boolean checkOID(long entKpiAchievScoreId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_KPI_ACHIEV_SCORE + " WHERE "
                    + PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_KPI_ACHIEV_SCORE_ID] + " = " + entKpiAchievScoreId;
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
            String sql = "SELECT COUNT(" + PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_KPI_ACHIEV_SCORE_ID] + ") FROM " + TBL_KPI_ACHIEV_SCORE;
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
                    KpiAchievScore entKpiAchievScore = (KpiAchievScore) list.get(ls);
                    if (oid == entKpiAchievScore.getOID()) {
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
