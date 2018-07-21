
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
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.location.Location" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
%>
<%    
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    //Get Filter
    String empNum = FRMQueryString.requestString(request, "employee_num");
    String empName = FRMQueryString.requestString(request, "employee_name");
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
    long positionId = FRMQueryString.requestLong(request, "position_id");
    int resign = FRMQueryString.requestInt(request, "resign");
    long levelId = FRMQueryString.requestLong(request, "level_id");
    long empCatId = FRMQueryString.requestLong(request, "emp_category_id"); 
    long maritalId = FRMQueryString.requestLong(request, "marital_id");
    long religionId = FRMQueryString.requestLong(request, "religion_id");
    long raceId = FRMQueryString.requestLong(request, "race_id");
    int birthMonth = FRMQueryString.requestInt(request, "birth_month");
    int command1 = FRMQueryString.requestInt(request, "command1");
//    dataAjaxSource = "&employee_num="+empNum+"&employee_name="+empName;
//                    dataAjaxSource = dataAjaxSource + "&company_id="+companyId+"&division_id="+divisionId;
//                    dataAjaxSource = dataAjaxSource + "&department_id="+departmentId+"&section_id="+sectionId;
//                    dataAjaxSource = dataAjaxSource + "&position_id="+positionId+"&resign="+resign+"&inEmpId="+inEmpId;
//                    dataAjaxSource = dataAjaxSource + "&level_id="+levelId+"&emp_category_id="+empCatId+"&level_id="+levelId;
//                    dataAjaxSource = dataAjaxSource + "&marital_id="+maritalId+"&religion_id="+religionId+"&race_id="+raceId+"&birth_month="+birthMonth;
//    
    //long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    String email = FRMQueryString.requestString(request, "email");
    String cc = FRMQueryString.requestStringWithoutInjection(request, "cc");
    String bcc = FRMQueryString.requestString(request, "bcc");
    String subject = FRMQueryString.requestString(request, "subject");
    String message = FRMQueryString.requestStringWithoutInjection(request, "message");
    int iErrCode = FRMMessage.ERR_NONE;
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");

    if (email.length()>0 && iCommand == Command.GOTO){
    session.putValue("empNum", empNum);
    session.putValue("empName", empName);
    session.putValue("companyId", companyId);
    session.putValue("divisionId", divisionId);
    session.putValue("departmentId", departmentId);
    session.putValue("sectionId", sectionId);
    session.putValue("positionId", positionId);
    session.putValue("resign", resign);
    session.putValue("levelId", levelId);
    session.putValue("empCatId", empCatId);
    session.putValue("maritalId", maritalId);
    session.putValue("religionId", religionId);
    session.putValue("raceId", raceId);
    session.putValue("birthMonth", birthMonth);
    session.putValue("command1", command1);
        
    session.putValue("emailAddress", email);
    session.putValue("ccAddress", cc);
    session.putValue("bccAddress", bcc);
    session.putValue("subject", subject);
    session.putValue("message", message);
    }
%>
<html>
<head> 
<title>Harisma - Send Email</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<script language="JavaScript">

<% if ( iCommand == Command.GOTO )  { %>
    window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListEmail");
<% } %>    


function cmdSendEmail1(){
       // window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailXLS?oid=");
       }
function cmdSendEmail(){
                document.frmEmpEmail.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmEmpEmail.action="editPenerima.jsp";
                document.frmEmpEmail.submit();
		//window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListEmail");
                //document.frmEmpEmail.email.value=0;
	}

</script>
</head>
<body  bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<script src="../../styles/ckeditor/ckeditor.js"></script>
<!-- End Calender-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FFFFFF">
  <tr>
    <td  bgcolor="#9BC1FF" height="20" ID="MAINMENU" valign="middle">
	<table width="100%" border="0">
  <tr>
    <td  bgcolor="#BBDDFF">
      <div align="center"><font color="#0000FF">&nbsp;</font></div>
	</td>
  </tr>
</table>
	</td>
  </tr>
  <tr>
    <td valign="middle" align="left">
      <form name="frmEmpEmail" action="" onsubmit="window.status=''">
        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">  
        
        <input type="hidden" name="approvedId1" value="0">
        <input type="hidden" name="isApprove1" value="false">
             
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          
          <tr>
            <td width="100%">&nbsp;</td>
          </tr>
          <tr> 
            <td width="100%" align="center"><font color="#FF0000" size="4"><b><font face="Verdana, Arial, Helvetica, sans-serif">.:: 
              SEND TO EMAIL ::.</font></b></font></td>
          </tr>
          <tr> 
            <td width="100%">&nbsp;</td>
          </tr>

          <tr> 
            <td width="100%" valign="middle" align="center"> 
              <table width="800" border="0" cellpadding="0" cellspacing="0" align="center">
                <tr> 
                  <td colspan="3" height="28" valign="top"> 
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="43" valign="top" align="right" background="../../images/login_images/uppmidd.jpg"><img src="../../images/login_images/upcorner.jpg" width="12" height="28"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr> 
                  <td width="12" valign="top" background="../../images/login_images/left.jpg"><img src="../../images/login_images/left.jpg" width="12" height="17"> 
                  </td>
                  <td valign="top"> 
                    <table width="100%" border="0" cellpadding="1" cellspacing="0" bgcolor="#52BAFF">
                        <input type="hidden" name="employee_num" value="<%=empNum%>">
                        <input type="hidden" name="employee_name" value="<%=empName%>">
                        <input type="hidden" name="company_id" value="<%=companyId%>">
                        <input type="hidden" name="division_id" value="<%=divisionId%>">
                        <input type="hidden" name="department_id" value="<%=departmentId%>">
                        <input type="hidden" name="department_id" value="<%=departmentId%>">
                        <input type="hidden" name="section_id" value="<%=sectionId%>">
                        <input type="hidden" name="position_id" value="<%=positionId%>">
                        <input type="hidden" name="resign" value="<%=resign%>">
                        <input type="hidden" name="level_id" value="<%=levelId%>">
                        <input type="hidden" name="emp_category_id" value="<%=empCatId%>">
                        <input type="hidden" name="marital_id" value="<%=maritalId%>">
                        <input type="hidden" name="religion_id" value="<%=religionId%>">
                        <input type="hidden" name="race_id" value="<%=raceId%>">
                        <input type="hidden" name="birth_month" value="<%=birthMonth%>">
                        <input type="hidden" name="command1" value="<%=command1%>"
                      <tr valign="middle"> 
                        <td width="89" height="15">&nbsp;</td>
                        <td width="222"></td>
                      </tr>
                      <tr valign="middle">
                          <td height="15" nowrap width="89">Subject</td>
                              <td width="222">
                                <input type="text" name="subject" size="25">
                        </td>
                      </tr>
                      <tr valign="middle"> 
                        <td height="15" nowrap width="89">Email</td>
                        <td width="222"> 
                          <input type="text" name="email" size="25" >
                        </td>
                      </tr>
                      <tr valign="middle">
                          <td height="15" nowrap width="89">CC</td>
                          <td width="222">
                              <input type="text" name="cc" size="25">
                          </td>
                      </tr>
                      <tr valign="middle">
                          <td height="15" nowrap width="89">BCC</td>
                          <td width="222">
                              <input type="text" name="bcc" size="25">
                          </td>
                      </tr>
                      <tr valign="top">
                          <td height="15" nowrap width="89">Message</td>
                          <td>
                              <textarea class="ckeditor" name="message" row="5" cols="20"></textarea>
                          </td>
                      </tr>
                      <tr valign="middle">
                          <td>
                              <a href="javascript:cmdSendEmail()" onMouseOut="MM_swapImgRestore()" >SEND</a>
                          </td>
                      </tr>
                    </table>
                  </td>
                  <td width="12" valign="top" background="../../images/login_images/right.jpg"><img src="../../images/login_images/right.jpg" width="12" height="17"></td>
                </tr>
                <tr> 
                  <td colspan="3" height="42" valign="top"> 
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="27" valign="top" background="../../images/login_images/bottom_middle.jpg">&nbsp;</td>
                        <td width="12" valign="top" align="right" background="../../images/login_images/bottom_middle.jpg"><img src="../../images/login_images/bottom_right_corner.jpg" width="12" height="42"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
		 
        </table>
		  </form>
    </td>
  </tr>
  <tr>
    <td colspan="2" height="20" <%=bgFooterLama%>>
      <%@ include file = "../../main/footer.jsp" %>
      </td>
  </tr>
</table>
<script language="JavaScript">
 document.frmEmpEmail.pass_wd.focus();
</script>
</body>
</html>
