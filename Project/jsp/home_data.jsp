<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%//@ page import = "com.dimata.harisma.utility.service.leavedp.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "main/javainit.jsp" %>
<%
    int  appObjCode = -1;//AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN);

    response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "nocache");
%>
<!-- Jsp Block -->
<%!
    public String drawList(Vector objectClass ){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Birthday","6%");
        ctrlist.addHeader("Payroll","5%");
        ctrlist.addHeader("Name","12%");
        ctrlist.addHeader("Sex","4%");
        ctrlist.addHeader("Religion","4%");
        ctrlist.addHeader("Department","10%");
        ctrlist.addHeader("Position","10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector)objectClass.get(i);
            Employee employee = (Employee)temp.get(0);
            Department department = (Department)temp.get(1);
            Position position = (Position)temp.get(2);
            Level level = (Level)temp.get(5);
            Religion religion = (Religion)temp.get(6);
            Marital marital = (Marital)temp.get(7);

            Vector rowx = new Vector();
            rowx.add(String.valueOf(Formater.formatDate(employee.getBirthDate(), "dd MMM yyyy")));
            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(PstEmployee.sexKey[employee.getSex()]);
            rowx.add(religion.getReligion());
            rowx.add(department.getDepartment());
            rowx.add(position.getPosition());

            lstData.add(rowx);
            //lstLinkData.add(String.valueOf(employee.getOID()));
        }
        return ctrlist.draw();
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
Vector listEmpBirthday = new Vector(1, 1);
if(!NonViewBirthday){
    
    SessEmployee sessEmployee = new SessEmployee();
    listEmpBirthday = sessEmployee.getBirthdayReminder();	
	
}

if(iCommand==Command.SUBMIT){
	SessEmployee sessEmployee = new SessEmployee();
    listEmpBirthday = sessEmployee.getBirthdayReminder();
}

%>

<!-- JSP Block -->
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - Index Page</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>

function cmdView(){
	document.frm.command.value="<%=Command.SUBMIT%>";
	document.frm.action="home.jsp";	
	document.frm.submit();
}

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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "main/mnmain_sanur_par_01.jsp" %>
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
                <td height="20"><font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->
                  <div align="center"><big><b>Welcome to<br>
                    Human Resources Management System</b></big></div>
                  <!-- #EndEditable --> 
                    </strong></font>
	        </td>
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
									<form name="frm" method="post" action=""> 
									<input type="hidden" name="command">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><a href="Harisma_n_Payroll.zip" >Data </a>
										  <% 
										if(NonViewBirthday){
										%>Employee's Birthday List
										<%}%></span> 
                                          </td>
                                        </tr>
                                      <tr> 
                                        <td> 
                                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <% 
										if(!NonViewBirthday){
										
											if((listEmpBirthday != null) && (listEmpBirthday.size() > 0)) {%>
                                        <tr> 
                                          <td height="8" width="100%"><%out.println(drawList(listEmpBirthday));%></td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No employee's birthday data available</span> 
                                          </td>
                                        </tr>
                                        <% }
										}else{
										
										if(iCommand==Command.SUBMIT){
										
										
										if((listEmpBirthday != null) && (listEmpBirthday.size() > 0)) {%>
                                        <tr> 
                                          <td height="8" width="100%"><%out.println(drawList(listEmpBirthday));%></td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No employee's birthday data available</span> 
                                          </td>
                                        </tr>
                                        <% }
										
										}else{%>
										<tr>
											<td>
											
                                            <a href="javascript:cmdView()">View Employees Birthday</a> 
											<br>
											<br>
											<br>
											</td>
										</tr>	
                                            <%
											}
										}
										%>
                                          </table>
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
      <%@ include file = "main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
