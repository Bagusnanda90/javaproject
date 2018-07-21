<%-- 
    Document   : med_level
    Created on : Feb 10, 2009, 1:59:13 PM
    Author     : bayu , updated by Kartika(2009-02-21)   
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

	public String drawList(int iCommand, FrmMedicalLevel objForm, MedicalLevel objEntity, Vector objectClass,  long medicalLevelId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("60%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.dataFormat("Sort Number","5%","center","center");
		ctrlist.dataFormat("Level","40%","left","left");
		ctrlist.dataFormat("Class","60%","left","left");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 MedicalLevel medicalLevel = (MedicalLevel)objectClass.get(i);
			 rowx = new Vector();
                         
			 if(medicalLevelId == medicalLevel.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
				//rowx.add(""+(i+1));	
                                rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalLevel.FRM_FIELD_SORT_NUMBER] +"\" value=\""+medicalLevel.getSortNumber()+"\" class=\"elemenForm\" size=\"5\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalLevel.FRM_FIELD_MEDICAL_LEVEL_NAME] +"\" value=\""+medicalLevel.getLevelName()+"\" class=\"elemenForm\" size=\"50\">");
				rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalLevel.FRM_FIELD_MEDICAL_LEVEL_CLASS] +"\" value=\""+medicalLevel.getLevelClass()+"\" class=\"elemenForm\" size=\"50\">");
			 }
                         else{
				//rowx.add(""+(i+1));
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalLevel.getOID())+"')\">"+medicalLevel.getSortNumber()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalLevel.getOID())+"')\">"+medicalLevel.getLevelName()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalLevel.getOID())+"')\">"+medicalLevel.getLevelClass()+"</a>");
			} 

			lstData.add(rowx);
		}

		rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && objForm.errorSize() > 0)){ 
                        //rowx.add(""+(objectClass.size()+1));
                        rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalLevel.FRM_FIELD_SORT_NUMBER] +"\" value=\""+objEntity.getSortNumber()+"\" class=\"elemenForm\" size=\"5\">");
                        rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalLevel.FRM_FIELD_MEDICAL_LEVEL_NAME] +"\" value=\""+objEntity.getLevelName()+"\" class=\"elemenForm\" size=\"50\">");
                        rowx.add("<input type=\"text\" name=\""+objForm.fieldNames[FrmMedicalLevel.FRM_FIELD_MEDICAL_LEVEL_CLASS] +"\" value=\""+objEntity.getLevelClass()+"\" class=\"elemenForm\" size=\"50\">");

		}

		lstData.add(rowx);

		return ctrlist.drawMe();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidMedicalLevel = FRMQueryString.requestLong(request, "hidden_medical_level_id");
String source = FRMQueryString.requestString(request, "source");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlMedicalLevel ctrlMedLevel = new CtrlMedicalLevel(request);
ControlLine ctrLine = new ControlLine();
Vector listMedicalLevel = new Vector(1,1);

/*switch statement */
iErrCode = ctrlMedLevel.action(iCommand , oidMedicalLevel);
/* end switch*/

FrmMedicalLevel frmMedLevel = ctrlMedLevel.getForm();
MedicalLevel medicalLevel = ctrlMedLevel.getMedicalLevel();
msgString =  ctrlMedLevel.getMessage();

/*count list All MedicalLevel*/
int vectSize = PstMedicalLevel.getCount(whereClause);

/*switch list MedicalLevel*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlMedLevel.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
orderClause =  PstMedicalLevel.fieldNames[PstMedicalLevel.FLD_SORT_NUMBER];
listMedicalLevel = PstMedicalLevel.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listMedicalLevel.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
                 start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listMedicalLevel = PstMedicalLevel.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Medical Level</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmmedicallevel.hidden_medical_level_id.value="0";
	document.frmmedicallevel.command.value="<%=Command.ADD%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdAsk(oidMedicalLevel){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalLevel;
	document.frmmedicallevel.command.value="<%=Command.ASK%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdConfirmDelete(oidMedicalLevel){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalLevel;
	document.frmmedicallevel.command.value="<%=Command.DELETE%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdSave(){
	document.frmmedicallevel.command.value="<%=Command.SAVE%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdEdit(oidMedicalLevel){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalLevel;
	document.frmmedicallevel.command.value="<%=Command.EDIT%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdCancel(oidMedicalLevel){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalLevel;
	document.frmmedicallevel.command.value="<%=Command.EDIT%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdBack(){
	document.frmmedicallevel.command.value="<%=Command.BACK%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdListFirst(){
	document.frmmedicallevel.command.value="<%=Command.FIRST%>";
	document.frmmedicallevel.prev_command.value="<%=Command.FIRST%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdListPrev(){
	document.frmmedicallevel.command.value="<%=Command.PREV%>";
	document.frmmedicallevel.prev_command.value="<%=Command.PREV%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdListNext(){
	document.frmmedicallevel.command.value="<%=Command.NEXT%>";
	document.frmmedicallevel.prev_command.value="<%=Command.NEXT%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

function cmdListLast(){
	document.frmmedicallevel.command.value="<%=Command.LAST%>";
	document.frmmedicallevel.prev_command.value="<%=Command.LAST%>";
	document.frmmedicallevel.action="med_level.jsp";
	document.frmmedicallevel.submit();
}

//-------------- script form image -------------------

function cmdDelPict(oidMedicalLevel){
	document.frmimage.hidden_medical_level_id.value=oidMedicalLevel;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="med_level.jsp";
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(source==null || source.length()==0){%> 
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
                  Clinic &gt; Medical Level <!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmmedicallevel" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_medical_level_id" value="<%=oidMedicalLevel%>">
                                      <input type="hidden" name="source" value="">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">&nbsp;  
                                                </td>
                                              </tr>
											  <% if((listMedicalLevel == null || listMedicalLevel.size()<1)&& (iCommand == Command.NONE)){%>
											  <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                              </tr>
											  <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="comment"> 
                                                  &nbsp;&nbsp;No Medical Level available </td>
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
                                                  <%= drawList(iCommand,frmMedLevel, medicalLevel,listMedicalLevel,oidMedicalLevel)%> 
                                                </td>
                                              </tr>
                                              <% 
											  	}catch(Exception exc){ 
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
                                                        New Medical Level </a> 
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
									String scomDel = "javascript:cmdAsk('"+oidMedicalLevel+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidMedicalLevel+"')";
									String scancel = "javascript:cmdEdit('"+oidMedicalLevel+"')";
									ctrLine.setBackCaption("Back to List Medical Level");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Yes Delete Medical Level");
									ctrLine.setDeleteCaption("Delete Medical Level");
									ctrLine.setSaveCaption("Save Medical Level");
									ctrLine.setAddCaption("Add Medical Level");

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
</html>
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>