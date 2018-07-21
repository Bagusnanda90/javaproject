
<%-- 
    Document   : DP_Balancing
    Created on : Dec 18, 2009, 9:16:19 AM
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
<%@ page import = "com.dimata.harisma.form.search.FrmSrcDPUpload" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcDPUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.DPUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstDPUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessDPUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_OPNAME_DP); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
int STS_SAVE_ONLY           = 0;
int STS_SAVE_AND_PROCESS    = 1;

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    
%>
<%!
    //result Draw List DP
    public String drawList(Vector objEmpDPData, Date opnameDate,int start){
        
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("<center>No</center>","5%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","6%");
        ctrlist.addHeader("<center>Name</center>","12%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.COMMENCING_DATE)+"</center>","10%");
        ctrlist.addHeader("<center>Owning Date</center>","10%");
        ctrlist.addHeader("<center>Opname Date</center>","10%");
        ctrlist.addHeader("<center>Acquisition Date</center>","10%");
         //ctrlist.addHeader("<center>Acquisition Date</center>","10%");
        ctrlist.addHeader("<center>DP (System)</center>","6%");
        ctrlist.addHeader("<center>DP </center>","8%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","5%");
        ctrlist.addHeader("<center>New Entry</center>","5%");
        ctrlist.addHeader("<center>Note</center>","4%");
        ctrlist.addHeader("<center>Status</center>","8%");
        ctrlist.addHeader("Process/<br>Reprocess","7%");
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();        
        
        int no = start;
        long tmpEmp = 0;
        
        try{
        for (int i = 0; i < objEmpDPData.size(); i++){  // looping sebanyak list pada object employee
           
            boolean sameEmp = true;          
            
            listDpUpload objListDpUpload = new listDpUpload();
            
            objListDpUpload = (listDpUpload)objEmpDPData.get(i); 
                          
            if(tmpEmp == 0){
                sameEmp = false;
            }else{
                
                if(tmpEmp == objListDpUpload.getEmployee_id()){
                    sameEmp = true;
                }else{
                    
                    sameEmp = false;
                    
                }
                
            }
            
            tmpEmp = objListDpUpload.getEmployee_id();
            
            DPUpload objDpUpload = new DPUpload();        
               
            DpStockManagement objDpStockManagement = new DpStockManagement();
            
            if(objListDpUpload.getDp_upload_id() != 0 ){                
                try{
                    objDpUpload = PstDPUpload.fetchExc(objListDpUpload.getDp_upload_id());
                }catch(Exception e){
                    System.out.println("EXCEPTION "+e.toString());
                }    
            }
            
            if(objListDpUpload.getDp_stock_id() != 0 ){
                try{
                    objDpStockManagement = PstDpStockManagement.fetchExc(objListDpUpload.getDp_stock_id());
                }catch(Exception e){
                    System.out.println("EXCEPTION "+e.toString());
                }    
            }
            
            Vector rowx = new Vector();
            
            if(sameEmp == false){
                no++;
                rowx.add(""+no);
            }else{
                rowx.add("");
            }
            
            
            if(objDpUpload.getOID()!= 0){       //kondis dimana data oid upload ditemukan
                    
                if(objDpStockManagement.getOID() != 0){
                    
                    if(sameEmp == false){
                        
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+objDpStockManagement.getOID()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+objDpUpload.getOID()+ "\">"
                        +objListDpUpload.getEmployee_num());
                        
                    }else{
                        
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+objDpStockManagement.getOID()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+objDpUpload.getOID()+ "\">");
                        
                    }                   
                    
                }else{
                    
                    if(sameEmp == false){
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+0+"\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+objDpUpload.getOID()+ "\">"
                        +objListDpUpload.getEmployee_num()); 
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+0+"\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+objDpUpload.getOID()+ "\">"); 
                    }
                }
                
            }else{      //kondisi dimana data oid upload tidak ditemukan
                
                if(objDpStockManagement.getOID() != 0){
                    
                    if(sameEmp == false){
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+"\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+objDpStockManagement.getOID()+"\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+0+"\">"
                        +objListDpUpload.getEmployee_num());                    
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+"\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+objDpStockManagement.getOID()+"\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+0+"\">");                    
                    }
                    
                    
                }else{
                    
                    if(sameEmp == false){
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+0+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+0+ "\">"
                        +objListDpUpload.getEmployee_num()); 
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objListDpUpload.getEmployee_id()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]+"\" value=\""+0+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]+"\" value=\""+objListDpUpload.getCommencing_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]+"\" value=\""+objListDpUpload.getOwning_date()+ "\">"
                        +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+0+ "\">"); 
                    }
                }
            }
            
            //menampilkan employee name
            if(objListDpUpload.getDp_upload_id() == 0 && objListDpUpload.getDp_stock_id() == 0){
                    
                    rowx.add("<input type=\"hidden\" name=\"emp_name\" value=\""+objListDpUpload.getFull_name()+"\"><font color=FF0000>"+objListDpUpload.getFull_name()+"</font>");  
            }else{
                  
                rowx.add("<input type=\"hidden\" name=\"emp_name\" value=\""+objListDpUpload.getFull_name()+"\">"+objListDpUpload.getFull_name());
                
            }
            
            if(objListDpUpload.getCommencing_date()!=null){  //jika commencing date sudah ada
                
                //menampilkan commencing date
                rowx.add("<input type=\"hidden\" name=\"emp_com_date\" value=\""+objListDpUpload.getCommencing_date()+ "\">"+String.valueOf(objListDpUpload.getCommencing_date()));                
            
                //menampilkan owning date
                if(objDpStockManagement.getOID() != 0){
                
                    rowx.add(""+objDpStockManagement.getDtOwningDate() == null ? "" : Formater.formatDate(objDpStockManagement.getDtOwningDate(),"yyyy-MM-dd"));                                
                
                }else{
                    rowx.add("");        
                }
                
                //menampilkan opname date
                if(objDpUpload.getOID()!= 0){
                   rowx.add(""+objDpUpload.getOpnameDate() == null ? "" : Formater.formatDate(objDpUpload.getOpnameDate(),"yyyy-MM-dd"));       
                }else{
                    rowx.add("");
                }
                
                //menampilkan acquisition date
                
                if(objDpUpload.getOID() != 0){
                    
                    String formater_1 = Formater.formatDate(objDpUpload.getOpnameDate(),"yyyy-MM-dd");
                    String formater_2 = Formater.formatDate(opnameDate,"yyyy-MM-dd");
                    
                    if(formater_1.equals(formater_2)){  //kondisi dimana opname date sama dengan acc date
                        
                        rowx.add("<input onClick=\"ds_sh(this);\" size=\"\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objDpUpload.getAcquisitionDate() == null ? opnameDate : objDpUpload.getAcquisitionDate()), "yyyy-MM-dd")+"\"/>");
                        
                    }else{
                        
                        rowx.add("<input type=\"hidden\" size=\"\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objDpUpload.getAcquisitionDate() == null ? opnameDate : objDpUpload.getAcquisitionDate()), "yyyy-MM-dd")+"\"/>"+Formater.formatDate((objDpUpload.getAcquisitionDate() == null ? opnameDate : objDpUpload.getAcquisitionDate()), "yyyy-MM-dd"));
                        
                    }
                    
                }else{
                    
                    if(objDpStockManagement.getOID() != 0){
                        
                        rowx.add("<input onClick=\"ds_sh(this);\" size=\"\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objDpStockManagement.getDtOwningDate() == null ? new Date() : objDpStockManagement.getDtOwningDate()), "yyyy-MM-dd")+"\"/>");
                        
                    }else{
                        
                        rowx.add("<input onClick=\"ds_sh(this);\" size=\"\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((opnameDate == null ? new Date() : opnameDate), "yyyy-MM-dd")+"\"/>");
                        
                    }
                    
                }
                
                
                if(objDpStockManagement.getOID() != 0){
                       
                    rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_SYSTEM]+"\" value=\""+objDpStockManagement.getiDpQty()+"\">"+objDpStockManagement.getiDpQty());                
                        
                }else{
                   
                    rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_SYSTEM]+"\" value=\""+0+"\">"+0);                
                        
                }
                
                
                if(objDpUpload.getOID() != 0){
                    
                    String formater_1 = Formater.formatDate(objDpUpload.getOpnameDate(),"yyyy-MM-dd");
                    String formater_2 = Formater.formatDate(opnameDate,"yyyy-MM-dd");
                    
                    if(formater_1.equals(formater_2)){   // kondisi dimana opname date sama dengan acc date
                        
                        rowx.add("<input type=\"text\" size=\"8%\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]+"\" value=\""+objDpUpload.getDPNumber()+"\">");                    
                        
                    }else{
                        
                        rowx.add("<input type=\"hidden\" size=\"8%\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]+"\" value=\""+objDpUpload.getDPNumber()+"\">"+objDpUpload.getDPNumber());                    
                        
                    }
                    
                }else{
                    
                    rowx.add("<input type=\"text\" size=\"8%\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]+"\" value=\""+0+"\">");                    
                }
                
                //menampilkan dp taken
                if(objDpStockManagement.getOID() != 0){
                    rowx.add(""+objDpStockManagement.getQtyUsed());     
                }else{
                    rowx.add(""+0);     
                }
                
                //add new record
                rowx.add("<center><input type=\"button\" name=\"btn\" onclick=\"cmdAddCol("+i+")\" value=\"add\"></center>");     

                //add note
                if(objDpUpload.getOID() != 0){
                    
                    String formater_1 = Formater.formatDate(objDpUpload.getOpnameDate(),"yyyy-MM-dd");
                    String formater_2 = Formater.formatDate(opnameDate,"yyyy-MM-dd");
                    
                    if(objDpStockManagement.getOID() != 0){
                        
                        if(formater_1.equals(formater_2)){   // kondisi dimana opname date sama dengan acc date
                        
                            rowx.add("<center><a href=\"javascript:cmdNote('"+objDpStockManagement.getOID()+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a></center>");
                        
                        }else{
                            
                            rowx.add("");
                        }
                        
                    }else{
                        
                        rowx.add("");
                        
                    }
                    
                }else{
                        
                    if(objDpStockManagement.getOID() != 0){
                        
                        rowx.add("<center><a href=\"javascript:cmdNote('"+objDpStockManagement.getOID()+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a></center>");
                        
                    }else{
                        
                        rowx.add("");
                        
                    }
                        
                }
                
                // menampilkan status 
                if(objDpUpload.getOID() != 0 ){
                                        
                    long dpUploadProcess1 = 0;
                    
                    dpUploadProcess1 = SessDPUpload.getDpOpnDateProcess(objDpUpload.getEmployeeId(), objDpUpload.getOpnameDate(),objDpUpload.getOID());
                    
                    if(dpUploadProcess1 != 0){
                        
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_PROCESS+"\">"+PstDPUpload.fieldStatusNames[PstDPUpload.FLD_DOC_STATUS_PROCESS]);
                        
                    }else{
                        
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                    }
                    
                }else{
                    
                    long dpUploadProcess2 = 0;
                    
                    if(objDpStockManagement.getOID() != 0){
                        
                        dpUploadProcess2 = SessDPUpload.getDpOpnDateProcessStock(objDpStockManagement.getEmployeeId(),objDpStockManagement.getOID(),opnameDate);
                        
                        if(dpUploadProcess2 != 0){
                        
                            rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_PROCESS+"\">"+PstDPUpload.fieldStatusNames[PstDPUpload.FLD_DOC_STATUS_PROCESS]);
                        
                        }else{
                        
                            rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                        }
                        
                    }else{
                        
                        rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                        
                    }
                    
                }
                
                // add check box
                if(objDpUpload.getOID() != 0 ){
                    
                    String formater_1 = Formater.formatDate(objDpUpload.getOpnameDate(),"yyyy-MM-dd");
                    String formater_2 = Formater.formatDate(opnameDate,"yyyy-MM-dd");
                    
                   if(formater_1.equals(formater_2)){
                        
                        rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                        
                    }else{
                        
                        rowx.add("");
                    }
                    
                }else{
                    
                    rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                    
                }
                
            }else{
                 
                /*rowx.add("<input type=\"hidden\" name=\"emp_com_date\" value=\"\">");       //commencing date           
                rowx.add("");                                                               //owning date
                rowx.add("");                                                               //opname date
                rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]+"\" value=\"0000-00-00\"><input type=\"text\" size=\"\" name=\"\"  class=\"elemenForm\" value=\"\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_SYSTEM]+"\" value=\"0\"><input type=\"text\"  size=\"14%\" name=\"\"  class=\"elemenForm\" value=\"\" disabled=\"true\">");
                rowx.add("<input type=\"text\" size=\"8%\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]+"\" value=\"\">");                                    
                rowx.add("");                                                                                           
                rowx.add("");
                rowx.add("");                                                                                           
                rowx.add("");
                rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\"\">");
                rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");*/

    rowx.add("");
    rowx.add("");
     rowx.add("");
    rowx.add("<input onclick=\"ds_sh(this);\" size=\"\" name=\"FRM_FLD_ACQUISITION_DATE\" readonly=\"readonly\" style=\"cursor: text\" value=\"\">");
    rowx.add("<input name=\"FRM_FLD_DP_SYSTEM\" value=\"0\" type=\"hidden\">");
    rowx.add("<input size=\"8%\" name=\"FRM_FLD_DP_ACQUIRED\" value=\"0\" type=\"text\">");
    rowx.add("");
    rowx.add("");
    rowx.add("");
     rowx.add("<input name=\"FRM_FLD_DATA_STATUS\" value=\"0\" type=\"hidden\">"); 
    rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");
            }
          
            lstData.add(rowx);         
        }
     }catch(Exception exce){
         System.out.println("Exception"+exce);
     }
        ctrlist.setLinkSufix("')");
        return ctrlist.draw();
    }

%>

<%
   
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
   // boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    //long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
   // boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    //boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    SessDPUpload.UpdateStatusDpStock();   // untuk mengexpiredkan dp yang sudah expired
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_DEPT]);    
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    SrcDPUpload srcDPUpload = new SrcDPUpload();
    FrmSrcDPUpload objFrmSrcDPUpload = new FrmSrcDPUpload(request, srcDPUpload);
    objFrmSrcDPUpload.requestEntityObject(srcDPUpload);
    CtrlDPUpload ctrlDPUpload = new CtrlDPUpload(request);
    
    ControlLine ctrLine = new ControlLine();
    // Proses jika command adalah SAVE	
    if (iCommand == Command.SAVE || iCommand == Command.ACTIVATE) {
	                                                                                                                                                                                                                                                                                                                    // Inisialisasi variable yang meng-handle nilai2 berikut
        String[] emp_id = null;		
        String[] dpUpload_id = null;
        String[] dp_aq_date = null;
        String[] dp_number = null;				
        String[] data_status = null;	
        String[] dpStockId = null;	
        boolean[] is_process = null;				
        Vector vDPUpload = new Vector();
		
        try {
            
            emp_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]);
            dpUpload_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]);
            dp_aq_date = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]);
            dp_number = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]);
            data_status = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]);
            dpStockId = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]);
            
            is_process = new boolean[data_status.length];
            
            for(int i=0; i<emp_id.length; i++){				
                int ix = FRMQueryString.requestInt(request, "data_is_process"+i);
                if(ix==1){
                    is_process[i] = true;
                }else{
                    is_process[i] = false;
                }
            }
            
            //Vector untuk menyimpan variable
            vDPUpload.add(emp_id);                      //0
            vDPUpload.add(dpUpload_id);                 //1
            vDPUpload.add(dp_aq_date);                  //2
            vDPUpload.add(dp_number);                   //3
            vDPUpload.add(data_status);                 //4
            vDPUpload.add(is_process);                  //5
            vDPUpload.add(srcDPUpload.getOpnameDate()); //6
            vDPUpload.add(dpStockId);                   //7
            
        }
        catch (Exception e) 
        {
            System.out.println("[ERROR] opnameDP : "+e.toString());
        }
        
        if(iCommand == Command.SAVE){//jika disimpan saja
            
            System.out.println("---------- SIMPAN DATA PADA STOCK UPLOAD ---------------");
            
            Vector vDPUploadId = new Vector(1,1);
            
            vDPUploadId = com.dimata.harisma.session.leave.SessDPUpload.saveDPUpload(vDPUpload);
            
            if(vDPUploadId.size()>0){
                
                status = true;
                
            }else{
                
                status = false;
                
            }
            
        }else if(iCommand == Command.ACTIVATE) {//Jika diproses
            
            System.out.println("-------------- PROCESS UPLOAD -------------------------");
            
            Vector vAlUploadId = new Vector(1,1);
            
            vAlUploadId = com.dimata.harisma.session.leave.SessDPUpload.saveDPUpload(vDPUpload);            //untuk penyimpanan
          
            status = com.dimata.harisma.session.leave.SessDPUpload.opnameAllDp(vAlUploadId);                //untuk pemrosesan
          
        }
    }

    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 20;
    int vectSize = 0;
    
    Vector vDataDpToUpload = new Vector(1,1);
    Vector resultDp = new Vector(1,1);
    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV) ||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
        
        Vector vTemp = new Vector(1,1);
        
        //vectSize = SessDPUpload.sizeListDP(srcDPUpload);
        //vectSize = SessDPUpload.sizeDp(srcDPUpload);
        
        start = ctrlDPUpload.actionList(iCommand, start, vectSize, recordToGet);
        
        //resultDp = SessDPUpload.listDP( srcDPUpload, start, recordToGet); // result untuk mendapatkan list DP
        resultDp = SessDPUpload.listDPBalancing( srcDPUpload, start, recordToGet); // result untuk mendapatkan list DP
        vectSize = resultDp.size();

    }else{
        
        if(iCommand==Command.ADD){
            
            int addIndex = FRMQueryString.requestInt(request,"addIndex");
            
            String[] emp_id = null;		
            String[] dpUpload_id = null;
            String[] dp_aq_date = null;
            String[] dp_number = null;				
            String[] data_status = null;
            String[] commencing_date = null;
            String[] owningDate = null;
            String[] dpStockId = null;	
            String[] dp_system = null;
            String[] owning_date = null;
            
            int[] is_process = null;				
            Vector vDPUpload = new Vector();
            
            // Mengambil array nilai2 berikut
            try {
                
                emp_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]);
                dpUpload_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]);
                dp_aq_date = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]);
                dp_number = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]);
                data_status = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]);
                commencing_date = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_COMMENCING_DATE]);
                dpStockId = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_STOCK_ID]);
                dp_system = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_SYSTEM]);
                owningDate = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_OWNING_DATE]);
                
                is_process = new int[data_status.length];
                
                for(int i=0; i<emp_id.length; i++){				
                    is_process[i] = FRMQueryString.requestInt(request, "data_is_process"+i);
                }
                
                //Looping to create employee and DPUpload object
                for(int i=0;i<emp_id.length;i++){
                   try{                    
                    Employee objEmployee = new Employee();
                    
                    DPUpload objDPUpload = new DPUpload();
                    
                    DpBalancing dpbalancing = new DpBalancing();          
                    
                    listDpUpload DpUpload = new listDpUpload();
                    
                    objEmployee = PstEmployee.fetchExc(Long.parseLong(emp_id[i]));
                    
                    if(objEmployee.getCommencingDate()!=null){
                        
                        DpStockManagement dpStockManagement = new DpStockManagement();
                    
                        if(Long.parseLong(dpStockId[i]) != 0){
                            
                            dpStockManagement = PstDpStockManagement.fetchExc(Long.parseLong(dpStockId[i]));
                        
                        }
                        
                        long dpUploadOid = 0;
                        
                        try{
                            
                            dpUploadOid = Long.parseLong(dpUpload_id[i]);
                            
                        }catch(Exception e){
                            System.out.println("EXCEPTION "+e.toString());
                        }
                        
                        DpUpload.setDp_stock_id(Long.parseLong(dpStockId[i]));
                        DpUpload.setEmployee_id(objEmployee.getOID());
                        DpUpload.setDepartment_id(objEmployee.getDepartmentId());
                        DpUpload.setDp_qty(Float.parseFloat(dp_system[i]));                 //stock pada dp management
                        DpUpload.setDp_qty_upload(Float.parseFloat(dp_number[i]));          //stock pada dp upload
                        DpUpload.setEmployee_num(objEmployee.getEmployeeNum());
                        DpUpload.setFull_name(objEmployee.getFullName());
                        DpUpload.setDp_aq_date(SessDPUpload.parseStringToDate(dp_aq_date[i],SessDPUpload.DATE_FORMAT_OTHER));
                        DpUpload.setData_status(Integer.parseInt(data_status[i]));
                        DpUpload.setCommencing_date(objEmployee.getCommencingDate());
                        DpUpload.setDp_upload_id(dpUploadOid);
                        
                        if(Long.parseLong(dpStockId[i]) != 0){
                            DpUpload.setOwning_date(dpStockManagement.getDtOwningDate());
                        }
                    }
                    
                    Vector vTemp = new Vector(1,1);
                    
                    resultDp.add(DpUpload);
                    
                    if(i==addIndex){
                        
                        listDpUpload DpUpload_II = new listDpUpload();
                        
                        DpBalancing dpbalancing2 = new DpBalancing();          
                        Employee objEmployee2 = new Employee();
                        objEmployee2 = PstEmployee.fetchExc(Long.parseLong(emp_id[i]));
                        Vector vTemp2 = new Vector(1,1);
                        String name = "<font color=FF0000>"+objEmployee.getFullName()+"</font>";
                        objEmployee2.setFullName(name);
                        
                        DpUpload_II.setDp_stock_id(0);
                        DpUpload_II.setEmployee_id(objEmployee2.getOID());
                        DpUpload_II.setDepartment_id(objEmployee2.getDepartmentId());
                        DpUpload_II.setDp_qty(0);                       //stock pada dp management
                        DpUpload_II.setDp_qty_upload(0);                //stock pada dp upload
                        DpUpload_II.setEmployee_num(objEmployee2.getEmployeeNum());
                        DpUpload_II.setFull_name(objEmployee2.getFullName());
                        DpUpload_II.setDp_aq_date(SessDPUpload.parseStringToDate(dp_aq_date[i],SessDPUpload.DATE_FORMAT_OTHER));
                        DpUpload_II.setData_status(Integer.parseInt(data_status[i]));
                        DpUpload_II.setCommencing_date(objEmployee.getCommencingDate());
                        
                        resultDp.add(DpUpload_II);
                    }
                    } catch(Exception exc2){
                    System.out.println(exc2);  
                   }
                }
             
            }catch(Exception ex){
                System.out.println("[ERROR] system.opnameDP.jsp :::::::::: "+ex.toString());
            }
            
        }else{ 
            start = 0;
        }
    }
%>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Day Off Payment Management</title>
<script language="JavaScript">
    
function cmdNote(hiddenOID){	
	window.open("note_edit_dp.jsp?command="+<%=Command.EDIT%>+"&hidden_DP_id="+hiddenOID, null, "height=300,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}
    
    
function cmdUpdateDep(){
	document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.OK)%>";
	document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="DP_Balancing.jsp"; 
	document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdSave() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "DP_Balancing.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdProccess() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.ACTIVATE)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "DP_Balancing.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function deptChange() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "DP_Balancing.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdSearch() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.LIST)%>";
    getThn();										
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "DP_Balancing.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdViewList(){
    window.open("dp_opname_list.jsp", null, "height=500,width=400, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
}

function cmdAddCol(index){
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.ADD)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.addIndex.value = index;
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "DP_Balancing.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

//---------------------------------------------------
    function getThn(){
            var date1 = ""+document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_mn.value=bln;
            document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_dy.value=hri;
            document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_yr.value=thn;
    }


    function hideObjectForDate(){
    }

    function showObjectForDate(){
    } 
    
    function setChecked(val) {
        
	dml=document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>;
	len = dml.elements.length;
	var i=0;
         
	for( i=0 ; i<len ; i++) {	
            dml.elements[i].checked = val;
        }   
        
    }
    
    function checkEnableAll(){
        if(document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.isSelect.value == 1){
            setChecked(1);
        }else{
            setChecked(0);
        }
    }
//-------------- script control line -------------------
        function cmdListFirst(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="DP_Balancing.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="DP_Balancing.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="DP_Balancing.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="DP_Balancing.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
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
    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display:none">
  
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
           <td height="20"> <font color="#FF6600" face="Arial"><strong><!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Leave Balancing &gt; Day off Payment <!-- #EndEditable --> 
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
                                    <form name="<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>" method="post" action="">
                                    <%if(iCommand == Command.SAVE || iCommand == Command.ACTIVATE){ %>
                                        <input type="hidden" name="command" value="<%=String.valueOf(Command.LIST)%>">
                                    <%}else{%>
                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <%}%>
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="addIndex">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="3"><b><u><font color="#FF0000">ATTENTION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>OPNAME</b>day off payment (DP) to database<br>                                               
                                                  <hr>
                                                </td>
                                              </tr>                                              

                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_NAME]%>"  value="<%=String.valueOf(srcDPUpload.getEmployeeName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_PAYROLL]%>"  value="<%=String.valueOf(srcDPUpload.getEmployeePayroll())%>" class="elemenForm">
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
							Vector listCat = PstEmpCategory.list(0, 0, "", "EMP_CATEGORY");                                                        
							for (int i = 0; i < listCat.size(); i++) {
								EmpCategory cat = (EmpCategory) listCat.get(i);
								cat_key.add(cat.getEmpCategory());
								cat_value.add(String.valueOf(cat.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_CAT],"formElemen",null,String.valueOf(srcDPUpload.getEmployeeCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                <% 
                                                /*Vector dept_value = new Vector(1, 1);
                                                Vector dept_key = new Vector(1, 1);
                                                Vector listDept = new Vector(1, 1);
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                        dept_value.add("0");
                        dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                    } else {
                        String whereClsDep="(DEPARTMENT_ID = " + departmentOid+")";
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
                                    if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                      grpIdx = countIdx;   // A ha .. found here 
                                    }
                                }
                                countIdx++;
                            } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop<MAX_LOOP)); // if found then exit
                            
                            // compose where clause
                            if(grpIdx>=0){
                                String[] grp = (String[]) depGroup.get(grpIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    whereClsDep=whereClsDep+ " OR (DEPARTMENT_ID = " + comp+")"; 
                                }         
                               }                                                  
                        } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);
                        }

                        listDept = PstDepartment.list(0, 0,whereClsDep, "");
                    }
                } else {
                    dept_value.add("0");
                    dept_key.add("select ...");
                    listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                }
            } else {
                dept_value.add("0");
                dept_key.add("select ...");
                listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
            }
                                                
           for (int i = 0; i < listDept.size(); i++) {
                Department dept = (Department) listDept.get(i);
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            }         */

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
                                   
                                                  <% 
                                                        //Vector dept_value = new Vector(1,1);
                                                        //Vector dept_key = new Vector(1,1);
                                                        //Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        //dept_key.add("all department...");
                                                        //dept_value.add("0");
                                                        
                                                        //for (int i = 0; i < listDept.size(); i++) {
                                                        //    Department dept = (Department) listDept.get(i);
                                                        //    dept_key.add(dept.getDepartment());
                                                        //    dept_value.add(String.valueOf(dept.getOID()));
                                                       // }
                                                    %>
                                                  <%=ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_DEPT], "elementForm", null, selectValueDepartment, dept_value, dept_key, " onChange=\"javascript:cmdUpdateDep()\"")%>
                                                  <%//= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_DEPT],"formElemen",null,String.valueOf(srcDPUpload.getEmployeeDepartement()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %> </td>
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
                                                <%= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_SEC],"formElemen",null,String.valueOf(srcDPUpload.getEmployeeSection()), sec_value, sec_key, "") %> </td>
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
                                                <%= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_POS],"formElemen",null,String.valueOf(srcDPUpload.getEmployeePosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      <tr> 
                                          	<td width="13%">Opname Date</td>
                                          	<td width="2%">:</td>
                                          	<td width="85%">
                                                    <input onClick="ds_sh(this);" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>" readonly="readonly" style="cursor: text" value="<%=Formater.formatDate((srcDPUpload.getOpnameDate() == null? new Date() : srcDPUpload.getOpnameDate()), "yyyy-MM-dd")%>"/>
                                                    <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_mn">
                                                    <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_dy">
                                                    <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_yr">
                                                    <a href="javascript:cmdViewList()" class="buttonlink"><img src="../../images/icon/folderopen.gif" border="0" alt="null"></a>
                                                    <script language="JavaScript" type="text/JavaScript">getThn();</script>
                                                </td>
                                              </tr>
                                              <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_DATA_STATUS]%>" value="-1">
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
                                          <% if (resultDp!=null && resultDp.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST)||(iCommand==Command.ADD))) { %>
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
                                                    <%//=drawList(vDataDpToUpload, srcDPUpload.getOpnameDate(), start)%>
                                                    <%=drawList(resultDp, srcDPUpload.getOpnameDate(), start)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %>
                                               
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                    <tr> 
                                                      
                                                      <td width="50" class="command" nowrap>
                                                          <br />
                                                          <a href="javascript:cmdSave()">Save</a>||<a href="javascript:cmdProccess()">Proccess</a></td>
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

