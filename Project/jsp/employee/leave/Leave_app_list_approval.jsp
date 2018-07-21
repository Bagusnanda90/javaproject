
<%-- 
    Document   : Leave_app_list_approval
    Created on : May 7, 2010, 3:22:45 PM
    Author     : root
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!

public String drawList(Vector objectClass )
{
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","5%");	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EMPLOYEE)+"</center>","10%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.DEPARTMENT)+"</center>","10%");
       
	ctrlist.addHeader("<center>Date Of Application</center>","10%");		
        ctrlist.addHeader("<center>Doc Status</center>","8%");
	ctrlist.addHeader("<center>Dep Head Approval</center>","10%");	
        ctrlist.addHeader("<center>HR Manager Approval</center>","10%");
        ctrlist.addHeader("<center>GM Approval","10%");
        ctrlist.addHeader("<center>AL</center>","4%");
        ctrlist.addHeader("<center>LL</center>","4%");
        ctrlist.addHeader("<center>DP</center>","4%");
        ctrlist.addHeader("<center>SL</center>","4%");
        ctrlist.addHeader("<center>UL</center>","4%");
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i=0; i<objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
                
                Employee employee = (Employee) temp.get(0);
                LeaveApplication objleaveApplication = (LeaveApplication) temp.get(1);
                Department department = (Department) temp.get(2);
                
		
		String strSubmissionDate = ""; 
		try
		{
			Date dt_SubmitDate = objleaveApplication.getSubmissionDate();
			if(dt_SubmitDate==null)
			{
				dt_SubmitDate = new Date();
			}
			strSubmissionDate = Formater.formatDate(dt_SubmitDate, "MMM dd, yyyy");
		}
		catch(Exception e)
		{ 
			strSubmissionDate = ""; 
		}

                
		String strApproval = "";
				
                Vector rowx = new Vector();
                String statusDoc = SessLeaveApplication.getStatusDocument(objleaveApplication.getDocStatus());
                if(objleaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                    rowx.add(""+employee.getEmployeeNum());		
                }else{    
                    rowx.add("<a href=\"javascript:cmdEdit('"+objleaveApplication.getOID()+"','" + employee.getOID() + "')\">"+employee.getEmployeeNum()+"</a>");		
                }
		rowx.add(employee.getFullName()); 
                rowx.add(department.getDepartment());
                String typeleave = SessLeaveApplication.typeLeave(objleaveApplication.getOID());                    
                //rowx.add(""+typeleave);  
		rowx.add(strSubmissionDate);	
                
                
                if(statusDoc != null){
                    rowx.add(""+statusDoc);	
                }else{
                    rowx.add("");	
                }
                
                String depHead = SessLeaveApplication.getEmployeeApp(objleaveApplication.getDepHeadApproval());
                String hrMan = SessLeaveApplication.getEmployeeApp(objleaveApplication.getHrManApproval());
                String gM = SessLeaveApplication.getEmployeeApp(objleaveApplication.getGmApproval());
                
                if(depHead != null){
                    rowx.add(depHead);		
                }else{
                    rowx.add("");
                }
                if(hrMan != null){
                    rowx.add(hrMan);		
                }else{
                    rowx.add("");		
                }
                if(gM != null){
                    rowx.add(gM);		
                }else{
                    rowx.add("");		
                }
                
                float sumAL = SessLeaveApplication.countAL(objleaveApplication.getOID(),employee.getOID());
                float sumLL = SessLeaveApplication.countLL(objleaveApplication.getOID(),employee.getOID());
                float sumDP = SessLeaveApplication.countDP(objleaveApplication.getOID(),employee.getOID());
                float sumSpecial = SessLeaveApplication.countSpecialLeave(objleaveApplication.getOID(),employee.getOID());
                float sumUnpaid = SessLeaveApplication.countUnpaidLeave(objleaveApplication.getOID(),employee.getOID());
                rowx.add(""+sumAL);
                rowx.add(""+sumLL);
                rowx.add(""+sumDP);
                rowx.add(""+sumSpecial);
		rowx.add(""+sumUnpaid);		
									
		lstData.add(rowx);
	}
	return ctrlist.draw();
}
%>

<%
ControlLine ctrLine = new ControlLine();
Control ctrlLeave = new Control();
long oidLeave = FRMQueryString.requestLong(request, "hidden_leave_application_id");
int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int recordToGet = 10;
int vectSize = 0;
String whereClause = "";

SrcLeaveApp srcLeaveApp = new SrcLeaveApp();
FrmSrcLeaveApp frmSrcLeaveApp = new FrmSrcLeaveApp(request, srcLeaveApp);
frmSrcLeaveApp.requestEntityObject(srcLeaveApp);

if((iCommand==Command.NEXT) || (iCommand==Command.FIRST) || (iCommand==Command.PREV) || (iCommand==Command.LAST))
{
	try
	{ 
		srcLeaveApp = (SrcLeaveApp)session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION); 
		if (srcLeaveApp == null) 
		{
			srcLeaveApp = new SrcLeaveApp();
		}
	}
	catch(Exception e)
	{ 
		srcLeaveApp = new SrcLeaveApp();
	}
}

SessLeaveApplication sessLeaveApplication = new SessLeaveApplication();
session.putValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION, srcLeaveApp);

//vectSize = sessLeaveApplication.getCountSearchLeaveApplicationHR(srcLeaveApp);
vectSize = sessLeaveApplication.CountsearchLeaveApplication(srcLeaveApp);

if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
{
	start = ctrlLeave.actionList(iCommand, start, vectSize, recordToGet);
}

//Vector records = sessLeaveApplication.searcsearchLeaveApplicathLeaveApplicationHR(srcLeaveApp, start, recordToGet);
Vector records = sessLeaveApplication.searchLeaveApplication(srcLeaveApp, start, recordToGet);
%>

<html>
<head>
<title>HARISMA - Leave Application</title>
<script language="JavaScript">
function cmdAdd()
{
	document.frm_leave_application.command.value="<%=Command.ADD%>";
	document.frm_leave_application.action="leave_app_edit.jsp";
	document.frm_leave_application.submit();
}

function cmdEdit(oidLeave, oidEmployee)
{
	document.frm_leave_application.command.value="<%=Command.EDIT%>";
        document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
	document.frm_leave_application.oid_employee.value = oidEmployee;
	document.frm_leave_application.action="leave_app_edit.jsp";
	document.frm_leave_application.submit();
}

function cmdListFirst()
{
	document.frm_leave_application.command.value="<%=Command.FIRST%>";
	document.frm_leave_application.action="leave_app_list.jsp";
	document.frm_leave_application.submit();
}

function cmdListPrev()
{
	document.frm_leave_application.command.value="<%=Command.PREV%>";
	document.frm_leave_application.action="leave_app_list.jsp";
	document.frm_leave_application.submit();
}

function cmdListNext()  
{
	document.frm_leave_application.command.value="<%=Command.NEXT%>";
	document.frm_leave_application.action="leave_app_list.jsp";
	document.frm_leave_application.submit();
}

function cmdListLast()
{
	document.frm_leave_application.command.value="<%=Command.LAST%>";
	document.frm_leave_application.action="leave_app_list.jsp";
	document.frm_leave_application.submit();
}

function cmdBack()
{
	document.frm_leave_application.command.value="<%=Command.BACK%>";
	document.frm_leave_application.action="leave_app_src.jsp";
	document.frm_leave_application.submit();
}

function fnTrapKD()
{
	switch(event.keyCode) 
	{
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
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave Application &gt; Leave Application &gt; Leave Application List<!-- #EndEditable --> 
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
							  <%
							  if(privView)
							  {
							  %> 
								<form name="frm_leave_application" method="post" action="">
								  <input type="hidden" name="command" value="">
								  <input type="hidden" name="start" value="<%=start%>">
								  <input type="hidden" name="hidden_leave_application_id" value="<%=oidLeave%>">
								  <%
								  if((records!=null)&&(records.size()>0))
								  {
									 out.println(drawList(records)); 
								  }										  
								  else
								  {
									  out.println("<span class=\"comment\"><br>&nbsp;Records is empty ...</span>");
								  }
								  %>
								  <table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr> 
									  <td> 
										<table width="100%" cellspacing="0" cellpadding="3">
										  <tr> 
											<td> 
											  <% 
											  ctrLine.setLocationImg(approot+"/images");
											  ctrLine.initDefault();
											  out.println(ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet));
											  %>
											</td>
										  </tr>
										</table>
									  </td>
									</tr>
									<tr> 
									  <td width="46%">&nbsp;</td>
									</tr>
									<tr> 
									  <td width="46" nowrap align="left" class="command">
										    <table width="51%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td colspan="3">
                                                 <table><tr>
                                                  <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Search Leave Application"></a></td>
                                                  <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                  <td width="229" nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Leave Application</a></td>
                                                  <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnAddOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Leave Application"></a></td>
                                                  <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                  <td width="229" nowrap> <a href="javascript:cmdAdd()" class="command">Add New Application</a></td>
                                                  
                                                 </tr>
                                                </table>
                                              </td>
                                              </tr>
                                            </table>
									  </td>
									</tr>
								  </table>
                                                                  
                                                                  <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>" value="">                           
                                                                  <input type="hidden" name="oid_employee" value="">     
                                                                  <input type="hidden" name="oid_period" value="">
								</form>
								<%
								}
								else
								{
								%>
								<div align="center">You do not have sufficient privilege to access this page.</div>
								<%
								}
								%>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>>
      <%@ include file = "../../main/footer.jsp" %>
     </td>
  </tr>
</table>
</body>
</html>
