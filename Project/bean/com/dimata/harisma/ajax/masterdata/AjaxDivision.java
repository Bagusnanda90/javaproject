/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.harisma.form.masterdata.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
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
 * @author Acer
 */
public class AjaxDivision extends HttpServlet {
    
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
	if(this.dataFor.equals("showdivisionform")){
	    this.htmlReturn = divisionForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlDivision ctrlDivision = new CtrlDivision(request);
	this.iErrCode = ctrlDivision.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDivision.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlDivision ctrlDivision = new CtrlDivision(request);
	this.iErrCode = ctrlDivision.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDivision.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listdivision")){
	    String[] cols = { PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID],
		PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID],
		PstDivision.fieldNames[PstDivision.FLD_DIVISION],
                PstDivision.fieldNames[PstDivision.FLD_DESCRIPTION],
                PstDivision.fieldNames[PstDivision.FLD_DIVISION_TYPE_ID],
                PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS]};

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
        
        if(dataFor.equals("listdivision")){
	    whereClause += " ("+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstDivision.fieldNames[PstDivision.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstDivision.fieldNames[PstDivision.FLD_DIVISION_TYPE_ID]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listdivision")){
	    total = PstDivision.getCount(whereClause);
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
        Division division = new Division();
        Company company = new Company();
        DivisionType divType =  new DivisionType();
	
	String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listdivision")){
               whereClause += " ("+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstDivision.fieldNames[PstDivision.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstDivision.fieldNames[PstDivision.FLD_DIVISION_TYPE_ID]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listdivision")){
	    listData = PstDivision.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listdivision")){
		division = (Division) listData.get(i);
                try {
                    company = PstCompany.fetchExc(division.getCompanyId());
                    divType = PstDivisionType.fetchExc(division.getDivisionTypeId());
                } catch (Exception exc){}
		ja.put(""+(this.start+i+1));
		ja.put(""+company.getCompany());
		ja.put(""+division.getDivision());
                ja.put(""+division.getDescription());
                ja.put(""+divType.getTypeName());
                ja.put(""+PstDivision.validStatusValue[division.getValidStatus()]);
                		
		String buttonUpdate = "";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral' data-oid='"+division.getOID()+"' data-for='showdivisionform' type='button'><i class='fa fa-pencil'></i> Edit</button> ";
		}
		ja.put(buttonUpdate+"<div class='btn btn-default' type='button'><input type='checkbox' name='"+FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_ID]+"' class='"+FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_ID]+"' value='"+division.getOID()+"'> Delete</div>");
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
    
    public String divisionForm(){
	
	//CHECK DATA
	Division division = new Division();
	
	if(this.oid != 0){
	    try{
		division = PstDivision.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector company_key = new Vector(1,1);
	Vector company_val = new Vector(1,1);
        
        Vector listCompany = PstCompany.list(0, 0, "", "");
        if (listCompany != null && listCompany.size() > 0) {
            for (int i = 0; i < listCompany.size(); i++) {
               Company comp = (Company) listCompany.get(i);
               company_key.add(""+comp.getOID());
               company_val.add(""+comp.getCompany());
            }
        }
        
        Vector divType_key = new Vector(1,1);
	Vector divType_val = new Vector(1,1);
        
        Vector listDivisionType = PstDivisionType.list(0, 0, "", "");
        if (listDivisionType != null && listDivisionType.size() > 0) {
            for (int ldt = 0; ldt < listDivisionType.size(); ldt++) {
                DivisionType divT = (DivisionType) listDivisionType.get(ldt);
                divType_key.add(""+divT.getOID());
                divType_val.add(""+divT.getTypeName());
            }
        }
        
        Vector valid_key = new Vector(1,1);
        Vector valid_val = new Vector(1,1);
        
        valid_key.add(""+PstDivision.VALID_ACTIVE);
        valid_val.add("Active");
        
        valid_key.add(""+PstDivision.VALID_HISTORY);
        valid_val.add("History");
        
        
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Date dateStart = division.getValidStart() == null ? new Date() : division.getValidStart();
        Date dateEnd = division.getValidEnd() == null ? new Date() : division.getValidEnd();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String strValidStart = sdf.format(dateStart);
        String strValidEnd = sdf.format(dateEnd);
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_ID]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_ID]+"' class='form-control' value='"+division.getOID()+"'>"
		+ "<div class='form-group'>"
		    + "<label>Division</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION]+"' class='form-control' value='"+division.getDivision()+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Company</label>"
		    + ""+ControlCombo.draw(FrmDivision.fieldNames[FrmDivision.FRM_FIELD_COMPANY_ID], "-- Select --", ""+division.getCompanyId()+"", company_key, company_val, "", "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' class='form-control' id='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DESCRIPTION] +"' name='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DESCRIPTION] +"'>"+ division.getDescription() +"</textarea> "
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Division Type Id</label>"
		    + ""+ControlCombo.draw(FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_TYPE_ID], "-- Select --", ""+division.getDivisionTypeId()+"", divType_key, divType_val, "", "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Valis Status</label>"
		    + ""+ControlCombo.draw(FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_STATUS], "-- Select --", ""+division.getValidStatus()+"", valid_key, valid_val, "", "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Validity</label>"
		    + "<div class='input-group'>"
			+ "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_START]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_START]+"' class='form-control datepicker' value='"+(division.getValidStart()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(division.getValidStart(),"yyyy-MM-dd"))+"'>"
			+ "<div class='input-group-addon'>"
			    + "To"
			+ "</div>"
			+ "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_END]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_END]+"' class='form-control datepicker' value='"+(division.getValidEnd()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(division.getValidEnd(),"yyyy-MM-dd"))+"'>"
		    + "</div>"
                + "</div>"
                + "<a> note: fill some of field below, if you choose Branch of Company </a>"
                + "<div class='form-group'>"
		    + "<label>Address</label>"
		    + "<textarea rows='5' class='form-control' id='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS] +"' name='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS] +"'>"+ division.getAddress()+"</textarea> "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>City</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]+"' class='form-control' value='"+division.getCity()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>NPWP</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]+"' class='form-control' value='"+division.getNpwp()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Province</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]+"' class='form-control' value='"+division.getProvince()+"'>"
		+ "</div>"    
                + "<div class='form-group'>"
		    + "<label>Region</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]+"' class='form-control' value='"+division.getRegion()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Sub Region</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]+"' class='form-control' value='"+division.getSubRegion()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Village</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]+"' class='form-control' value='"+division.getVillage()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Area</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]+"' class='form-control' value='"+division.getArea()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Telephone</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]+"' class='form-control' value='"+division.getTelphone()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Fax Number</label>"
		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]+"' class='form-control' value='"+division.getFaxNumber()+"'>"
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
