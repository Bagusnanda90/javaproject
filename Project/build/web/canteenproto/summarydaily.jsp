<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Canteen Detail Daily Report</title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<script language="javascript">
    function changedept(){
        document.frmDailyReport.command.value = "31";
        document.frmDailyReport.action = "detail_daily_report.jsp";
        document.frmDailyReport.submit();
    }

    function MM_swapImgRestore() { // v3.0
        var i, x, a = document.MM_sr;
        for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
            x.src = x.oSrc;
    }

    function MM_preloadImages() { // v3.0
        var d = document;
        if (d.images) {
            if (!d.MM_p)
             d.MM_p = new Array();
             var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
             for (i = 0; i < a.length; i++)
	      if (a[i].indexOf("#") != 0) {
               d.MM_p[j] = new Image;
               d.MM_p[j++].src = a[i];
              }
        }
    }

    function MM_findObj(n, d) { // v4.0
	var p, i, x;
        if (!d)
         d = document;
        if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
	 d = parent.frames[n.substring(p+1)].document;
         n = n.substring(0,p);
        }
	if (!(x = d[n]) && d.all)
         x = d.all[n];
        for (i = 0; !x && i < d.forms.length; i++)
         x = d.forms[i][n];
        for (i = 0; !x && d.layers && i < d.layers.length; i++)
         x = MM_findObj(n, d.layers[i].document);
	if(!x && document.getElementById)
         x = document.getElementById(n);
        return x;
    }

    function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
        document.MM_sr = new Array;
        for (i = 0; i < (a.length - 2); i += 3)
         if ((x = MM_findObj(a[i])) != null) {
          document.MM_sr[j++] = x;
          if (!x.oSrc)
           x.oSrc = x.src;
          x.src = a[i+2];
         }
    }


</script>

<link rel="stylesheet" href="../styles/main.css" type="text/css">
<link rel="stylesheet" href="../styles/login.css" type="text/css">
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr>
   <td ID="TOPTITLE" background="/harisma/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
   <!-- #BeginEditable "header" -->
   
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" height="45">&nbsp;</td>
    <td align="center" height="45"><b><font color="#FFFFFF"><i><font size="4">HUMAN 
      RESOURCES MANAGEMENT SYSTEM</font></i></font></b></td>
    <td align="right" height="45">&nbsp;</td>
  </tr>
</table>

   <!-- #EndEditable --> </td>
  </tr><tr>
   <td bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
      <table width="100%" border="0" cellspacing="0" cellpadding="0" height="10">
  <tr>  
    <td nowrap height="10" valign="middle"> 
      <div align="center">
	  <b> 
	  <A STYLE="text-decoration:none" id="divMenu7" href="/harisma/home.jsp"><font color="#30009D">Menu</font></A> 
      | <A STYLE="text-decoration:none" id="divMenu8" href="/harisma/logout.jsp"><font color="#30009D">Logout</font></A> 
      </b>
	  </div>
     </td> 
    </tr>
</table>

      <!-- #EndEditable --> </td>
  </tr>
  <tr>
    <td  bgcolor="#9BC1FF" height="10" valign="middle">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left"><img src="/harisma/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="/harisma/images/harismaMenuLine1.jpg" width="100%"><img src="/harisma/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="/harisma/images/harismaMenuRight1.jpg" width="8" height="8"></td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Reports 
                  >> Summary >> Daily Report<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top">
                                  <!-- #BeginEditable "content" -->
                                    <form name="frmDailyReport" method="POST">
                                     <input type="hidden" name="command" value="0">
                                     <input type="hidden" name="hidden_dept_name" value="">
                                     <input type="hidden" name="hidden_section_name" value="">
                                     <table width="100%">
                                     <tr>
                                      <td width="1%"></td>
                                          <td> 
                                            <table width="760">
                                              <tr> 
                                                <td width="129"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="15">:</td>
                                                <td width="600"> 
                                                  <select name="DEPARTMENT_ID" onChange="javascript:changedept()" class="formElemen">
                                                    <option value="0">Select ...</option>
                                                    <option value="504404240100923347">ACCOUNTING</option>
                                                    <option value="504404240100923407">ADMIN 
                                                    & GENERAL</option>
                                                    <option value="504404240100923427">ENGINEERING</option>
                                                    <option value="504404240100922796">FBK 
                                                    DEPARTMENT</option>
                                                    <option value="504404240100922686">FBS 
                                                    DEPARTMENT</option>
                                                    <option value="504404240100922615">FRONT 
                                                    OFFICE</option>
                                                    <option value="504404240100922515">HEALTH 
                                                    CLUB</option>
                                                    <option value="504404240100922455">HOUSEKEEPING</option>
                                                    <option value="504404240100922425" selected>HUMAN 
                                                    RESOURCES</option>
                                                    <option value="504404241637895957">KOPKAR</option>
                                                    <option value="504404240100922355">SALES 
                                                    & MARKETING</option>
                                                    <option value="504404240100922235">UNKNOWN</option>
                                                  </select>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>Period</td>
                                                <td>:</td>
                                                <td> 
                                                  <select name="date_mn" class="formElemen" >
                                                    <option value="1">Jan</option>
                                                    <option value="2">Feb</option>
                                                    <option value="3">Mar</option>
                                                    <option value="4">Apr</option>
                                                    <option value="5"selected>Mei</option>
                                                    <option value="6">Jun</option>
                                                    <option value="7">Jul</option>
                                                    <option value="8">Agu</option>
                                                    <option value="9">Sep</option>
                                                    <option value="10">Okt</option>
                                                    <option value="11">Nov</option>
                                                    <option value="12">Des</option>
                                                  </select>
                                                  <select name="date_dy" class="formElemen" >
                                                    <option value="1">01</option>
                                                    <option value="2">02</option>
                                                    <option value="3">03</option>
                                                    <option value="4">04</option>
                                                    <option value="5">05</option>
                                                    <option value="6">06</option>
                                                    <option value="7">07</option>
                                                    <option value="8">08</option>
                                                    <option value="9">09</option>
                                                    <option value="10">10</option>
                                                    <option value="11">11</option>
                                                    <option value="12">12</option>
                                                    <option value="13"selected>13</option>
                                                    <option value="14">14</option>
                                                    <option value="15">15</option>
                                                    <option value="16">16</option>
                                                    <option value="17">17</option>
                                                    <option value="18">18</option>
                                                    <option value="19">19</option>
                                                    <option value="20">20</option>
                                                    <option value="21">21</option>
                                                    <option value="22">22</option>
                                                    <option value="23">23</option>
                                                    <option value="24">24</option>
                                                    <option value="25">25</option>
                                                    <option value="26">26</option>
                                                    <option value="27">27</option>
                                                    <option value="28">28</option>
                                                    <option value="29">29</option>
                                                    <option value="30">30</option>
                                                    <option value="31">31</option>
                                                  </select>
                                                  , 
                                                  <select name="date_yr" class="formElemen">
                                                    <option value="2004">2004</option>
                                                    <option value="2005"selected>2005</option>
                                                  </select>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td valign="top">Report Type </td>
                                                <td valign="top">:</td>
                                                <td>
                                                  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td>
                                                        <input name="MORNING_AFTERNOON_NIGHT_ID" type="radio" value="0" checked>
                                                        Morning and Afternoon 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td>
                                                        <input type="radio" name="MORNING_AFTERNOON_NIGHT_ID" value="1" >
                                                        Night </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td></td>
                                                <td></td>
                                                <td>
                                                  <table width="447">
                                                    <tr> 
                                                      <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '/harisma/images/BtnSearchOn.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="../images/BtnSearch.jpg" width="24" height="24" alt="Search Visitation"></a></td>
                                                      <td width="10"><img src="/harisma/images/spacer.gif" width="4" height="1"></td>
                                                      <td width="397" class="command" nowrap><a href="javascript:cmdView()">View 
                                                        Visitation</a></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      
                                     </table>
                                    </form>
                                   <!-- #EndEditable -->
                                  </td>
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
      
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td  bgcolor="#BBDDFF" width="30%"><i>user : -</i></td>
    <td  bgcolor="#BBDDFF" width="40%" align="center"><font color="#0000FF">Powered 
      By Dimata Solution &copy; 2005, All right reserved</font></td>
    <td  bgcolor="#BBDDFF" width="30%">&nbsp;</td>
  </tr>
</table>
 
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->

<script language="javascript">

 function cmdView() {
    if(document.frmDailyReport.DEPARTMENT_ID.value != "0"){
        document.frmDailyReport.command.value = "1";
        document.frmDailyReport.hidden_dept_name.value = document.frmDailyReport.DEPARTMENT_ID.options[document.frmDailyReport.DEPARTMENT_ID.selectedIndex].text;
        document.frmDailyReport.hidden_section_name.value = document.frmDailyReport.SECTION_ID.options[document.frmDailyReport.SECTION_ID.selectedIndex].text;
        document.frmDailyReport.action = "detail_daily_report.jsp";
        document.frmDailyReport.submit();
    }else{
        alert("Please change department.");
    }
 }

 function reportPdf() {
    var linkPage = "/harisma/servlet/com.dimata.harisma.report.canteen.DailyDetailVisitation";
    handle = window.open("", "canteenWindow");
    handle.close();
    handle = window.open(linkPage, "canteenWindow");
    handle.focus();

 }
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
