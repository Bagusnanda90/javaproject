<%-- 
    Document   : empreprimand
    Created on : Sep 17, 2011, 10:39:15 AM
    Author     : Wiweka
--%>
<!--* Ari_20110909
    * Membuat reprimand di masterdata {
-->
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_EDUCATION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%!    public String drawList(Vector objectClass, long reprimandId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("30%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Description", "30%");
        ctrlist.addHeader("Reprimand Point", "10%", "align='center'");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        for (int i = 0; i < objectClass.size(); i++) {
            Reprimand reprimand = (Reprimand) objectClass.get(i);
            Vector rowx = new Vector();
            if (reprimandId == reprimand.getOID()) {
                index = i;
            }

            rowx.add(reprimand.getReprimandDesc());
            rowx.add("" + reprimand.getReprimandPoint());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(reprimand.getOID()));
        }

        return ctrlist.draw(index);
    }
%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidReprimand = FRMQueryString.requestLong(request, "hidden_reprimand_id");

            /*variable declaration*/
            int recordToGet = 10;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "" + PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID] + " ASC ";

            CtrlReprimand ctrlReprimand = new CtrlReprimand(request);
            ControlLine ctrLine = new ControlLine();
            Vector listReprimand = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlReprimand.action(iCommand, oidReprimand);
            /* end switch*/
            FrmReprimand frmReprimand = ctrlReprimand.getForm();

            /*count list All Reprimand*/
            int vectSize = PstReprimand.getCount(whereClause);

            Reprimand reprimand = ctrlReprimand.getReprimand();
            msgString = ctrlReprimand.getMessage();

            /*switch list Reprimand*/
            /*
            if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidReprimand == 0))
            start = PstReprimand.findLimitStart(reprimand.getOID(),recordToGet, whereClause, orderClause);
             */

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlReprimand.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listReprimand = PstReprimand.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listReprimand.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listReprimand = PstReprimand.list(start, recordToGet, whereClause, orderClause);
            }
%>

<html>
    <!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data Reprimand</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmeducation.hidden_reprimand_id.value="0";
                document.frmeducation.command.value="<%=Command.ADD%>";
                document.frmeducation.prev_command.value="<%=prevCommand%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdAsk(oidReprimand){
                document.frmeducation.hidden_reprimand_id.value=oidReprimand;
                document.frmeducation.command.value="<%=Command.ASK%>";
                document.frmeducation.prev_command.value="<%=prevCommand%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdConfirmDelete(oidReprimand){
                document.frmeducation.hidden_reprimand_id.value=oidReprimand;
                document.frmeducation.command.value="<%=Command.DELETE%>";
                document.frmeducation.prev_command.value="<%=prevCommand%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }
            function cmdSave(){
                document.frmeducation.command.value="<%=Command.SAVE%>";
                document.frmeducation.prev_command.value="<%=prevCommand%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdEdit(oidReprimand){
                document.frmeducation.hidden_reprimand_id.value=oidReprimand;
                document.frmeducation.command.value="<%=Command.EDIT%>";
                document.frmeducation.prev_command.value="<%=prevCommand%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdCancel(oidReprimand){
                document.frmeducation.hidden_reprimand_id.value=oidReprimand;
                document.frmeducation.command.value="<%=Command.EDIT%>";
                document.frmeducation.prev_command.value="<%=prevCommand%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdBack(){
                document.frmeducation.command.value="<%=Command.BACK%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdListFirst(){
                document.frmeducation.command.value="<%=Command.FIRST%>";
                document.frmeducation.prev_command.value="<%=Command.FIRST%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdListPrev(){
                document.frmeducation.command.value="<%=Command.PREV%>";
                document.frmeducation.prev_command.value="<%=Command.PREV%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdListNext(){
                document.frmeducation.command.value="<%=Command.NEXT%>";
                document.frmeducation.prev_command.value="<%=Command.NEXT%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
            }

            function cmdListLast(){
                document.frmeducation.command.value="<%=Command.LAST%>";
                document.frmeducation.prev_command.value="<%=Command.LAST%>";
                document.frmeducation.action="empreprimand.jsp";
                document.frmeducation.submit();
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
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
                function hideObjectForEmployee(){
                    //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
                }

                function hideObjectForLockers(){
                }

                function hideObjectForCanteen(){
                }

                function hideObjectForClinic(){
                }

                function hideObjectForMasterdata(){
                }

                function showObjectForMenu(){
                    //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
                }
        </SCRIPT>
        <!-- #EndEditable -->
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
                    <%@ include file = "../main/mnmain.jsp" %>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                                                    Master Data &gt; Employee &gt; Reprimand<!-- #EndEditable -->
                                                </strong></font> </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; ">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top"> <!-- #BeginEditable "content" -->
                                                                                <form name="frmeducation" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_reprimand_id" value="<%=oidReprimand%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="comment">&nbsp;Reprimand List </td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                                try {
                                                                                                                    if (listReprimand.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%= drawList(listReprimand, oidReprimand)%>
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
                                                                                                                                    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidReprimand == 0)) {
                                                                                                                                        cmd = PstReprimand.findLimitCommand(start, recordToGet, vectSize);
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
                                                                                                            </span> </td>
                                                                                                    </tr>
                                                                                                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand ==Command.BACK || iCommand ==Command.SAVE)&& (frmEmpCategory.errorSize()<1)){
                                                                                                                if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmReprimand.errorSize() < 1)) {
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
                                                                                                                    <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add New Reprimand</a> </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}
                                                                                                                }%>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmReprimand.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td colspan="3" class="listtitle"><%=oidReprimand == 0 ? "Add" : "Edit"%>
                                                                                                            Reprimand</td>
                                                                                                    </tr>

                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="21" valign="middle" width="1%">&nbsp;</td>
                                                                                                        <td height="21" valign="middle" width="7%">&nbsp;</td>
                                                                                                        <td height="21" colspan="2" width="92%" class="comment">*)= required</td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="21" valign="top" width="1%">&nbsp;</td>
                                                                                                        <td height="21" valign="top" width="7%">Description</td>
                                                                                                        <td height="21" colspan="2" width="92%">
                                                                                                            <input type="text" name="<%=frmReprimand.fieldNames[FrmReprimand.FRM_FIELD_REPRIMAND_DESC]%>"  value="<%= reprimand.getReprimandDesc()%>" class="formElemen" > * <%= frmReprimand.getErrorMsg(FrmReprimand.FRM_FIELD_REPRIMAND_DESC)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="21" valign="top" width="1%">&nbsp;</td>
                                                                                                        <td height="21" valign="top" width="7%">Point</td>
                                                                                                        <td height="21" colspan="2" width="92%">
                                                                                                            <input type="text" name="<%=frmReprimand.fieldNames[FrmReprimand.FRM_FIELD_REPRIMAND_POINT]%>"  value="<%= "" + reprimand.getReprimandPoint()%>" class="formElemen" size="40" >
                                                                                                            * <%= frmReprimand.getErrorMsg(FrmReprimand.FRM_FIELD_REPRIMAND_POINT)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="8" valign="middle" width="1%">&nbsp;</td>
                                                                                                        <td height="8" valign="middle" width="7%">&nbsp;</td>
                                                                                                        <td height="8" colspan="2" width="92%">&nbsp;
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="8" valign="middle" width="1%">&nbsp;</td>
                                                                                                        <td height="8" valign="middle" width="7%">&nbsp;</td>
                                                                                                        <td height="8" colspan="2" width="92%">&nbsp;</td>
                                                                                                    </tr>


                                                                                                    <tr align="left" valign="top" >
                                                                                                        <td colspan="4" class="command">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidReprimand + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidReprimand + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidReprimand + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setDeleteCaption("Delete");
                                                                                                                ctrLine.setSaveCaption("Save");
                                                                                                                ctrLine.setAddCaption("");

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
                                                                                                                if (iCommand == Command.ASK) {
                                                                                                                    ctrLine.setDeleteQuestion(msgString);
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="1%">&nbsp;</td>
                                                                                                        <td width="7%">&nbsp;</td>
                                                                                                        <td width="92%">&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top" >
                                                                                                        <td colspan="4">
                                                                                                            <div align="left"></div>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <script language="JavaScript">
            //var oBody = document.body;
            //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #BeginEditable "script" -->
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
<!--}-->

