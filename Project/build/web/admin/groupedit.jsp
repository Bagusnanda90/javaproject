 
<%
/*
 * grouplist.jsp
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
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.form.admin.*" %>
<%@ page import = "com.dimata.harisma.session.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_GROUP); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//out.println("privAdd : "+privAdd+" - privUpdate : "+privUpdate+" - privDelete : "+privDelete);
%>
<!-- JSP Block -->
<%!
public String ctrCheckBox(long groupID)
{ 
	ControlCheckBox chkBx=new ControlCheckBox();
	chkBx.setCellSpace("0");		
	chkBx.setCellStyle("");
	chkBx.setTableWidth("80%");
	chkBx.setWidth(3);
	chkBx.setTableAlign("left");
	chkBx.setCellWidth("10%");
	
        try{
            Vector checkValues = new Vector(1,1);
            Vector checkCaptions = new Vector(1,1);	    
			String orderBy = PstAppPriv.fieldNames[PstAppPriv.FLD_PRIV_NAME];    
            Vector allPrivs = PstAppPriv.list(0, 0, "", orderBy);

            if(allPrivs!=null){
                int maxV = allPrivs.size(); 
                for(int i=0; i< maxV; i++){
                    AppPriv appPriv = (AppPriv) allPrivs.get(i);
                    checkValues.add(Long.toString(appPriv.getOID()));
                    checkCaptions.add(appPriv.getPrivName());
                }
            }

            Vector checkeds = new Vector(1,1);

            PstGroupPriv pstGp = new PstGroupPriv(0);
            Vector privs = SessAppGroup.getGroupPriv(groupID);

            if(privs!=null){
                int maxV = privs.size(); 
                for(int i=0; i< maxV; i++){
                    AppPriv appPriv = (AppPriv) privs.get(i);
                    checkeds.add(Long.toString(appPriv.getOID()));
                }
            }
            

            String fldName = FrmAppGroup.fieldNames[FrmAppGroup.FRM_GROUP_PRIV];
            return chkBx.draw(fldName,checkValues,checkCaptions,checkeds);

        } catch (Exception exc){
            return "No privilege";
        }
        
}

%>
<%

/* VARIABLE DECLARATION */ 

ControlLine ctrLine = new ControlLine();

/* GET REQUEST FROM HIDDEN TEXT */
int iCommand = FRMQueryString.requestCommand(request);

long appGroupOID = FRMQueryString.requestLong(request,"group_oid");
int start = FRMQueryString.requestInt(request, "start"); 

CtrlAppGroup ctrlAppGroup = new CtrlAppGroup(request);

FrmAppGroup frmAppGroup = ctrlAppGroup.getForm();
 
int iErrCode = ctrlAppGroup.action(iCommand,appGroupOID);
String msgString = ctrlAppGroup.getMessage();
AppGroup appGroup = ctrlAppGroup.getAppGroup();
%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - System Group Editor</title>
<script language="JavaScript">

function cmdCancel(){
	//document.frmAppGroup.group_oid.value=oid;
	document.frmAppGroup.command.value="<%=Command.EDIT%>";
	document.frmAppGroup.action="groupedit.jsp";
	document.frmAppGroup.submit();
}

<% if(privAdd || privUpdate) {%>
function cmdSave(){
	document.frmAppGroup.command.value="<%=Command.SAVE%>";
	document.frmAppGroup.action="groupedit.jsp";
	document.frmAppGroup.submit();
}

<%}%>

<% if(privDelete) {%>
function cmdAsk(oid){
	document.frmAppGroup.group_oid.value=oid;
	document.frmAppGroup.command.value="<%=Command.ASK%>";
	document.frmAppGroup.action="groupedit.jsp";
	document.frmAppGroup.submit();
}
function cmdDelete(oid){
	document.frmAppGroup.group_oid.value=oid;
	document.frmAppGroup.command.value="<%=Command.DELETE%>";
	document.frmAppGroup.action="groupedit.jsp";
	document.frmAppGroup.submit();
}
<%}%>
function cmdBack(oid){
	document.frmAppGroup.group_oid.value=oid;
	document.frmAppGroup.command.value="<%=Command.LIST%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
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
<script language="JavaScript">
<!--
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
</script>

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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->Group 
                  Management &gt; <%= appGroupOID!=0 ? "Edit" : "Add"%>&nbsp;Group 
                  <!-- #EndEditable --> 
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
                                    <link rel="stylesheet" href="../css/default.css" type="text/css">
                                    <form name="frmAppGroup" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="group_oid" value="<%=appGroupOID%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <table width="100%">
                                        <%if(((iCommand==Command.SAVE)&&(frmAppGroup.errorSize()>0))
                    ||(iCommand==Command.ADD)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                        <tr> 
                                          <td colspan="2" class="txtheading1"></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" height="20" class="bigtitleflash">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Group Name</td>
                                          <td width="87%"> 
                                            <input type="text" name="<%=frmAppGroup.fieldNames[frmAppGroup.FRM_GROUP_NAME] %>" value="<%=appGroup.getGroupName()%>" class="formElemen" size="30">
                                            * &nbsp;<%= frmAppGroup.getErrorMsg(frmAppGroup.FRM_GROUP_NAME) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Description</td>
                                          <td width="87%"> 
                                            <textarea name="<%=frmAppGroup.fieldNames[frmAppGroup.FRM_DESCRIPTION] %>" cols="40" rows="3" class="formElemen"><%=appGroup.getDescription()%></textarea>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top" height="14" nowrap>Privilege 
                                            Assigned</td>
                                          <td width="87%" height="14"> <%=ctrCheckBox(appGroupOID)%> 
                                          </td>
                                        <tr> 
                                          <td width="13%" valign="top" height="14" nowrap>Creation/Update 
                                            Date</td>
                                          <td width="87%" height="14"><%=ControlDate.drawDate(frmAppGroup.fieldNames[FrmAppGroup.FRM_REG_DATE], appGroup.getRegDate(),"formElemen",  0, -30)%> 
                                          </td>
                                        <tr> 
                                          <td width="13%" valign="top" height="14" nowrap>&nbsp;</td>
                                          <td width="87%" height="14">&nbsp;</td>
                                        <tr> 
                                          <td colspan="2" class="command"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80%");
							String scomDel = "javascript:cmdAsk('"+appGroupOID+"')";
							String sconDelCom = "javascript:cmdDelete('"+appGroupOID+"')";
							String scancel = "javascript:cmdCancel('"+appGroupOID+"')";
							ctrLine.setBackCaption("Back to Group List");
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setSaveCaption("Save Group");
							ctrLine.setDeleteCaption("Delete Group");
							ctrLine.setConfirmDelCaption("Yes Delete Group");
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
                                        <%} else {%>
                                        <tr> 
                                          <td width="13%">&nbsp; Processing OK 
                                            .. back to list. </td>
                                          <td width="87%">&nbsp; <a href="javascript:cmdBack()">click 
                                            here</a> 
                                            <script language="JavaScript">
						cmdBack();
					</script>
                                          </td>
                                        </tr>
                                        <% }
                    %>
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
