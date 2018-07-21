/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.EmployeeCompetency;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PositionCompetencyRequired;
import com.dimata.harisma.entity.masterdata.PositionEducationRequired;
import com.dimata.harisma.entity.masterdata.PositionTrainingRequired;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstPositionCompetencyRequired;
import com.dimata.harisma.entity.masterdata.PstPositionEducationRequired;
import com.dimata.harisma.entity.masterdata.PstPositionTrainingRequired;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Section;
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

public class PstPositionCandidate extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_CANDIDATE = "hr_position_candidate";
    public static final int FLD_POS_CANDIDATE_ID = 0;
    public static final int FLD_CANDIDATE_TYPE = 1;
    public static final int FLD_POSITION_ID = 2;
    public static final int FLD_TITLE = 3;
    public static final int FLD_OBJECTIVES = 4;
    public static final int FLD_NUM_CANDIDATES = 5;
    public static final int FLD_DUE_DATE = 6;
    public static final int FLD_REQUEST_BY = 7;
    public static final int FLD_DOC_STATUS = 8;
    public static final int FLD_COMPANY = 9;
    public static final int FLD_DIVISION = 10;
    public static final int FLD_DEPARTMENT = 11;
    public static final int FLD_SECTION = 12;
    public static final int FLD_SEARCH_DATE = 13;
    public static String[] fieldNames = {
        "POS_CANDIDATE_ID",
        "CANDIDATE_TYPE",
        "POSITION_ID",
        "TITLE",
        "OBJECTIVES",
        "NUM_CANDIDATES",
        "DUE_DATE",
        "REQUEST_BY",
        "DOC_STATUS",
        "COMPANY",
        "DIVISION",
        "DEPARTMENT",
        "SECTION",
        "SEARCH_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE
    };

    public static final int TYPE_PLACEMENT = 0;
    public static final int TYPE_TALENTPOOL = 1;
    public static final String[] typeKey = {"Placement", "Talentpool"};
    public static final int[] typeValue = {0, 1};
    
    public static final int STATUS_START = 0;
    public static final int STATUS_NOMINATION = 1;
    public static final int STATUS_FINAL = 2;
    public static final int DONE = 3;
    public static final int CANCELED = 4;
    public static final String[] statusKey = {"Start", "Nomination", "Final", "Done", "Canceled"};
    public static final int[] statusValue = {0, 1, 2, 3, 4};
    
    public PstPositionCandidate() {
    }

    public PstPositionCandidate(int i) throws DBException {
        super(new PstPositionCandidate());
    }

    public PstPositionCandidate(String sOid) throws DBException {
        super(new PstPositionCandidate(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionCandidate(long lOid) throws DBException {
        super(new PstPositionCandidate(0));
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
        return TBL_POSITION_CANDIDATE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionCandidate().getClass().getName();
    }

    public static PositionCandidate fetchExc(long oid) throws DBException {
        try {
            PositionCandidate entPositionCandidate = new PositionCandidate();
            PstPositionCandidate pstPositionCandidate = new PstPositionCandidate(oid);
            entPositionCandidate.setOID(oid);
            entPositionCandidate.setCandidateType(pstPositionCandidate.getInt(FLD_CANDIDATE_TYPE));
            entPositionCandidate.setPositionId(pstPositionCandidate.getLong(FLD_POSITION_ID));
            entPositionCandidate.setTitle(pstPositionCandidate.getString(FLD_TITLE));
            entPositionCandidate.setObjectives(pstPositionCandidate.getString(FLD_OBJECTIVES));
            entPositionCandidate.setNumCandidates(pstPositionCandidate.getInt(FLD_NUM_CANDIDATES));
            entPositionCandidate.setDueDate(pstPositionCandidate.getDate(FLD_DUE_DATE));
            entPositionCandidate.setRequestBy(pstPositionCandidate.getLong(FLD_REQUEST_BY));
            entPositionCandidate.setDocStatus(pstPositionCandidate.getInt(FLD_DOC_STATUS));
            entPositionCandidate.setCompany(pstPositionCandidate.getString(FLD_COMPANY));
            entPositionCandidate.setDivision(pstPositionCandidate.getString(FLD_DIVISION));
            entPositionCandidate.setDepartment(pstPositionCandidate.getString(FLD_DEPARTMENT));
            entPositionCandidate.setSection(pstPositionCandidate.getString(FLD_SECTION));
            entPositionCandidate.setSearchDate(pstPositionCandidate.getDate(FLD_SEARCH_DATE));
            return entPositionCandidate;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidate(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionCandidate entPositionCandidate = fetchExc(entity.getOID());
        entity = (Entity) entPositionCandidate;
        return entPositionCandidate.getOID();
    }

    public static synchronized long updateExc(PositionCandidate entPositionCandidate) throws DBException {
        try {
            if (entPositionCandidate.getOID() != 0) {
                PstPositionCandidate pstPositionCandidate = new PstPositionCandidate(entPositionCandidate.getOID());
                pstPositionCandidate.setInt(FLD_CANDIDATE_TYPE, entPositionCandidate.getCandidateType());
                pstPositionCandidate.setLong(FLD_POSITION_ID, entPositionCandidate.getPositionId());
                pstPositionCandidate.setString(FLD_TITLE, entPositionCandidate.getTitle());
                pstPositionCandidate.setString(FLD_OBJECTIVES, entPositionCandidate.getObjectives());
                pstPositionCandidate.setInt(FLD_NUM_CANDIDATES, entPositionCandidate.getNumCandidates());
                pstPositionCandidate.setDate(FLD_DUE_DATE, entPositionCandidate.getDueDate());
                pstPositionCandidate.setLong(FLD_REQUEST_BY, entPositionCandidate.getRequestBy());
                pstPositionCandidate.setInt(FLD_DOC_STATUS, entPositionCandidate.getDocStatus());
                pstPositionCandidate.setString(FLD_COMPANY, entPositionCandidate.getCompany());
                pstPositionCandidate.setString(FLD_DIVISION, entPositionCandidate.getDivision());
                pstPositionCandidate.setString(FLD_DEPARTMENT, entPositionCandidate.getDepartment());
                pstPositionCandidate.setString(FLD_SECTION, entPositionCandidate.getSection());
                pstPositionCandidate.setDate(FLD_SEARCH_DATE, entPositionCandidate.getSearchDate());
                pstPositionCandidate.update();
                return entPositionCandidate.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidate(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionCandidate) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionCandidate pstPositionCandidate = new PstPositionCandidate(oid);
            pstPositionCandidate.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidate(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionCandidate entPositionCandidate) throws DBException {
        try {
            PstPositionCandidate pstPositionCandidate = new PstPositionCandidate(0);
            pstPositionCandidate.setInt(FLD_CANDIDATE_TYPE, entPositionCandidate.getCandidateType());
            pstPositionCandidate.setLong(FLD_POSITION_ID, entPositionCandidate.getPositionId());
            pstPositionCandidate.setString(FLD_TITLE, entPositionCandidate.getTitle());
            pstPositionCandidate.setString(FLD_OBJECTIVES, entPositionCandidate.getObjectives());
            pstPositionCandidate.setInt(FLD_NUM_CANDIDATES, entPositionCandidate.getNumCandidates());
            pstPositionCandidate.setDate(FLD_DUE_DATE, entPositionCandidate.getDueDate());
            pstPositionCandidate.setLong(FLD_REQUEST_BY, entPositionCandidate.getRequestBy());
            pstPositionCandidate.setInt(FLD_DOC_STATUS, entPositionCandidate.getDocStatus());
            pstPositionCandidate.setString(FLD_COMPANY, entPositionCandidate.getCompany());
            pstPositionCandidate.setString(FLD_DIVISION, entPositionCandidate.getDivision());
            pstPositionCandidate.setString(FLD_DEPARTMENT, entPositionCandidate.getDepartment());
            pstPositionCandidate.setString(FLD_SECTION, entPositionCandidate.getSection());
            pstPositionCandidate.setDate(FLD_SEARCH_DATE, entPositionCandidate.getSearchDate());
            pstPositionCandidate.insert();
            entPositionCandidate.setOID(pstPositionCandidate.getLong(FLD_POS_CANDIDATE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionCandidate(0), DBException.UNKNOWN);
        }
        return entPositionCandidate.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionCandidate) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_CANDIDATE;
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
                PositionCandidate entPositionCandidate = new PositionCandidate();
                resultToObject(rs, entPositionCandidate);
                lists.add(entPositionCandidate);
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
    
    public static Vector listGetCompetency(String positionId){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT "+PstPositionCompetencyRequired.TBL_POSITION_COMPETENCY_REQUIRED+".";
            sql += PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_COMPETENCY_ID];
            sql += " FROM "+PstPosition.TBL_HR_POSITION;
            sql += " INNER JOIN "+PstPositionCompetencyRequired.TBL_POSITION_COMPETENCY_REQUIRED+" ON ";
            sql += PstPosition.TBL_HR_POSITION + "."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+"=";
            sql += PstPositionCompetencyRequired.TBL_POSITION_COMPETENCY_REQUIRED+".";
            sql += PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_POSITION_ID];
            sql += " WHERE "+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+"="+positionId+"";
 

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PositionCompetencyRequired posCompetencyReq = new PositionCompetencyRequired();
                posCompetencyReq.setCompetencyId(rs.getLong(PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_COMPETENCY_ID]));
                lists.add(posCompetencyReq);
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
    
    public static Vector listEmployeeRelation(String division, String competency){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT ";
            sql += PstEmployee.TBL_HR_EMPLOYEE +"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+", ";
            sql += PstEmployee.TBL_HR_EMPLOYEE +"."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+", ";
            sql += PstEmployee.TBL_HR_EMPLOYEE +"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+", ";
            sql += PstPosition.TBL_HR_POSITION +"."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+", ";
            sql += PstDivision.TBL_HR_DIVISION +"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+", ";
            sql += PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+", ";
            sql += PstSection.TBL_HR_SECTION+"."+PstSection.fieldNames[PstSection.FLD_SECTION]+", ";
//            sql += "SUM("+PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY+"."+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_SCORE_VALUE]+")AS SCORE ";             
            sql += "FROM "+PstEmployee.TBL_HR_EMPLOYEE+" ";
            sql += "INNER JOIN "+PstPosition.TBL_HR_POSITION+" ON ";
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"=";
            sql += PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" ";
            sql += "INNER JOIN "+PstDivision.TBL_HR_DIVISION+" ON ";
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"=";
            sql += PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" ";
            sql += "INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" ON ";
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"=";
            sql += PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" ";
            sql += "LEFT JOIN "+PstSection.TBL_HR_SECTION+" ON ";
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"=";
            sql += PstSection.TBL_HR_SECTION+"."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+" "; 
            sql += "LEFT JOIN "+PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY+" ON ";
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"=";
            sql += PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY+"."+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+" ";
            sql += "LEFT JOIN "+ PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" ON ";
            sql += PstTrainingHistory.TBL_HR_TRAINING_HISTORY+"."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+"=";
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" ";
            sql += "WHERE ( "+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" IN("+division+")) ";
            sql += "OR("+PstEmployeeCompetency.TBL_EMPLOYEE_COMPETENCY+"."+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_COMPETENCY_ID]+" ";
            sql += "IN ("+competency+")) ";
            sql += "GROUP BY "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" ";
            sql += "ORDER BY SCORE DESC LIMIT 0,20 ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
  
                PositionCandidateResultSet hasil = new PositionCandidateResultSet();
                hasil.setEmployeeID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                hasil.setEmpFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                hasil.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                hasil.setPositionName(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                hasil.setScore(rs.getFloat("SCORE"));

                lists.add(hasil);
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
    
    public static void resultToObject(ResultSet rs, PositionCandidate entPositionCandidate) {
        try {
            entPositionCandidate.setOID(rs.getLong(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_POS_CANDIDATE_ID]));
            entPositionCandidate.setCandidateType(rs.getInt(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_CANDIDATE_TYPE]));
            entPositionCandidate.setPositionId(rs.getLong(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_POSITION_ID]));
            entPositionCandidate.setTitle(rs.getString(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_TITLE]));
            entPositionCandidate.setObjectives(rs.getString(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_OBJECTIVES]));
            entPositionCandidate.setNumCandidates(rs.getInt(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_NUM_CANDIDATES]));
            entPositionCandidate.setDueDate(rs.getDate(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_DUE_DATE]));
            entPositionCandidate.setRequestBy(rs.getLong(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_REQUEST_BY]));
            entPositionCandidate.setDocStatus(rs.getInt(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_DOC_STATUS]));
            entPositionCandidate.setCompany(rs.getString(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_COMPANY]));
            entPositionCandidate.setDivision(rs.getString(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_DIVISION]));
            entPositionCandidate.setDepartment(rs.getString(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_DEPARTMENT]));
            entPositionCandidate.setSection(rs.getString(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_SECTION]));
            entPositionCandidate.setSearchDate(rs.getDate(PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_SEARCH_DATE]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long entPositionCandidateId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_POSITION_CANDIDATE + " WHERE "
                    + PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_POS_CANDIDATE_ID] + " = " + entPositionCandidateId;

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
            String sql = "SELECT COUNT(" + PstPositionCandidate.fieldNames[PstPositionCandidate.FLD_POS_CANDIDATE_ID] + ") FROM " + TBL_POSITION_CANDIDATE;
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
                    PositionCandidate entPositionCandidate = (PositionCandidate) list.get(ls);
                    if (oid == entPositionCandidate.getOID()) {
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