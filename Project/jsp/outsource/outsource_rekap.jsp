<%-- 
    Document   : outsource eval
    Created on : Jan, 2015, 10:42:46 AM
    Author     : Priska
--%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutsrcCostProv"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceCostMaster"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourcePlanLocation"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation"%>
<%@page import="com.dimata.harisma.entity.outsource.SrcObject"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceEvaluationProvider"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceEvaluationProvider"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceEvaluation"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceEvaluation"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourceEvaluation"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourceEvaluation"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  outsource_rekap.jsp
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
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_FORM_EVALUASI  );%>

<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../main/checkuser.jsp" %>

<%!

	public String drawList(JspWriter outJsp,int iCommand,FrmOutSourceEvaluation frmObject, Vector Divisi, Vector Posisi, SrcObject srcObject)

	{
        String result = "";
        //if(rekapitulasiAbsensi==null){
         //   return result; 
        //}
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(1);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("No", "5%", "1", "0");
        ctrlist.addHeader("Posisi", "10%", "1", "0");
        ctrlist.addHeader("Jumlah Tenaga", "5%", "1", "0");
        ctrlist.addHeader("biaya Per Orang", "5%", "1", "0");
        ctrlist.addHeader("Total Biaya", "5%", "1", "0");
        ctrlist.addHeader("PPJ", "5%", "1", "0");
    
        

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        
        try {
               ctrlist.drawListHeaderWithJsVer2(outJsp);//header
                    
                    for (int idxcom = 0; idxcom < Posisi.size(); idxcom++) {
                        Position position = (Position) Posisi.get(idxcom);
                        Vector rowx = new Vector();
                        rowx.add(""+(idxcom+1));
                        rowx.add(""+position.getPosition());
                        double realisasiHuman = PstOutsrcCostProv.listOutSourcetotalEmployeeProviderALL(0, 0, "", "",srcObject,position.getOID());
                        double realisasiCost = PstOutsrcCostProvDetail.getcostvalueALL(0, 0, "", "",srcObject,position.getOID());
                        rowx.add(""+(realisasiHuman));
                        rowx.add(""+(realisasiCost)); 
                        rowx.add("");   
                        rowx.add(""+(realisasiHuman*realisasiCost));    

                        ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idxcom);
                        
                    } 
               

                result = "";
                ctrlist.drawListEndTableJsVer2(outJsp);
            

        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + ex);
        }
        return result;
    }

%>






<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidOutSourceEvaluation = FRMQueryString.requestLong(request, "hidden_outSourceEvaluation_id");

            
            //pencarian
            
            long companyId = FRMQueryString.requestLong(request, "companyId");
            long divisionId = FRMQueryString.requestLong(request, "divisionId");
            long providerId = FRMQueryString.requestLong(request, "providerId");
            long startPeriodId = FRMQueryString.requestLong(request, "startPeriodId");
            long endPeriodId = FRMQueryString.requestLong(request, "endPeriodId");
            long type = FRMQueryString.requestLong(request, "type");

            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "";

            CtrlOutSourceEvaluation ctrlOutSourceEvaluation = new CtrlOutSourceEvaluation(request);
            ControlLine ctrLine = new ControlLine();
            Vector listOutSourceEvaluation = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlOutSourceEvaluation.action(iCommand, oidOutSourceEvaluation);
            /* end switch*/
            FrmOutSourceEvaluation frmOutSourceEvaluation = ctrlOutSourceEvaluation.getForm();

            /*count list All Position*/
            int vectSize = PstOutSourceEvaluation.getCount(whereClause);

            OutSourceEvaluation outSourceEvaluation = ctrlOutSourceEvaluation.getOutSourceEvaluation();
            msgString = ctrlOutSourceEvaluation.getMessage();
            
            listOutSourceEvaluation = PstOutSourceEvaluation.list(start, recordToGet, whereClause, orderClause);
            
            String whereperiode = PstPayPeriod.getPayPeriodBySelectedPeriod(startPeriodId,endPeriodId);
            Vector whereperiodeV = PstPayPeriod.getPayPeriodBySelectedPeriodV(startPeriodId,endPeriodId);
            SrcObject srcObject = new SrcObject();
            Vector Divisi = new Vector();
            Vector Posisi = new Vector();
            
            Vector Divisi2 = new Vector();
            Vector Posisi2 = new Vector();
            
            Vector Divisi3 = new Vector();
            Vector Posisi3 = new Vector();
            if (iCommand == Command.SEARCH){

            srcObject.setDivisiId(divisionId);
            srcObject.setProviderId(providerId);
            srcObject.setPayPeriodId(whereperiode);
            srcObject.setPayPeriodIdV(whereperiodeV);
            //mencari divisi terkait
            Divisi = PstDivision.listJoinOutSourceEval(0, 0,"", srcObject);
            Posisi = PstPosition.listJoinOutSourceEval(0, 0,"", srcObject);
           // Vector Provider = PstContactList.listJoinOutSourceEval(0, 0, "","");
            
            Divisi2 = PstDivision.listJoinOutSourcePlanLoc(0, 0,"", srcObject);
            Posisi2 = PstPosition.listJoinOutSourcePlanLoc(0, 0,"", srcObject);
            
            Divisi3 = PstDivision.listJoinOutSourcePlanLoc(0, 0,"", srcObject);
            Posisi3 = PstPosition.listJoinOutSourcePlanLoc(0, 0,"", srcObject);
                      }     

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 

    <head> 

        <title>DIMATA HARISMA - Outsource</title>
        <%@ include file = "../main/konfigurasi_jquery.jsp" %>    
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <script type="text/javascript" src="../javascripts/jquery.min.js"></script>
        <script type="text/javascript" src="../javascripts/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../javascripts/gridviewScroll.min.js"></script>
        <link href="../stylesheets/GridviewScroll.css" rel="stylesheet" />
        
        <script language="JavaScript">


            function cmdAdd(){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value="0";
                document.frmoutSourceEvaluation.command.value="<%=Command.ADD%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdUpdateDiv(){
                document.frmoutSourceEvaluation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdSearch(nilai){
                document.frmoutSourceEvaluation.command.value="<%=Command.SEARCH%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp?type="+nilai;
                document.frmoutSourceEvaluation.submit();
            }

            function cmdAsk(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.ASK%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdConfirmDelete(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.DELETE%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }
            function cmdSave(){
                document.frmoutSourceEvaluation.command.value="<%=Command.SAVE%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdEdit(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.EDIT%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdCancel(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.EDIT%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdBack(){
                document.frmoutSourceEvaluation.command.value="<%=Command.BACK%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListFirst(){
                document.frmoutSourceEvaluation.command.value="<%=Command.FIRST%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.FIRST%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListPrev(){
                document.frmoutSourceEvaluation.command.value="<%=Command.PREV%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.PREV%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListNext(){
                document.frmoutSourceEvaluation.command.value="<%=Command.NEXT%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.NEXT%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListLast(){
                document.frmoutSourceEvaluation.command.value="<%=Command.LAST%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.LAST%>";
                document.frmoutSourceEvaluation.action="outsource_rekap.jsp";
                document.frmoutSourceEvaluation.submit();
            }
            
            
        //function cmdSearchEmp(){
        //window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmOutSourceEvaluation&empPathId=<%=frmOutSourceEvaluation.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
        //}
        
        function cmdSearchEmpCreated(){
        window.open("<%=approot%>/employee/search/search.jsp?formName=frmoutSourceEvaluation&empPathId=<%=frmOutSourceEvaluation.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }
        
        function cmdSearchEmpApproved(){
        window.open("<%=approot%>/employee/search/searchApproved.jsp?formName=frmoutSourceEvaluation&empPathId=<%=frmOutSourceEvaluation.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_APPROVED_BY_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }
        
        function setEmployee(){
        window.open("<%=approot%>/employee/search/search.jsp?formName=frmoutSourceEvaluation&empPathId=EMPID", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }
        function cmdDetail(oidOutSourceEvaluation){
	window.open("<%=approot%>/outsource/outsource_evaluation_report_provider.jsp?hidden_outSourceEvaluation_id="+oidOutSourceEvaluation+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
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

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 


    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
        <%//if(isMSIE){%>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>

            <%//}else{%>

            <%//}%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">    
                    <%@ include file = "../main/header.jsp" %>
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../main/mnmain.jsp" %>
                </td>
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
                                                    DIMATA HARISMA - Outsource Report<!-- #EndEditable -->
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
                                                                                <form name="frmoutSourceEvaluation" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_outSourceEvaluation_id" value="<%=oidOutSourceEvaluation%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                             <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                                                                      <tr> 
                                                                                                          <td  nowrap="nowrap">Perusahaan    : </td><td>   
                                                                                                            <%
                                                                                                            Vector comp_value = new Vector(1, 1);
                                                                                                            Vector comp_key = new Vector(1, 1);
                                                                                                            Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                            comp_value.add(""+0);
                                                                                                            comp_key.add("select");
                                                                                                            for (int i = 0; i < listComp.size(); i++) {
                                                                                                                Company div = (Company) listComp.get(i);
                                                                                                                comp_key.add(div.getCompany());
                                                                                                                comp_value.add(String.valueOf(div.getOID()));
                                                                                                            }

                                                                                                            %>
                                                                                                            <%= ControlCombo.draw("companyId", "formElemen", null, "" + companyId, comp_value, comp_key,"onChange=\"javascript:cmdUpdateDiv()\"")%> 
                                                                                                        </td>

                                                                                                        </tr>
                                                                                                        <tr> 

                                                                                                        <td  nowrap="nowrap">Lokasi   :   </td><td>  
                                                                                                            <%
                                                                                                                Vector div_value = new Vector(1, 1);
                                                                                                                Vector div_key = new Vector(1, 1);
                                                                                                                //update by satrya 2013-09-07
                                                                                                                   div_key.add("-select-");
                                                                                                                    div_value.add("0");
                                                                                                                    String strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  companyId ;//oidCompany; 
                                                                                                                    Vector listDiv = PstDivision.list(0, 0, strWhere, " DIVISION ");
                                                                                                                for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                    Division div = (Division) listDiv.get(i);
                                                                                                                    div_key.add(div.getDivision());
                                                                                                                    div_value.add(String.valueOf(div.getOID()));
                                                                                                                    
                                                                                                                }

                                                                                                            %>
                                                                                                            <%= ControlCombo.draw("divisionId", "formElemen", null, "" + divisionId, div_value, div_key,"")%> 
                                                                                                       
                                                                                                        </td>

                                                                                                        </tr>

                                                                                                        <tr> 

                                                                                                        <td  nowrap="nowrap">Provider   :   </td><td>  
                                                                                                            <%
                                                                                                                Vector provider_value = new Vector(1, 1);
                                                                                                                Vector provider_key = new Vector(1, 1);
                                                                                                                Vector listprovider = PstContactList.list(0, 0, "", "");
                                                                                                                provider_value.add(""+0);
                                                                                                                provider_key.add("select");
                                                                                                                for (int i = 0; i < listprovider.size(); i++) {
                                                                                                                    ContactList contactList = (ContactList) listprovider.get(i);
                                                                                                                    provider_key.add(contactList.getCompName());
                                                                                                                    provider_value.add(String.valueOf(contactList.getOID()));
                                                                                                                }

                                                                                                            %>
                                                                                                            <%= ControlCombo.draw("providerId", "formElemen", null, "" + providerId, provider_value, provider_key,"")%> 
                                                                                                       
                                                                                                        </td>

                                                                                                        </tr>
                                                                                                        
                                                                                                        
                                                                                                        <tr> 

                                                                                                        <td  nowrap="nowrap">Start Periode   :  </td>
                                                                                                        <td>   
                                                                                                            
                                                                                                            <%
                                                                                                            Vector perValue = new Vector(1, 1);
                                                                                                            Vector perKey = new Vector(1, 1);
                                                                                                            Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                            for (int r = 0; r < listPeriod.size(); r++) {
                                                                                                                PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                                                                                                                perValue.add("" + payPeriod.getOID());
                                                                                                                perKey.add(payPeriod.getPeriod());
                                                                                                            }
                                                                                                             %> 
                                                                                                            <%=ControlCombo.draw("startPeriodId", null, "" + startPeriodId, perValue, perKey, "")%>

                                                                                                        </td>
                                                                                                       
                                                                                                        </tr>
                                                                                                        <tr> 

                                                                                                        <td  nowrap="nowrap">End Periode   :  </td>
                                                                                                        <td>
                                                                                                            <%=ControlCombo.draw("endPeriodId", null, "" + endPeriodId, perValue, perKey, "")%>
                                                                                                        </td>
                                                                                                        </tr>
                                                                                                         <tr align="left" valign="top">
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>
                                                                                                      <tr>
                                                                                                            <td width="39%"><a href="javascript:cmdSearch(1)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search "></a>
                                                                                                            <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                            <a href="javascript:cmdSearch(1)">Search Jumlah Alih Daya per Provider</a></td>  
                                                                                                            
                                                                                                      </tr>
                                                                                                      </table>
                                                                                                <%  if (iCommand == Command.SEARCH){ %>            
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                     <tr align="left" valign="top">
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>&nbsp;OutSource List </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>
                                                                                                    
                                                                                                    
                                                                                                  
                                                                                                        <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">Realiasai Rekap Jumlah Alih - Per lokasi dan Posisi </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>
                                                                                                            <%=drawList(out,iCommand ,frmOutSourceEvaluation , Divisi2, Posisi2, srcObject)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                 
                                                                                                </table>
                                                                                                <% } %>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;
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

                    <%@include file="../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>>
                    <%@ include file = "../main/footer.jsp" %>
                </td>
            </tr>
            <%}%>
        </table>
        <script type="text/javascript">
            $(document).ready(function () {
                gridviewScroll();
            });
            <%
                int freesize = 3;

            %>
                function gridviewScroll() {
                    gridView1 = $('#GridView1').gridviewScroll({
                        width: 1310,
                        height: 500,
                        railcolor: "##33AAFF",
                        barcolor: "#CDCDCD",
                        barhovercolor: "#606060",
                        bgcolor: "##33AAFF",
                        freezesize: <%=freesize%>,
                        arrowsize: 30,
                        varrowtopimg: "<%=approot%>/images/arrowvt.png",
                        varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                        harrowleftimg: "<%=approot%>/images/arrowhl.png",
                        harrowrightimg: "<%=approot%>/images/arrowhr.png",
                        headerrowcount: 2,
                        railsize: 16,
                        barsize: 15
                    });
                }
        </script>

    </body>

</html>