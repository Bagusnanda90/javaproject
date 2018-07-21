<%-- 
    Document   : company_ajax
    Created on : 27-Jun-2016, 14:07:32
    Author     : Acer
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCompetency"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCompetencyDetail"%>
<%@page import="com.dimata.harisma.entity.masterdata.CompetencyDetail"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetencyDetail"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompetencyDetail"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_COMMPANY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String comDName = FRMQueryString.requestString(request, "competencyD_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listcomd")){
        String whereClause = "";
        String order = "";
        Vector listcomd = new Vector();

        CtrlCompetencyDetail ctrlCompetencyDetail = new CtrlCompetencyDetail(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstCompetencyDetail.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlCompetencyDetail.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(comDName.equals(""))){
            whereClause = PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_COMPETENCY_ID]+" LIKE '%"+comDName+"%'";
            order = PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_COMPETENCY_ID];
            vectSize = PstCompetencyDetail.getCount(whereClause);
            listcomd = PstCompetencyDetail.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstCompetencyDetail.getCount("");
            order = PstCompetencyDetail.fieldNames[PstCompetencyDetail.FLD_COMPETENCY_ID];
            listcomd = PstCompetencyDetail.list(start, recordToGet, "", order);
        }

        if (listcomd != null && listcomd.size()>0){
        %>
        <table id="listComd" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Competency Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listcomd.size(); i++) {
                CompetencyDetail competencyDetail = (CompetencyDetail) listcomd.get(i);
                Competency competency = new Competency();
                String comName = "";
                try {
                    competency = PstCompetency.fetchExc(competencyDetail.getCompetencyId());
                    comName = competency.getCompetencyName();}
                    catch (Exception exc){
                }
                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=comName%></td>
                    <td><%=competencyDetail.getDescription()%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competencyDetail.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showcomdform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competencyDetail.getOID() %>" data-for="showcomdform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
                    </td>
                </tr>
                <%
                 }
            %>
        </table>
            
        <%
         }
    }else if(datafor.equals("showcomdform")){

        if(iCommand == Command.SAVE){
            CtrlCompetencyDetail ctrlCompetencyDetail = new CtrlCompetencyDetail(request);
            ctrlCompetencyDetail.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlCompetencyDetail ctrlCompetencyDetail = new CtrlCompetencyDetail(request);
            ctrlCompetencyDetail.action(iCommand, oid);
        }else{
            CompetencyDetail competencyDetail = new CompetencyDetail();
            if(oid != 0){
                try{
                    competencyDetail = PstCompetencyDetail.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
           
            <div class="form-group">
                <label class="col-sm-4">Competency Name :</label>
                <div class="col-sm-7"> <select rows="5" class="form-control" id="cmpname" name="<%=FrmCompetencyDetail.fieldNames[FrmCompetencyDetail.FRM_FIELD_COMPETENCY_ID]%>">
                   
                      <%
                      Vector listCompetency = PstCompetency.list(0, 0, "", "");
                      if(listCompetency != null && listCompetency.size()>0){
                          for (int i = 0; i<listCompetency.size() ; i++){
                              Competency competency = (Competency) listCompetency.get(i);
                               if(competencyDetail.getCompetencyId()==competency.getOID()){  
                              %>
                              <option selected="selected" value="<%= competency.getOID()%>"><%= competency.getCompetencyName() %></option>
                              <%}else{%>
                              <option  value="<%= competency.getOID()%>"><%=competency.getCompetencyName()%></option>
                              <%
                             }
                             }
                           }
                      %>
                </select>
            </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4">Description :</label>
                <div class="col-sm-7"><textarea rows="5" class="form-control" id="description" name="<%=FrmCompetency.fieldNames[FrmCompetency.FRM_FIELD_DESCRIPTION]%>"><%= competencyDetail.getDescription() %></textarea>
            </div>
            </div>
        <%
    }
    
}


%>    