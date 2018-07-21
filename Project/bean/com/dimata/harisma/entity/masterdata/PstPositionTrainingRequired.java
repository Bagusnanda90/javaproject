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
public class PstPositionTrainingRequired extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_TRAINING_REQUIRED = "hr_pos_training_req";
    public static final int FLD_POS_TRAINING_REQ_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_TRAINING_ID = 2;
    public static final int FLD_DURATION_MIN = 3;
    public static final int FLD_DURATION_RECOMMENDED = 4;
    public static final int FLD_POINT_MIN = 5;
    public static final int FLD_POINT_RECOMMENDED = 6;
    public static final int FLD_NOTE = 7;
    public static String[] fieldNames = {
        "POS_TRAINING_REQ_ID",
        "POSITION_ID",
        "TRAINING_ID",
        "DURATION_MIN",
        "DURATION_RECOMMENDED",
        "POINT_MIN",
        "POINT_RECOMMENDED",
        "NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING
    };

    public PstPositionTrainingRequired() {
    }

    public PstPositionTrainingRequired(int i) throws DBException {
        super(new PstPositionTrainingRequired());
    }

    public PstPositionTrainingRequired(String sOid) throws DBException {
        super(new PstPositionTrainingRequired(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionTrainingRequired(long lOid) throws DBException {
        super(new PstPositionTrainingRequired(0));
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
        return TBL_POSITION_TRAINING_REQUIRED;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionTrainingRequired().getClass().getName();
    }

    public static PositionTrainingRequired fetchExc(long oid) throws DBException {
        try {
            PositionTrainingRequired entPositionTrainingRequired = new PositionTrainingRequired();
            PstPositionTrainingRequired pstPositionTrainingRequired = new PstPositionTrainingRequired(oid);
            entPositionTrainingRequired.setOID(oid);
            entPositionTrainingRequired.setPositionId(pstPositionTrainingRequired.getlong(FLD_POSITION_ID));
            entPositionTrainingRequired.setTrainingId(pstPositionTrainingRequired.getlong(FLD_TRAINING_ID));
            entPositionTrainingRequired.setDurationMin(pstPositionTrainingRequired.getInt(FLD_DURATION_MIN));
            entPositionTrainingRequired.setDurationRecommended(pstPositionTrainingRequired.getInt(FLD_DURATION_RECOMMENDED));
            entPositionTrainingRequired.setPointMin(pstPositionTrainingRequired.getInt(FLD_POINT_MIN));
            entPositionTrainingRequired.setPointRecommended(pstPositionTrainingRequired.getInt(FLD_POINT_RECOMMENDED));
            entPositionTrainingRequired.setNote(pstPositionTrainingRequired.getString(FLD_NOTE));
            return entPositionTrainingRequired;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionTrainingRequired(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionTrainingRequired entPositionTrainingRequired = fetchExc(entity.getOID());
        entity = (Entity) entPositionTrainingRequired;
        return entPositionTrainingRequired.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PositionTrainingRequired) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PositionTrainingRequired) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(PositionTrainingRequired entPositionTrainingRequired) throws DBException {
        try {
            PstPositionTrainingRequired pstPositionTrainingRequired = new PstPositionTrainingRequired(0);
            pstPositionTrainingRequired.setLong(FLD_POSITION_ID, entPositionTrainingRequired.getPositionId());
            pstPositionTrainingRequired.setLong(FLD_TRAINING_ID, entPositionTrainingRequired.getTrainingId());
            pstPositionTrainingRequired.setInt(FLD_DURATION_MIN, entPositionTrainingRequired.getDurationMin());
            pstPositionTrainingRequired.setInt(FLD_DURATION_RECOMMENDED, entPositionTrainingRequired.getDurationRecommended());
            pstPositionTrainingRequired.setInt(FLD_POINT_MIN, entPositionTrainingRequired.getPointMin());
            pstPositionTrainingRequired.setInt(FLD_POINT_RECOMMENDED, entPositionTrainingRequired.getPointRecommended());
            pstPositionTrainingRequired.setString(FLD_NOTE, entPositionTrainingRequired.getNote());

            pstPositionTrainingRequired.insert();
            entPositionTrainingRequired.setOID(pstPositionTrainingRequired.getLong(FLD_POS_TRAINING_REQ_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionTrainingRequired(0), DBException.UNKNOWN);
        }
        return entPositionTrainingRequired.getOID();
    }

    public static long updateExc(PositionTrainingRequired entPositionTrainingRequired) throws DBException {
        try {
            if (entPositionTrainingRequired.getOID() != 0) {
                PstPositionTrainingRequired pstPositionTrainingRequired = new PstPositionTrainingRequired(entPositionTrainingRequired.getOID());
                pstPositionTrainingRequired.setLong(FLD_POSITION_ID, entPositionTrainingRequired.getPositionId());
                pstPositionTrainingRequired.setLong(FLD_TRAINING_ID, entPositionTrainingRequired.getTrainingId());
                pstPositionTrainingRequired.setInt(FLD_DURATION_MIN, entPositionTrainingRequired.getDurationMin());
                pstPositionTrainingRequired.setInt(FLD_DURATION_RECOMMENDED, entPositionTrainingRequired.getDurationRecommended());
                pstPositionTrainingRequired.setInt(FLD_POINT_MIN, entPositionTrainingRequired.getPointMin());
                pstPositionTrainingRequired.setInt(FLD_POINT_RECOMMENDED, entPositionTrainingRequired.getPointRecommended());
                pstPositionTrainingRequired.setString(FLD_NOTE, entPositionTrainingRequired.getNote());

                pstPositionTrainingRequired.update();
                return entPositionTrainingRequired.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionTrainingRequired(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPositionTrainingRequired pstPositionTrainingRequired = new PstPositionTrainingRequired(oid);
            pstPositionTrainingRequired.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionTrainingRequired(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_POSITION_TRAINING_REQUIRED;
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
                PositionTrainingRequired entPositionTrainingRequired = new PositionTrainingRequired();
                resultToObject(rs, entPositionTrainingRequired);
                lists.add(entPositionTrainingRequired);
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
            String sql  = "SELECT * FROM " + TBL_POSITION_TRAINING_REQUIRED;
                   sql += " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" ON "+TBL_POSITION_TRAINING_REQUIRED+
                   "."+PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_TRAINING_ID]+"="+PstTraining.TBL_HR_TRAINING+"."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID];
                   sql += " WHERE "+TBL_POSITION_TRAINING_REQUIRED+"."+PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POSITION_ID]+" = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                PositionTrainingRequired entPositionTrainingRequired = new PositionTrainingRequired();
                Training train = new Training();
                resultToObject(rs, entPositionTrainingRequired);
                vect.add(entPositionTrainingRequired);
                train.setName(rs.getString(PstTraining.fieldNames[PstTraining.FLD_NAME]));
                vect.add(train);
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
            String sql  = "SELECT * FROM " + TBL_POSITION_TRAINING_REQUIRED;
                   sql += " INNER JOIN hr_position ON "+TBL_POSITION_TRAINING_REQUIRED+".POSITION_ID=hr_position.POSITION_ID ";
                   sql += " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" ON "+TBL_POSITION_TRAINING_REQUIRED+
                   "."+PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_TRAINING_ID]+"="+PstTraining.TBL_HR_TRAINING+"."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID];
                   sql += " WHERE "+TBL_POSITION_TRAINING_REQUIRED+"."+PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POS_TRAINING_REQ_ID]+" = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                PositionTrainingRequired entPositionTrainingRequired = new PositionTrainingRequired();
                resultToObject(rs, entPositionTrainingRequired);
                vect.add(entPositionTrainingRequired);
                Position pos = new Position();
                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(pos);
                Training train = new Training();
                train.setName(rs.getString(PstTraining.fieldNames[PstTraining.FLD_NAME]));
                vect.add(train);
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

    private static void resultToObject(ResultSet rs, PositionTrainingRequired entPositionTrainingRequired) {
        try {
            entPositionTrainingRequired.setOID(rs.getLong(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POS_TRAINING_REQ_ID]));
            entPositionTrainingRequired.setPositionId(rs.getLong(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POSITION_ID]));
            entPositionTrainingRequired.setTrainingId(rs.getLong(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_TRAINING_ID]));
            entPositionTrainingRequired.setDurationMin(rs.getInt(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_DURATION_MIN]));
            entPositionTrainingRequired.setDurationRecommended(rs.getInt(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_DURATION_RECOMMENDED]));
            entPositionTrainingRequired.setPointMin(rs.getInt(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POINT_MIN]));
            entPositionTrainingRequired.setPointRecommended(rs.getInt(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POINT_RECOMMENDED]));
            entPositionTrainingRequired.setNote(rs.getString(PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_NOTE]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entPositionTrainingRequiredId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_TRAINING_REQUIRED + " WHERE "
                    + PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POS_TRAINING_REQ_ID] + " = " + entPositionTrainingRequiredId;

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
            String sql = "SELECT COUNT(" + PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POS_TRAINING_REQ_ID] + ") FROM " + TBL_POSITION_TRAINING_REQUIRED;
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
                    PositionTrainingRequired entPositionTrainingRequired = (PositionTrainingRequired) list.get(ls);
                    if (oid == entPositionTrainingRequired.getOID()) {
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
