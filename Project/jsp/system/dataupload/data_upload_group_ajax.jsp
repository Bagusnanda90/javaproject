<%-- 
    Document   : data_upload_group_ajax
    Created on : Feb 24, 2016, 11:41:49 AM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.system.session.I_System"%>
<%@page import="com.dimata.system.entity.dataupload.*"%>
<%@page import="com.dimata.system.form.dataupload.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<!DOCTYPE html>

<%
    long oidDataGroupId = FRMQueryString.requestLong(request, "hidden_data_group_id");
    int systemName = FRMQueryString.requestInt(request, PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_SYSTEM_NAME]);
    int modul = FRMQueryString.requestInt(request, PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_MODUL]);
        
    CtrlDataUploadGroup ctrlDataUploadGroup = new CtrlDataUploadGroup(request);
    FrmDataUploadGroup frmDataUploadGroup = ctrlDataUploadGroup.getForm();
    
    if (oidDataGroupId != 0){
        DataUploadGroup dataUploadGroup = new DataUploadGroup();
        try {
            dataUploadGroup = PstDataUploadGroup.fetchExc(oidDataGroupId);
            systemName = dataUploadGroup.getSystemName();
            modul = dataUploadGroup.getModul();
        } catch(Exception e){
            System.out.print(""+e.toString());
        }
    }
%>
<table border="0" cellspacing="2" cellpadding="2" width="50%">
    <tr align="left" valign="top"> 
        <td valign="top" width="21%">
            System Name
        </td>
        <td width="79%">
            <select name="<%=frmDataUploadGroup.fieldNames[FrmDataUploadGroup.FRM_FIELD_SYSTEM_NAME]%>" onchange="javascript:loadModul(this.value)">
                <%
                for(int i = 0; i < I_System.SYSTEM_NAME.length; i++){
                    if (systemName == i){
                %>
                        <option selected="selected" value="<%=i%>"><%= I_System.SYSTEM_NAME[i] %></option>
                <%
                    } else {
                %>
                        <option value="<%=i%>"><%= I_System.SYSTEM_NAME[i] %></option>
                <%
                    }
                }
                %>
            </select>
            * <%=frmDataUploadGroup.getErrorMsg(FrmDataUploadGroup.FRM_FIELD_SYSTEM_NAME)%>
        </td>
    </tr>
    <tr align="left" valign="top"> 
        <td valign="top" width="21%">
            Modul
        </td>
        <td width="79%">
            <select name="<%=frmDataUploadGroup.fieldNames[FrmDataUploadGroup.FRM_FIELD_MODUL]%>">
                <%
                for(int j = 0; j < I_System.MODULS[systemName].length; j++){
                    if (modul == j){
                %>
                        <option selected="selected" value="<%=j%>"><%= I_System.MODULS[systemName][j] %></option>
                <%
                    } else {
                %>
                        <option value="<%=j%>"><%= I_System.MODULS[systemName][j] %></option>
                <%
                    }
                }
                %>
            </select>
            * <%=frmDataUploadGroup.getErrorMsg(FrmDataUploadGroup.FRM_FIELD_MODUL)%>
        </td>
    </tr>
</table>
