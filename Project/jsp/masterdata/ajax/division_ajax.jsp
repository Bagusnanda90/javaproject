<%-- 
    Document   : division_ajax
    Created on : 27-Jun-2016, 19:49:35
    Author     : Acer
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDivision"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String divName = FRMQueryString.requestString(request, "division_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listdivision")){
        String whereClause = "";
        String order = "";
        Vector listDivision = new Vector();

        CtrlDivision ctrlDivision = new CtrlDivision(request);
        FrmDivision FrmDivision = ctrlDivision.getForm();

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstDivision.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlDivision.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(divName.equals(""))){
            whereClause = PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" LIKE '%"+divName+"%'";
            order = PstDivision.fieldNames[PstDivision.FLD_DIVISION];
            vectSize = PstDivision.getCount(whereClause);
            listDivision = PstDivision.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstDivision.getCount("");
            order = PstDivision.fieldNames[PstDivision.FLD_DIVISION];
            listDivision = PstDivision.list(start, recordToGet, "", order);
        }

        if (listDivision != null && listDivision.size()>0){
        %>
        <table id="listDivision" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Company</th>
                    <th>Division</th>
                    <th>Description</th>
                    <th>Type</th>
                    <th>Valid Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listDivision.size(); i++) {
                Division division = (Division) listDivision.get(i);
                DivisionType divType = new DivisionType();
                    String divisionTypeName = "-";
                    try {
                        divType = PstDivisionType.fetchExc(division.getDivisionTypeId());
                        divisionTypeName = divType.getTypeName();
                    } catch (Exception e) {
                        //System.out.print("getDivisionType=>"+e.toString());//
                    }
                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=PstCompany.getCompanyName(division.getCompanyId())%></td>
                    <td><%=division.getDivision()%></td>
                    <td><%=division.getDescription()%></td>
                    <td><%=divisionTypeName%></td>
                    <td><%= PstDivision.validStatusValue[division.getValidStatus()] %></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= division.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showdivisionform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= division.getOID() %>" data-for="showdivisionform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
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
    }else if(datafor.equals("showdivisionform")){

        if(iCommand == Command.SAVE){
            CtrlDivision ctrlDivision = new CtrlDivision(request);
            ctrlDivision.action(iCommand, oid,"");
        }else if(iCommand == Command.DELETE){
            CtrlDivision ctrlDivision = new CtrlDivision(request);
            ctrlDivision.action(iCommand, oid,"");
        }else{
            Division division = new Division();
            if(oid != 0){
                try{
                    division = PstDivision.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Division">Division :</label>
                <input type="text" class="form-control" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION]%>"  value="<%= division.getDivision()%>">
            </div>
            <div class="form-group">
                <label for="Company">Company :</label>
                <select name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_COMPANY_ID]%>" class="form-control" id="company">
                    <option value="0">-select-</option>
                    <%
                        Vector listCompany = PstCompany.list(0, 0, "", "");
                        if (listCompany != null && listCompany.size() > 0) {
                            for (int i = 0; i < listCompany.size(); i++) {
                                Company comp = (Company) listCompany.get(i);
                                if (division.getCompanyId() == comp.getOID()) {
                                %>
                                <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany()%></option>
                                <%
                                } else {
                                %>
                                <option value="<%=comp.getOID()%>"><%= comp.getCompany()%></option>
                                <%
                                }
                            }
                        }
                    %>
                </select>
                </div>
            <div class="form-group">
                <label for="Description">Description :</label>
                <textarea rows="5" class="form-control" id="div_desc" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DESCRIPTION]%>"><%= division.getDescription()%></textarea>
            </div>
            <div class="form-group">
                <label for="Type">Division Type Id :</label>
                <select name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_TYPE_ID]%>" class="form-control" id="type">
                    <option value="0">-select-</option>
                    <%
                        Vector listDivisionType = PstDivisionType.list(0, 0, "", "");
                        if (listDivisionType != null && listDivisionType.size() > 0) {
                            for (int ldt = 0; ldt < listDivisionType.size(); ldt++) {
                                DivisionType divT = (DivisionType) listDivisionType.get(ldt);
                                if (division.getDivisionTypeId() == divT.getOID()) {
                                %>
                                <option selected="selected" value="<%=divT.getOID()%>"><%=divT.getTypeName()%></option>
                                <%
                                } else {
                                %>
                                <option value="<%=divT.getOID()%>"><%=divT.getTypeName()%></option>
                                <%
                                }
                            }
                        }
                    %>
                </select>
                </div>
            <div class="form-group">
                <label for="Valid Status">Valid Status :</label>
                <select name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_STATUS]%>" class="form-control" id="valid_status">
                    <%
                        if (division.getValidStatus() == PstDivision.VALID_ACTIVE) {
                    %>
                    <option value="<%=PstDivision.VALID_ACTIVE%>" selected="selected">Active</option>
                    <option value="<%=PstDivision.VALID_HISTORY%>">History</option>
                    <%
                    } else {
                    %>
                    <option value="<%=PstDivision.VALID_ACTIVE%>">Active</option>
                    <option value="<%=PstDivision.VALID_HISTORY%>" selected="selected">History</option>
                    <%
                        }
                    %>
                </select>
                
            </div>
            <div class="form-group">
                <label>Masa Berlaku</label>
                <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    Date dateStart = division.getValidStart() == null ? new Date() : division.getValidStart();
                    Date dateEnd = division.getValidEnd() == null ? new Date() : division.getValidEnd();
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    String strValidStart = sdf.format(dateStart);
                    String strValidEnd = sdf.format(dateEnd);
                %>
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <span class="input-group-addon"><input type="text" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_START]%>" id="datepicker1" value="<%=strValidStart%>" /></span>
                <span class="input-group-addon">to</span>
                <span class="input-group-addon"><input type="text" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VALID_END]%>" id="datepicker2" value="<%=strValidEnd%>" /></span>
                
            </div>
            <a> note: fill some of field below, if you choose Branch of Company </a>
            <div class="form-group">
                <label for="Address">Address :</label>
                <textarea rows="5" class="form-control" id="address" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS]%>"><%=division.getAddress()%></textarea>
            </div>
            <div class="form-group">
                <label for="City">City :</label>
                <input type="email" class="form-control" id="city" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]%>" value="<%=division.getCity()%>">
            </div>
            <div class="form-group">
                <label for="NPWP">NPWP :</label>
                <input type="email" class="form-control" id="npwp" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]%>" value="<%=division.getNpwp()%>">
            </div>
            <div class="form-group">
                <label for="Province">Province :</label>
                <input type="email" class="form-control" id="province" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]%>" value="<%=division.getProvince()%>">
            </div>
            <div class="form-group">
                <label for="Region">Region :</label>
                <input type="email" class="form-control" id="region" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]%>" value="<%=division.getRegion()%>">
            </div>
            <div class="form-group">
                <label for="Sub Region">Sub Region :</label>
                <input type="email" class="form-control" id="subregion" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]%>" value="<%=division.getSubRegion()%>" >
            </div>
            <div class="form-group">
                <label for="Village">Village :</label>
                <input type="email" class="form-control" id="village" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]%>" value="<%=division.getVillage()%>">
            </div>
            <div class="form-group">
                <label for="Area">Area :</label>
                <input type="email" class="form-control" id="area" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]%>" value="<%=division.getArea()%>"> 
            </div>
            <div class="form-group">
                <label for="Telephone">Telephone :</label>
                <input type="email" class="form-control" id="telephone" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]%>" value="<%=division.getTelphone()%>">
            </div>
            <div class="form-group">
                <label for="Fax Number">Fax Number :</label>
                <input type="email" class="form-control" id="faxnumber" name="<%=FrmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]%>" value="<%=division.getFaxNumber()%>">
            </div>
        
        
        <%
    }
}
    
%>    