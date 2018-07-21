 
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
                                          <td height="13" colspan="4"> 
                                            <table width="60%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td height="13" width="5%" align="right">Division 
                                                  :</td>
                                                <td height="13" width="14%"> 
                                                  <select name="select">
                                                    <option selected>- SELECT 
                                                    -</option>
                                                    <option>Local Fish Product 
                                                    </option>
                                                    <option>Export Fish Product</option>
                                                  </select>
                                                </td>
                                                <td height="13" width="20%" nowrap align="right">Department 
                                                  : </td>
                                                <td height="13" width="19%" nowrap> 
                                                  <select name="select">
                                                    <option selected>- SELECT 
                                                    - </option>
                                                    <option>Marketing</option>
                                                    <option>Warehouse</option>
                                                  </select>
                                                </td>
                                                <td height="13" width="19%" nowrap align="right">Section 
                                                  : </td>
                                                <td height="13" width="41%" nowrap> 
                                                  <select name="select">
                                                    <option selected>- ALL -</option>
                                                    <option>Receiving</option>
                                                    <option>Stock maintenance</option>
                                                  </select>
                                                </td>
                                                <td width="1%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="5%" nowrap>Employee 
                                                  Name: </td>
                                                <td width="14%"> 
                                                  <input type="text" name="textfield9" size="20">
                                                </td>
                                                <td width="20%" align="right">Payroll 
                                                  Nr. : </td>
                                                <td width="19%"> 
                                                  <input type="text" name="textfield92" size="20">
                                                </td>
                                                <td width="19%">&nbsp;</td>
                                                <td width="41%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="5%" height="32">Overtime 
                                                  status :</td>
                                                <td width="14%" height="32"> 
                                                  <input type="checkbox" name="checkbox5" value="checkbox" checked>
                                                  New/draft</td>
                                                <td width="20%" height="32"> 
                                                  <input type="checkbox" name="checkbox22" value="checkbox" checked>
                                                  Approve</td>
                                                <td width="19%" height="32"> 
                                                  <input type="checkbox" name="checkbox222" value="checkbox" checked>
                                                  Posted</td>
                                                <td width="19%" height="32">&nbsp;</td>
                                                <td width="41%" height="32">&nbsp;</td>
                                                <td width="1%" height="32">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td height="13" width="13%">Period 
                                                  : 2007-03</td>
                                                <td height="13" width="82%">Date 
                                                  from : 
                                                  <input type="text" name="textfield3" size="12">
                                                </td>
                                                <td width="20%" nowrap>to 
                                                  <input type="text" name="textfield322" size="12">
                                                </td>
                                                <td width="19%">&nbsp;</td>
                                                <td width="19%">&nbsp;</td>
                                                <td width="41%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="5%">&nbsp;</td>
                                                <td width="14%"> 
                                                  <input type="submit" name="Submit2" value="VIEW SELECTED">
                                                </td>
                                                <td width="20%"> 
                                                  <input type="submit" name="Submit2" value="VIEW ALL">
                                                </td>
                                                <td width="19%">&nbsp;</td>
                                                <td width="19%">&nbsp;</td>
                                                <td width="41%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td valign="top" colspan="4"> 
                                            <table width="800" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <tr align="center"> 
                                                <td width="1%" class="listgentitle" nowrap rowspan="2">No.</td>
                                                <td width="8%" class="listgentitle" nowrap rowspan="2">Pay.Nr 
                                                  <br>
                                                </td>
                                                <td width="10%" class="listgentitle" rowspan="2">Name 
                                                  <br>
                                                </td>
                                                <td width="9%" class="listgentitle" rowspan="2">Position</td>
                                                <td width="1%" class="listgentitle" rowspan="2">Work 
                                                  Date</td>
                                                <td width="4%" class="listgentitle" nowrap rowspan="2">Schedule</td>
                                                <td colspan="6" class="listgentitle" nowrap>Overtime</td>
                                                <td width="2%" class="listgentitle" nowrap rowspan="2">Idx 
                                                  1 </td>
                                                <td width="2%" class="listgentitle" nowrap rowspan="2">Idx 
                                                  1.5 </td>
                                                <td width="2%" class="listgentitle" nowrap rowspan="2">Idx 
                                                  2 </td>
                                                <td width="7%" class="listgentitle" nowrap rowspan="2">Idx 
                                                  3 </td>
                                                <td width="37%" class="listgentitle" nowrap rowspan="2">Idx 
                                                  4 </td>
                                                <td width="37%" class="listgentitle" nowrap rowspan="2">Total 
                                                  Idx </td>
                                                <td width="37%" class="listgentitle" nowrap rowspan="2">Approve</td>
                                              </tr>
                                              <tr align="center"> 
                                                <td width="7%" class="listgentitle" nowrap>Start 
                                                  Date </td>
                                                <td width="7%" class="listgentitle" nowrap> 
                                                  Time</td>
                                                <td width="7%" class="listgentitle" nowrap>End 
                                                  Date </td>
                                                <td width="7%" class="listgentitle" nowrap> 
                                                  Time </td>
                                                <td width="3%" class="listgentitle">Duration</td>
                                                <td width="6%" class="listgentitle">Doc. 
                                                  Nr </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap align="center">1</td>
                                                <td width="8%" nowrap align="center">2000-02<br>
                                                </td>
                                                <td width="10%" nowrap align="center">I 
                                                  Gede Bagus</td>
                                                <td width="9%" nowrap align="center">Supervisor</td>
                                                <td width="1%" nowrap align="center">2007-03-01<br>
                                                </td>
                                                <td width="4%" align="center">M9</td>
                                                <td width="7%" align="center" nowrap>2007-03-01</td>
                                                <td width="7%" align="center">18:00</td>
                                                <td width="7%" align="center" nowrap>2007-03-01</td>
                                                <td width="7%" align="center"> 
                                                  23:30</td>
                                                <td width="3%" align="center"> 
                                                  5.5</td>
                                                <td width="6%" align="center"> 
                                                  OV-08-001 </td>
                                                <td width="2%" align="center">&nbsp; 
                                                </td>
                                                <td width="2%" align="center">2</td>
                                                <td width="2%" align="center">3.5</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">10</td>
                                                <td width="37%" align="center"> 
                                                  <input type="checkbox" name="checkbox" value="checkbox">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap align="center">2</td>
                                                <td width="8%" nowrap align="center">2007-03<br>
                                                </td>
                                                <td width="10%" nowrap align="center">Ni 
                                                  Luh Putu Ayu</td>
                                                <td width="9%" nowrap align="center">Staff</td>
                                                <td width="1%" nowrap align="center">2007-03-01<br>
                                                </td>
                                                <td width="4%" align="center">M9</td>
                                                <td width="7%" align="center">2007-03-01</td>
                                                <td width="7%" align="center">18:00</td>
                                                <td width="7%" align="center">2007-03-01</td>
                                                <td width="7%" align="center">20:30</td>
                                                <td width="3%" align="center">2.5</td>
                                                <td width="6%" align="center"> 
                                                  OV-08-002 </td>
                                                <td width="2%" align="center">&nbsp; 
                                                </td>
                                                <td width="2%" align="center">2</td>
                                                <td width="2%" align="center">0.5</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">4</td>
                                                <td width="37%" align="center"> 
                                                  <input type="checkbox" name="checkbox2" value="checkbox">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap align="center">3</td>
                                                <td width="8%" nowrap align="center">2007-02<br>
                                                </td>
                                                <td width="10%" nowrap align="center">I 
                                                  Gede Bagus</td>
                                                <td width="9%" nowrap align="center">Supervisor</td>
                                                <td width="1%" nowrap align="center">2007-03-02<br>
                                                </td>
                                                <td width="4%" align="center">A3</td>
                                                <td width="7%" align="center">2007-03-02</td>
                                                <td width="7%" align="center">13:00</td>
                                                <td width="7%" align="center">2007-03-02</td>
                                                <td width="7%" align="center"> 
                                                  15:00</td>
                                                <td width="3%" align="center"> 
                                                  2.0</td>
                                                <td width="6%" align="center"> 
                                                  OV-08-003 </td>
                                                <td width="2%" align="center">2</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">2</td>
                                                <td width="37%" align="center"> 
                                                  <input type="checkbox" name="checkbox3" value="checkbox">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap align="center" height="32">4</td>
                                                <td width="8%" nowrap align="center" height="32"> 
                                                  <input type="text" name="textfield" size="12">
                                                  <img src="../../images/BtnSearch.jpg" width="24" height="24"> 
                                                </td>
                                                <td width="10%" nowrap align="center" height="32">&nbsp;</td>
                                                <td width="9%" nowrap align="center" height="32">&nbsp;</td>
                                                <td width="1%" nowrap align="center" height="32"> 
                                                  <input type="text" name="textfield2" size="12">
                                                </td>
                                                <td width="4%" align="center" height="32"> 
                                                  <input type="text" name="textfield22" size="8">
                                                </td>
                                                <td width="4%" align="center" height="32"> 
                                                  <input type="text" name="textfield26" size="12">
                                                </td>
                                                <td width="4%" align="center" height="32"> 
                                                  <input type="text" name="textfield23" size="6">
                                                </td>
                                                <td width="4%" align="center" height="32"> 
                                                  <input type="text" name="textfield27" size="12">
                                                </td>
                                                <td width="4%" align="center" height="32"> 
                                                  <input type="text" name="textfield24" size="6">
                                                </td>
                                                <td width="3%" align="center" height="32"> 
                                                  <input type="text" name="textfield242" size="6">
                                                </td>
                                                <td width="6%" align="center" height="32"> 
                                                  <input type="text" name="textfield25" size="12">
                                                </td>
                                                <td width="2%" align="center" height="32">&nbsp;</td>
                                                <td width="2%" align="center" height="32">&nbsp;</td>
                                                <td width="2%" align="center" height="32">&nbsp;</td>
                                                <td width="7%" align="center" height="32">&nbsp;</td>
                                                <td width="37%" align="center" height="32">&nbsp;</td>
                                                <td width="37%" align="center" height="32">&nbsp;</td>
                                                <td width="37%" align="center" height="32"> 
                                                  <input type="checkbox" name="checkbox4" value="checkbox">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap align="center">&nbsp;</td>
                                                <td width="8%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" nowrap align="center">&nbsp;</td>
                                                <td width="9%" nowrap align="center">&nbsp;</td>
                                                <td width="1%" nowrap align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap align="center">&nbsp;</td>
                                                <td width="8%" nowrap align="center">&nbsp;</td>
                                                <td width="10%" nowrap align="center">&nbsp;</td>
                                                <td width="9%" nowrap align="center">&nbsp;</td>
                                                <td width="1%" nowrap align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="4%" align="center">&nbsp;</td>
                                                <td width="3%" align="center">&nbsp;</td>
                                                <td width="6%" align="center">&nbsp;</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="2%" align="center">&nbsp;</td>
                                                <td width="7%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                                <td width="37%" align="center">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4"> 
                                            <input type="submit" name="Submit" value="Save All">
                                          </td>
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
