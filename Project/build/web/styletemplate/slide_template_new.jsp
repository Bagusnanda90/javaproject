<%-- 
    Document   : slide_template_new
    Created on : Oct 29, 2015, 3:57:25 PM
    Author     : Hendra Putu
--%>

<%@page import="org.jfree.ui.about.SystemProperties"%>

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


                                            
        <div> <!--style="border:1px solid <//S%=garisContent%>"-->
            <%
            if (headerStyle) {

                if (menuHeader.equals("HR") ){
                %>
                    <%@include file="../styletemplate/flyout_hr.jsp" %> 
                <%
                } else if (menuHeader.equals("BANK") ){
                %>
                    <%@include file="../styletemplate/flyout_bank.jsp" %> 
                <%   
                } else {
                %>
                    <%@include file="../styletemplate/flyout_hr.jsp" %> 
                <%   
                }
            }
            %>
        </div>
