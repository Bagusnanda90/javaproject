/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.harisma.util.email;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.system.session.dataupload.SessDataUpload;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.blob.ImageLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
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
public class AjaxEmpAssetInventory extends HttpServlet {

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
    private long oidApprover = 0;
    
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String htmlReturn2 = "";
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
        this.oidApprover = FRMQueryString.requestLong(request, "oidApprover");
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        this.htmlReturn2 = "";
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
            this.jSONObject.put("FRM_FIELD_HTML2", this.htmlReturn2);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
	
	response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showEmpAssetForm")){
	  this.htmlReturn = empAssetForm();
	} else if (this.dataFor.equals("getPosition")){
           this.htmlReturn = position(request, this.empId);
           this.htmlReturn2 = selectReceived(request, this.empId);
        } else if(this.dataFor.equals("approveFinger")){
            this.htmlReturn = updateFinger(request);
        }
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmpAssetInventory ctrlEmpAssetInventory = new CtrlEmpAssetInventory(request);
	this.iErrCode = ctrlEmpAssetInventory.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlEmpAssetInventory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpAssetInventory ctrlEmpAssetInventory = new CtrlEmpAssetInventory(request);
	this.iErrCode = ctrlEmpAssetInventory.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlEmpAssetInventory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlEmpAssetInventory ctrlEmpAssetInventory = new CtrlEmpAssetInventory(request);
	this.iErrCode = ctrlEmpAssetInventory.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlEmpAssetInventory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listEmpAssetInventory")){
	    String[] cols = {
                PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMP_ASSET_INVENTORY_ID], 
                PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMP_ASSET_INVENTORY_ID],
                PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM],
		PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME],
                PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_DETAIL], 
                PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_ASSIGNMENT_DATE]};

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
        
        if(dataFor.equals("listEmpAssetInventory")){
	     
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listEmpAssetInventory")){
	    total = PstEmpAssetInventory.getCount(whereClause);
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
        EmpAssetInventory empAsset = new EmpAssetInventory();
        Employee employee = new Employee();
        Position position = new Position();
        String whereClause = "";
        String order ="";
        String document = "";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listEmpAssetInventory")){
               
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listEmpAssetInventory")){
	    listData = PstEmpAssetInventory.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listEmpAssetInventory")){
		empAsset = (EmpAssetInventory) listData.get(i);
                try {
                    employee = PstEmployee.fetchExc(empAsset.getEmployeeId());
                    position = PstPosition.fetchExc(employee.getPositionId());
                } catch (Exception exc){}
                String checkButton = "<input type='checkbox' name='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_EMP_ASSET_INVENTORY_ID]+"' class='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_EMP_ASSET_INVENTORY_ID]+"' value='"+empAsset.getOID()+"'>" ;
                ja.put(""+checkButton);
                ja.put(""+(this.start+i+1));
		ja.put(""+employee.getEmployeeNum());
                ja.put(""+employee.getFullName());
		ja.put(""+position.getPosition());
                ja.put(""+Formater.formatDate(empAsset.getAssignmentDate(),"dd MMMM yyyy"));
                ja.put(""+Formater.formatDate(empAsset.getReturnDate(),"dd MMMM yyyy"));
                ja.put(""+empAsset.getDetail());
                ja.put(""+PstEmpAssetInventory.typeDoc[empAsset.getDocType()]);
                ja.put(""+PstEmpAssetInventory.statusDoc[empAsset.getStatus()]);
		String buttonUpdate = "";
                String buttonSend = "";
                buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+empAsset.getOID()+"' data-for='showEmpAssetForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                //buttonSend += " <button class='btn btn-primary btnsend btn-xs' data-oid='"+empRlvtDoc.getOID()+"' data-empid='"+empRlvtDoc.getEmployeeId()+"' type='button' data-toggle='tooltip' data-placement='top' title='Send Email' ><i class='fa fa-envelope'></i></button> " ;
                ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empAsset.getOID()+"' data-for='deleteEmpAssetInventoryForm' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> " );
                
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
    
    public String empAssetForm(){
        
        //Delete item yang oid 0
        Vector listItem = new Vector();
            String where = PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID] + " = 0";
            listItem = PstEmpAssetInventoryItem.list(0, 0, where, "");
            if (listItem.size() > 0){
                for (int i = 0; i < listItem.size(); i++){
                    EmpAssetInventoryItem item = (EmpAssetInventoryItem) listItem.get(i);
                    String sqlUpdate = "DELETE FROM "+PstEmpAssetInventoryItem.TBL_EMP_ASSET_INVENTORY_ITEM+" ";
                    sqlUpdate += " WHERE "+PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID]+"=0";
                    try {
                        DBHandler.execSqlInsert(sqlUpdate);
                    } catch (Exception exc){}
                }
            }
	
	//CHECK DATA
	EmpAssetInventory empAssetInventory = new EmpAssetInventory();
	Employee employee = new Employee();
        Position pos = new Position();
	if(this.oid != 0){
	    try{
		empAssetInventory = PstEmpAssetInventory.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        try {
            employee = PstEmployee.fetchExc(empAssetInventory.getEmployeeId());
            pos = PstPosition.fetchExc(employee.getPositionId());
        } catch (Exception exc){}
        
        Vector type_key = new Vector(1,1);
	Vector type_val = new Vector(1,1);
        
        for (int i = 0; i < PstEmpAssetInventory.typeDoc.length; i++) {
           type_key.add(""+i);
           type_val.add(""+PstEmpAssetInventory.typeDoc[i]);
        }
        
        Vector listEmployee = PstEmployee.list(0, 0, "", "");
        
        Date dtNw = new Date();
        
        long levelApprover = 0;
        try {
            levelApprover = Long.valueOf(PstSystemProperty.getValueByName("ASSET_APPROVAL_LEVEL_ID"));
        } catch (Exception exc){}
        String whereLevel = PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + levelApprover;
        Vector listApproval = PstEmployee.list(0,0,whereLevel,"");
        
        
        String returnData = ""
                + "<div class='row'>"
                    + "<div class='col-md-6'>"
                        + "<input type='hidden' name='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_EMP_ASSET_INVENTORY_ID]+"'  id='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_EMP_ASSET_INVENTORY_ID]+"' class='form-control' value='"+empAssetInventory.getOID()+"'>"
                        + "<div class='form-group'>"
                            + "<label>Form Type *</label>"
                                + ""+ControlCombo.draw(FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_DOC_TYPE], null, ""+empAssetInventory.getDocType()+"", type_key, type_val, "id="+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_DOC_TYPE], "form-control")+" "
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Employee *</label>"
                                + "<select id='employee' name='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control chosen-select' data-for='getPosition' data-replacement='#position'>"
                                + "<option value='0'>Select Employee...</option>";
                                for (int x=0; x < listEmployee.size(); x++){
                                    Employee emp = (Employee)listEmployee.get(x);
                                    if (empAssetInventory.getEmployeeId() == emp.getOID()){
                                    returnData += "<option value='"+emp.getOID()+"' selected>"+emp.getFullName()+"</option>";    
                                    } else {
                                    returnData += "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                    }
                                }
                        returnData += "</select>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Position</label>"
                                + "<input type='text' name='position'  id='position' class='form-control' value='"+pos.getPosition()+"' disabled>"
                        + "</div>"
                    + "</div>"
                    + "<div class='col-md-6'>"
                        + "<div class='form-group'>"
                            + "<label>Assignment Date</label> "
                            + "<input type='text' name='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_ASSIGNMENT_DATE]+"' id='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control datepicker' value='"+(empAssetInventory.getAssignmentDate() != null ? empAssetInventory.getAssignmentDate() : "" )+"'>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Return Date</label> "
                            + "<input type='text' name='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_RETURN_DATE]+"' id='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_RETURN_DATE]+"' class='form-control datepicker' value='"+(empAssetInventory.getReturnDate() != null ? empAssetInventory.getReturnDate() : "" )+"'>"
                        + "</div>"
                    + "</div>"
                    + "<div class='col-md-12'>"
                        + "<div class='form-group'>"
                            + "<label>Detail</label>"
                            + "<textarea name='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_DETAIL]+"' id='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_DETAIL]+"' class='form-control'>"+empAssetInventory.getDetail()+"</textarea>"
                        + "</div>"
                        + "<label>Item List</label> "        
                        + "<div class='form-group'>"
                            + "<div id='empAssetItemElement'>"
                                + "<table class='table table-bordered table-striped'>"
                                    + "<thead>"
                                        + "<tr>"
                                            + "<th></th>"
                                            + "<th>No</th>"
                                            + "<th>Location</th>"                                
                                            + "<th>Asset Name</th>"
                                            + "<th>Qty</th>"
                                            + "<th>Detail</th>"
                                            + "<th>Purpose</th>"
                                            + "<th>Action</th>"
                                        + "</tr>"
                                    + "</thead>"
                                + "</table>"
                            + "</div>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<button class='btn btn-primary btnadditem btn-xs' data-oid='0' data-oidasset='"+this.oid+"' data-for='showAssetItemForm' type='button' href='#additem'>"
                                + "<i class='fa fa-plus'></i> "
                            + "</button> "
                        + "</div>"
                    + "</div>"
                    + "<div class='col-md-4'>"
                        + "<div class='form-group'>"
                            + "<label>Check By</label>"
                                + "<input type='hidden' id='inputCheck' name='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_CHECK_BY]+"' value='"+empAssetInventory.getCheckBy()+"'>"
                                + "<select id='empCheck' name='empCheck' class='form-control chosen-select doApprove' data-for='check' data-oid='"+this.oid+"' data-target='#empCheck' data-target2='#inputCheck'>";
                                if (empAssetInventory.getCheckBy() != 0 ){
                                    Employee emp = new Employee();
                                    try {
                                        emp = PstEmployee.fetchExc(empAssetInventory.getCheckBy());
                                    } catch (Exception exc){}
                                    returnData += "<option value='"+emp.getOID()+"' selected disabled='disabled'>"+emp.getFullName()+"</option>";
                                } else if (empAssetInventory.getCheckBy() == 0){
                                   returnData += "<option value='0'>Select Employee...</option>";
                                    for (int x=0; x < listEmployee.size(); x++){
                                        Employee emp = (Employee)listEmployee.get(x);
                                        if (empAssetInventory.getCheckBy() == emp.getOID()){
                                            returnData += "<option value='"+emp.getOID()+"' selected disabled='disabled'>"+emp.getFullName()+"</option>";
                                        } else {
                                            returnData += "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                        }
                                    }
                                }
                                
                                
                returnData += "</select>"
                        + "</div>"
                        + "<div class='form-group'>"
                                + "<label>Check Date</label>"
                                + "<input type='text' name='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_CHECK_DATE]+"' class='form-control datepicker' value='"+(empAssetInventory.getCheckDate() != null ? empAssetInventory.getCheckDate() : Formater.formatDate(new Date(), "yyyy-MM-dd"))+"'>"
                        + "</div>"
                    + "</div>"
                    + "<div class='col-md-4'>"
                        + "<div class='form-group'>"
                            + "<label>Approved By</label>"
                            + "<input type='hidden' id='inputApprove' name='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_APPROVED_BY]+"' value='"+empAssetInventory.getApprovedBy()+"'>"
                            + "<select id='empApprove' name='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_APPROVED_BY]+"'  class='form-control chosen-select doApprove' data-for='approve' data-oid='"+this.oid+"' data-target='#empApprove' data-target2='#inputApprove'>";
                                if (empAssetInventory.getApprovedBy() != 0 ){
                                    Employee emp = new Employee();
                                    try {
                                        emp = PstEmployee.fetchExc(empAssetInventory.getApprovedBy());
                                    } catch (Exception exc){}
                                    returnData += "<option value='"+emp.getOID()+"' selected disabled='disabled'>"+emp.getFullName()+"</option>";
                                } else if (empAssetInventory.getApprovedBy() == 0){
                                   returnData += "<option value='0'>Select Employee...</option>";
                                    for (int x=0; x < listApproval.size(); x++){
                                        Employee emp = (Employee)listApproval.get(x);
                                        if (empAssetInventory.getApprovedBy() == emp.getOID()){
                                            returnData += "<option value='"+emp.getOID()+"' selected disabled='disabled'>"+emp.getFullName()+"</option>";                                        
                                        } else {
                                            returnData += "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                        }
                                    }
                                }
                            
                        
                                
                returnData += "</select>"
                        + "</div>"
                        + "<div class='form-group'>"
                                + "<label>Approved Date</label>"
                                + "<input type='text' name='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_APPROVED_DATE]+"' class='form-control datepicker' value='"+(empAssetInventory.getApprovedDate() != null ? empAssetInventory.getApprovedDate() : Formater.formatDate(new Date(), "yyyy-MM-dd"))+"'>"
                        + "</div>"
                    + "</div>"
                    + "<div class='col-md-4'>"
                        + "<div class='form-group'>"
                            + "<label>Received By</label>"
                            + "<input type='hidden' id='inputReceived' name='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_RECEIVED_BY]+"' value='"+empAssetInventory.getReceivedBy()+"'>"
                            + "<select id='empReceived' name='"+ FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_RECEIVED_BY]+"' class='form-control chosen-select doApprove' data-for='received' data-oid='"+this.oid+"' data-target='#empReceived' data-target2='#inputReceived'>";
                                Employee emp = new Employee();
                                if (empAssetInventory.getReceivedBy() != 0 ){
                                    try {
                                        emp = PstEmployee.fetchExc(empAssetInventory.getReceivedBy());
                                    } catch (Exception exc){}
                                    returnData += "<option value='"+emp.getOID()+"' selected disabled='disabled'>"+emp.getFullName()+"</option>";
                                } else if (empAssetInventory.getEmployeeId() != 0){
                                    try {
                                        emp = PstEmployee.fetchExc(empAssetInventory.getEmployeeId());
                                    } catch (Exception exc){}
                                    returnData += "<option value='0'>Select Employee...</option>"
                                            + "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                }
                                
                returnData += "</select>"
                        + "</div>"
                        + "<div class='form-group'>"
                                + "<label>Received Date</label>"
                                + "<input type='text' name='"+FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_RECEIVED_DATE]+"' class='form-control datepicker' value='"+(empAssetInventory.getReceivedDate() != null ? empAssetInventory.getReceivedDate() : Formater.formatDate(new Date(), "yyyy-MM-dd")) +"'>"
                        + "</div>"
                    + "</div>"            
                + "</div>";
                    
	return returnData;
    }
    
    public String position(HttpServletRequest request, long empId){
        String data = "";
        Employee emp = new Employee();
        Position pos = new Position();
        try{
            emp = PstEmployee.fetchExc(empId);
            pos = PstPosition.fetchExc(emp.getPositionId());
        } catch (Exception exc){}
        
        data +=  pos.getPosition();
        
        return data;
    }
    
    private String updateFinger(HttpServletRequest request){
        String htmlReturn="";
        String spvName ="";
        String dataFor = "";
        long oidDetail = 0;
        long empId = 0;
        EmpDocGeneral empDocGeneral = new EmpDocGeneral();
        
        oidDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OID_DETAIL");
        empId = FRMQueryString.requestLong(request, "FRM_FIELD_EMP_ID");
        dataFor = FRMQueryString.requestString(request, "GENERAL_DATA_FOR");
        
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(empId);
        } catch (Exception exc){}
        
        htmlReturn += "<option value='"+emp.getOID()+"' selected>"+emp.getFullName()+"</option>";
        
        String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
        sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_FINGER_CHECK]+"=0";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+empId;
        try {
            DBHandler.execUpdate(sqlUpdateEmp);
        } catch (Exception exc){}
        
        return htmlReturn;
        }    
    
    public String selectReceived (HttpServletRequest request, long employeeId){
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(employeeId);
        } catch (Exception exc){}
        String data = "";
        data += "<option value='0'>Select Employee</option>"
                + "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
        
        
        
        return data;
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
    