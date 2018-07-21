
<%-- 
    Document   : AL_Balancing
    Created on : Dec 5, 2009, 9:23:26 AM
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
<%@ page import = "com.dimata.harisma.form.search.FrmSrcAlUpload" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcAlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessOpnameAL" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.AlStockManagement" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_OPNAME_AL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
 long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    
%>
<%!
    public String drawList(Vector objEmpALData, Date opnameDate,int start){
        String systemMessage="";
        String DateSekarang = Formater.formatDate(opnameDate, "yyyy-MM-dd");

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("<center>No</center>","5%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","5%","2","0");
        ctrlist.addHeader("<center>Name</center>","14%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.COMMENCING_DATE)+"</center>","8%","2","0");
        ctrlist.addHeader("<center>Opname Previous</center>","8%","2","0");

        ctrlist.addHeader("<center>System</center>","25%","0","7");  
        ctrlist.addHeader("<center>Balance of  Prev Period</center>","5%","0","0"); //previous
        ctrlist.addHeader("<center>Entitle Date","5%","0","0");  
        ctrlist.addHeader("<center>This Period Entitle","5%","0","0");//Entitle
        ctrlist.addHeader("<center>Total Entitle","5%","0","0"); //Current Entitle       
        ctrlist.addHeader("<center>Taken","5%","0","0");
 //update by satrya 2012-10-16
        ctrlist.addHeader("<center>Will be Taken","5%","0","0");           
        ctrlist.addHeader("<center>Eligible","5%","0","0");
        

        ctrlist.addHeader("<center>New</center>","25%","0","6");   
        ctrlist.addHeader("<center>Balance of Prev Period</center>","5%","0","0");
        ctrlist.addHeader("<center>This Period Entitle</center>","5%","0","0");//Entitle
        ctrlist.addHeader("<center>Total Entitle</center>","5%","0","0");//Current Entitle
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center> ","5%","0","0");
         //update by satrya 2012-10-16
        ctrlist.addHeader("<center>Will be Taken","5%","0","0");
        ctrlist.addHeader("<center>Eligible</center> ","5%","0","0"); 
        
        ctrlist.addHeader("<center>Note</center> ","3%","2","0");
        ctrlist.addHeader("<center>Status</center>","13%","2","0");
        ctrlist.addHeader("Process/<br>Reprocess<br>" +
                "<a href=\"Javascript:SetAllCheckBoxes('"+FrmSrcAlUpload.FRM_AL_UPLOAD+"','data_is_process', true)\">Sel.All</a>"+
                "<br> <a href=\"Javascript:SetAllCheckBoxes('"+FrmSrcAlUpload.FRM_AL_UPLOAD+"','data_is_process', false)\">Del.All</a>"
                        ,"7%","2","0");
       
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();

        I_Leave leaveConfig = null;

        try{
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        }catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "Please contact your system administration to setup system property: LEAVE_CONFIG ";
        }
        //update by satrya 2012-10-17
         String formatFloat ="###.###";
        for(int i = 0; i < objEmpALData.size(); i++){            
          try{
            Vector tempData = new Vector();
            tempData = (Vector)objEmpALData.get(i);
            
            Employee objEmployee = new Employee();
            AlUpload objAlUpload = new AlUpload();
            
            objEmployee = (Employee)tempData.get(0);
            objAlUpload = (AlUpload)tempData.get(1);
            
            Vector rowx = new Vector();
            rowx.add(String.valueOf(i+1+start));

            boolean stsCanBalance = SessLeaveApplication.getStsHaveStock(objEmployee.getCommencingDate(),leaveConfig);
            
            //employee id
            rowx.add("<input type=\"hidden\" name=\"data_emp_id\" value=\""+objEmployee.getOID()+ "\">"
                    +"<input type=\"hidden\" name=\"data_alUpload_id\" value=\""+objAlUpload.getOID()+ "\">"
                    +objEmployee.getEmployeeNum());
            
            //employee num
            if(stsCanBalance){
                rowx.add("<input type=\"hidden\" name=\"data_emp_name\" value=\""+objEmployee.getFullName()+ "\">"
                    +objEmployee.getFullName());
            }else{
                rowx.add("<input type=\"hidden\" name=\"data_emp_name\" value=\""+objEmployee.getFullName()+ "\">"
                    +"<font color = FF0000>"+objEmployee.getFullName()+"</font>");
            }            
            
            if(objEmployee.getCommencingDate()!=null){
                
                //commencing date
                rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\""+objEmployee.getCommencingDate()+ "\">"+String.valueOf(objEmployee.getCommencingDate()));
               
                //untuk update prev balabnce yang sudah expired
                SessLeaveApplication.updateOpnamePrevBalance(objEmployee.getOID(),objAlUpload.getOID());
                
                AlUpload dataAlUploadPrev = SessAlUpload.getALOpnamePrevious(objEmployee.getOID(), objAlUpload.getOID()); //pengambilan data opname yang belum diproses

                //update by satrya 2013-01-06
                // ini untuk yg next date
                AlUpload dataAlUpload = SessAlUpload.getALOpnameCurr(objEmployee.getOID(), objAlUpload.getOID()); //pengambilan data opname yang belum diproses
                // ini untuk yg sama dipilih di date opname
                AlUpload dataAlUploadSameDateOpname = SessAlUpload.getALOpnameSameDateOpname(objEmployee.getOID(), objAlUpload.getOID()); //pengambilan data opname yang belum diproses
                AlStockManagement alStockManagementData = SessAlUpload.getValueStockManagement(objEmployee.getOID());
                Level level = new Level();
                if(objEmployee.getLevelId()!=0){
                try{
                    level = PstLevel.fetchExc(objEmployee.getLevelId());
                }catch(Exception exc){
                    systemMessage =systemMessage  + "<br> Employee : "+ objEmployee.getEmployeeNum()+" / "+ objEmployee.getFullName()+" has no level set : Leave entile failed";                    
                }
                } else{
                    systemMessage =systemMessage  + "<br> Employee : "+ objEmployee.getEmployeeNum()+" / "+ objEmployee.getFullName()+" has no level set : Leave entile failed";
                  }
                EmpCategory category = new EmpCategory();
                if(objEmployee.getEmpCategoryId()!=0){
                try{
                    category = PstEmpCategory.fetchExc(objEmployee.getEmpCategoryId());
                }catch(Exception exc){
                    System.out.print("Exc cat"+exc);
                    systemMessage =systemMessage  + "<br> Employee : "+ objEmployee.getEmployeeNum()+" / "+ objEmployee.getFullName()+" has no category set : Leave entile failed";                    
                }
                } else{
                    systemMessage =systemMessage  + "<br> Employee : "+ objEmployee.getEmployeeNum()+" / "+ objEmployee.getFullName()+" has no category set : Leave entile failed";
                  }
                
                //String strAl_curr = String.valueOf(com.dimata.harisma.session.leave.SessAlUpload.getAlEarned(objEmployee.getOID(), objAlUpload.getOpnameDate()));
                String strAl_curr = ""+ leaveConfig.getALEntitleAnualLeave( level.getLevel(), category.getEmpCategory() , 0, objEmployee.getCommencingDate(), opnameDate)  ;//String.valueOf(com.dimata.harisma.session.leave.SessAlUpload.getAlEntitle(objEmployee.getOID(), opnameDate));
                
                long DataUploadOID = 0;
                float ValResidue = 0;
                float PrevBalance = 0;
                float PrevTaken = 0;                
                float ALLast = 0;
                float AlQty = 0;
                //update by satrya 201-10-16
                float AL2BTaken = 0;
                float systemBalance = 0;
                long stockManagementID = 0;
                String EntDt = "-";
                
                boolean statusPrevExpired = false;
                
                if(alStockManagementData!=null){
                    
                    statusPrevExpired = SessLeaveApplication.getStatusLeaveAlExpired(alStockManagementData.getOID()); 
                    
                    stockManagementID = alStockManagementData.getOID();    
                    if(statusPrevExpired == true){
                        PrevBalance = 0;
                    }else{
                        PrevBalance = alStockManagementData.getPrevBalance();
                    }
                    DataUploadOID = alStockManagementData.getOID();
                    PrevTaken = alStockManagementData.getQtyUsed();
                    ALLast = alStockManagementData.getEntitled();
                    AlQty = alStockManagementData.getEntitled() + PrevBalance/* update by satrya 2013-10-09 alStockManagementData.getAlQty()*/;
                    AL2BTaken = alStockManagementData.getALtoBeTaken();
                    EntDt = alStockManagementData.getEntitleDate()==null ?"-": Formater.formatDate(alStockManagementData.getEntitleDate(),"yyyy-MM-dd");
                    ///update by satrya 2012-10-16
                    //  ValResidue = (PrevBalance + AlQty) - PrevTaken;
                    //ValResidue = PrevBalance + AlQty - PrevTaken - AL2BTaken; 
                    //update by satrya 2013-10-2
                     ValResidue = AlQty - PrevTaken - AL2BTaken; 
                    
                    systemBalance = AlQty - PrevTaken - AL2BTaken;                    
                    //update by satrya 2013-10-02
                    //systemBalance = PrevBalance + AlQty - PrevTaken - AL2BTaken;                    
                }               
                
                String DateSekarangOpname = DateSekarang;
                
                float bal = 0;
                
                if(dataAlUpload != null){
                    try{
                        //bal =dataAlUpload.getNewQty() + dataAlUpload.getLastPerToClear() - dataAlUpload.getCurrPerTaken() - AL2BTaken;
                        //update by satrya 2012-10-16
                         //bal = dataAlUpload.getNewQty() + dataAlUpload.getLastPerToClear() - dataAlUpload.getCurrPerTaken(); 
                        DateSekarangOpname = Formater.formatDate(dataAlUpload.getOpnameDate(), "yyyy-MM-dd"); 
                    }catch(Exception e){
                        System.out.println("Exception :::: "+e.toString());
                    }    
                }
                //create by satrya 2013-01-06
                 
                if(dataAlUpload != null){
                    try{
                        
                     if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0)){
                        bal =dataAlUpload.getNewQty() + dataAlUpload.getLastPerToClear() - dataAlUpload.getCurrPerTaken() - AL2BTaken;
                     }else{
                       if(dataAlUploadPrev!=null){
                         bal =dataAlUploadPrev.getNewQty() + dataAlUploadPrev.getLastPerToClear() - dataAlUploadPrev.getCurrPerTaken() - AL2BTaken;
                       }else{
                           bal =dataAlUploadSameDateOpname.getNewQty() + dataAlUploadSameDateOpname.getLastPerToClear() - dataAlUploadSameDateOpname.getCurrPerTaken() - AL2BTaken;
                       }
                     }  
                    }catch(Exception e){
                        System.out.println("Exception :::: "+e.toString());
                    }    
                }
                
                String opnameClear = "";
                String opnameKosong = "!nothing date opname previous";
                // opname previous
                if(dataAlUpload == null){                                                  
                    rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+opnameClear+"\" >"+opnameClear);
                }else{
                    if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0)){
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataAlUploadPrev.getOpnameDate()+"\" >"+String.valueOf(dataAlUploadPrev.getOpnameDate()));
                    }else{
                        //update by satyrya
                       if(dataAlUploadPrev!=null){
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataAlUploadPrev.getOpnameDate()+"\" >"+String.valueOf(dataAlUploadPrev.getOpnameDate()));
                       }else{
                           rowx.add("<p align=\"center\" title=\""+opnameKosong+"\">"+"-"+"</p>"+"<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataAlUploadSameDateOpname.getOpnameDate()+"\" >");
                       }
                    }
                }            
             
                Date dateCurrPerStart = new Date();
                Date dateLastPerEnd = new Date();
                String strAl_last="";
                
                try{
                    
                    dateCurrPerStart = com.dimata.harisma.session.leave.SessAlUpload.getStartPeriodDate(objEmployee.getOID(), opnameDate);
                    dateLastPerEnd = dateCurrPerStart;
                    dateLastPerEnd.setDate(dateCurrPerStart.getDate()-1);
                    strAl_last = String.valueOf(com.dimata.harisma.session.leave.SessAlUpload.getAlEarned(objEmployee.getOID(), dateLastPerEnd));
                    
                }catch(Exception ex){                   
                    System.out.println("Exception "+ex.toString());
                }    
                
                rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\""+PrevBalance+"\">"+PrevBalance); 
                //update by satrya 2013-01-04
                // rowx.add("<input type=\"text\" name=\"data_emp_earned_last_per\" value=\"\">"+PrevBalance);       
                if(alStockManagementData!=null){  
                    rowx.add("<input type=\"hidden\" size=\"\" name=\"data_emp_entitle_date"+i+"\"  value=\""+alStockManagementData.getEntitleDate()!=null ?Formater.formatDate((alStockManagementData.getEntitleDate()), "yyyy-MM-dd") :"-"+"\"/>"+alStockManagementData.getEntitleDate()!=null ?Formater.formatDate((alStockManagementData.getEntitleDate()), "yyyy-MM-dd") :"-");
                    //rowx.add("<input onClick=\"ds_sh(this);\" size=\"\" name=\"data_emp_entitle_date"+i+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((alStockManagementData.getEntitleDate() == null ? new Date() : alStockManagementData.getEntitleDate()), "yyyy-MM-dd")+"\"/>");
                }else{
                    rowx.add("-");
                }                                             
                
                rowx.add("<input type=\"hidden\" name=\"data_emp_AL_last_per\" value=\"\">"+Formater.formatNumber(ALLast, formatFloat));
                rowx.add("<input type=\"hidden\" name=\"data_emp_ALQty_last_per\" value=\"\">"+Formater.formatNumber(AlQty, formatFloat));                
                rowx.add("<input type=\"hidden\" name=\"data_emp_taken_last_per\" value=\"\">"+Formater.formatNumber(PrevTaken, formatFloat));
                 //update by satrya 2012-10-16
                rowx.add("<input type=\"hidden\" name=\"data_emp_al2bTaken_per\" value=\"\">"+Formater.formatNumber(AL2BTaken, formatFloat));
                
                rowx.add("<input type=\"hidden\" name=\"data_emp_Balance_last_per\" value=\"\">"+Formater.formatNumber(systemBalance, formatFloat)); 
                
                  

                //new previous                
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                       
                    if(dataAlUpload != null){
                        
                        if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0)){
                            if(statusPrevExpired == true){
                                rowx.add("<input type=\"text\" Readonly name=\"data_emp_to_clear_last_per"+i+"\" value=\""+dataAlUpload.getLastPerToClear()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                            }else{                                                                                   
                                rowx.add("<input type=\"text\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+dataAlUpload.getLastPerToClear()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                            }
                        }else{
                            if(dataAlUploadPrev!=null){
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+dataAlUploadPrev.getLastPerToClear()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                            //create by satrya 2013-01-06
                            //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+dataAlUpload.getLastPerToClear()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                            }else{
                                rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+dataAlUploadSameDateOpname.getLastPerToClear()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                            }
                        }
                           
                    }else{
                            
                        if(statusPrevExpired == true){
                        
                            //rowx.add("<input type=\"text\" Readonly name=\"data_emp_to_clear_last_per"+i+"\"  value=\""+PrevBalance+"\" size=\"5\" onkeyup=\"cmdCekQty("+i+","+strAl_last+")\">");                        
                            //update by satrya 2013-10-02
                            rowx.add("<input type=\"text\" Readonly name=\"data_emp_to_clear_last_per"+i+"\"  value=\""+PrevBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                        
                        }else{
                            
                            //rowx.add("<input type=\"text\" name=\"data_emp_to_clear_last_per"+i+"\"  value=\""+PrevBalance+"\" size=\"5\" onkeyup=\"cmdCekQty("+i+","+strAl_last+")\">"); 
                            //update by satrya 2013-10-02
                            rowx.add("<input type=\"text\" name=\"data_emp_to_clear_last_per"+i+"\"  value=\""+PrevBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                        }
                    }
                    
                }else{                   
                      rowx.add("<input type=\"hidden\" disabled=\"true\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+0+"\"><input type=\"text\" name=\"\"  value=\"0\" size=\"5\" disabled=\"true\">");                    
                }         
              
                //new AL
                if(objAlUpload.getOID()==0){
                    if(dataAlUpload != null)  
                        if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0))
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(dataAlUpload.getNewAl(), formatFloat)+"\">");                    
                        else
                          if(dataAlUploadPrev!=null){
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(dataAlUploadPrev.getNewAl(), formatFloat)+"\">");                    
                            //update by satrya 2013-01-06
                            //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+Formater.formatNumber(dataAlUpload.getNewAl(), formatFloat)+"\">");                    
                           }else{
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(dataAlUploadSameDateOpname.getNewAl(), formatFloat)+"\">");                    
                           }
                    else{
                        if(alStockManagementData!=null){
                            
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(alStockManagementData.getEntitled(), formatFloat)+"\">");                    
                            
                        }else{
                            
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+strAl_curr+"\">");                 
                            
                        }
                   }
                }else{ 
                    if(dataAlUpload != null)  
                        if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0))
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(dataAlUpload.getNewAl(), formatFloat)+"\">");                    
                        else
                             if(dataAlUploadPrev!=null){
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(dataAlUploadPrev.getNewAl(), formatFloat)+"\">");                    
                    //update by satrya 2013-01-6
                    //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+Formater.formatNumber(dataAlUpload.getNewAl(), formatFloat)+"\">");                    
                             }else{
                                rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(dataAlUploadSameDateOpname.getNewAl(), formatFloat)+"\">");                    
                             }
                    else
                        if(alStockManagementData!=null){
                            //rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekQty("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(alStockManagementData.getEntitled(), formatFloat)+"\">");                    
                            //update by satrya 2013-10-02
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+Formater.formatNumber(alStockManagementData.getEntitled(), formatFloat)+"\">");                    
                        }else{
                            //rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekQty("+i+","+strAl_last+")\" value=\""+strAl_curr+" \">");                                                                                  
                            //update by satrya 2013-10-02
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\" value=\""+strAl_curr+" \">");                                                                                  
                        }
                }               
                
                float qty = 0;
                
                if(dataAlUpload!=null){
                    try{
                        qty = dataAlUpload.getNewQty();
                       // System.out.println(" Total qty : "+qty);
                    }catch(Exception e){
                        System.out.println("EXCEPTION "+e.toString());
                    }
                }
                
                //new QTY
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                    if(dataAlUpload != null){
                        if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0))                   
                            rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(qty, formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                       
                        else
                              if(dataAlUploadPrev!=null){
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(dataAlUploadPrev.getNewQty(), formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                        //update by satrya 2013-01-6
                        //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(qty, formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                              }else{
                                rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(dataAlUploadSameDateOpname.getNewQty(), formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                              }
                    }else{
                        if(alStockManagementData!=null){
                           rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(AlQty, formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                             //update by satrya 2013-10-1
                             //rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(AlQty, formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                        }else{
                             rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+strAl_curr+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                             //update by satrya 2013-10-02
                             //rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+strAl_curr+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                        }     
                    }
                }else{
                    if(dataAlUpload != null){
                        if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0))                   
                            rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(qty, formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                       
                        else
                            if(dataAlUploadPrev!=null){
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(dataAlUploadPrev.getNewQty(), formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                        //update by satrya 2013-01-6
                        //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(qty, formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                            }else{
                                rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(dataAlUploadSameDateOpname.getNewQty(), formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                            }
                    }else{
                       if(alStockManagementData!=null){
                             rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+Formater.formatNumber(AlQty, formatFloat)+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                        }else{
                             rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+strAl_curr+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                        }    
                   }
                }                                                 
                
                //new Taken
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                   if(dataAlUpload != null){   
                     if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0))  
                        rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+Formater.formatNumber(dataAlUpload.getCurrPerTaken(), formatFloat)+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                     else
                         if(dataAlUploadPrev!=null){
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+Formater.formatNumber(dataAlUploadPrev.getCurrPerTaken(), formatFloat)+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                     //update by satrya 2013-01-06
                     //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+Formater.formatNumber(dataAlUpload.getCurrPerTaken(), formatFloat)+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                         }else{
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+Formater.formatNumber(dataAlUploadSameDateOpname.getCurrPerTaken(), formatFloat)+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                         }
                   }else{
                    rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\"  value=\""+Formater.formatNumber(PrevTaken, formatFloat)+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                   } 
                }else{
                   if(dataAlUpload != null){   
                     if(DateSekarang.equals(DateSekarangOpname)&& (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0))  
                        rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUpload.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                     else
                         if(dataAlUploadPrev!=null){
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUploadPrev.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                     //update by satrya 2013-01-06
                      //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUpload.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                          }else{
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUploadSameDateOpname.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                          }
                   }else{
                        rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+PrevTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                   }
                }
                
                //Balance
                if(dataAlUpload != null){ 
                      //update by satrya 2012-10-16
                      //rowx.add(""+AL2BTaken); 
                    rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_al2bTaken_per"+i+"\" size=\"5\" value=\""+Formater.formatNumber(AL2BTaken, formatFloat)+"\">");
                    //rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_al2bTaken_per"+i+"\" size=\"5\" value=\""+AL2BTaken+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                    rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_balance_per"+i+"\" size=\"5\" value=\""+Formater.formatNumber(bal, formatFloat)+"\">"); 
                  
                }else{
                    if(alStockManagementData == null){
                        //update by satrya 2012-10-17
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_al2bTaken_per"+i+"\" size=\"5\" value=\""+Formater.formatNumber(AL2BTaken, formatFloat)+"\">");
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_balance_per"+i+"\" size=\"5\" value=\""+strAl_curr+"\">");  
                        
                    }else{
                        //update by satrya 2012-10-17
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_al2bTaken_per"+i+"\" size=\"5\" value=\""+Formater.formatNumber(AL2BTaken, formatFloat)+"\">");
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_balance_per"+i+"\" size=\"5\" value=\""+Formater.formatNumber(ValResidue, formatFloat)+"\">"); 
                        
                    }
                }
                                
                //note
                //update by satrya 2013-01-06
                 if(objAlUpload.getOID()!=0){ 
                     rowx.add("<center><a href=\"javascript:cmdNote('"+objAlUpload.getOID()+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a></center>");
                 }else{
                    rowx.add("");
                 }
                
                /*    if(alStockManagementData!=null){
                        rowx.add("<center><a href=\"javascript:cmdNote('"+stockManagementID+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a></center>");
                    }else{
                        rowx.add("");
                    }*/
                
                
                //process                
                if(alStockManagementData==null){
                    rowx.add("<input type=\"hidden\" name=\"data_status\" value=\""+PstAlUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">"+"<center>NO STOCK</center>");
                }else{
                    if(objAlUpload.getDataStatus()==PstAlUpload.FLD_DOC_STATUS_PROCESS){
                        rowx.add("<input type=\"hidden\" name=\"data_status\" value=\""+PstAlUpload.FLD_DOC_STATUS_PROCESS+"\"><center>"
                            +PstAlUpload.fieldStatusNames[PstAlUpload.FLD_DOC_STATUS_PROCESS]+"</center>");                   
                    }else{
                        rowx.add("<input type=\"hidden\" name=\"data_status\" value=\""+PstAlUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                    }
                }
                
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                    if(dataAlUpload != null) 
                        if(DateSekarang.equals(DateSekarangOpname) && (com.dimata.util.DateCalc.timeDifference(opnameDate, dataAlUpload.getOpnameDate())>=0))
                            if(stsCanBalance){
                                rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                            }else{
                                 if(dataAlUploadPrev!=null){
                                rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                                 }else{
                                   rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                                 }
                            }
                        else
                            rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                    else
                        if(stsCanBalance){
                            rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                        }else{
                            rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                        }
                }else{
                        rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"0\" disabled=\"false\"></center>");
                }
                
            }else{
             
                rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\""+null+"\">"+"please insert commercing Date");
                rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+0.0+"\" >"+String.valueOf(""));
                //rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\\>"+"0");
                rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\""+0.0+"\">"+0.0); 
                rowx.add("<input onClick=\"ds_sh(this);\" size=\"\" name=\"data_emp_entitle_date"+i+"\" readonly=\"readonly\" style=\"cursor: text\" value=\"\"/>");
                rowx.add("<input type=\"hidden\" name=\"data_emp_AL_last_per\" value=\""+0.0+"\">"+0.0); 
               rowx.add("<input type=\"hidden\" name=\"data_emp_ALQty_last_per\" value=\""+0.0+"\">"+0.0); 
                rowx.add("<input type=\"hidden\" name=\"data_emp_taken_last_per\" value=\""+0.0+"\">"+0.0);
                
                rowx.add("<input type=\"hidden\" name=\"data_emp_al2bTaken_per\" value=\""+0.0+"\">"+0.0); 
                rowx.add("<input type=\"hidden\" name=\"data_emp_Balance_last_per\" value=\""+0.0+"\">"+0.0); 
                rowx.add("<input type=\"hidden\" name=\"data_emp_to_clear_last_per"+i+"\" onkeyup=\"cmdCekLast(0,0.0)\" value=\""+0.0+"\">"+0.0);  
                rowx.add("<input type=\"hidden\" name=\"data_emp_earned_curr_per"+i+"\" onkeyup=\"cmdCekLast(0,0.0)\" value=\""+0.0+"\">"+0.0);
                //update by satrya 2013-10-02
                //rowx.add("<input type=\"hidden\" name=\"data_emp_earned_curr_per"+i+"\" value=\""+0.0+"\">"+0.0);
                rowx.add("<input type=\"hidden\" name=\"data_emp_ALQty_new"+i+"\" value=\""+0.0+"\">"+0.0);  
                rowx.add("<input type=\"hidden\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+0.0+"\">"+0.0); 
                
                //rowx.add("<input type=\"hidden\" name=\"data_emp_to_clear_last_per"+i+"\" value=\"0\"><input type=\"text\" name=\"\"  value=\"0\" class=\"elemenForm\" disabled=\"true\">");
                //rowx.add("<input type=\"hidden\" name=\"data_emp_earned_curr_per"+i+"\" value=\"0\">"+"0");
                //rowx.add("<input type=\"hidden\" name=\"data_emp_taken_curr_per"+i+"\" value=\"0\"><input type=\"text\" name=\"\"  value=\"0\" class=\"elemenForm\" disabled=\"true\">");
                //update by satrya 2012-10-17
                rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_al2bTaken_per"+i+"\" size=\"5\" value=\"0.0\">");
                rowx.add("<input type=\"hidden\" disabled=\"true\" name=\"data_emp_balance_per"+i+"\" value=\"\">"+0.0); 
                rowx.add("");
                rowx.add("<input type=\"hidden\" name=\"data_status\" value=\""+PstAlUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">"); 
                rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");
            }
            lstData.add(rowx);        
                       } catch(Exception exc){
                System.out.println(">> "+i+" : "+exc);
            }
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.drawList() + "<br>"+systemMessage;
        
    }
%>

<%
    String systemMessage="";
    System.out.println("position OID "+positionOID);
    long GM_OID = 0;
    
    try{
        String stOIDGM = ""+PstSystemProperty.getValueByName("OID_GM");
    if(stOIDGM!=null && stOIDGM.length()>0){
        GM_OID=Long.parseLong(stOIDGM);    
    }
    System.out.println("GM OID "+GM_OID);
    } catch(Exception exc){
        System.out.println("[exception] "+exc);
        System.out.println("GM OID  - NOT DEFINED");
    }
    
    boolean statusManager = true;
    
    System.out.println("Status manager "+statusManager);    
    
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_DEPT]);
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    SrcAlUpload srcAlUpload = new SrcAlUpload();
  
    FrmSrcAlUpload objFrmSrcAlUpload = new FrmSrcAlUpload(request, srcAlUpload);
    objFrmSrcAlUpload.requestEntityObject(srcAlUpload);
    CtrlAlUpload ctrlAlUpload = new CtrlAlUpload(request);
    
    Date dateNow = new Date();
    System.out.println("Date sekarang : "+dateNow.getMonth());
    
    Date dt_StartDate = new Date();
    
    String prev = Formater.formatDate(dt_StartDate, "yyyy");
    
    System.out.println("tahun previous : "+prev);
    
    int YPrev = Integer.parseInt(prev)-1;
    
    System.out.println("tahun previous 2 : "+YPrev);
				
    String str_dt_StartDate = Formater.formatDate(dt_StartDate, "yyyy-MM-dd");
    
    String TglSekarang = "";
    
    TglSekarang = dateNow.getYear()+"-"+dateNow.getMonth()+"-"+dateNow.getDate();
    System.out.println("Tgl sekarang "+TglSekarang);    
   
    String potongan = TglSekarang.substring(0,4);
    
    System.out.println("Potongan "+potongan);
    
    ControlLine ctrLine = new ControlLine();
    
    Date selectedDateOpName = srcAlUpload.getOpnameDate();
    // Proses jika command adalah SAVE	
    if (iCommand == Command.SAVE || iCommand == Command.ACTIVATE) {
	
        String[] emp_id = null;		
        String[] alUpload_id = null;		
        String[] emp_name = null;
        String[] emp_comm_date = null;
        String[] emp_earned_last_per = null;
        String[] emp_to_clear_last_per = null;
        String[] emp_earned_curr_per = null;				
        String[] emp_taken_curr_per = null;				
        String[] data_status = null;
        String[] emp_ALQty_new = null;  
        String[] emp_entitle_date = null;  
        //update by satrya 2012-10-16
        //String[] emp_2bTaken = null;  
        boolean[] is_process = null;				
        Vector vAlUpload = new Vector();
	// Mengambil array nilai2 berikut
        
        try {
            
            emp_id = request.getParameterValues("data_emp_id");
            alUpload_id = request.getParameterValues("data_alUpload_id");
            emp_name = request.getParameterValues("data_emp_name");
            emp_comm_date = request.getParameterValues("data_emp_comm_date");
            emp_earned_last_per = request.getParameterValues("data_emp_earned_last_per");
            
            data_status = request.getParameterValues("data_status");
            
            is_process = new boolean[data_status.length];				
            emp_to_clear_last_per = new String[data_status.length];
            emp_earned_curr_per  = new String[data_status.length];
            emp_taken_curr_per = new String[data_status.length];
            emp_ALQty_new = new String[data_status.length];
            emp_entitle_date = new String[data_status.length];
            
            
            for(int i=0; i<emp_id.length; i++){
                emp_to_clear_last_per[i] = FRMQueryString.requestString(request,"data_emp_to_clear_last_per"+i);
                emp_earned_curr_per[i] = FRMQueryString.requestString(request,"data_emp_earned_curr_per"+i);				
                emp_taken_curr_per[i] = FRMQueryString.requestString(request,"data_emp_taken_curr_per"+i);				
                emp_earned_last_per[i] = FRMQueryString.requestString(request,"data_emp_earned_last_per"); 
//update by satrya 2013-01-04                
// emp_earned_last_per[i] = FRMQueryString.requestString(request,"data_emp_earned_last_per"+i);                               
                emp_ALQty_new[i] = FRMQueryString.requestString(request,"data_emp_ALQty_new"+i);
                emp_entitle_date[i] = FRMQueryString.requestString(request,"data_emp_entitle_date"+i);
               // emp_2bTaken[i] = FRMQueryString.requestString(request,"data_emp_al2bTaken_per"+i);                    
                
                int ix = FRMQueryString.requestInt(request, "data_is_process"+i);
                if(ix==1){
                    is_process[i] = true;
                }else{
                    is_process[i] = false;
                }
            }
            
            vAlUpload.add(emp_id);//0
            vAlUpload.add(alUpload_id);//1
            vAlUpload.add(emp_to_clear_last_per);//2
            vAlUpload.add(emp_taken_curr_per);//3
            vAlUpload.add(data_status);//4
            vAlUpload.add(is_process);//5
            vAlUpload.add(srcAlUpload.getOpnameDate());//6
            vAlUpload.add(emp_earned_curr_per);//7
            vAlUpload.add(emp_earned_last_per);//8
            vAlUpload.add(emp_ALQty_new);//9
            vAlUpload.add(emp_entitle_date);//10
            vAlUpload.add(emp_comm_date);//11
            
        }
        catch (Exception e) 
        {
            System.out.println("[ERROR] OpnameAl : "+e.toString());
        }
        
        if(iCommand == Command.SAVE){//jika disimpan saja
            System.out.println("Simpan Data............................................");
            Vector vAlUploadId = new Vector(1,1);
            vAlUploadId = com.dimata.harisma.session.leave.SessAlUpload.saveAlUploadBalance(vAlUpload,false);
            if(vAlUploadId.size()>0){
                status = true;
            }else{
                status = false;
            }
            
            //System.out.println("STATUS DATA :::::::::::::::::::::::::::::::::::::::: "+status);
        }else if(iCommand == Command.ACTIVATE) {//Jika diproses
            System.out.println("Process data..........................................");
            Vector vAlUploadId = new Vector(1,1);
            try{
            //vAlUploadId = com.dimata.harisma.session.leave.SessAlUpload.saveAlUpload(vAlUpload);
            vAlUploadId = com.dimata.harisma.session.leave.SessAlUpload.saveAlUploadBalance(vAlUpload,true);                        
            if(vAlUploadId!=null && vAlUploadId.size()>0){
                    systemMessage= (String) vAlUploadId.get(vAlUploadId.size()-1);                    
                    long testId= 0;
                    try{
                        testId = Long.parseLong(systemMessage);
                    }catch(Exception exc){                        
                    }
                    if(testId==0 || systemMessage.startsWith("SYSTEM :<BR>")){                 
                        status=false;
                        vAlUploadId.remove(vAlUploadId.size()-1); // remove message dari fungsi
                     } else {                        
                        status = com.dimata.harisma.session.leave.SessAlUpload.opnameALAllData(vAlUploadId,selectedDateOpName);                        
                     }
                    
            }
           } catch(Exception exc){
               systemMessage= systemMessage+ " "+ exc;
           }
            
            //com.dimata.harisma.session.leave.SessAlUpload.updateEntitleDate(vAlUpload); //untuk melakkukan update entitle sesua dengan commencing date            
        }
    }

    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 200;
    int vectSize = 0;
    
   Vector vDataAlToUpload = new Vector(1,1);
   // if(srcAlUpload.getOpnameDate()!=null && (iCommand != Command.SAVE && iCommand != Command.ACTIVATE && iCommand != Command.ADD)){
   //     vDataAlToUpload = com.dimata.harisma.session.leave.SessAlUpload.searchAlData(srcAlUpload, 0, 0);
   // }
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
    ||(iCommand==Command.LAST)||(iCommand==Command.LIST) ||(iCommand==Command.ACTIVATE))
    {   //Vector vTemp = new Vector(1,1);
        //vTemp = com.dimata.harisma.session.leave.SessAlUpload.searchAlData(srcAlUpload, 0, 0);
        //vectSize = vTemp.size();
        vectSize = com.dimata.harisma.session.leave.SessAlUpload.getEmployee(srcAlUpload);
        if(iCommand==Command.ACTIVATE) { start = ctrlAlUpload.actionList(Command.NEXT, start, vectSize, recordToGet);} else{
          start = ctrlAlUpload.actionList(iCommand, start, vectSize, recordToGet); }
        vDataAlToUpload = com.dimata.harisma.session.leave.SessAlUpload.searchAlData(srcAlUpload, start, recordToGet);
        //vDataAlToUpload = com.dimata.harisma.session.leave.SessAlUpload.getEmployeeAndDataUpload(srcAlUpload, start, recordToGet,null);
    }else{
        start = 0;
    }
%>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Annual Leave Management</title>
<script language="JavaScript">

function cmdNote(hiddenOID){	
	window.open("note_edit.jsp?command="+<%=Command.EDIT%>+"&hidden_AL_id="+hiddenOID, null, "height=300,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}

function cmdUpdateDep(){
	document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.ADD)%>";
	document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="AL_Balancing.jsp"; 
	document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
}
function cmdSave() {
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value = "<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action = "AL_Balancing.jsp";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
}

function cmdProccess() {
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value = "<%=String.valueOf(Command.ACTIVATE)%>";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action = "AL_Balancing.jsp";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
}

function deptChange() {
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action = "AL_Balancing.jsp";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
}

function cmdSearch() {
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value = "<%=String.valueOf(Command.LIST)%>";
    getThn();										
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action = "AL_Balancing.jsp";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
}

function cmdViewList(){
    window.open("al_opname_list.jsp", null, "height=500,width=400, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
}

function setChecked(val){
	dml=document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;
	}
}

function cmdBalancingPrevious(){
    var val1;
    var val2;
    return;
    var i;     
    
    <%
    for(int k=0;k<vDataAlToUpload.size();k++){
    %>
        document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value=20;
        document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_balance_per"+k%>.value=20;        
    <%
    }
    %>    
}

function cmdCekCurr(index,max){
    var val1;
    var val2;
    return;
    
    switch(index){
    <%
        for(int k=0;k<vDataAlToUpload.size();k++){
    %>
            case <%=""+k%>:
            val1 = parseInt(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            val2 = parseInt(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
            if(isNaN(val2)){
                if(trim(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value)+" "!=" "){
                    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value=0;
                }
            }
            if(isNaN(val1)){
                document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value=1;
            }
            <%--if(val2>max){
                document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value=max;
            }--%>
            if(val2>0 && val1>0){
                document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value=1;
            }
           
        
    <% break; 
        }%>
    }
} 



function cmdCekLast(index,max){
    var val1;
    var val2;
    var prev;
    var taken;
    var qty;
    var balance;
    //update by satrya 2012-10-16
    var entitle;
    var al2bTaken;
    
    switch(index){
    <%
        for(int k=0;k<vDataAlToUpload.size();k++){
    %>
            case <%=""+k%>:
            prev = parseFloat(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            taken = parseFloat(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
            ///qty = parseFloat(document.<//%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<//%="data_emp_ALQty_new"+k%>.value);
           //update by satrya 2012-10-16
           entitle = parseFloat(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_earned_curr_per"+k%>.value);
           qty = ( prev + entitle );
           
            al2bTaken = parseFloat(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_al2bTaken_per"+k%>.value);
            balance = (qty ) - taken - al2bTaken;
            //balance = ( prev + qty ) - taken - al2bTaken;
            
            val1 = parseFloat(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            val2 = parseFloat(document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
            
            document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_ALQty_new"+k%>.value=qty.toFixed(2);///format number menjadi 3 angka d belakang koma   
            document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%="data_emp_balance_per"+k%>.value=balance.toFixed(2);///format number menjadi 3 angka d belakang koma
            
            
            
            
            break ;
        
    <% } %>
    } 
}



//Trim string
function trim(inputString) {
    if (typeof inputString != "string") return inputString;
    return inputString
    //clear leading spaces and empty lines
    .replace(/^(\s|\n|\r)*((.|\n|\r)*?)(\s|\n|\r)*$/g,"$2")

    //take consecutive spaces down to one
    .replace(/(\s(?!(\n|\r))(?=\s))+/g,"")

    //take consecutive lines breaks down to one
    .replace(/(\n|\r)+/g,"\n\r")

    //remove spacing at the beginning of a line
    .replace(/(\n|\r)\s/g,"$1")

    //remove spacing at the end of a line
    .replace(/\s(\n|\r)/g,"$1");
}


//-------------------------- for Calendar -------------------------
    function getThn(){
            var date1 = ""+document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>_mn.value=bln;
            document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>_dy.value=hri;
            document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>_yr.value=thn;
    }


    function hideObjectForDate(){
    }

    function showObjectForDate(){
    } 
//-------------- script control line -------------------
        function cmdListFirst(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="AL_Balancing.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="AL_Balancing.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="AL_Balancing.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="AL_Balancing.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
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
<% if (vDataAlToUpload.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST) ||(iCommand==Command.ACTIVATE) )) { 
           for(int idx=0; idx<vDataAlToUpload.size();idx++){
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


<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" -->    
</head> 
<style type="text/css">
input[readonly] {
    color: graytext;
    background: buttonface;
}
</style>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

<!-- Untuk Calendar-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    
    <tr><td id="ds_calclass">
    </td></tr>
</table>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calendar-->

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
                  &gt; Leave Balancing &gt; Annual Leave<!-- #EndEditable --> 
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
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% if (privAdd) { %>
                                    <form name="<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>" method="post" action="">
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
                                                  annual leave to database<br>                                               
                                                  <hr>
                                                </td>
                                              </tr>                                              

                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_NAME]%>"  value="<%=String.valueOf(srcAlUpload.getEmployeeName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_PAYROLL]%>"  value="<%=String.valueOf(srcAlUpload.getEmployeePayroll())%>" class="elemenForm">
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
                                                <%= ControlCombo.draw(FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_CAT],"formElemen",null,String.valueOf(srcAlUpload.getEmployeeCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                  //hidden by satrya 2013-05-27
                                                        //DepartmentIDnNameList keyList= PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                        //Vector dept_value = keyList.getDepIDs();
                                                        //Vector dept_key = keyList.getDepNames();
                                                  
                                                        /*Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");
                                                       
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
                                                  <%//= ControlCombo.draw(FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_DEPT],"formElemen",null,String.valueOf(srcAlUpload.getEmployeeDepartement()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %> 
                                                <%=ControlCombo.draw(FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_DEPT], "elementForm", null, selectValueDepartment, dept_value, dept_key, " onChange=\"javascript:cmdUpdateDep()\"")%>  
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
                                                <%= ControlCombo.draw(FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_SEC],"formElemen",null,String.valueOf(srcAlUpload.getEmployeeSection()), sec_value, sec_key, "") %> </td>
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
                                                <%= ControlCombo.draw(FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_POS],"formElemen",null,String.valueOf(srcAlUpload.getEmployeePosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      <tr> 
                                          	<td width="13%">Opname Date</td>
                                          	<td width="2%">:</td>
                                          	<td width="85%">
                                                    <input onClick="ds_sh(this);" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>" readonly="readonly" style="cursor: text" value="<%=Formater.formatDate((srcAlUpload.getOpnameDate() == null? new Date() : srcAlUpload.getOpnameDate()), "yyyy-MM-dd")%>"/>
                                                    <input type="hidden" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>_mn">
                                                    <input type="hidden" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>_dy">
                                                    <input type="hidden" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE]%>_yr">
                                                    
                                                    <a href="javascript:cmdViewList()" class="buttonlink"><img src="../../images/icon/folderopen.gif" border="0" alt="null"></a>
                                                    <script language="JavaScript" type="text/JavaScript">getThn();</script>
                                                     
                                                 
                                                </td>
                                              </tr>
                                              <input type="hidden" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_DATA_STATUS]%>" value="-1">
                                              <%
                                                                String chekr = "";
                                                                String chekr1 = "";
                                                                String chekr2 = "";
                                                                if (srcAlUpload.getResignStatus() ==  0 ){
                                                                   chekr = " checked ";
                                                                } else {
                                                                   chekr = ""; 
                                                                }

                                                                 if (srcAlUpload.getResignStatus() == 1 ){
                                                                   chekr1 = " checked ";
                                                                } else {
                                                                   chekr1 = ""; 
                                                                }

                                                                if (srcAlUpload.getResignStatus() == 2 ){
                                                                   chekr2 = " checked ";
                                                                } else {
                                                                   chekr2 = ""; 
                                                                }
                                                                %>
                                              <tr> 
                                          	<td width="13%">Resign status</td>
                                          	<td width="2%">:</td>
                                          	<td width="85%">
                                                    <input type="radio" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_RESIGN_STATUS]%>" value="0" <%=chekr%>>
                                                            No
                                                            <input type="radio" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_RESIGN_STATUS]%>" value="1" <%=chekr1%>>
                                                            Yes
                                                            <input type="radio" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_RESIGN_STATUS]%>" value="2" <%=chekr2%>>
                                                            All 
                                                </td>
                                              </tr>
                                              <%/*
                                              <tr> 
                                                <td width="11%">Status</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector vStatus_Value = new Vector(1,1);
                                                        Vector vStatus_Key = new Vector(1,1);
                                                        vStatus_Key.add("all status...");
                                                        vStatus_Key.add("Process");
                                                        vStatus_Key.add("Not Process");
                                                        vStatus_Value.add("-1");
                                                        vStatus_Value.add("0");
                                                        vStatus_Value.add("1");
                                                    ControlCombo.draw(FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_DATA_STATUS],"formElemen",null,String.valueOf(srcAlUpload.getDataStatus()), vStatus_Value, vStatus_Key, "")  </td>
                                              </tr>
                                              */%>
                                              
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
                                                        <p><%=systemMessage%></p>
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
                                                        <p><%=systemMessage%></p>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                          <% if (vDataAlToUpload.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST) ||(iCommand==Command.ACTIVATE) )) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                               <% 
                                               /*<tr>
                                                <td align="right" valign="top">
                                                    [<a onclick="javascript:setChecked(1)"><b>Check All</b></a>]
                                                    |[<a onclick="javascript:setChecked(0)"><b>Uncheck All</b></a>]
                                                    <img src="<%=imagesroot*/%><%/*arrow_ltr.png">
                                                </td>
                                              </tr>*/
                                              %>
                                              <tr>
                                                <td>
                                                    <%=drawList(vDataAlToUpload, srcAlUpload.getOpnameDate(), start)%>
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
                                                          <!-- <a href="javascript:cmdSave()">Save</a>  || -->
                                                            <%
                                                             if( true/* statusManager */)  {
                                                            %>                                                       
                                                              <a href="javascript:cmdProccess()">Proccess</a>
                                                            <% 
                                                            }
                                                            %>
                                                      </td>
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
