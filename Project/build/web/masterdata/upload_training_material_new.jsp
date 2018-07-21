<%-- 
    Document   : upload_training_material_new
    Created on : Jan 6, 2016, 10:46:11 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTraining"%>
<%@page import="com.dimata.harisma.entity.masterdata.Training"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.TrainingFile"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmTrainingFile"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlTrainingFile"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../main/checkuser.jsp" %>
<!DOCTYPE html>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidTraining = FRMQueryString.requestLong(request, "training_id");
long oidTrainingFile = FRMQueryString.requestLong(request, "training_file_id");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
String strFileName = FRMQueryString.requestString(request, "pict");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

CtrlTrainingFile ctrlTrainingFile = new CtrlTrainingFile(request);
ControlLine ctrLine = new ControlLine();
Vector listTrainingMaterial = new Vector(1,1);

/*switch statement */
//iErrCode = ctrlTrainingFile.action(iCommand , oidEmpRelevantDoc, oidEmployee);
/* end switch*/
FrmTrainingFile frmTrainingFile = ctrlTrainingFile.getForm();
TrainingFile trainingFile = ctrlTrainingFile.getTrainingFile();
msgString =  ctrlTrainingFile.getMessage();

/*switch list CareerPath*/

Vector vectPict = new Vector(1,1);
 vectPict.add(""+oidTraining);
 vectPict.add(""+oidTrainingFile);

session.putValue("SELECTED_PHOTO_SESSION", vectPict);
System.out.println("vectPict.........."+vectPict);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Training Material</title>
        <script language="JavaScript">
        function cmdUpload(){
            document.frm_training_material.command.value="<%=Command.SAVE%>";
            document.frm_training_material.prev_command.value="<%=prevCommand%>";
            document.frm_training_material.action="upload_material_process_new.jsp";
            document.frm_training_material.submit();
        }

        function cmdBack(oidTraining){
            document.frm_training_material.command.value="<%=Command.BACK%>";
            document.frm_training_material.action="training-program.jsp?command="+<%=Command.EDIT%>+"&oid_training=" + oidTraining;
            document.frm_training_material.submit();
        }
        </script>
        <style type="text/css">
            body {
                font-family: sans-serif;
                font-size: 12px;
                background-color: #F5F5F5;
                color: #474747;
                margin: 0;
                padding: 0;
            }
            .info {
                background-color: #EEE;
                padding: 21px;
            }
            .content {
                padding: 21px;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            .footer {
                border-top: 1px solid #DDD;
                padding: 27px 0px;
            }
        </style>
    </head>
    <body>
        <form name="frm_training_material" method ="post" enctype="multipart/form-data" action="">
        <input type="hidden" name="command" value="">
        <input type="hidden" name="training_id" value="<%=oidTraining%>">
        <input type="hidden" name="training_file_id" value="<%=oidTrainingFile%>">
        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
        <div class="info">
            <% if(oidTraining != 0){
                String typeName = "-";
                Training objTraining = new Training();
                try{
                     objTraining = PstTraining.fetchExc(oidTraining);
                     TrainType trainType = PstTrainType.fetchExc(objTraining.getType());
                     typeName = trainType.getTypeName();
                }catch(Exception exc){
                     objTraining = new Training();
                }
            %>

                <table>
                  <tr> 
                    <td><strong>Training Name </strong></td>
                    <td>:</td>
                    <td><strong><%=objTraining.getName()%></strong></td>
                  </tr>
                  <tr> 
                    <td><strong>Training Type</strong></td>
                    <td>:</td>
                    <td><strong><%= typeName %></strong></td>
                  </tr>
                  <tr> 
                    <td><strong>Description</strong></td>
                    <td>:</td>
                    <td><strong><%=objTraining.getDescription()%></strong></td>
                  </tr>
                </table>
            <%}%>
        </div>
        <div class="content">
            <div style="font-weight: bold">Upload File</div>
            <div class=""><input type="file" name="pict" size="60" height="100"></div>
            <div style="margin: 12px 0px">
                <a class="btn" href="javascript:cmdUpload()" class="command">Save File</a>&nbsp;
                <a class="btn" href="javascript:cmdBack('<%=oidTraining%>')" class="command">Back to List</a>
            </div>
        </div>
      </form>
      <div class="footer">
          <p style="padding-left: 21px;">Copyright &copy; Dimata IT Solutions 2015</p>
      </div>
    </body>
</html>
