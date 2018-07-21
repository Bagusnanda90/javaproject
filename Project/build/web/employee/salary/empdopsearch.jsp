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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
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

                Hashtable position = new Hashtable();
                Vector listPosition = PstPosition.listAll();
                position.put("0", "-");
                for (int ls = 0; ls < listPosition.size(); ls++) {
                    Position pos = (Position) listPosition.get(ls);
                    position.put(String.valueOf(pos.getOID()), pos.getPosition());
                }

		for (int i = 0; i < objectClass.size(); i++) {
			Employee employee = (Employee) objectClass.get(i);
			Department department = new Department();
			try{
				 department = PstDepartment.fetchExc(employee.getDepartmentId());
			}catch(Exception exc){
				 department = new Department();
			}
			Vector rowx = new Vector();
			rowx.add(employee.getEmployeeNum());
			rowx.add(employee.getFullName());
			rowx.add(department.getDepartment());
			
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

    //out.println(emp_number);
    //out.println(emp_fullname);
    //out.println(emp_department);

    String whereClause = "";
   

    if (emp_number.compareToIgnoreCase("") != 0) {
        whereClause = whereClause + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + emp_number + "%' AND ";
    }
    if (emp_fullname.compareToIgnoreCase("") != 0) {
        whereClause = whereClause + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + emp_fullname + "%' AND ";
    }
    if (emp_department.compareToIgnoreCase("0") != 0) {
        whereClause = whereClause + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + emp_department + " AND ";
    }

    if (whereClause.length() > 0) {
        whereClause = whereClause + " 1 = 1";
    }
    

    int start = 0;
    int recordToGet = 0;
    String orderClause = "";
    Vector listEmp = new Vector(1,1);
    listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
    function cmdEdit(oid, number, fullname, department, position, commencing_date) {        
        self.opener.document.frm_empsalary.<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_EMPLOYEE_ID]%>.value = oid;
        self.opener.document.frm_empsalary.EMP_NUMBER.value = number;
        self.opener.document.frm_empsalary.EMP_FULLNAME.value = fullname;
        self.opener.document.frm_empsalary.EMP_DEPARTMENT.value = department;
        self.opener.document.frm_empsalary.EMP_POSITION.value = position;
        self.opener.document.frm_empsalary.EMP_COMMENCING_DATE.value = commencing_date;
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
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
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
                        <table width="100%" border="0" cellspacing="2" cellpadding="2" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
											<% if(listEmp != null && listEmp.size()>0){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                <%=drawList(listEmp)%>
                                                </td>
                                              </tr>
											  <%}else{%>
											  <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="comment"> 
                                                No Employee match search data
                                                </td>
                                              </tr>
											  <%}%>
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
  <tr> 
    <td colspan="2" height="20" bgcolor="#15A9F5">
      <%@ include file = "../../main/footer.jsp" %>
    </td>
  </tr>
</table>

</body>
</html>
