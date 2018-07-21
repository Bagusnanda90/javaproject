/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.harisma.entity.employee.PstTrainingHistory;
import com.dimata.harisma.entity.employee.TrainingHistory;
import com.dimata.harisma.entity.masterdata.PstEmpDoc;
import com.dimata.harisma.entity.masterdata.PstTraining;
import com.dimata.harisma.entity.masterdata.Training;
import com.dimata.harisma.form.employee.CtrlTrainingHistory;
import com.dimata.harisma.form.employee.FrmTrainingHistory;
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
public class AjaxTrainingHistory extends HttpServlet {

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
	if(this.dataFor.equals("showtraininghistoryform")){
	  this.htmlReturn = trainHistoryForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
	this.iErrCode = ctrlTrainingHistory.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlTrainingHistory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
	this.iErrCode = ctrlTrainingHistory.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlTrainingHistory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
	this.iErrCode = ctrlTrainingHistory.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlTrainingHistory.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listtraining")){
	    String[] cols = { PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID],
		PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_PROGRAM],
                PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE], 
                PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER],
                PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE],
		PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE],
                PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION],
                PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_REMARK]};

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
        
        if(dataFor.equals("listtraining")){
	    whereClause += " ("+PstTraining.fieldNames[PstTraining.FLD_NAME]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_REMARK]+" LIKE '%"+this.searchTerm+"%')" 
                       + " AND (DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_STATUS]+" = 2 OR EMP."
                       + ""+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMP_DOC_ID]+" = 0)"
                       + " AND EMP." +PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listtraining")){
	    total = PstTrainingHistory.getCountDataTable(whereClause);
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
        TrainingHistory trainHist = new TrainingHistory();
        Training training = new Training();
        String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listtraining")){
               whereClause += " ("+PstTraining.fieldNames[PstTraining.FLD_NAME]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_REMARK]+" LIKE '%"+this.searchTerm+"%')" 
                       + " AND (DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_STATUS]+" = 2 OR EMP."
                       + ""+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMP_DOC_ID]+" = 0)"
                       + " AND EMP." +PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listtraining")){
	    listData = PstTrainingHistory.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listtraining")){
		trainHist = (TrainingHistory) listData.get(i);
                double duration = trainHist.getDuration() / 60;
                try {
                    training = PstTraining.fetchExc(trainHist.getTrainingId());
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_HISTORY_ID]+"' class='"+FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_HISTORY_ID]+"' value='"+trainHist.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }
		ja.put(""+(this.start+i+1));
		ja.put(""+training.getName() );
                ja.put(""+trainHist.getTrainingTitle()!=null && trainHist.getTrainingTitle().length()>0 ? trainHist.getTrainingTitle() : "-");
		ja.put(""+trainHist.getTrainer() != null && trainHist.getTrainer().length()>0 ? trainHist.getTrainer() : "-");
                ja.put(""+trainHist.getStartDate());
                ja.put(""+trainHist.getEndDate());
                ja.put(""+""+ duration+ " Jam");
                ja.put(""+trainHist.getRemark());
                		
		String buttonUpdate = "";
		if(privUpdate){
		    buttonUpdate = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+trainHist.getOID()+"' data-for='showtraininghistoryform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+trainHist.getOID()+"' data-for='deleteTrainHistSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
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
        train_val.add("Select Training Program...");
        
        Vector listTraining = PstTraining.listAll();
        for (int i = 0; i < listTraining.size(); i++){
            Training train = (Training) listTraining.get(i);
            train_key.add(""+train.getOID());
            train_val.add(""+train.getName());
        }
        
        String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control' value='"+this.empId+"'>"
                + "<div class='form-group'>"
		    + "<label>Training Title *</label>"
		    + "<input type='text' placeholder='Input Training Tile...' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_TITLE]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_TITLE]+"' class='form-control' value='"+trainHist.getTrainingTitle()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Training Program *</label>"
		    + ""+ControlCombo.draw(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_ID], null, ""+trainHist.getTrainingId()+"", train_key, train_val, "id="+FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_ID], "form-control")+" "
		+ "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Trainer</label>"
                    + "<input type='text' placeholder='Input Trainer Name...' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINER]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINER]+"' class='form-control' value='"+trainHist.getTrainer()+"'>"
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
                + "<div class='form-group'>"
		    + "<label>Duration (Minute)</label>"
		    + "<input type='text' placeholder='Input Duration...' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_DURATION]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_DURATION]+"' class='form-control' value='"+(trainHist.getDuration()==0 ? "" :trainHist.getDuration())+"'>"
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Remark</label>"
		    + "<textarea name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_REMARK]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_REMARK]+"' placeholder='Input Type Remark...' class='form-control'>"+trainHist.getRemark()+"</textarea>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Point</label>"
		    + "<input type='text' placeholder='Input Point...' name='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_POINT]+"'  id='"+ FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_POINT]+"' class='form-control' value='"+(trainHist.getPoint()==0 ? "":trainHist.getPoint())+"'>"
		+ "</div>" 
                         
//                + "<div class='form-group'>"
//		    + "<label>Valis Status</label>"
//		    + ""+ControlCombo.draw(FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_STATUS], "-- Select --", ""+division.getValidStatus()+"", valid_key, valid_val, "", "form-control")+" "
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Validity</label>"
//		    + "<div class='input-group'>"
//			+ "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_START]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_START]+"' class='form-control datepicker' value='"+(division.getValidStart()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(division.getValidStart(),"yyyy-MM-dd"))+"'>"
//			+ "<div class='input-group-addon'>"
//			    + "To"
//			+ "</div>"
//			+ "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_END]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_END]+"' class='form-control datepicker' value='"+(division.getValidEnd()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(division.getValidEnd(),"yyyy-MM-dd"))+"'>"
//		    + "</div>"
//                + "</div>"
//                + "<a> note: fill some of field below, if you choose Branch of Company </a>"
//                + "<div class='form-group'>"
//		    + "<label>Address</label>"
//		    + "<textarea rows='5' class='form-control' id='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS] +"' name='" + FrmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS] +"'>"+ division.getAddress()+"</textarea> "
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>City</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]+"' class='form-control' value='"+division.getCity()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>NPWP</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]+"' class='form-control' value='"+division.getNpwp()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Province</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]+"' class='form-control' value='"+division.getProvince()+"'>"
//		+ "</div>"    
//                + "<div class='form-group'>"
//		    + "<label>Region</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]+"' class='form-control' value='"+division.getRegion()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Sub Region</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]+"' class='form-control' value='"+division.getSubRegion()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Village</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]+"' class='form-control' value='"+division.getVillage()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Area</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]+"' class='form-control' value='"+division.getArea()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Telephone</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]+"' class='form-control' value='"+division.getTelphone()+"'>"
//		+ "</div>"
//                + "<div class='form-group'>"
//		    + "<label>Fax Number</label>"
//		    + "<input type='text' name='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]+"'  id='"+ FrmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]+"' class='form-control' value='"+division.getFaxNumber()+"'>"
//		+ "</div>"
                
	    + "</div>"
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
    