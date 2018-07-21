/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.harisma.entity.employee.EmpEducation;
import com.dimata.harisma.entity.employee.PstEmpEducation;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.form.employee.CtrlEmpEducation;
import com.dimata.harisma.form.employee.FrmEmpEducation;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
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
public class AjaxEmpEducation extends HttpServlet {

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
	if(this.dataFor.equals("showempeducationform")){
	  this.htmlReturn = empEducationForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
	this.iErrCode = ctrlEmpEducation.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpEducation.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
	this.iErrCode = ctrlEmpEducation.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpEducation.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
	this.iErrCode = ctrlEmpEducation.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpEducation.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listempeducation")){
	    String[] cols = { PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID],
		PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID],
                PstEmpEducation.fieldNames[PstEmpEducation.FLD_INSTITUTION_ID],
                PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION],
		PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE],
                PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE],
                PstEmpEducation.fieldNames[PstEmpEducation.FLD_POINT],
                PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_DESC]};

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
        
        if(dataFor.equals("listempeducation")){
	    whereClause += " ("+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEducation.fieldNames[PstEducation.FLD_EDUCATION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_POINT]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR EMP."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_DESC]+" LIKE '%"+this.searchTerm+"%')"
                    + " AND " +PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listempeducation")){
	    total = PstEmpEducation.getCountDataTable(whereClause);
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
        EmpEducation empEducation = new EmpEducation();
        Education education = new Education();
	ContactList contList = new ContactList();
	String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listempeducation")){
               whereClause += " ("+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEducation.fieldNames[PstEducation.FLD_EDUCATION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpEducation.fieldNames[PstEmpEducation.FLD_POINT]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR EMP."+PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_DESC]+" LIKE '%"+this.searchTerm+"%')"
                    + " AND " +PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listempeducation")){
	    listData = PstEmpEducation.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listempeducation")){
		empEducation = (EmpEducation) listData.get(i);
                try {
                    education = PstEducation.fetchExc(empEducation.getEducationId());
                    contList = PstContactList.fetchExc(empEducation.getInstitutionId());
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID]+"' class='"+FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID]+"' value='"+empEducation.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }
		ja.put(""+(this.start+i+1));
		ja.put(""+education.getEducation());
		ja.put(""+contList.getCompName());
                ja.put(""+empEducation.getGraduation());
                ja.put(""+empEducation.getStartDate());
                ja.put(""+empEducation.getEndDate());
                ja.put(""+empEducation.getPoint());
                ja.put(""+empEducation.getEducationDesc());                
                		
		String buttonUpdate = "";
		if(privUpdate){
		    buttonUpdate = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+empEducation.getOID()+"' data-for='showempeducationform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empEducation.getOID()+"' data-for='deleteEmpEducationSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
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
    
    public String empEducationForm(){
	
	//CHECK DATA
	EmpEducation empEducation = new EmpEducation();
	
	if(this.oid != 0){
	    try{
		empEducation = PstEmpEducation.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector con_key = new Vector(1,1);
	Vector con_val = new Vector(1,1);
        
        con_key.add("0");
        con_val.add("Select University/Intitution...");
        
        Vector listProvider = PstContactList.list(0, 0, "", "");
        if (listProvider != null && listProvider.size() > 0) {
            for (int i = 0; i < listProvider.size(); i++) {
               ContactList contList = (ContactList) listProvider.get(i);
               con_key.add(""+contList.getOID());
               con_val.add(""+contList.getCompName());
            }
        }
        
        Vector edu_key = new Vector(1,1);
        Vector edu_val = new Vector(1,1);
        
        edu_key.add("");
        edu_val.add("Select Education...");
        
        Vector listEducation = PstEducation.list(0,0, "", "");
        if (listEducation != null && listEducation.size() > 0) {
            for (int i = 0; i < listEducation.size(); i++) {
               Education education = (Education) listEducation.get(i);
               edu_key.add(""+education.getOID());
               edu_val.add(""+education.getEducation());
            }
        }
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID]+"'  id='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID]+"' class='form-control' value='"+empEducation.getOID()+"'>"
		+ "<input type='hidden' name='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMPLOYEE_ID]+"'  id='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control' value='"+this.empId+"'>"
                + "<div class='form-group'>"
		    + "<label>Education *</label>"
		    + ""+ControlCombo.draw(FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_ID], null, ""+empEducation.getEducationId()+"", edu_key, edu_val, "id="+FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_ID], "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>University/Intitution</label>"
		    + ""+ControlCombo.draw(FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_INSTITUTION_ID], null, ""+empEducation.getInstitutionId()+"", con_key, con_val, "", "form-control")+" "
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Detail</label>"
		    + "<input type='text' placeholder='Input Detail University/Intitution' name='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_GRADUATION]+"'  id='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_GRADUATION]+"' class='form-control' value='"+empEducation.getGraduation()+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Start Year *</label>"
		    + ""+ControlDate.drawDateYear(FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_START_DATE], empEducation.getStartDate(),"form-control",-45,0)+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>End Year *</label>"
		    + ""+ControlDate.drawDateYear(FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_END_DATE], empEducation.getEndDate(),"form-control",-45,0)+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Point</label>"
		    + "<input type='text' placeholder='Input Point Value' name='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_POINT]+"'  id='"+ FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_POINT]+"' class='form-control' value='"+(empEducation.getPoint()==0 ?"":empEducation.getPoint())+"'>"
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' placeholder='Input Description' class='form-control' id='" + FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_DESC] +"' name='" + FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_DESC] +"'>"+ empEducation.getEducationDesc()+"</textarea> "
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
    