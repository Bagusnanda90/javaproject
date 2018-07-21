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
public class AjaxCompany extends HttpServlet {

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
	if(this.dataFor.equals("showCompanyForm")){
	  this.htmlReturn = companyForm();
	}
        
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlCompany ctrlCompany = new CtrlCompany(request);
	this.iErrCode = ctrlCompany.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlCompany.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlCompany ctrlCompany = new CtrlCompany(request);
	this.iErrCode = ctrlCompany.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlCompany.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlCompany ctrlCompany = new CtrlCompany(request);
	this.iErrCode = ctrlCompany.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlCompany.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listCompany")){
	    String[] cols = { PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID],
                PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID],
		PstCompany.fieldNames[PstCompany.FLD_COMPANY],
                PstCompany.fieldNames[PstCompany.FLD_DESCRIPTION], 
                PstCompany.fieldNames[PstCompany.FLD_COMPANY_PARENTS_ID],
                PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
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
        
        if(dataFor.equals("listCompany")){
	    whereClause += " ("+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listCompany")){
	    total = PstCompany.getCount(whereClause);
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
        Company comp = new Company();
        Company parent = new Company();
        String whereClause = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listCompany")){
               whereClause += " ("+PstCompany.fieldNames[PstCompany.FLD_COMPANY]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstCompany.fieldNames[PstCompany.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%' )";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listCompany")){
	    listData = PstCompany.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listCompany")){
		comp = (Company) listData.get(i);
                try {
                    parent = PstCompany.fetchExc(comp.getCompanyParentsId());
                } catch (Exception exc){}
                String checkButton = "<input type='checkbox' name='"+FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY_ID]+"' class='"+FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY_ID]+"' value='"+comp.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+comp.getCompany());
                ja.put(""+comp.getDescription());
		ja.put(""+PstCompany.getCompanyName(comp.getCompanyParentsId()));
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+comp.getOID()+"' data-for='showCompanyForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+comp.getOID()+"' data-for='deleteDocMasterSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    public String companyForm(){
	
	//CHECK DATA
	Company comp = new Company();
	
	if(this.oid != 0){
	    try{
		comp = PstCompany.fetchExc(this.oid);
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
		    + "<label>Company *</label>"
                    + "<input type='text' class='form-control' id='"+ FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY] +"' value='"+comp.getCompany()+"' name='"+ FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY] +"'>"
                + "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Company Temporary Address</label>"
                        + "<input type='text' name='"+ FrmCompany.fieldNames[FrmCompany.FRM_FIELD_DESCRIPTION]+"'  id='"+ FrmCompany.fieldNames[FrmCompany.FRM_FIELD_DESCRIPTION]+"' class='form-control' value='"+comp.getDescription()+"'>"
                + "</div>"
                + "<div class='form-group'>"
    		    + "<label>Company Parents</label>"
                    + "<select id='"+FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY_PARENTS_ID]+"' class='form-control' name='"+FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY_PARENTS_ID]+"'>"
                        + "<option value='0'>Select Parent</option>";
                        Vector listComp = PstCompany.list(0,0, "", " COMPANY ");
                        for (int i=0; i < listComp.size(); i++){
                            Company company = (Company) listComp.get(i);
                            if(comp.getCompanyParentsId() == company.getOID()){
                                returnData += "<option selected='selected' value='"+company.getOID()+"'>"+company.getCompany()+"</option>";
                            } else {
                                returnData += "<option value='"+company.getOID()+"'>"+company.getCompany()+"</option>";
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