
<%-- 
    Document   : leave_dp_department_by_period
    Created on : Aug 21, 2010, 10:53:48 AM
    Author     : roy a
--%>


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
                  com.dimata.harisma.entity.leave.*,
                  com.dimata.harisma.form.leave.*,
                  com.dimata.harisma.session.leave.RepItemLeaveAndDp"%>
                  
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_DP_SUMMARY); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * create list of report items
 */
public String drawList(Vector listCurrStock) 
{ 
    String result = "";   

    if(listCurrStock!=null && listCurrStock.size()>0)
    {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");

            ctrlist.addHeader("NO","2%", "2", "0");
            ctrlist.addHeader("DEPT.","10%", "2", "0");
            ctrlist.addHeader("#EMPLOYEE","4%", "2", "0");
            ctrlist.addHeader("DAY OF PAYMENT","28%", "0", "7");	
            ctrlist.addHeader("ANNUAL LEAVE","24%", "0", "6");
            ctrlist.addHeader("LONG LEAVE","28%", "0", "7");
            
            ctrlist.addHeader("QTY PREVIOUS","4%", "0", "0");
            ctrlist.addHeader("TAKEN PREVIOUS","4%", "0", "0");
            ctrlist.addHeader("EXPIRED PREVIOUS","4%", "0", "0");
            ctrlist.addHeader("QTY","4%", "0", "0");
            ctrlist.addHeader("TAKEN","4%", "0", "0");            
            ctrlist.addHeader("EXPIRED","4%", "0", "0");
            ctrlist.addHeader("BALANCE","4%", "0", "0");
            
            ctrlist.addHeader("QTY PREVIOUS","4%", "0", "0");
            ctrlist.addHeader("TAKEN PREVIOUS","4%", "0", "0");
            ctrlist.addHeader("ENTITLE","4%", "0", "0");
            ctrlist.addHeader("QTY","4%", "0", "0");
            ctrlist.addHeader("TAKEN","4%", "0", "0");
            ctrlist.addHeader("BALANCE ","4%", "0", "0");
            
            ctrlist.addHeader("QTY PREVIOUS","4%", "0", "0");
            ctrlist.addHeader("TAKEN PREVIOUS ","4%", "0", "0");
            ctrlist.addHeader("ENTITLE","4%", "0", "0");
            ctrlist.addHeader("QTY","4%", "0", "0");
            ctrlist.addHeader("TAKEN","4%", "0", "0");
            ctrlist.addHeader("EXPIRED","4%", "0", "0");
            ctrlist.addHeader("BALANCE","4%", "0", "0");

            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            // iterasi sebanyak data Special Leave yang ada
            int iterateNo           = 0;            
            int totalEmp            = 0;
            
            /* ==== DP ==== */
            float totalDpQtyBefore    = 0;
            float totalDpQtyTknBefore = 0;
            float totalDpQtyExpBefore = 0;
            float totalDpQtyCurrent   = 0;
            float totalDpQtyTknCurrent= 0;
            float totalDpQtyExpCurrent= 0;
            float totalBalanceDp      = 0;
            
            /* ===== AL ===== */
            float totalAlQtyBefore    = 0;
            float totalAlQtyTknBefore = 0;            
            float totalAlQtyCurrent   = 0;
            float totalAlQtyTknCurrent= 0;
            float totalBalanceAl      = 0;            
            
            /* int total = 0; */
            for (int i=0; i<listCurrStock.size(); i++) 
            {
                
                float Dp_Balance = 0;
                float Al_Balance = 0;
                float Ll_Balance = 0;
                
                RepLevDepartment repLevDepartment = new RepLevDepartment();
                repLevDepartment = (RepLevDepartment)listCurrStock.get(i);
                                
                iterateNo++;
                totalEmp = totalEmp + repLevDepartment.getCountEmployee();   
                
                Vector rowx = new Vector(1,1);
                
                /* ========== Employee ============ */
                rowx.add(""+iterateNo);
                rowx.add(""+repLevDepartment.getDepartment());
                rowx.add(""+repLevDepartment.getCountEmployee());
                
                /* ==== DP ====== */
                Dp_Balance = repLevDepartment.getDpQtyBeforeStartPeriod() - repLevDepartment.getDpTknBeforeStartPeriod() - repLevDepartment.getDpTknExpBeforeStartPeriod() + repLevDepartment.getDpQtyCurrentPeriod() - repLevDepartment.getDpTknCurrentPeriod() - repLevDepartment.getDpTknExpiredCurrentPeriod();
                rowx.add(""+repLevDepartment.getDpQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpiredCurrentPeriod());
                rowx.add(""+Dp_Balance);
                
                totalDpQtyBefore = totalDpQtyBefore + repLevDepartment.getDpQtyBeforeStartPeriod();
                totalDpQtyTknBefore = totalDpQtyTknBefore + repLevDepartment.getDpTknBeforeStartPeriod();
                totalDpQtyExpBefore = totalDpQtyExpBefore + repLevDepartment.getDpTknExpBeforeStartPeriod();
                totalDpQtyCurrent = totalDpQtyCurrent + repLevDepartment.getDpTknExpBeforeStartPeriod();
                totalDpQtyTknCurrent = totalDpQtyTknCurrent + repLevDepartment.getDpQtyCurrentPeriod();
                totalDpQtyExpCurrent = totalDpQtyExpCurrent + repLevDepartment.getDpTknExpiredCurrentPeriod(); 
                totalBalanceDp = totalBalanceDp + Dp_Balance;
                                
                /* ============= AL ========= */
                
                Al_Balance = repLevDepartment.getAlQtyBeforeStartPeriod() - repLevDepartment.getAlTknBeforeStartPeriod() + repLevDepartment.getAlQtyCurrentPeriod() - repLevDepartment.getAlTknCurrentPeriod();
                rowx.add(""+repLevDepartment.getAlQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getAlTknCurrentPeriod());
                rowx.add(""+Al_Balance);
                
                totalAlQtyBefore = totalAlQtyBefore + repLevDepartment.getAlQtyBeforeStartPeriod(); 
                totalAlQtyTknBefore = totalAlQtyTknBefore + repLevDepartment.getAlTknBeforeStartPeriod(); 
                totalAlQtyCurrent = totalAlQtyCurrent + repLevDepartment.getAlQtyCurrentPeriod(); 
                totalAlQtyTknCurrent = totalAlQtyTknCurrent + repLevDepartment.getAlTknCurrentPeriod(); 
                totalBalanceAl = totalBalanceAl + Al_Balance; 
                
                /* ========= LL ========= */                
                rowx.add(""+0);
                rowx.add(""+0);
                rowx.add(""+0);
                rowx.add(""+0);                
                rowx.add(""+0); 
                rowx.add(""+0);                                              
                rowx.add(""+Ll_Balance);                                              
                
                lstData.add(rowx);
                lstLinkData.add("0");
            }	
            
            Vector rowx = new Vector(1,1);  
                
            rowx.add("");
            rowx.add("<b>TOTAL</b>");
            rowx.add("<b>"+totalEmp+"</b>");
            
            /* ====== DP ===== */
            rowx.add("<b>"+totalDpQtyBefore+"</b>");
            rowx.add("<b>"+totalDpQtyTknBefore+"</b>");
            rowx.add("<b>"+totalDpQtyExpBefore+"</b>");
            rowx.add("<b>"+totalDpQtyCurrent+"</b>");
            rowx.add("<b>"+totalDpQtyTknCurrent+"</b>");
            rowx.add("<b>"+totalDpQtyExpCurrent+"</b>");
            rowx.add("<b>"+totalBalanceDp+"</b>");                        
            
            /* ===== AL ==== */
            rowx.add("<b>"+totalAlQtyBefore+"</b>");
            rowx.add("<b>"+totalAlQtyTknBefore+"</b>");
            rowx.add("<b>"+totalAlQtyCurrent+"</b>");
            rowx.add("<b>"+totalAlQtyTknCurrent+"</b>");
            rowx.add("<b>"+totalBalanceAl+"</b>");
            

            lstData.add(rowx);
            lstLinkData.add("0");

            result = ctrlist.drawList();
            
    }
    else
    {					
            result += "<div class=\"msginfo\">&nbsp;&nbsp;Leave and Dp Stock Data Found found ...</div>";																
    }
    return result;	
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
Date currPeriod = FRMQueryString.requestDate(request,"currPeriod");

/* Try */

CtrlLeaveApplication ctrlLeaveApplication = new CtrlLeaveApplication(request);

LeaveApplication objLeaveApplication = ctrlLeaveApplication.getLeaveApplication();    

/* End Try */
// for list
Vector listCurrStock = new Vector(1,1);
LeaveTarget leaveTarget = new LeaveTarget();

if(Command.LIST==iCommand){    
      
        listCurrStock = SessLeaveApplication.sumLeave_Department(new Date(), new Date());
        
        session.removeValue("SESS_LEAVE_DP_SUM_REPORT");
        session.putValue("SESS_LEAVE_DP_SUM_REPORT", listCurrStock);
        
    }

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Leave adn DP Stock Report By Period</title>
<script language="JavaScript">
<!--
function cmdView()
{
	document.frSpUnpaid.command.value="<%=String.valueOf(Command.LIST)%>";
	document.frSpUnpaid.action="leave_dp_department_by_period.jsp";
	document.frSpUnpaid.submit();
}

function cmdPrint()
{
        pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveApplicationReportPdf?oidLeaveApplication=0&approot=<%=approot%>";	
        window.open(pathUrl);
}

function cmdPrintXls()
{ 
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveDpSumXls";
    window.open(pathUrl);
}

//-->
</script>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">    
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<script language="JavaScript">

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
         
function fnTrapKD(){
   if (event.keyCode == 13) {
	document.all.aSearch.focus();
	cmdSearch();
   }
}

function hideObjectForDate(index){
}

function showObjectForDate(){
}         
    
function getStartPeriod(){
    <%=ControlDatePopup.writeDateCaller("frSpUnpaid","start_date")%>
}  

function getEndPeriod(){
    <%=ControlDatePopup.writeDateCaller("frSpUnpaid","end_date")%>
}  

</script>
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
                  &gt; Leave and DP &gt; Stock<!-- #EndEditable --> 
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
                                    <form name="frSpUnpaid" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start_date" value="">                                      
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="18%" nowrap>Periode : 
                                            <div align="left"></div>
                                          </td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%">                                              
                                          
                                                <%=ControlDatePopup.writeDate("start_date",new Date(), "getStartPeriod()")%>                                                
                                                
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="18%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%">&nbsp;</td>
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
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View Current Stock Report</a></td>
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
                                          <td><%=drawList(listCurrStock)%></td>
                                        </tr>
                                        <%
                                            if(listCurrStock.size()>0 && privPrint)
                                            {
                                        %>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr>
                                                <td width="24"><a href="javascript:cmdPrintXls()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdPrintXls()">Print Report XLS</a></td>
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

