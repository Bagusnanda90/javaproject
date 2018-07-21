

<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.attendance.PstAlStockManagement,
                  com.dimata.harisma.entity.attendance.AlStockManagement,
                  com.dimata.harisma.form.attendance.FrmAlStockManagement,
                  com.dimata.harisma.form.masterdata.CtrlPosition,
                  com.dimata.harisma.form.masterdata.FrmPosition,
                  com.dimata.harisma.form.attendance.CtrlAlStockManagement,
                  com.dimata.gui.jsp.ControlList,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.LeavePeriod,
                  com.dimata.util.Command,
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.util.Formater,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
		  com.dimata.harisma.entity.attendance.ALStockReporting,
                  com.dimata.harisma.entity.attendance.DpStockManagement,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.employee.PstEmployee,
                  com.dimata.harisma.entity.search.SrcLeaveManagement,
                  com.dimata.harisma.form.search.FrmSrcLeaveManagement,				  
                  com.dimata.harisma.session.attendance.AnnualLeaveMontly,
                  com.dimata.harisma.session.leave.*,
                  com.dimata.harisma.session.attendance.*,                  
                  com.dimata.harisma.session.leave.SessLeaveApp,com.dimata.harisma.session.leave.RepItemLeaveAndDp"%>
<!-- package qdep -->

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_DP_DETAIL); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * create list of report items
 */
public String drawList(JspWriter outObj, Vector listCurrStock) 
{ 
    String result = "";
    Date currDate = new Date();
    String formatFloat ="###.###";

    if(listCurrStock!=null && listCurrStock.size()>0)
    {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");

            ctrlist.addHeader("NO","2%", "2", "0");
            ctrlist.addHeader("PayRoll","5%", "2", "0");
            ctrlist.addHeader("Employee","20%", "2", "0");
            ctrlist.addHeader("DP","20%", "0", "5");	
            ctrlist.addHeader("AL","24%", "0", "6");
            ctrlist.addHeader("LL","28%", "0", "7");
            
            ctrlist.addHeader("QTY","4%", "0", "0");            
            ctrlist.addHeader("Taken","4%", "0", "0");
            ctrlist.addHeader("Will Be Taken","4%", "0", "0");
            ctrlist.addHeader("Expired","4%", "0", "0");
            ctrlist.addHeader("Balance","4%", "0", "0");
            
            ctrlist.addHeader("Balance Prev Period","4%", "0", "0");
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
                        
            //iterasi sebanyak data Special Leave yang ada
            int iterateNo = 0;
            //boolean resultAvailable = false;
            
            float totalDPent = 0;
            float totalDPTaken = 0;
            float totalDP2BTaken = 0;
            float totalExpDP = 0;
            float totalDPBlc = 0;
            
            float totalALPrev = 0;
            float totalALent = 0;
            float totalALTotal = 0;
            float totalALTaken = 0;
            float totalAL2BTaken = 0;
            float totalALBlc = 0;
            
            float totalLLPrev = 0;
            float totalLLent = 0;
            float totalLTotal = 0;
            float totalLLTaken = 0;
            float totalLL2BTaken = 0;
            float totalLLBlc = 0;
            float totalExpLL = 0;
            float LLBalanceWth2BTakenWthExpiredQty = 0;
            
            long depOID=0;
            for (int i=0; i<listCurrStock.size(); i++) 
            { 
              try{
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)listCurrStock.get(i);
                
                if(item==null) {
                    continue;
                }
                
                iterateNo++;

                if(depOID!=item.getDepartmentOID()){
                    
                    depOID=item.getDepartmentOID();
                    Department dep = new Department();
                    
                    try{
                        dep = PstDepartment.fetchExc(depOID);                                            
                    } catch(Exception exc){      
                        System.out.println("EXCEPTION ::::"+exc.toString());
                    }
                    
                Vector rowxDep = new Vector(1,1);
                
                //DP



                rowxDep.add("");
                rowxDep.add("<b>DEPT:</b>");
                rowxDep.add("<b>"+dep.getDepartment()+"</b>");
                
                for(int id=0;id<18;id++){                
                    rowxDep.add("");
                }                                
                lstData.add(rowxDep);
                lstLinkData.add("0");                    
                }
                
                Vector rowx = new Vector(1,1);
                
                //DP
                float residueDp = 0;

                //--------wick
                //Vector ls=(Vector)listCurrStock.get(0);
                //DpStockManagement dpStockManagement = (DpStockManagement)ls.get(0);
                float expiredQTY = SessLeaveManagement.getDpExpired(item.getEmployeeId(),null);
                residueDp = item.getDPQty()-(item.getDPTaken()+expiredQTY+item.getDP2BTaken());
                //--------

                //residueDp = item.getDPBalanceWth2BTaken() - SessLeaveManagement.totalExpiredDp(item.getEmployeeId());
             
                rowx.add(""+iterateNo);
                rowx.add(""+item.getPayrollNum());
                rowx.add(""+item.getEmployeeName()); 
                rowx.add(""+Formater.formatNumber(item.getDPQty(),formatFloat) );
                
                
                
                rowx.add(""+Formater.formatNumber(item.getDPTaken(),formatFloat));
                rowx.add(""+Formater.formatNumber(item.getDP2BTaken(),formatFloat));
                rowx.add(""+Formater.formatNumber(expiredQTY,formatFloat));
                
                //rowx.add(""+SessLeaveManagement.totalExpiredDp(item.getEmployeeId()));
                rowx.add(""+Formater.formatNumber(residueDp,formatFloat));
              
                
                totalDPent =totalDPent+ item.getDPQty();
                totalDPTaken =totalDPTaken+ item.getDPTaken();
                totalDP2BTaken =totalDP2BTaken+ item.getDP2BTaken();
                
                totalExpDP=totalExpDP+expiredQTY;
                //totalExpDP = totalExpDP + SessLeaveManagement.totalExpiredDp(item.getEmployeeId());
                totalDPBlc =totalDPBlc+ residueDp;
                
                //Mengecek status al stock aktif atau tidak
                boolean stsAlAktf = true;
                
                stsAlAktf = SessLeaveApplication.getLastDayAlPeriod(item.getALStockId(),new Date());
                boolean statusExpired = SessLeaveApplication.getStatusLeaveAlExpired(item.getALStockId());                  
                 //update by satrya 2013-10-10
                 float qtyAL = item.getALEntitle() + item.getALPrev();
                    
                if(stsAlAktf == true){
                    
                    float exp = 0;
                    
                    if(statusExpired == true){
                        
                        rowx.add("<font color=00FF00>"+Formater.formatNumber(item.getALPrev(), formatFloat)+"</font>");
                        exp = item.getALPrev();
                        
                    }else{                        
                        rowx.add(""+Formater.formatNumber(item.getALPrev(),formatFloat));
                        totalALPrev =totalALPrev + item.getALPrev();
                    }


                    String EmpName = item.getEmployeeName();
                    //float getALTaken= SessLeaveApplication.getTotalTakenAL(item.getEmployeeId());  // 7 Jan 2012 comment out by kartika
                    //float getAL2BTaken=SessLeaveApplication.get2BeTaknAl(item.getEmployeeId());
                    
                    float Total = qtyAL /*update by 2013-10-10 item.getALTotal()*/ - exp;
                    //float balance = Total - getALTaken;
                    float balance = Total - item.getALTaken() - item.getAL2BTaken();
                    //float balance = Total - item.getALTaken();
                    //float balance = Total - item.getALTaken() - item.getAL2BTaken();                   
                    
                   
                    //rowx.add(""+Formater.formatNumber(item.getALQty(), formatFloat));
                    //update by satrya 2013-10-10
                    rowx.add(""+Formater.formatNumber(item.getALEntitle(), formatFloat));
                    
                    rowx.add(""+Formater.formatNumber(Total, formatFloat));                    
                    rowx.add(""+Formater.formatNumber(item.getALTaken(), formatFloat));
                    //rowx.add(""+item.getALTaken());
                    rowx.add(""+Formater.formatNumber(item.getAL2BTaken(),formatFloat));
                    //rowx.add(""+item.getAL2BTaken());
                    rowx.add(""+Formater.formatNumber(balance,formatFloat));
                    
                    totalALent =totalALent+ item.getALEntitle()/* update by satrya 2013-10-10 item.getALQty()*/;                    
                    totalALTotal =totalALTotal + Total;
                    totalALTaken =totalALTaken + item.getALTaken();
                    totalAL2BTaken =totalAL2BTaken + item.getAL2BTaken();
                    totalALBlc = totalALBlc + balance;                   
                
                }else{
                    
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getALPrev(),formatFloat)+"</font>");
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber( item.getALEntitle() /* update by satrya 2013-10-10 item.getALQty()*/,formatFloat)+"</font>");
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getALEntitle() + item.getALPrev()/* update by satrya 2013-10-10 item.getALTotal()*/,formatFloat)+"</font>");
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getALTaken(),formatFloat)+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getAL2BTaken(),formatFloat)+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getALBalanceWth2BTaken(),formatFloat)+"</font>");   
                }
                boolean stsLLExp = false;
                stsLLExp = SessLeaveApplication.getStatusLLExpired(item.getEmployeeId()); 
                float LLQty = item.getLLEntitle() + item.getLLEntitle2() + item.getLLPrev(); 
                if(stsLLExp == false){
                    LLBalanceWth2BTakenWthExpiredQty = item.getLLBalanceWth2BTaken() - item.getLLExpdQty();                    
                    
                    rowx.add(""+Formater.formatNumber(item.getLLPrev(),formatFloat));
                    rowx.add(""+Formater.formatNumber(item.getLLEntitle()+item.getLLEntitle2()/* update by satrya 2013-10-10 item.getLLQty()*/,formatFloat));
                    rowx.add(""+Formater.formatNumber(LLQty/*item.getLLTotal()*/,formatFloat));
                    rowx.add(""+Formater.formatNumber(item.getLLTaken(),formatFloat));                                                                      
                    rowx.add(""+Formater.formatNumber(item.getLL2BTaken(),formatFloat));     
                    rowx.add(""+Formater.formatNumber(item.getLLExpdQty(),formatFloat));        
                    rowx.add(""+Formater.formatNumber(LLBalanceWth2BTakenWthExpiredQty,formatFloat)); 
                    
                    totalLLPrev = totalLLPrev + item.getLLPrev();
                    totalLLent = totalLLent + item.getLLEntitle()+item.getLLEntitle2()/*update by satrya 2013-10-10 item.getLLQty()*/;
                    totalLTotal = totalLTotal + LLQty/* update by satrya 2013-10-10 item.getLLTotal()*/;
                    totalLLTaken = totalLLTaken + item.getLLTaken(); 
                    totalLL2BTaken = totalLL2BTaken + item.getLL2BTaken();
                    totalExpLL = totalExpLL + item.getLLExpdQty();                    
                    totalLLBlc =totalLLBlc + LLBalanceWth2BTakenWthExpiredQty;                          
                    
                }else{
                    
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getLLPrev(),formatFloat)+"</font>");
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getLLEntitle()+item.getLLEntitle2()/*item.getLLQty()*/,formatFloat)+"</font>");
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(LLQty /* update by satrya 2013-10-10 item.getLLTotal()*/,formatFloat)+"</font>");
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getLLTaken(),formatFloat)+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getLL2BTaken(),formatFloat)+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(item.getLLExpdQty(),formatFloat)+"</font>");     
                    float totalEx = LLQty/*update by satrya 2013-10-10 item.getLLPrev() + item.getLLQty()*/ - item.getLLTaken() - item.getLL2BTaken() - item.getLLExpdQty();
                    rowx.add("<font color='FF0000'>"+Formater.formatNumber(totalEx,formatFloat)+"</font>");                                                                                             
                    
                }                
                lstData.add(rowx);
                lstLinkData.add("0");
            	
                } catch(Exception exc){
                System.out.println(exc);
                }
            }
            
            Vector rowx = new Vector(1,1);  
                
            rowx.add("");
            rowx.add("<b>TOTAL</b>");
            rowx.add("<b>"+""+"</b>");
            
            rowx.add("<b>"+Formater.formatNumber(totalDPent,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalDPTaken,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalDP2BTaken,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalExpDP,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalDPBlc,formatFloat)+"</b>");
            
            rowx.add("<b>"+Formater.formatNumber(totalALPrev,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalALent,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalALTotal,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalALTaken,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalAL2BTaken,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalALBlc,formatFloat)+"</b>");
            
            rowx.add("<b>"+Formater.formatNumber(totalLLPrev,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalLLent,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalLTotal,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalLLTaken,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalLL2BTaken,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalExpLL,formatFloat)+"</b>");
            rowx.add("<b>"+Formater.formatNumber(totalLLBlc,formatFloat)+"</b>");

            lstData.add(rowx);
            lstLinkData.add("0");

            result = ctrlist.drawList();                    
            /*try{
                       ctrlist.drawMe(outObj,0);
                   }catch(Exception e){
                       System.out.println("Exception "+e.toString());
    }
          return "";            */
     }
    else
    {					
            result += "<div class=\"msginfo\">&nbsp;&nbsp;Leave and Dp Stock Data Found found ...</div>";																
    }
    return result;	
}

%>

<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");

Vector listal = new Vector(1,1);
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();   
FrmSrcLeaveManagement frmSrcLeaveManagement = new FrmSrcLeaveManagement(request, srcLeaveManagement);

AnnualLeaveMontly alLeaveMon  = new AnnualLeaveMontly();
int dataStatus = DATA_NULL;
String strListInJsp = "&nbsp";
if(iCommand != Command.NONE)
{

        frmSrcLeaveManagement.requestEntityObject(srcLeaveManagement);
	listal = SessLeaveApp.detailLeaveDPStock(srcLeaveManagement);		

	try
	{
		session.removeValue("DETAIL_LEAVE_DP_REPORT");
	}
	catch(Exception e)
	{
		System.out.println("Exc when remove from session(\"DETAIL_LEAVE_DP_REPORT\") : " + e.toString());	
	}
	

	Vector listToSession = new Vector(1,1);
	listToSession.add(srcLeaveManagement);
	listToSession.add(""+srcLeaveManagement.getEmpDeptId());
	listToSession.add(listal);
	
	try
	{
		session.putValue("DETAIL_LEAVE_DP_REPORT",listToSession);
	}
	catch(Exception e)
	{
		System.out.println("Exc when put to session(\"DETAIL_LEAVE_DP_REPORT\") : " + e.toString());		
	}
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Leave & DP Report</title>
<script language="JavaScript">

function cmdPrintXls()
{ 
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveDpDetailXls";
    window.open(pathUrl);
}

function cmdView()
{
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="leave_dp_detail.jsp";
	document.frpresence.submit();
}

function cmdPrint()
{
	var linkPage = "<%=printroot%>.report.attendance.AnnualLeaveMonthlyPdf";
	window.open(linkPage);
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
                  &gt; Leave &gt; Detail Leave Report<!-- #EndEditable --> </strong></font>
                </td>
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
                                    <form name="frpresence" method="post" action="">
				      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Name</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="text" name="<%=FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_FULL_NAME]%>"  value="<%=srcLeaveManagement.getEmpName()%>" class="elemenForm" size="40">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Payroll Number</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="text" name="<%=FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_EMP_NUMBER]%>"  value="<%=srcLeaveManagement.getEmpNum()%>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <!--
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Category</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <% 
											/*
												Vector cat_value = new Vector(1,1);
												Vector cat_key = new Vector(1,1);        
												cat_value.add("0");
												cat_key.add("select ...");                                                          
												Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
												for (int i = 0; i < listCat.size(); i++) 
												{
													EmpCategory cat = (EmpCategory) listCat.get(i);
													cat_key.add(cat.getEmpCategory());
													cat_value.add(String.valueOf(cat.getOID()));
												}
												out.println(ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_CATEGORY],"formElemen",null, ""+srcLeaveManagement.getEmpCatId(), cat_value, cat_key, ""));
											*/	
											%>
                                          </td>
                                        </tr>
										-->
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <% 
            Vector dept_value = new Vector(1, 1);
            Vector dept_key = new Vector(1, 1);
            //Vector listDept = new Vector(1, 1);
            DepartmentIDnNameList keyList= new DepartmentIDnNameList ();            
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                        //dept_value.add("0");
                        //dept_key.add("select ...");
                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                        keyList= PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                    } else {
                        String whereClsDep="(DEPARTMENT_ID = " + departmentOid+" OR JOIN_TO_DEPARTMENT_ID='"+departmentOid+"')";
                        try {
                            String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                            Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                            int grpIdx = -1;
                            int maxGrp = depGroup == null ? 0 : depGroup.size();
                            int countIdx = 0;
                            int MAX_LOOP = 10;
                            int curr_loop = 0;
                            do { // find group department belonging to curretn user base in departmentOid
                                curr_loop++;
                                String[] grp = (String[]) depGroup.get(countIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                      grpIdx = countIdx;   // A ha .. found here 
                                    }
                                }
                                countIdx++;
                            } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop<MAX_LOOP)); // if found then exit
                            
                            // compose where clause
                            if(grpIdx>=0){
                                String[] grp = (String[]) depGroup.get(grpIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    whereClsDep=whereClsDep+ " OR (DEPARTMENT_ID = " + comp+")"; 
                                }         
                               }                                                  
                        } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);
                        }

                        //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                        keyList= PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, true);
                    }
                } else {
                    //dept_value.add("0");
                    //dept_key.add("select ...");
                    //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                    keyList= PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                }
            } else {
                //dept_value.add("0");
                //dept_key.add("select ...");
                //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                keyList= PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
            }
                                            
                                            

            //Vector dept_value = new Vector(1,1);
            //Vector dept_key = new Vector(1,1);

            //dept_key.add("ALL DEPARTMENT");
            //dept_value.add("0");

            //Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
            String selectDept = String.valueOf(srcLeaveManagement.getEmpDeptId());
            /*for (int i = 0; i < listDept.size(); i++) 
            {
                    Department dept = (Department) listDept.get(i);
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
            }*/
            dept_value = keyList.getDepIDs();
            dept_key = keyList.getDepNames();                                      
            out.println(ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_DEPARTMENT],"formElemen",null, selectDept, dept_value, dept_key, ""));
											%>
                                          </td>
                                        </tr>
                                        <!--
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <% 
											/*
												Vector sec_value = new Vector(1,1);
												Vector sec_key = new Vector(1,1); 
												sec_value.add("0");
												sec_key.add("select ...");
												Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
												for (int i = 0; i < listSec.size(); i++) 
												{
													Section sec = (Section) listSec.get(i);
													sec_key.add(sec.getSection());
													sec_value.add(String.valueOf(sec.getOID()));
												}
												out.println(ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_SECTION],"formElemen",null, ""+srcLeaveManagement.getEmpSectionId(), sec_value, sec_key, ""));
											*/	
											%>
                                          </td>
                                        </tr>
										-->
                                        <!--
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Position</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <% 
											/*
												Vector pos_value = new Vector(1,1);
												Vector pos_key = new Vector(1,1); 
												pos_value.add("0");
												pos_key.add("select ...");                                                       
												Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
												for (int i = 0; i < listPos.size(); i++) 
												{
													Position pos = (Position) listPos.get(i);
													pos_key.add(pos.getPosition());
													pos_value.add(String.valueOf(pos.getOID()));
												}
												out.println(ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_POSITION],"formElemen",null, ""+srcLeaveManagement.getEmpPosId(), pos_value, pos_key, ""));
											*/	
											%>
                                          </td>
                                        </tr>
										-->
										<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Level</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <% 
											
												Vector lev_value = new Vector(1,1);
												Vector lev_key = new Vector(1,1); 
												lev_value.add("0");
												lev_key.add("select ...");
												Vector listLev = PstLevel.list(0, 0, "", " LEVEL_ID, LEVEL ");
												for (int i = 0; i < listLev.size(); i++) 
												{
													Level lev = (Level) listLev.get(i);
													lev_key.add(lev.getLevel());
													lev_value.add(String.valueOf(lev.getOID()));
												}
												out.println(ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_LEVEL],"formElemen",null, ""+srcLeaveManagement.getEmpLevelId(), lev_value, lev_key, ""));
												
											%>
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
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View Report</a></td>
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
                                          <td><%=drawList(out, listal)%></td>
                                        </tr>
                                        <tr>
                                          <td>
                                              Notes :<BR>
                                              1. Red Color      : No stock Aktif ( Leave will not add to grand total )<BR>
                                              2. Green Color    : Stock Expired  ( Leave will not add to grand total )
                                          </td>
                                        </tr>
                                        <%
										if(listal != null && listal.size() > 0 && privPrint)
										{
										%>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr>
                                                <td width="24"><a href="javascript:cmdPrintXls()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdPrintXls()">Print Report Xls</a></td>
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
