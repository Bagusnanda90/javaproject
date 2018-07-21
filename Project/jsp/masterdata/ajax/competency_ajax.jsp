<%-- 
    Document   : PayrollGroup_ajax
    Created on : Jun 30, 2016, 3:12:23 PM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCompetency"%>
<%@page import="com.dimata.harisma.entity.masterdata.Competency"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetency"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompetency"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_COMPETENCE_TYPE);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String competencyName = FRMQueryString.requestString(request, "competency_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listcompetency")){
        String whereClause = "";
        String order = "";
        Vector listcompetency = new Vector();

        CtrlCompetency ctrlCompetency = new CtrlCompetency(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstCompetency.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlCompetency.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(competencyName.equals(""))){
            whereClause = PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_NAME]+" LIKE '%"+competencyName+"%'";
            order = PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_NAME];
            vectSize = PstCompetency.getCount(whereClause);
            listcompetency = PstCompetency.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstCompetency.getCount("");
            order = PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_NAME];
            listcompetency = PstCompetency.list(start, recordToGet, "", order);
        }
        if (listcompetency != null && listcompetency.size()>0){
        %>
        <table id="listcompetency" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Competency Name</th>
                    <th>Group Name</th>
                    <th>Type Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listcompetency.size(); i++) {
                Competency competency = (Competency) listcompetency.get(i);
                CompetencyGroup competency1 = new CompetencyGroup();
                CompetencyType competency2 = new CompetencyType();
                String ComGroup = "";
                String Comtype = "";
                try {
                    competency1 = PstCompetencyGroup.fetchExc(competency.getCompetencyGroupId());
                    ComGroup = competency1.getGroupName();}
                catch(Exception exc){
                }
                try {
                    competency2 = PstCompetencyType.fetchExc(competency.getCompetencyTypeId());
                    Comtype = competency2.getTypeName();}
                catch(Exception exc){
                }
            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=competency.getCompetencyName()%></td>
                    <td><%= ComGroup %></td>
                    <td><%= Comtype %></td>
                    <td><%=competency.getDescription()%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competency.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showcompetencyform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competency.getOID() %>" data-for="showcompetencyform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
                    </td>
                </tr>
                <%
            }
            %>
        </table>
            <%
        }
    }else if(datafor.equals("showcompetencyform")){

        if(iCommand == Command.SAVE){
            CtrlCompetency ctrlCompetency = new CtrlCompetency(request);
            ctrlCompetency.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlCompetency ctrlCompetency = new CtrlCompetency(request);
            ctrlCompetency.action(iCommand, oid);
        }else{
            Competency competency = new Competency();
            if(oid != 0){
                try{
                    competency = PstCompetency.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            
        <div class="form-group">
            <label class="col-sm-4">Group :</label>
            <div class="col-sm-7"><select rows="5" class="form-control" id="group" name="<%=FrmCompetency.fieldNames[FrmCompetency.FRM_FIELD_COMPETENCY_GROUP_ID]%>">
                    <option value="0">-Select-</option>
                      <%
                      Vector listCompetencyGroup = PstCompetencyGroup.list(0, 0, "", "");
                      if(listCompetencyGroup != null && listCompetencyGroup.size()>0){
                          for (int i = 0; i<listCompetencyGroup.size() ; i++){
                              CompetencyGroup competencyGroup = (CompetencyGroup) listCompetencyGroup.get(i);
                              if(competency.getCompetencyGroupId()==competencyGroup.getOID()){
                              %>
                              <option selected ="selected" value="<%= competencyGroup.getOID()%>"><%= competencyGroup.getGroupName() %></option>}
                              <%} else {%>
                              <option  value="<%= competencyGroup.getOID()%>"><%=competencyGroup.getGroupName()%></option>
                            <% }
                             }
                           }
                      %>
                </select>
            </div> 
        </div>
                
        <div class="form-group">
            <label class="col-sm-4">Type :</label>
            <div class="col-sm-7"> <select rows="5" class="form-control" id="stype" name="<%=FrmCompetency.fieldNames[FrmCompetency.FRM_FIELD_COMPETENCY_TYPE_ID] %>">
                    <option value="0">-Select-</option>
                      <%
                      Vector listCompetencyType = PstCompetencyType.list(0, 0, "", "");
                      if(listCompetencyType != null && listCompetencyType.size()>0){
                          for (int i = 0; i<listCompetencyType.size() ; i++){
                              CompetencyType competencyType = (CompetencyType) listCompetencyType.get(i);
                              if(competency.getCompetencyTypeId()==competencyType.getOID()){
                              %>
                              <option selected ="selected" value="<%= competencyType.getOID()%>"><%= competencyType.getTypeName() %></option>
                              <%}else{%>
                              <option  value="<%= competencyType.getOID()%>"><%=competencyType.getTypeName()%></option>
                             <% 
                            }
                           }
                                                   }
                      %>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4">Type Name :</label>
            <div class="col-sm-7"><input type="text" class="form-control" id="name" value="<%= competency.getCompetencyName() %>" name="<%=FrmCompetency.fieldNames[FrmCompetency.FRM_FIELD_COMPETENCY_NAME]%>"></div>
            </div>
            <div class="form-group">
                <label class="col-sm-4">Note :</label>
                <div class="col-sm-7"><textarea rows="5" class="form-control" id="Note" name="<%=FrmCompetency.fieldNames[FrmCompetency.FRM_FIELD_DESCRIPTION]%>"><%= competency.getDescription() %></textarea></div>
            </div>
            
        <%
    
}
}   
%>
