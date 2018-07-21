<%if(viewMinimum){%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" height="45">&nbsp;</td>
    <td align="center" height="45">
        <b><font color="#FFFFFF"><i><font size="4">
        <%if(!strInformation.equals("stop")){%>
        
        <div align="center"><strong><font color="#f3e007" size="3" face="Arial, Helvetica, sans-serif" ID=WGS> Time : <%=com.dimata.util.Formater.formatDate(new Date(), "HH:mm") %> </font><strong></div>
        
        <%}else{%>
        HUMAN RESOURCES MANAGEMENT SYSTEM
        <%}%>
        </font></i></font></b>
    </td>
    <td align="right" height="45">&nbsp;</td>
  </tr>
</table>
<%}else{%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" background="<%=approot%>/images/harismaBg2.jpg"><img src="<%=approot%>/images/harismaLeft2.gif" width="50%" height="54"></td>
    <!-- <td align="left" background="<%//=approot%>/images/harismaBg2.jpg"><img src="<%//=approot%>/images/harismaLeft2.gif" width="180" height="54"></td> -->
    <%if(!strInformation.equals("stop")){%>
    <td align="center" background="<%=approot%>/images/harismaBg2.jpg"><b><font color="#FFFFFF"><i><font size="4">
      <div align="center"><strong><font color="#f3e007" size="4" face="Arial, Helvetica, sans-serif" ID=WGS> Time : <%=com.dimata.util.Formater.formatDate(new Date(), "HH:mm") %> </font><strong></div>
        
        </font></i></font></b></td>
   <%}else{%>
         <td align="center" background="<%=approot%>/images/harismaBg2.jpg"><img src="<%=approot%>/images/harismaMiddle2.gif" width="50%" height="54"></td>
         <!-- <td align="center" background="<%=approot%>/images/harismaBg2.jpg"><img src="<%=approot%>/images/harismaMiddle2.gif" width="395" height="54"></td>-->
    <%}%>
    <td align="right" background="<%=approot%>/images/harismaBg2.jpg"><img src="<%=approot%>/images/harismaRight2.gif" width="50%" height="54"></td>
    <!-- <td align="right" background="<%//=approot%>/images/harismaBg2.jpg"><img src="<%//=approot%>/images/harismaRight2.gif" width="180" height="54"></td> -->
  </tr>
</table>
<%}%>

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