/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.OvertimeReportSummary;
import com.dimata.harisma.entity.overtime.PstOvertime;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.harisma.entity.payroll.SalaryLevelDetail;
import com.dimata.harisma.entity.search.SrcOvertimeSummary;
import com.dimata.harisma.form.search.FrmSrcOvertimeSummary;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.util.*;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessOvertimeSummary {
    public static final String SESS_SRC_OVERTIME_SUMMARY = "SESSION_SRC_OVERTIME_SUMMARY";
   
    
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
/**
 * mencari overtime summary
 * create by satrya 2013-08-08
 * @param srcOvertimeSummary
 * @param start
 * @param recordToGet
 * @return 
 */
    public static Vector searchOvertimeSumaryReport(SrcOvertimeSummary srcOvertimeSummary, int start, int recordToGet,String order) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        //System.out.println("nilai src.." + srcOvertime.getSalaryLevel().length());
        if (srcOvertimeSummary == null) {
            return new Vector(1, 1);
        }
        try {
            String sql =  " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
            +",EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
            +",EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
            +",COMP."+PstPayGeneral.fieldNames[PstPayGeneral.FLD_COMPANY_NAME]
            +",DIVX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]
            +",REL."+PstReligion.fieldNames[PstReligion.FLD_RELIGION]
            +",DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
            +",SEC."+PstSection.fieldNames[PstSection.FLD_SECTION]
            +",DEPTCOST."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " AS DEP_COST "
            +",HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]
            +",HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]
            +",HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]
            +",HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]
            +",HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]
            +",HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]
            //update by satrya 2014-02-11
            +",HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]
            + " FROM "+PstOvertime.TBL_OVERTIME + " AS HO "
    + " INNER JOIN "+PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS HOD ON HO."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]+"=HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID]
    + " INNER JOIN "+PstPayGeneral.TBL_PAY_GENERAL + " AS COMP ON COMP."+PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]+"=HO."+PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
    + " INNER JOIN "+PstDivision.TBL_HR_DIVISION + " AS DIVX ON DIVX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"=HO."+PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
    + " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT ON DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"=HO."+PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
    + " LEFT JOIN  "+PstDepartment.TBL_HR_DEPARTMENT + " AS DEPTCOST ON DEPTCOST."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=HO."+PstOvertime.fieldNames[PstOvertime.FLD_COST_DEP_ID]
    + " LEFT JOIN  "+PstSection.TBL_HR_SECTION + " AS SEC ON SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+"=HO."+PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
    + " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"=HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]
    + " INNER JOIN "+PstReligion.TBL_HR_RELIGION + " AS REL ON REl."+PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]+"=EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
    + " WHERE HO."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+"!="+I_DocStatus.DOCUMENT_STATUS_CANCELLED
    //+ " AND HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_SALARY;
    + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+srcOvertimeSummary.getResignStatus();
           
            String whereClause = "";
            if ((srcOvertimeSummary.getStartOvertime() != null) && (srcOvertimeSummary.getEndOvertime() != null)) {
                whereClause = whereClause + "\""+Formater.formatDate(srcOvertimeSummary.getEndOvertime(), "yyyy-MM-dd 23:59:59") +"\""+ " >=  HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] 
                        + " AND  HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " >= \""+Formater.formatDate(srcOvertimeSummary.getStartOvertime(), "yyyy-MM-dd 00:00:00")+"\" AND ";
           }

            if ((srcOvertimeSummary.getEmpNumber() != null) && (srcOvertimeSummary.getEmpNumber().length() > 0)) {
                Vector vectOvNum = logicParser(srcOvertimeSummary.getEmpNumber());
                if (vectOvNum != null && vectOvNum.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectOvNum.size(); i++) {
                        String str = (String) vectOvNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            /*whereClause = whereClause + " ( (OV." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                            + " LIKE '%" + str.trim() + "%')  OR  ( OV." + PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX]
                            + "='" + str.trim()   +"'))"; */
                            whereClause = whereClause + " ( (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%') )";


                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }
            
            if ((srcOvertimeSummary.getFullName() != null) && (srcOvertimeSummary.getFullName().length() > 0)) {
                Vector vectOvNama = logicParser(srcOvertimeSummary.getFullName());
                if (vectOvNama != null && vectOvNama.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectOvNama.size(); i++) {
                        String str = (String) vectOvNama.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            /*whereClause = whereClause + " ( (OV." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                            + " LIKE '%" + str.trim() + "%')  OR  ( OV." + PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX]
                            + "='" + str.trim()   +"'))"; */
                            whereClause = whereClause + " ( (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%') )";


                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if (srcOvertimeSummary.getCompanyId() != 0) {
                whereClause = whereClause + " HO." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]
                        + " = " + srcOvertimeSummary.getCompanyId() + " AND ";
            }

            if (srcOvertimeSummary.getDepartementId() != 0) {
                whereClause = whereClause + " HO." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]
                        + " = " + srcOvertimeSummary.getDepartementId() + " AND ";
            }

            if (srcOvertimeSummary.getDivisionId() != 0) {
                whereClause = whereClause + " HO." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]
                        + " = " + srcOvertimeSummary.getDivisionId() + " AND ";
            }
            if (srcOvertimeSummary.getSectionId() != 0) {
            whereClause = whereClause + " HO." + PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]
            + " = " + srcOvertimeSummary.getSectionId() + " AND ";
            }
            
            if (srcOvertimeSummary.getReligionId() != 0) {
            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
            + " = " + srcOvertimeSummary.getReligionId() + " AND ";
            }
            if (srcOvertimeSummary.getCostCenterDptId() != 0) {
            whereClause = whereClause + " HO." + PstOvertime.fieldNames[PstOvertime.FLD_COST_DEP_ID]
            + " = " + srcOvertimeSummary.getReligionId() + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            }
           if(order!=null && order.length()>0){
                sql = sql + " ORDER BY " + order +",";
            }else{
               sql = sql + " ORDER BY ";
           }
            switch (srcOvertimeSummary.getSortBy()) {
                case FrmSrcOvertimeSummary.ORDER_COMPANY:
                    sql = sql + "  HO." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID];
                    break;
                case FrmSrcOvertimeSummary.ORDER_DATE_FROM:
                    sql = sql + "  HOD." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM];
                    break;
                case FrmSrcOvertimeSummary.ORDER_DEPARTMENT:
                    sql = sql + "  HO." + PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID];
                    break;

                case FrmSrcOvertimeSummary.ORDER_DIVISION:
                   sql = sql + "  HO." + PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID];
                    break;
                case FrmSrcOvertimeSummary.ORDER_EMPLOYEE_NUMBER:
                   sql = sql + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                default:
                    //untuk jaga" jika ada sesuatu yg atau case" tertentu
                    sql = sql + "  HO." + PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID];
            }

            if(start!=0 && recordToGet!=0){
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                OvertimeReportSummary overtimeReportSummary = new OvertimeReportSummary();
                overtimeReportSummary.setEmployee_id(rs.getLong("EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID])); 
                overtimeReportSummary.setEmpNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                //Date tm_req = DBHandler.convertDate(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]),rs.getTime(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]));
                overtimeReportSummary.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                overtimeReportSummary.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                overtimeReportSummary.setCompany(rs.getString("COMP."+PstPayGeneral.fieldNames[PstPayGeneral.FLD_COMPANY_NAME]));
                overtimeReportSummary.setDivision(rs.getString("DIVX."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                overtimeReportSummary.setDepartmentEmployee(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                overtimeReportSummary.setSectionName(rs.getString("SEC."+PstSection.fieldNames[PstSection.FLD_SECTION])); 
                overtimeReportSummary.setCostDept(rs.getString("DEP_COST"));
                overtimeReportSummary.setOvtDuration(rs.getDouble(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]));
                overtimeReportSummary.setTotIdx(rs.getDouble(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]));
                overtimeReportSummary.setAllowance(rs.getInt(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]));
                overtimeReportSummary.setPaidBy(rs.getInt(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]));
               
                //update by satrya 2014-02-11
                overtimeReportSummary.setStatusDoc(rs.getInt("HOD."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]));
                result.add(overtimeReportSummary);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchOvertime : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }
    
   /**
    * create by satrya 2013-08-09
    * mencari gaji per employee
    * @param limitStart
    * @param recordToGet
    * @param konstantaGaji
    * @param order
    * @return Hashtable
    */ 
     public static java.util.Hashtable listGajiPerEmployee(int limitStart,int recordToGet, String konstantaGaji, String order){
        java.util.Hashtable hashtableGajiPerEmp = new java.util.Hashtable(); 
        DBResultSet dbrs = null;
        try {
                String sql = "SELECT PS."+PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                +",PSC."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]
                +",PSC."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]
                +" FROM "+PstPaySlip.TBL_PAY_SLIP + " AS PS "
             + " INNER JOIN "+PstPaySlipComp.TBL_PAY_SLIP_COMP
             + " AS PSC ON PSC."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]
             + " =PS."+PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
             + " WHERE PSC."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + " = \""+konstantaGaji+"\"";
                
                if(order != null && order.length() > 0)
                        sql = sql + " ORDER BY " + order;
                if(limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                else
                        sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()) {
                       hashtableGajiPerEmp.put(rs.getLong("PS."+PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]), rs.getDouble("PSC."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                        
                }
                rs.close();
               return hashtableGajiPerEmp;

        }catch(Exception e) {
                System.out.println("Exception list gaji"+e);
        }finally {
                DBResultSet.close(dbrs);
        }
                return hashtableGajiPerEmp;
   }


}
