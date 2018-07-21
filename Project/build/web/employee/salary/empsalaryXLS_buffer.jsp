<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<%
	
	int start = FRMQueryString.requestInt(request, "start");
	int recordToGet = 10;
	
	try{
		session.removeValue("LIST_SALARY");
	}catch(Exception e){}	
	
	SrcEmpSalary srcEmpSalary = new SrcEmpSalary();
	try{
		srcEmpSalary = (SrcEmpSalary)session.getValue(SessEmpSalary.SESS_SRC_SALARY);
	}catch(Exception e){}	

	Vector listEmpSalary = SessEmpSalary.searchEmpSalary(srcEmpSalary, 0, 0);
	
	try{
		session.putValue("LIST_SALARY",listEmpSalary);
	}catch(Exception e){}	
%>
<script language="JavaScript">
	document.location="<%=approot%>/servlet/com.dimata.harisma.report.employee.EmployeeSalaryXLS?time=<%=System.currentTimeMillis()%>";
</script>
