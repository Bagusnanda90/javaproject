<%-- 
    Document   : company_ajax
    Created on : 1-Jul-2016, 14:07:32
    Author     : Arys
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmLevel"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmpWarning"%>
<%@page import="com.dimata.harisma.entity.employee.EmpWarning"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmpWarning"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmpWarning"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_COMMPANY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long employeeid = FRMQueryString.requestLong(request, "empId");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    CtrlEmpWarning ctrlEmpWarning = new CtrlEmpWarning(request);
    FrmEmpWarning frmEmpWarning = ctrlEmpWarning.getForm();
    if(datafor.equals("listwarning")){
        String whereClause = "";
        String order = "";
        Vector listwarning = new Vector();

        
        int index = -1;
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpWarning.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmpWarning.actionList(iCommand, start, vectSize, recordToGet);
        }
            
            whereClause = PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            vectSize = PstEmpWarning.getCount("");
            order = PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID];
            listwarning = PstEmpWarning.list(start, recordToGet, whereClause, order);
        
        if (listwarning != null && listwarning.size()>0){
        %>
        <table id="listWarn" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Company</th>
                    <th>Division</th>
                    <th>Department</th>
                    <th>Section</th>
                    <th>Position</th>
                    <th>Level</th>
                    <th>Employee Category</th>
                    <th>Break Date</th>
                    <th>Break Fact</th>
                    <th>Warning Date</th>
                    <th>Warning Level ( Point )</th>
                    <th>Warning By </th>
                    <th>Valid Until </th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listwarning.size(); i++) {
                EmpWarning empWarning = (EmpWarning) listwarning.get(i);
                        Company comp = new Company();
                        Division div = new Division();
                        Department dep = new Department();
                        Section sec = new Section();
                        Position pos = new Position();
                        Level level = new Level();
                        EmpCategory cat = new EmpCategory();
                        
                        String compString = "-";
                        String divString = "-";
                        String depString = "-";
                        String secString = "-";
                        String posString = "-";
                        String levelString = "-";
                        String catString = "-";

                        try {
                            comp = PstCompany.fetchExc(empWarning.getCompanyId());
                            compString = comp.getCompany();
                            div = PstDivision.fetchExc(empWarning.getDivisionId());
                            divString = div.getDivision();
                            dep = PstDepartment.fetchExc(empWarning.getDepartmentId());
                            depString = dep.getDepartment();
                            sec = PstSection.fetchExc(empWarning.getSectionId());  
                            secString = sec.getSection();
                            pos = PstPosition.fetchExc(empWarning.getPositionId()); 
                            posString = pos.getPosition();
                            level = PstLevel.fetchExc(empWarning.getLevelId()); 
                            levelString = level.getLevel();
                            cat = PstEmpCategory.fetchExc(empWarning.getEmpCategoryId());
                            catString = cat.getEmpCategory();
                        }
                        catch(Exception e) {
                            comp = new Company(); 
                            div = new Division();
                            dep = new Department();
                            sec = new Section();
                            pos = new Position();
                            level = new Level();
                            cat = new EmpCategory();
                        }                        

                                        

			 Vector rowx = new Vector();
			 if(oid == empWarning.getOID())
				 index = i;

			Warning warning = new Warning();
			if(empWarning.getWarnLevelId() != -1){
				try{
					warning = PstWarning.fetchExc(empWarning.getWarnLevelId());
				}catch(Exception exc){
					warning = new Warning();
				}
			}
                        String breakDate = "";
                        breakDate = Formater.formatDate(empWarning.getBreakDate(), "MMM d, yyyy");

                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%= divString %></td>
                    <td><%= depString %></td>
                    <td><%= secString %></td>
                    <td><%= posString %></td>
                    <td><%= levelString %></td>
                    <td><%= catString %></td>
                    <td><%= String.valueOf(warning.getWarnDesc()) %></td>
                    <td><%= breakDate %></td>
                    <td><%= empWarning.getBreakFact() %></td>
                    <td><%= Formater.formatDate(empWarning.getWarningDate(), "MMM d, yyyy") %></td>
                    <td></td>
                    <td><%= empWarning.getWarningBy() %></td>
                    <td><%= Formater.formatDate(empWarning.getValidityDate(), "MMM d, yyyy") %></td>
                    <td width ="100px">
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empWarning.getOID() %>" data-empId="<%= empWarning.getEmployeeId() %>" class="addeditdatawarning btn btn-primary" data-command="<%= Command.NONE %>" data-for="showwarnform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empWarning.getOID() %>" data-for="showwarnform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedatawarning"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                       
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
    }else if(datafor.equals("showwarnform")){

        if(iCommand == Command.SAVE){
            ctrlEmpWarning.action(iCommand, oid,request,emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            ctrlEmpWarning.action(iCommand, oid,request,emplx.getFullName(), appUserIdSess);
        }else{
            EmpWarning empWarning = new EmpWarning();
            Employee employee = new Employee();
            try {
                employee = PstEmployee.fetchExc(employeeid);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if(oid != 0){
                try{
                    empWarning = PstEmpWarning.fetchExc(oid);
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <input type="hidden" name="<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=employeeid%>">
            <div class="row">
            <div class="col-md-12">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="Company" class="col-sm-4">Company</label>
                    <div class="col-sm-6">
                        <select class="form-control"  name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_COMPANY_ID]%>" id="company">
                        <option value="">-select-</option>
                        <%
                        Vector listCompany = PstCompany.list(0, 0, "", PstCompany.fieldNames[PstCompany.FLD_COMPANY]);
                        if (listCompany != null && listCompany.size()>0){
                            for(int i=0; i<listCompany.size(); i++){
                                Company comp = (Company)listCompany.get(i);
                                if (empWarning.getCompanyId() == comp.getOID()){
                                    %>
                                    <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                    <%
                                } if (employee.getCompanyId() == comp.getOID()) { %>
                                    <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>                            
                                <% } else  {
                                    %>
                                    <option value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                    <%
                                }
                            }
                        }
                        %>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="Division" class="col-sm-4">Division</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_DIVISION_ID]%>" id="division">
                        <option value="">-select-</option>
                        <%
                            Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                            if (listDivision != null && listDivision.size()>0){
                                for(int i=0; i<listDivision.size(); i++){
                                    Division divisi = (Division)listDivision.get(i);
                                    if (empWarning.getDivisionId() == divisi.getOID()){
                                        %><option selected="selected" value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                    } if (employee.getDivisionId() == divisi.getOID()) { %>
                                        <option selected="selected" value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%= divisi.getDivision()%></option>                            
                                    <% } else {
                                        %><option value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                    }
                                }
                            }

                        %>
                        </select>
                    </div>  
                </div>
                <div class="form-group">
                    <label for="Company" class="col-sm-4">Department</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_DEPARTMENT_ID]%>" id="department">
                            <option value="">-select-</option>
                            <%
                                Vector listDepart = PstDepartment.list(0, 0, "" , "");
                                if (listDepart != null && listDepart.size()>0){
                                    for(int i=0; i<listDepart.size(); i++){
                                        Department depart = (Department)listDepart.get(i);
                                        if (empWarning.getDepartmentId() == depart.getOID()){
                                            %><option selected="selected" value="<%=depart.getOID()%>" class="<%=depart.getDivisionId()%>"><%=depart.getDepartment()%></option><%
                                        } if (employee.getDepartmentId() == depart.getOID()) { %>
                                            <option selected="selected" value="<%=depart.getOID()%>" class="<%=depart.getDivisionId()%>"><%= depart.getDepartment()%></option>                            
                                        <% } else {
                                            %><option value="<%=depart.getOID()%>" value="<%=depart.getDivisionId()%>" class="<%=depart.getDivisionId()%>"><%=depart.getDepartment()%></option><%
                                        }
                                    }
                                }

                            %>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="Company" class="col-sm-4">Section</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_SECTION_ID]%>" id="section">
                        <option value="">-select-</option>
                        <%
                            Vector listSection = PstSection.list(0, 0, "", PstSection.fieldNames[PstSection.FLD_SECTION]);

                            if (listSection != null && listSection.size()>0){
                                for(int i=0; i<listSection.size(); i++){
                                    Section section = (Section)listSection.get(i);
                                    if (empWarning.getSectionId() == section.getOID()){
                                        %><option selected="selected" value="<%=section.getOID()%>" class="<%=section.getDepartmentId()%>"><%=section.getSection()%></option><%
                                    } if (employee.getSectionId() == section.getOID()) { %>
                                        <option selected="selected" value="<%=section.getOID()%>" class="<%=section.getDepartmentId()%>"><%= section.getSection()%></option>                            
                                    <% } else {
                                        %><option value="<%=section.getOID()%>" class="<%=section.getDepartmentId()%>"><%=section.getSection()%></option><%
                                    }
                                }
                            }

                        %>
                        </select>
                    </div>
                </div>
            
                <div class="form-group">
                    <label for="Company" class="col-sm-4">Position</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_POSITION_ID]%>">
                            <option value="0">-select-</option>
                            <%
                                Vector listPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);

                                if (listPosition != null && listPosition.size()>0){
                                    for(int i=0; i<listPosition.size(); i++){
                                        Position position = (Position)listPosition.get(i);
                                        if (empWarning.getPositionId() == position.getOID()){
                                            %><option selected="selected" value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                        } else {
                                            %><option value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                        }
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
            
                <div class="form-group">
                    <label for="Company" class="col-sm-4">Level</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_LEVEL_ID]%>">
                            <option value="0">-select-</option>
                            <%
                                Vector listLevel = PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_LEVEL]);

                                if (listLevel != null && listLevel.size()>0){
                                    for(int i=0; i<listLevel.size(); i++){
                                        Level level = (Level)listLevel.get(i);
                                        if (empWarning.getLevelId() == level.getOID()){
                                            %><option selected="selected" value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                                        } else {
                                            %><option value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                                        }
                                    }
                                }


                            %>
                        </select>
                    </div>
                </div>
            
                <div class="form-group">
                    <label for="Company" class="col-sm-4">Employee Category</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_EMP_CATEGORY_ID]%>">
                            <option value="0">-select-</option>
                            <%
                                Vector listCategory = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]);

                                if (listCategory != null && listCategory.size()>0){
                                    for(int i=0; i<listCategory.size(); i++){
                                        EmpCategory empCategory = (EmpCategory)listCategory.get(i);
                                        if (empWarning.getEmpCategoryId() == empCategory.getOID()){
                                            %><option selected="selected" value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                                        } else {
                                            %><option value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                                        }
                                    }
                                }


                            %>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="col-sm-4">Break Date</label>
                    <div class="col-sm-6">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <%
                            String DATE_FORMAT_NOW = "yyyy-MM-dd";
                            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                            Date breakDate = empWarning.getBreakDate()== null ? new Date() : empWarning.getBreakDate();
                            String strBreakDate = sdf.format(breakDate);
                            %>
                            <input type="text" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_BREAK_DATE]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strBreakDate %>">
                        </div>
                    </div>
                </div>
                
                
                <div class="form-group">
                    <label class="col-sm-4">Break Fact</label>
                    <div class="col-sm-6">
                        <textarea class="form-control" name="<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_BREAK_FACT] %>"><%= empWarning.getBreakFact() %></textarea>
                    </div>
                </div>
            
                <div class="form-group">
                    <label class="col-sm-4">Warning Date</label>
                    <div class="col-sm-6">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <%
                            Date warnDate = empWarning.getWarningDate()== null ? new Date() : empWarning.getWarningDate();
                            String strWarnDate = sdf.format(warnDate);
                            %>
                            <input type="text" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_DATE]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strWarnDate %>">
                        </div>
                    </div>
                </div>
            
                <div class="form-group">
                    <label class="col-sm-4">Warning Level ( Point ) :</label>
                        <div class="col-sm-6">
                            <%  Vector listWarn = PstWarning.listAll();
                                if((listWarn != null) && (listWarn.size() > 0)){
                                    %>
                                    <select type="text" name="<%= frmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_LEVEL_ID] %>" class="form-control" value="">
                                    <% for(int i=0;i<listWarn.size();i++){
                                        Warning warn = (Warning) listWarn.get(i);
                                        if (empWarning.getWarnLevelId() == warn.getOID()){
                                            %><option selected="selected" value="<%=warn.getOID()%>"><%=warn.getWarnPoint()%></option><%
                                        } else {
                                            %><option value="<%=warn.getOID()%>"><%=warn.getWarnPoint()%></option><%
                                        }
                                    } %>
                                    </select>
                                    <%
                                } else { %>
                                <font class="comment">No
                                Warning available</font>
                                <% }%>
                                * <%= frmEmpWarning.getErrorMsg(FrmEmpWarning.FRM_FIELD_WARN_LEVEL_ID)   
                            %>
                        </div>
                </div>
            
                <div class="form-group">
                    <label class="col-sm-4">Warning By :</label>
                    <div class="col-sm-6">
                        <%
                            
                            
                            Vector listEmployee = new Vector();
                            //leaveConfig.getApprovalEmployeeTopLink(objLeaveApplication.getDepHeadApproval(), 3);
                            listEmployee = PstMappingPosition.getWarningEmployeeTopLink(employeeid, 5);
                            //String whereEmp = PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " IN ( " +in+ " )";
                            //listEmployee = PstEmployee.list(0, 0, whereEmp, PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                            %>
                            <select class="form-control" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_BY]%>">
                            <% for(int i=0;i<listEmployee.size();i++){
                                Employee emp = (Employee) listEmployee.get(i);
                                if(empWarning.getWarningBy()==emp.getFullName()){
                            %> <option selected="selected" value="<%= emp.getFullName() %>"><%= emp.getFullName() %></option><%  
                                } else {
                            %><option value="<%= emp.getFullName()%>"><%= emp.getFullName()%></option><%
                                }
                            }%>
                            </select>    
                            
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-sm-4">Valid Date</label>
                    <div class="col-sm-6">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <%
                            Date valDate = empWarning.getValidityDate()== null ? new Date() : empWarning.getValidityDate();
                            String strVldDate = sdf.format(valDate);
                            %>
                            <input type="text" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_VALID_DATE]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strVldDate %>">
                        </div>
                    </div>
                </div>    
                </div>
            </div>
            </div>
            
            
            <%
    }
}
    
%>  