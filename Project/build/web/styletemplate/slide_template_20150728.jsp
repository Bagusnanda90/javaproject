<%-- 
    Document   : slide_template
    Created on : Oct 10, 2013, 2:32:42 PM
    Author     : user
--%>

<%@page import="java.util.Vector"%>
<%@page import="com.dimata.aplikasi.entity.uploadpicture.PstPictureBackground"%>
<%@page import="com.dimata.aplikasi.entity.uploadpicture.PictureBackground"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String picture = "";
    if (listPictureBackground != null && listPictureBackground.size() > 0) {
        pictureBackground = (PictureBackground) listPictureBackground.get(0);
        picture = "background-image:url(../imgupload/" + pictureBackground.getUploadPicture() + ")";
    }
%>
<style type="text/css">
    .contentBgPicture{

        position:relative;  min-height: 300px; 

        background-color:white;
        background-repeat: repeat-x;
        padding-top:61px;
        /*padding-left: 33px;*/
        /* background-image:url(../stylesheets/images/backgroundx.png);*/

        background-repeat: no-repeat;
        background-position: center;
        <%=picture%>
    }

</style>
<tr>
    <td>
        <table  width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td > 
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" style="background-color:<%=garisContent%>">
                        <tr> 
                            <td valign="top"> 
                                <table width="100%" border="0" cellspacing="1" cellpadding="1"  bgcolor="<%=bgColorContent%>">
                                    <tr> 
                                        <td valign="top">
                                            
                                            <div <%=listPictureBackground != null && listPictureBackground.size() > 0?"class=\"contentBgPicture\"":"class=\"content\"" %>> <!--style="border:1px solid <//S%=garisContent%>"-->
                                                <%if (headerStyle) {%> 
                                                <%@include file="../styletemplate/flyout.jsp" %> 
                                                <%}%>
                                            </div>
                                           
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



