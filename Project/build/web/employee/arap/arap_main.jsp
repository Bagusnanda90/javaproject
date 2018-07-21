<%-- 
    Document   : hutang kariawan
    Created on : April 17, 2015, 3:56:51 PM
    Author     : Priska
--%>
<%@page import="com.dimata.harisma.form.arap.FrmArApItem"%>
<%@page import="com.dimata.harisma.form.arap.CtrlArApItem"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApPayment"%>
<%@page import="com.dimata.harisma.entity.arap.ArApPayment"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.dimata.harisma.entity.arap.PstArApItem"%>
<%@page import="com.dimata.harisma.entity.arap.ArApItem"%>
<%@page import="com.dimata.harisma.entity.payroll.CurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.arap.ArApMain"%>
<%@page import="com.dimata.harisma.entity.arap.PstArApMain"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.arap.CtrlArApMain"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.system.entity.PstSystemProperty"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
    /*
     * Page Name  		:  arApMain.jsp
     * Created on 		:  [date] [time] AM/PM
     *
     * @author  		: Priska_20150417
     * @version  		: -
     */

    /**
     * *****************************************************************
     * Page Description : [project description ... ] Imput Parameters : [input
     * parameter ...] Output : [output ...]
     * *****************************************************************
     */
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
<%@ page import = "com.dimata.harisma.entity.masterdata.location.Location" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!    public String drawList(int command, Vector objectClass, long oidArapItem) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "10%");
        ctrlist.addHeader("Pay Amount", "20%");
        ctrlist.addHeader("Due Date", "10%");
        ctrlist.addHeader("Description", "50%");
        ctrlist.addHeader("Status", "10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();

        ctrlist.reset();
        int index = -1;
        int stat = 0;
        String statPrint = "";

        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setMonetaryDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        for (int i = 0; i < objectClass.size(); i++) {
            ArApItem arApItem = (ArApItem) objectClass.get(i);
            Vector rowx = new Vector();

            if (command == Command.EDIT && oidArapItem == arApItem.getOID()) {
                rowx.add("" + (i + 1));
                rowx.add("<input  type=\"text\" name=\"" + FrmArApItem.fieldNames[FrmArApItem.FRM_ANGSURAN] + "\"   value=\"" + (arApItem.getAngsuran() != 0 ? "Rp. " + df.format(arApItem.getAngsuran()) : "-") + "\" class=\"formElemen\">"
                        + " &nbsp;<a href=\"javascript:cmdSaveEditAmount()\">*Simpan</a>");
                rowx.add("" + arApItem.getDueDate());
                rowx.add("" + arApItem.getDescription());

                rowx.add("" + (arApItem.getArApItemStatus() == 0 ? "Open" : "Close"));

            } else {
                rowx.add("" + (i + 1));
                if(arApItem.getArApItemStatus() == 0){
                    statPrint = " &nbsp;<a href=\"javascript:cmdEditAmount('" + arApItem.getOID() + "')\">*Ubah Nominal</a>";
                    if(stat == 1){
                        statPrint = "";
                    }
                }
                rowx.add((arApItem.getAngsuran() != 0 ? "Rp. " + df.format(arApItem.getAngsuran()) : "-") + statPrint);
                        //(arApItem.getArApItemStatus() == 0 ? " &nbsp;<a href=\"javascript:cmdEditAmount('" + arApItem.getOID() + "')\">*Ubah Nominal</a>" : ""));
                rowx.add("" + arApItem.getDueDate());
                rowx.add("" + arApItem.getDescription());

                rowx.add("" + (arApItem.getArApItemStatus() == 0 ? "Open" : "Close"));
                if(arApItem.getArApItemStatus() == 0){
                    stat = 1;
                }
            }

            lstData.add(rowx);
        }
        return ctrlist.draw(index);
    }

%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    int commandItem = FRMQueryString.requestInt(request, "commandItem");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidArapMain = FRMQueryString.requestLong(request, "hidden_arapMain_id");
    long oidArapItem = FRMQueryString.requestLong(request, "hidden_arapItem_id");

    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
    /*variable declaration*/
    int recordToGet = 50;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    int iErrCodeItem = FRMMessage.NONE;
    String whereClause = " arap_main_id = " + oidArapMain;
    String orderClause = "";

    CtrlArApMain ctrlArApMain = new CtrlArApMain(request);
    CtrlArApItem ctrlArApItem = new CtrlArApItem(request);
    ControlLine ctrLine = new ControlLine();
    Vector listArApMain = new Vector(1, 1);
    Vector listArApItem = new Vector(1, 1);
    Vector listArApPayment = new Vector(1, 1);
    
    /*switch statement */
    iErrCode = ctrlArApMain.action(iCommand, oidArapMain);
    iErrCodeItem = ctrlArApItem.action(commandItem, oidArapItem);
    /* end switch*/
    
    FrmArApMain frmArApMain = ctrlArApMain.getForm();
    FrmArApItem frmArApItem = ctrlArApItem.getForm();
    /*count list All Position*/
    int vectSize = PstArApItem.getCount(whereClause);

    ArApMain arApMain = ctrlArApMain.getArApMain();
    ArApItem arApItem = ctrlArApItem.getArApItem();
    msgString = ctrlArApMain.getMessage();

    
    
    /*switch list ArApMain*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        //start = PstArApMain.findLimitStart(arApMain.getOID(),recordToGet, whereClause);
        oidArapMain = arApMain.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlArApMain.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listArApMain = PstArApMain.list(start, recordToGet, whereClause, orderClause);
    listArApItem = PstArApItem.list(start, recordToGet, "ARAP_MAIN_ID="+arApMain.getOID(), "DUE_DATE asc");

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    //  if (listArApMain.size() < 1 && start > 0) {
    //      if (vectSize - recordToGet > recordToGet) {
    //          start = start - recordToGet;   //go to Command.PREV
    //      } else {
    //          start = 0;
    //          iCommand = Command.FIRST;
    //          prevCommand = Command.FIRST; //go to Command.FIRST
    //      }
    //      listArApMain = PstArApMain.list(start, recordToGet, whereClause, orderClause);
    //  }

    if (listArApMain.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listArApItem = PstArApItem.list(start, recordToGet, whereClause, orderClause);
    }


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - ArApMain</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmArApMain.hidden_arapMain_id.value="0";
                document.frmArApMain.command.value="<%=Command.ADD%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdAsk(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.ASK%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdConfirmDelete(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.DELETE%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main_list.jsp";
                document.frmArApMain.submit();
            }
            function cmdSave(){
                document.frmArApMain.commandItem.value="<%=Command.NONE%>";
                document.frmArApMain.command.value="<%=Command.SAVE%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }
            function cmdSaveEditAmount(){
                document.frmArApMain.commandItem.value="<%=Command.SAVE%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }
            function cmdPost(){
                document.frmArApPayment.command.value="<%=Command.POST%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_main.jsp";
                document.frmArApPayment.submit();
            }

            function cmdEdit(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.EDIT%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }
            
            function cmdEditAmount(arApItemId){
                document.frmArApMain.hidden_arapItem_id.value=arApItemId;
                document.frmArApMain.command.value="<%=Command.EDIT%>";
                document.frmArApMain.commandItem.value="<%=Command.EDIT%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdCancel(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.EDIT%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdBack(){
                document.frmArApMain.command.value="<%=Command.BACK%>";
                document.frmArApMain.action="arap_main_list.jsp";
                document.frmArApMain.submit();
            }

            function cmdListFirst(){
                document.frmArApMain.command.value="<%=Command.FIRST%>";
                document.frmArApMain.prev_command.value="<%=Command.FIRST%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdListPrev(){
                document.frmArApMain.command.value="<%=Command.PREV%>";
                document.frmArApMain.prev_command.value="<%=Command.PREV%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdListNext(){
                document.frmArApMain.command.value="<%=Command.NEXT%>";
                document.frmArApMain.prev_command.value="<%=Command.NEXT%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdListLast(){
                document.frmArApMain.command.value="<%=Command.LAST%>";
                document.frmArApMain.prev_command.value="<%=Command.LAST%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }
            
            function cmdDay(){
                document.getElementById("every").disabled = false;
                document.getElementById("dmy").disabled = false;
                document.getElementById("period").disabled = true;
                document.getElementById("d<%=FrmArApMain.fieldNames[FrmArApMain.FRM_START_OF_PAYMENT_DATE]%>").disabled = false;
                document.getElementById("m<%=FrmArApMain.fieldNames[FrmArApMain.FRM_START_OF_PAYMENT_DATE]%>").disabled = false;
                document.getElementById("y<%=FrmArApMain.fieldNames[FrmArApMain.FRM_START_OF_PAYMENT_DATE]%>").disabled = false;
            }

            function cmdPeriod(){
                document.getElementById("every").disabled = true;
                document.getElementById("dmy").disabled = true;
                document.getElementById("period").disabled = false;
                document.getElementById("d<%=FrmArApMain.fieldNames[FrmArApMain.FRM_START_OF_PAYMENT_DATE]%>").disabled = true;
                document.getElementById("m<%=FrmArApMain.fieldNames[FrmArApMain.FRM_START_OF_PAYMENT_DATE]%>").disabled = true;
                document.getElementById("y<%=FrmArApMain.fieldNames[FrmArApMain.FRM_START_OF_PAYMENT_DATE]%>").disabled = true;
                
            }
            
            function changePlan(){
                var x = document.getElementById("amount");
                var y = document.getElementById("num");
                var plan = document.getElementById("amountPlan");
                var z = x.value/y.value;
                //alert(y.value+" && "+x.value+" && "+z);
                plan.value = z;
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

                    function cmdSearchEmp(){

                        window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmArApMain&empPathId=<%=frmArApMain.fieldNames[frmArApMain.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                    }
                    <%if(iCommand == Command.ADD){%>
                    cmdSearchEmpPay()
                    
                    function cmdSearchEmpPay(){
                        window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmArApMain&empPathId=<%=frmArApMain.fieldNames[frmArApMain.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                       // window.open("<!--%=approot%-->/employee/search/SearchMasterFlow.jsp?formName=FrmArApPayment&empPathId=<!--%=FrmArApPayment.fieldNames[FrmArApPayment.FRM_FIELD_EMPLOYEE_ID]%-->", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                    }
                    <%}%>
        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                                                Employee Deduction &gt; ArApMain<!-- #EndEditable -->
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
                                                                                <form name="frmArApMain" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="commandItem" value="<%=commandItem%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_arapMain_id" value="<%=oidArapMain%>">
                                                                                    <input type="hidden" name="hidden_arapItem_id" value="<%=oidArapItem%>">

                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">

                                                                                        <tr>
                                                                                            <td>&nbsp;
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr>
                                                                                                        <td class="listtitle"><%=oidArapMain == 0 ? "Add" : "Edit"%> ArApMain</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td height="100%">
                                                                                                            <table>
                                                                                                                <tr>
                                                                                                                    <td>Voucher Date</td>
                                                                                                                    <td><%=ControlDate.drawDateWithStyle(FrmArApMain.fieldNames[FrmArApMain.FRM_VOUCHER_DATE], arApMain.getVoucherDate() == null ? new Date() : arApMain.getVoucherDate(), 1, -35, "formElemen")%></td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>Entry Date</td>
                                                                                                                    <td><%=ControlDate.drawDateWithStyle(FrmArApMain.fieldNames[FrmArApMain.FRM_ENTRY_DATE], arApMain.getEntryDate() == null ? new Date() : arApMain.getEntryDate(), 1, -35, "formElemen")%> </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Employee Name</td>
                                                                                                                    <td><input type="text" name="EMP_FULLNAME" value="<% String name = PstEmployee.getEmployeeName(arApMain.getEmployeeId());%><%=(arApMain.getEmployeeId() != 0 ? name : "-")%>" class="elemenForm"> <input type="hidden" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=(arApMain.getEmployeeId())%>" class="formElemen" > <a href="javascript:cmdSearchEmp()">add employee</a>  </td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>Creditor</td>
                                                                                                                    <td>
                                                                                                                        <%
                                                                                                                            Vector creditor_value = new Vector(1, 1);
                                                                                                                            Vector creditor_key = new Vector(1, 1);

                                                                                                                            Vector listcre = PstArApCreditor.list(0, 0, "", "");
                                                                                                                            for (int i = 0; i < listcre.size(); i++) {
                                                                                                                                ArApCreditor apCreditor = (ArApCreditor) listcre.get(i);
                                                                                                                                creditor_key.add(apCreditor.getCreditorName());
                                                                                                                                creditor_value.add(String.valueOf(apCreditor.getOID()));
                                                                                                                            }

                                                                                                                        %>
                                                                                                                        <%=  ControlCombo.draw(FrmArApMain.fieldNames[FrmArApMain.FRM_CONTACT_ID], "formElemen", null, "" + (arApMain.getContactId() != 0 ? arApMain.getContactId() : arApMain.getContactId()), creditor_value, creditor_key)%> * <%= frmArApMain.getErrorMsg(frmArApMain.FRM_CONTACT_ID)%>

                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Bill Date</td>
                                                                                                                    <td><%=ControlDate.drawDateWithStyle(FrmArApMain.fieldNames[FrmArApMain.FRM_NOTA_DATE], arApMain.getNotaDate() == null ? new Date() : arApMain.getNotaDate(), 1, -35, "formElemen")%></td>  
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>Bill Number</td>
                                                                                                                    <td><input  type="text" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_NOTA_NO]%>"   value="<%=(arApMain.getNotaNo() != null ? arApMain.getNotaNo() : "")%>" class="formElemen"></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Amount / nominal</td>
                                                                                                                    <td>
                                                                                                                        <%
                                                                                                                            Vector cur_value = new Vector(1, 1);
                                                                                                                            Vector cur_key = new Vector(1, 1);

                                                                                                                            Vector listcur = PstCurrencyType.list(0, 0, "", "");
                                                                                                                            for (int i = 0; i < listcur.size(); i++) {
                                                                                                                                CurrencyType cur = (CurrencyType) listcur.get(i);
                                                                                                                                cur_key.add(cur.getCode());
                                                                                                                                cur_value.add(String.valueOf(cur.getOID()));
                                                                                                                            }

                                                                                                                        %>
                                                                                                                        <%=  ControlCombo.draw(FrmArApMain.fieldNames[FrmArApMain.FRM_ID_CURRENCY], "formElemen", null, "" + (arApMain.getIdCurrency() != 0 ? arApMain.getIdCurrency() : arApMain.getIdCurrency()), cur_value, cur_key)%> * <%= frmArApMain.getErrorMsg(frmArApMain.FRM_ID_CURRENCY)%>

                                                                                                                        <input  type="text" id="amount" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_AMOUNT]%>"   value="<%=(arApMain.getAmount() != 0 ? Formater.formatNumber(arApMain.getAmount(), "") : "")%>" class="formElemen">  
                                                                                                                    </td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td><input  type="hidden" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_RATE]%>"   value="<%=(arApMain.getRate() != 0 ? arApMain.getRate() : "1")%>" class="formElemen"> </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Number Of Payment</td>
                                                                                                                    <td><input  type="text" id="num" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_NUMBER_OF_PAYMENT]%>"   value="<%=(arApMain.getNumberOfPayment() != 0 ? arApMain.getNumberOfPayment() : "")%>" class="formElemen" onblur="changePlan()">  </td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Payment Amount Plan <p id="demo"></p></td>
                                                                                                                    <td><input  type="text" id="amountPlan" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_PAYMENT_AMOUNT_PLAN]%>"   value="<%=(arApMain.getPayment_amount_plan() != 0 ? arApMain.getPayment_amount_plan() : "")%>" class="formElemen">  </td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Period of Payment : </td>
                                                                                                                    <td colspan="2">
                                                                                                                        <input type="radio" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_PERIOD_TYPE]%>" value="0" <%=(arApMain.getPeriodType() == 0 ? "checked='checked'" : "")%> onclick="cmdDay()"/>&nbsp;&nbsp;Every&nbsp;&nbsp;
                                                                                                                        <input id="every" type="text" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_PERIOD_EVERY]%>"   value="<%=(arApMain.getPeriodeEvery() != 0 ? arApMain.getPeriodeEvery() : "1")%>" class="formElemen">
                                                                                                                        <%= ControlCombo.draw(FrmArApMain.fieldNames[FrmArApMain.FRM_PERIOD_EVERY_DMY], "formElemen", null, "" + (arApMain.getPeriodeEveryDMY() != 0 ? arApMain.getPeriodeEveryDMY() : arApMain.getPeriodeEveryDMY()), FrmArApMain.getPerValue(), FrmArApMain.getPerKey(), "id='dmy'")%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td></td>
                                                                                                                    <%
                                                                                                                        Vector period_value = new Vector(1, 1);
                                                                                                                        Vector period_key = new Vector(1, 1);

                                                                                                                        Vector listPeriod = PstPeriod.list(0, 0, "", "PERIOD");
                                                                                                                        for (int i = 0; i < listPeriod.size(); i++) {
                                                                                                                            Period period = (Period) listPeriod.get(i);
                                                                                                                            period_key.add(period.getPeriod());
                                                                                                                            period_value.add(String.valueOf(period.getOID()));
                                                                                                                        }

                                                                                                                    %>
                                                                                                                    <td colspan="2"><input type="radio" name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_PERIOD_TYPE]%>" value="1" <%=(arApMain.getPeriodType() == 1 ? "checked='checked'" : "")%> onclick="cmdPeriod()"/>&nbsp;&nbsp;Periode&nbsp;&nbsp;
                                                                                                                        <%= ControlCombo.draw(FrmArApMain.fieldNames[FrmArApMain.FRM_PERIOD_ID], "formElemen", null, ""+arApMain.getPeriodId() , period_value, period_key, "id='period'")%>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Start Of Payment Date</td>
                                                                                                                    <td><%=ControlDate.drawDateWithStyle(FrmArApMain.fieldNames[FrmArApMain.FRM_START_OF_PAYMENT_DATE], arApMain.getStartofpaymentdate() == null ? new Date() : arApMain.getStartofpaymentdate(), 1, -35, "formElemen")%> </td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Deskripsi</td>
                                                                                                                    <td>
                                                                                                                        <textarea name="<%=FrmArApMain.fieldNames[FrmArApMain.FRM_DESCRIPTION]%>" class="formElemen" rows="2" cols="30"><%=arApMain.getDescription()%></textarea> 
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td>Payroll Component Deduction</td>
                                                                                                                    <td>

                                                                                                                        <%
                                                                                                                            Vector CompDecd_value = new Vector(1, 1);
                                                                                                                            Vector CompDecd_key = new Vector(1, 1);

                                                                                                                            String whereClauseDeduction = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_DEDUCTION;
                                                                                                                            Vector listCompDecd = PstPayComponent.list(0, 0, whereClauseDeduction, "");
                                                                                                                            for (int i = 0; i < listCompDecd.size(); i++) {
                                                                                                                                PayComponent CompDecd = (PayComponent) listCompDecd.get(i);
                                                                                                                                CompDecd_key.add(CompDecd.getCompName());
                                                                                                                                CompDecd_value.add(String.valueOf(CompDecd.getOID()));
                                                                                                                            }

                                                                                                                        %>
                                                                                                                        <%=  ControlCombo.draw(FrmArApMain.fieldNames[FrmArApMain.FRM_COMPONENT_DEDUCTION_ID], "formElemen", null, "" + (arApMain.getComponentDeductionId() != 0 ? arApMain.getComponentDeductionId() : arApMain.getComponentDeductionId()), CompDecd_value, CompDecd_key)%> * <%= frmArApMain.getErrorMsg(frmArApMain.FRM_COMPONENT_DEDUCTION_ID)%>
                                                                                                                    </td>
                                                                                                                </tr>

                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>


                                                                                                    <td colspan="2">
                                                                                                        <%
                                                                                                            ctrLine.setLocationImg(approot + "/images");
                                                                                                            ctrLine.initDefault();
                                                                                                            ctrLine.setTableWidth("80%");
                                                                                                            String scomDel = "javascript:cmdAsk('" + oidArapMain + "')";
                                                                                                            String sconDelCom = "javascript:cmdConfirmDelete('" + oidArapMain + "')";
                                                                                                            String scancel = "javascript:cmdEdit('" + oidArapMain + "')";
                                                                                                            ctrLine.setBackCaption("Back to List");
                                                                                                            ctrLine.setCommandStyle("buttonlink");
                                                                                                            ctrLine.setBackCaption("Back to List ArApMain");
                                                                                                            ctrLine.setSaveCaption("Save ArApMain");
                                                                                                            ctrLine.setConfirmDelCaption("Yes Delete ArApMain");
                                                                                                            ctrLine.setDeleteCaption("Delete ArApMain");

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
                                                                                                    <% if (listArApItem.size() > 0) {%>

                                                                                                    <tr>
                                                                                                    <span class="bullettitle1">
                                                                                                        <li>Menghapus Maupun Mengedit Employee Deduction akan otomatis menghapus data item dan Payment realyzation </li>
                                                                                                    </span>
                                                                                        </tr>
                                                                                        <td>Payment Plan</td>

                                                                                        <tr align="left" valign="top">
                                                                                            <td height="22" valign="middle" colspan="3">
                                                                                                <%= drawList(commandItem, listArApItem, oidArapItem)%>
                                                                                            </td>
                                                                                        </tr>


                                                                                        <% }%>
                                                                                        <tr>
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
<%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
<tr>
    <td valign="bottom">
        <!-- untuk footer -->
        <%@include file="../../footer.jsp" %>
    </td>

</tr>
<%} else {%>
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
<!-- #EndTemplate -->
</html>

