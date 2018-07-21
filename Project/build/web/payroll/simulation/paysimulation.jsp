

<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<% 
/* 
 * Page Name  		:  paysimulation.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
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
<%@ page import = "com.dimata.harisma.entity.payroll.PstPaySimulation" %>
<%@ page import = "com.dimata.harisma.entity.payroll.PaySimulation" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcPaySimulation" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.system.entity.PstSystemProperty" %>
<%@ page import = "com.dimata.util.blob.*" %>
<%@page import="com.dimata.harisma.form.payroll.CtrlPaySimulation"%>
<%@page import="com.dimata.harisma.form.payroll.FrmPaySimulation"%>
<%@page import="com.dimata.harisma.form.payroll.SrcFrmPaySimulation"%>
<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL , AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_SIMULATION); 
    
%>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->

<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass,  long simulationId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("99%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Title","20%");
		ctrlist.addHeader("Objective","30%");
                ctrlist.addHeader("Max. Add.Salary","30%");
                ctrlist.addHeader("Max. Add.Employee","30%");
		//ctrlist.addHeader("Status","30%");
                //ctrlist.addHeader("Created By", "20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
	
                
		for (int i = 0; i < objectClass.size(); i++) {
			 PaySimulation paySimulation = (PaySimulation) objectClass.get(i);
			 Vector rowx = new Vector();
			rowx.add(""+paySimulation.getTitle());
			rowx.add(""+paySimulation.getObjectives() );
			rowx.add(""+Formater.formatNumber(paySimulation.getMaxTotalBudget(), "###,###"));			
			rowx.add(""+paySimulation.getMaxAddEmployee() );
                        //rowx.add(""+paySimulation.getStatusDoc());
			//rowx.add(""+paySimulation.getCreadedById() );
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(paySimulation.getOID()));
		}
		return ctrlist.draw(index);
	}
	
	public String chekOID(long oid, Vector vct){
		if(vct!=null && vct.size()>0){
			for(int i=0; i<vct.size(); i++){
				TrainingDept td = (TrainingDept)vct.get(i);
				if(td.getDepartmentId() == oid){
					return "checked";
				}
			}
		}
		return "";
	}
%>
<%
int iCommand    = FRMQueryString.requestCommand(request);
int prevCommand = FRMQueryString.requestInt(request, "prev_command"); 
SrcPaySimulation srcPaySimulation = (SrcPaySimulation)session.getValue("SESS_PAY_SIMULATION");


long oidPaySimulation = FRMQueryString.requestLong(request, SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]);

if(srcPaySimulation==null) {
    srcPaySimulation = new SrcPaySimulation();
}
 Vector listSimulation = null;
 int vectSize=0;
 int start = FRMQueryString.requestInt(request, "start");
 int recordToGet = srcPaySimulation.getRecordToGet();
 
String orderClause = ""+ srcPaySimulation.getSortBy();
String whereClause = ""+ srcPaySimulation.getWhere();


ControlLine ctrLine = new ControlLine();

int iErrCode = 0 ; 


    CtrlPaySimulation ctrPaySimulation = new CtrlPaySimulation(request);
   
    iErrCode = ctrPaySimulation.action(iCommand, oidPaySimulation);
    FrmPaySimulation frmPaySimulation = ctrPaySimulation.getForm();
    PaySimulation paySimulation = ctrPaySimulation.getPaySimulation();
     
  
    String msgString = ctrPaySimulation.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstPaySimulation.findLimitStart(paySimulation.getOID(), recordToGet, "", orderClause);
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrPaySimulation.actionList(iCommand, start, vectSize, recordToGet);
    }
     
//if(iCommand==Command.LIST){
  listSimulation = PstPaySimulation.list(srcPaySimulation.getRecordStartFrom(), srcPaySimulation.getRecordToGet(),whereClause, orderClause);
  vectSize = PstPaySimulation.getCount(whereClause);
//}

%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - Payroll Simulation</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value="0";
	document.frmpaysimulation.command.value="<%=Command.ADD%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdAsk(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.ASK%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdConfirmDelete(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.DELETE%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdSave(){
	document.frmpaysimulation.command.value="<%=Command.SAVE%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdEdit(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.EDIT%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}
	
function cmdCancel(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.EDIT%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdBack(){
	document.frmpaysimulation.command.value="<%=Command.BACK%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>="";
	document.frmpaysimulation.submit();
}
	
function cmdBackSearch(){
	document.frmpaysimulation.command.value="<%=Command.BACK%>";
	document.frmpaysimulation.action="srcpaysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdListFirst(){
	document.frmpaysimulation.command.value="<%=Command.FIRST%>";
	document.frmpaysimulation.prev_command.value="<%=Command.FIRST%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdListPrev(){
	document.frmpaysimulation.command.value="<%=Command.PREV%>";
	document.frmpaysimulation.prev_command.value="<%=Command.PREV%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdListNext(){
	document.frmpaysimulation.command.value="<%=Command.NEXT%>";
	document.frmpaysimulation.prev_command.value="<%=Command.NEXT%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdListLast(){
	document.frmpaysimulation.command.value="<%=Command.LAST%>";
	document.frmpaysimulation.prev_command.value="<%=Command.LAST%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function cmdDelete(){
	document.frmpaysimulation.command.value="<%=Command.SUBMIT%>";
	document.frmpaysimulation.prev_command.value="<%=Command.SUBMIT%>";
	document.frmpaysimulation.action="paysimulation.jsp";
	document.frmpaysimulation.submit();
}

function openSimulation(){
    var oid = document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value;  
    var url = "paysimulation_struct.jsp?command=<%=Command.EDIT %>"+"&<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>="+oid;
    var myWindow = window.open( url,"SimulationStructure", "width=1200,height=800, scrollbars=yes");
    myWindow.focus();
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
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
	
    function showObjectForMenu(){
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
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Payroll Simulation List<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor"  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmpaysimulation" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start %>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name=<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%> value="<%=oidPaySimulation%>">
                                      <input type="hidden" name=<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_CREATED_BY_ID]%> value="<%=(userSession!=null && userSession.getEmployee()!=null? userSession.getEmployee().getOID():0) %>">				  
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">&nbsp; 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Payroll Simulation </td>
                                              </tr>
                                              <%
                                                try{
                                                   if (listSimulation!=null && listSimulation.size()>0){
                                                %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
												
                                                  <%= drawList(listSimulation, oidPaySimulation)%> 
												  
                                                </td>
                                                </tr>
                                              <%  }else{
											  %>
                                                  <tr align="left" valign="top"> 
                                                    <td height="22" valign="middle" colspan="3"> 
                                                  <span class="comment">&nbsp;List is empty ...</span>
                                                  </td></tr>
                                                  <%} 
                                              }catch(Exception exc){ 
                                                    out.println(exc.toString());
                                              }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
                                                       int cmd = 0;
                                                  if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                                                                    (iCommand == Command.NEXT || iCommand == Command.LAST)){
                                                                            cmd =iCommand; 
                                                  }else{
                                                              if(iCommand == Command.NONE || prevCommand == Command.NONE)
                                                                    cmd = Command.FIRST;
                                                              else 
                                                                    cmd =prevCommand; 
                                                       } 
                                                    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                     %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">
												
                                                 <%if(iCommand!=Command.ADD && iCommand!=Command.ASK && iCommand!=Command.SAVE && iCommand!=Command.EDIT && iErrCode==FRMMessage.NONE){%>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr>  
                                                      <td width="2"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="2"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add New Payroll Simulation</a></td>
                                                    						
                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdBackSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="454"><a href="javascript:cmdBackSearch()" class="command">Back to Search Training</a></td>
											 
                                                    </tr>
                                                  </table>
                                                  <%}%>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||iCommand==Command.SAVE ||(iErrCode!=FRMMessage.NONE)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="97%" border="0" cellspacing="1" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td valign="middle" colspan="4"><b>Payroll Simulation</b></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td  valign="middle" >&nbsp;</td>
                                                <td colspan="3" class="comment">*)= 
                                                  required
                                                </td>
                                                
                                              </tr>
                                              <tr valign="top">
                                                <td colspan="4" valign="top">
                                                  <table>
                                                    <tr valign="top"> 
                                                     <td valign="top">
                                                      <table>
                                                        <tr align="left" valign="top"> 
                                                          <td valign="top">Title</td>
                                                          <td  > 
                                                            <input type="text" name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_TITLE] %>"  value="<%= paySimulation.getTitle() %>" class="formElemen" size="50">
                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_TITLE) %> 
                                                          </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                        <td  valign="top" >Objectives</td>
                                                        <td > 
                                                          <textarea name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_OBJECTIVES] %>" class="formElemen" cols="50" rows="3"><%= paySimulation.getObjectives() %></textarea>
                                                        </td>
                                                        
                                                       </tr>
                                                        <tr align="left" valign="top"> 
                                                          <td valign="top" >Max Total Budget</td>
                                                          <td > 
                                                            <input type="text" name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_MAX_TOTAL_BUDGET] %>"  value="<%= Formater.formatNumber(paySimulation.getMaxTotalBudget(), "######") %>" class="formElemen" size="20">
                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_MAX_TOTAL_BUDGET) %> 
                                                          </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                         <td  valign="top">Max Total Employee</td>
                                                         <td height="21" > 
                                                           <input type="text" name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_MAX_ADD_EMPL] %>"  value="<%= paySimulation.getMaxAddEmployee() %>" class="formElemen" size="10">
                                                           * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_MAX_ADD_EMPL) %> 
                                                         </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                         <td  valign="top">Source of Payroll Period</td>
                                                         <td >
                                                              <%
                                                            Vector perValue = new Vector(1, 1);
                                                            Vector perKey = new Vector(1, 1);
                                                            Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");

                                                            for (int r = 0; r < listPeriod.size(); r++) {
                                                                PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                                                                perValue.add("" + payPeriod.getOID());
                                                                perKey.add(payPeriod.getPeriod());
                                                            }
                                                            %> <%= ControlCombo.draw(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_SOURCE_PAY_PERIOD_ID], null, "" + paySimulation.getSourcePayPeriodId() , perValue, perKey, "")%>
                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_SOURCE_PAY_PERIOD_ID) %> 
                                                            </td>
                                                          </tr>  
                                                           <tr align="left" valign="top"> 
                                                            <td  valign="top">&nbsp;</td>
                                                            <td > &nbsp;</td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td  valign="top">&nbsp;Simulation Structure</td>
                                                            <td > &nbsp;
                                                            <% if(paySimulation!=null && paySimulation.getOID()!=0){ %>
                                                            <div valign="center" ><img src="../../images/BtnNew.jpg" onclick="javascript:openSimulation();" ><a href="javascript:openSimulation()"> Open Simulation Structure</a></div>
                                                            
                                                            <%}%>
                                                            </td>
                                                          </tr>  
                                                      </table>
                                                    </td>
                                                    <td>
                                                         <table>
                                                        <tr align="left" valign="top"> 
                                                          <td  valign="top" >Type of employee</td>
                                                          <td  > 
                                                              <%
                                                                Vector empCategories = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY ]);
                                                                 Vector catValue = new Vector(1, 1);
                                                                  Vector catKey = new Vector(1, 1);


                                                                  for (int r = 0; r < empCategories.size(); r++) {
                                                                      EmpCategory empCat = (EmpCategory) empCategories.get(r);
                                                                      catValue.add("" + empCat.getOID());
                                                                      catKey.add(empCat.getEmpCategory() );
                                                                  }
                                                                 %>

                                                                 <%= ControlCheckBox.draw(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_EMPLOYEE_CATEGORIES], "", paySimulation.getEmployeeCategoryIds(), catValue,catKey,  "", 5)%>


                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_EMPLOYEE_CATEGORIES) %> 
                                                          </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                         <td  valign="top"  rowspan="=4">Payroll Component</td>
                                                         <td> 
                                                            <%
                                                              Vector payComponents = PstPayComponent.list(0, 0, "", PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE ] +"," + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]);
                                                               Vector compValue = new Vector(1, 1);
                                                               Vector compKey = new Vector(1, 1);

                                                                for (int r = 0; r < payComponents.size(); r++) {
                                                                    PayComponent comp = (PayComponent) payComponents.get(r);
                                                                    compValue.add("" + comp.getOID());
                                                                    compKey.add(comp.getCompCode()+" "+comp.getCompName());
                                                                }


                                                               %>
                                                               <%= ControlCombo.drawStringArraySelected(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_PAYROLL_COMPONENTS], null,null, paySimulation.getPayrollComponents() ,  compKey, compValue, null, "multiple=\"multiple\" size=\"15\" ") %>

                                                               <% // ControlCombo.draw(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_PAYROLL_COMPONENTS], null, "" + paySimulation.getSourcePayPeriodId() , compValue, compKey, "multiple=\"multiple\" size=\"10\" ")%>

                                                          * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_PAYROLL_COMPONENTS) %> 
                                                        </td>
                                                       </tr>   
                                                      </table>
                                                    </td>
                                                 </tr>
                                                </table>
                                       </td>
                                     </tr>
                                             
                                              <tr align="left" valign="top"> 
                                                <td  valign="middle"></td>
                                                <td  colspan="3"></td>
                                              </tr>

                                       

                                              <tr align="left" valign="top" > 
                                                <td colspan="4" class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidPaySimulation+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidPaySimulation+"')";
									String scancel = "javascript:cmdEdit('"+oidPaySimulation+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
										ctrLine.setDeleteCaption("Delete");
										ctrLine.setSaveCaption("Save");
										ctrLine.setAddCaption("Add new");
										ctrLine.setDeleteCaption("Delete");

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
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>

                                              <tr align="left" valign="top" > 
                                                <td colspan="4"> 
                                                  <div align="left"></div>
                                                </td>
                                              </tr>
                                            </table>
                                             <% } // end if %>
                                          </td>
                                        </tr>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
