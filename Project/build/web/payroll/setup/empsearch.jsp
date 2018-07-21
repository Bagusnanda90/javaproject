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
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE); %>
<%@ include file = "../../main/javainit.jsp" %>

<%!
	public String drawList(Vector objectClass, String emp_period){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Employee Number","8%");
		ctrlist.addHeader("Full Name","15%");
		ctrlist.addHeader("Department","10%");
		ctrlist.addHeader("Section","10%");
			
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.reset();

                Hashtable hDept = new Hashtable();
                Vector listDept = PstDepartment.listAll();
                hDept.put("0", "-");
                for (int ls = 0; ls < listDept.size(); ls++) {
                    Department dept = (Department) listDept.get(ls);
                    hDept.put(String.valueOf(dept.getOID()), dept.getDepartment());
                }
				
				// hastable untuk section
				//updated by Yunny
				Hashtable hSec = new Hashtable();
                Vector listSec = PstSection.listAll();
                hSec.put("0", "-");
                for (int secIdx = 0; secIdx < listSec.size(); secIdx++) {
                    Section sec = (Section) listSec.get(secIdx);
                    hSec.put(String.valueOf(sec.getOID()), sec.getSection());
                }
				
                String whereClauseEmpSchedule = "";
                Hashtable hEmpSchedule = new Hashtable();
                if (emp_period.compareToIgnoreCase("") != 0) {
                    whereClauseEmpSchedule = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + " = " + emp_period + " ";
                    Vector listEmpSchedule = PstEmpSchedule.list(0, 0, whereClauseEmpSchedule, "");
                    if (listEmpSchedule.size() > 0) {
                        for (int ls = 0; ls < listEmpSchedule.size(); ls++) {
                            EmpSchedule es = (EmpSchedule) listEmpSchedule.get(ls);
                            hEmpSchedule.put(String.valueOf(es.getEmployeeId()), String.valueOf(es.getPeriodId()));
                        }
                    }
                }

		for (int i = 0; i < objectClass.size(); i++) {
                    /*
			Employee employee = (Employee) objectClass.get(i);
			Vector rowx = new Vector();
                        if (hEmpSchedule.get(String.valueOf(employee.getOID())) == null) {
                            rowx.add(employee.getEmployeeNum());
                        }
                        else {
                            rowx.add(employee.getEmployeeNum() + " *");
                        }
			rowx.add(employee.getFullName());
			rowx.add(hDept.get(String.valueOf(employee.getDepartmentId())));
			lstData.add(rowx);
                        if (hEmpSchedule.get(String.valueOf(employee.getOID())) == null) {
                            lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + employee.getDepartmentId());
                        }
                        else {
                            lstLinkData.add("");
                        }
                    */
			Employee employee = (Employee) objectClass.get(i);
			Vector rowx = new Vector();
                        if (hEmpSchedule.get(String.valueOf(employee.getOID())) == null) {
                            rowx.add(employee.getEmployeeNum());
                            rowx.add(employee.getFullName());
                            rowx.add(hDept.get(String.valueOf(employee.getDepartmentId())));
                            rowx.add(hSec.get(String.valueOf(employee.getSectionId())));
                            lstData.add(rowx);
                            lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + employee.getDepartmentId());
                        }
		}

		ctrlist.setLinkSufix("')");
		return ctrlist.draw();
	}
%>

<% 
    //String emp_number = request.getParameter("emp_number");
    //String emp_fullname = request.getParameter("emp_fullname");
    String emp_department = request.getParameter("emp_department");
	//String emp_section = request.getParameter("emp_section");
    String emp_period = "0";

    //out.println(emp_number);
    //out.println(emp_fullname);
    //out.println(emp_department);

    String whereClause = "";
    String whereClause1 = "";
    String whereClause2 = "";
    String whereClause3 = "";
	String whereClause4 = "";

   /* if (emp_number.compareToIgnoreCase("") != 0) {
        whereClause1 = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + emp_number + "%' ";
    }*/
    /*if (emp_fullname.compareToIgnoreCase("") != 0) {
        whereClause2 = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + emp_fullname + "%' ";
    }*/
    if (emp_department.compareToIgnoreCase("0") != 0) {
        whereClause3 = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + emp_department + " ";
    }
	// untuk permintaan Intimas, nambah parameter section
	// updated by yunny
	/*if (emp_section.compareToIgnoreCase("0") != 0) {
        whereClause4 = PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + emp_section + " ";
    }*/

    
    if (whereClause3.length() > 0) {
        if (whereClause.length() > 0) {
            whereClause += " AND " + whereClause3;
        }
        else {
            whereClause = whereClause3 + " AND ";
        }
    }
	
	 
    //out.println(whereClause);

    whereClause +=PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";

    int start = 0;
    int recordToGet = 0;
    String orderClause = "";
    Vector listEmp = new Vector(1,1);
    listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);

%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
    function cmdEdit(oid, number, fullname, department) {
        //alert(oid + " " + number + " " + fullname);
        self.opener.document.frm_pay_emp_level.department.value = department;
	self.opener.document.frm_pay_emp_level.payrollNum.value = number;
	self.opener.document.frm_pay_emp_level.command.value = "<%=Command.ADD%>";                 
        //self.close();
	self.opener.document.frm_pay_emp_level.submit();
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
                                                <%=drawList(listEmp, emp_period)%>
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
