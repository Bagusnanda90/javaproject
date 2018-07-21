/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.employee;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.employee.EmpRelevantDoc;
import com.dimata.harisma.entity.employee.PstEmpRelevantDoc;
import com.dimata.harisma.entity.masterdata.EmpRelevantDocGroup;
import com.dimata.harisma.entity.masterdata.PstEmpRelevantDocGroup;
import com.dimata.harisma.form.employee.CtrlEmpRelevantDoc;
import com.dimata.harisma.form.employee.FrmEmpRelevantDoc;
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
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import com.dimata.harisma.util.email;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.blob.ImageLoader;
import com.dimata.system.session.dataupload.SessDataUpload;

/**
 *
 * @author Acer
 */
public class AjaxEmpRelevantDoc extends HttpServlet {

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
            
            case Command.SUBMIT :
                commandSendEmail(request);
            break;
            
            case Command.POST :
                commandUpload(request);
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
	if(this.dataFor.equals("showEmpRelevantDocForm")){
	  this.htmlReturn = empRelevantDocForm();
	} if(this.dataFor.equals("showSendEmail")){
            this.htmlReturn = sendEmailForm();
        }
    }
    
    public void commandSave(HttpServletRequest request){
	CtrlEmpRelevantDoc ctrlEmpRelevantDoc = new CtrlEmpRelevantDoc(request);
	this.iErrCode = ctrlEmpRelevantDoc.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpRelevantDoc.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlEmpRelevantDoc ctrlEmpRelevantDoc = new CtrlEmpRelevantDoc(request);
	this.iErrCode = ctrlEmpRelevantDoc.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpRelevantDoc.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlEmpRelevantDoc ctrlEmpRelevantDoc = new CtrlEmpRelevantDoc(request);
	this.iErrCode = ctrlEmpRelevantDoc.action(this.iCommand, this.oid, this.empId, request, empName, userId, this.oidDelete);
	String message = ctrlEmpRelevantDoc.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listEmpRelevantDoc")){
	    String[] cols = { PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_RELEVANT_ID],
		PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMP_RELVT_DOC_GRP_ID],
                PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_TITLE], 
                PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_DESCRIPTION]};

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
    }
    
    public void commandSendEmail(HttpServletRequest request){
        String [] cc  = FRMQueryString.requestStringValues(request, "ccEmail");
        String [] bcc = FRMQueryString.requestStringValues(request, "bccEmail");
        String emailAddress = FRMQueryString.requestString(request, "email");
        String subject = FRMQueryString.requestString(request, "subject");
        String message = FRMQueryString.requestStringWithoutInjection(request, "message");
        Long empId = FRMQueryString.requestLong(request, "empId");
        Long docId = FRMQueryString.requestLong(request, "relevanDocId");
        
        Vector attachment = new Vector(); 
        Vector<String> attachmentName = new Vector(); 
        Vector<String> emailAddressLogs = new Vector();
        emailAddressLogs.add("List send via emailAddress by Dimata Hairisma System on "+ Formater.formatDate(new java.util.Date(),"yyyy-MM-dd hh:mm:ss"));
        
        String whereClause = PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_RELEVANT_ID] + " = " + docId;
        Vector docAttach = PstEmpRelevantDoc.list(0, 0, whereClause, "");
        for (int em = 0; em < docAttach.size(); em++){
            EmpRelevantDoc empRelevantDoc = (EmpRelevantDoc) docAttach.get(em);
            MimeBodyPart messageBodyPart=new MimeBodyPart();
            String harismaURL ="";
            try{
                 harismaURL= PstSystemProperty.getValueByName("IMGDOC");
            } catch (Exception e){}

            DataSource fds=new FileDataSource(harismaURL+empRelevantDoc.getFileName());

            attachment.add(fds);
            attachmentName.add(empRelevantDoc.getFileName());
        }
        Vector<String> listRec = new Vector();
        Vector<String> listCc = new Vector();
        Vector<String> listBcc = new Vector();
        listRec.add("" + emailAddress);
        
        if (cc != null){
            for (int i=0; i < cc.length; i++){
                listCc.add(""+ cc[i]);
            }
        }
        
        if (bcc != null){
            for (int x=0; x < bcc.length; x++){
                listBcc.add(""+ bcc[x]);
            }
        }

        if (cc != null && bcc != null){
            email.sendEmail(listRec, listCc, listBcc, subject, message, attachment, attachmentName);
        } else if (cc != null && bcc == null){
            email.sendEmail(listRec, listCc, null, subject, message, attachment, attachmentName);
        } else if (cc == null && bcc != null){
            email.sendEmail(listRec, null, listBcc, subject, message, attachment, attachmentName);
        } else {
            email.sendEmail(listRec, null, null, subject, message, attachment, attachmentName);
        }
        
    }
    
    public void commandUpload(HttpServletRequest request){
        long oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
        try {
            ImageLoader uploader = new ImageLoader();
            //int numFiles = uploader.uploadImage(config, request, response);

            //System.out.println("oid di proses upload image : "+oidmaterial);
            String fieldFormName = "FRM_DOC";
            Object obj = uploader.getImage(fieldFormName);

            String fileName = uploader.getFileName();

            byte[] byteOfObj = (byte[]) obj;
            int intByteOfObjLength = byteOfObj.length;
            SessDataUpload objSessDataUpload = new SessDataUpload();	
            if (intByteOfObjLength > 0) {
                String pathFileName = objSessDataUpload.getAbsoluteFileName(fileName);
                try{
                        PstEmpRelevantDoc.updateFileName(fileName, oid);
                        
                        System.out.println("update sukses.."+fileName);
                 }catch(Exception e){
                        System.out.println("err update.."+e.toString())	;
                 }
                java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);
                uploader.writeCache(byteIns, pathFileName, true);
                try {
                    //PROSES UPDATE

                } catch (Exception eY) {
                    System.out.print("");
                }

            }
            
        } catch(Exception ex){
        
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
        
        if(dataFor.equals("listEmpRelevantDoc")){
	     whereClause += " ("+PstEmpRelevantDocGroup.fieldNames[PstEmpRelevantDocGroup.FLD_DOC_GROUP]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_FILE_NAME]+" LIKE '%"+this.searchTerm+"%')"
                       + "AND "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listEmpRelevantDoc")){
	    total = PstEmpRelevantDoc.getCountDataTable(whereClause);
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
        EmpRelevantDoc empRlvtDoc = new EmpRelevantDoc();
        EmpRelevantDocGroup empRlvtDocGroup = new EmpRelevantDocGroup();
        String whereClause = "";
        String order ="";
        String document = "";
	boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += ""+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";                  
        }else{
	     if(dataFor.equals("listEmpRelevantDoc")){
               whereClause += " ("+PstEmpRelevantDocGroup.fieldNames[PstEmpRelevantDocGroup.FLD_DOC_GROUP]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_TITLE]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_DESCRIPTION]+" LIKE '%"+this.searchTerm+"%'"
                       + " OR "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_FILE_NAME]+" LIKE '%"+this.searchTerm+"%')"
                       + "AND "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+" LIKE '%"+this.empId+"%'";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listEmpRelevantDoc")){
	    listData = PstEmpRelevantDoc.listDataTable(this.start, this.amount,whereClause,order);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
	    if(datafor.equals("listEmpRelevantDoc")){
		empRlvtDoc = (EmpRelevantDoc) listData.get(i);
                try {
                    empRlvtDocGroup = PstEmpRelevantDocGroup.fetchExc(empRlvtDoc.getEmpRelvtDocGrpId());
                } catch (Exception exc){}
                if (privUpdate){
                    String checkButton = "<input type='checkbox' name='"+FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_RELEVANT_ID]+"' class='"+FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_RELEVANT_ID]+"' value='"+empRlvtDoc.getOID()+"'>" ;
                    ja.put(""+checkButton);
                }
		ja.put(""+(this.start+i+1));
		ja.put(""+empRlvtDocGroup.getDocGroup());
                ja.put(""+empRlvtDoc.getDocTitle());
		ja.put(""+empRlvtDoc.getDocDescription());
                if(!(empRlvtDoc.getFileName().equals(""))){
                        document = approot+"imgdoc/"+  empRlvtDoc.getFileName();
                    }
                
                String buttonView = "<a href='"+document+"' class='fancybox'> "+empRlvtDoc.getFileName()+"</a>";
                
                ja.put(""+buttonView);
                
		String buttonUpdate = "";
                String buttonSend = "";
		if(privUpdate){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+empRlvtDoc.getOID()+"' data-for='showEmpRelevantDocForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
                    buttonUpdate += "<button class='btn btn-success btnupload btn-xs' data-oid='"+empRlvtDoc.getOID()+"' data-for='showEmpRelevantDocForm' type='button' data-toggle='tooltip' data-placement='top' title='Upload'><i class='fa fa-cloud-upload'></i></button> ";
                    //buttonSend += " <button class='btn btn-primary btnsend btn-xs' data-oid='"+empRlvtDoc.getOID()+"' data-empid='"+empRlvtDoc.getEmployeeId()+"' type='button' data-toggle='tooltip' data-placement='top' title='Send Email' ><i class='fa fa-envelope'></i></button> " ;
                    buttonSend += "<button class='btn btn-primary btn-xs btnsend' id='sendEmail' data-oid='"+empRlvtDoc.getOID()+"' data-for='showSendEmail' data-empid='"+empRlvtDoc.getEmployeeId()+"' data-toggle='tooltip' data-placement='top' title='Send Email'><i class='fa fa-envelope'></i></a> ";
                    ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empRlvtDoc.getOID()+"' data-for='deleteEmpRlvtDocSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> " +buttonSend );
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
    
    public String empRelevantDocForm(){
	
	//CHECK DATA
	EmpRelevantDoc empRelevantDoc = new EmpRelevantDoc();
	
	if(this.oid != 0){
	    try{
		empRelevantDoc = PstEmpRelevantDoc.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        
        Vector docGroup_key = new Vector(1,1);
        Vector docGroup_val = new Vector(1,1);
        
        docGroup_key.add("");
        docGroup_val.add("Select Document Group...");
        
        Vector listRlvtDocGroup = PstEmpRelevantDocGroup.listAll();
        for (int i = 0; i < listRlvtDocGroup.size(); i++){
            EmpRelevantDocGroup empRlvtGroup = (EmpRelevantDocGroup) listRlvtDocGroup.get(i);
            docGroup_key.add(""+empRlvtGroup.getOID());
            docGroup_val.add(""+empRlvtGroup.getDocGroup());
        }
        
        String returnData = ""
                + "<input type='hidden' name='"+ FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_FILE_NAME]+"'  id='"+ FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_FILE_NAME]+"' class='form-control' value='"+empRelevantDoc.getFileName()+"'>"
                + "<div class='form-group'>"
		    + "<label>Document Group *</label>"
                        + ""+ControlCombo.draw(FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_EMP_RELVT_DOC_GRP_ID], null, ""+empRelevantDoc.getEmpRelvtDocGrpId()+"", docGroup_key, docGroup_val, "id="+FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_EMP_RELVT_DOC_GRP_ID], "form-control")+" "
                + "</div>"
		+ "<div class='form-group'>"
    		    + "<label>Title *</label>"
                        + "<input type='text' placeholder='Input Title...' name='"+ FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_TITLE]+"'  id='"+ FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_TITLE]+"' class='form-control' value='"+(empRelevantDoc.getDocTitle() == null ? "" : empRelevantDoc.getDocTitle()) +"'>"
                + "</div>"
                + "<div class='form-group'>"
		    + "<label>Description</label>"
			+ "<textarea row='5' placeholder='Type Description...' name='"+ FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_DESCRIPTION]+"'  id='"+ FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_DESCRIPTION]+"' class='form-control'>"+empRelevantDoc.getDocDescription()+"</textarea>"
		+ "</div>";
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
                            + "<input type='email' name='email' id='email' class='form-control'>"
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
    