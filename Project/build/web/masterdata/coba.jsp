
<%
            /*
             * Page Name  		:  religion.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		:  [lkarunia]
             * @version  		:  [version]
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
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_RELIGION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long warnId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("30%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Description", "30%");
        ctrlist.addHeader("Warning Point", "10%", "align='center'");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        for (int i = 0; i < objectClass.size(); i++) {
            Warning warning = (Warning) objectClass.get(i);
            Vector rowx = new Vector();
            if (warnId == warning.getOID()) {
                index = i;
            }

            rowx.add(warning.getWarnDesc());
            rowx.add(warning.getWarnPoint());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(warning.getOID()));
        }

        return ctrlist.draw(index);
    }

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidWarning = FRMQueryString.requestLong(request, "hidden_warning_id");

            /*variable declaration*/
            int recordToGet = 10;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "" + PstWarning.fieldNames[PstWarning.FLD_WARN_ID] + " ASC ";

            CtrlWarning ctrlWarning = new CtrlWarning(request);
            ControlLine ctrLine = new ControlLine();
            Vector listWarning = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlWarning.action(iCommand, oidWarning);
            /* end switch*/
            FrmWarning frmWarning = ctrlWarning.getForm();

            /*count list All Warning*/
            int vectSize = PstWarning.getCount(whereClause);

            Warning warning = ctrlWarning.getWarning();
            msgString = ctrlWarning.getMessage();

            /*switch list Warning*/
            /*
            if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidWarning == 0))
            start = PstWarning.findLimitStart(warning.getOID(),recordToGet, whereClause, orderClause);
             */

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlWarning.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listWarning = PstWarning.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listWarning.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listWarning = PstWarning.list(start, recordToGet, whereClause, orderClause);
            }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data Religion</title>
        <script language="JavaScript">
            <!--



            function cmdAdd(){
                document.frmwarning.hidden_warning_id.value="0";
                document.frmwarning.command.value="<%=Command.ADD%>";
                document.frmwarning.prev_command.value="<%=prevCommand%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdAsk(oidWarning){
                document.frmwarning.hidden_warning_id.value=oidWarning;
                document.frmwarning.command.value="<%=Command.ASK%>";
                document.frmwarning.prev_command.value="<%=prevCommand%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdConfirmDelete(oidWarning){
                document.frmwarning.hidden_warning_id.value=oidWarning;
                document.frmwarning.command.value="<%=Command.DELETE%>";
                document.frmwarning.prev_command.value="<%=prevCommand%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }
            function cmdSave(){
                document.frmwarning.command.value="<%=Command.SAVE%>";
                document.frmwarning.prev_command.value="<%=prevCommand%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdEdit(oidWarning){
                document.frmwarning.hidden_warning_id.value=oidWarning;
                document.frmwarning.command.value="<%=Command.EDIT%>";
                document.frmwarning.prev_command.value="<%=prevCommand%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdCancel(oidWarning){
                document.frmwarning.hidden_warning_id.value=oidWarning;
                document.frmwarning.command.value="<%=Command.EDIT%>";
                document.frmwarning.prev_command.value="<%=prevCommand%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdBack(){
                document.frmwarning.command.value="<%=Command.BACK%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdListFirst(){
                document.frmwarning.command.value="<%=Command.FIRST%>";
                document.frmwarning.prev_command.value="<%=Command.FIRST%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdListPrev(){
                document.frmwarning.command.value="<%=Command.PREV%>";
                document.frmwarning.prev_command.value="<%=Command.PREV%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdListNext(){
                document.frmwarning.command.value="<%=Command.NEXT%>";
                document.frmwarning.prev_command.value="<%=Command.NEXT%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }

            function cmdListLast(){
                document.frmwarning.command.value="<%=Command.LAST%>";
                document.frmwarning.prev_command.value="<%=Command.LAST%>";
                document.frmwarning.action="empwarning.jsp";
                document.frmwarning.submit();
            }


            //-------------- script form image -------------------

            function cmdDelPict(oidReligion){
                document.frmimage.hidden_religion_id.value=oidReligion;
                document.frmimage.command.value="<%=Command.POST%>";
                document.frmimage.action="religion.jsp";
                document.frmimage.submit();
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
                //-------------- script control line -------------------
                //-->
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
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                                                    Master Data > Religion<!-- #EndEditable -->
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
                                                                                <form name="frmwarning" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_warning_id" value="<%=oidWarning%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="comment">&nbsp;Warning
                                                                                                            List </td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                                try {
                                                                                                                    if (listWarning.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%= drawList(listWarning, oidWarning)%>
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
                                                                                                                                    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidWarning == 0)) {
                                                                                                                                        cmd = PstWarning.findLimitCommand(start, recordToGet, vectSize);
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
                                                                                                                if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmWarning.errorSize() < 1)) {
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
                                                                                                                    <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add
                                                                                                                            New Warning</a> </td>
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
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmWarning.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td colspan="3" class="listtitle"><%=oidWarning == 0 ? "Add" : "Edit"%>
                                                                                                            Warning</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td colspan="3" class="listtitle">&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="21" valign="middle" width="1%">&nbsp;</td>
                                                                                                        <td height="21" valign="middle" width="7%">&nbsp;</td>
                                                                                                        <td height="21" colspan="2" width="92%" class="comment">*)=
                                                                                                            required</td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="21" valign="top" width="1%">&nbsp;</td>
                                                                                                        <td height="21" valign="top" width="7%">Description</td>
                                                                                                        <td height="21" colspan="2" width="92%">
                                                                                                            <input type="text" name="<%=frmWarning.fieldNames[FrmWarning.FRM_FIELD_WARN_DESC]%>"  value="<%= "" + warning.getWarnDesc()%>" class="formElemen" size="40">
                                                                                                            * <%= frmWarning.getErrorMsg(FrmWarning.FRM_FIELD_WARN_DESC)%></td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="21" valign="top" width="1%">&nbsp;</td>
                                                                                                        <td height="21" valign="top" width="7%">Point</td>
                                                                                                        <td height="21" colspan="2" width="92%">
                                                                                                            <input type="text" name="<%=frmWarning.fieldNames[FrmWarning.FRM_FIELD_WARN_POINT]%>"  value="<%= warning.getWarnPoint()%>" class="formElemen" size="40">
                                                                                                            * <%= frmWarning.getErrorMsg(FrmWarning.FRM_FIELD_WARN_POINT)%></td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="8" valign="middle" width="1%">&nbsp;</td>
                                                                                                        <td height="8" valign="middle" width="7%">&nbsp;</td>
                                                                                                        <td height="8" colspan="2" width="92%">&nbsp;
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top" >
                                                                                                        <td colspan="4" class="command">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidWarning + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidWarning + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidWarning + "')";
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
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
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
                    <%@ include file = "../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
