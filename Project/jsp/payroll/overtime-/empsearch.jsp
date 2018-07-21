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
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE); %>
<%@ include file = "../../main/javainit.jsp" %>

<%!
	public String drawList(Vector objectClass){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Employee Number","8%");
		ctrlist.addHeader("Full Name","15%");
		ctrlist.addHeader("Department","10%");
			
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.reset();

         for (int i = 0; i < objectClass.size(); i++) {
          
			Employee employee = (Employee) objectClass.get(i);
			Vector rowx = new Vector();
                        
			rowx.add(employee.getEmployeeNum());
			rowx.add(employee.getFullName());
			Department department = new Department();
			Position position = new Position();
			if(employee.getDepartmentId()!=0)
			{
				try{
					department = PstDepartment.fetchExc(employee.getDepartmentId());
				}catch(Exception e){;}
			}
			
			if(employee.getPositionId()!=0)
			{
			   	try{
					position = PstPosition.fetchExc(employee.getPositionId());
				}catch(Exception e){;}
			}
			
			rowx.add(department.getDepartment());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + position.getPosition());
	  
		}

		ctrlist.setLinkSufix("')");
		return ctrlist.draw();
	}
%>

<% 
    String emp_division = request.getParameter("emp_division");
    String emp_department = request.getParameter("emp_department");
    String emp_section = request.getParameter("emp_section");
    String emp_name = request.getParameter("emp_name");
	String emp_number = request.getParameter("emp_number");
	
	/*out.println(emp_division);
    out.println(emp_department);
    out.println(emp_section);
	out.println(emp_name);
	out.println(emp_number);*/

    String whereClause = "";
    String whereClause1 = "";
    String whereClause2 = "";
    String whereClause3 = "";
	String whereClause4 = "";
	String whereClause5 = "";
	
	if (emp_division.compareToIgnoreCase("0") != 0) {
        whereClause1 = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + emp_division + " ";
    }
	
	if (emp_department.compareToIgnoreCase("0") != 0) {
        whereClause2 = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + emp_department + " ";
    }
	
	if (emp_section.compareToIgnoreCase("0") != 0) {
        whereClause3 = PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + emp_section + " ";
    }

    if (emp_number.compareToIgnoreCase("") != 0) {
        whereClause4 = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + emp_number + "%' ";
    }
    if (emp_name.compareToIgnoreCase("") != 0) {
        whereClause5 = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + emp_name + "%' ";
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
	
	if (whereClause4.length() > 0) {
        if (whereClause.length() > 0) {
            whereClause += " AND " + whereClause4;
        }
        else {
            whereClause = whereClause4;
        }
    }
	
	if (whereClause5.length() > 0) {
        if (whereClause.length() > 0) {
            whereClause += " AND " + whereClause5;
        }
        else {
            whereClause = whereClause5;
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
    function cmdEdit(oid, number, fullname, position) {
        //alert(oid + " " + number + " " + fullname);
        self.opener.document.frm_ovt_input.<%=FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_EMPLOYEE_NUM]%>.value = number;
		//self.opener.document.frm_ovt_input.hidden_id_employee.value = oid;
        self.opener.document.document.all.position.innerHTML = position;
        self.opener.document.document.all.nama.innerHTML = fullname;
        //self.opener.document.frm_ovt_input.position_name.value = position;
		self.close();
    }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
                                                <td height="22" valign="middle"> 
                                                <%=drawList(listEmp)%>
                                                </td>
                                              </tr>
                                              <%--
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle"> 
                                                &nbsp;&nbsp;&nbsp;<b>Note</b> : <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* - this employee already had a schedule
                                                </td>
                                              </tr>
                                              --%>
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
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td colspan="2" height="20" bgcolor="#15A9F5">
      <%@ include file = "../../main/footer.jsp" %>
    </td>
  </tr>
</table>

</body>
</html>
