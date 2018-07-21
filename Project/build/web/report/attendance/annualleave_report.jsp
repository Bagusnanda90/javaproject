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
                  com.dimata.harisma.session.attendance.AnnualLeaveMontly"%>
<!-- package qdep -->

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_AL_REPORT); %>
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
public Vector drawList(Vector listal, Date selectedDate) 
{
	Vector result = new Vector(1,1);
	if(listal!=null && listal.size()>0)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
	
		ctrlist.addHeader("NO","3%", "2", "0");
		ctrlist.addHeader("PAYROLL","8%", "2", "0");
		ctrlist.addHeader("EMPLOYEE","15%", "2", "0");
		ctrlist.addHeader("ANNUAL LEAVE","10%", "0", "7");
		ctrlist.addHeader("BALANCE "+((selectedDate.getYear()+1900)-1),"5%", "0", "0");
		ctrlist.addHeader("ENTITLED "+((selectedDate.getYear()+1900)),"5%", "0", "0");
		ctrlist.addHeader("EARNED YTD","5%", "0", "0");
		ctrlist.addHeader("TAKEN MTD","5%", "0", "0");
		ctrlist.addHeader("TAKEN YTD "+((selectedDate.getYear()+1900)),"5%", "0", "0");
		ctrlist.addHeader("BALANCE YTD","5%", "0", "0");
		ctrlist.addHeader("CLEAR "+((selectedDate.getYear()+1900)-1)+"+"+((selectedDate.getYear()+1900)),"5%", "0", "0");
	
		ctrlist.setLinkRow(-1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
	
		int totclrLast = 0;
		int totEntitled = 0;
		int totEarnedYtd = 0;
		int totTakenMtd = 0;
		int totTakenYtd = 0;
		int totClrCurrent = 0;
		int totClear = 0;
		Vector list = new Vector(1,1);
		Vector lsPdf = new Vector(1,1);
	
		boolean resultAvailable = false;	
		for (int i = 0; i < listal.size(); i++) {
			ALStockReporting objALStockReporting = (ALStockReporting)listal.get(i);			

			String payroll = objALStockReporting.getPayroll();
			String empName = objALStockReporting.getName();			
			int toClearLastYear = objALStockReporting.getToClearLastYear();
			int entitleCurrYear = objALStockReporting.getEntitleCurrYear();			
			int earnedYtd = objALStockReporting.getEarnedYtd();
			int takenMtd = objALStockReporting.getTakenMtd();
			int takenYtd = objALStockReporting.getTakenYtd();
			int toClearYtd = earnedYtd - takenYtd;
			int totalToClear = toClearLastYear + entitleCurrYear - takenYtd;  

			if( !(toClearLastYear==0 && entitleCurrYear==0 && earnedYtd==0 && takenMtd==0 && takenYtd==0) )
			{			
				Vector rowx = new Vector(1,1);	
				rowx.add(""+(i+1));
				rowx.add(payroll);
				rowx.add(empName);
				rowx.add(toClearLastYear < 0 ? "("+String.valueOf(toClearLastYear).substring(1)+")" : String.valueOf(toClearLastYear));
				rowx.add(entitleCurrYear < 0 ? "("+String.valueOf(entitleCurrYear).substring(1)+")" : String.valueOf(entitleCurrYear));
				rowx.add(earnedYtd < 0 ? "("+String.valueOf(earnedYtd).substring(1)+")" : String.valueOf(earnedYtd));
				rowx.add(takenMtd < 0 ? "("+String.valueOf(takenMtd).substring(1)+")" : String.valueOf(takenMtd));
				rowx.add(takenYtd < 0 ? "("+String.valueOf(takenYtd).substring(1)+")" : String.valueOf(takenYtd));
				rowx.add(toClearYtd < 0 ? "("+String.valueOf(toClearYtd).substring(1)+")" : String.valueOf(toClearYtd));
				rowx.add(totalToClear < 0 ? "("+String.valueOf(totalToClear).substring(1)+")" : String.valueOf(totalToClear));
		
				// for pdf
				Vector vect = new Vector(1,1);
				vect.add(""+(i+1));
				vect.add(payroll);
				vect.add(empName);
				vect.add(toClearLastYear < 0 ? "("+String.valueOf(toClearLastYear).substring(1)+")" : String.valueOf(toClearLastYear));
				vect.add(entitleCurrYear < 0 ? "("+String.valueOf(entitleCurrYear).substring(1)+")" : String.valueOf(entitleCurrYear));
				vect.add(earnedYtd < 0 ? "("+String.valueOf(earnedYtd).substring(1)+")" : String.valueOf(earnedYtd));
				vect.add(takenMtd < 0 ? "("+String.valueOf(takenMtd).substring(1)+")" : String.valueOf(takenMtd));
				vect.add(takenYtd < 0 ? "("+String.valueOf(takenYtd).substring(1)+")" : String.valueOf(takenYtd));
				vect.add(toClearYtd < 0 ? "("+String.valueOf(toClearYtd).substring(1)+")" : String.valueOf(toClearYtd));
				vect.add(totalToClear < 0 ? "("+String.valueOf(totalToClear).substring(1)+")" : String.valueOf(totalToClear));
				lsPdf.add(vect);
		
				totclrLast = totclrLast + toClearLastYear;
				totEntitled = totEntitled + entitleCurrYear;
				totEarnedYtd = totEarnedYtd + earnedYtd;
				totTakenMtd = totTakenMtd + takenMtd;
				totTakenYtd = totTakenYtd + takenYtd; 
				totClrCurrent = totClrCurrent + toClearYtd;
				totClear = totClear + totalToClear;
		
				lstData.add(rowx);
				
				resultAvailable = true;
			}
		}
		
		if(resultAvailable)
		{
			Vector rowx = new Vector(1,1);
			rowx.add("");
			rowx.add("");
			rowx.add("<b>TOTAL</b>");
			rowx.add("<b>"+(totclrLast < 0 ? "("+String.valueOf(totclrLast).substring(1)+")" : String.valueOf(totclrLast))+"</b>");
			rowx.add("<b>"+(totEntitled < 0 ? "("+String.valueOf(totEntitled).substring(1)+")" : String.valueOf(totEntitled))+"</b>");
			rowx.add("<b>"+(totEarnedYtd < 0 ? "("+String.valueOf(totEarnedYtd).substring(1)+")" : String.valueOf(totEarnedYtd))+"</b>");
			rowx.add("<b>"+(totTakenMtd < 0 ? "("+String.valueOf(totTakenMtd).substring(1)+")" : String.valueOf(totTakenMtd))+"</b>");
			rowx.add("<b>"+(totTakenYtd < 0 ? "("+String.valueOf(totTakenYtd).substring(1)+")" : String.valueOf(totTakenYtd))+"</b>");
			rowx.add("<b>"+(totClrCurrent < 0 ? "("+String.valueOf(totClrCurrent).substring(1)+")" : String.valueOf(totClrCurrent))+"</b>");
			rowx.add("<b>"+(totClear < 0 ? "("+String.valueOf(totClear).substring(1)+")" : String.valueOf(totClear))+"</b>");
			lstData.add(rowx);
			lstLinkData.add("0");
		
			// for pdf
			Vector vect = new Vector(1,1);
			vect.add("");
			vect.add("");
			vect.add("TOTAL");
			vect.add(""+(totclrLast < 0 ? "("+String.valueOf(totclrLast).substring(1)+")" : String.valueOf(totclrLast)));
			vect.add(""+(totEntitled < 0 ? "("+String.valueOf(totEntitled).substring(1)+")" : String.valueOf(totEntitled)));
			vect.add(""+(totEarnedYtd < 0 ? "("+String.valueOf(totEarnedYtd).substring(1)+")" : String.valueOf(totEarnedYtd)));
			vect.add(""+(totTakenMtd < 0 ? "("+String.valueOf(totTakenMtd).substring(1)+")" : String.valueOf(totTakenMtd)));
			vect.add(""+(totTakenYtd < 0 ? "("+String.valueOf(totTakenYtd).substring(1)+")" : String.valueOf(totTakenYtd)));
			vect.add(""+(totClrCurrent < 0 ? "("+String.valueOf(totClrCurrent).substring(1)+")" : String.valueOf(totClrCurrent)));
			vect.add(""+(totClear < 0 ? "("+String.valueOf(totClear).substring(1)+")" : String.valueOf(totClear)));
			lsPdf.add(vect);
		
			result.add(String.valueOf(DATA_PRINT));							
			result.add(ctrlist.drawList());				
			result.add(lsPdf);															
		}
		else
		{
			result.add(String.valueOf(DATA_NULL));					
			result.add("<div class=\"msginfo\">&nbsp;&nbsp;No annual leave data found ...</div>");				
			result.add(new Vector(1,1));																			
		}			
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No annual leave data found ...</div>");				
		result.add(new Vector(1,1));																
	}
	return result;	
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidLeavePeriod = FRMQueryString.requestLong(request,"leave_period_form");

// for list
Vector listal = new Vector(1,1);
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();   
FrmSrcLeaveManagement frmSrcLeaveManagement = new FrmSrcLeaveManagement(request, srcLeaveManagement);
AnnualLeaveMontly alLeaveMon  = new AnnualLeaveMontly();
int dataStatus = DATA_NULL;
String strListInJsp = "&nbsp";
if(iCommand != Command.NONE)
{
	frmSrcLeaveManagement.requestEntityObject(srcLeaveManagement);
	listal = alLeaveMon.listALStockReport(srcLeaveManagement);		

	try
	{
		session.removeValue("ANNUAL_LEAVE_REPORT");
	}
	catch(Exception e)
	{
		System.out.println("Exc when remove from session(\"ANNUAL_LEAVE_REPORT\") : " + e.toString());	
	}
	
	Vector list = drawList(listal, srcLeaveManagement.getLeavePeriod());
	dataStatus = Integer.parseInt(String.valueOf(list.get(0)));
	strListInJsp = (String)list.get(1);	
	Vector listToPdf = (Vector)list.get(2);	

	Vector listToSession = new Vector(1,1);
	listToSession.add(srcLeaveManagement.getLeavePeriod());
	listToSession.add(""+srcLeaveManagement.getEmpDeptId());
	listToSession.add(listToPdf);
	
	try
	{
		session.putValue("ANNUAL_LEAVE_REPORT",listToSession);
	}
	catch(Exception e)
	{
		System.out.println("Exc when put to session(\"ANNUAL_LEAVE_REPORT\") : " + e.toString());		
	}
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - AL Report</title>
<script language="JavaScript">
<!--
function cmdView()
{
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="annualleave_report.jsp";
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
                  &gt; Attendance &gt; Annual Leave Report<!-- #EndEditable --> </strong></font>
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
                                          <td nowrap width="18%">Period</td>
                                          <td width="3%">:</td>
                                          <td width="78%"><%=ControlDate.drawDateMY(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_PERIOD], srcLeaveManagement.getLeavePeriod()==null ? new Date() : srcLeaveManagement.getLeavePeriod(), "MMM yyyy", "formElemen", 0, installInterval)%> </td>   
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
                                                  Report AL</a></td>
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
