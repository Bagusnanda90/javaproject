<%-- 
    Document   : outsource_plan_edit
    Created on : Sep 14, 2015, 6:36:01 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.harisma.session.outsource.OutsourceReport"%>
<%@page import="com.dimata.harisma.entity.outsource.InOutReport"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmployee"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceCost"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutsrcCostProv"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceCostMaster"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceCostMaster"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourceCostMaster"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourceCostMaster"%>
<%@page import="com.dimata.harisma.entity.outsource.OutsrcCostProvDetail"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutsrcCostProvDetail"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutsrcCostProvDetail"%>
<%@page import="com.dimata.harisma.entity.outsource.OutsrcCostProv"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutsrcCostProv"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutsrcCostProv"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceCost"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourceCost"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourceCost"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%-- 
    Document   : outsource_plan_list
    Created on : Sep 14, 2015, 3:03:00 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.harisma.entity.outsource.PstOutSourcePlan"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourcePlan"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_MASTER_PLAN);%>
<%@ include file = "../main/checkuser.jsp" %>

<%!    public String drawList(int iCommand, int iCommandDetail, Vector vDataCostProvDetail, Vector vOutSourceCostProv, Vector vMaster, long oidOutSourceCost, long oidOutsrcCostProv, FrmOutsrcCostProv frmOutsrcCostProv, FrmOutsrcCostProvDetail frmOutsrcCostProvDetail) {
        Vector result = new Vector(1, 1);
        ControlList ctrlist = new ControlList();
        //if (vOutSourceCostProv != null && vOutSourceCostProv.size() > 0) {
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Posisi", "1%", "2", "0");
        ctrlist.addHeader("Penyedia", "6%", "2", "0");
        ctrlist.addHeader("Jumlah Karyawan  di Akhir Periode", "6%", "0", "3");
        for (int i = 0; i < vMaster.size(); i++) {
            OutSourceCostMaster outSourceCostMaster = (OutSourceCostMaster) vMaster.get(i);
            ctrlist.addHeader("" + outSourceCostMaster.getCostName(), "2%", "0", "0");
            ctrlist.addHeader("Catatan", "2%", "0", "0");
        }
        ctrlist.addHeader("Total Biaya Bulan Ini", "2%", "0", "0");
        ctrlist.addHeader("Biaya dari Awal tahun sdgn bulan ini", "2%", "0", "3");
        if (iCommand != Command.EDIT) {
            ctrlist.setLinkRow(0);
        }
        ctrlist.setLinkSufix("");

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Vector val_pos = new Vector(1, 1);
        Vector key_pos = new Vector(1, 1);
        Vector vPos = PstPosition.listAll();
        for (int k = 0; k < vPos.size(); k++) {
            Position position = (Position) vPos.get(k);
            val_pos.add("" + position.getOID());
            key_pos.add("" + position.getPosition());
        }

        Vector val_cl = new Vector(1, 1);
        Vector key_cl = new Vector(1, 1);
        Vector vcl = PstContactList.listAll();
        for (int k = 0; k < vcl.size(); k++) {
            ContactList contactList = (ContactList) vcl.get(k);
            val_cl.add("" + contactList.getOID());
            key_cl.add("" + contactList.getCompName());
        }

        // vector of data will used in pdf report
        Vector vectDataToPdf = new Vector(1, 1);
        Vector rowx = new Vector(1, 1);

        int sum = 0;
        int startMonth = 1;
        int iVal = 0;
        Date dateNow = new Date();
        String conDate = Formater.formatDate(dateNow, "M");
        int month = Integer.parseInt(conDate);
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);


        for (int i = 0; i < vOutSourceCostProv.size(); i++) {
            OutsrcCostProv outsrcCostProv = (OutsrcCostProv) vOutSourceCostProv.get(i);
            rowx = new Vector();

            if (iCommand == Command.EDIT && oidOutsrcCostProv != 0 && outsrcCostProv.getOID() == oidOutsrcCostProv) {

                rowx.add("<input type=\"hidden\" name=\"" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_OUTSOURCE_COST_ID] + "\"  value=\"" + oidOutSourceCost + "\" class=\"elemenForm\" size=\"30\">"
                        + "" + ControlCombo.draw("" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_POSITION_ID], null, "" + outsrcCostProv.getPositionId(), val_pos, key_pos, "" + outsrcCostProv.getPositionId(), "formElemen"));
                rowx.add("" + ControlCombo.draw("" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_PROVIDER_ID], null, "" + outsrcCostProv.getProviderId(), val_cl, key_cl, "" + outsrcCostProv.getProviderId(), "formElemen"));
                rowx.add("<input type=\"text\" name=\"" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_NUMBER_OF_PERSON] + "\"  value=\"" + outsrcCostProv.getNumberOfPerson() + "\" class=\"elemenForm\" size=\"30\">");

                Vector vOutSourceCostProvById = PstOutsrcCostProvDetail.list(0, 0, "OUTSRC_COST_PROVIDER_ID=" + outsrcCostProv.getOID(), "");
                for (int j = 0; j < vOutSourceCostProvById.size(); j++) {
                    OutsrcCostProvDetail outsrcCostProvDetail = (OutsrcCostProvDetail) vOutSourceCostProvById.get(j);
                    rowx.add("<input type=\"text\" name=\"" + frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_COST_VAL] + "_" + outsrcCostProvDetail.getOutsrcCostId() + "\"  value=\"" + outsrcCostProvDetail.getCostVal() + "\" class=\"elemenForm\" size=\"30\">");
                    rowx.add("<input type=\"text\" name=\"" + frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_NOTE] + "_" + outsrcCostProvDetail.getOutsrcCostId() + "\"  value=\"" + outsrcCostProvDetail.getNote() + "\" class=\"elemenForm\" size=\"30\">");
                }
            } else {

                rowx.add("" + outsrcCostProv.getPositionName());
                rowx.add("" + outsrcCostProv.getProviderName());
                rowx.add("" + outsrcCostProv.getNumberOfPerson());

                Vector vOutSourceCostProvById = PstOutsrcCostProvDetail.list(0, 0, "OUTSRC_COST_PROVIDER_ID=" + outsrcCostProv.getOID(), "");

                for (int j = 0; j < vOutSourceCostProvById.size(); j++) {
                    OutsrcCostProvDetail outsrcCostProvDetail = (OutsrcCostProvDetail) vOutSourceCostProvById.get(j);
                    String formatVal = numberFormatter.format(outsrcCostProvDetail.getCostVal());
                    rowx.add("" + formatVal);
                    rowx.add("" + outsrcCostProvDetail.getNote());
                    double val = outsrcCostProvDetail.getCostVal();
                    iVal = (int) val;
                    sum = sum + iVal;
                }
                String formatAvg = numberFormatter.format(sum);
                rowx.add("" + formatAvg);
                int jmlh = month * sum;

                String formatJml = numberFormatter.format(jmlh);
                rowx.add("" + formatJml);
                jmlh = 0;
                sum = 0;
            }
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(outsrcCostProv.getOID()));
        }
        rowx = new Vector();
        if (iCommand == Command.ADD) {

            rowx.add("<input type=\"hidden\" name=\"" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_OUTSOURCE_COST_ID] + "\"  value=\"" + oidOutSourceCost + "\" class=\"elemenForm\" size=\"30\">"
                    + "" + ControlCombo.draw("" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_POSITION_ID], null, "", val_pos, key_pos, "", "formElemen"));
            rowx.add("" + ControlCombo.draw("" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_PROVIDER_ID], null, "", val_cl, key_cl, "", "formElemen"));
            rowx.add("<input type=\"text\" name=\"" + frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_NUMBER_OF_PERSON] + "\"  value=\"\" class=\"elemenForm\" size=\"30\">");
            for (int i = 0; i < vMaster.size(); i++) {
                OutSourceCostMaster outSourceCostMaster = (OutSourceCostMaster) vMaster.get(i);
                rowx.add("<input type=\"text\" name=\"" + frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_COST_VAL] + "_" + outSourceCostMaster.getOID() + "\"  value=\"\" class=\"elemenForm\" size=\"30\">");
                rowx.add("<input type=\"text\" name=\"" + frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_NOTE] + "_" + outSourceCostMaster.getOID() + "\"  value=\"\" class=\"elemenForm\" size=\"30\">");
            }
            rowx.add("");
            rowx.add("");
            lstData.add(rowx);
        }
        //}
        return ctrlist.draw(0);
    }
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidEmployee = FRMQueryString.requestLong(request, "hidden_employee_id");
    long oidCompany = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID]);
    long oidDivision = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID]);
    long oidPosition = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROVIDER_ID]);

    Date selectedDateFrom = FRMQueryString.requestDate(request, "start_date");
    Date selectedDateTo = FRMQueryString.requestDate(request, "end_date");
    int iErrCode = 0;

    String startDate = Formater.formatDate(selectedDateFrom, "yyyy-MM-dd");
    String endDate = Formater.formatDate(selectedDateTo, "yyyy-MM-dd");

    Vector vListInOut = new Vector(1, 1);
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    FrmEmployee frmEmployee = ctrlEmployee.getForm();
    Employee employee = ctrlEmployee.getEmployee();
    
    iErrCode = ctrlEmployee.action(iCommand, oidEmployee, request, "", 0);
    

    vListInOut = OutsourceReport.listInOutSum(oidCompany, oidDivision, oidPosition, startDate, endDate);

%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>DIMATA HARISMA - Outsource</title>
        <script language="JavaScript">
            function cmdView() {
                document.frinout.command.value = "<%=Command.LIST%>";
                document.frinout.action = "report_in_out.jsp";
                document.frinout.submit();
            }
            
            function cmdNextProses(oid){
                document.frinout.command.value = "<%=Command.CONFIRM%>";
                document.frinout.hidden_outsource_id.value=oid;
                document.frinout.action = "report_in_out.jsp";
                document.frinout.submit();
            }
            
            function cmdViewGenerate(oid){
                document.frinout.command.value = "<%=Command.VIEW%>";
                document.frinout.hidden_outsource_id.value=oid;
                document.frinout.action = "report_in_out.jsp";
                document.frinout.submit();
            }
            
            function cmdGenerate(oid){
                document.frinout.command.value = "<%=Command.SUBMIT%>";
                document.frinout.hidden_outsource_id.value=oid;
                document.frinout.action = "report_in_out_generate.jsp";
                document.frinout.submit();
            }
            function cmdSave(val) {
                
                if(val==0){
                    document.frinout.icommanddetail.value=<%=Command.SAVE%>;
                } else {
                    document.frinout.icommanddetail.value=<%=Command.EDIT%>;
                    document.frinout.command.value = "<%=Command.SAVE%>";
                }
                document.frinout.action = "report_in_out.jsp";
                document.frinout.submit();
            }
            function cmdBack() {
                document.frinout.command.value = "<%=Command.LIST%>";
                document.frinout.action = "report_in_out.jsp";
                document.frinout.submit();
            }
            
            function cmdAddNew(oidSource) {
                document.frinout.hidden_outsource_id.value=oidSource;
                document.frinout.command.value = "<%=Command.ADD%>";
                document.frinout.icommanddetail.value=<%=Command.EDIT%>;
                document.frinout.action = "report_in_out.jsp";
                document.frinout.submit();
            }
            
            function cmdEdit(oid){
                
                document.frinout.hidden_outsourcecostprov_id.value=oid;
                
                //document.frinout.icommanddetail.value=3;

                document.frinout.command.value="<%=Command.EDIT%>";

                document.frinout.action="report_in_out.jsp";

                document.frinout.submit();

            }
            
            function cmdDelete(oid,val){
                
                if(val == 0){
                    //main
                    document.frinout.hidden_outsource_id.value=oid;

                    document.frinout.icommanddetail.value="<%=Command.DELETE%>";

                    document.frinout.action="report_in_out.jsp";

                    document.frinout.submit();
                    
                } else {
                    document.frinout.hidden_outsourcecostprov_id.value=oid;

                    document.frinout.command.value="<%=Command.DELETE%>";

                    document.frinout.action="report_in_out.jsp";

                    document.frinout.submit();
                }
		

            }
            
            function cmdCancel(){
                
                document.frinout.command.value="<%=Command.NONE%>";

                document.frinout.action="report_in_out.jsp";

                document.frinout.submit();

            }

            //-------------- script control line -------------------
            function MM_swapImgRestore() { //v3.0
                var i, x, a = document.MM_sr;
                for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
                    x.src = x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d = document;
                if (d.images) {
                    if (!d.MM_p)
                        d.MM_p = new Array();
                    var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
                    for (i = 0; i < a.length; i++)
                        if (a[i].indexOf("#") != 0) {
                            d.MM_p[j] = new Image;
                            d.MM_p[j++].src = a[i];
                        }
                }
            }

            function MM_findObj(n, d) { //v4.0
                var p, i, x;
                if (!d)
                    d = document;
                if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
                    d = parent.frames[n.substring(p + 1)].document;
                    n = n.substring(0, p);
                }
                if (!(x = d[n]) && d.all)
                    x = d.all[n];
                for (i = 0; !x && i < d.forms.length; i++)
                    x = d.forms[i][n];
                for (i = 0; !x && d.layers && i < d.layers.length; i++)
                    x = MM_findObj(n, d.layers[i].document);
                if (!x && document.getElementById)
                    x = document.getElementById(n);
                return x;
            }

            function MM_swapImage() { //v3.0
                var i, j = 0, x, a = MM_swapImage.arguments;
                document.MM_sr = new Array;
                for (i = 0; i < (a.length - 2); i += 3)
                    if ((x = MM_findObj(a[i])) != null) {
                        document.MM_sr[j++] = x;
                        if (!x.oSrc)
                            x.oSrc = x.src;
                        x.src = a[i + 2];
                    }
            }
        </script>
        <!-- update by devin 2014-01-29 -->
        <style type="text/css">
            .tooltip {
                display:none;
                position:absolute;
                border:1px solid #333;
                background-color:#161616;
                border-radius:5px;
                padding:10px;
                color:#fff;
                font-size:12px Arial;
            }
        </style>
        <!-- update by devin 2014-01-29 -->
        <style type="text/css">

            .bdr{border-bottom:2px dotted #0099FF;}

            .highlight {
                color: #090;
            }

            .example {
                color: #08C;
                cursor: pointer;
                padding: 4px;
                border-radius: 4px;
            }

            .example:after {
                font-family: Consolas, Courier New, Arial, sans-serif;
                content: '?';
                margin-left: 6px;
                color: #08C;
            }

            .example:hover {
                background: #F2F2F2;
            }

            .example.dropdown-open {
                background: #888;
                color: #FFF;
            }

            .example.dropdown-open:after {
                color: #FFF;
            }

        </style>
        <!-- update by devin 2014-01-29 -->
        <script type="text/javascript">
            $(document).ready(function() {
                // Tooltip only Text
                $('.masterTooltip').hover(function() {
                    // Hover over code
                    var title = $(this).attr('title');
                    $(this).data('tipText', title).removeAttr('title');
                    $('<p class="tooltip"></p>')
                    .text(title)
                    .appendTo('body')
                    .fadeIn('fast');
                }, function() {
                    // Hover out code
                    $(this).attr('title', $(this).data('tipText'));
                    $('.tooltip').remove();
                }).mousemove(function(e) {
                    var mousex = e.pageX + 20; //Get X coordinates
                    var mousey = e.pageY + 10; //Get Y coordinates
                    $('.tooltip')
                    .css({top: mousey, left: mousex})
                });
            });
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css"> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Laporan Alih Daya &gt; Form Keluar Masuk Alih Daya<!-- #EndEditable --> </strong></font> 
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
                                                                            <td valign="top"> <!-- #BeginEditable "content" -->
                                                                                <form name="frinout" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="hidden_employee_id" value="<%=oidEmployee%>">

                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <tr>
                                                                                            <td><hr></td>
                                                                                        </tr>
                                                                                        <%if (iCommand != Command.CONFIRM) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="80%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr> 
                                                                                                        <td width="6%" nowrap="nowrap"><div align="left">Perusahaan</div></td>
                                                                                                        <td width="30%" nowrap="nowrap">:
                                                                                                            <%
                                                                                                                Vector val_company = new Vector(1, 1);
                                                                                                                Vector key_company = new Vector(1, 1);
                                                                                                                Vector vCompany = PstPayGeneral.listAll();
                                                                                                                for (int k = 0; k < vCompany.size(); k++) {
                                                                                                                    PayGeneral payGeneral = (PayGeneral) vCompany.get(k);
                                                                                                                    val_company.add("" + payGeneral.getOID());
                                                                                                                    key_company.add("" + payGeneral.getCompanyName());
                                                                                                                }
                                                                                                            %>
                                                                                                            <%=ControlCombo.draw("" + frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID], null, "" + employee.getCompanyId(), val_company, key_company, "", "formElemen")%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="6%" nowrap="nowrap"><div align="left">Lokasi</div></td>
                                                                                                        <td width="30%" nowrap="nowrap">:
                                                                                                            <%
                                                                                                                Vector val_lok = new Vector(1, 1);
                                                                                                                Vector key_lok = new Vector(1, 1);
                                                                                                                Vector vlok = PstDivision.listAll();
                                                                                                                val_lok.add("0");
                                                                                                                key_lok.add("Tampilkan Semua");
                                                                                                                for (int k = 0; k < vlok.size(); k++) {
                                                                                                                    Division division = (Division) vlok.get(k);
                                                                                                                    val_lok.add("" + division.getOID());
                                                                                                                    key_lok.add("" + division.getDivision());
                                                                                                                }
                                                                                                            %>
                                                                                                            <%=ControlCombo.draw("" + frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID], null, "" + employee.getDivisionId(), val_lok, key_lok, "", "formElemen")%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr> 
                                                                                                        <td width="5%" nowrap="nowrap"><div align="left">Periode</div> </td>
                                                                                                        <td width="59%" nowrap="nowrap">:
                                                                                                            <%=ControlDate.drawDateWithStyle("start_date", selectedDateFrom != null ? selectedDateFrom : new Date(), 10, -25, "formElemen", "")%>
                                                                                                            &nbsp;&nbsp;sampai&nbsp;&nbsp;
                                                                                                            <%=ControlDate.drawDateWithStyle("end_date", selectedDateTo != null ? selectedDateTo : new Date(), 10, -25, "formElemen", "")%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="6%" nowrap="nowrap"><div align="left">Provider</div></td>
                                                                                                        <td width="30%" nowrap="nowrap">:
                                                                                                            <%
                                                                                                                Vector val_cl = new Vector(1, 1);
                                                                                                                Vector key_cl = new Vector(1, 1);
                                                                                                                Vector vcl = PstContactList.listAll();
                                                                                                                val_cl.add("0");
                                                                                                                key_cl.add("Tampilkan Semua");
                                                                                                                for (int k = 0; k < vcl.size(); k++) {
                                                                                                                    ContactList contactList = (ContactList) vcl.get(k);
                                                                                                                    val_cl.add("" + contactList.getOID());
                                                                                                                    key_cl.add("" + contactList.getCompName());
                                                                                                                }
                                                                                                            %>
                                                                                                            <%=ControlCombo.draw("" + frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROVIDER_ID], null, "" + employee.getProviderID(), val_cl, key_cl, "", "formElemen")%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                        <%if (privAdd) {%>
                                                                                        <tr> 
                                                                                            <td width="88%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                    <tr> 
                                                                                                        <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSearchOn.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Night Shift"></a></td>
                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdView()">View Plan</a></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                    </table>
                                                                                    <% if (iCommand == Command.LIST) {%>
                                                                                    <table width="100%" border="0" class="listgen">
                                                                                        <%
                                                                                            if(vListInOut.size() != 0) {
                                                                                        %>
                                                                                        <tr class="listgentitle">
                                                                                            <td>&nbsp;</td>
                                                                                            <td>Posisi</td>
                                                                                            <%for(int i = 0; i < vListInOut.size(); i++){
                                                                                                InOutReport inOutReport = (InOutReport)vListInOut.get(i);
                                                                                                
                                                                                                String positionName = PstPosition.getPositionName(""+inOutReport.getPositionId());
                                                                                                
                                                                                            %>
                                                                                            <td colspan="3"><%=positionName%></td>
                                                                                            <%}%>
                                                                                            <td colspan="5">Jumlah Total</td>
                                                                                        </tr>
                                                                                        <tr class="listgentitle">
                                                                                            <td>&nbsp;</td>
                                                                                            <td>&nbsp;</td>
                                                                                            <%for(int i = 0; i < vListInOut.size(); i++){
                                                                                            %>
                                                                                            <td>Penyedia</td>
                                                                                            <td>Masuk</td>
                                                                                            <td>Keluar</td>
                                                                                            <%}%>
                                                                                            <td>Masuk</td>
                                                                                            <td>Keluar</td>
                                                                                            <td>Selisih</td>
                                                                                            <td>Awal</td>
                                                                                            <td>Akhir</td>
                                                                                        </tr>
                                                                                        <%
                                                                                        String checkDiv ="";
                                                                                        int sumInRight = 0;
                                                                                        int sumOutRight = 0;
                                                                                        int totalIn = 0;
                                                                                        int totalOut = 0;
                                                                                        int totalSelisih = 0;
                                                                                        int totalAwal = 0;
                                                                                        int totalAkhir = 0;
                                                                                        int no = 1;
                                                                                        
                                                                                        for(int i = 0; i < vListInOut.size(); i++){
                                                                                            InOutReport inOutReport = (InOutReport)vListInOut.get(i);
                                                                                            
                                                                                            String divisionName = PstDivision.getDivisionName("DIVISION_ID="+inOutReport.getDivisionId(), "");
                                                                                            
                                                                                            int sumAwal = OutsourceReport.getTotalInOutAwal(oidCompany, inOutReport.getDivisionId(), startDate);
                                                                                            
                                                                                        %>
                                                                                        <tr class="listgensell">
                                                                                            <%if(!divisionName.equals(checkDiv)){%>
                                                                                            <td><%=no++%></td>
                                                                                            <td><%=divisionName%></td>
                                                                                            <%}else{%>
                                                                                            <td></td>
                                                                                            <td></td>
                                                                                            <%}%>
                                                                                            <%for(int j = 0; j < vListInOut.size(); j++){
                                                                                                InOutReport inOutReportVal = (InOutReport)vListInOut.get(j);
                                                                                                
                                                                                                if(inOutReport.getDivisionId() == inOutReportVal.getDivisionId() && inOutReport.getPositionId() == inOutReportVal.getPositionId()) {
                                                                                                
                                                                                                String providerName = PstContactList.getProviderName("CONTACT_ID="+inOutReportVal.getProviderId(), "");
                                                                                            %>
                                                                                            <td><%=providerName%></td>
                                                                                            <td><%=inOutReportVal.getIn()%></td>
                                                                                            <td><%=inOutReportVal.getOut()%></td>
                                                                                            
                                                                                            <% 
                                                                                                sumInRight = sumInRight + inOutReportVal.getIn(); 
                                                                                                sumOutRight = sumOutRight + inOutReportVal.getOut();
                                                                                                
                                                                                                totalIn = totalIn + sumInRight;
                                                                                                totalOut = totalOut + sumOutRight;
                                                                                            %>
                                                                                                <%}else{%>
                                                                                            <td>-</td>
                                                                                            <td>0</td>
                                                                                            <td>0</td>
                                                                                                <%}%>
                                                                                            <%}
                                                                                                int minInOutRight = sumInRight-sumOutRight;
                                                                                                totalSelisih = totalSelisih+minInOutRight;
                                                                                       
                                                                                            %>
                                                                                            <td><%=sumInRight%></td>
                                                                                            <td><%=sumOutRight%></td>
                                                                                            <td><%=minInOutRight%></td>
                                                                                            <td><%=sumAwal%></td>
                                                                                            <% int akhir = sumAwal+minInOutRight;%>
                                                                                            <td><%=akhir%></td>
                                                                                            <%
                                                                                                totalAwal = totalAwal + sumAwal;
                                                                                                totalAkhir = totalAkhir + akhir;
                                                                                                sumInRight = 0;
                                                                                                sumOutRight = 0;
                                                                                            %>
                                                                                        </tr>
                                                                                        <%
                                                                                        checkDiv = divisionName;
                                                                                        }
                                                                                        %>
                                                                                        <tr class="listgentitle">
                                                                                            <td>&nbsp;</td>
                                                                                            <td>GRAND TOTAL</td>
                                                                                            <%
                                                                                            int sumInLeft = 0;
                                                                                            int sumOutLeft = 0;
                                                                                            for(int i = 0; i < vListInOut.size(); i++){
                                                                                                InOutReport inOutReport = (InOutReport)vListInOut.get(i);
                                                                                                
                                                                                                for(int j = 0; j < vListInOut.size(); j++){
                                                                                                    InOutReport inOutReportSum = (InOutReport)vListInOut.get(j);
                                                                                                
                                                                                                    if(inOutReport.getPositionId() == inOutReportSum.getPositionId()){
                                                                                                        sumInLeft = sumInLeft + inOutReportSum.getIn();
                                                                                                        sumOutLeft = sumOutLeft + inOutReportSum.getOut();
                                                                                                    }
                                                                                                }
                                                                                                
                                                                                            %>
                                                                                            <td>&nbsp;</td>
                                                                                            <td><%=sumInLeft%></td>
                                                                                            <td><%=sumOutLeft%></td>
                                                                                            <%
                                                                                                sumInLeft = 0;
                                                                                            }
                                                                                            %>
                                                                                            <td><%=totalIn%></td>
                                                                                            <td><%=totalOut%></td>
                                                                                            <td><%=totalSelisih%></td>
                                                                                            <td><%=totalAwal%></td>
                                                                                            <td><%=totalAkhir%></td>
                                                                                        </tr>
                                                                                        <%} else {%>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <%
                                                                                                        out.println("<div class=\"msginfo\">&nbsp;&nbsp;No in out data found ...</div>");
                                                                                                    %>
                                                                                                </td>
                                                                                            </tr>
                                                                                        <%}%>
                                                                                    </table>
                                                                                    <%}%>
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" --> 
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>

