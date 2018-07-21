
<%-- 
    Document   : leave_dp_detail_by_period
    Created on : Aug 18, 2010, 9:07:44 AM
    Author     : Roy Andika
--%>


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
                        
            //iterasi sebanyak data Special Leave yang ada
            int iterateNo = 0;
            
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
                residueDp = item.getDPBalanceWth2BTaken() - SessLeaveManagement.totalExpiredDp(item.getEmployeeId());
                rowx.add(""+iterateNo);
                rowx.add(""+item.getPayrollNum());
                rowx.add(""+item.getEmployeeName());    
                           
                rowx.add(""+item.getDPQty());
                rowx.add(""+item.getDPTaken());
                rowx.add(""+item.getDP2BTaken());
                rowx.add(""+SessLeaveManagement.totalExpiredDp(item.getEmployeeId()));
                rowx.add(""+residueDp);
                
                totalDPent =totalDPent+ item.getDPQty();
                totalDPTaken =totalDPTaken+ item.getDPTaken();
                totalDP2BTaken =totalDP2BTaken+ item.getDP2BTaken();
                totalExpDP = totalExpDP + SessLeaveManagement.totalExpiredDp(item.getEmployeeId());
                totalDPBlc =totalDPBlc+ residueDp;
                
                //Mengecek status al stock aktif atau tidak
                boolean stsAlAktf = true;
                
                stsAlAktf = SessLeaveApplication.getLastDayAlPeriod(item.getALStockId(),new Date());
                boolean statusExpired = SessLeaveApplication.getStatusLeaveAlExpired(item.getALStockId());                  
                
                if(stsAlAktf == true){
                    
                    float exp = 0;
                    
                    if(statusExpired == true){
                        
                        rowx.add("<font color=00FF00>"+item.getALPrev()+"</font>");
                        exp = item.getALPrev();
                        
                    }else{                        
                        rowx.add(""+item.getALPrev());
                        totalALPrev =totalALPrev + item.getALPrev();
                    }
                    
                    float Total = item.getALTotal() - exp;
                    float balance = Total - item.getALTaken() - item.getAL2BTaken();
                    
                    rowx.add(""+item.getALQty());
                    rowx.add(""+Total);
                    rowx.add(""+item.getALTaken());                                                                      
                    rowx.add(""+item.getAL2BTaken());                       
                    rowx.add(""+balance);
                    
                    totalALent =totalALent+ item.getALQty();                    
                    totalALTotal =totalALTotal + Total;
                    totalALTaken =totalALTaken + item.getALTaken();
                    totalAL2BTaken =totalAL2BTaken + item.getAL2BTaken();
                    totalALBlc = totalALBlc + balance;                   
                
                }else{
                    
                    rowx.add("<font color='FF0000'>"+item.getALPrev()+"</font>");
                    rowx.add("<font color='FF0000'>"+item.getALQty()+"</font>");
                    rowx.add("<font color='FF0000'>"+item.getALTotal()+"</font>");
                    rowx.add("<font color='FF0000'>"+item.getALTaken()+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+item.getAL2BTaken()+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+item.getALBalanceWth2BTaken()+"</font>");                                                                                             
                    
                }
                
                boolean stsLLExp = false;
                stsLLExp = SessLeaveApplication.getStatusLLExpired(item.getEmployeeId()); 
                
                if(stsLLExp == false){
                    
                    LLBalanceWth2BTakenWthExpiredQty = item.getLLBalanceWth2BTaken() - item.getLLExpdQty();
                    
                    rowx.add(""+item.getLLPrev());
                    rowx.add(""+item.getLLQty());
                    rowx.add(""+item.getLLTotal());
                    rowx.add(""+item.getLLTaken());                                                                      
                    rowx.add(""+item.getLL2BTaken());     
                    rowx.add(""+item.getLLExpdQty());        
                    rowx.add(""+LLBalanceWth2BTakenWthExpiredQty); 
                    
                    totalLLPrev = totalLLPrev + item.getLLPrev();
                    totalLLent = totalLLent + item.getLLQty();
                    totalLTotal = totalLTotal + item.getLLTotal();
                    totalLLTaken = totalLLTaken + item.getLLTaken(); 
                    totalLL2BTaken = totalLL2BTaken + item.getLL2BTaken();
                    totalExpLL = totalExpLL + item.getLLExpdQty();                    
                    totalLLBlc =totalLLBlc + LLBalanceWth2BTakenWthExpiredQty;                          
                    
                }else{
                    
                    rowx.add("<font color='FF0000'>"+item.getLLPrev()+"</font>");
                    rowx.add("<font color='FF0000'>"+item.getLLQty()+"</font>");
                    rowx.add("<font color='FF0000'>"+item.getLLTotal()+"</font>");
                    rowx.add("<font color='FF0000'>"+item.getLLTaken()+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+item.getLL2BTaken()+"</font>");                                                                      
                    rowx.add("<font color='FF0000'>"+item.getLLExpdQty()+"</font>");     
                    float totalEx = item.getLLPrev() + item.getLLQty() - item.getLLTaken() - item.getLL2BTaken() - item.getLLExpdQty();
                    rowx.add("<font color='FF0000'>"+totalEx+"</font>");                                                                                             
                    
                }                
                lstData.add(rowx);
                lstLinkData.add("0");
            }	
            
            Vector rowx = new Vector(1,1);  
                
            rowx.add("");
            rowx.add("<b>TOTAL</b>");
            rowx.add("<b>"+""+"</b>");
            
            rowx.add("<b>"+totalDPent+"</b>");
            rowx.add("<b>"+totalDPTaken+"</b>");
            rowx.add("<b>"+totalDP2BTaken+"</b>");
            rowx.add("<b>"+totalExpDP+"</b>");
            rowx.add("<b>"+totalDPBlc+"</b>");
            
            rowx.add("<b>"+totalALPrev+"</b>");
            rowx.add("<b>"+totalALent+"</b>");
            rowx.add("<b>"+totalALTotal+"</b>");
            rowx.add("<b>"+totalALTaken+"</b>");
            rowx.add("<b>"+totalAL2BTaken+"</b>");
            rowx.add("<b>"+totalALBlc+"</b>");
            
            rowx.add("<b>"+totalLLPrev+"</b>");
            rowx.add("<b>"+totalLLent+"</b>");
            rowx.add("<b>"+totalLTotal+"</b>");
            rowx.add("<b>"+totalLLTaken+"</b>");
            rowx.add("<b>"+totalLL2BTaken+"</b>");
            rowx.add("<b>"+totalExpLL+"</b>");
            rowx.add("<b>"+totalLLBlc+"</b>");

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
<title>HARISMA - Leave & DP Report By Period</title>
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
                  &gt; Leave &gt; Detail Leave Report By Period <!-- #EndEditable --> </strong></font>
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
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <% 
            Vector dept_value = new Vector(1, 1);
            Vector dept_key = new Vector(1, 1);
            Vector listDept = new Vector(1, 1);
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                        dept_value.add("0");
                        dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                    } else {
                        String whereClsDep="(DEPARTMENT_ID = " + departmentOid+")";
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

                        listDept = PstDepartment.list(0, 0,whereClsDep, "");
                    }
                } else {
                    dept_value.add("0");
                    dept_key.add("select ...");
                    listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                }
            } else {
                dept_value.add("0");
                dept_key.add("select ...");
                listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
            }
                                            
												
												String selectDept = String.valueOf(srcLeaveManagement.getEmpDeptId());
												for (int i = 0; i < listDept.size(); i++) 
												{
													Department dept = (Department) listDept.get(i);
													dept_key.add(dept.getDepartment());
													dept_value.add(String.valueOf(dept.getOID()));
												}
												out.println(ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_DEPARTMENT],"formElemen",null, selectDept, dept_value, dept_key, ""));
											%>
                                          </td>
                                        </tr>
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
                                          <td><%=drawList(listal)%></td>
                                        </tr>
                                        <tr>
                                          <td>
                                              Notes :<BR>
                                              1. Red Color      : No stock Aktif<BR>
                                              2. Green Color    : Stock Expired
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
