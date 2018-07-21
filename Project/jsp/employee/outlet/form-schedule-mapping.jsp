<%-- 
    Document   : form-schedule-mapping.jsp
    Created on : Apr 24, 2014, 6:16:03 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.overtime.CtrlOvertimeDetail"%>
<%@page import="com.dimata.harisma.form.attendance.mappingoutlet.CtrlExtraScheduleOutletDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="com.dimata.harisma.form.overtime.FrmOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.gui.jsp.ControlDatePopup"%>
<%@page import="com.dimata.gui.jsp.ControlDatePopup"%>
<%@page import="com.dimata.gui.jsp.ControlDatePopup"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.form.attendance.mappingoutlet.FrmExtraScheduleOutletDetail"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutletDetail"%>
<%@page import="com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutletDetail"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.overtime.Overtime"%>
<%@page import="com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutlet"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.form.overtime.FrmOvertime"%>
<%@page import="com.dimata.harisma.form.overtime.CtrlOvertime"%>
<%@page import="com.dimata.util.Message"%>
<%@page import="com.dimata.harisma.form.attendance.mappingoutlet.CtrlExtraScheduleOutlet"%>
<%@page import="com.dimata.harisma.form.attendance.mappingoutlet.FrmExtraScheduleOutlet"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.attendance.mappingoutlet.FrmMappingOutlet"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_OUTLET, AppObjInfo.OBJ_EMPLOYEE_OUTLET);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
boolean ignoreOverlap=false;
         try{
           String sIgnoreOverlap = String.valueOf(PstSystemProperty.getValueByName("OVERTIME_IGNORE_OVERLAP"));
           if(sIgnoreOverlap!=null && sIgnoreOverlap.length()>0){
            ignoreOverlap = Boolean.parseBoolean(sIgnoreOverlap);
           }
        }catch(Exception ex){
            System.out.println("Execption REASON_DUTTY_NO: " + ex);
}
I_Leave leaveConfig = null; 
    try{
    leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    }catch (Exception e){
    System.out.println("Exception : " + e.getMessage());
    }
    


%>
<%!    
    public String drawListExtraScheduleForm(JspWriter outJsp, Vector objectClass,Date dtSelect) {
        String result = "";
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");

        ctrlist.setMaxFreezingTable(2);


        //mengambil nama dari kode komponent
        ctrlist.addHeader("Nr.", "3%");
        ctrlist.addHeader("Payroll", "10%");
        ctrlist.addHeader("Name", "15%");
        ctrlist.addHeader("Start Date", "15%");
        ctrlist.addHeader("Start Time", "7%");
        ctrlist.addHeader("End Date", "15%");
        ctrlist.addHeader("End Time", "7%");
        ctrlist.addHeader("Rest(h)", "7%");
        ctrlist.addHeader("Job Desc.", "20%");
        //ctrlist.addHeader("Type Of Schedule", "10%");
        ctrlist.addHeader("Location", "10%");
        ctrlist.addHeader("Status ", "10%");
        String selected = "<a href=\"Javascript:SetAllCheckBoxes('" + FrmMappingOutlet.FRM_MAPPING_OUTLET + "','userSelected', true)\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('" + FrmMappingOutlet.FRM_MAPPING_OUTLET + "','userSelected', false)\">Deselect All</a>";
        ctrlist.addHeader("Select <br>" + selected, "10%");
        ctrlist.addHeader("Action", "10%");


        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEditExtraSch('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        int index = -1;
        int recordNo = 1;

        //untuk mengambil Status
        Vector obj_status = new Vector(1, 1);
        Vector val_status = new Vector(1, 1);
        Vector key_status = new Vector(1, 1);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);


        if (objectClass != null && objectClass.size() > 0) {
            ctrlist.drawListHeaderWithJsVer2(outJsp);//header
            int no = 0;
            Date startTime = dtSelect;
            startTime.setHours(17);
            startTime.setMinutes(0);
            Date endTime = dtSelect;
            endTime.setHours(18);
            endTime.setMinutes(0);
            Vector location = PstLocation.listAll();
            Vector val_location = new Vector();
            Vector key_location = new Vector();
            if (location != null && location.size() > 0) {
                for (int idxloc = 0; idxloc < location.size(); idxloc++) {
                    Location locationx = (Location) location.get(idxloc);
                    val_location.add("" + locationx.getOID());
                    key_location.add(locationx.getName());
                }
            }


            for (int idx = 0; idx < objectClass.size(); idx++) {
                Vector rowx = new Vector();
                Vector listx = (Vector) objectClass.get(idx);
                Employee employee = (Employee) listx.get(0);
                ExtraScheduleOutlet extraScheduleOutlet = (ExtraScheduleOutlet) listx.get(1);
                ExtraScheduleOutletDetail extraScheduleOutletDetail = (ExtraScheduleOutletDetail) listx.get(2);
                no = no + 1;
                String inputHiddenMain =  
                        "<input type=\"hidden\" name=\"" + FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_EXTRA_SCHEDULE_MAPPING_ID]+"_"+employee.getOID() + "\" value=\"" + extraScheduleOutlet.getOID() + "\">";
                rowx.add("" + no + inputHiddenMain); 
                rowx.add(employee.getEmployeeNum() + "<input type=\"hidden\" name=\"" + FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_EMPLOYEE_ID]+"_"+employee.getOID() + "\" value=\"" + employee.getOID() + "\">");
                rowx.add(employee.getFullName());
                rowx.add(ControlDate.drawDate(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_START_DATE_PLAN]+"_"+employee.getOID(), extraScheduleOutletDetail.getStartDatePlan() != null ? extraScheduleOutletDetail.getStartDatePlan() : startTime, "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_START_DATE_PLAN]+"_"+employee.getOID(), (extraScheduleOutletDetail.getStartDatePlan() != null) ? extraScheduleOutletDetail.getStartDatePlan() : startTime, "formElemen", 24, 1, 0));

                rowx.add(ControlDate.drawDate(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_END_DATE_PLAN]+"_"+employee.getOID(), extraScheduleOutletDetail.getEndDatePlan() != null ? extraScheduleOutletDetail.getEndDatePlan() :endTime, "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_END_DATE_PLAN]+"_"+employee.getOID(), (extraScheduleOutletDetail.getEndDatePlan() != null) ? extraScheduleOutletDetail.getEndDatePlan() : endTime, "formElemen", 24, 1, 0));


                String dtRest = ControlDate.drawDate(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_REST_TIME_START]+"_"+employee.getOID(), extraScheduleOutletDetail.getRestTimeStart() != null ? extraScheduleOutletDetail.getRestTimeStart() : (extraScheduleOutletDetail.getStartDatePlan() != null ? extraScheduleOutletDetail.getStartDatePlan() : startTime), "formElemen", 1, -5);
                String tmRest = ControlDate.drawTime(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_REST_TIME_START]+"_"+employee.getOID(), extraScheduleOutletDetail.getRestTimeStart() != null ? extraScheduleOutletDetail.getRestTimeStart() : (extraScheduleOutletDetail.getStartDatePlan() != null ? extraScheduleOutletDetail.getStartDatePlan() : startTime), "formElemen", 24, 1, 0);

                rowx.add("<table><tr><td nowrap> start " + dtRest + "</td></tr><tr><td nowrap> " + tmRest + " for <input type=\"text\" name=\"" + FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_REST_TIME_HR]+"_"+employee.getOID() + "\" value=\"" + extraScheduleOutletDetail.getRestTimeHr() + "\" class=\"formElemen\" size=\"5\"> Hr </td></tr></table>");


                rowx.add("<input type=\"text\" name=\"" + FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_JOB_DESCH]+"_"+employee.getOID() + "\" value=\"" + extraScheduleOutletDetail.getJobDesc() + "\" class=\"formElemen\" size=\"25\">");
                //rowx.add(ControlCombo.draw(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_TYPE_OFF_SCHEDULE], null, "" + extraScheduleOutletDetail.getTypeOffSchedule(), PstExtraScheduleOutletDetail.getTypeOffScheduleValues(), PstExtraScheduleOutletDetail.getTypeOffScheduleKeys()));
                rowx.add(ControlCombo.draw(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_LOCATION_ID]+"_"+employee.getOID(), null, "" + extraScheduleOutletDetail.getLocationId(), val_location, key_location));
                rowx.add(ControlCombo.draw(FrmExtraScheduleOutletDetail.fieldNames[FrmExtraScheduleOutletDetail.FRM_FLD_STATUS_DOCUMENT]+"_"+employee.getOID(), null, "" + extraScheduleOutletDetail.getStatusDocDetail(), val_status, key_status));
                rowx.add("<input type=\"checkbox\" name=\"userSelected\" value=\"" + employee.getOID()+"_"+extraScheduleOutletDetail.getOID() + "\" class=\"formElemen\" size=\"10\">");

                rowx.add(extraScheduleOutletDetail.getOID() == 0 ? "can't delete" : "<a href=\"Javascript:cmdDeletePerEmp('" + extraScheduleOutletDetail.getOID() + "')\">Delete</a>");
                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idx);
            }
            ctrlist.drawListEndTableJsVer2(outJsp);
        }

        return result;
    }
%>

<%!
public String drawListOvertimeForm(JspWriter outJsp, Vector objectClass,Date dtSelect) {
        String result = "";
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");

        ctrlist.setMaxFreezingTable(2);


        //mengambil nama dari kode komponent
        ctrlist.addHeader("Nr.", "3%");
        ctrlist.addHeader("Payroll", "10%");
        ctrlist.addHeader("Name", "15%");
        ctrlist.addHeader("Start Date", "15%");
        ctrlist.addHeader("Start Time", "7%");
        ctrlist.addHeader("End Date", "15%");
        ctrlist.addHeader("End Time", "7%");
        ctrlist.addHeader("Rest(h)", "7%");
        ctrlist.addHeader("Job Desc.", "20%");
        ctrlist.addHeader("Location", "10%");
        ctrlist.addHeader("Paid w/", "10%");
        ctrlist.addHeader("Allowance", "10%");
        ctrlist.addHeader("Status ", "10%");
        String selected = "<a href=\"Javascript:SetAllCheckBoxes('" + FrmMappingOutlet.FRM_MAPPING_OUTLET + "','userSelected', true)\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('" + FrmMappingOutlet.FRM_MAPPING_OUTLET + "','userSelected', false)\">Deselect All</a>";
        ctrlist.addHeader("Select <br>" + selected, "10%");
        ctrlist.addHeader("Action", "10%");


        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEditExtraSch('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        int index = -1;
        int recordNo = 1;

        //untuk mengambil Status
        Vector obj_status = new Vector(1, 1);
        Vector val_status = new Vector(1, 1);
        Vector key_status = new Vector(1, 1);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);


        if (objectClass != null && objectClass.size() > 0) {
            ctrlist.drawListHeaderWithJsVer2(outJsp);//header
            int no = 0;
            Date startTime = dtSelect;
            startTime.setHours(17);
            startTime.setMinutes(0);
            Date endTime = dtSelect;
            endTime.setHours(18);
            endTime.setMinutes(0);
            Vector location = PstLocation.listAll();
            Vector val_location = new Vector();
            Vector key_location = new Vector();
            if (location != null && location.size() > 0) {
                for (int idxloc = 0; idxloc < location.size(); idxloc++) {
                    Location locationx = (Location) location.get(idxloc);
                    val_location.add("" + locationx.getOID());
                    key_location.add(locationx.getName());
                }
            }
             Vector allw_value = new Vector(1, 1);
        Vector allw_key = new Vector(1, 1);
        for (int il = 0; il < Overtime.allowanceType.length ; il++){
                allw_value.add(""+ Overtime.allowanceValue[il]);
                allw_key.add(Overtime.allowanceType[il] );
        }


            for (int idx = 0; idx < objectClass.size(); idx++) {
                Vector rowx = new Vector();
                Vector listx = (Vector) objectClass.get(idx);
                Employee employee = (Employee) listx.get(0);
                Overtime overtime = (Overtime) listx.get(1);
                OvertimeDetail overtimeDetail = (OvertimeDetail) listx.get(2);
                no = no + 1;
                 String inputHiddenMain = 
                        "<input type=\"hidden\" name=\"" + FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_OVERTIME_ID]+"_"+employee.getOID() + "\" value=\"" + overtime.getOID() + "\">";
                rowx.add("" + no+inputHiddenMain);
                rowx.add(employee.getEmployeeNum() + "<input type=\"hidden\" name=\""+FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_EMPLOYEE_ID]+"_"+employee.getOID() + "\" value=\"" + employee.getOID() + "\">");
                rowx.add(employee.getFullName());
                rowx.add(ControlDate.drawDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]+"_"+employee.getOID(), overtimeDetail.getDateFrom() != null ? overtimeDetail.getDateFrom() : startTime, "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]+"_"+employee.getOID(), (overtimeDetail.getDateFrom() != null) ? overtimeDetail.getDateFrom() : startTime, "formElemen", 24, 1, 0));

                rowx.add(ControlDate.drawDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]+"_"+employee.getOID(), overtimeDetail.getDateTo() != null ? overtimeDetail.getDateTo() : endTime, "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]+"_"+employee.getOID(), (overtimeDetail.getDateTo() != null) ? overtimeDetail.getDateTo() : endTime, "formElemen", 24, 1, 0));


                String dtRest = ControlDate.drawDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START]+"_"+employee.getOID(), overtimeDetail.getRestStart() != null ? overtimeDetail.getRestStart() : (overtimeDetail.getDateFrom() != null ? overtimeDetail.getDateFrom() : startTime), "formElemen", 1, -5);
                String tmRest = ControlDate.drawTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START]+"_"+employee.getOID(), overtimeDetail.getRestStart() != null ? overtimeDetail.getRestStart() : (overtimeDetail.getDateFrom() != null ? overtimeDetail.getDateFrom() : startTime), "formElemen", 24, 1, 0);

                rowx.add("<table><tr><td nowrap> start " + dtRest + "</td></tr><tr><td nowrap> " + tmRest + " for <input type=\"text\" name=\"" + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR] + "\" value=\"" + overtimeDetail.getRestTimeinHr() + "\" class=\"formElemen\" size=\"5\"> Hr </td></tr></table>");


                rowx.add("<input type=\"text\" name=\"" + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_JOBDESK]+"_"+employee.getOID() + "\" value=\"" + overtimeDetail.getJobDesk() + "\" class=\"formElemen\" size=\"25\">");
                rowx.add(ControlCombo.draw(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_LOCATION_ID]+"_"+employee.getOID(), null, "" + overtimeDetail.getLocationId(), val_location, key_location)); 
                rowx.add(ControlCombo.draw(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_PAID_BY]+"_"+employee.getOID(), null, "" + overtimeDetail.getPaidBy(), OvertimeDetail.getPaidByVal(), OvertimeDetail.getPaidByKey()));
                rowx.add(ControlCombo.draw(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_ALLOWANCE]+"_"+employee.getOID(), null, "" + overtime.getAllowence(), allw_value, allw_key, "")); 
                rowx.add(ControlCombo.draw(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_STATUS]+"_"+employee.getOID(), null, "" + overtimeDetail.getStatus(), val_status, key_status));
                
                rowx.add("<input type=\"checkbox\" name=\"userSelected\" value=\"" + employee.getOID()+"_"+overtimeDetail.getOID() + "\" class=\"formElemen\" size=\"10\">");

                rowx.add(overtimeDetail.getOID() == 0 ? "can't delete" : "<a href=\"Javascript:cmdDeletePerEmp('" + overtimeDetail.getOID() + "')\">Delete</a>");
                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idx);
            }
            ctrlist.drawListEndTableJsVer2(outJsp);
        }

        return result;
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String selectEmployee[] = request.getParameterValues("userSelect");
    int typeOffScheduleMapping = FRMQueryString.requestInt(request, "typeOffScheduleMapping");
    //long employeeId = FRMQueryString.requestLong(request, "employeeId");
    long lDtSelectedDate = FRMQueryString.requestLong(request, "selectedDateCheckBox");
    // long outletEmployeeId = FRMQueryString.requestLong(request, "selectedOutletMappingCheckBox");
    long oidExtraScheduleOrOidOvertime = FRMQueryString.requestLong(request, "oidExtraScheduleOrOidOvertime");
    int  flagFromDetail= FRMQueryString.requestInt(request, "flagFromDetail");
    //long oidOvertime = FRMQueryString.requestLong(request, "oidOvertime");
    String employeeId = "";
    if (selectEmployee != null && selectEmployee.length > 0) {
        for (int x = 0; x < selectEmployee.length; x++) {
            employeeId = employeeId + selectEmployee[x] + ",";
        }
        if (employeeId != null && employeeId.length() > 0) {
            employeeId = employeeId.substring(0, employeeId.length() - 1);
        }
    }
    long oidCompany = 0;//FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID]);
    long oidDivision = 0;//FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID]);
    long oidDepartment = 0;// FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID]);
    long oidSection = 0;//FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID]);
    String whereClauseEmp = "";
    /*if (employeeId != null && employeeId.length() > 0) { 
        whereClauseEmp = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")";
        Vector listEmployee = PstEmployee.listEmployee(0, 0, whereClauseEmp, "");
        if (listEmployee != null && listEmployee.size() > 0) {
            Employee emp = (Employee) listEmployee.get(0);
            oidCompany = emp.getCompanyId();
            oidDivision = emp.getDivisionId();
            oidDepartment = emp.getDepartmentId();
            oidSection = emp.getSectionId();
        }
    }*/

    if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
        oidCompany = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID]);
        oidDivision = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID]);
        oidDepartment = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID]);
        oidSection = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID]);
    } else {
        oidCompany = FRMQueryString.requestLong(request, FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_COMPANY_ID]);
        oidDivision = FRMQueryString.requestLong(request, FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_DIVISION_ID]);
        oidDepartment = FRMQueryString.requestLong(request, FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_DEPARTMENT_ID]);
        oidSection = FRMQueryString.requestLong(request, FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_SECTION_ID]);
    }

    if (employeeId != null && employeeId.length() > 0 && oidCompany == 0 && oidDivision == 0 && oidDepartment == 0) {
        whereClauseEmp = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")";
        Vector listEmployee = PstEmployee.listEmployee(0, 0, whereClauseEmp, "");
        if (listEmployee != null && listEmployee.size() > 0) {
            Employee emp = (Employee) listEmployee.get(0);
            oidCompany = emp.getCompanyId();
            oidDivision = emp.getDivisionId();
            oidDepartment = emp.getDepartmentId();
            oidSection = emp.getSectionId();
        }
    }

    Date dtSelected = lDtSelectedDate == 0 ? null : new Date(lDtSelectedDate);
    Vector listOvertime = new Vector();
    Vector listExtraScheduleOutlet = new Vector();

    CtrlExtraScheduleOutlet ctrlExtraScheduleOutlet = new CtrlExtraScheduleOutlet(request);
    FrmExtraScheduleOutlet frmExtraScheduleOutlet = ctrlExtraScheduleOutlet.getForm();
    CtrlExtraScheduleOutletDetail ctrlExtraScheduleOutletDetail = new CtrlExtraScheduleOutletDetail(request);
    FrmExtraScheduleOutletDetail frmExtraScheduleOutletDetail = ctrlExtraScheduleOutletDetail.getForm();
    
    int ErrExtraSchedule = Message.NONE;

    CtrlOvertime ctrlOvertime = new CtrlOvertime(request);
    FrmOvertime frmOvertime = ctrlOvertime.getForm();
    
    CtrlOvertimeDetail ctrlOvertimeDetail = new CtrlOvertimeDetail(request);
    FrmOvertimeDetail frmOvertimeDetail = ctrlOvertimeDetail.getForm();
    int ErrOvertimeSchedule = Message.NONE;
    
    ExtraScheduleOutlet extraScheduleOutlet = new ExtraScheduleOutlet();//ctrlExtraScheduleOutlet.getMappingOutlet();
    Overtime overtime = new Overtime();//ctrlOvertime.getOvertime();
    
    Hashtable hashCekOverlap = new Hashtable();
       String msg = "";
    if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
         ErrOvertimeSchedule = ctrlOvertime.action(iCommand, dtSelected, employeeId, flagFromDetail,oidExtraScheduleOrOidOvertime);
         overtime = ctrlOvertime.getOvertime();
        // ctrlOvertimeDetail.action(iCommand, 0, overtime.getOID(), request);
        if (employeeId != null && employeeId.length() > 0) {
            String whereClause = "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN(" + employeeId + ")";
            listOvertime = PstOvertimeDetail.listJointOvertimeMapping(0, 0, whereClause,  "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " ASC ",dtSelected,dtSelected);
            hashCekOverlap = ctrlExtraScheduleOutlet.getHashCekOverlap();
            msg = ctrlOvertime.getMessage();
        }
       
        hashCekOverlap = ctrlOvertime.getHashCekOverlap();
    } else if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM) {
        ErrExtraSchedule = ctrlExtraScheduleOutlet.action(iCommand, dtSelected, employeeId, flagFromDetail,oidExtraScheduleOrOidOvertime);
        extraScheduleOutlet = ctrlExtraScheduleOutlet.getMappingOutlet();
       // ctrlExtraScheduleOutletDetail.action(iCommand,extraScheduleOutlet.getOID());  
        if (employeeId != null && employeeId.length() > 0) {
            String whereClause = "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN(" + employeeId + ")";
            listExtraScheduleOutlet = PstExtraScheduleOutletDetail.listJointExtraSchedule(0, 0, whereClause, "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " ASC ", dtSelected, dtSelected); //buat querynya
            hashCekOverlap = ctrlExtraScheduleOutlet.getHashCekOverlap();
             msg = ctrlExtraScheduleOutlet.getMessage();
        }

    }
    
 
%>
<html>
    <head>
        <title>Harisma - Form Schedule Mapping</title>
         <%@ include file = "../../main/konfigurasi_jquery.jsp" %>    
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
            <link rel="stylesheet" href="../../styles/main.css" type="text/css">
                <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
                    <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
                    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
                    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
                    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
                    <SCRIPT language=JavaScript>
function getRequestTimeOt(){
    <%=ControlDatePopup.writeDateCaller("FRM_MAPPING_OUTLET",FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_REQUEST_OT])%>
}

function getApproveTimeOt(){
    <%=ControlDatePopup.writeDateCaller("FRM_MAPPING_OUTLET",FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_OT])%>
}

function getAckTimeOt(){
    <%=ControlDatePopup.writeDateCaller("FRM_MAPPING_OUTLET",FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_ACK_OT])%>
}
function cmdSaveOt(){
    getRequestTimeOt();
    getApproveTimeOt();
    getAckTimeOt()
    document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.SAVE%>";
    //document.frmovertime.prev_command.value="<--%=prevCommand--%>";
    document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
    document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
}

function cmdSaveExtra(){
                getRequestTimeOt();
                getApproveTimeOt();
                getAckTimeOt()
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.SAVE%>";
                //document.frmovertime.prev_command.value="<--%=prevCommand--%>";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
            }
                        
function changeRequester(){
              <% if((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)){ %>
                var reqID= document.frmovertime.<%=((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)
                                             ?"req_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]) %>.value;                                                                                                                                                                            
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]%>.value=reqID;
                //update by satrya 2013-04-30
                
               <% }%>
                getRequestTimeOt();
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.GOTO%>";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
            }
            
function changeRequesterEx(){
              <% if((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall() && extraScheduleOutlet.getEmpApprovall()!=0)){ %>
                var reqID= document.frmovertime.<%=((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall() && extraScheduleOutlet.getEmpApprovall()!=0)
                                             ?"req_id_x":frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT]) %>.value;                                                                                                                                                                            
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT]%>.value=reqID;
                //update by satrya 2013-04-30
                
               <% }%>
                getRequestTimeOt();
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.GOTO%>";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
            }

function changeApproval(){
              <% if((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)){ %>
                var appID= document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)
                                             ?"approval_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID]) %>.value;                                                                                                                                                                            
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID]%>.value=appID; 
                 
               <% }%>
                   getRequestTimeOt();
                getApproveTimeOt();
                   document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.GOTO%>";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
            }
            
function changeApprovalEx(){
              <% if((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall1() && extraScheduleOutlet.getEmpApprovall1()!=0)){ %>
                var appID= document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall1() && extraScheduleOutlet.getEmpApprovall1()!=0)
                                             ?"approval_id_x":frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT]) %>.value;                                                                                                                                                                            
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT]%>.value=appID; 
                 
               <% }%>
                   getRequestTimeOt();
                getApproveTimeOt();
                   document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.GOTO%>";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
            }

function changeAcknowlege(){
              <% if((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)){ %>
                var ackID= document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)
                                             ?"acknowlege_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID]) %>.value;                                                                                                                                                                            
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID]%>.value=ackID; 
                 
               <% }%>
                   getRequestTimeOt();
                getApproveTimeOt();
                getAckTimeOt();
                   document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.GOTO%>";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
            }
            
function changeAcknowlegeEx(){
              <% if((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall2() && extraScheduleOutlet.getEmpApprovall2()!=0)){ %>
                var ackID= document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall2() && extraScheduleOutlet.getEmpApprovall2()!=0)
                                             ?"acknowlege_id_x":frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT]) %>.value;                                                                                                                                                                            
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.<%=frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT]%>.value=ackID; 
                 
               <% }%>
                   getRequestTimeOt();
                getApproveTimeOt();
                getAckTimeOt();
                   document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.GOTO%>";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
            }
            
            

            function cmdDeletePerEmp(oidExtraScheduleOrOidOvertime){
                var x = confirm(" Are You Sure to Delete?");
                if(x){
                    document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.oidExtraScheduleOrOidOvertime.value=oidExtraScheduleOrOidOvertime;
                    document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.DELETE%>";
                    document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="form-schedule-mapping.jsp";
                    document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
                }
            }
            
            
                        function hideObjectForEmployee(){
                        }

                        function hideObjectForLockers(){
                        }

                        function hideObjectForCanteen(){
                        }

                        function hideObjectForClinic(){
                        }

                        function hideObjectForMasterdata(){
                        }
                        function SetAllCheckBoxes(FormName, FieldName, CheckValue)
                        {
                            if(!document.forms[FormName])
                                return;
                            var objCheckBoxes = document.forms[FormName].elements[FieldName];
   
                            if(!objCheckBoxes)
                                return;
                            var countCheckBoxes = objCheckBoxes.length;
    
                            if(!countCheckBoxes)
                                objCheckBoxes.checked = CheckValue;
                            else
                            // set the check value for all check boxes
                                for(var i = 0; i < countCheckBoxes; i++)
                                    objCheckBoxes[i].checked = CheckValue;
                            //alert(objCheckBoxes[i]);
                        }
                
                        function cmdTypeOfEmpSchedule(selectedDate,outletEmployeeId,typeOffSchedule,sourceValue,oidExtraScheduleOrOidOvertime){  
                            //alert("1");
                            window.open("about:blank",sourceValue,"status=no,toolbar=no,menubar=no,resizable=yes,scrollbars=yes,location=no");
                            //alert("Hi");
                            //popup.focus();
                            //popup.document.write("<p>This is 'myWindow'</p>");
                //wi            ndow.focus();
                            //alert("2");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.EDIT%>";
                            //alert("3");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.source.value=sourceValue;
                            //alert("4");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.selectedDateCheckBox.value=selectedDate;
                            //alert("5");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.userSelect.value=outletEmployeeId;
                            //alert("6");
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.typeOffScheduleMapping.value=typeOffSchedule;
                            
                                 document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.oidExtraScheduleOrOidOvertime.value=oidExtraScheduleOrOidOvertime;
                            
                           
                           
                            //alert("7");
                            //alert("2");
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="<%=approot%>/employee/outlet/form-schedule-mapping.jsp";
                //alert("8");                                        
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.target=sourceValue;
                            //alert("4");
                                        
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();                                                                                                    
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.target= "_selft";
                            
                            window.close();
                        }
                        function cmdTypeOfEmpScheduleOutletMapping(selectedDate,outletEmployeeId,typeOffSchedule,sourceValue){  
                            //alert("1");
                            window.open("about:blank",sourceValue,"status=no,toolbar=no,menubar=no,resizable=yes,scrollbars=yes,location=no");
                            //alert("Hi");
                            //popup.focus();
                            //popup.document.write("<p>This is 'myWindow'</p>");
                //wi            ndow.focus();
                            //alert("2");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value="<%=Command.EDIT%>";
                            //alert("3");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.source.value=sourceValue;
                            //alert("4");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.selectedDateCheckBox.value=selectedDate;
                            //alert("5");
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.userSelect.value=outletEmployeeId;
                            //alert("6");
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.typeOffScheduleMapping.value=typeOffSchedule;
                            //alert("7");
                            //alert("2");
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action="<%=approot%>/employee/outlet/outlet_mapping_edit.jsp";
                //alert("3");                                        
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.target=sourceValue;
                            //alert("4");
                                        
                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();                                                                                                    
                            document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.target= "_selft";
                            
                            window.close();
                        }

                    </SCRIPT>

                    </head> 
                    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >

                            <tr>
                                <td width="88%" valign="top" align="left">

                                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                                        <tr>
                                            <td width="100%">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="20">
                                                            <font color="#FF6600" face="Arial"><strong>
                                                                    Outlet Edit &gt;  Outlet Mapping Edit 
                                                                </strong></font>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                                            <tr>
                                                                                <td valign="top">
                                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">

                                                                                        <tr>
                                                                                            <td valign="top" width="100%">
                                                                                                <form name="<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>" action="" method="post">
                                                                                                    <input type="hidden" name="command" value=""></input>
                                                                                                    <input type="hidden" name="source" value=""></input>
                                                                                                    <input type="hidden" name="userSelect" value="<%=employeeId%>"></input>
                                                                                                    <input type="hidden" name="selectedDateCheckBox" value="<%=lDtSelectedDate%>"></input>
                                                                                                    <input type="hidden" name="typeOffScheduleMapping" value="<%=typeOffScheduleMapping%>"></input>
                                                                                                     <input type="hidden" name="oidExtraScheduleOrOidOvertime" value="<%=oidExtraScheduleOrOidOvertime%>"></input>
                                                                                                     <input type="hidden" name="<%=FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_FLAG_SAVE_EMPLOYEE]%>" value="<%=employeeId%>"></input>
                                                                                                     
                                                                                                    <table width="100%">
                                                                                                        <tr>
                                                                                                            <td>
                                                                                                                <table width="100%"  border="0"  cellpadding="2" height="26">
                                                                                                                    <tr>

                                                                                                                        <%-- TAB MENU --%>
                                                                                                                        <%-- active tab --%>
                                                                                                                        <%
                                                                                                                            String colorMappingOutlet = "bgcolor=\"#0066CC\"";
                                                                                                                            String colorOvertime = "bgcolor=\"#0066CC\"";
                                                                                                                            String colorExtraSchedule = "bgcolor=\"#0066CC\"";
                                                                                                                            if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                                colorOvertime = "bgcolor=\"#66CCFF\"";
                                                                                                                                colorMappingOutlet = "bgcolor=\"#0066CC\"";
                                                                                                                                colorExtraSchedule = "bgcolor=\"#0066CC\"";
                                                                                                                            } else if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM) {
                                                                                                                                colorExtraSchedule = "bgcolor=\"#66CCFF\"";
                                                                                                                                colorOvertime = "bgcolor=\"#0066CC\"";
                                                                                                                                colorMappingOutlet = "bgcolor=\"#0066CC\"";
                                                                                                                            } else {
                                                                                                                                colorMappingOutlet = "bgcolor=\"#66CCFF\"";
                                                                                                                                colorExtraSchedule = "bgcolor=\"#0066CC\"";
                                                                                                                                colorOvertime = "bgcolor=\"#0066CC\"";
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <td width="11%" nowrap <%=colorMappingOutlet%>>
                                                                                                                            <div align="center" class="tablink">
                                                                                                                                <!--<span class="tablink">Outlet Mapping</span>-->
                                                                                                                                <a href="javascript:cmdTypeOfEmpScheduleOutletMapping('<%=lDtSelectedDate%>','<%=employeeId%>','<%=0%>','formscheduleoutletmapping')" class="tablink">Outlet Mapping</a> 
                                                                                                                                <!--<a href="outlet_mapping_edit.jsp?typeOffScheduleMapping=0&userSelect=<//%=employeeId%>&selectedDateCheckBox=<//%=lDtSelectedDate%>" class="tablink">Mapping Extra Schedule</a>-->
                                                                                                                            </div>
                                                                                                                        </td>
                                                                                                                        <td width="11%" nowrap <%=colorExtraSchedule%>>
                                                                                                                            <div align="center"  class="tablink">
                                                                                                                                <%if (colorExtraSchedule.equalsIgnoreCase("bgcolor=\"#0066CC\"")) {%>
                                                                                                                                <a href="javascript:cmdTypeOfEmpSchedule('<%=lDtSelectedDate%>','<%=employeeId%>','<%=PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM%>','formscheduleextraschedule','<%=extraScheduleOutlet.getOID()%>')" class="tablink">Mapping Extra Schedule</a> 
                                                                                                                                <%} else {%>
                                                                                                                                <span class="tablink">Mapping Extra Schedule</span>
                                                                                                                                <%}%>


                                                                                                                                <!--<a href="form-schedule-mapping.jsp?typeOffScheduleMapping=2&userSelect=<//%=employeeId%>&selectedDateCheckBox=<//%=lDtSelectedDate%>" class="tablink">Mapping Extra Schedule</a>-->
                                                                                                                            </div>
                                                                                                                        </td>
                                                                                                                        <td width="11%" nowrap <%=colorOvertime%>>
                                                                                                                            <div align="center"  class="tablink">
                                                                                                                                <%if (colorOvertime.equalsIgnoreCase("bgcolor=\"#0066CC\"")) {%>
                                                                                                                                <a href="javascript:cmdTypeOfEmpSchedule('<%=lDtSelectedDate%>','<%=employeeId%>','<%=PstExtraScheduleOutletDetail.OVERTIME_FORM%>','formscheduleovertime','<%=overtime.getOID()%>')" class="tablink">Overtime</a> 
                                                                                                                                <%} else {%>
                                                                                                                                <span class="tablink">Overtime</span>
                                                                                                                                <%}%>

                                                                                                                                <!--<a href="form-schedule-mapping.jsp?typeOffScheduleMapping=1&userSelect='<//%=employeeId%>'&selectedDateCheckBox='<//%=lDtSelectedDate%>'" class="tablink">Overtime</a>-->
                                                                                                                            </div>
                                                                                                                        </td>
                                                                                                                        <%-- END TAB MENU --%>

                                                                                                                    </tr>
                                                                                                                </table>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <%if (msg != null && msg.length() > 0) {%>
                                                                                                        <tr>
                                                                                                            <td bgcolor="#FFFF00"><img src="<%=approot%>/images/info3.png"><b> Message : </b> <%=msg%> </td>
                                                                                                        </tr>
                                                                                                        <%}%>
                                                                                                        <table width="100%">
                                                                                                            <tr>
                                                                                                                <td colspan="4"><%=typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM ? " Overtime" : "Extra Schedule"%> Editor:</td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td width="8%">Req. Date</td>
                                                                                                                <td width="43%">: 
                                                                                                                    <%
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print(ControlDate.drawDate(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_DATE], overtime.getRequestDate() == null ? dtSelected : overtime.getRequestDate(), "formElemen", 1, -5));
                                                                                                                            out.print(ControlDate.drawTime(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_DATE], overtime.getRequestDate() == null ? dtSelected : overtime.getRequestDate(), "formElemen", 24, 1, 0));
                                                                                                                            out.print("*" + frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_REQ_DATE));
                                                                                                                        } else {
                                                                                                                            out.print(ControlDate.drawDate(frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_REQUEST_DATE_EXTRA_SCHEDULE], extraScheduleOutlet.getRequestDate() == null ? dtSelected : extraScheduleOutlet.getRequestDate(), "formElemen", 1, -5));
                                                                                                                            out.print(ControlDate.drawTime(frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_REQUEST_DATE_EXTRA_SCHEDULE], extraScheduleOutlet.getRequestDate() == null ? dtSelected : extraScheduleOutlet.getRequestDate(), "formElemen", 24, 1, 0));
                                                                                                                            out.print("*" + frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_REQUEST_DATE_EXTRA_SCHEDULE));
                                                                                                                        }

                                                                                                                    %>

                                                                                                                </td>

                                                                                                                <td width="8%">Number</td>
                                                                                                                <td width="41%">:

                                                                                                                    <%
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print((overtime.getOvertimeNum() == null || (overtime.getOvertimeNum() != null && overtime.getOvertimeNum().length() == 0) ? "Automatic" : overtime.getOvertimeNum()) + "<input type=\"hidden\" name=\"" + frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_OV_NUMBER] + "\" value=\"" + (overtime.getOvertimeNum()) + "\"/>");
                                                                                                                            out.print("*" + frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_OV_NUMBER));
                                                                                                                        } else {
                                                                                                                            out.print(extraScheduleOutlet.getNumberForm() == null ? "Automatic" : extraScheduleOutlet.getNumberForm() + "<input type=\"hidden\" name=\"" + frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_EXTRA_SCHEDULE_NUMBER] + "\" value=\"" + (extraScheduleOutlet.getNumberForm()) + "\"/>");
                                                                                                                            out.print("*" + frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_EXTRA_SCHEDULE_NUMBER));
                                                                                                                        }

                                                                                                                    %>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td>Company</td>
                                                                                                                <td>:
                                                                                                                    <%
                                                                                                                        Vector comp_value = new Vector(1, 1);
                                                                                                                        Vector comp_key = new Vector(1, 1);
                                                                                                                        comp_value.add("0");
                                                                                                                        comp_key.add("select ...");
                                                                                                                        String whereClauseComp = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "=" + oidCompany;
                                                                                                                        Vector listComp = PstCompany.list(0, 0, whereClauseComp, " COMPANY ");
                                                                                                                        for (int i = 0; i < listComp.size(); i++) {
                                                                                                                            Company comp = (Company) listComp.get(i);
                                                                                                                            comp_key.add(comp.getCompany());
                                                                                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                                                                                        }

                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print(ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + (overtime.getCompanyId() == 0 ? oidCompany : overtime.getCompanyId()), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_COMPANY_ID));
                                                                                                                        } else {
                                                                                                                            out.print(ControlCombo.draw(frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_COMPANY_ID], "formElemen", null, "" + (extraScheduleOutlet.getCompanyId() == 0 ? oidCompany : extraScheduleOutlet.getCompanyId()), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_COMPANY_ID));
                                                                                                                        }

                                                                                                                    %> 
                                                                                                                </td>

                                                                                                                <td width="8%">Status Doc.</td>
                                                                                                                <td width="41%">:
                                                                                                                    <%
                                                                                                                        Vector obj_status = new Vector(1, 1);
                                                                                                                        Vector val_status = new Vector(1, 1);
                                                                                                                        Vector key_status = new Vector(1, 1);

                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            if (overtime.getRequestId() != 0) {
                                                                                                                                overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                                                                                                                                val_status.clear();
                                                                                                                                key_status.clear();
                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);
                                                                                                                                if (overtime.getApprovalId() != 0) {
                                                                                                                                    overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);
                                                                                                                                    val_status.clear();
                                                                                                                                    key_status.clear();
                                                                                                                                    val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
                                                                                                                                    key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

                                                                                                                                    if (overtime.getAckId() != 0) {
                                                                                                                                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_PROCEED);
                                                                                                                                        val_status.clear();
                                                                                                                                        key_status.clear();
                                                                                                                                        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_PROCEED));
                                                                                                                                        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_PROCEED]);
                                                                                                                                        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CLOSED));
                                                                                                                                        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CLOSED]);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                // jika belum di sign oleh requester maka semua yg proses approval setelah itu akan di set 0 ( di reset )
                                                                                                                                overtime.setApprovalId(0);
                                                                                                                                overtime.setAckId(0);
                                                                                                                                if (overtime.getStatusDoc() != I_DocStatus.DOCUMENT_STATUS_CANCELLED) {
                                                                                                                                    overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_DRAFT);
                                                                                                                                }
                                                                                                                                val_status.clear();
                                                                                                                                key_status.clear();
                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);
                                                                                                                            }
                                                                                                                            out.println(ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_STATUS_DOC], null, "" + overtime.getStatusDoc(), val_status, key_status, "tabindex=\"4\"", "formElemen"));
                                                                                                                            out.print(frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_COMPANY_ID));
                                                                                                                        } else {
                                                                                                                            if (extraScheduleOutlet.getEmpApprovall() != 0) {
                                                                                                                                extraScheduleOutlet.setDocStsForm(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                                                                                                                                val_status.clear();
                                                                                                                                key_status.clear();
                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);
                                                                                                                                if (extraScheduleOutlet.getEmpApprovall1() != 0) {
                                                                                                                                    extraScheduleOutlet.setDocStsForm(I_DocStatus.DOCUMENT_STATUS_FINAL);
                                                                                                                                    val_status.clear();
                                                                                                                                    key_status.clear();
                                                                                                                                    val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
                                                                                                                                    key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

                                                                                                                                    if (extraScheduleOutlet.getEmpApprovall2() != 0) {
                                                                                                                                        extraScheduleOutlet.setDocStsForm(I_DocStatus.DOCUMENT_STATUS_PROCEED);
                                                                                                                                        val_status.clear();
                                                                                                                                        key_status.clear();
                                                                                                                                        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_PROCEED));
                                                                                                                                        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_PROCEED]);
                                                                                                                                        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CLOSED));
                                                                                                                                        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CLOSED]);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                // jika belum di sign oleh requester maka semua yg proses approval setelah itu akan di set 0 ( di reset )
                                                                                                                                extraScheduleOutlet.setEmpApprovall(0);
                                                                                                                                extraScheduleOutlet.setEmpApprovall1(0);
                                                                                                                                if (extraScheduleOutlet.getDocStsForm() != I_DocStatus.DOCUMENT_STATUS_CANCELLED) {
                                                                                                                                    extraScheduleOutlet.setDocStsForm(I_DocStatus.DOCUMENT_STATUS_DRAFT);
                                                                                                                                }
                                                                                                                                val_status.clear();
                                                                                                                                key_status.clear();
                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);
                                                                                                                            }
                                                                                                                            out.println(ControlCombo.draw(FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE], null, "" + extraScheduleOutlet.getDocStsForm(), val_status, key_status, "tabindex=\"4\"", "formElemen"));
                                                                                                                            out.print(frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE));
                                                                                                                        }


                                                                                                                    %>
                                                                                                                </td>
                                                                                                            </tr>

                                                                                                            <tr>
                                                                                                                <td><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                <td>:
                                                                                                                    <%
                                                                                                                        Vector div_value = new Vector(1, 1);
                                                                                                                        Vector div_key = new Vector(1, 1);
                                                                                                                        div_value.add("0");
                                                                                                                        div_key.add("select ...");
                                                                                                                        //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                        String strWhere = "";
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + (oidCompany == 0 ? overtime.getCompanyId() : oidCompany);
                                                                                                                        } else {
                                                                                                                            strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + (oidCompany == 0 ? extraScheduleOutlet.getCompanyId() : oidCompany);
                                                                                                                        }

                                                                                                                        Vector listDiv = PstDivision.list(0, 0, strWhere, PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                                                                                                        for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                            Division div = (Division) listDiv.get(i);
                                                                                                                            div_key.add(div.getDivision());
                                                                                                                            div_value.add(String.valueOf(div.getOID()));
                                                                                                                        }
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print(ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + (overtime.getDivisionId() == 0 ? oidDivision : overtime.getDivisionId()), div_value, div_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_DIVISION_ID));
                                                                                                                        } else {
                                                                                                                            out.print(ControlCombo.draw(frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_DIVISION_ID], "formElemen", null, "" + (extraScheduleOutlet.getDivisionId() == 0 ? oidDivision : extraScheduleOutlet.getDivisionId()), div_value, div_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_DIVISION_ID));
                                                                                                                        }
                                                                                                                    %>

                                                                                                                </td>

                                                                                                                <td width="8%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                                                <td width="41%">:
                                                                                                                    <%
                                                                                                                        Vector sec_value = new Vector(1, 1);
                                                                                                                        Vector sec_key = new Vector(1, 1);
                                                                                                                        sec_value.add("0");
                                                                                                                        sec_key.add("select ...");
                                                                                                                        //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                        String strWhereSec = "";
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + (oidDepartment == 0 ? overtime.getDepartmentId() : oidDepartment);
                                                                                                                        } else {
                                                                                                                            strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + (oidDepartment == 0 ? extraScheduleOutlet.getDepartmentId() : oidDepartment);
                                                                                                                        }

                                                                                                                        Vector listSect = PstSection.list(0, 0, strWhereSec, PstSection.fieldNames[PstSection.FLD_SECTION]);
                                                                                                                        for (int i = 0; i < listSect.size(); i++) {
                                                                                                                            Section section = (Section) listSect.get(i);
                                                                                                                            sec_key.add(section.getSection());
                                                                                                                            sec_value.add(String.valueOf(section.getOID()));
                                                                                                                        }
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print(ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID], "formElemen", null, "" + (overtime.getSectionId() == 0 ? oidSection : overtime.getSectionId()), sec_value, sec_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_SECTION_ID));
                                                                                                                        } else {
                                                                                                                            out.print(ControlCombo.draw(frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_SECTION_ID], "formElemen", null, "" + (extraScheduleOutlet.getSectionId() == 0 ? 0 : extraScheduleOutlet.getSectionId()), sec_value, sec_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_SECTION_ID));
                                                                                                                        }
                                                                                                                    %>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                <td>:
                                                                                                                    <%
                                                                                                                        Vector dept_value = new Vector(1, 1);
                                                                                                                        Vector dept_key = new Vector(1, 1);
                                                                                                                        dept_value.add("0");
                                                                                                                        dept_key.add("select ...");
                                                                                                                        //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                        String strWhereDepartement = "";
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            strWhereDepartement = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + (oidDivision == 0 ? overtime.getDivisionId() : oidDivision);
                                                                                                                        } else {
                                                                                                                            strWhereDepartement = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + (oidDivision == 0 ? extraScheduleOutlet.getDivisionId() : oidDivision);
                                                                                                                        }

                                                                                                                        Vector listDept = PstDepartment.list(0, 0, strWhereDepartement, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                                                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                                                                            Department department = (Department) listDept.get(i);
                                                                                                                            dept_key.add(department.getDepartment());
                                                                                                                            dept_value.add(String.valueOf(department.getOID()));
                                                                                                                        }
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print(ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + (overtime.getDepartmentId() == 0 ? oidDepartment : overtime.getDepartmentId()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_DEPARTMENT_ID));
                                                                                                                        } else {
                                                                                                                            out.print(ControlCombo.draw(frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_DEPARTMENT_ID], "formElemen", null, "" + (extraScheduleOutlet.getDepartmentId() == 0 ? oidDepartment : extraScheduleOutlet.getDepartmentId()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_DEPARTMENT_ID));
                                                                                                                        }
                                                                                                                    %>
                                                                                                                </td>
                                                                                                                <td>Cost Center</td>
                                                                                                                <td>:
                                                                                                                    <%
                                                                                                                        Vector deptCost_value = new Vector(1, 1);
                                                                                                                        Vector deptCost_key = new Vector(1, 1);
                                                                                                                        deptCost_value.add("0");
                                                                                                                        deptCost_key.add("select ...");
                                                                                                                        //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                        String strWhereDepartementCost = "";
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            strWhereDepartementCost = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + (oidDivision == 0 ? overtime.getDivisionId() : oidDivision);
                                                                                                                        } else {
                                                                                                                            strWhereDepartementCost = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + (oidDivision == 0 ? extraScheduleOutlet.getDivisionId() : oidDivision);
                                                                                                                        }

                                                                                                                        Vector listDeptCost = PstDepartment.list(0, 0, strWhereDepartementCost, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                                                                                        for (int i = 0; i < listDeptCost.size(); i++) {
                                                                                                                            Department department = (Department) listDeptCost.get(i);
                                                                                                                            deptCost_key.add(department.getDepartment());
                                                                                                                            deptCost_value.add(String.valueOf(department.getOID()));
                                                                                                                        }
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print(ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + (overtime.getCostDepartmentId() == 0 ? oidDepartment : overtime.getCostDepartmentId()), deptCost_value, deptCost_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_DEPARTMENT_ID));
                                                                                                                        } else {
                                                                                                                            out.print(ControlCombo.draw(frmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_DEPARTMENT_ID], "formElemen", null, "" + (extraScheduleOutlet.getCostCenterId() == 0 ? oidDepartment : extraScheduleOutlet.getCostCenterId()), deptCost_value, deptCost_key, "onChange=\"javascript:cmdUpdateDiv()\"") + "*");
                                                                                                                            out.print(frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_DEPARTMENT_ID));
                                                                                                                        }
                                                                                                                    %>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td><%=typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM ? " Overtime" : "Extra Schedule"%> Objective</td>
                                                                                                                <td>
                                                                                                                    <%
                                                                                                                        if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {
                                                                                                                            out.print("<textarea name=\"" + frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_OBJECTIVE] + "\" class=\"elemenForm\" cols=\"35\" rows=\"2\">" + overtime.getObjective() + "</textarea> *");
                                                                                                                            out.print(frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_OBJECTIVE));
                                                                                                                        } else {
                                                                                                                            out.print("<textarea name=\"" + FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_EXTRA_SCHEDULE_ADJECTIVE] + "\" class=\"elemenForm\" cols=\"35\" rows=\"2\">" + extraScheduleOutlet.getExtraScheduleObjctive() + "</textarea> *");
                                                                                                                            out.print(frmExtraScheduleOutlet.getErrorMsg(FrmExtraScheduleOutlet.FRM_EXTRA_SCHEDULE_ADJECTIVE));
                                                                                                                        }
                                                                                                                    %>
                                                                                                                </td>
                                                                                                                <td>&nbsp;</td>
                                                                                                                <td>&nbsp;</td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td colspan="4"><B>List Employee</B></td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <%if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM) {%>
                                                                                                                <td valign="top" colspan="4">
                                                                                                                    <%=drawListOvertimeForm(out, listOvertime,dtSelected)%>
                                                                                                                </td>
                                                                                                                <%} else if (typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM) {%>
                                                                                                                <td valign="top" colspan="4">
                                                                                                                    <%=drawListExtraScheduleForm(out, listExtraScheduleOutlet,dtSelected)%>
                                                                                                                </td>
                                                                                                                <%}%>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td colspan="4"></td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td colspan="4"></td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td colspan="4"></td>
                                                                                                            </tr>
                                                                                                            <tr>
                                                                                                                <td colspan="4"></td>
                                                                                                            </tr>
                                                                                                            
                                                                                                            <!-- approvall -->

                                                                                                            <tr>
                                                                                                                <td colspan="4" width="100%" height="20" align="center">
                                                                                                                    <table width="100%" cellspacing="1" cellpadding="1">
                                                                                                                        <tr>
                                                                                                                            <td width="5%" height="20" ></td>
                                                                                                                            <td width="30%" height="20" align="center">Requested by</td>
                                                                                                                            <td width="30%" height="20" align="center">Approved by</td>
                                                                                                                            <td width="30%" height="20" align="center">Final Approved by</td>
                                                                                                                            <td width="5%" height="20" ></td>
                                                                                                                        </tr>

                                                                                                                        <tr>
                                                                                                                            <td width="5%" height="20" ></td>
                                                                                                                            <td width="30%" height="20" align="center">
                                                                                                                                <%
														Vector req_value = new Vector(1, 1);
														Vector req_key = new Vector(1, 1);
														req_value.add("0");
														req_key.add("select ...");														
                Boolean changeOfReqList = true;
                Vector listReq = new Vector();
                if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){
                    listReq =leaveConfig.overtimeRequester(overtime.getOID());
                }else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){
                     listReq =leaveConfig.overtimeRequester(extraScheduleOutlet.getOID());
                }
                
                for (int i = 0; i < listReq.size(); i++) {
                        Employee req = (Employee) listReq.get(i);
                       
                       if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){
                           if(( req.getOID()==overtime.getRequestId())){
                            changeOfReqList=false; 
                           }
                           if(req.getOID()==emplx.getOID() || ( req.getOID()==overtime.getRequestId() ) ){
                                 req_key.add(req.getFullName());
                                 req_value.add(String.valueOf(req.getOID()));
                           }
                           
                       }else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){
                           if(( req.getOID()==overtime.getRequestId())){
                            changeOfReqList=false; 
                           }
                           if(req.getOID()==emplx.getOID() || ( req.getOID()==extraScheduleOutlet.getEmpApprovall() ) ){
                                 req_key.add(req.getFullName());
                                 req_value.add(String.valueOf(req.getOID()));
                           }
                           
                       } 
                       
                }
%>

<%if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){%>
    <%= ControlCombo.draw( ((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)?"req_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]),"formElemen", null, "" + overtime.getRequestId(), req_value, req_key, 
        ((emplx.getOID()!=overtime.getRequestId()  || (hashCekOverlap!=null&&hashCekOverlap.size()>0  && ignoreOverlap))?"disabled":"onChange=\"javascript:changeRequester()\"") )%>
<%}else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){%> 
    <%= ControlCombo.draw( ((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall() && extraScheduleOutlet.getEmpApprovall()!=0)?"req_id_x":frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT]),"formElemen", null, "" + extraScheduleOutlet.getEmpApprovall(), req_value, req_key, 
    ((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall()  || (hashCekOverlap!=null&&hashCekOverlap.size()>0  && ignoreOverlap))?"disabled":"onChange=\"javascript:changeRequesterEx()\"") )%>

<%}%>
<%if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){%>
                        <%if(((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0 || changeOfReqList))){
                            String dtFormat = overtime.getTimeReqOt()!=null?Formater.formatDate(overtime.getTimeReqOt(), "MMMM dd, yyyy"):"-";
                            out.println(overtime.getTimeReqOt()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");
                            out.println(ControlDatePopup.writeDateDisabled(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_REQUEST_OT],overtime.getTimeReqOt())); 
                        }else{%>  
                           ON  <%=ControlDatePopup.writeDate(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_REQUEST_OT],(overtime.getTimeReqOt()==null ? new Date() : overtime.getTimeReqOt()),"getRequestTimeOt()")%>
                        <%} 
                        if((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)){ %>
                             <input type="hidden" name ="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]%>" value="<%=overtime.getRequestId()%>">
                        <%}%>
                        
<%}else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){%>
                        <%if(((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall() && extraScheduleOutlet.getEmpApprovall()!=0 || changeOfReqList))){
                            String dtFormat = extraScheduleOutlet.getDtEmpApprovall()!=null?Formater.formatDate(extraScheduleOutlet.getDtEmpApprovall(), "MMMM dd, yyyy"):"-";
                            out.println(extraScheduleOutlet.getDtEmpApprovall()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; "); 
                            out.println(ControlDatePopup.writeDateDisabled(frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE],extraScheduleOutlet.getDtEmpApprovall()));
                        }else{%>   
                           ON  <%=ControlDatePopup.writeDate(frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE],(extraScheduleOutlet.getDtEmpApprovall()==null ? new Date() : extraScheduleOutlet.getDtEmpApprovall()),"getRequestTimeOt()")%>
                        <%} 
                        if((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall() && extraScheduleOutlet.getEmpApprovall()!=0)){ %>
                             <input type="hidden" name ="<%=frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE]%>" value="<%=extraScheduleOutlet.getEmpApprovall()%>">
                        <%}%>
<%}%>
                                                                                                                            </td>
                                                                                                                            <td width="30%" height="20" align="center">
                                                                                                                                
                    <% 
                        changeOfReqList = true;
                        Vector app_value = new Vector(1, 1);
                        Vector app_key = new Vector(1, 1);
                        app_value.add("0");
                        app_key.add("select ...");
                        Vector listApp =new Vector();
                         if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){
                            listApp =leaveConfig.overtimeApprover(overtime.getOID());
                        }else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){
                             listApp =leaveConfig.overtimeApprover(extraScheduleOutlet.getOID());
                        }													                                                                                                                        
                        
                        for (int i = 0; i < listApp.size(); i++) {
                                Employee app = (Employee) listApp.get(i);
                                
                              if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){
                                  if(( app.getOID()==overtime.getApprovalId() )){
                                   changeOfReqList=false; 
                                  }
                                  if(app.getOID()==emplx.getOID() || ( app.getOID()==overtime.getApprovalId() ) ){
                                     app_key.add(app.getFullName());
                                     app_value.add(String.valueOf(app.getOID()));
                                  }
                                  
                              }else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){
                                  if(( app.getOID()==extraScheduleOutlet.getEmpApprovall1() )){
                                   changeOfReqList=false; 
                                  }
                                  if(app.getOID()==emplx.getOID() || ( app.getOID()==extraScheduleOutlet.getEmpApprovall1() ) ){
                                     app_key.add(app.getFullName());
                                     app_value.add(String.valueOf(app.getOID()));
                                  }
                              }
                                
                        }
%>


<%if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){%>
    <%=ControlCombo.draw( (emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)?"approval_id_x": frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID], "formElemen", null, "" + overtime.getApprovalId(), app_value, app_key,
    ((emplx.getOID()!=overtime.getApprovalId() && overtime.getRequestId()==0  || (hashCekOverlap!=null&&hashCekOverlap.size()>0  && ignoreOverlap))?"disabled":" onChange=\"javascript:changeApproval()\" "))%>
<%}else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){%> 
    <%=ControlCombo.draw( (emplx.getOID()!=extraScheduleOutlet.getEmpApprovall1() && extraScheduleOutlet.getEmpApprovall1()!=0)?"approval_id_x": frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT], "formElemen", null, "" + extraScheduleOutlet.getEmpApprovall1(), app_value, app_key,
    ((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall1() && extraScheduleOutlet.getEmpApprovall()==0  || (hashCekOverlap!=null&&hashCekOverlap.size()>0  && ignoreOverlap))?"disabled":" onChange=\"javascript:changeApprovalEx()\" "))%>

<%}%>
<%if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){%>
                        <%if((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0 || changeOfReqList)){
                            String dtFormat = overtime.getTimeApproveOt()!=null ?Formater.formatDate(overtime.getTimeApproveOt(), "MMMM dd, yyyy"):"-";
                            out.println(overtime.getTimeApproveOt()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");  
                            out.println(ControlDatePopup.writeDateDisabled(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_OT],overtime.getTimeApproveOt()));
                        }else{ %>
                                ON <%=ControlDatePopup.writeDate(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_OT],(overtime.getTimeApproveOt()==null ? new Date() : overtime.getTimeApproveOt()), "getApproveTimeOt()")%>
                         <%}
                        if((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)){ %>
                               <input type="hidden" name ="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID]%>" value="<%=overtime.getApprovalId()%>">
                         <%}%>
                        
<%}else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){%>
                        <%if((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall1() && extraScheduleOutlet.getEmpApprovall1()!=0 || changeOfReqList)){
                            String dtFormat = extraScheduleOutlet.getDtEmpApprovall1()!=null ?Formater.formatDate(extraScheduleOutlet.getDtEmpApprovall1(), "MMMM dd, yyyy"):"-";
                            out.println(extraScheduleOutlet.getDtEmpApprovall1()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");  
                            out.println(ControlDatePopup.writeDateDisabled(FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE],extraScheduleOutlet.getDtEmpApprovall1()));
                        }else{ %>
                                ON <%=ControlDatePopup.writeDate(FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE],(extraScheduleOutlet.getDtEmpApprovall1()==null ? new Date() : extraScheduleOutlet.getDtEmpApprovall1()), "getApproveTimeOt()")%>
                         <%}
                        if((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall1() && extraScheduleOutlet.getEmpApprovall1()!=0)){ %> 
                               <input type="hidden" name ="<%=FrmExtraScheduleOutlet.fieldNames[FrmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE]%>" value="<%=extraScheduleOutlet.getEmpApprovall1()%>"> 
                         <%}%>
<%}%>


                                                                                        
                                                                                        
                                                                                        
                                                                                                                                                                                     
                        
                                                                                                                            </td>
                                                                                                                            <td width="30%" height="20" align="center">
<%
                Vector ack_value = new Vector(1, 1);
                Vector ack_key = new Vector(1, 1);
                ack_value.add("0");
                ack_key.add("select ...");														                                                                                                                
                changeOfReqList = true;
                Vector listAck =new Vector(); 
                if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){ 
                     listAck =leaveConfig.overtimeFinalApprover(overtime.getOID()) ;
                }else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){
                     listAck =leaveConfig.overtimeFinalApprover(extraScheduleOutlet.getOID()) ;
                }
                for (int i = 0; i < listAck.size(); i++) {
                        Employee ack = (Employee) listAck.get(i);
                        
                        if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){
                            if(( ack.getOID()==overtime.getAckId() )){
                              changeOfReqList=false; 
                            }
                            if(ack.getOID()==emplx.getOID() || ( ack.getOID()==overtime.getAckId() ) ){
                                ack_key.add(ack.getFullName());
                                ack_value.add(String.valueOf(ack.getOID()));
                            }   
                        }else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){
                            if(( ack.getOID()==extraScheduleOutlet.getEmpApprovall2())){
                              changeOfReqList=false; 
                            }
                            if(ack.getOID()==emplx.getOID() || ( ack.getOID()==extraScheduleOutlet.getEmpApprovall2() ) ){
                                ack_key.add(ack.getFullName());
                                ack_value.add(String.valueOf(ack.getOID()));
                            }
                        }
                        
                }
    %>
    
    
<%if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){%>
    <%= ControlCombo.draw( (emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)?"acknowlege_id_x": frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID], "formElemen", null, "" + overtime.getAckId(), ack_value, ack_key,  
    ((emplx.getOID()!=overtime.getAckId() && overtime.getApprovalId()==0)?"disabled":" onChange=\"javascript:changeAcknowlege()\" "))%>
<%}else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){%> 
    <%= ControlCombo.draw( (emplx.getOID()!=extraScheduleOutlet.getEmpApprovall2() && extraScheduleOutlet.getEmpApprovall2()!=0)?"acknowlege_id_x": frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT], "formElemen", null, "" + extraScheduleOutlet.getEmpApprovall2(), ack_value, ack_key,  
    ((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall2() && extraScheduleOutlet.getEmpApprovall1()==0)?"disabled":" onChange=\"javascript:changeAcknowlegeEx()\" "))%>
<%}%>




<%if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.OVERTIME_FORM){%>
                        <%if(((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0 || changeOfReqList ))){
                            String dtFormat = overtime.getTimeAckOt()!=null ?Formater.formatDate(overtime.getTimeAckOt(), "MMMM dd, yyyy"):"-";
                            out.println(overtime.getTimeAckOt()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");
                            out.println(ControlDatePopup.writeDateDisabled(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_ACK_OT],overtime.getTimeAckOt()));
                        }else{%> 
                            ON <%=ControlDatePopup.writeDate(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_ACK_OT],(overtime.getTimeAckOt()==null ? null : overtime.getTimeAckOt()), "getAckTimeOt()")%>
                        <%}
                        if((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)){ %>
                                <input type="hidden" name ="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID]%>" value="<%=overtime.getAckId()%>">
                        <%}%>
                        
<%}else if(typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM){%>
                        <%if(((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall2() && extraScheduleOutlet.getEmpApprovall2()!=0 || changeOfReqList ))){
                            String dtFormat = extraScheduleOutlet.getDtEmpApprovall2()!=null ?Formater.formatDate(extraScheduleOutlet.getDtEmpApprovall2(), "MMMM dd, yyyy"):"-";
                            out.println(extraScheduleOutlet.getDtEmpApprovall2()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");
                            out.println(ControlDatePopup.writeDateDisabled(frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE],extraScheduleOutlet.getDtEmpApprovall2()));
                        }else{%> 
                            ON <%=ControlDatePopup.writeDate(frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE],(extraScheduleOutlet.getDtEmpApprovall2()==null ? null : extraScheduleOutlet.getDtEmpApprovall2()), "getAckTimeOt()")%>
                        <%}
                        if((emplx.getOID()!=extraScheduleOutlet.getEmpApprovall2() && extraScheduleOutlet.getEmpApprovall2()!=0)){ %>
                                <input type="hidden" name ="<%=frmExtraScheduleOutlet.fieldNames[frmExtraScheduleOutlet.FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE]%>" value="<%=extraScheduleOutlet.getEmpApprovall2()%>">
                        <%}%>
<%}%>

                                                                                             
                          
                                                                                                                            </td>
                                                                                                                            <td width="5%" height="20" ></td>
                                                                                                                        </tr>

                                                                                                                    </table>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                            <!-- end approve -->
                                                                                                            <tr>
                                                                                                                <td colspan="4" width="100%">
                                                                                                                    <table width="100%">
                                                                                                                        <tr valign="top">
                                                                                                                            <td>
                                                                                                                                <table width="15%">
                                                                                                                                    <td width="5"></td>
                                                                                                                                    <td width="5"></td>
                                                                                                                                    <td width="5" nowrap class="command"></td>
                                                                                                                                </table>
                                                                                                                            </td>
                                                                                                                        </tr>
                                                                                                                        <tr valign="top">
                                                                                                                            <td>
                                                                                                                                <table width="15%">
                                                                                                                                    <td width="5"><a href="javascript:<%=typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM?" cmdSaveExtra()":" cmdSaveOt()"%>" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSave.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSaveOn.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                                                    <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                                                    <td width="5" nowrap class="command"><a href="javascript:<%=typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM?" cmdSaveExtra()":" cmdSaveOt()"%>">Save <%=typeOffScheduleMapping == PstExtraScheduleOutletDetail.EXTRA_SCHEDULE_FORM?" Extra Schedule":" Overtime"%></a></td>
                                                                                                                                </table>
                                                                                                                            </td>
                                                                                                                        </tr>
                                                                                                                        <tr valign="top">
                                                                                                                            <td>
                                                                                                                                <table width="15%">
                                                                                                                                    <td width="5"></td>
                                                                                                                                    <td width="5"></td>
                                                                                                                                    <td width="5" nowrap class="command"></td>
                                                                                                                                </table>
                                                                                                                            </td>
                                                                                                                        </tr>
                                                                                                                    </table>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                        </table>

                                                                                                    </table>    

                                                                                                </form>
                                                                                            </td>
                                                                                        </tr>

                                                                                        
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>

                                </td>
                            </tr>
                            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                            <tr>
                                <td valign="bottom">

                                    <%@include file="../../footer.jsp" %>
                                </td>

                            </tr>
                            <%} else {%>
                            <tr> 
                                <td colspan="2" height="20">
                                    <%@ include file = "../../main/footer.jsp" %>
                                </td>
                            </tr>
                            <%}%>
                        </table>

                        <script type="text/javascript">
                            $(document).ready(function () {
                                gridviewScroll();
                            });
                            <%
                                     int freesize = 3;

                            %>
                                     function gridviewScroll() {
                                         gridView1 = $('#GridView1').gridviewScroll({
                                                width: 1310,
                                                height: 500,
                                                railcolor: "##33AAFF",
                                                barcolor: "#CDCDCD",
                                                barhovercolor: "#606060",
                                                bgcolor: "##33AAFF",
                                                freezesize: <%=freesize%>,                     
                                                arrowsize: 30,
                                                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                                                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                                                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                                                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                                                headerrowcount: 1,
                                                railsize: 16,
                                                barsize: 15
                                         });
                                     }
                        </script>
                    </body>
                    </html>

