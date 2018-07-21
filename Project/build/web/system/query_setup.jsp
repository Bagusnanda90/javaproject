
<% 
/* 
 * Page Name  		:  position.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: gadnyana
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
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long divisionId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Report Title","10%");
		ctrlist.addHeader("Report Subtitle","10%");
		ctrlist.addHeader("Where","15%");
		ctrlist.addHeader("Order By","15%");
		ctrlist.addHeader("Group By","15%");
		ctrlist.addHeader("Query","25%");
		ctrlist.addHeader("Sub Query","25%");
		ctrlist.addHeader("Date","10%");
		ctrlist.addHeader("Description","25%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			Query query= (Query)objectClass.get(i);
			Vector rowx = new Vector();
			rowx.add(""+query.getReportTitle());
			rowx.add(""+query.getReportSubtitle());
			rowx.add(""+query.getWhereParam());
			rowx.add(""+query.getOrderByParam());
			rowx.add(""+query.getGroupByParam());
			rowx.add(""+query.getQuery());
			rowx.add(""+query.getSubQuery());
			rowx.add(""+query.getDate());
			rowx.add(""+query.getDescription());
			
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(query.getOID()));
		}
		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidQuery = FRMQueryString.requestLong(request, "hidden_query_id");
Date date = new Date();
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = " DATE ";

CtrlQuery ctrlQuery= new CtrlQuery(request);
ControlLine ctrLine = new ControlLine();
Vector listQuery = new Vector(1,1);

/*switch statement */
iErrCode = ctrlQuery.action(iCommand , oidQuery);
/* end switch*/
FrmQuery frmQuery= ctrlQuery.getForm();

/*count list All Position*/
int vectSize = PstQuery.getCount(whereClause);

Query query= ctrlQuery.getQuery();
msgString =  ctrlQuery.getMessage();
 
/*switch list Division*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstDivision.findLimitStart(division.getOID(),recordToGet, whereClause);
	oidQuery = query.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlQuery.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listQuery = PstQuery.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listQuery.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listQuery = PstQuery.list(start,recordToGet, whereClause , orderClause);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Division</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmquery.hidden_query_id.value="0";
	document.frmquery.command.value="<%=Command.ADD%>";
	document.frmquery.prev_command.value="<%=prevCommand%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
}

function cmdAsk(oidQuery){
	document.frmquery.hidden_query_id.value=oidQuery;
	document.frmquery.command.value="<%=Command.ASK%>";
	document.frmquery.prev_command.value="<%=prevCommand%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
}

function cmdConfirmDelete(oidQuery){
	document.frmquery.hidden_division_id.value=oidQuery;
	document.frmquery.command.value="<%=Command.DELETE%>";
	document.frmquery.prev_command.value="<%=prevCommand%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
}
function cmdSave(){
	document.frmquery.command.value="<%=Command.SAVE%>";
	document.frmquery.prev_command.value="<%=prevCommand%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
	}

function cmdEdit(oidQuery){
	document.frmquery.hidden_query_id.value=oidQuery;
	document.frmquery.command.value="<%=Command.EDIT%>";
	document.frmquery.prev_command.value="<%=prevCommand%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
	}

function cmdCancel(oidQuery){
	document.frmquery.hidden_query_id.value=oidQuery;
	document.frmquery.command.value="<%=Command.EDIT%>";
	document.frmquery.prev_command.value="<%=prevCommand%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
}

function cmdBack(){
	document.frmquery.command.value="<%=Command.BACK%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
	}

function cmdListFirst(){
	document.frmquery.command.value="<%=Command.FIRST%>";
	document.frmquery.prev_command.value="<%=Command.FIRST%>";
	document.frmquery.action="query_setup.jsp";
	document.frmquery.submit();
}

function cmdListPrev(){
	document.frmquery.command.value="<%=Command.PREV%>";
	document.frmquery.prev_command.value="<%=Command.PREV%>";
	document.frmquery.action="division.jsp";
	document.frmquery.submit();
	}

function cmdListNext(){
	document.frmquery.command.value="<%=Command.NEXT%>";
	document.frmquery.prev_command.value="<%=Command.NEXT%>";
	document.frmquery.action="division.jsp";
	document.frmquery.submit();
}

function cmdListLast(){
	document.frmquery.command.value="<%=Command.LAST%>";
	document.frmquery.prev_command.value="<%=Command.LAST%>";
	document.frmquery.action="division.jsp";
	document.frmquery.submit();
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
			  <!-- #BeginEditable "contenttitle" --> 
                  System &gt; Query Setup<!-- #EndEditable -->
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
                                    <form name="frmquery" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_query_id" value="<%=oidQuery%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Query
                                                  List </td>
                                              </tr>
                                              <%
							try{
								if (listQuery.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listQuery,oidQuery)%>
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
											<%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmDivision.errorSize()<1)){
											   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmQuery.errorSize()<1)){
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
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Query</a> </td>
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
											<td>&nbsp;
											</td>
										</tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmQuery.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidQuery == 0?"Add":"Edit"%><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">&nbsp;</td>
                                                      <td width="47%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Report Title</td>
                                                      <td width="47%"> 
                                                        <input type="text" name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_REPORT_TITLE] %>"  value="<%= query.getReportTitle()%>" class="elemenForm" size="30">
                                                        *<%=frmQuery.getErrorMsg(FrmQuery.FRM_FIELD_REPORT_TITLE)%>
                                                      </td>
                                                    </tr>
													<tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Report Sub Title</td>
                                                      <td width="47%"> 
                                                        <input type="text" name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_REPORT_SUBTITLE] %>"  value="<%= query.getReportSubtitle() %>" class="elemenForm" size="30">
                                                      </td>
                                                    </tr>
													<tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Where Parameter</td>
                                                      <td width="47%"> 
													  
                                                        <input type="text" name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_WHERE_PARAM] %>"  value="<%= query.getWhereParam() %>" class="elemenForm" size="30">
                                                      </td>
                                                    </tr>
													<tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Order By Parameter</td>
                                                      <td width="47%"> 
                                                        <input type="text" name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_ORDER_BY_PARAM] %>"  value="<%= query.getOrderByParam() %>" class="elemenForm" size="30">
                                                      </td>
                                                    </tr>
													<tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Group By Parameter</td>
                                                      <td width="47%"> 
                                                        <input type="text" name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_GROUP_BY_PARAM] %>"  value="<%= query.getGroupByParam() %>" class="elemenForm" size="30">
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Query </td>
                                                      <td width="47%"> 
                                                        <textarea name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_QUERY] %>" class="elemenForm" cols="30" rows="5"><%= query.getQuery() %></textarea>
                                                      </td>
                                                    </tr>
													 <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Sub Query </td>
                                                      <td width="47%"> 
                                                        <textarea name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_SUB_QUERY] %>" class="elemenForm" cols="30" rows="3"><%= query.getSubQuery() %></textarea>
                                                      </td>
                                                    </tr>
													<tr> 
												  <td width="47%" align="right" nowrap> 
													<div align="left">Date </div>
												  </td>
												  <td width="14%">: <%=ControlDate.drawDate(frmQuery.fieldNames[FrmQuery.FRM_FIELD_DATE],new Date(),"formElemen",0,installInterval)%></td>
												  <% //out.println("date "+date);
													//long periodId = PstPeriod.getPeriodIdBySelectedDate(date); 
												  %>
												</tr>
												<tr align="left" valign="top"> 
                                                      <td valign="top" width="39%"> 
                                                        Description </td>
                                                      <td width="47%"> 
                                                        <textarea name="<%=frmQuery.fieldNames[FrmQuery.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="30" rows="3"><%= query.getDescription() %></textarea>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidQuery+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidQuery+"')";
									String scancel = "javascript:cmdEdit('"+oidQuery+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List Query");
									ctrLine.setSaveCaption("Save Query");
									ctrLine.setConfirmDelCaption("Yes Delete Query");
									ctrLine.setDeleteCaption("Delete Query");

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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
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
