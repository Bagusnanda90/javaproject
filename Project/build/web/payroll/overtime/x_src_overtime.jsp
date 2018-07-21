<%--
    Document   : srcovertime
    Created on : Nov 12, 2011, 5:56:23 PM
    Author     : Wiweka
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.entity.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.overtime.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>

<%
            long oidDivision = FRMQueryString.requestLong(request, FrmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_DIVISION_ID]);
            long oidDepartment = FRMQueryString.requestLong(request, FrmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_DEPARTMENT_ID]);
            int iCommand = FRMQueryString.requestCommand(request);
            SrcOvertime srcOvertime = new SrcOvertime();
            FrmSrcOvertime frmSrcOvertime = new FrmSrcOvertime();

            if (iCommand == Command.GOTO) {
                frmSrcOvertime = new FrmSrcOvertime(request, srcOvertime);
                frmSrcOvertime.requestEntityObject(srcOvertime);
            }

            if (iCommand == Command.BACK) {
                frmSrcOvertime = new FrmSrcOvertime(request, srcOvertime);
                try {
                    srcOvertime = (SrcOvertime) session.getValue(SessOvertime.SESS_SRC_OVERTIME);
                    if (srcOvertime == null) {
                        srcOvertime = new SrcOvertime();
                    }
                    System.out.println("ecccccc " + srcOvertime.getOrderBy());
                } catch (Exception e) {
                    srcOvertime = new SrcOvertime();
                }
            }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Search Employee</title>
        <script language="JavaScript">

            function cmdUpdateDiv(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcovertime.action="src_overtime.jsp";
                document.frmsrcovertime.submit();
            }
            function cmdUpdateDep(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcovertime.action="src_overtime.jsp";
                document.frmsrcovertime.submit();
            }
            function cmdUpdatePos(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcovertime.action="src_overtime.jsp";
                document.frmsrcovertime.submit();
            }


            function cmdAdd(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.ADD)%>";
                document.frmsrcovertime.action="overtime.jsp";
                document.frmsrcovertime.submit();
            }

            function cmdSearch(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcovertime.action="overtime_list.jsp";
                document.frmsrcovertime.submit();
            }

            function cmdHeadCount(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcovertime.action="employee_headcount.jsp";
                document.frmsrcovertime.submit();
            }

            function cmdSpecialQuery(){
                document.frmsrcovertime.action="specialquery.jsp";
                document.frmsrcovertime.submit();
            }

            function fnTrapKD(){
                if (event.keyCode == 13) {
                    document.all.aSearch.focus();
                    cmdSearch();
                }
            }

            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.0
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
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
        <!-- #EndEditable -->
    </head>

    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                                                    Overtime &gt; Overtime Search <!-- #EndEditable -->
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
                                                                                <form name="<%=frmSrcOvertime.getFormName() %>" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td valign="middle" colspan="2">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                                                                                    <tr>
                                                                                                        <td width="3%">&nbsp;</td>
                                                                                                        <td width="97%">&nbsp;</td>
                                                                                                        <td width="0%">&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="3%">&nbsp;</td>
                                                                                                        <td width="97%">
                                                                                                            <table border="0" cellspacing="2" cellpadding="2" width="72%">
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Request Date</div></td>
                                                                                                                    <td width="83%"> <%=ControlDate.drawDateWithStyle(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_REQ_DATE], srcOvertime.getRequestDate(), 0, -50, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">No. Overtime</div></td>
                                                                                                                    <td width="83%"><input type="text" name="<%=frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_OV_NUMBER]%>"  value="<%= srcOvertime.getOvertimeNum()%>" class="elemenForm" size="40" onkeydown="javascript:fnTrapKD()"></td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Company</div></td>
                                                                                                                    <td width="83%"> <% 
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
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + srcOvertime.getCompanyId(), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                                Vector div_value = new Vector(1, 1);
                                                                                                                                Vector div_key = new Vector(1, 1);
                                                                                                                                div_value.add("0");
                                                                                                                                div_key.add("select ...");
                                                                                                                                Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                                for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                                    Division div = (Division) listDiv.get(i);
                                                                                                                                    div_key.add(div.getDivision());
                                                                                                                                    div_value.add(String.valueOf(div.getOID()));
                                                                                                                                }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + srcOvertime.getDivisionId(), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                                Vector dept_value = new Vector(1, 1);
                                                                                                                                Vector dept_key = new Vector(1, 1);
                                                                                                                                dept_value.add("0");
                                                                                                                                dept_key.add("select ...");
                                                                                                                                String strWhere = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                Vector listDept = PstDepartment.list(0, 0, strWhere, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                                                                                                for (int i = 0; i < listDept.size(); i++) {
                                                                                                                                    Department dept = (Department) listDept.get(i);
                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + srcOvertime.getDepartmentId(), dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                                Vector sec_value = new Vector(1, 1);
                                                                                                                                Vector sec_key = new Vector(1, 1);
                                                                                                                                sec_value.add("0");
                                                                                                                                sec_key.add("select ...");
                                                                                                                                //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                                                                                                String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                                                Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                                                                                                                for (int i = 0; i < listSec.size(); i++) {
                                                                                                                                    Section sec = (Section) listSec.get(i);
                                                                                                                                    sec_key.add(sec.getSection());
                                                                                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                                                                                                }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_SECTION_ID], "formElemen", null, "" + srcOvertime.getSectionId(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%></td>

                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Status Doc.</div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                                Vector obj_status = new Vector(1, 1);
                                                                                                                                Vector val_status = new Vector(1, 1);
                                                                                                                                Vector key_status = new Vector(1, 1);

                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);

                                                                                                                                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
                                                                                                                                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

                                                                                                                                out.println(ControlCombo.draw(FrmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_STATUS_DOC], null, "", val_status, key_status, "tabindex=\"4\"", "formElemen"));
                                                                                                                        %></td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Request by</div></td>
                                                                                                                    <td width="83%"><%
                                                                                                                                Vector req_value = new Vector(1, 1);
                                                                                                                                Vector req_key = new Vector(1, 1);
                                                                                                                                req_value.add("0");
                                                                                                                                req_key.add("select ...");
                                                                                                                                //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                                String strWhereReq = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                                                Vector listReq = PstEmployee.list(0, 0, strWhereReq, PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                                                                                                                                for (int i = 0; i < listReq.size(); i++) {
                                                                                                                                    Employee req = (Employee) listReq.get(i);
                                                                                                                                    req_key.add(req.getFullName());
                                                                                                                                    req_value.add(String.valueOf(req.getOID()));
                                                                                                                                }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_REQ_ID], "formElemen", null, "" + srcOvertime.getRequestId(), req_value, req_key)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Approval by</div></td>
                                                                                                                    <td width="83%"><%
                                                                                                                                Vector app_value = new Vector(1, 1);
                                                                                                                                Vector app_key = new Vector(1, 1);
                                                                                                                                app_value.add("0");
                                                                                                                                app_key.add("select ...");
                                                                                                                                //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                                String strWhereApp = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                Vector listApp = PstEmployee.list(0, 0, strWhereApp, PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                                                                                                                                for (int i = 0; i < listApp.size(); i++) {
                                                                                                                                    Employee app = (Employee) listApp.get(i);
                                                                                                                                    app_key.add(app.getFullName());
                                                                                                                                    app_value.add(String.valueOf(app.getOID()));
                                                                                                                                }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_APPROVAL_ID], "formElemen", null, "" + srcOvertime.getApprovalId(), req_value, req_key)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Acknowledge by</div></td>
                                                                                                                    <td width="83%"><%
                                                                                                                                Vector ack_value = new Vector(1, 1);
                                                                                                                                Vector ack_key = new Vector(1, 1);
                                                                                                                                ack_value.add("0");
                                                                                                                                ack_key.add("select ...");
                                                                                                                                //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                                                String strWhereAck = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                Vector listAck = PstEmployee.list(0, 0, strWhereAck, PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                                                                                                                                for (int i = 0; i < listReq.size(); i++) {
                                                                                                                                    Employee ack = (Employee) listAck.get(i);
                                                                                                                                    ack_key.add(ack.getFullName());
                                                                                                                                    ack_value.add(String.valueOf(ack.getOID()));
                                                                                                                                }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_ACK_ID], "formElemen", null, "" + srcOvertime.getAckId(), req_value, req_key)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" height="21" nowrap>
                                                                                                                        <div align="left">Sort By</div></td>
                                                                                                                    <td width="83%"> <%= ControlCombo.draw(frmSrcOvertime.fieldNames[FrmSrcOvertime.FRM_FIELD_ORDER], "formElemen", null, "" + srcOvertime.getOrderBy(), FrmSrcOvertime.getOrderValue(), FrmSrcOvertime.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%"> <div align="left"></div></td>
                                                                                                                    <td width="83%">
                                                                                                                        <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                            <tr>
                                                                                                                                <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                                                                                                <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search for Overtime</a></td>

                                                                                                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                                                                                                <td width="20%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                                                                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                                                                                                <td width="26%" class="command" nowrap><a href="javascript:cmdAdd()">Add New Overtime</a></td>
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
    <!-- #BeginEditable "script" -->
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

