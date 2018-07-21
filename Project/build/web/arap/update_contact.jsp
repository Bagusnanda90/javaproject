<%-- 
    Document   : update_contact
    Created on : Nov 18, 2014, 6:06:03 AM
    Author     : Kartika
--%>

<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.aiso.entity.arap.*"%>
<%@page import="com.dimata.aiso.entity.jurnal.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String content = request.getParameter("username");
        
        long oidContactList = FRMQueryString.requestLong(request, "oid");
        int start = FRMQueryString.requestInt(request, "start");
        long arap_main_id = FRMQueryString.requestLong(request, "arap_main_id");
        long journal_id = FRMQueryString.requestLong(request, "journal_id");
        String message = "";
        
        if(oidContactList!=0){
           if(arap_main_id!=0){
               try{
                    ArApMain arapMain = PstArApMain.fetchExc(arap_main_id);
                    arapMain.setContactId(oidContactList);
                    PstArApMain.updateExc(arapMain);
                    message = "Contact of Ar/Ap " + arapMain.getDescription() +" is updated";
               }catch(Exception exc){
                   message=exc.getMessage();
               }                  
           } 
           if(journal_id!=0){
               try{
                   JurnalUmum jUmum = PstJurnalUmum.fetchExc(journal_id);
                   jUmum.setContactOid(oidContactList);
                   PstJurnalUmum.updateExc(jUmum);
                    message=message+" ; Contact of Journal : "+ jUmum.getKeterangan()+ " is updated";
                      if(arap_main_id==0){
                           ArApMain arapMain = PstArApMain.fetchByJournalId(journal_id);
                           if(arapMain!=null){
                            arapMain.setContactId(oidContactList);
                            PstArApMain.updateExc(arapMain);
                            message = message+ "; Contact of Ar/Ap " + arapMain.getDescription() +" is updated";
                            }
                      }
               }catch(Exception exc){
                    message=message+" ; "+exc.getMessage();
               }
               
           }
        }

	response.setContentType("text/html");
	response.setHeader("Cache-Control", "no-cache");
	response.getWriter().write(message); 
 %>