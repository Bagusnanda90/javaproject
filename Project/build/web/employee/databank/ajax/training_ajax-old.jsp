<%-- 
    Document   : company_ajax
    Created on : 1-Jul-2016, 14:07:32
    Author     : Arys
--%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.form.employee.FrmTrainingHistory"%>
<%@page import="com.dimata.harisma.entity.employee.TrainingHistory"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.PstTrainingHistory"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.CtrlTrainingHistory"%>
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
    if(datafor.equals("listtraining")){
        String whereClause = "";
        String order = "";
        Vector listtraining = new Vector();

        CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstTrainingHistory.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlTrainingHistory.actionList(iCommand, start, vectSize, recordToGet);
        }
            whereClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            vectSize = PstTrainingHistory.getCount("");
            order = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE];
            listtraining = PstTrainingHistory.list(start, recordToGet, whereClause, order);
        
        if (listtraining != null && listtraining.size()>0){
        %>
        <table id="listtraining" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Training Program</th>
                    <th>Training Title</th>
                    <th>Trainer</th>
                    <td>Start Date</td>
                    <td>End Date</td>
                    <td>Duration</td>
                    <td>Remark</td>
                    <td>Action</td>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listtraining.size(); i++) {
                TrainingHistory trainingHistory = (TrainingHistory) listtraining.get(i);
                String trainingName = ""+trainingHistory.getTrainingProgram();
                trainingHistory.getTrainingId();
                double duration = trainingHistory.getDuration() / 60;
                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%= trainingName %></td>
                    <td><%= trainingHistory.getTrainingTitle()!=null && trainingHistory.getTrainingTitle().length()>0 ? trainingHistory.getTrainingTitle() : "-" %></td>
                    <td><%= trainingHistory.getTrainer() != null && trainingHistory.getTrainer().length()>0 ? trainingHistory.getTrainer() : "-" %></td>
                    <td><%= ""+trainingHistory.getStartDate() %></td>
                    <td><%= ""+trainingHistory.getEndDate() %></td>
                    <td><%= ""+ duration+ " Jam - "+trainingHistory.getDuration() %></td>
                    <td><%= trainingHistory.getRemark() %></td>
                    <td>
                        <a href="javascript:" data-oid="<%= trainingHistory.getOID() %>" data-empId="<%= trainingHistory.getEmployeeId() %>" data-for="showtrainingform" class="addeditdatatraining" data-command="<%= Command.NONE %>"><i class="fa fa-pencil"></i> Edit </a> 
                        |
                        <a href="javascript:" data-oid="<%= trainingHistory.getOID() %>" data-for="showtrainingform" class="deletedatatraining" data-command="<%= Command.DELETE %>"><i class="fa fa-times"></i> Delete</a>
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
    }else if(datafor.equals("showtrainingform")){

        if(iCommand == Command.SAVE){
            CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
            ctrlTrainingHistory.action(iCommand, oid,request,emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
            ctrlTrainingHistory.action(iCommand, oid,request,emplx.getFullName(), appUserIdSess);
        }else{
            Company company = new Company();
            if(oid != 0){
                try{
                    company = PstCompany.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
                <div class="form-group">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-6">
                    <div class="input-group-group">
                        <span class="input-group-btn">
                                    <button id="filter" class="btn btn-primary btn-block" onclick="add();">
                                        <span >Browse Training Program</span>
                                    </button>
                                    <button id="filter" class="btn btn-primary btn-block" onclick="add();">
                                        <span >Browse From Training Activity</span>
                                    </button>      
                        </span>
                    </div> 
                    </div>
                    <div class="col-sm-3"></div>
                </div>
                <div class="form-group">
                    <label for="Company" class="col-sm-3">Training Title</label>
                    <div class="col-sm-9">
                        <input type="text" name="training" class="form-control" value=""></input>
                    </div>
                </div>
                <div class="form-group">
                    <label for="Company" class="col-sm-3">Training Program</label>
                    <div class="col-sm-9">
                        <input type="text" name="training" class="form-control" value=""></input>
                    </div>
                </div>
                <div class="form-group">
                    <label for="Company" class="col-sm-3">Trainer</label>
                    <div class="col-sm-9">
                        <input type="text" name="training" class="form-control" value=""></input>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3">Start date - End date</label>
                    <div class="col-sm-9">
                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                        <span class="input-group-addon"><input type="text" name="" id="datepicker1" value="" /></span>
                        <span class="input-group-addon">to</span>
                        <span class="input-group-addon"><input type="text" name="" id="datepicker2" value="" /></span>
                    </div>
                </div>
                <div class="form-group">
                <label class="col-sm-3">Start Time - End Time</label>
                    <div class="col-sm-9">
                        <span class="input-group-addon">
                            <select>
                                <option>10</option>>    
                            </select>
                        </span>
                        <span class="input-group-addon">
                            <select>
                                <option>10</option>>    
                            </select>
                        </span>
                        <span class="input-group-addon">to</span>
                        <span class="input-group-addon">
                            <select>
                                <option>10</option>>    
                            </select>
                        </span>
                        <span class="input-group-addon">
                            <select>
                                <option>10</option>>    
                            </select>
                        </span>
                    </div>
            </div>
            <div class="form-group">
                <label for="Company" class="col-sm-3">Duration</label>
                <div class="col-sm-9">
                    <input type="text" name="training" class="form-control" value=""></input>
                </div>
            </div>
            <div class="form-group">
                <label for="Company" class="col-sm-3">Remark</label>
                <div class="col-sm-9">
                    <textarea class="form-control"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="Company" class="col-sm-3">Point</label>
                <div class="col-sm-9">
                    <input type="text" name="training" class="form-control" value=""></input>
                </div>
            </div>
            <%
    }
}
    
%>  