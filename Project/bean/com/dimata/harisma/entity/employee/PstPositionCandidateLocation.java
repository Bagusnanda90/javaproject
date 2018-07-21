/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

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

public class PstPositionCandidateLocation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_CANDIDATE_LOCATION = "hr_position_candidate_location";
    public static final int FLD_POS_CANDIDATE_LOCATION_ID = 0;
    public static final int FLD_POS_CANDIDATE_ID = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_DIVISION_ID = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_SECTION_ID = 5;
    public static final int FLD_NUMBER_NEEDED = 6;
    public static final int FLD_DUE_DATE = 7;
    public static String[] fieldNames = {
        "POS_CANDIDATE_LOCATION_ID",
        "POS_CANDIDATE_ID",
        "COMPANY_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "NUMBER_NEEDED",
        "DUE_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_DATE
    };

    public PstPositionCandidateLocation() {
    }

    public PstPositionCandidateLocation(int i) throws DBException {
        super(new PstPositionCandidateLocation());
    }

    public PstPositionCandidateLocation(String sOid) throws DBException {
        super(new PstPositionCandidateLocation(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionCandidateLocation(long lOid) throws DBException {
        super(new PstPositionCandidateLocation(0));
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
        return TBL_POSITION_CANDIDATE_LOCATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionCandidateLocation().getClass().getName();
    }

    public static PositionCandidateLocation fetchExc(long oid) throws DBException {
        try {
            PositionCandidateLocation entPositionCandidateLocation = new PositionCandidateLocation();
            PstPositionCandidateLocation pstPositionCandidateLocation = new PstPositionCandidateLocation(oid);
            entPositionCandidateLocation.setOID(oid);
            entPositionCandidateLocation.setPosCandidateId(pstPositionCandidateLocation.getLong(FLD_POS_CANDIDATE_ID));
            entPositionCandidateLocation.setCompanyId(pstPositionCandidateLocation.getLong(FLD_COMPANY_ID));
            entPositionCandidateLocation.setDivisionId(pstPositionCandidateLocation.getLong(FLD_DIVISION_ID));
            entPositionCandidateLocation.setDepartmentId(pstPositionCandidateLocation.getLong(FLD_DEPARTMENT_ID));
            entPositionCandidateLocation.setSectionId(pstPositionCandidateLocation.getLong(FLD_SECTION_ID));
            entPositionCandidateLocation.setNumberNeeded(pstPositionCandidateLocation.getInt(FLD_NUMBER_NEEDED));
            entPositionCandidateLocation.setDueDate(pstPositionCandidateLocation.getDate(FLD_DUE_DATE));
            return entPositionCandidateLocation;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateLocation(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionCandidateLocation entPositionCandidateLocation = fetchExc(entity.getOID());
        entity = (Entity) entPositionCandidateLocation;
        return entPositionCandidateLocation.getOID();
    }

    public static synchronized long updateExc(PositionCandidateLocation entPositionCandidateLocation) throws DBException {
        try {
            if (entPositionCandidateLocation.getOID() != 0) {
                PstPositionCandidateLocation pstPositionCandidateLocation = new PstPositionCandidateLocation(entPositionCandidateLocation.getOID());
                pstPositionCandidateLocation.setLong(FLD_POS_CANDIDATE_ID, entPositionCandidateLocation.getPosCandidateId());
                pstPositionCandidateLocation.setLong(FLD_COMPANY_ID, entPositionCandidateLocation.getCompanyId());
                pstPositionCandidateLocation.setLong(FLD_DIVISION_ID, entPositionCandidateLocation.getDivisionId());
                pstPositionCandidateLocation.setLong(FLD_DEPARTMENT_ID, entPositionCandidateLocation.getDepartmentId());
                pstPositionCandidateLocation.setLong(FLD_SECTION_ID, entPositionCandidateLocation.getSectionId());
                pstPositionCandidateLocation.setInt(FLD_NUMBER_NEEDED, entPositionCandidateLocation.getNumberNeeded());
                pstPositionCandidateLocation.setDate(FLD_DUE_DATE, entPositionCandidateLocation.getDueDate());
                pstPositionCandidateLocation.update();
                return entPositionCandidateLocation.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateLocation(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionCandidateLocation) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionCandidateLocation pstPositionCandidateLocation = new PstPositionCandidateLocation(oid);
            pstPositionCandidateLocation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateLocation(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionCandidateLocation entPositionCandidateLocation) throws DBException {
        try {
            PstPositionCandidateLocation pstPositionCandidateLocation = new PstPositionCandidateLocation(0);
            pstPositionCandidateLocation.setLong(FLD_POS_CANDIDATE_ID, entPositionCandidateLocation.getPosCandidateId());
            pstPositionCandidateLocation.setLong(FLD_COMPANY_ID, entPositionCandidateLocation.getCompanyId());
            pstPositionCandidateLocation.setLong(FLD_DIVISION_ID, entPositionCandidateLocation.getDivisionId());
            pstPositionCandidateLocation.setLong(FLD_DEPARTMENT_ID, entPositionCandidateLocation.getDepartmentId());
            pstPositionCandidateLocation.setLong(FLD_SECTION_ID, entPositionCandidateLocation.getSectionId());
            pstPositionCandidateLocation.setInt(FLD_NUMBER_NEEDED, entPositionCandidateLocation.getNumberNeeded());
            pstPositionCandidateLocation.setDate(FLD_DUE_DATE, entPositionCandidateLocation.getDueDate());
            pstPositionCandidateLocation.insert();
            entPositionCandidateLocation.setOID(pstPositionCandidateLocation.getLong(FLD_POS_CANDIDATE_LOCATION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateLocation(0), DBException.UNKNOWN);
        }
        return entPositionCandidateLocation.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionCandidateLocation) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_CANDIDATE_LOCATION;
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
                PositionCandidateLocation entPositionCandidateLocation = new PositionCandidateLocation();
                resultToObject(rs, entPositionCandidateLocation);
                lists.add(entPositionCandidateLocation);
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
    
    public static void resultToObject(ResultSet rs, PositionCandidateLocation entPositionCandidateLocation) {
        try {
            entPositionCandidateLocation.setOID(rs.getLong(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_POS_CANDIDATE_LOCATION_ID]));
            entPositionCandidateLocation.setPosCandidateId(rs.getLong(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_POS_CANDIDATE_ID]));
            entPositionCandidateLocation.setCompanyId(rs.getLong(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_COMPANY_ID]));
            entPositionCandidateLocation.setDivisionId(rs.getLong(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_DIVISION_ID]));
            entPositionCandidateLocation.setDepartmentId(rs.getLong(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_DEPARTMENT_ID]));
            entPositionCandidateLocation.setSectionId(rs.getLong(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_SECTION_ID]));
            entPositionCandidateLocation.setNumberNeeded(rs.getInt(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_NUMBER_NEEDED]));
            entPositionCandidateLocation.setDueDate(rs.getDate(PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_DUE_DATE]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long entPositionCandidateLocationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_CANDIDATE_LOCATION + " WHERE "
                    + PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_POS_CANDIDATE_LOCATION_ID] + " = " + entPositionCandidateLocationId;

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
            String sql = "SELECT COUNT(" + PstPositionCandidateLocation.fieldNames[PstPositionCandidateLocation.FLD_POS_CANDIDATE_LOCATION_ID] + ") FROM " + TBL_POSITION_CANDIDATE_LOCATION;
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
                    PositionCandidateLocation entPositionCandidateLocation = (PositionCandidateLocation) list.get(ls);
                    if (oid == entPositionCandidateLocation.getOID()) {
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
