<%-- 
    Document   : outlet_mapping_edit
    Created on : Mar 17, 2014, 11:45:03 AM
    Author     : Satrya Ramayu
--%>

<%@page import="com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutletDetail"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.attendance.mappingoutlet.MappingOutlet"%>
<%@page import="com.dimata.harisma.form.attendance.mappingoutlet.CtrlMappingOutlet"%>
<%@page import="com.dimata.harisma.form.attendance.mappingoutlet.FrmMappingOutlet"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.session.attendance.employeeoutlet.SessEmployeeOutlet"%>
<%@page import="com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.form.attendance.employeeoutlet.FrmEmployeeOutlet"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_OUTLET, AppObjInfo.OBJ_EMPLOYEE_OUTLET);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%!    public static final int OVERTIME_FORM = 1;
    public static final int EXTRA_SCHEDULE_FORM = 2;
%>
<%!    public String drawList(JspWriter outJsp, Vector objectClass, String approot, Date dtSelected, FrmMappingOutlet frmMappingOutlet) {
        String result = "";

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(3);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("No", "1%");
        ctrlist.addHeader("Status", "1%");
        ctrlist.addHeader("Employee Nr.", "1%");
        ctrlist.addHeader("Nama", "30%");
        ctrlist.addHeader("Outlet", "10%");
        ctrlist.addHeader("Kode Position", "10%");
        ctrlist.addHeader("Schedule", "10%");
        ctrlist.addHeader("Date", "1%");
        ctrlist.addHeader("Action<br> <a href=\"Javascript:SetAllCheckBoxes('FRM_MAPPING_OUTLET','userSelected', true)\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('FRM_MAPPING_OUTLET','userSelected', false)\">Deselect All</a>", "10%");
        ctrlist.reset();

        int no = 0;
        if (objectClass != null && objectClass.size() > 0) {
            Vector locationNew_value = new Vector(1, 1);
            Vector locationNew_key = new Vector(1, 1);
            Vector tooltipsLocation = new Vector(1, 1);
            Vector listLocationNew = PstLocation.list(0, 0, "", PstLocation.fieldNames[PstLocation.FLD_NAME]);
            if (listLocationNew != null && listLocationNew.size() > 0) {
                for (int r = 0; r < listLocationNew.size(); r++) {
                    Location location = (Location) listLocationNew.get(r);
                    tooltipsLocation.add(location.getName());
                    locationNew_value.add(String.valueOf(location.getOID()));
                    locationNew_key.add("" + location.getName());
                }
            }
            Vector positionNew_value = new Vector(1, 1);
            Vector positionNew_key = new Vector(1, 1);
            Vector tooltipsPosition = new Vector(1, 1);
            Vector listPositionNew = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);
            if (listPositionNew != null && listPositionNew.size() > 0) {
                for (int r = 0; r < listPositionNew.size(); r++) {
                    Position position = (Position) listPositionNew.get(r);
                    tooltipsPosition.add(position.getPosition());
                    positionNew_value.add(String.valueOf(position.getOID()));
                    positionNew_key.add("" + position.getKodePosition());
                }
            }
            Vector schedule_value = new Vector(1, 1);
            Vector schedule_key = new Vector(1, 1);
            Vector tooltips = new Vector(1, 1);
            Vector listSchedule = PstScheduleSymbol.listAll();
            if (listSchedule != null && listSchedule.size() > 0) {
                for (int r = 0; r < listSchedule.size(); r++) {
                    ScheduleSymbol schedulesymbol = (ScheduleSymbol) listSchedule.get(r);
                    schedule_key.add(schedulesymbol.getSymbol());
                    schedule_value.add("" + schedulesymbol.getOID());
                    String timeIn = schedulesymbol.getTimeIn() != null ? Formater.formatDate(schedulesymbol.getTimeIn(), "HH:mm") : "";
                    String timeOut = schedulesymbol.getTimeOut() != null ? Formater.formatDate(schedulesymbol.getTimeOut(), "HH:mm") : "";
                    tooltips.add("" + timeIn + "/" + timeOut);
                }
            }

            ctrlist.drawListHeaderWithJsVer2SelectTable(outJsp);//header
            //ctrlist.drawListHeaderWithJsVer2(outJsp);
            try {
                for (int x = 0; x < objectClass.size(); x++) {
                    //Vector rowx = new Vector(1,1);
                    ControlList ctControlList = new ControlList();
                    no = no + 1;

                    /*String tmpAction =
                     "<table width=\"15%\" cellspacing=\"0\" border=\"0\">"
                     +"<tr>"
                     +"<th width=\"3%\"><a href=\"#\"><img src=\""+approot+ "/images/buton-img/savedata.png\" border=\"0\"></a></th>" 
                     +"<th width=\"1%\"></th>"
                     +"<th width=\"3%\" style=\"font-size:12px\"><a href=\"#\">Save</a></th>" 
                     +"<th width=\"1%\">&nbsp;</th>";
                    
                     tmpAction = tmpAction+"<th width=\"3%\"><a href=\"#\"> <img src=\""+approot+"/images/buton-img/remove.png\"  border=\"0\"></a> </th>" 
                     +"<th width=\"1%\"></th>"
                     +"<th width=\"3%\" style=\"font-size:12px\"><a href=\"#\">Delete</a></th>"
                     +"</tr>"
                     +"</table>";*/
                    SessEmployeeOutlet sessEmployeeOutlet = (SessEmployeeOutlet) objectClass.get(x);
                    String status = sessEmployeeOutlet.getEmployeeOutletId() == 0 ? "Add" : "Update";
                    ctControlList.addColoms("" + no, "0", "0");
                    ctControlList.addColoms("" + status, "0", "0");
                    ctControlList.addColoms(sessEmployeeOutlet.getEmpNumber(), "0", "0");
                    ctControlList.addColoms(sessEmployeeOutlet.getFullName(), "0", "0");
                    String colors = "#FFFFF";
                    if (sessEmployeeOutlet.getLocationId() != 0) {
                        Location loc = new Location();
                        try {
                            loc = PstLocation.fetchExc(sessEmployeeOutlet.getLocationId());
                        } catch (Exception exc) {
                        }
                        colors = "#" + loc.getColorLocation();
                    }
                    String cbLocation = ControlCombo.drawTooltip(FrmMappingOutlet.fieldNames[FrmMappingOutlet.FRM_FIELD_LOCATION_ID] + "_" + sessEmployeeOutlet.getEmployeeId(), "select...", "" + (sessEmployeeOutlet.getLocationId() == 0 ? sessEmployeeOutlet.getLocationId() : sessEmployeeOutlet.getLocationId()), locationNew_value, locationNew_key, " onkeydown=\"javascript:fnTrapKD()\" ", tooltipsLocation);
                    ctControlList.addColoms(cbLocation + "*" + frmMappingOutlet.getCheckRequered("" + sessEmployeeOutlet.getEmployeeId() + "_location"), "0", "0");
                    String cbPosition = ControlCombo.drawTooltip(FrmMappingOutlet.fieldNames[FrmMappingOutlet.FRM_FIELD_POSITION_ID] + "_" + sessEmployeeOutlet.getEmployeeId(), "select...", "" + (sessEmployeeOutlet.getPositionId() == 0 ? sessEmployeeOutlet.getPositionIdFromEmp() : sessEmployeeOutlet.getPositionId()), positionNew_value, positionNew_key, " onkeydown=\"javascript:fnTrapKD()\" ", tooltipsPosition);
                    ctControlList.addColoms(cbPosition + "*" + frmMappingOutlet.getCheckRequered("" + sessEmployeeOutlet.getEmployeeId() + "_position"), "0", "0");
                    String cbSchedule = ControlCombo.drawTooltip(FrmMappingOutlet.fieldNames[FrmMappingOutlet.FRM_FIELD_SCHEDULE_SYMBOLE_ID] + "_" + sessEmployeeOutlet.getEmployeeId(), "select...", "" + sessEmployeeOutlet.getSchedleSymbolId(), schedule_value, schedule_key, " onkeydown=\"javascript:fnTrapKD()\" ", tooltips);
                    //if(sessEmployeeOutlet.getEmployeeOutletId()!=0){
                    cbSchedule = cbSchedule /*+ "<x title=\"cheked update to schedule\"><input type=\"checkbox\"  name=\"selectedSchedule\" value=\"" + (sessEmployeeOutlet.getEmployeeId()) + "\"></x>"*/;
                    ctControlList.addColoms(cbSchedule + "*" + frmMappingOutlet.getCheckRequered("" + sessEmployeeOutlet.getEmployeeId() + "_schedule"), "0", "0");
                    //}else{
                    //    ctControlList.addColoms("", "0", "0"); 
                    //}
// Code is modified by Hendra
                    String dateNameFrom = FrmMappingOutlet.fieldNames[FrmMappingOutlet.FRM_FIELD_DATE_FROM] + "_" + sessEmployeeOutlet.getEmployeeId();
                    String dateNameTo = FrmMappingOutlet.fieldNames[FrmMappingOutlet.FRM_FIELD_DATE_TO] + "_" + sessEmployeeOutlet.getEmployeeId();
                    String dtStart = ControlDate.drawDateWithStyle(dateNameFrom, sessEmployeeOutlet.getDtFrom() != null ? sessEmployeeOutlet.getDtFrom() : (dtSelected == null ? new Date() : dtSelected), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"");
                    String dtEnd = ControlDate.drawDateWithStyle(dateNameTo, sessEmployeeOutlet.getDtTo() != null ? sessEmployeeOutlet.getDtTo() : (dtSelected == null ? new Date() : dtSelected), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"");
                    String dtStartDP = "<input type=\"text\" class=\"datepick\" name=\"" + dateNameFrom + "\" id=\"dpick" + dateNameFrom + "\" />";
                    String dtEndDP = "<input type=\"text\" class=\"datepick\"  name=\"" + dateNameTo + "\"  id=\"dpick" + dateNameTo + "\" />";

                    String dateManual = "<div>" + dtStart + " to " + dtEnd + "*</div>"; // frmMappingOutlet.getCheckRequered("" + sessEmployeeOutlet.getEmployeeId() + "_dt"), "0", "0";
                    String datePicker = "<div>" + dtStartDP + " to " + dtEndDP + "</div>";
                    ctControlList.addColoms(dateManual + datePicker + frmMappingOutlet.getCheckRequered("" + sessEmployeeOutlet.getEmployeeId() + "_dt"), "0", "0");
                    ctControlList.addColoms("<input type=\"checkbox\"  name=\"userSelected\" value=\"" + (sessEmployeeOutlet.getEmployeeId() + "_" + sessEmployeeOutlet.getEmployeeOutletId()) + "\">", "0", "0");
// end
                    ctrlist.drawListRowJsVer2CoolspanRowsPan(outJsp, 0, ctControlList, x);
                }

            } catch (Exception exc) {
                System.out.println(exc);
            }

            result = "";
            ctrlist.drawListEndTableJsVer2(outJsp);
        } else {
            result = "<i>Belum ada data dalam sistem ...</i>";
        }
        return result;
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String selectEmployee[] = request.getParameterValues("userSelect");

    //long employeeId = FRMQueryString.requestLong(request, "employeeId");
    long lDtSelectedDate = FRMQueryString.requestLong(request, "selectedDateCheckBox");
    int typeOffScheduleMapping = FRMQueryString.requestInt(request, "typeOffScheduleMapping");
    long oidExtraScheduleOrOidOvertime = FRMQueryString.requestLong(request, "oidExtraScheduleOrOidOvertime");
    // long outletEmployeeId = FRMQueryString.requestLong(request, "selectedOutletMappingCheckBox");
    String employeeId = "";
    if (selectEmployee != null && selectEmployee.length > 0) {
        for (int x = 0; x < selectEmployee.length; x++) {
            employeeId = employeeId + selectEmployee[x] + ",";
        }
        if (employeeId != null && employeeId.length() > 0) {
            employeeId = employeeId.substring(0, employeeId.length() - 1);
        }
    }
    Date dtSelected = lDtSelectedDate == 0 ? null : new Date(lDtSelectedDate);
    String where = employeeId == null || employeeId.length() == 0 ? "" : "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN(" + employeeId + ")";
    String order = "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " ASC ";

    //String orders = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " ASC ";
    //String whereClause = employeeId==null || employeeId.length()==0?"":PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN ("+employeeId+")";
    //Hashtable hashObjMappingEmployeeIsNull = whereClause==null || whereClause.length()==0?new Hashtable():PstEmployee.listHashEmployee(0, 0, whereClause, orders); 
    CtrlMappingOutlet ctrlMappingOutlet = new CtrlMappingOutlet(request);
    FrmMappingOutlet frmMappingOutlet = ctrlMappingOutlet.getForm();
    Hashtable hashScheduleSymbol = new Hashtable();
    Hashtable hashSchedule = new Hashtable();
    if (iCommand == Command.SAVE && employeeId != null && employeeId.length() > 0) {
        Date dtPrevSelected = new Date((dtSelected != null ? dtSelected.getTime() : new Date().getTime()) - 1000 * 60 * 60 * 24);
        Date nextPrevSelected = new Date((dtSelected != null ? dtSelected.getTime() : new Date().getTime()) + 1000 * 60 * 60 * 24);
        Hashtable hashTblPeriod = PstPeriod.hashlistTblPeriod(0, 0, "", "");
        hashScheduleSymbol = PstScheduleSymbol.getHashTblScheduleVer2(0, 0, "", "");
        hashSchedule = PstEmpSchedule.getSchedule(dtPrevSelected, nextPrevSelected, employeeId, hashTblPeriod);
    }
    int iErrCode = ctrlMappingOutlet.action(iCommand, hashSchedule, hashScheduleSymbol);
    MappingOutlet obMappingOutlet = ctrlMappingOutlet.getMappingOutlet();
    String msg = ctrlMappingOutlet.getMessage();
    Vector listEmployeeMapping = PstEmployeeOutlet.listEmployeeOutletJoinEmployeeWithLocationFromTableEmployee(0, 0, where, order, dtSelected, dtSelected);
%>

<html>
    <head>
        <title>Harisma - Outlet Mapping</title>
        <%@ include file = "../../main/konfigurasi_jquery.jsp" %>    
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
            <link rel="stylesheet" href="../../styles/main.css" type="text/css">
                <link rel="stylesheet" href="../../styles/tab.css" type="text/css">

                    <link rel="stylesheet" href="../../javascripts/datepicker/themes/jquery.ui.all.css">
                        <script src="../../javascripts/datepicker/jquery.ui.core.js"></script>
                        <script src="../../javascripts/datepicker/jquery.ui.widget.js"></script>
                        <script src="../../javascripts/datepicker/jquery.ui.datepicker.js"></script>

                        <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>

                        <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
                        <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
                        <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />

                        <script type="text/javascript">

                            function changeHashOnLoad() {
                                window.location.href += "#";
                                setTimeout("changeHashAgain()", "50");
                            }

                            function changeHashAgain() {
                                window.location.href += "1";
                            }

                            var storedHash = window.location.hash;
                            window.setInterval(function() {
                                if (window.location.hash != storedHash) {
                                    window.location.hash = storedHash;
                                }
                            }, 50);

                            function cmdTypeOfEmpSchedule(selectedDate, outletEmployeeId, typeOffSchedule, sourceValue, oidExtraScheduleOrOidOvertime) {
                                //alert("1");
                                window.open("about:blank", sourceValue, "status=no,toolbar=no,menubar=no,resizable=yes,scrollbars=yes,location=no");
                                //alert("Hi");
                                //popup.focus();
                                //popup.document.write("<p>This is 'myWindow'</p>");
                                //window.focus();
                                //alert("2");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value = "<%=Command.EDIT%>";
                                //alert("3");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.source.value = sourceValue;
                                //alert("4");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.selectedDateCheckBox.value = selectedDate;
                                //alert("5");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.userSelect.value = outletEmployeeId;
                                //alert("6");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.typeOffScheduleMapping.value = typeOffSchedule;

                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.oidExtraScheduleOrOidOvertime.value = oidExtraScheduleOrOidOvertime;
                                //alert("7");
                                //alert("2");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action = "<%=approot%>/employee/outlet/form-schedule-mapping.jsp";
                                //alert("3");                            
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.target = sourceValue;
                                //alert("4");

                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.target = "_selft";

                                window.close();
                            }

                        </script>
                        <SCRIPT language=JavaScript>
                            function hideObjectForEmployee() {
                            }

                            function hideObjectForLockers() {
                            }

                            function hideObjectForCanteen() {
                            }

                            function hideObjectForClinic() {
                            }

                            function hideObjectForMasterdata() {
                            }
                            <%if (iErrCode == CtrlMappingOutlet.RSLT_OK) {%>
                            self.opener.cmdRefresh();
                            <%}%>
                            function SetAllCheckBoxes(FormName, FieldName, CheckValue)
                            {
                                if (!document.forms[FormName])
                                    return;
                                var objCheckBoxes = document.forms[FormName].elements[FieldName];
                                //alert(objCheckBoxes);
                                if (!objCheckBoxes)
                                    return;
                                var countCheckBoxes = objCheckBoxes.length;
                                if (!countCheckBoxes)
                                    objCheckBoxes.checked = CheckValue;
                                else
                                    // set the check value for all check boxes
                                    for (var i = 0; i < countCheckBoxes; i++)
                                        objCheckBoxes[i].checked = CheckValue;
                                //alert(objCheckBoxes[i]);
                            }
                            var chekTidakAdaDipilih = true;
                            function setSelected() {

                                if (!document.forms["<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>"])
                                    return;
                                if (!document.forms["<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>"].elements["userSelected"])
                                    return;
                                if (document.forms["<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>"].elements["userSelected"].length < 1)
                                    return;
                                var parentCheckboxes = document.forms["<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>"].elements["userSelected"];
                                chekTidakAdaDipilih = true;
                                for (count = 0; count < parentCheckboxes.length; count++) {
                                    if (parentCheckboxes[count].checked) {
                                        chekTidakAdaDipilih = false;
                                        return;
                                    }
                                }
                                if (chekTidakAdaDipilih) {
                                    //alert("Employee is not selected");
                                }

                            }
                            function cmdSave() {
                                //setSelected();
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value = "<%=Command.SAVE%>";
                                //alert("test1");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action = "outlet_mapping_edit.jsp";
                                //alert("test2");
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();
                                //alert("test3");

                            }

                            function cmdDelete() {
                                setSelected();
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.command.value = "<%=Command.DELETE%>";
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.action = "outlet_mapping_edit.jsp";
                                document.<%=FrmMappingOutlet.FRM_MAPPING_OUTLET%>.submit();

                            }
                            /*function setSelected(){
                     
                             if(!window.opener.document.forms["<//%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"])
                             return;
                             if(!window.opener.document.forms["<//%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"].elements["userSelect"])
                             return;
                             if(window.opener.document.forms["<//%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"].elements["userSelect"].length<1)
                             return;
                             var parentCheckboxes = window.opener.document.forms["<//%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"].elements["userSelect"];                            
                             for(count=0;count < parentCheckboxes.length;count++){
                             if(parentCheckboxes[count].checked){
                             alert(parentCheckboxes[count].value);
                             }
                             }
                             }*/


                            //setSelected();  
                        </SCRIPT>
                        <!-- 
                        Document : Change select option with Datepicker (JavaScript)
                        Author : Putu Hendra
                        -->
                        <script>                               
                                            function ganti(str) {

                                                var n = document.getElementById("dpick" + str).value;
                                                var bln = n.substr(0, 2);
                                                var tgl = n.substr(3, 2);
                                                var thn = n.substr(6, 4);

                                                var x = document.getElementById("m" + str).options;
                                                x[parseInt(bln)].selected = true;

                                                var y = document.getElementById("d" + str).options;
                                                y[parseInt(tgl)].selected = true;

                                                var z = document.getElementById("y" + str).options;
                                                var bnyak = document.getElementById("y" + str).length;

                                                for (var i = 0; i < bnyak; i++) {
                                                    if (z[i].text === thn) {
                                                        z[i].selected = true;
                                                    }
                                                }
                                            }

                                            $(function() {
                                                $('.datepick').each(function() {

                                                    $(this).datepicker(
                                                            {
                                                                onSelect: function() {
                                                                    var a = $(this).attr("name");
                                                                    //$("#testIn").val(a);
                                                                    ganti(a);
                                                                }
                                                            }
                                                    );

                                                });
                                            });
                        </script>  

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
                                                                                                        <table width="100%">
                                                                                                            <tr>
                                                                                                                <td>
                                                                                                                    <table width="100%"  border="0"  cellpadding="2" height="26">
                                                                                                                        <tr>
                                                                                                                            <%
                                                                                                                                String colorMappingOutlet = "bgcolor=\"#0066CC\"";
                                                                                                                                String colorOvertime = "bgcolor=\"#0066CC\"";
                                                                                                                                String colorExtraSchedule = "bgcolor=\"#0066CC\"";
                                                                                                                                if (typeOffScheduleMapping == OVERTIME_FORM) {
                                                                                                                                    colorOvertime = "bgcolor=\"#66CCFF\"";
                                                                                                                                    colorMappingOutlet = "bgcolor=\"#0066CC\"";
                                                                                                                                    colorExtraSchedule = "bgcolor=\"#0066CC\"";
                                                                                                                                } else if (typeOffScheduleMapping == EXTRA_SCHEDULE_FORM) {
                                                                                                                                    colorExtraSchedule = "bgcolor=\"#66CCFF\"";
                                                                                                                                    colorOvertime = "bgcolor=\"#0066CC\"";
                                                                                                                                    colorMappingOutlet = "bgcolor=\"#0066CC\"";
                                                                                                                                } else {
                                                                                                                                    colorMappingOutlet = "bgcolor=\"#66CCFF\"";
                                                                                                                                    colorExtraSchedule = "bgcolor=\"#0066CC\"";
                                                                                                                                    colorOvertime = "bgcolor=\"#0066CC\"";
                                                                                                                                }
                                                                                                                            %>
                                                                                                                            <%-- TAB MENU --%>
                                                                                                                            <%-- active tab --%>
                                                                                                                            <td width="11%" nowrap  <%=colorMappingOutlet%>>
                                                                                                                                <div align="center" class="tablink">
                                                                                                                                    <span class="tablink">Outlet Mapping</span>
                                                                                                                                </div>
                                                                                                                            </td>
                                                                                                                            <td width="11%" nowrap <%=colorExtraSchedule%>>
                                                                                                                                <div align="center"  class="tablink">
                                                                                                                                    <a href="javascript:cmdTypeOfEmpSchedule('<%=lDtSelectedDate%>','<%=employeeId%>','<%=EXTRA_SCHEDULE_FORM%>','formscheduleextraschedule','<%=oidExtraScheduleOrOidOvertime%>')" class="tablink">Mapping Extra Schedule</a>
                                                                                                                                    <!--<a href="form-schedule-mapping.jsp?typeOffScheduleMapping=2&userSelect=<%=employeeId%>&selectedDateCheckBox=<%=lDtSelectedDate%>" class="tablink">Mapping Extra Schedule</a>-->
                                                                                                                                </div>
                                                                                                                            </td>
                                                                                                                            <td width="11%" nowrap <%=colorOvertime%>>
                                                                                                                                <div align="center"  class="tablink">
                                                                                                                                    <a href="javascript:cmdTypeOfEmpSchedule('<%=lDtSelectedDate%>','<%=employeeId%>','<%=OVERTIME_FORM%>','formscheduleovertime','<%=oidExtraScheduleOrOidOvertime%>')" class="tablink">Overtime</a> 
                                                                                                                                    <!--<a href="form-schedule-mapping.jsp?typeOffScheduleMapping=1&userSelect=<%=employeeId%>&selectedDateCheckBox=<%=lDtSelectedDate%>" class="tablink">Overtime</a>-->
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
                                                                                                            <tr>
                                                                                                                <td>
                                                                                                                    <%=drawList(out, listEmployeeMapping, approot, dtSelected, frmMappingOutlet)%>		 
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                        </table>    




                                                                                                    </form>
                                                                                                </td>
                                                                                            </tr>
                                                                                            <tr valign="top">
                                                                                                <td colspan="2"><table width="15%">
                                                                                                        <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSave.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSaveOn.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                        <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="28" nowrap class="command"><a href="javascript:cmdSave()">Save</a></td>
                                                                                                        <td width="24"><a href="javascript:cmdDelete()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnDel.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnDelOn.jpg" width="24" height="24" alt="Delete"></a></td>
                                                                                                        <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="970" nowrap class="command"><a href="javascript:cmdDelete()">Delete</a></td>
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
                    $(document).ready(function() {
                        gridviewScroll();
                    });
                                <%
                                    int freesize = 4;

                                %>
                    function gridviewScroll() {
                        gridView1 = $('#GridView1').gridviewScroll({
                            width: 1300,
                            height: 300,
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



