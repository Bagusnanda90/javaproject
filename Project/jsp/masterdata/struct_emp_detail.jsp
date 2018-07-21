<%-- 
    Document   : struct_emp_detail
    Created on : Aug 13, 2015, 11:41:28 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>
<%
String oid = request.getParameter("pos_id");
String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+oid;
Vector listEmployee = PstEmployee.list(0, 0, whereClause, "");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employees Detail</title>
        <style type="text/css">
            .box {
                color: #575757;
                font-family: sans-serif;
                font-size: 11px;
                text-align: center;
                background-color: #EEE;
                padding: 7px;
                margin: 5px;
                border-radius: 3px;
                border:1px solid #CCC;
                float: left;
            }
            #name {
                color: #1392e9;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <%
        if (listEmployee != null && listEmployee.size()>0){
            for(int i=0; i<listEmployee.size(); i++){
                Employee emp = (Employee)listEmployee.get(i);
                String position = "-";
                String division = "-";
                try {
                    Position pos = PstPosition.fetchExc(emp.getPositionId());
                    position = pos.getPosition();
                } catch(Exception e){
                    System.out.println("Position=>"+e.toString());
                }
                try {
                    Division div = PstDivision.fetchExc(emp.getDivisionId());
                    division = div.getDivision();
                } catch(Exception e){
                    System.out.println("Division=>"+e.toString());
                }
                %>
                <div class="box">
                    <img style="padding: 3px; background-color: #FFF;" width="64" src="<%=approot%>/images/photocv.jpg" />
                    <div id="name"><%=emp.getFullName()%></div>
                    <div>Position: <%=position%></div>
                    <div>Divisi: <%=division%></div>
                </div>
                <%
            }
        }
        %>        
    </body>
</html>
