<%@page contentType="text/html"%>

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
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>

<%!
public String drawList(Vector objectClass)
{		
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Payroll","10%");
	ctrlist.addHeader("Full Name","40%");
	ctrlist.addHeader("Department","25%");
	ctrlist.addHeader("Position","25%");
		
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.reset();

	Hashtable position = new Hashtable();
	Vector listPosition = PstPosition.listAll();
	position.put("0", "-");
	for (int ls = 0; ls < listPosition.size(); ls++) 
	{
		Position pos = (Position) listPosition.get(ls);
		position.put(String.valueOf(pos.getOID()), pos.getPosition());
	}

	Hashtable hDept = new Hashtable();
	Vector listDept = PstDepartment.listAll();
	hDept.put("0", "-");
	for (int ls = 0; ls < listDept.size(); ls++) 
	{
		Department dept = (Department) listDept.get(ls);
		hDept.put(String.valueOf(dept.getOID()), dept.getDepartment());
	}

	String strDeparment = "";
	String strPosition = "";	
	for (int i = 0; i < objectClass.size(); i++) 
	{
		Employee employee = (Employee) objectClass.get(i);		
		
		strDeparment = String.valueOf(hDept.get(String.valueOf(employee.getDepartmentId())));
		strPosition = String.valueOf(position.get(String.valueOf(employee.getPositionId())));
		
		Vector rowx = new Vector();
		rowx.add(employee.getEmployeeNum());
		rowx.add(employee.getFullName());
		rowx.add(strDeparment);
		rowx.add(strPosition);

		lstData.add(rowx);
		lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + strPosition + "','" + strDeparment);
	}

	ctrlist.setLinkSufix("')");
	return ctrlist.draw();
}
%>

<% 
String strCommand = request.getParameter("command");
String emp_payroll = (request.getParameter("EMP_NUMBER")!=null ? request.getParameter("EMP_NUMBER") : "");
String emp_fullname = (request.getParameter("emp_fullname")!=null ? request.getParameter("emp_fullname") : "");
String emp_department = (request.getParameter("EMP_DEPARTMENT")!=null ? request.getParameter("EMP_DEPARTMENT") : "0");
int iCommand = strCommand!=null ? Integer.parseInt(strCommand) : 0;
long departmentSelected = emp_department!=null ? Long.parseLong(emp_department) : 0;

Vector listEmp = new Vector(1,1);
if( (iCommand==Command.LIST) || (emp_payroll!=null && emp_payroll.length()>0) || (emp_fullname!=null && emp_fullname.length()>0) || (departmentSelected > 0) )
{
	String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
	if(emp_payroll!=null && emp_payroll.length()>0)
	{
		whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + emp_payroll + "%' ";
	}
	
	if(emp_fullname!=null && emp_fullname.length()>0)       
	{
		whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + emp_fullname + "%' ";
	}
	
	if(departmentSelected > 0)        
	{
		whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + emp_department;
	}
	
	String orderClause = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
	listEmp = PstEmployee.list(0, 0, whereClause, orderClause);
}
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
function cmdSearchEmp()
{
	document.frm_leave_application.command.value="<%=Command.LIST%>";
	document.frm_leave_application.action="leave_application_empsearch.jsp";
	document.frm_leave_application.submit();	
}

function cmdEdit(oid, empnum, empname, empposition, empdepartment) 
{
	self.opener.document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oid;
	self.opener.document.frm_leave_application.EMP_NUM.value = empnum;				
	self.opener.document.frm_leave_application.EMP_NAME.value = empname;	
	self.opener.document.frm_leave_application.EMP_POSITION.value = empposition;				
	self.opener.document.frm_leave_application.EMP_DEPARTMENT.value = empdepartment;
	self.close();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="frm_leave_application" method="post" action=""> 
<input type="hidden" name="command" value="<%=iCommand%>">  
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FCFDEC" >
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
				  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
					<td valign="top"> 
						  <table cellpadding="1" cellspacing="1" border="0" width="100%" class="tabbg">
                            <tr> 
                              <td valign="top" width="12">&nbsp;</td>
                              <td valign="top" width="94">&nbsp;</td>
                              <td width="14">&nbsp;</td>
                              <td width="833">&nbsp;</td>
                              <td width="15">&nbsp;</td>
                            </tr>
                            <tr> 
                              <td valign="top" width="12">&nbsp;</td>
                              <td valign="top" width="94"> 
                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                              </td>
                              <td width="14">:</td>
                              <td width="833"> 
                                <input type="text" name="EMP_NUMBER"  value="<%=emp_payroll%>" class="elemenForm" size="10">
                                <input type="hidden" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]%>" value="" class="formElemen">
                              </td>
                              <td width="15">&nbsp; </td>
                            </tr>
                            <tr> 
                              <td valign="top" width="12">&nbsp;</td>
                              <td valign="top" width="94"> 
                                <div align="left">Name</div>
                              </td>
                              <td width="14">:</td>
                              <td width="833"> 
                                <input type="text" name="emp_fullname"  value="<%=emp_fullname%>" class="elemenForm" size="40">
                              </td>
                              <td width="15">&nbsp; </td>
                            </tr>
                            <tr> 
                              <td valign="top" width="12">&nbsp;</td>
                              <td valign="top" width="94"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                              <td width="14">:</td>
                              <td width="833"> 
                                <%
								Vector dept_value = new Vector(1,1);
								Vector dept_key = new Vector(1,1);
								Vector listDept = new Vector(1,1);
								
								dept_value.add("0");
								dept_key.add("ALL (may take long time) ...");
								
								listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");													
								for (int i = 0; i < listDept.size(); i++) 
								{
									Department dept = (Department) listDept.get(i);
									dept_key.add(dept.getDepartment());
									dept_value.add(String.valueOf(dept.getOID()));
								}
		
								out.println(ControlCombo.draw("EMP_DEPARTMENT", "formElemen", null, ""+departmentSelected, dept_value, dept_key));									
							  %>
                              </td>
                              <td width="15">&nbsp; </td>
                            </tr>
                            <tr> 
                              <td valign="top" width="12">&nbsp;</td>
                              <td valign="top" width="94">&nbsp;</td>
                              <td width="14">&nbsp;</td>
                              <td width="833"><a href="javascript:cmdSearchEmp()" class="command">Search 
                                Employee</a></td>
                              <td width="15">&nbsp;</td>
                            </tr>
                            <tr> 
                              <td valign="top" width="12">&nbsp;</td>
                              <td valign="top" width="94">&nbsp;</td>
                              <td width="14">&nbsp;</td>
                              <td width="833">&nbsp;</td>
                              <td width="15">&nbsp; </td>
                            </tr>
                          <%
						  if(listEmp!=null && listEmp.size()>0)
						  {
						  %>
                            <tr> 
                              <td colspan="5" valign="top"><%=drawList(listEmp)%></td>
                            </tr>
                          <%
						  }
						  else if(iCommand == Command.LIST)
						  {
						  %>
                            <tr> 
                              <td valign="top" width="12">&nbsp;</td>
                              <td colspan="3" valign="top">
                                <div class=msginfo>No employee data found ...</div>
                              </td>
                              <td width="15">&nbsp; </td>							  							  
                            </tr>
                            <tr> 
                              <td colspan="5" valign="top">&nbsp;</td>
                            </tr>
                          <%
						  }
						  %>
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
</form>
<script language="javascript">
	document.frm_leave_application.EMP_NUMBER.focus();
</script>
</body>
</html>
