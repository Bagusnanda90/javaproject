<%-- 
    Document   : emplist_ajax
    Created on : 27-Jun-2016, 19:35:48
    Author     : Acer
--%>


<%@page import="com.dimata.harisma.form.leave.FrmLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.attendance.Leave"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.harisma.entity.leave.PstLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.search.SrcLeaveApp"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.LeaveApplication"%>
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
    long oidLeave = FRMQueryString.requestLong(request, "hidden_leave_application_id");
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    //long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listEmployee")){
        String whereClause = "";
        String order = "";
        Vector listEmployee = new Vector();
        //Vector listEmployee = null; 
        SrcLeaveApp srcLeaveApp = new SrcLeaveApp();

        int type_form = FRMQueryString.requestInt(request, ""+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]);
        Control ctrlLeave = new Control();
        SessLeaveApplication sessLeaveApplication = new SessLeaveApplication();
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = sessLeaveApplication.searchCountLeaveApplication(srcLeaveApp, 0, 0);
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlLeave.actionList(iCommand, start, vectSize, recordToGet);
            
           try
	{ 
		srcLeaveApp = (SrcLeaveApp)session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION); 
		if (srcLeaveApp == null) 
		{
			srcLeaveApp = new SrcLeaveApp();
		}
	}
	catch(Exception e)
	{ 
		srcLeaveApp = new SrcLeaveApp();
	}
        }
        
        srcLeaveApp.setTypeForm(type_form);
        session.putValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION, srcLeaveApp);
        
        
        SessUserSession userSessionn = (SessUserSession) session.getValue(SessUserSession.HTTP_SESSION_NAME);
        AppUser appUserSess2 = userSessionn.getAppUser();
        String namaUser2 = appUserSess2.getFullName();
        if ((namaUser2.equals("Employee"))) {
            listEmployee = sessLeaveApplication.getListEmployeeLeaveApplication(emplx.getOID());
        } else {
            listEmployee = sessLeaveApplication.searchLeaveApplicationList(srcLeaveApp, start, recordToGet);
        }

        if (listEmployee != null && listEmployee.size()>0){
              
            I_Leave leaveConfig = null; 
            try {
                leaveConfig = (I_Leave)(Class.forName(""+PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
            }
            catch(Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }


            String useLongLeave ="";
            boolean bUseLL = false;
            try{
                useLongLeave = String.valueOf(PstSystemProperty.getValueByName("USE_LONG_LEAVE"));  
            }catch(Exception E){
                useLongLeave= "1";
                System.out.println("EXCEPTION SYS PROP USE_LONG_LEAVE : "+E.toString());
            }

            if( (useLongLeave==null || useLongLeave.equals("1"))  ){                           
                bUseLL = true;
            }
        %>
        
        <script language="JavaScript">
        function cmdEdit(oidLeave, oidEmployee)
        {
                document.frm_leave_application.command.value="<%=Command.EDIT%>";
                document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
                document.frm_leave_application.oid_employee.value = oidEmployee;
                document.frm_leave_application.action="leave_app_edit.jsp";
                document.frm_leave_application.submit();
        }
        </script>
        <form name="frm_leave_application" method="post" action="">
        <input type="hidden" name="command" value="">
        <input type="hidden" name="start" value="<%=start%>">
        <input type="hidden" name="hidden_leave_application_id" value="<%=oidLeave%>">
        <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>" value="">                           
        <input type="hidden" name="oid_employee" value="">     
        <input type="hidden" name="oid_period" value="">
        <table id="listEmployee" class="table table-bordered table-hover" >
            <thead>
                <tr>
                    <th>No</th>
                    <th>Payroll Number</th>
                    <th>Full Name</th>
                    <th>Department</th>
                    <th>Date Of Application</th>
                    <th>Doc Status</th>
                    <th>Approved by</th>
                    <%if(leaveConfig.isLeaveApprovalLevel(I_Leave.LEAVE_APPROVE_3)){%>
                    <th>GM Approval</th>
                    <%}%>
                    <th>HR Approved by</th>
                    <th>AL</th>
                    <%if(bUseLL){%>
                    <th>LL</th>
                    <%}%>
                    <th>DP</th>
                    <th>SL</th>
                    <th>UL</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
              for (int i=0; i<listEmployee.size(); i++)  {
              
                //Set Employee & Atribut :
                Vector temp = (Vector) listEmployee.get(i);
                Employee employee = (Employee) temp.get(0);
                LeaveApplication objleaveApplication = (LeaveApplication) temp.get(1);
                Department department = (Department) temp.get(2);
              
                //Set Date Of Application : 
                String strSubmissionDate = ""; 
		try
		{
			Date dt_SubmitDate = objleaveApplication.getSubmissionDate();
			if(dt_SubmitDate==null)
			{
				dt_SubmitDate = new Date();
			}
			strSubmissionDate = Formater.formatDate(dt_SubmitDate, "MMM dd, yyyy");
		}
		catch(Exception e)
		{ 
			strSubmissionDate = ""; 
		}  
              
                //Set Doc Status :
                String statusDoc = SessLeaveApplication.getStatusDocument(objleaveApplication.getDocStatus());
                
                //Set Approval :
                String depHead = SessLeaveApplication.getEmployeeApp(objleaveApplication.getDepHeadApproval());
                String hrMan = SessLeaveApplication.getEmployeeApp(objleaveApplication.getHrManApproval());                
                String gM = SessLeaveApplication.getEmployeeApp(objleaveApplication.getGmApproval());
                
                //Set Count Al,Ll,Dp Or Sl :
                
                
                float sumAL = SessLeaveApplication.countAL(objleaveApplication.getOID(),employee.getOID());
                float sumLL = SessLeaveApplication.countLL(objleaveApplication.getOID(),employee.getOID());
                float sumDP = SessLeaveApplication.countDP(objleaveApplication.getOID(),employee.getOID());
                float sumSpecial = SessLeaveApplication.countSpecialLeave(objleaveApplication.getOID(),employee.getOID());
                float sumUnpaid = SessLeaveApplication.countUnpaidLeave(objleaveApplication.getOID(),employee.getOID());
                
                %>
                <tr>                    
                    
                    <td><%=(i + 1)%></td>
                    <td><%= employee.getEmployeeNum() %></td>
                    <td><%= employee.getFullName() %></td>
                    <td><%= department.getDepartment() %></td>
                    <td><%= strSubmissionDate%></td>
                    <%
                    if(statusDoc != null){
                    %>
                    <td><%= statusDoc%></td>
                    <%}else{
                    %>
                    <td>-</td>
                    <%}%>
                    <!--Approval-->
                    <% if(depHead != null){ %>
                    <td><%= depHead%></td>
                    <%}else{%>
                    <td>-</td>
                    <%} if (hrMan != null){%>
                    <td><%= hrMan%></td>
                    <%}else {%>
                    <td>-</td>
                    <%}%> 
                    <% if(leaveConfig.isLeaveApprovalLevel(I_Leave.LEAVE_APPROVE_3)){%>
                    <%if (gM != null) {%>
                    <td><%= gM %></td>
                    <%}else {%>
                    <td>-</td>
                    <%}
                    }%>
                    <!--Stock Leave Leave-->
                    <% if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){%>
                    <td> <p title= <%= Formater.formatNumber(sumAL, "###.###")%> > <%= Formater.formatWorkDayHoursMinutes(sumAL,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p> </td>
                    <%} else {%>
                    <td> <p title= <%= Formater.formatNumber(sumAL, "###.###")%> > <%= Formater.formatWorkDayHoursMinutesII(sumAL,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p> </td>
                    <%}%>
                    <!--Stock Long Leave-->
                    <%if(bUseLL){%>
                    <%if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){ %>
                    <td><p title= <%= Formater.formatNumber(sumLL, "###.###")%> > <%= Formater.formatWorkDayHoursMinutes(sumLL,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%} else {%>
                    <td><p title= <%= Formater.formatNumber(sumLL, "###.###")%> > <%= Formater.formatWorkDayHoursMinutesII(sumLL,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%}}%>
                    <!--Stock Day Payment-->
                    <%if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){%>
                    <td><p title= <%= Formater.formatNumber(sumDP, "###.###")%> > <%= Formater.formatWorkDayHoursMinutes(sumDP,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%} else {%>
                    <td><p title= <%= Formater.formatNumber(sumDP, "###.###")%> > <%= Formater.formatWorkDayHoursMinutesII(sumDP,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%}%>
                    <!--Special Leave-->
                    <%if (leaveConfig != null && leaveConfig.isLeaveApplicationIsDay()) {%>
                    <td><P title= <%= Formater.formatNumber(sumSpecial,"###.###")%> > <%= Formater.formatWorkDayHoursMinutes(sumSpecial,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%} else {%>
                    <td><p title= <%= Formater.formatNumber(sumSpecial, "###.###")%> > <%= Formater.formatWorkDayHoursMinutesII(sumSpecial,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%}%>
                    <!--Unpaid Leave-->
                    <%if (leaveConfig != null && leaveConfig.isLeaveApplicationIsDay()) {%>
                    <td><P title= <%= Formater.formatNumber(sumUnpaid,"###.###")%> > <%= Formater.formatWorkDayHoursMinutes(sumUnpaid,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%} else {%>
                    <td><P title= <%= Formater.formatNumber(sumUnpaid,"###.###")%> > <%= Formater.formatWorkDayHoursMinutesII(sumUnpaid,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()) %> </p></td>
                    <%}%>
                    
                    <td>
                        <button type="button" onclick=" location.href='javascript:cmdEdit(\'<%=objleaveApplication.getOID()%>\',\'<%=employee.getOID()%>\')' " class="btn btn-primary"  title="Edit"><i class="fa fa-edit"></i></button>
                    </td>
                </tr>
                <%
            }
            %>
        </table>
         </form>   
        <%
        
        }  else { %>
            <a> <h4>No Record </h4></a>
        <% }%>
        
   <%
    }
%>    