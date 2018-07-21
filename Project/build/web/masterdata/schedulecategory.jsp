
<% 
/* 
 * Page Name  		:  schedulecategory.jsp
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_SCHEDULE, AppObjInfo.OBJ_SCHEDULE_CATEGORY); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long scheduleCategoryId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("70%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Type","20%");
		ctrlist.addHeader("Name","30%");
		ctrlist.addHeader("Description","50%");

		ctrlist.setLinkRow(1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			ScheduleCategory scheduleCategory = (ScheduleCategory)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(scheduleCategoryId == scheduleCategory.getOID())
				 index = i;

			rowx.add(PstScheduleCategory.fieldCategoryType[scheduleCategory.getCategoryType()]);
			rowx.add(scheduleCategory.getCategory());
			rowx.add(scheduleCategory.getDescription());

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(scheduleCategory.getOID()));
		}

		//return ctrlist.drawList(index);

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidScheduleCategory = FRMQueryString.requestLong(request, "hidden_schedule_category_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlScheduleCategory ctrlScheduleCategory = new CtrlScheduleCategory(request);
ControlLine ctrLine = new ControlLine();
Vector listScheduleCategory = new Vector(1,1);

/*switch statement */
iErrCode = ctrlScheduleCategory.action(iCommand , oidScheduleCategory);
/* end switch*/
FrmScheduleCategory frmScheduleCategory = ctrlScheduleCategory.getForm();

/*count list All ScheduleCategory*/
int vectSize = PstScheduleCategory.getCount(whereClause);

ScheduleCategory scheduleCategory = ctrlScheduleCategory.getScheduleCategory();
msgString =  ctrlScheduleCategory.getMessage();

/*switch list ScheduleCategory*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstScheduleCategory.findLimitStart(scheduleCategory.getOID(),recordToGet, whereClause);
	oidScheduleCategory = scheduleCategory.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlScheduleCategory.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listScheduleCategory = PstScheduleCategory.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listScheduleCategory.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listScheduleCategory = PstScheduleCategory.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Schedule Category</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmschedulecategory.hidden_schedule_category_id.value="0";
	document.frmschedulecategory.command.value="<%=Command.ADD%>";
	document.frmschedulecategory.prev_command.value="<%=prevCommand%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
}

function cmdAsk(oidScheduleCategory){
	document.frmschedulecategory.hidden_schedule_category_id.value=oidScheduleCategory;
	document.frmschedulecategory.command.value="<%=Command.ASK%>";
	document.frmschedulecategory.prev_command.value="<%=prevCommand%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
}

function cmdConfirmDelete(oidScheduleCategory){
	document.frmschedulecategory.hidden_schedule_category_id.value=oidScheduleCategory;
	document.frmschedulecategory.command.value="<%=Command.DELETE%>";
	document.frmschedulecategory.prev_command.value="<%=prevCommand%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
}
function cmdSave(){
	document.frmschedulecategory.command.value="<%=Command.SAVE%>";
	document.frmschedulecategory.prev_command.value="<%=prevCommand%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
	}

function cmdEdit(oidScheduleCategory){
	document.frmschedulecategory.hidden_schedule_category_id.value=oidScheduleCategory;
	document.frmschedulecategory.command.value="<%=Command.EDIT%>";
	document.frmschedulecategory.prev_command.value="<%=prevCommand%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
	}

function cmdCancel(oidScheduleCategory){
	document.frmschedulecategory.hidden_schedule_category_id.value=oidScheduleCategory;
	document.frmschedulecategory.command.value="<%=Command.EDIT%>";
	document.frmschedulecategory.prev_command.value="<%=prevCommand%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
}

function cmdBack(){
	document.frmschedulecategory.command.value="<%=Command.BACK%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
	}

function cmdListFirst(){
	document.frmschedulecategory.command.value="<%=Command.FIRST%>";
	document.frmschedulecategory.prev_command.value="<%=Command.FIRST%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
}

function cmdListPrev(){
	document.frmschedulecategory.command.value="<%=Command.PREV%>";
	document.frmschedulecategory.prev_command.value="<%=Command.PREV%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
	}

function cmdListNext(){
	document.frmschedulecategory.command.value="<%=Command.NEXT%>";
	document.frmschedulecategory.prev_command.value="<%=Command.NEXT%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
}

function cmdListLast(){
	document.frmschedulecategory.command.value="<%=Command.LAST%>";
	document.frmschedulecategory.prev_command.value="<%=Command.LAST%>";
	document.frmschedulecategory.action="schedulecategory.jsp";
	document.frmschedulecategory.submit();
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
                  Master Data &gt; Schedule &gt; Schedule Category<!-- #EndEditable --> 
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
                                    <form name="frmschedulecategory" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_schedule_category_id" value="<%=oidScheduleCategory%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Schedule 
                                                  Category List </td>
                                              </tr>
                                              <%
							try{
								if (listScheduleCategory.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listScheduleCategory,oidScheduleCategory)%> 
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
											 <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmScheduleCategory.errorSize()<1)){
											    if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmScheduleCategory.errorSize()<1)){
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
                                                        New Category</a> </td>
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
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmScheduleCategory.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="2" class="listtitle"><%=oidScheduleCategory==0?"Add":"Edit"%> Schedule 
                                                  Category</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%" colspan="2"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">&nbsp;</td>
                                                      <td width="83%" class="comment">*) 
                                                        entry required </td>
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                      <td valign="top" width="17%">Type</td>
                                                      <td width="83%">
													  <%
														Vector type_value = new Vector(1,1);
														Vector type_key = new Vector(1,1);                                                           
														for (int i = 0; i < PstScheduleCategory.fieldCategoryType.length; i++) {
															type_key.add(PstScheduleCategory.fieldCategoryType[i]);
															type_value.add(String.valueOf(i));
														}
                                                        %>
                                                        <%= ControlCombo.draw(FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_CATEGORY_TYPE],"formElemen",null, "" + scheduleCategory.getCategoryType(), type_value, type_key) %> 
													  </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Name</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_CATEGORY] %>"  value="<%= scheduleCategory.getCategory() %>" class="elemenForm" size="30">
                                                        * <%=frmScheduleCategory.getErrorMsg(FrmScheduleCategory.FRM_FIELD_CATEGORY)%> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Description</td>
                                                      <td width="83%"> 
                                                        <textarea name="<%=frmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="30" rows="3"><%= scheduleCategory.getDescription() %></textarea>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="2" class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidScheduleCategory+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidScheduleCategory+"')";
									String scancel = "javascript:cmdEdit('"+oidScheduleCategory+"')";
									ctrLine.setBackCaption("Back to List Category");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add Category");
									ctrLine.setSaveCaption("Save Category");
									ctrLine.setDeleteCaption("Delete Category");
									ctrLine.setConfirmDelCaption("Yes Delete Category");

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
                                                <td width="13%">&nbsp;</td>
                                                <td width="87%">&nbsp;</td>
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
