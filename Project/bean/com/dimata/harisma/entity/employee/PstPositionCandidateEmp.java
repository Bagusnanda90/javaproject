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

public class PstPositionCandidateEmp extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_CANDIDATE_EMP = "hr_position_candidate_emp";
    public static final int FLD_POS_CANDIDATE_EMP_ID = 0;
    public static final int FLD_POS_CANDIDATE_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_RANK = 3;
    public static final int FLD_SCORE = 4;
    public static final int FLD_SCORE_NEED = 5;
    public static final int FLD_COMPANY_ID = 6;
    public static final int FLD_DIVISION_ID = 7;
    public static final int FLD_DEPARTMENT_ID = 8;
    public static final int FLD_SECTION_ID = 9;
    public static final int FLD_POSITION_ID = 10;
    public static final int FLD_CANDIDATE_STATUS = 11;
    public static String[] fieldNames = {
        "POS_CANDIDATE_EMP_ID",
        "POS_CANDIDATE_ID",
        "EMPLOYEE_ID",
        "RANK",
        "SCORE",
        "SCORE_NEED",
        "COMPANY_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "POSITION_ID",
        "CANDIDATE_STATUS"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

    public static final int STATUS_START = 0;
    public static final int STATUS_NOMINATION = 1;
    public static final int STATUS_FINAL = 2;
    public static final int DONE = 3;
    public static final int CANCELED = 4;
    public static final String[] statusKey = {"Start", "Nomination", "Final", "Done", "Canceled"};
    public static final int[] statusValue = {0, 1, 2, 3, 4};
    
    public PstPositionCandidateEmp() {
    }

    public PstPositionCandidateEmp(int i) throws DBException {
        super(new PstPositionCandidateEmp());
    }

    public PstPositionCandidateEmp(String sOid) throws DBException {
        super(new PstPositionCandidateEmp(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionCandidateEmp(long lOid) throws DBException {
        super(new PstPositionCandidateEmp(0));
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
        return TBL_POSITION_CANDIDATE_EMP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionCandidateEmp().getClass().getName();
    }

    public static PositionCandidateEmp fetchExc(long oid) throws DBException {
        try {
            PositionCandidateEmp entPositionCandidateEmp = new PositionCandidateEmp();
            PstPositionCandidateEmp pstPositionCandidateEmp = new PstPositionCandidateEmp(oid);
            entPositionCandidateEmp.setOID(oid);
            entPositionCandidateEmp.setPosCandidateId(pstPositionCandidateEmp.getLong(FLD_POS_CANDIDATE_ID));
            entPositionCandidateEmp.setEmployeeId(pstPositionCandidateEmp.getLong(FLD_EMPLOYEE_ID));
            entPositionCandidateEmp.setRank(pstPositionCandidateEmp.getInt(FLD_RANK));
            entPositionCandidateEmp.setScore(pstPositionCandidateEmp.getfloat(FLD_SCORE));
            entPositionCandidateEmp.setScoreNeed(pstPositionCandidateEmp.getfloat(FLD_SCORE_NEED));
            entPositionCandidateEmp.setCompanyId(pstPositionCandidateEmp.getLong(FLD_COMPANY_ID));
            entPositionCandidateEmp.setDivisionId(pstPositionCandidateEmp.getLong(FLD_DIVISION_ID));
            entPositionCandidateEmp.setDepartmentId(pstPositionCandidateEmp.getLong(FLD_DEPARTMENT_ID));
            entPositionCandidateEmp.setSectionId(pstPositionCandidateEmp.getLong(FLD_SECTION_ID));
            entPositionCandidateEmp.setPositionId(pstPositionCandidateEmp.getLong(FLD_POSITION_ID));
            entPositionCandidateEmp.setCandidateStatus(pstPositionCandidateEmp.getInt(FLD_CANDIDATE_STATUS));
            return entPositionCandidateEmp;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateEmp(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionCandidateEmp entPositionCandidateEmp = fetchExc(entity.getOID());
        entity = (Entity) entPositionCandidateEmp;
        return entPositionCandidateEmp.getOID();
    }

    public static synchronized long updateExc(PositionCandidateEmp entPositionCandidateEmp) throws DBException {
        try {
            if (entPositionCandidateEmp.getOID() != 0) {
                PstPositionCandidateEmp pstPositionCandidateEmp = new PstPositionCandidateEmp(entPositionCandidateEmp.getOID());
                pstPositionCandidateEmp.setLong(FLD_POS_CANDIDATE_ID, entPositionCandidateEmp.getPosCandidateId());
                pstPositionCandidateEmp.setLong(FLD_EMPLOYEE_ID, entPositionCandidateEmp.getEmployeeId());
                pstPositionCandidateEmp.setInt(FLD_RANK, entPositionCandidateEmp.getRank());
                pstPositionCandidateEmp.setFloat(FLD_SCORE, entPositionCandidateEmp.getScore());
                pstPositionCandidateEmp.setFloat(FLD_SCORE_NEED, entPositionCandidateEmp.getScoreNeed());
                pstPositionCandidateEmp.setLong(FLD_COMPANY_ID, entPositionCandidateEmp.getCompanyId());
                pstPositionCandidateEmp.setLong(FLD_DIVISION_ID, entPositionCandidateEmp.getDivisionId());
                pstPositionCandidateEmp.setLong(FLD_DEPARTMENT_ID, entPositionCandidateEmp.getDepartmentId());
                pstPositionCandidateEmp.setLong(FLD_SECTION_ID, entPositionCandidateEmp.getSectionId());
                pstPositionCandidateEmp.setLong(FLD_POSITION_ID, entPositionCandidateEmp.getPositionId());
                pstPositionCandidateEmp.setInt(FLD_CANDIDATE_STATUS, entPositionCandidateEmp.getCandidateStatus());
                pstPositionCandidateEmp.update();
                return entPositionCandidateEmp.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateEmp(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionCandidateEmp) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionCandidateEmp pstPositionCandidateEmp = new PstPositionCandidateEmp(oid);
            pstPositionCandidateEmp.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateEmp(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionCandidateEmp entPositionCandidateEmp) throws DBException {
        try {
            PstPositionCandidateEmp pstPositionCandidateEmp = new PstPositionCandidateEmp(0);
            pstPositionCandidateEmp.setLong(FLD_POS_CANDIDATE_ID, entPositionCandidateEmp.getPosCandidateId());
            pstPositionCandidateEmp.setLong(FLD_EMPLOYEE_ID, entPositionCandidateEmp.getEmployeeId());
            pstPositionCandidateEmp.setInt(FLD_RANK, entPositionCandidateEmp.getRank());
            pstPositionCandidateEmp.setFloat(FLD_SCORE, entPositionCandidateEmp.getScore());
            pstPositionCandidateEmp.setFloat(FLD_SCORE_NEED, entPositionCandidateEmp.getScoreNeed());
            pstPositionCandidateEmp.setLong(FLD_COMPANY_ID, entPositionCandidateEmp.getCompanyId());
            pstPositionCandidateEmp.setLong(FLD_DIVISION_ID, entPositionCandidateEmp.getDivisionId());
            pstPositionCandidateEmp.setLong(FLD_DEPARTMENT_ID, entPositionCandidateEmp.getDepartmentId());
            pstPositionCandidateEmp.setLong(FLD_SECTION_ID, entPositionCandidateEmp.getSectionId());
            pstPositionCandidateEmp.setLong(FLD_POSITION_ID, entPositionCandidateEmp.getPositionId());
            pstPositionCandidateEmp.setInt(FLD_CANDIDATE_STATUS, entPositionCandidateEmp.getCandidateStatus());
            pstPositionCandidateEmp.insert();
            entPositionCandidateEmp.setOID(pstPositionCandidateEmp.getLong(FLD_POS_CANDIDATE_EMP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidateEmp(0), DBException.UNKNOWN);
        }
        return entPositionCandidateEmp.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionCandidateEmp) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_CANDIDATE_EMP;
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
                PositionCandidateEmp entPositionCandidateEmp = new PositionCandidateEmp();
                resultToObject(rs, entPositionCandidateEmp);
                lists.add(entPositionCandidateEmp);
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
    
    public static void resultToObject(ResultSet rs, PositionCandidateEmp entPositionCandidateEmp) {
        try {
            entPositionCandidateEmp.setOID(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_POS_CANDIDATE_EMP_ID]));
            entPositionCandidateEmp.setPosCandidateId(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_POS_CANDIDATE_ID]));
            entPositionCandidateEmp.setEmployeeId(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_EMPLOYEE_ID]));
            entPositionCandidateEmp.setRank(rs.getInt(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_RANK]));
            entPositionCandidateEmp.setScore(rs.getFloat(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_SCORE]));
            entPositionCandidateEmp.setScoreNeed(rs.getFloat(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_SCORE_NEED]));
            entPositionCandidateEmp.setCompanyId(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_COMPANY_ID]));
            entPositionCandidateEmp.setDivisionId(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_DIVISION_ID]));
            entPositionCandidateEmp.setDepartmentId(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_DEPARTMENT_ID]));
            entPositionCandidateEmp.setSectionId(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_SECTION_ID]));
            entPositionCandidateEmp.setPositionId(rs.getLong(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_POSITION_ID]));
            entPositionCandidateEmp.setCandidateStatus(rs.getInt(PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_CANDIDATE_STATUS]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long entPositionCandidateEmpId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_CANDIDATE_EMP + " WHERE "
                    + PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_POS_CANDIDATE_EMP_ID] + " = " + entPositionCandidateEmpId;

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
            String sql = "SELECT COUNT(" + PstPositionCandidateEmp.fieldNames[PstPositionCandidateEmp.FLD_POS_CANDIDATE_EMP_ID] + ") FROM " + TBL_POSITION_CANDIDATE_EMP;
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
                    PositionCandidateEmp entPositionCandidateEmp = (PositionCandidateEmp) list.get(ls);
                    if (oid == entPositionCandidateEmp.getOID()) {
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
