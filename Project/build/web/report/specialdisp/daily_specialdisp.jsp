<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import ="com.dimata.harisma.session.specialdisp.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_SPECIAL_DISCREPANCIES_REPORT, AppObjInfo.OBJ_SPECIAL_DISCREPANCIES_DAILY_REPORT); %>
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
public Vector drawList(Vector listSpecialDispPresence) 
{
	Vector result = new Vector(1,1);
	if(listSpecialDispPresence!=null && listSpecialDispPresence.size()>0)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%", "2", "0");
		ctrlist.addHeader("Payroll","8%", "2", "0");
		ctrlist.addHeader("Employee","35%", "2", "0");
		ctrlist.addHeader("Schedule","21%", "0", "3");
		ctrlist.addHeader("Symbol","7%", "0", "0");
		ctrlist.addHeader("Time In","7%", "0", "0");
		ctrlist.addHeader("Time Out","7%", "0", "0");
		ctrlist.addHeader("Actual","6%", "2", "");
		ctrlist.addHeader("Remark","28%", "2", "0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		
		// vector of data will used in pdf report
		Vector vectDataToPdf = new Vector(1,1);				
		
		int maxNightShiftPresence = listSpecialDispPresence.size();   
		int dataAmount = 0;																									
		for(int i=0; i<maxNightShiftPresence; i++) 
		{
			SpecialDispDaily specialdispDaily = (SpecialDispDaily)listSpecialDispPresence.get(i);
			Date schldIn = specialdispDaily.getSchldIn();
			Date schldOut = specialdispDaily.getSchldOut();			
					
			Vector rowx = new Vector(1,1);				
			rowx.add(""+(i+1));
			rowx.add(specialdispDaily.getEmpNum().toUpperCase());  
			rowx.add(specialdispDaily.getEmpName().toUpperCase());  
	
			// schedule
			rowx.add(specialdispDaily.getSchldSymbol());
			rowx.add(schldIn!=null ? Formater.formatTimeLocale(schldIn, "HH:mm") : "");		
			rowx.add(schldOut!=null ? Formater.formatTimeLocale(schldOut, "HH:mm") : "");
					
			// actual
			String strAbsence = "SD";
			rowx.add(strAbsence);
			
			// remark
			String remark = specialdispDaily.getRemark();
			rowx.add(remark);
			
			lstData.add(rowx);
			vectDataToPdf.add(rowx);			
		}

		result.add(String.valueOf(DATA_PRINT));			
		result.add(ctrlist.drawList());				
		result.add(vectDataToPdf);								
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));			
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No special dispensation data found ...</div>");				
		result.add(new Vector(1,1));				
	}	
	return result;		
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
Date date = FRMQueryString.requestDate(request,"date");

Vector listSpecialDispPresence = new Vector(1,1);
if(iCommand == Command.LIST)
{
	//listSpecialDispPresence = SessSpecialDisp.listSpecialDispDataDaily(oidDepartment,date);
	// untuk applikasi yang membuthkan parameter section,jika parameternya hanya department pake yang atas
	listSpecialDispPresence = SessSpecialDisp.listSpecialDispDataDaily(oidDepartment,date,oidSection);
}

// process data on control drawlist
Vector vectResult = drawList(listSpecialDispPresence);
int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector)vectResult.get(2);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(date);
vectPresence.add(""+oidDepartment);
vectPresence.add(vectDataToPdf);  

if(session.getValue("SPECIAL_DISP_DAILY")!=null){   
	session.removeValue("SPECIAL_DISP_DAILY");
}
session.putValue("SPECIAL_DISP_DAILY",vectPresence);			
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Special Dispensation Report</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="daily_specialdisp.jsp";
	document.frpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.specialdisp.DailySpecialDispPdf";       
	//window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; Daily Special Dispensation<!-- #EndEditable --> </strong></font> 
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
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="88%"> : 
                                          <%
                                          Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");										  
										  Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
										  deptValue.add("0");
										  deptKey.add(" - ALL - ");
										  for(int d=0;d<listDepartment.size();d++)
										  {
										  	Department department = (Department)listDepartment.get(d);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  out.println(ControlCombo.draw("department",null,""+oidDepartment,deptValue,deptKey));
										  %>
                                          </td>
                                        </tr>
										<tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="88%"> : 
                                          <%
                                          Vector listSection = PstSection.list(0, 0, "", "SECTION");										  
										  Vector sectValue = new Vector(1,1);
										  Vector sectKey = new Vector(1,1);
										  sectValue.add("0");
										  sectKey.add(" - ALL - ");
										  for(int d=0;d<listSection.size();d++)
										  {
										  	Section section = (Section)listSection.get(d);
											sectValue.add(""+section.getOID());
											sectKey.add(section.getSection());
										  }
										  out.println(ControlCombo.draw("section",null,""+oidSection,sectValue,sectKey));
										  %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Date </div>
                                          </td>
                                          <td width="88%">: <%=ControlDate.drawDate("date",date==null || iCommand == Command.NONE?new Date():date,"formElemen",0,installInterval)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="196">
                                              <tr> 
                                                <td width="26"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Special Dispensation"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="166" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Special Dispensation</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  <% if(iCommand == Command.LIST){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									    <tr>
											<td><hr></td>
										</tr>
                                        <tr>
											<td>
											<%
											out.println(listData);
											%>
											</td>
										</tr>
										<%if(dataStatus==DATA_PRINT && privPrint){%>
										<tr>
											<td>
											  
                                            <table width="23%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  
                                                <td width="13%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Daily Special Dispensation"></a></td>												  
                                                <td width="87%"><b><a href="javascript:reportPdf()" class="command">Print 
                                                  Daily Special Dispensation</a></b> 
                                                </td>
												</tr>
											  </table>
											 </td>
										</tr>
										<%}%>										  
									  </table>
									  <%}%>
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
