
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.entity.employee;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;

//Gede_7Feb2012 {
import com.dimata.harisma.session.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.masterdata.*;
//}

public class PstEmpEducation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_EMP_EDUCATION = "hr_emp_education";//"HR_EMP_EDUCATION";
    public static final int FLD_EMP_EDUCATION_ID = 0;
    public static final int FLD_EDUCATION_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_START_DATE = 3;
    public static final int FLD_END_DATE = 4;
    public static final int FLD_GRADUATION = 5;
    public static final int FLD_EDUCATION_DESC = 6;
    public static final int FLD_POINT = 7;
    public static final int FLD_INSTITUTION_ID = 8;
    public static final String[] fieldNames = {
        "EMP_EDUCATION_ID",
        "EDUCATION_ID",
        "EMPLOYEE_ID",
        "START_DATE",
        "END_DATE",
        "GRADUATION",
        "EDUCATION_DESC",
        "POINT",
        "INSTITUTION_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_LONG
    };

    public PstEmpEducation() {
    }

    public PstEmpEducation(int i) throws DBException {
        super(new PstEmpEducation());
    }

    public PstEmpEducation(String sOid) throws DBException {
        super(new PstEmpEducation(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpEducation(long lOid) throws DBException {
        super(new PstEmpEducation(0));
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
        return TBL_HR_EMP_EDUCATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpEducation().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpEducation empeducation = fetchExc(ent.getOID());
        ent = (Entity) empeducation;
        return empeducation.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpEducation) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpEducation) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpEducation fetchExc(long oid) throws DBException {
        try {
            EmpEducation empeducation = new EmpEducation();
            PstEmpEducation pstEmpEducation = new PstEmpEducation(oid);
            empeducation.setOID(oid);

            empeducation.setEducationId(pstEmpEducation.getlong(FLD_EDUCATION_ID));
            empeducation.setEmployeeId(pstEmpEducation.getlong(FLD_EMPLOYEE_ID));
            empeducation.setStartDate(pstEmpEducation.getInt(FLD_START_DATE));
            empeducation.setEndDate(pstEmpEducation.getInt(FLD_END_DATE));
            empeducation.setGraduation(pstEmpEducation.getString(FLD_GRADUATION));
            empeducation.setEducationDesc(pstEmpEducation.getString(FLD_EDUCATION_DESC));
            empeducation.setPoint(pstEmpEducation.getfloat(FLD_POINT));
            empeducation.setInstitutionId(pstEmpEducation.getLong(FLD_INSTITUTION_ID));

            return empeducation;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpEducation(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpEducation empeducation) throws DBException {
        try {
            PstEmpEducation pstEmpEducation = new PstEmpEducation(0);

            pstEmpEducation.setLong(FLD_EDUCATION_ID, empeducation.getEducationId());
            pstEmpEducation.setLong(FLD_EMPLOYEE_ID, empeducation.getEmployeeId());
            pstEmpEducation.setInt(FLD_START_DATE, empeducation.getStartDate());
            pstEmpEducation.setInt(FLD_END_DATE, empeducation.getEndDate());
            pstEmpEducation.setString(FLD_GRADUATION, empeducation.getGraduation());
            pstEmpEducation.setString(FLD_EDUCATION_DESC, empeducation.getEducationDesc());
            pstEmpEducation.setFloat(FLD_POINT, empeducation.getPoint());
            pstEmpEducation.setLong(FLD_INSTITUTION_ID, empeducation.getInstitutionId());

            pstEmpEducation.insert();
            empeducation.setOID(pstEmpEducation.getlong(FLD_EMP_EDUCATION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpEducation(0), DBException.UNKNOWN);
        }
        return empeducation.getOID();
    }

    public static long updateExc(EmpEducation empeducation) throws DBException {
        try {
            if (empeducation.getOID() != 0) {
                PstEmpEducation pstEmpEducation = new PstEmpEducation(empeducation.getOID());

                pstEmpEducation.setLong(FLD_EDUCATION_ID, empeducation.getEducationId());
                pstEmpEducation.setLong(FLD_EMPLOYEE_ID, empeducation.getEmployeeId());
                pstEmpEducation.setInt(FLD_START_DATE, empeducation.getStartDate());
                pstEmpEducation.setInt(FLD_END_DATE, empeducation.getEndDate());
                pstEmpEducation.setString(FLD_GRADUATION, empeducation.getGraduation());
                pstEmpEducation.setString(FLD_EDUCATION_DESC, empeducation.getEducationDesc());
                pstEmpEducation.setFloat(FLD_POINT, empeducation.getPoint());
                pstEmpEducation.setLong(FLD_INSTITUTION_ID, empeducation.getInstitutionId());

                pstEmpEducation.update();
                return empeducation.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpEducation(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpEducation pstEmpEducation = new PstEmpEducation(oid);
            pstEmpEducation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpEducation(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_EDUCATION;
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

            System.out.println("Emp education " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                EmpEducation empeducation = new EmpEducation();
                resultToObject(rs, empeducation);
                lists.add(empeducation);
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

    /**
     * create by satrya 2013-07-31
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector listEducation(String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT  EMPEDU."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]
            +",EMPEDU."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]
            +",EMPEDU."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]        
            +",EMPEDU."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]
            +",EDU."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION]
            +" FROM "+PstEducation.TBL_HR_EDUCATION + "  AS EDU "
    + " INNER JOIN "+PstEmpEducation.TBL_HR_EMP_EDUCATION
    + " AS EMPEDU ON EMPEDU."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]
    +"=EDU."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID];
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SessEmpEducation sessEmpEducation = new SessEmpEducation();
                sessEmpEducation.setEmployee_id(rs.getLong(PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]));
                sessEmpEducation.setEducation(rs.getString(PstEducation.fieldNames[PstEducation.FLD_EDUCATION]));
                sessEmpEducation.setStartDate(rs.getInt(PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]));
                sessEmpEducation.setEndDate(rs.getInt(PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]));
                sessEmpEducation.setGraduation(rs.getString(PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]));
                lists.add(sessEmpEducation);
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
    private static void resultToObject(ResultSet rs, EmpEducation empeducation) {
        try {
            empeducation.setOID(rs.getLong(PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID]));
            empeducation.setEducationId(rs.getLong(PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]));
            empeducation.setEmployeeId(rs.getLong(PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]));
            empeducation.setStartDate(rs.getInt(PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]));
            empeducation.setEndDate(rs.getInt(PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]));
            empeducation.setGraduation(rs.getString(PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]));
            empeducation.setEducationDesc(rs.getString(PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_DESC]));
            empeducation.setPoint(rs.getFloat(fieldNames[FLD_POINT]));
            empeducation.setInstitutionId(rs.getLong(PstEmpEducation.fieldNames[PstEmpEducation.FLD_INSTITUTION_ID]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long empEducationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_EDUCATION + " WHERE "
                    + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID] + " = " + empEducationId;

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
            String sql = "SELECT COUNT(" + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID] + ") FROM " + TBL_HR_EMP_EDUCATION;
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
                    EmpEducation empeducation = (EmpEducation) list.get(ls);
                    if (oid == empeducation.getOID()) {
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

    public static boolean checkEducation(long educationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_EDUCATION + " WHERE "
                    + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID] + " = '" + educationId + "'";

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

    //Gede_7Feb2012 {
    //untuk report excel
    public static int getCount2(SrcEmployee srcEmployee) {
        DBResultSet dbrs = null;
        SessEmployee sessEmployee = new SessEmployee();

        try {
            String sql = "SELECT COUNT(EE." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID] + ") FROM " + PstEmpEducation.TBL_HR_EMP_EDUCATION + " EE INNER JOIN "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON EE." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]) + " WHERE " + sessEmployee.whereList(srcEmployee) + "GROUP BY EE." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]
                    + " ORDER BY COUNT(EE." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID] + ") DESC LIMIT 1";

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
    //}
    //Gede_14Feb2012 {
    //relationship

    public static String getEducation(String edu) {
        String education = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstEducation.fieldNames[PstEducation.FLD_EDUCATION]
                    + " FROM " + PstEducation.TBL_HR_EDUCATION + " WHERE " + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]
                    + "=" + edu;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql1:"+sql);
            while (rs.next()) {
                education = rs.getString(1);
            }

            rs.close();
            //return relation;
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return education;
    }
    //}
}
