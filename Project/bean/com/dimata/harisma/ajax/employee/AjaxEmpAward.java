/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.harisma.form.employee.CtrlEmpAward;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.form.employee.FrmEmpAward;
import com.dimata.harisma.report.JurnalDocument;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @author Acer
 */
public class AjaxEmpAward extends HttpServlet {

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
    private long awardId = 0;
    private long datachange = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    private String object = "";
    private String htmlReturn2 = "";
    
    
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
        this.awardId = FRMQueryString.requestLong(request, "awardId");
        this.datachange = FRMQueryString.requestLong(request, "datachange");
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        this.htmlReturn2 = "";
        this.empName = FRMQueryString.requestString(request, "empName");
        this.object = FRMQueryString.requestString(request, "object");
	
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
            this.jSONObject.put("FRM_FIELD_HTML_2", this.htmlReturn2);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
	
	response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showEmpAwardForm")){
	  this.htmlReturn = empAwardForm();
	} else if (this.dataFor.equals("generateDocAward")){
          this.htmlReturn = generateDoc();  
        } else if (this.dataFor.equals("showEmpAwardFormDoc")){
          this.htmlReturn = empAwardFormDoc();  
        }
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmpAward ctrlEmpAward = new CtrlEmpAward(request);
	this.iErrCode = ctrlEmpAward.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete, this.object);
	String message = ctrlEmpAward.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        if (this.dataFor.equals("approveFinger")){
            this.htmlReturn = updateFinger(request);
        }
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpAward ctrlEmpAward = new CtrlEmpAward(request);
	this.iErrCode = ctrlEmpAward.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlEmpAward.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlEmpAward ctrlEmpAward = new CtrlEmpAward(request);
        this.iErrCode = ctrlEmpAward.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
        String message = ctrlEmpAward.getMessage();
        this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listEmpAward")){
	    String[] cols = { PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID],
		PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID],
                PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE],
                PstEmpAward.fieldNames[PstEmpAward.FLD_TITLE],
		PstEmpAward.fieldNames[PstEmpAward.FLD_DIVISION_ID],
                PstEmpAward.fieldNames[PstEmpAward.FLD_PROVIDER_ID],
                PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE],
                PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DESC]};

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
        
        if(dataFor.equals("listEmpAward")){
	    whereClause += " ("+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR "+PstEmpAward.fieldNames[PstEmpAward.FLD_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR "+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR CON."+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR TYPE."+PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DESC]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR "+PstEmpAward.fieldNames[PstEmpAward.FLD_DOCUMENT]+" LIKE '%"+this.searchTerm+"%')"
                    + " AND (DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_STATUS]+" = 2 OR EMP."
                    + ""+PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+" = 0)"
                    + " AND " +PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listEmpAward")){
	    total = PstEmpAward.getCountDataTable(whereClause);
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
        EmpAward empAward = new EmpAward();
        Division division = new Division();
	ContactList contList = new ContactList();
        AwardType awardType = new AwardType();
        EmpDoc empDoc = new EmpDoc();
	String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listEmpAward")){
               whereClause += " ("+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR "+PstEmpAward.fieldNames[PstEmpAward.FLD_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR "+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR CON."+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR TYPE."+PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR EMP."+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DESC]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR "+PstEmpAward.fieldNames[PstEmpAward.FLD_DOCUMENT]+" LIKE '%"+this.searchTerm+"%')"
                    + " AND (DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_STATUS]+" = 2 OR EMP."
                    + ""+PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+" = 0)"
                    + " AND " +PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listEmpAward")){
	    listData = PstEmpAward.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listEmpAward")){
		empAward = (EmpAward) listData.get(i);
                try {
                    awardType = PstAwardType.fetchExc(empAward.getAwardType());
                    division = PstDivision.fetchExc(empAward.getDivisionId());
                    contList = PstContactList.fetchExc(empAward.getProviderId());
                    empDoc = PstEmpDoc.fetchExc(empAward.getDocId());
                    
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_ID]+"' class='"+FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_ID]+"' value='"+empAward.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }    
                ja.put(""+(this.start+i+1));
		ja.put(""+empAward.getAwardDate());
		ja.put(""+empAward.getTitle());
                ja.put(""+division.getDivision());
                
                String provider = "-";
                if(empAward.getProviderId() > 0){
                    provider = contList.getCompName();
                }
                
                ja.put(""+empAward.getExternalFrom());
                ja.put(""+awardType.getAwardType());
                ja.put(""+empAward.getAwardDescription());
                String document="";
                if(!(empAward.getDocument().equals(""))){
                        document = approot+"imgdoc/"+  empAward.getDocument();
                    }
                String buttonTemplate= "<button class='btn btn-success btneditormodal btn-xs' data-oid='"+empAward.getDocId()+"' data-template1='"+empDoc.getDoc_master_id()+"' data-for='editor' type='button' data-toggle='tooltip' data-placement='top' title='Editor'><i class='fa fa-file'></i></button> ";
                String buttonView = "<a href='"+document+"' class='fancybox' target='_blank'> "+empAward.getDocument()+"</a>";
                
                //String buttonDocument = "<a href='doc_award_details.jsp?oidEmpDoc="+empAward.getDocId()+"' class='btn btn-info btn-sm fancybox fancybox.iframe' data-toggle='tooltip' data-placement='top' title='Details Doc'><i class='fa fa-file'></i></a>";
                //String buttonDocument = "<a href='doc_award_details.jsp?awardId="+empAward.getOID()+"' class='btn btn-info btn-sm fancybox fancybox.iframe' data-toggle='tooltip' data-placement='top' title='Details Doc'><i class='fa fa-file'></i></a>";
                //String buttonGenerate = "<button class='btn btn-info btneditgeneral btn-sm' data-oid='"+empAward.getOID()+"' data-for='generateDocAward' type='button' data-toggle='tooltip' data-placement='top' title='Generate Document'><i class='fa fa-refresh'></i></button> ";
                ja.put(""+buttonView);
                //ja.put(""+buttonDocument);
                
		String buttonUpdate = "";
		if(privUpdate){
                    if(empAward.getAcknowledgeStatus() == 0){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+empAward.getOID()+"' data-for='showEmpAwardForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		    //buttonUpdate += "<a href='emp_doc_award.jsp?awardId="+empAward.getOID()+"&dataFor=generate' class='btn btn-info btn-sm fancybox fancybox.iframe' data-toggle='tooltip' data-placement='top' title='Generate Document'><i class='fa fa-refresh'></i></a> ";
                        buttonUpdate += "<a href='doc_award_details.jsp?awardId="+empAward.getOID()+"' class='btn btn-warning btn-xs fancybox fancybox.iframe' data-toggle='tooltip' data-placement='top' title='Details Doc'><i class='fa fa-file'></i></a> ";
                        buttonUpdate += "<button class='btn btn-warning btnupload btn-xs' data-oid='"+empAward.getOID()+"' data-for='showEmpAwardForm' type='button' data-toggle='tooltip' data-placement='top' title='Upload'><i class='fa fa-cloud-upload'></i></button> ";
                        buttonUpdate += "<button class='btn btn-warning btnacknowledge btn-xs' data-oid='"+empAward.getOID()+"' data-for='showEmpAwardAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledge'><i class='fa fa-thumbs-up'></i></button> ";
                    } else if(empAward.getAcknowledgeStatus() > 0){
                        buttonUpdate = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+empAward.getOID()+"' data-for='showEmpAwardForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                        //buttonUpdate += "<a href='emp_doc_award.jsp?awardId="+empAward.getOID()+"&dataFor=generate' class='btn btn-info btn-sm fancybox fancybox.iframe' data-toggle='tooltip' data-placement='top' title='Generate Document'><i class='fa fa-refresh'></i></a> ";
                        buttonUpdate += "<a href='doc_award_details.jsp?awardId="+empAward.getOID()+"' class='btn btn-success btn-xs fancybox fancybox.iframe' data-toggle='tooltip' data-placement='top' title='Details Doc'><i class='fa fa-file'></i></a> ";
                        buttonUpdate += "<button class='btn btn-success btnupload btn-xs' data-oid='"+empAward.getOID()+"' data-for='showEmpAwardForm' type='button' data-toggle='tooltip' data-placement='top' title='Upload'><i class='fa fa-cloud-upload'></i></button> ";
                        buttonUpdate += "<button class='btn btn-success btn-xs' data-oid='"+empAward.getOID()+"' data-for='showEmpAwardAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledged'><i class='fa fa-check-circle'></i></button> ";
                    }
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empAward.getOID()+"' data-for='deleteAwardSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
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
    
    public String empAwardForm(){
	
	//CHECK DATA
	EmpAward empAward = new EmpAward();
	
	if(this.oid != 0){
	    try{
		empAward = PstEmpAward.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector com_key = new Vector(1,1);
	Vector com_val = new Vector(1,1);
        
        com_key.add("0");
        com_val.add("Select Company...");
        
        Vector listCompany = PstCompany.list(0, 0, "", "");
        if (listCompany != null && listCompany.size() > 0) {
            for (int i = 0; i < listCompany.size(); i++) {
               Company company = (Company) listCompany.get(i);
               com_key.add(""+company.getOID());
               com_val.add(""+company.getCompany());
            }
        }
        
        Vector div_key = new Vector(1,1);
        Vector div_val = new Vector(1,1);
        div_key.add("0");
        div_val.add("Select Division...");
        Vector listDivision = PstDivision.list(0,0, "", "");
        if (listDivision != null && listDivision.size() > 0) {
            for (int i = 0; i < listDivision.size(); i++) {
               Division division = (Division) listDivision.get(i);
               div_key.add(""+division.getOID());
               div_val.add(""+division.getDivision());
            }
        }
        
        Vector type_key = new Vector(1,1);
        Vector type_val = new Vector(1,1);
        
        type_key.add("");
        type_val.add("Select Award Type...");
        
        Vector listAwardType = PstAwardType.list(0,0,"","");
        if(listAwardType != null && listAwardType.size() > 0){
            for (int i = 0; i < listAwardType.size(); i++){
                AwardType awardType = (AwardType) listAwardType.get(i);
                type_key.add(""+awardType.getOID());
                type_val.add(""+awardType.getAwardType());
            }
        }
        
        Vector con_key = new Vector(1,1);
        Vector con_val = new Vector(1,1);
        con_key.add("0");
        con_val.add("Select Reference");
        
        Vector listProvider = PstContactList.list(0, 0, "", "");
        if (listProvider != null && listProvider.size() > 0) {
            for (int i = 0; i < listProvider.size(); i++) {
               ContactList contList = (ContactList) listProvider.get(i);
               con_key.add(""+contList.getOID());
               con_val.add(""+contList.getCompName());
            }
        }
        
        String checkedIn = "";
        String disabledIn = "disabled";
        String checkedEx = "";
        String disabledEx = "disabled";
        if (empAward.getDivisionId() != 0){
            checkedIn = "checked";
            disabledIn = "";
        }
        if (empAward.getProviderId() != 0){
            checkedEx = "checked";
            disabledEx = "";
        }
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EMPLOYEE_ID]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control' value='"+this.empId+"'>"
                + "<input type='hidden' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOCUMENT]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOCUMENT]+"' class='form-control' value='"+empAward.getDocument()+"'>"
                + "<div class='form-group'>"
		    + "<label>Award Title *</label>"
		    + "<input type='text' placeholder='Input Award Title' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]+"' class='form-control' value='"+empAward.getTitle()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Date</label>"
                    + "<input type='text' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DATE]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DATE]+"' class='form-control datepicker' value='"+(empAward.getAwardDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(empAward.getAwardDate(),"yyyy-MM-dd"))+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Award Type *</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_TYPE], null, ""+empAward.getAwardType()+"", type_key, type_val, "id="+FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_TYPE], "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
                    + "<label class='radio-inline'><input type='radio' name='rb' value='0' id='internal' "+checkedIn+">Internal</label>"
                + "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Company</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_COMPANY_ID], null, ""+empAward.getCompanyId()+"", com_key, com_val, disabledIn, "form-control internal")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Division</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DIVISION_ID], null, ""+empAward.getDivisionId()+"", div_key, div_val, disabledIn, "form-control internal")+" "
		+ "</div>"
                + "<div class='form-group'>"
                    + "<label class='radio-inline'><input type='radio' name='rb' value='1' id='external' "+checkedEx+">External</label>"
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Award From</label>"
		    + "<input type='text' placeholder='Input Award External' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EXTERNAL_FROM]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EXTERNAL_FROM]+"' class='form-control external' "+disabledEx+" >"
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' placeholder='Type Description Award...' class='form-control' id='" + FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DESC] +"' name='" + FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DESC] +"'>"+ empAward.getAwardDescription()+"</textarea> "
		+ "</div>"
	    + "</div>"
	+ "</div>";
	return returnData;
    }
    
    public String empAwardFormDoc(){
	
	//CHECK DATA
	EmpAward empAward = new EmpAward();
	
	if(this.oid != 0){
	    try{
		empAward = PstEmpAward.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector com_key = new Vector(1,1);
	Vector com_val = new Vector(1,1);
        
        com_key.add("0");
        com_val.add("-SELECT-");
        
        Vector listCompany = PstCompany.list(0, 0, "", "");
        if (listCompany != null && listCompany.size() > 0) {
            for (int i = 0; i < listCompany.size(); i++) {
               Company company = (Company) listCompany.get(i);
               com_key.add(""+company.getOID());
               com_val.add(""+company.getCompany());
            }
        }
        
        Vector div_key = new Vector(1,1);
        Vector div_val = new Vector(1,1);
        div_key.add("0");
        div_val.add("-SELECT-");
        Vector listDivision = PstDivision.list(0,0, "", "");
        if (listDivision != null && listDivision.size() > 0) {
            for (int i = 0; i < listDivision.size(); i++) {
               Division division = (Division) listDivision.get(i);
               div_key.add(""+division.getOID());
               div_val.add(""+division.getDivision());
            }
        }
        
        Vector type_key = new Vector(1,1);
        Vector type_val = new Vector(1,1);
        
        type_key.add("");
        type_val.add("-Select-");
        
        Vector listAwardType = PstAwardType.list(0,0,"","");
        if(listAwardType != null && listAwardType.size() > 0){
            for (int i = 0; i < listAwardType.size(); i++){
                AwardType awardType = (AwardType) listAwardType.get(i);
                type_key.add(""+awardType.getOID());
                type_val.add(""+awardType.getAwardType());
            }
        }
        
        Vector con_key = new Vector(1,1);
        Vector con_val = new Vector(1,1);
        con_key.add("0");
        con_val.add("-SELECT-");
        
        Vector listProvider = PstContactList.list(0, 0, "", "");
        if (listProvider != null && listProvider.size() > 0) {
            for (int i = 0; i < listProvider.size(); i++) {
               ContactList contList = (ContactList) listProvider.get(i);
               con_key.add(""+contList.getOID());
               con_val.add(""+contList.getCompName());
            }
        }
        
        Vector emp_key = new Vector(1,1);
        Vector emp_val = new Vector(1,1);
        emp_key.add("0");
        emp_val.add("-SELECT-");
        
        Vector listEmployee = PstEmployee.list(0, 0, "", "");
        if (listEmployee != null && listEmployee.size() > 0) {
            for (int i = 0; i < listEmployee.size(); i++) {
               Employee employee = (Employee) listEmployee.get(i);
               emp_key.add(""+employee.getOID());
               emp_val.add(""+employee.getFullName());
            }
        }
        
        String checkedIn = "";
        String disabledIn = "disabled";
        String checkedEx = "";
        String disabledEx = "disabled";
        if (empAward.getDivisionId() != 0){
            checkedIn = "checked";
            disabledIn = "";
        }
        if (empAward.getProviderId() != 0){
            checkedEx = "checked";
            disabledEx = "";
        }
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_ACKNOWLEDGE_STATUS]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_ACKNOWLEDGE_STATUS]+"' class='form-control' value='"+empAward.getAcknowledgeStatus()+"'>"
                + "<input type='hidden' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOCUMENT]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOCUMENT]+"' class='form-control' value='"+empAward.getDocument()+"'>"
                + "<div class='form-group'>"
		    + "<label>Employee *</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EMPLOYEE_ID], null, ""+empAward.getEmployeeId()+"", emp_key, emp_val, "id="+FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EMPLOYEE_ID], "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Award Title *</label>"
		    + "<input type='text' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]+"' class='form-control' value='"+empAward.getTitle()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Date</label>"
                    + "<input type='text' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DATE]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DATE]+"' class='form-control datepicker' value='"+(empAward.getAwardDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(empAward.getAwardDate(),"yyyy-MM-dd"))+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
		    + "<label>Award Type *</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_TYPE], null, ""+empAward.getAwardType()+"", type_key, type_val, "id="+FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_TYPE], "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
                    + "<label class='radio-inline'><input type='radio' name='rb' value='0' id='internal' "+checkedIn+">Internal</label>"
                + "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Company</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_COMPANY_ID], null, ""+empAward.getCompanyId()+"", com_key, com_val, disabledIn, "form-control internal")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Division</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DIVISION_ID], null, ""+empAward.getDivisionId()+"", div_key, div_val, disabledIn, "form-control internal")+" "
		+ "</div>"
                + "<div class='form-group'>"
                    + "<label class='radio-inline'><input type='radio' name='rb' value='1' id='external' "+checkedEx+">External</label>"
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Award From</label>"
		    + "<input type='text' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EXTERNAL_FROM]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EXTERNAL_FROM]+"' class='form-control external' "+disabledEx+" >"
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' class='form-control' id='" + FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DESC] +"' name='" + FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DESC] +"'>"+ empAward.getAwardDescription()+"</textarea> "
		+ "</div>"
	    + "</div>"
	+ "</div>";
	return returnData;
    }    
    
    public String generateDoc() {
        String returnData = ""
                + "<div class='row'>"
                    + "<div class='col-md-12'>";

        long oidEmpDoc = 0;
        long oidDoc = 0;
        EmpAward award = new EmpAward();
        Employee emp = new Employee();
        DocMaster docMaster = new DocMaster();
        String docMasterId = PstSystemProperty.getValueByName("DOC_MASTER_AWARD_ID");
        
        try {
            award = PstEmpAward.fetchExc(this.oid);
        } catch (Exception exc) {

        }

        try {
            emp = PstEmployee.fetchExc(award.getEmployeeId());
        } catch (Exception exc) {

        }
        
        try {
            docMaster = PstDocMaster.fetchExc(Long.valueOf(docMasterId));
        } catch (Exception exc){

        }
        
        if (!(docMaster.getDoc_number().equals(""))){
            String docNumber = docMaster.getDoc_number();
            String[] docNumArray = docMaster.getDoc_number().split("/");
            for (int i = 0; i < docNumArray.length; i++) {
                String element = docNumArray[i];
                if (element.equals("NUMBER")){
                    String whereDocMaster = PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_MASTER_ID] + " = " + docMasterId;
                    Vector listDocMaster = PstDocMaster.list(0, 0, whereDocMaster, "");
                    if (listDocMaster != null && listDocMaster.size() > 0){
                        for (int x = 0; x < listDocMaster.size() ; x++){
                            int number = 0;
                            DocMaster docMaster1 = (DocMaster) listDocMaster.get(x);
                            String[] docNumArray1 = docMaster1.getDoc_number().split("/");
                            number = Integer.valueOf(docNumArray[i]) + 1;
                            docNumber = docNumber.replaceAll("NUMBER", ""+number);
                        }
                    }
                } else if (element.equals("MM")){
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int month = cal.get(Calendar.MONTH);
                    docNumber = docNumber.replaceAll("MM", ""+month);
                } else if (element.equals("YYYY")){
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    docNumber = docNumber.replaceAll("MM", ""+year);
                }

            }
        }

        EmpDoc empDocAward = new EmpDoc();
        empDocAward.setDoc_title(award.getTitle() + " " + emp.getFullName());
        empDocAward.setDoc_master_id(Long.valueOf(docMasterId));
        empDocAward.setRequest_date(award.getAwardDate());
        empDocAward.setDoc_number("01");
        empDocAward.setDate_of_issue(award.getAwardDate());

        if (award.getDocId() == 0) {
            try {
                oidDoc = PstEmpDoc.insertExc(empDocAward);
            } catch (DBException dbexc) {

            } catch (Exception exc) {

            }

            oidEmpDoc = oidDoc;
            EmpAward empAward = new EmpAward();
            try {
                empDocAward = PstEmpDoc.fetchExc(oidDoc);
            } catch (Exception exc) {
            }

            award.setDocId(oidDoc);
            try {
               long oidAward = PstEmpAward.updateExc(award);
            } catch (DBException dbexc) {

            } catch (Exception exc) {

            }
            EmpDocList empDocList = new EmpDocList();
            empDocList.setEmployee_id(award.getEmployeeId());
            empDocList.setEmp_doc_id(oidEmpDoc);
            empDocList.setObject_name("TRAINNER2");

            try {
                long oidEmpList = PstEmpDocList.insertExc(empDocList);
            } catch (DBException dbexc) {

            } catch (Exception exc) {

            }
            EmpDocField empDocField = new EmpDocField();
            empDocField.setObject_name("AWARD");
            empDocField.setObject_type(0);
            empDocField.setValue(award.getTitle());
            empDocField.setEmp_doc_id(oid);
            empDocField.setClassName("ALLFIELD");

            try {
                long oidEmpDocField = PstEmpDocField.insertExc(empDocField);
            } catch (DBException dbexc) {

            } catch (Exception exc) {

            }
        } else {
            empDocAward.setOID(award.getDocId());
            try {
                oidDoc = PstEmpDoc.updateExc(empDocAward);
            } catch (DBException dbexc) {

            } catch (Exception exc) {

            }
            oidEmpDoc = oidDoc;
        }

        EmpDoc empDoc = new EmpDoc();
        DocMaster empDocMaster = new DocMaster();
        try {
            empDoc = PstEmpDoc.fetchExc(oidEmpDoc);
        } catch (Exception exc) {
        }

        if (empDoc != null) {
            try {
                empDocMaster = PstDocMaster.fetchExc(empDoc.getDoc_master_id());
            } catch (Exception exc) {

            }

            try {
                returnData += PstDocMasterTemplate.getTemplateText(empDoc.getDoc_master_id());
            } catch (Exception exc) {
            }
        }
        
        String strYear = "";
        String strMonth = "";
        String strDay = "";

        returnData = returnData.replace("&lt", "<");

        returnData = returnData.replace("<;", "<");
        returnData = returnData.replace("&gt", ">");
        String tanpaeditor = returnData;
        String subString = "";
        String stringResidual = returnData;
        Vector vNewString = new Vector();

        Hashtable empDOcFecthH = new Hashtable();

        try {
            empDOcFecthH = PstEmpDoc.fetchExcHashtable(oidEmpDoc);
        } catch (Exception e) {
        }

        String where1 = " " + PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
        Hashtable hlistEmpDocField = PstEmpDocField.Hlist(0, 0, where1, "");

        int startPosition = 0;
        int endPosition = 0;
        try {
            do {

                ObjectDocumentDetail objectDocumentDetail = new ObjectDocumentDetail();
                startPosition = stringResidual.indexOf("${") + "${".length();
                endPosition = stringResidual.indexOf("}", startPosition);
                subString = stringResidual.substring(startPosition, endPosition);

                //cek substring
                String[] parts = subString.split("-");
                String objectName = "";
                String objectType = "";
                String objectClass = "";
                String objectStatusField = "";
                try {
                    objectName = parts[0];
                    objectType = parts[1];
                    objectClass = parts[2];
                    objectStatusField = parts[3];
                } catch (Exception e) {
                    System.out.printf("pastikan 4 parameter");
                }

                //cek dulu apakah hanya object name atau tidak
                if (!objectName.equals("") && !objectType.equals("") && !objectClass.equals("") && !objectStatusField.equals("")) {

                    //jika list maka akan mencari penutupnya..
                    if (objectType.equals("SINGLE") && objectStatusField.equals("START")) {
                        String add = "<a href=\"javascript:cmdAddEmpSingle('" + objectName + "','" + oidEmpDoc + "')\">add employee</a></br>";
                        returnData = returnData.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
                        Vector listEmp = PstEmpDocList.list(0, 0, whereC, "");
                        EmpDocList empDocList = new EmpDocList();
                        Employee employeeFetch = new Employee();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmp.size() > 0) {
                            try {
                                empDocList = (EmpDocList) listEmp.get(listEmp.size() - 1);
                                employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                            } catch (Exception e) {
                            }

                        }

                        int xx = 2;
                        int startPositionOfFormula = 0;
                        int endPositionOfFormula = 0;
                        String subStringOfFormula = "";
                        String residuOfsubStringOfTd = textString;
                        do {

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                            subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 

                            String[] partsOfFormula = subStringOfFormula.split("-");
                            String objectNameFormula = partsOfFormula[0];
                            String objectTypeFormula = partsOfFormula[1];
                            String objectTableFormula = partsOfFormula[2];
                            String objectStatusFormula = partsOfFormula[3];
                            String value = "";
                            if (objectTableFormula.equals("EMPLOYEE")) {
                                value = (String) HashtableEmp.get(objectStatusFormula);
                                if (value == null) {
                                    value = "-";
                                }

                            } else {
                                System.out.print("Selain Object Employee belum bisa dipanggil");
                            }

                            returnData = returnData.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);

                        returnData = returnData.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");

                    } else if (objectType.equals("LIST") && objectStatusField.equals("START")) {
                        String add = "<a href=\"javascript:cmdAddEmp('" + objectName + "','" + oidEmpDoc + "')\">add employee</a></br>";
                        returnData = returnData.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 

                        //mencari jumlah table table
                        int startPositionOfTable = 0;
                        int endPositionOfTable = 0;
                        String subStringOfTable = "";
                        String residueOfTextString = textString;
                        do {
                            //cari tag table pembuka
                            startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                            //int tt = textString.indexOf("&lttable") + ((textString.indexOf("&gt") + 3)-textString.indexOf("&lttable"));
                            endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                            subStringOfTable = residueOfTextString.substring(startPositionOfTable, endPositionOfTable);//isi table 

                            //mencari body 
                            int startPositionOfBody = subStringOfTable.indexOf("<tbody>") + "<tbody>".length();
                            int endPositionOfBody = subStringOfTable.indexOf("</tbody>", startPositionOfBody);
                            String subStringOfBody = subStringOfTable.substring(startPositionOfBody, endPositionOfBody);//isi body

                            //mencari tr pertama pada table
                            int startPositionOfTr1 = subStringOfBody.indexOf("<tr>") + "<tr>".length();
                            int endPositionOfTr1 = subStringOfBody.indexOf("</tr>", startPositionOfTr1);
                            String subStringOfTr1 = subStringOfBody.substring(startPositionOfTr1, endPositionOfTr1);

                            String subStringOfBody2 = subStringOfBody.substring(endPositionOfTr1, subStringOfBody.length());//isi body setelah dipotong tr pertama
                            int startPositionOfTr2 = subStringOfBody2.indexOf("<tr>") + "<tr>".length();
                            int endPositionOfTr2 = subStringOfBody2.indexOf("</tr>", startPositionOfTr2);
                            String subStringOfTr2 = subStringOfBody2.substring(startPositionOfTr2, endPositionOfTr2);

                            //disini diisi perulanganya
                            String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
                            Vector listEmp = PstEmpDocList.list(0, 0, whereC, "");

                            //baca table dibawahnya
                            String stringTrReplace = "";
                            if (listEmp.size() > 0) {
                                for (int list = 0; list < listEmp.size(); list++) {

                                    EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                    Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                    Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());

                                    stringTrReplace = stringTrReplace + "<tr>";

                                    //menghitung jumlah td html
                                    int startPositionOfTd = 0;
                                    int endPositionOfTd = 0;
                                    String subStringOfTd = "";
                                    String residuOfsubStringOfTr2 = subStringOfTr2;
                                    int jumlahtd = 0;

                                    do {

                                        stringTrReplace = stringTrReplace + "<td>";

                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                        subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 

                                        int startPositionOfFormula = 0;
                                        int endPositionOfFormula = 0;
                                        String subStringOfFormula = "";
                                        String residuOfsubStringOfTd = subStringOfTd;
                                        do {

                                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                            subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 

                                            String[] partsOfFormula = subStringOfFormula.split("-");
                                            String objectNameFormula = partsOfFormula[0];
                                            String objectTypeFormula = partsOfFormula[1];
                                            String objectTableFormula = partsOfFormula[2];
                                            String objectStatusFormula = partsOfFormula[3];
                                            String value = "";
                                            if (objectTableFormula.equals("EMPLOYEE")) {
                                                value = (String) HashtableEmp.get(objectStatusFormula);

                                            } else {
                                                System.out.print("Selain Object Employee belum bisa dipanggil");
                                            }

                                            stringTrReplace = stringTrReplace + value;

                                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                        } while (endPositionOfFormula > 0);

                                        residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                        jumlahtd = jumlahtd + 1;

                                        stringTrReplace = stringTrReplace + "</td>";
                                    } while (endPositionOfTd > 0);

                                }
                            }

                            returnData = returnData.replace("<tr>" + subStringOfTr2 + "</tr>", stringTrReplace);
                            tanpaeditor = tanpaeditor.replace("<tr>" + subStringOfTr2 + "</tr>", stringTrReplace);
                            //tutup perulanganya

                            //setelah baca td maka akan membuat td baru... disini
                            residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());

                            startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                            endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);

                        } while (endPositionOfTable > 0);

                        returnData = returnData.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");

                    } else if (objectType.equals("LISTMUTATION") && objectStatusField.equals("START")) {
                        String add = "<a href=\"javascript:cmdAddEmpNew('" + objectName + "','" + oidEmpDoc + "')\">add employee</a></br>";
                        returnData = returnData.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 

                        //mencari jumlah table table
                        int startPositionOfTable = 0;
                        int endPositionOfTable = 0;
                        String subStringOfTable = "";
                        String residueOfTextString = textString;
                        do {
                            //cari tag table pembuka
                            startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                            //int tt = textString.indexOf("&lttable") + ((textString.indexOf("&gt") + 3)-textString.indexOf("&lttable"));
                            endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                            subStringOfTable = residueOfTextString.substring(startPositionOfTable, endPositionOfTable);//isi table 

                            //mencari body 
                            int startPositionOfBody = subStringOfTable.indexOf("<tbody>") + "<tbody>".length();
                            int endPositionOfBody = subStringOfTable.indexOf("</tbody>", startPositionOfBody);
                            String subStringOfBody = subStringOfTable.substring(startPositionOfBody, endPositionOfBody);//isi body

                            //mencari tr pertama pada table
                            int startPositionOfTr1 = subStringOfBody.indexOf("<tr>") + "<tr>".length();
                            int endPositionOfTr1 = subStringOfBody.indexOf("</tr>", startPositionOfTr1);
                            String subStringOfTr1 = subStringOfBody.substring(startPositionOfTr1, endPositionOfTr1);

                            String subStringOfBody2 = subStringOfBody.substring(endPositionOfTr1, subStringOfBody.length());//isi body setelah dipotong tr pertama
                            int startPositionOfTr2 = subStringOfBody2.indexOf("<tr>") + "<tr>".length();
                            int endPositionOfTr2 = subStringOfBody2.indexOf("</tr>", startPositionOfTr2);
                            String subStringOfTr2 = subStringOfBody2.substring(startPositionOfTr2, endPositionOfTr2);

                            String subStringOfBody3 = subStringOfBody2.substring(endPositionOfTr2, subStringOfBody2.length());//isi body setelah dipotong tr pertama
                            int startPositionOfTr3 = subStringOfBody3.indexOf("<tr>") + "<tr>".length();
                            int endPositionOfTr3 = subStringOfBody3.indexOf("</tr>", startPositionOfTr3);
                            String subStringOfTr3 = subStringOfBody3.substring(startPositionOfTr3, endPositionOfTr3);

                            String subStringOfBody4 = subStringOfBody3.substring(endPositionOfTr3, subStringOfBody3.length());//isi body setelah dipotong tr pertama
                            int startPositionOfTr4 = subStringOfBody4.indexOf("<tr>") + "<tr>".length();
                            int endPositionOfTr4 = subStringOfBody4.indexOf("</tr>", startPositionOfTr4);
                            String subStringOfTr4 = subStringOfBody4.substring(startPositionOfTr4, endPositionOfTr4);

                            String subStringOfBody5 = subStringOfBody4.substring(endPositionOfTr4, subStringOfBody4.length());//isi body setelah dipotong tr pertama
                            int startPositionOfTr5 = subStringOfBody5.indexOf("<tr>") + "<tr>".length();
                            int endPositionOfTr5 = subStringOfBody5.indexOf("</tr>", startPositionOfTr5);
                            String subStringOfTr5 = subStringOfBody5.substring(startPositionOfTr5, endPositionOfTr5);

                            //disini diisi perulanganya
                            String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
                            Vector listEmp = PstEmpDocList.list(0, 0, whereC, "");

                            //baca table dibawahnya
                            String stringTrReplace = "";
                            if (listEmp.size() > 0) {
                                for (int list = 0; list < listEmp.size(); list++) {

                                    EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                    Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                    Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());

                                    //cek nilai ada object atau tidak
                                    stringTrReplace = stringTrReplace + "<tr>";

                                    //menghitung jumlah td html
                                    int startPositionOfTd = 0;
                                    int endPositionOfTd = 0;
                                    String subStringOfTd = "";
                                    String residuOfsubStringOfTr2 = subStringOfTr5;
                                    int jumlahtd = 0;

                                    do {

                                        stringTrReplace = stringTrReplace + "<td>";

                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                        subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 

                                        int startPositionOfFormula = 0;
                                        int endPositionOfFormula = 0;
                                        String subStringOfFormula = "";
                                        String residuOfsubStringOfTd = subStringOfTd;
                                        do {
                                            try {
                                                startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 

                                                String[] partsOfFormula = subStringOfFormula.split("-");
                                                String objectNameFormula = partsOfFormula[0];
                                                String objectTypeFormula = partsOfFormula[1];
                                                String objectTableFormula = partsOfFormula[2];
                                                String objectStatusFormula = partsOfFormula[3];
                                                String value = "";
                                                if (objectTableFormula.equals("EMPLOYEE")) {
                                                    if (objectStatusFormula.equals("NEW_POSITION")) {
                                                        String select = "<a href=\"javascript:cmdSelectPos('" + oidEmpDoc + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "','" + employeeFetch.getOID() + "')\">SELECT</a></br>";

                                                        String newposition = PstEmpDocListMutation.getNewPosition(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "-" + select;
                                                        value = "-" + newposition;
                                                    } else if (objectStatusFormula.equals("NEW_GRADE_LEVEL_ID")) {
                                                        String newGrade = PstEmpDocListMutation.getNewGradeLevel(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "";
                                                        value = "-" + newGrade;
                                                    } else if (objectStatusFormula.equals("NEW_HISTORY_TYPE")) {
                                                        String newHistoryType = PstEmpDocListMutation.getNewHistoryType(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "";
                                                        value = "-" + newHistoryType;
                                                    } else {
                                                        value = (String) HashtableEmp.get(objectStatusFormula);
                                                    }
                                                } else {
                                                    System.out.print("Selain Object Employee belum bisa dipanggil");
                                                }

                                                stringTrReplace = stringTrReplace + value;

                                                residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                            } catch (Exception e) {
                                                System.out.printf(e + "");
                                            }
                                        } while (endPositionOfFormula > 0);

                                        residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                        jumlahtd = jumlahtd + 1;

                                        stringTrReplace = stringTrReplace + "</td>";
                                    } while (endPositionOfTd > 0);

                                }
                            }

                            returnData = returnData.replace("<tr>" + subStringOfTr5 + "</tr>", stringTrReplace);
                            tanpaeditor = tanpaeditor.replace("<tr>" + subStringOfTr5 + "</tr>", stringTrReplace);
                            //tutup perulanganya

                            //setelah baca td maka akan membuat td baru... disini
                            residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());

                            startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                            endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);

                        } while (endPositionOfTable > 0);

                        returnData = returnData.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");

                    } else if (objectType.equals("REPORT_JV")) {

                        long oidPeriod = 0;
                        long oidDiv = 0;
                        long companyId = 504404575327187914l;

                        String[] divisionSelect = {""};
                        try {
                            //
                            if (objectStatusField.equals("PERIOD")) {
                                oidPeriod = PstPayPeriod.getPayPeriodIdBySelectedDate(empDoc.getRequest_date());
                            } else {
                                oidPeriod = PstPayPeriod.getPayPeriodIdByName(objectStatusField);
                            }
                            if (objectClass.equals("ALL_DIVISION")) {
                                String allDiv = PstSystemProperty.getValueByName("ALL_OID_FOR_JURNAL_DOCUMENT");
                                divisionSelect = allDiv.split(",");
                            } else {
                                oidDiv = PstDivision.getDivisionIdByName(objectClass);
                                String[] xx = {"" + oidDiv};
                                divisionSelect = xx;
                            }

                        } catch (Exception e) {
                        }

                        String nilaiVal = "";
                        if (divisionSelect.length > 1) {
                            nilaiVal = JurnalDocument.listJurnalAllDiv(oidPeriod, companyId, divisionSelect);
                        } else {
                            nilaiVal = JurnalDocument.listJurnal(oidPeriod, companyId, divisionSelect);
                        }
                        returnData = returnData.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusField + "}", nilaiVal);
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusField + "}", nilaiVal);

                    } else if (objectType.equals("LISTLINE") && objectStatusField.equals("START")) {
                        String add = "<a href=\"javascript:cmdAddEmp('" + objectName + "','" + oidEmpDoc + "')\">add employee</a></br>";

                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        //menghapus tutup formula 
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
                        Vector listEmp = PstEmpDocList.list(0, 0, whereC, "");
                        String subListLine = "";
                        if (listEmp.size() > 0) {
                            for (int list = 0; list < listEmp.size(); list++) {
                                EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                String value = (String) HashtableEmp.get("FULL_NAME");
                                if ((listEmp.size() - 2) == list) {
                                    subListLine = subListLine + value + " dan ";
                                } else {
                                    subListLine = subListLine + value + ", ";
                                }
                            }
                        }
                        //subListLine = subListLine.substring(0,subListLine.length()-1);
                        add = add + subListLine;
                        returnData = returnData.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", subListLine);

                    } else if (objectType.equals("FIELD") && objectStatusField.equals("AUTO")) {
                        //String field = "<input type=\"text\" name=\""+ subString +"\" value=\"\">";
                        Date newd = new Date();
                        String field = "04/KEP/BPD-PMT/" + newd.getMonth() + "/" + newd.getYear();
                        returnData = returnData.replace("${" + subString + "}", field);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", field);

                    } else if (objectType.equals("FIELD")) {

                        if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXT"))) {
                            //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                            String add = "<a href=\"javascript:cmdAddText('" + oidEmpDoc + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "')\">" + (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "NEW TEXT") + "</a></br>";
                            returnData = returnData.replace("${" + subString + "}", add);
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : " "));
                        } else if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("DATE"))) {
                            String dateShow = "";
                            if (hlistEmpDocField.get(objectName) != null) {
                                SimpleDateFormat formatterDateSql = new SimpleDateFormat("yyyy-MM-dd");
                                String dateInString = (String) hlistEmpDocField.get(objectName);

                                SimpleDateFormat formatterDate = new SimpleDateFormat("dd MMMM yyyy");
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    Date dateX = formatterDateSql.parse(dateInString);
                                    String strDate = sdf.format(dateX);
                                    strYear = strDate.substring(0, 4);
                                    strMonth = strDate.substring(5, 7);
                                    if (strMonth.length() > 0) {
                                        switch (Integer.valueOf(strMonth)) {
                                            case 1:
                                                strMonth = "Januari";
                                                break;
                                            case 2:
                                                strMonth = "Februari";
                                                break;
                                            case 3:
                                                strMonth = "Maret";
                                                break;
                                            case 4:
                                                strMonth = "April";
                                                break;
                                            case 5:
                                                strMonth = "Mei";
                                                break;
                                            case 6:
                                                strMonth = "Juni";
                                                break;
                                            case 7:
                                                strMonth = "Juli";
                                                break;
                                            case 8:
                                                strMonth = "Agustus";
                                                break;
                                            case 9:
                                                strMonth = "September";
                                                break;
                                            case 10:
                                                strMonth = "Oktober";
                                                break;
                                            case 11:
                                                strMonth = "November";
                                                break;
                                            case 12:
                                                strMonth = "Desember";
                                                break;
                                        }
                                    }
                                    strDay = strDate.substring(8, 10);
                                    dateShow = strDay + " " + strMonth + " " + strYear;  ////formatterDate.format(dateX);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            String add = "<a href=\"javascript:cmdAddText('" + oidEmpDoc + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "')\">" + (hlistEmpDocField.get(objectName) != null ? dateShow : "NEW DATE") + "</a></br>";
                            returnData = returnData.replace("${" + subString + "}", add);
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", (hlistEmpDocField.get(objectName) != null ? dateShow : " "));
                        } else if ((objectClass.equals("CLASSFIELD"))) {
                            //cari tahu ini untuk perubahan apa Divisi
                            String getNama = "";
                            if (hlistEmpDocField.get(objectName) != null) {
                                getNama = PstDocMasterTemplate.getNama((String) hlistEmpDocField.get(objectName), objectStatusField);
                            }

                            String add = "<a href=\"javascript:cmdAddText('" + oidEmpDoc + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "')\">" + (hlistEmpDocField.get(objectName) != null ? getNama : "ADD") + "</a></br>";
                            returnData = returnData.replace("${" + subString + "}", add);
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : " "));

                        } else if ((objectClass.equals("EMPDOCFIELD"))) {
                            //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(empDOcFecthH.get(objectName) != null?(String)empDOcFecthH.get(objectName):"add")+"</a></br>";
                            returnData = returnData.replace("${" + subString + "}", "" + empDOcFecthH.get(objectStatusField));
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", "" + empDOcFecthH.get(objectStatusField));
                        }

                    }

                } else if (!objectName.equals("") && objectType.equals("") && objectClass.equals("") && objectStatusField.equals("")) {
                    String obj = "" + (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "-");
                    returnData = returnData.replace("${" + objectName + "}", obj);
                    tanpaeditor = tanpaeditor.replace("${" + objectName + "}", obj);
                }
                stringResidual = stringResidual.substring(endPosition, stringResidual.length());
                objectDocumentDetail.setStartPosition(startPosition);
                objectDocumentDetail.setEndPosition(endPosition);
                objectDocumentDetail.setText(subString);
                vNewString.add(objectDocumentDetail);

                //mengecek apakah masih ada sisa
                startPosition = stringResidual.indexOf("${") + "${".length();
                endPosition = stringResidual.indexOf("}", startPosition);
            } while (endPosition > 0);
        } catch (Exception e) {
        }
        
        returnData = returnData.replace("&lt", "<");
        returnData = returnData.replace("&gt", ">");
        returnData += ""
                + "</div>"
                + "</div>";
        return returnData;
    }
    
    private String updateFinger(HttpServletRequest request){
        String htmlReturn="";
        String spvName ="";
        long oidDetail = 0;
        
        EmpAward empAward = new EmpAward();
        
        oidDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OID_DETAIL");
        
        try {
            empAward =PstEmpAward.fetchExc(oidDetail);
        } catch (Exception e) {
        }
        
        String sqlUpdate = "UPDATE "+PstEmpAward.TBL_AWARD+" SET ";
        sqlUpdate += PstEmpAward.fieldNames[PstEmpAward.FLD_ACKNOWLEDGE_STATUS]+"=1";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdate += " WHERE "+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID]+"="+oidDetail;
        try {
            DBHandler.execUpdate(sqlUpdate);
        } catch (Exception exc){}
        
        String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
        sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_FINGER_CHECK]+"=0";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+empAward.getEmployeeId();
        try {
            DBHandler.execUpdate(sqlUpdateEmp);
        } catch (Exception exc){}
        
        return htmlReturn;
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
    
