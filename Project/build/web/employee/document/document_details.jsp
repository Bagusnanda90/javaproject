<%-- 
    Document   : document_details
    Created on : 29-Jul-2016, 17:23:18
    Author     : Acer
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidEmpDoc = FRMQueryString.requestLong(request, "EmpDocument_oid");
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

EmpDoc empDoc1 = new EmpDoc();
DocMaster empDocMaster1 = new DocMaster();
DocMasterTemplate empDocMasterTemplate = new DocMasterTemplate();

CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
ControlLine ctrLine = new ControlLine();

iErrCode = ctrlEmpDoc.action(iCommand , oidEmpDoc);
/* end switch*/
FrmEmpDoc frmEmpDoc = ctrlEmpDoc.getForm();

String empDocMasterTemplateText = "";

try {
    empDoc1 = PstEmpDoc.fetchExc(oidEmpDoc); 
} catch (Exception e){ }
if (empDoc1 != null){
try {
    empDocMaster1 = PstDocMaster.fetchExc(empDoc1.getDoc_master_id());
} catch (Exception e){ }
try {
    empDocMasterTemplateText = PstDocMasterTemplate.getTemplateText(empDoc1.getDoc_master_id());
} catch (Exception e){ }
}



String whereDocMasterFlow = " "+PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID] + " = \"" + empDoc1.getDoc_master_id() +"\"";
                                                 
Vector listMasterDocFlow = PstDocMasterFlow.list(0,0, whereDocMasterFlow, "");
String whereList = " "+PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
Hashtable hashtableEmpDocFlow = PstEmpDocFlow.Hlist(0, 0, whereList, "FLOW_INDEX");  

// cek komplit untuk approval
boolean nstatus = true;
for (int x = 0; x < listMasterDocFlow.size(); x++){
     DocMasterFlow docMasterFlow = (DocMasterFlow) listMasterDocFlow.get(x);
     EmpDocFlow empDocFlow = new EmpDocFlow();
            if (hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id()) != null){
                empDocFlow = (EmpDocFlow) hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id());
            }
     if (empDocFlow.getOID() == 0){
         nstatus = false;
     }
}
int show = 1;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Document Details | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 --> 
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/css/bootstrap.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<%= approot%>/assets/font-awesome/4.6.1/css/font-awesome.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="<%= approot%>/assets/ionicons/2.0.1/css/ionicons.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="<%= approot%>/assets/plugins/select2/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/AdminLTE.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/skins/skin-blue.css">
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/datepicker/bootstrap-datepicker3.css">
        <link rel="stylesheet" href="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= approot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= approot%>/assets/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .modal-body {
            max-height: calc(100vh - 212px);
            overflow-y: auto;
            }
        </style>
    </head>
    
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Master Data
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Master Data</li>
                        <li class="active">Position</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmEmpDoc" method ="post" action="" >
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
				      <input type="hidden" name="EmpDocument_oid" value="<%=oidEmpDoc%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td class="listtitle" width="37%">&nbsp;</td>
                                                      <td width="63%" class="comment">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              
                                             
                                              <%//=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_DATE], employee.getBirthDate() == null ? new Date() : employee.getBirthDate(), 0, -150, "formElemen")%>    
                                                        
                                             <% 
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
                                                
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("<;", "<");
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");
                                                String tanpaeditor = empDocMasterTemplateText;
                                                String subString = "";
                                                String stringResidual = empDocMasterTemplateText;
                                                Vector vNewString = new Vector();

                                                Hashtable empDOcFecthH = new Hashtable();
                                                
                                                try {
                                                    empDOcFecthH = PstEmpDoc.fetchExcHashtable(oidEmpDoc); 
                                                } catch (Exception e){ }
                                                
                                                String where1 = " "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                Hashtable hlistEmpDocField = PstEmpDocField.Hlist(0, 0, where1, ""); 
                                                
                                                int startPosition = 0 ;
                                                int endPosition = 0; 
                                                try {
                                                do {
                                                    
                                                        ObjectDocumentDetail objectDocumentDetail = new ObjectDocumentDetail();
                                                        startPosition = stringResidual.indexOf("${") + "${".length();
                                                        endPosition = stringResidual.indexOf("}", startPosition);
                                                        subString = stringResidual.substring(startPosition, endPosition);
                                                        
                                                        
                                                        //cek substring
                                                        
                                                        
                                                            String []parts = subString.split("-");
                                                            String objectName = "";
                                                            String objectType = "";
                                                            String objectClass = "";
                                                            String objectStatusField = "";
                                                            try{
                                                            objectName = parts[0]; 
                                                            objectType = parts[1];
                                                            objectClass = parts[2];
                                                            objectStatusField = parts[3];
                                                            } catch (Exception e){
                                                                System.out.printf("pastikan 4 parameter");
                                                            }
                                                             
                                                            
                                                        //cek dulu apakah hanya object name atau tidak
                                                        if  (!objectName.equals("") && !objectType.equals("") && !objectClass.equals("") && !objectStatusField.equals("")){
                                                        
                                                            
                                                            //jika list maka akan mencari penutupnya..
                                                        if  (objectType.equals("SINGLE") && objectStatusField.equals("START")){
                                                            String add = "<a href=\"javascript:cmdAddEmpSingle('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                            tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                                                            int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                                                                //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                                                                String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                                                               //menghapus tutup formula 
                                                               String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                               Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 
                                                               EmpDocList empDocList = new EmpDocList();
                                                               Employee employeeFetch = new Employee();
                                                               Hashtable HashtableEmp = new Hashtable();
                                                               if (listEmp.size() > 0 ){
                                                                   try {
                                                                       empDocList = (EmpDocList) listEmp.get(listEmp.size()-1);
                                                                       employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                       HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                   }catch (Exception e){}
                                                                
                                                               }
                                                                      
                                                               int xx = 2 ;
                                                                      int startPositionOfFormula = 0;
                                                                      int endPositionOfFormula = 0;
                                                                      String subStringOfFormula = "";
                                                                      String residuOfsubStringOfTd = textString;
                                                                       do{
                                                                                  
                                                                                startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 
                                                                      
                                                                                   
                                                                                String []partsOfFormula = subStringOfFormula.split("-");
                                                                                String objectNameFormula = partsOfFormula[0]; 
                                                                                String objectTypeFormula = partsOfFormula[1];
                                                                                String objectTableFormula = partsOfFormula[2];
                                                                                String objectStatusFormula = partsOfFormula[3];
                                                                                String value = "";
                                                                                 if (objectTableFormula.equals("EMPLOYEE")){
                                                                                         value = (String) HashtableEmp.get(objectStatusFormula);
                                                                                         if (value == null){
                                                                                             value = "-";
                                                                                         }
                                                                                      
                                                                                } else {
                                                                                    System.out.print("Selain Object Employee belum bisa dipanggil");
                                                                                }
                                                                           
                                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-"+objectStatusFormula+"}", value); 
                                                                                
                                                                                tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-"+objectStatusFormula+"}", value); 
                                                                                residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                
                                                                                startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                              }while(endPositionOfFormula > 0);
                                                               
                                                              
                                                          
                                                                                                                              
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                                                                tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       
                                                            
                                                        }else if  (objectType.equals("LIST") && objectStatusField.equals("START")){
                                                            String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                            tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                                                            int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                                                                //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                                                                String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                                                               //menghapus tutup formula 
                                                              
                                                          
                                                                //mencari jumlah table table
                                                                  int startPositionOfTable = 0;
                                                                  int endPositionOfTable = 0;
                                                                  String subStringOfTable = "";
                                                                  String residueOfTextString = textString;
                                                                  do{
                                                                      //cari tag table pembuka
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      //int tt = textString.indexOf("&lttable") + ((textString.indexOf("&gt") + 3)-textString.indexOf("&lttable"));
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      subStringOfTable = residueOfTextString.substring(startPositionOfTable, endPositionOfTable);//isi table 
                                                                      
                                                                      //mencari body 
                                                                      int startPositionOfBody = subStringOfTable.indexOf("<tbody>") + "<tbody>".length();
                                                                      int endPositionOfBody = subStringOfTable.indexOf("</tbody>", startPositionOfBody);
                                                                      String subStringOfBody = subStringOfTable.substring(startPositionOfBody, endPositionOfBody);//isi body
                                                                      
                                                                      //mencari tr pertama pada table
                                                                      int startPositionOfTr1 = subStringOfBody.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr1 = subStringOfBody.indexOf("</tr>", startPositionOfTr1);
                                                                      String subStringOfTr1 = subStringOfBody.substring(startPositionOfTr1, endPositionOfTr1);
                                                                      
                                                                      String subStringOfBody2 = subStringOfBody.substring(endPositionOfTr1, subStringOfBody.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr2 = subStringOfBody2.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr2 = subStringOfBody2.indexOf("</tr>", startPositionOfTr2);
                                                                      String subStringOfTr2 = subStringOfBody2.substring(startPositionOfTr2, endPositionOfTr2);
                                                                      
                                                                      //disini diisi perulanganya
                                                                      
                                                                     String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                                     Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 

                                                                     //baca table dibawahnya

                                                                         String stringTrReplace = ""; 
                                                                    if (listEmp.size() > 0 ){
                                                                     for(int list = 0 ; list < listEmp.size(); list++ ){
                                                                         
                                                                         EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                         Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                         Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                         
                                                                         
                                                                      
                                                                     stringTrReplace = stringTrReplace+"<tr>"; 
                                                                      
                                                                      //menghitung jumlah td html
                                                                      int startPositionOfTd = 0;
                                                                      int endPositionOfTd = 0;
                                                                      String subStringOfTd = "";
                                                                      String residuOfsubStringOfTr2 = subStringOfTr2 ;
                                                                      int jumlahtd = 0;
                                                                      
                                                                      
                                                                      
                                                                      do{
                                                                      
                                                                      stringTrReplace = stringTrReplace+"<td>";  
                                                                      
                                                                      startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                      endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 
                                                                      
                                                                      int startPositionOfFormula = 0;
                                                                      int endPositionOfFormula = 0;
                                                                      String subStringOfFormula = "";
                                                                      String residuOfsubStringOfTd = subStringOfTd;
                                                                              do{
                                                                                  
                                                                                startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 
                                                                      
                                                                                   
                                                                                String []partsOfFormula = subStringOfFormula.split("-");
                                                                                String objectNameFormula = partsOfFormula[0]; 
                                                                                String objectTypeFormula = partsOfFormula[1];
                                                                                String objectTableFormula = partsOfFormula[2];
                                                                                String objectStatusFormula = partsOfFormula[3];
                                                                                String value = "";
                                                                                if (objectTableFormula.equals("EMPLOYEE")){
                                                                                         value = (String) HashtableEmp.get(objectStatusFormula);
                                                                                      
                                                                                } else {
                                                                                    System.out.print("Selain Object Employee belum bisa dipanggil");
                                                                                }
                                                                                
                                                                                stringTrReplace = stringTrReplace+value;  
                                                                                
                                                                                residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                              }while(endPositionOfFormula > 0);
                                                                      
                                                                      
                                                                             
                                                                        residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      jumlahtd = jumlahtd + 1 ;
                                                                      
                                                                      stringTrReplace = stringTrReplace+"</td>"; 
                                                                      }while(endPositionOfTd > 0);
                                                                      
                                                                      }
                                                                                                                                              }
                                                                         
                                                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                                                        tanpaeditor = tanpaeditor.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                                                     //tutup perulanganya
                                                                      
                                                                      //setelah baca td maka akan membuat td baru... disini
                                                                      
                                                                       residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());
                                                                        
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      
                                                                  }  while ( endPositionOfTable > 0);
                                                                
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                                                                tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       
                                                            
                                                        } else if  (objectType.equals("LISTMUTATION") && objectStatusField.equals("START")){
                                                            String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                            tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                                                            int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                                                                //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                                                                String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                                                               //menghapus tutup formula 
                                                              
                                                          
                                                                //mencari jumlah table table
                                                                  int startPositionOfTable = 0;
                                                                  int endPositionOfTable = 0;
                                                                  String subStringOfTable = "";
                                                                  String residueOfTextString = textString;
                                                                  do{
                                                                      //cari tag table pembuka
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      //int tt = textString.indexOf("&lttable") + ((textString.indexOf("&gt") + 3)-textString.indexOf("&lttable"));
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      subStringOfTable = residueOfTextString.substring(startPositionOfTable, endPositionOfTable);//isi table 
                                                                      
                                                                      //mencari body 
                                                                      int startPositionOfBody = subStringOfTable.indexOf("<tbody>") + "<tbody>".length();
                                                                      int endPositionOfBody = subStringOfTable.indexOf("</tbody>", startPositionOfBody);
                                                                      String subStringOfBody = subStringOfTable.substring(startPositionOfBody, endPositionOfBody);//isi body
                                                                      
                                                                      //mencari tr pertama pada table
                                                                      int startPositionOfTr1 = subStringOfBody.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr1 = subStringOfBody.indexOf("</tr>", startPositionOfTr1);
                                                                      String subStringOfTr1 = subStringOfBody.substring(startPositionOfTr1, endPositionOfTr1);
                                                                      
                                                                      String subStringOfBody2 = subStringOfBody.substring(endPositionOfTr1, subStringOfBody.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr2 = subStringOfBody2.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr2 = subStringOfBody2.indexOf("</tr>", startPositionOfTr2);
                                                                      String subStringOfTr2 = subStringOfBody2.substring(startPositionOfTr2, endPositionOfTr2);
                                                                      
                                                                      String subStringOfBody3 = subStringOfBody2.substring(endPositionOfTr2, subStringOfBody2.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr3 = subStringOfBody3.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr3 = subStringOfBody3.indexOf("</tr>", startPositionOfTr3);
                                                                      String subStringOfTr3 = subStringOfBody3.substring(startPositionOfTr3, endPositionOfTr3);
                                                                      
                                                                      String subStringOfBody4 = subStringOfBody3.substring(endPositionOfTr3, subStringOfBody3.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr4 = subStringOfBody4.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr4 = subStringOfBody4.indexOf("</tr>", startPositionOfTr4);
                                                                      String subStringOfTr4 = subStringOfBody4.substring(startPositionOfTr4, endPositionOfTr4);
                                                                      
                                                                      String subStringOfBody5 = subStringOfBody4.substring(endPositionOfTr4, subStringOfBody4.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr5 = subStringOfBody5.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr5 = subStringOfBody5.indexOf("</tr>", startPositionOfTr5);
                                                                      String subStringOfTr5 = subStringOfBody5.substring(startPositionOfTr5, endPositionOfTr5);
                                                                      
                                                                      //disini diisi perulanganya
                                                                      
                                                                     String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                                     Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 

                                                                     //baca table dibawahnya

                                                                         String stringTrReplace = ""; 
                                                                    if (listEmp.size() > 0 ){
                                                                     for(int list = 0 ; list < listEmp.size(); list++ ){
                                                                         
                                                                         EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                         Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                         Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                         
                                                                         //cek nilai ada object atau tidak
                                                                         
                                                                         
                                                                      
                                                                     stringTrReplace = stringTrReplace+"<tr>"; 
                                                                      
                                                                      //menghitung jumlah td html
                                                                      int startPositionOfTd = 0;
                                                                      int endPositionOfTd = 0;
                                                                      String subStringOfTd = "";
                                                                      String residuOfsubStringOfTr2 = subStringOfTr5 ;
                                                                      int jumlahtd = 0;
                                                                      
                                                                      
                                                                      
                                                                      do{
                                                                      
                                                                      stringTrReplace = stringTrReplace+"<td>";  
                                                                      
                                                                      startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                      endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 
                                                                      
                                                                      int startPositionOfFormula = 0;
                                                                      int endPositionOfFormula = 0;
                                                                      String subStringOfFormula = "";
                                                                      String residuOfsubStringOfTd = subStringOfTd;
                                                                              do{
                                                                                try {
                                                                                startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 
                                                                      
                                                                                   
                                                                                String []partsOfFormula = subStringOfFormula.split("-");
                                                                                String objectNameFormula = partsOfFormula[0]; 
                                                                                String objectTypeFormula = partsOfFormula[1];
                                                                                String objectTableFormula = partsOfFormula[2];
                                                                                String objectStatusFormula = partsOfFormula[3];
                                                                                String value = "";
                                                                                if (objectTableFormula.equals("EMPLOYEE")){
                                                                                    if (objectStatusFormula.equals("NEW_POSITION")){
                                                                                        String select = "<a href=\"javascript:cmdSelectPos('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"','"+employeeFetch.getOID()+"')\">SELECT</a></br>";
                                                                
                                                                                        String newposition = PstEmpDocListMutation.getNewPosition(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "-"+select;
                                                                                        value = "-"+newposition;
                                                                                    }  else { 
                                                                                        if (objectStatusFormula.equals("NEW_GRADE_LEVEL_ID")){
                                                                                            String newGrade = PstEmpDocListMutation.getNewGradeLevel(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "";
                                                                                            value = "-"+newGrade;
                                                                                        } else if (objectStatusFormula.equals("NEW_HISTORY_TYPE")){
                                                                                            String newHistoryType = PstEmpDocListMutation.getNewHistoryType(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "";
                                                                                            value = "-"+newHistoryType;
                                                                                        } else {
                                                                                            value = (String) HashtableEmp.get(objectStatusFormula);
                                                                                        }
                                                                                         
                                                                                    }  
                                                                                } else {
                                                                                    System.out.print("Selain Object Employee belum bisa dipanggil");
                                                                                }
                                                                                
                                                                                stringTrReplace = stringTrReplace+value;  
                                                                                
                                                                                residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                              } catch (Exception e){
                                                                              System.out.printf(e+"");
                                                                              }
                                                                             }  while(endPositionOfFormula > 0);
                                                                      
                                                                      
                                                                             
                                                                        residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      jumlahtd = jumlahtd + 1 ;
                                                                      
                                                                      stringTrReplace = stringTrReplace+"</td>"; 
                                                                      }while(endPositionOfTd > 0);
                                                                      
                                                                      }
                                                                                                                                              }
                                                                         
                                                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr5+"</tr>", stringTrReplace); 
                                                                        tanpaeditor = tanpaeditor.replace("<tr>"+subStringOfTr5+"</tr>", stringTrReplace); 
                                                                     //tutup perulanganya
                                                                      
                                                                      //setelah baca td maka akan membuat td baru... disini
                                                                      
                                                                       residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());
                                                                        
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      
                                                                  }  while ( endPositionOfTable > 0);
                                                                
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                                                                tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       
                                                            
                                                        }  else if  (objectType.equals("LISTLINE") && objectStatusField.equals("START")){
                                                            String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                               
                                                                //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                                                               //menghapus tutup formula 
                                                               String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                               Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 
                                                               String subListLine ="";
                                                                if (listEmp.size() > 0 ){
                                                                     for(int list = 0 ; list < listEmp.size(); list++ ){
                                                                         EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                         Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                         Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                         String value = (String) HashtableEmp.get("FULL_NAME");
                                                                         if ((listEmp.size()-2) == list ){
                                                                            subListLine = subListLine+value+" dan ";  
                                                                         } else {
                                                                            subListLine = subListLine+value+", ";
                                                                         }
                                                                     }
                                                                }
                                                               //subListLine = subListLine.substring(0,subListLine.length()-1);
                                                               add = add + subListLine ;
                                                             empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add ); 
                                                             tanpaeditor = tanpaeditor.replace("${"+subString+"}", subListLine);       
                                                            
                                                        } else if  (objectType.equals("FIELD") && objectStatusField.equals("AUTO")){
                                                                //String field = "<input type=\"text\" name=\""+ subString +"\" value=\"\">";
                                                            Date newd = new Date();
                                                                String field = "04/KEP/BPD-PMT/"+newd.getMonth()+"/"+newd.getYear();
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", field); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", field); 
                                                              
                                                        } else if  (objectType.equals("FIELD")){
                                                            
                                                            if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXT"))){
                                                               //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                                                                String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"NEW TEXT")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                                                            } else if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("DATE"))){
                                                                String dateShow = "";
                                                                if (hlistEmpDocField.get(objectName) != null){
                                                                    SimpleDateFormat formatterDateSql = new SimpleDateFormat("yyyy-MM-dd");
                                                                    String dateInString = (String)hlistEmpDocField.get(objectName);		

                                                                    SimpleDateFormat formatterDate = new SimpleDateFormat("dd MMM yyyy");
                                                                    try {

                                                                            Date dateX = formatterDateSql.parse(dateInString);
                                                                            dateShow = formatterDate.format(dateX);

                                                                    } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                    }
                                                                }
                                                                String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?dateShow:"NEW DATE")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?dateShow:" ")); 
                                                            } else if ((objectClass.equals("CLASSFIELD"))){
                                                                //cari tahu ini untuk perubahan apa Divisi
                                                                 String getNama ="";
                                                                if (hlistEmpDocField.get(objectName) != null){
                                                                    getNama = PstDocMasterTemplate.getNama((String)hlistEmpDocField.get(objectName), objectStatusField);
                                                                }
            
                                                                String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?getNama:"ADD")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                                                            
                                                            } else if ((objectClass.equals("EMPDOCFIELD"))){
                                                                //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(empDOcFecthH.get(objectName) != null?(String)empDOcFecthH.get(objectName):"add")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", ""+empDOcFecthH.get(objectStatusField) ); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", ""+empDOcFecthH.get(objectStatusField) ); 
                                                            }
                                                                
                                                        }
                                                        
                                                        } else if (!objectName.equals("") && objectType.equals("") && objectClass.equals("") && objectStatusField.equals("")) {
                                                              String obj = ""+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"-");
                                                                 empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"}", obj);
                                                                 tanpaeditor = tanpaeditor.replace("${"+objectName+"}", obj);
                                                        }
                                                        stringResidual = stringResidual.substring(endPosition, stringResidual.length());
                                                        objectDocumentDetail.setStartPosition(startPosition);
                                                        objectDocumentDetail.setEndPosition(endPosition);
                                                        objectDocumentDetail.setText(subString);
                                                        vNewString.add(objectDocumentDetail);
                                                        
                                                        
                                                        //mengecek apakah masih ada sisa
                                                        startPosition = stringResidual.indexOf("${") + "${".length();
                                                        endPosition = stringResidual.indexOf("}", startPosition);
                                                 } while ( endPosition > 0);
                                                 } catch (Exception e){}
                                              
                                             %>
                                             <%                                               
                                                
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");
   
                                             %>
                                              
                                              
                                              <tr align="left" valign="top" > 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center"  cellspacing="0" cellpadding="0" >
                                                    <tr>
                                                      <td width="37%">&nbsp;</td>
                                                    </tr>
                                                    <tr>
                                                      <td width="37%">&nbsp;</td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                              
                                             <tr align="left" valign="top" > 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center" border="1"  cellspacing="0" cellpadding="0" style="background-color: white; ">
                                                    <tr>
                                                      <td width="37%"><%=empDocMasterTemplateText%></td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                           
                                              
                                               <% if(nstatus){%>
                                               <tr align="left" valign="top" > 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center" border="0"  cellspacing="0" cellpadding="0" style="margin-top: 10 ; margin-bottom: 10 ;">
                                                    <tr>
                                                      <td width="37%"><button id="btn1" onclick="cmdPrint('<%=oidEmpDoc%>')">EDITOR & PRINT</button> || <button id="btn1" onclick="cmdExpense('<%=oidEmpDoc%>')">EXPENSE</button> || 
                                                      
                                                      <% 
                                                       String whereClause1 = PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_DOC_MASTER_ID]+" = "+ empDoc1.getDoc_master_id();
                                                       Vector listDocMasterAction = PstDocMasterAction.list(0, 0, whereClause1, "");
                                                        if (listDocMasterAction.size() > 0 ) { 
                                                        for (int i = 0; i < listDocMasterAction.size(); i++){
                                                           DocMasterAction docMasterAction1 =  (DocMasterAction) listDocMasterAction.get(i);
                                                        %>  
                                                          
                                                        <button id="btn2" onclick="cmdAction('<%=oidEmpDoc%>','<%=docMasterAction1.getOID() %>')"  >ACTION <%=docMasterAction1.getActionTitle() %></button> 
                                                      
                                                        <%
                                                                 }
                                                        }
                                                        %>
                                                      
                                                      
                                                      </td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                              
                                              
                                              
                                              <%} else {%>
                                              <tr align="left" valign="top" > 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center" border="0"  cellspacing="0" cellpadding="0" style="margin-top: 10 ; margin-bottom: 10 ;">
                                                    <tr>
                                                      <td width="37%">
                                                      tombol editor, expense maupun action hanya akan muncul jika document sudah di approve
                                                      </td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                              <%} %>
                                             
                                              
                                          <!-- <textarea class="ckeditor"  class="elemenForm" cols="70" rows="40"></textarea> -->
                                       
                                        
                                            </table>
                                          </td>										  
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        
                                      </table>
                                    </form>
                                    <!-- #EndEditable -->
                            </td>
                          </tr>
                        </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
            <%@ include file="../../template/footer.jsp" %>
        </div>
        <div id="myModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">

                <!-- Modal content-->

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="addeditgeneral-title"></h4>
                    </div>
                    <form id="form-modal">
                        <input type="hidden" name="oid" id="oid">
                        <input type="hidden" name="datafor" id="datafor">
                        <input type="hidden" name="command" id="command">
                        <div class="modal-body active-scroll" id="modalbody" >
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                            <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="<%= approot%>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%= approot%>/assets/bootstrap/js/bootstrap.js"></script>
        <!-- Select2 -->
        <script src="<%= approot%>/assets/plugins/select2/select2.full.min.js"></script>
        <!-- FastClick -->
        <script src="<%= approot%>/assets/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="<%= approot%>/assets/dist/js/app.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="<%= approot%>/assets/plugins/slimScroll/jquery.slimscroll.js"></script>
        
        <script src="<%= approot%>/assets/bootstrap/datepicker/bootstrap-datepicker.js"></script>
        
        <!-- Datatables -->
        <script src="<%= approot %>/assets/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
        
        <script>
            $(function () {
                $(".comboDivision").select2({
                    placeholder: "Division"
                });
                $(".comboCompany").select2({
                    placeholder: "Company"
                });
                $(".comboDepartment").select2({
                    placeholder: "Department"
                });
                $(".comboSection").select2({
                    placeholder: "Section"
                });
                $(".comboPosition").select2({
                    placeholder: "Position"
                });
                
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function(){
                function modalSetting(selector, keyboard, show, backdrop){
                    $(selector).modal({
                        show : keyboard,
                        backdrop : backdrop,
                        keyboard : keyboard
                    });
                }
                
                var datePicker = function(contentId, formatDate){
                    $(contentId).datepicker({
                 format : formatDate
                    });
                    $(contentId).on('changeDate', function(ev){
                 $(this).datepicker('hide');
                    });
                };
                
                
                
                function sendData(url, datasend, onDone, onSuccess){
                   //alert("WORK");
                    $.ajax({
                        type    : "GET",
                        data    : datasend,
                        url     : url,
                        success : function(data){
                            onSuccess(data);
                        },
                        error: function(xhr,err){
                            alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
                            alert("responseText: "+xhr.responseText);
                        }
                    }).done(function(data){
                        onDone(data);
                    });
                    
                }
                
                function paging(selector){
                    $(selector).click(function(){
                        var start = $(this).data("start");
                        var commandlist = $(this).data("command");
                        $("#start").val(start);
                        $("#commandlist").val(commandlist);
                        loadData("#listdata");
                    })
                }
                
                function loadData(selector){
                       var oid = $(this).data("oid");
                       var dataFor = "listpos";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           $('#listPosition').DataTable( {
                                
                            } );
                           addEditTable("#listPosition", ".addeditdata");
                           deleteData(".deletedata")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/masterdata/ajax/position_ajax.jsp", dataSend, onDone, onSuccess);
                   
                }
                
                function deleteData(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadData("#listdata")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/masterdata/ajax/position_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                
                function addEdit(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showposform'){
			    $(".addeditgeneral-title").html("Edit Position");
			}

                        }else{
                            if(dataFor == 'showposform'){
                                $(".addeditgeneral-title").html("Add Position");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                           datePicker(".datepicker","yyyy-mm-dd");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/masterdata/ajax/position_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                addEdit(".addeditdata");
                function addEditTable(selectorTable, selector){
                    $(selectorTable).on("click", selector, function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showposform'){
			    $(".addeditgeneral-title").html("Edit Position");
			}

                        }else{
                            if(dataFor == 'showposform'){
                                $(".addeditgeneral-title").html("Add Position");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                           datePicker(".datepicker","yyyy-mm-dd");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/masterdata/ajax/position_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                modalSetting("#myModal", false, false, 'static');
                loadData("#listdata");
                
                $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModal").modal("hide");
                        loadData("#listdata");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/masterdata/ajax/position_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                });
            });
        </script>
    </body>
</html>
