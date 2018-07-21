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
                  com.dimata.harisma.session.leave.SessLeaveApp"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%// int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_LL_REPORT); %>
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
public String drawList(Vector listSpecialLeave, LeaveTarget leaveTarget, Date currDate) 
{
    String result = "";
    DecimalFormat dc = new DecimalFormat("0");
    DecimalFormat dc2 = new DecimalFormat("0.##");
    Date dateLastMonth = (Date)currDate.clone();
    dateLastMonth.setDate(1);
    dateLastMonth.setMonth(currDate.getMonth()+1);
    //Mencari tanggal terakhir
    dateLastMonth.setDate(dateLastMonth.getDate()-1);
    if(listSpecialLeave!=null && listSpecialLeave.size()>0)
    {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");

            ctrlist.addHeader("NO","2%", "2", "0");
            ctrlist.addHeader("DEPT.","10%", "2", "0");
            ctrlist.addHeader("No. of Employee","4%", "2", "0");
            ctrlist.addHeader("DP","28%", "0", "7");	
            ctrlist.addHeader("AL","28%", "0", "7");
            ctrlist.addHeader("LL","28%", "0", "7");
            
            ctrlist.addHeader("Prev. Balance","4%", "0", "0");
            ctrlist.addHeader("Entitle "+Formater.formatDate(dateLastMonth, "MMM"),"4%", "0", "0");
            ctrlist.addHeader("Sub Total","4%", "0", "0");
            ctrlist.addHeader("To Taken","4%", "0", "0");
            ctrlist.addHeader("Taken "+Formater.formatDate(dateLastMonth, "MMM"),"4%", "0", "0");
            ctrlist.addHeader("% Taken","4%", "0", "0");
            ctrlist.addHeader("Balance "+Formater.formatDate(dateLastMonth, "dd.MM.yy"),"4%", "0", "0");
            
            ctrlist.addHeader("Prev. Balance","4%", "0", "0");
            ctrlist.addHeader("Entitle "+Formater.formatDate(dateLastMonth, "MMM"),"4%", "0", "0");
            ctrlist.addHeader("Sub Total","4%", "0", "0");
            ctrlist.addHeader("To Taken","4%", "0", "0");
            ctrlist.addHeader("Taken "+Formater.formatDate(dateLastMonth, "MMM"),"4%", "0", "0");
            ctrlist.addHeader("% Taken","4%", "0", "0");
            ctrlist.addHeader("Balance "+Formater.formatDate(dateLastMonth, "dd.MM.yy"),"4%", "0", "0");
            
            ctrlist.addHeader("Prev. Balance","4%", "0", "0");
            ctrlist.addHeader("Entitle "+Formater.formatDate(dateLastMonth, "MMM"),"4%", "0", "0");
            ctrlist.addHeader("Sub Total","4%", "0", "0");
            ctrlist.addHeader("To Taken","4%", "0", "0");
            ctrlist.addHeader("Taken "+Formater.formatDate(dateLastMonth, "MMM"),"4%", "0", "0");
            ctrlist.addHeader("% Taken","4%", "0", "0");
            ctrlist.addHeader("Balance "+Formater.formatDate(dateLastMonth, "dd.MM.yy"),"4%", "0", "0");

            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            //Iterate To Take
         /*   Hashtable hToTakeDP = new Hashtable();
            Hashtable hToTakeAL = new Hashtable();
            Hashtable hToTakeLL = new Hashtable();
            
            hToTakeDP = SessLeaveApp.getToTakeDP(currDate);
            hToTakeAL = SessLeaveApp.getToTakeAL(currDate);
            hToTakeLL = SessLeaveApp.getToTakeLL(currDate); */
            
            // iterasi sebanyak data Special Leave yang ada
            int iterateNo = 0;
            boolean resultAvailable = false;
            int totalEmp = 0;
            int totalDPBlc = 0;
            int totalDPent = 0;
            int totalDPTOTake = 0;
            int totalDPTaken = 0;
            
            int totalALBlc = 0;
            int totalALent = 0;
            int totalALTOTake = 0;
            int totalALTaken = 0;
            
            int totalLLBlc = 0;
            int totalLLent = 0;
            float totalLLTOTake = 0;
            int totalLLTaken = 0;
            
            
            int total = 0;
            for (int i=0; i<listSpecialLeave.size(); i++) 
            {
                iterateNo += 1;
                
                Vector vSpList = new Vector(1,1);
                vSpList = (Vector)listSpecialLeave.get(i);

                Department dep = new Department();
                String strEmpCount = "";
                Vector vDP = new Vector(1,1);
                Vector vAL = new Vector(1,1);
                Vector vLL = new Vector(1,1);
                LeaveTargetDetail objLeaveTargetDetail = new LeaveTargetDetail();

                String strDPBlc = "";
                String strDPEnt = "";
                String strDPToTaken = "";
                String strDPTaken = "";

                String strALBlc = "";
                String strALEnt = "";
                String strALToTaken = "";
                String strALTaken = "";

                String strLLBlc = "";
                String strLLEnt = "";
                String strLLToTaken = "";
                String strLLTaken = "";
                
                //Inisialisasi Data
                dep = (Department)vSpList.get(0);
                strEmpCount = (String)vSpList.get(1);
                int empCount = Integer.parseInt(strEmpCount);
                vDP = (Vector)vSpList.get(2);
                vAL = (Vector)vSpList.get(3);
                vLL = (Vector)vSpList.get(4);
                
                totalEmp += empCount;
                
                strDPBlc = (String)vDP.get(0);
                strDPEnt = (String)vDP.get(1);
                strDPTaken = (String)vDP.get(2);
                strDPToTaken = ""+dc.format((leaveTarget.getDpTarget()>0?leaveTarget.getDpTarget():0));
                int dpBlc = Integer.parseInt(strDPBlc);
                int dpEnt = Integer.parseInt(strDPEnt);
                float dpToTaken = Float.parseFloat(strDPToTaken);
                int dpTaken = Integer.parseInt(strDPTaken);
                
                totalDPBlc += dpBlc;
                totalDPent += dpEnt;
                totalDPTaken += dpTaken;
                
                strALBlc = (String)vAL.get(0);
                strALEnt = (String)vAL.get(1);
                strALTaken = (String)vAL.get(2);
                strALToTaken = ""+dc.format((leaveTarget.getAlTarget()>0?leaveTarget.getAlTarget():0));
                int alBlc = Integer.parseInt(strALBlc);
                int alEnt = Integer.parseInt(strALEnt);
                float alToTaken = Float.parseFloat(strALToTaken);
                int alTaken = Integer.parseInt(strALTaken);
                
                totalALBlc += alBlc;
                totalALent += alEnt;
                totalALTaken += alTaken;
                
                strLLBlc = (String)vLL.get(0);
                strLLEnt = (String)vLL.get(1);
                strLLTaken = (String)vLL.get(2);
                strLLToTaken = ""+dc.format((leaveTarget.getLlTarget()>0?leaveTarget.getLlTarget():0));
                int llBlc = Integer.parseInt(strLLBlc);
                int llEnt = Integer.parseInt(strLLEnt);
                float llToTaken = Float.parseFloat(strLLToTaken);
                int llTaken = Integer.parseInt(strLLTaken);
                
                totalLLBlc += llBlc;
                totalLLent += llEnt;
                totalLLTaken += llTaken;
                
                double dToTakeDp = (dpBlc+dpEnt)*dpToTaken/100;
                double dToTakeAl = (alBlc+alEnt)*alToTaken/100;
                double dToTakeLl = (llBlc+llEnt)*llToTaken/100;
                
                String strToDP = dc.format(dToTakeDp);
                String strToAL = dc.format(dToTakeAl);
                String strToLL = dc.format(dToTakeLl);
                
                int iToTakeDp = Integer.parseInt(strToDP);
                int iToTakeAl = Integer.parseInt(strToAL);
                int iToTakeLl = Integer.parseInt(strToLL);
                
                Vector rowx = new Vector(1,1);
                //DP
                rowx.add(""+iterateNo);
                rowx.add(""+dep.getDepartment());
                rowx.add(""+strEmpCount);
                rowx.add(strDPBlc);
                rowx.add(strDPEnt);
                rowx.add(""+(dpBlc+dpEnt));
                rowx.add("<b>"+(strToDP)+"</b>");
                rowx.add(strDPTaken);
                double perc = 0;
                if(dToTakeDp<=0){
                    if(dpTaken==0){
                        perc = 0;
                    }else{
                        perc = 100;
                    }
                }else{
                    perc = (dpTaken*100)/iToTakeDp;
                }
                rowx.add(dc2.format(perc)+" %");
                rowx.add(""+(dpBlc+dpEnt-dpTaken));
                
                //AL
                rowx.add(strALBlc);
                rowx.add(strALEnt);
                rowx.add(""+(alBlc+alEnt));
                rowx.add("<b>"+(strToAL)+"</b>");
                rowx.add(strALTaken);
                
                if(dToTakeAl<=0){
                    if(alTaken==0){
                        perc = 0;
                    }else{
                        perc = 100;
                    }
                }else{
                    perc = (alTaken*100)/iToTakeAl;
                }
                rowx.add(dc2.format(perc)+" %");
                rowx.add(""+(alBlc+alEnt-alTaken));
                
                //LL
                rowx.add(strLLBlc);
                rowx.add(strLLEnt);
                rowx.add(""+(llBlc+llEnt));
                rowx.add("<b>"+(strToLL)+"</b>");
                rowx.add(strLLTaken);
                
                if(dToTakeLl<=0){
                    if(llTaken==0){
                        perc = 0;
                    }else{
                        perc = 100;
                    }
                }else{
                    perc = (llTaken*100)/iToTakeLl;
                }
                rowx.add(dc2.format(perc)+" %");
                rowx.add(""+(llBlc+llEnt-llTaken));
                                
                lstData.add(rowx);
                lstLinkData.add("0");
                
                totalDPTOTake += Double.parseDouble(strToDP);
                totalALTOTake += Double.parseDouble(strToAL);
                totalLLTOTake += Double.parseDouble(strToLL);
            }	
            
            Vector rowx = new Vector(1,1);  
                
            rowx.add("");
            rowx.add("<b>TOTAL</b>");
            rowx.add("<b>"+totalEmp);
            //DP
            rowx.add("<b>"+totalDPBlc+"</b>");
            rowx.add("<b>"+totalDPent+"</b>");
            rowx.add("<b>"+(totalDPBlc+totalDPent)+"</b>");
            rowx.add("<b>"+dc2.format(totalDPTOTake)+"</b>");
            rowx.add("<b>"+totalDPTaken+"</b>");
            float perc = 0;
            if(totalDPTOTake<=0){
                perc = 100;
            }else{
                perc = (totalDPTaken*100)/totalDPTOTake;
            }
            rowx.add("<b>"+dc2.format(perc)+" %");
            rowx.add("<b>"+(totalDPBlc+totalDPent-totalDPTaken)+"</b>");
            //AL
            rowx.add("<b>"+totalALBlc+"</b>");
            rowx.add("<b>"+totalALent+"</b>");
            rowx.add("<b>"+(totalALBlc+totalALent)+"</b>");
            rowx.add("<b>"+dc2.format(totalALTOTake)+"</b>");
            rowx.add("<b>"+totalALTaken+"</b>");
            perc = 0;
            if(totalALTOTake<=0){
                perc = 100;
            }else{
                perc = (totalALTaken*100)/totalALTOTake;
            }
            rowx.add("<b>"+dc2.format(perc)+" %"+"</b>");
            rowx.add("<b>"+(totalALBlc+totalALent-totalALTaken));
            //LL
            rowx.add("<b>"+totalLLBlc+"</b>");
            rowx.add("<b>"+totalLLent+"</b>");
            rowx.add("<b>"+(totalLLBlc+totalLLent)+"</b>");
            rowx.add("<b>"+dc2.format(totalLLTOTake)+"</b>");
            rowx.add("<b>"+totalLLTaken+"</b>");
            perc = 0;
            if(totalLLTOTake<=0){
                perc = 100;
            }else{
                perc = (totalLLTaken*100)/totalLLTOTake;
            }
            rowx.add("<b>"+dc2.format(perc)+" %"+"</b>");
            rowx.add("<b>"+(totalLLBlc+totalLLent-totalLLTaken)+"</b>");

            lstData.add(rowx);
            lstLinkData.add("0");

            result = ctrlist.drawList();
            
            int totalDpAlLl = (totalDPBlc+totalDPent+totalALBlc+totalALent-totalALTaken+totalLLBlc+totalLLent);
            int totalDpAlLLTaken = (totalDPTaken+totalALTaken+totalLLTaken);
            float totalDpAlLlTargeted = (totalDPTOTake+totalALTOTake+totalLLTOTake);
            float percTaken = (totalDpAlLLTaken*100)/totalDpAlLl;
            float percTargeted = (totalDpAlLLTaken*100)/totalDpAlLlTargeted;
            String strReview = 
                    "<br><table width='100%'>"
                    +"  <tr>"
                    +"      <td width='20%'>"
                    +"          <b>Previous Total DP,AL & LL </b>"
                    +"      </td>"
                    +"      <td width='10%'>"
                    +"          <b>= "+totalDpAlLl+"</b>"
                    +"      </td>"
                    +"      <td width='10%'></td>"
                    +"      <td width='60%'></td>"
                    +"  </tr>"
                    +"  <tr>"
                    +"      <td width='20%'>"
                    +"          <b>Actual Target</b>"
                    +"      </td>"
                    +"      <td width='10%'>"
                    +"          <b> = "+totalDpAlLLTaken+"</b>"
                    +"      </td>"
                    +"      <td width='10%'>"
                    +"          <b>"+dc2.format(percTaken)+"%</b>"
                    +"      </td>"
                    +"      <td width='60%'></td>"
                    +"  </tr>"
                    +"  <tr>"
                    +"      <td width='20%'>"
                    +"          <b>Targeted</b>"
                    +"      </td>"
                    +"      <td width='10%'>"
                    +"          <b> = "+dc2.format(totalDpAlLlTargeted)+"</b>"
                    +"      </td>"
                    +"      <td width='10%'>"
                    +"          <b>"+dc2.format(percTargeted)+"%</b>"
                    +"      </td>"
                    +"      <td width='60%'></td>"
                    +"  </tr>"
                    +"</table>"; 
            
            result += strReview;
            
    }
    else
    {					
            result += "<div class=\"msginfo\">&nbsp;&nbsp;No Month End data found ...</div>";																
    }
    return result;	
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
Date currPeriod = FRMQueryString.requestDate(request,"currPeriod");

// for list
Vector listMonthEnd = new Vector(1,1);
LeaveTarget leaveTarget = new LeaveTarget();
if(Command.LIST==iCommand){
    leaveTarget = PstLeaveTarget.getLeaveTarget(currPeriod);
    if(leaveTarget.getOID()>0){
        listMonthEnd = SessLeaveApp.listEndMonthReport(currPeriod);
        session.removeValue("SESS_MONTH_END_REPORT");
        session.putValue("SESS_MONTH_END_REPORT", listMonthEnd);
    }
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
	document.frSpUnpaid.action="dailyon_montly.jsp";
	document.frSpUnpaid.submit();
}

function cmdPrint()
{
	var linkPage = "<%=printroot%>.report.leave.MonthEndReportXls?currPeriod=<%=String.valueOf(currPeriod.getTime())%>";
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
                  &gt; Attendance &gt; Month End Report<!-- #EndEditable --> 
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
                                              <%=ControlDate.drawDateMY("currPeriod", (currPeriod!=null?currPeriod:new Date()), "MMM yyyy", "formElemen", 0, installInterval)%> 
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
                                                  Report End Month</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>									  
									  
									  
                                      <% 
                                      if(iCommand == Command.LIST && leaveTarget.getOID()>0)
                                      {
                                      %>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr>
                                          <td><hr></td>
                                        </tr>
                                        <tr>
                                          <td><%=drawList(listMonthEnd,leaveTarget,currPeriod)%></td>
                                        </tr>
                                        <%
                                            if(listMonthEnd.size()>0 && privPrint)
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
                                          if(!(leaveTarget.getOID()>0)){
                                          %>
                                          <font color="red">Target at this period haven't been set!<font>
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
