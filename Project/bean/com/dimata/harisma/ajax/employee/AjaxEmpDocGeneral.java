/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.harisma.entity.masterdata.DocMaster;
import com.dimata.harisma.entity.masterdata.EmpDoc;
import com.dimata.harisma.entity.masterdata.EmpDocGeneral;
import com.dimata.harisma.entity.masterdata.PstDocMaster;
import com.dimata.harisma.entity.masterdata.PstEmpDoc;
import com.dimata.harisma.entity.masterdata.PstEmpDocGeneral;
import com.dimata.harisma.form.masterdata.FrmEmpDocGeneral;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
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
 * @author Gunadi
 */
public class AjaxEmpDocGeneral extends HttpServlet {

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
            
            case Command.DELETE :
                commandDelete(request);
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

    }
    
    public void commandSave(HttpServletRequest request){
	
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	
    }
    
    public void commandDelete(HttpServletRequest request){
        
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listEmpGeneralDoc")){
	    String[] cols = { PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID],
		PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID],
                PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID], 
                PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_ACKNOWLEDGE_STATUS]};

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
        
        if(dataFor.equals("listEmpGeneralDoc")){
	     whereClause += " "+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listEmpGeneralDoc")){
	    total = PstEmpDocGeneral.getCount(whereClause);
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
        EmpDocGeneral empDocGeneral = new EmpDocGeneral();
        EmpDoc empDoc = new EmpDoc();
        DocMaster docMaster = new DocMaster();
        String whereClause = "";
        String order ="";
        String document = "";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += ""+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listEmpGeneralDoc")){
               whereClause += " "+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listEmpGeneralDoc")){
	    listData = PstEmpDocGeneral.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listEmpGeneralDoc")){
		empDocGeneral = (EmpDocGeneral) listData.get(i);
                try {
                    empDoc = PstEmpDoc.fetchExc(empDocGeneral.getEmpDocId());
                } catch (Exception exc){}
                try {
                    docMaster = PstDocMaster.fetchExc(empDoc.getDoc_master_id());
                } catch (Exception exc){}
                
                ja.put(""+(this.start+i+1));
		ja.put(""+docMaster.getDoc_title());
                ja.put(""+empDoc.getDoc_title());
		ja.put(""+empDoc.getDoc_number());
                ja.put(""+Formater.formatDate(empDoc.getRequest_date(),"dd MMMM yyyy"));
                String buttonTemplate= "<button class='btn btn-primary btneditor btn-xs' data-oid='"+empDocGeneral.getEmpDocId()+"' data-template1='"+empDoc.getDoc_master_id()+"' data-for='viewDoc' type='button' data-toggle='tooltip' data-placement='top' title='Editor' data-target='editor1'><i class='fa fa-file'></i></button> ";
                
		String buttonUpdate = "";
                String buttonSend = "";
		if(privUpdate){
                    if(empDocGeneral.getAcknowledgeStatus() == 0){
                        buttonUpdate += "<button class='btn btn-warning btnacknowledge btn-xs' data-oid='"+empDocGeneral.getOID()+"' data-for='showEmpDocGeneralAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledge'><i class='fa fa-thumbs-up'></i></button> ";
                    } else if(empDocGeneral.getAcknowledgeStatus() > 0){
                        buttonUpdate += "<button class='btn btn-success btn-xs' data-oid='"+empDocGeneral.getOID()+"' data-for='showEmpDocGeneralAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledged'><i class='fa fa-check-circle'></i></button> ";
                    }
		    ja.put(buttonTemplate+buttonUpdate);
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
