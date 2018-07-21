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
<%@ page import = "java.text.DecimalFormat" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_STAFF_CONTROL_REPORT, AppObjInfo.OBJ_MANNING_SUMMARY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
    //System.out.println("privPrint=" + privPrint);
%>
<!-- JSP Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Date dtPeriod = FRMQueryString.requestDate(request,"date_periode");

    if(iCommand == Command.NONE){
            dtPeriod = new Date();
            dtPeriod.setDate(1);
    }
    GregorianCalendar gcPeriod = new GregorianCalendar(dtPeriod.getYear()+1900, dtPeriod.getMonth(), dtPeriod.getDate());

    Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
    SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();
    Vector listManning = new Vector(1, 1);
    //listManning = sessEmpSalary.getManning(int year, int month, int date, long departmentId);

    //out.print(dtPeriod.toString());
    //out.print("<br>" + (dtPeriod.getYear()+1900) + " - " + (dtPeriod.getMonth()+1) + " - " + dtPeriod.getDate());
    //out.print("<br>" + gcPeriod.toString());
    //out.print(gcPeriod.get(Calendar.MONTH));
	
	
	Vector vctPrint = new Vector(1,1);
	
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
        document.frmstaff.command.value="<%=Command.LIST%>";
        document.frmstaff.month.value = month;
        document.frmstaff.year.value = year;
        document.frmstaff.action="staffrecapitulationmonthly.jsp";
        document.frmstaff.submit();
    }

    function cmdPrint(){
        window.open("<%=approot%>/servlet/com.dimata.harisma.report.staffcontrol.ManningPdf?yr=<%=(dtPeriod.getYear()+1900)%>&mn=<%=dtPeriod.getMonth()%>");
    }

    function chgPeriode(){
            document.frmstaff.command.value="<%=Command.LIST%>";
            document.frmstaff.action="manning.jsp";
            document.frmstaff.submit();
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Reports 
                  &gt; Staff Control &gt; Manning Summary<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                    <form name="frmstaff" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td>
                                            <table border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td nowrap><b>Year : </b></td>
                                                <td nowrap>
                                                    <%//=ControlDate.drawDateYear("recapYear", recapYear, "formElemen", 0, -5)%>
                                                    <%=ControlDate.drawDateMY("date_periode", dtPeriod,"MMMM","formElemen", 0,-1) %> 
                                                </td>
                                                <td><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td><a href="javascript:chgPeriode()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Report"></a></td>
                                                <td class="command" nowrap><a href="javascript:chgPeriode()">Search</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td><hr></td>
                                        </tr>
                                        <%
                                            String[] mon = {"January", "February", "March", 
                                                "April", "May", "June", "July", "August", 
                                                "September", "October", "November", "December"};
                                            int totalManning = 0;
                                            int grandTotalManning = 0;
                                            int deptManning = 0;
                                            int dayOfMonth = gcPeriod.getActualMaximum(Calendar.DAY_OF_MONTH);
                                            int year = gcPeriod.get(Calendar.YEAR);
                                            int month = gcPeriod.get(Calendar.MONTH);
                                        %>
                                        <tr>
                                          <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr>
                                                <td class="listgensell"><font size="2"><b><font size="3">MANNING 
                                                  SUMMARY REPORT</font></b></font><br>
                                                  <b><font size="2">MONTH / YEAR: <%=mon[month]%> / <%=year%>
                                                  </font></b></td>
                                              </tr>
                                              <tr>
                                                <td>
                                                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                    <tr> 
                                                      <td rowspan="2" class="listgentitle">DEPARTMENT</td>
                                                      <td rowspan="2" class="listgentitle">TOTAL 
                                                        MANNING</td>
                                                      <td colspan="<%=gcPeriod.getActualMaximum(Calendar.DAY_OF_MONTH)%>" class="listgentitle"> 
                                                        <div align="center">STAFF 
                                                          ON DUTY</div>
                                                      </td>
                                                      <td rowspan="2" class="listgentitle">Ratio in %</td>
                                                    <tr> 
                                                    <%
                                                        for (int i=0; i<gcPeriod.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                                                    %>
                                                      <td class="listgentitle"> 
                                                        <div align="center"><%=i+1%></div>
                                                      </td>
                                                    <%
                                                        }
                                                    %>
                                                    </tr>
                                                    <%
                                                        //System.out.println("--- month = " + month);
                                                        int[] grandDeptManning = new int[dayOfMonth];
                                                        for (int i=0; i<grandDeptManning.length; i++) {
                                                            grandDeptManning[i] = 0;
                                                        }
                                                        for (int d=0; d<listDepartment.size(); d++) {
                                                            Department dep = (Department) listDepartment.get(d);
                                                            totalManning = sessEmpSchedule.getTotalManning(dep.getOID());
                                                            grandTotalManning += totalManning;
                                                    %>
                                                    <tr> 
                                                      <td class="listgensell" nowrap>
													  <%=dep.getDepartment()%>
													  </td>
                                                      <td class="listgensell" align="center"><%=totalManning%></td>
                                                    <%
                                                        for (int j=1; j<=dayOfMonth; j++) {
                                                            if (j > 20) {
                                                                if (month == 11) {
                                                                    deptManning = sessEmpSchedule.getManning(year+1,1,j, dep.getOID());
                                                                }
                                                                else {
                                                                    deptManning = sessEmpSchedule.getManning(year,month+2,j, dep.getOID());
                                                                }
                                                            }
                                                            else {
                                                                deptManning = sessEmpSchedule.getManning(year,month+1,j, dep.getOID());
                                                            }
                                                            grandDeptManning[j-1] += deptManning;
                                                    %>
                                                      <td class="listgensell" align="right"><%=deptManning%>&nbsp;</td>
                                                    <%
                                                        }
                                                    %>
                                                      <td class="listgensell">&nbsp;</td>
                                                    </tr>
                                                    <%
                                                        }
                                                    %>
                                                    <tr> 
                                                      <td class="listgensell"></td>
                                                      <td class="listgensell"></td>
                                                    <%
                                                        for (int k=0; k<dayOfMonth; k++) {
                                                    %>
                                                      <td class="listgensell"></td>
                                                    <%
                                                        }
                                                    %>
                                                      <td class="listgensell"></td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listgensell"><b>Grand Total</b></td>
                                                      <td class="listgensell" align="center"><b><%=grandTotalManning%></b></td>
                                                    <%
                                                        for (int k=0; k<dayOfMonth; k++) {
                                                    %>
                                                      <td class="listgensell" align="right">&nbsp;<b><%=grandDeptManning[k]%></b>&nbsp;</td>
                                                    <%
                                                        }
                                                    %>
                                                      <td class="listgensell">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listgensell">In %</td>
                                                      <td class="listgensell" align="center">100</td>
                                                    <%
                                                        DecimalFormat myFormatter = new DecimalFormat("###.#");
                                                        double pct = 0.0;
                                                        for (int k=0; k<dayOfMonth; k++) {
                                                            pct = ((double) grandDeptManning[k] / (double) grandTotalManning) * 100.0;
                                                            //System.out.println(grandDeptManning[k] + " / " + grandTotalManning + " = " + (((double) grandDeptManning[k] / (double) grandTotalManning) * 100.0));
                                                    %>
                                                      <td class="listgensell" align="right" nowrap><%=myFormatter.format(pct)%></td>
                                                    <%
                                                        }
                                                        System.out.println("--- month = " + month);
                                                    %>
                                                      <td class="listgensell">&nbsp;</td>
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
                                        <% if(privPrint){%>
                                        <tr>
                                          <td>
                                              <table cellpadding="0" cellspacing="0" border="0">
                                                <tr>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="15"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><b><a href="javascript:cmdPrint()" class="command" style="text-decoration:none">Print 
                                            		Report</a></b></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}%>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>


