/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.harisma.entity.masterdata.DocMaster;
import com.dimata.harisma.entity.masterdata.DocMasterTemplate;
import com.dimata.harisma.entity.masterdata.DocType;
import com.dimata.harisma.entity.masterdata.PstDocMaster;
import com.dimata.harisma.entity.masterdata.PstDocMasterTemplate;
import com.dimata.harisma.entity.masterdata.PstDocType;
import com.dimata.harisma.form.masterdata.CtrlDocMaster;
import com.dimata.harisma.form.masterdata.FrmDocMaster;
import com.dimata.harisma.form.masterdata.FrmDocMasterTemplate;
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
public class AjaxDocMaster extends HttpServlet {

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
	if(this.dataFor.equals("showDocMasterForm")){
	  this.htmlReturn = docMasterForm();
	}
        if(this.dataFor.equals("showDocMasterTemplate")){
	  this.htmlReturn = docTemplateForm();
	} if (this.dataFor.equals("showFlowForm")){
          this.htmlReturn = docMasterFlow();  
        }
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlDocMaster ctrlDocMaster = new CtrlDocMaster(request);
	this.iErrCode = ctrlDocMaster.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDocMaster.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlDocMaster ctrlDocMaster = new CtrlDocMaster(request);
	this.iErrCode = ctrlDocMaster.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlDocMaster.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listDocMaster")){
	    String[] cols = { PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_MASTER_ID],
		PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_TYPE_ID],
                PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_TITLE], 
                PstDocMaster.fieldNames[PstDocMaster.FLD_DESCRIPTION]};

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
        
        if(dataFor.equals("listDocMaster")){
	    whereClause += " ("+PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_MASTER_ID]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listDocMaster")){
	    total = PstDocMaster.getCount(whereClause);
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
        DocType docType = new DocType();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listDocMaster")){
               whereClause += " ("+PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_MASTER_ID]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_TITLE]+" LIKE '%"+this.empId+"%' )";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listDocMaster")){
	    listData = PstDocMaster.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listDocMaster")){
		docMaster = (DocMaster) listData.get(i);
                try {
                    docType = PstDocType.fetchExc(docMaster.getDoc_type_id());
                } catch (Exception exc){}
                String checkButton = "<input type='checkbox' name='"+FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DOC_MASTER_ID]+"' class='"+FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DOC_MASTER_ID]+"' value='"+docMaster.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+docType.getType_name());
                ja.put(""+docMaster.getDoc_title());
		ja.put(""+docMaster.getDescription());
                String buttonTemplate = "<a class='btn btn-primary fancybox fancybox.iframe btn-xs'  href='doc_master_template.jsp?DocMasterId="+docMaster.getOID()+"' data-toggle='tooltip' data-placement='top' title='Template'><i class='fa fa-file'></i></a> ";
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                String buttonFlow = "<button class='btn btn-warning btnflow btn-xs' data-oid='"+docMaster.getOID()+"' data-for='showFlowForm' type='button' data-toggle='tooltip' data-placement='top' title='Approval'><i class='fa fa-user'></i></button> ";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+docMaster.getOID()+"' data-for='deleteDocMasterSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> "+buttonTemplate+buttonFlow);
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
    
    public String docMasterFlow(){
        String data = ""
                + "<div class='row'>"
                    + "<div class='col-md-12'>"
                        + "<div class='box-body'>"
                            + "<div id='docFlowElement'>"
                                + "<table class='table table-bordered table-striped'>"
                                    + "<thead>"
                                        + "<tr>"
                                            + "<th></th>"
                                            + "<th>No</th>"
                                            + "<th>Company</th>"
                                            + "<th>Division</th>"
                                            + "<th>Department</th>"
                                            + "<th>Level</th>"
                                            + "<th>Position</th>"
                                            + "<th>Employee</th>"
                                            + "<th>Action</th>"
                                        + "</tr>"
                                    + "</thead>"
                                + "</table>"
                            + "</div>"
                        + "<div class='box-footer'>"
                            + "<button class='btn btn-primary btnaddflow' data-oid='0' data-oidmaster='"+this.oid+"' data-for='showDocFlowForm' type='button' href='#addflow'>"
                                + "<i class='fa fa-plus'></i> Add Approver"
                            + "</button> "
                            + "<button class='btn btn-danger btndeleteflow' data-for='flow' type='button'>"
                                + "<i class='fa fa-trash'></i> Delete"
                            + "</button>"
                        + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>";
        
        return data;
    }
    
    public String docMasterForm(){
	
	//CHECK DATA
	DocMaster docMaster = new DocMaster();
	
	if(this.oid != 0){
	    try{
		docMaster = PstDocMaster.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector type_key = new Vector(1,1);
        Vector type_val = new Vector(1,1);
        
        type_key.add("0");
        type_val.add("-Select-");
        
        Vector listDocType = PstDocType.listAll();
        for (int i = 0; i < listDocType.size(); i++){
            DocType docType = (DocType) listDocType.get(i);
            type_key.add(""+docType.getOID());
            type_val.add(""+docType.getType_name());
        }
        
        String returnData = ""
                + "<div class='form-group'>"
		    + "<label>Document Type</label>"
                        + ""+ControlCombo.draw(FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DOC_TYPE_ID], null, ""+docMaster.getDoc_type_id()+"", type_key, type_val, "", "form-control")+" "
                + "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Title</label>"
                        + "<input type='text' name='"+ FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_TITLE]+"'  id='"+ FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_TITLE]+"' class='form-control' value='"+docMaster.getDoc_title()+"'>"
                + "</div>"
                + "<div class='form-group'>"
    		    + "<label>Document Default Number</label>"
                        + "<input type='text' name='"+ FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DOC_NUMBER]+"'  id='"+ FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DOC_NUMBER]+"' class='form-control' value='"+docMaster.getDoc_number()+"'>"
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Description</label>"
			+ "<textarea rows='5' name='"+ FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DESCRIPTION]+"'  id='"+ FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DESCRIPTION]+"' class='form-control' >"+ docMaster.getDescription()+"</textarea> "
		+ "</div>";
	return returnData;
    }
    
    public String docTemplateForm() {
        
        DocMaster docMaster = new DocMaster();
	DocMasterTemplate docMasterTemplate = new DocMasterTemplate();
	if(this.oid != 0){
	    try{
		docMaster = PstDocMaster.fetchExc(this.oid);
                docMasterTemplate = PstDocMasterTemplate.fetchExc(docMaster.getDoc_master_id());
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector docmaster_value = new Vector(1, 1);
        Vector docmaster_key = new Vector(1, 1);
        Vector listdocmaster = PstDocMaster.list(0, 0, "", "");
        docmaster_value.add(""+0);
        docmaster_key.add("select");
        for (int i = 0; i < listdocmaster.size(); i++) {
            DocMaster docMstr = (DocMaster) listdocmaster.get(i);
            docmaster_key.add(docMstr.getDoc_title());
            docmaster_value.add(String.valueOf(docMstr.getOID()));
        }
        
        String returnData = ""
                + "<div class='form-group>'"
                    + "<label>Title</label>"
                        + "<input type='text' name='"+ FrmDocMasterTemplate.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE]+"'  id='"+ FrmDocMasterTemplate.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE]+"' class='form-control' value='"+docMasterTemplate.getTemplate_title()+"'>"
                + "</div>"
                + "<div class='form-group>'"
                    + "<label>Doc Master</label>"
                    + "" + ControlCombo.draw(FrmDocMasterTemplate.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "form-control", null, "" + (docMasterTemplate.getDoc_master_id()!=0?docMasterTemplate.getDoc_master_id():"-"), docmaster_value, docmaster_key, "")+""
                + "</div>"
                + "<div class='form-group>'"
                    + "<label>Detail</label>"
                    + "<textarea class='ckeditor' name='"+ FrmDocMasterTemplate.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEXT_TEMPLATE]+"'  id='"+ FrmDocMasterTemplate.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEXT_TEMPLATE]+"' rows='100' >"+ docMasterTemplate.getText_template()+"</textarea> "
                + "</div>"
                + "";
        
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
    