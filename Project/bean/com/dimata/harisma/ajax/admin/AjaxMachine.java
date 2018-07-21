/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.admin;

import com.dimata.harisma.entity.admin.Machine;
import com.dimata.harisma.entity.admin.PstMachine;
import com.dimata.harisma.form.admin.CtrlMachine;
import com.dimata.harisma.form.admin.FrmMachine;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Gunadi
 */
public class AjaxMachine extends HttpServlet {
    
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
		
	    case Command.LIST :
		commandList(request, response);
	    break;
		
	    case Command.DELETEALL : 
		commandDeleteAll(request);
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
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showMachineForm")){
	    this.htmlReturn = machineForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlMachine ctrlMachine = new CtrlMachine(request);
	this.iErrCode = ctrlMachine.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlMachine.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlMachine ctrlMachine = new CtrlMachine(request);
	this.iErrCode = ctrlMachine.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlMachine.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listMachine")){
	    String[] cols = { PstMachine.fieldNames[PstMachine.FLD_MACHINE_NAME],
		PstMachine.fieldNames[PstMachine.FLD_MACHINE_IP],
		PstMachine.fieldNames[PstMachine.FLD_MACHINE_PORT],
                PstMachine.fieldNames[PstMachine.FLD_MACHINE_COM_KEY]};

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
    }
    
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
        
        if(dataFor.equals("listMachine")){
               whereClause += " ("+PstMachine.fieldNames[PstMachine.FLD_MACHINE_NAME]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstMachine.fieldNames[PstMachine.FLD_MACHINE_IP]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR "+PstMachine.fieldNames[PstMachine.FLD_MACHINE_PORT]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstMachine.fieldNames[PstMachine.FLD_MACHINE_COM_KEY]+" LIKE '%"+this.searchTerm+"%')";
            }
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listSubSection")){
	    total = PstMachine.getCount(whereClause);
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
        Machine machine = new Machine();
        
	String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listMachine")){
               whereClause += " ("+PstMachine.fieldNames[PstMachine.FLD_MACHINE_NAME]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstMachine.fieldNames[PstMachine.FLD_MACHINE_IP]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR "+PstMachine.fieldNames[PstMachine.FLD_MACHINE_PORT]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstMachine.fieldNames[PstMachine.FLD_MACHINE_COM_KEY]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listMachine")){
	    listData = PstMachine.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listMachine")){
		machine = (Machine) listData.get(i);
                
		ja.put(""+(this.start+i+1));
		ja.put(""+machine.getMachineName());
		ja.put(""+machine.getMachineIP());
                ja.put(""+machine.getMachinePort());
                ja.put(""+machine.getMachineComKey());
                		
		String buttonUpdate = "";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral' data-oid='"+machine.getOID()+"' data-for='showMachineForm' type='button'><i class='fa fa-pencil'></i> Edit</button> ";
		}
		ja.put(buttonUpdate+"<div class='btn btn-default' type='button'><input type='checkbox' name='"+FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_ID]+"' class='"+FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_ID]+"' value='"+machine.getOID()+"'> Delete</div>");
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
    
    public String drawInputHidden(String name, String value){
        String data = "<input type=\"hidden\" name=\""+name+"\" value=\""+value+"\">";
        return data;
    }
    
    public String drawFormGroup(String label, String input, String valueCaption){
        String strValueCaption = "";
        if (valueCaption.length()>0){
            strValueCaption = "<div id=\"div_value\">"+valueCaption+"</div>";
        }
        String data = "<div class='form-group'>"
		    + "<label>"+label+"</label>"
                    + strValueCaption
		    + input
                    + "</div>";
        return data;
    }
    
    public String drawInput(String name, String styleClass, String value){
        String data = "<input type=\"text\" name=\""+name+"\" class=\"form-control "+styleClass+"\" value=\""+value+"\" />";
        return data;
    }
    
    public String drawSelect(String name, String styleClass, String option, String dataFor, String dataReplacement){
        String strDataFor = "";
        String strDataReplacement = "";
        if (dataFor.length()>0){
            strDataFor = " data-for=\""+dataFor+"\" ";
        }
        if (dataReplacement.length()>0){
            strDataReplacement = " data-replacement=\""+dataReplacement+"\" ";
        }
        String data = "<select class=\"form-control "+styleClass+"\" name=\""+name+"\" "+strDataFor+" "+strDataReplacement+">";
        data += option;
        data += "</select>";
        return data;
    }
    
    public String machineForm(){
	
	//CHECK DATA
	Machine machine = new Machine();
	
	if(this.oid != 0){
	    try{
		machine = PstMachine.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_ID]+"'  id='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_ID]+"' class='form-control' value='"+machine.getOID()+"'>"
		+ "<div class='form-group'>"
		    + "<label>Machine Name</label>"
		    + "<input type='text' name='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_NAME]+"'  id='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_NAME]+"' class='form-control' value='"+machine.getMachineName()+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Machine IP</label>"
		    + "<input type='text' name='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_IP]+"'  id='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_IP]+"' class='form-control' value='"+machine.getMachineIP()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Machine Port</label>"
		    + "<input type='text' name='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_PORT]+"'  id='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_IP]+"' class='form-control' value='"+machine.getMachinePort()+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Machine Com Key</label>"
		    + "<input type='text' name='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_COM_KEY]+"'  id='"+ FrmMachine.fieldNames[FrmMachine.FRM_FIELD_MACHINE_COM_KEY]+"' class='form-control' value='"+machine.getMachineComKey()+"'>"
		+ "</div>"
            + "</div>"
	+ "</div>";
	return returnData;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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