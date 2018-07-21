/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.masterdata;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleCategory;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.form.masterdata.CtrlScheduleSymbol;
import com.dimata.harisma.form.masterdata.CtrlScheduleSymbol;
import com.dimata.harisma.form.masterdata.FrmScheduleSymbol;
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
 * @author IPAG
 */
public class AjaxScheduleSymbol extends HttpServlet {

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
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    //private int enableReplaceExistingSchedule = 0;
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
	
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
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showsymbolform")){
	    this.htmlReturn = schsymbolForm();
	}
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlScheduleSymbol ctrlSchedulesymbol = new CtrlScheduleSymbol(request);
	this.iErrCode = ctrlSchedulesymbol.action(this.iCommand, this.oid ,request, this.oidDelete);
	String message = ctrlSchedulesymbol.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlScheduleSymbol ctrlSchedulesymbol = new CtrlScheduleSymbol(request);
	this.iErrCode = ctrlSchedulesymbol.action(this.iCommand, this.oid,request, this.oidDelete);
	String message = ctrlSchedulesymbol.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlScheduleSymbol ctrlSchedulesymbol = new CtrlScheduleSymbol(request);
	this.iErrCode = ctrlSchedulesymbol.action(this.iCommand, this.oid,request, this.oidDelete);
	String message = ctrlSchedulesymbol.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listschsymbol")){
	    String[] cols = { PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID],
                PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID],
		PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE],
		PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN],
                PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT],
                PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT],
                PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN],
                PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN],
                PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_MAX_ENTITLE]
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
        
        if(dataFor.equals("listschsymbol")){
	    whereClause += " ("+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listschsymbol")){
	    total = PstScheduleSymbol.getCount(whereClause);
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
        ScheduleSymbol schsymbol = new ScheduleSymbol();
        ScheduleCategory schecategory =  new ScheduleCategory();
        Vector lsLL = new Vector(1,1);
		lsLL = PstScheduleCategory.listAll();
	
	String whereClause = "";
        String order ="";
	///boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listschsymbol")){
               whereClause += " ("+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]+" LIKE '%"+this.searchTerm+"%' "
		    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT]+" LIKE '%"+this.searchTerm+"%'"
                    + "OR "+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listschsymbol")){
	    listData = PstScheduleSymbol.list(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listschsymbol")){
		schsymbol = (ScheduleSymbol) listData.get(i);
                int scheduleCat = PstScheduleCategory.CATEGORY_ALL;
                
                String strNote = " max entitle : "+schsymbol.getMaxEntitle()
                                    +", periode : "+ (schsymbol.getPeriodeType() == PstScheduleSymbol.PERIODE_TYPE_TIME_AT_ALL ? "" : "per ") 
                                    + schsymbol.getPeriode() + " "
                                    + PstScheduleSymbol.fieldNamesPeriodeType[schsymbol.getPeriodeType()]
                                    +", and may be taken after "+schsymbol.getMinService()+" month of service";
                try{
                    schecategory = PstScheduleCategory.fetchExc(schsymbol.getScheduleCategoryId());
                    
                }catch (Exception exc){}
                
                String str_dt_TimeIn = ""; 
			try
			{
				Date dt_TimeIn = schsymbol.getTimeIn();
				if(dt_TimeIn==null)
				{
					//dt_TimeIn = new Date();
                                    str_dt_TimeIn = " Not to Be Set "; 
				}else{
                                    str_dt_TimeIn = Formater.formatTimeLocale(dt_TimeIn);
                                }
			}
                        catch(Exception e)
			{ 
				//str_dt_TimeIn = ""; 
                                str_dt_TimeIn = " Not to Be Set "; 
			}
                        String str_dt_TimeOut = ""; 
			try
			{
				Date dt_TimeOut = schsymbol.getTimeOut();
				if(dt_TimeOut==null)
				{
					//dt_TimeOut = new Date();
                                     str_dt_TimeOut = " Not to Be Set "; 
				}else{
				str_dt_TimeOut = Formater.formatTimeLocale(dt_TimeOut);
                                }
			}
			catch(Exception e)
			{ 
				str_dt_TimeOut = " Not to Be Set "; 
			}
                        
			String str_dt_BreakOut = ""; 
			try
			{
				Date dt_BreakOut = schsymbol.getBreakOut();
				if(dt_BreakOut==null)
				{
					//dt_BreakOut = new Date();
                                    str_dt_BreakOut = " Not to Be Set "; 
				}else{
				str_dt_BreakOut = Formater.formatTimeLocale(dt_BreakOut);
                                }
			}
                        catch(Exception e)
			{ 
				 str_dt_BreakOut = " Not to Be Set "; 
			}

			String str_dt_BreakIn = ""; 
			try
			{
				Date dt_BreakIn = schsymbol.getBreakIn();
				if(dt_BreakIn==null)
				{
					//dt_BreakIn = new Date();
                                    str_dt_BreakIn =  " Not to Be Set ";
				}else{
				str_dt_BreakIn = Formater.formatTimeLocale(dt_BreakIn);
                                 }
			}
			catch(Exception e)
			{ 
				str_dt_BreakIn =  " Not to Be Set ";
			}
                String checkButton = "<input type='checkbox' name='"+FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE_ID]+"' class='"+FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE_ID]+"' value='"+schsymbol.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
                ja.put(""+schsymbol.getSymbol());
                for (int j = 0; j < lsLL.size(); j++) 
			{
				ScheduleCategory cat = (ScheduleCategory) lsLL.get(j);
				if (cat.getOID() == schsymbol.getScheduleCategoryId()) 
				{
					ja.put(""+schecategory.getCategory());
					scheduleCat = cat.getCategoryType();
				}
			}
                ja.put(""+schsymbol.getSchedule());
                if(scheduleCat == PstScheduleCategory.CATEGORY_REGULAR
			   || scheduleCat == PstScheduleCategory.CATEGORY_SPLIT_SHIFT
			   || scheduleCat == PstScheduleCategory.CATEGORY_NIGHT_WORKER
   			   || scheduleCat == PstScheduleCategory.CATEGORY_ACCROSS_DAY
			   || scheduleCat == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY
			   || scheduleCat == PstScheduleCategory.CATEGORY_NEAR_ACCROSS_DAY			
			   || scheduleCat == PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF)			
			{
                            ja.put(""+str_dt_TimeIn);
                            ja.put(""+str_dt_BreakOut);
                            ja.put(""+str_dt_BreakIn);
                            ja.put(""+str_dt_TimeOut);
			}
			else
			{
                            ja.put("-");
                            ja.put("-");
                            ja.put("-");
                            ja.put("-");
			}
                
                
                
                ja.put(strNote);
                
                
		String buttonUpdate = "";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+schsymbol.getOID()+"' data-for='showsymbolform' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+schsymbol.getOID()+"' data-for='deletesymbol' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    public String schsymbolForm(){
	
	//CHECK DATA
	ScheduleSymbol schsymbol = new ScheduleSymbol();
	
	if(this.oid != 0){
	    try{
		schsymbol = PstScheduleSymbol.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector locationid_value = new Vector(1,1);
        Vector locationid_key = new Vector(1,1);
        String sel_locationid = "" + (schsymbol.getScheduleCategoryId()); //locker.getLocationId();

        //Untuk vector category
        Vector list = new Vector(1,1);
        list = PstScheduleCategory.listAll();
        int wd = schsymbol.getWorkDays();
        int nilai = schsymbol.getNightAllowance();
        int val = schsymbol.getTransportAllowance();
    /*    Vector vSpecialLeaveOid = new Vector(1,1);
        for(int i=0;i<list.size();i++){
            ScheduleCategory scheduleCategory = (ScheduleCategory) list.get(i);
            if(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE==scheduleCategory.getCategoryType()){
                vSpecialLeaveOid.add(String.valueOf(scheduleCategory.getOID()));
            }
        }
      */  
        for (int i = 0; i < list.size(); i++) {
                ScheduleCategory scheduleCategory = (ScheduleCategory) list.get(i);
                locationid_key.add(scheduleCategory.getCategory());
                locationid_value.add(String.valueOf(scheduleCategory.getOID()));
        }
        
        Vector periodeTypeValue = new Vector(1,1);
        Vector periodeTypeKey = new Vector(1,1);
        String periodeTypeSelect = String.valueOf(schsymbol.getPeriodeType());
        for (int i = 0; i < PstScheduleSymbol.fieldNamesPeriodeType.length; i++) {
                periodeTypeKey.add(PstScheduleSymbol.fieldNamesPeriodeType[i]);
                periodeTypeValue.add(String.valueOf(i));
        }
          
        String display = "style='display:none'";
        String jam = "";
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        if(schsymbol.getTimeIn()!=null){
        jam = formatter.format(schsymbol.getTimeIn()); 
        }
        if (!"00:00:00".equals(jam) || schsymbol.getBreakIn()!=null) {
        display = "";
        }else{
        display = "style='display:none'";   
        }
	String returnData = ""
	+ "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<input type='hidden' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE_ID]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE_ID]+"' class='form-control' value='"+schsymbol.getOID()+"'>"
		+ "<div class='form-group'>"
		    + "<label>Schedule Category</label>"
                    + ControlCombo.draw(FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE_CATEGORY_ID],null, sel_locationid, locationid_value, locationid_key," class='form-control' onChange='javascript:cmdCheckCategory()'")
		+ "</div>"
                
                + "<div class='form-group'>"
		    + "<label>Symbol</label>"
		    + "<input type='text' placeholder='input symbol schedule...' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SYMBOL]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SYMBOL]+"' class='form-control' value='"+schsymbol.getSymbol()+"'>"
		+ "</div>"
                
                + "<div class='form-group'>"
		    + "<label>Schedule</label>"
		    + "<input type='text' placeholder='input schedule name...' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE]+"' class='form-control' value='"+schsymbol.getSchedule()+"'>"
		+ "</div>"
                
                + "<div class='form-group'>"
		    + "<label>Time Symbol</label>"
                    + "<div class='form-group'>";
                    if(!"00:00:00".equals(jam))
                    {
		    returnData+= " Yes <input type=\'radio\' checked='checked' onclick=\'javascript:yesnoCheck();\' name=\'yesno\' id=\'yesCheck\'> No <input type=\'radio\'  onclick=\'javascript:yesnoCheck();\' name=\'yesno\' id=\'noCheck\'><br>";
                    }else{
                    display = "style='display:none'";
                    returnData+= " Yes <input type=\'radio\' onclick=\'javascript:yesnoCheck();\' name=\'yesno\'  id=\'yesCheck\'> No <input type=\'radio\' checked='checked' onclick=\'javascript:yesnoCheck();\' name=\'yesno\' id=\'noCheck\'><br>";    
                    }
                    returnData+= "</div>"
                    + "<div id='ifYes' "+display+">"
                
                            + "<div class='form-group'>"
                            + "<label>Time IN - Time OUT</label>"
                            + "<div class='input-group'>"
                                + "<input type='text' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TIME_IN_STRING]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TIME_IN_STRING]+"' class='form-control datetimepicker' value='"+(schsymbol.getTimeIn()== null ? "00:00" : schsymbol.getTimeIn())+"'>"
                                + "<div class='input-group-addon'>"
                                    + "To"
                                + "</div>"
                                + "<input type='text' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TIME_OUT_STRING]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TIME_OUT_STRING]+"' class='form-control datetimepicker' value='"+(schsymbol.getTimeOut()== null ? "00:00" : schsymbol.getTimeOut())+"'>"
                            + "</div>"
                            + "</div>"

                            + "<div class='form-group'>"
                            + "<label>Break Out - Break IN</label>"
                            + "<div class='input-group'>"
                                + "<input type='text' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_BREAK_OUT_STRING]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_BREAK_OUT_STRING]+"' class='form-control datetimepicker' value='"+(schsymbol.getBreakOut()== null ? "00:00" : schsymbol.getBreakOut())+"'>"
                                + "<div class='input-group-addon'>"
                                    + "To"
                                + "</div>"
                                + "<input type='text' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_BREAK_IN_STRING]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_BREAK_IN_STRING]+"' class='form-control datetimepicker' value='"+(schsymbol.getBreakIn()== null ? "00:00" : schsymbol.getBreakIn())+"'>"
                            + "</div>"
                            + "</div>"
                
                            + "<div class='form-group'>"
                            + "<label>Transport Allowance - Night Allowance</label>"
                            + "<div class='input-group'>"
                                +"<select class='form-control' name='"+FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TRANSPORT_ALLOWANCE]+"'>" ;
                                         if (nilai != 0 && nilai > 0){
                                           for(int i=0;i<=3;i++){ 
                                               if (i == nilai){
                                                    returnData += "<option value='"+i+"' selected='selected'>'"+i+"'</option>";
                                                } else {
                                                    returnData += "<option value='"+i+"'>'"+i+"'</option>";
                                             }                                                                                
                                           }
                                       } else {
                                            returnData += "<option value='0'>0</option>"+"<option value='1'>1</option>"+"<option value='2'>2</option>";
                                       }

                                  returnData += "</select>"
                                + "<div class='input-group-addon'>"
                                    + "-"
                                + "</div>"
                                +"<select class='form-control' name='"+FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_NIGHT_ALLOWANCE]+"'>" ;
                                         if (val != 0 && val > 0){
                                           for(int i=0;i<=3;i++){ 
                                               if (i == val){
                                                    returnData += "<option value='"+i+"' selected='selected'>'"+i+"'</option>";
                                                } else {
                                                    returnData += "<option value='"+i+"'>'"+i+"'</option>";
                                             }                                                                                
                                           }
                                       } else {
                                            returnData += "<option value='0'>0</option>"+"<option value='1'>1</option>"+"<option value='2'>2</option>";
                                       }

                                  returnData += "</select>"
                            + "</div>"
                            + "</div>"
                
                    + "</div>"
                + "</div>"
		+ "<div class='form-group'>"
		    + "<label>Work Days</label>"
		    +"<select class='form-control' name='"+FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_WORK_DAYS]+"'>" ;
                             if (wd != 0 && wd > 0){
                               for(int i=1;i<=2;i++){ 
                                   if (i == wd){
                                        returnData += "<option value='"+i+"' selected='selected'>"+i+"</option>";
                                    } else {
                                        returnData += "<option value='"+i+"'>"+i+"</option>";
                                 }                                                                                
                               }
                           } else {
                                returnData += "<option value='1'>1</option>"+"<option value='2'>2</option>";
                           }

                      returnData += "</select>"
		+ "</div>" 
                              
                + "<div class='form-group'>"
		    + "<label>Max Entitle</label>"
		    + "<input type='text' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_MAX_ENTITLE]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_MAX_ENTITLE]+"' class='form-control' value='"+schsymbol.getMaxEntitle()+"'>"
                + "</div>"
                              
                + "<div class='form-group'>"
                + "<label>Period</label>"
                + "<div class='input-group'>"
                    + "<input type='text' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_PERIODE]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_PERIODE]+"' class='form-control ' value='"+(schsymbol.getPeriode())+"'>"
                    + "<div class='input-group-addon'>"
                       + "Per"
                    + "</div>"
                    + ControlCombo.draw(FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_PERIODE_TYPE],null, periodeTypeSelect, periodeTypeValue, periodeTypeKey," class='form-control'")
                
                + "</div>"
                + "</div>"
                
                + "<div class='form-group'>"
		    + "<label>May Taken After</label>"
		    + "<input type='text' name='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_MIN_SERVICE]+"'  id='"+ FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_MIN_SERVICE]+"' class='form-control' value='"+schsymbol.getMinService()+"'>"
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
