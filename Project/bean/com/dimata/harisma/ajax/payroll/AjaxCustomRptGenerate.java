/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.payroll;

import com.dimata.harisma.entity.payroll.CustomRptConfig;
import com.dimata.harisma.entity.payroll.CustomRptDynamic;
import com.dimata.harisma.entity.payroll.PstCustomRptConfig;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
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
public class AjaxCustomRptGenerate extends HttpServlet {

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
	this.oid = FRMQueryString.requestLong(request, "oid_custom");
	this.oidReturn=0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        
	
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
		
	    break;
		
	    case Command.LIST :
		commandList(request);
	    break;
		
	    case Command.DELETEALL : 
		
	    break;
            
            case Command.DELETE :
                
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
	if(this.dataFor.equals("showFilter")){
	  
	}
    }

    
    public void commandDelete(HttpServletRequest request){
       
    }
    
    public void commandList(HttpServletRequest request){
	if(this.dataFor.equals("listGenerate")){
            this.htmlReturn = dataGenerate(this.oid, request);
	}
    }

    
    public String dataGenerate(long oidCustom, HttpServletRequest request){
	int generate = FRMQueryString.requestInt(request, "generate");
        int selectionChoose = FRMQueryString.requestInt(request, "selection_choose");
        String[] whereField = FRMQueryString.requestStringValues(request, "where_field");
        String[] whereValue = FRMQueryString.requestStringValues(request, "where_value");
        String[] whereType = FRMQueryString.requestStringValues(request, "where_type");
        String[] operator = FRMQueryString.requestStringValues(request, "operator");
        String whereCustom = FRMQueryString.requestString(request, "where_custom");
        int showValue = FRMQueryString.requestInt(request, "show_value");
        
        
        String returnData = "";


        /*
         * Description : Code to find WHERE DATA 
         */
        String whereClause = "";
        whereClause = PstCustomRptConfig.findWhereData(selectionChoose, whereField, whereValue, whereType, operator, whereCustom);
        /*
         * Description : Get SELECT DATA and JOIN DATA
         * Date : 2015-04-08 
         */
        String[] dataJoin = new String[5];

        String strSelect = "SELECT ";
        String strJoin = "";
        String strOrderBy = "";
        int inc = 0;
        /* WHERE CLAUSE WITH RPT_CONFIG_DATA_TYPE = 0 */
        String where = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID =" + oidCustom;
        Vector listData = PstCustomRptConfig.list(0, 0, where, "");
        /* WHERE CLAUSE WITH RPT_CONFIG_DATA_TYPE = 2 */
        String whereComp = " RPT_CONFIG_DATA_TYPE = 2 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID =" + oidCustom;
        Vector listSalaryComp = PstCustomRptConfig.list(0, 0, whereComp, "");
        /* WHERE CLAUSE WITH RPT_CONFIG_DATA_TYPE = 1 */
        String whereComb = " RPT_CONFIG_DATA_TYPE = 1 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID =" + oidCustom;
        Vector listComb = PstCustomRptConfig.list(0, 0, whereComb, "");
        /* Array join */
        int[] joinCollection = new int[PstCustomRptConfig.joinDataPriority.length];
        /* inisialisasi joinCollection */
        for (int d = 0; d < PstCustomRptConfig.joinDataPriority.length; d++) {
            joinCollection[d] = -1;
        }
        boolean found = false;
        if (listData != null && listData.size() > 0) {
            int incC = 0;
            for (int i = 0; i < listData.size(); i++) {
                CustomRptConfig rpt = (CustomRptConfig) listData.get(i);
                /* mendapatkan join data */
                for (int k = 0; k < PstCustomRptConfig.joinData.length; k++) {
                    for (String retval : PstCustomRptConfig.joinData[k].split(" ")) {
                        dataJoin[inc] = retval;
                        inc++;
                    }
                    inc = 0;
                    /* bandingkan nilai table dengan data join */
                    if (rpt.getRptConfigTableName().equals(dataJoin[2])) {
                        /* cek data join pada array joinCollection */
                        for (int c = 0; c < joinCollection.length; c++) {
                            if (PstCustomRptConfig.joinDataPriority[k] == joinCollection[c]) {
                                found = true; /* jika found == true, maka data sudah ada di joinCollection */
                            }
                        }
                        if (found == false) {
                            joinCollection[incC] = PstCustomRptConfig.joinDataPriority[k];
                        }
                        found = false;
                    }
                }
                incC++;
                /* mendapatkan data select */
                strSelect += rpt.getRptConfigTableName() + "." + rpt.getRptConfigFieldName();
                if (i == listData.size() - 1) {
                    strSelect += " ";
                } else {
                    strSelect += ", ";
                }
            }
            /* join Collection */
            Arrays.sort(joinCollection); /* sorting array */
            for (int m = 0; m < joinCollection.length; m++) {
                if (joinCollection[m] != -1) {
                    strJoin += PstCustomRptConfig.joinData[joinCollection[m]] + " ";
                }
            }
        }
        /* ORDER BY */
        String whereOrder = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 2 AND RPT_MAIN_ID =" + oidCustom;
        Vector listOrder = PstCustomRptConfig.list(0, 0, whereOrder, "");
        if (listOrder != null && listOrder.size() > 0) {
            strOrderBy = " ORDER BY ";
            for (int ord = 0; ord < listOrder.size(); ord++) {
                CustomRptConfig rptOrder = (CustomRptConfig) listOrder.get(ord);
                strOrderBy += rptOrder.getRptConfigTableName() + "." + rptOrder.getRptConfigFieldName();
                if (ord == listOrder.size() - 1) {
                    strOrderBy += " ";
                } else {
                    strOrderBy += ", ";
                }

            }
        }
        
        returnData += "<div class=\"callout callout-info col-md-12\">"
                        + "<p>"+strSelect+" FROM hr_employee "+strJoin+" "+whereClause+ strOrderBy+"</p>"
                    + "</div>";
        
        Vector listResult = new Vector();
        if (listSalaryComp != null && listSalaryComp.size() > 0) {
            listResult = PstCustomRptConfig.listData(strSelect + " FROM hr_employee " + strJoin + " " + whereClause + strOrderBy, listData);
            returnData += "<div class='çol-md-12'>"
                            + drawTable(listData, listResult, listSalaryComp, listComb, oidCustom, showValue)
                        + "</div>";
        } else {
            listResult = PstCustomRptConfig.listDataWithoutPaySlip(strSelect + " FROM hr_employee " + strJoin + " " + whereClause + strOrderBy, listData);
            returnData += "<div class='çol-md-12'>"
                            + drawListWithoutPaySlip(listData, listResult, oidCustom)
                        + "</div>";
        }
        
               
	return returnData;
    }
    
    public String drawTable(Vector listField, Vector listRecord, Vector listComp, Vector listComb, long oidCustom, int showValue){
        String whereSubTotal = " RPT_CONFIG_DATA_TYPE = 3 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID ="+oidCustom;
        Vector listSubTotal = PstCustomRptConfig.list(0, 0, whereSubTotal, "");
        String[] countName = new String[listField.size()];
        int[] countIdx = new int[listField.size()];
        /* get name of count by SECTION or DEPARTMENT or else */
        if (listSubTotal != null && listSubTotal.size()>0){
            for(int st=0; st<listSubTotal.size(); st++){
                CustomRptConfig sbt = (CustomRptConfig)listSubTotal.get(st);
                if ("COUNT".equals(sbt.getRptConfigTableGroup())){
                    countName[st] = sbt.getRptConfigTableName();
                }
            }
        }
        /* Some of array variable */
        String[][] arrField = new String[listRecord.size()+1][listField.size()]; /* for dinamic fields */
        double[][] totalComp = new double[listRecord.size()][listComp.size()]; /* for total salary component */
        double[] totalCombine = new double[listRecord.size()]; /* for total combine */
        /* update by Hendra Putu | 2015-11-05 */
        double countValueComp = 0;
        boolean[] showRecord = new boolean[listRecord.size()];
       
        /* mengambil field dinamis */
        /* Check count and get index */
        int idx = 0;
        for(int i=0;i<listField.size(); i++){
            CustomRptConfig fields = (CustomRptConfig)listField.get(i);              
            for(int cn=0; cn<countName.length; cn++){
                if (fields.getRptConfigFieldName().equals(countName[cn])){
                    countIdx[idx] = i;
                    idx++;
                }
            }
        }
        
        /* 
        ===============================================================================
        * listRecord berisi baris data hasil query dan akan ditampung ke variabel array 
        ===============================================================================
        */
        int grandCols = listField.size();
        boolean isStatusData = false;
        if (listRecord != null && listRecord.size()>0){
            for(int y=0; y<listRecord.size(); y++){
                CustomRptDynamic dyc = (CustomRptDynamic)listRecord.get(y);
                /* Get dinamis field */
                if (listField != null && listField.size() > 0){
                    for(int i=0;i<listField.size(); i++){
                        CustomRptConfig rpt = (CustomRptConfig)listField.get(i);
                        /* jika field name != PAY_SLIP_ID */
                        if(!rpt.getRptConfigFieldName().equals("PAY_SLIP_ID")){
                            if (!rpt.getRptConfigFieldName().equals("STATUS_DATA")){
                                arrField[y][i] = dyc.getField(rpt.getRptConfigFieldName());
                            } else {
                                isStatusData = true;
                            }
                        }  
                    }
                }
                /* get value field */
                for(int k=0; k<listComp.size(); k++){
                    CustomRptConfig rptComp = (CustomRptConfig)listComp.get(k);                    
                    totalComp[y][k] = convertDouble(dyc.getField(rptComp.getRptConfigFieldName()));
                    countValueComp += totalComp[y][k];
                }
                /* get value field combine */
                for(int l=0; l<listComb.size(); l++){
                    CustomRptConfig rptComb = (CustomRptConfig)listComb.get(l);
                    totalCombine[y] = convertDouble(dyc.getField(rptComb.getRptConfigFieldName()));
                }
                if (showValue == 0){
                   showRecord[y] = true;
                } else {
                    if (countValueComp == 0){
                        showRecord[y] = false;
                    } else {
                        showRecord[y] = true;
                    }
                }
                countValueComp = 0;
            }
        }
        /* END of List Record */
        int[] countTotal = new int[idx];
        boolean[] countFind = new boolean[idx];
        /* inisialisasi */
        for(int d=0; d<idx; d++){
            countTotal[d] = 1;
            countFind[d] = false;
        }
        int nomor = 0;
        int yStart = 0;
        int yEnd = 0;
        double total = 0;
        boolean ketemu = false;
        String table = "<table class='table table-bordered table-striped'>";
        table +="<tr>";
        table +="<td>No</td>";
        /* mengambil field dinamis */
        for(int i=0;i<listField.size(); i++){
            CustomRptConfig fields = (CustomRptConfig)listField.get(i);    
            if(!fields.getRptConfigFieldName().equals("PAY_SLIP_ID")){
                if (!fields.getRptConfigFieldName().equals("STATUS_DATA")){
                    table +="<td>"+fields.getRptConfigFieldHeader()+"</td>";
                }
            }
        }
        /* mengambil field salary component */
        for(int j=0; j<listComp.size(); j++){
            CustomRptConfig comp = (CustomRptConfig)listComp.get(j);
            table +="<td>"+comp.getRptConfigFieldHeader()+"</td>";
        }
        /* mengambil field combine */
        for(int k=0; k<listComb.size(); k++){
            CustomRptConfig comb = (CustomRptConfig)listComb.get(k);
            table +="<td>"+comb.getRptConfigFieldHeader()+"</td>";
        }
        table +="</tr>";
        /* ============== record result ==============*/
        if (listRecord != null && listRecord.size()>0){
            for(int y=0; y<listRecord.size(); y++){
                if (showRecord[y] == true){
                    nomor++;
                    table +="<tr>";
                    table +="<td>"+nomor+"</td>";
                    /* Fields dinamic */
                    if (listField != null && listField.size() > 0){
                        for(int i=0;i<listField.size(); i++){
                            if (arrField[y][i] != null){
                                table +="<td>"+arrField[y][i]+"</td>";
                            }
                        }
                        /* count record by ... */
                        for(int sb=0; sb<idx; sb++){
                            if (arrField[y][countIdx[sb]].equals(arrField[y+1][countIdx[sb]])){
                                countTotal[sb] = countTotal[sb] + 1;
                            } else {
                                countFind[sb] = true;
                                ketemu = true;
                                yEnd = y+1;
                            }
                        }
                    }

                    for(int k=0; k<listComp.size(); k++){
                        table +="<td>"+convertInteger(totalComp[y][k])+"</td>";
                    }
                    for(int l=0; l<listComb.size(); l++){
                        table +="<td>"+convertInteger(totalCombine[y])+"</td>";
                    }
                    table +="</tr>";
                    table +="<tr>";

                    boolean addColoum = false;
                    if (ketemu == true){
                        for(int f=0; f<idx; f++){
                            if (countFind[f] == true){
                                if (addColoum == false){
                                    for(int a=0; a<countIdx[f]+1; a++){
                                        table +="<td id='tdTotal'>&nbsp;</td>";
                                    }
                                    addColoum = true;
                                }
                                table +="<td id='tdTotal'>"+countTotal[f]+"</td>";

                                countFind[f] = false;
                                countTotal[f] = 1;
                            }
                        }
                        addColoum = false;
                        for(int b=0; b<listField.size()-(countIdx[idx-1]+2); b++){
                            table +="<td id='tdTotal'>&nbsp;</td>";
                        }
                        for(int kolom=0; kolom<listComp.size(); kolom++){
                            for(int x=yStart; x<yEnd; x++){
                                total += totalComp[x][kolom];
                            }
                            table +="<td id='tdTotal'>"+convertInteger(total)+"</td>";
                            total = 0;
                        }
                        for(int x=yStart; x<yEnd; x++){
                            total += totalCombine[x];
                        }
                        table +="<td id='tdTotal'>"+convertInteger(total)+"</td>";
                        total = 0;
                        ketemu = false;
                    }
                    table +="</tr>";
                    yStart = yEnd;
                }
            }
            table += "<tr>";
            if (isStatusData == true){
                grandCols = grandCols - 1;
            }
            table += "<td class='td_grand_total' colspan=\""+grandCols+"\">Grand Total</td>";
            /* mengambil field salary component */
            double grandTotalComp = 0;
            for(int j=0; j<listComp.size(); j++){
                for(int y=0; y<listRecord.size(); y++){
                    grandTotalComp = grandTotalComp + totalComp[y][j];
                }
                table +="<td class='td_grand_total'>"+convertInteger(grandTotalComp)+"</td>";
                grandTotalComp = 0;
            }
            /* mengambil field combine */
            double grandTotalComb = 0;
            
            for(int y=0; y<listRecord.size(); y++){
                grandTotalComb = grandTotalComb +  totalCombine[y];
            }
            if (grandTotalComb != 0){
                table +="<td class='td_grand_total'>"+convertInteger(grandTotalComb)+"</td>";
            }
            
            table += "</tr>";
        }
        table +="</table>";
        
        return table;
    }
    
    public String drawListWithoutPaySlip(Vector listField, Vector listRecord, long oidCustom){
        String table = "";
        String[][] arrField = new String[listRecord.size() + 1][listField.size()]; /* for dinamic fields */
            
        if (listRecord != null && listRecord.size() > 0) {
            for (int y = 0; y < listRecord.size(); y++) {
                CustomRptDynamic dyc = (CustomRptDynamic) listRecord.get(y);
                /* Get dinamis field */
                if (listField != null && listField.size() > 0) {
                    for (int i = 0; i < listField.size(); i++) {
                        CustomRptConfig rpt = (CustomRptConfig) listField.get(i);
                        arrField[y][i] = dyc.getField(rpt.getRptConfigFieldName());
                    }
                }
            }
        }
            
        int nomor = 0;
        int yStart = 0;
        int yEnd = 0;
        double total = 0;
        boolean ketemu = false;
        table = "<table class='table table-bordered table-striped'>";
        table += "<tr>";
        table += "<td>No</td>";
        /* mengambil field dinamis */
        for (int i = 0; i < listField.size(); i++) {
            CustomRptConfig fields = (CustomRptConfig) listField.get(i);
            table += "<td>" + fields.getRptConfigFieldHeader() + "</td>";
        }
            
        table += "</tr>";
        /* ============== record result ==============*/
        if (listRecord != null && listRecord.size() > 0) {
            for (int y = 0; y < listRecord.size(); y++) {
                nomor++;
                table += "<tr>";
                table += "<td>" + nomor + "</td>";
                /* Fields dinamic */
                if (listField != null && listField.size() > 0) {
                    for (int i = 0; i < listField.size(); i++) {
                        if (arrField[y][i] != null) {
                            table += "<td>" + arrField[y][i] + "</td>";
                        }
                    }
                }
                table += "</tr>";
                yStart = yEnd;
            }
        }
        table += "</table>";
            
        return table;
    }
    
    
    
    public String apostrophe(String value, String opr){
        String str = "";
        if (opr.equals("=")){
            str = "'"+value+"'";
        } else if (opr.equals("BETWEEN")){
            String[] data = new String[5];
            int inc = 0;
            for (String retval : value.split(" ")) {
                data[inc]= retval;
                inc++;
            }
            str = "'"+data[0]+"' AND '"+data[1]+"'";
        } else if (opr.equals("LIKE")){
            str = "'"+value+"'";
        } else if (opr.equals("IN")){
            String stIn = "";
            for (String retval : value.split(",")) {
                stIn += " '"+ retval +"', ";
            }
            stIn += "'0'";
            str += "("+stIn+")";
        } else if (opr.equals("!=")) {
            str = "'"+value+"'";
        } else {
            str = value;
        }
        return str;
    }
    /* Convert double */
    public double convertDouble(String val){
        BigDecimal bDecimal = new BigDecimal(Double.valueOf(val));
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_UP);
        return bDecimal.doubleValue();
    }
    /* Convert int */
    public int convertInteger(double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_UP);
        return bDecimal.intValue();
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