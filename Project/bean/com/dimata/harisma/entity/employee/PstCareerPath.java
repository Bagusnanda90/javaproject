
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] ///
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

//Gede_7Feb2012 {
import com.dimata.harisma.session.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.masterdata.*;
//}

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;
import static com.dimata.harisma.entity.employee.PstEmployee.resultToObjectKadiv;
import com.dimata.harisma.entity.log.ChangeValue;
import com.dimata.harisma.entity.masterdata.location.Location;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import static com.dimata.qdep.db.I_DBType.TYPE_DATE;
import static com.dimata.qdep.db.I_DBType.TYPE_FK;
import static com.dimata.qdep.db.I_DBType.TYPE_FLOAT;
import static com.dimata.qdep.db.I_DBType.TYPE_ID;
import static com.dimata.qdep.db.I_DBType.TYPE_INT;
import static com.dimata.qdep.db.I_DBType.TYPE_LONG;
import static com.dimata.qdep.db.I_DBType.TYPE_PK;
import static com.dimata.qdep.db.I_DBType.TYPE_STRING;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.lang.I_Dictionary;
import java.text.SimpleDateFormat;

/**
 * Ari_20111002
 * Menambah Company, Division, Level dan EmpCategory
 * @author Wiweka
 */
public class PstCareerPath extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_WORK_HISTORY_NOW = "hr_work_history_now";//"HR_WORK_HISTORY_NOW";
    public static final int FLD_WORK_HISTORY_NOW_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_COMPANY = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_DEPARTMENT = 5;
    public static final int FLD_POSITION_ID = 6;
    public static final int FLD_POSITION = 7;
    public static final int FLD_SECTION_ID = 8;
    public static final int FLD_SECTION = 9;
    public static final int FLD_DIVISION_ID = 10;
    public static final int FLD_DIVISION = 11;
    public static final int FLD_LEVEL_ID = 12;
    public static final int FLD_LEVEL = 13;
    public static final int FLD_EMP_CATEGORY_ID = 14;
    public static final int FLD_EMP_CATEGORY = 15;
    public static final int FLD_WORK_FROM = 16;
    public static final int FLD_WORK_TO = 17;
    public static final int FLD_DESCRIPTION = 18;
    public static final int FLD_SALARY = 19;
    //priska 2014-11-03
    public static final int FLD_LOCATION_ID = 20;
    public static final int FLD_LOCATION = 21;
    public static final int FLD_NOTE = 22;
        //kartika 2015-09-16
    public static final int FLD_PROVIDER_ID = 23;  // untuk karyawan yg outsource
    /* Update by Hendra Putu | 2015-10-09 */
    public static final int FLD_HISTORY_TYPE = 24;
    public static final int FLD_NOMOR_SK = 25;
    public static final int FLD_TANGGAL_SK = 26;
    public static final int FLD_EMP_DOC_ID = 27;
    public static final int FLD_HISTORY_GROUP = 28;
    /* Update by Hendra Putu | 2015-11-25 | GRADE_LEVEL_ID */
    public static final int FLD_GRADE_LEVEL_ID = 29;
    public static final int FLD_CONTRACT_FROM = 30;
    public static final int FLD_CONTRACT_TO = 31;
    public static final int FLD_CONTRACT_TYPE = 32;
    public static final int FLD_MUTATION_TYPE = 33;
    public static final int FLD_DOCUMENT = 34;
    public static final int FLD_ACKNOWLEDGE_STATUS = 35;
    public static final String[] fieldNames = {
        "WORK_HISTORY_NOW_ID",
        "EMPLOYEE_ID",
        "COMPANY_ID",
        "COMPANY",
        "DEPARTMENT_ID",
        "DEPARTMENT",
        "POSITION_ID",
        "POSITION",
        "SECTION_ID",
        "SECTION",
        "DIVISION_ID",
        "DIVISION",
        "LEVEL_ID",
        "LEVEL",
        "EMP_CATEGORY_ID",
        "EMP_CATEGORY",
        "WORK_FROM",
        "WORK_TO",
        "DESCRIPTION",
        "SALARY",
        //2014-11-03
        "LOCATION_ID",
        "LOCATION",
        "NOTE",
        "PROVIDER_ID", // kartika 2015-09-16
        "HISTORY_TYPE",
        "NOMOR_SK",
        "TANGGAL_SK",
        "EMP_DOC_ID",
        "HISTORY_GROUP",
        "GRADE_LEVEL_ID",
        "CONTRACT_FROM",
        "CONTRACT_TO",
        "CONTRACT_TYPE",
        "MUTATION_TYPE",
        "DOCUMENT",
        "ACKNOWLEDGE_STATUS"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_FLOAT,
        //priska 2014-11-03
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG + TYPE_FK,
        TYPE_INT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_LONG + TYPE_FK,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT
    };
    
    public static int CAREER_TYPE = 0;
    public static int PEJABAT_SEMENTARA_TYPE = 1;
    public static int PELAKSANA_TUGAS_TYPE = 2;
    public static int DETASIR_TYPE = 3;
    public static String[] historyType = {"Career", "Pejabat Sementara", "Pelaksana Tugas", "Detasir"};
    
    public static int RIWAYAT_JABATAN = 0;
    public static int RIWAYAT_GRADE = 1;
    public static int RIWAYAT_CAREER_N_GRADE = 2;
    public static String[] historyGroup = {"Riwayat Jabatan", "Riwayat Grade", "Riwayat Jabatan dan Grade"};

    
    public static int DEMOTION_MUTATION = 0;
    public static int PROMOTION_MUTATION = 1;
    public static int ROTATION_MUTATION = 2;
    public static String [] mutationTypeValue = {"Demotion", "Promotion", "Rotation"};
    
    public static int DO_CAREER = 0;
    public static int DO_CONTRACT = 1;
    public static int DO_MUTATION = 2;
    public static int DO_RESIGN = 3;
    public static String[] historyTypeForQT = {"Career", "Contract", "Mutation", "Resign"};
    
    public PstCareerPath() {
    }

    public PstCareerPath(int i) throws DBException {
        super(new PstCareerPath());
    }

    public PstCareerPath(String sOid) throws DBException {
        super(new PstCareerPath(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCareerPath(long lOid) throws DBException {
        super(new PstCareerPath(0));
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
        return TBL_HR_WORK_HISTORY_NOW;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCareerPath().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        CareerPath careerpath = fetchExc(ent.getOID());
        ent = (Entity) careerpath;
        return careerpath.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((CareerPath) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((CareerPath) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static CareerPath fetchExc(long oid) throws DBException {
        try {
            CareerPath careerpath = new CareerPath();
            PstCareerPath pstCareerPath = new PstCareerPath(oid);
            careerpath.setOID(oid);

            careerpath.setEmployeeId(pstCareerPath.getlong(FLD_EMPLOYEE_ID));
            careerpath.setCompanyId(pstCareerPath.getlong(FLD_COMPANY_ID));
            careerpath.setCompany(pstCareerPath.getString(FLD_COMPANY));
            careerpath.setDepartmentId(pstCareerPath.getlong(FLD_DEPARTMENT_ID));
            careerpath.setDepartment(pstCareerPath.getString(FLD_DEPARTMENT));
            careerpath.setPositionId(pstCareerPath.getlong(FLD_POSITION_ID));
            careerpath.setPosition(pstCareerPath.getString(FLD_POSITION));
            careerpath.setSectionId(pstCareerPath.getlong(FLD_SECTION_ID));
            careerpath.setSection(pstCareerPath.getString(FLD_SECTION));
            careerpath.setDivisionId(pstCareerPath.getlong(FLD_DIVISION_ID));
            careerpath.setDivision(pstCareerPath.getString(FLD_DIVISION));
            careerpath.setLevelId(pstCareerPath.getlong(FLD_LEVEL_ID));
            careerpath.setLevel(pstCareerPath.getString(FLD_LEVEL));
            careerpath.setEmpCategoryId(pstCareerPath.getlong(FLD_EMP_CATEGORY_ID));
            careerpath.setEmpCategory(pstCareerPath.getString(FLD_EMP_CATEGORY));
            careerpath.setWorkFrom(pstCareerPath.getDate(FLD_WORK_FROM));
            careerpath.setWorkTo(pstCareerPath.getDate(FLD_WORK_TO));
            careerpath.setDescription(pstCareerPath.getString(FLD_DESCRIPTION));
            careerpath.setSalary(pstCareerPath.getdouble(FLD_SALARY));
            careerpath.setLocationId(pstCareerPath.getlong(FLD_LOCATION_ID));
            careerpath.setLocation(pstCareerPath.getString(FLD_LOCATION));
            careerpath.setNote(pstCareerPath.getString(FLD_NOTE));
            careerpath.setProviderID(pstCareerPath.getlong(FLD_PROVIDER_ID));
            careerpath.setHistoryType(pstCareerPath.getInt(FLD_HISTORY_TYPE));
            careerpath.setNomorSk(pstCareerPath.getString(FLD_NOMOR_SK));
            careerpath.setTanggalSk(pstCareerPath.getDate(FLD_TANGGAL_SK));
            careerpath.setEmpDocId(pstCareerPath.getLong(FLD_EMP_DOC_ID));
            careerpath.setHistoryGroup(pstCareerPath.getInt(FLD_HISTORY_GROUP));
            careerpath.setGradeLevelId(pstCareerPath.getLong(FLD_GRADE_LEVEL_ID));
            careerpath.setContractFrom(pstCareerPath.getDate(FLD_CONTRACT_FROM));
            careerpath.setContractTo(pstCareerPath.getDate(FLD_CONTRACT_TO));
            careerpath.setContractType(pstCareerPath.getInt(FLD_CONTRACT_TYPE));
            careerpath.setMutationType(pstCareerPath.getInt(FLD_MUTATION_TYPE));
            careerpath.setDocument(pstCareerPath.getString(FLD_DOCUMENT));
            careerpath.setAcknowledgeStatus(pstCareerPath.getInt(FLD_ACKNOWLEDGE_STATUS));

            return careerpath;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCareerPath(0), DBException.UNKNOWN);
        }
    }
    public static Vector dateCareerPath(long oidEmployee){
        Vector result =new Vector();
        DBResultSet dbrs=null;
        try{
            String sql=" SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE " + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = "  + oidEmployee;
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                CareerPath careerPath = new CareerPath();
                careerPath.setWorkFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
                careerPath.setWorkTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));
                careerPath.setOID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
                result.add(careerPath);
            }
        }catch(Exception exc){

        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }

    
        public static Vector dateCareerPathwithlocation(long oidEmployee,long location){
        Vector result =new Vector();
        DBResultSet dbrs=null;
        try{
            String sql=" SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE " + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = "  + oidEmployee;
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                CareerPath careerPath = new CareerPath();
                careerPath.setLocationId(location);
                careerPath.setWorkFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
                careerPath.setWorkTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));
                careerPath.setOID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
                result.add(careerPath);
            }
        }catch(Exception exc){

        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static long insertExc(CareerPath careerpath) throws DBException {
        try {
            PstCareerPath pstCareerPath = new PstCareerPath(0);

            pstCareerPath.setLong(FLD_EMPLOYEE_ID, careerpath.getEmployeeId());
            pstCareerPath.setLong(FLD_COMPANY_ID, careerpath.getCompanyId());
            pstCareerPath.setString(FLD_COMPANY, careerpath.getCompany());
            pstCareerPath.setLong(FLD_DEPARTMENT_ID, careerpath.getDepartmentId());
            pstCareerPath.setString(FLD_DEPARTMENT, careerpath.getDepartment());
            pstCareerPath.setLong(FLD_POSITION_ID, careerpath.getPositionId());
            pstCareerPath.setString(FLD_POSITION, careerpath.getPosition());
            pstCareerPath.setLong(FLD_SECTION_ID, careerpath.getSectionId());
            pstCareerPath.setString(FLD_SECTION, careerpath.getSection());
            pstCareerPath.setLong(FLD_DIVISION_ID, careerpath.getDivisionId());
            pstCareerPath.setString(FLD_DIVISION, careerpath.getDivision());
            pstCareerPath.setLong(FLD_LEVEL_ID, careerpath.getLevelId());
            pstCareerPath.setString(FLD_LEVEL, careerpath.getLevel());
            pstCareerPath.setLong(FLD_EMP_CATEGORY_ID, careerpath.getEmpCategoryId());
            pstCareerPath.setString(FLD_EMP_CATEGORY, careerpath.getEmpCategory());
            pstCareerPath.setDate(FLD_WORK_FROM, careerpath.getWorkFrom());
            pstCareerPath.setDate(FLD_WORK_TO, careerpath.getWorkTo());
            pstCareerPath.setString(FLD_DESCRIPTION, careerpath.getDescription());
            pstCareerPath.setDouble(FLD_SALARY, careerpath.getSalary());
            pstCareerPath.setLong(FLD_LOCATION_ID, careerpath.getLocationId());
            pstCareerPath.setLong(FLD_PROVIDER_ID, careerpath.getProviderID());
            pstCareerPath.setString(FLD_LOCATION, careerpath.getLocation());
            pstCareerPath.setString(FLD_NOTE, careerpath.getNote());
            pstCareerPath.setInt(FLD_HISTORY_TYPE, careerpath.getHistoryType());
            pstCareerPath.setString(FLD_NOMOR_SK, careerpath.getNomorSk());
            pstCareerPath.setDate(FLD_TANGGAL_SK, careerpath.getTanggalSk());
            pstCareerPath.setLong(FLD_EMP_DOC_ID, careerpath.getEmpDocId());
            pstCareerPath.setInt(FLD_HISTORY_GROUP, careerpath.getHistoryGroup());
            pstCareerPath.setLong(FLD_GRADE_LEVEL_ID, careerpath.getGradeLevelId());
            pstCareerPath.setDate(FLD_CONTRACT_FROM, careerpath.getContractFrom());
            pstCareerPath.setDate(FLD_CONTRACT_TO, careerpath.getContractTo());
            pstCareerPath.setInt(FLD_CONTRACT_TYPE, careerpath.getContractType());
            pstCareerPath.setInt(FLD_MUTATION_TYPE, careerpath.getMutationType());
            pstCareerPath.setString(FLD_DOCUMENT, careerpath.getDocument());
            pstCareerPath.setInt(FLD_ACKNOWLEDGE_STATUS, careerpath.getAcknowledgeStatus());
            
            pstCareerPath.insert();
            careerpath.setOID(pstCareerPath.getlong(FLD_WORK_HISTORY_NOW_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCareerPath(0), DBException.UNKNOWN);
        }
        return careerpath.getOID();
    }

    public static long updateExc(CareerPath careerpath) throws DBException {
        try {
            if (careerpath.getOID() != 0) {
                PstCareerPath pstCareerPath = new PstCareerPath(careerpath.getOID());

                pstCareerPath.setLong(FLD_EMPLOYEE_ID, careerpath.getEmployeeId());
                pstCareerPath.setLong(FLD_COMPANY_ID, careerpath.getCompanyId());
                pstCareerPath.setString(FLD_COMPANY, careerpath.getCompany());
                pstCareerPath.setLong(FLD_DEPARTMENT_ID, careerpath.getDepartmentId());
                pstCareerPath.setString(FLD_DEPARTMENT, careerpath.getDepartment());
                pstCareerPath.setLong(FLD_POSITION_ID, careerpath.getPositionId());
                pstCareerPath.setString(FLD_POSITION, careerpath.getPosition());
                pstCareerPath.setLong(FLD_SECTION_ID, careerpath.getSectionId());
                pstCareerPath.setString(FLD_SECTION, careerpath.getSection());
                pstCareerPath.setLong(FLD_DIVISION_ID, careerpath.getDivisionId());
                pstCareerPath.setString(FLD_DIVISION, careerpath.getDivision());
                pstCareerPath.setLong(FLD_LEVEL_ID, careerpath.getLevelId());
                pstCareerPath.setString(FLD_LEVEL, careerpath.getLevel());
                pstCareerPath.setLong(FLD_EMP_CATEGORY_ID, careerpath.getEmpCategoryId());
                pstCareerPath.setString(FLD_EMP_CATEGORY, careerpath.getEmpCategory());
                pstCareerPath.setDate(FLD_WORK_FROM, careerpath.getWorkFrom());
                pstCareerPath.setDate(FLD_WORK_TO, careerpath.getWorkTo());
                pstCareerPath.setString(FLD_DESCRIPTION, careerpath.getDescription());
                pstCareerPath.setDouble(FLD_SALARY, careerpath.getSalary());
                pstCareerPath.setLong(FLD_LOCATION_ID, careerpath.getLocationId());
                pstCareerPath.setLong(FLD_PROVIDER_ID, careerpath.getProviderID());
                pstCareerPath.setString(FLD_LOCATION, careerpath.getLocation());
                pstCareerPath.setString(FLD_NOTE, careerpath.getNote());
                pstCareerPath.setInt(FLD_HISTORY_TYPE, careerpath.getHistoryType());
                pstCareerPath.setString(FLD_NOMOR_SK, careerpath.getNomorSk());
                pstCareerPath.setDate(FLD_TANGGAL_SK, careerpath.getTanggalSk());
                pstCareerPath.setLong(FLD_EMP_DOC_ID, careerpath.getEmpDocId());
                pstCareerPath.setInt(FLD_HISTORY_GROUP, careerpath.getHistoryGroup());
                pstCareerPath.setDate(FLD_CONTRACT_FROM, careerpath.getContractFrom());
                pstCareerPath.setDate(FLD_CONTRACT_TO, careerpath.getContractTo());
                pstCareerPath.setInt(FLD_CONTRACT_TYPE, careerpath.getContractType());
                pstCareerPath.setInt(FLD_MUTATION_TYPE, careerpath.getMutationType());
                pstCareerPath.setString(FLD_DOCUMENT, careerpath.getDocument());
                pstCareerPath.setInt(FLD_ACKNOWLEDGE_STATUS, careerpath.getAcknowledgeStatus());
                
                pstCareerPath.update();
                return careerpath.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCareerPath(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstCareerPath pstCareerPath = new PstCareerPath(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCareerPath(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW;
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
                CareerPath careerpath = new CareerPath();
                resultToObject(rs, careerpath);
                lists.add(careerpath);
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
    
    public static Vector listEmployeePECareerPath(String periodStart, String periodEnd) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT CP.* FROM " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " CP"
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " POS"
                    + " ON CP." + PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + " ="
                    + " POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " WHERE"
                    + " POS." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + " = 3"
                    + " AND CP." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " BETWEEN '"
                    + periodStart + "' AND '" + periodEnd + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
               CareerPath careerpath = new CareerPath();
                resultToObject(rs, careerpath);
                lists.add(careerpath);
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
    
    public static Vector listKadivCareer(long employeeId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT CP.* FROM " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " CP"
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " POS"
                    + " ON CP." + PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + " ="
                    + " POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " WHERE"
                    + " POS." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + " = 3"
                    + " AND CP." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = "
                    + employeeId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
               CareerPath careerpath = new CareerPath();
                resultToObject(rs, careerpath);
                lists.add(careerpath);
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
    
    public static Vector listLastCareer(long employeeId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT * FROM" + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " WHERE"
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " IN("
                    + employeeId + ") ORDER BY WORK_FROM LIMIT 1";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
               CareerPath careerpath = new CareerPath();
                resultToObject(rs, careerpath);
                lists.add(careerpath);
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
    
    public static Vector listHistory(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
     
         
            String sql = "SELECT p.POSITION, e.EMPLOYEE_NUM, e.FULL_NAME, h.* FROM " + TBL_HR_WORK_HISTORY_NOW + " h "+
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " e ON e."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +" = h."+ fieldNames[FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPosition.TBL_HR_POSITION + " p ON p."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] +" = h."+ fieldNames[FLD_POSITION_ID];
                   
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
                CareerPath careerpath = new CareerPath();
                resultToObjectHistory(rs, careerpath);
                lists.add(careerpath);
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


    
     public static Vector listcheckcareer(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW;
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
                CareerPath careerpath = new CareerPath();
                resultToObject(rs, careerpath);
                lists.add(careerpath);
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
    
    //dedy -20160823, digunakan pada lkpbu 801
    public static Vector listCheckCareerPE(String startDate, String endDate) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
    
        String lkpbuLvlId = "";
        try {
            lkpbuLvlId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }

        try {
            String sql = "SELECT wh.*"
                    + " FROM hr_work_history_now wh"
                    + " INNER JOIN hr_position pos ON wh.`POSITION_ID` = pos.`POSITION_ID`"
                    + " WHERE"
                    + " history_group='0'"
                    + " AND((work_from BETWEEN '" + startDate + "' AND '" + endDate + "') OR (work_to BETWEEN '" + startDate + "' AND '" + endDate + "'))"
                    + " AND pos.`LEVEL_ID` IN(" + lkpbuLvlId + ")"
                    + " ORDER BY employee_id, work_to ASC;";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerpath = new CareerPath();
                resultToObject(rs, careerpath);
                lists.add(careerpath);
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
    //dedy -20160824, digunakan pada lkpbu 801
    public static Vector listCareerLkpbu(String query) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        try {
            String sql = "SELECT * FROM hr_view_work_history_now";
        
            if (query != null && query.length() > 0) {
                sql = sql + " " + query;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerpath = new CareerPath();
                resultCareerLkpbu(rs, careerpath);
                lists.add(careerpath);
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
    
    //dedy -20160824, digunakan pada lkpbu 801
    
    public static Vector getCareerView(String dateFrom) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        // ambil sysprop untuk lkpbu
        String lkpbuLvlId = "";
        try {
            lkpbuLvlId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        String lkpbuLvlPEId = "";
        try {
            lkpbuLvlPEId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_PE_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        
        try {
            String sql = "(SELECT"
                            +" '2' AS CODE, vwhn.*"
                          +" FROM hr_view_work_history_now vwhn"
                            +" INNER JOIN hr_position AS pos"
                              +" ON vwhn.`POSITION_ID` = pos.`POSITION_ID`"
                         /*   +" INNER JOIN hr_level AS lvl"
                              +" ON vwhn.`LEVEL_ID` = lvl.`LEVEL_ID`"*/
                         +" WHERE history_type = 0 "
                              + "AND history_group != 1"
                             // +" AND pos.`HEAD_TITLE` = 3"
                              +" AND '"+dateFrom+"' BETWEEN vwhn.work_from AND vwhn.work_to"
                              //+" AND lvl.level_id NOT IN("+lkpbuLvlPEId+")"
                              +" AND pos.`LEVEL_ID` NOT IN("+lkpbuLvlId+"))"
                              +" UNION"
                          +" (SELECT"
                            +" '1' AS CODE, vwhn.*"
                          +" FROM hr_view_work_history_now vwhn"
                            +" INNER JOIN hr_position AS pos"
                              +" ON vwhn.`POSITION_ID` = pos.`POSITION_ID`"
                            /*+" INNER JOIN hr_level AS lvl"
                              +" ON vwhn.`LEVEL_ID` = lvl.`LEVEL_ID`"*/
                         +" WHERE history_type = 0 "
                              + "AND history_group != 1"
                              +" AND pos.`HEAD_TITLE` = 3"
                              +" AND '"+dateFrom+"' BETWEEN vwhn.work_from AND vwhn.work_to"
                            //  +" AND lvl.level_id IN("+lkpbuLvlPEId+")"
                              +" AND pos.`LEVEL_ID` IN("+lkpbuLvlId+"))"
                              +" ORDER BY CODE;";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerpath = new CareerPath();
                resultToObjectNowPrev(rs, careerpath);
                lists.add(careerpath);
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
    
    public static Vector getCareerViewBefore(long empId, String dateFrom) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        // ambil sysprop untuk lkpbu
        String lkpbuLvlId = "";
        try {
            lkpbuLvlId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        String lkpbuLvlPEId = "";
        try {
            lkpbuLvlPEId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_PE_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        
        try {
            String sql = "SELECT"
                            +" '1' AS CODE, vwhn.*"
                          +" FROM hr_view_work_history_now vwhn"
                            +" INNER JOIN hr_position AS pos"
                              +" ON vwhn.`POSITION_ID` = pos.`POSITION_ID`"
                           /*+" INNER JOIN hr_level AS lvl"
                              +" ON vwhn.`LEVEL_ID` = lvl.`LEVEL_ID`"*/
                          +" WHERE history_type = 0 AND history_group != 1"
                              //+" AND pos.`HEAD_TITLE` = 3"
                              +" AND vwhn.employee_id = '"+empId+"'"
                              +" AND vwhn.work_to = '"+dateFrom+"' - INTERVAL 1 DAY"
                             // +" AND lvl.level_id IN("+lkpbuLvlPEId+")"
                              +" AND pos.`LEVEL_ID` IN("+lkpbuLvlId+");";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerpath = new CareerPath();
                resultToObjectNowPrev(rs, careerpath);
                lists.add(careerpath);
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
    
    public static Vector checkPE(long empId, String dateFrom) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        // ambil sysprop untuk lkpbu
        String lkpbuLvlId = "";
        try {
            lkpbuLvlId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        String lkpbuLvlPEId = "";
        try {
            lkpbuLvlPEId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_PE_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        
        try {
            String sql = "SELECT"
                            +" '0' AS CODE, vwhn.*"
                          +" FROM hr_view_work_history_now vwhn"
                            +" INNER JOIN hr_position AS pos"
                              +" ON vwhn.`POSITION_ID` = pos.`POSITION_ID`"
                           /* +" INNER JOIN hr_level AS lvl"
                              +" ON vwhn.`LEVEL_ID` = lvl.`LEVEL_ID`"*/
                          +" WHERE history_group != 1"
                              +" AND pos.`HEAD_TITLE` = 3"
                              +" AND vwhn.employee_id = '"+empId+"'"
                              +" AND (vwhn.work_to < '"+dateFrom+"' OR vwhn.work_from < '"+dateFrom+"')"
                             // +" AND lvl.level_id IN("+lkpbuLvlPEId+")"
                              +" AND pos.`LEVEL_ID` IN("+lkpbuLvlId+");";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerpath = new CareerPath();
                resultToObjectNowPrev(rs, careerpath);
                lists.add(careerpath);
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
    
    public static Vector getCareerNowPrev(long empId, String dateFrom, String dateTo) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        // ambil sysprop untuk lkpbu
        String lkpbuLvlId = "";
        try {
            lkpbuLvlId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        String lkpbuLvlPEId = "";
        try {
            lkpbuLvlPEId = String.valueOf(PstSystemProperty.getValueByName("LKPBU_LEVEL_PE_ID"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        
        try {
            String sql = "(SELECT '2' AS CODE, vwhn.* FROM hr_view_work_history_now vwhn "
                        + "INNER JOIN hr_position AS pos ON vwhn.`POSITION_ID` = pos.`POSITION_ID` "
                        //+ "INNER JOIN hr_level AS lvl ON vwhn.`LEVEL_ID` = lvl.`LEVEL_ID` "
                        + "WHERE "
                        + "history_group != 1 "
                        + "AND pos.`HEAD_TITLE` = 3 "
                        + "AND vwhn.EMPLOYEE_ID = '"+empId+"' "
                        + "AND vwhn.WORK_FROM = '"+dateFrom+"' "
                        //+ "AND lvl.level_id IN ("+lkpbuLvlPEId+") "
                        + "AND pos.`LEVEL_ID` IN("+lkpbuLvlId+")) "
                        + "UNION "
                        + "(SELECT '1' AS CODE, vwhn.* FROM hr_view_work_history_now vwhn "
                        + "INNER JOIN hr_position AS pos ON vwhn.`POSITION_ID` = pos.`POSITION_ID` "
                        //+ "INNER JOIN hr_level AS lvl ON vwhn.`LEVEL_ID` = lvl.`LEVEL_ID` "
                        + "WHERE "
                        + "history_group != 1 "
                        + "AND pos.`HEAD_TITLE` = 3 "
                        + "AND vwhn.EMPLOYEE_ID = '"+empId+"' "
                        + "AND vwhn.WORK_FROM = ('"+dateTo+"' + INTERVAL 1 DAY) "
                        //+ "AND lvl.level_id IN ("+lkpbuLvlPEId+") "
                        + "AND pos.`LEVEL_ID` IN("+lkpbuLvlId+")) "
                        + "ORDER BY CODE";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerpath = new CareerPath();
                resultToObjectNowPrev(rs, careerpath);
                lists.add(careerpath);
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
     * query menampilkan workDate to terakhir dari employee {
     * ari_20110912
     * @param rs
     * @param careerpath
     */
    public static Date workDate(long employeeId) {
        Date LastworkDate = null;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAX(" + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO] + ") FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + employeeId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LastworkDate = rs.getDate(1);
            }
            rs.close();
            return LastworkDate;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Date();
    }
    
    public static Date getNewWorkFromOrCommencingDate(long employeeId) {
        Date newDate = null;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAX(" + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO] + ") FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                       + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + employeeId + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 0 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 1 + " )";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                newDate = rs.getDate(1);
            }
            rs.close();
            if (newDate!=null){
                newDate.setDate(newDate.getDate()+1);
                newDate.setMonth(newDate.getMonth());
                newDate.setYear(newDate.getYear());
                return newDate;
            } else {
                Employee employeeX = new Employee();
                try {
                employeeX = PstEmployee.fetchExc(employeeId); 
                newDate = employeeX.getCommencingDate();
                } catch (Exception exx) {}
                return newDate;
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Date();
    }
    
    public static Date getNewContractFromOrCommencingDate(long employeeId) {
        Date newDate = null;
        Date newDateFrom = null;
        DBResultSet dbrs = null;
        
        Employee employee = new Employee() ;
        try{
        employee = PstEmployee.fetchExc(employeeId);
        }catch(Exception e){}
        
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                       + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + employeeId + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 0 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 1 + " ) ORDER BY "  + PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_TO] + " ASC ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerPath = new CareerPath() ;
                resultToObject(rs, careerPath);
                newDate = careerPath.getContractTo();
                newDateFrom = careerPath.getContractFrom();
            }
            rs.close();
            
            if (newDate!=null && (!(""+newDate).equals(""+employee.getEnd_contract()))  ){
                newDate.setDate(newDate.getDate()+1);
                newDate.setMonth(newDate.getMonth());
                newDate.setYear(newDate.getYear());
                return newDate;
            } else {
                newDate = newDateFrom;
                if (newDate == null){
                    newDate = employee.getCommencingDate();
                }
                return newDate;
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Date();
    }
    /*}*/

    private static void resultToObject(ResultSet rs, CareerPath careerpath) {
        try {
            careerpath.setOID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
            careerpath.setEmployeeId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]));
            careerpath.setCompanyId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]));
            careerpath.setCompany(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY]));
            careerpath.setDepartmentId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]));
            careerpath.setDepartment(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT]));
            careerpath.setPositionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]));
            careerpath.setPosition(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION]));
            careerpath.setSectionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]));
            careerpath.setSection(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION]));
            careerpath.setDivisionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]));
            careerpath.setDivision(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION]));
            careerpath.setLevelId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]));
            careerpath.setLevel(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL]));
            careerpath.setEmpCategoryId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]));
            careerpath.setEmpCategory(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY]));
            careerpath.setWorkFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
            careerpath.setWorkTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));
            careerpath.setDescription(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DESCRIPTION]));
            careerpath.setSalary(rs.getDouble(PstCareerPath.fieldNames[PstCareerPath.FLD_SALARY]));
            careerpath.setLocationId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_LOCATION_ID]));
            careerpath.setProviderID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_PROVIDER_ID]));
            careerpath.setLocation(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_LOCATION]));
            careerpath.setNote(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_NOTE]));
            careerpath.setHistoryType(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE]));
            careerpath.setNomorSk(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_NOMOR_SK]));
            careerpath.setTanggalSk(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_TANGGAL_SK]));
            careerpath.setEmpDocId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_DOC_ID]));
            careerpath.setHistoryGroup(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP]));
            careerpath.setGradeLevelId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_GRADE_LEVEL_ID]));
            careerpath.setContractFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_FROM]));
            careerpath.setContractTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_TO]));
            careerpath.setContractType(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_TYPE]));
            careerpath.setMutationType(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_MUTATION_TYPE]));
            careerpath.setDocument(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DOCUMENT]));
            careerpath.setAcknowledgeStatus(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_ACKNOWLEDGE_STATUS]));

        } catch (Exception e) {
        }
    }
    
    //20160824 - lkpbu
    private static void resultCareerLkpbu(ResultSet rs, CareerPath careerpath) {
        try {
            careerpath.setOID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
            careerpath.setEmployeeId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]));
            careerpath.setCompanyId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]));
            careerpath.setDepartmentId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]));
            careerpath.setPositionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]));
            careerpath.setSectionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]));
            careerpath.setDivisionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]));
            careerpath.setLevelId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]));
            careerpath.setEmpCategoryId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]));
            careerpath.setWorkFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
            careerpath.setWorkTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));
            careerpath.setNomorSk(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_NOMOR_SK]));
            careerpath.setTanggalSk(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_TANGGAL_SK]));
            careerpath.setHistoryGroup(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP]));
            careerpath.setGradeLevelId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_GRADE_LEVEL_ID]));
            
        } catch (Exception e) {
        }
    }
    private static void resultToObjectNowPrev(ResultSet rs, CareerPath careerpath) {
        try {
            //careerpath.setCode(rs.getInt("CODE"));
            careerpath.setOID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
            careerpath.setEmployeeId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]));
            careerpath.setCompanyId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]));
            careerpath.setDepartmentId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]));
            careerpath.setPositionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]));
            careerpath.setSectionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]));
            careerpath.setDivisionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]));
            careerpath.setLevelId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]));
            careerpath.setEmpCategoryId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]));
            careerpath.setWorkFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
            careerpath.setWorkTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));
            careerpath.setNomorSk(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_NOMOR_SK]));
            careerpath.setTanggalSk(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_TANGGAL_SK]));
            careerpath.setHistoryGroup(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP]));
            careerpath.setGradeLevelId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_GRADE_LEVEL_ID]));
            
        } catch (Exception e) {
        }
    }

    private static void resultToObjectHistory(ResultSet rs, CareerPath careerpath) {
        try {
            careerpath.setOID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
            careerpath.setEmployeeId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]));
            careerpath.setCompanyId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]));
            careerpath.setCompany(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY]));
            careerpath.setDepartmentId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]));
            careerpath.setDepartment(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT]));
            careerpath.setPositionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]));
            String posPrev = rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION]);
            String posNow = rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]);
            careerpath.setPosition(((posPrev!=null && posPrev.length()>1 && posPrev.compareToIgnoreCase(posNow)==0) ? posPrev+"/" : "" ) +  posNow );
            
            careerpath.setSectionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]));
            careerpath.setSection(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION]));
            careerpath.setDivisionId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]));
            careerpath.setDivision(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION]));
            careerpath.setLevelId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]));
            careerpath.setLevel(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL]));
            careerpath.setEmpCategoryId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]));
            careerpath.setEmpCategory(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY]));
            careerpath.setWorkFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
            careerpath.setWorkTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));
            careerpath.setDescription(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DESCRIPTION]));
            careerpath.setSalary(rs.getDouble(PstCareerPath.fieldNames[PstCareerPath.FLD_SALARY]));
            careerpath.setLocationId(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_LOCATION_ID]));
            careerpath.setProviderID(rs.getLong(PstCareerPath.fieldNames[PstCareerPath.FLD_PROVIDER_ID]));
            careerpath.setLocation(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_LOCATION]));
            careerpath.setNote(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_NOTE]));
            Employee employee = new Employee();
            employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
            employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
            careerpath.setContractFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_FROM]));
            careerpath.setContractTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_TO]));
            careerpath.setContractType(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_TYPE]));
            careerpath.setMutationType(rs.getInt(PstCareerPath.fieldNames[PstCareerPath.FLD_MUTATION_TYPE]));
            careerpath.setEmployee(employee);

        } catch (Exception e) {
        }
    }
    
    public static void updateFileName(String fileName,long idDetail) {
        try {
            String sql = "UPDATE " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW+
            " SET " + PstCareerPath.fieldNames[FLD_DOCUMENT] + " = '" + fileName +"'"+
            " WHERE " + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID] +
            " = " + idDetail ;           
            System.out.println("sql PstCareerPath.updateFileName : " + sql);
            int result = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("\tExc updateFileName : " + e.toString());
        } finally {
            //System.out.println("\tFinal updatePresenceStatus");
        }
    }


    public static boolean checkOID(long workHistoryNowId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID] + " = " + workHistoryNowId;

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
            String sql = "SELECT COUNT(" + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID] + ") FROM " + TBL_HR_WORK_HISTORY_NOW;
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
                    CareerPath careerpath = (CareerPath) list.get(ls);
                    if (oid == careerpath.getOID()) {
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
        vectSize = vectSize + mdl;
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

    public static long deleteByEmployee(long emplOID) {
        try {
            String sql = " DELETE FROM " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW
                    + " WHERE " + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]
                    + " = " + emplOID;

            int status = DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println("error delete experience by employee " + exc.toString());
        }

        return emplOID;
    }

    public static boolean checkDepartment(long departmentId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID] + " = " + departmentId;

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

    public static boolean checkPosition(long positionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + " = " + positionId;

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

    public static boolean checkDivision(long divisionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION] + " = " + divisionId;

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
    
    //priska menambahkan location
    //27okt2014
    public static boolean checkLocation(long locationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_LOCATION] + " = " + locationId;

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

    /**
     * Ari_20110930
     * Menambah checkLevel dan checkEmpCategory {
     * @param
     * @return
     */
    public static boolean checkLevel(long levelId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID] + " = " + levelId;

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

    public static boolean checkEmpCategory(long empCategoryId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID] + " = " + empCategoryId;

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
    /* } */

    public static boolean checkSection(long sectionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION] + " = " + sectionId;

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

    /**
     * Ari_20110930
     * Menambah checkCompany {
     * @param companyId
     * @return
     */
    public static boolean checkCompany(long companyId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID] + " = " + companyId;

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
    /* } */

    //Gede_7Feb2012 {
    //untuk report excel
    public static int getCount2(SrcEmployee srcEmployee) {
        DBResultSet dbrs = null;
        SessEmployee sessEmployee = new SessEmployee();

        try {
            String sql = "SELECT COUNT(WHN." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID] + ") FROM " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " WHN INNER JOIN "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON WHN." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]) + " WHERE " + sessEmployee.whereList(srcEmployee) + "GROUP BY WHN." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]
                    + " ORDER BY COUNT(WHN." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID] + ") DESC LIMIT 1";

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
    //Company

    public static String getCompany(String comp) {
        String company = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + " FROM " + PstCompany.TBL_HR_COMPANY + " WHERE " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + "=" + comp;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                company = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return company;
    }
    //Division

    public static String getDivision(String div) {
        String division = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + " FROM " + PstDivision.TBL_HR_DIVISION + " WHERE " + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + "=" + div;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                division = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return division;
    }
    //priksa - location
    //27okt2014
    public static String getLocation(String loc) {
        String location = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstLocation.fieldNames[PstLocation.FLD_NAME]
                    + " FROM " + PstLocation.TBL_P2_LOCATION + " WHERE " + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
                    + "=" + loc;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                location = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return location;
    }
    
    //Department

    public static String getDepartment(String dept) {
        String department = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " FROM " + PstDepartment.TBL_HR_DEPARTMENT + " WHERE " + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + "=" + dept;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                department = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return department;
    }
    //Section

    public static String getSection(String sect) {
        String section = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + " FROM " + PstSection.TBL_HR_SECTION + " WHERE " + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + "=" + sect;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                section = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return section;
    }
    //Position

    public static String getPosition(String pos) {
        String position = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + " FROM " + PstPosition.TBL_HR_POSITION + " WHERE " + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + "=" + pos;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                position = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return position;
    }
    //Level

    public static String getLevel(String lev) {
        String level = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + " FROM " + PstLevel.TBL_HR_LEVEL + " WHERE " + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + "=" + lev;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                level = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return level;
    }
    //Category

    public static String getCategory(String cat) {
        String category = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + " FROM " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " WHERE " + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + "=" + cat;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                category = rs.getString(1);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return category;
    }
    //}
    /*
     * Update by Hendra Putu
     * Date : 2016-05-26
     * Description : drawList (Tampilan Daftar Data Riwayat Jabatan)
     */
    public static String convertHistoryDate(Date historyDate){
        String strDate = "-";
        try {
            if (historyDate == null) {
                historyDate = new Date();
            }
            strDate = Formater.formatDate(historyDate, "yyyy-MM-dd");
        } catch (Exception e) {
            strDate = "-";
        }
        return strDate;
    }
    
    public static String getTableTitle(I_Dictionary dictionaryD, String ClientName){
        String output = "";
        output = "<tr>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.COMPANY)+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.DIVISION)+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.DEPARTMENT)+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord("SECTION")+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.POSITION)+"</td>";

            int SetLocation01 = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
            if (SetLocation01 == 1) 
            {       

                output += "<td class=\"title_tbl\">"+dictionaryD.getWord("LOCATION")+"</td>";

            }

            output += "<td class=\"title_tbl\">"+dictionaryD.getWord("LEVEL")+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord("CATEGORY")+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.History_From)+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.History_To)+"</td>";
            if (!ClientName.equals("BPD")) {
                output += "<td class=\"title_tbl\">Contract From</td>";
                output += "<td class=\"title_tbl\">Contract To</td>";
            } 
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.GRADE)+"</td>";
            output += "<td class=\"title_tbl\">History Type</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.VIEW)+" "+dictionaryD.getWord(I_Dictionary.DETAIL)+"</td>";
            output += "<td class=\"title_tbl\">"+dictionaryD.getWord(I_Dictionary.VIEW)+" "+dictionaryD.getWord(I_Dictionary.DETAIL)+" Doc</td>";
            output += "<td class=\"title_tbl\">&nbsp;</td>";
        output += "</tr>";
        return output;
    }
    
    public static String drawList(Vector listData, long oid, boolean privUpdate, boolean privDelete, I_Dictionary dictionary, Employee employee){
        String output = "";
        String ClientName = "";
        try {
            ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));
        } catch (Exception e) {
            System.out.println("get ClientName (SysProp): " + e);
        }
        /*========= Group : Riwayat Jabatan =========*/
        output += "<div class=\"box-list\">";
        output += "<div class=\"title_part\">Riwayat Jabatan</div>";
        output += "<table class=\"tblStyle\">";
        /* Panggil Table Title*/
        output += getTableTitle(dictionary, ClientName);
        output += "<tr><td colspan=\"14\" class=\"title_tbl_part\">KARIR</td></tr>"; /* colspan = 14 (jumlah field) untuk saat ini dibutuhkan BPD */
        
        output += "<tr><td colspan=\"14\" class=\"title_tbl_part\">PENUGASAN</td></tr>"; /* colspan = 14 (jumlah field) untuk saat ini dibutuhkan BPD */
        
        output += "<tr><td colspan=\"14\" class=\"title_tbl_part\">KARIR SEKARANG</td></tr>"; /* colspan = 14 (jumlah field) untuk saat ini dibutuhkan BPD */
        //output += drawCareerNow(employee, listData);
        output += "</table>";
        output += "</div>";
        /*========= End Group : Riwayat Jabatan =========*/
        
        
        output += "<div>&nbsp;</div>";
        output += "<div>&nbsp;</div>";
        
        
        /*========= Group : Riwayat Grade =========*/
        output += "<div class=\"box-list\">";
        output += "<div class=\"title_part\">Riwayat Grade</div>";
        output += "<table class=\"tblStyle\">";
        /* Panggil Table Title*/
        output += getTableTitle(dictionary, ClientName);
        output += "<tr><td colspan=\"14\" class=\"title_tbl_part\">GRADE</td></tr>"; /* colspan = 14 (jumlah field) untuk saat ini dibutuhkan BPD */
        output += "<tr><td colspan=\"14\" class=\"title_tbl_part\">GRADE SEKARANG</td></tr>"; /* colspan = 14 (jumlah field) untuk saat ini dibutuhkan BPD */
        output += "</table>";
        output += "</div>";
        /*========= End Group : Riwayat Grade =========*/
        return output;
    }
    
    public static String drawCareerNow(Employee employee, Vector listCareer, String clientName) {
        String output = "";
        ChangeValue changeValue = new ChangeValue();
        int SetLocation = -1;
        try{
           SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
        } catch (Exception e){
            System.out.println("SetLocation => "+e.toString());
        }

        output += "<tr>";
        output += "<td id=\"current-list\">"+changeValue.getCompanyName(employee.getCompanyId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getDivisionName(employee.getDivisionId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getDepartmentName(employee.getDepartmentId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getSectionName(employee.getSectionId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getPositionName(employee.getPositionId()) +"</td>";

        if (SetLocation == 1) {
            String locationName = "-";
            if (employee.getLocationId() != 0) {
                Location location = new Location();
                try {
                    location = PstLocation.fetchExc(employee.getLocationId());
                } catch (Exception exc) {
                    System.out.println("SetLocation:" + exc.toString());
                }
                locationName = location.getName();
            }
            output += "<td id=\"current-list\">" + locationName + "</td>";
        }

        output += "<td id=\"current-list\">"+changeValue.getLevelName(employee.getLevelId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getEmpCategory(employee.getEmpCategoryId())+"</td>";
        String tempDate = "";
        Date dateWorkFrom = new Date();
        /* Jika ada data career path maka cari perhitungan workFrom */
        if (listCareer.size() > 0){
            long dateTempLong = 0;
            long dateFromLong = 0;
            for(int j=0; j<listCareer.size(); j++){
                CareerPath careerPath = (CareerPath) listCareer.get(j);
                if (careerPath.getHistoryType()==PstCareerPath.CAREER_TYPE || careerPath.getHistoryType()==PstCareerPath.PEJABAT_SEMENTARA_TYPE){
                    if (careerPath.getHistoryGroup() != PstCareerPath.RIWAYAT_GRADE){
                        /* Initialisasi Data */
                        tempDate = Formater.formatDate(careerPath.getWorkTo(), "yyyyMMdd");
                        dateFromLong = Long.valueOf(tempDate);
                        dateWorkFrom = careerPath.getWorkTo();
                        /* check jika dateTempLong == 0 maka isi nilai inisialisasi */
                        /* hanya dilakukan sekali */
                        if (dateTempLong == 0){
                            dateTempLong = Long.valueOf(tempDate);
                            dateWorkFrom = careerPath.getWorkTo();
                        }
                        /* bandingkan data */
                        if (dateTempLong < dateFromLong){
                            dateTempLong = dateFromLong;
                            dateWorkFrom = careerPath.getWorkTo();
                        }
                    }
                }
            }
            
            /* Get the next Date */
            String nextDate = "-";
            if (dateTempLong == 0 || dateFromLong == 0) {
                output += "<td id=\"current-list\">" + employee.getCommencingDate() + "</td>";
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); /*new SimpleDateFormat("dd MMMM yyyy");*/
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateWorkFrom);
                    c.add(Calendar.DATE, 1);  // number of days to add
                    nextDate = sdf.format(c.getTime());  // dt is now the new date
            } catch(Exception e){
                System.out.println("Date=>"+e.toString());
                }
                output += "<td id=\"current-list\">" + nextDate + "</td>";
            }
            
        } else {
            output += "<td id=\"current-list\">"+convertHistoryDate(employee.getCommencingDate())+"</td>";
            dateWorkFrom = employee.getCommencingDate();
        }
        String str_dt_WorkTo = "NOW";
        output += "<td id=\"current-list\">"+str_dt_WorkTo+"</td>";
        if(!clientName.equals("BPD")){
            output += "<td id=\"current-list\">-</td>";
            output += "<td id=\"current-list\">-</td>";
        }
        String gradeLevel = "";
        try {
            GradeLevel gLevel = PstGradeLevel.fetchExc(employee.getGradeLevelId());
            gradeLevel = gLevel.getCodeLevel();
        } catch(Exception e){
            System.out.print("=>"+e.toString());
        }
        output += "<td id=\"current-list\">"+gradeLevel+"</td>";
        output += "<td id=\"current-list\">"+PstCareerPath.historyType[employee.getHistoryType()]+"</td>";
        output += "<td id=\"current-list\"><a href=\"javascript:cmdDetail2('"+employee.getOID()+"','1')\">Detail</a></td>";
        
        output += "<td id=\"current-list\"><a href=\"javascript:cmdDetailDocument('"+employee.getEmpDocId()+"')\">Detail Document</a></td>";
        output += "<td id=\"current-list\">&nbsp;</td>";
        output += "</tr>";
        return output;

    }
    
    public static String drawGradeNow(Employee employee, Vector listCareer, String clientName) {
        String output = "";
        ChangeValue changeValue = new ChangeValue();
        int SetLocation = -1;
        try{
           SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
        } catch (Exception e){
            System.out.println("SetLocation => "+e.toString());
        }

        output += "<tr>";
        output += "<td id=\"current-list\">"+changeValue.getCompanyName(employee.getCompanyId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getDivisionName(employee.getDivisionId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getDepartmentName(employee.getDepartmentId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getSectionName(employee.getSectionId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getPositionName(employee.getPositionId()) +"</td>";

        if (SetLocation == 1) {
            String locationName = "-";
            if (employee.getLocationId() != 0) {
                Location location = new Location();
                try {
                    location = PstLocation.fetchExc(employee.getLocationId());
                } catch (Exception exc) {
                    System.out.println("SetLocation:" + exc.toString());
                }
                locationName = location.getName();
            }
            output += "<td id=\"current-list\">" + locationName + "</td>";
        }

        output += "<td id=\"current-list\">"+changeValue.getLevelName(employee.getLevelId()) +"</td>";
        output += "<td id=\"current-list\">"+changeValue.getEmpCategory(employee.getEmpCategoryId())+"</td>";
        String tempDate = "";
        Date dateWorkFrom = new Date();
        /* Jika ada data career path maka cari perhitungan workFrom */
        /* Cek history type */
        String whereRJ = fieldNames[FLD_EMPLOYEE_ID]+"="+employee.getOID()+" AND "+fieldNames[FLD_HISTORY_TYPE]+"="+CAREER_TYPE;
        Vector listRiwayatJabatan = list(0, 0, whereRJ, fieldNames[FLD_WORK_FROM]+" DESC");
        CareerPath riwayatJabatanData = new CareerPath();
        if (listRiwayatJabatan != null && listRiwayatJabatan.size()>0){
            riwayatJabatanData = (CareerPath)listRiwayatJabatan.get(0);
        }

        if (employee.getHistoryType() == PEJABAT_SEMENTARA_TYPE){
            if (riwayatJabatanData.getWorkFrom() != null){
                output += "<td id=\"current-list\">"+ riwayatJabatanData.getWorkFrom() +"</td>";
            } else {
                output += "<td id=\"current-list\">"+ employee.getCommencingDate() +"</td>";
            }
        } else {
            if (listCareer.size() > 0){
                long dateTempLong = 0;
                long dateFromLong = 0;
                for(int j=0; j<listCareer.size(); j++){
                    CareerPath careerPath = (CareerPath) listCareer.get(j);
                    if (careerPath.getHistoryGroup() == PstCareerPath.RIWAYAT_CAREER_N_GRADE || careerPath.getHistoryGroup() == PstCareerPath.RIWAYAT_GRADE){
                        if (careerPath.getHistoryType() == PstCareerPath.CAREER_TYPE){
                            /* Initialisasi Data */
                            tempDate = Formater.formatDate(careerPath.getWorkTo(), "yyyyMMdd");
                            dateFromLong = Long.valueOf(tempDate);
                            dateWorkFrom = careerPath.getWorkTo();
                            /* check jika dateTempLong == 0 maka isi nilai inisialisasi */
                            /* hanya dilakukan sekali */
                            if (dateTempLong == 0){
                                dateTempLong = Long.valueOf(tempDate);
                                dateWorkFrom = careerPath.getWorkTo();
                            }
                            /* bandingkan data */
                            if (dateTempLong < dateFromLong){
                                dateTempLong = dateFromLong;
                                dateWorkFrom = careerPath.getWorkTo();
                            }
                        }
                    }
                }
                /* Get the next Date */
                String nextDate = "-";
                if (dateTempLong == 0 || dateFromLong == 0){
                    output += "<td id=\"current-list\">"+ employee.getCommencingDate() +"</td>";
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); /*new SimpleDateFormat("dd MMMM yyyy");*/
                        Calendar c = Calendar.getInstance();
                        c.setTime(dateWorkFrom);
                        c.add(Calendar.DATE, 1);  // number of days to add
                        nextDate = sdf.format(c.getTime());  // dt is now the new date
                    } catch(Exception e){
                        System.out.println("Date=>"+e.toString());
                    }
                    output += "<td id=\"current-list\">"+ nextDate +"</td>";
                }
 
            } else {
                output += "<td id=\"current-list\">"+convertHistoryDate(employee.getCommencingDate())+"</td>";
            }
        }
        
        String str_dt_WorkTo = "NOW";
        output += "<td id=\"current-list\">"+str_dt_WorkTo+"</td>";
        if(!clientName.equals("BPD")){
            output += "<td id=\"current-list\">-</td>";
            output += "<td id=\"current-list\">-</td>";
        }
        String gradeLevel = "";
        try {
            GradeLevel gLevel = PstGradeLevel.fetchExc(employee.getGradeLevelId());
            gradeLevel = gLevel.getCodeLevel();
        } catch(Exception e){
            System.out.print("=>"+e.toString());
        }
        output += "<td id=\"current-list\">"+gradeLevel+"</td>";
        if (employee.getHistoryType() == PEJABAT_SEMENTARA_TYPE){
            if (riwayatJabatanData.getWorkFrom() != null){
                output += "<td id=\"current-list\">"+PstCareerPath.historyType[riwayatJabatanData.getHistoryType()]+"</td>";
            } else {
                output += "<td id=\"current-list\">"+PstCareerPath.historyType[employee.getHistoryType()]+"</td>";
            }
        } else {
            output += "<td id=\"current-list\">"+PstCareerPath.historyType[employee.getHistoryType()]+"</td>";
        }
        
        output += "<td id=\"current-list\"><a href=\"javascript:cmdDetail2('"+employee.getOID()+"','2')\">Detail</a></td>";
        
        output += "<td id=\"current-list\"><a href=\"javascript:cmdDetailDocument('"+employee.getEmpDocIdGrade()+"')\">Detail Document</a></td>";
        output += "<td id=\"current-list\">&nbsp;</td>";
        output += "</tr>";
        return output;

    }
    
    public static String drawRiwayatJabatan(Vector listCareer, boolean privUpdate, boolean privDelete, String ClientName) {
        String output = "";
        if (listCareer != null && listCareer.size()>0){
            for (int i=0; i<listCareer.size(); i++){
                CareerPath careerPath = (CareerPath)listCareer.get(i);
                if ((careerPath.getHistoryGroup() == RIWAYAT_CAREER_N_GRADE)||(careerPath.getHistoryGroup() == RIWAYAT_JABATAN)){
                    if (careerPath.getHistoryType() == CAREER_TYPE || careerPath.getHistoryType() == PEJABAT_SEMENTARA_TYPE ){
                        output += "<tr>";
                        if (privUpdate == true){
                            output += "<td><a href=\"javascript:cmdEdit('"+careerPath.getOID()+"')\">"+careerPath.getCompany()+"</a></td>";
                        } else {
                            output += "<td>"+careerPath.getCompany()+"</td>";
                        }
                        if (careerPath.getDivision()!= null && careerPath.getDivision().length()>0){
                            output += "<td>"+careerPath.getDivision()+"</td>";
                        } else {
                            output += "<td>"+"-"+"</td>";
                        }

                        if(careerPath.getDepartment() != null && careerPath.getDepartment().length()>0){
                            output += "<td>"+careerPath.getDepartment()+"</td>";
                        } else {
                            output += "<td>-</td>";
                        }

                        if (careerPath.getSection()!= null && careerPath.getSection().length()>0){
                            output += "<td>"+careerPath.getSection()+"</td>";
                        } else {
                            output += "<td>-</td>";
                        }

                        if (careerPath.getPosition() != null && careerPath.getPosition().length()>0){
                            output += "<td>"+careerPath.getPosition()+"</td>";
                        } else {
                            output += "<td>-</td>";
                        }

                        int SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
                        if (SetLocation == 1) { output += "<td>"+careerPath.getLocation()+"</td>"; }

                        output += "<td>"+careerPath.getLevel()+"</td>";
                        output += "<td>"+careerPath.getEmpCategory()+"</td>";

                        output += "<td>"+convertHistoryDate(careerPath.getWorkFrom())+"</td>";
                        output += "<td>"+convertHistoryDate(careerPath.getWorkTo())+"</td>";
                        if(!ClientName.equals("BPD")){
                            output += "<td>"+convertHistoryDate(careerPath.getContractFrom())+"</td>";
                            output += "<td>"+convertHistoryDate(careerPath.getContractTo())+"</td>";
                        }

                        String strGrade = "-";
                        if (careerPath.getGradeLevelId() != 0){
                            try {
                                GradeLevel gLevel = PstGradeLevel.fetchExc(careerPath.getGradeLevelId());
                                strGrade = gLevel.getCodeLevel();
                            } catch(Exception e){
                                System.out.println(""+e.toString());
                            }
                        } else {
                            strGrade = "-";
                        }         
                        output += "<td>"+strGrade+"</td>";
                        output += "<td>"+PstCareerPath.historyType[careerPath.getHistoryType()]+"</td>";
                        output += "<td>"+"<a href=\"javascript:cmdDetail('"+careerPath.getOID()+"','1')\">Detail</a></td>";
                        if (careerPath.getEmpDocId()!=0){
                            output += "<td>"+"<a href=\"javascript:cmdEditDetail2('"+careerPath.getEmpDocId()+"')\">Detail</a></td>";
                        } else {
                            output += "<td>-</td>";
                        }
                        String btnDel = "";
                        if (privDelete == true){
                            btnDel = "<a style=\"text-decoration:none; color:#575757;\" class=\"btn-small\" href=\"javascript:cmdMinta('"+careerPath.getOID()+"')\">&times;</a>";
                        }//
                        output += "<td>"+btnDel+"</td>";
                        output += "</tr>";
                    }
                }
            }
        }
        return output;
    }
    
    public static String drawRiwayatJabatanPenugasan(Vector listCareer, int historyType, boolean privUpdate, boolean privDelete, String ClientName) {
        String output = "";
        if (listCareer != null && listCareer.size()>0){
            for (int i=0; i<listCareer.size(); i++){
                CareerPath careerPath = (CareerPath)listCareer.get(i);
                if ((careerPath.getHistoryGroup() == RIWAYAT_CAREER_N_GRADE)||(careerPath.getHistoryGroup() == RIWAYAT_JABATAN)){
                    if (careerPath.getHistoryType() == historyType){
                        output += "<tr>";
                        if (privUpdate == true){
                            output += "<td><a href=\"javascript:cmdEdit('"+careerPath.getOID()+"')\">"+careerPath.getCompany()+"</a></td>";
                        } else {
                            output += "<td>"+careerPath.getCompany()+"</td>";
                        }
                        if (careerPath.getDivision()!= null && careerPath.getDivision().length()>0){
                            output += "<td>"+careerPath.getDivision()+"</td>";
                        } else {
                            output += "<td>"+"-"+"</td>";
                        }

                        if(careerPath.getDepartment() != null && careerPath.getDepartment().length()>0){
                            output += "<td>"+careerPath.getDepartment()+"</td>";
                        } else {
                            output += "<td>-</td>";
                        }

                        if (careerPath.getSection()!= null && careerPath.getSection().length()>0){
                            output += "<td>"+careerPath.getSection()+"</td>";
                        } else {
                            output += "<td>-</td>";
                        }

                        if (careerPath.getPosition() != null && careerPath.getPosition().length()>0){
                            output += "<td>"+careerPath.getPosition()+"</td>";
                        } else {
                            output += "<td>-</td>";
                        }

                        int SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
                        if (SetLocation == 1) { output += "<td>"+careerPath.getLocation()+"</td>"; }

                        output += "<td>"+careerPath.getLevel()+"</td>";
                        output += "<td>"+careerPath.getEmpCategory()+"</td>";

                        output += "<td>"+convertHistoryDate(careerPath.getWorkFrom())+"</td>";
                        output += "<td>"+convertHistoryDate(careerPath.getWorkTo())+"</td>";
                        if(!ClientName.equals("BPD")){
                            output += "<td>"+convertHistoryDate(careerPath.getContractFrom())+"</td>";
                            output += "<td>"+convertHistoryDate(careerPath.getContractTo())+"</td>";
                        }

                        String strGrade = "-";
                        if (careerPath.getGradeLevelId() != 0){
                            try {
                                GradeLevel gLevel = PstGradeLevel.fetchExc(careerPath.getGradeLevelId());
                                strGrade = gLevel.getCodeLevel();
                            } catch(Exception e){
                                System.out.println(""+e.toString());
                            }
                        } else {
                            strGrade = "-";
                        }         
                        output += "<td>"+strGrade+"</td>";
                        output += "<td>"+PstCareerPath.historyType[careerPath.getHistoryType()]+"</td>";
                        output += "<td>"+"<a href=\"javascript:cmdDetail('"+careerPath.getOID()+"')\">Detail</a></td>";
                        if (careerPath.getEmpDocId()!=0){
                            output += "<td>"+"<a href=\"javascript:cmdEditDetail2('"+careerPath.getEmpDocId()+"')\">Detail</a></td>";
                        } else {
                            output += "<td>-</td>";
                        }
                        String btnDel = "";
                        if (privDelete == true){
                            btnDel = "<a style=\"text-decoration:none; color:#575757;\" class=\"btn-small\" href=\"javascript:cmdMinta('"+careerPath.getOID()+"')\">&times;</a>";
                        }//
                        output += "<td>"+btnDel+"</td>";
                        output += "</tr>";
                    }
                }
            }
        }
        return output;
    }
    
    public static String drawRiwayatGrade(Vector listCareer, boolean privUpdate, boolean privDelete, String ClientName) {
        String output = "";
        if (listCareer != null && listCareer.size()>0){
            for (int i=0; i<listCareer.size(); i++){
                CareerPath careerPath = (CareerPath)listCareer.get(i);
                if ((careerPath.getHistoryGroup() == RIWAYAT_CAREER_N_GRADE)||(careerPath.getHistoryGroup() == RIWAYAT_GRADE)){
                    output += "<tr>";
                    if (privUpdate == true){
                        output += "<td><a href=\"javascript:cmdEdit('"+careerPath.getOID()+"')\">"+careerPath.getCompany()+"</a></td>";
                    } else {
                        output += "<td>"+careerPath.getCompany()+"</td>";
                    }
                    if (careerPath.getDivision()!= null && careerPath.getDivision().length()>0){
                        output += "<td>"+careerPath.getDivision()+"</td>";
                    } else {
                        output += "<td>"+"-"+"</td>";
                    }

                    if(careerPath.getDepartment() != null && careerPath.getDepartment().length()>0){
                        output += "<td>"+careerPath.getDepartment()+"</td>";
                    } else {
                        output += "<td>-</td>";
                    }

                    if (careerPath.getSection()!= null && careerPath.getSection().length()>0){
                        output += "<td>"+careerPath.getSection()+"</td>";
                    } else {
                        output += "<td>-</td>";
                    }

                    if (careerPath.getPosition() != null && careerPath.getPosition().length()>0){
                        output += "<td>"+careerPath.getPosition()+"</td>";
                    } else {
                        output += "<td>-</td>";
                    }

                    int SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
                    if (SetLocation == 1) { output += "<td>"+careerPath.getLocation()+"</td>"; }

                    output += "<td>"+careerPath.getLevel()+"</td>";
                    output += "<td>"+careerPath.getEmpCategory()+"</td>";

                    output += "<td>"+convertHistoryDate(careerPath.getWorkFrom())+"</td>";
                    output += "<td>"+convertHistoryDate(careerPath.getWorkTo())+"</td>";
                    if(!ClientName.equals("BPD")){
                        output += "<td>"+convertHistoryDate(careerPath.getContractFrom())+"</td>";
                        output += "<td>"+convertHistoryDate(careerPath.getContractTo())+"</td>";
                    }

                    String strGrade = "-";
                    if (careerPath.getGradeLevelId() != 0){
                        try {
                            GradeLevel gLevel = PstGradeLevel.fetchExc(careerPath.getGradeLevelId());
                            strGrade = gLevel.getCodeLevel();
                        } catch(Exception e){
                            System.out.println(""+e.toString());
                        }
                    } else {
                        strGrade = "-";
                    }         
                    output += "<td>"+strGrade+"</td>";
                    output += "<td>"+PstCareerPath.historyType[careerPath.getHistoryType()]+"</td>";
                    output += "<td>"+"<a href=\"javascript:cmdDetail('"+careerPath.getOID()+"','2')\">Detail</a></td>";
                    if (careerPath.getEmpDocId()!=0){
                        output += "<td>"+"<a href=\"javascript:cmdEditDetail2('"+careerPath.getEmpDocId()+"')\">Detail</a></td>";
                    } else {
                        output += "<td>-</td>";
                    }
                    String btnDel = "";
                    if (privDelete == true){
                        btnDel = "<a style=\"text-decoration:none; color:#575757;\" class=\"btn-small\" href=\"javascript:cmdMinta('"+careerPath.getOID()+"')\">&times;</a>";
                    }//
                    output += "<td>"+btnDel+"</td>";
                    output += "</tr>";
                }
            }
        }
        return output;
    }
    
    public static Date getPrevOrNextDate(Date inputDate, int prevOrNext){
        String output = "";
        Date outDate = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); /*new SimpleDateFormat("dd MMMM yyyy");*/
            Calendar c = Calendar.getInstance();
            c.setTime(inputDate);
            c.add(Calendar.DATE, prevOrNext);  
            output = sdf.format(c.getTime());  
            outDate = sdf.parse(output);
        } catch(Exception e){
            System.out.println("Next Date=>"+e.toString());
        }

        return outDate;
    }
    
    public static Date getLastDateCareer(long employeeId, String historyGroupCondition){
        Date outDate = new Date();
        String order = fieldNames[FLD_WORK_TO]+" DESC ";
        String where = fieldNames[FLD_EMPLOYEE_ID]+"="+employeeId+" AND ("+fieldNames[FLD_HISTORY_TYPE]+"="+CAREER_TYPE+" OR ";
        where += fieldNames[FLD_HISTORY_TYPE]+"="+PEJABAT_SEMENTARA_TYPE+") ";
        where += " AND "+historyGroupCondition;
        Vector listCareer = list(0, 0, where, order);
        if (listCareer != null && listCareer.size()>0){
            CareerPath careerPath = (CareerPath)listCareer.get(0);
            outDate = careerPath.getWorkTo();
        } else {
            try {
                Employee employee = PstEmployee.fetchExc(employeeId);
                outDate = employee.getCommencingDate();
            } catch(Exception e){
                System.out.println("getLastDateCareer=>"+e.toString());
            }
        }
        return outDate;
    }
    
    public static Date getLastDateCareerVersi2(long employeeId){
        Date outDate = new Date();
        String order = fieldNames[FLD_WORK_TO]+" DESC ";
        String where = fieldNames[FLD_EMPLOYEE_ID]+"="+employeeId+" AND ("+fieldNames[FLD_HISTORY_TYPE]+"="+CAREER_TYPE+" OR ";
        where += fieldNames[FLD_HISTORY_TYPE]+"="+PEJABAT_SEMENTARA_TYPE+") ";
        where += " AND "+fieldNames[FLD_HISTORY_GROUP]+"="+RIWAYAT_JABATAN;
        Vector listCareer = list(0, 0, where, order);
        if (listCareer != null && listCareer.size()>0){
            CareerPath careerPath = (CareerPath)listCareer.get(0);
            outDate = careerPath.getWorkTo();
        } else {
            try {
                Employee employee = PstEmployee.fetchExc(employeeId);
                outDate = employee.getCommencingDate();
            } catch(Exception e){
                System.out.println("getLastDateCareer=>"+e.toString());
            }
        }
        return outDate;
    }
    
    public static Date getLastDateCareerVersi3(long employeeId){
        Date outDate = new Date();
        String order = fieldNames[FLD_WORK_TO]+" DESC ";
        String where = fieldNames[FLD_EMPLOYEE_ID]+"="+employeeId+" AND ("+fieldNames[FLD_HISTORY_TYPE]+"="+CAREER_TYPE+" OR ";
        where += fieldNames[FLD_HISTORY_TYPE]+"="+PEJABAT_SEMENTARA_TYPE+") ";
        where += " AND "+fieldNames[FLD_HISTORY_GROUP]+"="+RIWAYAT_GRADE;
        Vector listCareer = list(0, 0, where, order);
        if (listCareer != null && listCareer.size()>0){
            CareerPath careerPath = (CareerPath)listCareer.get(0);
            outDate = careerPath.getWorkTo();
        } else {
            try {
                Employee employee = PstEmployee.fetchExc(employeeId);
                outDate = employee.getCommencingDate();
            } catch(Exception e){
                System.out.println("getLastDateCareer=>"+e.toString());
            }
        }
        return outDate;
    }
    
    /**
     * Date         : 2016-09-01
     * Author       : Hendra Putu
     * Description  : Check Riwayat Jabatan by Period
     **/
    public static Vector getCareerListByPeriod(String periodFrom, String periodTo, long divisionId){
        Vector careerList = new Vector();
        /*
         * input : Period From - Period To
         * sumber pencarian : career path dan databank
         * filter : position
         */
        /* coversi period from n period to */
        int intPeriodFrom = getConvertDateToInt(periodFrom);
        int intPeriodTo = getConvertDateToInt(periodTo);
        
        boolean ketemu = false;
        
        String whereClause = fieldNames[FLD_DIVISION_ID]+"="+divisionId+" AND "+fieldNames[FLD_HISTORY_GROUP]+"="+RIWAYAT_JABATAN;
        String orderBy = fieldNames[FLD_WORK_FROM];
        Vector listCareer = list(0, 0, whereClause, orderBy);
        if (listCareer != null && listCareer.size()>0){
            for (int i=0; i<listCareer.size(); i++){
                CareerPath career = (CareerPath)listCareer.get(i);
                String workFrom = ""+career.getWorkFrom();
                String workTo = ""+career.getWorkTo();
                int intWorkFrom = getConvertDateToInt(workFrom);
                int intWorkTo = getConvertDateToInt(workTo);
                
                ketemu = checkDataByPeriod(intWorkFrom, intWorkTo, intPeriodFrom, intPeriodTo);
                if (ketemu){
                    careerList.add(career);
                }
                ketemu = false;
            }
        }
        return careerList;
    }
    
    public static int getConvertDateToInt(String date){
        String[] arrDate = date.split("-");
        int intDate = Integer.valueOf(arrDate[0] + arrDate[1] + arrDate[2]);
        return intDate;
    }
    
    public static boolean checkDataByPeriod(int intWorkFrom, int intWorkTo, int intPeriodFrom, int intPeriodTo){
        String strBiner = "";
        boolean ketemu = false;
        int[] biner = new int[8];
        
        for (int b = 0; b < biner.length; b++) {
            biner[b] = 0;
        }
        if (intWorkFrom >= intPeriodFrom) {
            biner[0] = 1;
        } else { /* intWorkFrom < intPeriodFrom */
            biner[1] = 1;
        }
        if (intWorkFrom >= intPeriodTo) {
            biner[2] = 1;
        } else { /* intWorkFrom < intPeriodTo */
            biner[3] = 1;
        }

        if (intWorkTo >= intPeriodFrom) {
            biner[4] = 1;
        } else { /* intWorkTo < intPeriodFrom */
            biner[5] = 1;
        }
        if (intWorkTo >= intPeriodTo) {
            biner[6] = 1;
        } else { /* intWorkTo < intPeriodTo */
            biner[7] = 1;
        }

        for (int b = 0; b < biner.length; b++) {
            strBiner = strBiner + biner[b];
        }

        if (strBiner.equals("10011001")) {
            /*
             * Pf ===================== Pt
             *      Sd =========== Ed
             */
            ketemu = true;
        }
        if (strBiner.equals("01011010")) {
            /*
             *      Pf ======= Pt
             * Sd ================== Ed
             */
            ketemu = true;
        }
        if (strBiner.equals("10011010")) {
            /* 
             * Pf ================== Pt
             *          Sd ================ Ed
             */
            ketemu = true;
        }
        if (strBiner.equals("01011001")) {
            /*
             *          Pf ============ Pt
             * Sd ============= Ed
             */
            ketemu = true;
        }
        if (strBiner.equals("01010101")) {
            /*
             *              Pf ========== Pt
             * Sd ===== Ed
             */
        }
        if (strBiner.equals("10101010")) {
            /* 
             * Pf ========== Pt
             *                  Sd ========= Ed
             */
        }
        
        if (intWorkFrom == intPeriodFrom && intWorkFrom == intPeriodTo) {
            ketemu = true;
        }
        return ketemu;
    }
    
    public static Vector getEmployeeByPeriod(String periodFrom, String periodTo, long divisionId, String positionIds){
        Vector employeeList = new Vector();
        Vector careerList = getCareerListByPeriod(periodFrom, periodTo, divisionId);
        String whereClause = "";
        whereClause = PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" IN("+positionIds+") AND ";
        whereClause += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
        Vector empListTemp = PstEmployee.list(0, 0, whereClause, "");
        boolean ketemu = false;

        /* Sumber data = career path {proses add to employeeList} */
        if (careerList != null && careerList.size()>0){
            for (int i=0; i<careerList.size(); i++){
                CareerPath career = (CareerPath)careerList.get(i);
                EmployeeAndPosition empNPos = new EmployeeAndPosition();
                empNPos.setDivisionId(career.getDivisionId());
                empNPos.setEmployeeId(career.getEmployeeId());
                empNPos.setPositionId(career.getPositionId());
                employeeList.add(empNPos); /* this result */
            }
        }
        
        /* Sumber data = databank {proses add to employeeList} */
        if (empListTemp != null && empListTemp.size()>0){
            for (int i=0; i<empListTemp.size(); i++){
                Employee emp = (Employee)empListTemp.get(i);
                ketemu = isCurrentCareer(emp.getOID(), emp.getPositionId(), periodFrom, periodTo);
                if (ketemu){
                    EmployeeAndPosition empNPos = new EmployeeAndPosition();
                    empNPos.setDivisionId(emp.getDivisionId());
                    empNPos.setEmployeeId(emp.getOID());
                    empNPos.setPositionId(emp.getPositionId());
                    employeeList.add(empNPos); /* this result */
                }
            }
        }
        
        return employeeList;
    }
    
    public static boolean isCurrentCareer(long employeeId, long positionId, String periodFrom, String periodTo){
        int intPeriodFrom = getConvertDateToInt(periodFrom);
        int intPeriodTo = getConvertDateToInt(periodTo);
        boolean ketemu = false;
        
        String whereClause = fieldNames[FLD_EMPLOYEE_ID]+"="+employeeId+" AND "+fieldNames[FLD_HISTORY_GROUP]+"="+RIWAYAT_JABATAN;
        String orderBy = fieldNames[FLD_WORK_FROM]+" DESC ";
        Vector listCareer = list(0, 0, whereClause, orderBy);
        if (listCareer != null && listCareer.size()>0){
            CareerPath career = (CareerPath)listCareer.get(0);
            String workFrom = ""+career.getWorkFrom();
            String workTo = ""+career.getWorkTo();
            int intWorkFrom = getConvertDateToInt(workFrom);
            int intWorkTo = getConvertDateToInt(workTo);
            
            Date now = new Date();
            Date nextDate = getPrevOrNextDate(career.getWorkTo(), 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strNextDate = sdf.format(nextDate);
            String strDateNow = sdf.format(now);
            int intNextDate = getConvertDateToInt(strNextDate);
            int intDateNow = getConvertDateToInt(strDateNow);
            
            ketemu = checkDataByPeriod(intWorkFrom, intWorkTo, intPeriodFrom, intPeriodTo);
            if (ketemu){
                if (career.getPositionId() == positionId){
                    return false;
                } else {
                    ketemu = checkDataByPeriod(intNextDate, intDateNow, intPeriodFrom, intPeriodTo);
                    if (ketemu){
                        return true;
                    }
                }
            } else {
                ketemu = checkDataByPeriod(intNextDate, intDateNow, intPeriodFrom, intPeriodTo);
                if (ketemu){
                    return true;
                }
            }
        }
        return ketemu;
    }
    
    /* 
     * Update 2016-09-05 | Hendra Putu
     * Pencarian employee if (divType == PstDivisionType.TYPE_BOD){}
     */
    public static long getEmployeeIdCaseBOD(String periodFrom, String periodTo, long positionId){
        long employeeId = 0;
        long divisionId = 0;
        String whereClause = "";
        int intPeriodFrom = getConvertDateToInt(periodFrom);
        int intPeriodTo = getConvertDateToInt(periodTo);
        boolean ketemu = false;
        
        /* cari division dari hr_position_division */
        whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_POSITION_ID]+"="+positionId;
        Vector listPosDiv = PstPositionDivision.list(0, 0, whereClause, "");
        if (listPosDiv != null && listPosDiv.size()>0){
            PositionDivision posDiv = (PositionDivision)listPosDiv.get(0);
            divisionId = posDiv.getDivisionId();
        }
        
        whereClause  = fieldNames[FLD_POSITION_ID] + "=" + positionId + " AND ";
        whereClause += fieldNames[FLD_DIVISION_ID] + "=" + divisionId+" AND "+fieldNames[FLD_HISTORY_GROUP]+"="+RIWAYAT_JABATAN;
        String orderBy = fieldNames[FLD_WORK_FROM];
        Vector listCareer = list(0, 0, whereClause, orderBy);
        if (listCareer != null && listCareer.size()>0){
            for (int i=0; i<listCareer.size(); i++){
                CareerPath career = (CareerPath)listCareer.get(i);
                String workFrom = ""+career.getWorkFrom();
                String workTo = ""+career.getWorkTo();
                int intWorkFrom = getConvertDateToInt(workFrom);
                int intWorkTo = getConvertDateToInt(workTo);
                ketemu = checkDataByPeriod(intWorkFrom, intWorkTo, intPeriodFrom, intPeriodTo);
                if (ketemu){
                    employeeId = career.getEmployeeId();
                }
                ketemu = false;
            }
        }
        
        if (employeeId == 0){
            whereClause = PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+positionId+" AND ";
            whereClause += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
            Vector empListTemp = PstEmployee.list(0, 0, whereClause, "");
            
            /* Sumber data = databank {proses add to employeeList} */
            if (empListTemp != null && empListTemp.size()>0){
                for (int i=0; i<empListTemp.size(); i++){
                    Employee emp = (Employee)empListTemp.get(i);
                    ketemu = isCurrentCareer(emp.getOID(), emp.getPositionId(), periodFrom, periodTo);
                    if (ketemu){
                        employeeId = emp.getOID();
                    }
                }
            }
            
        }
        
        return employeeId;
    }
    
    public static Hashtable<String, CareerPath> listCareerPath(String date){
        Hashtable<String, CareerPath> lists = new Hashtable();
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT * FROM `hr_work_history_now` " +
                        "WHERE '"+date+"' BETWEEN work_from AND work_to " +
                        "AND history_group != 1 " +
                        "GROUP BY employee_id " +
                        "ORDER BY employee_id ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                CareerPath cp = new CareerPath();
                resultToObject(rs, cp);
                 lists.put(""+cp.getEmployeeId(), cp);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        
       return new Hashtable();
    }
    
        public static Vector listCareerUnionDatabank(String query) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        try {
            String sql = "SELECT * FROM hr_view_work_history_now";
        
            if (query != null && query.length() > 0) {
                sql = sql + " " + query;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath careerpath = new CareerPath();
                resultCareerLkpbu(rs, careerpath);
                lists.add(careerpath);
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
        
    public static Vector listDataTable(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT WH.* FROM "+PstCareerPath.TBL_HR_WORK_HISTORY_NOW+" AS WH "
                    + " LEFT JOIN "+PstCompany.TBL_HR_COMPANY+" AS COMP "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]+" = "
                        + " COMP."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+""
                    + " LEFT JOIN "+PstDivision.TBL_HR_DIVISION+" AS DV "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]+" = "
                        + " DV."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+""
                    + " LEFT JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" AS DEPT "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]+" = "
                        + " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+""
                    + " LEFT JOIN "+PstSection.TBL_HR_SECTION+" AS SEC "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]+" = "
                        + " SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" AS POS "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]+" = "
                        + " POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN "+PstLevel.TBL_HR_LEVEL+" AS LV "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]+" = "
                        + " LV."+PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]
                    + " LEFT JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY+" AS CAT "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]+" = "
                        + " CAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID];
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
                CareerPath careerpath = new CareerPath();
                resultToObject(rs, careerpath);
                lists.add(careerpath);
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

    public static int getCountDataTable(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]+") "
                    + "FROM "+PstCareerPath.TBL_HR_WORK_HISTORY_NOW+" AS WH "
                    + " LEFT JOIN "+PstCompany.TBL_HR_COMPANY+" AS COMP "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]+" = "
                        + " COMP."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+""
                    + " LEFT JOIN "+PstDivision.TBL_HR_DIVISION+" AS DV "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]+" = "
                        + " DV."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+""
                    + " LEFT JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" AS DEPT "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]+" = "
                        + " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+""
                    + " LEFT JOIN "+PstSection.TBL_HR_SECTION+" AS SEC "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]+" = "
                        + " SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" AS POS "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]+" = "
                        + " POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN "+PstLevel.TBL_HR_LEVEL+" AS LV "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]+" = "
                        + " LV."+PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]
                    + " LEFT JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY+" AS CAT "
                        + " ON WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]+" = "
                        + " CAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID];
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
    
    public static Hashtable fetchExcHashtable(long oid) throws DBException {
        try {
            Hashtable empCareerPath = new Hashtable();
            CareerPath careerPath = new CareerPath();
            PstCareerPath pstCareerPath = new PstCareerPath(oid);
            Employee employee = new Employee();
            
            EmpDoc empDoc = new EmpDoc();
            try{
                empDoc = PstEmpDoc.fetchExc(pstCareerPath.getLong(FLD_EMP_DOC_ID));
            } catch (Exception exc){
                
            }
            
            careerPath.setEmployeeId(pstCareerPath.getLong(FLD_EMPLOYEE_ID));
            try { employee = PstEmployee.fetchExc(careerPath.getEmployeeId()); } catch (Exception exc){}
            empCareerPath.put(PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID], employee.getFullName());
            
            Position position = new Position();
            if(pstCareerPath.getLong(FLD_EMP_DOC_ID) != 0 && empDoc.getDoc_status() == 2){
                careerPath.setPositionId(pstCareerPath.getLong(FLD_POSITION_ID));
                try { position = PstPosition.fetchExc(careerPath.getPositionId()); } catch (Exception exc){}
                empCareerPath.put("POSITION_HISTORY", position.getPosition());
                
                try { position = PstPosition.fetchExc(employee.getPositionId());} catch(Exception exc){}
                empCareerPath.put("POSITION_NOW", position.getPosition());
                
            } else {
                careerPath.setPositionId(pstCareerPath.getLong(FLD_POSITION_ID));
                try { position = PstPosition.fetchExc(careerPath.getPositionId()); } catch (Exception exc){}
                empCareerPath.put("POSITION_NOW", position.getPosition());
                
                try { position = PstPosition.fetchExc(employee.getPositionId());} catch(Exception exc){}
                empCareerPath.put("POSITION_HISTORY", position.getPosition());
            }            
            
            careerPath.setDepartmentId(pstCareerPath.getlong(FLD_DEPARTMENT_ID));
            Department department = new Department();
            try { department = PstDepartment.fetchExc(careerPath.getDepartmentId()); } catch (Exception e){ }
            empCareerPath.put(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID], department.getDepartment());
            
            return empCareerPath;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAward(0), DBException.UNKNOWN);
        }
    }    
        
    
}
