<%@ page language="java" %>
<%
/* 
 * Page Name  		:  staffrecapitulation.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_CLINIC_EXPENSE, AppObjInfo.OBJ_CLINIC_EXP_RECAPITULATE); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    privAdd 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    privUpdate 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    privDelete 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    privPrint 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<!-- JSP Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String year = FRMQueryString.requestString(request, "year");
    String month = FRMQueryString.requestString(request, "month");
	long recapYear = FRMQueryString.requestLong(request, "recapYear");
	
    Calendar cal = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
    int maxDate = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    Date dt = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month)-1, maxDate);

    cal.add(GregorianCalendar.MONTH, -1);
    int maxDatePrev = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    Date dtPrev = new Date(cal.get(GregorianCalendar.YEAR) - 1900, cal.get(GregorianCalendar.MONTH), maxDatePrev);

    System.out.println("\tyear-month : " + year + "-" + month);
    Vector listStaffRecapitulationMonthly = new Vector(1, 1);
    SessEmployee sessEmployee = new SessEmployee();
    listStaffRecapitulationMonthly = sessEmployee.getStaffRecapitulationMonthly(Integer.parseInt(month), Integer.parseInt(year));

    //out.print("size = " + listStaffRecapitulationMonthly.size());
    Vector vDept = (Vector) listStaffRecapitulationMonthly.get(0);
    Vector vCountCurr = (Vector) listStaffRecapitulationMonthly.get(1);
    Vector vCountPrev = (Vector) listStaffRecapitulationMonthly.get(2);
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Reports</title>
<script language="JavaScript">
    function cmdSearch(){
        document.frmstaff.command.value="<%=Command.LIST%>";
        document.frmstaff.action="staffrecapitulation.jsp";
        document.frmstaff.submit();
    }

    function cmdEdit(month, year){
        document.frmstaff.month.value = month;
        document.frmstaff.year.value = year;
        document.frmstaff.action="staffrecapitulationmonthly.jsp";
        document.frmstaff.submit();
    }

	function cmdShowStaffTurnover(month, year) {
        document.frmstaff.command.value="<%=Command.LIST%>";
        document.frmstaff.month.value = month;
        document.frmstaff.year.value = year;
        document.frmstaff.action="staffturnover.jsp";
        document.frmstaff.submit();
	}

	function cmdBack() {
        document.frmstaff.command.value="<%=Command.LIST%>";
        document.frmstaff.action="staffrecapitulation.jsp";
        document.frmstaff.submit();
	}

    function cmdPrint(){
        window.open("<%=approot%>/servlet/com.dimata.harisma.report.employment.StaffPdf?year=<%=year%>&month=<%=month%>&recapYear=<%=recapYear%>");
    }
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }

    function MM_swapImgRestore() { //v3.0
      var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
    }

    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
            var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
            if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function MM_findObj(n, d) { //v4.0
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
            d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && document.getElementById) x=document.getElementById(n); return x;
    }

    function MM_swapImage() { //v3.0
      var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
       if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
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
                <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Reports 
                  &gt; Employment &gt; Staff Recapitulation Monthly<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" -->
                                    <form name="frmstaff" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="recapYear" value="<%=year%>">
                                      <input type="hidden" name="month" value="">
                                      <input type="hidden" name="year" value="">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <tr> 
                                    <td> 
                                      <table  width="80%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td  valign="top" height="20" width="17%"> 
                                            <table width="76%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                              <tr> 
                                                <td   valign="top" align="left" width="1">&nbsp;&nbsp;&nbsp;</td>
                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap width="113"> 
                                                  <div align="center" class="tablink">Staff 
                                                    Recapitulation </div>
                                                </td>
                                                <td width="10"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20" width="11%"> 
                                            <table width="67%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="78" > 
                                                  <div align="center" class="tablink"><a href="javascript:cmdShowStaffTurnover('<%=month%>','<%=year%>');" class="tablink">Staff 
                                                    Turnover </a></div>
                                                </td>
                                                <td width="10"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td width="72%"  valign="top" height="20">&nbsp;</td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td class="tablecolor"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                              <tr> 
                                                <td valign="top" width="50%"> 
													
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td> 
                                                        <table border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                          <tr> 
                                                            <td class="listgensell"> 
                                                              <div align="center"><b>STAFF 
                                                                RECAPITULATION<br>
                                                                As of <%=Formater.formatDate(dt, "dd MMMM yyyy")%></b></div>
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            <td class="listgensell"> 
                                                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                                <% for (int i = 0; i < vDept.size(); i++) { 
                                                        int iPrev = Integer.parseInt(String.valueOf(vCountPrev.get(i)));
                                                        int iCurr = Integer.parseInt(String.valueOf(vCountCurr.get(i)));
                                                        int iDiscrepancy = iCurr - iPrev;
                                                    %>
                                                                <tr> 
                                                                  <td class="listgensell" colspan="6">&nbsp;</td>
                                                                </tr>
                                                                <tr> 
                                                                  <td class="listgentitle" colspan="6"> 
                                                                    <div align="center"><%=String.valueOf(vDept.get(i)).toUpperCase()%></div>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td class="listgensell"> 
                                                                    <div align="center">Status</div>
                                                                  </td>
                                                                  <td nowrap class="listgensell"> 
                                                                    <div align="center"><%=Formater.formatDate(dtPrev, "dd-MMM-yyyy")%></div>
                                                                  </td>
                                                                  <td nowrap class="listgensell"> 
                                                                    <div align="center"><%=Formater.formatDate(dt, "dd-MMM-yyyy")%></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center">Discrepancy</div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center">Staff 
                                                                      Resignation</div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center">Month 
                                                                      to Date 
                                                                      Turnover</div>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td class="listgensell">Permanent</td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"><%=vCountPrev.get(i)%></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"><%=vCountCurr.get(i)%></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"><%=iDiscrepancy%></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td class="listgensell">Contract</td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td class="listgensell">Apprenticeship</td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td class="listgensell">Daily 
                                                                    workers</td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td class="listgensell"> 
                                                                    <div align="left"><b>Total</b></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"><%=vCountPrev.get(i)%></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"><%=vCountCurr.get(i)%></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"><%=iDiscrepancy%></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                  <td class="listgensell"> 
                                                                    <div align="center"></div>
                                                                  </td>
                                                                </tr>
                                                                <% } %>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td> 
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a></td>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td nowrap><a href="javascript:cmdBack()" class="command" style="text-decoration:none">Back</a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="15"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td nowrap><b><a href="javascript:cmdPrint()" class="command" style="text-decoration:none">Print Report</a></b></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr>
                                                      <td>&nbsp;</td>
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
                                </table>
                                                            </form>
<!-- #EndEditable --> 
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td >&nbsp;</td>
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
