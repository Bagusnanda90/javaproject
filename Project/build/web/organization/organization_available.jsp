<%-- 
    Document   : organization_available
    Created on : Apr 28, 2016, 3:06:54 PM
    Author     : Dimata 007
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<%!
    public String getStructureTemplateName(long oid){
        String name = "-";
        if (oid != 0){
            try {
                StructureTemplate tmpName = PstStructureTemplate.fetchExc(oid);
                name = tmpName.getTemplateName();
            } catch(Exception e){
                System.out.println("structure name=>"+e.toString());
            }
        }
        return name;
    }
%>
<%
    long selectStructure = FRMQueryString.requestLong(request, "select_structure");
    String structureName = getStructureTemplateName(selectStructure);
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
    /* Get Division from OrgMapDivision */
    whereClause = PstOrgMapDivision.fieldNames[PstOrgMapDivision.FLD_TEMPLATE_ID]+"="+selectStructure;
    Vector listOrgMapDivision = PstOrgMapDivision.list(0, 0, whereClause, "");
    if (selectStructure != 0){
        %>
        <table cellpadding="5" cellspacing="5">
            <tr>
                <td colspan="3" valign="top">
                    <div class="tips">
                        <table>
                            <tr>
                                <td style="padding-right: 9px"><img width="16" src="<%=approot%>/images/tips.png" /></td>
                                <td><strong>Tips:</strong> Anda bisa klik nama divisi, department, atau section yang tampil <br />untuk menampilkan struktur organisasi</td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3" valign="top">
                    <div id="note">
                        Struktur Organisasi <%=structureName%> 
                    </div>
                </td>
            </tr>
        </table>
        <table cellpadding="5" cellspacing="5">
            <tr>
                <td>
                    <%
                    if(divisionId != 0){
                    %>
                    <a href="javascript:cmdViewByDiv('<%=selectStructure%>','<%= divisionId %>')" style="color:#FFF; margin-top: 12px;" class="btn">
                        <%= structureModule.getDivisionName(divisionId) %>
                    </a>
                    <% } %>
                </td>
                <td>
                    <% if (departmentId != 0){ %>
                    <a href="javascript:cmdViewByDept('<%=selectStructure%>','<%= departmentId %>')" style="color:#FFF; margin-top: 12px;" class="btn">
                        <%= structureModule.getDepartmentName(departmentId) %>
                    </a>
                    <% } %>
                </td>
                <td>
                    <% if (sectionId != 0){ %>
                    <a href="javascript:cmdViewBySect('<%=selectStructure%>','<%= sectionId %>')" style="color:#FFF; margin-top: 12px;" class="btn">
                        <%= structureModule.getSectionName(sectionId) %>
                    </a>
                    <% } %>
                </td>
            </tr>
        </table>
        <table cellpadding="5" cellspacing="5" width="80%">
            <tr>
                <td valign="top" width="30%">
                    <div class="box">
                    <div class="caption">
                        <%=dictionaryD.getWord(I_Dictionary.DIVISION)%>
                    </div>

                        <table cellspacing="0" cellpadding="0" width="100%">
                            <%

                                Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                if (listOrgMapDivision != null && listOrgMapDivision.size()>0){
                                    for(int i=0; i<listOrgMapDivision.size(); i++){
                                        OrgMapDivision orgMapDiv = (OrgMapDivision)listOrgMapDivision.get(i);
                                        Division divisi = (Division)listDivision.get(i);
                                        if (divisionId == orgMapDiv.getDivisionId()){
                                            %>
                                            <tr><td><div class="box-item-active" onclick="javascript:loadDepartment('<%=selectStructure%>','<%=companyId%>', '<%=orgMapDiv.getDivisionId()%>')"><strong><%= structureModule.getDivisionName(orgMapDiv.getDivisionId()) %></strong></div></td></tr>
                                            <%
                                        } else {
                                            %>
                                            <tr><td><div class="box-item" onclick="javascript:loadDepartment('<%=selectStructure%>','<%=companyId%>', '<%=orgMapDiv.getDivisionId()%>')"><%=structureModule.getDivisionName(orgMapDiv.getDivisionId())%></div></td></tr>
                                            <%
                                        }
                                    }
                                }

                            %>
                        </table>

                    </div>
                </td>
                <td valign="top" width="30%">
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
                                            <tr>
                                                <td>
                                                    <div class="box-item-active" onclick="javascript:loadSection('<%=selectStructure%>','<%=companyId%>','<%=divisionId%>','<%=depart.getOID()%>')"><strong><%=depart.getDepartment()%></strong></div>
                                                </td>
                                            </tr>
                                            <%
                                        } else {
                                            %>
                                            <tr>
                                                <td>
                                                    <div class="box-item" onclick="javascript:loadSection('<%=selectStructure%>','<%=companyId%>','<%=divisionId%>','<%=depart.getOID()%>')"><%=depart.getDepartment()%></div>
                                                </td>
                                            </tr>
                                            <%
                                        }
                                    }
                                }
                            }
                            %>
                        </table>

                    </div>
                </td>
                <td valign="top" width="30%">
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
                                            <tr><td><div class="box-item-active" onclick="javascript:clickSection('<%=selectStructure%>','<%=companyId%>','<%=divisionId%>','<%=departmentId%>', '<%=section.getOID()%>')"><strong><%=section.getSection()%></strong></div></td></tr>
                                            <%
                                        } else {
                                            %>
                                            <tr><td><div class="box-item" onclick="javascript:clickSection('<%=selectStructure%>','<%=companyId%>','<%=divisionId%>','<%=departmentId%>', '<%=section.getOID()%>')"><%=section.getSection()%></div></td></tr>
                                            <%
                                        }
                                    }
                                }

                            }        
                            %>
                        </table>

                    </div>
                </td>
            </tr>
        </table>
        <%
    }
%>
