/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.harisma.entity.employee.EmpAssetInventory;
import com.dimata.harisma.entity.employee.EmpAssetInventoryItem;
import com.dimata.harisma.entity.employee.PstEmpAssetInventory;
import com.dimata.harisma.entity.employee.PstEmpAssetInventoryItem;
import com.dimata.harisma.form.employee.CtrlEmpAssetInventoryItem;
import com.dimata.harisma.form.employee.FrmEmpAssetInventoryItem;
import com.dimata.harisma.session.employee.AssetLocation;
import com.dimata.harisma.session.employee.AssetMaterial;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.util.Enumeration;
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
public class AjaxEmpAssetInventoryItem extends HttpServlet {
    
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
    private long oidAssetInventory = 0;
    private long locationId = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    private int docType = 0;
    private int stok = 0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        this.empId = FRMQueryString.requestLong(request, "empId");
        this.oidAssetInventory = FRMQueryString.requestLong(request, "oidAssetInventory");
        this.locationId = FRMQueryString.requestLong(request, "locationId");
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        this.empName = FRMQueryString.requestString(request, "empName");
	
	//INT
	this.iCommand = FRMQueryString.requestCommand(request);
	this.iErrCode = 0;
        this.docType = FRMQueryString.requestInt(request, "docType");
	this.stok = FRMQueryString.requestInt(request, "stok");
	
	
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
		commandDeleteAll(request);
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
        if(this.dataFor.equals("showAssetItemForm")){
	  this.htmlReturn = docFlowForm();
	} else if (this.dataFor.equals("getMaterial")){
           this.htmlReturn = materialSelect(request, this.locationId, 0);
        } else if (this.dataFor.equals("getMaterialEmp")){
           this.htmlReturn = materialSelectEmp(request, this.locationId, 0, this.empId);
        } 
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmpAssetInventoryItem ctrlEmpAssetInventoryItem = new CtrlEmpAssetInventoryItem(request);
	this.iErrCode = ctrlEmpAssetInventoryItem.action(this.iCommand, this.oid, this.oidDelete, this.stok);
	String message = ctrlEmpAssetInventoryItem.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
	CtrlEmpAssetInventoryItem ctrlEmpAssetInventoryItem = new CtrlEmpAssetInventoryItem(request);
	this.iErrCode = ctrlEmpAssetInventoryItem.action(this.iCommand, this.oid, this.oidDelete, this.stok);
	String message = ctrlEmpAssetInventoryItem.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpAssetInventoryItem ctrlEmpAssetInventoryItem = new CtrlEmpAssetInventoryItem(request);
	this.iErrCode = ctrlEmpAssetInventoryItem.action(this.iCommand, this.oid, this.oidDelete, this.stok);
	String message = ctrlEmpAssetInventoryItem.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listAssetItem")){
	    String[] cols = { PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ITEM_ID],
		PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_MATERIAL_ID],
                PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_LOCATION_ID],
                PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_QTY], 
                PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_DETAIL],
                PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_PURPOSE]
            };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	} else if(this.dataFor.equals("listEmpAssetItem")){
            String[] cols = { PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ITEM_ID],
		PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_MATERIAL_ID],
                PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_QTY], 
                PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_DETAIL],
                PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_PURPOSE]
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
        
        if(dataFor.equals("listAssetItem")){
	    whereClause += ""+PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID]+" = '"+this.oidAssetInventory+"'";
	}
        
        if(dataFor.equals("listEmpAssetItem")){
            whereClause += "asset."+PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMPLOYEE_ID] + " = "+this.empId;
        }
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listAssetItem")){
	    total = PstEmpAssetInventoryItem.getCount(whereClause);
	}
        
        if(dataFor.equals("listEmpAssetItem")){
            total = PstEmpAssetInventoryItem.getCountEmpAsset(whereClause);
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
        EmpAssetInventory empAsset = new EmpAssetInventory();
        EmpAssetInventoryItem empAssetItem = new EmpAssetInventoryItem();
        String whereClause = "";
        
        String order ="";
        Hashtable<String, AssetLocation> location = PstEmpAssetInventoryItem.listMapLocation(0, 0, "", "");
        Hashtable<String, AssetMaterial> material = PstEmpAssetInventoryItem.listMapMaterial(0, 0, "", "");
        AssetLocation assetLocation = new AssetLocation();
        AssetMaterial assetMaterial = new AssetMaterial();
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listAssetItem")){
               whereClause += ""+PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID]+" = '"+this.oidAssetInventory+"'";
            } if(dataFor.equals("listEmpAssetItem")){
               whereClause += "asset."+PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMPLOYEE_ID]+" = '"+this.empId+"'"
                            + " AND asset."+PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_STATUS]+" = " +PstEmpAssetInventory.RECEIVED;
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
        if(datafor.equals("listAssetItem")){
	    listData = PstEmpAssetInventoryItem.list(this.start, this.amount,whereClause,order);
	}
        if(datafor.equals("listEmpAssetItem")){
            listData = PstEmpAssetInventoryItem.listEmpAsset(this.start, this.amount, whereClause, order);
        }
         
        
        int number = 0;
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listAssetItem")){
		empAssetItem = (EmpAssetInventoryItem) listData.get(i);
                assetLocation = location.get("" + empAssetItem.getLocationId());
                assetMaterial = material.get(""+ empAssetItem.getMaterialId());
                String checkButton = "<input type='checkbox' name='"+FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_EMP_ASSET_INVENTORY_ITEM_ID]+"' class='"+FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_EMP_ASSET_INVENTORY_ITEM_ID]+"' value='"+empAssetItem.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+assetLocation.getName());
                ja.put(""+assetMaterial.getName());
                ja.put(""+empAssetItem.getQty());
                ja.put(""+empAssetItem.getDetail());
                ja.put(""+empAssetItem.getPurpose());
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+docMaster.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btnedititem btn-xs' data-oid='"+empAssetItem.getOID()+"' data-oidasset='"+empAssetItem.getEmpAssetInventoryId()+"' data-for='showAssetItemForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empAssetItem.getOID()+"' data-for='deleteAssetItem' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
		array.put(ja);
	    }
            else if(datafor.equals("listEmpAssetItem")){
		empAssetItem = (EmpAssetInventoryItem) listData.get(i);
                String whereReturned = "";
                whereReturned += "asset."+PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMPLOYEE_ID]+" = '"+this.empId+"'"
                            + " AND asset."+PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_STATUS]+" = " +PstEmpAssetInventory.RETURNED+""
                            + " AND "+PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_LOCATION_ID]+ " = " + empAssetItem.getLocationId()+""
                            + " AND "+PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_MATERIAL_ID]+ " = " + empAssetItem.getMaterialId();
                
                Vector listReturned = PstEmpAssetInventoryItem.listEmpAsset(this.start, this.amount, whereReturned, order);
                if (listReturned.size() > 0){
                    EmpAssetInventoryItem returnedAsset = (EmpAssetInventoryItem) listReturned.get(listReturned.size()-1);
                    int diffQty = empAssetItem.getQty() - returnedAsset.getQty();
                    assetMaterial = material.get(""+ empAssetItem.getMaterialId());
                    if (diffQty != 0){
                        number = number+1;
                        try{
                            empAsset = PstEmpAssetInventory.fetchExc(empAssetItem.getEmpAssetInventoryId());
                        } catch (Exception exc){}
                        ja.put(""+(this.start+number));
                        ja.put(""+assetMaterial.getName());
                        ja.put(""+diffQty);
                        ja.put(""+empAssetItem.getDetail());
                        ja.put(""+empAssetItem.getPurpose());
                        ja.put(""+Formater.formatDate(empAsset.getReceivedDate(), "d-MMM-yyyy"));
                        array.put(ja);
                    }
                    
                } else {
                    number = number + 1;
                    assetMaterial = material.get(""+ empAssetItem.getMaterialId());
                    try{
                        empAsset = PstEmpAssetInventory.fetchExc(empAssetItem.getEmpAssetInventoryId());
                    } catch (Exception exc){}
                    ja.put(""+(this.start+number));
                    ja.put(""+assetMaterial.getName());
                    ja.put(""+empAssetItem.getQty());
                    ja.put(""+empAssetItem.getDetail());
                    ja.put(""+empAssetItem.getPurpose());
                    ja.put(""+Formater.formatDate(empAsset.getReceivedDate(), "d-MMM-yyyy"));
                    array.put(ja);
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
    
    
    
    public String docFlowForm(){
        
        //CHECK DATA
	EmpAssetInventoryItem empAssetInventoryItem = new EmpAssetInventoryItem();
	Hashtable<String, AssetLocation> location = PstEmpAssetInventoryItem.listMapLocation(0, 0, "", "");
        Hashtable<String, AssetMaterial> material = PstEmpAssetInventoryItem.listMapMaterial(0, 0, "", "");
        AssetLocation assetLocation = new AssetLocation();
        AssetMaterial assetMaterial = new AssetMaterial();
	if(this.oid != 0){
	    try{
		empAssetInventoryItem = PstEmpAssetInventoryItem.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        String dataFor = "";
        if (this.docType == 1){
            dataFor = "getMaterialEmp";
        } else {
            dataFor = "getMaterial";
        }
        
        String returnData= "<input type='hidden' name='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_EMP_ASSET_INVENTORY_ID]+"'  id='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_EMP_ASSET_INVENTORY_ID]+"' class='form-control' value='"+this.oidAssetInventory+"'>"
                + "<div class='form-group'>"
                    + "<label>Location</label>"
                    + "<select name='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_LOCATION_ID]+"'  id='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_LOCATION_ID]+"' class='form-control getmaterial' data-for='"+dataFor+"' data-replacement='#"+FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_MATERIAL_ID]+"'>"
                    + "<option value='0'>Select Location...</option>";
                        Enumeration e = location.keys();
                        while (e.hasMoreElements()){
                            assetLocation = location.get("" + e.nextElement());
                            if (empAssetInventoryItem.getLocationId() == assetLocation.getLocationId()){
                                returnData += "<option value='"+assetLocation.getLocationId()+"' selected>"+assetLocation.getName()+"</option>";
                            } else {
                                returnData += "<option value='"+assetLocation.getLocationId()+"'>"+assetLocation.getName()+"</option>";
                            }
                        }
        returnData  += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Material</label>"
                    + "<select name='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_MATERIAL_ID]+"'  id='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_MATERIAL_ID]+"' class='form-control'>";
                    if (this.empId != 0 && this.docType == 1 ){
                        returnData  += materialSelectEmp(null,empAssetInventoryItem.getLocationId(), empAssetInventoryItem.getMaterialId(), this.empId);
                    } else {
                        returnData  += materialSelect(null,empAssetInventoryItem.getLocationId(), empAssetInventoryItem.getMaterialId());
                    }
        returnData  += "</select>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Qty</label>"
                    + "<input type='text' name='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_QTY]+"'  id='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_QTY]+"' class='form-control getStok' value='"+empAssetInventoryItem.getQty()+"'>"
                + "</div>"
                + "<div class='form-group'>"
                    + "<label>Detail</label>"
                    + "<textarea name='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_DETAIL]+"'  id='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_DETAIL]+"' class='form-control'>"+empAssetInventoryItem.getDetail()+"</textarea>"
                + "</div>" 
                + "<div class='form-group'>"
                    + "<label>Purpose</label>"
                    + "<textarea name='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_PURPOSE]+"'  id='"+ FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_PURPOSE]+"' class='form-control'>"+empAssetInventoryItem.getPurpose()+"</textarea>"
                + "</div>" ;
                
        return returnData;
    }
    
    public String materialSelect(HttpServletRequest request, long locationId, long materialId){
        long oidCategory = Long.valueOf(PstSystemProperty.getValueByName("PROCHAIN_ASSET_CATEGORY"));
        Hashtable<String, AssetMaterial> material = PstEmpAssetInventoryItem.listMapMaterial(0, 0, "stock.LOCATION_ID = "+locationId+" AND material.CATEGORY_ID = "+oidCategory, "");
        AssetMaterial assetMaterial = new AssetMaterial();
        String data = "";
        data += "<option value='0'>Select Material...</option>";
        Enumeration x = material.keys();
        while (x.hasMoreElements()){
            assetMaterial = material.get("" + x.nextElement());
            if (materialId == assetMaterial.getMaterialId()){
                data += "<option value='"+assetMaterial.getMaterialId()+"' data-stok='"+assetMaterial.getQty()+"' selected>"+assetMaterial.getName()+" ["+assetMaterial.getQty()+"] </option>";
            } else {
                data += "<option value='"+assetMaterial.getMaterialId()+"' data-stok='"+assetMaterial.getQty()+"'>"+assetMaterial.getName()+" ["+assetMaterial.getQty()+"]</option>";
            }
        }
        
        return data;
    }
    
    public String materialSelectEmp(HttpServletRequest request, long locationId, long materialId, long empId){
        String where = PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_LOCATION_ID] + " = " + locationId + " AND "
                + "asset." + PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMPLOYEE_ID] + " = " + empId + " "
                + "AND asset."+PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_STATUS] + " = " + PstEmpAssetInventory.RECEIVED;
        Vector listMaterial = PstEmpAssetInventoryItem.listEmpAsset(0, 0, where, "");
        
        Hashtable<String, AssetMaterial> material = PstEmpAssetInventoryItem.listMapMaterial(0, 0, "stock.LOCATION_ID = "+locationId, "");
        AssetMaterial assetMaterial = new AssetMaterial();
        
        String data = "";
        data += "<option value='0'>Select Material...</option>";
        for (int i=0; i < listMaterial.size() ; i++){
            EmpAssetInventoryItem empAsset = (EmpAssetInventoryItem) listMaterial.get(i);
            assetMaterial = material.get(""+empAsset.getMaterialId());
            if (materialId == empAsset.getMaterialId()){
                data += "<option value='"+empAsset.getMaterialId()+"' data-stok='"+empAsset.getQty()+"' selected>"+assetMaterial.getName()+" ["+empAsset.getQty()+"] </option>";
            } else {
                data += "<option value='"+empAsset.getMaterialId()+"' data-stok='"+empAsset.getQty()+"'>"+assetMaterial.getName()+" ["+empAsset.getQty()+"]</option>";
            }
        }
        
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
    
