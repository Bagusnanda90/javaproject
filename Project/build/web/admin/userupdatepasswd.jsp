
<%
/*
 * userupdatepasswd.jsp
 *
 * Created on April 04, 2002, 11:30 AM
 *  
 * @author  ktanjana
 * @version 
 */ 
%>

<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.admin.*" %>
<%@ page import = "com.dimata.harisma.session.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_UPDATE_PASSWORD); %>
<%@ include file = "../main/checkuser.jsp" %>

<!-- JSP Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
long appUserOID = FRMQueryString.requestLong(request,"user_oid");
int start = FRMQueryString.requestInt(request, "start"); 

int excCode = FRMMessage.NONE;
String msgString =  "";

ControlLine ctrLine = new ControlLine();
CtrlAppUser ctrlAppUser = new CtrlAppUser(request);
FrmAppUser frmAppUser = ctrlAppUser.getForm();

if(iCommand == Command.NONE)
{
	iCommand = Command.EDIT;
}

AppUser appUser = userSession.getAppUser();
if(iCommand == Command.SAVE)
{
	AppUser appUserUpdated = new AppUser();
	frmAppUser.requestEntityObject(appUserUpdated);
	String pwd = FRMQueryString.requestString(request,frmAppUser.fieldNames[frmAppUser.FRM_PASSWORD]);
	String repwd  = FRMQueryString.requestString(request,frmAppUser.fieldNames[frmAppUser.FRM_CFRM_PASSWORD]);
	if(!pwd.equals(repwd))
	{
		excCode = FRMMessage.ERR_PWDSYNC;
		msgString = FRMMessage.getMessage(excCode);
	}
	
	// simpan data update ke database
	try
	{
		appUser.setLoginId(appUserUpdated.getLoginId());
		appUser.setPassword(appUserUpdated.getPassword());
		appUser.setFullName(appUserUpdated.getFullName());
		appUser.setEmail(appUserUpdated.getEmail());
		appUser.setDescription(appUserUpdated.getDescription());
		
		PstAppUser.update(appUser);
	}
	catch(Exception e)
	{
		System.out.println("Exc when save data to database : " + e.toString());
	}
}
%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Update Data User</title>
<script language="JavaScript">

function cmdSave()
{
	document.frmAppUser.command.value="<%=Command.SAVE%>";
	document.frmAppUser.action="userupdatepasswd.jsp";
	document.frmAppUser.submit();
}

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<script language="JavaScript">
<!--
function MM_swapImgRestore() 
{ //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<!-- #EndEditable --> 
</head>  

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->User 
                  Management > Update Date User<!-- #EndEditable --> </strong></font> 
                </td>
        </tr>
        <tr> 
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <link rel="stylesheet" href="../css/default.css" type="text/css">
                                    <form name="frmAppUser" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="user_oid" value="<%=appUserOID%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <table width="100%">
                                        <tr> 
                                          <td colspan="2" class="txtheading1"></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" height="26">Login ID</td>
                                          <td width="87%" height="26"> 
                                            <input type="text" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_LOGIN_ID] %>" value="<%=appUser.getLoginId()%>" class="formElemen">
                                            * &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_LOGIN_ID) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Password</td>
                                          <td width="87%"> 
                                            <input type="password" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_PASSWORD] %>" value="<%=appUser.getPassword()%>" class="formElemen">
                                            * &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_PASSWORD) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Confirm Password</td>
                                          <td width="87%"> 
                                            <input type="password" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_CFRM_PASSWORD] %>" value="<%=appUser.getPassword()%>" class="formElemen">
                                            * &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_CFRM_PASSWORD) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Fullname</td>
                                          <td width="87%"> 
                                            <input type="text" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_FULL_NAME] %>" value="<%=appUser.getFullName()%>" class="formElemen" size="50">
                                            * &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_FULL_NAME) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Email</td>
                                          <td width="87%"> 
                                            <input type="text" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_EMAIL] %>" value="<%=appUser.getEmail()%>" size="40" class="formElemen">
                                            &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_EMAIL) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Description</td>
                                          <td width="87%"> 
                                            <textarea name="<%=frmAppUser.fieldNames[frmAppUser.FRM_DESCRIPTION] %>" cols="35" rows="4" class="formElemen"><%=appUser.getDescription()%></textarea>
                                            &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_DESCRIPTION) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top" height="14" nowrap>&nbsp;</td>
                                          <td width="87%" height="14">&nbsp;</td>
                                        <tr> 
                                          <td colspan="2" class="command"> 
                                            <%
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();
											ctrLine.setTableWidth("80");
											String scomDel = "javascript:cmdDelete('"+appUserOID+"')";
											String sconDelCom = "javascript:cmdConfirmDelete('"+appUserOID+"')";
											String scancel = "javascript:cmdCancel('"+appUserOID+"')";
											ctrLine.setBackCaption("");
											ctrLine.setCommandStyle("buttonlink"); 
											ctrLine.setSaveCaption("Save User");
											ctrLine.setDeleteCaption("");
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
                                            <%= ctrLine.drawImage(iCommand, excCode, msgString)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td width="87%">&nbsp;</td>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --><!-- #EndEditable -->
<!-- #EndTemplate --></html>
