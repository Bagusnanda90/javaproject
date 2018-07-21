
<%-- 
    Document   : leave_al_closing
    Created on : Jan 15, 2010, 1:44:22 PM
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_AL_CLOSING); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%! 
static final int SEARCH_ALL = 1;
static final int SEARCH     = 2;
        
public String drawList(JspWriter outObj,Vector objectClass,Vector result,I_Leave leaveConfig)
{
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	      		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
	
        ctrlist.addHeader("<center>No</center>","5%");	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","5%");	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EMPLOYEE)+"</center>","15%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.DEPARTMENT)+"</center>","10%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.COMMENCING_DATE)+"</center>","5%");
       
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.ENTITLE) +" Date</center>","6%");		
        ctrlist.addHeader("<center>Prev. Period</center>","5%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.ENTITLE)+"</center>","5%");
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TAKEN)+"</center>","5%");	
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.BALANCE)+"</center>","5%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EXTEND)+"</center>","15%");
        int maxList=0;
        if(objectClass != null && objectClass.size() > 0){        
          maxList= objectClass.size(); 
        }
        int maxListEmpty=0;
        if(result != null && result.size() > 0){        
          maxListEmpty= result.size(); 
        }
        
        String link = "<a href=\"Javascript:SetAllCheckBoxes('"+ FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP +"','Close', true,"+maxList+","+maxListEmpty+")\">Set All</a> | "+
              "<a href=\"Javascript:SetAllCheckBoxes('"+ FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP +"','Close', false,"+maxList+","+maxListEmpty+")\">Release All</a>";                       
        ctrlist.addHeader("<center>Closing<br>"+link+"</center>","8%");	 
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.TRANSFER)+"</center>","8%");
        

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
        int no = 1 ; 
        String formatFloat ="###.###";
        if(objectClass != null && objectClass.size() > 0){
            Date today= new Date();
                //Vector vectValExtend = new Vector();
               // Vector vectListExtend = new Vector();
                
               /* vectValExtend.add(""+leaveConfig.EXTEND_12_MONTH);
                vectListExtend.add("12 MONTH");
                vectValExtend.add(""+leaveConfig.EXTEND_6_MONTH);
                vectListExtend.add("6 MONTH");
                vectValExtend.add(""+leaveConfig.EXTEND_3_MONTH);
                vectListExtend.add("3 MONTH");
                vectValExtend.add(""+leaveConfig.EXTEND_2_MONTH);
                vectListExtend.add("2 MONTH");
                vectValExtend.add(""+leaveConfig.EXTEND_1_MONTH);
                vectListExtend.add("1 MONTH");*/
                //update by satrya 2013-09-30
            //menjadikann nya konfigurasi 
             Vector vectValExtend = leaveConfig!=null?leaveConfig.getAlValExtend():new Vector(); 
             Vector vectListExtend = leaveConfig!=null?leaveConfig.getAlKeyExtend():new Vector(); 
            for(int i=0; i<objectClass.size(); i++)
            {
		LeaveAlClosingList leaveAlClosingList = new LeaveAlClosingList();
                
                leaveAlClosingList = (LeaveAlClosingList)objectClass.get(i);
                
                //jumlah data
                int countData = SessLeaveClosing.getCountStockManagement(leaveAlClosingList.getEmpId());
                
                Vector listStock = new Vector();
                
                listStock = SessLeaveClosing.getAlStockManagementAktif(leaveAlClosingList.getEmpId());
                
                AlStockManagement objAl = listStock!=null && listStock.size()>0?(AlStockManagement)listStock.get(0):new AlStockManagement(); 
                
                int resultStatus = SessLeaveClosing.statusAlStockManagement(leaveAlClosingList.getEmpId(),leaveAlClosingList.getAlStockManagementId());
                
                Vector rowx = new Vector();
                
                rowx.add(""+no);
                
                String comcDate = "";                
                
                try{
                    comcDate = Formater.formatDate(leaveAlClosingList.getCommancingDate(),"yyyy-MM-dd");
                }catch(Exception e){
                    System.out.println("exception Parsing: "+e.toString());
                    comcDate = "";
                }
                
                String entDate = leaveAlClosingList.getEntitledDate()!=null ? Formater.formatDate(leaveAlClosingList.getEntitledDate(),"yyyy-MM-dd"):"-";
                
                if(countData > 1){
                    
                    if(leaveAlClosingList.getAlStockManagementId()==objAl.getOID()){
                        
                        rowx.add("<input type=\"hidden\" name=\"al_stock\" value=\""+leaveAlClosingList.getAlStockManagementId()+"\">"+
                        "<input type=\"hidden\" name=\"al_stock_id"+i+"\" value=\""+leaveAlClosingList.getAlStockManagementId()+"\">"+
                        leaveAlClosingList.getEmpNum());
                
                        rowx.add("<input type=\"hidden\" name=\"employee_id"+i+"\" value=\""+leaveAlClosingList.getEmpId()+"\">"+leaveAlClosingList.getFullName());	
                        rowx.add(""+leaveAlClosingList.getDepartment());	
                        rowx.add("<input type=\"hidden\" name=\"commencing_date"+i+"\" value=\""+comcDate+"\">"+comcDate);
                        
                    }else{
                        rowx.add("<input type=\"hidden\" name=\"al_stock\" value=\""+leaveAlClosingList.getAlStockManagementId()+"\">"+
                        "<input type=\"hidden\" name=\"al_stock_id"+i+"\" value=\""+leaveAlClosingList.getAlStockManagementId()+"\">");
                
                        rowx.add("<input type=\"hidden\" name=\"employee_id"+i+"\" value=\""+leaveAlClosingList.getEmpId()+"\">");	
                        rowx.add("");	
                        rowx.add("<input type=\"hidden\" name=\"commencing_date"+i+"\" value=\""+comcDate+"\">");
                        
                    }
                    
                }else{
                    
                        rowx.add("<input type=\"hidden\" name=\"al_stock\" value=\""+leaveAlClosingList.getAlStockManagementId()+"\">"+
                        "<input type=\"hidden\" name=\"al_stock_id"+i+"\" value=\""+leaveAlClosingList.getAlStockManagementId()+"\">"+
                        leaveAlClosingList.getEmpNum());
                
                    rowx.add("<input type=\"hidden\" name=\"employee_id"+i+"\" value=\""+leaveAlClosingList.getEmpId()+"\">"+leaveAlClosingList.getFullName());	
                    rowx.add(""+leaveAlClosingList.getDepartment());	
                    rowx.add("<input type=\"hidden\" name=\"commencing_date"+i+"\" value=\""+comcDate+"\">"+comcDate);
                }
                
                rowx.add("<input type=\"hidden\" name=\"entitle_date"+i+"\" value=\""+entDate+"\">"+entDate);
                rowx.add(""+Formater.formatNumber(leaveAlClosingList.getPrevBalance(), formatFloat));
                rowx.add(""+Formater.formatNumber(/* update by satrya 2013-10-28 leaveAlClosingList.getAlQty()*/ leaveAlClosingList.getEntitled(), formatFloat));//(leaveAlClosingList.getEntitled(), formatFloat));
                rowx.add(""+Formater.formatNumber(leaveAlClosingList.getQtyUsed(), formatFloat));
                float residu = leaveAlClosingList.getPrevBalance() + leaveAlClosingList.getEntitled() /* update by satrya 2013-10-28 leaveAlClosingList.getAlQty()*/ - leaveAlClosingList.getQtyUsed();//leaveAlClosingList.getEntitled() - leaveAlClosingList.getQtyUsed();
                rowx.add(""+Formater.formatNumber(residu, formatFloat));
                
                String drawlist = ControlCombo.draw("Extended"+i, null, null, vectValExtend, vectListExtend,"");	
                                
                boolean statusApplicationNotClose = false;                
                statusApplicationNotClose = SessLeaveClosing.getApplicationByStockManagement(leaveAlClosingList.getEmpId(), leaveAlClosingList.getAlStockManagementId());
                Date nextEntitle = leaveAlClosingList.getEntitledDate()!=null? ((Date)leaveAlClosingList.getEntitledDate().clone()) : leaveAlClosingList.getCommancingDate() ;
                                
                boolean okNextEntitle = false;                                
                if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_ANUAL||leaveConfig.getALEntitleBy()==I_Leave.AL_ENTITLE_BY_PERIOD){ /* entitle by periode */                
                    if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_ANUAL){
                        nextEntitle.setYear(nextEntitle.getYear()+1);
                        nextEntitle.setMonth(0);
                        nextEntitle.setDate(1);
                        okNextEntitle = nextEntitle.before(today);                                                                      
                     } else{
                        nextEntitle.setYear(nextEntitle.getYear()+1);
                        nextEntitle.setMonth(0);
                        nextEntitle.setDate(1);
                        okNextEntitle = nextEntitle.before(today);                                                                      
                     }
                }else{
                    nextEntitle.setYear(nextEntitle.getYear()+1);
                    okNextEntitle = nextEntitle.before(today);                                                    
                }
                if(statusApplicationNotClose==true || !okNextEntitle){
                    if(!okNextEntitle){
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_extend"+i+"\" value=\""+leaveAlClosingList.getQtyResidue()+"\" size=\"5\">");
                        rowx.add("<center>Not yet entitle</center>");
                        rowx.add("");                        
                    }else{
                        rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_extend"+i+"\" value=\""+leaveAlClosingList.getQtyResidue()+"\" size=\"5\">");
                        rowx.add("<center>Please Execution the Application !</center>");
                        rowx.add("");
                    }                    
                }else{
                   
                    if(countData > 1){
                        
                        if(resultStatus == 4){
                
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">");
                            rowx.add("");
                            rowx.add("<center>Aktif</center>");
                    
                        }else if(resultStatus == 5){
                        
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">"+" "+drawlist);
                            rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center><input type=\"checkbox\" name=\"Transfered"+i+"\" value=\"1\"></center>");
                    
                        }else if(resultStatus == 6){
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">"+" "+drawlist);
                            rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("");
                        
                        }else if(resultStatus == 3){
                            
                            rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">");
                            rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("<center>invalid<center>");
                    
                        }
                                                
                    }else{
                        
                        if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_ANUAL||leaveConfig.getALEntitleBy()==I_Leave.AL_ENTITLE_BY_PERIOD){ /* entitle by periode */
                            
                            rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">"+" "+drawlist);
                            rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                            rowx.add("");
                            
                        }else if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_COMMENCING){
                            
                            if(resultStatus == 1){
                        
                                rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">");
                                rowx.add("");
                                rowx.add("<center>Aktif</center>");
                            
                            }else if(resultStatus == 2){
                                rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">"+" "+drawlist);
                                rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                                rowx.add("");
                            
                            }else if(resultStatus == 3){
                                rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">");
                                rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                                rowx.add("<center>invalid<center>");
                            }
                            
                        }else{ /* Default menggunakan commencing date */
                            
                            if(resultStatus == 1){
                        
                                rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">");
                                rowx.add("");
                                rowx.add("<center>Aktif</center>");
                            
                            }else if(resultStatus == 2){
                                rowx.add("<input type=\"text\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">"+" "+drawlist);
                                rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                                rowx.add("");
                            
                            }else if(resultStatus == 3){
                                rowx.add("<input type=\"text\" disabled=\"true\" name=\"data_extend"+i+"\" value=\""+residu+"\" size=\"5\">");
                                rowx.add("<center><input type=\"checkbox\" name=\"Close"+i+"\" value=\"1\"></center>");
                                rowx.add("<center>invalid<center>");
                            }                            
                        }
                    }                    
                }
                no++;
		lstData.add(rowx);
            }
        }
        
        if(result != null && result.size() > 0){
        
            for(int idx = 0 ; idx < result.size() ; idx++){

                LeaveAlClosingNoStockList leaveAlNoStockClosingList = new LeaveAlClosingNoStockList();
                
                leaveAlNoStockClosingList = (LeaveAlClosingNoStockList)result.get(idx);
            
                Vector rowx = new Vector();
                rowx.add(""+no);
                rowx.add(""+leaveAlNoStockClosingList.getEmpNum());
            
                String comcDate = "";

                try{
                    comcDate = Formater.formatDate(leaveAlNoStockClosingList.getCommancingDate(),"yyyy-MM-dd");
                }catch(Exception e){
                    System.out.println("Exception Parsing "+e.toString());
                    comcDate = "";
                }
            
                String entDate = Formater.formatDate(new Date(),"yyyy-MM-dd");
            
                rowx.add("<input type=\"hidden\" name=\"employee_empty\" value=\""+leaveAlNoStockClosingList.getEmpId()+"\">"+"<input type=\"hidden\" name=\"employee_id_empty"+idx+"\" value=\""+leaveAlNoStockClosingList.getEmpId()+"\">"+leaveAlNoStockClosingList.getFullName());
                rowx.add(""+leaveAlNoStockClosingList.getDepartment());
                rowx.add("<input type=\"hidden\" name=\"commencing_date_empty"+idx+"\" value=\""+comcDate+"\">"+comcDate);
                rowx.add("<input type=\"hidden\" name=\"entitle_date_empty"+idx+"\" value=\""+entDate+"\">");
                
                rowx.add("0");
                rowx.add("0");
                rowx.add("0");
                rowx.add("0");
                rowx.add("");
                rowx.add("<input type=\"hidden\" name=\"close_empty"+idx+"\"><center><input type=\"checkbox\" name=\"executed_empty"+idx+"\" value=\"1\" ></center>");
                rowx.add("<center><font color = FF0000>No Stock</font><center>");
                no++;
                lstData.add(rowx);
            }
        }
        try{
            ctrlist.drawMe(outObj,0);
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
}
        return ""; 
        //return ctrlist.draw();
}
%>

<%

privAdd=false;

int iCommand = FRMQueryString.requestCommand(request);
SrcLeaveAppAlClosing objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();
int Type = FRMQueryString.requestInt(request, "Type");

I_Leave leaveConfig = null;  
            
try{
   leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
}catch(Exception e) {
   System.out.println("Exception : " + e.getMessage());
}

if(iCommand==Command.ACTIVATE){
    
    String[] al_stock_id = null;	    
    String[] employee_id_empty = null;
    al_stock_id = request.getParameterValues("al_stock");    
    employee_id_empty = request.getParameterValues("employee_empty");    
    Vector resultClose = new Vector();    
    
    if(al_stock_id!= null){
        
        for(int i = 0 ; i < al_stock_id.length; i++){
        
            int ix = FRMQueryString.requestInt(request, "Close"+i);
            int iy = FRMQueryString.requestInt(request, "Transfered"+i);
        
            if(ix==1 || iy==1){
            
                int status = 0;
            
                if(ix == 1 && iy == 1){
                    status = 2; // transfer dan close
                }else if(ix == 1){
                    status = 1; // close
                }else if(iy == 1){
                    status = 2; // transfer dan close
                }
            
                String tmpAlStockId = "";
                String tmpEmployeeId = "";
                String tmpExtended = "";
                String tmpDataExtended = "";
                String tmpEntitledDate = "";
                String tmpCommencingDate = "";
            
                tmpAlStockId = FRMQueryString.requestString(request, "al_stock_id"+i);
                tmpEmployeeId = FRMQueryString.requestString(request,"employee_id"+i);
                tmpExtended = FRMQueryString.requestString(request,"Extended"+i);
                tmpDataExtended = FRMQueryString.requestString(request,"data_extend"+i);
                tmpEntitledDate = FRMQueryString.requestString(request,"entitle_date"+i);
                tmpCommencingDate = FRMQueryString.requestString(request,"commencing_date"+i);
                    
                long  AlStockId = 0;
                long  EmployeeId = 0;
                int   Extended = 0;
                float   DataExtended = 0;
            
                try{
                    AlStockId = Long.parseLong(tmpAlStockId);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
            
                try{
                    EmployeeId = Long.parseLong(tmpEmployeeId);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
            
                try{
                    Extended=Integer.parseInt(tmpExtended);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
            
                try{
                    DataExtended = Float.parseFloat(tmpDataExtended);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
            
                LeaveAlClosing leaveAlClosing = new LeaveAlClosing();
            
                leaveAlClosing.setStockId(AlStockId);
                leaveAlClosing.setEmployeeId(EmployeeId);
                leaveAlClosing.setExpiredDate(Extended);
                leaveAlClosing.setExtended(DataExtended);
                leaveAlClosing.setCommencingDate(tmpCommencingDate);
                leaveAlClosing.setEntitledDate(tmpEntitledDate);
                leaveAlClosing.setStatus(status);
                resultClose.add(leaveAlClosing);
            }
        }
    }
    
    if(employee_id_empty!=null){
        
    for(int x = 0; x < employee_id_empty.length ; x++){

          int ix = FRMQueryString.requestInt(request, "executed_empty"+x);
          int status = 0;
          
          if(ix == 1){
          
          status = 1;
          
          String tmpAlStockId = "";
          String tmpEmployeeId = "";
          String tmpExtended = "";
          String tmpDataExtended = "";
          String tmpEntitledDate = "";
          String tmpCommencingDate = "";
          
          tmpAlStockId = FRMQueryString.requestString(request, "al_stock_id_empty"+x);
          tmpEmployeeId = FRMQueryString.requestString(request,"employee_id_empty"+x);
          tmpExtended = FRMQueryString.requestString(request,"Extended_empty"+x);
          tmpDataExtended = FRMQueryString.requestString(request,"data_extend_empty"+x);
          tmpEntitledDate = FRMQueryString.requestString(request,"entitle_date_empty"+x);
          tmpCommencingDate = FRMQueryString.requestString(request,"commencing_date_empty"+x);
          
          long  AlStockId = 0;
          long  EmployeeId = 0;
          int   Extended = 0;
          float   DataExtended = 0;
          
          try{
              //update by satrya 2013-11-09
              if(tmpAlStockId!=null && tmpAlStockId.length()>0){
                AlStockId = Long.parseLong(tmpAlStockId);
              }
          }catch(Exception e){
                System.out.println("Exception "+e.toString());
          }
            
            try{
              //update by satrya 2013-11-09
              if(tmpEmployeeId!=null && tmpEmployeeId.length()>0){ 
                EmployeeId = Long.parseLong(tmpEmployeeId);
              }
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
               //update by satrya 2013-11-09
              if(tmpExtended!=null && tmpExtended.length()>0){ 
                Extended=Integer.parseInt(tmpExtended);
              }
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            try{
                 //update by satrya 2013-11-09
               if(tmpDataExtended!=null && tmpDataExtended.length()>0){
                DataExtended = Float.parseFloat(tmpDataExtended);
               }
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            LeaveAlClosing leaveAlClosing = new LeaveAlClosing();
            
            leaveAlClosing.setStockId(AlStockId);
            leaveAlClosing.setEmployeeId(EmployeeId);
            leaveAlClosing.setExpiredDate(Extended);
            leaveAlClosing.setExtended(DataExtended);
            leaveAlClosing.setCommencingDate(tmpCommencingDate);
            leaveAlClosing.setEntitledDate(tmpEntitledDate);
            leaveAlClosing.setStatus(status);
            resultClose.add(leaveAlClosing);

            }
        }
    }
    
    if(resultClose.size()>0){        
        System.out.println("----------------------- CLOSING AL START --------------------------");        
        if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_ANUAL||leaveConfig.getALEntitleBy()== I_Leave.AL_ENTITLE_BY_PERIOD){ /* menggunakan closing by period */
            SessLeaveClosing.ProcessClosingPeriod (resultClose,leaveConfig);
        }else if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_COMMENCING){ /* Menggunakan closing by commencing date */
            SessLeaveClosing.ProcessClosing(resultClose);
        }else{ /* Default gunakan closing by commencing date */
            SessLeaveClosing.ProcessClosing(resultClose);
        }
    }
}
//last closing

FrmSrcLeaveAppAlClosing objFrmSrcLeaveAppAlClosing = new FrmSrcLeaveAppAlClosing(request, objSrcLeaveAppAlClosing);
objFrmSrcLeaveAppAlClosing.requestEntityObject(objSrcLeaveAppAlClosing);


if(iCommand==Command.LOAD){
    //Untuk melakukan proses balancing di awal untuk hardrock, karena mereka memberikan data berupa excel
    SessLeaveClosing.procesDataFirstLL();
    SessLeaveClosing.procesDataFirstAL();
}

if(iCommand==Command.BACK)
{        
	objFrmSrcLeaveAppAlClosing= new FrmSrcLeaveAppAlClosing(request, objSrcLeaveAppAlClosing);
	try{				
		objSrcLeaveAppAlClosing = (SrcLeaveAppAlClosing) session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION);			
		if(objSrcLeaveAppAlClosing == null)
		{
			objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();
		}		
	}catch (Exception e){
		objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing(); 
	}
}


Vector resultSearch = new Vector();
Vector result = new Vector();

/* Search by parameter */
if(Type==SEARCH){
    
    if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_ANUAL){ /* entitle by periode */
        
        resultSearch = SessLeaveClosing.getEmployeeActiveByPeriod(objSrcLeaveAppAlClosing);
        result = SessLeaveClosing.getEmployeeNotYetHaveStockByPeriod(objSrcLeaveAppAlClosing,leaveConfig);
        
    }else if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_COMMENCING){ /* entitle by commencing */
        
        //resultSearch = SessLeaveClosing.getEmployeeActive(objSrcLeaveAppAlClosing);
        /*
 * update by satrya 2013-09-30
 * lisnya sudah di pakai lewat konfigurasi
        SessLeaveClosing.getEmployeeActiveByPeriode(objSrcLeaveAppAlClosing,leaveConfig)*/
        resultSearch = leaveConfig.listgetEmployeeActiveByPeriode(objSrcLeaveAppAlClosing, leaveConfig)/*SessLeaveClosing.getEmployeeActiveByPeriode(objSrcLeaveAppAlClosing,leaveConfig)*/;
        result = SessLeaveClosing.getEmployeeNotYetHaveStock(objSrcLeaveAppAlClosing,leaveConfig);
        
    }else if(leaveConfig.getALEntitleBy()==I_Leave.AL_ENTITLE_BY_PERIOD){

        resultSearch = SessLeaveClosing.getEmployeeActiveByPeriod2(objSrcLeaveAppAlClosing);
        result = SessLeaveClosing.getEmployeeNotYetHaveStockByPeriod(objSrcLeaveAppAlClosing,leaveConfig);

    }else{/* jika tidak keduanya, maka default pakai yang commencing date */
        resultSearch = SessLeaveClosing.getEmployeeActive(objSrcLeaveAppAlClosing);
        result = SessLeaveClosing.getEmployeeNotYetHaveStock(objSrcLeaveAppAlClosing);
    }
    
}else if(Type==SEARCH_ALL){
    if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_ANUAL||leaveConfig.getALEntitleBy()==I_Leave.AL_ENTITLE_BY_PERIOD){ /* entitle by periode */
        resultSearch = SessLeaveClosing.getEmployeeActiveALLByPeriod();
        result = SessLeaveClosing.getEmployeeNotYetHaveStockAllByPeriod();
    }else if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_BY_COMMENCING){ /* entitle by commencing */
        resultSearch = SessLeaveClosing.getEmployeeActiveALL();
        result = SessLeaveClosing.getEmployeeNotYetHaveStockAll();
    }else{/* jika tidak keduanya maka digunakan  default entitle by commencing */
        resultSearch = SessLeaveClosing.getEmployeeActiveALL();
        result = SessLeaveClosing.getEmployeeNotYetHaveStockAll();
    }
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - AL Leave Closing</title>
<script language="JavaScript">
<!--
function cmdClosing() {
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value ="<%=String.valueOf(Command.ACTIVATE)%>";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action ="leave_al_closing.jsp";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}    

function cmdProcesFirst(){
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value ="<%=String.valueOf(Command.LOAD)%>";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action ="leave_al_closing.jsp";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}
function cmdSearch(){
        getCommancingDateStart();
        getCommancingDateEnd();
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.Type.value = <%= SEARCH %>;
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value="<%=String.valueOf(Command.LIST)%>";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action="leave_al_closing.jsp";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}

function cmdSearchAll(){
        getCommancingDateStart();
        getCommancingDateEnd();
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.Type.value = <%= SEARCH_ALL %>;
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value="<%=String.valueOf(Command.LIST)%>";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action="leave_al_closing.jsp";        
	document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}

function SetAllCheckBoxes(FormName, FieldName, CheckValue, maxList, maxListEmpty)
{
	if(!document.forms[FormName])
		return;
        
        for(var i=0;i< maxList;i++) {                   
          var chkFieldName= FieldName+""+i;
	var objCheckBoxes = document.forms[FormName].elements[chkFieldName];
	if(!objCheckBoxes)
		continue;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
        }
        
        for(var i=0;i< maxListEmpty;i++) {                   
          var chkFieldName= "executed_empty"+i;
	var objCheckBoxes = document.forms[FormName].elements[chkFieldName];
	if(!objCheckBoxes)
		continue;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
        }                
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
//-------------------------------------------

function fnTrapKD(){
   if (event.keyCode == 13) {
	document.all.aSearch.focus();
	cmdSearch();
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
                  Employee &gt; Leave &gt; AL balance closing <!-- #EndEditable -->
                  </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
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
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%															
											/*Vector department_value = new Vector(1,1);
											Vector department_key = new Vector(1,1);
                                                                                        String where;
                                                                                        String depException = "";
                                                                                         department_key.add("select..");
												department_value.add("");
                                                                                        long oidHRD = 0;
                                                                                        
                                                                                        try{
                                                                                            oidHRD = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
                                                                                        }catch(Exception E){
                                                                                            System.out.println("[exception] Sys Prop OID_HRD_DEPARTMENT [not set] "+E.toString());
                                                                                        }
                                                                                        
                                                                                        String joinDept = "";

                                                                                        try {
                                                                                                joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT_REPORT_EXCEPTION");
                                                                                        } catch (Exception E) {
                                                                                                joinDept = "";
                                                                                                System.out.println("[exception] " + E.toString());
                                                                                        }


                                                                                        boolean departmentTrue = false;
                                                                                        if(joinDept.length() > 0 && joinDept.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED) != 0){
                                                                                                                                                                                            
                                                                                                Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                String[] grp = (String[]) depGroup.get(0);                                                                              

                                                                                                if (grp.length > 0) {

                                                                                                    depException = depException + " ( ";
                                                                                                    for (int g = 0; g < grp.length; g++) {
                                                                                                        
                                                                                                        if(Long.parseLong(grp[g])!=departmentOid){
                                                                                                            departmentTrue = true;
                                                                                                        }                                                                                                        

                                                                                                        if (g == 0) {
                                                                                                            depException = depException + grp[g];
                                                                                                        } else {
                                                                                                            depException = depException + "," + grp[g];
                                                                                                        }

                                                                                                     }
                                                                                                     depException = depException + " ) ";
                                                                                                }
                                                                                            
                                                                                        }else{
                                                                                                depException = "";
                                                                                                departmentTrue = true;
                                                                                        }
                                                                                        
                                                                                        
                                                                                        if(departmentOid==oidHRD){                                                                                            
                                                                                                                                                                      
                                                                                            
                                                                                            if(depException.length() > 0){
                                                                                                
                                                                                                where = PstDepartment.TBL_HR_DEPARTMENT +"."+ PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" NOT IN "+depException;
                                                                                                
                                                                                            }else{
                                                                                                
                                                                                                where="";
                                                                                                
                                                                                            }
                                                                                            
                                                                                        }else{
                                                                                            
                                                                                            where = "j."+"DEPARTMENT_ID = "+departmentOid;
                                                                                            
                                                                                        }
											
											Vector listDept = PstDepartment.list(0, 0, where, "DEPARTMENT");                                                        
											String selectValueDepartment = ""+objSrcLeaveAppAlClosing.getDepartmentId();
											for (int i = 0; i < listDept.size(); i++) 
											{
												Department dept = (Department) listDept.get(i);
												department_key.add(dept.getDepartment());
												department_value.add(String.valueOf(dept.getOID()));
											}
                                                                                        */
											%>
                                            
                                                                                                <%

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
                                                                                                    String selectValueDepartment = ""+objSrcLeaveAppAlClosing.getDepartmentId(); 

                                                                                                %>
                                            <%=ControlCombo.draw(objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_DEPARTMENT],"elementForm", null, selectValueDepartment, dept_value, dept_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
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
                                                //di hidde oleh satrya 2013-02-16
                                                //Vector vectCat = SessLeaveClosing.getEmpCategoryEntitle();
                                                // Vector listCategory = PstEmpCategory.listAll();
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
                                                cat_value.add("0");
                                                cat_key.add("-selected-");
                                                for(int idxCat = 0 ; idxCat < listCategory.size(); idxCat++){ 
                                                    EmpCategory empCategory = (EmpCategory)listCategory.get(idxCat); 
                                                    
                                                    cat_value.add(""+empCategory.getOID());
                                                    cat_key.add(""+empCategory.getEmpCategory());
                                                }
                                                
                                                String selectValueSection = ""+objSrcLeaveAppAlClosing.getCategoryId();
                                                
					    %>
                                             <%=ControlCombo.draw(objFrmSrcLeaveAppAlClosing.fieldNames[objFrmSrcLeaveAppAlClosing.FRM_FIELD_CATEGORY],"elementForm", null, selectValueSection, cat_value, cat_key ," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                          </td>
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
											Vector listSec = PstSection.list(0, 0, "", " SECTION ");                                                          
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
                                          <%=ControlDatePopup.writeDate(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_START],objSrcLeaveAppAlClosing.getEmpCommancingDateStart()==null ? Formater.formatDate("1990-01-01","yyyy-MM-dd") : objSrcLeaveAppAlClosing.getEmpCommancingDateStart(), "getCommancingDateStart()")%> To
                                          <%=ControlDatePopup.writeDate(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_END],objSrcLeaveAppAlClosing.getEmpCommancingDateEnd()==null ? new Date() : objSrcLeaveAppAlClosing.getEmpCommancingDateEnd(), "getCommancingDateEnd()")%>
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
                                                <% 
                                                //update by satrya 2012-12-20
                                                //if(departmentTrue){
                                                %>
                                                <td width="25px" ><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search closing employee" ></a></td>
                                                <td width="5px"></td>                                                
                                                <td width="30px" class="command" nowrap><a href="javascript:cmdSearch()">Search</a></td>  
                                                <td width="15px"></td>                                                
                                                <% 
                                                 //update by satrya 2012-12-20
                                                //}
                                                //if(departmentOid==oidHRD){
                                                %>
                                                <td width="30px" ><a href="javascript:cmdSearchAll()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search closing all employee" ></a></td>
                                                <td width="10px"></td>                                                                                                                                                                                             
                                                <td width="100px" class="command" nowrap><a href="javascript:cmdSearchAll()">Search All</a></td>   
                                                <td width="5px"></td>         
                                                <%
                                                //}
                                                %>
                                                <td width="25px" ><a href="javascript:cmdProcesFirst()"></a></td>
                                                <td width="15px"></td>
                                                <td width="100px" class="command" nowrap></td>                                                                                                     
                                              </tr>                                              
                                            </table>
                                          </td>
                                        </tr>
                                         <tr>
                                        <td colspan="3">
                                        <% 
                                           if((resultSearch !=null && resultSearch.size()>0 ) || (result != null && result.size() > 0)) {
                                                out.println(drawList(out, resultSearch,result,leaveConfig));
                                              
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
                                            <table border="0" cellpadding="0" cellspacing="0" width="225">
                                              <tr> 
                                                <td width="25px" ><a href="javascript:cmdClosing()" onMouseOut="MM_swapImgRestore()" onMouseOut="MM_swapImgRestore()" ><img name="Image300" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Close Period" ></a></td>
                                                <td width="5px"></td>
                                                <td width="30px" class="command" nowrap><a href="javascript:cmdClosing()">Closing AL</a></td>                                                                                                                                                      
                                                <td width="15px"></td>
                                                <td width="30px" ></td>
                                                <td width="10px"></td>
                                                <td width="10px"></td>                                                                                                     
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