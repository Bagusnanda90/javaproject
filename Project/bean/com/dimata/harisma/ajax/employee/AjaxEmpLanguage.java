/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.employee.EmpLanguage;
import com.dimata.harisma.entity.masterdata.Language;
import com.dimata.harisma.entity.employee.PstEmpLanguage;
import com.dimata.harisma.entity.masterdata.PstLanguage;
import com.dimata.harisma.form.employee.CtrlEmpLanguage;
import com.dimata.harisma.form.employee.FrmEmpLanguage;
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
public class AjaxEmpLanguage extends HttpServlet {

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
        this.empId = FRMQueryString.requestLong(request, "empId");
	
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
	if(this.dataFor.equals("showemplanguageform")){
	  this.htmlReturn = empLanguageForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
	this.iErrCode = ctrlEmpLanguage.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpLanguage.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
	this.iErrCode = ctrlEmpLanguage.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpLanguage.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
        this.iErrCode = ctrlEmpLanguage.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
        String message = ctrlEmpLanguage.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listemplanguage")){
	    String[] cols = { PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMP_LANGUAGE_ID],
		PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_LANGUAGE_ID],
		PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL],
                PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN],
                PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_DESCRIPTION]};

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
        
        if(dataFor.equals("listemplanguage")){
	    whereClause += " (IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+""
                            + " = "+PstEmpLanguage.GRADE_SELECT+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_SELECT]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+""
                            + " = "+PstEmpLanguage.GRADE_GOOD+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_GOOD]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+""
                            + " = "+PstEmpLanguage.GRADE_FAIR+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_FAIR]+"', "
                            + "'"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_POOR]+"'))) LIKE '%"+this.searchTerm+"%'"
                       + " OR IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+""
                            + " = "+PstEmpLanguage.GRADE_SELECT+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_SELECT]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+""
                            + " = "+PstEmpLanguage.GRADE_GOOD+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_GOOD]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+""
                            + " = "+PstEmpLanguage.GRADE_FAIR+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_FAIR]+"', "
                            + "'"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_POOR]+"'))) LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstLanguage.fieldNames[PstLanguage.FLD_LANGUAGE]+ " LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_DESCRIPTION]+ " LIKE '%"+this.searchTerm+"%')" 
                        + " AND " +PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%' ";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listemplanguage")){
	    total = PstEmpLanguage.getCountDataTable(whereClause);
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
        Language language = new Language();
	EmpLanguage empLanguage = new EmpLanguage();
	String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listemplanguage")){
               whereClause += " (IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+""
                            + " = "+PstEmpLanguage.GRADE_SELECT+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_SELECT]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+""
                            + " = "+PstEmpLanguage.GRADE_GOOD+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_GOOD]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+""
                            + " = "+PstEmpLanguage.GRADE_FAIR+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_FAIR]+"', "
                            + "'"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_POOR]+"'))) LIKE '%"+this.searchTerm+"%'"
                       + " OR IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+""
                            + " = "+PstEmpLanguage.GRADE_SELECT+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_SELECT]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+""
                            + " = "+PstEmpLanguage.GRADE_GOOD+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_GOOD]+"', "
                            + "IF ("+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+""
                            + " = "+PstEmpLanguage.GRADE_FAIR+", '"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_FAIR]+"', "
                            + "'"+PstEmpLanguage.gradeKey[PstEmpLanguage.GRADE_POOR]+"'))) LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstLanguage.fieldNames[PstLanguage.FLD_LANGUAGE]+ " LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_DESCRIPTION]+ " LIKE '%"+this.searchTerm+"%')" 
                        + " AND " +PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%' ";
                }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listemplanguage")){
	    listData = PstEmpLanguage.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listemplanguage")){
		empLanguage = (EmpLanguage) listData.get(i);
                try {
                    language = PstLanguage.fetchExc(empLanguage.getLanguageId());
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID]+"' class='"+FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID]+"' value='"+empLanguage.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }
		ja.put(""+(this.start+i+1));
		ja.put(""+language.getLanguage());
		ja.put(""+PstEmpLanguage.gradeKey[empLanguage.getOral()]);
                ja.put(""+PstEmpLanguage.gradeKey[empLanguage.getWritten()]);
                ja.put(""+empLanguage.getDescription());
                		
		String buttonUpdate = "";
		if(privUpdate){
		    buttonUpdate = "<button class='btn btn-success btn-xs btneditgeneral' data-oid='"+empLanguage.getOID()+"' data-for='showemplanguageform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empLanguage.getOID()+"' data-for='deleteEmpLangSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
                }
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
    
    public String empLanguageForm(){
	
	//CHECK DATA
	EmpLanguage empLanguage = new EmpLanguage();
	
	if(this.oid != 0){
	    try{
		empLanguage = PstEmpLanguage.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector lang_key = new Vector(1,1);
	Vector lang_val = new Vector(1,1);
        
        lang_key.add("");
        lang_val.add("Select Language");
        
        Vector listLanguage = PstLanguage.list(0, 0, "", "");
        if (listLanguage != null && listLanguage.size() > 0) {
            for (int i = 0; i < listLanguage.size(); i++) {
               Language lang = (Language) listLanguage.get(i);
               lang_key.add(""+lang.getOID());
               lang_val.add(""+lang.getLanguage());
            }
        }
        
        Vector grade_key = new Vector(1,1);
        Vector grade_val = new Vector(1,1);
        
        for (int n = 0; n < PstEmpLanguage.gradeKey.length; n++){
            grade_key.add(""+n);
            grade_val.add(""+PstEmpLanguage.gradeKey[n]);
        }
        
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID]+"'  id='"+ FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID]+"' class='form-control' value='"+empLanguage.getOID()+"'>"
		+ "<input type='hidden' name='"+ FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMPLOYEE_ID]+"'  id='"+ FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control' value='"+this.empId+"'>"
                + "<div class='form-group'>"
		    + "<label>Language *</label>"
		    + ""+ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID], null, ""+empLanguage.getLanguageId()+"", lang_key, lang_val, "id="+FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID], "form-control")+" "
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Oral</label>"
		    + ""+ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL], null, ""+empLanguage.getOral()+"", grade_key, grade_val, "", "form-control")+" "
		+ "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Written</label>"
		    + ""+ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN], null, ""+empLanguage.getWritten()+"", grade_key, grade_val, "", "form-control")+" "
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' placeholder='Input Description' class='form-control' id='" + FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION] +"' name='" + FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION] +"'>"+ empLanguage.getDescription() +"</textarea> "
		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Valis Status</label>"
//		    + ""+ControlCombo.draw(FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_STATUS], "-- Select --", ""+division.getValidStatus()+"", valid_key, valid_val, "", "form-control")+" "
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Validity</label>"
//		    + "<div class='input-group'>"
//			+ "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_START]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_START]+"' class='form-control datepicker' value='"+(division.getValidStart()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(division.getValidStart(),"yyyy-MM-dd"))+"'>"
//			+ "<div class='input-group-addon'>"
//			    + "To"
//			+ "</div>"
//			+ "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_END]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_END]+"' class='form-control datepicker' value='"+(division.getValidEnd()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(division.getValidEnd(),"yyyy-MM-dd"))+"'>"
//		    + "</div>"
//                + "</div>"
//                + "<a> note: fill some of field below, if you choose Branch of Company </a>"
//                + "<div class='form-group'>"
//		    + "<label>Address</label>"
//		    + "<textarea rows='5' class='form-control' id='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS] +"' name='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS] +"'>"+ division.getAddress()+"</textarea> "
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>City</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]+"' class='form-control' value='"+division.getCity()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>NPWP</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]+"' class='form-control' value='"+division.getNpwp()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Province</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]+"' class='form-control' value='"+division.getProvince()+"'>"
//		+ "</div>"    
//                + "<div class='form-group'>"
//		    + "<label>Region</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]+"' class='form-control' value='"+division.getRegion()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Sub Region</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]+"' class='form-control' value='"+division.getSubRegion()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Village</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]+"' class='form-control' value='"+division.getVillage()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Area</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]+"' class='form-control' value='"+division.getArea()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Telephone</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]+"' class='form-control' value='"+division.getTelphone()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Fax Number</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]+"' class='form-control' value='"+division.getFaxNumber()+"'>"
//		+ "</div>"
                
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
    