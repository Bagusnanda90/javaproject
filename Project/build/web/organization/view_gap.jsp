<%-- 
    Document   : view_gap
    Created on : Mar 20, 2016, 9:19:07 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.employee.ViewGapEmployee"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
long oidEmp = FRMQueryString.requestLong(request, "oid_emp");

ViewGapEmployee gapEmp = new ViewGapEmployee();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Gap</title>
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title-part {
                color:#0db2e1; 
                font-weight: bold; 
                font-size: 14px; 
                background-color: #FFF; 
                border-left: 1px solid #0099FF; 
                padding: 9px 18px;
                margin: 5px 0px;
            }

            body {
                font-family: sans-serif;
                font-size: 12px;
                background-color: #EEE;
                color:#474747;
                padding: 17px;
            }
            
        </style>
    </head>
    <body>
        <h1>View Gap :</h1>
        <%= gapEmp.drawGapEmployee(oidEmp) %>
    </body>
</html>
