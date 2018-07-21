/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.payroll;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.payroll.CustomRptMain;
import com.dimata.harisma.entity.payroll.PstCustomRptMain;
import com.dimata.harisma.form.payroll.CtrlCustomRptMain;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.CustomRptConfig;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PstCustomRptConfig;
import com.dimata.harisma.entity.payroll.PstPayComponent;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.harisma.form.payroll.CtrlCustomRptConfig;
import com.dimata.harisma.form.payroll.FrmCustomRptMain;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AjaxCustomRptConfig extends HttpServlet {


    
    //LONG
    private long oid = 0;
    private long oidCustomConfig = 0;
    private long oidReturn = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    private String inpCombine = "";
    private String inpCombineShow = "";
    private String inpOperator = "";
    private String inpAlias = "";
    
    //JSON
    JSONObject jSONObject;
    
    
    //INT
    private int iCommand = 0;
    private int tblIdx = 0;
    private int iErrCode = 0;
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
        this.oidCustomConfig = FRMQueryString.requestLong(request, "oid_custom_config");
	this.oidReturn=0;
        
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        
	//INT
	this.iCommand = FRMQueryString.requestCommand(request);
        this.tblIdx = FRMQueryString.requestInt(request, "tbl_idx");
	this.iErrCode = 0;
	
	this.jSONObject = new JSONObject();
	switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
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
	if(this.dataFor.equals("showCustomRptConfig")){
	  this.htmlReturn = customRptConfigForm();
	} else if(this.dataFor.equals("showTable")){
          this.htmlReturn = showTable();
        } else if(this.dataFor.equals("showTableComponent")){
          this.htmlReturn = showTableComponent();  
        } else if(this.dataFor.equals("showTableDataCombine")){
            this.htmlReturn = showTableDataCombine();
        } else if(this.dataFor.equals("showTableDataSubTotal")){
            this.htmlReturn = showTableDataSubTotal();
        } else if(this.dataFor.equals("combinePlus")){
            this.htmlReturn = combinePlus(request);
        } else if(this.dataFor.equals("combineMin")){
            this.htmlReturn = combineMin(request);
        } else if(this.dataFor.equals("showTableFilter")){
            this.htmlReturn = showTableFilter();
        } else if(this.dataFor.equals("showTableSort")){
            this.htmlReturn = showTableSort();
        } else if (this.dataFor.equals("showTableGroup")){
            this.htmlReturn = showTableGroup();
        }
    }
    
    public void commandSave(HttpServletRequest request){
        String commandCustom = FRMQueryString.requestString(request, "FRM_FIELD_DATA_SAVE");
        String[] fieldName = FRMQueryString.requestStringValues(request, "field_name");
        String[] fieldBenefit = FRMQueryString.requestStringValues(request, "field_benefit");
        String[] fieldDeduction = FRMQueryString.requestStringValues(request, "field_deduction");
        String[] fieldDataList = FRMQueryString.requestStringValues(request, "field_data_list");
        int showGroup = FRMQueryString.requestInt(request, "show_group");
        
        CustomRptConfig customRptConfig = new CustomRptConfig();
	if (commandCustom != null && commandCustom.length() > 0){
            if (commandCustom.equals("save_field_data_list")){
                if (fieldName != null) {
                    for (int i = 0; i < fieldName.length; ++i) {
                        customRptConfig.setRptConfigTableGroup(PstCustomRptConfig.realTableList[tblIdx]);
                        customRptConfig.setRptConfigTableName(PstCustomRptConfig.tableSystem[tblIdx][Integer.valueOf(fieldName[i])]);
                        customRptConfig.setRptConfigFieldName(PstCustomRptConfig.showFieldSystem[tblIdx][Integer.valueOf(fieldName[i])]);
                        customRptConfig.setRptConfigFieldHeader(PstCustomRptConfig.showFieldList[tblIdx][Integer.valueOf(fieldName[i])]);
                        customRptConfig.setRptConfigFieldType(PstCustomRptConfig.fieldTypeSystem[tblIdx][Integer.valueOf(fieldName[i])]);
                        customRptConfig.setRptConfigDataType(0);
                        customRptConfig.setRptConfigDataGroup(0);
                        customRptConfig.setRptMainId(this.oid);
                        try {
                            PstCustomRptConfig.insertExc(customRptConfig);
                        } catch(Exception e){
                            System.out.print(""+e.toString());
                        }
                    }
                }
            }
            if (commandCustom.equals("save_component")){
                if (fieldBenefit != null && fieldBenefit.length > 0){
                    for(int i=0; i<fieldBenefit.length; i++){
                        PayComponent dataComp1 = new PayComponent();
                        try {
                            dataComp1 = PstPayComponent.fetchExc(Long.valueOf(fieldBenefit[i]));
                        } catch(Exception e){
                            System.out.print(""+e.toString());
                        }
                        customRptConfig.setRptConfigTableGroup(PstPaySlip.TBL_PAY_SLIP);
                        customRptConfig.setRptConfigTableName(PstPaySlipComp.TBL_PAY_SLIP_COMP);
                        customRptConfig.setRptConfigFieldName(dataComp1.getCompCode());
                        customRptConfig.setRptConfigFieldHeader(dataComp1.getCompName());
                        customRptConfig.setRptConfigFieldType(1);
                        customRptConfig.setRptConfigDataType(2);
                        customRptConfig.setRptConfigDataGroup(0);
                        customRptConfig.setRptMainId(oid);
                        try{
                            PstCustomRptConfig.insertExc(customRptConfig);
                        } catch(Exception e){
                            System.out.print(""+e.toString());
                        }
                    }
                    customRptConfig = new CustomRptConfig();
                }
                if (fieldDeduction != null && fieldDeduction.length > 0){
                    for(int j=0; j<fieldDeduction.length; j++){
                        PayComponent dataComp2 = new PayComponent();
                        try {
                            dataComp2 = PstPayComponent.fetchExc(Long.valueOf(fieldDeduction[j]));
                        } catch(Exception e){
                            System.out.print(""+e.toString());
                        }
                        customRptConfig.setRptConfigTableGroup(PstPaySlip.TBL_PAY_SLIP);
                        customRptConfig.setRptConfigTableName(PstPaySlipComp.TBL_PAY_SLIP_COMP);
                        customRptConfig.setRptConfigFieldName(dataComp2.getCompCode());
                        customRptConfig.setRptConfigFieldHeader(dataComp2.getCompName());
                        customRptConfig.setRptConfigFieldType(1);
                        customRptConfig.setRptConfigDataType(2);
                        customRptConfig.setRptConfigDataGroup(0);
                        customRptConfig.setRptMainId(oid);
                        try {
                            PstCustomRptConfig.insertExc(customRptConfig);
                        } catch(Exception e){
                            System.out.print(""+e.toString());
                        }
                    }
                }
            }
            if (commandCustom.equals("submit_combine")){
                this.inpAlias = FRMQueryString.requestString(request, "inp_alias");
                customRptConfig.setRptConfigTableGroup("0"+this.inpOperator+"0");
                customRptConfig.setRptConfigTableName(PstPaySlipComp.TBL_PAY_SLIP_COMP);
                customRptConfig.setRptConfigFieldName(this.inpCombine);
                customRptConfig.setRptConfigFieldHeader(this.inpAlias);
                customRptConfig.setRptConfigFieldType(1);
                customRptConfig.setRptConfigDataType(showGroup);
                customRptConfig.setRptConfigDataGroup(0);
                customRptConfig.setRptMainId(oid);
                try{
                    PstCustomRptConfig.insertExc(customRptConfig);
                } catch(Exception e){
                    System.out.print(""+e.toString());
                }
                this.inpCombine = "";
                this.inpAlias = "";
            }
            if (commandCustom.equals("save_subtotal")){
            /* select_func, select_item, select_total_by */
            /*
            RPT_CONFIG_DATA_TYPE = 3
            RPT_CONFIG_DATA_GROUP = 0
            RPT_CONFIG_TABLE_GROUP = COUNT / SUM
            RPT_CONFIG_TABLE_NAME = hr_section
            RPT_CONFIG_FIELD_NAME = SECTION
            RPT_CONFIG_FIELD_TYPE = 0
            RPT_CONFIG_FIELD_HEADER = RECORD

            Query = COUNT(hr_section.SECTION)
            View = COUNT(RECORD) BY SECTION
            */
            int selectFunc = FRMQueryString.requestInt(request, "select_func");
            String selectItem = FRMQueryString.requestString(request, "select_item");
            String selectTotalBy = FRMQueryString.requestString(request, "select_total_by");
            String func = "";
            if (selectFunc > 0){
                if (selectFunc == 1){
                    func = "COUNT";
                } else {
                    func = "SUM";
                }
            }
            String headerName = "";
            String whereComp = " COMP_CODE='"+selectItem+"' ";
            Vector listComponent = PstPayComponent.list(0, 1, whereComp, "");
            if (listComponent != null && listComponent.size() > 0){
                for (int lc=0; lc<listComponent.size(); lc++){
                    PayComponent pcomp = (PayComponent)listComponent.get(lc);
                    headerName = pcomp.getCompName();
                }
            } else {
                headerName = selectItem;
            }
            customRptConfig.setRptConfigTableGroup(func);
            customRptConfig.setRptConfigTableName(selectTotalBy);
            customRptConfig.setRptConfigFieldName(selectItem);
            customRptConfig.setRptConfigFieldHeader(headerName);
            customRptConfig.setRptConfigFieldType(selectFunc);
            customRptConfig.setRptConfigDataType(3);
            customRptConfig.setRptConfigDataGroup(0);
            customRptConfig.setRptMainId(this.oid);
            try {
                PstCustomRptConfig.insertExc(customRptConfig);
            } catch(Exception e){
                System.out.print(""+e.toString());
            }
            selectTotalBy = "";
            selectItem = "";
            selectFunc = 0;
        }
        if (commandCustom.equals("save_field_other")){
            /*
            * 1 = WHERE, 2 = ORDER BY, 3 = GROUP BY
            */
            if (fieldDataList != null){
                for(int i=0; i<fieldDataList.length; i++){
                    try{
                        CustomRptConfig dataCustom = PstCustomRptConfig.fetchExc(Long.valueOf(fieldDataList[i]));
                        customRptConfig.setRptConfigTableGroup(dataCustom.getRptConfigTableGroup());
                        customRptConfig.setRptConfigTableName(dataCustom.getRptConfigTableName());
                        customRptConfig.setRptConfigFieldName(dataCustom.getRptConfigFieldName());
                        customRptConfig.setRptConfigFieldHeader(dataCustom.getRptConfigFieldHeader());
                        customRptConfig.setRptConfigFieldType(dataCustom.getRptConfigFieldType());
                        customRptConfig.setRptConfigDataType(0);
                        customRptConfig.setRptConfigDataGroup(showGroup);
                        customRptConfig.setRptMainId(this.oid);
                        PstCustomRptConfig.insertExc(customRptConfig);
                    } catch (Exception exc){
                        
                    }
                    
                }
            }
            
        }    
        }
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlCustomRptMain ctrlCustomRptMain = new CtrlCustomRptMain(request);
	this.iErrCode = ctrlCustomRptMain.action(this.iCommand, this.oid, this.oidDelete);
	String message = ctrlCustomRptMain.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlCustomRptConfig ctrlCustomRptConfig = new CtrlCustomRptConfig(request);
	this.iErrCode = ctrlCustomRptConfig.action(this.iCommand, this.oid);
	String message = ctrlCustomRptConfig.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public String combinePlus(HttpServletRequest request){
        String component = FRMQueryString.requestString(request, "component");
        String [] combineComponent = component.split(",");
        String combinePlus = "";
        String combineKoma1 = "";
        String opr = "";
        String returnData = "";
        this.inpCombineShow = "";
        if(combineComponent !=null && combineComponent.length > 0){
            for(int i=0; i<combineComponent.length; i++){
                combinePlus +=combineComponent[i];
                combineKoma1 +=combineComponent[i];

                opr +=",";

                if(i==combineComponent.length-1){
                    combinePlus +="";
                } else {
                    combinePlus +="+";
                    combineKoma1 +=",";
                    opr +="+";
                }
            }
        }
        if (this.inpCombine.length() > 0){
            this.inpCombine += ","+combineKoma1;
            this.inpCombineShow += "+"+combinePlus;
            this.inpOperator += "+"+opr;
        } else {
            this.inpCombine += combineKoma1;
            this.inpCombineShow += combinePlus;
            this.inpOperator += opr;
        } 
        return this.inpCombineShow;
    }
    
    public String combineMin(HttpServletRequest request){
        String component = FRMQueryString.requestString(request, "component");
        String [] combineComponent = component.split(",");
        String combineMinus = "";
        String combineKoma2 = "";
        String opr1 = "";
        String returnData = "";
        this.inpCombineShow = "";
        if(combineComponent !=null && combineComponent.length > 0){
            for(int i=0; i<combineComponent.length; i++){
                combineMinus +=combineComponent[i];
                combineKoma2 += combineComponent[i];

                opr1 += ",";


                if(i==combineComponent.length-1){
                    combineMinus +="";
                } else {
                    combineMinus +="-";
                    combineKoma2 += ",";
                    opr1 +="-";
                }
            }
        }
        if (this.inpCombine.length() > 0){
            this.inpCombine += ","+combineKoma2;
            this.inpCombineShow += "-"+combineMinus;
            this.inpOperator += "-"+opr1;
        } else {
            this.inpCombine += combineKoma2;
            this.inpCombineShow += combineMinus;
            this.inpOperator += opr1;
        }
        return this.inpCombineShow;
    }
    
    public String customRptConfigForm(){
	
	String returnData=""
                + "<div class='col-md-3'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Data List</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<div class='row'>"
                        + "<div class='col-md-12'>";
                        for(int i=0; i<PstCustomRptConfig.showTableList.length; i++){
                returnData += "<div class='row'>"
                            + "<div class='col-md-12'>"
                                + "<button class='btn btn-default btnadd' data-idx='"+i+"' data-oid='"+this.oid+"' data-for='showTable' data-save='save_field_data_list'>"+PstCustomRptConfig.showTableList[i]+"</button>"
                            + "</div>"
                            + "</div>"
                        +    "<div class='row'>"
                            + "<div class='col-md-2'>"
                            + "</div>"
                                + "<div class='col-md-10'>"
                                    + "<table class='table-bordered'>";
                                        String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[i]+"' AND RPT_MAIN_ID = "+oid+" ";
                                        String tdWarna = "";
                                        Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                                        if (listField !=null && listField.size()>0){
                                            for(int j=0; j<listField.size(); j++){
                                                CustomRptConfig dataField = (CustomRptConfig)listField.get(j);
                                                if (dataField.getRptConfigFieldColour()!= null && dataField.getRptConfigFieldColour().length()>0){
                                                    tdWarna = "#"+dataField.getRptConfigFieldColour();
                                                }
                                                    returnData += "<tr>"
                                                                    //+ "<td width='10%' style='text-align:center'>"+dataField.getRptConfigShowIdx()+"</td>"
                                                                    + "<td width='60%' style='background-color: "+tdWarna+"'>"+dataField.getRptConfigFieldHeader()+"</td>"
                                                                    + "<td width='15%' style='text-align:center'><button id='btn3' class='btn btn-danger btn-xs btndelete' data-oid='"+dataField.getOID()+"'><i class='fa fa-trash'></i></button></td>"
                                                            +   "</tr>";
                                                
                                        tdWarna = "";
                                            }
                                        }
                            returnData += "</table>"
                                    + "</div>"
                            + "</div>"; 
                        }
                returnData += "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<button class='btn btn-default btnadd' data-oid='"+this.oid+"' data-for='showTableComponent' data-save='save_component'>Salary Component</button>"
                                + "</div>"
                            + "</div>"
                            +    "<div class='row'>"
                                    + "<div class='col-md-2'>"
                                    + "</div>"
                                    + "<div class='col-md-10'>"
                                        + "<table class='table-bordered'>";
                                        String whereComp = " RPT_CONFIG_DATA_TYPE = 2 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oid+" ";
                                        String tdColor = "";              
                                        Vector listComp = PstCustomRptConfig.list(0, 0, whereComp, "");
                                        if (listComp !=null && listComp.size()>0){
                                            for(int j=0; j<listComp.size(); j++){
                                                CustomRptConfig dataField = (CustomRptConfig)listComp.get(j);

                                                if (dataField.getRptConfigFieldColour()!= null && dataField.getRptConfigFieldColour().length()>0){
                                                    tdColor = "#"+dataField.getRptConfigFieldColour();
                                                }
                                            returnData += "<tr>"
                                                            + "<td width='15%' style='text-align:center'><button id='btn4' class='btn btn-warning btn-xs'><i class='fa fa-pencil'></i></button></td>"
                                                            + "<td width='10%' style='text-align:center'>"+dataField.getRptConfigShowIdx()+"</td>"
                                                            + "<td width='60%' style='background-color: "+tdColor+"'>"+dataField.getRptConfigFieldHeader()+"</td>"
                                                            + "<td width='15%' style='text-align:center'><button id='btn3' class='btn btn-danger btn-xs btndelete' data-oid='"+dataField.getOID()+"'><i class='fa fa-trash'></i></button></td>"
                                                    +   "</tr>";
                                            tdColor = "";
                                            }
                                        }
                            returnData += "</table>"
                                    + "</div>"
                            + "</div>"
                                + "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<button class='btn btn-default btnadd' data-oid='"+this.oid+"' data-for='showTableDataCombine' data-save='submit_combine'>Add Data Combine</button>"
                                + "</div>"
                            + "</div>"
                            +    "<div class='row'>"
                                    + "<div class='col-md-2'>"
                                    + "</div>"
                                    + "<div class='col-md-10'>"
                                        + "<table class='table-bordered'>";
                                        String whereCombine = " RPT_CONFIG_DATA_TYPE = 1 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oid+" ";
                                        String tdColour = "";              
                                        Vector listCombine = PstCustomRptConfig.list(0, 0, whereCombine, "");
                                        if (listCombine !=null && listCombine.size()>0){
                                            for(int j=0; j<listCombine.size(); j++){
                                                CustomRptConfig dataField = (CustomRptConfig)listCombine.get(j);

                                                if (dataField.getRptConfigFieldColour()!= null && dataField.getRptConfigFieldColour().length()>0){
                                                    tdColour = "#"+dataField.getRptConfigFieldColour();
                                                }
                                    returnData += "<tr>"
                                                + "<td width='15%' style='text-align:center'><button id='btn4' class='btn btn-warning btn-xs'><i class='fa fa-pencil'></i></button></td>"
                                                + "<td width='10%' style='text-align:center'>"+dataField.getRptConfigShowIdx()+"</td>"
                                                + "<td width='60%' style='background-color: "+tdColour+"'>"+dataField.getRptConfigFieldHeader()+"</td>"
                                                + "<td width='15%' style='text-align:center'><button id='btn3' class='btn btn-danger btn-xs btndelete' data-oid='"+dataField.getOID()+"'><i class='fa fa-trash'></i></button></td>"
                                        +   "</tr>";
                                    tdColour = "";
                                    }
                                }
                        returnData += "</table>"
                                        + "</div>"
                                + "</div>"
                                + "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<button class='btn btn-default btnadd' data-oid='"+this.oid+"' data-for='showTableDataSubTotal' data-save='save_subtotal'>Add Sub Total</button>"
                                + "</div>"
                            + "</div>"
                            +    "<div class='row'>"
                                    + "<div class='col-md-2'>"
                                    + "</div>"
                                    + "<div class='col-md-10'>"
                                        + "<table class='table-bordered'>";
                                        String whereView = " RPT_CONFIG_DATA_TYPE = 3 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oid+" ";

                                        Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                                        if (listField !=null && listField.size()>0){
                                            for(int j=0; j<listField.size(); j++){
                                                CustomRptConfig dataField = (CustomRptConfig)listField.get(j);
                                                String func = "";
                                                if (dataField.getRptConfigFieldType() == 1){
                                                    func = "<strong>COUNT</strong>";
                                                } else {
                                                    func = "<strong>SUM</strong>";
                                                }
                                                returnData += "<tr>";
                                                    returnData += "<td>"+func+"("+dataField.getRptConfigFieldHeader()+") <strong>BY</strong> "+dataField.getRptConfigTableName()+"</td>";
                                                    returnData += "<td width='15%' style='text-align:center'><button id='btn3' class='btn btn-danger btn-xs btndelete' data-oid='"+dataField.getOID()+"'><i class='fa fa-trash'></i></button></td>";
                                                returnData += "</tr>";
                                            }
                                        }
                returnData += "</table>"
                                        + "</div>"
                                + "</div>"
                            +"</div>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                + "</div>"
                + selection()
                +sort()
                +group();          
                
        
	return returnData;
    }
    
    public String selection(){
        String returnData = "";
            returnData += "<div class='col-md-3'>"
                                + "<div class='box'>"
                                    + "<div class='box-header with-border'>"
                                        + "<div style='text-align: center;'><b>Selection / Filter</b></div>"
                                    + "</div>"
                                    + "<div class='box-body'>"
                                        + "<div class='row'>"
                                            + "<div class='col-md-12'>"
                                                + "<button class='btn btn-default btnadd' data-oid='"+this.oid+"' data-for='showTableFilter' data-save='save_field_other'>Add Field</button>"
                                            + "</div>"
                                        + "</div>"
                                        + "<div class='row'>"
                                            + "<div class='col-md-2'>"
                                            + "</div>"
                                            + "<div class='col-md-10'>"
                                                + "<table class='table-bordered'>"
                                                + drawTable(1, this.oid)
                                                + "</table>"
                                            + "</div>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                        + "</div>";
        
        return returnData;
    }
    
    public String sort(){
        String returnData = "";
            returnData += "<div class='col-md-3'>"
                                + "<div class='box'>"
                                    + "<div class='box-header with-border'>"
                                        + "<div style='text-align: center;'><b>Sort By</b></div>"
                                    + "</div>"
                                    + "<div class='box-body'>"
                                        + "<div class='row'>"
                                            + "<div class='col-md-12'>"
                                                + "<button class='btn btn-default btnadd' data-oid='"+this.oid+"' data-for='showTableSort' data-save='save_field_other'>Add Field</button>"
                                            + "</div>"
                                        + "</div>"
                                        + "<div class='row'>"
                                            + "<div class='col-md-2'>"
                                            + "</div>"
                                            + "<div class='col-md-10'>"
                                                + "<table class='table-bordered'>"
                                                + drawTable(2, this.oid)
                                                + "</table>"
                                            + "</div>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                        + "</div>";
        
        return returnData;
    }
    
    public String group(){
        String returnData = "";
            returnData += "<div class='col-md-3'>"
                                + "<div class='box'>"
                                    + "<div class='box-header with-border'>"
                                        + "<div style='text-align: center;'><b>Group By</b></div>"
                                    + "</div>"
                                    + "<div class='box-body'>"
                                        + "<div class='row'>"
                                            + "<div class='col-md-12'>"
                                                + "<button class='btn btn-default btnadd' data-oid='"+this.oid+"' data-for='showTableGroup' data-save='save_field_other'>Add Field</button>"
                                            + "</div>"
                                        + "</div>"
                                        + "<div class='row'>"
                                            + "<div class='col-md-2'>"
                                            + "</div>"
                                            + "<div class='col-md-10'>"
                                                + "<table class='table-bordered'>"
                                                + drawTable(3, this.oid)
                                                + "</table>"
                                            + "</div>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                        + "</div>";
        
        return returnData;
    }
    
    public String drawTable(int dataGroup, long oidCustom ){
        String returnData = "";
        
        for(int i=0; i<PstCustomRptConfig.realTableList.length; i++){
            String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = "+dataGroup+" AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[i]+"' AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                                      
            Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
            if (listField !=null && listField.size()>0){
                for(int j=0; j<listField.size(); j++){
                    CustomRptConfig dataField = (CustomRptConfig)listField.get(j);
                    returnData += "<tr>";
                        returnData += "<td width='85%'> "+dataField.getRptConfigFieldHeader()+"</td>";
                        returnData += "<td width='15%' style='text-align:center'><button id='btn3' class='btn btn-danger btn-xs btndelete' data-oid='"+dataField.getOID()+"'><i class='fa fa-trash'></i></button></td>";
                    returnData += "</tr>";
                }
            }
        }
        
        
        return returnData;
    }
    
    public String showTable(){
        
        String returnData=""
                + "<div class='col-md-12'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Choose Field from Table "+PstCustomRptConfig.showTableList[tblIdx]+"</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<table class='table'>";
                        int inc = 1;
                        for(int i=0; i<PstCustomRptConfig.showFieldList[tblIdx].length; i++){
                returnData += "<td>"
                        + "<input type='checkbox' name='field_name' value='"+i+"'/> "+PstCustomRptConfig.showFieldList[tblIdx][i]
                        + "</td>";
                        if (inc == 2){
                        inc = 1;
                returnData += "</tr>";
                        } else {
                                inc++;
                            }
                        }
                        if(inc == 2){
                returnData += "<td>&nbsp;</td></tr>";
                        }
                returnData += "</div>"
                        + "</div>";        
        
        return returnData;
        
    }
    
    public String showTableComponent(){
        
        String returnData=""
                + "<div class='col-md-12'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Choose Salary Component</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<table class='table'>"
                        + "<thead>"
                            + "<tr>"
                                + "<th>Benefit</th>"
                                + "<th>Deduction</th>"
                            + "</tr>"
                        + "</thead>"
                        + "<tr>"
                            + "<td>"
                                + "<table class='table'>";
                                    Vector listCompBenefit = PstPayComponent.list(0, 0, "COMP_TYPE=1", "");
                                    if (listCompBenefit != null && listCompBenefit.size()>0){
                                        for(int i=0; i<listCompBenefit.size(); i++){
                                            PayComponent payBenefit = (PayComponent)listCompBenefit.get(i);
                                returnData += "<tr>"
                                                + "<td>"
                                                    + "<input type='checkbox' name='field_benefit' value='"+payBenefit.getOID()+"' /> "+payBenefit.getCompName()
                                                + "</td>"
                                            + "</tr>";
                                        }
                                    }
                    returnData += "</table>"
                            + "</td>"
                            + "<td>"
                                + "<table class='table'>";
                                    Vector listCompDeduction = PstPayComponent.list(0, 0, "COMP_TYPE=2", "");
                                    if (listCompDeduction != null && listCompDeduction.size()>0){
                                        for(int i=0; i<listCompDeduction.size(); i++){
                                            PayComponent payDeduction = (PayComponent)listCompDeduction.get(i);
                                returnData += "<tr>"
                                                + "<td>"
                                                    + "<input type='checkbox' name='field_deduction' value='"+payDeduction.getOID()+"' />"+payDeduction.getCompName()
                                                + "</td>"
                                            + "</tr>";
                                        }
                                    }            
                    returnData += "</table>"
                            + "</td>"
                        + "</tr>"
                    + "</table>"
                    + "</div"
                + "</div>"
            + "</div>";                                                           
                            
                                
        
        return returnData;
        
    }
    
    public String showTableDataCombine(){
        
        String returnData=""
                + "<div class='col-md-12'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Salary Component to Combine Data</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<table class='table'>";
                        String whereView = " RPT_CONFIG_DATA_TYPE = 2 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oid+" ";
                        Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                        if (listField !=null && listField.size()>0){
                            for(int l=0; l<listField.size(); l++){
                                CustomRptConfig dataField = (CustomRptConfig)listField.get(l);
                                returnData += "<tr>"
                                                + "<td colspan='2'>"
                                                    + "<input type='checkbox' name='combine_component' id='combine_component' value='"+dataField.getRptConfigFieldName()+"' /> "+dataField.getRptConfigFieldHeader()
                                                + "</td>"
                                            + "</tr>";
                                        }
                                    }
                    returnData += "<tr>"
                                    + "<td colspan='2'>"
                                        + "<button type='button' class='btn btn-default btncombine' data-for='combinePlus' data-target='#inp_combine_show'><i class='fa fa-plus'></i></button>"
                                        + "<button type='button' class='btn btn-default btncombine' data-for='combineMin' data-target='#inp_combine_show'><i class='fa fa-minus'></i></button>"
                                    + "</td>"
                                + "</tr>"
                                + "<tr>"
                                    + "<td colspan='2'>"
                                        + "<input type='hidden' name='inp_combine' value='"+this.inpCombine+"'>"
                                    + "</td>"
                                + "</tr>"
                                + "<tr>"
                                    + "<td colspan='2'>"
                                        + "<input type='text' name='inp_combine_show' id='inp_combine_show' value='"+this.inpCombineShow+"' class='form-control'>"
                                    + "</td>"
                                + "</tr>"
                                + "<tr>"
                                    + "<td colspan='2'>"
                                        + "<input type='hidden' name='inp_operator' value='"+this.inpOperator+"'>"
                                    + "</td>"
                                + "</tr>"
                                + "<tr>"
                                    + "<td colspan='2'>"
                                        + "<input type='text' name='inp_alias' value='"+this.inpAlias+"' placeholder='Alias' class='form-control'>"
                                    + "</td>"
                                + "</tr>"
                            + "</table>"
                + "</div>"
            + "</div>";                                                           
                            
                                
        
        return returnData;
        
    }
    
    public String showTableDataSubTotal(){
        
        String returnData=""
                + "<div class='col-md-12'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Sub Total</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<table class='table'>"
                        + "<tr>"
                            + "<input type='hidden' name='select_func' value='1' />"
                            + "<input type='hidden' name='select_item' value='RECORD' />"
                            + "<td>"
                                + "<strong>COUNT</strong> (RECORD) <strong>BY</strong>"
                                    + "<select name='select_total_by' class='form-control'>"
                                        + "<option value='0'>-select-</option>";
                                        String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oid+" ";
                                        Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                                        if (listField != null && listField.size() > 0){
                                            for (int L=0; L<listField.size(); L++){
                                                CustomRptConfig itemField = (CustomRptConfig)listField.get(L);
                                            returnData += "<option value='"+itemField.getRptConfigFieldName()+"'>"+itemField.getRptConfigFieldHeader()+"</option>";
                                            }
                                        }
                       returnData += "</select>"
                            + "</td>"        
                        + "</tr>"
                    + "</table>"
                    + "</div>"
                + "</div>";
                            
        return returnData;
        
    }
    
    public String showTableFilter(){
        String returnData = "";
        returnData += "<div class='col-md-12'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Choose Field from Data List</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<table class='table'>"
                        + "<input type='hidden' name='show_group' value='1' />";
                        for(int k=0; k<PstCustomRptConfig.showTableList.length; k++){
                            String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[k]+"' AND RPT_MAIN_ID = "+oid+" ";
                            Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                            if (listField !=null && listField.size()>0){
                                for(int l=0; l<listField.size(); l++){
                                    CustomRptConfig dataField = (CustomRptConfig)listField.get(l);
                                returnData +=   " <tr> "
                                        + "<td colspan='2'><input type='checkbox' name='field_data_list' value="+dataField.getOID()+" /> "+dataField.getRptConfigFieldHeader()+"</td>"
                                    + "</tr>";
                                    
                                }
                            }
                        }
                    returnData += "</table>"
                    + "</div>"
                + "</div>";
        
        return returnData;
    }
    
    public String showTableSort(){
        String returnData = "";
        returnData += "<div class='col-md-12'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Choose Field from Data List</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<table class='table'>"
                        + "<input type='hidden' name='show_group' value='2' />";
                        for(int k=0; k<PstCustomRptConfig.showTableList.length; k++){
                            String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[k]+"' AND RPT_MAIN_ID = "+oid+" ";
                            Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                            if (listField !=null && listField.size()>0){
                                for(int l=0; l<listField.size(); l++){
                                    CustomRptConfig dataField = (CustomRptConfig)listField.get(l);
                                returnData +=   " <tr> "
                                        + "<td colspan='2'><input type='checkbox' name='field_data_list' value="+dataField.getOID()+" /> "+dataField.getRptConfigFieldHeader()+"</td>"
                                    + "</tr>";
                                    
                                }
                            }
                        }
                    returnData += "</table>"
                    + "</div>"
                + "</div>";
        
        return returnData;
    }
    
    public String showTableGroup(){
        String returnData = "";
        returnData += "<div class='col-md-12'>"
                    + "<div class='box'>"
                        + "<div class='box-header with-border'>"
                            + "<div style='text-align: center;'><b>Choose Field from Data List</b></div>"
                        + "</div>"
                    + "<div class='box-body'>"
                    + "<table class='table'>"
                        + "<input type='hidden' name='show_group' value='3' />";
                        for(int k=0; k<PstCustomRptConfig.showTableList.length; k++){
                            String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[k]+"' AND RPT_MAIN_ID = "+oid+" ";
                            Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                            if (listField !=null && listField.size()>0){
                                for(int l=0; l<listField.size(); l++){
                                    CustomRptConfig dataField = (CustomRptConfig)listField.get(l);
                                returnData +=   " <tr> "
                                        + "<td colspan='2'><input type='checkbox' name='field_data_list' value="+dataField.getOID()+" /> "+dataField.getRptConfigFieldHeader()+"</td>"
                                    + "</tr>";
                                    
                                }
                            }
                        }
                    returnData += "</table>"
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
