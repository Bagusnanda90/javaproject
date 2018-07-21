<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
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
                  com.dimata.harisma.session.leave.SessLeaveApp"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_ATTENDANCE_RECORD_REPORT); %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_LL_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * create list object
 * consist of : 
 *  first index  ==> status object (will displayed or not)
 *  second index ==> object string will displayed
 *  third index  ==> object vector of string used in report on PDF format.
 */
public String drawList(Vector listSpecialLeave) 
{
    String result = "";
    if(listSpecialLeave!=null && listSpecialLeave.size()>0)
    {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");

            ctrlist.addHeader("NO","3%", "2", "0");
            ctrlist.addHeader("PAYROLL","7%", "2", "0");
            ctrlist.addHeader("NAME","20%", "2", "0");
            ctrlist.addHeader("DEPARTMENT","10%", "2", "0");	
            ctrlist.addHeader("POSITION","10%", "2", "0");
            ctrlist.addHeader("NO OF DAYS REQ","10%", "2", "0");
            ctrlist.addHeader("UNPAID LEAVE","20%", "1", "2");
            ctrlist.addHeader("FROM","10%", "0", "0");
            ctrlist.addHeader("TO","10%", "0", "0");
            ctrlist.addHeader("REMARK","20%", "2", "0");

            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();

            // iterasi sebanyak data Special Leave yang ada
            int iterateNo = 0;
            boolean resultAvailable = false;
            int total = 0;
            for (int i=0; i<listSpecialLeave.size(); i++) 
            {
                Vector vSpList = new Vector(1,1);
                vSpList = (Vector)listSpecialLeave.get(i);

                SpecialLeave sp = new SpecialLeave();
                Employee emp = new Employee();
                Department dep = new Department();
                Position pos = new Position();
                Vector vDetail = new Vector(1,1);

                
                iterateNo += 1;
                sp = (SpecialLeave)vSpList.get(0);
                emp = (Employee)vSpList.get(1);
                dep = (Department)vSpList.get(2);
                pos = (Position)vSpList.get(3);
                vDetail = (Vector)vSpList.get(4);

                String strCount = (String)vDetail.get(0);

                int count = Integer.parseInt(strCount);
                Date dtStart = (Date)vDetail.get(1);
                Date dtEnd = (Date)vDetail.get(2);

                total += count;
                
                Vector rowx = new Vector(1,1);  
                
                rowx.add(""+iterateNo);
                rowx.add(""+emp.getEmployeeNum());
                rowx.add(""+emp.getFullName());
                rowx.add(""+dep.getDepartment());
                rowx.add(""+pos.getPosition());
                rowx.add(""+count);
                rowx.add(Formater.formatDate(dtStart, "dd-MMM-yyyy"));
                rowx.add(Formater.formatDate(dtEnd, "dd-MMM-yyyy"));
                
                String strRes =(sp.getUnpaidLeaveReason()!=null?sp.getUnpaidLeaveReason():"");
                String strRem =(sp.getOtherRemarks()!=null?sp.getOtherRemarks():"");
                
                String strResAndRem = "";
                if(strRes.length()>0){
                    strResAndRem = strRes;
                    if(strRem.length()>0){
                        strResAndRem += "<br>("+strRem+")";
                    }
                }else{
                    if(strRem.length()>0){
                        strResAndRem += "("+strRem+")";
                    }
                }
                
                rowx.add(strResAndRem);
                
                lstData.add(rowx);
                lstLinkData.add("0");
            }	
            
            Vector rowx = new Vector(1,1);  
                
            rowx.add("");
            rowx.add("");
            rowx.add("<b>Total : </b>");
            rowx.add("");
            rowx.add("");
            rowx.add(""+total);
            rowx.add("");
            rowx.add("");
            rowx.add("");
            
            lstData.add(rowx);
            lstLinkData.add("0");

            result = ctrlist.drawList();
            
    }
    else
    {					
            result += "<div class=\"msginfo\">&nbsp;&nbsp;No Unpaid leave data found ...</div>";																
    }
    return result;	
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
int year = FRMQueryString.requestInt(request,"year");

// for list
Vector listSpecialLeave = new Vector(1,1);
if(Command.LIST==iCommand){
    Date date = new Date(year-1900,0,1);
    listSpecialLeave = SessLeaveApp.listUnpaidLeave(date);
    session.removeValue("SESS_UNPAID_LEAVE");
    session.putValue("SESS_UNPAID_LEAVE", listSpecialLeave);
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Unpaid Leave Report</title>
<script language="JavaScript">
<!--
function cmdView()
{
	document.frSpUnpaid.command.value="<%=String.valueOf(Command.LIST)%>";
	document.frSpUnpaid.action="unpaid_leave.jsp";
	document.frSpUnpaid.submit();
}

function cmdPrint()
{
	var linkPage = "<%=printroot%>.report.leave.UnpaidLeaveReportXls?year=<%=String.valueOf(year)%>";
	window.open(linkPage);
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report 
                  &gt; Attendance &gt; Unpaid Leave Report<!-- #EndEditable --> 
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
                                    <form name="frSpUnpaid" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Period</td>
                                          <td width="3%">:</td>
                                          <td width="78%">
                                              <select name="year" class="formElemen">
                                                  <%
                                                  Date currDate = new Date();
                                                  int iYear = currDate.getYear()+1900;
                                                  int startYear = 1998;
                                                  for(int i=1998;i<=iYear;i++){
                                                  %>
                                                    <option value="<%=String.valueOf(i)%>" <%=(i==iYear?"selected":"")%>><%=String.valueOf(i)%></option>
                                                    <%}%>
                                            </select>
                                            </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="18%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report AL"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Report Unpaid Leave</a></td>
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
                                          <td><%=drawList(listSpecialLeave)%></td>
                                        </tr>
                                        <%
                                            if(listSpecialLeave.size()>0 && privPrint)
                                            {
                                        %>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr>
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdPrint()">Print Report</a></td>
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
