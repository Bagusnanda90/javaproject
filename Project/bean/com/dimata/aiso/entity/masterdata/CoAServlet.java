/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.aiso.entity.masterdata;

import com.dimata.qdep.form.FRMQueryString;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ktanjana
 */
public class CoAServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            int groupid = FRMQueryString.requestInt(request, "groupid");
            System.out.println("groupid=" + groupid);

            if (groupid != 0) {
                response.setContentType("text/html");
                System.out.println("groupid=" + generateCoA(groupid));
                response.getWriter().write(generateCoA(groupid));
            }


        } finally {
            out.close();
        }
    }

    public String generateCoA(int groupid) {
        StringBuffer returnData = null;
        if (groupid >= 0) {
            Vector vectAccountList = PstPerkiraan.getAllAccount(groupid, 0);
            

           // Vector accCodeKey = new Vector(1, 1);
            //Vector accOptionStyle = new Vector(1, 1);
            //Vector accCodeVal = new Vector(1, 1);
           
            int vectSize = vectAccountList.size();
            Vector listCode = new Vector(1, 1);
            int SESS_LANGUAGE = com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN; 
            String strNoAccSelected = (SESS_LANGUAGE == com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN) ? "- No Reference Account -" : "- Tidak ada referensi perkiraan -";

            Perkiraan noParent = new Perkiraan();
            noParent.setOID(0);
            noParent.setNama(strNoAccSelected);
            noParent.setAccountNameEnglish(strNoAccSelected);
            listCode.add(noParent);
            for (int item = 0; item < vectSize; item++) {
                Perkiraan account = (Perkiraan) vectAccountList.get(item);
                
                if (account.getPostable() == PstPerkiraan.ACC_NOTPOSTED) {
                    listCode.add(account); System.out.println(""+item+" " + account.getNoPerkiraan() + "       " + account.getNama() + " >> Header");
                }else{ System.out.println(""+item+" " + account.getNoPerkiraan() + "       " + account.getNama()); }
            }
            
            if (listCode != null && listCode.size() > 0) {
                String space = "";
                String style = "";
                String tempCoAs="";
                
                for (int i = 0; i < listCode.size(); i++) {
                    Perkiraan perk = (Perkiraan) listCode.get(i);
                    switch (perk.getLevel()) {
                        case 1:
                            space = "";
                            style = "Style=\"font-weight:bold; color:#000000\"";
                            break;
                        case 2:
                            space = "__";
                            style = "Style=\"font-weight:bold; color:#000000\"";
                            break;
                        case 3:
                            space = "________";
                            style = "Style=\"font-weight:bold; color:#000000\"";
                            break;
                        case 4:
                            space = "___________";
                            style = "Style=\"font-weight:bold; color:#000000\"";
                            break;
                        case 5:
                            space = "______________";
                            style = "Style=\"font-weight:bold; color:#000000\"";
                            break;
                        case 6:
                            space = "_________________";
                            style = "Style=\"font-weight:bold; color:#000000\"";
                            break;
                    }
                   //accCodeKey.add("" + perk.getOID());
                   //accOptionStyle.add(style);
                   //accCodeVal.add(space + perk.getNoPerkiraan() + "       " + perk.getNama());
                   System.out.println(space + perk.getNoPerkiraan() + "       " + perk.getNama());
                   tempCoAs= tempCoAs +  "{ \"id\":\"" + perk.getOID()+"\",\"name\":\""+ space +
                           perk.getNoPerkiraan() + "       " + perk.getNama() +"\"}" + ( (i+1)==listCode.size() ? "": "," );
                }
                returnData = new StringBuffer("\"CoAs\": [ " + tempCoAs + "]");  // ; "{ \"id\":\"101\",\"name\":\"IT services\"},{\"id\":\"102\",\"name\":\"software\"}
                
            }
        }
        return returnData == null ? "" : returnData.toString();
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
        return "Get List of CoA Parents, or List ";
    }// </editor-fold>
}
