<%-- 
    Document   : export_excel_list_lkpbu_801
    Created on : Aug 11, 2015, 3:44:55 PM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.harisma.entity.report.lkpbu.Lkpbu"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  java.text.*,				  
                  com.dimata.qdep.form.*,				  
                  com.dimata.gui.jsp.*,
                  com.dimata.util.*,				  
                  com.dimata.harisma.entity.masterdata.*,				  				  
                  com.dimata.harisma.entity.employee.*,
                  com.dimata.harisma.entity.attendance.*,
                  com.dimata.harisma.entity.search.*,
                  com.dimata.harisma.form.masterdata.*,				  				  
                  com.dimata.harisma.form.attendance.*,
                  com.dimata.harisma.form.search.*,				  
                  com.dimata.harisma.session.attendance.*,
                  com.dimata.harisma.session.leave.SessLeaveApp,
                  com.dimata.harisma.session.leave.*,
                  com.dimata.harisma.session.attendance.SessLeaveManagement,
                  com.dimata.harisma.session.leave.RepItemLeaveAndDp"%>
<!-- package qdep -->
<%@ include file = "../../../main/javainit.jsp" %>

<!DOCTYPE html>

<%
    Vector listResult = new Vector(1, 1);
   // int year = session.getValue("year");
    

    if(session.getValue("listresult")!=null){
        listResult = (Vector)session.getValue("listresult"); 
    }
    
    Vector listKadiv = new Vector(1, 1);

    if(session.getValue("listkadiv")!=null){
        listKadiv = (Vector)session.getValue("listkadiv"); 
    }
   
%>
<%@page contentType="application/x-msexcel" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
        <!-- #EndEditable -->
    </head>
    <body>
        
        
        
        <%
                            String code = "";
                            int codeAtt = 0;
                            int totalRecord = 0;
                            for(int i = 0; i < listResult.size(); i++) {
                            Lkpbu lkpbu = (Lkpbu)listResult.get(i);
                            if(i == 0){
                                code = lkpbu.getCode();
                                codeAtt = lkpbu.getTrainAteendes();
                            }

                            if(lkpbu.getCode().equals(code)){
                             codeAtt = codeAtt + lkpbu.getTrainAteendes();

                             if(i == ( listResult.size()-1) ){ 
                                 totalRecord++;            
                             }

                            } else { 
                                if(codeAtt!=0){
                                totalRecord++;

                                }
                                code = lkpbu.getCode();
                                codeAtt = 0;
                                i--;
                            }
                        %>
                        <%}%>
        
        
        
        
        <table>
            <tr>
                <td>
                    <center>
                        <p>
                            <b>TENAGA KERJA PERBANKAN</b><br>
                            <b>JUMLAH DAN JENIS PELATIHAN KARYAWAN Form 806</b>
                         </p>
                    </center>
                </td>
            </tr>
            <tr>
                <td>
                    <table border="1">
                        <tr style="text-align: center">
                            <td>Sandi Pelapor</td>
                            <td>Jenis Periode Laporan</td>
                            <td>Periode Data Laporan</td>
                            <td>Jenis Laporan</td>
                            <td>No Form</td>
                            <td>Jumlah Record Isi</td>  
                        </tr>
                        <tr style="text-align: center">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>805</td>
                            <td><%=totalRecord%></td>  
                        </tr>
                        <tr style="text-align: center">
                            <td>Jenis Pelatihan</td>               
                            <td colspan="5">Jumlah SDM</td>
                        </tr>
                        <%
                             code = "";
                             codeAtt = 0;

                            for(int i = 0; i < listResult.size(); i++) {
                            Lkpbu lkpbu = (Lkpbu)listResult.get(i);
                            if(i == 0){
                                code = lkpbu.getCode();
                                codeAtt = lkpbu.getTrainAteendes();
                            }

                            if(lkpbu.getCode().equals(code)){
                             codeAtt = codeAtt + lkpbu.getTrainAteendes();

                             if(i == ( listResult.size()-1) ){ %>
                                    <tr>
                                        <td>&nbsp;<%=code%></td>
                                        <td colspan="5">&nbsp;<%=codeAtt%></td>
                                    </tr>
                                    <%}

                            } else { 
                                if(codeAtt!=0){%>
                                <tr>
                                    <td>&nbsp;<%=code%></td>
                                    <td colspan="5">&nbsp;<%=codeAtt%></td>
                                </tr>
                        <%

                                }
                                code = lkpbu.getCode();
                                codeAtt = 0;
                                i--;
                            }
                        %>
                        <%}%>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" >
                        <%
                            if(listKadiv.size() > 0){
                            Lkpbu lkpbu = (Lkpbu)listKadiv.get(0);
                            String str_dt_now = ""; 
                            Date dt_NowDate = new Date();
                            str_dt_now = Formater.formatDate(dt_NowDate, "dd MMMM yyyy");
                        %>
                        <tr style="text-align: center">
                            <td colspan="3"></td>
                            <td colspan="3">......................, <%=str_dt_now%></td>
                        </tr>
                        <tr style="text-align: center">
                            <td colspan="3"></td>
                            <td colspan="3"><%=lkpbu.getCompanyTtd()%></td>
                        </tr>
                        <tr style="text-align: center">
                            <td colspan="3"></td>
                            <td colspan="3"><%=lkpbu.getDivisiTtd()%></td>
                        </tr>
                        <tr style="text-align: center">
                            <td colspan="3"></td>
                            <td colspan="3">Kepala,</td>
                        </tr>
                        <tr>
                            <td colspan="3"></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="3"></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="3"></td>
                            <td colspan="3"></td>
                        </tr>
                        <tr style="text-align: center">
                            <td colspan="3"></td>
                            <td colspan="3"><b><u><%=lkpbu.getNameTtd()%></u></b></td>
                        </tr>
                        <tr style="text-align: center">
                            <td colspan="3"></td>
                            <td colspan="3"><b>NRK.<%=lkpbu.getEmpNumTtd()%></b></td>
                        </tr>
                        <%}%>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>

