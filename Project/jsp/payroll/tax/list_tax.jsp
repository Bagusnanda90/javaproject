<%@ page language="java" %>
<%@ page import = "java.util.*" %>

<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- JSP Block -->
<%!
public String drawList(Vector objectClass, String levelCode){
	String result = "";
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","5%");
	ctrlist.addHeader("Payroll Nr.","10%");
	ctrlist.addHeader("Full Name","20%");
	ctrlist.addHeader("Position","20%");
	ctrlist.addHeader("Salary Level","20%");
	
	//header dinamis(benefit)
	String whereB = "slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.YES_TAKE+
				   " AND slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+levelCode+"'"+
				   " AND pay."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+PstPayComponent.TYPE_BENEFIT+
				   " AND pay."+PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]+" = "+PstPayComponent.NO_TAX;
	// header dinamis ( deduction )
	String whereD = "slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.YES_TAKE+
				   " AND slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+levelCode+"'"+
				   " AND pay."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+PstPayComponent.TYPE_DEDUCTION+
				   " AND pay."+PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]+" = "+PstPayComponent.NO_TAX;
				   
	String order = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME];			   
    Vector headCompB = PstPayComponent.getManualComponent(whereB,order);
		if(headCompB !=null && headCompB.size() >0){
			for(int b=0;b<headCompB.size();b++){
				PayComponent payComp = (PayComponent)headCompB.get(b);
				ctrlist.addHeader(""+payComp.getCompName(),"5%","0","0");
			}
		}
	ctrlist.addHeader("Total Benefit","5%");
	//header deduction
	Vector headCompD = PstPayComponent.getManualComponent(whereD,order);
	if(headCompD !=null && headCompD.size() >0){
		for(int d=0;d<headCompD.size();d++){
			PayComponent payComp = (PayComponent)headCompD.get(d);
			ctrlist.addHeader(""+payComp.getCompName(),"5%","0","0");
		}
	}
	ctrlist.addHeader("Total Deduction","5%");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	String frmCurrency = "#,###";
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			Vector temp = (Vector)objectClass.get(i);
			Employee employee = (Employee)temp.get(0);
			Marital marital = (Marital)temp.get(1);
			Position position = (Position)temp.get(2);
			PayEmpLevel payLevel = (PayEmpLevel)temp.get(3);
			PaySlip paySlip = (PaySlip)temp.get(4);
			SalaryLevel sal = (SalaryLevel)temp.get(5);
			
			double totBenefit=0;
			double totDeduction = 0;
			
			rowx = new Vector();
			rowx.add(String.valueOf(1 + i));
			rowx.add(String.valueOf(employee.getEmployeeNum()));
			rowx.add(String.valueOf(employee.getFullName()));
			rowx.add(position.getPosition());
			rowx.add(sal.getLevelName());
			//ambil nilai dari pay slip id dengan comp_code tertentu (benefit)
			 if(headCompB !=null && headCompB.size() >0){
				for(int b=0;b<headCompB.size();b++){
					PayComponent payComp = (PayComponent)headCompB.get(b);
					double compValue = PstPaySlipComp.getReportCompValue(paySlip.getOID(),payComp.getCompCode());
					rowx.add(""+Formater.formatNumber(compValue, frmCurrency));
					totBenefit = totBenefit + compValue;
				}
			}
			rowx.add("<b>"+Formater.formatNumber(totBenefit, frmCurrency)+"</b>");
			 //ambil nilai dari pay slip id dengan comp_code tertentu (deduction)
			  if(headCompD !=null && headCompD.size() >0){
				for(int d=0;d<headCompD.size();d++){
					PayComponent payComp = (PayComponent)headCompD.get(d);
					double compValue = PstPaySlipComp.getReportCompValue(paySlip.getOID(),payComp.getCompCode());
					rowx.add(""+Formater.formatNumber(compValue, frmCurrency));
					totDeduction = totDeduction + compValue;
				}
			}
			rowx.add("<b>"+Formater.formatNumber(totDeduction,frmCurrency)+"<b>");
			
			//PstPaySlipComp.hitungPph(totBenefit,totDeduction,levelCode, employee); 
			
			lstData.add(rowx);
		}
		
		result = ctrlist.draw();
	}else{
			result = "<i>Belum ada data dalam sistem ...</i>";
		}
	return result;
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
String levelCode = FRMQueryString.requestString(request,"salLevel");
long oidPeriod = FRMQueryString.requestLong(request,"period");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

//untuk menampilkan semua employee yang akan menerima slip 

Vector vListSlip = SessTax.listEmployeePaySlip(levelCode,oidPeriod,0,0);

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function cmdSearch(){
	document.frm_list_tax.command.value="<%=Command.LIST%>";
	document.frm_list_tax.action="list_tax.jsp";
	document.frm_list_tax.submit();
}



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
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Input 
                  Overtime<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frm_list_tax" method="post" action="">
									 <input type="hidden" name="command" value="">
										<input type="hidden" name="start" value="<%=start%>">
										 <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td height="13" width="0%"></td>
                                          <td height="13" colspan="5"></td>
                                        </tr>
                                        <tr> 
                                          <td height="25" width="0%"></td>
                                          <td height="25" colspan="5"> 
                                            <table width="100%" border="0">
                                              <tr> 
                                                <td height="20" colspan="4">Cari 
                                                  List Daftar Gaji Karyawan</td>
                                              </tr>
                                              <tr> 
                                                <td width="14%" height="20">Salary 
                                                  Level :</td>
                                                <td width="11%"> <%
														  Vector listSalLevel = PstSalaryLevel.list(0, 0, "", "");										  
														  Vector salValue = new Vector(1,1);
														  Vector salKey = new Vector(1,1);
														 
														  for(int d=0;d<listSalLevel.size();d++)
														  {
															SalaryLevel salaryLevel = (SalaryLevel)listSalLevel.get(d);
															salValue.add(""+salaryLevel.getLevelCode());
															salKey.add(""+salaryLevel.getLevelName());										  
														  }
														  out.println(ControlCombo.draw("salLevel",null,""+levelCode,salValue,salKey));
												   %> 
                                                </td>
                                                <td width="13%">&nbsp;</td>
                                                <td width="62%">&nbsp; </td>
                                              </tr>
                                              <tr> 
                                                <td height="20">&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td height="20">Periode : </td>
                                                <td><%
														  Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");										  
														  Vector periodValue = new Vector(1,1);
														  Vector periodKey = new Vector(1,1);
														 
														  for(int d=0;d<listPeriod.size();d++)
														  {
															Period period = (Period)listPeriod.get(d);
															periodValue.add(""+period.getOID());
															periodKey.add(""+period.getPeriod());										  
														  }
														  out.println(ControlCombo.draw("period",null,""+oidPeriod,periodValue,periodKey));
												   %> 
                                                </td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="2" width="0%"></td>
                                          <td height="2" colspan="5"></td>
                                        </tr>
                                        <tr> 
                                          <td width="0%" height="24"></td>
                                          <td valign="top" colspan="5" height="24"> 
                                            <table width="100%" border="0">
                                              <tr> 
                                                <td width="3%"> <a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a> 
                                                </td>
                                                <td width="97%" height="20"> <a href="javascript:cmdSearch()">Search list Employee
                                                  </a> </td>
                                              </tr>
                                              <tr> 
                                                <td height="20" colspan="2"> </td>
                                              </tr>
											  <%if(iCommand==Command.LIST){
											     
											   %>
											   <tr>
											   <td height="20" colspan="2"><%=drawList(vListSlip,levelCode)%>
												</td>
                                              </tr>
											  <%
											   
											  }%>
                                            </table> 
                                          </td>
                                        </tr>
                                        
                                        <tr> 
                                          <td class="listtitle" width="0%">&nbsp;</td>
                                          <td class="listtitle" colspan="5">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="5">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="5">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="5">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="5">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="5">&nbsp;</td>
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
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
