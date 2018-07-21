<%@ page language="java" %>
<%
/* 
 * Page Name  		:  lockerglobal.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.session.locker.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_REPORT, AppObjInfo.G2_RPT_LOCKER, AppObjInfo.OBJ_RPT_LOCKERS); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
    privStart 	= true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<!-- JSP Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Vector listLocker = new Vector(1, 1);
    SessLocker sessLocker = new SessLocker();
    listLocker = sessLocker.getLockersGlobal();

    Vector listLocation = (Vector) listLocker.get(0);
    Vector listCount = (Vector) listLocker.get(1);
    Vector listLocationId = (Vector) listLocker.get(2);
    //Vector lockercondition = PstLockerCondition.listAll();

    int iRow = 0;
    int iCol = 0;
    Vector listLockerCondition = new Vector(1,1);
    listLockerCondition = PstLockerCondition.listAll();
    iRow = listLocation.size(); //=4
    iCol = listLockerCondition.size(); //=5
    int[][] arrLC = new int[iRow][iCol];
    //System.out.println("iRow="+iRow + " - iCol="+iCol);
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Reports</title>
<script language="JavaScript">
    function cmdPrint(){
        window.open("<%=approot%>/servlet/com.dimata.harisma.report.lockers.LockersGlobalPdf");
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
                  &gt; Lockers &gt; Lockers<!-- #EndEditable --> </strong></font> 
                </td>
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
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
									    <tr><td>&nbsp;</td></tr>
                                        <tr>
                                          <td> 
                                              <table border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                <tr> 
                                                  <td class="listgensell"> 
                                                    <div align="center"><b>LOCKERS REPORT<br>
                                                      AS OF <%=Formater.formatDate(new Date(), "dd MMMM yyyy")%></b></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <td class="listgensell">
                                                    
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                              <%
                                                int iLockers = 0;
                                                int iNokeys = 0;
                                              %>
                                              <tr> 
                                                <td class="listgentitle" rowspan="2">No.</td>
                                                <td class="listgentitle" rowspan="2">Location</td>
                                                <td class="listgentitle" rowspan="2">Total 
                                                  Lockers</td>
                                                <td class="listgentitle" colspan="<%=listLockerCondition.size()%>"> 
                                                  <div align="center">Condition</div>
                                                </td>
                                                <td class="listgentitle" rowspan="2">No 
                                                  Keys</td>
                                              </tr>
                                              <tr> 
                                                <%
                                                    for (int i = 0; i < listLockerCondition.size(); i++) {
                                                        LockerCondition lockerCondition = (LockerCondition) listLockerCondition.get(i);
                                                %>
                                                <td class="listgentitle"><%=lockerCondition.getCondition()%></td>
                                                <%
                                                    }
                                                %>
                                              </tr>
                                              <% 
                                                for (int i = 0; i < listLocation.size(); i ++) {
                                                    //System.out.println("\t...i=" + i);
                                              %>
                                              <tr> 
                                                <td class="listgensell" nowrap><div align="center"><%=(i+1)%></div></td>
                                                <td class="listgensell"><%=listLocation.get(i)%></td>
                                                <td class="listgensell"><div align="center"><%=listCount.get(i)%></div>
                                                  <%
                                                    iLockers += Integer.parseInt(String.valueOf(listCount.get(i)));
                                                  %>
                                                </td>
                                                <%
                                                int LC = 0;
                                                for (int j = 0; j < listLockerCondition.size(); j++) {
                                                    //System.out.println("\t...j=" + j);
                                                    LockerCondition lockerCondition = (LockerCondition) listLockerCondition.get(j);
                                                    LC = SessLocker.getLockerConditionCount(String.valueOf(listLocation.get(i)), String.valueOf(lockerCondition.getCondition()));
                                                    arrLC[i][j] = LC;
                                                %>
                                                    <td class="listgensell"><div align="center"><%=LC%></div></td>
                                                <%
                                                }
                                                %>
                                                <td class="listgensell">
                                                    <div align="center"><%=SessLocker.getLockerNoKeys(String.valueOf(listLocationId.get(i)))%></div>
                                                  <%
                                                    iNokeys += SessLocker.getLockerNoKeys(String.valueOf(listLocationId.get(i)));
                                                  %>
                                                </td>
                                              </tr>
                                              <% 
                                                }
                                              %>
                                              <tr> 
                                                <td class="listgensell">&nbsp; </td>
                                                <td class="listgensell"><div align="center"><b>TOTAL</b></div></td>
                                                <td class="listgensell"><div align="center"><b><%=iLockers%></b></div></td>
                                                <%
                                                    //iRow = listLocation.size();
                                                    //iCol = listLockerCondition.size();
                                                    int temp = 0;
                                                    for (int k = 0; k < listLockerCondition.size(); k++) {
                                                        temp = 0;
                                                        for (int m = 0; m < listLocation.size(); m++) {
                                                            temp += arrLC[m][k];
                                                        }
                                                %>
                                                        <td class="listgensell"><div align="center"><b><%=temp%></b></div></td>
                                                <%
                                                    }
                                                %>
                                                <td class="listgensell"><div align="center"><b><%=iNokeys%></b></div></td>
                                              </tr>
                                            </table>
                                                    </td>
                                                </tr>
                                              </table>
                                          </td>
                                        </tr>
                                        <tr><td>&nbsp;</td>
                                        </tr>
										<%if(privPrint){%>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
