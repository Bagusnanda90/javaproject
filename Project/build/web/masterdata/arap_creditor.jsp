<%-- 
    Document   : arApCreditor
    Created on : Sep 30, 2011, 3:56:51 PM
    Author     : Wiweka
--%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmArApCreditor"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlArApCreditor"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.ArApCreditor"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstArApCreditor"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  arap_creditor.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Ari_20110930
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
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long arApCreditorId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Creditor", "30%");
        ctrlist.addHeader("Description", "50%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        Vector valueArApCreditor = new Vector();
        Vector keyArApCreditor = new Vector();
        valueArApCreditor.add("" + 0);
        keyArApCreditor.add("select");
        
        
        for (int i = 0; i < objectClass.size(); i++) {
            ArApCreditor arApCreditor = (ArApCreditor) objectClass.get(i);
            Vector rowx = new Vector();
            if (arApCreditorId == arApCreditor.getOID()) {
                index = i;
            }

            rowx.add(arApCreditor.getCreditorName());
            rowx.add(arApCreditor.getDescription());
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(arApCreditor.getOID()));
        }
        return ctrlist.draw(index);
    }

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidArApCreditor = FRMQueryString.requestLong(request, "hidden_arApCreditor_id");

            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "";

            CtrlArApCreditor ctrlArApCreditor = new CtrlArApCreditor(request);
            ControlLine ctrLine = new ControlLine();
            Vector listArApCreditor = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlArApCreditor.action(iCommand, oidArApCreditor);
            /* end switch*/
            FrmArApCreditor frmArApCreditor = ctrlArApCreditor.getForm();

            /*count list All Position*/
            int vectSize = PstArApCreditor.getCount(whereClause);

            ArApCreditor arApCreditor = ctrlArApCreditor.getArApCreditor();
            msgString = ctrlArApCreditor.getMessage();

            /*switch list ArApCreditor*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstArApCreditor.findLimitStart(arApCreditor.getOID(),recordToGet, whereClause);
                oidArApCreditor = arApCreditor.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlArApCreditor.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listArApCreditor = PstArApCreditor.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listArApCreditor.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listArApCreditor = PstArApCreditor.list(start, recordToGet, whereClause, orderClause);
            }


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data ArApCreditor</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmarApCreditor.hidden_arApCreditor_id.value="0";
                document.frmarApCreditor.command.value="<%=Command.ADD%>";
                document.frmarApCreditor.prev_command.value="<%=prevCommand%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdAsk(oidArApCreditor){
                document.frmarApCreditor.hidden_arApCreditor_id.value=oidArApCreditor;
                document.frmarApCreditor.command.value="<%=Command.ASK%>";
                document.frmarApCreditor.prev_command.value="<%=prevCommand%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdConfirmDelete(oidArApCreditor){
                document.frmarApCreditor.hidden_arApCreditor_id.value=oidArApCreditor;
                document.frmarApCreditor.command.value="<%=Command.DELETE%>";
                document.frmarApCreditor.prev_command.value="<%=prevCommand%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }
            function cmdSave(){
                document.frmarApCreditor.command.value="<%=Command.SAVE%>";
                document.frmarApCreditor.prev_command.value="<%=prevCommand%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdEdit(oidArApCreditor){
                document.frmarApCreditor.hidden_arApCreditor_id.value=oidArApCreditor;
                document.frmarApCreditor.command.value="<%=Command.EDIT%>";
                document.frmarApCreditor.prev_command.value="<%=prevCommand%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdCancel(oidArApCreditor){
                document.frmarApCreditor.hidden_arApCreditor_id.value=oidArApCreditor;
                document.frmarApCreditor.command.value="<%=Command.EDIT%>";
                document.frmarApCreditor.prev_command.value="<%=prevCommand%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdBack(){
                document.frmarApCreditor.command.value="<%=Command.BACK%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdListFirst(){
                document.frmarApCreditor.command.value="<%=Command.FIRST%>";
                document.frmarApCreditor.prev_command.value="<%=Command.FIRST%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdListPrev(){
                document.frmarApCreditor.command.value="<%=Command.PREV%>";
                document.frmarApCreditor.prev_command.value="<%=Command.PREV%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdListNext(){
                document.frmarApCreditor.command.value="<%=Command.NEXT%>";
                document.frmarApCreditor.prev_command.value="<%=Command.NEXT%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
            }

            function cmdListLast(){
                document.frmarApCreditor.command.value="<%=Command.LAST%>";
                document.frmarApCreditor.prev_command.value="<%=Command.LAST%>";
                document.frmarApCreditor.action="arap_creditor.jsp";
                document.frmarApCreditor.submit();
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
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
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
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
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
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                    <!-- #BeginEditable "contenttitle" -->
                                                    Master Data &gt; ArApCreditor<!-- #EndEditable -->
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
                                                                                <form name="frmarApCreditor" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_arApCreditor_id" value="<%=oidArApCreditor%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;ArApCreditor List </td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                                try {
                                                                                                                    if (listArApCreditor.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%= drawList(listArApCreditor, oidArApCreditor)%>
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
                                                                                                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmArApCreditor.errorSize()<1)){
                                                                                                    if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmArApCreditor.errorSize() < 1)) {
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
                                                                                                                        <a href="javascript:cmdAdd()" class="command">Add
                                                                                                                            New ArApCreditor</a> </td>
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
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmArApCreditor.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr>
                                                                                                        <td class="listtitle"><%=oidArApCreditor == 0 ? "Add" : "Edit"%> ArApCreditor</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td height="100%">
                                                                                                            <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">&nbsp;</td>
                                                                                                                    <td width="83%" class="comment">*)entry required </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        ArApCreditor</td>
                                                                                                                    <td width="83%">
                                                                                                                        <input type="text" name="<%=frmArApCreditor.fieldNames[FrmArApCreditor.FRM_FIELD_CREDITOR_NAME]%>"  value="<%= arApCreditor.getCreditorName()%>" class="elemenForm" size="30">
                                                                                                                        *<%=frmArApCreditor.getErrorMsg(FrmArApCreditor.FRM_FIELD_CREDITOR_NAME)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        Description </td>
                                                                                                                    <td width="83%">
                                                                                                                        <textarea name="<%=frmArApCreditor.fieldNames[FrmArApCreditor.FRM_FIELD_DESCRIPTION]%>" class="elemenForm" cols="30" rows="3"><%= arApCreditor.getDescription()%></textarea>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                
                                                                                                            </table >
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td colspan="2">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidArApCreditor + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidArApCreditor + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidArApCreditor + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List ArApCreditor");
                                                                                                                ctrLine.setSaveCaption("Save ArApCreditor");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete ArApCreditor");
                                                                                                                ctrLine.setDeleteCaption("Delete ArApCreditor");

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
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

