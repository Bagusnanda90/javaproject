 
<%
/*
 * useredit.jsp
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
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_LIST); %>
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
public String ctrCheckBox(long userID)
{ 
	ControlCheckBox chkBx=new ControlCheckBox();		
	chkBx.setCellSpace("0");		
	chkBx.setCellStyle("");
	chkBx.setWidth(4);
	chkBx.setTableAlign("left");
	chkBx.setCellWidth("10%");
	
        try{
            Vector checkValues = new Vector(1,1);
            Vector checkCaptions = new Vector(1,1);	    
			String orderBy = PstAppGroup.fieldNames[PstAppGroup.FLD_GROUP_NAME];    
            Vector allGroups = PstAppGroup.list(0, 0, "", orderBy);

            if(allGroups!=null){
                int maxV = allGroups.size(); 
                for(int i=0; i< maxV; i++){
                    AppGroup appGroup = (AppGroup) allGroups.get(i);
                    checkValues.add(Long.toString(appGroup.getOID()));
                    checkCaptions.add(appGroup.getGroupName());
                }
            }

            Vector checkeds = new Vector(1,1);
            PstUserGroup pstUg = new PstUserGroup(0);
            Vector groups = SessAppUser.getUserGroup(userID);

            if(groups!=null){
                int maxV = groups.size(); 
                for(int i=0; i< maxV; i++){
                    AppGroup appGroup = (AppGroup) groups.get(i);
                    checkeds.add(Long.toString(appGroup.getOID()));
                }
            }
 
            chkBx.setTableWidth("100%");

            String fldName = FrmAppUser.fieldNames[FrmAppUser.FRM_USER_GROUP];
            return chkBx.draw(fldName,checkValues,checkCaptions,checkeds);

        } catch (Exception exc){
            return "No group assigned";
        }
        
}

%>
<%

/* VARIABLE DECLARATION */ 

ControlLine ctrLine = new ControlLine();
 
/* GET REQUEST FROM HIDDEN TEXT */
int iCommand = FRMQueryString.requestCommand(request);

long appUserOID = FRMQueryString.requestLong(request,"user_oid");
int start = FRMQueryString.requestInt(request, "start"); 
AppUser appUser = new AppUser();
CtrlAppUser ctrlAppUser = new CtrlAppUser(request);
FrmAppUser frmAppUser = ctrlAppUser.getForm();

int excCode = FRMMessage.NONE;
String msgString =  "";

if(iCommand == Command.SAVE){
	frmAppUser.requestEntityObject(appUser);
	String pwd = FRMQueryString.requestString(request,frmAppUser.fieldNames[frmAppUser.FRM_PASSWORD]);
	String repwd  = FRMQueryString.requestString(request,frmAppUser.fieldNames[frmAppUser.FRM_CFRM_PASSWORD]);
	if(!pwd.equals(repwd)){
		excCode = FRMMessage.ERR_PWDSYNC;
		msgString = FRMMessage.getMessage(excCode);
	}
}

if(excCode == FRMMessage.NONE){
	excCode = ctrlAppUser.action(iCommand,appUserOID);
	msgString =  ctrlAppUser.getMessage();
	appUser = ctrlAppUser.getAppUser();

}

	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmAppUser.errorSize()<1)){
	%>
<jsp:forward page="userlist.jsp"> 
<jsp:param name="start" value="<%=start%>" />
<jsp:param name="user_oid" value="<%=appUser.getOID()%>" />
</jsp:forward>
<%
	}

%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - System User Editor</title>
<script language="JavaScript">
<!--



function cmdCancel(){
	//document.frmAppUser.user_oid.value=oid;
	document.frmAppUser.command.value="<%=Command.EDIT%>";
	document.frmAppUser.action="useredit.jsp";
	document.frmAppUser.submit();
}

<% if(privAdd || privUpdate) {%>
function cmdSave(){
	document.frmAppUser.command.value="<%=Command.SAVE%>";
	document.frmAppUser.action="useredit.jsp";
	document.frmAppUser.submit();
}
<%}%>

<% if(privDelete) {%>
function cmdDelete(oid){
	document.frmAppUser.user_oid.value=oid;
	document.frmAppUser.command.value="<%=Command.ASK%>";
	document.frmAppUser.action="useredit.jsp";
	document.frmAppUser.submit();
}

function cmdConfirmDelete(oid){
	document.frmAppUser.user_oid.value=oid;
	document.frmAppUser.command.value="<%=Command.DELETE%>";
	document.frmAppUser.action="useredit.jsp";
	document.frmAppUser.submit();
}
<%}%>


function cmdBack(oid){
	document.frmAppUser.user_oid.value=oid;
	document.frmAppUser.command.value="<%=Command.LIST%>";
	document.frmAppUser.action="userlist.jsp";
	document.frmAppUser.submit();
}


//-------------- script control line -------------------
//-->
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->System >> User 
                  Management >> <%= appUserOID!=0 ? "Edit"
                  : "Add"%> User <!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <link rel="stylesheet" href="../css/default.css" type="text/css">
                                    <form name="frmAppUser" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="user_oid" value="<%=appUserOID%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <table width="100%">
                                        <%if((excCode>-1) || ((iCommand==Command.SAVE)&&(frmAppUser.errorSize()>0))
                    ||(iCommand==Command.ADD)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                        <tr> 
                                          <td colspan="2" class="txtheading1"></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" height="26" class="bigtitleflash"> 
                                          </td>
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
                                            <input type="text" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_FULL_NAME] %>" value="<%=appUser.getFullName()%>" class="formElemen">
                                            * &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_FULL_NAME) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Email</td>
                                          <td width="87%"> 
                                            <input type="text" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_EMAIL] %>" value="<%=appUser.getEmail()%>" size="48" class="formElemen">
                                            &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_EMAIL) %></td>
                                        </tr>
                                        <tr>
                                          <td width="13%" valign="top">Employee </td>
                                          <td width="87%">
                                          <%
                                          Vector listEmployee = PstEmployee.list(0, 0, "RESIGNED = 0", "EMPLOYEE_NUM");
                                          Vector empKey = new Vector(1,1);
                                          Vector empValue = new Vector(1,1);
										  
                                          for(int i =0;i <listEmployee.size();i++){
                                                Employee employee = (Employee)listEmployee.get(i);
												Department dept =  new Department();
												try{
                                                	dept = PstDepartment.fetchExc(employee.getDepartmentId());
												}
												catch(Exception e){
													System.out.println("--- useredit.jsp >>>> ### :: "+employee.getFullName()+", "+e.toString());
												}
                                                empKey.add(employee.getEmployeeNum() + " - " + employee.getFullName() + " - (" + dept.getDepartment() + ")");
                                                empValue.add(""+employee.getOID());
                                          }
                                          %>
                                          <%=ControlCombo.draw(frmAppUser.fieldNames[frmAppUser.FRM_EMPLOYEE_ID],"select...",""+appUser.getEmployeeId(),empValue,empKey)%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Description</td>
                                          <td width="87%"> 
                                            <textarea name="<%=frmAppUser.fieldNames[frmAppUser.FRM_DESCRIPTION] %>" cols="48" rows="4" class="formElemen"><%=appUser.getDescription()%></textarea>
                                            &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_DESCRIPTION) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">User Status</td>
                                          <td width="87%"> 
                                            <%
                        ControlCombo cmbox = new ControlCombo();
                        Vector sts = AppUser.getStatusTxts();
                        Vector stsVals = AppUser.getStatusVals();
                    %>
                                            <%=cmbox.draw(frmAppUser.fieldNames[frmAppUser.FRM_USER_STATUS] ,"formElemen",
                        null, Integer.toString(appUser.getUserStatus()), stsVals, sts)%> 
                                            &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_USER_STATUS) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Last Update Date</td>
                                          <td width="87%">&nbsp;<%=appUser.getUpdateDate()%> 
                                            <input type="hidden" name="<%=frmAppUser.fieldNames[frmAppUser.FRM_UPDATE_DATE] %>2" value="<%=appUser.getUpdateDate()%>">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Registered Date</td>
                                          <td width="87%">&nbsp; <%=appUser.getRegDate()%></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Last Login Date</td>
                                          <td width="87%">&nbsp; 
                                            <% if(appUser.getLastLoginDate()==null)
                                                out.println("");
                                            else 
                                                out.println(appUser.getLastLoginDate());%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Last Login IP</td>
                                          <td width="87%">&nbsp; 
                                            <% if(appUser.getLastLoginIp()==null)
                                                out.println("");
                                            else 
                                                out.println(appUser.getLastLoginIp());%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top" height="14" nowrap>Privilege 
                                            Assigned</td>
                                          <td width="87%" height="14"> <%=ctrCheckBox(appUserOID)%> 
                                          </td>
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
											ctrLine.setBackCaption("Back to User List");
											ctrLine.setCommandStyle("buttonlink"); 
											ctrLine.setSaveCaption("Save User");
											ctrLine.setDeleteCaption("Delete User");
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
  <tr> 
    <td colspan="2" height="20"> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --><!-- #EndEditable -->
<!-- #EndTemplate --></html>
