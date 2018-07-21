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
public class PstPositionEducationRequired extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_EDUCATION_REQUIRED = "hr_pos_education_req";
    public static final int FLD_POS_EDUCATION_REQ_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_EDUCATION_ID = 2;
    public static final int FLD_DURATION_MIN = 3;
    public static final int FLD_DURATION_RECOMMENDED = 4;
    public static final int FLD_POINT_MIN = 5;
    public static final int FLD_POINT_RECOMMENDED = 6;
    public static final int FLD_NOTE = 7;
    public static String[] fieldNames = {
        "POS_EDUCATION_REQ_ID",
        "POSITION_ID",
        "EDUCATION_ID",
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

    public PstPositionEducationRequired() {
    }

    public PstPositionEducationRequired(int i) throws DBException {
        super(new PstPositionEducationRequired());
    }

    public PstPositionEducationRequired(String sOid) throws DBException {
        super(new PstPositionEducationRequired(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionEducationRequired(long lOid) throws DBException {
        super(new PstPositionEducationRequired(0));
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
        return TBL_POSITION_EDUCATION_REQUIRED;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionEducationRequired().getClass().getName();
    }

    public static PositionEducationRequired fetchExc(long oid) throws DBException {
        try {
            PositionEducationRequired entPositionEducationRequired = new PositionEducationRequired();
            PstPositionEducationRequired pstPositionEducationRequired = new PstPositionEducationRequired(oid);
            entPositionEducationRequired.setOID(oid);
            entPositionEducationRequired.setPositionId(pstPositionEducationRequired.getlong(FLD_POSITION_ID));
            entPositionEducationRequired.setEducationId(pstPositionEducationRequired.getlong(FLD_EDUCATION_ID));
            entPositionEducationRequired.setDurationMin(pstPositionEducationRequired.getInt(FLD_DURATION_MIN));
            entPositionEducationRequired.setDurationRecommended(pstPositionEducationRequired.getInt(FLD_DURATION_RECOMMENDED));
            entPositionEducationRequired.setPointMin(pstPositionEducationRequired.getInt(FLD_POINT_MIN));
            entPositionEducationRequired.setPointRecommended(pstPositionEducationRequired.getInt(FLD_POINT_RECOMMENDED));
            entPositionEducationRequired.setNote(pstPositionEducationRequired.getString(FLD_NOTE));
            return entPositionEducationRequired;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionEducationRequired(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionEducationRequired entPositionEducationRequired = fetchExc(entity.getOID());
        entity = (Entity) entPositionEducationRequired;
        return entPositionEducationRequired.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PositionEducationRequired) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PositionEducationRequired) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(PositionEducationRequired entPositionEducationRequired) throws DBException {
        try {
            PstPositionEducationRequired pstPositionEducationRequired = new PstPositionEducationRequired(0);
            pstPositionEducationRequired.setLong(FLD_POSITION_ID, entPositionEducationRequired.getPositionId());
            pstPositionEducationRequired.setLong(FLD_EDUCATION_ID, entPositionEducationRequired.getEducationId());
            pstPositionEducationRequired.setInt(FLD_DURATION_MIN, entPositionEducationRequired.getDurationMin());
            pstPositionEducationRequired.setInt(FLD_DURATION_RECOMMENDED, entPositionEducationRequired.getDurationRecommended());
            pstPositionEducationRequired.setInt(FLD_POINT_MIN, entPositionEducationRequired.getPointMin());
            pstPositionEducationRequired.setInt(FLD_POINT_RECOMMENDED, entPositionEducationRequired.getPointRecommended());
            pstPositionEducationRequired.setString(FLD_NOTE, entPositionEducationRequired.getNote());

            pstPositionEducationRequired.insert();
            entPositionEducationRequired.setOID(pstPositionEducationRequired.getLong(FLD_POS_EDUCATION_REQ_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionEducationRequired(0), DBException.UNKNOWN);
        }
        return entPositionEducationRequired.getOID();
    }

    public static long updateExc(PositionEducationRequired entPositionEducationRequired) throws DBException {
        try {
            if (entPositionEducationRequired.getOID() != 0) {
                PstPositionEducationRequired pstPositionEducationRequired = new PstPositionEducationRequired(entPositionEducationRequired.getOID());
                pstPositionEducationRequired.setLong(FLD_POSITION_ID, entPositionEducationRequired.getPositionId());
                pstPositionEducationRequired.setLong(FLD_EDUCATION_ID, entPositionEducationRequired.getEducationId());
                pstPositionEducationRequired.setInt(FLD_DURATION_MIN, entPositionEducationRequired.getDurationMin());
                pstPositionEducationRequired.setInt(FLD_DURATION_RECOMMENDED, entPositionEducationRequired.getDurationRecommended());
                pstPositionEducationRequired.setInt(FLD_POINT_MIN, entPositionEducationRequired.getPointMin());
                pstPositionEducationRequired.setInt(FLD_POINT_RECOMMENDED, entPositionEducationRequired.getPointRecommended());
                pstPositionEducationRequired.setString(FLD_NOTE, entPositionEducationRequired.getNote());

                pstPositionEducationRequired.update();
                return entPositionEducationRequired.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionEducationRequired(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPositionEducationRequired pstPositionEducationRequired = new PstPositionEducationRequired(oid);
            pstPositionEducationRequired.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionEducationRequired(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_POSITION_EDUCATION_REQUIRED;
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
                PositionEducationRequired entPositionEducationRequired = new PositionEducationRequired();
                resultToObject(rs, entPositionEducationRequired);
                lists.add(entPositionEducationRequired);
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
            String sql  = "SELECT * FROM " + TBL_POSITION_EDUCATION_REQUIRED;
                   sql += " INNER JOIN "+PstEducation.TBL_HR_EDUCATION+" ON "+TBL_POSITION_EDUCATION_REQUIRED+
                   "."+PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_EDUCATION_ID]+"="+PstEducation.TBL_HR_EDUCATION+"."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID];
                   sql += " WHERE "+TBL_POSITION_EDUCATION_REQUIRED+"."+PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POSITION_ID]+" = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                PositionEducationRequired entPositionEducationRequired = new PositionEducationRequired();
                Education edu = new Education();
                resultToObject(rs, entPositionEducationRequired);
                vect.add(entPositionEducationRequired);
                edu.setEducation(rs.getString(PstEducation.fieldNames[PstEducation.FLD_EDUCATION]));
                vect.add(edu);
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
            String sql  = "SELECT * FROM " + TBL_POSITION_EDUCATION_REQUIRED;
                   sql += " INNER JOIN hr_position ON "+TBL_POSITION_EDUCATION_REQUIRED+".POSITION_ID=hr_position.POSITION_ID ";
                   sql += " INNER JOIN "+PstEducation.TBL_HR_EDUCATION+" ON "+TBL_POSITION_EDUCATION_REQUIRED+
                   "."+PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_EDUCATION_ID]+"="+PstEducation.TBL_HR_EDUCATION+"."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID];
                   sql += " WHERE "+TBL_POSITION_EDUCATION_REQUIRED+"."+PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POS_EDUCATION_REQ_ID]+" = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                PositionEducationRequired entPositionEducationRequired = new PositionEducationRequired();
                resultToObject(rs, entPositionEducationRequired);
                vect.add(entPositionEducationRequired);
                // Position
                Position pos = new Position();
                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(pos);
                // Education
                Education edu = new Education();
                edu.setEducation(rs.getString(PstEducation.fieldNames[PstEducation.FLD_EDUCATION]));
                vect.add(edu);
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
    
    private static void resultToObject(ResultSet rs, PositionEducationRequired entPositionEducationRequired) {
        try {
            entPositionEducationRequired.setOID(rs.getLong(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POS_EDUCATION_REQ_ID]));
            entPositionEducationRequired.setPositionId(rs.getLong(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POSITION_ID]));
            entPositionEducationRequired.setEducationId(rs.getLong(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_EDUCATION_ID]));
            entPositionEducationRequired.setDurationMin(rs.getInt(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_DURATION_MIN]));
            entPositionEducationRequired.setDurationRecommended(rs.getInt(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_DURATION_RECOMMENDED]));
            entPositionEducationRequired.setPointMin(rs.getInt(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POINT_MIN]));
            entPositionEducationRequired.setPointRecommended(rs.getInt(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POINT_RECOMMENDED]));
            entPositionEducationRequired.setNote(rs.getString(PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_NOTE]));


        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entPositionEducationRequiredId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_EDUCATION_REQUIRED + " WHERE "
                    + PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POS_EDUCATION_REQ_ID] + " = " + entPositionEducationRequiredId;

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
            String sql = "SELECT COUNT(" + PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POS_EDUCATION_REQ_ID] + ") FROM " + TBL_POSITION_EDUCATION_REQUIRED;
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
                    PositionEducationRequired entPositionEducationRequired = (PositionEducationRequired) list.get(ls);
                    if (oid == entPositionEducationRequired.getOID()) {
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
