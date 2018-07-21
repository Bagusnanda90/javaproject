<%-- 
    Document   : user_data
    Created on : Feb 18, 2016, 12:50:12 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_SYSTEM_PROPERTIES); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Vector listModul = new Vector();
    listModul = PstLogSysHistory.listModule(0, 0, "", "");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modul Data</title>
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
            function loadList(modul_name) {
                if (modul_name.length == 0) { 
                    modul_name = "0";
                } 
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "modul_ajax.jsp?modul_name=" + modul_name, true);
                xmlhttp.send();
                
            }
            function prepare(){
                loadList("0");
            }
            function cmdGetItem(modul_name) {
                self.opener.document.frm.modul.value = modul_name;
                self.opener.document.getElementById("modul-span").innerHTML = modul_name;
                self.opener.document.getElementById("closemodule-x").innerHTML = "<span id=\"closemodule\" onclick=\"javascript:cmdDelModule()\">&times;</span>";
                self.close();
            }
        </script>
    </head>
    <body onload="prepare()">
        <h1>Modul Data</h1>
        <div><input type="text" name="modul_name" onkeyup="loadList(this.value)" placeholder="Type User Name..." size="51" /></div>
        <div>&nbsp;</div>
        <div id="div_respon"></div>
    </body>
</html>
