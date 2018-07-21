
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
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_REPORT, AppObjInfo.G2_RECOGNITION, AppObjInfo.OBJ_REC_YEARLY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
     privStart 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<!-- Jsp Block -->
<%
    Vector vYear = new Vector(1, 1);
    vYear = SessRecognition.getDistinctYear();

    Vector vResult = new Vector(1, 1);
    Vector vEmpId = new Vector(1, 1);
    Vector vEmpNum = new Vector(1, 1);
    Vector vEmp = new Vector(1, 1);
    Vector vPoint = new Vector(1, 1);
    Vector vName = new Vector(1, 1);
    Vector vDep = new Vector(1, 1);
    Vector vPos = new Vector(1, 1);

    vResult = SessRecognition.getPointYearly();
    vEmp = (Vector) vResult.get(0);
    vPoint = (Vector) vResult.get(1);
    vName = (Vector) vResult.get(2);
    vDep = (Vector) vResult.get(3);
    vPos = (Vector) vResult.get(4);
    vEmpId = (Vector) vResult.get(5);
    vEmpNum = (Vector) vResult.get(6);

    Vector[] vYy = new Vector[vYear.size()];
    Vector[] vY_aEmpId = new Vector[vYear.size()];
    Vector[] vY_aPoint = new Vector[vYear.size()];

    for (int i=0; i<vYy.length; i++) {
        vYy[i] = (Vector) vResult.get(i+7);
        vY_aEmpId[i] = (Vector) vYy[i].get(0);
        vY_aPoint[i] = (Vector) vYy[i].get(1);
    }
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report</title>
<script language="JavaScript">
<!--
//-------------- script control line -------------------
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function cmdPrint(){
    	window.open("<%=approot%>/servlet/com.dimata.harisma.report.recognition.RecYearlyPdf");
    }

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
                  Report &gt; Recognition &gt; Yearly Report<!-- #EndEditable --> 
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
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td> 
                                                  <div align="center"> 
                                                    <table border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                      <tr> 
                                                        <td class="listgensell"> 
                                                          <div align="center"><b>Yearly Recapitulation <%=vYear.get(0)%> - <%=vYear.get(vYear.size()-1)%></b></div>
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
                                                              <td class="listgentitle" colspan="<%=vYear.size()%>" nowrap> 
                                                                <div align="center">Point 
                                                                  accumulated</div>
                                                              </td>
                                                              <td class="listgentitle" rowspan="2" width="12%">Total</td>
                                                            </tr>
                                                            <tr> 
							    <% for (int i = 0; i < vYear.size(); i++) { %>
                                                              <td width="12%" class="listgentitle">
                                                                <div align="center"><%=vYear.get(i)%></div>
                                                              </td>
							    <% } %>
                                                            </tr>
                                                            <% for (int j=0; j<vEmp.size(); j++) { %>
                                                            <tr> 
                                                              <td width="4%" class="listgensell"><%=(j+1)%></td>
                                                              <td width="9%" class="listgensell" nowrap><%=vEmpNum.get(j)%></td>
                                                              <td width="8%" class="listgensell" nowrap><%=vEmp.get(j)%></td>
                                                              <td width="15%" class="listgensell" nowrap><%=vDep.get(j)%></td>
                                                              <td width="10%" class="listgensell" nowrap><%=vPos.get(j)%></td>
							      <% for (int k = 0; k < vYear.size(); k++) {
                                                              %>
                                                                  <td width="12%" class="listgensell">
                                                                    <div align="center">
                                                                    <%
                                                                    for (int a = 0; a < vY_aEmpId[k].size(); a++) {
                                                                        if (Long.parseLong(String.valueOf(vEmpId.get(j))) == Long.parseLong(String.valueOf(vY_aEmpId[k].get(a)))) {
                                                                            out.print(vY_aPoint[k].get(a));
                                                                        }
                                                                    }
                                                                    %>
                                                                    </div>
                                                                  </td>
							      <% } %>
                                                                  <td width="10%" class="listgensell" nowrap>
                                                                <div align="center"><b><%=vPoint.get(j)%></b></div>
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
