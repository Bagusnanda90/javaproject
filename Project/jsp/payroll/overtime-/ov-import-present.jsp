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
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<%
privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- JSP Block -->

<%!
public String drawList(int offset, int iCommand, FrmOvt_Employee frmObject, Ovt_Employee objEntity, Vector objectClass, long idOvt_Employee, long idPeriod, String code_overtime, Vector vListImport_Employee, Hashtable has){
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
	ctrlist.dataFormat("OverTime","42%","0","6","center","left");
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
	ctrlist.dataFormat("Transfer","5%","2","0","center","left");

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
	int index = -1;
	
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
	
	int nomor = 0; 
	int l = 0;
	Ovt_Employee objOvt_Employee = new Ovt_Employee();
	Vector list = new Vector();
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
		    
			Vector temp = (Vector)objectClass.get(i);
			
			Employee employee = (Employee)temp.get(0);
			Position position = (Position)temp.get(1);
			Vector vDate = (Vector)temp.get(2);
			Vector vScdTimeOut = (Vector)temp.get(3);
			Vector vTimeOut = (Vector)temp.get(4);
			//Vector vDate = (Vector)temp.get(4);
						
			int durasi = nDayOfMonthStart;
			int j = startDatePeriod;
			int noAdd = 0;
			int startMonthPeriod = 0;
			
			//looping untuk mendapatkan simbol
			
			int tanggal = 0;
			for(int k =0; k<durasi; k++)
				{
					double jumlah = 0.0;
					long employee_id = employee.getOID();
					String dateTime = (String)vDate.get(k);
					Date timeOut = (Date) vScdTimeOut.get(k);
					Date actualTimeOut = (Date)vTimeOut.get(k);
					
					String timeOut1 = Formater.formatTimeLocale(timeOut, "HH:mm");
					String actualOut1 = Formater.formatTimeLocale(actualTimeOut, "HH:mm");
					long iDuration = 0;
					long iDurationHour = 0;
					long iDurationMin = 0;
					String strDurationHour = "";
					/* -------------- difference beetwen time out and  actual time out -------------- */
					if(timeOut != null && actualTimeOut != null){
						timeOut.setSeconds(0);					
						iDuration = actualTimeOut.getTime()/60000 - timeOut.getTime()/60000 ;
						iDurationHour = (iDuration - (iDuration % 60)) / 60;
						iDurationMin = iDuration % 60;
						strDurationHour = iDurationHour + "h, ";
					}
					
					if(j==nDayOfMonthStart){
						tanggal = j;
						j = 1;
					}else{
						tanggal = j;
						j = j + 1;
					}
				
					if((tanggal >= startDatePeriod) && (tanggal<=nDayOfMonthStart)){
						startMonthPeriod = monthStart;
					}
					else{
						startMonthPeriod = monthEnd;
					}
					Vector listEmployeeSchedule = PstOvt_Employee.listSchedule(j, employee_id, idPeriod);
					ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
					if(listEmployeeSchedule!=null && listEmployeeSchedule.size()>0)
					{
						Vector tempX = (Vector)listEmployeeSchedule.get(0);
						scheduleSymbol = (ScheduleSymbol)tempX.get(1);
					}
					String DatePeriodX = yearStart+"-"+startMonthPeriod+"-0"+j;
					
					rowx = new Vector();
					//untuk tanggal di work date 
					String work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-0"+String.valueOf(tanggal);
					int hour = 0;
					String overtime_nr = "";
					String strChecked = "";
					String employee_num = "";
					String index_pay = "";
					
					if(iDurationHour > 0)
					{
						
						//untuk account nomor
						l = l + 1;
						if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK))
						{
							rowx.add(String.valueOf(l));
							rowx.add(employee.getEmployeeNum()+"<input type=\"hidden\" name=\"employee_num_"+k+"\" value=\""+employee.getEmployeeNum()+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(employee.getFullName()+"<input type=\"hidden\" name=\"employee_name_"+k+"\" value=\""+employee.getFullName()+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(position.getPosition());
							rowx.add(work_date);
							rowx.add(scheduleSymbol.getSymbol());
							rowx.add(dateTime);
							rowx.add(Formater.formatTimeLocale(timeOut, "HH:mm"));
							rowx.add(dateTime);
							rowx.add(Formater.formatTimeLocale(actualTimeOut, "HH:mm"));
							rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"\">"+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin));
							
							rowx.add("<input type=\"text\" name=\"overtime_nr\" value=\""+overtime_nr+"\" class=\"formElemen\">");
							if(vListOver!=null && vListOver.size()>0){
								for(int x=0; x<vListOver.size();x++){
									rowx.add("");
								}
							}
							rowx.add("<input type=\"checkbox\" name=\"checkbox\" value=\"1\">");
					   }
					   else
					   {
					        rowx.add(String.valueOf(l));
							rowx.add(employee.getEmployeeNum()+"<input type=\"hidden\" name=\"employee_num_"+i+""+k+"\" value=\""+employee.getEmployeeNum()+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(employee.getFullName()+"<input type=\"hidden\" name=\"employee_name_"+i+""+k+"\" value=\""+employee.getFullName()+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(position.getPosition()+"<input type=\"hidden\" name=\"potition_name_"+i+""+k+"\" value=\""+position.getPosition()+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(work_date+"<input type=\"hidden\" name=\"work_date_"+i+""+k+"\" value=\""+work_date+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(scheduleSymbol.getSymbol()+"<input type=\"hidden\" name=\"simbol_"+i+""+k+"\" value=\""+scheduleSymbol.getSymbol()+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(dateTime+"<input type=\"hidden\" name=\"dateTime_"+i+""+k+"\" value=\""+dateTime+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(timeOut1+"<input type=\"hidden\" name=\"timeOut_"+i+""+k+"\" value=\""+timeOut1+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(dateTime+"<input type=\"hidden\" name=\"dateTime_1_"+i+""+k+"\" value=\""+dateTime+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add(actualOut1+"<input type=\"hidden\" name=\"actualTime_"+i+""+k+"\" value=\""+actualOut1+"\" size=\"25\" class=\"elemenForm\">");
							rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"\">"+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"<input type=\"hidden\" name=\"iDurationHour_"+i+""+k+"\" value=\""+iDurationHour+"\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"iDurationMin_"+i+""+k+"\" value=\""+iDurationMin+"\" size=\"25\" class=\"elemenForm\">");
							
							/**
							* for get overtime nr from hastable
							*/	
							String strNmr = (String)has.get(employee.getEmployeeNum()+""+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin));
							if(strNmr != null){
								overtime_nr = strNmr;
								strChecked = "checked";
							}
							rowx.add("<input type=\"text\" name=\"overtime_nr_"+i+""+k+"\" value=\""+overtime_nr+"\" class=\"formElemen\">");
							if(vListOver!=null && vListOver.size()>0){
							String durationStr = String.valueOf(iDurationHour);
							double durationDbl = Double.parseDouble(durationStr);
							double total_idx = 0.0;
							double tot_Idx = 0.0;
							for(int x =0; x<vListOver.size();x++)
							{
									Ovt_Idx over_Idx = (Ovt_Idx)vListOver.get(x);
									double index_ov = over_Idx.getOvt_idx();
									double hourTo = over_Idx.getHour_to();
									double hourFrom = over_Idx.getHour_from();
									double pay_index = 0.0;
									
									if((durationDbl>0 && iDurationMin>0)  || (durationDbl>0 && iDurationMin==0))
									{
										
										if((iDurationHour>=hourFrom) && (iDurationHour<hourTo))
										{
											index_pay = String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin);
											pay_index = Double.parseDouble(index_pay);
											total_idx = pay_index * index_ov;
											jumlah = jumlah + total_idx;
											System.out.println("total_idx:::::::::::::::::;"+total_idx);
											durationDbl = durationDbl - iDurationHour;
											//Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
											iDurationMin = 0;
											rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
										}
										else
										{
											if(iDurationHour<hourTo)
											{
												index_pay = iDurationHour+"."+iDurationMin;
												//Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
												iDurationMin = 0;
											}
											else if(iDurationHour==0)
											{
												index_pay = "0."+iDurationMin;
											}
											else{
												index_pay = String.valueOf(hourTo);
											}
											durationDbl = durationDbl - hourTo;
											String strDuration = String.valueOf(durationDbl);
											long durationLong = Long.parseLong(strDuration.substring(0,strDuration.indexOf(".")));
											iDurationHour = durationLong;
											pay_index = Double.parseDouble(index_pay);
											total_idx = pay_index * index_ov;
											jumlah = jumlah + total_idx;
											System.out.println("total_idx:::::::::::::::::;"+total_idx);
											rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
										}
									}
									else
									{
										//untuk mengecek setelah pengurangan Hournya = 0, jika telah habis dikurangi maka melakukan pengecekan terhadap iDurationHournya  
										if(iDurationMin>0)
										{
											  index_pay = "0."+iDurationMin;
											  pay_index = Double.parseDouble(index_pay);
											  total_idx = pay_index * index_ov;
											  jumlah = jumlah + total_idx;
											  System.out.println("total_idx:::::::::::::::::;"+total_idx);
											  //jika masih lebih besar dari nol maka di set iDurationMin = 0
											  iDurationMin = 0;
										}
										else
										{
											index_pay = String.valueOf(0.0);
											pay_index = Double.parseDouble(index_pay);
											total_idx = pay_index * index_ov;
											jumlah = jumlah + total_idx;
											System.out.println("total_idx:::::::::::::::::;"+total_idx);
										}
										rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
									}
							}
				//tutup kurung untuk for yang ketiga			
				}
				rowx.add("<input type=\"text\" name=\"jumlah_"+i+""+k+"\" value=\""+jumlah+"\" class=\"formElemen\" size=\"2\">");
				rowx.add("<input type=\"checkbox\""+strChecked+" name=\"employee_oid_"+i+""+k+"\" value=\""+employee.getOID()+"\">");
			 }
			 lstData.add(rowx);
		   //tutup kurung untuk for yang ke dua	 
		   }
		  //tutup kurung untuk for yang pertama 
    	 }
		//tutup kurung untuk jika objectclass tidak sama dengan null.
		}
		rowx = new Vector();

		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			rowx.add("");
			rowx.add("net");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
		}
		lstData.add(rowx);
		result = ctrlist.drawMeList();
	}
	else{
		if(iCommand==Command.ADD){
				rowx.add("");
				rowx.add("net");
				rowx.add("");
				rowx.add("");
				rowx.add("");
				rowx.add("");
				rowx.add("");
				rowx.add("");
				rowx.add("");
			lstData.add(rowx);
			result = ctrlist.drawMeList();
		}else{
			result = "<i>Belum ada data dalam sistem ...</i>";
		}
	}
	return result;
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidOvt_Employee = FRMQueryString.requestLong(request, "hidden_ovt_Employee_id");
long oidDivision = FRMQueryString.requestLong(request,"division");
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
long oidPeriod = FRMQueryString.requestLong(request,"periodId");
String code_overtime = FRMQueryString.requestString(request,"ovt_type");


/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;


CtrlOvt_Employee ctrlOvt_Employee = new CtrlOvt_Employee(request);
Vector listOvt_EmployeeImport = new Vector(1,1);
Vector listOvt_EmployeeDuration = new Vector(1,1);
/*switch statement */
iErrCode = ctrlOvt_Employee.action(iCommand , oidOvt_Employee, request);
/* end switch*/
FrmOvt_Employee frmOvt_Employee = ctrlOvt_Employee.getForm();

Ovt_Employee ovt_Employee = ctrlOvt_Employee.getOvt_Employee();
msgString =  ctrlOvt_Employee.getMessage();

Vector vListCekOvt_Import = new Vector();

/* get record to display */
if(iCommand == Command.LIST || iCommand==Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD || iCommand == Command.SUBMIT)
{
	listOvt_EmployeeImport = SessEmployee.listImportPresent(oidDepartment,oidDivision,oidSection);
}

//untuk mencari time outnya
Date periodStart = new Date();
Date periodEnd = new Date();
Date counterDate = new Date();
Date tmpCounterDate = null;
Date timeIn = null;
Date timeOut = null;
Date paramTimeIn = null;
Date paramTimeOut = null;

Vector vDate = new Vector(1,1);
Vector vPresenceIdIn = new Vector(1,1);
Vector vPresenceIdOut = new Vector(1,1);
Vector vTimeIn = new Vector(1,1);
Vector vTimeOut = new Vector(1,1);
Vector vScdSymbol = new Vector(1,1);
Vector vScdTimeIn = new Vector(1,1);
Vector vScdTimeOut = new Vector(1,1);

long lScheduleId = 0;
long lPresenceIdIn = 0;
long lPresenceIdOut = 0;
String scdSymbol = "";

long iDuration = 0;
long iDurationHour = 0;

Period objPeriod = new Period();
if(oidPeriod!=0){
	try{
		objPeriod = PstPeriod.fetchExc(oidPeriod);
	}catch(Exception e){;}
	periodStart = objPeriod.getStartDate();
	periodEnd = objPeriod.getEndDate();
}

counterDate = periodStart;
int yearStart = periodStart.getYear() + 1900;
int monthStart = periodStart.getMonth() + 1;
int dateStart = periodStart.getDate();
int monthEnd = periodEnd.getMonth() + 1;
GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart-1, dateStart);
int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
int startDatePeriod = Integer.parseInt(""+PstSystemProperty.getValueByName("START_DATE_PERIOD"));
int durasi = nDayOfMonthStart;

//untuk mendapatkan jumlah index di overtime index
String sWhereOver = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+" = '"+code_overtime+"'";
Vector vListOver = PstOvt_Idx.list(0,0,sWhereOver,"");

if ((iCommand==Command.LIST)) {
	Employee employee = new Employee();
	Position position = new Position();
	
  if(listOvt_EmployeeImport!=null && listOvt_EmployeeImport.size()>0){
	for(int p=0;p<listOvt_EmployeeImport.size();p++){
		Vector temp = (Vector)listOvt_EmployeeImport.get(p);
		employee = (Employee)temp.get(0);
		position = (Position)temp.get(1);
		long oidEmployee = employee.getOID();
		int j = startDatePeriod;
			for(int n=0;n<durasi;n++){
			
				String count = String.valueOf(counterDate.getDate());
				String whichDt = "D" + String.valueOf(counterDate.getDate());
				int tanggal = 0;
				int startMonthPeriod = 0;
				if(j==nDayOfMonthStart){
					tanggal = j;
					j = 1;
				}else{
					tanggal = j;
					j = j + 1;
				}
			
				if((tanggal >= startDatePeriod) && (tanggal<=nDayOfMonthStart)){
					startMonthPeriod = monthStart;
				}
				else{
					startMonthPeriod = monthEnd;
				}
				  
				String work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-0"+String.valueOf(tanggal);
				Date coba = Formater.formatDate(work_date, "dd-MM-yyyy");
				System.out.println("work_date"+work_date);
				
				vDate.add(work_date);
				//counterDate.setDate(counterDate.getDate()+1);
				lScheduleId = SessPresence.getPresenceSchedule(oidEmployee, oidPeriod, whichDt);
				scdSymbol = SessPresence.getPresenceScdSymbol(lScheduleId);
				Date scdTimeIn = SessPresence.getPresenceScdTimeInOut(lScheduleId, 0);
				Date scdTimeOut = SessPresence.getPresenceScdTimeInOut(lScheduleId, 1);
				  
				if ((scdTimeIn != null) && (scdTimeOut != null)) {
					if (scdTimeIn.equals(scdTimeOut)) {
						scdTimeIn = null;
						scdTimeOut = null;
						paramTimeIn = null;
						paramTimeOut = null;
						timeIn = null;
						timeOut = null;
					} else {
						paramTimeIn = new Date(yearStart-1900, startMonthPeriod-1, tanggal, scdTimeIn.getHours(), scdTimeIn.getMinutes(), scdTimeIn.getSeconds());					
						timeIn = SessPresence.getPresenceActualIn(oidEmployee, paramTimeIn);
						if (((scdTimeIn != null) && (scdTimeOut != null)) && (scdTimeIn.getTime() > scdTimeOut.getTime())) {
							paramTimeOut = new Date(yearStart-1900, startMonthPeriod-1, tanggal, scdTimeOut.getHours(), scdTimeOut.getMinutes(), scdTimeOut.getSeconds());
						}else {
							int day = counterDate.getDate();
							if(scdTimeOut.getHours()< scdTimeIn.getHours()){									
									day = day+1;													
							}
							paramTimeOut = new Date(yearStart-1900, startMonthPeriod-1, tanggal, scdTimeOut.getHours(), scdTimeOut.getMinutes(), scdTimeOut.getSeconds());
						}
						timeOut = SessPresence.getPresenceActualOut(oidEmployee, paramTimeOut);
					}
				}
				/* --- scheduled time out --- */
				if (paramTimeOut != null) {
					vScdTimeOut.add(paramTimeOut);
				} else {
					vScdTimeOut.add(null);
				}
				//untuk actualnya
				if (timeOut != null) {
					vTimeOut.add(timeOut);
				} else {
					vTimeOut.add(null);
				}
			}
			
			temp.add(vDate);
			temp.add(vScdTimeOut);
			temp.add(vTimeOut);
			listOvt_EmployeeImport.setElementAt(temp,p);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
		}
	}
}


Hashtable has = new Hashtable();

if(iCommand==Command.LIST || iCommand == Command.SUBMIT){
	String sWhereClause = PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID]+" = "+oidPeriod+" AND "+
						  PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_CODE]+" = '"+code_overtime+"'";
						  
	vListCekOvt_Import = PstOvt_Employee.list(0,0,sWhereClause,"");
	if(vListCekOvt_Import!=null && vListCekOvt_Import.size()>0){
		for(int t=0;t<vListCekOvt_Import.size();t++){
		    Ovt_Employee objOvt_Employee = (Ovt_Employee)vListCekOvt_Import.get(t);
			has.put(String.valueOf(objOvt_Employee.getEmployee_num())+""+objOvt_Employee.getDuration(),objOvt_Employee.getOvt_doc_nr());
		}
	}
}
System.out.println("Haslistttttttttttttttttttttttttttttttttt =>>>>>>>> : "+has);

if(iCommand == Command.SUBMIT){
 System.out.println("Has =>>>>>>>> : "+durasi);
 boolean statusTransfer = false;
   if(listOvt_EmployeeImport!=null && listOvt_EmployeeImport.size()>0){
		for(int n=0;n<listOvt_EmployeeImport.size();n++){
		  Vector temp = (Vector)listOvt_EmployeeImport.get(n);
		  Employee employee = (Employee)temp.get(0);
		 // System.out.println("durasi:::::::::::::::::::::::::::::"+durasi);
			for(int q=0;q<durasi;q++){
				long oidEmployee = FRMQueryString.requestLong(request, "employee_oid_"+n+""+q+""); 
				
				String overtime_Nr = "";
				String employee_num = "";
				String employee_fullName = "";
				String position = "";
				String scheduleSymbol = "";
				
				Vector list = new Vector();
				if(oidEmployee!=0){
					statusTransfer = true;
					
					employee_num = FRMQueryString.requestString(request, "employee_num_"+n+""+q+"");
					list.add(employee_num);
					
					overtime_Nr = FRMQueryString.requestString(request, "overtime_nr_"+n+""+q+""); 
					list.add(overtime_Nr);
					
					employee_fullName = FRMQueryString.requestString(request, "employee_name_"+n+""+q+""); 
					list.add(employee_fullName);
					
					position = FRMQueryString.requestString(request, "potition_name_"+n+""+q+""); 
					list.add(position);
					
					String work_date = FRMQueryString.requestString(request, "work_date_"+n+""+q+""); 
					list.add(work_date);
					
					scheduleSymbol = FRMQueryString.requestString(request, "simbol_"+n+""+q+""); 
					list.add(scheduleSymbol);
					
					String dateTime = FRMQueryString.requestString(request, "dateTime_"+n+""+q+""); 
					list.add(dateTime);
					
					String timeOut1 = FRMQueryString.requestString(request, "timeOut_"+n+""+q+""); 
					list.add(timeOut1);
					
					String endTime = FRMQueryString.requestString(request, "dateTime_1_"+n+""+q+""); 
					list.add(endTime);
					
					String actualTime = FRMQueryString.requestString(request, "actualTime_"+n+""+q+""); 
					list.add(actualTime);
					
					long iDurationHour1 = FRMQueryString.requestLong(request, "iDurationHour_"+n+""+q+"");
					list.add(String.valueOf(iDurationHour1));
					
					long iDurationMin = FRMQueryString.requestLong(request, "iDurationMin_"+n+""+q+"");
					list.add(String.valueOf(iDurationMin));
					
					double jumlahIdx = FRMQueryString.requestDouble(request, "jumlah_"+n+""+q+"");
					list.add(String.valueOf(jumlahIdx));
					
					if(vListOver!=null && vListOver.size()>0){
					 for(int x =0; x<vListOver.size();x++){
							String index_Pay = FRMQueryString.requestString(request, "index_"+n+""+q+""+x+"");
							list.add(index_Pay);
					    }
					}
					has.put(String.valueOf(employee.getOID()+"_"+q),list);
				}else{
					// delete
					String durationHide = FRMQueryString.requestString(request, "duration_hide_"+n+""+q+"");
					//System.out.println("durationHide"+durationHide);
					PstOvt_Employee.deleteOvtEmployee(employee.getEmployeeNum(), durationHide, oidPeriod);
				}
			}
		}
	}
	System.out.println("Has =>>>>>>>> : "+has);
	System.out.println("code_overtime:::::::::::::"+code_overtime);
	
	String idPeriod = String.valueOf(oidPeriod);

	Vector temp1 = new Vector();
	Vector temp2 = new Vector();
	
	temp1.add(periodStart);
	temp1.add(periodEnd);
	temp1.add(listOvt_EmployeeImport);
	temp1.add(code_overtime);
	temp1.add(idPeriod);
	
	if(statusTransfer){
		try{
			session.putValue("employee_transfer",has);
			session.putValue("list_employee_import",temp1);
			//session.putValue("period_id_transfer",temp2);
		}catch(Exception e){}
		response.sendRedirect("ov-input.jsp?command="+iCommand+"");
	}
}
long oidOvt_EmployeeX = 0;
long oidEmployee_Ovt = 0;

%>
<!-- JSP Block -->
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
	document.frm_ovt_import.command.value="<%=Command.LIST%>";
	document.frm_ovt_import.action="ov-import-present.jsp";
	document.frm_ovt_import.submit();
}

function cmdSave(){
	document.frm_ovt_import.command.value="<%=Command.SAVE%>";
	document.frm_ovt_import.action="ov-import-present.jsp";
	document.frm_ovt_import.submit();
}

function cmdEdit(oidOvt_Employee){
	document.frm_ovt_import.hidden_ovt_Employee_id.value=oidOvt_Employee;
	document.frm_ovt_import.command.value="<%=Command.EDIT%>";
	document.frm_ovt_import.prev_command.value="<%=prevCommand%>";
	document.frm_ovt_import.action="ov-import-present.jsp";
	document.frm_ovt_import.submit();
}

function cmdBack(){
	document.frm_ovt_import.command.value="<%=Command.BACK%>";
	document.frm_ovt_import.action="ov-import-present.jsp";
	document.frm_ovt_import.submit();
}

function cmdAdd(){
	document.frm_ovt_import.hidden_ovt_Employee_id.value="0";
	document.frm_ovt_import.command.value="<%=Command.ADD%>";
	document.frm_ovt_import.prev_command.value="<%=prevCommand%>";
	document.frm_ovt_import.action="ov-import-present.jsp";
	document.frm_ovt_import.submit();
}

function cmdTransfer(){
	document.frm_ovt_import.command.value="<%=Command.SUBMIT%>";
	document.frm_ovt_import.prev_command.value="<%=prevCommand%>";
	document.frm_ovt_import.action="ov-import-present.jsp";
	document.frm_ovt_import.submit();
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Import 
                  Overtime from Presences<!-- #EndEditable --> </strong></font> 
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
                                   <form name="frm_ovt_import" method="post" action="">
									    <input type="hidden" name="command" value="<%=iCommand%>">
										<input type="hidden" name="start" value="<%=start%>">
										<input type="hidden" name="hidden_ovt_Employee_id" value="<%=oidOvt_Employee%>">
										 <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td height="13"></td>
                                          <td height="13"><table width="100%" border="0">
                                              <tr> 
                                                <td width="9%">Division :</td>
                                                <td width="13%"><%
													  Vector listDivision = PstDivision.list(0, 0, "", "DIVISION");										  
													  Vector divValue = new Vector(1,1);
													  Vector divKey = new Vector(1,1);
													  divValue.add("0");
                                                      divKey.add("select ..."); 
													  for(int d=0;d<listDivision.size();d++)
													  {
														Division division = (Division)listDivision.get(d);
														divValue.add(""+division.getOID());
														divKey.add(division.getDivision());										  
													  }
													  out.println(ControlCombo.draw("division",null,""+oidDivision,divValue,divKey));
                                                        %></td>
                                                <td width="13%">Department :</td>
                                                <td width="12%"> <% 
													Vector dept_value = new Vector(1,1);
													Vector dept_key = new Vector(1,1);        
													dept_value.add("0");
													dept_key.add("select ...");                                                          
													Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
													for (int i = 0; i < listDept.size(); i++) {
															Department dept = (Department) listDept.get(i);
															dept_key.add(dept.getDepartment());
															dept_value.add(String.valueOf(dept.getOID()));
													}
												out.println(ControlCombo.draw("department",null,""+oidDepartment,dept_value,dept_key));

												%> 
                                                </td>
                                                <td width="7%">Section : </td>
                                                <td width="46%"> <% 
													Vector sec_value = new Vector(1,1);
													Vector sec_key = new Vector(1,1); 
													sec_value.add("0");
													sec_key.add("select ...");
													//Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
													Vector listSec = PstSection.list(0, 0, "", " SECTION ");
													for (int i = 0; i < listSec.size(); i++) {
															Section sec = (Section) listSec.get(i);
															sec_key.add(sec.getSection());
															sec_value.add(String.valueOf(sec.getOID()));
													}
													out.println(ControlCombo.draw("section",null,""+oidSection,sec_value,sec_key));

												%> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>Period :</td>
                                                <td> <%
													  Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");										  
													  Vector periodValue = new Vector(1,1);
													  Vector periodKey = new Vector(1,1);
													
													  for(int d=0;d<listPeriod.size();d++)
													  {
														Period period = (Period)listPeriod.get(d);
														periodValue.add(""+period.getOID());
														periodKey.add(""+period.getPeriod());										  
													  }
													  out.println(ControlCombo.draw("periodId",null,""+oidPeriod,periodValue,periodKey));
                                               %> 
                                                </td>
                                                <td>Overtime Code :</td>
                                                <td>
												 <% 
														Vector ovt_type_value = new Vector(1,1);
														Vector ovt_type_key = new Vector(1,1); 
														
														Vector listOvt_Type = PstOvt_Type.list(0, 0, "", "");
														for (int i = 0; i < listOvt_Type.size(); i++) {
																Ovt_Type ovt = (Ovt_Type)listOvt_Type.get(i);
																ovt_type_key.add(ovt.getOvt_Type_Code());
																ovt_type_value.add(ovt.getOvt_Type_Code());
														}
														out.println(ControlCombo.draw("ovt_type",null,""+code_overtime,ovt_type_value,ovt_type_key));

												%>
												</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="9%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a> 
                                                  <img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td valign="top" colspan="4"> 
                                                  <a href="javascript:cmdSearch()">Search 
                                                  for Employee</a> </td>
                                                <td></td>
                                              </tr>
                                              <tr> 
                                                <td colspan="6">
												<table width="100%" border="0">
													<%
												   if(iCommand==Command.LIST)
													{%>
														<tr> 
														<td width="100%" colspan="6" height="8">
														<%=drawList(start, iCommand, frmOvt_Employee, ovt_Employee, listOvt_EmployeeImport, oidOvt_Employee, oidPeriod, code_overtime, vListCekOvt_Import, has)%></td>
													</tr>
												  <%}else{%>
													<tr> 
												  <td height="8" width="17%" class="comment"><span class="comment"><br>
													&nbsp;No Employee available</span> 
												  </td>
												</tr>
												 <%}%>
											</table>
												</td>
                                              </tr>
                                            </table> </td>
                                        </tr>
                                        <tr>
                                          <td height="13">&nbsp;</td>
                                          <td height="13"></td>
                                        </tr>
                                        <tr> 
                                          <td height="13">&nbsp;</td>
                                          <td height="13">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td valign="top"> 
                                           </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td> <a href="javascript:cmdTransfer()">Transfer approved presence as overtime &gt;&gt; 
                                            </a> </td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle">&nbsp;</td>
                                          <td class="listtitle">&nbsp;</td>
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
