/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.log.ChangeValue;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.masterdata.*;
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
 * @author Gunadi
 */
public class AjaxDocMasterFlow extends HttpServlet {
    
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
    private long oidDocMaster = 0;
    
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
        this.oidDocMaster = FRMQueryString.requestLong(request, "oidDocMaster");
	
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
		commandDeleteAll(request);
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
        if(this.dataFor.equals("showDocFlowForm")){
	  this.htmlReturn = docFlowForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlDocMasterFlow ctrlDocMasterFlow = new CtrlDocMasterFlow(request);
	this.iErrCode = ctrlDocMasterFlow.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDocMasterFlow.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlDocMasterFlow ctrlDocMasterFlow = new CtrlDocMasterFlow(request);
	this.iErrCode = ctrlDocMasterFlow.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDocMasterFlow.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlDocMasterFlow ctrlDocMasterFlow = new CtrlDocMasterFlow(request);
	this.iErrCode = ctrlDocMasterFlow.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDocMasterFlow.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listDocMasterFlow")){
	    String[] cols = { PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_FLOW_ID],
		PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_COMPANY_ID],
                PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DIVISION_ID], 
                PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DEPARTMENT_ID],
                PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_LEVEL_ID],
                PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_POSITION_ID],
                PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_EMPLOYEE_ID]};

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
        
        if(dataFor.equals("listDocMasterFlow")){
	    whereClause += ""+PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID]+" = '"+this.oidDocMaster+"'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listDocMaster")){
	    total = PstDocMasterFlow.getCount(whereClause);
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
        DocMaster docMaster = new DocMaster();
        DocMasterFlow docMasterFlow = new DocMasterFlow();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listDocMasterFlow")){
               whereClause += ""+PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID]+" = '"+this.oidDocMaster+"'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listDocMasterFlow")){
	    listData = PstDocMasterFlow.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listDocMasterFlow")){
		docMasterFlow = (DocMasterFlow) listData.get(i);
                ChangeValue changeValue = new ChangeValue();
                String checkButton = "<input type='checkbox' name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_FLOW_ID]+"' class='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_FLOW_ID]+"' value='"+docMasterFlow.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+changeValue.getCompanyName(docMasterFlow.getCompany_id()));
                ja.put(""+changeValue.getDivisionName(docMasterFlow.getDivision_id()));
                ja.put(""+changeValue.getDepartmentName(docMasterFlow.getDepartment_id()));
                ja.put(""+changeValue.getLevelName(docMasterFlow.getLevel_id()));
                ja.put(""+changeValue.getPositionName(docMasterFlow.getPosition_id()));
                ja.put(""+changeValue.getEmployeeName(docMasterFlow.getEmployee_id()));
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+docMasterFlow.getOID()+"' data-for='showDocFlowForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+docMasterFlow.getOID()+"' data-for='deleteDocFlowSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    
    
    public String docFlowForm(){
        
        //CHECK DATA
	DocMasterFlow docMasterFlow = new DocMasterFlow();
	
	if(this.oid != 0){
	    try{
		docMasterFlow = PstDocMasterFlow.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        String returnData= "<input type='hidden' name='"+ FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID]+"'  id='"+ FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID]+"' class='form-control' value='"+this.oidDocMaster+"'>"
                + "<div class='form-group'>"
                    + "<label>Title</label>"
                    + "<input type='text' name='"+ FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_TITLE]+"'  id='"+ FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_TITLE]+"' class='form-control' value='"+(docMasterFlow.getFlow_title() != null ? docMasterFlow.getFlow_title() : "")+"'>"
                + "</div>"
                + "<div class='for-group'>"
                    + "<label>Flow Index</label>"
                    + "<select name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_INDEX]+"' id='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_INDEX]+"' class='form-control'> ";
                        for(int i=0 ; i<=8;i++){
                            if (docMasterFlow.getFlow_index() == i){
                                returnData += "<option value='"+i+"' selected='selected'>"+i+"</option>";
                            } else {
                                returnData += "<option value='"+i+"'>"+i+"</option>";
                            }
                        }
        returnData += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Company</label>"
                    + "<select name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_COMPANY_ID]+"' id='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_COMPANY_ID]+"' class='form-control chosen-select'>"
                        + "<option value=''>-Select-</option>";        
                        Vector companyList = PstCompany.list(0, 0, "", "");
                        if (companyList != null && companyList.size()>0){
                            for (int i=0; i < companyList.size(); i++){
                                Company company = (Company) companyList.get(i);
                                if (docMasterFlow.getCompany_id() == company.getOID()){
                                    returnData += "<option selected='selected' value='"+company.getOID()+"'>"+company.getCompany()+"</option>";
                                } else {
                                    returnData += "<option value='"+company.getOID()+"'>"+company.getCompany()+"</option>";
                                }        
                            }
                        }
        returnData += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Division</label>"
                    + "<select name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID]+"' id='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID]+"' class='form-control'>"
                        + "<option value=''>-Select-</option>";        
                        Vector divisionList = PstDivision.list(0, 0, "", "");
                        if (divisionList != null && divisionList.size()>0){
                            for (int i=0; i < divisionList.size(); i++){
                                Division div = (Division) divisionList.get(i);
                                if (docMasterFlow.getDivision_id() == div.getOID()){
                                    returnData += "<option selected='selected' value='"+div.getOID()+"'>"+div.getDivision()+"</option>";
                                } else {
                                    returnData += "<option value='"+div.getOID()+"'>"+div.getDivision()+"</option>";
                                }        
                            }
                        }
        returnData += "</select>"
                + "</div>"  
                + "<div class='form-group'>"
                    + "<label>Department</label>"
                    + "<select name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID]+"' id='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID]+"' class='form-control'>"
                        + "<option value=''>-Select-</option>";        
                        Vector departmentList = PstDepartment.list(0, 0, "", "");
                        if (departmentList != null && departmentList.size()>0){
                            for (int i=0; i < departmentList.size(); i++){
                                Department dep = (Department) departmentList.get(i);
                                if (docMasterFlow.getDepartment_id() == dep.getOID()){
                                    returnData += "<option selected='selected' value='"+dep.getOID()+"'>"+dep.getDepartment()+"</option>";
                                } else {
                                    returnData += "<option value='"+dep.getOID()+"'>"+dep.getDepartment()+"</option>";
                                }        
                            }
                        }
        returnData += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Position</label>"
                    + "<select name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_POSITION_ID]+"' id='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_POSITION_ID]+"' class='form-control'>"
                        + "<option value=''>-Select-</option>";        
                        Vector positionList = PstPosition.list(0, 0, "", "");
                        if (positionList != null && positionList.size()>0){
                            for (int i=0; i < positionList.size(); i++){
                                Position pos = (Position) positionList.get(i);
                                if (docMasterFlow.getPosition_id() == pos.getOID()){
                                    returnData += "<option selected='selected' value='"+pos.getOID()+"'>"+pos.getPosition()+"</option>";
                                } else {
                                    returnData += "<option value='"+pos.getOID()+"'>"+pos.getPosition()+"</option>";
                                }        
                            }
                        }
        returnData += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Level</label>"
                    + "<select name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_LEVEL_ID]+"' id='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_LEVEL_ID]+"' class='form-control'>"
                        + "<option value=''>-Select-</option>";        
                        Vector levelList = PstLevel.list(0, 0, "", "");
                        if (levelList != null && levelList.size()>0){
                            for (int i=0; i < levelList.size(); i++){
                                Level lvl = (Level) levelList.get(i);
                                if (docMasterFlow.getLevel_id() == lvl.getOID()){
                                    returnData += "<option selected='selected' value='"+lvl.getOID()+"'>"+lvl.getLevel()+"</option>";
                                } else {
                                    returnData += "<option value='"+lvl.getOID()+"'>"+lvl.getLevel()+"</option>";
                                }        
                            }
                        }
        returnData += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Employee</label>"
                    + "<select name='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_EMPLOYEE_ID]+"' id='"+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control'>"
                        + "<option value=''>-Select-</option>";        
                        Vector employeeList = PstEmployee.list(0, 0, "", "");
                        if (employeeList != null && employeeList.size()>0){
                            for (int i=0; i < employeeList.size(); i++){
                                Employee emp = (Employee) employeeList.get(i);
                                if (docMasterFlow.getEmployee_id() == emp.getOID()){
                                    returnData += "<option selected='selected' value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                } else {
                                    returnData += "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                }        
                            }
                        }
        returnData += "</select>"
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
    
