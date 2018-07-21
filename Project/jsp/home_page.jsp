<%-- 
    Document   : home_page
    Created on : Dec 23, 2013, 2:27:10 PM
    Author     : Devin
--%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN); %>
<%@ include file = "main/checkuser.jsp" %>
<!DOCTYPE html>
<%
    String url= request.getParameter("menu");
    if(url!=null && url.length()>0){
        boolean cek = true;
    }
%>
<html>
    <head>
        <title>Employee</title>
        <link rel="stylesheet" type="text/css" href="<%=approot%>/stylesheets/general_home_style.css" />
        <link href="<%=approot%>/stylesheets/general_template_style.css" type="text/css" rel="stylesheet" />
        
         <script type='text/javascript' src="styletemplate/jquery.min.js"></script>
        <script type='text/javascript' src="styletemplate/menu_jquery.js"></script>
    </head>
    <body bgcolor="<%=bgColorBody%>">
        <!-- <table width="100%">
             <tr>
             <td bgcolor="#0066FF">
                 <table width="100%">
                         <tr>
                         <td>-->
        <!--<div id="BaseContainer">-->
          <%if(headerStyle){%>
            <%@include file="styletemplate/template_header.jsp" %>
         <%}%>  
            <div class="titlebig">News &amp; Event</div>
            <!-- <div id="Middlemenu">
                <div id="ContentBase">-->
            <table width="100%" >
                        <tr>
                            <td bgcolor="#0066FF">
                                 <table  width="100%"border="0" bgcolor="<%=listPictureBackground!=null && listPictureBackground.size()>0?bgColorBody:"white"%>" style="text-align: left; text-transform: capitalize;">
                                    <tr>
                                        <td nowrap="nowrap"  style="border:1px solid <%=garisContent%>">
                                            
                                            <%@include file="styletemplate/slide_template.jsp" %>
                                            
                                           
                                          
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                               <!-- untuk footer -->
                                <%@include file="styletemplate/footer.jsp" %>
                            </td>
                            
                        </tr>
                    </table>

    </body>
</html>