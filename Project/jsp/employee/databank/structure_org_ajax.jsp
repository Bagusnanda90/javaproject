<%-- 
    Document   : structure_org_ajax
    Created on : Dec 7, 2015, 3:10:23 PM
    Author     : Hendra McHen
--%>

<%@page import="com.dimata.harisma.form.employee.FrmCareerPath"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<%
long oidCareerPath = FRMQueryString.requestLong(request, "career_path_oid");
long companyId = FRMQueryString.requestLong(request, "comp_id");
long divisionId = FRMQueryString.requestLong(request, "divisi_id");
long departmentId = FRMQueryString.requestLong(request, "department_id");

I_Dictionary dictionaryD = userSession.getUserDictionary();
String strDivisi = "";
String strDepartment = "";
String strSection = "";
CareerPath careerPath = new CareerPath();
if (oidCareerPath != 0){
    try {
        careerPath = PstCareerPath.fetchExc(oidCareerPath);
    } catch(Exception e){
        System.out.println(""+e.toString());
    }
}

if (companyId != 0){
    String whereDivision = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+companyId;
    Vector listDivision = PstDivision.list(0, 0, whereDivision, PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
    strDivisi  = "<select name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID]+"\" onChange=\"javascript:loadDepartment('"+companyId+"',this.value)\">";
    strDivisi += "<option value=\"0\">-SELECT-</option>";
    if (listDivision != null && listDivision.size()>0){
        for(int i=0; i<listDivision.size(); i++){
            Division divisi = (Division)listDivision.get(i);
            if (divisionId == divisi.getOID()){
                strDivisi += "<option selected=\"selected\" value=\""+divisi.getOID()+"\">"+divisi.getDivision()+"</option>";
            } else {
                strDivisi += "<option value=\""+divisi.getOID()+"\">"+divisi.getDivision()+"</option>";
            }
        }
    }
    strDivisi += "</select>";
} else {
    strDivisi  = "<select name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID]+"\" onChange=\"javascript:loadDepartment('"+companyId+"',this.value)\">";
    strDivisi += "<option value=\"0\">-SELECT-</option>";
    strDivisi += "</select>";
}

if (divisionId != 0){
    String whereDepart = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+divisionId;
    Vector listDepart = PstDepartment.listDepartmentVer1(0, 0, String.valueOf(companyId) , String.valueOf(divisionId));
    strDepartment  = "<select name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]+"\" onchange=\"javascript:loadSection('"+companyId+"','"+divisionId+"',this.value)\">";
    strDepartment += "<option value=\"0\">-SELECT-</option>";
    if (listDepart != null && listDepart.size()>0){
        for(int i=0; i<listDepart.size(); i++){
            Department depart = (Department)listDepart.get(i);
            if (departmentId == depart.getOID()){
                strDepartment += "<option selected=\"selected\" value=\""+depart.getOID()+"\">"+depart.getDepartment()+"</option>";
            } else {
                strDepartment += "<option value=\""+depart.getOID()+"\">"+depart.getDepartment()+"</option>";
            }
        }
    }
    strDepartment += "</select>";
} else {
    strDepartment  = "<select name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]+"\" onchange=\"javascript:loadSection('"+companyId+"','"+divisionId+"',this.value)\">";
    strDepartment += "<option value=\"0\">-SELECT-</option>";
    strDepartment += "</select>";
}

if (departmentId != 0){
    String whereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+departmentId;
    Vector listSection = PstSection.list(0, 0, whereSection, PstSection.fieldNames[PstSection.FLD_SECTION]);
    strSection  = "<select name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID]+"\">";
    strSection += "<option value=\"0\">-SELECT-</option>";
    if (listSection != null && listSection.size()>0){
        for(int i=0; i<listSection.size(); i++){
            Section section = (Section)listSection.get(i);
            if (careerPath.getSectionId() == section.getOID()){
                strSection += "<option selected=\"selected\" value=\""+section.getOID()+"\">"+section.getSection()+"</option>";
            } else {
                strSection += "<option value=\""+section.getOID()+"\">"+section.getSection()+"</option>";
            }
        }
    }
    strSection += "</select>";
} else {
    strSection  = "<select name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID]+"\">";
    strSection += "<option value=\"0\">-SELECT-</option>";
    strSection += "</select>";
}

%>
<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.DIVISION)%>
</div>
<div class="divinput">
    <%=strDivisi%> <span id="req">*Required</span>
</div>

<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%>
</div>
<div class="divinput">
    <%=strDepartment%> <span id="req">*Required</span>
</div>

<div class="caption">
    <%=dictionaryD.getWord(I_Dictionary.SECTION)%>
</div>
<div class="divinput">
    <%=strSection%>
</div>