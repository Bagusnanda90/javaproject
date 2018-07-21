<%-- 
    Document   : pay_payslip_group
    Created on : Jan 24, 2013, 10:55:12 AM
    Author     : Satrya Ramayu
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language ="java" %>
<!-- package java -->
<%@ page import ="java.util.*" %>
<%@page import="java.util.Vector"%>
<!-- package dimata -->
<%@ page import ="com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*" %>
<%@ page import ="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.gui.jsp.ControlList"%>
<%@ page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ page import="com.dimata.util.Command"%>
<%@ page import="com.dimata.harisma.entity.payroll.PstPaySlipGroup"%>
<%@ page import="com.dimata.harisma.entity.payroll.FrmPaySlipGroup"%>
<%@ page import="com.dimata.harisma.entity.payroll.PaySlipGroup"%>
<%@ page import="com.dimata.gui.jsp.ControlLine"%>
<%@ page import="com.dimata.harisma.form.payroll.CtrlPaySlipGroup"%>
<%@ page import="com.dimata.qdep.form.FRMMessage"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT);%>
<%@ include file = "../../main/checkuser.jsp"%>

<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long companyId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Slip Group", "30%");
        ctrlist.addHeader("Description", "50%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        for (int i = 0; i < objectClass.size(); i++) {
            PaySlipGroup paySlipGroup = (PaySlipGroup) objectClass.get(i);
            Vector rowx = new Vector();
            if (companyId == paySlipGroup.getOID()) {
                index = i;
            }

            rowx.add(paySlipGroup.getGroupName());

            rowx.add(paySlipGroup.getGroupDesc());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(paySlipGroup.getOID()));
        }
        return ctrlist.draw(index);
    }

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidPayslipGroup = FRMQueryString.requestLong(request, "hidden_paySlipGroup_id");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_NAME];

    CtrlPaySlipGroup ctrlPaySlipGroup = new CtrlPaySlipGroup(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCompany = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlPaySlipGroup.action(iCommand, oidPayslipGroup);
    /* end switch*/
    FrmPaySlipGroup frmPaySlipGroup = ctrlPaySlipGroup.getForm();

    /*count list All Position*/
    int vectSize = PstPaySlipGroup.getCount(whereClause);

    PaySlipGroup paySlipGroup = ctrlPaySlipGroup.getCompany();
    msgString = ctrlPaySlipGroup.getMessage();

    /*switch list PaySlipGroup*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        //start = PstPaySlipGroup.findLimitStart(paySlipGroup.getOID(),recordToGet, whereClause);
        oidPayslipGroup = paySlipGroup.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlPaySlipGroup.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listCompany = PstPaySlipGroup.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listCompany.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listCompany = PstPaySlipGroup.list(start, recordToGet, whereClause, orderClause);
    }


%>
<html>
    <head>
        <title>HARISMA - Master Data Pay Slip Group</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script language="JavaScript">


            function cmdAdd(){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value="0";
                document.frmpayslipgroup.command.value="<%=Command.ADD%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdAsk(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.ASK%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdConfirmDelete(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.DELETE%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }
            function cmdSave(){
                document.frmpayslipgroup.command.value="<%=Command.SAVE%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdEdit(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.EDIT%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdCancel(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.EDIT%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdBack(){
                document.frmpayslipgroup.command.value="<%=Command.BACK%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListFirst(){
                document.frmpayslipgroup.command.value="<%=Command.FIRST%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.FIRST%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListPrev(){
                document.frmpayslipgroup.command.value="<%=Command.PREV%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.PREV%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListNext(){
                document.frmpayslipgroup.command.value="<%=Command.NEXT%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.NEXT%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListLast(){
                document.frmpayslipgroup.command.value="<%=Command.LAST%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.LAST%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
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

        </script>

    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file =  "../../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle">  
                    <%@ include file = "../../main/mnmain.jsp" %>
                     </td>
            </tr>
            <tr> 
                <td  valign="middle"> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td align="center" background="<%=approot%>/images/harismaMenuLine.jpg"><img src="<%=approot%>/images/harismaMenuLine.jpg" width="0" height="0"></td>
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
                                                Master Data &gt; Pay Slip Group<!-- #EndEditable -->
                                            </strong></font>
                                        </td>
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
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmpayslipgroup" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_paySlipGroup_id" value="<%=oidPayslipGroup%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;PaySlipGroup List </td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                        try {
                                                                                                            if (listCompany.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%= drawList(listCompany, oidPayslipGroup)%>
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
                                                                                                    <%
                                                                                                        if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmPaySlipGroup.errorSize() < 1)) {
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
                                                                                                                            New Pay Slip Group</a> </td>
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
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPaySlipGroup.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr>
                                                                                                        <td class="listtitle"><%=oidPayslipGroup == 0 ? "Add" : "Edit"%> Pay Slip Group</td>
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
                                                                                                                        Pay Slip Group Name</td>
                                                                                                                    <td width="83%">
                                                                                                                        <input type="text" name="<%=frmPaySlipGroup.fieldNames[FrmPaySlipGroup.FRM_FIELD_PAYSLIP_GROUP_NAME]%>"  value="<%= paySlipGroup.getGroupName() == null ? "" : paySlipGroup.getGroupName()%>" class="elemenForm" size="30">
                                                                                                                        *<%=frmPaySlipGroup.getErrorMsg(FrmPaySlipGroup.FRM_FIELD_PAYSLIP_GROUP_NAME)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        Description </td>
                                                                                                                    <td width="83%">
                                                                                                                        <textarea name="<%=frmPaySlipGroup.fieldNames[FrmPaySlipGroup.FRM_FIELD_PAYSLIP_GROUP_DESCRIPTION]%>" class="elemenForm" cols="30" rows="3"><%= paySlipGroup.getGroupDesc() == null ? "" : paySlipGroup.getGroupDesc()%></textarea>
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
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidPayslipGroup + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidPayslipGroup + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidPayslipGroup + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List PaySlipGroup");
                                                                                                                ctrLine.setSaveCaption("Save PaySlipGroup");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete PaySlipGroup");
                                                                                                                ctrLine.setDeleteCaption("Delete PaySlipGroup");

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
                                                                                        <tr  height="130" valign="top"> 
                                                                                            <td></td>
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
    <!-- #EndEditable -->
    <!-- #EndTemplate -->
</html>

