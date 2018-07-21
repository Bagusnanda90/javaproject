<%-- 
    Document   : struct_pos_detail
    Created on : Aug 13, 2015, 3:57:11 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.masterdata.PositionDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionDivision"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>
<%
String oid = request.getParameter("pos_id");
long divisionId = 0;
String whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_POSITION_ID]+"="+oid;
Vector listPosDiv = PstPositionDivision.list(0, 0, whereClause, "");
if (listPosDiv != null && listPosDiv.size()>0){
    PositionDivision posDiv = (PositionDivision)listPosDiv.get(0);
    divisionId = posDiv.getDivisionId();
}
String whEmp = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
Vector listEmployee = PstEmployee.list(0, 0, whEmp, "");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail</title>
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
