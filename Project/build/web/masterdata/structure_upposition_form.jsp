<%-- 
    Document   : structure_upposition_form
    Created on : Aug 24, 2015, 3:32:18 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmMappingPosition"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String positionName = FRMQueryString.requestString(request, "position_name");
    String dataFor = FRMQueryString.requestString(request, "datafor");
    String datatargetoid = "";
    String datatargetname = "";
    String title = "";
    if(dataFor.equals("filterup")){
        datatargetname="#uppositionname";
        datatargetoid="#uppositionoid";
        title = "Up Position";
    }else{
        datatargetname="#downpositionname";
        datatargetoid="#downpositionoid";
        title = "Down Position";
    }
    String whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+positionName+"%'";
    String order = ""+PstPosition.fieldNames[PstPosition.FLD_POSITION];
    Vector listPosition = new Vector();
    if (positionName.length() > 0){
       listPosition = PstPosition.list(0, 0, whereClause, order); 
    } else {
       listPosition = PstPosition.list(0, 0, "", order); 
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title %></title>
        <style type="text/css">
            .item {
                padding: 3px;
                border:1px solid #CCC;
                border-radius: 3px;
                background-color: #EEE;
                margin: 3px;
                cursor: pointer;
            }
            .teks {
                font-size: 11px;
                color:#474747;
                padding: 5px; 
                border:1px solid #CCC;
                border-radius: 3px;
                margin: 3px;
            }
        </style>
        <script language="javascript">
            function cmdGetItem(oid, posname) {
                self.opener.document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.up_position.value = oid;       
                self.opener.document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_UP_POSITION_ID]%>.value = oid; 
                self.opener.document.getElementById("upposname").textContent=posname;
                self.close();
                //self.opener.document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.submit();
            }
        </script>
    </head>
    <body>
        <h1 style="border-bottom: 1px solid #0599ab; padding-bottom: 12px;"><%= title %></h1>
        <%
        if (listPosition != null && listPosition.size()>0){
            for(int i=0; i<listPosition.size(); i++){
                Position position = (Position)listPosition.get(i);
                %>
                <div class="item" data-oid="<%=position.getOID()%>" data-name="<%=position.getPosition()%>" data-target-oid="<%= datatargetoid %>" data-target-name="<%= datatargetname %>"><%=position.getPosition()%></div>
                <%
            }
        }
        %>
    </body>
</html>
