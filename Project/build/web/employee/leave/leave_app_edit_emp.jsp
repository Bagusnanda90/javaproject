<%-- 
    Document   : leave_app_edit_emp (copy dari leave_app_edit.jsp)
    Created on : Jan 20, 2015, 3:44:42 PM
    Author     : Dimata 007 [Hendra McHen]
--%>

<%@page import="com.dimata.harisma.form.search.FrmSrcLeaveApp"%>
<%@page import="com.dimata.qdep.db.DBHandler"%>
<%@page import="org.apache.log4j.helpers.DateTimeDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%-- 
    Document   : Leave_Req
    Created on : Dec 31, 2009, 10:33:46 AM
    Author     : Tu Roy
    History    : 9 Jan 2012 , modified by Kartika : to support time based( 30 minutes unit ) leave
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*"%>
<%@ page import = "com.dimata.harisma.form.attendance.*"%>
<%@ page import = "com.dimata.harisma.entity.employee.*"%>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*"%>
<%@ page import = "com.dimata.harisma.form.leave.*"%>
<%@ page import = "com.dimata.harisma.session.attendance.*"%>
<%@ page import = "com.dimata.harisma.session.leave.*"%>
<%@ page import = "com.dimata.harisma.session.employee.*"%>
<%@ page import = "com.dimata.harisma.entity.masterdata.*"%>
<%@ page  import = "com.dimata.harisma.entity.search.SrcLeaveManagement" %>
<%@ include file = "../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
%>
<%!
public void jspInit(){
    try{
    leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    }catch (Exception e){
    System.out.println("Exception : " + e.getMessage());
    }
    }
%>

<%!
public Date getDateSetTime(int hr, int mnt, int sec){
    Date dt= new Date();
    dt.setHours(hr);
    dt.setMinutes(mnt);
    dt.setSeconds(sec);
    return dt;
}
%>

<%! 
 static final int LEAVE_TYPE_APPLICATION=0;
 static final int LEAVE_TYPE_AL=1;
 static final int LEAVE_TYPE_LL=2;
 static final int LEAVE_TYPE_DP=3;
 static final int LEAVE_TYPE_SPECIAL_UNPAID=4;
 static final int STATUS_LEAVE_SPECIAL_LEAVE = 1;       
 static final int STATUS_LEAVE_UNPAID_LEAVE = 2;        

 I_Leave leaveConfig = null;
 
    /*
     * Membuat table detail leave
     * 
     * Parameter :
     *      ids = list symbol oid untuk annualleave
     *      names = list symbol name untuk annual leave
     *      cats = list category untuk symbol annual leave
     *      
     *      qty = list jumlah requested day tiap tipe leave berdasarkan schedule/table
     *      dates = vector list tanggal (sebanyak qty) untuk tiap requested day 
     *      saved = flag menyatakan data induk sudah disimpan/belum 
     *
     * Output :
     *      String : kode html untuk table detail leave 
     *   
     */

    public Vector drawListAnnualLeave(Vector vListAl, Vector vectorAlStockTaken,int indexAlGet, long oidEmployee, long oidLeave, int docStatus, java.util.Date requestDate,AlStockTaken alStockTaken){
            Vector result = new Vector();
            Vector vctDetails = new Vector();            
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            
            ctrlist.addHeader("No","5%");
            ctrlist.addHeader("Type of Leave.","10%");
            ctrlist.addHeader("Qty","5%");
            ctrlist.addHeader("Taken","5%");
            ctrlist.addHeader("Current Qty","10%");
            ctrlist.addHeader("To Be Taken","10%");
            ctrlist.addHeader("AL <br />Eligible","10%");
            ctrlist.addHeader("AL <br />Requested","10%");
            ctrlist.addHeader("Start Date", "10%");
            ctrlist.addHeader("End Date", "10%");
            ctrlist.addHeader("Action", "10%");
            String color = "2275E6";
  
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();            
            int i = 0;
            float eligbleDay = 0;
            float alQty =0;
            float totalAlTaken = 0;
            float curentAlQty = 0;
            float totalAl2BeTaken = 0;
        
            boolean notSamePeriodLeaveKonfig=false;
            try{
                 notSamePeriodLeaveKonfig= Boolean.parseBoolean(PstSystemProperty.getValueByName ("SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"));
            }catch(Exception exc){
                notSamePeriodLeaveKonfig=false;
                System.out.println("Exc SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"+exc);
            }
            for (int j=0; j<vListAl.size(); j++)
            {
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)vListAl.get(j);
                eligbleDay = item.getALTotal() - item.getALTaken() - item.getAL2BTaken();
                // eligbleDay = item.getALTotal() - item.getALTaken() - item.getAL2BTaken();
                alQty = item.getALTotal();
                totalAlTaken = item.getALTaken();
                curentAlQty = item.getALTotal()-item.getALTaken();
                totalAl2BeTaken = item.getAL2BTaken();
              }
               // eligbleDay = eligbleDay + currEligible;
               // totalAl2BeTaken = totalAl2BeTaken + currAl2BeTaken;
                
            boolean status_stock_not_exist = SessLeaveApplication.statusAlStockNotExist(oidEmployee);
           
           
            
            long alStockId = SessLeaveApplication.OIDALStockManagement(oidEmployee);
            boolean stsAlAktf = true;
            
            //Untuk pengecekan status cuti expired atau tidak
            /* Rule
                1. Pengecekan range waktu karyawan boleh mengambil cuti setelah commencing date
                2. Pengecekan apakah status minus bisa mengambil cuti
                3. Pengecekan untuk yang sudah expired akan di block, dan di berwarna merah
             */

            if(alStockId!= 0){            
                
                if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_COMMENCING){
                    stsAlAktf = SessLeaveApplication.getLastDayAlPeriod(alStockId,new Date());
                }else if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_PERIOD){
                    stsAlAktf = SessLeaveApplication.getStatusAlAktif(alStockId,new Date(),leaveConfig.getMonthClosingAnnual(),leaveConfig.getDateClosingAnnual());
                }else{
                    stsAlAktf = SessLeaveApplication.getStatusAlAktif(alStockId,new Date(),leaveConfig.getMonthPeriod(),leaveConfig.getDatePeriod());
                }
                
            }


            boolean expiredDtCanUpdate = false;

            try{
                /* Untuk pengeccekan expired date al stock*/
                expiredDtCanUpdate = SessLeaveApplication.cekExpiredDate(oidEmployee,leaveConfig,alStockTaken);
                //update by satrya 2014-01-02
                //expiredDtCanUpdate = SessLeaveApplication.cekExpiredDate(oidEmployee,leaveConfig);
            }catch(Exception E){
                System.out.println("[exception] "+E.toString());
            }

            /* Stock yang boleh di ambil*/
            
            boolean StsCanMinus = false;

            /* Logikanya :
                - klo stock minus, harus di cek apakah kondisi minus masih bisa mengambil cuti atau tidak
            */

            /*if(leaveConfig.getALStockMinus()){  // stock can minus
                //StsCanMinus = true; //can taken
                StsCanMinus = false;
            }else{
                if(eligbleDay > 0){ //stock can't minus
                    StsCanMinus = true; //can taken
                }else{
                    StsCanMinus = false; //can't taken
                }
                
            }*/
            //update by satrya 2012-08-16
            //hidden by satrya 2013-08-20 karena sudah ada di leaveConfig.getALStockMinus(eligbleDay)
            /* if(eligbleDay > 0){ //stock can't minus
                    StsCanMinus = true; //can taken
                }else{
                    StsCanMinus = false; //can't taken
            }*/
            if(leaveConfig!=null && leaveConfig.getALStockMinus(eligbleDay,oidEmployee)){
                    StsCanMinus = true; //can taken
            }else{
                    StsCanMinus = false; //can't taken
            }
            
                if(vectorAlStockTaken.size() > 0){                    
                    
                    String dateStart = "";
                    String dateFinnish ="";
                    int indexAl;
                    boolean statusEditAl = false;
                    String formatTakenDate="yyyy-MM-dd HH:mm";
                    //update by satrya 2012-10-12
                     AlStockTaken objAlStockTaken = null; 
                    for(indexAl = 1 ; indexAl <= vectorAlStockTaken.size(); indexAl ++){
                         long oidDepHeadAppr=0;
                           long oidLeaveDifferent=0;
                       objAlStockTaken = (AlStockTaken)vectorAlStockTaken.get(indexAl-1);                
                        //AlStockTaken objAlStockTaken = (AlStockTaken)vectorAlStockTaken.get(indexAl-1);                                           
                       try{
                        /* Untuk pengeccekan expired date al stock*/
                           //update by satrya 20140102
                        expiredDtCanUpdate = SessLeaveApplication.cekExpiredDate(oidEmployee,leaveConfig,objAlStockTaken);
                        //update by satrya 2014-01-02
                        //expiredDtCanUpdate = SessLeaveApplication.cekExpiredDate(oidEmployee,leaveConfig);
                    }catch(Exception E){
                        System.out.println("[exception] "+E.toString());
                    }
                        if(objAlStockTaken.getTakenDate().getHours()==objAlStockTaken.getTakenFinnishDate().getHours() &&
                           objAlStockTaken.getTakenDate().getMinutes()==objAlStockTaken.getTakenFinnishDate().getMinutes()){
                           formatTakenDate = "yyyy-MM-dd";
                        } else{
                            formatTakenDate = "yyyy-MM-dd HH:mm";
                        }
                       //devin 
                       try{
                                                
                            if(notSamePeriodLeaveKonfig){
                                long oid=objAlStockTaken.getLeaveApplicationId();
                                String where= PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = " + oid; 
                                Vector getData = PstLeaveApplication.list(0, 0, where, "");


                                LeaveApplication cuti =(LeaveApplication)getData.get(0);
                                oidDepHeadAppr= cuti.getDepHeadApproval();
                                oidLeaveDifferent = cuti.getLeaveAppDiffPeriod();
                           }
                       }catch(Exception exc ){
                           
                       }
                                                
                        dateStart   = objAlStockTaken.getTakenDate()==null ? "": Formater.formatDate(objAlStockTaken.getTakenDate(), formatTakenDate);    
                        dateFinnish = objAlStockTaken.getTakenFinnishDate()==null ? "": Formater.formatDate(objAlStockTaken.getTakenFinnishDate(), formatTakenDate);
                        
                        String ControlDatePopupTakenDate = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE],( objAlStockTaken.getTakenDate() == null ? new Date() /*new Date()*/ : objAlStockTaken.getTakenDate()),"getALStartDate()");//untuk AL Start Date
                        String ControlDatePopupFinnishDate = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE],( objAlStockTaken.getTakenFinnishDate() == null ? new Date() /*new Date()*/ : objAlStockTaken.getTakenFinnishDate()),"getALStartDate()");///untuk AL Finish Date
                        requestDate = objAlStockTaken.getTakenDate();
                        
                        
                        
                        Vector rowx = new Vector();
                        
                        if(indexAl == 1){
                            
                            i = i + 1;
                            rowx.add(""+i);
                            if(stsAlAktf == false){
                                rowx.add("<font color=FF0000>Annual Leave (Leave Expired)</font>");
                            }else{
                                rowx.add("Annual Leave");
                            }

                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            
                            if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                            }else{
                                rowx.add("");
                            }
                            
                            //Update By Agus 14-02-2014
                            if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                //update by devin 2014-04-09
                                 if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0 ){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifference(objAlStockTaken.getTakenDate() , objAlStockTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                    rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())); 
                                   
                                }else{ 
                            rowx.add(""+Formater.formatWorkDayHoursMinutes(objAlStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                }
                            }else{
                                rowx.add(""+Formater.formatWorkDayHoursMinutesII(objAlStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                            }
                            if(indexAlGet == indexAl){
                                statusEditAl = true;
                                //String addStyle = " alt=\"{type:'fixed',mask:'99:99',stripMask: false}\" ";                                                            
                                //String ctrTimeStart = DrsControlText.drawTextWithStyleLabel("Time","30%", FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE]+FrmAlStockTaken.TIME, Formater.formatDate(objAlStockTaken.getTakenDate(), "HH:mm", "HH:mm"), 15, 10, "iMask", addStyle, "");                                
                                //String ctrTimeEnd = DrsControlText.drawTextWithStyleLabel("Time","30%", FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE]+FrmAlStockTaken.TIME, Formater.formatDate(objAlStockTaken.getTakenFinnishDate(), "HH:mm", "HH:mm"), 15, 10, "iMask", addStyle, "");                                
                                String ctrTimeStart = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE], objAlStockTaken.getTakenDate(), "elemenForm", 24, 15, 0);
                                String ctrTimeEnd = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE], objAlStockTaken.getTakenFinnishDate(), "elemenForm", 24, 15, 0);
                                
                                rowx.add(""+ControlDatePopupTakenDate+" "+ctrTimeStart);
                                rowx.add(""+ControlDatePopupFinnishDate+" "+ctrTimeEnd);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            
                                            if(StsCanMinus){

                                                if(expiredDtCanUpdate){
                                                    rowx.add("<a href=\"javascript:cmdSaveAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelAL('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");
                                                }else{
                                                    rowx.add("");
                                                }
                                            }else{
                                                rowx.add("");

                                            }
                                        }else{
                                            rowx.add("");
                                        }
                                    }    
                                }
                            }else{
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add("<font color ="+color+">"+dateFinnish+"</font>");
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            if(StsCanMinus){
                                                if(expiredDtCanUpdate){
                                                    rowx.add("<a href=\"javascript:cmdEditAL('" + oidEmployee + "','"+oidLeave+"','"+indexAl+"')\">Edit</a> | <a href=\"javascript:cmdDeleteAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Delete</a>");
                                                }else{
                                                    rowx.add("");
                                                }
                                            }else{
                                                rowx.add("");
                                            }
                                        }else{
                                            rowx.add("");
                                        }
                                    }    
                                }    
                            }                            
                        }else{
                            i = i + 1;
                            rowx.add(""+i);
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                            }else{
                                rowx.add("");
                            }
                            
                            if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                //update by devin 2014-04-09
                                 if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifference(objAlStockTaken.getTakenDate() , objAlStockTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                    rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                   
                                }else{ 
                            rowx.add(""+Formater.formatWorkDayHoursMinutes(objAlStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                     }
                            }else{
                                rowx.add(""+Formater.formatWorkDayHoursMinutesII(objAlStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                            }
                            if(indexAlGet == indexAl){
                                statusEditAl = true;
                                String ctrTimeStart = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE], objAlStockTaken.getTakenDate(), "elemenForm", 24, 15, 0);
                                String ctrTimeEnd = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE], objAlStockTaken.getTakenFinnishDate(), "elemenForm", 24, 15, 0);
                                
                                rowx.add(""+ControlDatePopupTakenDate+" "+ctrTimeStart);
                                rowx.add(""+ControlDatePopupFinnishDate+" "+ctrTimeEnd);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            if(StsCanMinus){
                                                if(expiredDtCanUpdate){
                                                    rowx.add("<a href=\"javascript:cmdSaveAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelAL('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");
                                                }else{
                                                    rowx.add("");
                                                }
                                            }else{
                                                rowx.add("");
                                            }
                                        }else{
                                            rowx.add("");
                                        }                                        
                                    }    
                                }    
                            }else{
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add("<font color ="+color+">"+dateFinnish+"</font>");
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            if(StsCanMinus){
                                                if(expiredDtCanUpdate){
                                                    rowx.add("<a href=\"javascript:cmdEditAL('" + oidEmployee + "','"+oidLeave+"','"+indexAl+"')\">Edit</a> | <a href=\"javascript:cmdDeleteAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Delete</a>");
                                                }else{
                                                    rowx.add("");
                                                }

                                            }else{
                                                rowx.add("");
                                            }
                                        }else{
                                            rowx.add("");
                                        }                                       
                                    }   
                                }    
                            }
                        }                        
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                       
                    }//end loop
                    
                      
                    
                    Vector rowx = new Vector();
                    if(statusEditAl == false){
                        indexAl++;
                        i = i + 1;            
                        String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE],(requestDate), "getALStartDate()");///untuk AL START DATE
                        String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE],(requestDate),"getALEndDate()");///untuk AL FINISH DATE
                        rowx.add(""+i);
                        rowx.add("");
                        //Update By Agus 14-02-2012
                        if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(alQty, leaveConfig.getHourOneWorkday() , leaveConfig.getFormatLeave() ));
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(totalAlTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(curentAlQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(totalAl2BeTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                        }else{
                                if(stsAlAktf == false){
                                    rowx.add("<font color=ff0000>"+Formater.formatWorkDayHoursMinutes(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");
                                }else{
                                    rowx.add(""+Formater.formatWorkDayHoursMinutes(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                }  
                                
                        }
                        }else{
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(alQty, leaveConfig.getHourOneWorkday() , leaveConfig.getFormatLeave() ));
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(totalAlTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(curentAlQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(totalAl2BeTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                        }else{
                                if(stsAlAktf == false){
                                    rowx.add("<font color=ff0000>"+Formater.formatWorkDayHoursMinutesII(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");
                                }else{
                                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                }  
                                
                        }
                        }
                        rowx.add(""+0);
                        //String addStyle = " alt=\"{type:'fixed',mask:'99:99',stripMask: false}\" ";                                                            
                        //DrsControlText.drawTextWithStyleLabel("Time","30%", FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE]+FrmAlStockTaken.TIME, Formater.formatDate(new Date(), "HH:mm", "HH:mm"), 15, 10, "iMask", addStyle, "");                                
                        //DrsControlText.drawTextWithStyleLabel("Time","30%", FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE]+FrmAlStockTaken.TIME, Formater.formatDate(new Date(), "HH:mm", "HH:mm"), 15, 10, "iMask", addStyle, "");                                

                        String ctrTimeStart = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE], getDateSetTime(0,0,0), "elemenForm", 24, 15, 0);
                        String ctrTimeEnd = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE], getDateSetTime(0,0,0), "elemenForm", 24, 15, 0);
                        //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{ rowx.add(""+ControlDatePopupTaken+" "+ctrTimeStart);}
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{rowx.add(""+ControlDatePopupFinnish+" "+ctrTimeEnd);}//end
                        if(oidEmployee == 0){
                            rowx.add("");
                        }else{
                            if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("");
                            }else{
                                if(stsAlAktf == false){
                                    rowx.add("");
                                }else{
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        if(StsCanMinus){
                                            if(expiredDtCanUpdate){
                                                rowx.add("<a href=\"javascript:cmdSaveAL('0','0')\">Save</a>");
                                            }else{
                                                rowx.add("");
                                            }
                                        }else{
                                            rowx.add("");
                                        }
                                    }else{
                                        rowx.add("");
                                    }
                                }
                            }    
                        }    
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                    }
                }else{
               
                    String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE],(requestDate), "getALStartDate()");//untuk set date
                    String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE],(requestDate),"getALEndDate()");//untuk set date
                    // String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE],(requestDate !=null ? requestDate : getDateSetTime(0,0,0)),"getALEndDate()");//untuk set date
                    
                    i = i+1;                    
                    Vector rowx = new Vector();
                    rowx.add(""+i);
                    if(stsAlAktf == false){
                        rowx.add("<font color=FF0000>Annual Leave (Leave Expired)</font>");
                    }else{
                        rowx.add("Annual Leave");
                    }

                    //Update By Agus 14-02-2014
                    if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutes(alQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutes(totalAlTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutes(curentAlQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutes(totalAl2BeTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));

                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                          rowx.add("Stock Empty");
                    }else{
                          if(stsAlAktf == false){  
                              rowx.add("<font color=FF0000>"+Formater.formatWorkDayHoursMinutes(eligbleDay,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");  
                          }else{
                              rowx.add(""+Formater.formatWorkDayHoursMinutes(eligbleDay,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                          }    
                    }
                    }else{
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutes(alQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutesII(totalAlTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutesII(curentAlQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                    rowx.add(leaveConfig==null?"":""+Formater.formatWorkDayHoursMinutesII(totalAl2BeTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));

                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                          rowx.add("Stock Empty");
                    }else{
                          if(stsAlAktf == false){  
                              rowx.add("<font color=FF0000>"+Formater.formatWorkDayHoursMinutesII(eligbleDay,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");  
                          }else{
                              rowx.add(""+Formater.formatWorkDayHoursMinutesII(eligbleDay,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                          }    
                    }
                    }


                    String addStyle = " alt=\"{type:'fixed',mask:'99:99',stripMask: false}\" ";                                                            
                    //String ctrTimeStart = DrsControlText.drawTextWithStyleLabel("Time","30%", FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE]+FrmAlStockTaken.TIME, Formater.formatDate(new Date(), "HH:mm", "HH:mm"), 15, 10, "iMask", addStyle, "");
                    //String ctrTimeEnd = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE], requestDate !=null ? requestDate  :getDateSetTime(0,0,0), "elemenForm", 24, 15, 0);
                    String ctrTimeStart = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE],requestDate, "elemenForm", 24, 15, 0);
                    String ctrTimeEnd = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE], requestDate, "elemenForm", 24, 15, 0);
                                                                                   
                    rowx.add(""+0);
                     //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{  rowx.add(""+ControlDatePopupTaken+" "+ctrTimeStart);}
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{rowx.add(""+ControlDatePopupFinnish+" "+ctrTimeEnd);}//end
                    if(oidEmployee == 0){
                        rowx.add("");
                    }else{
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                            rowx.add("");
                        }else{
                            if(stsAlAktf == false){
                                rowx.add("");
                            }else{
                                if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                     //update by satrya 2012-08-15
                                    if(StsCanMinus){
                                       //  if(StsCanMinus){
                                        rowx.add("<a href=\"javascript:cmdSaveAL('0','0')\">Save</a>");
                                    }else{
                                        rowx.add("No AL eligible!");
                                    }
                                }else{
                                    rowx.add("");
                                }
                            }
                        }    
                    }    
                    lstData.add(rowx);                      
                    vctDetails.add(rowx);
                } 
           
            result.add(ctrlist.draw());
            result.add(vctDetails);
            
            return result;
	}
    
    
        public Vector drawListLongLeave(Vector vListAl, Vector vectorLlStockTaken,long oidEmployee, long oidLeave, int indexLlget, int docStatus, java.util.Date requestDate){            
            Vector result = new Vector();
            Vector vctDetails = new Vector();            
        
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            
            ctrlist.addHeader("No","5%");
            ctrlist.addHeader("Type of Leave.","10%");
            ctrlist.addHeader("Qty","5%");
            ctrlist.addHeader("Taken","5%");
            ctrlist.addHeader("Current Qty","10%");
            ctrlist.addHeader("To Be Taken","10%");
            ctrlist.addHeader("No. of Days <br />Eligible","10%");
            ctrlist.addHeader("No. of Days <br />Requested","10%");
            ctrlist.addHeader("Start Date", "10%");
            ctrlist.addHeader("End Date", "10%");
            ctrlist.addHeader("Action", "10%");
  
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();
            
            String color = "2275E6";
            
            int i = 0;
            boolean statusEditLl = false;                                     
            int indexLl = 0;
            float eligbleDay = 0;
            float llQty = 0;
            float totalLlTaken = 0;
            float currentLLQty = 0;
            float toBeTakenLL = 0;

            boolean notSamePeriodLeaveKonfig=false;
            try{
                 notSamePeriodLeaveKonfig= Boolean.parseBoolean(PstSystemProperty.getValueByName ("SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"));
            }catch(Exception exc){
                notSamePeriodLeaveKonfig=false;
                System.out.println("Exc SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"+exc);
            }
            for (int j=0; j<vListAl.size(); j++)
            {
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)vListAl.get(j);
                eligbleDay = item.getLLQty() - item.getLLTaken() - item.getLL2BTaken();
                llQty = item.getLLQty();
                totalLlTaken = item.getLLTaken();
                currentLLQty = item.getLLQty()-item.getLLTaken();
                toBeTakenLL = item.getLL2BTaken();
              }
            
            boolean status_stock_not_exist = false;
            
            status_stock_not_exist = SessLeaveApplication.statusLlStockNotExist(oidEmployee);
                    
           // eligbleDay = SessLeaveApplication.getLlEligbleDay(oidEmployee);
            
            boolean dtaLLExpired = false;  //FALSE - > NOT EXPIRED ; TRUE - > EXPIRED
            
            dtaLLExpired = SessLeaveApplication.getStatusLLExpired(oidEmployee);
            
            if(vectorLlStockTaken.size() > 0){
                    
                    float request = 0;
                    float eligible = 0;
                    String tanggal = "";
                    String dateStart ="";
                    String dateFinnish ="";
                    long oidDepHeadAppr=0;
                    long oidLeaveDifferent=0;
                    
                    for(indexLl = 1 ; indexLl <= vectorLlStockTaken.size(); indexLl ++){
                        
                        LlStockTaken objLlStockTaken = (LlStockTaken)vectorLlStockTaken.get(indexLl-1);                    
                        //update by devin 2014-03-24
                       try{
                        if(notSamePeriodLeaveKonfig){
                           long oid=objLlStockTaken.getLeaveApplicationId();
                           String where= PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = " + oid; 
                           Vector getData = PstLeaveApplication.list(0, 0, where, "");
                        
                           
                           LeaveApplication cuti =(LeaveApplication)getData.get(0);
                           oidDepHeadAppr= cuti.getDepHeadApproval();
                           oidLeaveDifferent = cuti.getLeaveAppDiffPeriod();
                        }
                       }catch(Exception exc ){
                           
                       }                                         
                        
                        dateStart = objLlStockTaken.getTakenDate()==null ? "" : Formater.formatDate(objLlStockTaken.getTakenDate(), "yyyy-MM-dd");    
                        dateFinnish = objLlStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(objLlStockTaken.getTakenFinnishDate(), "yyyy-MM-dd");
                                            
                        String ControlDatePopupTakenDateLL = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_TAKEN_DATE],(objLlStockTaken.getTakenDate() == null ? new Date() : objLlStockTaken.getTakenDate()), "getLLStartDate()");
                        //String ControlDatePopupTakenDateLL = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_TAKEN_DATE],(objLlStockTaken.getTakenDate() == null ? new Date() : objLlStockTaken.getTakenDate()), "getLLStartDate()");
                        String ControlDatePopupFinnishDateLL = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_FINNISH_DATE],( objLlStockTaken.getTakenFinnishDate() == null ? new Date() : objLlStockTaken.getTakenFinnishDate()),"getLLEndDate()");
                        //String ControlDatePopupFinnishDateLL = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_FINNISH_DATE],( objLlStockTaken.getTakenFinnishDate() == null ? new Date() : objLlStockTaken.getTakenFinnishDate()),"getLLEndDate()");
                        Vector rowx = new Vector();
                        
                        if(indexLl == 1){
                            i++;
                            rowx.add(""+i);
                            if(dtaLLExpired == true){
                                rowx.add("<font color=ff0000>Long Leave</font>");
                            }else{
                                rowx.add("Long Leave");
                            }

                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                            }else{
                                rowx.add("");
                            }
                            //update by devin 2014-03-24
                            if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifferenceLl(objLlStockTaken.getTakenDate() , objLlStockTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                    rowx.add(""+qty);  
                                   
                                }else{ 
                            rowx.add(""+objLlStockTaken.getTakenQty());
                                     }
                            
                            if(indexLl == indexLlget){
                                statusEditLl = true;
                                //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{ rowx.add(""+ControlDatePopupTakenDateLL);}
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{rowx.add(""+ControlDatePopupFinnishDateLL);}//end
                               
                             
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");                                        
                                    }else{           
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            rowx.add("<a href=\"javascript:cmdSaveLL('"+indexLl+"','"+objLlStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelLL('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");                                
                                        }else{
                                            rowx.add("");                                        
                                        }       
                                    }    
                                }
                            }else{
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add("<font color ="+color+">"+dateFinnish+"</font>");
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{         
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            rowx.add("<a href=\"javascript:cmdEditLL('" + oidEmployee + "','"+oidLeave+"','"+indexLl+"')\">Edit</a> | <a href=\"javascript:cmdDeleteLL('"+indexLl+"','"+objLlStockTaken.getOID()+"')\">Delete</a>");                                
                                        }else{
                                            rowx.add(""); 
                                        }
                                    }    
                                }    
                            }
                        }else{
                            i++;
                            rowx.add(""+i);
                            rowx.add("");

                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                            }else{
                                rowx.add("");
                            }
                             //update by devin 2014-03-24
                            if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifferenceLl(objLlStockTaken.getTakenDate() , objLlStockTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                    rowx.add(""+qty);  
                                   
                                }else{ 
                            rowx.add(""+objLlStockTaken.getTakenQty());
                                     }
                           
                            if(indexLl == indexLlget){
                                statusEditLl = true;
                                    //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{rowx.add(""+ControlDatePopupTakenDateLL);}
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{ rowx.add(""+ControlDatePopupFinnishDateLL);}//end
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            rowx.add("<a href=\"javascript:cmdSaveLL('"+indexLl+"','"+objLlStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelLL('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");                                
                                        }else{
                                            rowx.add("");
                                        }
                                    }    
                                }    
                            }else{
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add("<font color ="+color+">"+dateFinnish+"</font>");
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdEditLL('" + oidEmployee + "','"+oidLeave+"','"+indexLl+"')\">Edit</a> | <a href=\"javascript:cmdDeleteLL('"+indexLl+"','"+objLlStockTaken.getOID()+"')\">Delete</a>");                                
                                    }else{
                                        rowx.add("");
                                    }
                                }    
                            }
                        }                        
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                    }  
                    
                    Vector rowx = new Vector();
                    
                    if(statusEditLl == false){
                        
                        i = i + 1;                    
                        String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_TAKEN_DATE],new Date(), "getLLStartDate()");
                        String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_FINNISH_DATE],new Date(),"getLLEndDate()");
                                        
                        rowx.add(""+i);
                        rowx.add("");
                        
                        //Update By Agus 14-02-2014
                        if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(llQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(totalLlTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(currentLLQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(toBeTakenLL, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                        }else{
                                if(dtaLLExpired == true){
                                    rowx.add("<font color=ff0000>"+Formater.formatWorkDayHoursMinutes(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");
                                }else{
                                    rowx.add(""+Formater.formatWorkDayHoursMinutes(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                }
                                
                        }
                        }else{
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(llQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(totalLlTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(currentLLQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(toBeTakenLL, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                        }else{
                                if(dtaLLExpired == true){
                                    rowx.add("<font color=ff0000>"+Formater.formatWorkDayHoursMinutesII(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");
                                }else{
                                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                }
                                
                        }
                        }
                        rowx.add(""+0);
                        //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{    rowx.add(""+ControlDatePopupTaken);}
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{ rowx.add(""+ControlDatePopupFinnish);}//end
                        if(oidEmployee == 0){
                             rowx.add("");
                        }else{
                            if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("");
                            }else{
                                if(dtaLLExpired == true){
                                    rowx.add("");
                                }else{
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdSaveLL('0','0')\">Save</a>");
                                    }else{
                                        rowx.add("");
                                    }
                                }
                            }    
                        }    
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                    }                    
                }else{                    
                    
                    Vector rowx = new Vector();
                    
                    i = i + 1;
                    String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_TAKEN_DATE],(new Date()),"getLLEndDate()");
                    String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_FINNISH_DATE],(new Date()),"getLLEndDate()");
                    rowx.add(""+i);
                    rowx.add("Long Leave");
                    
                    //Update By Agus 14-02-2014
                    if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                    rowx.add(""+Formater.formatWorkDayHoursMinutes(llQty,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutes(totalLlTaken,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutes(currentLLQty,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutes(toBeTakenLL,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                    }else{
                        if(dtaLLExpired == true){
                            rowx.add("<font color=ff0000>"+Formater.formatWorkDayHoursMinutes(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");
                        }else{
                            rowx.add(""+Formater.formatWorkDayHoursMinutes(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        }   
                    }
                    }else{
                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(llQty,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(totalLlTaken,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(currentLLQty,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(toBeTakenLL,leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                    }else{
                        if(dtaLLExpired == true){
                            rowx.add("<font color=ff0000>"+Formater.formatWorkDayHoursMinutesII(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</font>");
                        }else{
                            rowx.add(""+Formater.formatWorkDayHoursMinutesII(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                        }   
                    }
                    }
                    rowx.add(""+0);
                    //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{  rowx.add(""+ControlDatePopupTaken);}
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{ rowx.add(""+ControlDatePopupFinnish);}//end
                    
                  
                   
                    
                    if(oidEmployee == 0){
                        rowx.add("");
                    }else{
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                            rowx.add("");
                        }else{
                            if(dtaLLExpired == true){
                                rowx.add("");
                            }else{
                                if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                    rowx.add("<a href=\"javascript:cmdSaveLL('0','0')\">Save</a>");
                                }else{
                                    rowx.add("");
                                }                                
                            }
                        }    
                    }    
                    lstData.add(rowx);                      
                    vctDetails.add(rowx);
                }
                                         
            result.add(ctrlist.draw());
            result.add(vctDetails);
            
            return result;
	}
        
        
        public Vector drawListSpecialUnpaidLeave(Vector vectorSpecialStockTaken,LeaveApplication objLeaveApplication, long oidLeave, int indexSpecialget, int docStatus,java.util.Date requestDate,int stsKaryawan,int typeForm){
            //public Vector drawListSpecialUnpaidLeave(Vector vectorSpecialStockTaken,long oidEmployee, long oidLeave, int indexSpecialget, int docStatus,java.util.Date requestDate){ 
        
        //  public Vector drawListSpecialUnpaidLeave(Vector vectorSpecialStockTaken,long oidEmployee, long oidLeave, int indexSpecialget, int docStatus){
            Vector result = new Vector();
            Vector vctDetails = new Vector();            
            long oidEmployee=0;
            if(objLeaveApplication!=null){
                oidEmployee=objLeaveApplication.getEmployeeId();
            }
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            
            ctrlist.addHeader("No","5%");
            ctrlist.addHeader("Type of Leave.","15%");
            ctrlist.addHeader("Schedule","15%");
            ctrlist.addHeader("No. of Days <br />Requested","15%");
            ctrlist.addHeader("Start Date", "20%");  
            ctrlist.addHeader("End Date", "20%");  
            ctrlist.addHeader("Action", "10%");                        
  
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();
            
            String color = "2275E6";
            
            int i = 0;
            boolean statusEditSpecial = false;                                     
            int indexSpecial = 0;
            
            Vector schedule_value = new Vector(1,1);
	    Vector schedule_key = new Vector(1,1);
            
            String oidSpecialLeave = "";
            String oidUnpaidLeave = "";
            //update by satrya 2013-04-11
            String oidExcuseLeave="";
            String whereSchedule = "";//PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidSpecialLeave+
                            //" OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidUnpaidLeave; 
            boolean notSamePeriodLeaveKonfig=false;
            try{
                 notSamePeriodLeaveKonfig= Boolean.parseBoolean(PstSystemProperty.getValueByName ("SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"));
            }catch(Exception exc){
                notSamePeriodLeaveKonfig=false;
                System.out.println("Exc SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"+exc);
            }
            try{
                oidSpecialLeave = String.valueOf(PstSystemProperty.getValueByName("OID_SPECIAL"));  
            }catch(Exception E){
                oidSpecialLeave = "0";
                System.out.println("EXCEPTION SYS PROP OID_SPECIAL : "+E.toString());
            }
            
            try{
                oidUnpaidLeave = String.valueOf(PstSystemProperty.getValueByName("OID_UNPAID"));  
            }catch(Exception E){
                oidUnpaidLeave = "0";
                System.out.println("EXCEPTION SYS PROP OID_UNPAID : "+E.toString());
            }
            //update by satrya 2013-04-11
            try{
                oidExcuseLeave = String.valueOf(PstSystemProperty.getValueByName("OID_EXCUSE_SCHEDULE_CATEGORY"));  
                if(oidExcuseLeave.length()==0){
                    oidExcuseLeave="0";
                }
            }catch(Exception E){
                oidExcuseLeave = "0";
                System.out.println("EXCEPTION SYS PROP OID_EXCUSE_SCHEDULE_CATEGORY : "+E.toString());
            }
            if(stsKaryawan==0 || typeForm==PstLeaveApplication.EXCUSE_APPLICATION){//dia tidak berhak cuti
                //maka dia excuse form
                 whereSchedule = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidExcuseLeave;
            }else{ 
               if(objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION){
                    whereSchedule = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidExcuseLeave;
               }else{
                    whereSchedule = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidSpecialLeave+
                            " OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidUnpaidLeave; 
               }
            }
            
            
            //DI isikan oid excuse form
                                                                                        
	    Vector listSchedule = PstScheduleSymbol.list(0,0,whereSchedule,null);
            int indexschedule;
            for(indexschedule = 0; indexschedule < listSchedule.size(); indexschedule++) 
	    {
                  ScheduleSymbol scheduleSymbol = (ScheduleSymbol)listSchedule.get(indexschedule);
                  schedule_value.add(String.valueOf(scheduleSymbol.getOID()));
                  schedule_key.add(scheduleSymbol.getSchedule());
            }
            
            if(vectorSpecialStockTaken.size() > 0){
                    
                    String dateStart ="";
                    String dateFinnish ="";
                      //update by devin 2014-03-25
                     long oidDepHeadAppr=0;
                    long oidLeaveDifferent=0;
                    
                    for(indexSpecial = 1 ; indexSpecial <= vectorSpecialStockTaken.size(); indexSpecial ++){
                        
                        
                        SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken)vectorSpecialStockTaken.get(indexSpecial-1);                    
                        //update by devin 2014-03-25
                       try{
                        if(notSamePeriodLeaveKonfig){
                           long oid=objSpecialUnpaidLeaveTaken.getLeaveApplicationId();
                           String where= PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = " + oid; 
                           Vector getData = PstLeaveApplication.list(0, 0, where, "");
                        
                           
                           LeaveApplication cuti =(LeaveApplication)getData.get(0);
                           oidDepHeadAppr= cuti.getDepHeadApproval();
                           oidLeaveDifferent = cuti.getLeaveAppDiffPeriod();
                        }
                       }catch(Exception exc ){
                           
                       }                                                            
                        
                        int statusLeave = SessLeaveApplication.LeaveStatus(objSpecialUnpaidLeaveTaken.getScheduledId());
                        
                        Vector vectSchedule = new Vector();
                        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();        
                        try{
                            String where = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+"="+objSpecialUnpaidLeaveTaken.getScheduledId();
                            
                            vectSchedule = PstScheduleSymbol.list(0, 0, where,null);
                            scheduleSymbol = (ScheduleSymbol) vectSchedule.get(0);
                        }catch(Exception e){
                            System.out.println("Exception "+e);
                        }
                        
                        String stsLeave = "";
                        
                        if(statusLeave == STATUS_LEAVE_SPECIAL_LEAVE){
                            stsLeave = "Special Leave";
                        }else if(statusLeave == STATUS_LEAVE_UNPAID_LEAVE){
                            stsLeave = "Unpaid Leave";
                        }else{
                            stsLeave = "Undefined";
                        }
                        
                        String selectValueSchedule = ""+objSpecialUnpaidLeaveTaken.getScheduledId();
                        
                        String controlCombo = ControlCombo.draw(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SCHEDULED_ID],"elementForm", null, selectValueSchedule, schedule_value, schedule_key," onkeydown=\"javascript:fnTrapKD()\"");
                           
                        dateStart = objSpecialUnpaidLeaveTaken.getTakenDate()==null ? "" : Formater.formatDate(objSpecialUnpaidLeaveTaken.getTakenDate(), "yyyy-MM-dd HH:mm");   //untuk Time start Date 
                        dateFinnish = objSpecialUnpaidLeaveTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(objSpecialUnpaidLeaveTaken.getTakenFinnishDate(), "yyyy-MM-dd HH:mm"); //untuk time Finnish date
                                            
                        String ControlDatePopupTakenDateSpecial = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],(objSpecialUnpaidLeaveTaken.getTakenDate() == null ? requestDate : objSpecialUnpaidLeaveTaken.getTakenDate()), "getSpecialStartDate()"); //untuk tanggal taken date
                        String ControlDatePopupFinnishDateSpecial = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],( objSpecialUnpaidLeaveTaken.getTakenFinnishDate() == null ? requestDate : objSpecialUnpaidLeaveTaken.getTakenFinnishDate()),"getSpecialEndDate()"); //untuk tanggal finnish date
                        //update by satrya 2012-07-31
                        //String ControlDatePopupTakenDateSpecial = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],(objSpecialUnpaidLeaveTaken.getTakenDate() == null ? new Date() : objSpecialUnpaidLeaveTaken.getTakenDate()), "getSpecialStartDate()");
                        //String ControlDatePopupFinnishDateSpecial = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],( objSpecialUnpaidLeaveTaken.getTakenFinnishDate() == null ? new Date() : objSpecialUnpaidLeaveTaken.getTakenFinnishDate()),"getSpecialEndDate()");
                        String ctrTimeStart = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE], objSpecialUnpaidLeaveTaken.getTakenDate()/*requestDate*/, "elemenForm", 24, 15, 0); //ctrl start time 
                        String ctrTimeEnd = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE], objSpecialUnpaidLeaveTaken.getTakenFinnishDate() /*requestDate*/, "elemenForm", 24, 15, 0); //ctrl finnish 
                        //String ctrTimeStart = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE], objSpecialUnpaidLeaveTaken.getTakenDate(), "elemenForm", 24, 15, 0);
                        //String ctrTimeEnd = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE], objSpecialUnpaidLeaveTaken.getTakenFinnishDate(), "elemenForm", 24, 15, 0);
                       
                        Vector rowx = new Vector();
                        
                        if(indexSpecial == 1){
                            i++;                            
                            rowx.add(""+i);
                            rowx.add(stsLeave);                            
                            if(indexSpecial == indexSpecialget){
                                
                                statusEditSpecial = true;
                                rowx.add(""+controlCombo);
                                if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                      //update by devin 2014-03-25
                                if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifference(objSpecialUnpaidLeaveTaken.getTakenDate() , objSpecialUnpaidLeaveTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                   rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                   
                                }else{ 
                                rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                     }
                                //rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                }else{
                                rowx.add(""+Formater.formatWorkDayHoursMinutesII(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                }
                                rowx.add(""+ControlDatePopupTakenDateSpecial+"<br>"+ctrTimeStart);
                                rowx.add(""+ControlDatePopupFinnishDateSpecial+"<br>"+ctrTimeEnd);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdSaveSpecial('"+indexSpecial+"','"+objSpecialUnpaidLeaveTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelSpecial('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");                                
                                    }else{
                                        rowx.add("");
                                    }
                                }
                            }else{
                                rowx.add(""+scheduleSymbol.getSchedule());
                                if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                    //update by devin 2014-03-25
                                if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifference(objSpecialUnpaidLeaveTaken.getTakenDate() , objSpecialUnpaidLeaveTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                   rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                   
                                }else{ 
                                rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                     }
                                //rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                }else{
                                rowx.add(""+Formater.formatWorkDayHoursMinutesII(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                }
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add("<font color ="+color+">"+dateFinnish+"</font>");
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdEditSpecial('" + oidEmployee + "','"+oidLeave+"','"+indexSpecial+"')\">Edit</a> | <a href=\"javascript:cmdDeleteSpecial('"+indexSpecial+"','"+objSpecialUnpaidLeaveTaken.getOID()+"')\">Delete</a>");                                
                                    }else{
                                        rowx.add("");
                                    }
                                }    
                            }
                            
                        }else{
                            i++;
                            rowx.add(""+i);
                            rowx.add(stsLeave);
                            
                            if(indexSpecial == indexSpecialget){
                                statusEditSpecial = true;
                                rowx.add(""+controlCombo);
                                if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                   //update by devin 2014-03-25
                                if( notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifference(objSpecialUnpaidLeaveTaken.getTakenDate() , objSpecialUnpaidLeaveTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                   rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                   
                                }else{
                                        rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                     }  
                                //rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() )); //rowx.add(""+objSpecialUnpaidLeaveTaken.getTakenQty());
                                }else{
                                rowx.add(""+Formater.formatWorkDayHoursMinutesII(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                }
                                rowx.add(""+ControlDatePopupTakenDateSpecial+"<br>"+ctrTimeStart);
                                rowx.add(""+ControlDatePopupFinnishDateSpecial+"<br>"+ctrTimeEnd);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdSaveSpecial('"+indexSpecial+"','"+objSpecialUnpaidLeaveTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelSpecial('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");                                
                                    }else{
                                        rowx.add("");
                                    }                                    
                                }    
                            }else{
                                rowx.add(""+scheduleSymbol.getSchedule());
                                if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                     //update by devin 2014-03-25
                                if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifference(objSpecialUnpaidLeaveTaken.getTakenDate() , objSpecialUnpaidLeaveTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                   rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                   
                                }else{
                                        rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                     }
                                //rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() )); //rowx.add(""+objSpecialUnpaidLeaveTaken.getTakenQty());
                                }else{
                                rowx.add(""+Formater.formatWorkDayHoursMinutesII(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                                }
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add("<font color ="+color+">"+dateFinnish+"</font>");
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdEditSpecial('" + oidEmployee + "','"+oidLeave+"','"+indexSpecial+"')\">Edit</a> | <a href=\"javascript:cmdDeleteSpecial('"+indexSpecial+"','"+objSpecialUnpaidLeaveTaken.getOID()+"')\">Delete</a>");                                
                                    }else{
                                        rowx.add("");
                                    }                                    
                                }
                            }
                        }                        
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                    }  
                    
                    Vector rowx = new Vector();
                    String ctrTimeStart = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE  ], requestDate, "elemenForm", 24, 15, 0);
                    String ctrTimeEnd = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE], requestDate, "elemenForm", 24, 15, 0);
                    // String ctrTimeStart = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE], getDateSetTime(0,0,0), "elemenForm", 24, 15, 0);
                    //String ctrTimeEnd = ControlDate.drawTime(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE], getDateSetTime(0,0,0), "elemenForm", 24, 15, 0);

                    if(statusEditSpecial == false){
                        i = i + 1;                    
                        String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],requestDate, "getSpecialStartDate()");
                        String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],requestDate,"getSpecialEndDate()");
                        //String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],new Date(), "getSpecialStartDate()");
                        //String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],new Date(),"getSpecialEndDate()");
                        String controlCombo = ControlCombo.draw(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SCHEDULED_ID],"elementForm", "-select-", "-", schedule_value, schedule_key," onkeydown=\"javascript:fnTrapKD()\"");
                        rowx.add(""+i);
                        rowx.add("");
                        rowx.add(""+controlCombo);
                        rowx.add(""+0);
                          //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{   rowx.add(""+ControlDatePopupTaken+"<br>"+ctrTimeStart); }
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{  rowx.add(""+ControlDatePopupFinnish+"<br>"+ctrTimeEnd);}//end
                     
                      
                        if(oidEmployee == 0){
                              rowx.add("");
                        }else{
                            if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                rowx.add("<a href=\"javascript:cmdSaveSpecial('0','0')\">Save</a>");
                            }else{
                                rowx.add("");
                            }
                        }    
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                    }
                    
                }else{              
                    Vector rowx = new Vector();
                    i = i + 1;
                    String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],(requestDate), "getSpecialStartDate()");
                    String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],(requestDate), "getSpecialStartDate()");
                    //String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],(new Date()), "getSpecialStartDate()");
                   // String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],(new Date ()), "getSpecialStartDate()");
                   //update by satrya 2012-08-02
                    String ctrTimeStart = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE], requestDate, "elemenForm", 24, 15, 0); //ctrl start time
                    String ctrTimeEnd = ControlDate.drawTime(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE], requestDate, "elemenForm", 24, 15, 0); //ctrl finnish
                        
                    String controlCombo = ControlCombo.draw(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SCHEDULED_ID],"elementForm", "-select-", "-", schedule_value, schedule_key," onkeydown=\"javascript:fnTrapKD()\"");
                    rowx.add(""+i);
                    rowx.add("");
                    //updatrew by satrya 2012-08-02
///                    rowx.add(""+Formater.formatWorkDayHoursMinutes(objSpecialUnpaidLeaveTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave() ));
                     //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{ rowx.add(""+controlCombo);}//end
                   
                    rowx.add(""+0);
                      //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{ rowx.add(""+ControlDatePopupTaken+"<br>"+ctrTimeStart);} //untuk memunculkan pilihan start time
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                         }
                         else{ rowx.add(""+ControlDatePopupFinnish+"<br>"+ctrTimeEnd);}//end //untuk memunculkan pilihan end time
                    if(oidEmployee == 0){
                        rowx.add("");
                    }else{
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                            rowx.add("<a href=\"javascript:cmdSaveSpecial('0','0')\">Save</a>");
                        }else{
                            rowx.add("");
                        }
                    }    
                    lstData.add(rowx);                      
                    vctDetails.add(rowx);
                }
                                         
            result.add(ctrlist.draw());
            result.add(vctDetails);
            
            return result;
	}
        
    
       public Vector drawListDPLeave(Vector vListAl, Vector vectorDpStockTaken, long oidEmployee, long oidLeave,int indexDpGet,int docStatus, java.util.Date requestDate,int iCommand){
           // public Vector drawListDPLeave(Vector vListAl, Vector vectorDpStockTaken, long oidEmployee, long oidLeave,int indexDpGet,int docStatus){
            Vector result = new Vector();
            Vector vctDetails = new Vector();

            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");

            ctrlist.addHeader("No","5%");
            ctrlist.addHeader("Type of Leave.","10%");
            ctrlist.addHeader("Qty","5%");
            ctrlist.addHeader("Taken","5%");
            ctrlist.addHeader("Current Qty","10%");
            ctrlist.addHeader("To Be Taken","10%");
            ctrlist.addHeader("No. of Days <br />Eligible","5%");
            ctrlist.addHeader("No. of Days <br />Requested","10%");
            ctrlist.addHeader("Taken Date", "22%");
            ctrlist.addHeader("Unpaid Date", "10%");
            ctrlist.addHeader("Action", "10%");

            String color = "2275E6";

            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();
            int i = 0;
            float eligbleDay = 0;
            float dpQty =0;
            float totalDpTaken = 0;
            float curentDpQty = 0;
            float totalDp2BeTaken = 0;
            
            boolean notSamePeriodLeaveKonfig=false;
            try{
                 notSamePeriodLeaveKonfig= Boolean.parseBoolean(PstSystemProperty.getValueByName ("SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"));
            }catch(Exception exc){
                notSamePeriodLeaveKonfig=false;
                System.out.println("Exc SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD"+exc);
            }
            //update by satrya 2014-01-18
            //float expiredQTY = SessLeaveManagement.getDpExpired(oidEmployee,null);
            float expiredQTY = 0;
            if(leaveConfig.getBalanceNotCalculationDpExpired()){
                expiredQTY = SessLeaveManagement.getDpExpired(oidEmployee,new Date()); 
            }else{
                expiredQTY = SessLeaveManagement.getDpExpired(oidEmployee,null);
            }
            for (int j=0; j<vListAl.size(); j++)
            {
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)vListAl.get(j);
               
               dpQty = item.getDPQty();
               curentDpQty = item.getDPQty() - item.getDPTaken();
                eligbleDay = curentDpQty - item.getDP2BTaken()- expiredQTY;
                totalDpTaken = item.getDPTaken();
               
                totalDp2BeTaken = item.getDP2BTaken();
              }
            //update by satrya 2012-08-16
                /* Stock yang boleh di ambil*/
                boolean StsCanMinus = false;
                /* Logikanya :
                - klo stock minus, harus di cek apakah kondisi minus masih bisa mengambil cuti atau tidak
                */
               
                /*if(eligbleDay > 0){ //stock can't minus
                    StsCanMinus = true; //can taken
                }else{
                    StsCanMinus = false; //can't taken
                }*/
                //update by satrya 2013-08-29
                if(leaveConfig.getDPEligibleMinus(eligbleDay)){ //stock can't minus 
                    StsCanMinus = true; //can taken
                }else{
                    StsCanMinus = false; //can't taken
                }
                 //float tempDPTakenQty =0; 
            //eligbleDay = SessLeaveApplication.getDpEligbleDay(oidEmployee);
                if(vectorDpStockTaken.size() > 0){
                  //  float eligible = 0;
                    String dateStart ="";
                    String datePaid ="";
                    //String datePaid = FRMQueryString.requestDateString( request, "date");
                    int indexDp;
                    boolean statusEditDp = false;
                    String formatTakenDate = "yyyy-MM-dd";
                    String formatFinishTime= "";
                    //update by devin 2014-03-24
                     long oidDepHeadAppr=0;
                    long oidLeaveDifferent=0;
                   
                   
                    for(indexDp = 1 ; indexDp <= vectorDpStockTaken.size(); indexDp ++){
                        DpStockTaken objDpStockTaken = (DpStockTaken)vectorDpStockTaken.get(indexDp-1);
                       //  tempDPTakenQty = tempDPTakenQty + objDpStockTaken.getTakenQty();                         
                        //update by devin 2014-03-24
                       try{
                          if(notSamePeriodLeaveKonfig){
                           long oid=objDpStockTaken.getLeaveApplicationId();
                           String where= PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = " + oid; 
                           Vector getData = PstLeaveApplication.list(0, 0, where, "");
                          
                           
                           LeaveApplication cuti =(LeaveApplication)getData.get(0); 
                           oidDepHeadAppr= cuti.getDepHeadApproval();
                           oidLeaveDifferent = cuti.getLeaveAppDiffPeriod();
                          }
                       }catch(Exception exc ){
                           
                       }  
                        if(objDpStockTaken.getTakenDate().getHours()==objDpStockTaken.getTakenFinnishDate().getHours() &&
                           objDpStockTaken.getTakenDate().getMinutes()==objDpStockTaken.getTakenFinnishDate().getMinutes()){
                           formatTakenDate = "yyyy-MM-dd";
                        } else{
                           formatTakenDate = "yyyy-MM-dd HH:mm";
                           formatFinishTime = " HH:mm";
                        } 
                                                                                              
                       dateStart = objDpStockTaken.getTakenDate()==null ? "": Formater.formatDate(objDpStockTaken.getTakenDate() !=null ? objDpStockTaken.getTakenDate():requestDate, formatTakenDate);
                       // dateStart = objDpStockTaken.getTakenDate()==null ? "": Formater.formatDate(objDpStockTaken.getTakenDate(), formatTakenDate);
                        if(formatFinishTime.length()>1){
                            dateStart = dateStart + " to " + (objDpStockTaken.getTakenFinnishDate()==null ? "": Formater.formatDate(objDpStockTaken.getTakenFinnishDate() !=null ? objDpStockTaken.getTakenFinnishDate() :requestDate, formatFinishTime));
                            //dateStart = dateStart + " to " + (objDpStockTaken.getTakenFinnishDate()==null ? "": Formater.formatDate(objDpStockTaken.getTakenFinnishDate(), formatFinishTime));
                        }
                        datePaid = objDpStockTaken.getPaidDate()==null ? "-": Formater.formatDate(objDpStockTaken.getPaidDate(), formatTakenDate);                        
                        String ControlDatePopupTakenDate = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],( objDpStockTaken.getTakenDate() == null ? requestDate : objDpStockTaken.getTakenDate()),"getDPStartDate()");
                        //update by satrya 2013-12-12  String ControlDatePopupFinnishDate = ControlDatePopup.writeDateHide(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],( objDpStockTaken.getTakenFinnishDate() == null ? requestDate : objDpStockTaken.getTakenFinnishDate()),"getDpFinishDate()");
                        String ControlDatePopupFinnishDate = leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ?ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],( objDpStockTaken.getTakenFinnishDate() == null ? requestDate : objDpStockTaken.getTakenFinnishDate()),"getDpFinishDate()"):ControlDatePopup.writeDateHide(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],( objDpStockTaken.getTakenFinnishDate() == null ? requestDate : objDpStockTaken.getTakenFinnishDate()),"getDpFinishDate()");
                         String cekboxFullDay = leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ? "<br><input name="+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FLAG_FULL_SCHEDULE]+" type=\"checkbox\" value=\"1\" checked /> Cek full day <br>" :""; 
                        //String ControlDatePopupTakenDate = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],( objDpStockTaken.getTakenDate() == null ? new Date() : objDpStockTaken.getTakenDate()),"getDPStartDate()");
                        //String ControlDatePopupFinnishDate = ControlDatePopup.writeDateHide(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],( objDpStockTaken.getTakenFinnishDate() == null ? new Date() : objDpStockTaken.getTakenFinnishDate()),"");
                        //untuk command Edit
                        String cmdViewDP ="<input type=\"text\" disabled name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_PAID_DATE]+"\" value=\""+datePaid+"\" >"+
                                //String cmdViewDP ="<input type=\"text\" disabled name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_PAID_DATE]+"\" value=\""+datePaid+"\" >"+
                                "<input type=\"hidden\" value=\""+objDpStockTaken.getDpStockId()+"\" name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"\">"+
                                "<a href=\"javascript:cmdViewDPList("+iCommand+","+eligbleDay+")\" class=\"buttonlink\"><img src=\"../../images/icon/folderopen.gif\" border=\"0\" alt=\"Select Available DP \"></a>";

                        String ctrTimeStart = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE], objDpStockTaken.getTakenDate() !=null ? objDpStockTaken.getTakenDate():requestDate, "elemenForm", 24, 15, 0);
                        String ctrTimeEnd = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], objDpStockTaken.getTakenFinnishDate() !=null ? objDpStockTaken.getTakenFinnishDate(): requestDate, "elemenForm", 24, 15, 0);
                        //String ctrTimeStart = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE], objDpStockTaken.getTakenDate(), "elemenForm", 24, 15, 0);
                        //String ctrTimeEnd = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], objDpStockTaken.getTakenFinnishDate(), "elemenForm", 24, 15, 0);
                        
                        Vector rowx = new Vector();

                        if(indexDp == 1){
                            i = i + 1;
                            rowx.add(""+i);
                            rowx.add("Day off Payment");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                 //update by devin 2014-03-24
                            if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifference(objDpStockTaken.getTakenDate() , objDpStockTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                   rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));//No. of DaysRequested
                                   
                                }else{ 
                            rowx.add(""+Formater.formatWorkDayHoursMinutes(objDpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));//No. of DaysRequested
                                     }
                            
                            }else{
                            rowx.add(""+Formater.formatWorkDayHoursMinutesII(objDpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                            }
                            if(indexDpGet == indexDp){
                                statusEditDp = true;
                                //update by satrya 2013-12-17 rowx.add(""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ctrTimeEnd + " "+ ControlDatePopupFinnishDate);
                                rowx.add(leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ?""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ControlDatePopupFinnishDate  + " "+  ctrTimeEnd + cekboxFullDay:""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ctrTimeEnd + " "+ ControlDatePopupFinnishDate);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                    rowx.add("");
                                }else{
                                      //update by satrrya 2012-07-27
                                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                        rowx.add("");
                                        }else{    rowx.add(""+cmdViewDP);}//end
                                
                                    if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdSaveDP('"+indexDp+"','"+objDpStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelDP('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");
                                    }else{
                                        rowx.add("");
                                    }
                                }
                            }else{
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add(""+datePaid);
                                if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                    rowx.add("<a href=\"javascript:cmdEditDP('" + oidEmployee + "','"+oidLeave+"','"+indexDp+"')\">Edit</a> | <a href=\"javascript:cmdDeleteDP('"+indexDp+"','"+objDpStockTaken.getOID()+"')\">Delete</a>");
                                }else{
                                    rowx.add("");
                                }
                            }
                        }else{
                            i = i + 1;
                            rowx.add(""+i);
                            //rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                                 //update by devin 2014-03-24
                            if(notSamePeriodLeaveKonfig && oidDepHeadAppr !=0 && (oidLeaveDifferent !=0 || oidDepHeadAppr ==0 && oidLeaveDifferent !=0)){
                                    float qty=0;
                                    float totalQty =0;
                                    qty= DateCalc.workDayDifferenceLl(objDpStockTaken.getTakenDate() , objDpStockTaken.getTakenFinnishDate(), 8f);
                                    totalQty=qty + 1;
                                   rowx.add(""+Formater.formatWorkDayHoursMinutes(qty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())); 
                                   
                                }else{ 
                            rowx.add(""+Formater.formatWorkDayHoursMinutes(objDpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                                     }
                            rowx.add(""+Formater.formatWorkDayHoursMinutes(objDpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                            }else{
                            rowx.add(""+Formater.formatWorkDayHoursMinutesII(objDpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                            }
                            if(indexDpGet == indexDp){
                                statusEditDp = true;
                               // update by satrya 2013-12-12 rowx.add(""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ctrTimeEnd + " "+ ControlDatePopupFinnishDate);                                                                
                               
                                    rowx.add(leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ?""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ ControlDatePopupFinnishDate  + " "+ ctrTimeEnd + cekboxFullDay:""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ctrTimeEnd + " "+ ControlDatePopupFinnishDate);
                                if(oidEmployee == 0){
                                    rowx.add(""); 
                                    rowx.add("");
                                }else{
                                      //update by satrrya 2012-07-27
                                     if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                    rowx.add(""); 
                                    }else{  rowx.add(""+cmdViewDP);}//end 
                                 
                                   if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                        rowx.add("<a href=\"javascript:cmdSaveDP('"+indexDp+"','"+objDpStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelDP('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");
                                   }else{
                                        rowx.add("");
                                   }
                                }
                            }else{
                                rowx.add("<font color ="+color+">"+dateStart+"</font>");
                                rowx.add(""+datePaid);
                                if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                    rowx.add("<a href=\"javascript:cmdEditDP('" + oidEmployee + "','"+oidLeave+"','"+indexDp+"')\">Edit</a> | <a href=\"javascript:cmdDeleteDP('"+indexDp+"','"+objDpStockTaken.getOID()+"')\">Delete</a>");
                                }else{
                                    rowx.add("");
                                }
                            }
                        }
                        lstData.add(rowx);
                        vctDetails.add(rowx);
                    }///menentukan selanjutnya/jika pilihat pertama sdh di pilih 
                    Vector rowx = new Vector();
                    if(statusEditDp == false){
                        indexDp++;
                        i = i + 1;
                        Date  aNewDate = new Date();
                        aNewDate.setHours(0);
                        aNewDate.setMinutes(0);
                        aNewDate.setSeconds(0);
                        //jika sudah di save maka selanjutnya akan di set tgl n jambrp
                        String ControlDatePopupTakenDate = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],requestDate ==null ? aNewDate: requestDate, "getDPStartDate()");
                        
                        //update by satrya 2013-12-12  String ControlDatePopupFinnishDate = ControlDatePopup.writeDateHide(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], requestDate ==null ? aNewDate: requestDate,"getDpFinishDate()");
                        String ControlDatePopupFinnishDate = leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ?ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], requestDate ==null ? aNewDate: requestDate,"getDpFinishDate()"):ControlDatePopup.writeDateHide(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], requestDate ==null ? aNewDate: requestDate,"getDpFinishDate()");
                        String cekboxFullDay = leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ? "<br><input name="+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FLAG_FULL_SCHEDULE]+" type=\"checkbox\" value=\"1\" checked /> Cek full day <br>" :""; 
                        String ctrTimeStart = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE], aNewDate, "elemenForm", 24, 15, 0);//waktunya
                        String ctrTimeEnd = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], aNewDate, "elemenForm", 24, 15, 0);//waktunya
                        
                        rowx.add(""+i);
                        rowx.add("");
                        //Update By Agus 14-02-2014
                        if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(dpQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                        rowx.add(""+"<p title="+Formater.formatNumber(totalDpTaken, "###.###")+">"+Formater.formatWorkDayHoursMinutes(totalDpTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+"</p>");
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(curentDpQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutes(totalDp2BeTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                        rowx.add(""+"<p title="+Formater.formatNumber(eligbleDay, "###.###")+">"+Formater.formatWorkDayHoursMinutes(eligbleDay,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+"</p>");
                        }else{
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(dpQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                        rowx.add(""+"<p title="+Formater.formatNumber(totalDpTaken, "###.###")+">"+Formater.formatWorkDayHoursMinutesII(totalDpTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+"</p>");
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(curentDpQty,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                        rowx.add(""+Formater.formatWorkDayHoursMinutesII(totalDp2BeTaken,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave()));
                        rowx.add(""+"<p title="+Formater.formatNumber(eligbleDay, "###.###")+">"+Formater.formatWorkDayHoursMinutesII(eligbleDay,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+"</p>");
                        }
                        rowx.add(""+0);
                          //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        
                         //update by satrya 2013-12-12 ""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ctrTimeEnd + " "+ ControlDatePopupFinnishDate 
                        }else{rowx.add(leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ?""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ControlDatePopupFinnishDate  + " "+  ctrTimeEnd + cekboxFullDay:""+ControlDatePopupTakenDate + " "+ctrTimeStart+" to "+ctrTimeEnd + " "+ ControlDatePopupFinnishDate);}
                        
                       String cmdViewDP ="<input type=\"text\" disabled name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_PAID_DATE]+"\" >"+
                                //update by satrya 2012-08-07
                                   //String cmdViewDP ="<input type=\"text\" readonly name=\"DpPaidDate\value=\""+datePaid+"\" >"+
                                "<input type=\"hidden\" value=\"\" name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"\">"+
                                // "<input type=\"hidden\" value=\"\" name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"\">"+ 
                                "<a href=\"javascript:cmdViewDPList('0',"+eligbleDay+")\" class=\"buttonlink\"><img src=\"../../images/icon/folderopen.gif\" border=\"0\" alt=\"Select Available DP \"></a>";
                        if(oidEmployee == 0){
                            rowx.add("");
                            rowx.add("");
                        }else{
                              //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{  rowx.add(""+cmdViewDP);}//end
                          
                            if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                rowx.add("<a href=\"javascript:cmdSaveDP('0','0')\">Save</a>");
                            }else{
                                rowx.add("");
                            }
                        }
                        lstData.add(rowx);
                        vctDetails.add(rowx);
                    }
                }else{//untuk Save DP
                    //Date  aNewDate = new Date();
                // Date aNewDate = objLeaveApplication.getSubmissionDate();
                    ////aNewDate.setHours(0);
                   // aNewDate.setMinutes(0);
                    //aNewDate.setSeconds(0);
                    String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],requestDate, "getDPStartDate()");
                    //update by satrya 2012-08-01
                    //String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],aNewDate, "getALStartDate()");
                    
                    // update by satrya 2013-12-12 String ControlDatePopupFinnishDate = ControlDatePopup.writeDateHide(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],(requestDate),"getDpFinishDate()");
                    String ControlDatePopupFinnishDate = leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ?ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],(requestDate),"getDpFinishDate()"):ControlDatePopup.writeDateHide(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],(requestDate),"getDpFinishDate()");
                    String cekboxFullDay = leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ? "<br> <input name="+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FLAG_FULL_SCHEDULE]+" type=\"checkbox\" value=\"1\" checked /> Cek full day " :""; 
                    //String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],requestDate,"getDpFinishDate()");
                   // String inputHidden = "<input type=\"hidden\" value=\""+ requestDate + "\" name=\"" +ControlDatePopupFinnish+ "\">";
                    //update by satrya 2012-08-01
                    //String ctrTimeStart = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE], aNewDate, "elemenForm", 24, 15, 0);
                    String ctrTimeStart = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE], requestDate, "elemenForm", 24, 15, 0); //untuk time start date
                    //String ctrTimeStart = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE], requestDate, "elemenForm", 24, 15, 0);
                    String ctrTimeEnd = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], requestDate, "elemenForm", 24, 15, 0);
                    //  String ctrTimeEnd = ControlDate.drawTime(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE], aNewDate, "elemenForm", 24, 15, 0);
                    
                    i = i+1;
                    Vector rowx = new Vector();
                    rowx.add(""+i);
                    rowx.add("Day off payment");
                    if(leaveConfig!=null && leaveConfig.isLeaveApplicationIsDay()){
                    rowx.add(""+Formater.formatWorkDayHoursMinutes(dpQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+"<p title="+Formater.formatNumber(totalDpTaken, "###.###")+">"+Formater.formatWorkDayHoursMinutes(totalDpTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</p>");
                    rowx.add(""+Formater.formatWorkDayHoursMinutes(curentDpQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutes(totalDp2BeTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+"<p title="+Formater.formatNumber(eligbleDay, "###.###")+">"+Formater.formatWorkDayHoursMinutes(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</p>");
                    }else{
                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(dpQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+"<p title="+Formater.formatNumber(totalDpTaken, "###.###")+">"+Formater.formatWorkDayHoursMinutesII(totalDpTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</p>");
                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(curentDpQty, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+Formater.formatWorkDayHoursMinutesII(totalDp2BeTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    rowx.add(""+"<p title="+Formater.formatNumber(eligbleDay, "###.###")+">"+Formater.formatWorkDayHoursMinutesII(eligbleDay, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</p>");
                    }
                    rowx.add(""+0);
                      //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        // update by satrya 2013-12-12 ""+ControlDatePopupTaken+ctrTimeStart+" to "+ctrTimeEnd  +" "+ ControlDatePopupFinnishDate
                        }else{ rowx.add(leaveConfig!=null && leaveConfig.getConfigurationLeaveApprovall()==I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG ?""+ControlDatePopupTaken+ctrTimeStart+" to "+ ControlDatePopupFinnishDate +" "+ ctrTimeEnd + cekboxFullDay:""+ControlDatePopupTaken+ctrTimeStart+" to "+ctrTimeEnd  +" "+ ControlDatePopupFinnishDate);}//end
                    //else{ rowx.add(""+ControlDatePopupTaken+"to"+ctrTimeStart+" "+ctrTimeEnd);}//end
                    //}else{ rowx.add(""+ControlDatePopupTaken+ctrTimeStart+" to "+ctrTimeEnd +" " +ControlDatePopupFinnish);}//end
                   
                    String cmdViewDP = "<input type=\"text\" disabled name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_PAID_DATE]+"\">"+
                            //String cmdViewDP = "<input type=\"text\" disabled name=\"DpPaidDate\">"+
                            "<input type=\"hidden\" value=\"\" name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"\">"+
                            //"<input type=\"text\" value=\""+tempDPTakenQty+"\" name=\""+tempDPTakenQty+"\">"+
                            "<a href=\"javascript:cmdViewDPList('0',"+eligbleDay+")\" class=\"buttonlink\"><img src=\"../../images/icon/folderopen.gif\" border=\"0\" alt=\"Select Available DP \"></a>"; 
                    if(oidEmployee == 0){
                        rowx.add("");
                        rowx.add("");
                    }else{
                          //update by satrrya 2012-07-27
                         if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        rowx.add("");
                        }else{ rowx.add(""+cmdViewDP); }//end
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                            //update by satrya 2012-08-16
                            if(StsCanMinus){
                                rowx.add("<a href=\"javascript:cmdSaveDP('0','0')\">Save</a>");
                            }else{
                                rowx.add("No DP eligible!");
                            }
                        }else{
                            rowx.add("");
                        }

                    }
                    lstData.add(rowx);
                    vctDetails.add(rowx);
                }
            result.add(ctrlist.draw());
            result.add(vctDetails);
           // String InputtempDPTakenQty= "<input  type=\"text\" name=\"totaldp\" value=\"" + tempDPTakenQty + "\">"; 
            //result.add(tempDPTakenQty); 
            return result;
	}
    
        public void parserDate(Calendar date, int iCommand, long symbolId, long oidEmployee, SpecialLeaveStock leaveStock,
                               CtrlSpecialLeaveStock ctrlSpecialLeaveStock, SessLeaveApplication sessLeave){
            
            Calendar d = (Calendar)date.clone();
            
            for(int i=1; i<=date.getActualMaximum(Calendar.DATE); i++){
                d.set(Calendar.DATE, i);
                
                long oidStock = sessLeave.getLeaveStock(symbolId, oidEmployee, d.getTime());
                leaveStock.setOID(oidStock);
                leaveStock.setTakenDate(d.getTime());
                
                ctrlSpecialLeaveStock.action(iCommand, oidStock, leaveStock);
            }            
        }
        
        public long getCategoryId(Vector specialLeaveIds, Vector specialLeaveCats, long symbolId) {
            // ambil category AL
            if(specialLeaveIds != null) {
                for(int i=0; i<specialLeaveIds.size(); i++) {
                    long id = Long.parseLong(String.valueOf(specialLeaveIds.get(i)));

                    if(id == symbolId) {
                        return Long.parseLong(String.valueOf(specialLeaveCats.get(i)));
                    }
                }
            }
            
            return 0;
        }
    
%>

<%
     /** 
      * @DESC : UNTUK MENGEXPIRED-KAN DP YANG HARUS EXPIRED   
      */            
    SessDPUpload.UpdateStatusDpStock();   
    String source=FRMQueryString.requestString(request, "source");
    int iCommand = FRMQueryString.requestCommand(request); 
    //int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int leaveType = FRMQueryString.requestInt(request, "leaveType");
    long oidLeave = FRMQueryString.requestLong(request,FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]);                   
    long oid_employee = emplx.getOID();///FRMQueryString.requestLong(request, "oid_employee");
    int indexMain = FRMQueryString.requestInt(request, "indexMain");
    int indexAl = FRMQueryString.requestInt(request, "indexAl");
    int indexLl = FRMQueryString.requestInt(request, "indexLl");
    int indexSpecial = FRMQueryString.requestInt(request, "indexSpecial");
    int indexDP = FRMQueryString.requestInt(request, "indexDP");
    //update by satrya 2013-04-15
    int type_form = FRMQueryString.requestInt(request, ""+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]); 
    //update by satrya 2012-12-03
  //  float tempTotalDp = 0;
    long TakenALOid = FRMQueryString.requestLong(request, FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]);
    long TakenLLOid = FRMQueryString.requestLong(request, FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]);
    long TakenSpecialOid = FRMQueryString.requestLong(request, FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]);
    long TakenDpOid = FRMQueryString.requestLong(request, FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]);
   //update by satrya 2013-12-10
    I_Leave ileaveConfig = null;

    try {
        ileaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    } catch (Exception e){
        System.out.println("Exception : " + e.getMessage());
    }
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsgLeaveApp = "";

        CtrlLeaveApplication ctrlLeaveApplication = new CtrlLeaveApplication(request);
        int leaveStatus =-1;
//update by satrya 2012-07-25
    if (leaveType == LEAVE_TYPE_APPLICATION || (source != null && "presence".equals(source) && iCommand == Command.EDIT)) {
        if (source != null && "presence".equals(source) && iCommand == Command.EDIT) {
            leaveType = LEAVE_TYPE_APPLICATION;
            try {
                long employeeId = FRMQueryString.requestLong(request, "employeeId");
                Long lDateLeave = FRMQueryString.requestLong(request, "requestDateDaily");
                Date dateLeave = new Date(lDateLeave);
                //String sDateLeave = Formater.formatTimeLocale(dateLeave, "yyyy-MM-dd");
                Vector listLeaveAplication =  PstLeaveApplication.listOid(employeeId, dateLeave);
               if(listLeaveAplication !=null && listLeaveAplication.size()>0){
                   try{
                       //update by satrya
                       /**
                       /* untuk mengambil OID Leave
                       **/
                    for (int j = 0; j < listLeaveAplication.size(); j++) {
                    LeaveOidSym leaveOidSym = (LeaveOidSym) listLeaveAplication.get(j);
                    oidLeave = leaveOidSym.getLeaveOid();
                }
                  // oidLeave = (Long) listLeaveAplication.get(0);
                   }catch(Exception ex){System.out.println(ex);}
                
               }
               //ambil oidLeave
        
            } catch (Exception e) {

                System.out.println("EXCEPTION " + e.toString());
            }
        }
            if(ileaveConfig!=null && ileaveConfig.getConfigurationSendMail()==I_Leave.LEAVE_CONFIG_SEND_EMAIL_CREATE_BY_LEAVE_FORM){ 
               //update by satrya 2014-01-20
                // iErrCode = ctrlLeaveApplication.action(iCommand, oidLeave, null,approot,emplx!=null?emplx.getOID():0); 
               //update by satrya 2014-02-08 if(indexAl==0 && indexLl==0 && indexSpecial==0 && indexDP==0){
                if(indexMain!=0){ 
                    iErrCode = ctrlLeaveApplication.action(iCommand, oidLeave, null,approot,emplx!=null?emplx.getOID():0); 
               }
               
               
            }else{
                //update by satrya 2014-01-20
                // iErrCode = ctrlLeaveApplication.action(iCommand, oidLeave, null,approot); 
                //update by satrya 2014-02-08if(indexAl==0 && indexLl==0 && indexSpecial==0 && indexDP==0){
                if(indexMain!=0){
                      iErrCode = ctrlLeaveApplication.action(iCommand, oidLeave, null,approot); 
                }
               
            }
        } else{
            if(ileaveConfig.getConfigurationSendMail()==I_Leave.LEAVE_CONFIG_SEND_EMAIL_CREATE_BY_LEAVE_FORM){
                //update by satrya 2014-01-20
                // iErrCode = ctrlLeaveApplication.action(Command.EDIT, oidLeave, null,approot,emplx!=null?emplx.getOID():0);
                //update by satrya 2014-02-08 if(indexAl==0 && indexLl==0 && indexSpecial==0 && indexDP==0){
                if(indexMain!=0){
                     iErrCode = ctrlLeaveApplication.action(Command.EDIT, oidLeave, null,approot,emplx!=null?emplx.getOID():0);
                }
               
            }else{
                //update by satrya 2014-01-20
                //iErrCode = ctrlLeaveApplication.action(iCommand, oidLeave, null,approot); 
                //update by satrya 2014-02-08 if(indexAl==0 && indexLl==0 && indexSpecial==0 && indexDP==0){
                if(indexMain!=0){
                    iErrCode = ctrlLeaveApplication.action(iCommand, oidLeave, null,approot); 
                }
                 
            }
        }
       
	errMsgLeaveApp = ctrlLeaveApplication.getMessage();        
        
        LeaveApplication objLeaveApplication = ctrlLeaveApplication.getLeaveApplication();
        
        if(objLeaveApplication ==null ){
            objLeaveApplication = new LeaveApplication();            
        }
        
        String sReadOnly = "";
        
        if(objLeaveApplication.getOID()==0){
            //update by satrya 2014-01-20
            //karena ketika d hapus form'nya jadi ikut hilang
            // objLeaveApplication.setSubmissionDate(new Date());
            //update by satrya 2014-02-08 if(oidLeave!=0){ 
                if(oidLeave!=0){
                    try{
                        objLeaveApplication = PstLeaveApplication.fetchExc(oidLeave);
                    }catch(Exception exc){ 
                                       
                    }
                }else{
                   objLeaveApplication.setSubmissionDate(new Date());
                 }
            } else{
              if(leaveType==LEAVE_TYPE_APPLICATION){
             
                  if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                    sReadOnly="readonly";
                  }
                  //end
                    long oidApprover = FRMQueryString.requestLong(request, "oidApprover");
                    long ApprovalDate_ms = FRMQueryString.requestLong(request, "ApprovalDate");
                    Date ApprovalDates = new Date(ApprovalDate_ms);
                    if(oidApprover!=0){
                        int indexApproval = FRMQueryString.requestInt(request, "indexApproval");
                        switch(indexApproval){
                            case 1 : objLeaveApplication.setDepHeadApproval(oidApprover);
                                     LeaveApplication objLeave = new LeaveApplication();
                                     
                                    I_Leave leaveConfig1 = null;

                                    try {
                                        leaveConfig1 = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
                                    } catch (Exception e){
                                        System.out.println("Exception : " + e.getMessage());
                                    }

                                    int maxApproval1 = leaveConfig1.getMaxApproval(objLeaveApplication.getEmployeeId());
                                    boolean stsDokApprov1 = false;

                                    // if doc status Draft
                                    if (objLeaveApplication.getDocStatus() == 1) {

                                        if (objLeaveApplication.getEmployeeId() != 0 && maxApproval1 == I_Leave.LEAVE_APPROVE_1) {
                                            if (objLeaveApplication.getDepHeadApproval() != 0) {
                                                stsDokApprov1 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        } else if (objLeaveApplication.getEmployeeId() != 0 && maxApproval1 == I_Leave.LEAVE_APPROVE_2) {
                                            if (objLeaveApplication.getDepHeadApproval() != 0 && objLeaveApplication.getHrManApproval() != 0) {
                                                stsDokApprov1 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        } else if (objLeaveApplication.getEmployeeId() != 0 && maxApproval1 == I_Leave.LEAVE_APPROVE_3) {
                                            if (objLeaveApplication.getGmApproval() != 0) {
                                                stsDokApprov1 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        }else if(objLeaveApplication.getEmployeeId() != 0 && maxApproval1 == I_Leave.LEAVE_APPROVE_5){
                                            if (objLeaveApplication.getHrManApproval() != 0) {
                                                stsDokApprov1 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }                                            
                                        }
                                    }
                                    
                                         try{
                                             objLeave = PstLeaveApplication.fetchExc(objLeaveApplication.getOID());
                                         }catch(Exception E){
                                             System.out.println("Exception "+E.toString());
                                         }
                                         objLeave.setDepHeadApproval(oidApprover);
                                         //updatre by satrya 2012-08-02
                                         objLeave.setDepHeadApproveDate(objLeaveApplication.getDepHeadApproveDate() ==null ? new Date() :  objLeaveApplication.getDepHeadApproveDate());//objLeaveApplication.getDepHeadApproveDate());
                                         
                                         if(stsDokApprov1 == true){
                                             objLeave.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED); 
                                         }
                                         
                                         try{                                            
                                             PstLeaveApplication.updateScheduleLeave(objLeave,stsDokApprov1);
                                         }catch(Exception E){
                                             System.out.println("Exception "+E.toString());
                                         }
                            break;
                            case 2 : objLeaveApplication.setHrManApproval(oidApprover);
                                     LeaveApplication objLeave2 = new LeaveApplication();
                                     
                                    I_Leave leaveConfig2 = null;

                                    try {
                                        leaveConfig2 = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
                                    } catch (Exception e) {
                                        System.out.println("Exception : " + e.getMessage());
                                    }

                                    int maxApproval2 = leaveConfig2.getMaxApproval(objLeaveApplication.getEmployeeId());

                                    boolean stsDokApprov2 = false;

                                    // if doc status Draft
                                    if (objLeaveApplication.getDocStatus() == 1) {

                                        if (objLeaveApplication.getEmployeeId() != 0 && maxApproval2 == I_Leave.LEAVE_APPROVE_1) {
                                            if (objLeaveApplication.getDepHeadApproval() != 0) {
                                                stsDokApprov2 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        } else if (objLeaveApplication.getEmployeeId() != 0 && maxApproval2 == I_Leave.LEAVE_APPROVE_2) {
                                            if (objLeaveApplication.getDepHeadApproval() != 0 && objLeaveApplication.getHrManApproval() != 0) {
                                                stsDokApprov2 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        } else if (objLeaveApplication.getEmployeeId() != 0 && maxApproval2 == I_Leave.LEAVE_APPROVE_3) {
                                            if (objLeaveApplication.getGmApproval() != 0) {
                                                stsDokApprov2 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        }else if(objLeaveApplication.getEmployeeId() != 0 && maxApproval2 == I_Leave.LEAVE_APPROVE_5){
                                            if (objLeaveApplication.getHrManApproval() != 0) {
                                                stsDokApprov2 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }                                            
                                        }
                                    }
                                     
                                         try{
                                             objLeave2 = PstLeaveApplication.fetchExc(objLeaveApplication.getOID());
                                         }catch(Exception E){
                                             System.out.println("Exception "+E.toString());
                                         }
                                         objLeave2.setHrManApproval(oidApprover);
                                         //update by satrya 2012-08-02
                                         objLeave2.setHrManApproveDate( objLeaveApplication.getHrManApproveDate() ==null ? new Date() :objLeaveApplication.getHrManApproveDate());//));
                                         
                                         if(stsDokApprov2 == true){
                                             objLeave2.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED); 
                                         }
                                         
                                         try{                                         
                                             PstLeaveApplication.updateScheduleLeave(objLeave2,stsDokApprov2);
                                         }catch(Exception E){
                                             System.out.println("Exception "+E.toString());
                                         }
                            break;
                            case 3 : objLeaveApplication.setGmApproval(oidApprover);
                                    LeaveApplication objLeave3 = new LeaveApplication();
                                    
                                    I_Leave leaveConfig3 = null;

                                    try {
                                        leaveConfig3 = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
                                    } catch (Exception e) {
                                        System.out.println("Exception : " + e.getMessage());
                                    }

                                    int maxApproval3 = leaveConfig3.getMaxApproval(objLeaveApplication.getEmployeeId());

                                    boolean stsDokApprov3 = false;

                                    // if doc status Draft
                                    if (objLeaveApplication.getDocStatus() == 1){

                                        if (objLeaveApplication.getEmployeeId() != 0 && maxApproval3 == I_Leave.LEAVE_APPROVE_1) {
                                            if (objLeaveApplication.getDepHeadApproval() != 0) {
                                                stsDokApprov3 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        } else if (objLeaveApplication.getEmployeeId() != 0 && maxApproval3 == I_Leave.LEAVE_APPROVE_2) {
                                            if (objLeaveApplication.getDepHeadApproval() != 0 && objLeaveApplication.getHrManApproval() != 0) {
                                                stsDokApprov3 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        } else if (objLeaveApplication.getEmployeeId() != 0 && maxApproval3 == I_Leave.LEAVE_APPROVE_3) {
                                            if (objLeaveApplication.getGmApproval() != 0) {
                                                stsDokApprov3 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }
                                        }else if(objLeaveApplication.getEmployeeId() != 0 && maxApproval3 == I_Leave.LEAVE_APPROVE_5){
                                            if (objLeaveApplication.getHrManApproval() != 0) {
                                                stsDokApprov3 = true;
                                                objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            }                                            
                                        }
                                    }
                                    
                                         try{                                             
                                             objLeave3 = PstLeaveApplication.fetchExc(objLeaveApplication.getOID());
                                            
                                         }catch(Exception E){
                                             System.out.println("Exception "+E.toString());
                                         }
                                         objLeave3.setGmApproval(oidApprover);
                                         //update by satrya 2012-08-02
                                         objLeave3.setGmApprovalDate(objLeaveApplication.getGmApprovalDate() ==null ? new Date() : objLeaveApplication.getGmApprovalDate());//objLeaveApplication.getGmApprovalDate());
                                         
                                         if(stsDokApprov3 == true){
                                             objLeave3.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED); 
                                         }
                                         
                                         try{
                                             
                                             PstLeaveApplication.updateScheduleLeave(objLeave3,stsDokApprov3);
                                             
                                         }catch(Exception E){
                                             System.out.println("Exception "+E.toString());
                                         }
                            break;
                        }                         
                    }
                 }
            }
    
    Employee objEmployee = new Employee();
    Division div = new Division();
    Department objDepartment = new Department();
    Position pos = new Position();
    //Long dtReq = FRMQueryString.requestLong(request, "requestDate");
    //Date requestDate = new Date(dtReq.longValue()); 
//update by satrya
Long dtReq = FRMQueryString.requestLong(request, "requestDateDaily");
java.util.Date requestDate = new Date();  
if(source!=null && "presence".equals(source)){
    requestDate = new Date(dtReq.longValue());
    //waktu add new melalui presence daily supaya submission datenya yg di pakai 
   if(objLeaveApplication!=null &&  iCommand == Command.ADD){
    objLeaveApplication.setSubmissionDate(requestDate);
   }
}                  
requestDate.setHours(0);
requestDate.setMinutes(0);
requestDate.setSeconds(0);
Vector cekLeaveForm = new Vector();
Vector cekExcuseForm = new Vector();
    if(source!=null && "presence".equals(source) && iCommand==Command.ADD){
        try{
            /*String empNum = FRMQueryString.requestString(request, "empNum");
            if (empNum != null && empNum.length() > 0) {
            objEmployee = PstEmployee.getEmployeeByNum(empNum);
            } else {
                long empId = FRMQueryString.requestLong(request, "empId");
                objEmployee = PstEmployee.fetchExc(empId);
            }*/
            //update by satrya 2012-07-27
            String empNum = FRMQueryString.requestString(request, "empNum");
            if (empNum != null && empNum.length() > 0) {
            objEmployee = PstEmployee.getEmployeeByNum(empNum);
            } else {
                long employeeId = FRMQueryString.requestLong(request, "employeeId");
                objEmployee = PstEmployee.fetchExc(employeeId);
            }
            
        } catch (Exception e) {
            objEmployee = new Employee();
            System.out.println("EXCEPTION "+e.toString());
        }        
    }else{
    if(oid_employee!= 0){
        try{
            objEmployee = PstEmployee.fetchExc(oid_employee);
        }catch(Exception e){
            objEmployee = new Employee();
            System.out.println("EXCEPTION "+e.toString());
        }
    }else {

        try{
           
            objEmployee = PstEmployee.fetchExc(objLeaveApplication.getEmployeeId());
        }catch(Exception e){
            objEmployee = new Employee();     
            System.out.println("EXCEPTION "+e.toString());          
        }
       }
    }

if(objEmployee!=null && objEmployee.getOID()>0  && objLeaveApplication.getSubmissionDate()!=null){
    //&& source!=null && source.equalsIgnoreCase("leave")
    Date start = (Date)objLeaveApplication.getSubmissionDate().clone(); 
     // start = DBHandler.convertDate(start, time)
       //     start.setHours(0);
         //   start.setMinutes(0);
           // start.setSeconds(0);
            Date end = (Date)objLeaveApplication.getSubmissionDate().clone();
            //end.setHours(23);
            //end.setMinutes(59);
           // end.setSeconds(59);
            String whereClause = PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+" Between"+"\""+Formater.formatDate(start, "yyyy-MM-dd 00:00:00")+"\" AND \""+Formater.formatDate(end, "yyyy-MM-dd 23:59:59")+"\" AND " 
                                + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+"="+objEmployee.getOID()+" AND "+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]+"="+PstLeaveApplication.LEAVE_APPLICATION;
            String order =  PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+ " ASC ";
            String whereClauseX = PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+" Between"+"\""+Formater.formatDate(start, "yyyy-MM-dd 00:00:00")+"\" AND \""+Formater.formatDate(end, "yyyy-MM-dd 23:59:59")+"\" AND " 
                                + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+"="+objEmployee.getOID()+" AND "+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]+"="+PstLeaveApplication.EXCUSE_APPLICATION;
            String orderX =  PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+ " ASC ";
           cekLeaveForm = PstLeaveApplication.list(0, 0, whereClause, order);
           cekExcuseForm = PstLeaveApplication.list(0, 0, whereClauseX, orderX);
}else if(objEmployee!=null &&  source!=null && source.equalsIgnoreCase("presence")){
    Date start = (Date)requestDate.clone();
            start.setHours(0);
            start.setMinutes(0);
            start.setSeconds(0);
            Date end = (Date)requestDate.clone();
            end.setHours(23);
            end.setMinutes(59);
            end.setSeconds(59);
            String whereClause = PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+" Between"+"\""+Formater.formatDate(start, "yyyy-MM-dd HH:mm:ss")+"\" AND \""+Formater.formatDate(end, "yyyy-MM-dd HH:mm:ss")+"\" AND " 
                                + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+"="+objEmployee.getOID();
            String order =  PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+ " ASC ";
           String whereClauseX = PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+" Between"+"\""+Formater.formatDate(start, "yyyy-MM-dd 00:00:00")+"\" AND \""+Formater.formatDate(end, "yyyy-MM-dd 23:59:59")+"\" AND " 
                                + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+"="+objEmployee.getOID()+" AND "+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]+"="+PstLeaveApplication.EXCUSE_APPLICATION;
            String orderX =  PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]+ " ASC ";
           cekLeaveForm = PstLeaveApplication.list(0, 0, whereClause, order);
           cekExcuseForm = PstLeaveApplication.list(0, 0, whereClauseX, orderX);
}
    long AlStockManagementIdALStockAktif = 0;
    long AlStockManagementIdLLStockAktif = 0;
    
    if(objEmployee.getOID() != 0){
        
        AlStockManagementIdALStockAktif = SessLeaveApplication.getALAktif(objEmployee.getOID());        
        AlStockManagementIdLLStockAktif = SessLeaveApplication.getLLAktif(objEmployee.getOID());        
        try {
            long oidDiv = objEmployee.getDivisionId();
            div = PstDivision.fetchExc(oidDiv);
        }
        catch(Exception e) {
            div = new Division();
        }

        try{
            objDepartment = PstDepartment.fetchExc(objEmployee.getDepartmentId());
        }catch(Exception e){
            objDepartment = new Department();
            System.out.println("EXCEPTION "+e.toString());
        }
               
        try {
            long oidPos = objEmployee.getPositionId();
            pos = PstPosition.fetchExc(oidPos);
        }
        catch(Exception e) {
            pos = new Position();
        }
    }

    /* Hanya untuk testing */
    //Date entitleDt_1 = SessLeaveApplication.getEntitle_I(objEmployee.getCommencingDate());
    //System.out.println("expired ent 1"+Formater.formatDate(entitleDt_1,"yyyy-MM-dd"));
    /* ----------- END --------- */

    
    int iErrCodeAL = FRMMessage.ERR_NONE;
    int iErrCodeLL = FRMMessage.ERR_NONE;
    int iErrCodeSpecial = FRMMessage.ERR_NONE;
    int iErrCodeDp = FRMMessage.ERR_NONE;
    String errMsgAL = "";
    String errMsgLL = "";
    String errMsgSpecialUnpaid = "";
    String errMsgDp = "";
    boolean checkBreak = false;
     Vector listal = new Vector(1,1);
   
    SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
    srcLeaveManagement.setEmpNum(objEmployee.getEmployeeNum());
   
    listal = SessLeaveApp.detailLeaveDPStock(srcLeaveManagement);
    //update by satrya 2012-10-24
   
    //update by satrya 2014-01-02
     AlStockTaken objAlStockTaken = new AlStockTaken();
    CtrlSpecialUnpaidLeaveTaken ctrlSpecialUnpaidTaken =null;
        if(leaveType == LEAVE_TYPE_AL){
            CtrlAlStockTaken ctrlAlStockTaken = new CtrlAlStockTaken(request);
            //update by satrya 2012-10-10            
            iErrCodeAL=ctrlAlStockTaken.action(iCommand, TakenALOid,listal);
            if((iErrCodeAL == 0 && iCommand == Command.SAVE) || (iErrCodeAL == 0 && iCommand == Command.DELETE)){
                indexAl = -1;
            }
            FrmAlStockTaken frmAlStockTaken = ctrlAlStockTaken.getForm();
            errMsgAL = ctrlAlStockTaken.getMessage(); 
            objAlStockTaken = ctrlAlStockTaken.getAlStockTaken();
         
        }else if(leaveType == LEAVE_TYPE_LL){
            CtrlLlStockTaken ctrlLlStockTaken = new CtrlLlStockTaken(request);
            iErrCodeLL=ctrlLlStockTaken.action(iCommand, TakenLLOid,listal);
            if((iErrCodeLL == 0 && iCommand == Command.SAVE) || (iErrCodeLL == 0 && iCommand == Command.DELETE)){
                indexLl = -1;
            }
            FrmLlStockTaken frmLlStockTaken = ctrlLlStockTaken.getForm();
            errMsgLL = ctrlLlStockTaken.getMessage(); 
        }else if(leaveType == LEAVE_TYPE_SPECIAL_UNPAID){
            ctrlSpecialUnpaidTaken = new CtrlSpecialUnpaidLeaveTaken(request);
            iErrCodeSpecial=ctrlSpecialUnpaidTaken.action( iCommand, TakenSpecialOid);
            if((iErrCodeLL == 0 && iCommand == Command.SAVE) || (iErrCodeSpecial == 0 && iCommand == Command.DELETE)){
                indexSpecial = -1;
            }
            FrmSpecialUnpaidLeaveTaken frmSpecialUnpaidLeaveTaken = ctrlSpecialUnpaidTaken.getForm();
            errMsgSpecialUnpaid = ctrlSpecialUnpaidTaken.getMessage(); 
        }else if(leaveType == LEAVE_TYPE_DP){
            CtrlDpStockTaken ctrlDpStockTaken = new CtrlDpStockTaken(request);
            iErrCodeDp=ctrlDpStockTaken.action(iCommand, TakenDpOid, listal);  
            if((iErrCodeDp == 0 && iCommand == Command.SAVE) || (iErrCodeDp == 0 && iCommand == Command.DELETE)){
                indexDP = -1;
            }
            FrmDpStockTaken frmDpStockTaken = ctrlDpStockTaken.getForm();
            errMsgDp = ctrlDpStockTaken.getMessage();             
        }
    listal = SessLeaveApp.detailLeaveDPStock(srcLeaveManagement);
     //update by satrya 2013-04-11
     RepItemLeaveAndDp repItemLeaveAndDp = new RepItemLeaveAndDp(); 
     if(listal!=null && listal.size()>0){
         repItemLeaveAndDp = (RepItemLeaveAndDp) listal.get(0);
     }
    //update by satrya 2013-04-11
    /*float eligibleLeave =0;
    if(objEmployee!=null && objEmployee.getOID()!=0){
        eligibleLeave = SessLeaveApp.getLeaveEligible(objEmployee.getOID());
    }*/
   /* update by satrya 2014-01-02 AlStockTaken objAlStockTaken = new AlStockTaken();*/
    LlStockTaken objLlStockTaken = new LlStockTaken();
    DpStockTaken objDpStockTaken = new DpStockTaken();
    SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
    
    Vector vectorAlStockTaken = new Vector();
    Vector vectorLlStockTaken = new Vector();
    Vector vectorDpStockTaken = new Vector();
    Vector vectorSpecialStockTaken = new Vector();
    Vector vectorUnpaidStockTaken = new Vector();
   
    String reason = "";
    
    if(objLeaveApplication.getOID()!=0){
        if(objLeaveApplication.getLeaveReason()==null){
            reason = "";
        }
    try{        
        String whereClause = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]+" = "+objLeaveApplication.getOID();
        vectorAlStockTaken = PstAlStockTaken.list(0, 0, whereClause, null);        
        
    }catch(Exception e){
        System.out.println("Exception "+e.toString());
    }  
    
    try{
        String whereClause = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]+" = "+objLeaveApplication.getOID();
        vectorLlStockTaken = PstLlStockTaken.list(0, 0, whereClause, null);        
    }catch(Exception e){
     System.out.println("Exception "+e.toString());}
    
    try{
        String whereClause = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+" = "+objLeaveApplication.getOID();  
        String order = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE];
        vectorDpStockTaken = PstDpStockTaken.list(0,0, whereClause, order);        
    }catch(Exception e){
        System.out.println("Exception "+e.toString());
    }
    
    //special and unpaid leave    
        
    try{
        vectorSpecialStockTaken = SessLeaveApplication.ListSpecialUnpaidLeave(objLeaveApplication.getOID(),type_form);                   
    }catch(Exception e){
        System.out.println("Exception "+e.toString());}        
  
    }
                               
    I_Leave leaveConfig = null;  
            
    try {
        leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
    }
    catch(Exception e) {
        System.out.println("Exception : " + e.getMessage());
    }

    int maxApproval = leaveConfig.getMaxApproval(objEmployee.getOID());
    ControlLine ctrLine = new ControlLine();
    Vector vctStrDetail = new Vector();
 
%>

<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>    
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - <%if(type_form== PstLeaveApplication.EXCUSE_APPLICATION  || objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION || repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO){%>Excuse Form <%}else{%>Leave Application <%}%>Request</title>
<script language="JavaScript">
</script>
<!-- #EndEditable -->

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 

<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->

<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #EndEditable -->

<!-- #BeginEditable "headerscript" --> 
<script language="JavaScript">

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
         
function fnTrapKD(){
   if (event.keyCode == 13) {
	document.all.aSearch.focus();
	cmdSearch();
   }
}
         
//-------------- script control date popup -------------------

function getALStartDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE])%>   
}

function getLLStartDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_TAKEN_DATE])%>   
}

function getLLEndDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_FINNISH_DATE])%>   
}

function getALEndDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE])%>
}

function getSpecialStartDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE])%>
}

function getSpecialEndDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE])%>
}

function getDpStartDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE])%>
}

function getDpFinishDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE])%>
}
   /* function getDpUnpaidDate(){
        <///%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_PAID_DATE])%>
    }*/
//-------------- script control date popup -------------------
function getSubMsDate(){    
     <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SUBMISSION_DATE])%>
}
    
function getDepartHeadApprovalDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVE_DATE])%>
}

function getHRApprovalDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVE_DATE])%>
}

function getGMApprovalDate(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_GM_APPROVE_DATE])%>
}   

function hideObjectForDate(index){
}

function showObjectForDate(){
} 

function cmdViewDPList(iCommands,eligibles){
    getDpStartDate();
    //update by satrya 2012-08-07
    getDpFinishDate();
   /// getDpUnpaidDate();
    var employeeId="<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_EMPLOYEE_ID]+"=")%>"+document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value;
    var leaveAppId ="<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_LEAVE_APPLICATION_ID]+"=")%>"+document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value;
    var dpStokId ="<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"=")%>"+document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]%>.value; 
    var strReDate = "<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_yr"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_yr")%>.value +
                   "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_mn"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_mn")%>.value +
                   "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_dy"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_dy")%>.value +
                   //update by satrya 2012-11-25
                   "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_hr"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_hr")%>.value +
                   "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_mi"+"=")%>"+
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_mi")%>.value +
                    "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE]+"_hr"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE]+"_hr")%>.value +
                   "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE]+"_mi"+"=")%>"+
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE]+"_mi")%>.value;
                   

         window.open("<%=approot%>/employee/leave/dp_available_list.jsp?"+employeeId+"&"+strReDate+"&"+leaveAppId+"&"+dpStokId+"&iCommands="+iCommands+"&eligibles="+eligibles, null, "height=600,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
}

//------------------- main processing ------------------------

function setEmployee(){
       window.open("<%=approot%>/employee/search/search.jsp?formName=frm_leave_application&empPathId=<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
    }
 function cmdClearSearchEmp(){
                    document.frm_leave_application.EMP_NUMBER.value = "";
                    document.frm_leave_application.EMP_FULLNAME.value = "";
                    document.frm_leave_application.EMP_DEPARTMENT.value = "";
                }
function cmdCancel()
{
        document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_APPLICATION %>;
        document.frm_leave_application.indexMain.value = 1;
	document.frm_leave_application.command.value="<%=String.valueOf(Command.CANCEL)%>";
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
} 


function cmdEdit(oid)
{ 
        document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_APPLICATION %>;
        document.frm_leave_application.indexMain.value = 1;
        document.frm_leave_application.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit(); 
} 

function cmdSave()
{
        document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_APPLICATION %>;
        document.frm_leave_application.indexAl.value = 0;
        document.frm_leave_application.indexLl.value = 0;
        document.frm_leave_application.indexSpecial.value = 0;
        document.frm_leave_application.indexDP.value = 0;
        document.frm_leave_application.indexMain.value = 1;
        getSubMsDate();        
        <%if(objLeaveApplication.getDocStatus()!=0){%>
        
        <%if(objLeaveApplication.getOID()!=0 && maxApproval== I_Leave.LEAVE_APPROVE_1){%>            
           
            getDepartHeadApprovalDate(); 
            
        <%}else if(objLeaveApplication.getOID()!=0 && maxApproval ==I_Leave.LEAVE_APPROVE_2){%>     
            
            getDepartHeadApprovalDate(); 
            getHRApprovalDate();
            
        <%}else if(objLeaveApplication.getOID()!=0 && maxApproval == I_Leave.LEAVE_APPROVE_3){%>  
            
            getHRApprovalDate();
            getGMApprovalDate();
            
        <%}%>
        <%}%>
        var empid=document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value;
        if(empid==0 || empid==""){
              alert("Please set employee");
            } else{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.SAVE)%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
        }
}
//update by satrya 2012-07-27
function cmdExecute(){

        document.frm_leave_application.command.value="<%=String.valueOf(Command.POST)%>"; 
        document.frm_leave_application.indexMain.value = 1;
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
       <%if(iCommand==Command.POST && iErrCode==FRMMessage.ERR_NONE){%>
           alert("your Employee is Execute");
           window.close();
       <%}%>
        //
} 
//update by satrya 2012-07-27
function cmdExit(){
        window.close();
} 

function cmdAsk(oid)
{
        document.frm_leave_application.indexMain.value = 1;
	document.frm_leave_application.command.value="<%=String.valueOf(Command.ASK)%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
} 
function cmdRel(oid)
{
	//document.frm_leave_application.command.value="<%//=String.valueOf(Command.ASK)%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
} 

function cmdConfirmDelete(oid)
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.DELETE)%>";
        document.frm_leave_application.indexMain.value = 1;
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp"; 
	document.frm_leave_application.submit();
}  

function cmdBack()
{
       document.frm_leave_application.command.value="<%=String.valueOf(Command.BACK)%>"; 
        ///document.frm_leave_application.command.value="<%//=String.valueOf(Command.FIRST)%>";
         <%if(((objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION) || (repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO) || (type_form==PstLeaveApplication.EXCUSE_APPLICATION))){%>
        document.frm_leave_application.action="<%=approot%>/employee/leave/excuse_app_list.jsp";
        <%}else{%> 
        document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_list.jsp";
        <%}%>
        
        document.frm_leave_application.submit();
}

/*function cmdBackExcuse()
{
	document.frm_leave_application.command.value="<%//=String.valueOf(Command.BACK)%>"; 
        ///document.frm_leave_application.command.value="<%//=String.valueOf(Command.FIRST)%>"; 
	document.frm_leave_application.action="<%//=approot%>/employee/leave/excuse_app_list.jsp";
	document.frm_leave_application.submit();
}*/
function cmdBackListExecuteExcuse()
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.BACK)%>"; 
        ///document.frm_leave_application.command.value="<%//=String.valueOf(Command.FIRST)%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/leave/excuse_app_execute.jsp";
	document.frm_leave_application.submit();
}
//update by satrya 2012-11-1
function cmdBackListExecute()
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.BACK)%>"; 
        ///document.frm_leave_application.command.value="<%//=String.valueOf(Command.FIRST)%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_execute.jsp";
	document.frm_leave_application.submit();
}

//------------------- utility functions ------------------------

function cmdEditDates(oidEmployee, oidLeave, oidSymbol, oidCategory, eligibleDay){
        window.open("<%=approot%>/employee/leave/leave_request_edit.jsp?employee_id=" + oidEmployee + "&leave_id=" + oidLeave + "&symbol_id=" + oidSymbol + "&category_id=" + oidCategory + "&day=" + eligibleDay, 
                     null, "height=300,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");        
} 

function cmdCancelAL(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";       
        document.frm_leave_application.indexAl.value = 0; 
        document.frm_leave_application.indexMain.value = 0;
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();        
} 

function cmdCancelLL(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";       
        document.frm_leave_application.indexLl.value = 0; 
        document.frm_leave_application.indexMain.value = 0;
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
        
}

function cmdCancelDP(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";        
        document.frm_leave_application.indexDP.value = 0; 
        document.frm_leave_application.indexMain.value = 0;
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
    
}

function cmdCancelSpecial(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";       
        document.frm_leave_application.indexSpecial.value = 0; 
        document.frm_leave_application.indexMain.value = 0;
	document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
	document.frm_leave_application.submit();
}

    
function cmdSaveAL(indexAl,TakenALOid){    
    getALStartDate();
    getALEndDate();
    document.frm_leave_application.command.value="<%=Command.SAVE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_AL %>;
    document.frm_leave_application.indexAl.value = indexAl;
    document.frm_leave_application.indexMain.value = 0;
    document.frm_leave_application.<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]%>.value = TakenALOid;
    document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
    document.frm_leave_application.submit();
}

function cmdSaveLL(indexLl,TakenLLOid){    
    getLLStartDate();
    getLLEndDate();
    document.frm_leave_application.command.value="<%=Command.SAVE%>";           
    document.frm_leave_application.indexMain.value = 0;
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_LL %>;
    document.frm_leave_application.indexLl.value = indexLl;
    document.frm_leave_application.<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]%>.value = TakenLLOid;
    document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
    document.frm_leave_application.submit();
}

function cmdSaveSpecial(indexSpecial,TakenSpecialOid){    
    getSpecialStartDate();
    getSpecialEndDate();
    document.frm_leave_application.command.value="<%=Command.SAVE%>";           
    document.frm_leave_application.indexMain.value = 0;
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_SPECIAL_UNPAID %>;
    document.frm_leave_application.indexSpecial.value = indexSpecial;
    document.frm_leave_application.<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]%>.value = TakenSpecialOid;
    document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
    document.frm_leave_application.submit();
}

function cmdSaveDP(indexDP,TakenDPOid){     
    var dpOid = document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]%>.value;
       
    if(dpOid == '' && <%=leaveConfig.getDPUnpaidIsNull()==true%>){
        
        alert ('Chose unpaid date first');
    
    }else{
        getDpStartDate();
        //update by satrya 2012-08-07
        getDpFinishDate();
        //getDpUnpaidDate();
        document.frm_leave_application.command.value="<%=Command.SAVE%>";           
        document.frm_leave_application.leaveType.value=<%= LEAVE_TYPE_DP %>;
        document.frm_leave_application.indexDP.value = indexDP;
        document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>.value = TakenDPOid;
        document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
        document.frm_leave_application.indexMain.value = 0;
        document.frm_leave_application.submit();
    }
}

function cmdDeleteAL(indexAl,TakenALOid){     
    var cfrm = confirm('Are you sure you want to delete AL request ?');
    if( cfrm==true){
    getALStartDate();
    getALEndDate();
    document.frm_leave_application.command.value="<%=Command.DELETE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_AL %>;
    document.frm_leave_application.indexAl.value = indexAl;
    document.frm_leave_application.<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]%>.value = TakenALOid;
    document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
    document.frm_leave_application.indexMain.value = 0;
    document.frm_leave_application.submit();
   }
}

function cmdDeleteLL(indexLl,TakenLLOid){     
    var cfrm = confirm('Are you sure you want to delete LL request ?');
    if( cfrm==true){
    getLLStartDate();
    getLLEndDate();
    document.frm_leave_application.command.value="<%=Command.DELETE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_LL %>;
    document.frm_leave_application.indexLl.value = indexLl;
    document.frm_leave_application.<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]%>.value = TakenLLOid;
    document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
    document.frm_leave_application.indexMain.value = 0;
    document.frm_leave_application.submit();
   }
}

function cmdDeleteSpecial(indexSpecial,TakenSpecialOid){     
    var cfrm = confirm('Are you sure you want to delete special leave request ?');
    if( cfrm==true){
    getSpecialStartDate();
    getSpecialEndDate();
    document.frm_leave_application.command.value="<%=Command.DELETE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_SPECIAL_UNPAID %>;
    document.frm_leave_application.indexSpecial.value = indexSpecial;
    document.frm_leave_application.<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]%>.value = TakenSpecialOid;
    document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
    document.frm_leave_application.indexMain.value = 0;
    document.frm_leave_application.submit();
   }
}

function cmdDeleteDP(indexDP,TakenDPOid){     
    var cfrm = confirm('Are you sure you want to delete DP request ?');
    if( cfrm==true){
    getALStartDate();
    getALEndDate();
    document.frm_leave_application.command.value="<%=Command.DELETE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_DP %>;
    document.frm_leave_application.indexDP.value = indexDP;
    document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>.value = TakenDPOid;
    document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
    document.frm_leave_application.indexMain.value = 0;
    document.frm_leave_application.submit();
   }
}


function cmdEditAL(oidEmployee,oidLeave,indexAl,TakenALOid){        
       getALStartDate();
       getALEndDate();
       document.frm_leave_application.command.value="<%=Command.EDIT%>";
       document.frm_leave_application.<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
       document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
       document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_AL %>;
       document.frm_leave_application.indexAl.value = indexAl;
       document.frm_leave_application.<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]%>.value = TakenALOid;
       document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
       document.frm_leave_application.indexMain.value = 0;
       document.frm_leave_application.submit();
} 

function cmdEditLL(oidEmployee,oidLeave,indexLl,TakenLlOid){        
    //function cmdEditLL(oidEmployee,oidLeave,indexLl,TakenLlOid){
       getLLStartDate();
       getLLEndDate();
       document.frm_leave_application.command.value="<%=Command.EDIT%>";
       document.frm_leave_application.<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
       document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
       document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_LL %>;
       document.frm_leave_application.indexLl.value = indexLl;
       document.frm_leave_application.<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]%>.value = TakenLlOid;
       document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
       document.frm_leave_application.indexMain.value = 0;
       document.frm_leave_application.submit();
}

function cmdEditDP(oidEmployee,oidLeave,indexDP,TakenDPOid){
       getDpStartDate();
       //update by satrya 2012-08-07
       getDpFinishDate();
      // getDpUnpaidDate();
       document.frm_leave_application.command.value="<%=Command.EDIT%>";
       document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
       document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
       document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_DP %>;
       document.frm_leave_application.indexDP.value = indexDP;
       document.frm_leave_application.indexMain.value = 0;
       document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>.value = TakenDPOid;
       document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
       document.frm_leave_application.submit();        
}

function cmdEditSpecial(oidEmployee,oidLeave,indexSpecial,TakenSpecialOid){        
       getSpecialStartDate();
       getSpecialEndDate();
       document.frm_leave_application.command.value="<%=Command.EDIT%>";
       document.frm_leave_application.<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_LEAVE_APLICATION_ID]%>.value = oidLeave;
       document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
       document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_SPECIAL_UNPAID %>;
       document.frm_leave_application.indexSpecial.value = indexSpecial;
       document.frm_leave_application.indexMain.value = 0;
       document.frm_leave_application.<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]%>.value = TakenSpecialOid;
       document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_edit.jsp";
       document.frm_leave_application.submit();        
}
function cmdOpen(oidLeaveApp,empId,requestDateDaily,typeLeaveForm){            
    //update by satrya 2013-11-15
    ////function cmdOpen(oidLeaveApp,empId,requestDateDaily){ 
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/employee/leave/leave_app_edit.jsp?source=leave&command=<%=(Command.EDIT)%>&<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>="+oidLeaveApp
                    +"&employeeId="+empId+"&requestDateDaily="+requestDateDaily+"&<%=PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]%>="+typeLeaveForm; 
                //update by satrya 2013-11-15
            //    var linkPage = "<//%=approot%>/employee/leave/leave_app_edit.jsp?source=leave&command=<//%=(Command.EDIT)%>&<//%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>="+oidLeaveApp
                 //   +"&employeeId="+empId+"&requestDateDaily="+requestDateDaily;  
           
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }


function checkApproval(index)
{
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_APPLICATION %>;
   
  var empLoggedIn = "<%=String.valueOf(objEmployee.getOID())%>";
	var empApprovalSelected = 0;
        if(index==1){
            empApprovalSelected = document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL]%>.value;
        }
        if(index==2){
            empApprovalSelected = document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL]%>.value;
        }
        if(index==3){
            empApprovalSelected = document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_GM_APPROVE]%>.value;
        }
	if(empLoggedIn != 0)
	{
		if(empApprovalSelected != 0)
		{
			
				document.frm_leave_application.command.value="<%=String.valueOf(Command.LIST)%>";
				document.frm_leave_application.indexApproval.value=index;
                                document.frm_leave_application.indexMain.value=1;
				document.frm_leave_application.oidApprover.value=empApprovalSelected;
				document.frm_leave_application.action="<%=approot%>/employee/leave/leave_app_approval.jsp";
                                
				document.frm_leave_application.submit();  		
			
		}
		else
		{
			alert('Please choose an authorized manager to approve this Leave Application ...');    					
		}
	}
	else
	{
            alert('You should login into Harisma as an authorized user ...');
            if(index==1){
		document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL]%>.value = "0";
            }   		
            if(index==2){
		document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL]%>.value = "0";
            }   		
            if(index==3){
		document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_GM_APPROVE]%>.value = "0";
            }   		
	}
}

function cmdPrint()
{ 
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveApplicationReportPdf?oidLeaveApplication=<%=objLeaveApplication.getOID()%>&approot=<%=approot%>&TYPE_FORM_LEAVE=<%=type_form%>";
    window.open(pathUrl);
}

///function reload
function reloadPage() {
    if(document.frm_leave_application.command.value == '<%= "" + Command.BACK %>'){
        document.frm_leave_application.command.value = '<%= "" + Command.NONE %>'       
        cmdSave();
    }
}
 function openLeaveOverlap(oidLeave)
{
	document.frm_leave_application.command.value="<%=Command.EDIT%>";
        document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
	document.frm_leave_application.action="leave_app_edit.jsp";
        document.frm_leave_application.indexMain.value = 1;
        document.frm_leave_application.submit();
}

</script>
<!-- #EndEditable -->

</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')" onfocus="javascript:reloadPage()">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){
         if(source==null || source.length()==0){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
         <%}else{%>
             <%@include file="../../styletemplate/template_header_no_menu.jsp" %>
         <%}%>
    <%}else{%>
<tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
</tr> 
<tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
      <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> 
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
            <td height="20"> 
                <!-- #BeginEditable "contenttitle" --> 
                <font color="#FF6600" face="Arial"><strong> 
                Employee &gt; <%if((type_form== PstLeaveApplication.EXCUSE_APPLICATION) || (objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION) || (repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO)){%>Excuse Application <%}else{%>Leave Application <%}%> &gt; <%if((type_form== PstLeaveApplication.EXCUSE_APPLICATION) || objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION || repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO){%>Excuse Application <%}else{%>Leave Application <%}%> &gt; <%if((type_form== PstLeaveApplication.EXCUSE_APPLICATION) || objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION || repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO){%>Excuse Form <%}else{%>Leave Form <%}%> <%if(objLeaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){out.println("Execute");}else{out.println("Request");}%>
                </strong></font> 
                <!-- #EndEditable --> 
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
                    <table style="border:1px solid <%=garisContent%>" width="100%" border="1" cellspacing="1" cellpadding="1" class="tabbg">
                    <tr>                     
                        <td valign="top">
                            
                        <!-- #BeginEditable "content" -->
                        <form name="frm_leave_application" method="post" action="">                            
                            <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>"> 
                            <!-- karena berpengaruh ketika user memilih delete lalu memilih pop up dp save, maka prosesnya masih ada deletnya -->
                            <!--<input type="hidden" name="command" value="<//%=String.valueOf(iCommand)%>"> -->
                            <input type="hidden" name="requestDateDaily" value="<%=(requestDate!=null? requestDate.getTime(): (new Date()).getTime() )%>">                             
                            <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>" value="<%=objLeaveApplication.getOID()%>">                             
                            <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>" value="<%=objLeaveApplication.getEmployeeId()!=0?objLeaveApplication.getEmployeeId():objEmployee.getOID() %>">     
                            <input type="hidden" name="<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]%>" value="<%=String.valueOf(TakenALOid)%>">     
                            <input type="hidden" name="<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]%>" value="<%=String.valueOf(TakenLLOid)%>"> 
                            <input type="hidden" name="<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]%>" value="<%=String.valueOf(TakenSpecialOid)%>"> 
                            <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>" value="<%=String.valueOf(TakenDpOid)%>"> 
                            <input type="hidden" name="leaveType" value="<%=String.valueOf(leaveType)%>">     
                            <!-- update by satrya 2014-02-08 -->
                            <input type="hidden" name="indexMain" value="<%=String.valueOf(indexMain)%>">                            
                            <input type="hidden" name="indexAl" value="<%=String.valueOf(indexAl)%>">                            
                            <input type="hidden" name="indexLl" value="<%=String.valueOf(indexLl)%>">                            
                            <input type="hidden" name="indexDP" value="<%=String.valueOf(indexDP)%>">                            
                            <input type="hidden" name="indexSpecial" value="<%=String.valueOf(indexSpecial)%>">                            
                            <input type="hidden" name="indexApproval" value="1">
                            <input type="hidden" name="oidApprover" value="0">
                            <input type="hidden" name="<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_ID]%>" value="<%=String.valueOf(AlStockManagementIdALStockAktif)%>">  
                            <input type="hidden" name="<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_ID]%>" value="<%=String.valueOf(AlStockManagementIdLLStockAktif)%>">                                                            
                             <input type="hidden" name="source" value="<%=source%>">
                             <input type="hidden" name="<%=PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]%>" value="<%=String.valueOf(type_form)%>">
                            <%if(cekLeaveForm!=null && cekLeaveForm.size()>0 || cekExcuseForm!=null && cekExcuseForm.size()>0){%> 
                            <table width="100%" border="1">
                            <tr>
                                
                                <td <%=cekLeaveForm!=null && cekLeaveForm.size()>0?"rowspan=\""+2+"\"":""%>  nowrap="nowrap"><B>Existing form for today: <br> <%=objLeaveApplication!=null && objLeaveApplication.getSubmissionDate()!=null ?Formater.formatDate( objLeaveApplication.getSubmissionDate(), "yyyy-MM-dd"): Formater.formatDate( new Date(), "yyyy-MM-dd") %></B></td>
                              <%
                           
                            String note="";
                            if(cekLeaveForm!=null && cekLeaveForm.size()>0){
                                for(int x=0;x<cekLeaveForm.size();x++){
                                    LeaveApplication leaveApplicationx = (LeaveApplication)cekLeaveForm.get(x);
                                     String reasonx = leaveApplicationx.getLeaveReason();
                                     //smile menjadi mile
                                     if(reasonx.length()>=10){ 
                                    //  String sLimit = reasonx.substring(0, 10);//reasonx.length()-10, reasonx.length());
                                      reasonx = reasonx.substring(0, 10 )+ " ..."; //reasonx.replace(sLimit, "....");
                                     }
                                    String docStatus = PstLeaveApplication.fieldStatusApplication[leaveApplicationx.getDocStatus()]; 
                                    if(leaveApplicationx.getDocStatus()== PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                      note = note +" <B>Reason:</B> <a href=\"javascript:cmdOpen('"+leaveApplicationx.getOID()+"','"+leaveApplicationx.getEmployeeId()+ "','" + leaveApplicationx.getSubmissionDate().getTime() + "','" + PstLeaveApplication.LEAVE_APPLICATION + "')\">"+reasonx+"["+docStatus+"],</a>";  
                                    }else{
                                              note = note +" <B>Reason:</B> <a href=\"javascript:cmdOpen('"+leaveApplicationx.getOID()+"','"+leaveApplicationx.getEmployeeId()+ "','" + leaveApplicationx.getSubmissionDate().getTime() + "','" + PstLeaveApplication.LEAVE_APPLICATION + "')\">"+reasonx+"<blink>["+docStatus+"]</blink>,</a>";  
                                    }
                                    
                                }
                                 //note= note.substring(1,1); 
                             }
                                
                                if(cekLeaveForm!=null && cekLeaveForm.size()>0){
                            %>
                                <td width="5%" nowrap="nowrap">Leave Form Aplication</td>
                                <td width="95%" nowrap="nowrap"><%=note%></td>
                              <%}%>
                            </tr>
                            <%
                           
                            String noteExc="";
                            if(cekExcuseForm!=null && cekExcuseForm.size()>0){
                                for(int x=0;x<cekExcuseForm.size();x++){
                                    LeaveApplication leaveApplicationExc = (LeaveApplication)cekExcuseForm.get(x);
                                     String reasonExc = leaveApplicationExc.getLeaveReason(); 
                                    String docStatusExc = PstLeaveApplication.fieldStatusApplication[leaveApplicationExc.getDocStatus()]; 
                                    if(reasonExc.length()>=10){ 
                                     //String sLimit = reasonExc.substring(reasonExc.length()-10, reasonExc.length());
                                     //reasonExc = reasonExc.replace(sLimit, "...."); 
                                        reasonExc = reasonExc.substring(0, 10 )+ " ...";
                                     }
                                 
                               
                                 if(leaveApplicationExc.getDocStatus()== PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                      noteExc = noteExc +" <B>Reason:</B> <a href=\"javascript:cmdOpen('"+leaveApplicationExc.getOID()+"','"+leaveApplicationExc.getEmployeeId()+ "','" + leaveApplicationExc.getSubmissionDate().getTime()+ "','" + PstLeaveApplication.EXCUSE_APPLICATION+ "')\">"+reasonExc+"["+docStatusExc+"],</a>";  
                                    }else{
                                       noteExc = noteExc +" <B>Reason:</B> <a href=\"javascript:cmdOpen('"+leaveApplicationExc.getOID()+"','"+leaveApplicationExc.getEmployeeId()+ "','" + leaveApplicationExc.getSubmissionDate().getTime() + "','" + PstLeaveApplication.EXCUSE_APPLICATION + "')\">"+reasonExc+"<blink>["+docStatusExc+"]</blink>,</a>";  
                                    }
                                }
                                //noteExc= noteExc.substring(0,noteExc.length()-1);
                             }
                                
    
                            %>
                           <%if(noteExc!=null && noteExc.length()>0){%>
                            <tr>
                               <td width="5%" nowrap="nowrap">Excuse Form Aplication</td>
                                                             
                              <td width="95%" nowrap="nowrap"><%=noteExc%></td>
                            </tr>
                           <%}%>
                          </table>
                            <table>
                                <tr>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
                            <%}%>
                            <div align="center">
                                <%if(repItemLeaveAndDp.getStatusKaryawan()!=0){%>
                                    <% 
                             if(type_form== PstLeaveApplication.LEAVE_APPLICATION){
                                 if(iCommand==Command.ADD){
                                    Vector vectValsLeaveType = new Vector(1,1);
                                    Vector vectListsLeaveType = new Vector(1,1);
                                            vectValsLeaveType.add(""+PstLeaveApplication.LEAVE_APPLICATION);
                                            vectListsLeaveType.add("LEAVE APPLICATION");
                                            vectValsLeaveType.add(""+PstLeaveApplication.EXCUSE_APPLICATION);
                                            vectListsLeaveType.add("EXCUSE_APPLICATION");
                                            
                                    out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_TYPE_FORM_LEAVE], null, ""+PstLeaveApplication.strLeaveAppType[objLeaveApplication.getTypeFormLeave()], vectValsLeaveType, vectListsLeaveType,"")); if(objLeaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){out.println("EXECUTE");}else{out.println("");}
                                    }else{
                                        %>
                                        <h2><%=""+PstLeaveApplication.strLeaveAppType[objLeaveApplication.getTypeFormLeave()]%> <%if(objLeaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){out.println("EXECUTE");}else{out.println("");}%></h2>
                                    <%}
                                 
                             }else{%>
                                       <h2>EXCUSE APPLICATION <%if(objLeaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){out.println("EXECUTE");}else{out.println("");}%></h2>
                                    <% objLeaveApplication.setTypeFormLeave(PstLeaveApplication.EXCUSE_APPLICATION);%>
                             <%}%>
                                <!--<h2>LEAVE APPLICATION <%//if(objLeaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){out.println("EXECUTE");}else{out.println("");}%></h2>-->
                                    
                                <%}else{%>
                                    <h2>EXCUSE APPLICATION <%if(objLeaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){out.println("EXECUTE");}else{out.println("");}%></h2>
                                    <% objLeaveApplication.setTypeFormLeave(PstLeaveApplication.EXCUSE_APPLICATION);%>
                                <%}%>
                                 <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_TYPE_FORM_LEAVE]%>" value="<%=objLeaveApplication.getTypeFormLeave()%>">
                                 <input type="hidden" name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_TYPE_FORM]%>" value="<%=objLeaveApplication.getTypeFormLeave()%>">
                            </div> 
                            
<table width="100%" align="center">
  <tr>
       <% 
        boolean showImgEmployee = false;
        try{
            showImgEmployee = Boolean.parseBoolean(String.valueOf(PstSystemProperty.getValueByName("USE_PICTURE_EMPLOYEE_IN_LEAVE_FORM")));  
        }catch(Exception E){
            showImgEmployee = false;
            //System.out.println("EXCEPTION USE_PICTURE_EMPLOYEE_IN_LEAVE_FORM : "+E.toString());
        }
        if(showImgEmployee){%>
    <td width="12%" rowspan="6" valign="top">
    
        <table>
            <tr>
                <td>
                    <%
                    String pictPath = "";
                    try {
                        SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                        pictPath = sessEmployeePicture.fetchImageEmployee(objEmployee.getOID());

                    } catch (Exception e) {
                        System.out.println("err." + e.toString());
                    }%><%
                    if (pictPath != null && pictPath.length() > 0) {
                    out.println("<img width=\"140\" height=\"150\"  src=\"" + approot + "/" + pictPath + "\">");
                    } else {
                    %>
                    <img width="140" height="150" src="<%=approot%>/imgcache/no_photo.JPEG"></image>
                    <%

                        }
                    %>
                </td>
            </tr>
        </table>
     
    </td>
     <%}%>
    <td width="4%">&nbsp;</td>
    <td width="8%">Payroll</td>
    <td width="1%">:</td>
    <td width="26%"><input type="text" name="EMP_NUMBER"  value="<%=objEmployee.getEmployeeNum()%>" class="elemenForm" size="25" disabled></td>
    <td width="3%">&nbsp;</td>
    <td colspan="3">
        <table cellpadding="0" cellspacing="0" border="0">
                        <tr>
                         <% 
                        /*
                         * Description : mengambil user name pada AppUser
                         * Date : 2015-01-20
                         * Author : Hendra Putu
                        */
                        SessUserSession userSessionn = (SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME);
                        AppUser appUserSess1 = userSessionn.getAppUser();
                        String namaUser1 = appUserSess1.getFullName();
                            if(objLeaveApplication.getDocStatus()!=PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                            // update by Hendra McHen 2015-01-21
                                if(!(namaUser1.equals("Employee"))){
                         %>
                         <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                         <td width="15"><a href="javascript:setEmployee()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                         <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                         <td class="command" nowrap width="99"> 
                         <div align="left"><a href="javascript:setEmployee()">Search Employee</a></div>
                         </td>
                         <td width="15"><img src="<%=approot%>/images/spacer.gif" width="15" height="4"></td>
                         <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                         <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                         <td class="command" nowrap width="99"> 
                         <div align="left "><a href="javascript:cmdClearSearchEmp()">Clear Search</a></div>
                              <%
                                   }
                                }
                            %> <!-- update by satrya 2012-07-27 /// jika status'nya tidak excecute-->
                         </td>
                         </tr>
          </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Name</td>
    <td>:</td>
    <td><strong><input type="text" name="EMP_FULLNAME"  value="<%=objEmployee.getFullName()%>" class="elemenForm" size="30"  disabled></strong></td>
    <td>&nbsp;</td>
    <td width="21%">Position</td>
    <td width="2%">:</td>
    <td width="23%"><strong><input type="text" name="EMP_POSITION"  value="<%=pos.getPosition()%>" class="elemenForm" size="35"  disabled></strong></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
    <td>:</td>
    <td><strong><input type="text" name="EMP_DIVISION"  value="<%=div.getDivision()%>" class="elemenForm" size="43"  disabled> </strong></td>
    <td>&nbsp;</td>
    <td>Commencing Date</td>
    <td>:</td>
    <td><input type="text" name="EMP_COMENCING"  value="<%= objEmployee.getCommencingDate() == null ? "" : Formater.formatDate(objEmployee.getCommencingDate(), "MMMM dd, yyyy") %>" class="elemenForm" size="25"  disabled></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
    <td>:</td>
    <td><strong><input type="text" name="EMP_DEPARTMENT"  value="<%= objDepartment.getDepartment() %>" class="elemenForm" size="30"  disabled> </strong></td>
    <td>&nbsp;</td>
    <td>Date of Request</td>
    <td>:</td>
    <td>
        <strong>
            <% //update by satrya 2012-07-27
                                    if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                        out.println("<B>"+Formater.formatDate(objLeaveApplication.getSubmissionDate(), "MMMM dd, yyyy")+"</B>");
                                    }
                                        else{
                                        out.println(ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SUBMISSION_DATE],(objLeaveApplication.getSubmissionDate() != null ? objLeaveApplication.getSubmissionDate() : new Date()), "getSubMsDate()"));
                                        }
                 %>
        </strong>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Date Off Request</td>
    <td>:</td>
    <td><strong>
                                    <% 
                                        Vector vectListSts = new Vector();
                                        Vector vectValSts = new Vector();
                                        
                                        if(objEmployee.getOID()==0 || (source!=null && source.equalsIgnoreCase("presence") && objLeaveApplication.getOID()==0)){
                                        //update by satrya 2014-02-03
                                        // if(objEmployee.getOID()==0){
                                            
                                            %>
                                            <B>Draft</B>
                                            <%
                                            
                                        }else{
                                            
                                        if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT);
                                            vectListSts.add("Draft");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE);
                                            vectListSts.add("To be approved");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED);
                                            vectListSts.add("Canceled");
                                        
                                        }else if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE){
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT);
                                            vectListSts.add("Draft");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE);
                                            vectListSts.add("To be approved");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED);
                                            vectListSts.add("Canceled");
                                            
                                        }else if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED){
                                            
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT);
                                            vectListSts.add("Draft");                                            
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            vectListSts.add("Approved");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED);
                                            vectListSts.add("Canceled");
                                            
                                        }else if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED){
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED);
                                            vectListSts.add("Canceled");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT);
                                            vectListSts.add("Draft");
                                            
                                        }
                                        
                                        if(objLeaveApplication.getDocStatus()!=PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                            
                                            int selectedAproved = objLeaveApplication.getDocStatus();
                                            String selected = ""+selectedAproved;
                                        
                                            out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DOC_STATUS], null, selected, vectValSts, vectListSts,""));														  
                                        }else{
                                            %>
                                            <B>Executed</B>
                                            <%
                                        }
                                        }
                                    %>
                                    </strong>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
                            <table width="99%" align="center">
                                                 
                        <tr>
                            <td>
                            <% Vector res = new Vector();
                               try{
                               if(type_form==PstLeaveApplication.LEAVE_APPLICATION && objLeaveApplication!=null && objLeaveApplication.getOID()!=0 && (objLeaveApplication.getTypeFormLeave()==0 && repItemLeaveAndDp.getStatusKaryawan()!=0)){    
                                   //update by satrya 2013-04-11
                                   //if(objLeaveApplication!=null && objLeaveApplication.getOID()!=0 ){
                               //res=drawListAnnualLeave(listal, vectorAlStockTaken,indexAl, objLeaveApplication.getEmployeeId() ,oidLeave, objLeaveApplication.getDocStatus(),request);                            
                               //Date requestDateLeave =  objLeaveApplication.getSubmissionDate();
                                res=drawListAnnualLeave(listal, vectorAlStockTaken,indexAl, objLeaveApplication.getEmployeeId() ,oidLeave, objLeaveApplication.getDocStatus(),objLeaveApplication.getSubmissionDate() !=null ? objLeaveApplication.getSubmissionDate() : new Date(),objAlStockTaken);                            
                               //update by satrya 2012-10-12
                               // res=drawListAnnualLeave(listal, vectorAlStockTaken,indexAl, objLeaveApplication.getEmployeeId() ,oidLeave, objLeaveApplication.getDocStatus(),objLeaveApplication.getSubmissionDate() !=null ? objLeaveApplication.getSubmissionDate() : new Date());                            
                               
                               vctStrDetail = (Vector)res.get(1);                              
                              
                            %>
                            <%= res.get(0) %>                            
                            <% } else{
                                  if(objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()!=PstLeaveApplication.EXCUSE_APPLICATION){ 
                                   out.println("Please save first to see the detail");
                                  }
                                }
                               } catch(Exception exc){                                                                
                                 System.out.println(exc);
                            } %>
                            </td>
                        </tr>
                        <% 
                        if(iErrCodeAL != CtrlAlStockTaken.RSLT_OK){
                        %>   
                        <tr>
                            <td class="msgerror">
                                <font color = "FF0000">
                             <% 
                               
                                 String errorAl = CtrlAlStockTaken.resultText[CtrlLlStockTaken.LANGUAGE_FOREIGN][iErrCodeAL]; 
                                System.out.println("Message : "+errorAl);
                             %>   
                                Message : <%=errMsgAL +":"+errorAl%><%//=errorAl%></font>                            
                          </td>
                        </tr>
                        <% 
                        }
                        %> 
                        <tr>
                            <td>&nbsp;&nbsp;           
                            </td>
                        </tr>
                        <%
                          String useLongLeave ="";
                            try{
                                useLongLeave = String.valueOf(PstSystemProperty.getValueByName("USE_LONG_LEAVE"));  
                            }catch(Exception E){
                                useLongLeave= "1";
                                System.out.println("EXCEPTION SYS PROP USE_LONG_LEAVE : "+E.toString());
                            }
                               
                            if( type_form==PstLeaveApplication.LEAVE_APPLICATION &&(useLongLeave==null || useLongLeave.equals("1")) && (objLeaveApplication!=null && objLeaveApplication.getOID()!=0 )  && (objLeaveApplication.getTypeFormLeave()==0 && repItemLeaveAndDp.getStatusKaryawan()!=0)){                           
                                //update by satrya 2013-04-11 
                                //if( (useLongLeave==null || useLongLeave.equals("1")) && (objLeaveApplication!=null && objLeaveApplication.getOID()!=0 )  ){                           
                         %>
                        
                        <tr>
                            <td>
                            <% Vector vectdrawListLongLeave =  new Vector();
                                  try{
                                   vectdrawListLongLeave=drawListLongLeave(listal, vectorLlStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexLl, objLeaveApplication.getDocStatus(),requestDate);
                                   //vectdrawListLongLeave=drawListLongLeave(listal, vectorLlStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexLl, objLeaveApplication.getDocStatus());
                                   vctStrDetail = (Vector)vectdrawListLongLeave.get(1);
                                %>
                                <%= vectdrawListLongLeave.get(0) %>
                                <% } catch(Exception exc){
                                    System.out.println(exc);
                                }                             
                             %>
                            </td>
                        </tr>
                        <% 
                        if(iErrCodeLL != CtrlLlStockTaken.RSLT_OK || (errMsgLL!=null&& errMsgLL.length()>0)){
                        %>   
                        <tr>
                            <td class="msgerror">
                                <font color = "FF0000">
                             <% 
                                
                             
                               String errorLl = CtrlLlStockTaken.resultText[CtrlLlStockTaken.LANGUAGE_FOREIGN][iErrCodeLL];
                               
                                //System.out.println("Message : "+errorLl);
                                //System.out.println("Message : "+errorLlSpc);
                             %>   
                                Message : <%=errMsgLL +":"+errorLl%><%//=errorLl%></font>                            
                          </td>
                        </tr>
                        <% 
                        }
                        %>  
                        <tr>
                            <td>&nbsp;&nbsp;           
                            </td>
                        </tr>
                       <%}
                        %>
                        <tr>
                            <td>
                                
                            <% if(objLeaveApplication!=null && objLeaveApplication.getOID()!=0){
                              
                              Vector vectdrawlistSpecialLeave = new Vector();
                               try{
                                   //update by satrya 2012-07-31
                              /// Date requestDateLeave =  objLeaveApplication.getSubmissionDate();
                                vectdrawlistSpecialLeave =drawListSpecialUnpaidLeave(vectorSpecialStockTaken, objLeaveApplication ,oidLeave, indexSpecial, objLeaveApplication.getDocStatus(),objLeaveApplication.getSubmissionDate() !=null ? objLeaveApplication.getSubmissionDate() : new Date(),repItemLeaveAndDp.getStatusKaryawan(),type_form);
                                //update by satrya 2013-04-11
                                //vectdrawlistSpecialLeave =drawListSpecialUnpaidLeave(vectorSpecialStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexSpecial, objLeaveApplication.getDocStatus(),objLeaveApplication.getSubmissionDate() !=null ? objLeaveApplication.getSubmissionDate() : new Date());
                                //vectdrawlistSpecialLeave =drawListSpecialUnpaidLeave(vectorSpecialStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexSpecial, objLeaveApplication.getDocStatus());                            
                               vctStrDetail = (Vector)vectdrawlistSpecialLeave.get(1);
                                                             
                            %>
                            <%= vectdrawlistSpecialLeave.get(0) %>
                            <% } catch(Exception exc){                                                                
                                 System.out.println(exc);
                            } } %>
                            </td>
                        </tr>  
                        <!-- <tr>
                            
                            <td 
                                <//%=(iErrCodeSpecial!=ctrlSpecialUnpaidTaken.RSLT_OK?"class=\"msgerror\"":"")%>>&nbsp;&nbsp;<//%=(ctrlSpecialUnpaidTaken!=null? ctrlSpecialUnpaidTaken.getMessage() :"") %>
                            </td>
                             
                        </tr>-->
                    <% 
                        if(iErrCodeSpecial != CtrlSpecialUnpaidLeaveTaken.RSLT_OK){
                        %>   
                        <tr>
                            <td class="msgerror">
                                <font color = "FF0000">
                             <% 
                                String errorSP = "";                                
                                
                                errorSP = CtrlSpecialUnpaidLeaveTaken.resultText[CtrlSpecialUnpaidLeaveTaken.LANGUAGE_FOREIGN][iErrCodeSpecial];
                               // System.out.println("Message : "+errorSP);
                             %>   
                                Message :  <%=errMsgSpecialUnpaid +":"+errorSP%> <%//=errorSP%></font>                            
                          </td>
                        </tr>
                        <% 
                        }
                        %> 
                        <tr>
                            <td>
                             <% if(objLeaveApplication!=null && objLeaveApplication.getOID()!=0 && (objLeaveApplication.getTypeFormLeave()==0 && repItemLeaveAndDp.getStatusKaryawan()!=0) && type_form==PstLeaveApplication.LEAVE_APPLICATION){
                                 //update by satrya 2013-04-11
                                 // if(objLeaveApplication!=null && objLeaveApplication.getOID()!=0 ){
                               try{ 
                                   //update by satrya 2012-07-31
                                //Date requestDateLeave =  objLeaveApplication.getSubmissionDate();
                                Vector vectdrawlistDpLeave = drawListDPLeave(listal   ,vectorDpStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexDP, objLeaveApplication.getDocStatus(), objLeaveApplication.getSubmissionDate() !=null ? objLeaveApplication.getSubmissionDate(): new Date(),iCommand);                            
                               //Vector vectdrawlistDpLeave = drawListDPLeave(listal,vectorDpStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexDP, objLeaveApplication.getDocStatus());                            
                               vctStrDetail = (Vector)vectdrawlistDpLeave.get(1);
                               //tempTotalDp = (Float)vectdrawlistDpLeave.get(2);   
                            %>
                            <%=vectdrawlistDpLeave.get(0) %> <% }catch(Exception exc ){
                                System.out.println(exc);
                            }} %>
                            </td>
                        </tr>   
                        <% 
                        if(iErrCodeDp != CtrlDpStockTaken.RSLT_OK){
                        %>   
                        <tr>
                            <td class="msgerror">
                                <font color = "FF0000">
                             <% 
                                String errorDp = "";
                                
                                errorDp = CtrlDpStockTaken.resultText[CtrlDpStockTaken.LANGUAGE_FOREIGN][iErrCodeDp];
                                //errorDp = CtrlDpStockTaken.resultText[CtrlDpStockTaken.LANGUAGE_FOREIGN][CtrlDpStockTaken.RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE];
                                //System.out.println("Message : "+errorDp);iErrCodeDp
                             %>   
                                Message : <%=errMsgDp + " : " + errorDp%><%//=errorDp%></font>                            
                          </td>
                        </tr>
                        <% 
                        }
                        %> 
                        <tr>
                            <td>
                               <!-- devin hijau -->
                            <table width="100%" bgcolor="#E0EDF0">
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3"><b><%if(type_form==PstLeaveApplication.EXCUSE_APPLICATION  || objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION || repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO){%>Excuse Form<%}else{%>Leave<%}%> Reason</b><td>
                            </tr>
                            <tr>
                                <td width="20%">Reason <%if(type_form==PstLeaveApplication.EXCUSE_APPLICATION  || objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION || repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO){%>Excuse Form<%}else{%>leave application<%}%></td>
                                <td width="5%">:</td>
                                <td width="75%">
                                      <% if(objLeaveApplication.getDocStatus() !=PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){%>
                                    <input type="text" name="<%= FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_REASON] %>" value="<%= objLeaveApplication.getLeaveReason() %>" size="50">
                                       <% } else{out.println("<B>"+objLeaveApplication.getLeaveReason()+"</B>");}%>
                                </td>
                            </tr>                           
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            </table>
                            </td>
                        </tr>                       
                        
                        <tr> 
                            <td valign="top"> 
                            <% 
                             if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                %><blink>To view approval form, please set document status to TO BE APPROVED</<blink><% 
                             } else {
                                   
                                   boolean statusSchedule = SessLeaveApplication.CekScheduleExist(objLeaveApplication.getOID());
                                   
                                   if(statusSchedule==false){
                                       
                                       %><font color="FF0000" ><blink>To view approval form, please set Schedule first</bink> , <a href="<%=approot%>/employee/attendance/empschedule_edit.jsp" >click here </a> </font><% 
                                       
                                   }else{
                                   
                                   
                            %>
                              <table width="100%" border="0"  cellpadding="1" cellspacing="1" bgcolor="#E0EDF0">
                                <tr> 
                                  <td valign="top"> 
                                    <table width="100%" border="0" bgcolor="#E0EDF0">
                                      <tr>
                                                <td width="15%" align="left"><b>Approval</b></td >
                                                <td width="2%" ></td>
                                                <td width="60%" align="center"><b>Signature</b></td>
                                                <td width="23%" align="center"><b>Date</b></td>
                                            </tr>
                                      <!--Employee-->
                                      <tr>
                                          <td width="15%" >Employee</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b><%=objEmployee.getFullName()%></b></td>
                                          <td width="23%" ><b>
                                              <%=Formater.formatDate(objLeaveApplication.getSubmissionDate(), "MMMM dd, yyyy")%>
                                          </b></td>
                                      </tr>
                                      <!--Dept Head-->
                                      <%
                                        boolean deptHead = false;
                                        boolean HRDMan = false;
                                      
                                      if((objLeaveApplication.getOID()!=0) && (maxApproval== I_Leave.LEAVE_APPROVE_1 || maxApproval ==I_Leave.LEAVE_APPROVE_2 )){
                                          deptHead = true;
                                      %>
                                      <tr>
                                          <td width="15%" >Approved by</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                               <%												  
                                               
                                                          
                                                  if(iErrCode==0 && objLeaveApplication.getOID()!=0) 
                                                  {
                                                     if((objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE ||
                                                        objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT) && 
                                                        objLeaveApplication.getTypeLeave()!= PstPublicLeaveDetail.TYPE_LEAVE){
                                                          Vector divHeadKey = new Vector(1,1);
                                                          Vector divHeadValue = new Vector(1,1);
                                                          divHeadKey.add("Select Approver");
                                                          divHeadValue.add("0");
                                                            
                                                          Vector listDivHead = leaveConfig.getApprovalDepartmentHead(objLeaveApplication.getEmployeeId());
                                                          
                                                          if(listDivHead != null && listDivHead.size()>0){
                                                          
                                                                for(int i=0; i<listDivHead.size(); i++)
                                                                {
                                                                    Employee objEmp = (Employee)listDivHead.get(i);

                                                                    divHeadKey.add(objEmp.getFullName());
                                                                    divHeadValue.add(""+objEmp.getOID());
                                                                   
                                                                }
                                                          
                                                          }
                                                          
                                                          String selectedApproval = ""+objLeaveApplication.getDepHeadApproval();                                                          
                                                          String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(1)\"";
                                                          //if(objLeaveApplication.getDocStatus()!=PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                                          //update by satrya 2014-01-18
                                                          //out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                          if(objLeaveApplication.getDepHeadApproval()!=0 && leaveConfig.CanNotChangeApproval()){ 
                                                              out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, "disabled"));														  
                                                         }else{
                                                              out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                         }
                                                         //out.println(ControlCombo.);
                                                          ///update by satrya 2012-08-01
                                                          //jika statusnya sdh execute
                                                      }else{
                                                           Employee objEmpName = new Employee();
                                                            try{
                                                          objEmpName = PstEmployee.fetchExc(objLeaveApplication.getDepHeadApproval());
                                                         //out.println("<B>"+objEmpName.getFullName()+"</B>");
                                                          out.println("<B>"+objEmpName.getFullName()+"</B>"
                                                                  +"<input type=\"hidden\" name=\""+FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL]+"\" value=\""+objLeaveApplication.getDepHeadApproval()+"\">");														  
                                                          
                                                            }catch(Exception e){System.out.println(e);}
                                                }
                                                 }
                                                Vector vctSession = new Vector();                                               
                                                vctSession.add(vctStrDetail);
                                                vctSession.add(""+maxApproval);

                                                session.putValue("LEAVE_APPLICATION", vctSession);							  		  
                                                					  		  
                                          %>

                                          </b></td>                                          
                                          
                                          <td width="23%" ><b>
                                                  <% //update by satrya 2012-07-23   
                                                  if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE ||
                                                        objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                                       out.println(ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVE_DATE],(objLeaveApplication.getDepHeadApproveDate()==null ? new Date() : objLeaveApplication.getDepHeadApproveDate()), "getDepartHeadApprovalDate()"));
                                                  }else{
                                                    out.println("<B>"+Formater.formatDate(objLeaveApplication.getDepHeadApproveDate() !=null ? objLeaveApplication.getDepHeadApproveDate() : new Date(), "MMMM dd, yyyy")+"</B>");
                                                    out.println(ControlDatePopup.writeDateDisabled(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVE_DATE],(objLeaveApplication.getDepHeadApproveDate()==null ? new Date() : objLeaveApplication.getDepHeadApproveDate())));
                                                  }
                                               %>
                                              <%--if(specialLeave.getApprovalId()>0){%>
                                              <%}--%>
                                        </b></td>
                                      </tr>

                                      <%}%>
                                      <!--Exc Prod-->
                                      <%
                                      if(objLeaveApplication.getOID()!=0 && (maxApproval == I_Leave.LEAVE_APPROVE_2 || maxApproval == I_Leave.LEAVE_APPROVE_3 || maxApproval == I_Leave.LEAVE_APPROVE_5) ){
                                          HRDMan = true;
                                      %>
                                      <tr>
                                          <td width="15%" >HRD Approved by</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                          <%                                               												  
                                                    if(iErrCode==0 &&objLeaveApplication.getOID()!=0)
                                                    {
                                                         if(objLeaveApplication.getDocStatus()!=PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                                                          Vector divHeadKey = new Vector(1,1);
                                                          Vector divHeadValue = new Vector(1,1);                                                          

                                                          String selectedApproval = "";
                                                          
                                                          if(deptHead == true){
                                                              
                                                              if(objLeaveApplication.getDepHeadApproval() == 0){
                                                                  
                                                                    divHeadKey.add("Waiting Approval Div./Dep");
                                                                    divHeadValue.add("0");
                                                                  
                                                              }else{
                                                                  
                                                                    divHeadKey.add("Select HRD");
                                                                    divHeadValue.add("0");
                                                                
                                                                    selectedApproval = ""+objLeaveApplication.getHrManApproval();                                                                   
                                                          
                                                                    Vector listHRMan = leaveConfig.listHRManager(objLeaveApplication.getEmployeeId());
                                                                   
                                                                    if(listHRMan != null && listHRMan.size() > 0){
                                                                        
                                                                        for(int i=0; i<listHRMan.size(); i++)
                                                                        {
                                                                            Employee objEmp = (Employee)listHRMan.get(i);
                                                                            divHeadKey.add(objEmp.getFullName());
                                                                            divHeadValue.add(""+objEmp.getOID());                                                                           
                                                                        }
                                                                        
                                                                    }
                                                              }
                                                              
                                                              
                                                          }else{
                                                              
                                                              divHeadKey.add("Select HRD");
                                                              divHeadValue.add("0");
                                                                
                                                              selectedApproval = ""+objLeaveApplication.getHrManApproval();
                                                          
                                                              Vector listHRMan = leaveConfig.listHRManager(objLeaveApplication.getEmployeeId());                                                              
                                                              
                                                              if(listHRMan != null && listHRMan.size() > 0){  
                                                                for(int i=0; i<listHRMan.size(); i++)
                                                                {
                                                                    Employee objEmp = (Employee)listHRMan.get(i);                                 
                                                                    divHeadKey.add(objEmp.getFullName());
                                                                    divHeadValue.add(""+objEmp.getOID());
                                                                   
                                                                }
                                                              }
                                                              
                                                          }
                                                          String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(2)\"";
                                                          //out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                         //update by satrya 2014-01-18
                                                          //out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                         if(objLeaveApplication.getHrManApproval()!=0 && leaveConfig.CanNotChangeApproval()){ 
                                                              out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, "disabled"));														  
                                                         }else{
                                                              out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                         }
                                             ////jika statusnya sdh execute
                                                          //update by satrya 2012-08-01
                                                    }else{
                                                       Employee objEmpName = new Employee();
                                                            try{
                                                          objEmpName = PstEmployee.fetchExc(objLeaveApplication.getHrManApproval());
                                                         out.println("<B>"+objEmpName.getFullName()+"</B>");
                                                            }catch(Exception e){System.out.println(e);}
                                              }											  		  
                                              }											  		  
                                          %>
                                          </b></td>
                                          <td width="23%" ><b>
                                               
                                               <% //update by satrya 2012-07-23   
                                                  if(objLeaveApplication.getDocStatus() !=PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){ 
                                                      out.println(ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVE_DATE],(objLeaveApplication.getHrManApproveDate()==null ? new Date() : objLeaveApplication.getHrManApproveDate()), "getHRApprovalDate()"));
                                                  }else{
                                              //update by satrya 2012-09-28
                                                out.println("<B>"+Formater.formatDate(objLeaveApplication.getHrManApproveDate() !=null ? objLeaveApplication.getHrManApproveDate() : objLeaveApplication.getSubmissionDate(), "MMMM dd, yyyy")+"</B>");}
                                               
                                              
                                             
                                              %>
                                          </b></td>
                                      </tr>
                                      <%}%>
                                      <%
                                      if(objLeaveApplication.getOID()!=0 && maxApproval == I_Leave.LEAVE_APPROVE_3){
                                      %>
                                      <tr>
                                          <td width="15%" >Final Approved by</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                          <%    
                                              if(objLeaveApplication.getOID()!=0)
                                              {
                                                        
                                                          Vector divHeadKey = new Vector(1,1);
                                                          Vector divHeadValue = new Vector(1,1);
                                                          
                                                          String selectedApproval = ""+objLeaveApplication.getGmApproval();
                                                          
                                                          if(HRDMan == true){
                                                              
                                                              if(objLeaveApplication.getHrManApproval() == 0){
                                                                  
                                                                   divHeadKey.add("Waiting Approval HRD");
                                                                   divHeadValue.add("0");
                                                                  
                                                              }else{
                                                                  
                                                                divHeadKey.add("Select Approver");
                                                                divHeadValue.add("0");
                                                          
                                                                Vector vectPositionLvl1 = new Vector(1,1);                                                         
                                                                vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           
                                                                Vector listDivHead = new Vector();                                                                
                                                                
                                                                try{
                                                                   long oidGM = Long.parseLong(PstSystemProperty.getValueByName("GM_POS_ID"));  
                                                                    if(oidGM!=0){
                                                                       Employee  emGM = PstEmployee.getEmployeeByPositionId(oidGM);
                                                                       if(emGM!=null && emGM.getFullName().length()>1){
                                                                           listDivHead.add(emGM);
                                                                       }
                                                                    }
                                                                }catch(Exception E){
                                                                    divHeadKey.add("Please set OID for General Manager in System Ppoperty : GM");
                                                                    divHeadValue.add("0");                                                                        
                                                                }

                                                                try{
                                                                   long oidHRDir = Long.parseLong(PstSystemProperty.getValueByName("HR_DIR_POS_ID"));  
                                                                    if(oidHRDir!=0){
                                                                       Employee  emHRDir = PstEmployee.getEmployeeByPositionId(oidHRDir);
                                                                       if(emHRDir!=null && emHRDir.getFullName().length()>1){
                                                                           listDivHead.add(emHRDir);
                                                                       }
                                                                    }
                                                                }catch(Exception E){
                                                                    System.out.println(" HR Director not set in System Property : HR_DIRECTOR");
                                                                }
                                                                
                                                                if(listDivHead==null || listDivHead.size()==0){
                                                                    listDivHead = SessEmployee.listEmployeeByPositionLevelGeneralM(objEmployee,vectPositionLvl1);
                                                                }
                                                          
                                                                for(int i=0; i<listDivHead.size(); i++)
                                                                {
                                                                    Employee objEmp = (Employee)listDivHead.get(i);
                                                                    divHeadKey.add(objEmp.getFullName());
                                                                    divHeadValue.add(""+objEmp.getOID());
                                                                }
                                                                  
                                                              }
                                                              
                                                          }else{

                                                            divHeadKey.add("Select General Manager");
                                                            divHeadValue.add("0");
                                                          
                                                            Vector vectPositionLvl1 = new Vector(1,1);
                                                         
                                                            vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           

                                                            Vector listDivHead = SessEmployee.listEmployeeByPositionLevelGeneralM(objEmployee,vectPositionLvl1);
                                                          
                                                            for(int i=0; i<listDivHead.size(); i++)
                                                            {
                                                                  Employee objEmp = (Employee)listDivHead.get(i);
                                                                  divHeadKey.add(objEmp.getFullName());
                                                                  divHeadValue.add(""+objEmp.getOID());
                                                                  
                                                            }
                                                            
                                                          }
                                                          String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(3)\"";
                                                          out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_GM_APPROVE], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                  }												  		  
                                                  %>
                                          </b></td>
                                          <td width="23%" ><b>
                                                  
                                               <%
                                                 
                                              out.println(ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_GM_APPROVE_DATE],(objLeaveApplication.getGmApprovalDate()==null ? new Date() : objLeaveApplication.getGmApprovalDate()), "getGMApprovalDate()"));
                                               
                                                %>
                                          </b></td>
                                      </tr>  
                                     <%} %>
                                    </table>
                                  </td>
                                </tr>
                              </table>
                              <%
                             }
                             }%>
                            </td>
                          </tr>
                        <tr> 
                            <td>&nbsp; </td>
                        </tr>                        
                        <tr> 
                            <td> 
                            <%
                                ctrLine.setLocationImg(approot+"/images");
                                ctrLine.initDefault();
                                ctrLine.setTableWidth("80");
                                ctrLine.setCommandStyle("buttonlink");												

                                String scomDel = "javascript:cmdAsk('"+oidLeave+"')";
                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidLeave+"')";
                                String scancel = "javascript:cmdEdit('"+oidLeave+"')";

                                ctrLine.setAddCaption("");
                             if(type_form==PstLeaveApplication.EXCUSE_APPLICATION  || repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO || objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION){											
                                ctrLine.setBackCaption("Back to List Excuse Application"); 
                                
                                ctrLine.setConfirmDelCaption("Yes, Delete Excuse Application");
                                ctrLine.setDeleteCaption("Delete Excuse Application");
                                ctrLine.setSaveCaption("Save  Excuse Application"); 
                             }else{
                                ctrLine.setBackCaption("Back to List Leave Application");
                                ctrLine.setConfirmDelCaption("Yes, Delete Leave Application");
                                ctrLine.setDeleteCaption("Delete Leave Application");
                                ctrLine.setSaveCaption("Save  Leave Application"); 
                             }
                                if ((privDelete) && true)
                                {                                        
                                        
                                            ctrLine.setConfirmDelCommand(sconDelCom);
                                            ctrLine.setDeleteCommand(scomDel);
                                            ctrLine.setEditCommand(scancel); 
                                            if(repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()==PstEmpCategory.ENTITLE_NO || objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()==PstLeaveApplication.EXCUSE_APPLICATION){ 
                                            ctrLine.setSaveCaption("Save Excuse Application"); 
                                            }else{
                                                 ctrLine.setSaveCaption("Save Leave Application"); 
                                            }

                                }
                                else
                                {					
                                            ctrLine.setConfirmDelCaption("");
                                            ctrLine.setDeleteCaption("");
                                            ctrLine.setEditCaption("");                                        
                                }

                                if (!privAdd)
                                {
                                        
                                        ctrLine.setAddCaption("");
                                            
                                }

                                if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED)
                                {                                       
                                            ctrLine.setConfirmDelCaption("");
                                            ctrLine.setDeleteCaption("");
                                            ctrLine.setEditCaption("");
                                            ctrLine.setSaveCaption("");													
                                            ctrLine.setAddCaption("");
                                            ctrLine.setBackCaption("");

                                }     
                                
                                if(iCommand == Command.GOTO)
                                    iCommand = Command.EDIT;
                               //update by satrya 2014-01-20
                                if(errMsgAL!=null && errMsgAL.length()>0){ 
                                     ctrLine.setConfrmDelInfo(errMsgAL);
                                     //update by satrya 2014-07-16 biar kelihatan success save ato tidak
                                     errMsgLeaveApp = errMsgAL;
                                }else if(errMsgDp!=null && errMsgDp.length()>0){
                                     ctrLine.setConfrmDelInfo(errMsgDp);
                                     //update by satrya 2014-07-16 biar kelihatan success save ato tidak
                                     errMsgLeaveApp = errMsgDp;
                                }else if(errMsgLL!=null && errMsgLL.length()>0){
                                     ctrLine.setConfrmDelInfo(errMsgLL);
                                     //update by satrya 2014-07-16 biar kelihatan success save ato tidak
                                     errMsgLeaveApp = errMsgLL;
                                }else if(errMsgSpecialUnpaid!=null && errMsgSpecialUnpaid.length()>0){
                                     ctrLine.setConfrmDelInfo(errMsgSpecialUnpaid);
                                     //update by satrya 2014-07-16 biar kelihatan success save ato tidak
                                     errMsgLeaveApp = errMsgSpecialUnpaid;
                                }   
                                out.println(ctrLine.drawImage(iCommand, iErrCode, errMsgLeaveApp));
                            %>
                            </td>
                            
                            
                        </tr>
                        <%
                        if(objLeaveApplication.getOID()!=0){
                        %>
                        <tr> 
                                <td>
                                <table>
                                    
                                    <tr>
                                  <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print data"></a></td>                                  
                                  <td height="22" valign="middle" colspan="3" width="51"> 
                                    <a href="javascript:cmdPrint()" class="command">Print</a> </td>
                                  <!-- update by satrya 2012-07-27 -->
                                  
                                  <% //update by satrya 2012-07-27
                                  if(isHRDLogin && (objLeaveApplication.getDocStatus()!=PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED)  
                                          && (objLeaveApplication.getDocStatus()!=PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT) 
                                          && (objLeaveApplication.getDocStatus()!=PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED) 
                                    && (objLeaveApplication.getDocStatus()== PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED) ){%>
                                  <td width="24"><a href="javascript:cmdExecute()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNew.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Execute"></a></td>                                  
                                     <td height="22" valign="middle" colspan="3" width="51"> 
                                    <a href="javascript:cmdExecute()" class="command">Execute</a> </td>
                                     <%  }%>
                                     <!-- update by satrya 2012-11-1 -->
                                     <%if(isHRDLogin && (objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()!=PstLeaveApplication.EXCUSE_APPLICATION)){%>
                                     <!--<td width="24"><a href="javascript:cmdBackListExecute()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnEdit.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnEdit.jpg" width="24" height="24" alt="Back"></a></td>                                  
                                  <td height="22" valign="middle" colspan="3" width="100" nowrap="nowrap"> 
                                    <a href="javascript:cmdBackListExecute()" class="command">Back to  Leave Excecution List</a> </td>-->
                                  <%}%>
                                  <!-- end -->
                                    <%if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED && (objLeaveApplication!=null && objLeaveApplication.getTypeFormLeave()!=PstLeaveApplication.EXCUSE_APPLICATION || repItemLeaveAndDp!=null && repItemLeaveAndDp.getStatusKaryawan()!=PstEmpCategory.ENTITLE_NO)){ %>
                                    <!-- update by satrya 2012-09-04-->
                                     <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnEdit.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnEdit.jpg" width="24" height="24" alt="Back"></a></td>                                  
                                     <td height="22" valign="middle" colspan="3" width="13%"  nowrap="nowrap"><a href="javascript:cmdBack()" class="command">Back to Leave Application List</a></td>
                                    
                                    <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnEdit.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnEdit.jpg" width="24" height="24" alt="Exit"></a></td>                                  
                                     <td height="22" valign="middle" colspan="3" width="900"><a href="javascript:cmdExit()" class="command">Exit Window</a> 
                                    <%}%>
                                    </tr>
                                </table>
                                </td>
                                 
                        </tr>
                        <%
                        }
                        %>
                        </table>                        
                        </form>
                        <!-- #EndEditable -->
                        
                        </td>
                    </tr>
                    </table>
                    </td>
                </tr>
                </table>
                </td>
            </tr>
            <tr> 
                <td>&nbsp;</td>
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
                <td colspan="2" height="20" <%=bgFooterLama%> > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
<SCRIPT>
function doBlink() {
  // Blink, Blink, Blink...
  var blink = document.all.tags("blink")
  for (var i=0; i < blink.length; i++)
    blink[i].style.visibility = blink[i].style.visibility == "" ? "hidden" : "" 
}

function startBlink() {
  // Make sure it is IE4
  if (document.all)
    setInterval("doBlink()",1000)
}
window.onload = startBlink;
</SCRIPT>
</body>

<!-- #BeginEditable "script" -->
<script language="JavaScript">

</script>
<!-- #EndEditable -->

<!-- #EndTemplate -->

</html>

