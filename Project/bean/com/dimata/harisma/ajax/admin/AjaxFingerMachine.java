/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.admin;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.admin.Machine;
import com.dimata.harisma.entity.admin.PstMachine;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.form.admin.FrmMachine;
import com.dimata.harisma.utility.machine.Finger;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Gunadi
 */
public class AjaxFingerMachine extends HttpServlet {

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
    private long userId = 0;
    private long empId = 0;
    private long awardId = 0;
    private long machineId = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    
    public static final int LEFT_LITTLE_FINGER = 0;
    public static final int LEFT_RING_FINGER = 1;
    public static final int LEFT_MIDDLE_FINGER = 2;
    public static final int LEFT_FORE_FINGER = 3;
    public static final int LEFT_THUMB = 4;
    public static final int RIGHT_THUMB = 5;
    public static final int RIGHT_FORE_FINGER = 6;
    public static final int RIGHT_MIDDLE_FINGER = 7;
    public static final int RIGHT_RING_FINGER = 8;
    public static final int RIGHT_LITTLE_FINGER = 9;
    public static String[] textListFinger = {
        "Left Little Finger","Left Ring Finger","Left Middle Finger","Left Fore Finger","Left Thumb","Right Thumb","Right Fore Finger","Right Middle Finger","Right Ring Finger","Right Little Finger"
    };
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        this.empId = FRMQueryString.requestLong(request, "empId");
        this.awardId = FRMQueryString.requestLong(request, "awardId");
        this.machineId = FRMQueryString.requestLong(request, "machineId");
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        this.empName = FRMQueryString.requestString(request, "empName");
	
	//INT
	this.iCommand = FRMQueryString.requestCommand(request);
	this.iErrCode = 0;
	
	
	
	//OBJECT
	this.jSONObject = new JSONObject();
	
	switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
	    break;
                
            case Command.DELETE :
                
            break;    
                
	    case Command.DELETEALL : 
		
	    break;                
	 
	    default : commandNone(request);
	}
	
	
	try{
	    
	    this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
	
	response.getWriter().print(this.jSONObject);
        
    }
    
   public void commandSave(HttpServletRequest request){
	if(this.dataFor.equals("showUploadForm")){
           try {
               long oidMachine = FRMQueryString.requestLong(request, "FRM_FIELD_MACHINE_ID");
               int type = FRMQueryString.requestInt(request, "type");
               //type 0 = upload name
               //type 1 = transfer data
               
               Machine machine = new Machine();
               if (oidMachine >0){
                   try{
                   machine = PstMachine.fetchExc(oidMachine);
                   } catch (Exception exc){}
                   
                   
                     String buffered = Finger.executeThroughSocket(machine.getMachinePort(),machine.getMachineIP(), "", this.empId,""+0,type);
                     
                     if (type == 0){
                        int first = buffered.indexOf("<SetUserInfoResponse>") + "<SetUserInfoResponse>".length();
                        int end = buffered.indexOf("</SetUserInfoResponse>",first);
                        String subString = buffered.substring(first, end);

                        int dataStart = subString.indexOf("<Row>") + "<Row>".length();
                        int dataEnd = subString.indexOf("</Row>",dataStart);
                        String data = subString.substring(dataStart, dataEnd); 

                        int infoStart = data.indexOf("<Information>") + "<Information>".length();
                        int infoEnd = data.indexOf("</Information>",infoStart);
                        String info = data.substring(infoStart, infoEnd);
                        this.htmlReturn = "<i class='fa fa-info'></i> "+info;
                     }
               } else {
                   this.htmlReturn = "<i class='fa fa-info'></i> Please select Machine";
               }
               
                
           } catch (IOException ex) {
               Logger.getLogger(AjaxFingerMachine.class.getName()).log(Level.SEVERE, null, ex);
               this.htmlReturn = "<i class='fa fa-info'></i> Can't connect to Machine";
           }
	} else if (this.dataFor.equals("showTransferForm")){
            try {
            String targetMachine = FRMQueryString.requestString(request, "secondMachineStr");
            String template = FRMQueryString.requestStringWithoutInjection(request, "finger");
            String finger = FRMQueryString.requestString(request, "fingerId");
            
            String fingerType = "-1";
            if (template.equals("All")){
                finger = "All";
            } else {
                for (int i=0;i<textListFinger.length;i++) {
                    if (textListFinger[i].equals(finger)) {
                        fingerType = ""+i;
                        break;
                    }
                }
            }
            
            String[] machineArray = targetMachine.split(",");
            
            
            Machine machine = new Machine();
               if (machineArray.length>0){
                   for (int x=0; x < machineArray.length; x++){
                   try{
                   machine = PstMachine.fetchExc(Long.valueOf(machineArray[x]));
                   } catch (Exception exc){}
                   
                   
                     String transferName = Finger.executeThroughSocket(machine.getMachinePort(),machine.getMachineIP(), "", this.empId,fingerType,0);
                     
                     if (!transferName.equals("")){
                        int first = transferName.indexOf("<SetUserInfoResponse>") + "<SetUserInfoResponse>".length();
                        int end = transferName.indexOf("</SetUserInfoResponse>",first);
                        String subString = transferName.substring(first, end);

                        int dataStart = subString.indexOf("<Row>") + "<Row>".length();
                        int dataEnd = subString.indexOf("</Row>",dataStart);
                        String data = subString.substring(dataStart, dataEnd); 

                        int infoStart = data.indexOf("<Information>") + "<Information>".length();
                        int infoEnd = data.indexOf("</Information>",infoStart);
                        String info = data.substring(infoStart, infoEnd);
                        this.htmlReturn = "<i class='fa fa-info'></i> "+info;
                     }
                     
                     String transferTemplate = Finger.executeThroughSocket(machine.getMachinePort(),machine.getMachineIP(), template, this.empId,fingerType,1);
                     
                     if (!transferTemplate.equals("")){
                        int first = transferTemplate.indexOf("<SetUserTemplateResponse>") + "<SetUserTemplateResponse>".length();
                        int end = transferTemplate.indexOf("</SetUserTemplateResponse>",first);
                        String subString = transferTemplate.substring(first, end);

                        int dataStart = subString.indexOf("<Row>") + "<Row>".length();
                        int dataEnd = subString.indexOf("</Row>",dataStart);
                        String data = subString.substring(dataStart, dataEnd); 

                        int infoStart = data.indexOf("<Information>") + "<Information>".length();
                        int infoEnd = data.indexOf("</Information>",infoStart);
                        String info = data.substring(infoStart, infoEnd);
                        this.htmlReturn = "<i class='fa fa-info'></i> "+info;
                     }
                   }
               } else {
                   this.htmlReturn = "<i class='fa fa-info'></i> Please select Machine";
               }
            
            } catch (Exception ex) {
               this.htmlReturn = "<i class='fa fa-info'></i> Can't connect to Machine";
           }
          
        } else if (this.dataFor.equals("deleteFinger")){
            try {
               long oidMachine = FRMQueryString.requestLong(request, "FRM_FIELD_MACHINE_ID");
               int type = FRMQueryString.requestInt(request, "type");
               String info = "";
               //type 0 = upload name
               //type 1 = transfer data
               //type 2 = get user info
               //type 3 = remove user
               //type 4 = remove template
               
               Machine machine = new Machine();
               if (oidMachine >0){
                   try{
                   machine = PstMachine.fetchExc(oidMachine);
                   } catch (Exception exc){}
                   
                   
                     String deleteTemplate = Finger.executeThroughSocket(machine.getMachinePort(),machine.getMachineIP(), "", this.empId,""+0,4);
                    try {
                        int first = deleteTemplate.indexOf("<DeleteTemplateResponse>") + "<DeleteTemplateResponse>".length();
                        int end = deleteTemplate.indexOf("</DeleteTemplateResponse>",first);
                        String subString = deleteTemplate.substring(first, end);

                        int dataStart = subString.indexOf("<Row>") + "<Row>".length();
                        int dataEnd = subString.indexOf("</Row>",dataStart);
                        String data = subString.substring(dataStart, dataEnd); 

                        int infoStart = data.indexOf("<Information>") + "<Information>".length();
                        int infoEnd = data.indexOf("</Information>",infoStart);
                        info = data.substring(infoStart, infoEnd);
                        System.out.println("info ="+info);
                        this.htmlReturn = "<i class='fa fa-info'></i> "+info;
                    } catch (Exception exc){}

                     String deleteUser = Finger.executeThroughSocket(machine.getMachinePort(),machine.getMachineIP(), "", this.empId,""+0,3);
                     if (info.equals("")){
                        try{
                            int first = deleteUser.indexOf("<DeleteUserResponse>") + "<DeleteUserResponse>".length();
                            int end = deleteUser.indexOf("</DeleteUserResponse>",first);
                            String subString = deleteUser.substring(first, end);

                            int dataStart = subString.indexOf("<Row>") + "<Row>".length();
                            int dataEnd = subString.indexOf("</Row>",dataStart);
                            String data = subString.substring(dataStart, dataEnd); 

                            int infoStart = data.indexOf("<Information>") + "<Information>".length();
                            int infoEnd = data.indexOf("</Information>",infoStart);
                            info = data.substring(infoStart, infoEnd);
                            System.out.println("info ="+info);
                            this.htmlReturn = "<i class='fa fa-info'></i> "+info;
                        } catch (Exception exc){}
                     }
                     
               } else {
                   this.htmlReturn = "<i class='fa fa-info'></i> Please select Machine";
               }
               
                
           } catch (IOException ex) {
               Logger.getLogger(AjaxFingerMachine.class.getName()).log(Level.SEVERE, null, ex);
               this.htmlReturn = "<i class='fa fa-info'></i> Can't connect to Machine";
           }
        }
    }
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showUploadForm") || this.dataFor.equals("deleteFinger")){
	  this.htmlReturn = fingerForm();
	} else if (this.dataFor.equals("showTransferForm")){
          this.htmlReturn = transferForm();
        } else if (this.dataFor.equals("changeSecondMachine")){
            this.htmlReturn = machineSelect(this.machineId);
        } else if (this.dataFor.equals("getFingerList")){
            this.htmlReturn = fingerList();
        }
    }
    
    
    public String fingerForm(){
        
        Vector machine_key = new Vector(1,1);
	Vector machine_val = new Vector(1,1);
        
        Vector listMachine = PstMachine.list(0, 0, "", "");
        if (listMachine != null && listMachine.size() > 0) {
            for (int i = 0; i < listMachine.size(); i++) {
               Machine machine = (Machine) listMachine.get(i);
               machine_key.add(""+machine.getOID());
               machine_val.add(""+machine.getMachineName());
            }
        }
        
        
        String returnData = ""
                        + "<div class='row'>"
                            + "<div class='col-md-12'>"
                                + "<div class='form-group'>"
                                    + "<input type='hidden' name='type' value='0'>"
                                    + "<label>Machine</label>"
                                        + ""+ControlCombo.draw(FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_ID], "-- Select --", null, machine_key, machine_val, "", "form-control")+" "
                                + "</div>"
                            + "</div>"
                        + "</div>";
        
        return returnData;
    }
    
    public String transferForm(){
        
        Vector listMachine = PstMachine.list(0,0,"","");
        
        
        String returnData = ""
                        + "<div class='row'>"
                            + "<div class='col-md-12'>"
                                + "<div class='form-group'>"
                                    + "<label>From</label>"
                                        + "<select id='firstMachine' name='firstMachine' class='form-control chosen-select' data-for='changeSecondMachine' data-replacement='#finger' data-remove='#secondMachine'>"
                                        + machineSelect(0)
                                + "</select>"
                                + "</div>"
                                + "<div class='form-group'>"
                                    + "<label>To</label>"
                                        + "<select multiple id='secondMachine' name='secondMachine' class='form-control chosen-select' data-for='getFingerList' data-replacement='#finger'>"
                                        + machineSelect(0)
                                + "</select>"
                                + "</div>"
                                + "<div class='form-group'>"
                                    + "<label>Finger List</label>"
                                        + "<select id='finger' name='finger' class='form-control chosen-select'>"
                                        + "<option value='0'>Select Finger</option>"
                                + "</select>"
                                + "</div>"   
                            + "</div>"
                        + "</div>";
        
        return returnData;
    }
    
    public String machineSelect(long machineId){
        Vector listMachine = new Vector();
        if (machineId > 0){
            listMachine = PstMachine.list(0,0,PstMachine.fieldNames[PstMachine.FLD_MACHINE_ID]+" != "+machineId,"");
        } else {
            listMachine = PstMachine.list(0, 0, "", "");
        }

        String data =""+ 
                "<option value='0'>Select Machine...</option>";
                if (listMachine.size() > 0){
                    for (int x=0; x < listMachine.size(); x++){
                        Machine machine = (Machine)listMachine.get(x);
                        data += "<option value='"+machine.getOID()+"'>"+machine.getMachineName()+"</option>";
                    }
                }
                
        return data;
    }
    
    public String fingerList(){
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(empId);
        } catch (Exception exc){}
        
        Machine machine = new Machine();
        try {
            machine = PstMachine.fetchExc(machineId);
        } catch (Exception exc){
            
        }
        
        String buffered = "";
        String returnData="<option value='0'>Select Finger..</option>";
        try {
            buffered = Finger.executeThroughSocket(machine.getMachinePort(),machine.getMachineIP(), "", this.empId,""+0,2);
        } catch (Exception exc){}
        
        int first = buffered.indexOf("<GetUserTemplateResponse>") + "<GetUserTemplateResponse>".length();
        int end = buffered.indexOf("</GetUserTemplateResponse>",first);
        String subString = buffered.substring(first, end);
        
        String[] stringArray = subString.split(" ");

        if (stringArray.length >0){
            for (int i=0; i< stringArray.length;i++){

            if (stringArray[i].length()>0){                         
                int dataStart = stringArray[i].indexOf("<Row>") + "<Row>".length();
                int dataEnd = stringArray[i].indexOf("</Row>",dataStart);
                String data = stringArray[i].substring(dataStart, dataEnd); 

                int pinStart = data.indexOf("<PIN>") + "<PIN>".length();
                int pinEnd = data.indexOf("</PIN>",pinStart);
                String pin = data.substring(pinStart, pinEnd);

                int fingerStart = data.indexOf("<FingerID>") + "<FingerID>".length();
                int fingerEnd = data.indexOf("</FingerID>",fingerStart);
                String finger = data.substring(fingerStart, fingerEnd);
                int fingerId = Integer.valueOf(finger);

                int sizeStart = data.indexOf("<Size>") + "<Size>".length();
                int sizeEnd = data.indexOf("</Size>",sizeStart);
                String size = data.substring(sizeStart, sizeEnd);

                int validStart = data.indexOf("<Valid>") + "<Valid>".length();
                int validEnd = data.indexOf("</Valid>",validStart);
                String valid = data.substring(validStart, validEnd);

                int templateStart = data.indexOf("<Template>") + "<Template>".length();
                int templateEnd = data.indexOf("</Template>",templateStart);
                String template = data.substring(templateStart, templateEnd);
                
                returnData += ""
                        + "<option value='"+template+"'>"+textListFinger[fingerId]+"</option>";
            }
            }
        }
        
        
        
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