<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmKPI_Employee_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_ListAllDataEmp"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Employee_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_List"%>
<%@page import="com.dimata.harisma.entity.masterdata.Company"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompany"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_List"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_Company_Target"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlKPI_Company_Target"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Company_Target"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmKPI_Company_Target"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  kpi_achievement.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: priska
 * @version  		: 01 
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
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->

<%!    public String drawList2(JspWriter outJsp,int iCommand, Vector objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun) {
        String result = "";
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(2);
        //mengambil nama dari kode komponent
        
        ctrlist.addHeader("NO", "5%", "2", "0");
        ctrlist.addHeader("KPI", "5%", "2", "0");
        ctrlist.addHeader("DETAIL", "5%", "2", "0");
        ctrlist.addHeader("TARGET", "20%", "0", "13");
        ctrlist.addHeader("JANUARI", "5%", "0", "0");
        ctrlist.addHeader("FEBRUARI", "5%", "0", "0");
        ctrlist.addHeader("MARET", "5%", "0", "0");
        ctrlist.addHeader("APRIL", "5%", "0", "0");
        ctrlist.addHeader("MEI", "5%", "0", "0");
        ctrlist.addHeader("JUNI", "5%", "0", "0");
        ctrlist.addHeader("JULI", "5%", "0", "0");
        ctrlist.addHeader("AGUSTUS", "5%", "0", "0");
        ctrlist.addHeader("SEPTEMBER", "5%", "0", "0");
        ctrlist.addHeader("OKTOBER", "5%", "0", "0");
        ctrlist.addHeader("NOVEMBER", "5%", "0", "0");
        ctrlist.addHeader("DESEMBER", "5%", "0", "0");
        ctrlist.addHeader("TOTAL", "5%", "0", "0");
        
        ctrlist.addHeader("ACHIEVEMENT", "20%", "0", "13");
        ctrlist.addHeader("JANUARI", "5%", "0", "0");
        ctrlist.addHeader("FEBRUARI", "5%", "0", "0");
        ctrlist.addHeader("MARET", "5%", "0", "0");
        ctrlist.addHeader("APRIL", "5%", "0", "0");
        ctrlist.addHeader("MEI", "5%", "0", "0");
        ctrlist.addHeader("JUNI", "5%", "0", "0");
        ctrlist.addHeader("JULI", "5%", "0", "0");
        ctrlist.addHeader("AGUSTUS", "5%", "0", "0");
        ctrlist.addHeader("SEPTEMBER", "5%", "0", "0");
        ctrlist.addHeader("OKTOBER", "5%", "0", "0");
        ctrlist.addHeader("NOVEMBER", "5%", "0", "0");
        ctrlist.addHeader("DESEMBER", "5%", "0", "0");
        ctrlist.addHeader("TOTAL", "5%", "0", "0");
        ctrlist.addHeader("TOTAL ALL", "20%", "0", "1");
        ctrlist.addHeader("GAP", "5%", "0", "0");
        
        
        
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        ctrlist.drawListHeaderWithJsVer2(outJsp);
        
        try{
           int no = 1 ;
        for (int i = 0; i < objectClass.size(); i++) {
            Vector listKPI = (Vector) objectClass.get(i);
			 Vector rowx = new Vector();
            double totaltarget = 0;
            double totalAchiev = 0;
             
                
                     rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(listKPI.get(0))+"')\"> "+ no + " </a>");
                     rowx.add(""+String.valueOf(listKPI.get(1)));
                     rowx.add(""+String.valueOf(listKPI.get(2)));
                for (int k = 3; k<15 ; k++){
                    if(k<listKPI.size()){
                        rowx.add(""+ ( k<listKPI.size() ? (""+listKPI.get(k)) : ""));
                        totaltarget = totaltarget + ( k<listKPI.size() ? ((Double)listKPI.get(k)).doubleValue() : 0.0d );
                     }else{
                        rowx.add("-");
                     }
               }
              
            rowx.add(""+totaltarget);
            
                for (int k = 15; k<27 ; k++){
                    if(k<listKPI.size()){ //Formater.formatNumber(kPI_ListAllDataEmp.getPercentOfTarget(), "#,###.##")
                       // double nilai = Double.valueOf((Double)listKPI.get(k));
                        rowx.add(""+ Formater.formatNumber((Double)listKPI.get(k), "#,###.#"));
                        totalAchiev = totalAchiev + ( k<listKPI.size() ? ((Double)listKPI.get(k)).doubleValue() : 0.0d );
                     }else{
                        rowx.add("-");
                     }
               }
            rowx.add(""+ Formater.formatNumber(totalAchiev, "#,###.#"));
            double gap = Math.abs(totalAchiev-totaltarget);
            rowx.add(""+ Formater.formatNumber(gap, "#,###.#"));
            ctrlist.drawListRowJsVer2(outJsp, 0, rowx, 0); 
            no++;
          }
        
        ctrlist.drawListEndTableJsVer2(outJsp);
        
        
            } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp: " + ex);
        }
        return result;
    }

 %>


<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidKPI_Company_Target = FRMQueryString.requestLong(request, "hidden_kpi_company_target_id");
            
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "";

            CtrlKPI_Company_Target ctrlKPI_Company_Target = new CtrlKPI_Company_Target(request);
            ControlLine ctrLine = new ControlLine();
            Vector listAllKPI_Company_Target = new Vector();
             Vector listKPI_Employee_Target = new Vector(1, 1);
            long CompanyId = FRMQueryString.requestLong(request, FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_COMPANY_ID]);
            long kpiListId = FRMQueryString.requestLong(request, FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_KPI_LIST_ID]);
            int tahun = FRMQueryString.requestInt(request, FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_YEAR]);
            if (tahun == 0){
             tahun = 2015;
            }
            
            
            /* get record to display */
          //  listKPI_Company_Target = PstKPI_Company_Target.list(start, recordToGet, whereClause, orderClause);
           
            Vector listKPI_Company_Target = new Vector(1, 1);
            Vector kpiV = PstKPI_List.listAll();
            listAllKPI_Company_Target = PstKPI_Company_Target.listAllKPIdata(tahun, kpiV, CompanyId);
            
           
           //listKPI_Employee_Target = PstKPI_Company_Target.listAlldataEmployee(tahun, kpiListId, CompanyId);
           
            /*switch statement */
            iErrCode = ctrlKPI_Company_Target.action(iCommand, oidKPI_Company_Target, kpiListId, tahun, CompanyId, listKPI_Employee_Target);
            /* end switch*/
            FrmKPI_Company_Target frmKPI_Company_Target = ctrlKPI_Company_Target.getForm();
            FrmKPI_Employee_Target frmKPI_Employee_Target = new FrmKPI_Employee_Target();
            /*count list All Position*/
            int vectSize = PstKPI_Company_Target.getCount(whereClause);

            KPI_Company_Target kpi_company_target = ctrlKPI_Company_Target.getdKPI_Company_Target();
            msgString = ctrlKPI_Company_Target.getMessage();

            /*switch list KPI_Company_Target*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstKPI_Company_Target.findLimitStart(kpi_company_target.getOID(),recordToGet, whereClause);
                oidKPI_Company_Target = kpi_company_target.getOID();
            }

          
            /* end switch list*/

            
  
            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listAllKPI_Company_Target.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                //listKPI_Company_Target = PstKPI_Company_Target.list(start, recordToGet, whereClause, orderClause);
                listAllKPI_Company_Target = PstKPI_Company_Target.listAllKPIdata(tahun, kpiV, CompanyId);
            }

           if (iCommand == Command.GOTO){
               listAllKPI_Company_Target = PstKPI_Company_Target.listAllKPIdata(tahun, kpiV, CompanyId);
           }
          
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>KPI TYPE</title>

        <%@ include file = "../../main/konfigurasi_jquery.jsp" %>    
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
        <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
        <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
        
        <script language="JavaScript">

function cmdAdd(){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value="0";
	document.frmKPI_Company_Target.command.value="<%=Command.ADD%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdAsk(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.ASK%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}


function cmdConfirmDelete(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.DELETE%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdSave(){
	document.frmKPI_Company_Target.command.value="<%=Command.SAVE%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdEdit(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdCancel(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdBack(){
	document.frmKPI_Company_Target.command.value="<%=Command.BACK%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListFirst(){
	document.frmKPI_Company_Target.command.value="<%=Command.FIRST%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.FIRST%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListPrev(){
	document.frmKPI_Company_Target.command.value="<%=Command.PREV%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.PREV%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListNext(){
	document.frmKPI_Company_Target.command.value="<%=Command.NEXT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.NEXT%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListLast(){
	document.frmKPI_Company_Target.command.value="<%=Command.LAST%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.LAST%>";
	document.frmKPI_Company_Target.action="kpi_achievement.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdUpdateSec(){
                document.frmKPI_Company_Target.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmKPI_Company_Target.action="kpi_achievement.jsp";
                document.frmKPI_Company_Target.submit();
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
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 

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

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
			  <!-- #BeginEditable "contenttitle" -->Report 
                  &gt;  Company Performance <!-- #EndEditable --> 
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
                                    <form name="frmKPI_Company_Target" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
				      <input type="hidden" name="KPI_Company_Target_oid" value="<%=oidKPI_Company_Target%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap> 
                                                        <div align="left">Tahun</div></td>
                                                      <td width="83%"> <%= ControlCombo.draw(frmKPI_Company_Target.fieldNames[frmKPI_Company_Target.FRM_FIELD_YEAR],"formElemen",null, ""+tahun, frmKPI_Company_Target.getYearValue(), frmKPI_Company_Target.getYearKey(), "onChange=\"javascript:cmdUpdateSec()\"") %></td> 
                                                    </tr>
                                                    
                                                    
                                                    <tr align="left" valign="top">
                                                        <td valign="top" width="17%">
                                                            Company</td>
                                                        <td width="83%">
                                                        <%
                                                                        Vector comp_value2 = new Vector(1, 1);
                                                                        Vector comp_key2 = new Vector(1, 1);
                                                                        Vector listComp2 = PstCompany.list(0, 0, "", " COMPANY ");
                                                                        comp_value2.add(""+0);
                                                                        comp_key2.add("select");
                                                                        for (int i = 0; i < listComp2.size(); i++) {
                                                                            Company div2 = (Company) listComp2.get(i);
                                                                            comp_key2.add(div2.getCompany());
                                                                            comp_value2.add(String.valueOf(div2.getOID()));
                                                                        }

                                                                        %>
                                                           <%= ControlCombo.draw(FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + (CompanyId), comp_value2, comp_key2,"onChange=\"javascript:cmdUpdateSec()\"")%>     
                                                        </td>
                                                    </tr>
                                                    
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td class="listtitle" width="37%">&nbsp;</td>
                                                      <td width="63%" class="comment">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listtitle" width="37%">Doc  List</td>
                                                      <td width="63%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%  if(iCommand == Command.SAVE || iCommand == Command.GOTO || iCommand == Command.ASK || iCommand == Command.EDIT) {
												try{
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                   <%= drawList2(out, iCommand, listAllKPI_Company_Target, oidKPI_Company_Target, frmKPI_Company_Target, tahun)%>
                                                                                                         </tr>
                                              <% 
											  }catch(Exception exc){ 
											  }
                                                        }
                                                  %>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else 
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmKPI_Company_Target.errorSize()<1)){
											  	if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                    <!--  <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td> 
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Doc Type</a> </td> -->
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
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command" height="26"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									//String scomDel = "javascript:cmdAsk('"+oidKPI_Company_Target+"')";
									//String sconDelCom = "javascript:cmdConfirmDelete('"+oidKPI_Company_Target+"')";
									String scancel = "javascript:cmdEdit('"+oidKPI_Company_Target+"')";
									ctrLine.setBackCaption("");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("");
									ctrLine.setSaveCaption("Save KPI_Company_Target");
									//ctrLine.setDeleteCaption("Delete KPI_Company_Target");
									ctrLine.setConfirmDelCaption("");
                                                                        
									ctrLine.setDeleteCaption("");

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										//ctrLine.setAddCaption("");
									}
									
									if((listAllKPI_Company_Target.size()<1)&&(iCommand == Command.NONE))
										 //iCommand = Command.ADD;  
										 
									if(iCommand == Command.ASK)
										//ctrLine.setDeleteQuestion(msgString);										 
									%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
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
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
