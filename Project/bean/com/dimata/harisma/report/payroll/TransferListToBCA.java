/*
 * TransferListXLS.java
 *
 * Created on December 24, 2007, 2:27 PM
 */

package com.dimata.harisma.report.payroll;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import java.io.InputStream; 
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.dimata.qdep.form.*;
import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.session.employee.SessEmployee;
import java.io.PrintWriter;


/**
 *
 * @author  Yunny
 */
public class TransferListToBCA  extends HttpServlet{
    
     /** Initializes the servlet.
    */  
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

     /** Destroys the servlet.
    */  
    public void destroy() {

    }
    
    /** Creates a new instance of TransferListXLS */
    public TransferListToBCA() {
    }
    
      /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        
        System.out.println("---===| BCA  Report |===--- ");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("text/txt");//application/msword");

        PrintWriter out =  response.getWriter(); //response.getOutputStream();
        
                
         /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctPayRollList = null;
            String headerBCA = null;
            try{
	        vctPayRollList = (Vector)sessEmpPresence.getValue("QUERY_REPORT");
                headerBCA = (String) sessEmpPresence.getValue("HEADER_REPORT_BCA");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
        }
               
        String period = "";
        String summ = "";
        String departmentName = "";
        Vector listSalaryPdf = new Vector(1,1);
        String footerNote = "";
        String totalTransfered = "";
        Period periodObj = null;        
         if(vctPayRollList != null && vctPayRollList.size()==7){
                try{
                        listSalaryPdf = (Vector)vctPayRollList.get(0);
	            	period = (String) vctPayRollList.get(1);
                        summ = (String) vctPayRollList.get(2);
                        departmentName = (String) vctPayRollList.get(3);
                        footerNote = (String) vctPayRollList.get(4);
                        totalTransfered = (String) vctPayRollList.get(5);
                        //if(vctPayRollList.size()>6){
                            periodObj = (Period) vctPayRollList.get(6);
                        //}
                        if(departmentName.equals("")){
                            departmentName = " ALL DEPARTMENT";
                        }else{
                            departmentName =departmentName;
                        }                                                                    
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }else{
             out.println("Data parameter is in complete !");
         }           
        if(headerBCA!=null && headerBCA.length()>0){            
            if(periodObj!=null && headerBCA.contains("MMYYYY")){
                 headerBCA = headerBCA.replace("MMYYYY",  Formater.formatDate(periodObj.getPaySlipDate(), "MMyyyy"));
            }
            int idxPoint = headerBCA.indexOf(".");
            if(idxPoint>0){
                String strTotal = ""+totalTransfered;
                int strLen = strTotal.length();
                if(strLen>0 && (idxPoint >strLen) ){
                    headerBCA= headerBCA.substring(0, idxPoint-strLen)+strTotal+headerBCA.substring(idxPoint);
                }
            }            
            out.println(headerBCA.toCharArray());
        }    
      
        for (int i = 0; i < listSalaryPdf.size()-1; i++) { // last vector item is summary
            try{
            Vector itemPayroll = (Vector) listSalaryPdf.get(i);
            String bankAcc = String.valueOf(itemPayroll.get(5));
            if(bankAcc==null || bankAcc.length()==0){  // bank account sepanjang 11 character
                bankAcc = "00000000000";
            }else{
                bankAcc=bankAcc.replaceAll("-", "");
                //bankAcc=bankAcc.replaceAll(".", "");
                bankAcc=bankAcc.replaceAll(" ", "");
                bankAcc=bankAcc.trim();
             if(bankAcc.length()>11){
                bankAcc = bankAcc.substring(bankAcc.length()-11);
             }else{
                 if(bankAcc.length()<11){
                     bankAcc = "00000000000".substring(bankAcc.length()) + bankAcc ;
                 }
             }   
            }                    
            out.print(bankAcc); // BANK ACC
            
            String totalPaid = String.valueOf(itemPayroll.get(6));                    
            if(totalPaid==null || totalPaid.length()==0){  // total paid sepanjang 13 character
                totalPaid = "0000000000000";
            }else{
             totalPaid=totalPaid.trim();
             totalPaid=totalPaid.replaceAll(",","");                
             if(totalPaid.length()>13){
                totalPaid = totalPaid.substring(totalPaid.length()-13);
             }else{
                 if(totalPaid.length()<13){
                     totalPaid = "0000000000000".substring(totalPaid.length()) + totalPaid ;
                 }
             }   
            }                                
            out.print(totalPaid); // Total                        
            
            if(itemPayroll.size()==8){ // payroll size = 7 character
              String payRoll = String.valueOf(itemPayroll.get(7));
            if(payRoll==null || payRoll.length()==0){  // total paid sepanjang 13 character
                payRoll = "0000000";
            }else{
                payRoll=payRoll.trim();
             if(payRoll.length()>7){
                payRoll = payRoll.substring(payRoll.length()-7);
             }else{
                 if(payRoll.length()<7){
                     payRoll = "0000000".substring(payRoll.length()) + payRoll ;
                 }
             }   
            }                                              
              out.print(payRoll); 
            }else{
                out.print("0000000");
            }
            String accName = String.valueOf(itemPayroll.get(1));
            if(accName==null){
                accName="--N0 NAME--";
            }
            accName=accName.trim();
            out.print(String.valueOf("    "+accName)); // Nama length bebas
            
            String dept =""+itemPayroll.get(2);
            if(dept==null || dept.length()==0){  // total paid sepanjang 13 character
                dept = "----";
            }else{
             if(dept.length()>4){
                dept = dept.substring(0,4);
             }else{
                 if(dept.length()<4){
                     dept = dept+ "----".substring(dept.length()) ;
                 }
             }   
            }                                              
            
            out.println("    "+dept); // DEPT
            //out.print(String.valueOf(itemAbsence.get(3))+" "); // SEC
            //out.print(String.valueOf(itemAbsence.get(4))+" "); // COMMENCING
            }catch(Exception exc){
                out.println("Data failed on number "+(i+1)+" "+exc);
            }
         }
        out.flush();
    }
    
    
     /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    } 

    
      /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
}
