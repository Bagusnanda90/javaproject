<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.search.SrcLateness,
                  com.dimata.harisma.entity.attendance.EmpSchedule,
                  com.dimata.harisma.entity.attendance.Presence,
                  com.dimata.qdep.db.DBHandler,
                  com.dimata.harisma.entity.attendance.PstPresence,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.ScheduleSymbol,
                  com.dimata.harisma.session.lateness.SessEmployeeLateness,
				  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.gui.jsp.ControlList,
                  com.dimata.util.DateCalc,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.util.Command,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.harisma.session.lateness.LatenessMonthly,
                  com.dimata.harisma.entity.masterdata.PstScheduleSymbol"%>
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
public String drawList(Vector objectClass,Date selectedDateFrom, Date selectedDateTo)
{
	Vector vListCategory = PstEmpCategory.list(0,0,"","");
	System.out.println("selectedDateFrom....="+selectedDateFrom+" selectedDateTo...="+selectedDateTo);
	String result = "";
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat("Level","22%","2","0","center","left");
	
	if(vListCategory!=null && vListCategory.size()>0)
	{
		for(int i=0;i<vListCategory.size();i++)
		{
			EmpCategory objCateg = (EmpCategory)vListCategory.get(i);
			String nmLevel = objCateg.getEmpCategory();
			int k = 1;
			k = k + 1;
			int indexnya = 65 / k;
			String sIdx = String.valueOf(indexnya)+"%";
			ctrlist.dataFormat(nmLevel,sIdx,"0","2","center","left");
		}
	}
	ctrlist.dataFormat("Jumlah","10%","2","0","center","left");
	
	if(vListCategory!=null && vListCategory.size()>0)
	{
		for(int i=0;i<vListCategory.size();i++)
		{
			/*int k = 1;
			k = k + 1;
			int indexnya = 65 / k;
			int index = indexnya / 2;
			String sIdx = String.valueOf(index)+"%";*/
			ctrlist.dataFormat("M","8%","0","0","center","left");
			ctrlist.dataFormat("F","8%","0","0","center","left");
		}
	}
	
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	int l = 0;
	if(objectClass!=null && objectClass.size()>0){
		for(int k=0; k<=objectClass.size(); k++){
			rowx = new Vector();
			if(k==objectClass.size()){
				rowx.add("");
				rowx.add("<b>Total</b>");
				if(vListCategory!=null && vListCategory.size()>0)
				{
					for(int i=0;i<vListCategory.size();i++)
					{
						// jumlah total male dan female
						EmpCategory objEmpCatgeory = (EmpCategory)vListCategory.get(i);
						int totalMale = SessEmployee.listEmployeeCategMale(selectedDateFrom,selectedDateTo,objEmpCatgeory.getOID(),0);
						int totalFemale = SessEmployee.listEmployeeCategFemale(selectedDateFrom,selectedDateTo,objEmpCatgeory.getOID(),0);
						rowx.add("<b>"+totalMale+"</b>");
						rowx.add("<b>"+totalFemale+"</b>");
					}
				}
				int totalJumlah = SessEmployee.listEmployeeSumCateg(selectedDateFrom,selectedDateTo,0);
				rowx.add("<b>"+totalJumlah+"</b>");
				lstData.add(rowx);
				
			}else{
				Vector temp = (Vector)objectClass.get(k);
				Level objLevel = (Level)temp.get(0);
				
				l = l + 1;
				rowx.add(String.valueOf(l));
				rowx.add(objLevel.getLevel());
				if(vListCategory!=null && vListCategory.size()>0)
				{
					for(int i=0;i<vListCategory.size();i++)
					{
						EmpCategory objEmpCatgeory = (EmpCategory)vListCategory.get(i);
						int countMale = SessEmployee.listEmployeeCategMale(selectedDateFrom,selectedDateTo,objEmpCatgeory.getOID(),objLevel.getOID());
						int countFemale = SessEmployee.listEmployeeCategFemale(selectedDateFrom,selectedDateTo,objEmpCatgeory.getOID(),objLevel.getOID());
						rowx.add(String.valueOf(countMale));
						rowx.add(String.valueOf(countFemale));
					}
				}
				int countJumlah = SessEmployee.listEmployeeSumCateg(selectedDateFrom,selectedDateTo,objLevel.getOID());
				rowx.add(String.valueOf(countJumlah));
				lstData.add(rowx);
				/*if(k==objectClass.size()-1)
				{
					break;
				}else{
					rowx = new Vector();
				}*/
			}
		}
	}
	
	result = ctrlist.drawMeList();
	return result;

}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidLevel = FRMQueryString.requestLong(request,"level");
long oidSection = FRMQueryString.requestLong(request,"section");
int idx = FRMQueryString.requestInt(request,"week_idx");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int setFooter = FRMQueryString.requestInt(request, "setFooter");
String footer = FRMQueryString.requestString(request, "footer");
Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
int optionDate= FRMQueryString.requestInt(request, "dateSelected");


Vector tempLevel = new Vector(1,1);
tempLevel = SessEmployee.tmpNmLevel(oidLevel);
//out.println("tempLevel::::::::::::::::::::::::::::::::::"+tempLevel.size());

Vector temp = new Vector();
if(optionDate==0){
		selectedDateFrom = null;
		selectedDateTo = null;
	}else{
		selectedDateFrom = selectedDateFrom;
		selectedDateTo = selectedDateTo;
}
if(iCommand==Command.PRINT)
{
	temp.add(String.valueOf(oidLevel));
	temp.add(String.valueOf(footer));
	
	temp.add(selectedDateFrom);
	temp.add(selectedDateTo);
	session.putValue("LIST_EMP_LEVEL",temp);
}



%>

<!-- End of JSP Block -->
<html>

<head>
<title>HARISMA - Report Lateness Monthly</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>

function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="list_employee_category.jsp";
	document.frpresence.submit();
}

function reportPdf(){
    document.frpresence.command.value="<%=Command.PRINT%>";
	document.frpresence.action="list_employee_category.jsp";
	document.frpresence.submit();
}
function cmdFooter(){
	document.frpresence.setFooter.value="1";
	document.frpresence.action="list_employee_category.jsp";
	document.frpresence.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function cmdSearch(){
		document.frpresence.command.value="<%=Command.LIST%>";
		document.frpresence.action="list_employee_category.jsp";
		document.frpresence.submit();
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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <%@ include file = "../../main/header.jsp" %> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <%@ include file = "../../main/mnmain.jsp" %> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table></td>
  </tr>
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> Employee 
                  &gt; List Off Employee Category </strong></font> </td>
              </tr>
              <tr> 
                <td> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; ">
                          <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td style="border:1px solid <%=garisContent%>" valign="top"> <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <form name="frpresence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <input type="hidden" name="setFooter" value="<%=setFooter%>">
                                      <table width="68%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="12%" align="right" nowrap> 
                                            <div align="left">Level </div></td>
                                          <td width="85%"> : 
                                            <%
                                          Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
						              	  deptValue.add("0");
                                          deptKey.add("All Level...");
										  Vector listLevel = PstLevel.list(0, 0, "", "LEVEL");
                                          for(int d=0;d<listLevel.size();d++){
										  	Level level = (Level)listLevel.get(d);
											deptValue.add(""+level.getOID());
											deptKey.add(level.getLevel());
										  }
										  %> 
                                            <%=ControlCombo.draw("level",null,""+oidLevel,deptValue,deptKey,"onChange=\"javascript:cmdUpdateLevp()\"")%></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td align="right" nowrap>Commencing Date</td>
										  <%
										  if(optionDate==0) {%>
														<td><input type="radio" name="dateSelected" value="0" checked>
                                                        All Date </td>
										  <%}else{
										  %>
										  				<td><input type="radio" name="dateSelected" value="0">
                                                        All Date </td>	
										  <%	
										  }
										  %>
										  <td>&nbsp;</td>
										  </tr>
										  <tr>
										  <td>&nbsp;</td>
										  <td>&nbsp;</td>
										  <%
										  	if(optionDate==1){
											%>
												<td>
                                                  <input type="radio" name="dateSelected" value="1" checked>
                                                   <%=ControlDate.drawDate("check_date_start",selectedDateFrom==null || iCommand == Command.NONE || iCommand == Command.GOTO?new Date():selectedDateFrom,"formElemen",0,-50)%> to <%=ControlDate.drawDate("check_date_finish",selectedDateTo==null || iCommand == Command.NONE || iCommand == Command.GOTO?new Date():selectedDateTo,"formElemen",0,-50)%>                                    
										   		</td>
											<%
											}else{
										  %>
										  <td>
											  <input type="radio" name="dateSelected" value="1">
											   <%=ControlDate.drawDate("check_date_start",selectedDateFrom==null || iCommand == Command.NONE || iCommand == Command.GOTO?new Date():selectedDateFrom,"formElemen",0,-50)%> to <%=ControlDate.drawDate("check_date_finish",selectedDateTo==null || iCommand == Command.NONE || iCommand == Command.GOTO?new Date():selectedDateTo,"formElemen",0,-50)%>                                    
									   		</td>
										   <%
										   	}
										   %>
										   
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="12%" align="right" nowrap> 
                                            <div align="left"><a href="javascript:cmdFooter()">Set 
                                              Footer</a> </div></td>
                                          <%
											 if(setFooter==1){
											%>
                                          <td width="85%"> : 
                                            <input name="footer" type="text" size="85" value="<%=footer%>"> 
                                          </td>
                                          <%
										   }
										   %>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="12%" nowrap> <div align="left"></div></td>
                                          <td width="85%"> <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr> 
                                                <td width="16"></td>
                                                <td width="2"></td>
                                                <td width="94" class="command" nowrap> 
                                                </td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap><div align="right"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></div></td>
                                          <td> <a href="javascript:cmdView()">Search 
                                            for Employee Category</a> </td>
                                        </tr>
                                      </table>
                                      <%if(iCommand == Command.LIST || iCommand==Command.PRINT){%>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <%if(tempLevel != null && tempLevel.size() > 0){%>
                                        <tr> 
                                          <td><hr></td>
                                        </tr>
										<%
											if(optionDate==0){
												selectedDateFrom = null;
												selectedDateTo = null;
											}else{
												selectedDateFrom = selectedDateFrom;
												selectedDateTo = selectedDateTo;
											}
										%>
                                        <tr> 
                                          <td> <%=drawList(tempLevel,selectedDateFrom,selectedDateTo)%> </td>
                                        </tr>
                                        <%if(privPrint){%>
                                        <tr> 
                                          <td> <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                              <tr> 
                                                <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                                  Category</a></b> </td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <%}%>
                                        <%}else{%>
                                        <tr> 
                                          <td></td>
                                        </tr>
                                        <tr> 
                                          <td> <%
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No employee category data found ...</div>");											
											%> 
                                          </td>
                                        </tr>
                                        <%}%>
                                      </table>
                                      <%}%>
                                    </form></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
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
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
</html>
