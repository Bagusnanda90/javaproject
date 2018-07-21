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
<%@ page import ="com.dimata.harisma.session.employee.*"%>
<%@ page import ="com.dimata.harisma.entity.search.*" %>
<%@ page import ="com.dimata.harisma.form.search.*" %>

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
public Vector drawList(Vector listEndPeriod, String[] vectReportTitle) 
{
        Vector result = new Vector(1,1);
        
	if(listEndPeriod != null && listEndPeriod.size() > 0)
	{
                ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader(vectReportTitle[0],"5%", "0", "0");   
		ctrlist.addHeader(vectReportTitle[1],"10%", "0", "0");
		ctrlist.addHeader(vectReportTitle[2],"25%", "0", "0"); 
		ctrlist.addHeader(vectReportTitle[3],"20%", "0", "0");    
		ctrlist.addHeader(vectReportTitle[4],"20%", "0", "0");
                ctrlist.addHeader(vectReportTitle[5],"10%", "0", "0");    
		ctrlist.addHeader(vectReportTitle[6],"10%", "0", "0");
                	
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
                Vector vectDataToPdf = new Vector(1,1);	
		ctrlist.reset();	

		boolean resultAvailable = false;
		int recordNo = 0;
                
		for(int i=0; i<listEndPeriod.size(); i++) 
		{
                    Vector list = (Vector)listEndPeriod.get(i);

                    Employee emp = (Employee)list.get(0);
                    Department dept = (Department)list.get(1);
                    Section sec = (Section)list.get(2);

                    recordNo++;

                    // listing for this jsp page
                    Vector rowx = new Vector(1,1);
                    rowx.add(String.valueOf(recordNo));
                    rowx.add(emp.getEmployeeNum());
                    rowx.add(emp.getFullName());
                    rowx.add(dept.getDepartment());
                    rowx.add(sec.getSection());
                    rowx.add(String.valueOf(emp.getCommencingDate()));
                    rowx.add(String.valueOf(emp.getResignedDate()));

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
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No trainee data found ...</div>");				
		result.add(new Vector(1,1));																
	}
	return result;

}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);

String[] vectReportTitle = {
		"NO",
		"PAYROLL",
		"EMPLOYEE",
		"DEPARTMENT",
		"SECTION",
                "COMMENCING DATE",
                "END DATE"
};

SrcEndTraining srcEndTraining = new SrcEndTraining();

String listData = "";
int dataStatus = DATA_NULL;
Vector vectDataToPdf = new Vector(1,1);
Vector listEndPeriod = new Vector(1,1);

if(iCommand == Command.LIST) {
    FrmSrcEndTraining frmEndTraining = new FrmSrcEndTraining(request, srcEndTraining);
    frmEndTraining.requestEntityObject(srcEndTraining);
    listEndPeriod = SessEndTraining.getEndTraining(srcEndTraining);
    
    Vector listResult = drawList(listEndPeriod, vectReportTitle);
    
    dataStatus = Integer.parseInt(String.valueOf(listResult.get(0)));
    listData = String.valueOf(listResult.get(1));
    vectDataToPdf = (Vector)listResult.get(2);
    
    Vector sessionEndTraining = new Vector(1,1);
    
    sessionEndTraining.add(vectReportTitle);
    sessionEndTraining.add(srcEndTraining);
    sessionEndTraining.add(vectDataToPdf);
    
    session.putValue("END_TRAINING", sessionEndTraining);
}

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Trainee End Period</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="end_tr_rpt.jsp";
	document.frpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.employee.EndTrainingPdf";  
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Trainee 
                  &gt; End Period<!-- #EndEditable --> </strong></font> 
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
                                                  
                                                  out.println(ControlCombo.draw(FrmSrcEndTraining.fieldNames[FrmSrcEndTraining.FRM_FIELD_DEPARTMENT],null,String.valueOf(srcEndTraining.getDepartmentId()),deptValue,deptKey));
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
                                                  out.println(ControlCombo.draw(FrmSrcEndTraining.fieldNames[FrmSrcEndTraining.FRM_FIELD_SECTION],null,String.valueOf(srcEndTraining.getSectionId()),sectValue,sectKey));
                                          %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Ending Period In</div>
                                          </td>
                                          <td width="88%">: 
                                               <% 
                                                    Vector value = new Vector(1,1);
                                                    Vector key = new Vector(1,1);
                                                    
                                                    key.add("1 Week");
                                                    value.add("1");
                                                    
                                                    key.add("2 Weeks");
                                                    value.add("2");
                                                    
                                                    key.add("3 Weeks");
                                                    value.add("3");
                                                    
                                                    key.add("4 Weeks");
                                                    value.add("4");
              
                                                    out.println(ControlCombo.draw(FrmSrcEndTraining.fieldNames[FrmSrcEndTraining.FRM_FIELD_END_PERIOD],"formElemen", null, String.valueOf(srcEndTraining.getEndPeriod()), value, key, ""));
                                            %>
					  </td>
                                        </tr>	
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Sort By</div>
                                          </td>
                                          <td width="88%">: 
                                               <% 
                                                    Vector sortvalue = new Vector(1,1);
                                                    Vector sortkey = new Vector(1,1);
                                                    
                                                    sortkey.add("End Of Period");
                                                    sortvalue.add(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]);
                                                    
                                                    sortkey.add("Department");
                                                    sortvalue.add(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                    
                                                    sortkey.add("Section");
                                                    sortvalue.add(PstSection.fieldNames[PstSection.FLD_SECTION]);
                                                    
                                                    sortkey.add("Payroll Number");
                                                    sortvalue.add(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                                                    
                                                    sortkey.add("Employee Name");
                                                    sortvalue.add(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
              
                                                    out.println(ControlCombo.draw(FrmSrcEndTraining.fieldNames[FrmSrcEndTraining.FRM_FIELD_SORT_FIELD],"formElemen", null, String.valueOf(srcEndTraining.getSortField()), sortvalue, sortkey, ""));
                                            %>
					  </td>
                                        </tr>		
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
                                      End Period</a></b> 
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
