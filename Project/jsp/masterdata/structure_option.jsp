<%-- 
    Document   : structure_option
    Created on : Aug 13, 2015, 7:20:48 PM
    Author     : Dimata 007
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String oid = request.getParameter("pos_id");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            h1 {
                background-color: #DDD;
                color:#FFF;
            }
            a {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
              text-decoration: none;
            }

            a:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
        </style>
        <script type="text/javascript">
            function cmdDetailPosition(){
                
            }
        </script>
    </head>
    <body>
        <p>
        <a href="struct_emp_detail.jsp?pos_id=<%=oid%>">Tampilkan rincian pejabat</a>
        </p>
        <p>
        <a href="struct_pos_detail.jsp?pos_id=<%=oid%>">Tampilkan rincian karyawan</a>
        </p>
    </body>
</html>
