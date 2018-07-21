<%-- 
    Document   : outsource_plan_edit
    Created on : Sep 14, 2015, 6:36:01 PM
    Author     : dimata005
--%>

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

<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
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
            for(int i = 0; i < vMaster.size(); i++) {
                OutSourceCostMaster outSourceCostMaster = (OutSourceCostMaster)vMaster.get(i);
                ctrlist.addHeader(""+outSourceCostMaster.getCostName(), "2%", "0", "0");
                ctrlist.addHeader("Catatan", "2%", "0", "0");
            }
            ctrlist.addHeader("Total Biaya Bulan Ini", "2%", "0", "0");
            ctrlist.addHeader("Biaya dari Awal tahun sdgn bulan ini", "2%", "0", "3");
            if(iCommand != Command.EDIT){
                ctrlist.setLinkRow(0);
            }
            ctrlist.setLinkSufix("");

            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            Vector val_pos = new Vector(1,1);
            Vector key_pos = new Vector(1,1);
            Vector vPos= PstPosition.listAll();
             for (int k = 0; k < vPos.size(); k++) {
                Position position = (Position) vPos.get(k);
                val_pos.add(""+position.getOID());
                key_pos.add(""+position.getPosition());
             }

            Vector val_cl = new Vector(1,1);
            Vector key_cl = new Vector(1,1);
            Vector vcl= PstContactList.listAll();
             for (int k = 0; k < vcl.size(); k++) {
                ContactList contactList = (ContactList) vcl.get(k);
                val_cl.add(""+contactList.getOID());
                key_cl.add(""+contactList.getCompName());
             }
            
            // vector of data will used in pdf report
            Vector vectDataToPdf = new Vector(1, 1);
            Vector rowx = new Vector(1, 1);
            
            int sum = 0;
            int startMonth = 1;
            int iVal = 0;
            Date dateNow = new Date();
            String conDate=Formater.formatDate(dateNow,"M");
            int month = Integer.parseInt(conDate);
            NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
            
            
            for (int i = 0; i < vOutSourceCostProv.size(); i++) {   
                OutsrcCostProv outsrcCostProv = (OutsrcCostProv) vOutSourceCostProv.get(i);
                rowx = new Vector();
                
                if(iCommand == Command.EDIT && oidOutsrcCostProv != 0 && outsrcCostProv.getOID() == oidOutsrcCostProv) {

                    rowx.add("<input type=\"hidden\" name=\""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_OUTSOURCE_COST_ID]+"\"  value=\""+oidOutSourceCost+"\" class=\"elemenForm\" size=\"30\">"+
                            ""+ControlCombo.draw(""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_POSITION_ID], null,""+outsrcCostProv.getPositionId(), val_pos, key_pos,""+outsrcCostProv.getPositionId(), "formElemen"));
                    rowx.add(""+ControlCombo.draw(""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_PROVIDER_ID], null,""+outsrcCostProv.getProviderId(), val_cl, key_cl,""+outsrcCostProv.getProviderId(), "formElemen"));
                    rowx.add("<input type=\"text\" name=\""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_NUMBER_OF_PERSON]+"\"  value=\""+outsrcCostProv.getNumberOfPerson()+"\" class=\"elemenForm\" size=\"30\">");
                    
                    Vector vOutSourceCostProvById = PstOutsrcCostProvDetail.list(0, 0, "OUTSRC_COST_PROVIDER_ID="+outsrcCostProv.getOID(), "");
                    for(int j = 0;j < vOutSourceCostProvById.size(); j++){
                        OutsrcCostProvDetail outsrcCostProvDetail = (OutsrcCostProvDetail)vOutSourceCostProvById.get(j);
                        rowx.add("<input type=\"text\" name=\""+frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_COST_VAL]+"_"+ outsrcCostProvDetail.getOutsrcCostId() +"\"  value=\""+outsrcCostProvDetail.getCostVal()+"\" class=\"elemenForm\" size=\"30\">");
                        rowx.add("<input type=\"text\" name=\""+frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_NOTE]+"_"+ outsrcCostProvDetail.getOutsrcCostId() +"\"  value=\""+outsrcCostProvDetail.getNote()+"\" class=\"elemenForm\" size=\"30\">");
                    }
                } else {
                    
                    rowx.add(""+outsrcCostProv.getPositionName());
                    rowx.add(""+outsrcCostProv.getProviderName());
                    rowx.add(""+outsrcCostProv.getNumberOfPerson());

                    Vector vOutSourceCostProvById = PstOutsrcCostProvDetail.list(0, 0, "OUTSRC_COST_PROVIDER_ID="+outsrcCostProv.getOID(), "");

                    for(int j = 0;j < vOutSourceCostProvById.size(); j++){
                        OutsrcCostProvDetail outsrcCostProvDetail = (OutsrcCostProvDetail)vOutSourceCostProvById.get(j);
                        String formatVal = numberFormatter.format(outsrcCostProvDetail.getCostVal());
                        rowx.add(""+formatVal);
                        rowx.add(""+outsrcCostProvDetail.getNote());
                        double val = outsrcCostProvDetail.getCostVal();
                        iVal =  (int)val;
                        sum = sum + iVal;
                    }
                    String formatAvg = numberFormatter.format(sum);
                    rowx.add(""+formatAvg);
                    int jmlh = month*sum;
                    
                    String formatJml = numberFormatter.format(jmlh);
                    rowx.add(""+formatJml);
                    jmlh = 0;
                    sum = 0;
                }
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(outsrcCostProv.getOID()));
            }
            rowx = new Vector();
            if(iCommand == Command.ADD) {
                
                rowx.add("<input type=\"hidden\" name=\""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_OUTSOURCE_COST_ID]+"\"  value=\""+oidOutSourceCost+"\" class=\"elemenForm\" size=\"30\">"+
                        ""+ControlCombo.draw(""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_POSITION_ID], null,"", val_pos, key_pos,"", "formElemen"));
                rowx.add(""+ControlCombo.draw(""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_PROVIDER_ID], null,"", val_cl, key_cl,"", "formElemen"));
                rowx.add("<input type=\"text\" name=\""+frmOutsrcCostProv.fieldNames[frmOutsrcCostProv.FRM_FIELD_NUMBER_OF_PERSON]+"\"  value=\"\" class=\"elemenForm\" size=\"30\">");
                for(int i = 0; i < vMaster.size(); i++) {
                    OutSourceCostMaster outSourceCostMaster = (OutSourceCostMaster)vMaster.get(i);
                    rowx.add("<input type=\"text\" name=\""+frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_COST_VAL]+"_"+ outSourceCostMaster.getOID() +"\"  value=\"\" class=\"elemenForm\" size=\"30\">");
                    rowx.add("<input type=\"text\" name=\""+frmOutsrcCostProvDetail.fieldNames[frmOutsrcCostProvDetail.FRM_FIELD_NOTE]+"_"+ outSourceCostMaster.getOID() +"\"  value=\"\" class=\"elemenForm\" size=\"30\">");
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
    int iCommandDetail = FRMQueryString.requestInt(request, "icommanddetail");
    long oidOutSourceCost = FRMQueryString.requestLong(request, "hidden_outsource_id");
    long oidOutsrcCostProv = FRMQueryString.requestLong(request, "hidden_outsourcecostprov_id");
    long oidOutsrcCostProvDet = FRMQueryString.requestLong(request, "hidden_outsourcecostprovdet_id");
    long oidOutsrcCostMaster = FRMQueryString.requestLong(request, "hidden_outsourcecostmaster_id");
    int iErrCode=0;
    int iErrCodeProv=0;
    int cmd = 0;
    Vector vMaster = new Vector(1,1);
    Vector vOutSourceCostProv = new Vector(1,1);
    
    Vector vDataCostProvDetail = new Vector(1,1);
    
    vMaster = PstOutSourceCostMaster.list(0, 0, "", "SHOW_INDEX");
    
    //main
    CtrlOutSourceCost ctrlOutSourceCost = new CtrlOutSourceCost(request);
    /*if(iCommand == Command.EDIT || iCommand == Command.ADD) {*/
        iErrCode = ctrlOutSourceCost.action(iCommandDetail,oidOutSourceCost);
        /*cmd = 1;
    } else {
        iErrCode = ctrlOutSourceCost.action(iCommand,oidOutSourceCost);
    }*/
    
    FrmOutSourceCost frmOutSourceCost = ctrlOutSourceCost.getForm();
    OutSourceCost outSourceCost = ctrlOutSourceCost.getOutSourceCost();
        
    //prov
    CtrlOutsrcCostProv ctrlOutsrcCostProv = new CtrlOutsrcCostProv(request);
    iErrCodeProv = ctrlOutsrcCostProv.action(iCommand, oidOutsrcCostProv, vMaster);
    FrmOutsrcCostProv frmOutsrcCostProv = ctrlOutsrcCostProv.getForm();
    OutsrcCostProv outsrcCostProv = ctrlOutsrcCostProv.getOutsrcCostProv();
    
    vOutSourceCostProv = PstOutsrcCostProv.listJoin(0, 0, "OUTSOURCE_COST_ID="+oidOutSourceCost, "");
    
        
    //prov detail
    CtrlOutsrcCostProvDetail ctrlOutsrcCostProvDetail = new CtrlOutsrcCostProvDetail(request);
    //iErrCode = ctrlOutsrcCostProv.action(iCommand,oidOutsrcCostProvDet);
    FrmOutsrcCostProvDetail frmOutsrcCostProvDetail = ctrlOutsrcCostProvDetail.getForm();
    OutsrcCostProvDetail outsrcCostProvDetail = ctrlOutsrcCostProvDetail.getOutsrcCostProvDetail();
    
    vDataCostProvDetail = PstOutsrcCostProvDetail.list(0, 0, "", "");
    
    //master
    CtrlOutSourceCostMaster ctrlOutSourceCostMaster = new CtrlOutSourceCostMaster(request);
    //iErrCode = ctrlOutsrcCostProv.action(iCommand,oidOutsrcCostMaster);
    FrmOutSourceCostMaster frmOutSourceCostMaster = ctrlOutSourceCostMaster.getForm();
    OutSourceCostMaster outSourceCostMaster = ctrlOutSourceCostMaster.getOutSourceCostMaster();
    /*
    if(outSourceCost.getOID()!=0){
_        oidOutSourceCost=outSourceCost.getOID();
    }*/
    
    String dateNowx = FRMQueryString.requestString(request, "DateSelected");
    DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
    Date dateNow = null;
    if(dateNowx == "" ){
        dateNowx = Formater.formatDate(new Date(), "yyyy-MM-dd");
    }
    dateNow = df3.parse(dateNowx);
    dateNow.setMonth(1);
    dateNow.setDate(1);
    String selected = Formater.formatDate(dateNow, "yyyy");
    
    
    Vector selectedDevisionRegular = new Vector(1,1);
    Vector selectedDevisionBranc = new Vector(1,1);
    Vector selectedPosition = new Vector(1,1);
    
    Date startDate = new Date();
    
%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>DIMATA HARISMA - Outsource</title>
        <script language="JavaScript">
            function cmdView() {
                document.frpresence.command.value = "<%=Command.LIST%>";
                document.frpresence.action = "cost_edit.jsp";
                document.frpresence.submit();
            }
            
            function cmdNextProses(oid){
                document.frpresence.command.value = "<%=Command.CONFIRM%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "cost_edit.jsp";
                document.frpresence.submit();
            }
            
            function cmdViewGenerate(oid){
                document.frpresence.command.value = "<%=Command.VIEW%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "cost_edit.jsp";
                document.frpresence.submit();
            }
            
            function cmdGenerate(oid){
                document.frpresence.command.value = "<%=Command.SUBMIT%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "cost_edit_generate.jsp";
                document.frpresence.submit();
            }
            function cmdSave(val) {
                
                if(val==0){
                    document.frpresence.icommanddetail.value=<%=Command.SAVE%>;
                } else {
                    document.frpresence.icommanddetail.value=<%=Command.EDIT%>;
                    document.frpresence.command.value = "<%=Command.SAVE%>";
                }
                document.frpresence.action = "cost_edit.jsp";
                document.frpresence.submit();
            }
            function cmdBack() {
                document.frpresence.command.value = "<%=Command.LIST%>";
                document.frpresence.action = "cost_src.jsp";
                document.frpresence.submit();
            }
            
            function cmdAddNew(oidSource) {
                document.frpresence.hidden_outsource_id.value=oidSource;
                document.frpresence.command.value = "<%=Command.ADD%>";
                document.frpresence.icommanddetail.value=<%=Command.EDIT%>;
                document.frpresence.action = "cost_edit.jsp";
                document.frpresence.submit();
            }
            
            function cmdEdit(oid){
                
		document.frpresence.hidden_outsourcecostprov_id.value=oid;
                
                //document.frpresence.icommanddetail.value=3;

                document.frpresence.command.value="<%=Command.EDIT%>";

                document.frpresence.action="cost_edit.jsp";

                document.frpresence.submit();

            }
            
            function cmdDelete(oid,val){
                
                if(val == 0){
                    //main
                    document.frpresence.hidden_outsource_id.value=oid;

                    document.frpresence.icommanddetail.value="<%=Command.DELETE%>";

                    document.frpresence.action="cost_src.jsp";

                    document.frpresence.submit();
                    
                } else {
                    document.frpresence.hidden_outsourcecostprov_id.value=oid;

                    document.frpresence.command.value="<%=Command.DELETE%>";

                    document.frpresence.action="cost_edit.jsp";

                    document.frpresence.submit();
                }
		

            }
            
            function cmdCancel(){
                
                document.frpresence.command.value="<%=Command.NONE%>";

                document.frpresence.action="cost_edit.jsp";

                document.frpresence.submit();

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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Laporan Alih Daya &gt; Form Master Alih Daya<!-- #EndEditable --> </strong></font> 
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
                                                                                <form name="frpresence" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="hidden_outsource_id" value="<%=oidOutSourceCost%>">
                                                                                    <input type="hidden" name="hidden_outsourcecostprov_id" value="<%=oidOutsrcCostProv%>">
                                                                                    <input type="hidden" name="icommanddetail" value="<%=iCommandDetail%>">
                                                                                    
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <tr>
                                                                                            <td><hr></td>
                                                                                        </tr>
                                                                                        <%if (iCommand!=Command.CONFIRM) {%>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <table width="80%" border="0" cellspacing="2" cellpadding="2">
                                                                                                        <tr> 
                                                                                                            <td width="5%" nowrap="nowrap"><div align="left">Periode</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                            <%
                                                                                                                    Vector val_per = new Vector(1,1);
                                                                                                                    Vector key_per = new Vector(1,1);
                                                                                                                    Vector vPer= PstPayPeriod.list(0,0,"","start_date, end_date");
                                                                                                                    
                                                                                                                     for (int k = 0; k < vPer.size(); k++) {
                                                                                                                        PayPeriod payPeriod = (PayPeriod) vPer.get(k);
                                                                                                                        val_per.add(""+payPeriod.getOID());
                                                                                                                        key_per.add(""+payPeriod.getPeriod());
                                                                                                                     }
                                                                                                                %>
                                                                                                                <%=ControlCombo.draw(""+frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_PERIOD_ID], null,""+outSourceCost.getPeriodId(), val_per, key_per,"", "formElemen")%>
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Status</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                <%
                                                                                                                     Vector val_status = new Vector(1,1);
                                                                                                                     Vector key_status = new Vector(1,1);
                                                                                                                     val_status.add(""+I_DocStatus.DOCUMENT_STATUS_DRAFT) ;
                                                                                                                     key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT] );
                                                                                                                     val_status.add(""+I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                                                                                                                     key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED] );
                                                                                                                     val_status.add(""+I_DocStatus.DOCUMENT_STATUS_FINAL);
                                                                                                                     key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                                                                                                                %>
                                                                                                                <%=ControlCombo.draw(""+frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_STATUS_DOC], null,"", val_status, key_status,"", "formElemen")%>
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Tanggal Pembuatan</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                <%=ControlDate.drawDateWithStyle(frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_CREATED_DATE], (outSourceCost.getCreatedDate()==null) ? startDate :outSourceCost.getCreatedDate(), 1, -5,"formElemen", "")%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Perusahaan</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <%	    
                                                                                                                    Vector val_company = new Vector(1,1);
                                                                                                                    Vector key_company = new Vector(1,1);
                                                                                                                    Vector vCompany= PstPayGeneral.listAll();
                                                                                                                     for (int k = 0; k < vCompany.size(); k++) {
                                                                                                                        PayGeneral payGeneral = (PayGeneral) vCompany.get(k);
                                                                                                                        val_company.add(""+payGeneral.getOID());
                                                                                                                        key_company.add(""+payGeneral.getCompanyName());
                                                                                                                     }
                                                                                                                %>
                                                                                                                <%=ControlCombo.draw(""+frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_COMPANY_ID], null,""+outSourceCost.getCompanyId(), val_company, key_company,"", "formElemen")%>
                                                                                                            </td>
                                                                                                            <td width="6%" nowrap="nowrap"><div align="right">Lokasi</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <%	    
                                                                                                                    Vector val_lok = new Vector(1,1);
                                                                                                                    Vector key_lok = new Vector(1,1);
                                                                                                                    Vector vlok= PstDivision.listAll();
                                                                                                                     for (int k = 0; k < vlok.size(); k++) {
                                                                                                                        Division division = (Division) vlok.get(k);
                                                                                                                        val_lok.add(""+division.getOID());
                                                                                                                        key_lok.add(""+division.getDivision());
                                                                                                                     }
                                                                                                                %>
                                                                                                                <%=ControlCombo.draw(""+frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_DIVISION_ID], null,""+outSourceCost.getDivisionId(), val_lok, key_lok,"", "formElemen")%>
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Dibuat oleh</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                <input class="masterTooltip" type="hidden" size="40" name="<%=frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_CREATED_BY_ID]%>"  value="<%=outSourceCost.getCreatedById()!=0? outSourceCost.getCreatedById() : emplx.getOID()%>" title="" class="elemenForm" >
                                                                                                                <input class="masterTooltip" type="text" size="40" name="createName"  value="<%=outSourceCost.getCreatedById()!=0? outSourceCost.getCreatedById() : emplx.getFullName().length()>0 ? emplx.getFullName() : "-"%>" title="" class="elemenForm" >
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left"></div></td>
                                                                                                            <td width="30%" nowrap="nowrap">
                                                                                                                
                                                                                                            </td>
                                                                                                            
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Note</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <input class="masterTooltip" type="text" size="40" name="<%=frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_NOTE]%>"  value="<%=outSourceCost.getNote()%>" title="" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                                                                                            </td>
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left"></div></td>
                                                                                                            <td width="30%" nowrap="nowrap">
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Tgl Persetujuan</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                <%=ControlDate.drawDateWithStyle(frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_APPROVED_DATE], (outSourceCost.getApprovedDate()==null) ? startDate :outSourceCost.getApprovedDate(), 1, -5,"formElemen", "")%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left"></div></td>
                                                                                                            <td width="30%" nowrap="nowrap">
                                                                                                            </td>
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left"></div></td>
                                                                                                            <td width="30%" nowrap="nowrap">
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Disetujui Oleh</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                 <input class="masterTooltip" type="hidden" size="40" name="<%=frmOutSourceCost.fieldNames[FrmOutSourceCost.FRM_FIELD_APPROVED_BY_ID]%>"  value="<%=outSourceCost.getCreatedById()%>" title="" class="elemenForm" >
                                                                                                                <input class="masterTooltip" type="text" size="50" name="full_name"  value="" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" >
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                    </table>
                                                                                                </td>
                                                                                            </tr>
                                                                                        <%}%>
                                                                                        <%if (privAdd) {%>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <table width="80%" border="0" cellspacing="1" cellpadding="1">
                                                                                                        <tr>
                                                                                                            <td width="5%"></td>
                                                                                                            <%if(iCommandDetail == Command.ADD){%>
                                                                                                            <td ><a href="javascript:cmdSave(0)"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdSave(0)" class="command">Save</a></b> </td>
                                                                                                            <td ><a href="javascript:cmdBack()"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdBack()" class="command">Back to List</a></b> </td>
                                                                                                            <%} else if(iCommandDetail == Command.EDIT && outSourceCost.getOID() != 0 || iCommandDetail == Command.SAVE) {%>
                                                                                                            <td ><a href="javascript:cmdSave(0)"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdSave(0)" class="command">Save</a></b> </td>
                                                                                                            <td><a href="javascript:cmdDelete('<%=outSourceCost.getOID()%>',0)"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Delete"></a><b><a href="javascript:cmdDelete('<%=oidOutsrcCostProv%>',0)" class="command">Delete Detail</a></b></td>
                                                                                                            <td ><a href="javascript:cmdBack()"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdBack()" class="command">Back to List</a></b> </td>
                                                                                                            
                                                                                                            <%} else {%>
                                                                                                            <%}%>
                                                                                                            
                                                                                                        </tr>
                                                                                                    </table>
                                                                                                </td>
                                                                                            </tr>
                                                                                        <%}%>
                                                                                    </table>
                                                                                <%if(outSourceCost.getOID() != 0) {%>
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <hr>
                                                                                                <h3>Biaya Detail Alih Daya</h3>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%=drawList(iCommand, iCommandDetail, vDataCostProvDetail, vOutSourceCostProv, vMaster, oidOutSourceCost, oidOutsrcCostProv, frmOutsrcCostProv, frmOutsrcCostProvDetail)%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="15%">
                                                                                                <%if(iCommand == Command.ADD || iCommand == Command.EDIT){%>
                                                                                                    <a href="javascript:cmdSave(1)"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdSave(1)" class="command">Save Detail</a></b>&nbsp;&nbsp;&nbsp;
                                                                                                    <a href="javascript:cmdDelete('<%=oidOutsrcCostProv%>',1)"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Delete"></a><b><a href="javascript:cmdDelete('<%=oidOutsrcCostProv%>',1)" class="command">Delete Detail</a></b>
                                                                                                    <a href="javascript:cmdCancel()"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdCancel()" class="command">Cancel</a></b>
                                                                                                    
                                                                                                    
                                                                                                <%} else {%>
                                                                                                <a href="javascript:cmdAddNew('<%=outSourceCost.getOID()%>')"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdAddNew('<%=outSourceCost.getOID()%>')" class="command">Add Detail</a></b>
                                                                                                <%}%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <hr>
                                                                                            </td>
                                                                                        </tr>
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

