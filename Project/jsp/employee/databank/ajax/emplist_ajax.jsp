<%-- 
    Document   : emplist_ajax
    Created on : 27-Jun-2016, 19:35:48
    Author     : Acer
--%>


<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompany"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.masterdata.Company"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmployee"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import="com.dimata.util.Formater"%>
<%@ include file = "../../../main/javainit.jsp" %>

<link rel="stylesheet" href="<%= approot%>/assets/dist/css/AdminLTE.css">
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_EMPLOYEE_MENU, AppObjInfo.OBJ_EMPLOYEE_MENU);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String empName = FRMQueryString.requestString(request, "employee_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    int resign = FRMQueryString.requestInt(request, "resign");
    String birtFrom = FRMQueryString.requestString(request, "FRM_FIELD_BIRTH_DATE_FROM");
    String birtTo = FRMQueryString.requestString(request, "FRM_FIELD_BIRTH_DATE_TO");
    String inEmpId = FRMQueryString.requestString(request, "inEmpId");
    if(datafor.equals("listEmployee")){
        String whereClause = "";
        String order = "";
        Vector listEmployee = new Vector();

        CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
        SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
        FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
//        if(birtFrom.length() > 0){
//            searchSpecialQuery.setDtBirthFrom(Formater.formatDate(birtFrom, "yyyy-MM-dd"));
//            searchSpecialQuery.setDtBirthTo(Formater.formatDate(birtTo, "yyyy-MM-dd"));
//            iCommand = Command.LIST;
//        }
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmployee.getCount("");
        
        if (!(inEmpId.equals(""))){
            whereClause = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN (" + inEmpId + ")";
        }
        
//        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
//                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
//            start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
//            try{
//            frmSrcSpecialEmployeeQuery.requestEntityObject(searchSpecialQuery);
//            
//            searchSpecialQuery = (SearchSpecialQuery)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE); 
//			if (searchSpecialQuery == null) {
//				searchSpecialQuery = new SearchSpecialQuery();
//			}
//          }catch(Exception e)
//          {
//          searchSpecialQuery = new SearchSpecialQuery();
//          }
//        }
        
        
        if ( iCommand == Command.LIST){
            listEmployee = SessSpecialEmployee.searchSpecialEmployee(searchSpecialQuery,start,recordToGet);
        } else {
            if (resign == 0){
                if (whereClause.equals("")){
                    whereClause = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = '"+resign+"'";
                } else {
                    whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = '"+resign+"'";
                }
                order = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                vectSize = PstEmployee.getCount(whereClause);
                listEmployee = PstEmployee.list(0, 0, whereClause, order);
                session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQuery);
            } else {
                vectSize = PstEmployee.getCount("");
                order = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                listEmployee = PstEmployee.list(start, recordToGet, whereClause, order);
                session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQuery);
            }
        }
        if (listEmployee != null && listEmployee.size()>0){
            
        %>
        <table id="listEmployee" class="table table-bordered table-hover" >
            <thead>
                <tr>
                    <th>No</th>
                    <th>Payroll Number</th>
                    <th>Full Name</th>
                    <th>Division</th>
                    <th>Position</th>
                    <th>Gender</th>
                    <th>Phone</th>
                    <th>Join Date</th>
                    <th>Date of Birth</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listEmployee.size(); i++) {
                Employee employee = (Employee) listEmployee.get(i);
                Company comp = new Company();
                Division div = new Division();
                Position pos = new Position();
                String compName = "-";
                String divName = "-";
                String posName = "-";
                String sex = "-";
                String commencingDate = "-";
                String birthDate = "-";
                String Status ="-";
                    try {
                        comp = PstCompany.fetchExc(employee.getCompanyId());
                        compName = comp.getCompany();
                        div = PstDivision.fetchExc(employee.getDivisionId());
                        divName = div.getDivision();
                        pos = PstPosition.fetchExc(employee.getPositionId());
                        posName = pos.getPosition();
                        
                        if (employee.getSex() == 0) {
                            sex = "Male";
                        } else if (employee.getSex() == 1) {
                            sex = "Female";
                        }
                        
                        commencingDate = Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy");
                        
                        birthDate = Formater.formatDate(employee.getBirthDate(), "dd MMM yyyy");
                        
                        if (employee.getResigned()== 0){
                            Status = "Active"; 
                        } else if (employee.getResigned() ==1){
                            Status = "Non-Active";
                        }
                        
                                                
                    } catch (Exception e) {
                        //System.out.print("getDivisionType=>"+e.toString());//
                    }
                %>
                <tr>                    
                    <%
                        if (employee.getResigned() == 1) {
                            %>
                    <td><font color="red"><%=(i + 1)%></font></td>
                    <td><font color="red"><%= employee.getEmployeeNum() %></font></td>
                    <td><font color="red"><%= employee.getFullName()%></font></td>
                    <td><font color="red"><%= divName %></font></td>
                    <td><font color="red"><%= posName %></font></td>
                    <td><font color="red"><%= sex %></font></td>
                    <td><font color="red"><%= employee.getPhone() %></font></td>
                    <td><font color="red"><%= commencingDate %></font></td>
                    <td><font color="red"><%= birthDate %></font></td>
                    <td><font color="red"><%= Status %></font></td>
                    <%
                        } else {
                    %>
                    <td><%=(i + 1)%></td>
                    <td><%= employee.getEmployeeNum() %></td>
                    <td><%= employee.getFullName()%></td>
                    <td><%= divName %></td>
                    <td><%= posName %></td>
                    <td><%= sex %></td>
                    <td><%= employee.getPhone() %></td>
                    <td><%= commencingDate %></td>
                    <td><%= birthDate %></td>
                    <td><font color="blue"><%= Status %></font></td>
                    <%
                        }
                    %>
                    <td>
                        
                        <button type="button" onclick="location.href='../databank/employee_edit.jsp?oid=<%= employee.getOID() %>'" class="btn btn-primary" data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" data-oid="<%= employee.getOID() %>" data-for="showempform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata" data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    </td>
                </tr>
                <%
            }
            %>
        </table>
            
        <%
        }  else { %>
            <a> <h4>No Record </h4></a>
        <% }
    } else if(datafor.equals("showempform")){

        if(iCommand == Command.DELETE){
            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            ctrlEmployee.action(iCommand, oid, request, emplx.getFullName(), appUserIdSess);
        }
    }
%>    