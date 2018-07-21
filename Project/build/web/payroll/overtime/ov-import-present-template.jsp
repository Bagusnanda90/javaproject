 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Import 
                  Overtime from Presences<!-- #EndEditable --> </strong></font> 
                </td>
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
                                          <td height="13">&nbsp;</td>
                                          <td height="13">Division : 
                                            <select name="select9">
                                              <option selected>- SELECT -</option>
                                              <option>Local Fish Product </option>
                                              <option>Export Fish Product</option>
                                            </select>
                                            Department : 
                                            <select name="select10">
                                              <option selected>- SELECT - </option>
                                              <option>Marketing</option>
                                              <option>Warehouse</option>
                                            </select>
                                            Section 
                                            <select name="select11">
                                              <option selected>- ALL -</option>
                                              <option>Receiving</option>
                                              <option>Stock maintenance</option>
                                            </select>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td height="13">&nbsp;</td>
                                          <td height="13">Period : 2007-03 </td>
                                        </tr>
                                        <tr> 
                                          <td height="13">&nbsp;</td>
                                          <td height="13">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td valign="top"> 
                                            <table width="800" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <tr align="center"> 
                                                <td width="2%" class="listgentitle" nowrap rowspan="2">No.</td>
                                                <td width="5%" class="listgentitle" nowrap rowspan="2">Pay.Nr 
                                                  <br>
                                                </td>
                                                <td width="10%" class="listgentitle" rowspan="2">Name 
                                                  <br>
                                                </td>
                                                <td width="7%" class="listgentitle" rowspan="2">Position</td>
                                                <td width="10%" class="listgentitle" rowspan="2">Work 
                                                  Date</td>
                                                <td width="6%" class="listgentitle" nowrap rowspan="2">Schedule</td>
                                                <td colspan="6" class="listgentitle" nowrap>Overtime</td>
                                                <td width="23%" class="listgentitle" rowspan="2">Transfer 
                                                  ? </td>
                                              </tr>
                                              <tr align="center"> 
                                                <td width="9%" class="listgentitle" nowrap>Start 
                                                  Date </td>
                                                <td width="3%" class="listgentitle" nowrap> 
                                                  Time</td>
                                                <td width="6%" class="listgentitle" nowrap>End 
                                                  Date </td>
                                                <td width="3%" class="listgentitle" nowrap> 
                                                  Time </td>
                                                <td width="6%" class="listgentitle">Duration</td>
                                                <td width="10%" class="listgentitle">OV-Form 
                                                  Nr </td>
                                              </tr>
                                              <tr> 
                                                <td width="2%" nowrap align="center">1</td>
                                                <td width="5%" nowrap align="center">2000-02<br>
                                                </td>
                                                <td width="10%" nowrap align="center">I 
                                                  Gede Bagus</td>
                                                <td width="7%" nowrap align="center">Supervisor</td>
                                                <td width="10%" nowrap align="center">2007-03-01<br>
                                                </td>
                                                <td width="6%" align="center">M9</td>
                                                <td width="9%" align="center" nowrap>2007-03-01</td>
                                                <td width="3%" align="center">18:00</td>
                                                <td width="6%" align="center" nowrap>2007-03-01</td>
                                                <td width="3%" align="center"> 
                                                  23:30</td>
                                                <td width="6%" align="center"> 
                                                  5.5</td>
                                                <td width="10%" align="center"> 
                                                  <input type="text" name="textfield" size="15" value="OV-08-001">
                                                </td>
                                                <td width="23%" align="center"> 
                                                  <input type="checkbox" name="checkbox" value="checkbox">
                                                  yes </td>
                                              </tr>
                                              <tr> 
                                                <td width="2%" nowrap align="center">2</td>
                                                <td width="5%" nowrap align="center">2007-03<br>
                                                </td>
                                                <td width="10%" nowrap align="center">Ni 
                                                  Luh Putu Ayu</td>
                                                <td width="7%" nowrap align="center">Staff</td>
                                                <td width="10%" nowrap align="center">2007-03-01<br>
                                                </td>
                                                <td width="6%" align="center">M9</td>
                                                <td width="9%" align="center">2007-03-01</td>
                                                <td width="3%" align="center">18:00</td>
                                                <td width="6%" align="center">2007-03-01</td>
                                                <td width="3%" align="center">20:30</td>
                                                <td width="6%" align="center">2.5</td>
                                                <td width="10%" align="center"> 
                                                  <input type="text" name="textfield2" size="15" value="OV-08-002">
                                                </td>
                                                <td width="23%" align="center"> 
                                                  <input type="checkbox" name="checkbox2" value="checkbox">
                                                  yes </td>
                                              </tr>
                                              <tr> 
                                                <td width="2%" nowrap align="center">3</td>
                                                <td width="5%" nowrap align="center">2000-02<br>
                                                </td>
                                                <td width="10%" nowrap align="center">I 
                                                  Gede Bagus</td>
                                                <td width="7%" nowrap align="center">Supervisor</td>
                                                <td width="10%" nowrap align="center">2007-03-02<br>
                                                </td>
                                                <td width="6%" align="center">A3</td>
                                                <td width="9%" align="center">2007-03-02</td>
                                                <td width="3%" align="center">13:00</td>
                                                <td width="6%" align="center">2007-03-02</td>
                                                <td width="3%" align="center"> 
                                                  15:00</td>
                                                <td width="6%" align="center"> 
                                                  2.0</td>
                                                <td width="10%" align="center"> 
                                                  <input type="text" name="textfield" size="15" value="OV-08-003">
                                                </td>
                                                <td width="23%" align="center"> 
                                                  <input type="checkbox" name="checkbox" value="checkbox">
                                                  yes </td>
                                              </tr>
                                              <tr> 
                                                <td width="2%" nowrap align="center">&nbsp;</td>
                                                <td width="5%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" nowrap align="center">&nbsp;</td>
                                                <td width="7%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="9%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="10%" align="center">&nbsp;</td>
                                                <td width="23%" align="center">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="2%" nowrap align="center">&nbsp;</td>
                                                <td width="5%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" nowrap align="center">&nbsp;</td>
                                                <td width="7%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="9%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="10%" align="center">&nbsp;</td>
                                                <td width="23%" align="center">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="2%" nowrap align="center">&nbsp;</td>
                                                <td width="5%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" nowrap align="center">&nbsp;</td>
                                                <td width="7%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="9%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="10%" align="center">&nbsp;</td>
                                                <td width="23%" align="center">
                                                  <input type="submit" name="Submit" value="Check All">
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td> <a href="ov-input.jsp">Transfer 
                                            approved presence as overtime &gt;&gt; 
                                            </a> </td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle">&nbsp;</td>
                                          <td class="listtitle">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td>&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td>&nbsp;</td>
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
