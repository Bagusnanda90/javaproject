<%-- 
    Document   : geo_addres
    Created on : 15-Jul-2016, 09:29:53
    Author     : Acer
--%>

<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");            
            
            long oidNegara = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]);
            long oidProvinsi = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI ]);
            long oidKabupaten = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]);            
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            long oidKecamatan = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]);            
            String employee = FRMQueryString.requestString(request, "employee");            
            
            String formName = FRMQueryString.requestString(request,"formName");
            int addresstype = FRMQueryString.requestInt(request,"addresstype");
            String sKecamatan ="";
            String sKabupaten ="";
            String sProvinsi ="";
            String sNegara ="";
                        
            
            /*variable declaration*/
            int recordToGet = 10;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;

            CtrlKecamatan ctrlKecamatan = new CtrlKecamatan(request);

            ControlLine ctrLine = new ControlLine();

            /*switch statement */
            iErrCode = ctrlKecamatan.action(iCommand, oidKecamatan);
            /* end switch*/
            FrmKecamatan frmKecamatan = ctrlKecamatan.getForm();

            Kecamatan kecamatan = ctrlKecamatan.getKecamatan();
            //Provinsi provinsi = ctrlProvinsi.getPropinsi();
            msgString = ctrlKecamatan.getMessage();
            int commandRefresh = FRMQueryString.requestInt(request, "commandRefresh");
            
            kecamatan.setIdNegara(oidNegara);
            kecamatan.setIdPropinsi(oidProvinsi);
            kecamatan.setIdKabupaten(oidKabupaten);                
            kecamatan.setOID(oidKecamatan);    
            String geoAddress = "";
    
%>    
<div class="row">
<div class="col-md-12">
<div class="form-group">
    <label class="col-sm-3">Country</label>
    <div class="col-sm-9">
    <select name="<%=frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]%>" class="form-control controlcombo" id="negara">
        <option value="">-Select-</option>
    <%
    Vector listNeg = PstNegara.list(0, 300, "", " NAMA_NGR ");
    for (int i = 0; i < listNeg.size(); i++) {
        Negara neg = (Negara) listNeg.get(i);
        if(kecamatan.getIdNegara()==neg.getOID()){ %>
            <option selected="selected" value="<%=neg.getOID()%>"><%=neg.getNmNegara()%></option>
        <%  geoAddress = geoAddress+ neg.getNmNegara();
        } else {
        %><option value="<%=neg.getOID()%>"><%=neg.getNmNegara()%></option><%
        }
    }
    if(kecamatan.getIdNegara()==0){
            geoAddress = geoAddress+ "-";
    }
    %>
    </select>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3">Province</label>
    <div class="col-sm-9">
    <select name="<%=frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI]%>" class="form-control controlcombo" id="propinsi">
        <option value="">-Select-</option>
    <%
    Provinsi pro = new Provinsi();    
    Vector listPro = PstProvinsi.list(0, 300, "", "NAMA_PROP");
    boolean oidProvOk = false;
    for (int i = 0; i < listPro.size(); i++) {
        Provinsi prov = (Provinsi) listPro.get(i);
        if(kecamatan.getIdPropinsi()==prov.getOID()){ %>
            <option selected="selected" value="<%=prov.getOID()%>" class="<%= prov.getIdNegara() %>"><%=prov.getNmProvinsi()%></option>
        <%  geoAddress = geoAddress+ ","+ prov.getNmProvinsi();
        } else {
        %><option value="<%=prov.getOID()%>" class="<%= prov.getIdNegara() %>"><%=prov.getNmProvinsi()%></option><%
        }
        if(prov.getOID()==kecamatan.getIdPropinsi()){
            oidProvOk=true;
        }
    }
    if(!oidProvOk){
        kecamatan.setIdPropinsi(0);
        oidProvinsi=0;
    }
    if(kecamatan.getIdPropinsi()==0){
            geoAddress = geoAddress+ ",-";
        }
    %>
    </select>
    </div>
</div> 
<div class="form-group">
    <label class="col-sm-3">Regency</label>
    <div class="col-sm-9">
    <select name="<%=frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]%>" class="form-control controlcombo" id="kabupaten">
        <option value="">-Select-</option>
    <%
    Vector listKab = PstKabupaten.list(0, 300, "", "NAMA_KAB");
    boolean oidKabOk = false;
    for (int i = 0; i < listKab.size(); i++) {
        Kabupaten kab = (Kabupaten) listKab.get(i);
        if(kecamatan.getIdKabupaten()==kab.getOID()){ %>
            <option selected="selected" value="<%=kab.getOID()%>" class="<%=kab.getIdPropinsi()%>"><%=kab.getNmKabupaten()%></option>
        <%  geoAddress = geoAddress+ ","+ kab.getNmKabupaten();
        } else {
        %><option value="<%=kab.getOID()%>" class="<%=kab.getIdPropinsi()%>"><%=kab.getNmKabupaten()%></option><%
        }
        if(oidKabupaten==kab.getOID()){
        oidKabOk=true;  
      }
    }
    if(!oidKabOk){
        oidKabupaten=0;
        kecamatan.setIdKabupaten(0);
    }
    if(kecamatan.getIdKabupaten()==0){
            geoAddress = geoAddress+ ",-";
        }
    %>
    </select>
    </div>
</div>    
<div class="form-group">
    <label class="col-sm-3">Sub Regency</label>
    <div class="col-sm-9">
    <select name="<%=frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]%>" class="form-control controlcombo" id="kecamatan">
        <option value="">-Select-</option>
    <%
    Vector listKecamatan = new Vector(1, 1);                                                                                                                        
    listKecamatan = PstKecamatan.listJoinKec(0, 300, "", "");    
    for (int i = 0; i < listKecamatan.size(); i++) {
        Kecamatan kec = (Kecamatan) listKecamatan.get(i);
    if(kecamatan.getOID() ==kec.getOID()){ %>
            <option selected="selected" value="<%=kec.getOID()%>" class="<%=kec.getIdKabupaten()%>"><%=kec.getNmKecamatan()%></option>
        <%  geoAddress = geoAddress+ ","+ kec.getNmKecamatan();
        } else {
        %><option value="<%=kec.getOID()%>" class="<%=kec.getIdKabupaten()%>"><%=kec.getNmKecamatan()%></option><%
        }
    }
    if(kecamatan.getOID ()==0){
        geoAddress = geoAddress+ ",-";
    }
    %>
    </select>
    </div>
</div>    
</div>
    </div>