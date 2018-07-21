/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

/**
 *
 * @author Wiweka
 */
/* java package */
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import java.util.Date;

/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.overtime.*;
import com.dimata.system.entity.system.*;
import com.dimata.harisma.utility.machine.*;

public class SessOvertime {

    public static final String SESS_SRC_OVERTIME = "SESSION_SRC_OVERTIME";
    private static float defaultOvDurationHaveRestTimeInHour = 9.0f; // limit duration of overtime time , to setup otomatis restime
    private static float defaultOvertimeRestTimeInHour = 0.0f; // default rest time if overtime more then defaultOvDurationHaveRestTimeInHour
    private static int overtimeRoundStart = 0; //system property => OVERTIME_ROUND_START
    private static double overtimeMinimumAsstManager = 0;//system property => OVERTIME_ROUND_START
    private static int overtimeRoundTo = 0;//system property => OVERTIME_ROUND_TO

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

    public static Vector searchOvertime(SrcOvertime srcOvertime, int start, int recordToGet) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        //System.out.println("nilai src.." + srcOvertime.getSalaryLevel().length());
        if (srcOvertime == null) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_OBJECTIVE]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_CUSTOMER_TASK_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_LOGBOOK_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_ACK_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                    + ", OV." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
                    + ", COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + " FROM " + PstOvertime.TBL_OVERTIME + " OV "
                    + " LEFT JOIN " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " OD ON OD." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + " = OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]
                    + " LEFT JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP " + "  ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " COMP ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                    //update by satrya 2013-08-13
                    //+ " LEFT JOIN " + PstCompany.TBL_HR_COMPANY + " COMP ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                    //update by satrya 2013-08-13
                    //+ " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " SEC " + " ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " DIVS " + " ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                    //update by satrya 2013-08-13
                    //+ " LEFT JOIN " + PstDivision.TBL_HR_DIVISION + " DIVS " + " ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " WHERE (1=1) ";
            /*
             + " , " + PstCompany.TBL_HR_COMPANY + " COMP "
             + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
             + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
             + " , " + PstSection.TBL_HR_SECTION + " SEC "
             + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
             + " WHERE "                                        
             + " ( OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
             + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
             + " OR OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID] + "=0"
             + " OR OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID] + " IS NULL )"
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
             + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
             + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]     
             //+ " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
             //+ " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]
             + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             * 
             */
            /*+ " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID]
             + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_ACK_ID]
             + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]*/;

            String whereClause = "";
            if ((srcOvertime.getRequestDate() != null) && (srcOvertime.getRequestDateTo() != null)) {
                whereClause = whereClause + " (OD." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN "
                        + "\"" + Formater.formatDate(srcOvertime.getRequestDate(), "yyyy-MM-dd ") + " 00:00:00\" AND \"" + Formater.formatDate(srcOvertime.getRequestDateTo(), "yyyy-MM-dd") + " 23:59:59\" ) AND ";
            }

            if ((srcOvertime.getOvertimeNum() != null) && (srcOvertime.getOvertimeNum().length() > 0)) {
                Vector vectOvNum = logicParser(srcOvertime.getOvertimeNum());
                if (vectOvNum != null && vectOvNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectOvNum.size(); i++) {
                        String str = (String) vectOvNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            /*whereClause = whereClause + " ( (OV." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                             + " LIKE '%" + str.trim() + "%')  OR  ( OV." + PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX]
                             + "='" + str.trim()   +"'))"; */
                            whereClause = whereClause + " ( (OV." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                                    + " LIKE '" + str.trim() + "') )";


                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if (srcOvertime.getStatusDoc() != -1) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]
                        + " = " + srcOvertime.getStatusDoc() + " AND ";
            }

            if (srcOvertime.getRequestId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]
                        + " = " + srcOvertime.getRequestId() + " AND ";
            }

            if (srcOvertime.getApprovalId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID]
                        + " = " + srcOvertime.getApprovalId() + " AND ";
            }

            if (srcOvertime.getAckId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_ACK_ID]
                        + " = " + srcOvertime.getAckId() + " AND ";
            }

            if (srcOvertime.getCompanyId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                        + " = " + srcOvertime.getCompanyId() + " AND ";
            }

            if (srcOvertime.getDepartmentId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                        + " = " + srcOvertime.getDepartmentId() + " AND ";
            }

            if (srcOvertime.getDivisionId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                        + " = " + srcOvertime.getDivisionId() + " AND ";
            }
            if (srcOvertime.getSectionId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
                        + " = " + srcOvertime.getSectionId() + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID];


            switch (srcOvertime.getOrderBy()) {
                case FrmSrcOvertime.ORDER_REQ_DATE:
                    sql = sql + " ORDER BY OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE];
                    break;
                case FrmSrcOvertime.ORDER_NUMBER:
                    sql = sql + " ORDER BY OV." + PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX];
                    break;
                case FrmSrcOvertime.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;

                case FrmSrcOvertime.ORDER_COMPANY:
                    sql = sql + " ORDER BY COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                            + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                            + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    //+ ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION];
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;
            //System.out.println("\t SQL searchOvertime : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Overtime overtime = new Overtime();
                Company company = new Company();
                Division division = new Division();
                Department department = new Department();
                Position position = new Position();
                Section section = new Section();
                Employee employee = new Employee();

                overtime.setOID(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]));
                //Date tm_req = DBHandler.convertDate(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]),rs.getTime(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]));
                //overtime.setRequestDate(tm_req);
                overtime.setRequestDate(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]));
                overtime.setOvertimeNum(rs.getString(PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]));
                overtime.setCountIdx(rs.getInt(PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX]));
                overtime.setObjective(rs.getString(PstOvertime.fieldNames[PstOvertime.FLD_OBJECTIVE]));
                overtime.setStatusDoc(rs.getInt(PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]));
                overtime.setCustomerTaskId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_CUSTOMER_TASK_ID]));
                overtime.setLogbookId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_LOGBOOK_ID]));
                overtime.setRequestId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]));
                overtime.setApprovalId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID]));
                overtime.setAckId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_ACK_ID]));
                overtime.setCompanyId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]));
                overtime.setDivisionId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]));
                overtime.setDepartmentId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]));
                overtime.setSectionId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]));
                vect.add(overtime);

                company.setCompany(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                vect.add(company);


                division.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                vect.add(division);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);


                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                vect.add(employee);


                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchOvertime : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static int countOvertime(SrcOvertime srcOvertime) {
        DBResultSet dbrs = null;
        int result = 0;
        //System.out.println("nilai src.." + srcOvertime.getSalaryLevel().length());
        if (srcOvertime == null) {
            return 0;
        }
        try {
            String sql = "SELECT COUNT(THEID) FROM ( SELECT OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID] + " AS THEID "
                    + " FROM " + PstOvertime.TBL_OVERTIME + " OV "
                    + " LEFT JOIN " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " OD ON OD." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + " = OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]
                    + " LEFT JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP " + "  ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " COMP ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                    //+ " LEFT JOIN " + PstCompany.TBL_HR_COMPANY + " COMP ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                    // UPDATE BY SATRYA 2013-08-13
                    + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                    //UPDATE BY SATRYA 2013-08-13
                    //+ " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT ON" + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " SEC " + " ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " DIVS " + " ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                    //UPDATE BY SATRYA 2013-08-13
                    //+ " LEFT JOIN " + PstDivision.TBL_HR_DIVISION + " DIVS " + " ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                    + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " WHERE (1=1) ";
            /*
             + " , " + PstCompany.TBL_HR_COMPANY + " COMP "
             + " , " + PstDivision.TBL_HR_DIVISION + " DIVS "
             + " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEPT "
             + " , " + PstSection.TBL_HR_SECTION + " SEC "
             + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
             + " WHERE "                                        
             + " ( OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
             + " = COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
             + " OR OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID] + "=0"
             + " OR OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID] + " IS NULL )"
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
             + " = DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
             + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]     
             //+ " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
             //+ " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]
             + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             * 
             */
            /*+ " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID]
             + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_ACK_ID]
             + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
             + " AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]*/;

            String whereClause = "";
            if ((srcOvertime.getRequestDate() != null) && (srcOvertime.getRequestDateTo() != null)) {
                whereClause = whereClause + " (OD." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN "
                        + "\"" + Formater.formatDate(srcOvertime.getRequestDate(), "yyyy-MM-dd ") + " 00:00:00\" AND \"" + Formater.formatDate(srcOvertime.getRequestDateTo(), "yyyy-MM-dd") + " 23:59:59\" ) AND ";
            }

            if ((srcOvertime.getOvertimeNum() != null) && (srcOvertime.getOvertimeNum().length() > 0)) {
                Vector vectOvNum = logicParser(srcOvertime.getOvertimeNum());
                if (vectOvNum != null && vectOvNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectOvNum.size(); i++) {
                        String str = (String) vectOvNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            /*whereClause = whereClause + " ( (OV." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                             + " LIKE '%" + str.trim() + "%')  OR  ( OV." + PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX]
                             + "='" + str.trim()   +"'))"; */
                            whereClause = whereClause + " ( (OV." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                                    + " LIKE '" + str.trim() + "') )";


                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if (srcOvertime.getStatusDoc() != -1) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]
                        + " = " + srcOvertime.getStatusDoc() + " AND ";
            }

            if (srcOvertime.getRequestId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]
                        + " = " + srcOvertime.getRequestId() + " AND ";
            }

            if (srcOvertime.getApprovalId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID]
                        + " = " + srcOvertime.getApprovalId() + " AND ";
            }

            if (srcOvertime.getAckId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_ACK_ID]
                        + " = " + srcOvertime.getAckId() + " AND ";
            }

            if (srcOvertime.getCompanyId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                        + " = " + srcOvertime.getCompanyId() + " AND ";
            }

            if (srcOvertime.getDepartmentId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                        + " = " + srcOvertime.getDepartmentId() + " AND ";
            }

            if (srcOvertime.getDivisionId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                        + " = " + srcOvertime.getDivisionId() + " AND ";
            }
            if (srcOvertime.getSectionId() != 0) {
                whereClause = whereClause + " OV." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
                        + " = " + srcOvertime.getSectionId() + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID];


            switch (srcOvertime.getOrderBy()) {
                case FrmSrcOvertime.ORDER_REQ_DATE:
                    sql = sql + " ORDER BY OV." + PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE];
                    break;
                case FrmSrcOvertime.ORDER_NUMBER:
                    sql = sql + " ORDER BY OV." + PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX];
                    break;
                case FrmSrcOvertime.ORDER_DEPARTMENT:
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    break;

                case FrmSrcOvertime.ORDER_COMPANY:
                    sql = sql + " ORDER BY COMP." + PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                            + ", DIVS." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                            + ", DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                    //+ ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION];
                    break;
                default:
                    sql = sql + "";
            }


            sql = sql + " ) AS THERESULT ";
            //System.out.println("\t SQL searchOvertime : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getInt(1);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchOvertime : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;

    }
    static Hashtable<String, Ovt_Type> levelToOvTypeHoliday = new Hashtable();
    static Hashtable<String, Ovt_Type> levelToOvTypeWork = new Hashtable();
    static Hashtable<String, Ovt_Type> levelToOvTypeEndYear = new Hashtable();
    static Hashtable<String, Ovt_Type> dPLevelToOvTypeHoliday = new Hashtable();
    static Hashtable<String, Ovt_Type> dPLevelToOvTypeWork = new Hashtable();
    static Hashtable<String, Ovt_Type> dPLevelToOvTypeEndYear = new Hashtable();
    static Hashtable<String, Ovt_Type> levelToOvTypeHolidayMasterLevel = new Hashtable();
    static Hashtable<String, Ovt_Type> levelToOvTypeWorkMasterLevel = new Hashtable();
    static Hashtable<String, Ovt_Type> levelToOvTypeEndYearMasterLevel = new Hashtable();
    static Hashtable<String, Ovt_Type> dPLevelToOvTypeHolidayMasterLevel = new Hashtable();
    static Hashtable<String, Ovt_Type> dPLevelToOvTypeWorkMasterLevel = new Hashtable();
    static Hashtable<String, Ovt_Type> dPLevelToOvTypeEndYearMasterLevel = new Hashtable();

    static Hashtable<String, Level> hMasterLevelMap = new Hashtable<String, Level>();
    static Hashtable<String, ScheduleCategory> hScheduleCategory = new Hashtable<String, ScheduleCategory>(); 
    static boolean levelMalLoaded = false;
    static Date sysPropEndYearStartDate = null;
    static Date syspropEndYearEndDate =null;

    /**
     * FUngsi ini untuk menghitung index overtime karyawan
     *
     * @param overtime
     * @param reloadMap
     * @return
     */
   public static String calcOvTmIndex(OvertimeDetail overtime, boolean reloadMap, float minOvertimeHour) {
      
        String msg = "";
        if (overtime == null) {
            return "overtime is null";
        }
        //update by satrya 2013-02-03
        if (overtime.getFlagStatus() == 2) {
            return "ini sudah di input manual di attd daily";
        }
        if (overtime.getRoundDuration() <= 0.0) {
            return "duration = 0";
        }
        if (overtime.getRoundDuration() < minOvertimeHour) {
            try {
                return "duration < " + Formater.formatNumber(minOvertimeHour, "###,###.##");
            } catch (Exception exc) {
                return "duration < " + minOvertimeHour;
            }
        }
        try {
           if (reloadMap || (!levelMalLoaded)) {
            levelToOvTypeHoliday = PstOvt_Type.getLevelOvIdxMap(PstOvt_Type.HOLIDAY);
            levelToOvTypeWork = PstOvt_Type.getLevelOvIdxMap(PstOvt_Type.WORKING_DAY);
            levelToOvTypeEndYear = PstOvt_Type.getLevelOvIdxMap(PstOvt_Type.END_OF_YEAR);
            levelToOvTypeHolidayMasterLevel = PstOvt_Type.getLevelOvMapLevelOID(PstOvt_Type.HOLIDAY);
            levelToOvTypeWorkMasterLevel = PstOvt_Type.getLevelOvMapLevelOID(PstOvt_Type.WORKING_DAY);
            levelToOvTypeEndYearMasterLevel = PstOvt_Type.getLevelOvMapLevelOID(PstOvt_Type.END_OF_YEAR);              
            hMasterLevelMap = PstLevel.hashlistLevelStringOid(); 
            sysPropEndYearStartDate = PstSystemProperty.getPropertyDatebyName("OVERTIME_END_YEAR_START_DATE","yyyy-MM-dd");
            syspropEndYearEndDate = PstSystemProperty.getPropertyDatebyName("OVERTIME_END_YEAR_START_DATE","yyyy-MM-dd");
           }
            ScheduleSymbol sch = PstEmpSchedule.getDailySchedule(overtime.getDateFrom(), overtime.getEmployeeId());
            int empLevel = PstEmployee.getEmployeePosLevelPayrollIdx(overtime.getEmployeeId());// getEmployeePosLevelIdx(overtime.getEmployeeId());
            Level masterLevel = hMasterLevelMap.get(""+overtime.getEmployeeMasterLevel());
            int dayType = PstOvt_Type.WORKING_DAY;
            if (sch != null && sch.getOID() != 0) {
                ScheduleCategory schCategory = PstScheduleCategory.fetchExc(sch.getScheduleCategoryId());
                if ( (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_LONG_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_UNPAID_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_PARTIAL_LEAVE_DAY)) {
                    dayType = PstOvt_Type.HOLIDAY;
                }else if ((schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF)
                        ) {
                    dayType = PstOvt_Type.SCHEDULE_OFF;
                }
            } else {
                dayType = PstOvt_Type.HOLIDAY; // jika tidak ada shedule maka dianggap hari libur
            }
            Ovt_Type ovType = null;
            Ovt_Type ovTypeMasterLevel = null;
            
            if (dayType == PstOvt_Type.HOLIDAY) {
                ovType = levelToOvTypeHoliday.get("" + empLevel); // get OV index group by level category
                ovTypeMasterLevel = levelToOvTypeHolidayMasterLevel.get("" + masterLevel.getOID()); // get OV index group by master level
//                if(overtime.getRoundDuration()> defaultOvDurationHaveRestTimeInHour){
//                    overtime.setRestTimeinHr(defaultOvertimeRestTimeInHour);
//                }
            } else {
                ovType = levelToOvTypeWork.get("" + empLevel);
                ovTypeMasterLevel = levelToOvTypeWork.get("" + empLevel);
            }
            
            if(ovType ==null && ovTypeMasterLevel!=null){
                ovType = ovTypeMasterLevel;
            }else{
                return "No overtime index found";
            }
            
            
            double totalIdx = 0.0;
            Vector vOvIdx = ovType.getOvIndex();
            if (vOvIdx != null && vOvIdx.size() > 0) {
                double durationCalculate = 0.0;

                for (int idx = 0; idx < vOvIdx.size(); idx++) {
                    Ovt_Idx oVIdx = (Ovt_Idx) vOvIdx.get(idx);
                    if (overtime.getRoundDuration() < oVIdx.getHour_from()) {
                        break;
                    }
                    
                   
                    //update by devin 2014-02-25
                    //update by satrya 2014-05-15  empLevel==PstPosition.LEVEL_ASST_MANAGER
//                   update by ramayu 2014-09-26 if(overtime.getRoundDuration()> oVIdx.getHour_from() && dayType==PstOvt_Type.WORKING_DAY && empLevel==PstPosition.LEVEL_ASST_MANAGER ){// && overtime.getRoundDuration()<=oVIdx.getHour_to()){                        
//                        if(overtime.getRoundDuration()<=oVIdx.getHour_to()){
//                            //UPDATE BY DEVIN 2014-02-12
//                            //totalIdx =  totalIdx+ (overtime.getRoundDuration()- durationCalculate)  * oVIdx.getOvt_idx();
//                         totalIdx =  totalIdx+ (overtime.getRoundDuration()- oVIdx.getOvt_idx());
//                         durationCalculate = durationCalculate + (overtime.getRoundDuration()-oVIdx.getHour_from());
//                        
//                        }else {
//                         totalIdx =  totalIdx+ (oVIdx.getHour_to()- durationCalculate)  * oVIdx.getOvt_idx();
//                         durationCalculate = durationCalculate + (oVIdx.getHour_to()-oVIdx.getHour_from());                        
//                        }
                    if (dayType == PstOvt_Type.WORKING_DAY && empLevel == PstPosition.LEVEL_ASST_MANAGER) {// && overtime.getRoundDuration()<=oVIdx.getHour_to()){
                        if (SessOvertime.getOvertimeMinimumAsstManager() != 0 && overtime.getRoundDuration() > SessOvertime.getOvertimeMinimumAsstManager()) {
                            double realCalculate = overtime.getRoundDuration() - SessOvertime.getOvertimeMinimumAsstManager();
                            if (realCalculate>=oVIdx.getHour_to()) {
                                totalIdx = totalIdx + (oVIdx.getHour_to() * oVIdx.getOvt_idx());
                                 durationCalculate = durationCalculate + (realCalculate-oVIdx.getHour_to());
                            } else if(durationCalculate>0.00000000000){
                                totalIdx = totalIdx + (durationCalculate) * oVIdx.getOvt_idx();
                            }
                        }else{
                            break;
                        }



// masih prosess update by satrya 2014-09-04                      if(overtime.getRoundDuration()<=oVIdx.getHour_to()){
//                            //UPDATE BY DEVIN 2014-02-12
//                            //totalIdx =  totalIdx+ (overtime.getRoundDuration()- durationCalculate)  * oVIdx.getOvt_idx();
//                         totalIdx =  totalIdx+ (overtime.getRoundDuration()- PstOvt_Type.VARIABLE_NILAI_PENGURANG_OT_ASST_MANAGER)  * oVIdx.getOvt_idx();
//                         durationCalculate = durationCalculate + (overtime.getRoundDuration()-oVIdx.getHour_from());
//                         x
//                        
//                        }else {
//                         totalIdx =  totalIdx+ (oVIdx.getHour_to()- durationCalculate)  * oVIdx.getOvt_idx();
//                         durationCalculate = durationCalculate + (oVIdx.getHour_to()-oVIdx.getHour_from());                        
//                        }
                        continue;
                    } else {
                        if (overtime.getRoundDuration() > oVIdx.getHour_from() && dayType != PstOvt_Type.WORKING_DAY) {// && overtime.getRoundDuration()<=oVIdx.getHour_to()){                        
                            if (overtime.getRoundDuration() <= oVIdx.getHour_to()) {
                                //UPDATE BY DEVIN 2014-02-12
                                totalIdx = totalIdx + (overtime.getRoundDuration() - durationCalculate) * oVIdx.getOvt_idx();
                                //totalIdx =  totalIdx+ (overtime.getRoundDuration()- oVIdx.getHour_from())  * oVIdx.getOvt_idx();
                                durationCalculate = durationCalculate + (overtime.getRoundDuration() - oVIdx.getHour_from());
                            } else {
                                totalIdx = totalIdx + (oVIdx.getHour_to() - durationCalculate) * oVIdx.getOvt_idx();
                                durationCalculate = durationCalculate + (oVIdx.getHour_to() - oVIdx.getHour_from());
                            }
                            continue;
                        }//update by devin 2014-02-12
                        else if (overtime.getRoundDuration() > oVIdx.getHour_from() && dayType == PstOvt_Type.WORKING_DAY) {// && overtime.getRoundDuration()<=oVIdx.getHour_to()){                        
                            if (overtime.getRoundDuration() <= oVIdx.getHour_to()) {
                                //UPDATE BY DEVIN 2014-02-12
                                //totalIdx =  totalIdx+ (overtime.getRoundDuration()- durationCalculate)  * oVIdx.getOvt_idx();
                                totalIdx = totalIdx + (overtime.getRoundDuration() - oVIdx.getHour_from()) * oVIdx.getOvt_idx();
                                durationCalculate = durationCalculate + (overtime.getRoundDuration() - oVIdx.getHour_from());
                            } else {
                                totalIdx = totalIdx + (oVIdx.getHour_to() - durationCalculate) * oVIdx.getOvt_idx();
                                durationCalculate = durationCalculate + (oVIdx.getHour_to() - oVIdx.getHour_from());
                            }
                            continue;
                        }

                    }


                    /**
                     * //hiden by devin 2014-02-12
                     * if(overtime.getRoundDuration()> oVIdx.getHour_from()){//
                     * && overtime.getRoundDuration()<=oVIdx.getHour_to()){
                     * if(overtime.getRoundDuration()<=oVIdx.getHour_to()){
                     * totalIdx = totalIdx+ (overtime.getRoundDuration()-
                     * durationCalculate) * oVIdx.getOvt_idx();
                     * durationCalculate = durationCalculate +
                     * (overtime.getRoundDuration()-oVIdx.getHour_from()); }else
                     * { totalIdx = totalIdx+ (oVIdx.getHour_to()-
                     * durationCalculate) * oVIdx.getOvt_idx();
                     * durationCalculate = durationCalculate +
                     * (oVIdx.getHour_to()-oVIdx.getHour_from()); } continue; }
                     */
                    /*if(overtime.getRoundDuration()>=oVIdx.getHour_to()){
                     totalIdx = totalIdx + ( oVIdx.getHour_to() - oVIdx.getHour_from())  * oVIdx.getOvt_idx();
                     durationCalculate = durationCalculate + oVIdx.getHour_to();
                     }*/
                }
                overtime.setTot_Idx(totalIdx);
            }

        } catch (Exception exc) {
            System.out.println(exc);
        }
        return msg;
    }

    /**
     * FUngsi ini untuk menghitung index overtime karyawan
     *
     * @param overtime
     * @param reloadMap
     * @return
     */
    public static String calcOvTmDayOff(OvertimeDetail overtime, boolean reloadMap, float minOvertimeHour) {
        String msg = "";
        if (overtime == null) {
            return "overtime is null";
        }
        if (overtime.getRoundDuration() <= 0.0) {
            return "duration = 0";
        }
        if (overtime.getRoundDuration() < minOvertimeHour) {
            try {
                return "duration < " + Formater.formatNumber(minOvertimeHour, "###,###.##");
            } catch (Exception exc) {
                return "duration < " + minOvertimeHour;
            }
        }
        if (reloadMap || (!levelMalLoaded)) {
            dPLevelToOvTypeHoliday = PstOvt_Type.getLevelOvIdxMap(PstOvt_Type.DP_HOLIDAY);
            dPLevelToOvTypeWork = PstOvt_Type.getLevelOvIdxMap(PstOvt_Type.DP_WORKING_DAY);
            dPLevelToOvTypeHolidayMasterLevel = PstOvt_Type.getLevelOvMapLevelOID(PstOvt_Type.DP_HOLIDAY);
            dPLevelToOvTypeWorkMasterLevel = PstOvt_Type.getLevelOvMapLevelOID(PstOvt_Type.DP_WORKING_DAY);        }
        try {
            ScheduleSymbol sch = PstEmpSchedule.getDailySchedule(overtime.getDateFrom(), overtime.getEmployeeId());
            int empLevel = PstEmployee.getEmployeePosLevelPayrollIdx(overtime.getEmployeeId());
            int dayType = PstOvt_Type.DP_WORKING_DAY;
            if (sch != null && sch.getOID() != 0) {
                ScheduleCategory schCategory = PstScheduleCategory.fetchExc(sch.getScheduleCategoryId());
                if ((schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_LONG_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_UNPAID_LEAVE)
                        || (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_PARTIAL_LEAVE_DAY)) {
                    dayType = PstOvt_Type.DP_HOLIDAY;
                }
            } else {
                dayType = PstOvt_Type.DP_HOLIDAY; // jika tidak ada shedule maka dianggap hari libur
            }
            Ovt_Type ovType = null;
            if (dayType == PstOvt_Type.DP_HOLIDAY) {
                ovType = dPLevelToOvTypeHoliday.get("" + empLevel);
//                if(overtime.getRoundDuration()> defaultOvDurationHaveRestTimeInHour){
//                    overtime.setRestTimeinHr(defaultOvertimeRestTimeInHour);
//                }
            } else {
                ovType = dPLevelToOvTypeWork.get("" + empLevel);
            }

            double totalIdx = 0.0;
            Vector vOvIdx = ovType.getOvIndex();
            if (vOvIdx != null && vOvIdx.size() > 0) {
                double durationCalculate = 0.0;

                for (int idx = 0; idx < vOvIdx.size(); idx++) {
                    Ovt_Idx oVIdx = (Ovt_Idx) vOvIdx.get(idx);
                    if (overtime.getRoundDuration() < oVIdx.getHour_from()) {
                        break;
                    }

                    if (overtime.getRoundDuration() > oVIdx.getHour_from()) { // && overtime.getRoundDuration()<=oVIdx.getHour_to()){                        
                        if (overtime.getRoundDuration() <= oVIdx.getHour_to()) {
                            totalIdx = totalIdx + (overtime.getRoundDuration() - durationCalculate) * oVIdx.getOvt_idx();
                            durationCalculate = durationCalculate + (overtime.getRoundDuration() - oVIdx.getHour_from());
                        } else {
                            totalIdx = totalIdx + (oVIdx.getHour_to() - durationCalculate) * oVIdx.getOvt_idx();
                            durationCalculate = durationCalculate + (oVIdx.getHour_to() - oVIdx.getHour_from());
                        }
                        continue;
                    }

                    /*if(overtime.getRoundDuration()>=oVIdx.getHour_to()){
                     totalIdx = totalIdx + ( oVIdx.getHour_to() - oVIdx.getHour_from())  * oVIdx.getOvt_idx();
                     durationCalculate = durationCalculate + oVIdx.getHour_to();
                     }*/
                }
                overtime.setTot_Idx(totalIdx);
            }

        } catch (Exception exc) {
            System.out.println(exc);
        }
        return msg;
    }

    /**
     * @return the defaultOvDurationHaveRestTimeInHour
     */
    public static float getDefaultOvDurationHaveRestTimeInHour() {
        return defaultOvDurationHaveRestTimeInHour;
    }

    /**
     * @param aDefaultOvDurationHaveRestTimeInHour the
     * defaultOvDurationHaveRestTimeInHour to set
     */
    public static void setDefaultOvDurationHaveRestTimeInHour(float aDefaultOvDurationHaveRestTimeInHour) {
        defaultOvDurationHaveRestTimeInHour = aDefaultOvDurationHaveRestTimeInHour;
    }

    /**
     * @return the defaultOvertimeRestTimeInHour
     */
    public static float getDefaultOvertimeRestTimeInHour() {
        return defaultOvertimeRestTimeInHour;
    }

    /**
     * @param aDefaultOvertimeRestTimeInHour the defaultOvertimeRestTimeInHour
     * to set
     */
    public static void setDefaultOvertimeRestTimeInHour(float aDefaultOvertimeRestTimeInHour) {
        defaultOvertimeRestTimeInHour = aDefaultOvertimeRestTimeInHour;
    }

    /**
     * @return the overtimeRoundStart
     */
    public static int getOvertimeRoundStart() {
        return overtimeRoundStart;
    }

    /**
     * @param aOvertimeRoundStart the overtimeRoundStart to set
     */
    public static void setOvertimeRoundStart(int aOvertimeRoundStart) {
        overtimeRoundStart = aOvertimeRoundStart;
    }

    /**
     * @return the overtimeRoundTo
     */
    public static int getOvertimeRoundTo() {
        return overtimeRoundTo;
    }

    /**
     * @param aOvertimeRoundTo the overtimeRoundTo to set
     */
    public static void setOvertimeRoundTo(int aOvertimeRoundTo) {
        overtimeRoundTo = aOvertimeRoundTo;
    }

    /**
     * @return the overtimeMinimumAsstManager
     */
    public static double getOvertimeMinimumAsstManager() {
        return overtimeMinimumAsstManager;
    }

    /**
     * @param aOvertimeMinimumAsstManager the overtimeMinimumAsstManager to set
     */
    public static void setOvertimeMinimumAsstManager(double aOvertimeMinimumAsstManager) {
        overtimeMinimumAsstManager = aOvertimeMinimumAsstManager;
    }
}
