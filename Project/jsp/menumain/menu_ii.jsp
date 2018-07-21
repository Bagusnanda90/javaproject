<%-- 
    Document   : menu_ii
    Created on : Aug 27, 2013, 9:06:22 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%
    String spliturlMenu[] = request.getServletPath().toString().trim().split("/");

    String urlMenu = null;
    if (spliturlMenu != null && spliturlMenu.length > 0) {
        // for(int i=0;i<spliturl.length;i++) {
        // if (spliturl[i].equalsIgnoreCase("home.jsp")) {
        int idxLnght = spliturlMenu.length - 1;
        try {
            urlMenu = spliturlMenu[idxLnght];
        } catch (Exception exc) {
        }
    }
    String homeActive = "";
    String employeeActive = "";
    String trainingActive = "";
    String reportsActive = "";
    String canteenActive = "";
    String clinicActive = "";
    String lockerActive = "";
    String masterDataActive = "";
    String systemActive = "";
    String payrollSetupActive = "";
    String overtimeActive = "";
    String payrollProcessActive = "";
    String helpActive = "";

    if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("home.jsp")) {
        homeActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("employee.jsp")) {
        employeeActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("training.jsp")) {
        trainingActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("reports.jsp")) {
        reportsActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("canteen.jsp")) {
        canteenActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("clinic.jsp")) {
        clinicActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("locker.jsp")) {
        lockerActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("master_data.jsp")) {
        masterDataActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("system.jsp")) {
        systemActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("payroll_setup.jsp")) {
        payrollSetupActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("overtime.jsp")) {
        overtimeActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("payroll_process.jsp")) {
        payrollProcessActive = "class=\"current\"";
    } else if (urlMenu != null && urlMenu.length() > 0 && urlMenu.equalsIgnoreCase("help.jsp")) {
        helpActive = "class=\"current\"";
    }
%>
     
        <style>
            #tabs24{
     *display: inline;/* untuk IE */
    *zoom:1;/* untuk IE */
    width: auto !important;/* untuk IE */
    width: 1200px;/* untuk IE */
    min-width: 1200px;/* untuk IE */
    vertical-align:middle;
   
    position:relative;height:70px; overflow: auto; width: auto;font-size:13px;overflow: auto; background-color: <%=bgMenu%>;font-family:Georgia, "Times New Roman", Times, serif;}
#tabs24 ul{margin:0;padding:0;list-style-type:none;width:auto;}
#tabs24 ul li{display:inline;}
#tabs24 ul li a span{display:block;float:left; color: <%=warnaFont%>;text-decoration:none;padding:30px 6px 5px 6px; height: 29px;}
#tabs24 ul li a:hover span{display:block;cursor:pointer; font-weight: bold;color: <%=hoverMenu%>;}
#tabs24 ul li a.current span{display:block;cursor:pointer; font-weight: bold;color: <%=hoverMenu%>;}
        </style>
        <%
    //Update By Agus 20140106
        boolean mnuEmployee = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_EMPLOYEE_MENU, AppObjInfo.OBJ_EMPLOYEE_MENU), 
             AppObjInfo.COMMAND_VIEW));        
        boolean mnuTraining= userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_MENU, AppObjInfo.OBJ_TRAINING_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuReports = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_REPORT_MENU, AppObjInfo.OBJ_REPORT_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuCanteen= userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_CANTEEN_MENU, AppObjInfo.OBJ_CANTEEN_MENU), 
             AppObjInfo.COMMAND_VIEW)); 
        boolean mnuClinic = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_CLINIC_MENU, AppObjInfo.OBJ_CLINIC_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuLocker = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_LOCKER, AppObjInfo.G2_LOCKER_MENU, AppObjInfo.OBJ_LOCKER_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuMaster = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MASTERDATA_MENU, AppObjInfo.OBJ_MASTERDATA_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuSystem = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_SYSTEM_MENU, AppObjInfo.OBJ_SYSTEM_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuPayrollSetup = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP_MENU, AppObjInfo.OBJ_PAYROLL_SETUP_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuOvertime = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME_MENU, AppObjInfo.OBJ_PAYROLL_OVERTIME_MENU), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuPayrollProcess = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS_MENU, AppObjInfo.OBJ_PAYROLL_PROCESS_MENU), 
             AppObjInfo.COMMAND_VIEW)) ;    
%>
        <div id="tabs24" align="right">
            <ul>
   <li><a href="<%=approot%>/home.jsp?menu=home.jsp"><span><%=homeActive.length()==0?"Home":"<b>Home</b>"%></span></a></li>             
   <%if(mnuEmployee){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=employee.jsp"><span><%=employeeActive.length()==0?"Employee":"<b>Employee</b>"%></span></a></li>
   <%}%>
   <%if(mnuTraining){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=training.jsp"><span><%=trainingActive.length()==0?"Training":"<b>Training</b>"%></span></a></li>
   <%}%>
   <%if(mnuReports){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=reports.jsp"><span><%=reportsActive.length()==0?"Reports":"<b>Reports</b>"%></span></a></li>
   <%}%>
   <%if(mnuCanteen){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=canteen.jsp"><span><%=canteenActive.length()==0?"Canteen":"<b>Canteen</b>"%></span></a></li>
   <%}%>
   <%if(mnuClinic){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=clinic.jsp"><span><%=clinicActive.length()==0?"Clinic":"<b>Clinic</b>"%></span></a></li>
   <%}%>
   <%if(mnuLocker){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=locker.jsp"><span><%=lockerActive.length()==0?"Locker":"<b>Locker</b>"%></span></a></li>
   <%}%>
   <%if(mnuMaster){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=master_data.jsp"><span><%=masterDataActive.length()==0?"Master":"<b>Master</b>"%></span></a></li>
   <%}%>
   <%if(mnuSystem){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=system.jsp"><span><%=systemActive.length()==0?"System":"<b>System</b>"%></span></a></li>
   <%}%>
   <%if(mnuPayrollSetup){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=payroll_setup.jsp"><span><%=payrollSetupActive.length()==0?"Pay.Setup":"<b>Pay.Setup</b>"%></span></a></li>
   <%}%>
   <%if(mnuOvertime){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=overtime.jsp"><span><%=overtimeActive.length()==0?"Overtime":"<b>Overtime</b>"%></span></a></li>
   <%}%>
   <%if(mnuPayrollProcess){%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=payroll_process.jsp"><span><%=payrollProcessActive.length()==0?"Pay.Process":"<b>Pay.Process</b>"%></span></a></li>
   <%}%>
   <li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=help.jsp"><span><%=helpActive.length()==0?"Help":"<b>Help</b>"%></span></a></li>
                
            </ul>
        </div> 