
<% 
/* 
 * Page Name  		:  devimprovementplan.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidDevImprovementPlan = FRMQueryString.requestLong(request, "hidden_dev_improvement_plan_id");
long oidEmpAppraisal  = FRMQueryString.requestLong(request, "employee_appraisal_oid");
long oidEmployee  = FRMQueryString.requestLong(request, "employee_oid");
long grRankOID = 0;

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstDevImprovementPlan.fieldNames[PstDevImprovementPlan.FLD_EMPLOYEE_APPRAISAL]+ " = "+oidEmpAppraisal;
String orderClause = "";

CtrlDevImprovementPlan ctrlDevImprovementPlan = new CtrlDevImprovementPlan(request);
ControlLine ctrLine = new ControlLine();
Vector listDevImprovementPlan = new Vector(1,1);

/*switch statement */
iErrCode = ctrlDevImprovementPlan.action(iCommand , oidDevImprovementPlan, oidEmpAppraisal);
/* end switch*/
FrmDevImprovementPlan frmDevImprovementPlan = ctrlDevImprovementPlan.getForm();

/*count list All DevImprovementPlan*/
int vectSize = PstDevImprovementPlan.getCount(whereClause);

DevImprovementPlan devImprovementPlan = ctrlDevImprovementPlan.getDevImprovementPlan();
msgString =  ctrlDevImprovementPlan.getMessage();

/*switch list DevImprovementPlan*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
	start = PstDevImprovementPlan.findLimitStart(devImprovementPlan.getOID(),recordToGet, whereClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlDevImprovementPlan.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listDevImprovementPlan = PstDevImprovementPlan.listDevImprovementPlan(oidEmpAppraisal);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listDevImprovementPlan.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listDevImprovementPlan = PstDevImprovementPlan.listDevImprovementPlan(oidEmpAppraisal);
}
%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Dev &amp; Improvement Plan</title>
<script language="JavaScript">
<!--



function cmdAdd(){
	document.frmdevimprovementplan.hidden_dev_improvement_plan_id.value="0";
	document.frmdevimprovementplan.command.value="<%=Command.ADD%>";
	document.frmdevimprovementplan.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
}

function cmdAsk(oidDevImprovementPlan){
	document.frmdevimprovementplan.hidden_dev_improvement_plan_id.value=oidDevImprovementPlan;
	document.frmdevimprovementplan.command.value="<%=Command.ASK%>";
	document.frmdevimprovementplan.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
}

function cmdConfirmDelete(oidDevImprovementPlan){
	document.frmdevimprovementplan.hidden_dev_improvement_plan_id.value=oidDevImprovementPlan;
	document.frmdevimprovementplan.command.value="<%=Command.DELETE%>";
	document.frmdevimprovementplan.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
}
function cmdSave(){
	document.frmdevimprovementplan.command.value="<%=Command.SAVE%>";
	document.frmdevimprovementplan.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
	}

function cmdEdit(oidDevImprovementPlan){
	document.frmdevimprovementplan.hidden_dev_improvement_plan_id.value=oidDevImprovementPlan;
	document.frmdevimprovementplan.command.value="<%=Command.EDIT%>";
	document.frmdevimprovementplan.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
	}

function cmdCancel(oidDevImprovementPlan){
	document.frmdevimprovementplan.hidden_dev_improvement_plan_id.value=oidDevImprovementPlan;
	document.frmdevimprovementplan.command.value="<%=Command.EDIT%>";
	document.frmdevimprovementplan.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
}

function cmdBack(){
	document.frmdevimprovementplan.command.value="<%=Command.BACK%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
	}

function cmdListFirst(){
	document.frmdevimprovementplan.command.value="<%=Command.FIRST%>";
	document.frmdevimprovementplan.prev_command.value="<%=Command.FIRST%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
}

function cmdListPrev(){
	document.frmdevimprovementplan.command.value="<%=Command.PREV%>";
	document.frmdevimprovementplan.prev_command.value="<%=Command.PREV%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
	}

function cmdListNext(){
	document.frmdevimprovementplan.command.value="<%=Command.NEXT%>";
	document.frmdevimprovementplan.prev_command.value="<%=Command.NEXT%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
}

function cmdListLast(){
	document.frmdevimprovementplan.command.value="<%=Command.LAST%>";
	document.frmdevimprovementplan.prev_command.value="<%=Command.LAST%>";
	document.frmdevimprovementplan.action="devimprovementplan.jsp";
	document.frmdevimprovementplan.submit();
}

//-------------- script control line -------------------
//-->
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
<!--
function hideObjectForEmployee(){
	<%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovementPlan.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
	 	document.frmdevimprovementplan.<%=frmDevImprovementPlan.fieldNames[FrmDevImprovementPlan.FRM_FIELD_CATEGORY_APPRAISAL_ID]%>.style.visibility="hidden";
	 <%}%>    
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
	 <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovementPlan.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
	 	document.frmdevimprovementplan.<%=frmDevImprovementPlan.fieldNames[FrmDevImprovementPlan.FRM_FIELD_CATEGORY_APPRAISAL_ID]%>.style.visibility="hidden";
	 <%}%>
}

function showObjectForMenu(){
	 <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovementPlan.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
	 	document.frmdevimprovementplan.<%=frmDevImprovementPlan.fieldNames[FrmDevImprovementPlan.FRM_FIELD_CATEGORY_APPRAISAL_ID]%>.style.visibility="";
	 <%}%>

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
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr>  
  <tr> 
    <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" --> 
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
				  <td>
					<font color="#FF6600" face="Arial"><strong>
					  <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Appraisal &gt; Development and Improvement Plan  <!-- #EndEditable --> 
					</strong></font>
				  </td>
				</tr>
				<tr> 
				 <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                              <form name="frmdevimprovementplan" method ="post" action="">
                                <input type="hidden" name="command" value="<%=iCommand%>">
                                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                <input type="hidden" name="start" value="<%=start%>">
                                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                <input type="hidden" name="hidden_dev_improvement_plan_id" value="<%=oidDevImprovementPlan%>">
								<input type="hidden" name="employee_appraisal_oid" value="<%=oidEmpAppraisal%>">
								<input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <% if(oidEmployee != 0){%>
                                  <tr> 
                                    <td> 
                                      <table  border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td  valign="top" height="20" width="104"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                              <tr> 
                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap> 
                                                  <div align="center" class="tablink"><a href="empappraisal_edit.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&command=<%=Command.EDIT%>" class="tablink">Appraisal</a></div>
                                                </td>
                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20" width="158"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td   valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap > 
                                                  <div align="center" class="tablink"><a href="perfevaluation.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Performance 
                                                    Evaluation </a></div>
                                                </td>
                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20"  width="165"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="137"> 
                                                  <div align="center" class="tablink"><a href="devimprovement.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Improvement 
                                                    Appraisal</a></div>
                                                </td>
                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20" width="125"> 
                                            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap width="110"> 
                                                  <div align="center" class="tablink">Improvement 
                                                    Plan</div>
                                                </td>
                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td width="150"  valign="top" height="20">&nbsp;</td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <%}%>
                                  <tr> 
                                    <td class="tablecolor"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                              <tr align="left" valign="top"> 
                                                <td height="8"  colspan="3"> 
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                                    <tr align="left" valign="top"> 
                                                      <td height="8"  colspan="3"> 
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr align="left" valign="top">
                                                            <td height="8" valign="middle" colspan="3">&nbsp;</td>
                                                          </tr>
                                                          <% if(oidEmployee != 0){
														  	try{
																Employee employee = (Employee)PstEmployee.fetchExc(oidEmployee);
																Level level = PstLevel.fetchExc(employee.getLevelId());
															    grRankOID = level.getGroupRankId();
															%>
                                                          <tr align="left" valign="top">
                                                            <td height="8" valign="middle" colspan="3">
                                                              <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                                <tr>
                                                                  <td width="18%">Employee Name</td>
                                                                  <td width="82%">: 
                                                                    <%=employee.getFullName()%></td>
                                                                </tr>
                                                                <tr>
                                                                  <td width="18%">Employee Number</td>
                                                                  <td width="82%">: 
                                                                    <%=employee.getEmployeeNum()%></td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td height="8" valign="middle" colspan="3">&nbsp;</td>
                                                          </tr>                                                          
														  <%}catch(Exception  exc){
														  	}
														  }%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="8" valign="middle" colspan="3">&nbsp; 
                                                            </td>
                                                          </tr>                                                          
                                                          <%
														try{															
															if (listDevImprovementPlan.size()>0){
														%>
														<tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Development 
                                                              and Improvement 
                                                              Plan List </td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td height="57" valign="middle" colspan="3"> 
                                                              <table width="80%" border="0" cellspacing="2" cellpadding="1" class="listgen">
                                                                <tr> 
                                                                  <td width="6%" class="listgentitle" height="16">&nbsp;</td>
                                                                  <td height="16" class="listgentitle" width="13%">Recommend</td>
                                                                  <td height="16" class="listgentitle" width="81%">Development 
                                                                    and Improvement 
                                                                    Plan</td>
                                                                </tr>
                                                                <% 														  																
															try{
															int limit = start + recordToGet;
															if(limit > listDevImprovementPlan.size())
																limit = listDevImprovementPlan.size();
																
															for(int i = start;i<limit;i++){
																Vector vector = (Vector)listDevImprovementPlan.get(i);
																Vector numbers = (Vector)vector.get(0);
																CategoryAppraisal categApp = (CategoryAppraisal)vector.get(1);
																DevImprovementPlan devImprov = (DevImprovementPlan)vector.get(2);
																
																int num = Integer.parseInt(""+numbers.get(0));
																																																							
															if(num == 0){														
															%>
                                                                <tr> 
                                                                  <td colspan="3" class="listgensell">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b color="#0000FF"><%=categApp.getCategory()%></b></td>
                                                                </tr>
                                                                <%	}  
																System.out.println("devImprov.getRecommend()"+devImprov.getRecommend());%>
                                                                <tr> 
                                                                  <td width="6%" <%if((devImprov.getOID()==oidDevImprovementPlan)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>>&nbsp;&nbsp;&nbsp;<a href="javascript:cmdEdit('<%=devImprov.getOID()%>')"><%= num + 1%></a>&nbsp;&nbsp;&nbsp;</td>
                                                                  <td width="13%" <%if((devImprov.getOID()==oidDevImprovementPlan)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%if(devImprov.getRecommend()){%><%="Yes"%><%}else{%><%="No"%><%}%></td>
                                                                  <td width="81%" <%if((devImprov.getOID()==oidDevImprovementPlan)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=devImprov.getImprovPlan()%></td>
                                                                </tr>
                                                                <% 
																}
														  }catch(Exception exc){
															System.out.println("exc.............."+exc.toString());
														  }%>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <%  }else{%>														  
														  <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="comment">&nbsp; No Development 
                                                              and Improvement 
                                                              Plan available </td>
                                                          </tr>
														  <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                                          </tr>
														  <% }
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
                                                              <% ctrLine.setLocationImg(approot+"/images/ctr_line");
							   	ctrLine.initDefault();
								 %>
                                                              <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                              </span> </td>
                                                          </tr>
														   <%if(iCommand == Command.NONE || iCommand == Command.SAVE || iCommand == Command.DELETE || iCommand==Command.BACK ||
														iCommand == Command.FIRST || iCommand == Command.PREV ||iCommand == Command.NEXT || iCommand == Command.LAST){%>
                                                          	<%if(privAdd){%>
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
                                                                    New Improvement Plan</a> 
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
                                                        <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovementPlan.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                          <tr align="left" valign="top"> 
                                                            <td height="21" valign="middle" width="17%">&nbsp;</td>
                                                            <td height="21" colspan="2" width="83%" class="comment">*)= 
                                                              required</td>
                                                          </tr> 
                                                          <tr align="left" valign="top"> 
                                                            <td height="21" valign="top" width="17%">Category 
                                                              Appraisal </td>
                                                            <td height="21" colspan="2" width="83%"> 
                                                              <%    Vector categoryappraisalid_value = new Vector(1,1);
																	Vector categoryappraisalid_key = new Vector(1,1);
																	Vector listCategImprovement =  PstDevImprovementPlan.listCategImprovement(grRankOID);
																	for(int i=0;i<listCategImprovement.size();i++){
																		CategoryAppraisal categApp = (CategoryAppraisal)listCategImprovement.get(i);
																		categoryappraisalid_value.add(""+categApp.getOID());
																		categoryappraisalid_key.add(categApp.getCategory());
																	}																	
																   %>
																    <table>
							<tr>
                                                        <td>    <%= ControlCombo.draw(frmDevImprovementPlan.fieldNames[FrmDevImprovementPlan.FRM_FIELD_CATEGORY_APPRAISAL_ID],"formElemen",null, ""+devImprovementPlan.getCategoryAppraisalId(),  categoryappraisalid_value, categoryappraisalid_key) %> 
                                                              * <%= frmDevImprovementPlan.getErrorMsg(FrmDevImprovementPlan.FRM_FIELD_CATEGORY_APPRAISAL_ID) %> </td>
                                                        <td><p title="Please insert data Group Rank AND List Group Category to Master Data > Perpormance Apparaisal > Group Rank > List Group Category > List Category">
                                                                <img name="Image300" border="0" src="<%=approot%>/images/Info-icon.png" width="20" height="15" alt="Info"></p></td>
							</tr>
                                                    </table>
                                                            
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="17%">Recommend</td>
                                                            <td height="21" colspan="2" width="83%">
															  <%
															  String str ="";
															  if(devImprovementPlan.getRecommend())
																	str ="checked";
															  %>
                                                              <input type="checkbox" name="<%=frmDevImprovementPlan.fieldNames[FrmDevImprovementPlan.FRM_FIELD_RECOMMEND] %>" value="1" <%=str%>>
                                                              Yes 
                                                          
                                                          <tr align="left" valign="top"> 
                                                            <td height="21" valign="top" width="17%">Improvement 
                                                              Plan</td>
                                                            <td height="21" colspan="2" width="83%"> 
                                                              <textarea name="<%=frmDevImprovementPlan.fieldNames[FrmDevImprovementPlan.FRM_FIELD_IMPROV_PLAN] %>" class="elemenForm" cols="40" rows="3"><%= devImprovementPlan.getImprovPlan() %></textarea>
                                                              * <%= frmDevImprovementPlan.getErrorMsg(FrmDevImprovementPlan.FRM_FIELD_IMPROV_PLAN) %> 
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
									String scomDel = "javascript:cmdAsk('"+oidDevImprovementPlan+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidDevImprovementPlan+"')";
									String scancel = "javascript:cmdEdit('"+oidDevImprovementPlan+"')";
									ctrLine.setBackCaption("Back to List Improvement Plan");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setSaveCaption("Save Improvement Plan");
									ctrLine.setDeleteCaption("Delete Improvement Plan");
									ctrLine.setConfirmDelCaption(" Yes Delete Improvement Plan");

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
													<tr> 
														<td width="13%">&nbsp;</td>
														<td width="87%">&nbsp;</td>
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
                                    <td>&nbsp; </td>
                                  </tr>
                                </table>
                              </form>
                              <!-- #EndEditable --> 
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td >&nbsp;</td>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> 	  
      <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
