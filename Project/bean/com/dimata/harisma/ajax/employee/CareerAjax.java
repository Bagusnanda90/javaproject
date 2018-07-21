/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.harisma.entity.employee.CareerPath;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.log.ChangeValue;
import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.ContractType;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.GradeLevel;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstContractType;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstGradeLevel;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.PstResignedReason;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.ResignedReason;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.form.employee.CtrlCareerPath;
import com.dimata.harisma.form.employee.FrmCareerPath;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @author Dimata 007
 */
public class CareerAjax extends HttpServlet {

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
    private long mutationId = -1;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String delResign = "";
    
    
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
        this.companyId = FRMQueryString.requestLong(request, "company_id");
        this.divisionId = FRMQueryString.requestLong(request, "division_id");
        this.mutationId = FRMQueryString.requestLong(request, "mutation_id");
        
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
        this.delResign = FRMQueryString.requestString(request, "delete_resign");
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
	if(this.dataFor.equals("career_form")){
	   this.htmlReturn = careerForm();
	} else if (this.dataFor.equals("get_division")){
           this.htmlReturn = divisionSelect(request, this.divisionId);
        } else if (this.dataFor.equals("get_department")){
           this.htmlReturn = departmentSelect(request, this.departmentId);
        } else if (this.dataFor.equals("get_section")){
           this.htmlReturn = sectionSelect(request, this.sectionId);
        } else if (this.dataFor.equals("do_contract_form")){
            this.htmlReturn = contractForm();
        } else if (this.dataFor.equals("do_resign_form")){
            this.htmlReturn = doResignForm();
        } else if (this.dataFor.equals("do_mutation_form")){
            this.htmlReturn = getDoMutationForm();
        } else if (this.dataFor.equals("get_position")){
           this.mutationId = FRMQueryString.requestLong(request, "mutation_id");
           String strPosition = "<div class='form-group'>"
                                + "<label>Position</label>"
                                + "<select class=\"form-control\" name=\"emp_position_id\">"
                                + positionSelect(request, this.mutationId)
                                + "</select>"
                                + "</div>";
           String strLevel = "<div class='form-group'>"
                                + "<label>Level</label>"
                                + "<select class=\"form-control\" name=\"emp_level_id\">"
                                + levelSelect(request, this.mutationId)
                                + "</select>"
                                + "</div>";
           this.htmlReturn = strPosition + strLevel;
        }
    }
    
    public void commandSave(HttpServletRequest request){
        String message = "";
        if (this.dataFor.equals("do_contract_form")){
            String contractToDate = FRMQueryString.requestString(request, FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO]);
            long empCategoryId = FRMQueryString.requestLong(request, "emp_category_id");
            CareerPath careerPath = new CareerPath();
            FrmCareerPath frmCareerPath = new FrmCareerPath(request, careerPath);
            frmCareerPath.requestEntityObject(careerPath);
            try {
                careerPath.setHistoryType(PstCareerPath.DO_CONTRACT);
                PstCareerPath.insertExc(careerPath);
                String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_END_CONTRACT]+"='"+contractToDate+"', ";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId;
                sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+careerPath.getEmployeeId();
                DBHandler.execUpdate(sqlUpdateEmp);
                message = "Data Saved";
                this.htmlReturn = "<i class='fa fa-info'></i> "+message;
            } catch(Exception e){
                System.out.print("Save Career => "+e.toString());
            }
        } else if (this.dataFor.equals("do_resign_form")){
            String resignDate = FRMQueryString.requestString(request, "resign_date");
            long resignReason = FRMQueryString.requestLong(request, "resign_reason");
            String resignDesc = FRMQueryString.requestString(request, "resign_desc");
            CareerPath careerPath = new CareerPath();
            FrmCareerPath frmCareerPath = new FrmCareerPath(request, careerPath);
            frmCareerPath.requestEntityObject(careerPath);
            careerPath.setWorkTo(java.sql.Date.valueOf(resignDate));
            try {
                careerPath.setHistoryType(PstCareerPath.DO_RESIGN);
                PstCareerPath.insertExc(careerPath);
                String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"=1,";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]+"='"+resignDate+"',";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_REASON_ID]+"="+resignReason+",";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DESC]+"='"+resignDesc+"'";
                sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+careerPath.getEmployeeId();
                DBHandler.execUpdate(sqlUpdateEmp);
                message = "Data Saved";
                this.htmlReturn = "<i class='fa fa-info'></i> "+message;
            } catch(Exception e){
                System.out.print("Save Career => "+e.toString());
            }
        } else if (this.dataFor.equals("do_mutation_form")){
            long companyId = FRMQueryString.requestLong(request, "emp_company_id");
            long divisionId = FRMQueryString.requestLong(request, "emp_division_id");
            long departmentId = FRMQueryString.requestLong(request, "emp_department_id");
            long sectionId = FRMQueryString.requestLong(request, "emp_section_id");
            long positionId = FRMQueryString.requestLong(request, "emp_position_id");
            long levelId = FRMQueryString.requestLong(request, "emp_level_id");
            //long empCategoryId = FRMQueryString.requestLong(request, "emp_category_id");
            CareerPath careerPath = new CareerPath();
            FrmCareerPath frmCareerPath = new FrmCareerPath(request, careerPath);
            frmCareerPath.requestEntityObject(careerPath);
            try {
                careerPath.setHistoryType(PstCareerPath.DO_MUTATION);
                PstCareerPath.insertExc(careerPath);
                String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
                sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId+", ";
                sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId+", ";
                sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId+", ";
                sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId+", ";
                sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+positionId+", ";
                sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+"="+levelId+" ";
                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+careerPath.getEmployeeId();
                DBHandler.execUpdate(sqlUpdateEmp);
                message = "Data Saved";
                this.htmlReturn = "<i class='fa fa-info'></i> "+message;
            } catch(Exception e){
                System.out.print("Save Career => "+e.toString());
            }
        } else if (this.dataFor.equals("approveFinger")){
            this.htmlReturn = updateFinger(request);
        }else {
            CtrlCareerPath ctrlCareer = new CtrlCareerPath(request);
            this.iErrCode = ctrlCareer.action(this.iCommand, this.oid, this.oidEmployee, request); //.action(this.iCommand, this.oid, request, colName, oid); ///.action(this.iCommand, this.oid, this.oidDelete);
            message = ctrlCareer.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        }
        
        
    }
    
    public void commandDeleteAll(HttpServletRequest request){        
        String[] splits = this.oidDelete.split(",");
        String message = "";
        for (String asset : splits) {
            if (!"".equals(asset)) {
                long oidDiv = Long.parseLong(asset);
                if (oidDiv != 0) {
                    try {
                        long oid = PstCareerPath.deleteExc(oidDiv);
                        message = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                    } catch (Exception exc) {
                        message = "career_c";
                    }
                }
            }
        }
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){        
        CtrlCareerPath ctrlCareerPath = new CtrlCareerPath(request);
        this.iErrCode = ctrlCareerPath.action(this.iCommand, this.oid, this.oidEmployee, request);
        if (delResign.equals("true")){
            try {
                String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"=0,";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]+"='',";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_REASON_ID]+"='',";
                sqlUpdateEmp += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DESC]+"=''";
                sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+this.oidEmployee;
                DBHandler.execUpdate(sqlUpdateEmp);

            } catch(Exception e){
                System.out.print("Save Career => "+e.toString());
            }
        }
        String message = ctrlCareerPath.getMessage();
        this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("career_list")){
	    String[] cols = {
                PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID],
                PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM],
                PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO],
                PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_FROM],
                PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_TO],
                PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE]
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
	
	if(dataFor.equals("career_list")){
            whereClause = "(COMP."+PstCompany.fieldNames[PstCompany.FLD_COMPANY]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR DV."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR SEC."+PstSection.fieldNames[PstSection.FLD_SECTION]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR LV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR DATE_FORMAT(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]+", '%d %M %Y') LIKE '%"+this.searchTerm+"%'"
                    + " OR DATE_FORMAT(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]+", '%d %M %Y') LIKE '%"+this.searchTerm+"%'"
                    + " OR DATE_FORMAT(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_FROM]+", '%d %M %Y') LIKE '%"+this.searchTerm+"%'"
                    + " OR DATE_FORMAT(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_CONTRACT_TO]+", '%d %M %Y') LIKE '%"+this.searchTerm+"%'" 
                    + " OR IF(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE]+" = "
                        + PstCareerPath.DO_CAREER+ ", '"+PstCareerPath.historyTypeForQT[PstCareerPath.DO_CAREER]+"' , "
                        + "IF(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE]+" = "
                        + PstCareerPath.DO_CONTRACT+ ", '"+PstCareerPath.historyTypeForQT[PstCareerPath.DO_CONTRACT]+"' , "
                        + "IF(WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE]+" = "
                        + PstCareerPath.DO_MUTATION+ ", '"+PstCareerPath.historyTypeForQT[PstCareerPath.DO_MUTATION]+"' , "
                        + "'"+PstCareerPath.historyTypeForQT[PstCareerPath.DO_RESIGN]+"'"
                        + "))) LIKE '%"+this.searchTerm+"%'"
                    + " OR CAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+" LIKE '%"+this.searchTerm+"%')"
                    + "AND WH."+PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+this.oidEmployee;
	    total = PstCareerPath.getCountDataTable(whereClause); 
	}
        
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
        ChangeValue changeValue = new ChangeValue();
        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        String order =""+PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]+" ASC";
        String delResign="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.colOrder>0){
            order = ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("career_list")){
            listData = PstCareerPath.listDataTable(this.start, this.amount, whereClause, order);
	}

        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("career_list")){
                CareerPath career = (CareerPath)listData.get(i);
                
                if (career.getHistoryType() == 3){
                    delResign = "data-resign='true'";
                }
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID]+"' class='"+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID]+"' value='"+career.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }                
                ja.put(""+(i+1));
                ja.put(changeValue.getCompanyName(career.getCompanyId()));
                ja.put(changeValue.getDivisionName(career.getDivisionId()));
                ja.put(changeValue.getDepartmentName(career.getDepartmentId()));
                ja.put(changeValue.getSectionName(career.getSectionId()));
                ja.put(changeValue.getPositionName(career.getPositionId()));
                ja.put(changeValue.getLevelName(career.getLevelId()));
                ja.put(changeValue.getEmpCategory(career.getEmpCategoryId()));
                ja.put(Formater.formatDate(career.getWorkFrom(), "dd MMM yyyy"));
                ja.put(Formater.formatDate(career.getWorkTo(),"dd MMM yyyy"));
                ja.put(Formater.formatDate(career.getContractFrom(), "dd MMM yyyy"));
                ja.put(Formater.formatDate(career.getContractTo(),"dd MMM yyyy"));
                ja.put(PstCareerPath.historyTypeForQT[career.getHistoryType()]);
                String document ="";
                if(!(career.getDocument().equals(""))){
                        document = approot+"/imgdoc/"+  career.getDocument();
                    }
                
                String buttonView = "<a href='"+document+"' target='_blank'> "+career.getDocument()+"</a>";
                
                ja.put(""+buttonView);
                String buttonUpdate = "";
                if(career.getAcknowledgeStatus() == 0){
                    buttonUpdate += "<button class='btn btn-warning btnacknowledge btn-xs' data-oid='"+career.getOID()+"' data-for='showEmpCareerAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledge'><i class='fa fa-thumbs-up'></i></button> ";
                    String action = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+career.getOID()+"' data-for='career_form' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> " +
                                    "<button class='btn btn-warning btnupload btn-xs' data-oid='"+career.getOID()+"' data-for='showEmpCareer' type='button' data-toggle='tooltip' data-placement='top' title='Upload'><i class='fa fa-cloud-upload'></i></button> " + buttonUpdate +
                                    "<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+career.getOID()+"' data-for='deleteCareer' data-empid='"+career.getEmployeeId()+"' "+delResign+" type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>";
                if (privUpdate){
                    ja.put(action);
                }
                
                } else if(career.getAcknowledgeStatus() > 0){
                    buttonUpdate += "<button class='btn btn-success btn-xs' data-oid='"+career.getOID()+"' data-for='showEmpCareerAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledged'><i class='fa fa-check-circle'></i></button> ";
                    String action = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+career.getOID()+"' data-for='career_form' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> " +
                        "<button class='btn btn-success btnupload btn-xs' data-oid='"+career.getOID()+"' data-for='showEmpCareer' type='button' data-toggle='tooltip' data-placement='top' title='Upload'><i class='fa fa-cloud-upload'></i></button> " + buttonUpdate +
                                    "<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+career.getOID()+"' data-for='deleteCareer' data-empid='"+career.getEmployeeId()+"' "+delResign+" type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>";
                if (privUpdate){
                    ja.put(action);
                }
                
                }
                
                array.put(ja);
                
	    }
        }
        
        totalAfterFilter = total;
        
        String where = ""+PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+" = " + this.oidEmployee;
        Vector listCareerAll = PstCareerPath.list(0, 0, where, ""+PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]);
        
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(this.oidEmployee);
        } catch (Exception exc){}
        Date workFrom = new Date();
        Date contractFrom = new Date();
        Date contractTo = new Date();
        int resign = 0;
        if (listCareerAll.size() > 0){
            CareerPath career = (CareerPath)listCareerAll.get(listCareerAll.size()-1);
            Calendar c = Calendar.getInstance(); 
            c.setTime(career.getWorkTo()); 
            c.add(Calendar.DATE, 1);
            workFrom = c.getTime();
            contractFrom = career.getContractFrom();
            contractTo = career.getContractTo();
            if (career.getHistoryType() == 3){
                resign = 1;
            }
        } else {
            workFrom = emp.getCommencingDate();
            contractFrom = emp.getCommencingDate();
            contractTo = emp.getEnd_contract();
        }
        

        if (resign != 1){
            JSONArray ja = new JSONArray();
            if (privUpdate){
                ja.put("");
            }
            ja.put(""+(listData.size()+1));
            ja.put(changeValue.getCompanyName(emp.getCompanyId()));
            ja.put(changeValue.getDivisionName(emp.getDivisionId()));
            ja.put(changeValue.getDepartmentName(emp.getDepartmentId()));
            ja.put(changeValue.getSectionName(emp.getSectionId()));
            ja.put(changeValue.getPositionName(emp.getPositionId()));
            ja.put(changeValue.getLevelName(emp.getLevelId()));
            ja.put(changeValue.getEmpCategory(emp.getEmpCategoryId()));
            ja.put(Formater.formatDate(workFrom, "dd MMM yyyy"));
            ja.put("NOW");
            ja.put(Formater.formatDate(contractFrom, "dd MMM yyyy"));
            ja.put(Formater.formatDate(contractTo,"dd MMM yyyy"));
            ja.put("Career Now");
            ja.put("");
            if (privUpdate){
                ja.put("");
            }
            array.put(ja);
            totalAfterFilter = totalAfterFilter + 1;
        }
        
        try {
            result.put("iTotalRecords", total);
            result.put("iTotalDisplayRecords", totalAfterFilter);
            result.put("aaData", array);
        } catch (Exception e) {

        }
        
        return result;
    }
    
    public String drawInputHidden(String name, String value){
        return drawInputHidden(name, value, "");
    }
    
    public String drawInputHidden(String name, String value, String id){
        String data = "<input type=\"hidden\" name=\""+name+"\" value=\""+value+"\" id=\""+id+"\">";
        return data;
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
    
    public String drawInput(String name, String styleClass, String value){
        return drawInput(name, styleClass, value, "", "");
    }
    
    public String drawInput(String name, String styleClass, String value, String id){
        return drawInput(name, styleClass, value, id, "");
    }
    
    public String drawInput(String name, String styleClass, String value, String id, String att){
        String data = "<input type=\"text\" name=\""+name+"\" class=\"form-control "+styleClass+"\" value=\""+value+"\" id=\""+id+"\" "+att+"/>";
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
        whereClause += " AND "+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+this.companyId;
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
        Vector departList = PstDepartment.listDepartmentVer2(0, 0, this.companyId);
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
        String whereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+this.companyId;
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
                    data += "<option selected=\"selected\" value=\""+position.getOID()+"\">"+position.getPosition()+" - "+this.mutationId+"</option>";
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
                    data += "<option selected=\"selected\" value=\""+level.getOID()+"\">"+level.getLevel()+" - "+this.mutationId+"</option>";
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
            emp = PstEmployee.fetchExc(this.oidEmployee);
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

        
        data += "<option value=\"\">Select Position...</option>";
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
            emp = PstEmployee.fetchExc(this.oidEmployee);
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
        data += "<option value=\"\">Select Category Emoloyee...</option>";
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
    
    public String empCategorySelect2(String categoryId){
        String whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " NOT IN (" + categoryId + ")";
        String data = "";
        Vector empCategoryList = PstEmpCategory.list(0, 0, whereClause, "");
        if (empCategoryList != null && empCategoryList.size()>0){
            for (int i=0; i<empCategoryList.size(); i++){
                EmpCategory empCat = (EmpCategory)empCategoryList.get(i);
                    data += "<option value=\""+empCat.getOID()+"\">"+empCat.getEmpCategory()+"</option>";
            }
        }
        return data;
    }
    
    public String getContractType(){
        String data = "";
        data += "<option value=\"\">Select Contract Type...</option>";
        Vector contractList = PstContractType.list(0, 0, "", "");
        if (contractList != null && contractList.size()>0){
            for (int i=0; i<contractList.size(); i++){
                ContractType contType = (ContractType)contractList.get(i);
                data += "<option value=\""+contType.getOID()+"\">"+contType.getContractName()+"</option>";
            }
        }
        return data;
    }
    
    public String getResignReason(){
        String data = "";
        data += "<option value=\"\">Select Resign Reason...</option>";
        Vector reasonList = PstResignedReason.list(0, 0, "", "");
        if (reasonList != null && reasonList.size()>0){
            for(int i=0; i<reasonList.size(); i++){
                ResignedReason reason = (ResignedReason)reasonList.get(i);
                data += "<option value=\""+reason.getOID()+"\">"+reason.getResignedReason()+"</option>";
            }
        }
        return data;
    }
    
    public String careerForm(){
        CareerPath career = new CareerPath();
	if(this.oid != 0){
	    try{
		career = PstCareerPath.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        this.companyId = career.getCompanyId();
        this.divisionId = career.getDivisionId();
        this.departmentId = career.getDepartmentId();
        this.sectionId = career.getSectionId();
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID], ""+career.getOID())
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMPLOYEE_ID], ""+this.oidEmployee)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_HISTORY_TYPE], ""+PstCareerPath.DO_CAREER)
                + "<div class='form-group'>"
		    + "<label>Company *</label>"
                    + "<select class=\"form-control company_struct\" id=\"company_career\" name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-replacement=\"#division_career\">"
		    + companySelect(career.getCompanyId())
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Division *</label>"
		    + "<select id=\"division_career\" class=\"form-control company_struct\" name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID]+"\" data-for=\"get_department\" data-replacement=\"#department_career\">"
                    + divisionSelect(null, career.getDivisionId())
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Department *</label>"
		    + "<select id=\"department_career\" class=\"form-control company_struct\" name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]+"\" data-for=\"get_section\" data-replacement=\"#section_career\">"
                    + departmentSelect(null, career.getDepartmentId())
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Section *</label>"
		    + "<select id=\"section_career\" class=\"form-control company_struct\" name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID]+"\">"
                    + sectionSelect(null, career.getSectionId())
                    + "</select>"
		+ "</div>"
                + drawFormGroup("Position *", drawSelect(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID], "", positionSelectVer1(null, career.getPositionId()), "", "", "position_career"), "")
                + drawFormGroup("Level *", drawSelect(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID], "", levelSelectVer1(null, career.getLevelId()), "", "", "level_career"), "")
                + drawFormGroup("Emp Category *", drawSelect(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID], "", empCategorySelect(career.getEmpCategoryId()), "", "", "category_career"), "")
                + "<div class='form-group'>"
		    + "<label>Career Type *</label>"
                    + "<select class=\"form-control\" name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_HISTORY_TYPE]+"\" id=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_HISTORY_TYPE]+"\">"
                        + "<option value=\"\">Select Career Type</option>"
                        + "<option value=\""+PstCareerPath.DO_CAREER+"\">"+PstCareerPath.historyTypeForQT[PstCareerPath.DO_CAREER]+"</option>"
                        + "<option value=\""+PstCareerPath.DO_CONTRACT+"\">"+PstCareerPath.historyTypeForQT[PstCareerPath.DO_CONTRACT]+"</option>"
                        + "<option value=\""+PstCareerPath.DO_MUTATION+"\">"+PstCareerPath.historyTypeForQT[PstCareerPath.DO_MUTATION]+"</option>"
                    + "</select>"
                + "</div>"
                + drawFormGroup("Work From *", drawInput(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM_STR], "datepicker", ""+(career.getWorkFrom() != null ? career.getWorkFrom(): ""), "work_from_career", "placeholder='Input Work From...'"), "")
                + drawFormGroup("Work To *", drawInput(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO_STR], "datepicker", ""+(career.getWorkTo() != null ? career.getWorkTo(): ""), "work_to_career", "placeholder='Input Work To...'"), "")
                + drawFormGroup("Contract From *", drawInput(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM], "datepicker", ""+(career.getContractFrom() != null ? career.getContractFrom() : ""), "contract_from_career", "placeholder='Input Contract From...'"), "")
                + drawFormGroup("Contract To *", drawInput(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO], "datepicker", ""+(career.getContractTo() != null ? career.getContractTo() : ""), "contract_to_career", "placeholder='Input Contract To...'"), "")
	    + "</div>"
	+ "</div>";
	return returnData;
    }
    
    public String contractForm(){
        ChangeValue changeValue = new ChangeValue();
        Employee employee = new Employee();
        if (this.oidEmployee != 0){
            try {
                employee = PstEmployee.fetchExc(this.oidEmployee);
            } catch(Exception e){
                System.out.println("fecth employee: "+e.toString());
            }
        }
        String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+this.oidEmployee;
        String order = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]+" DESC";
        Vector careerList = PstCareerPath.list(0, 0, whereClause, order);
        String workFrom = null;
        String workTo = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (careerList != null && careerList.size()>0){
            CareerPath career = (CareerPath)careerList.get(0);
            workFrom = sdf.format(career.getWorkFrom());
            workTo = sdf.format(career.getWorkTo());
        } else {
            workFrom = sdf.format(employee.getCommencingDate());
            Date date = new Date();
            workTo = sdf.format(date);
        }
        
        String inCategoryId = "";
        for (int i = 0; i < careerList.size(); i++){
            CareerPath career = (CareerPath) careerList.get(i);
            inCategoryId = inCategoryId + "," + career.getEmpCategoryId();
        }
        inCategoryId = inCategoryId + "," + employee.getEmpCategoryId();
        inCategoryId = inCategoryId.substring(1);
        
        String strData = ""
        + "<div class='row'>"
            + "<div class='col-md-12'>"
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID], "0")
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMPLOYEE_ID], ""+this.oidEmployee)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM_STR], workFrom)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO_STR], workTo)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID], ""+employee.getEmpCategoryId())
                + drawFormGroup("Company", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_COMPANY_ID], ""+employee.getCompanyId()), changeValue.getCompanyName(employee.getCompanyId()))
                + drawFormGroup("Division", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID], ""+employee.getDivisionId()), changeValue.getDivisionName(employee.getDivisionId()))
                + drawFormGroup("Department", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID], ""+employee.getDepartmentId()), changeValue.getDepartmentName(employee.getDepartmentId()))
                + drawFormGroup("Section", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID], ""+employee.getSectionId()), changeValue.getSectionName(employee.getSectionId()))
                + drawFormGroup("Position", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID], ""+employee.getPositionId()), changeValue.getPositionName(employee.getPositionId()))
                + drawFormGroup("Level", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID], ""+employee.getLevelId()), changeValue.getLevelName(employee.getLevelId()))
                + drawFormGroup("Employee Cateogry *", drawSelect("emp_category_id", "", empCategorySelect2(inCategoryId), "", "", "emp_category_id"), "")
                + drawFormGroup("Contract From *", drawInput( FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM], "datepicker", "", FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM],"placeholder='Contract From...'" ), "")
                + drawFormGroup("Contract To *", drawInput( FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO], "datepicker", "", FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO],"placeholder='Contract To...'"), "" )
            + "</div>"
        + "</div>";
        return strData;
    }
    
    public String doResignForm(){
        ChangeValue changeValue = new ChangeValue();
        Employee employee = new Employee();
        if (this.oidEmployee != 0){
            try {
                employee = PstEmployee.fetchExc(this.oidEmployee);
            } catch(Exception e){
                System.out.println("fecth employee: "+e.toString());
            }
        }
        String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+this.oidEmployee;
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
            contractFrom = sdf.format(career.getContractFrom());
            contractTo = sdf.format(career.getContractTo());
            contractType = career.getContractType();
        } else {
            c.setTime(employee.getCommencingDate()); 
            c.add(Calendar.DATE, 1);
            workFrom = sdf.format(c.getTime());
            contractFrom = sdf.format(employee.getCommencingDate());
            contractTo = sdf.format(employee.getEnd_contract());
        }
        String strData = ""
        + "<div class='row'>"
            + "<div class='col-md-12'>"
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID], "0")
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMPLOYEE_ID], ""+this.oidEmployee)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM_STR], workFrom)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO_STR], "", "FRM_FIELD_WORK_TO_STR")
                + drawFormGroup("Company", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_COMPANY_ID], ""+employee.getCompanyId()), changeValue.getCompanyName(employee.getCompanyId()))
                + drawFormGroup("Division", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID], ""+employee.getDivisionId()), changeValue.getDivisionName(employee.getDivisionId()))
                + drawFormGroup("Department", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID], ""+employee.getDepartmentId()), changeValue.getDepartmentName(employee.getDepartmentId()))
                + drawFormGroup("Section", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID], ""+employee.getSectionId()), changeValue.getSectionName(employee.getSectionId()))
                + drawFormGroup("Position", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID], ""+employee.getPositionId()), changeValue.getPositionName(employee.getPositionId()))
                + drawFormGroup("Level", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID], ""+employee.getLevelId()), changeValue.getLevelName(employee.getLevelId()))
                + drawFormGroup("Employee Cateogry", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID], ""+employee.getEmpCategoryId()), "")
                + drawFormGroup("Contract From", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM], contractFrom), contractFrom)
                + drawFormGroup("Contract To", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO], contractTo), contractTo)
                + drawFormGroup("Resign Date *", drawInput("resign_date", "datepicker", "", "resign_date","placeholder='Resign Dtae...'"), "")
                + drawFormGroup("Resign Reason", drawSelect("resign_reason", "", getResignReason(), "placeholder='Contract From...'", ""), "")
                + drawFormGroup("Resign Description", "<textarea name='resign_desc' placeholder='Input Description...' class='form-control'>"+employee.getResignedDesc()+"</textarea>", "")
            + "</div>"
        + "</div>";
        return strData;
    }
    
    public String getDoMutationForm(){
        ChangeValue changeValue = new ChangeValue();
        Employee employee = new Employee();
        if (this.oidEmployee != 0){
            try {
                employee = PstEmployee.fetchExc(this.oidEmployee);
            } catch(Exception e){
                System.out.println("fecth employee: "+e.toString());
            }
        }
        String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+this.oidEmployee;
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
            contractFrom = sdf.format(career.getContractFrom() != null ? career.getContractFrom() : employee.getCommencingDate());
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
        String strData = ""
        + "<div class='row'>"
            + "<div class='col-md-6'>"
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID], "0")
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMPLOYEE_ID], ""+this.oidEmployee)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM_STR], workFrom)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO_STR], workTo)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM], contractFrom)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO], contractTo)
                + drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID], ""+employee.getEmpCategoryId())
                + drawFormGroup("Company", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_COMPANY_ID], ""+employee.getCompanyId()), changeValue.getCompanyName(employee.getCompanyId()))
                + drawFormGroup("Division", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID], ""+employee.getDivisionId()), changeValue.getDivisionName(employee.getDivisionId()))
                + drawFormGroup("Department", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID], ""+employee.getDepartmentId()), changeValue.getDepartmentName(employee.getDepartmentId()))
                + drawFormGroup("Section", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID], ""+employee.getSectionId()), changeValue.getSectionName(employee.getSectionId()))
                + drawFormGroup("Position", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID], ""+employee.getPositionId()), changeValue.getPositionName(employee.getPositionId()))
                + drawFormGroup("Level", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID], ""+employee.getLevelId()), changeValue.getLevelName(employee.getLevelId()))
                + drawFormGroup("Employee Cateogry", drawInputHidden(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID], ""+employee.getEmpCategoryId()), changeValue.getEmpCategory(employee.getEmpCategoryId()))
            + "</div>"
            + "<div class='col-md-6'>"
                + "<div class='form-group'>"
		    + "<label>Mutation Type *</label>"
                    + "<select class=\"form-control company_struct\" name=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_MUTATION_TYPE]+"\" id=\""+FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_MUTATION_TYPE]+"\" data-for=\"get_position\" data-replacement=\"#position_level\">"
                        + "<option value=\"\">Select Mutation Type...</option>"
                        + "<option value=\""+PstCareerPath.PROMOTION_MUTATION+"\">"+PstCareerPath.mutationTypeValue[PstCareerPath.PROMOTION_MUTATION]+"</option>"
                        + "<option value=\""+PstCareerPath.ROTATION_MUTATION+"\">"+PstCareerPath.mutationTypeValue[PstCareerPath.ROTATION_MUTATION]+"</option>"
                        + "<option value=\""+PstCareerPath.DEMOTION_MUTATION+"\">"+PstCareerPath.mutationTypeValue[PstCareerPath.DEMOTION_MUTATION]+"</option>"
                    + "</select>"
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Company *</label>"
                    + "<select class=\"form-control company_struct\" name=\"emp_company_id\" id=\"company_mutation\" data-for=\"get_division\" data-replacement=\"#division_mutation\">"
		    + companySelect(0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Division *</label>"
		    + "<select id=\"division_mutation\" class=\"form-control company_struct\" name=\"emp_division_id\" data-for=\"get_department\" data-replacement=\"#department_mutation\">"
                    + divisionSelect(null, 0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Department *</label>"
		    + "<select id=\"department_mutation\" class=\"form-control company_struct\" name=\"emp_department_id\" data-for=\"get_section\" data-replacement=\"#section_mutation\">"
                    + departmentSelect(null, 0)
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Section *</label>"
		    + "<select id=\"section_mutation\" class=\"form-control company_struct\" name=\"emp_section_id\">"
                    + sectionSelect(null, 0)
                    + "</select>"
		+ "</div>"
                + "<div id=\"position_level\">"
                    + "<div class='form-group'>"
                        + "<label>Position *</label>"
                        + "<select class=\"form-control\" name=\"emp_position_id\" id=\"position_mutation\">"
                        + positionSelect(null, employee.getPositionId())
                        + "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Level *</label>"
                        + "<select class=\"form-control\" name=\"emp_level_id\" id=\"level_mutation\">"
                        + levelSelect(null, employee.getLevelId())
                        + "</select>"
                    + "</div>"
		+ "</div>"
            + "</div>"
        + "</div>";
        return strData;
    }
    
    private String updateFinger(HttpServletRequest request){
        String htmlReturn="";
        String spvName ="";
        long oidDetail = 0;
        
        CareerPath careerPath = new CareerPath();
        
        oidDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OID_DETAIL");
        
        try {
            careerPath = PstCareerPath.fetchExc(oidDetail);
        } catch (Exception e) {
        }
        
        String sqlUpdate = "UPDATE "+PstCareerPath.TBL_HR_WORK_HISTORY_NOW+" SET ";
        sqlUpdate += PstCareerPath.fieldNames[PstCareerPath.FLD_ACKNOWLEDGE_STATUS]+"=1";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdate += " WHERE "+PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]+"="+oidDetail;
        try {
            DBHandler.execUpdate(sqlUpdate);
        } catch (Exception exc){}
        
        String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
        sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_FINGER_CHECK]+"=0";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+careerPath.getEmployeeId();
        try {
            DBHandler.execUpdate(sqlUpdateEmp);
        } catch (Exception exc){}
        
        return htmlReturn;
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
