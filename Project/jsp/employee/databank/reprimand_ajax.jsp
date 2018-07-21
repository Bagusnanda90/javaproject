<%-- 
    Document   : reprimand_ajax
    Created on : Dec 14, 2015, 3:29:25 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<%
long oid = FRMQueryString.requestLong(request, "reprimand_id");
long companyId = FRMQueryString.requestLong(request, "company_id");
long divisionId = FRMQueryString.requestLong(request, "division_id");
long departmentId = FRMQueryString.requestLong(request, "department_id");
long sectionId = FRMQueryString.requestLong(request, "section_id");
long repLevelId = FRMQueryString.requestLong(request, "rep_level_id");

I_Dictionary dictionaryD = userSession.getUserDictionary();

if (oid != 0){
    EmpReprimand empReprimand = new EmpReprimand();
    try {
        empReprimand = PstEmpReprimand.fetchExc(oid);
        companyId = empReprimand.getCompanyId();
        divisionId = empReprimand.getDivisionId();
        departmentId = empReprimand.getDepartmentId();
        sectionId = empReprimand.getSectionId();
        repLevelId = empReprimand.getReprimandLevelId();
        
    } catch(Exception e){
        System.out.print(""+e.toString());
    }
}

%>
<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.COMPANY)%>
</div>
<div class="divinput">
    <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_COMPANY_ID]%>" onchange="javascript:loadDivision(this.value)">
        <option value="0">-select-</option>
        <%
        Vector listCompany = PstCompany.list(0, 0, "", PstCompany.fieldNames[PstCompany.FLD_COMPANY]);
        if (listCompany != null && listCompany.size()>0){
            for(int i=0; i<listCompany.size(); i++){
                Company comp = (Company)listCompany.get(i);
                if (companyId == comp.getOID()){
                    %>
                    <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                    <%
                } else {
                    %>
                    <option value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                    <%
                }
            }
        }
        %>
    </select>
</div>

<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.DIVISION)%>
</div>
<div class="divinput">
    <select name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_DIVISION_ID]%>" onchange="javascript:loadDepartment('<%=companyId%>', this.value)">
        <option value="0">-select-</option>
        <%
        if(companyId != 0){
            String whereDiv = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+companyId;
            Vector listDivision = PstDivision.list(0, 0, whereDiv, PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
            if (listDivision != null && listDivision.size()>0){
                for(int i=0; i<listDivision.size(); i++){
                    Division divisi = (Division)listDivision.get(i);
                    if (divisionId == divisi.getOID()){
                        %><option selected="selected" value="<%=divisi.getOID()%>"><%=divisi.getDivision()%></option><%
                    } else {
                        %><option value="<%=divisi.getOID()%>"><%=divisi.getDivision()%></option><%
                    }
                }
            }
        }
        %>
    </select>
</div>

<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%>
</div>
<div class="divinput">
    <select name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_DEPARTMENT_ID]%>" onchange="javascript:loadSection('<%=companyId%>','<%=divisionId%>',this.value)">
        <option value="0">-select-</option>
        <%
        if (divisionId != 0){
            Vector listDepart = PstDepartment.listDepartmentVer1(0, 0, String.valueOf(companyId) , String.valueOf(divisionId));
            if (listDepart != null && listDepart.size()>0){
                for(int i=0; i<listDepart.size(); i++){
                    Department depart = (Department)listDepart.get(i);
                    if (departmentId == depart.getOID()){
                        %><option selected="selected" value="<%=depart.getOID()%>"><%=depart.getDepartment()%></option><%
                    } else {
                        %><option value="<%=depart.getOID()%>"><%=depart.getDepartment()%></option><%
                    }
                }
            }
        }
        %>
    </select>
</div>

<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.SECTION)%>
</div>
<div class="divinput">
    <select name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_SECTION_ID]%>">
        <option value="0">-select-</option>
        <%
        if (departmentId != 0){
            String whereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+departmentId;
            Vector listSection = PstSection.list(0, 0, whereSection, PstSection.fieldNames[PstSection.FLD_SECTION]);

            if (listSection != null && listSection.size()>0){
                for(int i=0; i<listSection.size(); i++){
                    Section section = (Section)listSection.get(i);
                    if (sectionId == section.getOID()){
                        %><option selected="selected" value="<%=section.getOID()%>"><%=section.getSection()%></option><%
                    } else {
                        %><option value="<%=section.getOID()%>"><%=section.getSection()%></option><%
                    }
                }
            }

        }        
        %>
    </select>
</div>
<div class="caption">
    Reprimand Level (Point)
</div>
<div class="divinput">
    <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_LEVEL_ID]%>">
        <option value="0">-select-</option>
        <%
        Vector listLevel = PstReprimand.listAll();
        if (listLevel != null && listLevel.size()>0){
            for(int i=0; i<listLevel.size(); i++){
                Reprimand rep = (Reprimand) listLevel.get(i);
                if (repLevelId == rep.getOID()){
                    %>
                    <option selected="selected" value="<%=rep.getOID()%>"><%=rep.getReprimandDesc()%> (<%=rep.getReprimandPoint()%>)</option>
                    <%
                } else {
                    %>
                    <option value="<%=rep.getOID()%>"><%=rep.getReprimandDesc()%> (<%=rep.getReprimandPoint()%>)</option>
                    <%
                }
                
            }
        }
        %>
    </select>
</div>
