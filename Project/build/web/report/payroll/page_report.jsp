
<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.db.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>


<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.payroll.Query,
                  com.dimata.harisma.entity.payroll.PstQuery,
				  com.dimata.harisma.entity.payroll.SalaryLevel,
				  com.dimata.harisma.entity.payroll.PstSalaryLevel,
                  com.dimata.harisma.form.payroll.FrmQuery,
				  com.dimata.harisma.form.payroll.CtrlQuery,
	              com.dimata.harisma.entity.employee.Employee,
				  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.employee.PstEmployee"%>


<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_MONTHLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!
public Vector drawList(String sql,String whereParam,String levelCode,String subQuery)
	{
		Vector result = new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector headerColumn = new Vector(1,1);
		Vector vectContent = new Vector(1,1);
		String whereClause = "";
		//mengambil header dari query
			 headerColumn = PstQuery.getHeaderCoulumn(sql);
			 for (int j =0 ;j<headerColumn.size();j++){
			 	ctrlist.addHeader(""+headerColumn.get(j),"5%","0","0");
		}
		//mengambil where parameter
		if(whereParam.length() > 0){
			whereClause = whereClause + whereParam+"='"+levelCode+"'";
		}
		// jika ada subquery dipake unutk header yang dinamis
		if(subQuery.length() > 0){
			 Vector vectSubQuery = new Vector();
			 vectSubQuery = PstQuery.getSubQuery(subQuery,whereClause);
			 for(int s=0;s<vectSubQuery.size();s++){
			 	ctrlist.addHeader(""+vectSubQuery.get(s),"5%","0","0");	
			 }
		}
		//mengambil content dari query
		vectContent = PstQuery.getContent(sql,whereClause);
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		for (int i = 0; i < vectContent.size(); i++) {
			 Vector rowx = new Vector();
			 Vector rowy = new Vector(1,1);
			 rowy  = (Vector)vectContent.get(i);
			 index = i;
		   for (int k =0 ;k<rowy.size();k++){
			 	rowx.add(rowy.get(k));
			}
			lstData.add(rowx);
			vectDataToPdf.add(rowx);
		}

		//return ctrlist.drawMeList();
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		//result.add(vectReason);
		//return ctrlist.drawList();
		return result;
	}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
String levelCode = FRMQueryString.requestString(request,"salaryLevel");
long oidPeriod = FRMQueryString.requestLong(request,"oidPeriod");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
String titleName = FRMQueryString.requestString(request, "titleName");

// ambil query dengan title = TitleName
Vector tempQueryReport = new Vector(1,1);
String where = "";
String sql = "";
String whereParam = "";
String orderBy = "";
String groupBy ="";
String subQuery = "";
if(iCommand == Command.LIST){
		where = PstQuery.fieldNames[PstQuery.FLD_REPORT_TITLE]+"='"+titleName+"'";
		tempQueryReport = PstQuery.list(0,0,where,"");
		if(tempQueryReport!=null && tempQueryReport.size() > 0){
			for(int i=0;i<tempQueryReport.size();i++){
				Query sqlQuery = (Query)tempQueryReport.get(i);
				sql = sqlQuery.getQuery();
				whereParam = sqlQuery.getWhereParam();
				subQuery = sqlQuery.getSubQuery();
			}
		}
}



							

//System.out.println("yearFrom "+yearForm);


Period pr = new Period();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(oidPeriod);
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}
	

	
Vector listReason = new Vector(1,1);
listReason = PstReason.listAll();

Vector temp = new Vector();

// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
Vector vectReason = new Vector (1,1);
vectResult = drawList(sql,whereParam,levelCode,subQuery);
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
/*vectReason = (Vector)vectResult.get(2);*/

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+footer);  


/*int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));  
vectPresence.add(""+startDatePeriod); */    

if(session.getValue("QUERY_REPORT")!=null){
	session.removeValue("QUERY_REPORT");
}
session.putValue("QUERY_REPORT",vectPresence);

%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Report off Employee Leave</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>

function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="page_report.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="page_report.jsp";
	document.frmreason.submit();
}

function periodChange(){
	document.frmreason.command.value="<%=Command.ADD%>";
	document.frmreason.action="list_absence_reason.jsp";
	document.frmreason.submit();
}

function reportPdf(){
	var linkPage ="<%=printroot%>.report.ListEmployeeLeavePdf";
	window.open(linkPage); 
}

function reportPdfYear(){
	var linkPage ="<%=printroot%>.report.ListAbsenceReasonYearPdf";
	window.open(linkPage); 
}

function deptChange() {
	document.frmreason.command.value = "<%=Command.GOTO%>";
	document.frmreason.hidden_goto_period.value = document.frmreason.reportPeriod.value;

	//document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	document.frmreason.action = "list_absence_reason.jsp";
	document.frmreason.submit();
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
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
    }

    function hideObjectForLockers(){
    }

    function hideObjectForCanteen(){
    }

    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }

	function showObjectForMenu(){
    }
	
	
function cmdUpdateDep(){
	document.frpresence.command.value="<%=Command.ADD%>";
	document.frpresence.action="lateness_monthly_report.jsp"; 
	document.frpresence.submit();
}
	function cmdUpdateLevp(){
	document.frpresence.command.value="<%=Command.ADD%>";
	document.frpresence.action="list_employee_category.jsp"; 
	document.frpresence.submit();
}
</SCRIPT>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee
                  &gt; List Off Employee Leave<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmreason" method="post" action="">
									<input type="hidden" name="command" value="">
									<input type="hidden" name="start" value="<%=start%>">
									<input type="hidden" name="prev_command" value="<%=prevCommand%>">
									<input type="hidden" name="setFooter" value="<%=setFooter%>">
									<input type="hidden" name="titleName" value="<%=titleName%>">
									  <table width="93%" border="0" cellspacing="2" cellpadding="2">
									  <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left">Salary Level </div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector salValue = new Vector(1,1);
										  Vector salKey = new Vector(1,1);
										 // salkey.add(" ALL DEPARTMET");
										  //deptValue.add("0");
						                  Vector listSalLevel = PstSalaryLevel.list(0, 0, "", "LEVEL_NAME");
                                          for(int p=0;p<listSalLevel.size();p++){
										  	SalaryLevel salaryLevel = (SalaryLevel)listSalLevel.get(p);
											salValue.add(""+salaryLevel.getLevelCode());
											salKey.add(salaryLevel.getLevelCode()+" - "+salaryLevel.getLevelName());
										  }
										  %> <%=ControlCombo.draw("salaryLevel",null,""+levelCode,salValue,salKey,"")%></td>
										
                                        </tr>
														
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" nowrap> <div align="left"><a href="javascript:cmdFooter()">Set Footer</a></div></td>
										  
                                          <td width="83%"> <table border="0" cellspacing="0" cellpadding="0" width="550">
											  <% if(setFooter==1){ %>
                                                <td  colspan="2"> : <input name="footer" type="text" size="85" value="<%=footer%>">
												</td>
                                                <td width="1" class="command" nowrap> 
                                                </td>
                                              </tr>
											   <%
											  }
											  %>
                                            </table></td>
                                        </tr>
                                        <tr>
                                          <td>&nbsp;</td>
                                          <td nowrap><div align="right"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></div></td>
                                          <td>
										  <a href="javascript:cmdView()">Search for Employee</a>
										  </td>
                                        </tr>
                                      </table>
									  <%if(iCommand == Command.LIST || iCommand==Command.PRINT){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									  <%if(tempQueryReport != null && tempQueryReport.size() > 0){%>
									    <tr><td><hr></td></tr>
                                        <tr>
											<td>
											<%
												out.println(listData);
												
											%>
												<% //out.println(vectPresence);%>											
											</td>
										  </tr>
										  
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="23%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="5%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="95%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Leave Report</a></b>
												  </td>
												</tr>
											  </table>
											 </td>
										  </tr>
										  <%}%>
									    <%}else{%>
									    <tr><td></td></tr>
											<tr>
											<td>
											<%
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No employee leave  data found ...</div>");											
											%>
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
  <tr>
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
