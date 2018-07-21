 
<% 
/* 
 * Page Name  		:  categoryappraisal.jsp
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

	public String drawList(Vector objectClass ,  long categoryAppraisalId, long groupCategoryId, long groupRankId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Category","40%");				
		ctrlist.addHeader("Description","45%");
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
			CategoryAppraisal categoryAppraisal = (CategoryAppraisal)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(categoryAppraisalId == categoryAppraisal.getOID())
				 index = i;
				 
			rowx.add(categoryAppraisal.getCategory());									
			rowx.add(categoryAppraisal.getDescription());
                        rowx.add("<a href=\"javascript:cmdListCriteria('"+groupRankId+"', '"+groupCategoryId+"', '"+String.valueOf(categoryAppraisal.getOID())+"')\">List Criteria</a>");
			
			
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(categoryAppraisal.getOID()));
		}

		return ctrlist.draw(index);
		
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidCategoryAppraisal = FRMQueryString.requestLong(request, "categ_appraisal_oid");
long oidGroupCategory = FRMQueryString.requestLong(request,"group_category_oid");
long groupRankId = FRMQueryString.requestLong(request, "group_rank_oid");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstCategoryAppraisal.fieldNames[PstCategoryAppraisal.FLD_GROUP_CATEGORY_ID]+" = "+oidGroupCategory;
String orderClause = "";

CtrlCategoryAppraisal ctrlCategoryAppraisal = new CtrlCategoryAppraisal(request);
ControlLine ctrLine = new ControlLine();
Vector listCategoryAppraisal = new Vector(1,1);

/*switch statement */
iErrCode = ctrlCategoryAppraisal.action(iCommand , oidCategoryAppraisal, oidGroupCategory);
/* end switch*/ 
FrmCategoryAppraisal frmCategoryAppraisal = ctrlCategoryAppraisal.getForm();

/*count list All CategoryAppraisal*/
int vectSize = PstCategoryAppraisal.getCount(whereClause);

CategoryAppraisal categoryAppraisal = ctrlCategoryAppraisal.getCategoryAppraisal();
msgString =  ctrlCategoryAppraisal.getMessage();

/*switch list CategoryAppraisal*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCategoryAppraisal == 0)){
	start = PstCategoryAppraisal.findLimitStart(categoryAppraisal.getOID(),recordToGet, whereClause, orderClause);
	oidCategoryAppraisal = categoryAppraisal.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlCategoryAppraisal.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listCategoryAppraisal = PstCategoryAppraisal.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listCategoryAppraisal.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listCategoryAppraisal = PstCategoryAppraisal.list(start,recordToGet, whereClause , orderClause);
}
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Category Appraisal</title>
<script language="JavaScript">

function cmdListCriteria(grpRankId, grpCategId, categAppId){
	document.frmcategoryappraisal.group_rank_oid.value=grpRankId;
	document.frmcategoryappraisal.group_category_oid.value=grpCategId;
	document.frmcategoryappraisal.categ_appraisal_oid.value=categAppId;
	document.frmcategoryappraisal.command.value="<%=Command.FIRST%>";
	document.frmcategoryappraisal.action="categorycriteria.jsp";
	document.frmcategoryappraisal.submit();	
}

function prevPage(oid){
	document.frmcategoryappraisal.group_rank_oid.value='<%=groupRankId%>';
	document.frmcategoryappraisal.group_category_oid.value='<%=oidGroupCategory%>';
	document.frmcategoryappraisal.command.value="<%=Command.LIST%>";
	document.frmcategoryappraisal.action="groupcategory.jsp";
	document.frmcategoryappraisal.submit();	
}

function cmdAdd(){
	document.frmcategoryappraisal.categ_appraisal_oid.value="0";
	document.frmcategoryappraisal.command.value="<%=Command.ADD%>";
	document.frmcategoryappraisal.prev_command.value="<%=prevCommand%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
}

function cmdAsk(oidCategoryAppraisal){
	document.frmcategoryappraisal.categ_appraisal_oid.value=oidCategoryAppraisal;
	document.frmcategoryappraisal.command.value="<%=Command.ASK%>";
	document.frmcategoryappraisal.prev_command.value="<%=prevCommand%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
}

function cmdConfirmDelete(oidCategoryAppraisal){
	document.frmcategoryappraisal.categ_appraisal_oid.value=oidCategoryAppraisal;
	document.frmcategoryappraisal.command.value="<%=Command.DELETE%>";
	document.frmcategoryappraisal.prev_command.value="<%=prevCommand%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
}
function cmdSave(){
	document.frmcategoryappraisal.command.value="<%=Command.SAVE%>";
	document.frmcategoryappraisal.prev_command.value="<%=prevCommand%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
	}

function cmdEdit(oidCategoryAppraisal){
	document.frmcategoryappraisal.categ_appraisal_oid.value=oidCategoryAppraisal;
	document.frmcategoryappraisal.command.value="<%=Command.EDIT%>";
	document.frmcategoryappraisal.prev_command.value="<%=prevCommand%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
	}

function cmdCancel(oidCategoryAppraisal){
	document.frmcategoryappraisal.categ_appraisal_oid.value=oidCategoryAppraisal;
	document.frmcategoryappraisal.command.value="<%=Command.EDIT%>";
	document.frmcategoryappraisal.prev_command.value="<%=prevCommand%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
}

function cmdBack(){
	document.frmcategoryappraisal.command.value="<%=Command.BACK%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
	}

function cmdListFirst(){
	document.frmcategoryappraisal.command.value="<%=Command.FIRST%>";
	document.frmcategoryappraisal.prev_command.value="<%=Command.FIRST%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
}

function cmdListPrev(){
	document.frmcategoryappraisal.command.value="<%=Command.PREV%>";
	document.frmcategoryappraisal.prev_command.value="<%=Command.PREV%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
	}

function cmdListNext(){
	document.frmcategoryappraisal.command.value="<%=Command.NEXT%>";
	document.frmcategoryappraisal.prev_command.value="<%=Command.NEXT%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
}

function cmdListLast(){
	document.frmcategoryappraisal.command.value="<%=Command.LAST%>";
	document.frmcategoryappraisal.prev_command.value="<%=Command.LAST%>";
	document.frmcategoryappraisal.action="categoryappraisal.jsp";
	document.frmcategoryappraisal.submit();
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Masterdata 
                  &gt; Performance Apprisial &gt; Category Appraisal<!-- #EndEditable --> 
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
                                    <form name="frmcategoryappraisal" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="categ_appraisal_oid" value="<%=categoryAppraisal.getOID()%>">
                                      <input type="hidden" name="group_category_oid" value="<%=oidGroupCategory%>">
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
                                                            <td valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                          </tr>
                                                          <% if(oidGroupCategory != 0){
														try{
														GroupCategory groupCateg = (GroupCategory)PstGroupCategory.fetchExc(oidGroupCategory);
														%>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="middle" colspan="3" class="listedittitle"> 
                                                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr> 
                                                                  <td width="21%">Group 
                                                                    Rank </td>
                                                                  <td width="79%"> 
                                                                    <% GroupRank grRank = PstGroupRank.fetchExc(groupCateg.getGroupRankId());%>
                                                                    <%=grRank.getGroupName()%></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="21%">Group 
                                                                    Category </td>
                                                                  <td width="79%"><%=groupCateg.getGroupName()%></td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <% }catch(Exception exc){
															
													   }
													 } %>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                          </tr>
                                                          <%
													try{
														if (listCategoryAppraisal.size()>0){
													%>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="middle" colspan="3" class="listtitle">&nbsp;Category 
                                                              Appraisal List </td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="middle" colspan="3"> 
                                                              <%= drawList(listCategoryAppraisal,iCommand==Command.SAVE?categoryAppraisal.getOID():oidCategoryAppraisal,oidGroupCategory,groupRankId)%> 
                                                            </td>
                                                          </tr>
                                                          <%  }else{%>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="middle" colspan="3">&nbsp;</td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="middle" colspan="3" class="comment">&nbsp;No 
                                                              Category Appraisal 
                                                              available </td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="middle" colspan="3">&nbsp;</td>
                                                          </tr>
                                                          <% }
													  }catch(Exception exc){													  	
													  }%>
                                                          <tr align="left" valign="top"> 
                                                            <td align="left" colspan="3" class="command"> 
                                                              <span class="command"> 
                                                              <% 
													   int cmd = 0;
														   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
															(iCommand == Command.NEXT || iCommand == Command.LAST))
																cmd =iCommand; 
													   else{
														  if(iCommand == Command.NONE && prevCommand == Command.NONE)
															cmd = Command.FIRST;
														  else{ 
															if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCategoryAppraisal == 0))
																cmd = PstCategoryAppraisal.findLimitCommand(start,recordToGet,vectSize); 
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
                                                          <% if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmCategoryAppraisal.errorSize()<1)){
														
														%>
                                                          <tr align="left" valign="top"> 
                                                            <td> 
                                                              <table cellpadding="0" cellspacing="0" border="0">
                                                                <tr> 
                                                                  <td>&nbsp;</td>
                                                                </tr>
                                                                <tr> 
																<%if(privAdd){%>
                                                                  <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                  <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add 
                                                                    New Category</a> 
                                                                  </td>
																  <%}%>
																  <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td width="24"><a href="javascript:prevPage('<%=groupRankId%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Group Rank"></a></td>
                                                                  <td width="3"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                  <td height="22" valign="middle" colspan="3" width="714"><a href="javascript:prevPage('<%=groupRankId%>')" class="command">Back 
                                                                    To Group Category</a> 
                                                                  </td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <%
													}%>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="middle" colspan="3"> 
                                                        <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmCategoryAppraisal.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td colspan="2" class="listtitle" height="20"><%=oidCategoryAppraisal==0?"Add":"Edit"%> 
                                                              Category Appraisal</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="100%" colspan="2" height="166"> 
                                                              <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                                <tr align="left" valign="top"> 
                                                                  <td valign="top" width="17%">&nbsp;</td>
                                                                  <td width="83%" class="comment">*) 
                                                                    entry required</td>
                                                                </tr>
                                                                <tr align="left" valign="top"> 
                                                                  <td valign="top" width="17%">Category</td>
                                                                  <td width="83%"> 
                                                                    <textarea name="<%=frmCategoryAppraisal.fieldNames[FrmCategoryAppraisal.FRM_FIELD_CATEGORY] %>" class="elemenForm" cols="40" rows="2"><%= categoryAppraisal.getCategory() %></textarea>
                                                                    * <%= frmCategoryAppraisal.getErrorMsg(FrmCategoryAppraisal.FRM_FIELD_CATEGORY) %> 
                                                                  </td>
                                                                </tr>
                                                                <tr align="left" valign="top"> 
                                                                  <td valign="top" width="17%">Description</td>
                                                                  <td width="83%"> 
                                                                    <textarea name="<%=frmCategoryAppraisal.fieldNames[FrmCategoryAppraisal.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="35" rows="3"><%= categoryAppraisal.getDescription() %></textarea>
                                                                  </td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top" > 
                                                            <td colspan="2" class="command" height="34"> 
                                                              <%
														ctrLine.setLocationImg(approot+"/images");
														ctrLine.initDefault();
														ctrLine.setTableWidth("80%"); 
														String scomDel = "javascript:cmdAsk('"+oidCategoryAppraisal+"')";
														String sconDelCom = "javascript:cmdConfirmDelete('"+oidCategoryAppraisal+"')";
														String scancel = "javascript:cmdEdit('"+oidCategoryAppraisal+"')";
														ctrLine.setBackCaption("Back to List Category");
														ctrLine.setCommandStyle("buttonlink");
														ctrLine.setConfirmDelCaption("Yes Delete Category");
														ctrLine.setDeleteCaption("Delete Category");
														ctrLine.setAddCaption("Add New Category");
														ctrLine.setSaveCaption("Save Category");
														
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
                                                    <tr align="left" valign="top"> 
                                                      <td valign="middle" colspan="3">&nbsp;</td>
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
