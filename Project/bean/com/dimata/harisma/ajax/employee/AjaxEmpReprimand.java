/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.employee.EmpReprimand;
import com.dimata.harisma.entity.employee.EmpWarning;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmpReprimand;
import com.dimata.harisma.entity.employee.PstEmpWarning;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.EmpDoc;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstEmpDoc;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstMappingPosition;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstReprimand;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.PstWarning;
import com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat;
import com.dimata.harisma.entity.masterdata.PstWarningReprimandBab;
import com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal;
import com.dimata.harisma.entity.masterdata.Reprimand;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.masterdata.Warning;
import com.dimata.harisma.entity.masterdata.WarningReprimandAyat;
import com.dimata.harisma.entity.masterdata.WarningReprimandBab;
import com.dimata.harisma.entity.masterdata.WarningReprimandPasal;
import com.dimata.harisma.form.employee.CtrlEmpReprimand;
import com.dimata.harisma.form.employee.CtrlEmpWarning;
import com.dimata.harisma.form.employee.FrmEmpAward;
import com.dimata.harisma.form.employee.FrmEmpReprimand;
import com.dimata.harisma.form.employee.FrmEmpReprimand;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
public class AjaxEmpReprimand extends HttpServlet {

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
    private long userId = 0;
    private long empId = 0;
    private long companyId = 0;
    private long datachange = 0;
    
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
        this.oidEmployee = FRMQueryString.requestLong(request, "empId");
        this.companyId = FRMQueryString.requestLong(request, "company_id");
        this.datachange = FRMQueryString.requestLong(request, "datachange");
	
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
	if(this.dataFor.equals("showEmpReprimandForm")){
	   this.htmlReturn = empWarningForm();
	} else if (this.dataFor.equals("get_division")){
           this.htmlReturn = divisionSelect(request, 0);
        } else if (this.dataFor.equals("get_department")){
           this.htmlReturn = departmentSelect(request, 0);
        } else if (this.dataFor.equals("get_section")){
           this.htmlReturn = sectionSelect(request, 0);
	} else if (this.dataFor.equals("get_article")){
            this.htmlReturn = articleSelect(request, 0);
        } else if (this.dataFor.equals("get_verse")){
            this.htmlReturn = verseSelect(request, 0);
        }
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmpReprimand ctrlEmpReprimand = new CtrlEmpReprimand(request);
	this.iErrCode = ctrlEmpReprimand.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlEmpReprimand.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        if (this.dataFor.equals("approveFinger")){
            this.htmlReturn = updateFinger(request);
        }
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpReprimand ctrlEmpReprimand = new CtrlEmpReprimand(request);
	this.iErrCode = ctrlEmpReprimand.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlEmpReprimand.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlEmpReprimand ctrlEmpReprimand = new CtrlEmpReprimand(request);
	this.iErrCode = ctrlEmpReprimand.action(this.iCommand, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlEmpReprimand.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listEmpReprimand")){
	    String[] cols = { PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID],
                PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_COMPANY_ID],
                PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID],
		PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DEPARTMENT_ID],
		PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID],
		PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE],
                PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_CHAPTER],
		PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_ARTICLE],
		PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_PAGE],
		PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DESCRIPTION],
                PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_VALIDITY],
                PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_LEVEL_ID],
                PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_LEVEL_ID],
                PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DOCUMENT]};

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
        
        if(dataFor.equals("listEmpReprimand")){
	    whereClause += " ("+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR DATE_FORMAT("+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE]+", '%d-%M-%Y') LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_PAGE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR DATE_FORMAT("+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_VALIDITY]+", '%d-%M-%Y') LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_POINT]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DOCUMENT]+" LIKE '%"+this.searchTerm+"%')"
                       + " AND (DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_STATUS]+" = 2 OR EMP."
                       + ""+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+" = 0)"
                       + " AND EMP." +PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]+" LIKE '%"+this.oidEmployee+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listEmpReprimand")){
	    total = PstEmpReprimand.getCountDataTable(whereClause);
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
        EmpReprimand empReprimand = new EmpReprimand();
        Company comp = new Company();
        Division div = new Division();
        Department dep = new Department();
        Section sec = new Section();
        Position pos = new Position();
        Level level = new Level();
        EmpCategory cat = new EmpCategory();
        WarningReprimandBab bab = new WarningReprimandBab();
        WarningReprimandPasal pasal = new WarningReprimandPasal();
        Reprimand reprimand = new Reprimand();
        EmpDoc empDoc = new EmpDoc();
        
        String compString = "-";
        String divString = "-";
        String depString = "-";
        String secString = "-";
        String posString = "-";
        String levelString = "-";
        String catString = "-";
        String breakDate = "-";
        
        
	String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]+" LIKE '%"+this.oidEmployee+"%'";                  
        }else{
	     if(dataFor.equals("listEmpReprimand")){
               whereClause += " ("+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR DATE_FORMAT("+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE]+", '%d-%M-%Y') LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_PAGE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR EMP."+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR DATE_FORMAT("+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_VALIDITY]+", '%d-%M-%Y') LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_POINT]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DOCUMENT]+" LIKE '%"+this.searchTerm+"%')"
                       + " AND (DOC."+PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_STATUS]+" = 2 OR EMP."
                       + ""+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+" = 0)"
                       + " AND EMP." +PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]+" LIKE '%"+this.oidEmployee+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listEmpReprimand")){
	    listData = PstEmpReprimand.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listEmpReprimand")){
		empReprimand = (EmpReprimand) listData.get(i);
                try {
                    comp = PstCompany.fetchExc(empReprimand.getCompanyId());
                    compString = comp.getCompany();
                    div = PstDivision.fetchExc(empReprimand.getDivisionId());
                    divString = div.getDivision();
                    dep = PstDepartment.fetchExc(empReprimand.getDepartmentId());
                    depString = dep.getDepartment();
                    pos = PstPosition.fetchExc(empReprimand.getPositionId()); 
                    posString = pos.getPosition();
                    pasal = PstWarningReprimandPasal.fetchExc(Long.valueOf(empReprimand.getArticle()));
                    bab = PstWarningReprimandBab.fetchExc(Long.valueOf(empReprimand.getChapter()));
                    reprimand = PstReprimand.fetchExc(empReprimand.getReprimandLevelId());
                    empDoc = PstEmpDoc.fetchExc(empReprimand.getEmpDocId());
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]+"' class='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]+"' value='"+empReprimand.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }
		ja.put(""+(this.start+i+1));
                //ja.put(compString);
		ja.put(divString);
                //ja.put(depString);
                ja.put(posString);
                ja.put(""+Formater.formatDate(empReprimand.getReprimandDate(), "d-MMM-yyyy"));
                ja.put(""+bab.getBabTitle());
                ja.put(""+pasal.getPasalTitle());
                ja.put(""+empReprimand.getPage());
                ja.put((empReprimand.getDescription().length() > 100) ? empReprimand.getDescription().substring(0, 100) + " ..." : empReprimand.getDescription());
                ja.put(""+Formater.formatDate(empReprimand.getValidityDate(), "d-MMM-yyyy"));
                //ja.put(""+String.valueOf(reprimand.getReprimandDesc()));
                ja.put(""+String.valueOf(reprimand.getReprimandPoint()));
                String document = "";	
                if(!(empReprimand.getDocument().equals(""))){
                        document = approot+"imgdoc/"+  empReprimand.getDocument();
                    }
		String buttonView = "<a href='"+document+"' class='fancybox' target='_blank'> "+empReprimand.getDocument()+"</a>";
                String buttonTemplate= "<button class='btn btn-success btneditormodal btn-xs' data-oid='"+empReprimand.getEmpDocId()+"' data-template1='"+empDoc.getDoc_master_id()+"' data-for='editor' type='button' data-toggle='tooltip' data-placement='top' title='Editor'><i class='fa fa-file'></i></button> ";
                ja.put(""+buttonView);
                
		String buttonUpdate = "";
		if(privUpdate){
                    
                    if(empReprimand.getAcknowledgeStatus() == 0){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+empReprimand.getOID()+"' data-for='showEmpReprimandForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                        buttonUpdate +="<button class='btn btn-warning btnupload btn-xs' data-oid='"+empReprimand.getOID()+"' data-for='showEmpReprimandForm' type='button' data-toggle='tooltip' data-placement='top' title='Upload'><i class='fa fa-cloud-upload'></i></button> ";
                        buttonUpdate += "<button class='btn btn-warning btnacknowledge btn-xs' data-oid='"+empReprimand.getOID()+"' data-for='showEmpRepAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledge'><i class='fa fa-thumbs-up'></i></button> ";
                    
                    
                    } else if(empReprimand.getAcknowledgeStatus() > 0){
                        buttonUpdate = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+empReprimand.getOID()+"' data-for='showEmpReprimandForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                        buttonUpdate +="<button class='btn btn-success btnupload btn-xs' data-oid='"+empReprimand.getOID()+"' data-for='showEmpReprimandForm' type='button' data-toggle='tooltip' data-placement='top' title='Upload'><i class='fa fa-cloud-upload'></i></button> ";
                        buttonUpdate += "<button class='btn btn-success btn-xs' data-oid='"+empReprimand.getOID()+"' data-for='showEmpRepAcknowledge' type='button' data-toggle='tooltip' data-placement='top' title='Acknowledged'><i class='fa fa-check-circle'></i></button> ";
                    }
                    ja.put(buttonUpdate+buttonTemplate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empReprimand.getOID()+"' data-for='deleteEmpRepSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button>");
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
    

    
    public String companySelect(long companyId){
        String data = "";
        Employee emp = new Employee();
           try {
               emp = PstEmployee.fetchExc(this.oidEmployee);
           } catch (Exception exc) {}
        Vector companyList = PstCompany.list(0, 0, "", "");
        data = "<select class=\"form-control datachange\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_COMPANY_ID]+"\" id=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_COMPANY_ID]+"\" data-for=\"get_division\" data-target=\"#division\">";
        data += "<option value=\"\">Select Company...</option>";
        if (companyList != null && companyList.size()>0){
            for (int i=0; i<companyList.size(); i++){
                Company company = (Company)companyList.get(i);
                if (emp.getCompanyId() == company.getOID()) {
                    data += "<option selected=\"selected\" value=\""+company.getOID()+"\">"+company.getCompany()+"</option>";
                }
                else if (companyId == company.getOID()){
                    data += "<option selected=\"selected\" value=\""+company.getOID()+"\">"+company.getCompany()+"</option>";
                } else {
                    data += "<option value=\""+company.getOID()+"\">"+company.getCompany()+"</option>";
                }
            }
        }
        data += "</select>";
        return data;
    }
    public String divisionSelect(HttpServletRequest request, long divisionId){
        String data = "";
        Employee emp = new Employee();
           try {
               emp = PstEmployee.fetchExc(this.oidEmployee);
           } catch (Exception exc) {}
        data += "<option value=\"\">Select Division...</option>";
        long oidComp = 0;
        if (this.datachange > 0){
            oidComp = this.datachange;
        } else {
            oidComp = emp.getCompanyId();
        }
        String whereClause = PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS]+"="+PstDivision.VALID_ACTIVE;
        whereClause += " AND "+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+oidComp;
        Vector divisionList = PstDivision.list(0, 0, whereClause, "");
        if (divisionList != null && divisionList.size()>0){
           for (int i=0; i<divisionList.size(); i++){
                Division divisi = (Division)divisionList.get(i);
                if (emp.getDivisionId() == divisi.getOID()){
                    data += "<option selected=\"selected\" value=\""+divisi.getOID()+"\">"+divisi.getDivision()+"</option>";
                }
                else if (divisionId == divisi.getOID()){
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
        if (this.companyId == 0){
            this.companyId = departmentId;
        }
        Employee emp = new Employee();
           try {
               emp = PstEmployee.fetchExc(this.oidEmployee);
           } catch (Exception exc) {}
        data += "<option value=\"\">Select Department...</option>";
        long oidDiv = 0;
        if (this.datachange > 0){
            oidDiv = this.datachange;
        } else {
            oidDiv = emp.getDivisionId();
        }
        Vector departList = PstDepartment.listDepartmentVer2(0, 0, oidDiv);
        if (departList != null && departList.size()>0){
            for(int i=0; i<departList.size(); i++){
                Department depart = (Department)departList.get(i);
                if (emp.getDepartmentId() == depart.getOID()){
                    data += "<option selected=\"selected\" value=\""+depart.getOID()+"\">"+depart.getDepartment()+"</option>";
                }
                else if (departmentId == depart.getOID()){
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
        if (this.companyId == 0){
            this.companyId = sectionId;
        }
        Employee emp = new Employee();
           try {
               emp = PstEmployee.fetchExc(this.oidEmployee);
           } catch (Exception exc) {}
           
        long oidDep = 0;
        if (this.datachange > 0){
            oidDep = this.datachange;
        } else {
            oidDep = emp.getDepartmentId();
        }   
        String whereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDep;
        data += "<option value=\"\">Select Department...</option>";
        Vector sectionList = PstSection.list(0, 0, whereClause, "");
        if (sectionList != null && sectionList.size()>0){
            for (int i=0; i<sectionList.size(); i++){
                Section section = (Section)sectionList.get(i);
                if (emp.getSectionId() == section.getOID()){
                    data += "<option selected=\"selected\" value=\""+section.getOID()+"\">"+section.getSection()+"</option>";
                }
                else if (sectionId == section.getOID()){
                    data += "<option selected=\"selected\" value=\""+section.getOID()+"\">"+section.getSection()+"</option>";
                } else {
                    data += "<option value=\""+section.getOID()+"\">"+section.getSection()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String positionSelect(long positionId){
        String data = "";
        Employee emp = new Employee();
           try {
               emp = PstEmployee.fetchExc(this.oidEmployee);
           } catch (Exception exc) {}
        data += "<option value=\"\">Select Position...</option>";
        Vector positionList = PstPosition.list(0, 0, "", "");
        if (positionList != null && positionList.size()>0){
            for (int i=0; i<positionList.size(); i++){
                Position position = (Position)positionList.get(i);
                if (emp.getPositionId() == position.getOID()){
                    data += "<option selected=\"selected\" value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
                }
                else if (positionId == position.getOID()){
                    data += "<option selected=\"selected\" value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
                } else {
                    data += "<option value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
                }
                
            }
        }
        return data;
    }
    
    public String levelSelect(long levelId){
        String data = "";
        Employee emp = new Employee();
           try {
               emp = PstEmployee.fetchExc(this.oidEmployee);
           } catch (Exception exc) {}
        data += "<option value=\"\">Select Level...</option>";
        Vector levelList = PstLevel.list(0, 0, "", "");
        if (levelList != null && levelList.size()>0){
            for (int i=0; i<levelList.size(); i++){
                Level level = (Level)levelList.get(i);
                if (emp.getLevelId() == level.getOID()){
                    data += "<option selected=\"selected\" value=\""+level.getOID()+"\">"+level.getLevel()+"</option>";
                }
                else if (levelId == level.getOID()){
                    data += "<option selected=\"selected\" value=\""+level.getOID()+"\">"+level.getLevel()+"</option>";
                } else {
                    data += "<option value=\""+level.getOID()+"\">"+level.getLevel()+"</option>";
                }
            }
        }
        return data;
    }
    
    public String empCategorySelect(long empCatId){
        String data = "";
        Employee emp = new Employee();
           try {
               emp = PstEmployee.fetchExc(this.oidEmployee);
           } catch (Exception exc) {}
        data += "<option value=\"\">Select Category...</option>";
        Vector empCategoryList = PstEmpCategory.list(0, 0, "", "");
        if (empCategoryList != null && empCategoryList.size()>0){
            for (int i=0; i<empCategoryList.size(); i++){
                EmpCategory empCat = (EmpCategory)empCategoryList.get(i);
                if (emp.getEmpCategoryId() == empCat.getOID()){
                    data += "<option selected=\"selected\" value=\""+empCat.getOID()+"\">"+empCat.getEmpCategory()+"</option>";
                }
                else if (empCatId == empCat.getOID()){
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
        data += "<option value=\"\">Select Chapter...</option>";
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
    
    
    public String empWarningForm(){
	
	//CHECK DATA
	EmpReprimand empReprimand = new EmpReprimand();
        
	if(this.oid != 0){
	    try{
		empReprimand = PstEmpReprimand.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        String check = "checked";
        
        
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
       
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-6'>"
                + "<input type='hidden' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]+"'  id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]+"' class='form-control' value='"+empReprimand.getOID()+"'>"
                + "<input type='hidden' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMPLOYEE_ID]+"'  id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control' value='"+this.oidEmployee+"'>"
                + "<input type='hidden' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DOCUMENT]+"'  id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DOCUMENT]+"' class='form-control' value='"+empReprimand.getDocument()+"'>"
                + "<div class='form-group'>"
		    + "<label>Company *</label>"
                    + companySelect(empReprimand.getCompanyId())
		+ "</div>"    
                + "<div class='form-group'>"
		    + "<label>Division *</label>"
                    + "<select id=\"division\" class=\"form-control datachange\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DIVISION_ID]+"\" data-for=\"get_department\" data-target=\"#department\">"
                    + divisionSelect(null, empReprimand.getDivisionId())
                    + "</select>"
		+ "</div>"   
                + "<div class='form-group'>"
		    + "<label>Department *</label>"
                    + "<select id=\"department\" class=\"form-control datachange\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DEPARTMENT_ID]+"\" data-for=\"get_section\" data-target=\"#section\">"
                    + departmentSelect(null, empReprimand.getDepartmentId())
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Section *</label>"
                     + "<select id=\"section\" class=\"form-control company_struct\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_SECTION_ID]+"\">"
                    + sectionSelect(null, empReprimand.getSectionId())
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Position *</label>"
                    + "<select id=\"position\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_POSITION_ID]+"\">"
                    + positionSelect(empReprimand.getPositionId())
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Level</label>"
		    + "<select id=\"level\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_LEVEL_ID]+"\">"
                    + levelSelect(empReprimand.getLevelId())
                    + "</select>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Emp Category</label>"
		    + "<select id=\"emp_category\" class=\"form-control\" name=\""+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMP_CATEGORY_ID]+"\">"
                    + empCategorySelect(empReprimand.getEmpCategoryId())
                    + "</select>"
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
                    + "<input type='text' placeholder='Input Page...' name='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_PAGE]+"' class='form-control pull-right' id='"+FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_PAGE]+"' value='"+empReprimand.getPage()+"'>"
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
                    + "<textarea class='form-control' placeholder='Type Description Point...' name='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DESCRIPTION] +"' id='"+ FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DESCRIPTION] +"'>"+ empReprimand.getDescription()+"</textarea>"
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
    
    private String updateFinger(HttpServletRequest request){
        String htmlReturn="";
        String spvName ="";
        long oidDetail = 0;
        
        EmpReprimand empReprimand = new EmpReprimand();
        
        oidDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OID_DETAIL");
        
        try {
            empReprimand =PstEmpReprimand.fetchExc(oidDetail);
        } catch (Exception e) {
        }
        
        String sqlUpdate = "UPDATE "+PstEmpReprimand.TBL_REPRIMAND+" SET ";
        sqlUpdate += PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_ACKNOWLEDGE_STATUS]+"=1";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdate += " WHERE "+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID]+"="+oidDetail;
        try {
            DBHandler.execUpdate(sqlUpdate);
        } catch (Exception exc){}
        
        String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
        sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_FINGER_CHECK]+"=0";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+empReprimand.getEmployeeId();
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
