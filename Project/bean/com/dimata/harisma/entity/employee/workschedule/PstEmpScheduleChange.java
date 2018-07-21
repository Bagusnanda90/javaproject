/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee.workschedule;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstEmpScheduleChange  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_EMP_SCHEDULE_CHANGE = "hr_emp_schedule_change";
   public static final int FLD_EMP_SCHEDULE_CHANGE_ID = 0;
   public static final int FLD_DATE_OF_REQUEST_DATETIME = 1;
   public static final int FLD_STATUS_DOC = 2;
   public static final int FLD_TYPE_OF_FORM = 3;
   public static final int FLD_TYPE_OF_SCHEDULE = 4;
   public static final int FLD_APPLICANT_EMPLOYEE_ID = 5;
   public static final int FLD_EXCHANGE_EMPLOYEE_ID = 6;
   public static final int FLD_ORIGINAL_DATE = 7;
   public static final int FLD_ORIGINAL_SCHEDULE_ID = 8;
   public static final int FLD_NEW_CHANGE_DATE = 9;
   public static final int FLD_NEW_CHANGE_SCHEDULE_ID = 10;
   public static final int FLD_REASON = 11;
   public static final int FLD_REMARK = 12;
   public static final int FLD_APPROVAL_LEVEL1_ID = 13;
   public static final int FLD_APPROVAL_LEVEL2_ID = 14;
   public static final int FLD_APPROVAL_DATE_LEVEL1 = 15;
   public static final int FLD_APPROVAL_DATE_LEVEL2 = 16;
   public static final int FLD_APPROVAL_DATE_APPLICANT = 17;
   public static final int FLD_APPROVAL_DATE_EXCHANGE = 18;
   public static final int FLD_CHECKED_BY_ID = 19;
   public static final int FLD_CHECKED_DATE = 20;
   
   public static final int FLD_APPROVAL_LEVEL1_NOTE = 21;
   public static final int FLD_APPROVAL_LEVEL2_NOTE = 22;
   public static final int FLD_PERSONAL_TYPE = 23;
   public static final int FLD_REMARK2 = 24;
   
    public static final String[] fieldNames = {
      "EMP_SCHEDULE_CHANGE_ID",
      "DATETIME_OF_REQUEST",
      "STATUS_DOC",
      "TYPE_OF_FORM",
      "TYPE_OF_SCHEDULE",
      "APPLICANT_EMPLOYEE_ID",
      "EXCHANGE_EMPLOYEE_ID",
      "ORIGINAL_DATE",
      "ORIGINAL_SCHEDULE_ID",
      "NEW_CHANGE_DATE",
      "NEW_CHANGE_SCHEDULE_ID",
      "REASON",
      "REMARK",
      "APPROVER_LEVEL1_ID",
      "APPROVER_LEVEL2_ID",
      "APPROVAL_DATE_LEVEL1",
      "APPROVAL_DATE_LEVEL2",
      "APPROVAL_DATE_APPLICANT",
      "APPROVAL_DATE_EXCHANGE",
      "CHECKED_BY_ID",
      "CHECKED_DATE",
      "APPROVER_LEVEL1_NOTE",
      "APPROVER_LEVEL2_NOTE",
      "PERSONAL_TYPE",
      "REMARK2"
    };
    public static final int[] fieldTypes = {
      TYPE_LONG + TYPE_PK + TYPE_ID,
      TYPE_DATE,
      TYPE_INT,
      TYPE_INT,
      TYPE_INT,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_DATE,
      TYPE_LONG,
      TYPE_DATE,
      TYPE_LONG,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_DATE,
      TYPE_DATE,
      TYPE_DATE,
      TYPE_DATE,
      TYPE_LONG,
      TYPE_DATE,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_INT,
      TYPE_STRING
    };

    
   public static final int TYPE_SCHEDULE_CHANGE = 0;
   public static final int TYPE_EO_FORM = 1;
   public static final int TYPE_EH_FORM = 2;
    
   public PstEmpScheduleChange() {
   }

    public PstEmpScheduleChange(int i) throws DBException {
        super(new PstEmpScheduleChange());
    }

    public PstEmpScheduleChange(String sOid) throws DBException {
        super(new PstEmpScheduleChange(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpScheduleChange(long lOid) throws DBException {
        super(new PstEmpScheduleChange(0));
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
        return TBL_HR_EMP_SCHEDULE_CHANGE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpScheduleChange().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpScheduleChange empScheduleChange = fetchExc(ent.getOID());
        ent = (Entity) empScheduleChange;
        return empScheduleChange.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpScheduleChange) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpScheduleChange) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpScheduleChange fetchExc(long oid) throws DBException {
        try {
         
         EmpScheduleChange entEmpScheduleChange = new EmpScheduleChange();
         PstEmpScheduleChange pstEmpScheduleChange = new PstEmpScheduleChange(oid);
         entEmpScheduleChange.setOID(oid);
         entEmpScheduleChange.setDateOfRequestDatetime(pstEmpScheduleChange.getDate(FLD_DATE_OF_REQUEST_DATETIME));
         entEmpScheduleChange.setStatusDoc(pstEmpScheduleChange.getInt(FLD_STATUS_DOC));
         entEmpScheduleChange.setTypeOfForm(pstEmpScheduleChange.getInt(FLD_TYPE_OF_FORM));
         entEmpScheduleChange.setTypeOfSchedule(pstEmpScheduleChange.getInt(FLD_TYPE_OF_SCHEDULE));
         entEmpScheduleChange.setApplicantEmployeeId(pstEmpScheduleChange.getLong(FLD_APPLICANT_EMPLOYEE_ID));
         entEmpScheduleChange.setExchangeEmployeeId(pstEmpScheduleChange.getLong(FLD_EXCHANGE_EMPLOYEE_ID));
         entEmpScheduleChange.setOriginalDate(pstEmpScheduleChange.getDate(FLD_ORIGINAL_DATE));
         entEmpScheduleChange.setOriginalScheduleId(pstEmpScheduleChange.getLong(FLD_ORIGINAL_SCHEDULE_ID));
         entEmpScheduleChange.setNewChangeDate(pstEmpScheduleChange.getDate(FLD_NEW_CHANGE_DATE));
         entEmpScheduleChange.setNewChangeScheduleId(pstEmpScheduleChange.getLong(FLD_NEW_CHANGE_SCHEDULE_ID));
         entEmpScheduleChange.setReason(pstEmpScheduleChange.getString(FLD_REASON));
         entEmpScheduleChange.setRemark(pstEmpScheduleChange.getString(FLD_REMARK));
         entEmpScheduleChange.setApprovalLevel1Id(pstEmpScheduleChange.getLong(FLD_APPROVAL_LEVEL1_ID));
         entEmpScheduleChange.setApprovalLevel2Id(pstEmpScheduleChange.getLong(FLD_APPROVAL_LEVEL2_ID));
         entEmpScheduleChange.setApprovalDateLevel1(pstEmpScheduleChange.getDate(FLD_APPROVAL_DATE_LEVEL1));
         entEmpScheduleChange.setApprovalDateLevel2(pstEmpScheduleChange.getDate(FLD_APPROVAL_DATE_LEVEL2));
         entEmpScheduleChange.setApprovalDateApplicant(pstEmpScheduleChange.getDate(FLD_APPROVAL_DATE_APPLICANT));
         entEmpScheduleChange.setApprovalDateExchange(pstEmpScheduleChange.getDate(FLD_APPROVAL_DATE_EXCHANGE));
         entEmpScheduleChange.setCheckedById(pstEmpScheduleChange.getLong(FLD_CHECKED_BY_ID));
         entEmpScheduleChange.setCheckedDate(pstEmpScheduleChange.getDate(FLD_CHECKED_DATE));
         entEmpScheduleChange.setApprovalLevel1Note(pstEmpScheduleChange.getString(FLD_APPROVAL_LEVEL1_NOTE));
         entEmpScheduleChange.setApprovalLevel2Note(pstEmpScheduleChange.getString(FLD_APPROVAL_LEVEL2_NOTE));
         entEmpScheduleChange.setPersonalType(pstEmpScheduleChange.getInt(FLD_PERSONAL_TYPE));
         entEmpScheduleChange.setRemark2(pstEmpScheduleChange.getString(FLD_REMARK2));
         return entEmpScheduleChange;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleChange(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpScheduleChange entEmpScheduleChange) throws DBException {
        try {
               PstEmpScheduleChange pstEmpScheduleChange = new PstEmpScheduleChange(0);
            pstEmpScheduleChange.setDate(FLD_DATE_OF_REQUEST_DATETIME, entEmpScheduleChange.getDateOfRequestDatetime());
            pstEmpScheduleChange.setInt(FLD_STATUS_DOC, entEmpScheduleChange.getStatusDoc());
            pstEmpScheduleChange.setInt(FLD_TYPE_OF_FORM, entEmpScheduleChange.getTypeOfForm());
            pstEmpScheduleChange.setInt(FLD_TYPE_OF_SCHEDULE, entEmpScheduleChange.getTypeOfSchedule());
            pstEmpScheduleChange.setLong(FLD_APPLICANT_EMPLOYEE_ID, entEmpScheduleChange.getApplicantEmployeeId());
            pstEmpScheduleChange.setLong(FLD_EXCHANGE_EMPLOYEE_ID, entEmpScheduleChange.getExchangeEmployeeId());
            pstEmpScheduleChange.setDate(FLD_ORIGINAL_DATE, entEmpScheduleChange.getOriginalDate());
            pstEmpScheduleChange.setLong(FLD_ORIGINAL_SCHEDULE_ID, entEmpScheduleChange.getOriginalScheduleId());
            pstEmpScheduleChange.setDate(FLD_NEW_CHANGE_DATE, entEmpScheduleChange.getNewChangeDate());
            pstEmpScheduleChange.setLong(FLD_NEW_CHANGE_SCHEDULE_ID, entEmpScheduleChange.getNewChangeScheduleId());
            pstEmpScheduleChange.setString(FLD_REASON, entEmpScheduleChange.getReason());
            pstEmpScheduleChange.setString(FLD_REMARK, entEmpScheduleChange.getRemark());
            pstEmpScheduleChange.setLong(FLD_APPROVAL_LEVEL1_ID, entEmpScheduleChange.getApprovalLevel1Id());
            pstEmpScheduleChange.setLong(FLD_APPROVAL_LEVEL2_ID, entEmpScheduleChange.getApprovalLevel2Id());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_LEVEL1, entEmpScheduleChange.getApprovalDateLevel1());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_LEVEL2, entEmpScheduleChange.getApprovalDateLevel2());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_APPLICANT, entEmpScheduleChange.getApprovalDateApplicant());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_EXCHANGE, entEmpScheduleChange.getApprovalDateExchange());
            pstEmpScheduleChange.setLong(FLD_CHECKED_BY_ID, entEmpScheduleChange.getCheckedById());
            pstEmpScheduleChange.setDate(FLD_CHECKED_DATE, entEmpScheduleChange.getCheckedDate());
            pstEmpScheduleChange.setString(FLD_APPROVAL_LEVEL1_NOTE, entEmpScheduleChange.getApprovalLevel1Note());
            pstEmpScheduleChange.setString(FLD_APPROVAL_LEVEL2_NOTE, entEmpScheduleChange.getApprovalLevel2Note());
            pstEmpScheduleChange.setInt(FLD_PERSONAL_TYPE, entEmpScheduleChange.getPersonalType());
            pstEmpScheduleChange.setString(FLD_REMARK2, entEmpScheduleChange.getRemark2());
            pstEmpScheduleChange.insert();
            entEmpScheduleChange.setOID(pstEmpScheduleChange.getlong(FLD_EMP_SCHEDULE_CHANGE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleChange(0), DBException.UNKNOWN);
        }
        return entEmpScheduleChange.getOID();
    }

    public static long updateExc(EmpScheduleChange entEmpScheduleChange) throws DBException {
        try {
            if (entEmpScheduleChange.getOID() != 0) {
                PstEmpScheduleChange pstEmpScheduleChange = new PstEmpScheduleChange(entEmpScheduleChange.getOID());

            pstEmpScheduleChange.setDate(FLD_DATE_OF_REQUEST_DATETIME, entEmpScheduleChange.getDateOfRequestDatetime());
            pstEmpScheduleChange.setInt(FLD_STATUS_DOC, entEmpScheduleChange.getStatusDoc());
            pstEmpScheduleChange.setInt(FLD_TYPE_OF_FORM, entEmpScheduleChange.getTypeOfForm());
            pstEmpScheduleChange.setInt(FLD_TYPE_OF_SCHEDULE, entEmpScheduleChange.getTypeOfSchedule());
            pstEmpScheduleChange.setLong(FLD_APPLICANT_EMPLOYEE_ID, entEmpScheduleChange.getApplicantEmployeeId());
            pstEmpScheduleChange.setLong(FLD_EXCHANGE_EMPLOYEE_ID, entEmpScheduleChange.getExchangeEmployeeId());
            pstEmpScheduleChange.setDate(FLD_ORIGINAL_DATE, entEmpScheduleChange.getOriginalDate());
            pstEmpScheduleChange.setLong(FLD_ORIGINAL_SCHEDULE_ID, entEmpScheduleChange.getOriginalScheduleId());
            pstEmpScheduleChange.setDate(FLD_NEW_CHANGE_DATE, entEmpScheduleChange.getNewChangeDate());
            pstEmpScheduleChange.setLong(FLD_NEW_CHANGE_SCHEDULE_ID, entEmpScheduleChange.getNewChangeScheduleId());
            pstEmpScheduleChange.setString(FLD_REASON, entEmpScheduleChange.getReason());
            pstEmpScheduleChange.setString(FLD_REMARK, entEmpScheduleChange.getRemark());
            pstEmpScheduleChange.setLong(FLD_APPROVAL_LEVEL1_ID, entEmpScheduleChange.getApprovalLevel1Id());
            pstEmpScheduleChange.setLong(FLD_APPROVAL_LEVEL2_ID, entEmpScheduleChange.getApprovalLevel2Id());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_LEVEL1, entEmpScheduleChange.getApprovalDateLevel1());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_LEVEL2, entEmpScheduleChange.getApprovalDateLevel2());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_APPLICANT, entEmpScheduleChange.getApprovalDateApplicant());
            pstEmpScheduleChange.setDate(FLD_APPROVAL_DATE_EXCHANGE, entEmpScheduleChange.getApprovalDateExchange());
            pstEmpScheduleChange.setLong(FLD_CHECKED_BY_ID, entEmpScheduleChange.getCheckedById());
            pstEmpScheduleChange.setDate(FLD_CHECKED_DATE, entEmpScheduleChange.getCheckedDate());
            pstEmpScheduleChange.setLong(FLD_CHECKED_BY_ID, entEmpScheduleChange.getCheckedById());
            pstEmpScheduleChange.setDate(FLD_CHECKED_DATE, entEmpScheduleChange.getCheckedDate());
            pstEmpScheduleChange.setString(FLD_APPROVAL_LEVEL1_NOTE, entEmpScheduleChange.getApprovalLevel1Note());
            pstEmpScheduleChange.setString(FLD_APPROVAL_LEVEL2_NOTE, entEmpScheduleChange.getApprovalLevel2Note());
            pstEmpScheduleChange.setInt(FLD_PERSONAL_TYPE, entEmpScheduleChange.getPersonalType());
            pstEmpScheduleChange.setString(FLD_REMARK2, entEmpScheduleChange.getRemark2());
            
            pstEmpScheduleChange.update();
            return entEmpScheduleChange.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleChange(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpScheduleChange pstEmpScheduleChange = new PstEmpScheduleChange(oid);
            pstEmpScheduleChange.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleChange(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE_CHANGE;
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
                EmpScheduleChange empScheduleChange = new EmpScheduleChange();
                resultToObject(rs, empScheduleChange);
                lists.add(empScheduleChange);
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
    
      public static void resultToObject(ResultSet rs, EmpScheduleChange entEmpScheduleChange) {
        try {
            entEmpScheduleChange.setOID(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_EMP_SCHEDULE_CHANGE_ID]));
            entEmpScheduleChange.setDateOfRequestDatetime(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_DATE_OF_REQUEST_DATETIME]));
            entEmpScheduleChange.setStatusDoc(rs.getInt(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_STATUS_DOC]));
            entEmpScheduleChange.setTypeOfForm(rs.getInt(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_TYPE_OF_FORM]));
            entEmpScheduleChange.setTypeOfSchedule(rs.getInt(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_TYPE_OF_SCHEDULE]));
            entEmpScheduleChange.setApplicantEmployeeId(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPLICANT_EMPLOYEE_ID]));
            entEmpScheduleChange.setExchangeEmployeeId(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_EXCHANGE_EMPLOYEE_ID]));
            entEmpScheduleChange.setOriginalDate(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_ORIGINAL_DATE]));
            entEmpScheduleChange.setOriginalScheduleId(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_ORIGINAL_SCHEDULE_ID]));
            entEmpScheduleChange.setNewChangeDate(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_NEW_CHANGE_DATE]));
            entEmpScheduleChange.setNewChangeScheduleId(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_NEW_CHANGE_SCHEDULE_ID]));
            entEmpScheduleChange.setReason(rs.getString(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_REASON]));
            entEmpScheduleChange.setRemark(rs.getString(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_REMARK]));
            entEmpScheduleChange.setApprovalLevel1Id(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_LEVEL1_ID]));
            entEmpScheduleChange.setApprovalLevel2Id(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_LEVEL2_ID]));
            entEmpScheduleChange.setApprovalDateLevel1(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_DATE_LEVEL1]));
            entEmpScheduleChange.setApprovalDateLevel2(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_DATE_LEVEL2]));
            entEmpScheduleChange.setApprovalDateApplicant(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_DATE_APPLICANT]));
            entEmpScheduleChange.setApprovalDateExchange(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_DATE_EXCHANGE]));
            entEmpScheduleChange.setCheckedById(rs.getLong(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_CHECKED_BY_ID]));
            entEmpScheduleChange.setCheckedDate(rs.getDate(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_CHECKED_DATE]));
            entEmpScheduleChange.setApprovalLevel1Note(rs.getString(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_LEVEL1_NOTE]));
            entEmpScheduleChange.setApprovalLevel2Note(rs.getString(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_APPROVAL_LEVEL2_NOTE]));
            entEmpScheduleChange.setPersonalType(rs.getInt(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_PERSONAL_TYPE]));
            entEmpScheduleChange.setRemark2(rs.getString(PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_REMARK2]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long empScheduleChangeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE_CHANGE + " WHERE "
                    + PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_EMP_SCHEDULE_CHANGE_ID] + " = " + empScheduleChangeId;

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
            String sql = "SELECT COUNT(" + PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_EMP_SCHEDULE_CHANGE_ID] + ") FROM " + TBL_HR_EMP_SCHEDULE_CHANGE;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    EmpScheduleChange empScheduleChange = (EmpScheduleChange) list.get(ls);
                    if (oid == empScheduleChange.getOID()) {
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

  
}
