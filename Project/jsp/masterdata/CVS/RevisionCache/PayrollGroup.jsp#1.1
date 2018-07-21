<%-- 
    Document   : PayrollGroup
    Created on : 27-Jul-2015, 14:21:37
    Author     : GUSWIK
--%>

<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPayrollGroup"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPayrollGroup"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PayrollGroup"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPayrollGroup"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  PayrollGroup.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: priska
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmPayrollGroup frmObject, PayrollGroup objEntity, Vector objectClass,  long payrollGroupId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Payroll Group Name","30%");
		ctrlist.addHeader("Description","30%");
                
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

                
		for (int i = 0; i < objectClass.size(); i++) {
			 PayrollGroup payrollGroup = (PayrollGroup)objectClass.get(i);
			 rowx = new Vector();
			 if(payrollGroupId == payrollGroup.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){				
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayrollGroup.FRM_FIELD_PAYROLL_GROUP_NAME ] +"\" value=\""+payrollGroup.getPayrollGroupName()+"\" class=\"elemenForm\"> * ");
				rowx.add("<textarea name=\""+frmObject.fieldNames[FrmPayrollGroup.FRM_FIELD_DESCRIPTION] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+payrollGroup.getDescription()+"</textarea>");
                               
                         }else{
                                rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(payrollGroup.getOID())+"')\">"+payrollGroup.getPayrollGroupName()+"</a>");
				rowx.add(payrollGroup.getDescription());
                                
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayrollGroup.FRM_FIELD_PAYROLL_GROUP_NAME ] +"\" value=\""+objEntity.getPayrollGroupName()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<textarea name=\""+frmObject.fieldNames[FrmPayrollGroup.FRM_FIELD_DESCRIPTION] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+objEntity.getDescription()+"</textarea>");
                                rowx.add("-");
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidPayrollGroup = FRMQueryString.requestLong(request, "PayrollGroup_oid");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlPayrollGroup ctrlPayrollGroup = new CtrlPayrollGroup(request);
ControlLine ctrLine = new ControlLine();
Vector listPayrollGroup = new Vector(1,1);

iErrCode = ctrlPayrollGroup.action(iCommand , oidPayrollGroup);
/* end switch*/
FrmPayrollGroup frmPayrollGroup = ctrlPayrollGroup.getForm();

/*count list All GroupRank*/
int vectSize = PstPayrollGroup.getCount(whereClause);

/*switch list GroupRank*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPayrollGroup.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

PayrollGroup payrollGroup = ctrlPayrollGroup.getdPayrollGroup();
msgString =  ctrlPayrollGroup.getMessage();

/* get record to display */
listPayrollGroup = PstPayrollGroup.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listPayrollGroup.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listPayrollGroup = PstPayrollGroup.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Payroll Group</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmPayrollGroup.PayrollGroup_oid.value="0";
	document.frmPayrollGroup.command.value="<%=Command.ADD%>";
	document.frmPayrollGroup.prev_command.value="<%=prevCommand%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdAsk(oidPayrollGroup){
	document.frmPayrollGroup.PayrollGroup_oid.value=oidPayrollGroup;
	document.frmPayrollGroup.command.value="<%=Command.ASK%>";
	document.frmPayrollGroup.prev_command.value="<%=prevCommand%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdFlow(PayrollGroupId){
	document.frmPayrollGroup.command.value="<%=Command.REFRESH%>";
	document.frmPayrollGroup.prev_command.value="<%=prevCommand%>";
	document.frmPayrollGroup.action="PayrollGroupFlow.jsp?PayrollGroupId="+PayrollGroupId;
	document.frmPayrollGroup.submit();
    
	//window.open("<%=approot%>/masterdata/PayrollGroupFlow.jsp?PayrollGroupId="+PayrollGroupId+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdTemplate(PayrollGroupId){
	window.open("<%=approot%>/masterdata/PayrollGroup_template.jsp?PayrollGroupId="+PayrollGroupId+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdExpense(PayrollGroupId){
	window.open("<%=approot%>/masterdata/PayrollGroup_expense.jsp?PayrollGroupId="+PayrollGroupId+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdConfirmDelete(oidPayrollGroup){
	document.frmPayrollGroup.PayrollGroup_oid.value=oidPayrollGroup;
	document.frmPayrollGroup.command.value="<%=Command.DELETE%>";
	document.frmPayrollGroup.prev_command.value="<%=prevCommand%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdSave(){
	document.frmPayrollGroup.command.value="<%=Command.SAVE%>";
	document.frmPayrollGroup.prev_command.value="<%=prevCommand%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdEdit(oidPayrollGroup){
	document.frmPayrollGroup.PayrollGroup_oid.value=oidPayrollGroup;
	document.frmPayrollGroup.command.value="<%=Command.EDIT%>";
	document.frmPayrollGroup.prev_command.value="<%=prevCommand%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdCancel(oidPayrollGroup){
	document.frmPayrollGroup.PayrollGroup_oid.value=oidPayrollGroup;
	document.frmPayrollGroup.command.value="<%=Command.EDIT%>";
	document.frmPayrollGroup.prev_command.value="<%=prevCommand%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdBack(){
	document.frmPayrollGroup.command.value="<%=Command.BACK%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdListFirst(){
	document.frmPayrollGroup.command.value="<%=Command.FIRST%>";
	document.frmPayrollGroup.prev_command.value="<%=Command.FIRST%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdListPrev(){
	document.frmPayrollGroup.command.value="<%=Command.PREV%>";
	document.frmPayrollGroup.prev_command.value="<%=Command.PREV%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdListNext(){
	document.frmPayrollGroup.command.value="<%=Command.NEXT%>";
	document.frmPayrollGroup.prev_command.value="<%=Command.NEXT%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdListLast(){
	document.frmPayrollGroup.command.value="<%=Command.LAST%>";
	document.frmPayrollGroup.prev_command.value="<%=Command.LAST%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmPayrollGroup.submit();
}

function cmdListCateg(oidPayrollGroup){
	document.frmPayrollGroup.PayrollGroup_oid.value=oidPayrollGroup;
	document.frmPayrollGroup.command.value="<%=Command.LIST%>";
	document.frmPayrollGroup.action="PayrollGroup.jsp";
	document.frmdoctype.submit();
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
			  <!-- #BeginEditable "contenttitle" -->Masterdata 
                  &gt; PayrollGroup<!-- #EndEditable --> 
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
                                    <form name="frmPayrollGroup" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <input type="hidden" name="PayrollGroup_oid" value="<%=oidPayrollGroup%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td class="listtitle" width="37%">&nbsp;</td>
                                                      <td width="63%" class="comment">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listtitle" width="37%">Payroll Group List</td>
                                                      <td width="63%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%
												try{
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmPayrollGroup, payrollGroup, listPayrollGroup,oidPayrollGroup)%></td>
                                              </tr>
                                              <% 
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
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmPayrollGroup.errorSize()<1)){
											  	if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Payroll Group</a> </td>
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
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command" height="26"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidPayrollGroup+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidPayrollGroup+"')";
									String scancel = "javascript:cmdEdit('"+oidPayrollGroup+"')";
									ctrLine.setBackCaption("Back to List Payroll Group");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add Payroll Group");
									ctrLine.setSaveCaption("Save Payroll Group");
									ctrLine.setDeleteCaption("Delete Payroll Group");
									ctrLine.setConfirmDelCaption("Yes Delete Payroll Group");

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
									
									if((listPayrollGroup.size()<1)&&(iCommand == Command.NONE))
										 iCommand = Command.ADD;  
										 
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString);										 
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
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
