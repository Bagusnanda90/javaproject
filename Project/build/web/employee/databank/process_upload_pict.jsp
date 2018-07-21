<% 
/* 
 * Page Name  		:  careerpath.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
%>

<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></title>
<script language="JavaScript">
function cmdAdd(){
	document.frmcareerpath.emp_picture_oid.value="0";
	document.frmcareerpath.command.value="<%=Command.ADD%>";
	document.frmcareerpath.prev_command.value="<%=prevCommand%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}

function cmdAsk(oidEmpPicture){
	document.frmcareerpath.emp_picture_oid.value=oidEmpPicture;
	document.frmcareerpath.command.value="<%=Command.ASK%>";
	document.frmcareerpath.prev_command.value="<%=prevCommand%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}

function cmdConfirmDelete(oidEmpPicture){
	document.frmcareerpath.emp_picture_oid.value=oidEmpPicture;
	document.frmcareerpath.command.value="<%=Command.DELETE%>";
	document.frmcareerpath.prev_command.value="<%=prevCommand%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}
function cmdSave(){
	document.frmcareerpath.command.value="<%=Command.SAVE%>";
	document.frmcareerpath.prev_command.value="<%=prevCommand%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
	}

function cmdEdit(oidEmpPicture){
	document.frmcareerpath.emp_picture_oid.value=oidEmpPicture;
	document.frmcareerpath.command.value="<%=Command.EDIT%>";
	document.frmcareerpath.prev_command.value="<%=prevCommand%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
	}

function cmdCancel(oidEmpPicture){
	document.frmcareerpath.emp_picture_oid.value=oidEmpPicture;
	document.frmcareerpath.command.value="<%=Command.EDIT%>";
	document.frmcareerpath.prev_command.value="<%=prevCommand%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}

function cmdBack(){
	document.frmcareerpath.command.value="<%=Command.BACK%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}

function cmdListSection(){	
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}

function cmdBackEmp(empOID){
	document.frmcareerpath.employee_oid.value=empOID;
	document.frmcareerpath.command.value="<%=Command.EDIT%>";	
	document.frmcareerpath.action="employee_edit.jsp";
	document.frmcareerpath.submit();
	}


function cmdListFirst(){
	document.frmcareerpath.command.value="<%=Command.FIRST%>";
	document.frmcareerpath.prev_command.value="<%=Command.FIRST%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}

function cmdListPrev(){
	document.frmcareerpath.command.value="<%=Command.PREV%>";
	document.frmcareerpath.prev_command.value="<%=Command.PREV%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
	}

function cmdListNext(){
	document.frmcareerpath.command.value="<%=Command.NEXT%>";
	document.frmcareerpath.prev_command.value="<%=Command.NEXT%>";
	document.frmcareerpath.action="picture.jsp";
	document.frmcareerpath.submit();
}

function cmdListLast(){
	document.frmcareerpath.command.value="<%=Command.LAST%>";
	document.frmcareerpath.prev_command.value="<%=Command.LAST%>";
	document.frmcareerpath.action="careerpath.jsp";
	document.frmcareerpath.submit();
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
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
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
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" --> 
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
                <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Career Path<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                              <form name="frmcareerpath" method ="post" action="careerpath.jsp">
                                <input type="hidden" name="command" value="<%=iCommand%>">
								 <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <tr> 
                                    <td class="tablecolor"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                              <tr align="left" valign="top"> 
                                                <td height="8"  colspan="3"> 
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                    </tr>
												   <% if(iCommand==Command.NONE || iCommand==Command.SAVE) {%>
                                                    <tr align="left" valign="top"> 
                                                      <td> 
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td width="4" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="24" height="25"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                            <td width="6" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td height="25" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add Picture</a> 
                                                            </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
													 <% }%>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3"> 
                                                  <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE))||(iCommand==Command.EDIT)||(iCommand==Command.ASK)||(iCommand==Command.LIST)){%>
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr align="left" valign="top" > 
                                                      <td class="command"> <%
														ctrLine.setLocationImg(approot+"/images");
														ctrLine.initDefault();
														ctrLine.setTableWidth("80%");
														String scomDel = "javascript:cmdAsk()";
														String sconDelCom = "javascript:cmdConfirmDelete()";
														String scancel = "javascript:cmdEdit()";
														ctrLine.setBackCaption("");
														ctrLine.setCommandStyle("buttonlink");
														ctrLine.setAddCaption("");
														ctrLine.setSaveCaption("Save Picture");
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
														%> <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
                                                    </tr>
                                                  </table>
                                                  <%}%>
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
                              </form>
                              <!-- #EndEditable --> </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td >&nbsp;</td>
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
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
