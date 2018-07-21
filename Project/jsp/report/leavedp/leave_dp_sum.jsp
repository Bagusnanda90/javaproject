
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
int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * create list of report items
 */
public String drawList(Vector listCurrStock) 
{ 
    String result = "";
    Date currDate = new Date();

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
            ctrlist.addHeader("#Employee","4%", "2", "0");
            ctrlist.addHeader("DP","28%", "0", "5");	
            ctrlist.addHeader("AL","28%", "0", "6");
            ctrlist.addHeader("LL","28%", "0", "7");
            
            ctrlist.addHeader("QTY","4%", "0", "0");
            ctrlist.addHeader("Taken","4%", "0", "0");
            ctrlist.addHeader("Will Be Taken","4%", "0", "0");
            ctrlist.addHeader("Expired","4%", "0", "0");
            ctrlist.addHeader("Balance","4%", "0", "0");            
            
            ctrlist.addHeader("Prev.Prd","4%", "0", "0");
            ctrlist.addHeader("Entitle ","4%", "0", "0");
            ctrlist.addHeader("Sub Total","4%", "0", "0");
            ctrlist.addHeader("Taken","4%", "0", "0");
            ctrlist.addHeader("Will Be Taken","4%", "0", "0");
            ctrlist.addHeader("Balance ","4%", "0", "0");
            
            ctrlist.addHeader("Prev.Prd","4%", "0", "0");
            ctrlist.addHeader("Entitle ","4%", "0", "0");
            ctrlist.addHeader("Sub Total","4%", "0", "0");
            ctrlist.addHeader("Taken","4%", "0", "0");
            ctrlist.addHeader("Will Be Taken","4%", "0", "0");
            ctrlist.addHeader("Expired","4%", "0", "0");
            ctrlist.addHeader("Balance ","4%", "0", "0");

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
            float totalEmp = 0;            
            float totalDPent = 0;
            float totalDPTaken = 0;
            float totalDPBlc = 0;
            
            float totalDp2BeTaken = 0;
            float totalDpExpired = 0;
            
            float totalALPrev = 0;
            float totalALent = 0;
            float totalALTotal = 0;
            float totalALTaken = 0;
            float totalAL2BeTaken = 0;
            float totalALBlc = 0;
            
            float totalLLPrev = 0;
            float totalLLent = 0;
            float totalLTotal = 0;
            float totalLLTaken = 0;
            float totalLL2BeTaken = 0;
            float totalLLExpired = 0;
            float totalLLBlc = 0;
            
            //int total = 0;
             long CompanyOID=0;
               long DivOID=0;
               long DeptOID=0;
               long secOID=0; 
            for (int i=0; i<listCurrStock.size(); i++) 
            { try{
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)listCurrStock.get(i);
                LeaveTargetDetail objLeaveTargetDetail = new LeaveTargetDetail();
                
                float dpBalance = 0;

                if(item==null) {
                    continue;
                }
                
                iterateNo++;
                //update by satrya 2013-10-25
                if(CompanyOID!=item.getCompanyOID()){
                    
                    CompanyOID=item.getCompanyOID();
                    
                    
                        //Vector rowxDep = new Vector(1,1);
                        ControlList ctrlistCompany = new ControlList();
                        ctrlistCompany.addColoms("<b>COMPANY:</b>"+"<b>"+item.getCompanyName()+"</b>", "0", "4");
                        ctrlistCompany.addColoms("","0","0");
                        ctrlistCompany.addColoms("","0","0");
                        

                        for(int id=0;id<19;id++){                
                            ctrlistCompany.addColoms("","0","0");
                        }                                
                        lstData.add(ctrlistCompany);
                        lstLinkData.add("0");                    
                }
                
                 if(DivOID!=item.getDivisionId()){
                    
                    DivOID=item.getDivisionId();
                    
                    
                        //Vector rowxDiv = new Vector(1,1);

                        /*rowxDiv.add("");
                        rowxDiv.add("<b> - DIVISION:</b>"+"<b>"+item.getDivisionName()+"</b>");
                        

                        for(int id=0;id<19;id++){                
                            rowxDiv.add("");
                        }                                
                        lstData.add(rowxDiv); 
                        lstLinkData.add("0");*/
                        ControlList ctrlistCompany = new ControlList();
                        ctrlistCompany.addColoms("","0","0");
                        ctrlistCompany.addColoms("<b> - DIVISION:</b>"+"<b>"+item.getDivisionName()+"</b>", "0", "4");
                        ctrlistCompany.addColoms("","0","0");
                        

                        for(int id=0;id<19;id++){                
                            ctrlistCompany.addColoms("","0","0");
                        }                                
                        lstData.add(ctrlistCompany);
                        lstLinkData.add("0");                    
                }
                
                //update by satrya 2013-10-25
                
                totalEmp =totalEmp + item.getEmpQty();   
                
                Vector rowx = new Vector(1,1);
                
                //Employee
                ControlList ctrlistData = new ControlList();
                ctrlistData.addColoms(""+iterateNo,"0","0");
                ctrlistData.addColoms(""+item.getDepName(),"0","0");
                ctrlistData.addColoms(""+item.getEmpQty(),"0","0");
                /*rowx.add(""+iterateNo);
                rowx.add(""+item.getDepName());
                rowx.add(""+item.getEmpQty());*/
                                
                //DP
                float totDpExpired = SessLeaveManagement.getTotalDPExpiredByDepartment(item.getDepartmentOID());
                float totalDp2BeTake = SessLeaveApplication.getTotal2BeTakenDP(item.getDepartmentOID());
                dpBalance = item.getDPQty() - (item.getDPTaken() + totDpExpired);
                
                /*rowx.add(""+item.getDPQty());
                rowx.add(""+item.getDPTaken());
                rowx.add(""+totalDp2BeTake);
                rowx.add(""+totDpExpired);
                rowx.add(""+dpBalance);*/
                ctrlistData.addColoms(""+item.getDPQty(),"0","0");
                ctrlistData.addColoms(""+item.getDPTaken(),"0","0");
                ctrlistData.addColoms(""+totalDp2BeTake,"0","0");
                ctrlistData.addColoms(""+totDpExpired,"0","0");
                ctrlistData.addColoms(""+dpBalance,"0","0");
                
                totalDPent =totalDPent+ item.getDPQty();
                totalDPTaken =totalDPTaken+ item.getDPTaken();                
                totalDp2BeTaken = totalDp2BeTaken + totalDp2BeTake;
                totalDpExpired = totalDpExpired + totDpExpired;
                totalDPBlc =totalDPBlc+ dpBalance;                
                
                //Annual Leave    
                AlStockManagement alStockManagement = new AlStockManagement();
                alStockManagement = SessLeaveApplication.getTotalPeriodAlAktif(item.getDepartmentOID());

                
                long jumlah =item.getALStockId();

                float subTotal = alStockManagement.getPrevBalance() + alStockManagement.getAlQty();
                
                //float AlTknQty = SessLeaveApplication.getTotalTakenALByDept(item.getDepartmentOID());
                float balance = subTotal - alStockManagement.getQtyUsed() ;
                
                
                       
                /*rowx.add(""+alStockManagement.getPrevBalance());
                rowx.add(""+alStockManagement.getAlQty());
                rowx.add(""+subTotal);
                rowx.add(""+alStockManagement.getQtyUsed());
                rowx.add(""+alStockManagement.getALtoBeTaken());     
                rowx.add(""+balance);*/      
                ctrlistData.addColoms(""+alStockManagement.getPrevBalance(),"0","0");
                ctrlistData.addColoms(""+alStockManagement.getAlQty(),"0","0");
                ctrlistData.addColoms(""+subTotal,"0","0");
                ctrlistData.addColoms(""+alStockManagement.getQtyUsed(),"0","0");
                ctrlistData.addColoms(""+alStockManagement.getALtoBeTaken(),"0","0");
                ctrlistData.addColoms(""+balance,"0","0");

                totalALPrev =totalALPrev + alStockManagement.getPrevBalance();
                totalALent =totalALent+ alStockManagement.getAlQty();
                totalALTotal =totalALTotal + subTotal;
                totalALTaken =totalALTaken + alStockManagement.getQtyUsed();
                totalAL2BeTaken = totalAL2BeTaken + alStockManagement.getALtoBeTaken();
                totalALBlc =totalALBlc + balance;
                
                //Long Leave
                LLStockManagement llStockManagement = new LLStockManagement();
                llStockManagement = SessLeaveApplication.getTotalPeriodLlAktif(item.getDepartmentOID());                
                float totalLL = llStockManagement.getPrevBalance() + llStockManagement.getLLQty();
                float llExp = SessLeaveApplication.totExpired(item.getDepartmentOID());
                float totalBalance = totalLL - llStockManagement.getQtyUsed() - llStockManagement.getToBeTaken() - llExp;
                
                
               /* rowx.add(""+llStockManagement.getPrevBalance());
                rowx.add(""+llStockManagement.getLLQty());
                rowx.add(""+totalLL);
                rowx.add(""+llStockManagement.getQtyUsed());                                                                      
                rowx.add(""+llStockManagement.getToBeTaken());            
                rowx.add(""+llExp);
                rowx.add(""+totalBalance);   */         

                ctrlistData.addColoms(""+llStockManagement.getPrevBalance(),"0","0");
                ctrlistData.addColoms(""+llStockManagement.getLLQty(),"0","0");
                ctrlistData.addColoms(""+totalLL,"0","0");
                ctrlistData.addColoms(""+llStockManagement.getQtyUsed(),"0","0");
                ctrlistData.addColoms(""+llStockManagement.getToBeTaken(),"0","0");
                ctrlistData.addColoms(""+llExp,"0","0");
                ctrlistData.addColoms(""+totalBalance,"0","0");
                
                totalLLPrev =totalLLPrev + llStockManagement.getPrevBalance();
                totalLLent =totalLLent + llStockManagement.getLLQty();
                totalLTotal =totalLTotal + totalLL;
                totalLLTaken =totalLLTaken + llStockManagement.getQtyUsed();
                totalLL2BeTaken = totalLL2BeTaken + llStockManagement.getToBeTaken();
                totalLLExpired = totalLLExpired + llExp;
                totalLLBlc =totalLLBlc + totalBalance;
                                
                lstData.add(ctrlistData);
                lstLinkData.add("0");
                
                //update by satrya 2013-10-27
                /* sementara di hidden by satrya  if(secOID!=item.getSectionOID() && item.getSectionName().length()>0){

                    secOID=item.getSectionOID();
                    ControlList ctrlistSec = new ControlList();
                    ctrlistSec.addColoms("","0","0");
                    ctrlistSec.addColoms("","0","0");
                    ctrlistSec.addColoms("<b> - SECTION:</b>"+"<b>"+item.getSectionName()+"</b>", "0", "4");


                    for(int id=0;id<19;id++){                
                     ctrlistSec.addColoms("","0","0");
                    }                                
                    lstData.add(ctrlistSec);
                    lstLinkData.add("0");                    
                }*/
                               } catch(Exception exc){
                                   System.out.println(" i= "+i+" /n "+exc);
                               }
            }
            
            Vector rowx = new Vector(1,1);  
                
            /*rowx.add("");
            rowx.add("<b>TOTAL</b>");
            rowx.add("<b>"+totalEmp+"</b>");
            
            rowx.add("<b>"+totalDPent+"</b>");
            rowx.add("<b>"+totalDPTaken+"</b>");
            rowx.add("<b>"+totalDp2BeTaken+"</b>");
            rowx.add("<b>"+totalDpExpired+"</b>");
            rowx.add("<b>"+totalDPBlc+"</b>");
            
            rowx.add("<b>"+totalALPrev+"</b>");
            rowx.add("<b>"+totalALent+"</b>");
            rowx.add("<b>"+totalALTotal+"</b>");
            rowx.add("<b>"+totalALTaken+"</b>");
            rowx.add("<b>"+totalAL2BeTaken+"</b>");
            rowx.add("<b>"+totalALBlc+"</b>");
            
            rowx.add("<b>"+totalLLPrev+"</b>");
            rowx.add("<b>"+totalLLent+"</b>");
            rowx.add("<b>"+totalLTotal+"</b>");
            rowx.add("<b>"+totalLLTaken+"</b>");
            rowx.add("<b>"+totalLL2BeTaken+"</b>");            
            rowx.add("<b>"+totalLLExpired+"</b>");
            rowx.add("<b>"+totalLLBlc+"</b>");*/
            ControlList ctrlistDataTot = new ControlList();
            ctrlistDataTot.addColoms("","0","0");
            ctrlistDataTot.addColoms("<b>TOTAL</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalEmp+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalDPent+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalDPTaken+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalDp2BeTaken+"</b>","0","0");
            
            ctrlistDataTot.addColoms("<b>"+totalDpExpired+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalDPBlc+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalALPrev+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalALent+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalALTotal+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalALTaken+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalAL2BeTaken+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalALBlc+"</b>","0","0");
            
            ctrlistDataTot.addColoms("<b>"+totalLLPrev+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalLLent+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalLTotal+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalLLTaken+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalLL2BeTaken+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalLLExpired+"</b>","0","0");
            ctrlistDataTot.addColoms("<b>"+totalLLBlc+"</b>","0","0");
            
            lstData.add(ctrlistDataTot);
            lstLinkData.add("0");

            result = ctrlist.drawListCoolPansData();
            
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

// for list
Vector listCurrStock = new Vector(1,1);
LeaveTarget leaveTarget = new LeaveTarget();
if(Command.LIST==iCommand){
        listCurrStock = SessLeaveApp.listSumLeaveAndDPStock();
        session.removeValue("SESS_LEAVE_DP_SUM_REPORT");
        session.putValue("SESS_LEAVE_DP_SUM_REPORT", listCurrStock);
    }

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Leave adn DP Stock Report</title>
<script language="JavaScript">
<!--
function cmdView()
{
	document.frSpUnpaid.command.value="<%=String.valueOf(Command.LIST)%>";
	document.frSpUnpaid.action="leave_dp_sum.jsp";
	document.frSpUnpaid.submit();
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
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
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
