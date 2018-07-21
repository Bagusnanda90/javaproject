
<% 
/* 
 * Page Name  		:  srctraininghistory.jsp
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>


<% int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_HISTORY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
privAdd = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
privPrint = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%--
<jsp:useBean id="srcTrainingHistory" class="com.dimata.harisma.entity.search.SrcTrainingHistory" scope="request" />
<jsp:setProperty name="srcTrainingHistory" property="*" />
--%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);

    SrcTrainingHistory srcTrainingHistory = new SrcTrainingHistory();
    FrmSrcTrainingHistory frmSrcTrainingHistory = new FrmSrcTrainingHistory();

    if(iCommand==Command.BACK)
    {
        frmSrcTrainingHistory = new FrmSrcTrainingHistory(request, srcTrainingHistory);
        try{
            srcTrainingHistory = (SrcTrainingHistory) session.getValue(SessTrainingHistory.SESS_SRC_TRAININGHISTORY);
            System.out.println("src_training_hist => srcTrainingHistory");
            if(srcTrainingHistory == null) {
                srcTrainingHistory = new SrcTrainingHistory();
                System.out.println("src_training_hist => new srcTrainingHistory karena null...");
            }
        }catch (Exception e){
            System.out.println("e....."+e.toString());
            srcTrainingHistory = new SrcTrainingHistory();
        }
    }

/*
if(iCommand == Command.NONE){
    srcTrainingHistory = new SrcTrainingHistory();
}
*/
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training</title>
<script language="JavaScript">

function cmdAdd(){
	document.frsearch.command.value="<%=Command.ADD%>";
	document.frsearch.action="training_hist_edit.jsp";
	document.frsearch.submit();
}

function cmdSearch(){
	document.frsearch.command.value="<%=Command.LIST%>";
	document.frsearch.action="training_hist_list.jsp";
	document.frsearch.submit();
}
function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
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
	document.frsearch.FRM_FIELD_PERIODTO_dy.style.visibility = "hidden";
	document.frsearch.FRM_FIELD_PERIODTO_mn.style.visibility = "hidden";
	document.frsearch.FRM_FIELD_PERIODTO_yr.style.visibility = "hidden";
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
	document.frsearch.FRM_FIELD_PERIODTO_dy.style.visibility = "";
	document.frsearch.FRM_FIELD_PERIODTO_mn.style.visibility = "";
	document.frsearch.FRM_FIELD_PERIODTO_yr.style.visibility = "";
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                  &gt; Training &gt; Search History Training<!-- #EndEditable --> 
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
                                    <form name="frsearch" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="1">
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="middle" width="3%">&nbsp;</td>
                                          <td height="21" valign="middle" width="13%">&nbsp;</td>
                                          <td height="21" width="84%" class="comment">&nbsp;</td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="13%" align="left">Employee 
                                            Name</td>
                                          <td height="21" width="84%"> 
                                            <input type="text" name="<%=frmSrcTrainingHistory.fieldNames[FrmSrcTrainingHistory.FRM_FIELD_NAME] %>"  value="<%= srcTrainingHistory.getEmployee() %>" class="elemenForm" size="50" onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="13%" align="left">Payroll 
                                            number</td>
                                          <td height="21" width="84%"> 
                                            <input type="text" name="<%=frmSrcTrainingHistory.fieldNames[FrmSrcTrainingHistory.FRM_FIELD_PAYROLL] %>"  value="<%= srcTrainingHistory.getPayrollNumber() %>" class="elemenForm" size="30" onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="13%" align="left">Program 
                                          </td>
                                          <td height="21" width="84%"> 
                                            <input type="text" name="<%=frmSrcTrainingHistory.fieldNames[FrmSrcTrainingHistory.FRM_FIELD_PROGRAM] %>"  value="<%= srcTrainingHistory.getProgram() %>" class="elemenForm" size="50" onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="13%" align="left">Period 
                                          </td>
                                          <td height="21" width="84%"> <%=ControlDate.drawDateWithStyle(frmSrcTrainingHistory.fieldNames[FrmSrcTrainingHistory.FRM_FIELD_PERIODFROM], srcTrainingHistory.getStartDate(), 1,-40,"formElemen","onkeydown=\"javascript:fnTrapKD()\"") %> 
                                            &nbsp;&nbsp;to&nbsp;&nbsp; <%=ControlDate.drawDateWithStyle(frmSrcTrainingHistory.fieldNames[FrmSrcTrainingHistory.FRM_FIELD_PERIODTO], srcTrainingHistory.getEndDate(), 1,-40,"formElemen","onkeydown=\"javascript:fnTrapKD()\"") %> 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="13%" align="left">Trainer 
                                          </td>
                                          <td height="21" width="84%"> 
                                            <input type="text" name="<%=frmSrcTrainingHistory.fieldNames[FrmSrcTrainingHistory.FRM_FIELD_TRAINER] %>"  value="<%= srcTrainingHistory.getTrainer() %>" class="elemenForm" size="30" onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="13%">&nbsp;</td>
                                          <td height="21" width="84%">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td width="3%" height="28">&nbsp;</td>
                                          <td height="28">&nbsp; </td>
                                          <td height="28">
                                            <table width="15%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="6%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="35%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                  for Training History</a></td>
                                                <% if(privAdd){%>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                <td width="6%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="30%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New Training History</a></td>
                                                <%}else{%>
                                                <td width="6%">&nbsp;</td>
                                                <%}%>
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
<script language="JavaScript">
	document.frsearch.<%=frmSrcTrainingHistory.fieldNames[FrmSrcTrainingHistory.FRM_FIELD_NAME] %>.focus();
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
