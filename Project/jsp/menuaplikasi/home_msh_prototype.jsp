<%--
    Document   : employee
    Created on : Jul 29, 2013, 2:16:39 PM
    Author     : user
--%>

<%@page import="com.dimata.aplikasi.entity.uploadpicture.PictureBackground"%>
<%@page import="com.dimata.aplikasi.entity.uploadpicture.PstPictureBackground"%>
<%@page import="com.dimata.aplikasi.form.mastertemplate.CtrlTempDinamis"%>
<%@page import="com.dimata.aplikasi.entity.mastertemplate.PstTempDinamis"%>
<%@page import="com.dimata.aplikasi.entity.mastertemplate.TempDinamis"%>
<%@page import="java.util.ResourceBundle.Control"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.aplikasi.form.mastertemplate.FrmTempDinamis"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN); %>
<%@ include file = "../main/checkuser.jsp" %>

<%
    String url = request.getParameter("menu");
    if (url != null && url.length() > 0) {
        boolean cek = true;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>Home</title>
           <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="<%=approot%>/stylesheets/general_home_style.css" />
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
        <link rel="stylesheet" href="<%=approot%>/styles/form.css" type="text/css">
        
        <script type='text/javascript' src="<%=approot%>/styletemplate/jquery.min.js"></script>
        <script type='text/javascript' src="<%=approot%>/styletemplate/menu_jquery.js"></script>
    </head>

    <body bgcolor="<%=bgColorBody%>" <%=verTemplate.equalsIgnoreCase("0")%> leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">      
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" <%=headerStyle ? "" : "bgcolor=\"#F9FCFF\""%> >
            <%@include file="../styletemplate/template_header.jsp" %>

            <tr> 
                <td width="100%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
                        <tr> 
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
                                    <tr> 
                                        <td height="20"></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table  width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td > 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1"  bgcolor="<%=bgColorContent%>">
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                <%@include file="../styletemplate/slide_template.jsp" %>
                                                                                
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td> 

                                    </tr>

                                </table>
                            </td> 
                        </tr>
                    </table>
                </td> 
            </tr>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    
                </td>
            </tr>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
