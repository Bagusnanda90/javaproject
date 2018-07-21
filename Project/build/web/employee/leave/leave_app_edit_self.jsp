
<%@page import="javax.mail.FetchProfile.Item"%>
<%@page import="javax.el.ELException"%>
<%-- 
    Document   : leave_app_edit_self
    Created on : May 10, 2010, 8:55:41 AM
    Author     : root
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
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@page  import = "com.dimata.harisma.entity.search.SrcLeaveManagement" %>
<%@ include file = "../../main/javainit.jsp" %>

<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
 static final int LEAVE_TYPE_APPLICATION=0;
 static final int LEAVE_TYPE_AL=1;
 static final int LEAVE_TYPE_LL=2;
 static final int LEAVE_TYPE_DP=3;
 static final int LEAVE_TYPE_SPECIAL_UNPAID=4;
 
 static final int STATUS_LEAVE_SPECIAL_LEAVE = 1;       
 static final int STATUS_LEAVE_UNPAID_LEAVE = 2;       
 
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

    public Vector drawListAnnualLeave(Vector vListAl, Vector vectorAlStockTaken,int indexAlGet, long oidEmployee, long oidLeave, int docStatus){
            
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
  
            String color = "2275E6";
            
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();            
            float i = 0;
            float eligbleDay = 0;
            float alQty =0;
            float totalAlTaken = 0;
            float curentAlQty = 0;
            float totalAl2BeTaken = 0;

            for (int j=0; j<vListAl.size(); j++)
            {
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)vListAl.get(j);
                eligbleDay = item.getALTotal() - item.getALTaken() - item.getAL2BTaken();
                alQty = item.getALTotal();
                totalAlTaken = item.getALTaken();
                curentAlQty = item.getALTotal()-item.getALTaken();
                totalAl2BeTaken = item.getAL2BTaken();
              }
            
            boolean status_stock_not_exist = false;
            
            status_stock_not_exist = SessLeaveApplication.statusAlStockNotExist(oidEmployee);
            
            long alStockId = SessLeaveApplication.OIDALStockManagement(oidEmployee);
            
            boolean stsAlAktf = true;
            
            I_Leave leaveConfig = null;

            try{
                leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            }catch (Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            
            if(alStockId!= 0){            
                
                if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_COMMENCING){
                    stsAlAktf = SessLeaveApplication.getLastDayAlPeriod(alStockId,new Date());
                }else if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_PERIOD){
                    stsAlAktf = SessLeaveApplication.getStatusAlAktif(alStockId,new Date(),leaveConfig.getMonthClosingAnnual(),leaveConfig.getDateClosingAnnual());
                }else{
                    stsAlAktf = SessLeaveApplication.getStatusAlAktif(alStockId,new Date(),leaveConfig.getMonthPeriod(),leaveConfig.getDatePeriod());
                }
                
            }
            
                if(vectorAlStockTaken.size() > 0){                    
                    
                    String dateStart ="";
                    String dateFinnish ="";
                    int indexAl;
                    boolean statusEditAl = false;
                    
                    for(indexAl = 1 ; indexAl <= vectorAlStockTaken.size(); indexAl ++){
                        
                        AlStockTaken objAlStockTaken = (AlStockTaken)vectorAlStockTaken.get(indexAl-1);                    
                        
                        dateStart = objAlStockTaken.getTakenDate()==null ? "": Formater.formatDate(objAlStockTaken.getTakenDate(), "yyyy-MM-dd");    
                        dateFinnish = objAlStockTaken.getTakenFinnishDate()==null ? "": Formater.formatDate(objAlStockTaken.getTakenFinnishDate(), "yyyy-MM-dd");
                        
                        String ControlDatePopupTakenDate = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE],( objAlStockTaken.getTakenDate() == null ? new Date() : objAlStockTaken.getTakenDate()),"getALStartDate()");
                        String ControlDatePopupFinnishDate = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE],( objAlStockTaken.getTakenFinnishDate() == null ? new Date() : objAlStockTaken.getTakenFinnishDate()),"getALStartDate()");
                        
                        Vector rowx = new Vector();
                        
                        if(indexAl == 1){
                            i = i + 1;
                            rowx.add(""+i);
                            if(stsAlAktf == false){
                                rowx.add("<font color=ff0000>Annual Leave</font>");
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
                            
                            rowx.add(""+objAlStockTaken.getTakenQty());
                            
                            if(indexAlGet == indexAl){
                                statusEditAl = true;
                                rowx.add(""+ControlDatePopupTakenDate);
                                rowx.add(""+ControlDatePopupFinnishDate);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            rowx.add("<a href=\"javascript:cmdSaveAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelAL('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");
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
                                            rowx.add("<a href=\"javascript:cmdEditAL('" + oidEmployee + "','"+oidLeave+"','"+indexAl+"')\">Edit</a> | <a href=\"javascript:cmdDeleteAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Delete</a>");
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
                            
                            rowx.add(""+objAlStockTaken.getTakenQty());
                            if(indexAlGet == indexAl){
                                statusEditAl = true;
                                rowx.add(""+ControlDatePopupTakenDate);
                                rowx.add(""+ControlDatePopupFinnishDate);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                }else{
                                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                        rowx.add("");
                                    }else{
                                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            rowx.add("<a href=\"javascript:cmdSaveAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Save</a> | <a href=\"javascript:cmdCancelAL('" + oidEmployee + "','"+oidLeave+"')\">Cancel</a>");
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
                                            rowx.add("<a href=\"javascript:cmdEditAL('" + oidEmployee + "','"+oidLeave+"','"+indexAl+"')\">Edit</a> | <a href=\"javascript:cmdDeleteAL('"+indexAl+"','"+objAlStockTaken.getOID()+"')\">Delete</a>");
                                        }else{
                                            rowx.add("");
                                        }
                                    }   
                                }    
                            }
                        }                        
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                    }  
                    
                    Vector rowx = new Vector();
                    if(statusEditAl == false){
                        indexAl++;
                        i = i + 1;            
                        String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE],(new Date()), "getALStartDate()");
                        String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE],(new Date()),"getALEndDate()");
                        rowx.add(""+i);
                        rowx.add("");
                        rowx.add(""+alQty);
                        rowx.add(""+totalAlTaken);
                        rowx.add(""+curentAlQty);
                        rowx.add(""+totalAl2BeTaken);
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                        }else{
                                if(stsAlAktf == false){
                                    rowx.add("<font color=ff0000>"+eligbleDay+"</font>");
                                }else{
                                    rowx.add(""+eligbleDay);
                                }
                        }
                        rowx.add(""+0);
                        rowx.add(""+ControlDatePopupTaken);
                        rowx.add(""+ControlDatePopupFinnish);
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
                                        rowx.add("<a href=\"javascript:cmdSaveAL('0','0')\">Save</a>");
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
               
                    String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_TAKEN_DATE],(new Date()), "getALStartDate()");
                    String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_FINNISH_DATE],(new Date()),"getALEndDate()");
                    
                    i = i+1;                    
                    Vector rowx = new Vector();
                    rowx.add(""+i);                    
                    rowx.add("Annual Leave");                   
                    rowx.add(""+alQty);                   
                    rowx.add(""+totalAlTaken);                    
                    rowx.add(""+curentAlQty);                                       
                    rowx.add(""+totalAl2BeTaken);
                    
                    if(status_stock_not_exist == true && oidEmployee !=0){
                        rowx.add("Stock Emprty");
                        }
                    else{
                        if(stsAlAktf == false){
                              rowx.add("<font color=FF0000>"+eligbleDay+"</font>");
                          }else{
                              rowx.add(""+eligbleDay);
                          }
                        }
                    /**
                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                          rowx.add("Stock Empty");
                    }else{
                          if(stsAlAktf == false){  
                              rowx.add("<font color=FF0000>"+eligbleDay+"</font>");  
                          }else{
                              rowx.add(""+eligbleDay);
                          }    
                    }
                    **/
                                     
                    rowx.add(""+0);
                    rowx.add(""+ControlDatePopupTaken);
                    rowx.add(""+ControlDatePopupFinnish);
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
                                   rowx.add("<a href=\"javascript:cmdSaveAL('0','0')\">Save</a>");                                    
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
    
    
        public Vector drawListLongLeave(Vector vListAl, Vector vectorLlStockTaken,long oidEmployee, long oidLeave, int indexLlget, int docStatus){
            
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
  
            String color = "2275E6";
            
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();
            
            int i = 0;
            boolean statusEditLl = false;                                     
            int indexLl = 0;
            float eligbleDay = 0;            
            float llQty = 0;
            float totalLlTaken = 0;
            float currentLLQty = 0;
            float toBeTakenLL = 0;

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
                    
            //eligbleDay = SessLeaveApplication.getLlEligbleDay(oidEmployee);
            
            boolean dtaLLExpired = false;   //FALSE - > NOT EXPIRED ; TRUE - > EXPIRED
            
            dtaLLExpired = SessLeaveApplication.getStatusLLExpired(oidEmployee);           
            
            if(vectorLlStockTaken.size() > 0){
                                                           
                    float request = 0;
                    float eligible = 0;
                    String tanggal = "";
                    String dateStart ="";
                    String dateFinnish ="";

                    for(indexLl = 1 ; indexLl <= vectorLlStockTaken.size(); indexLl ++){                       
                        
                        LlStockTaken objLlStockTaken = (LlStockTaken)vectorLlStockTaken.get(indexLl-1);
                        
                        dateStart = objLlStockTaken.getTakenDate()==null ? "" : Formater.formatDate(objLlStockTaken.getTakenDate(), "yyyy-MM-dd");    
                        dateFinnish = objLlStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(objLlStockTaken.getTakenFinnishDate(), "yyyy-MM-dd");
                                            
                        String ControlDatePopupTakenDateLL = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_TAKEN_DATE],(objLlStockTaken.getTakenDate() == null ? new Date() : objLlStockTaken.getTakenDate()), "getLLStartDate()");
                        String ControlDatePopupFinnishDateLL = ControlDatePopup.writeDate(FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_FINNISH_DATE],( objLlStockTaken.getTakenFinnishDate() == null ? new Date() : objLlStockTaken.getTakenFinnishDate()),"getLLEndDate()");
                        
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

                            rowx.add(""+objLlStockTaken.getTakenQty());
                            if(indexLl == indexLlget){
                                statusEditLl = true;
                                rowx.add(""+ControlDatePopupTakenDateLL);
                                rowx.add(""+ControlDatePopupFinnishDateLL);
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
                            rowx.add(""+objLlStockTaken.getTakenQty());
                            if(indexLl == indexLlget){
                                statusEditLl = true;
                                rowx.add(""+ControlDatePopupTakenDateLL);
                                rowx.add(""+ControlDatePopupFinnishDateLL);
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

                        rowx.add(""+llQty);
                        rowx.add(""+totalLlTaken);
                        rowx.add(""+currentLLQty);
                        rowx.add(""+toBeTakenLL);
                        
                        if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                        }else{
                                if(dtaLLExpired == true){
                                    rowx.add("<font color=ff0000>"+eligbleDay+"</font>");
                                }else{
                                    rowx.add(""+eligbleDay);
                                }
                                
                        }
                        rowx.add(""+0);
                        rowx.add(""+ControlDatePopupTaken);
                        rowx.add(""+ControlDatePopupFinnish);
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
                    
                    rowx.add(""+llQty);
                    rowx.add(""+totalLlTaken);
                    rowx.add(""+currentLLQty);
                    rowx.add(""+toBeTakenLL);
                    
                    if(status_stock_not_exist == true && oidEmployee != 0 ){
                                rowx.add("Stock Empty");
                    }else{
                        if(dtaLLExpired == true){
                            rowx.add("<font color=ff0000>"+eligbleDay+"</font>");
                        }else{
                            rowx.add(""+eligbleDay);
                        }   
                    }
                    rowx.add(""+0);
                    rowx.add(""+ControlDatePopupTaken);
                    rowx.add(""+ControlDatePopupFinnish);
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
        
        
        public Vector drawListSpecialUnpaidLeave(Vector vectorSpecialStockTaken,long oidEmployee, long oidLeave, int indexSpecialget, int docStatus){
            
            Vector result = new Vector();
            Vector vctDetails = new Vector();            
        
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
  
            String color = "2275E6";
            
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();
            
            int i = 0;
            boolean statusEditSpecial = false;                                     
            int indexSpecial = 0;
            
            Vector schedule_value = new Vector(1,1);
	    Vector schedule_key = new Vector(1,1);
            
            String oidSpecialLeave = String.valueOf(PstSystemProperty.getValueByName("OID_SPECIAL"));  
            String oidUnpaidLeave = String.valueOf(PstSystemProperty.getValueByName("OID_UNPAID"));  
            
            String whereSchedule = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidSpecialLeave+
                            " OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" = "+oidUnpaidLeave;
                                                                                        
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
                    
                    for(indexSpecial = 1 ; indexSpecial <= vectorSpecialStockTaken.size(); indexSpecial ++){
                        
                        SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken)vectorSpecialStockTaken.get(indexSpecial-1);                    
                        
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
                           
                        dateStart = objSpecialUnpaidLeaveTaken.getTakenDate()==null ? "" : Formater.formatDate(objSpecialUnpaidLeaveTaken.getTakenDate(), "yyyy-MM-dd");    
                        dateFinnish = objSpecialUnpaidLeaveTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(objSpecialUnpaidLeaveTaken.getTakenFinnishDate(), "yyyy-MM-dd");
                                            
                        String ControlDatePopupTakenDateSpecial = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],(objSpecialUnpaidLeaveTaken.getTakenDate() == null ? new Date() : objSpecialUnpaidLeaveTaken.getTakenDate()), "getSpecialStartDate()");
                        String ControlDatePopupFinnishDateSpecial = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],( objSpecialUnpaidLeaveTaken.getTakenFinnishDate() == null ? new Date() : objSpecialUnpaidLeaveTaken.getTakenFinnishDate()),"getSpecialEndDate()");
                        
                        Vector rowx = new Vector();
                        
                        if(indexSpecial == 1){
                            i++;                            
                            rowx.add(""+i);
                            rowx.add(stsLeave);                            
                            if(indexSpecial == indexSpecialget){
                                statusEditSpecial = true;
                                rowx.add(""+controlCombo);
                                rowx.add(""+objSpecialUnpaidLeaveTaken.getTakenQty());
                                rowx.add(""+ControlDatePopupTakenDateSpecial);
                                rowx.add(""+ControlDatePopupFinnishDateSpecial);
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
                                rowx.add(""+objSpecialUnpaidLeaveTaken.getTakenQty());
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
                                rowx.add(""+objSpecialUnpaidLeaveTaken.getTakenQty());
                                rowx.add(""+ControlDatePopupTakenDateSpecial);
                                rowx.add(""+ControlDatePopupFinnishDateSpecial);
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
                                rowx.add(""+objSpecialUnpaidLeaveTaken.getTakenQty());
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
                    if(statusEditSpecial == false){
                        i = i + 1;                    
                        String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],new Date(), "getSpecialStartDate()");
                        String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],new Date(),"getSpecialEndDate()");
                        String controlCombo = ControlCombo.draw(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SCHEDULED_ID],"elementForm", null, null, schedule_value, schedule_key," onkeydown=\"javascript:fnTrapKD()\"");                
                        rowx.add(""+i);
                        rowx.add("");
                        rowx.add(""+controlCombo);
                        rowx.add(""+0);
                        rowx.add(""+ControlDatePopupTaken);
                        rowx.add(""+ControlDatePopupFinnish);
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
                    String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_DATE],(new Date()), "getSpecialStartDate()");
                    String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_TAKEN_FINNISH_DATE],(new Date()), "getSpecialStartDate()");
                    String controlCombo = ControlCombo.draw(FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SCHEDULED_ID],"elementForm", null, null, schedule_value, schedule_key," onkeydown=\"javascript:fnTrapKD()\"");                
                    rowx.add(""+i);
                    rowx.add("");
                    rowx.add(""+controlCombo);
                    rowx.add(""+0);
                    rowx.add(""+ControlDatePopupTaken);
                    rowx.add(""+ControlDatePopupFinnish);
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
        
    
        public Vector drawListDPLeave(Vector vListAl, Vector vectorDpStockTaken, long oidEmployee, long oidLeave,int indexDpGet,int docStatus){
            
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
            ctrlist.addHeader("Taken Date", "10%");
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
            float expiredQTY = SessLeaveManagement.getDpExpired(oidEmployee,null);

            for (int j=0; j<vListAl.size(); j++)
            {
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)vListAl.get(j);
                eligbleDay = item.getDPQty() - item.getDPTaken() - item.getDP2BTaken()-expiredQTY;
                dpQty = item.getDPQty();
                totalDpTaken = item.getDPTaken();
                curentDpQty = item.getDPQty() - item.getDPTaken();
                totalDp2BeTaken = item.getDP2BTaken();
              }
                    
            //eligbleDay = SessLeaveApplication.getDpEligbleDay(oidEmployee);
              
                if(vectorDpStockTaken.size() > 0){
                    int eligible = 0;                    
                    String dateStart ="";
                    String datePaid ="";
                    int indexDp;
                    boolean statusEditDp = false;
                    
                    for(indexDp = 1 ; indexDp <= vectorDpStockTaken.size(); indexDp ++){
                        
                        DpStockTaken objDpStockTaken = (DpStockTaken)vectorDpStockTaken.get(indexDp-1);                    
                        
                        dateStart = objDpStockTaken.getTakenDate()==null ? "": Formater.formatDate(objDpStockTaken.getTakenDate(), "yyyy-MM-dd");    
                        datePaid = objDpStockTaken.getPaidDate()==null ? "": Formater.formatDate(objDpStockTaken.getPaidDate(), "yyyy-MM-dd");
                        
                        String ControlDatePopupTakenDate = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],( objDpStockTaken.getTakenDate() == null ? new Date() : objDpStockTaken.getTakenDate()),"getDPStartDate()");
                        String ControlDatePopupFinnishDate = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],( objDpStockTaken.getTakenFinnishDate() == null ? new Date() : objDpStockTaken.getTakenFinnishDate()),"getDPStartDate()");
                        String cmdViewDP ="<input type=\"text\" readonly name=\"DpPaidDate\">"+ 
                                "<input type=\"hidden\" value=\"\" name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"\">"+ 
                                "<a href=\"javascript:cmdViewDPList()\" class=\"buttonlink\"><img src=\"../../images/icon/folderopen.gif\" border=\"0\" alt=\"Select Available DP \"></a>";

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
                            rowx.add(""+objDpStockTaken.getTakenQty());
                            if(indexDpGet == indexDp){
                                statusEditDp = true;
                                rowx.add(""+ControlDatePopupTakenDate);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                    rowx.add("");
                                }else{
                                    rowx.add(""+cmdViewDP);
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
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add(""+objDpStockTaken.getTakenQty());
                            if(indexDpGet == indexDp){
                                statusEditDp = true;
                                rowx.add(""+ControlDatePopupTakenDate);
                                if(oidEmployee == 0){
                                    rowx.add("");
                                    rowx.add("");
                                }else{
                                   rowx.add(""+cmdViewDP);
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
                    }  
                    
                    Vector rowx = new Vector();
                    if(statusEditDp == false){
                        indexDp++;
                        i = i + 1;            
                        String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],(new Date()), "getDPStartDate()");                        
                        rowx.add(""+i);
                        rowx.add("");
                        rowx.add(""+dpQty);
                        rowx.add(""+totalDpTaken);
                        rowx.add(""+curentDpQty);
                        rowx.add(""+totalDp2BeTaken);
                        rowx.add(""+eligbleDay);
                        rowx.add(""+0);
                        rowx.add(""+ControlDatePopupTaken);
                        String cmdViewDP ="<input type=\"text\" readonly name=\"DpPaidDate\">"+ 
                                "<input type=\"hidden\" value=\"\" name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"\">"+ 
                                "<a href=\"javascript:cmdViewDPList()\" class=\"buttonlink\"><img src=\"../../images/icon/folderopen.gif\" border=\"0\" alt=\"Select Available DP \"></a>";
                        if(oidEmployee == 0){
                            rowx.add("");
                            rowx.add("");
                        }else{
                            rowx.add(""+cmdViewDP);
                            if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){                                 
                                rowx.add("<a href=\"javascript:cmdSaveDP('0','0')\">Save</a>");
                            }else{                                
                                rowx.add("");
                            }
                        }
                        lstData.add(rowx);                      
                        vctDetails.add(rowx);
                    }
                }else{
               
                    String ControlDatePopupTaken = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE],(new Date()), "getALStartDate()");
                    String ControlDatePopupFinnish = ControlDatePopup.writeDate(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE],(new Date()),"getALEndDate()");
                    i = i+1;                    
                    Vector rowx = new Vector();
                    rowx.add(""+i);                    
                    rowx.add("Day off payment");
                    rowx.add(""+dpQty);
                    rowx.add(""+totalDpTaken);
                    rowx.add(""+curentDpQty);
                    rowx.add(""+totalDp2BeTaken);
                    rowx.add(""+eligbleDay);                    
                    rowx.add(""+0);
                    rowx.add(""+ControlDatePopupTaken);
                    String cmdViewDP = "<input type=\"text\" readonly name=\"DpPaidDate\">"+
                            "<input type=\"hidden\" value=\"\" name=\""+FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]+"\">"+ 
                            "<a href=\"javascript:cmdViewDPList()\" class=\"buttonlink\"><img src=\"../../images/icon/folderopen.gif\" border=\"0\" alt=\"Select Available DP \"></a>";
                    if(oidEmployee == 0){
                        rowx.add("");
                        rowx.add("");
                    }else{
                        rowx.add(""+cmdViewDP);
                        if(docStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){                             
                            rowx.add("<a href=\"javascript:cmdSaveDP('0','0')\">Save</a>");
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
    
    int iCommand = FRMQueryString.requestCommand(request); 
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int leaveType = FRMQueryString.requestInt(request, "leaveType");
    long oidLeave = FRMQueryString.requestLong(request,FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]);                   
    
    long oid_employee = FRMQueryString.requestLong(request, "oid_employee");
    
    int indexAl = FRMQueryString.requestInt(request, "indexAl");
    int indexLl = FRMQueryString.requestInt(request, "indexLl");
    int indexSpecial = FRMQueryString.requestInt(request, "indexSpecial");
    int indexDP = FRMQueryString.requestInt(request, "indexDP");
    
    long TakenALOid = FRMQueryString.requestLong(request, FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]);
    long TakenLLOid = FRMQueryString.requestLong(request, FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]);
    long TakenSpecialOid = FRMQueryString.requestLong(request, FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]);
    long TakenDpOid = FRMQueryString.requestLong(request, FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]);
    
    	int iErrCode = FRMMessage.ERR_NONE;
	String errMsgLeaveApp = "";

        CtrlLeaveApplication ctrlLeaveApplication = new CtrlLeaveApplication(request);
        
        if(leaveType==LEAVE_TYPE_APPLICATION){
            iErrCode = ctrlLeaveApplication.action(iCommand, oidLeave, null,approot);
        } else{
            iErrCode = ctrlLeaveApplication.action(Command.EDIT, oidLeave, null,approot);
        }
	errMsgLeaveApp = ctrlLeaveApplication.getMessage();        
        
        LeaveApplication objLeaveApplication = ctrlLeaveApplication.getLeaveApplication();        
        if(objLeaveApplication ==null ){
            objLeaveApplication = new LeaveApplication();            
        }
        if(objLeaveApplication.getOID()==0){
               objLeaveApplication.setSubmissionDate(new Date());
            } else{
              if(leaveType==LEAVE_TYPE_APPLICATION){
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
                                    } catch (Exception e) {
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
                                         objLeave.setDepHeadApproveDate(objLeaveApplication.getDepHeadApproveDate());
                                         
                                         if(stsDokApprov1 == true){
                                             objLeave.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED); 
                                         }
                                         
                                         try{
                                             //PstLeaveApplication.updateExc(objLeave);
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
                                         objLeave2.setHrManApproveDate(objLeaveApplication.getHrManApproveDate());
                                         
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
                                    if (objLeaveApplication.getDocStatus() == 1) {

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
                                         objLeave3.setGmApprovalDate(objLeaveApplication.getGmApprovalDate());
                                         
                                         if(stsDokApprov3 == true){
                                             objLeave3.setDocStatus(PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED); 
                                         }
                                         
                                         try{
                                             //PstLeaveApplication.updateExc(objLeave3);
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
    
    AlStockManagement objAlStockManagement = new AlStockManagement();
    
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
    
    int iErrCodeAL = FRMMessage.ERR_NONE;
    int iErrCodeLL = FRMMessage.ERR_NONE;
    int iErrCodeSpecial = FRMMessage.ERR_NONE;
    int iErrCodeDp = FRMMessage.ERR_NONE;
    String errMsgAL = "";
    String errMsgLL = "";
    String errMsgSpecialUnpaid = "";
    String errMsgDp = "";
    
        if(leaveType == LEAVE_TYPE_AL){
            CtrlAlStockTaken ctrlAlStockTaken = new CtrlAlStockTaken(request);
            iErrCodeAL=ctrlAlStockTaken.action(iCommand, TakenALOid);
            if((iErrCodeAL == 0 && iCommand == Command.SAVE) || (iErrCodeAL == 0 && iCommand == Command.DELETE)){
                indexAl = -1;
            }
            FrmAlStockTaken frmAlStockTaken = ctrlAlStockTaken.getForm();
            errMsgAL = ctrlAlStockTaken.getMessage(); 
         
        }else if(leaveType == LEAVE_TYPE_LL){
            CtrlLlStockTaken ctrlLlStockTaken = new CtrlLlStockTaken(request);
            iErrCodeLL=ctrlLlStockTaken.action(iCommand, TakenLLOid);
            if((iErrCodeLL == 0 && iCommand == Command.SAVE) || (iErrCodeLL == 0 && iCommand == Command.DELETE)){
                indexLl = -1;
            }
            FrmLlStockTaken frmLlStockTaken = ctrlLlStockTaken.getForm();
            errMsgAL = ctrlLlStockTaken.getMessage(); 
        }else if(leaveType == LEAVE_TYPE_SPECIAL_UNPAID){
            CtrlSpecialUnpaidLeaveTaken ctrlSpecialUnpaidTaken = new CtrlSpecialUnpaidLeaveTaken(request);
            iErrCodeSpecial=ctrlSpecialUnpaidTaken.action(iCommand, TakenSpecialOid);
            if((iErrCodeLL == 0 && iCommand == Command.SAVE) || (iErrCodeSpecial == 0 && iCommand == Command.DELETE)){
                indexSpecial = -1;
            }
            FrmSpecialUnpaidLeaveTaken frmSpecialUnpaidLeaveTaken = ctrlSpecialUnpaidTaken.getForm();
            errMsgSpecialUnpaid = ctrlSpecialUnpaidTaken.getMessage(); 
        }else if(leaveType == LEAVE_TYPE_DP){
            CtrlDpStockTaken ctrlDpStockTaken = new CtrlDpStockTaken(request);
            iErrCodeDp=ctrlDpStockTaken.action(iCommand, TakenDpOid);            
            if((iErrCodeDp == 0 && iCommand == Command.SAVE) || (iErrCodeDp == 0 && iCommand == Command.DELETE)){
                indexDP = -1;
            }
            FrmDpStockTaken frmDpStockTaken = ctrlDpStockTaken.getForm();
            errMsgDp = ctrlDpStockTaken.getMessage();             
        }
 
    
    AlStockTaken objAlStockTaken = new AlStockTaken();
    LlStockTaken objLlStockTaken = new LlStockTaken();
    DpStockTaken objDpStockTaken = new DpStockTaken();
    SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
    
    
    Vector vectorAlStockTaken = new Vector();
    Vector vectorLlStockTaken = new Vector();
    Vector vectorDpStockTaken = new Vector();
    Vector vectorSpecialStockTaken = new Vector();
    Vector vectorUnpaidStockTaken = new Vector();
    Vector listal = new Vector(1,1);
    SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
    srcLeaveManagement.setEmpNum(objEmployee.getEmployeeNum());
    listal = SessLeaveApp.detailLeaveDPStock(srcLeaveManagement);
    
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
            System.out.println("Exception "+e.toString());
        }
    
        try{
            String whereClause = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+" = "+objLeaveApplication.getOID();        
            vectorDpStockTaken = PstDpStockTaken.list(0,0, whereClause, null);
        
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
    
   
        try{
            vectorSpecialStockTaken = SessLeaveApplication.ListSpecialUnpaidLeave(objLeaveApplication.getOID());                   
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
    
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
<title>HARISMA - Leave Application Request</title>
<script language="JavaScript">
</script>
<!-- #EndEditable -->

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 

<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->

<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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

function cmdViewDPList(){
    getDpStartDate();
    var employeeId=document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value;

    var strReDate = "<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_yr"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_yr")%>.value +
                   "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_mn"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_mn")%>.value +
                   "&"+"<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_dy"+"=")%>"+ 
                   document.frm_leave_application.<%=(FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]+"_dy")%>.value;
                   
    window.open("dp_available_list.jsp?employee_id="+employeeId+"&"+strReDate, null, "height=600,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
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
	document.frm_leave_application.command.value="<%=String.valueOf(Command.CANCEL)%>";
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit();
} 


function cmdEdit(oid)
{ 
        document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_APPLICATION %>;
        document.frm_leave_application.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit(); 
} 

function cmdSave()
{
        document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_APPLICATION %>;
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

	document.frm_leave_application.command.value="<%=String.valueOf(Command.SAVE)%>"; 
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit();

}

function cmdAsk(oid)
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.ASK)%>"; 
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit();
} 

function cmdConfirmDelete(oid)
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frm_leave_application.action="leave_app_edit_self.jsp"; 
	document.frm_leave_application.submit();
}  

function cmdBack()
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.NONE)%>"; 
	document.frm_leave_application.action="<%=approot%>/home.jsp";
	document.frm_leave_application.submit();
}


function cmdEditDates(oidEmployee, oidLeave, oidSymbol, oidCategory, eligibleDay){

        window.open("leave_request_edit.jsp?employee_id=" + oidEmployee + "&leave_id=" + oidLeave + "&symbol_id=" + oidSymbol + "&category_id=" + oidCategory + "&day=" + eligibleDay, 
                     null, "height=300,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");        
} 

function cmdCancelAL(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";        
        document.frm_leave_application.indexAl.value = 0; 
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit();        
} 

function cmdCancelLL(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";        
        document.frm_leave_application.indexLl.value = 0; 
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit();
        
}

function cmdCancelDP(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";        
        document.frm_leave_application.indexDP.value = 0; 
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit();
    
}

function cmdCancelSpecial(oidEmployee,oidLeave){
        document.frm_leave_application.command.value="<%=Command.EDIT%>";        
        document.frm_leave_application.indexSpecial.value = 0; 
	document.frm_leave_application.action="leave_app_edit_self.jsp";
	document.frm_leave_application.submit();
}

    
function cmdSaveAL(indexAl,TakenALOid){    
    getALStartDate();
    getALEndDate();
    document.frm_leave_application.command.value="<%=Command.SAVE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_AL %>;
    document.frm_leave_application.indexAl.value = indexAl;
    document.frm_leave_application.<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]%>.value = TakenALOid;
    document.frm_leave_application.action="leave_app_edit_self.jsp";
    document.frm_leave_application.submit();
}

function cmdSaveLL(indexLl,TakenLLOid){    
    getLLStartDate();
    getLLEndDate();
    document.frm_leave_application.command.value="<%=Command.SAVE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_LL %>;
    document.frm_leave_application.indexLl.value = indexLl;
    document.frm_leave_application.<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]%>.value = TakenLLOid;
    document.frm_leave_application.action="leave_app_edit_self.jsp";
    document.frm_leave_application.submit();
}

function cmdSaveSpecial(indexSpecial,TakenSpecialOid){    
    getSpecialStartDate();
    getSpecialEndDate();
    document.frm_leave_application.command.value="<%=Command.SAVE%>";           
    document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_SPECIAL_UNPAID %>;
    document.frm_leave_application.indexSpecial.value = indexSpecial;
    document.frm_leave_application.<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]%>.value = TakenSpecialOid;
    document.frm_leave_application.action="leave_app_edit_self.jsp";
    document.frm_leave_application.submit();
}

function cmdSaveDP(indexDP,TakenDPOid){        
    var dpOid = document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]%>.value;
       
    if(dpOid == ''){
        
        alert ('Chose DP date first');
    
    }else{
        getDpStartDate();
        document.frm_leave_application.command.value="<%=Command.SAVE%>";           
        document.frm_leave_application.leaveType.value=<%= LEAVE_TYPE_DP %>;
        document.frm_leave_application.indexDP.value = indexDP;
        document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>.value = TakenDPOid;
        document.frm_leave_application.action="leave_app_edit_self.jsp";
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
    document.frm_leave_application.action="leave_app_edit_self.jsp";
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
    document.frm_leave_application.action="leave_app_edit_self.jsp";
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
    document.frm_leave_application.action="leave_app_edit_self.jsp";
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
    document.frm_leave_application.action="leave_app_edit_self.jsp";
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
       document.frm_leave_application.action="leave_app_edit_self.jsp";
       document.frm_leave_application.submit();
} 

function cmdEditLL(oidEmployee,oidLeave,indexLl,TakenLlOid){        
       getLLStartDate();
       getLLEndDate();
       document.frm_leave_application.command.value="<%=Command.EDIT%>";
       document.frm_leave_application.<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
       document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
       document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_LL %>;
       document.frm_leave_application.indexLl.value = indexLl;
       document.frm_leave_application.<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]%>.value = TakenLLOid;
       document.frm_leave_application.action="leave_app_edit_self.jsp";
       document.frm_leave_application.submit();
}

function cmdEditDP(oidEmployee,oidLeave,indexDP,TakenDPOid){
       getDpStartDate();
       document.frm_leave_application.command.value="<%=Command.EDIT%>";
       document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
       document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
       document.frm_leave_application.leaveType.value =<%= LEAVE_TYPE_DP %>;
       document.frm_leave_application.indexDP.value = indexDP;
       document.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>.value = TakenDPOid;
       document.frm_leave_application.action="leave_app_edit_self.jsp";
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
       document.frm_leave_application.<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]%>.value = TakenSpecialOid;
       document.frm_leave_application.action="leave_app_edit_self.jsp";
       document.frm_leave_application.submit();        
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
			document.frm_leave_application.oidApprover.value=empApprovalSelected;
			document.frm_leave_application.action="leave_approval_self.jsp";
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
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveApplicationReportPdf?oidLeaveApplication=<%=objLeaveApplication.getOID()%>&approot=<%=approot%>";
    window.open(pathUrl);
}

function reloadPage() {
    if(document.frm_leave_application.command.value == '<%= "" + Command.BACK %>') {
        document.frm_leave_application.command.value = '<%= "" + Command.NONE %>'       
        cmdSave();
    }
}

</script>
<!-- #EndEditable -->

</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')" onfocus="javascript:reloadPage()">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                Employee &gt; Leave Application &gt; Leave Form &gt; Leave Application Request
                </strong></font> 
                <!-- #EndEditable --> 
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
                    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="tabbg">
                    <tr>                     
                        <td valign="top">
                            
                        <!-- #BeginEditable "content" -->
                        <form name="frm_leave_application" method="post" action="">
                            
                            <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">                                                        
                            <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>" value="<%=objLeaveApplication.getOID()%>">                             
                            <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>" value="<%=objEmployee.getOID() %>">     
                            <input type="hidden" name="<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID]%>" value="<%=String.valueOf(TakenALOid)%>">     
                            <input type="hidden" name="<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID]%>" value="<%=String.valueOf(TakenLLOid)%>"> 
                            <input type="hidden" name="<%=FrmSpecialUnpaidLeaveTaken.fieldNames[FrmSpecialUnpaidLeaveTaken.FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]%>" value="<%=String.valueOf(TakenSpecialOid)%>"> 
                            <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>" value="<%=String.valueOf(TakenDpOid)%>"> 
                            <input type="hidden" name="leaveType" value="<%=String.valueOf(leaveType)%>">     
                            <input type="hidden" name="indexAl" value="<%=String.valueOf(indexAl)%>">                            
                            <input type="hidden" name="indexLl" value="<%=String.valueOf(indexLl)%>">                            
                            <input type="hidden" name="indexDP" value="<%=String.valueOf(indexDP)%>">                            
                            <input type="hidden" name="indexSpecial" value="<%=String.valueOf(indexSpecial)%>">                            
                            <input type="hidden" name="indexApproval" value="1">
                            <input type="hidden" name="oidApprover" value="0">
                            <input type="hidden" name="<%=FrmAlStockTaken.fieldNames[FrmAlStockTaken.FRM_FIELD_AL_STOCK_ID]%>" value="<%=String.valueOf(AlStockManagementIdALStockAktif)%>">  
                            <input type="hidden" name="<%=FrmLlStockTaken.fieldNames[FrmLlStockTaken.FRM_FIELD_LL_STOCK_ID]%>" value="<%=String.valueOf(AlStockManagementIdLLStockAktif)%>">                                
                            
                            <div align="center">
                                <h2>LEAVE APPLICATION</h2>
                            </div>  
                            <table width="99%" align="center">
                            <tr>
                                <td>
                                <table width="100%" border=0>
                                <tr>
                                <td width="14%">Payroll</td>
                                <td width="3%">:</td>
                                <td width="35%"><input type="text" name="EMP_NUMBER"  value="<%=objEmployee.getEmployeeNum()%>" class="elemenForm" size="25" readonly> </td>
                                <td width="15%" >
                                    &nbsp;
                                </td> 
                                <td width="3%">&nbsp;</td>
                                <td>&nbsp;</td>
                                </tr>
                                <tr>
                                <td width="14%">Name</td>
                                <td width="3%">:</td>
                                <td width="35%">
                                    <strong><input type="text" name="EMP_FULLNAME"  value="<%=objEmployee.getFullName()%>" class="elemenForm" size="25"  readonly></strong>                                    
                                </td>
                                <td width="15%">&nbsp;</td>
                                <td width="30%">&nbsp;</td>
                            <tr>
                            <tr> <td><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                <td width=3%>:</td>
                                <td>
                                    <strong><input type="text" name="EMP_DEPARTMENT"  value="<%= objDepartment.getDepartment() %>" class="elemenForm" size="25"  readonly> </strong>
                                </td>
                                <td>Position</td>
                                <td><input type="text" name="EMP_POSITION"  value="<%=pos.getPosition()%>" class="elemenForm" size="23"  readonly> </td>
                                <td>
                                    
                                </td>
                                
                            <tr>
                            <tr>
                                <td><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                <td>:</td>
                                <td>
                                    <input type="text" name="EMP_DIVISION"  value="<%=div.getDivision()%>" class="elemenForm" size="25"  readonly>
                                </td>
                                 <td>Date of Request</td>
                                <td><%=ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SUBMISSION_DATE],(objLeaveApplication.getSubmissionDate() == null ? new Date() : objLeaveApplication.getSubmissionDate()), "getSubMsDate()")%></td>
                                <td>
                                    
                                </td>
                            <tr>
                            <tr>
                                <td>Commencing Date</td>
                                <td>:</td>
                                <td>
                                    <input type="text" name="EMP_COMENCING"  value="<%= objEmployee.getCommencingDate() == null ? "" : Formater.formatDate(objEmployee.getCommencingDate(), "MMMM dd, yyyy") %>" class="elemenForm" size="25"  readonly>
                                </td>                              
                                <td>&nbsp;</td
                                <td></td>
                                <td>
                                    <strong>
                                    &nbsp;
                                    </strong>
                                </td>
                             </tr>
                             
                             <tr>
                                <td>Document Status </td>
                                <td>:</td>
                                <td>
                                    <strong>
                                    <% 
                                        Vector vectListSts = new Vector();
                                        Vector vectValSts = new Vector();
                                        
                                        if(objEmployee.getOID()==0){
                                            
                                            %>
                                            <B>Draft</B>
                                            <%
                                            
                                        }else{
                                            
                                        if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT){
                                            
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT);
                                            vectListSts.add("Draft");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE);
                                            vectListSts.add("To be approve");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED);
                                            vectListSts.add("Canceled");
                                        
                                        }else if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE){
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT);
                                            vectListSts.add("Draft");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE);
                                            vectListSts.add("To be approve");
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED);
                                            vectListSts.add("Canceled");
                                            
                                        }else if(objLeaveApplication.getDocStatus()==PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED){
                                            
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT);
                                            vectListSts.add("Draft");                                            
                                            vectValSts.add(""+PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED);
                                            vectListSts.add("Aproved");
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
                                            <B>Excecuted</B>
                                            <%
                                        }
                                        }
                                    %>
                                    </strong>
                                </td>                              
                                <td>&nbsp;</td
                                <td>&nbsp;</td>
                                <td>
                                    <strong>
                                    &nbsp;
                                    </strong>
                                </td>
                             </tr>                             
                            <tr>
                                <td colspan="5">&nbsp;</td>                                
                            <tr>
                            </table>
                            
                            </td>
                        </tr>                            
                        <tr>
                            <td>
                            <% Vector res = drawListAnnualLeave(listal, vectorAlStockTaken,indexAl, objLeaveApplication.getEmployeeId() ,oidLeave,objLeaveApplication.getDocStatus());
                            
                               vctStrDetail = (Vector)res.get(1);
                            %>
                            <%= res.get(0) %>
                            </td>
                        </tr>
                        <% 
                        if(iErrCodeAL != CtrlAlStockTaken.RSLT_OK){
                        %>   
                        <tr>
                            <td>
                                <font color = "FF0000">
                             <% 
                                String errorAl = "";                                
                                
                                errorAl = CtrlAlStockTaken.resultText[CtrlLlStockTaken.LANGUAGE_FOREIGN][iErrCodeAL];
                                System.out.println("Message : "+errorAl);
                             %>   
                                Message : <%=errorAl%></font>                            
                          </td>
                        </tr>
                        <% 
                        }
                        %> 
                        <tr>
                            <td>&nbsp;&nbsp;           
                            </td>
                        </tr>                        
                        <tr>
                            <td>
                            <% Vector vectdrawListLongLeave = drawListLongLeave(listal, vectorLlStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexLl,objLeaveApplication.getDocStatus());
                            
                               vctStrDetail = (Vector)vectdrawListLongLeave.get(1);
                            %>
                            <%= vectdrawListLongLeave.get(0) %>
                            </td>
                        </tr>
                        <% 
                        if(iErrCodeLL != CtrlLlStockTaken.RSLT_OK){
                        %>   
                        <tr>
                            <td>
                                <font color = "FF0000">
                             <% 
                                String errorLl = "";
                                
                                errorLl = CtrlLlStockTaken.resultText[CtrlLlStockTaken.LANGUAGE_FOREIGN][iErrCodeLL];
                                System.out.println("Message : "+errorLl);
                             %>   
                                Message : <%=errorLl%></font>                            
                          </td>
                        </tr>
                        <% 
                        }
                        %>  
                        <tr>
                            <td>&nbsp;&nbsp;           
                            </td>
                        </tr>
                        <tr>
                            <td>
                            <% Vector vectdrawlistSpecialLeave = drawListSpecialUnpaidLeave(vectorSpecialStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexSpecial,objLeaveApplication.getDocStatus());
                            
                               vctStrDetail = (Vector)vectdrawlistSpecialLeave.get(1);
                            %>
                            <%= vectdrawlistSpecialLeave.get(0) %>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;           
                            </td>
                        </tr>
                        <tr>
                            <td>
                             <% Vector vectdrawlistDpLeave = drawListDPLeave(listal, vectorDpStockTaken, objLeaveApplication.getEmployeeId() ,oidLeave, indexDP,objLeaveApplication.getDocStatus());
                            
                               vctStrDetail = (Vector)vectdrawlistDpLeave.get(1);
                            %>
                            <%=vectdrawlistDpLeave.get(0) %>
                            <% 
                                
                            %>
                            </td>
                        </tr>
                        <% 
                        if(iErrCodeDp != CtrlDpStockTaken.RSLT_OK){
                        %>   
                        <tr>
                            <td>
                                <font color = "FF0000">
                             <% 
                                String errorDp = "";
                                
                                errorDp = CtrlDpStockTaken.resultText[CtrlDpStockTaken.LANGUAGE_FOREIGN][CtrlDpStockTaken.RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE];
                                System.out.println("Message : "+errorDp);
                             %>   
                                Message : <%=errorDp%></font>                            
                          </td>
                        </tr>
                        <% 
                        }
                        %>    
                        <tr>
                            <td>
                            <table width="100%">
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3"><b>Leave reason</b><td>
                            </tr>
                            <tr>
                                <td width="20%">Reason for leave</td>
                                <td width="5%">:</td>
                                <td width="75%">
                                    <input type="text" name="<%= FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_REASON] %>" value="<%= objLeaveApplication.getLeaveReason() %>" size="50">
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
                                 
                                %>To view approval form, please set document status to TO BE APPROVED<% 
                                
                             } else {
                                   
                                   boolean statusSchedule = SessLeaveApplication.CekScheduleExist(objLeaveApplication.getOID());
                                   
                                   if(statusSchedule==false){
                                       
                                       %><font color=FF0000>To view approval form, please set Schedule first</font><% 
                                       
                                   }else{
                                   
                            %>
                              <table width="100%" border="0" class="tablecolor" cellpadding="1" cellspacing="1">
                                <tr> 
                                  <td valign="top"> 
                                    <table width="100%" border="0" bgcolor="#F9FCFF">
                                      <tr>
                                                <td width="15%" align="left"><b>Approval</b></td >
                                                <td width="2%" ></td>
                                                <td width="60%" align="center"></td>
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
                                          <td width="15%" >Div./Department Head</td>
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                              <%												  
                                                  if(objLeaveApplication.getOID()!=0)
                                                  {
                                                          Vector divHeadKey = new Vector(1,1);
                                                          Vector divHeadValue = new Vector(1,1);

                                                          divHeadKey.add("Select Department Head");
                                                          divHeadValue.add("0");

                                                          String selectedApproval = ""+objLeaveApplication.getDepHeadApproval();
                                                          
                                                          Vector listDivHead = leaveConfig.getApprovalDepartmentHead(objLeaveApplication.getEmployeeId());
                                                          
                                                          if(listDivHead != null && listDivHead.size()>0){
                                                          
                                                                for(int i=0; i<listDivHead.size(); i++)
                                                                {
                                                                    Employee objEmp = (Employee)listDivHead.get(i);
                                                                    divHeadKey.add(objEmp.getFullName());
                                                                    divHeadValue.add(""+objEmp.getOID());                                                                   
                                                                }
                                                          
                                                          }
                                                          
                                                          String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(1)\"";
                                                          out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                  }
                                              	
                                               Vector vctSession = new Vector();                                               
                                               vctSession.add(vctStrDetail);
                                               vctSession.add(""+maxApproval);

                                               session.putValue("LEAVE_APPLICATION", vctSession);							  		  
                                          %>

                                          </b></td>                                          
                                          
                                          <td width="23%" ><b>
                                                <%=ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVE_DATE],(objLeaveApplication.getDepHeadApproveDate()==null ? new Date() : objLeaveApplication.getDepHeadApproveDate()), "getDepartHeadApprovalDate()")%>
                                              
                                        </b></td>
                                      </tr>

                                      <%}%>
                                      <!--Exc Prod-->
                                      <%
                                      if(objLeaveApplication.getOID()!=0 && (maxApproval == I_Leave.LEAVE_APPROVE_2 || maxApproval == I_Leave.LEAVE_APPROVE_3 || maxApproval == I_Leave.LEAVE_APPROVE_5) ){
                                      HRDMan = true;    
                                      %>
                                      <tr>
                                          <td width="15%" >HR. Manager</td>
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                          <%                                               												  
                                                    if(objLeaveApplication.getOID()!=0)
                                                    {
                                                          Vector divHeadKey = new Vector(1,1);
                                                          Vector divHeadValue = new Vector(1,1);                                                          

                                                          String selectedApproval = "";
                                                          
                                                          if(deptHead == true){
                                                              
                                                              if(objLeaveApplication.getDepHeadApproval() == 0){
                                                                  
                                                                    divHeadKey.add("Waiting Approval Dept. Head");
                                                                    divHeadValue.add("0");
                                                                  
                                                                  
                                                              }else{
                                                                  
                                                                    divHeadKey.add("Select Hr Manager");
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
                                                              
                                                              divHeadKey.add("Select Hr Manager");
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
                                                          out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                  }                
                                              											  		  
                                          %>

                                          </b></td>
                                          <td width="23%" ><b>
                                              <%=ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVE_DATE],(objLeaveApplication.getHrManApproveDate()==null ? new Date() : objLeaveApplication.getHrManApproveDate()), "getHRApprovalDate()")%>
                                          </b></td>
                                      </tr>
                                      <%}%>
                                      <%
                                      if(objLeaveApplication.getOID()!=0 && maxApproval == I_Leave.LEAVE_APPROVE_3){
                                      %>
                                      <tr>
                                          <td width="15%" >General Manager</td >
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
                                                                  
                                                                   divHeadKey.add("Waiting Approval HR. Manager");
                                                                   divHeadValue.add("0");
                                                                  
                                                              }else{
                                                                  
                                                                divHeadKey.add("Select GM");
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
                                               <%=ControlDatePopup.writeDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_GM_APPROVE_DATE],(objLeaveApplication.getGmApprovalDate()==null ? new Date() : objLeaveApplication.getGmApprovalDate()), "getGMApprovalDate()")%>
                                          </b></td>
                                      </tr>  
                                     <%} %>
                                    </table>
                                  </td>
                                </tr>
                              </table>
                              <%
                                }
                              }
                              %>
                            </td>
                          </tr>
                        <tr> 
                            <td>&nbsp;</td>
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
                                ctrLine.setBackCaption("Back to home");
                                ctrLine.setConfirmDelCaption("Yes, Delete Leave Application");
                                ctrLine.setDeleteCaption("Delete Leave Application");
                                ctrLine.setSaveCaption("Save Leave Application"); 

                                if ((privDelete) && true)
                                {       
                                        
                                            ctrLine.setConfirmDelCommand(sconDelCom);
                                            ctrLine.setDeleteCommand(scomDel);
                                            ctrLine.setEditCommand(scancel);
                                        
                               }else{												 
                                        
                                            ctrLine.setConfirmDelCaption("");
                                            ctrLine.setDeleteCaption("");
                                            ctrLine.setEditCaption("");
                                        
                               }

                                if (!privAdd)
                                {
                                        
                                            ctrLine.setAddCaption("");
                                        
                                }
                                if(false)
                                { 
                                            ctrLine.setConfirmDelCaption("");
                                            ctrLine.setDeleteCaption("");
                                            ctrLine.setEditCaption("");
                                            ctrLine.setSaveCaption("");													
                                            ctrLine.setAddCaption("");                                        
                                }                                        
                                
                                if(iCommand == Command.GOTO)
                                    iCommand = Command.EDIT;
                                
                                out.println(ctrLine.drawImage(iCommand, iErrCode, errMsgLeaveApp));
                            %>
                            </td>
                        </tr>
                        <% 
                        if(objLeaveApplication.getOID()!=0){
                        %>
                        <tr> 
                                <td>
                                <table border =0><tr>
                                  <td width="20"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print data"></a></td>
                                  
                                  <td height="32" valign="middle" colspan="3" width="951"> 
                                    <a href="javascript:cmdPrint()" class="command">Print</a> </td>
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
<tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>>
      <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> 
    </td>
</tr>
</table>

</body>

<!-- #BeginEditable "script" -->
<script language="JavaScript">

</script>
<!-- #EndEditable -->

<!-- #EndTemplate -->
</html>
