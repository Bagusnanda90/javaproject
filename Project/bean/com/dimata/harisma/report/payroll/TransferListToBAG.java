/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.payroll;

import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.util.Formater;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Description : Report Bank Artha Graha Date : June 23, 2015
 *
 * @author Hendra Putu
 */
public class TransferListToBAG extends HttpServlet {

    /**
     * Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {
    }

    /**
     * Creates a new instance of TransferListXLS
     */
    public TransferListToBAG() {
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        System.out.println("---===| BAG  Report |===--- ");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("text/txt");//application/msword");

        PrintWriter out = response.getWriter(); //response.getOutputStream();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = formatDate.format(cal.getTime());
        String tgl = dateFormat.substring(8);
        String bln = dateFormat.substring(5, 7);
        String thn = dateFormat.substring(2,4);//

        /* get data from session */
        HttpSession sessEmpPresence = request.getSession(true);
        Vector vctPayRollList = null;
        String headerBCA = null;
        try {
            vctPayRollList = (Vector) sessEmpPresence.getValue("QUERY_REPORT");
            headerBCA = (String) sessEmpPresence.getValue("HEADER_REPORT_BCA");
        } catch (Exception e) {
            System.out.println("Exc : " + e.toString());
        }

        String period = "";
        String summ = "";
        String departmentName = "";
        Vector listSalaryPdf = new Vector(1, 1);
        String footerNote = "";
        String totalTransfered = "";
        Period periodObj = null;
        if (vctPayRollList != null && vctPayRollList.size() == 7) {
            try {
                listSalaryPdf = (Vector) vctPayRollList.get(0);
                period = (String) vctPayRollList.get(1);
                summ = (String) vctPayRollList.get(2);
                departmentName = (String) vctPayRollList.get(3);
                footerNote = (String) vctPayRollList.get(4);
                totalTransfered = (String) vctPayRollList.get(5);
                //if(vctPayRollList.size()>6){
                periodObj = (Period) vctPayRollList.get(6);
                //}
                if (departmentName.equals("")) {
                    departmentName = " ALL DEPARTMENT";
                } else {
                    departmentName = departmentName;
                }
            } catch (Exception e) {
                System.out.println("exc on get List vctMonthlyAbsence : " + e.toString());
            }
        } else {
            out.println("Data parameter is in complete !");
        }
        if (headerBCA != null && headerBCA.length() > 0) {
            if (periodObj != null && headerBCA.contains("MMYYYY")) {
                headerBCA = headerBCA.replace("MMYYYY", Formater.formatDate(periodObj.getPaySlipDate(), "MMyyyy"));
            }
            int idxPoint = headerBCA.indexOf(".");
            if (idxPoint > 0) {
                String strTotal = "" + totalTransfered;
                int strLen = strTotal.length();
                if (strLen > 0 && (idxPoint > strLen)) {
                    headerBCA = headerBCA.substring(0, idxPoint - strLen) + strTotal + headerBCA.substring(idxPoint);
                }
            }
            //out.println(headerBCA.toCharArray());
        }

        for (int i = 0; i < listSalaryPdf.size() - 1; i++) { // last vector item is summary
            try {
                Vector itemPayroll = (Vector) listSalaryPdf.get(i);
                String bankAcc = String.valueOf(itemPayroll.get(5));
                if (bankAcc == null || bankAcc.length() == 0) {  // bank account sepanjang 11 character
                    bankAcc = "00000000000000";
                } else {
                    bankAcc = "9999"+bankAcc;
                }
                out.print(bankAcc); // BANK ACC

                String totalPaid = String.valueOf(itemPayroll.get(6));
                if (totalPaid == null || totalPaid.length() == 0) {  // total paid sepanjang 13 character
                    totalPaid = "        0000000";
                } else {
                    totalPaid = totalPaid.trim();
                    totalPaid = totalPaid.replaceAll(",", "");
                    totalPaid = totalPaid.replaceAll("-", "");
                    int totalSpace = 15 - totalPaid.length();
                    String space = "";
                    for(int j=0; j<totalSpace; j++){
                        space +=" ";
                    }
                    totalPaid = space + totalPaid +"+"+tgl+bln+thn;
                    
                }
                out.print(totalPaid); // Total                        
                out.println(" ");
                
            } catch (Exception exc) {
                out.println("Data failed on number " + (i + 1) + " " + exc);
            }
        }
        out.flush();
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}