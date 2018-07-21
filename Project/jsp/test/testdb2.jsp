<%-- 
    Document   : testdb
    Created on : Oct 11, 2009, 10:15:52 PM
    Author     : Ketut Kartika T
--%>

<%@page contentType="text/html" pageEncoding="Shift_JIS"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
        <title>JSP Page</title>
    </head>
    <body> 
        <h2>TEST DB 2!</h2>
        <%=com.dimata.util.testDB.TestDB.testData()%>
    </body>
</html>
