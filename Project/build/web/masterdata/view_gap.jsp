<%-- 
    Document   : view_gap
    Created on : Oct 30, 2015, 9:40:29 AM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.masterdata.Competency"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetency"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
long empId = FRMQueryString.requestLong(request, "oid");
String empName = "-";
String empNum = "-";
try {
    Employee emp = PstEmployee.fetchExc(empId);
    empName = emp.getFullName();
    empNum = emp.getEmployeeNum();
} catch(Exception e){
    System.out.println(e.toString());
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Gap</title>
        <style type="text/css">
            body {
                color: #575757;
                background-color: #EEE;
                margin: 0;
                padding: 0;
            }
            .tblStyle {border-collapse: collapse; background-color: #FFF;}
            .tblStyle td {
                font-size: 12px; font-family: sans-serif; color:#474747; 
                border: 1px solid #C7C7C7; padding: 3px 5px;
            }
            .container {
                padding: 21px;
                font-family: sans-serif;
            }
            h3 {color: #0096bb;}
        </style>
    </head>
    <body>
        <div class="container">
        <h1><%=empName%> | <%=empNum%></h1>
        <h3>Kompetensi</h3>
        <table class="tblStyle">
            <tr>
                <td colspan="2">
                    <strong>Yang dibutuhkan</strong>
                </td>
                <td colspan="2">
                    <strong>Yang dimiliki</strong>
                </td>
            </tr>
            <tr>
                <td>Caption</td>
                <td>Value</td>
                <td>Caption</td>
                <td>Value</td>
            </tr>
            <%
            Vector listCompetency = PstCompetency.list(0, 0, "", "");
            if (listCompetency != null && listCompetency.size()>0){
                for(int i=0; i<listCompetency.size(); i++){
                    Competency competency = (Competency)listCompetency.get(i);
            %>
            <tr>
                <td><%=competency.getCompetencyName()%></td>
                <td>Value</td>
                <td>Caption</td>
                <td>Value</td>
            </tr>
            <% 
                }
            } 
            %>
        </table>
        <h3>Pendidikan</h3>
        <table class="tblStyle">
            <tr>
                <td colspan="2">
                    <strong>Yang dibutuhkan</strong>
                </td>
                <td colspan="2">
                    <strong>Yang dimiliki</strong>
                </td>
            </tr>
            <tr>
                <td>Caption</td>
                <td>Value</td>
                <td>Caption</td>
                <td>Value</td>
            </tr>
        </table>
        <h3>Pelatihan</h3>
        <table class="tblStyle">
            <tr>
                <td colspan="2">
                    <strong>Yang dibutuhkan</strong>
                </td>
                <td colspan="2">
                    <strong>Yang dimiliki</strong>
                </td>
            </tr>
            <tr>
                <td>Caption</td>
                <td>Value</td>
                <td>Caption</td>
                <td>Value</td>
            </tr>
        </table>
        <h3>Key Performance Indicator</h3>
        <table class="tblStyle">
            <tr>
                <td colspan="2">
                    <strong>Yang dibutuhkan</strong>
                </td>
                <td colspan="2">
                    <strong>Yang dimiliki</strong>
                </td>
            </tr>
            <tr>
                <td>Caption</td>
                <td>Value</td>
                <td>Caption</td>
                <td>Value</td>
            </tr>
        </table>
        </div>
    </body>
</html>
