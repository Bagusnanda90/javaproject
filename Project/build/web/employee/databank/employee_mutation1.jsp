<%--
    Document   : employee_mutation
    Created on : Sep 2, 2011, 4:51:16 PM
    Author     : Wiweka
--%>
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
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidCareerPath = FRMQueryString.requestLong(request, "career_path_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            long oidEmpPicture = FRMQueryString.requestLong(request, "emp_picture_oid");
            String pictName = FRMQueryString.requestString(request, "pict");
            long oidHistoryDept = FRMQueryString.requestLong(request, FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]);


//System.out.println("iCommand............."+iCommand);
/*variable declaration*/
            int recordToGet = 10;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee;
            String orderClause = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
            ControlLine ctrLine = new ControlLine();
            CtrlCareerPath ctrlCareerPath = new CtrlCareerPath(request);

            Vector listCareerPath = new Vector(1, 1);
            Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
            Vector listSection = new Vector(1, 1);


            /*switch statement */
            iErrCode = ctrlCareerPath.action(iCommand, oidCareerPath, oidEmployee, request);
            /* end switch*/
            FrmCareerPath frmCareerPath = ctrlCareerPath.getForm();
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

            /*switch statement */
            iErrCode = ctrlEmpPicture.action(iCommand, oidEmpPicture, oidEmployee);
            /* end switch*/
            FrmEmpPicture frmEmpPicture = ctrlEmpPicture.getForm();

            EmpPicture empPicture = ctrlEmpPicture.getEmpPicture();

            CareerPath careerPath = ctrlCareerPath.getCareerPath();
            msgString = ctrlCareerPath.getMessage();

            /*switch list CareerPath*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCareerPath == 0)) {
                start = PstCareerPath.findLimitStart(careerPath.getOID(), recordToGet, whereClause, orderClause);
            }

            /*count list All CareerPath*/
            int vectSize = PstCareerPath.getCount(whereClause);

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlCareerPath.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listCareerPath = PstCareerPath.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listCareerPath.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listCareerPath = PstCareerPath.list(start, recordToGet, whereClause, orderClause);
            }

            long oidDepartment = 0;
            if (oidEmployee != 0) {
                Employee employee = new Employee();
                try {
                    employee = PstEmployee.fetchExc(oidEmployee);
                    oidDepartment = employee.getDepartmentId();
                } catch (Exception exc) {
                    employee = new Employee();
                }
            }

            if (iCommand == Command.GOTO) {
                frmCareerPath = new FrmCareerPath(request, careerPath);
                frmCareerPath.requestEntityObject(careerPath);
            }

//listSection = PstSection.list(0,500,PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+ " = "+oidDepartment,"SECTION");

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HARISMA - Employee Mutation</title>
        <script language="JavaScript">
            function cmdAdd(){
                document.frmcareerpath.career_path_oid.value="0";
                document.frmcareerpath.command.value="<%=Command.ADD%>";
                document.frmcareerpath.prev_command.value="<%=Command.ADD%>";
                document.frmcareerpath.action="employee_mutation.jsp";
                document.frmcareerpath.submit();
            }

            function cmdAsk(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.ASK%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="employee_mutation.jsp";
                document.frmcareerpath.submit();
            }

            function cmdConfirmDelete(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.DELETE%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="employee_mutation.jsp";
                document.frmcareerpath.submit();
            }
            function cmdSave(){
                document.frmcareerpath.command.value="<%=Command.SAVE%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="employee_mutation.jsp";
                document.frmcareerpath.submit();
            }

            function cmdEdit(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.prev_command.value="<%=Command.EDIT%>";
                document.frmcareerpath.action="employee_mutation.jsp";
                document.frmcareerpath.submit();
            }

            function cmdCancel(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="employee_mutation.jsp";
                document.frmcareerpath.submit();
            }

            function cmdBack(){
                document.frmcareerpath.command.value="<%=Command.BACK%>";
                document.frmcareerpath.action="employee_edit.jsp";
                document.frmcareerpath.submit();
            }

            function cmdUpdateSection(){
                document.frmcareerpath.command.value="<%= Command.GOTO%>";
                document.frmcareerpath.prev_command.value="<%= prevCommand%>";
                document.frmcareerpath.action="employee_mutation.jsp";
                document.frmcareerpath.submit();
            }

            function cmdBackEmp(empOID){
                document.frmcareerpath.employee_oid.value=empOID;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.action="employee_edit.jsp";
                document.frmcareerpath.submit();
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
                                                                                <form name="frmcareerpath" method="post" action="employee_mutation.jsp">
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
                                                                                                            <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <% if (oidEmployee != 0) {
                                                                                                                                Employee employee = new Employee();
                                                                                                                                try {
                                                                                                                                    employee = PstEmployee.fetchExc(oidEmployee);
                                                                                                                                } catch (Exception exc) {
                                                                                                                                    employee = new Employee();
                                                                                                                                }
                                                                                                                %>
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
                                                                                                    <tr>
                                                                                                        <td><hr /></td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td colspan="4"><font color="#FF6600" face="Arial"><strong>
                                                                                                                                <!-- #BeginEditable "contenttitle" -->
                                                                                                                                Current Career<!-- #EndEditable -->
                                                                                                                            </strong></font></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                    <td width="15%" height="20">:
                                                                                                                        <% Division division = new Division();
                                                                                                                             try {
                                                                                                                                 division = PstDivision.fetchExc(employee.getDivisionId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 division = new Division();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=division.getDivision()%> </td>

                                                                                                                    <td width="5%" height="20" nowrap>Level</td>
                                                                                                                    <td width="15%" height="20">:
                                                                                                                        <% Level level = new Level();
                                                                                                                             try {
                                                                                                                                 level = PstLevel.fetchExc(employee.getLevelId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 level = new Level();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=level.getLevel()%> </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                    <td width="15%" height="20">:
                                                                                                                        <% Department department = new Department();
                                                                                                                             try {
                                                                                                                                 department = PstDepartment.fetchExc(employee.getDepartmentId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 department = new Department();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=department.getDepartment()%> </td>
                                                                                                                    <td width="5%" height="20" nowrap>Position</td>
                                                                                                                    <td width="15%" height="20">:
                                                                                                                        <% Position position = new Position();
                                                                                                                             try {
                                                                                                                                 position = PstPosition.fetchExc(employee.getPositionId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 position = new Position();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=position.getPosition()%> </td>
                                                                                                                </tr>
                                                                                                                <%}%>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="20" nowrap>Start Date</td>
                                                                                                                    <td width="15%" height="20">: <%=careerPath.getWorkTo()%></td>
                                                                                                                    <td width="5%" height="20" nowrap></td>
                                                                                                                    <td width="15%" height="20"></td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td height="8" align="left" colspan="4" class="listedittitle">
                                                                                                                        <span class="command">
                                                                                                                            <%
                                                                                                                                        int cmd = 0;
                                                                                                                                        if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                                                                                                || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                                                                                                            cmd = iCommand;
                                                                                                                                        } else {
                                                                                                                                            if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                                                                                                cmd = Command.FIRST;
                                                                                                                                            } else {
                                                                                                                                                if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCareerPath == 0)) {
                                                                                                                                                    cmd = PstCareerPath.findLimitCommand(start, recordToGet, vectSize);
                                                                                                                                                } else {
                                                                                                                                                    cmd = prevCommand;
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                            %>
                                                                                                                            <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                                        ctrLine.initDefault();
                                                                                                                            %> </span> </td>
                                                                                                                </tr>
                                                                                                                <% if (iCommand == Command.NONE || (iCommand == Command.SAVE && frmCareerPath.errorSize() < 1) || iCommand == Command.DELETE || iCommand == Command.BACK
                                                                                                                                    || iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST) {%>
                                                                                                                <% if (privAdd) {%>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td>
                                                                                                                        <table widht="100%" cellpadding="0" cellspacing="0" border="0">
                                                                                                                            <tr>
                                                                                                                                <td>&nbsp;</td>
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                                <td width="10" height="25"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                                <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                                <td height="30" valign="middle" colspan="3" width="1000"><a href="javascript:cmdAdd()" class="command">Add New Career</a></td>
                                                                                                                                <td width="10" height="25"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to Personal Data"></a></td>
                                                                                                                                <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                                <td height="30" valign="middle" colspan="3" width="1000"><a href="javascript:cmdBack()" class="command">Back to Personal Data</a></td>
                                                                                                                            </tr>
                                                                                                                        </table>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <% }
                                                                                                                            }%>


                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td><hr /></td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmCareerPath.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST) || (iCommand == Command.GOTO)) {%>

                                                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td colspan="4"><font color="#FF6600" face="Arial"><strong>
                                                                                                                                <!-- #BeginEditable "contenttitle" -->
                                                                                                                                New Career<!-- #EndEditable -->
                                                                                                                            </strong></font></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">New Career Reason</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4"><textarea name="<%=frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DESCRIPTION]%>" class="elemenForm" cols="35" rows="7"><%= careerPath.getDescription()%></textarea></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td colspan="4">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                    <td width="20%" height="30"><%   Vector department_value = new Vector(1, 1);
                                                                                                                        Vector department_key = new Vector(1, 1);

                                                                                                                        for (int i = 0; i < listDepartment.size(); i++) {
                                                                                                                            Department department = (Department) listDepartment.get(i);
                                                                                                                            department_value.add("" + department.getOID());
                                                                                                                            department_key.add(department.getDepartment());
                                                                                                                        }

                                                                                                                        String selDept = "" + careerPath.getDepartmentId();
                                                                                                                        if (careerPath.getDepartmentId() == 0) {
                                                                                                                            selDept = "" + oidHistoryDept;
                                                                                                                        }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, selDept, department_value, department_key, "onchange='javascript:cmdUpdateSection()'")%> * <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%></td>
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
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID], "formElemen", null, "" + careerPath.getPositionId(), position_value, position_key)%> * <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_POSITION_ID)%> </td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                    <td width="20%" height="30"><%   Vector division_value = new Vector(1, 1);
                                                                                                                        Vector division_key = new Vector(1, 1);
                                                                                                                        Vector listDivision = PstDivision.list(0, 0, "", "DIVISION");
                                                                                                                        for (int i = 0; i < listDivision.size(); i++) {
                                                                                                                            Division division = (Division) listDivision.get(i);
                                                                                                                            division_value.add("" + division.getOID());
                                                                                                                            division_key.add(division.getDivision());
                                                                                                                        }

                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + careerPath.getDivisionId(), division_value, division_key)%> * <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%></td>
                                                                                                                    <td width="5%" height="30">Level</td>
                                                                                                                    <td width="20%" height="30"><%   Vector level_value = new Vector(1, 1);
                                                                                                                        Vector level_key = new Vector(1, 1);
                                                                                                                        Vector listLevel = PstLevel.list(0, 0, "", "LEVEL");
                                                                                                                        for (int i = 0; i < listDivision.size(); i++) {
                                                                                                                            Level level = (Level) listLevel.get(i);
                                                                                                                            level_value.add("" + level.getOID());
                                                                                                                            level_key.add(level.getLevel());
                                                                                                                        }

                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID], "formElemen", null, "" + careerPath.getLevelId(), level_value, level_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%></td>

                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="5%" height="30">Start by</td>
                                                                                                                    <td width="20%" height="30"><%=	ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM], careerPath.getWorkFrom() == null ? new Date() : careerPath.getWorkFrom(), 5, -30, "formElemen")%> to
                                                                                                                        <%=	ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO], careerPath.getWorkTo() == null ? new Date() : careerPath.getWorkTo(), 5, -35, "formElemen")%> *
                                                                                                                        <% String strStart = frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_WORK_FROM);
                                                                                                                            String strEnd = frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_WORK_TO);
                                                                                                                            if ((strStart.length() > 0) && (strEnd.length() > 0)) {
                                                                                                                        %>
                                                                                                                        <%= strStart%>
                                                                                                                        <%} else {
                                                                                                                                                                                                   if ((strStart.length() > 0) || (strEnd.length() > 0)) {%>
                                                                                                                        <%= strStart.length() > 0 ? strStart : strEnd%>
                                                                                                                        <% }
                                                                  }%></td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
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
                                                                                                                        <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
                                                                                                                </tr>
                                                                                                                <tr>

                                                                                                                </tr>
                                                                                                            </table>
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
