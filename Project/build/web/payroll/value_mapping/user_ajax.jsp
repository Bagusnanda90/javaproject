<%-- 
    Document   : user_ajax
    Created on : Mar 18, 2016, 11:20:35 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.harisma.entity.admin.AppUser"%>
<%@page import="com.dimata.harisma.entity.admin.PstAppUser"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userName = FRMQueryString.requestString(request, "user_name");
    int iCommand = FRMQueryString.requestCommand(request);
    Vector listUser = new Vector();
    String whereClause = "";
    String order = "";
    
    if (userName.equals("0")){
        order = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
        listUser = PstEmployee.list(0, 0, "", order);
    } else {
        whereClause = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+userName+"%'";
        order = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
        listUser = PstEmployee.list(0, 0, whereClause, order);
    }
%>
<table class="tblStyle">
    <tr>
        <td class="title_tbl">No</td>
        <td class="title_tbl">Emp.No.</td>
        <td class="title_tbl">Nama</td>
    </tr>
    <%
    if (listUser != null && listUser.size()>0){
        for (int i=0; i<listUser.size(); i++){
            Employee emp = (Employee)listUser.get(i);
            %>
            <tr>
                <td><%=i+1%></td>
                <td><%= emp.getEmployeeNum() %></td>
                <td><div id="uname" onclick="javascript:cmdGetItem('<%= emp.getOID() %>','<%= emp.getEmployeeNum() %>')"><%= emp.getFullName() %></div></td>
            </tr>
            <%
        }
    }
    %>
</table>
