<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.leave.LeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.PstLeaveApplication"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApplication"%>
<%@page import="com.dimata.harisma.form.leave.FrmLeaveApplication"%>
<%@ page language="java" %>
<%@ include file = "main/javainit.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dimata.qdep.system.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.harisma.entity.admin.*" %>
<%!    public String drawListMyLeaveApplication(Vector objectClass,I_Dictionary dictionaryD) {
    
        
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>", "5%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EMPLOYEE)+"</center>", "10%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.DEPARTMENT)+"</center>", "10%");        
        ctrlist.addHeader("<center>DATE OF APPLICATION</center>", "10%");
        ctrlist.addHeader("<center>DOC. STATUS</center>", "8%");
        ctrlist.addHeader("<center>DEP. HEAD APPROVAL</center>", "10%");
        ctrlist.addHeader("<center>HR. MANAGER APPROVAL</center>", "10%");
        ctrlist.addHeader("<center>GM. APPROVAL", "10%");
        ctrlist.addHeader("<center>ANNUAL LEAVE</center>", "4%");
        ctrlist.addHeader("<center>LONG LEAVE</center>", "4%");
        ctrlist.addHeader("<center>DAY OF PAYMENT</center>", "4%");
        ctrlist.addHeader("<center>SPECIAL LEAVE</center>", "4%");
        ctrlist.addHeader("<center>UNPAID LEAVE</center>", "4%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEditMyLeave('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector) objectClass.get(i);

            Employee employee = (Employee) temp.get(0);
            LeaveApplication objleaveApplication = (LeaveApplication) temp.get(1);
            Department department = (Department) temp.get(2);

            String strSubmissionDate = "";
            try {
                Date dt_SubmitDate = objleaveApplication.getSubmissionDate();
                if (dt_SubmitDate == null) {
                    dt_SubmitDate = new Date();
                }
                strSubmissionDate = Formater.formatDate(dt_SubmitDate, "MMM dd, yyyy");
            } catch (Exception e) {
                strSubmissionDate = "";
            }

            String strApproval = "";

            Vector rowx = new Vector();
            String statusDoc = SessLeaveApplication.getStatusDocument(objleaveApplication.getDocStatus());
            if (objleaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                rowx.add("" + employee.getEmployeeNum());
            } else {
                rowx.add("<a href=\"javascript:cmdEditMyLeave('" + objleaveApplication.getOID() + "','" + employee.getOID() + "')\">" + employee.getEmployeeNum() + "</a>");
            }
            rowx.add(employee.getFullName());
            rowx.add(department.getDepartment());
            String typeleave = SessLeaveApplication.typeLeave(objleaveApplication.getOID());            
            rowx.add(strSubmissionDate);

            if (statusDoc != null) {
                rowx.add("" + statusDoc);
            } else {
                rowx.add("");
            }

            String depHead = SessLeaveApplication.getEmployeeApp(objleaveApplication.getDepHeadApproval());
            String hrMan = SessLeaveApplication.getEmployeeApp(objleaveApplication.getHrManApproval());
            String gM = SessLeaveApplication.getEmployeeApp(objleaveApplication.getGmApproval());

            if (depHead != null) {
                rowx.add(depHead);
            } else {
                rowx.add("");
            }
            if (hrMan != null) {
                rowx.add(hrMan);
            } else {
                rowx.add("");
            }
            if (gM != null) {
                rowx.add(gM);
            } else {
                rowx.add("");
            }

            float sumAL = SessLeaveApplication.countAL(objleaveApplication.getOID(), employee.getOID());
            float sumLL = SessLeaveApplication.countLL(objleaveApplication.getOID(), employee.getOID());
            float sumDP = SessLeaveApplication.countDP(objleaveApplication.getOID(), employee.getOID());
            float sumSpecial = SessLeaveApplication.countSpecialLeave(objleaveApplication.getOID(), employee.getOID());
            float sumUnpaid = SessLeaveApplication.countUnpaidLeave(objleaveApplication.getOID(), employee.getOID());
            rowx.add("" + sumAL);
            rowx.add("" + sumLL);
            rowx.add("" + sumDP);
            rowx.add("" + sumSpecial);
            rowx.add("" + sumUnpaid);

            lstData.add(rowx);
        }
        return ctrlist.draw();
    }
%>
<%int  appObjCode = 1;%>

<%
if(isLoggedIn==false)
{
%>
	<script language="javascript">
		 window.location="index.jsp";
	</script>
<%
}
%>

<%
String sic = (request.getParameter("ic")==null) ? "0" : request.getParameter("ic");
int infCode = 0;
String msgAccess = "";

I_Dictionary dictionaryD = userSession.getUserDictionary();
               
try
{
	infCode = Integer.parseInt(sic);
}
catch(Exception e)
{ 
	infCode = 0;
}

switch(infCode) 
{
	case I_SystemInfo.DATA_LOCKED : 
		msgAccess  = I_SystemInfo.textInfo[infCode];
		break;

	case I_SystemInfo.HAVE_NOPRIV : 
		msgAccess  = I_SystemInfo.textInfo[infCode];
		break;

	case I_SystemInfo.NOT_LOGIN : 
		msgAccess  = I_SystemInfo.textInfo[infCode];
		break;

	default:
%>
<script language="javascript">
    window.location="index.jsp";
</script>
<%
}
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Vector listNeedApprove = new Vector();
    if (iCommand == Command.LOAD) { 

        listNeedApprove = SessLeaveApplication.getListEmployeeLeaveApplication(emplx.getOID());

    }
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
<style type="text/css">
    #tabmenu {
       color: #333;
       font-weight: bold;
       padding: 3px 7px 3px 5px;
       margin: 3px 0px;
       border-left: 1px #333 solid;
       background-color: #ccc;
       cursor: pointer;
       float: left;
    }
    #tabmenu:hover{
       color: #FFF;
       background-color: #999; 
    }
    

</style>
<script type="text/javascript">
/* Update 2015-01-20 | Putu Hendra */
    function cmdViewApplyLeave(){
        document.frm.command.value="<%=Command.EDIT%>";        
        document.frm.oid_employee.value = "<%=emplx.getOID()%>";
        document.frm.action="employee/leave/leave_app_edit.jsp";
        document.frm.submit();
    }

    function cmdViewMyLeave(){
        document.frm.command.value="<%=Command.LOAD %>";
        document.frm.oid_employee.value = "<%=emplx.getOID()%>";
        document.frm.action="inform.jsp";	
        document.frm.submit();
    }
</script>
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "main/mnmain.jsp" %>
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
                  System Information
                  <!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                          <form name="frm" method="post" action=""> 
                            <input type="hidden" name="command">
                            <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>"  value="">
                            <input type="hidden" name="oid_employee"  value="<%=emplx.getOID()%>">
                            <input type="hidden" name="employee_oid"  value="">
                            <input type="hidden" name="status">
                            <input type="hidden" name="hidden_emp_schedule_id" value="0">
                                                                                    
                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">

                              <tr> 
                                <td valign="top"> 
                                  <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                    <tr> 
                                      <td valign="top"> <!-- #BeginEditable "content" --> 
                                          <div id="tabmenu" onclick="javascript:cmdViewApplyLeave()">Leave Form</div>


                                        <!-- #EndEditable 
                                        
                                        --> 
                                      </td>

                                    </tr>
                                    
                                            <%if (iCommand == Command.LOAD) {%>
                                            
                                            <tr> 
                                                <td height="12" width="100%" class="comment">List My Leave Application</td>
                                            </tr>
                                            <tr> 
                                                <td height="8" width="100%" class="comment">   
                                                    <% if (listNeedApprove != null && listNeedApprove.size() > 0) {
                                                    out.println(drawListMyLeaveApplication(listNeedApprove,dictionaryD));

                                                } else {
                                                    %>    
                                                    List Empty                                                     
                                                    <%} %> 
                                                </td>
                                            </tr>                                        
                                            <%
                                            } else {
                                            %>    
                                            
                                            <tr> 
                                                <td height="8" width="100%"> 
                                                    <div id="tabmenu" onclick="javascript:cmdViewMyLeave()">
                                                        List My Leave Application
                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                            }
                                            %>
                                        
                                  </table>
                                </td>
                              </tr>

                            </table>
                          </form>
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
                                <%@include file="footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
