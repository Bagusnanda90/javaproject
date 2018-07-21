/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.system.PstSystemProperty;
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
public class AjaxSection extends HttpServlet {

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
	if(this.dataFor.equals("showSectionForm")){
	  this.htmlReturn = departmentForm();
	} 
        
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlSection ctrlSection = new CtrlSection(request);
	this.iErrCode = ctrlSection.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlSection.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlSection ctrlSection = new CtrlSection(request);
	this.iErrCode = ctrlSection.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlSection.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlSection ctrlSection = new CtrlSection(request);
	this.iErrCode = ctrlSection.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlSection.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listSection")){
	    String[] cols = { PstSection.fieldNames[PstSection.FLD_SECTION_ID],
                PstSection.fieldNames[PstSection.FLD_SECTION_ID],
		PstSection.fieldNames[PstSection.FLD_SECTION],
                PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID], 
                PstSection.fieldNames[PstSection.FLD_DESCRIPTION],
                PstSection.fieldNames[PstSection.FLD_SECTION_LINK_TO],
                PstSection.fieldNames[PstSection.FLD_VALID_STATUS],
                PstSection.fieldNames[PstSection.FLD_SECTION_ID]
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
        
        if(dataFor.equals("listSection")){
	    whereClause += " ("+PstSection.fieldNames[PstSection.FLD_SECTION]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listSection")){
	    total = PstSection.getCount(whereClause);
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
        Section sec = new Section();
        Department dept = new Department();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listSection")){
               whereClause += " ("+PstSection.fieldNames[PstSection.FLD_SECTION]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstSection.fieldNames[PstSection.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%' )";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listSection")){
	    listData = PstSection.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listSection")){
		sec = (Section) listData.get(i);
                try {
                    dept = PstDepartment.fetchExc(sec.getDepartmentId());
                } catch (Exception exc){}
                String checkButton = "<input type='checkbox' name='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_SECTION_ID]+"' class='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_SECTION_ID]+"' value='"+sec.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+sec.getSection());
                ja.put(""+dept.getDepartment());
		ja.put(""+sec.getDescription());
                ja.put(""+PstSection.validStatusValue[sec.getValidStatus()]);
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+sec.getOID()+"' data-for='showSectionForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+sec.getOID()+"' data-for='deleteSectionSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    public String departmentForm(){
	
	//CHECK DATA
	Section sec = new Section();
        
	if(this.oid != 0){
	    try{
		sec = PstSection.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        
        String returnData = "<div class='row'>"
                + "<div class='col-md-12'>"
                    + "<div class='form-group'>"
                        + "<label>Section *</label>"
                            + "<input type='text' id='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_SECTION]+"' class='form-control' name='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_SECTION]+"' value='"+sec.getSection()+"'>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Department *</label>"
                        + "<select name='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_DEPARTMENT_ID]+"' id='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_DEPARTMENT_ID]+"' class='form-control'> "
                            + "<option value=''>Select Department..</option>";
                                String strWhereDept = "";
                                Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, strWhereDept);
                                String prevCompany = "";
                                String prevDivision = "";
                                for (int i = 0; i < listCostDept.size(); i++) {
                                    Department dept = (Department) listCostDept.get(i);
                                    if(sec.getDepartmentId() == dept.getOID()){
                                        returnData += "<option value='"+String.valueOf(dept.getOID())+"' selected='selected'>" + dept.getDepartment()+"</option>";
                                    } else {
                                    if (prevCompany.equals(dept.getCompany())) {
                                        if (prevDivision.equals(dept.getDivision())) { 
                                     returnData +=" <option value='"+String.valueOf(dept.getOID())+"'>" + dept.getDepartment()+"</option>";
                                         } else { 
                                    returnData += "<option value='-2'>-" + dept.getDivision() + "-</option>"
                                            + " <option value='"+String.valueOf(dept.getOID())+"'>" + dept.getDepartment()+"</option>";
                                         prevDivision = dept.getDivision(); 
                                        }
                                    } else { 
                                           returnData += "<option value='-1'>-" + dept.getCompany() + "-</option>"
                                            + "<option value='-2'>-" + dept.getDivision() + "-</option>"
                                            + "<option value='"+String.valueOf(dept.getOID())+"'>" + dept.getDepartment()+"</option>";

                                      prevCompany = dept.getCompany();
                                        prevDivision = dept.getDivision();
                                    }
                                    }
                                }

                returnData+= "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Description</label>"
                        + "<textarea id='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DESCRIPTION]+"' class='form-control' name='"+FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DESCRIPTION]+"'>"+sec.getDescription()+"</textarea>"
                    + "</div>"    
                    + "<div class='form-group'>"
                            + "<label>Valid Status</label>"
                            + "<select name='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_STATUS]+"' id='"+FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_STATUS]+"' class='form-control' > ";
                                if (sec.getValidStatus() == PstSection.VALID_ACTIVE){
                        returnData += "<option value='"+PstSection.VALID_ACTIVE+"' selected='selected'>"+PstSection.validStatusValue[PstSection.VALID_ACTIVE]+"</option>"
                                    + "<option value='"+PstSection.VALID_HISTORY+"'>"+PstSection.validStatusValue[PstSection.VALID_HISTORY]+"</option>";
                                } else {
                        returnData += "<option value='"+PstSection.VALID_ACTIVE+"'>"+PstSection.validStatusValue[PstSection.VALID_ACTIVE]+"</option>"
                                    + "<option value='"+PstSection.VALID_HISTORY+"' selected='selected'>"+PstSection.validStatusValue[PstSection.VALID_HISTORY]+"</option>";                                    
                                }
                returnData += "</select>"
                    + "</div>"
                + "</div>"
                + "<div class='col-md-6'>"
                    + "<label>Start Date</label>"
                    + "<input type='text' name='"+ FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_START]+"'  id='"+ FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_START]+"' class='form-control datepicker' value='"+(sec.getValidStart()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(sec.getValidStart(),"yyyy-MM-dd"))+"'>"
                + "</div>" 
                + "<div class='col-md-6'>"
                    + "<label>End Date</label>"
                    + "<input type='text' name='"+ FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_END]+"'  id='"+ FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_END]+"' class='form-control datepicker' value='"+(sec.getValidEnd()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(sec.getValidEnd(),"yyyy-MM-dd"))+"'>"
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