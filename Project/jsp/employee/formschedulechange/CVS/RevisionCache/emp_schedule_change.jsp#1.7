<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.employee.workschedule.EmpScheduleChange"%>
<%@page import="com.dimata.harisma.form.employee.workschedule.FrmEmpScheduleChange"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.employee.workschedule.CtrlEmpScheduleChange"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.employee.workschedule.PstEmpScheduleChange"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%
            /*
             * Page Name  		:  empSchedule.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Priska_20150930
             * @version  		: -
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

<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<!--package harisma -->

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G1_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_MENU);%>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long empScheduleChangeId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Date Time Request", "10%");
        ctrlist.addHeader("Status Doc", "10%");
        ctrlist.addHeader("Applicant Employee", "10%");
        ctrlist.addHeader("Exchange Employee", "10%");
        ctrlist.addHeader("Original Date", "10%");
        ctrlist.addHeader("Original Schedule Id", "10%");
        ctrlist.addHeader("New Change Date", "10%");
        ctrlist.addHeader("New Change Schedule Date", "10%");
        ctrlist.addHeader("Reason", "10%");
        ctrlist.addHeader("Remark", "10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        Hashtable scheduleSym = PstScheduleSymbol.getHashTblScheduleSymbol(0, 0, "", "");
        Hashtable hashStatus = new Hashtable();
        hashStatus.put(I_DocStatus.DOCUMENT_STATUS_DRAFT,I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]) ;
        hashStatus.put(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED,I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]) ;
        hashStatus.put(I_DocStatus.DOCUMENT_STATUS_FINAL,I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]) ;
        
        
        for (int i = 0; i < objectClass.size(); i++) {
            EmpScheduleChange empScheduleChange = (EmpScheduleChange) objectClass.get(i);
            Vector rowx = new Vector();
   
            rowx.add(""+empScheduleChange.getDateOfRequestDatetime());
            rowx.add(""+hashStatus.get (empScheduleChange.getStatusDoc()));
            rowx.add(""+PstEmployee.getEmployeeName(empScheduleChange.getApplicantEmployeeId()));
            rowx.add(""+PstEmployee.getEmployeeName(empScheduleChange.getExchangeEmployeeId()));
            rowx.add(""+empScheduleChange.getOriginalDate());
            rowx.add(""+scheduleSym.get(empScheduleChange.getOriginalScheduleId()));
            rowx.add(""+empScheduleChange.getNewChangeDate());
            rowx.add(""+scheduleSym.get(empScheduleChange.getNewChangeScheduleId()));
            rowx.add(""+empScheduleChange.getReason());
            rowx.add(""+empScheduleChange.getRemark());

            
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(empScheduleChange.getOID()));
        }
        return ctrlist.draw(index);
    }

%>

<%!

	public String drawList2(Vector objectClass, long empScheduleChangeId) {

		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Type Name","30%");
		ctrlist.addHeader("Description","50%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 
			 rowx = new Vector();
			
				rowx.add("-");
				rowx.add("-");
		 

			lstData.add(rowx);
		}


		return ctrlist.draw();
	}

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidEmpScheduleChange = FRMQueryString.requestLong(request, "oidEmpScheduleChange");

            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = ""+PstEmpScheduleChange.fieldNames[PstEmpScheduleChange.FLD_TYPE_OF_SCHEDULE] +" = "+ PstEmpScheduleChange.TYPE_SCHEDULE_CHANGE ;
            String orderClause = "";

            CtrlEmpScheduleChange ctrlEmpScheduleChange = new CtrlEmpScheduleChange(request);
            ControlLine ctrLine = new ControlLine();
            Vector listEmpScheduleChange = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlEmpScheduleChange.action(iCommand, oidEmpScheduleChange);
            /* end switch*/
            FrmEmpScheduleChange frmEmpScheduleChange = ctrlEmpScheduleChange.getForm();

            /*count list All Position*/
            int vectSize = PstEmpScheduleChange.getCount(whereClause);

            EmpScheduleChange empScheduleChange = ctrlEmpScheduleChange.getdEmpScheduleChange();
            msgString = ctrlEmpScheduleChange.getMessage();

            /*switch list EmpScheduleChange*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstEmpScheduleChange.findLimitStart(empScheduleChange.getOID(),recordToGet, whereClause);
                oidEmpScheduleChange = empScheduleChange.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlEmpScheduleChange.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listEmpScheduleChange = PstEmpScheduleChange.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listEmpScheduleChange.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listEmpScheduleChange = PstEmpScheduleChange.list(start, recordToGet, whereClause, orderClause);
            }

                

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data EmpScheduleChange</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmempScheduleChange.oidEmpScheduleChange.value="0";
                document.frmempScheduleChange.command.value="<%=Command.ADD%>";
                document.frmempScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmempScheduleChange.action="emp_schedule_change_edit.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdAsk(oidEmpScheduleChange){
                document.frmempScheduleChange.oidEmpScheduleChange.value=oidEmpScheduleChange;
                document.frmempScheduleChange.command.value="<%=Command.ASK%>";
                document.frmempScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdConfirmDelete(oidEmpScheduleChange){
                document.frmempScheduleChange.oidEmpScheduleChange.value=oidEmpScheduleChange;
                document.frmempScheduleChange.command.value="<%=Command.DELETE%>";
                document.frmempScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }
            function cmdSave(){
                document.frmempScheduleChange.command.value="<%=Command.SAVE%>";
                document.frmempScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdEdit(oidEmpScheduleChange){
                document.frmempScheduleChange.oidEmpScheduleChange.value=oidEmpScheduleChange;
                document.frmempScheduleChange.command.value="<%=Command.EDIT%>";
                document.frmempScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmempScheduleChange.action="emp_schedule_change_edit.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdCancel(oidEmpScheduleChange){
                document.frmempScheduleChange.oidEmpScheduleChange.value=oidEmpScheduleChange;
                document.frmempScheduleChange.command.value="<%=Command.EDIT%>";
                document.frmempScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdBack(){
                document.frmempScheduleChange.command.value="<%=Command.BACK%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdListFirst(){
                document.frmempScheduleChange.command.value="<%=Command.FIRST%>";
                document.frmempScheduleChange.prev_command.value="<%=Command.FIRST%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdListPrev(){
                document.frmempScheduleChange.command.value="<%=Command.PREV%>";
                document.frmempScheduleChange.prev_command.value="<%=Command.PREV%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdListNext(){
                document.frmempScheduleChange.command.value="<%=Command.NEXT%>";
                document.frmempScheduleChange.prev_command.value="<%=Command.NEXT%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function cmdListLast(){
                document.frmempScheduleChange.command.value="<%=Command.LAST%>";
                document.frmempScheduleChange.prev_command.value="<%=Command.LAST%>";
                document.frmempScheduleChange.action="emp_schedule_change.jsp";
                document.frmempScheduleChange.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
                }

                //-------------- script control line -------------------
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

        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
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
                                                    Change Off/Shift
                                                </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmempScheduleChange" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="oidEmpScheduleChange" value="<%=oidEmpScheduleChange%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;EmpScheduleChange List </td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                                try {
                                                                                                                    if (listEmpScheduleChange.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%=drawList(listEmpScheduleChange, oidEmpScheduleChange)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%  }
                                                                                                        } catch (Exception exc) {
                                                                                                        }%>
                                                                                                    <tr align="left" valign="top">
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
                                                                                                                                    cmd = prevCommand;
                                                                                                                                }
                                                                                                                            }
                                                                                                                %>
                                                                                                                <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                            ctrLine.initDefault();
                                                                                                                %>
                                                                                                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>
                                                                                                            </span> </td>
                                                                                                    </tr>
                                                                                                  
                                                                                                       <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmEmpScheduleChange.errorSize()<1)){
                                                                                                    if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmEmpScheduleChange.errorSize() < 1)) {
                                                                                                        if (privAdd) {%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>
                                                                                                            <table cellpadding="0" cellspacing="0" border="0">
                                                                                                                <tr>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                    <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="22" valign="middle" colspan="3" width="951">
                                                                                                                        <a href="javascript:cmdAdd()" class="command">Add  New </a> </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}
                                                                                                      }%>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmEmpScheduleChange.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                
                                                                                                    
                                                                                                    <tr>
                                                                                                        <td colspan="2">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidEmpScheduleChange + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidEmpScheduleChange + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidEmpScheduleChange + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setSaveCaption("Save");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete");
                                                                                                                ctrLine.setDeleteCaption("Delete");

                                                                                                                if ( privDelete ) {
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

                                                                                                                if (iCommand == Command.ASK) {
                                                                                                                    ctrLine.setDeleteQuestion(msgString);
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
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
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

