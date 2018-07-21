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
public class AjaxSubSection extends HttpServlet {
    
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
	if(this.dataFor.equals("showSubSectionForm")){
	    this.htmlReturn = subSectionForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlSubSection ctrlSubSection = new CtrlSubSection(request);
	this.iErrCode = ctrlSubSection.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlSubSection.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlSubSection ctrlSubSection = new CtrlSubSection(request);
	this.iErrCode = ctrlSubSection.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlSubSection.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listSubSection")){
	    String[] cols = { PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION_ID],
		PstSubSection.fieldNames[PstSubSection.FLD_SECTION_ID],
		PstSubSection.fieldNames[PstSubSection.FLD_SECTION_ID],
                PstSubSection.fieldNames[PstSubSection.FLD_DESCRIPTION],
                PstSubSection.fieldNames[PstSubSection.FLD_VALID_STATUS]};

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
        
        if(dataFor.equals("listSubSection")){
               whereClause += " ("+PstSubSection.fieldNames[PstSubSection.FLD_SECTION_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR "+PstSubSection.fieldNames[PstSubSection.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstSubSection.fieldNames[PstSubSection.FLD_VALID_STATUS]+" LIKE '%"+this.searchTerm+"%')";
            }
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listSubSection")){
	    total = PstSubSection.getCount(whereClause);
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
        SubSection subSection = new SubSection();
        Section section = new Section();
	
	String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listSubSection")){
               whereClause += " ("+PstSubSection.fieldNames[PstSubSection.FLD_SECTION_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR "+PstSubSection.fieldNames[PstSubSection.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstSubSection.fieldNames[PstSubSection.FLD_VALID_STATUS]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listSubSection")){
	    listData = PstSubSection.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listSubSection")){
		subSection = (SubSection) listData.get(i);
                try {
                    section = PstSection.fetchExc(subSection.getSectionId());
                } catch (Exception exc){}
		ja.put(""+(this.start+i+1));
		ja.put(""+section.getSection());
		ja.put(""+subSection.getSubSection());
                ja.put(""+subSection.getDescription());
                ja.put(""+PstSubSection.validStatusValue[subSection.getValidStatus()]);
                		
		String buttonUpdate = "";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral' data-oid='"+subSection.getOID()+"' data-for='showSubSectionForm' type='button'><i class='fa fa-pencil'></i> Edit</button> ";
		}
		ja.put(buttonUpdate+"<div class='btn btn-default' type='button'><input type='checkbox' name='"+FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_SUB_SECTION_ID]+"' class='"+FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_SUB_SECTION_ID]+"' value='"+subSection.getOID()+"'> Delete</div>");
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
    
    public String subSectionForm(){
	
	//CHECK DATA
	SubSection subSection = new SubSection();
	
	if(this.oid != 0){
	    try{
		subSection = PstSubSection.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector section_key = new Vector(1,1);
	Vector section_val = new Vector(1,1);
        
        Vector listSection = PstSection.list(0, 0, "", "");
        if (listSection != null && listSection.size() > 0) {
            for (int i = 0; i < listSection.size(); i++) {
               Section sec = (Section) listSection.get(i);
               section_key.add(""+sec.getOID());
               section_val.add(""+sec.getSection());
            }
        }
        
        Vector valid_key = new Vector(1,1);
        Vector valid_val = new Vector(1,1);
        
        valid_key.add(""+PstSection.VALID_ACTIVE);
        valid_val.add("Active");
        
        valid_key.add(""+PstSection.VALID_HISTORY);
        valid_val.add("History");
        
        
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Date dateStart = subSection.getValidStart() == null ? new Date() : subSection.getValidStart();
        Date dateEnd = subSection.getValidEnd() == null ? new Date() : subSection.getValidEnd();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String strValidStart = sdf.format(dateStart);
        String strValidEnd = sdf.format(dateEnd);
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_SUB_SECTION_ID]+"'  id='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_SUB_SECTION_ID]+"' class='form-control' value='"+subSection.getOID()+"'>"
		+ "<div class='form-group'>"
		    + "<label>Sub Section</label>"
		    + "<input type='text' name='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_SUB_SECTION]+"'  id='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_SUB_SECTION]+"' class='form-control' value='"+subSection.getSubSection()+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Section</label>"
		    + ""+ControlCombo.draw(FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_SECTION_ID], "-- Select --", ""+subSection.getSectionId()+"", section_key, section_val, "", "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' class='form-control' id='" + FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_DESCRIPTION] +"' name='" + FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_DESCRIPTION] +"'>"+ subSection.getDescription() +"</textarea> "
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Valis Status</label>"
		    + ""+ControlCombo.draw(FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_VALID_STATUS], "-- Select --", ""+subSection.getValidStatus()+"", valid_key, valid_val, "", "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Validity</label>"
		    + "<div class='input-group'>"
			+ "<input type='text' name='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_VALID_START]+"'  id='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_VALID_START]+"' class='form-control datepicker' value='"+(subSection.getValidStart()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(subSection.getValidStart(),"yyyy-MM-dd"))+"'>"
			+ "<div class='input-group-addon'>"
			    + "To"
			+ "</div>"
			+ "<input type='text' name='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_VALID_END]+"'  id='"+ FrmSubSection.fieldNames[FrmSubSection.FRM_FIELD_VALID_END]+"' class='form-control datepicker' value='"+(subSection.getValidEnd()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(subSection.getValidEnd(),"yyyy-MM-dd"))+"'>"
		    + "</div>"
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
