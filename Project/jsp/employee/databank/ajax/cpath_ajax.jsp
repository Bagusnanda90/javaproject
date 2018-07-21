<%-- 
    Document   : cpath_ajax
    Created on : 01-Jul-2016, 11:35:45
    Author     : Acer
--%>

<%@page import="com.dimata.common.entity.contact.ContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.PstContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.ContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.harisma.form.masterdata.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_EMPLOYEE_MENU, AppObjInfo.OBJ_EMPLOYEE_AND_FAMILY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long employeeid = FRMQueryString.requestLong(request, "empId");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listcareer")){
        Employee employee = new Employee();
        ResignedReason reason = new ResignedReason();
        long oidReason = 0;
        try{
            employee = PstEmployee.fetchExc(employeeid);
            oidReason = employee.getResignedReasonId();
            reason = PstResignedReason.fetchExc(oidReason);
        } catch (Exception exc){
            System.out.print("employee=>"+exc.toString());//
        }
        
        %> <a><h4> No Contract Data </h4></a> 
           <hr/>
           <a><h4> No Employee Mutation Data</h4></a>
           <hr/><%
        
        if(employee.getResigned() == 1){
         %> <a> <h5>Employee Resigned on : <%=employee.getResignedDate()%> </h5></a> 
          <a> <h5>Resigned Reason : <%=reason.getResignedReason()%> </h5></a>  
          <a> <h5>Resigned Description : <%=employee.getResignedDesc()%> </h5> </a> <%
        } else {
          %><a><h4> No Resign Data</h4></a><%
        }
    }
    
    if(datafor.equals("doresignform")){

        if(iCommand == Command.SAVE){
            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            ctrlEmployee.action(iCommand, oid, request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            ctrlEmployee.action(iCommand, oid, request, emplx.getFullName(), appUserIdSess);
        }else{
            Employee employee = new Employee();
            if(oid != 0){
                try{
                    employee = PstEmployee.fetchExc(employeeid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            
            <div class="form-group">
                <label>Resigned Status :</label>
                <input type="radio" name="resigned" value="YES" style="border:'none'"> YES
                <input type="radio" name="resigned" value="No" style="border:'none'"> NO
            </div>
            <div class="form-group">
                <label>Resigned Date :</label>
                <input type="text" name="" value="" class="form-control">
            </div>
            <div class="form-group">
                <label>Resigned Reason :</label>
                <select name="company" class="form-control">
                </select>    
            </div>
            <div class="form-group">
                <label>Resigned Description :</label>
                <textarea name="description" class="form-control" rows="3"></textarea>
            </div>
            
            
            
        <%
    }
}
    
%>    
    