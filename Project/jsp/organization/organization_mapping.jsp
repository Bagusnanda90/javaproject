<%-- 
    Document   : organization_mapping
    Created on : Apr 25, 2016, 9:32:25 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.session.employee.SessEmployeeView"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_ORGANISASI, AppObjInfo.OBJ_MENU_STRUKTUR_ORGANISASI); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
    long templateId = FRMQueryString.requestLong(request, "template_id");
    long oidDelDivision = FRMQueryString.requestLong(request, "oid_del_division");
    long oidDelDepartment = FRMQueryString.requestLong(request, "oid_del_department");
    long oidDelSection= FRMQueryString.requestLong(request, "oid_del_section");
    String whereClause = "";
    SessEmployeeView sessEmpView = new SessEmployeeView();
    if (divisionId != 0){
        OrgMapDivision orgMapDiv = new OrgMapDivision();
        orgMapDiv.setDivisionId(divisionId);
        orgMapDiv.setTemplateId(templateId);
        try {
            PstOrgMapDivision.insertExc(orgMapDiv);
        } catch(Exception ex){
            System.out.print(ex.toString());
        }
        divisionId = 0;
        templateId = 0;
    }
    if (departmentId != 0){
        OrgMapDepartment orgMapDept = new OrgMapDepartment();
        orgMapDept.setDepartmentId(departmentId);
        orgMapDept.setTemplateId(templateId);
        try {
            PstOrgMapDepartment.insertExc(orgMapDept);
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
        departmentId = 0;
        templateId = 0;
    }
    if (sectionId != 0){
        OrgMapSection orgMapSect = new OrgMapSection();
        orgMapSect.setSectionId(sectionId);
        orgMapSect.setTemplateId(templateId);
        try {
            PstOrgMapSection.insertExc(orgMapSect);
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
        sectionId = 0;
        templateId = 0;
    }
    if (oidDelDivision != 0){
        try {
            PstOrgMapDivision.deleteExc(oidDelDivision);
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    if (oidDelDepartment != 0){
        try {
            PstOrgMapDepartment.deleteExc(oidDelDepartment);
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    if (oidDelSection != 0){
        try {
            PstOrgMapSection.deleteExc(oidDelSection);
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    Vector listOrganisasi = PstStructureTemplate.list(0, 0, "", "");
    Vector listOrgMapDiv = new Vector();
    Vector listOrgMapDept = new Vector();
    Vector listOrgMapSect = new Vector();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Organization Mapping</title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px; background-color: #FFF; }
            .tblStyle td {padding: 6px 9px; border: 1px solid #CCC; font-size: 12px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .tr1 {background-color: #FFF;}
            .tr2 {background-color: #EEE;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #font-info {
                padding: 2px 5px;
                font-size: 12px; 
                font-weight: bold;
            }
            #font-val {
                color : #007fba;
                padding: 2px 5px;
                font-size: 12px; 
                font-weight: bold;
            }
            #tbl_form td {
                font-size: 12px;
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; border-bottom: 1px solid #DDD;}
            #menu_sub {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #FFF; margin-bottom: 5px; } 
            #note {
                font-size: 13px;
                padding: 11px;
                background-color: #d3f0f9;
                color: #59aac1;
            }
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {
                background-color: #EEE;
            }
            .header {
                
            }
            .content-main {
                padding: 21px 32px;
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
                margin: 5px;
                padding: 14px 17px;
                background-color: #FFF;
                border-radius: 3px;
                border: 1px solid #DDD;
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
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
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
            
            .btn-small-1 {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #EEE; color: #575757; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-1:hover { background-color: #DDD; color: #474747;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            .caption {
                font-size: 13px;
                color: #575757;
                font-weight: bold;
                padding: 9px 13px 9px 13px;
                border-bottom: 1px solid #DDD;
            }
            #divinput {
                margin: 5px;
                padding-bottom: 5px;
                background-color: #DDD;
            }
                       
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            #record_count{
                font-size: 12px;
                font-weight: bold;
                padding-bottom: 9px;
            }
            #box-form {
                background-color: #EEE; 
                border-radius: 5px;
            }
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                text-align: left;
                border-bottom: 1px solid #DDD;
                background-color: #FFF;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                text-align: left;
                padding: 21px;
                background-color: #DDD;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                background-color: #FFF;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 13px 17px;
                background-color: #FF6666;
                color: #FFF;
                border-radius: 3px;
                font-size: 12px;
                font-weight: bold;
                visibility: hidden;
            }
            #btn-confirm {
                padding: 4px 9px; border-radius: 3px;
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px; 
            }
            
            #floatleft {
                font-size: 12px;
                color: #474747;
                padding: 7px 11px;
                background-color: #FFF;
                border-radius: 3px;
                margin: 5px 0px;
                cursor: pointer;
            }
            #floatleft:hover {
                color: #FFF;
                background-color: #474747;
            }
            #box-item {
                font-weight: bold;
                margin: 3px 0px;
                padding: 5px 9px;
                color: #575757;
                background-color: #EEE;
                border:1px solid #DDD;
                border-right: none;
            }
            #box-times {
                font-weight: bold;
                margin: 3px 0px;
                padding: 5px 9px;
                color: #B09595;
                background-color: #EEE;
                border:1px solid #DDD;
                cursor: pointer;
            }
            #box-times:hover {
                font-weight: bold;
                margin: 3px 0px;
                padding: 5px 9px;
                color: #B09595;
                background-color: #FFD9D9;
                border:1px solid #D9B8B8;
            }
        </style>
        <script type="text/javascript">
            function cmdOpenDivision(oidTemp){
                newWindow=window.open("dialog_division_check.jsp?oid_temp="+oidTemp,"DialogStruct", "height=500,width=500,status=yes,toolbar=no,menubar=no,scrollbars=yes");
                newWindow.focus();
            }
            function cmdOpenDepartment(oidTemp){
                newWindow=window.open("dialog_depart_check.jsp?oid_temp="+oidTemp,"DialogStruct", "height=500,width=500,status=yes,toolbar=no,menubar=no,scrollbars=yes");
                newWindow.focus();
            }
            function cmdOpenSection(oidTemp){
                newWindow=window.open("dialog_section_check.jsp?oid_temp="+oidTemp,"DialogStruct", "height=500,width=500,status=yes,toolbar=no,menubar=no,scrollbars=yes");
                newWindow.focus();
            }
            function deleteDiv(oid){
                document.frm.oid_del_division.value = oid;
                document.frm.action = "organization_mapping.jsp";
                document.frm.submit();
            }
            function deleteDept(oid){
                document.frm.oid_del_department.value = oid;
                document.frm.action = "organization_mapping.jsp";
                document.frm.submit();
            }
            function deleteSect(oid){
                document.frm.oid_del_section.value = oid;
                document.frm.action = "organization_mapping.jsp";
                document.frm.submit();
            }
        </script>
    </head>
    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../main/mnmain.jsp" %>
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
            <span id="menu_title">Organization Mapping</span>
        </div>
        <div class="content-main">
            <form name="frm" method="post" action="" >
                <input type="hidden" name="division_id" value="<%= divisionId %>" />
                <input type="hidden" name="department_id" value="<%= departmentId %>" />
                <input type="hidden" name="section_id" value="<%= sectionId %>" />
                <input type="hidden" name="template_id" value="<%= templateId %>" />
                <input type="hidden" name="oid_del_division" value="" />
                <input type="hidden" name="oid_del_department" value="" />
                <input type="hidden" name="oid_del_section" value="" />

                    
                <%
                if (listOrganisasi != null && listOrganisasi.size()>0){
                    for(int i=0; i<listOrganisasi.size(); i++){
                        StructureTemplate template = (StructureTemplate)listOrganisasi.get(i);
                        %>
                        <div class="box">
                            <div style="color:#575757; padding: 5px 0px 7px 0px; font-weight: bold;"><%= template.getTemplateName() %></div>
                            <a class="btn" style="color:#FFF;" href="javascript:cmdOpenDivision('<%= template.getOID() %>')">Mapping to Division</a>
                            <a class="btn" style="color:#FFF;" href="javascript:cmdOpenDepartment('<%= template.getOID() %>')">Mapping to Department</a>
                            <a class="btn" style="color:#FFF;" href="javascript:cmdOpenSection('<%= template.getOID() %>')">Mapping to Section</a>
                            <div>&nbsp;</div>
                            <table>
                                <tr>
                                    <td valign="top">
                                        <%
                                        whereClause = PstOrgMapDivision.fieldNames[PstOrgMapDivision.FLD_TEMPLATE_ID]+"="+template.getOID();
                                        listOrgMapDiv = PstOrgMapDivision.list(0, 0, whereClause, "");
                                        if (listOrgMapDiv != null && listOrgMapDiv.size()>0){
                                            for(int j=0; j<listOrgMapDiv.size(); j++){
                                                OrgMapDivision orgMDiv = (OrgMapDivision)listOrgMapDiv.get(j);
                                                %>
                                                <div>
                                                    <table style="margin:0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td><div id="box-item"><%= sessEmpView.getDivisionName(orgMDiv.getDivisionId()) %></div></td>
                                                            <td><div id="box-times" onclick="javascript:deleteDiv('<%= orgMDiv.getOID() %>')">&times;</div></td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <%
                                            }
                                        } else {
                                            %>
                                            <strong style="background-color: #EEE; padding: 5px; border-radius: 3px">There is no division data!</strong>
                                            <%
                                        }
                                        %>
                                        
                                    </td>
                                    <td valign="top">
                                        <%
                                        whereClause = PstOrgMapDepartment.fieldNames[PstOrgMapDepartment.FLD_TEMPLATE_ID]+"="+template.getOID();
                                        listOrgMapDept = PstOrgMapDepartment.list(0, 0, whereClause, "");
                                        if (listOrgMapDept != null && listOrgMapDept.size()>0){
                                            for(int j=0; j<listOrgMapDept.size(); j++){
                                                OrgMapDepartment orgMDept = (OrgMapDepartment)listOrgMapDept.get(j);
                                                %>
                                                <div>
                                                    <table style="margin:0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td><div id="box-item"><%= sessEmpView.getDepartmentName(orgMDept.getDepartmentId()) %></div></td>
                                                            <td><div id="box-times" onclick="javascript:deleteDept('<%= orgMDept.getOID() %>')">&times;</div></td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <%
                                            }
                                        } else {
                                            %>
                                            <strong style="background-color: #EEE; padding: 5px; border-radius: 3px">There is no department data!</strong>
                                            <%
                                        }
                                        %>
                                    </td>
                                    <td valign="top">
                                        <%
                                        whereClause = PstOrgMapSection.fieldNames[PstOrgMapSection.FLD_TEMPLATE_ID]+"="+template.getOID();
                                        listOrgMapSect = PstOrgMapSection.list(0, 0, whereClause, "");
                                        if (listOrgMapSect != null && listOrgMapSect.size()>0){
                                            for(int j=0; j<listOrgMapSect.size(); j++){
                                                OrgMapSection orgMSect = (OrgMapSection)listOrgMapSect.get(j);
                                                %>
                                                <div>
                                                    <table style="margin:0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td><div id="box-item"><%= sessEmpView.getSectionName(orgMSect.getSectionId()) %></div></td>
                                                            <td><div id="box-times" onclick="javascript:deleteSect('<%= orgMSect.getOID() %>')">&times;</div></td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <%
                                            }
                                        } else {
                                            %>
                                            <strong style="background-color: #EEE; padding: 5px; border-radius: 3px">There is no section data!</strong>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            
                        </div>
                        <%
                    }
                }
                %>

            </form>           
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>