
<% 
/* 
 * Page Name  		:  traininghistory_list.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  lkarunia
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_HISTORY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
privAdd = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete =true;// userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
privPrint = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%--
<jsp:useBean id="srcTrainingHistory" class="com.dimata.harisma.entity.search.SrcTrainingHistory" scope="session" />
<jsp:setProperty name="srcTrainingHistory" property="*" />
--%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");		
		ctrlist.addHeader("Payroll Number","8%");
		ctrlist.addHeader("Employee Name","15%");
		ctrlist.addHeader("Department","8%");
		ctrlist.addHeader("Position","9%");		
		ctrlist.addHeader("Commencing Date","9%");
		ctrlist.addHeader("Training Program","15%");
		ctrlist.addHeader("Date","12%");		
		ctrlist.addHeader("Trainer","10%");
//		ctrlist.addHeader("Remark","14%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp =(Vector)objectClass.get(i);
			TrainingHistory trainingHistory = (TrainingHistory)temp.get(0);
			Employee employee = (Employee)temp.get(1);
			Department department = (Department)temp.get(2);
			Position position = (Position)temp.get(3);
			Vector rowx = new Vector();
			rowx.add(employee.getEmployeeNum());
			rowx.add(employee.getFullName());
			rowx.add(department.getDepartment());
			rowx.add(position.getPosition());
			rowx.add(Formater.formatDate(employee.getCommencingDate(),"dd MMM yyyy"));
			rowx.add(trainingHistory.getTrainingProgram());
			rowx.add(Formater.formatDate(trainingHistory.getStartDate(),"dd MMM")+" - "+
					 Formater.formatDate(trainingHistory.getEndDate(),"dd MMM yyyy"));
			rowx.add(trainingHistory.getTrainer());
//			rowx.add(trainingHistory.getRemark());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(trainingHistory.getOID()));
		}
		return ctrlist.draw();
	}
%>
<%
	ControlLine ctrLine = new ControlLine();
	CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
	long oidTrainingHistory = FRMQueryString.requestLong(request, "hidden_training_history_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String msgStr = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request, "start");
	int recordToGet = 10;
	int vectSize = 0;
	String whereClause = "";

	SrcTrainingHistory srcTrainingHistory = new SrcTrainingHistory();
	FrmSrcTrainingHistory frmSrcTrainingHistory = new FrmSrcTrainingHistory(request, srcTrainingHistory);
	frmSrcTrainingHistory.requestEntityObject(srcTrainingHistory);
        if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)){
             try{ 
                srcTrainingHistory = (SrcTrainingHistory) session.getValue(SessTrainingHistory.SESS_SRC_TRAININGHISTORY); 
             }catch(Exception e){ 
                srcTrainingHistory = new SrcTrainingHistory();
             }
        }
	SessTrainingHistory sessTrainingHistory = new SessTrainingHistory();
	session.putValue(SessTrainingHistory.SESS_SRC_TRAININGHISTORY, srcTrainingHistory);
	vectSize = SessTrainingHistory.getCountTrainingHistory(srcTrainingHistory);
	ctrlTrainingHistory.action(iCommand , oidTrainingHistory, request, "", 0);
	if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
		start = ctrlTrainingHistory.actionList(iCommand, start, vectSize, recordToGet);
	Vector records = SessTrainingHistory.searchTrainingHistory(srcTrainingHistory,start,recordToGet);
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training</title>
<script language="JavaScript">

	function cmdAdd(){
		document.frm_traininghistory.command.value="<%=Command.ADD%>";
		document.frm_traininghistory.action="training_hist_edit.jsp";
		document.frm_traininghistory.submit();
	}

	function cmdEdit(oid){
		document.frm_traininghistory.hidden_training_history_id.value=oid;
		document.frm_traininghistory.command.value="<%=Command.EDIT%>";
		document.frm_traininghistory.action="training_hist_edit.jsp";
		document.frm_traininghistory.submit();
	}

	function cmdListFirst(){
		document.frm_traininghistory.command.value="<%=Command.FIRST%>";
		document.frm_traininghistory.action="training_hist_list.jsp";
		document.frm_traininghistory.submit();
	}

	function cmdListPrev(){
		document.frm_traininghistory.command.value="<%=Command.PREV%>";
		document.frm_traininghistory.action="training_hist_list.jsp";
		document.frm_traininghistory.submit();
	}

	function cmdListNext(){
		document.frm_traininghistory.command.value="<%=Command.NEXT%>";
		document.frm_traininghistory.action="training_hist_list.jsp";
		document.frm_traininghistory.submit();
	}

	function cmdListLast(){
		document.frm_traininghistory.command.value="<%=Command.LAST%>";
		document.frm_traininghistory.action="training_hist_list.jsp";
		document.frm_traininghistory.submit();
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


	function cmdBack(){
		document.frm_traininghistory.command.value="<%=Command.BACK%>";
		document.frm_traininghistory.action="src_training_hist.jsp";
		document.frm_traininghistory.submit();
	}
	
	function cmdPrint(){
		window.open("training_hist_buffer.jsp","reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
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

function showObjectForMenu(){
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Training &gt; Result Of Training History Search <!-- #EndEditable --> 
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
                                    <form name="frm_traininghistory" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_training_history_id" value="<%=oidTrainingHistory%>">
                                      <table border="0" width="100%">
                                        <tr>
                                          <td height="8">&nbsp; </td>
                                        </tr>                                        
                                        <%if((records!=null)&&(records.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle">List Of Training History</td>
                                        </tr>
                                        <tr> 
                                          <td height="8" width="100%"><%=drawList(records)%></td>
                                        </tr>
                                         <%}
                                        else{
                                        %>
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle"><span class="comment"><br>
                                      &nbsp;No Training History available</span></td>
                                        </tr>
                                        <%}%>
                                      </table>                                  
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <table width="100%" cellspacing="0" cellpadding="3">
                                              <tr> 
                                                <td> 
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                         ctrLine.initDefault();
                                                  %>
                                                  <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="46%" nowrap align="left" class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Training History</a></td>
                                                <% if(privAdd) {%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Training History</a></b></td>
                                                <% } %>
                                                <% if(records != null && records.size()>0){
                                                    if(privPrint) {%> 
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrint()" class="command">Print Training History</a></td>                                         
                                              	<% } } %>
                                              </tr>
                                            </table>                                            
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
