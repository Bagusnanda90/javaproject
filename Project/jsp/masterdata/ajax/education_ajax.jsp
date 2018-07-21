<%-- 
    Document   : education_ajax
    Created on : Jun 28, 2016, 10:35:44 AM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEducation"%>
<%@page import="com.dimata.harisma.entity.masterdata.Education"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEducation"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEducation"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_EDUCATION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String EduName = FRMQueryString.requestString(request, "Education_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listeducation")){
        String whereClause = "";
        String order = "";
        Vector listeducation = new Vector();

        CtrlEducation ctrlEducation = new CtrlEducation(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEducation.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEducation.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(EduName.equals(""))){
            whereClause = PstEducation.fieldNames[PstEducation.FLD_EDUCATION]+" LIKE '%"+EduName+"%'";
            order = PstCompany.fieldNames[PstEducation.FLD_EDUCATION];
            vectSize = PstEducation.getCount(whereClause);
            listeducation = PstEducation.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstEducation.getCount("");
            order = PstEducation.fieldNames[PstEducation.FLD_EDUCATION];
            listeducation = PstEducation.list(start, recordToGet, "", order);
        }

        if (listeducation != null && listeducation.size()>0){
        %>
        <table id="example1" class="table table-bordered table-striped">
        <thead>
            <tr>
                <th>No</th>
                <th>Education</th>
                <th>Level</th>
                <th>Description</th>
                <th>Action</th>
                </tr>
        </thead>
        <%
            for (int i = 0; i < listeducation.size(); i++) {
                Education education = (Education) listeducation.get(i);
         %>
         <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=education.getEducation()%></td>
                    <td><%=education.getEducationLevel()%></td>
                    <td><%=education.getEducationDesc()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= education.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showeducationform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= education.getOID() %>" data-for="showeducationform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
                    </td>
          </tr>
          <%
            }
          %>
   </table>
            <%
        } else { %>
            <a> <h4>No Record </h4></a>
        <% }
        }else if(datafor.equals("showeducationform")){

        if(iCommand == Command.SAVE){
            CtrlEducation ctrlEducation = new CtrlEducation(request);
            ctrlEducation.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlEducation ctrlEducation = new CtrlEducation(request);
            ctrlEducation.action(iCommand, oid);
        }else{
            Education education = new Education();
            if(oid != 0){
                try{
                    education = PstEducation.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Company">Education :</label>
                <input type="text" class="form-control" id="company" value="<%= education.getEducation() %>" name="<%=FrmEducation.fieldNames[FrmEducation.FRM_FIELD_EDUCATION] %>">
            </div>
            <div class="form-group">
                <label for="Company">Level :</label>
                <input type="text" class="form-control" name="<%=FrmEducation.fieldNames[FrmEducation.FRM_FIELD_EDUCATION_LEVEL] %>"  value="<%= ""+education.getEducationLevel() %>">
            </div>
            <div class="form-group">
                <label for="Company">Description :</label>
                <input type="text" class="form-control" name="<%=FrmEducation.fieldNames[FrmEducation.FRM_FIELD_EDUCATION_DESC] %>"  value="<%= education.getEducationDesc() %>">
            </div>

        <%
    }
}
    
%>    