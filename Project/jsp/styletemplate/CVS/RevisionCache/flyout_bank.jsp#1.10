<%-- 
    Document   : flyout
    Created on : Sep 18, 2013, 5:13:29 PM
    Author     : user
--%>

<%@page import="com.dimata.harisma.util.PrintFlyOut"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.admin.AppUser"%>
<%@page import="com.dimata.harisma.session.admin.SessUserSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.entity.template.Flyout"%>


<%if(true){%>
<!-- isMSIE -->
<link rel="stylesheet" href="../stylesheets/flayout_no_ie.css" type="text/css">
<%}else{%>
<style type="text/css">
    .content{position:relative; width:100%; 
/*background-color:white;
background-repeat: repeat-x;
padding-top:61px;*/
min-height: 400px;
height: 400px !important;
background-attachment: local;
 background-image:url(../stylesheets/images/background.png);
 background-repeat: no-repeat;
background-position: right bottom;
 /*background:url(../menuaplikasi/imgHeader/elemet%20grafis.png) no-repeat right ;*/
}
</style>
<link rel="stylesheet" href=""+approot+"/stylesheets/flayout_with_ie.css" type="text/css">
<%}%>
<%
/*
 * Description : mengambil user name pada AppUser
 * Date : 2015-01-20
 * Author : Hendra Putu
*/
SessUserSession userSessionn = (SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME);
AppUser appUserSess1 = userSessionn.getAppUser();
String namaUser1 = appUserSess1.getFullName();
I_Dictionary dictionaryD = userSession.getUserDictionary();
 dictionaryD.loadWord();

 PrintFlyOut.flyOutBank(isMSIEE, userSession, approot, out, url, dictionaryD, namaUser1);

%>