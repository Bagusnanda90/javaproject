<%-- 
    Document   : data_upload_ajax
    Created on : Mar 18, 2016, 9:09:21 AM
    Author     : khirayinnura
--%>

<%@page import="java.util.Vector"%>
<%@page import="com.dimata.system.entity.dataupload.PstDataUploadGroup"%>
<%@page import="com.dimata.system.entity.dataupload.DataUploadGroup"%>
<%@page import="com.dimata.system.entity.dataupload.PstDataUploadMain"%>
<%@page import="com.dimata.harisma.entity.leave.PstDPUpload"%>
<%@page import="com.dimata.system.entity.dataupload.DataUploadMain"%>
<%@page import="com.dimata.system.form.dataupload.CtrlDataUploadDetail"%>
<%@page import="com.dimata.system.form.dataupload.FrmDataUploadDetail"%>
<%@page import="com.dimata.system.entity.dataupload.DataUploadDetail"%>
<%@page import="com.dimata.system.entity.dataupload.PstDataUploadDetail"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>

<%
    long oidDataMain = FRMQueryString.requestLong(request, "data_main_oid");
    long oidDataDetail = FRMQueryString.requestLong(request, "data_detail_oid");
    int cmd = FRMQueryString.requestInt(request, "cmd");
    int row = FRMQueryString.requestInt(request, "row");
        
    Vector listDataUploadDetail = new Vector(1, 1);
    
    listDataUploadDetail = PstDataUploadDetail.list(0, 0, "DATA_MAIN_ID="+oidDataMain, "");
    
    DataUploadMain dUpMain = new DataUploadMain();
    DataUploadGroup dUpGroup = new DataUploadGroup();
    
    dUpMain = PstDataUploadMain.fetchExc(oidDataMain);
    dUpGroup = PstDataUploadGroup.fetchExc(dUpMain.getDataGroupId());
%>
<h3>Detail</h3>
<table>
    <tr>
        <td>Title Main</td>
        <td>:</td>
        <td><%=dUpMain.getDataMainTitle()%></td>
    </tr>
    <tr>
        <td>Group Main</td>
        <td>:</td>
        <td><%=dUpGroup.getDataGroupTitle()%></td>
    </tr>
    <tr>
        <td>Desc Main</td>
        <td>:</td>
        <td><%=dUpMain.getDataMainDesc()%></td>
    </tr>
</table>
    </br>
<table width="100%">
    <tr>
        <td class="td" width="5px">No</td>
        <td class="td">Title</td>
        <td class="td">Description</td>
        <td class="td">Filename</td>
    </tr>
<%
    if (listDataUploadDetail.size() > 0 && listDataUploadDetail != null) {
%>
        <%
            for (int j = 0; j < listDataUploadDetail.size(); j++) {
                DataUploadDetail dataUpDet = (DataUploadDetail) listDataUploadDetail.get(j);
            if(cmd == 3 && oidDataDetail == dataUpDet.getOID()){
        %>
            <tr>
                <td class="td"></td>
                <td class="td">
                    <input type="hidden" name="<%=FrmDataUploadDetail.fieldNames[FrmDataUploadDetail.FRM_FIELD_DATA_MAIN_ID]%>"  value="<%=oidDataMain%>" class="formElemen">
                    <input type="text" name="<%=FrmDataUploadDetail.fieldNames[FrmDataUploadDetail.FRM_FIELD_DATA_DETAIL_TITLE]%>"  value="<%=dataUpDet.getDataDetailTitle()%>" class="formElemen"></br></br>
                    <a style="color:#FFF" class="btn" href="javascript:cmdSaveDetail('<%=oidDataMain%>','<%=dataUpDet.getOID()%>')">Save</a>&nbsp;<a style="color:#FFF" id="div_drop" class="btn" href="">Batal</a>
                </td>
                <td class="td">
                    <textarea name="<%=FrmDataUploadDetail.fieldNames[FrmDataUploadDetail.FRM_FIELD_DATA_DETAIL_DESC]%>" class="elemenForm" cols="30" rows="3"><%=dataUpDet.getDataDetailDesc()%></textarea>
                </td>
                <td class="td">&nbsp;</td>
            </tr>
                
        <%} else {%>
            <tr>
                <td class="td"><%=j + 1%></td>
                <td class="td"><a href="javascript:loadDetail('<%=oidDataMain%>','<%=dataUpDet.getOID()%>','3')"><%=dataUpDet.getDataDetailTitle()%></a></td>
                <td class="td"><%=dataUpDet.getDataDetailDesc()%></td>
                <td class="td"><a href="upload_pict_detail.jsp?command=3&detail_oid=<%=dataUpDet.getOID()%>&main_id=<%=oidDataMain%>">Upload</a></br><a href="<%=approot%>/imgdoc/<%=dataUpDet.getFilename()%>"><%=dataUpDet.getFilename()%></a></td>
            </tr>
        <%}%>
        <%}%>
    
    <%}%>
<%
    if(cmd == 2){
        
%>
        <tr>
            <td class="td"></td>
            <td class="td">
                <input type="hidden" name="<%=FrmDataUploadDetail.fieldNames[FrmDataUploadDetail.FRM_FIELD_DATA_MAIN_ID]%>"  value="<%=oidDataMain%>" class="formElemen">
                <input type="text" name="<%=FrmDataUploadDetail.fieldNames[FrmDataUploadDetail.FRM_FIELD_DATA_DETAIL_TITLE]%>"  value="" class="formElemen"></br></br>
                <a style="color:#FFF" class="btn" href="javascript:cmdSaveDetail('<%=oidDataMain%>')">Save</a>&nbsp;<a style="color:#FFF" id="div_drop" class="btn" href="">Batal</a>
            </td>
            <td class="td">
                <textarea name="<%=FrmDataUploadDetail.fieldNames[FrmDataUploadDetail.FRM_FIELD_DATA_DETAIL_DESC]%>" class="elemenForm" cols="30" rows="3"></textarea>
            </td>
            <td class="td">&nbsp;</td>
        </tr>
    
<%
    }
%>
</table>