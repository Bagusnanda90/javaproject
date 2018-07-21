
<%@page import="com.dimata.harisma.entity.attendance.Presence"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.attendance.PstPresence"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.util.Calendar"%>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*"%>
<%@ page import = "com.dimata.util.*"%>
<%@ page import = "com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*"%>
<%@ page import = "com.dimata.harisma.entity.employee.*"%>
<%@ page import = "com.dimata.harisma.session.attendance.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
 long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * create list object
 * consist of : 
 *  first index  ==> status object (will displayed or not)
 *  second index ==> object string will displayed
 *  third index  ==> object vector of string used in report on PDF format.
 */
public Vector drawList(Vector listPresenceReport, int dateOfMonth, int startPeriodSchedule,Hashtable hashTblSchedule, Vector vOvertimeDetail,Vector listPresencePersonalInOut) 
{
	Vector result = new Vector(1,1);
	if(listPresenceReport!=null && listPresenceReport.size()>0)
	{
		//int startPeriod =  Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%", "2", "0");
		ctrlist.addHeader("Payroll","6%", "2", "0");
		ctrlist.addHeader("Employee","16%", "2", "0");
		ctrlist.addHeader("Presence Duration (hours, minutes)","70%", "0", ""+dateOfMonth);
		ctrlist.addHeader(""+startPeriodSchedule,"2%", "0", "0");				
                int startPeriod = startPeriodSchedule;
		for(int j=0; j<dateOfMonth-1; j++){
			if(startPeriod == dateOfMonth){
				startPeriod =1;
				ctrlist.addHeader(""+startPeriod,"2%", "0", "0");
			}
			else{
				startPeriod =startPeriod+1;
				ctrlist.addHeader(""+startPeriod,"2%", "0", "0");
			}
			
		}
		ctrlist.addHeader("Total Days","3%", "2", "0");
	
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		
		// vector of data will used in pdf report
		Vector vectDataToPdf = new Vector(1,1);									
                 Presence presenceBreak = new Presence();
		int maxPresenceData = listPresenceReport.size();  
		int dataAmount = 0;
                long breakDurationPersonal = 0; 
                ScheduleSymbol scheduleSymbol=new ScheduleSymbol();
                 long nightShiftScheduleId = 0; 
                                 long nightShiftScheduleId2 = 0;
                //update by satrya 2012-10-17
                long breakDuration1st = 0;
                
                long breakDuration2nd =0;
                 Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();  						 							
		for(int i=0; i<maxPresenceData; i++) 
		{
			PresenceMonthly presenceMonthly = (PresenceMonthly)listPresenceReport.get(i);
			String empNum = presenceMonthly.getEmpNum();
			String empName = presenceMonthly.getEmpName();
			Vector empSchedules = presenceMonthly.getEmpSchedules();
			Vector empActualIn = presenceMonthly.getEmpIn();
			Vector empActualOut = presenceMonthly.getEmpOut();
			Vector empSchedules2 = presenceMonthly.getEmpSchedules2();
			Vector empActualIn2 = presenceMonthly.getEmpIn2();
			Vector empActualOut2 = presenceMonthly.getEmpOut2();
			  int kk=0; 
                     Date dtIdx = presenceMonthly.getDayIdx(kk);
                 long empId=presenceMonthly.getEmployeId();
			int startPeriodPresence = startPeriodSchedule;// Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));			
			startPeriodPresence = startPeriodPresence - 1;
			{
				int totalPresence = 0;
				int presenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
				Vector rowxMonth = new Vector(1,1);				
				for(int isch=0; isch<empSchedules.size(); isch++)
				{
					if(startPeriodPresence == dateOfMonth){
						startPeriodPresence =1;
					}
					else{
						startPeriodPresence  = startPeriodPresence + 1;
					}
					
                                        long PresenceScheduleId = Long.parseLong(String.valueOf(empSchedules.get(startPeriodPresence-1)));				
					String schldSymbol = PstScheduleSymbol.getScheduleSymbol(PresenceScheduleId);//PresenceScheduleId = schedule id nyua
					
                                        long PresenceScheduleId2 = Long.parseLong(String.valueOf(empSchedules2.get(startPeriodPresence-1)));				
					String schldSymbol2 = PstScheduleSymbol.getScheduleSymbol(PresenceScheduleId2);
					 /*if(breakTimeDuration.get(""+PresenceScheduleId) !=null){ 
                                                breakDuration1st = ((Long)breakTimeDuration.get(""+PresenceScheduleId)).longValue();
                                         }
                                         if(breakTimeDuration.get(""+PresenceScheduleId2) !=null){ 
                                                breakDuration2nd = ((Long)breakTimeDuration.get(""+PresenceScheduleId2)).longValue();
                                         } */
                                         if(scheduleSymbol!=null && scheduleSymbol.getBreakOut()!=null && scheduleSymbol.getBreakIn()!=null && dtIdx!=null && listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                         Date dtSchDateTime = null; 
                         Date dtpresenceDateTime = null;
                         Date dtSchEmpScheduleBIn = (Date) dtIdx.clone();
                         Date dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                         long preBreakOutX=0;
                         
                        
                         long preBreakInX=0;
                         
                         Date dtBreakOut=null; 
                         Date dtBreakIn=null;
                         boolean ispreBreakOutsdhdiambil = false; 
                          
                         for(int bIdx = 0;bIdx < listPresencePersonalInOut.size();bIdx++){
                         presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                         //update by satrya 2012-10-17
                         if(dtSchEmpScheduleBOut!=null){
                             dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                             dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                                         }                                        
                         if(dtSchEmpScheduleBOut!=null){
                             dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                             dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                         }
                         if(presenceBreak.getScheduleDatetime()!=null){
                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0); 
                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);       
                         }
                         
                         if(presenceBreak.getEmployeeId()==empId 
                                  && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),dtIdx)==0 )
                                  && presenceBreak.getScheduleDatetime()== null ){ 
                              if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  //bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                 // dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                              else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  //bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                  // dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                             
                          }
                         else if( dtpresenceDateTime!=null/*update by satrya 2014-01-27 presenceBreak.getScheduleDatetime() !=null*/
                                 && presenceBreak.getEmployeeId()==empId
                                 //update by satrya 2014-01-27 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),dtIdx)==0 )){
                                 &&(DateCalc.dayDifference(dtpresenceDateTime,dtIdx)==0 )){
                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                 //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                 //update by satrya 2013-07-28
                                      
                                  //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                  //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                   preBreakOutX  = dtpresenceDateTime==null?0:dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                  dtBreakOut = dtpresenceDateTime; 
                                  if(dtSchEmpScheduleBIn!=null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()){
                                      preBreakOutX = presenceBreak.getPresenceDatetime().getTime();
                                  }
                                  else if((presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){ ///jika karyawan mendahului istirahat
                                      preBreakOutX = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                    
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakOutX = presenceBreak.getPresenceDatetime().getTime();//jika schedulenya 00:00
                                  }
                                  else{
                                       preBreakOutX = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                  }
                                 
                                  ispreBreakOutsdhdiambil = false; 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){ 
                                  //istirahat terlamabat 
                                   preBreakInX = presenceBreak.getPresenceDatetime()==null?0:presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                   dtBreakIn = presenceBreak.getPresenceDatetime();
                                 if(preBreakOutX !=0L){   
                                  //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                     //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                  if(dtSchEmpScheduleBIn!=null && dtBreakOut!=null && dtBreakIn!=null &&
                                          dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()){
                                      //karena sudah pasti melewatijam istirahatnya
                                     long  tmpBreakDuration = ((Long)breakTimeDuration.get(""+nightShiftScheduleId)).longValue();
                                      preBreakOutX = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                  }   
                                  else if(presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){ ///jika karyawan melewati jam istirahat
                                        preBreakOutX = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakOutX = presenceBreak.getPresenceDatetime().getTime(); //jika schedulenya 00:00 
                                  }
                                  else{
                                      preBreakInX = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                    }
                                  
                                   breakDurationPersonal = breakDurationPersonal + (preBreakInX -  preBreakOutX);
                                
                                 
                                 ispreBreakOutsdhdiambil = true;
                                  preBreakOutX =0L;
                                   
                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                   // preBOut=null;
                                 }
                                 // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                           
                              }else if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                   dtBreakOut = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                   ispreBreakOutsdhdiambil=false;
                                   listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                              } else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  dtBreakIn = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                  listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                                   ispreBreakOutsdhdiambil=true;
                                 
                              }
                             
                             if(ispreBreakOutsdhdiambil){
                                   preBreakOutX=0L;
                                   preBreakInX=0L;
                                   dtBreakOut=null;
                                   dtBreakIn=null; 
                             }
                         }//end else if
                     }//end for break In                          
                         //update by satrya 2012-10-18
                             //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                                if(breakDurationPersonal ==0 && nightShiftScheduleId!=0 && breakTimeDuration.get(""+nightShiftScheduleId) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                                        try{                          
                                         breakDurationPersonal = ((Long)breakTimeDuration.get(""+nightShiftScheduleId)).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                                        }catch(Exception ex){
                                            System.out.println("Exception scheduleSymbol"+ex.toString());
                                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                                        }
                                }
                    }
                   //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                        else{
                            if(breakDurationPersonal ==0 && nightShiftScheduleId!=0 && breakTimeDuration.get(""+nightShiftScheduleId) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                                        try{                          
                                 breakDurationPersonal = ((Long)breakTimeDuration.get(""+nightShiftScheduleId)).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                                        }catch(Exception ex){
                                            System.out.println("Exception scheduleSymbol"+ex.toString());
                                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                                        }
                                }
                          }
  //update by satrya 2014-01-24
  long breakOvertime=0;
    if(vOvertimeDetail!=null && vOvertimeDetail.size()>0){
        for(int idxOt=0; idxOt<vOvertimeDetail.size();idxOt++){
            OvertimeDetail  ovdDetail = (OvertimeDetail)vOvertimeDetail.get(idxOt);
            if( ovdDetail.getEmployeeId()==empId && ovdDetail.getRestTimeinHr()!=0  && dtIdx!=null && ovdDetail.getDateFrom()!=null
                && DateCalc.dayDifference(ovdDetail.getDateFrom(),dtIdx)==0){
                breakOvertime = breakOvertime + (long) (ovdDetail.getRestTimeinHr()*60*60*1000);
                vOvertimeDetail.remove(idxOt);                              
                idxOt=idxOt-1;
                       }
            
                        }
                      }
                                        if(schldSymbol!=null && schldSymbol.length()>0)
					{
						String strDuration = "";
						Date dateActualIn = (Date)empActualIn.get(startPeriodPresence-1);
						Date dateActualOut = (Date)empActualOut.get(startPeriodPresence-1);
						
						String strDuration2 = "";
						Date dateActualIn2 = (Date)empActualIn2.get(startPeriodPresence-1);
						Date dateActualOut2 = (Date)empActualOut2.get(startPeriodPresence-1);
						
						if(dateActualIn != null && dateActualOut != null){ 
							/*long iDuration = (dateActualOut.getTime()/60000 - breakDuration1st/60000) - dateActualIn.getTime()/60000;
							long iDurationHour = (iDuration - (iDuration % 60)) / 60;
							long iDurationMin = iDuration % 60;
							String strDurationHour = iDurationHour + "h, ";
							String strDurationMin = iDurationMin + "m";
							strDuration = strDurationHour + strDurationMin;
                                                        * */
                                                      long iDurTimeIn = dateActualIn.getTime() / 1000;
                                                     long iDurTimeOut = (dateActualOut.getTime()-breakDuration1st) / 1000;
                                                     long iDuration = 0;
                                                     if (iDurTimeIn != iDurTimeOut) {
                                                            iDuration = (iDurTimeIn == 0 || iDurTimeOut == 0) ? 0 : iDurTimeOut - iDurTimeIn;
                                                     }
                                                        long iDurationHour = (iDuration - (iDuration % 3600)) / 3600;
                                                        long iDurationMin = iDuration % 3600 / 60;

                                                        if (!(iDurationHour == 0 && iDurationMin == 0)) {
                                                            String strDurationHour = (iDurationHour != 0) ? iDurationHour + "h, " : "";
                                                            String strDurationMin = (iDurationMin != 0) ? iDurationMin + "m" : "";
                                                            strDuration = strDurationHour + strDurationMin;
                                                        }
                                                    //untuk 2nd
                                                    /*
							long iDuration2 = 0;
                                                        if(dateActualIn2 != null && dateActualOut2 != null){ 
                                                            iDuration2 = (dateActualOut2.getTime()/60000 - breakDuration2nd/60000) - dateActualIn2.getTime()/60000;
                                                        }
                                                        long iDurationHour2 = (iDuration2 - (iDuration2 % 60)) / 60;
                                                        long iDurationMin2 = iDuration2 % 60;
                                                        String strDurationHour2 = iDurationHour2 + "h, ";
                                                        String strDurationMin2 = iDurationMin2 + "m";
                                                        strDuration2 = strDurationHour2 + strDurationMin2;
                                                        
                                                        if(schldSymbol2!=null && schldSymbol2.length()>0){
                                                            if(dateActualIn2 != null && dateActualOut2 != null){ 
                                                                strDuration+= "/"+strDuration2;
                                                            }else{
                                                                strDuration+="/"+schldSymbol2;
                                                            }
                                                        }
                                                        */
                                                         long iDurTimeIn2nd = 0;
                                                          long iDurTimeOut2nd = 0;
                                                           long iDuration2nd =0;
                                                   if(dateActualIn2 != null && dateActualOut2 != null){
                                                             iDurTimeIn2nd = dateActualIn2.getTime() / 1000;
                                                            iDurTimeOut2nd = (dateActualOut2.getTime()-breakDuration2nd) / 1000;
                                                  }
                                                            if (iDurTimeIn2nd != iDurTimeOut2nd) {
                                                                   iDuration2nd = (iDurTimeIn2nd == 0 || iDurTimeOut2nd == 0) ? 0 : iDurTimeOut2nd - iDurTimeIn2nd;
                                                            }
                                                               long iDurationHour2nd = (iDuration2nd - (iDuration2nd % 3600)) / 3600;
                                                               long iDurationMin2nd = iDuration2nd % 3600 / 60;

                                                               if (!(iDurationHour2nd == 0 && iDurationMin2nd == 0)) {
                                                                   String strDurationHour2nd = (iDurationHour2nd != 0) ? iDurationHour2nd + "h, " : "";
                                                                   String strDurationMin2nd = (iDurationMin2nd != 0) ? iDurationMin2nd + "m" : "";
                                                                   strDuration2 = strDurationHour2nd + strDurationMin2nd;
                                                               }				
                                                        
							presenceNull += 1;
                                                        
                                                        if(strDuration!=null && strDuration.length()>0)
                                                        {
                                                                totalPresence += 1;
                                                        }
                                                        
						}else{
                                                    strDuration = ""+schldSymbol;
                                                    
                                                    long iDuration2 = 0;
                                                    if(dateActualIn2 != null && dateActualOut2 != null){ 
                                                        iDuration2 = dateActualOut2.getTime()/60000 - dateActualIn2.getTime()/60000;
                                                    }
                                                    long iDurationHour2 = (iDuration2 - (iDuration2 % 60)) / 60;
                                                    long iDurationMin2 = iDuration2 % 60;
                                                    String strDurationHour2 = iDurationHour2 + "h, ";
                                                    String strDurationMin2 = iDurationMin2 + "m";
                                                    strDuration2 = strDurationHour2 + strDurationMin2;
                                                        
                                                    
                                                    if(schldSymbol2!=null && schldSymbol2.length()>0){
                                                        if(dateActualIn2 != null && dateActualOut2 != null){ 
                                                            strDuration+= "/"+strDuration2;
                                                        }else{
                                                            strDuration+="/"+schldSymbol2;
                                                        }
                                                    }
						}					
						rowxMonth.add(strDuration);						
						
					}
					else
					{
						rowxMonth.add("");											
					}
   kk++;
				}								
				
				
				if(presenceNull>0)
				{
					dataAmount += 1;				
					Vector rowx = new Vector(1,1);				
					rowx.add(""+dataAmount);
					rowx.add(empNum);  
					rowx.add(empName);  
					rowx.addAll(rowxMonth);
					rowx.add(String.valueOf(totalPresence));
					lstData.add(rowx);
					vectDataToPdf.add(rowx);
				}													

			}			
		}
		
		if(dataAmount>0)
		{
			result.add(String.valueOf(DATA_PRINT));							
			result.add(ctrlist.drawList());				
			result.add(vectDataToPdf);							
		}
		else
		{
			result.add(String.valueOf(DATA_NULL));					
			result.add("<div class=\"msginfo\">&nbsp;&nbsp;No presence data found ...</div>");				
			result.add(new Vector(1,1));																
		}	
								
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No presence data found ...</div>");				
		result.add(new Vector(1,1));																
	}
	return result;
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
//update by devin 2014-01-28
long oidSection = FRMQueryString.requestLong(request,"section");
long oidCompany = FRMQueryString.requestLong(request,"hidden_companyId");
long oidDivision = FRMQueryString.requestLong(request,"hidden_divisionId");
long periodId = FRMQueryString.requestLong(request,"period");
//update by devin 2014-02-03
Period periodd =new Period();
if(periodId !=0){
    try{
       periodd=PstPeriod.fetchExc(periodId);   
    }catch(Exception exc){
        
    }
  
}

Date dtStartDate = periodd.getStartDate();
Date dtEndDate = periodd.getEndDate();
//Date date = FRMQueryString.requestDate(request,"month_year");
//update by satrya 20`-10-17
String empNum = FRMQueryString.requestString(request, "emp_number");
String fullName = FRMQueryString.requestString(request, "full_name");
SessPresence sessPresence = new SessPresence();
Hashtable hasDfltScheduleTbl = new Hashtable();
PstOvertimeDetail pstOvertimeDetail = new PstOvertimeDetail();
Vector vOvertimeDetail =new Vector();
Vector listPresencePersonalInOut=new  Vector();
Period pr = new Period();
Date date = new Date();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(periodId);
			date =  pr.getStartDate();
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}

// get maximum date of selected month
Calendar newCalendar = Calendar.getInstance();
newCalendar.setTime(date);
int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 

Vector listPresenceReport = new Vector(1,1);
if(iCommand == Command.LIST)
{
      String order = "DATE("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, "
                        + "TIME("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, " 
                        + PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]
                        + " , "+ PstPresence.fieldNames[PstPresence.FLD_STATUS] + " ASC ";
	// untuk aplikasi yang membutuhkan parameter section.Jika cukup dengan parameter department pake yang atas
    //update by satrya 2012-10-17
	listPresenceReport = SessPresence.listPresenceDataMonthly(oidDepartment,oidCompany,oidDivision, date,oidSection,empNum.trim(),fullName.trim());
         hasDfltScheduleTbl = PstDefaultSchedule.getHashTblDfltSch();
          listPresencePersonalInOut = PstPresence.list(0 , 0, order, oidDepartment, fullName.trim(), dtStartDate, dtEndDate, oidSection, empNum.trim(),null,null,-1);
       vOvertimeDetail=pstOvertimeDetail.listOvertimeDetailMonthly(oidDepartment,oidCompany,oidDivision,iCalendarType,periodId ,oidSection,empNum.trim(), fullName.trim());
       
}
int startDatePeriod = pr==null || pr.getOID()==0 ?  Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD"))) :
     pr.getStartDate().getDate() ;

// process on drawlist
Vector vectResult = drawList((listPresenceReport !=null && listPresenceReport.size() > 0 ? listPresenceReport : new Vector()), dateOfMonth, startDatePeriod,hasDfltScheduleTbl,vOvertimeDetail,listPresencePersonalInOut);
int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector)vectResult.get(2);

// debug
//System.out.println("List Size = " + listPresenceReport.size());

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1); 
vectPresence.add(""+periodName);
vectPresence.add(""+oidDepartment);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+dateOfMonth);     
vectPresence.add(""+startDatePeriod); 
//vectPresence.add(""+empNum);     
//vectPresence.add(""+fullName);
vectPresence.add(""+oidSection);
if(session.getValue("PRESENCE_RPT_MONTHLY")!=null){
	session.removeValue("PRESENCE_RPT_MONTHLY");
}
session.putValue("PRESENCE_RPT_MONTHLY",vectPresence);			
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Mothly Presence Report</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="monthly_presence.jsp";
	document.frpresence.submit();
}
//update by devin 2014-01-28
function cmdUpdateDiv(){
    document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frpresence.action="monthly_presence.jsp";
    document.frpresence.target = "";
    document.frpresence.submit();
}
function cmdUpdatePos(){
    document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frpresence.action="monthly_presence.jsp";
    document.frpresence.target = "";
    document.frpresence.submit();
}

function cmdUpdateDep(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                 document.frpresence.action="monthly_presence.jsp";
                 document.frpresence.target = "";
                 document.frpresence.submit();
            }
function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.attendance.MonthlyPresencePdf";  
	//window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
	window.open(linkPage);  				
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
<!-- update by devin 2014-01-28 -->
<style type="text/css">
.tooltip {
	display:none;
	position:absolute;
	border:1px solid #333;
	background-color:#161616;
	border-radius:5px;
	padding:10px;
	color:#fff;
	font-size:12px Arial;
}
</style>
<!-- update by devin 2014-01-28 -->
<style type="text/css">
      
            .bdr{border-bottom:2px dotted #0099FF;}
		
		.highlight {
			color: #090;
		}
		
		.example {
			color: #08C;
			cursor: pointer;
			padding: 4px;
			border-radius: 4px;
		}
		
		.example:after {
			font-family: Consolas, Courier New, Arial, sans-serif;
			content: '?';
			margin-left: 6px;
			color: #08C;
		}
		
		.example:hover {
			background: #F2F2F2;
		}
		
		.example.dropdown-open {
			background: #888;
			color: #FFF;
		}
		
		.example.dropdown-open:after {
			color: #FFF;
		}
		
	</style>
        <!-- update by devin 2014-01-28 -->
<script type="text/javascript">
$(document).ready(function() {
        // Tooltip only Text
        $('.masterTooltip').hover(function(){
                // Hover over code
                var title = $(this).attr('title');
                $(this).data('tipText', title).removeAttr('title');
                $('<p class="tooltip"></p>')
                .text(title)
                .appendTo('body')
                .fadeIn('fast');
        }, function() {
                // Hover out code
                $(this).attr('title', $(this).data('tipText'));
                $('.tooltip').remove();
        }).mousemove(function(e) {
                var mousex = e.pageX + 20; //Get X coordinates
                var mousey = e.pageY + 10; //Get Y coordinates
                $('.tooltip')
                .css({ top: mousey, left: mousex })
        });
});
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; Monthly Presence Report<!-- #EndEditable --> </strong></font> 
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
                                                                              <!-- update by devin 2014-01-28 -->
                                        <tr>
                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Payrol Num </div></td>
                                                                                             <td width="30%" nowrap="nowrap">:
                                                                                               <input class="masterTooltip" type="text" size="40" name="emp_number"  value="<%= sessPresence.getEmpNum() != null ? sessPresence.getEmpNum() : empNum %>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                          </td>
                                                                                             <td width="5%" nowrap="nowrap"><div align="right"> Full Name </div> </td>
                                                                                             <td width="59%" nowrap="nowrap">:
                                                                                             <input class="masterTooltip" type="text" size="50" name="full_name"  value="<%= sessPresence.getEmpFullName() != null ? sessPresence.getEmpFullName() : fullName%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                          </td>
					</tr>
										<tr> 
              <td width="6%" nowrap="nowrap"><div align="left">Company </div></td>
              <td width="30%" nowrap="nowrap">:
                                          <%
               
    
					Vector comp_value = new Vector(1, 1);
					Vector comp_key = new Vector(1, 1);  
String whereComp="";   
/*if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
    whereComp = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+srcOvertime.getCompanyId();
}*/   
    Vector div_value = new Vector(1, 1);
    Vector div_key = new Vector(1, 1);      
    
                                          Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {
                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                    div_value.add("0");
                   div_key.add("select ...");
                   
                    dept_value.add("0");
                   dept_key.add("select ...");
                                                                                                            } else {
                                                                                                                Position position = null;
                                                                                                                try {
                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                } catch (Exception exc) {
                                                                                                                }
                                                                                                                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                    // comp_value.add("0");
                    // comp_key.add("select ...");
                     
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+  whereDiv +")": whereDiv;
                    
                                                                                                                } else {

                                                                                                                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                                                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
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
                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                    grpIdx = countIdx;   // A ha .. found here 
                                                                                                                                }
                                                                                                                            }
                                                                                                                            countIdx++;
                                                                                                                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                                                        // compose where clause
                                                                                                                        if (grpIdx >= 0) {
                                                                                                                            String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } catch (Exception exc) {
                                                                                                                            System.out.println(" Parsing Join Dept" + exc);
                                                                                                                         
                                                                                                                    }
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+ whereClsDep +")":whereClsDep;
                     
                                                                                                                }
                                                                                                            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
     div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }               
    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    String prevCompany="";
    String prevDivision="";
    


long prevCompanyTmp=0;        
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                //}
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
                                                                                                        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
                                                                                                        }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
             //untuk karyawan admin yg hanya bisa akses departement tertentu (ketika di awal)
             ////update
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                if(oidCompany!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
                }
                                                                                                    } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                                                                                                    }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                    if(oidCompany!=0){
                       div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision();             
                   }
                    //update by satrya 2013-09-19
                    else if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){
                        div_key.add(dept.getDivision());
                        div_value.add(""+dept.getDivisionId()); 

                        //update by satrya 2013-09-19
                        dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));

                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                   }

                }else{
                    
                     div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                }
            }
        }
 }else{
      if(oidCompany!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
       }
 }
            
            }
           else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }  
			%>
                <%= ControlCombo.draw("hidden_companyId", "formElemen", null, ""+oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> </td>
              <td width="5%" nowrap="nowrap"><div align="right"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div> </td>
              <td width="59%" nowrap="nowrap">:
                <%
               
					
                                   //update by satrya 2013-08-13
                                   //jika user memilih select kembali
                                   if(oidCompany==0){ 
                                      oidDivision=0; 
                                   }

if(oidCompany!=0){
    whereComp = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+oidCompany;
 listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0;  
long tmpFirstDiv=0;   

if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                   div_value.add("0");
                   div_key.add("select ...");
                   
                   dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                } 
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
   long prevDivTmp=0;
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //update
                if(oidDivision!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                }
                //lama
                /*
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
                */
                
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
           String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             //comp_key.add(dept.getCompany());
             //comp_value.add(""+dept.getCompanyId());
             
            
             
             if(prevDivTmp!=dept.getDivisionId()){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 
                    prevDivTmp=dept.getDivisionId();
              }
             
                    tmpFirstDiv=dept.getDivisionId(); 

                   // dept_key.add(dept.getDepartment());
                 //   dept_value.add(String.valueOf(dept.getOID()));           
               
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }
           /*else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }*/
          String chkAdaDpt="";
          if(whereComp!=null && whereComp.length()>0){
                chkAdaDpt = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp)+ ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+oidDivision;
          }
            Vector listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);
            if((listCheckAdaDept==null || listCheckAdaDept.size()==0)){
                
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                
                oidDivision=tmpFirstDiv;
              
            }
        }
 }else{
         oidDivision = tmpFirstDiv;
     
 }
               
            }
        }
    }
}
			%>
                <%= ControlCombo.draw("hidden_divisionId", "formElemen", null,""+oidDivision , div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%> </td>
                                        </tr>
										<tr> 
              <td width="6%" align="right" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
              <td width="30%" nowrap="nowrap"> :
                                          <%

            //update by satrya 2013-08-13
            //jika user memilih select kembali
            if(oidDepartment==0){  
                oidSection=0;
										  }
if(oidDivision!=0){
    if(whereComp!=null && whereComp.length()>0){
        whereComp = "("+whereComp + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+oidDivision;
    }
    
    listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0; 

if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                   div_value.add("0");
                   div_key.add("select ...");
                   
                   dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                } 
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
                
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             comp_key.add(dept.getCompany());
             comp_value.add(""+dept.getCompanyId());
             
             
             div_key.add(dept.getDivision());
             div_value.add(""+dept.getDivisionId()); 
              
             dept_key.add(dept.getDepartment());
             dept_value.add(String.valueOf(dept.getOID()));
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }
}
			%>
                <%= ControlCombo.draw("department", "formElemen", null, "" + oidDepartment, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%> </td>
              <td width="5%" align="left" nowrap valign="top"><div align="right"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
              <td width="59%" nowrap="nowrap">:
                <%

                                          Vector sec_value = new Vector(1, 1);
                                                                                                    Vector sec_key = new Vector(1, 1);
                                                                                                    sec_value.add("0");
                                                                                                    sec_key.add("select ...");

                                                                                                    //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                                                                                    //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                                                                                    String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                    Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
                                                                                                    for (int i = 0; i < listSec.size(); i++) {
                                                                                                        Section sec = (Section) listSec.get(i);
                                                                                                        sec_key.add(sec.getSection());
                                                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                                                    }
										  %>
                <%=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Period</div>
                                          </td>
                                          <td width="88%">: <%//=ControlDate.drawDateMY("month_year",date==null || iCommand == Command.NONE?new Date():date,"MMM yyyy","formElemen",0,installInterval)%>
										  		<%
										  Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
										  Vector periodValue = new Vector(1,1);
										  Vector periodKey = new Vector(1,1);
                                          //deptValue.add("0");
                                          //deptKey.add("ALL");

                                          for(int d=0;d<listPeriod.size();d++){
										  	Period period = (Period)listPeriod.get(d);
											periodValue.add(""+period.getOID());
											periodKey.add(period.getPeriod());
										  }
										  %> <%=ControlCombo.draw("period",null,""+periodId,periodValue,periodKey)%>	
									  </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr> 
                                                <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Monthly Presence"></a></td>
                                                <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="94" class="command" nowrap><a href="javascript:cmdView()">View Monthly Presence</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  
									  <% if(iCommand == Command.LIST){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									    <tr>
											<td><hr></td>
										</tr>
                                        <tr>
											<td>
											<%
											out.println(listData);																						
											%>
											</td>
										</tr>
										<%if(dataStatus==DATA_PRINT && privPrint){%>
										<tr>
											<td>
											  <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  
                                                <td width="11%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Monthly Presence"></a></td>
												  
                                                <td width="89%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Monthly Presence</a></b> </td>
												</tr>
											  </table>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
