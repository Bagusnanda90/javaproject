<%-- 
    Document   : list_view_ajax
    Created on : Apr 14, 2016, 10:43:28 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeeView"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeePicture"%>
<%@page import="java.util.Vector"%>
<%@ include file = "../../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<table class="list-view">
<%
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
    SessEmployeeView sessEmpView = new SessEmployeeView();
    String whereClause = "";
    String pictPath = "";
    Vector listEmployee = new Vector();
    
    if (divisionId != 0 && departmentId == 0 && sectionId == 0){
        whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
        listEmployee = PstEmployee.list(0, 0, whereClause, "");
    }
    if (divisionId != 0 && departmentId != 0 && sectionId == 0){
        whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
        whereClause += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
        listEmployee = PstEmployee.list(0, 0, whereClause, "");
    }
    if (divisionId != 0 && departmentId != 0 && sectionId != 0){
        whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
        whereClause += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
        whereClause += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId;
        listEmployee = PstEmployee.list(0, 0, whereClause, "");
    }
    
    int count = 1;
%>
    <tr>
        <td colspan="2" valign="top">
            <div class="box-info-1">
                <table>
                    <tr>
                        <td><strong style="padding-right: 12px">Division</strong></td>
                        <td><%= sessEmpView.getDivisionName(divisionId) %></td>
                    </tr>
                    <tr>
                        <td><strong style="padding-right: 12px">Department</strong></td>
                        <td><%= sessEmpView.getDepartmentName(departmentId) %></td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
<%
    for(int i=0; i<listEmployee.size(); i++){
        Employee emp = (Employee)listEmployee.get(i);
        if (count == 1){
%>
    
    <tr>
        <% } %>
        <td width="30%" style="background-color:#FFF; border: 1px solid #DDD">
            <table style="background-color:#FFF; padding: 3px;" border="0">
                <tr>
                    <td rowspan="2">
                        <%

            try {
                SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                pictPath = sessEmployeePicture.fetchImageEmployee(emp.getOID());

            } catch (Exception e) {
                System.out.println("err." + e.toString());
            }%> 
            <%
                 if (pictPath != null && pictPath.length() > 0) {
                    out.println("<img height=\"58\" src=\"" + approot + "/" + pictPath + "\">");
                 } else {
            %>
            <img height="38" src="<%=approot%>/imgcache/no-img.jpg" />
            <%
                }
            %>
                    </td>
                    <td style="padding:2px" valign="bottom">
                        <strong style="font-size: 12px;">
                            <a href="javascript:viewEmployeeDetail('<%= emp.getOID() %>')">
                            <%= emp.getFullName()+" ("+emp.getEmployeeNum()+")" %>
                            </a>
                        </strong>
                    </td>
                </tr>
                <tr>
                    <td style="padding:2px" valign="top">
                        <%= sessEmpView.getPositionName(emp.getPositionId()) %>
                    </td>
                </tr>
            </table>
        </td>

        <%
        if (count == 2){
            count = 1;
            %>
            </tr>
            <%
        } else {
            count++;
        }
    }
    if (count < 3){
        %>
        </tr>
        <%
    }
    %>
</table>