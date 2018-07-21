<%-- 
    Document   : user_activity_log_new
    Created on : Feb 12, 2016, 4:38:23 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.form.log.FrmLogSysHistory"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_SYSTEM_PROPERTIES); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String parsingData(String data){
        String str = "";
        str = data.replaceAll(";", "<br>");
        return str;
    }
    
    public Vector<String> parsingDataVersi2(String data){
        Vector<String> vdata = new Vector();
        for (String retval : data.split(";")) {
            vdata.add(retval);
        }
        return vdata;
    }
    
    public String getTableDetail(String field, String prev, String curr){
        String table = "";
        Vector vField = parsingDataVersi2(field);
        Vector vPrev = parsingDataVersi2(prev);
        Vector vCurr = parsingDataVersi2(curr);
        table = "<table class=\"tblStyle\">";
        table += "<tr>";
        table += "<td class=\"title_tbl\">Field</td><td class=\"title_tbl\">Previous</td><td class=\"title_tbl\">Current</td>";
        table += "</tr>";
        if (vField != null && vField.size()>0){
            for(int i=0; i<vField.size(); i++){
                String dataField = (String)vField.get(i);
                String dataPrev = (String)vPrev.get(i);
                String dataCurr = (String)vCurr.get(i);
                table += "<tr>";
                table += "<td><strong>"+dataField+"</strong></td>";
                table += "<td>"+dataPrev+"</td>";
                table += "<td>"+dataCurr+"</td>";
                table += "</tr>";
            }
        }
        table += "</table>";
        return table;
    }
    
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String dateFrom = FRMQueryString.requestString(request, "datefrom");
    String dateTo = FRMQueryString.requestString(request, "dateto");
    int cboxStatus = FRMQueryString.requestInt(request, "cbox_status");
    long userId = FRMQueryString.requestLong(request, "user_id");
    String modul = FRMQueryString.requestString(request, "modul");
    String[] statusChx = FRMQueryString.requestStringValues(request, "status_chx");
    Vector listLogSysHistory = new Vector();
    String whereClause = "";
    String orderBy = ""+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_UPDATE_DATE];
    
    String strUrl = "";
    strUrl  = "'0',";
    strUrl += "'0',";
    strUrl += "'0',";
    strUrl += "'0',";
    strUrl += "'"+FrmLogSysHistory.fieldNames[FrmLogSysHistory.FRM_FIELD_COMPANY_ID]+"',";
    strUrl += "'"+FrmLogSysHistory.fieldNames[FrmLogSysHistory.FRM_FIELD_DIVISION_ID]+"',";
    strUrl += "'"+FrmLogSysHistory.fieldNames[FrmLogSysHistory.FRM_FIELD_DEPARTMENT_ID]+"',";
    strUrl += "'"+FrmLogSysHistory.fieldNames[FrmLogSysHistory.FRM_FIELD_SECTION_ID]+"'";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Activity Log</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            .tr1 {background-color: #FFF;}
            .tr2 {background-color: #EEE;}
            #fullname {
                background-color: #E4F5C6; 
                color: #506629; padding: 8px 11px; 
                margin-left: 5px;
                font-weight: bold;
                border-radius: 3px;
                
            }
            #close {
                background-color: #F5CBD0; 
                color: #8F2F3A; 
                padding: 8px 11px; 
                margin-left: 5px;
                border-radius: 3px;
                cursor: pointer;
            }
            #modul {
                background-color: #474747; 
                color: #EEE; padding: 8px 11px; 
                margin-left: 5px;
                border-radius: 3px;
                
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            #style_add {
                font-weight: bold;
                color: #677A1F;
                font-size: 11px;
                background-color: #E8F5BA;
                padding: 3px 5px;
                border-radius: 3px;
            }
            #style_edit {
                font-weight: bold;
                color: #257865;
                font-size: 11px;
                background-color: #BAF5E7;
                padding: 3px 5px;
                border-radius: 3px;
            }
            #style_delete {
                font-weight: bold;
                color: #8F2F3A;
                font-size: 11px;
                background-color: #F5CBD0;
                padding: 3px 5px;
                border-radius: 3px;
            }
            #style_login {
                font-weight: bold;
                color: #B83916;
                font-size: 11px;
                background-color: #FFD5C9;
                padding: 3px 5px;
                border-radius: 3px;
            }
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE; font-size: 12px;}
            .header {
                
            }
            .content-main {
                padding: 5px 25px 25px 25px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            #info {
                background-color: #DDD;
                color:#474747;
                margin-top: 21px;
                padding: 12px 17px;
                border-radius: 3px;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                border-radius: 3px;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                border: solid #00a1ec 1px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
                border: 1px solid #007fba;
            }
            
            .btn1 {
                background-color: #EEE;
                border-radius: 3px;
                font-family: Arial;
                border-radius: 3px;
                color: #575757;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                border: solid #EEE 1px;
                text-decoration: none;
            }

            .btn1:hover {
                color: #474747;
                background-color: #DDD;
                text-decoration: none;
                border: 1px solid #DDD;
            }
            
            .btn-small {
                text-decoration: none;
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 18px 21px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #btn-confirm {
                padding: 3px; border: 1px solid #CF5353; 
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            td { font-size: 12px; }
            .caption {
                padding: 3px 0px 1px 0px;
                font-weight: bold;
            }
        </style>
        <script type="text/javascript">
            function cmdSearch(){
                //document.frm.command.value="<=Command.SEARCH%>";
                //document.frm.action="user_activity_log.jsp";
                //document.frm.submit();
                var strUrl = "";

                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_list").innerHTML = xmlhttp.responseText;
                    }
                };
                strUrl = "list_log_history.jsp";
                strUrl += "?company_id="+document.getElementById("company").value;
                strUrl += "&division_id="+document.getElementById("division").value;
                strUrl += "&department_id="+document.getElementById("department").value;
                strUrl += "&section_id="+document.getElementById("section").value;
                strUrl += "&start_date="+document.getElementById("start_date").value;
                strUrl += "&end_date="+document.getElementById("end_date").value;;
                strUrl += "&user_id="+document.getElementById("user_id").value;;
                strUrl += "&cb_status="+document.getElementById("cb_status").value;;
                strUrl += "&module="+document.getElementById("modul").innerHTML;;

                xmlhttp.open("GET", strUrl, true);
                xmlhttp.send();

            }
            
            function cmdBrowseUser(){
                newWindow=window.open("user_data.jsp","UserData", "height=600,width=500,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
            }
            function cmdBrowseModule(){
                newWindow=window.open("modul_data.jsp","ModulData", "height=600,width=500,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
            }
            function cmdApprove(){
                document.frm.command.value="<%=Command.APPROVE%>";
                document.frm.action="user_activity_log.jsp";
                document.frm.submit();
            }
            function cmdDelUser(){
                document.getElementById("user_id").value = "";
                document.getElementById("fullname").innerHTML = "...";
                document.getElementById("close-x").innerHTML = "";
            }
            
            function cmdDetail(oid){
                newWindow=window.open("more_detail.jsp?oid="+oid,"MoreDetail", "height=500,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
            }
        </script>
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        
<script type="text/javascript">

function loadCompany(
    pCompanyId, pDivisionId, pDepartmentId, pSectionId,
    frmCompany, frmDivision, frmDepartment, frmSection
) {
    var strUrl = "";
    if (pCompanyId.length == 0) { 
        document.getElementById("div_result").innerHTML = "";
        return;
    } else {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("div_result").innerHTML = xmlhttp.responseText;
            }
        };
        strUrl = "company_structure.jsp";
        strUrl += "?p_company_id="+pCompanyId;
        strUrl += "&p_division_id="+pDivisionId;
        strUrl += "&p_department_id="+pDepartmentId;
        strUrl += "&p_section_id="+pSectionId;
        
        strUrl += "&frm_company="+frmCompany;
        strUrl += "&frm_division="+frmDivision;
        strUrl += "&frm_department="+frmDepartment;
        strUrl += "&frm_section="+frmSection;
        xmlhttp.open("GET", strUrl, true);
        xmlhttp.send();
    }
}
    
function loadDivision(
    companyId, frmCompany, frmDivision, frmDepartment, frmSection
) {
    var strUrl = "";
    if (companyId.length == 0) { 
        document.getElementById("div_result").innerHTML = "";
        return;
    } else {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("div_result").innerHTML = xmlhttp.responseText;
            }
        };
        strUrl = "company_structure.jsp";

        strUrl += "?company_id="+companyId;

        strUrl += "&frm_company="+frmCompany;
        strUrl += "&frm_division="+frmDivision;
        strUrl += "&frm_department="+frmDepartment;
        strUrl += "&frm_section="+frmSection;
        xmlhttp.open("GET", strUrl, true);

        xmlhttp.send();
    }
}
    
function loadDepartment(
    companyId, divisionId, frmCompany, 
    frmDivision, frmDepartment, frmSection
) {
    var strUrl = "";
    if ((companyId.length == 0)&&(divisionId.length == 0)) { 
        document.getElementById("div_result").innerHTML = "";
        return;
    } else {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("div_result").innerHTML = xmlhttp.responseText;
            }
        };
        strUrl = "company_structure.jsp";
       
        strUrl += "?company_id="+companyId;
        strUrl += "&division_id="+divisionId;
        
        strUrl += "&frm_company="+frmCompany;
        strUrl += "&frm_division="+frmDivision;
        strUrl += "&frm_department="+frmDepartment;
        strUrl += "&frm_section="+frmSection;
        xmlhttp.open("GET", strUrl, true);
        xmlhttp.send();
    }
}
    
function loadSection(
    companyId, divisionId, departmentId,
    frmCompany, frmDivision, frmDepartment, frmSection
) {
    var strUrl = "";
    if ((companyId.length == 0)&&(divisionId.length == 0)&&(departmentId.length == 0)) { 
        document.getElementById("div_result").innerHTML = "";
        return;
    } else {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("div_result").innerHTML = xmlhttp.responseText;
            }
        };
        strUrl = "company_structure.jsp";
       
        strUrl += "?company_id="+companyId;
        strUrl += "&division_id="+divisionId;
        strUrl += "&department_id="+departmentId;
        
        strUrl += "&frm_company="+frmCompany;
        strUrl += "&frm_division="+frmDivision;
        strUrl += "&frm_department="+frmDepartment;
        strUrl += "&frm_section="+frmSection;
        xmlhttp.open("GET", strUrl, true);
        xmlhttp.send();
    }
}
</script>
        <script>
            function pageLoad(){ 
                $(".mydate").datepicker({ dateFormat: "yy-mm-dd" }); 
                loadCompany(<%=strUrl%>);
            } 
	</script>
    </head>
    <body onload="pageLoad()">
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%}%>
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title">System <strong style="color:#333;"> / </strong>User Activity Log</span>
        </div>

        <div class="content-main">
            <form name="frm" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <div style="padding: 11px; background-color: #FFF">
                <table width="100%">
                    <tr>
                        <!-- Left Side -->
                        <td width="50%" valign="top">
                            <table cellpadding="5" cellspacing="5">
                                <tr>
                                    <td><strong>Date from</strong></td>
                                    <td><input type="text" name="datefrom" id="start_date" class="mydate" value="<%=dateFrom%>" /> to <input type="text" name="dateto" id="end_date" class="mydate" value="<%=dateTo%>" /></td>
                                </tr>
                                <tr>
                                    <td><strong>User</strong></td>
                                    <td>
                                        <a href="javascript:cmdBrowseUser()" style="color:#474747" class="btn1">Pilih user</a>
                                        <span id="fullname">...</span>&nbsp;<span id="close-x"></span>
                                        <input type="hidden" name="user_id" id="user_id" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td><strong>Status</strong></td>
                                    <td>
                                        <%
                                            Vector val_status = new Vector(1,1);
                                            Vector key_status = new Vector(1,1);
                                            val_status.add(""+I_DocStatus.DOCUMENT_STATUS_DRAFT) ;//
                                            key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT] );//
                                            val_status.add(""+I_DocStatus.DOCUMENT_STATUS_CANCELLED);//
                                            key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED] );//
                                            val_status.add(""+I_DocStatus.DOCUMENT_STATUS_CLOSED);//
                                            key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CLOSED]);//
                                        %>
                                        <%=ControlCombo.draw("cbox_status", null, ""+cboxStatus, val_status, key_status, " id=\"cb_status\" ", "formElemen")%>
                                    </td>
                                </tr>
                                <tr>
                                    <td><strong>Module</strong></td>
                                    <td>
                                        <a href="javascript:cmdBrowseModule()" style="color:#474747" class="btn1">Pilih modul</a>
                                        <input type="hidden" name="modul" value="">
                                        <span id="modul"></span>
                                    </td>
                                </tr>
                                
                            </table>
                        </td>
                        <!-- Right Side -->
                        <td width="50%" valign="top">
                            <div id="div_result"></div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="padding: 12px;">
                            <a href="javascript:cmdSearch()" style="color:#FFF" class="btn">Search</a>
                        </td>
                    </tr>
                </table>
                </div>
                <div>&nbsp;</div>
                <div id="div_list"></div>
            </form>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>