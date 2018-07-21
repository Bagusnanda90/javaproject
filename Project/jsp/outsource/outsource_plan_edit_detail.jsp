<%-- 
    Document   : outsource_plan_edit_generate
    Created on : Sep 15, 2015, 6:16:08 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.harisma.form.outsource.FrmOutSourcePlanDetail"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourcePlanDetail"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourcePlanDetail"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourcePlanDetail"%>
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

<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidOutSourcePlan = FRMQueryString.requestLong(request, "hidden_outsource_id");
    long position_id=FRMQueryString.requestLong(request, "position_id");
    int iErrCode=0;   
    
    OutSourcePlan outSourcePlan = null;
    
    try{
       outSourcePlan= PstOutSourcePlan.fetchExc(oidOutSourcePlan);
    }catch(Exception exc){
        outSourcePlan = new OutSourcePlan();
        outSourcePlan.setTitle(exc.toString());
    }
    
    if(outSourcePlan==null){
        outSourcePlan = new OutSourcePlan();
    }
    String whereClause = PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_OUTSOURCEPLANID]+"="+oidOutSourcePlan+" AND "
            + PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_POSITIONID]+"="+position_id;
    String orderBy = PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_POSITIONID];
    Vector vList = PstOutSourcePlanDetail.list(0,1, whereClause, orderBy );
    
    long oidDetail = 0;
    if(vList!=null && vList.size()>0){
        OutSourcePlanDetail outSourcePlanDetail = (OutSourcePlanDetail) vList.get(0); 
        oidDetail= outSourcePlanDetail.getOID();
    }
    
    String msgString = "" ;
            
    CtrlOutSourcePlanDetail ctrlOutSourcePlanDetail = new CtrlOutSourcePlanDetail(request);
    ctrlOutSourcePlanDetail.action(iCommand, oidDetail);
    OutSourcePlanDetail outSourcePlanDetail = ctrlOutSourcePlanDetail.getOutSourcePlanDetail();
    if(outSourcePlanDetail==null || outSourcePlanDetail.getOID()==0){
        outSourcePlanDetail = new OutSourcePlanDetail();
        outSourcePlanDetail.setPositionId(position_id);
    }
    FrmOutSourcePlanDetail frmOutSourcePlanDetail = ctrlOutSourcePlanDetail.getForm();
    msgString = ctrlOutSourcePlanDetail.getMessage();
%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>DIMATA HARISMA - Outsource</title>
        <script language="JavaScript">
      
  function cmdEdit() {
                document.frpresence.command.value = "<%=Command.EDIT%>";
                document.frpresence.action = "outsource_plan_edit_detail.jsp";
                document.frpresence.submit();
            }

  function cmdSave() {
                document.frpresence.command.value = "<%=Command.SAVE%>";
                document.frpresence.action = "outsource_plan_edit_detail.jsp";
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
                                                                                    <input type="hidden" name="position_id" value="<%=position_id%>" >
                                                                                    <input type="hidden" name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_OUTSOURCEPLANID]%>" value="<%=oidOutSourcePlan%>" >
                                                                                    
<table width="100%" border="0" cellspacing="2" cellpadding="2">
<tr>
    <td align="center" ><h3><%=outSourcePlan.getTitle() %></h3></td>
</tr>
<tr>
 <td><hr></td>
</tr>                                                                                        
<tr>
    <td>
        <table border="0" cellspacing="1" cellpadding="1">
            <tbody>
                <tr>
                    <th valign="middle">Position<% //dictionaryD.getWord(I_Dictionary.POSITION)%></th>
                    <td valign="middle">
                    <%
                        Vector pos_value = new Vector(1, 1);
                        Vector pos_key = new Vector(1, 1);
                        Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                        for (int i = 0; i < listPos.size(); i++) {
                            Position pos = (Position) listPos.get(i);
                            pos_key.add(pos.getPosition());
                            pos_value.add(String.valueOf(pos.getOID()));
                        }
                    %> <%= ControlCombo.draw(FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_POSITIONID], "formElemen", null, "" +outSourcePlanDetail.getPositionId(), pos_value, pos_key)%>  
                    </td>
                    <td>                         
                    </td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_POSITIONID)%></td>
                </tr>
                <tr>
                    <th>Rencana Biaya / orang [Rp]</th>
                    <td><input name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_COSTPERPERSON] %>" type="text" value="<%=Formater.formatNumber(outSourcePlanDetail.getCostPerPerson(),"###,###.##")%>" size="15"></td>
                    <td></td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_COSTPERPERSON)%></td>
                </tr>
                <tr>
                    <th valign="top" >Gambaran Umum</th>
                    <td colspan="2"><textarea name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_GENERALINFO] %>" rows="3" cols="60"><%=outSourcePlanDetail.getGeneralInfo()%></textarea></td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_GENERALINFO)%></td>                    
                </tr>
                <tr>
                    <th >Jenis perjanjian</th>
                    <td colspan="2"><input name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_TYPEOFCONTRACT] %>" type="text" value="<%=outSourcePlanDetail.getTypeOfContract()%>" size="60"></td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_TYPEOFCONTRACT)%></td>                    
                </tr>                
                <tr>
                    <th>Jangka Waktu</th>
                    <td colspan="2"><input name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_CONTRACTPERIOD] %>" type="text" value="<%=outSourcePlanDetail.getContractPeriod()%>"> [Tahun]</td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_CONTRACTPERIOD)%></td>                    
                </tr>
                <tr>
                    <th valign="top">Tujuan</th>
                    <td colspan="2"><textarea name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_OBJECTIVES] %>"rows="4" cols="60"><%=outSourcePlanDetail.getObjectives() %></textarea></td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_OBJECTIVES)%></td>                    
                </tr>                
                <tr>
                    <th valign="top" >Analisa Biaya dan Manfaat</th>
                    <td colspan="2"><textarea name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_COSTNBENEFITANLYSIS] %>"rows="8" cols="60"><%=outSourcePlanDetail.getCostNBenefitAnlysis()%></textarea></td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_COSTNBENEFITANLYSIS)%></td>                    
                </tr>                
                <tr>
                    <th >&nbsp;</th>
                    <td colspan="2">[Rp]<input type="text" name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_COSTTOTAL]%>" value="<%=Formater.formatNumber(outSourcePlanDetail.getCostTotal(),"###,###.##") %>" ></td>
                    <td>* <%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_COSTTOTAL)%></td>                    
                </tr>                
                <tr>
                    <th valign="top" >Analisa Resiko</th>
                    <td colspan="2"><textarea name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_RISKANALIYS] %>"rows="8" cols="60"><%=outSourcePlanDetail.getRiskAnaliys()%></textarea></td>
                    <td>*<%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_RISKANALIYS)%></td>                    
                </tr>                
                <tr>
                    <th valign="top">Keterangan</th>
                    <td colspan="2"><textarea name="<%=FrmOutSourcePlanDetail.fieldNames[FrmOutSourcePlanDetail.FRM_FIELD_DESCRIPTION ] %>"rows="4" cols="60"><%=outSourcePlanDetail.getDescription() %></textarea></td>
                    <td><%=frmOutSourcePlanDetail.getErrorMsg(FrmOutSourcePlanDetail.FRM_FIELD_DESCRIPTION)%></td>                    
                </tr>
            </tbody>
        </table>        
    </td>
</tr>
<tr>
    <td><%=msgString %></td>
</tr>
    
                                                                                        <%if (privAdd) {%>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <table width="60%" border="0" cellspacing="1" cellpadding="1">
                                                                                                        <tr>
                                                                                                            <td width="10%"></td>   
                                                                                                            <td class="command"> 
                                                  <%    ControlLine ctrLine = new ControlLine();
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidDetail+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidDetail+"')";
									String scancel = "javascript:cmdEdit('"+oidDetail+"')";
									ctrLine.setBackCaption("");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("");
									ctrLine.setSaveCaption("Save Detail");
									ctrLine.setDeleteCaption("");
									ctrLine.setConfirmDelCaption("");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
									
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString); 
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
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
</html>

