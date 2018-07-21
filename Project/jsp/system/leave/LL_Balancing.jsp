
<%-- 
    Document   : LL_Balancing
    Created on : Dec 18, 2009, 9:16:02 AM
    Author     : Tu Roy
--%>

<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcLLUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.FrmLLUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcLLUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.LLUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstLLUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessLLUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.I_Leave" %>
<%@ page import = "com.dimata.system.entity.system.PstSystemProperty" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.LLStockManagement" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_OPNAME_LL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    
%>
<%!
    public String drawList(Vector objEmpLLData, Date opnameDate, int start, I_Leave leaveConfig,Hashtable hashDataLL,Hashtable hashLLstockmanagement){
        String DateSekarang = Formater.formatDate(opnameDate, "yyyy-MM-dd");
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
      //update by satrya 2013-09-02
      if(leaveConfig!=null &&leaveConfig.getLLShowEntile2()==false){
        ctrlist.addHeader("<center>No</center>","5%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","6%","2","0");
        ctrlist.addHeader("<center>Name</center>","14%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.COMMENCING_DATE)+"</center>","8%","2","0");
        ctrlist.addHeader("<center>Opname Previous</center>","8%","2","0");
       
        ctrlist.addHeader("<center>System</center>","25%","0","9");
        ctrlist.addHeader("<center>Balance of Prev Period</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.ENTITLE) +" Date</center>","5%","0","0");
        ctrlist.addHeader("<center>Exp Entitle</center>","10%","0","0");
      
        ctrlist.addHeader("<center>This Period Entitle</center>","5%","0","0");
        ctrlist.addHeader("<center>Total Entitle</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","5%","0","0");
        ctrlist.addHeader("<center>Will Be Taken</center>","5%","0","0");
        ctrlist.addHeader("<center>Expired</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.BALANCE)+"</center>","5%","0","0");  

        ctrlist.addHeader("<center>New</center>","25%","0","8");
        
         ctrlist.addHeader("<center>Balance of Prev Period</center>","5%","0","0");
        ctrlist.addHeader("<center>This Period Entitle</center>","5%","0","0");
      
        ctrlist.addHeader("<center>Total Entitle</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","5%","0","0");
        ctrlist.addHeader("<center>Will Be Taken</center>","5%","0","0");
        
        ctrlist.addHeader("<center>Expired</center>","5%","0","0");
        ctrlist.addHeader("<center>Eligible</center>","5%","0","0");                                 
        
      }else{
        ctrlist.addHeader("<center>No</center>","5%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","6%","2","0");
        ctrlist.addHeader("<center>Name</center>","14%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.COMMENCING_DATE)+"</center>","8%","2","0");
        ctrlist.addHeader("<center>Opname Previous</center>","8%","2","0");
       
        ctrlist.addHeader("<center>System</center>","25%","0","10");
        ctrlist.addHeader("<center>Balance of Prev Period</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.ENTITLE) +" Date</center>","5%","0","0");
        ctrlist.addHeader("<center>Exp Entitle 1</center>","10%","0","0");
      
        ctrlist.addHeader("<center>Exp Entitle 2</center>","10%","0","0");        
      
        ctrlist.addHeader("<center>This Period Entitle</center>","5%","0","0");
        
        ctrlist.addHeader("<center>Total Entitle</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","5%","0","0");
        ctrlist.addHeader("<center>Will Be Taken</center>","5%","0","0");
        ctrlist.addHeader("<center>Expired</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.BALANCE)+"</center>","5%","0","0");  

        ctrlist.addHeader("<center>New</center>","25%","0","8");
        ctrlist.addHeader("<center>Balance of Prev Period</center>","5%","0","0");
        ctrlist.addHeader("<center>This Period Entitle 1</center>","5%","0","0");
             
        ctrlist.addHeader("<center>This Period Entitle 2</center>","5%","0","0");
      
        
        ctrlist.addHeader("<center>Total Entitle</center>","5%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","5%","0","0");
        ctrlist.addHeader("<center>Will Be Taken</center>","5%","0","0");
        ctrlist.addHeader("<center>Expired</center>","5%","0","0");
        ctrlist.addHeader("<center>Eligible</center>","5%","0","0");                                
      
      
      }
        ctrlist.addHeader("<center>Note</center>","3%","2","0");
        ctrlist.addHeader("<center>Status</center>","13%","2","0"); 
        ctrlist.addHeader("Process/<br>Reprocess"+                
                "<br><a href=\"Javascript:SetAllCheckBoxes('"+FrmSrcLLUpload.FRM_LL_UPLOAD+"','data_is_process', true)\">Sel.All</a>"+
                "<br> <a href=\"Javascript:SetAllCheckBoxes('"+FrmSrcLLUpload.FRM_LL_UPLOAD+"','data_is_process', false)\">Del.All</a>"
                ,"7%","2","0");             
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();
        
        System.out.println("Membuat table dalam proses...");
        long tmptestOid=0;
        String empNumber=""; 
        for (int i = 0; i < objEmpLLData.size(); i++){
           try{
            Vector tempData = new Vector();
            tempData = (Vector)objEmpLLData.get(i);
            Employee objEmployee = new Employee();
            LLUpload objLLUpload = new LLUpload();
            boolean opnameNotYetProcess = false;            
            
            objEmployee = (Employee)tempData.get(0);
            objLLUpload = (LLUpload)tempData.get(1);           
            tmptestOid = objEmployee.getOID(); 
            empNumber = objEmployee.getEmployeeNum();
            int count_expired = 0;
            
            
            //LLUpload dataLL = SessLLUpload.getOpnamePrevious(objEmployee.getOID());
            LLUpload dataLL = (LLUpload)hashDataLL.get(objEmployee.getOID());
           
            
            //int iEntitled_Intv5 = SessLLUpload.getLLEntitled(objEmployee.getOID(), opnameDate, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
            //int iEntitled_Intv8 = SessLLUpload.getLLEntitled(objEmployee.getOID(), opnameDate, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);
            
            int configuration = leaveConfig!=null?leaveConfig.getIntervalLLinMonths()[LeaveConfigHR.iINTERVAL_LL_HARDROCK_I]:0;     // range mendapatkan LL (dalam bulan)
            int configuration_2 = leaveConfig!=null?leaveConfig.getIntervalLLinMonths()[LeaveConfigHR.iINTERVAL_LL_HARDROCK_II]:0;  // range mendapatkan LL (dalam bulan)

            Vector rowx = new Vector();            
            rowx.add(String.valueOf(i+1+start));
            
            rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objEmployee.getOID()+ "\">"
                    +"<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_UPLOAD_ID]+"\" value=\""+objLLUpload.getOID()+ "\">"
                    +objEmployee.getEmployeeNum());
            
            rowx.add("<input type=\"hidden\" name=\"data_emp_name\" value=\""+objEmployee.getFullName()+ "\">"+objEmployee.getFullName());
            
            if(objEmployee.getCommencingDate()!=null){
                
                rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\""+objEmployee.getCommencingDate()+ "\">"+String.valueOf(objEmployee.getCommencingDate()));
                Date dateCurrPerStart = new Date();
                Date dateLastPerEnd = new Date();
                //update by satrya 2013-07-04
                LLUpload dataLLUploadPrev = SessLLUpload.getLLOpnamePrevious(objEmployee.getOID(), objLLUpload.getOID());
                LLUpload dataLLUpload = SessLLUpload.getLLOpnameCurr(objEmployee.getOID(), objLLUpload.getOID());
                LLUpload dataLLUploadSameDateOpname = SessLLUpload.getLLOpnameSameDateOpname(objEmployee.getOID(), objLLUpload.getOID()); 
                
                float system_previous = 0;
                float system_ll = 0;
                float system_qty = 0;
                float system_taken = 0;
                float system_balance = 0;
                long systemID = 0;
                float entitle_1 = 0;
                float entitle_2 = 0;
                float tobeTaken =0;
                String exp_ent_1 = "-"; 
                String exp_ent_2 = "-";
                
                //LLStockManagement objllstockmanagement = SessLLUpload.getValueStockManagement(objEmployee.getOID());
                LLStockManagement objllstockmanagement = (LLStockManagement)hashLLstockmanagement.get(objEmployee.getOID());
                 String sEntitleDate="-";
                if(objllstockmanagement != null){
                    
                    systemID = objllstockmanagement.getOID();
                    system_previous = objllstockmanagement.getPrevBalance();
                    system_taken = objllstockmanagement.getQtyUsed();
                    entitle_1 = objllstockmanagement.getEntitled();
                    
                    system_qty = entitle_1 + + entitle_2 + system_previous;/* update by satrya 2013-10-09 objllstockmanagement.getLLQty()*/;
                         //update by satrya 2013-09-02
                    tobeTaken = objllstockmanagement.getToBeTaken();
                   if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                    entitle_2 = objllstockmanagement.getEntitle2();
                   }
                    system_ll = entitle_1 + entitle_2;
                    
                    if(objllstockmanagement.getExpiredDate() != null ){                       
                        
                        try{
                            exp_ent_1 = Formater.formatDate(objllstockmanagement.getExpiredDate(),"yyyy-MM-dd");
                        }catch(Exception e){
                            exp_ent_1 = "-";
                        }
                        
                    }
                   
                    if(objllstockmanagement.getEntitledDate()!= null ){
                            sEntitleDate = Formater.formatDate(objllstockmanagement.getEntitledDate(),"yyyy-MM-dd");
                    }else{
                        sEntitleDate ="-";
                    }
                    
                    if(objllstockmanagement.getExpiredDate2() != null && leaveConfig!=null && leaveConfig.getLLShowEntile2()){ 
                        try{
                            exp_ent_2 = Formater.formatDate(objllstockmanagement.getExpiredDate2(),"yyyy-MM-dd");
                        }catch(Exception e){
                            exp_ent_2 = "-";
                        }
                    }
                    
                    
                    count_expired = SessLLUpload.getTotalExpired(objllstockmanagement.getOID());
                    system_balance = system_qty - system_taken - count_expired - tobeTaken;
                    //update by satrya 2013-10-01
                    //system_balance = system_previous + system_qty - system_taken - count_expired - tobeTaken;
                    //System.out.println("System balance : "+system_previous+"+"+system_qty+"-"+system_taken+"-"+count_expired+"="+system_balance);
                }
                
                String tot_entitle = ""+entitle_1+"+"+entitle_2;
                String DateSekarangOpname = DateSekarang;                
                //String DateSekarangOpnameSame = "";
                String opnameClear = "";
                 String opnameKosong = "!nothing date opname previous";
                float opPrevious = 0;
                float opLL = 0;
                float opTaken = 0;
                float opQty = 0;
                float opBalance = 0;
                float opLL2 = 0;
                // opname previous
                if(dataLLUpload != null){
                    try{
                       DateSekarangOpname = Formater.formatDate(dataLLUpload.getOpnameDate(), "yyyy-MM-dd"); 
                    }catch(Exception e){
                        System.out.println("Exception :::: "+e.toString());
                    }    
                }
                //create by satrya 2013-01-06
                 
                if(dataLLUpload != null){
                    try{
                        
                     if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataLLUpload.getOpnameDate())>=0)){
                         opnameNotYetProcess = true;
                         // DateSekarangOpnameSame = Formater.formatDate(dataLLUpload.getOpnameDate(), "yyyy-MM-dd");
                        opPrevious = dataLLUpload.getLastPerToClearLL();
                        opLL = dataLLUpload.getNewLL();
                       //update by satrya 2013-09-02
                       if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                        opLL2 = dataLLUpload.getNewLL2();
                       }
                        opTaken = dataLLUpload.getLlTakenYear1();
                        opQty = opLL + opLL2 + opPrevious/*update by satrya 2013-10-09 dataLLUpload.getLLQty()*/;
                        opBalance = opQty - opTaken - count_expired /* update by satrya 2013-10-09 opPrevious + opQty - opTaken - count_expired*/;
                       
                     }else{
                       if(dataLLUploadPrev!=null){
                           opnameNotYetProcess = true;
                          //DateSekarangOpnameSame = Formater.formatDate(dataLLUploadPrev.getOpnameDate(), "yyyy-MM-dd");
                        opPrevious = dataLLUploadPrev.getLastPerToClearLL();
                        opLL = dataLLUploadPrev.getNewLL();
                       //update by satrya 2013-09-02
                       if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                        opLL2 = dataLLUploadPrev.getNewLL2();
                       }
                        opTaken = dataLLUploadPrev.getLlTakenYear1();
                        opQty = opLL + opLL2 + opPrevious/*update by satrya 2013-10-09 dataLLUploadPrev.getLLQty()*/;
                        opBalance = opQty - opTaken - count_expired /*opPrevious + opQty - opTaken - count_expired*/;
                        
                       }else{
                             opnameNotYetProcess = true;
                          //DateSekarangOpnameSame = Formater.formatDate(dataLLUploadSameDateOpname.getOpnameDate(), "yyyy-MM-dd");
                        opPrevious = dataLLUploadSameDateOpname.getLastPerToClearLL();
                        opLL = dataLLUploadSameDateOpname.getNewLL();
                       //update by satrya 2013-09-02
                       if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                        opLL2 = dataLLUploadSameDateOpname.getNewLL2();
                       }
                        opTaken = dataLLUploadSameDateOpname.getLlTakenYear1();
                        opQty = opLL + opLL2 + opPrevious/*update by satrya 2013-10-09 dataLLUploadSameDateOpname.getLLQty()*/;
                        opBalance = opQty - opTaken - count_expired/*opPrevious + opQty - opTaken - count_expired*/;
                        
                       }
                     }  
                    }catch(Exception e){
                        System.out.println("Exception :::: "+e.toString());
                    }    
                }
                
                
                if(dataLLUpload == null){                                                  
                    rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+opnameClear+"\" >"+opnameClear);
                }else{
                    if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataLLUpload.getOpnameDate())>=0)){
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLLUploadPrev.getOpnameDate()+"\" >"+String.valueOf(dataLLUploadPrev.getOpnameDate()));
                    }else{
                        //update by satyrya
                       if(dataLLUploadPrev!=null){
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLLUploadPrev.getOpnameDate()+"\" >"+String.valueOf(dataLLUploadPrev.getOpnameDate()));
                       }else{
                           rowx.add("<p align=\"center\" title=\""+opnameKosong+"\">"+"-"+"</p>"+"<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLLUploadSameDateOpname.getOpnameDate()+"\" >");
                       }
                    }
                }
                
                
                
               /* if(dataLLUpload == null){                                                  
                     rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+opnameClear+"\" >"+opnameClear);
                }else{
                    if(dataLLUploadSameDateOpname!=null && DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataLLUpload.getOpnameDate())>=0)){
                         opnameNotYetProcess = true;
                          DateSekarangOpnameSame = Formater.formatDate(dataLLUploadSameDateOpname.getOpnameDate(), "yyyy-MM-dd");
                        opPrevious = dataLLUploadSameDateOpname.getLastPerToClearLL();
                        opLL = dataLLUploadSameDateOpname.getNewLL();
                        opLL2 = dataLLUploadSameDateOpname.getNewLL2();
                        opTaken = dataLLUploadSameDateOpname.getLlTakenYear1();
                        opQty = dataLLUploadSameDateOpname.getLLQty();
                         opBalance = opPrevious + opQty - opTaken - count_expired;
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLLUploadSameDateOpname.getOpnameDate()+"\" >"+String.valueOf(dataLLUploadSameDateOpname.getOpnameDate()));
                    }else{
                      
                       if(dataLLUploadPrev!=null){
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLLUploadPrev.getOpnameDate()+"\" >"+String.valueOf(dataLLUploadPrev.getOpnameDate()));
                        DateSekarangOpname = Formater.formatDate(dataLLUploadPrev.getOpnameDate(), "yyyy-MM-dd");
                                //update by satyrya
                                opnameNotYetProcess = true;
                                 try{
                             if(dataLLUpload!=null){ 
                              DateSekarangOpname = Formater.formatDate(dataLLUpload.getOpnameDate(), "yyyy-MM-dd"); 
                               opPrevious = dataLLUpload.getLastPerToClearLL();
                              opLL = dataLLUpload.getNewLL();
                              opLL2 = dataLLUpload.getNewLL2();
                              opTaken = dataLLUpload.getLlTakenYear1();
                              opQty = dataLLUpload.getLLQty();
                             }
                          }catch(Exception e){
                              System.out.println("EXCEPTION :::: "+e.toString());
                          } 
                          opBalance = opPrevious + opQty - opTaken - count_expired;
                       }else{
                           rowx.add("<p align=\"center\" title=\""+opnameKosong+"\">"+"-"+"</p>"+"<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLLUploadSameDateOpname.getOpnameDate()+"\" >");
                                                           //update by satyrya
                                opnameNotYetProcess = true;
                                 try{
                             if(dataLLUploadSameDateOpname!=null){ 
                              DateSekarangOpnameSame = Formater.formatDate(dataLLUploadSameDateOpname.getOpnameDate(), "yyyy-MM-dd"); 
                               opPrevious = dataLLUploadSameDateOpname.getLastPerToClearLL();
                              opLL = dataLLUploadSameDateOpname.getNewLL();
                              opLL2 = dataLLUploadSameDateOpname.getNewLL2();
                              opTaken = dataLLUploadSameDateOpname.getLlTakenYear1();
                              opQty = dataLLUploadSameDateOpname.getLLQty();
                             }
                          }catch(Exception e){
                              System.out.println("EXCEPTION :::: "+e.toString());
                          } 
                          opBalance = opPrevious + opQty - opTaken - count_expired;
                       }
                    }
                } hidden create ramayu*/ 
              
              /*  if(dataLL == null){                                                  
                    rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+opnameClear+"\" >"+opnameClear);
                }else{
                    opnameNotYetProcess = true;
                                       
                    try{
                        DateSekarangOpname = Formater.formatDate(dataLL.getOpnameDate(), "yyyy-MM-dd"); 
                    }catch(Exception e){
                        System.out.println("EXCEPTION :::: "+e.toString());
                    }     
                    
                    opPrevious = dataLL.getLastPerToClearLL();
                    opLL = dataLL.getNewLL();
                    opLL2 = dataLL.getNewLL2();
                    opTaken = dataLL.getLlTakenYear1();
                    opQty = dataLL.getLLQty();
                    opBalance = opPrevious + opQty - opTaken - count_expired;
                    if(DateSekarang.equals(DateSekarangOpname)){
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLL.getOpnameDate()+"\" >"+String.valueOf(dataLL.getOpnameDate()));
                    }else{
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataLL.getOpnameDate()+"\" >"+String.valueOf(dataLL.getOpnameDate()));
                    }
                }*/  
                //update by satrya 2013-09-02
               rowx.add("<input type=\"hidden\" name=\"system_previous"+i+"\" value=\"\">"+system_previous); 
               
                rowx.add(""+sEntitleDate);
                rowx.add(""+exp_ent_1);
               //update by satrya 2013-09-02
               if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                rowx.add(""+exp_ent_2);
               }
               //update by satrya 2013-09-03
               if(leaveConfig!=null && leaveConfig.getLLShowEntile2()==false){
                rowx.add("<input type=\"hidden\" name=\"system_ll"+i+"\" value=\"\">"+entitle_1);
               }else{
                rowx.add("<input type=\"hidden\" name=\"system_ll"+i+"\" value=\"\">"+entitle_1+"+"+entitle_2);
               }
                
                //rowx.add("<input type=\"hidden\" name=\"system_previous"+i+"\" value=\"\">"+system_previous); 
                rowx.add("<input type=\"hidden\" name=\"system_qty"+i+"\" value=\"\">"+system_qty);
                rowx.add("<input type=\"hidden\" name=\"system_taken"+i+"\" value=\"\">"+system_taken);
                rowx.add(""+tobeTaken);
                rowx.add(""+count_expired);
                rowx.add("<input type=\"hidden\" name=\"system_balance"+i+"\" value=\"\">"+system_balance);
                
                float opnameBalance = 0;  
                
                boolean minInterval = SessLLUpload.getMinLLTime(objEmployee.getCommencingDate(),configuration,opnameDate);
                
                //String strReadOnly=(minInterval)?"":"readonly";
                String strReadOnly= "";
                
                if(objllstockmanagement == null && minInterval){
                
                    Date entitle_date_1 = SessLLUpload.getHaveStockLL(objEmployee.getOID(),configuration);
                    String str_date_1 = Formater.formatDate(entitle_date_1,"yyyy-MM-dd");
                    
                    Employee employee = new Employee();
                    EmpCategory empCategory = new EmpCategory();
                    Level level = new Level();
                    
                    try{
                        employee = PstEmployee.fetchExc(objEmployee.getOID());
                    }catch(Exception e){
                        System.out.println("Exception " + e.toString());
                    }
                    
                    try {
                        empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
                    } catch (Exception e) {
                        System.out.println("Exception " + e.toString());
                    }

                    try {
                        level = PstLevel.fetchExc(employee.getLevelId());
                    } catch (Exception e) {
                        System.out.println("Exception " + e.toString());
                    }
                    
                    float var_ll_entitle_I = 0;
                    //hiden by satrya 2013-07-06
                    //var_ll_entitle_I = leaveConfig.getLLEntile(level.getLevel(), empCategory.getEmpCategory(), configuration);
                     rowx.add("<input type=\"text\" name=\"new_previous"+i+"\" value=\""+0.0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    rowx.add("<input type=\"text\" name=\"new_ll"+i+"\" value=\""+var_ll_entitle_I+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                   //update by satrya 2013-09-02
                   if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                    rowx.add("<input type=\"text\" name=\"new_ll_2"+i+"\" value=\""+0.0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                   }
                   
                    rowx.add("<input type=\"text\" readonly=\"readonly\"  name=\"new_qty"+i+"\" value=\""+var_ll_entitle_I+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    rowx.add("<input type=\"text\" name=\"new_taken"+i+"\" value=\""+0.0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    
                    rowx.add("<input readonly=\"readonly\" type=\"text\" name=\"new_tobe_taken"+i+"\" value=\""+tobeTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    rowx.add(""+0);
                    rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"new_balance"+i+"\" value=\""+var_ll_entitle_I+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    rowx.add("");
                    
                }else{
                
                
                if(opnameNotYetProcess){
                     /*if(DateSekarang.equals(DateSekarangOpname)){
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_ll"+i+"\" value=\""+opLL+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_ll_2"+i+"\" value=\""+opLL2+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_previous"+i+"\" value=\""+opPrevious+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_qty"+i+"\" value=\""+opQty+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_taken"+i+"\" value=\""+opTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add(""+count_expired);
                        rowx.add("<input "+strReadOnly+" type=\"text\" Readonly name=\"new_balance"+i+"\" value=\""+opBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        
                        if(objllstockmanagement == null)
                            rowx.add("");
                        else
                            rowx.add("<a href=\"javascript:cmdNote('"+systemID+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a>");
                     }else{                       
                        rowx.add("<input "+strReadOnly+" type=\"text\" disabled=\"true\" name=\"new_ll"+i+"\" value=\""+opLL+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" disabled=\"true\" name=\"new_ll_2"+i+"\" value=\""+opLL2+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" disabled=\"true\" name=\"new_previous"+i+"\" value=\""+opPrevious+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" disabled=\"true\" name=\"new_qty"+i+"\" value=\""+opQty+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" disabled=\"true\" name=\"new_taken"+i+"\" value=\""+opTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add(""+count_expired);
                        rowx.add("<input "+strReadOnly+" type=\"text\" disabled=\"true\" name=\"new_balance"+i+"\" value=\""+opBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        if(objllstockmanagement == null)
                            rowx.add("");
                        else
                            rowx.add("<img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\">");
                     }*/ 
                     if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataLLUpload.getOpnameDate())>=0)){
                         rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_previous"+i+"\" value=\""+opPrevious+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                         rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_ll"+i+"\" value=\""+opLL+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                       //update by satrya 2013-09-02
                       if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_ll_2"+i+"\" value=\""+opLL2+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                       }
                        
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_qty"+i+"\" value=\""+opQty+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_taken"+i+"\" value=\""+opTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        //update by satrya 2013-09-03
                        rowx.add("<input readonly=\"readonly\" type=\"text\" name=\"new_tobe_taken"+i+"\" value=\""+tobeTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        
                        rowx.add(""+count_expired);
                        rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_balance"+i+"\" value=\""+opBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                        
                        if(objllstockmanagement == null)
                            rowx.add("");
                        else
                            rowx.add("<a href=\"javascript:cmdNote('"+systemID+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a>");
                        }else{
                            if(dataLLUploadPrev!=null){
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_previous"+i+"\" value=\""+opPrevious+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");//previous
                             //start preve
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_ll"+i+"\" value=\""+opLL+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                               //update by satrya 2013-09-02
                               if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_ll_2"+i+"\" value=\""+opLL2+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                               }
                                
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_qty"+i+"\" value=\""+opQty+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_taken"+i+"\" value=\""+opTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                
                                //update by satrya 2013-09-03
                                rowx.add("<input disabled=\"true\" type=\"text\" readonly=\"readonly\" name=\"new_tobe_taken"+i+"\" value=\""+tobeTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                
                                rowx.add(""+count_expired);
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_balance"+i+"\" value=\""+opBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                if(objllstockmanagement == null)
                                    rowx.add("");
                                else
                                    rowx.add("<img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\">");
                            }else{
                               rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_previous"+i+"\" value=\""+opPrevious+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                //sameLL
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_ll"+i+"\" value=\""+opLL+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                               //update by satrya 2013-09-02
                               if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_ll_2"+i+"\" value=\""+opLL2+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                               }
                                
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_qty"+i+"\" value=\""+opQty+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_taken"+i+"\" value=\""+opTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                
                                rowx.add("<input readonly type=\"text\" readonly=\"readonly\" name=\"new_tobe_taken"+i+"\" value=\""+tobeTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                rowx.add(""+count_expired);
                                rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_balance"+i+"\" value=\""+opBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                                if(objllstockmanagement == null)
                                    rowx.add("");
                                else
                                    rowx.add("<img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\">");
                            }
                        }
                }else{   

                    rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_previous"+i+"\" value=\""+system_previous+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    
                    rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_ll"+i+"\" value=\""+entitle_1+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                  //update by satrya 2013-09-02
                  if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
                    rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_ll_2"+i+"\" value=\""+entitle_2+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                   }
                    rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_qty"+i+"\" value=\""+system_qty+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    rowx.add("<input "+strReadOnly+" type=\"text\" name=\"new_taken"+i+"\" value=\""+system_taken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    
                    //update by satrya 2013-09-03
                    rowx.add("<input readonly=\"readonly\" type=\"text\" name=\"new_tobe_taken"+i+"\" value=\""+tobeTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
                    rowx.add(""+count_expired);
                    rowx.add("<input "+strReadOnly+" type=\"text\" readonly=\"readonly\" name=\"new_balance"+i+"\" value=\""+system_balance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");      
                    if(objllstockmanagement == null)
                        rowx.add("");                                           
                    else    
                        rowx.add("<a href=\"javascript:cmdNote('"+systemID+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a>");                                           
                }               
               
                }
                
                if(objllstockmanagement == null){
                    
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">"+"<center>NO STOCK</center>");
                    
                }else{
                
                    if(objLLUpload.getDataStatus()==PstLLUpload.FLD_DOC_STATUS_PROCESS){
                        rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_PROCESS+"\">"+PstLLUpload.fieldStatusNames[PstLLUpload.FLD_DOC_STATUS_PROCESS]);
                    
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                    }
                }
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){ 
                    if(opnameNotYetProcess){
                        if(DateSekarang.equals(DateSekarangOpname)){
                            rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                        }else{
                            rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                        }
                    }else{
                        rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                    }                           
                }else{
                    rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"0\" disabled=\"true\"></center>");
                }
                
            }else{
               
                /*rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\"\">");
                rowx.add("<input type=\"hidden\" name=\"data_ll_entitled\">0");
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR1]+"\" value=\"0\"><input type\"text\" size=\"3\" class=\"elemenForm\" value=\"0\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");*/
                
     rowx.add("<input name=\"data_emp_comm_date\" value=\""+null+"\" type=\"hidden\">"+"please insert commercing date");
     rowx.add("<input name=\"opname_previous"+i+"\" value=\"\" type=\"hidden\">");  
    rowx.add("<input type=\"hidden\" name=\"system_previous"+i+"\" value=\"\">"+0);  
     rowx.add("-");
     
     if(leaveConfig!=null &&leaveConfig.getLLShowEntile2()){
         rowx.add("");//expired 1
        rowx.add("<input type=\"hidden\" name=\"system_ll"+i+"\" value=\"\">"+0); 
    }else{
        rowx.add("");//expired 1
        rowx.add("");//expired 2
        rowx.add("<input type=\"hidden\" name=\"system_ll"+i+"\" value=\"\">"+0+"+"+0); 
    }
     rowx.add("<input type=\"hidden\" name=\"system_qty"+i+"\" value=\"\">"+0);
     rowx.add("<input type=\"hidden\" name=\"system_taken"+i+"\" value=\"\">"+0);
     rowx.add("<input type=\"hidden\" name=\"new_tobe_taken"+i+"\" value=\"\">"+0);//will be taken
     rowx.add(""+count_expired);
     rowx.add("<input type=\"hidden\" name=\"system_balance"+i+"\" value=\"\">"+0);
    

   rowx.add("<input  type=\"text\" name=\"new_previous"+i+"\" value=\""+0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
   rowx.add("<input  type=\"text\" name=\"new_ll"+i+"\" value=\""+0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
   //update by satrya 2013-09-02
   if(leaveConfig!=null && leaveConfig.getLLShowEntile2()){
    rowx.add("<input  type=\"text\" name=\"new_ll_2"+i+"\" value=\""+0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
   }
    rowx.add("<input  type=\"text\" name=\"new_qty"+i+"\" value=\""+0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
    rowx.add("<input  type=\"text\" name=\"new_taken"+i+"\" value=\""+0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");
    
    //update by satrya 2013-09-03
    rowx.add("<input  type=\"text\" name=\"new_tobe_taken"+i+"\" value=\""+0+"\" size=\"5\" readonly=\"readonly\" onkeyup=\"cmdCekLast("+i+")\">");
    rowx.add(""+count_expired);
   
    rowx.add("<input  type=\"text\" readonly=\"readonly\" name=\"new_balance"+i+"\" value=\""+0+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+")\">");      
    //rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR1]+"\" value=\"0\"><input type\"text\" size=\"3\" class=\"elemenForm\" value=\"0\" disabled=\"true\">");
    rowx.add("");                                           
    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
    rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");
                            
            }
            lstData.add(rowx);
 }catch(Exception exc){ 
    System.out.println("Exception drawlist LL Balancing"+exc+ " EmpId="+tmptestOid+" epmNumber="+empNumber);
 }
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.drawList();
    }
%>

<%
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_DEPT]);
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    SrcLLUpload srcLLUpload = new SrcLLUpload();
    FrmSrcLLUpload objFrmSrcLLUpload = new FrmSrcLLUpload(request, srcLLUpload);
    CtrlLLUpload ctrlLLUpload = new CtrlLLUpload(request);
    objFrmSrcLLUpload.requestEntityObject(srcLLUpload);
    ControlLine ctrLine = new ControlLine();
    
    // Proses jika command adalah SAVE	
    if (iCommand == Command.SAVE || iCommand == Command.ACTIVATE) {
	
        String[] emp_id = null;
        String[] llUpload_id = null;
        String[] data_status = null;        
        String[] new_ll = null;
        String[] new_previous = null;
        String[] new_qty = null;
        String[] new_taken = null;
        String[] new_tobe_taken=null;
        String[] new_ll_2 = null;
        boolean[] is_process = null;
        
        Date dateOpname;
        
        Vector vLLUpload = new Vector(1,1);
	// Mengambil array nilai2 berikut
        try {
            emp_id = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_EMPLOYEE_ID]);
            llUpload_id = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_UPLOAD_ID]);
            data_status = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]);
            new_previous = new String[data_status.length];
            new_ll = new String[data_status.length];
            new_ll_2 = new String[data_status.length];
            new_qty = new String[data_status.length];
            new_taken = new String[data_status.length];
            
            new_tobe_taken = new String[data_status.length]; 
            dateOpname = (Date)srcLLUpload.getOpnameDate().clone();
                        
            is_process = new boolean[data_status.length];//request.getParameterValues("data_is_process");
            for(int i=0; i<emp_id.length; i++){
                new_previous[i] = FRMQueryString.requestString(request,"new_previous"+i);
                new_ll[i] = FRMQueryString.requestString(request,"new_ll"+i);
                new_ll_2[i] = FRMQueryString.requestString(request,"new_ll_2"+i);
                new_qty[i] = FRMQueryString.requestString(request,"new_qty"+i);
                new_taken[i] = FRMQueryString.requestString(request,"new_taken"+i);	
                	
                new_tobe_taken[i] = FRMQueryString.requestString(request,"new_tobe_taken"+i);	
                
                int ix = FRMQueryString.requestInt(request, "data_is_process"+i);
                if(ix==1){
                    is_process[i] = true;
                }else{
                    is_process[i] = false;
                }
            }
            
            vLLUpload.add(emp_id);      //0
            vLLUpload.add(llUpload_id); //1
            vLLUpload.add(data_status); //2
            vLLUpload.add(is_process);  //3
            vLLUpload.add(dateOpname);  //4
            
            vLLUpload.add(new_previous);  //5
            vLLUpload.add(new_ll);  //6
            vLLUpload.add(new_qty); //7
            vLLUpload.add(new_taken); //8
            ///update by satrya 2013-09-03
            vLLUpload.add(new_tobe_taken);
            vLLUpload.add(new_ll_2); //9
        }
        catch (Exception e) 
        {
            System.out.println("[ERROR] OpnameLL : "+e.toString());
        }
        
        if(iCommand == Command.SAVE){//jika disimpan saja
            System.out.println("Simpan Data............................................");
            Vector vLLUploadId = new Vector(1,1);
            vLLUploadId = com.dimata.harisma.session.leave.SessLLUpload.saveLLUploadBalance(vLLUpload);
            if(vLLUploadId.size()>0){
                status = true;
            }else{
                status = false;
            }
            
        }else if(iCommand == Command.ACTIVATE) {//Jika diproses
            System.out.println("Process data..........................................");
            Vector vLLUploadId = new Vector(1,1);
            vLLUploadId = com.dimata.harisma.session.leave.SessLLUpload.saveLLUploadBalance(vLLUpload);
            status = com.dimata.harisma.session.leave.SessLLUpload.opnameLLAllData(vLLUploadId);
        }
    }

    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 50;
    int vectSize = 0;
	    
    Vector vDataLLToUpload = new Vector(1,1);
     I_Leave leaveConfig = null;
   Hashtable hashDataLL = null;
   Hashtable hashLLstockmanagement = null;
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
    ||(iCommand==Command.LAST)||(iCommand==Command.LIST) || (iCommand == Command.ACTIVATE) )
    {
        
    try {
         leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
    }
    catch(Exception e) {
        System.out.println("Exception : " + e.getMessage());
    }
    hashDataLL = SessLLUpload.getHashOpnamePrevious();
    hashLLstockmanagement = SessLLUpload.getHashValueStockManagement(); 
    
        Vector vTemp = new Vector(1,1);
        vTemp = com.dimata.harisma.session.leave.SessLLUpload.searchLLData(srcLLUpload, 0, 0);
        vectSize = vTemp.size();
        if(iCommand == Command.ACTIVATE){
                start = ctrlLLUpload.actionList(Command.NEXT, start, vectSize, recordToGet);
               }else{
                start = ctrlLLUpload.actionList(iCommand, start, vectSize, recordToGet);            
               }
        vDataLLToUpload = com.dimata.harisma.session.leave.SessLLUpload.searchLLData(srcLLUpload, start, recordToGet);
    }else{
        start = 0;
    }
    
%>

<script language="JavaScript">
function cmdNote(hiddenOID){	
	window.open("note_edit_LL.jsp?command="+<%=Command.EDIT%>+"&hidden_LL_id="+hiddenOID, null, "height=300,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}
function cmdUpdateDep(){
	document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.ADD)%>";  
	document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="LL_Balancing.jsp"; 
	document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdSave(){
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "LL_Balancing.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdProccess() {
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.ACTIVATE)%>";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "LL_Balancing.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function deptChange() {
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "LL_Balancing.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdSearch() {
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.LIST)%>";
    getThn();										
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "LL_Balancing.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdViewList(){    
    window.open("ll_opname_list.jsp", null, "height=500,width=400, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");

}
function cmdCekLast(index){
    var val1;
    var val2;
    var prev;
    var taken;
    var tobetaken;
    var qty;
    var balance;
    //update by satrya 2013-10-02
    var eligible1;
    var eligible2;
    switch(index){
    <%
        for(int k=0;k< vDataLLToUpload.size();k++){
    %>
            case <%=""+k%>:           
            prev = parseFloat(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="new_previous"+k%>.value);
            taken = parseFloat(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="new_taken"+k%>.value);
            
            tobetaken = parseFloat(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="new_tobe_taken"+k%>.value);
            
            //update by satrya 2013-10-02
            //qty = parseFloat(document.<//%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<//%="new_qty"+k%>.value);
            eligible1 = parseFloat(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="new_ll"+k%>.value);
            if(<%=leaveConfig!=null && leaveConfig.getLLShowEntile2()%>){ 
               eligible2 = parseFloat(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="new_ll_2"+k%>.value); 
               qty = prev + (eligible1 + eligible2);
            }else{
                qty = prev + (eligible1);
                
            }
             
              
            balance = (qty ) - taken - tobetaken;
            //update by satrya 2013-10-02
            // balance = ( prev + qty ) - taken - tobetaken;
            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="new_qty"+k%>.value=qty;
            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="new_balance"+k%>.value=balance;
            
         //break;
    <%break;}%>
    }  
} 




function cmdCek(index){
    var val1;
    var val2;
    switch(index){
    <%
        for(int k=0;k<vDataLLToUpload.size();k++){
    %>
            case <%=""+k%>:
            val1 = parseFloat(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            val2 = parseFloat(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
            if(val1>0 && val2>0){
                alert("Data not valid...");
            }
           
         //break;
    <% break;}%>
    }
    
    
}




//---------------------------------------------------
    function getThn(){
            var date1 = ""+document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_mn.value=bln;
            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_dy.value=hri;
            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_yr.value=thn;
    }

    function hideObjectForDate(){
    }

    function showObjectForDate(){
    } 

    function setChecked(val) {
	dml=document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;
	}
    }
//-------------- script control line -------------------
        function cmdListFirst(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="LL_Balancing.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="LL_Balancing.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="LL_Balancing.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="LL_Balancing.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}


        function MM_swapImgRestore() 
        { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
        }

        function MM_preloadImages() 
        { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
        }

        function MM_findObj(n, d) 
        { //v4.0
                var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                if(!x && document.getElementById) x=document.getElementById(n); return x;
        }

        function MM_swapImage() 
        { //v3.0
                var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
        }
        
<!--
function SetAllCheckBoxes(FormName, FieldName, CheckValue)
{
	if(!document.forms[FormName])
		return;
            var objCheckBoxes ;
<% if (vDataLLToUpload.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST) ||(iCommand==Command.ACTIVATE) )) { 
           for(int idx=0; idx<vDataLLToUpload.size();idx++){
       %>
	objCheckBoxes = document.forms[FormName].elements["data_is_process<%=idx%>"];
	if(!objCheckBoxes)
		return;	
        objCheckBoxes.checked = CheckValue;
     <% }
       }%>
}
// -->                
        
</script>
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<style type="text/css">
<!--
.styleInt8 {
	background-color: #fff7b8;
}
-->
input[readonly] {
    color: graytext;
    background: buttonface;
}
</style>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Long Leave Management</title>




<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!--<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css"> -->



</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
     
    <tr><td id="ds_calclass">
    </td></tr>
</table>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
      
    </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Leave Balancing &gt; Long Leave <!-- #EndEditable --> 
                  </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>;"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% if (privAdd) { %>
                                    <form name="<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>" method="post" action="">
                                    <%if(iCommand == Command.SAVE || iCommand == Command.ACTIVATE){ %>
                                        <input type="hidden" name="command" value="<%=String.valueOf(Command.LIST)%>">
                                    <%}else{%>
                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <%}%>
				    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="3"><b><u><font color="#FF0000">ATTENTION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>OPNAME</b> 
                                                  long leave to database<br>                                               
                                                  <hr>
                                                </td>
                                              </tr>                                              

                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_NAME]%>"  value="<%=String.valueOf(srcLLUpload.getEmployeeName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_PAYROLL]%>"  value="<%=String.valueOf(srcLLUpload.getEmployeePayroll())%>" class="elemenForm">
                                                </td>
                                              </tr>
                                             
                                              <tr> 
                                                <td width="11%">Category</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
							Vector cat_value = new Vector(1,1);
							Vector cat_key = new Vector(1,1);        
							cat_value.add("0");
							cat_key.add("all category ...");                                                          
							Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
							for (int i = 0; i < listCat.size(); i++) {
								EmpCategory cat = (EmpCategory) listCat.get(i);
								cat_key.add(cat.getEmpCategory());
								cat_value.add(String.valueOf(cat.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_CAT],"formElemen",null,String.valueOf(srcLLUpload.getEmployeeCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                       /* Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");
                                                        //String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }*/
                                                  Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    //Vector listDept = new Vector(1, 1);
                                                                                                    DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {
                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager) {
                                                                                                                keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                            } else {
                                                                                                                Position position = null;
                                                                                                                try {
                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                } catch (Exception exc) {
                                                                                                                }
                                                                                                                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
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
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                                                    //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            //dept_value.add("0");
                                                                                                            //dept_key.add("select ...");
                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                            //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                        }
                                                                                                    } else {
                                                                                                        //dept_value.add("0");
                                                                                                        //dept_key.add("select ...");
                                                                                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                    }
                                                                                                    dept_value = keyList.getDepIDs();
                                                                                                    dept_key = keyList.getDepNames();

                                                                                                    /*for (int i = 0; i < listDept.size(); i++) {
                                                                                                    Department dept = (Department) listDept.get(i);
                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    } */


                                                                                                    String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();

                                                    %>
                                                  <%//= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %>
                                                  <%//= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_DEPT],"formElemen",null,String.valueOf(srcLLUpload.getEmployeeDepartement()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %> 
                                                <%=ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_DEPT], "elementForm", null, selectValueDepartment, dept_value, dept_key, " onChange=\"javascript:cmdUpdateDep()\"")%> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                <% 
							Vector sec_value = new Vector(1,1);
							Vector sec_key = new Vector(1,1); 
							sec_value.add("0");
							sec_key.add("all section ...");
                                                        String strWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
							Vector listSec = PstSection.list(0, 0, strWhere, " DEPARTMENT_ID, SECTION ");
							for (int i = 0; i < listSec.size(); i++) {
								Section sec = (Section) listSec.get(i);
								sec_key.add(sec.getSection());
								sec_value.add(String.valueOf(sec.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_SEC],"formElemen",null,String.valueOf(srcLLUpload.getEmployeeSection()), sec_value, sec_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Position</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                              <% 
							Vector pos_value = new Vector(1,1);
							Vector pos_key = new Vector(1,1); 
							pos_value.add("0");
							pos_key.add("all position ...");                                                       
							Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                        for (int i = 0; i < listPos.size(); i++) {
								Position pos = (Position) listPos.get(i);
								pos_key.add(pos.getPosition());
								pos_value.add(String.valueOf(pos.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_POS],"formElemen",null,String.valueOf(srcLLUpload.getEmployeePosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      <tr> 
                                          	<td width="13%">Opname Date</td>
                                          	<td width="2%">:</td>
                                          	<td width="85%">
                                                    <input onClick="ds_sh(this);" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>" readonly="readonly" style="cursor: text" value="<%=Formater.formatDate((srcLLUpload.getOpnameDate() == null? new Date() : srcLLUpload.getOpnameDate()), "yyyy-MM-dd")%>"/>
                                                    <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_mn">
                                                    <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_dy">
                                                    <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_yr">
                                                    
                                                    <a href="javascript:cmdViewList()" class="buttonlink"><img src="../../images/icon/folderopen.gif" border="0" alt="null"></a>
                                                    <script language="JavaScript" type="text/JavaScript">getThn();</script>
                                                     <% //ControlDate.drawDate(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE],srcLLUpload.getOpnameDate(),+5,-10) %>
                                                 
                                                </td>
                                              </tr>
                                              <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_DATA_STATUS]%>" value="-1">                                              
                                              <tr> 
                                                <td width="11%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="88%"> 
                                                  <input type="submit" name="Submit" value="Search Employee" onClick="javascript:cmdSearch()">
                                                </td>
                                              </tr>
                                              
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                            <% if(iCommand == Command.SAVE && status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="fffff9">
                                                <tr>
                                                    <td>
                                                        <center>SAVE DATA SUCCESS</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                            <% if(iCommand == Command.SAVE && !status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="red">
                                                <tr>
                                                    <td>
                                                        <center>SAVE DATA FAILED</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                            <% if(iCommand == Command.ACTIVATE && status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="fffff9">
                                                <tr>
                                                    <td>
                                                        <center>PROCESS DATA SUCCESS</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                            <% if(iCommand == Command.ACTIVATE && !status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="red">
                                                <tr>
                                                    <td>
                                                        <center>PROCESS DATA FAILED</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                          <% //
                                          if (vDataLLToUpload.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST) || (iCommand == Command.ACTIVATE) )) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <%/*  
                                              <tr>
                                                <td align="right" valign="top">
                                                    [<a onclick="javascript:setChecked(1)"><b>Check All</b></a>]
                                                    |[<a onclick="javascript:setChecked(0)"><b>Uncheck All</b></a>]
                                                    <img src="<%=imagesroot*/%><%/*arrow_ltr.png">
                                                </td>
                                              </tr>
 *                                            */%>  
                                              <tr>
       
                                                <td>
                                                    <%=drawList(vDataLLToUpload, srcLLUpload.getOpnameDate(),start,leaveConfig,hashDataLL,hashLLstockmanagement)%>
                                                </td>
                                              </tr>
					      <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit((iCommand==Command.ACTIVATE)?Command.NEXT: iCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                    <tr> 
                                                      
                                                      <td width="50" class="command" nowrap>
                                                          <br />
                                                         <!--  <a href="javascript:cmdSave()">Save</a>
                                                      || --> <a href="javascript:cmdProccess()">Proccess</a></td>
                                                    </tr> 
                                                  </table>                      
                                                </td>
                                              </tr>
                                            </table>
                                          <% } %>
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
                                    <!-- #EndEditable -->
                            </td>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>


