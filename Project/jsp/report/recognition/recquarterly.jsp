
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
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_REPORT, AppObjInfo.G2_RECOGNITION, AppObjInfo.OBJ_REC_QUARTERLY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
    privStart 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int year = FRMQueryString.requestInt(request, "year");

    if (year == 0) {
        year = new Date().getYear() + 1900;
    }

    String month[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    Calendar dateStartYearly = new GregorianCalendar(year,0,21);
    Calendar dateEndYearly = new GregorianCalendar(year+1,0,20);

    Vector vResult = new Vector(1, 1);
    Vector vQ1 = new Vector(1, 1);
    Vector vQ2 = new Vector(1, 1);
    Vector vQ3 = new Vector(1, 1);

    Vector vEmpId = new Vector(1, 1);
    Vector vEmpNum = new Vector(1, 1);
    Vector vEmp = new Vector(1, 1);
    Vector vPoint = new Vector(1, 1);
    Vector vName = new Vector(1, 1);
    Vector vDep = new Vector(1, 1);
    Vector vPos = new Vector(1, 1);

    vResult = SessRecognition.getPointQuarterly(year);
    vEmp = (Vector) vResult.get(0);
    vPoint = (Vector) vResult.get(1);
    vName = (Vector) vResult.get(2);
    vDep = (Vector) vResult.get(3);
    vPos = (Vector) vResult.get(4);
    vEmpId = (Vector) vResult.get(5);
    vEmpNum = (Vector) vResult.get(6);

    vQ1 = (Vector) vResult.get(7);
    Vector vQ1_EmpId = new Vector(1, 1);
    Vector vQ1_Point = new Vector(1, 1);
    vQ1_EmpId = (Vector) vQ1.get(0);
    vQ1_Point = (Vector) vQ1.get(1);

    vQ2 = (Vector) vResult.get(8);
    Vector vQ2_EmpId = new Vector(1, 1);
    Vector vQ2_Point = new Vector(1, 1);
    vQ2_EmpId = (Vector) vQ2.get(0);
    vQ2_Point = (Vector) vQ2.get(1);

    vQ3 = (Vector) vResult.get(9);
    Vector vQ3_EmpId = new Vector(1, 1);
    Vector vQ3_Point = new Vector(1, 1);
    vQ3_EmpId = (Vector) vQ3.get(0);
    vQ3_Point = (Vector) vQ3.get(1);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report</title>
<script language="JavaScript">
    function cmdPrint(){
    window.open("<%=approot%>/servlet/com.dimata.harisma.report.recognition.RecQuarterlyPdf?year=<%=year%>");
    }

    function cmdGetList() {
        document.frmbarcode.command.value = "<%=Command.GOTO%>";
        document.frmbarcode.year.value = document.frmbarcode.YEAR.value;
        document.frmbarcode.action = "recquarterly.jsp";
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
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
                  Report &gt; Recognition &gt; Quarterly Report<!-- #EndEditable --> 
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
                                <% if (privStart) { %>
                                    <form name="frmbarcode" method="post" action="">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="year">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="6%">Year</td>
                                                <td width="93%"><%=ControlDate.drawDateYear("YEAR", new Date(year-1900,0,1),"elemenForm",0,-5)%></td>
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
                                                          <div align="center"><b>Quarterly 
                                                            Recapitulation <%=year%></b></div>
                                                        </td>
                                                      </tr>
                                                      <tr> 
                                                        <td class="listgensell"> 
                                                          <table border="0" cellspacing="2" cellpadding="2" class="listgen">
                                                            <tr> 
                                                              <td class="listgentitle" rowspan="2" width="4%">No.</td>
                                                              <td class="listgentitle" rowspan="2" width="9%">Payroll</td>
                                                              <td class="listgentitle" rowspan="2" width="8%">Name</td>
                                                              <td class="listgentitle" rowspan="2" width="15%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                              <td class="listgentitle" rowspan="2" width="10%">Position</td>
                                                              <td class="listgentitle" colspan="3" nowrap> 
                                                                <div align="center">Point 
                                                                  accumulated 
                                                                  <%=year%> </div>
                                                              </td>
                                                              <td class="listgentitle" rowspan="2" width="12%">Total</td>
                                                            </tr>
                                                            <tr> 
                                                              <td class="listgentitle" width="15%" nowrap><%=month[0]%>-<%=month[4]%></td>
                                                              <td class="listgentitle" width="15%" nowrap><%=month[4]%>-<%=month[8]%></td>
                                                              <td class="listgentitle" width="15%" nowrap><%=month[8]%>-<%=month[0]%></td>
                                                            </tr>
                                                            <% for (int i=0; i<vEmp.size(); i++) { %>
                                                            <tr> 
                                                              <td width="4%" class="listgensell"><%=(i+1)%></td>
                                                              <td width="9%" class="listgensell" nowrap><%=vEmpNum.get(i)%></td>
                                                              <td width="8%" class="listgensell" nowrap><%=vEmp.get(i)%></td>
                                                              <td width="15%" class="listgensell" nowrap><%=vDep.get(i)%></td>
                                                              <td width="10%" class="listgensell" nowrap><%=vPos.get(i)%></td>
                                                              <td width="12%" class="listgensell">
                                                                <div align="center"> 
                                                                  <%
                                                                    for (int a = 0; a < vQ1_EmpId.size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vQ1_EmpId.get(a)))) {
                                                                            out.print(vQ1_Point.get(a));
                                                                        }
                                                                    }
                                                                %>
                                                                </div>
                                                              </td>
                                                              <td width="9%" class="listgensell">
                                                                <div align="center"> 
                                                                  <%
                                                                    for (int a = 0; a < vQ2_EmpId.size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vQ2_EmpId.get(a)))) {
                                                                            out.print(vQ2_Point.get(a));
                                                                        }
                                                                    }
                                                                %>
                                                                </div>
                                                              </td>
                                                              <td width="21%" class="listgensell">
                                                                <div align="center"> 
                                                                  <%
                                                                    for (int a = 0; a < vQ3_EmpId.size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vQ3_EmpId.get(a)))) {
                                                                            out.print(vQ3_Point.get(a));
                                                                        }
                                                                    }
                                                                %>
                                                                </div>
                                                              </td>
                                                              <td width="12%" class="listgensell"> 
                                                                <div align="center"> 
                                                                  <b><%=vPoint.get(i)%></b></div>
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
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td width="131">&nbsp;</td>
                                                      <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                      <td nowrap width="574"><b><a href="javascript:cmdPrint()" class="command" style="text-decoration:none">Print 
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
