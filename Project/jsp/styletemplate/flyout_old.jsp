<%-- 
    Document   : flyout
    Created on : Sep 18, 2013, 5:13:29 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.entity.template.Flyout"%>


<%if(true){%>
<!-- isMSIE -->
<link rel="stylesheet" href="../stylesheets/flayout_no_ie.css" type="text/css">
<%}else{%>
<style type="text/css">
    .content{position:relative; width:100%; 
/*background-color:white;
background-repeat: repeat-x;
padding-top:61px;*/
min-height: 400px;
height: 400px !important;
background-attachment: local;
 background-image:url(../stylesheets/images/background.png);
 background-repeat: no-repeat;
background-position: right bottom;
 /*background:url(../menuaplikasi/imgHeader/elemet%20grafis.png) no-repeat right ;*/
}
</style>
<link rel="stylesheet" href="../stylesheets/flayout_with_ie.css" type="text/css">
<%}%>
<%
Flyout flyout = new Flyout();
flyout.setUrlKlik(url);
flyout.addMenuFlyOut("employee.jsp", "Employee", "Data Bank", false, null, "data_bank", "../employee/databank/srcemployee.jsp", "", true);

flyout.addMenuFlyOut("employee.jsp", "Employee", "Company Structur", false, null, "company_structure", "../employee/databank/srcEmployee_structure.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "Employee2", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee3", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee4", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee5", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee6", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee7", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee8", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee9", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee10", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee11", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee12", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee13", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee14", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee15", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee16", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee17", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee18", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee19", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee20", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee21", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee22", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee23", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);
flyout.addMenuFlyOut("employee.jsp", "Employee", "New Employee24", false, null, "new_employee", "../employee/databank/new_employee_list.jsp", "", true);

flyout.addMenuFlyOut("employee.jsp", "Employee", "Absance Management", false, null, "absence_management", "../employee/absence/srcabsence.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ABSENCE_MANAGEMENT, AppObjInfo.OBJ_MENU_ABSENCE_MANAGEMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("employee.jsp", "Employee", "Recognition", true, "Entry Per Departement0", "recognitions", "#", "../employee/absence/srcabsence.jsp",true); 
flyout.addMenuFlyOut("employee.jsp", "Employee", "Recognition", true, "Entry Per Departement1", "recognitions", "#", "../employee/absence/srcabsence.jsp",true); 
flyout.addMenuFlyOut("employee.jsp", "Employee", "Recognition", true, "Entry Per Departement2", "recognitions", "#", "../employee/absence/srcabsence.jsp", true); 

flyout.addMenuFlyOut("training.jsp", "Training", "Training", true, "Entry Per Departement1", "Recognitionstxza", "#", "../employee/absence/srcabsence.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECOGNITION, AppObjInfo.OBJ_MENU_ENTRY_PER_DEPT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("training.jsp", "Training", "Training", true, "Update Per Employee2", "Recognitionstxza", "#", "../employee/recognition/recognitiondep.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECOGNITION, AppObjInfo.OBJ_MENU_UPDATE_PER_EMPLOYEE), 
             AppObjInfo.COMMAND_VIEW)));




//flyout.MenuFlyout(out, url, isMSIE, approot);
flyout.drawMenuFlayOut(out, url, isMSIEE, approot);;

%>

                
               

