<%-- 
    Document   : employee_view (copy from employee_edit.jsp)
    Created on : Jan 20, 2015, 3:17:32 PM
    Author     : Dimata 007 (Hendra McHen)
--%>


<%@page import="com.dimata.harisma.entity.clinic.MedicalType"%>
<%@page import="com.dimata.harisma.entity.clinic.MedExpenseType"%>
<%@page import="com.dimata.harisma.entity.clinic.PstMedExpenseType"%>
<%@page import="com.dimata.harisma.entity.clinic.PstMedicalType"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.location.Location" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    long oidEmployee = emplx.getOID(); //FRMQueryString.requestLong(request, "employee_oid");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int hidden_command = FRMQueryString.requestInt(request, "hidden_flag_cmd");

    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
     //update by satrya 2013-04-09
    int schedulePerWeek = 0;    
    int recordToGet=7;
    try{
        schedulePerWeek = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK"));
        if(schedulePerWeek!=0){
            recordToGet=35;
        }
    }catch(Exception ex){
        System.out.println("Execption ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK: " + ex.toString());
        schedulePerWeek=0;
    }

    int iErrCode = FRMMessage.ERR_NONE;
    String errMsg = "";
    String whereClause = "";
    String orderClause = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");

    //out.println("iCommand : "+iCommand);
    ControlLine ctrLine = new ControlLine();
    //System.out.println("iCommand = "+iCommand);
    //System.out.println("prevCommand = "+prevCommand);
    iErrCode = ctrlEmployee.action(iCommand, oidEmployee, request, "", 0);

    errMsg = ctrlEmployee.getMessage();
    FrmEmployee frmEmployee = ctrlEmployee.getForm();
    //Employee employee = ctrlEmployee.getEmployee();
    //oidEmployee = employee.getOID();
    //--------------------------------------
    //sehubungan dengan picture
    //update by satrya 2013-02-12
    Employee employee = new Employee();
   
    try {
        if(oidEmployee!=0){
        employee = PstEmployee.fetchExc(oidEmployee);
        }
    } catch (Exception exc) {
        employee = new Employee();
        System.out.println("Exception employee"+exc);
    }
     ///update by satrya 2013-10-21
    if(iCommand==Command.GOTO){
        iCommand=Command.ADD;
        frmEmployee.requestEntityObject(employee);
    }
    if (iCommand == Command.ADD) {
        //employee = new Employee();
        //frmEmployee.requestEntityObject(employee);
    }
    
     System.out.println("Oid Employee: "+oidEmployee);

   // if (iErrCode != 0) {
       // if (iErrCode != 0 && iCommand == Command.SAVE) {
     //update by sarya 2013-08-13
     //karena waktu save empnya tidak muncul
        if(iCommand == Command.SAVE){
            employee = ctrlEmployee.getEmployee();
        }
         //}
    //}



    //----------------------------------------
    //locker;
    FrmLocker frmLocker = ctrlEmployee.getFormLocker();
    Locker locker = ctrlEmployee.getLocker();


//	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmEmployee.errorSize()<1)){
    if (iCommand == Command.DELETE) {
%>
<jsp:forward page="employee_list.jsp">
    <jsp:param name="prev_command" value="<%=prevCommand%>" />
    <jsp:param name="start" value="<%=start%>" />
    <jsp:param name="employee_oid" value="<%=employee.getOID()%>" />
</jsp:forward>
<%
    }

    //if ((iCommand == Command.SAVE) && (iErrCode == 0)) {
      //  iCommand = Command.EDIT;
    //}
    //Gede_21Nov2011
    boolean isCopy = FRMQueryString.requestBoolean(request, "hidden_copy_status"); 
    long gotoEmployee = FRMQueryString.requestLong(request, "hidden_goto_employee");
    
    long companyId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID]);
    long divisionId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID]);
    long departementId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID]);
    
    long wacompanyId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_COMPANY_ID]);
    long wadivisionId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DIVISION_ID]);
    long wadepartementId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DEPARTMENT_ID]);
    
    long wasectionId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_SECTION_ID]);
    
    //update by satrya 20130907
    if(iCommand==Command.GOTO){
        employee.setCompanyId(companyId);
        employee.setDivisionId(divisionId);
        employee.setDepartmentId(departementId);
    }
    //long sectionId = FRMQueryString.requestLong(request, "hidden_companyId");
%>

<!-- End of Jsp Block -->
<html>
    <!-- #BeginTemplate "/Templates/maintab.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Employee</title>
        <script language="JavaScript">
            <!--

            function cmdPrint(){
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailPdf?oid=<%=oidEmployee%>");
            }

            function cmdPrintXLS(){
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailXLS?oid=<%=oidEmployee%>");
            }

            function cmdGetLocker(){
                strLockerLocation = document.frm_employee.LOCKER_LOCATION.value;
                if(strLockerLocation!=0){
                    window.open("srcLocker.jsp?<%=FrmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCATION]%>="+strLockerLocation, null, "height=500,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
                }else{
                    document.frm_employee.LOCKER_NUMBER_POS.value="";
                    document.frm_employee.<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCKER_ID]%>.value="";
                }

            }
            //Gede_25Nov2011{
            function cmdSearchEmp(){
                window.open("<%=approot%>/employee/search/search.jsp?formName=frm_employee&employeeOID=<%=String.valueOf(oidEmployee)%>&empPathId=<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HOD_EMPLOYEE_ID]%>",
                            "Search_HOD",                    "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }

             function editDefaultSch(){
                 window.open("<%=approot%>/employee/databank/default_schedule.jsp?employeeId=<%=employee.getOID()%>",
                            "Harisma_Edit_Default_Schedule", "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
             }


            function updateGeoAddress(){
                oidNegara    = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_COUNTRY_ID]%>.value;
                oidProvinsi  = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PROVINCE_ID]%>.value ;
                oidKabupaten = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_REGENCY_ID]%>.value ;
                oidKecamatan = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_SUBREGENCY_ID]%>.value;                    
                
                window.open("<%=approot%>/employee/search/geo_address.jsp?formName=frm_employee&employee_oid=<%=String.valueOf(oidEmployee)%>&addresstype=1&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]%>="+oidNegara+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI]%>="+oidProvinsi+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]%>="+oidKabupaten+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]%>="+oidKecamatan+"&employee=<%=(employee.getEmployeeNum() + " / " + employee.getFullName())%>",                            
                null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }

            function updateGeoAddressPmnt(){
                oidNegara    = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_COUNTRY_ID]%>.value;
                oidProvinsi  = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_PROVINCE_ID]%>.value ;
                oidKabupaten = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_REGENCY_ID]%>.value ;
                oidKecamatan = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_SUBREGENCY_ID]%>.value;                    
                window.open("<%=approot%>/employee/search/geo_address.jsp?formName=frm_employee&employee_oid=<%=String.valueOf(oidEmployee)%>&addresstype=2&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]%>="+oidNegara+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI]%>="+oidProvinsi+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]%>="+oidKabupaten+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]%>="+oidKecamatan+"&employee=<%=(employee.getEmployeeNum() + " / " + employee.getFullName())%>",                                                
                null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }

            function cmdClearSearchEmp(){
                document.frm_employee.EMP_NUMBER.value = "";
                document.frm_employee.EMP_FULLNAME.value = "";
                document.frm_employee.EMP_DEPARTMENT.value = "";
            }
            //}
            function cmdAdd(){
                document.frm_employee.command.value="<%=Command.ADD%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.employee_oid.value=0;
                document.frm_employee.submit();
            }
            function cmdCancel(){
                document.frm_employee.command.value="<%=Command.CANCEL%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }

            function cmdEdit(oid){
                document.frm_employee.command.value="<%=Command.EDIT%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }

            function cmdSave(){
                document.frm_employee.command.value="<%=Command.SAVE%>";
                document.frm_employee.action="employee_edit.jsp";
                
                document.frm_employee.submit();
            }

            function cmdAsk(oid){
                document.frm_employee.command.value="<%=Command.ASK%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }

            function cmdConfirmDelete(oid){
                document.frm_employee.command.value="<%=Command.DELETE%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }

            function cmdBack(){
                document.frm_employee.command.value="<%=Command.FIRST%>";
                //document.frm_employee.command.value="<%=Command.LIST%>";
                document.frm_employee.action="employee_list.jsp";
                document.frm_employee.submit();
            }
            //Gede_25Nov2011{
            function cmdSearchEmp_old(){
                emp_number = document.frm_employee.EMP_NUMBER.value;
                emp_fullname = document.frm_employee.EMP_FULLNAME.value;
                emp_department = document.frm_employee.EMP_DEPARTMENT.value;
                emp_section = document.frm_employee.EMP_SECTION.value;
                emp_edit = document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HOD_EMPLOYEE_ID]%>.value;
                window.open("empsearch.jsp?emp_number=" + emp_number + "&emp_fullname=" + emp_fullname + "&emp_department=" + emp_department + "&emp_edit=" + emp_edit + "&emp_section=" + emp_section);

            }

            function cmdClearSearchEmp_old(){
                document.frm_employee.EMP_NUMBER.value = "";
                document.frm_employee.EMP_FULLNAME.value = "";
            }

            function cmdCopyPaste(oid) {

                document.frm_employee.command.value="<%=String.valueOf(Command.SAVE)%>";
                document.frm_employee.hidden_copy_status.value = true;
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }
            
            function cmdUpdateSec(){
                document.frm_employee.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }
            
            function cmdUpdatePos(){
                document.frm_employee.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }
            
            function cmdUpdateDiv(){
                document.frm_employee.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }
             function cmdUpdateDep(){
                document.frm_employee.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }
            
            function cmdEmpMutation(oidEmp){
                if(oidEmp==0){
                    alert("can't use Employee Mutation because you select create new employee");
                }else{
                document.frm_employee.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_employee.employee_oid.value="<%=oidEmployee%>";
                document.frm_employee.action="employee_mutation.jsp";
                document.frm_employee.submit();
               }
            }
            
             function cmdViewKadiv(employeeId){            
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/employee/outlet/kadiv_mapping.jsp?command=<%=(Command.EDIT)%>&FRM_FIELD_EMPLOYEE_ID="+employeeId; 
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }
            
           
            //}
            //-->
        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
            <!--

            function hideObjectForEmployee(){
            }

            function hideObjectForLockers(){
            }

            function hideObjectForCanteen(){
            }

            function hideObjectForClinic(){
            }

            function hideObjectForMasterdata(){
            }

            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.0
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                    if(!x && document.getElementById) x=document.getElementById(n); return x;
                }

                function MM_swapImage() { //v3.0
                    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                        if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                }
                
                //-->
        </SCRIPT>
        <!-- #EndEditable -->
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">


        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" -->
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td  bgcolor="#9BC1FF" height="10" valign="middle">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                                        <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                                        <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%}%>
                        <tr>
                            <td width="88%" valign="top" align="left">
                                <table width="100%" border="0" cellspacing="3" cellpadding="2">
                                    <tr>
                                        <td width="100%">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                                                            Employee Data<!-- #EndEditable --> </strong></font>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                                        <tr>
                                                                            <td valign="top"> <!-- #BeginEditable "content" -->
                                                                                <form name="frm_employee" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="start" value="<%=start%>">

                                                                                    <!-- Gede_21Nov2011 -->
                                                                                    <input type="hidden" name="hidden_goto_employee" value="<%=String.valueOf(gotoEmployee)%>">
                                                                                    <input type="hidden" name="hidden_copy_status">
                                                                                    <input type="hidden" name="employee_oid" value="<%=employee.getOID()%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_flag_cmd" value="<%=hidden_command%>">
                                                                                    
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">

                                                                                        <% if (oidEmployee != 0 || (employee!=null && employee.getOID()!=0) ) {
                                                                                            //karena keika save ini tidak muncul
                                                                                            if(oidEmployee==0 && employee!=null && employee.getOID()!=0){
                                                                                                oidEmployee = employee.getOID();
                                                                                            }
                                                                                         %>
                                                                                        
                                                                                        <tr>
                                                                                            <td>
                                                                                                <br>
                                                                                                <table width="98%" align="center" border="0" cellspacing="2" cellpadding="2" height="26">
                                                                                                    <tr>

                                                                                                        <%-- TAB MENU --%>
                                                                                                        <%-- active tab --%>
                                                                                                        <td width="11%" nowrap bgcolor="#66CCFF">
                                                                                                            <div align="center" class="tablink">
                                                                                                                <span class="tablink">Personal Data</span>
                                                                                                            </div>
                                                                                                        </td>

                                                                                                        <td width="11%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="7%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Language</a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="8%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="empeducation.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="8%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="9%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="careerpath.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="7%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="training.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="7%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="warning.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="8%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center" class="tablink">
                                                                                                                <a href="reprimand.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.REPRIMAND) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="6%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="award.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="7%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center"  class="tablink">
                                                                                                                <a href="picture.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PICTURE) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <td width="11%" nowrap bgcolor="#0066CC">
                                                                                                            <div align="center">
                                                                                                                <a href="doc_relevant.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS) %></a>
                                                                                                            </div>
                                                                                                        </td>
                                                                                                        <%-- END TAB MENU --%>

                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                        <tr>
                                                                                            <td   style="background-color:<%=bgColorContent%>; ">
                                                                                                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                                                    <tr>
                                                                                                        <td valign="top">
                                                                                                            <table style="border:1px solid <%=garisContent%>" width="100%" height="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                                                                <tr>
                                                                                                                    <td valign="top" width="50%">
                                                                                                                        <table width="100%" cellspacing="2" cellpadding="2">
                                                                                                                            <tr>
                                                                                                                                <td width="0%">&nbsp;</td>
                                                                                                                                <td width="42%" class="txtheading1">&nbsp;</td>
                                                                                                                                <td colspan="2" class="comment">
                                                                                                                                    <div align="left">*) entry
                                                                                                                                        required</div></td>
                                                                                                                                <td width="46%" class="txtheading1">&nbsp;</td>
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td colspan="2" rowspan="4" >
                                                                                                                                    <%
                                                                                                                                        String pictPath = "";
                                                                                                                                        try {
                                                                                                                                            SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                                                                                                                                            pictPath = sessEmployeePicture.fetchImageEmployee(employee.getOID());

                                                                                                                                        } catch (Exception e) {
                                                                                                                                            System.out.println("err." + e.toString());
                                                                                                                                        }%> <a href="picture.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%
                                                                                                                                             if (pictPath != null && pictPath.length() > 0) {
                                                                                                                                                out.println("<img width=\"100\"  src=\"" + approot + "/" + pictPath + "\"> Click here to upload");
                                                                                                                                             } else {
                                                                                                                                        %>
                                                                                                                                        <img width="140" height="150" src="<%=approot%>/imgcache/no_photo.JPEG"></image>
                                                                                                                                        <%

                                                                                                                                            }
                                                                                                                                        %> </a>                                                                                                                                </td>
                                                                                                                                <!--td height="20" nowrap></td-->
                                                                                                                               
                                                                                                                                <td height="20">Payroll 
                                                                                                                                    <%
                                                                                                                                    
                                                                                                                                    if(attdConfig != null && attdConfig.getConfigurasiInputEmpNum() == I_Atendance.CONFIGURATION_II_GENERATE_AUTOMATIC_EMPLOYEE_NUMBER){
                                                                                                                                    %>
                                                                                                                                    <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>" value="<%=employee.getEmployeeNum()==null || employee.getEmployeeNum().length()==0?"automatic":employee.getEmployeeNum()%>" class="formElemen" readonly="readonly">
                                                                                                                                    <%}else{%>
                                                                                                                                    <input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>" value="<%=employee.getEmployeeNum()%>" class="formElemen">
                                                                                                                                    <%}%>
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMPLOYEE_NUM)%></td>
                                                                                                                                <td height="20">Company</td>
                                                                                                                                <td height="20"><%
                                                                                                                                    Vector comp_value = new Vector(1, 1);
                                                                                                                                    Vector comp_key = new Vector(1, 1);
                                                                                                                                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                                                    comp_value.add(""+0);
                                                                                                                                    comp_key.add("select");
                                                                                                                                    for (int i = 0; i < listComp.size(); i++) {
                                                                                                                                        Company div = (Company) listComp.get(i);
                                                                                                                                        comp_key.add(div.getCompany());
                                                                                                                                        comp_value.add(String.valueOf(div.getOID()));
                                                                                                                                    }
                                                                                                                                    
                                                                                                                                    %>
                                                                                                                                    <!-- Ari_20110903
                                                                                                                                        Menambah Link menuju employee_mutation.jsp { -->
                                                                                                                                    <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + (employee.getCompanyId()!=0?employee.getCompanyId():companyId), comp_value, comp_key,"onChange=\"javascript:cmdUpdateDiv()\"")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_COMPANY_ID)%> <% if (employee.getResigned() != PstEmployee.YES_RESIGN) {%> <a href="javascript:cmdEmpMutation('<%=oidEmployee%>')" class="command" style="text-decoration:none">  Employee Mutation Form</a><%}%></td>
                                                                                                                                <!-- } -->
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <!--td width="0%" height="20">&nbsp;</td>
                                                                                                                                <td width="11%" height="20" nowrap></td-->
                                                                                                                                
                                                                                                                                  
                                                                                                                                <td width="31%" height="20">
                                                                                                                                    Fullname <input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]%>" value="<%=employee.getFullName()%>" class="formElemen" size="40">
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_FULL_NAME)%>                                                                                                                                </td>
                                                                                                                                <td width="58%" height="20">
                                                                                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div></td>
                                                                                                                                <td width="46%" height="20">
                                                                                                                                    <%
                                                                                                                                        Vector div_value = new Vector(1, 1);
                                                                                                                                        Vector div_key = new Vector(1, 1);
                                                                                                                                        //update by satrya 2013-09-07
                                                                                                                                           div_key.add("-select-");
                                                                                                                                            div_value.add("0");
                                                                                                                                            String strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  "" + (employee.getCompanyId()) ;//oidCompany; 
                                                                                                                                            Vector listDiv = PstDivision.list(0, 0, strWhere, " DIVISION ");
                                                                                                                                            boolean adaDiv=false;
                                                                                                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                                            Division div = (Division) listDiv.get(i);
                                                                                                                                            div_key.add(div.getDivision());
                                                                                                                                            div_value.add(String.valueOf(div.getOID()));
                                                                                                                                            if(employee.getDivisionId()==div.getOID()){
                                                                                                                                            adaDiv=true;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        
                                                                                                                                    %>
                                                                                                                                    <!-- Ari_20110903
                                                                                                                                        Menambah Link menuju employee_mutation.jsp { -->
                                                                                                                                    <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + (employee.getDivisionId()), div_value, div_key,"onChange=\"javascript:cmdUpdateDep()\"")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DIVISION_ID)%>                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <!--td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="11%"  valign="top"  >
                                                                                                                                </td-->
                                                                                                                                <td  width="31%"  valign="top">
                                                                                                                                    Address <input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS]%>" value="<%=employee.getAddress()%>" class="formElemen" size="45">
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_ADDRESS)%> 
                                                                                                                                    <input class="formElemen"  type="text" name="geo_address" readonly="true" value="<%=employee.getGeoAddress()%>" size="60" onClick="javascript:updateGeoAddress()" >

                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_COUNTRY_ID]%>" value="<%="" + employee.getAddrCountryId()%>" >
                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PROVINCE_ID]%>" value="<%="" + employee.getAddrProvinceId()%>" >
                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_REGENCY_ID]%>" value="<%="" + employee.getAddrRegencyId()%>" >
                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrSubRegencyId()%>" >
                                                                                                                                   
                                                                                                                                </td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <%
                                                                                                                                    if(employee.getCompanyId()==0 || adaDiv==false){  
                                                                                                                                     employee.setDivisionId(0); 
                                                                                                                                    }
                                                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                                                    dept_key.add("-select-");
                                                                                                                                    dept_value.add("0");
                                                                                                                                    String strWhereDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + "" + employee.getDivisionId(); //oidDivision;
                                                                                                                                    Vector listDept = PstDepartment.list(0, 0, strWhereDept, " DEPARTMENT ");
                                                                                                                                    boolean adaDept=false;
                                                                                                                                    for (int i = 0; i < listDept.size(); i++) {
                                                                                                                                    Department dept = (Department) listDept.get(i);
                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                    if(employee.getDepartmentId()==dept.getOID()){
                                                                                                                                               adaDept=true;
                                                                                                                                    }

                                                                                                                                    }

                                                                                                                                    //update by satrya 2013-09-07
                                                                                                                                    if(employee.getDivisionId()==0 || adaDept==false){ 
                                                                                                                                    //departementId=0;
                                                                                                                                    employee.setDepartmentId(0);
                                                                                                                                    }
                                                                                                                                    %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + (employee.getDepartmentId()), dept_value, dept_key,"onChange=\"javascript:cmdUpdateSec()\"")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DEPARTMENT_ID)%>                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <!--td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="11%"  valign="top" >
                                                                                                                                    <div align="left">Permanent Address</div></td-->
                                                                                                                                <td  width="31%"  valign="top"> Permanent Address
                                                                                                                                    <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_PERMANENT]%>" value="<%=(employee.getAddressPermanent() != null ? employee.getAddressPermanent() : "")%>" class="formElemen" size="45">
                                                                                                                                    <input class="formElemen"  type="text" name="geo_address_pmnt" readonly="true" value="<%=employee.getGeoAddressPmnt()%>" size="60" onClick="javascript:updateGeoAddressPmnt()"  >
                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_COUNTRY_ID]%>" value="<%="" + employee.getAddrPmntCountryId()%>" >
                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_PROVINCE_ID]%>" value="<%="" + employee.getAddrPmntProvinceId()%>" >
                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_REGENCY_ID]%>" value="<%="" + employee.getAddrPmntRegencyId()%>" >
                                                                                                                                    <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrPmntSubRegencyId()%>" >                                                                                                                                    </td>
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                          <%
                                                                                                                                        Vector sec_value = new Vector(1, 1);
                                                                                                                                        Vector sec_key = new Vector(1, 1);
                                                                                                                                        sec_key.add("- select -");
                                                                                                                                            sec_value.add("0");
                                                                                                                                            String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + employee.getDepartmentId();
                                                                                                                                        Vector listSec = PstSection.list(0, 0, strWhereSec, "SECTION");
                                                                                                                                        for (int i = 0; i < listSec.size(); i++) {
                                                                                                                                            Section sec = (Section) listSec.get(i);
                                                                                                                                            sec_key.add(sec.getSection());
                                                                                                                                            sec_value.add(String.valueOf(sec.getOID()));
                                                                                                                                        }
                                                                                                                                       
                                                                                                                                    %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECTION_ID], "formElemen", null, "" + (employee.getSectionId()), sec_value, sec_key,"onChange=\"javascript:cmdUpdatePos()\"")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_SECTION_ID)%>                                                                                                                                </td>
                                                                                                                            </tr>

                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Zip Code</div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSTAL_CODE]%>" value="<%=employee.getPostalCode() == 0 ? "" : "" + employee.getPostalCode()%>" class="formElemen"></td>
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                    <div align="left">Employee  Category</div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                            <%
                                                                                                                                        Vector ctg_value = new Vector(1, 1);
                                                                                                                                        Vector ctg_key = new Vector(1, 1);
                                                                                                                                        Vector listCtg = PstEmpCategory.listAll();
                                                                                                                                        for (int i = 0; i < listCtg.size(); i++) {
                                                                                                                                            EmpCategory ctg = (EmpCategory) listCtg.get(i);
                                                                                                                                            ctg_key.add(ctg.getEmpCategory());
                                                                                                                                            ctg_value.add(String.valueOf(ctg.getOID()));
                                                                                                                                        }
                                                                                                                                    %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID], null, "" + employee.getEmpCategoryId(), ctg_value, ctg_key)%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID)%>                                                                                                                        												</td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  ><!-- Gede_29Nov2011{-->
                                                                                                                                    <div align="left">Phone/Handphone</div></td>
                                                                                                                                <td width="31%"  valign="top">
                                                                                                                                    <table>
                                                                                                                                        <td  width="4%"  valign="top"><input type="text" size="15" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE]%>" value="<%=(employee.getPhone() != null ? employee.getPhone() : "")%>" class="formElemen"/>                                                                                                                                  </td>
                                                                                                                                        <td  width="7%"  valign="top" align="left">/<input type="text" size="15" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HANDPHONE]%>" value="<%=employee.getHandphone()%>" class="formElemen"/>                                                                                                                                </td>
                                                                                                                                    </table>                                                                                                                                </td><!--}-->
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                    <div align="left">Level</div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <%
                                                                                                                                        Vector lvl_value = new Vector(1, 1);
                                                                                                                                        Vector lvl_key = new Vector(1, 1);
                                                                                                                                        Vector listlvl = PstLevel.list(0, 0, "", " LEVEL ");
                                                                                                                                        for (int i = 0; i < listlvl.size(); i++) {
                                                                                                                                            Level lvl = (Level) listlvl.get(i);
                                                                                                                                            lvl_key.add(lvl.getLevel());
                                                                                                                                            lvl_value.add(String.valueOf(lvl.getOID()));
                                                                                                                                        }
                                                                                                                                        Vector gd_value = new Vector();
                                                                                                                                        Vector gd_key = new Vector();
                                                                                                                                        Vector listGradeLevel = PstGradeLevel.listAll(); 
                                                                                                                                        for (int i = 0; i < listGradeLevel.size(); i++) {
                                                                                                                                            GradeLevel gradeLevel = (GradeLevel) listGradeLevel.get(i);
                                                                                                                                            gd_key.add(gradeLevel.getCodeLevel());
                                                                                                                                            gd_value.add(String.valueOf(gradeLevel.getOID()));
                                                                                                                                        }
                                                                                                                                    %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LEVEL_ID], "formElemen", null, "" + employee.getLevelId(), lvl_value, lvl_key)%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_LEVEL_ID)%>
                                                                                                                                    <%if(attdConfig!=null && attdConfig.getConfigurationShowNoRekening()==I_Atendance.CONFIGURASI_I_VIEW_SHOW_ALL_CONFIGURATION_MINIMART){
                                                                                                                                    int SetGrade = Integer.valueOf(PstSystemProperty.getValueByName("USE_GRADE_SET")); 
                                                                                                                                    if (SetGrade==1) {   
                                                                                                                                    %>
                                                                                                                                    <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_GRADE_LEVEL_ID], "formElemen", null, "" + employee.getGradeLevelId(), gd_value, gd_key)%> 
                                                                                                                                    <%}
                                                                                                                                    }%>
                                                                                                                                     
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left"><!-- Gede_29Nov2011-->
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Emergency Phone/Person Name</div></td>
                                                                                                                                <td  width="31%"  valign="top">
                                                                                                                                    <table>
                                                                                                                                        <td  width="50%"  valign="top"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE_EMERGENCY]%>" value="<%=(employee.getPhoneEmergency() != null ? employee.getPhoneEmergency() : "")%>" class="formElemen">                                                                                                                                </td>                                                                                                                                
                                                                                                                                        <td  width="50%"  valign="top" align="left" >/<input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NAME_EMG]%>" value="<%=employee.getNameEmg()%>" class="formElemen">                                                                                                                                </td>
                                                                                                                                    </table>                                                                                                                                </td><!--}-->
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <%
                                                                                                                                        Vector pos_value = new Vector(1, 1);
                                                                                                                                        Vector pos_key = new Vector(1, 1);
                                                                                                                                        Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                                                                                        for (int i = 0; i < listPos.size(); i++) {
                                                                                                                                            Position pos = (Position) listPos.get(i);
                                                                                                                                            pos_key.add(pos.getPosition());
                                                                                                                                            pos_value.add(String.valueOf(pos.getOID()));
                                                                                                                                        }
                                                                                                                                    %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSITION_ID], "formElemen", null, "" + employee.getPositionId(), pos_value, pos_key)%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_POSITION_ID)%>
                                                                                                                                     </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left"><!-- Gede_29Nov2011-->
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Emergency Address</div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_EMG]%>" value="<%=employee.getAddressEmg()%>" class="formElemen" size="45">                                                                                                                                </td><!--}-->
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.COMMENCING_DATE) %></div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMMENCING_DATE], employee.getCommencingDate() == null ? new Date() : employee.getCommencingDate(), 0, -40, "formElemen")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_COMMENCING_DATE)%>                                                                                                                                </td>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Gender</div></td>
                                                                                                                                <td  width="31%"  valign="top"><% for (int i = 0; i < PstEmployee.sexValue.length; i++) {
                                                                                                                                        String str = "";
                                                                                                                                        if (employee.getSex() == PstEmployee.sexValue[i]) {
                                                                                                                                            str = "checked";
                                                                                                                                        }
                                                                                                                                    %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SEX]%>" value="<%="" + PstEmployee.sexValue[i]%>" <%=str%> style="border:none">
                                                                                                                                    <%=PstEmployee.sexKey[i]%> <% }%>
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_SEX)%>
                                                                                                                                    <!-- Locker -->
                                                                                                                                <!-- Probation End Date | Update 2015-01-09 | Hendra McHen -->
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                    <div align="left">Probation End Date</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROBATION_END_DATE], employee.getProbationEndDate() == null ? new Date() : employee.getProbationEndDate(), 0, -40, "formElemen")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_PROBATION_END_DATE)%> 
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Place of Birth</div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_PLACE]%>" value="<%=employee.getBirthPlace()%>" class="formElemen">
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_BIRTH_PLACE)%>                                                                                                                                </td>
                                                                                                                                
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                        <div align="left">Locker</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                        <%

                                                                                                                                                Vector locLocKey = new Vector(1, 1);
                                                                                                                                                Vector locLocValue = new Vector(1, 1);
                                                                                                                                                Vector listLockerLocation = PstLockerLocation.list(0, 0, "", "");
                                                                                                                                                locLocKey.add("select...");
                                                                                                                                                locLocValue.add("0");
                                                                                                                                                for (int i = 0; i < listLockerLocation.size(); i++) {
                                                                                                                                                        LockerLocation lockerlocation = (LockerLocation) listLockerLocation.get(i);
                                                                                                                                                        locLocKey.add(lockerlocation.getLocation());
                                                                                                                                                        locLocValue.add("" + lockerlocation.getOID());
                                                                                                                                                }
                                                                                                                                                //out.println(ControlCombo.draw(frmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCATION_ID],"formElemen",null,locker.getLocationId() != 0 ? ""+locker.getLocationId() : "",locLocValue,locLocKey));
                                                                                                                                                long locationId = 0;

                                                                                                                                                try {
                                                                                                                                                        if (employee.getLockerId() > 0) {
                                                                                                                                                                Locker lock = PstLocker.fetchExc(employee.getLockerId());
                                                                                                                                                                locationId = lock.getLocationId();
                                                                                                                                                        }
                                                                                                                                                } catch (Exception e) {
                                                                                                                                                        System.out.println("Exception EmployeeLockerId()"+e);
                                                                                                                                                }

                                                                                                                                                //out.println(ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCKER_ID],"formElemen",null,employee.getLockerId() != 0 ? ""+locationId : "",locLocValue,locLocKey));
                                                                                                                                                out.println(ControlCombo.draw("LOCKER_LOCATION", "formElemen", null, employee.getLockerId() != 0 ? "" + locationId : "", locLocValue, locLocKey, "onchange='javascript:cmdGetLocker()'"));

                                                                                                                                                Locker lockerEmp = new Locker();
                                                                                                                                                try {
                                                                                                                                                        if(employee.getLockerId()!=0)
                                                                                                                                                        lockerEmp = PstLocker.fetchExc(employee.getLockerId());
                                                                                                                                                } catch (Exception ex) {
                                                                                                                                                        System.out.println("Exception Loker"+ex);
                                                                                                                                                }

                                                                                                                                        %>
                                                                                                                                        <%--input type="text" name="<%=frmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCKER_NUMBER]%>" value="<%=locker.getLockerNumber()%>" class="formElemen" size="10"--%>
                                                                                                                                        <input type="text" name="LOCKER_NUMBER_POS" readonly value="<%=lockerEmp.getLockerNumber()%>" class="formElemen" size="10">
                                                                                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCKER_ID]%>" value="<%="" + employee.getLockerId()%>" >
                                                                                                                                        <%--input type="hidden" name="<%=frmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCKER_ID]%>" value="<%=""+employee.getLockerId()%>"--%>     
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Date of Birth</div></td>
                                                                                                                                <td  width="31%"  valign="top"><%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_DATE], employee.getBirthDate() == null ? new Date() : employee.getBirthDate(), 0, -75, "formElemen")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_BIRTH_DATE)%>
                                                                                                                                </td>
                                                                                                                             
                                                                                                                                <td  width="58%"  valign="top" height="29">
                                                                                                                                        <div align="left">Jamsostek Number</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top" height="29">
                                                                                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_NUM]%>" value="<%=employee.getAstekNum()%>" class="formElemen">
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Shio</div></td>
                                                                                                                                <td  width="31%"  valign="top">
                                                                                                                                    <input type="text" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SHIO]%>" value="<%=employee.getShio()%>" class="formElemen">*Auto</td>
                                                                                                                            
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                        <div align="left">Jamsostek Date</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_DATE], employee.getAstekDate() == null ? new Date() : employee.getAstekDate(), 1, -35, "formElemen")%> 
                                                                                                                                </td>
                                                                                                                             </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Elemen</div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ELEMEN]%>" value="<%=employee.getElemen()%>" class="formElemen">*Auto</td>
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                        <div align="left">BPJS Kesehatan NO.</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BPJS_NO]%>" value="<%=employee.getBpjs_no()!=null? employee.getBpjs_no():""%>" class="formElemen"></td>

                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top" height="29"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" height="29"  >
                                                                                                                                    <div align="left">Religion</div></td>
                                                                                                                                <td  width="31%"  valign="top" height="29"><% Vector relKey = new Vector(1, 1);
                                                                                                                                    Vector relValue = new Vector(1, 1);
                                                                                                                                    Vector listReligion = PstReligion.listAll();
                                                                                                                                    for (int i = 0; i < listReligion.size(); i++) {
                                                                                                                                        Religion religion = (Religion) listReligion.get(i);
                                                                                                                                        relKey.add(religion.getReligion());
                                                                                                                                        relValue.add("" + religion.getOID());
                                                                                                                                    }
                                                                                                                                    out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RELIGION_ID], "formElemen", null, "" + employee.getReligionId(), relValue, relKey));
                                                                                                                                    %>
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_RELIGION_ID)%>                                                                                                                                </td>
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                        <div align="left">BPJS Kesehatan Date.</div></td>
                                                                                                                                <td  width="46%"  valign="top"><%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BPJS_DATE], employee.getBpjs_date(), 1, -35, "formElemen")%> </td>

                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Marital
                                                                                                                                        Status  for HR</div></td>
                                                                                                                                <td  width="31%"  valign="top">
                                                                                                                                    <% Vector marKey = new Vector(1, 1);
                                                                                                                                        Vector marValue = new Vector(1, 1);
                                                                                                                                        Vector listMarital = PstMarital.list(0, 0, "", " MARITAL_STATUS ");
                                                                                                                                        marKey.add("-select-");
                                                                                                                                        marValue.add("0");
                                                                                                                                        for (int i = 0; i < listMarital.size(); i++) {
                                                                                                                                            Marital marital = (Marital) listMarital.get(i);
                                                                                                                                            marKey.add(marital.getMaritalStatus() + " - " + marital.getNumOfChildren());
                                                                                                                                            marValue.add("" + marital.getOID());
                                                                                                                                        }
                                                                                                                                        out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MARITAL_ID], "formElemen", null, "" + employee.getMaritalId(), marValue, marKey));
                                                                                                                                    %>
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_MARITAL_ID)%>                                                                                                                                
                                                                                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for Tax Report <% out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_TAX_MARITAL_ID], "formElemen", null, "" + employee.getTaxMaritalId(), marValue, marKey)); %> (Set per 1 January conform to Tax Regulation )
                                                                                                                                    * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_TAX_MARITAL_ID)%>
                                                                                                                                </td>
                                                                                                                                <!-- Status Pensiun Program | Update 2015-01-09 | Hendra McHen -->
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                        <div align="left">Status Pensiun Program</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <% for (int i = 0; i < PstEmployee.statusPensiunProgramValue.length; i++) {
                                                                                                                                            String strStPensiun = "";
                                                                                                                                            if (employee.getStatusPensiunProgram() == PstEmployee.statusPensiunProgramValue[i]) {
                                                                                                                                                    strStPensiun = "checked";
                                                                                                                                            }
                                                                                                                                    %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_STATUS_PENSIUN_PROGRAM]%>" value="<%="" + PstEmployee.statusPensiunProgramValue[i]%>" <%=strStPensiun%> style="border:'none'">
                                                                                                                                    <%=PstEmployee.statusPensiunProgramKey[i]%> <%}%> 
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left">Blood Type</div></td>
                                                                                                                                <td  width="31%"  valign="top"><%
                                                                                                                                    out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BLOOD_TYPE], "formElemen", null, employee.getBloodType(), PstEmployee.getBlood(), PstEmployee.getBlood()));
                                                                                                                                    %>                                                                                                                                </td>
                                                                                                                                <!-- Start Date Pensiun | Update 2015-01-09 | Hendra McHen -->
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                        <div align="left">Start Date Pensiun</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top"><%//=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DATE], employee.getResignedDate()==null?new Date():employee.getResignedDate(), +5, -10,"formElemen")%> 
                                                                                                                                        <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_START_DATE_PENSIUN], employee.getStartDatePensiun(), +5, -10, "formElemen")%>
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left">Race</div></td>
                                                                                                                                <td  width="31%"  valign="top"><%
                                                                                                                                    Vector race_key = new Vector();
                                                                                                                                    Vector race_value = new Vector();
                                                                                                                                    race_key.add("-select-");
                                                                                                                                    race_value.add(""+0);
                                                                                                                                    Vector listRace = PstRace.list(0, 0, "", PstRace.fieldNames[PstRace.FLD_RACE_NAME]);

                                                                                                                                    for (int i = 0; i < listRace.size(); i++) {
                                                                                                                                        Race race = (Race) listRace.get(i);
                                                                                                                                        race_key.add(race.getRaceName());
                                                                                                                                        race_value.add(String.valueOf(race.getOID()));
                                                                                                                                    }

                                                                                                                                    out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RACE], "formElemen", null, "" + employee.getRace(), race_value, race_key));
                                                                                                                                    %> </td>
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                        <div align="left">Resigned</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                        <% for (int i = 0; i < PstEmployee.resignValue.length; i++) {
                                                                                                                                                        String strRes = "";
                                                                                                                                                        if (employee.getResigned() == PstEmployee.resignValue[i]) {
                                                                                                                                                                strRes = "checked";
                                                                                                                                                        }
                                                                                                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED]%>" value="<%="" + PstEmployee.resignValue[i]%>" <%=strRes%> style="border:'none'">
                                                                                                                                        <%=PstEmployee.resignKey[i]%> <%}%> 
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left">Barcode Num </div></td>
                                                                                                                               
                                                                                                                                <td  width="31%"  valign="top"><input  type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BARCODE_NUMBER]%>" title=" If Employe is a Daily Worker (DL)  please replace 'DL-' with '12' ,for  example 'DL-333' become to '12333'.     If Employe's  Status  'Resigned'  please input the barcode number, barcode number is unique for example -R(BarcodeNumb/PinNumber)" value="<%=(employee.getBarcodeNumber() != null ? employee.getBarcodeNumber() : "")%>" class="formElemen">
                                                                                                                                    * <%=frmEmployee.getErrorMsgModif(FrmEmployee.FRM_FIELD_BARCODE_NUMBER)%>
                                                                                                                                      <%//=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_BARCODE_NUMBER)%><%//=(employee.getResigned() == PstEmployee.YES_RESIGN && employee.getBarcodeNumber() != null && employee.getBarcodeNumber().length() > 0 ? "<br><blink>YOU SHOULD CLEAR BARCODE FOR RESIGN EMPLOYEE<blink>" : "")%>
                                                                                                                                   </td>
                                                                                                                               
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                        <div align="left">Resigned Date</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top"><%//=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DATE], employee.getResignedDate()==null?new Date():employee.getResignedDate(), +5, -10,"formElemen")%> 
                                                                                                                                        <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DATE], employee.getResignedDate(), +5, -10, "formElemen")%>
                                                                                                                                </td>
                                                                                                                            </tr>

                                                                                                                            <!-- Ari_20110901_02
                                                                                                                            Menambah ID Card No.{ -->
                                                                                                                            
                                                                                                                            <!-- ganki priska memperbaharui 2014-12-07 -->
                                                                                                                            <tr>
                                                                                                                                <td height="20">&nbsp;</td>
                                                                                                                                <td height="20" nowrap>ID Card No. </td>
                                                                                                                                <td height="30"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_INDENT_CARD_NR]%>" value="<%=employee.getIndentCardNr()%>" class="formElemen">
                                                                                                                                    <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_INDENT_CARD_NR)%> Tipe <%
                                                                                                                                    out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ID_CARD_TYPE], "formElemen", null, employee.getIdcardtype(), PstEmployee.getId_Card_Type(), PstEmployee.getId_Card_Type()));
                                                                                                                                    %>                      </td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                        <div align="left">Resigned Reason </div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                        <% Vector resKey = new Vector(1, 1);
                                                                                                                                                Vector resValue = new Vector(1, 1);
                                                                                                                                                Vector listRes = PstResignedReason.list(0, 0, "", "RESIGNED_REASON");
                                                                                                                                                resKey.add("select...");
                                                                                                                                                resValue.add("0");
                                                                                                                                                for (int i = 0; i < listRes.size(); i++) {
                                                                                                                                                        ResignedReason resignedReason = (ResignedReason) listRes.get(i);
                                                                                                                                                        resKey.add(resignedReason.getResignedReason());
                                                                                                                                                        resValue.add("" + resignedReason.getOID());
                                                                                                                                                }
                                                                                                                                                out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_REASON_ID], "formElemen", null, "" + employee.getResignedReasonId(), resValue, resKey));
                                                                                                                                        %> 
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            
                                                                                                                            
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top"  >
                                                                                                                                    <div align="left"></div></td>
                                                                                                                                <td  width="31%"  valign="top"> valid to <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_INDENT_CARD_VALID_TO],
                                                                                                                                            employee.getIndentCardValidTo() == null ? new Date() : employee.getIndentCardValidTo(), 5, -25, "formElemen")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_INDENT_CARD_VALID_TO)%></td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>Resigned Description </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                        <textarea name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DESC]%>" class="formElemen" rows="2" cols="30"><%=employee.getResignedDesc()%></textarea> 
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            
                                                                                                                            <!-- } -->
                                                                                                                            <!-- Gede_15Nov2011 {-->


                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left">Email </div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" placeholder="e.g: jhon@domain.com" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMAIL_ADDRESS]%>" class="formElemen" size="40" value="<%=employee.getEmailAddress()%>" /> </td>
                                                                                                                                <%if(attdConfig!=null && attdConfig.getConfigurationOutletMiniMarket()==I_Atendance.CONFIGURASI_I_VIEW_SHOW_ALL_CONFIGURATION_MINIMART){%>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left">Kadiv Mapping</div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <a href="javascript:cmdViewKadiv('<%=oidEmployee%>')">Add Or View Kadiv Outlet</a>
                                                                                                                                </td>
                                                                                                                                <%}%>
                                                                                                                                
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td valign="top">&nbsp;</td>
                                                                                                                                <td colspan="2" valign="top">
                                                                                                                                   <!-- Medical Information | Update 2015-01-09 | Hendra McHen -->
                                                                                                                                    <table style="background-color: #eeeeee">
                                                                                                                                        <tr>
                                                                                                                                            <td colspan="2"><div style="background-color: #dddddd; padding:5px"><b>Medical Information:</b></div></td>
                                                                                                                                        </tr>
                                                                                                                                        <%
                                                                                                                                            String whereClauseMedical = " " + PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_SHOW_STATUS] + "=1 ";
                                                                                                                                            Vector listMedical = PstMedExpenseType.list(0, 0, whereClauseMedical, "");
                                                                                                                                            String whereClauseMType = "";
                                                                                                                                            // pecah data medical info
                                                                                                                                            Vector medicalData = new Vector(1, 1);
                                                                                                                                            String strMedical = employee.getMedicalInfo();
                                                                                                                                            if(employee.getMedicalInfo()!=null){
                                                                                                                                                
                                                                                                                                                for (String retval : strMedical.split(",")) {
                                                                                                                                                    medicalData.add(retval);
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            for (int k = 0; k < listMedical.size(); k++) {
                                                                                                                                                MedExpenseType medExTy = (MedExpenseType) listMedical.get(k);
                                                                                                                                                whereClauseMType = " " + PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID] + " = " + medExTy.getOID();
                                                                                                                                                Vector listMedicalType = PstMedicalType.list(0, 0, whereClauseMType, "");
                                                                                                                                        %>
                                                                                                                                        <tr>
                                                                                                                                            <td style="padding:3px"><%=medExTy.getType()%></td>
                                                                                                                                            <td style="padding:3px">
                                                                                                                                                <select name="medicalinfo">
                                                                                                                                                    <option value="0">-select-</option>
                                                                                                                                                    <%
                                                                                                                                                            
                                                                                                                                                            for (int j = 0; j < listMedicalType.size(); j++) {
                                                                                                                                                                MedicalType mty = (MedicalType) listMedicalType.get(j);
                                                                                                                                                                boolean option = false;
                                                                                                                                                                if(employee.getMedicalInfo()!=null){
                                                                                                                                                                    for(int h=0; h<medicalData.size(); h++){
                                                                                                                                                                        String strMed = (String) medicalData.get(h);
                                                                                                                                                                       if (strMed.equals(String.valueOf(mty.getOID()))) {
                                                                                                                                                                           option = true;
                                                                                                                                                                           %>
    }
                                                                                                                                                                           <option value="<%=mty.getOID()%>" selected="selected"><%=mty.getTypeName()%></option>
                                                                                                                                                                           <%
                                                                                                                                                                       }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                                if(option == false){
                                                                                                                                                                    
                                                                                                                                                    %>
                                                                                                                                                                
                                                                                                                                                                <option value="<%=mty.getOID()%>"><%=mty.getTypeName()%></option>
                                                                                                                                                    <%
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        
                                                                                                                                                    %>
                                                                                                                                                </select>
                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                        <%
                                                                                                                                            }
                                                                                                                                        %>
                                                                                                                                                
                                                                                                                                    </table>
                                                                                                                                </td>
                                                                                                                                <td  width="58%"  valign="top">
                                                                                                                                        <div align="left">Employee PIN</div>
                                                                                                                                </td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                        <input type="password" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_PIN]%>" value="<%=(employee.getEmpPin() != null ? employee.getEmpPin() : "")%>" class="formElemen">
                                                                                                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_PIN)%>&nbsp;                                                                                                                                
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                <div align="left">IQ</div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_IQ]%>" value="<%=(employee.getIq() != null ? employee.getIq() : "")%>" class="formElemen"></td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left">W. A. COMPANY</div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                   
                                                                                                                                    <%
                                                                                                                                    //priska 4-11-2014
                                                                                                                                    Vector wa_comp_value = new Vector(1, 1);
                                                                                                                                    Vector wa_comp_key = new Vector(1, 1);
                                                                                                                                    Vector listWa_Comp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                                                    wa_comp_value.add(""+0);
                                                                                                                                    wa_comp_key.add("select");
                                                                                                                                    for (int i = 0; i < listWa_Comp.size(); i++) {
                                                                                                                                        Company wacomp = (Company) listComp.get(i);
                                                                                                                                        wa_comp_key.add(wacomp.getCompany());
                                                                                                                                        wa_comp_value.add(String.valueOf(wacomp.getOID()));
                                                                                                                                    }
                                                                                                                                    
                                                                                                                                    %>
                                                                                                                                    <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_COMPANY_ID], "formElemen", null, "" + (employee.getWorkassigncompanyId()!=0?employee.getWorkassigncompanyId():companyId!=0?companyId:employee.getCompanyId()), wa_comp_value, wa_comp_key,"onChange=\"javascript:cmdUpdateDiv()\"")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_WORK_ASSIGN_COMPANY_ID)%> </td>
                                                                                                                              </tr>
                                                                                                                            
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                <div align="left">EQ</div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EQ]%>" value="<%=(employee.getEq() != null ? employee.getEq() : "")%>" class="formElemen"></td>
                                                                                                                                 <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left">W. A. DIVISION</div></td>
                                                                                                                               <td width="46%" height="20">
                                                                                                                                    <%
                                                                                                                                        Vector wa_div_value = new Vector(1, 1);
                                                                                                                                        Vector wa_div_key = new Vector(1, 1);
                                                                                                                                        //update by satrya 2013-09-07
                                                                                                                                            wa_div_key.add("-select-");
                                                                                                                                            wa_div_value.add("0");
                                                                                                                                            long xWACompany=employee.getWorkassigncompanyId()!=0?employee.getWorkassigncompanyId():companyId!=0?companyId:employee.getCompanyId();
                                                                                                                                            String strWherewadiv = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "='"+xWACompany+"'" ;//oidCompany; 
                                                                                                                                            Vector listWaDiv = PstDivision.list(0, 0, strWherewadiv, " DIVISION ");
                                                                                                                                            boolean adaWaDiv=false;
                                                                                                                                            for (int i = 0; i < listWaDiv.size(); i++) {
                                                                                                                                            Division wadiv = (Division) listWaDiv.get(i);
                                                                                                                                            wa_div_key.add(wadiv.getDivision());
                                                                                                                                            wa_div_value.add(String.valueOf(wadiv.getOID()));
                                                                                                                                            if(employee.getWorkassigndivisionId()==wadiv.getOID()){
                                                                                                                                            adaWaDiv=true;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        
                                                                                                                                    %>
                                                                                                                                    <!-- Priska menambahkan work assign 2014-11-3 -->
                                                                                                                                    <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DIVISION_ID], "formElemen", null, "" + (employee.getWorkassigndivisionId()!=0?employee.getWorkassigndivisionId():wadivisionId!=0?wadivisionId:employee.getDivisionId()), wa_div_value, wa_div_key,"onChange=\"javascript:cmdUpdateDep()\"")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_WORK_ASSIGN_DIVISION_ID)%>                                                                                                                                </td>
                                                                                                                            
                                                                                                                            
                                                                                                                            </tr>
                                                                                                                            <%
                                                                                                                            int showRekening=attdConfig.getConfigurationShowNoRekening(); 
                                                                                                                            if(showRekening==I_Atendance.CONFIGURASI_I_VIEW_SHOW_NO_REKENING){
                                                                                                                            %>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left">No Rekening </div></td>
                                                                                                                                <td  width="31%"  valign="top"><input type="text" disabled="disabled" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NO_REKENING]%>" class="formElemen" size="40" value="<%=employee.getNoRekening()%>" /> </td>
                                                                                                                                
                                                                                                                                 <% int SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
                                                                                                                                 if (SetLocation==1) {
                                                                                                                                 %>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                <div align="left">Location</div></td>
                                                                                                                                <td  width="46%"  valign="top">         
                                                                                                                                <%
                                                                                                                                 String CtrOrderClause = PstLocation.fieldNames[PstLocation.FLD_CODE];
                                                                                                                                 Vector vectLocation = PstLocation.list(0,0,"",CtrOrderClause);
                                                                                                                                 Vector val_Location = new Vector(1,1); //hidden values that will be deliver on request (oids) 
                                                                                                                                 Vector key_Location = new Vector(1,1); //texts that displayed on combo box
                                                                                                                                 val_Location.add("0");
                                                                                                                                 key_Location.add("All Location");
                                                                                                                                 for(int c = 0; c < vectLocation.size();c++){
                                                                                                                                 Location location = (Location)vectLocation.get(c);
                                                                                                                                 val_Location.add(""+location.getOID());
                                                                                                                                 key_Location.add(location.getName());
                                                                                                                                 }
                                                                                                                                 String select_complocation = ""+employee.getLocationId(); //selected on combo box
                                                                                                                                %>
                                                                                                                                <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCATION_ID], "formElemen", null, "" + employee.getLocationId(), val_Location, key_Location)%>  <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_LOCATION_ID)%>                                                                                                                           </td>
                                                                                                                                <% } %>   
                                                                                                                                
                                                                                                                               
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left">NPWP </div></td>
                                                                                                                                <td  width="31%"  valign="top"> <input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NPWP]%>" value="<%=(employee.getNpwp() != null ? employee.getNpwp() : "")%>" class="formElemen"></td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left">Ended Contract</div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_END_CONTRACT], employee.getEnd_contract() == null ? employee.getEnd_contract() : employee.getEnd_contract(), 0, 30, "formElemen")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_END_CONTRACT)%>
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left"> </div></td>
                                                                                                                                <td  width="31%"  valign="top"></td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left">W. A. DEPARTMENT</div></td>
                                                                                                                               <td  width="46%"  valign="top">
                                                                                                                                    <%
                                                                                                                                    if(employee.getWorkassigncompanyId()==0 || adaWaDiv==false){  
                                                                                                                                     employee.setWorkassigndivisionId(0); 
                                                                                                                                    }
                                                                                                                                    Vector wa_dept_value = new Vector(1, 1);
                                                                                                                                    Vector wa_dept_key = new Vector(1, 1);
                                                                                                                                    wa_dept_key.add("-select-");
                                                                                                                                    wa_dept_value.add("0");
                                                                                                                                    long WAdivision = employee.getWorkassigndivisionId()!=0?employee.getWorkassigndivisionId():wadivisionId!=0?wadivisionId:employee.getDivisionId();
                                                                                                                                    String strWherewaDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + WAdivision ; //oidDivision;
                                                                                                                                    Vector listwaDept = PstDepartment.list(0, 0, strWherewaDept, " DEPARTMENT ");
                                                                                                                                    boolean adawaDept=false;
                                                                                                                                    for (int i = 0; i < listwaDept.size(); i++) {
                                                                                                                                    Department wa_dept = (Department) listwaDept.get(i);
                                                                                                                                    wa_dept_key.add(wa_dept.getDepartment());
                                                                                                                                    wa_dept_value.add(String.valueOf(wa_dept.getOID()));
                                                                                                                                    if(employee.getWorkassigndepartmentId()==wa_dept.getOID()){
                                                                                                                                               adawaDept=true;
                                                                                                                                    }

                                                                                                                                    }

                                                                                                                                    //update by Priska 2014-11-06
                                                                                                                                    if(employee.getWorkassigndivisionId()==0 || adawaDept==false){ 
                                                                                                                                    //departementId=0;
                                                                                                                                    employee.setWorkassigndepartmentId(0);
                                                                                                                                    }
                                                                                                                                    %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DEPARTMENT_ID], "formElemen", null, "" + (employee.getWorkassigndepartmentId()!=0?employee.getWorkassigndepartmentId():wadepartementId!=0?wadepartementId:employee.getDepartmentId()), wa_dept_value, wa_dept_key,"onChange=\"javascript:cmdUpdateSec()\"")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_WORK_ASSIGN_DEPARTMENT_ID)%>
                                                                                                                               </td>
                                                                                                                                                                                                                                                 
                                                                                                                            </tr>
                                                                                                                            
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left"> </div></td>
                                                                                                                                <td  width="31%"  valign="top"></td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left">W. A. SECTION</div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                     <%
                                                                                                                                        Vector wa_sec_value = new Vector(1, 1);
                                                                                                                                        Vector wa_sec_key = new Vector(1, 1);
                                                                                                                                            wa_sec_key.add("- select -");
                                                                                                                                            wa_sec_value.add("0");
                                                                                                                                            
                                                                                                                                            long WAdepartment = employee.getWorkassigndepartmentId()!=0?employee.getWorkassigndepartmentId():wadepartementId!=0?wadepartementId:employee.getDepartmentId();
                                                                                                                                            String strWherewaSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + WAdepartment;
                                                                                                                                        Vector listwaSec = PstSection.list(0, 0, strWherewaSec, "SECTION");
                                                                                                                                        for (int i = 0; i < listwaSec.size(); i++) {
                                                                                                                                            Section wasec = (Section) listwaSec.get(i);
                                                                                                                                            wa_sec_key.add(wasec.getSection());
                                                                                                                                            wa_sec_value.add(String.valueOf(wasec.getOID()));
                                                                                                                                        }
                                                                                                                                       
                                                                                                                                    %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_SECTION_ID], "formElemen", null, "" + (employee.getWorkassignsectionId()!=0?employee.getWorkassignsectionId():employee.getSectionId()), wa_sec_value, wa_sec_key)%>  <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_WORK_ASSIGN_SECTION_ID)%>
                                                                                                                                </td>
                                                                                                                            
                                                                                                                            </tr>
                                                                                                                            
                                                                                                                            <tr align="left">
                                                                                                                                <td width="0%"  valign="top"  >&nbsp;</td>
                                                                                                                                <td width="42%"  valign="top" nowrap  >
                                                                                                                                    <div align="left"> </div></td>
                                                                                                                                <td  width="31%"  valign="top"></td>
                                                                                                                                <td  width="58%"  valign="top" nowrap>
                                                                                                                                    <div align="left">W. A. POSITION</div></td>
                                                                                                                                <td  width="46%"  valign="top">
                                                                                                                                    <%
                                                                                                                                        Vector wa_pos_value = new Vector(1, 1);
                                                                                                                                        Vector wa_pos_key = new Vector(1, 1);
                                                                                                                                        Vector listwaPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                                                                                        for (int i = 0; i < listwaPos.size(); i++) {
                                                                                                                                            Position wapos = (Position) listwaPos.get(i);
                                                                                                                                            wa_pos_key.add(wapos.getPosition());
                                                                                                                                            wa_pos_value.add(String.valueOf(wapos.getOID()));
                                                                                                                                        }
                                                                                                                                     
                                                                                                                                    %>
                                                                                                                                    <%=  ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_POSITION_ID], "formElemen", null, "" + (employee.getWorkassignpositionId()!=0?employee.getWorkassignpositionId():employee.getPositionId()), wa_pos_value, wa_pos_key) %> * <%= frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_WORK_ASSIGN_POSITION_ID) %>
                                                                                                                                
                                                                                                                                     
                                                                                                                                     </td>
                                                                                                                            </tr>
                                                                                                                            <%}%>
                                                                                                                            
                                                                                                                             
                                                                                                                            
                                                                                                                            

                                                                                                                            <tr align="left">
                                                                                                                                <td height="20">&nbsp;</td>
                                                                                                                                <td width="42%" valign="top" colspan="2">
                                                                                                                                    <table cellpadding="1" cellspacing="1" border="0">
                                                                                                                                        <tr>

                                                                                                                                            <td height="20" nowrap>Parents </td>
                                                                                                                                        </tr>
                                                                                                                                        <tr>

                                                                                                                                            <td width="11%"  valign="top" nowrap>
                                                                                                                                                <div align="left" nowrap>Father Name </div></td>
                                                                                                                                            <td  width="31%"  valign="top"><input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FATHER]%>" value="<%=employee.getFather()%>" class="formElemen">                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                        <tr>

                                                                                                                                            <td width="25"  valign="top"  >
                                                                                                                                                <div align="left" nowrap>Mother Name </div></td>
                                                                                                                                            <td  width="31%"  valign="top"><input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MOTHER]%>" value="<%=employee.getMother()%>" class="formElemen">                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                        <tr>

                                                                                                                                            <td width="11%"  valign="top"  >
                                                                                                                                                <div align="left" nowrap>Parents Address</div></td>
                                                                                                                                            <td  width="31%"  valign="top"><input type="text" disabled="disabled" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PARENTS_ADDRESS]%>" value="<%=employee.getParentsAddress()%>" class="formElemen" size="45">                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                    </table>                                                                                                                                </td>

                                                                                                                                                                                                                                                             </td>
                                                                                                                            </tr>
                                                                                                                            <tr align="left">
                                                                                                                                <!-- Presence Check Parameter | Update 2015-01-09 | Hendra McHen -->
                                                                                                                                <td valign="top" colspan="2">Presence Check Parameter : 
                                                                                                                                
                                                                                                                                    
                                                                                                                                    <% for (int i = 0; i < PstEmployee.presenceCheckValue.length; i++) {
                                                                                                                                            String strPresenceCheck = "";
                                                                                                                                            if (employee.getPresenceCheckParameter() == PstEmployee.presenceCheckValue[i]) {
                                                                                                                                                strPresenceCheck = "checked";
                                                                                                                                            }
                                                                                                                                                                                                                                                                               %> <input type="radio" disabled="disabled" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PRESENCE_CHECK_PARAMETER]%>" value="<%="" + PstEmployee.presenceCheckValue[i]%>" <%=strPresenceCheck%> style="border:'none'">
                                                                                                                                    <%=PstEmployee.presenceCheckKey[i]%> 
                                                                                                                                    <%}%>
                                                                                                                                    
                                                                                                                                    
                                                                                                                                </td>
                                                                                                                                <td valign="top">&nbsp;</td>
                                                                                                                                <td valign="top">&nbsp;</td>
                                                                                                                                <td valign="top">&nbsp;</td>
                                                                                                                                <td valign="top">&nbsp;</td>
                                                                                                                            </tr>
<tr align="left">
    <td width="0%"  valign="top"  >Default Schedule </td>
    <td colspan="2"  valign="top" nowrap  >
        <%
         String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
         String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
         Vector dftSchedules = PstDefaultSchedule.list(0, 35, whereClauseDS, orderDS);
         //Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);
        %>
       <table >
            <tr>
                <td>&nbsp;</td>
                <td>Sun</td>
                <td>Mon</td>
                <td>Tue</td>
                <td>Wed</td>
                <td>Thu</td>
                <td>Fri</td>
                <td>Sat</td>
            </tr>            
            <tr>
                <%
                String week="";
                if(schedulePerWeek!=0){
                    week= " Week ";
                }    
                %>
                <td> <%=week%> 1st </td>
                <%                    
                  for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %>
                    <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                    <%
                    }%>
            </tr>
            <!-- update by satrya 2013-04-08 -->
            <%if(schedulePerWeek!=0){%>
            <tr>
                <td> Week 2nd </td>
                <%                    
                  for(int idx=8;idx <= 14; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %>
                    <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                    <%
                    }%>
            </tr>
            <tr>
                <td> Week 3rd </td>
                <%                    
                  for(int idx=15;idx <= 21; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %>
                    <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                    <%
                    }%>
            </tr>
            <tr>
                <td> Week 4th </td>
                <%                    
                  for(int idx=22;idx <= 28; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %>
                    <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                    <%
                    }%>
            </tr>
            <tr>
                <td> Week 5th </td>
                <%                    
                  for(int idx=29;idx <=35; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %>
                    <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                    <%
                    }%>
            </tr>
            <%}%>
            <%if(schedulePerWeek!=0){%>
            <tr>
                <td>2nd2</td>
                    <%                    
                  for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %>
                    <td><input type="text" size="4" name="sche2_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule2()): "-" )%>" readonly="true" </td>
                    <%
                    }%>                
            </tr>
            <%}%>
        </table>
    </td>
    <td  width="58%"  valign="top" nowrap>
        <div align="left"></div></td>
    <td  width="46%"  valign="top"></td>                                                                                                                                </td>
</tr>


<!-- }-->
<tr align="left">
<td width="0%"  valign="top"  >&nbsp;</td>
<td width="42%"  valign="top" nowrap  >
    <div align="left"></div></td>
<td  width="31%"  valign="top"></td>
<td  width="58%"  valign="top" nowrap>
    <div align="left"></div></td>
<td  width="46%"  valign="top">                                                                                                                                </td>
</tr>
<tr align="left">
<td width="0%"  valign="top"  >&nbsp;</td>
<td colspan="4"  valign="top"  >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <% if(iErrCode!=FRMMessage.ERR_NONE  ){%>
        <tr>
            <td>
                <% 
                 int maxFld = FrmEmployee.fieldNames.length;
                 for(int idx=0;idx< maxFld;idx++)
                 {     
                    String msgX = frmEmployee.getErrorMsg(idx); 
                    if(msgX!=null && msgX.length()>0){
                       out.println( FrmEmployee.fieldNamesForUser[idx]+" = "+ frmEmployee.getErrorMsg(idx)+";");
                     }
                 }
                                    
                 //}
                
                %>
            </td>
        </tr>
        <%} %>
        <tr>
            <td> <%

                ctrLine.setLocationImg(approot + "/images");
                ctrLine.initDefault();
                ctrLine.setTableWidth("80");
                String scomDel = "javascript:cmdAsk('" + oidEmployee + "')";
                String sconDelCom = "javascript:cmdConfirmDelete('" + oidEmployee + "')";
                String scancel = "javascript:cmdEdit('" + oidEmployee + "')";
                ctrLine.setBackCaption("Back to List Employee");
                ctrLine.setCommandStyle("buttonlink");
                ctrLine.setDeleteCaption("Delete Employee");
                ctrLine.setAddCaption("Add Employee");
                ctrLine.setSaveCaption("Save Employee");

                if (privDelete) {
                    ctrLine.setConfirmDelCommand(sconDelCom);
                    ctrLine.setDeleteCommand(scomDel);
                    ctrLine.setEditCommand(scancel);
                } else {
                    ctrLine.setConfirmDelCaption("");
                    ctrLine.setDeleteCaption("");
                    ctrLine.setEditCaption("");
                }
                if (privAdd == false && privUpdate == false) {
                    ctrLine.setSaveCaption("");
                }
                if (privAdd == false) {
                    ctrLine.setAddCaption("");
                }

                if (iCommand == Command.EDIT || prevCommand == Command.EDIT) {
                    iCommand = Command.EDIT;
                }
                %> <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> </td>
        </tr>
        <tr>
            <td> <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <% if (privPrint) {%>
                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                        <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                        <td nowrap><a href="javascript:cmdPrint()" class="command" style="text-decoration:none">Print
                                Employee</a></td>
                        <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                        <td width="24"><a href="javascript:cmdPrintXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Export to Excel"></a></td>
                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                        <td nowrap><a href="javascript:cmdPrintXLS()" class="command" style="text-decoration:none">Export
                                to Excel</a></td>
                                <%}%>
                    </tr>
                </table></td>
        </tr>
    </table></td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>

                                                                                                </form>
                                                                                                <!-- #EndEditable -->
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td >&nbsp;</td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
                    </table>

                    </body>
                    <!-- #BeginEditable "script" -->
                    <!-- #EndEditable --> <!-- #EndTemplate -->
                    </html>

