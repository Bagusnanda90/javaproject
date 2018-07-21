 
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
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_GROUP); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- JSP Block -->
<%!

public String drawListAppGroup(Vector objectClass)
{
	String temp = "";
	String regdatestr = "";
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("70%");
	ctrlist.setListStyle("listarea");
	ctrlist.setTitleStyle("tableheader");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("tableheader");
	ctrlist.addHeader("Group. Name","30%");
	ctrlist.addHeader("Description","40%");
	ctrlist.addHeader("Creation Date ","30%");		

	ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
	
	Vector lstData = ctrlist.getData();

	Vector lstLinkData 	= ctrlist.getLinkData();						
	
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
								
	for (int i = 0; i < objectClass.size(); i++) {
		 AppGroup appGroup = (AppGroup)objectClass.get(i);

		 Vector rowx = new Vector();
		 
		 rowx.add(String.valueOf(appGroup.getGroupName()));		 
		 rowx.add(String.valueOf(appGroup.getDescription()));
		 try{
			 Date regdate = appGroup.getRegDate();
			 regdatestr = Formater.formatDate(regdate, "dd MMMM yyyy");
		 }catch(Exception e){
			 regdatestr = "";
		 }
		 
		 rowx.add(regdatestr);
		 		 
		 lstData.add(rowx);
		 lstLinkData.add(String.valueOf(appGroup.getOID()));
	}						

	return ctrlist.draw();
}

%>
<%

/* VARIABLE DECLARATION */
int recordToGet = 10;

String order = " " + PstAppGroup.fieldNames[PstAppGroup.FLD_GROUP_NAME];

Vector listAppGroup = new Vector(1,1);
ControlLine ctrLine = new ControlLine();

/* GET REQUEST FROM HIDDEN TEXT */
int iCommand = FRMQueryString.requestCommand(request);
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int start = FRMQueryString.requestInt(request, "start"); 
long appGroupOID = FRMQueryString.requestLong(request,"group_oid");
int listCommand = FRMQueryString.requestInt(request, "list_command");
if(listCommand==Command.NONE)
 listCommand = Command.LIST;

CtrlAppGroup ctrlAppGroup = new CtrlAppGroup(request);
 
int vectSize = PstAppGroup.getCount(""); 

start = ctrlAppGroup.actionList(listCommand, start,vectSize,recordToGet);

listAppGroup = PstAppGroup.list(start,recordToGet, "" , order);

/* TO HANDLE CONDITION AFTER DELETE LAST, IF START LIMIT IS BIGGER THAN VECT SIZE, GET LIST FIRST */
if(((listAppGroup==null)||(listAppGroup.size()<1))){		
	start=0;
	listAppGroup = PstAppGroup.list(start,recordToGet, "" , order);
}
%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - System Group List</title>
<script language="JavaScript">
<% if (privAdd){%>
function addNew(){
	document.frmAppGroup.group_oid.value="0";
	document.frmAppGroup.list_command.value="<%=listCommand%>";
	document.frmAppGroup.command.value="<%=Command.ADD%>";
	document.frmAppGroup.action="groupedit.jsp";
	document.frmAppGroup.submit();
}
 <%}%>
function cmdEdit(oid){
	document.frmAppGroup.group_oid.value=oid;
	document.frmAppGroup.list_command.value="<%=listCommand%>";
	document.frmAppGroup.command.value="<%=Command.EDIT%>";
	document.frmAppGroup.action="groupedit.jsp";
	document.frmAppGroup.submit();
}

function first(){
	document.frmAppGroup.command.value="<%=Command.FIRST%>";
	document.frmAppGroup.list_command.value="<%=Command.FIRST%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
}
function prev(){
	document.frmAppGroup.command.value="<%=Command.PREV%>";
	document.frmAppGroup.list_command.value="<%=Command.PREV%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
}

function next(){
	document.frmAppGroup.command.value="<%=Command.NEXT%>";
	document.frmAppGroup.list_command.value="<%=Command.NEXT%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
}
function last(){
	document.frmAppGroup.command.value="<%=Command.LAST%>";
	document.frmAppGroup.list_command.value="<%=Command.LAST%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
}

function cmdListFirst()
{
	document.frmAppGroup.command.value="<%=Command.FIRST%>";
	document.frmAppGroup.prev_command.value="<%=Command.FIRST%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
}

function cmdListPrev()
{
	document.frmAppGroup.command.value="<%=Command.PREV%>";
	document.frmAppGroup.prev_command.value="<%=Command.PREV%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
}

function cmdListNext()
{
	document.frmAppGroup.command.value="<%=Command.NEXT%>";
	document.frmAppGroup.prev_command.value="<%=Command.NEXT%>";
	document.frmAppGroup.action="grouplist.jsp";
	document.frmAppGroup.submit();
}

function cmdListLast()
{
	document.frmAppGroup.command.value="<%=Command.LAST%>";
	document.frmAppGroup.prev_command.value="<%=Command.LAST%>";
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
	
	function showObjectForMenu(){
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->User 
                  Management &gt; Group List <!-- #EndEditable --> 
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
                                    <form name="frmAppGroup" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">									  
                                      <input type="hidden" name="group_oid" value="<%=appGroupOID%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="list_command" value="<%=listCommand%>">
                                      <table width="100%" cellspacing="0" cellpadding="0">
                                        <% if ((listAppGroup!=null)&&(listAppGroup.size()>0)){ %>
                                        <tr> 
                                          <td colspan="2" class="bigtitleflash">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"><%=drawListAppGroup(listAppGroup)%></td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td colspan="2" height="20">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2">
                                            <div class="comment">&nbsp;&nbsp;&nbsp;&nbsp;No 
                                              Group available</div>
                                          </td>
                                        </tr>
                                        <%}%>
                                      </table>
                                      <table width="100%">
                                        <tr> 
                                          <td colspan="2"> <span class="command"> 
                                            <% 
											int cmd = 0;					  
										    if ((iCommand == Command.FIRST || iCommand == Command.PREV )||
												(iCommand == Command.NEXT || iCommand == Command.LAST))
											{	
													cmd = iCommand;								   
											}		
										    else
											{					   
													cmd = prevCommand;
										    }
											   
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();						   					   
											%>
										    <%//=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%>
                                            <%=ctrLine.drawMeListLimit(listCommand,vectSize,start,recordToGet,"first","prev","next","last","left")%> 
                                            </span> </td>
                                        </tr>
                                        <% if (privAdd){%>
                                        <tr> 
                                          <td colspan="2" class="command"> 
                                            <table width="15%" border="0" cellspacing="2" cellpadding="3">
                                              <tr> 
                                                <td width="20%"><a href="javascript:addNew()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10011','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image10011" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Surcharge"></a></td>
                                                <td nowrap width="80%"><a href="javascript:addNew()" class="command">Add 
                                                  New Group</a></td>
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
