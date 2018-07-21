<%-- 
    Document   : language_ajax
    Created on : Aug 15, 2016, 2:43:34 PM
    Author     : ARYS
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmLanguage"%>
<%@page import="com.dimata.harisma.entity.masterdata.Language"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstLanguage"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlLanguage"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_EDUCATION);%>

<%
    String LangName = FRMQueryString.requestString(request, "lang_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listlang")){
        String whereClause = "";
        String order = "";
        Vector listLanguage = new Vector();

        CtrlLanguage ctrlLanguage = new CtrlLanguage(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstLanguage.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlLanguage.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(LangName.equals(""))){
            whereClause = PstLanguage.fieldNames[PstLanguage.FLD_LANGUAGE]+" LIKE '%"+LangName+"%'";
            order = PstLanguage.fieldNames[PstLanguage.FLD_LANGUAGE];
            vectSize = PstLanguage.getCount(whereClause);
            listLanguage = PstLanguage.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstLanguage.getCount("");
            order = PstLanguage.fieldNames[PstLanguage.FLD_LANGUAGE];
            listLanguage = PstLanguage.list(start, recordToGet, "", order);
        }

        if (listLanguage != null && listLanguage.size()>0){
        %>
 
        <table id="tblang" class="table table-bordered table-striped">
        <thead>
            <tr>
                <th width="100px">No</th>
                <th>Language</th>
                <th>Action</th>
                </tr>
        </thead>
        <%
            for (int i = 0; i < listLanguage.size(); i++) {
                Language language = (Language) listLanguage.get(i);
         %>
         <tr>
                    <td width="100px"><%=(i + 1)%></td>
                    <td><%=language.getLanguage()%></td>
                    <td width="100px">
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= language.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showlangform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= language.getOID() %>" data-for="showlangform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
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
        }else if(datafor.equals("showlangform")){

        if(iCommand == Command.SAVE){
            CtrlLanguage ctrlLanguage = new CtrlLanguage(request);
            ctrlLanguage.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlLanguage ctrlLanguage = new CtrlLanguage(request);
            ctrlLanguage.action(iCommand, oid);
        }else{
            Language language = new Language();
            if(oid != 0){
                try{
                    language = PstLanguage.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Company">Language :</label>
                <input type="text" class="form-control" id="company" value="<%= language.getLanguage() %>" name="<%=FrmLanguage.fieldNames[FrmLanguage.FRM_FIELD_LANGUAGE] %>">
            </div>
        <%
    }
}
    
%>
