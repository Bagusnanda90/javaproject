/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.employee.Experience;
import com.dimata.harisma.entity.employee.PstExperience;
import com.dimata.harisma.form.employee.CtrlExperience;
import com.dimata.harisma.form.employee.FrmExperience;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
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
public class AjaxEmpExperience extends HttpServlet {

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
	if(this.dataFor.equals("showempexperienceform")){
	  this.htmlReturn = empExperienceForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlExperience ctrlExperience = new CtrlExperience(request);
	this.iErrCode = ctrlExperience.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlExperience.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlExperience ctrlExperience = new CtrlExperience(request);
	this.iErrCode = ctrlExperience.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlExperience.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlExperience ctrlExperience = new CtrlExperience(request);
	this.iErrCode = ctrlExperience.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlExperience.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listempexperience")){
	    String[] cols = { PstExperience.fieldNames[PstExperience.FLD_WORK_HISTORY_PAST_ID],
		PstExperience.fieldNames[PstExperience.FLD_COMPANY_NAME],
		PstExperience.fieldNames[PstExperience.FLD_START_DATE],
                PstExperience.fieldNames[PstExperience.FLD_END_DATE],
                PstExperience.fieldNames[PstExperience.FLD_POSITION],
                PstExperience.fieldNames[PstExperience.FLD_MOVE_REASON],
                PstExperience.fieldNames[PstExperience.FLD_PROVIDER_ID]};

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
        
        if(dataFor.equals("listempexperience")){
	    whereClause += " ("+PstExperience.fieldNames[PstExperience.FLD_COMPANY_NAME]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_MOVE_REASON]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+this.searchTerm+"%')"
                    + " AND EMP." +PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listempexperience")){
	    total = PstExperience.getCountDataTable(whereClause);
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
        Experience experience = new Experience();
	ContactList contList = new ContactList();
	String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "EMP."+PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listempexperience")){
               whereClause += " ("+PstExperience.fieldNames[PstExperience.FLD_COMPANY_NAME]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstExperience.fieldNames[PstExperience.FLD_MOVE_REASON]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+this.searchTerm+"%')"
                    + " AND EMP." +PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listempexperience")){
	    listData = PstExperience.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listempexperience")){
		experience = (Experience) listData.get(i);
                try {
                    contList = PstContactList.fetchExc(experience.getProviderID());
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmExperience.fieldNames[FrmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID]+"' class='"+FrmExperience.fieldNames[FrmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID]+"' value='"+experience.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }    
		ja.put(""+(this.start+i+1));
		ja.put(""+experience.getCompanyName());
		ja.put(""+Formater.formatDate(experience.getStartDate(),"yyyy-MM"));
                ja.put(""+Formater.formatDate(experience.getEndDate(),"yyyy-MM"));
                ja.put(""+experience.getPosition());
                ja.put(""+experience.getMoveReason());
                ja.put(""+contList.getCompName());
                ja.put(""+experience.getSalaryReceived());
                		
		String buttonUpdate = "";
		if(privUpdate){
		    buttonUpdate = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+experience.getOID()+"' data-for='showempexperienceform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+experience.getOID()+"' data-for='deleteEmpExperienceSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
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
    
    public String empExperienceForm(){
	
	//CHECK DATA
	Experience experience = new Experience();
	
	if(this.oid != 0){
	    try{
		experience = PstExperience.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector con_key = new Vector(1,1);
	Vector con_val = new Vector(1,1);
        
        con_key.add("0");
        con_val.add("Select Reference...");
        
        Vector listProvider = PstContactList.list(0, 0, "", "");
        if (listProvider != null && listProvider.size() > 0) {
            for (int i = 0; i < listProvider.size(); i++) {
               ContactList contList = (ContactList) listProvider.get(i);
               con_key.add(""+contList.getOID());
               con_val.add(""+contList.getCompName());
            }
        }
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID]+"'  id='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID]+"' class='form-control' value='"+experience.getOID()+"'>"
		+ "<input type='hidden' name='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_EMPLOYEE_ID]+"'  id='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control' value='"+this.empId+"'>"
                + "<div class='form-group'>"
		    + "<label>Company Name *</label>"
             + "<input type='text' placeholder='Input Company Name' name='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_COMPANY_NAME]+"'  id='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_COMPANY_NAME]+"' class='form-control' value='"+experience.getCompanyName()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
             + "<label>Start Year - End Year</label>"
             + "<div class='input-group'>"
                 + "<input type='text' name='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_START_DATE]+"'  id='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_START_DATE]+"' class='form-control datepicker1' value ='"+(experience.getStartDate()== null ? Formater.formatDate(new Date(), "yyyy-MM") : Formater.formatDate(experience.getStartDate(),"yyyy-MM"))+"'>"
                 + "<div class='input-group-addon'>"
                     + "To"
		+ "</div>"
                 + "<input type='text' name='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_END_DATE]+"'  id='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_END_DATE]+"' class='form-control datepicker1' value ='"+(experience.getEndDate()== null ? Formater.formatDate(new Date(), "yyyy-MM") : Formater.formatDate(experience.getEndDate(),"yyyy-MM"))+"'>"
		+ "</div>"
         + "</div>"
         
//         + "<div class='form-group'>"
//             + "<label>Start Year *</label>"
//             + ""+ControlDate.drawDateYear(FrmExperience.fieldNames[FrmExperience.FRM_FIELD_START_DATE], experience.getStartDate(),"form-control",-45,0)+" "
//         + "</div>"
//         + "<div class='form-group'>"
//             + "<label>End Year *</label>"
//             + ""+ControlDate.drawDateYear(FrmExperience.fieldNames[FrmExperience.FRM_FIELD_END_DATE], experience.getEndDate(),"form-control",-45,0)+" "
//         + "</div>"
                
		+ "<div class='form-group'>"
    		    + "<label>Position *</label>"
             + "<input type='text' placeholder='Input Position' name='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_POSITION]+"'  id='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_POSITION]+"' class='form-control' value='"+experience.getPosition()+"'>"
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Move Reason *</label>"
             + "<textarea rows='5' placeholder='Input Move Reason..' class='form-control' id='" + FrmExperience.fieldNames[FrmExperience.FRM_FIELD_MOVE_REASON] +"' name='" + FrmExperience.fieldNames[FrmExperience.FRM_FIELD_MOVE_REASON] +"'>"+ experience.getMoveReason()+"</textarea> "
		+ "</div>"
                + "<div class='form-group'>"
             + "<label>Reference</label>"
		    + ""+ControlCombo.draw(FrmExperience.fieldNames[FrmExperience.FRM_FIELD_PROVIDER_ID], null, ""+experience.getProviderID()+"", con_key, con_val, "", "form-control")+" "
		+ "</div>"
         + "<div class='form-group'>"
                 + "<label>Salary Received </label>"
             + "<input type='text' placeholder='Input Salary Received..' name='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_SALARY_RECEIVED]+"'  id='"+ FrmExperience.fieldNames[FrmExperience.FRM_FIELD_SALARY_RECEIVED]+"' class='form-control' value='"+experience.getSalaryReceived()+"'>" 
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
    