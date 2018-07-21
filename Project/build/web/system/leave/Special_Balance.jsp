
<%-- 
    Document   : Special_Balance
    Created on : Dec 25, 2009, 1:25:43 PM
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
<%@ page import = "com.dimata.harisma.form.search.FrmSrcSpecialUpload" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcAlUpload" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcSpecialUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessOpnameAL" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.AlStockManagement" %>
<%@ page import = "com.dimata.harisma.entity.attendance.AlStockManagement" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_AL_OPNAME); %>
<%@ include file = "../../main/checkuser.jsp" %>


<%!
    public String drawList(Vector objEmpALData, Date opnameDate,int start){
        String DateSekarang = Formater.formatDate(opnameDate, "yyyy-MM-dd");
        
        System.out.println("Opname date sekarang : "+DateSekarang);

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("<center>No</center>","5%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","6%","2","0");
        ctrlist.addHeader("<center>Name</center>","15%","2","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.COMMENCING_DATE)+"</center>","10%","2","0");
       
        ctrlist.addHeader("<center>Opname Previous</center>","8%","2","0");        
        ctrlist.addHeader("<center>System</center>","30%","0","5");       
        ctrlist.addHeader("<center>Previous</center>","8%","0","0");
        ctrlist.addHeader("<center>AL","8%","0","0");
        ctrlist.addHeader("<center>Qty","8%","0","0");
        ctrlist.addHeader("<center>Taken","8%","0","0");    
        ctrlist.addHeader("<center>Balance","8%","0","0"); 
         
        ctrlist.addHeader("<center>New</center>","30%","0","5");   
        ctrlist.addHeader("<center>Previous</center>","6%","0","0");
        ctrlist.addHeader("<center>AL</center>","6%","0","0");
        ctrlist.addHeader("<center>QTY</center>","6%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center> ","6%","0","0");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.BALANCE)+"</center> ","6%","0","0");
        
        ctrlist.addHeader("<center>Note</center> ","5%","2","0");
        ctrlist.addHeader("<center>Status</center>","9%","2","0");
        ctrlist.addHeader("Process/<br>Reprocess","7%","2","0");
       
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();

        System.out.println("Membuat table dalam proses...");
        for (int i = 0; i < objEmpALData.size(); i++){
            
            
            //System.out.println(i);
            Vector tempData = new Vector();
            tempData = (Vector)objEmpALData.get(i);
            
            Employee objEmployee = new Employee();
            AlUpload objAlUpload = new AlUpload();
            //SrcAlUpload srcAlUpload = new SrcAlUpload();
            
            objEmployee = (Employee)tempData.get(0);
            objAlUpload = (AlUpload)tempData.get(1);
            //srcAlUpload = (SrcAlUpload)tempData.get(2);
            
            Vector rowx = new Vector();
            rowx.add(String.valueOf(i+1+start));
            
            //employee id
            rowx.add("<input type=\"hidden\" name=\"data_emp_id\" value=\""+objEmployee.getOID()+ "\">"
                    +"<input type=\"hidden\" name=\"data_alUpload_id\" value=\""+objAlUpload.getOID()+ "\">"
                    +objEmployee.getEmployeeNum());
            
            //employee num
            rowx.add("<input type=\"hidden\" name=\"data_emp_name\" value=\""+objEmployee.getFullName()+ "\">"
                    +objEmployee.getFullName());
            
            if(objEmployee.getCommencingDate()!=null){
                           
                boolean cekopnamebefor = SessOpnameAL.checkOpnameBefore(objEmployee.getOID(), objEmployee.getCommencingDate());
                
                //commencing date
                rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\""+objEmployee.getCommencingDate()+ "\">"+String.valueOf(objEmployee.getCommencingDate()));
               
                AlUpload dataAlUpload = SessAlUpload.getOpnamePrevious(objEmployee.getOID()); // pengambilan data opname yang belum diproses
                
                AlStockManagement AlStockManagementData = SessAlUpload.getValueStockManagement(objEmployee.getOID());
                
                long DataUploadOID = 0;
                int prevBalanceCurrent = 0;
                float alQtyCurrent = 0;
                float qtyUsedCurrent = 0;
                float qtyResidueCurrent = 0;
                float entitledCurrent = 0; 
               
                float ValResidue = 0;
                float PrevBalance = 0;
                float PrevTaken = 0;                
                float ALLast = 0;
                float AlQty = 0;
                float systemBalance = 0;
                long stockManagementID = 0;
                
                if(AlStockManagementData!=null){
                    stockManagementID = AlStockManagementData.getOID();
                    ValResidue = AlStockManagementData.getQtyResidue();
                    PrevBalance = AlStockManagementData.getPrevBalance();
                    DataUploadOID = AlStockManagementData.getOID();
                    PrevTaken = AlStockManagementData.getQtyUsed();
                    ALLast = AlStockManagementData.getEntitled();
                    AlQty = AlStockManagementData.getAlQty();
                    systemBalance = AlStockManagementData.getQtyResidue();
                }                
                
                String DateSekarangOpname = DateSekarang;
                
                float bal = 0;
                
                if(dataAlUpload != null){
                    bal = dataAlUpload.getNewQty() + dataAlUpload.getLastPerToClear() - dataAlUpload.getCurrPerTaken(); 
                    try{
                    DateSekarangOpname = Formater.formatDate(dataAlUpload.getOpnameDate(), "yyyy-MM-dd"); 
                    }catch(Exception e){
                        System.out.println("EXCEPTION :::: "+e.toString());
                    }    
               }
                
                String opnameClear = "";
                
                // opname previous
                if(dataAlUpload == null){                                                  
                    rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+opnameClear+"\" >"+opnameClear);
                }else{
                    if(DateSekarang.equals(DateSekarangOpname)){
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataAlUpload.getOpnameDate()+"\" >"+String.valueOf(dataAlUpload.getOpnameDate()));
                    }else{
                        rowx.add("<input type=\"hidden\" name=\"opname_previous"+i+"\" value=\""+dataAlUpload.getOpnameDate()+"\" >"+String.valueOf(dataAlUpload.getOpnameDate()));
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
                    //rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\""+strAl_last+"\">"+strAl_last);
                    //rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\""+objAlUpload.getNewAl()+"\">"+objAlUpload.getNewAl());
                }catch(Exception ex){                   
                    //rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\"\">"+"0");
                }    
                
                rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\"\">"+PrevBalance); 
                rowx.add("<input type=\"hidden\" name=\"data_emp_AL_last_per\" value=\"\">"+ALLast);
                rowx.add("<input type=\"hidden\" name=\"data_emp_ALQty_last_per\" value=\"\">"+AlQty);                       
                rowx.add("<input type=\"hidden\" name=\"data_emp_taken_last_per\" value=\"\">"+PrevTaken);
                rowx.add("<input type=\"hidden\" name=\"data_emp_Balance_last_per\" value=\"\">"+systemBalance);             
              
                //new previous 
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                    if(dataAlUpload != null)
                        if(DateSekarang.equals(DateSekarangOpname))
                            rowx.add("<input type=\"text\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+dataAlUpload.getLastPerToClear()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                        else
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+dataAlUpload.getLastPerToClear()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                    else
                        rowx.add("<input type=\"text\" name=\"data_emp_to_clear_last_per"+i+"\"  value=\""+PrevBalance+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                }else{                   
                        rowx.add("<input type=\"hidden\" disabled=\"true\" name=\"data_emp_to_clear_last_per"+i+"\" value=\""+AlStockManagementData.getQtyResidue()+"\"><input type=\"text\" name=\"\"  value=\"0\" size=\"5\" disabled=\"true\">");                    
                }             
                             
                String strAl_curr = String.valueOf(com.dimata.harisma.session.leave.SessAlUpload.getAlEarned(objEmployee.getOID(), objAlUpload.getOpnameDate()));
               
                //new AL
                if(objAlUpload.getOID()==0){
                    if(dataAlUpload != null)  
                        if(DateSekarang.equals(DateSekarangOpname))
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+dataAlUpload.getNewAl()+"\">");                    
                        else
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+dataAlUpload.getNewAl()+"\">");                    
                    else
                        rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+strAl_curr+"\">");                    
                   
                }else{ 
                    if(dataAlUpload != null)  
                        if(DateSekarang.equals(DateSekarangOpname))
                            rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+dataAlUpload.getNewAl()+"\">");                    
                        else
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+dataAlUpload.getNewAl()+"\">");                    
                    else
                        rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" size=\"5\" value=\""+strAl_curr+"\">");                                                                                  
                }               
                
                //rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" value=\""+strAl_curr+"\">");
                //rowx.add("<input type=\"text\" name=\"data_emp_earned_curr_per"+i+"\" value=\""+objAlUpload.getNewAl()+"\">");                              
                
                float qty = 0;
                
                if(dataAlUpload!=null){
                    try{
                        qty = dataAlUpload.getNewQty();
                        System.out.println(" Total qty : "+qty );
                    }catch(Exception e){
                        System.out.println("EXCEPTION "+e.toString());
                    }
                }
                
                //new QTY
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                    if(dataAlUpload != null){
                        if(DateSekarang.equals(DateSekarangOpname))                   
                            rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+qty+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                       
                        else
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+qty+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                    }else{
                        rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+AlQty+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                    }
                }else{
                    if(dataAlUpload != null){
                        if(DateSekarang.equals(DateSekarangOpname))                   
                            rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+qty+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                       
                        else
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+qty+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                        
                    }else{
                        rowx.add("<input type=\"text\" name=\"data_emp_ALQty_new"+i+"\" size=\"5\" value=\""+AlQty+"\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");                                    
                    }
                }                                                 
                
                //new Taken
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                   if(dataAlUpload != null){   
                     if(DateSekarang.equals(DateSekarangOpname))  
                        rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUpload.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                     else
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUpload.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                   }else{
                    rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\"  value=\""+PrevTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                   } 
                }else{
                   if(dataAlUpload != null){   
                     if(DateSekarang.equals(DateSekarangOpname))  
                        rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUpload.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                     else
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+dataAlUpload.getCurrPerTaken()+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                   }else{
                        rowx.add("<input type=\"text\" name=\"data_emp_taken_curr_per"+i+"\" value=\""+PrevTaken+"\" size=\"5\" onkeyup=\"cmdCekLast("+i+","+strAl_last+")\">");
                   }
                }
                
                //Balance
                if(dataAlUpload != null){                                       
                    rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_balance_per"+i+"\" size=\"5\" value=\""+bal+"\">");                                    
                }else{
                    rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_emp_balance_per"+i+"\" size=\"5\" value=\""+ValResidue+"\">");                                    
                }    
                //note
                rowx.add("<a href=\"javascript:cmdNote('"+stockManagementID+"')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\" title=\"Note\"></a>");
                
                //process
                if(objAlUpload.getDataStatus()==PstAlUpload.FLD_DOC_STATUS_PROCESS){
                    rowx.add("<input type=\"hidden\" name=\"data_status\" value=\""+PstAlUpload.FLD_DOC_STATUS_PROCESS+"\">"
                            +PstAlUpload.fieldStatusNames[PstAlUpload.FLD_DOC_STATUS_PROCESS]);                   
                }else{
                    rowx.add("<input type=\"hidden\" name=\"data_status\" value=\""+PstAlUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                }
                
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                    if(dataAlUpload != null) 
                        if(DateSekarang.equals(DateSekarangOpname))  
                            rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                        else
                            rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                    else
                        rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
                }else{
                    rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"0\" disabled=\"false\"></center>");
                }
            }else{              
                rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\"\">");
                rowx.add("<input type=\"hidden\" name=\"data_emp_earned_last_per\" value=\\>"+"0");
                rowx.add("<input type=\"hidden\" name=\"data_emp_to_clear_last_per"+i+"\" value=\"0\"><input type=\"text\" name=\"\"  value=\"0\" class=\"elemenForm\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\"data_emp_earned_curr_per"+i+"\" value=\"0\">"+"0");
                rowx.add("<input type=\"hidden\" name=\"data_emp_taken_curr_per"+i+"\" value=\"0\"><input type=\"text\" name=\"\"  value=\"0\" class=\"elemenForm\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\"data_emp_balance_per\" value=\"\">"+"0");
                rowx.add("<input type=\"hidden\" name=\"data_status\" value=\""+PstAlUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");
            }
            lstData.add(rowx);            
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.drawList();
        
    }
%>

<%
    
    System.out.println("position OID "+positionOID);
    
    long GM_OID = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_GM")));
    System.out.println("GM OID "+GM_OID);
    //boolean statusManager = GM_OID == positionOID ? true : false;
    
    boolean statusManager = true;
    
    System.out.println("Status manager "+statusManager);    
    
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_EMP_DEPT]);
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    
    
    SrcSpecialUpload srcSpecialUpload = new SrcSpecialUpload();
    FrmSrcSpecialUpload objFrmSrcSpecialUpload = new FrmSrcSpecialUpload(request, srcSpecialUpload);
    objFrmSrcSpecialUpload.requestEntityObject(srcSpecialUpload);
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
    
    //System.out.println("tahun sekarang "+str_dt_StartDate);
    
    String potongan = TglSekarang.substring(0,4);
    
    System.out.println("Potongan "+potongan);
    
    ControlLine ctrLine = new ControlLine();
    
    // Proses jika command adalah SAVE	
    if (iCommand == Command.SAVE || iCommand == Command.ACTIVATE) {
	//System.out.println("MASUK KESINI........");
		// Inisialisasi variable yang meng-handle nilai2 berikut
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
            
            is_process = new boolean[data_status.length];//request.getParameterValues("data_is_process");				
            emp_to_clear_last_per = new String[data_status.length];
            emp_earned_curr_per  = new String[data_status.length];
            emp_taken_curr_per = new String[data_status.length];
            emp_ALQty_new = new String[data_status.length];
            
            for(int i=0; i<emp_id.length; i++){
                emp_to_clear_last_per[i] = FRMQueryString.requestString(request,"data_emp_to_clear_last_per"+i);
                emp_earned_curr_per[i] = FRMQueryString.requestString(request,"data_emp_earned_curr_per"+i);				
                emp_taken_curr_per[i] = FRMQueryString.requestString(request,"data_emp_taken_curr_per"+i);				
                emp_earned_last_per[i] = FRMQueryString.requestString(request,"data_emp_earned_last_per"+i);                
                emp_ALQty_new[i] = FRMQueryString.requestString(request,"data_emp_ALQty_new"+i);
                
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
            vAlUpload.add(srcSpecialUpload.getOpnameDate());//6
            vAlUpload.add(emp_earned_curr_per);//7
            vAlUpload.add(emp_earned_last_per);//8
            vAlUpload.add(emp_ALQty_new);//9
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
            //vAlUploadId = com.dimata.harisma.session.leave.SessAlUpload.saveAlUpload(vAlUpload);
            vAlUploadId = com.dimata.harisma.session.leave.SessAlUpload.saveAlUploadBalance(vAlUpload,true); 
//update by satrya 2013-01-06
            //status = com.dimata.harisma.session.leave.SessAlUpload.opnameALAllData(vAlUploadId);                      
            status = com.dimata.harisma.session.leave.SessAlUpload.opnameALAllData(vAlUploadId,null);
        }
    }

    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 20;
    int vectSize = 0;
    
   Vector vDataAlToUpload = new Vector(1,1);
   // if(srcAlUpload.getOpnameDate()!=null && (iCommand != Command.SAVE && iCommand != Command.ACTIVATE && iCommand != Command.ADD)){
   //     vDataAlToUpload = com.dimata.harisma.session.leave.SessAlUpload.searchAlData(srcAlUpload, 0, 0);
   // }
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        Vector vTemp = new Vector(1,1);
        vTemp = com.dimata.harisma.session.leave.SessAlUpload.searchAlData(srcSpecialUpload, 0, 0);
        vectSize = vTemp.size();
        start = ctrlAlUpload.actionList(iCommand, start, vectSize, recordToGet);
        vDataAlToUpload = com.dimata.harisma.session.leave.SessAlUpload.searchAlData(srcSpecialUpload, start, recordToGet);
        //vDataAlToUpload = com.dimata.harisma.session.leave.SessAlUpload.getEmployeeAndDataUpload(srcAlUpload, start, recordToGet,null);
    }else{
        start = 0;
    }
%>

<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Special Balance</title>
<script language="JavaScript">

function cmdNote(hiddenOID){	
	window.open("note_edit.jsp?command="+<%=Command.EDIT%>+"&hidden_AL_id="+hiddenOID, null, "height=300,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}

function cmdUpdateDep(){
	document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value="<%=String.valueOf(Command.ADD)%>";
	document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action="Special_Balancing.jsp"; 
	document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
}

function cmdSave() {
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value = "<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action = "Special_Balancing.jsp";
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
}

function cmdProccess() {
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value = "<%=String.valueOf(Command.ACTIVATE)%>";
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action = "Special_Balancing.jsp";
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
}

function deptChange() {
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action = "Special_Balancing.jsp";
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
}

function cmdSearch() {
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value = "<%=String.valueOf(Command.LIST)%>";
    getThn();										
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action = "Special_Balancing.jsp";
    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
}

function cmdViewList(){
    window.open("al_opname_list.jsp", null, "height=500,width=400, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
}

    function setChecked(val) {
	dml=document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>;
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
        document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value=20;
        document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_balance_per"+k%>.value=20;        
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
            val1 = parseInt(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            val2 = parseInt(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
            if(isNaN(val2)){
                if(trim(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value)+" "!=" "){
                    document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value=0;
                }
            }
            if(isNaN(val1)){
                document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value=1;
            }
            <%--if(val2>max){
                document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value=max;
            }--%>
            if(val2>0 && val1>0){
                document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value=1;
            }
            break;
        
    <%}%>
    }
}



function cmdCekLast(index,max){
    var val1;
    var val2;
    var prev;
    var taken;
    var qty;
    var balance;
    switch(index){
    <%
        for(int k=0;k<vDataAlToUpload.size();k++){
    %>
            case <%=""+k%>:
           
            prev = parseInt(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            taken = parseInt(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
            qty = parseInt(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_ALQty_new"+k%>.value);
            balance = ( prev + qty ) - taken;
            
            val1 = parseInt(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            val2 = parseInt(document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
                
            document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%="data_emp_balance_per"+k%>.value=balance;
            
            
            break;
        
    <%}%>
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
            var date1 = ""+document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>_mn.value=bln;
            document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>_dy.value=hri;
            document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>_yr.value=thn;
    }


    function hideObjectForDate(){
    }

    function showObjectForDate(){
    } 
//-------------- script control line -------------------
        function cmdListFirst(){
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action="opnameAl.jsp";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action="opnameAl.jsp";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action="opnameAl.jsp";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.action="opnameAl.jsp";
		document.<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD%>.submit();
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
<!-- #BeginEditable "stylestab" -->    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

<!-- Untuk Calendar-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    <tr><td id="ds_calclass">
    </td></tr>
</table>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calendar-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Employee &gt; Leave Opname Aplication &gt; Annual Leave<!-- #EndEditable --> 
                  </strong></font> </td>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% if (privAdd) { %>
                                    <form name="<%=FrmSrcSpecialUpload.FRM_SPECIAL_UPLOAD %>" method="post" action="">
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
                                                  special leave to database<br>                                               
                                                  <hr>
                                                </td>
                                              </tr>                                              

                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_EMP_NAME]%>"  value="<%=String.valueOf(srcSpecialUpload.getEmployeeName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_EMP_PAYROLL]%>"  value="<%=String.valueOf(srcSpecialUpload.getEmployeePayroll())%>" class="elemenForm">
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
                                                <%= ControlCombo.draw(FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_EMP_CAT],"formElemen",null,String.valueOf(srcSpecialUpload.getEmployeeCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");                                                       
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  
                                                  <%= ControlCombo.draw(FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_EMP_DEPT],"formElemen",null,String.valueOf(srcSpecialUpload.getEmployeeDepartement()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %> </td>
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
                                                <%= ControlCombo.draw(FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_EMP_SEC],"formElemen",null,String.valueOf(srcSpecialUpload.getEmployeeSection()), sec_value, sec_key, "") %> </td>
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
                                                <%= ControlCombo.draw(FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_EMP_POS],"formElemen",null,String.valueOf(srcSpecialUpload.getEmployeePosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      <tr> 
                                          	<td width="13%">Opname Date</td>
                                          	<td width="2%">:</td>
                                          	<td width="85%">
                                                    <input onClick="ds_sh(this);" name="<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>" readonly="readonly" style="cursor: text" value="<%=Formater.formatDate((srcSpecialUpload.getOpnameDate() == null? new Date() : srcSpecialUpload.getOpnameDate()), "yyyy-MM-dd")%>"/>
                                                    <input type="hidden" name="<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>_mn">
                                                    <input type="hidden" name="<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>_dy">
                                                    <input type="hidden" name="<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_OPNAME_DATE]%>_yr">
                                                    
                                                    <a href="javascript:cmdViewList()" class="buttonlink"><img src="../../images/icon/folderopen.gif" border="0" alt="null"></a>
                                                    <script language="JavaScript" type="text/JavaScript">getThn();</script>
                                                    
                                                </td>
                                              </tr>
                                              <input type="hidden" name="<%=FrmSrcSpecialUpload.fieldNames[FrmSrcSpecialUpload.FRM_FIELD_DATA_STATUS]%>" value="-1">                                              
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
                                          <% if (vDataAlToUpload.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                               <tr>
                                                <td align="right" valign="top">
                                                    [<a onclick="javascript:setChecked(1)"><b>Check All</b></a>]
                                                    |[<a onclick="javascript:setChecked(0)"><b>Uncheck All</b></a>]
                                                    <img src="<%=imagesroot%>/arrow_ltr.png">
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
                                                    <%=drawList(vDataAlToUpload, srcSpecialUpload.getOpnameDate(), start)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                    <tr> 
                                                      
                                                      <td width="50" class="command" nowrap>
                                                          <br />
                                                          <a href="javascript:cmdSave()">Save</a>                                                          
                                                            <%
                                                             if(statusManager)  {
                                                            %>                                                       
                                                            ||<a href="javascript:cmdProccess()">Proccess</a>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
