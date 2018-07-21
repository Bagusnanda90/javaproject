/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
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
 * @author Gunadi
 */
public class AjaxPositionGroup extends HttpServlet {

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
    private long datachange = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        this.empId = FRMQueryString.requestLong(request, "empId");
        this.datachange = FRMQueryString.requestLong(request, "datachange");
	
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
		
	    case Command.LIST :
		commandList(request, response);
	    break;
                
            case Command.DELETE : 
		commandDelete(request);
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
	if(this.dataFor.equals("showPositionGroupForm")){
	  this.htmlReturn = positionForm();
	} 
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlPositionGroup ctrlPositionGroup = new CtrlPositionGroup(request);
	this.iErrCode = ctrlPositionGroup.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlPositionGroup.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlPositionGroup ctrlPositionGroup = new CtrlPositionGroup(request);
	this.iErrCode = ctrlPositionGroup.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlPositionGroup.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlPositionGroup ctrlPositionGroup = new CtrlPositionGroup(request);
	this.iErrCode = ctrlPositionGroup.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlPositionGroup.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listPositionGroup")){
	    String[] cols = { PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_ID],
                PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_ID],
		PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_NAME],
                PstPositionGroup.fieldNames[PstPositionGroup.FLD_DESCRIPTION], 
                PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_ID]
            };

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
        
        if(dataFor.equals("listPositionGroup")){
	    whereClause += " ("+PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_NAME]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listPositionGroup")){
	    total = PstPositionGroup.getCount(whereClause);
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
        PositionGroup posGroup = new PositionGroup();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listPositionGroup")){
               whereClause += " ("+PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_NAME]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstPositionGroup.fieldNames[PstPositionGroup.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%' )";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listPositionGroup")){
	    listData = PstPositionGroup.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listPositionGroup")){
		posGroup = (PositionGroup) listData.get(i);
                String checkButton = "<input type='checkbox' name='"+FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_POSITION_GROUP_ID]+"' class='"+FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_POSITION_GROUP_ID]+"' value='"+posGroup.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
                ja.put(""+posGroup.getPositionGroupName());
                ja.put(""+posGroup.getDescription());
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+posGroup.getOID()+"' data-for='showPositionGroupForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+posGroup.getOID()+"' data-for='deletePositionGroupSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    public String positionForm(){
	
	//CHECK DATA
	PositionGroup posGroup = new PositionGroup();
        
	if(this.oid != 0){
	    try{
		posGroup = PstPositionGroup.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        
        String returnData = "<div class='row'>"
                + "<div class='col-md-12'>"
                    + "<div class='form-group'>"
                        + "<label>Position Group *</label>"
                        + "<input type='text' id='"+FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_POSITION_GROUP_NAME]+"' class='form-control' name='"+FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_POSITION_GROUP_NAME]+"' value='"+posGroup.getPositionGroupName()+"'>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Description</label>"
                        + "<textarea id='"+FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_DESCRIPTION]+"' class='form-control' name='"+FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_DESCRIPTION]+"'>"+posGroup.getDescription()+"</textarea>"
                    + "</div>"
                + "</div>"
            + "</div>" ;      
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