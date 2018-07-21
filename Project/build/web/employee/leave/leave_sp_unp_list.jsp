
<%-- 
    Document   : leave_sp_unp_list
    Created on : Mar 11, 2010, 4:34:14 PM
    Author     : Tu Roy
--%>

<%@ page language="java" %>
<!-- package java -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

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
                  com.dimata.harisma.session.attendance.AnnualLeaveMontly,
                  com.dimata.harisma.entity.leave.*,
                  com.dimata.harisma.session.leave.*,
                  com.dimata.harisma.entity.leave.*,
                  com.dimata.harisma.session.leave.SessLeaveApp,com.dimata.harisma.session.leave.RepItemLeaveAndDp"%>
<!-- package qdep -->

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_SPECIAL_UNPAID_LEAVE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%! 
public String drawList(Vector listSp) {
    
    String result = "";
    
    if((listSp != null && listSp.size() > 0 )){
        
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");

            ctrlist.addHeader("<center>NO</center>","2%", "2", "0");
            ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.DEPARTMENT)+"</center>","20%", "2", "0");
            ctrlist.addHeader("<center>PayRoll</center>","8%", "2", "0");
            ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EMPLOYEE)+"</center>","30%", "2", "0");
            ctrlist.addHeader("<center>Special Leave</center>","20%", "0", "2");	
            ctrlist.addHeader("<center>Unpaid Leave</center>","20%", "0", "2");
            
            ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","10%", "0", "0");
            ctrlist.addHeader("<center>2b-Taken</center>","10%", "0", "0");
            
            ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","10%", "0", "0");
            ctrlist.addHeader("<center>2b-Taken</center>","10%", "0", "0");
            
            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
        
            int no = 1;
            int totalSpTkn = 0;
            int totalSpToBeTkn = 0;
            int totalUnpTkn = 0;
            int totalUnpToBeTkn = 0;
            
            long idxDept = 0;
            
            for(int idx = 0 ; idx< listSp.size() ; idx++)
            {
            
                ListSp listSP = new ListSp();
                
                listSP = (ListSp)listSp.get(idx);
                
                Vector rowx = new Vector(1,1);
                Department department = new Department();
                
                try{
                    department = PstDepartment.fetchExc(listSP.getDepartmentId());
                }catch(Exception e){
                    System.out.println("EXCEPTION "+e.toString());
                }
                
                rowx.add(""+no);
                
                if(idxDept == 0){
                    
                    rowx.add(""+department.getDepartment());
                    
                }else{
                    if(idxDept != department.getOID()){
                        rowx.add(""+department.getDepartment());
                    }else{
                        rowx.add("");
                    }
                }
                
                
                rowx.add(""+listSP.getEmployeeNum());
                rowx.add(""+listSP.getFullName());
                rowx.add(""+listSP.getTakenSp());
                rowx.add(""+listSP.getToBeTakenSp());
                rowx.add(""+listSP.getTakenUnp());
                rowx.add(""+listSP.getTobeTakenUnp());
                
                totalSpTkn = totalSpTkn + listSP.getTakenSp();
                totalSpToBeTkn = totalSpToBeTkn + listSP.getToBeTakenSp();
                totalUnpTkn = totalUnpTkn + listSP.getTakenUnp();
                totalUnpToBeTkn = totalUnpToBeTkn + listSP.getTobeTakenUnp();
            
                idxDept = department.getOID();
                lstData.add(rowx);
                lstLinkData.add("0");
                no++;            
            }
        
            Vector rowTotal = new Vector(1,1);
            
            rowTotal.add("TOTAL : ");
            rowTotal.add("");
            rowTotal.add("");
            rowTotal.add("");
            rowTotal.add(""+totalSpTkn);
            rowTotal.add(""+totalSpToBeTkn);
            rowTotal.add(""+totalUnpTkn);
            rowTotal.add(""+totalUnpToBeTkn);
            
            lstData.add(rowTotal);
            lstLinkData.add("0");

            result = ctrlist.drawList();
        
   }else{
       
        result += "<div class=\"msginfo\">&nbsp;&nbsp;Special and Unpaid Leave Stock Data Found found ...</div>";																
        
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
long oidDepartment = FRMQueryString.requestLong(request,"department");

Vector listSp = new Vector(1,1);
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();   
FrmSrcLeaveManagement frmSrcLeaveManagement = new FrmSrcLeaveManagement(request, srcLeaveManagement);

AnnualLeaveMontly alLeaveMon  = new AnnualLeaveMontly();

String strListInJsp = "&nbsp";
if(iCommand != Command.NONE)
{

        frmSrcLeaveManagement.requestEntityObject(srcLeaveManagement);        
        listSp = SessLeaveApplication.listSchedule(srcLeaveManagement);
        
	try{
		session.removeValue("DETAIL_SPECIAL");
	}
	catch(Exception e){
		System.out.println("Exc when remove from session(\"DETAIL_LEAVE_DP_REPORT\") : " + e.toString());	
	}
	

	Vector listToSession = new Vector(1,1);	
	listToSession.add(listSp);
	
	try
	{
		session.putValue("DETAIL_LEAVE_SP_REPORT",listToSession);
	}
	catch(Exception e)
	{
		System.out.println("Exc when put to session(\"DETAIL_LEAVE_DP_REPORT\") : " + e.toString());		
	}
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Leave Special and Unpaid</title>
<script language="JavaScript">

function cmdView()
{
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="leave_sp_unp_list.jsp";
	document.frpresence.submit();
}

function cmdPrint()
{
	var linkPage = "<%=printroot%>.report.attendance.AnnualLeaveMonthlyPdf";
	window.open(linkPage);
}

function cmdPrintXls()
{
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveSpXls";
    window.open(pathUrl);
}

function getTimeStart(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveManagement.FRM_NAME_SRCDAYOFPAYMENT,FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_START_DATE])%>
}

function getTimeEnd(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveManagement.FRM_NAME_SRCDAYOFPAYMENT,FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_END_DATE])%>
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
                  &gt; Leave &gt; Detail Leave Report Special and Unpaid Leave<!-- #EndEditable --> </strong></font>
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
                                          <td nowrap width="18%">Periode</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <% 						
                                                                                      
                                            String selectValuePeriod = ""+srcLeaveManagement.getPeriodId();
                                            
                                            Vector period_value = new Vector(1, 1);
                                            Vector period_key = new Vector(1, 1);
            
                                            period_value.add("0");
                                            period_key.add("select...");
                                            Vector listPeriod = new Vector(1, 1);
           
                                            listPeriod = PstPeriod.list(0, 0, "", PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" DESC");
                                            for (int i = 0; i < listPeriod.size(); i++) {
                                                Period period = (Period) listPeriod.get(i);
                                                period_key.add(period.getPeriod());
                                                period_value.add(String.valueOf(period.getOID()));
                                            }
                                           %>
                                           <%= ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_PERIOD], null, selectValuePeriod, period_value, period_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
					   <input type="radio" name="<%=FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_TIME]%>" value="0" checked> 						
                                            
                                           <%=ControlDatePopup.writeDate(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_START_DATE],srcLeaveManagement.getStartDate()==null ? new Date() : srcLeaveManagement.getStartDate(), "getTimeStart()")%> To
                                           <%=ControlDatePopup.writeDate(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_END_DATE],srcLeaveManagement.getEndDate()==null ? new Date() : srcLeaveManagement.getEndDate(), "getTimeEnd()")%>                                          
                                          
                                            
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
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Report</a></td>
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
                                          <td><%=drawList(listSp)%></td>
                                        </tr>
                                        <%
                                        if(listSp != null && listSp.size()>0 && privPrint)
					{
					%>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr>
                                                <td width="24"><a href="javascript:cmdPrintXls()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdPrintXls()">Print Report Xls</a></td>
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