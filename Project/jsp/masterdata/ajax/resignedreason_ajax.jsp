<%-- 
    Document   : resignedreason_ajax
    Created on : Jun 29, 2016, 11:43:21 AM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmResignedReason"%>
<%@page import="com.dimata.harisma.entity.masterdata.ResignedReason"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstResignedReason"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlResignedReason"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_RESIGNED_REASON);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String resignName = FRMQueryString.requestString(request, "resign_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listresign")){
        String whereClause = "";
        String order = "";
        Vector listresign = new Vector();

        CtrlResignedReason ctrlResignedReason = new CtrlResignedReason(request);

        int recordToGet = 3;
        int vectSize = 0;
        vectSize = PstResignedReason.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlResignedReason.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(resignName.equals(""))){
            whereClause = PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON]+" LIKE '%"+resignName+"%'";
            order = PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON];
            vectSize = PstResignedReason.getCount(whereClause);
            listresign = PstResignedReason.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstResignedReason.getCount("");
            order = PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_REASON];
            listresign = PstResignedReason.list(start, recordToGet, "", order);
        }

        if (listresign != null && listresign.size()>0){
        %>
        <table id="example1" class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Resigned Reason</th>
                    <th>Code</th>
                    <th>Action</th>
                    </tr>
            </thead>
            <%
            for (int i = 0; i < listresign.size(); i++) {
                ResignedReason resignedReason = (ResignedReason) listresign.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=resignedReason.getResignedReason()%></td>
                    <td><%=resignedReason.getResignedCode()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= resignedReason.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showresignedreasonform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= resignedReason.getOID() %>" data-for="showresignedreasonform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
                    </td>
                </tr>
                <%
            }
            %>
        </table>
            <div>&nbsp;</div>
            <div id="record_count">
                <%
                if (vectSize >= recordToGet){
                    %>
                    List : <%=start%> &HorizontalLine; <%= (start+recordToGet) %> | 
                    <%
                }
                %>
                Total : <%= vectSize %>
            </div>
            <div class="pagging">
                <a style="color:#000000" href=javascript:" data-start="<%= start %>" class="btn-small">First |</a>
                <a style="color:#000000" href="javascript:cmdListPrev('<%=start%>')" class="btn-small">Previous |</a>
                <a style="color:#000000" href="javascript:cmdListNext('<%=start%>')" class="btn-small">Next |</a>
                <a style="color:#000000" href="javascript:cmdListLast('<%=start%>')" class="btn-small">Last</a>
            </div>
            <%
         }
        }else if(datafor.equals("showresignedreasonform")){

        if(iCommand == Command.SAVE){
            CtrlResignedReason ctrlResignedReason = new CtrlResignedReason(request);
            ctrlResignedReason.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlResignedReason ctrlResignedReason = new CtrlResignedReason(request);
            ctrlResignedReason.action(iCommand, oid);
        }else{
            ResignedReason resignedReason = new ResignedReason();
            if(oid != 0){
                try{
                    resignedReason = PstResignedReason.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Company">Resigned Reason :</label>*
                <input type="text" class="form-control" id="description" value="<%= resignedReason.getResignedReason() %>" name="<%=FrmResignedReason.fieldNames[FrmResignedReason.FRM_FIELD_RESIGNED_REASON]%>">
            </div>
            <div class="form-group">
                <label for="Company">Code :</label>
                <input type="text" class="form-control" id="description" value="<%= resignedReason.getResignedCode() %>" name="<%=FrmResignedReason.fieldNames[FrmResignedReason.FRM_FIELD_RESIGNED_CODE]%>">
            </div>
            
        <%
    }
}
    
%>