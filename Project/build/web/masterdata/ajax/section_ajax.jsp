<%-- 
    Document   : division_ajax
    Created on : 27-Jun-2016, 19:49:35
    Author     : Acer
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmSection"%>
<%@page import="com.dimata.harisma.entity.masterdata.Section"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstSection"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlSection"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_SECTION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String secName = FRMQueryString.requestString(request, "sec_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    long oidSectionTemp = FRMQueryString.requestLong(request, "section_temp_id");
    String sectionInput = FRMQueryString.requestString(request, "section_input");
    if(datafor.equals("listsec")){
        String whereClause = "";
        String order = "";
        Vector listSection = new Vector();

        CtrlSection ctrlSection = new CtrlSection(request);
       
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstSection.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlSection.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(secName.equals(""))){
            whereClause = PstSection.fieldNames[PstSection.FLD_SECTION]+" LIKE '%"+secName+"%'";
            order = PstSection.fieldNames[PstSection.FLD_SECTION];
            vectSize = PstSection.getCount(whereClause);
            listSection = PstSection.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstSection.getCount("");
            order = PstSection.fieldNames[PstSection.FLD_SECTION];
            listSection = PstSection.list(start, recordToGet, "", order);
        }

        if (listSection != null && listSection.size()>0){
        %>
        <table id="listSection" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Section</th>
                    <th>Department</th>
                    <th>Description</th>
                    <th>Section Link</th>
                    <th>Valid Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listSection.size(); i++) {
                    Section section = (Section)listSection.get(i);
                    String strDept = "-";
                    String secLink ="";
                    try {
                        Department dept = PstDepartment.fetchExc(section.getDepartmentId());
                        strDept = dept.getDepartment();
                    } catch(Exception e){
                        System.out.println(""+e.toString());
                    }
                    if(section.getSectionLinkTo() == null){
                        secLink = "-";
                    } else{
                        secLink = section.getSectionLinkTo();
                    }
                   
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= section.getSection()%></td>
                <td>
                    
                    <%= strDept %>
                </td>
                <td><%= section.getDescription() %></td>
                <td> <%= secLink %> </td>
                <td><%= PstSection.validStatusValue[section.getValidStatus()] %></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= section.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showsecform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= section.getOID() %>" data-for="showsecform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
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
    }else if(datafor.equals("showsecform")){

        if(iCommand == Command.SAVE){
            CtrlSection ctrlSection = new CtrlSection(request);
            ctrlSection.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlSection ctrlSection = new CtrlSection(request);
            ctrlSection.action(iCommand, oid);
        }else{
            Section section = new Section();
            if(oid != 0){
                try{
                    section = PstSection.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Section">Section :</label>
                <input type="text" class="form-control" id="section" name="<%=FrmSection.fieldNames[FrmSection.FRM_FIELD_SECTION] %>" value="<%= section.getSection()%>">
            </div>
            <div class="form-group">
                <label for="Company">Department :</label>
                <select name="<%=FrmSection.fieldNames[FrmSection.FRM_FIELD_DEPARTMENT_ID]%>" class="form-control" id="department">
                    <option value="0">-select-</option>
                    <%
                        String strWhereDept = "";
                        Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, strWhereDept);
                        String prevCompany = "";
                        String prevDivision = "";
                        for (int i = 0; i < listCostDept.size(); i++) {
                            Department dept = (Department) listCostDept.get(i);
                            if (prevCompany.equals(dept.getCompany())) {
                                if (prevDivision.equals(dept.getDivision())) { %>
                                    <option value="<%=String.valueOf(dept.getOID())%>"><%= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment()%></option>
                                <% } else { %>
                                    <option value="-2"><%= "&nbsp;-" + dept.getDivision() + "-"%></option>
                                    <option value="<%=String.valueOf(dept.getOID())%>"><%= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment()%></option>
                                <%  prevDivision = dept.getDivision(); }
                            } else { %>
                                    <option value="-1"><%= "-" + dept.getCompany() + "-"%></option>
                                    <option value="-2"><%= "&nbsp;-" + dept.getDivision() + "-"%></option>
                                    <option value="<%=String.valueOf(dept.getOID())%>"><%= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment()%></option>
                                
                            <%  prevCompany = dept.getCompany();
                                prevDivision = dept.getDivision();
                            }
                        }


                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="Description">Description :</label>
                <textarea rows="5" class="form-control" id="desc" name="<%=FrmSection.fieldNames[FrmSection.FRM_FIELD_DESCRIPTION] %>"><%= section.getDescription() %></textarea>
            </div>
            <div class="form-group">
                <label for="Section Link">Add Section Link To :</label>
                <button type="button" class="btn btn-primary btn-sm fa fa-plus" data-toggle="modal" data-target="#myModal"> Add Section Link</button>
                <select id="select_section">
                    <option value="0">-select-</option>
                    <%
                        Vector listSectionLink = new Vector();
                        listSectionLink = PstSection.list(0, 0, "", "");
                        if (listSectionLink != null && listSectionLink.size() > 0) {
                            for (int i = 0; i < listSectionLink.size(); i++) {
                                Section sec = (Section) listSectionLink.get(i);
                    %>
                    <option value="<%="" + sec.getOID()%>"><%="[" + sec.getOID() + "] " + sec.getSection()%></option>
                    <%
                            }
                        }
                        String sectionData = "";
                        if (section.getSectionLinkTo() != null && !section.getSectionLinkTo().equals("")) {
                            //if (sectionInput.equals("")){
                            sectionData = section.getSectionLinkTo();
                            //} else {
                            //sectionData = sectionInput;
                            //}
                        } else if (oidSectionTemp == section.getOID()) {
                            sectionData = sectionInput;
                        } else {
                            sectionData = "";
                        }
                        sectionInput = "";
                    %>
                </select><br />
                <input type="hidden" name="section_temp_id" value="<%=section.getOID()%>" />
                <input type="hidden" id="section_input" name="section_input" value="<%=sectionData%>" size="70" />
                <input type="text" class="form-control"  name="<%=FrmSection.fieldNames[FrmSection.FRM_FIELD_SECTION_LINK_TO]%>" value="<%=sectionData%>"/>
                </div>
            <div class="form-group">
                <label for="Valid Status">Valid Status  :</label>
                <select name="<%=FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_STATUS]%>" class="form-control" id="valid_status">
                    <%
                        if (section.getValidStatus() == PstSection.VALID_ACTIVE) {
                    %>
                    <option value="<%=PstSection.VALID_ACTIVE%>" selected="selected">Active</option>
                    <option value="<%=PstSection.VALID_HISTORY%>">History</option>
                    <%
                    } else {
                    %>
                    <option value="<%=PstSection.VALID_ACTIVE%>">Active</option>
                    <option value="<%=PstSection.VALID_HISTORY%>" selected="selected">History</option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label>Masa Berlaku</label>
                <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    Date dateStart = section.getValidStart() == null ? new Date() : section.getValidStart();
                    Date dateEnd = section.getValidEnd() == null ? new Date() : section.getValidEnd();
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    String strValidStart = sdf.format(dateStart);
                    String strValidEnd = sdf.format(dateEnd);
                %>
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <span class="input-group-addon"><input type="text" name="<%=FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_START]%>" id="datepicker1" value="<%=strValidStart%>" /></span>
                <span class="input-group-addon">to</span>
                <span class="input-group-addon"><input type="text" name="<%=FrmSection.fieldNames[FrmSection.FRM_FIELD_VALID_END]%>" id="datepicker2" value="<%=strValidEnd%>" /></span>
                
            </div>
        <%
    }
}
    
%>    