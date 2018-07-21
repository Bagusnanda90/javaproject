
<% 
/* 
 * Page Name  		:  recrinterviewer.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
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
<%@ page import = "com.dimata.harisma.entity.recruitment.*" %>
<%@ page import = "com.dimata.harisma.form.recruitment.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>


<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_RECRUITMENT, AppObjInfo.OBJ_INTERVIEWER); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%--
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
--%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long recrInterviewerId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("50%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Position","100%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			RecrInterviewer recrInterviewer = (RecrInterviewer)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(recrInterviewerId == recrInterviewer.getOID())
				 index = i;

			rowx.add(String.valueOf(recrInterviewer.getInterviewer()));

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(recrInterviewer.getOID()));
		}

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidRecrInterviewer = FRMQueryString.requestLong(request, "hidden_recr_interviewer_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlRecrInterviewer ctrlRecrInterviewer = new CtrlRecrInterviewer(request);
ControlLine ctrLine = new ControlLine();
Vector listRecrInterviewer = new Vector(1,1);

/*switch statement */
iErrCode = ctrlRecrInterviewer.action(iCommand , oidRecrInterviewer);
/* end switch*/
FrmRecrInterviewer frmRecrInterviewer = ctrlRecrInterviewer.getForm();

/*count list All RecrInterviewer*/
int vectSize = PstRecrInterviewer.getCount(whereClause);

RecrInterviewer recrInterviewer = ctrlRecrInterviewer.getRecrInterviewer();
msgString =  ctrlRecrInterviewer.getMessage();

/*switch list RecrInterviewer*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidRecrInterviewer == 0))
	start = PstRecrInterviewer.findLimitStart(recrInterviewer.getOID(),recordToGet, whereClause, orderClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlRecrInterviewer.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listRecrInterviewer = PstRecrInterviewer.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listRecrInterviewer.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listRecrInterviewer = PstRecrInterviewer.list(start,recordToGet, whereClause , orderClause);
}
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recruitment Master Data</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmrecrinterviewer.hidden_recr_interviewer_id.value="0";
	document.frmrecrinterviewer.command.value="<%=Command.ADD%>";
	document.frmrecrinterviewer.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
}

function cmdAsk(oidRecrInterviewer){
	document.frmrecrinterviewer.hidden_recr_interviewer_id.value=oidRecrInterviewer;
	document.frmrecrinterviewer.command.value="<%=Command.ASK%>";
	document.frmrecrinterviewer.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
}

function cmdConfirmDelete(oidRecrInterviewer){
	document.frmrecrinterviewer.hidden_recr_interviewer_id.value=oidRecrInterviewer;
	document.frmrecrinterviewer.command.value="<%=Command.DELETE%>";
	document.frmrecrinterviewer.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
}
function cmdSave(){
	document.frmrecrinterviewer.command.value="<%=Command.SAVE%>";
	document.frmrecrinterviewer.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
	}

function cmdEdit(oidRecrInterviewer){
	document.frmrecrinterviewer.hidden_recr_interviewer_id.value=oidRecrInterviewer;
	document.frmrecrinterviewer.command.value="<%=Command.EDIT%>";
	document.frmrecrinterviewer.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
	}

function cmdCancel(oidRecrInterviewer){
	document.frmrecrinterviewer.hidden_recr_interviewer_id.value=oidRecrInterviewer;
	document.frmrecrinterviewer.command.value="<%=Command.EDIT%>";
	document.frmrecrinterviewer.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
}

function cmdBack(){
	document.frmrecrinterviewer.command.value="<%=Command.BACK%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
	}

function cmdListFirst(){
	document.frmrecrinterviewer.command.value="<%=Command.FIRST%>";
	document.frmrecrinterviewer.prev_command.value="<%=Command.FIRST%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
}

function cmdListPrev(){
	document.frmrecrinterviewer.command.value="<%=Command.PREV%>";
	document.frmrecrinterviewer.prev_command.value="<%=Command.PREV%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
	}

function cmdListNext(){
	document.frmrecrinterviewer.command.value="<%=Command.NEXT%>";
	document.frmrecrinterviewer.prev_command.value="<%=Command.NEXT%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
}

function cmdListLast(){
	document.frmrecrinterviewer.command.value="<%=Command.LAST%>";
	document.frmrecrinterviewer.prev_command.value="<%=Command.LAST%>";
	document.frmrecrinterviewer.action="recrinterviewer.jsp";
	document.frmrecrinterviewer.submit();
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Recruitment &gt; Interviewer<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmrecrinterviewer" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_recr_interviewer_id" value="<%=oidRecrInterviewer%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">Interviewer 
                                                  List </td>
                                              </tr>
                                              <%
                                                try{
                                                        if (listRecrInterviewer.size()>0){
                                                %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listRecrInterviewer,oidRecrInterviewer)%> 
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
                                                          { 
                                                                if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidRecrInterviewer == 0))
                                                                    cmd = PstRecrInterviewer.findLimitCommand(start,recordToGet,vectSize);
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
                                              <%--
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add 
                                                  New</a></td>
                                              </tr>
                                              --%>
                                              <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand ==Command.BACK || iCommand ==Command.SAVE)&& (frmDepartment.errorSize()<1)){
                                               if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmRecrInterviewer.errorSize()<1)){
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
                                                        New Interviewer</a> 
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
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmRecrInterviewer.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="2%">&nbsp;</td>
                                                <td height="21" valign="middle" width="6%">&nbsp;</td>
                                                <td height="21" colspan="2" width="92%" class="comment">*)= 
                                                  required</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="2%">&nbsp;</td>
                                                <td height="21" valign="top" width="6%">Position</td>
                                                <td height="21" colspan="2" width="92%"> 
                                                  <input type="text" name="<%=frmRecrInterviewer.fieldNames[FrmRecrInterviewer.FRM_FIELD_INTERVIEWER] %>"  value="<%= recrInterviewer.getInterviewer() %>" class="formElemen">
                                                  * <%= frmRecrInterviewer.getErrorMsg(FrmRecrInterviewer.FRM_FIELD_INTERVIEWER) %> 
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="2%">&nbsp;</td>
                                                <td height="8" valign="middle" width="6%">&nbsp;</td>
                                                <td height="8" colspan="2" width="92%">&nbsp; 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="4" class="command"> 
                                                  <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80");
                                                    String scomDel = "javascript:cmdAsk('"+oidRecrInterviewer+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidRecrInterviewer+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidRecrInterviewer+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                            ctrLine.setDeleteCaption("Delete");
                                                            ctrLine.setSaveCaption("Save");
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
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
