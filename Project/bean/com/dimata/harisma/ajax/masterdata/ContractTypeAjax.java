/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.harisma.entity.masterdata.ContractType;
import com.dimata.harisma.entity.masterdata.PstContractType;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Dimata 007
 */
public class ContractTypeAjax extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
    private long oidEmployee = 0;
    private long oidReturn = 0;
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
	this.oidEmployee = FRMQueryString.requestLong(request, "empId");
        
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
	
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
	if (this.dataFor.equals("contract_type_form")){
	   this.htmlReturn = contractTypeForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
        /* save code */
        String message = "";
        String contractTypeName = FRMQueryString.requestString(request, "field_contract_type");
        ContractType contType = new ContractType();
        contType.setContractName(contractTypeName);
        if (this.oid != 0){
            contType.setOID(this.oid);
            try {
                PstContractType.updateExc(contType);
                message = "Update successful";
            } catch (Exception e){
                System.out.print(e.toString());
            }
        } else {
            try {
                PstContractType.insertExc(contType);
                message = "Insert successful";
            } catch (Exception e){
                System.out.print(e.toString());
            }
        }
        
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        
    }
    
    public void commandDeleteAll(HttpServletRequest request){        
        String[] splits = this.oidDelete.split(",");
        String message = "";
        for (String asset : splits) {
            if (!"".equals(asset)) {
                long oidCont = Long.parseLong(asset);
                if (oidCont != 0) {
                    try {
                        long oid = PstContractType.deleteExc(oidCont);                    
                    } catch (Exception exc) {
                        message = "career_c";
                    }
                }
            }
        }
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("contract_list")){
	    String[] cols = {
                PstContractType.fieldNames[PstContractType.FLD_CONTRACT_NAME]
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

        String colName = cols[col];
        int total = -1;
        total = PstContractType.getCount(whereClause); 

        this.amount = amount;
        this.colName = colName;
        this.dir = dir;
        this.start = start;
        this.colOrder = col;
        
        try {
            result = getData(total, request, dataFor, whereClause);
        } catch(Exception ex){
            System.out.println(ex);
        }
       
       return result;
    }
    
    public JSONObject getData(int total, HttpServletRequest request, String datafor, String whereClause){
        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();

        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("contract_list")){
            listData = PstContractType.list(this.start, this.amount, whereClause, order);
	}

        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("contract_list")){
                ContractType contType = (ContractType)listData.get(i);
                ja.put(""+(i+1));
                ja.put(contType.getContractName());
                String action = "<button class='btn btn-warning btneditgeneral' data-oid='"+contType.getOID()+"' data-for='contract_type_form' type='button'><i class=\"fa fa-pencil\"></i> Edit</button>" +
                "&nbsp;<div class='btn btn-default' type='button'><input type='checkbox' name='contract_type_id' class='contract_type_id' value='"+contType.getOID()+"'> Delete</div>";
                ja.put(action);
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
    
    public String contractTypeForm(){
        ContractType contType = new ContractType();
        try {
            contType = PstContractType.fetchExc(this.oid);
        } catch (Exception e){
            System.out.println("ContractType Fecth => "+e.toString());
        }
        String strData = ""
        + "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='field_contract_id' class='form-control' value=\""+contType.getOID()+"\">"
                + "<div class='form-group'>"
		    + "<label>Contract Type Name</label>"
		    + "<input type='text' name='field_contract_type' id='field_contract_type' class='form-control' value=\""+contType.getContractName()+"\" />"
		+ "</div>"
            + "</div>"
        + "</div>";
        return strData;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
