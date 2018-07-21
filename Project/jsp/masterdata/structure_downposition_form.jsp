<%-- 
    Document   : structure_downposition_form
    Created on : Aug 24, 2015, 3:33:04 PM
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
        <title>Down Position</title>
        <style type="text/css">
            body {
                color:#575757;
                font-size: 11px;
                font-family: sans-serif;
                background-color: #F5F5F5;
            }
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
            function cmdGetItem(oid , posname) {
                self.opener.document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.down_position.value = oid;  
                self.opener.document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_DOWN_POSITION_ID]%>.value = oid;  
                self.opener.document.getElementById("downposname").textContent=posname;
                self.close();
                //self.opener.document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.submit();
            }
        </script>
    </head>
    <body>
        <h1 style="border-bottom: 1px solid #0599ab; padding-bottom: 12px;">Down Position</h1>
        <form method="post" name="frm" action="">
            <input class="teks" placeholder="search position..." type="text" name="position_name" size="50" />
        </form>
        <%
        if (listPosition != null && listPosition.size()>0){
            for(int i=0; i<listPosition.size(); i++){
                Position position = (Position)listPosition.get(i);
                %>
                <div class="item" onclick="cmdGetItem('<%=position.getOID()%>', '<%=position.getPosition()%>')"><%=position.getPosition()%></div>
                <%
            }
        }
        %>
    </body>
</html>