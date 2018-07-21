<%-- 
    Document   : level_ajax
    Created on : 07-Jul-2016, 14:43:29
    Author     : Acer
--%>


<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.harisma.form.masterdata.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.harisma.entity.admin.*"%>
<%@page import="com.dimata.harisma.entity.clinic.*"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_EMPLOYEE_MENU, AppObjInfo.OBJ_EMPLOYEE_AND_FAMILY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listlevel")){
        String whereClause = "";
        String order = "";
        Vector listLevel = new Vector();

        CtrlLevel ctrlLevel = new CtrlLevel(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstLevel.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlLevel.actionList(iCommand, start, vectSize, recordToGet);
        }

            order = PstLevel.fieldNames[PstLevel.FLD_LEVEL];
            vectSize = PstLevel.getCount(whereClause);
            listLevel = PstLevel.list(0, 0, "", order);        
        

        if (listLevel != null && listLevel.size()>0){
        %>
        <table id="listEmpEducation" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Level</th>
                    <th>Group</th>
                    <th>Employee Medical Level</th>
                    <th>Family Medical Level</th>
                    <th>Description</th>
                    <th>Level Point</th>
                    <th>Code</th>
                    <th>Level Rank</th>
                    <th>Max Level Approval</th>
                    <th>HR Approval</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listLevel.size(); i++) {
                    Level level = (Level)listLevel.get(i);
                    GroupRank groupRank = new GroupRank();
                    MedicalLevel mLevel = new MedicalLevel();
                    String empMedicLevel = "";
                    try {
                        groupRank = PstGroupRank.fetchExc(level.getGroupRankId());
                        mLevel = PstMedicalLevel.fetchExc(level.getEmployeeMedicalId());
                        empMedicLevel = mLevel.getLevelName();
                    } catch (Exception exc){
                        groupRank = new GroupRank();
                        empMedicLevel = "";
                    }
                    
                    String famMedicLevel = "";
                    try {
                        mLevel = PstMedicalLevel.fetchExc(level.getFamilyMedicalId());
                        famMedicLevel = mLevel.getLevelName();
                    } catch (Exception exc){
                        famMedicLevel = "";
                    }
                    
                    Level level1 = new Level();
                    try {
                        level1 = PstLevel.fetchExc(level.getMaxLevelApproval());
                    } catch (Exception exc) {
                        level1 = new Level();
                    }
                    
                    String hrApp = "No";
                    if (level.getHr_approval() != 0) {
                        hrApp = "Yes";
                    }
                    
                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= level.getLevel()%></td>
                <td><%= groupRank.getGroupName() %></td>
                <td><%= mLevel.getLevelName() %></td>
                <td><%= empMedicLevel%></td>
                <td><%= famMedicLevel%></td>
                <td><%= level.getDescription()%></td>
                <td><%= level.getCode()%></td>
                <td><%= level.getLevelRank() %></td>
                <td><%= level1.getLevel()%></td>
                <td><%= hrApp %></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= level.getOID() %>" class="addeditdatalevel btn btn-primary" data-command="<%= Command.NONE %>" data-for="showlevelform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= level.getOID() %>" data-for="showlevelform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedatalevel"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
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
    }else if(datafor.equals("showlevelform")){

        if(iCommand == Command.SAVE){
            CtrlLevel ctrlLevel = new CtrlLevel(request);
            ctrlLevel.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlLevel ctrlLevel = new CtrlLevel(request);
            ctrlLevel.action(iCommand, oid);
        }else{
            Level level = new Level();
            if(oid != 0){
                try{
                    level = PstLevel.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Group Rank :</label>
                <select name="<%= FrmLevel.fieldNames[FrmLevel.FRM_FIELD_GROUP_RANK_ID] %>" class="form-control">
                    <option value="0">-SELECT-</option>
                    <%
                    Vector listGroup = PstGroupRank.listAll();
                    for (int i = 0; i < listGroup.size(); i++) {
                            GroupRank groupRank = (GroupRank)listGroup.get(i);
                            if (level.getGroupRankId()==groupRank.getOID()){
                                %>
                                <option value="<%=groupRank.getOID()%>" selected="selected"><%=groupRank.getGroupName()%></option>
                                <%
                            } else {
                            %>
                                <option value="<%=groupRank.getOID()%>"><%=groupRank.getGroupName()%></option>
                            <%
                            }
                        }                                               
                    %>
                </select>
            </div>
            <label>Employee Medical Level</label>
             <div class="input-group">
                        <select class="form-control" id="emp_med_level">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select>
                        <span class="input-group-btn">
                            <button id="filter" class="btn btn-primary btn-block" onclick="add();">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                        </span>
              </div>
                
            <div class="form-group">
                <label>Start Year :</label>
                <input type="text" name="<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_START_DATE] %>" value="<%= empEducation.getStartDate() %>" class="form-control" placeholder="Eg. 1985">
            </div>
            <div class="form-group">
                <label>End Year :</label>
                <input type="text" name="<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_END_DATE] %>" value="<%= empEducation.getEndDate()%>" class="form-control" placeholder="Eg. 2000">
            </div>    
            <div class="form-group">
                <label>University / Institution :</label>
                <select class="form-control" name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_INSTITUTION_ID] %>">
                <option value="0">-SELECT-</option>
                <%
                /**
                * Update by Hendra Putu | 2015-11-18
                */
                // select contact_class where contact type = 13 [Institution] . result get contact class id
                String whereCClass = PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"=13";
                Vector listCClass = PstContactClass.list(0, 0, whereCClass, "");
                long cClassId = 0;
                if (listCClass != null && listCClass.size()>0){
                    ContactClass cclass = (ContactClass)listCClass.get(0);
                    cClassId = cclass.getOID();
                }
                String inContactId = "";
                if (cClassId != 0){
                    String whereCAssign = PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CNT_CLS_ID] +"="+cClassId;
                    Vector listCAssign = PstContactClassAssign.list(0, 0, whereCAssign, "");
                    if (listCAssign != null && listCAssign.size()>0){
                        for(int a=0; a<listCAssign.size(); a++){
                            ContactClassAssign cA = (ContactClassAssign)listCAssign.get(a);
                            inContactId += cA.getContactId()+",";
                        }
                        inContactId += "0";
                    }
                }


                String whereContact = PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+" IN("+inContactId+")";
                Vector listContact = PstContactList.list(0, 0, whereContact, "");
                if (listContact != null && listContact.size()>0){
                    for(int i=0; i<listContact.size(); i++){
                        ContactList contact = (ContactList)listContact.get(i);
                        if (empEducation.getInstitutionId()==contact.getOID()){
                            %>
                            <option selected="selected" value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                            <%
                        } else {
                            %>
                            <option value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                            <%
                        }
                        %>

                        <%
                    }
                }
                %>
            </select>
            </div>
            <div class="form-group">
                <label>Detail :</label>
                <input type="text" name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_GRADUATION] %>"  value="<%= empEducation.getGraduation() %>" class="form-control"></div>
            </div>
            <div class="form-group">
                <label>Point :</label>
                <input type="text" name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_POINT] %>"  value="<%= empEducation.getPoint()%>" class="form-control"></div>
            </div>
            <div class="form-group">
                <label>Description :</label>
                <textarea name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_DESC]%>" class="form-control" rows="2"><%= empEducation.getEducationDesc()%></textarea>
            </div>
            
        <%
    }
}
    
%>    
    