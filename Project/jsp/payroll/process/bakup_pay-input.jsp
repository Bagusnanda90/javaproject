 <%@page import="com.dimata.harisma.entity.overtime.Overtime"%>
<%@page import="com.dimata.harisma.entity.overtime.TableHitungOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.HashTblOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="com.dimata.harisma.printout.PayPrintText"%>
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>

<%@ page import = "com.dimata.util.*" %>

<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@ page import = "java.util.Date" %>


<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.SessEmpSchedule" %>


<%@ include file = "../../main/javainit_no_javasript.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%!
public String drawList(JspWriter outJsp,boolean privViewDetail, int iCommand, Vector objectClass,Hashtable sumOvertimeDailyDurIdx,long periodId,int dayOfMonth,String salaryLevel,double minOvtDuration,I_PayrollCalculator payrollCalculator ,int iPropInsentifLevel,int iPropNotCalculateALDPLL,Vector listAttdAbsensi,Hashtable  sumOvertimeMealAllowanceMoney,HttpServletRequest request/*,Hashtable hashCheked*/){ 
	String result = "";
	Vector token = new Vector(1,1);
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
             ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");  
            ctrlist.setHeaderStyle("listheaderJs");
	//mengambil nama dari kode komponent
		ctrlist.addHeader("No","5%", "2", "0");
		ctrlist.addHeader("Employee Nr.","5%", "2", "0");
		ctrlist.addHeader("Nama","12%", "2", "0");
		ctrlist.addHeader("Position","20%", "2", "0");
		ctrlist.addHeader("Working Day","12%", "0", "14");
		ctrlist.addHeader("Present On Time ","2%", "0", "0");
		ctrlist.addHeader("Paid Leave","4%", "0", "0");
		ctrlist.addHeader("Absent","2%", "0", "0");
		ctrlist.addHeader("Unpaid Lv","4%", "0", "0");
		ctrlist.addHeader("Not Ok / Late","2%", "0", "0");
		ctrlist.addHeader("Presence Ok (%)","2%", "0", "0");
                 //update by satrya 2013-02-20
                //sementara di hidden by satrya 2014-02-03 ctrlist.addHeader("Date OK With Leave","10%", "0", "0");
                ctrlist.addHeader("Insentif","10%", "0", "0");
                //sementara di hidden by satrya 2014-02-03 ctrlist.addHeader("Days OFF OT","10%", "0", "0");
                ctrlist.addHeader("Days OFF Sch","10%", "0", "0");
                
                //update by satrya 2013-05-06
                ctrlist.addHeader("Ov.Idx Paid Salary By Form","10%", "0", "0");
                ctrlist.addHeader("Ov.Idx Paid Salary Adjustment","10%", "0", "0");
               
                //update by satrya 2014-02-06
                ctrlist.addHeader("Money Allowance by Form","10%", "0", "0");
                ctrlist.addHeader("Money Allowance adj","10%", "0", "0");
                
                 ctrlist.addHeader("Private Note","10%", "0", "0");
		ctrlist.addHeader("Emp.Work Days","10%", "0", "0");
                
                
              if(privViewDetail){  
		ctrlist.addHeader("Income","10%", "2", "0");
		ctrlist.addHeader("Deduction","5%", "2", "0");
               }
		/*ctrlist.addHeader("Pph 21","10%", "2", "0");
		ctrlist.addHeader("Pph 26","10%", "2", "0");
		ctrlist.addHeader("Nett THP","10%", "2", "0");*/
                ctrlist.addHeader("Note Admin","10%", "2", "0");
                ctrlist.addHeader("Prosess <br> <a href=\"Javascript:SetAllCheckBoxesProsess('frm_input_pay', true)\">Select All</a> | <a href=\"Javascript:SetAllCheckBoxesProsess('frm_input_pay', false)\">Deselect All</a>","10%", "2", "0");
                
		ctrlist.addHeader("Final <br> <a href=\"Javascript:SetAllCheckBoxesLevel('frm_input_pay', true)\">Select All</a> | <a href=\"Javascript:SetAllCheckBoxesLevel('frm_input_pay', false)\">Deselect All</a>","10%", "2", "0");
                //update by satrya 2014-02-03
                ctrlist.addHeader("Adjustment <br> <a href=\"Javascript:SetAllCheckBoxesLevelAdjusment('frm_input_pay', true)\">Select All</a> | <a href=\"Javascript:SetAllCheckBoxesLevelAdjusment('frm_input_pay', false)\">Deselect All</a>","10%", "2", "0");
		ctrlist.addHeader("Note","20%", "2", "0");
	String checked = "";	
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
                //ctrlist.drawListHeaderWithJs(outJsp);
                ctrlist.drawListHeaderWithJsVer2(outJsp);//header
                Vector iVst = new Vector();
                iVst.add(new Integer(6));
                iVst.add(new Integer(9));
                iVst.add(new Integer(14));
                //iVst.add(new Integer(16));
                long paidLeaveOids[] =PstScheduleSymbol.getOidArrayScheduleDpAlLlSpecial();
                long specialLeaveOids[] =PstScheduleSymbol.getOidSpecial();
                long unpaidLeaveOids[] =PstScheduleSymbol.getOidArrayUnpaidLeave ();                                                               
		for(int i=0; i<objectClass.size(); i++){
                    
                    try{      

			int total = 0;
			Vector temp = (Vector)objectClass.get(i);
			Employee employee = (Employee)temp.get(0);
			PaySlip paySlip = (PaySlip)temp.get(1);
                         
                        if(employee==null ){ continue;}
                        
                        double otIdxByForm=0;
                        double otAllowanceMoney=0; 
                       
			/*  ambil presence summary*/
			//int presence = PstEmpSchedule.getStatusPresenceWithTimeInAndOut(employee.getOID(),periodId,dayOfMonth,PstEmpSchedule.STATUS_PRESENCE_OK);//PstEmpSchedule.getStatusPresence(employee.getOID(),periodId,dayOfMonth,PstEmpSchedule.STATUS_PRESENCE_OK);//                        
                        //update by satrya 20130216
                        int totalBenefit = 0;
			int totalDeduction=0;
                       // int presence = 0;
                        // int presenceX = 0;
                      /*   int dayAbsent=0;
                         int dayLate=0;
                        int absenceDisp = 0;
                        //int dayOffSchedule=0;  
                       int totDayOffOT=0; 
                       int dayOffOT=0;
                       int diffDay = 0; 
                       double insentif=0; 
                       int dateOkWithLeave=0; */
                       PayPeriod payPeriod = new PayPeriod(); 
                        // Period period = null;
                        try{
                            payPeriod= PstPayPeriod.fetchExc(periodId); 
                            //   period= PstPeriod.fetchExc(periodId);
                         } catch(Exception exc){                    
                             System.out.println("Period id ="+periodId + " not in system or could not fetched from database ! <br> "+exc);
                         } 
                      
                         //int iPositionLevel = PstPosition.iGetPositionLevel(employee.getOID());  
                        
                     if(iCommand!=Command.LIST){
                       /*int dayAbsent=0;
                       int dayLate=0;
                       int dateOkWithLeave=0;*/
                        /*if(listOvertime!=null && listOvertime.size()>0){
                            for(int idxOt=0;idxOt<listOvertime.size();idxOt++){
                                OvertimeDetail overtimeDetail = (OvertimeDetail)listOvertime.get(idxOt);
                                if(employee!=null && overtimeDetail.getEmployeeId()==employee.getOID()){
                                    otIdxByForm = overtimeDetail.getTot_Idx();
                                    listOvertime.remove(idxOt);
                                    idxOt = idxOt-1;
                                    break;
                                }
                            }
                        }*/
                        //update by satrya 2014-02-03
                        if(sumOvertimeDailyDurIdx!=null && sumOvertimeDailyDurIdx.size()>0 && employee.getOID()!=0 && sumOvertimeDailyDurIdx.get(employee.getOID())!=null){
                            TableHitungOvertimeDetail overtimeDetailDur = (TableHitungOvertimeDetail) sumOvertimeDailyDurIdx.get(employee.getOID());
                                                            
                                if (overtimeDetailDur.getEmployee_id() != 0 && overtimeDetailDur.getEmployee_id() == employee.getOID()) {

                                   // cell = row.createCell((short) collPos);
                                    /*otIdxByForm =0;
                                    if(overtimeDetailDur.getSizeDuration()!=0){
                                        for(int idxDur=0;idxDur<overtimeDetailDur.getSizeDuration();idxDur++){
                                               totDuration = totDuration + overtimeDetailDur.getvDuration(idxDur);

                                        }
                                    }*/
                                    otIdxByForm=0;
                                    if(overtimeDetailDur.getSizeTotIdx()!=0){
                                        for(int idxtot=0;idxtot<overtimeDetailDur.getSizeTotIdx();idxtot++){
                                               otIdxByForm = otIdxByForm + overtimeDetailDur.getvTotIdx(idxtot);
                                        }
                                    }
                                }
                        } 
                        //update by satrya 2014-02-06
                        if(sumOvertimeMealAllowanceMoney!=null && sumOvertimeMealAllowanceMoney.size()>0 && employee.getOID()!=0 && sumOvertimeMealAllowanceMoney.get(employee.getOID())!=null){
                            TableHitungOvertimeDetail overtimeDetailMoneyAllowance = (TableHitungOvertimeDetail) sumOvertimeMealAllowanceMoney.get(employee.getOID());
                                                            
                                if (overtimeDetailMoneyAllowance.getEmployee_id() != 0 && overtimeDetailMoneyAllowance.getEmployee_id() == employee.getOID()) {

                                   // cell = row.createCell((short) collPos);
                                    /*otIdxByForm =0;
                                    if(overtimeDetailDur.getSizeDuration()!=0){
                                        for(int idxDur=0;idxDur<overtimeDetailDur.getSizeDuration();idxDur++){
                                               totDuration = totDuration + overtimeDetailDur.getvDuration(idxDur);

                                        }
                                    }*/
                                    otAllowanceMoney=0;
                                    if(overtimeDetailMoneyAllowance.getSizeAllowanceMoney()!=0){
                                        for(int idxtot=0;idxtot<overtimeDetailMoneyAllowance.getSizeAllowanceMoney();idxtot++){
                                               otAllowanceMoney = otAllowanceMoney + overtimeDetailMoneyAllowance.getvAllowanceMoney(idxtot);
                                        }
                                    }
                                }
                        } 
                       int presenceOnTheTime=0;
                       int presenceDayOffSchedule=0;
                       int insentif =0;
                       int totalAbsce =0;
                       int totLate=0;
                         if(listAttdAbsensi!=null && listAttdAbsensi.size()>0){
                             for(int idx=0;idx<listAttdAbsensi.size();idx++){
                                  PayInputPresence payInputPresence =(PayInputPresence)listAttdAbsensi.get(idx);
                                  if(employee.getOID()==payInputPresence.getEmployeeId()){
                                       presenceOnTheTime = payInputPresence.getPresenceOnTime();
                                       presenceDayOffSchedule = payInputPresence.getDayOffSchedule();
                                       insentif = payInputPresence.getInsentif();
                                       totalAbsce = payInputPresence.getAbsence(); 
                                       totLate = payInputPresence.getLate();
                                        listAttdAbsensi.remove(idx);
                                        idx = idx -1; 
                                  }
                                 
                             }
                         
                         }
                           
                            // ambil untuk yang reasonnya abt,abrsk dan tgs ( karena reason ini dianggap karyawan masuk )
                            //masih belum terpakai, jadi di hidde by satrya 2013-02-18
                           // absenceDisp = PstEmpSchedule.getStatusDisp(employee.getOID(),period.getOID(),diffDay,PstEmpSchedule.STATUS_PRESENCE_ABSENCE,iVst,dtPeriodNew);
                            //absenceDisp = PstEmpSchedule.getStatusDisp(employee.getOID(),period.getOID(),diffDay,PstEmpSchedule.STATUS_PRESENCE_ABSENCE,iVst);
                            // absenceDisp = PstEmpSchedule.getStatusDisp(employee.getOID(),periodId,dayOfMonth,PstEmpSchedule.STATUS_PRESENCE_ABSENCE,iVst);
                            //absenceDisp = absenceDisp + absenceDispX;
                            //update by satrya 2013-02-16
                            //int absenceDisp = PstEmpSchedule.getStatusDisp(employee.getOID(),periodId,dayOfMonth,PstEmpSchedule.STATUS_PRESENCE_ABSENCE,iVst);
                             
                        //PstEmpSchedule.getStatusDisp(employee.getOID(),periodId,dayOfMonth,PstEmpSchedule.STATUS_PRESENCE_ABSENCE);
                        //mengambil total hari karyawan overtime pada saat karyawan tsb schedulnya off
                        //    Vector dates = PstEmpSchedule.getDateOfScheduleSymbol(employee.getOID(),period.getOID(),diffDay,vctSchIDOff,dtPeriodNew);
                       //     int dayOffOTX = PstOvertimeDetail.getTotalDatesOverTm(dates, period.getOID(), employee.getOID(), minOvtDuration);  
                        //mencari total date_ok_whit_leave
                           // int dateOkWithLeaveX = PstEmpSchedule.getStatusPresence(employee.getOID(),period.getOID(),diffDay,PstEmpSchedule.STATUS_PRESENCE_OK ,dtPeriodNew);
                           // dateOkWithLeave = dateOkWithLeave + dateOkWithLeaveX; 
                            
                        // presence = presence + absenceDisp;
			//System.out.println("absenceDisp......"+absenceDisp);
                        //dayAbsent  = dayAbsent  - absenceDisp;
			
			int maxNum = PstProcenPresence.getMaxNum();
			int minNum = PstProcenPresence.getMaxMin();
                        Vector listDate = new Vector();                        
                        try{
                          /* comment out by PAK TUT for test only */
			  listDate = SessEmpSchedule.getEmpAbsence(employee.getOID(),periodId); 
                         } catch(Exception exc){
                             System.out.println(""+exc);
                         }
                        
                        double dayPaidLv = (iPropNotCalculateALDPLL!=0? PstAlStockTaken.getAlQty(employee.getOID(), payPeriod.getStartDate(), payPeriod.getEndDate()) + 
                                PstLlStockTaken.getLlQty(employee.getOID(), payPeriod.getStartDate(), payPeriod.getEndDate()) +  
                                PstDpStockTaken.getDpQty(employee.getOID(), payPeriod.getStartDate(), payPeriod.getEndDate()):0) +
                                PstSpecialUnpaidLeaveTaken.getSpUnQty(employee.getOID(), payPeriod.getStartDate(), payPeriod.getEndDate(), specialLeaveOids)  ; //PstEmpSchedule.sumScheduleSymbolId(periodId, employee.getOID(),paidLeaveOids);
                        
                        double dayUnpaidLv = PstSpecialUnpaidLeaveTaken.getSpUnQty(employee.getOID(), payPeriod.getStartDate(), payPeriod.getEndDate(), unpaidLeaveOids)  ;//PstEmpSchedule.sumScheduleSymbolId(periodId, employee.getOID(),unpaidLeaveOids);
                                                
			//System.out.println("nilai listDate untuk"+employee.getOID()+" adalah "+listDate.size());
                        //di hidden di karenakan belum bisa
                        //by satrya 2013-02-20
			//double procentase = PstProcenPresence.getProcentase(listDate.size(),maxNum,minNum);
                        double procentase = 0;
                        try{
                            procentase=(presenceOnTheTime*100)/(presenceOnTheTime+totalAbsce+totLate);
                        }catch(Exception exc){
                            System.out.println("Exc prosentase"+exc);
                        }
			//System.out.println("iCommand...................."+iCommand);
			//update payslip dengan period dam employee id yang bersangkutany
                       int prosess= FRMQueryString.requestInt(request,"prosess"+i+"");  
                        
                        
			if(iCommand==Command.EDIT && prosess==1 /*&& hashCheked!=null && hashCheked.containsKey(employee.getOID())*/){
                                //hanya yg d centang
				PstPaySlip.updateWorkingDay(employee.getOID(),periodId,presenceOnTheTime,dayPaidLv, totalAbsce, dayUnpaidLv, totLate, PstPaySlip.NO_APPROVE,procentase,0,insentif,0,presenceDayOffSchedule,paySlip.getOvIdxAdj(),paySlip.getPrivateNote(),otIdxByForm,otAllowanceMoney,paySlip.getMealAllowanceMoneyAdj()); 
                                //update by satrya 2014-01-31 PstPaySlip.updateWorkingDay(employee.getOID(),periodId,presenceOnTheTime,dayPaidLv, totalAbsce, dayUnpaidLv, totLate, PstPaySlip.NO_APPROVE,procentase,dateOkWithLeave,insentif,dayOffOT,presenceDayOffSchedule,paySlip.getOvIdxAdj(),paySlip.getPrivateNote()); 
                                paySlip.setDayPresent(presenceOnTheTime);
                                paySlip.setDayAbsent(totalAbsce);
                                paySlip.setDayLate(totLate);
                                paySlip.setProcentasePresence((presenceOnTheTime*100)/(presenceOnTheTime+totalAbsce+totLate));//procentase);
                                //update by satrya 2013-02-20
                                paySlip.setDayPaidLv(dayPaidLv);
                                paySlip.setDayUnpaidLv(dayUnpaidLv);
                                paySlip.setInsentif(insentif);
                                /* update by satrya 2014-01-31 paySlip.setDaysOkWithLeave(dateOkWithLeave);
                                paySlip.setTotDayOffOt(dayOffOT);
                                paySlip.setDayOffSch(dayOffSchedule);*/
                                paySlip.setDaysOkWithLeave(0);
                                paySlip.setTotDayOffOt(0);
                                paySlip.setDayOffSch(presenceDayOffSchedule);
                                //UPDATE BY SATRYA 2014-02-06
                                paySlip.setMealAllowanceMoneyByForm(otIdxByForm);
                                
                                
			}
                    
                        
                    }
                          
			//mengambil payroll level karyawan
			String whereLevel = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]+"="+employee.getOID()+
								" AND " +PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+"="+PstPayEmpLevel.CURRENT;
			Vector vectLevel = PstPayEmpLevel.list(0,0,whereLevel,"");
			String levelCode="";
			if(vectLevel!=null && vectLevel.size() > 0) {
				PayEmpLevel level = (PayEmpLevel) vectLevel.get(0);
				levelCode = level.getLevelCode();
			}
			totalBenefit = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,(salaryLevel!=null && salaryLevel.length()>0?salaryLevel:levelCode),PstPayComponent.TYPE_BENEFIT,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY,0);
			totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,(salaryLevel!=null && salaryLevel.length()>0?salaryLevel:levelCode),PstPayComponent.TYPE_DEDUCTION,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY,0);
			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add(String.valueOf(1 + i));
				rowx.add(employee.getEmployeeNum()+"<input type=\"hidden\" name=\"employee_id\" value=\""+employee.getOID()+"\" class=\"formElemen\" size=\"10\">");
                                if(privViewDetail){ 
                                    rowx.add("<a href=\"javascript:cmdLevel('"+String.valueOf(employee.getOID())+"','"+String.valueOf(levelCode)+"','"+String.valueOf(paySlip.getOID())+"','1')\">"+employee.getFullName()+"</a>");
                                }else{
                                    rowx.add(employee.getFullName()); 
                                }
				      
				rowx.add(paySlip.getPosition()+ "<input type=\"hidden\" name=\"levelCode\" value=\""+levelCode+"\" class=\"formElemen\" size=\"10\">");
				rowx.add("<input type=\"text\" name=\"day_present\" value=\""+paySlip.getDayPresent()+"\" class=\"formElemen\" size=\"2\">");
				rowx.add("<input type=\"text\" name=\"day_paid_lv\" value=\""+paySlip.getDayPaidLv()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"day_absent\" value=\""+paySlip.getDayAbsent()+"\" class=\"formElemen\" size=\"2\">");
				rowx.add("<input type=\"text\" name=\"day_unpaid_lv\" value=\""+paySlip.getDayUnpaidLv()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"day_late\" value=\""+paySlip.getDayLate()+"\" class=\"formElemen\" size=\"2\">");
                                
				rowx.add("<div align = \"right\"><input type=\"text\" name=\"procen_presence\" value=\""+Formater.formatNumber(paySlip.getProcentasePresence(),frmCurrency)+"\" class=\"formElemen\" size=\"10\"></div>");
				
                                //update by satrya 2013-02-20
                                //sementara di hidden by satrya 2014-02-03rowx.add("<input type=\"text\" name=\"day_date_Ok_With_Leave\" value=\""+paySlip.getDaysOkWithLeave()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"day_insentif\" value=\""+paySlip.getInsentif()+"\" class=\"formElemen\" size=\"2\">");
				//sementara di hidden by satrya 2014-02-03 rowx.add("<input type=\"text\" name=\"day_Off_OT\" value=\""+paySlip.getTotDayOffOt()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"day_Off_Schedule\" value=\""+paySlip.getDayOffSch()+"\" class=\"formElemen\" size=\"2\">");
                                //update by satrya 2013-05-06
                                rowx.add(""+otIdxByForm+"<input type=\"hidden\" name=\"overtime_idx\" value=\""+otIdxByForm+"\" class=\"formElemen\" size=\"7\">");
                                rowx.add("<input type=\"text\" name=\"ov_idx_adjstment\" value=\""+paySlip.getOvIdxAdj()+"\" class=\"formElemen\" size=\"7\">");
                                //update by satrya 2014-02-06
                                 rowx.add(""+otAllowanceMoney+"<input type=\"hidden\" name=\"meal_allowance_money\" value=\""+otAllowanceMoney+"\" class=\"formElemen\" size=\"7\">");
                                rowx.add("<input type=\"text\" name=\"meal_allowance_money_adj\" value=\""+paySlip.getMealAllowanceMoneyAdj()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"private_note\" value=\""+paySlip.getPrivateNote()+"\" class=\"formElemen\" size=\"2\">");
                                double totalWorkingDay = (paySlip.getDayPresent()+paySlip.getDayLate());;//(paySlip.getDayPresent()+paySlip.getDayPaidLv())-(paySlip.getDayAbsent()+paySlip.getDayUnpaidLv()+paySlip.getDayLate());
				rowx.add(""+totalWorkingDay);

                                if(privViewDetail){ 
                                    rowx.add(""+Formater.formatNumber(totalBenefit, frmCurrency));
                                    rowx.add(""+Formater.formatNumber(totalDeduction, frmCurrency));
                                }
				
				/*rowx.add("");
				rowx.add("");
				rowx.add("");*/
                                if(paySlip.getOID()!=0){
                                    rowx.add("<center><a href=\"javascript:cmdNoteAdmin('"+paySlip.getOID()+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a></center>");
                                }else{
                                    rowx.add("");
                                }
                                rowx.add("<input type=\"checkbox\" name=\"prosess"+i+"\" value=\""+1+"\" class=\"formElemen\" size=\"10\"  >");
				if(paySlip.getStatus()==1){
					rowx.add("<input type=\"checkbox\" name=\"final"+i+"\" value=\""+employee.getOID()+"\" class=\"formElemen\" size=\"10\" checked >");
				}
				else{
					rowx.add("<input type=\"checkbox\" name=\"final"+i+"\" value=\""+employee.getOID()+"\" class=\"formElemen\" size=\"10\">"); 

				}
                                //update by satrya 2014-02-03
				rowx.add("<input type=\"checkbox\" name=\"adjustment"+i+"\" value=\""+1+"\" class=\"formElemen\" size=\"10\"  >");
				//rowx.add("<a href=\"javascript:cmdNote('"+employee.getOID()+"','"+periodId+"','"+paySlip.getOID()+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\"></a>");
				rowx.add(paySlip.getNote());

			}else{
				rowx.add(String.valueOf(1 + i));
				rowx.add(employee.getEmployeeNum()+"<input type=\"hidden\" name=\"employee_id\" value=\""+employee.getOID()+"\" class=\"formElemen\" size=\"2\">");
                                if(privViewDetail){
                                    rowx.add("<a href=\"javascript:cmdLevel('"+String.valueOf(employee.getOID())+"','"+String.valueOf(levelCode)+"','"+String.valueOf(paySlip.getOID())+"','1')\">"+employee.getFullName()+"</a>");
                                }else{
                                    rowx.add(employee.getFullName());
                                }
				rowx.add(paySlip.getPosition()+ "<input type=\"hidden\" name=\"levelCode\" value=\""+levelCode+"\" class=\"formElemen\" size=\"10\">");
				rowx.add("<input type=\"text\" name=\"day_present\" value=\""+paySlip.getDayPresent()+"\" class=\"formElemen\" size=\"2\">");
				rowx.add("<input type=\"text\" name=\"day_paid_lv\" value=\""+paySlip.getDayPaidLv()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"day_absent\" value=\""+paySlip.getDayAbsent()+"\" class=\"formElemen\" size=\"2\">");
				rowx.add("<input type=\"text\" name=\"day_unpaid_lv\" value=\""+paySlip.getDayUnpaidLv()+"\" class=\"formElemen\" size=\"7\">");
				
                                rowx.add("<input type=\"text\" name=\"day_late\" value=\""+paySlip.getDayLate()+"\" class=\"formElemen\" size=\"2\">");
                                
				rowx.add("<div align = \"right\"><input type=\"text\" name=\"procen_presence\" value=\""+Formater.formatNumber(paySlip.getProcentasePresence(),frmCurrency)+"\" class=\"formElemen\" size=\"2\"></div>");
				
                                //update by satrya 2013-02-20
                                //sementara di hidden by satrya 2014-02-03 rowx.add("<input type=\"text\" name=\"day_date_Ok_With_Leave\" value=\""+paySlip.getDaysOkWithLeave()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"day_insentif\" value=\""+paySlip.getInsentif()+"\" class=\"formElemen\" size=\"2\">");
				// sementara di hidden rowx.add("<input type=\"text\" name=\"day_Off_OT\" value=\""+paySlip.getTotDayOffOt()+"\" class=\"formElemen\" size=\"7\">");
				rowx.add("<input type=\"text\" name=\"day_Off_Schedule\" value=\""+paySlip.getDayOffSch()+"\" class=\"formElemen\" size=\"2\">");
                                //update by satrya 2013-05-06
                                rowx.add(""+otIdxByForm+"<input type=\"hidden\" name=\"overtime_idx\" value=\""+otIdxByForm+"\" class=\"formElemen\" size=\"7\">");
                                rowx.add("<input type=\"text\" name=\"ov_idx_adjstment\" value=\""+paySlip.getOvIdxAdj()+"\" class=\"formElemen\" size=\"7\">");
                                //update by satrya 2014-02-06
                                rowx.add(""+otAllowanceMoney+"<input type=\"hidden\" name=\"meal_allowance_money\" value=\""+otAllowanceMoney+"\" class=\"formElemen\" size=\"7\">");
                                rowx.add("<input type=\"text\" name=\"meal_allowance_money_adj\" value=\""+paySlip.getMealAllowanceMoneyAdj()+"\" class=\"formElemen\" size=\"7\">");
                                if(paySlip.getPrivateNote()==null){
                                    paySlip.setPrivateNote(""); 
                                }
				rowx.add("<input type=\"text\" name=\"private_note\" value=\""+paySlip.getPrivateNote()+"\" class=\"formElemen\" size=\"30\">");
                                double totalWorkingDay = (paySlip.getDayPresent()+paySlip.getDayLate());
				rowx.add(""+totalWorkingDay);
                                
                                if(privViewDetail){ 
                                    rowx.add(""+Formater.formatNumber(totalBenefit, frmCurrency));
                                    rowx.add(""+Formater.formatNumber(totalDeduction, frmCurrency));
                                }
				/*rowx.add("");
				rowx.add("");
				rowx.add("");*/
                                if(paySlip.getOID()!=0){
                                    rowx.add("<center><a href=\"javascript:cmdNoteAdmin('"+paySlip.getOID()+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a></center>");
                                }else{
                                    rowx.add("");
                                }
                                rowx.add("<input type=\"checkbox\" name=\"prosess"+i+"\" value=\""+1+"\" class=\"formElemen\" size=\"10\"  >");
				if(paySlip.getStatus()==1){
					rowx.add("<input type=\"checkbox\" name=\"final"+i+"\" value=\""+employee.getOID()+"\" class=\"formElemen\" size=\"10\" checked >");
				}
				else{
					rowx.add("<input type=\"checkbox\" name=\"final"+i+"\" value=\""+employee.getOID()+"\" class=\"formElemen\" size=\"10\">");

				}				//rowx.add("<a href=\"javascript:cmdNote('"+employee.getOID()+"','"+periodId+"','"+paySlip.getOID()+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\"></a>");
                                //update by satrya 2014-02-03
                                rowx.add("<input type=\"checkbox\" name=\"adjustment"+i+"\" value=\""+1+"\" class=\"formElemen\" size=\"10\"  >");
				rowx.add(paySlip.getNote());
			}
			//lstData.add(rowx);		
                        //ctrlist.drawListRowJs(outJsp, 0, rowx, i);
                        ctrlist.drawListRowJsVer2(outJsp, 0, rowx, i);
                    } catch(Exception exc){
                        System.out.println(exc);
                    }
		}
                    result ="";
                    //ctrlist.drawListEndTableJs(outJsp); //ctrlist.drawList(outJsp,0 ); //ctrlist.drawList(); 
                    //update by satrya 2014-02-25
                    ctrlist.drawListEndTableJsVer2(outJsp);
		}else{
			result = "<i>Belum ada data dalam sistem ...</i>";
		}
	return result;
}
%>
<%
     boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
            long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
            boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
            long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
            boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>


<%
		int iCommand = FRMQueryString.requestCommand(request);
                int noteCommand = FRMQueryString.requestInt(request, "noteCommand");
                String noteToAll = FRMQueryString.requestString(request,"noteToAll");
                String allLeftSeparator = FRMQueryString.requestString(request,"allLeftSeparator");
                String allLeftSeparatorNew = FRMQueryString.requestString(request,"allLeftSeparatorNew");
                if(allLeftSeparator==null || allLeftSeparator.length()==0){
                    allLeftSeparator="FYI:";
                }
                boolean privViewDetailX= privViewDetail;
                if(allLeftSeparatorNew==null || allLeftSeparatorNew.length()==0){
                    allLeftSeparatorNew="FYI:";
                }
                
                    //update by satrya 2013-07-08
        I_Atendance attdConfig = null;
               try {
                   attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
               } catch (Exception e) {
                   System.out.println("Exception : " + e.getMessage());
                   System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
               }
                    
		String levelCode = FRMQueryString.requestString(request,"level");
                //String levelCodeX = FRMQueryString.requestString(request,"levelCode");
                long companyId = FRMQueryString.requestLong(request,"comapanyId");
                long oidDivision = FRMQueryString.requestLong(request,"oidDivision");
                //long oidDepartment=FRMQueryString.requestLong(request,"oidDepartment");
               // long oidSection=FRMQueryString.requestLong(request,"oidSection");
		long departmentName = FRMQueryString.requestLong(request,"department");
		long sectionName = FRMQueryString.requestLong(request,"section");
		String searchNrFrom = FRMQueryString.requestString(request,"searchNrFrom");
		String searchNrTo = FRMQueryString.requestString(request,"searchNrTo");
		String searchName = FRMQueryString.requestString(request,"searchName");
		long periodId = FRMQueryString.requestLong(request,"periodId");
		int dataStatus = FRMQueryString.requestInt(request,"dataStatus");
               // Hashtable hashCheked = new Hashtable();
                
            
	Vector listPayInput = new Vector(1,1);
        Vector listOtIdx= new Vector(1,1);
         if(noteCommand== Command.SAVE){
             PstPaySlip.updateNote(periodId, noteToAll, allLeftSeparator, allLeftSeparatorNew);
         }
        /*mengambil nama period saat ini
Updated By Yunny*/
PayPeriod pr = new PayPeriod();
//Period pr = new Period();
String periodName ="";
Date startDate = new Date();
Double minOvtDuration=0.0;
 String strMinOvtDur = String.valueOf(PstSystemProperty.getValueByName("MIN_OVERTM_DURATION"));
 
 //update by satrya 2013-02-20
 String parollCalculatorClassName ="";    
I_PayrollCalculator  payrollCalculator = null;                

try{
        //update by satrya 2014-01-28
        if(periodId!=0){
            pr = PstPayPeriod.fetchExc(periodId); 
            periodName = pr.getPeriod();
            startDate = pr.getStartDate();
            if(strMinOvtDur!=null && strMinOvtDur.length()>0){
                minOvtDuration = Double.parseDouble(strMinOvtDur);
            }
        }
        //	pr = PstPeriod.fetchExc(periodId);
	
}
	catch(Exception e){
            System.out.println("Exception"+e);
}
try{
    parollCalculatorClassName = PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME");
    if(parollCalculatorClassName==null || parollCalculatorClassName.length()< 1 ){
        parollCalculatorClassName ="com.dimata.harisma.session.payroll.PayrollCalculator";    
    }
    payrollCalculator = (I_PayrollCalculator) (Class.forName(parollCalculatorClassName).newInstance());
    if(pr!=null){
        payrollCalculator.initializedPreloadedData(pr.getStartDate(), pr.getEndDate()); 
    }
    } catch(Exception exc){
        System.out.println(exc);
  }                        
  if(payrollCalculator==null){
      try{
        parollCalculatorClassName ="com.dimata.harisma.session.payroll.PayrollCalculator";                          
        payrollCalculator = (I_PayrollCalculator) (Class.forName(parollCalculatorClassName).newInstance());
      } catch(Exception exc){
        System.out.println(exc);
      }
  }
    int iPropInsentifLevel = 0;//hanya cuti full day jika fullDayLeave = 0
    try{
        iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
    }catch(Exception ex){

        //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        System.out.println("<blink>PAYROLL_INSENTIF_MAX_LEVEL NOT TO BE SET</blink>" );
    }
    int iPropNotCalculateALDPLL = 0;// 0 artinya tidak, sedngkan 1 artinya di hitung cuti AL,DP,LL
    try{
        iPropNotCalculateALDPLL = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_CALCULATE_UNPAID"));
    }catch(Exception ex){
        iPropNotCalculateALDPLL = 0;
        //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        System.out.println("<blink>PAYROLL_PAYROLL_CALCULATE_UNPAID NOT TO BE SET</blink>" );
    }
    
     long lHolidays=0;
        try{
            lHolidays = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
        }catch(Exception ex){
            System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        }
     
     I_PayrollCalculator payrollCalculatorConfig = null;
                    try{
                        payrollCalculatorConfig = (I_PayrollCalculator)(Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
        }catch(Exception e) {
           System.out.println("Exception PAYROLL_CALC_CLASS_NAME " + e.getMessage());
        }
    
   Vector listAttdAbsensi= new Vector();
   Hashtable sumOvertimeDailyPaidBySalary = new Hashtable();
   HashTblOvertimeDetail hashTblOvertimeDetail = new HashTblOvertimeDetail();
   Hashtable sumOvertimeDailyMoneyAllowance = new Hashtable();
	if(iCommand == Command.LIST  || iCommand == Command.ADD)
            //update by satrya 2014-02-03
            //if(iCommand == Command.LIST || iCommand==Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD) 
		{
			listPayInput = SessEmployee.listPayInput(departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,periodId,dataStatus); 
                        
                        //listOtIdx = PstOvertimeDetail.listGetCalculateTotIdx( departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,dataStatus,OvertimeDetail.PAID_BY_SALARY, pr.getStartDate(),pr.getEndDate());		
                        //SessPaySlip.generatePaySlip(periodId, (levelCode), companyId,oidDivision, departmentName, sectionName, searchNrFrom, searchNrTo, searchName, dataStatus); 
                        //update by satrya 2014-01-31
                         PayPeriod payPeriod = new PayPeriod(); 
                        // Period period = null;
                        try{
                            payPeriod= PstPayPeriod.fetchExc(periodId); 
                            //   period= PstPeriod.fetchExc(periodId);
                         } catch(Exception exc){                    
                             System.out.println("Period id ="+periodId + " not in system or could not fetched from database ! <br> "+exc);
                         } 
                        //Vector listPeriodDate = PstPeriod.getListStartEndDatePeriod(payPeriod.getStartDate(), payPeriod.getEndDate());                               
                        String getListEmployeeId = PstEmployee.getSEmployeeIdJoinSalary(0, 0,companyId,oidDivision, departmentName, sectionName, periodId, levelCode, searchNrFrom, searchNrTo, searchName, dataStatus, "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
                        sumOvertimeDailyMoneyAllowance = PstOvertimeDetail.summaryOTOffPayInput( pr.getStartDate(), pr.getEndDate(), getListEmployeeId, -1, -1, -1,  Overtime.ALLOWANCE_MONEY, 0);
                        sumOvertimeDailyPaidBySalary = PstOvertimeDetail.summaryOTOffPayInput(pr.getStartDate(), pr.getEndDate(), getListEmployeeId, OvertimeDetail.PAID_BY_SALARY, -1, -1, -1, 0);
                        
                         Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF); 
                         Hashtable hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
                          HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(payPeriod.getStartDate(), payPeriod.getEndDate()); 
                          Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();  
                          Hashtable hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, "", "");  
                        //selectedDateFrom, selectedDateTo, getListEmployeeId, 0, PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                           hashTblOvertimeDetail =PstOvertimeDetail.HashOvertimeOverlapVer2(0, 0, 0, getListEmployeeId, payPeriod.getStartDate(), payPeriod.getEndDate(),"");
                        listAttdAbsensi = PstEmpSchedule.getListAttendace(payPeriod.getStartDate(), payPeriod.getEndDate(), getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);    
                       
                        //payPeriod.getStartDate(), payPeriod.getEndDate(), getListEmployeeId, vctSchIDOff,hashSchOff, iPropInsentifLevel, holidaysTable, payrollCalculatorConfig
                       
		}

 if(iCommand==Command.EDIT){
listPayInput = SessEmployee.listPayInput(departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,periodId,dataStatus); 
                        //listOtIdx = PstOvertimeDetail.listGetCalculateTotIdx( departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,dataStatus,OvertimeDetail.PAID_BY_SALARY, pr.getStartDate(),pr.getEndDate());		
                        SessPaySlip.generatePaySlip(periodId, (levelCode), companyId,oidDivision, departmentName, sectionName, searchNrFrom, searchNrTo, searchName, dataStatus); 
                        //update by satrya 2014-01-31
                         PayPeriod payPeriod = new PayPeriod(); 
                        // Period period = null;
                        try{
                            payPeriod= PstPayPeriod.fetchExc(periodId); 
                            //   period= PstPeriod.fetchExc(periodId);
                         } catch(Exception exc){                    
                             System.out.println("Period id ="+periodId + " not in system or could not fetched from database ! <br> "+exc);
                         } 
                        //Vector listPeriodDate = PstPeriod.getListStartEndDatePeriod(payPeriod.getStartDate(), payPeriod.getEndDate());                               
                        String getListEmployeeId = PstEmployee.getSEmployeeIdJoinSalary(0, 0,companyId,oidDivision, departmentName, sectionName, periodId, levelCode, searchNrFrom, searchNrTo, searchName, dataStatus, "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
                        sumOvertimeDailyMoneyAllowance = PstOvertimeDetail.summaryOTOffPayInput( pr.getStartDate(), pr.getEndDate(), getListEmployeeId, -1, -1, -1,  Overtime.ALLOWANCE_MONEY, 0);
                        sumOvertimeDailyPaidBySalary = PstOvertimeDetail.summaryOTOffPayInput(pr.getStartDate(), pr.getEndDate(), getListEmployeeId, OvertimeDetail.PAID_BY_SALARY, -1, -1, -1, 0);
                        
                         Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF); 
                         Hashtable hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
                          HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(payPeriod.getStartDate(), payPeriod.getEndDate()); 
                          Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();  
                          Hashtable hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, "", "");  
                        //selectedDateFrom, selectedDateTo, getListEmployeeId, 0, PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                           hashTblOvertimeDetail =PstOvertimeDetail.HashOvertimeOverlapVer2(0, 0, 0, getListEmployeeId, payPeriod.getStartDate(), payPeriod.getEndDate(),"");
                        listAttdAbsensi = PstEmpSchedule.getListAttendace(payPeriod.getStartDate(), payPeriod.getEndDate(), getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);    
                       
	}

	String s_employee_id = null;
    String s_day_present= null;
    String s_day_paid_lv= null;
	String s_day_absent = null;
	String s_day_unpaid_lv = null;
	String s_day_late = null;
	String s_procen_presence = null;
	String s_note = null;
        //update by satrya 2013-02-20
        String s_date_ok_with_leave = null;
        String s_insentif = null; 
        String s_day_0ff_ot =null;
        String s_day_Off_schedule=null; 
	long  oidEmployee=0;
	int statusApprove = 0;
	// Jika tekan command Save
    if (iCommand == Command.SAVE) {
        //update by satrya 2014-02-04
        listPayInput = SessEmployee.listPayInput(departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,periodId,dataStatus); 
		String[] employee_id = null;		
        String[] day_present = null;
        String[] day_paid_lv= null;
		String[] day_absent = null;
		String[] day_unpaid_lv = null;
		String[] day_late = null;
		String[] procen_presence = null;
		String[] note = null;
                //update by satrya 2013-02-20
                String [] date_ok_with_leave = null;
                String [] day_insentif = null; 
                String [] day_0ff_ot =null;
                String [] day_Off_schedule=null; 
		String [] ov_idx_adjstment =null;
                String [] private_note=null; 
                //update by satrya 2014-02-06
                String [] overtime_idx =null;
                String [] meal_allowance_money=null; 
                String [] meal_allowance_money_adj=null; 
		// Inisialisasi variable yang meng-handle nilai2 berikut
		try {
			
			employee_id = request.getParameterValues("employee_id");
            day_present = request.getParameterValues("day_present");
            day_paid_lv = request.getParameterValues("day_paid_lv");
			day_absent = request.getParameterValues("day_absent");
			day_unpaid_lv = request.getParameterValues("day_unpaid_lv");
			day_late = request.getParameterValues("day_late");
			procen_presence = request.getParameterValues("procen_presence");
			note = request.getParameterValues("note");
                        //update by satrya 2013-02-20
                       
                        date_ok_with_leave = request.getParameterValues("day_date_Ok_With_Leave");
			day_insentif = request.getParameterValues("day_insentif");
			day_0ff_ot = request.getParameterValues("day_Off_OT");
			day_Off_schedule = request.getParameterValues("day_Off_Schedule");
			//update by satrya 2013-05-06
                        ov_idx_adjstment = request.getParameterValues("ov_idx_adjstment");
                        private_note = request.getParameterValues("private_note");
                        ///update by satrya 2014-02-06
                        overtime_idx= request.getParameterValues("overtime_idx");
                        meal_allowance_money = request.getParameterValues("meal_allowance_money");
                        meal_allowance_money_adj = request.getParameterValues("meal_allowance_money_adj");
			 
		 }
        catch (Exception e) 
		{
			System.out.println("Err : "+e.toString());
		}
		 
		for (int i = 0; i < listPayInput.size(); i++){
                            int cheked = FRMQueryString.requestInt(request, "adjustment"+i+""); 
                            oidEmployee = FRMQueryString.requestLong(request, "final"+i+""); 
                            if(cheked==1 || oidEmployee!=0){ 
                                    /*long employeeIdx=employee_id[i]!=null?Long.parseLong(employee_id[i]):0;
                                    if(employeeIdx!=0){ 
                                        hashCheked.put(employeeIdx, true);
                                    }*/
                                    
				try {
						
					   //oidEmployee = FRMQueryString.requestLong(request, "final"+i+""); 
					  /* s_employee_id = String.valueOf(employee_id[i]);
					   s_day_present = String.valueOf(day_present[i]);
					   s_day_paid_lv= String.valueOf(day_paid_lv[i]);
					   s_day_absent= String.valueOf(day_absent[i]);
					   s_day_unpaid_lv= String.valueOf(day_unpaid_lv[i]);
					   s_day_late= String.valueOf(day_late[i]);
					   s_note= String.valueOf(note[i]);*/
					  // out.println("oidEmployee   "+ oidEmployee);
                                    
					   if(oidEmployee!=0){
						statusApprove = PstPaySlip.YES_APPROVE;
						}
						else{
							statusApprove = PstPaySlip.NO_APPROVE;
						}
					   
					} catch (Exception e){
                                        }
					try{
						//update tabel
						//double cek = Double.parseDouble(day_present[i]);//.substring(0, day_present[i].indexOf(".")));
						PstPaySlip.updateWorkingDay(Long.parseLong(employee_id[i]),periodId,Double.parseDouble(day_present[i]),Double.parseDouble(day_paid_lv[i]),Double.parseDouble(day_absent[i]),Double.parseDouble(day_unpaid_lv[i]),Double.parseDouble(day_late[i]),statusApprove,Double.parseDouble(procen_presence[i])
                                                        ,(date_ok_with_leave==null?0:Double.parseDouble(date_ok_with_leave[i])),Double.parseDouble(day_insentif[i]),(day_0ff_ot==null?0:Double.parseDouble(day_0ff_ot[i])),Double.parseDouble(day_Off_schedule[i]),Double.parseDouble(ov_idx_adjstment[i]),String.valueOf(private_note[i]),((overtime_idx!=null?Double.parseDouble(overtime_idx[i]):0)),((meal_allowance_money!=null?Double.parseDouble(meal_allowance_money[i]):0)),((meal_allowance_money_adj!=null?Double.parseDouble(meal_allowance_money_adj[i]):0)));     
					}catch(Exception e){
                                            System.out.println("ERR"+e.toString());
                                        }
                              }
                                        
                                        
			}
                
			
			/*listPayInput = SessEmployee.listPayInput(departmentName,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,periodId,dataStatus);			
                        listOtIdx = PstOvertimeDetail.listGetCalculateTotIdx(departmentName,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,dataStatus,OvertimeDetail.PAID_BY_SALARY, pr.getStartDate(),pr.getEndDate());*/		
			listPayInput = SessEmployee.listPayInput(departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,periodId,dataStatus); 
                        //listOtIdx = PstOvertimeDetail.listGetCalculateTotIdx( departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,dataStatus,OvertimeDetail.PAID_BY_SALARY, pr.getStartDate(),pr.getEndDate());		
                        SessPaySlip.generatePaySlip(periodId, (levelCode), companyId,oidDivision, departmentName, sectionName, searchNrFrom, searchNrTo, searchName, dataStatus); 
                        //update by satrya 2014-01-31
                         PayPeriod payPeriod = new PayPeriod(); 
                        // Period period = null;
                        try{
                            payPeriod= PstPayPeriod.fetchExc(periodId); 
                            //   period= PstPeriod.fetchExc(periodId);
                         } catch(Exception exc){                    
                             System.out.println("Period id ="+periodId + " not in system or could not fetched from database ! <br> "+exc);
                         } 
                        //Vector listPeriodDate = PstPeriod.getListStartEndDatePeriod(payPeriod.getStartDate(), payPeriod.getEndDate());                               
                        String getListEmployeeId = PstEmployee.getSEmployeeIdJoinSalary(0, 0,companyId,oidDivision, departmentName, sectionName, periodId, levelCode, searchNrFrom, searchNrTo, searchName, dataStatus, "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
                        sumOvertimeDailyMoneyAllowance = PstOvertimeDetail.summaryOTOffPayInput( pr.getStartDate(), pr.getEndDate(), getListEmployeeId, -1, -1, -1,  Overtime.ALLOWANCE_MONEY, 0);
                        sumOvertimeDailyPaidBySalary = PstOvertimeDetail.summaryOTOffPayInput(pr.getStartDate(), pr.getEndDate(), getListEmployeeId, OvertimeDetail.PAID_BY_SALARY, -1, -1, -1, 0);
                        
                         Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF); 
                         Hashtable hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
                          HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(payPeriod.getStartDate(), payPeriod.getEndDate()); 
                          Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();  
                          Hashtable hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, "", "");  
                        //selectedDateFrom, selectedDateTo, getListEmployeeId, 0, PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                           hashTblOvertimeDetail =PstOvertimeDetail.HashOvertimeOverlapVer2(0, 0, 0, getListEmployeeId, payPeriod.getStartDate(), payPeriod.getEndDate(),"");
                        listAttdAbsensi = PstEmpSchedule.getListAttendace(payPeriod.getStartDate(), payPeriod.getEndDate(), getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);    
                       
	}
	
%>

<html>
<head>
<title>HARISMA - Salary Process - Input</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
	
  
<SCRIPT language=JavaScript>
            function noBack() { window.history.forward(); }
            function cmdUpdateDiv(){
                document.frm_input_pay.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_input_pay.action="pay-input.jsp";
                document.frm_input_pay.submit();
            }
            function cmdUpdateDep(){
                document.frm_input_pay.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_input_pay.action="pay-input.jsp";
                document.frm_input_pay.submit();
            }
            function cmdUpdatePos(){
                document.frm_input_pay.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_input_pay.action="pay-input.jsp";
                document.frm_input_pay.submit();
            }
function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function cmdSearch(){
	document.frm_input_pay.command.value="<%=Command.LIST%>";
	document.frm_input_pay.action="pay-input.jsp";
	document.frm_input_pay.submit();
}

function cmdSave(){
	document.frm_input_pay.command.value="<%=Command.SAVE%>";
	document.frm_input_pay.action="pay-input.jsp";
	document.frm_input_pay.submit();
}

function cmdEdit(){
	document.frm_input_pay.command.value="<%=Command.EDIT%>";
	document.frm_input_pay.action="pay-input.jsp";
	document.frm_input_pay.submit();
}

function setNote(){
	document.frm_input_pay.noteCommand.value="<%=Command.SAVE%>";    
	document.frm_input_pay.command.value="<%=Command.LIST%>";
	document.frm_input_pay.action="pay-input.jsp";
	document.frm_input_pay.submit();
}

function cmdNote(oid,periodId,payId){
	window.open("note_edit.jsp?command="+<%=Command.EDIT%>+"&hidden_leave_stock_id=" + oid+"&periodId=" + periodId+"&payId=" + payId, null,"height=300,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}

function cmdLevel(employeeId,salaryLevel,paySlipId,paySlipPeriod){
        var detailYN=document.frm_input_pay.detail_slip.value;
        //alert(document.frm_input_pay.detail_slip);
        //alert(detailYN);
        var periodId =document.frm_input_pay.periodId.value; 
        // document.frm_input_pay.target="PayrollDetail";
	// document.frm_input_pay.action="pay-input-detail.jsp?employeeId=" + employeeId+ "&salaryLevel=" + salaryLevel+"&paySlipId=" + paySlipId +"&paySlipPeriod=" + paySlipPeriod+"&detail_slip"+detailYN;
	// document.frm_input_pay.command.value="<%=Command.LIST%>";
	// document.frm_input_pay.submit();                
        var strUrl = "pay-input-detail.jsp?command="+<%=Command.LIST%>+"&employeeId=" + employeeId+ "&salaryLevel=" + 
               salaryLevel+"&paySlipId=" + paySlipId +"&paySlipPeriod=" + paySlipPeriod+"&detail_slip="+detailYN+"&periodId="+periodId;
          // alert(strUrl);
        var detailWindow = window.open(strUrl);
        //detailWindow.document.write("Detail Payroll");
        detailWindow.focus();  
}

function openLevel(){
    var strUrl ="sel_salary-level.jsp?frmname=frm_input_pay";
    var levelWindow = window.open(strUrl);
    levelWindow.focus();
}

function cmdNoteAdmin(hiddenOID){	
	window.open("note_edit_admin.jsp?command="+<%=Command.EDIT%>+"&hidden_payslip="+hiddenOID, null, "height=300,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}
function SetAllCheckBoxesLevelAdjusment(FormName, CheckValue){
	    if(!document.forms[FormName])
		return;
            <%  
               if(listPayInput!=null){ 
                for(int i = 0 ; i < listPayInput.size() ; i++){
                    String nameAdjm = "adjustment"+i;
                    %> //update by satrya 2014-02-03
                     document.forms[FormName].<%=nameAdjm%>.checked = CheckValue;
                    <%
                 }
               }else{
            %>
                    return;
                    <%}%>
        }

function SetAllCheckBoxesProsess(FormName, CheckValue){
	    if(!document.forms[FormName])
		return;
            <%  
               if(listPayInput!=null){ 
                for(int i = 0 ; i < listPayInput.size() ; i++){
                    String nameInp = "prosess"+i;                   
                    
                    %> document.forms[FormName].<%=nameInp%>.checked = CheckValue;
                      
                    <%
                 }
               }else{
            %>
                    return;
                    <%}%>
        }  
function SetAllCheckBoxesLevel(FormName, CheckValue){
	    if(!document.forms[FormName])
		return;
            <%  
               if(listPayInput!=null){ 
                for(int i = 0 ; i < listPayInput.size() ; i++){
                    String nameInp = "final"+i;                   
                    
                    %> document.forms[FormName].<%=nameInp%>.checked = CheckValue;
                      
                    <%
                 }
               }else{
            %>
                    return;
                    <%}%>
        }  

function clearLevelCode(){
    document.frm_input_pay.level.value="";
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

</head>

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">  
    <style>
.fakeContainer { /* The parent container */
    margin: 1px;
    padding: 1px;
    border: none;
    width: 100%; /* Required to set */
    height: 450px; /* Required to set */
    overflow: auto; /* Required to set */
}
.skinCon {
    float: inside;
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
      
      <%@ include file = "../../main/header.jsp" %>
      </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
      <%@ include file = "../../main/mnmain.jsp" %>
      </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> 
                        Payroll Prosess &gt; Salary Input </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" cellspacing="1" cellpadding="1" class="tabbg" >
                                <tr> 
                                  <td valign="top"> 
                                    <form name="frm_input_pay" method="post" action="">
									<input type="hidden" name="command" value="">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
											 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" width="39%" nowrap><b class="listtitle"><font size="3" color="#000000">Period &nbsp; &nbsp;
                                            
											<%
                                          Vector perValue = new Vector(1,1);
										  Vector perKey = new Vector(1,1);
										 // salkey.add(" ALL DEPARTMET");
										  //deptValue.add("0");
						                  Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC"); 
                                                                  //Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                          for(int r=0;r<listPeriod.size();r++){ 
										  	PayPeriod payPeriod = (PayPeriod)listPeriod.get(r);
                                                                                        //Period period = (Period)listPeriod.get(r);
											perValue.add(""+payPeriod.getOID()); 
											perKey.add(payPeriod.getPeriod());
										  }
										  %> <%=ControlCombo.draw("periodId",null,""+periodId,perValue,perKey,"")%>
										  </font></b></td>
                                          <td height="13" width="36%">&nbsp;</td>
                                          <td height="13" width="25%">&nbsp;</td>
                                          <td height="13" width="0%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="31" width="0%">&nbsp;</td>
                                          <td height="31" width="39%" nowrap>Salary Level 
                                              :  
                                              <input name="level" type="text" readonly="true" value="<%=levelCode%>"> <a href="javascript:openLevel();">Select</a>
                                              | <a href="javascript:clearLevelCode()">Clear</a>
                                             <%/*
                                              Vector listSalaryLevel = PstSalaryLevel.list(0, 0, "", " LEVEL_CODE, LEVEL_NAME");										  
                                              Vector salValue = new Vector(1,1);
                                              Vector salKey = new Vector(1,1);
                                                    salValue.add("0");
                                                    salKey.add("-All Level-");										  

                                              for(int d=0;d<listSalaryLevel.size();d++)
                                              {
                                                    SalaryLevel salLevel = (SalaryLevel)listSalaryLevel.get(d);
                                                    salValue.add(""+salLevel.getLevelCode());
                                                    salKey.add(""+salLevel.getLevelCode()+" "+ salLevel.getLevelName());										  
                                              }
                                              out.println(ControlCombo.draw("level",null,""+levelCode,salValue,salKey));
                                                 */    %>
                                          </td>
                                          <td>
                                              Company : 
                                              <%
                                                    Vector comp_value = new Vector(1, 1);
                                                    Vector comp_key = new Vector(1, 1);
                                                    String whereCompany="";
                                                    if (!(isHRDLogin || isEdpLogin || isGeneralManager)){
                                                        whereCompany = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"='"+ emplx.getCompanyId()+"'";
                                                    } else{
                                                        comp_value.add("0");
                                                        comp_key.add("select ...");                                            
                                                    }
                                                    Vector listComp = PstCompany.list(0, 0, whereCompany, " COMPANY ");
                                                    for (int i = 0; i < listComp.size(); i++) {
                                                            Company comp = (Company) listComp.get(i);
                                                            comp_key.add(comp.getCompany());
                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                    }
                                               //update by satrya 2013-08-13
                                               //jika user memilih select kembali
                                               if(companyId==0){
                                                   //srcOvertime = new SrcOvertime();
                                                   oidDivision = 0;
                                                   departmentName=0;
                                                   sectionName=0;
                                               }
                                    %> <%= ControlCombo.draw("comapanyId", "formElemen", null, "" + companyId, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> 
                                          </td>
                                          <td>
                                              Division : 
                                              <%
					Vector div_value = new Vector(1, 1);
					Vector div_key = new Vector(1, 1);
                                        String whereDivision ="";
                                        if (!(isHRDLogin || isEdpLogin || isGeneralManager)){
                                            whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"='"+ emplx.getDivisionId()+"'";
                                            oidDivision = emplx.getDivisionId();
                                        } else{
                                            div_value.add("0");
                                            div_key.add("select ...");                                            
                                        }
					Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
					for (int i = 0; i < listDiv.size(); i++) {
						Division div = (Division) listDiv.get(i);
						div_key.add(div.getDivision());
						div_value.add(String.valueOf(div.getOID()));
					}
                                   //update by satrya 2013-08-13
                                   //jika user memilih select kembali
                                   if(oidDivision==0){
                                       departmentName =0;
                                       sectionName=0;  
                                   }
			%> <%= ControlCombo.draw("oidDivision", "formElemen", null, "" + oidDivision, div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%>  
                                          </td>
                                          
                                          
                                          <td height="31" width="0%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td height="31" width="0%">&nbsp;</td>
                                            <td height="31" width="39%" nowrap>
                                                Department 
                                            : 
                                            <%
					Vector dept_value = new Vector(1, 1);
					Vector dept_key = new Vector(1, 1);
					//dept_value.add("0");
					//dept_key.add("select ...");
					//String strWhere = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
					Vector listDept = new Vector(); //PstDepartment.list(0, 0, strWhere, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
					/*for (int i = 0; i < listDept.size(); i++) {
						Department dept = (Department) listDept.get(i);
						dept_key.add(dept.getDepartment());
						dept_value.add(String.valueOf(dept.getOID()));
					}*/
            
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    
                    if (isHRDLogin || isEdpLogin || isGeneralManager){
                        String strWhere = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                        dept_value.add("0");
                        dept_key.add("select ...");                        
                        listDept = PstDepartment.list(0, 0, strWhere, "DEPARTMENT");
                        
                    } else {                        
                        Position position = new Position();
                        try{
                            position = PstPosition.fetchExc(emplx.getPositionId());
                        }catch(Exception exc){
                            
                        }
                                
                        String whereClsDep="(((hr_department.DEPARTMENT_ID = " + departmentOid+") "
                                + "AND hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision+") OR "
                                 + "(hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + "=" + departmentOid+"))";
                        
                        if(position.getOID()!=0 && position.getDisabedAppDivisionScope()==0){
                            whereClsDep=" ( hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision+") ";
                        }
                        
                        Vector SectionList = new Vector();
                        try {
                            String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                            Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);
                            
                            String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                            Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);

                            int grpIdx = -1;
                            int maxGrp = depGroup == null ? 0 : depGroup.size();
                            
                            int grpSecIdx = -1;
                            int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();
                                                        
                            int countIdx = 0;
                            int MAX_LOOP = 10;
                            int curr_loop = 0;
                            
                            int countIdxSec = 0;
                            int MAX_LOOPSec = 10;
                            int curr_loopSec = 0;
                            
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
                            
                            Vector idxSecGroup = new Vector();
                            
                            for(int x = 0; x < maxGrpSec ; x++){
                                
                                String[] grp = (String[]) depSecGroup.get(x);
                                for(int j = 0 ; j < 1 ; j++){
                                    
                                    String comp = grp[j];
                                    if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                        Counter counter = new Counter();
                                        counter.setCounter(x);
                                        idxSecGroup.add(counter);
                                    }                                    
                                }
                            }
                            
                            for(int s = 0 ; s < idxSecGroup.size() ; s++ ){
                                
                                Counter counter = (Counter)idxSecGroup.get(s);
                                
                                String[] grp = (String[]) depSecGroup.get(counter.getCounter());
                                
                                Section sec = new Section();
                                sec.setDepartmentId(Long.parseLong(grp[0]));
                                sec.setOID(Long.parseLong(grp[2]));
                                SectionList.add(sec);
                                
                            }
                            
                            // compose where clause
                            if(grpIdx>=0){
                                String[] grp = (String[]) depGroup.get(grpIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    whereClsDep=whereClsDep+ " OR (j.DEPARTMENT_ID = " + comp+")"; 
                                }                                     
                            }             
                            whereClsDep = " (" + whereClsDep + ") AND hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                        } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);
                        }

                        //dept_value.add("0");
                        //dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0,whereClsDep, "");
                        
                        for(int idx = 0 ; idx < SectionList.size() ; idx++){    
                            
                            Section sect = (Section)SectionList.get(idx);
                            
                            long sectionOid = 0;
                            
                            for(int z = 0 ; z < listDept.size() ; z++){
                            
                                Department dep = new Department();
                                
                                dep = (Department)listDept.get(z);
                                
                                if(sect.getDepartmentId() == dep.getOID()){
                                    
                                    sectionOid = sect.getOID();
                                    
                                }                                
                            }
                            
                            if(sectionOid != 0){
                                
                                Section lstSection = new Section();
                                Department lstDepartment = new Department();
                                
                                try{
                                    lstSection = PstSection.fetchExc(sectionOid);
                                }catch(Exception e){
                                    System.out.println("Exception "+e.toString());
                                }
                                
                                try{
                                    lstDepartment = PstDepartment.fetchExc(lstSection.getDepartmentId());
                                }catch(Exception e){
                                    System.out.println("Exception "+e.toString());
                                }
                                
                                listDept.add(lstDepartment);
                                
                            }
                        }                        
                    }
                } else {
                    dept_value.add("0");
                    dept_key.add("select ...");
                    listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision ) , "DEPARTMENT");
                }
            } else {
                dept_value.add("0");
                dept_key.add("select ...");
                listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision ) , "DEPARTMENT");
            }

            for (int i = 0; i < listDept.size(); i++) {
                Department dept = (Department) listDept.get(i);
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            }


            //update by satrya 2013-08-13
            //jika user memilih select kembali
            if(departmentName==0){
                sectionName=0; 
            }
			%> <%= ControlCombo.draw("department", "formElemen", null, "" + departmentName, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>
                                            </td>
                                            <td height="31" width="25%">Section 
                                           <%
               
					Vector sec_value = new Vector(1, 1);
					Vector sec_key = new Vector(1, 1);
					sec_value.add("0");
					sec_key.add("select ...");
					//Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
					String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + departmentName;
					Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
					for (int i = 0; i < listSec.size(); i++) {
						Section sec = (Section) listSec.get(i);
						sec_key.add(sec.getSection());
						sec_value.add(String.valueOf(sec.getOID()));
					}
			%> <%= ControlCombo.draw("section", "formElemen", null, "" + sectionName, sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="31" width="0%">&nbsp;</td>
                                          <td height="31" width="39%" nowrap>Employee.Nr 
                                            : 
                                            <input type="text" name="searchNrFrom" size="12" value="<%=searchNrFrom%>">
                                            to 
                                            <input type="text" name="searchNrTo" size="12" value="<%=searchNrTo%>">
                                          </td>
                                          <td height="31" width="36%">Name 
                                            <input type="text" name="searchName" size="20" value="<%=searchName%>">
                                          </td>
                                          <td height="31" width="25%">Status : 
                                            <%
															if(dataStatus==0){%>
													
																<input type="radio" name="dataStatus" value="0"checked >
																Draft
																<input type="radio" name="dataStatus" value="1">
																Final
																<input type="radio" name="dataStatus" value="2"  >
																All 
																<% } %>
																<%
															if(dataStatus==1){%>
													
																<input type="radio" name="dataStatus" value="0"  >
																Draft 
																<input type="radio" name="dataStatus" value="1" checked>
																Final
																<input type="radio" name="dataStatus" value="2" >
																All 
																<% } %>
																<%
																if(dataStatus==2){%>
																<input type="radio" name="dataStatus" value="0"  >
																Draft 
																<input type="radio" name="dataStatus" value="1"  >
																Final
																<input type="radio" name="dataStatus" value="2"  checked>
																All 
																<% } %>
											
											 </td>
                                          <td height="31" width="0%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                            <td width="39%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                           <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                          <a href="javascript:cmdSearch()">Search  for Employee</a></td>                                          
                                          <td height="13" width="25%">
                                              &nbsp;Note for all : Current Separator <input type="text" value="<%=allLeftSeparator%>" name="allLeftSeparator" size="5" >
                                               > New Separator <input type="text" value="<%=allLeftSeparatorNew%>" name="allLeftSeparatorNew" size="5" >
                                              <input size ="80" multiple="" type="text" value="<%=noteToAll %>" name="noteToAll"> <a href="javascript:setNote()">Set</a>
                                              <input type="hidden" name="noteCommand" value="0">
                                          </td>
                                          <td height="13" width="36%" nowrap>&nbsp;</td>
                                          <td height="13" width="0%">&nbsp;</td>
                                        </tr>                                                                                                                
                                        
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="4">&nbsp;</td>
                                        </tr>
										<tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="4"></td>
                                        </tr>
                                        <tr> 
										 <td height="13" width="0%">&nbsp;</td>
                                         
										  <%
										  //System.out.println("listPreData  "+listPreData.size());
										  if((listPayInput!=null)&&(listPayInput.size()>0)){
										  	Calendar newCalendar = Calendar.getInstance();
											newCalendar.setTime(startDate);
											int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
										  %>
											  <td colspan="6" height="8"><%=drawList(out, privViewDetailX,iCommand,listPayInput,sumOvertimeDailyPaidBySalary,periodId,dateOfMonth,(levelCode),minOvtDuration,payrollCalculator,iPropInsentifLevel,iPropNotCalculateALDPLL,listAttdAbsensi,sumOvertimeDailyMoneyAllowance,request /*,hashCheked*/)%></td> 
										  <% }
										  else{%>
										  	<tr> 
											<td>&nbsp;  </td>
                                          <td height="8" width="39%" class="comment"><span class="comment"><br>
                                            &nbsp;No Employee available</span> 
                                          </td>
                                        </tr>
										  <% }%>
                                          <td valign="top" colspan="4"> 
                                            
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle" width="0%">&nbsp;</td>
                                          <td class="listtitle" colspan="4">&nbsp;</td>
                                        </tr>
                                       
                                      </table>
									  <table width="100%" border="0">
									  <%
									  if((listPayInput!=null)&&(listPayInput.size()>0)){
									  %>
									 <tr>
											<td>
											 <img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Process Summary">
											<img src="<%=approot%>/images/spacer.gif" width="6" height="1">
											 <a href="javascript:cmdEdit()" class="command">Process Working Day Summary</a> &nbsp;&nbsp; &nbsp;&nbsp;
											
											 <img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data">
											<img src="<%=approot%>/images/spacer.gif" width="6" height="1">
											 <a href="javascript:cmdSave()" class="command">Save Adjusment OR Final Status</a> &nbsp;&nbsp; &nbsp;&nbsp;
											</td>
                                                                                   </tr>
									 <tr>
											<td>As open pay slip show details : <input name="detail_slip" type="radio" value="1"> Yes &nbsp;&nbsp;&nbsp; <input name="detail_slip" type="radio" value="0" checked> No</td>
                                                                                   </tr>                                                                                   
                                                                                   
										  <%
										  }//else {
										  %>
										  
										  <%
										 // }
										  %>
										</table>

                                    </form>
                                    </td>
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
            <!-- jangan menggunakan tanda sebelah kiri tsb jika menggunakan menu freezing karena error di IE<tr> 
                            <td valign="bottom">
                            
                                <%//@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>-->
            <%}else{%>
            <!--<tr> 
                <td colspan="2" height="20" > 
      <%//@ include file = "../../main/footer.jsp" %>
               </td>
            </tr>-->
            <%}%>
</table>

        <script type="text/javascript">
	    $(document).ready(function () {
	        gridviewScroll();
	    });
            <%
                int freesize=4;
                
            %>
	    function gridviewScroll() {
	        gridView1 = $('#GridView1').gridviewScroll({
                width: 1310,
                height: 500,
                railcolor: "##33AAFF",
                barcolor: "#CDCDCD",
                barhovercolor: "#606060",
                bgcolor: "##33AAFF",
                freezesize: <%=freesize%>,
                arrowsize: 30,
                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                headerrowcount: 2,
                railsize: 16,
                barsize: 15
            });
	    }
	</script>
</body>


</html>
