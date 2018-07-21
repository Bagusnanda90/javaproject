/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.masterdata.*;
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
public class AjaxEmpCompetency extends HttpServlet {

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
	if(this.dataFor.equals("showempcompetencyform")){
	    this.htmlReturn = empCompetencyForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmployeeCompetency ctrlEmpCompetency = new CtrlEmployeeCompetency(request);
	this.iErrCode = ctrlEmpCompetency.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlEmpCompetency.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmployeeCompetency ctrlEmpCompetency = new CtrlEmployeeCompetency(request);
	this.iErrCode = ctrlEmpCompetency.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlEmpCompetency.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlEmployeeCompetency ctrlEmpCompetency = new CtrlEmployeeCompetency(request);
        this.iErrCode = ctrlEmpCompetency.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
        String message = ctrlEmpCompetency.getMessage();
        this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listempcompetency")){
	    String[] cols = { PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_COMP_ID],
		PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_COMPETENCY_ID],
		PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_LEVEL_VALUE],
                PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_DATE_OF_ACHVMT],
                PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_SPECIAL_ACHIEVEMENT]};

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
        
        if(dataFor.equals("listempcompetency")){
            whereClause += " ("+PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_NAME]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_LEVEL_VALUE]+ " LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_DATE_OF_ACHVMT]+ " LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_SPECIAL_ACHIEVEMENT]+ " LIKE '%"+this.searchTerm+"%')"
		    + "AND "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+" = '"+this.empId+"'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listempcompetency")){
	    total = PstEmployeeCompetency.getCountDataTable(whereClause);
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
        Competency competency = new Competency();
	EmployeeCompetency empCompetency = new EmployeeCompetency();
	String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+" = '"+this.empId+"'";                  
        }else{
	     if(dataFor.equals("listempcompetency")){
               whereClause += " ("+PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_NAME]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_LEVEL_VALUE]+ " LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_DATE_OF_ACHVMT]+ " LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_SPECIAL_ACHIEVEMENT]+ " LIKE '%"+this.searchTerm+"%')"
		    + "AND "+PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+" = '"+this.empId+"'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listempcompetency")){
	    listData = PstEmployeeCompetency.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listempcompetency")){
		empCompetency = (EmployeeCompetency) listData.get(i);
                try {
                    competency = PstCompetency.fetchExc(empCompetency.getCompetencyId());
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]+"' class='"+FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]+"' value='"+empCompetency.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }
		ja.put(""+(this.start+i+1));
		ja.put(""+competency.getCompetencyName());
		ja.put(""+empCompetency.getLevelValue());
                ja.put(""+empCompetency.getDateOfAchvmt());
                ja.put(""+empCompetency.getSpecialAchievement());
                		
		String buttonUpdate = "";
		if(privUpdate){
		    buttonUpdate = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+empCompetency.getOID()+"' data-for='showempcompetencyform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empCompetency.getOID()+"' data-for='deleteEmpCompetencySingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
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
    
    public String empCompetencyForm(){
	
	//CHECK DATA
	EmployeeCompetency empCompetency = new EmployeeCompetency();
	
	if(this.oid != 0){
	    try{
		empCompetency = PstEmployeeCompetency.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector comp_key = new Vector(1,1);
	Vector comp_val = new Vector(1,1);
        
        comp_val.add("Select Competency...");
        comp_key.add("");
        Vector listCompetency = PstCompetency.list(0, 0, "", "");
        if (listCompetency != null && listCompetency.size() > 0) {
            for (int i = 0; i < listCompetency.size(); i++) {
               Competency comp = (Competency) listCompetency.get(i);
               comp_key.add(""+comp.getOID());
               comp_val.add(""+comp.getCompetencyName());
            }
        }
        
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]+"'  id='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]+"' class='form-control' value='"+empCompetency.getOID()+"'>"
                + "<input type='hidden' name='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_ID]+"'  id='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control' value='"+this.empId+"'>"
		+ "<div class='form-group'>"
		    + "<label>Competency *</label>"
		    + ""+ControlCombo.draw(FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_COMPETENCY_ID], null, ""+empCompetency.getCompetencyId()+"", comp_key, comp_val, "id="+FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_COMPETENCY_ID], "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Level Value</label>"
		    + "<input type='text' placeholder='Input Value' name='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_LEVEL_VALUE]+"'  id='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_LEVEL_VALUE]+"' class='form-control' value='"+(empCompetency.getLevelValue()==0 ?"":empCompetency.getLevelValue() )+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Date of Achievment</label>"
		    + "<input type='text' placeholder='Click To Select Date' name='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_DATE_OF_ACHVMT]+"'  id='"+ FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_DATE_OF_ACHVMT]+"' class='form-control datepicker' value='"+(empCompetency.getDateOfAchvmt()==null ? "" : Formater.formatDate(empCompetency.getDateOfAchvmt(),"yyyy-MM-dd"))+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Special Achievment</label>"
		    + "<textarea rows='5' placeholder='Input Description' class='form-control' id='" + FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_SPECIAL_ACHIEVEMENT] +"' name='" + FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_SPECIAL_ACHIEVEMENT] +"'>"+ empCompetency.getSpecialAchievement()+"</textarea> "
		+ "</div>"
//		+ "<div class='form-group'>"
//		    + "<label>Division Type Id</label>"
//		    + ""+ControlCombo.draw(FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_TYPE_ID], "-- Select --", ""+division.getDivisionTypeId()+"", divType_key, divType_val, "", "form-control")+" "
//		+ "</div>"
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
    