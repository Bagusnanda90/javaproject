 
<% 
/* 
 * Page Name  		:  categorycriteria.jsp
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

	public String drawList(int iCommand,FrmCategoryCriteria frmObject, CategoryCriteria objEntity, Vector objectClass,  long categoryCriteriaId, int start)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("50%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Number","10%");
		ctrlist.addHeader("Criteria","90%");		
		
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 CategoryCriteria categoryCriteria = (CategoryCriteria)objectClass.get(i);
			 rowx = new Vector();
			 if(categoryCriteriaId == categoryCriteria.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
				rowx.add("<div align=\"center\">"+(start+(i+1))+"</div>");	
				rowx.add("Title : <br><textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_CRITERIA] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+categoryCriteria.getCriteria()+"</textarea>"+
                                            "<br>Desc : <br><textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_1] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+categoryCriteria.getDesc1()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_2] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+categoryCriteria.getDesc2()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_3] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+categoryCriteria.getDesc3()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_4] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+categoryCriteria.getDesc4()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_5] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+categoryCriteria.getDesc5()+"</textarea>");
			}else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(categoryCriteria.getOID())+"')\"><div align=\"center\">"+(start+(i+1))+"</div></a>");
				rowx.add(categoryCriteria.getCriteria()+
                                         ((categoryCriteria.getDesc1()!=null && categoryCriteria.getDesc1().trim().length()>2)? ("<br><br>"+categoryCriteria.getDesc1()):"")+
                                         ((categoryCriteria.getDesc2()!=null && categoryCriteria.getDesc2().trim().length()>2)? ("<br><br>"+categoryCriteria.getDesc2()):"")+
                                         ((categoryCriteria.getDesc3()!=null && categoryCriteria.getDesc3().trim().length()>2)? ("<br><br>"+categoryCriteria.getDesc3()):"")+
                                         ((categoryCriteria.getDesc4()!=null && categoryCriteria.getDesc4().trim().length()>2)? ("<br><br>"+categoryCriteria.getDesc4()):"")+
                                         ((categoryCriteria.getDesc5()!=null && categoryCriteria.getDesc5().trim().length()>2)? ("<br><br>"+categoryCriteria.getDesc5()):"")+"<br><br>"
                                         );				
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){
				rowx.add("<div align=\"center\">"+(objectClass.size()+1)+"</div>"); 
				rowx.add("Title : <textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_CRITERIA] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+objEntity.getCriteria()+"</textarea>"+
                                            "Desc : <textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_1] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+objEntity.getDesc1()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_2] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+objEntity.getDesc2()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_3] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+objEntity.getDesc3()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_4] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+objEntity.getDesc4()+"</textarea>"+
				"<textarea name=\""+frmObject.fieldNames[FrmCategoryCriteria.FRM_FIELD_DESC_5] +"\"  class=\"elemenForm\" cols=\"50\" rows=\"2\">"+objEntity.getDesc5()+"</textarea>");
				
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidCategoryCriteria = FRMQueryString.requestLong(request, "hidden_category_criteria_id");
long oidCategoryAppraisal = FRMQueryString.requestLong(request, "categ_appraisal_oid");
long oidGroupCategory = FRMQueryString.requestLong(request,"group_category_oid");
long groupRankId = FRMQueryString.requestLong(request, "group_rank_oid");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstCategoryCriteria.fieldNames[PstCategoryCriteria.FLD_CATEGORY_APPRAISAL_ID]+" = "+oidCategoryAppraisal;
String orderClause = "";

CtrlCategoryCriteria ctrlCategoryCriteria = new CtrlCategoryCriteria(request);
ControlLine ctrLine = new ControlLine();
Vector listCategoryCriteria = new Vector(1,1);

/*switch statement */
iErrCode = ctrlCategoryCriteria.action(iCommand , oidCategoryCriteria, oidCategoryAppraisal);
/* end switch*/
FrmCategoryCriteria frmCategoryCriteria = ctrlCategoryCriteria.getForm();

/*count list All CategoryCriteria*/
int vectSize = PstCategoryCriteria.getCount(whereClause);

/*switch list CategoryCriteria*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlCategoryCriteria.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

CategoryCriteria categoryCriteria = ctrlCategoryCriteria.getCategoryCriteria();
msgString =  ctrlCategoryCriteria.getMessage();

/* get record to display */
listCategoryCriteria = PstCategoryCriteria.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listCategoryCriteria.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listCategoryCriteria = PstCategoryCriteria.list(start,recordToGet, whereClause , orderClause);
}
//out.println("listCategoryCriteria >> "+listCategoryCriteria.size());
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Category Criteria</title>
<script language="JavaScript">
<!--


function cmdAdd(){
	document.frmcategorycriteria.hidden_category_criteria_id.value="0";
	document.frmcategorycriteria.command.value="<%=Command.ADD%>";
	document.frmcategorycriteria.prev_command.value="<%=prevCommand%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdAsk(oidCategoryCriteria){
	document.frmcategorycriteria.hidden_category_criteria_id.value=oidCategoryCriteria;
	document.frmcategorycriteria.command.value="<%=Command.ASK%>";
	document.frmcategorycriteria.prev_command.value="<%=prevCommand%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdConfirmDelete(oidCategoryCriteria){
	document.frmcategorycriteria.hidden_category_criteria_id.value=oidCategoryCriteria;
	document.frmcategorycriteria.command.value="<%=Command.DELETE%>";
	document.frmcategorycriteria.prev_command.value="<%=prevCommand%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdSave(){
	document.frmcategorycriteria.command.value="<%=Command.SAVE%>";
	document.frmcategorycriteria.prev_command.value="<%=prevCommand%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdEdit(oidCategoryCriteria){
	document.frmcategorycriteria.hidden_category_criteria_id.value=oidCategoryCriteria;
	document.frmcategorycriteria.command.value="<%=Command.EDIT%>";
	document.frmcategorycriteria.prev_command.value="<%=prevCommand%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdCancel(oidCategoryCriteria){
	document.frmcategorycriteria.hidden_category_criteria_id.value=oidCategoryCriteria;
	document.frmcategorycriteria.command.value="<%=Command.EDIT%>";
	document.frmcategorycriteria.prev_command.value="<%=prevCommand%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdBack(){
	document.frmcategorycriteria.command.value="<%=Command.BACK%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function prevPage(oid){
	document.frmcategorycriteria.group_rank_oid.value="<%=groupRankId%>";
	document.frmcategorycriteria.group_category_oid.value="<%=oidGroupCategory%>";
	document.frmcategorycriteria.categ_appraisal_oid.value="<%=oidCategoryAppraisal%>";
	//document.frmcategorycriteria.command.value="<%//=Command.BACK%>";
        document.frmcategorycriteria.command.value="<%=Command.LIST%>";
	document.frmcategorycriteria.action="categoryappraisal.jsp";
	document.frmcategorycriteria.submit();
        
        /*document.frmcategoryappraisal.group_rank_oid.value='<%//=groupRankId%>';
	document.frmcategoryappraisal.group_category_oid.value='<%//=oidGroupCategory%>';
	document.frmcategoryappraisal.command.value="<%//=Command.LIST%>";
	document.frmcategoryappraisal.action="groupcategory.jsp";
	document.frmcategoryappraisal.submit();	*/
}

function cmdListFirst(){
	document.frmcategorycriteria.command.value="<%=Command.FIRST%>";
	document.frmcategorycriteria.prev_command.value="<%=Command.FIRST%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdListPrev(){
	document.frmcategorycriteria.command.value="<%=Command.PREV%>";
	document.frmcategorycriteria.prev_command.value="<%=Command.PREV%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdListNext(){
	document.frmcategorycriteria.command.value="<%=Command.NEXT%>";
	document.frmcategorycriteria.prev_command.value="<%=Command.NEXT%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
}

function cmdListLast(){
	document.frmcategorycriteria.command.value="<%=Command.LAST%>";
	document.frmcategorycriteria.prev_command.value="<%=Command.LAST%>";
	document.frmcategorycriteria.action="categorycriteria.jsp";
	document.frmcategorycriteria.submit();
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
//-------------- script form image -------------------

function cmdDelPict(oidCategoryCriteria){
	document.frmimage.hidden_category_criteria_id.value=oidCategoryCriteria;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="categorycriteria.jsp";
	document.frmimage.submit();
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Master 
                  Date &gt; Performance Appraisal &gt; Category Criteria<!-- #EndEditable --> 
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
                                    <form name="frmcategorycriteria" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_category_criteria_id" value="<%=oidCategoryCriteria%>">
                                      <input type="hidden" name="categ_appraisal_oid" value="<%=oidCategoryAppraisal%>">
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
                                                            <td height="8" valign="middle" colspan="3">&nbsp;</td>
                                                          </tr> 
                                                          <% if(oidCategoryAppraisal != 0){
														 try{
														 	CategoryAppraisal categoryAppraisal = PstCategoryAppraisal.fetchExc(oidCategoryAppraisal);
															GroupCategory grpCateg = PstGroupCategory.fetchExc(categoryAppraisal.getGroupCategoryId());
															%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="8" valign="middle" colspan="3"> 
                                                              <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                                <tr> 
                                                                  <td width="15%" height="17" class="reportSubTitle">Group 
                                                                    Category </td>
                                                                  <td width="85%" height="17" class="reportSubTitle"><%=grpCateg.getGroupName()%> 
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="15%" height="14" class="reportSubTitle"> 
                                                                    Category </td>
                                                                  <td width="85%" height="14" class="reportSubTitle"><%=categoryAppraisal.getCategory()%></td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <%  }catch(Exception exc){
														}
													}%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="8" valign="middle" colspan="3">&nbsp; 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Category 
                                                              Criteria List </td>
                                                          </tr>
                                                          <%
							try{
							%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="22" valign="middle" colspan="3"> 
                                                              <% if(iCommand == Command.NONE)
													  			iCommand = Command.ADD;
																%>
                                                              <%= drawList(iCommand == Command.NONE?Command.ADD:iCommand,frmCategoryCriteria, categoryCriteria,listCategoryCriteria,oidCategoryCriteria, start)%> 
                                                            </td>
                                                          </tr>
                                                          <% 
						  }catch(Exception exc){ 
						  	System.out.println("snfgsdhfgsdfgsdfg"+exc);
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
                                                         

                                                   <% if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT && iCommand != Command.SAVE && iCommand != Command.DELETE)){%>
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td height="22" valign="middle" colspan="3"> 
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td>&nbsp;</td>
                                                          </tr>
                                                          <tr>
														  <% if(privAdd){%> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                            <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td valign="middle" ><a href="javascript:cmdAdd()" class="command">Add 
                                                              New Criteria</a> 
                                                            </td>
															<%}%>															
															<td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="24"><a href="javascript:prevPage('<%=oidCategoryAppraisal%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Category Appraisal"></a></td>
                                                            <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td valign="middle" colspan="3" ><a href="javascript:prevPage('<%=oidCategoryAppraisal%>')" class="command">Back To 
                                                              Category Appraisal</a> 
                                                            </td>															
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <%//}
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
                                                      <td colspan="3" class="command"> 
                                                        <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidCategoryCriteria+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidCategoryCriteria+"')";
									String scancel = "javascript:cmdEdit('"+oidCategoryCriteria+"')";
									ctrLine.setBackCaption("Back to List Category Criteria");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Yes Delete Criteria");
									ctrLine.setDeleteCaption("Delete Criteria");
									ctrLine.setAddCaption("Add New Criteria");
									ctrLine.setSaveCaption("Save Criteria");

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
                                                    <tr align="left" valign="top" > 
                                                      <td colspan="3" class="command">&nbsp;</td>
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
