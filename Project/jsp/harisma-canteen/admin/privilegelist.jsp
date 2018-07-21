 
<%
/*
 * privilegelist.jsp
 *
 * Created on April 04, 2002, 11:30 AM
 *
 * @author  ktanjana
 * @version 
 */ 
%>
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.form.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_PRIVILEGE); %>
<%@ include file = "../main/checkuser.jsp" %>
<%

/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- JSP Block -->
<%!

public String drawListAppPriv(Vector objectClass, long privId)
{
	String temp = "";
	String regdatestr = "";
	
	ControlList ctrlist = new ControlList();	
	ctrlist.setAreaWidth("98%");
	ctrlist.setListStyle("listarea");
	ctrlist.setTitleStyle("tableheader");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("tableheader");
	ctrlist.addHeader("Priv. Name","30%");
	ctrlist.addHeader("Description","50%");
	ctrlist.addHeader("Creation Date ","20%");		

	ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
	
	Vector lstData = ctrlist.getData();

	Vector lstLinkData 	= ctrlist.getLinkData();						
	
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	int index = -1;							
	for (int i = 0; i < objectClass.size(); i++) {
		 AppPriv appPriv = (AppPriv)objectClass.get(i);

		 Vector rowx = new Vector();
		 
		 if(privId == appPriv.getOID())
		 	index = i;
			
		 rowx.add(String.valueOf(appPriv.getPrivName()));		 
		 rowx.add(String.valueOf(appPriv.getDescr()));
		 try{
			 Date regdate = appPriv.getRegDate();
			 regdatestr = Formater.formatDate(regdate, "dd MMMM yyyy");
		 }catch(Exception e){
			 regdatestr = "";
		 }
		 
		 rowx.add(regdatestr);
		 		 
		 lstData.add(rowx);
		 lstLinkData.add(String.valueOf(appPriv.getOID()));
	}						

	return ctrlist.draw(index);
}

%>
<%

/* VARIABLE DECLARATION */
int recordToGet = 10;
String order = " " + PstAppPriv.fieldNames[PstAppPriv.FLD_PRIV_NAME];

Vector listAppPriv = new Vector(1,1);
ControlLine ctrLine = new ControlLine();

/* GET REQUEST FROM HIDDEN TEXT */
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start"); 
long appPrivOID = FRMQueryString.requestLong(request,"appriv_oid");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int prevSaveCommand = FRMQueryString.requestInt(request, "prev_save_command");

CtrlAppPriv ctrlAppPriv = new CtrlAppPriv(request);
FrmAppPriv frmAppPriv = ctrlAppPriv.getForm();
 
int vectSize = PstAppPriv.getCount(""); 

int excCode = ctrlAppPriv.action(iCommand,appPrivOID,start,vectSize,recordToGet);
vectSize = PstAppPriv.getCount(""); 
String msgString = ctrlAppPriv.getMessage(); 
AppPriv appPriv = ctrlAppPriv.getAppPriv();

if ((iCommand == Command.FIRST || iCommand == Command.PREV )||
	(iCommand == Command.NEXT || iCommand == Command.LAST))
		start = ctrlAppPriv.getStart();

if((iCommand == Command.SAVE)&&(frmAppPriv.errorSize()<1))
	start = PstAppPriv.findLimitStart(appPriv.getOID(),recordToGet,"", order);
		
order= PstAppPriv.fieldNames[PstAppPriv.FLD_PRIV_NAME] ;		
listAppPriv = PstAppPriv.list(start,recordToGet, "" , order);

/* TO HANDLE CONDITION AFTER DELETE LAST, IF START LIMIT IS BIGGER THAN VECT SIZE, GET LIST FIRST */
if(((listAppPriv==null)||(listAppPriv.size()<1))){		
	start=0;
	listAppPriv = PstAppPriv.list(start,recordToGet, "" , order);
}



%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - System Privilege List</title>
<script language="JavaScript">
function addNew(){
	document.frmAppPriv.appriv_oid.value="0";
	document.frmAppPriv.prev_command.value="<%=prevCommand%>";
	document.frmAppPriv.command.value="<%=Command.ADD%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}

function cmdEdit(oid){
	document.frmAppPriv.appriv_oid.value=oid;
	document.frmAppPriv.prev_command.value="<%=prevCommand%>";
	document.frmAppPriv.command.value="<%=Command.EDIT%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}


function cmdSave(){
	document.frmAppPriv.command.value="<%=Command.SAVE%>";
	document.frmAppPriv.prev_command.value="<%=prevCommand%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}

function cmdEditObj(oid){
	document.frmAppPriv.appriv_oid.value=oid;
	document.frmAppPriv.prev_command.value="<%=prevCommand%>";
	document.frmAppPriv.command.value="<%=Command.LIST%>";
	document.frmAppPriv.action="privilegeedit.jsp";
	document.frmAppPriv.submit();
}

function cmdAsk(oid){
	document.frmAppPriv.appriv_oid.value=oid;
	document.frmAppPriv.prev_command.value="<%=prevCommand%>";
	document.frmAppPriv.command.value="<%=Command.ASK%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}
function cmdDelete(oid){
	document.frmAppPriv.appriv_oid.value=oid;
	document.frmAppPriv.prev_command.value="<%=prevCommand%>";
	document.frmAppPriv.command.value="<%=Command.DELETE%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}

function cmdCancel(){
	document.frmAppPriv.prev_command.value="<%=prevCommand%>";
	document.frmAppPriv.command.value="<%=Command.EDIT%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}


function cmdListFirst(){
	document.frmAppPriv.command.value="<%=Command.FIRST%>";
	document.frmAppPriv.prev_command.value="<%=Command.FIRST%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}
function cmdListPrev(){
	document.frmAppPriv.command.value="<%=Command.PREV%>";
	document.frmAppPriv.prev_command.value="<%=Command.PREV%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}

function cmdListNext(){
	document.frmAppPriv.command.value="<%=Command.NEXT%>";
	document.frmAppPriv.prev_command.value="<%=Command.NEXT%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}
function cmdListLast(){
	document.frmAppPriv.command.value="<%=Command.LAST%>";
	document.frmAppPriv.prev_command.value="<%=Command.LAST%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
}

function cmdBack(){
	document.frmAppPriv.command.value="<%=Command.BACK%>";
	document.frmAppPriv.action="privilegelist.jsp";
	document.frmAppPriv.submit();
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg','<%=approot%>/images/BtnEditOn.jpg')">
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
			  <!-- #BeginEditable "contenttitle" -->User 
                  Management &gt; Privilege &gt; Privilege List <!-- #EndEditable --> 
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
                                    <link rel="stylesheet" href="../css/default.css" type="text/css">
                                    <form name="frmAppPriv" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="appriv_oid" value="<%=appPrivOID%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="prev_save_command" value="<%=prevSaveCommand%>">
                                      <table width="100%" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" class="bigtitleflash">&nbsp; 
                                          </td>
                                        </tr>
                                      </table>
                                      <% if ((listAppPriv!=null)&&(listAppPriv.size()>0)){ %>
                                      <%=drawListAppPriv(listAppPriv, appPrivOID)%> 
                                      <%}%>
                                      <table width="100%">
                                        <tr> 
                                          <td colspan="2"> <span class="command"> 
                                            <% 
					   int cmd = 0;					  
					   if ((iCommand == Command.FIRST || iCommand == Command.PREV )||
							(iCommand == Command.NEXT || iCommand == Command.LAST))
								cmd =iCommand;								   
					   else{					   
						  if(iCommand == Command.NONE || prevCommand == Command.NONE)						  
							cmd=Command.FIRST;							
						  else{
						  	if((prevSaveCommand==Command.ADD)&&(iCommand==Command.SAVE)&&(frmAppPriv.errorSize()<1)){
								cmd = Command.LAST;
							}						
							else{ 
								if((iCommand == Command.SAVE) && (frmAppPriv.errorSize()<1))
									cmd = PstAppPriv.findLimitCommand(start,recordToGet,vectSize); 
								else
									cmd = prevCommand;
							  }
							
						  }
					   }
					   
					        ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();						   					   
				    %>
                                            <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                            </span> </td>
                                        </tr>
                                        <%if(privAdd  && (iCommand!=Command.ADD)&&(iCommand!=Command.ASK)&&(iCommand!=Command.EDIT)&&(frmAppPriv.errorSize()<1)){%>
                                        <tr> 
                                          <td colspan="2" class="command"> 
                                            <table width="15%" border="0" cellspacing="2" cellpadding="3">
                                              <tr> 
                                                <td width="20%"><a href="javascript:addNew()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100111','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100111" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Privilege"></a></td>
                                                <td nowrap width="80%"><a href="javascript:addNew()" class="command">Add 
                                                  New Privilege</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td width="15%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
                                        </tr>
                                        <%if(((iCommand==Command.SAVE)&&(frmAppPriv.errorSize()>0))||(iCommand==Command.ADD)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                        <tr> 
                                          <td colspan="2" class="listtitle"> 
                                            <%= appPrivOID!=0 ? "Edit" : "Add"%>&nbsp;Privilege</td>
                                        </tr>
                                        <tr> 
                                          <td width="15%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="15%">Privilege Name</td>
                                          <td width="85%"> 
                                            <input type="text" name="<%=frmAppPriv.fieldNames[frmAppPriv.FRM_PRIV_NAME] %>" value="<%=appPriv.getPrivName()%>" class="formElemen" size="30">
                                            * &nbsp;<%= frmAppPriv.getErrorMsg(frmAppPriv.FRM_PRIV_NAME) %></td>
                                        </tr>
                                        <tr> 
                                          <td width="15%" valign="top">Description</td>
                                          <td width="85%"> 
                                            <textarea name="<%=frmAppPriv.fieldNames[frmAppPriv.FRM_DESCRIPTION] %>" cols="45" rows="3" class="formElemen"><%=appPriv.getDescr()%></textarea>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="15%" valign="top" height="14">Creation/Update 
                                            Date</td>
                                          <td width="85%" height="14"><%=ControlDate.drawDate(frmAppPriv.fieldNames[FrmAppPriv.FRM_REG_DATE], appPriv.getRegDate(), 0, -30)%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" class="command">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" class="command" height="22"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80%");
							String scomDel = "javascript:cmdAsk('"+appPrivOID+"')";
							String sconDelCom = "javascript:cmdDelete('"+appPrivOID+"')";
							String scancel = "javascript:cmdCancel('"+appPrivOID+"')";
							ctrLine.setBackCaption("Back to Privilege List");
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setSaveCaption("Save Privilege");
							ctrLine.setDeleteCaption("Delete Privilege");
							ctrLine.setConfirmDelCaption("Yes Delete Privilege");
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
                                            <%= ctrLine.drawImage(iCommand, excCode, msgString)%> 
                                          </td>
                                        </tr>
                                        <% if((privAdd && privUpdate) && (iCommand != Command.ASK || iCommand == Command.DELETE) && (appPrivOID != 0)){%>
                                        <tr> 
                                          <td colspan="2" class="command"> 
                                            <table width="15%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="20%"><a href="javascript:cmdEditObj('<%=appPrivOID%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10011','','<%=approot%>/images/BtnEditOn.jpg',1)"><img name="Image10011" border="0" src="<%=approot%>/images/BtnEdit.jpg" width="24" height="24" alt="Edit Module Access"></a></td>
                                                <td nowrap width="80%"><a href="javascript:cmdEditObj('<%=appPrivOID%>')" class="command">Edit 
                                                  Module Access</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td width="15%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td width="15%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
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
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
