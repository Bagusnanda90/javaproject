<%--
    Document   : employee_mutation
    Created on : Sep 2, 2011, 4:51:16 PM
    Author     : Wiweka
--%>
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
<%@ page import = "com.dimata.harisma.form.employee.*" %>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
            CtrlEmployeeMutation ctrlEmployeeMutation = new CtrlEmployeeMutation(request);
            //CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            int hidden_command = FRMQueryString.requestInt(request, "hidden_flag_cmd");
            long oidCareerPath = FRMQueryString.requestLong(request, "career_path_oid");
            long oidEmpPicture = FRMQueryString.requestLong(request, "emp_picture_oid");
            String pictName = FRMQueryString.requestString(request, "pict");
            long oidHistoryDept = FRMQueryString.requestLong(request, FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]);


            int recordToGet = 10;
            int iErrCode = FRMMessage.ERR_NONE;
            String errMsg = "";
            String whereClause = "";
            String orderClause = "";
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");

            //Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
            //Vector listSection = new Vector(1, 1);


            ControlLine ctrLine = new ControlLine();

            iErrCode = ctrlEmployeeMutation.action(iCommand, oidEmployee, request);

            errMsg = ctrlEmployeeMutation.getMessage();
            //FrmEmployee frmEmployee = ctrlEmployee.getForm();
            FrmEmployeeMutation frmEmployeeMutation = ctrlEmployeeMutation.getForm();

            //Sehubungan dengan CareerPath;
            CtrlCareerPath ctrlCareerPath = new CtrlCareerPath(request);

            iErrCode = ctrlCareerPath.action(iCommand, oidCareerPath, oidEmployee, request);

            FrmCareerPath frmCareerPath = ctrlCareerPath.getForm();

            CareerPath careerPath = ctrlCareerPath.getCareerPath();

            int vectSize = PstCareerPath.getCount(whereClause);

            //Sehubungan dengan Picture;
            Vector listEmpPicture = new Vector(1, 1);
            CtrlEmpPicture ctrlEmpPicture = new CtrlEmpPicture(request);
            SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
            String pictPath = "";
            try {
                pictPath = sessEmployeePicture.fetchImagePeserta(oidEmployee);

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

            function cmdBack(){
                document.frm_empmutation.command.value="<%=Command.BACK%>";
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
                                                    <td class="tablecolor">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frm_empmutation" method="post" action="employee_mutation.jsp">
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
                                                                                                            <table widht="100%" cellpadding="0" cellspacing="0" border="0">
                                                                                                                <tr>
                                                                                                                    <td width="30%" height="30">
                                                                                                                        <table>
                                                                                                                            <tr>
                                                                                                                                <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                                <td width="10" height="25"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                                <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                                <td height="30" valign="middle" colspan="3" width="1000"><a href="javascript:cmdAdd()" class="command">Add New Career</a></td>
                                                                                                                                <td width="5" height="25"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to Personal Data"></a></td>
                                                                                                                                <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                                <td height="30" valign="middle" colspan="3" width="1000"><a href="javascript:cmdBack()" class="command">Back to Personal Data</a></td>
                                                                                                                            </tr>
                                                                                                                        </table>
                                                                                                                    </td>
                                                                                                                    <td  width="15%" height="30"></td>
                                                                                                                    <td width="15%" height="30"></td>
                                                                                                                    <td width="20%" height="30"></td>
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
                                                                                                            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmCareerPath.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST) || (iCommand == Command.GOTO)) {%>

                                                                                                            <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td colspan="4"><font color="#FF6600" face="Arial"><strong>
                                                                                                                                <!-- #BeginEditable "contenttitle" -->
                                                                                                                                Current Career<!-- #EndEditable -->
                                                                                                                            </strong></font></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"></td>
                                                                                                                    <td colspan="3"><div align="left"><font color="blue" face="Arial"><i>* ) entry required</i></font></div></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                    <td width="20%" height="30"><% Vector department_value = new Vector(1, 1);
                                                                                                                        Vector department_key = new Vector(1, 1);
                                                                                                                        Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                                        for (int i = 0; i < listDepartment.size(); i++) {
                                                                                                                            Department department = (Department) listDepartment.get(i);
                                                                                                                            department_value.add("" + department.getOID());
                                                                                                                            department_key.add(department.getDepartment());
                                                                                                                        }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + employee.getDepartmentId(), department_value, department_key, "readonly='true'")%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%></td>
                                                                                                                    <td width="5%" height="20">Position </td>
                                                                                                                    <td width="20%" height="20">
                                                                                                                        <% Vector position_value = new Vector(1, 1);
                                                                                                                            Vector position_key = new Vector(1, 1);
                                                                                                                            Vector listPosition = PstPosition.list(0, 0, "", "POSITION");
                                                                                                                            for (int i = 0; i < listPosition.size(); i++) {
                                                                                                                                Position position = (Position) listPosition.get(i);
                                                                                                                                position_value.add("" + position.getOID());
                                                                                                                                position_key.add(position.getPosition());
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID], "formElemen", null, "" + employee.getPositionId(), position_value, position_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_POSITION_ID)%> </td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                    <td width="20%" height="30"><%Vector division_value = new Vector(1, 1);
                                                                                                                        Vector division_key = new Vector(1, 1);
                                                                                                                        Vector listDivision = PstDivision.list(0, 0, "", "DIVISION");
                                                                                                                        for (int i = 0; i < listDivision.size(); i++) {
                                                                                                                            Division division = (Division) listDivision.get(i);
                                                                                                                            division_value.add("" + division.getOID());
                                                                                                                            division_key.add(division.getDivision());
                                                                                                                        }

                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + employee.getDivisionId(), division_value, division_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DIVISION_ID)%></td>
                                                                                                                    <td width="5%" height="30">Level</td>
                                                                                                                    <td width="20%" height="30">
                                                                                                                        <%   Vector level_value = new Vector(1, 1);
                                                                                                                            Vector level_key = new Vector(1, 1);
                                                                                                                            Vector listLevel = PstLevel.list(0, 0, "", "LEVEL");
                                                                                                                            for (int i = 0; i < listLevel.size(); i++) {
                                                                                                                                Level level = (Level) listLevel.get(i);
                                                                                                                                level_value.add("" + level.getOID());
                                                                                                                                level_key.add(level.getLevel());
                                                                                                                            }

                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID], "formElemen", null, "" + employee.getLevelId(), level_value, level_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_LEVEL_ID)%></td>

                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap>Start Date</td>
                                                                                                                    <td width="15%" height="20"><%=	ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM], LastworkDate == null ? employee.getCommencingDate() : LastworkDate, 5, -35, "formElemen")%></td>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                                                    <td width="20%" height="30">
                                                                                                                        <%   Vector section_value = new Vector(1, 1);
                                                                                                                            Vector section_key = new Vector(1, 1);
                                                                                                                            Vector listSection = PstSection.list(0, 0, "", "SECTION");
                                                                                                                            for (int i = 0; i < listSection.size(); i++) {
                                                                                                                                Section section = (Section) listSection.get(i);
                                                                                                                                section_value.add("" + section.getOID());
                                                                                                                                section_key.add(section.getSection());
                                                                                                                            }

                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID], "formElemen", null, "" + employee.getSectionId(), section_value, section_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_SECTION_ID)%></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap>Salary</td>
                                                                                                                    <td width="15%" height="20">
                                                                                                                        <%
                                                                                                                            NumberFormat format = NumberFormat.getInstance();
                                                                                                                            format.setGroupingUsed(false);
                                                                                                                        %>
                                                                                                                        <input type="text" name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SALARY]%>" value="<%= format.format(careerPath.getSalary())%>"> * <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_SALARY)%></td>
                                                                                                                    <td width="5%" height="30"></td>
                                                                                                                    <td width="20%" height="30"></td>
                                                                                                                </tr>

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
                                                                                                                    <td colspan="4"><font color="#FF6600" face="Arial"><strong>
                                                                                                                                <!-- #BeginEditable "contenttitle" -->
                                                                                                                                New Career<!-- #EndEditable -->
                                                                                                                            </strong></font></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30">&nbsp;</td>
                                                                                                                    <td colspan="3"><div align="left"><font color="blue" face="Arial"><i>* ) entry required</i></font></div></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">New Career Reason</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4"><textarea name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DESCRIPTION]%>" class="elemenForm" cols="35" rows="7"><%= careerPath.getDescription()%></textarea> *</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                    <td width="20%" height="30"><%
                                                                                                                        Vector dept_value = new Vector(1, 1);
                                                                                                                        Vector dept_key = new Vector(1, 1);
                                                                                                                        Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                                                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                                                                            Department dept = (Department) listDept.get(i);
                                                                                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                            dept_key.add(dept.getDepartment());
                                                                                                                        }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_DEPARTMENT_ID_MUTATION], "formElemen", null, "" + employee.getDepartmentId(), dept_value, dept_key)%>* <%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_DEPARTMENT_ID_MUTATION)%></td>
                                                                                                                    <td width="5%" height="20">Position </td>
                                                                                                                    <td width="20%" height="20">
                                                                                                                        <%
                                                                                                                            Vector pos_value = new Vector(1, 1);
                                                                                                                            Vector pos_key = new Vector(1, 1);
                                                                                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                                                                                Position pos = (Position) listPos.get(i);
                                                                                                                                pos_value.add(String.valueOf(pos.getOID()));
                                                                                                                                pos_key.add(pos.getPosition());
                                                                                                                            }
                                                                                                                            String select_positionid = "" + employee.getPositionId();
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_POSITION_ID_MUTATION], "formElemen", null, select_positionid, pos_value, pos_key)%>* <%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_POSITION_ID_MUTATION)%> </td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                    <td width="20%" height="30"><%
                                                                                                                        Vector div_value = new Vector(1, 1);
                                                                                                                        Vector div_key = new Vector(1, 1);
                                                                                                                        Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                        for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                            Division div = (Division) listDiv.get(i);
                                                                                                                            div_value.add(String.valueOf(div.getOID()));
                                                                                                                            div_key.add(div.getDivision());
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_DIVISION_ID_MUTATION], "formElemen", null, "" + employee.getDivisionId(), div_value, div_key)%>* <%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_DIVISION_ID_MUTATION)%></td>
                                                                                                                    <td width="5%" height="30">Level</td>
                                                                                                                    <td width="20%" height="30"> <%
                                                                                                                        Vector lvl_value = new Vector(1, 1);
                                                                                                                        Vector lvl_key = new Vector(1, 1);
                                                                                                                        Vector listlvl = PstLevel.list(0, 0, "", " LEVEL ");
                                                                                                                        for (int i = 0; i < listlvl.size(); i++) {
                                                                                                                            Level lvl = (Level) listlvl.get(i);
                                                                                                                            lvl_value.add(String.valueOf(lvl.getOID()));
                                                                                                                            lvl_key.add(lvl.getLevel());
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_LEVEL_ID_MUTATION], "formElemen", null, "" + employee.getLevelId(), lvl_value, lvl_key)%>*<%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_LEVEL_ID_MUTATION)%> </td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30">Start New Career</td>
                                                                                                                    <td width="20%" height="30"><%=ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO], careerPath.getWorkTo() == null ? new Date() : careerPath.getWorkTo(), 5, -30, "formElemen")%>*</td>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                                                    <td width="20%" height="30"> <%
                                                                                                                        Vector sec_value = new Vector(1, 1);
                                                                                                                        Vector sec_key = new Vector(1, 1);
                                                                                                                        Vector listsec = PstSection.list(0, 0, "", " SECTION ");
                                                                                                                        for (int i = 0; i < listsec.size(); i++) {
                                                                                                                            Section sec = (Section) listsec.get(i);
                                                                                                                            sec_value.add(String.valueOf(sec.getOID()));
                                                                                                                            sec_key.add(sec.getSection());
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmEmployeeMutation.fieldNames[FrmEmployeeMutation.FRM_FIELD_SECTION_ID_MUTATION], "formElemen", null, "" + employee.getSectionId(), sec_value, sec_key)%>*<%= frmEmployeeMutation.getErrorMsg(FrmEmployeeMutation.FRM_FIELD_SECTION_ID_MUTATION)%> </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top" >
                                                                                                                    <td colspan="2" class="command">
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
                                                                                                                </tr>

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
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
        </table>
    </body>
</html>
