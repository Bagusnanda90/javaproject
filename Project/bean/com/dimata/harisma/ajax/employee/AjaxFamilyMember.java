/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.harisma.entity.masterdata.Religion;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.harisma.entity.employee.FamilyMember;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.PstFamilyMember;
import com.dimata.harisma.entity.masterdata.FamRelation;
import com.dimata.harisma.entity.masterdata.PstFamRelation;
import com.dimata.harisma.form.employee.CtrlFamilyMember;
import com.dimata.harisma.form.employee.FrmFamilyMember;
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
public class AjaxFamilyMember extends HttpServlet {

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
	if(this.dataFor.equals("showfamilymemberform")){
	  this.htmlReturn = familyMemberForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
	this.iErrCode = ctrlFamilyMember.action(this.iCommand, this.empId, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlFamilyMember.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
	this.iErrCode = ctrlFamilyMember.action(this.iCommand, this.empId, this.oid, request, empName, userId, this.oidDelete);
	String message = ctrlFamilyMember.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
        this.iErrCode = ctrlFamilyMember.action(this.iCommand, this.empId, this.oid, request, empName, userId, this.oidDelete);
        String message = ctrlFamilyMember.getMessage();
        this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listfamilymember")){
	    String[] cols = { PstFamilyMember.fieldNames[PstFamilyMember.FLD_FAMILY_MEMBER_ID],
		PstFamilyMember.fieldNames[PstFamilyMember.FLD_FULL_NAME],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_SEX], 
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_GUARANTEED],
		PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_JOB],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_ADDRESS],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_CARD_ID],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_EDUCATION_ID],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELIGION_ID],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_NO_TELP],
                PstFamilyMember.fieldNames[PstFamilyMember.FLD_BPJS_NO]};

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
        
        if(dataFor.equals("listfamilymember")){
	    whereClause += " ("+PstFamilyMember.fieldNames[PstFamilyMember.FLD_FULL_NAME]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_JOB]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_ADDRESS]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_CARD_ID]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstEducation.fieldNames[PstEducation.FLD_EDUCATION]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_CARD_ID]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstReligion.fieldNames[PstReligion.FLD_RELIGION]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_NO_TELP]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_BPJS_NO]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR IF("+PstFamilyMember.fieldNames[PstFamilyMember.FLD_SEX]+" = "
                        + PstEmployee.sexValue[PstEmployee.MALE]+ ", '"+PstEmployee.sexKey[PstEmployee.MALE]+"' , "
                        + "'"+PstEmployee.sexKey[PstEmployee.FEMALE]+"') LIKE '%"+this.searchTerm+"%'"
                    + " OR IF("+PstFamilyMember.fieldNames[PstFamilyMember.FLD_GUARANTEED]+" = 0, 'NO', 'YES') LIKE '%"+this.searchTerm+"%')"
                    + " AND " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+ " LIKE '%"+this.empId+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listfamilymember")){
	    total = PstFamilyMember.getCountDataTable(whereClause);
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
        FamilyMember famMember = new FamilyMember();
        Education education = new Education();
        Religion religion = new Religion();
        FamRelation famRelation = new FamRelation();
	String whereClause = "";
        String order ="";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listfamilymember")){
               whereClause += " ("+PstFamilyMember.fieldNames[PstFamilyMember.FLD_FULL_NAME]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_JOB]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_ADDRESS]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_CARD_ID]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstEducation.fieldNames[PstEducation.FLD_EDUCATION]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_CARD_ID]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstReligion.fieldNames[PstReligion.FLD_RELIGION]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_NO_TELP]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_BPJS_NO]+ " LIKE '%"+this.searchTerm+"%'"
                    + " OR IF("+PstFamilyMember.fieldNames[PstFamilyMember.FLD_SEX]+" = "
                        + PstEmployee.sexValue[PstEmployee.MALE]+ ", '"+PstEmployee.sexKey[PstEmployee.MALE]+"' , "
                        + "'"+PstEmployee.sexKey[PstEmployee.FEMALE]+"') LIKE '%"+this.searchTerm+"%'"
                    + " OR IF("+PstFamilyMember.fieldNames[PstFamilyMember.FLD_GUARANTEED]+" = 0, 'NO', 'YES') LIKE '%"+this.searchTerm+"%')"                       
                    + " AND " +PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+ " LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listfamilymember")){
	    listData = PstFamilyMember.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listfamilymember")){
		famMember = (FamilyMember) listData.get(i);
                try {
                    education = PstEducation.fetchExc(famMember.getEducationId());
                } catch (Exception exc){}
                
                try{
                   religion = PstReligion.fetchExc(famMember.getReligionId()); 
                } catch (Exception exc){}
                
                try {
                    famRelation = PstFamRelation.fetchExc(Long.valueOf(famMember.getRelationship()));
                } catch (Exception exc){}
                
                if(privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FAMILY_MEMBER_ID]+"' class='"+FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FAMILY_MEMBER_ID]+"' value='"+famMember.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }
		ja.put(""+(this.start+i+1));
		ja.put(""+famMember.getFullName()+" / "+PstEmployee.sexKey[famMember.getSex()] );
                ja.put(""+famRelation.getFamRelation());
		ja.put(famMember.getGuaranteed()==true ? "Yes" : "No");
                ja.put(""+(famMember.getBirthDate() == null ? "-" : famMember.getBirthDate()));
                ja.put(""+famMember.getJob()+ " / " + famMember.getJobPlace());
                ja.put(""+famMember.getCardId());
                ja.put(""+education.getEducation() + " / " + religion.getReligion());
                ja.put(""+famMember.getNoTelp());
                ja.put(""+famMember.getBpjsNum());
                		
		String buttonUpdate = "";
		if(privUpdate){
		    buttonUpdate = "<button class='btn btn-success btneditgeneral btn-xs' data-oid='"+famMember.getOID()+"' data-for='showfamilymemberform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+famMember.getOID()+"' data-for='deleteFamMemberSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></button>");
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
    
    public String familyMemberForm(){
	
	//CHECK DATA
	FamilyMember famMember = new FamilyMember();
	
	if(this.oid != 0){
	    try{
		famMember = PstFamilyMember.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        
        Vector relation_key = new Vector(1,1);
        Vector relation_val = new Vector(1,1);
        
        relation_key.add("");
        relation_val.add("Select Relation...");
        Vector listRelation = PstFamRelation.listAll();
        for (int i = 0; i < listRelation.size(); i++){
            FamRelation famRelation = (FamRelation) listRelation.get(i);
            relation_key.add(""+famRelation.getOID());
            relation_val.add(""+famRelation.getFamRelation());
        }
        
        Vector edu_key = new Vector(1,1);
        Vector edu_val = new Vector(1,1);
        
        edu_key.add("");
        edu_val.add("Select Education...");
        Vector listEducation = PstEducation.listAll();
        for (int i = 0; i < listEducation.size(); i++){
            Education education = (Education) listEducation.get(i);
            edu_key.add(""+education.getOID());
            edu_val.add(""+education.getEducation());
        }
        
        Vector rel_key = new Vector(1,1);
        Vector rel_val = new Vector(1,1);
        
        rel_key.add("");
        rel_val.add("Select Relegion...");
        Vector listReligion = PstReligion.listAll();
        for (int i = 0; i < listReligion.size(); i++){
            Religion religion = (Religion) listReligion.get(i);
            rel_key.add(""+religion.getOID());
            rel_val.add(""+religion.getReligion());
        }
        
        String check = "checked";
        
        
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FAMILY_MEMBER_ID]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FAMILY_MEMBER_ID]+"' class='form-control' value='"+famMember.getOID()+"'>"
                + "<div class='form-group'>"
		    + "<label>Full Name *</label>"
		    + "<input type='text' placeholder='Type Full Name' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME]+"' class='form-control' value='"+famMember.getFullName()+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Sex</label>"
                + "<div id='mRadio' class='form-control'>";
                    for (int i = 0; i < PstEmployee.sexValue.length; i++) {
                        String str = "";
                        if (famMember.getSex() == PstEmployee.sexValue[i]) {
                            str = "checked";
                        }
		    returnData +=  "<label class='radio-inline'>"
                            + "<input type='radio' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_SEX]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_SEX]+"' value='"+PstEmployee.sexValue[i]+"'"+str+" >" + PstEmployee.sexKey[i] +"</label>";
                    }
                returnData += "</div>"
                        + "</div>"
		+ "<div class='form-group'>"
		    + "<label>Relationship *</label>"
		    + ""+ControlCombo.draw(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP], null, ""+famMember.getRelationship()+"", relation_key, relation_val, "id="+FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP], "form-control")+" "
		+ "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Guaranteed</label>"
                    + "<div class='checkbox'>"
		    + "<label><input type='checkbox' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]+"' value='1' "+ (famMember.getGuaranteed() ? "checked" : "")+"> Yes</label>"
		+ "</div>"
		+ "</div>"        
                + "<div class='form-group'>"
		    + "<label>Date of Birth</label>"
		    + "<input type='text' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]+"' class='form-control datepicker' value='"+(famMember.getBirthDate()== null ? Formater.formatDate(new Date(), "yyyy-MM-dd") : Formater.formatDate(famMember.getBirthDate(),"yyyy-MM-dd"))+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Job</label>"
		    + "<input type='text' placeholder='Input Job' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB]+"' class='form-control' value='"+famMember.getJob()+"'>"
		+ "</div>"                
                + "<div class='form-group'>"
		    + "<label>Job Address</label>"
		    + "<textarea rows='5' placeholder='Input Job Address' class='form-control' id='" + FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB_PLACE] +"' name='" + FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB_PLACE] +"'>"+ famMember.getJobPlace()+"</textarea> "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>ID Card</label>"
		    + "<input type='text' placeholder='Input ID Card' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_CARD_ID]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_CARD_ID]+"' class='form-control' value='"+(famMember.getCardId().equals("0") ?"":famMember.getCardId())+"'>"
		+ "</div>" 
                + "<div class='form-group'>"
		    + "<label>Education</label>"
		    + ""+ControlCombo.draw(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_EDUCATION_ID], null, ""+famMember.getEducationId()+"", edu_key, edu_val, "", "form-control")+" "
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>Religion</label>"
		    + ""+ControlCombo.draw(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELIGION_ID], null, ""+famMember.getReligionId()+"", rel_key, rel_val, "", "form-control")+" "
		+ "</div>" 
                + "<div class='form-group'>"
		    + "<label>Phone Number</label>"
		    + "<input type='text' placeholder='Input Phone Number' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_NO_TELP]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_NO_TELP]+"' class='form-control' value='"+(famMember.getNoTelp() ==0 ?"":famMember.getNoTelp())+"'>"
		+ "</div>"
                + "<div class='form-group'>"
		    + "<label>BPJS Number</label>"
		    + "<input type='text' placeholder='Input Bpjs Number' name='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BPJS_NUM]+"'  id='"+ FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BPJS_NUM]+"' class='form-control' value='"+famMember.getBpjsNum()+"'>"
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
    