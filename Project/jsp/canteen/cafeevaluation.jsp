<% 
/* 
 * Page Name  		:  cafeevaluation.jsp
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
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<%@ page import = "com.dimata.harisma.form.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<%--
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>   
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
--%>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_CANTEEN_CAFE, AppObjInfo.OBJ_CANTEEN_CAFE_EVALUATION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
  //  boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
  //  boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
  //  boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
  //  boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>

<!-- Jsp Block -->

<%!

	public String drawList(Vector objectClass ,  long cafeEvaluationId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("cellStyle");
		ctrlist.setHeaderStyle("tableheader");
		ctrlist.addHeader("Checklist Mark Id","33%");
		ctrlist.addHeader("Cafe Checklist Id","33%");
		ctrlist.addHeader("Checklist Item Id","33%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			CafeEvaluation cafeEvaluation = (CafeEvaluation)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(cafeEvaluationId == cafeEvaluation.getOID())
				 index = i;

			rowx.add(String.valueOf(cafeEvaluation.getChecklistMarkId()));

			rowx.add(String.valueOf(cafeEvaluation.getCafeChecklistId()));

			rowx.add(String.valueOf(cafeEvaluation.getChecklistItemId()));

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(cafeEvaluation.getOID()));
		}

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidCafeEvaluation = FRMQueryString.requestLong(request, "hidden_cafe_evaluation_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlCafeEvaluation ctrlCafeEvaluation = new CtrlCafeEvaluation(request);
ControlLine ctrLine = new ControlLine();
Vector listCafeEvaluation = new Vector(1,1);

/*switch statement */
iErrCode = ctrlCafeEvaluation.action(iCommand , oidCafeEvaluation);
/* end switch*/
FrmCafeEvaluation frmCafeEvaluation = ctrlCafeEvaluation.getForm();

/*count list All CafeEvaluation*/
int vectSize = PstCafeEvaluation.getCount(whereClause);

CafeEvaluation cafeEvaluation = ctrlCafeEvaluation.getCafeEvaluation();
msgString =  ctrlCafeEvaluation.getMessage();

/*switch list CafeEvaluation*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidCafeEvaluation == 0))
	start = PstCafeEvaluation.findLimitStart(cafeEvaluation.getOID(),recordToGet, whereClause, orderClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlCafeEvaluation.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listCafeEvaluation = PstCafeEvaluation.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listCafeEvaluation.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listCafeEvaluation = PstCafeEvaluation.list(start,recordToGet, whereClause , orderClause);
}
%>


<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>harisma--</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmcafeevaluation.hidden_cafe_evaluation_id.value="0";
	document.frmcafeevaluation.command.value="<%=Command.ADD%>";
	document.frmcafeevaluation.prev_command.value="<%=prevCommand%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
}

function cmdAsk(oidCafeEvaluation){
	document.frmcafeevaluation.hidden_cafe_evaluation_id.value=oidCafeEvaluation;
	document.frmcafeevaluation.command.value="<%=Command.ASK%>";
	document.frmcafeevaluation.prev_command.value="<%=prevCommand%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
}

function cmdConfirmDelete(oidCafeEvaluation){
	document.frmcafeevaluation.hidden_cafe_evaluation_id.value=oidCafeEvaluation;
	document.frmcafeevaluation.command.value="<%=Command.DELETE%>";
	document.frmcafeevaluation.prev_command.value="<%=prevCommand%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
}
function cmdSave(){
	document.frmcafeevaluation.command.value="<%=Command.SAVE%>";
	document.frmcafeevaluation.prev_command.value="<%=prevCommand%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
	}

function cmdEdit(oidCafeEvaluation){
	document.frmcafeevaluation.hidden_cafe_evaluation_id.value=oidCafeEvaluation;
	document.frmcafeevaluation.command.value="<%=Command.EDIT%>";
	document.frmcafeevaluation.prev_command.value="<%=prevCommand%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
	}

function cmdCancel(oidCafeEvaluation){
	document.frmcafeevaluation.hidden_cafe_evaluation_id.value=oidCafeEvaluation;
	document.frmcafeevaluation.command.value="<%=Command.EDIT%>";
	document.frmcafeevaluation.prev_command.value="<%=prevCommand%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
}

function cmdBack(){
	document.frmcafeevaluation.command.value="<%=Command.BACK%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
	}

function cmdListFirst(){
	document.frmcafeevaluation.command.value="<%=Command.FIRST%>";
	document.frmcafeevaluation.prev_command.value="<%=Command.FIRST%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
}

function cmdListPrev(){
	document.frmcafeevaluation.command.value="<%=Command.PREV%>";
	document.frmcafeevaluation.prev_command.value="<%=Command.PREV%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
	}

function cmdListNext(){
	document.frmcafeevaluation.command.value="<%=Command.NEXT%>";
	document.frmcafeevaluation.prev_command.value="<%=Command.NEXT%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
}

function cmdListLast(){
	document.frmcafeevaluation.command.value="<%=Command.LAST%>";
	document.frmcafeevaluation.prev_command.value="<%=Command.LAST%>";
	document.frmcafeevaluation.action="cafeevaluation.jsp";
	document.frmcafeevaluation.submit();
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
<link rel="stylesheet" href="../style/main.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="2" cellpadding="2" height="100%">
	<tr>
		<td colspan="2" height="25" class="toptitle">
			<div align="center">Header Title</div>
		</td>
	</tr>
	<tr>  
		<td colspan="2" class="topmenu" height="20">
			<!-- #BeginEditable "menu_main" --><%@ include file = "../main/mnmain.jsp" %><!-- #EndEditable --> </td>
	</tr>
	<tr>
		<td width="88%" valign="top" align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="20" class="contenttitle" >
					<!-- #BeginEditable "contenttitle" -->
					Content Title .......
					<!-- #EndEditable --> 
					</td>
				</tr>
				<tr>
					<td valign="top">
					<!-- #BeginEditable "content" -->

						<form name="frmcafeevaluation" method ="post" action="">
				<input type="hidden" name="command" value="<%=iCommand%>">
				<input type="hidden" name="vectSize" value="<%=vectSize%>">
				<input type="hidden" name="start" value="<%=start%>">
				<input type="hidden" name="prev_command" value="<%=prevCommand%>">
				<input type="hidden" name="hidden_cafe_evaluation_id" value="<%=oidCafeEvaluation%>">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						 <tr align="left" valign="top">
							 <td height="8"  colspan="3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr align="left" valign="top">
						      <td height="8" valign="middle" colspan="3">
						      		<hr>
						      </td>
						  </tr>
						  <tr align="left" valign="top">
						      <td height="14" valign="middle" colspan="3" class="comment">&nbsp;CafeEvaluation
						       List </td>
						  </tr>
						  <%
							try{
								if (listCafeEvaluation.size()>0){
							%>
						  <tr align="left" valign="top">
						        <td height="22" valign="middle" colspan="3"> <%= drawList(listCafeEvaluation,oidCafeEvaluation)%> </td>
						  </tr>
						  <%  } 
						  }catch(Exception exc){ 
						  }%>
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
									  { 
									  	if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCafeEvaluation == 0))
									  		cmd = PstCafeEvaluation.findLimitCommand(start,recordToGet,vectSize);
									  	else
									  		cmd = prevCommand;
									  } 
								   } 
							    %>
								 <% ctrLine.setLocationImg(approot+"/images/ctr_line");
							   	ctrLine.initDefault();
								 %>
							    <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
						  </tr>
						  <tr align="left" valign="top">
						        <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add
						          New</a></td>
						  </tr>
					</table>
							 </td>
						 </tr>
						 <tr align="left" valign="top">
								  <td height="8" valign="middle" colspan="3">
							 <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmCafeEvaluation.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
								 <table width="100%" border="0" cellspacing="1" cellpadding="0">
					      <tr align="left" valign="top">
					         <td height="21" valign="middle" width="17%">&nbsp;</td>
					         <td height="21" colspan="2" width="83%" class="comment">*)= required</td>
					      </tr>
					    <tr align="left" valign="top">
					       <td height="21" valign="top" width="17%">Checklist Mark Id</td>
					       <td height="21" colspan="2" width="83%">
							<input type="text" name="<%=frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_CHECKLIST_MARK_ID] %>"  value="<%= cafeEvaluation.getChecklistMarkId() %>" class="formElemen">
						* <%= frmCafeEvaluation.getErrorMsg(FrmCafeEvaluation.FRM_FIELD_CHECKLIST_MARK_ID) %>
					    <tr align="left" valign="top">
					       <td height="21" valign="top" width="17%">Cafe Checklist Id</td>
					       <td height="21" colspan="2" width="83%">
							<input type="text" name="<%=frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_CAFE_CHECKLIST_ID] %>"  value="<%= cafeEvaluation.getCafeChecklistId() %>" class="formElemen">
						* <%= frmCafeEvaluation.getErrorMsg(FrmCafeEvaluation.FRM_FIELD_CAFE_CHECKLIST_ID) %>
					    <tr align="left" valign="top">
					       <td height="21" valign="top" width="17%">Checklist Item Id</td>
					       <td height="21" colspan="2" width="83%">
							<input type="text" name="<%=frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_CHECKLIST_ITEM_ID] %>"  value="<%= cafeEvaluation.getChecklistItemId() %>" class="formElemen">
						* <%= frmCafeEvaluation.getErrorMsg(FrmCafeEvaluation.FRM_FIELD_CHECKLIST_ITEM_ID) %>
								 <tr align="left" valign="top">
								   <td height="8" valign="middle" width="17%">&nbsp;</td>
								   <td height="8" colspan="2" width="83%">&nbsp; </td>
								 </tr>
								 <tr align="left" valign="top" >
								   <td colspan="3" class="command">
									<%
									ctrLine.setLocationImg(approot+"/images/ctr_line");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidCafeEvaluation+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidCafeEvaluation+"')";
									String scancel = "javascript:cmdEdit('"+oidCafeEvaluation+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
										ctrLine.setDeleteCaption("Delete");
										ctrLine.setSaveCaption("Save");
										ctrLine.setAddCaption("");

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
								 <tr>
								   	<td width="13%">&nbsp;</td>
								   	<td width="87%">&nbsp;</td>
								 </tr>
								 <tr align="left" valign="top" >
								   	<td colspan="3"><div align="left"></div>
								     </td>
								 </tr>
							 </table>
							<%}%>
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
	<tr>
		<td colspan="2" height="20" class="footer">
			<div align="center"> copyright Bali Information Technologies 2002</div>
		</td>
	</tr>
</table>
</body>
<!-- #EndTemplate -->
</html>
