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
 boolean mnuChangeTemplate = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_HEADER_MODIF, AppObjInfo.G2_MENU_HEADER_MODIF_TEMPLATE, AppObjInfo.OBJ_MENU_CHANGE_MENU), 
             AppObjInfo.COMMAND_VIEW));
boolean mnuChangePictureCompany = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_HEADER_MODIF, AppObjInfo.G2_MENU_HEADER_MODIF_TEMPLATE, AppObjInfo.OBJ_MENU_CHANGE_IMAGE_COMPANY), 
             AppObjInfo.COMMAND_VIEW)); 

String menuChangeTemplate="";
if(mnuChangeTemplate){
    menuChangeTemplate= "<a href=\"javascript:changePicture()\" title=\"change picture\"  style=\"color:"+warnaFont+"\">Change Picture</a> |";
}
String changePicture="";
if(mnuChangePictureCompany){
    changePicture = "<a href=\""+approot+"/styletemplate/chage_template.jsp\" title=\"modif template\"  style=\"color:\""+warnaFont+"\">Modif Template</a> |";
}
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
        <table align="right" height="<%=!strInformation.equals("stop")?"80px":"20px"%>">
            <tr> 
                <%if(!strInformation.equals("stop")){%>
                
                <td width="50%" ><strong><font color="#f3e007" size="5" face="Arial, Helvetica, sans-serif" ID=WGS> Time : <%=com.dimata.util.Formater.formatDate(new Date(), "HH:mm") %> </font><strong></td>   
                <%}%>
                
                <td  style="color: <%=warnaFont%>" align="right"><%=Formater.formatDate(new Date(), "EE,dd-MM-yyyy HH:mm")%> |  <a href="#" title="user"  style="color: <%=warnaFont%>"><%=userIsLogin.toLowerCase()%></a> | <%=menuChangeTemplate%> <%=changePicture%>   <a href="<%=approot%>/logout.jsp" title="logout"  style="color: <%=warnaFont%>">Logout </a></td>
            </tr>
        </table>    
    </td>
</tr>
<tr>
    <td valign="top" nowrap>
        <table cellspacing="1" cellpadding="0" style="background-color: <%=header2%>;" width="100%">
          
            <tr >
                <!--<td rowspan="2" height="70px" width="120px" >
            <center><img src="<//%=approot%>/imgstyle/logo.png" ></center>
                </td>-->
                <td rowspan="2" height="70px" width="160px" >
                    <img src="<%=approot%>/imgcompany/<%=pictureCompany == null || pictureCompany.getNamaPicture() == null ? "logo.png" : pictureCompany.getNamaPicture()%>" height="70px" width="160px">
                </td>
                <!--<td>
                    <font color="black" size="6">
                    <!--<span style="color: blue;" id="neonlight0">S</span><span style="color: blueviolet;" id="neonlight1">e</span><span style="color: pink;" id="neonlight2">l</span><span style="color: violet;" id="neonlight3">a</span><span style="color: yellowgreen;" id="neonlight4">m</span><span style="color: gold;" id="neonlight5">a</span><span style="color: graytext;" id="neonlight6">t</span><span style="color: aqua;" id="neonlight7"> </span><span style="color: crimson;" id="neonlight8">D</span><span style="color: blue;" id="neonlight9">a</span><span style="color: red;" id="neonlight10">t</span><span style="color: red;" id="neonlight11">a</span><span style="color: red;" id="neonlight12">n</span><span style="color: black;" id="neonlight13">g</span><span style="color: black;" id="neonlight14"></span>     -->
                <!--</td>-->
            </tr>
           
            <tr >
                <td nowrap valign="middle" ><!-- fungsinya agar tidak scroll ke bawah-->
                    <%
                        if (navigation != null && navigation.equalsIgnoreCase("menu_i") && !isMSIE) {
                    %>
                    <%@include file="../menumain/menu_i.jsp"%>  
                    <%} else if (navigation != null && navigation.equalsIgnoreCase("menu_ii")) {%>
                    <%@include file="../menumain/menu_ii.jsp"%>  
                    <% } else if (isMSIE || navigation != null && navigation.equalsIgnoreCase("menu_iii")) {%>
                    <%@include file="../menumain/menu_iii.jsp"%>
                    <%} else if (navigation != null && navigation.equalsIgnoreCase("menu_v")) {%>
                    <%@include file="../menumain/menu_v.jsp"%>  
                    <% } else if (navigation != null && navigation.equalsIgnoreCase("menu_vi")) {%>
                    <%@include file="../menumain/menu_vi.jsp"%> 
                    <% } else {%>
                    <%@include file="../menumain/menu_vii.jsp"%>
                    <% }%>
                </td>

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