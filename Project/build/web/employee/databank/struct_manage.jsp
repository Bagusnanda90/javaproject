<%-- 
    Document   : struct_manage
    Created on : Apr 10, 2016, 3:44:29 PM
    Author     : Dimata 007
--%>
<%@ include file = "../../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

long companyId = 0;
Vector listCompany = PstCompany.list(0, 0, "", "");
if (listCompany != null && listCompany.size()>0){
    Company comp = (Company)listCompany.get(0);
    companyId = comp.getOID();
}
long divisionId = FRMQueryString.requestLong(request, "division_id");
long departmentId = FRMQueryString.requestLong(request, "department_id");
long sectionId = FRMQueryString.requestLong(request, "section_id");

I_Dictionary dictionaryD = userSession.getUserDictionary();


%>

<table>
    <tr>
        <td valign="top" width="50%">
            <div class="box">
            <div class="caption">
                <%=dictionaryD.getWord(I_Dictionary.DIVISION)%>
            </div>

                <table cellspacing="0" cellpadding="0">
                    <%

                        Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                        if (listDivision != null && listDivision.size()>0){
                            for(int i=0; i<listDivision.size(); i++){
                                Division divisi = (Division)listDivision.get(i);
                                if (divisi.getValidStatus() != 0){
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
        
        <td valign="top" width="50%">
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
                                    <tr><td><div class="box-item-active" onclick="javascript:clickSection('<%=companyId%>','<%=divisionId%>','<%=departmentId%>','<%=section.getOID()%>')"><strong><%=section.getSection()%></strong></div></td></tr>
                                    <%
                                } else {
                                    %>
                                    <tr><td><div class="box-item" onclick="javascript:clickSection('<%=companyId%>','<%=divisionId%>','<%=departmentId%>','<%=section.getOID()%>')"><%=section.getSection()%></div></td></tr>
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