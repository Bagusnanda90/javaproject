
<% 
/* 
 * Page Name  		:  lockerlocation.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
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
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOCKER, AppObjInfo.G2_LOCKER, AppObjInfo.OBJ_LOCKER); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long locationId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("40%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Location","20%");
		//ctrlist.addHeader("Sex","20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			LockerLocation lockerLocation = (LockerLocation)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(locationId == lockerLocation.getOID())
				 index = i;
			rowx.add(lockerLocation.getLocation());
			//rowx.add(lockerLocation.getSex());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(lockerLocation.getOID()));
		}

		//return ctrlist.drawList(index);

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidLockerLocation = FRMQueryString.requestLong(request, "hidden_location_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlLockerLocation ctrlLockerLocation = new CtrlLockerLocation(request);
ControlLine ctrLine = new ControlLine();
Vector listLockerLocation = new Vector(1,1);

/*switch statement */
iErrCode = ctrlLockerLocation.action(iCommand , oidLockerLocation);
/* end switch*/
FrmLockerLocation frmLockerLocation = ctrlLockerLocation.getForm();

/*count list All LockerLocation*/
int vectSize = PstLockerLocation.getCount(whereClause);

LockerLocation lockerLocation = ctrlLockerLocation.getLockerLocation();
msgString =  ctrlLockerLocation.getMessage();

/*switch list LockerLocation*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
	start = PstLockerLocation.findLimitStart(lockerLocation.getOID(),recordToGet, whereClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlLockerLocation.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listLockerLocation = PstLockerLocation.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listLockerLocation.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listLockerLocation = PstLockerLocation.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Locker LOcation</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmlockerlocation.hidden_location_id.value="0";
	document.frmlockerlocation.command.value="<%=Command.ADD%>";
	document.frmlockerlocation.prev_command.value="<%=prevCommand%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
}

function cmdAsk(oidLockerLocation){
	document.frmlockerlocation.hidden_location_id.value=oidLockerLocation;
	document.frmlockerlocation.command.value="<%=Command.ASK%>";
	document.frmlockerlocation.prev_command.value="<%=prevCommand%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
}

function cmdConfirmDelete(oidLockerLocation){
	document.frmlockerlocation.hidden_location_id.value=oidLockerLocation;
	document.frmlockerlocation.command.value="<%=Command.DELETE%>";
	document.frmlockerlocation.prev_command.value="<%=prevCommand%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
}
function cmdSave(){
	document.frmlockerlocation.command.value="<%=Command.SAVE%>";
	document.frmlockerlocation.prev_command.value="<%=prevCommand%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
	}

function cmdEdit(oidLockerLocation){
	document.frmlockerlocation.hidden_location_id.value=oidLockerLocation;
	document.frmlockerlocation.command.value="<%=Command.EDIT%>";
	document.frmlockerlocation.prev_command.value="<%=prevCommand%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
	}

function cmdCancel(oidLockerLocation){
	document.frmlockerlocation.hidden_location_id.value=oidLockerLocation;
	document.frmlockerlocation.command.value="<%=Command.EDIT%>";
	document.frmlockerlocation.prev_command.value="<%=prevCommand%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
}

function cmdBack(){
	document.frmlockerlocation.command.value="<%=Command.BACK%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
	}

function cmdListFirst(){
	document.frmlockerlocation.command.value="<%=Command.FIRST%>";
	document.frmlockerlocation.prev_command.value="<%=Command.FIRST%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
}

function cmdListPrev(){
	document.frmlockerlocation.command.value="<%=Command.PREV%>";
	document.frmlockerlocation.prev_command.value="<%=Command.PREV%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
	}

function cmdListNext(){
	document.frmlockerlocation.command.value="<%=Command.NEXT%>";
	document.frmlockerlocation.prev_command.value="<%=Command.NEXT%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
}

function cmdListLast(){
	document.frmlockerlocation.command.value="<%=Command.LAST%>";
	document.frmlockerlocation.prev_command.value="<%=Command.LAST%>";
	document.frmlockerlocation.action="lockerlocation.jsp";
	document.frmlockerlocation.submit();
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

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
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
			  <!-- #BeginEditable "contenttitle" --> 
                  Master Data &gt; Locker Location<!-- #EndEditable --> 
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
                        <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmlockerlocation" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_location_id" value="<%=oidLockerLocation%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Locker 
                                                  Location List </td>
                                              </tr>
                                              <%
							try{
								if (listLockerLocation.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listLockerLocation,oidLockerLocation)%> 
                                                </td>
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
                                                                cmd =prevCommand; 
                                                       } 
                                                    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                     %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                               <%
                                              if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmLockerLocation.errorSize()<1)){
                                              if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Location</a></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%}
                                              }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp; </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmLockerLocation.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="2"><b class="listtitle"><%=oidLockerLocation == 0 ?"Add":"Edit"%> Locker 
                                                  Location </b></td>
                                              </tr>
                                              <tr> 
                                                <td height="100%" colspan="2"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="19%">&nbsp;</td>
                                                      <td width="81%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="19%"> 
                                                        Location </td>
                                                      <td width="81%"> 
                                                        <input type="text" name="<%=frmLockerLocation.fieldNames[FrmLockerLocation.FRM_FIELD_LOCATION] %>"  value="<%= lockerLocation.getLocation() %>" class="elemenForm">
                                                        * <%=frmLockerLocation.getErrorMsg(FrmLockerLocation.FRM_FIELD_LOCATION)%></td>
                                                    </tr>
                                                    <%--
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="19%"> 
                                                        Sex</td>
                                                      <td width="81%"> 
                                                        <% Vector sex_value = new Vector(1,1);
						Vector sex_key = new Vector(1,1);
					 	String sel_sex = ""+lockerLocation.getSex();
					   sex_key.add("Male");
					   sex_value.add("Male");
					   sex_key.add("Female");
					   sex_value.add("Female");
					   %>
                                                        <%= ControlCombo.draw(frmLockerLocation.fieldNames[FrmLockerLocation.FRM_FIELD_SEX],null, sel_sex, sex_key, sex_value) %> 
                                                      </td>
                                                    </tr>
                                                    --%>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="2" class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidLockerLocation+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidLockerLocation+"')";
									String scancel = "javascript:cmdEdit('"+oidLockerLocation+"')";
									ctrLine.setBackCaption("Back to List Location");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add Location");
									ctrLine.setSaveCaption("Save Location");
									ctrLine.setDeleteCaption("Delete Location");
									ctrLine.setConfirmDelCaption("Delete Location");

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
									
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString); 
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="23%">&nbsp;</td>
                                                <td width="77%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="3">
                                                  <div align="left"></div>
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
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
