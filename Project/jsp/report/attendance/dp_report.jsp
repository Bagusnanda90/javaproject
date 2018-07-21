
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.util.Command,
                  com.dimata.util.Formater,				  
                  com.dimata.gui.jsp.ControlList,
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.employee.PstEmployee,				  
                  com.dimata.harisma.entity.masterdata.LeavePeriod,				  
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.attendance.DpStockReporting,				  
                  com.dimata.harisma.entity.search.SrcLeaveManagement,				  
                  com.dimata.harisma.form.search.FrmSrcLeaveManagement,
                  com.dimata.harisma.session.attendance.SessLeaveManagement,				  
                  com.dimata.harisma.session.attendance.DPMontly"%>
<!-- package qdep -->

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_ATTENDANCE_RECORD_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
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
public Vector drawList(Vector listDPReport, String[] vectReportTitle) 
{
	Vector result = new Vector(1,1);
	if(listDPReport!=null && listDPReport.size()>0)
	{

		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		
		ctrlist.addHeader(vectReportTitle[0],"3%", "2", "0");   
		ctrlist.addHeader(vectReportTitle[1],"8%", "2", "0");
		ctrlist.addHeader(vectReportTitle[2],"15%", "2", "0");  
		ctrlist.addHeader(vectReportTitle[3],"5%", "2", "0");    
		ctrlist.addHeader(vectReportTitle[4],"10%", "0", "4");
		ctrlist.addHeader(vectReportTitle[5],"5%", "0", "0");
		ctrlist.addHeader(vectReportTitle[6],"5%", "0", "0");
		ctrlist.addHeader(vectReportTitle[7],"5%", "0", "0");
		ctrlist.addHeader(vectReportTitle[8],"5%", "0", "0");
	
		ctrlist.setLinkRow(-1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
	
		float totPrevAmount = 0;
		float totEarnedQty = 0;
		float totUsedQty = 0;
		float totExpiredQty = 0;
		float totBalanceQty = 0;	
		Vector list = new Vector(1,1);
		Vector lsPdf = new Vector(1,1);
	
		boolean resultAvailable = false;
		int recordNo = 0;		
		for (int i = 0; i < listDPReport.size(); i++) 
		{
			DpStockReporting objDpStockReporting = (DpStockReporting)listDPReport.get(i);			

			String payroll = objDpStockReporting.getPayroll();
			String empName = objDpStockReporting.getName();			
			float prevAmount = objDpStockReporting.getPrevAmount();
			float earnedQty = objDpStockReporting.getEarn();
			float usedQty = objDpStockReporting.getUsed();
			float expiredQty = objDpStockReporting.getExpired();
			float balanceQty = prevAmount + earnedQty - usedQty - expiredQty;
							
			if( !(prevAmount==0 && earnedQty==0 && usedQty==0 && expiredQty==0) )
			{
				recordNo++;
								
				// listing for this jsp page
				Vector rowx = new Vector(1,1);				
				rowx.add(""+recordNo);
				rowx.add(payroll);
				rowx.add(empName);
				rowx.add(prevAmount < 0 ? "("+String.valueOf(prevAmount).substring(1)+")" : String.valueOf(prevAmount));		
				rowx.add(earnedQty < 0 ? "("+String.valueOf(earnedQty).substring(1)+")" : String.valueOf(earnedQty));		
				rowx.add(usedQty < 0 ? "("+String.valueOf(usedQty).substring(1)+")" : String.valueOf(usedQty));		
				rowx.add(expiredQty < 0 ? "("+String.valueOf(expiredQty).substring(1)+")" : String.valueOf(expiredQty));										
				rowx.add(balanceQty < 0 ? "("+String.valueOf(balanceQty).substring(1)+")" : String.valueOf(balanceQty));										
		
				// listing for report (.pdf)
				Vector vect = new Vector(1,1);		
				vect.add(""+recordNo);
				vect.add(payroll);
				vect.add(empName);		
				vect.add(prevAmount < 0 ? "("+String.valueOf(prevAmount).substring(1)+")" : String.valueOf(prevAmount));		
				vect.add(earnedQty < 0 ? "("+String.valueOf(earnedQty).substring(1)+")" : String.valueOf(earnedQty));		
				vect.add(usedQty < 0 ? "("+String.valueOf(usedQty).substring(1)+")" : String.valueOf(usedQty));		
				vect.add(expiredQty < 0 ? "("+String.valueOf(expiredQty).substring(1)+")" : String.valueOf(expiredQty));										
				vect.add(balanceQty < 0 ? "("+String.valueOf(balanceQty).substring(1)+")" : String.valueOf(balanceQty));										
				lsPdf.add(vect);
		
				totPrevAmount = totPrevAmount + prevAmount;
				totEarnedQty = totEarnedQty + earnedQty;
				totUsedQty = totUsedQty + usedQty;
				totExpiredQty = totExpiredQty + expiredQty;
				totBalanceQty = totBalanceQty + balanceQty;								
		
				lstData.add(rowx);		
				
				resultAvailable	= true;			
			}
		}
		
		
		if(resultAvailable)
		{
			// listing for this jsp page 
			String strTotBalanceQty = totBalanceQty < 0 ? "("+String.valueOf(totBalanceQty).substring(1)+")" : String.valueOf(totBalanceQty);			
			Vector rowx = new Vector(1,1);
			rowx.add("");
			rowx.add("");
			rowx.add("<b>"+vectReportTitle[9]+"</b>");
			rowx.add("<b>"+(totPrevAmount < 0 ? "("+String.valueOf(totPrevAmount).substring(1)+")" : String.valueOf(totPrevAmount))+"</b>");
			rowx.add("<b>"+(totEarnedQty < 0 ? "("+String.valueOf(totEarnedQty).substring(1)+")" : String.valueOf(totEarnedQty))+"</b>");
			rowx.add("<b>"+(totUsedQty < 0 ? "("+String.valueOf(totUsedQty).substring(1)+")" : String.valueOf(totUsedQty))+"</b>");
			rowx.add("<b>"+(totExpiredQty < 0 ? "("+String.valueOf(totExpiredQty).substring(1)+")" : String.valueOf(totExpiredQty))+"</b>");
			rowx.add("<b>"+(totBalanceQty < 0 ? "("+String.valueOf(totBalanceQty).substring(1)+")" : String.valueOf(totBalanceQty))+"</b>");	
			lstData.add(rowx);
			lstLinkData.add("0");
		
			// listing for report (.pdf)
			Vector vect = new Vector(1,1);
			vect.add("");
			vect.add("");
			vect.add("TOTAL");
			vect.add(""+(totPrevAmount < 0 ? "("+String.valueOf(totPrevAmount).substring(1)+")" : String.valueOf(totPrevAmount)));
			vect.add(""+(totEarnedQty < 0 ? "("+String.valueOf(totEarnedQty).substring(1)+")" : String.valueOf(totEarnedQty)));
			vect.add(""+(totUsedQty < 0 ? "("+String.valueOf(totUsedQty).substring(1)+")" : String.valueOf(totUsedQty)));
			vect.add(""+(totExpiredQty < 0 ? "("+String.valueOf(totExpiredQty).substring(1)+")" : String.valueOf(totExpiredQty)));
			vect.add(""+(totBalanceQty < 0 ? "("+String.valueOf(totBalanceQty).substring(1)+")" : String.valueOf(totBalanceQty)));	
			lsPdf.add(vect);
		
			result.add(String.valueOf(DATA_PRINT));							
			result.add(ctrlist.drawList());				
			result.add(lsPdf);	
		}
		else
		{
			result.add(String.valueOf(DATA_NULL));					
			result.add("<div class=\"msginfo\">&nbsp;&nbsp;No day off payment data found ...</div>");				
			result.add(new Vector(1,1));																			
		}															
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No day off payment data found ...</div>");				
		result.add(new Vector(1,1));																
	}
	return result;
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_DEPARTMENT]);
long periodId =FRMQueryString.requestLong(request,"period");

System.out.println("OID Department : "+oidDepartment);

// vector report's title
String[] vectReportTitle = {
		"NO",
		"PAYROLL",
		"EMPLOYEE",
		"PREV AMOUNT",
		"DP",
		"MTD",
		"USED",
		"EXPIRED",
		"BALANCE",
		"TOTAL"
};

String strListInJsp = "&nbsp";
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();   
Vector listDPReport = new Vector(1,1);
int dataStatus = DATA_NULL;
if(iCommand != Command.NONE)
{
	FrmSrcLeaveManagement frmSrcLeaveManagement = new FrmSrcLeaveManagement(request, srcLeaveManagement);
	frmSrcLeaveManagement.requestEntityObject(srcLeaveManagement);
	//listDPReport = DPMontly.getListDpStockReport(srcLeaveManagement);	
	listDPReport = DPMontly.listDpStockReport(srcLeaveManagement);			
	 
	Vector list = drawList(listDPReport, vectReportTitle);  
	dataStatus = Integer.parseInt(String.valueOf(list.get(0)));
	strListInJsp = (String)list.get(1);	
	Vector vectToPdf = (Vector)list.get(2);

	// vector data yg akan dikirim ke pdf
	Vector listToPdf = new Vector(1,1);
	listToPdf.add(vectReportTitle);
	listToPdf.add(srcLeaveManagement.getLeavePeriod());
	listToPdf.add(String.valueOf(oidDepartment));
	listToPdf.add(vectToPdf);
	try
	{
		session.putValue("DP_REPORT",listToPdf);
	}
	catch(Exception e)
	{
		System.out.println("Exc when put DP_REPORT to session");	
	}	
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - DP Report</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="dp_report.jsp";
	document.frpresence.submit();
}

function cmdPrint(){
	var linkPage = "<%=printroot%>.report.attendance.DPMonthlyPdf";
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
                  &gt; Attendance &gt; DP Report<!-- #EndEditable --> </strong></font>
                </td>
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
												Vector dept_value = new Vector(1,1);
												Vector dept_key = new Vector(1,1);
												
												dept_key.add("ALL DEPARTMENT");
												dept_value.add("0");
												
												Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
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
                                          <td nowrap width="18%">Period</td>
                                          <td width="3%">:</td>
                                          <td width="78%"><%=ControlDate.drawDateMY(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_PERIOD], srcLeaveManagement.getLeavePeriod()==null ? new Date() : srcLeaveManagement.getLeavePeriod(), "MMM yyyy", "formElemen", 0, installInterval)%> 
										  <%
										  /*Vector listPeriod = PstPeriod.list(0, 0, "", "PERIOD");
										  Vector periodValue = new Vector(1,1);
										  Vector periodKey = new Vector(1,1);
                                          //deptValue.add("0");
                                          //deptKey.add("ALL");

                                          for(int d=0;d<listPeriod.size();d++){
										  	Period period = (Period)listPeriod.get(d);
											periodValue.add(""+period.getOID());
											periodKey.add(period.getPeriod());
										  }*/
										  %> <%//=ControlCombo.draw("period",null,""+periodId,periodValue,periodKey)%>	
										  
										  
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
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report DP"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Report DP</a></td>
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
                                          <td><%=strListInJsp%></td>
                                        </tr>
                                        <%
										if(dataStatus==DATA_PRINT && privPrint)
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

