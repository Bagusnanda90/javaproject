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
public Vector drawList(Vector objectClass)
{
   	
	//String result = "";
	Vector result = new Vector(1,1);
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	/*ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat("Kategori","21%","2","0","center","left");
	ctrlist.dataFormat("Jenis Kelamin","10%","2","0","center","left");
	ctrlist.dataFormat("Nama","22%","0","0","center","left");
	ctrlist.dataFormat("Department","22%","0","0","center","left");
	ctrlist.dataFormat("Section","22%","0","0","center","left");*/
	
	ctrlist.addHeader("No.","3%");
	ctrlist.addHeader("Category","21%");
	ctrlist.addHeader("Gender","7%");
	ctrlist.addHeader("Full Name","35%");
	ctrlist.addHeader("Department","20%");
	ctrlist.addHeader("Section","22%");
	
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.reset();
	
	int index = -1;
	int l = 0;
	Vector vListPdf = new Vector(1,1);
	
	if(objectClass!=null && objectClass.size()>0)
	{   
	   for(int j=0;j<objectClass.size();j++)
		{	
			EmpCategory empCategory = (EmpCategory)objectClass.get(j);
			long oidCategory = empCategory.getOID();
			l = l + 1;
			Vector rowx = new Vector(1,1);
			rowx.add(String.valueOf(l));
			rowx.add(empCategory.getEmpCategory());
			
			Vector listNmFemaleEmployee = SessEmployee.nameFemaleCategory(oidCategory);
			Vector listNmMaleEmployee = SessEmployee.nameMaleCategory(oidCategory);
			
			rowx.add("Female");
			
			if(listNmFemaleEmployee.size() > 0){
				for(int k =0;k<listNmFemaleEmployee.size();k++)
				{
					Vector tempFemale = (Vector)listNmFemaleEmployee.get(k);
					Employee employee = (Employee)tempFemale.get(0);
					Department dept = (Department)tempFemale.get(1);
					Section sect = (Section)tempFemale.get(2);
					
					if(k!=0){
						rowx = new Vector(1,1);
						rowx.add("");
						rowx.add("");
						rowx.add("");
					}
					rowx.add(""+employee.getFullName());
					rowx.add(""+dept.getDepartment());
					rowx.add(""+sect.getSection());
					lstData.add(rowx);
					vListPdf.add(rowx);
				}
			}
			else{
				rowx.add("-");
				rowx.add("-");
				rowx.add("-");
				lstData.add(rowx);
				vListPdf.add(rowx);		
			}
			
			rowx = new Vector(1,1);
			rowx.add("");
			rowx.add("");	
			rowx.add("Total");
			rowx.add(""+listNmFemaleEmployee.size()+" EMPLOYEE (S)");
			rowx.add("");
			rowx.add("");	
			lstData.add(rowx);
			vListPdf.add(rowx);
			
			rowx = new Vector(1,1);
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");	
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");	
			lstData.add(rowx);
			//vListPdf.add(rowx);
			
			rowx = new Vector(1,1);
			rowx.add("");
			rowx.add("");
			rowx.add("Male");
			if(listNmMaleEmployee.size() > 0){
				for(int k =0;k<listNmMaleEmployee.size();k++)
				{
					Vector tempMale = (Vector)listNmMaleEmployee.get(k);
					Employee employee = (Employee)tempMale.get(0);
					Department dept = (Department)tempMale.get(1);
					Section sect = (Section)tempMale.get(2);
					
						if(k!=0){
							rowx = new Vector(1,1);
							rowx.add("");
							rowx.add("");
							rowx.add("");
						}
						rowx.add(""+employee.getFullName());
						rowx.add(""+dept.getDepartment());
						rowx.add(""+sect.getSection());
						lstData.add(rowx);
						vListPdf.add(rowx);
				}
			}
			else{
				rowx.add("-");
				rowx.add("-");
				rowx.add("-");
				lstData.add(rowx);
				vListPdf.add(rowx);		
			}
			
			rowx = new Vector(1,1);
			rowx.add("");
			rowx.add("");	
			rowx.add("Total");
			rowx.add(""+listNmMaleEmployee.size()+" EMPLOYEE (S)");
			rowx.add("");
			rowx.add("");	
			lstData.add(rowx);
			vListPdf.add(rowx);
			
			rowx = new Vector(1,1);
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");	
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");
			rowx.add("&nbsp;");	
			lstData.add(rowx);
			//vListPdf.add(rowx);
		
		}
		
	}
	result.add(ctrlist.draw());
	result.add(vListPdf);
	return result;

}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidCategory = FRMQueryString.requestLong(request,"category");
long oidSection = FRMQueryString.requestLong(request,"section");
int idx = FRMQueryString.requestInt(request,"week_idx");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");

String whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" = "+oidCategory;
Vector vListCategoryTemp = new Vector();
if(oidCategory!=0)
	vListCategoryTemp = PstEmpCategory.list(0,0,whereClause,"");
else
	vListCategoryTemp = PstEmpCategory.list(0,0,"","");

Vector vListResult = drawList(vListCategoryTemp);
String listData = String.valueOf(vListResult.get(0));
Vector vectDataToPdf = (Vector)vListResult.get(1);

Vector temp = new Vector();	
if(iCommand==Command.PRINT)
{
	temp.add(String.valueOf(oidCategory));
	temp.add(vectDataToPdf);
	
	if(session.getValue("LIST_EMP_CATEGORY_BY_NAME")!=null){
		session.removeValue("LIST_EMP_CATEGORY_BY_NAME");
	}
	session.putValue("LIST_EMP_CATEGORY_BY_NAME",temp);
}

	
%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Report List Employee</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategByNamePdf");
<%}%>

function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="list_employee_by_Name.jsp";
	document.frpresence.submit();
}

function reportPdf(){
    document.frpresence.command.value="<%=Command.PRINT%>";
	document.frpresence.action="list_employee_by_Name.jsp";
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
	document.frpresence.action="list_employee_by_Name.jsp"; 
	document.frpresence.submit();
}
</SCRIPT>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee
                  &gt; List Off Employee <!-- #EndEditable --> </strong></font> </td>
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
									<input type="hidden" name="command" value="">
									<input type="hidden" name="start" value="<%=start%>">
									<input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Level </div></td>
                                          <td width="88%"> : 
                                            <%
                                          Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
						              	  deptValue.add("0");
                                          deptKey.add("All Category...");
										  Vector vListCategory = PstEmpCategory.list(0,0,"","");
                                          for(int d=0;d<vListCategory.size();d++){
										  	EmpCategory empCategory = (EmpCategory)vListCategory.get(d);
											deptValue.add(""+empCategory.getOID());
											deptKey.add(empCategory.getEmpCategory());
										  }
										  %> <%=ControlCombo.draw("category",null,""+oidCategory,deptValue,deptKey,"onChange=\"javascript:cmdUpdateLevp()\"")%></td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" nowrap> <div align="left"></div></td>
                                          <td width="88%"> <table border="0" cellspacing="0" cellpadding="0" width="137">
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
                                          <td>
										  <a href="javascript:cmdView()">Search for Employee</a>
										  </td>
                                        </tr>
                                      </table>
									 <%if(iCommand == Command.LIST || iCommand==Command.PRINT){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									  <%if(vListCategoryTemp != null && vListCategoryTemp.size() > 0){%>
									    <tr><td><hr></td></tr>
                                        <tr>
											<td>
												<%//=drawList(vListCategoryTemp)
												out.println(listData);
												%>											
											</td>
										  </tr>
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Category</a></b>
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
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No lateness data found ...</div>");											
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
