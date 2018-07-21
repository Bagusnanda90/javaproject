/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.recruitment;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.recruitment.PstStaffRequisition;
import com.dimata.harisma.entity.recruitment.StaffRequisition;
import com.dimata.harisma.form.recruitment.CtrlStaffRequisition;
import com.dimata.harisma.form.recruitment.FrmStaffRequisition;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.json.*;

/**
 *
 * @author Gunadi
 */
public class AjaxStaffRequisition extends HttpServlet {

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
    private long oidUserLogin = 0;
    
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
        this.oidUserLogin = FRMQueryString.requestLong(request, "user");
	
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
	if(this.dataFor.equals("showRequisitionForm")){
	  this.htmlReturn = staffRequisitionForm();
	} 
        
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlStaffRequisition ctrlStaffRequisition = new CtrlStaffRequisition(request);
	this.iErrCode = ctrlStaffRequisition.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlStaffRequisition.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlStaffRequisition ctrlStaffRequisition = new CtrlStaffRequisition(request);
	this.iErrCode = ctrlStaffRequisition.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlStaffRequisition.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlStaffRequisition ctrlStaffRequisition = new CtrlStaffRequisition(request);
	this.iErrCode = ctrlStaffRequisition.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlStaffRequisition.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listStaffRequisition")){
	    String[] cols = { PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_STAFF_REQUISITION_ID],
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_STAFF_REQUISITION_ID],
		PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID],
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID], 
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID],
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUISITION_TYPE],
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_MALE],
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_FEMALE],
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EXP_COMM_DATE],
                PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_TEMP_FOR]
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
        
        if(dataFor.equals("listStaffRequisition")){
	    whereClause += " (dep."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR sec."+PstSection.fieldNames[PstSection.FLD_SECTION]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR cat."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR IF (req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUISITION_TYPE]
                    + " = "+PstStaffRequisition.REPLACEMNT+", '"+PstStaffRequisition.reqtypeKey[PstStaffRequisition.REPLACEMNT]+"',"
                    + "'"+PstStaffRequisition.reqtypeKey[PstStaffRequisition.ADDITIONAL]+"') LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_MALE]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_FEMALE]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EXP_COMM_DATE]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_TEMP_FOR]+" LIKE '%"+this.searchTerm+"%' "
                    + ")";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listStaffRequisition")){
	    total = PstStaffRequisition.getCountJoin(whereClause);
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
        StaffRequisition staff = new StaffRequisition();
        EmpCategory empCat = new EmpCategory();
        Position pos = new Position();
        Section sec = new Section();
        Department dept = new Department();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listStaffRequisition")){
               whereClause += " (dep."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR sec."+PstSection.fieldNames[PstSection.FLD_SECTION]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR cat."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR IF (req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUISITION_TYPE]
                    + " = "+PstStaffRequisition.REPLACEMNT+", '"+PstStaffRequisition.reqtypeKey[PstStaffRequisition.REPLACEMNT]+"',"
                    + "'"+PstStaffRequisition.reqtypeKey[PstStaffRequisition.ADDITIONAL]+"') LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_MALE]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_FEMALE]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EXP_COMM_DATE]+" LIKE '%"+this.searchTerm+"%' "
                    + "OR req."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_TEMP_FOR]+" LIKE '%"+this.searchTerm+"%' "
                    + ")";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listStaffRequisition")){
	    listData = PstStaffRequisition.listJoin(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listStaffRequisition")){
		staff = (StaffRequisition) listData.get(i);
                try {
                    dept = PstDepartment.fetchExc(staff.getDepartmentId());
                    sec = PstSection.fetchExc(staff.getSectionId());
                    pos = PstPosition.fetchExc(staff.getPositionId());
                    empCat = PstEmpCategory.fetchExc(staff.getEmpCategoryId());
                } catch (Exception exc){}
                
                String checkButton = "<input type='checkbox' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_STAFF_REQUISITION_ID]+"' class='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_STAFF_REQUISITION_ID]+"' value='"+staff.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
                ja.put(""+dept.getDepartment());
		ja.put(""+sec.getSection());
                ja.put(""+pos.getPosition());
                ja.put(""+empCat.getEmpCategory());
                ja.put(""+PstStaffRequisition.reqtypeKey[staff.getRequisitionType()]);
                ja.put(""+staff.getNeededMale());
                ja.put(""+staff.getNeededFemale());
                ja.put(""+staff.getExpCommDate());
                ja.put(""+staff.getTempFor());
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+staff.getOID()+"' data-for='showRequisitionForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+staff.getOID()+"' data-for='deleteReqSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    public String staffRequisitionForm(){
	
	//CHECK DATA
	StaffRequisition req = new StaffRequisition();
        
	if(this.oid != 0){
	    try{
		req = PstStaffRequisition.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Employee employee = new Employee();
        try{
            employee = PstEmployee.fetchExc(this.oidUserLogin);
        } catch (Exception exc){

        }
        
        Vector dept_value = new Vector(1,1);
        Vector dept_key = new Vector(1,1);                                                            
        Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
        for (int i = 0; i < listDept.size(); i++) {
                Department dept = (Department) listDept.get(i);
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
        }
        
        Vector sec_value = new Vector(1,1);
        Vector sec_key = new Vector(1,1);                                                            
        Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");                                                          
        for (int i = 0; i < listSec.size(); i++) {
                Section sec = (Section) listSec.get(i);
                sec_key.add(sec.getSection());
                sec_value.add(String.valueOf(sec.getOID()));
        }
        
        Vector pos_value = new Vector(1,1);
        Vector pos_key = new Vector(1,1);
        Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
        for (int i = 0; i < listPos.size(); i++) {
                Position pos = (Position) listPos.get(i);
                pos_key.add(pos.getPosition());
                pos_value.add(String.valueOf(pos.getOID()));
        }
        
        Vector st_value = new Vector(1,1);
        Vector st_key = new Vector(1,1); 
        Vector listSt = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");
        for (int i = 0; i < listSt.size(); i++) {
                EmpCategory st = (EmpCategory) listSt.get(i);
                st_key.add(st.getEmpCategory());
                st_value.add(String.valueOf(st.getOID()));
        }
        
        Vector emp_value = new Vector(1,1);
        Vector emp_key = new Vector(1,1);
        Vector listEmp = PstEmployee.list(0, 0, "", "");
        for (int i = 0; i < listEmp.size(); i++) {
                Employee emp = (Employee) listEmp.get(i);
                emp_key.add(emp.getFullName());
                emp_value.add(String.valueOf(emp.getOID()));
        }
        
        String strDisabledApp = "";
        String strDisabledAck = "";
        if (req.getAcknowledgedBy() != 0){
            strDisabledAck = "disabled='disabled'";
        }
        if (req.getApprovedBy() != 0){
            strDisabledApp = "disabled='disabled'";
        }
        
        
        String returnData = "<div class='row'>"
                + "<div class='col-md-12'>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Requisition Type *</label>"
                        + "<div class='col-md-6'>";
                            for(int i=0;i<PstStaffRequisition.reqtypeValue.length;i++){
                                String strReq = "";
                                if(req.getRequisitionType()==PstStaffRequisition.reqtypeValue[i]){
                                    strReq = "checked";
                                }
                returnData += "<label class='radio-inline'>"
                                + "<input type='radio' "+strReq+" id='reqType' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_REQUISITION_TYPE]+"' value='"+PstStaffRequisition.reqtypeValue[i]+"'> "+PstStaffRequisition.reqtypeKey[i]
                            + "</label>";
                            }
            returnData += "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Department *</label>"
                        + "<div class='col-md-6'>"
                            + ""+ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_DEPARTMENT_ID], "form-control chosen-select", "Select Department...", ""+req.getDepartmentId(), dept_value, dept_key, "id='department'")+" "
                        + "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Section *</label>"
                        + "<div class='col-md-6'>"
                            + ""+ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_SECTION_ID],"form-control chosen-select","Select Section...", "" + req.getSectionId(), sec_value, sec_key, "id='section'")+" "
                        + "</div>"
                    + "</div>"    
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Position *</label>"
                        + "<div class='col-md-6'>"
                            + ""+ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_POSITION_ID],"form-control chosen-select","Select Position...", "" + req.getPositionId(), pos_value, pos_key, "id='position'")+" "
                        + "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Status *</label>"
                        + "<div class='col-md-6'>"
                            + ""+ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_EMP_CATEGORY_ID],"form-control chosen-select","Select Category...", "" + req.getEmpCategoryId(), st_value, st_key, "id='category'")+" "
                        + "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Total Needed</label>"
                        + "<div class='col-md-3'>"
                            + "<input type='text' id='male' class='form-control' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_NEEDED_MALE]+"' value='"+req.getNeededMale()+"'>"
                            + "<div class='help'>male</div>"
                        + "</div>"
                        + "<div class='col-md-3'>"
                            + "<input type='text' id='male' class='form-control' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_NEEDED_FEMALE]+"' value='"+req.getNeededFemale()+"'>"
                            + "<div class='help'>female</div>"
                        + "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Expected Commencing Date</label>"
                        + "<div class='col-md-6'>"
                            + "<input type='text' id='male' class='form-control datepicker' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_EXP_COMM_DATE_STR]+"' value='"+(req.getExpCommDate() != null ? req.getExpCommDate(): Formater.formatDate(new Date(),"yyyy-MM-dd"))+"'>"
                        + "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-12'>Temporary for</label>"
                        + "<div class='col-md-4'>"
                            + "<input type='text' id='temporary' class='form-control' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_TEMP_FOR]+"' value='"+req.getTempFor()+"'>"
                        + "</div>"
                        + "<div class='col-md-8'>months</div>"
                    + "</div>"
                + "</div>"
                + "<div class='col-md-12'>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-4'>Requested By</label>"
                        + "<label class='col-md-4'>Approved By</label>"
                        + "<label class='col-md-4'>Acknowledged By</label>"
                        + "<div class='col-md-4'>"
                            + "<select name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_REQUESTED_BY]+"' id='requested' class='form-control chosen-select'>"
                                + "<option value='"+this.oidUserLogin+"' selected='selected'>"+employee.getFullName()+"</option>"
                            + "</select>"
                        + "</div>"
                        + "<div class='col-md-4'>";
                            if (req.getAcknowledgedBy() != 0){
                    returnData += "<select name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_BY]+"' id='acknowledged' class='form-control chosen-select'>"
                                    + "<option value='"+req.getAcknowledgedBy()+"' selected='selected'>"+PstEmployee.getFullName(req.getAcknowledgedBy()) +"</option>"
                                + "</select>";
                            } else {
                returnData += ""+ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_BY],"form-control chosen-select doApprove","Select Employee...", "" + req.getAcknowledgedBy(), emp_value, emp_key, "id='acknowledged' data-target='#acknowledged' "+strDisabledAck )+" ";
                            }
            returnData += "</div>"   
                        + "<div class='col-md-4'>";
                            if (req.getApprovedBy() != 0){
                            returnData += "<select name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_APPROVED_BY]+"' id='approved' class='form-control chosen-select'>"
                                            + "<option value='"+req.getApprovedBy()+"' selected='selected'>"+PstEmployee.getFullName(req.getApprovedBy()) +"</option>"
                                        + "</select>";
                                    } else {
                    returnData += ""+ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_APPROVED_BY],"form-control chosen-select doApprove","Select Employee...", "" + req.getApprovedBy(), emp_value, emp_key, "id='approved' data-target='#approved' "+strDisabledApp)+" ";
                                    }
                            
                        returnData += "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label class='col-md-4'>Date</label>"
                        + "<label class='col-md-4'>Date</label>"
                        + "<label class='col-md-4'>Date</label>"
                        + "<div class='col-md-4'>"
                            + "<input type='text' id='reqDate' class='form-control datepicker' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_REQUESTED_DATE_STR]+"' value='"+(req.getRequestedDate() != null ? req.getRequestedDate(): Formater.formatDate(new Date(),"yyyy-MM-dd"))+"'>"
                        + "</div>"
                        + "<div class='col-md-4'>"
                            + "<input type='text' id='acknowledgedDate' class='form-control datepicker' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_DATE_STR]+"' value='"+(req.getAcknowledgedDate() != null ? req.getAcknowledgedDate(): Formater.formatDate(new Date(),"yyyy-MM-dd"))+"'>"
                        + "</div>"   
                        + "<div class='col-md-4'>"
                            + "<input type='text' id='approvedDate' class='form-control datepicker' name='"+FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_APPROVED_DATE_STR]+"' value='"+(req.getApprovedDate() != null ? req.getApprovedDate(): Formater.formatDate(new Date(),"yyyy-MM-dd"))+"'>"
                        + "</div>"
                    + "</div>"
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