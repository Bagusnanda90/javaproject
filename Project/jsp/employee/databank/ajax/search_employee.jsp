<%-- 
    Document   : search_employee
    Created on : 05-Jul-2016, 13:42:58
    Author     : Acer
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.form.search.*"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.harisma.entity.search.*"%>
<%@page import="com.dimata.harisma.session.employee.*" %>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmpWarning"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_COMMPANY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  
    String datafor = FRMQueryString.requestString(request, "datafor");
    String formName = FRMQueryString.requestString(request,"formName");
    String empPathId = FRMQueryString.requestString(request,"empPathId");
    
    SrcEmployee srcEmployee = new SrcEmployee();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
    
    CtrlEmpWarning ctrlEmpWarning = new CtrlEmpWarning(request);
    FrmEmpWarning frmEmpWarning = ctrlEmpWarning.getForm();
    if(datafor.equals("listemployee")){
        String whereClause = "";
        String order = "";
        Vector listEmployee = new Vector();

        
        int index = -1;
        int recordToGet = 0;
            
            order = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            listEmployee = PstEmployee.list(0, recordToGet, "", order);
        
        if (listEmployee != null && listEmployee.size()>0){
        %>
        <table id="listemployee" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Payroll</th>
                    <th>Name</th>
                    <th>Commencing Date</th>
                    <td>Division</td>
                    <td>Department</td>
                    <td>Position</td>
                    <td>Action</td>
                    
                </tr>
            </thead>
            <%
            for (int i = 0; i < listEmployee.size(); i++) {
                Employee employee = (Employee)listEmployee.get(i);
                Division div = new Division();
                Department dep = new Department();
                Position pos = new Position();
                
                String divString = "-";
                String depString = "-";
                String posString = "-";
                
                try {
                    
                    div = PstDivision.fetchExc(employee.getDivisionId());
                    divString = div.getDivision();
                    dep = PstDepartment.fetchExc(employee.getDepartmentId());
                    depString = dep.getDepartment();
                    pos = PstPosition.fetchExc(employee.getPositionId()); 
                    posString = pos.getPosition();
                    
                }
                catch(Exception e) {
                    div = new Division();
                    dep = new Department();
                    pos = new Position();
                    
                }  
                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%= employee.getEmployeeNum() %></td>
                    <td><%= employee.getFullName() %></td>
                    <% if(employee.getCommencingDate()!=null){ %>
                    <td><%= Formater.formatDate(employee.getCommencingDate(),"yyyy-MM-dd") %></td>
                    <% } else { %>
                    <td></td>
                    <% } %>
                    <td><%= divString %></td>
                    <td><%= depString %></td>
                    <td><%= posString %></td>
                    
                    <td>
                        <a href="javascript:" data-oid="<%= employee.getOID() %>" data-empId="<%= employee.getFullName()%>" data-for="showwarnform" class="addeditdata" data-command="<%= Command.NONE %>"><i class="fa fa-pencil"></i> Edit </a> 
                        |
                        <a href="javascript:" data-oid="<%= employee.getOID() %>" data-for="showwarnform" class="deletedata" data-command="<%= Command.DELETE %>"><i class="fa fa-times"></i> Delete</a>
                    </td>
                </tr>
                <%
            }
            %>
        </table>
        <%
        } else { %>
            <a> <h4>No Record </h4></a>
        <% }
    } %>
