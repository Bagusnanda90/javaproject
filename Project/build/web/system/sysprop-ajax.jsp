<%-- 
    Document   : sysprop-ajax
    Created on : Feb 2, 2016, 9:42:04 AM
    Author     : Acer
--%>

<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page import="java.util.*" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.entity.*" %>
<%@ page import="com.dimata.system.entity.system.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.system.form.system.*" %>
<%@ page import="com.dimata.system.session.system.*" %>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_PROGRAM, AppObjInfo.OBJ_MENU_TRAINING_PROGRAM);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%   
int iCommand = FRMQueryString.requestCommand(request);
String typeName = FRMQueryString.requestString(request, "type_name");
String syspropCari = FRMQueryString.requestString(request, "sysprop_cari");
int start = FRMQueryString.requestInt(request, "start");
String whereClause = "";
String order = "";


CtrlSystemProperty ctrlSystemProperty = new CtrlSystemProperty(request);


Vector vSysProp = SessSystemProperty.getvSysProp();
Vector listSysprop = new Vector();

int recordToGet = 10;
int vectSize = 0;
vectSize = PstSystemProperty.getCount("");

SystemProperty sysProp = ctrlSystemProperty.getSystemProperty();
FrmSystemProperty frmSystemProperty = ctrlSystemProperty.getForm();

if (!(syspropCari.equals("0"))){
    if (typeName.equals("0")){
        whereClause = PstSystemProperty.fieldNames[PstSystemProperty.FLD_NAME]+" LIKE '%"+syspropCari+"%'";
        order = PstSystemProperty.fieldNames[PstSystemProperty.FLD_NAME];
        vectSize = PstSystemProperty.getCount(whereClause);
        listSysprop = PstSystemProperty.list(0, 0, whereClause, order);        
    } else {
        whereClause = PstSystemProperty.fieldNames[PstSystemProperty.FLD_DISTYPE]+" LIKE '%"+typeName+"%'";
        order = PstSystemProperty.fieldNames[PstSystemProperty.FLD_NAME];
        vectSize = PstSystemProperty.getCount(whereClause);
        listSysprop = PstSystemProperty.list(0, 0, whereClause, order);   
    }   
    } 
    else {
        vectSize = PstSystemProperty.getCount("");
        order = PstSystemProperty.fieldNames[PstSystemProperty.FLD_NAME];
        listSysprop = PstSystemProperty.list(0, 0, "", order);
    }
if (listSysprop != null && listSysprop.size()>0){
%>

<table class="tblStyle">
            <tr>
                <td class="title_tbl" style="background-color: #DDD;">Name</td>
                <td class="title_tbl" style="background-color: #DDD;">Value</td>
                <td class="title_tbl" style="background-color: #DDD;">Value Type</td>
                <td class="title_tbl" style="background-color: #DDD;">Description</td>
            </tr>
        <%
        for(int i=0; i<listSysprop.size(); i++){
            SystemProperty systemProp = (SystemProperty)listSysprop.get(i);
            try{
                SystemProperty sysPropX = PstSystemProperty.fetchByName(systemProp.getName());                                 
                if(sysPropX!=null && sysPropX.getOID()!=0){
                systemProp.setOID(sysPropX.getOID());
                systemProp.setValue(sysPropX.getValue());
                } else{
                PstSystemProperty.insert(systemProp);
                }
            } catch (Exception e) {
            }


            %>
            <tr>
                <td><a href="javascript:cmdEdit('<%=systemProp.getOID()%>')"><%= systemProp.getName() %></a></td>
                <td><%= systemProp.getValue() %></td>
                <td><%= systemProp.getValueType() %></td>
                <td><%= systemProp.getNote() %></td>
            </tr>
            <%
        }
        %>
        </table>
        <%
            }
        %>

