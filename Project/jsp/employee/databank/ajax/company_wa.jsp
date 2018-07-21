<%-- 
    Document   : warning_ajax
    Created on : 07-Apr-2016, 10:03:03
    Author     : Acer
--%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
long employee_id = FRMQueryString.requestLong(request, "employee_id");
long companyId = FRMQueryString.requestLong(request, "company_id");
long divisionId = FRMQueryString.requestLong(request, "division_id");
long departmentId = FRMQueryString.requestLong(request, "department_id");
long sectionId = FRMQueryString.requestLong(request, "section_id");
long positionId = FRMQueryString.requestLong(request, "position_id");

long providerId = 0;
Date waFrom = new Date();
Date waTo = new Date();

if (employee_id != 0){
    Employee employee = new Employee();
    try {
        employee = PstEmployee.fetchExc(employee_id);
        companyId = employee.getWorkassigncompanyId();
        divisionId = employee.getWorkassigndivisionId();
        departmentId = employee.getWorkassigndepartmentId();
        sectionId = employee.getWorkassignsectionId();
        positionId = employee.getWorkassignpositionId();
        providerId = employee.getProviderID();
        waFrom = employee.getWaFrom();
        waTo = employee.getWaTo();
    } catch(Exception e){
        System.out.print(""+e.toString());
    }
}
%>
<div class="form-group">
<label>
    W.A. Company
</label>
    <select class="form-control"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_COMPANY_ID]%>" onchange="javascript:loadWaDivision(this.value)">
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
<div class="form-group">    
<label>
   W.A. Division
</label>
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DIVISION_ID]%>" onchange="javascript:loadWaDepartment('<%=companyId%>', this.value)">
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

<label>
    W.A. Department
</label>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DEPARTMENT_ID]%>" onchange="javascript:loadWaSection('<%=companyId%>','<%=divisionId%>',this.value)">
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

<label>
    W.A. Section
</label>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_SECTION_ID]%>">
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
    
<label>
    W.A. Position
</label>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_POSITION_ID]%>">
        <option value="0">-select-</option>
        <%
            Vector listPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);

            if (listPosition != null && listPosition.size()>0){
                for(int i=0; i<listPosition.size(); i++){
                    Position position = (Position)listPosition.get(i);
                    if (positionId == position.getOID()){
                        %><option selected="selected" value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                    } else {
                        %><option value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                    }
                }
            }
        %>
    </select>
</div>   
<label>
    W.A. Provider
</label>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROVIDER_ID]%>">
        <option value="0">-select-</option>
        <%
            Vector listProvider = PstContactList.list(0, 0, "", ""+ PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+","+ PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]);
                for (int i = 0; i < listProvider.size(); i++) {
                    ContactList waContact = (ContactList) listProvider.get(i);
                    if (providerId == waContact.getOID()){
                        %><option selected="selected" value="<%=waContact.getOID()%>"><%=waContact.getCompName()%></option><%
                    } else {
                        %><option value="<%=waContact.getOID()%>"><%=waContact.getCompName()%></option><%
                    }
                }
        %>
    </select>
</div>
  