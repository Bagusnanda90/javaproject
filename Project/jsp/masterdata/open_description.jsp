<%-- 
    Document   : open_description
    Created on : Jan 7, 2016, 10:37:42 AM
    Author     : Dimata 007
--%>

<%@page import="com.sun.org.apache.xml.internal.security.keys.content.SPKIData"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
long oidPosition = FRMQueryString.requestLong(request, "oid_position");
Position position = new Position();
if (oidPosition != 0){
    try {
        position = PstPosition.fetchExc(oidPosition);
    } catch(Exception e){
        System.out.println(""+e.toString());
    }
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Description</title>
        <style type="text/css">
            body {
                font-size: 12px;
                font-family: sans-serif;
                color: #474747;
                padding: 21px;
                background-color: #F5F5F5;
            }
        </style>
    </head>
    <body>
        
        <%
        if (oidPosition != 0){
            String[] description = new String[position.getDescription().length()];
            String[] strDes =  position.getDescription().split("");
            String stringDes = "";
            for(int i=0; i<description.length; i++){
                if (strDes[i].equals("\n")){
                    stringDes += "<br />";
                }
                stringDes += strDes[i];
            }
            %>
            <h1><%= position.getPosition() %> - Description</h1>
            <p><%= stringDes %></p>
            <%
        } else {
            %>
            <p>No Description</p>
            <%
        }
        %>
    </body>
</html>
