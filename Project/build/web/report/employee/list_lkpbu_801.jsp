
<%@page import="com.dimata.harisma.entity.report.lkpbu.PstLkpbu"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  java.text.*,				  
                  com.dimata.qdep.form.*,				  
                  com.dimata.gui.jsp.*,
                  com.dimata.util.*,				  
                  com.dimata.harisma.entity.masterdata.*,				  				  
                  com.dimata.harisma.entity.employee.*,
                  com.dimata.harisma.entity.attendance.*,
                  com.dimata.harisma.entity.search.*,
                  com.dimata.harisma.form.masterdata.*,				  				  
                  com.dimata.harisma.form.attendance.*,
                  com.dimata.harisma.form.search.*,				  
                  com.dimata.harisma.session.attendance.*,
                  com.dimata.harisma.session.leave.SessLeaveApp,
                  com.dimata.harisma.session.leave.*,
                  com.dimata.harisma.session.attendance.SessLeaveManagement,
                  com.dimata.harisma.session.leave.RepItemLeaveAndDp"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_DP_SUMMARY); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
/**
 * create list of report items
 */
public String drawList(Vector listResult) 
{ 
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
                
        ctrlist.addHeader("Status Data","2%", "0", "0");
        ctrlist.addHeader("NIP","2%", "0", "0");
        ctrlist.addHeader("Nama Pejabat Eksekutif","2%", "0", "0");
        ctrlist.addHeader("Status Tenaga Kerja","2%", "0", "0");	
        ctrlist.addHeader("Nama Jabatan","2%", "0", "0");
        ctrlist.addHeader("Alamat Rumah (Saat Ini)","2%", "0", "0");

        ctrlist.addHeader("Alamat KTP atau Paspor atau KITAS (WNA)","20%", "0", "0");
        ctrlist.addHeader("No. Telp","2%", "0", "0");
        ctrlist.addHeader("No. Fax","2%", "0", "0");
        ctrlist.addHeader("NPWP","2%", "0", "0");
        ctrlist.addHeader("No. ID","2%", "0", "0");            

        ctrlist.addHeader("Tempat Lahir","4%", "0", "0");
        ctrlist.addHeader("Tgl Lahir ","4%", "0", "0");
        ctrlist.addHeader("Kewarganegaraan","4%", "0", "0");
        ctrlist.addHeader("Jenis kelamin","4%", "0", "0");
        ctrlist.addHeader("No. Surat Pelaporan","4%", "0", "0");
        ctrlist.addHeader("Tanggal Surat Pelaporan ","4%", "0", "0");

        ctrlist.addHeader("Status PE","4%", "0", "0");
        ctrlist.addHeader("Nomor Surat Keputusan Pengangkatan/Penggantian/Penggantian Sementara","20%", "0", "0");
        ctrlist.addHeader("Tanggal Efektif Pengangkatan/Penggantian/Penggantian Sementara","20%", "0", "0");
        ctrlist.addHeader("Nomor Surat Keputusan Pemberhentian","20%", "0", "0");
        ctrlist.addHeader("Tanggal Efektif Pemberhentian","4%", "0", "0");
        ctrlist.addHeader("Keterangan","4%", "0", "0");

        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Vector rowx = new Vector(1,1);
        for(int i = 0; i < listResult.size(); i++) {
            Employee employee = (Employee)listResult.get(i);

            rowx = new Vector(1,1);
            rowx.add("1");
            rowx.add("0000000000000000"+employee.getEmployeeNum());
            
            String fullname = employee.getFullName();
            String[] splits = fullname.split(",");
            ArrayList name= new ArrayList();
            for(int j=0; j < splits.length ; j++){
                name.add(splits[j].trim());
            }
            rowx.add(""+splits[0]);
            rowx.add("1");
            rowx.add(employee.getPositionName()+" "+employee.getDepartmentName());
            rowx.add(""+employee.getAddress());
            rowx.add(""+employee.getAddressPermanent());
            rowx.add(""+employee.getPhone());
            rowx.add("");
            
            String npwp = employee.getNpwp();
            npwp = npwp.replaceAll("[-,.]", "");
            rowx.add(""+npwp);
            
            rowx.add(""+employee.getIndentCardNr());
            rowx.add(employee.getBirthPlace());
            rowx.add(Formater.formatDate(employee.getBirthDate(),"ddMMyyyy"));
            rowx.add("ID");
            if(employee.getSex() == 0) {
                rowx.add("1");
            } else {
                rowx.add("2");
            }

            rowx.add("");
            rowx.add("");
            rowx.add("1");
            rowx.add("");
            rowx.add(Formater.formatDate(employee.getCommencingDate(),"ddMMyyyy"));
            rowx.add("");
            rowx.add(Formater.formatDate(employee.getResignedDate(),"ddMMyyyy"));
            rowx.add("");
            
            lstData.add(rowx);
            lstLinkData.add("0");
        }
     
        return ctrlist.drawList();	
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
int year = FRMQueryString.requestInt(request,"year");

Vector listResult = new Vector(1,1);
listResult = PstEmployee.listEmployeeKadiv(year);

Vector listKadiv= new Vector(1,1);

// for list

Employee employee = new Employee();

session.putValue("listresult", listResult);
//get value kadiv HRD
String kadivPositionOid = PstSystemProperty.getValueByName("HR_DIR_POS_ID");
String whereClauseOidPosition = "POSITION_ID='"+kadivPositionOid+"'";

listKadiv = PstLkpbu.listPosition(whereClauseOidPosition);
session.putValue("listkadiv", listKadiv);

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - LKPBU 801 Report</title>
<script language="JavaScript">
<!--
function cmdView()
{
	document.lkpbu.command.value="<%=String.valueOf(Command.LIST)%>";
	document.lkpbu.action="list_lkpbu_801.jsp";
	document.lkpbu.submit();
}

function cmdPrint()
{
        pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveApplicationReportPdf?oidLeaveApplication=0&approot=<%=approot%>";
	//var linkPage = "<%//=printroot%>//.report.leave.LeaveDPStockReportXls?ms=<%//=String.valueOf((new Date()).getTime())%>//";
	//window.open(linkPage);pathUrl
         window.open(pathUrl);
}

function cmdPrintXls()
{ 
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveDpSumXls";
    window.open(pathUrl);
}

function cmdExportExcel(){
                 
    var linkPage = "<%=approot%>/report/employee/export_excel/export_excel_list_lkpbu_801.jsp";    
    var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
     newWin.focus();

    //document.frpresence.action="rekapitulasi_absensi.jsp"; 
    //document.frpresence.target = "SummaryAttendance";
    //document.frpresence.submit();
    //document.frpresence.target = "";
}

//-------------- script control line -------------------
function MM_swapImgRestore() 
{ //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<!-- #EndEditable -->
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report 
                  &gt; Employee &gt; LKPBU Form 801<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" -->
                                    <form name="lkpbu" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="18%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="1%"><%=ControlDate.drawDateYear("year", year==0? Validator.getIntYear(new Date()) : year ,"formElemen", -40)%></td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report LKPBU 801"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View LKPBU 801 Report</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>									  
									  
									  
                                      <% 
                                      if(iCommand == Command.LIST)
                                      {
                                      %>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr>
                                          <td><hr></td>
                                        </tr>
                                        <tr>
                                          <td><%=drawList(listResult)%></td>
                                        </tr>
                                        <%
                                            if(listResult.size()>0 && privPrint)
                                            {
                                        %>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr>
                                                <td width="24"><a href="javascript:cmdExportExcel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdExportExcel()">Export to Excel</a></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                      </table>
                                      <%
                                          }                                         
                                      %>									  
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
