/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.payroll;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.payroll.CustomRptMain;
import com.dimata.harisma.entity.payroll.PstCustomRptMain;
import com.dimata.harisma.form.payroll.CtrlCustomRptMain;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.payroll.FrmCustomRptMain;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
 * @author Acer
 */
public class AjaxCustomRptMain extends HttpServlet {

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
            
            case Command.DELETE :
                commandDelete(request);
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
	if(this.dataFor.equals("showCustomReportForm")){
	  this.htmlReturn = empRelevantDocForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlCustomRptMain ctrlCustomRptMain = new CtrlCustomRptMain(request);
	this.iErrCode = ctrlCustomRptMain.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlCustomRptMain.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlCustomRptMain ctrlCustomRptMain = new CtrlCustomRptMain(request);
	this.iErrCode = ctrlCustomRptMain.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlCustomRptMain.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlCustomRptMain ctrlCustomRptMain = new CtrlCustomRptMain(request);
	this.iErrCode = ctrlCustomRptMain.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlCustomRptMain.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listCustomReport")){
	    String[] cols = { PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_TITLE],
		PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DESC],
                PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DATE_CREATE], 
                PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_CREATED_BY],
                PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_PRIV_POS]};

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
        
        if(dataFor.equals("listCustomReport")){
	     whereClause += " ("+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DESC]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_PRIV_POS]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_CREATED_BY]+" LIKE '%"+this.searchTerm+"%'"                     
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DATE_CREATE]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listCustomReport")){
	    total = PstCustomRptMain.getCount(whereClause);
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
        CustomRptMain customRptMain = new CustomRptMain();
        Level level = new Level();
        Position position = new Position();
        Employee employee = new Employee();
        String whereClause = "";
        String order ="";
        String document = "";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listCustomReport")){
               whereClause += " ("+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DESC]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_PRIV_POS]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_CREATED_BY]+" LIKE '%"+this.searchTerm+"%'"                     
                       + " OR "+PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DATE_CREATE]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listCustomReport")){
	    listData = PstCustomRptMain.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listCustomReport")){
		customRptMain = (CustomRptMain) listData.get(i);
                try {
                    level = PstLevel.fetchExc(customRptMain.getRptMainPrivLevel());
                    position = PstPosition.fetchExc(customRptMain.getRptMainPrivPos());
                    employee = PstEmployee.fetchExc(customRptMain.getRptMainCreatedBy());
                } catch(Exception ex){
                    System.out.println(ex.toString());
                }
                
                String checkButton = "<input type='checkbox' name='"+FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_ID]+"' class='"+FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_ID]+"' value='"+customRptMain.getOID()+"'>" ;
                ja.put(""+checkButton);
                ja.put(""+(this.start+i+1));
		ja.put(""+customRptMain.getRptMainTitle());
                ja.put(""+customRptMain.getRptMainDesc());
		ja.put(""+customRptMain.getRptMainDateCreate());
		ja.put(""+employee.getFullName());                
                ja.put(""+level.getLevel() + " : " + position.getPosition());
		String buttonUpdate = "";
                String buttonManageData = "<a href='custom_rpt_config_new.jsp?oid_custom="+customRptMain.getOID()+"' class='btn btn-primary btn-sm' data-toggle='tooltip' data-placement='top' title='Manage Data'><i class='fa fa-database'></i></a> ";
                String buttonGenerate = "<a href='custom_rpt_generate_new.jsp?oid_custom="+customRptMain.getOID()+"' class='btn btn-primary btn-sm' data-toggle='tooltip' data-placement='top' title='Generate Data'><i class='fa fa-file'></i></a> ";
                buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-sm' data-oid='"+customRptMain.getOID()+"' data-for='showCustomReportForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                ja.put(buttonUpdate+"<button class='btn btn-danger btn-sm btndeletesingle' data-oid='"+customRptMain.getOID()+"' data-for='deleteCustomReportSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> "+ buttonManageData+" "+buttonGenerate );
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
    
    public String empRelevantDocForm(){
	
        CustomRptMain customRptMain = new CustomRptMain();
        if(this.oid != 0){
            try {
                customRptMain = PstCustomRptMain.fetchExc(this.oid);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        Vector level_key = new Vector(1,1);
        Vector level_val = new Vector(1,1);
        
        level_key.add("");
        level_val.add("-Select-");
        
        Vector listLevel = PstLevel.listAll();
        for (int i = 0; i < listLevel.size(); i++){
            Level level = (Level) listLevel.get(i);
            level_key.add(""+level.getOID());
            level_val.add(""+level.getLevel());
        }
        
        Vector pos_key = new Vector(1,1);
        Vector pos_val = new Vector(1,1);
        
        pos_key.add("");
        pos_val.add("-Select");
        
        Vector listPosition = PstPosition.listAll();
        for (int i = 0; i < listPosition.size(); i++){
            Position pos = (Position) listPosition.get(i);
            pos_key.add(""+pos.getOID());
            pos_val.add(""+pos.getPosition());
        }
        
	String returnData=""
                        + "<input type='hidden' name='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_ID]+"'  id='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_ID]+"' class='form-control' value='"+customRptMain.getOID()+"'>"
                        + "<div class='form-group'>"
                            + "<label>Report Title *</label>"
                            + "<input type='text' name='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_TITLE]+"'  id='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_TITLE]+"' class='form-control' value='"+customRptMain.getRptMainTitle()+"'>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Description</label>"
                                + "<textarea row='5' name='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_DESC]+"'  id='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_DESC]+"' class='form-control'>"+customRptMain.getRptMainDesc()+"</textarea>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Date Created *</label>"
                                + "<input type='text' name='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_DATE_CREATE]+"'  id='"+ FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_DATE_CREATE]+"' class='form-control datepicker' value='"+(customRptMain.getRptMainDateCreate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(customRptMain.getRptMainDateCreate(),"yyyy-MM-dd"))+"'>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Privilege Setup</label>"
                                + "<div class='input-group'>"
                                + ""+ControlCombo.draw(FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_PRIV_LEVEL], null, ""+customRptMain.getRptMainPrivLevel()+"", level_key, level_val, "id="+FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_PRIV_LEVEL], "form-control")+" "
                                + "<div class='input-group-addon'>"
                                    + ""
                                + "</div>"
                                + ""+ControlCombo.draw(FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_PRIV_POS], null, ""+customRptMain.getRptMainPrivPos()+"", pos_key, pos_val, "id="+FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_PRIV_POS], "form-control")+" "
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
