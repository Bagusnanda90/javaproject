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
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
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
	ctrlist.dataFormat("OV-Form-Nr","7%","2","0","center","left");
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
	ctrlist.dataFormat("Transfer<br><input type=\"checkbox\" onchange=\"javascript:setChecked()\" name=\"chk_nama\" value=\"1\"><a href=#>All</a>","5%","2","0","left","left");
	ctrlist.dataFormat("Start Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("End Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("Duration Real","7%","0","0","center","left");
	ctrlist.dataFormat("Duration","7%","0","0","center","left");
	

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	String empNumParam="";
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
			Vector vTimeIn = (Vector)temp.get(5);
							
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
					Date timeOut = (Date)vScdTimeOut.get(k);
					Date actualTimeOut = (Date)vTimeOut.get(k);
					Date timeIn = (Date)vTimeIn.get(k);
					
					String timeOut1 = Formater.formatTimeLocale(timeOut, "HH:mm");
					String actualOut1 = Formater.formatTimeLocale(actualTimeOut, "HH:mm");
					String timeIn1 = Formater.formatTimeLocale(timeIn, "HH:mm");
					long iDuration = 0;
					long iDurationHour = 0;
					long iDurationMin = 0;
					String strDurationHour = "";
					
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
					
					Vector listEmployeeSchedule = PstOvt_Employee.listSchedule(tanggal, employee_id, idPeriod);
					ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
					if(listEmployeeSchedule!=null && listEmployeeSchedule.size()>0)
					{
						Vector tempX = (Vector)listEmployeeSchedule.get(0);
						scheduleSymbol = (ScheduleSymbol)tempX.get(1);
					}
					
					//untuk mendapatkan schedule category
					String sWhereClSym = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]+" = '"+scheduleSymbol.getSymbol()+"'";
					Vector vListSym = PstScheduleSymbol.list(0,0,sWhereClSym,"");
					ScheduleSymbol schedule = new ScheduleSymbol();
					if(vListSym.size()>0 && vListSym!=null){
						schedule = (ScheduleSymbol)vListSym.get(0);
					}
					
					//untuk mendapatkan category
					ScheduleCategory category = new ScheduleCategory();
					if(schedule.getScheduleCategoryId()>0){
						try{
							category = PstScheduleCategory.fetchExc(schedule.getScheduleCategoryId());
						}catch(Exception e){;}
					}
					
					Date dtCateg = Formater.formatDate(dateTime,"yyyy-MM-dd");
					
					Calendar newCalendar = Calendar.getInstance();
					newCalendar.setTime(dtCateg);
					int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
					
					int tglCateg = dtCateg.getDate();
					int monthCateg = dtCateg.getMonth();
					int yearCateg = dtCateg.getYear();
					
					//if(category.getCategory().equalsIgnoreCase("Across day schedule")){
					if(category.getCategoryType()==PstScheduleCategory.CATEGORY_ACCROSS_DAY){
						tglCateg = tglCateg + 1;
						if(tglCateg > dateOfMonth){
							tglCateg = 1;
							monthCateg = monthCateg + 1;
						}
					}
					
					//untuk menampilkan listemployee berdasarkan code_overtime
					String sWhereOverTm = PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE]+" = '"+code_overtime+"'";
					Vector vListOverTm = PstOvt_Type.list(0,0,sWhereOverTm,"");					
					Ovt_Type objType = new Ovt_Type();
					if(vListOverTm.size()>0 && vListOverTm!=null){
						objType = (Ovt_Type)vListOverTm.get(0);
					}
					
					//untuk menampilkan dateTime di startDate
					String dateTime1 = "";
					String str_dt_TimeOut = ""; 
					Date dt_TimeOut = new Date();
					String strTimeIn = "";
					String strTimeInPresence = "";
					String strTimeInIdx = "";
					Date dtTimeIn = new Date();
					int hoursTimeInPresence = 0;
					int minutesTimeInPresence = 0;
					int hoursTimeInIdx = 0;
					int minutesTimeInIdx = 0;
					//System.out.println("objType.getType_of_day()....."+objType.getType_of_day());
					//Jika jenis overtimenya adalah Holiday
					if(objType.getType_of_day()==2){
						//untuk menampilkan startdate dan time date jika tipedaynya == Holiday
							strTimeInPresence = PstOvt_Employee.listDateIn(tanggal, employee.getOID(), idPeriod);
							Date dateTimeInPresence = Formater.formatDate(strTimeInPresence, "yyyy-MM-dd HH:mm");
							if(dateTimeInPresence !=null){
							  hoursTimeInPresence  = dateTimeInPresence.getHours();
							  minutesTimeInPresence = dateTimeInPresence.getMinutes();
							}
							strTimeInIdx = PstOvt_Type.getStartTimeHL();
							Date dateTimeInIdx = Formater.formatDate(strTimeInIdx, "yyyy-MM-dd HH:mm");
							if(dateTimeInIdx!=null){
								hoursTimeInIdx  = dateTimeInIdx.getHours();
								minutesTimeInIdx = dateTimeInIdx.getMinutes();
							}
							// untuk menentukan start time pada overtime
							if(hoursTimeInPresence < hoursTimeInIdx ){
								strTimeIn = strTimeInIdx;
							}
							else if(hoursTimeInPresence==hoursTimeInIdx){
								if(minutesTimeInPresence > minutesTimeInIdx){
									strTimeIn = strTimeInPresence;
								}
							}
							else {
								strTimeIn = strTimeInPresence;
							}
						if(strTimeIn!=null)
						{
							dtTimeIn = Formater.formatDate(strTimeIn, "yyyy-MM-dd HH:mm");
							dateTime1 = Formater.formatDate(dtTimeIn, "yyyy-MM-dd");
							str_dt_TimeOut = Formater.formatTimeLocale(dtTimeIn, "HH:mm");
							dt_TimeOut = Formater.formatDate(str_dt_TimeOut, "HH:mm");	
						}
					}else
					{
						if(tglCateg > 9)
							dateTime1 = String.valueOf(yearCateg+1900)+"-"+String.valueOf(monthCateg+1)+"-"+String.valueOf(tglCateg);
						else
							dateTime1 = String.valueOf(yearCateg+1900)+"-"+String.valueOf(monthCateg+1)+"-0"+String.valueOf(tglCateg);
							
						//untuk medapatkan time di startDate
						dt_TimeOut = schedule.getTimeOut();
						Date dt_balance = new Date(); //actualTimeOut;
						if(actualTimeOut!=null){
							try{
								dt_balance.setDate(actualTimeOut.getDate());
								dt_balance.setMonth(actualTimeOut.getMonth());
								dt_balance.setYear(actualTimeOut.getYear());
								
								dt_balance.setHours(dt_TimeOut.getHours());
								dt_balance.setMinutes(dt_TimeOut.getMinutes());
								dt_balance.setSeconds(dt_TimeOut.getSeconds());
							}catch(Exception e){
								System.out.println("sadfdsfsd : "+e.toString());
							}
						}
											
						try
						{
							if(dt_TimeOut==null)
							{
								dt_TimeOut = new Date();
							}
							str_dt_TimeOut = Formater.formatTimeLocale(dt_TimeOut);
						}
						catch(Exception e)
						{ 
							str_dt_TimeOut = ""; 
						}
					}
					
					String DatePeriodX = yearStart+"-"+startMonthPeriod+"-0"+j;
					
					//untuk tanggal di work date
					String work_date = ""; 
					if(tanggal > 9)
						work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-"+String.valueOf(tanggal);
					else
						work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-0"+String.valueOf(tanggal);
					
					
					//untuk mendapatkan endtime 
					String strActualOut = PstOvt_Employee.listDateOut(tanggal, employee_id, idPeriod);
					Date dtOutActual = new Date();
					String dtActualReal = "";
					String dtTimeActualReal = "";
					Date dtTimeReal = new Date();
					
					if(strActualOut!=null){
					//System.out.println("strActualOut:::::::::::::::::::::::::::::::::"+strActualOut);
					//System.out.println("str_dt_TimeOut:::::::::::::::::::::::::::::::::"+str_dt_TimeOut);
					//System.out.println("dt_TimeOut:::::::::::::::::::::::::::::::::"+dt_TimeOut);
						dtOutActual = Formater.formatDate(strActualOut, "yyyy-MM-dd HH:mm");
						dtActualReal = Formater.formatDate(dtOutActual, "yyyy-MM-dd");
						dtTimeActualReal = Formater.formatTimeLocale(dtOutActual, "HH:mm");
						dtTimeReal = Formater.formatDate(dtTimeActualReal, "HH:mm");
					}
					
					int tnglActual = 0;	
					if(dtTimeReal != null && dt_TimeOut != null && strActualOut!=null){
						dt_TimeOut.setSeconds(0);
						tnglActual = dtOutActual.getDate();
						
						if(category.getCategoryType()==PstScheduleCategory.CATEGORY_ACCROSS_DAY && tnglActual == tglCateg){
							long timeRl = dtTimeReal.getTime()/60000;
							long timeOt = dt_TimeOut.getTime()/60000;
							/*if(timeRl < 0)
								timeRl = -(timeRl);*/
							System.out.println("timeRl:::::::::::::::::::::::::::::::::"+timeRl);
							System.out.println("timeOt:::::::::::::::::::::::::::::::::"+timeOt);
							iDuration = timeRl - timeOt;
							iDurationHour = (iDuration - (iDuration % 60)) / 60;
							iDurationMin = iDuration % 60;
						}else{					
							iDuration = dtTimeReal.getTime()/60000 - dt_TimeOut.getTime()/60000 ;
							iDurationHour = (iDuration - (iDuration % 60)) / 60;
							iDurationMin = iDuration % 60;
						}
						
					}
					
					
					int typeSch = 0;
					int typeSch1 = 0;
					int typeSch2 = 0;
					int typeSch3 = 0;
					int typeSch4 = 0;
					int typeSch5 = 0;
					int typeSch6 = 0;
					int typeSch7  = 0;
					int typeSch8  = 0;
					
					if(objType.getType_of_day()==2){
						typeSch = PstScheduleCategory.CATEGORY_OFF;
						typeSch1 = PstScheduleCategory.CATEGORY_LONG_LEAVE;
						typeSch2 = PstScheduleCategory.CATEGORY_ANNUAL_LEAVE;
						typeSch3 = 11;
						typeSch4 = 11;
						typeSch5 = 11;
						typeSch6 = 11;
						typeSch7 = 11;
						typeSch8 = 11;
					}else{
						typeSch = PstScheduleCategory.CATEGORY_ALL;
						typeSch1 = PstScheduleCategory.CATEGORY_REGULAR;
						typeSch2 = PstScheduleCategory.CATEGORY_SPLIT_SHIFT;
						typeSch3 = PstScheduleCategory.CATEGORY_NIGHT_WORKER;
						typeSch4 = PstScheduleCategory.CATEGORY_ABSENCE;
						typeSch5 = PstScheduleCategory.CATEGORY_ACCROSS_DAY;
						typeSch6 = PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT;
						typeSch7 = PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY;
						typeSch8 = PstScheduleCategory.CATEGORY_NEAR_ACCROSS_DAY;
					}
					
					int hour = 0;
					String overtime_nr = "";
					String strChecked = "";
					String employee_num = "";
					String index_pay = "";
					String striDurationMinX = "";
					
					String frmCurrency = "#.###";
					
					if(iDurationHour >= 0 && iDurationMin >= 0 && (category.getCategoryType()==typeSch || category.getCategoryType()==typeSch1 || category.getCategoryType()==typeSch2 || category.getCategoryType()==typeSch3 || category.getCategoryType()==typeSch4 || category.getCategoryType()==typeSch5 || category.getCategoryType()==typeSch6 || category.getCategoryType()==typeSch7 || category.getCategoryType()==typeSch8))
					{
						
						String striDurationP = String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin);
						double dblDurationP = Double.parseDouble(striDurationP);
						
						if(dblDurationP!=0.0)
						{
								if(iDurationMin < 10){
									striDurationMinX = "0"+iDurationMin;
								}else{
									striDurationMinX = String.valueOf(iDurationMin);
								}
								
								
								int nMenit = Integer.parseInt(striDurationMinX);
								//System.out.println("striDurationMinX:::::::::::::::::::::::::::::::::::::"+striDurationMinX);
								double menit = 0.0;
								/*if(!striDurationMinX.equalsIgnoreCase("00"))
								 */
								 /*try{
								  menit = Double.parseDouble(striDurationMinX);
								 }catch(Exception e){System.out.println("Err::::::::::"+e);}*/
								 
								long iDourationHoursX = iDurationHour;
								
								if(15 <= nMenit && nMenit < 45){
									nMenit = 30;
								}else if(45 <= nMenit && nMenit < 60){
									nMenit = 0;
									iDourationHoursX = iDourationHoursX + 1;
								}else {
									nMenit = 0;
								}
								if(nMenit==30){
									nMenit = 5;
								}
								
								
								String strDurationAll = String.valueOf(iDourationHoursX)+"."+String.valueOf(nMenit);
								double durationAll = Double.parseDouble(strDurationAll);
								
								String strMenitX = "0." + String.valueOf(nMenit);
								menit = Double.parseDouble(strMenitX);
								//System.out.println("strmenit::::::::::::::::::::::::::::::::::::::::::::::::::"+strMenitX);
								//untuk account nomor
								l = l + 1;
								int status = PstOvt_Employee.getStatus(employee.getEmployeeNum(),idPeriod,work_date);
								String empNum = "";
								String fullName = "";
								String empPos = "";
								if(employee.getEmployeeNum().equals(""+empNumParam)){
									empNum = empNum;
									fullName = fullName;
									empPos = empPos;
								}else{
									empNum = employee.getEmployeeNum();
									fullName = employee.getFullName();
									empPos = position.getPosition();
								}
								
								if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK) && (status==PstOvt_Employee.DRAFT || status==0))
								{
									
									rowx = new Vector();
									empNumParam = employee.getEmployeeNum();
									rowx.add(String.valueOf(l));
									rowx.add(empNum+"<input type=\"hidden\" name=\"employee_num_"+k+"\" value=\""+employee.getEmployeeNum()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(fullName+"<input type=\"hidden\" name=\"employee_name_"+k+"\" value=\""+employee.getFullName()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(empPos);
									rowx.add(Formater.formatDate(work_date,"dd-yy-MMMM"));
									rowx.add(scheduleSymbol.getSymbol());
									rowx.add(dateTime);
									rowx.add(Formater.formatTimeLocale(timeOut, "HH:mm"));
									rowx.add(dateTime);
									rowx.add(Formater.formatTimeLocale(actualTimeOut, "HH:mm"));
									rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"\">"+String.valueOf(iDurationHour)+"."+striDurationMinX);
									rowx.add("<input type=\"text\" name=\"overtime_nr\" value=\""+overtime_nr+"\" class=\"formElemen\">");
									if(vListOver!=null && vListOver.size()>0){
										for(int x=0; x<vListOver.size();x++){
											rowx.add("");
										}
									}
									rowx.add("<input type=\"checkbox\" name=\"checkbox\" value=\"1\">");
							   }
							   else if(status==PstOvt_Employee.DRAFT||status==0)
							   {
							   		rowx = new Vector();
									empNumParam = employee.getEmployeeNum();
									rowx.add(String.valueOf(l));
									rowx.add(empNum+"<input type=\"hidden\" name=\"employee_num_"+i+""+k+"\" value=\""+employee.getEmployeeNum()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(fullName+"<input type=\"hidden\" name=\"employee_name_"+i+""+k+"\" value=\""+employee.getFullName()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(empPos+"<input type=\"hidden\" name=\"potition_name_"+i+""+k+"\" value=\""+position.getPosition()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(work_date+"<input type=\"hidden\" name=\"work_date_"+i+""+k+"\" value=\""+work_date+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(scheduleSymbol.getSymbol()+"<input type=\"hidden\" name=\"simbol_"+i+""+k+"\" value=\""+scheduleSymbol.getSymbol()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(dateTime1+"<input type=\"hidden\" name=\"dateTime_"+i+""+k+"\" value=\""+dateTime1+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(str_dt_TimeOut+"<input type=\"hidden\" name=\"timeOut_"+i+""+k+"\" value=\""+str_dt_TimeOut+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(dtActualReal+"<input type=\"hidden\" name=\"dateTime_1_"+i+""+k+"\" value=\""+dtActualReal+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(dtTimeActualReal+"<input type=\"hidden\" name=\"actualTime_"+i+""+k+"\" value=\""+dtTimeActualReal+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"\">"+String.valueOf(iDurationHour)+"."+striDurationMinX+"<input type=\"hidden\" name=\"iDurationHour_"+i+""+k+"\" value=\""+iDurationHour+"\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"iDurationMin_"+i+""+k+"\" value=\""+iDurationMin+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDourationHoursX)+"."+Formater.formatNumber(menit,frmCurrency)+"\">"+String.valueOf(durationAll)+"<input type=\"hidden\" name=\"iDurationHoursX_"+i+""+k+"\" value=\""+iDourationHoursX+"\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"menit_"+i+""+k+"\" value=\""+durationAll+"\" size=\"25\" class=\"elemenForm\">");
									
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
									String durationStr = String.valueOf(iDourationHoursX);
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
											double allDuration = 0.0;
											//System.out.println("menit::::::::::::::::::::::::::::::::::"+menit);
											if((durationDbl>0 && menit>0)  || (durationDbl>0 && menit==0))
											{
												
												if((iDourationHoursX>=hourFrom) && (iDourationHoursX<hourTo))
												{
													//allDuration = Double.parseDouble(String.valueOf(iDourationHoursX))+menit;
													allDuration = durationDbl + menit;
													index_pay = String.valueOf(allDuration);
													pay_index = Double.parseDouble(index_pay);
													total_idx = pay_index * index_ov;
													jumlah = jumlah + total_idx;
													//durationDbl = durationDbl - iDourationHoursX;
													durationDbl = durationDbl - allDuration;
													menit = 0;
													
													rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
												}
												else
												{       
													if(iDourationHoursX<hourTo)
													{
														allDuration = durationDbl + menit;
														index_pay = String.valueOf(allDuration);
														//Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
														menit = 0;
													}
													else if(iDourationHoursX==0)
													{
														index_pay = "0."+menit;
													}
													else{
														index_pay = String.valueOf(hourTo);
													}
													durationDbl = durationDbl - hourTo;
													String strDuration = String.valueOf(durationDbl);
													long durationLong = Long.parseLong(strDuration.substring(0,strDuration.indexOf(".")));
													iDourationHoursX = durationLong;
													pay_index = Double.parseDouble(index_pay);
													total_idx = pay_index * index_ov;
													jumlah = jumlah + total_idx;
													
													rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
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
												rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
											}
									}

						//tutup kurung untuk for yang ketiga			
						}
						rowx.add("<input type=\"text\" name=\"jumlah_"+i+""+k+"\" value=\""+Formater.formatNumber(jumlah,frmCurrency)+"\" class=\"formElemen\" size=\"2\">");
						rowx.add("<input type=\"checkbox\""+strChecked+" name=\"employee_oid_"+i+""+k+"\" value=\""+employee.getOID()+"\" align=\"center\">");
					 }//tutp kurung else
					 if(status==PstOvt_Employee.DRAFT||status==0){
					 	lstData.add(rowx);
					}
				}
		   }//tutup kurung untuk iDurationHours > 0
		  //tutup kurung untuk for yang pertama 
    	 }
		//tutup kurung untuk jika objectclass tidak sama dengan null.
		}
		

		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			rowx = new Vector();
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
				rowx = new Vector();
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
			result = "<i>No Data Found ...</i>";
		}
	}
	return result;
}
%>
<%!
public String drawListDaily(int offset, int iCommand, FrmOvt_Employee frmObject, Ovt_Employee objEntity, Vector objectClass, long idOvt_Employee, long idPeriod, String code_overtime, Vector vListImport_Employee, Hashtable has, Date dateSelected){
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
	ctrlist.dataFormat("OV-Form-Nr","7%","2","0","center","left");
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
	ctrlist.dataFormat("Transfer<br><input type=\"checkbox\" onchange=\"javascript:setChecked()\" name=\"chk_nama\" value=\"1\"><a href=#>All</a>","5%","2","0","left","left");
	ctrlist.dataFormat("Start Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("End Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("Duration Real","7%","0","0","center","left");
	ctrlist.dataFormat("Duration","7%","0","0","center","left");
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	String empNumParam="";
	int index = -1;
	
	Date dtPeriodStart = new Date();
	Date dtPeriodEnd = new Date();
	//untuk menangkap period Id
	long periodId = PstPeriod.getPeriodIdBySelectedDate(dateSelected); 
	int tanggal = dateSelected.getDate();
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
			Vector vTimeIn = (Vector)temp.get(5);
			
			for(int k =0; k<1; k++)
			{
			double jumlah = 0.0;
			String dateTime = (String)vDate.get(k);
			Date timeOut = (Date)vScdTimeOut.get(k);
			Date actualTimeOut = (Date)vTimeOut.get(k);
			Date timeIn = (Date)vTimeIn.get(k);
			
			String timeOut1 = Formater.formatTimeLocale(timeOut, "HH:mm");
			String actualOut1 = Formater.formatTimeLocale(actualTimeOut, "HH:mm");
			String timeIn1 = Formater.formatTimeLocale(timeIn, "HH:mm");
			long iDuration = 0;
			long iDurationHour = 0;
			long iDurationMin = 0;
			String strDurationHour = "";
			//looping untuk mendapatkan simbol
			Vector listEmployeeSchedule = PstOvt_Employee.listSchedule(tanggal, employee.getOID(), periodId);
			ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
			if(listEmployeeSchedule!=null && listEmployeeSchedule.size()>0)
			{
				Vector tempX = (Vector)listEmployeeSchedule.get(0);
				scheduleSymbol = (ScheduleSymbol)tempX.get(1);
			}
			//untuk mendapatkan schedule category
			String sWhereClSym = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]+" = '"+scheduleSymbol.getSymbol()+"'";
			Vector vListSym = PstScheduleSymbol.list(0,0,sWhereClSym,"");
			ScheduleSymbol schedule = new ScheduleSymbol();
			if(vListSym.size()>0 && vListSym!=null){
				schedule = (ScheduleSymbol)vListSym.get(0);
			}
			
			//untuk mendapatkan category
			ScheduleCategory category = new ScheduleCategory();
			if(schedule.getScheduleCategoryId()>0){
				try{
					category = PstScheduleCategory.fetchExc(schedule.getScheduleCategoryId());
				}catch(Exception e){;}
			}
			Calendar newCalendar = Calendar.getInstance();
			newCalendar.setTime(dateSelected);
			int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
			int dateCateg = dateSelected.getDate();
			int monthCateg = dateSelected.getMonth();
			//if(category.getCategory().equalsIgnoreCase("Across day schedule")){
			if(category.getCategoryType()==PstScheduleCategory.CATEGORY_ACCROSS_DAY){
				dateCateg = dateCateg + 1;
				if(dateCateg > dateOfMonth){
					dateCateg = 1;
					monthCateg = monthCateg + 1;
				}
			}
			//untuk menampilkan listemployee berdasarkan code_overtime
			String sWhereOverTm = PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE]+" = '"+code_overtime+"'";
			Vector vListOverTm = PstOvt_Type.list(0,0,sWhereOverTm,"");					
			Ovt_Type objType = new Ovt_Type();
			if(vListOverTm.size()>0 && vListOverTm!=null){
				objType = (Ovt_Type)vListOverTm.get(0);
			}
			
			//untuk menampilkan dateTime di startDate
			String dateTime1 = "";
			String str_dt_TimeOut = ""; 
			Date dt_TimeOut = new Date();
			String strTimeIn = "";
			String strTimeInPresence = "";
			String strTimeInIdx = "";
			Date dtTimeIn = new Date();
			int hoursTimeInPresence = 0;
			int minutesTimeInPresence=0;
			int hoursTimeInIdx = 0;
			int minutesTimeInIdx = 0;
			int yearPrsence = 0;
			int monthPrsence = 0;
			int datePrsence = 0;


			if(objType.getType_of_day()==2){
			//untuk menampilkan startdate dan time date jika tipedaynya == Holiday
			strTimeInPresence = PstOvt_Employee.listDateIn(tanggal, employee.getOID(), periodId);
			Date dateTimeInPresence = Formater.formatDate(strTimeInPresence, "yyyy-MM-dd HH:mm");
			System.out.println("dateTimeInPresence........."+dateTimeInPresence);
			if(dateTimeInPresence !=null){
			  hoursTimeInPresence  = dateTimeInPresence.getHours();
			  minutesTimeInPresence = dateTimeInPresence.getMinutes();
			  yearPrsence = dateTimeInPresence.getYear()+1900;
			 monthPrsence = dateTimeInPresence.getMonth()+1;
			 	datePrsence = dateTimeInPresence.getDate();
		}
			strTimeInIdx = PstOvt_Type.getStartTimeHL();
			Date dateTimeInIdx = Formater.formatDate(strTimeInIdx, "yyyy-MM-dd HH:mm");
			if(dateTimeInIdx!=null){
				hoursTimeInIdx  = dateTimeInIdx.getHours();
				minutesTimeInIdx = dateTimeInIdx.getMinutes();
			}
			// untuk menentukan start time pada overtime
			if(hoursTimeInPresence < hoursTimeInIdx ){
				strTimeIn = strTimeInIdx;
			}
			else if(hoursTimeInPresence==hoursTimeInIdx){
				if(minutesTimeInPresence > minutesTimeInIdx){
					strTimeIn = strTimeInPresence;
				}else if(minutesTimeInPresence < minutesTimeInIdx){
					strTimeIn = strTimeInIdx;
				}
				else{
				     strTimeIn = strTimeInIdx;

				}
			}
//nambah dari sini
			else if(hoursTimeInPresence > hoursTimeInIdx ){
				hoursTimeInPresence  = hoursTimeInPresence + 1;
				minutesTimeInPresence = 0;
				//strTimeIn = strTimeInIdx;
				strTimeIn = ""+yearPrsence+"-"+monthPrsence+"-"+datePrsence+" "+hoursTimeInPresence+":"+minutesTimeInPresence+"0:00";				
			}
// sampai sini

			else {
				strTimeIn = strTimeInPresence;
			}
			System.out.println("strTimeIn........."+strTimeIn);
			//Date dateTimeIn = Formater.formatDate(strTimeIn, "yyyy-MM-dd HH:mm");
			if(strTimeIn!=null)
			{
				dtTimeIn = Formater.formatDate(strTimeIn, "yyyy-MM-dd HH:mm");
				dateTime1 = Formater.formatDate(dtTimeIn, "yyyy-MM-dd");
				str_dt_TimeOut = Formater.formatTimeLocale(dtTimeIn, "HH:mm");
				dt_TimeOut = Formater.formatDate(str_dt_TimeOut, "HH:mm");	
			}
		  }else
			{
				if(dateSelected.getDate() > 9)
					dateTime1 = String.valueOf(dateSelected.getYear()+1900)+"-"+String.valueOf(dateSelected.getMonth()+1)+"-"+String.valueOf(dateSelected.getDate());
				else
					dateTime1 = String.valueOf(dateSelected.getYear()+1900)+"-"+String.valueOf(dateSelected.getMonth()+1)+"-0"+String.valueOf(dateSelected.getDate());
				
				//untuk medapatkan time di startDate
				dt_TimeOut = schedule.getTimeOut();
				Date dt_balance = new Date(); //actualTimeOut;
				if(actualTimeOut!=null){
					try{
						dt_balance.setDate(actualTimeOut.getDate());
						dt_balance.setMonth(actualTimeOut.getMonth());
						dt_balance.setYear(actualTimeOut.getYear());
						
						dt_balance.setHours(dt_TimeOut.getHours());
						dt_balance.setMinutes(dt_TimeOut.getMinutes());
						dt_balance.setSeconds(dt_TimeOut.getSeconds());
					}catch(Exception e){
						System.out.println("sadfdsfsd : "+e.toString());
					}
				}
				try
				{
					if(dt_TimeOut==null)
					{
						dt_TimeOut = new Date();
					}
					str_dt_TimeOut = Formater.formatTimeLocale(dt_TimeOut);
				}
				catch(Exception e)
				{ 
					str_dt_TimeOut = ""; 
				}
			}
			
			/*String DatePeriodX = yearStart+"-"+startMonthPeriod+"-0"+j;
					
			//untuk tanggal di work date
			String work_date = ""; 
			if(tanggal > 9)
				work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-"+String.valueOf(tanggal);
			else
				work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-0"+String.valueOf(tanggal);
			*/
			//untuk mendapatkan endtime 
			String strActualOut = PstOvt_Employee.listDateOut(tanggal, employee.getOID(), periodId);
			Date dtOutActual = new Date();
			String dtActualReal = "";
			String dtTimeActualReal = "";
			Date dtTimeReal = new Date();
			
			if(strActualOut!=null){
			//System.out.println("strActualOut:::::::::::::::::::::::::::::::::"+strActualOut);
			//System.out.println("str_dt_TimeOut:::::::::::::::::::::::::::::::::"+str_dt_TimeOut);
			//System.out.println("dt_TimeOut:::::::::::::::::::::::::::::::::"+dt_TimeOut);
			dtOutActual = Formater.formatDate(strActualOut, "yyyy-MM-dd HH:mm");
			dtActualReal = Formater.formatDate(dtOutActual, "yyyy-MM-dd");
			dtTimeActualReal = Formater.formatTimeLocale(dtOutActual, "HH:mm");
			dtTimeReal = Formater.formatDate(dtTimeActualReal, "HH:mm");
			}
			int tnglActual = 0;	
			if(dtTimeReal != null && dt_TimeOut != null && strActualOut!=null){
				dt_TimeOut.setSeconds(0);
				tnglActual = dtOutActual.getDate();
				
				if(category.getCategoryType()==PstScheduleCategory.CATEGORY_ACCROSS_DAY && tnglActual == dateSelected.getDate()){
					long timeRl = dtTimeReal.getTime()/60000;
					long timeOt = dt_TimeOut.getTime()/60000;
					/*if(timeRl < 0)
						timeRl = -(timeRl);*/
					System.out.println("timeRl:::::::::::::::::::::::::::::::::"+timeRl);
					System.out.println("timeOt:::::::::::::::::::::::::::::::::"+timeOt);
					iDuration = timeRl - timeOt;
					iDurationHour = (iDuration - (iDuration % 60)) / 60;
					iDurationMin = iDuration % 60;
				}else{					
					iDuration = dtTimeReal.getTime()/60000 - dt_TimeOut.getTime()/60000 ;
					iDurationHour = (iDuration - (iDuration % 60)) / 60;
					iDurationMin = iDuration % 60;
				}
				
			}
			int typeSch = 0;
			int typeSch1 = 0;
			int typeSch2 = 0;
			int typeSch3 = 0;
			int typeSch4 = 0;
			int typeSch5 = 0;
			int typeSch6 = 0;
			int typeSch7  = 0;
			int typeSch8  = 0;
			
			if(objType.getType_of_day()==2){
				typeSch = PstScheduleCategory.CATEGORY_OFF;
				typeSch1 = PstScheduleCategory.CATEGORY_LONG_LEAVE;
				typeSch2 = PstScheduleCategory.CATEGORY_ANNUAL_LEAVE;
				typeSch3 = 11;
				typeSch4 = 11;
				typeSch5 = 11;
				typeSch6 = 11;
				typeSch7 = 11;
				typeSch8 = 11;
			}else{
				typeSch = PstScheduleCategory.CATEGORY_ALL;
				typeSch1 = PstScheduleCategory.CATEGORY_REGULAR;
				typeSch2 = PstScheduleCategory.CATEGORY_SPLIT_SHIFT;
				typeSch3 = PstScheduleCategory.CATEGORY_NIGHT_WORKER;
				typeSch4 = PstScheduleCategory.CATEGORY_ABSENCE;
				typeSch5 = PstScheduleCategory.CATEGORY_ACCROSS_DAY;
				typeSch6 = PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT;
				typeSch7 = PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY;
				typeSch8 = PstScheduleCategory.CATEGORY_NEAR_ACCROSS_DAY;
		}
					int hour = 0;
					String overtime_nr = "";
					String strChecked = "";
					String employee_num = "";
					String index_pay = "";
					String striDurationMinX = "";
					
					String frmCurrency = "#.###";
					
					if(iDurationHour >= 0 && iDurationMin >= 0 && (category.getCategoryType()==typeSch || category.getCategoryType()==typeSch1 || category.getCategoryType()==typeSch2 || category.getCategoryType()==typeSch3 || category.getCategoryType()==typeSch4 || category.getCategoryType()==typeSch5 || category.getCategoryType()==typeSch6 || category.getCategoryType()==typeSch7 || category.getCategoryType()==typeSch8))
					{
						
						String striDurationP = String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin);
						double dblDurationP = Double.parseDouble(striDurationP);
						
						if(dblDurationP!=0.0)
						{
								if(iDurationMin < 10){
									striDurationMinX = "0"+iDurationMin;
								}else{
									striDurationMinX = String.valueOf(iDurationMin);
								}
								
								
								int nMenit = Integer.parseInt(striDurationMinX);
								//System.out.println("striDurationMinX:::::::::::::::::::::::::::::::::::::"+striDurationMinX);
								double menit = 0.0;
								/*if(!striDurationMinX.equalsIgnoreCase("00"))
								 */
								 /*try{
								  menit = Double.parseDouble(striDurationMinX);
								 }catch(Exception e){System.out.println("Err::::::::::"+e);}*/
								 
								long iDourationHoursX = iDurationHour;
								
								if(15 <= nMenit && nMenit < 45){
									nMenit = 30;
								}else if(45 <= nMenit && nMenit < 60){
									nMenit = 0;
									iDourationHoursX = iDourationHoursX + 1;
								}else {
									nMenit = 0;
								}
								if(nMenit==30){
									nMenit = 5;
								}
								
								
								String strDurationAll = String.valueOf(iDourationHoursX)+"."+String.valueOf(nMenit);
								double durationAll = Double.parseDouble(strDurationAll);
								
								String strMenitX = "0." + String.valueOf(nMenit);
								menit = Double.parseDouble(strMenitX);
								//System.out.println("strmenit::::::::::::::::::::::::::::::::::::::::::::::::::"+strMenitX);
								//untuk account nomor
								l = l + 1;
								String workDt = Formater.formatDate(dateSelected,"yyyy-MM-dd");
								int status = PstOvt_Employee.getStatus(employee.getEmployeeNum(),periodId,workDt);
								String empNum = "";
								String fullName = "";
								String empPos = "";
								if(employee.getEmployeeNum().equals(""+empNumParam)){
									empNum = empNum;
									fullName = fullName;
									empPos = empPos;
								}else{
									empNum = employee.getEmployeeNum();
									fullName = employee.getFullName();
									empPos = position.getPosition();
								}
								
								if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK) && (status==PstOvt_Employee.DRAFT||status==0))
								{
									rowx = new Vector();
									empNumParam = employee.getEmployeeNum();
									rowx.add(String.valueOf(l));
									rowx.add(empNum+"<input type=\"hidden\" name=\"employee_num_"+k+"\" value=\""+employee.getEmployeeNum()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(fullName+"<input type=\"hidden\" name=\"employee_name_"+k+"\" value=\""+employee.getFullName()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(empPos);
									rowx.add(Formater.formatDate(dateSelected,"dd-MMM-yyyy"));
									rowx.add(scheduleSymbol.getSymbol());
									rowx.add(dateTime);
									rowx.add(Formater.formatTimeLocale(timeOut, "HH:mm"));
									rowx.add(dateTime);
									rowx.add(Formater.formatTimeLocale(actualTimeOut, "HH:mm"));
									rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"\">"+String.valueOf(iDurationHour)+"."+striDurationMinX);
									rowx.add("<input type=\"text\" name=\"overtime_nr\" value=\""+overtime_nr+"\" class=\"formElemen\">");
									if(vListOver!=null && vListOver.size()>0){
										for(int x=0; x<vListOver.size();x++){
											rowx.add("");
										}
									}
									rowx.add("<input type=\"checkbox\" name=\"checkbox\" value=\"1\">");
							   }
							   else if(status==PstOvt_Employee.DRAFT||status==0)
							   {
							   		rowx = new Vector();
									empNumParam = employee.getEmployeeNum();
									rowx.add(String.valueOf(l));
									rowx.add(empNum+"<input type=\"hidden\" name=\"employee_num_"+i+""+k+"\" value=\""+employee.getEmployeeNum()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(fullName+"<input type=\"hidden\" name=\"employee_name_"+i+""+k+"\" value=\""+employee.getFullName()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(empPos+"<input type=\"hidden\" name=\"potition_name_"+i+""+k+"\" value=\""+position.getPosition()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(Formater.formatDate(dateSelected,"yyyy-MM-dd")+"<input type=\"hidden\" name=\"work_date_"+i+""+k+"\" value=\""+workDt+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(scheduleSymbol.getSymbol()+"<input type=\"hidden\" name=\"simbol_"+i+""+k+"\" value=\""+scheduleSymbol.getSymbol()+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(dateTime1+"<input type=\"hidden\" name=\"dateTime_"+i+""+k+"\" value=\""+dateTime1+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(str_dt_TimeOut+"<input type=\"hidden\" name=\"timeOut_"+i+""+k+"\" value=\""+str_dt_TimeOut+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(dtActualReal+"<input type=\"hidden\" name=\"dateTime_1_"+i+""+k+"\" value=\""+dtActualReal+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add(dtTimeActualReal+"<input type=\"hidden\" name=\"actualTime_"+i+""+k+"\" value=\""+dtTimeActualReal+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"\">"+String.valueOf(iDurationHour)+"."+striDurationMinX+"<input type=\"hidden\" name=\"iDurationHour_"+i+""+k+"\" value=\""+iDurationHour+"\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"iDurationMin_"+i+""+k+"\" value=\""+iDurationMin+"\" size=\"25\" class=\"elemenForm\">");
									rowx.add("<input type=\"hidden\" name=\"duration_hide_"+i+""+k+"\" value=\""+String.valueOf(iDourationHoursX)+"."+Formater.formatNumber(menit,frmCurrency)+"\">"+String.valueOf(durationAll)+"<input type=\"hidden\" name=\"iDurationHoursX_"+i+""+k+"\" value=\""+iDourationHoursX+"\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"menit_"+i+""+k+"\" value=\""+durationAll+"\" size=\"25\" class=\"elemenForm\">");
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
									String durationStr = String.valueOf(iDourationHoursX);
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
											double allDuration = 0.0;
											//System.out.println("menit::::::::::::::::::::::::::::::::::"+menit);
											if((durationDbl>0 && menit>0)  || (durationDbl>0 && menit==0))
											{
												if((iDourationHoursX>=hourFrom) && (iDourationHoursX<hourTo))
												{
													//allDuration = Double.parseDouble(String.valueOf(iDourationHoursX))+menit;
													allDuration = durationDbl + menit;
													index_pay = String.valueOf(allDuration);
													pay_index = Double.parseDouble(index_pay);
													total_idx = pay_index * index_ov;
													jumlah = jumlah + total_idx;
													//durationDbl = durationDbl - iDourationHoursX;
													durationDbl = durationDbl - allDuration;
													menit = 0;
													rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
												}
												else
												{
													if(iDourationHoursX<hourTo)
													{
														allDuration = durationDbl + menit;
														index_pay = String.valueOf(allDuration);
														//Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
														menit = 0;
													}
													else if(iDourationHoursX==0)
													{
														index_pay = "0."+menit;
													}
													else{
														index_pay = String.valueOf(hourTo);
													}
													durationDbl = durationDbl - hourTo;
													String strDuration = String.valueOf(durationDbl);
													long durationLong = Long.parseLong(strDuration.substring(0,strDuration.indexOf(".")));
													iDourationHoursX = durationLong;
													pay_index = Double.parseDouble(index_pay);
													total_idx = pay_index * index_ov;
													jumlah = jumlah + total_idx;
													rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
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
												rowx.add("<input type=\"text\" name=\"index_"+i+""+k+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\">");
											}
									}

						//tutup kurung untuk for yang ketiga			
						}
						rowx.add("<input type=\"text\" name=\"jumlah_"+i+""+k+"\" value=\""+Formater.formatNumber(jumlah,frmCurrency)+"\" class=\"formElemen\" size=\"2\">");
						rowx.add("<input type=\"checkbox\""+strChecked+" name=\"employee_oid_"+i+""+k+"\" value=\""+employee.getOID()+"\" align=\"center\">");
					 }//tutp kurung else
					 if(status==PstOvt_Employee.DRAFT||status==0){
					 	lstData.add(rowx);
					}
				}
		   }//tutup kurung untuk iDurationHours > 0
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
			result = "<i>No Data Found ...</i>";
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
String name_full = FRMQueryString.requestString(request,"full_name");
int gotoPeriod = FRMQueryString.requestInt(request, "hidden_goto_period");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
Date date = FRMQueryString.requestDate(request,"date");
String strDate = FRMQueryString.requestString(request,"datesrc");
if(strDate!=""){
System.out.println("sfsdf"+strDate);
Date datesrc = Formater.formatDate(strDate,"yyyy-MM-dd");
date = datesrc;
System.out.println(datesrc);
}

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

String sWhereOver = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+" = '"+code_overtime+"'";
Vector vListOver = PstOvt_Idx.list(0,0,sWhereOver,"");


Ovt_Type objOvtType = new Ovt_Type();
int typeOvertime = 0;

/* get record to display */
if(iCommand == Command.LIST || iCommand==Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD || iCommand == Command.SUBMIT)
{
	listOvt_EmployeeImport = SessEmployee.listImportPresent(oidDepartment,oidDivision,oidSection,name_full);
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




counterDate = periodStart;
int yearStart = periodStart.getYear() + 1900;
int monthStart = periodStart.getMonth() + 1;
int dateStart = periodStart.getDate();
int monthEnd = periodEnd.getMonth() + 1;
GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart-1, dateStart);
int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
int startDatePeriod = Integer.parseInt(""+PstSystemProperty.getValueByName("START_DATE_PERIOD"));
int durasi = nDayOfMonthStart;
System.out.println("gotoPeriod..............."+gotoPeriod);

//untuk mendapatkan jumlah index di overtime index
if((iCommand==Command.LIST) && (gotoPeriod==PstOvt_Employee.DAILY)) {
	oidPeriod = PstPeriod.getPeriodIdBySelectedDate(date); 
	Employee employee = new Employee();
	Position position = new Position();
	
	if(listOvt_EmployeeImport!=null && listOvt_EmployeeImport.size()>0){
	for(int p=0;p<listOvt_EmployeeImport.size();p++){
		Vector temp = (Vector)listOvt_EmployeeImport.get(p);
		employee = (Employee)temp.get(0);
		position = (Position)temp.get(1);
		long oidEmployee = employee.getOID();
		int tanggal = date.getDate();
			for(int n=0;n<1;n++){
				String whichDt = "D" + String.valueOf(tanggal);
				//String work_date = "";
				
				vDate.add(Formater.formatDate(date,"yyyy-MM-dd"));
				//counterDate.setDate(counterDate.getDate()+1);
				lScheduleId = SessPresence.getPresenceSchedule(oidEmployee, oidPeriod, whichDt);
				scdSymbol = SessPresence.getPresenceScdSymbol(lScheduleId);
				vScdSymbol.add(scdSymbol);
				
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
						paramTimeIn = new Date(yearStart-1900, date.getMonth(), tanggal, scdTimeIn.getHours(), scdTimeIn.getMinutes(), scdTimeIn.getSeconds());					
						timeIn = SessPresence.getPresenceActualIn(oidEmployee, paramTimeIn);
						if (((scdTimeIn != null) && (scdTimeOut != null)) && (scdTimeIn.getTime() > scdTimeOut.getTime())) {
							paramTimeOut = new Date(yearStart-1900, date.getMonth(), tanggal, scdTimeOut.getHours(), scdTimeOut.getMinutes(), scdTimeOut.getSeconds());
						}else {
							int day = counterDate.getDate();
							if(scdTimeOut.getHours()< scdTimeIn.getHours()){									
									day = day+1;													
							}
							paramTimeOut = new Date(yearStart-1900, date.getMonth(), tanggal, scdTimeOut.getHours(), scdTimeOut.getMinutes(), scdTimeOut.getSeconds());
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
				
				if (timeIn != null) {
					vTimeIn.add(timeIn);
				} else {
					vTimeIn.add(null);
				}
			}
			
			temp.add(vDate);
			temp.add(vScdTimeOut);
			temp.add(vTimeOut);
			temp.add(vTimeIn);
			listOvt_EmployeeImport.setElementAt(temp,p);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
		}
	}
}else if((iCommand==Command.LIST)&& (gotoPeriod==PstOvt_Employee.MONTLY)){
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
				String work_date = "";
				if(tanggal > 9)
					work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-"+String.valueOf(tanggal);
				else
					work_date = String.valueOf(yearStart)+"-"+String.valueOf(startMonthPeriod)+"-0"+String.valueOf(tanggal);

				vDate.add(work_date);
				//counterDate.setDate(counterDate.getDate()+1);
				lScheduleId = SessPresence.getPresenceSchedule(oidEmployee, oidPeriod, whichDt);
				scdSymbol = SessPresence.getPresenceScdSymbol(lScheduleId);
				vScdSymbol.add(scdSymbol);
				
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
				
				if (timeIn != null) {
					vTimeIn.add(timeIn);
				} else {
					vTimeIn.add(null);
				}
			}
			
			temp.add(vDate);
			temp.add(vScdTimeOut);
			temp.add(vTimeOut);
			temp.add(vTimeIn);
			listOvt_EmployeeImport.setElementAt(temp,p);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
			vTimeOut = new Vector(1,1);
		}
	}
}


long iDuration = 0;
long iDurationHour = 0;

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
//System.out.println("Haslistttttttttttttttttttttttttttttttttt =>>>>>>>> : "+has);

if((iCommand == Command.SUBMIT) && (gotoPeriod==PstOvt_Employee.MONTLY)){
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
					
					long iDurationHoursX = FRMQueryString.requestLong(request, "iDurationHoursX_"+n+""+q+"");
					list.add(String.valueOf(iDurationHoursX));
					
					double menit = FRMQueryString.requestDouble(request, "menit_"+n+""+q+"");
					list.add(String.valueOf(menit));
					
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
	String idDivision = String.valueOf(oidDivision);
	String idDepartment = String.valueOf(oidDepartment);
	String idSection = String.valueOf(oidSection);

	Vector temp1 = new Vector();
	Vector temp2 = new Vector();
	
	temp1.add(periodStart);
	temp1.add(periodEnd);
	temp1.add(listOvt_EmployeeImport);
	temp1.add(code_overtime);
	temp1.add(idPeriod);
	temp1.add(idDivision);
	temp1.add(idDepartment);
	temp1.add(idSection);
	
	if(statusTransfer){
		try{
			session.putValue("employee_transfer",has);
			session.putValue("list_employee_import",temp1);
			session.putValue("date_selected",date);
			session.putValue("periode_selected",""+gotoPeriod);
			//session.putValue("period_id_transfer",temp2);
		}catch(Exception e){}
		response.sendRedirect("ov-input.jsp?command="+iCommand+"");
	}
}

if((iCommand == Command.SUBMIT) && (gotoPeriod==PstOvt_Employee.DAILY)){
 System.out.println("Has =>>>>>>>> : "+durasi);
 boolean statusTransfer = false;
   if(listOvt_EmployeeImport!=null && listOvt_EmployeeImport.size()>0){
		for(int n=0;n<listOvt_EmployeeImport.size();n++){
		  Vector temp = (Vector)listOvt_EmployeeImport.get(n);
		  Employee employee = (Employee)temp.get(0);
		 // System.out.println("durasi:::::::::::::::::::::::::::::"+durasi);
			for(int q=0;q<1;q++){
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
					
					long iDurationHoursX = FRMQueryString.requestLong(request, "iDurationHoursX_"+n+""+q+"");
					list.add(String.valueOf(iDurationHoursX));
					
					double menit = FRMQueryString.requestDouble(request, "menit_"+n+""+q+"");
					list.add(String.valueOf(menit));
					
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
	String idDivision = String.valueOf(oidDivision);
	String idDepartment = String.valueOf(oidDepartment);
	String idSection = String.valueOf(oidSection);

	Vector temp1 = new Vector();
	Vector temp2 = new Vector();
	
	temp1.add(periodStart);
	temp1.add(periodEnd);
	temp1.add(listOvt_EmployeeImport);
	temp1.add(code_overtime);
	temp1.add(idPeriod);
	temp1.add(idDivision);
	temp1.add(idDepartment);
	temp1.add(idSection);
	
	if(statusTransfer){
		try{
			session.putValue("employee_transfer",has);
			session.putValue("list_employee_import",temp1);
			session.putValue("date_selected",date);
			session.putValue("periode_selected",""+gotoPeriod);
			//session.putValue("period_id_transfer",temp2);
		}catch(Exception e){}
		response.sendRedirect("ov-input.jsp?command="+iCommand);
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
function deptChange() {
	document.frm_ovt_import.command.value = "<%=Command.GOTO%>";
	document.frm_ovt_import.hidden_goto_period.value = document.frm_ovt_import.reportPeriod.value;
	//document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	document.frm_ovt_import.action = "ov-import-present.jsp";
	document.frm_ovt_import.submit();
}

function setChecked(val) {
	dml=document.frm_ovt_import;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;					
	}
}

function setChecked() {
	dml=document.frm_ovt_import;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {
		dml.elements[i].checked = dml.chk_nama.checked;					
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
										 <input type="hidden" name="hidden_goto_period" value="<%=gotoPeriod%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td width="0%" height="13"></td>
                                          <td height="13" colspan="2"><table width="100%" border="0">
                                              <tr> 
                                                <td width="8%">Division :</td>
                                                <td width="20%"><%
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
                                                <td width="14%">Department :</td>
                                                <td width="19%"> <% 
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

												%> </td>
                                                <td width="12%">Section : </td>
                                                <td width="27%"> <% 
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

												%> </td>
                                              </tr>
                                              <tr> 
                                                <td>Import Period :</td>
                                                <td> <%
													// Import Overtime Period
													Vector impKey = new Vector();
													Vector impValue = new Vector();
													
													impKey.add(PstOvt_Employee.DAILY+"");
													impKey.add(PstOvt_Employee.MONTLY+"");
													impValue.add(PstOvt_Employee.periodImport[PstOvt_Employee.DAILY]);
													impValue.add(PstOvt_Employee.periodImport[PstPayEmpLevel.MONTLY]);
													String selectPeriod = String.valueOf(gotoPeriod);
													//out.println("gotoPeriod  "+gotoPeriod);
												  %> <%=ControlCombo.draw("reportPeriod",null,""+selectPeriod,impKey,impValue,"onchange=\"javascript:deptChange();\"")%></td>

                                                <td>Overtime Code :</td>
                                                <td> <% 
														Vector ovt_type_value = new Vector(1,1);
														Vector ovt_type_key = new Vector(1,1); 
														
														Vector listOvt_Type = PstOvt_Type.list(0, 0, "", "");
														for (int i = 0; i < listOvt_Type.size(); i++) {
																Ovt_Type ovt = (Ovt_Type)listOvt_Type.get(i);
																ovt_type_key.add(ovt.getOvt_Type_Code());
																ovt_type_value.add(ovt.getOvt_Type_Code());
														}
														out.println(ControlCombo.draw("ovt_type",null,""+code_overtime,ovt_type_value,ovt_type_key));

												%> </td>
                                                <td>Full Name : </td>
                                                <td> <input type="text" name="full_name"  value="<%=name_full%>" class="elemenForm" size="40"> </td>
                                              </tr>
											  <% if(gotoPeriod==PstOvt_Employee.DAILY){%>
											  <tr> 
                                                <td>Date  :</td>
                                                <td> <%=ControlDate.drawDate("date",date==null || iCommand == Command.GOTO || iCommand == Command.NONE?new Date():date,"formElemen",0,installInterval)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                              </tr>
											  <% }else if(gotoPeriod==PstOvt_Employee.MONTLY){
											  %>
											  	<tr> 
                                                <td> Period :</td>
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
                                               %> </td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;  </td>
                                                <td>&nbsp; </td>
                                                <td>&nbsp; </td>
                                              </tr>	
											  <% }%>
                                              <tr> 
                                                <td width="8%"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee">
                                                  <img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td valign="top" colspan="4"> 
                                                  <a href="javascript:cmdSearch()">Search 
                                                  for Employee</a> </td>
                                                <td></td>
                                              </tr>
                                              <tr> 
                                                <td colspan="6"> <table width="100%" border="0">
                                                    <%
												   if((iCommand==Command.LIST) && (gotoPeriod==PstOvt_Employee.DAILY))
													{%>
                                                    <tr> 
                                                      <td width="100%" colspan="6" height="8"> 
                                                        <%=drawListDaily(start, iCommand, frmOvt_Employee, ovt_Employee, listOvt_EmployeeImport, oidOvt_Employee, oidPeriod, code_overtime, vListCekOvt_Import, has,date)%></td>
                                                    </tr>
													<%
													}else if((iCommand==Command.LIST) && (gotoPeriod==PstOvt_Employee.MONTLY)){
													%>
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
                                                  </table></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <td height="13">&nbsp;</td>
                                          <td height="13" colspan="2"></td>
                                        </tr>
                                        <tr> 
                                          <td height="13">&nbsp;</td>
                                          <td height="13" colspan="2">&nbsp;</td>
                                        </tr>
                                       
                                        <tr> 
                                          <td>&nbsp;</td> 
                                          <td colspan="2"> <a href="javascript:cmdTransfer()">Transfer 
                                            approved presence as overtime &gt;&gt; 
                                            </a> </td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle">&nbsp;</td>
                                          <td colspan="2" class="listtitle">&nbsp;</td>
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
