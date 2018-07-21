<%-- 
    Document   : division_ajax
    Created on : 27-Jun-2016, 19:49:35
    Author     : Acer
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.Department"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDepartment"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String deptName = FRMQueryString.requestString(request, "dept_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    long companyIdHidden=FRMQueryString.requestLong(request, "hidden_companyId");
    if(datafor.equals("listdept")){
        String whereClause = "";
        String order = "";
        Vector listDepartment = new Vector();

        CtrlDepartment ctrlDepartment = new CtrlDepartment(request);
       
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstDepartment.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlDepartment.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(deptName.equals(""))){
            whereClause = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" LIKE '%"+deptName+"%'";
            order = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
            vectSize = PstDepartment.getCount(whereClause);
            listDepartment = PstDepartment.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstDepartment.getCount("");
            order = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
            listDepartment = PstDepartment.list(start, recordToGet, "", order);
        }

        if (listDepartment != null && listDepartment.size()>0){
        %>
        <table id="listDepartment" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Department</th>
                    <th>Division</th>
                    <th>Description</th>
                    <th>HOD Join to Department</th>
                    <th>Valid Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listDepartment.size(); i++) {
                Department department = (Department) listDepartment.get(i);
                Division division = new Division();
                    String divisionName = "-";
                    try {
                        division = PstDivision.fetchExc(department.getDivisionId());
                        divisionName = division.getDivision();
                    } catch (Exception e) {
                        //System.out.print("getDivisionType=>"+e.toString());//
                    }
                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=department.getDepartment()%></td>
                    <td><%=divisionName%></td>
                    <td><%=department.getDescription()%></td>
                    <td><%=department.getJoinToDepartment()!=null ?department.getJoinToDepartment():"-"%></td>
                    <td><%= PstDepartment.validStatusValue[department.getValidStatus()] %></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= department.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showdeptform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= department.getOID() %>" data-for="showdeptform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
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
    }else if(datafor.equals("showdeptform")){

        if(iCommand == Command.SAVE){
            CtrlDepartment ctrlDepartment = new CtrlDepartment(request);
            ctrlDepartment.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlDepartment ctrlDepartment = new CtrlDepartment(request);
            ctrlDepartment.action(iCommand, oid);
        }else{
            Department department = new Department();
            if(oid != 0){
                try{
                    department = PstDepartment.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Division">Division :</label>
                <span class="input-group-addon">
                <select name="hidden_companyId" class="form-control" id="company">
                    <option value="0">-select-</option>
                    <%
                        Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                        for (int i = 0; i < listComp.size(); i++) {
                          Company comp = (Company) listComp.get(i);
                          if(companyIdHidden==0){
                              companyIdHidden=comp.getOID();
                          }%>
                                <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany()%></option>
                                <%
                                
                        }
                    %>
                </select></span>
                <span class="input-group-addon">
                    
                <select class="form-control" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DIVISION_ID]%>" id="division">
                    <%
                    
                    /*if(companyIdHidden!=0){
                      whereClauseComp= PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+companyIdHidden;
                    }*/
                              
                              Vector listDiv = PstDivision.list(0, 0, "", "DIVISION");
                              %>
                              <option data-group='SHOW' value='0'>-- Select --</option>
                              <%
                              for(int i =0;i < listDiv.size();i++){
                                Division division = (Division)listDiv.get(i); 
                                if(department.getDivisionId() == division.getOID()){    
                              %>
                              <option selected="selected" value="<%=division.getOID()%>"><%=division.getDivision()%></option>
                              <%
                                } else {  
                              %>      
                                    <option data-group="<%=division.getCompanyId()%>" value="<%=division.getOID()%>"><%=division.getDivision()%></option>
                              <%    }
                                }
                        %>
                </select></span>
                
                
            </div>
            <div class="form-group">
                <label for="Department">Department :</label>
                <input type="text" class="form-control" id="department" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT] %>" value="<%= department.getDepartment() %>">
            </div>
            <div class="form-group">
                <label for="Description">Description :</label>
                <textarea rows="5" class="form-control" id="dep_desc" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DESCRIPTION] %>" ><%= department.getDescription() %></textarea>
            </div>
            <div class="form-group">
                <label for="Join">HOD Join to Department :</label>
                <select name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_JOIN_TO_DEP_ID]%>" class="form-control" id="company">
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
                <label for="Type">Department Type Id  :</label>
                <select name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_TYPE_ID]%>" class="form-control" id="type_id">
                    <option value="0">-select-</option>
                    <%
                        Vector listDepartmentType = PstDepartmentType.list(0, 0, "", "");
                        if (listDepartmentType != null && listDepartmentType.size() > 0) {
                            for (int ldt = 0; ldt < listDepartmentType.size(); ldt++) {
                                DepartmentType depT = (DepartmentType) listDepartmentType.get(ldt);
                                if (department.getDepartmentTypeId() == depT.getOID()) {
                    %>
                    <option selected="selected" value="<%=depT.getOID()%>"><%=depT.getTypeName()%></option>
                    <%
                    } else {
                    %>
                    <option value="<%=depT.getOID()%>"><%=depT.getTypeName()%></option>
                    <%
                                }
                            }
                        }
                    %>

                </select>
            </div>
            <div class="form-group">
                <label for="Valid Status">Valid Status  :</label>
                <select name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_STATUS]%>" class="form-control" id="valid_status">
                    <%
                        if (department.getValidStatus() == PstDepartment.VALID_ACTIVE) {
                    %>
                    <option value="<%=PstDepartment.VALID_ACTIVE%>" selected="selected">Active</option>
                    <option value="<%=PstDepartment.VALID_HISTORY%>">History</option>
                    <%
                    } else {
                    %>
                    <option value="<%=PstDepartment.VALID_ACTIVE%>">Active</option>
                    <option value="<%=PstDepartment.VALID_HISTORY%>" selected="selected">History</option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label>Masa Berlaku</label>
                <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    Date dateStart = department.getValidStart() == null ? new Date() : department.getValidStart();
                    Date dateEnd = department.getValidEnd() == null ? new Date() : department.getValidEnd();
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    String strValidStart = sdf.format(dateStart);
                    String strValidEnd = sdf.format(dateEnd);
                %>
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <span class="input-group-addon"><input type="text" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_START]%>" id="datepicker1" value="<%=strValidStart%>" class="datepicker"/></span>
                <span class="input-group-addon">to</span>
                <span class="input-group-addon"><input type="text" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VALID_END]%>" id="datepicker2" value="<%=strValidEnd%>" class="datepicker" /></span>
                
            </div>
            <a> note: fill some of field below, if you choose Branch of Company </a>
            <div class="form-group">
                <label for="Address">Address :</label>
                <textarea rows="5" class="form-control" id="address" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_ADDRESS]%>" ><%=department.getAddress()%></textarea>
            </div>
            <div class="form-group">
                <label for="City">City :</label>
                <input type="text" class="form-control" id="city" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_CITY]%>" value="<%=department.getCity()%>">
            </div>
            <div class="form-group">
                <label for="NPWP">NPWP :</label>
                <input type="text" class="form-control" id="npwp" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_NPWP]%>" value="<%=department.getNpwp()%>">
            </div>
            <div class="form-group">
                <label for="Province">Province :</label>
                <input type="text" class="form-control" id="province" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_PROVINCE]%>" value="<%=department.getProvince()%>">
            </div>
            <div class="form-group">
                <label for="Region">Region :</label>
                <input type="text" class="form-control" id="region" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_REGION]%>" value="<%=department.getRegion()%>">
            </div>
            <div class="form-group">
                <label for="Sub Region">Sub Region :</label>
                <input type="text" class="form-control" id="subregion" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_SUB_REGION]%>" value="<%=department.getSubRegion()%>">
            </div>
            <div class="form-group">
                <label for="Village">Village :</label>
                <input type="text" class="form-control" id="village" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VILLAGE]%>" value="<%=department.getVillage()%>" >
            </div>
            <div class="form-group">
                <label for="Area">Area :</label>
                <input type="text" class="form-control" id="area" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_AREA]%>" value="<%=department.getArea()%>">
            </div>
            <div class="form-group">
                <label for="Telephone">Telephone :</label>
                <input type="text" class="form-control" id="telephone" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_TELEPHONE]%>" value="<%=department.getTelphone()%>">
            </div>
            <div class="form-group">
                <label for="Fax Number">Fax Number :</label>
                <input type="text" class="form-control" id="faxnumber" name="<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_FAX_NUMBER]%>" value="<%=department.getFaxNumber()%>">
            </div>
        
        
        <%
    }
}
    
%>    