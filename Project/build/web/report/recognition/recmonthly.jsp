
<% 
/* 
 * Page Name  		:  recognition.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_REPORT, AppObjInfo.G2_RECOGNITION, AppObjInfo.OBJ_REC_MONTHLY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
    privStart 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int year = FRMQueryString.requestInt(request, "year");
    int quarter = FRMQueryString.requestInt(request, "quarter");

    if (year == 0) {
        year = new Date().getYear() + 1900;
    }
    if (quarter == 0) {
        quarter = 1;
    }

    String month[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int start = (quarter - 1) * 4;
    Calendar dateStart = new GregorianCalendar(year,start,21);
    Calendar dateEnd = new GregorianCalendar(year,start,20);
    dateEnd.add(Calendar.MONTH, 4);

    Vector vResult = new Vector(1, 1);
    Vector vM1 = new Vector(1, 1);
    Vector vM2 = new Vector(1, 1);
    Vector vM3 = new Vector(1, 1);
    Vector vM4 = new Vector(1, 1);

    Vector vEmpId = new Vector(1, 1);
    Vector vEmpNum = new Vector(1, 1);
    Vector vEmp = new Vector(1, 1);
    Vector vPoint = new Vector(1, 1);
    Vector vName = new Vector(1, 1);
    Vector vDep = new Vector(1, 1);
    Vector vPos = new Vector(1, 1);

    vResult = SessRecognition.getPointMonthly(year, quarter);
    vEmp = (Vector) vResult.get(0);
    vPoint = (Vector) vResult.get(1);
    vName = (Vector) vResult.get(2);
    vDep = (Vector) vResult.get(3);
    vPos = (Vector) vResult.get(4);
    vEmpId = (Vector) vResult.get(5);
    vEmpNum = (Vector) vResult.get(6);

    vM1 = (Vector) vResult.get(7);
    Vector vM1_EmpId = new Vector(1, 1);
    Vector vM1_Point = new Vector(1, 1);
    vM1_EmpId = (Vector) vM1.get(0);
    vM1_Point = (Vector) vM1.get(1);

    vM2 = (Vector) vResult.get(8);
    Vector vM2_EmpId = new Vector(1, 1);
    Vector vM2_Point = new Vector(1, 1);
    vM2_EmpId = (Vector) vM2.get(0);
    vM2_Point = (Vector) vM2.get(1);

    vM3 = (Vector) vResult.get(9);
    Vector vM3_EmpId = new Vector(1, 1);
    Vector vM3_Point = new Vector(1, 1);
    vM3_EmpId = (Vector) vM3.get(0);
    vM3_Point = (Vector) vM3.get(1);

    vM4 = (Vector) vResult.get(10);
    Vector vM4_EmpId = new Vector(1, 1);
    Vector vM4_Point = new Vector(1, 1);
    vM4_EmpId = (Vector) vM4.get(0);
    vM4_Point = (Vector) vM4.get(1);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report</title>
<script language="JavaScript">
    function cmdPrint(){
    window.open("<%=approot%>/servlet/com.dimata.harisma.report.recognition.RecMonthlyPdf?year=<%=year%>&quarter=<%=quarter%>");
    }

    function cmdGetList() {
        document.frmbarcode.command.value = "<%=Command.GOTO%>";
        document.frmbarcode.year.value = document.frmbarcode.YEAR.value;
        document.frmbarcode.quarter.value = document.frmbarcode.QUARTER.value;
        document.frmbarcode.action = "recmonthly.jsp";
        document.frmbarcode.submit();
    }

//-------------- script control line -------------------
	function MM_swapImgRestore() { //v3.0
		var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}

function MM_preloadImages() { //v3.0
		var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}

function MM_findObj(n, d) { //v4.0
		var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		if(!x && document.getElementById) x=document.getElementById(n); return x;
	}

function MM_swapImage() { //v3.0
		var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Report &gt; Recognition &gt; Monthly Report<!-- #EndEditable --> </strong></font> 
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
                                <% if (privStart) { %>
                                    <form name="frmbarcode" method="post" action="">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="year">
                                    <input type="hidden" name="quarter">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="6%">Quarter</td>
                                                <td width="93%">
                                                  <select name="QUARTER">
                                                    <option value="1" <%if (quarter==1) {%> selected <%}%>>21 
                                                    Jan to 20 May</option>
                                                    <option value="2" <%if (quarter==2) {%> selected <%}%>>21 May to 
                                                    20 Sep</option>
                                                    <option value="3" <%if (quarter==3) {%> selected <%}%>>21 Sep to 
                                                    20 Jan</option>
                                                  </select>
						  <%=ControlDate.drawDateYear("YEAR", new Date(year-1900,0,1),"elemenForm",0,-5)%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="6%"></td>
                                                <td width="93%"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td width="8"><a href="javascript:cmdGetList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Locker"></a></td>
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="2" height="8"></td>
                                                      <td width="110" class="command" nowrap><a href="javascript:cmdGetList()">Get 
                                                        List </a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td> 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <div align="center"> 
                                                    <table border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                      <tr> 
                                                        <td class="listgensell"> 
                                                          <div align="center"><b>Monthly 
                                                            Recapitulation</b><br>
                                                            <b><%=month[dateStart.get(Calendar.MONTH)+1]%> 
                                                            <%=dateStart.get(Calendar.YEAR)%> 
                                                            - <%=month[dateEnd.get(Calendar.MONTH)]%> 
                                                            <%=dateEnd.get(Calendar.YEAR)%></b></div>
                                                        </td>
                                                      </tr>
                                                      <tr> 
                                                        <td class="listgensell"> 
                                                          <table border="0" cellspacing="2" cellpadding="2" class="listgen">
                                                            <tr> 
                                                              <td class="listgentitle" rowspan="2">No.</td>
                                                              <td class="listgentitle" rowspan="2">Payroll</td>
                                                              <td class="listgentitle" rowspan="2">Name</td>
                                                              <td class="listgentitle" rowspan="2"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                              <td class="listgentitle" rowspan="2">Position</td>
                                                              <td class="listgentitle" colspan="4" nowrap> 
                                                                <div align="center">Point 
                                                                  accumulated 
                                                                  <%=dateStart.get(Calendar.YEAR)%> 
                                                                </div>
                                                              </td>
                                                              <td class="listgentitle" rowspan="2">Total</td>
                                                            </tr>
                                                            <%
                                                            dateStart.roll(Calendar.MONTH,true);
                                                            %>
                                                            <tr> 
                                                              <td class="listgentitle" width="8%"> 
                                                                <div align="center"> 
                                                                  <%=month[dateStart.get(Calendar.MONTH)]%></div>
                                                              </td>
                                                              <% dateStart.roll(Calendar.MONTH,true); %>
                                                              <td class="listgentitle" width="7%"> 
                                                                <div align="center"> 
                                                                  <%=month[dateStart.get(Calendar.MONTH)]%></div>
                                                              </td>
                                                              <% dateStart.roll(Calendar.MONTH,true); %>
                                                              <td class="listgentitle" width="7%"> 
                                                                <div align="center"> 
                                                                  <%=month[dateStart.get(Calendar.MONTH)]%></div>
                                                              </td>
                                                              <% dateStart.roll(Calendar.MONTH,true); %>
                                                              <td class="listgentitle" width="7%"> 
                                                                <div align="center"> 
                                                                  <%=month[dateStart.get(Calendar.MONTH)]%></div>
                                                              </td>
                                                            </tr>
                                                            <% for (int i=0; i<vEmp.size(); i++) { %>
                                                            <tr> 
                                                              <td width="1%" class="listgensell"><%=(i+1)%></td>
                                                              <td width="25%" class="listgensell" nowrap><%=vEmpNum.get(i)%></td>
                                                              <td width="25%" class="listgensell" nowrap><%=vEmp.get(i)%></td>
                                                              <td width="25%" class="listgensell" nowrap><%=vDep.get(i)%></td>
                                                              <td width="25%" class="listgensell" nowrap><%=vPos.get(i)%></td>
                                                              <td width="8%" class="listgensell">
                                                                <div align="center"> 
                                                                  <%
                                                                    for (int a = 0; a < vM1_EmpId.size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vM1_EmpId.get(a)))) {
                                                                            out.print(vM1_Point.get(a));
                                                                        }
                                                                    }
                                                                %>
                                                                </div>
                                                              </td>
                                                              <td width="7%" class="listgensell">
                                                                <div align="center"> 
                                                                  <%
                                                                    for (int a = 0; a < vM2_EmpId.size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vM2_EmpId.get(a)))) {
                                                                            out.print(vM2_Point.get(a));
                                                                        }
                                                                    }
                                                                %>
                                                                </div>
                                                              </td>
                                                              <td width="7%" class="listgensell">
                                                                <div align="center"> 
                                                                  <%
                                                                    for (int a = 0; a < vM3_EmpId.size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vM3_EmpId.get(a)))) {
                                                                            out.print(vM3_Point.get(a));
                                                                        }
                                                                    }
                                                                %>
                                                                </div>
                                                              </td>
                                                              <td width="7%" class="listgensell">
                                                                <div align="center"> 
                                                                  <%
                                                                    for (int a = 0; a < vM4_EmpId.size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vM4_EmpId.get(a)))) {
                                                                            out.print(vM4_Point.get(a));
                                                                        }
                                                                    }
                                                                %>
                                                                </div>
                                                              </td>
                                                              <td width="20%" class="listgensell"> 
                                                                <div align="center"><b><%=vPoint.get(i)%></b></div>
                                                              </td>
                                                            </tr>
                                                            <% } %>
                                                          </table>
                                                        </td>
                                                      </tr>
                                                    </table>
                                                  </div>
                                                </td>
                                              </tr>
											  <% if(privPrint){%>
                                              <tr>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table cellpadding="0" cellspacing="0" border="0" width="75%">
                                                    <tr> 
                                                      <td width="18%">&nbsp;</td>
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
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
