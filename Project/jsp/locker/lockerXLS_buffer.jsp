<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.harisma.session.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>

<%@ include file = "../main/javainit.jsp" %>
<%
	try{
		session.removeValue("LIST_LOCKER");
	}catch(Exception e){}	
	
	SrcLocker srcLocker = new SrcLocker();
	try{
		srcLocker = (SrcLocker)session.getValue(SessLocker.SESS_SRC_LOCKER);
	}catch(Exception e){}	

	Vector vtlist = SessLocker.searchLocker(srcLocker, 0, 0);
	
	try{
		session.putValue("LIST_LOCKER",vtlist);
	}catch(Exception e){}	
%>
<script language="JavaScript">
	document.location="<%=approot%>/servlet/com.dimata.harisma.report.LockerListXLS?time=<%=System.currentTimeMillis()%>";
</script>
