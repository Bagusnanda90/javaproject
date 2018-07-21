<%-- 
    Document   : footer
    Created on : Dec 20, 2013, 12:08:31 PM
    Author     : Devin
--%>
<%
    //cek tipe browser, browser detection
    //String userAgents = request.getHeader("User-Agent");
    //boolean isMSIEE = (userAgents!=null && userAgents.indexOf("MSIE") !=-1); 
%>
<style type="text/css">
    html, body {
width: 100%;
height: 100%;
margin: 0px;
padding: 0px;
}
</style>
<!--[if IE]>
    <style type='text/css'>
   input[type=text],[type=password] {
-webkit-transition: all 0.30s ease-in-out;
    -moz-transition: all 0.30s ease-in-out;
    -ms-transition: all 0.30s ease-in-out;
    -o-transition: all 0.30s ease-in-out;
    outline: none;
    padding: 10px 0px 3px 3px;
    margin: 2px 1px 3px 0px;
    border:1px solid #456879;
    border-radius:5px;

}

textarea {
    -webkit-transition: all 0.30s ease-in-out;
    -moz-transition: all 0.30s ease-in-out;
    -ms-transition: all 0.30s ease-in-out;
    -o-transition: all 0.30s ease-in-out;
    outline: none;
    padding: 3px 0px 3px 3px;
    margin: 5px 1px 3px 0px;
    border:1px solid #456879;
    border-radius:5px;
}

input[type=text]:focus,[type=password]:focus, textarea:focus {
    box-shadow: 0 0 5px rgba(81, 203, 238, 1);
    padding: 10px 0px 3px 3px;
    margin: 5px 1px 3px 0px;
    border: 1px solid rgba(81, 203, 238, 1);
    
}
    </style>
    <![endif]-->
<% if(isMSIEE){%>
 <script type="text/javascript">
//disable back function

function changeHashOnLoad() {
window.location.href += "#";
setTimeout("changeHashAgain()", "50");
}

function changeHashAgain() {
window.location.href +="1";
}

var storedHash = window.location.hash;
window.setInterval(function () {
if (window.location.hash != storedHash) {
window.location.hash = storedHash;
}
}, 50);

</script>    
    
<%}else{%>
<script type="text/javascript">
  location.hash='#no-';
if(location.hash == '#no-'){
location.hash='#_';
window.onhashchange=function(){
if(location.hash == '#no-')
location.hash='#_';}}


</script>
<%}%>
<SCRIPT type="text/javascript">
	window.history.forward();
	function noBack() { window.history.forward(); }
</SCRIPT>

 <!--<div id="FooterTemplate1" style="background-color: <//%=footerBg%>;">
    <div id="text" style="border-bottom:<//%=garisFooter%> 1px solid">
        <div style="color:<//%=warnaFont%>"><center>Copy Right Dimata IT Solution 2013</center></div>
</div>
<div id="gambar">
    
</div>

</div>-->
 <style>
     .hiddenTb{
         visibility: hidden;
         position: absolute;
        top: -10000px;
        left: -10000px;
     }
 </style>
<div style="position:fixed;bottom: 0;right:0;left:0">
<table width="100%"  border="0" cellpadding="0" cellspacing="0" style="background-color: <%=footerBg%>;">
  <tr>
      <td width="9%" rowspan="2" valign="top"><center><img src="<%=approot%>/menustylesheet/icon/logo_dimata.png" height="25px"></center></td>
    <td>
        <div style="padding: 7px;">
            <font color="<%=warnaFont%>"><center>Copyright &COPY; Dimata IT Solution 2015</center></font>
        </div>
    </td>
  </tr>
  <tr>
      <td class="hiddenTb">&nbsp;</td><!-- mencari  hidden="hidden" support IE -->
  </tr>
</table>
</div>
  