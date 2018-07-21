<%-- 
    Document   : employee_search
    Created on : Feb 20, 2016, 12:26:14 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.session.employee.SessEmployeeView"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeePicture"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String parameterInput = FRMQueryString.requestString(request, "parameter_input");
    SessEmployeeView sessEmpView = new SessEmployeeView();
    String field = "";
    String value = "";
    
    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"=2018";
    Vector listEmployee = PstEmployee.list(0, 0, whereClause, "");
    String pictPath = "";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Employee</title>
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse;}
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                padding: 5px 7px 25px 7px;
                margin: 0px 21px 59px 21px;
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
            .box-info {
                margin:8px; 
                padding: 9px 12px;
                color: #32819C;
                background-color: #BEE1ED;
                border-radius: 3px;
            }
            .box-info-1 {
                margin-top: 5px;
                padding: 9px 12px;
                color: #575757;
                font-weight: bold;
                background-color: #DDD;
                border-radius: 3px;
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
                padding: 7px 17px 7px 17px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            .btn-small {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #676767; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small:hover { background-color: #474747; color: #FFF;}
            #inp {
                color: #575757;
                padding: 7px 7px;
                font-weight: bold;
            }
            select {
                padding: 5px 7px;
            }
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
            td { font-size: 12px; color: #474747; }
            .search-title {
                color:#575757;
                padding: 3px 11px;
                font-size: 18px;
                font-weight: bold;
                text-align: center;
            }
            .search-input {
                padding: 7px 11px;
                text-align: center;
            }
            .caption {
                font-size: 13px;
                color: #575757;
                font-weight: bold;
                padding: 9px 13px 9px 13px;
                border-bottom: 1px solid #DDD;
            }
            .box {
                margin: 0px 5px 5px 5px;
                background-color: #FFF;
                border-radius: 3px;
                border: 1px solid #DDD;
                color:#575757;
            }

            .box-item {
                color: #575757;
                padding: 7px 13px;
                font-size: 11px;
                font-weight: bold;
                background-color: #FFF;
                margin: 1px 0px;
                cursor: pointer;
            }
            .box-item:hover {
                color: #373737;
                background-color: #EEE;
            }
            .box-item-active {
                color: #F5F5F5;
                padding: 9px 13px;
                font-size: 11px;
                background-color: #007fba;
                margin: 1px 0px;
                cursor: pointer;
            }
            .emp-hover {
                background-color: #FFF;
            }
            .emp-hover:hover {
                background-color: #EEE;
            }
        </style>
        <script type="text/javascript">
            function search(){
                document.frm.action="employee_search.jsp";
                document.frm.submit();
            }
            
            function viewEmployeeDetail(oid){
                document.frm.employee_oid.value=oid;
		document.frm.command.value="<%=Command.EDIT%>";
                document.frm.target="_blank";
		document.frm.action="employee_edit.jsp";
		document.frm.submit();
            }
            
            function loadCompany(oid) {
                if (oid.length == 0) { 
                    document.getElementById("txtHint").innerHTML = "";
                    document.getElementById("list_emp").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_manage.jsp", true);
                    xmlhttp.send();
                    
                    var xmlhttp1 = new XMLHttpRequest();
                    xmlhttp1.onreadystatechange = function() {
                        if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
                            document.getElementById("list_emp").innerHTML = xmlhttp1.responseText;
                        }
                    };
                    xmlhttp1.open("GET", "list_view_ajax.jsp", true);
                    xmlhttp1.send();
                }
            }
            function loadDivision() {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "struct_manage.jsp", true);
                xmlhttp.send();
                
                
            }

            function loadDepartment(comp_id, divisi_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_manage.jsp?company_id="+comp_id+"&division_id=" + divisi_id, true);
                    xmlhttp.send();
                    
                    var xmlhttp1 = new XMLHttpRequest();
                    xmlhttp1.onreadystatechange = function() {
                        if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
                            document.getElementById("list_emp").innerHTML = xmlhttp1.responseText;
                        }
                    };
                    xmlhttp1.open("GET", "list_view_ajax.jsp?company_id="+comp_id+"&division_id=" + divisi_id, true);
                    xmlhttp1.send();
                }
            }

            function loadSection(comp_id, divisi_id, depart_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)&&(depart_id.length == 0)) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_manage.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id, true);
                    xmlhttp.send();
                    
                    var xmlhttp1 = new XMLHttpRequest();
                    xmlhttp1.onreadystatechange = function() {
                        if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
                            document.getElementById("list_emp").innerHTML = xmlhttp1.responseText;
                        }
                    };
                    xmlhttp1.open("GET", "list_view_ajax.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id, true);
                    xmlhttp1.send();
                }
            }
            
            function clickSection(comp_id, divisi_id, depart_id, sect_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)&&(depart_id.length == 0)&&(sect_id.length == 0)) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_manage.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id+"&section_id="+sect_id, true);
                    xmlhttp.send();
                    
                    var xmlhttp1 = new XMLHttpRequest();
                    xmlhttp1.onreadystatechange = function() {
                        if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
                            document.getElementById("list_emp").innerHTML = xmlhttp1.responseText;
                        }
                    };
                    xmlhttp1.open("GET", "list_view_ajax.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id+"&section_id="+sect_id, true);
                    xmlhttp1.send();
                }
            }
            
            function pageLoad(){ loadCompany('0'); }
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
            <span id="menu_title">Pencarian Karyawan Cepat</span>
        </div>
        <div class="content-main">
            <form name="frm" method="post" action="">
                <input type="hidden" name="command" value="" />
                <input type="hidden" name="employee_oid" value="" />
                
                <div class="search-title">Hairisma Searching</div>
                <div class="search-input">

                    <input type="text" id="inp" name="parameter_input" placeholder="type payroll or full name..." size="100" />&nbsp;
                    <a href="javascript:search()" class="btn" style="color:#FFF">Search</a>
                    
                </div>

            </form>
            <div>&nbsp;</div>
            <table border="0" width="100%">
                <tr>
                    <td width="45%" valign="top">
                        <div class="box-info" style="">
                            <strong>Filter by structure</strong>
                        </div>
                        <div id="txtHint"></div>
                    </td>
                    <td width="55%" valign="top">
                        
                        <div id="list_emp"></div>
                        <div>&nbsp;</div>
                        <table>
                            <tr>
                                <td style="font-weight: bold; padding: 9px 0px;">Total Data : 20</td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="pagging">
                                        <a style="color:#F5F5F5" href="javascript:cmdListFirst('0')" class="btn-small">First</a>
                                        <a style="color:#F5F5F5" href="javascript:cmdListPrev('0')" class="btn-small">Previous</a>
                                        <a style="color:#F5F5F5" href="javascript:cmdListNext('0')" class="btn-small">Next</a>
                                        <a style="color:#F5F5F5" href="javascript:cmdListLast('0')" class="btn-small">Last</a>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div>&nbsp;</div>
                    </td>
                </tr>
            </table>
            
            
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
