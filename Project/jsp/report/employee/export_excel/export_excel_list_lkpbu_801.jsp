<%-- 
    Document   : export_excel_list_lkpbu_801
    Created on : Aug 11, 2015, 3:44:55 PM
    Author     : khirayinnura
--%>

<%@page import="org.apache.poi.ss.usermodel.Workbook"%>
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
        <table>
            <tr>
                <td>
                    <table border="1">
                        <tr style="text-align: center">
                            <td colspan="6">Sandi Pelapor</td>
                            <td colspan="3">Jenis Periode Laporan</td>
                            <td colspan="4">Priode Data Laporan</td>
                            <td colspan="5">Jenis Laporan</td>
                            <td colspan="2">No Form</td>
                            <td colspan="3">Jumlah Record Isi</td>
                        </tr>
                        <tr style="text-align: center">
                            <td colspan="6"></td>
                            <td colspan="3"></td>
                            <td colspan="4"></td>
                            <td colspan="5"></td>
                            <td colspan="2">801</td>
                            <td colspan="3"><%=listResult.size()%></td>
                        </tr>
                        <tr style="text-align: center">
                            <td>Status Data</td>
                            <td>NIP</td>
                            <td>Nama Pejabat Eksekutif</td>
                            <td>Status Tenaga Kerja</td>
                            <td>Nama Jabatan</td>
                            <td>Alamat Rumah (Saat Ini)</td>
                            <td>Alamat KTP atau Paspor atau KITAS (WNA)</td>
                            <td>No. Telp</td>
                            <td>No. Fax</td>
                            <td>NPWP</td>
                            <td>No. ID</td>
                            <td>Tempat Lahir</td>
                            <td>Tgl Lahir</td>
                            <td>Kewarganegaraan</td>
                            <td>Jenis kelamin</td>
                            <td>No. Surat Pelaporan</td>	
                            <td>Tanggal Surat Pelaporan</td>
                            <td>Status PE</td>
                            <td>Nomor Surat Keputusan Pengangkatan/Penggantian/Penggantian Sementara</td>
                            <td>Tanggal Efektif Pengangkatan/Penggantian/Penggantian Sementara</td>
                            <td>Nomor Surat Keputusan Pemberhentian</td>
                            <td>Tanggal Efektif Pemberhentian</td>
                            <td>Keterangan</td>
                        </tr>
                        <%
                            for(int i = 0; i < listResult.size(); i++) {
                                Employee employee = (Employee)listResult.get(i);
                        %>
                        <tr>
                            <td>1</td>
                            <td>&nbsp;<%="0000000000000000"+employee.getEmployeeNum()%></td>

                           <% String fullname = employee.getFullName();
                            String[] splits = fullname.split(",");
                            ArrayList name= new ArrayList();
                            for(int j=0; j < splits.length ; j++){
                                name.add(splits[j].trim());
                            }%>
                            <td>&nbsp;<%=splits[0]%></td>
                            <td>1</td>
                            <td>&nbsp;<%=employee.getPositionName()+" "+employee.getDepartmentName()%></td>
                            <td>&nbsp;<%=employee.getAddress()%></td>
                            <td>&nbsp;<%=employee.getAddressPermanent()%></td>
                            <td>&nbsp;<%=employee.getPhone()%></td>
                            <td></td>

                            <%
                                String npwp = employee.getNpwp();
                                npwp = npwp.replaceAll("[-,.]", "");
                            %>
                            <td>&nbsp;<%=npwp%></td>

                            <td>&nbsp;<%=employee.getIndentCardNr()%></td>
                            <td>&nbsp;<%=employee.getBirthPlace()%></td>
                            <td>
                                <%if(employee.getBirthDate() != null){%>
                                    &nbsp;<%=Formater.formatDate(employee.getBirthDate(),"ddMMyyyy")%>
                                <%} else {
                                    System.out.println("-");
                                }%>
                            </td>
                            <td>ID</td>
                            <%
                                if(employee.getSex() == 0) {
                                    %><td>1</td>
                                <%} else {%>
                                    <td>2</td><%
                                }
                            %>
                            <td></td>
                            <td></td>
                            <td>1</td>
                            <td></td>
                            <td>
                                <%if(employee.getCommencingDate() != null){%>
                                    &nbsp;<%=Formater.formatDate(employee.getCommencingDate(),"ddMMyyyy")%>
                                <%} else {
                                    System.out.println("-");
                                }%>
                            </td>
                            <td></td>
                            <td>
                                <%if(employee.getResignedDate() != null){%>
                                &nbsp;<%=(Formater.formatDate(employee.getResignedDate(),"ddMMyyyy"))%>
                                <%} else {
                                    System.out.println("-");
                                }%>
                            </td>
                            <td></td>
                        </tr>
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
