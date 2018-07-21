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

<%@ include file = "../../main/javainit.jsp" %>
<%
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- JSP Block -->
<%!
public String drawList(int offset, int iCommand, FrmOvt_Employee frmObject, Ovt_Employee objEntity, Vector objectClass, long idPeriod, String code_overtime, int ovtPosted){
	String result = "";
	
	//untuk mendapatkan jumlah index di overtime index
	String sWhereOver = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+" = '"+code_overtime+"'";
	Vector vListOver = PstOvt_Idx.list(0,0,sWhereOver,"");
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat("Pay Nr.","5%","2","0","center","left");
 	ctrlist.dataFormat("Name","20%","2","0","center","left");
	ctrlist.dataFormat("Position","10%","2","0","center","left");
	ctrlist.dataFormat("Work Date","10%","2","0","center","left");
	ctrlist.dataFormat("Schedule","5%","2","0","center","left");
	ctrlist.dataFormat("OverTime","47%","0","6","center","left");
	double index_tittle = 0.0;
	if(vListOver!=null && vListOver.size()>0){
	    for(int i =0; i<vListOver.size();i++){
			Ovt_Idx over_Idx = (Ovt_Idx)vListOver.get(i);
			String sJudul = "Idx "+String.valueOf(over_Idx.getOvt_idx());
			index_tittle = over_Idx.getOvt_idx();
			int k = 1;
			k = k + 1;
			int indexnya = 20 / k;
			String sIdx = String.valueOf(indexnya)+"%";
			ctrlist.dataFormat(sJudul,sIdx,"2","0","center","left");
		}
	}
	ctrlist.dataFormat("Total Idx","5%","2","0","center","left");

	ctrlist.dataFormat("Start Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("End Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("Duration","7%","0","0","center","left");
	ctrlist.dataFormat("OV-Form-Nr","7%","0","0","center","left");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	Vector rowx = new Vector(1,1);
	Date dtPeriodStart = new Date();
	Date dtPeriodEnd = new Date();
	//untuk menangkap StartDate di Period
	Period objPeriod = new Period();
	if(idPeriod!=0){
		try{
			objPeriod = PstPeriod.fetchExc(idPeriod);
		}catch(Exception e){;}
	dtPeriodStart = objPeriod.getStartDate();
	dtPeriodEnd = objPeriod.getEndDate();
	}
	
	int yearStart = dtPeriodStart.getYear() + 1900;
	int monthStart = dtPeriodStart.getMonth() + 1;
	int dateStart = dtPeriodStart.getDate();
	int monthEnd = dtPeriodEnd.getMonth() + 1;
	GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart-1, dateStart);
	int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	int startDatePeriod = Integer.parseInt(""+PstSystemProperty.getValueByName("START_DATE_PERIOD"));
	
	int l = 0;
	
	Vector list = new Vector();
	Ovt_Employee objOvtEmployeeX = new Ovt_Employee();
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			Vector temp = (Vector)objectClass.get(i);
			
			Employee employee = (Employee)temp.get(0);
			Position position = (Position)temp.get(1);
			Ovt_Employee objOvt_Employee = (Ovt_Employee)temp.get(2);
			double jumlah = 0.0;
			String frmCurrency = "#.###";
			if(iCommand==Command.LIST || iCommand==Command.SUBMIT)
			{
			
			    l = l + 1;
				String ovt_employee_Start = Formater.formatDate(objOvt_Employee.getOvt_Start(), "HH:mm");
				String ovt_employee_End = Formater.formatDate(objOvt_Employee.getOvt_End(), "HH:mm");
				
				//System.out.println("ini lho oidEmployeee yang mau diubah1111111111111111111111111111::::::::::::::"+objOvt_Employee.getOID());
				
				rowx.add(String.valueOf(l));
				rowx.add(objOvt_Employee.getEmployee_num());
				rowx.add(employee.getFullName());
				rowx.add(position.getPosition());
				rowx.add(Formater.formatDate(objOvt_Employee.getWorkDate(), "dd-MM-yyyy"));
				rowx.add(objOvt_Employee.getWork_schedule()!=null?objOvt_Employee.getWork_schedule():"-"); 
				rowx.add(Formater.formatDate(objOvt_Employee.getOvt_Start(), "dd-MM-yyyy"));
				rowx.add(ovt_employee_Start);
				rowx.add(Formater.formatDate(objOvt_Employee.getOvt_End(), "dd-MM-yyyy"));
				rowx.add(ovt_employee_End);
				rowx.add(String.valueOf(objOvt_Employee.getDuration()));
				rowx.add(objOvt_Employee.getOvt_doc_nr()!=null?objOvt_Employee.getOvt_doc_nr(): objOvt_Employee.getOvt_doc_nr());
				
				/***perhitungan untuk duration index*/
				if(vListOver!=null && vListOver.size()>0){
					String durationStr = String.valueOf(objOvt_Employee.getDuration());
					//double durationDbl = Double.parseDouble(durationStr);
					double total_idx = 0.0;
					double tot_Idx = 0.0;
					
					String durationStrHourX = durationStr.substring(0,durationStr.indexOf("."));
					String durationStrMinX = "";
					String index_pay = "";
					durationStrMinX = durationStr.substring(durationStr.indexOf("."),durationStr.length());
					String strMenit = "0"+durationStrMinX;
					double menit = Double.parseDouble(strMenit);
					
					double iDurationMinDbl = Double.parseDouble(String.valueOf(0)+durationStrMinX);
					String striDurationMin = String.valueOf(iDurationMinDbl);
					double iDurationDbl = iDurationMinDbl * 100;
					String iDurationDblStrX = String.valueOf(iDurationDbl);
					String iDurationDblStr = iDurationDblStrX.substring(0,iDurationDblStrX.indexOf("."));
					
					long iDurationMin = Long.parseLong(iDurationDblStr);
					long iDurationHour = Long.parseLong(durationStrHourX);
					String durationStrX = String.valueOf(iDurationHour);
					double durationDbl = Double.parseDouble(durationStrX);
					
					String striDurationMinX = "";
					if(iDurationMin < 10){
						striDurationMinX = "0"+iDurationMin;
					}else{
						striDurationMinX = String.valueOf(iDurationMin);
					}
					
					for(int r =0; r<vListOver.size();r++){
						    Ovt_Idx over_Idx = (Ovt_Idx)vListOver.get(r);
							double index_ov = over_Idx.getOvt_idx();
							double hourTo = over_Idx.getHour_to();
							
							String strHourTo = String.valueOf(hourTo);
							String hourToX = strHourTo.substring(0,strHourTo.indexOf("."));
							double dblHourTo = Double.parseDouble(hourToX);
							//System.out.println("dblHourTo:::::::::::::::::::::::::::::::::::::::::"+dblHourTo);
							
							double hourFrom = over_Idx.getHour_from();
							double pay_index = 0.0;
							double allDuration = 0.0;
							
							if((durationDbl>0 && menit>0)  || (durationDbl>0 && menit==0)) 
									{
									    /*System.out.println("menit::::::::::::::::::::::::::::::::::::"+menit);
										System.out.println("iDurationHour1111::::::::::::::::::::::::::::::::::::"+iDurationHour);
										System.out.println("durationDbl::::::::::::::::::::::::::::::::::::"+durationDbl);*/
										if((iDurationHour>=hourFrom) && (iDurationHour<hourTo))
										{
											allDuration = durationDbl + menit;
											index_pay = String.valueOf(allDuration);
											pay_index = Double.parseDouble(index_pay);
											total_idx = pay_index * index_ov;
											jumlah = jumlah + total_idx;
											//durationDbl = durationDbl - iDourationHoursX;
											durationDbl = durationDbl - allDuration;
											menit = 0;
											
											if(pay_index > 0)
											    rowx.add(index_pay);
										    else
											    rowx.add("0.0");
										}
										else
										{
											if(iDurationHour<hourTo)
											{
												allDuration = durationDbl + menit;
												index_pay = String.valueOf(allDuration);
												menit = 0;
												
											}
											else if(iDurationHour==0)
											{
												index_pay = "0."+menit;
												//System.out.println("index_pay::::::::::::::::::::::::::::::::::::"+index_pay);
											}
											else{
												index_pay = String.valueOf(hourTo);
												//System.out.println("index_pay::::::::::::::::::::::::::::::::::::"+index_pay);
											}
											//System.out.println("durationDbl1111111111111111111111111111::::::::::::::::::::::::::::::::::::"+iDurationHour);
											
											//durationDbl = durationDbl - dblHourTo;
										
											durationDbl = durationDbl - hourTo;
											//System.out.println("durationDbl11111111111111111111111111112222::::::::::::::::::::::::::::::::::::"+durationDbl);
											String strDuration = String.valueOf(durationDbl);
											long durationLong = Long.parseLong(strDuration.substring(0,strDuration.indexOf(".")));
											iDurationHour = durationLong;
											pay_index = Double.parseDouble(index_pay);
											total_idx = pay_index * index_ov;
											jumlah = jumlah + total_idx;
											if(pay_index > 0)
											    rowx.add(index_pay);
											else
												rowx.add("0.0");
										}
									}
									else
									{
										//untuk mengecek setelah pengurangan Hournya = 0, jika telah habis dikurangi maka melakukan pengecekan terhadap iDurationHournya  
										if(menit>0)
										{
											  index_pay = String.valueOf(menit);
											  pay_index = Double.parseDouble(index_pay);
											  total_idx = pay_index * index_ov;
											  jumlah = jumlah + total_idx;
											  menit = 0;
										}
										else
										{
											index_pay = String.valueOf(0.0);
											pay_index = Double.parseDouble(index_pay);
											total_idx = pay_index * index_ov;
											jumlah = jumlah + total_idx;
										}
										//rowx.add(index_pay);
										if(pay_index > 0)
											    rowx.add(index_pay);
										else
											    rowx.add("0.0");
									}
							}
				}//tutup perhitungan

					rowx.add(Formater.formatNumber(jumlah,frmCurrency));
					/*
					untuk mendapatkan id payslip yang akan di update ke tabel pay_ovt_employee
						1. select query dari pay_slip berdasarkan periode dan id_employee nya
						2. dapat id_payslipnya
						3. baru edit data id_payslip di tabel pay_ovt_employee
						
						select * from pay_slip where period_id=504404343502872505 and employee_id = 56
					*/
					//Dapatkan id_employee
					String strWhereIdEmployee = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" = "+objOvt_Employee.getEmployee_num();
					Vector vListEmployee = PstEmployee.list(0,0,strWhereIdEmployee,"");
					Employee objEmployee = new Employee();
					if(vListEmployee!=null && vListEmployee.size()>0)
					{
						objEmployee = (Employee)vListEmployee.get(0);
					}
					
					//set untuk mencari pay slipnya
					String whereStr = PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+" = "+objOvt_Employee.getPeriodId()+
									  " AND "+PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]+" = "+objEmployee.getOID();
					Vector vListPaySlip = PstPaySlip.list(0,0,whereStr,"");
					PaySlip paySlip = new PaySlip();
					if(vListPaySlip!=null && vListPaySlip.size()>0)
					{
						paySlip = (PaySlip)vListPaySlip.get(0);
					}
										
					if(iCommand==Command.SUBMIT)
					{
					    int statusPost = 3;
						//System.out.println("ini lho oidEmployeee yang mau diubah::::::::::::::"+paySlip.getOID());
						if(ovtPosted!=0)
						{
							try{
								objOvtEmployeeX.setOID(objOvt_Employee.getOID());
								objOvtEmployeeX.setPeriodId(objOvt_Employee.getPeriodId());
								objOvtEmployeeX.setEmployee_num(objOvt_Employee.getEmployee_num());
								objOvtEmployeeX.setWork_schedule(objOvt_Employee.getWork_schedule());
								objOvtEmployeeX.setWorkDate(objOvt_Employee.getWorkDate());
								objOvtEmployeeX.setOvt_Start(objOvt_Employee.getOvt_Start());
								objOvtEmployeeX.setOvt_End(objOvt_Employee.getOvt_End());
								objOvtEmployeeX.setDuration(objOvt_Employee.getDuration());
								objOvtEmployeeX.setOvt_doc_nr(objOvt_Employee.getOvt_doc_nr());
								objOvtEmployeeX.setStatus(statusPost);
								objOvtEmployeeX.setPay_slip_id(paySlip.getOID());
								objOvtEmployeeX.setOvt_code(objOvt_Employee.getOvt_code());
								objOvtEmployeeX.setTot_Idx(jumlah);
								
								PstOvt_Employee.updateExc(objOvtEmployeeX);
							}catch(Exception e){;}
						}
						else{
							System.out.println("Gagal terupdate");
						}
					}	
				}
				lstData.add(rowx);
				if(i==objectClass.size()-1)
				{
					break;
				}else{
					rowx = new Vector();
				}
			}
	    result = ctrlist.drawMeList();
    }//tutupnya jika objectclassnya !=0
	else{
		 result = "<i>Belum ada data dalam sistem ...</i>";
	}
	return result;
	
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidOvt_Employee = FRMQueryString.requestLong(request, "hidden_ovt_Employee_id");
long oidPeriod = FRMQueryString.requestLong(request,"period");
String code_overtime = FRMQueryString.requestString(request,"ovt_type");
int ovtPosted = FRMQueryString.requestInt(request,"ovtPosted");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;


CtrlOvt_Employee ctrlOvt_Employee = new CtrlOvt_Employee(request);
Vector listOvt_EmployeePost = new Vector(1,1);

/*switch statement */
iErrCode = ctrlOvt_Employee.action(iCommand , oidOvt_Employee, request);
/* end switch*/
FrmOvt_Employee frmOvt_Employee = ctrlOvt_Employee.getForm();
Ovt_Employee ovt_Employee = ctrlOvt_Employee.getOvt_Employee();
msgString =  ctrlOvt_Employee.getMessage();

if(iCommand == Command.LIST || iCommand==Command.SUBMIT)
{
	//System.out.println("ovtPosted1111111111111111111111111::::::::::::::::::::"+ovtPosted);
	listOvt_EmployeePost = SessEmployee.listPostingOvertime(oidPeriod, code_overtime);
}

System.out.println("ovtPosted::::::::::::::::::::"+ovtPosted);
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
	document.frm_ovt_posting.command.value="<%=Command.LIST%>";
	document.frm_ovt_posting.action="ov-posting.jsp";
	document.frm_ovt_posting.submit();
}

function cmdPosting(){
    var x = confirm(" Apakah anda mau melakukan posting overtime?");
	if(x){
		document.frm_ovt_posting.command.value="<%=Command.SUBMIT%>";
		document.frm_ovt_posting.action="ov-posting.jsp";
		document.frm_ovt_posting.submit();
	}
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
                                    <form name="frm_ovt_posting" method="post" action="">
									 <input type="hidden" name="command" value="">
										<input type="hidden" name="start" value="<%=start%>">
										<input type="hidden" name="hidden_ovt_Employee_id" value="<%=oidOvt_Employee%>">
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
                                                <td width="14%" height="20">Periode 
                                                  : </td>
                                                <td width="11%">
													<%
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
                                                <td width="13%">Overtime Code : 
                                                </td>
                                                <td width="62%">
												 <% 
														Vector ovt_type_value = new Vector(1,1);
														Vector ovt_type_key = new Vector(1,1); 
														Vector listOvt_Type = PstOvt_Type.list(0, 0, "", "");
                                                                                                                ovt_type_key.add("-all-");
														ovt_type_value.add("");
														for (int i = 0; i < listOvt_Type.size(); i++) {
																Ovt_Type ovt = (Ovt_Type)listOvt_Type.get(i);
																ovt_type_key.add(ovt.getOvt_Type_Code());
																ovt_type_value.add(ovt.getOvt_Type_Code());
														}
														out.println(ControlCombo.draw("ovt_type",null,""+code_overtime,ovt_type_value,ovt_type_key));
												%>
												</td>
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
                                                <td width="97%" height="20"> <a href="javascript:cmdSearch()">Search 
                                                  for Employee</a> </td>
                                              </tr>
                                              <tr> 
                                                <td height="20" colspan="2"> </td>
                                              </tr>
											  <%if(iCommand==Command.LIST || iCommand==Command.SUBMIT){
											    
											   %>
											   <tr>
											   <td height="20" colspan="2"><%=drawList(start, iCommand,frmOvt_Employee, ovt_Employee, listOvt_EmployeePost, oidPeriod, code_overtime, ovtPosted)%>
												</td>
                                              </tr>
											  <%
											  
											  }%>
                                            </table> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%"></td>
                                          <td width="23%" nowrap> <input type="checkbox" name="ovtPosted" value="1" checked>
                                            Recalculate Index lembur on Posting 
                                          </td>
                                          <td width="2%">&nbsp;</td>
                                          <td width="8%">&nbsp;</td>
                                          <td width="63%"><a href="javascript:cmdPosting()" class="command">DO POSTING 
                                            OVERTIME</a></td>
                                          <td width="4%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle" width="0%">&nbsp;</td>
										  <%
										  if(iCommand==Command.SUBMIT)
										  {
										  %>
                                          <td class="listtitle" colspan="5" >Telah Berhasil Di Posting</td>
										  <%
										  }
										  %>
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
