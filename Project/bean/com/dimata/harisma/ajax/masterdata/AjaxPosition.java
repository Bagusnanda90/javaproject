/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.util.Date;
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
 * @author Gunadi
 */
public class AjaxPosition extends HttpServlet {

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
    private long datachange = 0;
    private long value = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    I_Atendance attdConfig = null;

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        this.empId = FRMQueryString.requestLong(request, "empId");
        this.datachange = FRMQueryString.requestLong(request, "datachange");
        this.value = FRMQueryString.requestLong(request, "value");
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        this.empName = FRMQueryString.requestString(request, "empName");
	
	//INT
	this.iCommand = FRMQueryString.requestCommand(request);
	this.iErrCode = 0;
        
        try {
            this.attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
        }
	
	
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
                
            case Command.POST :
                commandSaveMapping(request);
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
	if(this.dataFor.equals("showPositionForm")){
	  this.htmlReturn = positionForm();
	} else if(this.dataFor.equals("showPositionMappingForm")){
	  this.htmlReturn = positionMappingForm();
	}  
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlPosition ctrlPosition = new CtrlPosition(request);
	this.iErrCode = ctrlPosition.action(this.iCommand, this.oid, this.attdConfig, this.oidDelete);
	String message = ctrlPosition.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandSaveMapping(HttpServletRequest request){
	if(this.dataFor.equals("addToCompany")){
            PositionCompany psc = new PositionCompany();
            psc.setCompanyId(this.value);
            psc.setPositionId(this.oid);
            try{
                PstPositionCompany.insertExc(psc);
            } catch (Exception exc){}
	}
        if(this.dataFor.equals("addToDivision")){
            PositionDivision div = new PositionDivision();
            div.setDivisionId(this.value);
            div.setPositionId(this.oid);
            try{
                PstPositionDivision.insertExc(div);
            } catch (Exception exc){}
	}
        if(this.dataFor.equals("addToDepartment")){
            PositionDepartment depart = new PositionDepartment();
            depart.setDepartmentId(this.value);
            depart.setPositionId(this.oid);
            try{
                PstPositionDepartment.insertExc(depart);
            } catch (Exception exc){}
	}
        if(this.dataFor.equals("addToSection")){
            PositionSection section = new PositionSection();
            section.setSectionId(this.value);
            section.setPositionId(this.oid);
            try{
                PstPositionSection.insertExc(section);
            } catch (Exception exc){}
	}
        if(this.dataFor.equals("addToSubSection")){
            PositionSubSection subSection = new PositionSubSection();
            subSection.setSubSectionId(this.value);
            subSection.setPositionId(this.oid);
            try{
                PstPositionSubSection.insertExc(subSection);
            } catch (Exception exc){}
	}
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlPosition ctrlPosition = new CtrlPosition(request);
	this.iErrCode = ctrlPosition.action(this.iCommand, this.oid, this.attdConfig, this.oidDelete);
	String message = ctrlPosition.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlPosition ctrlPosition = new CtrlPosition(request);
	this.iErrCode = ctrlPosition.action(this.iCommand, this.oid, this.attdConfig, this.oidDelete);
	String message = ctrlPosition.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listPosition")){
	    String[] cols = { PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
                PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
		PstPosition.fieldNames[PstPosition.FLD_POSITION],
                PstPosition.fieldNames[PstPosition.FLD_POSITION_KODE], 
                PstPosition.fieldNames[PstPosition.FLD_DESCRIPTION],
                PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID],
                PstPosition.fieldNames[PstPosition.FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT],
                PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
                PstPosition.fieldNames[PstPosition.FLD_VALID_STATUS]
            };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	} 
        if(this.dataFor.equals("listCompany")){
	    String[] cols = { PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
                PstPositionCompany.fieldNames[PstPositionCompany.FLD_COMPANY_ID]
            };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
        if(this.dataFor.equals("listDivision")){
	    String[] cols = { PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
                PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]
            };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
        if(this.dataFor.equals("listDepartment")){
	    String[] cols = { PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
                PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_DEPARTMENT_ID]
            };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
        if(this.dataFor.equals("listSection")){
	    String[] cols = { PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
                PstPositionSection.fieldNames[PstPositionSection.FLD_SECTION_ID]
            };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
        if(this.dataFor.equals("listSubSection")){
	    String[] cols = { PstPosition.fieldNames[PstPosition.FLD_POSITION_ID],
                PstPositionSubSection.fieldNames[PstPositionSubSection.FLD_SUBSECTION_ID]
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
        
        if(dataFor.equals("listPosition")){
	    whereClause += " ("+PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listPosition")){
	    total = PstPosition.getCount(whereClause);
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
        Position pos = new Position();
        Position deptJoin = new Position();
        Division div = new Division();
        String whereClause = "";
        String whereCompany = "";
        String whereDivision = "";
        String whereDepartment = "";
        String whereSection = "";
        String whereSubSection = "";
        String order ="";
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listPosition")){
               whereClause += " ("+PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstPosition.fieldNames[PstPosition.FLD_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%' )";
            }
             if(dataFor.equals("listCompany")){
                 whereCompany += "";
             }
             if(dataFor.equals("listDivision")){
                 whereDivision += "";
             }
             if(dataFor.equals("listDepartment")){
                 whereDepartment += "";
             }
             if(dataFor.equals("listSection")){
                 whereSection += "";
             }
             if(dataFor.equals("listSubSection")){
                 whereSubSection += "";
             }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
        Vector listCompany = new Vector(1,1);
        Vector listDivision = new Vector(1,1);
        Vector listDepartment = new Vector(1,1);
        Vector listSection = new Vector(1,1);
        Vector listSubSection = new Vector(1,1);
	if(datafor.equals("listPosition")){
	    listData = PstPosition.list(this.start, this.amount,whereClause,order);
	}
        if(datafor.equals("listCompany")){
            whereCompany = "POSITION_ID="+this.oid;
            listCompany = PstPositionCompany.list(0, 0, whereCompany, "");
        }
        if(datafor.equals("listDivision")){
            String whereDiv = "POSITION_ID="+this.oid;
            listDivision = PstPositionDivision.list(0, 0, whereDiv, "");
        }
        if(datafor.equals("listDepartment")){
            String wherePosDepart = "POSITION_ID="+this.oid;
            listDepartment = PstPositionDepartment.list(0, 0, wherePosDepart, "");
        }
        if(datafor.equals("listSection")){
            String whereSec = "POSITION_ID="+this.oid;
            listSection = PstPositionSection.list(0, 0, whereSec, "");
        }
        if(datafor.equals("listSubSection")){
            whereSubSection = "POSITION_ID="+this.oid;
            listSubSection = PstPositionSubSection.list(0, 0, whereSubSection, "");
        }
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listPosition")){
		pos = (Position) listData.get(i);
                String checkButton = "<input type='checkbox' name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_ID]+"' class='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_ID]+"' value='"+pos.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
                ja.put(""+pos.getPosition());
                if (attdConfig != null && attdConfig.getConfigurationShowPositionCode()) {
                    ja.put(""+pos.getKodePosition());
                }
                ja.put(""+PstPosition.strPositionLevelNames[pos.getPositionLevel()]);
		ja.put(""+PstPosition.strShowPayInput[pos.getFlagShowPayInput()]);
                
                String buttonSearch = "<button class='btn btn-success btnjobdesc btn-xs' data-oid='"+pos.getOID()+"' data-for='showPositionJobDesc' type='button' data-toggle='tooltip' data-placement='top' title='Job Description'><i class='fa fa-search'></i></button> ";
                
                ja.put(""+buttonSearch);
                ja.put(""+PstPosition.validStatusValue[pos.getValidStatus()]);
                String buttonUpdate = "";
                String buttonMapping = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+pos.getOID()+"' data-for='showPositionForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		    buttonMapping = "<button class='btn btn-warning btnmapping btn-xs' data-oid='"+pos.getOID()+"' data-for='showPositionMappingForm' type='button' data-toggle='tooltip' data-placement='top' title='Mapping Position'><i class='fa fa-cog fa-spin'></i></button> ";                    
		}
		ja.put(buttonUpdate+buttonMapping+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+pos.getOID()+"' data-for='deletePositionSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
		array.put(ja);
	    }
        } 
        
        for (int idxComp = 0; idxComp < listCompany.size(); idxComp++){
            JSONArray ja = new JSONArray();
            if(datafor.equals("listCompany")){
                try {
                PositionCompany posComp = (PositionCompany)listCompany.get(idxComp);
                Company company = PstCompany.fetchExc(posComp.getCompanyId());

                ja.put(""+(this.start+idxComp+1));
                ja.put(""+company.getCompany());
                ja.put("");
                array.put(ja);

                } catch (Exception exc){

                }
            }
        }
        
        for (int idxDiv = 0; idxDiv < listDivision.size(); idxDiv++){
            JSONArray ja = new JSONArray();
            if(datafor.equals("listDivision")){
                try {
                PositionDivision posDiv = (PositionDivision)listDivision.get(idxDiv);
                Division division = PstDivision.fetchExc(posDiv.getDivisionId());

                ja.put(""+(this.start+idxDiv+1));
                ja.put(""+division.getDivision());
                ja.put("");
                array.put(ja);

                } catch (Exception exc){

                }
            }
        }
        
        for (int idxDep = 0; idxDep < listDepartment.size(); idxDep++){
            JSONArray ja = new JSONArray();
            if(datafor.equals("listDepartment")){
                try {
                PositionDepartment posDept = (PositionDepartment)listDepartment.get(idxDep);
                Department dept = PstDepartment.fetchExc(posDept.getDepartmentId());

                ja.put(""+(this.start+idxDep+1));
                ja.put(""+dept.getDepartment());
                ja.put("");
                array.put(ja);

                } catch (Exception exc){

                }
            }
        }
        
        for (int idxSec = 0; idxSec < listSection.size(); idxSec++){
            JSONArray ja = new JSONArray();
            if(datafor.equals("listSection")){
                try {
                PositionSection posSec = (PositionSection)listSection.get(idxSec);
                Section sec = PstSection.fetchExc(posSec.getSectionId());

                ja.put(""+(this.start+idxSec+1));
                ja.put(""+sec.getSection());
                ja.put("");
                array.put(ja);

                } catch (Exception exc){

                }
            }
        }
        
        for (int idxSSec = 0; idxSSec < listSubSection.size(); idxSSec++){
            JSONArray ja = new JSONArray();
            if(datafor.equals("listSubSection")){
                try {
                PositionSubSection posSSec = (PositionSubSection)listSubSection.get(idxSSec);
                SubSection sSec = PstSubSection.fetchExc(posSSec.getSubSectionId());

                ja.put(""+(this.start+idxSSec+1));
                ja.put(""+sSec.getSubSection());
                ja.put("");
                array.put(ja);

                } catch (Exception exc){

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
    
    public String positionForm(){
	
	//CHECK DATA
	Position pos = new Position();
        
	if(this.oid != 0){
	    try{
		pos = PstPosition.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        
        String returnData = "<div class='row'>"
                + "<div class='col-md-12'>"
                    + "<div class='form-group'>"
                        + "<label>Position *</label>"
                        + "<input type='text' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION]+"' class='form-control' name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION]+"' value='"+pos.getPosition()+"'>"
                    + "</div>";
                    if(attdConfig!=null && attdConfig.getConfigurationShowPositionCode()){
        returnData +=  "<div class='form-group'>"
                            + "<label>Position Code</label>"
                            + "<input type='text' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE]+"' class='form-control' name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE]+"' value='"+pos.getKodePosition()+"'>"
                    + "</div>";
                    } else {
        returnData += "<input type='hidden' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE]+"' class='form-control' name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE]+"' value='-'>";
                    }
        returnData += "<div class='form-group'>"
                        + "<label>Position Group</label>"
                        + "<select name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_GROUP_ID]+"' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_GROUP_ID]+"' class='form-control'>";
                            Vector listPosGroup = PstPositionGroup.listAll(); 
                            for(int idx=0; idx < listPosGroup.size();idx++){
                                PositionGroup positionGroup = (PositionGroup) listPosGroup.get(idx);																						
                                if (pos.getPositionGroupId()== positionGroup.getOID()) {
                                    returnData += "<option selected='selected' value='"+positionGroup.getOID()+"'>"+positionGroup.getPositionGroupName()+"</option>";
                                } else {
                                    returnData += "<option value='"+positionGroup.getOID()+"'>"+positionGroup.getPositionGroupName()+"</option>";
                                }
                            }
            returnData += "</select>"          
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Type</label>"
                        + "<select name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL]+"' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL]+"' class='form-control'>";
                            for(int x=0; x < PstPosition.strPositionLevelNames.length;x++){
                                if (pos.getPositionLevel()== Integer.valueOf(PstPosition.strPositionLevelValue[x])) {
                                    returnData += "<option selected='selected' value='"+PstPosition.strPositionLevelValue[x]+"'>"+PstPosition.strPositionLevelNames[x]+"</option>";
                                } else {
                                    returnData += "<option value='"+PstPosition.strPositionLevelValue[x]+"'>"+PstPosition.strPositionLevelNames[x]+"</option>";
                                }
                            }
            returnData += "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Type for Payroll</label>"
                        + "<select name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL_PAYROL]+"' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL_PAYROL]+"' class='form-control'>"
                            + "<option value=''>None</option>";
                            for(int i=0; i < PstPosition.strPositionLevelNames.length;i++){
                                if (pos.getPositionLevelPayrol()== Integer.valueOf(PstPosition.strPositionLevelValue[i])) {
                                    returnData += "<option selected='selected' value='"+PstPosition.strPositionLevelValue[i]+"'>"+PstPosition.strPositionLevelNames[i]+"</option>";
                                } else {
                                    returnData += "<option value='"+PstPosition.strPositionLevelValue[i]+"'>"+PstPosition.strPositionLevelNames[i]+"</option>";
                                }
                            }
            returnData += "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                        + "<label>Head Title</label>"
                            + "<select name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_HEAD_TITLE]+"' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_HEAD_TITLE]+"' class='form-control'>"
                            + "<option value=''>None</option>";
                            try{
                                for(int n=0; n < PstPosition.strHeadTitle.length;n++){																						
                                    if(pos.getHeadTitle() == Integer.valueOf(PstPosition.strHeadTitleInt[n])){
                                    returnData += "<option selected='selected' value='"+PstPosition.strHeadTitleInt[n]+"'>"+PstPosition.strHeadTitle[n]+"</option>";
                                    } else {
                                        returnData += "<option value='"+PstPosition.strHeadTitleInt[n]+"'>"+PstPosition.strHeadTitle[n]+"</option>";
                                    }
                                } 
                            }   catch (Exception e){
                                    System.out.println("Error on head title : "+e.toString());
                                }
            returnData += "</select>"
                    + "</div>"
                    + "<div class='form-group'>"
                            + "<label>Valid Status</label>"
                            + "<select name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_STATUS]+"' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_STATUS]+"' class='form-control' > ";
                                if (pos.getValidStatus() == PstPosition.VALID_ACTIVE){
                        returnData += "<option value='"+PstPosition.VALID_ACTIVE+"' selected='selected'>"+PstPosition.validStatusValue[PstPosition.VALID_ACTIVE]+"</option>"
                                    + "<option value='"+PstPosition.VALID_HISTORY+"'>"+PstPosition.validStatusValue[PstPosition.VALID_HISTORY]+"</option>";
                                } else {
                        returnData += "<option value='"+PstPosition.VALID_ACTIVE+"'>"+PstPosition.validStatusValue[PstPosition.VALID_ACTIVE]+"</option>"
                                    + "<option value='"+PstPosition.VALID_HISTORY+"' selected='selected'>"+PstPosition.validStatusValue[PstPosition.VALID_HISTORY]+"</option>";                                    
                                }
                returnData += "</select>"
                    + "</div>"
                + "</div>"
                + "<div class='col-md-6'>"
                    + "<div class='form-group'>"
                        + "<label>Start Date</label>"
                        + "<input type='text' name='"+ FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_START]+"'  id='"+ FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_START]+"' class='form-control datepicker' value='"+(pos.getValidStart()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(pos.getValidStart(),"yyyy-MM-dd"))+"'>"
                    + "</div>"
                + "</div>" 
                + "<div class='col-md-6'>"
                    + "<div class='form-group'>"
                        + "<label>End Date</label>"
                        + "<input type='text' name='"+ FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_END]+"'  id='"+ FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_END]+"' class='form-control datepicker' value='"+(pos.getValidEnd()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(pos.getValidEnd(),"yyyy-MM-dd"))+"'>"
                    + "</div>"
                + "</div>"
                        + "<div class='col-md-12'>"
                        + "<div class='form-group'>"
                            + "<label>End Date</label>"
                            + "<select name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL]+"' id='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL]+"' class='form-control'>"
                            + "<option value=''>Select Level...</option>";
                             String orderLevel = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK] + " ASC";
                                Vector listLevel = PstLevel.list(0, 0, "", orderLevel);
                                if (listLevel != null && listLevel.size() > 0) {
                                    for (int l = 0; l < listLevel.size(); l++) {
                                        Level level = (Level) listLevel.get(l);
                                        if (level.getOID() == pos.getLevelId()) {
                                            returnData += "<option selected='selected' value='"+level.getOID()+"'>"+level.getLevel()+"</option>";
                                        } else {
                                            returnData += "<option value='"+level.getOID()+"'>"+level.getLevel()+"</option>";
                                        }
                                    }
                                }
            returnData += "</div>"
                    + "<div class='form-group'>"
                        + "<label>Show In Pay Input</label><br>"
                        + "<input type='checkbox' name='"+FrmPosition.fieldNames[FrmPosition.FRM_FIELD_FLAG_POSITION_SHOW_PAY_INPUT]+"' "+(pos.getFlagShowPayInput()==PstPosition.YES_SHOW_PAY_INPUT ? "\"checked\"" : "\"\"" )+" value='1'> "
                        + "<a>please check to show in pay input </a>"
                    + "</div>"
                    + "</div>"
            + "</div>" ;      
        return returnData;
    }
    
    public String positionMappingForm(){
        
        /*Company*/
        Vector com_value = new Vector(1, 1);
        Vector com_key = new Vector(1, 1);
        
        com_key.add("Select Company...");
        com_value.add("");
        
        Vector listCom = PstCompany.list(0, 0, "", "");
        for (int i = 0; i < listCom.size(); i++) {
            Company company = (Company) listCom.get(i);
            com_key.add(company.getCompany());
            com_value.add(String.valueOf(company.getOID()));
        }
        
        /*Division*/
        Vector div_value = new Vector(1, 1);
        Vector div_key = new Vector(1, 1);

        Vector listDiv  = new Vector();
        div_key.add("Select Division...");
        div_value.add("");
        
        listDiv = PstDivision.list(0, 0, "VALID_STATUS=1", "");
        
        for (int i = 0; i < listDiv.size(); i++) {
            Division div = (Division) listDiv.get(i);
            div_key.add(div.getDivision());
            div_value.add(String.valueOf(div.getOID()));
        }
        
        /*Department*/
        Vector dep_value = new Vector(1, 1);
        Vector dep_key = new Vector(1, 1);
        Vector listDep = PstDepartment.list(0, 0, "hr_department.VALID_STATUS=1", "");
        
        dep_key.add("Select Department...");
        dep_value.add("");
        
        Hashtable hashDiv = PstDivision.listMapDivisionName(0, 0, "", "");
        long tempDivOid = 0 ;
        for (int i = 0; i < listDep.size(); i++) {
            Department dep = (Department) listDep.get(i);

            if (dep.getDivisionId() != tempDivOid){
                dep_key.add("--"+hashDiv.get(dep.getDivisionId())+"--");
                dep_value.add(String.valueOf(-1));
                tempDivOid = dep.getDivisionId();
            }

            dep_key.add(dep.getDepartment());
            dep_value.add(String.valueOf(dep.getOID()));
        }
        
        /*Section*/
        Vector sec_value = new Vector(1, 1);
        Vector sec_key = new Vector(1, 1);

        Vector listSec = new Vector();
        sec_key.add("Select Section...");
        sec_value.add("");

        listSec = PstSection.list(0, 0, "VALID_STATUS=1", "DEPARTMENT_ID");
            
        Hashtable hashDepart = PstDepartment.listMapDepName(0, 0, "", "", "");
        long tempDepOid = 0 ;
        for (int i = 0; i < listSec.size(); i++) {
            Section sec = (Section) listSec.get(i);

            if (sec.getDepartmentId() != tempDepOid){
                sec_key.add("--"+hashDepart.get(""+sec.getDepartmentId())+"--");
                sec_value.add(String.valueOf(-1));
                tempDepOid = sec.getDepartmentId();
            }

            sec_key.add(sec.getSection());
            sec_value.add(String.valueOf(sec.getOID()));
        }
        
        /*Sub Section*/
        Vector subsec_value = new Vector(1, 1);
        Vector subsec_key = new Vector(1, 1);

        Vector listSubSec = new Vector();
        subsec_key.add("Select Sub Section...");
        subsec_value.add("");

        listSubSec = PstSubSection.list(0, 0, "VALID_STATUS=1", "SECTION_ID");
            
        Hashtable hashSection = PstSection.listMapSecName(0,0,"","");
        long tempSecOid = 0 ;
        for (int i = 0; i < listSubSec.size(); i++) {
            SubSection subSec = (SubSection) listSubSec.get(i);

            if (subSec.getSectionId() != tempSecOid){
                subsec_key.add("--"+hashSection.get(""+subSec.getSectionId())+"--");
                subsec_value.add(String.valueOf(-1));
                tempSecOid = subSec.getSectionId();
            }

            subsec_key.add(subSec.getSubSection());
            subsec_value.add(String.valueOf(subSec.getOID()));
        }
        
        String returnData = "";
                returnData += "<div class='row'>"
                                + "<ul class='nav nav-tabs'>"
                                    + "<li class='active '><a class='tabCompany' href='#tab_company' data-toggle=\"tab\">Company</a></li>"
                                    + "<li><a class='tabDivision' href='#tab_division' data-toggle=\"tab\">Division</a></li>"
                                    + "<li><a class='tabDepartment' href='#tab_department' data-toggle=\"tab\">Department</a></li>"
                                    + "<li><a class='tabSection' href='#tab_section' data-toggle=\"tab\">Section</a></li>"
                                    + "<li><a class='tabSubSection' href='#tab_subsection' data-toggle=\"tab\">Sub Section</a></li>"
                                + "</ul>"
                                + "<div class='tab-content'>"
                                    + "<div class='tab-pane active' id='tab_company'>"
                                        + "<div class='input-group' style='margin-top:5px'>"
                                            + "<span class='input-group-btn'>"
                                                + "<button class='btn btn-primary btnaddmapping' data-oid='"+this.oid+"' data-for='addToCompany' type='button'>"
                                                    + "<i class='fa fa-plus'></i> Add"
                                                + "</button>"
                                            + "</span>"
                                            + ControlCombo.draw("select_company", "choosen-select form-control", null, "" + "", com_value, com_key, "id='select_company'")
                                        + "</div>"
                                        + "<div id='companyElement' style='margin-top:5px'>"
                                            + "<table class='table table-bordered table-striped'>"
                                                + "<thead>"
                                                    + "<tr>"
                                                        + "<th>No.</th>"
                                                        + "<th>Company Name</th>"
                                                        + "<th>Action</th>"
                                                    + "</tr>"
                                                + "</thead>"
                                            + "</table>"
                                        + "</div>"
                                    + "</div>"
                                    + "<div class='tab-pane' id='tab_division'>"
                                        + "<div class='input-group' style='margin-top:15px'>"
                                            + "<span class='input-group-btn'>"
                                                + "<button class='btn btn-primary btnaddmapping' data-oid='"+this.oid+"' data-for='addToDivision' type='button'>"
                                                    + "<i class='fa fa-plus'></i> Add"
                                                + "</button>"
                                            + "</span>"
                                            + ControlCombo.draw("select_division", "choosen-select form-control", null, "", div_key, div_value, null, "id='select_division'")
                                        + "</div>"
                                        + "<div id='divisionElement' style='margin-top:5px'>"
                                            + "<table class='table table-bordered table-striped'>"
                                                + "<thead>"
                                                    + "<tr>"
                                                        + "<th>No.</th>"
                                                        + "<th>Division Name</th>"
                                                        + "<th>Action</th>"
                                                    + "</tr>"
                                                + "</thead>"
                                            + "</table>"
                                        + "</div>"
                                    + "</div>"
                                    + "<div class='tab-pane' id='tab_department'>"
                                        + "<div class='input-group' style='margin-top:15px'>"
                                            + "<span class='input-group-btn'>"
                                                + "<button class='btn btn-primary btnaddmapping' data-oid='"+this.oid+"' data-for='addToDepartment' type='button'>"
                                                    + "<i class='fa fa-plus'></i> Add"
                                                + "</button>"
                                            + "</span>"
                                            + ControlCombo.draw("select_department", "choosen-select form-control", null, "", dep_key, dep_value, null, "id='select_department'")
                                        + "</div>"
                                        + "<div id='departmentElement' style='margin-top:5px'>"
                                            + "<table class='table table-bordered table-striped'>"
                                                + "<thead>"
                                                    + "<tr>"
                                                        + "<th>No.</th>"
                                                        + "<th>Department Name</th>"
                                                        + "<th>Action</th>"
                                                    + "</tr>"
                                                + "</thead>"
                                            + "</table>"
                                        + "</div>"
                                    + "</div>"
                                    + "<div class='tab-pane' id='tab_section'>"
                                        + "<div class='input-group' style='margin-top:15px'>"
                                            + "<span class='input-group-btn'>"
                                                + "<button class='btn btn-primary btnaddmapping' data-oid='"+this.oid+"' data-for='addToSection' type='button'>"
                                                    + "<i class='fa fa-plus'></i> Add"
                                                + "</button>"
                                            + "</span>"
                                            + ControlCombo.draw("select_section", "choosen-select form-control", null, "", sec_key, sec_value, null, "id='select_section'")
                                        + "</div>"
                                        + "<div id='sectionElement' style='margin-top:5px'>"
                                            + "<table class='table table-bordered table-striped'>"
                                                + "<thead>"
                                                    + "<tr>"
                                                        + "<th>No.</th>"
                                                        + "<th>Section Name</th>"
                                                        + "<th>Action</th>"
                                                    + "</tr>"
                                                + "</thead>"
                                            + "</table>"
                                        + "</div>"
                                    + "</div>"
                                    + "<div class='tab-pane' id='tab_subsection'>"
                                        + "<div class='input-group' style='margin-top:15px'>"
                                            + "<span class='input-group-btn'>"
                                                + "<button class='btn btn-primary btnaddmapping' data-oid='"+this.oid+"' data-for='addToSubSection' type='button'>"
                                                    + "<i class='fa fa-plus'></i> Add"
                                                + "</button>"
                                            + "</span>"
                                            + ControlCombo.draw("select_subsection", "choosen-select form-control", null, "", subsec_key, subsec_value, null, "id='select_subsection'")
                                        + "</div>"
                                        + "<div id='subsectionElement' style='margin-top:5px'>"
                                            + "<table class='table table-bordered table-striped'>"
                                                + "<thead>"
                                                    + "<tr>"
                                                        + "<th>No.</th>"
                                                        + "<th>Sub Section Name</th>"
                                                        + "<th>Action</th>"
                                                    + "</tr>"
                                                + "</thead>"
                                            + "</table>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
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