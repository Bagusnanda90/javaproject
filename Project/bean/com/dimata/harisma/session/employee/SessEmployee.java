/*
 * Session Name  	:  SessEmployee.java
 * Created on 	:  [date] [time] AM/PM
 *
 * @author  	: lkarunia
 * @version  	: 01
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 ******************************************************************
 */
package com.dimata.harisma.session.employee;

/* java package */
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
//gede
import java.util.Vector;

/* qdep package */
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import java.util.Date;

/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.session.payroll.SessPaySlip;
import com.dimata.system.entity.system.*;
import com.dimata.harisma.utility.machine.*;
import com.dimata.qdep.entity.I_DocStatus;

public class SessEmployee {

    private static int MIN_BANK_ACC_LENGTH = 5;
    public static final String SESS_SRC_EMPLOYEE = "SESSION_SRC_EMPLOYEE";
    public static final String SESS_SRC_EMPLOYEE_RESIGNATION = "SESS_SRC_EMPLOYEE_RESIGNATION";

    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }

        return vector;
    }

    public static Vector searchEmployeeWithPayLevel(SrcEmployee srcEmployee, int start, int recordToGet) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("nilai src.." + srcEmployee.getSalaryLevel().length());
        if (srcEmployee == null) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_CURIER]
                    /**
                     * Ari_20111003 Menambah Company
                     */
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]+
                    //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+
                    ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstCompany.TBL_HR_COMPANY + " COMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    + " , " + PstPosition.TBL_HR_POSITION + " POST "
                    + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                    + " , " + PstSection.TBL_HR_SECTION + " SEC "
                    + " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                    + //" , "+PstLocker.TBL_HR_LOCKER + " LOC "+
                    " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                    + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                    + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
                    + ", " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEVEL "
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID];

            String whereClause = "";
            if ((srcEmployee.getName() != null) && (srcEmployee.getName().length() > 0)) {
                Vector vectName = logicParser(srcEmployee.getName());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getAddress() != null) && (srcEmployee.getAddress().length() > 0)) {
                Vector vectAddr = logicParser(srcEmployee.getAddress());
                if (vectAddr != null && vectAddr.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectAddr.size(); i++) {
                        String str = (String) vectAddr.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getEmpnumber() != null) && (srcEmployee.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcEmployee.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            //System.out.println("srcEmployee.getStartCommenc = "+srcEmployee.getStartCommenc());
            //System.out.println("srcEmployee.getEndCommenc = "+srcEmployee.getEndCommenc());

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
            }

            if (srcEmployee.getCompanyId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcEmployee.getCompanyId() + " AND ";
            }

            if (srcEmployee.getDepartment() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcEmployee.getDepartment() + " AND ";
            }
            //System.out.println("department"+srcEmployee.getDepartment());

            if (srcEmployee.getDivisionId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcEmployee.getDivisionId() + " AND ";
            }

            if (srcEmployee.getPosition() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcEmployee.getPosition() + " AND ";
            }


            if (srcEmployee.getSection() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcEmployee.getSection() + " AND ";
            }

            if (srcEmployee.getMaritalStatus() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + srcEmployee.getMaritalStatus() + " AND ";
            }

            if (srcEmployee.getSalaryLevel().length() > 0) {
                whereClause = whereClause + " LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + srcEmployee.getSalaryLevel() + "' AND ";
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }

            if (srcEmployee.getEmpCategory() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + srcEmployee.getEmpCategory() + " AND ";
            }

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian by religion,edited by Yunny
            if (srcEmployee.getReligion() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + srcEmployee.getReligion() + " AND ";
            }
            //---------------------
            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny
            if (srcEmployee.getGender() < 2) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + " = " + srcEmployee.getGender() + " AND ";
            }
            //----------------------------

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            switch (srcEmployee.getOrderBy()) {
                case FrmSrcEmployee.ORDER_EMPLOYEE_NAME:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case FrmSrcEmployee.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;
                case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                case FrmSrcEmployee.ORDER_COMM_DATE:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL searchEmployee : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();
                Division division = new Division();
                EmpEducation empEducation = new EmpEducation();
                Company company = new Company();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                /**
                 * menambah company
                 */
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                //khussu untuk Intimas,edited By Yunny
                employee.setCurier(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_CURIER]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);
                /**/
                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                //locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                //locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                vect.add(company);//Tambah Company

                /*String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID];
                 String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
                 Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);

                 Education education = new Education();
                 System.out.println("vedu size"+vedu.size());
                 if(vedu!=null && vedu.size()>0)
                 {
                 empEducation = (EmpEducation)vedu.get(0);
                 try{
                 education = PstEducation.fetchExc(empEducation.getEducationId());
                 }catch(Exception e){;}
                 }
                 vect.add(empEducation);*/

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee: " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector searchEmployee(SrcEmployee srcEmployee, int start, int recordToGet) {
        return searchEmployee(srcEmployee, start, recordToGet, "");
    }

    public static Vector searchEmployee(SrcEmployee srcEmployee, int start, int recordToGet, String addWhere) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        //System.out.println("nilai src.." + srcEmployee.getSalaryLevel().length());
        if (srcEmployee == null) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    /**
                     * Nambah Company
                     */
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_CURIER]
                    + // ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]+
                    ", COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + (srcEmployee.getSalaryLevel().length() > 0 ? ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] : " , HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL])
                    + //( srcEmployee.getSalaryLevel().length() >0 ?
                    //", LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    // :"" )+
                    ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]+
                    //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+
                    ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", RAC." + PstRace.fieldNames[PstRace.FLD_RACE_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " LEFT JOIN  " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN  " + PstPosition.TBL_HR_POSITION + " POST ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN  " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN  " + PstSection.TBL_HR_SECTION + " SEC ON " + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID])
                    + " LEFT JOIN  " + PstMarital.TBL_HR_MARITAL + " MAR " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " LEFT JOIN  " + PstRace.TBL_RACE + " RAC " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + " = RAC." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
                    + " LEFT JOIN  " + PstReligion.TBL_HR_RELIGION + " REL " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " LEFT JOIN  " + PstDivision.TBL_HR_DIVISION + " DIVS " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " LEFT JOIN  " + PstCompany.TBL_HR_COMPANY + " COMP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    //update by satrya 2012-11-14
                    //untuk education
                    + " LEFT JOIN  " + PstEducation.TBL_HR_EDUCATION + " AS EDU  ON EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID] + " = EDU." + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]
                    + " LEFT JOIN  " + PstEmpLanguage.TBL_HR_EMP_LANGUAGE + " AS LANG  ON EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = LANG." + PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            /*
             + " , " + PstCompany.TBL_HR_COMPANY + " COMP "
             + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
             + " , " + PstPosition.TBL_HR_POSITION + " POST "
             + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
             + " , " + PstSection.TBL_HR_SECTION + " SEC "
             + (srcEmployee.getSalaryLevel().length() > 0 ? " , " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " : " , " + PstLevel.TBL_HR_LEVEL + " HR_LEV ")
             + //( srcEmployee.getSalaryLevel().length() >0 ?
             //" , "+PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " : "")+
             //" , "+PstLocker.TBL_HR_LOCKER + " LOC "+
             " , " + PstMarital.TBL_HR_MARITAL + " MAR "
             + " , " + PstRace.TBL_RACE + " RAC "
             + " , " + PstReligion.TBL_HR_RELIGION + " REL "
             + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
             + " WHERE "
             + " ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
             + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
             + " OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +"=0"
             + " OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +" IS NULL )"
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
             + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
             + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
             + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
             + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
             + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
             + (srcEmployee.getSalaryLevel().length() > 0 ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID])
             + //( srcEmployee.getSalaryLevel().length() >0 ?
             //" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
             //" = LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] : "" )+
             " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
             + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
             + " = RAC." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
             + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
             + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
             * 
             */

            String whereClause = "";
            if ((srcEmployee.getName() != null) && (srcEmployee.getName().length() > 0)) {
                Vector vectName = logicParser(srcEmployee.getName());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getAddress() != null) && (srcEmployee.getAddress().length() > 0)) {
                Vector vectAddr = logicParser(srcEmployee.getAddress());
                if (vectAddr != null && vectAddr.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectAddr.size(); i++) {
                        String str = (String) vectAddr.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getEmpnumber() != null) && (srcEmployee.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcEmployee.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            //System.out.println("srcEmployee.getStartCommenc = "+srcEmployee.getStartCommenc());
            //System.out.println("srcEmployee.getEndCommenc = "+srcEmployee.getEndCommenc());

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
            }

            if ((srcEmployee.getResigned() == 1) && (srcEmployee.getStartResign() != null) && (srcEmployee.getEndResign() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartResign(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndResign(), "yyyy-MM-dd") + "' AND ";
            }
            if (srcEmployee.getCompanyId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcEmployee.getCompanyId() + " AND ";
            }
            if (srcEmployee.getDepartment() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcEmployee.getDepartment() + " AND ";
            }
            //System.out.println("department"+srcEmployee.getDepartment());

            if (srcEmployee.getDivisionId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcEmployee.getDivisionId() + " AND ";
            }

            if (srcEmployee.getPosition() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcEmployee.getPosition() + " AND ";
            }


             //update by satrya 2014-06-19 //Sementara karena di pepito tidak muncul
           // if (srcEmployee.getEmployeeIdLeaveConfig() != null && srcEmployee.getEmployeeIdLeaveConfig().length()>0) {
           //     whereClause = whereClause + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
           //             " IN(" + srcEmployee.getEmployeeIdLeaveConfig() +") AND ";
           // }
            if (srcEmployee.getSection() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcEmployee.getSection() + " AND ";
            }

            if (srcEmployee.getMaritalStatus() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + srcEmployee.getMaritalStatus() + " AND ";
            }

            if (srcEmployee.getRaceId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                        + " = " + srcEmployee.getRaceId() + " AND ";
            }

            if (!srcEmployee.isBirthdayChecked()) {
                System.out.println("Bithday checked : " + srcEmployee.isBirthdayChecked());
                System.out.println("Bithday : " + srcEmployee.getBirthday());
                System.out.println("Bithmon : " + srcEmployee.getBirthmonth());

                if (srcEmployee.getBirthday() != null) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthday().getMonth() + 1) + "' AND ";
                }

                if (srcEmployee.getBirthmonth() > 0) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthmonth()) + "' AND ";
                }
            }

            if (srcEmployee.getSalaryLevel().length() > 0) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + srcEmployee.getSalaryLevel() + "' AND ";
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }

            if (srcEmployee.getEmpCategory() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + srcEmployee.getEmpCategory() + " AND ";
            }

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian by religion,edited by Yunny
            if (srcEmployee.getReligion() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + srcEmployee.getReligion() + " AND ";
            }
            //---------------------
            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny
            if (srcEmployee.getGender() < 2) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + " = " + srcEmployee.getGender() + " AND ";
            }
            //----------------------------
            //update by satrya 2012-11-14
             /*if (srcEmployee.getEducation_id() !=0) {
             whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID]
             + " = " + srcEmployee.getEducation_id() + " AND ";
                
             }*/
            if (srcEmployee.getEducationIds() != null && srcEmployee.getEducationIds().size() > 0) {
                //sql = sql + " AND ";
                if ((Long) srcEmployee.getEducationIds().get(0) != 0) {
                    sql = sql + " AND ( ";
                    for (int i = 0; i < srcEmployee.getEducationIds().size(); i++) {
                        long lstr = (Long) srcEmployee.getEducationIds().get(i);
                        sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID]
                                + " = " + lstr + (i < (srcEmployee.getEducationIds().size() - 1) ? " OR " : "");
                    }
                    sql = sql + " ) ";
                }
            }
            if (srcEmployee.getBlood() != null && srcEmployee.getBlood().length() > 0) {
                //whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                //      + " = " +"\""+ srcEmployee.getBlood() +"\""+ " AND ";
                Vector vectNum = logicParser(srcEmployee.getBlood());
                String sBlood = (String) vectNum.get(0);
                if (!sBlood.equalsIgnoreCase("0")) {
                    if (vectNum != null && vectNum.size() > 0) {
                        whereClause = whereClause + " (";
                        for (int i = 0; i < vectNum.size(); i++) {
                            String str = (String) vectNum.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ") AND ";
                    }
                }
            }
            if (srcEmployee.getLanguage() != 0) {
                whereClause = whereClause + " LANG." + PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_LANGUAGE_ID]
                        + " = " + "\"" + srcEmployee.getLanguage() + "\"" + " AND ";

            }

            if (srcEmployee.getLevel() != 0) {
                whereClause = whereClause + " HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                        + " = '" + srcEmployee.getLevel() + "' AND ";
            }
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + addWhere + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            switch (srcEmployee.getOrderBy()) {
                case FrmSrcEmployee.ORDER_EMPLOYEE_NAME:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case FrmSrcEmployee.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;
                case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                case FrmSrcEmployee.ORDER_COMM_DATE:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
                    break;
                case FrmSrcEmployee.ORDER_COMPANY:
                    sql = sql + " ORDER BY COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                            + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                            + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                            + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                            + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            //System.out.println("\t SQL searchEmployee : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();
                Division division = new Division();
                EmpEducation empEducation = new EmpEducation();
                Race race = new Race();
                Company company = new Company();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                //khussu untuk Intimas,edited By Yunny
                employee.setCurier(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_CURIER]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);

                /* company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                 vect.add(company);*/

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                if (srcEmployee.getSalaryLevel().length() > 0) {
                    level.setLevel(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                } else {
                    level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                }

                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                //locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                //locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                race.setRaceName(rs.getString(PstRace.fieldNames[PstRace.FLD_RACE_NAME]));
                vect.add(race);

                company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                vect.add(company);


//                String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+""+sc;
//                String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
//                Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);
//
//                Education education = new Education();
//                //System.out.println("vedu size"+vedu.size());
//                if(vedu!=null && vedu.size()>0)
//                {
//                empEducation = (EmpEducation)vedu.get(0);
//                try{
//                education = PstEducation.fetchExc(empEducation.getEducationId());
//                }catch(Exception e){
//                    System.out.println("Exception hr_education in sessEmployee"+e);
//                    }
//                }
//                vect.add(empEducation);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector searchEmployee(SrcEmployee srcEmployee, int start, int recordToGet, long periodOid) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("nilai src.." + srcEmployee.getSalaryLevel().length());
        if (srcEmployee == null) {
            return new Vector(1, 1);
        }

        try {

            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    /**
                     * Menambah Company
                     */
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_CURIER]
                    + ", COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + (srcEmployee.getSalaryLevel().length() > 0 ? ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] : " , HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL])
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", RAC." + PstRace.fieldNames[PstRace.FLD_RACE_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    + " , " + PstPosition.TBL_HR_POSITION + " POST "
                    + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                    + " , " + PstSection.TBL_HR_SECTION + " SEC "
                    + (srcEmployee.getSalaryLevel().length() > 0 ? " , " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " : " , " + PstLevel.TBL_HR_LEVEL + " HR_LEV ")
                    + " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                    + " , " + PstRace.TBL_RACE + " RAC "
                    + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                    + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0 ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID])
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + " = RAC." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String whereClause = "";
            if ((srcEmployee.getName() != null) && (srcEmployee.getName().length() > 0)) {
                Vector vectName = logicParser(srcEmployee.getName());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getAddress() != null) && (srcEmployee.getAddress().length() > 0)) {
                Vector vectAddr = logicParser(srcEmployee.getAddress());
                if (vectAddr != null && vectAddr.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectAddr.size(); i++) {
                        String str = (String) vectAddr.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getEmpnumber() != null) && (srcEmployee.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcEmployee.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
            }

            if ((srcEmployee.getResigned() == 1) && (srcEmployee.getStartResign() != null) && (srcEmployee.getEndResign() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartResign(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndResign(), "yyyy-MM-dd") + "' AND ";
            }

            if (srcEmployee.getCompanyId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcEmployee.getCompanyId() + " AND ";
            }

            if (srcEmployee.getDepartment() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcEmployee.getDepartment() + " AND ";
            }

            if (srcEmployee.getDivisionId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcEmployee.getDivisionId() + " AND ";
            }

            if (srcEmployee.getPosition() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcEmployee.getPosition() + " AND ";
            }


            if (srcEmployee.getSection() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcEmployee.getSection() + " AND ";
            }

            if (srcEmployee.getMaritalStatus() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + srcEmployee.getMaritalStatus() + " AND ";
            }

            if (srcEmployee.getRaceId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                        + " = " + srcEmployee.getRaceId() + " AND ";
            }

            if (!srcEmployee.isBirthdayChecked()) {
                System.out.println("Bithday checked : " + srcEmployee.isBirthdayChecked());
                System.out.println("Bithday : " + srcEmployee.getBirthday());
                System.out.println("Bithmon : " + srcEmployee.getBirthmonth());

                if (srcEmployee.getBirthday() != null) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthday().getMonth() + 1) + "' AND ";
                }

                if (srcEmployee.getBirthmonth() > 0) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthmonth()) + "' AND ";
                }
            }

            if (srcEmployee.getSalaryLevel().length() > 0) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + srcEmployee.getSalaryLevel() + "' AND ";
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }

            if (srcEmployee.getEmpCategory() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + srcEmployee.getEmpCategory() + " AND ";
            }

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian by religion,edited by Yunny
            if (srcEmployee.getReligion() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + srcEmployee.getReligion() + " AND ";
            }
            //---------------------
            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny
            if (srcEmployee.getGender() < 2) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + " = " + srcEmployee.getGender() + " AND ";
            }
            //----------------------------

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            switch (srcEmployee.getOrderBy()) {
                case FrmSrcEmployee.ORDER_EMPLOYEE_NAME:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case FrmSrcEmployee.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;
                case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                case FrmSrcEmployee.ORDER_COMM_DATE:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
                    break;
                case FrmSrcEmployee.ORDER_COMPANY:
                    sql = sql + " ORDER BY COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                            + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                            + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                            + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                            + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL searchEmployee : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();
                Division division = new Division();
                EmpEducation empEducation = new EmpEducation();
                Race race = new Race();
                Company company = new Company();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                /**
                 * Menambah Company
                 */
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                //khussu untuk Intimas,edited By Yunny
                employee.setCurier(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_CURIER]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);



                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                if (srcEmployee.getSalaryLevel().length() > 0) {
                    level.setLevel(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                } else {
                    level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                }

                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                race.setRaceName(rs.getString(PstRace.fieldNames[PstRace.FLD_RACE_NAME]));
                vect.add(race);

                company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                vect.add(company);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector searchEmployeeAndFamily(SrcEmployee srcEmployee, int start, int recordToGet) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("nilai src.." + srcEmployee.getSalaryLevel().length());
        if (srcEmployee == null) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_CURIER]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + // ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]+
                    ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    : " , HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL])
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]+
                    //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+
                    ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", RAC." + PstRace.fieldNames[PstRace.FLD_RACE_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstCompany.TBL_HR_COMPANY + " COMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    + " , " + PstPosition.TBL_HR_POSITION + " POST "
                    + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                    + " , " + PstSection.TBL_HR_SECTION + " SEC "
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " , " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " : " , " + PstLevel.TBL_HR_LEVEL + " HR_LEV ")
                    + //" , "+PstLocker.TBL_HR_LOCKER + " LOC "+
                    " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                    + " , " + PstRace.TBL_RACE + " RAC "
                    + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                    + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID])
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + " = RAC." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String whereClause = "";
            if ((srcEmployee.getName() != null) && (srcEmployee.getName().length() > 0)) {
                Vector vectName = logicParser(srcEmployee.getName());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getAddress() != null) && (srcEmployee.getAddress().length() > 0)) {
                Vector vectAddr = logicParser(srcEmployee.getAddress());
                if (vectAddr != null && vectAddr.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectAddr.size(); i++) {
                        String str = (String) vectAddr.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getEmpnumber() != null) && (srcEmployee.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcEmployee.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            //System.out.println("srcEmployee.getStartCommenc = "+srcEmployee.getStartCommenc());
            //System.out.println("srcEmployee.getEndCommenc = "+srcEmployee.getEndCommenc());

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
            }

            if (srcEmployee.getCompanyId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcEmployee.getCompanyId() + " AND ";
            }

            if (srcEmployee.getDepartment() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcEmployee.getDepartment() + " AND ";
            }
            //System.out.println("department"+srcEmployee.getDepartment());

            if (srcEmployee.getDivisionId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcEmployee.getDivisionId() + " AND ";
            }

            if (srcEmployee.getPosition() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcEmployee.getPosition() + " AND ";
            }


            if (srcEmployee.getSection() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcEmployee.getSection() + " AND ";
            }

            if (srcEmployee.getMaritalStatus() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + srcEmployee.getMaritalStatus() + " AND ";
            }

            if (srcEmployee.getRaceId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                        + " = " + srcEmployee.getRaceId() + " AND ";
            }

            if (!srcEmployee.isBirthdayChecked()) {
                System.out.println("Bithday checked : " + srcEmployee.isBirthdayChecked());
                System.out.println("Bithday : " + srcEmployee.getBirthday());
                System.out.println("Bithmon : " + srcEmployee.getBirthmonth());

                if (srcEmployee.getBirthday() != null) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthday().getMonth() + 1) + "' AND ";
                }

                if (srcEmployee.getBirthmonth() > 0) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthmonth()) + "' AND ";
                }
            }

            if (srcEmployee.getSalaryLevel().length() > 0) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + srcEmployee.getSalaryLevel() + "' AND ";
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }

            if (srcEmployee.getEmpCategory() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + srcEmployee.getEmpCategory() + " AND ";
            }

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian by religion,edited by Yunny
            if (srcEmployee.getReligion() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + srcEmployee.getReligion() + " AND ";
            }
            //---------------------
            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny
            if (srcEmployee.getGender() < 2) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + " = " + srcEmployee.getGender() + " AND ";
            }
            //----------------------------

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            switch (srcEmployee.getOrderBy()) {
                case FrmSrcEmployee.ORDER_EMPLOYEE_NAME:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case FrmSrcEmployee.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;
                case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                case FrmSrcEmployee.ORDER_COMM_DATE:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL searchEmployee : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Division division = new Division();
                //EmpEducation empEducation = new EmpEducation();
                Locker locker = new Locker();
                Race race = new Race();
                FamilyMemberList famList = null;//new FamilyMemberList();
                Company company = new Company();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                //khussu untuk Intimas,edited By Yunny
                employee.setCurier(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_CURIER]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                if (srcEmployee.getSalaryLevel().length() > 0) {
                    level.setLevel(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                } else {
                    level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                }
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                //locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                //locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                try {
                    String famWhere = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID] + " = '"
                            + employee.getOID() + "' ";
                    String famOrder = PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE];
                    Vector vectFam = PstFamilyMember.list(0, 10, famWhere, famOrder);
                    if (vectFam != null && vectFam.size() > 0) {
                        famList = new FamilyMemberList();
                        for (int ifam = 0; ifam < vectFam.size(); ifam++) {
                            FamilyMember famMem = (FamilyMember) vectFam.get(ifam);
                            /**
                             * Ari Merubah kondisi if
                             */
                            //if (famMem != null && ((PstFamilyMember.relationValue[PstFamilyMember.REL_WIFE].equalsIgnoreCase(famMem.getRelationship())
                            //|| (PstFamilyMember.relationValue[PstFamilyMember.REL_HUSBAND].equalsIgnoreCase(famMem.getRelationship()))))) {
                            if (famMem != null && famMem.getRelationType() == PstFamRelation.SPOUSE) {
                                famList.setSpouse(famMem);
                            } else {
                                famList.addChild(famMem);
                            }
                            /*} else if(famMem != null && (famMem.getRelationship().equalsIgnoreCase("Wife") || famMem.getRelationship().equalsIgnoreCase("Husband") )){
                             famList.addChild(famMem);
                             } else {
                             famList.addOther(famMem);
                             }*/
                        }
                    }


                } catch (Exception exc) {
                    System.out.println("Exc : get family member " + exc);
                }
                employee.setFamilyMemberList(famList);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                race.setRaceName(rs.getString(PstRace.fieldNames[PstRace.FLD_RACE_NAME]));
                vect.add(race);

                company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                vect.add(company);

                /*String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID];
                 String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
                 Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);

                 Education education = new Education();
                 System.out.println("vedu size"+vedu.size());
                 if(vedu!=null && vedu.size()>0)
                 {
                 empEducation = (EmpEducation)vedu.get(0);
                 try{
                 education = PstEducation.fetchExc(empEducation.getEducationId());
                 }catch(Exception e){;}
                 }
                 vect.add(empEducation);*/

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

        public static Vector searchEmployeeWithDocList(SrcEmployee srcEmployee, int start, int recordToGet, String addWhere, String ObjectName,long empDocId ) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        //System.out.println("nilai src.." + srcEmployee.getSalaryLevel().length());
        if (srcEmployee == null) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    /**
                     * Nambah Company
                     */
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_CURIER]
                    
                    //add by priska 20150902 EMPDOCLIST
                    + ", EMPDOCLIST. *" 
                    
                    + // ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]+
                    ", COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + (srcEmployee.getSalaryLevel().length() > 0 ? ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] : " , HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL])
                    + //( srcEmployee.getSalaryLevel().length() >0 ?
                    //", LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    // :"" )+
                    ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]+
                    //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+
                    ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", RAC." + PstRace.fieldNames[PstRace.FLD_RACE_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " LEFT JOIN  " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN  " + PstPosition.TBL_HR_POSITION + " POST ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN  " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN  " + PstSection.TBL_HR_SECTION + " SEC ON " + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID])
                    + " LEFT JOIN  " + PstMarital.TBL_HR_MARITAL + " MAR " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " LEFT JOIN  " + PstRace.TBL_RACE + " RAC " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + " = RAC." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
                    + " LEFT JOIN  " + PstReligion.TBL_HR_RELIGION + " REL " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " LEFT JOIN  " + PstDivision.TBL_HR_DIVISION + " DIVS " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " LEFT JOIN  " + PstCompany.TBL_HR_COMPANY + " COMP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    //update by satrya 2012-11-14
                    //untuk education
                    + " LEFT JOIN  " + PstEducation.TBL_HR_EDUCATION + " AS EDU  ON EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID] + " = EDU." + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]
                    + " LEFT JOIN  " + PstEmpLanguage.TBL_HR_EMP_LANGUAGE + " AS LANG  ON EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = LANG." + PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]
                    
                    //add by priska 20150902
                    + " LEFT JOIN  " + PstEmpDocList.TBL_HR_EMP_DOC_LIST + " AS EMPDOCLIST  ON (EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = EMPDOCLIST." + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMPLOYEE_ID]
                    + " AND EMPDOCLIST." + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME]+" = \""+ ObjectName+"\"" 
                    + " AND EMPDOCLIST." + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID]+" = "+ empDocId 
                    +")"
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            /*
             + " , " + PstCompany.TBL_HR_COMPANY + " COMP "
             + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
             + " , " + PstPosition.TBL_HR_POSITION + " POST "
             + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
             + " , " + PstSection.TBL_HR_SECTION + " SEC "
             + (srcEmployee.getSalaryLevel().length() > 0 ? " , " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " : " , " + PstLevel.TBL_HR_LEVEL + " HR_LEV ")
             + //( srcEmployee.getSalaryLevel().length() >0 ?
             //" , "+PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " : "")+
             //" , "+PstLocker.TBL_HR_LOCKER + " LOC "+
             " , " + PstMarital.TBL_HR_MARITAL + " MAR "
             + " , " + PstRace.TBL_RACE + " RAC "
             + " , " + PstReligion.TBL_HR_RELIGION + " REL "
             + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
             + " WHERE "
             + " ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
             + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
             + " OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +"=0"
             + " OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +" IS NULL )"
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
             + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
             + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
             + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
             + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
             + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
             + (srcEmployee.getSalaryLevel().length() > 0 ? " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID])
             + //( srcEmployee.getSalaryLevel().length() >0 ?
             //" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
             //" = LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] : "" )+
             " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
             + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
             + " = RAC." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
             + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
             + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
             * 
             */

            String whereClause = "";
            if ((srcEmployee.getName() != null) && (srcEmployee.getName().length() > 0)) {
                Vector vectName = logicParser(srcEmployee.getName());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getAddress() != null) && (srcEmployee.getAddress().length() > 0)) {
                Vector vectAddr = logicParser(srcEmployee.getAddress());
                if (vectAddr != null && vectAddr.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectAddr.size(); i++) {
                        String str = (String) vectAddr.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getEmpnumber() != null) && (srcEmployee.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcEmployee.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            //System.out.println("srcEmployee.getStartCommenc = "+srcEmployee.getStartCommenc());
            //System.out.println("srcEmployee.getEndCommenc = "+srcEmployee.getEndCommenc());

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
            }

            if ((srcEmployee.getResigned() == 1) && (srcEmployee.getStartResign() != null) && (srcEmployee.getEndResign() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartResign(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndResign(), "yyyy-MM-dd") + "' AND ";
            }
            if (srcEmployee.getCompanyId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcEmployee.getCompanyId() + " AND ";
            }
            if (srcEmployee.getDepartment() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcEmployee.getDepartment() + " AND ";
            }
            //System.out.println("department"+srcEmployee.getDepartment());

            if (srcEmployee.getDivisionId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcEmployee.getDivisionId() + " AND ";
            }

            if (srcEmployee.getPosition() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcEmployee.getPosition() + " AND ";
            }


             //update by satrya 2014-06-19
            if (srcEmployee.getEmployeeIdLeaveConfig() != null && srcEmployee.getEmployeeIdLeaveConfig().length()>0) {
                whereClause = whereClause + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " IN(" + srcEmployee.getEmployeeIdLeaveConfig() +") AND ";
            }
            if (srcEmployee.getSection() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcEmployee.getSection() + " AND ";
            }

            if (srcEmployee.getMaritalStatus() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + srcEmployee.getMaritalStatus() + " AND ";
            }

            if (srcEmployee.getRaceId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                        + " = " + srcEmployee.getRaceId() + " AND ";
            }

            if (!srcEmployee.isBirthdayChecked()) {
                System.out.println("Bithday checked : " + srcEmployee.isBirthdayChecked());
                System.out.println("Bithday : " + srcEmployee.getBirthday());
                System.out.println("Bithmon : " + srcEmployee.getBirthmonth());

                if (srcEmployee.getBirthday() != null) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthday().getMonth() + 1) + "' AND ";
                }

                if (srcEmployee.getBirthmonth() > 0) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthmonth()) + "' AND ";
                }
            }

            if (srcEmployee.getSalaryLevel().length() > 0) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + srcEmployee.getSalaryLevel() + "' AND ";
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }

            if (srcEmployee.getEmpCategory() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + srcEmployee.getEmpCategory() + " AND ";
            }

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian by religion,edited by Yunny
            if (srcEmployee.getReligion() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + srcEmployee.getReligion() + " AND ";
            }
            //---------------------
            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny
            if (srcEmployee.getGender() < 2) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + " = " + srcEmployee.getGender() + " AND ";
            }
            //----------------------------
            //update by satrya 2012-11-14
             /*if (srcEmployee.getEducation_id() !=0) {
             whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID]
             + " = " + srcEmployee.getEducation_id() + " AND ";
                
             }*/
            if (srcEmployee.getEducationIds() != null && srcEmployee.getEducationIds().size() > 0) {
                //sql = sql + " AND ";
                if ((Long) srcEmployee.getEducationIds().get(0) != 0) {
                    sql = sql + " AND ( ";
                    for (int i = 0; i < srcEmployee.getEducationIds().size(); i++) {
                        long lstr = (Long) srcEmployee.getEducationIds().get(i);
                        sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID]
                                + " = " + lstr + (i < (srcEmployee.getEducationIds().size() - 1) ? " OR " : "");
                    }
                    sql = sql + " ) ";
                }
            }
            if (srcEmployee.getBlood() != null && srcEmployee.getBlood().length() > 0) {
                //whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                //      + " = " +"\""+ srcEmployee.getBlood() +"\""+ " AND ";
                Vector vectNum = logicParser(srcEmployee.getBlood());
                String sBlood = (String) vectNum.get(0);
                if (!sBlood.equalsIgnoreCase("0")) {
                    if (vectNum != null && vectNum.size() > 0) {
                        whereClause = whereClause + " (";
                        for (int i = 0; i < vectNum.size(); i++) {
                            String str = (String) vectNum.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ") AND ";
                    }
                }
            }
            if (srcEmployee.getLanguage() != 0) {
                whereClause = whereClause + " LANG." + PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_LANGUAGE_ID]
                        + " = " + "\"" + srcEmployee.getLanguage() + "\"" + " AND ";

            }

            if (srcEmployee.getLevel() != 0) {
                whereClause = whereClause + " HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                        + " = '" + srcEmployee.getLevel() + "' AND ";
            }
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + addWhere + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            switch (srcEmployee.getOrderBy()) {
                case FrmSrcEmployee.ORDER_EMPLOYEE_NAME:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case FrmSrcEmployee.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;
                case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                case FrmSrcEmployee.ORDER_COMM_DATE:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
                    break;
                case FrmSrcEmployee.ORDER_COMPANY:
                    sql = sql + " ORDER BY COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                            + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                            + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                            + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                            + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            //System.out.println("\t SQL searchEmployee : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();
                Division division = new Division();
                EmpEducation empEducation = new EmpEducation();
                Race race = new Race();
                Company company = new Company();
                
                EmpDocList empDocList = new EmpDocList();
                empDocList.setOID(rs.getLong(PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_LIST_ID]));
                empDocList.setEmp_doc_id(rs.getLong(PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID]));
                empDocList.setEmployee_id(rs.getLong(PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMPLOYEE_ID]));
                empDocList.setAssign_as(rs.getString(PstEmpDocList.fieldNames[PstEmpDocList.FLD_ASSIGN_AS]));
                empDocList.setJob_desc(rs.getString(PstEmpDocList.fieldNames[PstEmpDocList.FLD_JOB_DESC]));
                empDocList.setObject_name(rs.getString(PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME]));
            
            
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                //khussu untuk Intimas,edited By Yunny
                employee.setCurier(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_CURIER]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);

                /* company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                 vect.add(company);*/

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                if (srcEmployee.getSalaryLevel().length() > 0) {
                    level.setLevel(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                } else {
                    level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                }

                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                //locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                //locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                race.setRaceName(rs.getString(PstRace.fieldNames[PstRace.FLD_RACE_NAME]));
                vect.add(race);

                company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                vect.add(company);

                vect.add(empDocList);

//                String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+""+sc;
//                String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
//                Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);
//
//                Education education = new Education();
//                //System.out.println("vedu size"+vedu.size());
//                if(vedu!=null && vedu.size()>0)
//                {
//                empEducation = (EmpEducation)vedu.get(0);
//                try{
//                education = PstEducation.fetchExc(empEducation.getEducationId());
//                }catch(Exception e){
//                    System.out.println("Exception hr_education in sessEmployee"+e);
//                    }
//                }
//                vect.add(empEducation);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }
    
    public static Vector listEmployeePayroll(long idDepartment, long idDivision, long idSection, String name, String payrollNum, int statusPayroll, int dataStatus) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("dataStatus" + dataStatus);
        if (idDepartment == 0 && idDivision == 0 && idSection == 0 && name == null && payrollNum == null && statusPayroll == 0 && dataStatus == 0) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_FOR_TAX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_TAX_REG_NR]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", PAY." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]
                    + ", PAY." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_PAY_EMP_LEVEL_ID]
                    + ", PAY." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_MEAL_ALLOWANCE]
                    + ", PAY." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]
                    + " FROM "
                    + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " PAY "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = PAY." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN " + PstPosition.TBL_HR_POSITION + " POST ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " SEC ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " LEV ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " LEFT JOIN " + PstMarital.TBL_HR_MARITAL + " MAR ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " LEFT JOIN " + PstReligion.TBL_HR_RELIGION + " REL ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " LEFT JOIN " + PstDivision.TBL_HR_DIVISION + " DIVS ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " WHERE (1=1) ";
            String whereClause = "";

            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment + " AND ";
            }
            //System.out.println("department"+srcEmployee.getDepartment());

            if (idDivision != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + idDivision + " AND ";
            }

            if (idSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + idSection + " AND ";
            }

            if ((name != null) && (name.length() > 0)) {
                Vector vectName = logicParser(name);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((payrollNum != null) && (payrollNum.length() > 0)) {
                Vector vectNum = logicParser(payrollNum);
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if (statusPayroll < 2) {
                if (statusPayroll == 0) {
                    whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                            + " = " + statusPayroll + " AND ";
                }
                if (statusPayroll == 1) {
                    Date now = new Date();
                    int monthNow = now.getMonth() + 1;
                    int yearNow = now.getYear() + 1900;
                    whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                            + " = " + statusPayroll + " AND ";
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                            + " Between '" + yearNow + "-" + monthNow + "-01'"
                            + " AND '" + yearNow + "-" + monthNow + "-31' AND";
                }
            }

            if (dataStatus < 2) {
                if (dataStatus == 0) {
                    whereClause += " PAY." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]
                            + " = " + dataStatus + " AND ";
                }
                if (dataStatus == 1) {
                    Date now = new Date();
                    int monthNow = now.getMonth() + 1;
                    int yearNow = now.getYear() + 1900;
                    whereClause += " PAY." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]
                            + " = " + dataStatus + " AND ";
                }
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            //sql = sql + " GROUP BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL searchEmployee1 : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();
                Division division = new Division();
                EmpEducation empEducation = new EmpEducation();
                PayEmpLevel payEmpLevel = new PayEmpLevel();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                //khussu untuk Intimas,edited By Yunny
                employee.setAddressForTax(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_FOR_TAX]));
                employee.setResigned(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                employee.setTaxRegNr(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_TAX_REG_NR]));

                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                //locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                //locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                payEmpLevel.setStatusData(rs.getInt(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]));
                payEmpLevel.setOID(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_PAY_EMP_LEVEL_ID]));
                payEmpLevel.setMealAllowance(rs.getInt(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_MEAL_ALLOWANCE]));
                payEmpLevel.setOvtIdxType(rs.getInt(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]));
                vect.add(payEmpLevel);

                /*String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID];
                 String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
                 Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);

                 Education education = new Education();
                 System.out.println("vedu size"+vedu.size());
                 if(vedu!=null && vedu.size()>0)
                 {
                 empEducation = (EmpEducation)vedu.get(0);
                 try{
                 education = PstEducation.fetchExc(empEducation.getEducationId());
                 }catch(Exception e){;}
                 }
                 vect.add(empEducation);*/

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector listEmployeePayrollBlank(long idDepartment, long idDivision, long idSection, String name, String payrollNum, int statusPayroll) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("idDepartment" + idDepartment);
        if (idDepartment == 0 && idDivision == 0 && idSection == 0 && name == null && payrollNum == null && statusPayroll == 0) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_FOR_TAX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_TAX_REG_NR]
                    + // ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]+
                    ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]+
                    //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+
                    ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    /* update by satrya 2014-03-26 karena ada employee yg tdk muncul + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                     + " , " + PstPosition.TBL_HR_POSITION + " POST "
                     + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                     + " , " + PstSection.TBL_HR_SECTION + " SEC "
                     + " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                     + //" , "+PstLocker.TBL_HR_LOCKER + " LOC "+
                     " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                     + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                     + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "*/
                    + " INNER JOIN  " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT   ON DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " INNER JOIN  " + PstPosition.TBL_HR_POSITION + " POST  ON POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " INNER JOIN  " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT  ON EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN  " + PstSection.TBL_HR_SECTION + " SEC   ON SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " INNER JOIN  " + PstLevel.TBL_HR_LEVEL + " LEV    ON LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " INNER JOIN  " + PstMarital.TBL_HR_MARITAL + " MAR  ON MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " REL   ON REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " DIVS   ON DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " WHERE ";

            /* update by satrya 2014-03-26 + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
             + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
             + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
             + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
             + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]                    
             +( idSection==0?"":(" AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
             + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] ) )
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
             + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
             + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
             + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
             + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
             + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID];*/


            String whereClause = "";

            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment + " AND ";
            }
            //System.out.println("department"+srcEmployee.getDepartment());

            if (idDivision != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + idDivision + " AND ";
            }

            if (idSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + idSection + " AND ";
            }

            if ((name != null) && (name.length() > 0)) {
                Vector vectName = logicParser(name);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((payrollNum != null) && (payrollNum.length() > 0)) {
                Vector vectNum = logicParser(payrollNum);
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if (statusPayroll < 2) {
                if (statusPayroll == 0) {
                    whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                            + " = " + statusPayroll + " AND ";
                }
                if (statusPayroll == 1) {
                    Date now = new Date();
                    int monthNow = now.getMonth() + 1;
                    int yearNow = now.getYear() + 1900;
                    whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                            + " = " + statusPayroll + " AND ";
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                            + " Between '" + yearNow + "-" + monthNow + "-01'"
                            + " AND '" + yearNow + "-" + monthNow + "-31' AND";
                }
            }

            /*if (whereClause != null && whereClause.length() > 0) {
             whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
             sql = sql + whereClause;
             }*/
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + "(1=1)";
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            /*switch(srcEmployee.getOrderBy()){
             case FrmSrcEmployee.ORDER_EMPLOYEE_NAME :
             sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] ;
             break;
             case FrmSrcEmployee.ORDER_DEPARTMENT:
             sql = sql + " ORDER BY DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] ;
             break;
             case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
             sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] ;
             break;
             case FrmSrcEmployee.ORDER_COMM_DATE:
             sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] ;
             break;
             default:
             sql = sql + "";
             }*/


            //sql = sql + " LIMIT " + start + "," + recordToGet;

            //System.out.println("\t SQL searchEmployeeBlank : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();
                Division division = new Division();
                EmpEducation empEducation = new EmpEducation();
                PayEmpLevel payEmpLevel = new PayEmpLevel();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                //khussu untuk Intimas,edited By Yunny
                employee.setAddressForTax(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_FOR_TAX]));
                employee.setResigned(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                employee.setTaxRegNr(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_TAX_REG_NR]));

                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                //locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                //locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                /*String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID];
                 String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
                 Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);

                 Education education = new Education();
                 System.out.println("vedu size"+vedu.size());
                 if(vedu!=null && vedu.size()>0)
                 {
                 empEducation = (EmpEducation)vedu.get(0);
                 try{
                 education = PstEducation.fetchExc(empEducation.getEducationId());
                 }catch(Exception e){;}
                 }
                 vect.add(empEducation);*/

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static int countEmployee(SrcEmployee srcEmployee) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcEmployee == null) {
            return 0;
        }

        try {
            String sql = " SELECT COUNT(DISTINCT(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "))"
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " LEFT JOIN  " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN  " + PstPosition.TBL_HR_POSITION + " POST ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN  " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT ON " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN  " + PstSection.TBL_HR_SECTION + " SEC ON " + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + (srcEmployee.getSalaryLevel().length() > 0
                    ? " LEFT JOIN  " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    : " " + " LEFT JOIN " + PstLevel.TBL_HR_LEVEL + " HR_LEV " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID])
                    + " LEFT JOIN  " + PstMarital.TBL_HR_MARITAL + " MAR " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " LEFT JOIN  " + PstRace.TBL_RACE + " RAC " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                    + " = RAC." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
                    + " LEFT JOIN  " + PstReligion.TBL_HR_RELIGION + " REL " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " LEFT JOIN  " + PstDivision.TBL_HR_DIVISION + " DIVS " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " LEFT JOIN  " + PstCompany.TBL_HR_COMPANY + " COMP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    //update by satrya 2012-11-14
                    //untuk education
                    + " LEFT JOIN  " + PstEducation.TBL_HR_EDUCATION + " AS EDU  ON EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID] + " = EDU." + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]
                    + " LEFT JOIN  " + PstEmpLanguage.TBL_HR_EMP_LANGUAGE + " AS LANG  ON EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = LANG." + PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]
                    + " WHERE "
                    + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String whereClause = "";
            if ((srcEmployee.getName() != null) && (srcEmployee.getName().length() > 0)) {
                Vector vectName = logicParser(srcEmployee.getName());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getAddress() != null) && (srcEmployee.getAddress().length() > 0)) {
                Vector vectAddr = logicParser(srcEmployee.getAddress());
                if (vectAddr != null && vectAddr.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectAddr.size(); i++) {
                        String str = (String) vectAddr.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getEmpnumber() != null) && (srcEmployee.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcEmployee.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
            }

            if ((srcEmployee.getResigned() == 1) && (srcEmployee.getStartResign() != null) && (srcEmployee.getEndResign() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartResign(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndResign(), "yyyy-MM-dd") + "' AND ";
            }

            if (srcEmployee.getCompanyId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcEmployee.getCompanyId() + " AND ";
            }

            if (srcEmployee.getDepartment() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcEmployee.getDepartment() + " AND ";
            }

            if (srcEmployee.getDivisionId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcEmployee.getDivisionId() + " AND ";
            }


            if (srcEmployee.getPosition() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcEmployee.getPosition() + " AND ";
            }


            if (srcEmployee.getSection() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcEmployee.getSection() + " AND ";
            }
            
            //update by satrya 2014-06-19  //Sementara karena di pepito tidak muncul
            //if (srcEmployee.getEmployeeIdLeaveConfig() != null && srcEmployee.getEmployeeIdLeaveConfig().length()>0) {
            //    whereClause = whereClause + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
            //            " IN(" + srcEmployee.getEmployeeIdLeaveConfig() +") AND ";
            //}

            if (srcEmployee.getMaritalStatus() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + srcEmployee.getMaritalStatus() + " AND ";
            }

            if (srcEmployee.getSalaryLevel().length() > 0) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + srcEmployee.getSalaryLevel() + "' AND ";
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }

            if (srcEmployee.getEmpCategory() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + srcEmployee.getEmpCategory() + " AND ";
            }
            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by religion,edited by Yunny
            if (srcEmployee.getReligion() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + srcEmployee.getReligion() + " AND ";
            }
            //---------------------
            if (srcEmployee.getRaceId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                        + " = " + srcEmployee.getRaceId() + " AND ";
            }

            if (!srcEmployee.isBirthdayChecked()) {
                if (srcEmployee.getBirthday() != null) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthday().getMonth() + 1) + "' AND ";
                }

                if (srcEmployee.getBirthmonth() > 0) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthmonth()) + "' AND ";
                }
            }

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny
            if (srcEmployee.getGender() < 2) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + " = " + srcEmployee.getGender() + " AND ";
            }
            //----------------------------
            //update by satrya 2012-11-14

            if (srcEmployee.getEducationIds() != null && srcEmployee.getEducationIds().size() > 0) {
                ///sql = sql + " AND ";
                if ((Long) srcEmployee.getEducationIds().get(0) != 0) {
                    sql = sql + " AND ( ";
                    for (int i = 0; i < srcEmployee.getEducationIds().size(); i++) {
                        long lstr = (Long) srcEmployee.getEducationIds().get(i);
                        sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EDUCATION_ID]
                                + " = " + lstr + (i < (srcEmployee.getEducationIds().size() - 1) ? " OR " : "");
                    }
                    sql = sql + " )";
                }
            }
            if (srcEmployee.getBlood() != null && srcEmployee.getBlood().length() > 0) {
                if (!srcEmployee.getBlood().equalsIgnoreCase("0")) {
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                            + " = " + "\"" + srcEmployee.getBlood() + "\"" + " AND ";
                }
            }
            if (srcEmployee.getLanguage() != 0) {
                whereClause = whereClause + " LANG." + PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]
                        + " = " + "\"" + srcEmployee.getLanguage() + "\"" + " AND ";
            }
            //update by satrya 2012-12-23
            if (srcEmployee.getLevel() != 0) {
                whereClause = whereClause + " HR_LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                        + " = '" + srcEmployee.getLevel() + "' AND ";
            }
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            if (srcEmployee.getAddWhere() != null && srcEmployee.getAddWhere().length() > 1) {
                sql = sql + " AND " + srcEmployee.getAddWhere();
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while (rs.next()) {
                num = rs.getInt(1);
            }

            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public static Vector getBirthdayReminder() {
        Vector vBirthday = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        try {
            Calendar cal = new GregorianCalendar();
            if (cal.getActualMaximum(cal.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR) < 3) {
                sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                        + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                        + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                        + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                        + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                        + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                        + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                        + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                        + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                        + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                        + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                        + " , " + PstPosition.TBL_HR_POSITION + " POST "
                        + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                        + " , " + PstSection.TBL_HR_SECTION + " SEC "
                        + " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                        + " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                        + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                        + " WHERE "
                        + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                        + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                        + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 "
                        + " AND (DAYOFYEAR(NOW()) - 3 < DAYOFYEAR(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") ";

                sql += " OR DAYOFYEAR(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") < 6) ";
                sql += " ORDER BY MONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") DESC, DAYOFMONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") + MONTH("
                        + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") * 100, "
                        + //" DAYOFYEAR("+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+"), " +
                        PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + ", "
                        + " TO_DAYS(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") ";
            } else {
                sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                        + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                        + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                        + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                        + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                        + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                        + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                        + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                        + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                        + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                        + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                        + " , " + PstPosition.TBL_HR_POSITION + " POST "
                        + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                        + " , " + PstSection.TBL_HR_SECTION + " SEC "
                        + " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                        + " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                        + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                        + " WHERE "
                        + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                        + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                        + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 "
                        + " AND DAYOFYEAR(NOW()) - 3 < DAYOFYEAR(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") ";

                sql += " AND DAYOFYEAR(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") < (DAYOFYEAR(NOW()) + 8) ";
                sql += " ORDER BY DAYOFMONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") + MONTH("
                        + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") * 100, "
                        + //" DAYOFYEAR("+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+"), " +
                        PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + ", "
                        + " TO_DAYS(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") ";
            }

            System.out.println("\t SQL getBirthdayReminder : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));

                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));

                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                vect.add(marital);

                vBirthday.add(vect);
            }

            return vBirthday;
        } catch (Exception e) {
            System.out.println("\t Exception on getBirthdayReminder : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    /**
     * create by satrya 2014-03-22 keterangan: untuk melihat yg ulang tahun
     * bulan ini
     *
     * @return
     */
    public static Vector getBirthdayReminderMonth() {
        Vector vBirthday = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        try {

            sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " POST ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN  " + PstSection.TBL_HR_SECTION + " SEC ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN  " + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " LEFT JOIN  " + PstMarital.TBL_HR_MARITAL + " MAR ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID] + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " LEFT JOIN  " + PstReligion.TBL_HR_RELIGION + " REL  ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    //+ " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    //+ " , " + PstPosition.TBL_HR_POSITION + " POST "
                    //+ " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                    //+ " , " + PstSection.TBL_HR_SECTION + " SEC "
                    //+ " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                    //+ " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                    //+ " , " + PstReligion.TBL_HR_RELIGION + " REL "
                    + " WHERE "
                    //+ " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    //+ " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+ " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+ " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+ " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]+ " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]+ " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 "
                    + " AND DATE_FORMAT(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ",'%m')=\"" + Formater.formatDate(new Date(), "MM") + "\"";


            sql += " ORDER BY DAYOFMONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") + MONTH("
                    + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") * 100, "
                    + //" DAYOFYEAR("+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+"), " +
                    PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + ", "
                    + " TO_DAYS(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") ";


            //System.out.println("\t SQL getBirthdayReminder : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));

                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));

                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                vect.add(marital);

                vBirthday.add(vect);
            }

            return vBirthday;
        } catch (Exception e) {
            System.out.println("\t Exception on getBirthdayReminder : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }
    
    /**
     * create by satrya 2014-03-22 keterangan: untuk melihat yg ulang tahun
     * hari ini
     *
     * @return
     */
    public static Vector getBirthdayReminderDay() {
        Vector vBirthday = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        try {

            sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " POST ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN  " + PstSection.TBL_HR_SECTION + " SEC ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN  " + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " LEFT JOIN  " + PstMarital.TBL_HR_MARITAL + " MAR ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID] + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " LEFT JOIN  " + PstReligion.TBL_HR_RELIGION + " REL  ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    //+ " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    //+ " , " + PstPosition.TBL_HR_POSITION + " POST "
                    //+ " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                    //+ " , " + PstSection.TBL_HR_SECTION + " SEC "
                    //+ " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                    //+ " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                    //+ " , " + PstReligion.TBL_HR_RELIGION + " REL "
                    + " WHERE "
                    //+ " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    //+ " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+ " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+ " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+ " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]+ " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    //+ " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]+ " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 "
                    + " AND DATE_FORMAT(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ",'%m')=\"" + Formater.formatDate(new Date(), "MM") + "\""
                    + " AND DATE_FORMAT(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ",'%d')=\"" + Formater.formatDate(new Date(), "dd") + "\"";


            sql += " ORDER BY DAYOFMONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") + MONTH("
                    + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") * 100, "
                    + //" DAYOFYEAR("+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+"), " +
                    PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + ", "
                    + " TO_DAYS(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") ";


            //System.out.println("\t SQL getBirthdayReminder : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));

                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));

                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                vect.add(marital);

                vBirthday.add(vect);
            }

            return vBirthday;
        } catch (Exception e) {
            System.out.println("\t Exception on getBirthdayReminder : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    /**
     * Untuk mencari ultah perbulan tapi jika tanggal sdh lewat tidak di
     * munculkan
     *
     * @return
     */
    public static Vector getBirthdayReminderMonthNotShowDatePassed() {
        Vector vBirthday = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        try {

            sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " POST ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " LEFT JOIN  " + PstSection.TBL_HR_SECTION + " SEC ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN  " + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " LEFT JOIN  " + PstMarital.TBL_HR_MARITAL + " MAR ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID] + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " LEFT JOIN  " + PstReligion.TBL_HR_RELIGION + " REL  ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 "
                    + " AND DATE_FORMAT(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ",'%m')=\"" + Formater.formatDate(new Date(), "MM") + "\"";


            sql += " ORDER BY DAYOFMONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") + MONTH("
                    + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") * 100, "
                    + //" DAYOFYEAR("+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+"), " +
                    PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + ", "
                    + " TO_DAYS(" + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") ";


            //System.out.println("\t SQL getBirthdayReminder : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            Date dtNewDate = new Date();
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Date dtUltah = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]);
                //cerk jika sudah lewat maka tidak di munculkan
                if (dtUltah != null && dtUltah.getDate() >= dtNewDate.getDate()) {
                    employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                    employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                    employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                    employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                    employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                    employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                    employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                    employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                    employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                    employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                    employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                    employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                    employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));

                    employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                    employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                    employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));

                    vect.add(employee);

                    department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                    vect.add(department);

                    position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                    vect.add(position);

                    section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                    vect.add(section);

                    empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                    vect.add(empcategory);

                    level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                    vect.add(level);

                    religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                    vect.add(religion);

                    marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                    marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                    vect.add(marital);

                    vBirthday.add(vect);
                }

            }

            return vBirthday;
        } catch (Exception e) {
            System.out.println("\t Exception on getBirthdayReminder : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    public static Vector getStaffRecapitulation(java.util.Date recapYear) {
        Vector vStaffRecap = new Vector(1, 1);
        Vector vDept = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sqlDep = "";
        String sqlCount = "";
        //System.out.println("\trecapYear = : " + recapYear.getYear());

        try {
            sqlDep = " SELECT DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEP "
                    + " WHERE ((EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 AND "
                    + "    TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ") < TO_DAYS('" + (recapYear.getYear() + 1900) + "-1-1') "
                    + "     ) OR ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 1 AND "
                    + "       TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ") > TO_DAYS('" + (recapYear.getYear() + 1900) + "-1-1') "
                    + "   )  ) AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " GROUP BY EMP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " ORDER BY DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            //System.out.println("\t--- SQL getStaffRecapitulation : getDept ---\n" + sqlDep);

            dbrs = DBHandler.execQueryResult(sqlDep);
            ResultSet rs = dbrs.getResultSet();

            int i = 0;
            while (rs.next()) {
                //System.out.println(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vDept.add(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                i++;
            }
            vDept.add("");
            vStaffRecap.add(vDept);
        } catch (Exception e) {
            System.out.println("\t Exception on getStaffRecapitulation : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }

        //------------------------------------------------
        Calendar cal = new GregorianCalendar();
        Calendar calNow = new GregorianCalendar();
        Calendar calRecap = new GregorianCalendar();
        String strRecap = "";
        boolean printed = false;

        for (int month = 0; month < 12; month++) {
            Vector vCount = new Vector(1, 1);
            cal.set(recapYear.getYear() + 1900, month, 1);

            //System.out.println("\tgetActualMaximum() = " + cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
            strRecap = (recapYear.getYear() + 1900) + "-" + (month + 1) + "-" + cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            //System.out.println("\tstrRecap = " + strRecap);

            calRecap.set(recapYear.getYear() + 1900, month, cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
            if (calRecap.before(calNow)) {
                printed = true;
            } else {
                printed = false;
            }
            //System.out.println("\t...Printed = " + printed);

            try {
                sqlCount = " SELECT COUNT(*) AS COUNT "
                        + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                        + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEP "
                        + " WHERE ((EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 AND "
                        + "    TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ") < TO_DAYS('" + strRecap + "') "
                        + "     ) OR ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 1 AND "
                        + "       TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ") > TO_DAYS('" + strRecap + "') "
                        + "   )  ) AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                        + " GROUP BY EMP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                        + " ORDER BY DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

                //System.out.println("\t--- SQL getStaffRecapitulation : getCount month: " + month + " ---\n" + sqlCount);

                dbrs = DBHandler.execQueryResult(sqlCount);
                ResultSet rs = dbrs.getResultSet();

                int j = 0;
                int cnt = 0;
                int total = 0;
                while (rs.next()) {
                    //System.out.println(rs.getString("COUNT"));
                    if (printed) {
                        cnt = rs.getInt("COUNT");
                        vCount.add(rs.getString("COUNT"));
                        total += cnt;
                    } else {
                        vCount.add("");
                    }
                    j++;
                }
                if (printed) {
                    vCount.add(String.valueOf(total));
                } else {
                    vCount.add("");
                }
                //System.out.println("--");
                vStaffRecap.add(vCount);
            } catch (Exception e) {
                System.out.println("\t Exception on getStaffRecapitulation : " + e);
            } finally {
                DBResultSet.close(dbrs);
            }
        }
        return vStaffRecap;
        //return new Vector(1, 1);
    }

    public static Vector getStaffRecapitulationMonthly(int recapMonth, int recapYear) {
        Vector vStaffRecapMonthly = new Vector(1, 1);
        Vector vDept = new Vector(1, 1);
        Vector vCountCurrMonth = new Vector(1, 1);
        Vector vCountPrevMonth = new Vector(1, 1);
        DBResultSet dbrs = null;

        String sqlDep = "";
        String strRecap = "";
        Calendar calCurr = new GregorianCalendar(recapYear, recapMonth - 1, 1);

        strRecap = recapYear + "-" + recapMonth + "-" + calCurr.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        //System.out.println("\tstrRecap = " + strRecap);

        calCurr.add(GregorianCalendar.MONTH, -1);
        String strRecapPrev = calCurr.get(GregorianCalendar.YEAR) + "-" + (calCurr.get(GregorianCalendar.MONTH) + 1) + "-" + calCurr.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        //System.out.println("\tstrRecapPrev = " + strRecapPrev);

        try {
            sqlDep = " SELECT DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " , COUNT(*) AS COUNT "
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEP "
                    + " WHERE ((EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 AND "
                    + "    TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ") < TO_DAYS('" + strRecap + "') "
                    + "     ) OR ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 1 AND "
                    + "       TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ") > TO_DAYS('" + strRecap + "') "
                    + "   )  ) AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " GROUP BY EMP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " ORDER BY DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            //System.out.println("\t--- SQL getStaffRecapitulationMonthly : getDept ---\n" + sqlDep);

            dbrs = DBHandler.execQueryResult(sqlDep);
            ResultSet rs = dbrs.getResultSet();

            int i = 0;
            while (rs.next()) {
                //System.out.println(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vDept.add(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vCountCurrMonth.add(rs.getString("COUNT"));
                i++;
            }
            vStaffRecapMonthly.add(vDept);
            vStaffRecapMonthly.add(vCountCurrMonth);
        } catch (Exception e) {
            System.out.println("\t Exception on getStaffRecapitulationMonthly : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }

        try {
            sqlDep = " SELECT COUNT(*) AS COUNT "
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEP "
                    + " WHERE ((EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 AND "
                    + "    TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ") < TO_DAYS('" + strRecapPrev + "') "
                    + "     ) OR ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 1 AND "
                    + "       TO_DAYS(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ") > TO_DAYS('" + strRecapPrev + "') "
                    + "   )  ) AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " GROUP BY EMP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " ORDER BY DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            //System.out.println("\t--- SQL getStaffRecapitulationMonthly : getCount ---\n" + sqlDep);

            dbrs = DBHandler.execQueryResult(sqlDep);
            ResultSet rs = dbrs.getResultSet();

            int i = 0;
            while (rs.next()) {
                //System.out.println(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vCountPrevMonth.add(rs.getString("COUNT"));
                i++;
            }
            vStaffRecapMonthly.add(vCountPrevMonth);
        } catch (Exception e) {
            System.out.println("\t Exception on getStaffRecapitulationMonthly : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        //return new Vector(1, 1);
        return vStaffRecapMonthly;
    }

    public static Vector getStaffNewHire(int recapMonth, int recapYear) {
        Vector vStaffNewHire = new Vector(1, 1);
        DBResultSet dbrs = null;

        String sql = "";

        try {
            sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " , POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + " , EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPosition.TBL_HR_POSITION + " POS "
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = "
                    + " POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " AND "
                    + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ") = "
                    + recapMonth + " AND "
                    + " YEAR(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ") = "
                    + +recapYear
                    + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            //System.out.println("\t--- SQL getStaffNewHire ---\n" + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Position pos = new Position();

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(pos);

                vStaffNewHire.add(vect);
            }
            return vStaffNewHire;
        } catch (Exception e) {
            System.out.println("\t Exception on getStaffNewHire : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    public static Vector getStaffResigned(int recapMonth, int recapYear) {
        Vector vStaffResigned = new Vector(1, 1);
        DBResultSet dbrs = null;

        String sql = "";

        try {
            sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " , EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + " , POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + " , RES." + PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPosition.TBL_HR_POSITION + " POS "
                    + " , " + PstResignedReason.TBL_HR_RESIGNED_REASON + " RES "
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = "
                    + " POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " AND "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_REASON_ID] + " = "
                    + " RES." + PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON_ID]
                    + " AND "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 1 "
                    + " AND "
                    + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ") = "
                    + recapMonth + " AND "
                    + " YEAR(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ") = "
                    + +recapYear
                    + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            //System.out.println("\t--- SQL getStaffResigned ---\n" + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Position pos = new Position();
                ResignedReason res = new ResignedReason();

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(pos);

                res.setResignedReason(rs.getString(PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON]));
                vect.add(res);

                vStaffResigned.add(vect);
            }
            return vStaffResigned;
        } catch (Exception e) {
            System.out.println("\t Exception on getStaffResigned : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    /**
     * this method used to list all employee specified by his/her department,
     * section and department created by gedhy
     */
    public static Vector listEmployeeByPositionLevel(Employee offsetEmployee, int positionLevel) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = " + offsetEmployee.getDepartmentId()
                    + " AND POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + " = " + positionLevel
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

            System.out.println("sql approvel Level : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(1));
                employee.setEmployeeNum(rs.getString(2));
                employee.setFullName(rs.getString(3));
                result.add(employee);
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
     * this method used to list all employee specified by his/her department,
     * section and department created by gedhy
     */
    public static Vector listEmployeeByPositionLevel(Employee offsetEmployee, Vector vectPositionLevel) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        String strPositionLevel = "";

        if (vectPositionLevel != null && vectPositionLevel.size() > 0) {
            int maxPositionLevel = vectPositionLevel.size();
            for (int i = 0; i < maxPositionLevel; i++) {
                strPositionLevel = strPositionLevel + vectPositionLevel.get(i) + ",";
            }

            if (strPositionLevel != null && strPositionLevel.length() > 0) {
                strPositionLevel = strPositionLevel.substring(0, strPositionLevel.length() - 1);
            }
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = " + offsetEmployee.getDepartmentId()
                    + " AND POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + " IN (" + strPositionLevel + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

            System.out.println("sql employee position level : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(1));
                employee.setEmployeeNum(rs.getString(2));
                employee.setFullName(rs.getString(3));
                result.add(employee);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector listEmployeeLevelHR(Vector vectPositionLevel) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        String oidHR = String.valueOf(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));

        String strPositionLevel = "";
        if (vectPositionLevel != null && vectPositionLevel.size() > 0) {
            int maxPositionLevel = vectPositionLevel.size();
            for (int i = 0; i < maxPositionLevel; i++) {
                strPositionLevel = strPositionLevel + vectPositionLevel.get(i) + ",";
            }

            if (strPositionLevel != null && strPositionLevel.length() > 0) {
                strPositionLevel = strPositionLevel.substring(0, strPositionLevel.length() - 1);
            }
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = '" + oidHR
                    + "' AND POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + " IN (" + strPositionLevel + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

            System.out.println("sql hr manager : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(1));
                employee.setEmployeeNum(rs.getString(2));
                employee.setFullName(rs.getString(3));
                result.add(employee);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector listEmployeeByPositionLevelExecutiveOffice(long offsetEmployee) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        Vector vectPositionLevel = new Vector();
        vectPositionLevel.add("" + PstPosition.LEVEL_GENERAL_MANAGER);

        String strPositionLevel = "";
        if (vectPositionLevel != null && vectPositionLevel.size() > 0) {
            int maxPositionLevel = vectPositionLevel.size();
            for (int i = 0; i < maxPositionLevel; i++) {
                strPositionLevel = strPositionLevel + vectPositionLevel.get(i) + ",";
            }

            if (strPositionLevel != null && strPositionLevel.length() > 0) {
                strPositionLevel = strPositionLevel.substring(0, strPositionLevel.length() - 1);
            }
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = " + offsetEmployee
                    + " AND POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + " IN (" + strPositionLevel + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

            System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(1));
                employee.setEmployeeNum(rs.getString(2));
                employee.setFullName(rs.getString(3));
                result.add(employee);
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
     * this method used to list all employee specified by his/her department,
     * section and department created by gedhy
     */
    public static Vector listEmployeeByPositionLevel(long oidDepartment, Vector vectPositionLevel) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        String strPositionLevel = "";
        if (vectPositionLevel != null && vectPositionLevel.size() > 0) {
            int maxPositionLevel = vectPositionLevel.size();
            for (int i = 0; i < maxPositionLevel; i++) {
                strPositionLevel = strPositionLevel + vectPositionLevel.get(i) + ",";
            }

            if (strPositionLevel != null && strPositionLevel.length() > 0) {
                strPositionLevel = strPositionLevel.substring(0, strPositionLevel.length() - 1);
            }
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = " + oidDepartment
                    + " AND POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + " IN (" + strPositionLevel + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

//                System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(1));
                employee.setEmployeeNum(rs.getString(2));
                employee.setFullName(rs.getString(3));
                result.add(employee);
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
     * this method used to list all employee specified by his/her department,
     * section and department created by gedhy
     */
    public static int listEmployeeCategory(long oidLevel) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        /* String strPositionLevel = "";
         if(vectPositionLevel!=null && vectPositionLevel.size()>0)
         {
         int maxPositionLevel = vectPositionLevel.size();
         for(int i=0; i<maxPositionLevel; i++)
         {
         strPositionLevel = strPositionLevel + vectPositionLevel.get(i) + ",";
         }

         if(strPositionLevel!=null && strPositionLevel.length()>0)
         {
         strPositionLevel = strPositionLevel.substring(0,strPositionLevel.length()-1);
         }
         }*/

        try {
            String sql = " SELECT COUNT(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")"
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                    + " WHERE "
                    + "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID];
            System.out.println("sql Category : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //Vector numList = new Vector (1,1);
            int num = 0;
            while (rs.next()) {

                num = rs.getInt(1);
                //System.out.println("nilai Num   "+num);
            }

            return num;

        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    //search statut karayawan berdasar periode
    public static Vector searchResignation(String startPeriod, String endPeriod, int status) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        /* if (srcEmployee == null)
         return new Vector(1,1);*/

        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE];
            if (status == 1) {
                sql = sql + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_REASON_ID]
                        + ", REAS." + PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON];

            }

            // ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]+
            sql = sql + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstSection.TBL_HR_SECTION + " SEC "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ";
            if (status == 1) {
                sql = sql + " , " + PstResignedReason.TBL_HR_RESIGNED_REASON + " REAS ";

            }
            sql = sql + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];
            if (status == 1) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_REASON_ID]
                        + " = REAS." + PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON_ID]
                        + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                        + " BETWEEN '" + startPeriod + "'"
                        + " AND '" + endPeriod + "'";
            }
            if (status == 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                        + " BETWEEN '" + startPeriod + "'"
                        + " AND '" + endPeriod + "'";
            }

            System.out.println("\t SQL searchResignation : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                //Position position = new Position();
                Section section = new Section();
                //EmpCategory empcategory = new EmpCategory();
                //Level level = new Level();
                //Religion religion = new Religion();
                //Marital marital = new Marital();
                //Locker locker = new Locker();
                // Division division = new Division();
                ResignedReason resignedReason = new ResignedReason();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                /*if(status==1){
                 employee.setResignedReasonId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_REASON_ID]));
                 vect.add(employee);
                 resignedReason.setResignedReason(rs.getString(PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON]));
                 vect.add(resignedReason);
                 }*/
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);
                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector searchEmployeeResignation(SrcEmployee srcEmployee, int start, int recordToGet) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcEmployee == null) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + /*", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_PHONE]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SEX]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]+*/ ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + //", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + /*", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+*/ ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + /*", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]+
                     ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_CURIER]+*/ ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DESC]
                    + // ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]+
                    ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]+
                    //", LOC."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+
                    ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    + " , " + PstPosition.TBL_HR_POSITION + " POST "
                    + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                    + " , " + PstSection.TBL_HR_SECTION + " SEC "
                    + " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                    + //" , "+PstLocker.TBL_HR_LOCKER + " LOC "+
                    " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                    + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                    + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID];

            String whereClause = "";



            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                if (srcEmployee.getResigned() == 0) {
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                            + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                            + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";

                } else {
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                            + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                            + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
                }
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }



            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            switch (srcEmployee.getOrderBy()) {
                case FrmSrcEmployee.ORDER_EMPLOYEE_NAME:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case FrmSrcEmployee.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;
                case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                case FrmSrcEmployee.ORDER_COMM_DATE:
                    if (srcEmployee.getResigned() == 0) {
                        sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
                    } else if (srcEmployee.getResigned() == 1) {
                        sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE];
                    }
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL searchEmployeeResignation : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                EmpCategory empcategory = new EmpCategory();
                Level level = new Level();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Locker locker = new Locker();
                Division division = new Division();
                EmpEducation empEducation = new EmpEducation();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                /*employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                 employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                 employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                 employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                 employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                 employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                 employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));*/
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                //employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                /*employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                 employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                 employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                 employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                 employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                 employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                 employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                 employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));*/
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                //employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                employee.setResigned(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                employee.setResignedDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                employee.setResignedDesc(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DESC]));
                vect.add(employee);
                //khussu untuk Intimas,edited By Yunny
                //employee.setCurier(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_CURIER]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));


                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                marital.setNumOfChildren(rs.getInt(PstMarital.fieldNames[PstMarital.FLD_NUM_OF_CHILDREN]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);
                //locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                //locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                if (employee.getLockerId() != 0) {
                    try {
                        locker = PstLocker.fetchExc(employee.getLockerId());
                    } catch (Exception e) {
                        locker = new Locker();
                        System.out.println("\tlocker error");
                    }
                }
                vect.add(locker);

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                /*String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID];
                 String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
                 Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);

                 Education education = new Education();
                 System.out.println("vedu size"+vedu.size());
                 if(vedu!=null && vedu.size()>0)
                 {
                 empEducation = (EmpEducation)vedu.get(0);
                 try{
                 education = PstEducation.fetchExc(empEducation.getEducationId());
                 }catch(Exception e){;}
                 }
                 vect.add(empEducation);*/

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static int countListRecognition(SrcEmployee srcEmployee) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcEmployee == null) {
            return 0;
        }

        try {
            String sql = " SELECT COUNT(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")"
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    + " , " + PstPosition.TBL_HR_POSITION + " POST "
                    + " , " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "
                    + " , " + PstSection.TBL_HR_SECTION + " SEC "
                    + " , " + PstLevel.TBL_HR_LEVEL + " LEV "
                    + " , " + PstMarital.TBL_HR_MARITAL + " MAR "
                    + " , " + PstReligion.TBL_HR_RELIGION + " REL "
                    + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
                    + " WHERE "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " AND  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = EMPCAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID];


            String whereClause = "";


            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                if (srcEmployee.getResigned() == 0) {
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                            + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                            + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";

                } else {
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                            + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                            + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
                }
            }


            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }


            //---------------------

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            //System.out.println("\t SQL COUNTEmployee : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while (rs.next()) {
                num = rs.getInt(1);
            }

            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public static Vector listEmployee_Ovt(long idDepartment, long idDivision, long idSection, String name, String payrollNum, int ovtStatus) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            /*
             *select emp.full_name, emp.address, pst.position, emp.employee_num
             from hr_employee emp
             inner join hr_position pst on pst.position_id = emp.position_id
             inner join hr_department dept on dept.department_id = emp.department_id
             inner join hr_division  vis on vis.division_id = emp.division_id
             */

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " PST "
                    + " ON PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    + " ON DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " VIS "
                    + " ON VIS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstOvt_Employee.TBL_OVT_EMPLOYEE + " OVT "
                    + " ON OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM] + " = EMP." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM];


            String whereClause = "";
            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment;
            }


            if (idDivision != 0) {
                if (idDepartment != 0) {
                    whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + idDivision;
                } else {
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + idDivision;
                }
            }

            if (idSection != 0) {
                if (idDivision != 0) {
                    if (idDepartment != 0) {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    } else {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    }
                } else {
                    if (idDepartment != 0) {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    } else {
                        whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    }
                }

            }

            if (idDivision == 0 && idSection == 0 && idDepartment == 0) {
                if ((name != null) && (name.length() > 0)) {
                    Vector vectName = logicParser(name);
                    if (vectName != null && vectName.size() > 0) {
                        whereClause = whereClause + " (";
                        for (int i = 0; i < vectName.size(); i++) {
                            String str = (String) vectName.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            } else {
                if ((name != null) && (name.length() > 0)) {
                    Vector vectName = logicParser(name);
                    if (vectName != null && vectName.size() > 0) {
                        whereClause = whereClause + " AND (";
                        for (int i = 0; i < vectName.size(); i++) {
                            String str = (String) vectName.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            }

            if (idDivision == 0 && idSection == 0 && idDepartment == 0 && name.length() == 0) {
                if ((payrollNum != null) && (payrollNum.length() > 0)) {
                    Vector vectNum = logicParser(payrollNum);
                    if (vectNum != null && vectNum.size() > 0) {
                        whereClause = whereClause + " (";
                        for (int i = 0; i < vectNum.size(); i++) {
                            String str = (String) vectNum.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            } else {
                if ((payrollNum != null) && (payrollNum.length() > 0)) {
                    Vector vectNum = logicParser(payrollNum);
                    if (vectNum != null && vectNum.size() > 0) {
                        whereClause = whereClause + " AND (";
                        for (int i = 0; i < vectNum.size(); i++) {
                            String str = (String) vectNum.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            }

            /*if(ovtStatus==1){
             whereClause += " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.DRAFT;
             }else if(ovtStatus==2){
             whereClause += " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.APPROVE;
             }else if(ovtStatus==3){
             whereClause += " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.POSTED;
             }else if(ovtStatus==1 && ovtStatus==2){
             whereClause += " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.DRAFT+
             " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.APPROVE;
             }else if(ovtStatus==1 && ovtStatus==3){
             whereClause += " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.DRAFT+
             " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.POSTED;
             }else if(ovtStatus==2 && ovtStatus==3){
             whereClause += " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.APPROVE+
             " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.POSTED;
             }else if(ovtStatus==1 && ovtStatus==2 && ovtStatus==3){
             whereClause += " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.DRAFT+
             " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.APPROVE+
             " AND OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+" = "+PstOvt_Employee.POSTED;
             }*/

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println("sql::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector();
                Employee objEmpl = new Employee();
                objEmpl.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                objEmpl.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                objEmpl.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                temp.add(objEmpl);

                Position position = new Position();
                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                temp.add(position);

                result.add(temp);
            }
        } catch (Exception ex) {
            System.out.println("err Eselon List :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;

    }

    public static Vector listImportPresent(long idDepartment, long idDivision, long idSection, String full_name) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " PST "
                    + " ON PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
                    + " ON DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " VIS "
                    + " ON VIS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID];
            /*" LEFT JOIN "+PstOvt_Employee.TBL_OVT_EMPLOYEE+ " AS OVT "+
             " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+ " = "+
             " OVT. "+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM];*/
            /*" INNER JOIN "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" SCH "+
             " ON SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];*/

            // String whereClause = " OVT."+PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS]+ " = "+PstOvt_Employee.DRAFT;
            String whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
            // String whereClause ="";
            if (idDepartment != 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment;
            }


            if (idDivision != 0) {
                if (idDepartment != 0) {
                    whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + idDivision;
                } else {
                    whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + idDivision;
                }
            }

            if (idSection != 0) {
                if (idDivision != 0) {
                    if (idDepartment != 0) {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    } else {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    }
                } else {
                    if (idDepartment != 0) {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    } else {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    }
                }

            }

            /*if(full_name.length()>0 && full_name!=null){
             System.out.println("Masuk ke methode yang ada di Java nich11111111::::::::::::::::::::::::::"+full_name);
             if(idSection!= 0){
             if(idDivision!=0) {
             if(idDepartment!=0){
             whereClause = whereClause + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }else{
             whereClause = whereClause + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }
             }else{
             if(idDepartment!=0){
             whereClause = whereClause + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }else{
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }
             }
             }else{
             if(idDivision!=0) {
             if(idDepartment!=0){
             whereClause = whereClause + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }else{
             whereClause = whereClause + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }
             }else{
             if(idDepartment!=0){
             whereClause = whereClause + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }else{
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
             " = '"+full_name+"'";
             }
             }
             }
             }*/


            if ((full_name != null) && (full_name.length() > 0)) {
                Vector vectName = logicParser(full_name);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " AND (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ")";
                }
            }


            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println("sql di importnya111111111::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector();
                Employee objEmpl = new Employee();
                objEmpl.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                objEmpl.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                objEmpl.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                objEmpl.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                temp.add(objEmpl);

                Position position = new Position();
                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                temp.add(position);

                result.add(temp);
            }
        } catch (Exception ex) {
            System.out.println("err Eselon List :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;

    }

    public static Vector listPreData(long idDepartment, String levelCode, long idSection, String searchNrFrom, String searchNrTo, String searchName, int statusData, String codeComponent, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("searchNrFrom)" + searchNrFrom);
        if (idDepartment == 0 && levelCode == null && idSection == 0 && searchNrFrom == null && searchNrTo == null && searchName == null && statusData == 0 && codeComponent == null && periodId == 0) {
            return new Vector(1, 1);
        }
        PayPeriod payPeriod123 = new PayPeriod();
        try {
            payPeriod123 = PstPayPeriod.fetchExc(periodId); 
        } catch (Exception e){
            System.out.printf("period Id nya mana?");
        }
        try {
            String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAID_STATUS]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    //  + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    
                    //update by priska pengambilan employee berdasarkan resigned date nya seduah startdate pay period
                   + " WHERE ( EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 OR EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] +" > \"" + Formater.formatDate(payPeriod123.getStartDate(), "yyyy-MM-dd HH:mm:ss")+ " \" ) " 
                        
                    
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + PstPayEmpLevel.CURRENT;


            String whereClause = "";

            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment + " AND ";
            }

            //System.out.println("department"+srcEmployee.getDepartment());

            if (levelCode != null && !levelCode.equals("") && !levelCode.equals("0")) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + levelCode + "' AND ";
            }

            if (idSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + idSection + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }
            if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }

            if ((codeComponent != null) && (codeComponent.length() > 0)) {
                Vector vectCode = logicParser(codeComponent);
                if (vectCode != null && vectCode.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectCode.size(); i++) {
                        String str = (String) vectCode.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }

            if (statusData < 2) {
                whereClause += " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]
                        + " = " + statusData + " AND ";
            }

            /*if(dataStatus < 2){
             if(dataStatus==0)
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             if(dataStatus==1){
             Date now = new Date();
             int monthNow =now.getMonth()+1;
             int yearNow = now.getYear()+1900;
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             }
             }*/




            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }




            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            /*switch(srcEmployee.getOrderBy()){
             case FrmSrcEmployee.ORDER_EMPLOYEE_NAME :
             sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] ;
             break;
             case FrmSrcEmployee.ORDER_DEPARTMENT:
             sql = sql + " ORDER BY DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] ;
             break;
             case FrmSrcEmployee.ORDER_EMPLOYEE_NUM:
             sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] ;
             break;
             case FrmSrcEmployee.ORDER_COMM_DATE:
             sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] ;
             break;
             default:
             sql = sql + "";
             }*/


            //sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL listPreData : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PaySlip paySlip = new PaySlip();
                PayEmpLevel payEmpLevel = new PayEmpLevel();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setDivisionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]));

                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);
                paySlip.setEmployeeId(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]));
                paySlip.setStatus(rs.getInt(PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]));
                paySlip.setPaidStatus(rs.getInt(PstPaySlip.fieldNames[PstPaySlip.FLD_PAID_STATUS]));
                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                paySlip.setCompCode(rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE]));
                paySlip.setPeriodId(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]));
                //paySlip.setProcentasePresence(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_PROCENTASE_PRESENCE]));
                vect.add(paySlip);

                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector listOvt_Empolyee(long idDepartment, long idDivision, long idSection, String name, String payrollNum, String overtime_code, long periodId, int dateFrom, int dateTo, int ovtStatus, int ovtStatus1, int ovtStatus2) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        /*
         *select emp.full_name, pst.position, ovt.*
         from pay_ovt_employee ovt
         inner join hr_employee emp on ovt.employee_num = emp.employee_num
         inner join hr_position pst on emp.position_id = pst.position_id
         */
        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", OVT.*"
                    + " FROM " + PstOvt_Employee.TBL_OVT_EMPLOYEE + " OVT "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " ON OVT." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstPosition.TBL_HR_POSITION + " PST "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];


            String whereClause = "";
            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment;
            }


            if (idDivision != 0) {
                if (idDepartment != 0) {
                    whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + idDivision;
                } else {
                    whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + idDivision;
                }
            }

            if (idSection != 0) {
                if (idDivision != 0) {
                    if (idDepartment != 0) {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    } else {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    }
                } else {
                    if (idDepartment != 0) {
                        whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    } else {
                        whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + idSection;
                    }
                }

            }

            if (idDivision == 0 && idSection == 0 && idDepartment == 0) {
                if ((name != null) && (name.length() > 0)) {
                    Vector vectName = logicParser(name);
                    if (vectName != null && vectName.size() > 0) {
                        whereClause = whereClause + " (";
                        for (int i = 0; i < vectName.size(); i++) {
                            String str = (String) vectName.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            } else {
                if ((name != null) && (name.length() > 0)) {
                    Vector vectName = logicParser(name);
                    if (vectName != null && vectName.size() > 0) {
                        whereClause = whereClause + " AND (";
                        for (int i = 0; i < vectName.size(); i++) {
                            String str = (String) vectName.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            }

            if (idDivision == 0 && idSection == 0 && idDepartment == 0 && name.length() == 0) {
                if ((payrollNum != null) && (payrollNum.length() > 0)) {
                    Vector vectNum = logicParser(payrollNum);
                    if (vectNum != null && vectNum.size() > 0) {
                        whereClause = whereClause + " (";
                        for (int i = 0; i < vectNum.size(); i++) {
                            String str = (String) vectNum.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            } else {
                if ((payrollNum != null) && (payrollNum.length() > 0)) {
                    Vector vectNum = logicParser(payrollNum);
                    if (vectNum != null && vectNum.size() > 0) {
                        whereClause = whereClause + " AND (";
                        for (int i = 0; i < vectNum.size(); i++) {
                            String str = (String) vectNum.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ")";
                    }
                }
            }

            if (idDivision == 0 && idSection == 0 && idDepartment == 0 && name.length() == 0 && payrollNum.length() == 0) {
                if (overtime_code != null && overtime_code.length() > 0) {
                    whereClause = whereClause + " OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_CODE]
                            + " = '" + overtime_code + "'";
                }
            } else {
                if (overtime_code != null && overtime_code.length() > 0) {
                    whereClause = whereClause + " AND OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_CODE]
                            + " = '" + overtime_code + "'";
                }
            }

            if (idDivision == 0 && idSection == 0 && idDepartment == 0 && name.length() == 0 && payrollNum.length() == 0 && overtime_code.length() == 0) {
                if (periodId != 0) {
                    whereClause = whereClause + " OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID]
                            + " = " + periodId;
                }
            } else {
                if (periodId != 0) {
                    whereClause = whereClause + " AND OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID]
                            + " = " + periodId;
                }
            }

            //untuk mencari list dari tanggal sampai end tanggal

            Period objPeriod = new Period();
            int durasi = 0;
            if (dateFrom != 0 && dateTo != 0) {
                if (periodId != 0) {
                    try {
                        objPeriod = PstPeriod.fetchExc(periodId);
                    } catch (Exception e) {
                        ;
                    }
                }
                Date datePeriodStart = objPeriod.getStartDate();
                Date datePeriodEnd = objPeriod.getEndDate();

                int yearStart = datePeriodStart.getYear() + 1900;
                int monthStart = datePeriodStart.getMonth() + 1;
                int dateStart = datePeriodStart.getDate();
                int monthEnd = datePeriodEnd.getMonth() + 1;
                GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart - 1, dateStart);
                int nDayOfMonthMaxStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                String endDateStr = "";
                String startDateStr = "";

                String maxDatePeriodStr = PstSystemProperty.getValueByName("START_DATE_PERIOD");
                int maxDatePeriod = Integer.parseInt(maxDatePeriodStr);

                int endDatePeriod = maxDatePeriod - 1;
                durasi = ((nDayOfMonthMaxStart - dateFrom) + 1) + dateTo;
                int tanggal = dateFrom - 1;
                int bulan = 0;
                int tanggalAwal = 1;
                Date dateStartPeriod = new Date();
                Date dateEndPeriod = new Date();
                String tanggalAwalPeriod = "";
                String tanggalAkhirPeriod = "";
                int durasiX = durasi - 1;
                for (int k = 0; k < durasi; k++) {
                    if (tanggal == nDayOfMonthMaxStart) {
                        tanggal = 1;
                        startDateStr = String.valueOf(yearStart) + "-" + String.valueOf(monthEnd) + "-" + String.valueOf(tanggal);
                    } else {
                        tanggal = tanggal + 1;
                        if ((tanggal >= maxDatePeriod) && (tanggal <= nDayOfMonthMaxStart)) {
                            bulan = monthStart;
                        } else if ((tanggal >= tanggalAwal) && (tanggal <= endDatePeriod)) {
                            bulan = monthEnd;
                        }
                        startDateStr = String.valueOf(yearStart) + "-" + String.valueOf(bulan) + "-" + String.valueOf(tanggal);
                    }

                    if (k == 0) {
                        tanggalAwalPeriod = startDateStr;
                    } else if (k == durasiX) {
                        tanggalAkhirPeriod = startDateStr;
                    }

                    dateStartPeriod = Formater.formatDate(tanggalAwalPeriod, "yyyy-MM-dd");
                    dateEndPeriod = Formater.formatDate(tanggalAkhirPeriod, "yyyy-MM-dd");


                }

                whereClause = whereClause + " AND OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_WORK_DATE]
                        + " BETWEEN  '" + Formater.formatDate(dateStartPeriod, "yyyy-MM-dd") + "'"
                        + " AND '" + Formater.formatDate(dateEndPeriod, "yyyy-MM-dd") + "'";


            }



            if (ovtStatus != 0 || ovtStatus1 != 0 || ovtStatus2 != 0) {
                whereClause = whereClause + " AND ( ";
                if (ovtStatus != 0) {
                    if ((ovtStatus == 1) && (ovtStatus1 == 2 || ovtStatus2 == 3)) {
                        whereClause = whereClause + " OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS] + " = " + I_DocStatus.DOCUMENT_STATUS_DRAFT + " OR ";
                    } else {
                        whereClause = whereClause + " OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS] + " = " + I_DocStatus.DOCUMENT_STATUS_DRAFT;
                    }
                }

                if (ovtStatus1 != 0) {
                    if ((ovtStatus1 == 2) && (ovtStatus2 == 3)) {
                        whereClause = whereClause + " OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS] + " = " + I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED + " OR ";
                    } else {
                        whereClause = whereClause + " OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS] + " = " + I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED;
                    }
                }
                if (ovtStatus2 != 0) {
                    if (ovtStatus2 == 3) {
                        whereClause = whereClause + " OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS] + " IN (" + I_DocStatus.DOCUMENT_STATUS_FINAL + "," + I_DocStatus.DOCUMENT_STATUS_PROCEED + ")";
                    }
                }

                whereClause = whereClause + " ) ";


            }

            //whereClause = whereClause + " OR OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_STATUS] + " = 0";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println("sql::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector();
                Employee emp = new Employee();
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                temp.add(emp);

                Position objPst = new Position();
                objPst.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                temp.add(objPst);

                Ovt_Employee ovt_Employee = new Ovt_Employee();
                PstOvt_Employee.resultToObject(rs, ovt_Employee);
                temp.add(ovt_Employee);

                result.add(temp);

            }


        } catch (Exception ex) {
            System.out.println("err Eselon List :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;

    }

    public static Vector listPostingOvertime(long idPeriod, String ovtCode) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", OVT.*"
                    + " FROM " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " OVT "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " ON OVT." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " PST "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = PST." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE OVT." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS] + " =  " + I_DocStatus.DOCUMENT_STATUS_PROCEED
                    + " AND OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID] + " = " + idPeriod;

            if (ovtCode.length() > 0) {
                sql = sql + " AND OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_CODE] + " = '" + ovtCode + "'";
            }

            System.out.println("sql::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector();
                Employee emp = new Employee();
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                temp.add(emp);

                Position objPst = new Position();
                objPst.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                temp.add(objPst);

                Ovt_Employee ovt_Employee = new Ovt_Employee();
                PstOvt_Employee.resultToObject(rs, ovt_Employee);
                temp.add(ovt_Employee);

                result.add(temp);
            }
        } catch (Exception ex) {
            System.out.println("err Eselon List :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;

    }

    public static int listEmployeeCategMale(Date dateFrom, Date dateTo, long oidEmployeeCateg, long levelId) {
        DBResultSet dbrs = null;
        int count = 0;
        // System.out.println("srcEmployee.getStartCommenc()...."+srcEmployee.getStartCommenc());
        try {
            String sql = "SELECT COUNT(*) "
                    + "FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstLevel.TBL_HR_LEVEL + " AS LEV "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CATEG "
                    + " ON CATEG." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " WHERE (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.MALE + ")";

            if (oidEmployeeCateg != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + oidEmployeeCateg + ")";
            }

            if (levelId != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + levelId + ")";
            }

            if ((dateFrom != null) && (dateTo != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(dateFrom, "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(dateTo, "yyyy-MM-dd") + "' ";

            }


            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            System.out.println("sql SessEmployee.listEmployeeCategMale::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static int listEmployeeCategFemale(Date dateFrom, Date dateTo, long oidEmployeeCateg, long levelId) {
        DBResultSet dbrs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) "
                    + "FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstLevel.TBL_HR_LEVEL + " AS LEV "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CATEG "
                    + " ON CATEG." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " WHERE (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.FEMALE + ")";

            if (oidEmployeeCateg != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + oidEmployeeCateg + ")";
            }

            if (levelId != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + levelId + ")";
            }

            if ((dateFrom != null) && (dateTo != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(dateFrom, "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(dateTo, "yyyy-MM-dd") + "' ";

            }
            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            //System.out.println("sql SessEmployee.listEmployeeCategFemale::::::"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static int listEmployeeSumCateg(Date dateFrom, Date dateTo, long levelId) {
        DBResultSet dbrs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) "
                    + "FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstLevel.TBL_HR_LEVEL + " AS LEV "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CATEG "
                    + " ON CATEG." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;


            if (levelId != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + levelId + ")";
            }

            if ((dateFrom != null) && (dateTo != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(dateFrom, "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(dateTo, "yyyy-MM-dd") + "' ";

            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static Vector tmpNmLevel(long idLevel) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        try {
            String sql = " SELECT DISTINCT " + PstLevel.fieldNames[PstLevel.FLD_LEVEL]
                    + ", " + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]
                    + " FROM " + PstLevel.TBL_HR_LEVEL;

            if (idLevel != 0) {
                sql = sql + " WHERE " + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " = " + idLevel;
            }

            System.out.println("sqlnya::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector();
                Level level = new Level();

                //objDiklat.setOID(rs.getLong(PstDiklat.fieldNames[PstDiklat.FLD_ID_PNS_DIKLAT]));
                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                level.setOID(rs.getLong(PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]));

                temp.add(level);
                result.add(temp);
            }
        } catch (Exception ex) {
            System.out.println("err searchPeriode :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    //query yang digunakan untk menampilkan jenis pendidikan
    public static Vector tmpNmPendidikan() {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        /*
         *SELECT *
         FROM `hr_education`
         where education_id = 2 or education_id = 12 or education_id = 19 or education_id = 20 or education_id = 11
         order by education_id asc
         */
        try {
            String sql = " SELECT DISTINCT " + PstEducation.fieldNames[PstEducation.FLD_EDUCATION]
                    + ", " + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]
                    + " FROM " + PstEducation.TBL_HR_EDUCATION
                    + /*" WHERE "+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+" = 2"+
                     " OR "+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+" = 12 "+
                     " OR "+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+" = 19 "+
                     " OR "+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+" = 20 "+
                     " OR "+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+" = 11 "+*/ " ORDER BY " + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID] + " ASC ";


            System.out.println("sqlnya::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                //Vector temp = new Vector();
                Education education = new Education();

                //objDiklat.setOID(rs.getLong(PstDiklat.fieldNames[PstDiklat.FLD_ID_PNS_DIKLAT]));
                education.setEducation(rs.getString(PstEducation.fieldNames[PstEducation.FLD_EDUCATION]));
                education.setOID(rs.getLong(PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]));

                //temp.add(education );
                result.add(education);
            }
        } catch (Exception ex) {
            System.out.println("err searchPeriode :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /*
     *select count(*)
     from hr_employee emp
     inner join hr_emp_education edemp
     on emp.employee_id = edemp.employee_id
     inner join hr_education edu
     on edu.education_id = edemp.education_id
     where (edemp.end_date in (select max(end_date)from hr_emp_education group by employee_id)) and edemp.education_id = 19
     and emp.level_id = 51 and emp.sex = 1 and emp.religion_id = 3
     and edemp.end_date between '2001-03-06' AND '2003-06-08'
     *
     */
    public static int listEducationEmpMale(long education_id, long level_id, long religion_id, SrcEmployee srcEmployee, long oidPosition) {
        DBResultSet dbrs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID] + ") "
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstEmpEducation.TBL_HR_EMP_EDUCATION + " EDEMP "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstEducation.TBL_HR_EDUCATION + " EDU "
                    + " ON EDU." + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]
                    + " WHERE  EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID] + " = " + education_id
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.MALE
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + " = " + religion_id;

            if (level_id != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + level_id;
            }

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "'";
            }
            if (oidPosition != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + oidPosition;
            }

            if (srcEmployee.getResigned() < 2) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned();
            }

            // filter education level
            sql += " AND ("
                    + "    SELECT MAX(SEDU.EDUCATION_LEVEL) FROM hr_employee SEMP "
                    + "    INNER JOIN hr_emp_education SEDEMP ON SEMP.EMPLOYEE_ID = SEDEMP.EMPLOYEE_ID "
                    + "    INNER JOIN hr_education SEDU ON SEDU.EDUCATION_ID = SEDEMP.EDUCATION_ID "
                    + "    WHERE SEMP.EMPLOYEE_ID = EMP.EMPLOYEE_ID "
                    + ") = EDU.EDUCATION_LEVEL";


            System.out.println("sqlnya Male Education ::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static int listEducationEmpFemale(long education_id, long level_id, long religion_id, SrcEmployee srcEmployee, long oidPosition) {
        DBResultSet dbrs = null;
        int count = 0;
        //EDEMP.EMP_EDUCATION_ID
        try {
            String sql = "SELECT COUNT(EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID] + ") "
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstEmpEducation.TBL_HR_EMP_EDUCATION + " EDEMP "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstEducation.TBL_HR_EDUCATION + " EDU "
                    + " ON EDU." + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]
                    + " WHERE EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID] + " = " + education_id
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.FEMALE
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + " = " + religion_id;

            if (level_id != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + level_id;
            }

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "'";
            }
            if (oidPosition != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + oidPosition;
            }

            if (srcEmployee.getResigned() < 2) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned();
            }

            //sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED ]+" = "+PstEmployee.NO_RESIGN;

            // filter education level
            sql += " AND ("
                    + "    SELECT MAX(SEDU.EDUCATION_LEVEL) FROM hr_employee SEMP "
                    + "    INNER JOIN hr_emp_education SEDEMP ON SEMP.EMPLOYEE_ID = SEDEMP.EMPLOYEE_ID "
                    + "    INNER JOIN hr_education SEDU ON SEDU.EDUCATION_ID = SEDEMP.EDUCATION_ID "
                    + "    WHERE SEMP.EMPLOYEE_ID = EMP.EMPLOYEE_ID "
                    + ") = EDU.EDUCATION_LEVEL";

            System.out.println("sqlnya Female Education ::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /*
     *SELECT COUNT(*)  FROM HR_EMPLOYEE EMP
     INNER JOIN HR_EMP_EDUCATION EDEMP  ON EMP.EMPLOYEE_ID = EDEMP.EMPLOYEE_ID
     INNER JOIN HR_EDUCATION EDU  ON EDU.EDUCATION_ID = EDEMP.EDUCATION_ID
     WHERE (EDEMP.END_DATE IN (SELECT MAX(END_DATE)
     FROM HR_EMP_EDUCATION GROUP BY EDUCATION_ID))
     AND EDEMP.EDUCATION_ID = 20
     AND EMP.LEVEL_ID = 59 AND EDEMP.END_DATE BETWEEN '2001-03-06' AND '2003-06-08'
     */
    public static int listEducationJum(long education_id, long level_id, SrcEmployee srcEmployee, long oidPosition) {
        DBResultSet dbrs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) "
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstEmpEducation.TBL_HR_EMP_EDUCATION + " EDEMP "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstEducation.TBL_HR_EDUCATION + " EDU "
                    + " ON EDU." + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]
                    + " WHERE  EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID] + " = " + education_id;


            if (level_id != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + level_id;
            }

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "'";
            }
            if (oidPosition != 0) {

                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + oidPosition;
            }

            if (srcEmployee.getResigned() < 2) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned();
            }
            //System.out.println("sql jumlah ::::::::::::::::::::::"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /*
     *SELECT EMP.FULL_NAME, DEPT.DEPARTMENT, SECT.SECTION
     FROM HR_EMPLOYEE AS EMP
     INNER JOIN HR_EMP_CATEGORY AS CATEG  ON CATEG.EMP_CATEGORY_ID = EMP.EMP_CATEGORY_ID
     INNER JOIN HR_DEPARTMENT AS DEPT ON DEPT.DEPARTMENT_ID = EMP.DEPARTMENT_ID
     INNER JOIN HR_SECTION AS SECT ON SECT.SECTION_ID = EMP.SECTION_ID
     WHERE (EMP.SEX = 1) AND (EMP.EMP_CATEGORY_ID = 504404320187418817)

     *
     */
    public static Vector nameFemaleCategory(long oidEmpCategory) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " , DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " , SECT." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CATEG "
                    + " ON CATEG." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT "
                    + " ON DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SECT "
                    + " ON SECT." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " WHERE (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.FEMALE + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;


            if (oidEmpCategory != 0) {
                sql = sql + " AND (EMP." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = " + oidEmpCategory + ")";
            }

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            System.out.println("sql countFemale ::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector();

                Employee employee = new Employee();
                Department dept = new Department();
                Section sect = new Section();

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                temp.add(employee);

                dept.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                temp.add(dept);

                sect.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                temp.add(sect);

                result.add(temp);
            }
        } catch (Exception ex) {
            System.out.println("err searchPeriode :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public static Vector nameMaleCategory(long oidEmpCategory) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " , DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " , SECT." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CATEG "
                    + " ON CATEG." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT "
                    + " ON DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SECT "
                    + " ON SECT." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " WHERE (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.MALE + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;


            if (oidEmpCategory != 0) {
                sql = sql + " AND (EMP." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = " + oidEmpCategory + ")";
            }

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            System.out.println("sql countFemale ::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector();

                Employee employee = new Employee();
                Department dept = new Department();
                Section sect = new Section();

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                temp.add(employee);

                dept.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                temp.add(dept);

                sect.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                temp.add(sect);

                result.add(temp);
            }
        } catch (Exception ex) {
            System.out.println("err searchPeriode :" + ex.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /**
     * This method used to list employee pay slip Created By Yunny update by
     * Kartika(22 Agustus 2012 ) to macth period to level
     *
     * @param idDepartment
     * @param idDivision
     * @param idSection
     * @param searchNrFrom
     * @param searchNrTo
     * @param searchName
     * @param periodId
     * @param statusData : -1=both current of history
     * @param inclResign
     * @return
     */
    public static Vector listEmpPaySlip(long idDepartment, long idDivision, long idSection, String searchNrFrom,
            String searchNrTo, String searchName, long periodId, int statusData, boolean inclResign,long idPayrollGroup) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("searchNrFrom)" + searchNrFrom);
        String ClientName = "";
            try {
                ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
            } catch (Exception e) {
                System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
            }
            
        int withoutDH = 0;
               try{
                        withoutDH = Integer.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("SAL_LEVEL_WITHOUT_DH")); 
               } catch (Exception e){
                       System.out.printf("VALUE_NOTDC TIDAK DI SET?"); 
               }
        
        if (idDepartment == 0 && idDivision == 0 && idSection == 0 && searchNrFrom == null && searchNrTo == null && searchName == null && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    //update by satrya 2013-01-20
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]
                    //+ ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_BANK_ADDRESS]
                    //update by satrya 2013-05-06
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_DATE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ((statusData == PstPayEmpLevel.CURRENT || statusData == PstPayEmpLevel.HISTORY) ? (" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + statusData /*PstPayEmpLevel.CURRENT */) : "")
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    /* Start Add Query by Hendra Putu | 2015-05-19 */
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID];
                    if (withoutDH == 1){
                    sql =sql+" INNER JOIN pay_emp_level PEL ON (EMP.EMPLOYEE_ID = PEL.EMPLOYEE_ID AND PEL.`LEVEL_CODE` NOT LIKE '%-DH%') ";
                    }
                    sql=sql+ " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPART "
                    + " ON DEPART."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];
                    //priska 20150710
                    //untuk BPD Tidak memakai section
                    // if (idSection != 0) {
                       sql = sql + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                        + " ON SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID];
                   //    } 
                    
                    /* End Add Query by Hendra Putu | 2015-05-19 */
                   sql =sql + " INNER JOIN " + PstPayPeriod.TBL_HR_PAY_PERIOD + " AS PER ON PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + "="
                    + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                    //update by satrya 2013-01-20
                    //+ " INNER JOIN "+ PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] 
                    + " LEFT JOIN " + PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                    + " = BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]
                    + " WHERE "
                    + " ( (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + "  ) || ("
                    + "PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " ) || "
                    + " (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] + " ))"
                    /*" LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0" */
                    + (inclResign ? "" : " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " >= PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ) ") + " ";
                    //update by priska pengambilan employee berdasarkan resigned date nya seduah startdate pay period
                 //   + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " > PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ";
            String whereClause = "";

            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment + " AND ";
            }

            //System.out.println("department"+srcEmployee.getDepartment());

            if (idDivision != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + idDivision + " AND ";
            }

            if (idSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + idSection + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }
            if (idPayrollGroup != 0){
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_PAYROLL_GROUP]
                        + " = " + idPayrollGroup + " AND ";
            }
            if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }

            /*if(dataStatus < 2){
             if(dataStatus==0)
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             if(dataStatus==1){
             Date now = new Date();
             int monthNow =now.getMonth()+1;
             int yearNow = now.getYear()+1900;
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             }
             }*/




            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

       


            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY DEPART."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + ", ";
            // if (!ClientName.equals("BPD") || (ClientName != "BPD") ) { //priska 20150710
            sql = sql + " SEC."+PstSection.fieldNames[PstSection.FLD_SECTION] + ", ";
            // }
            sql = sql + " POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION] + " ASC ";


            //System.out.println("\t SQL listEmpPaySlip : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                PaySlip paySlip = new PaySlip();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                try{
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                }catch(Exception e){}
                employee.setDivisionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]));
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);
                payEmpLevel.setEmployeeId(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]));
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                payEmpLevel.setStartDate(rs.getDate(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]));
                //update by satrya 2013-01-20
                payEmpLevel.setBankAccNr(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]));

                vect.add(payEmpLevel);

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                //update by satrya 2013-01-20
                paySlip.setBankName(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]));
                paySlip.setPaySlipDate(rs.getDate(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_DATE]));
                //paySlip.setBankAddress(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ADDRESS]));
                if (rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]) == null || rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]).equalsIgnoreCase("null")) {
                    paySlip.setPrivateNote("");
                } else {
                    paySlip.setPrivateNote(rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]));
                }
                paySlip.setOvIdxAdj(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]));
                vect.add(paySlip);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /**
     * Create by satrya 2013-01-14
     *
     * @param employee_id
     * @param periodId
     * @param statusData
     * @param inclResign
     * @return
     */
    public static Vector listEmpPaySlipByEmployeeId(Vector employeeIds, long periodId, int statusData, boolean inclResign) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        if (employeeIds == null && employeeIds.size() < 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            /*        String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
             + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
             + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
             + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
             + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
             + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] 
             //update by satrya 2013-01-20
             + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
             + ", BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]
             + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
             + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"                     
             + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
             + ( (statusData==PstPayEmpLevel.CURRENT || statusData==PstPayEmpLevel.HISTORY) ? (" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" +  statusData /*PstPayEmpLevel.CURRENT *///) //: "" )*/
      /*              + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"                    
             + " ON EMP." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
             + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
             + " INNER JOIN " + PstPeriod.TBL_HR_PERIOD + " AS PER ON PER."+ PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]+"="
             + " SLIP."+ PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]                    
             //update by satrya 2013-01-20
             + " INNER JOIN "+ PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] 
             + " = BANK."+PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]
             + " WHERE " +  
             " ( (PER."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]  + " <= LEV."+ PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" >= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE] 
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" <= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] 
             +"  ) || ("
             +  "PER."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" >= LEV."+ PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
             + " && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" <= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
             +" ) || "
             +" (PER." + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" >= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +" <= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" >= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]+" ))"                    
             /*" LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0" */
            /*            + (inclResign ? "" : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 ") + " ";*/

            String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    //update by satrya 2013-01-20
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ((statusData == PstPayEmpLevel.CURRENT || statusData == PstPayEmpLevel.HISTORY) ? (" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + statusData /*PstPayEmpLevel.CURRENT */) : "")
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPayPeriod.TBL_HR_PAY_PERIOD + " AS PER ON PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + "="
                    + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                    //update by satrya 2013-01-20
                    //+ " INNER JOIN "+ PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] 
                    + " LEFT JOIN " + PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                    + " = BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]
                    + " WHERE "
                    + " ( (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + "  ) || ("
                    + "PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " ) || "
                    + " (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] + " ))"
                    /*" LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0" */
                    + (inclResign ? "" : " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " >= PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ) ") + " ";
              
            String whereClause = "";

            if (periodId != 0) {
                whereClause = whereClause + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }
            //update by satrya 2013-01-14
            if (employeeIds != null && employeeIds.size() > 0) {
                whereClause = whereClause + " ( ";
                if ((Long) employeeIds.get(0) != 0) {
                    for (int i = 0; i < employeeIds.size(); i++) {
                        long lstr = (Long) employeeIds.get(i);
                        whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                                + " = " + lstr + (i < (employeeIds.size() - 1) ? " OR " : "");
                    }
                    whereClause = whereClause + " ) AND ";
                }
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }




            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


            //System.out.println("\t SQL listEmpPaySlip : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                PaySlip paySlip = new PaySlip();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setDivisionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]));
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                vect.add(employee);
                payEmpLevel.setEmployeeId(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]));
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                payEmpLevel.setStartDate(rs.getDate(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]));
                //update by satrya 2013-01-20
                payEmpLevel.setBankAccNr(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]));
                vect.add(payEmpLevel);

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                //update by satrya 2013-01-20
                paySlip.setBankName(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]));
                vect.add(paySlip);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }
    /* This method used to list employee pay slip
     * Created By Yunny
     */

    public static Vector getEmpSalary(String level_code, long oidPeriod, Vector secSelect, long oidSection, int transfered) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        if (level_code == null) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CAT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + (!level_code.trim().equals("0") ? " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "='" + level_code + "'" : " ")
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0"
                    + " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + oidPeriod;

            String whereClause = "";

            //kondisi untuk karyawan yang dibayar cash atau transfered
            if (transfered < 2) {
                if (transfered == PstPayEmpLevel.TRANSFERED_YES) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                            + " != " + oidCash + " AND ";
                }

                if (transfered == PstPayEmpLevel.TRANSFERED_NO) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                            + " = " + oidCash + " AND ";
                }
            }

            if (secSelect != null && secSelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < secSelect.size(); x++) {
                    Department dept = (Department) secSelect.get(x);
                    sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dept.getOID() + " OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }


            if (oidSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + oidSection + " AND ";
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " LIMIT 5000";
            System.out.println("\t SQL SessEmployee.getEmpSalary : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                Religion religion = new Religion();
                EmpCategory empCategory = new EmpCategory();
                PaySlip paySlip = new PaySlip();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                empCategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCategory);

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                vect.add(paySlip);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /**
     * create by Yunny 2007 modified by Kartika 27 June 2008
     *
     * @param levSelect : selected Salary Level to be viewed
     * @param oidPeriod
     * @param oidDepartment
     * @param getCash : true get cash only salary
     * @return
     */
    public static Vector getEmpSalary(Vector levSelect, long oidPeriod, long oidDepartment, boolean getCash) {
        return getEmpSalary(levSelect, oidPeriod, oidDepartment, getCash ? SessPaySlip.PAID_BY_ALL : SessPaySlip.PAID_BY_BANK, null);
    }

    public static Vector getEmpSalary(Vector levSelect, long oidPeriod, long oidDepartment, int paidBy, Vector empCategory) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidCash = 0;
        oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        int withoutDH = 0;
               try{
                        withoutDH = Integer.valueOf(PstSystemProperty.getValueByName("SAL_LEVEL_WITHOUT_DH")); 
               } catch (Exception e){
                       System.out.printf("VALUE_NOTDC TIDAK DI SET?"); 
               }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_NO_REKENING]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    + ", POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " LEFT JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstPayPeriod.TBL_HR_PAY_PERIOD + " AS PER ON PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + "="
                    //update by satrya 2013-05-06
                    //+ " INNER JOIN " + PstPeriod.TBL_HR_PERIOD + " AS PER ON PER."+ PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]+"="
                    + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                    + " WHERE  ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0  || ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=1 AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " >= PER."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE]+ " )) "
                    /*+ " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0" */
                    + " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + oidPeriod
                    + ( withoutDH==1 ? " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]+" NOT LIKE '%-DH' " :"");
            switch (paidBy) {
                case SessPaySlip.PAID_BY_CASH:
                       sql = sql + " AND ( LENGTH( EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_NO_REKENING]+") = 0 OR EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_NO_REKENING]+" = 'CASH' ) " ;

                    /*sql = sql + " AND ( LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "=" + oidCash + " OR "
                            + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "=0 OR "
                            + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR] + "=\"\"  OR "
                            + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR] + " IS NULL ) "; */
                    break;
                case SessPaySlip.PAID_BY_BANK:
                       sql = sql + " AND  LENGTH( EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_NO_REKENING]+") > 0 AND EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_NO_REKENING]+" <> 'CASH' " ;
                    
                    /*sql = sql + " AND ( LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "<>" + oidCash + " AND "
                            + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "<>0 AND "
                            + " LENGTH(LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR] + ")> " + getMIN_BANK_ACC_LENGTH() + "  AND "
                            + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR] + " IS NOT NULL  )";*/
                default:
                    ;
            }
            if (empCategory != null && empCategory.size() > 0) {
                sql = sql + " AND  ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " IN (";
                for (int i = 0; i < empCategory.size(); i++) {
                    sql = sql + empCategory.get(i) + ((i == (empCategory.size() - 1)) ? ")" : ",");
                }

                sql = sql + ")";
            }


            sql = sql + " AND ( (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + "  ) || ("
                    + "PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " ) || "
                    + " (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] + " ))";
            /* dirubah menjadi payperiod
             *  sql=sql+ " AND ( (PER."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]  + " <= LEV."+ PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" >= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE] 
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" <= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] 
             +"  ) || ("
             +  "PER."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" >= LEV."+ PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
             + " && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" <= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
             +" ) || "
             +" (PER." + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" >= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +" <= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
             +" && PER."+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +" >= LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]+" ))";

             */

            String whereClause = "";

            //ambil dari sistem property untuk employee yang gajinya tidak ditransfer
            /*long oidCategory = 0;
             oidCategory = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_TRAIINING_CATEGORY")));
             if(oidCategory!= 0){
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+
             " != "+oidCategory+ " AND " ;
             }*/

            if (oidDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + oidDepartment + " AND ";
            }

            if (levSelect != null && levSelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < levSelect.size(); x++) {
                    SalaryLevel s = (SalaryLevel) levSelect.get(x);
                    sql = sql + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "='" + s.getLevelCode() + "' OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_NO_REKENING];// " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];
            
            //System.out.println("\t SQL getEmpSalary with Dept : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                Religion religion = new Religion();
                Position position = new Position();
                PaySlip paySlip = new PaySlip();
                Department department = new Department();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                payEmpLevel.setBankAccNr(rs.getString("EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_NO_REKENING]));
                vect.add(payEmpLevel);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                vect.add(paySlip);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* This method used to list employee transfered
     * Created By Yunny
     */
    public static Vector getEmpSalary(Vector levSelect, long oidPeriod, long oidDepartment, int transfered) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidCash = 0;
        oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));

        if (levSelect.size() == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    + ", POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0"
                    + " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + oidPeriod;
            //" AND LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]+"!="+oidCash;

            String whereClause = "";

            if (transfered < 2) {
                if (transfered == PstPayEmpLevel.TRANSFERED_YES) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                            + " != " + oidCash + " AND ";
                }

                if (transfered == PstPayEmpLevel.TRANSFERED_NO) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                            + " = " + oidCash + " AND ";
                }
            }


            //ambil dari sistem property untuk employee yang gajinya tidak ditransfer
            /*long oidCategory = 0;
             oidCategory = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_TRAIINING_CATEGORY")));
             if(oidCategory!= 0){
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+
             " != "+oidCategory+ " AND " ;
             }*/

            if (oidDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + oidDepartment + " AND ";
            }

            if (levSelect != null && levSelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < levSelect.size(); x++) {
                    SalaryLevel s = (SalaryLevel) levSelect.get(x);
                    sql = sql + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "='" + s.getLevelCode() + "' OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];
            System.out.println("\t SQL getEmpSalary with Dept : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                Religion religion = new Religion();
                Position position = new Position();
                PaySlip paySlip = new PaySlip();
                Department department = new Department();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                payEmpLevel.setBankAccNr(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]));
                vect.add(payEmpLevel);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                vect.add(paySlip);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* This method used to list employee non transfered
     * @param oidPeriod
     * Created By Yunny
     */
    public static Vector getEmpNonTransfered(long oidPeriod) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidCash = 0;
        oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));

        if (oidPeriod == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0"
                    + " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + oidPeriod
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "=" + oidCash;

            String whereClause = "";

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            System.out.println("\t SQL getNonTransfered : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                PaySlip paySlip = new PaySlip();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                payEmpLevel.setBankAccNr(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]));
                vect.add(payEmpLevel);

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                vect.add(paySlip);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* This method used to list employee non transfered,khusus intimas
     * @param oidPeriod
     * Created By Yunny
     */
    public static Vector getEmpNonTransfered(long oidPeriod, String oidTraining) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);


        if (oidPeriod == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + oidPeriod
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + Long.parseLong(oidTraining);

            String whereClause = "";

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            System.out.println("\t SQL SessEmployee.getEmpNonTransfered.. : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlip paySlip = new PaySlip();

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                vect.add(paySlip);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector getEmpPaySlip() {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidCash = 0;

        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " AS DIVI"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SEC"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0";


            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            //System.out.println("\t SQL SessEmployee.getEmpPaySlip : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Division division = new Division();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                PayEmpLevel payEmpLevel = new PayEmpLevel();


                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                payEmpLevel.setBankId(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]));
                vect.add(payEmpLevel);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector getEmpPaySlip(String payLevelCode) {
        return getEmpPaySlip(0, payLevelCode, 0, 0, 0, 0, "", "", "");
        /*  DBResultSet dbrs = null;
         Vector result = new Vector(1, 1);
         long oidCash = 0;

         try {
         String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
         + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
         + ", DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
         + ", DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
         + ", POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
         + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
         + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
         + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
         + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " AS DIVI"
         + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
         + " = DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
         + " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP"
         + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
         + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
         + " LEFT JOIN " + PstPosition.TBL_HR_POSITION + " AS POS"
         + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
         + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
         + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SEC"
         + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
         + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
         + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
         + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
         + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
         + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
         + (payLevelCode != null && !payLevelCode.equalsIgnoreCase("0") && payLevelCode.length() > 0 ? " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "=\"" + payLevelCode + "\"" : "");


         sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
         System.out.println("\t SQL SessEmployee.getEmpPaySlip : " + sql);

         dbrs = DBHandler.execQueryResult(sql);
         ResultSet rs = dbrs.getResultSet();

         while (rs.next()) {
         Vector vect = new Vector(1, 1);

         Employee employee = new Employee();
         Division division = new Division();
         Department department = new Department();
         Position position = new Position();
         Section section = new Section();
         PayEmpLevel payEmpLevel = new PayEmpLevel();


         employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
         employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])==null ? 
         new Date() : rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
         //employee.setCommencingDate(DBHandler.convertDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])),rs.getTime(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
         vect.add(employee);
         //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

         division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
         vect.add(division);

         department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
         vect.add(department);

         position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
         vect.add(position);

         section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
         vect.add(section);

         payEmpLevel.setBankId(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]));
         vect.add(payEmpLevel);

         result.add(vect);
         }

         return result;
         } catch (Exception e) {
         System.out.println("\t Exception on  searchEmployee : " + e);
         } finally {
         DBResultSet.close(dbrs);
         }
         return new Vector(1, 1); */

    }

    public static Vector getEmpPaySlip(long periodId, String payLevelCode, long companyId, long divisionId, long departmentId, long sectionId,
            String searchNrFrom, String searchNrTo, String searchName) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidCash = 0;

        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " AS DIVI"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN " + PstPosition.TBL_HR_POSITION + " AS POS"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SEC"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + (payLevelCode != null && !payLevelCode.equalsIgnoreCase("0") && payLevelCode.length() > 0 ? " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + " LIKE\"%" + payLevelCode + "%\"" : "")
                    + (divisionId == 0 ? "" : " AND (DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " =\"" + divisionId + "\") ")
                    //update by satrya 2014-02-03
                    + (companyId == 0 ? "" : " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " =\"" + companyId + "\") ")
                    + (departmentId == 0 ? "" : " AND (DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " =\"" + departmentId + "\") ")
                    + (sectionId == 0 ? "" : " AND (SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " =\"" + sectionId + "\") ");

            if (searchNrFrom != null && searchNrTo != null && searchNrFrom.length() > 0 && searchNrTo.length() > 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " >= \"" + searchNrFrom + "\" AND "
                        + "  (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " <= \"" + searchNrTo + "\" ) )";
            } else {
                sql = sql + ((searchNrFrom == null || searchNrFrom.length() < 1) ? "" : (" AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "=\"" + searchNrFrom + "\""));
                sql = sql + ((searchNrTo == null || searchNrTo.length() < 1) ? "" : (" AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "=\"" + searchNrTo + "\""));
            }

            sql = sql + ((searchName == null || searchName.length() < 1) ? "" : (" AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \"%" + searchName + "%\""));

            if (periodId != 0) {
                try {
                    PayPeriod payPeriod = PstPayPeriod.fetchExc(periodId);
                    //Period period = PstPeriod.fetchExc(periodId);
                    if (payPeriod != null && payPeriod.getStartDate() != null && payPeriod.getEndDate() != null) {
                        sql = sql + " AND ( (DATE(LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE] + ") <= \""
                                + Formater.formatDate(payPeriod.getStartDate(), "yyyy-MM-dd") + "\")  AND "
                                + " (DATE(LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] + ") >= \""
                                + Formater.formatDate(payPeriod.getStartDate(), "yyyy-MM-dd") + "\") ) ";
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            //System.out.println("\t SQL SessEmployee.getEmpPaySlip : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Division division = new Division();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                PayEmpLevel payEmpLevel = new PayEmpLevel();


                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]) == null
                        ? new Date() : rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                //employee.setCommencingDate(DBHandler.convertDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])),rs.getTime(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                payEmpLevel.setBankId(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]));
                vect.add(payEmpLevel);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* This method used to list employee overtime
     * Created By Yunny
     */
    public static Vector getOvtEmployee(long departmentId, long sectionId, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        if (departmentId == 0 && sectionId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]
                    + ", LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstOvt_Employee.TBL_OVT_EMPLOYEE + " AS OVT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + " = OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CAT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEVEL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + PstPayEmpLevel.CURRENT;

            String whereClause = "";

            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId + " AND ";
            }


            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " OVT." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM];
            System.out.println("\t SQL employeeMeal : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Ovt_Employee ovtEmp = new Ovt_Employee();
                EmpCategory empCat = new EmpCategory();
                PayEmpLevel payEmpLevel = new PayEmpLevel();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                ovtEmp.setEmployee_num(rs.getString(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]));
                vect.add(ovtEmp);

                empCat.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCat);

                payEmpLevel.setOvtIdxType(rs.getInt(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]));
                vect.add(payEmpLevel);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);


    }

    /* This method used to list employee overtime
     * Created By Yunny
     */
    public static Vector getOvtEmployee(long departmentId, long sectionId, long periodId, int index, Vector levSelect) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        if (departmentId == 0 && sectionId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]
                    + ", LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstOvt_Employee.TBL_OVT_EMPLOYEE + " AS OVT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + " = OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CAT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEVEL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + PstPayEmpLevel.CURRENT;

            String whereClause = "";



            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId + " AND ";
            }


            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " OVT." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }

            if (index < 2) {
                whereClause = whereClause + " LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]
                        + " = " + index + " AND ";

            }

            if (levSelect != null && levSelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < levSelect.size(); x++) {
                    SalaryLevel s = (SalaryLevel) levSelect.get(x);
                    sql = sql + " LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "='" + s.getLevelCode() + "' OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY OVT." + PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM];
            System.out.println("\t SQL SessEmployee.getOvtEmployee With index : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Ovt_Employee ovtEmp = new Ovt_Employee();
                EmpCategory empCat = new EmpCategory();
                PayEmpLevel payEmpLevel = new PayEmpLevel();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                ovtEmp.setEmployee_num(rs.getString(PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_EMPLOYEE_NUM]));
                vect.add(ovtEmp);

                empCat.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCat);

                payEmpLevel.setOvtIdxType(rs.getInt(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]));
                vect.add(payEmpLevel);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);


    }

    /**
     * @Author Roy Andika
     * @param departmentId
     * @param sectionId
     * @param mealAllowance
     * @param meal
     * @param catSelect
     * @param salarySelect
     * @return List Employee
     */
    public static Vector getListEmp(long departmentId, long sectionId, int mealAllowance, String meal, Vector catSelect, Vector salarySelect) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidTugasDaerah = 108;

        if (departmentId == 0 && sectionId == 0 && mealAllowance == 0 && meal == null && catSelect == null) {
            return new Vector(1, 1);
        }

        try {

            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_CATEGORY_DATE]
                    + ", CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CAT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + PstPayEmpLevel.CURRENT
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_MEAL_ALLOWANCE] + "=" + mealAllowance
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "!=" + oidTugasDaerah;

            String whereClause = "";

            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId + " AND ";
            }

            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId + " AND ";
            }

            if (catSelect != null && catSelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < catSelect.size(); x++) {
                    EmpCategory s = (EmpCategory) catSelect.get(x);
                    sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + s.getOID() + " OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }

            if (salarySelect != null && salarySelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < salarySelect.size(); x++) {
                    SalaryLevel sal = (SalaryLevel) salarySelect.get(x);
                    sql = sql + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "='" + sal.getLevelCode() + "' OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
            System.out.println("\t SQL listEmp : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                EmpCategory empCat = new EmpCategory();
                Religion religion = new Religion();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setCategoryDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_CATEGORY_DATE]));
                vect.add(employee);

                empCat.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCat);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);


    }

    public static Vector getSummMeal(long empCategoryId, int mealAllowance) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        if (empCategoryId == 0 && mealAllowance == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CAT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_MEAL_ALLOWANCE] + "=" + mealAllowance
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "!=" + empCategoryId;

            String whereClause = "";


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
            System.out.println("\t SQL listEmp : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                EmpCategory empCat = new EmpCategory();
                Religion religion = new Religion();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                vect.add(employee);

                empCat.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCat);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on search Employee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    public static Vector getListDW(long departmentId, long sectionId, long oidCategory, Vector salarySelect) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        long oidTugasDaerah = 108;

        if (departmentId == 0 && sectionId == 0 && oidCategory == 0 && salarySelect == null) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_CATEGORY_DATE]
                    + ", CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CAT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + PstPayEmpLevel.CURRENT;

            String whereClause = "";

            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId + " AND ";
            }

            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId + " AND ";

            }

            if (oidCategory != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + oidCategory + " AND ";

            }

            if (salarySelect != null && salarySelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < salarySelect.size(); x++) {
                    SalaryLevel sal = (SalaryLevel) salarySelect.get(x);
                    sql = sql + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "='" + sal.getLevelCode() + "' OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];
            System.out.println("\t SQL listEmp : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                EmpCategory empCat = new EmpCategory();
                Religion religion = new Religion();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setCategoryDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_CATEGORY_DATE]));
                vect.add(employee);

                empCat.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCat);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);


    }

    public static int getMaxFingerPrintNumber() {
        int max = -1;
        DBResultSet dbrs = null;
        try {
            String sqlMax = "SELECT EMP_PIN FROM " + PstEmployee.TBL_HR_EMPLOYEE + " WHERE EMP_PIN != \"\"";
            dbrs = DBHandler.execQueryResult(sqlMax);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                String strMax = rs.getString(1);
                int tmpValue = -1;
                try {
                    tmpValue = Integer.parseInt(strMax);
                } catch (Exception ex) {
                }
                if (max < tmpValue) {
                    max = tmpValue;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return max;
    }

    public static boolean setAllEmpFingerPrintNumber() {

        boolean status = false;
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN] + " LIKE \"\" OR " + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN] + " IS NULL";
        Vector vEmployee = new Vector(1, 1);
        vEmployee = PstEmployee.list(0, 0, whereClause, null);
        System.out.println("===========================================");
        System.out.println("Starting to update employee finger print id");
        System.out.println("===========================================");
        int iMaxFingerPrintNumber = 0;
        int iAddFingerPrintNumber = 0;
        iAddFingerPrintNumber = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("FINGER_PRINT_NUMBER")));
        iMaxFingerPrintNumber = getMaxFingerPrintNumber();
        System.out.println(" ::: MAX FINGER PRINT ID = " + iMaxFingerPrintNumber);
        System.out.println(" ::: MAX DATA EMPLOYE    = " + vEmployee.size());
        for (int i = 0; i < vEmployee.size(); i++) {
            Employee emp = new Employee();
            emp = (Employee) vEmployee.get(i);
            if (iMaxFingerPrintNumber > -1) {
                iMaxFingerPrintNumber += iAddFingerPrintNumber;
            } else {
                iMaxFingerPrintNumber = 0;
            }
            emp.setEmpPin(String.valueOf(iMaxFingerPrintNumber));
            PstEmployee.updateBarcodeAndPin(emp.getOID(), emp.getBarcodeNumber(), emp.getEmpPin());
            System.out.println(">> Update OID : " + emp.getOID() + " ::: EMP FINGER PRINT ID : " + emp.getEmpPin());
        }
        status = true;
        System.out.println("======================================= End update finger print id");
        return status;
    }

    // added by Bayu
    public static int listEmployeeCategMale(long oidDept, long oidSect, long oidEmployeeCategory, long oidRace) {
        DBResultSet dbrs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) "
                    + "FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstRace.TBL_RACE + " AS RACE "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE] + " = RACE." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CATEG "
                    + " ON CATEG." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "= DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SECT "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "= SECT." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " WHERE (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.MALE + ")";

            if (oidDept != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDept + ")";
            }

            if (oidSect != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + oidSect + ")";
            }

            if (oidEmployeeCategory != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + oidEmployeeCategory + ")";
            }

            if (oidRace != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE] + " = " + oidRace + ")";
            }

            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            System.out.println("SQL CATEG MALE = " + sql);

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static int listEmployeeCategFemale(long oidDept, long oidSect, long oidEmployeeCategory, long oidRace) {
        DBResultSet dbrs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) "
                    + "FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstRace.TBL_RACE + " AS RACE "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE] + " = RACE." + PstRace.fieldNames[PstRace.FLD_RACE_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CATEG "
                    + " ON CATEG." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "= DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SECT "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "= SECT." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " WHERE (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + " = " + PstEmployee.FEMALE + ")";

            if (oidDept != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDept + ")";
            }

            if (oidSect != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + oidSect + ")";
            }

            if (oidEmployeeCategory != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + oidEmployeeCategory + ")";
            }

            if (oidRace != 0) {
                sql = sql + " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE] + " = " + oidRace + ")";
            }

            sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            System.out.println("SQL CATEG FEMALE = " + sql);

            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;

        } catch (Exception ex) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static Vector listEmployeeByPositionLevelGeneralM(Employee offsetEmployee, Vector vectPositionLevel) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        String strPositionLevel = "";
        if (vectPositionLevel != null && vectPositionLevel.size() > 0) {
            int maxPositionLevel = vectPositionLevel.size();
            for (int i = 0; i < maxPositionLevel; i++) {
                strPositionLevel = strPositionLevel + vectPositionLevel.get(i) + ",";
            }

            if (strPositionLevel != null && strPositionLevel.length() > 0) {
                strPositionLevel = strPositionLevel.substring(0, strPositionLevel.length() - 1);
            }
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + " IN (" + strPositionLevel + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

            System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(1));
                employee.setEmployeeNum(rs.getString(2));
                employee.setFullName(rs.getString(3));
                result.add(employee);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector listEmployeeByPositionLevelGeneralM(Vector vectPositionLevel) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        String strPositionLevel = "";
        if (vectPositionLevel != null && vectPositionLevel.size() > 0) {
            int maxPositionLevel = vectPositionLevel.size();
            for (int i = 0; i < maxPositionLevel; i++) {
                strPositionLevel = strPositionLevel + vectPositionLevel.get(i) + ",";
            }

            if (strPositionLevel != null && strPositionLevel.length() > 0) {
                strPositionLevel = strPositionLevel.substring(0, strPositionLevel.length() - 1);
            }
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + " IN (" + strPositionLevel + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

            System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(1));
                employee.setEmployeeNum(rs.getString(2));
                employee.setFullName(rs.getString(3));
                result.add(employee);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static void updateEmpNum_Pin(long employeeId, String userPin) {

        DBResultSet dbrs = null;

        try {

            String sql = "UPDATE " + PstEmployee.TBL_HR_EMPLOYEE + " SET "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN] + " = '" + userPin + "' WHERE "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + employeeId;

            DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * Create By : satrya 2013-12-23 Keterangan: untuk mengganti password
     *
     * @param employeeId
     * @param userName
     * @param password
     */
    public static void updateEmpPassword(long employeeId, String password) {

        DBResultSet dbrs = null;

        try {

            String sql = "UPDATE " + PstAppUser.TBL_APP_USER + " SET "
                    //+ PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID] + " = '" + userName  + "',"
                    + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD] + " = '" + password
                    + "' WHERE "
                    + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + " = " + employeeId;

            DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static void setEmployeePin() {

        DBResultSet dbrs = null;

        Vector listEmployee = PstEmployee.listAll();

        if (listEmployee != null && listEmployee.size() > 0) {

            for (int i = 0; i < listEmployee.size(); i++) {

                Employee employee = (Employee) listEmployee.get(i);

                updatePIN(employee.getOID(), employee.getEmployeeNum());

            }
        }

    }

    public static void updatePIN(long employee_id, String pin) {

        DBResultSet dbrs = null;

        try {

            String sql = "UPDATE " + PstEmployee.TBL_HR_EMPLOYEE + " SET "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN] + " = '" + pin + "' WHERE "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + employee_id;

            DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * @Author : Roy Andika
     * @Desc : Untuk menset barcode number sesuai dengan employee number(untuk
     * kasus nikko)
     */
    public static void setBarcodeNumber() {

        String sqlEmployee = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " != '.' AND "
                + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " != '-' ";
        String orderEmployee = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

        Vector employee = new Vector();

        try {

            employee = PstEmployee.list(0, 0, sqlEmployee, orderEmployee);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        if (employee != null && employee.size() > 0) {

            for (int i = 0; i < employee.size(); i++) {

                Employee objEmployee = new Employee();

                objEmployee = (Employee) employee.get(i);

                /* Untuk mengupdate database */
                if (objEmployee.getEmployeeNum() != null && objEmployee.getEmployeeNum().length() > 0) {

                    updateBarcodeNumber(objEmployee);
                }
            }
        }
    }

    /**
     * @Author Roy Andika
     * @Desc Untuk mengupdate barcode number sesuai dengan employee number
     * @param employee
     */
    public static void updateBarcodeNumber(Employee employee) {

        try {

            String sql = "UPDATE " + PstEmployee.TBL_HR_EMPLOYEE + " SET " + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + " = '" + employee.getEmployeeNum() + "' WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + employee.getOID();

            System.out.println("[sql] " + sql);

            DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("[exception] " + e.toString());
        }

    }

    public static void insertToAccess() {

        deleteUserInfo();

        String sqlEmployee = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " != '.' AND "
                + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " != '-' ";

        //+" AND "+PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]+" IS NOT NULL";

        String orderEmployee = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

        Vector employee = new Vector();

        try {

            employee = PstEmployee.list(0, 0, sqlEmployee, orderEmployee);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }


        if (employee != null && employee.size() > 0) {

            for (int i = 0; i < employee.size(); i++) {

                Employee objEmployee = new Employee();

                objEmployee = (Employee) employee.get(i);

                insertToAtt2008(objEmployee);

            }
        }
    }

    /**
     * @Author Roy Andika
     * @Desc Untuk memasukan data employee ke database mesin.
     * @param employee
     * @return yes/no
     */
    public static boolean insertToAtt2008(Employee employee) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        Connection cn = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String budgeNumber = "";

            /**
             * @Desc :Untuk mengecek barcode number apakah sama dengan null atau
             * desa
             */
            /*
             if(employee.getBarcodeNumber() != null && employee.getBarcodeNumber().length() > 0 ){

             budgeNumber = employee.getBarcodeNumber();

             }else{

             boolean udpateEmp = false;

             udpateEmp = insertBarcodeNumHarisma(employee.getOID(),employee.getEmployeeNum());

             if(udpateEmp == true){

             budgeNumber = employee.getEmployeeNum();

             }
             }
             */
            if (employee.getEmployeeNum() != null && employee.getEmployeeNum().length() > 0) {

                budgeNumber = employee.getEmployeeNum();

            }


            if (budgeNumber != null && budgeNumber.length() > 0) {

                String fullName = employee.getFullName();

                /*
                 if(employee.getFullName().length() >8){
                 fullName = employee.getFullName().substring(0,8);
                 }else{
                 fullName = employee.getFullName();
                 }
                 */
                int VERIFICATIONMETHOD = 1;
                int DEFAULTDEPTID = 1;
                int ATT = 1;
                int INLATE = 0;
                int OUTEARLY = 0;
                int OVERTIME = 1;
                int SEP = 1;
                int HOLIDAY = 1;
                int LUNCHDURATION = 1;
                int privilege = 0;
                int InheritDeptSch = 1;
                int AutoSchPlan = 1;
                int MinAutoSchInterval = 24;
                int RegisterOT = 1;
                int InheritDeptRule = 1;
                int EMPRIVILEGE = 0;

                String sql = "INSERT INTO USERINFO ( Badgenumber, Name, VERIFICATIONMETHOD, DEFAULTDEPTID, ATT, "
                        + "INLATE, OUTEARLY, OVERTIME, SEP, HOLIDAY, LUNCHDURATION, privilege, "
                        + "InheritDeptSch, AutoSchPlan, MinAutoSchInterval, RegisterOT, "
                        + "InheritDeptRule, EMPRIVILEGE, Pin1 ) VALUES ('" + budgeNumber + "','" + fullName + "'," + VERIFICATIONMETHOD + ","
                        + DEFAULTDEPTID + "," + ATT + "," + INLATE + "," + OUTEARLY + "," + OVERTIME + "," + SEP + "," + HOLIDAY + "," + LUNCHDURATION + ","
                        + privilege + "," + InheritDeptSch + "," + AutoSchPlan + "," + MinAutoSchInterval + "," + RegisterOT + "," + InheritDeptRule + ","
                        + EMPRIVILEGE + ",'" + budgeNumber + "')";

                System.out.println("QUERY : " + sql);
                Statement stm = cn.createStatement();
                stm.executeUpdate(sql);
                stm.close();
            } else {
                System.out.println("[gagal insert]:::" + employee.getFullName());
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }

        return false;
    }

    public static void deleteUserInfo() {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;
        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String sql = "DELETE FROM USERINFO";

            stm = cn.createStatement();
            stm.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }
    }

    public static boolean insertBarcodeNumHarisma(long employeeId, String employeeNum) {

        try {

            String sql = "UPDATE " + PstEmployee.TBL_HR_EMPLOYEE + " SET "
                    + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + " = " + employeeNum
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + employeeId;

            DBHandler.execUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
            return false;
        }
    }

    public static void UpdateSts() {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;
        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String sql = "UPDATE CHECKINOUT SET STATUS = 1 WHERE CHECKTIME < '2010-07-14 00:00:00'";

            stm = cn.createStatement();
            stm.executeUpdate(sql);


        } catch (Exception e) {
            System.out.println();
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }
    }

    /**
     * @Author Roy Anduika
     * @param barcode_old
     * @param barcode
     * @param name
     * @return
     */
    public static boolean updateBarcodeAtt2010(String barcode_old, String barcode, String name) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            Connection cn = DriverManager.getConnection(cs);

            String sql = "UPDATE USERINFO SET Badgenumber = '" + barcode + "',Pin1 = '" + barcode + "' WHERE Badgenumber ='" + barcode_old + "'";

            Statement stm = cn.createStatement();
            stm.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        return false;
    }

    public static boolean updateFullNameAtt2010(String barcode, String name) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String sql = "UPDATE USERINFO SET Name = '" + name + "' WHERE Badgenumber ='" + barcode + "'";

            stm = cn.createStatement();
            stm.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("[exception] " + e.toString());
            }
        }
        return false;
    }

    public static boolean insertUserInfo(String budgeNumber, String fullName) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            int VERIFICATIONMETHOD = 1;
            int DEFAULTDEPTID = 1;
            int ATT = 1;
            int INLATE = 0;
            int OUTEARLY = 0;
            int OVERTIME = 1;
            int SEP = 1;
            int HOLIDAY = 1;
            int LUNCHDURATION = 1;
            int privilege = 0;
            int InheritDeptSch = 1;
            int AutoSchPlan = 1;
            int MinAutoSchInterval = 24;
            int RegisterOT = 1;
            int InheritDeptRule = 1;
            int EMPRIVILEGE = 0;

            String sql = "INSERT INTO USERINFO ( Badgenumber, Name, VERIFICATIONMETHOD, DEFAULTDEPTID, ATT, "
                    + "INLATE, OUTEARLY, OVERTIME, SEP, HOLIDAY, LUNCHDURATION, privilege, "
                    + "InheritDeptSch, AutoSchPlan, MinAutoSchInterval, RegisterOT, "
                    + "InheritDeptRule, EMPRIVILEGE, Pin1 ) VALUES (" + budgeNumber + ",'" + fullName + "'," + VERIFICATIONMETHOD + ","
                    + DEFAULTDEPTID + "," + ATT + "," + INLATE + "," + OUTEARLY + "," + OVERTIME + "," + SEP + "," + HOLIDAY + "," + LUNCHDURATION + ","
                    + privilege + "," + InheritDeptSch + "," + AutoSchPlan + "," + MinAutoSchInterval + "," + RegisterOT + "," + InheritDeptRule + ","
                    + EMPRIVILEGE + "," + budgeNumber + ")";

            stm = cn.createStatement();
            stm.executeUpdate(sql);

            return true;

        } catch (Exception e) {

            System.out.println("Exception " + e.toString());

        } finally {

            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }

        return false;
    }

    /**
     * @Author Roy Andika
     * @param barcodeNumber
     * @return
     */
    public static boolean delDbFingerSpot(String barcodeNumber) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String sql = "DELETE FROM " + Att2000.Tbl_UserInfo + " WHERE " + Att2000.Fld_Badgenumber + " = '" + barcodeNumber + "'";

            stm = cn.createStatement();
            stm.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }
        return false;
    }

    /**
     * @Desc method untuk nikko untuk menyamakan barcode number dengan employee
     * number
     */
    public static void updateBarcodeNumberEmployee() {

        updateEmployeeNumberEmployeeResigned();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ","
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ","
                    + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " WHERE "
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                    + " ORDER BY " + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));

                /* Jika kondisinya barcode number dalam keadaan null*/
                if (rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]) == null || rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]).length() <= 0) {

                    prosesUpdateBarcodeNumberEmployee(employee);

                } else {

                    /* Jika employee number dan barcode number tidak sama */
                    if (rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).compareTo(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER])) != 0) {

                        prosesUpdateBarcodeNumberEmployee(employee);

                    }
                }
            }

        } catch (Exception E) {
            System.out.println();
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    private static void prosesUpdateBarcodeNumberEmployee(Employee employee) {

        DBResultSet dbrs = null;

        try {

            String sql = "UPDATE " + PstEmployee.TBL_HR_EMPLOYEE + " SET "
                    + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + "= '" + employee.getEmployeeNum() + "' "
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + employee.getOID();

            DBHandler.execUpdate(sql);

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * @Author Roy Andika
     * @Desc Untuk mengupdate employee yang sudah expired
     */
    public static void updateEmployeeNumberEmployeeResigned() {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ","
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ","
                    + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " WHERE "
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.YES_RESIGN
                    + " ORDER BY " + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int no = 0;

            while (rs.next()) {

                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));

                /* Jika kondisinya barcode number dalam keadaan null*/
                if (rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]) == null || rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]).length() <= 0) {

                    updateBarcodeResigned(employee, no);

                } else {
                    /* Jika employee number dan barcode number tidak sama */
                    if (rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).compareTo(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER])) != 0) {

                        updateBarcodeResigned(employee, no);

                    }
                }

                no++;
            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * @Author Roy A.
     * @Desc Untuk mengupdate data employee number dimana employee nya sudah
     * expired
     * @param employee
     * @param countExpired
     */
    private static void updateBarcodeResigned(Employee employee, int countExpired) {

        DBResultSet dbrs = null;

        try {

            String ext = employee.getEmployeeNum() + "_EXP_" + countExpired;

            String sql = "UPDATE " + PstEmployee.TBL_HR_EMPLOYEE + " SET "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + ext + "' WHERE "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employee.getOID();

            DBHandler.execUpdate(sql);

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * @aut : Roy Andika
     * @Desc: Untuk menghapus data pada table checkInOut
     */
    public static void delCheckInOut() {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String sql = "DELETE FROM " + Att2000.Tbl_CheckInOut;

            stm = cn.createStatement();
            stm.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }
    }

    /**
     * @Author : Roy Andika
     * @Desc : Untuk Merubah status data access sehingga data tersebut bisa di
     * proses
     */
    public static void UpdateStsUncheck() {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String sql = "UPDATE CHECKINOUT SET STATUS = 0 WHERE SENSORID = '1'";

            stm = cn.createStatement();
            stm.executeUpdate(sql);


        } catch (Exception e) {
            System.out.println();
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }
    }

    /**
     * @Desc Untuk menghitung sum report dp detail
     *
     * @param employeeId
     * @param srcLeaveManagement
     * @return
     */
    public static int countDetailDpStock(long employeeId, SrcLeaveManagement srcLeaveManagement) {

        int result = 0;
        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + ") "
                    + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DSM "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN;

            /* Untuk mengecek apakah level period ada atau sama dengan nll*/
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {

                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);

                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                sql = sql + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                        + " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd")
                        + " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";

            } else if (srcLeaveManagement.getPeriodId() > 0) {
                Period period = new Period();
                try {
                    period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }
                Date dtStartDate = (Date) period.getStartDate().clone();
                Date dtEndDate = (Date) period.getEndDate().clone();

                sql = sql + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                        + " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd")
                        + " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            sql = sql + " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            System.out.println("LIST SUM SQL = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getInt(1);
            }

            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /**
     * @Author Roy Andika
     * @Des Untuk mendapatkan employee yang ada di harisma
     * @return
     */
    private static Vector empHarisma() {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + " as barcodeNumber,"
                    + "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " as fullName,"
                    + "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " as employeeNum,"
                    + "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " as depId,"
                    + "DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " as dep "
                    + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT
                    + " DEP ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " WHERE "
                    + " ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " != '-' AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " != '.' ) AND EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER];


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {

                try {
                    if (rs.getString("barcodeNumber") != null && rs.getString("barcodeNumber").length() > 0 && rs.getString("employeeNum") != null && rs.getString("employeeNum").length() > 0) {

                        try {
                            EmployeeHarisma employeeHarisma = new EmployeeHarisma();
                            employeeHarisma.setBarcodeNUmber(rs.getString("barcodeNumber"));
                            employeeHarisma.setDepartment(rs.getString("dep"));
                            employeeHarisma.setDepartmentId(rs.getLong("depId"));
                            employeeHarisma.setFullName(rs.getString("fullName"));
                            employeeHarisma.setEmployeeNumber(rs.getString("employeeNum"));

                            result.add(employeeHarisma);
                        } catch (Exception E) {
                            System.out.println("[exception] " + E.toString());
                        }
                    }
                } catch (Exception E) {
                    System.out.println("[exception] " + E.toString() + " : " + rs.getString("employeeNum") + " : " + rs.getString("fullName"));
                }

            }

            return result;

        } catch (Exception E) {
            System.out.println("[exception] " + E.toString());
        } finally {
            try {
                DBResultSet.close(dbrs);
            } catch (Exception E) {
                System.out.println("[exception] " + E.toString());
            }
        }

        return null;

    }

    /**
     * @Author Roy Andika
     * @Desc Untuk mendapatkan list employee yang ada di mesin
     * @return
     */
    public static Vector empMachine() {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            db_access = "";
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;


        if (!db_access.equals("")) {

            try {

                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String cs = "jdbc:odbc:" + db_access;
                cn = DriverManager.getConnection(cs);

                String sql = "SELECT " + Att2000.Fld_Badgenumber + ","
                        + Att2000.Fld_Name
                        + " FROM " + Att2000.Tbl_UserInfo;

                stm = cn.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                Vector result = new Vector();

                while (rs.next()) {

                    EmployeeMachine employeeMachine = new EmployeeMachine();
                    employeeMachine.setEmployeeNum(rs.getString(Att2000.Fld_Badgenumber));
                    employeeMachine.setFullName(rs.getString(Att2000.Fld_Name));
                    result.add(employeeMachine);

                }

                return result;

            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            } finally {
                try {
                    stm.close();
                    cn.close();
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }
            }
        }

        return null;

    }

    /**
     * @Author Roy Andika
     * @Desc Untuk membandingkan antara data employee yang ada di harisma dengan
     * employee yang ada di mesin
     * @return
     */
    public static Vector listCompare() {

        Vector empHarisma = empHarisma();
        Vector empMachine = empMachine();

        Vector result = new Vector();

        if (empHarisma != null && empHarisma.size() > 0) {

            String nameExcp = "";
            String payrollExcp = "";

            boolean found = false;
            for (int idxHarisma = 0; idxHarisma < empHarisma.size(); idxHarisma++) {

                found = false;

                try {

                    EmployeeHarisma employeeHarisma = (EmployeeHarisma) empHarisma.get(idxHarisma);
                    //update by satrya 2013-02-16
                    if (empMachine != null && empMachine.size() > 0) {
                        for (int xMachine = 0; xMachine < empMachine.size(); xMachine++) {

                            EmployeeMachine employeeMachine = (EmployeeMachine) empMachine.get(xMachine);

                            if (employeeHarisma.getBarcodeNUmber().trim().equalsIgnoreCase(employeeMachine.getEmployeeNum().trim())) {
                                empHarisma.remove(idxHarisma);
                                idxHarisma--;
                                empMachine.remove(xMachine);
                                found = true;
                                break;
                            }
                        }


                        if (found == false) {

                            HarismaVsMachine harismaVsMachine = new HarismaVsMachine();

                            harismaVsMachine.setDepartmentHarisma(employeeHarisma.getDepartment());
                            harismaVsMachine.setDepartmentIdHarsima(employeeHarisma.getDepartmentId());
                            harismaVsMachine.setEmployeeNumHarisma(employeeHarisma.getEmployeeNumber());
                            harismaVsMachine.setFullNameHarisma(employeeHarisma.getFullName());
                            harismaVsMachine.setFullNameMachine("");
                            harismaVsMachine.setBarcodeMachine("");

                            result.add(harismaVsMachine);
                            empHarisma.remove(idxHarisma);
                            idxHarisma--;
                        }
                    }
                } catch (Exception E) {
                    System.out.println("Exception " + E.toString() + " : " + payrollExcp + " : " + nameExcp);
                }
            }
        }


        for (int xMch = 0; xMch < empMachine.size(); xMch++) {

            EmployeeMachine employeeMachine = (EmployeeMachine) empMachine.get(xMch);

            HarismaVsMachine harismaVsMachine = new HarismaVsMachine();

            harismaVsMachine.setDepartmentHarisma("");
            harismaVsMachine.setDepartmentIdHarsima(0);
            harismaVsMachine.setEmployeeNumHarisma("");
            harismaVsMachine.setFullNameHarisma("");
            harismaVsMachine.setFullNameMachine(employeeMachine.getFullName());
            harismaVsMachine.setBarcodeMachine(employeeMachine.getEmployeeNum());

            result.add(harismaVsMachine);

        }

        if (result != null && result.size() > 0) {
            return result;
        }

        return null;

    }

    /**
     * @return the MIN_BANK_ACC_LENGTH
     */
    public static int getMIN_BANK_ACC_LENGTH() {
        return MIN_BANK_ACC_LENGTH;
    }

    /**
     * @param aMIN_BANK_ACC_LENGTH the MIN_BANK_ACC_LENGTH to set
     */
    public static void setMIN_BANK_ACC_LENGTH(int aMIN_BANK_ACC_LENGTH) {
        MIN_BANK_ACC_LENGTH = aMIN_BANK_ACC_LENGTH;
    }

    //gede (where clause srcemployee.jsp)
    public String whereList(SrcEmployee srcEmployee) {
        DBResultSet dbrs = null;

        String whereClause = "";

        try {

            if ((srcEmployee.getName() != null) && (srcEmployee.getName().length() > 0)) {
                Vector vectName = logicParser(srcEmployee.getName());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if ((srcEmployee.getAddress() != null) && (srcEmployee.getAddress().length() > 0)) {
                Vector vectAddr = logicParser(srcEmployee.getAddress());
                if (vectAddr != null && vectAddr.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectAddr.size(); i++) {
                        String str = (String) vectAddr.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if ((srcEmployee.getEmpnumber() != null) && (srcEmployee.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcEmployee.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            //System.out.println("srcEmployee.getStartCommenc = "+srcEmployee.getStartCommenc());
            //System.out.println("srcEmployee.getEndCommenc = "+srcEmployee.getEndCommenc());

            if ((srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "' AND ";
            }

            if ((srcEmployee.getResigned() == 1) && (srcEmployee.getStartResign() != null) && (srcEmployee.getEndResign() != null)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartResign(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndResign(), "yyyy-MM-dd") + "' AND ";
            }
            if (srcEmployee.getCompanyId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcEmployee.getCompanyId() + " AND ";
            }
            if (srcEmployee.getDepartment() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcEmployee.getDepartment() + " AND ";
            }
            //System.out.println("department"+srcEmployee.getDepartment());

            if (srcEmployee.getDivisionId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcEmployee.getDivisionId() + " AND ";
            }

            if (srcEmployee.getPosition() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcEmployee.getPosition() + " AND ";
            }


            if (srcEmployee.getSection() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcEmployee.getSection() + " AND ";
            }

            if (srcEmployee.getMaritalStatus() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + srcEmployee.getMaritalStatus() + " AND ";
            }

            if (srcEmployee.getRaceId() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                        + " = " + srcEmployee.getRaceId() + " AND ";
            }

            if (!srcEmployee.isBirthdayChecked()) {
                System.out.println("Bithday checked : " + srcEmployee.isBirthdayChecked());
                System.out.println("Bithday : " + srcEmployee.getBirthday());
                System.out.println("Bithmon : " + srcEmployee.getBirthmonth());

                if (srcEmployee.getBirthday() != null) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthday().getMonth() + 1) + "' AND ";
                }

                if (srcEmployee.getBirthmonth() > 0) {
                    whereClause = whereClause + " MONTH(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                            + ") = '" + (srcEmployee.getBirthmonth()) + "' AND ";
                }
            }

            if (srcEmployee.getSalaryLevel().length() > 0) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " = '" + srcEmployee.getSalaryLevel() + "' AND ";
            }

            if (srcEmployee.getResigned() < 2) {
                whereClause += " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned() + " AND ";
            }

            if (srcEmployee.getEmpCategory() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " = " + srcEmployee.getEmpCategory() + " AND ";
            }

            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian by religion,edited by Yunny
            if (srcEmployee.getReligion() != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + srcEmployee.getReligion() + " AND ";
            }
            //---------------------
            //kondisi ini ditambahkan untuk keperluan intimas jika pencarian employee by gender,edited by Yunny
            if (srcEmployee.getGender() < 2) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + " = " + srcEmployee.getGender() + " AND ";
            }
            //----------------------------

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = "" + whereClause.substring(0, whereClause.length() - 4);
                //sql = sql + whereClause;
            }


        } catch (Exception e) {
            System.out.println("\t Exception on  whereList : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return whereClause;
    }
    
     /**
     * Create by satrya 2014-02-03
     * untuk list hashtable period
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
     public static Hashtable hashlistTblEmpDiff(long idDepartment, long idDivision, long idSection, String searchNrFrom,
            String searchNrTo, String searchName, long periodId, int statusData, boolean inclResign) {
        Hashtable hashtablePeriod = new Hashtable();
        DBResultSet dbrs = null;
        try {
           String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    //update by satrya 2013-01-20
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]
                    //+ ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_BANK_ADDRESS]
                    //update by satrya 2013-05-06
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_DATE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ((statusData == PstPayEmpLevel.CURRENT || statusData == PstPayEmpLevel.HISTORY) ? (" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + statusData /*PstPayEmpLevel.CURRENT */) : "")
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPayPeriod.TBL_HR_PAY_PERIOD + " AS PER ON PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + "="
                    + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                    //update by satrya 2013-01-20
                    //+ " INNER JOIN "+ PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] 
                    + " LEFT JOIN " + PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                    + " = BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]
                    + " WHERE "
                    + " ( (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + "  ) || ("
                    + "PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " ) || "
                    + " (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] + " ))"
                    /*" LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0" */
                  //  + (inclResign ? "" : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 ") + " ";
                   // + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " > PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ";
          + " AND ( EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " > PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ) ";
          
            String whereClause = "";

            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment + " AND ";
            }

            //System.out.println("department"+srcEmployee.getDepartment());

            if (idDivision != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + idDivision + " AND ";
            }

            if (idSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + idSection + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }
            if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }

            /*if(dataStatus < 2){
             if(dataStatus==0)
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             if(dataStatus==1){
             Date now = new Date();
             int monthNow =now.getMonth()+1;
             int yearNow = now.getYear()+1900;
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             }
             }*/




            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }




            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + 
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] ;            
                    
            //System.out.println("\t SQL listEmpPaySlip : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashtablePeriod.put(rs.getLong("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), rs.getString("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
            }
            rs.close();
            return hashtablePeriod;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashtablePeriod;
    }
         
/**
     Priska(10 jan 2015 ) to macth period to level
     *
     * @param idDepartment
     * @param idDivision
     * @param idSection
     * @param searchNrFrom
     * @param searchNrTo
     * @param searchName
     * @param periodId
     * @param statusData : -1=both current of history
     * @param inclResign
     * @return
     */
    public static Vector listEmpPaySlipDiff(long idDepartment, long idDivision, long idSection, String searchNrFrom,
            String searchNrTo, String searchName, long periodId, int statusData, boolean inclResign) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("searchNrFrom)" + searchNrFrom);
        if (idDepartment == 0 && idDivision == 0 && idSection == 0 && searchNrFrom == null && searchNrTo == null && searchName == null && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    //update by satrya 2013-01-20
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]
                    //+ ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_BANK_ADDRESS]
                    //update by satrya 2013-05-06
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_DATE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ((statusData == PstPayEmpLevel.CURRENT || statusData == PstPayEmpLevel.HISTORY) ? (" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + statusData /*PstPayEmpLevel.CURRENT */) : "")
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPayPeriod.TBL_HR_PAY_PERIOD + " AS PER ON PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + "="
                    + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                    //update by satrya 2013-01-20
                    //+ " INNER JOIN "+ PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] 
                    + " LEFT JOIN " + PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                    + " = BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]
                    + " WHERE "
                    + " ( (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + "  ) || ("
                    + "PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " ) || "
                    + " (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] + " ))"
                    /*" LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0" */
                    //+ (inclResign ? "" : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 ") + " ";
                   // + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " > PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ";
                    + " AND ( EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " > PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ) ";
          
            String whereClause = "";

            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment + " AND ";
            }

            //System.out.println("department"+srcEmployee.getDepartment());

            if (idDivision != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + idDivision + " AND ";
            }

            if (idSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + idSection + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }
            if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }

            /*if(dataStatus < 2){
             if(dataStatus==0)
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             if(dataStatus==1){
             Date now = new Date();
             int monthNow =now.getMonth()+1;
             int yearNow = now.getYear()+1900;
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             }
             }*/




            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }




            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + 
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] ;            
                    
            //System.out.println("\t SQL listEmpPaySlip : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
           
                Employee employee = new Employee();
    

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setDivisionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]));
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
             
                result.add(employee);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }       
/**
     * This method used to list employee pay slip Created By Yunny update by
     * Priska(08 jan 2014 ) to macth period to level
     *
     * @param idDepartment
     * @param idDivision
     * @param idSection
     * @param searchNrFrom
     * @param searchNrTo
     * @param searchName
     * @param periodId
     * @param statusData : -1=both current of history
     * @param inclResign
     * @return
     */
    public static Vector listEmpPaySlipPerdepart(long idDepartment, long idDivision, long idSection, String searchNrFrom,
            String searchNrTo, String searchName, long periodId, int statusData, boolean inclResign) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        System.out.println("searchNrFrom)" + searchNrFrom);
        PayPeriod payPeriod =  new PayPeriod();
        try {
            payPeriod = PstPayPeriod.fetchExc(periodId);
        } catch (Exception e){
            System.out.print("no periode ");
        }
        
        
        if (idDepartment == 0 && idDivision == 0 && idSection == 0 && searchNrFrom == null && searchNrTo == null && searchName == null && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    //update by satrya 2013-01-20
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]
                    + ", BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]
                    + ", BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]
                    //+ ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_BANK_ADDRESS]
                    //update by satrya 2013-05-06
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]
                    + ", SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_DATE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " LEFT JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + ((statusData == PstPayEmpLevel.CURRENT || statusData == PstPayEmpLevel.HISTORY) ? (" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + statusData /*PstPayEmpLevel.CURRENT */) : "")
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP"
                    + " ON EMP." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPayPeriod.TBL_HR_PAY_PERIOD + " AS PER ON PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + "="
                    + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                    //update by satrya 2013-01-20
                    //+ " INNER JOIN "+ PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] 
                    + " LEFT JOIN " + PstPayBanks.TBL_PAY_BANKS + " as BANK on LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]
                    + " = BANK." + PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]
                    + " WHERE "
                    + " ( (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + "  ) || ("
                    + "PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " ) || "
                    + " (PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " <= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE]
                    + " && PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " >= LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_END_DATE] + " ))"
                    /*" LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=0" */
                    + " AND ( EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 OR EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " > PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ) ";
          
                  //  + (inclResign ? "" : " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0 OR  EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " > PER." + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ") + " ";

            String whereClause = "";

            if (idDepartment != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + idDepartment + " AND ";
            }
            
            
            
            //System.out.println("department"+srcEmployee.getDepartment());

            if (idDivision != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + idDivision + " AND ";
            }

            if (idSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + idSection + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }
            if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }

            /*if(dataStatus < 2){
             if(dataStatus==0)
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             if(dataStatus==1){
             Date now = new Date();
             int monthNow =now.getMonth()+1;
             int yearNow = now.getYear()+1900;
             whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
             " = "+ dataStatus + " AND ";
             }
             }*/




            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }




            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + 
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] ;            
                    
            //System.out.println("\t SQL listEmpPaySlip : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                PaySlip paySlip = new PaySlip();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setDivisionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]));
                employee.setCompanyId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                payEmpLevel.setEmployeeId(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]));
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                payEmpLevel.setStartDate(rs.getDate(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE]));
                //update by satrya 2013-01-20
                payEmpLevel.setBankAccNr(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ACC_NR]));
                payEmpLevel.setBankId(rs.getLong(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID]));

                vect.add(payEmpLevel);

                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                //update by satrya 2013-01-20
                paySlip.setBankName(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]));
                paySlip.setPaySlipDate(rs.getDate(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_DATE]));
                //paySlip.setBankAddress(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ADDRESS]));
                if (rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]) == null || rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]).equalsIgnoreCase("null")) {
                    paySlip.setPrivateNote("");
                } else {
                    paySlip.setPrivateNote(rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]));
                }
                paySlip.setOvIdxAdj(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]));
                vect.add(paySlip);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }    
      
    
    public static Vector<EmployeeSum> listEducationSummary( long level_id, SrcEmployee srcEmployee, long oidPosition) {
        DBResultSet dbrs = null;
        int count = 0;
        Vector<EmployeeSum>  vSumEdu = new Vector();
        try {
            String sql = "SELECT EDEMP."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+",EDU."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION] +
                    ",EDU."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_DESC] +", COUNT(EDEMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") AS SUMEMP "
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " INNER JOIN " + PstEmpEducation.TBL_HR_EMP_EDUCATION + " EDEMP "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstEducation.TBL_HR_EDUCATION + " EDU "
                    + " ON EDU." + PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID] + " = EDEMP." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]
                    + " GROUP BY EDU."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID];


            if (level_id != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + level_id;
            }

            if (srcEmployee!=null && (srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "'";
            }
            if (oidPosition != 0) {

                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + oidPosition;
            }

            if (srcEmployee!=null && srcEmployee.getResigned() < 2) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned();
            }
            //System.out.println("sql jumlah ::::::::::::::::::::::"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
               EmployeeSum empSum = new EmployeeSum();
               empSum.setReportItemId(rs.getLong(PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]));
               empSum.setReportItemName(rs.getString(PstEducation.fieldNames[PstEducation.FLD_EDUCATION]));
               empSum.setReportItemDesc(rs.getString(PstEducation.fieldNames[PstEducation.FLD_EDUCATION_DESC]));
               empSum.setReportItemNumber(rs.getFloat("SUMEMP"));
               vSumEdu.add(empSum);
            }

            rs.close();
            return vSumEdu;

        } catch (Exception ex) {
            return vSumEdu;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    

    public static Vector<EmployeeSum> listAgeSummary( long level_id, SrcEmployee srcEmployee, long oidPosition) {
        DBResultSet dbrs = null;
        int count = 0;
        Vector<EmployeeSum>  vSumEdu = new Vector();
        try {
            Date now = new Date();
            now.setMonth(0);
            now.setDate(1);
            now.setMonth(0);

            String sql = "SELECT \"<20\" AS AGE, COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` > \""+(now.getYear()-20+1900)+"-01-01\" AND EMP.`RESIGNED`=0 " +
            " UNION SELECT \"20<=Age<25\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-25+1900)+"-01-01\" AND \""+(now.getYear()-21+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"25<=Age<30\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-30+1900)+"-01-01\" AND \""+(now.getYear()-26+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"30<=Age<35\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-35+1900)+"-01-01\" AND \""+(now.getYear()-31+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"35<=Age<40\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-40+1900)+"-01-01\" AND \""+(now.getYear()-36+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"40<=Age<45\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-45+1900)+"-01-01\" AND \""+(now.getYear()-41+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"45<=Age<50\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-50+1900)+"-01-01\" AND \""+(now.getYear()-46+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"50<=Age<55\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-55+1900)+"-01-01\" AND \""+(now.getYear()-51+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"55<=Age<60\" , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` BETWEEN \""+(now.getYear()-60+1900)+"-01-01\" AND \""+(now.getYear()-56+1900)+"-12-31\" AND EMP.`RESIGNED`=0 "+
            " UNION SELECT \"60>=\"       , COUNT(EMP.`BIRTH_DATE`) AS NUM FROM hr_employee EMP WHERE EMP.`BIRTH_DATE` <= \""+(now.getYear()-65+1900)+"-01-01\" AND EMP.`RESIGNED`=0 ";


            if (level_id != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + level_id;
            }

            if (srcEmployee!=null && (srcEmployee.getStartCommenc() != null) && (srcEmployee.getEndCommenc() != null)) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate(srcEmployee.getStartCommenc(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(srcEmployee.getEndCommenc(), "yyyy-MM-dd") + "'";
            }
            if (oidPosition != 0) {

                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + oidPosition;
            }

            if (srcEmployee!=null && srcEmployee.getResigned() < 2) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcEmployee.getResigned();
            }
            //System.out.println("sql jumlah ::::::::::::::::::::::"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
               EmployeeSum empSum = new EmployeeSum();
               empSum.setReportItemId(0);
               empSum.setReportItemName(rs.getString("AGE"));
               empSum.setReportItemDesc("");
               empSum.setReportItemNumber(rs.getFloat("NUM"));
               vSumEdu.add(empSum);
            }

            rs.close();
            return vSumEdu;

        } catch (Exception ex) {
            return vSumEdu;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
}
