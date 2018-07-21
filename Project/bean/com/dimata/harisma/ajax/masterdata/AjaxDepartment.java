/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
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
public class AjaxDepartment extends HttpServlet {

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
    private long datachange = 0;
    
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
        this.datachange = FRMQueryString.requestLong(request, "datachange");
	
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
	if(this.dataFor.equals("showDepartmentForm")){
	  this.htmlReturn = departmentForm();
	} else if (this.dataFor.equals("getDivision")){
          this.htmlReturn = divisionSelect(request, this.datachange, 0);
        }
        
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlDepartment ctrlDepartment = new CtrlDepartment(request);
	this.iErrCode = ctrlDepartment.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDepartment.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlDepartment ctrlDepartment = new CtrlDepartment(request);
	this.iErrCode = ctrlDepartment.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDepartment.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlDepartment ctrlDepartment = new CtrlDepartment(request);
	this.iErrCode = ctrlDepartment.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDepartment.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listDepartment")){
	    String[] cols = { PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID],
                PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID],
		PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT],
                PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID], 
                PstDepartment.fieldNames[PstDepartment.FLD_DESCRIPTION],
                PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID],
                PstDepartment.fieldNames[PstDepartment.FLD_VALID_STATUS],
                PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
            };

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
        
        if(dataFor.equals("listDepartment")){
	    whereClause += " ("+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listDepartment")){
	    total = PstDepartment.getCount(whereClause);
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
        Department dept = new Department();
        Department deptJoin = new Department();
        Division div = new Division();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listDepartment")){
               whereClause += " (hr_department."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR hr_department." +PstDepartment.fieldNames[PstDepartment.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%' )";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listDepartment")){
	    listData = PstDepartment.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listDepartment")){
		dept = (Department) listData.get(i);
                try {
                    div = PstDivision.fetchExc(dept.getDivisionId());
                    deptJoin = PstDepartment.fetchExc(dept.getJoinToDepartmentId());
                } catch (Exception exc){}
                String checkButton = "<input type='checkbox' name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_ID]+"' class='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_ID]+"' value='"+dept.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+dept.getDepartment());
                ja.put(""+div.getDivision());
		ja.put(""+dept.getDescription());
                
                String joinStr = "-";
                if (dept.getJoinToDepartmentId() != 0){
                    joinStr = deptJoin.getDepartment();
                }
                
                ja.put(""+joinStr);
                ja.put(""+PstDepartment.validStatusValue[dept.getValidStatus()]);
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+dept.getOID()+"' data-for='showDepartmentForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+dept.getOID()+"' data-for='deleteDepartmentSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    public String divisionSelect(HttpServletRequest request, long companyId, long divisionId){
        String returnData = "";
        
        String whereClause = PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS]+"="+PstDivision.VALID_ACTIVE;
        whereClause += " AND "+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+companyId;
        Vector divisionList = PstDivision.list(0, 0, whereClause, "");
        if (divisionList != null && divisionList.size()>0){
           for (int i=0; i<divisionList.size(); i++){
                Division divisi = (Division)divisionList.get(i);
                if (divisionId == divisi.getOID()){
                    returnData += "<option selected=\"selected\" value=\""+divisi.getOID()+"\">"+divisi.getDivision()+"</option>";
                } else {
                    returnData += "<option value=\""+divisi.getOID()+"\">"+divisi.getDivision()+"</option>";
                }
                
            }
        }
        
        
        return returnData;
    }
    
    public String departmentForm(){
	
	//CHECK DATA
	Department dept = new Department();
	Division div = new Division();
        
	if(this.oid != 0){
	    try{
		dept = PstDepartment.fetchExc(this.oid);
                div = PstDivision.fetchExc(dept.getDivisionId());
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        
        String returnData = "<div class='row'>"
                + "<div class='col-md-12'>"
                + "<input type='hidden' id='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_ID]+"' class='form-control' name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_ID]+"' value='"+dept.getOID()+"'>"
                    + "<div class='form-group'>"
                        + "<label>Company</label>"
                        + "<select name='company' id='company' data-for='getDivision' data-target='#division' class='form-control'> "
                            + "<option value=''>Select Company..</option>";
                                Vector listCompany = PstCompany.list(0,0,"","");
                                for (int i=0; i < listCompany.size(); i ++){
                                    Company comp = (Company) listCompany.get(i);
                                    if (comp.getOID() == div.getCompanyId()){
                                        returnData += "<option value='"+comp.getOID()+"' selected>"+comp.getCompany()+"</option>";
                                    } else {
                                        returnData += "<option value='"+comp.getOID()+"'>"+comp.getCompany()+"</option>";                                    
                                    }
                                }
                returnData+= "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Division *</label>"
                            + "<select name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DIVISION_ID]+"' id='division' class='form-control'> "
                                + "<option value=''>Select Division..</option>"
                                + "" + divisionSelect(null,div.getCompanyId(), dept.getDivisionId())
                            + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Department *</label>"
                        + "<input type='text' id='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT]+"' class='form-control' name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT]+"' value='"+dept.getDepartment()+"'>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Description</label>"
                        + "<textarea id='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DESCRIPTION]+"' class='form-control' name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DESCRIPTION]+"'>"+dept.getDescription()+"</textarea>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>HoD Join to Department</label>"
                            + "<select name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_JOIN_TO_DEP_ID]+"' id='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_JOIN_TO_DEP_ID]+"' class='form-control' > "
                                + "<option value=''>Select Join..</option>";
                                Vector listDepartment = PstDepartment.list(0, 0, "", "DIVISION_ID");
                                Hashtable hashDivision = PstDivision.listMapDivisionName(0, 0, "", "");
                                long tempDepOid = 0 ;
                                for (int i = 0; i < listDepartment.size(); i++) {
                                        Department deptSelect = (Department) listDepartment.get(i);
                                        if (deptSelect.getDivisionId() != tempDepOid){
                                            if (i != 0){
                                               returnData += "</optgroup>";
                                            }
                                            returnData += "<optgroup label='"+hashDivision.get(deptSelect.getDivisionId()) +"'>";
                                            tempDepOid = deptSelect.getDivisionId();
                                        }
                    returnData += "<option value='"+String.valueOf(deptSelect.getOID()) +"'>"+ deptSelect.getDepartment() +"</option>";
                            }
                           returnData += "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                            + "<label>Department Type</label>"
                            + "<select name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_TYPE_ID]+"' id='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_TYPE_ID]+"' class='form-control' > "
                                + "<option value=''>Select Type..</option>";
                                    Vector listDepartmentType = PstDepartmentType.list(0, 0, "", "");
                                    if (listDepartmentType != null && listDepartmentType.size() > 0) {
                                        for (int ldt = 0; ldt < listDepartmentType.size(); ldt++) {
                                            DepartmentType depT = (DepartmentType) listDepartmentType.get(ldt);
                                            if (dept.getDepartmentTypeId() == depT.getOID()) {
                                                returnData += "<option value='"+depT.getOID()+"' selected>"+depT.getTypeName()+"</option>";
                                            } else {
                                                returnData += "<option value='"+depT.getOID()+"'>"+depT.getTypeName()+"</option>";
                                            }
                                        }
                                    }
                          returnData += "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                            + "<label>Valid Status</label>"
                            + "<select name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_STATUS]+"' id='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_STATUS]+"' class='form-control' > "
                                  + "<option value='"+PstDepartment.VALID_ACTIVE+"'>"+PstDepartment.validStatusValue[PstDepartment.VALID_ACTIVE]+"</option>"
                                  + "<option value='"+PstDepartment.VALID_HISTORY+"'>"+PstDepartment.validStatusValue[PstDepartment.VALID_HISTORY]+"</option>"
                        + "</select>"
                    + "</div>"
                + "</div>"
                + "<div class='col-md-6'>"
                    + "<label>Start Date</label>"
                    + "<input type='text' name='"+ FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_START]+"'  id='"+ FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_START]+"' class='form-control datepicker' value='"+(dept.getValidStart()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(dept.getValidStart(),"yyyy-MM-dd"))+"'>"
                + "</div>" 
                + "<div class='col-md-6'>"
                    + "<label>End Date</label>"
                    + "<input type='text' name='"+ FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_END]+"'  id='"+ FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_END]+"' class='form-control datepicker' value='"+(dept.getValidEnd()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(dept.getValidEnd(),"yyyy-MM-dd"))+"'>"
                + "</div>"
            + "</div>" ;      
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