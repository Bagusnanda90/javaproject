<%-- 
    Document   : structure_preview
    Created on : Sep 17, 2015, 1:54:37 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.StructureModule"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
    long parentMain = FRMQueryString.requestLong(request, "parent_main");
    long templateID = FRMQueryString.requestLong(request, "template_id");
    int chkPhoto = FRMQueryString.requestInt(request, "chk_photo");
    int levelRank = FRMQueryString.requestInt(request, "level_rank");
    String approot = FRMQueryString.requestString(request, "approot");
    StructureModule structureModule = new StructureModule();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Structure Preview</title>
    </head>
    <style type="text/css">
        body {
            font-family: sans-serif;
        }
        a {text-decoration: none; color: #373737;}
        .tblStyle {border-collapse: collapse; background-color: #FFF;}
        .tblStyle td {font-size: 11px; color:#373737; text-align: center; border: 1px solid #C7C7C7; padding: 3px 5px;}
        
    </style>
    <body>
        <table class="tblStyle">

                <tr>
                    <td colspan="4">
                        LAMPIRAN KEPUTUSAN DIREKSI PT BANK PEMBANGUNAN DAERAH BALI NOMOR: 0016/KEP/DIR/RENSTRA/2014 TANGGAL 28 JANUARI 2014
                    </td>
                </tr>
            
                <tr>
                    <td>
                        Keputusan Nomor:<br />
                        0016/KEP/DIR/RENSTRA/2014
                    </td>
                    <td rowspan="2">LOGO</td>
                    <td rowspan="2">PT BANK PEMBANGUNAN DAERAH BALI BUKU PEDOMAN PERUSAHAAN SUSUNAN ORGANISASI DAN URAIAN TUGAS</td>
                    <td>Buku</td>
                </tr>
                <tr>
                    <td>Berlaku : 28 Januari 2014</td>
                    <td>Halaman</td>
                </tr>
                <tr>
                    <td colspan="4">Nama Bab : Susunan Organisasi Bank BPD Bali Revisi II - 2014</td>
                </tr>

        </table>
        <div>&nbsp;</div>
        <%
        /* mengisi nilai whereEmployee */
        String whereEmployee = "";
        if (companyId > 0 && divisionId == 0 && departmentId == 0 && sectionId == 0){
            whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
        }
        if (companyId > 0 && divisionId > 0 && departmentId == 0 && sectionId == 0){
            whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
        }
        if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId == 0){
            whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
            whereEmployee += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
        }
        if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId > 0){
            whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
            whereEmployee += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
            whereEmployee += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId;
        }
        structureModule.setWhereEmployee(whereEmployee);
        if (parentMain > 0){
            structureModule.setupEmployee(" AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+parentMain);
        }
        %>
        <table class="tblStyle">
            <tr>
                <td>
                    <% if (chkPhoto == 1){ %>
                    <div><img width="64" src="<%=approot%>/imgcache/employee-sample.jpg" style="padding:3px; background-color: #373737;" /></div>
                    <% } %>
                    <div style="color: #373737"><strong><%=structureModule.getEmployeeName()%></strong></div>
                    <div><%=structureModule.getPositionName(parentMain)%></div>
                    <%=structureModule.getDrawDownPosition(parentMain, templateID, whereEmployee, approot, chkPhoto, levelRank)%>
                </td>
            </tr>
        </table>
        
    </body>
</html>
