
<%
            /*
             * Page Name  		:  ovdetail.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: wiweka
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
<%@ page import = "java.text.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.entity.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.overtime.*" %>
<%@ page import = "com.dimata.harisma.form.overtime.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%!    public String drawList(int iCommand, Vector objectClass, FrmOvertimeDetail frmObject, OvertimeDetail objEntity, long overtimeDetailId) {

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No.", "3%");
        ctrlist.addHeader("Payroll", "10%");
        ctrlist.addHeader("Name", "15%");
        ctrlist.addHeader("Start Date", "15%");
        ctrlist.addHeader("Start Time", "7%");
        ctrlist.addHeader("End Date", "15%");
        ctrlist.addHeader("End Time", "7%");
        ctrlist.addHeader("JobDesk", "20%");
        ctrlist.addHeader("Status ", "10%");
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEditOv('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        Vector rowx = new Vector();
        int index = -1;
        int recordNo = 1;

        //untuk mengambil Status
        Vector obj_status = new Vector(1, 1);
        Vector val_status = new Vector(1, 1);
        Vector key_status = new Vector(1, 1);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);


        System.out.println("objectClass" + objectClass.size());

        if (objectClass != null && objectClass.size() > 0) {
            Employee employee = new Employee();
            Overtime overtime = new Overtime();

            for (int i = 0; i < objectClass.size(); i++) {
                OvertimeDetail overtimeDetail = (OvertimeDetail) objectClass.get(i);
                
                try {
                    overtime = PstOvertime.fetchExc(overtimeDetail.getOvertimeId());
                } catch (Exception e) {
                }


                if (overtimeDetailId == overtimeDetail.getOID()) {
                    index = i;
                }

                rowx = new Vector();
                if ((index == i) && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                    rowx.add(String.valueOf(recordNo++));
                    rowx.add("<input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_EMPLOYEE_ID] + "\" value=\"" + overtimeDetail.getEmployeeId()
                            + "\"><input type=\"text\" size=\"15\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_PAYROLL] + "\" value=\"" + overtimeDetail.getPayroll() + "\" class=\"formElemen\" size=\"20\"onkeydown=\"javascript:keyDownCheck(event)\"><a href=\"javascript:cmdCheck()\">CHK</a>");
                    rowx.add( "\"><input type=\"text\" size=\"15\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_NAME] + "\" value=\"" + overtimeDetail.getName() + "\" class=\"formElemen\" readOnly>");
                    rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], overtimeDetail.getDateFrom() != null ? overtimeDetail.getDateFrom() : new Date(), "formElemen", 1, -5));
                    rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], overtimeDetail.getDateFrom() != null ? overtimeDetail.getDateFrom() : new Date(), "formElemen", 24, 1, 0));
                    rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], overtimeDetail.getDateTo() != null ? overtimeDetail.getDateTo() : new Date(), "formElemen", 1, -5));
                    rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], overtimeDetail.getDateTo() != null ? overtimeDetail.getDateTo() : new Date(), "formElemen", 24, 1, 0));
                    rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_JOBDESK] + "\" value=\"" + overtimeDetail.getJobDesk() + "\" class=\"formElemen\" size=\"25\">");
                    rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_STATUS], null, "" + overtimeDetail.getStatus(), val_status, key_status));

                } else {
                    String startTime = Formater.formatDate(overtimeDetail.getDateFrom(), "HH:mm");
                    String endTime = Formater.formatDate(overtimeDetail.getDateTo(), "HH:mm");

                    rowx.add(String.valueOf(recordNo++));
                    rowx.add("<a href=\"javascript:cmdEditOv('" + String.valueOf(overtimeDetail.getOID()) + "')\">" + overtimeDetail.getPayroll() + "</a>");
                    rowx.add(String.valueOf(overtimeDetail.getName()));
                    rowx.add(Formater.formatDate(overtimeDetail.getDateFrom(), "dd-MM-yyyy"));
                    rowx.add(startTime);
                    rowx.add(Formater.formatDate(overtimeDetail.getDateTo(), "dd-MM-yyyy"));
                    rowx.add(endTime);
                    rowx.add(String.valueOf(overtimeDetail.getJobDesk()));
                    String select_status = "" + overtimeDetail.getStatus();
                    if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_DRAFT) {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);
                    } else if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED) {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);
                    } else if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_FINAL) {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                    } else {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);
                    }

                }
                lstData.add(rowx);
            }
            rowx = new Vector();


            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)) {
                rowx.add(String.valueOf(recordNo++));
                rowx.add("<input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_EMPLOYEE_ID] + "\" value=\"" + objEntity.getEmployeeId()
                            + "\"> <input type=\"text\" name=\"" + frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_PAYROLL] + "\" value=\"" + objEntity.getPayroll() + "\" class=\"formElemen\" size=\"20\"onkeydown=\"javascript:keyDownCheck(event)\"><a href=\"javascript:cmdCheck()\">CHK</a>");
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_NAME] + "\" value=\"" + objEntity.getName() + "\" class=\"formElemen\" size=\"20\">");
                rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], objEntity.getDateFrom() != null ? objEntity.getDateFrom() : new Date(), "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], objEntity.getDateFrom() != null ? objEntity.getDateFrom() : new Date(), "formElemen", 24, 1, 0));
                rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], objEntity.getDateTo() != null ? objEntity.getDateTo() : new Date(), "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], objEntity.getDateTo() != null ? objEntity.getDateTo() : new Date(), "formElemen", 24, 1, 0));
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_JOBDESK] + "\"" + objEntity.getJobDesk() + "\" class=\"formElemen\" size=\"25\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_STATUS], null, "" + objEntity.getStatus(), val_status, key_status));

            }
            lstData.add(rowx);

        } else {
            if (iCommand == Command.ADD) {
                rowx.add(String.valueOf(recordNo++));
                rowx.add("<input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_EMPLOYEE_ID] + "\" value=\"" + objEntity.getEmployeeId()
                            + "\"> <input type=\"text\" name=\"" + frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_PAYROLL] + "\" value=\"" + objEntity.getPayroll() + "\" class=\"formElemen\" size=\"20\"onkeydown=\"javascript:keyDownCheck(event)\"><a href=\"javascript:cmdCheck()\">CHK</a>");
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_NAME] + "\" value=\"" + objEntity.getName() + "\" class=\"formElemen\" size=\"20\">");
                rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], objEntity.getDateFrom() != null ? objEntity.getDateFrom() : new Date(), "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], objEntity.getDateFrom() != null ? objEntity.getDateFrom() : new Date(), "formElemen", 24, 1, 0));
                rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], objEntity.getDateTo() != null ? objEntity.getDateTo() : new Date(), "formElemen", 1, -5));
                rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], objEntity.getDateTo() != null ? objEntity.getDateTo() : new Date(), "formElemen", 24, 1, 0));
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_JOBDESK] + "\"" + objEntity.getJobDesk() + "\" class=\"formElemen\" size=\"25\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOvertimeDetail.FRM_FIELD_STATUS], null, "" + objEntity.getStatus(), val_status, key_status));

                lstData.add(rowx);

            }
        }
        return ctrlist.draw(index);
    }

%>

<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidOvertime = FRMQueryString.requestLong(request, "overtime_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            long oidOvt_Employee = FRMQueryString.requestLong(request, "ovtEmployee_oid");
            long oidOvertimeDetail = FRMQueryString.requestLong(request, "hidden_overtime_detail_id");

            long oidCompany = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID]);
            long oidDivision = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID]);
            long oidDepartment = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID]);
            long oidSection = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID]);

            int recordToGet = 10;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + " = " + oidOvertime;
            String orderClause = "";

            CtrlOvertimeDetail ctrlOvertimeDetail = new CtrlOvertimeDetail(request);
            ControlLine ctrLine = new ControlLine();
            Vector listOvertimeDetail = new Vector(1, 1);
            Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
            Vector listCompany = PstCompany.list(0, 0, "", "COMPANY");
            Vector listSection = new Vector(1, 1);


            iErrCode = ctrlOvertimeDetail.action(iCommand, oidOvertimeDetail, oidOvertime, request);           
            FrmOvertimeDetail frmOvertimeDetail = ctrlOvertimeDetail.getForm();
            OvertimeDetail overtimeDetail = ctrlOvertimeDetail.getOvertimeDetail();
            msgString = ctrlOvertimeDetail.getMessage();

            /*switch list OvertimeDetail*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidOvertimeDetail == 0)) {
                start = PstOvertimeDetail.findLimitStart(overtimeDetail.getOID(), recordToGet, whereClause, orderClause);
            }

            /*count list All OvertimeDetail*/
            int vectSize = PstOvertimeDetail.getCount(whereClause);

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlOvertimeDetail.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listOvertimeDetail = PstOvertimeDetail.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listOvertimeDetail.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listOvertimeDetail = PstOvertimeDetail.list(start, recordToGet, whereClause, orderClause);
            }

            if (oidOvertime != 0) {
                Overtime overtime = new Overtime();
                try {
                    overtime = PstOvertime.fetchExc(oidOvertime);
                } catch (Exception exc) {
                    overtime = new Overtime();
                }
            }

            if (iCommand == Command.GOTO) {
                frmOvertimeDetail = new FrmOvertimeDetail(request, overtimeDetail);
                frmOvertimeDetail.requestEntityObject(overtimeDetail);
            }

%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>Overtime </title>
        <script language="JavaScript">
            //Function Untuk Overtime Detail
            function cmdAddOv(){
                document.frmovdetail.hidden_overtime_detail_id.value="0";
                document.frmovdetail.command.value="<%=Command.ADD%>";
                document.frmovdetail.prev_command.value="<%=prevCommand%>";
                document.frmovdetail.action="ovdetail.jsp";
                document.frmovdetail.submit();
            }


            function cmdAskOv(oidOvertimeDetail){
                document.frmovdetail.hidden_overtime_detail_id.value=oidOvertimeDetail;
                document.frmovdetail.command.value="<%=Command.ASK%>";
                document.frmovdetail.prev_command.value="<%=prevCommand%>";
                document.frmovdetail.action="ovdetail.jsp";
                document.frmovdetail.submit();
            }


            function cmdConfirmDeleteOv(oid){
                var x = confirm(" Are You Sure to Delete?");
                if(x){
                    document.frmovdetail.command.value="<%=Command.DELETE%>";
                    document.frmovdetail.action="ovdetail.jsp";
                    document.frmovdetail.submit();
                }
            }

            function cmdSaveOv(){
                document.frmovdetail.command.value="<%=Command.SAVE%>";
                document.frmovdetail.prev_command.value="<%=prevCommand%>";
                document.frmovdetail.action="ovdetail.jsp";
                document.frmovdetail.submit();
            }

            function cmdEditOv(oidOvertimeDetail){
                document.frmovdetail.hidden_overtime_detail_id.value=oidOvertimeDetail;
                document.frmovdetail.command.value="<%=Command.EDIT%>";
                document.frmovdetail.prev_command.value="<%=prevCommand%>";
                document.frmovdetail.action="ovdetail.jsp";
                document.frmovdetail.submit();
            }
            function cmdBackOv(){
                document.frmovdetail.command.value="<%=Command.BACK%>";
                document.frmovdetail.action="ovdetail.jsp";
                document.frmovdetail.submit();
            }
            function cmdCheck(){
                var strvalue  = "src_ovemployee.jsp?command=<%=Command.FIRST%>";
                window.open(strvalue,"material", "height=500,width=700,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
            }

            function keyDownCheck(e){
                if (e.keyCode == 13) {
                    //document.all.aSearch.focus();
                    cmdCheck();
                }
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode)         {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break        ;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break        ;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break        ;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
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
                                        <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee &gt; Overtime<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td valign="top">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr>
                                                                <td valign="top"> <!-- #BeginEditable "content" -->
                                                                    <form name="frmovdetail" method ="post" action="ovdetail.jsp">
                                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                        <input type="hidden" name="overtime_oid" value="<%=oidOvertime%>">
                                                                        <input type="hidden" name="department_oid" value="<%=oidDepartment%>">
                                                                        <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                        <input type="hidden" name="hidden_overtime_detail_id" value="<%=oidOvertimeDetail%>">
                                                                        <input type="hidden" name="ovtEmployee_oid" value="<%=oidOvt_Employee%>">


                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                            <tr>
                                                                                <td class="tablecolor">
                                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                                        <tr>
                                                                                            <td valign="top">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="8" valign="middle" colspan="3">
                                                                                                            <% if (oidOvertime != 0) {
                                                                                                                            Overtime overtime = new Overtime();
                                                                                                                            try {
                                                                                                                                overtime = PstOvertime.fetchExc(oidOvertime);
                                                                                                                            } catch (Exception exc) {
                                                                                                                                overtime = new Overtime();
                                                                                                                            }
                                                                                                            %>
                                                                                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                                <tr>
                                                                                                                    <td colspan="2"><b class="listtitle">Career
                                                                                                                            Path Editor</b></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="100%" colspan="2">
                                                                                                                        <table width="100%" cellspacing="1" cellpadding="1">
                                                                                                                            <tr>
                                                                                                                                <td width="100" height="20">Req. Date</td>
                                                                                                                                <td width="400" height="20"> <%=Formater.formatDate(overtime.getRequestDate(), "dd-mm-yyyy HH:mm")%>
                                                                                                                                <td width="100" height="20">No.</td>
                                                                                                                                <td width="400" height="20"> <%=overtime.getOvertimeNum()%></td>
                                                                                                                               
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td width="100" height="20">Company</td>
                                                                                                                                <td width="400" height="20"><%
                                                                                                                                     Vector comp_value = new Vector(1, 1);
                                                                                                                                     Vector comp_key = new Vector(1, 1);
                                                                                                                                     comp_value.add("0");
                                                                                                                                     comp_key.add("select ...");
                                                                                                                                     Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                                                     for (int i = 0; i < listComp.size(); i++) {
                                                                                                                                         Company comp = (Company) listComp.get(i);
                                                                                                                                         comp_key.add(comp.getCompany());
                                                                                                                                         comp_value.add(String.valueOf(comp.getOID()));
                                                                                                                                     }
                                                                                                                                    %> <%= ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID], "formElemen", "" + overtime.getCompanyId(), comp_value, comp_key, "disabled=\"true\"")%></td>
                                                                                                                                <td width="100" height="20">Status Doc.</td>
                                                                                                                                <td width="400" height="20">
                                                                                                                                    <%
                                                                                                                                         Vector obj_status = new Vector(1, 1);
                                                                                                                                         Vector val_status = new Vector(1, 1);
                                                                                                                                         Vector key_status = new Vector(1, 1);

                                                                                                                                         val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
                                                                                                                                         key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

                                                                                                                                         val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
                                                                                                                                         key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);

                                                                                                                                         val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
                                                                                                                                         key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

                                                                                                                                         out.println(ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_STATUS_DOC], "formElemen", "" + overtime.getStatusDoc(), val_status, key_status, "disabled=\"true\""));
                                                                                                                                    %></td>
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                                <td width="400" height="20"> <%
                                                                                                                                     Vector div_value = new Vector(1, 1);
                                                                                                                                     Vector div_key = new Vector(1, 1);
                                                                                                                                     div_value.add("0");
                                                                                                                                     div_key.add("select ...");
                                                                                                                                     Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                                     //String strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + oidCompany;
                                                                                                                                     //Vector listDiv = PstDivision.list(0, 0, strWhere, PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                                                                                                                     for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                                         Division div = (Division) listDiv.get(i);
                                                                                                                                         div_key.add(div.getDivision());
                                                                                                                                         div_value.add(String.valueOf(div.getOID()));
                                                                                                                                     }
                                                                                                                                    %> <%= ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID], "formElemen", "" + overtime.getDivisionId(), div_value, div_key, "disabled=\"true\"")%></td>
                                                                                                                                <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                                                                <td width="400" height="20"><%
                                                                                                                                     Vector sec_value = new Vector(1, 1);
                                                                                                                                     Vector sec_key = new Vector(1, 1);
                                                                                                                                     sec_value.add("0");
                                                                                                                                     sec_key.add("select ...");
                                                                                                                                     Vector listSec = PstSection.list(0, 0, "", " SECTION ");
                                                                                                                                     //String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                                                     //Vector listSec = PstSection.list(0, 0, strWhereSec, PstSection.fieldNames[PstSection.FLD_SECTION]);
                                                                                                                                     for (int i = 0; i < listSec.size(); i++) {
                                                                                                                                         Section sec = (Section) listSec.get(i);
                                                                                                                                         sec_key.add(sec.getSection());
                                                                                                                                         sec_value.add(String.valueOf(sec.getOID()));
                                                                                                                                     }
                                                                                                                                    %> <%= ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID], "formElemen", "" + overtime.getSectionId(), sec_value, sec_key, "disabled=\"true\"")%></td>
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                                <td width="400" height="20"><%
                                                                                                                                     Vector dept_value = new Vector(1, 1);
                                                                                                                                     Vector dept_key = new Vector(1, 1);
                                                                                                                                     dept_value.add("0");
                                                                                                                                     dept_key.add("select ...");
                                                                                                                                     //String strWhereDept = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                     //Vector listDept = PstDepartment.list(0, 0, strWhereDept, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                                                                                                     Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                                                                                                     for (int i = 0; i < listDept.size(); i++) {
                                                                                                                                         Department dept = (Department) listDept.get(i);
                                                                                                                                         dept_key.add(dept.getDepartment());
                                                                                                                                         dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                     }
                                                                                                                                    %> <%= ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID], "formElemen", "" + overtime.getDepartmentId(), dept_value, dept_key, "disabled=\"true\"")%></td>
                                                                                                                                <td width="100" height="20"><a href="javascript:cmdLink()" class="command">Link</a></td>
                                                                                                                                <td width="400" height="20">
                                                                                                                                    <% if (iCommand == Command.ADD) {%>
                                                                                                                                    <table width="100%" cellspacing="1" cellpadding="1">

                                                                                                                                        <tr>
                                                                                                                                            <td width="10%" height="20">Customer Task </td>
                                                                                                                                            <td width="15%" height="20"><label>
                                                                                                                                                    <input type="text" name="textfield" id="textfield" disabled="true" />
                                                                                                                                                </label></td>
                                                                                                                                        </tr>
                                                                                                                                        <tr>
                                                                                                                                            <td width="10%" height="20">Logbook </td>
                                                                                                                                            <td width="15%" height="20"><label>
                                                                                                                                                    <input type="text" name="textfield2" id="textfield2" disabled="true" />
                                                                                                                                                </label></td>
                                                                                                                                        </tr>

                                                                                                                                    </table><% }%>
                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td width="100" valign="top" height="20">Ov. Objective</td>
                                                                                                                                <td width="400" height="20" colspan="3"><textarea name="<%=FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_OBJECTIVE]%>" class="elemenForm" cols="35" rows="2" disabled="true"><%= overtime.getObjective()%></textarea></td>

                                                                                                                            </tr>                                                                                                                           
                                                                                                                            <tr>
                                                                                                                                <td colspan="4"></td>
                                                                                                                            </tr>
                                                                                                                        </table>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="100" height="20"><hr /></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td><font color="#FF9900"><b>List of Overtime</b></font></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>
                                                                                                                        <table width="100%" border="0" cellspacing="" cellpadding="" class="listgensell">
                                                                                                                            <tr>
                                                                                                                                <td
                                                                                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                                                                                                        <tr>
                                                                                                                                            <td align="center">
                                                                                                                                                <div align="left">
                                                                                                                                                </div>
                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                        <%
                                                                                                                                             try {
                                                                                                                                        %>
                                                                                                                                        <tr>
                                                                                                                                            <td align="left">
                                                                                                                                                <%=drawList(iCommand, listOvertimeDetail, frmOvertimeDetail, overtimeDetail, oidOvertimeDetail)%>
                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                        <%
                                                                                                                                             } catch (Exception exc) {
                                                                                                                                                 System.out.println("Err::::::" + exc.toString());
                                                                                                                                             }%>
                                                                                                                                        <tr>
                                                                                                                                            <td height="8" align="left" colspan="3" class="command">
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
                                                                                                                                                                 if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidOvertimeDetail == 0)) {
                                                                                                                                                                     cmd = PstOvertimeDetail.findLimitCommand(start, recordToGet, vectSize);
                                                                                                                                                                 } else {
                                                                                                                                                                     cmd = prevCommand;
                                                                                                                                                                 }
                                                                                                                                                             }
                                                                                                                                                         }
                                                                                                                                                    %>
                                                                                                                                                    <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                                                         ctrLine.initDefault();
                                                                                                                                                    %>
                                                                                                                                                    <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>
                                                                                                                                                </span>
                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                        <tr>
                                                                                                                                            <td>
                                                                                                                                                <table>
                                                                                                                                                    <tr>
                                                                                                                                                        <%if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmOvertimeDetail.errorSize() < 1)) {%>
                                                                                                                                                        <td colspan="2" valign="middle"> <a href="javascript:cmdAddOv()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                                                                                                                                        </td>
                                                                                                                                                        <td width="261" colspan="2" valign="middle">
                                                                                                                                                            <a href="javascript:cmdAddOv()" class="command">Add New OvertimeDetail</a></td>
                                                                                                                                                                <%}%>
                                                                                                                                                    </tr>
                                                                                                                                                </table>
                                                                                                                                            </td>
                                                                                                                                        </tr>
                                                                                                                                        <tr>
                                                                                                                                            <td height="15">
                                                                                                                                                <table>
                                                                                                                                                    <tr>
                                                                                                                                                        <%
                                                                                                                                                             if ((iCommand == Command.ADD || iCommand == Command.EDIT)) {
                                                                                                                                                        %>
                                                                                                                                                        <td width="24" valign="middle"> <a href="javascript:cmdSaveOv()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
                                                                                                                                                        </td>
                                                                                                                                                        <td width="100" valign="middle">
                                                                                                                                                            <a href="javascript:cmdSaveOv()" class="command" >Save OvertimeDetail</a></td>
                                                                                                                                                        <td width="145">
                                                                                                                                                            <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                                                                                                                                                            <a href="javascript:cmdConfirmDeleteOv()" class="command">Delete OvertimeDetail</a></td>
                                                                                                                                                        <td width="24"  valign="middle"><a href="javascript:cmdBackOv()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
                                                                                                                                                        </td>
                                                                                                                                                        <td width="172" colspan="2" valign="middle"><a href="javascript:cmdBackOv()" class="command" >Back to List OvertimeDetail</a></td>
                                                                                                                                                    </tr>
                                                                                                                                                    <%}%>
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
                                                                                                                    <td width="100" height="20"><hr /></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="100" height="20">
                                                                                                                        <table width="100%" cellspacing="1" cellpadding="1">
                                                                                                                            <tr>
                                                                                                                                <td width="5%" height="20" ></td>
                                                                                                                                <td width="30%" height="20" align="center">Request by</td>
                                                                                                                                <td width="30%" height="20" align="center">Approval by</td>
                                                                                                                                <td width="30%" height="20" align="center">Acknowledge</td>
                                                                                                                                <td width="5%" height="20" ></td>
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td width="5%" height="20" ></td>
                                                                                                                                <td width="30%" height="20" align="center">
                                                                                                                                    <%
                                                                                                                                         Vector req_value = new Vector(1, 1);
                                                                                                                                         Vector req_key = new Vector(1, 1);
                                                                                                                                         req_value.add("0");
                                                                                                                                         req_key.add("select ...");
                                                                                                                                         Vector listReq = PstEmployee.list(0, 0, "", " FULL_NAME ");
                                                                                                                                         //String strWhereReq = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                                                         //Vector listReq = PstEmployee.list(0, 0, strWhereReq, PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                                                                                                                                         for (int i = 0; i < listReq.size(); i++) {
                                                                                                                                             Employee req = (Employee) listReq.get(i);
                                                                                                                                             req_key.add(req.getFullName());
                                                                                                                                             req_value.add(String.valueOf(req.getOID()));
                                                                                                                                         }
                                                                                                                                    %> <%= ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID], "formElemen", "" + overtime.getRequestId(), req_value, req_key, "disabled=\"true\"")%>
                                                                                                                                </td>
                                                                                                                                <td width="30%" height="20" align="center">
                                                                                                                                    <%
                                                                                                                                         Vector app_value = new Vector(1, 1);
                                                                                                                                         Vector app_key = new Vector(1, 1);
                                                                                                                                         app_value.add("0");
                                                                                                                                         app_key.add("select ...");
                                                                                                                                         Vector listApp = PstEmployee.list(0, 0, "", " FULL_NAME ");
                                                                                                                                         //String strWhereApp = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                         //Vector listApp = PstEmployee.list(0, 0, strWhereApp, PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                                                                                                                                         for (int i = 0; i < listApp.size(); i++) {
                                                                                                                                             Employee app = (Employee) listApp.get(i);
                                                                                                                                             app_key.add(app.getFullName());
                                                                                                                                             app_value.add(String.valueOf(app.getOID()));
                                                                                                                                         }
                                                                                                                                    %> <%= ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID], "formElemen", "" + overtime.getApprovalId(), req_value, req_key, "disabled=\"true\"")%>
                                                                                                                                </td>
                                                                                                                                <td width="30%" height="20" align="center">
                                                                                                                                    <%
                                                                                                                                         Vector ack_value = new Vector(1, 1);
                                                                                                                                         Vector ack_key = new Vector(1, 1);
                                                                                                                                         ack_value.add("0");
                                                                                                                                         ack_key.add("select ...");
                                                                                                                                         Vector listAck = PstEmployee.list(0, 0, "", " FULL_NAME ");
                                                                                                                                         //String strWhereAck = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                         //Vector listAck = PstEmployee.list(0, 0, strWhereAck, PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                                                                                                                                         for (int i = 0; i < listReq.size(); i++) {
                                                                                                                                             Employee ack = (Employee) listAck.get(i);
                                                                                                                                             ack_key.add(ack.getFullName());
                                                                                                                                             ack_value.add(String.valueOf(ack.getOID()));
                                                                                                                                         }
                                                                                                                                    %> <%= ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID], "formElemen", "" + overtime.getAckId(), req_value, req_key, "disabled=\"true\"")%>
                                                                                                                                </td>
                                                                                                                                <td width="5%" height="20" ></td>
                                                                                                                            </tr>
                                                                                                                            <tr>
                                                                                                                                <td height="20" align="center" valign="middle"></td>
                                                                                                                            </tr>
                                                                                                                        </table>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                            </table><%}%>
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
                                                                    </form>
                                                                    <!-- #EndEditable --> </td>
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
            <tr>
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
