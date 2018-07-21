
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
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_MONTHLY_REPORT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
            //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<!-- update by devin 2014-01-29  -->
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
    /** 
     * untuk mencari lateness employee
     * @param timeIn
     * @param timeInSchedule
     * @return
     * @created by gadnyana 
     * edited by Edhy on August 18, 2004 
     */
    public Vector cekLateEmployee(Date timeIn, Date timeInSchedule,Date timeOut,Date timeOutSchedule) {
        
        Vector vlLate = new Vector(1, 1);

        try {
            // diproses jika "timeIn on schedule!=null" dan "time In on presence!=null"
            if (timeIn != null && timeInSchedule != null) {

                // waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
                // berisi data HOUR dan MINUTES 
                Date dtSchedule = new Date();
                if (timeInSchedule.getHours() == 0 && timeInSchedule.getMinutes() == 0) {
                    dtSchedule.setDate(timeIn.getDate() + 1);
                    dtSchedule.setMonth(timeIn.getMonth());
                    dtSchedule.setYear(timeIn.getYear());
                    dtSchedule.setHours(timeInSchedule.getHours());
                    dtSchedule.setMinutes(timeInSchedule.getMinutes() - SessEmployeeLateness.TIME_LATES);
                    dtSchedule.setSeconds(0);
                } else {
                    dtSchedule.setDate(timeIn.getDate());
                    dtSchedule.setMonth(timeIn.getMonth());
                    dtSchedule.setYear(timeIn.getYear());
                    dtSchedule.setHours(timeInSchedule.getHours());
                    dtSchedule.setMinutes(timeInSchedule.getMinutes() - SessEmployeeLateness.TIME_LATES);
                    dtSchedule.setSeconds(0);
                }

                // waktu time In on presence
                Date dtPresence = new Date();
                dtPresence.setTime(timeIn.getTime());
                dtPresence.setSeconds(0);

                // ambil selisih antara timeIn schedule dengan timeIn presence (dalam detik)
                long lTimeSchld = dtSchedule.getTime();
                long lTimeActual = dtPresence.getTime();
                long iDurationSec = lTimeSchld / 1000 - lTimeActual / 1000;


                // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                int intMult = (iDurationSec < -1) ? -1 : 1;

                // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                iDurationSec = iDurationSec * intMult;

                // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                long iDurationMin = 0;
                if (iDurationSec >= 60) {
                    iDurationMin = (iDurationSec - (iDurationSec % 60)) / 60;
                    iDurationSec = iDurationSec % 60;
                }

                // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                long iDurationHour = 0;
                if (iDurationMin >= 60) {
                    iDurationHour = (iDurationMin - (iDurationMin % 60)) / 60;
                    iDurationMin = iDurationMin % 60;
                    iDurationSec = iDurationSec % 60;
                }

                // memasukkan data hasil proses ke vector durasi jam
               if ((iDurationHour * intMult) < 0) {
                    vlLate.add("" + (iDurationHour * intMult));
                } else {
                    vlLate.add("0");
                }
                // memasukkan data hasil proses ke vector durasi menit
                if ((iDurationMin * intMult) < 0) {
                    vlLate.add("" + (iDurationMin * intMult));
                } else {
                    vlLate.add("0");
                }
            } // tidak diproses karena salah satu "timeIn on schedule" dan/atau "time In on presence"	adalah null	 
            else {
                vlLate.add("0");
                vlLate.add("0");
            }
            //update by satrya 2012-11-07
                if(timeOut != null && timeOutSchedule != null)
		{
			
			// waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
			// berisi data HOUR dan MINUTES 
			Date dtScheduleOut = new Date();			
			dtScheduleOut.setDate(timeOut.getDate());
			dtScheduleOut.setMonth(timeOut.getMonth());
			dtScheduleOut.setYear(timeOut.getYear());
			dtScheduleOut.setHours(timeOutSchedule.getHours());
			dtScheduleOut.setMinutes(timeOutSchedule.getMinutes()- SessEmployeeLateness.TIME_LATES);
			dtScheduleOut.setSeconds(0);
			
			// waktu time In on presence
			Date dtPresenceOut = new Date();
			dtPresenceOut.setTime(timeOut.getTime());
			dtPresenceOut.setSeconds(0);			
			
			// ambil selisih antara timeOut schedule dengan timeOut presence (dalam detik)
			long lTimeSchldOut = dtScheduleOut.getTime();
			long lTimeActualOut = dtPresenceOut.getTime();
			long iDurationSecOut =  lTimeActualOut/1000 - lTimeSchldOut/1000;
			

			// faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
			int intMultTo = (iDurationSecOut < -1) ? -1 : 1;
			
			// nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
			iDurationSecOut = iDurationSecOut * intMultTo;			
			
			// hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
			long iDurationMinOut = 0;			
			if(iDurationSecOut >= 60)
			{
				iDurationMinOut = (iDurationSecOut - (iDurationSecOut % 60)) / 60;
				iDurationSecOut = iDurationSecOut % 60;
			}
			
			// hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
			long iDurationHourOut = 0;			
			if(iDurationMinOut >= 60)
			{
				iDurationHourOut = (iDurationMinOut - (iDurationMinOut % 60)) / 60;
				iDurationMinOut = iDurationMinOut % 60;
				iDurationSecOut = iDurationSecOut % 60;				
			}

			// memasukkan data hasil proses ke vector durasi jam
			if( (iDurationHourOut * intMultTo) < 0 )
			{
				//strDurationHourOut = (iDurationHourOut * intMultTo) + "h, ";
                                vlLate.add("" + (iDurationHourOut * intMultTo)); 
              
			}
                        else {
                            vlLate.add("0");
                        }

			// memasukkan data hasil proses ke vector durasi menit
			if( (iDurationMinOut * intMultTo) < 0 )
			{
				//strDurationMinOut = (iDurationMinOut * intMultTo) +"m ";
                            vlLate.add("" + (iDurationMinOut * intMultTo));
			}else{
                            vlLate.add("0");
                        }
                         
			//vlLate.add(strDurationHourOut + strDurationMinOut); 
		}else{
                    vlLate.add("0");
                    vlLate.add("0");
                }
                
        } catch (Exception e) {
            System.out.println("Exc on cekLateEmployee : " + e.toString());
        }
        return vlLate;
    }

    public Vector drawList(JspWriter outObj,Vector listLate, Date dtSch, Date stDate, Date endDate,Vector listPresencePersonalInOut) {
        Vector result = new Vector(1, 1);
        ControlList ctrlist = new ControlList();
        //int max = endDate.getDate() - stDate.getDate();
        int max = (int)((endDate.getTime() - stDate.getTime())/(1000*60*60*24));

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
       // ctrlist.setHeaderStyle("listgentitle");
        ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        ctrlist.addHeader("No", "2%", "2", "0");
        ctrlist.addHeader("Payroll", "6%", "2", "0");
        ctrlist.addHeader("Employee", "10%", "2", "0");        
        ctrlist.addHeader("Duration (hour, minutes)", "70%", "1", "" + (max + 1) + "");

        //stDate.setHours(11);stDate.setMinutes(0);stDate.setSeconds(0);
        //endDate.setHours(11);endDate.setMinutes(0);endDate.setSeconds(0);
        
        Date tmpDate = new Date(stDate.getTime());
        Date tmpEndDate = new Date(endDate.getTime());
        Date dtTemp = new Date(stDate.getTime());
        while(dtTemp.before(tmpEndDate) || dtTemp.equals(tmpEndDate) ){
            int k=dtTemp.getDate();
            ctrlist.addHeader("" + k, "2%", "0", "0");
            dtTemp = new Date(dtTemp.getTime()+ 24*60*60*1000);
        }
        //ctrlist.addHeader("Total", "3%", "2", "0");
        ctrlist.addHeader("Total", "3%", "2", "0");
        ctrlist.addHeader("count", "3%", "2", "0");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        Vector list = new Vector(1, 1);
        int index = -1; 
        int num = 0;
      if(listLate !=null && listLate.size()>0){
       
           ctrlist.drawListHeaderWithJs(outObj);//header
        try{
        for (int i = 0; i < listLate.size(); i++) {
            
            LatenessMonthly latenessMonthly = (LatenessMonthly) listLate.get(i);
           
            String empNum = latenessMonthly.getEmpNum();
            String empName = latenessMonthly.getEmpName();
            Vector empSchedules1st = latenessMonthly.getEmpSchedules1st();
            Vector empSchedules2nd = latenessMonthly.getEmpSchedules2nd();
            Vector empActualIn1st = latenessMonthly.getEmpIn1st();
            Vector empActualIn2nd = latenessMonthly.getEmpIn2nd();
            Vector empActualOut1st = latenessMonthly.getEmpOut1st();
            Vector empActualOut2nd = latenessMonthly.getEmpOut2nd();

            Vector rowx = new Vector(1, 1);
            Vector pres = new Vector(1, 1);

            Vector vt1St = new Vector(1, 1);
            Vector vt2St = new Vector(1, 1);
            int totHour1st = 0;
            int totMenit1st = 0;
            int totHour2st = 0;
            int totMenit2st = 0;
            
            int totalCount = 0;
            //int kk = 0;
             Date dtSchDateTime = new Date();
              Date dtPresenceDateTime = new Date();
               
            //tmpDate = new Date(stDate.getTime());
            while(tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate) ){
            int kk=tmpDate.getDate()-1;
             //update by satrya 2012-11-10
              long temDurHourBo=0;
              long tempDurMinBi=0;
              long temDurSecBi=0;
              long temDurHourBi=0;
              String strDurationHourBo="";
              String strDurationMinBo="";
              String strDurationHourBi="";
              String strDurationMinBi="";
              long tempDurMinBo=0;
              long temDurSecBo=0;
              long durHourBi=0;
              long durMinBi=0;
              long durHourBo=0;
              long durMinBo=0;
               long durHourOUT=0;
              long durMinOUT=0;
               long durHourIN=0;
              long durMinIN=0;
              long lSchedulePersonal=0;
              long lActualPersonal=0;
              Date schPo = null;
              String sSchPo="";
              Date schPi = null; 
              String sSchPi="";
              String stimeIn1st="";
              String stimeOut1st="";
            
            long temDurHourIN= 0;
            long tempDurMinIN = 0;
            long temDurSecIN= 0;

            long temDurHourOUT= 0;
            long tempDurMinOUT = 0;
            long temDurSecOUT= 0;
            String strDurationHourIN="";
            String strDurationMinIN="";
             String strDurationHourOUT="";
            String strDurationMinOUT="";
              Vector hasil = new Vector(1,1);
              Vector InOut = new Vector(1,1); 
        if (false)  {    
         for (int idxPer=0; idxPer < listPresencePersonalInOut.size(); idxPer++){
                     Presence presenceBreak = (Presence) listPresencePersonalInOut.get(idxPer);
                     int schPresence = 0;
                     if(presenceBreak.getScheduleDatetime()!=null){
                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setDate(dtSchDateTime.getDate());
                            dtSchDateTime.setMonth(dtSchDateTime.getMonth());
                            dtSchDateTime.setYear(dtSchDateTime.getYear());
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes()- SessEmployeeLateness.TIME_LATES);
                            dtSchDateTime.setSeconds(0); 
                            schPresence = presenceBreak.getScheduleDatetime().getDate(); 
                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtPresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            dtPresenceDateTime.setHours(dtPresenceDateTime.getHours());
                            dtPresenceDateTime.setMinutes(dtPresenceDateTime.getMinutes());
                            dtPresenceDateTime.setSeconds(0);  
                         } 
                 if(presenceBreak.getScheduleDatetime() !=null
                       && presenceBreak.getEmployeeId()==latenessMonthly.getEmpId() 
                       &&(schPresence==tmpDate.getDate())){
                                  
                     if(presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL){    
                        
                           if(dtPresenceDateTime != null && dtSchDateTime != null){
                                    // ambil selisih antara Break Out presence dengan Schedule Break Out (dalam detik)
                                    lSchedulePersonal = dtSchDateTime.getTime(); 
                                   lActualPersonal = dtPresenceDateTime.getTime();
                                    long iDurationSecBo =  lActualPersonal/1000 - lSchedulePersonal/1000;
                                    schPo = dtSchDateTime; 
                                    // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                                    int intMultBo = (iDurationSecBo < -1) ? -1 : 1;

                                    // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                                    iDurationSecBo = iDurationSecBo* intMultBo;			

                                    // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                                    long iDurationMinBo = 0;			
                                    if(iDurationSecBo>= 60)
                                    {
                                            iDurationMinBo = (iDurationSecBo - (iDurationSecBo % 60)) / 60;
                                            
                                            iDurationSecBo = iDurationSecBo % 60;
                                           
                                    }

                                    // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                                    long iDurationHourBo = 0;			
                                    if(iDurationMinBo >= 60)
                                    {
                                            iDurationHourBo = (iDurationMinBo - (iDurationMinBo % 60)) / 60;
                                           
                                            iDurationMinBo = iDurationMinBo % 60;
                                            iDurationSecBo = iDurationSecBo % 60;				
                                    }
                                     temDurHourBo= iDurationHourBo;
                                     tempDurMinBo = iDurationMinBo;
                                     temDurSecBo= iDurationSecBo;
                                     
                                    //temDurHourBo= temDurHourBo+iDurationHourBo;
                                     //tempDurMinBo = tempDurMinBo+iDurationMinBo;
                                     //temDurSecBo= temDurSecBo+iDurationSecBo;
                                    

                                    // memasukkan data hasil proses ke vector durasi jam
                                    if( (temDurHourBo * intMultBo) < 0 )
                                    {
                                            strDurationHourBo = (temDurHourBo * intMultBo) + "h, ";
                                              durHourBo = (temDurHourBo * intMultBo);
                                                totHour1st = totHour1st + Integer.parseInt("" + durHourBo); // total 1st hour
                                    }

                                    // memasukkan data hasil proses ke vector durasi menit
                                    if( (tempDurMinBo * intMultBo) < 0 )
                                    {
                                            strDurationMinBo = (tempDurMinBo * intMultBo) +"m ";
                                              durMinBo = (tempDurMinBo * intMultBo);
                                              totMenit1st = totMenit1st + Integer.parseInt("" + durMinBo); // total 1st menit
                                    }

                                    hasil.add(strDurationHourBo + strDurationMinBo); 
                                    strDurationHourBo="";
                                    strDurationMinBo=""; 
                                  
                                    
                            }
                           else{
                                hasil.add("" + "");
                           }
                        listPresencePersonalInOut.remove(idxPer);
                        idxPer=idxPer-1;
                     }//end personal out
                     else if(presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL){
                           if(dtPresenceDateTime != null && dtSchDateTime != null){
                                    // ambil selisih antara Break Out presence dengan Schedule Break Out (dalam detik)
                                    lSchedulePersonal = dtSchDateTime.getTime(); 
                                   lActualPersonal = dtPresenceDateTime.getTime();
                                    long iDurationSecBi =   lSchedulePersonal/1000 - lActualPersonal/1000; 
                                    schPi = dtSchDateTime;
                                    // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                                    int intMultBi = (iDurationSecBi < -1) ? -1 : 1;

                                    // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                                    iDurationSecBi = iDurationSecBi* intMultBi;			

                                    // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                                    long iDurationMinBi = 0;			
                                    if(iDurationSecBi>= 60)
                                    {
                                            iDurationMinBi = (iDurationSecBi - (iDurationSecBi % 60)) / 60;
                                            
                                            iDurationSecBi = iDurationSecBi % 60;
                                           
                                    }

                                    // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                                    long iDurationHourBi = 0;			
                                    if(iDurationMinBi >= 60)
                                    {
                                            iDurationHourBi = (iDurationMinBi - (iDurationMinBi % 60)) / 60;
                                           
                                            iDurationMinBi = iDurationMinBi % 60;
                                            iDurationSecBi = iDurationSecBi % 60;				
                                    }
                                     temDurHourBi= iDurationHourBi;
                                     tempDurMinBi = iDurationMinBi;
                                     temDurSecBi= iDurationSecBi;
                                     
                                     //temDurHourBi= temDurHourBi+iDurationHourBi;
                                     //tempDurMinBi = tempDurMinBi+iDurationMinBi;
                                     //temDurSecBi= temDurSecBi+iDurationSecBi;
                                    // memasukkan data hasil proses ke vector durasi jam
                                    if( (temDurHourBi * intMultBi) < 0 )
                                    {
                                            strDurationHourBi = (temDurHourBi * intMultBi) + "h, ";
                                            durHourBi = (temDurHourBi* intMultBi);
                                             totHour1st = totHour1st + Integer.parseInt("" + durHourBi); // total 1st hour
                                    }

                                    // memasukkan data hasil proses ke vector durasi menit
                                    if( (tempDurMinBi * intMultBi) < 0 )
                                    {
                                            strDurationMinBi = (tempDurMinBi * intMultBi) +"m ";
                                            durMinBi =(tempDurMinBi * intMultBi);
                                             totMenit1st = totMenit1st + Integer.parseInt("" + durMinBi); // total 1st menit
                                    }

                                    hasil.add(strDurationHourBi + strDurationMinBi); 
                                    strDurationHourBi="";
                                    strDurationMinBi=""; 
                                   
                                   
                            }
                           else{
                                hasil.add("" + "");
                           }
                        listPresencePersonalInOut.remove(idxPer);
                        idxPer=idxPer-1;
                     }//end personal In
                 }//end cek yg sama
     }
    }//end listPresencePersonalInOut  
              
            //for (int x = stDate.getDate(); x <= endDate.getDate(); x++) {
                String formTime = "";
                String formTime2 = "";
                String formTimeOut ="";
                String formTimeOut2 ="";
                //update by satrya 2012-11-10
                String formTimeBO="";
             String formTimeBi="";
                Date timeIn = (Date) empActualIn1st.get(kk);
                Date actualIn2nd = (Date) empActualIn2nd.get(kk);

                Date timeOut = (Date) empActualOut1st.get(kk);
                Date actualOut2nd = (Date) empActualOut2nd.get(kk);

                // jika 2 shift
                if ((timeIn != null && timeOut != null) && (actualIn2nd != null && actualOut2nd != null)) {
                    long oidSch1St = Long.parseLong((String) empSchedules1st.get(kk));
                    long oidSch2St = Long.parseLong((String) empSchedules2nd.get(kk));

                    // get schedule 1 st
                    ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                    try {
                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch1St);
                    } catch (Exception e) {
                    }
                    Vector vect = cekLateEmployee(timeIn, scheduleSymbol.getTimeIn(),timeOut,scheduleSymbol.getTimeOut()); // value 1 st
                   if ((Integer.parseInt("" + vect.get(0)) != 0) || (Integer.parseInt("" + vect.get(1)) != 0)) {
                        if (Integer.parseInt("" + vect.get(0)) != 0) {
                            formTime = vect.get(0) + "h";
                        }

                        if (Integer.parseInt("" + vect.get(1)) != 0) {
                            if (formTime.length() > 0) {
                                if (formTime.length() > 3) {
                                    formTime = formTime + ", " + vect.get(1) + "m";
                                } else {
                                    formTime = formTime + ",  " + vect.get(1) + "m";
                                }
                            } else {
                                formTime = vect.get(1) + "m";
                            }
                        }
                        totHour1st = totHour1st + Integer.parseInt("" + vect.get(0)); // total 1st hour
                        totMenit1st = totMenit1st + Integer.parseInt("" + vect.get(1)); // total 1st menit
                    }

                    // get schedule 2 st
                    try {
                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch2St);
                    } catch (Exception e) {
                    }
                    vect = cekLateEmployee(actualIn2nd, scheduleSymbol.getTimeIn(),actualOut2nd,scheduleSymbol.getTimeOut());  // value 2st
                    if ((Integer.parseInt("" + vect.get(0)) != 0) || (Integer.parseInt("" + vect.get(1)) != 0)) {
                        if (Integer.parseInt("" + vect.get(0)) != 0) {
                            formTime2 = vect.get(0) + "h";
                        }
                        if (Integer.parseInt("" + vect.get(1)) != 0) {
                            if (formTime2.length() > 0) {
                                if (formTime.length() > 3) {
                                    formTime2 = formTime2 + ", " + vect.get(1) + "m";
                                } else {
                                    formTime2 = formTime2 + ", " + vect.get(1) + "m";
                                }
                            } else {
                                formTime2 = vect.get(1) + "m";
                            }
                        }
                        totHour2st = totHour2st + Integer.parseInt("" + vect.get(0)); // total 2st hour
                        totMenit2st = totMenit2st + Integer.parseInt("" + vect.get(1)); // total 2st menit
                    }

                    vt1St.add(formTime);
                    vt2St.add(formTime2);
                } 
                // 1 shift
                else if (timeIn != null || timeOut != null) {
                  long oidSch1St = Long.parseLong((String) empSchedules1st.get(kk));
                    // get schedule 1 st
                    ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                    try {
                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch1St);
                    } catch (Exception e) {
                    }
                    //update by satrya 2012-11-10
                if (timeIn != null && scheduleSymbol.getTimeIn() != null) {

                // waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
                // berisi data HOUR dan MINUTES 
                Date dtSchedule = new Date(); 
                if (scheduleSymbol.getTimeIn().getHours() == 0 && scheduleSymbol.getTimeIn().getMinutes() == 0) {
                    dtSchedule.setDate(timeIn.getDate() + 1);
                    dtSchedule.setMonth(timeIn.getMonth());
                    dtSchedule.setYear(timeIn.getYear());
                    dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                    dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                    dtSchedule.setSeconds(0);
                } else {
                    dtSchedule.setDate(timeIn.getDate());
                    dtSchedule.setMonth(timeIn.getMonth());
                    dtSchedule.setYear(timeIn.getYear());
                    dtSchedule.setHours(scheduleSymbol.getTimeIn().getHours());
                    dtSchedule.setMinutes(scheduleSymbol.getTimeIn().getMinutes() - SessEmployeeLateness.TIME_LATES);
                    dtSchedule.setSeconds(0);
                }

                // waktu time In on presence
                Date dtPresence = new Date();
                dtPresence.setTime(timeIn.getTime());
                dtPresence.setSeconds(0);

                // ambil selisih antara timeIn schedule dengan timeIn presence (dalam detik)
                long lTimeSchld = dtSchedule.getTime();
                long lTimeActual = dtPresence.getTime();
                long iDurationSec = lTimeSchld / 1000 - lTimeActual / 1000;


                // faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
                int intMult = (iDurationSec < -1) ? -1 : 1;

                // nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
                iDurationSec = iDurationSec * intMult;

                // hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
                long iDurationMin = 0;
                if (iDurationSec >= 60) {
                    iDurationMin = (iDurationSec - (iDurationSec % 60)) / 60;
                    iDurationSec = iDurationSec % 60;
                }

                // hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
                long iDurationHour = 0;
                if (iDurationMin >= 60) {
                    iDurationHour = (iDurationMin - (iDurationMin % 60)) / 60;
                    iDurationMin = iDurationMin % 60;
                    iDurationSec = iDurationSec % 60;
                }
                temDurHourIN= iDurationHour; 
                tempDurMinIN = iDurationMin;
                temDurSecIN = iDurationSec;
                if( (temDurHourIN * intMult) < 0 ) 
                {
                                strDurationHourIN = (temDurHourIN * intMult) + "h, ";
                                  durHourIN = (temDurHourIN * intMult);
                                  totHour1st = totHour1st + Integer.parseInt("" + durHourIN); // total 1st hour
                }

                // memasukkan data hasil proses ke vector durasi menit
                if( (tempDurMinIN * intMult) < 0 )
                {
                                strDurationMinIN = (tempDurMinIN * intMult) +"m ";
                                  durMinIN = (tempDurMinIN * intMult);
                                  totMenit1st = totMenit1st + Integer.parseInt("" + durMinIN); // total 1st menit
                }
                  InOut.add(strDurationHourIN + strDurationMinIN); 
                  
                if ((strDurationHourIN+strDurationMinIN).length() > 0 ){
                    totalCount = totalCount+1;
                }
                  strDurationHourIN="";
                  strDurationMinIN="";
                
            } // tidak diproses karena salah satu "timeIn on schedule" dan/atau "time In on presence"	adalah null	 
            else {
                InOut.add("" + "");
            }
                    
                     if(timeOut != null && scheduleSymbol.getTimeOut() != null)
		{
			
			// waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
			// berisi data HOUR dan MINUTES 
			Date dtScheduleOut = new Date();			
			dtScheduleOut.setDate(timeOut.getDate());
			dtScheduleOut.setMonth(timeOut.getMonth());
			dtScheduleOut.setYear(timeOut.getYear());
			dtScheduleOut.setHours(scheduleSymbol.getTimeOut().getHours());
			dtScheduleOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes()- SessEmployeeLateness.TIME_LATES);
			dtScheduleOut.setSeconds(0);
			
			// waktu time In on presence
			Date dtPresenceOut = new Date();
			dtPresenceOut.setTime(timeOut.getTime());
			dtPresenceOut.setSeconds(0);			
			
			// ambil selisih antara timeOut schedule dengan timeOut presence (dalam detik)
			long lTimeSchldOut = dtScheduleOut.getTime();
			long lTimeActualOut = dtPresenceOut.getTime();
			long iDurationSecOut =  lTimeActualOut/1000 - lTimeSchldOut/1000;
			

			// faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
			  int intMultTo = (iDurationSecOut < -1) ? -1 : 1;
			
			// nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
			iDurationSecOut = iDurationSecOut * intMultTo;			
			
			// hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
			long iDurationMinOut = 0;			
			if(iDurationSecOut >= 60)
			{
				iDurationMinOut = (iDurationSecOut - (iDurationSecOut % 60)) / 60;
				iDurationSecOut = iDurationSecOut % 60;
			}
			
			// hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
			long iDurationHourOut = 0;			
			if(iDurationMinOut >= 60)
			{
				iDurationHourOut = (iDurationMinOut - (iDurationMinOut % 60)) / 60;
				iDurationMinOut = iDurationMinOut % 60;
				iDurationSecOut = iDurationSecOut % 60;				
			}
                temDurHourOUT= iDurationHourOut; 
                tempDurMinOUT = iDurationMinOut;
                temDurSecOUT = iDurationSecOut;
                if( (temDurHourOUT * intMultTo) < 0 ) 
                {
                                strDurationHourOUT = (temDurHourOUT * intMultTo) + "h, ";
                                  durHourOUT = (temDurHourOUT * intMultTo);
                                  totHour1st = totHour1st + Integer.parseInt("" +durHourOUT); // total 1st hour
                }

                // memasukkan data hasil proses ke vector durasi menit
                if( (temDurHourOUT * intMultTo) < 0 )
                {
                                strDurationMinOUT = (tempDurMinOUT * intMultTo) +"m ";
                                  durMinOUT = (tempDurMinOUT * intMultTo);
                                  totMenit1st = totMenit1st + Integer.parseInt("" + durMinOUT); // total 1st menit
                }
                  InOut.add(strDurationHourOUT + strDurationMinOUT); 
                  strDurationHourOUT="";
                   strDurationMinOUT="";           
                
             
		}else{
                    InOut.add(""+"");
                }
                   if(InOut!=null && InOut.size()>0){
                       for(int idxSch=0; idxSch < InOut.size(); idxSch++){ 
                       if(idxSch%2==0){ 
                        if(InOut.get(idxSch)!=null) 
                            formTime = formTime + ((String) InOut.get(idxSch)); 
                       // hasil.remove(idx);
                       // idx = idx -1;
                       }else{
                        if(InOut.get(idxSch)!=null){
                            formTimeOut = formTimeOut + ((String) InOut.get(idxSch));  
                           // hasil.remove(idx);
                        }
                       }
                     }
                   }
                    //cek break In break out
                        if(hasil!=null && hasil.size()>0){
                      for(int idx=0; idx < hasil.size(); idx++){ 
                       if(idx%2==0){ 
                        if(hasil.get(idx)!=null) 
                            formTimeBO = formTimeBO + ((String) hasil.get(idx)); 
                       // hasil.remove(idx);
                       // idx = idx -1;
                       }else{
                        if(hasil.get(idx)!=null){
                            formTimeBi = formTimeBi + ((String) hasil.get(idx));  
                           // hasil.remove(idx);
                        }
                       }
                     }
                   }
                        

                   // Vector vect = cekLateEmployee(timeIn, scheduleSymbol.getTimeIn(),timeOut,scheduleSymbol.getTimeOut());  // value 1 st
                    /*if ((Integer.parseInt("" + vect.get(0)) != 0) || (Integer.parseInt("" + vect.get(1)) != 0)) {
                        if (Integer.parseInt("" + vect.get(0)) != 0) {
                            formTime = vect.get(0) + "h";
                        }
                        if (Integer.parseInt("" + vect.get(1)) != 0) {
                            if (formTime.length() > 0) {
                                if (formTime.length() > 3) {
                                    formTime = formTime + ", " + vect.get(1) + "m";
                                } else {
                                    formTime = formTime + ", " + vect.get(1) + "m";
                                }
                            } else {
                                formTime = vect.get(1) + "m";
                            }
                        }
                        totHour1st = totHour1st + Integer.parseInt("" + vect.get(0)); // total 1st hour
                        totMenit1st = totMenit1st + Integer.parseInt("" + vect.get(1)); // total 1st menit
                    }*/
                 
                    
                    
                    if(!(formTimeBO !=null && formTimeBO.length()>0)){
                        formTimeBO =".";  
                    }
                   if(!(formTimeBi !=null && formTimeBi.length()>0)){
                        formTimeBi = "."; 
                    }
                   if(!(formTime !=null && formTime.length()>0)){
                       formTime = ".";  
                   }
                   if(!(formTimeOut !=null && formTimeOut.length()>0)){
                       formTimeOut = "."; 
                   }
                   // vt1St.add(formTime+"/"+formTimeOut+"/"+formTimeBO+"/"+formTimeBi);
                    vt1St.add(formTime);
                    formTimeBi ="";
                    formTimeBO =""; 
                    vt2St.add("");
                } // jika schedule off, absence, DP, AL atau LL						
                else {
                    vt1St.add("OFF");
                    vt2St.add("");
                }

                //kk++;
                tmpDate = new Date(tmpDate.getTime()+ 24*60*60*1000);
               
            }

            boolean bool = false;
            num++;
            pres.add("" + 0);

            rowx.add("" + num);
            pres.add("" + num);

            rowx.add(empNum);
            pres.add(empNum);

            rowx.add(empName);
            pres.add(empName);

            for (int k = 0; k < vt1St.size(); k++) {
                rowx.add("" + vt1St.get(k));
                pres.add("" + vt1St.get(k));

                if (!vt2St.get(k).toString().equals("")) {
                    bool = true;
                }
            }

            String strTotal = "";
            if (totMenit1st != 0) {
                int jm = totMenit1st / 60;
                if (jm != 0) {
                    totHour1st = totHour1st + jm;
                    if ((totMenit1st % 60) != 0) {
                        totMenit1st = totMenit1st % 60;
                    }
                }
            }

            if (totHour1st != 0) {
                strTotal = totHour1st + "h";
            }
            if (totMenit1st != 0) {
                if (strTotal.length() > 0) {
                    if (strTotal.length() > 3) {
                        strTotal = strTotal + ", " + totMenit1st + "m";
                    } else {
                        strTotal = strTotal + ",  " + totMenit1st + "m";
                    }
                } else {
                    strTotal = totMenit1st + "m";
                }
            }

            rowx.add(strTotal);
            rowx.add(""+totalCount);
            pres.add(strTotal);
            pres.add(""+totalCount);

            lstData.add(rowx);
            list.add(pres);

            if (bool) {
                pres = new Vector(1, 1);
                rowx = new Vector(1, 1);
                pres.add("-1");

                rowx.add("");
                pres.add("");
                rowx.add("");
                pres.add("");
                rowx.add("");
                pres.add("");
                for (int k = 0; k < vt2St.size(); k++) {
                    rowx.add("" + vt2St.get(k));
                    pres.add("" + vt2St.get(k));
                }
                strTotal = "";
                if (totMenit2st != 0) {
                    int jm = totMenit2st / 60;
                    if (jm != 0) {
                        totHour2st = totHour2st + jm;
                        if ((totMenit2st % 60) != 0) {
                            totMenit2st = totMenit2st % 60;
                        }
                    }
                }

                if (totHour2st != 0) {
                    strTotal = totHour2st + "h";
                }
                if (totMenit2st != 0) {
                    if (strTotal.length() > 0) {
                        if (strTotal.length() > 3) {
                            strTotal = strTotal + ", " + totMenit2st + "m";
                        } else {
                            strTotal = strTotal + ",  " + totMenit2st + "m";
                        }
                    } else {
                        strTotal = totMenit1st + "m";
                    }
                }

                rowx.add(strTotal);
                rowx.add(""+totalCount);
                pres.add(strTotal);
                pres.add(""+totalCount);
                
                lstData.add(rowx);
                list.add(pres);
            }
             ctrlist.drawListRowJs(outObj, 0, rowx, i);
             tmpDate = new Date(stDate.getTime());
        }//end loop listLate
       } catch(Exception ex){
           System.out.println("Exception ListLate : " +ex.toString());
       }
      }//end jika lisLate kosong
        ctrlist.drawListEndTableJs(outObj);
        result.add("");//ctrlist.drawListFixedHeader(index));
        result.add(list);
        return result;
    }
%>

<%
            int iCommand = FRMQueryString.requestCommand(request);
            long oidDepartment = FRMQueryString.requestLong(request, "department");
            //update by devin 2014-01-29
            long oidCompany = FRMQueryString.requestLong(request, "hidden_companyId");
            long oidDivision = FRMQueryString.requestLong(request, "hidden_divisionId");
            long oidSection = FRMQueryString.requestLong(request, "section");
            int idx = FRMQueryString.requestInt(request, "week_idx");
            Date date = FRMQueryString.requestDate(request, "month_year");
            long periodId = FRMQueryString.requestLong(request, "period");
                String empNum = FRMQueryString.requestString(request, "emp_number");
                String fullName = FRMQueryString.requestString(request, "full_name");
            //update by satrya 2012-10-03
               LatenessMonthly latenessMonthly = new LatenessMonthly();
            Period objPeriod = new Period();
            try{
                  if(periodId!=0){
                    objPeriod=PstPeriod.fetchExc(periodId);
                    }
                  else{
                      objPeriod=PstPeriod.fetchExc(PstPeriod.getPeriodeIdBetween(new Date()));
                  }
            } catch (Exception exc){
                System.out.println("Exception period in latnessMontly"+ exc);
            }
            		try{
			Period pr = PstPeriod.fetchExc(periodId);
			date =  pr.getStartDate();
		}
		catch(Exception e){
                    System.out.println("Exception period in latnessMontly"+ e);
	}
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTime(date);
            //String startDatePrd = PstSystemProperty.getValueByName("START_DATE_PERIOD");
            Date dStartDateOfMonth = objPeriod.getStartDate()==null? new Date():objPeriod.getStartDate();//newCalendar.getTime();
            Date dEndDateOfMonth = objPeriod.getEndDate()==null?new Date():objPeriod.getEndDate();//new Date(dStartDateOfMonth.getYear(), dStartDateOfMonth.getMonth(), newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            //update by satrya 2012-11-11
            int startDatePrd = objPeriod.getStartDate()==null? new Date().getDate():objPeriod.getStartDate().getDate();
             int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
            Vector listLateness = new Vector(1, 1);
            Vector listPresencePersonalInOut= new Vector(1,1);
            if (iCommand == Command.LIST) {
                listLateness = SessEmployeeLateness.listLatenessDataMonthly(oidDepartment,oidCompany,oidDivision,oidSection,periodId,empNum.trim(),fullName.trim());
                  String order = "DATE("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, "
                        + "TIME("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, " 
                        + PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]
                        + " , "+ PstPresence.fieldNames[PstPresence.FLD_STATUS] + " ASC ";  
        
                listPresencePersonalInOut =  PstPresence.list(0 , 0, order, oidDepartment, fullName.trim(), dStartDateOfMonth, dEndDateOfMonth, oidSection, empNum.trim(),null,null,-1);
            }

           
%>

<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Report Lateness Monthly</title>
        <script language="JavaScript">
            function cmdView(){
                document.frpresence.command.value="<%=Command.LIST%>";
                document.frpresence.action="lateness_monthly_report.jsp";
                document.frpresence.submit();
            }
            //update by devin 2014-01-29
function cmdUpdateDiv(){
    document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frpresence.action="lateness_monthly_report.jsp";
    document.frpresence.target = "";
    document.frpresence.submit();
}
function cmdUpdatePos(){
    document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frpresence.action="lateness_monthly_report.jsp";
    document.frpresence.target = "";
    document.frpresence.submit();
}
            

 function cmdUpdateDep(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                 document.frpresence.action="lateness_monthly_report.jsp";
                 document.frpresence.target = "";
                 document.frpresence.submit();
            }


            
            function reportPdf(){
                var linkPage = "<%=printroot%>.report.employee.LatenessMonthlyPdf";
                window.open(linkPage,"reportWeeklyPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");
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
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" />
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
        </SCRIPT>
        <!-- update by devin 2014-01-29 -->
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
<!-- update by devin 2014-01-29 -->
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
        <!-- update by devin 2014-01-29 -->
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
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
     <style>
.fakeContainer { /* The parent container */
    margin: 0px;
    padding: 0px;
    border: none;
    width: 1310px; /* Required to set */
    height: 400px; /* Required to set */
    overflow: hidden; /* Required to set */
}
.skinCon {
    float: left;
    margin: 0px;
    border: none;
    width: 1310px;
}

</style>
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
                                        &gt; Lateness Monthly<!-- #EndEditable --> </strong></font> </td>
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
																					<!-- update by satrya 2012-10-03 -->
                                                                                        <tr>
                                            <!-- update by devin 2014-01-29 -->
                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Payrol Num </div></td>
                                                                                             <td width="30%" nowrap="nowrap">:
                                                                                               <input class="masterTooltip" type="text" size="40" name="emp_number"  value="<%= latenessMonthly.getEmpNum() != null ? latenessMonthly.getEmpNum() : empNum %>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                                                                            </td>
                                                                                             <td width="5%" nowrap="nowrap"><div align="right"> Full Name </div> </td>
                                                                                             <td width="59%" nowrap="nowrap">:
                                                                                             <input class="masterTooltip" type="text" size="50" name="full_name"  value="<%= latenessMonthly.getEmpName() != null ? latenessMonthly.getEmpName() : fullName%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                                                                             </td>
                                                                                        </tr>
                                                                                            
                                                                                            <!-- update by devin 2014-01-29 -->
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
                                                                                        
																					<!-- end -->
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
                                                                                              <td width="6%" nowrap="nowrap"><div align="left">Periode </div></td>
              <td width="30%" nowrap="nowrap">: <%//ControlDate.drawDateMY("month_year",date==null || iCommand == Command.NONE?new Date():date,"MMM yyyy","formElemen",0,installInterval)%>
                                                                                                <%
            Vector listPeriod = PstPeriod.list(0, 0, "", "start_date, end_date");
            Vector periodValue = new Vector(1, 1);
            Vector periodKey = new Vector(1, 1);
            //deptValue.add("0");
            //deptKey.add("ALL");

            for (int d = 0; d < listPeriod.size(); d++) {
                Period period = (Period) listPeriod.get(d);
                periodValue.add("" + period.getOID());
                periodKey.add(period.getPeriod());
            }
                                                                                                %> <%=ControlCombo.draw("period", null, "" + periodId, periodValue, periodKey)%>                                          
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="3%">&nbsp;</td>
                                                                                            <td width="9%" nowrap> <div align="left"></div></td>
                                                                                            <td width="88%"> <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                    <tr>
                                                                                                        <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Lateness"></a></td>
                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdView()">View
                                                                                                        Lateness</a></td>
                                                                                                    </tr>
                                                                                            </table></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <% if (iCommand == Command.LIST) {%>
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <% if (listLateness != null && listLateness.size() > 0) {%>
                                                                                        <tr><td><hr></td></tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%
try{
 Vector vLateness = drawList(out,listLateness, date, dStartDateOfMonth, dEndDateOfMonth,listPresencePersonalInOut);
            String drawList = "";
            Vector vctMonthlyLateness = new Vector(1, 1);
            if (vLateness != null && vLateness.size() > 0) {
                drawList = (String) vLateness.get(0);
                Vector list = (Vector) vLateness.get(1);
                vctMonthlyLateness.add(list);
            }
            else {
                vctMonthlyLateness.add(new Vector());
            }
            vctMonthlyLateness.add(dStartDateOfMonth);//1
            vctMonthlyLateness.add(dEndDateOfMonth);//2
            vctMonthlyLateness.add("" + oidDepartment);//3
            vctMonthlyLateness.add(""+oidSection);//4
            vctMonthlyLateness.add(objPeriod.getPeriod());//5
             
            
            /*GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, objPeriod.getStartDate().getYear()+1900);
            calendar.set(Calendar.MONTH, objPeriod.getStartDate().getMonth());*/
            //calendar.set(Calendar.YEAR, objPeriod.getEndDate().getYear()+1900);
            //calendar.set(Calendar.MONTH, objPeriod.getEndDate().getMonth());
           
            vctMonthlyLateness.add(""+dateOfMonth);//6  
            vctMonthlyLateness.add(""+startDatePrd);//7
            if (session.getValue("MONTHLY_LATENESS") != null) {
                session.removeValue("MONTHLY_LATENESS");
            }
            session.putValue("MONTHLY_LATENESS", vctMonthlyLateness);
}catch(Exception ex){
    System.out.println("exception list Late"+ex);

}                                                                                             
     //if (drawList != null && drawList.length() > 0) {
       //  out.println(drawList);
     ///} else {
        // out.println("<div class=\"msginfo\">&nbsp;&nbsp;No lateness data found ...</div>");
     //}
                                                                                                %>											
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (privPrint) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                                                                                    <tr>
                                                                                                        <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                                                                        <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
                                                                                                            Lateness</a></b>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                        <%} else {%>
                                                                                        <tr><td><hr></td></tr>
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
    <script type="text/javascript" src="<%=approot%>/javascripts/superTables.js"></script>
<script type="text/javascript">
//<![CDATA[

(function () {
    new superTable("demoTable", {
    	cssSkin : "sDefault",
        fixedCols : 3,
		headerRows : 2
    });
})();

//]]>
</script>
    </body>
    <!-- #BeginEditable "script" -->
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
