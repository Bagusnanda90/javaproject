
<% 
/* 
 * Page Name  		:  devimprovement.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  lkarunia 
 * @version  		:  01
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
 
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidDevImprovement = FRMQueryString.requestLong(request, "dev_improvement_oid");
long oidEmpAppraisal  = FRMQueryString.requestLong(request, "employee_appraisal_oid");
long oidEmployee  = FRMQueryString.requestLong(request, "employee_oid");
long grRankOID = 0;
/*
out.println("oidEmpAppraisal "+oidEmpAppraisal);
out.println("oidEmployee "+oidEmployee);*/
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstDevImprovement.fieldNames[PstDevImprovement.FLD_EMPLOYEE_APPRAISAL]+ " = "+oidEmpAppraisal;
String orderClause = "";

CtrlDevImprovement ctrlDevImprovement = new CtrlDevImprovement(request);
ControlLine ctrLine = new ControlLine();
Vector listDevImprovement = new Vector(1,1);

/*switch statement */
iErrCode = ctrlDevImprovement.action(iCommand , oidDevImprovement, oidEmpAppraisal);
/* end switch*/
FrmDevImprovement frmDevImprovement = ctrlDevImprovement.getForm();

/*count list All DevImprovement*/
int vectSize = PstDevImprovement.getCount(whereClause);

DevImprovement devImprovement = ctrlDevImprovement.getDevImprovement();
msgString =  ctrlDevImprovement.getMessage();

/*switch list DevImprovement*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
	start = PstDevImprovement.findLimitStart(devImprovement.getOID(),recordToGet, whereClause, orderClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlDevImprovement.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listDevImprovement = PstDevImprovement.listDevImprovement(oidEmpAppraisal);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listDevImprovement.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listDevImprovement = PstDevImprovement.listDevImprovement(oidEmpAppraisal);
}
%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Dev Improvement</title>
<script language="JavaScript">
<!--

//update by satrya 2012-12-20
function cmdAddToNewCategory(){
    var linkPage = "<%=approot%>/masterdata/groupcategory.jsp"; 
                //window.open(linkPage,"Absence Edit","height=600,width=800,status=yes,toolbar=yes,menubar=yes,location=yes");  			
                var newWin = window.open(linkPage,"category","height=600,width=800,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=no");  			
                newWin.focus();
    
}

function cmdAdd(){
	document.frmdevimprovement.dev_improvement_oid.value="0";
	document.frmdevimprovement.command.value="<%=Command.ADD%>";
	document.frmdevimprovement.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
}

function cmdAsk(oidDevImprovement){
	document.frmdevimprovement.dev_improvement_oid.value=oidDevImprovement;
	document.frmdevimprovement.command.value="<%=Command.ASK%>";
	document.frmdevimprovement.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
}

function cmdConfirmDelete(oidDevImprovement){
	document.frmdevimprovement.dev_improvement_oid.value=oidDevImprovement;
	document.frmdevimprovement.command.value="<%=Command.DELETE%>";
	document.frmdevimprovement.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
}
function cmdSave(){
	document.frmdevimprovement.command.value="<%=Command.SAVE%>";
	document.frmdevimprovement.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
	}

function cmdEdit(oidDevImprovement){
	document.frmdevimprovement.dev_improvement_oid.value=oidDevImprovement;
	document.frmdevimprovement.command.value="<%=Command.EDIT%>";
	document.frmdevimprovement.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
	}

function cmdCancel(oidDevImprovement){
	document.frmdevimprovement.dev_improvement_oid.value=oidDevImprovement;
	document.frmdevimprovement.command.value="<%=Command.EDIT%>";
	document.frmdevimprovement.prev_command.value="<%=prevCommand%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
}

function cmdBack(){
	document.frmdevimprovement.command.value="<%=Command.BACK%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
	}

function cmdListFirst(){
	document.frmdevimprovement.command.value="<%=Command.FIRST%>";
	document.frmdevimprovement.prev_command.value="<%=Command.FIRST%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
}

function cmdListPrev(){
	document.frmdevimprovement.command.value="<%=Command.PREV%>";
	document.frmdevimprovement.prev_command.value="<%=Command.PREV%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
	}

function cmdListNext(){
	document.frmdevimprovement.command.value="<%=Command.NEXT%>";
	document.frmdevimprovement.prev_command.value="<%=Command.NEXT%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
}

function cmdListLast(){
	document.frmdevimprovement.command.value="<%=Command.LAST%>";
	document.frmdevimprovement.prev_command.value="<%=Command.LAST%>";
	document.frmdevimprovement.action="devimprovement.jsp";
	document.frmdevimprovement.submit();
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
	 <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovement.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
	 		document.frmdevimprovement.<%=frmDevImprovement.fieldNames[FrmDevImprovement.FRM_FIELD_GROUP_CATEGORY_ID]%>.style.visibility="hidden";
	 <%}%>   
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
	 <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovement.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
	 		document.frmdevimprovement.<%=frmDevImprovement.fieldNames[FrmDevImprovement.FRM_FIELD_GROUP_CATEGORY_ID]%>.style.visibility="hidden";
	 <%}%>
}

function showObjectForMenu(){
	 <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovement.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
	 		document.frmdevimprovement.<%=frmDevImprovement.fieldNames[FrmDevImprovement.FRM_FIELD_GROUP_CATEGORY_ID]%>.style.visibility="";
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
                  &gt; Appraisal &gt; Development and Improvement <!-- #EndEditable --> 
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
                              <form name="frmdevimprovement" method ="post" action="">
                                <input type="hidden" name="command" value="<%=iCommand%>">                               
                                <input type="hidden" name="start" value="<%=start%>">
                                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                <input type="hidden" name="dev_improvement_oid" value="<%=oidDevImprovement%>">
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
                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap width="137"> 
                                                  <div align="center" class="tablink">Improvement 
                                                    Appraisal</div>
                                                </td>
                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20" width="125"> 
                                            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="110"> 
                                                  <div align="center" class="tablink"><a href="devimprovementplan.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Improvement 
                                                    Plan</a></div>
                                                </td>
                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
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
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr align="left" valign="top">
                                                      <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                                    </tr>
													<% if(oidEmployee != 0){
														try{
															Employee employee = (Employee)PstEmployee.fetchExc(oidEmployee);
															Level level = PstLevel.fetchExc(employee.getLevelId());
															grRankOID = level.getGroupRankId();
														%>
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="comment">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                          <tr> 
                                                            <td width="18%">Employee 
                                                              Name</td>
                                                            <td width="82%">: 
                                                              <%=employee.getFullName()%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="18%">Employee 
                                                              Number</td>
                                                            <td width="82%">: 
                                                              <%=employee.getEmployeeNum()%></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
													<%}catch(Exception exc){
													  }
													}%>
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                                    </tr>                                                    
                                                    <%
													try{
														if (listDevImprovement.size()>0){
													%>
													<tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;&nbsp;  
                                                        List Of Development and 
                                                        Improvement</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="22" valign="middle" colspan="3">
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td width="1%">&nbsp;</td>
                                                            <td width="99%">
														<table width="80%" border="0" cellspacing="2" cellpadding="1" class="listgen">
                                                          <tr> 
                                                            <td width="6%" class="listgentitle" height="16">&nbsp;</td>
                                                            <td height="16" class="listgentitle" width="94%">Development 
                                                              and Improvement</td>
                                                          </tr>
                                                          <% 														  																
															try{
															int limit = start + recordToGet;
															if(limit > listDevImprovement.size())
																limit = listDevImprovement.size();
																
															for(int i = start;i<limit;i++){
																Vector vector = (Vector)listDevImprovement.get(i);
																Vector numbers = (Vector)vector.get(0);
																GroupCategory groupCategory = (GroupCategory)vector.get(1);
																DevImprovement devImprov = (DevImprovement)vector.get(2);
																
																int num = Integer.parseInt(""+numbers.get(0));
																																																							
															if(num == 0){														
															%>
                                                          <tr> 
                                                            <td colspan="2" class="listgensell"><b color="#0000FF"><%=groupCategory.getGroupName()%></b></td>
                                                          </tr>
                                                          <%	}  %>
                                                          <tr> 
                                                            <td width="6%" <%if((devImprov.getOID()==oidDevImprovement)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>>&nbsp;&nbsp;&nbsp;<a href="javascript:cmdEdit('<%=devImprov.getOID()%>')"><%= num + 1%></a>&nbsp;&nbsp;&nbsp;</td>
                                                            <td width="94%" <%if((devImprov.getOID()==oidDevImprovement)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=devImprov.getImprovement()%></td>
                                                          </tr>
                                                          <% 
																}
														  }catch(Exception exc){
															//System.out.println("exc.............."+exc.toString());
														  }%>                                                         
                                                        </table>
                                                      </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <%  }else{%>
													<tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                                    </tr>
													<tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="comment">No Development 
                                                        and Improvement available </td>
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
                                                              New Improvement</a> 
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
                                                  <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDevImprovement.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="middle" width="17%">&nbsp;</td>
                                                      <td height="21" colspan="2" width="83%" class="comment">*)= 
                                                        required</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="top" width="17%">Group  Category </td>
                                                      <td height="21" colspan="2" width="83%">
													  	<%
														Vector gr_key = new Vector(1,1);
														Vector gr_value = new Vector(1,1);
														Vector listGrcategory = PstDevImprovement.listCategImprovement(grRankOID);
														for(int i = 0;i < listGrcategory.size();i++){
															GroupCategory grpCategory = (GroupCategory)listGrcategory.get(i);
															gr_key.add(grpCategory.getGroupName());
															gr_value.add(""+grpCategory.getOID());
														}
														%> 
														
                                                    <table>
							<tr>
                                                        <td><%=ControlCombo.draw(frmDevImprovement.fieldNames[FrmDevImprovement.FRM_FIELD_GROUP_CATEGORY_ID],"formElemen",null,""+devImprovement.getGroupCategoryId(),gr_value,gr_key)%>
                                                        * <%= frmDevImprovement.getErrorMsg(FrmDevImprovement.FRM_FIELD_GROUP_CATEGORY_ID) %> </td>
                                                        <td><p title="Please insert data Group Rank AND List Group Category to Master Data > Perpormance Apparaisal > Group Rank > List Group Category">
                                                                <img name="Image300" border="0" src="<%=approot%>/images/Info-icon.png" width="20" height="15" alt="Info"></p></td>
							</tr>
                                                    </table>
                                                        
                                                        
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="top" width="17%">Improvement</td>
                                                      <td height="21" colspan="2" width="83%"> 
                                                        <textarea name="<%=frmDevImprovement.fieldNames[FrmDevImprovement.FRM_FIELD_IMPROVEMENT] %>" class="elemenForm" cols="35" rows="3"><%= devImprovement.getImprovement() %></textarea>
                                                        * <%= frmDevImprovement.getErrorMsg(FrmDevImprovement.FRM_FIELD_IMPROVEMENT) %> 
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
									String scomDel = "javascript:cmdAsk('"+oidDevImprovement+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidDevImprovement+"')";
									String scancel = "javascript:cmdEdit('"+oidDevImprovement+"')";
									ctrLine.setBackCaption("Back to List Improvement");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setSaveCaption("Save Improvement");
									ctrLine.setDeleteCaption("Delete Improvement");
									ctrLine.setConfirmDelCaption(" Yes Delete Improvement");

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
                                                      <td colspan="3">&nbsp; 
                                                        
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
