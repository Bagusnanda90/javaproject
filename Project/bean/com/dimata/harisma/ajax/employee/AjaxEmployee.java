/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.FingerPatern;
import com.dimata.harisma.entity.admin.PstFingerPatern;
import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.log.ChangeValue;
import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstPositionCompany;
import com.dimata.harisma.entity.masterdata.PstPositionDepartment;
import com.dimata.harisma.entity.masterdata.PstPositionDivision;
import com.dimata.harisma.entity.masterdata.PstPositionSection;
import com.dimata.harisma.entity.masterdata.PstPositionSubSection;
import com.dimata.harisma.entity.masterdata.PstRace;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Race;
import com.dimata.harisma.entity.masterdata.Religion;
import com.dimata.harisma.form.employee.CtrlEmployee;
import com.dimata.harisma.form.employee.FrmEmployee;
import com.dimata.harisma.session.admin.SessUserSession;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Dimata 007
 */
public class AjaxEmployee extends HttpServlet {

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
    private long empId = 0;
    private long posComp = 0;
    private long posDiv = 0;
    private long posDept = 0;
    private long posSec = 0;
    private long posSubSec = 0;
    
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    public static final String textListFinger[][]={
        {"Kelingking Kiri","Jari Manis Kiri","Jari Tengah Kiri","Telunjuk Kiri","Ibu Jari Kiri","Ibu Jari Kanan","Telunjuk Kanan","Jari Tengah Kanan","Jari Manis Kanan","Kelingking Kanan"},
        {"Left Little Finger","Left Ring Finger","Left Middle Finger","Left Fore Finger","Left Thumb","Right Thumb","Right Fore Finger","Right Middle Finger","Right Ring Finger","Right Little Finger"}   
    };
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
        this.empId = FRMQueryString.requestLong(request, "empId");
	this.posComp = FRMQueryString.requestLong(request, "posComp");
        this.posDiv = FRMQueryString.requestLong(request, "podDiv");
        this.posDept = FRMQueryString.requestLong(request, "posDept");
        this.posSec = FRMQueryString.requestLong(request, "posSec");
        this.posSubSec = FRMQueryString.requestLong(request, "posSubSec");

        
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
		
	    /*case Command.DELETEALL : 
		commandDeleteAll(request);
	    break;*/
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
	if(this.dataFor.equals("showchaneltypeform")){
	   // this.htmlReturn = chanelTypeForm();
	}else if(this.dataFor.equals("addfilter")){
            this.htmlReturn = addFilter(request);
        }
        else if(this.dataFor.equals("showform")){
            this.htmlReturn = dataBankForm();
        }
        else if(this.dataFor.equals("showSendEmailForm")){
            this.htmlReturn = sendEmailForm();
        }
        else if(this.dataFor.equals("showAcknowledgeForm")){
            this.htmlReturn = checkUser(request);
        }
        else if (this.dataFor.equals("checkStatusUser")){
            this.htmlReturn = checkStatusUser(request);
        }
        else if (this.dataFor.equals("getPosition")){
            this.htmlReturn = posSelect(this.empId);
        }
    }
    
    public void commandSave(HttpServletRequest request){
        CtrlEmployee ctrlEmployeeType = new CtrlEmployee(request);
	this.iErrCode = ctrlEmployeeType.action(this.iCommand, this.oid, request, colName, oid); ///.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlEmployeeType.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        if (this.dataFor.equals("approveFinger")){
            this.htmlReturn = updateFinger(request);
        }
        
    }
    
    public void commandDeleteAll(HttpServletRequest request){
        /*
	CtrlChanelType ctrlChanelType = new CtrlChanelType(request);
	this.iErrCode = ctrlChanelType.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlChanelType.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        */
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("employee_list")){
	    String[] cols = {
                PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID],
                PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM],
                PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME],
                PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID],
                PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID],
                PstEmployee.fieldNames[PstEmployee.FLD_SEX],
                PstEmployee.fieldNames[PstEmployee.FLD_PHONE],
                PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE],
                PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE],
            };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
    }
    
    public JSONObject listDataTables (HttpServletRequest request, HttpServletResponse response, String[] cols, String dataFor, JSONObject result){
        this.searchTerm = FRMQueryString.requestString(request, "sSearch");
        String empNum = FRMQueryString.requestString(request, "employee_num");
        String empName = FRMQueryString.requestString(request, "employee_name");
        long companyId = FRMQueryString.requestLong(request, "company_id");
        long divisionId = FRMQueryString.requestLong(request, "division_id");
        long departmentId = FRMQueryString.requestLong(request, "department_id");
        long sectionId = FRMQueryString.requestLong(request, "section_id");
        long positionId = FRMQueryString.requestLong(request, "position_id");
        int resign = FRMQueryString.requestInt(request, "resign");
        String inEmpId = FRMQueryString.requestString(request, "inEmpId");
        long levelId = FRMQueryString.requestLong(request, "level_id");
        long empCatId = FRMQueryString.requestLong(request, "emp_category_id");
        long maritalId = FRMQueryString.requestLong(request, "marital_id");
        long raceId = FRMQueryString.requestLong(request, "race_id");
        long religionId = FRMQueryString.requestLong(request, "religion_id");
        int birthMonth = FRMQueryString.requestInt(request, "birth_month");
        
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
        
        if(dataFor.equals("employee_list")){
            Vector whereVect = new Vector();
            if (empNum.length() > 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE '%"+empNum+"%' ";
                whereVect.add(whereClause);
            }
            if (empName.length() > 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+empName+"%' ";
                whereVect.add(whereClause);
            }
            if (companyId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId+" ";
                whereVect.add(whereClause);
            }
            if (divisionId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId+" ";
                whereVect.add(whereClause);
            }
            if (departmentId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId+" ";
                whereVect.add(whereClause);
            }
            if (sectionId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId+" ";
                whereVect.add(whereClause);
            }
            if (positionId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+positionId+" ";
                whereVect.add(whereClause);
            }
            if (resign > -1){
                /* tampilkan hanya yang tidak resign */
                if (resign == 0){
                    whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+resign+" ";
                    whereVect.add(whereClause);
                }
                /* tampilkan yang resign dan yang tidak resign*/
                if (resign == 1){
                    whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" IN (0,1) ";
                    whereVect.add(whereClause);
                }
            }
            if (!(inEmpId.equals(""))){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN ("+inEmpId+") ";
                whereVect.add(whereClause);
            }
            if (empCatId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCatId+" ";
                whereVect.add(whereClause);
            }
            if (levelId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+"="+levelId+" ";
                whereVect.add(whereClause);
            }
            if (maritalId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]+"="+maritalId+" ";
                whereVect.add(whereClause);
            }
            if (raceId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RACE]+"="+raceId+" ";
                whereVect.add(whereClause);
            }
            if (religionId != 0){
                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]+"="+religionId+" ";
                whereVect.add(whereClause);
            }
            if (birthMonth != 0){
                whereClause = " MONTH(EMP."+PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+") = "+birthMonth+" ";
                whereVect.add(whereClause);
            }
            if (!(this.searchTerm.equals(""))){
                whereClause = " (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+this.searchTerm+"%'"
                        + " OR EMP." +PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE '%"+this.searchTerm+"%' "
                        + " OR DV." +PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+this.searchTerm+"%'"
                        + " OR POS." +PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%'"
                        + " OR EMP." +PstEmployee.fieldNames[PstEmployee.FLD_PHONE]+" LIKE '%"+this.searchTerm+"%'"
                        + " OR DATE_FORMAT(EMP." +PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+ ", '%d %M %Y') LIKE '%"+this.searchTerm+"%'"
                        + " OR DATE_FORMAT(EMP." +PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]+ ", '%d %M %Y') LIKE '%"+this.searchTerm+"%'"
                        + " OR IF("+PstEmployee.fieldNames[PstEmployee.FLD_SEX]+" = "
                        + PstEmployee.sexValue[PstEmployee.MALE]+ ", '"+PstEmployee.sexKey[PstEmployee.MALE]+"' , "
                        + "'"+PstEmployee.sexKey[PstEmployee.FEMALE]+"') LIKE '%"+this.searchTerm+"%')";
                whereVect.add(whereClause);
            }
            if (whereVect != null && whereVect.size()>0){
                whereClause = "";
                for(int i=0; i<whereVect.size(); i++){
                    String data = (String)whereVect.get(i);
                    whereClause = whereClause + data;
                    if (i == whereVect.size()-1){
                        whereClause += " ";
                    } else {
                        whereClause += " AND ";
                    }
                }
            }
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("employee_list")){
	    total = PstEmployee.getCountDataTable(whereClause); // PstChanelType.getCount(whereClause);
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
        Employee employee = new Employee();

        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("employee_list")){
            listData = PstEmployee.listDataTable(this.start, this.amount, whereClause, order);
	}
        String sex = "-";
        String commencingDate = "-";
        String birthDate = "-";
        String status = "-";
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("employee_list")){
                employee = (Employee) listData.get(i);
                if (employee.getSex() == 0) {
                    sex = "Male";
                } else if (employee.getSex() == 1) {
                    sex = "Female";
                }
                if (employee.getResigned()== 0){
                    status = "Active"; 
                } else if (employee.getResigned() ==1){
                    status = "Non-Active";
                }
                commencingDate = Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy");
                birthDate = Formater.formatDate(employee.getBirthDate(), "dd MMM yyyy");
                if(employee.getResigned() == 1){
                    ja.put("<font color=\"red\">"+(this.start+i+1)+"</font>");
                    ja.put("<font color=\"red\">"+employee.getEmployeeNum()+"</font>");
                    ja.put("<font color=\"red\">"+employee.getFullName()+"</font>");
                    ja.put("<font color=\"red\">"+changeValue.getDivisionName(employee.getDivisionId())+"</font>");
                    ja.put("<font color=\"red\">"+changeValue.getPositionName(employee.getPositionId())+"</font>");
                    ja.put("<font color=\"red\">"+sex+"</font>");
                    ja.put("<font color=\"red\">"+employee.getPhone()+"</font>");
                    ja.put("<font color=\"red\">"+commencingDate+"</font>");
                    ja.put("<font color=\"red\">"+birthDate+"</font>");
                    ja.put("<font color=\"red\">"+status+"</font>");
                } else {
                    ja.put(""+(this.start+i+1));
                    ja.put(""+employee.getEmployeeNum());
                    ja.put(""+employee.getFullName());
                    ja.put(changeValue.getDivisionName(employee.getDivisionId()));
                    ja.put(changeValue.getPositionName(employee.getPositionId()));
                    ja.put(sex);
                    ja.put(employee.getPhone());
                    ja.put(commencingDate);
                    ja.put(birthDate);
                    ja.put("<font color=\"blue\">"+status+"</font>");
                }
                String action = "<button type=\"button\" onclick=\"location.href='../databank/employee_edit.jsp?oid="+ employee.getOID() +"'\" class=\"btn btn-primary btn-xs\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Edit\"><i class=\"fa fa-pencil\"></i></button> ";
                if (employee.getAcknowledgeStatus() > 0){
                   action += "<button type=\"button\" data-oid=\""+ employee.getOID()+"\" class=\"btn btn-success btn-xs deletedata\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Acknowledged\"><i class=\"fa fa-check-circle\"></i></button>";
                }
                ja.put(action);
                /*
		String buttonUpdate = "";
		if(true){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral' data-oid='"+chanelType.getOID()+"' data-for='showchaneltypeform' type='button'><i class='fa fa-pencil'></i> Edit</button> ";
		}
		ja.put(buttonUpdate+"<div class='btn btn-default' type='button'><input type='checkbox' name='"+FrmChanelType.fieldNames[FrmChanelType.FRM_FIELD_CHANEL_TYPE_ID]+"' class='"+FrmChanelType.fieldNames[FrmChanelType.FRM_FIELD_CHANEL_TYPE_ID]+"' value='"+chanelType.getOID()+"'> Delete</div>");
		*/
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
    
    public String addFilter(HttpServletRequest request){
        String filter = FRMQueryString.requestString(request, "FRM_FIELD_FILTER");
        String returnData = "";
        if(filter.equals("all")){
            returnData = empNumForm("emp_num");
            returnData += empNameForm("emp_name");
            returnData += companyForm("company");
            returnData += divisionForm("division");
            returnData += departmentForm("department");
            returnData += sectionForm("section");
            returnData += positionForm("position");
            returnData += levelForm("level");
            returnData += categoryForm("emp_cat");
            returnData += resignForm("resign");
            returnData += maritalForm("marital");
            returnData += raceForm("race");
            returnData += religionForm("birthday");
            returnData += birthdayForm("religion");
        }
         else if(filter.equals("emp_num")){
            returnData = empNumForm(filter);
        }
          else if(filter.equals("emp_name")){
            returnData = empNameForm(filter);
        }
          else if(filter.equals("company")){
            returnData = companyForm(filter);
        }
          else if(filter.equals("division")){
            returnData = divisionForm(filter);
        }
          else if(filter.equals("department")){
            returnData = departmentForm(filter);
        }
          else if(filter.equals("section")){
            returnData = sectionForm(filter);
        }
          else if(filter.equals("position")){
            returnData = positionForm(filter);
        }
          else if(filter.equals("level")){
            returnData = levelForm(filter);
        }
          else if(filter.equals("emp_cat")){
            returnData = categoryForm(filter);
        }
          else if(filter.equals("resign")){
            returnData = resignForm(filter);
        }
          else if(filter.equals("marital")){
            returnData = maritalForm(filter);
        }
          else if(filter.equals("race")){
            returnData = raceForm(filter);
        }
          else if(filter.equals("religion")){
            returnData = religionForm(filter);
        }
        else if(filter.equals("birthday")){
            returnData = birthdayForm(filter);
        }
        
        return returnData;
    }
    
    public String empNumForm(String filter){
        String returnData = "";
        returnData+=""
        + "<div class='col-md-3' id='"+filter+"'>"
                + "<label></label>"
            + "<input type='text' name='employee_number' id='employee_number' class='form-control' placeholder='Employee Number...'>"
        + "</div>";
        return returnData;
    }
    public String empNameForm(String filter){
        String returnData = "";
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
            + "<label></label>"
            + "<input type='text' name='employee_name' id='employee_name' class='form-control' placeholder='Employee Name..'>"
        + "</div>"; 
        return returnData;
    }
    public String companyForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboCompany' style='width: 100%;' name='company_id' id='company_id'>"
                + "<option value='0'>Company</option>";
                Vector listCompany = PstCompany.list(0,0,"","");
                if(listCompany != null && listCompany.size() > 0){
                    for (int i = 0; i < listCompany.size(); i ++){
                        Company comp = (Company) listCompany.get(i);
                        returnData += "<option value='"+comp.getOID()+"'>"+comp.getCompany()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String divisionForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboDivision' style='width: 100%;' name='division_id' id='division_id'>"
                + "<option value='0'>Division</option>";
                Vector listDivision = PstDivision.list(0,0,"","");
                if(listDivision != null && listDivision.size() > 0){
                    for (int i = 0; i < listDivision.size(); i ++){
                        Division div = (Division) listDivision.get(i);
                        returnData += "<option value='"+div.getOID()+"'>"+div.getDivision()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String departmentForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboDepartment' style='width: 100%;' name='department_id' id='department_id'>"
                + "<option value='0'>Department</option>";
                Vector listDepartment = PstDepartment.list(0,0,"","");
                if(listDepartment != null && listDepartment.size() > 0){
                    for (int i = 0; i < listDepartment.size(); i ++){
                        Department dep = (Department) listDepartment.get(i);
                        returnData += "<option value='"+dep.getOID()+"'>"+dep.getDepartment()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String sectionForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboSection' style='width: 100%;' name='section_id' id='section_id'>"
                + "<option value='0'>Section</option>";
                Vector listSection = PstSection.list(0,0,"","");
                if(listSection != null && listSection.size() > 0){
                    for (int i = 0; i < listSection.size(); i ++){
                        Section sec = (Section) listSection.get(i);
                        returnData += "<option value='"+sec.getOID()+"'>"+sec.getSection()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String positionForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboPosition' style='width: 100%;' name='position_id' id='position_id'>"
                + "<option value='0'>Position</option>";
                Vector listPosition = PstPosition.list(0,0,"","");
                if(listPosition != null && listPosition.size() > 0){
                    for (int i = 0; i < listPosition.size(); i ++){
                        Position pos = (Position) listPosition.get(i);
                        returnData += "<option value='"+pos.getOID()+"'>"+pos.getPosition()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String levelForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboLevel' style='width: 100%;' name='level_id' id='level_id'>"
                + "<option value='0'>Level</option>";
                Vector listLevel = PstLevel.list(0,0,"","");
                if(listLevel != null && listLevel.size() > 0){
                    for (int i = 0; i < listLevel.size(); i ++){
                        Level lvl = (Level) listLevel.get(i);
                        returnData += "<option value='"+lvl.getOID()+"'>"+lvl.getLevel()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String categoryForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboCategory' style='width: 100%;' name='category_id' id='category_id'>"
                + "<option value='0'>Emp Category</option>";
                Vector listEmpCategory = PstEmpCategory.list(0,0,"","");
                if(listEmpCategory != null && listEmpCategory.size() > 0){
                    for (int i = 0; i < listEmpCategory.size(); i ++){
                        EmpCategory cat = (EmpCategory) listEmpCategory.get(i);
                        returnData += "<option value='"+cat.getOID()+"'>"+cat.getEmpCategory()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String resignForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
            + "<label></label>"
                + "<div id='radio' class='form-control'>"
                + "<label class='radio-inline'>"
                    + "<input type='radio' class='resign' name='resign' value='0' checked> No"
                + "</label>"
                + "<label class='radio-inline'>"
                    + "<input type='radio' class='resign' name='resign' value='1'> Yes"
                + "</label>"
                + "</div>"
        + "</div>";        
        return returnData;
    }
    public String maritalForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboMarital' style='width: 100%;' name='marital_id' id='marital_id'>"
                + "<option value='0'>Marital Status</option>";
                Vector listMarital = PstMarital.list(0,0,"","");
                if(listMarital != null && listMarital.size() > 0){
                    for (int i = 0; i < listMarital.size(); i ++){
                        Marital marital = (Marital) listMarital.get(i);
                        returnData += "<option value='"+marital.getOID()+"'>"+marital.getMaritalStatus()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String raceForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboRace' style='width: 100%;' name='race_id' id='race_id'>"
                + "<option value='0'>Race</option>";
                Vector listRace = PstRace.list(0,0,"","");
                if(listRace != null && listRace.size() > 0){
                    for (int i = 0; i < listRace.size(); i ++){
                        Race race = (Race) listRace.get(i);
                        returnData += "<option value='"+race.getOID()+"'>"+race.getRaceName()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String religionForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboReligion' style='width: 100%;' name='religion_id' id='religion_id'>"
                + "<option value='0'>Religion</option>";
                Vector listReligion = PstReligion.list(0,0,"","");
                if(listReligion != null && listReligion.size() > 0){
                    for (int i = 0; i < listReligion.size(); i ++){
                        Religion religion = (Religion) listReligion.get(i);
                        returnData += "<option value='"+religion.getOID()+"'>"+religion.getReligion()+"</option>";
                    }
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    public String birthdayForm(String filter){
        String returnData = "";
        
        returnData += ""
        + "<div class='col-md-3' id='"+filter+"'>"
        + "<label></label>"
        + "<select class='form-control comboBirthday' style='width: 100%;' name='birthday_id' id='birthday_id'>"
                + "<option value='0'>Birthday</option>";
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                for(int i=0; i<12; i++) {
                    cal.set(Calendar.MONTH, i);
                    returnData += "<option value='"+(i+1)+"'>"+Formater.formatDate(cal.getTime(), "MMMM")+"</option> ";
                }
        returnData += "</Select>"
                + "</div>";        
        return returnData;
    }
    
    public String posSelect(long employeeId){
        String returnData = "";
        
            returnData += "<option value=''>Select Position...</option>";
        String whereClause = "";
        whereClause = whereClause + " com."+PstPositionCompany.fieldNames[PstPositionCompany.FLD_COMPANY_ID]+ " = "+this.posComp +" OR "
                                  + " dv."+PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+ " = "+this.posDiv+" OR "
                                  + " dept."+PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_DEPARTMENT_ID]+ " = "+this.posDept + " OR "
                                  + " sec."+PstPositionSection.fieldNames[PstPositionSection.FLD_SECTION_ID]+ " = "+this.posSec + " OR "
                                  + " subsec."+PstPositionSubSection.fieldNames[PstPositionSubSection.FLD_SUBSECTION_ID]+ " = "+this.posSubSec;
        
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(employeeId);
        } catch (Exception exc){
            
        }
        
        Vector listPosition = PstPosition.listMappingPos(0, 0, whereClause, "");
        
        if (listPosition != null && listPosition.size()>0){
            for(int i=0; i<listPosition.size(); i++){
                Position position = (Position)listPosition.get(i);
                if (emp.getPositionId() == position.getOID()){
                    returnData +="<option selected=\"selected\" value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
                } else {
                    returnData +="<option value=\""+position.getOID()+"\">"+position.getPosition()+"</option>";
                }
            }
        }
        
        return returnData;
    }
    
    public String dataBankForm(){
        String returnData = "";
        
        I_Atendance attdConfig = null;
        try {
            attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
        }
        
        Employee employee = new Employee();
        try {
            employee = PstEmployee.fetchExc(this.empId);
        } catch (Exception exc){}
        
        String postalCode = "";
        if (employee.getPostalCode() == 0){
            postalCode = "";
        } else {
            postalCode = ""+employee.getPostalCode();
        }
        
        returnData += ""
                + "<div class='col-md-6'>"
                    + "<div class='box' id='hide_basic' style='display: none'>"
                        + "<div class ='box-header with-border'>"
                            + "<div class='pull-left'>Baisc Information</div>"
                            + "<div class='pull-right'><button type='button' class='btn btn-sm' id='show_basic_b'>Show</button></div>"
                        + "</div>"
                    + "</div>"
                    + "<div class='box' id='show_basic'>"
                        + "<div class ='box-header with-border'>"
                            + "<div class='pull-left'>Baisc Information</div>"
                            + "<div class='pull-right'><button type='button' class='btn btn-sm' id='hide_basic_b'>Hide</button></div>"
                        + "</div>"
                        + "<div class='box-body'>"
                            + "<div class='form-group'>"
                                + "<label>Employee Number</label>";
                                if (attdConfig != null && attdConfig.getConfigurasiInputEmpNum() == I_Atendance.CONFIGURATION_II_GENERATE_AUTOMATIC_EMPLOYEE_NUMBER) {
                                    returnData += "<input type='text' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM] + "' value='" + employee.getEmployeeNum() == null || employee.getEmployeeNum().length()==0? "automatic" :employee.getEmployeeNum() + "' class='form-control' readonly='readonly'>";
                                } else {
                                    returnData += "<input type='text' name ='"+ FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM] +"' value='"+ employee.getEmployeeNum()+"' class='form-control'>";
                                }
                 returnData += "</div>"
                            + "<div class='form-group>'>"
                                + "<label> Full Name </label>"
                                + "<input type='text' name='"+ FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]+"' value='"+ employee.getFullName() +"' class='form-control'>"
                            + "</div>"
                            + "<div class='form-group'>"
                                + "<label> Temporary Address </label>"
                                + "<input type='text' name='"+ FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS] + "' size='50' value='"+ employee.getAddress()+"' class='form-control'>"
                                + "<input class='form-control addeditgeo'  type='text' name='geo_address' readonly='true' value='" + employee.getGeoAddress() +"' size='50' id='geo_address'  data-for='geoaddress'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_COUNTRY_ID] +"' value='" + employee.getAddrCountryId() + "' id='oidnegara'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PROVINCE_ID] +"' value='" + employee.getAddrPmntProvinceId()+ "' id='oidprovinsi'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_REGENCY_ID] +"' value='" + employee.getAddrRegencyId()+ "' id='oidkabupaten'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_SUBREGENCY_ID] +"' value='" + employee.getAddrSubRegencyId()+ "' id='oidkecamatan'>"
                            + "</div>"
                            + "<div class='form-group'>"
                                + "<label> Permanent Address </label>"
                                + "<input type='text' name='"+ FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_PERMANENT] +"' size='50' value='"+ employee.getAddressPermanent() != null ? employee.getAddressPermanent() : "" +"' class='form-control'>"
                                + "<input class='form-control addeditgeo' type='text' name'geo_address_pmnt id='geo_address_pmnt' readonly='true' size='50' value='"+ employee.getGeoAddressPmnt() +"' data-for='geoaddresspmnt'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_COUNTRY_ID] +"' value='" + employee.getAddrPmntCountryId() + "' id='oidnegarapmnt'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_PROVINCE_ID] +"' value='" + employee.getAddrPmntProvinceId()+ "' id='oidprovinsipmnt'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_REGENCY_ID] +"' value='" + employee.getAddrPmntRegencyId()+ "' id='oidkabupatenpmnt'>"
                                + "<input type='hidden' name='" + FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_SUBREGENCY_ID] +"' value='" + employee.getAddrPmntSubRegencyId()+ "' id='oidkecamatanpmnt'>"
                            + "</div>"
                            + "<div class='form-group'>"
                                + "<label> Zip Code </label>"
                                + "<input type='text' name ='"+FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSTAL_CODE]+"' value='" + postalCode +"' class='form-control'>"
                            + "</div>"
                            + "<div class='form-group'>"
                                + "<label> Telephone / Handphone </label>"
                                + "<div class='input-group'>"
                                    + "<input type='text' name='" +FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE]+"' value='" + employee.getPhone() != null ? employee.getPhone() : "" + "' class='form-control'>"
                                    + "<span class='input-group-addon'>/</span>"
                                    + "<input type='text' name='" +FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HANDPHONE] + "' value='" + employee.getHandphone() +"' class='form-control'>"
                                + "</div>"
                            + "</div>"
                            + "<div class='form-group'>"
                                + "<label> Emergency Phone / Person Name </label>"
                                + "<div class='input-group'>"
                                    + "<input type='text' name='"+FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE_EMERGENCY] +"' value='"+employee.getPhoneEmergency() != null ? employee.getPhoneEmergency() : "" +"' class='form-control'>"
                                    + "<span class='input-group-addon'>/</span>"
                                    + "<input type='text' name='"+FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NAME_EMG] +"' value='"+employee.getNameEmg() != null ? employee.getNameEmg() : ""+"' class='form-control'>"
                                + "</div>"
                            + "</div>"
                            + "<div class='form-group'>"
                                + "<label>Emergency Address</label>"
                                + "<textarea class='form-control' rows='2' name='"+FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_EMG]+"'>"+employee.getAddressEmg()+"</textarea>"
                            + "</div>"
                            + "<div class='form-group'>"
                                + "<label>Gender</label> *";
                           
                            
                
        return returnData;
    }
    
    public String sendEmailForm(){
        String returnData = ""
                + "<div class='row'>"
                    + "<div class='col-md-12'>"
                        + "<div class='form-group'>"
                            + "<label>Subject</label>"
                            + "<input type='text' name='subject' id='subject' class='form-control'>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>To</label>"
                            + "<input type='text' name='email' id='email' class='form-control'>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>CC</label>"
                            + "<ul id='cc' name='cc'></ul>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>BCC</label>"
                            + "<ul name='bcc' id='bcc'>"
                        + "</div>"
                        + "<div class='form-group'>"
                            + "<label>Message</label>"
                            + "<textarea name='message' id='message' class='form-control'></textarea>"
                        + "</div>"
                    + "</div>"
                + "</div>";
        
        return returnData;
    }
    
    private String checkUser(HttpServletRequest request){
        String htmlReturn="";
        
        //STRING
        String result ="";
        String whereClause="";
        String whereFinger="";
        String whereFinger2="";
        String group ="0";
        String login="";
        String base="";
        
        //LONG
        long oidEmployee=0;
        long oidDetail=0;
        
        //INTEGER
        int typeVerification =1;
        int language=1;
        int cancelationStatus=0;
        
        //Hastable
        Hashtable<Integer, Boolean> fingerType = new Hashtable<Integer, Boolean>();
        //OBJECT
        AppUser appUser= new AppUser();
        oidEmployee = FRMQueryString.requestLong(request,"empId");
        oidDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
        
        htmlReturn += "<input type='hidden' value='"+oidDetail+"' name='FRM_FIELD_OID_DETIL_FINGER_APPROVE' id='FRM_FIELD_OID_DETIL_FINGER_APPROVE'>";
        if (oidEmployee>0){
            whereFinger = " "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+"="+oidEmployee+"";
            Vector listFinger = PstFingerPatern.list(0, 0, whereFinger, "");
            
            //masukkan ke hash table
            for (int i = 0; i<listFinger.size();i++){
                FingerPatern fingerPatern = (FingerPatern)listFinger.get(i);
                fingerType.put(fingerPatern.getFingerType(), true);  
            }
            
            //buat sepuluh kotak
            for (int j= 0; j<10;j++){
                Boolean found = false;
                try{
                    if (fingerType.size()>0){
                        found = fingerType.containsKey(j);
                    }

                }catch(Exception ex){
                    found= false;
                }
                if (found==true){
                    //jika jari tersebut sudah didaftarkan, backgorund kotak hijau, dan langsung berisi link verification
   
                    htmlReturn +=
                    "<div class='finger'>";
                    
                    //memberi link untuk masing-masing kotak jari
                    whereFinger2 =" "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+"= "+oidEmployee+" and "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+"="+j+"";
                    Vector listFinger2 = PstFingerPatern.list(0, 0, whereFinger2, "");
                    FingerPatern fingerPatern = (FingerPatern) listFinger2.get(0);
                   
                    String urlBase = "verification&"+oidEmployee+"&"+fingerPatern.getFingerPatern()+"&"+approot+"employee/databank/process_verification.jsp";
                    if (typeVerification==1){
                        htmlReturn +="<a class='loginFinger' href='findspot:findspot protocol;"+urlBase+"'><div class='finger_spot green' >"+textListFinger[1][j]+"</div></a>";
                    }else{
                        htmlReturn +="<a class='loginFinger2' href='findspot:findspot protocol;"+urlBase+"'><div class='finger_spot green' >"+textListFinger[1][j]+"</div></a>";
                    
                    }
                    htmlReturn +="</div>";
                  
                }else{
                    //jika jari tersebut belum didaftarkan, background kotak putih, dan link verifikasi tidak ada
                    htmlReturn +="<div class='finger'>";
                    htmlReturn +="<div class='finger_spot'></div>";
                    htmlReturn +="</div>";
                }
            }
            
            
        }else{
            for (int j= 0; j<10;j++){
                htmlReturn +="<div class='finger'>";
                htmlReturn +="<div class='finger_spot'></div>";
                htmlReturn +="</div>"; 
            }
        }
        
        return htmlReturn;
    }
    
    private String checkStatusUser(HttpServletRequest request){
        String htmlReturn="";
        //INT
        int result=0;
        //STRING
        String whereClause="";
        long loginId =0;
        
        loginId = FRMQueryString.requestLong(request,"FRM_FIELD_LOGIN_ID");
        
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(loginId);
        } catch (Exception exc){
            
        }
        
        htmlReturn = ""+emp.getFingerCheck();
        
        return htmlReturn;
    }
    
    private String updateFinger(HttpServletRequest request){
        String htmlReturn="";
        String spvName ="";
        long oidDetail = 0;
        
        Employee employee = new Employee();
        
        oidDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OID_DETAIL");
        
        try {
            employee =PstEmployee.fetchExc(oidDetail);
        } catch (Exception e) {
        }
        
        String sqlUpdate = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
        sqlUpdate += PstEmployee.fieldNames[PstEmployee.FLD_ACKNOWLEDGE_STATUS]+"=1";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdate += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+oidDetail;
        try {
            DBHandler.execUpdate(sqlUpdate);
        } catch (Exception exc){}
        
        String sqlUpdateEmp = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
        sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_FINGER_CHECK]+"=0";
        //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
        sqlUpdateEmp += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+employee.getOID();
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
