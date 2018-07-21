<%-- 
    Document   : dialog_division
    Created on : Apr 6, 2016, 4:09:26 PM
    Author     : Dimata 007
--%>
<%@ include file = "../../main/javainit.jsp" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String divisionName = FRMQueryString.requestString(request, "division_name");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            body {
                background-color: #EEE;
                margin: 0;
                padding: 0;
                color:#575757;
                font-size: 11px;
                font-family: sans-serif;
                background-color: #F5F5F5;
            }
            .tblStyle {border-collapse: collapse; font-size: 12px; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            
        </style>
        <script language="javascript">
            function cmdCount(val){
                var nilai = document.getElementById("count").innerHTML;
                var count = parseInt(nilai);
                var valCheck = document.getElementById("checklist"+val).checked;
                if (valCheck == true){
                    document.getElementById("div_item"+val).className="item-active";
                    count = count + 1;
                } else {
                    document.getElementById("div_item"+val).className="item";
                    count = count - 1;
                }
                
                document.getElementById("count").innerHTML=count;
            }
        </script>
    </head>
    <body>
        
        <form method="post" name="frm" action="">
            <h1 style="color:#575757; text-align: center;">Select Division</h1>
            <div style="text-align: center">
                <input type="text" name="division_name" size="50" placeholder="type division name..." />
            </div>
            <div>&nbsp;</div>
            <table class="tblStyle">
                <%
                
                %>
                <tr>
                    <td>No</td>
                    <td>Division Name</td>
                </tr>
            </table>            
        </form>
    </body>
</html>
