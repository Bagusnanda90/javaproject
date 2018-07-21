<%@ page language="java" %>
<%@ include file = "../main/javainit.jsp" %>
<!-- JSP Block -->

<!-- End of JSP Block -->  
 
<html>
<!-- DW6 -->
<head>
  
<title>HARISMA - </title>
 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
 
<link rel="stylesheet" href="styles/main.css" type="text/css"> 

 
<link rel="stylesheet" href="styles/tab.css" type="text/css"> 


<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }
	
	function showObjectForMenu(){
        //document.all.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT> 
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="20%" height="54"><img src="images/harismaLeft1.jpg" width="220" height="59"> 
      <%@ include file = "../main/header.jsp" %></td>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="67%"><div align="center"><strong><font color="#FF6600" face="Arial">Welcome to Dimata Harisma </font></strong></div></td>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="13%"><img src="images/harismaRight2.jpg" width="180" height="54"></td>
  </tr> 
  <tr> 
    <td height="15" colspan="3" valign="middle"  bgcolor="#9BC1FF" ID="MAINMENU">  
      <%@ include file = "../main/mnmain.jsp" %>      </td> 
  </tr>
  <tr> 
    <td height="10" colspan="3" valign="middle"  bgcolor="#9BC1FF"> 
		
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
			<td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
			<td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
	    </tr>
	  </table>	</td> 
  </tr>
  <tr> 
    <td colspan="3" align="left" valign="top"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">    	        </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">		    				  &nbsp;{content} </td>
                          </tr>
                        </table>                      </td>
                    </tr>
                  </table>                </td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
              </tr>
            </table>		  </td> 
        </tr>
      </table>		  </td> 
        </tr>
      </table>    </td> 
  </tr>
  <tr> 
    <td colspan="4" height="20" bgcolor="#9BC1FF"> 
      <%@ include file = "../main/footer.jsp" %>      </td>
  </tr>
</table>
</body>
{script}
</html>
