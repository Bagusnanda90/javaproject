<%-- 
    Document   : reprimand_ajax
    Created on : Dec 14, 2015, 3:29:25 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<%
long oid = FRMQueryString.requestLong(request, "reprimand_id");

long babId = FRMQueryString.requestLong(request, "bab_id");
long pasalId = FRMQueryString.requestLong(request, "pasal_id");
long ayatId = FRMQueryString.requestLong(request, "ayat_id");

I_Dictionary dictionaryD = userSession.getUserDictionary();

if (oid != 0){
    EmpReprimand empReprimand = new EmpReprimand();
    try {
        empReprimand = PstEmpReprimand.fetchExc(oid);
        
        // Cek dulu apakah datanya bisa diconvert ke Long apa tidak <option value="504404606611001786">BAB II - Penerimaan,Penetapan, Pengangkatan &amp; Pemindahan Karyawan</option>
        babId = Long.valueOf(empReprimand.getChapter());
        pasalId = Long.valueOf(empReprimand.getArticle());
        ayatId = Long.valueOf(empReprimand.getVerse());
    } catch(Exception e){
        System.out.print(""+e.toString());
    }
}

%>

<div class="caption">
    Chapter/Bab
</div>
<div class="divinput">
    <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_CHAPTER]%>" onchange="loadPasal(this.value)">
        <option value="0">-select-</option>
        <%
        Vector listBab = PstWarningReprimandBab.list(0, 0, "", PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID]);
        if (listBab != null && listBab.size()>0){
            for(int i=0; i<listBab.size(); i++){
                WarningReprimandBab cbBab = (WarningReprimandBab) listBab.get(i);
                if (babId == cbBab.getOID()){
                    %>
                    <option selected="selected" value="<%=cbBab.getOID()%>"><%=cbBab.getBabTitle()%></option>
                    <%
                } else {
                    %>
                    <option value="<%=cbBab.getOID()%>"><%=cbBab.getBabTitle()%></option>
                    <%
                }
                
            }
        }
        %>
    </select>
</div>

<div class="caption">
    Article/Pasal
</div>
<div class="divinput">
    <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_ARTICLE]%>" onchange="loadAyat('<%= babId %>',this.value)">
        <option value="0">-select-</option>
        <%
        if (babId != 0){
            String wherePasal = PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_BAB_ID]+"="+babId;
            Vector listPasal = PstWarningReprimandPasal.list(0, 0, wherePasal, PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE]);
            if (listPasal != null && listPasal.size()>0){
                for(int i=0; i<listPasal.size(); i++){
                    WarningReprimandPasal cbPasal = (WarningReprimandPasal) listPasal.get(i);
                    if (pasalId == cbPasal.getOID()){
                        %>
                        <option selected="selected" value="<%=cbPasal.getOID()%>"><%= cbPasal.getPasalTitle() %></option>
                        <%
                    } else {
                        %>
                        <option value="<%=cbPasal.getOID()%>"><%= cbPasal.getPasalTitle() %></option>
                        <%
                    }
                    
                }
            }
        }
        
        %>
    </select>
</div>
<div class="caption">
    Verse/Ayat
</div>
<div class="divinput">
    <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_VERSE]%>">
        <option value="0">-select-</option>
        <%
        if (pasalId != 0){
            String orderAyat = PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE];
            String whereAyat = PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_PASAL_ID]+"=" + pasalId;
            Vector listAyat = PstWarningReprimandAyat.list(0, 10, whereAyat, orderAyat);

            if(listAyat != null && listAyat.size()>0){
                for(int i=0; i<listAyat.size(); i++){
                    WarningReprimandAyat cbAyat = (WarningReprimandAyat)listAyat.get(i);
                    if (ayatId == cbAyat.getOID()){
                        %>
                        <option selected="selected" value="<%=cbAyat.getOID()%>"><%= cbAyat.getAyatTitle() %></option>
                        <%
                    } else {
                        %>
                        <option value="<%=cbAyat.getOID()%>"><%= cbAyat.getAyatTitle() %></option>
                        <%
                    }
                }
            }
        }
        %>
    </select>
</div>