<%-- 
    Document   : listsystemproperties
    Created on : Dec 24, 2010, 1:21:37 PM
    Author     : ktanjana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import= "java.util.Properties" %>
<%@page import= "java.util.Enumeration" %>
<%@page import= "java.util.*" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
// Get a list of all the system properties and their values
// Not available in unsigned Applets, only applications and signed Applets.
Properties sysprops = System .getProperties();
for ( Enumeration e = sysprops.propertyNames(); e.hasMoreElements(); )
   {
   String key = (String)e.nextElement();
   String value = sysprops.getProperty( key );
   out.println( key + "=" + value +"<br>" );
   } // end for

System.out.println(System.);
%>
    </body>
</html>
