 
<%
/*
 * userlist.jsp
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
<%@ page import = "com.dimata.harisma.form.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_LIST); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- JSP Block -->
<%!

public String drawListAppUser(Vector objectClass)
{
	String temp = ""; 
	String regdatestr = "";
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("90%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("tableheader");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("tableheader");
	ctrlist.addHeader("Login ID","30%");
	ctrlist.addHeader("Full Name","40%");
	ctrlist.addHeader("Status ","30%");

	ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");

	Vector lstData = ctrlist.getData();

	Vector lstLinkData 	= ctrlist.getLinkData();

	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i = 0; i < objectClass.size(); i++) {
		 AppUser appUser = (AppUser)objectClass.get(i);

		 Vector rowx = new Vector();
		 
		 rowx.add(String.valueOf(appUser.getLoginId()));
		 rowx.add(String.valueOf(appUser.getFullName()));
		 rowx.add(String.valueOf(AppUser.getStatusTxt(appUser.getUserStatus())));		 
		 		 
		 lstData.add(rowx);
		 lstLinkData.add(String.valueOf(appUser.getOID()));
	}						

	return ctrlist.draw();
}

%>
<%

/* VARIABLE DECLARATION */
int recordToGet = 10;

String order = " " + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID];

Vector listAppUser = new Vector(1,1);
ControlLine ctrLine = new ControlLine();

/* GET REQUEST FROM HIDDEN TEXT */
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start"); 
long appUserOID = FRMQueryString.requestLong(request,"user_oid");
int listCommand = FRMQueryString.requestInt(request, "list_command");
if(listCommand==Command.NONE)
 listCommand = Command.LIST;

CtrlAppUser ctrlAppUser = new CtrlAppUser(request);
 
int vectSize = PstAppUser.getCount(""); 
start = ctrlAppUser.actionList(listCommand, start,vectSize,recordToGet);

listAppUser = PstAppUser.listPartObj(start,recordToGet, "" , order);

%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - System User List</title>
<script language="JavaScript">
<% if (privAdd){%>
function addNew(){
	document.frmAppUser.user_oid.value="0";
	document.frmAppUser.list_command.value="<%=listCommand%>";
	document.frmAppUser.command.value="<%=Command.ADD%>";
	document.frmAppUser.action="useredit.jsp";
	document.frmAppUser.submit();
}
<%}%>
 
function cmdEdit(oid){
	document.frmAppUser.user_oid.value=oid;
	document.frmAppUser.list_command.value="<%=listCommand%>";
	document.frmAppUser.command.value="<%=Command.EDIT%>";
	document.frmAppUser.action="useredit.jsp";
	document.frmAppUser.submit();
}

function cmdListFirst(){
	document.frmAppUser.command.value="<%=Command.FIRST%>";
	document.frmAppUser.list_command.value="<%=Command.FIRST%>";
	document.frmAppUser.action="userlist.jsp";
	document.frmAppUser.submit();
}
function cmdListPrev(){
	document.frmAppUser.command.value="<%=Command.PREV%>";
	document.frmAppUser.list_command.value="<%=Command.PREV%>";
	document.frmAppUser.action="userlist.jsp";
	document.frmAppUser.submit();
}

function cmdListNext(){
	document.frmAppUser.command.value="<%=Command.NEXT%>";
	document.frmAppUser.list_command.value="<%=Command.NEXT%>";
	document.frmAppUser.action="userlist.jsp";
	document.frmAppUser.submit();
}
function cmdListLast(){
	document.frmAppUser.command.value="<%=Command.LAST%>";
	document.frmAppUser.list_command.value="<%=Command.LAST%>";
	document.frmAppUser.action="userlist.jsp";
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
<link rel="stylesheet" href="../css/default.css" type="text/css">
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
                  Management >> User List<!-- #EndEditable --> 
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
                                    <form name="frmAppUser" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="user_oid" value="<%=appUserOID%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="list_command" value="<%=listCommand%>">
                                      <table width="100%" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" class="listtitle">&nbsp;</td>
                                        </tr>
                                      </table>
                                      <% if ((listAppUser!=null)&&(listAppUser.size()>0)){ %>
                                      <%=drawListAppUser(listAppUser)%> 
                                      <%}%>
                                      <table width="100%">
                                        <tr> 
                                          <td colspan="2"> <span class="command"> 
                                            <%//=ctrLine.drawMeListLimit(listCommand,vectSize,start,recordToGet,"first","prev","next","last","left")%>
											<%
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();
											out.println(ctrLine.drawImageListLimit(listCommand,vectSize,start,recordToGet));
											%>                                                  											 
                                            </span> </td>
                                        </tr>
                                        <% if (privAdd){%>
                                        <tr valign="middle"> 
                                          <td colspan="2" class="command"> 
                                            <table width="15%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="20%"><a href="javascript:addNew()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10011','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image10011" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Surcharge"></a></td>
                                                <td nowrap width="80%"><a href="javascript:addNew()" class="command">Add 
                                                  New User</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}%>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --><!-- #EndEditable -->
<!-- #EndTemplate --></html>
