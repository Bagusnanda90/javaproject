<%@ page language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.entity.*" %>
<%@ page import="com.dimata.system.entity.system.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.system.form.system.*" %>
<%@ page import="com.dimata.system.session.system.*" %>

<%@ include file = "../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_SYSTEM_PROPERTIES); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long lOid = FRMQueryString.requestLong(request, "oid");

CtrlSystemProperty ctrlSystemProperty = new CtrlSystemProperty(request);
ctrlSystemProperty.action(iCommand, lOid);

SystemProperty sysProp = ctrlSystemProperty.getSystemProperty();
FrmSystemProperty frmSystemProperty = ctrlSystemProperty.getForm();
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>System Property</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function hideObjectForEmployee()
{
} 
 
function hideObjectForLockers()
{ 
}

function hideObjectForCanteen()
{
}

function hideObjectForClinic()
{
}

function hideObjectForMasterdata()
{
}

function showObjectForMenu()
{
}
	
function cmdSave() 
{
  document.frmData.command.value="<%= Command.SAVE %>";          
  document.frmData.oid.value = "<%= lOid %>";
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdEdit() 
{
  document.frmData.command.value="<%= Command.EDIT %>";          
  document.frmData.oid.value = "<%= lOid %>";
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdAdd() 
{
  document.frmData.command.value="<%= Command.ADD %>";          
  document.frmData.oid.value = "0";
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdBack() 
{
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdAsk() 
{
  document.frmData.command.value="<%= Command.ASK %>";          
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdDelete() 
{
  document.frmData.command.value="<%= Command.DELETE %>";          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}	
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --><b>System 
                  &gt; Property</b><!-- #EndEditable --> </strong></font> </td>
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
                                    <script language=javascript>

</script>
                                    <link rel="stylesheet" href="../css/default.css" type="text/css">
                                    <form name="frmData" method="post" action="">
                                      <input type="hidden" name="command" value="0">
                                      <input type="hidden" name="oid" value="0">
                                      <table width="100%" border="0" cellspacing="6" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td width="13%">&nbsp;</td>
                                          <td width="87%" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td width="13%">Property Name</td>
                                          <td width="87%" valign="top"> 
                                            <input type="text" name="<%= FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_NAME] %>" size="30" maxlength="255" value="<%= sysProp.getName() %>" class="formElemen">
                                            * <%= frmSystemProperty.getErrorMsg(frmSystemProperty.FRM_NAME) %> 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td width="13%" height="2">Value Type</td>
                                          <td width="87%" height="2" valign="top"> 
                                            <%
											String selVal = sysProp.getValueType(); 
											if(selVal == null || selVal.equals("")) selVal = "-- No Value --";
											Vector vct = Validator.toVector(PstSystemProperty.valueTypes);            
											String cbxName = FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_VALTYPE];
											out.println(ControlCombo.draw(cbxName, null, selVal, vct));
											%>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td width="13%" height="2">Property 
                                            Group</td>
                                          <td width="87%" height="2" valign="top"> 
                                            <%
											selVal = sysProp.getGroup(); 
											if(selVal == null || selVal.equals("")) selVal = "-- No Value --";
											Vector grs = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "> ", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ", true);
											Vector val = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "", "", false);
											cbxName = FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_GROUP];
											out.println(ControlCombo.draw(cbxName, "formElemen", null, selVal, val, grs));									
											%>
                                            * <%= frmSystemProperty.getErrorMsg(frmSystemProperty.FRM_GROUP) %> 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td width="13%" height="29">Property 
                                            Value</td>
                                          <td width="87%" height="29" valign="top"> 
                                              <textarea name="<%= FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_VALUE] %>" cols="130" rows="3" class="formElemen"><%= sysProp.getValue() %></textarea>
                                            <!--<input type="text" name="<%//= FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_VALUE] %>" size="100" maxlength="255" value="<%//= sysProp.getValue() %>" class="formElemen">-->
                                            * <%= frmSystemProperty.getErrorMsg(frmSystemProperty.FRM_VALUE) %> 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td width="13%" height="81">Description</td>
                                          <td width="87%" height="81" valign="top"> 
                                            <textarea name="<%= FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_NOTE] %>" cols="30" rows="3" class="formElemen"><%= sysProp.getNote() %></textarea>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"> 
                                            <%
											ControlLine cln = new ControlLine();
											cln.setConfirmDelCommand("javascript:cmdDelete()");
											cln.setDeleteCaption("");

											if(privUpdate)
											{
												cln.setSaveCaption("Save");
											}
											else
											{
												cln.setSaveCaption("");
											}
																						
											out.println(cln.drawFrm(iCommand, ctrlSystemProperty.getMsgCode(), ctrlSystemProperty.getMessage()));
											%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2">N O T E : <br>
                                            - Use "\\" character when you want 
                                            to input "\" character in value field.<br>
                                            - Click "Load new value" link when 
                                            property it's updated. </td>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
