 
<% 
/* 
 * Page Name  		:  groupcategory.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_CATEGORY_APPRAISAL); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long groupCategoryId, long groupRankId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setCellSpacing("2");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");		
		ctrlist.addHeader("Group Category  ","65%");
		ctrlist.addHeader("Group Rank","20%");
		ctrlist.addHeader("Command","15%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			GroupCategory groupCategory = (GroupCategory)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(groupCategoryId == groupCategory.getOID())
				 index = i;			

				
			rowx.add(groupCategory.getGroupName());
			GroupRank groupRank = new GroupRank();
			try{
				groupRank = PstGroupRank.fetchExc(groupCategory.getGroupRankId());
			}catch(Exception exc){
				groupRank = new GroupRank();
			}	
			rowx.add(groupRank.getGroupName());
			rowx.add("<a href=\"javascript:cmdListCategory('"+String.valueOf(groupCategory.getOID())+"','"+String.valueOf(groupRankId)+"')\"><i>List Category</i></a>");

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(groupCategory.getOID()));
		}		

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidGroupCategory = FRMQueryString.requestLong(request, "group_category_oid");
long groupRankId = FRMQueryString.requestLong(request, "group_rank_oid");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause ="";
if(groupRankId!=0){
    whereClause = PstGroupCategory.fieldNames[PstGroupCategory.FLD_GROUP_RANK_ID]+ " = "+groupRankId;
}

String orderClause = "";

CtrlGroupCategory ctrlGroupCategory = new CtrlGroupCategory(request);
ControlLine ctrLine = new ControlLine();
Vector listGroupCategory = new Vector(1,1);

/*switch statement */
iErrCode = ctrlGroupCategory.action(iCommand , oidGroupCategory);
/* end switch*/
FrmGroupCategory frmGroupCategory = ctrlGroupCategory.getForm();

/*count list All GroupCategory*/
int vectSize = PstGroupCategory.getCount(whereClause);

GroupCategory groupCategory = ctrlGroupCategory.getGroupCategory();
msgString =  ctrlGroupCategory.getMessage();

/*switch list GroupCategory*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidGroupCategory == 0))
	start = PstGroupCategory.findLimitStart(groupCategory.getOID(),recordToGet, whereClause, orderClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlGroupCategory.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listGroupCategory = PstGroupCategory.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listGroupCategory.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listGroupCategory = PstGroupCategory.list(start,recordToGet, whereClause , orderClause);
}
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Group Category</title>
<script language="JavaScript">
<!--

function cmdListCategory(oidGroupCategory,oidGroupRankId){
	document.frmgroupcategory.group_category_oid.value=oidGroupCategory;
	document.frmgroupcategory.group_rank_oid.value=oidGroupRankId;
	document.frmgroupcategory.command.value="<%=Command.LIST%>";
	document.frmgroupcategory.action="categoryappraisal.jsp";
	document.frmgroupcategory.submit();
}

function prevPage(){
	document.frmgroupcategory.command.value="<%=Command.LIST%>";
	document.frmgroupcategory.action="grouprank.jsp";
	document.frmgroupcategory.submit();	
}

function cmdAdd(){
	document.frmgroupcategory.group_category_oid.value="0";
	document.frmgroupcategory.command.value="<%=Command.ADD%>";
	document.frmgroupcategory.prev_command.value="<%=prevCommand%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
}

function cmdAsk(oidGroupCategory){
	document.frmgroupcategory.group_category_oid.value=oidGroupCategory;
	document.frmgroupcategory.command.value="<%=Command.ASK%>";
	document.frmgroupcategory.prev_command.value="<%=prevCommand%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
}

function cmdListCriteria(oidGroupCategory){
	document.frmgroupcategory.group_category_oid.value=oidGroupCategory;
	document.frmgroupcategory.command.value="<%=Command.LIST%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
}

function cmdConfirmDelete(oidGroupCategory){
	document.frmgroupcategory.group_category_oid.value=oidGroupCategory;
	document.frmgroupcategory.command.value="<%=Command.DELETE%>";
	document.frmgroupcategory.prev_command.value="<%=prevCommand%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
}
function cmdSave(){
	document.frmgroupcategory.command.value="<%=Command.SAVE%>";
	document.frmgroupcategory.prev_command.value="<%=prevCommand%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
	}

function cmdEdit(oidGroupCategory){
	document.frmgroupcategory.group_category_oid.value=oidGroupCategory;
	document.frmgroupcategory.command.value="<%=Command.EDIT%>";
	document.frmgroupcategory.prev_command.value="<%=prevCommand%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
	}

function cmdCancel(oidGroupCategory){
	document.frmgroupcategory.group_category_oid.value=oidGroupCategory;
	document.frmgroupcategory.command.value="<%=Command.EDIT%>";
	document.frmgroupcategory.prev_command.value="<%=prevCommand%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
}

function cmdBack(){
	document.frmgroupcategory.group_category_oid.value=0;
	document.frmgroupcategory.command.value="<%=Command.BACK%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
	}

function cmdListFirst(){
	document.frmgroupcategory.command.value="<%=Command.FIRST%>";
	document.frmgroupcategory.prev_command.value="<%=Command.FIRST%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
}

function cmdListPrev(){
	document.frmgroupcategory.command.value="<%=Command.PREV%>";
	document.frmgroupcategory.prev_command.value="<%=Command.PREV%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
	}

function cmdListNext(){
	document.frmgroupcategory.command.value="<%=Command.NEXT%>";
	document.frmgroupcategory.prev_command.value="<%=Command.NEXT%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
}

function cmdListLast(){
	document.frmgroupcategory.command.value="<%=Command.LAST%>";
	document.frmgroupcategory.prev_command.value="<%=Command.LAST%>";
	document.frmgroupcategory.action="groupcategory.jsp";
	document.frmgroupcategory.submit();
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
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Masterdata &gt; Performance Appraisal &gt; Group Category<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmgroupcategory" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="group_category_oid" value="<%=groupCategory.getOID()%>">
                                      <input type="hidden" name="group_rank_oid" value="<%=groupRankId%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="99%"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                              <tr> 
                                                <td valign="top"> 
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                                    <tr align="left" valign="top"> 
                                                      <td height="8"  colspan="3"> 
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                          </tr>
                                                          <%
													try{
														if (listGroupCategory.size()>0){
														%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Group 
                                                              Category List </td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td height="22" valign="middle" colspan="3"> 
                                                              <%= drawList(listGroupCategory,iCommand==Command.SAVE?groupCategory.getOID():oidGroupCategory, groupRankId)%> 
                                                            </td>
                                                          </tr>
                                                          <%  } else {%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="comment">&nbsp;No 
                                                              Group Category available 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3">&nbsp; 
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
															  else{
															  		if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidGroupCategory == 0))
																		cmd = PstGroupCategory.findLimitCommand(start,recordToGet,vectSize); 
																	else 
																		cmd = prevCommand;
															  } 
														   } 
														%>
                                                              <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                              <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                              </span> </td>
                                                          </tr>
                                                          <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmGroupCategory.errorSize()<1)){
                                                    	if(privAdd){
														%>
                                                          <tr align="left" valign="top"> 
                                                            <td> 
                                                              <table cellpadding="0" cellspacing="0" border="0">
                                                                <tr> 
                                                                  <td width="2">&nbsp;</td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="2"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                  <td width="3"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add 
                                                                    New Group 
                                                                    Category</a> 
                                                                  </td>
																  <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td width="24"><a href="javascript:prevPage()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Group Rank"></a></td>
                                                                  <td width="3"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td height="22" valign="middle" colspan="3" width="714"><a href="javascript:prevPage()" class="command">Back 
                                                                    To Group Rank</a> 
                                                                  </td>
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
                                                        <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmGroupCategory.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td colspan="2" class="listtitle"><%=oidGroupCategory==0?"Add":"Edit"%> 
                                                              Group Category</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="100%" colspan="2" height="67"> 
                                                              <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                                <tr align="left" valign="top"> 
                                                                  <td valign="top" width="14%" height="27">&nbsp;</td>
                                                                  <td width="86%"  class="comment">*) 
                                                                    entry required</td>
                                                                </tr>
                                                                <tr align="left" valign="top"> 
                                                                  <td valign="top" width="14%" height="27">Group 
                                                                    Rank </td>
                                                                  <td width="86%" height="27"> 
                                                                    <%    Vector grouprank_value = new Vector(1,1);
																	Vector grouprank_key = new Vector(1,1);																	
																    Vector listGroup = PstGroupRank.listAll();
																	for(int i = 0;i<listGroup.size();i++){
																		GroupRank groupRank = (GroupRank)listGroup.get(i);
																		grouprank_value.add(""+groupRank.getOID());
																		grouprank_key.add(groupRank.getGroupName());
																	}
																   %>
                                                                    <%= ControlCombo.draw(frmGroupCategory.fieldNames[FrmGroupCategory.FRM_FIELD_GROUP_RANK_ID],null, oidGroupCategory==0?""+groupRankId:""+groupCategory.getGroupRankId(), grouprank_value, grouprank_key) %> 
                                                                    * <%= frmGroupCategory.getErrorMsg(FrmGroupCategory.FRM_FIELD_GROUP_RANK_ID) %> 
                                                                  </td>
                                                                </tr>
                                                                <tr align="left" valign="top"> 
                                                                  <td valign="top" width="14%">Group 
                                                                    Name</td>
                                                                  <td width="86%"> 
                                                                    <textarea name="<%=frmGroupCategory.fieldNames[FrmGroupCategory.FRM_FIELD_GROUP_NAME] %>" class="elemenForm" cols="50" rows="4"><%= groupCategory.getGroupName() %></textarea>
                                                                    * <%= frmGroupCategory.getErrorMsg(FrmGroupCategory.FRM_FIELD_GROUP_NAME) %> 
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
									ctrLine.setTableWidth("90%");
									String scomDel = "javascript:cmdAsk('"+oidGroupCategory+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidGroupCategory+"')";
									String scancel = "javascript:cmdEdit('"+oidGroupCategory+"')";
									ctrLine.setBackCaption("Back to List Group Category");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Yes Delete Group Category");
									ctrLine.setDeleteCaption("Delete Group Category");
									ctrLine.setAddCaption("Add New Group Category");
									ctrLine.setSaveCaption("Save Group Category");

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
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> 
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
