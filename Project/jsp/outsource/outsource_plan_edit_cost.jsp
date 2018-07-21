<%-- 
    Document   : outsource_plan_edit_generate
    Created on : Sep 15, 2015, 6:16:08 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.harisma.session.outsource.OutSourceValues"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceCostProvDetailAvrg"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourcePlanCost"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourcePlanCost"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourcePlanCost"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceCostMaster"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceCostMaster"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourcePlanLocation"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourcePlanLocation"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourcePlanLocation"%>
<%-- 
    Document   : outsource_plan_edit
    Created on : Sep 14, 2015, 6:36:01 PM
    Author     : dimata005
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourcePlan"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourcePlan"%>
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
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

<%!    
     public String drawList(Vector vOutSourcePlan) {
        Vector result = new Vector(1, 1);
        ControlList ctrlist = new ControlList();
        if (vOutSourcePlan != null && vOutSourcePlan.size() > 0) {
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("No", "1%", "2", "0");
            ctrlist.addHeader("Lokasi", "1%", "2", "0");
            ctrlist.addHeader("Existing", "6%", "2", "0");
            ctrlist.addHeader("TW1", "6%", "2", "0");
            ctrlist.addHeader("TW2", "6%", "2", "0");
            ctrlist.addHeader("TW3", "6%", "2", "0");
            ctrlist.addHeader("TW4", "6%", "2", "0"); 
            
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
                OutSourcePlanLocation outSourcePlanLocation = (OutSourcePlanLocation) vOutSourcePlan.get(i);
                Vector rowx = new Vector(1, 1);
                count=count+1;
                rowx.add("" +count);
                rowx.add(""+outSourcePlanLocation.getNameDivision());
                rowx.add(""+outSourcePlanLocation.getPrevExisting());
                rowx.add(""+outSourcePlanLocation.getNumberTW1());
                rowx.add(""+outSourcePlanLocation.getNumberTW2());
                rowx.add(""+outSourcePlanLocation.getNumberTW3());
                rowx.add(""+outSourcePlanLocation.getNumberTW4());
                lstData.add(rowx);
            }
        }
        return ctrlist.draw();
    }
    
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidOutSourcePlan = FRMQueryString.requestLong(request, "hidden_outsource_id");
    long oidOutMasterCost = FRMQueryString.requestLong(request, "hidden_cost_oid");
    int iErrCode=0;
    int iErrCodeLoc=0;
    Vector val_locationid = new Vector(1,1);
    Vector key_locationid = new Vector(1,1);
    String strScript = "onChange=\"javascript:changeDate()\"";
    
    CtrlOutSourcePlan ctrlOutSourcePlan = new CtrlOutSourcePlan(request);
    CtrlOutSourcePlanLocation ctrlOutSourcePlanLocation = new CtrlOutSourcePlanLocation(request);
    iErrCode = ctrlOutSourcePlan.action(Command.EDIT, oidOutSourcePlan);
    FrmOutSourcePlan frmOutSourcePlan = ctrlOutSourcePlan.getForm();
    FrmOutSourcePlanLocation frmOutSourcePlanLocation =ctrlOutSourcePlanLocation.getForm();
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
 
    Vector selectedDevision = new Vector(1,1);
    Vector selectedPosition = new Vector(1,1);
    Vector vProses = new Vector(1,1);
    
    if(iCommand==Command.SUBMIT && oidOutSourcePlan!=0){
        /*String where = " (ht."+PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"=0 OR ht."+PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"=1) ";
        Vector vectDevision = PstDivision.listJoin(0,0, where, "");
        if(vectDevision!=null && vectDevision.size()>0){
                for(int i=0; i<vectDevision.size(); i++)
                {
                        Division division = (Division)vectDevision.get(i);
                        long stt = FRMQueryString.requestLong(request,""+division.getOID());
                        if(stt!=0){
                                selectedDevision.add(String.valueOf(stt));
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
        vProses.add(selectedPosition);
        vProses.add(selectedDevision);*/
        CtrlOutSourcePlanCost ctrlOutSourcePlanCost = new CtrlOutSourcePlanCost(request);
        iErrCodeLoc= ctrlOutSourcePlanCost.actionSubmit(oidOutSourcePlan, oidOutMasterCost);
    }
    
    iErrCodeLoc = ctrlOutSourcePlanLocation.actionGenerate(iCommand, oidOutSourcePlan, vProses, outSourcePlan, request);
    
    
    String whereLoc= "ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan;
    Vector listSourcePlanLocationPosition =  PstOutSourcePlanLocation.listJoinPositionOnly(0, 0, whereLoc, "");
    
    Vector listSourcePlanLocationDivision =  PstOutSourcePlanLocation.listJoinDivisionOnly(0, 0, whereLoc, "");
    
    
%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Outsource</title>
        <script language="JavaScript">
            
                   
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
     
     function cmdEditMain(oid){
         document.frpresence.command.value = "<%=Command.EDIT%>";
         document.frpresence.hidden_outsource_id.value = oid;
         document.frpresence.action = "outsource_plan_edit.jsp";
         document.frpresence.submit();
         
     }      
      
    function cmdView() {
                document.frpresence.command.value = "<%=Command.LIST%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }
             
            function cmdViewGenerate(oid){
                document.frpresence.command.value = "<%=Command.VIEW%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "outsource_plan_edit_generate.jsp";
                document.frpresence.submit();
            }           
           
            
            function cmdNextProses(oid){
                document.frpresence.command.value = "<%=Command.CONFIRM%>";
                document.frpresence.hidden_outsource_id.value=oid;
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }
            function cmdGenerate(){
                
            }
            function cmdSave() {
                document.frpresence.command.value = "<%=Command.SUBMIT%>";
                document.frpresence.action = "outsource_plan_edit_cost.jsp";
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
                                                                                    <input type="hidden" name="hidden_outsource_id" value="<%=oidOutSourcePlan%>">
                                                                                    <input type="hidden" name="hidden_cost_oid" value="<%=oidOutMasterCost%>">
                                                                                   
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <tr>
                                                                                            <td><hr></td>
                                                                                        </tr>
<tr>
    <td><h2> <a href="javascript:cmdEditMain('<%=oidOutSourcePlan%>')"> Rencana Jumlah Alih Daya <%=outSourcePlan.getPlanYear()%> : <%=outSourcePlan.getTitle() %></a> >>
         <a href="javascript:cmdViewGenerate('<%=oidOutSourcePlan%>')">   Lokasi & Posisi</a> >>
        <%String strCost = "";
          Vector listCost = PstOutSourceCostMaster.list(0,0,
                  PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_TYPE]+"="+PstOutSourceCostMaster.COST_TYPE_PLAN_N_INPUT,
                  PstOutSourceCostMaster.fieldNames[PstOutSourceCostMaster.FLD_SHOWINDEX]);
          if(listCost!=null && listCost.size()>0){
            for(int idc=0;idc < listCost.size() ; idc++){
                   OutSourceCostMaster cost = (OutSourceCostMaster) listCost.get(idc);
                   %> <a href="javascript:openCost('<%=outSourcePlan.getOID() %>','<%=cost.getOID()%>')"> <%=cost.getCostName() %> <a> >> 
            <%
                  if(cost.getOID()==oidOutMasterCost)
                      strCost =cost.getCostName(); 
            }
          }
        %>
<a href="javascript:openTotalCost('<%=outSourcePlan.getOID() %>')">View Total Cost</a>

        </h2>

    </td>
        </tr> 
        <tr>
            <td>
                <table>
                     <tr>
                         <td colspan="2"  class="listheader" >
                          <h3> Biaya Alih Daya <%=outSourcePlan.getPlanYear()%> : <%=outSourcePlan.getTitle() %> : <%=strCost %> </h3> 
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" >
                           <%
                             
                           %>
                          Biaya Alih Daya <%=(outSourcePlan.getPlanYear()-1)%>
                        </td>
                        <td width="50%" >
                          Perkiraan  Biaya Alih Daya <%=outSourcePlan.getPlanYear()%>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" >
                            <table width="100%" border="1" style="background-color:#90EE90">
                                <% try{ 
                                    if(listSourcePlanLocationPosition.size()>0){  // biaya alih daya : sebelumnya
                                       Date startDate = new Date(outSourcePlan.getPlanYear() - 1900-1, 0, 1);
                                       Date endDate = new Date(outSourcePlan.getPlanYear() - 1900-1, 11, 31);
                                       
                                       Hashtable<String , OutSourceCostProvDetailAvrg> hCostAvrg = PstOutsrcCostProvDetail.listCostDetailByDivPos(startDate, endDate, 0, 0, oidOutMasterCost, I_DocStatus.DOCUMENT_STATUS_FINAL);
                                        
                                    %>
                                          <tr>
                                              <td width="78"><center>Posisi</center></td>
                                             <%
                                              for (int i = 0; i < listSourcePlanLocationPosition.size(); i++) {
                                                    Vector vPosition = (Vector) listSourcePlanLocationPosition.get(i);
                                                    %>
                                                    <td  nowrap="true"><center><%=vPosition.get(1)%></center></td>
                                             <%}%>  
                                          </tr>
                                          <tr>
                                              <td width="78"><center> <input type="text" readonly value="Average" size="8"> </center></td>
                                             <%
                                              for (int i = 0; i < listSourcePlanLocationPosition.size(); i++) {
                                                    Vector vPosition = (Vector) listSourcePlanLocationPosition.get(i);
                                                    %>
                                                    <td >
                                                        &nbsp;
                                                    </td>
                                             <%}%>  
                                          </tr>
                                          <% 
                                            //Vector vDivisionHeader = (Vector) listSourcePlanLocationDivision.get(1);
                                            %>
                                           
                                          <% 
                                             for (int i = 0; i < listSourcePlanLocationDivision.size(); i++) {
                                                    Vector vDivision = (Vector) listSourcePlanLocationDivision.get(i);
                                                    %>
                                                    <tr>
                                                        <td  nowrap><%=vDivision.get(1)%></td>
                                                    <%
                                                           
                                                     for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {
                                                          Vector vPosition = (Vector) listSourcePlanLocationPosition.get(k);
                                                          
                                                          String whereLocSelected= " ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]+"="+vDivision.get(0)+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                         
                                                          Vector<OutSourcePlanLocation> listSourcePlanLocation =  PstOutSourcePlanLocation.listJoin(0, 1, whereLocSelected, "");  
                                                          OutSourcePlanLocation outPlanLoc = listSourcePlanLocation !=null && listSourcePlanLocation.size()>0 ? listSourcePlanLocation.get(0): null ; 
                                                                                                                   
                                                          String whereCostSelected= " co."+PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID]+"="+oidOutMasterCost+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]+"="+vDivision.get(0)+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                          Vector<OutSourcePlanCost> listSourcePlanCost =  PstOutSourcePlanCost.listJoin(0, 0, whereCostSelected, ""); 
                                                       if( true ){ // || (listSourcePlanCost!=null && listSourcePlanCost.size()>0){ 
                                                       for (int j = 0; j < 1 /*listSourcePlanCost.size()*/; j++) { 
                                                         try{       
                                                             // OutSourcePlanCost entOutSourcePlanCost = (OutSourcePlanCost) listSourcePlanCost.get(j);
                                                            %> 
                                                            <td width="3" align="center">
                                                               <% try{
                                                                   OutSourceCostProvDetailAvrg costAvrg =  hCostAvrg !=null ? hCostAvrg.get(""+vDivision.get(0)+"_"+vPosition.get(0)) :null;
                                                                  %>
                                                                <input size="8" type="text" readonly value="<%=Formater.formatNumber(costAvrg!=null ? costAvrg.getAverageCost():0,"###,###.##") %>" style="text-align:right">
                                                              <%}catch(Exception exc){} %>
                                                            </td>

                                                             <% }catch(Exception exc){
                                                               out.println(exc);
                                                                }
                                                         }
                                                        }else{%>
                                                          <td width="3" align="center"> <%    %>
                                                              <input type="text" readonly value="0" size="6">
                                                          </td>
                                                            <%}
                                                      }%>
                                                  </tr>
                                                  
                                            <%}%>  
                                <%} } catch(Exception exc){out.println("<tr><td>"+ exc +"</td></tr>"); }%>
                    </table>
                        </td>
                        <td width="50%" >
                           <table width="100%" border="1" style="background-color:#90EE90">
                                <%if(listSourcePlanLocationPosition.size()>0){ // biaya laih daya YANG DI EDIT
                                    %>
                                          <tr>
                                              <td width="78"><center>Posisi</center></td>
                                             <%
                                              for (int i = 0; i < listSourcePlanLocationPosition.size(); i++) {
                                                    Vector vPosition = (Vector) listSourcePlanLocationPosition.get(i);
                                                    %>
                                                    <td nowrap="true"><center><%=vPosition.get(1)%></center></td>
                                             <%}%>  
                                          </tr>
                                          <tr>
                                              <td width="78"><center>Kenaikan</center></td>
<%                                               for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {
                                                          Vector vPosition = (Vector) listSourcePlanLocationPosition.get(k);
                                                          
                                                          String whereLocSelected= " ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                         
                                                          Vector<OutSourcePlanLocation> listSourcePlanLocation =  PstOutSourcePlanLocation.listJoin(0, 1, whereLocSelected, "");  
                                                          OutSourcePlanLocation outPlanLoc = listSourcePlanLocation !=null && listSourcePlanLocation.size()>0 ? listSourcePlanLocation.get(0): null ; 
                                                                                                                   
                                                          String whereCostSelected= " co."+PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID]+"="+oidOutMasterCost+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                          Vector<OutSourcePlanCost> listSourcePlanCost =  PstOutSourcePlanCost.listJoin(0, 0, whereCostSelected, ""); 
                                                       if(listSourcePlanCost!=null && listSourcePlanCost.size()>0){ 
                                                       for (int j = 0; j < 1 /*listSourcePlanCost.size()*/; j++) {
                                                         try{       
                                                              OutSourcePlanCost entOutSourcePlanCost = (OutSourcePlanCost) listSourcePlanCost.get(j);
                                                            %> 
                                                            <td width="3" align="center">
                                                               <% try{ %>
                                                                  <input type="text"  size="4" name="<%=PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_INCRSTOPREVYEAR]%>-<%=entOutSourcePlanCost.getOutSourcePlanLocId()%>-<%=oidOutMasterCost%>"  value="<%=entOutSourcePlanCost.getIncrsToPrevYear() %>" class="form-control" style="text-align:center">% 
                                                              <%}catch(Exception exc){} %>
                                                            </td>

                                                             <% }catch(Exception exc){
                                                               out.println(exc);
                                                                }
                                                         }
                                                        }else{%>
                                                          <td width="3" align="center">
                                                            <input type="text"  size="4" name="<%=PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_INCRSTOPREVYEAR]%>-<%=outPlanLoc.getOID()%>-<%=oidOutMasterCost%>"  value="0" class="form-control" style="text-align:center"> 
                                                          </td>
                                                            <%}
                                                      }%> 
                                          </tr>
                                          <% 
                                            Vector vDivisionHeader = (Vector) listSourcePlanLocationDivision.get(1);
                                            %>
                                           
                                          <% Hashtable listPersentase = frmOutSourcePlanLocation.getGetHastableValueInput();
                                             if(listPersentase==null){
                                                 listPersentase = new Hashtable();
                                             }
                                             for (int i = 0; i < listSourcePlanLocationDivision.size(); i++) {
                                                    Vector vDivision = (Vector) listSourcePlanLocationDivision.get(i);
                                                    %>
                                                    <tr>
                                                        <td  nowrap><%=vDivision.get(1)%></td>
                                                    <%
                                                           
                                                     for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {
                                                          Vector vPosition = (Vector) listSourcePlanLocationPosition.get(k);
                                                          
                                                          String whereLocSelected= " ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]+"="+vDivision.get(0)+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                         
                                                          Vector<OutSourcePlanLocation> listSourcePlanLocation =  PstOutSourcePlanLocation.listJoin(0, 1, whereLocSelected, "");  
                                                          OutSourcePlanLocation outPlanLoc = listSourcePlanLocation !=null && listSourcePlanLocation.size()>0 ? listSourcePlanLocation.get(0): null ; 
                                                                                                                   
                                                          String whereCostSelected= " co."+PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID]+"="+oidOutMasterCost+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]+"="+vDivision.get(0)+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                          Vector<OutSourcePlanCost> listSourcePlanCost =  PstOutSourcePlanCost.listJoin(0, 0, whereCostSelected, ""); 
                                                       if(listSourcePlanCost!=null && listSourcePlanCost.size()>0){ 
                                                       for (int j = 0; j < 1 /*listSourcePlanCost.size()*/ ; j++) {
                                                         try{       
                                                              OutSourcePlanCost entOutSourcePlanCost = (OutSourcePlanCost) listSourcePlanCost.get(j);
                                                            %> 
                                                            <td width="3" align="center">
                                                               <% try{ %>
                                                                <input type="text"  size="8" name="<%=PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST]%>-<%=entOutSourcePlanCost.getOutSourcePlanLocId()%>-<%=oidOutMasterCost%>"  value="<%=Formater.formatNumber(entOutSourcePlanCost.getPlanAvrgCost(),"###,###.##")  %>" class="form-control" style="text-align:right"> 
                                                              <%}catch(Exception exc){} %>
                                                            </td>

                                                             <% }catch(Exception exc){
                                                               out.println(exc);
                                                                }
                                                         }
                                                        }else{%>
                                                          <td width="3" align="center"> 
                                                            <input type="text"  size="6" name="<%=PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST]%>-<%=outPlanLoc.getOID()%>-<%=oidOutMasterCost%>"  value="0" class="form-control" style="text-align:center"> 
                                                          </td>
                                                            <%}
                                                      }%>
                                                  </tr>
                                                  
                                            <%}%>  
                                <%}%>
                    </table>
                        </td>
                    </tr>
                </table>
        </tr>
        <tr>
            <td>
             Rencana <%=strCost%> <%=outSourcePlan.getPlanYear()%> 
            </td>
        </tr>
        <tr> 
            <td>
                <table width="100%" border="1" style="background-color:#90EE90">
                            <%if(listSourcePlanLocationPosition.size()>0){
                                String whereClause =" ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+" AND "+ PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID]+"="+oidOutMasterCost;
                                Hashtable<String, Double>  hPlanCost = PstOutSourcePlanCost.hPlanCostKeyLocPos(0, 0, whereClause, "");
                                 Vector<OutSourceValues> vSubTotalCost = new Vector<OutSourceValues>();
                                 OutSourceValues grandTotalCost = new OutSourceValues();
                                %>
                                      <tr>
                                          <td ><center>Posisi</center></td>
                                         <%
                                          for (int i = 0; i < listSourcePlanLocationPosition.size(); i++) {
                                                Vector vPosition = (Vector) listSourcePlanLocationPosition.get(i);
                                                %>
                                                <td colspan="4"><center><%=vPosition.get(1)%></center></td>
                                         <%}%>  
                                            <td colspan="4"><center>Biaya Total per Tahun</center></td>
                                            <td ><center>Total</center></td>
                                      </tr>

                                      <% 
                                        Vector vDivisionHeader = (Vector) listSourcePlanLocationDivision.get(1);
                                        %>
                                        <tr>
                                            <td nowrop ><center>Lokasi</center></td>
                                        <%
                                         for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {
                                              Vector vPosition = (Vector) listSourcePlanLocationPosition.get(k);
                                              String whereLocSelected= " ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                       " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]+"="+vDivisionHeader.get(0)+
                                                                       " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                              Vector listSourcePlanLocation =  PstOutSourcePlanLocation.listJoin(0, 0, whereLocSelected, "");
                                              for (int j = 0; j < listSourcePlanLocation.size(); j++) {
                                         %> 
                                                <td width="3"><center>12 Bulan</center></td>
                                                <td width="3"><center>9 Bulan</center></td>
                                                <td width="3"><center>6 Bulan</center></td>
                                                <td width="3"><center>3 Bulan</center></td>

                                        <% }}%>
                                                <td width="3"><center>12 Bulan</center></td>
                                                <td width="3"><center>9 Bulan</center></td>
                                                <td width="3"><center>6 Bulan</center></td>
                                                <td width="3"><center>3 Bulan</center></td>                                        
                                                <td width="3"><center><%=outSourcePlan.getPlanYear()%></center></td>                                        
                                      </tr> 
                                      <% Hashtable listPersentase = frmOutSourcePlanLocation.getGetHastableValueInput();
                                         if(listPersentase==null){
                                             listPersentase = new Hashtable();
                                         }

                                         for (int i = 0; i < listSourcePlanLocationDivision.size(); i++) {
                                                Vector vDivision = (Vector) listSourcePlanLocationDivision.get(i);
                                                %>
                                                <tr>
                                                    <td width="78" ><%=vDivision.get(1)%></td>
                                                <%
                                                OutSourceValues locationTotalCost = new OutSourceValues();
                                                 for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {
                                                      Vector vPosition = (Vector) listSourcePlanLocationPosition.get(k);
                                                      OutSourceValues subTotalCost=null;
                                                      if(i==0){ // just create subtotal per position by first location loop
                                                          subTotalCost =  new OutSourceValues(); 
                                                          vSubTotalCost.add(subTotalCost);
                                                      }else{
                                                          subTotalCost=vSubTotalCost.get(k);
                                                      }
                                                      
                                                      String whereCostSelected= " co."+PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID]+"="+oidOutMasterCost+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]+"="+vDivision.get(0)+
                                                                                   " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                       Vector<OutSourcePlanCost> listSourcePlanCost =  PstOutSourcePlanCost.listJoin(0, 0, whereCostSelected, ""); 
                                                       if(listSourcePlanCost!=null && listSourcePlanCost.size()>0){
                                                           
                                                       }
                                                      
                                                      String whereLocSelected= " ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+
                                                                               " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]+"="+vDivision.get(0)+
                                                                               " AND ho."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]+"="+vPosition.get(0);
                                                      Vector listSourcePlanLocation =  PstOutSourcePlanLocation.listJoin(0, 0, whereLocSelected, "");
                                                      for (int j = 0; j < listSourcePlanLocation.size(); j++) {
                                                    try{       OutSourcePlanLocation entOutSourcePlanLocation = (OutSourcePlanLocation) listSourcePlanLocation.get(j);
                                                           listPersentase.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_PREV_EXISTING]+"_"+entOutSourcePlanLocation.getOID(), entOutSourcePlanLocation.getNumberTW1() - entOutSourcePlanLocation.getPrevExisting());
                                                           listPersentase.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW1]+"_"+entOutSourcePlanLocation.getOID(), entOutSourcePlanLocation.getNumberTW2()- entOutSourcePlanLocation.getNumberTW1());
                                                           listPersentase.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW2]+"_"+entOutSourcePlanLocation.getOID(), entOutSourcePlanLocation.getNumberTW3()- entOutSourcePlanLocation.getNumberTW2());
                                                           listPersentase.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW3]+"_"+entOutSourcePlanLocation.getOID(), entOutSourcePlanLocation.getNumberTW4()- entOutSourcePlanLocation.getNumberTW3());
                                                           Double aCost = hPlanCost.get(""+vDivision.get(0)+"_"+vPosition.get(0));
                                                           aCost = aCost==null ? new Double(0.0): aCost;
                                                           double cost12bulan=entOutSourcePlanLocation.getNumberTW1() * aCost.doubleValue();
                                                           double cost9bulan=(entOutSourcePlanLocation.getNumberTW2()-entOutSourcePlanLocation.getNumberTW1()) * aCost.doubleValue();
                                                           double cost6bulan=(entOutSourcePlanLocation.getNumberTW3()-entOutSourcePlanLocation.getNumberTW2()) * aCost.doubleValue();
                                                           double cost3bulan=(entOutSourcePlanLocation.getNumberTW4()-entOutSourcePlanLocation.getNumberTW3()) * aCost.doubleValue();
                                                           subTotalCost.addToTriwulan(cost12bulan,cost9bulan,cost6bulan,cost3bulan);
                                                           locationTotalCost.addToTriwulan(cost12bulan,cost9bulan,cost6bulan,cost3bulan);
                                                           grandTotalCost.addToTriwulan(cost12bulan,cost9bulan,cost6bulan,cost3bulan);   
                                                  %> 
                                                        <td width="3" align="right">
                                                           <% try{ %> <%=Formater.formatNumber(cost12bulan,"###,###.##")%>                                                            
                                                         <%}catch(Exception exc){} %>
                                                        </td>
                                                        <td width="3" align="right">
                                                           <% try{ %><%=Formater.formatNumber(cost9bulan,"###,###.##")%>                                                         
                                                         <%}catch(Exception exc){} %>
                                                        </td>
                                                        <td width="3" align="right">
                                                            <% try{ %><%=Formater.formatNumber(cost6bulan,"###,###.##")%>
                                                            
                                                          <%}catch(Exception exc){} %>
                                                        </td>
                                                        <td width="3" align="right">
                                                            <% try{ %><%=Formater.formatNumber(cost3bulan,"###,###.##")%>
                                                          <%}catch(Exception exc){} %>
                                                        </td>
                                                <% }catch(Exception exc){
                                                    out.println(exc);
                                                }
                                              }}%>
                                                       <td width="3" align="right">
                                                           <% try{ %> <%=Formater.formatNumber(locationTotalCost.getTriwulan1(),"###,###.##")%>                                                            
                                                         <%}catch(Exception exc){} %>
                                                        </td>
                                                        <td width="3" align="right"> 
                                                           <% try{ %><%=Formater.formatNumber(locationTotalCost.getTriwulan2(),"###,###.##")%>                                                         
                                                         <%}catch(Exception exc){} %>
                                                        </td>
                                                        <td width="3" align="right">
                                                          <% try{ %><%=Formater.formatNumber(locationTotalCost.getTriwulan3(),"###,###.##")%>
                                                            
                                                          <%}catch(Exception exc){} %>
                                                        </td>
                                                        <td width="3" align="right">
                                                            <% try{ %><%=Formater.formatNumber(locationTotalCost.getTriwulan4(),"###,###.##")%>
                                                          <%}catch(Exception exc){} %>
                                                        </td>
                                                        <td width="3" align="right">
                                                            <% try{ %><%=Formater.formatNumber(locationTotalCost.getTotalAnnual(),"###,###.##")%>
                                                          <%}catch(Exception exc){} %>
                                                        </td>
                                              
                                              </tr><%}%>
                                            
                                       <tr>
                                            <td nowrop ><center>Total</center></td>
                                        <%
                                         for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {      
                                             OutSourceValues subTotalCost=vSubTotalCost.get(k);
                                         %> 
                                                <td width="3" align="right"><b><%=Formater.formatNumber(subTotalCost.getTriwulan1(),"###,###.##")%></b></td>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(subTotalCost.getTriwulan2(),"###,###.##")%></b></td>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(subTotalCost.getTriwulan3(),"###,###.##")%></b></td>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(subTotalCost.getTriwulan4(),"###,###.##")%></b></td>

                                        <% }%>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(grandTotalCost.getTriwulan1(),"###,###.##")%></b></td>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(grandTotalCost.getTriwulan2(),"###,###.##")%></b></td>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(grandTotalCost.getTriwulan3(),"###,###.##")%></b></td>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(grandTotalCost.getTriwulan4(),"###,###.##")%></b></td>
                                                <td width="3" align="right"><b><%=Formater.formatNumber(grandTotalCost.getTotalAnnual(),"###,###.##")%></b></td>
                                      </tr>
                            
                           <% }%>
                </table>
            </td>    
        </tr>
                                                                                        <%if (privAdd) {%>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <table width="60%" border="0" cellspacing="1" cellpadding="1">
                                                                                                        <tr>
                                                                                                            <td width="10%"></td>
                                                                                                            <%if (oidOutSourcePlan!=0) {%>
                                                                                                                <td width="15%"><a href="javascript:cmdSave()"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a><b><a href="javascript:cmdSave()" class="command">Save</a></b> </td>
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
                                                    <td>&nbsp; Catatan :<br>
                                                        <ul>
                                                            Nilai biaya tahun sebelumnya adalah biaya alih daya yang sudah di entry melalui input biaya alih daya dengan status FINAL.
                                                            Sistem akan menghitung total biaya dibagi total jumlah karyawan alih daya.
                                                        </ul>
                                                        <ul>
                                                            Nilai perkiraan tahun perencanaan bisa dientry manual , atau dalam kondisi kosong / di awal generate akan otomatis di isi
                                                            oleh sistem saat save dengan memperhitungkan kenaikan dalam %.
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;<br><br></td>
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
</html>

