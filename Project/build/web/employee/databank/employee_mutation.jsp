<%--
    Document   : employee_mutation
    Created on : Sep 2, 2011, 4:51:16 PM
    Author     : Wiweka
--%>

<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmployeeMutation"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmployeeMutation"%>
<%@page import="com.dimata.harisma.form.employee.CtrlCareerPath"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmpPicture"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmpPicture"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployeeMutation"%>
<%@page import="com.dimata.harisma.form.employee.FrmCareerPath"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%
    CtrlEmployeeMutation ctrlEmployeeMutation = new CtrlEmployeeMutation(request);
    //CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int hidden_command = FRMQueryString.requestInt(request, "hidden_flag_cmd");
    long oidCareerPath = FRMQueryString.requestLong(request, "career_path_oid");
    long oidEmpPicture = FRMQueryString.requestLong(request, "emp_picture_oid");
    String pictName = FRMQueryString.requestString(request, "pict");
    /*Date dateFrom = FRMQueryString.requestDate(request,"date_from");
     Date dateTo = FRMQueryString.requestDate(request,"date_to");*/
    Date dateFrom = FRMQueryString.requestDate(request, FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM]);
    Date dateTo = FRMQueryString.requestDate(request, FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO]);

    long oidHistoryDept = 0;//FRMQueryString.requestLong(request, ""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]); 

    //update by satrya 2013-08-23
    long oidCompanyHidden = FRMQueryString.requestLong(request, FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_COMPANY_ID_MUTATION]);
    long oidDivisionHidden = FRMQueryString.requestLong(request, FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_DIVISION_ID_MUTATION]);
    long oidDepartementHidden = FRMQueryString.requestLong(request, FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_DEPARTMENT_ID_MUTATION]);
    long oidSectionHidden = FRMQueryString.requestLong(request, FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_SECTION_ID_MUTATION]);
    long oidLevelHidden = FRMQueryString.requestLong(request, FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_LEVEL_ID_MUTATION]);
    long oidPositionHidden = FRMQueryString.requestLong(request, FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_POSITION_ID_MUTATION]);
    long oidLocationHidden = FRMQueryString.requestLong(request, FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_LOCATION_ID_MUTATION]);

    int recordToGet = 10;
    int iErrCode = 0;
    String errMsg = "";
    String whereClause = "";
    String orderClause = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");

    //Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
    //Vector listSection = new Vector(1, 1);


    ControlLine ctrLine = new ControlLine();
    //iErrCode = ctrlEmployeeMutation.action(cmd, oidEmployee, request);
    iErrCode = ctrlEmployeeMutation.action(iCommand, oidEmployee, request, dateFrom, dateTo);


    errMsg = ctrlEmployeeMutation.getMessage();
    //FrmEmployee frmEmployee = ctrlEmployee.getForm();
    FrmEmployeeMutation frmEmployeeMutation = ctrlEmployeeMutation.getForm();

    //Sehubungan dengan CareerPath;

    CtrlCareerPath ctrlCareerPath = new CtrlCareerPath(request);
    if (iErrCode == FRMMessage.ERR_NONE) {
        iErrCode = ctrlCareerPath.action(iCommand, oidCareerPath, oidEmployee, request, "", 0);
        errMsg = ctrlCareerPath.getMessage();
    }

    FrmCareerPath frmCareerPath = ctrlCareerPath.getForm();
    CareerPath careerPath = ctrlCareerPath.getCareerPath();


    //Sehubungan dengan Picture;
    Vector listEmpPicture = new Vector(1, 1);
    CtrlEmpPicture ctrlEmpPicture = new CtrlEmpPicture(request);
    SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
    String pictPath = "";
    try {
        pictPath = sessEmployeePicture.fetchImageEmployee(oidEmployee);

    } catch (Exception e) {
        System.out.println("err." + e.toString());
    }
    System.out.println("pictPath sebelum..............." + pictPath);

    iErrCode = ctrlEmpPicture.action(iCommand, oidEmpPicture, oidEmployee);
    FrmEmpPicture frmEmpPicture = ctrlEmpPicture.getForm();
    EmpPicture empPicture = ctrlEmpPicture.getEmpPicture();

    long oidDepartment = 0;
    Employee employee = new Employee();
    try {
        employee = PstEmployee.fetchExc(oidEmployee);
        oidDepartment = employee.getDepartmentId();
    } catch (Exception exc) {
        employee = new Employee();
    }

    if (iErrCode != 0 && iCommand == Command.SAVE) {
        employee = ctrlEmployeeMutation.getEmployee();
    }

    if (iCommand == Command.ADD) {
        employee = new Employee();
    }


    String note = "";
    Vector vector = PstCareerPath.list(0, 1, PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee, "");
    if (vector != null && vector.size() > 0) {
        CareerPath careerpathnote = (CareerPath) vector.get(0);
        note = careerpathnote.getNote();
    }



    //----------------------------------------
    //locker;
    //FrmLocker frmLocker = ctrlEmployee.getFormLocker();
    //Locker locker = ctrlEmployee.getLocker();


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

    if ((iCommand == Command.SAVE) && (iErrCode == 0)) {
        iCommand = Command.EDIT;
    }
    Date LastworkDate = PstCareerPath.workDate(oidEmployee);
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HARISMA - Employee Mutation</title>
        <script language="JavaScript">
            
            function cmdUpdateDiv(){
                document.frm_empmutation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }
            function cmdUpdateDep(){
                document.frm_empmutation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }
            function cmdUpdatePos(){
                document.frm_empmutation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }
            function cmdAdd(){
                document.frm_empmutation.career_path_oid.value="0";
                document.frm_empmutation.command.value="<%=Command.ADD%>";
                document.frm_empmutation.prev_command.value="<%=Command.ADD%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdAsk(oidCareerPath){
                document.frm_empmutation.career_path_oid.value=oidCareerPath;
                document.frm_empmutation.command.value="<%=Command.ASK%>";
                document.frm_empmutation.prev_command.value="<%=prevCommand%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdConfirmDelete(oidCareerPath){
                document.frm_empmutation.career_path_oid.value=oidCareerPath;
                document.frm_empmutation.command.value="<%=Command.DELETE%>";
                document.frm_empmutation.prev_command.value="<%=prevCommand%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }
            function cmdSave(){
                document.frm_empmutation.command.value="<%=Command.SAVE%>";
                document.frm_empmutation.prev_command.value="<%=prevCommand%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdEdit(oidCareerPath){
                document.frm_empmutation.career_path_oid.value=oidCareerPath;
                document.frm_empmutation.command.value="<%=Command.EDIT%>";
                document.frm_empmutation.prev_command.value="<%=Command.EDIT%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdCancel(oidCareerPath){
                document.frm_empmutation.career_path_oid.value=oidCareerPath;
                document.frm_empmutation.command.value="<%=Command.EDIT%>";
                document.frm_empmutation.prev_command.value="<%=prevCommand%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdBackEmp(){
                document.frm_empmutation.command.value="<%=Command.BACK%>";
                document.frm_empmutation.action="employee_edit.jsp";
                document.frm_empmutation.submit();
            }
            function cmdBack(empOID){
                document.frm_empmutation.command.value="<%=Command.EDIT%>";
                document.frm_empmutation.action="employee_edit.jsp";
                document.frm_empmutation.submit();
            }

            function cmdUpdateSection(){
                document.frm_empmutation.command.value="<%= Command.GOTO%>";
                document.frm_empmutation.prev_command.value="<%= prevCommand%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdBackEmp(empOID){
                document.frm_empmutation.employee_oid.value=empOID;
                document.frm_empmutation.command.value="<%=Command.EDIT%>";
                document.frm_empmutation.action="employee_edit.jsp";
                document.frm_empmutation.submit();
            }


        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
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
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                <!-- #BeginEditable "contenttitle" -->
                                                Employee &gt; Employee Mutation Form<!-- #EndEditable -->
                                            </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td class="tablecolor" style="background-color:<%=bgColorContent%>; ">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frm_empmutation" method="post" action="employee_edit.jsp">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="career_path_oid" value="<%=oidCareerPath%>">
                                                                                    <input type="hidden" name="department_oid" value="<%=oidDepartment%>">
                                                                                    <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                                    <input type="hidden" name="emp_picture_oid" value="<%=oidEmpPicture%>">


                                                                                    <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <% if (oidEmployee != 0) {
                                                                                                                    //Employee employee = new Employee();
                                                                                                                    try {
                                                                                                                        employee = PstEmployee.fetchExc(oidEmployee);
                                                                                                                    } catch (Exception exc) {
                                                                                                                        employee = new Employee();
                                                                                                                    }
                                                                                                            %>
                                                                                                            <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="8%" rowspan="4"><%
                                                                                                                        if (pictPath != null && pictPath.length() > 0) {
                                                                                                                            out.println("<img width=\"100\" height=\"110\" src=\"" + approot + "/" + pictPath + "\">");
                                                                                                                        } else {
                                                                                                                        %>
                                                                                                                        <img width="100" height="110" src="<%=approot%>/imgcache/no_photo.JPEG">
                                                                                                                        <%

                                                                                                                            }
                                                                                                                        %></td>
                                                                                                                    <td width="15%" height="20" nowrap>Full Name</td>
                                                                                                                    <td width="1%" height="20" nowrap>:</td>
                                                                                                                    <td width="39% height="20"><%=employee.getFullName()%> </td>
                                                                                                                    <td width="5%" height="20" nowrap></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="15%" height="20" nowrap>Place & Date of Birth</td>
                                                                                                                    <td width="1%" height="20" nowrap>:</td>
                                                                                                                    <td width="39%" height="20"><%=employee.getBirthPlace()%>,<%=employee.getBirthDate()%></td>
                                                                                                                    <td width="5%" height="20" nowrap></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="15%" height="20" nowrap>Address</td>
                                                                                                                    <td width="1%" height="20" nowrap>:</td>
                                                                                                                    <td width="39%" height="20"><%=employee.getAddress()%></td>
                                                                                                                    <td width="5%" height="20" nowrap></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="15%" height="20" nowrap>Commencing Date</td>
                                                                                                                    <td width="1%" height="20" nowrap>:</td>
                                                                                                                    <td width="29%" height="20"><%=employee.getCommencingDate()%></td>
                                                                                                                    <td width="5%" height="20" nowrap></td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>

                                                                                                    <% if (iCommand == Command.NONE || (iCommand == Command.SAVE && frmCareerPath.errorSize() < 1) || iCommand == Command.DELETE || iCommand == Command.BACK
                                                                                                                || iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST) {%>
                                                                                                    <% if (privAdd) {%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>
                                                                                                            <table width=100%>
                                                                                                                <tr>
                                                                                                                    <td width="3" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td width="24" height="25"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                    <td width="3" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="30" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add New Career</a></td>
                                                                                                                    <td width="36" height="25"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to Personal Data"></a></td>
                                                                                                                    <td width="3" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="30" valign="middle" colspan="3" width="977"><a href="javascript:cmdBack()" class="command">Back to Personal Data</a></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <!--isi-->
                                                                                                                    <td height="30" colspan="9">
                                                                                                                        <table width="100%">
                                                                                                                            <% if (iCommand == Command.SAVE) {%>
                                                                                                                            <tr>
                                                                                                                                <td align="center">
                                                                                                                                    <div><font color="#000000" face="Arial" ><blink><big><strong>Data Saved !</strong></big></blink></font></div>                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <% }%>
                                                                                                                        </table>
                                                                                                                    </td>

                                                                                                                    <!-- isi -->
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <% }
                                                                                                        }%>


                                                                                                    <tr>
                                                                                                        <td><hr /></td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (iErrCode > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST) || (iCommand == Command.GOTO)) {%>

                                                                                                            <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td colspan="3"><font color="#FF6600" face="Arial"><strong>
                                                                                                                            <!-- #BeginEditable "contenttitle" -->
                                                                                                                            Current Career<!-- #EndEditable -->
                                                                                                                        </strong></font></td>
                                                                                                                    <td ><div align="left"><font color="blue" face="Arial"><i></i></font></div></td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td colspan="4">Note</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4"><textarea name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_NOTE]%>" class="elemenForm" cols="70" rows="3"><%=note%></textarea> *</td>
                                                                                                                </tr>                                                                                                                
                                                                                                                <tr>

                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30">Company</td>
                                                                                                                    <td width="20%" height="30">:
                                                                                                                        <%  //Vector listCompany = PstCompany.list(0, 0, "", "COMPANY");                                                                                                                            
                                                                                                                            Company empCompany = new Company();
                                                                                                                            try {
                                                                                                                                empCompany = PstCompany.fetchExc(employee.getCompanyId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getCompanyId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_COMPANY_ID]%>" />
                                                                                                                        <%=empCompany.getCompany()%></td>
                                                                                                                    <td width="5%" height="20">Level</td>
                                                                                                                    <td width="20%" height="20">:
                                                                                                                        <%
                                                                                                                            Level level = new Level();
                                                                                                                            try {
                                                                                                                                level = PstLevel.fetchExc(employee.getLevelId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }


                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getLevelId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID]%>" />
                                                                                                                        <%=level.getLevel()%></td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></td>
                                                                                                                    <td width="20%" height="30">:
                                                                                                                        <%
                                                                                                                            Division division = new Division();
                                                                                                                            try {
                                                                                                                                division = PstDivision.fetchExc(employee.getDivisionId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getDivisionId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID]%>" />
                                                                                                                        <%=division.getDivision()%></td>
                                                                                                                    <td width="5%" height="30">Position</td>
                                                                                                                    <td width="20%" height="30">:
                                                                                                                        <%
                                                                                                                            Position position = new Position();
                                                                                                                            try {
                                                                                                                                position = PstPosition.fetchExc(employee.getPositionId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }

                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getPositionId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID]%>" />
                                                                                                                        <%=position.getPosition()%></td>

                                                                                                                </tr>
                                                                                                                <tr>

                                                                                                                    <td width="5%" height="20" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></td>
                                                                                                                    <td width="15%" height="20">:
                                                                                                                        <%
                                                                                                                            Department department = new Department();
                                                                                                                            try {
                                                                                                                                department = PstDepartment.fetchExc(employee.getDepartmentId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getDepartmentId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]%>" />
                                                                                                                        <%=department.getDepartment()%></td>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.SECTION)%></td>
                                                                                                                    <td width="20%" height="30">:
                                                                                                                        <%
                                                                                                                            Section section = new Section();
                                                                                                                            try {
                                                                                                                                section = PstSection.fetchExc(employee.getSectionId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getSectionId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID]%>" />
                                                                                                                        <%=section.getSection()%></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap>Employee Category</td>
                                                                                                                    <td width="15%" height="20">:
                                                                                                                        <% Vector listEmpCategory = PstEmpCategory.list(0, 0, "", "EMP_CATEGORY");
                                                                                                                            String empCate = "-";
                                                                                                                            for (int i = 0; i < listEmpCategory.size(); i++) {
                                                                                                                                EmpCategory empCategory = (EmpCategory) listEmpCategory.get(i);

                                                                                                                                if (empCategory.getOID() == employee.getEmpCategoryId()) {
                                                                                                                                    empCate = empCategory.getEmpCategory();
                                                                                                                                }
                                                                                                                            }

                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getEmpCategoryId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID]%>" />
                                                                                                                        <%=empCate%></td>
                                                                                                                    <td width="5%" height="30" nowrap>Work Start</td>
                                                                                                                    <%
                                                                                                                        Date dateX = PstCareerPath.getNewWorkFromOrCommencingDate(employee.getOID());
                                                                                                                    %>
                                                                                                                    <td width="20%" height="30"><%=ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM], dateX, 5, -30, "formElemen")%>*</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap>Salary end by</td>
                                                                                                                    <td width="15%" height="20">  
                                                                                                                        <%

                                                                                                                            NumberFormat format = NumberFormat.getInstance();
                                                                                                                            format.setGroupingUsed(false);
                                                                                                                        %>
                                                                                                                        <input type="text" name="<%=frmCareerPath.fieldNames[frmCareerPath.FRM_FIELD_SALARY]%>" value="<%= format.format(careerPath.getSalary())%>"> * <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_SALARY)%></td>
                                                                                                                    <td width="5%" height="30" nowrap>End of Current by</td>
                                                                                                                    <td width="20%" height="30"><%=ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO], careerPath.getWorkTo() == null ? new Date() : careerPath.getWorkTo(), 5, -30, "formElemen")%>*</td>
                                                                                                                     </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30" nowrap>Contract From</td>
                                                                                                                    <%
                                                                                                                        Date dateContractFrom = PstCareerPath.getNewContractFromOrCommencingDate(employee.getOID());
                                                                                                                    %>
                                                                                                                    <td width="20%" height="30"><%=ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM], dateContractFrom, 5, -30, "formElemen")%>*</td>
                                                                                                                    <td width="5%" height="30" nowrap>Contract to</td>
                                                                                                                    <td width="20%" height="30"><%=ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO], employee.getEnd_contract() == null ? new Date() : employee.getEnd_contract(), 5, -30, "hidden")%>*</td>
                                                                                                                
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>

                                                                                                                <% int SetLocation = 1;
                                                                                                                    try {
                                                                                                                        SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET"));
                                                                                                                    } catch (Exception e) {
                                                                                                                    }
                                                                                                                    if (SetLocation == 1) {
                                                                                                                %>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap></td>
                                                                                                                    <td width="15%" height="20">  
                                                                                                                    </td>
                                                                                                                    <td width="5%" height="30" nowrap>Location</td>
                                                                                                                    <td width="15%" height="20">:
                                                                                                                        <%
                                                                                                                            Location location = new Location();
                                                                                                                            try {
                                                                                                                                location = PstLocation.fetchExc(employee.getLocationId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <input type="hidden" value ="<%=employee.getLocationId()%>" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LOCATION_ID]%>" />
                                                                                                                        <%=location.getName()%></td>
                                                                                                                </tr>
                                                                                                                <% }%>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>


                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td><hr /></td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td colspan="3"><font color="#FF6600" face="Arial"><strong>
                                                                                                                            <!-- #BeginEditable "contenttitle" -->
                                                                                                                            New Career<!-- #EndEditable -->
                                                                                                                        </strong></font></td>
                                                                                                                    <td ><div align="left"><font color="blue" face="Arial"><i>* ) entry required</i></font></div></td>
                                                                                                                </tr>
                                                                                                                <!--  <tr>
                                                                                                                    <td colspan="4">Reason</td>
                                                                                                                </tr>
                                                                                                                  <tr>
                                                                                                                    <td colspan="4"><textarea name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DESCRIPTION]%>" class="elemenForm" cols="70" rows="3"><%= careerPath.getDescription()%></textarea> *</td>
                                                                                                                </tr>   -->                                                                                                             

                                                                                                                
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap>Company</td>
                                                                                                                    <td width="15%" height="20"><%
                                                                                                                            Vector comp_value = new Vector(1, 1);
                                                                                                                            Vector comp_key = new Vector(1, 1);
                                                                                                                            String whereCompany = "";
                                                                                                                            if (!(isHRDLogin || isEdpLogin || isGeneralManager || isDirector)) {
                                                                                                                                whereCompany = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "='" + emplx.getCompanyId() + "'";
                                                                                                                            } else {
                                                                                                                                comp_value.add("0");
                                                                                                                                comp_key.add("select ...");
                                                                                                                            }
                                                                                                                            Vector listComp = PstCompany.list(0, 0, whereCompany, " COMPANY ");
                                                                                                                            for (int i = 0; i < listComp.size(); i++) {
                                                                                                                                Company comp = (Company) listComp.get(i);
                                                                                                                                comp_key.add(comp.getCompany());
                                                                                                                                comp_value.add(String.valueOf(comp.getOID()));
                                                                                                                            }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_COMPANY_ID_MUTATION], "formElemen", null, "" + oidCompanyHidden, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> * <%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_COMPANY_ID_MUTATION)%>
                                                                                                                    </td>
                                                                                                                    <td width="5%" height="30" nowrap>Level</td>
                                                                                                                    <td width="20%" height="30"><%
                                                                                                                        Vector lvl_value = new Vector(1, 1);
                                                                                                                        Vector lvl_key = new Vector(1, 1);
                                                                                                                        Vector listlvl = PstLevel.list(0, 0, "", " LEVEL ");
                                                                                                                        for (int i = 0; i < listlvl.size(); i++) {
                                                                                                                            Level lvl = (Level) listlvl.get(i);
                                                                                                                            lvl_value.add(String.valueOf(lvl.getOID()));
                                                                                                                            lvl_key.add(lvl.getLevel());
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_LEVEL_ID_MUTATION], "formElemen", null, "" +oidLevelHidden , lvl_value, lvl_key)%>*<%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_LEVEL_ID_MUTATION)%>
                                                                                                                   </td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="10%" height="30"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></td>
                                                                                                                    <td width="20%" height="30">
                                                                                                                        <%
                                                                                                                            Vector div_value = new Vector(1, 1);
                                                                                                                            Vector div_key = new Vector(1, 1);
                                                                                                                            String whereDivision = "";
                                                                                                                            if (!(isHRDLogin || isEdpLogin || isGeneralManager || isDirector)) {
                                                                                                                                whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "='" + emplx.getDivisionId() + "'";
                                                                                                                                oidDivisionHidden = emplx.getDivisionId();
                                                                                                                            } else {
                                                                                                                                div_value.add("0");
                                                                                                                                div_key.add("select ...");
                                                                                                                            }
                                                                                                                            if (whereDivision != null && whereDivision.length() > 0 && oidCompanyHidden != 0) {
                                                                                                                                whereDivision = " AND " + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + oidCompanyHidden;
                                                                                                                            } else if (oidCompanyHidden != 0) {
                                                                                                                                whereDivision = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + oidCompanyHidden;
                                                                                                                            }
                                                                                                                            Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
                                                                                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                                Division div = (Division) listDiv.get(i);
                                                                                                                                div_key.add(div.getDivision());
                                                                                                                                div_value.add(String.valueOf(div.getOID()));
                                                                                                                            }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_DIVISION_ID_MUTATION], "formElemen", null, "" + oidDivisionHidden, div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%>* <%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_DIVISION_ID_MUTATION)%>
                                                                                                                    </td>
                                                                                                                    <td width="10%" height="30">Position</td>
                                                                                                                    <td width="20%" height="30"> <%
                                                                                                                        Vector pos_value = new Vector(1, 1);
                                                                                                                        Vector pos_key = new Vector(1, 1);
                                                                                                                        Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                                                                        for (int i = 0; i < listPos.size(); i++) {
                                                                                                                            Position pos = (Position) listPos.get(i);
                                                                                                                            pos_value.add(String.valueOf(pos.getOID()));
                                                                                                                            pos_key.add(pos.getPosition());
                                                                                                                        }
                                                                                                                       
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_POSITION_ID_MUTATION], "formElemen", null, ""+oidPositionHidden, pos_value, pos_key)%>* <%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_POSITION_ID_MUTATION)%> </td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="10%" height="30"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></td>
                                                                                                                    <td width="20%" height="30">
                                                                                                                        <%
                                                                                                                            Vector dept_value = new Vector(1, 1);
                                                                                                                            Vector dept_key = new Vector(1, 1);

                                                                                                                            Vector listDept = new Vector();

                                                                                                                            if (processDependOnUserDept) {
                                                                                                                                if (emplx.getOID() > 0) {
                                                                                                                                    if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                                        String strWhere = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivisionHidden;
                                                                                                                                        dept_value.add("0");
                                                                                                                                        dept_key.add("select ...");
                                                                                                                                        listDept = PstDepartment.list(0, 0, strWhere, "DEPARTMENT");

                                                                                                                                    } else {
                                                                                                                                        position = new Position();
                                                                                                                                        try {
                                                                                                                                            position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                        } catch (Exception exc) {
                                                                                                                                        }

                                                                                                                                        String whereClsDep = "(((hr_department.DEPARTMENT_ID = " + departmentOid + ") "
                                                                                                                                                + "AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivisionHidden + ") OR "
                                                                                                                                                + "(hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + "=" + departmentOid + "))";

                                                                                                                                        if (position.getOID() != 0 && position.getDisabedAppDivisionScope() == 0) {
                                                                                                                                            whereClsDep = " ( hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivisionHidden + ") ";
                                                                                                                                        }

                                                                                                                                        Vector SectionList = new Vector();
                                                                                                                                        try {
                                                                                                                                            String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                                            Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                                            String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                                                                                                                                            Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);

                                                                                                                                            int grpIdx = -1;
                                                                                                                                            int maxGrp = depGroup == null ? 0 : depGroup.size();

                                                                                                                                            int grpSecIdx = -1;
                                                                                                                                            int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();

                                                                                                                                            int countIdx = 0;
                                                                                                                                            int MAX_LOOP = 10;
                                                                                                                                            int curr_loop = 0;

                                                                                                                                            int countIdxSec = 0;
                                                                                                                                            int MAX_LOOPSec = 10;
                                                                                                                                            int curr_loopSec = 0;

                                                                                                                                            do { // find group department belonging to curretn user base in departmentOid
                                                                                                                                                curr_loop++;
                                                                                                                                                String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                                                for (int g = 0; g < grp.length; g++) {
                                                                                                                                                    String comp = grp[g];
                                                                                                                                                    if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                                        grpIdx = countIdx;   // A ha .. found here                                       
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                countIdx++;
                                                                                                                                            } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit                            

                                                                                                                                            Vector idxSecGroup = new Vector();

                                                                                                                                            for (int x = 0; x < maxGrpSec; x++) {

                                                                                                                                                String[] grp = (String[]) depSecGroup.get(x);
                                                                                                                                                for (int j = 0; j < 1; j++) {

                                                                                                                                                    String comp = grp[j];
                                                                                                                                                    if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                                        Counter counter = new Counter();
                                                                                                                                                        counter.setCounter(x);
                                                                                                                                                        idxSecGroup.add(counter);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }

                                                                                                                                            for (int s = 0; s < idxSecGroup.size(); s++) {

                                                                                                                                                Counter counter = (Counter) idxSecGroup.get(s);

                                                                                                                                                String[] grp = (String[]) depSecGroup.get(counter.getCounter());

                                                                                                                                                Section sec = new Section();
                                                                                                                                                sec.setDepartmentId(Long.parseLong(grp[0]));
                                                                                                                                                sec.setOID(Long.parseLong(grp[2]));
                                                                                                                                                SectionList.add(sec);

                                                                                                                                            }

                                                                                                                                            // compose where clause
                                                                                                                                            if (grpIdx >= 0) {
                                                                                                                                                String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                                                for (int g = 0; g < grp.length; g++) {
                                                                                                                                                    String comp = grp[g];
                                                                                                                                                    whereClsDep = whereClsDep + " OR (j.DEPARTMENT_ID = " + comp + ")";
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            whereClsDep = " (" + whereClsDep + ") AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivisionHidden;
                                                                                                                                        } catch (Exception exc) {
                                                                                                                                            System.out.println(" Parsing Join Dept" + exc);
                                                                                                                                        }

                                                                                                                                        //dept_value.add("0");
                                                                                                                                        //dept_key.add("select ...");
                                                                                                                                        listDept = PstDepartment.list(0, 0, whereClsDep, "");

                                                                                                                                        for (int idx = 0; idx < SectionList.size(); idx++) {

                                                                                                                                            Section sect = (Section) SectionList.get(idx);

                                                                                                                                            long sectionOid = 0;

                                                                                                                                            for (int z = 0; z < listDept.size(); z++) {

                                                                                                                                                Department dep = new Department();

                                                                                                                                                dep = (Department) listDept.get(z);

                                                                                                                                                if (sect.getDepartmentId() == dep.getOID()) {

                                                                                                                                                    sectionOid = sect.getOID();

                                                                                                                                                }
                                                                                                                                            }

                                                                                                                                            if (sectionOid != 0) {

                                                                                                                                                Section lstSection = new Section();
                                                                                                                                                Department lstDepartment = new Department();

                                                                                                                                                try {
                                                                                                                                                    lstSection = PstSection.fetchExc(sectionOid);
                                                                                                                                                } catch (Exception e) {
                                                                                                                                                    System.out.println("Exception " + e.toString());
                                                                                                                                                }

                                                                                                                                                try {
                                                                                                                                                    lstDepartment = PstDepartment.fetchExc(lstSection.getDepartmentId());
                                                                                                                                                } catch (Exception e) {
                                                                                                                                                    System.out.println("Exception " + e.toString());
                                                                                                                                                }

                                                                                                                                                listDept.add(lstDepartment);

                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    dept_value.add("0");
                                                                                                                                    dept_key.add("select ...");
                                                                                                                                    listDept = PstDepartment.list(0, 0, ("hr_department."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivisionHidden), "DEPARTMENT");
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                dept_value.add("0");
                                                                                                                                dept_key.add("select ...");
                                                                                                                                listDept = PstDepartment.list(0, 0, ("hr_department."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivisionHidden), "DEPARTMENT");
                                                                                                                            }

                                                                                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                                                                                Department dept = (Department) listDept.get(i);
                                                                                                                                dept_key.add(dept.getDepartment());
                                                                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                            }



                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_DEPARTMENT_ID_MUTATION], "formElemen", null, "" + oidDepartementHidden, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>* <%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_DEPARTMENT_ID_MUTATION)%>
                                                                                                                    </td>
                                                                                                                    <td width="10%" height="30"><%=dictionaryD.getWord(I_Dictionary.SECTION)%></td>
                                                                                                                    <td width="20%" height="30"> 
                                                                                                                        <%
                                                                                                                            Vector sec_value = new Vector(1, 1);
                                                                                                                            Vector sec_key = new Vector(1, 1);
                                                                                                                            sec_value.add("0");
                                                                                                                            sec_key.add("select ...");
                                                                                                                            //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                                                                                            String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + oidDepartementHidden;
                                                                                                                            Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                                                                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                                                                                Section sec = (Section) listSec.get(i);
                                                                                                                                sec_key.add(sec.getSection());
                                                                                                                                sec_value.add(String.valueOf(sec.getOID()));
                                                                                                                            }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_SECTION_ID_MUTATION], "formElemen", null, "" + oidSectionHidden, sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%>

                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="10%" height="30">Employee Category</td>
                                                                                                                    <td width="20%" height="30"><%
                                                                                                                        Vector empCat_value = new Vector(1, 1);
                                                                                                                        Vector empCat_key = new Vector(1, 1);
                                                                                                                        Vector listEmpCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");
                                                                                                                        for (int i = 0; i < listEmpCat.size(); i++) {
                                                                                                                            EmpCategory empCategory = (EmpCategory) listEmpCat.get(i);
                                                                                                                            empCat_value.add(String.valueOf(empCategory.getOID()));
                                                                                                                            empCat_key.add(empCategory.getEmpCategory());
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_EMP_CATEGORY_ID_MUTATION], "formElemen", null, "" + employee.getEmpCategoryId(), empCat_value, empCat_key)%>*<%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_EMP_CATEGORY_ID_MUTATION)%></td>
                                                                                                                    <td width="5%" height="30" nowrap>End Contract</td>
                                                                                                                    <td width="20%" height="30"><%=ControlDate.drawDateWithStyle(FrmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_END_CONTRACT_MUTATION], employee.getEnd_contract() == null ? new Date() : employee.getEnd_contract(), 5, -30, "formElemen")%>*</td>
    
                                                                                                                    
                                                                                                                </tr>
                                                                                                                
                                                                                                                <% if (SetLocation == 1) {
                                                                                                                %>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap></td>
                                                                                                                    <td width="15%" height="20">  

                                                                                                                    </td> 


                                                                                                                    <td width="10%" height="30">Location</td>
                                                                                                                    <td width="20%" height="30">

                                                                                                                        <%
                                                                                                                            Vector loc_value = new Vector(1, 1);
                                                                                                                            Vector loc_key = new Vector(1, 1);
                                                                                                                            Vector listloc = PstLocation.list(0, 0, "", "LOCATION_ID");
                                                                                                                            for (int i = 0; i < listloc.size(); i++) {
                                                                                                                                Location loc = (Location) listloc.get(i);
                                                                                                                                loc_value.add(String.valueOf(loc.getOID()));
                                                                                                                                loc_key.add(loc.getName());
                                                                                                                            }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_LOCATION_ID_MUTATION], "formElemen", null, "" + employee.getLocationId(), loc_value, loc_key)%>*<%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_LOCATION_ID_MUTATION)%>


                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <% }%>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>

                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top" >
                                                                                                                    <td  widht="30%" colspan="3" class="command">
                                                                                                                        <%
                                                                                                                            ctrLine.setLocationImg(approot + "/images");
                                                                                                                            ctrLine.initDefault();
                                                                                                                            ctrLine.setTableWidth("80%");
                                                                                                                            String scomDel = "javascript:cmdAsk('" + oidCareerPath + "')";
                                                                                                                            String sconDelCom = "javascript:cmdConfirmDelete('" + oidCareerPath + "')";
                                                                                                                            String scancel = "javascript:cmdEdit('" + oidCareerPath + "')";
                                                                                                                            ctrLine.setBackCaption("Back to Personal Data");
                                                                                                                            ctrLine.setCommandStyle("buttonlink");
                                                                                                                            ctrLine.setAddCaption("Add New Career");
                                                                                                                            ctrLine.setSaveCaption("Save New Career");


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

                                                                                                                            if (iCommand == Command.GOTO) {
                                                                                                                                iCommand = prevCommand;
                                                                                                                            }


                                                                                                                        %>
                                                                                                                        <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> </td>
                                                                                                                    <!--<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                                    <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                                                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                                    <td nowrap> <a href="javascript:cmdBack()" class="command">Back
                                                                                                                            To Edit Employee</a></td>-->
                                                                                                                </tr>
                                                                                                                <td colspan="2">
                                                                                                                    <!-- update by devin 2014-02-05 -->

                                                                                                                </td>
                                                                                                            </table>
                                                                                                            <%}%>
                                                                                                            <%}%>


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
                                                    <td>&nbsp; </td>
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
            <tr>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </tr>
    </table>
</body>
</html>
