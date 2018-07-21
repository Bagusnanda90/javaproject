<%@page contentType="text/html"%>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.recruitment.*" %>
<%@ page import = "com.dimata.harisma.form.recruitment.*" %>
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
		//ctrlist.addHeader("Employee Number","8%");
		ctrlist.addHeader("Full Name","15%");
		ctrlist.addHeader("Department","10%");
                ctrlist.addHeader("Commencement Date","10%");

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

		for (int i = 0; i < objectClass.size(); i++) {
			RecrApplication recrapplication = (RecrApplication) objectClass.get(i);
			Vector rowx = new Vector();
			//rowx.add(employee.getEmployeeNum());
			rowx.add(recrapplication.getFullName());
			//rowx.add(String.valueOf(employee.getDepartmentId()));
			rowx.add(hDept.get(String.valueOf(recrapplication.getFnlDepartmentId())));
                        //rowx.add(position.get(String.valueOf(employee.getPositionId())));
                        rowx.add(Formater.formatDate(recrapplication.getFnlCommDate(), "dd MMMM yyyy"));

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(recrapplication.getOID()) + 
                                        "','" + recrapplication.getFullName() + 
                                        "','" + recrapplication.getFnlDepartmentId() + 
                                        "','" + position.get(String.valueOf(recrapplication.getFnlPositionId())) + 
                                        "','" + Formater.formatDate(recrapplication.getFnlCommDate(), "dd MMMM yyyy"));
		}

		ctrlist.setLinkSufix("')");
		return ctrlist.draw();
	}
%>

<% 
    String fullname = request.getParameter("fullname");
    String department = request.getParameter("department");

    //out.println(emp_number);
    //out.println(emp_fullname);
    //out.println(emp_department);

    String whereClause = "";
    String whereClause1 = "";
    String whereClause2 = "";
    String whereClause3 = "";

    /*if (emp_number.compareToIgnoreCase("") != 0) {
        whereClause1 = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + emp_number + "%' ";
    }*/

    if (fullname.compareToIgnoreCase("") != 0) {
        whereClause2 = PstRecrApplication.fieldNames[PstRecrApplication.FLD_FULL_NAME] + " LIKE '%" + fullname + "%' ";
    }
    if ((department != null) && (department.compareToIgnoreCase("0") != 0) && (department.length() > 0)) {
        whereClause3 = PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_DEPARTMENT_ID] + " = " + department + " ";
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
    //out.println(whereClause);

    if (whereClause.length() > 0) {
        whereClause += " AND "; }

    whereClause += PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_DEPARTMENT_ID] + " > 0 ";

    System.out.println("whereClause = " + whereClause);
    int start = 0;
    int recordToGet = 0;
    String orderClause = "";
    Vector listEmp = new Vector(1,1);
    listEmp = PstRecrApplication.list(start, recordToGet, whereClause, orderClause);
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
    function cmdEdit(oid, fullname, department, position, commencing_date) {
        //alert(oid + " " + number + " " + fullname);
        self.opener.document.frm_orichecklist.<%=FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_RECR_APPLICATION_ID]%>.value = oid;
        self.opener.document.frm_orichecklist.FULLNAME.value = fullname;
        self.opener.document.frm_orichecklist.DEPARTMENT.value = department;
        self.opener.document.frm_orichecklist.POSITION.value = position;
        self.opener.document.frm_orichecklist.COMM_DATE.value = commencing_date;
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
                      <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="22"> 
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
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>

</body>
</html>
