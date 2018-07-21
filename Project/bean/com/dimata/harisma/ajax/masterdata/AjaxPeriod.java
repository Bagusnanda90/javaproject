/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.form.masterdata.CtrlPeriod;
import com.dimata.harisma.form.masterdata.FrmPeriod;
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
public class AjaxPeriod extends HttpServlet {

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
    private int enableReplaceExistingSchedule = 0;
    
    
    
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
            case Command.POST :
                commandPost(request);
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
	if(this.dataFor.equals("showperiodform")){
	    this.htmlReturn = periodForm();
	}
    }
    public void commandPost(HttpServletRequest request){
	CtrlPeriod ctrlPeriod = new CtrlPeriod(request);
	this.iErrCode = ctrlPeriod.action(this.iCommand, this.oid,1, this.oidDelete);
	String message = ctrlPeriod.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlPeriod ctrlPeriod = new CtrlPeriod(request);
	this.iErrCode = ctrlPeriod.action(this.iCommand, this.oid, this.enableReplaceExistingSchedule, this.oidDelete);
	String message = ctrlPeriod.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlPeriod ctrlPeriod = new CtrlPeriod(request);
	this.iErrCode = ctrlPeriod.action(this.iCommand, this.oid,this.enableReplaceExistingSchedule, this.oidDelete);
	String message = ctrlPeriod.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlPeriod ctrlPeriod = new CtrlPeriod(request);
	this.iErrCode = ctrlPeriod.action(this.iCommand, this.oid,this.enableReplaceExistingSchedule, this.oidDelete);
	String message = ctrlPeriod.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listperiod")){
	    String[] cols = { PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID],
                PstPeriod.fieldNames[PstPeriod.FLD_PERIOD],
		PstPeriod.fieldNames[PstPeriod.FLD_START_DATE],
		PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]};

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
        
        if(dataFor.equals("listperiod")){
	    whereClause += " ("+PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listperiod")){
	    total = PstPeriod.getCount(whereClause);
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
        Period period = new Period();
	
	String whereClause = "";
        String order ="";
	///boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listperiod")){
               whereClause += " ("+PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listperiod")){
	    listData = PstPeriod.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listperiod")){
		period = (Period) listData.get(i);
                String checkButton = "<input type='checkbox' name='"+FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_PERIOD_ID]+"' class='"+FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_PERIOD_ID]+"' value='"+period.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+period.getPeriod());
		ja.put(""+period.getStartDate());
                ja.put(""+period.getEndDate());
                		
		String buttonUpdate = "";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+period.getOID()+"' data-for='showperiodform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+period.getOID()+"' data-for='deleteperiod' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> "
                        + " <button class='btn btn-success btn-xs btngeneratesch' data-oid='"+period.getOID()+"' data-for='deleteperiod' type='button' data-toggle='tooltip' data-placement='top' title='Generate Default Schedule'><i class='fa fa-calendar-o'></i></button>");
                
                
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
    
    public String periodForm(){
	
	//CHECK DATA
	Period period = new Period();
	
	if(this.oid != 0){
	    try{
		period = PstPeriod.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Date dateStart = period.getStartDate() == null ? new Date() : period.getStartDate();
        Date dateEnd = period.getEndDate() == null ? new Date() : period.getEndDate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String strValidStart = sdf.format(dateStart);
        String strValidEnd = sdf.format(dateEnd);
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_PERIOD_ID]+"'  id='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_PERIOD_ID]+"' class='form-control' value='"+period.getOID()+"'>"
		+ "<div class='form-group'>"
		    + "<label>Period</label>"
		    + "<input type='text' name='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_PERIOD]+"'  id='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_PERIOD]+"' class='form-control' value='"+period.getPeriod()+"' placeholder='input period...'>"
		+ "</div>"
		
                + "<div class='form-group'>"
		    + "<label>Start Date & End Date</label>"
		    + "<div class='input-group'>"
			+ "<input type='text' name='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_START_DATE]+"'  id='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_START_DATE]+"' class='form-control datepicker' value='"+(period.getStartDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(period.getStartDate(),"yyyy-MM-dd"))+"'>"
			+ "<div class='input-group-addon'>"
			    + "To"
			+ "</div>"
			+ "<input type='text' name='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_END_DATE]+"'  id='"+ FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_END_DATE]+"' class='form-control datepicker' value='"+(period.getEndDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(period.getEndDate(),"yyyy-MM-dd"))+"'>"
		    + "</div>"
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
