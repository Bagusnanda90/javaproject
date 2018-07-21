
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.util.Command,
                  com.dimata.util.Formater,				  
                  com.dimata.gui.jsp.ControlList,
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.gui.jsp.ControlDatePopup,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.employee.PstEmployee,				  
                  com.dimata.harisma.entity.masterdata.LeavePeriod,				  
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.attendance.DpStockReporting,
		  com.dimata.harisma.entity.attendance.DpStockManagement,
                  com.dimata.harisma.entity.search.SrcDPExpiration,				  
                  com.dimata.harisma.form.search.FrmSrcDPExpiration,
                  com.dimata.harisma.session.attendance.SessLeaveManagement,	
                  com.dimata.harisma.session.attendance.SessDayOfPayment,
                  com.dimata.harisma.entity.attendance.*,
                  com.dimata.harisma.session.attendance.DPMontly"%>
<!-- package qdep -->

<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_DP_EXPIRED); %>

<%@ include file = "../../main/checkuser.jsp" %>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;
        
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
		
		ctrlist.addHeader(vectReportTitle[0],"5%", "0", "0");   
		ctrlist.addHeader(vectReportTitle[1],"10%", "0", "0");
		ctrlist.addHeader(vectReportTitle[2],"25%", "0", "0"); 
		ctrlist.addHeader(vectReportTitle[3],"5%", "0", "0");    
		ctrlist.addHeader(vectReportTitle[4],"10%", "0", "0");
		ctrlist.setLinkRow(-1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
                Vector lsPdf = new Vector(1,1);
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
	
		boolean resultAvailable = false;
		int recordNo = 0;		
                
		for (int i = 0; i < listDPReport.size(); i++) 
		{
			Vector list = (Vector)listDPReport.get(i);
                        
                        Employee emp = (Employee)list.get(0);
                        DpStockManagement dp = (DpStockManagement)list.get(1);
                    			
                        String empNum = emp.getEmployeeNum();
                        String fullName = emp.getFullName();
                        float dpQty = dp.getiDpQty();
                        Date expDate = dp.getDtExpiredDate();

                        recordNo++;

                        // listing for this jsp page
                        Vector rowx = new Vector(1,1);
                        rowx.add(String.valueOf(recordNo));
                        rowx.add(empNum);
                        rowx.add(fullName);
                        rowx.add(String.valueOf(dpQty));
                        if(dp.getiExceptionFlag() == PstDpStockManagement.EXC_STS_YES){
                            rowx.add(String.valueOf(dp.getDtExpiredDateExc()));
                        }else{
                            rowx.add(String.valueOf(expDate));
                        }
                        lstData.add(rowx);
                        lsPdf.add(rowx);
                        
                        resultAvailable = true;
		}		
					
		result.add(String.valueOf(DATA_PRINT));							
		result.add(ctrlist.draw());				
		result.add(lsPdf);		  														
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
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_DEPARTMENT]);
int expWeek = FRMQueryString.requestInt(request, FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_DP_EXP_PERIOD]);



String[] vectReportTitle = {
		"NO",
		"PAYROLL",
		"EMPLOYEE",
		"TOTAL DP",
		"EXPIRATION DATE"
};

String strListInJsp = "&nbsp";
SrcDPExpiration srcDPExpiration = new SrcDPExpiration();
Vector listDPReport = new Vector(1,1);

int dataStatus = DATA_NULL;
if(iCommand != Command.NONE)
{
	FrmSrcDPExpiration frmSrcDPExpiration = new FrmSrcDPExpiration(request, srcDPExpiration);
        frmSrcDPExpiration.requestEntityObject(srcDPExpiration);
        listDPReport = SessDayOfPayment.getExpiredDPPeriod(srcDPExpiration);
         
	Vector list = drawList(listDPReport, vectReportTitle);
        
	dataStatus = Integer.parseInt(String.valueOf(list.get(0)));
	strListInJsp = (String)list.get(1);	
	Vector vectToPdf = (Vector)list.get(2);  
        
 
        // vector data yg akan dikirim ke pdf
	Vector listToPdf = new Vector(1,1);
	listToPdf.add(vectReportTitle);
	listToPdf.add(String.valueOf(oidDepartment));
        listToPdf.add(String.valueOf(expWeek));
	listToPdf.add(vectToPdf);
  
	session.putValue("DP_EXP_REPORT",listToPdf);	
}

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - DP Expiration Report</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="dpexp_report.jsp";
	document.frpresence.submit();
}

function cmdPrint(){
	var linkPage = "<%=printroot%>.report.attendance.DPExpirationPdf";
	window.open(linkPage);
}

function getThn(){
<%
     out.println(ControlDatePopup.writeDateCaller("frpresence",FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_START_DATE]));
%>
}


function hideObjectForDate(){
      
}

function showObjectForDate(){
      
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
 <link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">

    <%=ControlDatePopup.writeTable(approot)%>
    
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report
                  &gt; Leave &gt; Expired DP<!-- #EndEditable --> </strong></font>
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
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Name</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="text" name="<%=FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_FULL_NAME]%>"  value="<%=srcDPExpiration.getEmpName()%>" class="elemenForm" size="40">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Payroll Number</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="text" name="<%=FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_EMP_NUMBER]%>"  value="<%=srcDPExpiration.getEmpNum()%>" class="elemenForm">
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
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
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
 
                                            
                                                    //Vector dept_value = new Vector(1,1);
                                                    //Vector dept_key = new Vector(1,1);

                                                    //dept_key.add("ALL DEPARTMENT");
                                                    //dept_value.add("0");

                                                    //Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                    String selectDept = String.valueOf(srcDPExpiration.getEmpDeptId());
                                                    for (int i = 0; i < listDept.size(); i++) 
                                                    {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                    }
                                                    out.println(ControlCombo.draw(FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_DEPARTMENT],"formElemen",null, selectDept, dept_value, dept_key, ""));
                                            %>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Start Date</td>
                                          <td width="3%">:</td>
                                          <td width="78%">                                              
                                               <%=ControlDatePopup.writeDate(FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_START_DATE],srcDPExpiration.getStartDate())%>
                                          </td>
                                        </tr>
                                        <!--<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Period</td>
                                          <td width="3%">:</td>
                                          <td width="78%">
                                                <%=ControlDate.drawDateMY(FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_PERIOD], srcDPExpiration.getLeavePeriod()==null ? new Date() : srcDPExpiration.getLeavePeriod(), "MMM yyyy", "formElemen", 0, installInterval)%> 
					  </td>
                                        </tr>-->
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Expired Period</td>
                                          <td width="3%">: </td>
                                          <td width="78%">                                    
                                            <% 
                                                    Vector value = new Vector(1,1);
                                                    Vector key = new Vector(1,1);

                                                    key.add("All");
                                                    value.add("0");
                                                    
                                                    key.add("1 Week");
                                                    value.add("1");
                                                    
                                                    key.add("2 Weeks");
                                                    value.add("2");
                                                    
                                                    key.add("3 Weeks");
                                                    value.add("3");
                                                    
                                                    key.add("4 Weeks");
                                                    value.add("4");
                                                  
                                                    String selected = String.valueOf(srcDPExpiration.getExpirationPeriod());
                                                    out.println(ControlCombo.draw(FrmSrcDPExpiration.fieldNames[FrmSrcDPExpiration.FRM_FIELD_DP_EXP_PERIOD],"formElemen", null, selected, value, key, ""));
                                            %>
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

