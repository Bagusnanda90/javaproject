
<%
            /*
             * Page Name  		:  employee_mutation.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: lkarunia
             * @version  		: 01
             */

            /*******************************************************************
             * Page Description 	: [project description ... ]
             * Imput Parameters 	: [input parameter ...]
             * Output 			: [output ...]
             *******************************************************************/
%>
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
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%
            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            int hidden_command = FRMQueryString.requestInt(request, "hidden_flag_cmd");
            long oidCareerPath = FRMQueryString.requestLong(request, "career_path_oid");
            long oidEmpPicture = FRMQueryString.requestLong(request, "emp_picture_oid");
            String pictName = FRMQueryString.requestString(request, "pict");


            int recordToGet = 10;
            int iErrCode = FRMMessage.ERR_NONE;
            String errMsg = "";
            String whereClause = "";
            String orderClause = "";
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");

            Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
            Vector listSection = new Vector(1, 1);


            ControlLine ctrLine = new ControlLine();

            iErrCode = ctrlEmployee.action(iCommand, oidEmployee, request);

            errMsg = ctrlEmployee.getMessage();
            FrmEmployee frmEmployee = ctrlEmployee.getForm();

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



            Employee employee = new Employee();
            try {
                employee = PstEmployee.fetchExc(oidEmployee);
            } catch (Exception exc) {
                employee = new Employee();
            }

            if (iErrCode != 0 && iCommand == Command.SAVE) {
                employee = ctrlEmployee.getEmployee();
            }

            if (iCommand == Command.ADD) {
                employee = new Employee();
            }

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

            if ((iCommand == Command.SAVE) && (iErrCode == 0)) {
                iCommand = Command.EDIT;
            }
%>

<!-- End of Jsp Block -->
<html>
    <!-- #BeginTemplate "/Templates/maintab.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Employee</title>
        <script language="JavaScript">
            <!--

            function cmdPrint()        {
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailPdf?oid=<%=oidEmployee%>");
            }

            function cmdPrintXLS()        {
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailXLS?oid=<%=oidEmployee%>");
            }

            function cmdGetLocker(){
                strLockerLocation = document.frm_empmutation.LOCKER_LOCATION.value;
                if(strLockerLocation!=0){
                    window.open("srcLocker.jsp?<%=FrmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCATION]%>="+strLockerLocation, null, "height=500,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
                }else{
                    document.frm_empmutation.LOCKER_NUMBER_POS.value="";
                    document.frm_empmutation.<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCKER_ID]%>.value="";
                }

            }



            function cmdAdd()        {
                document.frm_empmutation.command.value="<%=Command.ADD%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }
            function cmdCancel()        {
                document.frm_empmutation.command.value="<%=Command.CANCEL%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdEdit(oid)        {
                document.frm_empmutation.command.value="<%=Command.EDIT%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdSave()        {
                document.frm_empmutation.command.value="<%=Command.SAVE%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdAsk(oid)        {
                document.frm_empmutation.command.value="<%=Command.ASK%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdConfirmDelete(oid)        {
                document.frm_empmutation.command.value="<%=Command.DELETE%>";
                document.frm_empmutation.action="employee_mutation.jsp";
                document.frm_empmutation.submit();
            }

            function cmdBack()        {
                document.frm_empmutation.command.value="<%=Command.BACK%>";
                //document.frm_empmutation.command.value="<%--=Command.LIST--%>";
                document.frm_empmutation.action="employee_edit.jsp";
                document.frm_empmutation.submit();
            }
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
                                        <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                                                    Employee &gt; Employee Editor<!-- #EndEditable --> </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                            <tr>
                                                                <td valign="top">
                                                                    <!-- #BeginEditable "content" -->
                                                                    <form name="frm_empmutation" method="post" action="">
                                                                        <input type="hidden" name="command" value="">
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                        <input type="hidden" name="career_path_oid" value="<%=oidCareerPath%>">
                                                                        <input type="hidden" name="department_oid" value="<%--=oidDepartment--%>">
                                                                        <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                        <input type="hidden" name="emp_picture_oid" value="<%--=oidEmpPicture--%>">
                                                                        <% if (oidEmployee != 0) {

                                                                                        try {
                                                                                            employee = PstEmployee.fetchExc(oidEmployee);
                                                                                        } catch (Exception exc) {
                                                                                            employee = new Employee();
                                                                                        }
                                                                        %>
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECTION_ID]%>" value="<%=employee.getSectionId()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>" value="<%=employee.getEmployeeNum()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID]%>" value="<%=employee.getEmpCategoryId()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]%>" value="<%=employee.getFullName()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS]%>" value="<%=employee.getAddress()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_PLACE]%>" value="<%=employee.getBirthPlace()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RELIGION_ID]%>" value="<%=employee.getReligionId()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_DATE]%>" value="<%=employee.getBirthDate()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMMENCING_DATE]%>" value="<%=employee.getCommencingDate()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BARCODE_NUMBER]%>" value="<%=employee.getBarcodeNumber()%>">
                                                                        <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_PIN]%>" value="<%=employee.getEmpPin()%>">

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
                                                                                        <%}%>
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
                                                                                                   
                                                                                                    <tr>
                                                                                                        <td width="5%" height="30">Start by</td>
                                                                                                        <td width="20%" height="30"></td>
                                                                                                        <td>&nbsp;</td>
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>

                                                                                                    <% if (iCommand == Command.NONE || (iCommand == Command.SAVE && frmCareerPath.errorSize() < 1) || iCommand == Command.DELETE || iCommand == Command.BACK
                                                                                                                        || iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST) {%>
                                                                                                    <% if (privAdd) {%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td colspan="2">
                                                                                                            <table widht=50%" cellpadding="0" cellspacing="0" border="0">

                                                                                                                <tr>
                                                                                                                    <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td width="10" height="25"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                    <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="20" valign="middle" colspan="3" width="1000"><a href="javascript:cmdAdd()" class="command">Add New Career</a></td>
                                                                                                                    <td width="10" height="25"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to Personal Data"></a></td>
                                                                                                                    <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="20" valign="middle" colspan="3" width="1000"><a href="javascript:cmdBack()" class="command">Back to Personal Data</a></td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                        <td colspan="2"></td>
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
                                                                                                        <td colspan="4"></td>
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
                                                                                                            <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + employee.getDepartmentId(), dept_value, dept_key)%>* <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%></td>
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
                                                                                                            %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSITION_ID], "formElemen", null, select_positionid, pos_value, pos_key)%>* <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_POSITION_ID)%> </td>
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
                                                                                                            %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + employee.getDivisionId(), div_value, div_key)%>* <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%></td>
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
                                                                                                            %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LEVEL_ID], "formElemen", null, "" + employee.getLevelId(), lvl_value, lvl_key)%>*<%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%> </td>
                                                                                                    </tr>


                                                                                                    <tr>
                                                                                                        <td width="5%" height="30">Start by</td>
                                                                                                        <td width="20%" height="30"></td>
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
                                                                                                                ctrLine.setTableWidth("80");
                                                                                                                ctrLine.setBackCaption("Back to List Employee");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setDeleteCaption("Delete Employee");
                                                                                                                ctrLine.setAddCaption("Add Employee");
                                                                                                                ctrLine.setSaveCaption("Save Employee");


                                                                                                                if (privAdd == false && privUpdate == false) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                }
                                                                                                                if (privAdd == false) {
                                                                                                                    ctrLine.setAddCaption("");
                                                                                                                }

                                                                                                                if (iCommand == Command.EDIT) {
                                                                                                                    iCommand = Command.EDIT;
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> </td>
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
    <!-- #BeginEditable "script" -->
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
