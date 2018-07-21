 
<%@ page language="java" %>
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        
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
        
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Overtime 
                  Index <!-- #EndEditable --> </strong></font> </td>
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="form1" method="post" action="">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td class="listtitle">Overtime Type</td>
                                        </tr>
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="300" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <tr align="center"> 
                                                <td width="21%" class="listgentitle">Kode</td>
                                                <td width="28%" class="listgentitle">Name</td>
                                                <td width="51%" class="listgentitle">Description</td>
                                                <td colspan="2" class="listgentitle" nowrap>Normal 
                                                  Work</td>
                                                <td width="51%" class="listgentitle" nowrap>Overwritten</td>
                                              </tr>
                                              <tr align="center"> 
                                                <td width="21%" class="listgentitle">&nbsp;</td>
                                                <td width="28%" class="listgentitle">&nbsp;</td>
                                                <td width="51%" class="listgentitle">&nbsp;</td>
                                                <td width="51%" class="listgentitle" nowrap>start 
                                                  time </td>
                                                <td width="51%" class="listgentitle" nowrap>End 
                                                  Time </td>
                                                <td width="51%" class="listgentitle" nowrap>by 
                                                  Schedule</td>
                                              </tr>
                                              <tr> 
                                                <td width="21%" nowrap align="center"><a href="#OVINDEX">WD1</a></td>
                                                <td width="28%" nowrap align="center"> 
                                                  Working Days</td>
                                                <td width="51%" align="center">Monday 
                                                  to Friday</td>
                                                <td width="51%" align="center">09:00</td>
                                                <td width="51%" align="center">18:00</td>
                                                <td width="51%" align="center"> 
                                                  <input type="checkbox" name="checkbox" value="checkbox" checked>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="21%" nowrap align="center">WD2</td>
                                                <td width="28%" nowrap align="center"> 
                                                  Working Days</td>
                                                <td width="51%" align="center">Saturday</td>
                                                <td width="51%" align="center">09:00</td>
                                                <td width="51%" align="center">14:00</td>
                                                <td width="51%" align="center"> 
                                                  <input type="checkbox" name="checkbox2" value="checkbox" checked>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="21%" nowrap align="center">HLD</td>
                                                <td width="28%" nowrap align="center">Holidays</td>
                                                <td width="51%" align="center">Sunday 
                                                  and holidays depend on religion 
                                                  of employee</td>
                                                <td width="51%" align="center">00:00</td>
                                                <td width="51%" align="center">00:00</td>
                                                <td width="51%" align="center"> 
                                                  <input type="checkbox" name="checkbox22" value="checkbox" checked>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="21%" nowrap align="center"> 
                                                  <input type="text" name="textfield4" size="8">
                                                </td>
                                                <td width="28%" nowrap align="center"> 
                                                  <select name="select2">
                                                    <option>Working Day</option>
                                                    <option>Holiday</option>
                                                  </select>
                                                </td>
                                                <td width="51%" align="center"> 
                                                  <input type="text" name="textfield2" size="32">
                                                </td>
                                                <td width="51%" align="center">
                                                  <input type="text" name="textfield43" size="8">
                                                </td>
                                                <td width="51%" align="center">
                                                  <input type="text" name="textfield44" size="8">
                                                </td>
                                                <td width="51%" align="center">
                                                  <input type="checkbox" name="checkbox3" value="checkbox">
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle">Overtime Index 
                                            for : <font size="3">WD1 </font></td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <table width="500" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <tr align="center"> 
                                                <td colspan="2" class="listgentitle"><a name="OVINDEX"></a>Overtime 
                                                  Lenght </td>
                                                <td width="28%" class="listgentitle">Overtime</td>
                                              </tr>
                                              <tr align="center"> 
                                                <td width="8%" class="listgentitle">&lt;= 
                                                  From </td>
                                                <td width="8%" class="listgentitle">&lt; 
                                                  To </td>
                                                <td width="28%" class="listgentitle">Indexes</td>
                                              </tr>
                                              <tr> 
                                                <td width="8%" nowrap align="center">1</td>
                                                <td width="8%" nowrap align="center"> 
                                                  2 </td>
                                                <td width="28%" align="center"> 
                                                  1.5</td>
                                              </tr>
                                              <tr> 
                                                <td width="8%" nowrap align="center">2</td>
                                                <td width="8%" nowrap align="center">Up</td>
                                                <td width="28%" align="center">2</td>
                                              </tr>
                                              <tr> 
                                                <td width="8%" nowrap align="center"> 
                                                  <input type="text" name="textfield42" size="8">
                                                </td>
                                                <td width="8%" nowrap align="center"> 
                                                  <input type="text" name="textfield3" size="10">
                                                </td>
                                                <td width="28%" align="center"> 
                                                  <input type="text" name="textfield22" size="24">
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    <!-- #EndEditable --> </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
