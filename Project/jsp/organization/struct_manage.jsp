<%-- 
    Document   : struct_manage
    Created on : Apr 10, 2016, 3:44:29 PM
    Author     : Dimata 007
--%>
<%@ include file = "../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
StructureModule structureModule = new StructureModule();
long companyId = 0;
Vector listCompany = PstCompany.list(0, 0, "", "");
if (listCompany != null && listCompany.size()>0){
    Company comp = (Company)listCompany.get(0);
    companyId = comp.getOID();
}
long divisionId = FRMQueryString.requestLong(request, "division_id");
long departmentId = FRMQueryString.requestLong(request, "department_id");
long sectionId = FRMQueryString.requestLong(request, "section_id");
String whereClause = "";
I_Dictionary dictionaryD = userSession.getUserDictionary();
Vector listPosition = new Vector();
if (divisionId != 0 && departmentId == 0 && sectionId == 0){
    whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+divisionId;
    Vector listPosDiv = PstPositionDivision.list(0, 0, whereClause, "");
    if (listPosDiv != null && listPosDiv.size()>0){
        String where = "";
        for(int i=0; i<listPosDiv.size(); i++){
            PositionDivision posDiv = (PositionDivision)listPosDiv.get(i);
            where += posDiv.getPositionId()+",";
        }
        where += "0";
        whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" IN("+where+")";
        listPosition = PstPosition.list(0, 0, whereClause, PstPosition.fieldNames[PstPosition.FLD_POSITION]+"");
    }
}
if (divisionId !=0 && departmentId != 0 && sectionId == 0){
    whereClause = PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_DEPARTMENT_ID]+"="+departmentId;
    Vector listPosDept= PstPositionDepartment.list(0, 0, whereClause, "");
    if (listPosDept != null && listPosDept.size()>0){
        String where = "";
        for(int i=0; i<listPosDept.size(); i++){
            PositionDepartment posDept= (PositionDepartment)listPosDept.get(i);
            where += posDept.getPositionId()+",";
        }
        where += "0";
        whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" IN("+where+")";
        listPosition = PstPosition.list(0, 0, whereClause, PstPosition.fieldNames[PstPosition.FLD_POSITION]+"");
    }
}
if (divisionId !=0 && departmentId != 0 && sectionId != 0){
    whereClause = PstPositionSection.fieldNames[PstPositionSection.FLD_SECTION_ID]+"="+sectionId;
    Vector listPosSec = PstPositionSection.list(0, 0, whereClause, "");
    if (listPosSec != null && listPosSec.size()>0){
        String where = "";
        for(int i=0; i<listPosSec.size(); i++){
            PositionSection posSec = (PositionSection)listPosSec.get(i);
            where += posSec.getPositionId()+",";
        }
        where += "0";
        whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" IN("+where+")";
        listPosition = PstPosition.list(0, 0, whereClause, PstPosition.fieldNames[PstPosition.FLD_POSITION]+"");
    }
}
%>

<table border="0" cellpadding="2" cellspacing="2" width="100%">
    <tr>
        <td valign="top" width="32%">
            <div class="box">
            <div class="caption">
                <%=dictionaryD.getWord(I_Dictionary.DIVISION)%>
            </div>

                <table cellspacing="0" cellpadding="0" width="100%">
                    <%

                        Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                        if (listDivision != null && listDivision.size()>0){
                            for(int i=0; i<listDivision.size(); i++){
                                Division divisi = (Division)listDivision.get(i);
                                if (divisi.getValidStatus()!= 0){
                                    if (divisionId == divisi.getOID()){
                                        %>
                                        <tr><td><div class="box-item-active" onclick="javascript:loadDepartment('<%=companyId%>', '<%=divisi.getOID()%>')"><strong><%=divisi.getDivision()%></strong></div></td></tr>
                                        <%
                                    } else {
                                        %>
                                        <tr><td><div class="box-item" onclick="javascript:loadDepartment('<%=companyId%>', '<%=divisi.getOID()%>')"><%=divisi.getDivision()%></div></td></tr>
                                        <%
                                    }
                                }
                            }
                        }

                    %>
                </table>

            </div>
        </td>
        
        <td valign="top" width="28%">
            <div class="box">
            <div class="caption">
                <%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%>
            </div>

                <table cellspacing="0" cellpadding="0" width="100%">
                    <%
                    if (divisionId != 0){
                        Vector listDepart = PstDepartment.listDepartmentVer1(0, 0, String.valueOf(companyId) , String.valueOf(divisionId));
                        if (listDepart != null && listDepart.size()>0){
                            for(int i=0; i<listDepart.size(); i++){
                                Department depart = (Department)listDepart.get(i);
                                if (departmentId == depart.getOID()){
                                    %>
                                    <tr><td><div class="box-item-active" onclick="javascript:loadSection('<%=companyId%>','<%=divisionId%>','<%=depart.getOID()%>')"><strong><%=depart.getDepartment()%></strong></div></td></tr>
                                    <%
                                } else {
                                    %>
                                    <tr><td><div class="box-item" onclick="javascript:loadSection('<%=companyId%>','<%=divisionId%>','<%=depart.getOID()%>')"><%=depart.getDepartment()%></div></td></tr>
                                    <%
                                }
                            }
                        }
                    }
                    %>
                </table>

            </div>
            <div class="box">
            <div class="caption">
                <%=dictionaryD.getWord(I_Dictionary.SECTION)%>
            </div>

                <table cellspacing="0" cellpadding="0" width="100%">
                    <%
                    if (departmentId != 0){
                        String whereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+departmentId;
                        Vector listSection = PstSection.list(0, 0, whereSection, PstSection.fieldNames[PstSection.FLD_SECTION]);

                        if (listSection != null && listSection.size()>0){
                            for(int i=0; i<listSection.size(); i++){
                                Section section = (Section)listSection.get(i);
                                if (sectionId == section.getOID()){
                                    %>
                                    <tr><td><div class="box-item-active" onclick="javascript:clickSection('<%=companyId%>','<%=divisionId%>','<%=departmentId%>', '<%=section.getOID()%>')"><strong><%=section.getSection()%></strong></div></td></tr>
                                    <%
                                } else {
                                    %>
                                    <tr><td><div class="box-item" onclick="javascript:clickSection('<%=companyId%>','<%=divisionId%>','<%=departmentId%>', '<%=section.getOID()%>')"><%=section.getSection()%></div></td></tr>
                                    <%
                                }
                            }
                        }

                    }        
                    %>
                </table>

            </div>
        </td>
        <td valign="top" width="40%" style="padding: 4px 6px;">
            <div style="background-color: #FFF; padding: 9px; border:1px solid #DDD; border-radius: 3px;">
                <table>
                    <tr>
                        <td><div id="font-info"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></div></td>
                        <td><span id="font-val"><%= structureModule.getDivisionName(divisionId) %></span></td>
                    </tr>
                    <tr>
                        <td><div id="font-info"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></div></td>
                        <td><span id="font-val"><%= structureModule.getDepartmentName(departmentId) %></span></td>
                    </tr>
                    <tr>
                        <td><div id="font-info"><%=dictionaryD.getWord(I_Dictionary.SECTION)%></div></td>
                        <td><span id="font-val"><%= structureModule.getSectionName(sectionId) %></span></td>
                    </tr>
                    <tr>
                        <td><div id="font-info">Jumlah Position</div></td>
                        <td>
                            <span id="font-val"><%= listPosition.size() %></span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div>&nbsp;</div>
                            <a style="color:#FFF" href="javascript:cmdAdd()" class="btn">Add Position</a>
                            <div>&nbsp;</div>
                        </td>
                    </tr>
                </table>
            </div>
            <table width="100%" class="tblStyle" style="margin-top: 5px;">
                <tr>
                    <td class="title_tbl">No</td>
                    <td class="title_tbl">Position</td>
                    <td class="title_tbl">Description</td>
                    <td class="title_tbl">Action</td>
                </tr>
                <%
                if (listPosition != null && listPosition.size()>0){
                    String trCSS = "tr1";
                    for(int p=0; p<listPosition.size(); p++){
                        Position position = (Position)listPosition.get(p);
                        if (p % 2 == 0){
                            trCSS = "tr1";
                        } else {
                            trCSS = "tr2";
                        }
                        %>
                        <tr class="<%= trCSS %>">
                            <td><%= (p+1) %></td>
                            <td><%= position.getPosition() %></td>
                            <td><a href="javascript:cmdViewDesc('<%=position.getOID()%>')">View Description</a></td>
                            <td>Delete</td>
                        </tr>
                        <%
                    }
                } else {
                    %>
                    <tr>
                        <td colspan="4">There is no data available</td>
                    </tr>
                    <%
                }
                %>
                
            </table>
        </td>
    </tr>
</table>


