/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.admin;

import com.dimata.harisma.entity.admin.FingerPatern;
import com.dimata.harisma.entity.admin.PstFingerPatern;
import com.dimata.harisma.form.admin.CtrlFingerPatern;
import com.dimata.harisma.form.admin.FrmFingerPatern;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AjaxFingerPatern extends HttpServlet {

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
        this.awardId = FRMQueryString.requestLong(request, "awardId");
	
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
    
    public void commandDelete(HttpServletRequest request){
        CtrlFingerPatern ctrlFingerPatern = new CtrlFingerPatern(request);
	this.iErrCode = ctrlFingerPatern.action(this.iCommand, this.oid, this.empId, this.oidDelete);
	String message = ctrlFingerPatern.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    } 
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlFingerPatern ctrlFingerPatern = new CtrlFingerPatern(request);
	this.iErrCode = ctrlFingerPatern.action(this.iCommand, this.oid, this.empId, this.oidDelete);
	String message = ctrlFingerPatern.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }    
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showFingerPaternForm")){
	  this.htmlReturn = fingerForm();
	} else if (this.dataFor.equals("generateDocAward")){
          
        }
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listFinger")){
	    String[] cols = { PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_PATERN_ID],
		PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID],
                PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE],
                PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_PATERN]};

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
        
        if(dataFor.equals("listFinger")){
	    whereClause += "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_FORE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_FORE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_LITTLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_LITTLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_MIDDLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_MIDDLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_RING_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_RING_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_THUMB+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_THUMB]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_FORE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_FORE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_LITTLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_LITTLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_MIDDLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_MIDDLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_RING_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_RING_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_THUMB+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_THUMB]+"', '')))))))))) "
                            + " LIKE '%"+this.searchTerm+"%' AND "
                       + " "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listFinger")){
	    total = PstFingerPatern.getCount(whereClause);
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
        FingerPatern finger = new FingerPatern();
        String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listFinger")){
               whereClause += "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_FORE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_FORE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_LITTLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_LITTLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_MIDDLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_MIDDLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_RING_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_RING_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.LEFT_THUMB+", '"+PstFingerPatern.textListFinger[PstFingerPatern.LEFT_THUMB]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_FORE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_FORE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_LITTLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_LITTLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_MIDDLE_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_MIDDLE_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_RING_FINGER+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_RING_FINGER]+"', "
                            + "IF ("+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+""
                            + " = "+PstFingerPatern.RIGHT_THUMB+", '"+PstFingerPatern.textListFinger[PstFingerPatern.RIGHT_THUMB]+"', '')))))))))) "
                            + " LIKE '%"+this.searchTerm+"%' AND "
                       + " "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listFinger")){
	    listData = PstFingerPatern.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listFinger")){
		finger = (FingerPatern) listData.get(i);
                
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmFingerPatern.fieldNames[FrmFingerPatern.FRM_FIELD_FINGER_PATERN_ID]+"' class='"+FrmFingerPatern.fieldNames[FrmFingerPatern.FRM_FIELD_FINGER_PATERN_ID]+"' value='"+finger.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }    
                ja.put(""+(this.start+i+1));
		ja.put(""+PstFingerPatern.textListFinger[finger.getFingerType()]);
                
		String buttonUpdate = "";
		if(privUpdate){
                    buttonUpdate = "<a class='btn btn-warning btn-xs fingerSpotUpdate' data-oid='"+finger.getOID()+"'  href='findspot:findspot protocol;register&"+this.empId+"&"+finger.getFingerType()+"&"+approot+"masterdata/emp_finger_register.jsp' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></a> ";
		    //buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+finger.getOID()+"' data-for='showFingerPaternForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		    //buttonUpdate += "<a href='emp_doc_award.jsp?awardId="+empAward.getOID()+"&dataFor=generate' class='btn btn-info btn-sm fancybox fancybox.iframe' data-toggle='tooltip' data-placement='top' title='Generate Document'><i class='fa fa-refresh'></i></a> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+finger.getOID()+"' data-for='deleteFingerPaternSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
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
    
    public String fingerForm(){
        Vector listFingerPatern = new Vector(1,1);
        String whereClause ="";
        String order ="";
        Hashtable<Integer, Boolean> fingerType = new Hashtable<Integer, Boolean>();
        whereClause = " "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+"="+this.empId+" ";
        order =" "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+" ";
        listFingerPatern = PstFingerPatern.list(0, 0, whereClause, order);
        if (listFingerPatern.size()>0){
            for (int i = 0; i<listFingerPatern.size();i++){
                FingerPatern fingerPatern = (FingerPatern)listFingerPatern.get(i);
                fingerType.put(fingerPatern.getFingerType(), true);
            }
        }
        
        String returnData = ""
                        + "<div class='row'>"
                            + "<div class='col-md-12'>"
                                + "<div class='form-group'>"
                                    + "<label>Finger Type</label>"
                                    + "<select name='"+ FrmFingerPatern.fieldNames[FrmFingerPatern.FRM_FIELD_FINGER_TYPE]+"'  id='fingerId' class='form-control finger' data-for='change_url' data-replacement='#fingerSpot'>"
                                        + "<option value=''>Select Finger...</option>";
                                        for (int i=0;i<10;i++){
                                            Boolean found = false;
                                            try {
                                                if(fingerType.size()>0){
                                                    found = fingerType.containsKey(i);
                                                }
                                            } catch (Exception exc){
                                                
                                            }
                                            String urlRegister = "register&"+this.empId+"&"+i+"&"+approot+"masterdata/emp_finger_register.jsp";
                                            String uRegister64 = urlRegister;
                                            if (found == false){
                                                returnData += "<option value='"+uRegister64+"'>"+PstFingerPatern.textListFinger[i]+"</option>";
                                            }
                                        }
                    returnData += "</div>"
                            + "</div>"
                        + "</div>";
        
        return returnData;
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
