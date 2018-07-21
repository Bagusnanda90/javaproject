
<% 
/* 
 * Page Name  		:  srcguesthandling.jsp
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.session.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_GUEST_HANDLING, AppObjInfo.OBJ_GUEST_HANDLING); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!

	public String drawList(Vector objectClass ){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("95%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
		ctrlist.dataFormat("Date","10%","center","center");
		ctrlist.dataFormat("Guest Name","20%","center","left");
		ctrlist.dataFormat("Room","7%","center","center");		
		ctrlist.dataFormat("Diagnosis","17%","center","left");
		ctrlist.dataFormat("Treatment","23%","center","left");
		ctrlist.dataFormat("Remarks","23%","center","left");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();		
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			GuestHandling guestHandling = (GuestHandling)objectClass.get(i);
			Vector rowx = new Vector();
			
			String str_dt_Date = ""; 
			try{
				Date dt_Date = guestHandling.getDate();
				if(dt_Date==null){
					dt_Date = new Date();
				}

				str_dt_Date = Formater.formatDate(dt_Date, "dd/MM/yy");
			}catch(Exception e){ str_dt_Date = ""; }

			rowx.add(str_dt_Date);
			rowx.add("<a href=\"javascript:cmdEdit('"+guestHandling.getOID()+"')\">"+guestHandling.getGuestName()+"</a>");
			rowx.add(guestHandling.getRoom());			
			rowx.add(guestHandling.getDiagnosis());
			rowx.add(guestHandling.getTreatment());
			rowx.add(guestHandling.getDescription());

			lstData.add(rowx);			
		}

		return ctrlist.drawMe();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int guestHandlingOID = FRMQueryString.requestInt(request, "guest_handling_oid");

ControlLine ctrLine = new ControlLine(); 
SrcGuestHandling srcGuestHandling = new SrcGuestHandling();
SessGuestHandling sessGuestHandling = new SessGuestHandling();
CtrlGuestHandling ctrlGuestHandling = new CtrlGuestHandling(request);
int recordToGet = 10;
int vectSize = 0;

FrmSrcGuestHandling frmSrcGuestHandling = new FrmSrcGuestHandling(request, srcGuestHandling);
frmSrcGuestHandling.requestEntityObject(srcGuestHandling);

if(iCommand != Command.LIST && iCommand != Command.NONE){
	try{
		srcGuestHandling = (SrcGuestHandling)session.getValue(SessGuestHandling.SESS_SRC_GUESTHANDLING);
		if(srcGuestHandling == null)
			srcGuestHandling = new SrcGuestHandling();
	}catch(Exception e){		
		srcGuestHandling = new SrcGuestHandling();
	}
	iCommand = prevCommand; 
}
//if add new employee visit, system will be find start and iCommand where is new data...
vectSize = sessGuestHandling.countGuestHandling(srcGuestHandling);
if(iCommand == Command.ADD){
	start = PstGuestHandling.findLimitStart(guestHandlingOID, recordToGet,vectSize,srcGuestHandling);	
	iCommand = PstGuestHandling.findLimitCommand(start, recordToGet, vectSize);
}
//if start = vectSize when delete employee visit 
if(start == vectSize){
	if(vectSize > recordToGet)
		start = vectSize - recordToGet;
	else
		start = 0;
}

Vector listGuestHandling = new Vector(1,1);
if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST)){		
		start = ctrlGuestHandling.actionList(iCommand, start, vectSize, recordToGet);		
		listGuestHandling = sessGuestHandling.searchGuestHandling(srcGuestHandling, start, recordToGet);
}

session.putValue(SessGuestHandling.SESS_SRC_GUESTHANDLING, srcGuestHandling);
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Guest Hndling</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmguesthandling.command.value="<%=Command.ADD%>";
	document.frmguesthandling.prev_command.value="<%=Command.ADD%>";
	document.frmguesthandling.action="guesthandling_edit.jsp";
	document.frmguesthandling.submit();
}

function cmdEdit(oid){
	document.frmguesthandling.guest_handling_oid.value=oid;
	document.frmguesthandling.command.value="<%=Command.EDIT%>";
	document.frmguesthandling.prev_command.value="<%=prevCommand%>";
	document.frmguesthandling.action="guesthandling_edit.jsp";
	document.frmguesthandling.submit();
}

function cmdListFirst(){
	document.frmguesthandling.command.value="<%=Command.FIRST%>";
	document.frmsrcguesthandling.prev_command.value="<%=Command.FIRST%>";
	document.frmguesthandling.action="srcguesthandling.jsp";
	document.frmguesthandling.submit();
}

function cmdListPrev(){
	document.frmguesthandling.command.value="<%=Command.PREV%>";
	document.frmsrcguesthandling.prev_command.value="<%=Command.PREV%>";
	document.frmguesthandling.action="srcguesthandling.jsp";
	document.frmguesthandling.submit();
}

function cmdListNext(){
	document.frmguesthandling.command.value="<%=Command.NEXT%>";
	document.frmsrcguesthandling.prev_command.value="<%=Command.NEXT%>";
	document.frmguesthandling.action="srcguesthandling.jsp";
	document.frmguesthandling.submit();
}

function cmdListLast(){
	document.frmguesthandling.command.value="<%=Command.LAST%>";
	document.frmsrcguesthandling.prev_command.value="<%=Command.LAST%>";
	document.frmguesthandling.action="srcguesthandling.jsp";
	document.frmguesthandling.submit();
}


function cmdSearch(){
	document.frmsrcguesthandling.command.value="<%=Command.LIST%>";
	document.frmsrcguesthandling.prev_command.value="<%=Command.LIST%>";
	document.frmsrcguesthandling.action="srcguesthandling.jsp";
	document.frmsrcguesthandling.submit();
}

function cmdPrint(){
		var guestName  = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SRC_GUEST_NAME]%>.value;
		var diagnosis   = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SRC_DIAGNOSIS] %>.value;		
		var startYear  = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]+"_yr"%>.value;								
		var startMonth = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]+"_mn"%>.value;
		var startDate  = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]+"_dy"%>.value;
		var dueYear    = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_yr"%>.value;				
		var dueMonth   = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_mn"%>.value;				
		var dueDate    = document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_dy"%>.value;	
		var sortBy     = 0;
		var len = document.frmsrcguesthandling.elements.length;		
		for (var i=0; i < len;i++){
			if(document.frmsrcguesthandling.elements[i].checked){
				sortBy    = document.frmsrcguesthandling.elements[i].value;				
			}
		}
		var linkPage   = "guesthand_buffer.jsp?" +
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SRC_GUEST_NAME] %>=" + guestName + "&" +
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SRC_DIAGNOSIS] %>=" + diagnosis + "&" +						 
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]%>_yr=" + startYear + "&" + 
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]%>_mn=" + startMonth + "&" + 
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]%>_dy=" + startDate + "&" + 						 
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]%>_yr=" + dueYear + "&" + 
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]%>_mn=" + dueMonth + "&" + 
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]%>_dy=" + dueDate + "&" + 
						 "<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SORT_BY]%>=" + sortBy;  		
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			

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
<!--
function hideObjectForEmployee(){ 
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]+"_yr"%>.style.visibility ="hidden";   
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_mn"%>.style.visibility ="hidden";   
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_dy"%>.style.visibility ="hidden";   
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_yr"%>.style.visibility = "hidden";    

}

function showObjectForMenu(){
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM]+"_yr"%>.style.visibility = "";  
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_mn"%>.style.visibility= "";   
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_dy"%>.style.visibility = "";    
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO]+"_yr"%>.style.visibility = "";    

}

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
//-->
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Search Guest Handling<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcguesthandling" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                        <tr align="left" valign="top"> 
                                          <td  valign="middle" width="13%">&nbsp;</td>
                                          <td  valign="middle" width="9%">&nbsp;</td>
                                          <td  width="14%" class="comment">&nbsp;</td>
                                          <td  width="64%" class="comment">&nbsp;</td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td  valign="top" width="13%" align="right"><b>Search 
                                            By </b>&nbsp;&nbsp;</td>
                                          <td  valign="top" width="9%">Guest Name</td>
                                          <td colspan="2"> 
                                            <input type="text" name="<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SRC_GUEST_NAME] %>"  value="<%= srcGuestHandling.getGuestName() %>" class="elemenForm" size="40">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td  valign="top" width="13%">&nbsp;</td>
                                          <td  valign="top" width="9%">Diagnosis</td>
                                          <td colspan="2"> 
                                            <input type="text" name="<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SRC_DIAGNOSIS] %>"  value="<%= srcGuestHandling.getDiagnosis() %>" class="elemenForm" size="40">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td  valign="top" width="13%">&nbsp;</td>
                                          <td  valign="top" width="9%">Date </td>
                                          <td colspan="2">
										  <%Date fromDt = srcGuestHandling.getDateFrom();
										    if(fromDt == null){
												fromDt = new Date();
												fromDt.setDate(1);
											}
											
										    Date toDt = srcGuestHandling.getDateTo();
										    if(toDt == null){
												toDt = new Date();
												Calendar calendar = new GregorianCalendar();												
												toDt.setDate(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
											}
										   %>
										   <%=	ControlDate.drawDate(frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_FROM], fromDt,"formElemen", 1,-5) %> 
                                            &nbsp;to &nbsp;<%=	ControlDate.drawDate(frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_DATE_TO], toDt,"formElemen", 1,-5) %></td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td  valign="top" colspan="4" align="right"> 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td  valign="top" width="13%" align="right"><b>Sort 
                                            By</b>&nbsp;&nbsp;</td>
                                          <td  valign="top" colspan="2">
										  <% String sortBy = srcGuestHandling.getSortBy();  
										     if((sortBy == null) || (sortBy.equals(""))){
												sortBy = PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_NAME];	
											 }
											%> 
                                            <input type="radio" name="<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SORT_BY] %>" value="<%=PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_NAME]%>" <%if(sortBy.equals(PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_NAME])){%>checked<%}%>>
                                            Guest Name &nbsp;&nbsp; 
                                            <input type="radio" name="<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SORT_BY] %>" value="<%=PstGuestHandling.fieldNames[PstGuestHandling.FLD_DATE]%>" <%if(sortBy.equals(PstGuestHandling.fieldNames[PstGuestHandling.FLD_DATE])){%>checked<%}%>>
                                            Date </td>
                                          <td  valign="top"> 
                                            <input type="Submit" name="Submit" value="Search" onClick="javascript:cmdSearch()">
                                            <input type="reset" name="Reset" value="Reset" >
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td  valign="top" colspan="4">
                                            <hr>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
									<form name="frmguesthandling" method="post" action="">
									<input type="hidden" name="command" value="<%=iCommand%>">
									<input type="hidden" name="start" value="<%=start%>">
									<input type="hidden" name="prev_command" value="<%=prevCommand%>">
									<input type="hidden" name="guest_handling_oid" value="<%=guestHandlingOID%>">
									  <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                        <% if(listGuestHandling != null && listGuestHandling.size()>0){%>										
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2" class="listtitle">Guest 
                                            Handling List</td>
                                        </tr>										
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2"><%=drawList(listGuestHandling)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2"> 
                                            <% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
											%>
                                            <%=ctrLine.drawImageListLimit(iCommand==Command.BACK?Command.FIRST:iCommand,vectSize,start,recordToGet)%></td>
                                        </tr>
                                        <tr>
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr><%if(privAdd){%> 
                                                <td width="21%"> 
                                                  <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td height="34">&nbsp;</td>
                                                      <td width="11%" height="34"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                      <td width="4%" height="34"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="81%" class="command" nowrap height="34"><a href="javascript:cmdAdd()">Add 
                                                        New Guest Handling</a></td>
                                                    </tr>
                                                  </table>
                                                </td><%}%><%if(privPrint){%>
                                                <td width="24%"> 
                                                  <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td height="34">&nbsp;</td>
                                                      <td width="11%" height="34"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                      <td width="4%" height="34"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="81%" class="command" nowrap height="34"><a href="javascript:cmdPrint()">Print 
                                                        Guest Handling</a></td>
                                                    </tr>
                                                  </table>
                                                </td><%}%>
                                                <td width="55%">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}else{
											if(iCommand == Command.LIST){%>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2" class="comment">No Guest 
                                            Handling match with search data</td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2" class="comment">&nbsp;</td>
                                        </tr>
                                        <%  }
										}%>
                                        <%if(privAdd && (listGuestHandling == null || listGuestHandling.size()<1)){%>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td width="61%"> 
                                            <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td height="34">&nbsp;</td>
                                                <td width="11%" height="34"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="4%" height="34"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="81%" class="command" nowrap height="34"><a href="javascript:cmdAdd()">Add 
                                                  New Guest Handling</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td width="35%">&nbsp;</td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td height="14" width="4%">&nbsp;</td>
                                          <td height="14" width="61%">&nbsp;</td>
                                          <td height="14" width="35%">&nbsp;</td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	document.frmsrcguesthandling.<%=frmSrcGuestHandling.fieldNames[FrmSrcGuestHandling.FRM_FIELD_SRC_GUEST_NAME] %>.focus();
	
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>
