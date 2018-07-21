<%-- 
    Document   : company_ajax
    Created on : 1-Jul-2016, 14:07:32
    Author     : Arys
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmpReprimand"%>
<%@page import="com.dimata.harisma.entity.employee.EmpReprimand"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmpReprimand"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmpReprimand"%>
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
    if(datafor.equals("listreprimand")){
        String whereClause = "";
        String order = "";
        Vector listrep = new Vector();

        CtrlEmpReprimand ctrlEmpReprimand = new CtrlEmpReprimand(request);
        int index = -1;
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpReprimand.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmpReprimand.actionList(iCommand, start, vectSize, recordToGet);
        }
            
            whereClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            vectSize = PstEmpReprimand.getCount("");
            order = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID];
            listrep = PstEmpReprimand.list(start, recordToGet, whereClause, order);
        
        if (listrep != null && listrep.size()>0){
        %>
        <table id="listrep" class="table table-bordered table-striped table-responsive">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Company</th>
                    <th>Division</th>
                    <th>Department</th>
                    <th>Position</th>
                    <th>Date</th>
                    <th>Chapter</th>
                    <th>Article</th>
                    <th>Page</th>
                    <th>Description</th>
                    <th>Valid Until </th>
                    <th>Level</th>
                    <th>Point</th>
                    <th>Document</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listrep.size(); i++) {
                EmpReprimand empReprimand = (EmpReprimand) listrep.get(i);
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
                            comp = PstCompany.fetchExc(empReprimand.getCompanyId());
                            compString = comp.getCompany();
                            div = PstDivision.fetchExc(empReprimand.getDivisionId());
                            divString = div.getDivision();
                            dep = PstDepartment.fetchExc(empReprimand.getDepartmentId());
                            depString = dep.getDepartment();
                            sec = PstSection.fetchExc(empReprimand.getSectionId());   
                            secString = sec.getSection();
                            pos = PstPosition.fetchExc(empReprimand.getPositionId());
                            posString = pos.getPosition();
                            level = PstLevel.fetchExc(empReprimand.getLevelId()); 
                            levelString = level.getLevel();
                            cat = PstEmpCategory.fetchExc(empReprimand.getEmpCategoryId()); 
                            catString = cat.getEmpCategory();
                        }
                        catch(Exception e) {
                            comp = new Company();     
                            div = new Division();
                            sec = new Section();
                            pos = new Position();
                            level = new Level();
                            cat = new EmpCategory();
                        }
                        String dataDate = "";
                        dataDate = ""+Formater.formatDate(empReprimand.getReprimandDate(), "d-MMM-yyyy");
                        
                        String chapter = "";
                        try {
                            WarningReprimandBab bab = PstWarningReprimandBab.fetchExc(Long.valueOf(empReprimand.getChapter()));
                            chapter = bab.getBabTitle();
                        } catch(Exception e){
                            System.out.println("chapter"+e.toString());
                        }
                        String article = "";
                        try {
                            WarningReprimandPasal pasal = PstWarningReprimandPasal.fetchExc(Long.valueOf(empReprimand.getArticle()));
                            article = pasal.getPasalTitle();
                        } catch(Exception e){
                            System.out.println("article"+e.toString());
                        }
                        
                        Vector rowx = new Vector();
			 if(oid == empReprimand.getOID())
				 index = i;

			Reprimand reprimand = new Reprimand();
			if(empReprimand.getReprimandLevelId() != -1){
				try{
					reprimand = PstReprimand.fetchExc(empReprimand.getReprimandLevelId());
				}catch(Exception exc){
					reprimand = new Reprimand();
				}
			}
                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=compString %></td>
                    <td><%=divString %></td>
                    <td><%=depString %></td>
                    <td><%=posString %></td>
                    <td><%= dataDate %></td>
                    <td><%= chapter%></td>
                    <td><%= article %></td>
                    <td><%= empReprimand.getPage() %></td>
                    <td><%= (empReprimand.getDescription().length() > 100) ? empReprimand.getDescription().substring(0, 100) + " ..." : empReprimand.getDescription() %></td>
                    <td><%= Formater.formatDate(empReprimand.getValidityDate(), "d-MMM-yyyy") %></td>
                    <td><%=  String.valueOf(reprimand.getReprimandDesc()) %></td>
                    <td><%=  String.valueOf(reprimand.getReprimandPoint())%></td>
                    <%  String document = "";
                    if(!(empReprimand.getDocument().equals(""))){
                        document = approot+"/imgdoc/"+  empReprimand.getDocument();
                    }
                %>
                <td><a href="<%=document%>" target="_blank"> <%=empReprimand.getDocument()%> </a></td>
                    <td width="350px">
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empReprimand.getOID() %>" data-empId="<%= empReprimand.getEmployeeId() %>" class="addeditdatareprimand btn btn-primary" data-command="<%= Command.NONE %>" data-for="showrepform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empReprimand.getOID() %>" data-empId="<%= empReprimand.getEmployeeId() %>" data-for="showuploadrep" data-command="<%= Command.UPDATE %>" class="btn btn-success uploaddata"  data-toggle="tooltip" data-placement="top" title="Upload"><i class="fa fa-upload"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empReprimand.getOID() %>" data-for="showrepform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedatareprimand"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>                      
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
    }else if(datafor.equals("showrepform")){

        if(iCommand == Command.SAVE){
            CtrlEmpReprimand ctrlEmpReprimand = new CtrlEmpReprimand(request);
            ctrlEmpReprimand.action(iCommand, oid, request,emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlEmpReprimand ctrlEmpReprimand = new CtrlEmpReprimand(request);
            ctrlEmpReprimand.action(iCommand, oid,request,emplx.getFullName(), appUserIdSess);
        }else{
            EmpReprimand empReprimand = new EmpReprimand();
            Employee employee = new Employee();
            long babId = 0;
            long pasalId = 0;
            long ayatId = 0;
            try {
                employee = PstEmployee.fetchExc(employeeid);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if(oid != 0){
                try{
                    empReprimand = PstEmpReprimand.fetchExc(oid);
                    babId = Long.valueOf(empReprimand.getChapter());
                    pasalId = Long.valueOf(empReprimand.getArticle());
                    ayatId = Long.valueOf(empReprimand.getVerse());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            
            <div class="row">
            <div class="col-md-12">
            <div class="col-md-6">
            <input type="hidden" name="<%= FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMPLOYEE_ID] %>" value="<%= employeeid %>">
            <div class="form-group">
                <label class="col-sm-4">Company</label>
                <div class="col-sm-6">
                <select class="form-control"  name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_COMPANY_ID]%>" id="company">
                <option value="">-select-</option>
                <%
                Vector listCompany = PstCompany.list(0, 0, "", PstCompany.fieldNames[PstCompany.FLD_COMPANY]);
                if (listCompany != null && listCompany.size()>0){
                    for(int i=0; i<listCompany.size(); i++){
                        Company comp = (Company)listCompany.get(i);
                        if (empReprimand.getCompanyId() == comp.getOID()){
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
                <label class="col-sm-4">Division</label>
                <div class="col-sm-6">
                <select class="form-control" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DIVISION_ID]%>" id="division">
                <option value="">-select-</option>
                <%
                    Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                    if (listDivision != null && listDivision.size()>0){
                        for(int i=0; i<listDivision.size(); i++){
                            Division divisi = (Division)listDivision.get(i);
                            if (empReprimand.getDivisionId() == divisi.getOID()){
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
                <label class="col-sm-4">Department </label>
                <div class="col-sm-6">
                <select class="form-control" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DEPARTMENT_ID]%>" id="department">
                <option value="">-select-</option>
                <%
                    Vector listDepart = PstDepartment.list(0, 0, "" , "");
                    if (listDepart != null && listDepart.size()>0){
                        for(int i=0; i<listDepart.size(); i++){
                            Department depart = (Department)listDepart.get(i);
                            if (empReprimand.getDepartmentId() == depart.getOID()){
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
                <label class="col-sm-4">Section </label>
                <div class="col-sm-6">
                <select class="form-control" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_SECTION_ID]%>" id="section">
                <option value="">-select-</option>
                <%
                    Vector listSection = PstSection.list(0, 0, "", PstSection.fieldNames[PstSection.FLD_SECTION]);

                    if (listSection != null && listSection.size()>0){
                        for(int i=0; i<listSection.size(); i++){
                            Section section = (Section)listSection.get(i);
                            if (empReprimand.getSectionId() == section.getOID()){
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
                <label class="col-sm-4">Position </label>
                <div class="col-sm-6">
                <select class="form-control" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_POSITION_ID]%>">
                    <option value="0">-select-</option>
                    <%
                        Vector listPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);

                        if (listPosition != null && listPosition.size()>0){
                            for(int i=0; i<listPosition.size(); i++){
                                Position position = (Position)listPosition.get(i);
                                if (empReprimand.getPositionId() == position.getOID()){
                                    %><option selected="selected" value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                } if(employee.getPositionId() == position.getOID()) {
                                    %><option selected="selected" value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                } else { %>
                                    <option value="<%= position.getOID() %>"><%= position.getPosition() %></option>
                              <%  }
                            }
                        }
                    %>
                </select>
                </div>
                </div>
                
                <div class="form-group">
                <label class="col-sm-4">Level </label>
                <div class="col-sm-6">
                <select class="form-control" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_LEVEL_ID]%>">
                    <option value="0">-select-</option>
                    <%
                        Vector listLevel = PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_LEVEL]);

                        if (listLevel != null && listLevel.size()>0){
                            for(int i=0; i<listLevel.size(); i++){
                                Level level = (Level)listLevel.get(i);
                                if (empReprimand.getLevelId() == level.getOID()){
                                    %><option selected="selected" value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                                } if(employee.getLevelId() == level.getOID()) {
                                    %><option selected="selected" value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                                } else { %>
                                    <option value="<%= level.getOID() %>"><%= level.getLevel() %></option>
                            <%  }
                            }
                        }


                    %>
                </select>
                </div>
                </div>
                <div class="form-group">
                <label class="col-sm-4">Employee Category </label>
                <div class="col-sm-6">
                <select class="form-control" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_EMP_CATEGORY_ID]%>">
                    <option value="0">-select-</option>
                    <%
                        Vector listCategory = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]);

                        if (listCategory != null && listCategory.size()>0){
                            for(int i=0; i<listCategory.size(); i++){
                                EmpCategory empCategory = (EmpCategory)listCategory.get(i);
                                if (empReprimand.getEmpCategoryId() == empCategory.getOID()){
                                    %><option selected="selected" value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                                } if(employee.getEmpCategoryId() == empCategory.getOID()) {
                                    %><option selected="selected" value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                                } else { %>
                                    <option value="<%= empCategory.getOID() %>"><%= empCategory.getEmpCategory() %></option>
                             <% }
                            }
                        }


                    %>
                </select>
                </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                <label class="col-sm-4">Reprimand Level (Point)</label>
                <div class="col-sm-6">
                <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_LEVEL_ID]%>" class="form-control">
                <option value="0">-select-</option>
                <%
                Vector listRepLevel = PstReprimand.listAll();
                if (listRepLevel != null && listRepLevel.size()>0){
                    for(int i=0; i<listRepLevel.size(); i++){
                        Reprimand rep = (Reprimand) listRepLevel.get(i);
                        if (empReprimand.getReprimandLevelId() == rep.getOID()){
                            %>
                            <option selected="selected" value="<%=rep.getOID()%>"><%=rep.getReprimandDesc()%> (<%=rep.getReprimandPoint()%>)</option>
                            <%
                        } else {
                            %>
                            <option value="<%=rep.getOID()%>"><%=rep.getReprimandDesc()%> (<%=rep.getReprimandPoint()%>)</option>
                            <%
                        }

                    }
                }
                %>
                </select>
                </div>
                </div>
                
                <div class="form-group">
                <label class="col-sm-4">Chapter/Bab</label>
                <div class="col-sm-6">
                <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_CHAPTER]%>" class="form-control" id="bab">
                    <option value="">-select-</option>
                    <%
                    Vector listBab = PstWarningReprimandBab.list(0, 0, "", PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID]);
                    if (listBab != null && listBab.size()>0){
                        for(int i=0; i<listBab.size(); i++){
                            WarningReprimandBab cbBab = (WarningReprimandBab) listBab.get(i);
                            if (babId == cbBab.getOID()){
                                %>
                                <option selected="selected" value="<%=cbBab.getOID()%>"><%=cbBab.getBabTitle()%></option>
                                <%
                            } else {
                                %>
                                <option value="<%=cbBab.getOID()%>"><%=cbBab.getBabTitle()%></option>
                                <%
                            }

                        }
                    }
                    %>
                </select>
                </div>
                </div>
                
                <div class="form-group">
                <label class="col-sm-4">Article/Pasal</label>
                <div class="col-sm-6">
                <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_ARTICLE]%>" class="form-control" id="pasal">
                    <option value="">-select-</option>
                    <%
                        Vector listPasal = PstWarningReprimandPasal.list(0, 0, "", PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE]);
                        if (listPasal != null && listPasal.size()>0){
                            for(int i=0; i<listPasal.size(); i++){
                                WarningReprimandPasal cbPasal = (WarningReprimandPasal) listPasal.get(i);
                                if (pasalId == cbPasal.getOID()){
                                    %>
                                    <option selected="selected" value="<%=cbPasal.getOID()%>" class="<%= cbPasal.getBabId() %>"><%= cbPasal.getPasalTitle() %></option>
                                    <%
                                } else {
                                    %>
                                    <option value="<%=cbPasal.getOID()%>" class="<%= cbPasal.getBabId() %>"><%= cbPasal.getPasalTitle() %></option>
                                    <%
                                }

                            }
                        }
                    
                    %>
                </select>
                </div>
            </div>
                
                <div class="form-group">
                <label class="col-sm-4">Verse/Ayat</label>
                <div class="col-sm-6">
                <select name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_VERSE]%>" class="form-control" id="ayat" onclick="getPage(this.value)">
                <option value="">-select-</option>
                <%
                    String orderAyat = PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE];
                    Vector listAyat = PstWarningReprimandAyat.list(0, 0, "", orderAyat);

                    if(listAyat != null && listAyat.size()>0){
                        for(int i=0; i<listAyat.size(); i++){
                            WarningReprimandAyat cbAyat = (WarningReprimandAyat)listAyat.get(i);
                            if (ayatId == cbAyat.getOID()){
                                %>
                                <option selected="selected" value="<%=cbAyat.getOID()%>" class="<%= cbAyat.getPasalId()%>"><%= cbAyat.getAyatTitle() %></option>
                                <%
                            } else {
                                %>
                                <option value="<%=cbAyat.getOID()%>" class="<%= cbAyat.getPasalId()%>"><%= cbAyat.getAyatTitle() %></option>
                                <%
                            }
                        }
                    }
                %>
                </select>
                </div>
                </div>
            
                <div class="form-group">
                <label class="col-sm-4">Page/Halaman</label>
                <div class="col-sm-6">
                <input type="text" id="page" name="<%= FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_PAGE] %>" value="<%= empReprimand.getPage() %>" class="form-control" ></input>
                </div>
                </div>
                
                <div class="form-group">
                    <label class="col-sm-4">Date</label>
                    <div class="col-sm-6">
                    <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                    <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    Date repDate = empReprimand.getReprimandDate()== null ? new Date() : empReprimand.getReprimandDate();
                    String strRepDate = sdf.format(repDate);
                    %>
                    <input type="text" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REP_DATE]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strRepDate %>">
                </div>
                </div>
                </div>
                
                <div class="form-group">
                    <label class="col-sm-4">Description</label>
                    <div class="col-sm-6">
                    <textarea rows="2" name="<%= FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_DESCRIPTION] %>" class="form-control"><%= empReprimand.getDescription() %></textarea>
                </div>
                </div>
                
                <div class="form-group">
                    <label class="col-sm-4">Valid Until</label>
                    <div class="col-sm-6">
                    <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                    <%
                    Date validDate = empReprimand.getValidityDate()== null ? new Date() : empReprimand.getValidityDate();
                    String strValidDate = sdf.format(validDate);
                    %>
                    <input type="text" name="<%=FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_VALID_UNTIL]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strValidDate %>">
                </div>
                    </div>
                </div>
            </div>     
            </div>
            </div>
            <%
    }
} else if (datafor.equals("showuploadrep")){
 
 
 
 %>
 <input required style="width:0px; height:0px;" id="fileupload" name ="fileupload" type="file">
 <div class="input-group my-colorpicker2 colorpicker-element">
        <input required id="tempname" name="tempname" class="form-control" type="text">
        <div style="cursor: pointer" class="input-group-addon" id="uploadtrigger">
            <i class="fa fa-file-pdf-o"></i>
        </div>
    </div>
 <%
}
    
%>    
    