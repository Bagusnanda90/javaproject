<%-- 
    Document   : template_header
    Created on : Jul 29, 2013, 2:31:38 PM
    Author     : user
--%>

<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.aplikasi.entity.mastertemplate.TempDinamis"%>
<%@page import="com.dimata.aplikasi.entity.mastertemplate.PstTempDinamis"%>
<%@page import="com.dimata.aplikasi.entity.picturecompany.PictureCompany"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.aplikasi.entity.picturecompany.PstPictureCompany"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<!DOCTYPE html-->
<%
    String userAgent = request.getHeader("User-Agent");
boolean isMSIE = (userAgent!=null && userAgent.indexOf("MSIE")!=-1);
%>
<script>
    function changePicture() {
        //window.open("<//%=approot%>/styletemplate/picture_company.jsp? height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");

        window.open("<%=approot%>/styletemplate/picture_company.jsp?oidCompanyPic=" + "<%=pictureCompany != null && pictureCompany.getOID() != 0 ? pictureCompany.getOID() : 0%>",
        "upload_Image", "height=550,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");

    }
</script>
<style type="text/css">
    .header1{
        background-color: <%=header1%>;

    }

</style>
<tr class="header1"><!-- style="background-color: <//%=header1%>"-->
    <td colspan="5"   style="color: <%=warnaFont%>; border-bottom: 1px solid <%=garis1%> "><!-- garis border bawah-->
        <table align="right" height="20px">
            <tr> 
                <%if(!strInformation.equals("stop")){%>
                
                    <td><div align="center"><strong><font color="#f3e007" size="3" face="Arial, Helvetica, sans-serif" ID=WGS> Time : <%=com.dimata.util.Formater.formatDate(new Date(), "HH:mm") %> </font><strong></div></td>   
                <%}%>
                
                <td  style="color: <%=warnaFont%>" align="right"><%=Formater.formatDate(new Date(), "EE,dd-MM-yyyy HH:mm")%> |  <a href="#" title="user"  style="color: <%=warnaFont%>"><%=userIsLogin.toLowerCase()%></a></td>
            </tr>
        </table>    
    </td>
</tr>

<%
        if(!strInformation.equals("stop")){
        %>
    <SCRIPT LANGUAGE="JavaScript">
        var msg = "<%=strInformation%>";
    var speeds=1000;
    var visible=0;
    function Flashs() {
        if (visible == 0) {
            document.all.WGS.innerHTML = msg;
            visible=1;
        } else {
            document.all.WGS.innerHTML = "&nbsp;";
            visible=0;
        }
        setTimeout('Flashs()', speeds);
    }
    Flashs();
    </SCRIPT>
    <%}%>