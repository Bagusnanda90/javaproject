<%-- 
    Document   : plan-list-ajax
    Created on : Jan 16, 2016, 9:59:02 AM
    Author     : Dimata 007
--%>


<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.employee.PstTrainingActivityPlan"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.form.employee.CtrlTrainingActivityPlan"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTrainingDept"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTraining"%>
<%@page import="com.dimata.harisma.entity.masterdata.Training"%>
<%@page import="com.dimata.harisma.entity.employee.TrainingActivityPlan"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.session.employee.SessTraining"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public String drawListGen(Vector objectClass, int start){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("98%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("tableheader");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("tableheader");

        ctrlist.addHeader("No", "", "0", "0");
        ctrlist.addHeader("Training Title", "", "0", "0");
        ctrlist.addHeader("Program", "", "0", "0");
        /* ctrlist.addHeader("Department","","0","0");	*/
        ctrlist.addHeader("No. of Programs", "", "0", "0");
        ctrlist.addHeader("Total Hours", "", "0", "0");
        ctrlist.addHeader("No. of Trainees", "", "0", "0");
        ctrlist.addHeader("Trainer", "", "0", "0");
        ctrlist.addHeader("Remark", "", "0", "0");
        ctrlist.addHeader("Training Material", "", "0", "0");

        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        String whereClause = "";
        Vector rowx = new Vector();

        int sumPlanPrg = 0;
        int sumActPrg = 0;
        int sumPlanHour = 0;
        int sumActHour = 0;
        int sumPlanTrain = 0;
        int sumActTrain = 0;
        int procent = 0;
        String strProcent = "";

        if (objectClass != null && objectClass.size() > 0) {
            int totalDept = PstDepartment.getCount("");

            for (int i = 0; i < objectClass.size(); i++) {
                TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan) objectClass.get(i);
                Training trn = new Training();
                Vector vctDept = new Vector();

                try {
                    trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
                } catch (Exception e) {
                    trn = new Training();
                }

                String where = PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID] + "=" + trn.getOID();
                vctDept = PstTrainingDept.list(0, 0, where, "");

                rowx = new Vector();

                rowx.add("" + (++start));
                rowx.add(trainingActivityPlan.getTrainingTitle());
                rowx.add(trn.getName());

                rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan()));   //2		
                sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();

                rowx.add(SessTraining.getDurationString(trainingActivityPlan.getTotHoursPlan()));   //3				
                sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();

                rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //4				
                sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();

                rowx.add(trainingActivityPlan.getTrainer());    //5
                rowx.add(trainingActivityPlan.getRemark());     //6

                // get training material
                rowx.add("<a style=\"text-decoration:none\" href =\"javascript:cmdView('" + trainingActivityPlan.getTrainingId() + "')\"><font color=\"#30009D\">View</font></a>");     //7

                lstData.add(rowx);
                lstLinkData.add(String.valueOf(trainingActivityPlan.getOID()));
            }
        } else {
            rowx = new Vector();

            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");

            lstData.add(rowx);
        }

        rowx = new Vector();


        rowx.add("");
        rowx.add("&nbsp;<b>Total</b>");
        rowx.add("");
        rowx.add(String.valueOf(sumPlanPrg));
        rowx.add(SessTraining.getDurationString(sumPlanHour));
        rowx.add(String.valueOf(sumPlanTrain));
        rowx.add("");
        rowx.add("");
        rowx.add("");
        lstData.add(rowx);

        return ctrlist.drawList();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String bulan = FRMQueryString.requestString(request, "bulan");
    String tahun = FRMQueryString.requestString(request, "tahun");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = tahun+"-"+bulan+"-01";
    Date date = df.parse(strDate);
    int start = FRMQueryString.requestInt(request,"start");
    int typ = FRMQueryString.requestInt(request, "type");
    CtrlTrainingActivityPlan ctrlTrainingActivityPlan = new CtrlTrainingActivityPlan(request);

    int recordToGet = 20;
    int vectSize = PstPosition.getCount("") + 1;
    String whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_POSITION_ID]+" = 0"+
         " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(date,"yyyy-MM-dd")+"'";

    //Vector listDepartment = new Vector(1,1);
    Vector listPosition = new Vector(1,1);
   
    Vector listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");

    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
        start = ctrlTrainingActivityPlan.actionList(iCommand, start, vectSize, recordToGet);
        listPosition = PstPosition.list((start-1)<0?0:(start-1),(start-1)<0?recordToGet-1:recordToGet,"",PstPosition.fieldNames[PstPosition.FLD_POSITION]);
    }


    //listPlanning = (Vector)PstTrainingActivityPlan.listTrainingPlanByPos(0, date);

    if(listPlanning != null)
        vectSize = listPlanning.size();
    else
        vectSize = 0;

%>
<%= drawListGen(listPlanning, start) %>
