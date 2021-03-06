<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.gui.jsp.ControlList,
				  com.dimata.gui.jsp.ControlLine,				  
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.LeavePeriod,
                  com.dimata.util.Command,
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.util.Formater,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.employee.PstEmployee,
                  com.dimata.harisma.entity.attendance.LLStockManagement,
                  com.dimata.harisma.form.attendance.FrmLLStockManagement,
                  com.dimata.harisma.entity.attendance.PstLLStockManagement,
                  com.dimata.harisma.entity.search.SrcLeaveManagement,
                  com.dimata.harisma.form.search.FrmSrcLeaveManagement,
                  com.dimata.harisma.session.attendance.SessLeaveManagement,				  				  				  				  				  
                  com.dimata.harisma.form.attendance.CtrlLLStockManagement"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_LL_MANAGEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
// untuk menampilkan daftar employee yang memiliki Dp dalam periode terpilih
// created by Gadnyana
// edited@documented by Edhy
public String drawListSummary(int offset, Vector listLL) 
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","5%", "0", "0");
	ctrlist.addHeader("Payroll","20%", "0", "0");
	ctrlist.addHeader("Employee","45%", "0", "0");
	ctrlist.addHeader("Quantity","10%", "0", "0");
	ctrlist.addHeader("Taken","10%", "0", "0");
	ctrlist.addHeader("Balance","10%", "0", "0");			
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i = 0; i < listLL.size(); i++) {
		Vector rowx = new Vector(1,1);
        Vector ls = (Vector)listLL.get(i);
        LLStockManagement llStockManagement = (LLStockManagement)ls.get(0);
        Employee emp = (Employee)ls.get(1);

        rowx.add(""+(offset+i+1));
        rowx.add(emp.getEmployeeNum());
        rowx.add(emp.getFullName());
        rowx.add(""+llStockManagement.getLLQty());
        rowx.add(""+llStockManagement.getQtyUsed());
        rowx.add(""+llStockManagement.getQtyResidue());
		
        lstData.add(rowx);
        lstLinkData.add(""+llStockManagement.getEmployeeId());
    }
	return ctrlist.drawList();
}
%>


<%
boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
long empOid = FRMQueryString.requestLong(request,""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_EMPLOYEE_ID]+"");

// konstanta untuk navigasi ke database
int recordToGet = 15;
int vectSize = 0;

// process get list LL (summary or detail depend on Empid value)
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
Vector vectListLL = new Vector(1,1);
if(iCommand != Command.NONE)
{
	// mencari parameter pencarian yang disimpan di session atau form		
	FrmSrcLeaveManagement frmSrcLeaveManagement = new FrmSrcLeaveManagement(request, srcLeaveManagement);
	frmSrcLeaveManagement.requestEntityObject(srcLeaveManagement);	
	
	if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.BACK)
	{
		 try
		 {		  
		 	if(session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_LL) != null)
			{
				srcLeaveManagement = (SrcLeaveManagement)session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_LL); 			
			}
		 }
		 catch(Exception e)
		 { 
			srcLeaveManagement = new SrcLeaveManagement();
		 }
		 
		 // jika command BACK (kembali dari form detail, maka ubah status command menjadi LIST
		 // supaya menampilkan list record employee - AL yang benar
		 if(iCommand == Command.BACK)
		 {
			iCommand = Command.LIST;		 	
		 }
	}	
	session.putValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_LL, srcLeaveManagement);		
	
	// mencari nilai limitStart
	vectSize = SessLeaveManagement.countSummaryLlStock(srcLeaveManagement);	
	if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST)
	{
		CtrlLLStockManagement ctrlLLStockManagement = new CtrlLLStockManagement(request);	
		start = ctrlLLStockManagement.actionList(iCommand, start, vectSize, recordToGet);
	}		

	// list record yang sesuai 
	vectListLL = SessLeaveManagement.listSummaryLlStock(srcLeaveManagement, start, recordToGet); 
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - LL Management</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.start.value="0";	
	document.frpresence.action="longleave.jsp";
	document.frpresence.submit();
}

function cmdEdit(oid){
	document.frpresence.command.value="<%=Command.LIST%>";
    document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_EMPLOYEE_ID]%>.value=oid;		
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdListFirst(){
	document.frpresence.command.value="<%=Command.FIRST%>";
	document.frpresence.action="longleave.jsp";
	document.frpresence.submit();
}

function cmdListPrev(){
	document.frpresence.command.value="<%=Command.PREV%>";
	document.frpresence.action="longleave.jsp";
	document.frpresence.submit();
}

function cmdListNext(){
	document.frpresence.command.value="<%=Command.NEXT%>";
	document.frpresence.action="longleave.jsp";
	document.frpresence.submit();
}

function cmdListLast(){
	document.frpresence.command.value="<%=Command.LAST%>";
	document.frpresence.action="longleave.jsp";  
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; LL (Long Leave) &gt; Summary<!-- #EndEditable --> </strong></font> 
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
                                    <input type="hidden" name="start" value="<%=start%>">
                                    <input type="hidden" name="start_summary" value="<%=start%>">   																		
                                    <input type="hidden" name="<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=empOid%>">																		
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
                                                        if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
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

                                                for (int i = 0; i < listDept.size(); i++) {
                                                    Department dept = (Department) listDept.get(i);
                                                    dept_key.add(dept.getDepartment());
                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                }
                                            %>
                                          <%= ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_DEPARTMENT],"formElemen",null,String.valueOf(srcLeaveManagement.getEmpDeptId()), dept_value, dept_key, "") %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Period</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="checkbox" name="<%=FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_PERIOD_CHECKED]%>" <%=(srcLeaveManagement.isPeriodChecked() ? "checked" : "")%> value="1">
                                            <i><font color="#FF0000">Select all 
                                            period</font></i></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%">&nbsp;<%=ControlDate.drawDateMY(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_PERIOD], srcLeaveManagement.getLeavePeriod()==null ? new Date() : srcLeaveManagement.getLeavePeriod(), "MMM yyyy", "formElemen", 0, installInterval)%></td>
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
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Get Long Leave"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">Get 
                                                  Long Leave</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  
									  
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">										
                                        <%
										//--- start untuk menampilkan garis pembatas antara 'search' dengan 'list result'
										if(vectListLL.size()>0)
										{
										%>
                                        <tr> 
                                          <td>
                                            <hr>
                                          </td>
                                        </tr>
                                        <%
										}
										//--- end untuk menampilkan garis pembatas antara 'search' dengan 'list result'
										%>
									  
										<%
										String drawList = "";
										if(vectListLL.size()>0)
										{
											drawList = drawListSummary(start, vectListLL);
										}
										
										String strDpData = drawList.trim();
										if(strDpData!=null && strDpData.length()>0)
										{
										%>
										<tr>
										  <td><%=strDpData%></td>
										</tr>
										<%
										}
										else if(iCommand == Command.LIST)
										{
										%>
                                        <tr>
                                          <td><%="<div class=\"msginfo\">&nbsp;&nbsp;No long leave data found ...</div>"%></td>
                                        </tr>										
										<%	
										}
										//--- end untuk menampilkan list data result, baik summary maupun detail per employee 
										%>
													
                                        <tr> 
                                          <td> 
                                            <% 
										    ControlLine ctrLine = new ControlLine();												
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.setLanguage(SESS_LANGUAGE);
											ctrLine.initDefault();		
											int listCommand = iCommand;											
											if(iCommand==Command.EDIT && empOid!=0)
											{
												listCommand = Command.LIST;
											}
											out.println(ctrLine.drawImageListLimit(listCommand, vectSize, start, recordToGet));
											%>
                                          </td>
                                        </tr>
                                      </table>
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
