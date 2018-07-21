 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Input 
                  Overtime<!-- #EndEditable --> </strong></font> </td>
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
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="4">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" width="18%"><b>Period 
                                            : 2007-03</b></td>
                                          <td height="13" width="4%">&nbsp;</td>
                                          <td height="13" width="76%">&nbsp;</td>
                                          <td height="13" width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="4">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%" height="144">&nbsp;</td>
                                          <td valign="top" colspan="4" height="144"> 
                                            <table width="800" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <tr align="center"> 
                                                <td width="3%" class="listgentitle" nowrap>No.</td>
                                                <td width="6%" class="listgentitle" nowrap>Pay.Nr 
                                                  <br>
                                                </td>
                                                <td width="15%" class="listgentitle">Name 
                                                  <br>
                                                </td>
                                                <td width="6%" class="listgentitle">Position</td>
                                                <td width="8%" class="listgentitle">Work 
                                                  Date</td>
                                                <td width="7%" class="listgentitle" nowrap>Schedule</td>
                                                <td width="8%" class="listgentitle" nowrap> 
                                                  Start Time</td>
                                                <td width="7%" class="listgentitle" nowrap>End 
                                                  Time </td>
                                                <td width="6%" class="listgentitle">Duration</td>
                                                <td width="9%" class="listgentitle" nowrap>OV-Form 
                                                  Nr </td>
                                                <td width="4%" class="listgentitle" nowrap>Idx 
                                                  1 </td>
                                                <td width="5%" class="listgentitle" nowrap>Idx 
                                                  1.5 </td>
                                                <td width="4%" class="listgentitle" nowrap>Idx 
                                                  2 </td>
                                                <td width="4%" class="listgentitle" nowrap>Idx 
                                                  3 </td>
                                                <td width="8%" class="listgentitle" nowrap>Idx 
                                                  4 </td>
                                                <td width="37%" class="listgentitle" nowrap>Total 
                                                  Idx </td>
                                              </tr>
                                              <tr> 
                                                <td width="3%" nowrap align="center">1</td>
                                                <td width="6%" nowrap align="center">2000-02<br>
                                                </td>
                                                <td width="15%" nowrap align="center">I 
                                                  Gede Bagus</td>
                                                <td width="6%" nowrap align="center">Supervisor</td>
                                                <td width="8%" nowrap align="center">2007-03-01<br>
                                                </td>
                                                <td width="7%" align="center">M9</td>
                                                <td width="8%" align="center">18:00</td>
                                                <td width="7%" align="center"> 
                                                  23:30</td>
                                                <td width="6%" align="center"> 
                                                  5.5</td>
                                                <td width="9%" align="center" nowrap> 
                                                  OV-08-001 </td>
                                                <td width="4%" align="center">&nbsp; 
                                                </td>
                                                <td width="5%" align="center">2</td>
                                                <td width="4%" align="center">3.5</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">10</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%" nowrap align="center">2</td>
                                                <td width="6%" nowrap align="center">2007-03<br>
                                                </td>
                                                <td width="15%" nowrap align="center">Ni 
                                                  Luh Putu Ayu</td>
                                                <td width="6%" nowrap align="center">Staff</td>
                                                <td width="8%" nowrap align="center">2007-03-01<br>
                                                </td>
                                                <td width="7%" align="center">M9</td>
                                                <td width="8%" align="center">18:00</td>
                                                <td width="7%" align="center">20:30</td>
                                                <td width="6%" align="center">2.5</td>
                                                <td width="9%" align="center" nowrap> 
                                                  OV-08-002 </td>
                                                <td width="4%" align="center">&nbsp; 
                                                </td>
                                                <td width="5%" align="center">2</td>
                                                <td width="4%" align="center">0.5</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">4</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%" nowrap align="center">3</td>
                                                <td width="6%" nowrap align="center">2007-02<br>
                                                </td>
                                                <td width="15%" nowrap align="center">I 
                                                  Gede Bagus</td>
                                                <td width="6%" nowrap align="center">Supervisor</td>
                                                <td width="8%" nowrap align="center">2007-03-02<br>
                                                </td>
                                                <td width="7%" align="center">A3</td>
                                                <td width="8%" align="center">13:00</td>
                                                <td width="7%" align="center"> 
                                                  15:00</td>
                                                <td width="6%" align="center"> 
                                                  2.0</td>
                                                <td width="9%" align="center" nowrap> 
                                                  OV-08-003 </td>
                                                <td width="4%" align="center">2</td>
                                                <td width="5%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">2</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%" nowrap align="center" height="32">&nbsp;</td>
                                                <td width="6%" nowrap align="center" height="32">&nbsp;</td>
                                                <td width="15%" nowrap align="center" height="32">&nbsp;</td>
                                                <td width="6%" nowrap align="center" height="32">&nbsp;</td>
                                                <td width="8%" nowrap align="center" height="32">&nbsp;</td>
                                                <td width="7%" align="center" height="32">&nbsp;</td>
                                                <td width="8%" align="center" height="32">&nbsp;</td>
                                                <td width="7%" align="center" height="32">&nbsp;</td>
                                                <td width="6%" align="center" height="32">&nbsp;</td>
                                                <td width="9%" align="center" height="32">&nbsp;</td>
                                                <td width="4%" align="center" height="32">&nbsp;</td>
                                                <td width="5%" align="center" height="32">&nbsp;</td>
                                                <td width="4%" align="center" height="32">&nbsp;</td>
                                                <td width="4%" align="center" height="32">&nbsp;</td>
                                                <td width="8%" align="center" height="32">&nbsp;</td>
                                                <td width="8%" align="center" height="32">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%" nowrap align="center">&nbsp;</td>
                                                <td width="6%" nowrap align="center">&nbsp;</td>
                                                <td width="15%" nowrap align="center">&nbsp;</td>
                                                <td width="6%" nowrap align="center">&nbsp;</td>
                                                <td width="8%" nowrap align="center">&nbsp;</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="9%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="5%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%" nowrap align="center">&nbsp;</td>
                                                <td width="6%" nowrap align="center">&nbsp;</td>
                                                <td width="15%" nowrap align="center">&nbsp;</td>
                                                <td width="6%" nowrap align="center">&nbsp;</td>
                                                <td width="8%" nowrap align="center">&nbsp;</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="9%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="5%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                                <td width="8%" align="center">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td width="18%" nowrap> 
                                            <input type="checkbox" name="checkbox" value="checkbox">
                                            Recalculate Index lembur on Posting 
                                          </td>
                                          <td width="4%">&nbsp;</td>
                                          <td width="76%"><a href="#">DO POSTING 
                                            OVERTIME</a></td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle" width="0%">&nbsp;</td>
                                          <td class="listtitle" colspan="4">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4">&nbsp;</td>
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
