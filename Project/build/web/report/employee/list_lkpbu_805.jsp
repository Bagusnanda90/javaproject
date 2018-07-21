<%-- 
    Document   : list_lkpbu_805
    Created on : Aug 12, 2015, 11:56:42 AM
    Author     : khirayinnura
--%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.entity.report.lkpbu.PstLkpbu805"%>
<%@page import="com.dimata.harisma.form.report.lkpbu.CtrlLkpbu805"%>
<%@page import="com.dimata.harisma.entity.report.lkpbu.Lkpbu805"%>
<%@page import="com.dimata.harisma.form.report.lkpbu.FrmLkpbu805"%>
<%@page import="com.dimata.harisma.entity.report.lkpbu.PstLkpbu"%>
<%@page import="com.dimata.harisma.entity.report.lkpbu.Lkpbu"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%!
public String drawListEdit(int iCommand, Vector listLkpbu, long lkpbuId, FrmLkpbu805 frmObject, int year) 
{ 
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
                 
        ctrlist.addHeader("Jenis Pekerjaan","2%", "2", "");
        ctrlist.addHeader("Jenis Pendidikan","2%", "2", "");
        ctrlist.addHeader("Status Pegawai","2%", "2", "");
        ctrlist.addHeader("Tahun Realisasi","2%", "2", "");
        ctrlist.addHeader("Jumlah Tenaga Kerja","2%", "", "4");
        ctrlist.addHeader("Tahun Prediksi 1","1%", "0", "0");
        ctrlist.addHeader("Tahun Prediksi 2","1%", "0", "0");
        ctrlist.addHeader("Tahun Prediksi 3","1%", "0", "0");
        ctrlist.addHeader("Tahun Prediksi 4","1%", "0", "0");
        
        if(iCommand != Command.EDIT) {
            ctrlist.setLinkRow(0);
        }
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        
       
        //level
            Vector lvlid_value = new Vector(1, 1);
            Vector lvlid_key = new Vector(1, 1);

            lvlid_key.add("");
            lvlid_value.add("---Pilih---");

            Vector listLvl = PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_CODE]);
            if (listLvl != null && listLvl.size() > 0) {
                for (int j = 0; j < listLvl.size(); j++) {
                    Level lvl = (Level) listLvl.get(j);
                    lvlid_value.add(""+lvl.getCode()+" ("+lvl.getLevel()+")");
                    lvlid_key.add("" + lvl.getOID());
                }
            }
      //Education
            Vector eduid_value = new Vector(1, 1);
            Vector eduid_key = new Vector(1, 1);

            eduid_key.add("");
            eduid_value.add("---Pilih---");

            Vector listEdu = PstEducation.list(0, 0, "", PstEducation.fieldNames[PstEducation.FLD_EDUCATION_LEVEL]);
            if (listEdu != null && listEdu.size() > 0) {
                for (int j = 0; j < listEdu.size(); j++) {
                    Education edu = (Education) listEdu.get(j);
                    eduid_value.add(""+edu.getEducationLevel()+" ("+edu.getEducation()+")");
                    eduid_key.add("" + edu.getOID());
                }
            }
        // Emp Category
            Vector catid_value = new Vector(1, 1);
            Vector catid_key = new Vector(1, 1);

            catid_key.add("");
            catid_value.add("---Pilih---");

            Vector listCat = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_CODE]);
            if (listCat != null && listCat.size() > 0) {
                for (int j = 0; j < listCat.size(); j++) {
                    EmpCategory empCategory = (EmpCategory) listCat.get(j);
                    catid_value.add(""+empCategory.getCode()+" ("+empCategory.getEmpCategory()+")");
                    catid_key.add(""+ empCategory.getOID());
                }
            }
        
        Vector rowx = new Vector(1,1);
        for(int i = 0; i < listLkpbu.size(); i++) {
            Lkpbu805 lkpbu805 = (Lkpbu805)listLkpbu.get(i);
            rowx = new Vector(1,1);
           
            if(iCommand == Command.EDIT && lkpbuId == lkpbu805.getOID()) {
                rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_LEVEL_ID], null, ""+lkpbu805.getLevelId(), lvlid_key, lvlid_value, "", "formElemen"));
                rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_EDUCATION_ID], null, ""+lkpbu805.getEducationId(), eduid_key, eduid_value, "", "formElemen"));
                rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_EMP_CATEGORY_ID], null, ""+lkpbu805.getEmpCategoryId(), catid_key, catid_value, "", "formElemen"));
                rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 1\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_REALISASI]+"\"  value=\""+ lkpbu805.getLkpbu805YearRealisasi() +"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
                rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 1\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_1]+"\"  value=\""+ lkpbu805.getLkpbu805YearPrediksi1() +"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
                rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 2\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_2]+"\"  value=\""+ lkpbu805.getLkpbu805YearPrediksi2() +"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
                rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 3\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_3]+"\"  value=\""+ lkpbu805.getLkpbu805YearPrediksi3() +"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
                rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 4\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_4]+"\"  value=\""+ lkpbu805.getLkpbu805YearPrediksi4() +"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");

            } else {
                rowx.add(""+lkpbu805.getLevelCode());
                String codeEdu = lkpbu805.getEducationCode();
                int jml = codeEdu.length();
                if(jml == 1){
                    rowx.add("0"+codeEdu);
                } else {
                    rowx.add(""+codeEdu);
                }
                rowx.add(""+lkpbu805.getEmpCategoryCode());
                rowx.add(""+lkpbu805.getLkpbu805YearRealisasi());
                rowx.add(""+lkpbu805.getLkpbu805YearPrediksi1());
                rowx.add(""+lkpbu805.getLkpbu805YearPrediksi2());
                rowx.add(""+lkpbu805.getLkpbu805YearPrediksi3());
                rowx.add(""+lkpbu805.getLkpbu805YearPrediksi4());
            }
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(lkpbu805.getOID()));
        }
        rowx = new Vector(1,1);
        if(iCommand == Command.ADD) {
            rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_LEVEL_ID], null, "", lvlid_key, lvlid_value, "", "formElemen"));
            rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_EDUCATION_ID], null, "", eduid_key, eduid_value, "", "formElemen"));
            rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_EMP_CATEGORY_ID], null, "", catid_key, catid_value, "", "formElemen"));
            rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 1\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_REALISASI]+"\"  value=\"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
            rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 1\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_1]+"\"  value=\"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
            rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 2\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_2]+"\"  value=\"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
            rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 3\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_3]+"\"  value=\"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
            rowx.add("<input type=\"text\" placeholder=\"Tahun Prediksi 4\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_4]+"\"  value=\"\" class=\"elemenForm\" onKeyDown=\"javascript:fnTrapKD()\">");
            lstData.add(rowx);
        }
        
        return ctrlist.drawList();
    }
%>

<%!
    public String drawList(int iCommand, Vector listLkpbu, long lkpbuId, FrmLkpbu805 frmObject, int year, CtrlLkpbu805 ctrlLkpbu805) 
    { 
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
                
        ctrlist.addHeader("Jenis Pekerjaan","2%", "2", "");
        ctrlist.addHeader("Jenis Pendidikan","2%", "2", "");
        ctrlist.addHeader("Status Pegawai","2%", "2", "");
        ctrlist.addHeader("Tahun Realisasi","2%", "2", "");
        ctrlist.addHeader("Jumlah Tenaga Kerja","2%", "", "4");
        ctrlist.addHeader("Tahun Prediksi 1","1%", "0", "0");
        ctrlist.addHeader("Tahun Prediksi 2","1%", "0", "0");
        ctrlist.addHeader("Tahun Prediksi 3","1%", "0", "0");
        ctrlist.addHeader("Tahun Prediksi 4","1%", "0", "0");
        
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Vector rowx = new Vector(1,1);
        
        String codeStatus = "";
        String codeJabatan = "";
        String codeEdu = "";
        int umurPre1 = 0;
        int umurPre2 = 0;
        int umurPre3 = 0;
        int umurPre4 = 0;
        int pensiun = 0;
        int totalRealisasi = 0;
        
        for(int i = 0; i < listLkpbu.size(); i++) {
            Lkpbu805 lkpbu805 = (Lkpbu805)listLkpbu.get(i);
            rowx = new Vector(1,1);
                        
            if(i == 0){
                codeJabatan = lkpbu805.getLevelCode();
                codeEdu = lkpbu805.getEducationCode();
                codeStatus = lkpbu805.getEmpCategoryCode();
            }
            
            if( lkpbu805.getEmpCategoryCode().equals(codeStatus) && lkpbu805.getLevelCode().equals(codeJabatan) && 
                    lkpbu805.getEducationCode().equals(codeEdu)){
                
                totalRealisasi++;
                
                if(lkpbu805.getUmur() == 54){
                    umurPre1++;
                } else if(lkpbu805.getUmur() == 53){
                    umurPre2++;
                } else if(lkpbu805.getUmur() == 52){
                    umurPre3++;
                } else if(lkpbu805.getUmur() == 51){
                    umurPre4++;
                } else if(lkpbu805.getUmur() >= 55){
                    pensiun++;
                } else {
                    
                }
                
                 if(i == ( listLkpbu.size()-1) ){
                        rowx.add(""+codeJabatan);
                
                        int jml = codeEdu.length();
                        if(jml == 1){
                            rowx.add("0"+codeEdu);
                        } else {
                            rowx.add(""+codeEdu);
                        }                

                        rowx.add(""+codeStatus);
                        int prediksi1 = (totalRealisasi - pensiun) - umurPre1;
                        int prediksi2 = (totalRealisasi - pensiun) - umurPre2;
                        int prediksi3 = (totalRealisasi - pensiun) - umurPre3;
                        int prediksi4 = (totalRealisasi - pensiun) - umurPre4;
                        rowx.add(""+totalRealisasi);
                        rowx.add(""+prediksi1);
                        rowx.add(""+prediksi2);
                        rowx.add(""+prediksi3);
                        rowx.add(""+prediksi4);
                        
                        if(iCommand == Command.SAVETEMP){
                                int iErrCode = FRMMessage.NONE;

                                Lkpbu805 lkpbu = new Lkpbu805();

                                String dateS = year+"-01-01";
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
                                Date date = new Date();
                                try {
                                    lkpbu.setLevelId(lkpbu805.getLevelId());
                                    lkpbu.setEducationId(lkpbu805.getEducationId());
                                    lkpbu.setEmpCategoryId(lkpbu805.getEmpCategoryId());
                                    lkpbu.setLkpbu805YearRealisasi(totalRealisasi);
                                    lkpbu.setLkpbu805YearPrediksi1(prediksi1);
                                    lkpbu.setLkpbu805YearPrediksi2(prediksi2);
                                    lkpbu.setLkpbu805YearPrediksi3(prediksi3);
                                    lkpbu.setLkpbu805YearPrediksi4(prediksi4);
                                    date = df.parse(dateS);
                                    lkpbu.setLkpbu805StartDate(date);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                iErrCode = ctrlLkpbu805.action(iCommand, lkpbuId, "", lkpbu);

                            }
                        
                        lstData.add(rowx);
                        lstLinkData.add("0");
                    }
                                                 
            } else {
                
                rowx.add(""+codeJabatan);
                
                int jml = codeEdu.length();
                if(jml == 1){
                    rowx.add("0"+codeEdu);
                } else {
                    rowx.add(""+codeEdu);
                }                
                
                rowx.add(""+codeStatus);
                int prediksi1 = (totalRealisasi - pensiun) - umurPre1;
                int prediksi2 = (totalRealisasi - pensiun) - umurPre2;
                int prediksi3 = (totalRealisasi - pensiun) - umurPre3;
                int prediksi4 = (totalRealisasi - pensiun) - umurPre4;
                rowx.add(""+totalRealisasi);
                rowx.add(""+prediksi1);
                rowx.add(""+prediksi2);
                rowx.add(""+prediksi3);
                rowx.add(""+prediksi4);
                
                if(iCommand == Command.SAVETEMP){
                    int iErrCode = FRMMessage.NONE;
                    
                    Lkpbu805 lkpbu = new Lkpbu805();
                                        
                    String dateS = year+"-01-01";
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
                    Date date = new Date();
                    try {
                        lkpbu.setLevelId(lkpbu805.getLevelId());
                        lkpbu.setEducationId(lkpbu805.getEducationId());
                        lkpbu.setEmpCategoryId(lkpbu805.getEmpCategoryId());
                        lkpbu.setLkpbu805YearRealisasi(totalRealisasi);
                        lkpbu.setLkpbu805YearPrediksi1(prediksi1);
                        lkpbu.setLkpbu805YearPrediksi2(prediksi2);
                        lkpbu.setLkpbu805YearPrediksi3(prediksi3);
                        lkpbu.setLkpbu805YearPrediksi4(prediksi4);
                        date = df.parse(dateS);
                        lkpbu.setLkpbu805StartDate(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    iErrCode = ctrlLkpbu805.action(iCommand, lkpbuId, "", lkpbu);
                    
                }
                        
                codeJabatan = lkpbu805.getLevelCode();
                codeEdu = lkpbu805.getEducationCode();
                codeStatus = lkpbu805.getEmpCategoryCode();
                
                totalRealisasi=0;
                umurPre1 = 0;
                umurPre2 = 0;
                umurPre3 = 0;
                umurPre4 = 0;
                pensiun = 0;
                
                i--;
                                
                lstData.add(rowx);
                lstLinkData.add("0");
            } 
    }
    return ctrlist.drawList();
}          
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidLkpbu805 = FRMQueryString.requestLong(request, "hidden_lkpbu805_id");
    String lkpbu805DeleteStatus = FRMQueryString.requestString(request, "lkpbu805Delete");
    int year = FRMQueryString.requestInt(request, "year");
    
    int recordToGet = 15;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "YEAR(LKPBU_805_START_DATE) = '"+year+"'";
    String whereClauseYear = "YEAR(emp.COMMENCING_DATE) <= '%"+year+"%' && emp.RESIGNED='0'";
    String orderClause = "level_code, edu.EDUCATION_LEVEL, cat_code";
    CtrlLkpbu805 ctrlLkpbu805 = new CtrlLkpbu805(request);
    ControlLine ctrLine = new ControlLine();
    Vector listLkpbu805 = new Vector(1,1);
    Vector listKadiv= new Vector(1,1);
    Vector listLkpbuFiltering = new Vector();
    
    /* end switch list*/
    
    Lkpbu805 lkpbu805 = ctrlLkpbu805.getLkpbu805();
    msgString = ctrlLkpbu805.getMessage();
    
/*switch statement */
    
    if(iCommand != Command.SAVETEMP){
        iErrCode = ctrlLkpbu805.action(iCommand, oidLkpbu805, lkpbu805DeleteStatus, lkpbu805);
    }
        
    FrmLkpbu805 frmLkpbu805 = ctrlLkpbu805.getForm();
    
    //membuat command menjadi none kembali setelah menjalankan delete all
    if(iCommand == Command.DELETE && iErrCode == FRMMessage.NONE) {
        iCommand = Command.NONE;
    }
        
/* end switch*/
    
    int vectSize = PstLkpbu805.getCount(whereClause);
    
/*switch list Student*/
    
    if(iCommand == Command.FIRST || iCommand == Command.NEXT || iCommand == Command.PREV || iCommand == Command.LAST) {
               start = ctrlLkpbu805.action(iCommand, start, vectSize, recordToGet);
    }
    

/* get record to display */

    listLkpbu805 = PstLkpbu805.listLkpbuRealisasi(whereClauseYear, year);
    listLkpbuFiltering = PstLkpbu805.listJoin(0, 0, whereClause, orderClause);
    
/*handle condition if size of record to display = 0 and start > 0 after delete*/
    
    if(listLkpbu805.size() < 1 && start > 0) {
        if(vectSize - recordToGet > recordToGet) {
            start = start - recordToGet; //go to Command.PREV
        } else {
            start = 0 ;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listLkpbu805 = PstLkpbu805.listLkpbuRealisasi(whereClauseYear, year);
    }
    
    session.putValue("listresult", listLkpbuFiltering);
    //get value kadiv HRD
    String kadivPositionOid = PstSystemProperty.getValueByName("HR_DIR_POS_ID");
    String whereClauseOidPosition = "POSITION_ID='"+kadivPositionOid+"'";

    listKadiv = PstLkpbu.listPosition(whereClauseOidPosition);
    session.putValue("listkadiv", listKadiv);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HARISMA - LKPBU 805 Report</title>
        <script language="JavaScript">
            <%if (iCommand == Command.PRINT) {%>
                //com.dimata.harisma.report.listRequest	
                window.open("<%=printroot%>.report.listRequest.ListEmpEducationPdf");
            <%}%>

                function cmdAdd(){
                    document.frmemplkpbu805.command.value="<%=Command.ADD%>";
                    document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                    document.frmemplkpbu805.submit();
                }
                
                function cmdSave(){
                    document.frmemplkpbu805.command.value="<%=Command.SAVE%>";
                    document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                    document.frmemplkpbu805.submit();
                }
                
                function cmdSaveTemp()
                {
                    document.frmemplkpbu805.command.value="<%=Command.SAVETEMP%>";
                    document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                    document.frmemplkpbu805.submit();
                }
                
                function cmdEdit(oid){
                    document.frmemplkpbu805.command.value="<%=Command.EDIT%>";
                    document.frmemplkpbu805.hidden_lkpbu805_id.value=oid;
                    document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                    document.frmemplkpbu805.submit();
                }
                function cmdCancel(){
                    document.frmemplkpbu805.command.value="<%=Command.NONE%>";
                    document.frmemplkpbu805.hidden_lkpbu805_id.value=0;
                    document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                    document.frmemplkpbu805.submit();
                }
                function cmdCancelToList(){
                    document.frmemplkpbu805.command.value="<%=Command.LIST%>";
                    document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                    document.frmemplkpbu805.submit();
                }

                function reportPdf(){
                    document.frmemplkpbu805.command.value="<%=Command.PRINT%>";
                    document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                    document.frmemplkpbu805.submit();
                }

                function cmdSpecialQuery(){
                    document.frmemplkpbu805.action="specialquery.jsp";
                    document.frmemplkpbu805.submit();
                }

                function fnTrapKD(){
                    if (event.keyCode == 13) {
                        document.all.aSearch.focus();
                        cmdSearch();
                    }
                }
                function cmdExportExcel(){
                 
                    var linkPage = "<%=approot%>/report/employee/export_excel/export_excel_list_lkpbu_805.jsp";    
                    var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
                     newWin.focus();
                }
                
                function cmdView()
                {
                        document.frmemplkpbu805.command.value="<%=String.valueOf(Command.LIST)%>";
                        document.frmemplkpbu805.action="list_lkpbu_805.jsp";
                        document.frmemplkpbu805.submit();
                }
                
                function cmdUpload(){
                        document.frmemplkpbu805.command.value="<%=Command.LOAD%>"; 
                        document.frmemplkpbu805.action="../../system/excel_up/up_lkpbu_805.jsp";
                        document.frmemplkpbu805.target="";
                        document.frmemplkpbu805.submit();
                }

                function MM_swapImgRestore() { //v3.0
                    var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
                }

                function MM_preloadImages() { //v3.0
                    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
                            if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                    }

                    function MM_findObj(n, d) { //v4.0
                        var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
                            d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                        if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
                        for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                        if(!x && document.getElementById) x=document.getElementById(n); return x;
                    }

                    function MM_swapImage() { //v3.0
                        var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                            if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                    }

                    function cmdUpdateLevp(){
                        document.frmemplkpbu805.command.value="<%=Command.ADD%>";
                        document.frmemplkpbu805.action="list_lkpbu_805.jsp"; 
                        document.frmemplkpbu805.submit();
                    }
        </script>
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <!-- #EndEditable -->
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr> 
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td> 
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="10" valign="middle"> 

                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table>
                </td> 
            </tr>
            <%}%>
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
                        <tr> 
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
                                    <tr> 
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                <!-- #BeginEditable "contenttitle" --> 
                                                Report &gt;Employee &gt; LKPBU Form 805 <!-- #EndEditable --> 
                                            </strong></font>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" --> 
                                                                                <form name="frmemplkpbu805" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_lkpbu805_id" value="<%=oidLkpbu805%>">
                                                                                    <input type="hidden" name="lkpbu805Delete" value="">
                                                                                    <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="18%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="1%"><%=ControlDate.drawDateYear("year", year==0? Validator.getIntYear(new Date()) : year ,"formElemen", -40)%></td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report LKPBU 805"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View LKPBU 805 Report</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>									  
									  
									  
                                      <% 
                                      if(iCommand == Command.LIST || iCommand == Command.EDIT || iCommand == Command.ADD || iCommand == Command.SAVE || iCommand == Command.SAVETEMP)
                                      {
                                      %>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr>
                                          <td><hr></td>
                                        </tr>
                                        <tr>
                                            <%
                                                if(listLkpbu805.size() != 0 && listLkpbuFiltering.size() < 1){
                                            %>
                                                <td><%=drawList(iCommand, listLkpbu805, oidLkpbu805, frmLkpbu805, year, ctrlLkpbu805)%></td>
                                            <% } else { %>
                                                <td><%=drawListEdit(iCommand, listLkpbuFiltering, oidLkpbu805, frmLkpbu805, year)%></td>
                                            <% } %>
                                            <td></td>
                                        </tr>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                 <%
                                                    if(iCommand == Command.ADD || iCommand == Command.EDIT) {
                                                 %>  
                                                    <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td nowrap><b><a href="javascript:cmdSave()" class="command">Save</a></b></td>
                                                    <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td width="24"><a href="javascript:cmdCancelToList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td nowrap><b><a href="javascript:cmdCancelToList()" class="command">Cancel</a></b></td>
                                                    <%} else if(listLkpbu805.size() != 0 && listLkpbuFiltering.size() < 1 && iCommand == Command.LIST){%>
                                                    <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td width="24"><a href="javascript:cmdSaveTemp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td nowrap><b><a href="javascript:cmdSaveTemp()" class="command">Save</a></b></td>
                                                    <% } else { %>
                                                    <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                    <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td nowrap><b><a href="javascript:cmdAdd()" class="command">Add LKPBU</a></b></td>
                                                    <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td width="24"><a href="javascript:cmdExportExcel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                    <td nowrap><b><a href="javascript:cmdExportExcel()" class="command">Export to Excel</a></b></td>
                                                    <%}%>
                                                
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                      <%
                                          }                                         
                                      %>
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr> 
                                                    <td>&nbsp; </td>
                                                </tr>
                                            </table>
                                        </td> 
                                    </tr>
                                </table>
                            </td> 
                        </tr>
                    </table>
                </td> 
            </tr>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
</html>

