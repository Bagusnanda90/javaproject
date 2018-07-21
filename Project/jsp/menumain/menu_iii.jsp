<%-- 
    Document   : nemenu_iii
    Created on : Aug 27, 2013, 10:45:06 AM
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

<style type="text/css">
    /* ---------------------- Blueslate nav ---------------------- */
     #tabs26{
          display:table-cell; vertical-align:middle;position:relative;height:75px;
      width: expression( document.body.clientWidth < 1100 ? "1100px" : "auto" ); /* set min-width for IE */
          font-size:13px;/*font-weight:bold*/; background-color: <%=bgMenu%>;  font-family:Arial,Verdana,Helvitica,sans-serif; overflow:auto;
    }
    
   ul.menu3 {      
    margin:0;     
    padding:0;     
    list-style:none;
    color: <%=fontMenu%>;
} 

 ul.menu3 li a,  ul.menu3 li span {  
    display: block;
    
} 


 ul.menu3 li {     
    float:left;     
   text-align: center;
     list-style:none;
} 

 ul.menu3 li a {     
    text-decoration:line-through;     
    padding:0  15px; 
} 


 ul.menu3 li a:hover {  
    background: <%=hoverMenu%>;
    
}



    /* ---------------------- END Blueslate nav ---------------------- */
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
        
        String strLangFlyOut = "";
           int appLanguageFlyOut = 0;
           if(session.getValue("APPLICATION_LANGUAGE")!=null){
                    strLangFlyOut = String.valueOf(session.getValue("APPLICATION_LANGUAGE"));
            }
            appLanguageFlyOut = (strLangFlyOut!=null && strLangFlyOut.length()>0) ? Integer.parseInt(strLangFlyOut) : 0;
%>
<div id="tabs26" align="left" >
    <ul class="menu3">
<li><a href="<%=approot%>/home.jsp?menu=home.jsp"><span><img src="<%=approot%>/menustylesheet/icon/home.png" /></span></a><%=homeActive.length()==0?"Home":"<b>Home</b>"%></li>

<%if(mnuEmployee){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=employee.jsp"><span><img src="<%=approot%>/menustylesheet/icon/employee.png" /></span></a><% if (appLanguageFlyOut == 0){%><%=employeeActive.length()==0?"Karyawan":"<b>Karyawan</b>"%><% } else {%><%=employeeActive.length()==0?"Employee":"<b>Employee</b>"%> <%}%></li>
<%}%>
<%if(mnuTraining){%> 
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=training.jsp"><span><img src="<%=approot%>/menustylesheet/icon/training.png" /></span></a><% if (appLanguageFlyOut == 0){%><%=employeeActive.length()==0?"Latihan":"<b>Latihan</b>"%><% } else {%><%=employeeActive.length()==0?"Training":"<b>Training</b>"%> <%}%></li>
<%}%>
<%if(mnuReports){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=reports.jsp"><span><img src="<%=approot%>/menustylesheet/icon/report.png" /></span></a><% if (appLanguageFlyOut == 0){%><%=employeeActive.length()==0?"Laporan":"<b>Laporan</b>"%><% } else {%><%=employeeActive.length()==0?"Reports":"<b>Reports</b>"%> <%}%></li>
<%}%>
<%if(mnuCanteen){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=canteen.jsp"><span><img src="<%=approot%>/menustylesheet/icon/canteen.png" /></span></a><% if (appLanguageFlyOut == 0){%><%=employeeActive.length()==0?"Kantin":"<b>Kantin</b>"%><% } else {%><%=employeeActive.length()==0?"Canteen":"<b>Canteen</b>"%> <%}%></li>
<%}%>
<%if(mnuClinic){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=clinic.jsp"><span><img src="<%=approot%>/menustylesheet/icon/clinic.png" /></span></a><%=clinicActive.length()==0?"Clinic":"<b>Clinic</b>"%></li>
<%}%>
<%if(mnuLocker){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=locker.jsp"><span><img src="<%=approot%>/menustylesheet/icon/locker.png" /></span></a><%=lockerActive.length()==0?"Locker":"<b>Locker</b>"%></li>
<%}%>
<%if(mnuMaster){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=master_data.jsp"><span><img src="<%=approot%>/menustylesheet/icon/master.png" /></span></a><%=masterDataActive.length()==0?"Master":"<b>Master</b>"%></li>
<%}%>
<%if(mnuSystem){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=system.jsp"><span><img src="<%=approot%>/menustylesheet/icon/system.png" /></span></a><%=systemActive.length()==0?"System":"<b>System</b>"%></li>
<%}%>
<%if(mnuPayrollSetup){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=payroll_setup.jsp"><span><img src="<%=approot%>/menustylesheet/icon/setup_payroll.png" /></span></a><%=payrollSetupActive.length()==0?"Pay.Setup":"<b>Pay.Setup</b>"%></li>
<%}%>
<%if(mnuOvertime){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=overtime.jsp"><span><img src="<%=approot%>/menustylesheet/icon/overtime.png" /></span></a><%=overtimeActive.length()==0?"Overtime":"<b>Overtime</b>"%></li>
<%}%>
<%if(mnuPayrollProcess){%>
<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=payroll_process.jsp"><span><img src="<%=approot%>/menustylesheet/icon/payroll_p.png" /></span></a><%=payrollProcessActive.length()==0?"Pay.Process":"<b>Pay.Process</b>"%></li>
<%}%>

<li><a href="<%=approot%>/menuaplikasi/home.jsp?menu=help.jsp"><span><img src="<%=approot%>/menustylesheet/icon/help.png" /></span></a><%=helpActive.length()==0?"Help":"<b>Help</b>"%></li>

</ul> 
</div>