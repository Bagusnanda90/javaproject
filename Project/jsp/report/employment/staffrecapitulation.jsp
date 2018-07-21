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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_EMPLOYEMENT_REPORT, AppObjInfo.OBJ_STAFF_RECAPITULATION_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
    System.out.println("privPrint=" + privPrint);
%>
<!-- JSP Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Date recapYear = new Date(FRMQueryString.requestInt(request, "recapYear") - 1900, 0, 1);

    if(iCommand == Command.NONE){
        recapYear = new Date();
    }

    Vector listStaffRecapitulation = new Vector(1, 1);
    SessEmployee sessEmployee = new SessEmployee();
    listStaffRecapitulation = sessEmployee.getStaffRecapitulation(recapYear);
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
        window.open("<%=approot%>/servlet/com.dimata.harisma.report.employment.StaffRecapitulationPdf?recapYear=<%=recapYear.getYear()%>");
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
                  &gt; Employment &gt; Staff Recapitulation<!-- #EndEditable --> 
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
                                      <input type="hidden" name="month" value="">
                                      <input type="hidden" name="year" value="">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td nowrap width="33"><b>Year : </b></td>
                                                <td nowrap width="13"><%=ControlDate.drawDateYear("recapYear", recapYear, "formElemen", 0, -5)%></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                <td class="command" nowrap><a href="javascript:cmdSearch()">Search</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td><hr>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td> 
                                            <table border="0" cellspacing="1" cellpadding="1" class="listgen">
											<%
                                                  Vector vDept = (Vector) listStaffRecapitulation.get(0);
                                                  Vector vJan = (Vector) listStaffRecapitulation.get(1);
                                                  Vector vFeb = (Vector) listStaffRecapitulation.get(2);
                                                  Vector vMar = (Vector) listStaffRecapitulation.get(3);
                                                  Vector vApr = (Vector) listStaffRecapitulation.get(4);
                                                  Vector vMay = (Vector) listStaffRecapitulation.get(5);
                                                  Vector vJun = (Vector) listStaffRecapitulation.get(6);
                                                  Vector vJul = (Vector) listStaffRecapitulation.get(7);
                                                  Vector vAug = (Vector) listStaffRecapitulation.get(8);
                                                  Vector vSep = (Vector) listStaffRecapitulation.get(9);
                                                  Vector vOct = (Vector) listStaffRecapitulation.get(10);
                                                  Vector vNov = (Vector) listStaffRecapitulation.get(11);
                                                  Vector vDec = (Vector) listStaffRecapitulation.get(12);
											%>
										      <tr>
											    <td colspan="14" class="listgensell">
                                                  <div align="center"><b>SUMMARY 
                                                    OF STAFF RECAPITULATION<br>
                                                    YEAR <%=(recapYear.getYear() + 1900)%></b></div>
                                                </td>
											  </tr>
                                              <tr> 
                                                <td class="listgentitle" rowspan="2">No.</td>
                                                <td class="listgentitle" rowspan="2"> 
                                                  <div align="center"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vJan.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">1</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('1','<%=(recapYear.getYear()+1900)%>')">1</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vFeb.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">2</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('2','<%=(recapYear.getYear()+1900)%>')">2</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vMar.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">3</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('3','<%=(recapYear.getYear()+1900)%>')">3</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vApr.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">4</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('4','<%=(recapYear.getYear()+1900)%>')">4</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vMay.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">5</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('5','<%=(recapYear.getYear()+1900)%>')">5</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vJun.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">6</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('6','<%=(recapYear.getYear()+1900)%>')">6</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vJul.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">7</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('7','<%=(recapYear.getYear()+1900)%>')">7</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vAug.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">8</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('8','<%=(recapYear.getYear()+1900)%>')">8</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vSep.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">9</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('9','<%=(recapYear.getYear()+1900)%>')">9</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vOct.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">10</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('10','<%=(recapYear.getYear()+1900)%>')">10</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vNov.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">11</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('11','<%=(recapYear.getYear()+1900)%>')">11</a></div>
                                                  <% } %>
                                                </td>
                                                <td class="listgentitle" width="40"> 
                                                  <% if (String.valueOf(vDec.get(vDept.size()-1)).compareTo("") == 0) { %>
                                                    <div align="center">12</div>
                                                  <% } else { %>
                                                    <div align="center"><a href="javascript:cmdEdit('12','<%=(recapYear.getYear()+1900)%>')">12</a></div>
                                                  <% } %>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td class="listgentitle" colspan="12"> 
                                                  <div align="center">Permanent 
                                                    Staff </div>
                                                </td>
                                              </tr>
                                              <% 
                                                  for (int i=0; i<vDept.size() - 1; i ++) {
                                                %>
                                              <tr> 
                                                <td class="listgensell" nowrap><%=(i+1)%></td>
                                                <td class="listgensell" nowrap><%=vDept.get(i)%></td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vJan.get(i)%></div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vFeb.get(i)%></div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vMar.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vApr.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vMay.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vJun.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vJul.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vAug.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vSep.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vOct.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vNov.get(i)%> 
                                                  </div>
                                                </td>
                                                <td class="listgensell" width="40"> 
                                                  <div align="center"><%=vDec.get(i)%> 
                                                  </div>
                                                </td>
                                              </tr>
                                              <% 
                                                }
                                                %>
                                              <tr> 
                                                <td class="listgensell"> 
                                                  <div align="center"><b></b></div>
                                                </td>
                                                <td class="listgensell" nowrap> 
                                                  <div align="center"><b>Total 
                                                    Permanent Staff</b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vJan.get(vDept.size()-1)%></b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vFeb.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vMar.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vApr.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vMay.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vJun.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vJul.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vAug.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vSep.get(vDept.size()-1)%></b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vOct.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vNov.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                                <td class="listgensell"> 
                                                  <div align="center"><b><%=vDec.get(vDept.size()-1)%> 
                                                    </b></div>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr><td>&nbsp;</td>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
