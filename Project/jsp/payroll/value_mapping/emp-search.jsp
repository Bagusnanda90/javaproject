<%-- 
    Document   : emp-search
    Created on : Mar 18, 2016, 11:17:22 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.payroll.FrmValue_Mapping"%>
<%@ include file = "../../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_SYSTEM_PROPERTIES); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Vector listUser = new Vector();
    listUser = PstAppUser.listPartObj(0, 0, "", "");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Data</title>
        <style type="text/css">
            .tblStyle {border-collapse: collapse; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #user {
                background-color: #474747; 
                color: #EEE; padding: 5px 11px; 
                border-radius: 3px;
                
            }
            body {
                color:#373737;
                padding: 21px;
                font-family: sans-serif;
                background-color: #EEE;
            }
            input {
                padding: 5px 9px;
                border: 1px solid #CCC;
                color: #0099FF;
                border-radius: 3px;
            }
            #uname {
                color:#3498db;
                cursor: pointer;
            }
        </style>
        <script type="text/javascript">
            function loadList(user_name) {
                if (user_name.length == 0) { 
                    user_name = "0";
                } 
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "user_ajax.jsp?user_name=" + user_name, true);
                xmlhttp.send();
                
            }
            function prepare(){
                loadList("0");
            }
            function cmdGetItem(oid, empNum) {
                self.opener.document.frm.<%= FrmValue_Mapping.fieldNames[FrmValue_Mapping.FRM_FIELD_EMPLOYEE_ID] %>.value = oid;
                self.opener.document.getElementById("payroll_num").innerHTML = empNum;
                self.close();
            }
        </script>
    </head>
    <body onload="prepare()">
        <h1>User Data</h1>
        <div><input type="text" name="user_name" onkeyup="loadList(this.value)" placeholder="Type User Name..." size="51" /></div>
        <div>&nbsp;</div>
        <div id="div_respon"></div>
    </body>
</html>