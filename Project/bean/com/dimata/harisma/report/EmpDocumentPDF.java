
package com.dimata.harisma.report;

import java.io.*;
import java.sql.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.dimata.util.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.session.employee.*;
import com.dimata.qdep.form.*;

/**
 *
 * @author guest
 */
public class EmpDocumentPDF extends HttpServlet {   
  
 
    // setting the color values
    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(240, 240, 240);
    
    // setting some fonts in the color chosen by the user
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLDITALIC, border);
    public static Font fontSubTitle = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD, border);
    public static Font fontListHeader = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLDITALIC, border);
    public static Font fontLsContent = new Font(Font.HELVETICA, 10);
    public static Font fontFooter = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, border);
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.ITALIC, border);

    /** Processes requests for both HTTP <code>GET</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

      
        Document document = new Document(PageSize.A4.rotate());
        document.setMargins(50, 50, 50, 50);

        Long oidEmpDoc = FRMQueryString.requestLong(request, "oid");   
        
        
        EmpDoc empDoc1 = new EmpDoc();
        DocMaster empDocMaster1 = new DocMaster();
        DocMasterTemplate empDocMasterTemplate = new DocMasterTemplate(); 

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
        String subString = "";
                                                String stringResidual = empDocMasterTemplateText;
                                                Vector vNewString = new Vector();

                                                 String where1 = " "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                 Hashtable hlistEmpDocField = PstEmpDocField.Hlist(0, 0, where1, ""); 
                                                
                                                int startPosition = 0 ;
                                                int endPosition = 0;                                             
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
                                                        if  (objectType.equals("LIST") && objectStatusField.equals("START")){
                                                            String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                
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
                                                                     for(int list = 0 ; list < listEmp.size(); list++ ){
                                                                         EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                         Employee employeeFetch =new Employee();
                                                                         Hashtable HashtableEmp = new Hashtable();
                                                                         try {
                                                                            employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                            HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                         }catch (Exception e){
                                                                             
                                                                         }
                                                                        
                                                                       
                                                                         
                                                                         
                                                                      
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
                                                                                        //if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME])){
                                                                                        //    value = (String) employeeFetch.getFullName();
                                                                                        //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS])){
                                                                                        //    value = (String) employeeFetch.getAddress();
                                                                                        //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM])){
                                                                                        //    value = (String) employeeFetch.getEmployeeNum();
                                                                                        //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_NPWP])){
                                                                                        //    value = (String) employeeFetch.getNpwp();
                                                                                        //} else if (objectStatusFormula.equals(PstEmpDocList.fieldNames[PstEmpDocList.FLD_JOB_DESC])){
                                                                                        //    value = (String) empDocList.getJob_desc();
                                                                                        //} else if (objectStatusFormula.equals(PstEmpDocList.fieldNames[PstEmpDocList.FLD_ASSIGN_AS])){
                                                                                        //    value = (String) empDocList.getAssign_as();
                                                                                        //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_BPJS_NO])){
                                                                                        //    value = (String) employeeFetch.getBpjs_no();
                                                                                        //}
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
                                                                         
                                                                         
                                                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                                                     //tutup perulanganya
                                                                      
                                                                      //setelah baca td maka akan membuat td baru... disini
                                                                      
                                                                       residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());
                                                                        
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      
                                                                  }  while ( endPositionOfTable > 0);
                                                                
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                                                                    
                                                            
                                                        } else if  (objectType.equals("FIELD") && objectStatusField.equals("AUTO")){
                                                                //String field = "<input type=\"text\" name=\""+ subString +"\" value=\"\">";
                                                            Date newd = new Date();
                                                                String field = "04/KEP/BPD-PMT/"+newd.getMonth()+"/"+newd.getYear();
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", field); 
                                                              
                                                        } else if  (objectType.equals("FIELD")){
                                                            if ((objectClass.equals("DOCFIELD")) && (objectStatusField.equals("TEXT"))){
                                                                String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                            }  else {
                                                               //  Class test = Class.forName(objectClass);
                                                            }
                                                                
                                                        }
                                                        
                                                        } else if (!objectName.equals("") && objectType.equals("") && objectClass.equals("") && objectStatusField.equals("")) {
                                                              String obj = ""+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"-");
                                                                 empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"}", obj);
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
        
        
        String pathImage = "";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            HeaderFooter header = new HeaderFooter(new Phrase("Employee Document ", fontTitle), false);           
            header.setAlignment(Element.ALIGN_LEFT); 
            document.setHeader(header); 
                                 
            HeaderFooter footer = new HeaderFooter(new Phrase("Hairisma ", fontFooter), true);           
            footer.setAlignment(Element.ALIGN_RIGHT); 
            document.setFooter(footer); 

            document.open();

            // get data from session 
            Vector listTraining = new Vector();
            SrcTrainingTarget srcTraining = new SrcTrainingTarget();
            HttpSession sess = request.getSession(true);
            
         document.add(new Paragraph(empDocMasterTemplateText));
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }

        // closing the document
        document.close();

        // we have written the pdfstream to a ByteArrayOutputStream,
        // now we are going to write this outputStream to the ServletOutputStream       
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }

 
    
}
