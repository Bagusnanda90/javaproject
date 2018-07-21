<%-- 
    Document   : outsource_plan_edit
    Created on : Sep 14, 2015, 6:36:01 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.harisma.entity.outsource.OutSourceCostMaster"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceCostMaster"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourcePlan"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourcePlan"%>
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

<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_MASTER_PLAN);%>
<%@ include file = "../main/checkuser.jsp" %>

<%!    public String drawList(Vector vOutSourcePlan) {
        Vector result = new Vector(1, 1);
        ControlList ctrlist = new ControlList();
        if (vOutSourcePlan != null && vOutSourcePlan.size() > 0) {
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("No", "1%", "2", "0");
            ctrlist.addHeader("Tahun", "6%", "2", "0");
            ctrlist.addHeader("Judul", "6%", "0", "3");
            ctrlist.addHeader("Tgl Buat", "2%", "0", "0");
            ctrlist.addHeader("Oleh", "2%", "0", "0");
            ctrlist.addHeader("Status", "2%", "0", "0");
            ctrlist.addHeader("Disetujui Oleh", "2%", "0", "3");
            ctrlist.addHeader("Tanggal Pesetujuan", "2%", "0", "0");
            ctrlist.addHeader("Note", "2%", "0", "0");
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");

            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();

            // vector of data will used in pdf report
            Vector vectDataToPdf = new Vector(1, 1);
            int count = 0;
            for (int i = 0; i < vOutSourcePlan.size(); i++) {
                OutSourcePlan outSourcePlan = (OutSourcePlan) vOutSourcePlan.get(i);
                Vector rowx = new Vector(1, 1);
                count = count + 1;
                rowx.add("" + count);
                rowx.add("");
                rowx.add("");
                rowx.add("");
                rowx.add("");
                rowx.add("");
                rowx.add("");
                rowx.add("");
                rowx.add("");
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(outSourcePlan.getOID()));
            }
        }
        return ctrlist.draw(0);
    }

    public String drawCheckDivisionBranch(Vector checked){
		ControlCheckBox chkBx=new ControlCheckBox();
		chkBx.setWidth(4);
		chkBx.setCellSpace("0");
		chkBx.setCellStyle("");
		chkBx.setTableAlign("center");
		Vector checkName = new Vector(1,1);
		Vector checkValue = new Vector(1,1);
		Vector checkCaption = new Vector(1,1);
                String where = "ht."+PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"=1";
                Vector sttRsv = PstDivision.listJoin(0,0, where, "");
		for(int i = 0;i < sttRsv.size();i++){
			Division temp = (Division) sttRsv.get(i);
			checkValue.add(""+temp.getOID());
			checkCaption.add(temp.getDivision());
		}
		checkName = checkValue;
		return chkBx.draw(checkName,checkValue,checkCaption,checked);
    }
    
    
    public String drawCheckDivision(Vector checked){
		ControlCheckBox chkBx=new ControlCheckBox();
		chkBx.setWidth(4);
		chkBx.setCellSpace("0");
		chkBx.setCellStyle("");
		chkBx.setTableAlign("center");
		Vector checkName = new Vector(1,1);
		Vector checkValue = new Vector(1,1);
		Vector checkCaption = new Vector(1,1);
		String where = "ht."+PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"=0";
                Vector sttRsv = PstDivision.listJoin(0,0, where, "");
		for(int i = 0;i < sttRsv.size();i++){
			Division temp = (Division) sttRsv.get(i);
			checkValue.add(""+temp.getOID());
			checkCaption.add(temp.getDivision());
		}
		checkName = checkValue;
		return chkBx.draw(checkName,checkValue,checkCaption,checked);
    }
    
     public String drawCheckPosition(Vector checked){
		ControlCheckBox chkBx=new ControlCheckBox();
		chkBx.setWidth(4);
		chkBx.setCellSpace("0");
		chkBx.setCellStyle("");
		chkBx.setTableAlign("center");
		Vector checkName = new Vector(1,1);
		Vector checkValue = new Vector(1,1);
		Vector checkCaption = new Vector(1,1);
                String where = PstPosition.fieldNames[PstPosition.FLD_POSITION]+"!='nol' AND "+PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]+"=0";
		Vector sttRsv = PstPosition.list(0,0,where, "");
		for(int i = 0;i < sttRsv.size();i++){
			Position temp = (Position) sttRsv.get(i);
			checkValue.add(""+temp.getOID());
			checkCaption.add(temp.getPosition());
		}
		checkName = checkValue;
		return chkBx.draw(checkName,checkValue,checkCaption,checked);
    }
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidOutSourcePlan = FRMQueryString.requestLong(request, "hidden_outsource_id");
    int iErrCode=0;
    Vector val_locationid = new Vector(1,1);
    Vector key_locationid = new Vector(1,1);
    String strScript = "onChange=\"javascript:changeDate()\"";
    
    CtrlOutSourcePlan ctrlOutSourcePlan = new CtrlOutSourcePlan(request);
    iErrCode = ctrlOutSourcePlan.action(iCommand,oidOutSourcePlan);
    FrmOutSourcePlan frmOutSourcePlan = ctrlOutSourcePlan.getForm();
    OutSourcePlan outSourcePlan = ctrlOutSourcePlan.getOutSourcePlan();
    if(outSourcePlan.getOID()!=0){
        oidOutSourcePlan=outSourcePlan.getOID();
    }
    
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
    /*if(iCommand==Command.CONFIRM && oidOutSourcePlan!=0){
        String where = "ht."+PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"=0";
        Vector vectDevision = PstDivision.listJoin(0,0, where, "");
        if(vectDevision!=null && vectDevision.size()>0){
                for(int i=0; i<vectDevision.size(); i++)
                {
                        Division division = (Division)vectDevision.get(i);
                        long stt = FRMQueryString.requestLong(request,""+division.getOID());
                        if(stt!=0){
                                selectedDevisionRegular.add(String.valueOf(stt));
                        }
                }
        }
        
        
        where = "ht."+PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"=1";
        vectDevision = PstDivision.listJoin(0,0, where, "");
        if(vectDevision!=null && vectDevision.size()>0){
                for(int i=0; i<vectDevision.size(); i++)
                {
                        Division division = (Division)vectDevision.get(i);
                        long stt = FRMQueryString.requestLong(request,""+division.getOID());
                        if(stt!=0){
                                selectedDevisionBranc.add(String.valueOf(stt));
                        }
                }
        }
        
        Vector vposition = PstPosition.list(0, 0,"","");
        if(vposition!=null && vposition.size()>0){
                for(int i=0; i<vposition.size(); i++)
                {
                        Position position = (Position)vposition.get(i);
                        long stt = FRMQueryString.requestLong(request,""+position.getOID());
                        if(stt!=0){
                                selectedPosition.add(String.valueOf(stt));
                        }
                }
        }
    }*/
    
%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Outsource</title>
        <script language="JavaScript">
            function cmdView() {
                document.frpresence.command.value = "<%=Command.LIST%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }
            
            function cmdNextProses(oid){
                document.frpresence.command.value = "<%=Command.CONFIRM%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }
            
            function cmdViewGenerate(oid){
                document.frpresence.command.value = "<%=Command.VIEW%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "outsource_plan_edit_generate.jsp";
                document.frpresence.submit();
            }
            
            function cmdGenerate(oid){
                document.frpresence.command.value = "<%=Command.SUBMIT%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "outsource_plan_edit_generate.jsp";
                document.frpresence.submit();
            }
            
            
            function openCost(oid, oidCost){
                document.frpresence.command.value = "<%=Command.EDIT%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.hidden_cost_oid.value=oidCost;
                document.frpresence.action = "outsource_plan_edit_cost.jsp";
                document.frpresence.submit();
            }    
            
            function openTotalCost(oid){
                document.frpresence.command.value = "<%=Command.VIEW%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "outsource_plan_total_cost.jsp";
                document.frpresence.submit();
            }  
            
            function cmdSave() {
                document.frpresence.command.value = "<%=Command.SAVE%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }
            function cmdBack() {
                document.frpresence.command.value = "<%=Command.LIST%>";
                document.frpresence.action = "outsource_plan_list.jsp";
                document.frpresence.submit();
            }
            
            function cmdAddNew() {
                document.frpresence.command.value = "<%=Command.ADD%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }

            //update by devin 2014-01-29
            function cmdUpdateDiv() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
            function cmdUpdatePos() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
            function cmdUpdateSec() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }

            function cmdUpdateDep() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.target = "";
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
                                        <td height="20"> 
                                           <font color="#FF6600" face="Arial">  <h2><!-- #BeginEditable "contenttitle" -->Laporan Alih Daya &gt; <a href="javascript:cmdBack();">Form Master Alih Daya</a> >> <!-- #EndEditable -->
                                           <%=outSourcePlan.getTitle() %> >> <a href="javascript:cmdViewGenerate('<%=oidOutSourcePlan%>')">Lokasi & Posisi</a> >>
                                                                                                <%
                   Vector listCost = PstOutSourceCostMaster.list(0,0,
                  PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_TYPE]+"="+PstOutSourceCostMaster.COST_TYPE_PLAN_N_INPUT,
                  PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_SHOWINDEX]);
                                                                                                  if(listCost!=null && listCost.size()>0){
                                                                                                    for(int idc=0;idc < listCost.size() ; idc++){
                                                                                                           OutSourceCostMaster cost = (OutSourceCostMaster) listCost.get(idc);
                                                                                                           %> <a href="javascript:openCost('<%=outSourcePlan.getOID() %>','<%=cost.getOID()%>')"> <%=cost.getCostName() %> <a> >> 
                                                                                                    <%
                                                                                                    }
                                                                                                  }
                                                                                                %>
                                                                                                <a href="javascript:openTotalCost('<%=outSourcePlan.getOID() %>')">View Total Cost</a>
                                                                                                
                                                                                                </h2> </font>
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
                                                                                    <input type="hidden" name="hidden_outsource_id" value="<%=oidOutSourcePlan%>">
                                                                                    <input type="hidden" name="hidden_cost_oid" value="0">
                                                                                    
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <tr>
                                                                                            <td><hr></td>
                                                                                        </tr>
                                                                                        <%if (iCommand!=Command.CONFIRM) {%>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <table width="80%" border="0" cellspacing="2" cellpadding="2">
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Tahun</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <%
                                                                                                                    
                                                                                                                    int count=0;
                                                                                                                    int count1=0;
                                                                                                                    Date now = new Date();
                                                                                                                    now.setMonth(1);
                                                                                                                    now.setDate(1);

                                                                                                                    Date now1 = new Date();
                                                                                                                    now1.setMonth(1);
                                                                                                                    now1.setDate(1);

                                                                                                                    Date nowCounter = new Date();
                                                                                                                    nowCounter.setMonth(1);
                                                                                                                    nowCounter.setDate(1);

                                                                                                                    Date nowCounter1 = new Date();
                                                                                                                    nowCounter1.setMonth(1);
                                                                                                                    nowCounter1.setDate(1);

                                                                                                                    for (int k = 2; k > 0; k--) {
                                                                                                                       //count1=count1+1;
                                                                                                                       now1.setYear(nowCounter1.getYear() - k);
                                                                                                                       val_locationid.add(""+Formater.formatDate(now1, "yyyy"));
                                                                                                                       key_locationid.add(""+Formater.formatDate(now1, "yyyy"));
                                                                                                                    }

                                                                                                                    val_locationid.add(""+Formater.formatDate(now, "yyyy"));
                                                                                                                    key_locationid.add(""+Formater.formatDate(now, "yyyy"));

                                                                                                                    for (int k = 0; k < 5; k++) {
                                                                                                                       count=count+1;
                                                                                                                       now.setYear(nowCounter.getYear()+count);
                                                                                                                       val_locationid.add(""+Formater.formatDate(now, "yyyy"));
                                                                                                                       key_locationid.add(""+Formater.formatDate(now, "yyyy"));
                                                                                                                    }
                                                                                                                    
                                                                                                              %>
                                                                                                                <%=ControlCombo.draw(""+frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_PLANYEAR], null,""+outSourcePlan.getPlanYear(), val_locationid, key_locationid,strScript, "formElemen")%>
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
                                                                                                                <%=ControlCombo.draw(""+frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_STATUS], null,""+outSourcePlan.getStatus(), val_status, key_status,"", "formElemen")%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Perusahaan</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <%
                                                                                                                    Date startDate = new Date();	    
                                                                                                                    Vector val_company = new Vector(1,1);
                                                                                                                    Vector key_company = new Vector(1,1);
                                                                                                                    Vector vCompany= PstPayGeneral.listAll();
                                                                                                                     for (int k = 0; k < vCompany.size(); k++) {
                                                                                                                        PayGeneral payGeneral = (PayGeneral) vCompany.get(k);
                                                                                                                        val_company.add(""+payGeneral.getOID());
                                                                                                                        key_company.add(""+payGeneral.getCompanyName());
                                                                                                                     }
                                                                                                                %>
                                                                                                                <%=ControlCombo.draw(""+frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_COMPANYID], null,""+outSourcePlan.getCompanyId(), val_company, key_company,"", "formElemen")%>
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Tanggal Buat</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                <%=ControlDate.drawDateWithStyle(frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_CREATEDDATE], (outSourcePlan.getCreatedDate()==null) ? startDate :outSourcePlan.getCreatedDate(), 1, -5,"formElemen", "")%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Judul</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <input class="masterTooltip" type="text" size="40" name="<%=frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_TITLE]%>"  value="<%=outSourcePlan.getTitle()%>" title="" class="elemenForm" >
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Dibuat Oleh</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                <input class="masterTooltip" type="hidden" size="40" name="<%=frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_CREATEDBYID]%>"  value="<%=outSourcePlan.getCreatedById()!=0? outSourcePlan.getCreatedById() : emplx.getOID()%>" title="" class="elemenForm" >
                                                                                                                <input class="masterTooltip" type="text" size="40" name="createName"  value="<%=outSourcePlan.getCreatedById()!=0? outSourcePlan.getCreatedById() : emplx.getFullName().length()>0 ? emplx.getFullName() : "-"%>" title="" class="elemenForm" >
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Berlaku</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <%
                                                                                                                    Date validFrom = new Date();
                                                                                                                    validFrom.setMonth(0);
                                                                                                                    validFrom.setDate(1);
                                                                                                                    Date validTo = new Date();
                                                                                                                    validTo.setMonth(11);
                                                                                                                    validTo.setDate(31);
                                                                                                                %>
                                                                                                                <%=ControlDate.drawDateWithStyle(frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_VALID_FROM], (outSourcePlan.getValidFrom()==null) ? validFrom :outSourcePlan.getValidFrom(), 1, -5,"formElemen", "")%>
                                                                                                                s/d <%=ControlDate.drawDateWithStyle(frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_VALID_TO], (outSourcePlan.getValidTo()==null) ? validTo :outSourcePlan.getValidTo(), 1, -5,"formElemen", "")%>
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Tgl Persetujuan</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                <%=ControlDate.drawDateWithStyle(frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_APPROVEDDATE], (outSourcePlan.getApprovedDate()==null) ? startDate :outSourcePlan.getApprovedDate(), 1, -5,"formElemen", "")%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Note</div></td>
                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                <input class="masterTooltip" type="text" size="40" name="<%=frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_NOTE]%>"  value="" title="" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                                                                                            </td>
                                                                                                            <td width="5%" nowrap="nowrap"><div align="right">Disetujui Oleh</div> </td>
                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                 <input class="masterTooltip" type="hidden" size="40" name="<%=frmOutSourcePlan.fieldNames[FrmOutSourcePlan.FRM_FIELD_APPROVEBYID]%>"  value="<%=outSourcePlan.getCreatedById()%>" title="" class="elemenForm" >
                                                                                                                <input class="masterTooltip" type="text" size="50" name="full_name"  value="" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" >
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                    </table>
                                                                                                </td>
                                                                                            </tr>
                                                                                        <%}%>
                                                                                        <%if (oidOutSourcePlan!=0 && iCommand==Command.CONFIRM) {%>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left"><b>Devision Regular</b></div></td>
                                                                                                            <td width="30%" nowrap="nowrap"></td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">&nbsp;</div></td>
                                                                                                            <td width="30%" nowrap="nowrap"><%=drawCheckDivision(selectedDevisionRegular)%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left"><b>Devision Branch</b></div></td>
                                                                                                            <td width="30%" nowrap="nowrap"></td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">&nbsp;</div></td>
                                                                                                            <td width="30%" nowrap="nowrap"><%=drawCheckDivisionBranch(selectedDevisionRegular)%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left"><b>Position</b></div></td>
                                                                                                            <td width="30%" nowrap="nowrap"></td>
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">&nbsp;</div></td>
                                                                                                            <td width="30%" nowrap="nowrap"><%=drawCheckPosition(selectedPosition)%>
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
                                                                                                            <td width="10%"></td>
                                                                                                            <%if (oidOutSourcePlan!=0 && iCommand==Command.CONFIRM) {%>
                                                                                                                <td width="15%"><a href="javascript:cmdGenerate('<%=oidOutSourcePlan%>')"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdGenerate('<%=oidOutSourcePlan%>')" class="command">Generate</a></b> </td>
                                                                                                            <%}else{%>
                                                                                                                <td width="15%"><a href="javascript:cmdSave()"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdSave()" class="command">Save</a></b> </td>
                                                                                                            <%}%>
                                                                                                            <%if(val_locationid.size()>0){%>
                                                                                                                <td width="25%"><b>Generate Detail Dari</b> <%=ControlCombo.draw("periode_generate", null,""+selected, val_locationid, key_locationid,strScript, "formElemen")%></td>	
                                                                                                                <td width="20%"><a href="javascript:cmdNextProses('<%=oidOutSourcePlan%>')"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdNextProses('<%=oidOutSourcePlan%>')" class="command">GO Create</a></b> </td>
                                                                                                                 <td width="20%"><a href="javascript:cmdViewGenerate('<%=oidOutSourcePlan%>')"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Lokasi dan Posisi"></a><b><a href="javascript:cmdViewGenerate('<%=oidOutSourcePlan%>')" class="command">Rencana Lokasi dan Posisi</a></b> </td>
                                                                                                            <%}%>	
                                                                                                            <td width="25%"><a href="javascript:cmdBack()"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdBack()" class="command">Back to List</a></b> </td>
                                                                                                        </tr>
                                                                                                    </table>
                                                                                                </td>
                                                                                            </tr>
                                                                                        <%}%>
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

