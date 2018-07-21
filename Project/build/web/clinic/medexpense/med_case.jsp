<%-- 
    Document   : med_case
    Created on : Feb 21, 2009, 1:59:13 PM
    Author     : Kartika(2009-02-21)   
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICAL, AppObjInfo.OBJ_MEDICAL_GROUP); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand, FrmMedicalCase objForm, MedicalCase objEntity, Vector objectClass,  long medicalCaseId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.dataFormat("Sort Number","10%","center","center");
		ctrlist.dataFormat("Case Group","30%","left","left");
		ctrlist.dataFormat("Case Name","30%","left","left");
		ctrlist.dataFormat("Case Link","5%","left","left");
		ctrlist.dataFormat("Max Use","5%","left","left");
		ctrlist.dataFormat("Max Unit","10%","left","left");
		ctrlist.dataFormat("Min Taken by","5%","left","left");
		ctrlist.dataFormat("Period","10%","left","left");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 MedicalCase medicalCase = (MedicalCase)objectClass.get(i);
			 rowx = new Vector();
                         
			 if(medicalCaseId == medicalCase.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
				//rowx.add(""+(i+1));	
                                rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_SORT_NUMBER] +"\" value=\""+medicalCase.getSortNumber()+"\" class=\"elemenForm\" size=\"5\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_CASE_GROUP] +"\" value=\""+medicalCase.getCaseGroup()+"\" class=\"elemenForm\" size=\"50\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_CASE_NAME] +"\" value=\""+medicalCase.getCaseName()+"\" class=\"elemenForm\" size=\"50\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_CASE_LINK] +"\" value=\""+medicalCase.getCaseLink()+"\" class=\"elemenForm\" size=\"50\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MAX_USE] +"\" value=\""+medicalCase.getMaxUse()+"\" class=\"elemenForm\" size=\"5\">");
                                rowx.add(""+com.dimata.gui.jsp.ControlCombo.drawWithStyle(objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MAX_USE_PERIOD],
                                        "Please Select",""+medicalCase.getMaxUsePeriod(),MedicalCase.getQtyUnitIndexString(),MedicalCase.getQtyUnitTitle(),"combo","combo"));

				//rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MAX_USE_PERIOD] +"\" value=\""+medicalCase.getMaxUsePeriod()+"\" class=\"elemenForm\" size=\"10\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MIN_TAKEN_BY] +"\" value=\""+medicalCase.getMinTakenBy()+"\" class=\"elemenForm\" size=\"5\">");
				//rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MIN_TAKEN_BY_PERIOD] +"\" value=\""+medicalCase.getMinTakenByPeriod()+"\" class=\"elemenForm\" size=\"10\">");
                                rowx.add(""+com.dimata.gui.jsp.ControlCombo.drawWithStyle(objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MIN_TAKEN_BY_PERIOD],
                                        "Please Select",""+medicalCase.getMinTakenByPeriod(),MedicalCase.getPeriodIndexString(),MedicalCase.getPeriodTitle(),"combo","combo"));

                                }
                         else{
				//rowx.add(""+(i+1));
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+medicalCase.getSortNumber()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+medicalCase.getCaseGroup()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+medicalCase.getCaseName()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+medicalCase.getCaseLink()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+medicalCase.getMaxUse()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+MedicalCase.qtyUnitTitle[medicalCase.getMaxUsePeriod()]+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+medicalCase.getMinTakenBy()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalCase.getOID())+"')\">"+MedicalCase.PeriodTitle[medicalCase.getMinTakenByPeriod()]+"</a>");
			} 

			lstData.add(rowx);
		}

		rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && objForm.errorSize() > 0)){ 
                        //rowx.add(""+(objectClass.size()+1));
                                rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_SORT_NUMBER] +"\" value=\""+objEntity.getSortNumber()+"\" class=\"elemenForm\" size=\"5\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_CASE_GROUP] +"\" value=\""+objEntity.getCaseGroup()+"\" class=\"elemenForm\" size=\"50\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_CASE_NAME] +"\" value=\""+objEntity.getCaseName()+"\" class=\"elemenForm\" size=\"50\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_CASE_LINK] +"\" value=\""+objEntity.getCaseLink()+"\" class=\"elemenForm\" size=\"50\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MAX_USE] +"\" value=\""+objEntity.getMaxUse()+"\" class=\"elemenForm\" size=\"5\">");
				//rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MAX_USE_PERIOD] +"\" value=\""+objEntity.getCaseName()+"\" class=\"elemenForm\" size=\"10\">");
                                                                rowx.add(""+com.dimata.gui.jsp.ControlCombo.drawWithStyle(objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MAX_USE_PERIOD],
                                        "Please Select",""+objEntity.getMaxUsePeriod(),MedicalCase.getQtyUnitIndexString(),MedicalCase.getQtyUnitTitle(),"combo","combo"));

				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MIN_TAKEN_BY] +"\" value=\""+objEntity.getCaseName()+"\" class=\"elemenForm\" size=\"5\">");
				//rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MIN_TAKEN_BY_PERIOD] +"\" value=\""+objEntity.getCaseName()+"\" class=\"elemenForm\" size=\"10\">");
                                rowx.add(""+com.dimata.gui.jsp.ControlCombo.drawWithStyle(objForm.fieldNames[FrmMedicalCase.FRM_FIELD_MIN_TAKEN_BY_PERIOD],
                                        "Please Select",""+objEntity.getMinTakenByPeriod(),MedicalCase.getPeriodIndexString(),MedicalCase.getPeriodTitle(),"combo","combo"));

		}

		lstData.add(rowx);

		return ctrlist.drawMe();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidMedicalCase = FRMQueryString.requestLong(request, "hidden_medical_level_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlMedicalCase ctrlMedLevel = new CtrlMedicalCase(request);
ControlLine ctrLine = new ControlLine();
Vector listMedicalCase = new Vector(1,1);

/*switch statement */
iErrCode = ctrlMedLevel.action(iCommand , oidMedicalCase);
/* end switch*/

FrmMedicalCase frmMedLevel = ctrlMedLevel.getForm();
MedicalCase medicalCase = ctrlMedLevel.getMedicalCase();
msgString =  ctrlMedLevel.getMessage();

/*count list All MedicalCase*/
int vectSize = PstMedicalCase.getCount(whereClause);

/*switch list MedicalCase*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlMedLevel.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
orderClause =  PstMedicalCase.fieldNames[PstMedicalCase.FLD_SORT_NUMBER];
listMedicalCase = PstMedicalCase.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listMedicalCase.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
                 start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listMedicalCase = PstMedicalCase.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Medical Case</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmmedicallevel.hidden_medical_level_id.value="0";
	document.frmmedicallevel.command.value="<%=Command.ADD%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdAsk(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.ASK%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdConfirmDelete(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.DELETE%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdSave(){
	document.frmmedicallevel.command.value="<%=Command.SAVE%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdEdit(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.EDIT%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdCancel(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.EDIT%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdBack(){
	document.frmmedicallevel.command.value="<%=Command.BACK%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdListFirst(){
	document.frmmedicallevel.command.value="<%=Command.FIRST%>";
	document.frmmedicallevel.prev_command.value="<%=Command.FIRST%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdListPrev(){
	document.frmmedicallevel.command.value="<%=Command.PREV%>";
	document.frmmedicallevel.prev_command.value="<%=Command.PREV%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdListNext(){
	document.frmmedicallevel.command.value="<%=Command.NEXT%>";
	document.frmmedicallevel.prev_command.value="<%=Command.NEXT%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

function cmdListLast(){
	document.frmmedicallevel.command.value="<%=Command.LAST%>";
	document.frmmedicallevel.prev_command.value="<%=Command.LAST%>";
	document.frmmedicallevel.action="med_case.jsp";
	document.frmmedicallevel.submit();
}

//-------------- script form image -------------------

function cmdDelPict(oidMedicalCase){
	document.frmimage.hidden_medical_level_id.value=oidMedicalCase;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="med_case.jsp";
	document.frmimage.submit();
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
			  <!-- #BeginEditable "contenttitle" --> 
                  Clinic &gt; Medical Case <!-- #EndEditable --> 
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
                                    <form name="frmmedicallevel" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_medical_level_id" value="<%=oidMedicalCase%>">
                                      
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">&nbsp;  
                                                </td>
                                              </tr>
											  <% if((listMedicalCase == null || listMedicalCase.size()<1)&& (iCommand == Command.NONE)){%>
											  <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                              </tr>
											  <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="comment"> 
                                                  &nbsp;&nbsp;No Medical Case available </td>
                                              </tr>
                                              <% }else{%>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Medical 
                                                  Level List </td>
                                              </tr>
                                              <%
												try{
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmMedLevel, medicalCase,listMedicalCase,oidMedicalCase)%> 
                                                </td>
                                              </tr>
                                              <% 
											  	}catch(Exception exc){ 
                                                                                                    System.out.println(exc);
											  	}
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
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images/ctr_line");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
											  <% if((iCommand == Command.NONE) || (iCommand== Command.BACK) || (iCommand == Command.FIRST ) ||
											 		(iCommand == Command.LAST) || (iCommand == Command.PREV) || (iCommand == Command.NEXT)){
											 		if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="24" height="23"><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Get List"></a></td>
                                                      <td width="6" height="23"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="23" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Medical Case </a> 
                                                      </td>
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
                                          <td height="8" valign="middle" width="17%">&nbsp; </td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidMedicalCase+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidMedicalCase+"')";
									String scancel = "javascript:cmdEdit('"+oidMedicalCase+"')";
									ctrLine.setBackCaption("Back to List Medical Case");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Yes Delete Medical Case");
									ctrLine.setDeleteCaption("Delete Medical Case");
									ctrLine.setSaveCaption("Save Medical Case");
									ctrLine.setAddCaption("Add Medical Case");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if( (privAdd == false  && privUpdate == false) || (iCommand == Command.SAVE && frmMedLevel.errorSize() == 0)){
										ctrLine.setSaveCaption("");
                                                                                ctrLine.setSaveCommand("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->&nbsp;{script}<!-- #EndEditable -->
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
