/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
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
 * @author Gunadi
 */
public class AjaxDocumentAcknowledge extends HttpServlet {

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
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oid);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
	
	response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandNone(HttpServletRequest request){
        if (this.dataFor.equals("showAcknowledgeStatus")){
          this.htmlReturn = docAcknowledge();  
        }
    }
    
    public void commandSave(HttpServletRequest request){
	
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	
    }
    
    public void commandDelete(HttpServletRequest request){
        
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listEmpGeneralDoc")){
	    String[] cols = { PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID],
		PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]};

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
        
	long oidGeneralDoc = Long.valueOf(PstSystemProperty.getValueByName("OID_GENERAL_DOCUMENT"));
        Vector listEmpDocList = new Vector();
        Vector listEmpDocListMutation = new Vector();
        String whereList = PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = " + this.oid;
        String whereListMutation = PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID] + " = " +this.oid;

        listEmpDocList = PstEmpDocList.list(0, 0, whereList, "");
        listEmpDocListMutation = PstEmpDocListMutation.list(0, 0, whereListMutation, "");

                    
        String colName = cols[col];
        int total = -1;

        
        EmpDoc empDoc = new EmpDoc();
        try{
            empDoc = PstEmpDoc.fetchExc(this.oid);
        } catch (Exception exc){
            
        }
        if (oidGeneralDoc == empDoc.getDoc_master_id()){
            if(dataFor.equals("listEmpGeneralDoc")){
                whereClause += " DOC."+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]+" LIKE '%"+this.oid+"%' "
                            + " AND ( EMP.EMPLOYEE_NUM LIKE '%"+this.searchTerm+"%' OR EMP.FULL_NAME LIKE '%"+this.searchTerm+"%')";
            }

            if(dataFor.equals("listEmpGeneralDoc")){
                total = PstEmpDocGeneral.getCountDataTable(whereClause);
            }
        } else if (listEmpDocList.size() > 0)  {
            for (int i = 0 ; i < listEmpDocList.size() ; i++ ){
                EmpDocList empDocList = (EmpDocList) listEmpDocList.get(i);
                if (empDocList.getEmp_award_id() > 0 ){
                    if(dataFor.equals("listEmpGeneralDoc")){
                        whereClause += " "+PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+" LIKE '%"+this.oid+"%'";
                    }

                    if(dataFor.equals("listEmpGeneralDoc")){
                        total = PstEmpAward.getCount(whereClause);
                    }
                }
                if (empDocList.getEmp_reprimand() > 0 ){
                    if(dataFor.equals("listEmpGeneralDoc")){
                        whereClause += " "+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+" LIKE '%"+this.oid+"%'";
                    }

                    if(dataFor.equals("listEmpGeneralDoc")){
                        total = PstEmpReprimand.getCount(whereClause);
                    }
                }
            }
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
        Employee emp = new Employee();
        EmpAward empAward = new EmpAward();
        EmpReprimand empReprimand = new EmpReprimand();
        String whereClause = "";
        String order ="";
        String document = "";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        
        long oidGeneralDoc = Long.valueOf(PstSystemProperty.getValueByName("OID_GENERAL_DOCUMENT"));
        Vector listEmpDocList = new Vector();
        Vector listEmpDocListMutation = new Vector();
        Vector listEmpDocGeneral = new Vector();
        
        String whereList = PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = " + this.oid;
        String whereListMutation = PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID] + " = " + this.oid;
        String whereDocGeneral = PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID] + " = " + this.oid;

        listEmpDocList = PstEmpDocList.list(0, 0, whereList, "");
        listEmpDocListMutation = PstEmpDocListMutation.list(0, 0, whereListMutation, "");
        listEmpDocGeneral = PstEmpDocGeneral.list(0, 0, whereDocGeneral, "");
        
        
        try{
            empDoc = PstEmpDoc.fetchExc(this.oid);
        } catch (Exception exc){
            
        }
        if (oidGeneralDoc == empDoc.getDoc_master_id()){
            if (this.searchTerm == null){
                whereClause += ""+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]+" LIKE '%"+this.oid+"%'";                  
            }else{
                 if(dataFor.equals("listEmpGeneralDoc")){
                    whereClause += " DOC."+PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]+" LIKE '%"+this.oid+"%' "
                        + " AND ( EMP.EMPLOYEE_NUM LIKE '%"+this.searchTerm+"%' OR EMP.FULL_NAME LIKE '%"+this.searchTerm+"%')";
                }
            }

            if (this.colOrder>=0){
                order += ""+colName+" "+dir+"";
            }

            Vector listData = new Vector(1,1);
            if(datafor.equals("listEmpGeneralDoc")){
                listData = PstEmpDocGeneral.listDataTable(this.start, this.amount,whereClause,order);
            }



            for (int i =0 ; i<=listData.size()-1;i++){
                JSONArray ja = new JSONArray();
                if(datafor.equals("listEmpGeneralDoc")){
                    empDocGeneral = (EmpDocGeneral) listData.get(i);
                    try {
                        emp = PstEmployee.fetchExc(empDocGeneral.getEmployeeId());
                    } catch (Exception exc){}
                    try {
                        docMaster = PstDocMaster.fetchExc(empDoc.getDoc_master_id());
                    } catch (Exception exc){}

                    ja.put(""+(this.start+i+1));
                    ja.put(""+emp.getEmployeeNum());
                    ja.put(""+emp.getFullName());

                    String iconDone= "<i class='fa fa-check-circle' style='color:green'>";
                    String iconNotYet="<i class='fa fa-times-circle' style='color:red'>";
                    if (empDocGeneral.getAcknowledgeStatus() == 1){
                        ja.put(iconDone);
                    } else {
                        ja.put(iconNotYet);
                    }
                    array.put(ja);
                }
            }
        } else if (listEmpDocList.size() > 0)  {
            for (int i = 0 ; i < listEmpDocList.size() ; i++ ){
                EmpDocList empDocList = (EmpDocList) listEmpDocList.get(i);
                if (empDocList.getEmp_award_id() > 0 ){
                    if (this.searchTerm == null){
                        whereClause += ""+PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+" LIKE '%"+this.oid+"%'";                  
                    }else{
                         if(dataFor.equals("listEmpGeneralDoc")){
                           whereClause += " "+PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+" LIKE '%"+this.oid+"%'";
                        }
                    }

                    if (this.colOrder>=0){
                        order += ""+colName+" "+dir+"";
                    }

                    Vector listData = new Vector(1,1);
                    if(datafor.equals("listEmpGeneralDoc")){
                        listData = PstEmpAward.list(this.start, this.amount,whereClause,"");
                    }



                    for (int x =0 ; x<=listData.size()-1;x++){
                        JSONArray ja = new JSONArray();
                        if(datafor.equals("listEmpGeneralDoc")){
                            empAward = (EmpAward) listData.get(x);
                            try {
                                emp = PstEmployee.fetchExc(empAward.getEmployeeId());
                            } catch (Exception exc){}
                            
                            ja.put(""+(this.start+i+1));
                            ja.put(""+emp.getEmployeeNum());
                            ja.put(""+emp.getFullName());

                            String iconDone= "<i class='fa fa-check-circle' style='color:green'>";
                            String iconNotYet="<i class='fa fa-times-circle' style='color:red'>";
                            if (empAward.getAcknowledgeStatus() == 1){
                                ja.put(iconDone);
                            } else {
                                ja.put(iconNotYet);
                            }
                            array.put(ja);
                        }
                    }
                }
                if (empDocList.getEmp_reprimand() > 0 ){
                    if (this.searchTerm == null){
                        whereClause += ""+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+" LIKE '%"+this.oid+"%'";                  
                    }else{
                         if(dataFor.equals("listEmpGeneralDoc")){
                           whereClause += " "+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+" LIKE '%"+this.oid+"%'";
                        }
                    }

                    if (this.colOrder>=0){
                        order += ""+colName+" "+dir+"";
                    }

                    Vector listData = new Vector(1,1);
                    if(datafor.equals("listEmpGeneralDoc")){
                        listData = PstEmpReprimand.list(this.start, this.amount,whereClause,order);
                    }



                    for (int x =0 ; x<=listData.size()-1;x++){
                        JSONArray ja = new JSONArray();
                        if(datafor.equals("listEmpGeneralDoc")){
                            empReprimand = (EmpReprimand) listData.get(x);
                            try {
                                emp = PstEmployee.fetchExc(empReprimand.getEmployeeId());
                            } catch (Exception exc){}
                            
                            ja.put(""+(this.start+i+1));
                            ja.put(""+emp.getEmployeeNum());
                            ja.put(""+emp.getFullName());

                            String iconDone= "<i class='fa fa-check-circle' style='color:green'>";
                            String iconNotYet="<i class='fa fa-times-circle' style='color:red'>";
                            if (empReprimand.getAcknowledgeStatus() == 1){
                                ja.put(iconDone);
                            } else {
                                ja.put(iconNotYet);
                            }
                            array.put(ja);
                        }
                    }
                }
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
    
    
    public String docAcknowledge(){
        String data = ""
                + "<div class='row'>"
                    + "<div class='col-md-12'>"
                        + "<div class='box-body'>"
                            + "<div id='docAcknowledgeElement'>"
                                + "<table class='table table-bordered table-striped'>"
                                    + "<thead>"
                                        + "<tr>"
                                            + "<th>No</th>"
                                            + "<th>Payroll</th>"
                                            + "<th>Name</th>"
                                            + "<th>Status</th>"
                                        + "</tr>"
                                    + "</thead>"
                                + "</table>"
                            + "</div>"
                        + "<div class='box-footer'>"
                        + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>";
        
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
