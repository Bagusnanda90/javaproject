
<%-- 
    Document   : leave_ll_closing
    Created on : Feb 2, 2010, 9:11:56 AM
    Author     : Tu Roy
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.session.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_LL_CLOSING); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
        long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!

static final int SEARCH_ALL             = 1;
static final int SEARCH_BY_PARAMETER    = 2;

public String drawList(Vector result_parameter,Vector result_all,int intervals,int intervals_2,I_Leave leaveConfig)
{
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	
        ctrlist.addHeader("<center>No</center>","2%");	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","5%");	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EMPLOYEE)+"</center>","15%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.DEPARTMENT)+"</center>","10%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.COMMENCING_DATE)+"</center>","5%");
       
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.ENTITLE) +" Date</center>","5%");
     if(leaveConfig==null || leaveConfig.getLLShowEntile2()){
        ctrlist.addHeader("<center>Exp Ent 1</center>","5%");
        ctrlist.addHeader("<center>Exp Ent 2</center>","5%");
     }else{
        ctrlist.addHeader("<center>Exp Ent</center>","5%");
     }
        ctrlist.addHeader("<center>Prev. Period</center>","5%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.ENTITLE)+"</center>","5%");
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","5%");	
        ctrlist.addHeader("<center>Expired</center>","5%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.BALANCE)+"</center>","5%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EXTEND)+"</center>","12%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.CLOSING)+"</center>","5%");	
        ctrlist.addHeader("<center>Status</center>","5%");	
        
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
        int no = 1 ; 
        
        /*I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }*/
        
        /*Vector vectValExtend = new Vector();
        Vector vectListExtend = new Vector();*/
         
        /*vectValExtend.add(""+leaveConfig.EXTEND_12_MONTH);
        vectListExtend.add("12 MONTH");
        vectValExtend.add(""+leaveConfig.EXTEND_6_MONTH);
        vectListExtend.add("6 MONTH");
        vectValExtend.add(""+leaveConfig.EXTEND_3_MONTH);
        vectListExtend.add("3 MONTH");
        vectValExtend.add(""+leaveConfig.EXTEND_2_MONTH);
        vectListExtend.add("2 MONTH");
        vectValExtend.add(""+leaveConfig.EXTEND_1_MONTH);
        vectListExtend.add("1 MONTH");*/
        //update by satrya 2013-10-01
        Vector vectValExtend = leaveConfig!=null?leaveConfig.getAlValExtend():new Vector();
        Vector vectListExtend = leaveConfig!=null?leaveConfig.getAlKeyExtend():new Vector(); 
        
        int max_size = result_parameter.size();
        
        int max_iterasi = result_parameter.size() -1 ;
        
        int max_count = 0;
        
        int i = 0 ;
        
	for (int index=0; index < result_parameter.size(); index++) 
	{
		LeaveLlClosingList leaveLlClosingList = new LeaveLlClosingList();
                
                leaveLlClosingList = (LeaveLlClosingList)result_parameter.get(index);
                
                float sum_exp = SessLeaveClosing.getSumExpired(leaveLlClosingList.getLlStockManagementId());
                
                float var_residu = leaveLlClosingList.getPrevBalance() + leaveLlClosingList.getLlQty() - leaveLlClosingList.getQtyUsed() - sum_exp;
                
                int statusLongLeave = SessLeaveClosing.statusDataLLStockManagement(leaveLlClosingList.getLlStockManagementId(),leaveLlClosingList.getEmpId(),leaveLlClosingList.getCommancingDate(), intervals, leaveLlClosingList.getEntitledDate(),intervals_2);
                
                int countData = SessLeaveClosing.sumLLStock(leaveLlClosingList.getEmpId());    //Jumlah data yang aktif
                
                //if(( statusLongLeave == SessLeaveClosing.LL_AKTIF_EXP_1 || statusLongLeave == SessLeaveClosing.LL_AKTIF_EXP_2 ) && var_residu <=0 ){
                    
                    //kondisi ini tidak akan ditampilkan karena stocknya sudah habis
                    
                //}else if(countData == 1 && statusLongLeave == SessLeaveClosing.LL_AKTIF){
                Vector rowx = new Vector();
                float sum_expired = SessLeaveClosing.getSumExpired(leaveLlClosingList.getLlStockManagementId());
                if(countData == 1 && statusLongLeave == SessLeaveClosing.LL_AKTIF){
                    
                    //kondisi ini tidak akan ditampilkan karena stocknya masih aktif
                     rowx.add(""+no);
                     rowx.add(leaveLlClosingList.getEmpNum());
                     rowx.add(leaveLlClosingList.getFullName());
                     rowx.add(leaveLlClosingList.getDepartment());
                     String commercing = leaveLlClosingList.getCommancingDate()!=null?Formater.formatDate(leaveLlClosingList.getCommancingDate(), "yyyy-MM-dd") :"-";
                     rowx.add(commercing);
                     String entitleDate1 = leaveLlClosingList.getEntitledDate()!=null?Formater.formatDate(leaveLlClosingList.getEntitledDate(), "yyyy-MM-dd") :"-"; 
                     String entitleDate2 = leaveLlClosingList.getEntitleDate2()!=null?Formater.formatDate(leaveLlClosingList.getEntitleDate2(), "yyyy-MM-dd") :"-"; 
                     if(leaveConfig==null || leaveConfig.getLLShowEntile2()){
                        rowx.add(entitleDate1 + entitleDate2);
                     }else{
                         rowx.add(entitleDate1);
                     }
                     String expiredDate1 = leaveLlClosingList.getExpiredDate()!=null?Formater.formatDate(leaveLlClosingList.getExpiredDate(), "yyyy-MM-dd") :"-"; 
                     String expiredDate2 = leaveLlClosingList.getExpiredDate2()!=null?Formater.formatDate(leaveLlClosingList.getExpiredDate2(), "yyyy-MM-dd") :"-"; 
                     if(leaveConfig==null || leaveConfig.getLLShowEntile2()){
                        rowx.add(expiredDate1);
                         rowx.add(expiredDate2);
                     }else{
                         rowx.add(expiredDate1);
                     }
                     rowx.add(""+leaveLlClosingList.getPrevBalance());
                     if(leaveConfig==null || leaveConfig.getLLShowEntile2()){
                        rowx.add(""+leaveLlClosingList.getEntitled() + leaveLlClosingList.getEntitled2());
                     }else{
                         rowx.add(""+leaveLlClosingList.getEntitled());
                     }
                     rowx.add(""+leaveLlClosingList.getQtyUsed());
                     rowx.add(""+sum_expired);
                     rowx.add(""+leaveLlClosingList.getQtyResidue()); 
                     rowx.add(""+0);
                     rowx.add("");
                     rowx.add("<center>Aktiv</center>");
                      no++;
                     i++;
                     lstData.add(rowx);
                }else{
                    
                max_count = max_count + 1;    
                
                int diff = SessLeaveClosing.DATEDIFF(leaveLlClosingList.getExpiredDate(), leaveLlClosingList.getExpiredDate2());
                
                float extend_val = 0;
                
                if(diff == 0){
                    
                    extend_val = leaveLlClosingList.getPrevBalance()+leaveLlClosingList.getEntitled()-leaveLlClosingList.getQtyUsed();
                    
                }else if(diff > 0){
                    
                    extend_val = leaveLlClosingList.getPrevBalance()+leaveLlClosingList.getEntitled2()-leaveLlClosingList.getQtyUsed();
                    
                }else if(diff < 0){
                    
                    extend_val = leaveLlClosingList.getPrevBalance()+leaveLlClosingList.getEntitled()-leaveLlClosingList.getQtyUsed();
                    
                }
                
                long stock_id_first = 0;
                
                stock_id_first = SessLeaveClosing.getLLStock_Id_First(leaveLlClosingList.getEmpId());
                
                String drawlist = ControlCombo.draw("interval"+i, null, null, vectValExtend, vectListExtend,"");	
                
                
                
                rowx.add("<input type=\"hidden\" name=\"countMAX\" value=\""+no+"\">"+no);
                
                String comencingDate = Formater.formatDate(leaveLlClosingList.getCommancingDate(),"yyyy-MM-dd");
                String entitleDate = Formater.formatDate(leaveLlClosingList.getEntitledDate(),"yyyy-MM-dd");
                
                if(countData > 1){
                    
                    if(leaveLlClosingList.getLlStockManagementId()==stock_id_first){ //jika termasuk stock list pertama
                        
                        if( index == max_iterasi){
                            
                            rowx.add("<input type=\"hidden\" name=\"max_data\" value=\""+max_count+"\">"+
                                "<input type=\"hidden\" name=\"ll_stock_id"+i+"\" value=\""+leaveLlClosingList.getLlStockManagementId()+"\">"+
                                leaveLlClosingList.getEmpNum());
                            
                        }else{
                            
                            rowx.add("<input type=\"hidden\" name=\"ll_stock_id"+i+"\" value=\""+leaveLlClosingList.getLlStockManagementId()+"\">"+
                                leaveLlClosingList.getEmpNum());
                            
                        }
                        
                        rowx.add("<input type=\"hidden\" name=\"employee_id"+i+"\" value=\""+leaveLlClosingList.getEmpId()+"\">"+leaveLlClosingList.getFullName());	
                        rowx.add(""+leaveLlClosingList.getDepartment());	
                        rowx.add("<input type=\"hidden\" name=\"commencing_date"+i+"\" value=\""+comencingDate+"\">"+comencingDate);
                        
                    }else{
                        
                        if( index == max_iterasi){
                            
                            rowx.add("<input type=\"hidden\" name=\"max_data\" value=\""+max_count+"\">"+
                                "<input type=\"hidden\" name=\"ll_stock_id"+i+"\" value=\""+leaveLlClosingList.getLlStockManagementId()+"\">"+
                                leaveLlClosingList.getEmpNum());
                            
                        }else{
                            
                            rowx.add("<input type=\"hidden\" name=\"sum_data\" value=\""+1+"\">"+
                                "<input type=\"hidden\" name=\"ll_stock_id"+i+"\" value=\""+leaveLlClosingList.getLlStockManagementId()+"\">"+
                                leaveLlClosingList.getEmpNum());
                            
                        }
                        
                        rowx.add("<input type=\"hidden\" name=\"employee_id"+i+"\" value=\""+leaveLlClosingList.getEmpId()+"\">");	
                        rowx.add("");	
                        rowx.add("<input type=\"hidden\" name=\"commencing_date"+i+"\" value=\""+comencingDate+"\">");
                    }
                    
                }else{
                    
                    if( index == max_iterasi){
                            
                            rowx.add("<input type=\"hidden\" name=\"max_data\" value=\""+max_count+"\">"+
                                "<input type=\"hidden\" name=\"ll_stock_id"+i+"\" value=\""+leaveLlClosingList.getLlStockManagementId()+"\">"+
                                leaveLlClosingList.getEmpNum());
                            
                    }else{
                            
                            rowx.add("<input type=\"hidden\" name=\"sum_data\" value=\""+1+"\">"+
                                "<input type=\"hidden\" name=\"ll_stock_id"+i+"\" value=\""+leaveLlClosingList.getLlStockManagementId()+"\">"+
                                leaveLlClosingList.getEmpNum());
                            
                    }
                    
                    rowx.add("<input type=\"hidden\" name=\"employee_id"+i+"\" value=\""+leaveLlClosingList.getEmpId()+"\">"+leaveLlClosingList.getFullName());	
                    rowx.add(""+leaveLlClosingList.getDepartment());	
                    rowx.add("<input type=\"hidden\" name=\"commencing_date"+i+"\" value=\""+comencingDate+"\">"+comencingDate);
                }
                
                //update by satrya 2013-10-01
                /*rowx.add("<input type=\"hidden\" name=\"exp_date_1"+i+"\" value=\""+leaveLlClosingList.getExpiredDate()+"\">"+
                         "<input type=\"hidden\" name=\"exp_date_2"+i+"\" value=\""+leaveLlClosingList.getExpiredDate2()+"\">"+
                         "<input type=\"hidden\" name=\"entitle_date_1"+i+"\" value=\""+leaveLlClosingList.getEntitled()+"\">"+
                         "<input type=\"hidden\" name=\"entitle_date_2"+i+"\" value=\""+leaveLlClosingList.getEntitled2()+"\">"+
                         "<input type=\"hidden\" name=\"ll_entitle_date"+i+"\" value=\""+leaveLlClosingList.getEntitledDate()+"\">"+
                         "<input type=\"hidden\" name=\"ll_entitle_date_2"+i+"\" value=\""+leaveLlClosingList.getEntitleDate2()+"\">"+
                         "<input type=\"hidden\" name=\"qty_used"+i+"\" value=\""+leaveLlClosingList.getQtyUsed()+"\">"+
                         "<input type=\"hidden\" name=\"ll_prev_balance"+i+"\" value=\""+leaveLlClosingList.getPrevBalance()+"\">"+
                         "<input type=\"hidden\" name=\"qty"+i+"\" value=\""+leaveLlClosingList.getLlQty()+"\">"
                         +entitleDate);*/ 
              if(leaveConfig!=null && leaveConfig.getLLShowEntile2()==false){
                  rowx.add("<input type=\"hidden\" name=\"exp_date_1"+i+"\" value=\""+leaveLlClosingList.getExpiredDate()+"\">"+
                         //"<input type=\"hidden\" name=\"exp_date_2"+i+"\" value=\""+leaveLlClosingList.getExpiredDate2()+"\">"+
                         "<input type=\"hidden\" name=\"entitle_date_1"+i+"\" value=\""+leaveLlClosingList.getEntitled()+"\">"+
                        // "<input type=\"hidden\" name=\"entitle_date_2"+i+"\" value=\""+leaveLlClosingList.getEntitled2()+"\">"+
                         "<input type=\"hidden\" name=\"ll_entitle_date"+i+"\" value=\""+leaveLlClosingList.getEntitledDate()+"\">"+
                         //"<input type=\"hidden\" name=\"ll_entitle_date_2"+i+"\" value=\""+leaveLlClosingList.getEntitleDate2()+"\">"+
                         "<input type=\"hidden\" name=\"qty_used"+i+"\" value=\""+leaveLlClosingList.getQtyUsed()+"\">"+
                         "<input type=\"hidden\" name=\"ll_prev_balance"+i+"\" value=\""+leaveLlClosingList.getPrevBalance()+"\">"+
                         "<input type=\"hidden\" name=\"qty"+i+"\" value=\""+leaveLlClosingList.getLlQty()+"\">"
                         +entitleDate);
              }else{
                rowx.add("<input type=\"hidden\" name=\"exp_date_1"+i+"\" value=\""+leaveLlClosingList.getExpiredDate()+"\">"+
                         "<input type=\"hidden\" name=\"exp_date_2"+i+"\" value=\""+leaveLlClosingList.getExpiredDate2()+"\">"+
                         "<input type=\"hidden\" name=\"entitle_date_1"+i+"\" value=\""+leaveLlClosingList.getEntitled()+"\">"+
                         "<input type=\"hidden\" name=\"entitle_date_2"+i+"\" value=\""+leaveLlClosingList.getEntitled2()+"\">"+
                         "<input type=\"hidden\" name=\"ll_entitle_date"+i+"\" value=\""+leaveLlClosingList.getEntitledDate()+"\">"+
                         "<input type=\"hidden\" name=\"ll_entitle_date_2"+i+"\" value=\""+leaveLlClosingList.getEntitleDate2()+"\">"+
                         "<input type=\"hidden\" name=\"qty_used"+i+"\" value=\""+leaveLlClosingList.getQtyUsed()+"\">"+
                         "<input type=\"hidden\" name=\"ll_prev_balance"+i+"\" value=\""+leaveLlClosingList.getPrevBalance()+"\">"+
                         "<input type=\"hidden\" name=\"qty"+i+"\" value=\""+leaveLlClosingList.getLlQty()+"\">"
                         +entitleDate);
               }
                
                
                
                String exp_dt1 = "-";
                String exp_dt2 = "-";
                
                if(leaveLlClosingList.getExpiredDate() != null){
                    
                    try{
                        exp_dt1 = Formater.formatDate(leaveLlClosingList.getExpiredDate(),"yyyy-MM-dd");
                    }catch(Exception e){
                        exp_dt1 = "-";
                    }
                    
                }
                
                if(leaveLlClosingList.getExpiredDate2() != null){
                    
                    try{
                        exp_dt2 = Formater.formatDate(leaveLlClosingList.getExpiredDate2(),"yyyy-MM-dd");
                    }catch(Exception e){
                        exp_dt2 = "-";
                    }
                    
                }
                
                rowx.add(""+exp_dt1);
             //update by satrya 2013-10-01
             if(leaveConfig!=null && leaveConfig.getLLShowEntile2()==false){
                 //tidak di tampilkan
             }else{
                    rowx.add(""+exp_dt2);                
             }   
                rowx.add(""+leaveLlClosingList.getPrevBalance());
              //update by satrya 2013-10-01
             if(leaveConfig!=null && leaveConfig.getLLShowEntile2()==false){
                 rowx.add(""+leaveLlClosingList.getEntitled());
             }else{
                    rowx.add(""+leaveLlClosingList.getEntitled()+"+"+leaveLlClosingList.getEntitled2());      
             }  
                
                rowx.add(""+leaveLlClosingList.getQtyUsed());
                rowx.add(""+sum_expired);
                float residu = leaveLlClosingList.getPrevBalance() + leaveLlClosingList.getLlQty() - leaveLlClosingList.getQtyUsed() - sum_expired;
                rowx.add(""+residu);
                
                boolean statusApplicationNotClose = false;
                
                statusApplicationNotClose = SessLeaveClosing.getApplicationLLNotClose(leaveLlClosingList.getLlStockManagementId());                
                
                if(statusApplicationNotClose == true && statusLongLeave == SessLeaveClosing.LL_CLOSE){
                    
                    rowx.add("");
                    rowx.add("");
                    rowx.add("<center>Please close the application</center>");
                    
                }else{
                   
                    if(countData > 1){
                        
                        if(statusLongLeave == SessLeaveClosing.LL_CLOSE){
                        
                            rowx.add("");
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_CLOSE+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+extend_val+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Aktif</center>");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_CLOSE_AKTIF_EXIST){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_CLOSE_AKTIF_EXIST+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_INVALID){
                            
                            rowx.add("");
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_INVALID+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Invalid</center>");
                       
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF_ENTITLE_2){
                            
                            rowx.add("");
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF_ENTITLE_2+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Entitle 2</center>");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF_EXP_1){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF_EXP_1+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Expired 1</center>");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF_EXP_2){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF_EXP_2+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Expired 2</center>");
                        }
                                                
                    }else{
                        
                        if(statusLongLeave == SessLeaveClosing.LL_CLOSE){
                        
                            rowx.add("");
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_CLOSE+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF){
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+extend_val+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Aktif</center>");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_CLOSE_AKTIF_EXIST){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_CLOSE_AKTIF_EXIST+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_INVALID){
                            
                            rowx.add("");
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_INVALID+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Invalid</center>");
                       
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF_ENTITLE_2){
                            
                            rowx.add("");
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF_ENTITLE_2+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Entitle 2</center>");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF_EXP_1){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF_EXP_1+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Expired 1</center>");
                            
                        }else if(statusLongLeave == SessLeaveClosing.LL_AKTIF_EXP_2){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"3\">"+" "+drawlist);
                            rowx.add("<input type=\"hidden\" name=\"status_data"+i+"\" value=\""+SessLeaveClosing.LL_AKTIF_EXP_2+"\">"+"<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>Expired 2</center>");
                            
                        }
                    }
                }
                no++;
                i++;
		lstData.add(rowx);
                }
	}
        
        return ctrlist.draw();
}
%>


<%

I_Leave leaveConfig = null;

try {
    leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
} catch (Exception e) {
    System.out.println("Exception ::::: " + e.getMessage());
}

int configuration = leaveConfig.getIntervalLLinMonths()[LeaveConfigHR.iINTERVAL_LL_HARDROCK_I];     // range mendapatkan LL (dalam bulan)
int configuration_2 = leaveConfig.getIntervalLLinMonths()[LeaveConfigHR.iINTERVAL_LL_HARDROCK_II];  // range mendapatkan LL (dalam bulan)

privAdd=false;

int iCommand = FRMQueryString.requestCommand(request);
SrcLeaveAppAlClosing objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();
int Type = FRMQueryString.requestInt(request, "Type");

if(iCommand==Command.ACTIVATE){

    String[] jumlah_data = null;
    Vector result_close = new Vector();
    String[] countMAX = request.getParameterValues("countMAX");
    int size = countMAX.length;
    jumlah_data = request.getParameterValues("max_data");
    int count = FRMQueryString.requestInt(request, "max_data");
    
    for(int i = 0 ; i < size ; i++){
        
        int ix = FRMQueryString.requestInt(request, "Close"+i);
        
        if(ix == 1){   // jika data diclose
           
            String tmp_llStockId = "";
            String tmp_employee_id = "";
            String tmp_commencing_date = "";
            String tmp_entitle_date = "";
            String tmp_entitle_date_2 = "";
            String tmp_status_data = "";
            String tmp_interval_date = "";
            String tmp_extended_value = "";
            String tmp_entitle_1 = "";
            String tmp_entitle_2 = "";
            String tmp_exp_date_1 = "";
            String tmp_exp_date_2 = "";
            String tmp_prev_bal = "";
            String tmp_qty_used = "";
            String tmp_qty = "";
            
            tmp_llStockId = FRMQueryString.requestString(request, "ll_stock_id"+i); 
            tmp_employee_id = FRMQueryString.requestString(request, "employee_id"+i); 
            tmp_commencing_date = FRMQueryString.requestString(request, "commencing_date"+i); 
            tmp_entitle_date = FRMQueryString.requestString(request, "ll_entitle_date"+i);
            tmp_entitle_date_2 = FRMQueryString.requestString(request, "ll_entitle_date_2"+i);            
            tmp_status_data = FRMQueryString.requestString(request, "status_data"+i);
            tmp_interval_date = FRMQueryString.requestString(request, "interval"+i);
            tmp_extended_value = FRMQueryString.requestString(request, "data_extend"+i);            
            tmp_entitle_1  = FRMQueryString.requestString(request, "entitle_date_1"+i);
            tmp_entitle_2  = FRMQueryString.requestString(request, "entitle_date_2"+i);
            tmp_exp_date_1 = FRMQueryString.requestString(request, "exp_date_1"+i);
            tmp_exp_date_2 = FRMQueryString.requestString(request, "exp_date_2"+i);
            tmp_prev_bal   = FRMQueryString.requestString(request, "ll_prev_balance"+i);
            tmp_qty_used   = FRMQueryString.requestString(request, "qty_used"+i);
            tmp_qty   = FRMQueryString.requestString(request, "qty"+i);
            
            long  llStockId = 0;
            long  employee_id = 0;
            Date  commencing_date = new Date();
            Date  entitle_date = new Date();
            Date  entitle_date_2 = new Date();
            int   status_data = 0;
            int   interval_date = 0;
            int   extended_value = 0;
            int   entitle_1 = 0;
            int   entitle_2 = 0;
            Date  exp_date_1 = new Date();
            Date  exp_date_2 = new Date();
            int   prev_bal = 0;
            int   qty_used = 0;
            int   qty = 0;
            
            try{
                qty = Integer.parseInt(tmp_qty);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                llStockId = Long.parseLong(tmp_llStockId);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                employee_id = Long.parseLong(tmp_employee_id);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                commencing_date = Formater.formatDate(tmp_commencing_date,"yyyy-MM-dd");
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                entitle_date = Formater.formatDate(tmp_entitle_date,"yyyy-MM-dd");
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                status_data = Integer.parseInt(tmp_status_data);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                interval_date = Integer.parseInt(tmp_interval_date);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                extended_value = Integer.parseInt(tmp_extended_value);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                entitle_1 = Integer.parseInt(tmp_entitle_1);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                entitle_2 = Integer.parseInt(tmp_entitle_2);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                exp_date_1 = Formater.formatDate(tmp_exp_date_1,"yyyy-MM-dd");
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                exp_date_2 = Formater.formatDate(tmp_exp_date_2,"yyyy-MM-dd");
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                entitle_date_2 = Formater.formatDate(tmp_entitle_date_2,"yyyy-MM-dd");
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                prev_bal = Integer.parseInt(tmp_prev_bal);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                qty_used = Integer.parseInt(tmp_qty_used);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            LlClosingSelected llClosingSelected = new LlClosingSelected();
            llClosingSelected.setLlStockId(llStockId);
            llClosingSelected.setEmployeeId(employee_id);
            llClosingSelected.setEntitleDate(entitle_date);
            llClosingSelected.setEntitleDate2(entitle_date_2);
            llClosingSelected.setCommencingDate(commencing_date);
            llClosingSelected.setStatusData(status_data);
            llClosingSelected.setIterval_date(interval_date);
            llClosingSelected.setExtended_value(extended_value);
            llClosingSelected.setEntitle_1(entitle_1);
            llClosingSelected.setEntitle_2(entitle_2);
            llClosingSelected.setExp_date_1(exp_date_1);
            llClosingSelected.setExp_date_2(exp_date_2);
            llClosingSelected.setPrev_balance(prev_bal);
            llClosingSelected.setQty_taken(qty_used);
            llClosingSelected.setQty(qty);
            
            result_close.add(llClosingSelected);
        }
    }
    if(result_close != null && result_close.size() > 0){
        System.out.println(":::::::::::::::::::::::: CLOSING LL START ::::::::::::::::::::::::::::");
        SessLeaveClosing.ProcessClosingLL(result_close);
    }
    
}

Vector resultLL_all = new Vector();
Vector resultLL_parameter = new Vector();

FrmSrcLeaveAppAlClosing objFrmSrcLeaveAppAlClosing = new FrmSrcLeaveAppAlClosing(request, objSrcLeaveAppAlClosing);
objFrmSrcLeaveAppAlClosing.requestEntityObject(objSrcLeaveAppAlClosing);

if(iCommand==Command.BACK)
{        
	objFrmSrcLeaveAppAlClosing= new FrmSrcLeaveAppAlClosing(request, objSrcLeaveAppAlClosing);
	try
	{				
		objSrcLeaveAppAlClosing = (SrcLeaveAppAlClosing) session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION);			
		if(objSrcLeaveAppAlClosing == null)
		{
			objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();
		}		
	}
	catch (Exception e)
	{
		objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing(); 
	}
}
if(Type==SEARCH_BY_PARAMETER){
    
    // untuk pencarian 
    resultLL_all = SessLeaveClosing.listLLClosingLLByParameter(objSrcLeaveAppAlClosing);
    
}else if(Type==SEARCH_ALL){    
    
    resultLL_all = SessLeaveClosing.listLLClosingALL();
    
}

boolean isManager = false;
boolean isHrManager = false;

if(positionType == PstPosition.LEVEL_MANAGER)
{
    isManager = true;
	
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
	if(departmentOid == hrdDepartmentOid)
	{	
		isHrManager = true;
	}
}
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Application</title>
<script language="JavaScript">
<!--

function cmdClosing() {
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value ="<%=String.valueOf(Command.ACTIVATE)%>";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action ="leave_ll_closing.jsp";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}    

function cmdSearch(){
        getCommancingDateStart();
        getCommancingDateEnd();
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.Type.value = <%= SEARCH_BY_PARAMETER %>;
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value="<%=String.valueOf(Command.LIST)%>";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action="leave_ll_closing.jsp";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}

function cmdSearchAll(){
        getCommancingDateStart();
        getCommancingDateEnd();
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.Type.value = <%= SEARCH_ALL %>;
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value="<%=String.valueOf(Command.LIST)%>";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action="leave_ll_closing.jsp";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}

function getThn(){
<%
     //out.println(ControlDatePopup.writeDateCaller(FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SUBMISSION_DATE]));
%>
}

function hideObjectForDate(index){
}

function showObjectForDate(){
} 

function getCommancingDateStart(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP,FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_START])%>
}

function getCommancingDateEnd(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP,FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_END])%>
}

function cmdUpdateDep(){
    document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action="leave_ll_closing.jsp";
    document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}

//-------------------------------------------

function fnTrapKD(){
   if (event.keyCode == 13) {
	document.all.aSearch.focus();
	//cmdSearch();
   }
}

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
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" -->  
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">

<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave &gt; Leave Application<!-- #EndEditable --> 
                  </strong></font> </td>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="<%=objFrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>" method="post" action="">
                                      <!--<input type="hidden" name="command" value="">-->
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="Type" value="<%=String.valueOf(Type)%>">     
                                      <table border="0" cellspacing="2" cellpadding="2" width="100%" >
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <input type="text" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_EMP_NUMBER] %>"  value="<%= objSrcLeaveAppAlClosing.getEmpNum() %>" class="elemenForm"  onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <input type="text" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_FULLNAME] %>"  value="<%= objSrcLeaveAppAlClosing.getFullName() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="40">
                                          </td>
                                        </tr>
                                        <tr>
                                            <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%">
                                              <%														
                                            Vector div_value = new Vector(1,1);
                                            Vector div_key = new Vector(1,1);
                                            div_value.add("0");
                                            div_key.add("select ...");                                                              
                                            Vector listDiv = PstDivision.list(0, 0, "", "");                                                          
                                            String selectValueDiv = ""+objSrcLeaveAppAlClosing.getDivisionId();
                                            for (int i = 0; i < listDiv.size(); i++) 
                                            {
                                                    Division div = (Division) listDiv.get(i);
                                                    div_key.add(div.getDivision());
                                                    div_value.add(String.valueOf(div.getOID()));
                                            }															
                                            %>
                                            <%=ControlCombo.draw(objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_DIVISION],"elementForm", null, selectValueDiv, div_value, div_key,"onChange=\"javascript:cmdUpdateDep()\"") %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%															
											Vector department_value = new Vector(1,1);
											Vector department_key = new Vector(1,1);
                                                                                        String where;
                                                                                        
                                                                                        department_key.add("select..");
                                                                                        department_value.add("");
                                                                                        long oidHRD = 0;
                                                                                        
                                                                                        try{
                                                                                            oidHRD = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
                                                                                        }catch(Exception E){
                                                                                            System.out.println("[exception] Sys Prop OID_HRD_DEPARTMENT [not set] "+E.toString());
                                                                                        }
                                                                                        
                                                                                        if(departmentOid==oidHRD){
                                                                                            where="";
                                                                                        }else{
                                                                                            where = " DEPARTMENT_ID = "+departmentOid;
                                                                                        }
											
											Vector listDept = new Vector();
                                                                                        if(objSrcLeaveAppAlClosing.getDivisionId() != 0){
                                                                                            listDept = PstDepartment.list(0, 0, "hr_department.DIVISION_ID="+objSrcLeaveAppAlClosing.getDivisionId(), " DEPARTMENT ");                                                        
                                                                                        }
                                                                                        
											String selectValueDepartment = ""+objSrcLeaveAppAlClosing.getDepartmentId();
											for (int i = 0; i < listDept.size(); i++) 
											{
												Department dept = (Department) listDept.get(i);
												department_key.add(dept.getDepartment());
												department_value.add(String.valueOf(dept.getOID()));
											}														
											%>
                                                                                        
                                             
                                                                                                <%

                                                                                                   /* Vector dept_value = new Vector(1, 1);
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
                                                                                                    String selectValueDepartment = ""+objSrcLeaveAppAlClosing.getDepartmentId(); 
*/
                                                                                                %>
                                            <%=ControlCombo.draw(objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_DEPARTMENT],"elementForm", null, selectValueDepartment, department_value, department_key,"onChange=\"javascript:cmdUpdateDep()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%														
                                                Vector section_value = new Vector(1,1);
                                                Vector section_key = new Vector(1,1);
                                                section_value.add("0");
                                                section_key.add("select ...");  

                                                Vector listSec = new Vector();
                                                if(objSrcLeaveAppAlClosing.getDepartmentId() != 0){
                                                    listSec = PstSection.list(0, 0, "DEPARTMENT_ID="+objSrcLeaveAppAlClosing.getDepartmentId(), " SECTION ");                                                          
                                                }                                                         
                                                String selectValueSections = ""+objSrcLeaveAppAlClosing.getSectionId();
                                                for (int i = 0; i < listSec.size(); i++) 
                                                {
                                                        Section sec = (Section) listSec.get(i);
                                                        section_key.add(sec.getSection());
                                                        section_value.add(String.valueOf(sec.getOID()));
                                                }															
                                                %>
                                            <%=ControlCombo.draw(objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_SECTION],"elementForm", null, selectValueSections, section_value, section_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.CATEGORY) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%		
                                                Vector cat_value = new Vector();
                                                Vector cat_key = new Vector();
                                                Vector vectCat = new Vector();
                                                 cat_value.add(""+0);
                                                    cat_key.add("-selected-");
                                                try{ vectCat = SessLeaveClosing.getEmpCategoryEntitle();} catch(Exception exc){ System.out.println(exc);}
                                                //update by satrya 2012-12-20
                                                String empCat  = "";
                                                String whereClause = "";
                                                try{
                                                    empCat  = PstSystemProperty.getValueByName("OID_EMP_CATEGORY");
                                                    if(empCat!=null && empCat.length()>0){
                                                        whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" IN ("+empCat+")";
                                                    }
                                                }catch(Exception ex){
                                                    System.out.println("Exception System properties OID_EMP_CATEGORY ca'nt ben set"+ex);
                                                }
                                                Vector listCategory = PstEmpCategory.list(0, 500, whereClause, ""); 
                                                
                                                for(int idxCat = 0 ; idxCat < listCategory.size(); idxCat++){
                                                    EmpCategory empCategory = (EmpCategory)listCategory.get(idxCat);
                                                    cat_value.add(""+empCategory.getOID());
                                                    cat_key.add(""+empCategory.getEmpCategory());
                                                }
                                                
                                                String selectValueSection = ""+objSrcLeaveAppAlClosing.getCategoryId();
                                                
					    %>
                                             <%=ControlCombo.draw(objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_CATEGORY],"elementForm", null, selectValueSection, cat_value, cat_key ," onkeydown=\"javascript:fnTrapKD()\"") %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%														
											Vector position_value = new Vector(1,1);
											Vector position_key = new Vector(1,1);
											position_value.add("0");
											position_key.add("select ...");                                                       
											Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
											String selectValuePosition = ""+objSrcLeaveAppAlClosing.getPositionId();
											for (int i = 0; i < listPos.size(); i++) 
											{
												Position pos = (Position) listPos.get(i);
												position_key.add(pos.getPosition());
												position_value.add(String.valueOf(pos.getOID()));
											}														
                                            %>
                                            <%=ControlCombo.draw(objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_POSITION],"elementForm", null, selectValuePosition, position_value, position_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Commancing Date</td>
                                          <td width="2%">:</td>
                                          <td width="85%">                                         
                                          <%=ControlDatePopup.writeDate(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_START],objSrcLeaveAppAlClosing.getEmpCommancingDateStart() == null ? Formater.formatDate("1990-01-01","yyyy-MM-dd") : objSrcLeaveAppAlClosing.getEmpCommancingDateStart(), "getCommancingDateStart()")%> To
                                          <%=ControlDatePopup.writeDate(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_END],objSrcLeaveAppAlClosing.getEmpCommancingDateEnd()== null ? new Date() : objSrcLeaveAppAlClosing.getEmpCommancingDateEnd(), "getCommancingDateEnd()")%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Status</td>
                                          <td width="2%">:</td>
                                          <td width="85%">
                                        <%if(objSrcLeaveAppAlClosing.getStatus()==0){%>
                                            <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="0" class="elemenForm"  checked="checked" onkeydown="javascript:fnTrapKD()">Active
                                          <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="1" class="elemenForm"  onkeydown="javascript:fnTrapKD()">To Be Close
                                          <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="2" class="elemenForm"   onkeydown="javascript:fnTrapKD()">All
                                        <%}else if(objSrcLeaveAppAlClosing.getStatus()==1){%>
                                            <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="0" class="elemenForm"  onkeydown="javascript:fnTrapKD()">Active
                                          <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="1" class="elemenForm"  checked="checked" onkeydown="javascript:fnTrapKD()">To Be Close
                                          <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="2" class="elemenForm"   onkeydown="javascript:fnTrapKD()">All
                                        <%}else{%>
                                            <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="0" class="elemenForm"   onkeydown="javascript:fnTrapKD()">Active
                                          <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="1" class="elemenForm"   onkeydown="javascript:fnTrapKD()">To Be Close
                                          <input type="radio" name="<%=objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_STATUS] %>"  value="2" class="elemenForm"   checked="checked" onkeydown="javascript:fnTrapKD()">All
                                        <%}%>
                                          
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%"> 
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td width="25px" ><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search closing employee" ></a></td>
                                                <td width="5px"></td>
                                                <td width="30px" class="command" nowrap><a href="javascript:cmdSearch()">Search</a></td>  
                                                <td width="15px"></td>
                                                <td width="30px" ><a href="javascript:cmdSearchAll()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search closing all employee" ></a></td>
                                                <td width="10px"></td>
                                                <td width="100px" class="command" nowrap><a href="javascript:cmdSearchAll()">Search All</a></td>   
                                                <td width="5px"></td>
                                                <td width="25px" ></td>
                                                <td width="15px"></td>
                                                <td width="100px" class="command" nowrap></td>                                                                                                     
                                              </tr>                                              
                                            </table>
                                          </td>
                                        </tr>
                                         <tr>
                                        <td colspan="3">
                                        <% 
                                           if(resultLL_all!=null && resultLL_all.size()>0){
                                               
                                               out.println(drawList(resultLL_all,resultLL_parameter,configuration,configuration_2,leaveConfig));
                                              
                                         %>
                                        </td>
                                        </tr> 
                                         <tr>
                                        <td colspan="3">
                                        &nbsp;
                                        </td>
                                        </tr> 
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%"> 
                                            <table border="0" cellpadding="0" cellspacing="0" width="120">
                                              <tr> 
                                                <td width="25px" ><a href="javascript:cmdClosing()" onMouseOut="MM_swapImgRestore()" onMouseOut="MM_swapImgRestore()" ><img name="Image300" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Close Period" ></a></td>
                                                <td width="5px"></td>
                                                <td width="80px" class="command" nowrap><a href="javascript:cmdClosing()">Close LL</a></td>                                                                                                                                                      
                                                                                                                                                    
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
</body>
</html>