<%-- 
    Document   : position_form
    Created on : Feb 3, 2015, 4:42:13 PM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION);%>
<%@ include file = "../main/checkuser.jsp" %>

<%!
	public String drawList(Vector objectClass, long oidCompetency) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.setAreaStyle("customTable");
        ctrlist.addHeader("No", "7%");
        ctrlist.addHeader("Competency Name", "20%");

        /////////
        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ////////


        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;

        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            // membuat object WarningReprimandAyat berdasarkan objectClass ke-i
            
            Competency competency = (Competency) objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();

            no = no + 1;
            rowx.add("" + no);
            
            
            rowx.add(competency.getCompetencyName());
           

            lstData.add(rowx);
            // menambah ID ke list LinkData
            lstLinkData.add(String.valueOf(competency.getOID()));

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }
%>
<%
Vector listCompetency = new Vector(1, 1);
listCompetency = PstCompetency.list(0, 100, "", "");
%>
<html>
<head>
    <title>Competency Form</title>
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
                                                <tr>
                                                    <%if (listCompetency != null && listCompetency.size() > 0) {%>
                                                    <td>
                                                        <!-- untuk drawlist -->
                                                        <!-- membuat tabel yang ditulis dari method drowList(); -->
                                                        <%=drawList(listCompetency, 0)%>
                                                    </td>
                                
                                                    <%} else {%>
                                                    <td>
                                                        record not found
                                                    </td>
                                                    <%}%>
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
  <tr> 
    <td colspan="2" height="20" bgcolor="#15A9F5">
      <%@ include file = "../../main/footer.jsp" %>
    </td>
  </tr>
</table>

</body>
</html>