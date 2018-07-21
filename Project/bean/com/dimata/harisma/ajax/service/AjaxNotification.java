/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.service;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.service.notification.Notification;
import com.dimata.harisma.entity.service.notification.NotificationRecipient;
import com.dimata.harisma.entity.service.notification.PstNotification;
import com.dimata.harisma.entity.service.notification.PstNotificationRecipient;
import com.dimata.harisma.form.service.notification.CtrlNotification;
import com.dimata.harisma.form.service.notification.FrmNotification;
import com.dimata.harisma.utility.machine.NotificationService;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author khirayinnura
 */
public class AjaxNotification extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //DATATABLES
    private String searchTerm;
    private String colName;
    private int colOrder;
    private String dir;
    private int start;
    private int amount;
    
    //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    
    //LONG
    private long oid = 0;
    private long oidReturn = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String status = "";
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
        this.status = FRMQueryString.requestString(request, "FRM_FIELD_STATUS");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
	
	//INT
	this.iCommand = FRMQueryString.requestCommand(request);
	this.iErrCode = 0;
	
	
	
	//OBJECT
	this.jSONObject = new JSONObject();
	
	switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
	    break;
                
            case Command.EDIT :
		commandEdit(request);
	    break;
		
	    case Command.LIST :
		commandList(request, response);
	    break;
	    
            case Command.UPDATE :
		commandUpdate(request, response);
	    break;
		
	    case Command.DELETEALL : //delAll
		commandDeleteAll(request);
	    break;
            
            case Command.DELETE :
                commandDelete(request);
            break;    
                
	    default : commandNone(request);
	}
	
	
	try{
	    
	    this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
            this.jSONObject.put("FRM_FIELD_STATUS", this.status);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
	
	response.getWriter().print(this.jSONObject);
	
    }
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("show_notif_form")){
	    this.htmlReturn = notifForm();
	}
    }
    
    public void commandEdit(HttpServletRequest request){
	CtrlNotification ctrlNotification = new CtrlNotification(request);
	this.iErrCode = ctrlNotification.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlNotification.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlNotification ctrlNotification = new CtrlNotification(request);
	this.iErrCode = ctrlNotification.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlNotification.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandUpdate(HttpServletRequest request, HttpServletResponse response){
        if (this.dataFor.equals("offNotification")){
            String sqlUpdate = "UPDATE "+PstNotification.TBL_HR_NOTIFICATION+" SET ";
            sqlUpdate += PstNotification.fieldNames[PstNotification.FLD_STATUS]+"=0";
            //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
            sqlUpdate += " WHERE "+PstNotification.fieldNames[PstNotification.FLD_NOTIFICATION_ID]+"="+this.oid;
            try {
                DBHandler.execUpdate(sqlUpdate);
            } catch (Exception exc){}
        } else if (this.dataFor.equals("onNotification")){
            String sqlUpdate = "UPDATE "+PstNotification.TBL_HR_NOTIFICATION+" SET ";
            sqlUpdate += PstNotification.fieldNames[PstNotification.FLD_STATUS]+"=1";
            //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
            sqlUpdate += " WHERE "+PstNotification.fieldNames[PstNotification.FLD_NOTIFICATION_ID]+"="+this.oid;
            try {
                DBHandler.execUpdate(sqlUpdate);
            } catch (Exception exc){}
        } else {
            CtrlNotification ctrlNotification = new CtrlNotification(request);
            this.iErrCode = ctrlNotification.action(this.iCommand, this.oid, this.oidDelete);
            String stat = processService(request, response, this.status);
            this.htmlReturn = ""+stat;
        }
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlNotification ctrlNotification = new CtrlNotification(request);
	this.iErrCode = ctrlNotification.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlNotification.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlNotification ctrlNotification = new CtrlNotification(request);
	this.iErrCode = ctrlNotification.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlNotification.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }    
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("list_notif")){
	    String[] cols = { PstNotification.fieldNames[PstNotification.FLD_NOTIFICATION_ID],
                PstNotification.fieldNames[PstNotification.FLD_MODUL_NAME],
		PstNotification.fieldNames[PstNotification.FLD_TYPE],
		PstNotification.fieldNames[PstNotification.FLD_TIME_DISTANCE],
		PstNotification.fieldNames[PstNotification.FLD_SUBJECT],
		PstNotification.fieldNames[PstNotification.FLD_TEXT],
		PstNotification.fieldNames[PstNotification.FLD_STATUS],
		PstNotification.fieldNames[PstNotification.FLD_TARGET_EMPLOYEE]};

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
        
    }
    public String processService(HttpServletRequest request, HttpServletResponse response, String status) {
        String strStatusManualAttd = "";
        NotificationService notificationService = NotificationService.getInstance(true);
       
            //if (iCommand == Command.ACTIVATE){
            Date nowDate = new Date();
            Date dtNow = new Date(nowDate.getYear(), nowDate.getMonth(), nowDate.getDate());
            if (status != null) {
                if (status.equalsIgnoreCase("Run")) {
                    try {
                        notificationService.startTransfer(new Date(), new Date());
                    } catch (Exception e) {
                        System.out.println("\t Exception manualAttd.startAnalisa() = " + e);
                    }
                } else if (status.equalsIgnoreCase("Stop")) {
                    try {
                        notificationService.stopTransfer();


                    } catch (Exception e) {
                        System.out.println("\t Exception svrmgrMachine.stopAnalisa() = " + e);
                    }
                }
            }///comand run

            if (notificationService.getStatus()) {
                //strStatusManualAttd = "Run";
                strStatusManualAttd = "<i id=\"run\" class=\"fa fa-stop\">&nbspStop</i>";

            } else {
                strStatusManualAttd = "<i id=\"run\" class=\"fa fa-play-circle-o\">&nbspRun</i>";;
            }
        
        return strStatusManualAttd;
    }
    //LIST ACADEMIC YEAR
    public JSONObject listDataTables (HttpServletRequest request, HttpServletResponse response, String[] cols, String dataFor, JSONObject result){
        this.searchTerm = FRMQueryString.requestString(request, "sSearch");
        int amount = 10;
        int start = 0;
        int col = 0;
        String dir = "asc";
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        
        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
            if (amount < 10) {
                amount = 10;
            }
        }
        if (sCol != null) {
            col = Integer.parseInt(sCol);
            if (col < 0 )
                col = 0;
        }
        if (sdir != null) {
            if (!sdir.equals("asc"))
            dir = "desc";
        }
        
	
        
        String whereClause = "";
        
        if(dataFor.equals("list_notif")){
	    whereClause += " ("+PstNotification.fieldNames[PstNotification.FLD_MODUL_NAME]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstNotification.fieldNames[PstNotification.FLD_SUBJECT]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("list_notif")){
	    total = PstNotification.getCount(whereClause);
	}
        
        
        this.amount = amount;
       
        this.colName = colName;
        this.dir = dir;
        this.start = start;
        this.colOrder = col;
        
        try {
            result = getData(total, request, dataFor);
        } catch(Exception ex){
            System.out.println(ex);
        }
       
       return result;
    }
    
    public JSONObject getData(int total, HttpServletRequest request, String datafor){
        
        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        Notification notification = new Notification();
	
	String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	    if(datafor.equals("list_notif")){
		whereClause += " ("+PstNotification.fieldNames[PstNotification.FLD_MODUL_NAME]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstNotification.fieldNames[PstNotification.FLD_SUBJECT]+" LIKE '%"+this.searchTerm+"%')";
	    }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("list_notif")){
	    listData = PstNotification.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("list_notif")){
		notification = (Notification) listData.get(i);
                
                
                String checkButton = "<input type='checkbox' name='"+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_NOTIFICATION_ID]+"' class='"+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_NOTIFICATION_ID]+"' value='"+notification.getOID()+"'>" ;
                    ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
                ja.put(""+PstNotification.modulNames[notification.getModulName()]);
                
                String tipe = notification.getType();
                String []partsOfTipe = tipe.split("-");
                String type1 = partsOfTipe[0]; 
                String type2 = partsOfTipe[1];
                String type3 = partsOfTipe[2];
                
		ja.put(""+(type1.equals("1")?"Email":"")+"<br>"
                        +(type2.equals("1")?"SMS":"")+"<br>"
                        +(type3.equals("1")?"Alert":""));
                
                String timeDistance = notification.getTimeDistance();
                String []partsOftimeDistance = timeDistance.split("-");
                String timeDistance1 = partsOftimeDistance[0]; 
                String timeDistance2 = partsOftimeDistance[1];
                String timeDistance3 = partsOftimeDistance[2];
                String timeDistance4 = partsOftimeDistance[3];
                
		ja.put(""+(timeDistance1.equals("1")?"- 30 Days":"")+"<br>"
                        +(timeDistance2.equals("1")?"- 7 Days":"")+"<br>"
                        +(timeDistance3.equals("1")?"- 1 Day":"")+"<br>"
                        +(timeDistance4.equals("1")?"- 0 Day":""));
                ja.put(""+notification.getTime());
                ja.put(""+notification.getSubject());
                
                String text = notification.getText();
                text = text.replaceAll("&ltbr&gt", "&#13;&#10");
                text = text.replaceAll("(?<=&lt).*?(?=&gt)", "");
                text = text.replaceAll("&lt", "");
                text = text.replaceAll("&gt", "");
		ja.put("<textarea disabled id='text' style='width: 100%;height: 50px'>"+text+"</textarea>");
                
                Vector listNotificationRecipient = PstNotificationRecipient.list(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 0 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\"", ""); 
                if (listNotificationRecipient.size()>0){
                    for (int xx = 0 ; xx<listNotificationRecipient.size(); xx++ ){
                        NotificationRecipient notificationRecipient = (NotificationRecipient) listNotificationRecipient.get(xx);
                        
                        ja.put(""+notificationRecipient.getRecipientsEmail()+",");
                    }
                }else{
                    ja.put("-");
                }
                
                Vector listNotificationSms = PstNotificationRecipient.list(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 4 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\"", ""); 
                if (listNotificationSms.size()>0){
                    for (int xx = 0 ; xx<listNotificationSms.size(); xx++ ){
                        NotificationRecipient notificationRecipient = (NotificationRecipient) listNotificationSms.get(xx);
                        
                        ja.put(""+notificationRecipient.getRecipientsSms()+",");
                    }
                }else{
                    ja.put("-");
                }
                
                Vector listNotificationRecipientCC = PstNotificationRecipient.list(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 1 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\"", ""); 
                if (listNotificationRecipientCC.size()>0){
                    for (int xx = 0 ; xx<listNotificationRecipientCC.size(); xx++ ){
                        NotificationRecipient notificationRecipient = (NotificationRecipient) listNotificationRecipientCC.get(xx);
                        ja.put(""+notificationRecipient.getRecipientsEmail()+",");
                    }
                }else{
                    ja.put("-");
                }
                
                Vector listNotificationRecipientBCC = PstNotificationRecipient.list(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 2 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\"", ""); 
                if (listNotificationRecipientBCC.size()>0){
                    for (int xx = 0 ; xx<listNotificationRecipientBCC.size(); xx++ ){
                        NotificationRecipient notificationRecipient = (NotificationRecipient) listNotificationRecipientBCC.get(xx);
                        ja.put(""+notificationRecipient.getRecipientsEmail()+",");
                    }
                }else{
                    ja.put("-");
                }
                String buttonOnOff = "";
                String buttonUpdate = "<button class='btn btn-warning btn-edit-general btn-xs' data-oid='"+notification.getOID()+"' data-for='show_notif_form' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                if (notification.getStatus() == 1){
                    buttonOnOff +="<button class='btn btn-success btnonoff btn-xs' data-oid='"+notification.getOID()+"' data-for='offNotification' type='button' data-toggle='tooltip' data-placement='top' title='Turn Off'><i class='fa fa-power-off'></i></button> ";
                } else {
                    buttonOnOff +="<button class='btn btn-danger btnonoff btn-xs' data-oid='"+notification.getOID()+"' data-for='onNotification' type='button' data-toggle='tooltip' data-placement='top' title='Turn On'><i class='fa fa-power-off'></i></button> ";
                }
		ja.put(""+(notification.getTargetEmployee() == 1 ?"YES":"NO"));
		ja.put(buttonOnOff + buttonUpdate + "<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+notification.getOID()+"' data-for='deleteAwardSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
		
		//String buttonUpdate = "";
		//if(true/*privUpdate*/){
		//    buttonUpdate = "<button class='btn btn-warning btn-edit-general' data-oid='"+notification.getOID()+"' data-for='show_notif_form' type='button'><i class='fa fa-pencil'></i> Edit</button> ";
		//}
		//ja.put(buttonUpdate+"<div class='btn btn-default' type='button'><input type='checkbox' name='"+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_NOTIFICATION_ID]+"' class='"+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_NOTIFICATION_ID]+"' value='"+notification.getOID()+"'> Delete</div>");
		array.put(ja);
	    }
        }
        
        totalAfterFilter = total;
        
        try {
            result.put("iTotalRecords", total);
            result.put("iTotalDisplayRecords", totalAfterFilter);
            result.put("aaData", array);
        } catch (Exception e) {

        }
        
        return result;
    }
    
    public String notifForm(){
	
	//CHECK DATA
	Notification notification = new Notification();
        	
        if(this.oid != 0){
	    try{
		notification = PstNotification.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        String tipe = notification.getType();
        String type1 = "0"; 
        String type2 = "0";
        String type3 = "0";
        if(!(tipe.equals(""))){
            String []partsOfTipe = tipe.split("-");
            type1 = partsOfTipe[0]; 
            type2 = partsOfTipe[1];
            type3 = partsOfTipe[2];
        }
        
        String timeDistance1 = "0"; 
        String timeDistance2 = "0"; 
        String timeDistance3 = "0"; 
        String timeDistance4 = "0"; 
        String timeDistance = notification.getTimeDistance();
        if(!(timeDistance.equals(""))){
            String []partsOftimeDistance = timeDistance.split("-");
            timeDistance1 = partsOftimeDistance[0]; 
            timeDistance2 = partsOftimeDistance[1];
            timeDistance3 = partsOftimeDistance[2];
            timeDistance4 = partsOftimeDistance[3];
        }
        
//        Hashtable hListNotifRecipient = PstNotificationRecipient.hListEmail(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = 0 AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = " + notification.getOID() + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD] + " = 0", ""); 
        String emailRec = PstNotificationRecipient.listStringEmail(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = 0 AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = " + notification.getOID() + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD] + " = 1", ""); 
        String smsRec = PstNotificationRecipient.listStringSms(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = 4 AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = " + notification.getOID() + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD] + " = 1", ""); 
//        Vector employeeListX = PstEmployee.list(0, 0, "", "");
//        String htmlSRec = "";
//        for (int i = 0; i < employeeListX.size(); i++) {
//            Employee employee = (Employee) employeeListX.get(i);
//            String email = (employee.getEmailAddress()!=null?" <"+employee.getEmailAddress()+">":" ");
//
//            if (hListNotifRecipient.get(""+employee.getEmailAddress()) != null) { 
//                   htmlSRec += "<option selected value='"+employee.getEmailAddress()+"'>"+employee.getFullName()+email+"</option>";
//            } else { 
//                   htmlSRec += "<option value='"+employee.getEmailAddress()+"'>"+employee.getFullName()+email+"</option>";
//            }
//         }
        
//        Hashtable hListNotifRecipientCC = PstNotificationRecipient.hListEmail(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 1 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\" AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD] + " = \"" + 0 + "\"", ""); 
        String emailcc = PstNotificationRecipient.listStringEmail(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 1 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\" AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD] + " = \"" + 1 + "\"", ""); 
//        String htmlSCc = "";
//        for (int i = 0; i < employeeListX.size(); i++) {
//            Employee employee = (Employee) employeeListX.get(i);
//            String email = (employee.getEmailAddress()!=null?" <"+employee.getEmailAddress()+">":" ");
//            if (hListNotifRecipientCC.get(""+employee.getEmailAddress()) != null) { 
//                   htmlSCc += "<option selected value='"+employee.getEmailAddress()+"'>"+employee.getFullName()+email+"</option>";
//            } else { 
//                   htmlSCc += "<option value='"+employee.getEmailAddress()+"'>"+employee.getFullName()+email+"</option>";
//            }
//         }
//        Hashtable hListNotifRecipientBCC = PstNotificationRecipient.hListEmail(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 2 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\" AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD] + " = \"" + 0 + "\"", ""); 
        String emailbcc = PstNotificationRecipient.listStringEmail(0, 0, PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + 2 + " AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = \"" + notification.getOID() + "\" AND " +  PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD] + " = \"" + 1 + "\"", ""); 
//        String htmlSBcc = "";
//        for (int i = 0; i < employeeListX.size(); i++) {
//            Employee employee = (Employee) employeeListX.get(i);
//            String email = (employee.getEmailAddress()!=null?" <"+employee.getEmailAddress()+">":" ");
//            if (hListNotifRecipientBCC.get(""+employee.getEmailAddress()) != null) { 
//                   htmlSBcc += "<option selected value='"+employee.getEmailAddress()+"'>"+employee.getFullName()+email+"</option>";
//            } else { 
//                   htmlSBcc += "<option value='"+employee.getEmailAddress()+"'>"+employee.getFullName()+email+"</option>";
//            }
//         }
            
	String returnData = ""
        + "<input type='hidden' name='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_NOTIFICATION_ID]+"'  id='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_NOTIFICATION_ID]+"' class='form-control' value='"+notification.getOID()+"'>"
        + "<input type='hidden' name='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_STATUS]+"'  id='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_STATUS]+"' class='form-control' value='"+notification.getStatus()+"'>"
	+ "<div class='row'>"
//	    + "<div class='col-md-4'>"
//                + "<div class='form-group'>"
//		    + "<label>Recipient</label>"
//		    + "<select name='recipient' multiple style='width:100%'>"
//                    + htmlSRec
//                    + "</select>"
//                    +"<input name='recipientAdd' id='emailX' value='"+emailRec+"' style='width:100%'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>cc</label>"
//		    + "<select name='recipientCC' multiple style='width:100%'>"
//                    + htmlSCc
//                    + "</select>"
//                    +"<input name='ccAdd' id='emailX' value='"+emailcc+"' style='width:100%'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>bcc</label>"
//		    + "<select name='recipientBCC' multiple style='width:100%'>"
//                    + htmlSBcc
//                    + "</select>"
//                    +"<input name='bccAdd' id='emailX' value='"+emailbcc+"' style='width:100%'>"
//		+ "</div>"
//	    + "</div>"
//            + "<div class='col-md-8'>"
                + "<div class='form-group'>"
                    + "<label>E-Mail</label>"
                    + "<input type='text' name='recipientAdd' id='email' class='form-control' value='"+emailRec+"'>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Phone Number</label>"
                    + "<input type='text' name='sms' id='sms' class='form-control' value='"+smsRec+"'>"
                + "</div>"
                + "<div class='checkbox'>"
                    + "<label>"
                        + "<input type='checkbox' name='"+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_TARGET_EMPLOYEE]+"' id='checkAllEmployee' value='1' "+(notification.getTargetEmployee() == 1 ?"checked":"")+" /> All Employee"
                    + "</label>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>CC</label>"
                    +"<input name='ccAdd' class='form-control' id='emailX' value='"+emailcc+"' style='width:100%'>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>BCC</label>"
                    +"<input name='bccAdd' class='form-control' id='emailX' value='"+emailbcc+"' style='width:100%'>"
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Mutation Type *</label>"
                    + "<select class=\"form-control\" name=\""+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_MODUL_NAME]+"\" id=\""+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_MODUL_NAME]+"\" >"
                        + "<option value=\"\">-SELECT-</option>";
                        for (int i=0; i< 4; i++){
                            if (notification.getModulName() == i){
                                returnData += "<option value='"+i+"' selected>"+PstNotification.modulNames[i]+"</option>";
                            } else {
                                returnData += "<option value='"+i+"'>"+PstNotification.modulNames[i]+"</option>";                                
                            }
                        }
                        returnData += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Subject</label>"
                    + "<input type='text' name='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_SUBJECT]+"'  id='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_SUBJECT]+"' class='form-control' value='"+notification.getSubject()+"'>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Time Send</label>"
                    + "<div id='timepicker' class='input-group'>"
                        + "<input type='text' class='form-control' name='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_TIME]+"' value='"+notification.getTime()+"'></input>"
                        + "<span class='input-group-addon'>"
                            + "<i class='fa fa-clock-o'></i>"
                        + "</span>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='col-md-6'>"
                        + "<div class='form-group'>"
                            + "<label>Type</label>"
                                + "<div class='checkbox'>"
                                    + "<label><input type='checkbox' name='type_email' id='type_email' value='1' "+(type1.equals("1")?"checked":"")+" />Email</label>"
                                + "</div>"
                                + "<div class='checkbox'>"
                                    + "<label><input type='checkbox' name='type_sms' id='type_sms' value='1' "+(type2.equals("1")?"checked":"")+" />SMS</label>"
                                + "</div>"
                                + "<div class='checkbox'>"
                                    + "<label><input type='checkbox' name='type_alert' id='type_alert' value='1' "+(type3.equals("1")?"checked":"")+" />Alert</label>"
                                + "</div>"
                        + "</div>"
                    + "</div>"
                    + "<div class='col-md-6'>"
                        + "<div class='form-group'>"
                            + "<label>Time Distance</label>"
                            + "<div class='checkbox'>"
                                + "<label><input type='checkbox' name='time30' id='time30' value='1' "+(timeDistance1.equals("1")?"checked":"")+"/>- 30</label>"
                            + "</div>"
                            + "<div class='checkbox'>"
                                + "<label><input type='checkbox' name='time7' id='time7' value='1' "+(timeDistance2.equals("1")?"checked":"")+"/>- 7</label>"
                            + "</div>"
                            + "<div class='checkbox'>"
                                + "<label><input type='checkbox' name='time1' id='time1' value='1' "+(timeDistance3.equals("1")?"checked":"")+"/>- 1</label>"
                            + "</div>"
                            + "<div class='checkbox'>"
                                + "<label><input type='checkbox' name='time0' id='time0' value='1' "+(timeDistance4.equals("1")?"checked":"")+"/> 0</label>"
                            + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>"                
                + "<div class='form-group'>"
                    + "<label>Text</label>"
                    + "<textarea name='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_TEXT]+"'  id='"+ FrmNotification.fieldNames[FrmNotification.FRM_FIELD_TEXT]+"' class='form-control' style='height: 150px'>"+notification.getText()+"</textarea>"
                + "</div>"
                
//                + "<div class='form-group'>"
//                    + "<label class='checkbox-inline'><input type='checkbox' name='"+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_TARGET_EMPLOYEE]+"' id='"+FrmNotification.fieldNames[FrmNotification.FRM_FIELD_TARGET_EMPLOYEE]+"' value='1' "+(notification.getTargetEmployee() == 1 ?"checked":"")+" /> All Employee</label>"
//                + "</div>"
//            + "</div>"
	+ "</div>";
	return returnData;
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
