/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.log.ChangeValue;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.report.JurnalDocument;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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
public class AjaxEmpDoc extends HttpServlet {

    //DATATABLES
    private String searchTerm;
    private String colName;
    private int colOrder;
    private String dir;
    private int start;
    private int amount;
    private int objectType;
    private int flowIndex;
    
    //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    
    //LONG
    private long oid = 0;
    private long oidReturn = 0;
    private long userId = 0;
    private long empId = 0;
    private long datachange = 0;
    private long template = 0;
    private long oidApprover = 0;
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private long recipientId = 0;
    private long empDataId = 0;
    private long mutationId = -1;
    private long oidEmployee = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String htmlReturn2 = "";
    private String empName = "";
    private String keyword = "";
    private String object = "";
    private String objectClass = "";
    private String objectTypeString = "";
    private String objectStatusField = "";
    private String flowTitle = "";
    private String strLoginId = "";
    private String strPassword = "";
    private String note = "";
    private String issueDate = "";
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    //BOOLEAN
    private boolean isApprove = false;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        this.empId = FRMQueryString.requestLong(request, "empId");
        this.datachange = FRMQueryString.requestLong(request, "datachange");
        this.template = FRMQueryString.requestLong(request, "template");
        this.oidApprover = FRMQueryString.requestLong(request, "oidApprover");
        this.companyId = FRMQueryString.requestLong(request, "company_id");
        this.divisionId = FRMQueryString.requestLong(request, "division_id");
        this.recipientId = FRMQueryString.requestLong(request, "FRM_FIELD_EMP_DOC_RECIPIENT_ID");
        this.empDataId = FRMQueryString.requestLong(request, "FRM_FIELD_EMP_DATA_ID");
        this.oidEmployee = FRMQueryString.requestLong(request, "FRM_FIELD_EMPLOYEE_ID");
        
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        this.htmlReturn2 = "";
        this.empName = FRMQueryString.requestString(request, "empName");
        this.keyword = FRMQueryString.requestString(request, "keyword");
        this.object = FRMQueryString.requestString(request, "object");
        this.objectClass = FRMQueryString.requestString(request, "objectClass");
        this.objectTypeString = FRMQueryString.requestString(request, "objectType");
        this.objectStatusField = FRMQueryString.requestString(request, "objectStatusField");
        this.flowTitle = FRMQueryString.requestString(request, "flowTitle");
        this.strLoginId = FRMQueryString.requestString(request, "login_id");
        this.strPassword = FRMQueryString.requestString(request, "pass_wd");
        this.note = FRMQueryString.requestStringWithoutInjection(request, "note");
        this.issueDate = FRMQueryString.requestString(request, "FRM_FIELD_DATE_OF_ISSUE_STRING");
	
	//INT
        this.objectType = FRMQueryString.requestInt(request, "objectType");
        this.flowIndex = FRMQueryString.requestInt(request, "flowIndex");
	this.iCommand = FRMQueryString.requestCommand(request);
	this.iErrCode = 0;
	
        //BOOLEAN
        this.isApprove = FRMQueryString.requestBoolean(request, "isApprove");
	
	
	//OBJECT
	this.jSONObject = new JSONObject();
	
	switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
	    break;
                
            case Command.POST :
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
            this.jSONObject.put("FRM_FIELD_HTML_2", this.htmlReturn2);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
	
	response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showEmpDocForm")){
	  this.htmlReturn = empDocForm();
	} else if (this.dataFor.equals("set_doc_number")){
            
           String whereList = PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = 0";
           String whereField = PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = 0";
           String whereRecipient = PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID] + " = 0";
           String whereMutation = PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID] + " = 0";

           Vector listEmpDocList = PstEmpDocList.list(0, 0, whereList, "");
           Vector listEmpDocField = PstEmpDocField.list(0, 0, whereField, "");
           Vector listEmpDocRecipient = PstEmpDocRecipient.list(0, 0, whereRecipient, ""); 
           Vector listEmpDocMutation = PstEmpDocListMutation.list(0, 0, whereMutation, "");
           
           if(listEmpDocList.size() > 0){
               for (int i=0; i< listEmpDocList.size(); i++){
                   EmpDocList empDocList = (EmpDocList) listEmpDocList.get(i);
                   if (empDocList.getEmp_award_id() > 0){
                       try {
                           PstEmpAward.deleteExc(empDocList.getEmp_award_id());
                       } catch (Exception exc){}
                   }
                   if (empDocList.getEmp_career() > 0){
                       try {
                           PstCareerPath.deleteExc(empDocList.getEmp_career());
                       } catch (Exception exc){}
                   }
                   if (empDocList.getEmp_reprimand() > 0){
                       try {
                           PstEmpReprimand.deleteExc(empDocList.getEmp_reprimand());
                       } catch (Exception exc){}
                   }
                   if (empDocList.getEmp_training() > 0){
                       try {
                           PstTrainingHistory.deleteExc(empDocList.getEmp_training());
                       } catch (Exception exc){}
                   }
                   try{
                       PstEmpDocList.deleteExc(empDocList.getOID());
                   } catch (Exception exc){}
               } 
           } if (listEmpDocField.size() > 0){
               for(int i =0; i < listEmpDocField.size(); i++){
                   EmpDocField empDocField = (EmpDocField) listEmpDocField.get(i);
                   try{
                       PstEmpDocField.deleteExc(empDocField.getOID());
                   } catch (Exception exc){}
               }
           } if (listEmpDocRecipient.size() > 0){
               for(int i =0; i < listEmpDocRecipient.size(); i++){
                   EmpDocRecipient empDocRecipient = (EmpDocRecipient) listEmpDocRecipient.get(i);
                   try{
                       PstEmpDocRecipient.deleteExc(empDocRecipient.getOID());
                   } catch (Exception exc){}
               }
           } if (listEmpDocMutation.size() > 0){
               for(int i=0; i < listEmpDocMutation.size(); i++){
                   EmpDocListMutation empDocListMutation = (EmpDocListMutation) listEmpDocMutation.get(i);
                   try {
                       PstEmpDocListMutation.deleteExc(empDocListMutation.getOID());
                   } catch (Exception exc){}
               }
           }
           
            
           this.htmlReturn = docNumber(request);
           this.htmlReturn2 = template(request, this.datachange);
          
           
        } else if (this.dataFor.equals("addempsingle")){
            this.htmlReturn = employeeList();
        } else if(this.dataFor.equals("addtext")){
            this.htmlReturn = textForm();
        } else if(this.dataFor.equals("editor")){
            this.htmlReturn = editor(request, this.oid);
        } else if(this.dataFor.equals("approve")){
            this.htmlReturn = approval();
        } else if(this.dataFor.equals("dologin")){
            this.htmlReturn = dologin();
        } else if(this.dataFor.equals("review")){
            this.htmlReturn = review();
        } else if(this.dataFor.equals("viewnote")){
            this.htmlReturn = viewnote();
        } else if(this.dataFor.equals("addrecipient")){
            this.htmlReturn = recipientForm();
        } else if (this.dataFor.equals("get_division")){
           this.htmlReturn = divisionSelect(request, this.divisionId);
        } else if (this.dataFor.equals("get_department")){
           this.htmlReturn = departmentSelect(request, this.departmentId);
        } else if (this.dataFor.equals("get_section")){
           this.htmlReturn = sectionSelect(request, this.sectionId);
        } else if(this.dataFor.equals("getRecipient")){
            this.htmlReturn = addFilter(request);
        } else if (this.dataFor.equals("showEmpAwardFormDoc")){
          this.htmlReturn = empAwardFormDoc();  
        } else if (this.dataFor.equals("showEmpReprimandFormDoc")){
          this.htmlReturn = empReprimandFormDoc();  
        } else if (this.dataFor.equals("showEmpTrainingFormDoc")){
          this.htmlReturn = trainHistoryForm();  
        } else if (this.dataFor.equals("showEmpCarrerFormDoc")){
          this.htmlReturn = getDoMutationForm();  
        }else if (this.dataFor.equals("getEmpCareer")){
          this.htmlReturn = getEmployeeCareer(request);  
        } else if (this.dataFor.equals("getCareer")){
          this.htmlReturn = getEmpCareerPath(request);  
        } else if (this.dataFor.equals("get_position")){
           this.mutationId = FRMQueryString.requestLong(request, "mutation_id");
           String strPosition = "<div class='form-group'>"
                                + "<label>Position</label>"
                                + "<select class=\"form-control\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_POSITION_ID]+">"
                                + positionSelect(request, this.mutationId)
                                + "</select>"
                                + "</div>";
           String strLevel = "<div class='form-group'>"
                                + "<label>Level</label>"
                                + "<select class=\"form-control\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_LEVEL_ID]+">"
                                + levelSelect(request, this.mutationId)
                                + "</select>"
                                + "</div>";
           this.htmlReturn = strPosition + strLevel;
        } else if (this.dataFor.equals("viewDoc")){
            this.htmlReturn = viewDoc();
        }
    }
    
    public void commandSave(HttpServletRequest request){
        if(this.dataFor.equals("showEmpDocForm") || this.dataFor.equals("editor") ){
            CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
            this.iErrCode = ctrlEmpDoc.action(this.iCommand, this.oid, this.oidDelete);
            String message = ctrlEmpDoc.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        } else if(this.dataFor.equals("saveempsingle")){
            CtrlEmpDocList ctrlEmpDocList = new CtrlEmpDocList(request);
            Vector listEmpDocList = new Vector();
            String whereStatus = PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = " + this.oid + ""
                    + " AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = '" + object + "'";
            listEmpDocList = PstEmpDocList.list(0, 0, whereStatus, "");
            
            long oidDelete = 0;
            if(listEmpDocList.size() > 0){
                for (int i = 0; i < listEmpDocList.size(); i++){
                    EmpDocList empDocList = (EmpDocList) listEmpDocList.get(i);
                    try {
                        oidDelete = PstEmpDocList.deleteExc(empDocList.getOID());
                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                    
                }
            }
            long oidInsert = 0;
            if (this.empId != 0){
                EmpDocList empDocList = new EmpDocList();
                empDocList.setEmp_doc_id(this.oid);
                empDocList.setEmployee_id(this.empId);
                empDocList.setObject_name(object);
                try {
                    oidInsert = PstEmpDocList.insertExc(empDocList);
                } catch(Exception e){
                    System.out.println(e.toString());
                }
            }
            this.htmlReturn = "<i class='fa fa-info'></i> Sucess";
            this.htmlReturn2 = template(request, this.datachange);
        } else if(this.dataFor.equals("addtext")){
            String value = FRMQueryString.requestString(request, "FRM_FIELD_VALUE");
            CtrlEmpDocField ctrlEmpDocField = new CtrlEmpDocField(request);
            Vector listEmpDocField = new Vector();
            String whereStatus = PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = " + this.oid + ""
                    + " AND " + PstEmpDocField.fieldNames[PstEmpDocField.FLD_OBJECT_NAME] + " = '" + object + "'";
            listEmpDocField = PstEmpDocField.list(0, 0, whereStatus, "");
            
            long oidDelete = 0;
            if(listEmpDocField.size() > 0){
                for (int i = 0; i < listEmpDocField.size(); i++){
                    EmpDocField empDocField = (EmpDocField) listEmpDocField.get(i);
                    try {
                        oidDelete = PstEmpDocField.deleteExc(empDocField.getOID());
                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                    
                }
            }
            
            long oidInsert = 0;
            if (!(value.equals(""))){
                EmpDocField empDocField = new EmpDocField();
                empDocField.setEmp_doc_id(this.oid);
                empDocField.setObject_name(object);
                empDocField.setObject_type(objectType);
                empDocField.setClassName(objectClass);
                empDocField.setValue(value);
                try {
                    oidInsert = PstEmpDocField.insertExc(empDocField);
                } catch(Exception e){
                    System.out.println(e.toString());
                }
            }
            this.htmlReturn = "<i class='fa fa-info'></i> Sucess";
            this.htmlReturn2 = template(request, this.datachange);
        } else if(this.dataFor.equals("dologin")){
            this.htmlReturn = doLogin(request);
        } else if (this.dataFor.equals("review")){
            if (!(note.equals(""))){
                int status = PstEmpDoc.updateNote(this.oid, this.note, 0);
            }
            this.htmlReturn = "<i class='fa fa-info'></i> Data has been saved";
        } else if (this.dataFor.equals("viewnote")){
            if (!(note.equals(""))){
                int status = PstEmpDoc.updateNote(this.oid, this.note, 1);
            }
            this.htmlReturn = "<i class='fa fa-info'></i> Confirmed";
        } else if (this.dataFor.equals("addrecipient")){
            CtrlEmpDocRecipient ctrlEmpDocRecipient = new CtrlEmpDocRecipient(request);
            this.iErrCode = ctrlEmpDocRecipient.action(this.iCommand, this.recipientId);
            String message = ctrlEmpDocRecipient.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;
            this.htmlReturn2 = template(request, this.datachange);
        } else if (this.dataFor.equals("showEmpAwardFormDoc")){
            CtrlEmpAward ctrlEmpAward = new CtrlEmpAward(request);
            this.iErrCode = ctrlEmpAward.action(this.iCommand, this.empDataId, request, empName, userId, this.oidDelete, this.object);
            String message = ctrlEmpAward.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;  
            this.htmlReturn2 = template(request, this.datachange);
        } else if (this.dataFor.equals("showEmpReprimandFormDoc")){
            CtrlEmpReprimand ctrlEmpReprimand = new CtrlEmpReprimand(request);
            this.iErrCode = ctrlEmpReprimand.action(this.iCommand, this.empDataId, request, empName, userId, this.oidDelete, this.object);
            String message = ctrlEmpReprimand.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;  
            this.htmlReturn2 = template(request, this.datachange);
        } else if (this.dataFor.equals("showEmpTrainingFormDoc")){
            CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
            this.iErrCode = ctrlTrainingHistory.action(this.iCommand, this.empDataId, request, empName, userId, this.oidDelete, this.object);
            String message = ctrlTrainingHistory.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;  
            this.htmlReturn2 = template(request, this.datachange);
        } else if(this.dataFor.equals("showEmpCarrerFormDoc")){
            CtrlEmpDocListMutation ctrlEmpDocListMutation = new CtrlEmpDocListMutation(request);
            this.iErrCode = ctrlEmpDocListMutation.action(this.iCommand, this.empDataId);
            String message = ctrlEmpDocListMutation.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;  
            this.htmlReturn2 = template(request, this.datachange);
        } else if(this.dataFor.equals("approveFinger")){
            this.htmlReturn = updateFinger(request);
        }
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
	this.iErrCode = ctrlEmpDoc.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlEmpDoc.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        
        String[] splits = this.oidDelete.split(",");
        for (String oid : splits) {
            if (oid != "") {
                long oidDoc = Long.parseLong(oid);
                String sqlDelete = "DELETE FROM "+PstEmpDocGeneral.TBL_HR_EMP_DOC_GENERAL;
                sqlDelete += " WHERE "+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]+"="+oidDoc;
                try {
                    DBHandler.execUpdate(sqlDelete);
                } catch (Exception exc){}
            }
        }
        
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
	this.iErrCode = ctrlEmpDoc.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlEmpDoc.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        
        String sqlDelete = "DELETE FROM "+PstEmpDocGeneral.TBL_HR_EMP_DOC_GENERAL;
        sqlDelete += " WHERE "+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]+"="+this.oid;
        try {
            DBHandler.execUpdate(sqlDelete);
        } catch (Exception exc){}
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listEmpDoc")){
	    String[] cols = { PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID],
		PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_MASTER_ID],
                PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_TITLE], 
                PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_NUMBER],
                PstEmpDoc.fieldNames[PstEmpDoc.FLD_REQUEST_DATE],
                PstEmpDoc.fieldNames[PstEmpDoc.FLD_DATE_OF_ISSUE]};

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
        
        if(dataFor.equals("listEmpDoc")){
	    whereClause += " ("+PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_TITLE]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listEmpDoc")){
	    total = PstEmpDoc.getCount(whereClause);
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
        EmpDoc empDoc = new EmpDoc();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listEmpDoc")){
               whereClause += " ("+PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_TITLE]+" LIKE '%"+this.searchTerm+"%' )";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listEmpDoc")){
	    listData = PstEmpDoc.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listEmpDoc")){
		empDoc = (EmpDoc) listData.get(i);
                try {
                    docMaster = PstDocMaster.fetchExc(empDoc.getDoc_master_id());
                } catch (Exception exc){}
                
                String checkButton = "<input type='checkbox' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_EMP_DOC_ID]+"' class='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_EMP_DOC_ID]+"' value='"+empDoc.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+docMaster.getDoc_title());
                ja.put(""+empDoc.getDoc_title());
		ja.put(""+empDoc.getDoc_number());
                ja.put(""+empDoc.getRequest_date());
                ja.put(""+empDoc.getDate_of_issue());
                
		String buttonUpdate = "";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+empDoc.getOID()+"' data-template1='"+empDoc.getDoc_master_id()+"' data-for='showEmpDocForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
                String buttonTemplate= "<button class='btn btn-primary btneditor btn-xs' data-oid='"+empDoc.getOID()+"' data-template1='"+empDoc.getDoc_master_id()+"' data-for='editor' type='button' data-toggle='tooltip' data-placement='top' title='Editor'><i class='fa fa-file'></i></button> ";
                //String buttonTemplate = "<a href='EmpDocumentDetails.jsp?EmpDocument_oid="+empDoc.getOID()+"' class='btn btn-primary fancybox fancybox.iframe btn-xs' data-toggle='tooltip' data-placement='top' title='Document'><i class='fa fa-file'></i></a> ";
                String buttonDetail = "<button class='btn btn-warning fancybox fancybox.iframe btn-xs' data-oid='"+empDoc.getOID()+"' onclick=\"location.href='EmpDocumentDetails.jsp?EmpDocument_oid="+empDoc.getOID()+"'\" type='button' data-toggle='tooltip' data-placement='top' title='Detail'><i class='fa fa-files'></i></button> ";
                String buttonViewReview = "";
                String buttonApprove = "";
                String buttonAcknowledge = "<button class='btn btn-warning btnacknowledgestatus btn-xs' data-oid='"+empDoc.getOID()+"' data-for='showAcknowledgeStatus' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledge Status'><i class='fa fa-thumbs-up'></i></button> ";
                if (empDoc.getNote() != null && empDoc.getNote_status() == 0 && empDoc.getDoc_status() != 2 ){
                    buttonViewReview = "<button class='btn btn-danger btn-xs btnviewnote' data-oid='"+empDoc.getOID()+"' data-for='viewnote' type='button' data-toggle='tooltip' data-placement='top' title='View'><i class='fa fa-exclamation'></i></button> ";
                } else if (empDoc.getNote() != null && empDoc.getNote_status() == 1 && empDoc.getDoc_status() != 2){
                    buttonViewReview = "<button class='btn btn-success btn-xs btnviewnote' data-oid='"+empDoc.getOID()+"' data-for='viewnote' type='button' data-toggle='tooltip' data-placement='top' title='View'><i class='fa fa-exclamation'></i></button> ";
                }
                if (empDoc.getDoc_status() == 0){
                    buttonApprove = "<button class='btn btn-warning btn-xs btnapprove' data-oid='"+empDoc.getOID()+"' data-template1='"+empDoc.getDoc_master_id()+"' data-for='approve' type='button' data-toggle='tooltip' data-placement='top' title='Approve'><i class='fa fa-check-circle'></i></button> ";
                } else if (empDoc.getDoc_status() == 2){
                    buttonApprove = "<button class='btn btn-success btn-xs btnapproved' data-oid='"+empDoc.getOID()+"' data-template1='"+empDoc.getDoc_master_id()+"' data-for='approve' type='button' data-toggle='tooltip' data-placement='top' title='Approved'><i class='fa fa-check-circle '></i></button> ";
                }
		ja.put(buttonTemplate+buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empDoc.getOID()+"' data-for='deleteEmpDocSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> " +buttonApprove+ buttonViewReview+ buttonAcknowledge);
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
    
    public String docMasterSelect(long docMasterId){
        String data = "";
        
        Vector docMasterList = PstDocMaster.list(0, 0, "", "");
        data = "<select class=\"form-control datachange\" name=\""+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID]+"\" id=\""+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID]+"\" data-for=\"set_doc_number\" data-target=\"#"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER]+"\"  data-target2='#editor1'>";
        data += "<option value=\"\">Select Doc Master...</option>";
        if (docMasterList != null && docMasterList.size()>0){
            for (int i=0; i<docMasterList.size(); i++){
                DocMaster docMaster = (DocMaster)docMasterList.get(i);
                if (docMasterId == docMaster.getOID()){
                    data += "<option selected=\"selected\" value=\""+docMaster.getOID()+"\">"+docMaster.getDoc_title()+"</option>";
                } else {
                    data += "<option value=\""+docMaster.getOID()+"\">"+docMaster.getDoc_title()+"</option>";
                }
            }
        }
        data += "</select>";
        return data;
    }
    
    public String textForm(){
        Vector listEmpDocField = new Vector();
        String data = "";
        EmpDocField oidEmpDocField = new EmpDocField();
        String whereStatus = PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = " + this.oid + ""
                    + " AND " + PstEmpDocField.fieldNames[PstEmpDocField.FLD_OBJECT_NAME] + " = '" + object + "'";
        listEmpDocField = PstEmpDocField.list(0, 0, whereStatus, "");
        for (int i =0; i < listEmpDocField.size(); i++){
            EmpDocField empDocField = (EmpDocField) listEmpDocField.get(i);
            
            try {
                oidEmpDocField = PstEmpDocField.fetchExc(empDocField.getOID());
            } catch(Exception exc){
                
            }
        }
        if (this.objectStatusField.equals("TEXT")){
            data += ""
                    + "<input type='text' placeholder='Type Text or Number' name='FRM_FIELD_VALUE' value='"+oidEmpDocField.getValue()+"' id='FRM_FIELD_VALUE' class='form-control'/>";
        } else if (this.objectStatusField.equals("DATE")){
            data += ""
                    + "<input type='text' name='FRM_FIELD_VALUE' value='"+oidEmpDocField.getValue()+"' id='FRM_FIELD_VALUE' class='form-control datepicker'/>";
        } else if (this.objectStatusField.equals("TEXTAREA")){
            data += ""
                    + "<textarea name='FRM_FIELD_VALUE' id='FRM_FIELD_VALUE' class='form-control'>"+oidEmpDocField.getValue()+"</textarea>";
                    
        }
        return data;
    }
    
    public String employeeList(){
        String data = "";
        
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+this.keyword+"%'";
        String order = ""+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
        Vector listEmployee = new Vector();
        if (this.keyword.length() > 0){
           listEmployee = PstEmployee.list(0, 0, whereClause, order); 
        } else {
           listEmployee = PstEmployee.list(0, 0, "", order); 
        }
        
        if (listEmployee != null && listEmployee.size()>0){
            for(int i=0; i<listEmployee.size(); i++){
                Employee employee = (Employee)listEmployee.get(i);
                
                data += "<div class='item' data-empid="+employee.getOID()+" data-object="+this.object+" data-oid="+this.oid+" data-template1="+this.template+" data-for='saveempsingle' data-target2='#editor1' >"+employee.getFullName()+"</div>";
                
            }
        }
        
        return data;
    }
    
    public String docNumber(HttpServletRequest request){
        String data = "";
        String docNumber = "";
        DocMaster docMaster = new DocMaster();
           try {
               docMaster = PstDocMaster.fetchExc(this.datachange);
           } catch (Exception exc) {}
        
        if (!(docMaster.getDoc_number().equals(""))){
            docNumber = docMaster.getDoc_number();
            String[] docNumArray = docMaster.getDoc_number().split("/");
            for (int i = 0; i < docNumArray.length; i++) {
                String element = docNumArray[i];
                if (element.equals("NUMBER")){
                    String whereDocMaster = PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_MASTER_ID] + " = " + this.datachange;
                    Vector listEmpDoc = PstEmpDoc.list(0, 0, whereDocMaster, "");
                    if (listEmpDoc != null && listEmpDoc.size() > 0){
                        int number = 0;
                        for (int x = 0; x < listEmpDoc.size() ; x++){
                            EmpDoc empDocNumber = (EmpDoc) listEmpDoc.get(x);
                            String[] docNumArray1 = empDocNumber.getDoc_number().split("/");
                            number = Integer.valueOf(docNumArray1[i]) + 1;
                        }
                        if (number < 10) {
                            docNumber = docNumber.replaceAll("NUMBER", "0"+number);
                        } else {
                            docNumber = docNumber.replaceAll("NUMBER", ""+number);
                        }    
                    } else {
                        docNumber = docNumber.replaceAll("NUMBER", "01");
                    }
                } else if (element.equals("MM")){
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int month = cal.get(Calendar.MONTH) + 1;
                    docNumber = docNumber.replaceAll("MM", ""+month);
                } else if (element.equals("YYYY")){
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    docNumber = docNumber.replaceAll("YYYY", ""+year);
                } else if (element.equals("YY")){
                    DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
                    String formattedDate = df.format(Calendar.getInstance().getTime());
                    docNumber = docNumber.replaceAll("YY", ""+formattedDate);
                }

            }
        }
        
        data += docNumber;
           
        return data;
    }
    
    public String editor(HttpServletRequest request, long oidEmpDoc){
        String empDocMasterTemplateText = "";
        String whereClause = "";
        String orderClause = "";
        EmpDoc empDoc1 = new EmpDoc();
        DocMaster empDocMaster1 = new DocMaster();
        DocMasterTemplate empDocMasterTemplate = new DocMasterTemplate();

        try {
            empDoc1 = PstEmpDoc.fetchExc(oidEmpDoc); 
        } catch (Exception e){ }
        if (empDoc1 != null){
        try {
            empDocMaster1 = PstDocMaster.fetchExc(empDoc1.getDoc_master_id());
        } catch (Exception e){ }

        if (empDoc1.getDetails().length() > 0){
            empDocMasterTemplateText = empDoc1.getDetails();
        } else {
                try {
                    empDocMasterTemplateText = PstDocMasterTemplate.getTemplateText(empDoc1.getDoc_master_id());
                } catch (Exception e){ }
                }
        }
        
        empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
                                                
        empDocMasterTemplateText = empDocMasterTemplateText.replace("<;", "<");
        empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");
        String tanpaeditor = empDocMasterTemplateText;
        String subString = "";
        String stringResidual = empDocMasterTemplateText;
        Vector vNewString = new Vector();

        Hashtable empDOcFecthH = new Hashtable();

        try {
            empDOcFecthH = PstEmpDoc.fetchExcHashtable(oidEmpDoc); 
        } catch (Exception e){ }

        String where1 = " "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
        Hashtable hlistEmpDocField = PstEmpDocField.Hlist(0, 0, where1, ""); 
        String where2 = " " + PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
        Vector listEmpDocRecipient = PstEmpDocRecipient.list(0, 0, where2, "");
        String recName = "";
        if(listEmpDocRecipient.size() > 0){
            for (int i = 0; i < listEmpDocRecipient.size(); i++){
                EmpDocRecipient empDocRecipient = (EmpDocRecipient) listEmpDocRecipient.get(i);
                if(!(empDocRecipient.getAlias().equals(""))){
                    recName = empDocRecipient.getAlias();
                } else if (empDocRecipient.getPositionId() > 0){
                    Position pos = new Position();
                    try {
                        pos = PstPosition.fetchExc(empDocRecipient.getPositionId());
                    } catch(Exception exc){}
                }
            }
        }
        
        if(empDoc1.getDate_of_issue() != null){
            issueDate = String.valueOf(empDoc1.getDate_of_issue());
        }

        int startPosition = 0 ;
        int endPosition = 0; 
        try {
        do {

                ObjectDocumentDetail objectDocumentDetail = new ObjectDocumentDetail();
                startPosition = stringResidual.indexOf("${") + "${".length();
                endPosition = stringResidual.indexOf("}", startPosition);
                subString = stringResidual.substring(startPosition, endPosition);


                //cek substring


                    String []parts = subString.split("-");
                    String objectName = "";
                    String objectType = "";
                    String objectClass = "";
                    String objectStatusField = "";
                    try{
                    objectName = parts[0]; 
                    objectType = parts[1];
                    objectClass = parts[2];
                    objectStatusField = parts[3];
                    } catch (Exception e){
                        System.out.printf("pastikan 4 parameter");
                    }


                //cek dulu apakah hanya object name atau tidak
                if  (!objectName.equals("") && !objectType.equals("") && !objectClass.equals("") && !objectStatusField.equals("")){


                    //jika list maka akan mencari penutupnya..
                if  (objectType.equals("SINGLE") && objectStatusField.equals("START")){
                    String add = "<a href=\"javascript:cmdAddEmpSingle('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                    empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                    tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                    int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                       //menghapus tutup formula 
                       String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                       Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 
                       EmpDocList empDocList = new EmpDocList();
                       Employee employeeFetch = new Employee();
                       Hashtable HashtableEmp = new Hashtable();
                       if (listEmp.size() > 0 ){
                           try {
                               empDocList = (EmpDocList) listEmp.get(listEmp.size()-1);
                               employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                               HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                           }catch (Exception e){}

                       }

                       int xx = 2 ;
                              int startPositionOfFormula = 0;
                              int endPositionOfFormula = 0;
                              String subStringOfFormula = "";
                              String residuOfsubStringOfTd = textString;
                               do{

                                        startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                        subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 


                                        String []partsOfFormula = subStringOfFormula.split("-");
                                        String objectNameFormula = partsOfFormula[0]; 
                                        String objectTypeFormula = partsOfFormula[1];
                                        String objectTableFormula = partsOfFormula[2];
                                        String objectStatusFormula = partsOfFormula[3];
                                        String value = "";
                                         if (objectTableFormula.equals("EMPLOYEE")){
                                                 value = (String) HashtableEmp.get(objectStatusFormula);
                                                 if (value == null){
                                                     value = "-";
                                                 }

                                        } else {
                                            System.out.print("Selain Object Employee belum bisa dipanggil");
                                        }

                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-"+objectStatusFormula+"}", value); 

                                        tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-"+objectStatusFormula+"}", value); 
                                        residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                                        startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                        }while(endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                        tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       

                } else if  (objectType.equals("LIST") && objectStatusField.equals("START")){
                    String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                    empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                    tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                    int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                       //menghapus tutup formula 


                        //mencari jumlah table table
                          int startPositionOfTable = 0;
                          int endPositionOfTable = 0;
                          String subStringOfTable = "";
                          String residueOfTextString = textString;
                          do{
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

                             String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                             Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 

                             //baca table dibawahnya

                                 String stringTrReplace = ""; 
                            if (listEmp.size() > 0 ){
                             for(int list = 0 ; list < listEmp.size(); list++ ){

                                 EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                 Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                 Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());



                             stringTrReplace = stringTrReplace+"<tr>"; 

                              //menghitung jumlah td html
                              int startPositionOfTd = 0;
                              int endPositionOfTd = 0;
                              String subStringOfTd = "";
                              String residuOfsubStringOfTr2 = subStringOfTr2 ;
                              int jumlahtd = 0;



                              do{

                              stringTrReplace = stringTrReplace+"<td>";  

                              startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                              endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                              subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 

                              int startPositionOfFormula = 0;
                              int endPositionOfFormula = 0;
                              String subStringOfFormula = "";
                              String residuOfsubStringOfTd = subStringOfTd;
                                      do{

                                        startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                        subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 


                                        String []partsOfFormula = subStringOfFormula.split("-");
                                        String objectNameFormula = partsOfFormula[0]; 
                                        String objectTypeFormula = partsOfFormula[1];
                                        String objectTableFormula = partsOfFormula[2];
                                        String objectStatusFormula = partsOfFormula[3];
                                        String value = "";
                                        if (objectTableFormula.equals("EMPLOYEE")){
                                                 value = (String) HashtableEmp.get(objectStatusFormula);

                                        } else {
                                            System.out.print("Selain Object Employee belum bisa dipanggil");
                                        }

                                        stringTrReplace = stringTrReplace+value;  

                                        residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                      }while(endPositionOfFormula > 0);



                                residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                              jumlahtd = jumlahtd + 1 ;

                              stringTrReplace = stringTrReplace+"</td>"; 
                              }while(endPositionOfTd > 0);

                              }
                                                                                                      }

                                empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                tanpaeditor = tanpaeditor.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                             //tutup perulanganya

                              //setelah baca td maka akan membuat td baru... disini

                               residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());

                              startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                              endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);

                          }  while ( endPositionOfTable > 0);

                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                        tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       

                } else if  (objectType.equals("LISTMUTATION") && objectStatusField.equals("START")){
                    String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                    empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                    tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                    int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                       //menghapus tutup formula 


                        //mencari jumlah table table
                          int startPositionOfTable = 0;
                          int endPositionOfTable = 0;
                          String subStringOfTable = "";
                          String residueOfTextString = textString;
                          do{
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

                             String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                             Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 

                             //baca table dibawahnya

                                 String stringTrReplace = ""; 
                            if (listEmp.size() > 0 ){
                             for(int list = 0 ; list < listEmp.size(); list++ ){

                                 EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                 Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                 Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());

                                 //cek nilai ada object atau tidak



                             stringTrReplace = stringTrReplace+"<tr>"; 

                              //menghitung jumlah td html
                              int startPositionOfTd = 0;
                              int endPositionOfTd = 0;
                              String subStringOfTd = "";
                              String residuOfsubStringOfTr2 = subStringOfTr5 ;
                              int jumlahtd = 0;



                              do{

                              stringTrReplace = stringTrReplace+"<td>";  

                              startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                              endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                              subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 

                              int startPositionOfFormula = 0;
                              int endPositionOfFormula = 0;
                              String subStringOfFormula = "";
                              String residuOfsubStringOfTd = subStringOfTd;
                                      do{
                                        try {
                                        startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                        subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 


                                        String []partsOfFormula = subStringOfFormula.split("-");
                                        String objectNameFormula = partsOfFormula[0]; 
                                        String objectTypeFormula = partsOfFormula[1];
                                        String objectTableFormula = partsOfFormula[2];
                                        String objectStatusFormula = partsOfFormula[3];
                                        String value = "";
                                        if (objectTableFormula.equals("EMPLOYEE")){
                                            if (objectStatusFormula.equals("NEW_POSITION")){
                                                String select = "<a href=\"javascript:cmdSelectPos('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"','"+employeeFetch.getOID()+"')\">SELECT</a></br>";

                                                String newposition = PstEmpDocListMutation.getNewPosition(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) ;
                                                value = newposition;
                                            } else { 

                                                if (objectStatusFormula.equals("NEW_GRADE_LEVEL_ID")){
                                                    String newGrade = PstEmpDocListMutation.getNewGradeLevel(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "";
                                                    value = "-"+newGrade;
                                                } else if (objectStatusFormula.equals("NEW_HISTORY_TYPE")){
                                                    String newHistoryType = PstEmpDocListMutation.getNewHistoryType(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "";
                                                    value = "-"+newHistoryType;
                                                } else {
                                                    value = (String) HashtableEmp.get(objectStatusFormula);
                                                }


                                            }  
                                        } else {
                                            System.out.print("Selain Object Employee belum bisa dipanggil");
                                        }

                                        stringTrReplace = stringTrReplace+value;  

                                        residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                      } catch (Exception e){
                                      System.out.printf(e+"");
                                      }
                                     }  while(endPositionOfFormula > 0);



                                residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                              jumlahtd = jumlahtd + 1 ;

                              stringTrReplace = stringTrReplace+"</td>"; 
                              }while(endPositionOfTd > 0);

                              }
                                                                                                      }

                                empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr5+"</tr>", stringTrReplace); 
                                tanpaeditor = tanpaeditor.replace("<tr>"+subStringOfTr5+"</tr>", stringTrReplace); 
                             //tutup perulanganya

                              //setelah baca td maka akan membuat td baru... disini

                               residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());

                              startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                              endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);

                          }  while ( endPositionOfTable > 0);

                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                        tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       

                } else if (objectType.equals("REPORT_JV")) {

      long oidPeriod = 0;
      long oidDiv = 0;
      long companyId = 504404575327187914l;

      String[] divisionSelect = null;
      String[] componentSelect = null;

           oidPeriod = PstPayPeriod.getPayPeriodIdBySelectedDate(empDoc1.getRequest_date());

      if (!objectClass.equals("")){
          whereClause = PstDivisionCodeJv.fieldNames[PstDivisionCodeJv.FLD_DIVISION_CODE]+"='"+objectClass+"'";
          Vector divCodeJvList = PstDivisionCodeJv.list(0, 0, whereClause, "");
          if (divCodeJvList != null && divCodeJvList.size()>0){
              DivisionCodeJv divCodeObj = (DivisionCodeJv)divCodeJvList.get(0);
              whereClause = PstDivisionMapJv.fieldNames[PstDivisionMapJv.FLD_DIVISION_CODE_ID]+"="+divCodeObj.getOID();
              Vector divMapList = PstDivisionMapJv.list(0, 0, whereClause, "");
              if (divMapList != null && divMapList.size()>0){
                  divisionSelect = new String[divMapList.size()];
                  for (int i=0; i<divMapList.size(); i++){
                      DivisionMapJv divMap = (DivisionMapJv)divMapList.get(i);
                      divisionSelect[i] = ""+divMap.getDivisionId();
          }
           }
          }
      }
      if (objectStatusField.length()>0){
          whereClause = PstComponentCodeJv.fieldNames[PstComponentCodeJv.FLD_COMP_CODE]+"='"+objectStatusField+"'";
          Vector compCodeJvList = PstComponentCodeJv.list(0, 0, whereClause, "");
          if (compCodeJvList != null && compCodeJvList.size()>0){
              ComponentCodeJv compCodeObj = (ComponentCodeJv)compCodeJvList.get(0);
              whereClause = PstComponentMapJv.fieldNames[PstComponentMapJv.FLD_COMP_CODE_ID]+"="+compCodeObj.getOID();
              Vector compMapList = PstComponentMapJv.list(0, 0, whereClause, "");
              if (compMapList != null && compMapList.size()>0){
                  componentSelect = new String[compMapList.size()];
                  for (int i=0; i<compMapList.size(); i++){
                      ComponentMapJv compMap = (ComponentMapJv)compMapList.get(i);
                      componentSelect[i] = ""+compMap.getComponentId();
                  }
              }
          }
      }

      String nilaiVal = "";
      nilaiVal = JurnalDocument.printJurnal(oidPeriod, divisionSelect, componentSelect);
      empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusField + "}", nilaiVal);
      tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusField + "}", nilaiVal);

    }  else if  (objectType.equals("LISTLINE") && objectStatusField.equals("START")){
                    String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";

                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                       //menghapus tutup formula 
                       String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                       Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 
                       String subListLine ="";
                        if (listEmp.size() > 0 ){
                             for(int list = 0 ; list < listEmp.size(); list++ ){
                                 EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                 Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                 Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                 String value = (String) HashtableEmp.get("FULL_NAME");
                                 if ((listEmp.size()-2) == list ){
                                    subListLine = subListLine+value+" dan ";  
                                 } else {
                                    subListLine = subListLine+value+", ";
                                 }
                             }
                        }
                       //subListLine = subListLine.substring(0,subListLine.length()-1);
                       add = add + subListLine ;
                     empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add ); 
                     tanpaeditor = tanpaeditor.replace("${"+subString+"}", subListLine);       

                } else if  (objectType.equals("FIELD") && objectStatusField.equals("AUTO")){
                        //String field = "<input type=\"text\" name=\""+ subString +"\" value=\"\">";
                    Date newd = new Date();
                        String field = "04/KEP/BPD-PMT/"+newd.getMonth()+"/"+newd.getYear();
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", field); 
                        tanpaeditor = tanpaeditor.replace("${"+subString+"}", field); 

                } else if  (objectType.equals("FIELD")){

                    if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXT"))){
                       //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                        String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"NEW TEXT")+"</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                        tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                    } else if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("DATE"))){
                        String dateShow = "";
                        if (hlistEmpDocField.get(objectName) != null){
                            SimpleDateFormat formatterDateSql = new SimpleDateFormat("yyyy-MM-dd");
                            String dateInString = (String)hlistEmpDocField.get(objectName);		

                            SimpleDateFormat formatterDate = new SimpleDateFormat("dd MMM yyyy");
                            try {


                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateX = formatterDateSql.parse(dateInString);
                                String strDate = sdf.format(dateX);
                                String strYear = strDate.substring(0, 4);
                                String strMonth = strDate.substring(5, 7);
                                String strDay = strDate.substring(8, 10);
                                if (strMonth.length() > 0){
                                    switch(Integer.valueOf(strMonth)){
                                        case 1: strMonth = "Januari"; break;
                                        case 2: strMonth = "Februari"; break;
                                        case 3: strMonth = "Maret"; break;
                                        case 4: strMonth = "April"; break;
                                        case 5: strMonth = "Mei"; break;
                                        case 6: strMonth = "Juni"; break;
                                        case 7: strMonth = "Juli"; break;
                                        case 8: strMonth = "Agustus"; break;
                                        case 9: strMonth = "September"; break;
                                        case 10: strMonth = "Oktober"; break;
                                        case 11: strMonth = "November"; break;
                                        case 12: strMonth = "Desember"; break;
                                    }
                                }

                                dateShow = strDay + " "+ strMonth + " " + strYear;

                            } catch (Exception e) {
                                    e.printStackTrace();
                            }
                        }
                        String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?dateShow:"NEW DATE")+"</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                        tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?dateShow:" ")); 
                    } else if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXTAREA"))){
                            String add="<a href='#' id='addText' data-object='"+objectName+"' data-oid='"+this.oid+"' data-objectType='"+objectType+"' data-objectClass='"+objectClass+"' data-objectStatusField='"+objectStatusField+"' data-template1='"+this.datachange+"' data-for='addtext' data-target2='#editor1'>"+ (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "OPEN EDITOR") +"</a></br>";
//                            String add = "<a href=\"javascript:cmdAddText('" + this.oid + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "')\">" + (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "NEW TEXT") + "</a></br>";
                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : " "));
                        } else if ((objectClass.equals("CLASSFIELD"))){
                        //cari tahu ini untuk perubahan apa Divisi
                         String getNama ="";
                        if (hlistEmpDocField.get(objectName) != null){
                            getNama = PstDocMasterTemplate.getNama((String)hlistEmpDocField.get(objectName), objectStatusField);
                        }

                        String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?getNama:"ADD")+"</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                        tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 

                    } else if ((objectClass.equals("EMPDOCFIELD"))){
                        //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(empDOcFecthH.get(objectName) != null?(String)empDOcFecthH.get(objectName):"add")+"</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", ""+(empDOcFecthH.get(objectStatusField) != null ? empDOcFecthH.get(objectStatusField) : "-")); 
                        tanpaeditor = tanpaeditor.replace("${"+subString+"}", ""+(empDOcFecthH.get(objectStatusField) != null ? empDOcFecthH.get(objectStatusField) : "-") ); 
                    }

                } else if(objectType.equals("MULTIPLE")){
                        String add = "<a href='#' id='addRecipient' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='addrecipient' data-target2='#editor1' data-issuedate='"+issueDate+"'>"+(!(recName.equals("")) ? recName : "add recipient")+"</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", recName);
                    } else if(objectType.equals("AWARD") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpAward = PstEmpDocList.list(0, 0, whereC, "");
                        EmpDocList empDocList = new EmpDocList();
                        Employee employeeFetch = new Employee();
                        EmpAward empAwardFetch = new EmpAward();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpAward.size() > 0) {
                            try {
                                empDocList = (EmpDocList) listEmpAward.get(listEmpAward.size() - 1);
                                empAwardFetch = PstEmpAward.fetchExc(empDocList.getEmp_award_id());
                                HashtableEmp = PstEmpAward.fetchExcHashtable(empDocList.getEmp_award_id());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpAwardFormDoc' data-target2='#editor1' data-empdataid='"+empDocList.getEmp_award_id()+"'>add award</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                            if (objectTableFormula.equals("AWARD")) {
                                value = (String) HashtableEmp.get(objectStatusFormula);
                                if (value == null) {
                                    value = "-";
                                }

                            } else {
                                System.out.print("Selain Object Employee Award belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    } else if(objectType.equals("REPRIMAND") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpReprimand = PstEmpDocList.list(0, 0, whereC, "");
                        EmpDocList empDocList = new EmpDocList();
                        Employee employeeFetch = new Employee();
                        EmpReprimand empReprimandFetch = new EmpReprimand();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpReprimand.size() > 0) {
                            try {
                                empDocList = (EmpDocList) listEmpReprimand.get(listEmpReprimand.size() - 1);
                                empReprimandFetch = PstEmpReprimand.fetchExc(empDocList.getEmp_reprimand());
                                HashtableEmp = PstEmpReprimand.fetchExcHashtable(empDocList.getEmp_reprimand());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpReprimandFormDoc' data-target2='#editor1' data-empdataid='"+empDocList.getEmp_reprimand()+"'>add reprimand</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                            if (objectTableFormula.equals("REPRIMAND")) {
                                value = (String) HashtableEmp.get(objectStatusFormula);
                                if (value == null) {
                                    value = "-";
                                }

                            } else {
                                System.out.print("Selain Object Employee Reprimand belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    } else if(objectType.equals("TRAINING") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpReprimand = PstEmpDocList.list(0, 0, whereC, "");
                        EmpDocList empDocList = new EmpDocList();
                        Employee employeeFetch = new Employee();
                        TrainingHistory empTrainingFetch = new TrainingHistory();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpReprimand.size() > 0) {
                            try {
                                empDocList = (EmpDocList) listEmpReprimand.get(listEmpReprimand.size() - 1);
                                empTrainingFetch = PstTrainingHistory.fetchExc(empDocList.getEmp_training());
                                HashtableEmp = PstTrainingHistory.fetchExcHashtable(empDocList.getEmp_training());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpTrainingFormDoc' data-target2='#editor1' data-empdataid='"+empDocList.getEmp_reprimand()+"'>add training</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                                System.out.print("Selain Object Employee Training belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    } else if(objectType.equals("MUTATION") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpCareer = PstEmpDocListMutation.list(0, 0, whereC, "");
                        EmpDocListMutation empDocListMutation = new EmpDocListMutation();
                        Employee employeeFetch = new Employee();
                        EmpDocListMutation empDocListMutationFetch = new EmpDocListMutation();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpCareer.size() > 0) {
                            try {
                                empDocListMutation = (EmpDocListMutation) listEmpCareer.get(listEmpCareer.size() - 1);
                                //empDocListMutationFetch = PstEmpDocListMutation.fetchExc(empDocList.getEmployee_id());
                                HashtableEmp = PstEmpDocListMutation.fetchExcHashtable(empDocListMutation.getOID());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpCarrerFormDoc' data-target2='#editor1' data-empdataid='"+empDocListMutation.getOID()+"'>add mutation</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                                System.out.print("Selain Object Employee Training belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    }

                } else if (!objectName.equals("") && objectType.equals("") && objectClass.equals("") && objectStatusField.equals("")) {
                      String obj = ""+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"-");
                         empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"}", obj);
                         tanpaeditor = tanpaeditor.replace("${"+objectName+"}", obj);
                }
                stringResidual = stringResidual.substring(endPosition, stringResidual.length());
                objectDocumentDetail.setStartPosition(startPosition);
                objectDocumentDetail.setEndPosition(endPosition);
                objectDocumentDetail.setText(subString);
                vNewString.add(objectDocumentDetail);


                //mengecek apakah masih ada sisa
                startPosition = stringResidual.indexOf("${") + "${".length();
                endPosition = stringResidual.indexOf("}", startPosition);
         } while ( endPosition > 0);
         } catch (Exception e){}

        empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
        empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");
        
        String returnData = ""
                + "<div class='form-group'>"
                    + "<textarea class='form-control' id='editor_text' style='visibility: hidden; display: none;'>"+tanpaeditor+"</textarea>"
                + "</div>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_EMP_DOC_ID]+"'  value='"+empDoc1.getOID()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID]+"'  value='"+empDoc1.getDoc_master_id()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_TITLE]+"'  value='"+empDoc1.getDoc_title()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REQUEST_DATE_STRING]+"'  value='"+empDoc1.getRequest_date()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER]+"'  value='"+empDoc1.getDoc_number()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DATE_OF_ISSUE_STRING]+"'  value='"+empDoc1.getDate_of_issue()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_PLAN_DATE_FROM]+"'  value='"+empDoc1.getPlan_date_from()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_PLAN_DATE_TO]+"'  value='"+empDoc1.getPlan_date_to()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REAL_DATE_FROM]+"'  value='"+empDoc1.getReal_date_from()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REAL_DATE_TO]+"'  value='"+empDoc1.getReal_date_to()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_OBJECTIVES]+"'  value='"+empDoc1.getObjectives()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_COUNTRY_ID]+"'  value='"+empDoc1.getCountry_id()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_PROVINCE_ID]+"'  value='"+empDoc1.getProvince_id()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REGION_ID]+"'  value='"+empDoc1.getRegion_id()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_SUBREGION_ID]+"'  value='"+empDoc1.getSubregion_id()+"' class='form-control'>"
                + "<input type='hidden' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_GEO_ADDRESS]+"'  value='"+empDoc1.getGeo_address()+"' class='form-control'>";
 
                
        
        return returnData;
    }
    
    public String template(HttpServletRequest request, long oidMaster){
        
        String empDocMasterTemplateText = "";
        EmpDoc empDoc1 = new EmpDoc();
        DocMaster empDocMaster1 = new DocMaster();
        long templateId = 0;
        if (this.oid > 0){
            try {
            empDoc1 = PstEmpDoc.fetchExc(this.oid); 
                } catch (Exception e){ }
                if (empDoc1 != null){
                try {
                    empDocMaster1 = PstDocMaster.fetchExc(empDoc1.getDoc_master_id());
                } catch (Exception e){ }

                if (empDoc1.getDetails().length() > 0){
                    empDocMasterTemplateText = empDoc1.getDetails();
                } else {
                        try {
                            empDocMasterTemplateText = PstDocMasterTemplate.getTemplateText(empDoc1.getDoc_master_id());
                        } catch (Exception e){ }
                        }
                }
        } else {
            try {
                empDoc1 = PstEmpDoc.fetchExc(oidMaster); 
            } catch (Exception e){ }
            if (empDoc1 != null){
            try {
                empDocMaster1 = PstDocMaster.fetchExc(oidMaster);
            } catch (Exception e){ }
            try {
                empDocMasterTemplateText = PstDocMasterTemplate.getTemplateText(oidMaster);
            } catch (Exception e){ }
            }
        }
        String strYear = "";
        String strMonth = "";
        String strDay = "";
        
        empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
                                    
        empDocMasterTemplateText = empDocMasterTemplateText.replace("<;", "<");
        empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");
        String tanpaeditor = empDocMasterTemplateText;
        String subString = "";
        String stringResidual = empDocMasterTemplateText;
        Vector vNewString = new Vector();

        Hashtable empDOcFecthH = new Hashtable();

        try {
            empDOcFecthH = PstEmpDoc.fetchExcHashtable(this.oid);
        } catch (Exception e) {
        }

        String where1 = " " + PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
        Hashtable hlistEmpDocField = PstEmpDocField.Hlist(0, 0, where1, "");
        
        String where2 = " " + PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
        Vector listEmpDocRecipient = PstEmpDocRecipient.list(0, 0, where2, "");
        String recName = "";
        if(listEmpDocRecipient.size() > 0){
            for (int i = 0; i < listEmpDocRecipient.size(); i++){
                EmpDocRecipient empDocRecipient = (EmpDocRecipient) listEmpDocRecipient.get(i);
                if(!(empDocRecipient.getAlias().equals(""))){
                    recName = empDocRecipient.getAlias();
                } else if (empDocRecipient.getPositionId() > 0){
                    Position pos = new Position();
                    try {
                        pos = PstPosition.fetchExc(empDocRecipient.getPositionId());
                    } catch(Exception exc){}
                }
            }
        }
        
        if(empDoc1.getDate_of_issue() != null){
            issueDate = String.valueOf(empDoc1.getDate_of_issue());
        }
        

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
                        String add = "<a href='#' id='addEmpSingle' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='addempsingle'>add employee</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
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

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");

                    } else if (objectType.equals("LIST") && objectStatusField.equals("START")) {
                        String add = "<a href=\"javascript:cmdAddEmp('" + objectName + "','" + this.oid + "')\">add employee</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
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

                            String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
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

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>" + subStringOfTr2 + "</tr>", stringTrReplace);
                            tanpaeditor = tanpaeditor.replace("<tr>" + subStringOfTr2 + "</tr>", stringTrReplace);
                            //tutup perulanganya

                            //setelah baca td maka akan membuat td baru... disini

                            residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());

                            startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                            endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);

                        } while (endPositionOfTable > 0);

                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");

                    } else if (objectType.equals("LISTMUTATION") && objectStatusField.equals("START")) {
                        String add = "<a href=\"javascript:cmdAddEmpNew('" + objectName + "','" + this.oid + "')\">add employee</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
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

                            String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
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
                                                        String select = "<a href=\"javascript:cmdSelectPos('" + this.oid + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "','" + employeeFetch.getOID() + "')\">SELECT</a></br>";

                                                        String newposition = PstEmpDocListMutation.getNewPosition(this.oid, employeeFetch.getOID(), objectNameFormula) + "-" + select;
                                                        value = "-" + newposition;
                                                    } else {
                                                        if (objectStatusFormula.equals("NEW_GRADE_LEVEL_ID")) {
                                                            String newGrade = PstEmpDocListMutation.getNewGradeLevel(this.oid, employeeFetch.getOID(), objectNameFormula) + "";
                                                            value = "-" + newGrade;
                                                        } else if (objectStatusFormula.equals("NEW_HISTORY_TYPE")) {
                                                            String newHistoryType = PstEmpDocListMutation.getNewHistoryType(this.oid, employeeFetch.getOID(), objectNameFormula) + "";
                                                            value = "-" + newHistoryType;
                                                        } else {
                                                            value = (String) HashtableEmp.get(objectStatusFormula);
                                                        }

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

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>" + subStringOfTr5 + "</tr>", stringTrReplace);
                            tanpaeditor = tanpaeditor.replace("<tr>" + subStringOfTr5 + "</tr>", stringTrReplace);
                            //tutup perulanganya

                            //setelah baca td maka akan membuat td baru... disini

                            residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());

                            startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                            endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);

                        } while (endPositionOfTable > 0);

                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");

                    } else if (objectType.equals("REPORT_JV")) {

                      long oidPeriod = 0;
                      long oidDiv = 0;
                      long companyId = 504404575327187914l;

                      String[] divisionSelect = {""};
                      try{
                           //
                          if (objectStatusField.equals("PERIOD")){
                           oidPeriod = PstPayPeriod.getPayPeriodIdBySelectedDate(empDoc1.getRequest_date());
                          }else {
                            oidPeriod = PstPayPeriod.getPayPeriodIdByName(objectStatusField);  
                          }
                           if (objectClass.equals("ALL_DIVISION")){
                               String allDiv = PstSystemProperty.getValueByName("ALL_OID_FOR_JURNAL_DOCUMENT");
                               divisionSelect = allDiv.split(",");
                           } else {
                               oidDiv = PstDivision.getDivisionIdByName(objectClass);
                               String[] xx = {""+oidDiv};
                               divisionSelect = xx;
                           }

                      }catch(Exception e){}

                      String nilaiVal = "";
                      if (divisionSelect.length > 1 ){
                           nilaiVal = JurnalDocument.listJurnalAllDiv(oidPeriod, companyId, divisionSelect);
                      } else {
                           nilaiVal = JurnalDocument.listJurnal(oidPeriod, companyId, divisionSelect);
                      }
                      empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusField + "}", nilaiVal);
                      tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusField + "}", nilaiVal);

                    } else if (objectType.equals("LISTLINE") && objectStatusField.equals("START")) {
                        String add = "<a href=\"javascript:cmdAddEmp('" + objectName + "','" + this.oid + "')\">add employee</a></br>";

                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        //menghapus tutup formula 
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
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
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", subListLine);

                    } else if (objectType.equals("FIELD") && objectStatusField.equals("AUTO")) {
                        //String field = "<input type=\"text\" name=\""+ subString +"\" value=\"\">";
                        Date newd = new Date();
                        String field = "04/KEP/BPD-PMT/" + newd.getMonth() + "/" + newd.getYear();
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", field);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", field);

                    } else if (objectType.equals("FIELD")) {

                        if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXT"))) {
                            //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                            String add="<a href='#' id='addText' data-object='"+objectName+"' data-oid='"+this.oid+"' data-objectType='"+objectType+"' data-objectClass='"+objectClass+"' data-objectStatusField='"+objectStatusField+"' data-template1='"+this.datachange+"' data-for='addtext' data-target2='#editor1'>"+ (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "NEW TEXT") +"</a></br>";
//                            String add = "<a href=\"javascript:cmdAddText('" + this.oid + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "')\">" + (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "NEW TEXT") + "</a></br>";
                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
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
                                    if (strMonth.length() > 0){
                                        switch(Integer.valueOf(strMonth)){
                                            case 1: strMonth = "Januari"; break;
                                            case 2: strMonth = "Februari"; break;
                                            case 3: strMonth = "Maret"; break;
                                            case 4: strMonth = "April"; break;
                                            case 5: strMonth = "Mei"; break;
                                            case 6: strMonth = "Juni"; break;
                                            case 7: strMonth = "Juli"; break;
                                            case 8: strMonth = "Agustus"; break;
                                            case 9: strMonth = "September"; break;
                                            case 10: strMonth = "Oktober"; break;
                                            case 11: strMonth = "November"; break;
                                            case 12: strMonth = "Desember"; break;
                                        }
                                    }
                                    strDay = strDate.substring(8, 10);
                                    dateShow = strDay + " "+ strMonth + " " + strYear;  ////formatterDate.format(dateX);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            String add = "<a href='#' id='addText' data-object='"+objectName+"' data-oid='"+this.oid+"' data-objectType='"+objectType+"' data-objectClass='"+objectClass+"' data-objectStatusField='"+objectStatusField+"' data-template1='"+this.datachange+"' data-for='addtext' data-target2='#editor1'>" + (hlistEmpDocField.get(objectName) != null ? dateShow : "NEW DATE") + "</a></br>";
                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", (hlistEmpDocField.get(objectName) != null ? dateShow : " "));
                        } else if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXTAREA"))){
                            String add="<a href='#' id='addText' data-object='"+objectName+"' data-oid='"+this.oid+"' data-objectType='"+objectType+"' data-objectClass='"+objectClass+"' data-objectStatusField='"+objectStatusField+"' data-template1='"+this.datachange+"' data-for='addtext' data-target2='#editor1'>"+ (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "OPEN EDITOR") +"</a></br>";
//                            String add = "<a href=\"javascript:cmdAddText('" + this.oid + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "')\">" + (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "NEW TEXT") + "</a></br>";
                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : " "));
                        } else if ((objectClass.equals("CLASSFIELD"))) {
                            //cari tahu ini untuk perubahan apa Divisi
                            String getNama = "";
                            if (hlistEmpDocField.get(objectName) != null) {
                                getNama = PstDocMasterTemplate.getNama((String) hlistEmpDocField.get(objectName), objectStatusField);
                            }

                            String add = "<a href=\"javascript:cmdAddText('" + this.oid + "','" + objectName + "','" + objectType + "','" + objectClass + "','" + objectStatusField + "')\">" + (hlistEmpDocField.get(objectName) != null ? getNama : "ADD") + "</a></br>";
                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : " "));

                        } else if ((objectClass.equals("EMPDOCFIELD"))) {
                            //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(empDOcFecthH.get(objectName) != null?(String)empDOcFecthH.get(objectName):"add")+"</a></br>";
                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", "" + (empDOcFecthH.get(objectStatusField) != null ? empDOcFecthH.get(objectStatusField) : "-"));
                            tanpaeditor = tanpaeditor.replace("${" + subString + "}", "" + (empDOcFecthH.get(objectStatusField) != null ? empDOcFecthH.get(objectStatusField) : "-"));
                        }

                    } else if(objectType.equals("MULTIPLE")){
                        String add = "<a href='#' id='addRecipient' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='addrecipient' data-target2='#editor1' data-issuedate='"+issueDate+"'>"+(!(recName.equals("")) ? recName : "add recipient")+"</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", recName);
                    } else if(objectType.equals("AWARD") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpAward = PstEmpDocList.list(0, 0, whereC, "");
                        EmpDocList empDocList = new EmpDocList();
                        Employee employeeFetch = new Employee();
                        EmpAward empAwardFetch = new EmpAward();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpAward.size() > 0) {
                            try {
                                empDocList = (EmpDocList) listEmpAward.get(listEmpAward.size() - 1);
                                empAwardFetch = PstEmpAward.fetchExc(empDocList.getEmp_award_id());
                                HashtableEmp = PstEmpAward.fetchExcHashtable(empDocList.getEmp_award_id());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpAwardFormDoc' data-target2='#editor1' data-empdataid='"+empDocList.getEmp_award_id()+"'>add award</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                            if (objectTableFormula.equals("AWARD")) {
                                value = (String) HashtableEmp.get(objectStatusFormula);
                                if (value == null) {
                                    value = "-";
                                }

                            } else {
                                System.out.print("Selain Object Employee Award belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    } else if(objectType.equals("REPRIMAND") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpReprimand = PstEmpDocList.list(0, 0, whereC, "");
                        EmpDocList empDocList = new EmpDocList();
                        Employee employeeFetch = new Employee();
                        EmpReprimand empReprimandFetch = new EmpReprimand();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpReprimand.size() > 0) {
                            try {
                                empDocList = (EmpDocList) listEmpReprimand.get(listEmpReprimand.size() - 1);
                                empReprimandFetch = PstEmpReprimand.fetchExc(empDocList.getEmp_reprimand());
                                HashtableEmp = PstEmpReprimand.fetchExcHashtable(empDocList.getEmp_reprimand());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpReprimandFormDoc' data-target2='#editor1' data-empdataid='"+empDocList.getEmp_reprimand()+"'>add reprimand</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                            if (objectTableFormula.equals("REPRIMAND")) {
                                value = (String) HashtableEmp.get(objectStatusFormula);
                                if (value == null) {
                                    value = "-";
                                }

                            } else {
                                System.out.print("Selain Object Employee Reprimand belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    } else if(objectType.equals("TRAINING") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpReprimand = PstEmpDocList.list(0, 0, whereC, "");
                        EmpDocList empDocList = new EmpDocList();
                        Employee employeeFetch = new Employee();
                        TrainingHistory empTrainingFetch = new TrainingHistory();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpReprimand.size() > 0) {
                            try {
                                empDocList = (EmpDocList) listEmpReprimand.get(listEmpReprimand.size() - 1);
                                empTrainingFetch = PstTrainingHistory.fetchExc(empDocList.getEmp_training());
                                HashtableEmp = PstTrainingHistory.fetchExcHashtable(empDocList.getEmp_training());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpTrainingFormDoc' data-target2='#editor1' data-empdataid='"+empDocList.getEmp_training()+"'>add training</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                                System.out.print("Selain Object Employee Training belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    } else if(objectType.equals("MUTATION") && objectStatusField.equals("START")){
                        String whereC = " " + PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_OBJECT_NAME] + " = \"" + objectName + "\" AND " + PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID] + " = \"" + this.oid + "\"";
                        Vector listEmpCareer = PstEmpDocListMutation.list(0, 0, whereC, "");
                        EmpDocListMutation empDocListMutation = new EmpDocListMutation();
                        Employee employeeFetch = new Employee();
                        EmpDocListMutation empDocListMutationFetch = new EmpDocListMutation();
                        Hashtable HashtableEmp = new Hashtable();
                        if (listEmpCareer.size() > 0) {
                            try {
                                empDocListMutation = (EmpDocListMutation) listEmpCareer.get(listEmpCareer.size() - 1);
                                //empDocListMutationFetch = PstEmpDocListMutation.fetchExc(empDocList.getEmployee_id());
                                HashtableEmp = PstEmpDocListMutation.fetchExcHashtable(empDocListMutation.getOID());
                            } catch (Exception e) {
                            }

                        }
                        
                        String add = "<a href='#' id='addEmpAward' data-object='"+objectName+"' data-oid='"+this.oid+"' data-template1='"+this.datachange+"' data-for='showEmpCarrerFormDoc' data-target2='#editor1' data-empdataid='"+empDocListMutation.getOID()+"'>add mutation</a></br>";
                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + subString + "}", add);
                        tanpaeditor = tanpaeditor.replace("${" + subString + "}", " ");
                        int endPnutup = stringResidual.indexOf("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", endPosition);
                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                        //menghapus tutup formula 
                        
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
                                System.out.print("Selain Object Employee Training belum bisa dipanggil");
                            }

                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);

                            tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-" + objectStatusFormula + "}", value);
                            residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);

                            startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                            endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                        } while (endPositionOfFormula > 0);




                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                        tanpaeditor = tanpaeditor.replace("${" + objectName + "-" + objectType + "-" + objectClass + "-END" + "}", " ");
                    }

                } else if (!objectName.equals("") && objectType.equals("") && objectClass.equals("") && objectStatusField.equals("")) {
                    String obj = "" + (hlistEmpDocField.get(objectName) != null ? (String) hlistEmpDocField.get(objectName) : "-");
                    empDocMasterTemplateText = empDocMasterTemplateText.replace("${" + objectName + "}", obj);
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

        empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
        empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");

        
        if(this.dataFor.equals("viewDoc")){
            return tanpaeditor;
        } else {
            return empDocMasterTemplateText;
        }
    }
    
    public String empDocForm(){
	
	//CHECK DATA
	EmpDoc empDoc = new EmpDoc();
	
	if(this.oid != 0){
	    try{
		empDoc = PstEmpDoc.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector docMaster_key = new Vector(1,1);
        Vector docMaster_val = new Vector(1,1);
        
        docMaster_key.add("0");
        docMaster_val.add("Select Master Document...");
        
        Vector listDocMaster = PstDocMaster.listAll();
        for (int i = 0; i < listDocMaster.size(); i++){
            DocMaster docMaster = (DocMaster) listDocMaster.get(i);
            docMaster_key.add(""+docMaster.getOID());
            docMaster_val.add(""+docMaster.getDoc_title());
        }
        
        String returnData = ""
                + "<div class='form-group'>"
		    + "<label>Document Master</label>"
                        + docMasterSelect(empDoc.getDoc_master_id())
                + "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Document Title</label>"
                        + "<input type='text' placeholder='Type Title...' name='"+ FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_TITLE]+"'  id='"+ FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_TITLE]+"' class='form-control' value='"+empDoc.getDoc_title()+"'>"
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Document Number</label>"
			+ "<input type='text' placeholder='Automatic Generate...' name='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER]+"' id='"+FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER]+"' value='"+empDoc.getDoc_number()+"' class='form-control'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Document Date</label>"
			+ "<input type='text' name='"+ FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REQUEST_DATE_STRING]+"'  id='"+ FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REQUEST_DATE_STRING]+"' class='form-control datepicker' value='"+(empDoc.getRequest_date()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(empDoc.getRequest_date(),"yyyy-MM-dd"))+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Valid Date</label>"
			+ "<input type='text' name='"+ FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DATE_OF_ISSUE_STRING]+"'  id='"+ FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DATE_OF_ISSUE_STRING]+"' class='form-control datepicker' value='"+(empDoc.getDate_of_issue()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(empDoc.getDate_of_issue(),"yyyy-MM-dd"))+"'>"
		+ "</div>"
                + "<div class='form-group'>"
                    + "<label>Document</label>"
                    + "<table border='1' width='100%'>"
                        + "<td style='padding:10px' id='editor1'>";
                        if (this.oid != 0){
                           returnData += template(null, empDoc.getDoc_master_id());
                        }
        returnData += "</td>"
                    + "</table>"
                + "</div>";
	return returnData;
    }
    
    public String viewDoc(){
	
	//CHECK DATA
	EmpDoc empDoc = new EmpDoc();
	
	if(this.oid != 0){
	    try{
		empDoc = PstEmpDoc.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        String returnData = ""
                + "<div class='form-group'>"
                    + "<label></label>"
                    + "<table border='1'>"
                        + "<td style='padding:10px' id='editor1'>";
                        if (this.oid != 0){
                           returnData += template(null, empDoc.getDoc_master_id());
                        }
        returnData += "</td>"
                    + "</table>"
                + "</div>";
	return returnData;
    }
    
    public String approval(){
        String whereDocMasterFlow = " "+PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID] + " = \"" + this.template +"\"";
                                                 
        Vector listMasterDocFlow = PstDocMasterFlow.list(0,0, whereDocMasterFlow, "");
        String whereList = " "+PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID] + " = \"" + this.oid+"\"";
        Hashtable hashtableEmpDocFlow = PstEmpDocFlow.Hlist(0, 0, whereList, "FLOW_INDEX");  

        // cek komplit untuk approval
        boolean nstatus = true;
        for (int x = 0; x < listMasterDocFlow.size(); x++){
             DocMasterFlow docMasterFlow = (DocMasterFlow) listMasterDocFlow.get(x);
             EmpDocFlow empDocFlow = new EmpDocFlow();
                    if (hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id()) != null){
                        empDocFlow = (EmpDocFlow) hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id());
                    }
             if (empDocFlow.getOID() == 0){
                 nstatus = false;
             }
        }
         nstatus = true;
        String returnData =""
                + "<table class='table-striped' width='100%'>"
                + "<thead>"
                    + "<tr>"
                        + "<td>Name</td>"
                        + "<td>Flow Title</td>"
                        + "<td>Index</td>"
                        + "<td>Status</td>"
                    + "</tr>"
                + "</thead>";
                    for (int i = 0; i < listMasterDocFlow.size(); i++) {
                        DocMasterFlow docMasterFlow = (DocMasterFlow) listMasterDocFlow.get(i);
                        EmpDocFlow empDocFlow = new EmpDocFlow();
                        Vector appKey = new Vector(1,1);
                        Vector appVal = new Vector(1,1);
                        appKey.add("Select");
                        appVal.add("0");
                        try{
                        String whereClause = " 1=1 ";
                                if (docMasterFlow.getCompany_id() != 0 && docMasterFlow.getCompany_id() > 0) {
                                    whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] +" = "+ docMasterFlow.getCompany_id() ;
                                }
                                if (docMasterFlow.getDivision_id() != 0 && docMasterFlow.getDivision_id() > 0) {
                                    whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +" = "+ docMasterFlow.getDivision_id() ;
                                }
                                if (docMasterFlow.getDepartment_id() != 0 && docMasterFlow.getDepartment_id() > 0) {
                                    whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +" = "+ docMasterFlow.getDepartment_id() ;
                                }
                                if (docMasterFlow.getLevel_id() != 0 && docMasterFlow.getLevel_id() > 0) {
                                    whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] +" = "+ docMasterFlow.getLevel_id() ;
                                }
                                if (docMasterFlow.getPosition_id() != 0 && docMasterFlow.getPosition_id() > 0) {
                                    whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] +" = "+ docMasterFlow.getPosition_id() ;
                                }
                                if (docMasterFlow.getEmployee_id() != 0 && docMasterFlow.getEmployee_id() > 0) {
                                    whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +" = "+ docMasterFlow.getEmployee_id() ;
                                }
                                Vector listEmp = PstEmployee.list(0, 0, whereClause, "");

                                if (listEmp.size() > 0){
                                    for (int x=0;x<listEmp.size();x++){
                                        Employee employee = (Employee) listEmp.get(x);
                                        appKey.add(""+employee.getFullName());
                                        appVal.add(""+employee.getOID());
                                        if (hashtableEmpDocFlow.get(employee.getOID()) != null){
                                            empDocFlow = (EmpDocFlow) hashtableEmpDocFlow.get(employee.getOID());
                                        }
                                    }
                                }
                        }catch (Exception e ){}

                        String statusAp = "Be Approve";
                        boolean statusBoolean = false;
                        if (empDocFlow.getOID() != 0){
                            statusAp = "Approve";
                            statusBoolean = true;
                        }
                        Employee employee = new Employee();
                        try{
                            employee = PstEmployee.fetchExc(empDocFlow.getSignedBy());
                        } catch (Exception e){}
                        Vector rowx = new Vector();
                        //EmpDocumentDetails.jsp
                        if (statusBoolean){
                            returnData += "<tr><td>"+employee.getFullName()+"</td>";
                        } else {
                            //rowx.add("<a href=\"javascript:cmdApproval1('"+docMasterFlow.getEmployee_id()+"','"+oidEmpDoc+"','"+docMasterFlow.getFlow_title()+"','"+docMasterFlow.getFlow_index()+"')\">"+employee.getFullName()+"</a>");
                            String strAttribute = "class='form-control doapprove' data-oid='"+this.oid+"' data-empid='"+employee.getOID()+"' data-title='"+docMasterFlow.getFlow_title()+"' data-flowindex='"+docMasterFlow.getFlow_index()+"' data-index='"+i+"' data-for='dologin' ";
                            //rowx.add(""+ControlCombo.draw("app", null, "", appVal, appKey, strAttribute));
                            returnData += "<tr><td>"+ControlCombo.draw("FRM_APP"+i, "formElemen", null, String.valueOf(employee.getOID()), appVal, appKey, strAttribute)+"</td>";
                        }
                        returnData += 
                                    "<td>"+docMasterFlow.getFlow_title()+"</td>"
                                    + "<td>"+docMasterFlow.getFlow_index()+"</td>"
                                    + "<td>"+statusAp+"</td>"
                                + "</tr>";
                    }
                returnData += "</table>";
        
        return returnData; 
    }
    
    public String dologin(){
        String userName = "";
        AppUser objAppUser = new AppUser();
        if(this.oidApprover!=0)
        {
                try
                {
                        objAppUser = PstAppUser.getByEmployeeId(""+this.oidApprover);
                        userName = objAppUser.getLoginId();				
                }
                catch(Exception e)
                {
                        System.out.println("Exc when fetch app user : " + e.toString());
                }
        }
        String returnData ="<input type='hidden' name='oidApprover' value='"+this.oidApprover+"'>"
                            + "<input type='hidden' name='flowIndex' value='"+this.flowIndex+"'>"
                            + "<input type='hidden' name='flowTitle' value='"+this.flowTitle+"'>"
                            + "<input type='hidden' name='FRM_FIELD_OID' value='"+this.oid+"'>"
                            + "<input type='hidden' name='isApprove' value='"+this.isApprove+"'>"
                            + "<input type='hidden' name='approvedId1' value='0'>"
                            + "<input type='hidden' name='isApprove1' value='false'>"
                    + "<div class='text-center'>"
                       + "<font color='#FF0000' size='4'>"
                       + "<b><font face='Verdana, Arial, Helvetica, sans-serif'>"
                       + ".:: MANAGER AUTHORIZATION ::.</font></b></font>"
                    + "</div>";
                    if (userName.equals("")){
        returnData += "<div class='text-center'>"
                       + "<font color='#FF0000' size='4'>"
                       +  "<b><font face='Verdana, Arial, Helvetica, sans-serif'>"
                       +  ".:: USER NOT FOUND Please create User for Login ::.</font></b></font>"
                    + "</div>";
                    } else {
        returnData += "<div class='form-group'>"
                        + "<label>Login Name</label>"
                            + "<input type='text' name='login_id' id='login_id' value='"+userName+"' readOnly class='form-control'>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Password</label>"
                            + "<input type='password' name='pass_wd' id='pass_wd' class='form-control'>"
                    + "<div>";                
                    }
                    
        
        return returnData;
    }
    
    public String review(){
        
        EmpDoc empDoc = new EmpDoc();
	
	if(this.oid != 0){
	    try{
		empDoc = PstEmpDoc.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        String returnData = "<div class='form-group'>"
                            + "<label>Note</label>"
                            + "<textarea name='note' id='note' class='form-control'>"+(empDoc.getNote() != null ? empDoc.getNote() : "") +"</textarea>"
                        + "</div>"
                + "";
        
        return returnData;
    }
    
    public String viewnote(){
        EmpDoc empDoc = new EmpDoc();
	
	if(this.oid != 0){
	    try{
		empDoc = PstEmpDoc.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        String returnData = "<div class='form-group'>"
                            + "<input type='hidden' name='note' id='note' class='form-control' value='"+ empDoc.getNote()+"'>"
                            + empDoc.getNote()
                        + "</div>"
                + "";
        
        return returnData;
    }
    
    public String recipientForm(){
        //CHECK DATA
	EmpDocRecipient empDocRecipient = new EmpDocRecipient();
	Vector listRecipient = PstEmpDocRecipient.list(0, 0, PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID] + " = " + this.oid, "");

        if(listRecipient.size() > 0){
            empDocRecipient = (EmpDocRecipient) listRecipient.get(listRecipient.size() - 1);
        }
        String returnData = "<input type='hidden' name='"+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_EMP_DOC_RECIPIENT_ID]+"' id='"+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_EMP_DOC_RECIPIENT_ID]+"' value='"+(empDocRecipient.getOID() != 0 ? empDocRecipient.getOID() : "")+"'>"
                + "<input type='hidden' name='"+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_EMP_DOC_ID]+"' id='"+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_EMP_DOC_ID]+"' value='"+(empDocRecipient.getEmpDocId() != 0 ? empDocRecipient.getEmpDocId() : "")+"'>"
                + "<div class='form-group'>"
		    + "<label>Send To</label>"
                    + "<select class='form-control sendTo' id='sendTo' name='sendTo' data-for='getRecipient'>"
                        + "<option value=''>Select Recipient...</option>"
                        + "<option value='1'>Company</option>"
                        + "<option value='2'>Division</option>"
                        + "<option value='3'>Department</option>"
                        + "<option value='4'>Section</option>"
                        + "<option value='5'>Position</option>"
                        + "<option value='6'>Level</option>"                
                    + "</select>"
		+ "</div>"
                + "<div class='form-group' id='customSelection'>"
                + "</div>"
                +"<div class='form-group'>"
                    + "<label>Alias</label>"
                    + "<input type='text' placeholder='Type Alias...' name='"+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_ALIAS]+"' class='form-control' value='"+(empDocRecipient.getAlias() != null ? empDocRecipient.getAlias() : "")+"'>"
                + "</div>";
        
        return returnData;
    }
    
    public String addFilter(HttpServletRequest request){
        EmpDocRecipient empDocRecipient = new EmpDocRecipient();
	Vector listRecipient = PstEmpDocRecipient.list(0, 0, PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID] + " = " + this.oid, "");

        if(listRecipient.size() > 0){
            empDocRecipient = (EmpDocRecipient) listRecipient.get(listRecipient.size() - 1);
        }
        String filter = FRMQueryString.requestString(request, "FRM_FIELD_FILTER");
        String returnData = "";
        if(filter.equals("1")){
            returnData = "<div class='form-group'>"
		    + "<label>Company</label>"
                    + "<select class=\"form-control company_struct\" id=\"company\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-replacement=\"#division\">"
		    + companySelect(0)
                    + "</select>"
		+ "</div>";
        }
         else if(filter.equals("2")){
            returnData = "<div class='form-group'>"
		    + "<label>Company</label>"
                    + "<select class=\"form-control company_struct\" id=\"company\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-replacement=\"#division\">"
		    + companySelect(0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Division</label>"
		    + "<select id=\"division\" class=\"form-control company_struct\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_DIVISION_ID]+"\" data-for=\"get_department\" data-replacement=\"#department\">"
                    + divisionSelect(null, 0)
                    + "</select>"
		+ "</div>";
        }
          else if(filter.equals("3")){
            returnData = "<div class='form-group'>"
		    + "<label>Company</label>"
                    + "<select class=\"form-control company_struct\" id=\"company\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-replacement=\"#division\">"
		    + companySelect(0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Division</label>"
		    + "<select id=\"division\" class=\"form-control company_struct\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_DIVISION_ID]+"\" data-for=\"get_department\" data-replacement=\"#department\">"
                    + divisionSelect(null, 0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Department</label>"
		    + "<select id=\"department\" class=\"form-control company_struct\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_DEPARTMENT_ID]+"\" data-for=\"get_section\" data-replacement=\"#section\">"
                    + departmentSelect(null, 0)
                    + "</select>"
		+ "</div>";
        }
          else if(filter.equals("4")){
            returnData = "<div class='form-group'>"
		    + "<label>Company</label>"
                    + "<select class=\"form-control company_struct\" id=\"company\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-replacement=\"#division\">"
		    + companySelect(0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Division</label>"
		    + "<select id=\"division\" class=\"form-control company_struct\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_DIVISION_ID]+"\" data-for=\"get_department\" data-replacement=\"#department\">"
                    + divisionSelect(null, 0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Department</label>"
		    + "<select id=\"department\" class=\"form-control company_struct\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_DEPARTMENT_ID]+"\" data-for=\"get_section\" data-replacement=\"#section\">"
                    + departmentSelect(null, 0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Section</label>"
		    + "<select id=\"section\" class=\"form-control company_struct\" name=\""+FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_SECTION_ID]+"\">"
                    + sectionSelect(null, 0)
                    + "</select>"
		+ "</div>";
        }
          else if(filter.equals("5")){
            returnData = ""+ drawFormGroup("Position", drawSelect(FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_POSITION_ID], "", positionSelectVer1(null, empDocRecipient.getPositionId()), "", "", "position"), "");
        }
          else if(filter.equals("6")){
            returnData = ""+ drawFormGroup("Level", drawSelect(FrmEmpDocRecipient.fieldNames[FrmEmpDocRecipient.FRM_FIELD_LEVEL_ID], "", levelSelectVer1(null, empDocRecipient.getLevelId()), "", "", "level"), "");
        }
        
        return returnData;
    }
    
    public String drawFormGroup(String label, String input, String valueCaption){
        String strValueCaption = "";
        if (valueCaption.length()>0){
            strValueCaption = "<div id=\"div_value\">"+valueCaption+"</div>";
        }
        String data = "<div class='form-group'>"
		    + "<label>"+label+"</label>"
                    + strValueCaption
		    + input
                    + "</div>";
        return data;
    }
    
    public String drawSelect(String name, String styleClass, String option, String dataFor, String dataReplacement){
        return drawSelect(name, styleClass, option, dataFor, dataReplacement, "");
    }
    
    public String drawSelect(String name, String styleClass, String option, String dataFor, String dataReplacement, String id){
        String strDataFor = "";
        String strDataReplacement = "";
        if (dataFor.length()>0){
            strDataFor = " data-for=\""+dataFor+"\" ";
        }
        if (dataReplacement.length()>0){
            strDataReplacement = " data-replacement=\""+dataReplacement+"\" ";
        }
        String data = "<select class=\"form-control "+styleClass+"\" name=\""+name+"\" "+strDataFor+" "+strDataReplacement+" id=\""+id+"\">";
        data += option;
        data += "</select>";
        return data;
    }
    
    public String companySelect(long companyId){
        String data = "";
        Vector companyList = PstCompany.list(0, 0, "", "");
        data += "<option value=\"\">Select Company...</option>";
        if (companyList != null && companyList.size()>0){
            for (int i=0; i<companyList.size(); i++){
                Company company = (Company)companyList.get(i);
                if (companyId == company.getOID()){
                    data += "<option selected=\"selected\" value=\""+company.getOID()+"\">"+company.getCompany()+"</option>";
                } else {
                    data += "<option value=\""+company.getOID()+"\">"+company.getCompany()+"</option>";
                }
            }
        }
        return data;
    }
    public String divisionSelect(HttpServletRequest request, long divisionId){
        String data = "";
        data += "<option value=\"\">Select Division...</option>";
        String whereClause = PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS]+"="+PstDivision.VALID_ACTIVE;
        if(divisionId == 0){
            whereClause += " AND "+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+this.companyId;
        }
        Vector divisionList = PstDivision.list(0, 0, whereClause, "");
        if (divisionList != null && divisionList.size()>0){
           for (int i=0; i<divisionList.size(); i++){
                Division divisi = (Division)divisionList.get(i);
                if (divisionId == divisi.getOID()){
                    data += "<option selected=\"selected\" value=\""+divisi.getOID()+"\">"+divisi.getDivision()+"</option>";
                } else {
                    data += "<option value=\""+divisi.getOID()+"\">"+divisi.getDivision()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String departmentSelect(HttpServletRequest request, long departmentId){
        String data = "";
        if (this.divisionId != 0){
            this.companyId = this.divisionId;
        }
        data += "<option value=\"\">Select Department...</option>";
        Vector departList = new Vector();
        if(departmentId == 0){
            departList = PstDepartment.listDepartmentVer2(0, 0, this.companyId);
        } else {
            departList = PstDepartment.list(0, 0, "","");            
        }
        if (departList != null && departList.size()>0){
            for(int i=0; i<departList.size(); i++){
                Department depart = (Department)departList.get(i);
                if (departmentId == depart.getOID()){
                    data += "<option selected=\"selected\" value=\""+depart.getOID()+"\">"+depart.getDepartment()+"</option>";
                } else {
                    data += "<option value=\""+depart.getOID()+"\">"+depart.getDepartment()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String sectionSelect(HttpServletRequest request, long sectionId){
        String data = "";
        if (this.departmentId != 0){
            this.companyId = this.departmentId;
        }
        String whereClause = "";
        if (sectionId == 0){
            whereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+this.companyId;
        }
        data += "<option value=\"\">Select Section...</option>";
        Vector sectionList = PstSection.list(0, 0, whereClause, "");
        if (sectionList != null && sectionList.size()>0){
            for (int i=0; i<sectionList.size(); i++){
                Section section = (Section)sectionList.get(i);
                if (sectionId == section.getOID()){
                    data += "<option selected=\"selected\" value=\""+section.getOID()+"\">"+section.getSection()+"</option>";
                } else {
                    data += "<option value=\""+section.getOID()+"\">"+section.getSection()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String positionSelectVer1(HttpServletRequest request, long positionId){
        String data = "";
        data += "<option value=\"\">Select Position...</option>";
        Vector positionList = PstPosition.list(0, 0, "", "");
        if (positionList != null && positionList.size()>0){
            for (int i=0; i<positionList.size(); i++){
                Position position = (Position)positionList.get(i);
                if (positionId == position.getOID()){
                    data += "<option selected=\"selected\" value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
                } else {
                    data += "<option value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String levelSelectVer1(HttpServletRequest request, long levelId){
        String data = "";
        data += "<option value=\"\">Select Level...</option>";
        Vector levelList = PstLevel.list(0, 0, "", "");
        if (levelList != null && levelList.size()>0){
            for (int i=0; i<levelList.size(); i++){
                Level level = (Level)levelList.get(i);
                if (levelId == level.getOID()){
                    data += "<option selected=\"selected\" value=\""+level.getOID()+"\">"+level.getLevel()+"</option>";
                } else {
                    data += "<option value=\""+level.getOID()+"\">"+level.getLevel()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String positionSelect(HttpServletRequest request, long positionId){
        String data = "";
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(this.empId);
        } catch (Exception exc){
            
        }
        Level level = new Level();
        try {
            level = PstLevel.fetchExc(emp.getLevelId());
        } catch (Exception exc){
            
        }
        Vector levelList = new Vector();
        if (this.mutationId == 0){
            levelList = PstLevel.list(0, 0, PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" < "+level.getLevelRank(), "");
        } else if (this.mutationId == 1) {
            levelList = PstLevel.list(0, 0, PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" > "+level.getLevelRank(), "");
        } else if(this.mutationId == 2){
            levelList = PstLevel.list(0, 0, PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" = "+level.getLevelRank(), "");
        } else {
            levelList = PstLevel.list(0, 0, "", "");
        }
        String inLevelId = "";
        if (levelList.size() > 0){
            for (int i = 0; i < levelList.size() ; i++){
                Level lv = (Level) levelList.get(i);
                inLevelId = inLevelId + "," + lv.getOID();
            }
        }
        if (inLevelId.equals("")){
            inLevelId = "";
        } else {
            inLevelId = inLevelId.substring(1);            
        }

        
        data += "<option value=\"\"> Select Position... </option>";
        Vector positionList = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID]+" IN ("+inLevelId+")", "");
        if (positionList != null && positionList.size()>0){
            for (int i=0; i<positionList.size(); i++){
                Position position = (Position)positionList.get(i);
                        data += "<option value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
            }
        }
        return data;
    }
    
    public String levelSelect(HttpServletRequest request, long levelId){
        String data = "";
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(this.empId);
        } catch (Exception exc){
            
        }
        Level level = new Level();
        try {
            level = PstLevel.fetchExc(emp.getLevelId());
        } catch (Exception exc){
            
        }
        
        Vector levelList = new Vector();
        
        if (this.mutationId == 0){
            levelList = PstLevel.list(0, 0, PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" < "+level.getLevelRank(), "");
        } else if (this.mutationId == 1) {
            levelList = PstLevel.list(0, 0, PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" > "+level.getLevelRank(), "");
        } else if(this.mutationId == 2){
            levelList = PstLevel.list(0, 0, PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" = "+level.getLevelRank(), "");
        } else {
            levelList = PstLevel.list(0, 0, "", "");
        }
        data += "<option value=\"\">Select Level...</option>";
        
        if (levelList != null && levelList.size()>0){
            for (int i=0; i<levelList.size(); i++){
                Level lvl = (Level)levelList.get(i);
                    data += "<option value=\""+lvl.getOID()+"\">"+lvl.getLevel()+"</option>";
            }
        }
        return data;
    }
    
    public String empCategorySelect(long empCatId){
        String data = "";
        data += "<option value=\"\">Select Employee Category...</option>";
        Vector empCategoryList = PstEmpCategory.list(0, 0, "", "");
        if (empCategoryList != null && empCategoryList.size()>0){
            for (int i=0; i<empCategoryList.size(); i++){
                EmpCategory empCat = (EmpCategory)empCategoryList.get(i);
                if (empCatId == empCat.getOID()){
                    data += "<option selected=\"selected\" value=\""+empCat.getOID()+"\">"+empCat.getEmpCategory()+"</option>";
                } else {
                    data += "<option value=\""+empCat.getOID()+"\">"+empCat.getEmpCategory()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String chapterSelect(long chapterId){
        String data = "";
        
        Vector listBab = PstWarningReprimandBab.list(0, 0, "", PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID]);
        data = "<select class=\"form-control datachange\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_CHAPTER]+"\" id=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_CHAPTER]+"\" data-for=\"get_article\" data-target=\"#article\">";
        data += "<option value=\"\">Select Chapter</option>";
        if (listBab != null && listBab.size()>0){
            for (int i=0; i<listBab.size(); i++){
                WarningReprimandBab cbBab = (WarningReprimandBab)listBab.get(i);
                if (chapterId == cbBab.getOID()){
                    data += "<option selected=\"selected\" value=\""+cbBab.getOID()+"\">"+cbBab.getBabTitle()+"</option>";
                } else {
                    data += "<option value=\""+cbBab.getOID()+"\">"+cbBab.getBabTitle()+"</option>";
                }
            }
        }
        data += "</select>";
        return data;
    }
    public String articleSelect(HttpServletRequest request, long articleId){
        String data = "";
       
        data += "<option value=\"\">Select Article...</option>";
           
        String whereClause = "";
        if (this.datachange > 0){
            whereClause = PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_BAB_ID]+"="+this.datachange;
        }
        Vector articleList = PstWarningReprimandPasal.list(0, 0, whereClause, "");
        if (articleList != null && articleList.size()>0){
           for (int i=0; i<articleList.size(); i++){
                WarningReprimandPasal cbPasal = (WarningReprimandPasal)articleList.get(i);
                if (articleId == cbPasal.getOID()){
                    data += "<option selected=\"selected\" value=\""+cbPasal.getOID()+"\">"+cbPasal.getPasalTitle()+"</option>";
                } else {
                    data += "<option value=\""+cbPasal.getOID()+"\">"+cbPasal.getPasalTitle()+"</option>";
                }
                
            }
        }
        return data;
    }
    
    public String verseSelect(HttpServletRequest request, long verseId){
        String data = "";
        
        data += "<option value=\"\">Select Verse...</option>";
        long oidVerse = 0;
        
        String whereClause = "";
        if (this.datachange > 0){
          whereClause = PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_PASAL_ID]+"="+this.datachange;  
        } 
        Vector verseList = PstWarningReprimandAyat.list(0, 0, whereClause, "");
        if (verseList != null && verseList.size()>0){
            for(int i=0; i<verseList.size(); i++){
                WarningReprimandAyat cbAyat = (WarningReprimandAyat)verseList.get(i);
                if (verseId == cbAyat.getOID()){
                    data += "<option selected=\"selected\" value=\""+cbAyat.getOID()+"\">"+cbAyat.getAyatTitle()+"</option>";
                } else {
                    data += "<option value=\""+cbAyat.getOID()+"\">"+cbAyat.getAyatTitle()+"</option>";
                }
            }
        }
        return data;
    }
    
    //Emp Data
        public String empAwardFormDoc(){
	
	//CHECK DATA
	EmpAward empAward = new EmpAward();
	
	if(this.empDataId != 0){
	    try{
		empAward = PstEmpAward.fetchExc(this.empDataId);
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
        con_val.add("Select Reference...");
        
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
        emp_val.add("Select Employee...");
        
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
                + "<input type='hidden' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOCUMENT]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOCUMENT]+"' class='form-control' value='"+empAward.getDocument()+"'>"
                + "<input type='hidden' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOC_ID]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DOC_ID]+"' class='form-control' value='"+empAward.getDocId()+"'>"
                + "<div class='form-group'>"
		    + "<label>Employee *</label>"
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EMPLOYEE_ID], null, ""+empAward.getEmployeeId()+"", emp_key, emp_val, "id="+FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EMPLOYEE_ID], "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Award Title *</label>"
		    + "<input type='text' placeholder='Input Award Title...' name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]+"' class='form-control' value='"+empAward.getTitle()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Date</label>"
                    + "<input type='text' placeholder='Click For Date'  name='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DATE]+"'  id='"+ FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DATE]+"' class='form-control datepicker' value='"+(empAward.getAwardDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(empAward.getAwardDate(),"yyyy-MM-dd"))+"'>"
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
		    + ""+ControlCombo.draw(FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_PROVIDER_ID], null, ""+empAward.getProviderId()+"", con_key, con_val, disabledEx, "form-control external")+" "
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Description</label>"
		    + "<textarea rows='5' placeholder='Input Description For Award...' class='form-control' id='" + FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DESC] +"' name='" + FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DESC] +"'>"+ empAward.getAwardDescription()+"</textarea> "
		+ "</div>"
	    + "</div>"
	+ "</div>";
	return returnData;
    }
        
        public String empReprimandFormDoc(){
	
	//CHECK DATA
	EmpReprimand empReprimand = new EmpReprimand();
        
	if(this.empDataId != 0){
	    try{
		empReprimand = PstEmpReprimand.fetchExc(this.empDataId);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        Employee emp = new Employee();
        if(this.empId != 0){
            try {
                emp = PstEmployee.fetchExc(this.empId);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        
        String check = "checked";
        
        long oidCompany = 0;
        long oidDivision = 0;
        long oidSection = 0;
        long oidDepartment = 0;
        long oidPosition = 0;
        long oidLevel = 0;
        long oidCategory = 0;
        
        if(empReprimand.getCompanyId() != 0){
            oidCompany = empReprimand.getCompanyId();
        } else if (emp.getCompanyId() != 0 ){
            oidCompany = emp.getCompanyId();
        }
        if(empReprimand.getDivisionId() != 0){
            oidDivision = empReprimand.getDivisionId();
        } else if (emp.getDivisionId() != 0 ){
            oidDivision = emp.getDivisionId();
        }
        if(empReprimand.getDepartmentId() != 0){
            oidDepartment = empReprimand.getDepartmentId();
        } else if (emp.getDepartmentId() != 0 ){
            oidDepartment = emp.getDepartmentId();
        }
        if(empReprimand.getSectionId() != 0){
            oidSection = empReprimand.getSectionId();
        } else if (emp.getSectionId() != 0 ){
            oidSection = emp.getSectionId();
        }
        if(empReprimand.getPositionId() != 0){
            oidPosition = empReprimand.getPositionId();
        } else if (emp.getSectionId() != 0 ){
            oidPosition = emp.getPositionId();
        }
        if(empReprimand.getLevelId() != 0){
            oidLevel = empReprimand.getLevelId();
        } else if (emp.getLevelId() != 0 ){
            oidLevel = emp.getLevelId();
        }
        if(empReprimand.getEmpCategoryId() != 0){
            oidCategory = empReprimand.getEmpCategoryId();
        } else if (emp.getEmpCategoryId() != 0 ){
            oidCategory = emp.getEmpCategoryId();
        }
        
        //Reprimand Level ( Point )
        Vector repLvl_key = new Vector(1,1);
        Vector repLvl_val = new Vector(1,1);
        
        repLvl_key.add("");
        repLvl_val.add("Select Level Reprimand...");
        
        Vector listRep = PstReprimand.listAll();
        for(int i=0;i<listRep.size();i++){
            Reprimand rep = (Reprimand) listRep.get(i);
            repLvl_key.add(""+rep.getOID());
            repLvl_val.add(""+rep.getReprimandPoint());
        }
        
       long chapterId = 0;
       if (!(empReprimand.getChapter().equals(""))){
           chapterId = Long.valueOf(empReprimand.getChapter());
       }
       long articleId = 0;
       if (!(empReprimand.getArticle().equals(""))){
           articleId = Long.valueOf(empReprimand.getArticle());
       }
       long verseId = 0;
       if (!(empReprimand.getVerse().equals(""))){
           verseId = Long.valueOf(empReprimand.getVerse());
       }
       
        Vector emp_key = new Vector(1,1);
        Vector emp_val = new Vector(1,1);
        emp_key.add("0");
        emp_val.add("Select Employee...");
        
        Vector listEmployee = PstEmployee.list(0, 0, "", "");
        if (listEmployee != null && listEmployee.size() > 0) {
            for (int i = 0; i < listEmployee.size(); i++) {
               Employee employee = (Employee) listEmployee.get(i);
               emp_key.add(""+employee.getOID());
               emp_val.add(""+employee.getFullName());
            }
        }
       
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-6'>"
                + "<input type='hidden' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]+"'  id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]+"' class='form-control' value='"+empReprimand.getOID()+"'>"
                + "<input type='hidden' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DOCUMENT]+"'  id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DOCUMENT]+"' class='form-control' value='"+empReprimand.getDocument()+"'>"
                + "<input type='hidden' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMP_DOC_ID]+"'  id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMP_DOC_ID]+"' class='form-control' value='"+empReprimand.getEmpDocId()+"'>"
                 + "<div class='form-group'>"
		    + "<label>Employee *</label>"
		    + ""+ControlCombo.draw(FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMPLOYEE_ID], null, ""+empReprimand.getEmployeeId()+"", emp_key, emp_val, "id="+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMPLOYEE_ID]+ " data-for='getEmpCareer'", "form-control filteremp" )+" "
		+ "</div>"
                + "<div id='emp_container'>"
                    + "<div class='form-group'>"
                        + "<label>Company</label>"
                        + "<select class=\"form-control company_struct\" id=\"company\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-replacement=\"#division\">"
                        + companySelect(oidCompany)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Division</label>"
                        + "<select id=\"division\" class=\"form-control company_struct\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DIVISION_ID]+"\" data-for=\"get_department\" data-replacement=\"#department\">"
                        + divisionSelect(null, oidDivision)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Department</label>"
                        + "<select id=\"department\" class=\"form-control company_struct\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DEPARTMENT_ID]+"\" data-for=\"get_section\" data-replacement=\"#section\">"
                        + departmentSelect(null, oidDepartment)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Section</label>"
                        + "<select id=\"section\" class=\"form-control company_struct\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_SECTION_ID]+"\">"
                        + sectionSelect(null, oidSection)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Position *</label>"
                        + "<select id=\"position\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_POSITION_ID]+"\">"
                        + positionSelectVer1(null, oidPosition)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Level</label>"
                        + "<select id=\"level\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_LEVEL_ID]+"\">"
                        + levelSelectVer1(null, oidLevel)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Emp Category</label>"
                        + "<select id=\"emp_category\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMP_CATEGORY_ID]+"\">"
                        + empCategorySelect(oidCategory)
                        + "</select>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='col-md-6'>"
                + "<div class='form-group'>"
		    + "<label>Reprimand Level ( Point ) *</label>"
                    + ""+ControlCombo.draw(FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_LEVEL_ID], null, ""+empReprimand.getReprimandLevelId()+"", repLvl_key, repLvl_val, "id="+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_LEVEL_ID], "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Chapter</label>"
		    + chapterSelect(chapterId)
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Article</label>"
		    + "<select id=\"article\" class=\"form-control datachange\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_ARTICLE]+"\" data-for=\"get_verse\" data-target=\"#verse\">"
                    + articleSelect(null, articleId)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Verse</label>"
		    + "<select id=\"verse\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_VERSE]+"\">"
                    + verseSelect(null, verseId)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Page</label>"
                    + "<input type='text' placeholder='Type Page Reprimand...' name='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_PAGE]+"' class='form-control pull-right' id='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_PAGE]+"' value='"+empReprimand.getPage()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Date</label>"
                    + "<div class='input-group'>"
                        + "<div class='input-group-addon'>"
                            + "<i class='fa fa-calendar'></i>"
                        + "</div>"
                        + "<input type='text' name='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REP_DATE]+"' class='form-control pull-right datepicker' id='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REP_DATE]+"' value='"+(empReprimand.getReprimandDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(empReprimand.getReprimandDate(),"yyyy-MM-dd"))+"'>"
                    + "</div>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Description</label>"
                    + "<textarea class='form-control' placeholder='Type Description' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DESCRIPTION] +"' id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DESCRIPTION] +"'>"+ empReprimand.getDescription()+"</textarea>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Valid Until *</label>"
                    + "<div class='input-group'>"
                        + "<div class='input-group-addon'>"
                            + "<i class='fa fa-calendar'></i>"
                        + "</div>"
                        + "<input type='text' name='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_VALID_UNTIL]+"' class='form-control pull-right datepicker' id='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_VALID_UNTIL]+"' value='"+(empReprimand.getValidityDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(empReprimand.getValidityDate(),"yyyy-MM-dd"))+"'>"
                    + "</div>"
		+ "</div>"
                + "</div>"           
	    + "</div>"
	+ "</div>";
	return returnData;
        
    } 
        
    public String getEmployeeCareer(HttpServletRequest request){
        Employee emp = new Employee();
        if(this.empId != 0){
            try {
                emp = PstEmployee.fetchExc(this.empId);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        
        
        
        long oidCompany = 0;
        long oidDivision = 0;
        long oidSection = 0;
        long oidDepartment = 0;
        long oidPosition = 0;
        long oidLevel = 0;
        long oidCategory = 0;
        
        if (emp.getCompanyId() != 0 ){
            oidCompany = emp.getCompanyId();
        }
        if (emp.getDivisionId() != 0 ){
            oidDivision = emp.getDivisionId();
        }
        if (emp.getDepartmentId() != 0 ){
            oidDepartment = emp.getDepartmentId();
        }
        if (emp.getSectionId() != 0 ){
            oidSection = emp.getSectionId();
        }
        if (emp.getSectionId() != 0 ){
            oidPosition = emp.getPositionId();
        }
        if (emp.getLevelId() != 0 ){
            oidLevel = emp.getLevelId();
        }
        if (emp.getEmpCategoryId() != 0 ){
            oidCategory = emp.getEmpCategoryId();
        }
        
        String returnData = ""
                    + "<div class='form-group'>"
                        + "<label>Company</label>"
                        + "<select class=\"form-control company_struct\" id=\"company\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-replacement=\"#division\">"
                        + companySelect(oidCompany)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Division</label>"
                        + "<select id=\"division\" class=\"form-control company_struct\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DIVISION_ID]+"\" data-for=\"get_department\" data-replacement=\"#department\">"
                        + divisionSelect(null, oidDivision)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Department</label>"
                        + "<select id=\"department\" class=\"form-control company_struct\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DEPARTMENT_ID]+"\" data-for=\"get_section\" data-replacement=\"#section\">"
                        + departmentSelect(null, oidDepartment)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Section</label>"
                        + "<select id=\"section\" class=\"form-control company_struct\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_SECTION_ID]+"\">"
                        + sectionSelect(null, oidSection)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Position *</label>"
                        + "<select id=\"position\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_POSITION_ID]+"\">"
                        + positionSelectVer1(null, oidPosition)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Level</label>"
                        + "<select id=\"level\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_LEVEL_ID]+"\">"
                        + levelSelectVer1(null, oidLevel)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Emp Category</label>"
                        + "<select id=\"emp_category\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMP_CATEGORY_ID]+"\">"
                        + empCategorySelect(oidCategory)
                        + "</select>"
                    + "</div>"
                + "</div>";
        
        return returnData;
    }    
        
    public String trainHistoryForm(){
	
	//CHECK DATA
	TrainingHistory trainHist = new TrainingHistory();
	
	if(this.oid != 0){
	    try{
		trainHist = PstTrainingHistory.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector train_key = new Vector(1,1);
        Vector train_val = new Vector(1,1);
        
        train_key.add("");
        train_val.add("Select Training...");
        
        Vector listTraining = PstTraining.listAll();
        for (int i = 0; i < listTraining.size(); i++){
            Training train = (Training) listTraining.get(i);
            train_key.add(""+train.getOID());
            train_val.add(""+train.getName());
        }
        
               
        Vector emp_key = new Vector(1,1);
        Vector emp_val = new Vector(1,1);
        emp_key.add("0");
        emp_val.add("Select Employee...");
        
        Vector listEmployee = PstEmployee.list(0, 0, "", "");
        if (listEmployee != null && listEmployee.size() > 0) {
            for (int i = 0; i < listEmployee.size(); i++) {
               Employee employee = (Employee) listEmployee.get(i);
               emp_key.add(""+employee.getOID());
               emp_val.add(""+employee.getFullName());
            }
        }
        
        String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMP_DOC_ID]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMP_DOC_ID]+"' class='form-control' value='"+trainHist.getEmpDocId()+"'>"
                 + "<div class='form-group'>"
		    + "<label>Employee *</label>"
		    + ""+ControlCombo.draw(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID], null, ""+trainHist.getEmployeeId()+"", emp_key, emp_val, "id="+FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID], "form-control")+" "
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Training Title *</label>"
		    + "<input type='text' placeholder='Input Training Title...' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_TITLE]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_TITLE]+"' class='form-control' value='"+trainHist.getTrainingTitle()+"'>"
		+ "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Trainer</label>"
                    + "<input type='text' placeholder='Input Trainer...' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINER]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINER]+"' class='form-control' value='"+trainHist.getTrainer()+"'>"
		+ "</div>"        
                + "<div class='form-group'>"
		    + "<label>Start date - End date</label>"
		    + "<div class='input-group'>"
			+ "<input type='text' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_DATE]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_DATE]+"' class='form-control datepicker' value='"+(trainHist.getStartDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(trainHist.getStartDate(),"yyyy-MM-dd"))+"'>"
			+ "<div class='input-group-addon'>"
			    + "To"
			+ "</div>"
			+ "<input type='text' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_DATE]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_DATE]+"' class='form-control datepicker' value='"+(trainHist.getEndDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(trainHist.getEndDate(),"yyyy-MM-dd"))+"'>"
		    + "</div>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Start time - End time</label>"
		    + "<div class='input-group'>"
			+ "<input type='text' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_TIME_STRING]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_TIME_STRING]+"' class='form-control datetimepicker' value='"+(trainHist.getStartTime()== null ? Formater.formatDate(new Date(), "HH:MM") : Formater.formatDate(trainHist.getStartTime(),"HH:MM"))+"'>"
			+ "<div class='input-group-addon'>"
			    + "To"
			+ "</div>"
			+ "<input type='text' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_TIME_STRING]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_TIME_STRING]+"' class='form-control datetimepicker' value='"+(trainHist.getEndTime()== null ? Formater.formatDate(new Date(), "HH:MM") : Formater.formatDate(trainHist.getEndTime(),"HH:MM"))+"'>"
		    + "</div>"
		+ "</div>"
	    + "</div>"
	+ "</div>";
	return returnData;
    }       
    
    public String getDoMutationForm(){
        ChangeValue changeValue = new ChangeValue();
        
        Vector emp_key = new Vector(1,1);
        Vector emp_val = new Vector(1,1);
        emp_key.add("0");
        emp_val.add("Select Employee...");
        
        Vector listEmployee = PstEmployee.list(0, 0, "", "");
        if (listEmployee != null && listEmployee.size() > 0) {
            for (int i = 0; i < listEmployee.size(); i++) {
               Employee employee = (Employee) listEmployee.get(i);
               emp_key.add(""+employee.getOID());
               emp_val.add(""+employee.getFullName());
            }
        }
        String strData = ""
        + "<div class='row'>"
            + "<div class='col-md-12'>"
                + "<div class='form-group'>"
		    + "<label>Employee *</label>"
		    + ""+ControlCombo.draw(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMPLOYEE_ID], null, "0", emp_key, emp_val, "id="+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMPLOYEE_ID] +" data-for='getCareer'", "form-control getCareer")+" "
		+ "</div>"
                + "<div class='form-group' id='careerContainer'>" 
                + "</div>"
            + "</div>"
        + "</div>";
        return strData;
    }    
    
    public String drawInputHidden(String name, String value){
        return drawInputHidden(name, value, "");
    }
    
    public String drawInputHidden(String name, String value, String id){
        String data = "<input type=\"hidden\" name=\""+name+"\" value=\""+value+"\" id=\""+id+"\">";
        return data;
    }
    
    public String getEmpCareerPath(HttpServletRequest request){
        
        Employee employee = new Employee();
        if (this.empId != 0){
            try {
                employee = PstEmployee.fetchExc(this.empId);
            } catch(Exception e){
                System.out.println("fecth employee: "+e.toString());
            }
        }
        
        String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+this.empId;
        String order = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]+" DESC";
        Vector careerList = PstCareerPath.list(0, 0, whereClause, order);
        String workFrom = null;
        String workTo = null;
        String contractFrom = null;
        String contractTo = null;
        int contractType = 0;
        Calendar c = Calendar.getInstance(); 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (careerList != null && careerList.size()>0){
            CareerPath career = (CareerPath)careerList.get(0);
            c.setTime(career.getWorkTo()); 
            c.add(Calendar.DATE, 1);
            workFrom = sdf.format(c.getTime());
            Date dt = new Date();
            workTo = sdf.format(dt);
            contractFrom = sdf.format(career.getContractFrom());
            contractTo = sdf.format(career.getContractTo());
            contractType = career.getContractType();
        } else {
            c.setTime(employee.getCommencingDate()); 
            c.add(Calendar.DATE, 1);
            workFrom = sdf.format(c.getTime());
            Date dt = new Date();
            workTo = sdf.format(dt);
            contractFrom = sdf.format(employee.getCommencingDate());
            contractTo = sdf.format(employee.getEnd_contract());
        }
        String inCategoryId = "";
        for (int i = 0; i < careerList.size(); i++){
            CareerPath career = (CareerPath) careerList.get(i);
            inCategoryId = inCategoryId + "," + career.getEmpCategoryId();
        }
        if (careerList.size() > 0){
            inCategoryId = inCategoryId.substring(1);
        }
        String returnData = "<div class='form-group'>"
                        + drawInputHidden(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_OBJECT_NAME], object)
                        + drawInputHidden(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMPLOYEE_ID], ""+this.empId)
                        + drawInputHidden(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_FROM_STR], workFrom)
                        + drawInputHidden(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_TO_STR], workTo)
                        + drawInputHidden(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_CONTRACT_FROM_STR], contractFrom)
                        + drawInputHidden(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_CONTRACT_TO_STR], contractTo)
                        + "<label>Mutation Type *</label>"
                        + "<select class=\"form-control company_struct\" name=\""+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_TIPE_DOC]+"\" id=\""+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_TIPE_DOC]+"\" data-for=\"get_position\" data-empId="+this.empId+" data-replacement=\"#position_level\">"
                            + "<option value=\"\">-SELECT-</option>"
                            + "<option value=\""+PstCareerPath.PROMOTION_MUTATION+"\">"+PstCareerPath.mutationTypeValue[PstCareerPath.PROMOTION_MUTATION]+"</option>"
                            + "<option value=\""+PstCareerPath.ROTATION_MUTATION+"\">"+PstCareerPath.mutationTypeValue[PstCareerPath.ROTATION_MUTATION]+"</option>"
                            + "<option value=\""+PstCareerPath.DEMOTION_MUTATION+"\">"+PstCareerPath.mutationTypeValue[PstCareerPath.DEMOTION_MUTATION]+"</option>"
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Company *</label>"
                        + "<select class=\"form-control company_struct\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_COMPANY_ID]+" id=\"company_mutation\" data-for=\"get_division\" data-replacement=\"#division_mutation\">"
                        + companySelect(0)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Division *</label>"
                        + "<select id=\"division_mutation\" class=\"form-control company_struct\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_DIVISION_ID]+" data-for=\"get_department\" data-replacement=\"#department_mutation\">"
                        + divisionSelect(null, 0)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Department *</label>"
                        + "<select id=\"department_mutation\" class=\"form-control company_struct\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_DEPARTMENT_ID]+" data-for=\"get_section\" data-replacement=\"#section_mutation\">"
                        + departmentSelect(null, 0)
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Section *</label>"
                        + "<select id=\"section_mutation\" class=\"form-control company_struct\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_SECTION_ID]+">"
                        + sectionSelect(null, 0)
                        + "</select>"
                    + "</div>"
                    + "<div id=\"position_level\">"
                        + "<div class='form-group'>"
                            + "<label>Position *</label>"
                            + "<select class=\"form-control\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_POSITION_ID]+" id=\"position_mutation\">"
                            + positionSelect(null, 0)
                            + "</select>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Level *</label>"
                            + "<select class=\"form-control\" name="+FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_LEVEL_ID]+" id=\"level_mutation\">"
                            + levelSelect(null, 0)
                            + "</select>"
                        + "</div>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Work From</label>"
                        + "<input type='text' name='"+ FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_FROM_STR]+"'  id='"+ FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_FROM_STR]+"' class='form-control datepicker' value=''>";
        return returnData;
    }
    
    public String doLogin(HttpServletRequest request){
            String htmlReturn="";
            boolean loggedInSuccess = false;
            AppUser user = PstAppUser.getByLoginIDAndPassword(this.strLoginId, this.strPassword);
            if(user!=null && user.getEmployeeId()==this.oidApprover)
            {
                    loggedInSuccess = true;
                    this.isApprove = true;
            }
            if (loggedInSuccess){   
                htmlReturn = "1";
                try{
                  EmpDocFlow empDocFlow = new EmpDocFlow();
                  empDocFlow.setFlowIndex(this.flowIndex);
                  empDocFlow.setFlowTitle(this.flowTitle);
                  empDocFlow.setEmpDocId(this.oid);
                  empDocFlow.setSignedBy(this.oidApprover);
                  Date signedId = new Date();
                  empDocFlow.setSignedDate(signedId);
                  long n = PstEmpDocFlow.insertExc(empDocFlow);


                  //cek status document
                  EmpDoc empDoc1 = new EmpDoc();
                  try {
                        empDoc1 = PstEmpDoc.fetchExc(this.oid); 
                    } catch (Exception e){ }
                  String whereDocMasterFlow = " "+PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID] + " = \"" + empDoc1.getDoc_master_id() +"\"";

                    Vector listMasterDocFlow = PstDocMasterFlow.list(0,0, whereDocMasterFlow, "");
                    String whereList = " "+PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID] + " = \"" + this.oid+"\"";
                    Hashtable hashtableEmpDocFlow = PstEmpDocFlow.Hlist(0, 0, whereList, "FLOW_INDEX");  

                    // cek komplit untuk approval
                    boolean nstatus = true;
                    if (listMasterDocFlow.size() == 0){
                        nstatus = false;
                    }
                    for (int x = 0; x < listMasterDocFlow.size(); x++){
                         DocMasterFlow docMasterFlow = (DocMasterFlow) listMasterDocFlow.get(x);
                         EmpDocFlow empDocFlow1 = new EmpDocFlow();
                                if (hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id()) != null){
                                    empDocFlow1 = (EmpDocFlow) hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id());
                                }
                         if (empDocFlow1.getOID() == 0){
                             nstatus = false;
                         }
                    }
                    if (nstatus){
                        int nilai = PstEmpDoc.updateEmpDoc(this.oid, I_DocStatus.DOCUMENT_STATUS_FINAL );
                        
                        //CareerPath
                        String whereStatus  = PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID] + " = " + this.oid;
                        Vector listEmpDocMutation = PstEmpDocListMutation.list(0, 0, whereStatus, "");
                        if (listEmpDocMutation.size() > 0){
                            for (int i=0; i < listEmpDocMutation.size() ; i++){
                                EmpDocListMutation empDocListMutation = (EmpDocListMutation) listEmpDocMutation.get(i);
                                long companyId = empDocListMutation.getCompanyId();
                                long divisionId = empDocListMutation.getDivisionId();
                                long departmentId = empDocListMutation.getDepartmentId();
                                long sectionId = empDocListMutation.getSectionId();
                                long positionId = empDocListMutation.getPositionId();
                                long levelId = empDocListMutation.getLevelId();
                                
                                Employee emp = new Employee();
                                try{
                                    emp = PstEmployee.fetchExc(empDocListMutation.getEmployeeId());
                                } catch (Exception exc){}
                                
                                String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+emp.getOID();
                                String order = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]+" DESC";
                                Vector careerList = PstCareerPath.list(0, 0, whereClause, order);
                                String workFromstr = "";
                                Date workFrom = new Date();
                                
                                Calendar c = Calendar.getInstance(); 
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                if (careerList != null && careerList.size()>0){
                                    CareerPath career = (CareerPath)careerList.get(0);
                                    c.setTime(career.getWorkTo()); 
                                    c.add(Calendar.DATE, 1);
                                    workFromstr = sdf.format(c.getTime());
                                    workFrom = sdf.parse(workFromstr);
                                } else {
                                    c.setTime(emp.getCommencingDate()); 
                                    c.add(Calendar.DATE, 1);
                                    workFromstr = sdf.format(c.getTime());
                                    workFrom = sdf.parse(workFromstr);
                                }
                                
                                CareerPath career = new CareerPath();
                                career.setEmployeeId(emp.getOID());
                                career.setCompanyId(emp.getCompanyId());
                                career.setDivisionId(emp.getDivisionId());
                                career.setDepartmentId(emp.getDepartmentId());
                                career.setSectionId(emp.getSectionId());
                                career.setPositionId(emp.getPositionId());
                                career.setLevelId(emp.getLevelId());
                                career.setEmpCategoryId(emp.getEmpCategoryId());
                                career.setWorkFrom(workFrom);
                                career.setWorkTo(empDocListMutation.getWorkTo());
                                career.setContractFrom(empDocListMutation.getContractFrom());
                                career.setContractTo(empDocListMutation.getContractTo());
                                career.setMutationType(empDocListMutation.getTipeDoc());
                                career.setHistoryType(PstCareerPath.DO_MUTATION);
                                career.setEmpDocId(this.oid);
                                PstCareerPath.insertExc(career);
                                
                                try {
                                    String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
                                    sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId+", ";
                                    sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId+", ";
                                    sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId+", ";
                                    sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId+", ";
                                    sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+positionId+", ";
                                    sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+"="+levelId+" ";
                                    //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                    sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+empDocListMutation.getEmployeeId();
                                    DBHandler.execUpdate(sqlUpdateEmp);
                                } catch(Exception e){
                                    System.out.print("Save Career => "+e.toString());
                                }
                                
                            }
                        } // End Career Path
                        
                        //Recipient Add ke General Doc
                        String whereRecipient = PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID] + " = " + this.oid;
                        Vector listRecipient = PstEmpDocRecipient.list(0, 0, whereRecipient, "");
                        String whereClause = "";
                        Vector listEmployee = new Vector();
                        if (listRecipient.size() > 0){
                            EmpDocGeneral empDocGeneral = new EmpDocGeneral();
                            for (int i=0; i < listRecipient.size(); i++){
                                EmpDocRecipient empDocRecipient = (EmpDocRecipient) listRecipient.get(i);
                                if (empDocRecipient.getSectionId() > 0 ){
                                    whereClause = PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + empDocRecipient.getSectionId();
                                    listEmployee = PstEmployee.list(0, 0, whereClause, "");
                                    if (listEmployee.size() > 0){
                                        for (int x = 0; x < listEmployee.size() ;x++){
                                            Employee emp = (Employee) listEmployee.get(x);
                                            empDocGeneral.setEmpDocId(this.oid);
                                            empDocGeneral.setEmployeeId(emp.getOID());
                                            empDocGeneral.setAcknowledgeStatus(0);
                                            PstEmpDocGeneral.insertExc(empDocGeneral);
                                        }
                                    }
                                } else if (empDocRecipient.getDepartmentId() > 0){
                                    whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + empDocRecipient.getDepartmentId();
                                    listEmployee = PstEmployee.list(0, 0, whereClause, "");
                                    if (listEmployee.size() > 0){
                                        for (int x = 0; x < listEmployee.size() ;x++){
                                            Employee emp = (Employee) listEmployee.get(x);
                                            empDocGeneral.setEmpDocId(this.oid);
                                            empDocGeneral.setEmployeeId(emp.getOID());
                                            empDocGeneral.setAcknowledgeStatus(0);
                                            PstEmpDocGeneral.insertExc(empDocGeneral);
                                        }
                                    }
                                } else if (empDocRecipient.getDivisionId() > 0){
                                    whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + empDocRecipient.getDivisionId();
                                    listEmployee = PstEmployee.list(0, 0, whereClause, "");
                                    if (listEmployee.size() > 0){
                                        for (int x = 0; x < listEmployee.size() ;x++){
                                            Employee emp = (Employee) listEmployee.get(x);
                                            empDocGeneral.setEmpDocId(this.oid);
                                            empDocGeneral.setEmployeeId(emp.getOID());
                                            empDocGeneral.setAcknowledgeStatus(0);
                                            PstEmpDocGeneral.insertExc(empDocGeneral);
                                        }
                                    }
                                } else if (empDocRecipient.getCompanyId() > 0){
                                    whereClause = PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + empDocRecipient.getCompanyId();
                                    listEmployee = PstEmployee.list(0, 0, whereClause, "");
                                    if (listEmployee.size() > 0){
                                        for (int x = 0; x < listEmployee.size() ;x++){
                                            Employee emp = (Employee) listEmployee.get(x);
                                            empDocGeneral.setEmpDocId(this.oid);
                                            empDocGeneral.setEmployeeId(emp.getOID());
                                            empDocGeneral.setAcknowledgeStatus(0);
                                            PstEmpDocGeneral.insertExc(empDocGeneral);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }catch (Exception e){

                }
        }else{
                htmlReturn += "0";
            }
       return htmlReturn;
    }
    
    private String updateFinger(HttpServletRequest request){
        String htmlReturn="";
        String spvName ="";
        long oidDetail = 0;
        long empId = 0;
        EmpDocGeneral empDocGeneral = new EmpDocGeneral();
        
        oidDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OID_DETAIL");
        empId = FRMQueryString.requestLong(request, "FRM_FIELD_EMP_ID");
        
        String whereStatus = PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID] + " = " + oidDetail + " AND "
                            + PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID] + " = " + empId;
        Vector listDocGeneral = PstEmpDocGeneral.list(0,0,whereStatus,"");
        if (listDocGeneral.size() > 0){
                EmpDocGeneral empDoc = (EmpDocGeneral) listDocGeneral.get(listDocGeneral.size() - 1);
                String sqlUpdate = "UPDATE "+PstEmpDocGeneral.TBL_HR_EMP_DOC_GENERAL+" SET ";
                sqlUpdate += PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_ACKNOWLEDGE_STATUS]+"=1";
                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                sqlUpdate += " WHERE "+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID]+"="+empDoc.getOID();
                try {
                    DBHandler.execUpdate(sqlUpdate);
                } catch (Exception exc){}
        } else {
            EmpDocGeneral empDoc = new EmpDocGeneral();
            try {
                empDoc = PstEmpDocGeneral.fetchExc(oidDetail);
                String sqlUpdate = "UPDATE "+PstEmpDocGeneral.TBL_HR_EMP_DOC_GENERAL+" SET ";
                sqlUpdate += PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_ACKNOWLEDGE_STATUS]+"=1";
                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                sqlUpdate += " WHERE "+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID]+"="+empDoc.getOID();
                try {
                    DBHandler.execUpdate(sqlUpdate);
                } catch (Exception exc){}
            } catch (Exception exc){
                
            }
        } 
        
        
        
        
        
        String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
        sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_FINGER_CHECK]+"=0";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+empId;
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
    