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
                  com.dimata.harisma.session.attendance.*"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_LL_REPORT); %>
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
public Vector drawList(Vector listLL) 
{
	Vector result = new Vector(1,1);
	if(listLL!=null && listLL.size()>0)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
	
		ctrlist.addHeader("NO","3%", "2", "0");
		ctrlist.addHeader("PAYROLL","7%", "2", "0");
		ctrlist.addHeader("EMPLOYEE","20%", "2", "0");
		ctrlist.addHeader("COMM DATE","10%", "2", "0");	
		ctrlist.addHeader("LONG LEAVE","10%", "0", "6");
		ctrlist.addHeader("ENTITLEMENT I","10%", "0", "0");
		ctrlist.addHeader("ENTITLEMENT II","10%", "0", "0");
		ctrlist.addHeader("TOTAL LL","10%", "0", "0");
		ctrlist.addHeader("TAKEN MTD","10%", "0", "0");
		ctrlist.addHeader("TAKEN YTD","10%", "0", "0");
		ctrlist.addHeader("BALANCE","10%", "0", "0");
	
		ctrlist.setLinkRow(-1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
	
		// untuk menyimpan nilai total masing-masing kolom
		int totEnt1 = 0;
		int totEnt2 = 0;
		int totLlTotal = 0;
		int totLlTakenMtd = 0;
		int totLlTakenYtd = 0;
		int totBalance = 0;
		
		// untuk menampung data yang akan ditampilkan di web browser dan session to Pdf
		Vector list = new Vector(1,1);
		Vector lsPdf = new Vector(1,1);
	
	
		// iterasi sebanyak data LL yang ada
		int iterateNo = 0;
		boolean resultAvailable = false;		
		for (int i=0; i<listLL.size(); i++) 
		{
			LLStockReporting objLLStockReporting = (LLStockReporting)listLL.get(i);			

			// get values from objLLStockReporting
			String payroll = objLLStockReporting.getPayroll();
			String empName = objLLStockReporting.getName();
			Date commDate = objLLStockReporting.getCommDate();		
			int intEnt1 = objLLStockReporting.getEntitle1();
			int intEnt2 = objLLStockReporting.getEntitle2();			
			int intTakenMtd = objLLStockReporting.getTakenMtd();
			int intTakenYtd = objLLStockReporting.getTakenYtd();
			int intLlTotal = intEnt1 - intEnt2;
			int intBalance = intLlTotal - intTakenYtd;			
			
			if( !(intEnt1==0 && intEnt2==0 && intTakenMtd==0 && intTakenYtd==0) )
			{
				// calculate for reporting (mtd)
				if(intEnt1 < intTakenMtd)
				{
					if( (intEnt1+intEnt2) >= intTakenMtd )
					{
						intTakenMtd = intTakenMtd - intEnt1;					
					}
					else
					{
						intTakenMtd = intTakenMtd - intEnt1 - intEnt2;
					}							
				}
				
				
				
				// calculate for reporting (ytd)
				if(intEnt1 < intTakenYtd)
				{
					if( (intEnt1+intEnt2) >= intTakenYtd )       
					{
						intLlTotal = intEnt2;
						intTakenYtd = intTakenYtd - intEnt1;					
						intBalance = intLlTotal - intTakenYtd;										
						intEnt1 = 0;																							
					}
					else
					{					
						intLlTotal = 0;														
						intBalance = intEnt1 + intEnt2 - intTakenYtd;					
						intTakenYtd = intTakenYtd - intEnt1 - intEnt2;																				
						intEnt1 = 0;
						intEnt2 = 0;										
					}							
				}
				
				if( !(intLlTotal==intTakenYtd &&  intTakenMtd==0) || (intLlTotal==intTakenYtd &&  intTakenMtd==0) || intEnt1 == 0 ) 
				{
					iterateNo++;
					
					Vector rowx = new Vector(1,1);
					rowx.add(""+iterateNo);
					rowx.add(payroll);
					rowx.add(empName);
					rowx.add(Formater.formatDate(commDate,"dd-MMM-yyyy"));		
					rowx.add(intEnt1 < 0 ? "("+String.valueOf(intEnt1).substring(1)+")" : String.valueOf(intEnt1));
					rowx.add(intEnt2 < 0 ? "("+String.valueOf(intEnt2).substring(1)+")" : String.valueOf(intEnt2));
					rowx.add(intLlTotal < 0 ? "("+String.valueOf(intLlTotal).substring(1)+")" : String.valueOf(intLlTotal));
					rowx.add(intTakenMtd < 0 ? "("+String.valueOf(intTakenMtd).substring(1)+")" : String.valueOf(intTakenMtd));
					rowx.add(intTakenYtd < 0 ? "("+String.valueOf(intTakenYtd).substring(1)+")" : String.valueOf(intTakenYtd));								
					rowx.add(intBalance < 0 ? "("+String.valueOf(intBalance).substring(1)+")" : String.valueOf(intBalance));										
			
					// for pdf
					Vector vect = new Vector(1,1);
					vect.add(""+iterateNo);
					vect.add(payroll);
					vect.add(empName);
					vect.add(Formater.formatDate(commDate,"dd-MMM-yyyy"));		
					vect.add(intEnt1 < 0 ? "("+String.valueOf(intEnt1).substring(1)+")" : String.valueOf(intEnt1));
					vect.add(intEnt2 < 0 ? "("+String.valueOf(intEnt2).substring(1)+")" : String.valueOf(intEnt2));
					vect.add(intLlTotal < 0 ? "("+String.valueOf(intLlTotal).substring(1)+")" : String.valueOf(intLlTotal));
					vect.add(intTakenMtd < 0 ? "("+String.valueOf(intTakenMtd).substring(1)+")" : String.valueOf(intTakenMtd));
					vect.add(intTakenYtd < 0 ? "("+String.valueOf(intTakenYtd).substring(1)+")" : String.valueOf(intTakenYtd));								
					vect.add(intBalance < 0 ? "("+String.valueOf(intBalance).substring(1)+")" : String.valueOf(intBalance));															
					lsPdf.add(vect);		
					
			
					totEnt1 = totEnt1 + intEnt1;
					totEnt2 = totEnt2 + intEnt2;
					totLlTotal = totLlTotal + intLlTotal;
					totLlTakenMtd = totLlTakenMtd + intTakenMtd;
					totLlTakenYtd = totLlTakenYtd + intTakenYtd;
					totBalance = totBalance + intBalance;
			
					lstData.add(rowx);
				}
				
				resultAvailable = true;
			}
		}		

		
		if(resultAvailable)
		{					
			Vector rowx = new Vector(1,1);  
			rowx.add("");
			rowx.add("");
			rowx.add("");	
			rowx.add("<b>TOTAL</b>");
			rowx.add("<b>"+(totEnt1 < 0 ? "("+String.valueOf(totEnt1).substring(1)+")" : String.valueOf(totEnt1))+"</b>");
			rowx.add("<b>"+(totEnt2 < 0 ? "("+String.valueOf(totEnt2).substring(1)+")" : String.valueOf(totEnt2))+"</b>");
			rowx.add("<b>"+(totLlTotal < 0 ? "("+String.valueOf(totLlTotal).substring(1)+")" : String.valueOf(totLlTotal))+"</b>");
			rowx.add("<b>"+(totLlTakenMtd < 0 ? "("+String.valueOf(totLlTakenMtd).substring(1)+")" : String.valueOf(totLlTakenMtd))+"</b>");
			rowx.add("<b>"+(totLlTakenYtd < 0 ? "("+String.valueOf(totLlTakenYtd).substring(1)+")" : String.valueOf(totLlTakenYtd))+"</b>");
			rowx.add("<b>"+(totBalance < 0 ? "("+String.valueOf(totBalance).substring(1)+")" : String.valueOf(totBalance))+"</b>");
			lstData.add(rowx);
			lstLinkData.add("0"); 
		
			// for pdf
			Vector vect = new Vector(1,1);
			vect.add("");
			vect.add("");
			vect.add("");	
			vect.add("TOTAL");
			vect.add(""+(totEnt1 < 0 ? "("+String.valueOf(totEnt1).substring(1)+")" : String.valueOf(totEnt1)));
			vect.add(""+(totEnt2 < 0 ? "("+String.valueOf(totEnt2).substring(1)+")" : String.valueOf(totEnt2)));
			vect.add(""+(totLlTotal < 0 ? "("+String.valueOf(totLlTotal).substring(1)+")" : String.valueOf(totLlTotal)));
			vect.add(""+(totLlTakenMtd < 0 ? "("+String.valueOf(totLlTakenMtd).substring(1)+")" : String.valueOf(totLlTakenMtd)));
			vect.add(""+(totLlTakenYtd < 0 ? "("+String.valueOf(totLlTakenYtd).substring(1)+")" : String.valueOf(totLlTakenYtd)));
			vect.add(""+(totBalance < 0 ? "("+String.valueOf(totBalance).substring(1)+")" : String.valueOf(totBalance)));			
			lsPdf.add(vect);		
	
			result.add(String.valueOf(DATA_PRINT));							
			result.add(ctrlist.drawList());				
			result.add(lsPdf);																	
		}
		else
		{
			result.add(String.valueOf(DATA_NULL));					
			result.add("<div class=\"msginfo\">&nbsp;&nbsp;No long leave data found ...</div>");				
			result.add(new Vector(1,1));																				
		}	
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No long leave data found ...</div>");				
		result.add(new Vector(1,1));																	
	}
	return result;	
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");

// for list
Vector listLL = new Vector(1,1);
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();   
FrmSrcLeaveManagement frmSrcLeaveManagement = new FrmSrcLeaveManagement(request, srcLeaveManagement);
LongLeaveMonthly objLongLeaveMonthly  = new LongLeaveMonthly();
int dataStatus = DATA_NULL;
String strListInJsp = "&nbsp";
if(iCommand != Command.NONE)
{
	frmSrcLeaveManagement.requestEntityObject(srcLeaveManagement);
	listLL = objLongLeaveMonthly.listLLStockReport(srcLeaveManagement);		

	try
	{
		session.removeValue("LONG_LEAVE_REPORT");
	}
	catch(Exception e)
	{
		System.out.println("Exc when remove from session(\"LONG_LEAVE_REPORT\") : " + e.toString());
	}	

	Vector list = drawList(listLL);	
	dataStatus = Integer.parseInt(String.valueOf(list.get(0)));
	strListInJsp = (String)list.get(1);	
	Vector listToPdf = (Vector)list.get(2);	

	Vector listToSession = new Vector(1,1);
	listToSession.add(srcLeaveManagement.getLeavePeriod());
	listToSession.add(""+srcLeaveManagement.getEmpDeptId());
	listToSession.add(listToPdf);
	
	try
	{
		session.putValue("LONG_LEAVE_REPORT", listToSession);
	}
	catch(Exception e)
	{
		System.out.println("Exc when put to session(\"LONG_LEAVE_REPORT\") : " + e.toString());	
	}
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - LL Report</title>
<script language="JavaScript">
<!--
function cmdView()
{
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="longleave_report.jsp";
	document.frpresence.submit();
}

function cmdPrint()
{
	var linkPage = "<%=printroot%>.report.attendance.LongLeaveMonthlyPdf";
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
                  &gt; Attendance &gt; Long Leave Report<!-- #EndEditable --> 
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
                                                  Report LL</a></td>
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
