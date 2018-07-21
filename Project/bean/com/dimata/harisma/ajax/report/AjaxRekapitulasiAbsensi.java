/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.report;

import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.RekapitulasiAbsensiGrandTotal;
import com.dimata.harisma.entity.attendance.RekapitulasiPresenceDanSchedule;
import com.dimata.harisma.entity.employee.EmployeeSrcRekapitulasiAbs;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.payday.HashTblPayDay;
import com.dimata.harisma.entity.masterdata.payday.PstPayDay;
import com.dimata.harisma.entity.masterdata.sessdepartment.SessDepartment;
import com.dimata.harisma.entity.masterdata.sessdivision.SessDivision;
import com.dimata.harisma.entity.masterdata.sessemployee.EmployeeMinimalis;
import com.dimata.harisma.entity.masterdata.sessemployee.SessEmployee;
import com.dimata.harisma.entity.masterdata.sesssection.SessSection;
import com.dimata.harisma.entity.payroll.PstPayEmpLevel;
import com.dimata.harisma.session.attendance.rekapitulasiabsensi.RekapitulasiAbsensi;
import com.dimata.harisma.session.payroll.I_PayrollCalculator;
import com.dimata.qdep.form.*;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import org.json.*;

/**
 *
 * @author Gunadi
 */
public class AjaxRekapitulasiAbsensi extends HttpServlet {
    //DATATABLES

    private String searchTerm;
    private String colName;
    private int colOrder;
    private String dir;
    private int start;
    private int amount;
    //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    //LONG
    private long oid = 0;
    private long oidReturn = 0;
    private long userId = 0;
    private long empId = 0;
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //LONG
        this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
        this.oidReturn = 0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        this.empId = FRMQueryString.requestLong(request, "empId");

        //STRING
        this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
        this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
        this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
        this.htmlReturn = "";
        this.empName = FRMQueryString.requestString(request, "empName");

        //INT
        this.iCommand = FRMQueryString.requestCommand(request);
        this.iErrCode = 0;



        //OBJECT
        this.jSONObject = new JSONObject();

        switch (this.iCommand) {
            case Command.SAVE:
                commandSave(request);
                break;

            case Command.LIST:
                commandList(request, response);
                break;

            case Command.DELETEALL:
                commandDeleteAll(request);
                break;

            case Command.DELETE:
                commandDelete(request);
                break;

            default:
                commandNone(request);
        }


        try {

            this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
            this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
        } catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }

        response.getWriter().print(this.jSONObject);

    }

    public void commandNone(HttpServletRequest request) {
        if (this.dataFor.equals("get_division")) {
            this.htmlReturn = divisionSelect(request);
        } else if (this.dataFor.equals("get_department")) {
            this.htmlReturn = departmentSelect(request);
        } else if (this.dataFor.equals("get_section")) {
            this.htmlReturn = sectionSelect(request);
        } else if (this.dataFor.equals("listData")) {
            this.htmlReturn = rekapTable(request);
        }
    }

    public void commandSave(HttpServletRequest request) {
    }

    public void commandDeleteAll(HttpServletRequest request) {
    }

    public void commandDelete(HttpServletRequest request) {
    }

    public void commandList(HttpServletRequest request, HttpServletResponse response) {
        if (this.dataFor.equals("listEmpGeneralDoc")) {
            String[] cols = {};

            jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
        }
    }

    public JSONObject listDataTables(HttpServletRequest request, HttpServletResponse response, String[] cols, String dataFor, JSONObject result) {
        this.searchTerm = FRMQueryString.requestString(request, "sSearch");
        int amount = 10;
        int start = 0;
        int col = 0;
        String dir = "asc";
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");

        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
            if (amount < 10) {
                amount = 10;
            }
        }
        if (sCol != null) {
            col = Integer.parseInt(sCol);
            if (col < 0) {
                col = 0;
            }
        }
        if (sdir != null) {
            if (!sdir.equals("asc")) {
                dir = "desc";
            }
        }



        String whereClause = "";

        if (dataFor.equals("listEmpGeneralDoc")) {
            whereClause += " ";
        }

        String colName = cols[col];
        int total = -1;

        if (dataFor.equals("listEmpGeneralDoc")) {
            total = 0;
        }


        this.amount = amount;

        this.colName = colName;
        this.dir = dir;
        this.start = start;
        this.colOrder = col;

        try {
            result = getData(total, request, dataFor);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return result;
    }

    public JSONObject getData(int total, HttpServletRequest request, String datafor) {

        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();

        String whereClause = "";
        String order = "";
        String document = "";
        boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");

        if (this.searchTerm == null) {
            whereClause += "";
        } else {
            if (dataFor.equals("listEmpGeneralDoc")) {
                whereClause += " ";
            }
        }

        if (this.colOrder >= 0) {
            order += "" + colName + " " + dir + "";
        }

        Vector listData = new Vector(1, 1);
        if (datafor.equals("listEmpGeneralDoc")) {
            // listData = PstEmpDocGeneral.list(this.start, this.amount,whereClause,order);
        }



        for (int i = 0; i <= listData.size() - 1; i++) {
            JSONArray ja = new JSONArray();
            if (datafor.equals("listEmpGeneralDoc")) {
            }
        }

        totalAfterFilter = total;

        try {
            result.put("iTotalRecords", total);
            result.put("iTotalDisplayRecords", totalAfterFilter);
            result.put("aaData", array);
        } catch (Exception e) {
        }

        return result;
    }

    public String divisionSelect(HttpServletRequest request) {
        String inComp = FRMQueryString.requestString(request, "company");
        String data = "";
        String whereClause = PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS] + "=" + PstDivision.VALID_ACTIVE;
        whereClause += " AND " + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + " IN (" + inComp + ")";
        Vector divisionList = PstDivision.list(0, 0, whereClause, "");
        if (divisionList != null && divisionList.size() > 0) {
            for (int i = 0; i < divisionList.size(); i++) {
                Division divisi = (Division) divisionList.get(i);
                data += "<option value=\"" + divisi.getOID() + "\">" + divisi.getDivision() + "</option>";
            }
        }
        return data;
    }

    public String departmentSelect(HttpServletRequest request) {
        String[] arrDivision = FRMQueryString.requestStringValues(request, "division[]");
        String inDivision = "";
        if (arrDivision != null && arrDivision.length > 0) {
            for (int i = 0; i < arrDivision.length; i++) {
                inDivision = inDivision + "," + arrDivision[i];
            }
            inDivision = inDivision.substring(1);
        }
        String data = "";
        String whereClause = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_VALID_STATUS] + "=" + PstDepartment.VALID_ACTIVE;
        whereClause += " AND " + PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + " IN (" + inDivision + ")";
        String order = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID];
        Vector departmentList = PstDepartment.list(0, 0, whereClause, order);
        long divisionId = 0;
        if (departmentList != null && departmentList.size() > 0) {
            for (int i = 0; i < departmentList.size(); i++) {
                Department department = (Department) departmentList.get(i);
                if (divisionId != department.getDivisionId()) {
                    if (divisionId != 0) {
                        data += "</optgroup>";
                    }
                    Division div = new Division();
                    try {
                        div = PstDivision.fetchExc(department.getDivisionId());
                    } catch (Exception exc) {
                    }
                    data += "<optgroup label='" + div.getDivision() + "'>";
                    divisionId = department.getDivisionId();
                }

                data += "<option value=\"" + department.getOID() + "\">" + department.getDepartment() + "</option>";
            }
            data += "</optgroup>";
        }
        return data;
    }

    public String sectionSelect(HttpServletRequest request) {
        String[] arrDepartment = FRMQueryString.requestStringValues(request, "department[]");
        String inDepartment = "";
        if (arrDepartment != null && arrDepartment.length > 0) {
            for (int i = 0; i < arrDepartment.length; i++) {
                inDepartment = inDepartment + "," + arrDepartment[i];
            }
            inDepartment = inDepartment.substring(1);
        }
        String data = "";
        String whereClause = PstSection.fieldNames[PstSection.FLD_VALID_STATUS] + "=" + PstSection.VALID_ACTIVE;
        whereClause += " AND " + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " IN (" + inDepartment + ")";
        String order = PstSection.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
        Vector sectionList = PstSection.list(0, 0, whereClause, order);
        long departmentId = 0;
        if (sectionList != null && sectionList.size() > 0) {
            for (int i = 0; i < sectionList.size(); i++) {
                Section section = (Section) sectionList.get(i);
                if (departmentId != section.getDepartmentId()) {
                    if (departmentId != 0) {
                        data += "</optgroup>";
                    }
                    Department dept = new Department();
                    try {
                        dept = PstDepartment.fetchExc(section.getDepartmentId());
                    } catch (Exception exc) {
                    }
                    data += "<optgroup label='" + dept.getDepartment() + "'>";
                    departmentId = section.getDepartmentId();
                }

                data += "<option value=\"" + section.getOID() + "\">" + section.getSection() + "</option>";
            }
            data += "</optgroup>";
        }
        return data;
    }

    private String rekapTable(HttpServletRequest request) {
        String returnData = "";

        String source = FRMQueryString.requestString(request, "source");
        String[] stsEmpCategory = null;
        int sizeCategory = PstEmpCategory.listAll() != null ? PstEmpCategory.listAll().size() : 0;
        stsEmpCategory = new String[sizeCategory];
        String stsEmpCategorySel = "";
        int maxEmpCat = 0;
        for (int j = 0; j < sizeCategory; j++) {
            String name = "EMP_CAT_" + j;
            String val = FRMQueryString.requestString(request, name);
            stsEmpCategory[j] = val;
            if (val != null && val.length() > 0) {
                //stsEmpCategorySel.add(""+val); 
                stsEmpCategorySel = stsEmpCategorySel + val + ",";
            }
            maxEmpCat++;
        }


        //    OID_DAILYWORKER
        long Dw = 0;
        try {
            String sDw = PstSystemProperty.getValueByName("OID_DAILYWORKER");
            Dw = Integer.parseInt(sDw);
        } catch (Exception ex) {
            System.out.println("VALUE_DAILYWORKER NOT Be SET" + ex);
        }
        int iCommand = FRMQueryString.requestCommand(request);
        int iErrCode = FRMMessage.NONE;
        int start = FRMQueryString.requestInt(request, "start");

        RekapitulasiAbsensi rekapitulasiAbsensi = new RekapitulasiAbsensi();
        rekapitulasiAbsensi.setCompanyId(FRMQueryString.requestLong(request, "company_id"));
        //rekapitulasiAbsensi.setDeptId(FRMQueryString.requestLong(request, "department"));
        rekapitulasiAbsensi.addArrDivision(FRMHandler.getParamsStringValuesStatic(request, "division_id"));
        rekapitulasiAbsensi.addArrDepartment(FRMHandler.getParamsStringValuesStatic(request, "department"));
        rekapitulasiAbsensi.addArrSection(FRMHandler.getParamsStringValuesStatic(request, "section"));

        //rekapitulasiAbsensi.setSectionId(FRMQueryString.requestLong(request, "section"));
        //rekapitulasiAbsensi.setDivisionId(FRMQueryString.requestLong(request, "division_id"));
        rekapitulasiAbsensi.setDtFrom(java.sql.Date.valueOf(FRMQueryString.requestString(request, "check_date_start")));
        rekapitulasiAbsensi.setDtTo(java.sql.Date.valueOf(FRMQueryString.requestString(request, "check_date_finish")));
        rekapitulasiAbsensi.setEmpCategory(stsEmpCategorySel);
        rekapitulasiAbsensi.setFullName(FRMQueryString.requestString(request, "full_name"));
        rekapitulasiAbsensi.setPayrollNumber(FRMQueryString.requestString(request, "emp_number"));
        rekapitulasiAbsensi.setResignSts(FRMQueryString.requestInt(request, "statusResign"));
        rekapitulasiAbsensi.setSourceTYpe(FRMQueryString.requestInt(request, "source_type"));
        rekapitulasiAbsensi.setViewschedule(FRMQueryString.requestInt(request, "viewschedule"));
        int viewschedule = FRMQueryString.requestInt(request, "viewschedule");
        int OnlyDw = FRMQueryString.requestInt(request, "OnlyDw");
        if (OnlyDw != 0 && OnlyDw == 1) {
            rekapitulasiAbsensi.setEmpCategory(Dw + ",");
        }
        Vector listCompany = new Vector();
        Hashtable hashDivision = new Hashtable();
        Hashtable hashDepartment = new Hashtable();
        Hashtable hashSection = new Hashtable();
        Hashtable hashEmployee = new Hashtable();
        Hashtable hashEmployeeSection = new Hashtable();
        Hashtable listAttdAbsensi = null;
        Hashtable listPayDayfromSalLevel = null;

        I_Atendance attdConfig = null;
        try {
            attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
        }
        int iPropInsentifLevel = 0;
        long lHolidays = 0;
        try {
            iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
            lHolidays = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
        } catch (Exception ex) {
            System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        }

        I_PayrollCalculator payrollCalculatorConfig = null;
        try {
            payrollCalculatorConfig = (I_PayrollCalculator) (Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception PAYROLL_CALC_CLASS_NAME " + e.getMessage());
        }
        int propReasonNo = -1;
        try {
            propReasonNo = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_REASON_DUTTY_NO"));

        } catch (Exception ex) {
            System.out.println("Execption REASON_DUTTY_NO: " + ex);
        }
        int propCheckLeaveExist = 0;
        try {
            propCheckLeaveExist = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_WHEN_LEAVE_EXIST"));

        } catch (Exception ex) {
            System.out.println("Execption ATTANDACE_WHEN_LEAVE_EXIST: " + ex);
        }

        int showOvertime = 0;
        try {
            showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
        } catch (Exception ex) {

            System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>");
            showOvertime = 0;
        }

        //update by satrya 2013-04-09
        long oidScheduleOff = 0;
        try {
            oidScheduleOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
        } catch (Exception ex) {

            System.out.println("<blink>OID_DAY_OFF NOT TO BE SET</blink>");
            oidScheduleOff = 0;
        }
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName("com.dimata.harisma.session.leave.LeaveConfigDinamis").newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }



        Hashtable vctSchIDOff = new Hashtable();
        Hashtable hashSchOff = new Hashtable();
        HolidaysTable holidaysTable = new HolidaysTable();
        Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();
        String whereClausePeriod = "";
        if (rekapitulasiAbsensi.getDtTo() != null && rekapitulasiAbsensi.getDtFrom() != null) {
            whereClausePeriod = "\"" + Formater.formatDate(rekapitulasiAbsensi.getDtTo(), "yyyy-MM-dd HH:mm:ss") + "\" >="
                    + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " AND "
                    + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= \"" + Formater.formatDate(rekapitulasiAbsensi.getDtFrom(), "yyyy-MM-dd HH:mm:ss") + "\"";
        }

        Hashtable hashPeriod = new Hashtable();
        Vector vReason = null;
        //Hashtable hashSectionClone = new Hashtable(hashSection);  
        HashTblPayDay hashTblPayDay = new HashTblPayDay();
        long oidEmployeeDw = Dw;
        String where = rekapitulasiAbsensi.getWhereClauseEMployee();
        if (iCommand == Command.LIST) {
            EmployeeSrcRekapitulasiAbs employeeSrcRekapitulasiAbs = PstEmployee.getEmployeeFilter(0, 0, where, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
            String sectionId = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstEmployee.getSectionIdByEmpId(0, 0, (employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN(" + employeeSrcRekapitulasiAbs.getEmpId() + ")" : ""), PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]) : "";

            hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
            vctSchIDOff = PstScheduleSymbol.getHashScheduleId(PstScheduleCategory.CATEGORY_OFF);
            hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, whereClausePeriod, "");
            holidaysTable = PstPublicHolidays.getHolidaysTable(rekapitulasiAbsensi.getDtFrom(), rekapitulasiAbsensi.getDtTo());
            hashPositionLevel = PstPosition.hashGetPositionLevel();
            listCompany = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstCompany.list(0, 0, (employeeSrcRekapitulasiAbs.getCompanyId() != null && employeeSrcRekapitulasiAbs.getCompanyId().length() > 0 ? PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " IN(" + employeeSrcRekapitulasiAbs.getCompanyId() + ")" : ""), PstCompany.fieldNames[PstCompany.FLD_COMPANY]) : new Vector();
            hashDivision = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstDivision.hashListDivision(0, 0, (employeeSrcRekapitulasiAbs.getDivisionId() != null && employeeSrcRekapitulasiAbs.getDivisionId().length() > 0 ? PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " IN(" + employeeSrcRekapitulasiAbs.getDivisionId() + ")" : "")) : new Hashtable();
            hashDepartment = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstDepartment.hashListDepartment(0, 0, (employeeSrcRekapitulasiAbs.getDepartmentId() != null && employeeSrcRekapitulasiAbs.getDepartmentId().length() > 0 ? PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " IN(" + employeeSrcRekapitulasiAbs.getDepartmentId() + ")" : "")) : new Hashtable();
            hashSection = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstSection.hashListSection(0, 0, (sectionId != null && sectionId.length() > 0 ? PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " IN(" + sectionId + ")" : "")) : new Hashtable();
            hashEmployee = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstEmployee.hashListEmployee(0, 0, rekapitulasiAbsensi.whereClauseEmpId(employeeSrcRekapitulasiAbs.getEmpId())) : new Hashtable();

            //   Vector nilaial = PstAlStockTaken.getAnnualLeave(rekapitulasiAbsensi.getDtFrom(), rekapitulasiAbsensi.getDtTo());
            hashEmployeeSection = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstEmployee.hashListEmployeeSection(0, 0, rekapitulasiAbsensi.whereClauseEmpId(employeeSrcRekapitulasiAbs.getEmpId())) : new Hashtable();

            listAttdAbsensi = employeeSrcRekapitulasiAbs.getEmpId() != null && employeeSrcRekapitulasiAbs.getEmpId().length() > 0 ? PstEmpSchedule.getListAttendaceRekap(attdConfig, leaveConfig, rekapitulasiAbsensi.getDtFrom(), rekapitulasiAbsensi.getDtTo(), employeeSrcRekapitulasiAbs.getEmpId(), vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig, hashPeriod) : new Hashtable();
            vReason = PstReason.listReason(0, 0, PstReason.fieldNames[PstReason.FLD_REASON_TIME] + "=" + PstReason.REASON_TIME_YES, PstReason.fieldNames[PstReason.FLD_NUMBER_OF_SHOW] + " ASC ");
            listPayDayfromSalLevel = PstPayEmpLevel.getHashPayDayFromSalLev(employeeSrcRekapitulasiAbs.getEmpId(), rekapitulasiAbsensi.getDtTo());

            //hashSectionClone = new Hashtable(hashSection); 
             /*Hashtable listAttdAbsensiClone = new Hashtable(listAttdAbsensi);  
             Hashtable hashDepartmentClone = new Hashtable(hashDepartment); 
             Hashtable hashDivisionClone = new Hashtable(hashDivision); 
             Hashtable hashEmployeeClone = new Hashtable(hashEmployee); 
             Hashtable hashEmployeeSectionClone = new Hashtable(hashEmployeeSection); */


            /*rekapitulasiAbsensi.setHashDepartment(hashDepartmentClone);
             rekapitulasiAbsensi.setHashDivision(hashDivisionClone);
             rekapitulasiAbsensi.setHashEmployee(hashEmployeeClone);
             rekapitulasiAbsensi.setHashEmployeeSection(hashEmployeeSectionClone);
             rekapitulasiAbsensi.setHashSection(hashSectionClone);
             rekapitulasiAbsensi.setListAttdAbsensi(listAttdAbsensiClone);*/
            rekapitulasiAbsensi.setListCompany(listCompany);
            //rekapitulasiAbsensi.setvReason(vReason); 
            rekapitulasiAbsensi.setDtFrom(rekapitulasiAbsensi.getDtFrom());
            rekapitulasiAbsensi.setDtTo(rekapitulasiAbsensi.getDtTo());
            rekapitulasiAbsensi.setJudul(" REKAPITULASI  ABSENSI : ");

            if (rekapitulasiAbsensi.getSourceTYpe() != 0) {
                hashTblPayDay = PstPayDay.hashtblPayDay(0, 0, "", "");
            }

        }

        returnData += tableData(rekapitulasiAbsensi, listCompany, hashDivision, hashDepartment, hashSection, hashEmployee, hashEmployeeSection, listAttdAbsensi, vReason, viewschedule);

        return returnData;
    }

    private String tableData(RekapitulasiAbsensi rekapitulasiAbsensi, Vector listCompany, Hashtable hashDivision, Hashtable hashDepartment, Hashtable hashSection, Hashtable hashEmployee, Hashtable hashEmployeeSection, Hashtable listAttdAbsensi, Vector vReason, int viewschedule) {
        String returnData = "";
        if (rekapitulasiAbsensi == null) {
            return returnData;
        }
        String ClientName = "";
        try {
            ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        boolean withH_EO = false;
        if ((ClientName.equals("PEPITO")) || (ClientName.equals("MINIMART"))) {
            withH_EO = true;
        }

        returnData += "<table id='rekapitulasi' class='table table-bordered'>"
                + "<tbody>"
                + "<tr>"
                + "<th rowspan='2' style='vertical-align:middle;text-align:center'>Head Count</th>"
                + "<th rowspan='2' style='vertical-align:middle;text-align:center'>No</th>"
                + "<th rowspan='2' style='vertical-align:middle;text-align:center''>Nama</th>";
        if ((ClientName.equals("PEPITO"))) {
            if (withH_EO) {
                returnData += "<th colspan='" + (vReason != null && vReason.size() > 0 ? 12 + vReason.size() : 12) + "' style='text-align:center'>Absensi</th>";
            } else {
                returnData += "<th colspan='" + (vReason != null && vReason.size() > 0 ? 11 + vReason.size() : 11) + "' style='text-align:center'>Absensi</th>";
            }
        } else {
            if (withH_EO) {
                returnData += "<th colspan='" + (vReason != null && vReason.size() > 0 ? 10 + vReason.size() : 10) + "' style='text-align:center'>Absensi</th>";
            } else {
                returnData += "<th colspan='" + (vReason != null && vReason.size() > 0 ? 9 + vReason.size() : 9) + "' style='text-align:center'>Absensi</th>";
            }
        }

        long diffStartToFinish = 0;
        int itDate = 0;
        if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null) {
            diffStartToFinish = rekapitulasiAbsensi.getDtTo().getTime() - rekapitulasiAbsensi.getDtFrom().getTime();
            if (diffStartToFinish >= 0) {
                itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)) + 1;
                returnData += "<th colspan='" + itDate + "' style='text-align:center'>Working Schedule <br>" + Formater.formatDate(rekapitulasiAbsensi.getDtFrom(), "dd MMM yyyy") + " s/d " + Formater.formatDate(rekapitulasiAbsensi.getDtTo(), "dd MMM yyyy") + "</th>";
            }
        }
        returnData += "</tr>"
                + "<tr>";

        returnData += "<th style='text-align:center'>HK</th>";
        if (withH_EO) {
            returnData += "<th style='text-align:center'>E</th>";
            returnData += "<th style='text-align:center'>H</th>";
        } else {
            returnData += "<th style='text-align:center'>H</th>";
        }

        returnData += "<th style='text-align:center'>R</th>";
        returnData += "<th style='text-align:center'>A</th>";
        returnData += "<th style='text-align:center'>D</th>";
        returnData += "<th style='text-align:center'>I</th>";
        returnData += "<th style='text-align:center'>C</th>";
        returnData += "<th style='text-align:center'>S</th>";
        if (vReason != null && vReason.size() > 0) {
            for (int d = 0; d < vReason.size(); d++) {
                Reason reason = (Reason) vReason.get(d);
                returnData += "<th style='text-align:center'>" + reason.getKodeReason() + "</th>";
            }
        }
        if ((ClientName.equals("PEPITO"))) {
            returnData += "<th style='text-align:center'>SO 4</th>";
            returnData += "<th style='text-align:center'>SO 8</th>";
        }
        returnData += "<th style='text-align:center'>Total</th>";

        long dayOFF = 0;
        try {
            dayOFF = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
        } catch (Exception ex) {
            System.out.println("OID_DAY_OFF NOT Be SET" + ex);
            dayOFF = 0;
        }

        long extraDayOFF = 0;
        try {
            extraDayOFF = Long.parseLong(PstSystemProperty.getValueByName("OID_EXTRA_OFF"));
        } catch (Exception ex) {
            System.out.println("OID_DAY_OFF NOT Be SET" + ex);
            extraDayOFF = 0;
        }

        long intAl = 0;
        try {
            String sintAl = PstSystemProperty.getValueByName("VALUE_ANNUAL_LEAVE");
            intAl = Integer.parseInt(sintAl);
        } catch (Exception ex) {
            System.out.println("VALUE_ANNUAL_LEAVE NOT Be SET" + ex);
            intAl = 0;
        }

        long intDp = 0;
        try {
            String sintDp = PstSystemProperty.getValueByName("VALUE_DAY_OF_PAYMENT");
            intDp = Integer.parseInt(sintDp);
        } catch (Exception ex) {
            System.out.println("VALUE_DAY_OF_PAYMENT NOT Be SET" + ex);
            intDp = 0;
        }

        long intB = 0;
        try {
            String sintB = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL");
            intB = Integer.parseInt(sintB);
        } catch (Exception ex) {
            System.out.println("VALUE_DAY_OF_PAYMENT NOT Be SET" + ex);
            intB = 0;
        }


        if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null) {
            diffStartToFinish = rekapitulasiAbsensi.getDtTo().getTime() - rekapitulasiAbsensi.getDtFrom().getTime();
            if (diffStartToFinish >= 0) {
                itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)) + 1;
                for (int idx = 0; idx < itDate; idx++) {
                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                    Date selectedDatePrev = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + (idx >= 0 ? idx - 1 : idx)));
                    SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                    String dayString = formatterDay.format(selectedDate);
                    // hanya untuk cobactrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%", "0", "0");
                    if (idx != 0 && selectedDate.getYear() == selectedDatePrev.getYear()) {

                        if (dayString.equalsIgnoreCase("Sat")) {
                            returnData += "<th style='text-align:center'><font color=\"darkblue\">" + Formater.formatDate(selectedDate, "dd") + "</font></th>";
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            //ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%" + "</font>");
                            returnData += "<th style='text-align:center'><font color=\"red\">" + Formater.formatDate(selectedDate, "dd") + "</font></th>";
                        } else {
                            //ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%");
                            returnData += "<th style='text-align:center'><font color=\"red\">" + Formater.formatDate(selectedDate, "dd") + "</font></th>";
                        }

                    } else {
                        if (dayString.equalsIgnoreCase("Sat")) {
                            //ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                            returnData += "<th style='text-align:center'><font color=\"darkblue\">" + Formater.formatDate(selectedDate, "dd") + "</font></th>";
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            //ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                            returnData += "<th style='text-align:center'><font color=\"red\">" + Formater.formatDate(selectedDate, "dd") + "</font></th>";
                        } else {
                            //ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%");
                            returnData += "<th style='text-align:center'>" + Formater.formatDate(selectedDate, "dd") + "</th>";
                        }
                    }

                }
            }
        }

        returnData += "</tr>";

        int index = -1;
        String empNumTest = "";
        int no = 0;
        int totalpercompany = 0;

        try {
            if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null && listCompany != null && listCompany.size() > 0) {

                RekapitulasiPresenceDanSchedule rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule();

                if (listCompany != null && listCompany.size() > 0) {
                    for (int idxcom = 0; idxcom < listCompany.size(); idxcom++) {
                        Company company = (Company) listCompany.get(idxcom);
                        returnData += "<tr>"
                                + "<td></td>"
                                + "<td><font color='red'> <B># " + company.getCompany() + "</B></font></td>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<td></td>";
                        if ((ClientName.equals("PEPITO"))) {
                            returnData += "<td></td>"
                                    + "<td></td>";
                        }

                        if (withH_EO) {
                            returnData += "<td></td>"
                                    + "<td></td>";
                        } else {
                            returnData += "<td></td>";
                        }
                        returnData += "<td></td>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<td></td>";
                        if (vReason != null && vReason.size() > 0) {
                            for (int d = 0; d < vReason.size(); d++) {
                                //Reason reason = (Reason)vReason.get(d);
                                returnData += "<td></td>";
                            }
                        }
                        for (int idx = 0; idx < itDate; idx++) {
                            returnData += "<td></td>";
                        }
                        returnData += "</tr>";
                        int TotalEmployeePerCompany=0;
                        RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotalPerCompany = new RekapitulasiAbsensiGrandTotal();
                        if (hashDivision != null && hashDivision.size() > 0) {
                            SessDivision sessDivision = (SessDivision) hashDivision.get(company.getOID());
                            if (sessDivision != null && sessDivision.getListDivision() != null) {
                                for (int idxDiv = 0; idxDiv < sessDivision.getListDivision().size(); idxDiv++) {
                                    Division division = (Division) sessDivision.getListDivision().get(idxDiv);
                                    returnData += "<tr>"
                                            + "<td></td>"
                                            + "<td><" + division.getDivision() + "</td>"
                                            + "<td></td>"
                                            + "<td></td>"
                                            + "<td></td>";
                                    if ((ClientName.equals("PEPITO"))) {
                                        returnData += "<td></td>"
                                                + "<td></td>";
                                    }

                                    if (withH_EO) {
                                        returnData += "<td></td>"
                                                + "<td></td>";
                                    } else {
                                        returnData += "<td></td>";
                                    }
                                    returnData += "<td></td>"
                                            + "<td></td>"
                                            + "<td></td>"
                                            + "<td></td>"
                                            + "<td></td>"
                                            + "<td></td>";
                                    if (vReason != null && vReason.size() > 0) {
                                        for (int d = 0; d < vReason.size(); d++) {
                                            //Reason reason = (Reason)vReason.get(d);
                                            returnData += "<td></td>";
                                        }
                                    }
                                    for (int idx = 0; idx < itDate; idx++) {
                                        returnData += "<td></td>";
                                    }
                                    returnData += "</tr>";
                                    if (hashDepartment != null && hashDepartment.size() > 0) {
                                        SessDepartment sessDepartment = (SessDepartment) hashDepartment.get(division.getOID());
                                        if (sessDepartment != null && sessDepartment.getListDepartment() != null && sessDepartment.getListDepartment().size() > 0) {
                                            for (int idxDept = 0; idxDept < sessDepartment.getListDepartment().size(); idxDept++) {
                                                Department department = (Department) sessDepartment.getListDepartment().get(idxDept);
                                                if (department.getOID() == 3006L) {
                                                    boolean dx = true;
                                                    if (dx) {
                                                        int becek = 0;
                                                    }
                                                }
                                                returnData += "<tr>"
                                                        + "<td></td>"
                                                        + "<td><" + department.getDepartment() + "</td>"
                                                        + "<td></td>"
                                                        + "<td></td>"
                                                        + "<td></td>";
                                                if ((ClientName.equals("PEPITO"))) {
                                                    returnData += "<td></td>"
                                                            + "<td></td>";
                                                }

                                                if (withH_EO) {
                                                    returnData += "<td></td>"
                                                            + "<td></td>";
                                                } else {
                                                    returnData += "<td></td>";
                                                }
                                                returnData += "<td></td>"
                                                        + "<td></td>"
                                                        + "<td></td>"
                                                        + "<td></td>"
                                                        + "<td></td>"
                                                        + "<td></td>";
                                                if (vReason != null && vReason.size() > 0) {
                                                    for (int d = 0; d < vReason.size(); d++) {
                                                        //Reason reason = (Reason)vReason.get(d);
                                                        returnData += "<td></td>";
                                                    }
                                                }
                                                for (int idx = 0; idx < itDate; idx++) {
                                                    returnData += "<td></td>";
                                                }
                                                returnData += "</tr>";
                                                int TotalEmployeePerDeptnSection=0;
                                                RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                if (hashEmployee != null && hashEmployee.size() > 0) { 
                                                    SessEmployee sessEmployee = (SessEmployee) hashEmployee.get(department.getOID());
                                                    Vector sessEmployeeClone =sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0? new Vector(sessEmployee.getListEmployee()):null;
                                                    if (sessEmployeeClone!=null) {
                                                        int noEMployeeNoSection=1;
                                                        for (int idxEmp = 0; idxEmp < sessEmployeeClone.size(); idxEmp++) {
                                                            EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) sessEmployeeClone.get(idxEmp);
                                                            rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                            if(employeeMinimalis.getSectionId()==0){
                                                                int hk = 0 ;
                                                                Vector reasonnew = new Vector();
                                                                if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID())){
                                                                    rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                    listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                    if(vReason!=null && vReason.size()>0){
                                                                        for(int d=0;d<vReason.size();d++){
                                                                            Reason reason = (Reason)vReason.get(d);
                                                                            int totReason=rekapitulasiPresenceDanSchedule.getTotalReason(reason.getNo());
                                                                            if (reason.getNo() == intAl || reason.getNo() == intDp || reason.getNo() == intB ){           
                                                                                hk = hk + totReason; 
                                                                            }
                                                                            reasonnew.add(""+totReason);
                                                                            if(rekapitulasiAbsensiGrandTotal!=null && rekapitulasiAbsensiGrandTotal.getReason().containsKey(""+reason.getNo())){
                                                                                int totRes= rekapitulasiAbsensiGrandTotal.getTotalReason(reason.getNo())+totReason;
                                                                                rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totRes);
                                                                            } else {
                                                                                rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totReason);
                                                                            }
                                                                        }
                                                                    }
                                                                    rekapitulasiPresenceDanSchedule.setTotalWorkingDays((rekapitulasiPresenceDanSchedule.getTotalWorkingDays()));
                                                                    rekapitulasiPresenceDanSchedule.setRealTotalReason((hk)); 
                                                                    rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                    if (withH_EO) {
                                                                        rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH() + rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                        rekapitulasiAbsensiGrandTotal.setEO(rekapitulasiAbsensiGrandTotal.getEO() + rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                    } else {
                                                                        rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH() + rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                    }
                                                                    rekapitulasiAbsensiGrandTotal.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                    rekapitulasiAbsensiGrandTotal.setAnnualLeave(rekapitulasiAbsensiGrandTotal.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                    rekapitulasiAbsensiGrandTotal.setdPayment(rekapitulasiAbsensiGrandTotal.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                    rekapitulasiAbsensiGrandTotal.setUnpaidLeave(rekapitulasiAbsensiGrandTotal.getUnpaidLeave() +rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                    rekapitulasiAbsensiGrandTotal.setSpecialLeave(rekapitulasiAbsensiGrandTotal.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                    rekapitulasiAbsensiGrandTotal.setSo4j(rekapitulasiAbsensiGrandTotal.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                    rekapitulasiAbsensiGrandTotal.setSo8j(rekapitulasiAbsensiGrandTotal.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                    rekapitulasiAbsensiGrandTotal.setS(rekapitulasiAbsensiGrandTotal.getS()+rekapitulasiPresenceDanSchedule.getTotalS());

                                                                    rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                    if (withH_EO){
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setEO(rekapitulasiAbsensiGrandTotalPerCompany.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                     } else {
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                     }
                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setAnnualLeave(rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setdPayment(rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());

                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setSpecialLeave(rekapitulasiAbsensiGrandTotalPerCompany.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setUnpaidLeave(rekapitulasiAbsensiGrandTotalPerCompany.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());

                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setSo4j(rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setSo8j(rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setS(rekapitulasiAbsensiGrandTotalPerCompany.getS()+rekapitulasiPresenceDanSchedule.getTotalS());
                                                                }
                                                                TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                                TotalEmployeePerCompany = TotalEmployeePerCompany + 1; 
                                                                returnData += "<tr>"
                                                                        + "<td>"+(noEMployeeNoSection++)+"</td>"
                                                                        + "<td>"+employeeMinimalis.getEmployeeNum()+"</td>"
                                                                        + "<td>"+employeeMinimalis.getFullName()+"</td>"
                                                                        + "<td>"+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly()+"</td>";
                                                                if (withH_EO){
                                                                   returnData += "<td>"+rekapitulasiPresenceDanSchedule.getDayOffScheduleH()+"</td>"
                                                                           + "<td>"+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO()+"</td>";
                                                                } else {
                                                                   returnData += "<td>"+rekapitulasiPresenceDanSchedule.getDayOffSchedule()+"</td>";
                                                                }
                                                                returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit()+"</td>"
                                                                        + "<td>"+rekapitulasiPresenceDanSchedule.getAnnualLeave()+"</td>"
                                                                        + "<td>"+rekapitulasiPresenceDanSchedule.getdPayment()+"</td>"
                                                                        + "<td>"+rekapitulasiPresenceDanSchedule.getUnpaidLeave()+"</td>"
                                                                        + "<td>"+rekapitulasiPresenceDanSchedule.getSpecialLeave()+"</td>"
                                                                        + "<td>"+rekapitulasiPresenceDanSchedule.getTotalS()+"</td>";
                                                                if(reasonnew!=null && reasonnew.size()>0){
                                                                    for (int d = 0; d < reasonnew.size(); d++) {
                                                                        String reasonn = (String) reasonnew.get(d);
                                                                        returnData += "<td>"+reasonn+"</td>";
                                                                    }
                                                                }
                                                                if ((ClientName.equals("PEPITO"))){
                                                                    returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()+"</td>"
                                                                            + "<td>"+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()+"</td>";
                                                                 }
                                                            
                                                                if (withH_EO){
                                                                    returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito()+"</td>";
                                                                    totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito();
                                                                } else {
                                                                    returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalSemuanya()+"</td>";
                                                                    totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanya() ;
                                                                }
                                                                
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                    long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                    if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                        returnData += "<td><b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b></td>";
                                                                    } else {
                                                                        returnData += "<td>"+rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate)+"</td>";
                                                                    }
                                                                }
                                                                returnData += "</tr>";
                                                            }
                                                        } // end loop emloyee
                                                        if (hashSection == null || (hashSection != null && hashSection.size() > 0 && hashSection.containsKey("" + department.getOID()) == false)) {
                                                            returnData += "<tr>"
                                                                    + "<td>" + (noEMployeeNoSection - 1) + "</td>"
                                                                    + "<td></td>"
                                                                    + "<td><b>Total Department</b></td>"
                                                                    + "<td><b>" + rekapitulasiAbsensiGrandTotal.getHK() + "</b></td>";
                                                            
                                                            if (withH_EO) {
                                                                returnData += "<td><b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b></td>"
                                                                        + "<td><b>"+rekapitulasiAbsensiGrandTotal.getEO()+"</b></td>";
                                                            } else {
                                                                returnData += "<td><b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b></td>";
                                                            }
                                                            returnData += "<td><b>"+rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+"</b></td>"
                                                                    + "<td><b>"+rekapitulasiAbsensiGrandTotal.getAnnualLeave()+"</b></td>"
                                                                    + "<td><b>"+rekapitulasiAbsensiGrandTotal.getdPayment()+"</b></td>"
                                                                    + "<td><b>"+rekapitulasiAbsensiGrandTotal.getUnpaidLeave()+"</b></td>"
                                                                    + "<td><b>"+rekapitulasiAbsensiGrandTotal.getSpecialLeave()+"</b></td>"
                                                                    + "<td><b>"+rekapitulasiAbsensiGrandTotal.getS()+"</b></td>";
                                                            if (vReason != null && vReason.size() > 0) {
                                                                for (int d = 0; d < vReason.size(); d++) {
                                                                    Reason reason = (Reason)vReason.get(d);
                                                                    int totReason=rekapitulasiAbsensiGrandTotal.getGrandTotalReason(reason.getNo());
                                                                    returnData += "<td>"+totReason+"</td>";
                                                                }
                                                            }
                                                            if ((ClientName.equals("PEPITO"))){
                                                                returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotal.getSo4j())+"</b></td>"
                                                                        + "<td><b>"+(rekapitulasiAbsensiGrandTotal.getSo8j())+"</b></td>";
                                                            }
                                                            if (withH_EO){
                                                                returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanyaByPepito())+"</b></td>";
                                                            } else {
                                                                returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanya())+"</b></td>";
                                                            }
                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                returnData += "<td></td>";
                                                            }
                                                            returnData += "</tr>";
                                                        }
                                                    }
                                                } //end hash employee
                                                if (hashSection != null && hashSection.size() > 0 && hashSection.containsKey(""+department.getOID())) {
                                                    SessSection sessSection = (SessSection) hashSection.get(""+department.getOID());
                                                    Vector vSection = sessSection != null && sessSection.getListSection() != null && sessSection.getListSection().size() > 0?new Vector(sessSection.getListSection()):null;
                                                    if (vSection!=null) {
                                                        int loopBarisSection=0;
                                                        for (int idxSec = 0; idxSec < vSection.size(); idxSec++) {
                                                            Section dSection = (Section) vSection.get(idxDept);
                                                            //dept
                                                            loopBarisSection = loopBarisSection + 1;
                                                            vSection.remove(idxSec);
                                                            idxSec = idxSec -1;
                                                            returnData += "<tr>"
                                                                    + "<td> &nbsp;&nbsp;<b>>>> " + dSection.getSection()+"</b></td>"
                                                                    + "<td></td>"
                                                                    + "<td></td>";
                                                            if (withH_EO){
                                                                returnData += "<td></td>"
                                                                        + "<td></td>";
                                                            } else {
                                                                returnData += "<td></td>";
                                                            }
                                                            returnData += "<td></td>"
                                                                    + "<td></td>"
                                                                    + "<td></td>"
                                                                    + "<td></td>"
                                                                    + "<td></td>"
                                                                    + "<td></td>";
                                                            if(vReason!=null && vReason.size()>0){
                                                                for(int d=0;d<vReason.size();d++){
                                                                    //Reason reason = (Reason)vReason.get(d);
                                                                    returnData += "<td></td>";
                                                                }
                                                            }
                                                            returnData += "<td></td>";
                                                            if ((ClientName.equals("PEPITO"))){
                                                                returnData += "<td></td>"
                                                                        + "<td></td>"; 
                                                            }
                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                returnData += "<td></td>";
                                                            }
                                                            int noEMployeeNoSection=1;
                                                            if (hashEmployeeSection != null && hashEmployeeSection.size() > 0) {
                                                                SessEmployee sessEmployee = (SessEmployee) hashEmployeeSection.get(dSection.getOID());
                                                                if (sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0) {
                                                                    RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotalSec = new RekapitulasiAbsensiGrandTotal();
                                                                    int loopBarieEMpSec=0;
                                                                    int axx = 0;
                                                                    Vector cloneListEMp = sessEmployee.getListEmployee()!=null && sessEmployee.getListEmployee().size()>0?new Vector(sessEmployee.getListEmployee()):new Vector();
                                                                    for (int idxEmpSec = 0; idxEmpSec < cloneListEMp.size(); idxEmpSec++) {
                                                                        axx += 1;
                                                                        EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) cloneListEMp.get(idxEmpSec);
                                                                        rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                                        if(employeeMinimalis.getSectionId()!=0){
                                                                            Vector reasonnew = new Vector();
                                                                            int hk = 0;
                                                                            if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID())){
                                                                                rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                                listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                                if(vReason!=null && vReason.size()>0){
                                                                                    for(int d=0;d<vReason.size();d++){
                                                                                        Reason reason = (Reason)vReason.get(d);
                                                                                        int totReason=rekapitulasiPresenceDanSchedule.getTotalReason(reason.getNo());
                                                                                        if (reason.getNo() == intAl || reason.getNo() == intDp || reason.getNo() == intB ){
                                                                                            hk = hk + totReason; 
                                                                                        }
                                                                                        reasonnew.add(""+totReason);
                                                                                        if(rekapitulasiAbsensiGrandTotalSec!=null && rekapitulasiAbsensiGrandTotalSec.getReason().containsKey(""+reason.getNo())){
                                                                                            int totRes= rekapitulasiAbsensiGrandTotalSec.getTotalReason(reason.getNo())+totReason;
                                                                                            rekapitulasiAbsensiGrandTotalSec.addReason(reason.getNo(), totRes);
                                                                                        }else{
                                                                                            rekapitulasiAbsensiGrandTotalSec.addReason(reason.getNo(), totReason);
                                                                                        }
                                                                                        if(rekapitulasiAbsensiGrandTotal!=null && rekapitulasiAbsensiGrandTotal.getReason().containsKey(""+reason.getNo())){
                                                                                            int totRes= rekapitulasiAbsensiGrandTotal.getTotalReason(reason.getNo())+totReason;
                                                                                            rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totRes);
                                                                                        }else{
                                                                                            rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totReason);
                                                                                        }
                                                                                    }
                                                                                }
                                                                                rekapitulasiPresenceDanSchedule.setTotalWorkingDays((rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly()));
                                                                                rekapitulasiPresenceDanSchedule.setRealTotalReason((hk));   
                                                                                rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                                if (withH_EO){
                                                                                    rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                                    rekapitulasiAbsensiGrandTotal.setEO(rekapitulasiAbsensiGrandTotal.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                                } else {
                                                                                    rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                }
                                                                                rekapitulasiAbsensiGrandTotal.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                rekapitulasiAbsensiGrandTotal.setAnnualLeave(rekapitulasiAbsensiGrandTotal.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                rekapitulasiAbsensiGrandTotal.setdPayment(rekapitulasiAbsensiGrandTotal.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());

                                                                                rekapitulasiAbsensiGrandTotal.setUnpaidLeave(rekapitulasiAbsensiGrandTotal.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                                rekapitulasiAbsensiGrandTotal.setSpecialLeave(rekapitulasiAbsensiGrandTotal.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());

                                                                                rekapitulasiAbsensiGrandTotal.setSo4j(rekapitulasiAbsensiGrandTotal.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                rekapitulasiAbsensiGrandTotal.setSo8j(rekapitulasiAbsensiGrandTotal.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                rekapitulasiAbsensiGrandTotal.setS(rekapitulasiAbsensiGrandTotal.getS()+rekapitulasiPresenceDanSchedule.getTotalS());

                                                                                rekapitulasiAbsensiGrandTotalSec.setHK(rekapitulasiAbsensiGrandTotalSec.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                                //rekapitulasiAbsensiGrandTotalSec.setHK(rekapitulasiAbsensiGrandTotalSec.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                                if (withH_EO){
                                                                                   rekapitulasiAbsensiGrandTotalSec.setH(rekapitulasiAbsensiGrandTotalSec.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                                   rekapitulasiAbsensiGrandTotalSec.setEO(rekapitulasiAbsensiGrandTotalSec.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                                } else {
                                                                                   rekapitulasiAbsensiGrandTotalSec.setH(rekapitulasiAbsensiGrandTotalSec.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                }
                                                                                rekapitulasiAbsensiGrandTotalSec.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalSec.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                rekapitulasiAbsensiGrandTotalSec.setAnnualLeave(rekapitulasiAbsensiGrandTotalSec.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                rekapitulasiAbsensiGrandTotalSec.setdPayment(rekapitulasiAbsensiGrandTotalSec.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                                rekapitulasiAbsensiGrandTotalSec.setUnpaidLeave(rekapitulasiAbsensiGrandTotalSec.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                                rekapitulasiAbsensiGrandTotalSec.setSpecialLeave(rekapitulasiAbsensiGrandTotalSec.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());

                                                                                rekapitulasiAbsensiGrandTotalSec.setSo4j(rekapitulasiAbsensiGrandTotalSec.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                rekapitulasiAbsensiGrandTotalSec.setSo8j(rekapitulasiAbsensiGrandTotalSec.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                rekapitulasiAbsensiGrandTotalSec.setS(rekapitulasiAbsensiGrandTotalSec.getS()+rekapitulasiPresenceDanSchedule.getTotalS());

                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                                //rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());

                                                                                 if (withH_EO){
                                                                                   rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                                   rekapitulasiAbsensiGrandTotalPerCompany.setEO(rekapitulasiAbsensiGrandTotalPerCompany.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                                } else {
                                                                                   rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                }
                                                                                //rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setAnnualLeave(rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setdPayment(rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setSpecialLeave(rekapitulasiAbsensiGrandTotalPerCompany.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setUnpaidLeave(rekapitulasiAbsensiGrandTotalPerCompany.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());


                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setSo4j(rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setSo8j(rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                rekapitulasiAbsensiGrandTotalPerCompany.setS(rekapitulasiAbsensiGrandTotalPerCompany.getS()+rekapitulasiPresenceDanSchedule.getTotalS());
                                                                            }
                                                                            loopBarieEMpSec = loopBarieEMpSec + 1;
                                                                            TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                                            TotalEmployeePerCompany = TotalEmployeePerCompany + 1;
                                                                            cloneListEMp.remove(idxEmpSec);
                                                                            idxEmpSec = idxEmpSec -1;
                                                                            returnData += "<tr>"
                                                                                    + "<td>"+(noEMployeeNoSection++)+"</td>"
                                                                                    + "<td>"+employeeMinimalis.getEmployeeNum()+"</td>"
                                                                                    + "<td>"+employeeMinimalis.getFullName()+"</td>"
                                                                                    + "<td>"+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly()+"</td>";
                                                                            if (withH_EO){
                                                                                returnData += "<td>"+rekapitulasiPresenceDanSchedule.getDayOffScheduleH()+"</td>"
                                                                                        + "<td>"+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO()+"</td>";
                                                                            } else {
                                                                                returnData += "<td>"+rekapitulasiPresenceDanSchedule.getDayOffSchedule()+"</td>";
                                                                            }
																			returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit()+"</td>"
																					+ "<td>"+rekapitulasiPresenceDanSchedule.getAnnualLeave()+"</td>"
																					+ "<td>"+rekapitulasiPresenceDanSchedule.getdPayment()+"</td>"
																					+ "<td>"+rekapitulasiPresenceDanSchedule.getUnpaidLeave()+"</td>"
																					+ "<td>"+rekapitulasiPresenceDanSchedule.getSpecialLeave()+"</td>"
																					+ "<td>"+rekapitulasiPresenceDanSchedule.getTotalS()+"</td>";
																			if(reasonnew!=null && reasonnew.size()>0){
																				for(int d=0;d<reasonnew.size();d++){
																					String reasonn = (String) reasonnew.get(d);
																					returnData += "<td>"+reasonn+"</td>";
																				}
																			}
																			if ((ClientName.equals("PEPITO"))){
																				returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()+"</td>"
																						+ "<td>"+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()+"</td>";
																			}
																			if (withH_EO){
																				returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito()+"</td>";
																				totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito() ;
																			}else{
																				returnData += "<td>"+rekapitulasiPresenceDanSchedule.getTotalSemuanya()+"</td>";
																				totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanya() ;
																			}
																			
																			for (int idx = 0; idx < itDate; idx++) {
																				Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
																				if (viewschedule == 2){
																					long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
																					if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
																						returnData += "<td><b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b></td>";
																					} else {
																						returnData += "<td>"+rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate)+"</td>";
																					}
																				} else if (viewschedule == 1) {
																					long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
																					if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
																						returnData += "<td><b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataStatus(selectedDate) +"</b></td>";
																					} else {
																						returnData += "<td>"+rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataStatus(selectedDate)+"</td>";
																					}
																				} else {
																					long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
																					if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
																						returnData += "<td><b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b></td>";
																					} else {
																						if ((ClientName.equals("MINIMART")) || (ClientName.equals("PEPITO"))){
																							returnData += "<td>"+rekapitulasiPresenceDanSchedule.getDataReasonOrScheduleStatusForMinimart(selectedDate , employeeMinimalis.getDivisionId())+"</td>";
																						 } else {
																							returnData += "<td>"+rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate)+"</td>";
																						 } 
																					}
																				}
																			}
																			returnData += "</tr>";
                                                                        }
                                                                    }
																	returnData += "<tr>"
																			+ "<td>"+(noEMployeeNoSection-1)+"</td>"
																			+ "<td></td>"
																			+ "<td><b>Total Section</b></td>"
																			+ "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getHK()+"</b></td>";
																	if (withH_EO){
																		returnData += "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getH()+"</b></td>"
																				+ "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getH()+"</b></td>";
																	} else {
																		returnData += "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getH()+"</b></td>";
																	}
																	returnData += "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getLateLebihLimaMenit()+"</b></td>"
																			+ "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getAnnualLeave()+"</b></td>"
																			+ "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getdPayment()+"</b></td>"
																			+ "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getUnpaidLeave()+"</b></td>"
																			+ "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getSpecialLeave()+"</b></td>"
																			+ "<td><b>"+rekapitulasiAbsensiGrandTotalSec.getS()+"</b></td>";
																	int totReason = 0;
																	if(vReason!=null && vReason.size()>0){
																		for(int d=0;d<vReason.size();d++){
																			Reason reason = (Reason)vReason.get(d);
																			totReason=rekapitulasiAbsensiGrandTotalSec.getGrandTotalReason(reason.getNo());
																			returnData += "<td>"+totReason+"</td>";
																		}
																	}
																	rekapitulasiAbsensiGrandTotalSec.setTotalAlasan(totReason);
																	if ((ClientName.equals("PEPITO"))){
																		returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotalSec.getSo4j())+"</b></td>"
																				+ "<td><b>"+(rekapitulasiAbsensiGrandTotalSec.getSo8j())+"</b></td>";
																	}
																	if (withH_EO){
																		returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotalSec.getTotalSemuanyaByPepito())+"</b></td>";
																	} else {
																		returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotalSec.getTotalSemuanya())+"</b></td>";
																	}
																	for (int idx = 0; idx < itDate; idx++) {
																		returnData += "<td></td>";
																	}
																	returnData += "</tr>";
                                                                }
                                                            }
                                                        }
                                                    }
													returnData += "<tr>"
															+ "<td>"+(TotalEmployeePerDeptnSection)+"</td>"
															+ "<td></td>"
															+ "<td><b>Total Department</b></td>"
															+ "<td><b>"+rekapitulasiAbsensiGrandTotal.getHK()+"</b></td>";
													if (withH_EO){
														returnData += "<td><b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b></td>"
																+ "<td><b>"+rekapitulasiAbsensiGrandTotal.getEO()+"</b></td>";
													} else {
														returnData += "<td><b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b></td>";
													}
													
													returnData += "<td><b>"+rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+"</b></td>"
															+ "<td><b>"+rekapitulasiAbsensiGrandTotal.getAnnualLeave()+"</b></td>"
															+ "<td><b>"+rekapitulasiAbsensiGrandTotal.getdPayment()+"</b></td>"
															+ "<td><b>"+rekapitulasiAbsensiGrandTotal.getUnpaidLeave()+"</b></td>"
															+ "<td><b>"+rekapitulasiAbsensiGrandTotal.getSpecialLeave()+"</b></td>"
															+ "<td><b>"+rekapitulasiAbsensiGrandTotal.getS()+"</b></td>";
													
													if(vReason!=null && vReason.size()>0){
														for(int d=0;d<vReason.size();d++){
															Reason reason = (Reason)vReason.get(d);
															int totReason=rekapitulasiAbsensiGrandTotal.getGrandTotalReason(reason.getNo());
															returnData += "<td>"+totReason+"</td>";
														}
													}
													if ((ClientName.equals("PEPITO"))){
														returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotal.getSo4j())+"</b></td>"
																+ "<td><b>"+(rekapitulasiAbsensiGrandTotal.getSo8j())+"</b></td>";
													}
													if (withH_EO){
														returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanyaByPepito())+"</b></td>";
													} else {
														returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanya())+"</b></td>";
													}
													for (int idx = 0; idx < itDate; idx++) {
														returnData += "<td></td>";
													}
													returnData += "</tr>";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
						returnData += "<tr>"
								+ "<td>"+(TotalEmployeePerCompany)+"</td>"
								+ "<td></td>"
								+ "<td><b>Grand Total ---->>>>></b></td>"
								+ "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getHK()+"</b></td>";
						if (withH_EO){
							returnData += "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getH()+"</b></td>"
									+ "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getEO()+"</b></td>";
						} else {
							returnData += "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getH()+"</b></td>";
						}
						
						returnData += "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+"</b></td>"
								+ "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+"</b></td>"
								+ "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+"</b></td>"
								+ "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getUnpaidLeave()+"</b></td>"
								+ "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getSpecialLeave()+"</b></td>"
								+ "<td><b>"+rekapitulasiAbsensiGrandTotalPerCompany.getS()+"</b></td>";
						
						if(vReason!=null && vReason.size()>0){
							for(int d=0;d<vReason.size();d++){
								Reason reason = (Reason)vReason.get(d);
								int totReason=rekapitulasiAbsensiGrandTotalPerCompany.getGrandTotalReason(reason.getNo());
								returnData += "<td>"+totReason+"</td>";
							}
						}
						if ((ClientName.equals("PEPITO"))){
							returnData += "<td><b>"+(rekapitulasiAbsensiGrandTotalPerCompany.getSo4j())+"</b></td>"
									+ "<td><b>"+(rekapitulasiAbsensiGrandTotalPerCompany.getSo8j())+"</b></td>";
						}
						
						returnData += "<td><b>"+totalpercompany+"</b></td>";
						
						for (int idx = 0; idx < itDate; idx++) {
							returnData += "<td></td>";
						}
						returnData += "</tr>";
                    }

                }

            } else {
                returnData = "<i>Belum ada data dalam sistem ...</i>";
            }

        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }

        returnData += "</tbody>"
                + "</table>";



        return returnData;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
