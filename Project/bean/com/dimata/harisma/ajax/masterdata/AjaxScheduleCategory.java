/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.masterdata.ScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.ScheduleCategory;
import com.dimata.harisma.form.masterdata.CtrlScheduleCategory;
import com.dimata.harisma.form.masterdata.FrmScheduleCategory;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
 * @author IPAG
 */
public class AjaxScheduleCategory extends HttpServlet {

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
    //private int enableReplaceExistingSchedule = 0;
    
    
    
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
	if(this.dataFor.equals("showcategoryform")){
	    this.htmlReturn = schcategoryForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlScheduleCategory ctrlScheduleCategory = new CtrlScheduleCategory(request);
	this.iErrCode = ctrlScheduleCategory.action(this.iCommand, this.oid , this.oidDelete);
	String message = ctrlScheduleCategory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlScheduleCategory ctrlScheduleCategory = new CtrlScheduleCategory(request);
	this.iErrCode = ctrlScheduleCategory.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlScheduleCategory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlScheduleCategory ctrlScheduleCategory = new CtrlScheduleCategory(request);
	this.iErrCode = ctrlScheduleCategory.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlScheduleCategory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listschcategory")){
	    String[] cols = { PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID],
                PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE],
		PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY],
		PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]};

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
        
        if(dataFor.equals("listschcategory")){
	    whereClause += " ("+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listschcategory")){
	    total = PstScheduleCategory.getCount(whereClause);
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
        ScheduleCategory schcategory = new ScheduleCategory();
	
	String whereClause = "";
        String order ="";
	///boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listschcategory")){
               whereClause += " ("+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listschcategory")){
	    listData = PstScheduleCategory.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listschcategory")){
		schcategory = (ScheduleCategory) listData.get(i);
                String checkButton = "<input type='checkbox' name='"+FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_SCHEDULE_CATEGORY_ID]+"' class='"+FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_SCHEDULE_CATEGORY_ID]+"' value='"+schcategory.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+PstScheduleCategory.fieldCategoryType[schcategory.getCategoryType()]);
		ja.put(""+schcategory.getCategory());
                ja.put(""+schcategory.getDescription());
                		
		String buttonUpdate = "";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+schcategory.getOID()+"' data-for='showcategoryform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+schcategory.getOID()+"' data-for='deletecategory' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    public String schcategoryForm(){
	
	//CHECK DATA
	ScheduleCategory schcategory = new ScheduleCategory();
	
	if(this.oid != 0){
	    try{
		schcategory = PstScheduleCategory.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector type_key = new Vector(1,1);
        Vector type_val = new Vector(1,1);                                                         
        for (int i = 0; i < PstScheduleCategory.fieldCategoryType.length; i++) {
                type_key.add(PstScheduleCategory.fieldCategoryType[i]);
                type_val.add(String.valueOf(i));
        }
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_SCHEDULE_CATEGORY_ID]+"'  id='"+ FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_SCHEDULE_CATEGORY_ID]+"' class='form-control' value='"+schcategory.getOID()+"'>"
		+ "<div class='form-group'>"
		    + "<label>Type Category</label>"
                    + ""+ControlCombo.draw(FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_CATEGORY_TYPE],"form-control",null, "" + schcategory.getCategoryType(), type_val, type_key)+" "
		+ "</div>"
                
                + "<div class='form-group'>"
		    + "<label>Name Schedule Category</label>"
		    + "<input type='text' placeholder='Input Category Name..' name='"+ FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_CATEGORY]+"'  id='"+ FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_CATEGORY]+"' class='form-control' value='"+schcategory.getCategory()+"'>"
		+ "</div>"
		
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' placeholder='Input Description..' class='form-control' id='" + FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_DESCRIPTION] +"' name='" + FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_DESCRIPTION] +"'>"+ schcategory.getDescription() +"</textarea> "
		+ "</div>"
                
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
