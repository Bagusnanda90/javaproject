 
<% 
/* 
 * Page Name  		:  expcoverage.jsp
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_EXPLANATION_COVERAGE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long explanationCoverageId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("95%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Group Rank","10%");
		ctrlist.addHeader("Descriptions","40%");
		ctrlist.addHeader("Definition Coverage","50%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			ExpCoverage expCoverage = (ExpCoverage)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(explanationCoverageId == expCoverage.getOID())
				 index = i;
				 
			GroupRank grRank = new GroupRank();
			try{
				grRank = PstGroupRank.fetchExc(expCoverage.getGroupRankId());
			}catch(Exception exc){
				grRank = new GroupRank();
			}

			rowx.add( (grRank.getGroupName()==null  || grRank.getGroupName().trim().length()<1? "-*-" :grRank.getGroupName()));

			rowx.add(expCoverage.getDescriptions());

			rowx.add(expCoverage.getDefCoverage());

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(expCoverage.getOID()));
		}

		//return ctrlist.drawList(index);

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidExpCoverage = FRMQueryString.requestLong(request, "hidden_explanation_coverage_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlExpCoverage ctrlExpCoverage = new CtrlExpCoverage(request);
ControlLine ctrLine = new ControlLine();
Vector listExpCoverage = new Vector(1,1);

/*switch statement */
iErrCode = ctrlExpCoverage.action(iCommand , oidExpCoverage);
/* end switch*/
FrmExpCoverage frmExpCoverage = ctrlExpCoverage.getForm();

/*count list All ExpCoverage*/
int vectSize = PstExpCoverage.getCount(whereClause);

ExpCoverage expCoverage = ctrlExpCoverage.getExpCoverage();
msgString =  ctrlExpCoverage.getMessage();

/*switch list ExpCoverage*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidExpCoverage ==0))
	start = PstExpCoverage.findLimitStart(expCoverage.getOID(),recordToGet, whereClause, orderClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlExpCoverage.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listExpCoverage = PstExpCoverage.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listExpCoverage.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listExpCoverage = PstExpCoverage.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Explanations and Coverage</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmexpcoverage.hidden_explanation_coverage_id.value="0";
	document.frmexpcoverage.command.value="<%=Command.ADD%>";
	document.frmexpcoverage.prev_command.value="<%=prevCommand%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
}

function cmdAsk(oidExpCoverage){
	document.frmexpcoverage.hidden_explanation_coverage_id.value=oidExpCoverage;
	document.frmexpcoverage.command.value="<%=Command.ASK%>";
	document.frmexpcoverage.prev_command.value="<%=prevCommand%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
}

function cmdConfirmDelete(oidExpCoverage){
	document.frmexpcoverage.hidden_explanation_coverage_id.value=oidExpCoverage;
	document.frmexpcoverage.command.value="<%=Command.DELETE%>";
	document.frmexpcoverage.prev_command.value="<%=prevCommand%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
}
function cmdSave(){
	document.frmexpcoverage.command.value="<%=Command.SAVE%>";
	document.frmexpcoverage.prev_command.value="<%=prevCommand%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
	}

function cmdEdit(oidExpCoverage){
	document.frmexpcoverage.hidden_explanation_coverage_id.value=oidExpCoverage;
	document.frmexpcoverage.command.value="<%=Command.EDIT%>";
	document.frmexpcoverage.prev_command.value="<%=prevCommand%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
	}

function cmdCancel(oidExpCoverage){
	document.frmexpcoverage.hidden_explanation_coverage_id.value=oidExpCoverage;
	document.frmexpcoverage.command.value="<%=Command.EDIT%>";
	document.frmexpcoverage.prev_command.value="<%=prevCommand%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
}

function cmdBack(){
	document.frmexpcoverage.command.value="<%=Command.BACK%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
	}

function cmdListFirst(){
	document.frmexpcoverage.command.value="<%=Command.FIRST%>";
	document.frmexpcoverage.prev_command.value="<%=Command.FIRST%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
}

function cmdListPrev(){
	document.frmexpcoverage.command.value="<%=Command.PREV%>";
	document.frmexpcoverage.prev_command.value="<%=Command.PREV%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
	}

function cmdListNext(){
	document.frmexpcoverage.command.value="<%=Command.NEXT%>";
	document.frmexpcoverage.prev_command.value="<%=Command.NEXT%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
}

function cmdListLast(){
	document.frmexpcoverage.command.value="<%=Command.LAST%>";
	document.frmexpcoverage.prev_command.value="<%=Command.LAST%>";
	document.frmexpcoverage.action="expcoverage.jsp";
	document.frmexpcoverage.submit();
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
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
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
			  <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Appraisal &gt; Explanations and Coverage. <!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table style="background-color:<%=bgColorContent%>;" width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmexpcoverage" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_explanation_coverage_id" value="<%=oidExpCoverage%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp; 
                                                </td>
                                              </tr>
                                              <%
							try{
								if (listExpCoverage.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Explanation 
                                                  and Coverage List </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listExpCoverage,iCommand==Command.SAVE?expCoverage.getOID():oidExpCoverage)%> 
                                                </td>
                                              </tr>
                                              <%  }else{%>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp; 
                                                  No Explanation and Coverage 
                                                  available </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">&nbsp; 
                                                </td>
                                              </tr>
                                              <%} 
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
									  	if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidExpCoverage == 0))
											cmd = PstExpCoverage.findLimitCommand(start,recordToGet,vectSize); 
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
											   <%if(iCommand == Command.NONE || (iCommand == Command.SAVE && frmExpCoverage.errorSize()<1) || iCommand == Command.DELETE || iCommand==Command.BACK ||
														iCommand == Command.FIRST || iCommand == Command.PREV ||iCommand == Command.NEXT || iCommand == Command.LAST){%>
                                              	<%if(privAdd){%>
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
                                                        New Coverage</a> </td>
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
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmExpCoverage.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="2" class="listtitle">Explanation 
                                                  and Coverage Editor</td>
                                              </tr>
                                              <tr> 
                                                <td width="100%" colspan="2"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Group 
                                                        Rank </td>
                                                      <td width="83%"> 
                                                        <%  Vector grouprank_value = new Vector(1,1);
															Vector grouprank_key = new Vector(1,1);
															Vector listGroup = PstGroupRank.listAll();
															for(int i=0;i<listGroup.size();i++){
																GroupRank groupRank = (GroupRank)listGroup.get(i);
																grouprank_value.add(""+groupRank.getOID());
																grouprank_key.add(groupRank.getGroupName());
															}														  
														   %>
                                                        <%= ControlCombo.draw(frmExpCoverage.fieldNames[FrmExpCoverage.FRM_FIELD_GROUP_RANK_ID],null, ""+expCoverage.getGroupRankId(), grouprank_value,grouprank_key) %> 
                                                        * <%= frmExpCoverage.getErrorMsg(FrmExpCoverage.FRM_FIELD_GROUP_RANK_ID) %> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Descriptions</td>
                                                      <td width="83%"> 
                                                        <textarea name="<%=frmExpCoverage.fieldNames[FrmExpCoverage.FRM_FIELD_DESCRIPTIONS] %>" class="elemenForm" cols="50" rows="3"><%= expCoverage.getDescriptions() %></textarea>
                                                        * <%= frmExpCoverage.getErrorMsg(FrmExpCoverage.FRM_FIELD_DESCRIPTIONS) %> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Definition 
                                                        Coverage</td>
                                                      <td width="83%"> 
                                                        <textarea name="<%=frmExpCoverage.fieldNames[FrmExpCoverage.FRM_FIELD_DEF_COVERAGE] %>" class="elemenForm" cols="60" rows="7"><%= expCoverage.getDefCoverage() %></textarea>
                                                        * <%= frmExpCoverage.getErrorMsg(FrmExpCoverage.FRM_FIELD_DEF_COVERAGE) %> 
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
									String scomDel = "javascript:cmdAsk('"+oidExpCoverage+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidExpCoverage+"')";
									String scancel = "javascript:cmdEdit('"+oidExpCoverage+"')";
									ctrLine.setBackCaption("Back to List Coverage");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Delete Coverage");
									ctrLine.setDeleteCaption("Delete Coverage");
									ctrLine.setAddCaption("Add New Coverage");
									ctrLine.setSaveCaption("Save Coverage");

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
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
