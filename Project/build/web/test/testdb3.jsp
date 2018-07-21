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
        <h2>TEST DB !</h2>
        <p>&nbsp;</p>		
        <p>
          <%
      String driver= "sun.jdbc.odbc.JdbcOdbcDriver";
      String dburl = "AttCompactSeriesTransfer";
      String dbuser = "";
      String dbpass = "";
      String sqlQuery="";

  try{
      driver= com.dimata.qdep.form.FRMQueryString.requestString(request, "driver");
      dburl = com.dimata.qdep.form.FRMQueryString.requestString(request, "dburl");
      dbuser = com.dimata.qdep.form.FRMQueryString.requestString(request, "dbuser");
      dbpass = com.dimata.qdep.form.FRMQueryString.requestString(request, "dbpass");
      sqlQuery= com.dimata.qdep.form.FRMQueryString.requestString(request, "sql");


        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }        
        
        Connection conn = null;
// assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;

        try {
            
            conn = DriverManager.getConnection(""+dburl+"?" + "user="+dbuser+"&password="+dbpass);
        // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            out.println("SQLException: " + ex.getMessage());
            out.println("SQLState: " + ex.getSQLState());
            out.println("VendorError: " + ex.getErrorCode());
        }

        try {
            stmt = conn.createStatement();
            //rs = stmt.executeQuery("SELECT foo FROM bar");
            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...
            if (stmt.execute( sqlQuery)) {
                rs = stmt.getResultSet();
            }
        // Now do something with the ResultSet ....
            String str = "";
            while(rs.next()){
                out.print(rs.getString(1));
                out.println(rs.getString(2));
            }
            
        } catch (SQLException ex) {
            // handle any errors
            out.println("SQLException: " + ex.getMessage());
            out.println("SQLState: " + ex.getSQLState());
            out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }
                

  } catch(Exception exc){
      out.println(exc);
  }

%>

<form name="form1" method="post" action="testdb3.jsp">
        <input name="sql"    type="text" value="<%=((sqlQuery==null || sqlQuery.length()<2)?"select * from ":sqlQuery)%>" size="90">
        <p>driver
          <input name="driver"    type="text" value="<%=((driver==null || driver.length()<2)?"com.mysql.jdbc.Driver":driver)%>" size="90">
 </p>
        <p>url
          <input name="dburl" type="text" value="<%=((dburl==null || dburl.length()<2)?"jdbc:mysql://localhost/":dburl) %>""  size="90">
        </p>
        <p>user
          <input name="dbuser" type="text" value="" size="50">
        </p>
        <p>paswd&nbsp;
          <input name="dbpass" type="text" value="" size="50">
        </p>
        <p>
          <input type="submit" name="Submit" value="Submit">
        </p>
		</form>

      

            </p>
    </body>
</html>
