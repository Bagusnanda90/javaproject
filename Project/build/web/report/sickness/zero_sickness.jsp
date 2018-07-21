<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<%@ page import ="java.util.Calendar"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.attendance.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import ="com.dimata.harisma.session.sickness.*"%>

<%@ include file = "../../main/javainit.jsp" %>

<%-- YANG INI BELUM DIEDIT --%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_SICK_DAYS_REPORT, AppObjInfo.OBJ_SICK_DAYS_MONTHLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>


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
public Vector drawList(Vector listZeroSickness, String[] vectReportTitle) 
{
	Vector result = new Vector(1,1);
        
	if(listZeroSickness!=null && listZeroSickness.size()>0)
	{
                ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader(vectReportTitle[0],"5%", "0", "0");   
		ctrlist.addHeader(vectReportTitle[1],"10%", "0", "0");
		ctrlist.addHeader(vectReportTitle[2],"30%", "0", "0"); 
		ctrlist.addHeader(vectReportTitle[3],"30%", "0", "0");    
		ctrlist.addHeader(vectReportTitle[4],"25%", "0", "0");
                	
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
                Vector vectDataToPdf = new Vector(1,1);	
		ctrlist.reset();	

		boolean resultAvailable = false;
		int recordNo = 0;
                
		for(int i=0; i<listZeroSickness.size(); i++) 
		{
                    ZeroSickness list = (ZeroSickness)listZeroSickness.get(i);

                    String empNum = list.getEmpNum();
                    String fullName = list.getEmpName();
                    String dept = list.getDepartment();
                    String sec = list.getSection();

                    recordNo++;

                    // listing for this jsp page
                    Vector rowx = new Vector(1,1);
                    rowx.add(String.valueOf(recordNo));
                    rowx.add(empNum);
                    rowx.add(fullName);
                    rowx.add(dept);
                    rowx.add(sec);

                    lstData.add(rowx);
                    vectDataToPdf.add(rowx);

                    resultAvailable = true;
                }		
					
		result.add(String.valueOf(DATA_PRINT));							
		result.add(ctrlist.draw());				
		result.add(vectDataToPdf);		  														
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No zero sickness data found ...</div>");				
		result.add(new Vector(1,1));																
	}
	return result;
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
long periodStartId = FRMQueryString.requestLong(request,"periodStart");
long periodEndId = FRMQueryString.requestLong(request,"periodEnd");
//String footer = FRMQueryString.requestString(request,"footer");
//int setFooter =  FRMQueryString.requestInt(request,"setFooter");
SessSickness sessSickness = new SessSickness();
String empNum = FRMQueryString.requestString(request, "emp_number");
String fullName = FRMQueryString.requestString(request, "full_name");

String[] vectReportTitle = {
		"NO",
		"PAYROLL",
		"EMPLOYEE",
		"DEPARTMENT",
		"SECTION"
};

Period pr = new Period();

Date dateStart = new Date();
Date dateEnd = new Date();
String periodStartName ="";
String periodEndName ="";

try{
        pr = PstPeriod.fetchExc(periodStartId);
        dateStart = pr.getStartDate();
        periodStartName = pr.getPeriod();

        pr = PstPeriod.fetchExc(periodEndId);
        dateEnd = pr.getStartDate();
        periodEndName = pr.getPeriod();
        
        // check to ensure dateEnd is bigger than dateStart
        if(dateStart.compareTo(dateEnd) > 0) {
            dateEnd = dateStart;
            periodEndName = periodStartName;
            
            dateStart = pr.getStartDate();
            periodStartName = pr.getPeriod();
        }       
}
catch(Exception e){}

Vector listZeroSickness = new Vector(1,1);

if(iCommand == Command.LIST)
{	
    //listZeroSickness = SessSickness.listZeroSickness(oidDepartment,oidSection, dateStart, dateEnd);
    //listZeroSickness = SessSickness.listZeroSickness(oidDepartment, oidSection, periodStartId, periodEndId);
    listZeroSickness = SessSickness.listZeroSickness(oidDepartment, oidSection, periodStartId, periodEndId,empNum.trim(),fullName.trim());
}

Vector vectResult = drawList(listZeroSickness, vectReportTitle);

int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector)vectResult.get(2);


// design vector that handle data to store in session
Vector vectZeroSickness = new Vector(1,1);

vectZeroSickness.add(vectReportTitle);
vectZeroSickness.add(String.valueOf(oidDepartment));
vectZeroSickness.add(String.valueOf(oidSection));
vectZeroSickness.add(periodStartName);
vectZeroSickness.add(periodEndName);
vectZeroSickness.add(vectDataToPdf);
vectZeroSickness.add(empNum);
vectZeroSickness.add(fullName);
session.putValue("ZERO_SICKNESS",vectZeroSickness);	

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Zero Sickness Report</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="zero_sickness.jsp";
	document.frpresence.submit();
}

/*function cmdFooter(){
	document.frpresence.setFooter.value="1";
	document.frpresence.action="zero_sickness.jsp";
	document.frpresence.submit();
}*/

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.sickness.ZeroSicknessPdf";  
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table <%=noBack%> width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                  &gt; Zero Sickness<!-- #EndEditable --> </strong></font> 
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
									<%--<input type="hidden" name="setFooter" value="<%=setFooter%>">--%>
									  <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <!-- update by satrya 2012-10-03 -->
                                        <tr> 
                                                       
                                                        <td width="9%"> <div align="left">Payrol Num </div></td>
                                                        <td width="88%">:
                                                            <input type="text" size="20" name="emp_number"  value="<%= sessSickness.getEmpNum() != null ? sessSickness.getEmpNum() : empNum %>" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                    </tr>
                                                        <tr>
                                                                   
                                                                    <td width="9%"> <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME) %></div></td>
                                                                    <td width="88%">:
                                                                        <input type="text" size="50" name="full_name"  value="<%= sessSickness.getEmpFullName() != null ? sessSickness.getEmpFullName()  : fullName%>" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                                </tr>
                                                                
						<!-- end -->
										<tr> 
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
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Period From</div>
                                          </td>
                                          <td width="88%">: 
                                              <%
                                                  Vector listPeriod = PstPeriod.list(0, 0, "", "PERIOD");
                                                  Vector periodValue = new Vector(1,1);
                                                  Vector periodKey = new Vector(1,1);

                                                  for(int d=0;d<listPeriod.size();d++){
                                                        Period period = (Period)listPeriod.get(d);
                                                        periodValue.add(""+period.getOID());
                                                        periodKey.add(period.getPeriod());
                                                  }
                                              %> 
                                            
                                                <%=ControlCombo.draw("periodStart",null,""+periodStartId,periodValue,periodKey)%>	
						
                                                &nbsp;&nbsp;To&nbsp;&nbsp;
                                          
                                                <%=ControlCombo.draw("periodEnd",null,""+periodEndId,periodValue,periodKey)%>	
						
					  </td>
                                        </tr>
					<!--<tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><a href="javascript:cmdFooter()">Set Footer</a></div>
                                          </td>
                                              <%-- if(setFooter==1){ %>
                                                <td  colspan="2"> : 
                                                    <input name="footer" type="text" size="85" value="<%=footer%>">
                                                </td>
                                                <td width="1" class="command" nowrap> 
                                                </td>
                                              <% } --%>	
                                        </tr>-->
                                        <tr> 
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="239">
                                              <tr> 
                                                <td width="26"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Sickness"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="209" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Data</a></td>
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
                                                                              <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                  <tr>

                                    <td width="11%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Monthly Sickness"></a></td>

                                    <td width="89%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                      Zero Sickness</a></b> 
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
