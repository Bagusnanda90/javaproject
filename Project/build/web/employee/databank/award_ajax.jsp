<%-- 
    Document   : award_ajax
    Created on : Feb 18, 2016, 10:06:22 AM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.harisma.form.employee.FrmEmpAward"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file = "../../main/javainit.jsp" %>

<%
// sysProp enable USE_DIV_ONLY
SystemProperty sysPropX = PstSystemProperty.fetchByName("USE_DIV_ONLY");

long oidAward = FRMQueryString.requestLong(request, "award_id");
long companyId = FRMQueryString.requestLong(request, FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_COMPANY_ID]);
long divisionId = FRMQueryString.requestLong(request, FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DIVISION_ID]);
long departmentId = FRMQueryString.requestLong(request, FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DEPARTMENT_ID]);
long sectionId = FRMQueryString.requestLong(request, FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_SECTION_ID]);

I_Dictionary dictionaryD = userSession.getUserDictionary();

if (oidAward != 0){
    EmpAward empAward = new EmpAward();
    try {
        empAward = PstEmpAward.fetchExc(oidAward);
        companyId = empAward.getCompanyId();
        divisionId = empAward.getDivisionId();
        departmentId = empAward.getDepartmentId();
        sectionId = empAward.getSectionId();
    } catch(Exception e){
        System.out.print(""+e.toString());
    }
}
%>

<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.COMPANY)%>
</div>
<div class="divinput">
    <select id="comp" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_COMPANY_ID]%>" onchange="javascript:loadDivision(this.value)">
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
    <select id="divi" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DIVISION_ID]%>" onchange="javascript:loadDepartment('<%=companyId%>', this.value)">
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
<%
    if(sysPropX.getValue().equals("0")){
%>
<div class="caption">
    Department on award<!--%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%-->
</div>
<div class="divinput">
    <select id="depart" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DEPARTMENT_ID]%>" onchange="javascript:loadSection('<%=companyId%>','<%=divisionId%>',this.value)">
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
    Section on award<!--%=dictionaryD.getWord(I_Dictionary.SECTION)%-->
</div>
<div class="divinput">
    <select id="section" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_SECTION_ID]%>">
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
    <%}%>
