<%@page contentType="text/html"%>
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
<%@ page import = "com.dimata.harisma.form.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<% int  appObjCode = 1; %>

<%@ include file = "../../main/javainit.jsp" %>

<%!
public String drawList(Vector objectClass)
{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Employee Number","8%");
		ctrlist.addHeader("Full Name","15%");
		ctrlist.addHeader("Department","10%");
		ctrlist.addHeader("Position","10%");
			
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.reset();

		Hashtable position = new Hashtable();
		Vector listPosition = PstPosition.listAll();
		position.put("0", "-");
		for (int ls = 0; ls < listPosition.size(); ls++) {
			Position pos = (Position) listPosition.get(ls);
			position.put(String.valueOf(pos.getOID()), pos.getPosition());
		}

		Hashtable hDept = new Hashtable();
		Vector listDept = PstDepartment.listAll();
		hDept.put("0", "-");
		for (int ls = 0; ls < listDept.size(); ls++) {
			Department dept = (Department) listDept.get(ls);
			hDept.put(String.valueOf(dept.getOID()), dept.getDepartment());
		}

		for (int i = 0; i < objectClass.size(); i++) 
		{
			Employee employee = (Employee) objectClass.get(i);
			Vector rowx = new Vector();
			rowx.add(employee.getEmployeeNum());
			rowx.add(employee.getFullName());
			rowx.add(hDept.get(String.valueOf(employee.getDepartmentId())));
			rowx.add(position.get(String.valueOf(employee.getPositionId())));
			
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + employee.getDepartmentId() + "','" + position.get(String.valueOf(employee.getPositionId())) + "','" + Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"));
		}

		ctrlist.setLinkSufix("')");
		return ctrlist.draw();
	}
%>

<% 
String emp_number = request.getParameter("emp_number");
String emp_fullname = request.getParameter("emp_fullname");
String emp_department = request.getParameter("emp_department");

String whereClause = "";
String whereClause1 = "";
String whereClause2 = "";
String whereClause3 = "";

if (emp_number.compareToIgnoreCase("") != 0) {
	whereClause1 = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + emp_number + "%' ";
}
if (emp_fullname.compareToIgnoreCase("") != 0) {
	whereClause2 = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + emp_fullname + "%' ";
}
if (emp_department.compareToIgnoreCase("0") != 0) {
	whereClause3 = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + emp_department + " ";
}

if (whereClause1.length() > 0) {
	whereClause = whereClause1;
}
if (whereClause2.length() > 0) {
	if (whereClause.length() > 0) {
		whereClause += " AND " + whereClause2;
	}
	else {
		whereClause = whereClause2;
	}
}
if (whereClause3.length() > 0) {
	if (whereClause.length() > 0) {
		whereClause += " AND " + whereClause3;
	}
	else {
		whereClause = whereClause3;
	}
}

whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";

int start = 0;
int recordToGet = 0;
String orderClause = "";
Vector listEmp = new Vector(1,1);
listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
function cmdEdit(oid, number, fullname, department, position, commencing_date) 
{
	self.opener.document.frm_canteenvisitation.<%=FrmCanteenVisitation.fieldNames[FrmCanteenVisitation.FRM_FIELD_EMPLOYEE_ID]%>.value = oid;
	self.opener.document.frm_canteenvisitation.EMP_NUMBER.value = number;
	self.opener.document.frm_canteenvisitation.EMP_FULLNAME.value = fullname;
	self.opener.document.frm_canteenvisitation.EMP_DEPARTMENT.value = department;
	self.opener.document.frm_canteenvisitation.EMP_POSITION.value = position;
	self.opener.document.frm_canteenvisitation.EMP_COMMENCING_DATE.value = commencing_date;
	self.close();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FCFDEC" >
  <%-- <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <%@ include file = "../../main/header.jsp" %>
  </tr> --%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong>Employee</strong></font></td>
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
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                <%=drawList(listEmp)%>
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
</table>
</body>
</html>
