<%-- 
    Document   : company_ajax
    Created on : 1-Jul-2016, 14:07:32
    Author     : Arys
--%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTraining"%>
<%@ page import = "com.dimata.gui.jsp.*" %>
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
    
    CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
    
    
    if(datafor.equals("listtraining")){
        String whereClause = "";
        String order = "";
        Vector listtraining = new Vector();

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
        <table id="listtraining" class="table table-bordered table-striped table-hover">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Training Program</th>
                    <th>Training Title</th>
                    <th>Trainer</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Duration</th>
                    <th>Remark</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listtraining.size(); i++) {
                TrainingHistory trainingHistory = (TrainingHistory) listtraining.get(i);
                String trainingName = ""+trainingHistory.getTrainingProgram();
                String Tprogram = "-";
                trainingHistory.getTrainingId();
                double duration = trainingHistory.getDuration() / 60;
                
                Training training = new Training();
                try{
                    training = PstTraining.fetchExc(trainingHistory.getTrainingId());
                    Tprogram = training.getName();
                }
                catch(Exception e){
                        System.out.println("external=>"+e.toString());
                    }
                        
                
                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%= Tprogram %></td>
                    <td><%= trainingHistory.getTrainingTitle()!=null && trainingHistory.getTrainingTitle().length()>0 ? trainingHistory.getTrainingTitle() : "-" %></td>
                    <td><%= trainingHistory.getTrainer() != null && trainingHistory.getTrainer().length()>0 ? trainingHistory.getTrainer() : "-" %></td>
                    <td><%= ""+trainingHistory.getStartDate() %></td>
                    <td><%= ""+trainingHistory.getEndDate() %></td>
                    <td><%= ""+ duration+ " Jam - "+trainingHistory.getDuration() %></td>
                    <td><%= trainingHistory.getRemark() %></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= trainingHistory.getOID() %>" data-empId="<%= trainingHistory.getEmployeeId() %>" class="addeditdatatraining btn btn-primary" data-command="<%= Command.NONE %>" data-for="showtraining"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= trainingHistory.getOID() %>" data-for="showtraining" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedatatraining"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                        
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
    }else if(datafor.equals("showtraining")){
        

        if(iCommand == Command.SAVE){
            //CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
            ctrlTrainingHistory.action(iCommand, oid,request,emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            //CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
            ctrlTrainingHistory.action(iCommand, oid,request,emplx.getFullName(), appUserIdSess);
        }else{
            TrainingHistory trainingHistory = new TrainingHistory();
            Training training = new Training();
           
            if(oid != 0){
                try{
                    trainingHistory = PstTrainingHistory.fetchExc(oid);
                    training = PstTraining.fetchExc(trainingHistory.getTrainingId());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <input type="hidden" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID] %>" value="<%= employeeid %>">
            <div class="form-group">
                    <label class="col-sm-4">Training Title :</label>
                    <div class="col-sm-8">
                        <input type="text" id="training_title" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_TITLE] %>" value="<%= trainingHistory.getTrainingTitle() %>" class="form-control">
                    </div>
            </div>
            <div class="form-group">
                    <label class="col-sm-4">Training Program :</label>
                    <div class="col-sm-8">
                        <div class="form-group col-sm-10">
                            
                            <button type="button" id="filter" class="btn btn-success custom-button-width .navbar-right browse" href="#stack1" data-for="filterup" data-oid="0">Browse T. Program</button>
                            <button type="button" class="btn btn-primary custom-button-width .navbar-right">Browse T. Activity</button>
                        </div>
                        <input type="hidden" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_ID]%>" id="uppositionoid">
                        <input type="text" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_ID]%>" disabled="true" value="<%= training.getName() %>" id="uppositionname" class="form-control">
                    </div>
            </div>
          
            
             <div class="form-group">
                <label class="col-sm-4">Trainer :</label>
                <div class="col-sm-8">
                <input type="text" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINER] %>" class="form-control" value="<%=trainingHistory.getTrainer()%>">
                </div>
             </div>
            
            <div class="form-group">
                <label class="col-sm-4">Start date - End date</label>
                <div class="col-sm-8">
                    <div class="form-group col-sm-9">
                       <table>
                        <tr>
                    <td><input type="text"  class="form-control pull-right datepicker" id="datepicker" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_DATE] %>" value="<%=trainingHistory.getStartDate()%>" ></td>
                    <td>To</td>
                    <td><input type="text" class="form-control pull-right datepicker" id="datepicker" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_DATE] %>"   value="<%=trainingHistory.getEndDate()%>" ></td>
                       </tr>
                       </table>
                    </div>
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-4">Start time - End time</label>
                <div class="col-sm-8">
                    <div class="form-group col-sm-9">
                       <div id="div_input">
                        <%=ControlDate.drawTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_TIME], (trainingHistory.getStartTime() == null) ? new Date() : trainingHistory.getStartTime(), "formElemen")%> To 
                        <%=ControlDate.drawTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_TIME], (trainingHistory.getEndTime() == null) ? new Date() : trainingHistory.getEndTime(), "formElemen")%>
                       </div>
                    </div>
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-4">Duration :</label>
                <div class="col-sm-8">
                <input type="text" name="training" class="form-control" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_DURATION] %>" value="<%=trainingHistory.getDuration()%>">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4">Remark :</label>
                <div class="col-sm-8">
                <textarea class="form-control" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_REMARK] %>"><%=trainingHistory.getRemark() %></textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4">Point :</label>
                <div class="col-sm-8">
                <input type="text" class="form-control" name="<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_POINT] %>" value="<%= trainingHistory.getPoint() %>">
                </div>
            </div>
            <%
    }
}
    
%>  