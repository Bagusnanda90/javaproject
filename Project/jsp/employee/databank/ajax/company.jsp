<%-- 
    Document   : warning_ajax
    Created on : 07-Apr-2016, 10:03:03
    Author     : Acer
--%>
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
long empCat = 0;
long empLevel = 0;
long empGrade = 0;
long empPosition = 0;

String errMsg = "";
CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
errMsg = ctrlEmployee.getMessage();
FrmEmployee frmEmployee = ctrlEmployee.getForm();

if (employee_id != 0){
    Employee employee = new Employee();
    try {
        employee = PstEmployee.fetchExc(employee_id);
        companyId = employee.getCompanyId();
        divisionId = employee.getDivisionId();
        departmentId = employee.getDepartmentId();
        sectionId = employee.getSectionId();
        empCat = employee.getEmpCategoryId();
        empLevel = employee.getLevelId();
        empGrade = employee.getGradeLevelId();
        empPosition = employee.getPositionId();
        
    } catch(Exception e){
        System.out.print(""+e.toString());
    }
}
%>
<div class="form-group">
<label>
    Company 
</label> <font color="blue">* ] Entry Required</font> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_COMPANY_ID)%>
    <select class="form-control"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID]%>" onchange="javascript:loadDivision(this.value)">
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
   Division
</label> <font color="blue">* ] Entry Required</font> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DIVISION_ID)%>
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID]%>" onchange="javascript:loadDepartment('<%=companyId%>', this.value)">
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
    Department
</label> <font color="blue">* ] Entry Required</font> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DEPARTMENT_ID)%>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID]%>" onchange="javascript:loadSection('<%=companyId%>','<%=divisionId%>',this.value)">
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
    Section
</label> <font color="blue">* ] Entry Required</font> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_SECTION_ID)%>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECTION_ID]%>">
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
    Employee Category
</label> <font color="blue">* ] Entry Required</font> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID)%>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID]%>">
        <option value="0">-select-</option>
        <%
            Vector listCategory = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]);

            if (listCategory != null && listCategory.size()>0){
                for(int i=0; i<listCategory.size(); i++){
                    EmpCategory empCategory = (EmpCategory)listCategory.get(i);
                    if (empCat == empCategory.getOID()){
                        %><option selected="selected" value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                    } else {
                        %><option value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                    }
                }
            }

             
        %>
    </select>
</div>
<div class="form-group">
<label>
    Level
</label> <font color="blue">* ] Entry Required</font> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_LEVEL_ID)%>
<div class="input-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LEVEL_ID]%>">
        <option value="0">-select-</option>
        <%
            Vector listLevel = PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_LEVEL]);

            if (listLevel != null && listLevel.size()>0){
                for(int i=0; i<listLevel.size(); i++){
                    Level level = (Level)listLevel.get(i);
                    if (empLevel == level.getOID()){
                        %><option selected="selected" value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                    } else {
                        %><option value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                    }
                }
            }

             
        %>
    </select>
    <span class="input-group-addon">-</span>
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_GRADE_LEVEL_ID]%>">
        <option value="0">-select-</option>
        <%
            Vector listGrade = PstGradeLevel.list(0, 0, "", PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE]);

            if (listGrade != null && listGrade.size()>0){
                for(int i=0; i<listGrade.size(); i++){
                    GradeLevel gradeLevel = (GradeLevel)listGrade.get(i);
                    if (empGrade == gradeLevel.getOID()){
                        %><option selected="selected" value="<%=gradeLevel.getOID()%>"><%=gradeLevel.getCodeLevel()%></option><%
                    } else {
                        %><option value="<%=gradeLevel.getOID()%>"><%=gradeLevel.getCodeLevel()%></option><%
                    }
                }
            }

             
        %>
    </select>
</div>
</div>    
<label>
    Position
</label> <font color="blue">* ] Entry Required</font> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_POSITION_ID)%>
<div class="form-group">
    <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSITION_ID]%>">
        <option value="0">-select-</option>
        <%
            Vector listPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);

            if (listPosition != null && listPosition.size()>0){
                for(int i=0; i<listPosition.size(); i++){
                    Position position = (Position)listPosition.get(i);
                    if (empPosition == position.getOID()){
                        %><option selected="selected" value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                    } else {
                        %><option value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                    }
                }
            }
        %>
    </select>
</div>